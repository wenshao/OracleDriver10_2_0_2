package oracle.jdbc.internal;

import java.sql.SQLException;

public abstract interface OracleResultSet extends oracle.jdbc.OracleResultSet {
	public abstract void closeStatementOnClose() throws SQLException;
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.internal.OracleResultSet JD-Core Version: 0.6.0
 */