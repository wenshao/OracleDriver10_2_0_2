/*    */package oracle.jdbc.driver;

/*    */
/*    */import java.sql.SQLException;

/*    */
/*    */abstract class OracleTimeout
/*    */{
	/* 74 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*    */public static final boolean TRACE = false;
	/*    */public static final boolean PRIVATE_TRACE = false;
	/*    */public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";

	/*    */
	/*    */static OracleTimeout newTimeout(String paramString)
	/*    */throws SQLException
	/*    */{
		/* 38 */OracleTimeoutThreadPerVM localOracleTimeoutThreadPerVM = new OracleTimeoutThreadPerVM(
				paramString);
		/*    */
		/* 40 */return localOracleTimeoutThreadPerVM;
		/*    */}

	/*    */
	/*    */abstract void setTimeout(long paramLong,
			OracleStatement paramOracleStatement)
	/*    */throws SQLException;

	/*    */
	/*    */abstract void cancelTimeout()
	/*    */throws SQLException;

	/*    */
	/*    */abstract void close()
	/*    */throws SQLException;
	/*    */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.OracleTimeout
 * JD-Core Version: 0.6.0
 */