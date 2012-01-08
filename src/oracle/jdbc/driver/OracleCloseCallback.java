package oracle.jdbc.driver;

import oracle.jdbc.internal.OracleConnection;

public abstract interface OracleCloseCallback
{
  public abstract void beforeClose(OracleConnection paramOracleConnection, Object paramObject);

  public abstract void afterClose(Object paramObject);
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleCloseCallback
 * JD-Core Version:    0.6.0
 */