package oracle.net.resolver;

import java.util.ArrayList;
import java.util.Hashtable;
import oracle.net.nl.InvalidSyntaxException;
import oracle.net.nl.NLException;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVPair;
import oracle.net.ns.NetException;

public class NameResolver
{
  private String tnsAdmin;
  private String[] readPath;
  private Hashtable adapterHash;
  private static final boolean DEBUG = false;
  private static final String[] DEFAULT_SEARCH_PATH = { "TNSNAMES", "HOSTNAME" };
  private static final String TNS_ADMIN_PROPERTY = "oracle.net.tns_admin";
  private static final String READ_PATH_PROPERTY = "oracle.net.names.directory_path";

  protected NameResolver(String paramString)
    throws NetException
  {
    this.tnsAdmin = paramString;
    bootNameResolver();
  }

  public String resolveName(String paramString)
    throws NetException
  {
    if (paramString == null)
      throw new NetException(120);
    String str1 = paramString.trim();
    if (str1.length() == 0)
      throw new NetException(120);
    String str2 = null;
    if (this.tnsAdmin == null)
    {
      localObject = new HostnameNamingAdapter();
      str2 = ((HostnameNamingAdapter)localObject).resolve(str1);
      return str2;
    }
    Object localObject = null;
    for (int i = 0; (str2 == null) && (i < this.readPath.length); i++)
    {
      localObject = (NamingAdapterInterface)this.adapterHash.get(this.readPath[i]);
      try
      {
        str2 = ((NamingAdapterInterface)localObject).resolve(str1);
      }
      catch (NetException localNetException)
      {
      }
    }
    if (str2 == null)
      throw new NetException(122, "\"" + paramString + "\"");
    return (String)str2;
  }

  private void addAdapters()
  {
    if (this.adapterHash == null)
      this.adapterHash = new Hashtable();
    this.adapterHash.put("TNSNAMES", new TNSNamesNamingAdapter(this.tnsAdmin));
    this.adapterHash.put("HOSTNAME", new HostnameNamingAdapter());
  }

  private void bootNameResolver()
    throws NetException
  {
    if (this.tnsAdmin != null)
    {
      setReadPath();
      addAdapters();
    }
  }

  private boolean checkForValidAdapter(String paramString)
  {
    int j = DEFAULT_SEARCH_PATH.length;
    for (int i = 0; i < j; i++)
      if (DEFAULT_SEARCH_PATH[i].equalsIgnoreCase(paramString))
        return true;
    return false;
  }

  private void setDefaultPath()
  {
    this.readPath = DEFAULT_SEARCH_PATH;
  }

  private void setReadPath()
    throws NetException
  {
    int i = 0;
    String[] arrayOfString = getUserReadPath();
    if (arrayOfString == null)
    {
      setDefaultPath();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    for (i = 0; i < arrayOfString.length; i++)
    {
      if (checkForValidAdapter(arrayOfString[i]) != true)
        continue;
      String str = arrayOfString[i];
      str = str.toUpperCase();
      if (localArrayList.contains(str))
        continue;
      localArrayList.add(arrayOfString[i].toUpperCase());
    }
    int j = localArrayList.size();
    if (j == 0)
      throw new NetException(121, " The Read path did not contain any valid adapters.");
    this.readPath = new String[j];
    localArrayList.toArray(this.readPath);
  }

  private String[] getUserReadPath()
    throws NetException
  {
    Object localObject = null;
    String str1 = System.getProperty("oracle.net.names.directory_path");
    if (str1 == null)
      return null;
    str1 = str1.trim();
    if (str1.length() == 0)
      return null;
    StringBuffer localStringBuffer = new StringBuffer(str1);
    if (localStringBuffer.charAt(0) == '(')
      localStringBuffer.insert(0, "(path=").append(')');
    else
      localStringBuffer.insert(0, "(path=(").append("))");
    String str2 = localStringBuffer.toString();
    NVFactory localNVFactory = new NVFactory();
    NVPair localNVPair = null;
    try
    {
      localNVPair = localNVFactory.createNVPair(str2);
      if ((localNVPair.getRHSType() != NVPair.RHS_LIST) || (localNVPair.getListType() != NVPair.LIST_COMMASEP))
        throw new NetException(121, " Read path specified is " + str1);
      int i = localNVPair.getListSize();
      String[] arrayOfString = new String[i];
      for (int j = 0; j < i; j++)
        arrayOfString[j] = localNVPair.getListElement(j).getName();
      localObject = arrayOfString;
    }
    catch (InvalidSyntaxException localInvalidSyntaxException)
    {
      throw new NetException(121, " Read path specified is " + str1);
    }
    catch (NLException localNLException)
    {
      throw new NetException(121, " Read path specified is " + str2);
    }
    return localObject;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NameResolver
 * JD-Core Version:    0.6.0
 */