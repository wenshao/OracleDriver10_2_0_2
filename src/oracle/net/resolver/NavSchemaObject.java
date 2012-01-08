package oracle.net.resolver;

import oracle.net.TNSAddress.SchemaObject;
import oracle.net.nt.ConnStrategy;

public abstract interface NavSchemaObject extends SchemaObject
{
  public static final boolean DEBUG = false;
  public static final String SR = "(SOURCE_ROUTE=yes)";
  public static final String HC = "(HOP_COUNT=0)";
  public static final String LB = "(LOAD_BALANCE=yes)";
  public static final String NFO = "(FAILOVER=false)";
  public static final String CD = "(CONNECT_DATA=";
  public static final String CID = "(CID=(PROGRAM=)(HOST=__jdbc__)(USER=))";

  public abstract void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer);

  public abstract void addToString(ConnStrategy paramConnStrategy);
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NavSchemaObject
 * JD-Core Version:    0.6.0
 */