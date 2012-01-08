package oracle.jdbc.rowset;

import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public abstract interface OraclePredicate extends Predicate
{
  public abstract boolean evaluate(RowSet paramRowSet);

  public abstract boolean evaluate(Object paramObject, int paramInt)
    throws SQLException;

  public abstract boolean evaluate(Object paramObject, String paramString)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OraclePredicate
 * JD-Core Version:    0.6.0
 */