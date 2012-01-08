package oracle.net.nt;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;
import oracle.net.nl.NLException;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVNavigator;
import oracle.net.nl.NVPair;
import oracle.net.ns.NetException;

public class ConnOption
{
  public NTAdapter nt;
  public int port;
  public int tdu;
  public int sdu;
  public String protocol;
  public String host;
  public String sid;
  public String addr;
  public String service_name;
  public String instance_name;
  public StringBuffer conn_data = new StringBuffer();
  public String sslServerCertDN;
  public String origSSLServerCertDN;
  public String origServiceName;
  public String origSid;
  public boolean done;

  private NTAdapter getNT(Properties paramProperties)
    throws NetException
  {
    try
    {
      if (this.protocol.equalsIgnoreCase("tcp"))
      {
        this.nt = new TcpNTAdapter(this.addr, paramProperties);
        this.origServiceName = this.service_name;
        this.origSid = this.sid;
      }
      else if (this.protocol.equalsIgnoreCase("tcps"))
      {
        Class localClass = Class.forName("oracle.net.nt.TcpsNTAdapter");
        Class[] arrayOfClass = { String.class, Properties.class };
        Object[] arrayOfObject = { this.addr, paramProperties };
        this.nt = ((NTAdapter)localClass.getConstructor(arrayOfClass).newInstance(arrayOfObject));
        this.origSSLServerCertDN = this.sslServerCertDN;
        this.origServiceName = this.service_name;
        this.origSid = this.sid;
        String[] arrayOfString = { this.origSSLServerCertDN, this.origServiceName, this.origSid };
        this.nt.setOption(8, arrayOfString);
      }
      else
      {
        throw new NetException(21);
      }
    }
    catch (NLException localNLException)
    {
      throw new NetException(501);
    }
    catch (Exception localException)
    {
      throw new NetException(21);
    }
    return this.nt;
  }

  public void connect(Properties paramProperties)
    throws IOException
  {
    try
    {
      populateProtocol();
      if (this.protocol == null)
        throw new NetException(501);
    }
    catch (NLException localNLException)
    {
      throw new NetException(501);
    }
    this.nt = getNT(paramProperties);
    this.nt.connect();
  }

  private void populateProtocol()
    throws NLException
  {
    NVPair localNVPair1 = null;
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair2 = new NVFactory().createNVPair(this.addr);
    localNVPair1 = localNVNavigator.findNVPair(localNVPair2, "PROTOCOL");
    if (localNVPair1 != null)
      this.protocol = localNVPair1.getAtom();
    else
      throw new NLException("NoNVPair-04614", "PROTOCOL");
  }

  public void restoreFromOrigCoption(ConnOption paramConnOption)
    throws IOException
  {
    this.origSSLServerCertDN = paramConnOption.origSSLServerCertDN;
    this.origServiceName = paramConnOption.origServiceName;
    this.origSid = paramConnOption.origSid;
    this.conn_data = paramConnOption.conn_data;
    if (this.protocol.equalsIgnoreCase("tcps"))
    {
      String[] arrayOfString = { this.origSSLServerCertDN, this.origServiceName, this.origSid };
      this.nt.setOption(8, arrayOfString);
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.nt.ConnOption
 * JD-Core Version:    0.6.0
 */