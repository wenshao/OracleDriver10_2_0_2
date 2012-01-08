package oracle.jdbc.pool;

import java.sql.SQLException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

public abstract interface OracleConnectionCache extends DataSource
{
  public abstract void reusePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException;

  public abstract void closePooledConnection(PooledConnection paramPooledConnection)
    throws SQLException;

  public abstract void close()
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionCache
 * JD-Core Version:    0.6.0
 */