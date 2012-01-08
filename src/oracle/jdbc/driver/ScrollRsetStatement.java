package oracle.jdbc.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

abstract interface ScrollRsetStatement
{
  public abstract Connection getConnection()
    throws SQLException;

  public abstract void notifyCloseRset()
    throws SQLException;

  public abstract int copyBinds(Statement paramStatement, int paramInt)
    throws SQLException;

  public abstract String getOriginalSql()
    throws SQLException;

  public abstract OracleResultSetCache getResultSetCache()
    throws SQLException;

  public abstract void setAutoRefetch(boolean paramBoolean)
    throws SQLException;

  public abstract boolean getAutoRefetch()
    throws SQLException;

  public abstract int getMaxFieldSize()
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ScrollRsetStatement
 * JD-Core Version:    0.6.0
 */