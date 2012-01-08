package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public abstract interface Communication
{
  public abstract void connect(String paramString, Properties paramProperties)
    throws IOException, NetException;

  public abstract void disconnect()
    throws IOException, NetException;

  public abstract void sendBreak()
    throws IOException, NetException;

  public abstract void sendReset()
    throws IOException, NetException;

  public abstract InputStream getInputStream()
    throws NetException;

  public abstract OutputStream getOutputStream()
    throws NetException;

  public abstract void setO3logSessionKey(byte[] paramArrayOfByte)
    throws NetException, NetException;

  public abstract void setOption(int paramInt, Object paramObject)
    throws NetException, IOException;

  public abstract Object getOption(int paramInt)
    throws NetException, IOException;

  public abstract void abort()
    throws NetException, IOException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.Communication
 * JD-Core Version:    0.6.0
 */