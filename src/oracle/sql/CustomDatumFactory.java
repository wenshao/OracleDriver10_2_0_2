package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.internal.ObjectDataFactory;

public abstract interface CustomDatumFactory extends ObjectDataFactory {
	public abstract CustomDatum create(Datum paramDatum, int paramInt)
			throws SQLException;
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CustomDatumFactory
 * JD-Core Version: 0.6.0
 */