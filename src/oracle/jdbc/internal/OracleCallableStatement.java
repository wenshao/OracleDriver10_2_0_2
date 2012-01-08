package oracle.jdbc.internal;

import java.sql.SQLException;

public abstract interface OracleCallableStatement extends
		oracle.jdbc.OracleCallableStatement, OraclePreparedStatement {
	public abstract byte[] privateGetBytes(int paramInt) throws SQLException;
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.internal.OracleCallableStatement JD-Core Version: 0.6.0
 */