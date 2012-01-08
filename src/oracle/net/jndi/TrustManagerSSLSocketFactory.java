package oracle.net.jndi;

import javax.net.ssl.SSLContext;

public final class TrustManagerSSLSocketFactory extends CustomSSLSocketFactory
{
  private static final boolean DEBUG = false;

  protected void setDefaultFactory()
  {
    try
    {
      SSLContext localSSLContext = SSLContext.getInstance("SSL");
      localSSLContext.init(null, new javax.net.ssl.TrustManager[] { new TrustManager() }, null);
      setFactory(localSSLContext.getSocketFactory());
    }
    catch (Exception localException)
    {
      super.setDefaultFactory();
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.jndi.TrustManagerSSLSocketFactory
 * JD-Core Version:    0.6.0
 */