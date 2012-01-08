package oracle.jdbc.internal;

import java.sql.SQLException;

public abstract interface OraclePreparedStatement extends
		oracle.jdbc.OraclePreparedStatement, OracleStatement {

	public void enterImplicitCache() throws SQLException;

	public void exitImplicitCacheToActive() throws SQLException;

	public void exitImplicitCacheToClose() throws SQLException;

	public abstract void enterExplicitCache() throws SQLException;

	public abstract void exitExplicitCacheToActive() throws SQLException;

	public abstract void exitExplicitCacheToClose() throws SQLException;
}
