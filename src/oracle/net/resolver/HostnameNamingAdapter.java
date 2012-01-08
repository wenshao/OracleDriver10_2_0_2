package oracle.net.resolver;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import oracle.net.ns.NetException;

public class HostnameNamingAdapter
  implements NamingAdapterInterface
{
  public static final int DEFAULT_DATABASE_PORT = 1521;
  public static final String DEFAULT_PROTOCOL = "TCP";

  public String resolve(String paramString)
    throws NetException
  {
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (paramString.startsWith("//") == true)
      paramString = paramString.substring(2);
    if (paramString.charAt(0) == '[')
    {
      i1 = 1;
      n = paramString.indexOf(']');
      if (n != -1)
        j = paramString.indexOf(':', n);
      if ((n == -1) || ((j != -1) && (j != n + 1)))
        throw new NetException(117);
      m = 1;
      k = paramString.indexOf('/', n);
    }
    else
    {
      m = 0;
      j = paramString.indexOf(':');
      k = paramString.indexOf('/', m);
    }
    if (((k != -1) && (j > k)) || (paramString.endsWith("/")) || (paramString.endsWith(":")))
      throw new NetException(117);
    String str3;
    try
    {
      String str1;
      if (i1 != 0)
        str1 = paramString.substring(m, n);
      else if (j != -1)
        str1 = paramString.substring(m, j);
      else if (k != -1)
        str1 = paramString.substring(m, k);
      else
        str1 = paramString.substring(m);
      int i;
      if (j != -1)
      {
        if (k != -1)
          i = Integer.parseInt(paramString.substring(j + 1, k));
        else
          i = Integer.parseInt(paramString.substring(j + 1));
      }
      else
        i = 1521;
      String str2;
      if (k != -1)
        str2 = paramString.substring(k + 1);
      else
        str2 = str1;
      InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(str1);
      if ((k == -1) && (str1.indexOf(".") == -1))
        try
        {
          Class localClass = Class.forName("java.net.InetAddress");
          Method localMethod = localClass.getMethod("getCanonicalHostName", null);
          if (localMethod != null)
            str2 = (String)localMethod.invoke(arrayOfInetAddress[0], null);
        }
        catch (Exception localException)
        {
        }
      String str4 = "";
      for (int i2 = 0; i2 < arrayOfInetAddress.length; i2++)
      {
        str1 = arrayOfInetAddress[i2].getHostAddress();
        str4 = str4 + "(ADDRESS=" + "(PROTOCOL=" + "TCP" + ")(HOST=" + str1 + ")(PORT=" + i + "))";
      }
      String str5 = "(DESCRIPTION=(CONNECT_DATA=(SERVICE_NAME=" + str2 + ")(CID=(PROGRAM=)(HOST=__jdbc__)(USER=)))";
      str3 = str5 + str4 + ")";
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new NetException(116);
    }
    catch (UnknownHostException localUnknownHostException)
    {
      throw new NetException(118);
    }
    return str3;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.HostnameNamingAdapter
 * JD-Core Version:    0.6.0
 */