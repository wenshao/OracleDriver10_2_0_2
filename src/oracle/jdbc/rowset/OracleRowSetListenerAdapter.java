package oracle.jdbc.rowset;

import java.io.Serializable;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public abstract class OracleRowSetListenerAdapter
  implements RowSetListener, Serializable
{
  public void cursorMoved(RowSetEvent paramRowSetEvent)
  {
  }

  public void rowChanged(RowSetEvent paramRowSetEvent)
  {
  }

  public void rowSetChanged(RowSetEvent paramRowSetEvent)
  {
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleRowSetListenerAdapter
 * JD-Core Version:    0.6.0
 */