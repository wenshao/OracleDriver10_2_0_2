package oracle.jdbc.pool;

import oracle.jdbc.OracleConnection;

public abstract interface OracleConnectionCacheCallback
{
  public abstract boolean handleAbandonedConnection(OracleConnection paramOracleConnection, Object paramObject);

  public abstract void releaseConnection(OracleConnection paramOracleConnection, Object paramObject);
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionCacheCallback
 * JD-Core Version:    0.6.0
 */