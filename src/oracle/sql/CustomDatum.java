package oracle.sql;

import java.sql.SQLException;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.internal.ObjectData;

public abstract interface CustomDatum extends ObjectData {
	public abstract Datum toDatum(OracleConnection paramOracleConnection)
			throws SQLException;
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CustomDatum JD-Core
 * Version: 0.6.0
 */