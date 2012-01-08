package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.internal.ObjectDataFactory;

public abstract interface ORADataFactory extends ObjectDataFactory
{
  public abstract ORAData create(Datum paramDatum, int paramInt)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ORADataFactory
 * JD-Core Version:    0.6.0
 */