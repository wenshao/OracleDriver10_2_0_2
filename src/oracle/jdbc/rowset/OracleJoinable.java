package oracle.jdbc.rowset;

import java.sql.SQLException;
import javax.sql.rowset.Joinable;

public abstract interface OracleJoinable extends Joinable
{
  public abstract int[] getMatchColumnIndexes()
    throws SQLException;

  public abstract String[] getMatchColumnNames()
    throws SQLException;

  public abstract void setMatchColumn(int paramInt)
    throws SQLException;

  public abstract void setMatchColumn(int[] paramArrayOfInt)
    throws SQLException;

  public abstract void setMatchColumn(String paramString)
    throws SQLException;

  public abstract void setMatchColumn(String[] paramArrayOfString)
    throws SQLException;

  public abstract void unsetMatchColumn(int paramInt)
    throws SQLException;

  public abstract void unsetMatchColumn(int[] paramArrayOfInt)
    throws SQLException;

  public abstract void unsetMatchColumn(String paramString)
    throws SQLException;

  public abstract void unsetMatchColumn(String[] paramArrayOfString)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleJoinable
 * JD-Core Version:    0.6.0
 */