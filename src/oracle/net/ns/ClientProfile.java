package oracle.net.ns;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

public class ClientProfile extends Properties
{
  private static final String profile_name = "ora-net-profile";
  private static final String shared_profile_name = "ora-shared-profile";

  public ClientProfile()
  {
  }

  public ClientProfile(Properties paramProperties)
  {
    if (paramProperties.containsKey("oracle.net.profile"))
      put("oracle.net.profile", paramProperties.getProperty("oracle.net.profile"));
    put("oracle.net.authentication_services", paramProperties.getProperty("oracle.net.authentication_services", "()"));
    put("oracle.net.encryption_client", paramProperties.getProperty("oracle.net.encryption_client", "ACCEPTED"));
    put("oracle.net.encryption_types_client", paramProperties.getProperty("oracle.net.encryption_types_client", "()"));
    put("oracle.net.crypto_checksum_client", paramProperties.getProperty("oracle.net.crypto_checksum_client", "ACCEPTED"));
    put("oracle.net.crypto_checksum_types_client", paramProperties.getProperty("oracle.net.crypto_checksum_types_client", "()"));
    put("oracle.net.crypto_seed", paramProperties.getProperty("oracle.net.crypto_seed", ""));
  }

  public String[] getAuthenticationServices()
  {
    return getServices((String)get("oracle.net.authentication_services"));
  }

  public String[] getEncryptionServices()
  {
    return getServices((String)get("oracle.net.encryption_types_client"));
  }

  public String[] getDataIntegrityServices()
  {
    return getServices((String)get("oracle.net.crypto_checksum_types_client"));
  }

  public String getEncryptionLevel()
  {
    return (String)get("oracle.net.encryption_client");
  }

  public int getEncryptionLevelNum()
  {
    return translateAnoValue(getEncryptionLevel());
  }

  public String getDataIntegrityLevel()
  {
    return (String)get("oracle.net.crypto_checksum_client");
  }

  public int getDataIntegrityLevelNum()
  {
    return translateAnoValue(getDataIntegrityLevel());
  }

  public void print()
  {
    System.out.println(" ----------------------------------------");
    System.out.println(" Displaying the content of ClientProfile ");
    System.out.println(" List:");
    list(System.out);
    Enumeration localEnumeration = propertyNames();
    System.out.println("Enumeration has elements ? " + localEnumeration.hasMoreElements());
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      String str = (String)localEnumeration.nextElement();
      System.out.println("Key " + i + " = " + str);
      System.out.println("Value = " + getProperty(str));
    }
    System.out.println(" ----------------------------------------");
  }

  private String[] getServices(String paramString)
  {
    String str = removeParenths(paramString);
    StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
    int i = localStringTokenizer.countTokens();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
      arrayOfString[j] = localStringTokenizer.nextToken().trim();
    return arrayOfString;
  }

  private String removeParenths(String paramString)
  {
    int i = paramString.indexOf('(');
    int j = i == -1 ? 0 : i + 1;
    int k = paramString.lastIndexOf(')');
    int m = k == -1 ? paramString.length() : k;
    String str = paramString.substring(j, m);
    return str.trim();
  }

  private int translateAnoValue(String paramString)
  {
    int i = 0;
    if (paramString.equalsIgnoreCase("ACCEPTED"))
      i = 0;
    else if (paramString.equalsIgnoreCase("REQUESTED"))
      i = 2;
    else if (paramString.equalsIgnoreCase("REQUIRED"))
      i = 3;
    else if (paramString.equalsIgnoreCase("REJECTED"))
      i = 1;
    return i;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.ClientProfile
 * JD-Core Version:    0.6.0
 */