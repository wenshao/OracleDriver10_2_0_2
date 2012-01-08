package oracle.net.nt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import oracle.net.ns.NetException;

public class ConnStrategy
{
  static final boolean DEBUG = false;
  private boolean optFound = false;
  public boolean reuseOpt = false;
  private ConnOption copt;
  public int sdu;
  public int tdu;
  public int nextOptToTry = 0;
  public Properties socketOptions = new Properties();
  public Vector cOpts = new Vector(10, 10);

  public ConnStrategy(Properties paramProperties)
  {
    createSocketOptions(paramProperties);
  }

  public void createSocketOptions(Properties paramProperties)
  {
    String str1 = null;
    String str2 = null;
    int i = 0;
    Enumeration localEnumeration = paramProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      if (str1.equalsIgnoreCase("TCP.NODELAY"))
      {
        i = 1;
        str2 = paramProperties.getProperty("TCP.NODELAY").toUpperCase();
        if (str2.equals("NO"))
        {
          this.socketOptions.put(new Integer(0), "NO");
          continue;
        }
        this.socketOptions.put(new Integer(0), "YES");
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.READ_TIMEOUT"))
      {
        str2 = paramProperties.getProperty("oracle.net.READ_TIMEOUT");
        this.socketOptions.put(new Integer(3), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.CONNECT_TIMEOUT"))
      {
        str2 = paramProperties.getProperty("oracle.net.CONNECT_TIMEOUT");
        this.socketOptions.put(new Integer(2), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.ssl_server_dn_match"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_server_dn_match");
        this.socketOptions.put(new Integer(4), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.wallet_location"))
      {
        str2 = paramProperties.getProperty("oracle.net.wallet_location");
        this.socketOptions.put(new Integer(5), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.ssl_version"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_version");
        this.socketOptions.put(new Integer(6), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("oracle.net.ssl_cipher_suites"))
      {
        str2 = paramProperties.getProperty("oracle.net.ssl_cipher_suites");
        this.socketOptions.put(new Integer(7), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.keyStore"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStore");
        this.socketOptions.put(new Integer(8), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.keyStoreType"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStoreType");
        this.socketOptions.put(new Integer(9), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.keyStorePassword"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.keyStorePassword");
        this.socketOptions.put(new Integer(10), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.trustStore"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStore");
        this.socketOptions.put(new Integer(11), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.trustStoreType"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStoreType");
        this.socketOptions.put(new Integer(12), str2);
        continue;
      }
      if (str1.equalsIgnoreCase("javax.net.ssl.trustStorePassword"))
      {
        str2 = paramProperties.getProperty("javax.net.ssl.trustStorePassword");
        this.socketOptions.put(new Integer(13), str2);
        continue;
      }
      if (!str1.equalsIgnoreCase("ssl.keyManagerFactory.algorithm"))
        continue;
      str2 = paramProperties.getProperty("ssl.keyManagerFactory.algorithm");
      this.socketOptions.put(new Integer(14), str2);
    }
    if ((i == 0) && (!this.reuseOpt))
      this.socketOptions.put(new Integer(0), "YES");
  }

  public void addSocketOptions(boolean paramBoolean)
  {
    if (paramBoolean)
      this.socketOptions.put(new Integer(1), "YES");
    else if (!this.reuseOpt)
      this.socketOptions.put(new Integer(1), "NO");
  }

  public void addOption(ConnOption paramConnOption)
  {
    this.cOpts.addElement(paramConnOption);
  }

  public boolean hasMoreOptions()
  {
    return this.nextOptToTry < this.cOpts.size();
  }

  public ConnOption execute()
    throws NetException
  {
    while (this.nextOptToTry < this.cOpts.size())
      try
      {
        this.copt = ((ConnOption)this.cOpts.elementAt(this.nextOptToTry));
        this.copt.connect(this.socketOptions);
        this.copt.sdu = this.sdu;
        this.copt.tdu = this.tdu;
        this.optFound = true;
        this.nextOptToTry += 1;
        return this.copt;
      }
      catch (IOException localIOException)
      {
        this.nextOptToTry += 1;
      }
    throw new NetException(20);
  }

  public boolean optAvailable()
  {
    return this.optFound;
  }

  public void clearElements()
  {
    this.cOpts.removeAllElements();
  }

  public ConnOption getOption()
  {
    return this.copt;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.nt.ConnStrategy
 * JD-Core Version:    0.6.0
 */