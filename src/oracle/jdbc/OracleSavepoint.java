package oracle.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

public abstract interface OracleSavepoint extends Savepoint
{
  public abstract int getSavepointId()
    throws SQLException;

  public abstract String getSavepointName()
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleSavepoint
 * JD-Core Version:    0.6.0
 */