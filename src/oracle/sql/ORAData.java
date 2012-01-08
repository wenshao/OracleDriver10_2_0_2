package oracle.sql;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.internal.ObjectData;

public abstract interface ORAData extends ObjectData
{
  public abstract Datum toDatum(Connection paramConnection)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ORAData
 * JD-Core Version:    0.6.0
 */