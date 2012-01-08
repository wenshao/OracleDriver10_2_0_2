package oracle.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract interface OracleResultSetMetaData extends ResultSetMetaData
{
  public abstract boolean isNCHAR(int paramInt)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleResultSetMetaData
 * JD-Core Version:    0.6.0
 */