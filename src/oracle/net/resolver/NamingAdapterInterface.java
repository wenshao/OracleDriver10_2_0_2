package oracle.net.resolver;

import oracle.net.ns.NetException;

public abstract interface NamingAdapterInterface
{
  public static final boolean DEBUG = false;

  public abstract String resolve(String paramString)
    throws NetException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NamingAdapterInterface
 * JD-Core Version:    0.6.0
 */