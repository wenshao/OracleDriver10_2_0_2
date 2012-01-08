package oracle.net.nt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;
import oracle.net.nl.NLException;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVNavigator;
import oracle.net.nl.NVPair;
import oracle.net.ns.NetException;

public class TcpNTAdapter
  implements NTAdapter
{
  static final boolean DEBUG = false;
  int port;
  String host;
  protected Socket socket;
  protected int sockTimeout;
  protected Properties socketOptions;

  public TcpNTAdapter(String paramString, Properties paramProperties)
    throws NLException
  {
    this.socketOptions = paramProperties;
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair1 = new NVFactory().createNVPair(paramString);
    NVPair localNVPair2 = localNVNavigator.findNVPair(localNVPair1, "HOST");
    NVPair localNVPair3 = localNVNavigator.findNVPair(localNVPair1, "PORT");
    if (localNVPair2 == null)
      throw new NLException("NoNVPair-04614", "HOST");
    if (localNVPair3 == null)
      throw new NLException("NoNVPair-04614", "PORT");
    this.host = localNVPair2.getAtom();
    try
    {
      this.port = Integer.parseInt(localNVPair3.getAtom());
    }
    catch (Exception localException)
    {
      throw new NLException(new NetException(116).getMessage());
    }
    if ((this.port < 0) || (this.port > 65535))
      throw new NLException(new NetException(116).getMessage());
  }

  public void connect()
    throws IOException
  {
    String str = (String)this.socketOptions.get(new Integer(2));
    if (str != null)
      try
      {
        Class[] arrayOfClass1 = { Class.forName("java.net.SocketAddress"), Integer.TYPE };
        Method localMethod = Socket.class.getMethod("connect", arrayOfClass1);
        if (localMethod != null)
        {
          Class[] arrayOfClass2 = { String.class, Integer.TYPE };
          Object[] arrayOfObject1 = { this.host, new Integer(this.port) };
          Object[] arrayOfObject2 = { Class.forName("java.net.InetSocketAddress").getConstructor(arrayOfClass2).newInstance(arrayOfObject1), new Integer(str) };
          this.socket = ((Socket)Class.forName("java.net.Socket").newInstance());
          localMethod.invoke(this.socket, arrayOfObject2);
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        if ((localInvocationTargetException.getTargetException() instanceof InterruptedIOException))
          throw new NetException(503);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new NetException(505);
      }
      catch (Exception localException)
      {
      }
    if (this.socket == null)
      this.socket = new Socket(this.host, this.port);
    setSocketOptions();
  }

  public void setSocketOptions()
    throws IOException
  {
    int i = 0;
    String str = null;
    Class localClass = Socket.class;
    Class[] arrayOfClass = { Boolean.TYPE };
    Object[] arrayOfObject = { Boolean.TRUE };
    Enumeration localEnumeration = this.socketOptions.keys();
    while (localEnumeration.hasMoreElements())
    {
      i = ((Integer)localEnumeration.nextElement()).intValue();
      switch (i)
      {
      case 0:
        str = (String)this.socketOptions.get(new Integer(0));
        try
        {
          if (str.equals("YES"))
            this.socket.setTcpNoDelay(true);
          else
            this.socket.setTcpNoDelay(false);
        }
        catch (SocketException localSocketException)
        {
          throw new IOException(localSocketException.getMessage());
        }
      case 1:
        str = (String)this.socketOptions.get(new Integer(1));
        if (!str.equals("YES"))
          continue;
        try
        {
          Method localMethod = localClass.getMethod("setKeepAlive", arrayOfClass);
          if (localMethod != null)
            localMethod.invoke(this.socket, arrayOfObject);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          throw new IOException(localIllegalAccessException.getMessage());
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          throw new IOException(localInvocationTargetException.getMessage());
        }
      case 3:
        str = (String)this.socketOptions.get(new Integer(3));
        try
        {
          this.sockTimeout = Integer.parseInt(str);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new NetException(506);
        }
        setSocketTimeout(this.sockTimeout);
        continue;
      case 2:
      }
    }
  }

  public void disconnect()
    throws IOException
  {
    try
    {
      this.socket.close();
    }
    finally
    {
      this.socket = null;
    }
  }

  public InputStream getInputStream()
    throws IOException
  {
    return this.socket.getInputStream();
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    return this.socket.getOutputStream();
  }

  public void setOption(int paramInt, Object paramObject)
    throws IOException, NetException
  {
    switch (paramInt)
    {
    case 1:
      this.sockTimeout = Integer.parseInt((String)paramObject);
      setSocketTimeout(this.sockTimeout);
      return;
    }
  }

  public Object getOption(int paramInt)
    throws IOException, NetException
  {
    switch (paramInt)
    {
    case 1:
      return "" + this.sockTimeout;
    }
    return null;
  }

  private void setSocketTimeout(int paramInt)
    throws IOException
  {
    if (this.socket != null)
      try
      {
        Class localClass = Socket.class;
        Class[] arrayOfClass = { Integer.TYPE };
        Method localMethod = localClass.getMethod("setSoTimeout", arrayOfClass);
        if (localMethod != null)
        {
          Object[] arrayOfObject = { new Integer(paramInt) };
          localMethod.invoke(this.socket, arrayOfObject);
          return;
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new IOException(localInvocationTargetException.getMessage());
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new IOException(localIllegalAccessException.getMessage());
      }
  }

  public void abort()
    throws NetException, IOException
  {
    try
    {
      this.socket.setSoLinger(true, 0);
    }
    catch (Exception localException)
    {
    }
    this.socket.close();
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.nt.TcpNTAdapter
 * JD-Core Version:    0.6.0
 */