package oracle.net.nt;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.util.Properties;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVNavigator;
import oracle.net.nl.NVPair;
import oracle.net.ns.NetException;

public class CustomSSLSocketFactory
{
  static final boolean DEBUG = false;
  public static final String CLEAR_WALLET_FILE_NAME = "cwallet.sso";
  public static final String CLEAR_WALLET_TYPE = "SSO";
  public static final String SUPPORTED_METHOD_TYPE = "FILE";
  public static SSLSocketFactory defSSLFactory;
  public static String defPropString = getDefaultPropertiesString();
  public static boolean initDefFactory;

  public static SSLSocketFactory getSSLSocketFactory(Properties paramProperties)
    throws IOException
  {
    String str3;
    String str2;
    String str1 = str2 = str3 = null;
    String str5;
    String str6;
    String str4 = str6 = str5 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    str11 = (String)paramProperties.get(new Integer(5));
    if (str11 == null)
      str11 = System.getProperty("oracle.net.wallet_location");
    if ((str11 == null) || ((paramProperties.get(new Integer(5)) == null) && (paramProperties.get(new Integer(8)) != null)))
    {
      str1 = (String)paramProperties.get(new Integer(8));
      if (str1 == null)
        str1 = System.getProperty("javax.net.ssl.keyStore");
      if (str1 != null)
      {
        str3 = (String)paramProperties.get(new Integer(9));
        if (str3 == null)
          str3 = System.getProperty("javax.net.ssl.keyStoreType", KeyStore.getDefaultType());
        str2 = (String)paramProperties.get(new Integer(10));
        if (str2 == null)
          str2 = System.getProperty("javax.net.ssl.keyStorePassword", "");
        str7 = (String)paramProperties.get(new Integer(14));
        if (str7 == null)
          str7 = Security.getProperty("ssl.keyManagerFactory.algorithm");
        if (str7 == null)
          str7 = KeyManagerFactory.getDefaultAlgorithm();
      }
      str4 = (String)paramProperties.get(new Integer(11));
      if (str4 == null)
        str4 = System.getProperty("javax.net.ssl.trustStore");
      if (str4 != null)
      {
        str5 = (String)paramProperties.get(new Integer(12));
        if (str5 == null)
          str5 = System.getProperty("javax.net.ssl.trustStoreType", KeyStore.getDefaultType());
        str6 = (String)paramProperties.get(new Integer(13));
        if (str6 == null)
          str6 = System.getProperty("javax.net.ssl.trustStorePassword", "");
        str8 = (String)paramProperties.get(new Integer(15));
        if (str8 == null)
          str8 = Security.getProperty("ssl.trustManagerFactory.algorithm");
        if (str8 == null)
          str8 = TrustManagerFactory.getDefaultAlgorithm();
      }
      str9 = str1 + "#" + str3 + "#" + str2 + "#" + str4 + "#" + str5 + "#" + str6 + "#" + str7 + "#" + str8;
    }
    else
    {
      str10 = processWalletLocation(str11);
      str1 = str10 + System.getProperty("file.separator") + "cwallet.sso";
      str3 = "SSO";
      str2 = "";
      str7 = KeyManagerFactory.getDefaultAlgorithm();
      str4 = str1;
      str5 = "SSO";
      str6 = "";
      str8 = TrustManagerFactory.getDefaultAlgorithm();
      str9 = str11 + "#" + str7 + "#" + str8;
    }
    Object localObject1;
    if (str9.equals(defPropString))
    {
      if (initDefFactory)
        return defSSLFactory;
      synchronized (CustomSSLSocketFactory.class)
      {
        if (!initDefFactory)
          try
          {
            KeyManager[] arrayOfKeyManager = null;
            localObject1 = null;
            if (str1 != null)
              arrayOfKeyManager = getKeyManagerArray(str1, str2, str3, str7);
            if (str4 != null)
              localObject1 = getTrustManagerArray(str4, str6, str5, str8);
            SSLContext localSSLContext = SSLContext.getInstance("SSL");
            localSSLContext.init(arrayOfKeyManager, localObject1, null);
            defSSLFactory = localSSLContext.getSocketFactory();
            if (defSSLFactory != null)
              initDefFactory = true;
          }
          catch (Exception localException2)
          {
            throw new NetException(410, localException2.toString());
          }
        return defSSLFactory;
      }
    }
    try
    {
      ??? = null;
      TrustManager[] arrayOfTrustManager = null;
      if (str1 != null)
        ??? = getKeyManagerArray(str1, str2, str3, str7);
      if (str4 != null)
        arrayOfTrustManager = getTrustManagerArray(str4, str6, str5, str8);
      localObject1 = SSLContext.getInstance("SSL");
      ((SSLContext)localObject1).init(???, arrayOfTrustManager, null);
      return ((SSLContext)localObject1).getSocketFactory();
    }
    catch (Exception localException1)
    {
    }
    throw new NetException(410, localException1.toString());
  }

  public static KeyManager[] getKeyManagerArray(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(paramString3);
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      localKeyStore.load(localFileInputStream, paramString2.toCharArray());
      KeyManagerFactory localKeyManagerFactory = KeyManagerFactory.getInstance(paramString4);
      localKeyManagerFactory.init(localKeyStore, paramString2.toCharArray());
      return localKeyManagerFactory.getKeyManagers();
    }
    catch (Exception localException)
    {
    }
    throw new NetException(408, localException.toString());
  }

  public static TrustManager[] getTrustManagerArray(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(paramString3);
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      localKeyStore.load(localFileInputStream, paramString2.toCharArray());
      TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance(paramString4);
      localTrustManagerFactory.init(localKeyStore);
      return localTrustManagerFactory.getTrustManagers();
    }
    catch (Exception localException)
    {
    }
    throw new NetException(409, localException.toString());
  }

  public static String processWalletLocation(String paramString)
    throws NetException
  {
    String str1 = null;
    try
    {
      NVNavigator localNVNavigator = new NVNavigator();
      NVPair localNVPair1 = new NVFactory().createNVPair(paramString);
      NVPair localNVPair2 = localNVNavigator.findNVPair(localNVPair1, "METHOD");
      NVPair localNVPair3 = localNVNavigator.findNVPair(localNVPair1, "METHOD_DATA");
      NVPair localNVPair4 = localNVNavigator.findNVPair(localNVPair3, "DIRECTORY");
      str1 = localNVPair2.getAtom();
      if (str1.equalsIgnoreCase("FILE"))
      {
        String str2 = localNVPair4.getAtom();
        return str2;
      }
      throw new NetException(412, str1);
    }
    catch (Exception localException)
    {
    }
    throw new NetException(407, localException.toString());
  }

  public static String getDefaultPropertiesString()
  {
    Object localObject1 = null;
    String str1 = null;
    Object localObject2 = null;
    String str2 = null;
    String str3 = null;
    str1 = System.getProperty("oracle.net.wallet_location");
    if (str1 != null)
      localObject1 = str1;
    else
      localObject1 = System.getProperty("javax.net.ssl.keyStore", "") + "#" + System.getProperty("javax.net.ssl.keyStoreType", KeyStore.getDefaultType()) + "#" + System.getProperty("javax.net.ssl.keyStorePassword", "") + "#" + System.getProperty("javax.net.ssl.trustStore", "") + "#" + System.getProperty("javax.net.ssl.trustStoreType", KeyStore.getDefaultType()) + "#" + System.getProperty("javax.net.ssl.trustStorePassword", "");
    if (str2 == null)
      str2 = KeyManagerFactory.getDefaultAlgorithm();
    if (str3 == null)
      str3 = TrustManagerFactory.getDefaultAlgorithm();
    return (String)((String)localObject1 + "#" + str2 + "#" + str3);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.nt.CustomSSLSocketFactory
 * JD-Core Version:    0.6.0
 */