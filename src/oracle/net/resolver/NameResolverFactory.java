package oracle.net.resolver;

import java.util.HashMap;
import oracle.net.ns.NetException;

public class NameResolverFactory
{
  private static HashMap resolverMap = new HashMap();
  private static final String TNS_ADMIN_PROPERTY = "oracle.net.tns_admin";
  private static final boolean DEBUG = false;

  public static NameResolver getNameResolver(String paramString)
    throws NetException
  {
    if (paramString != null)
      paramString = paramString.trim();
    if ((paramString != null) && (paramString.length() == 0))
      throw new NetException(119);
    synchronized (NameResolverFactory.class)
    {
      NameResolver localNameResolver = (NameResolver)resolverMap.get(paramString);
      if (localNameResolver == null)
      {
        localNameResolver = new NameResolver(paramString);
        resolverMap.put(paramString, localNameResolver);
      }
      return localNameResolver;
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NameResolverFactory
 * JD-Core Version:    0.6.0
 */