/*    */package oracle.jdbc.driver;

/*    */
/*    */import java.sql.SQLException;

/*    */
/*    */class VarcharAccessor extends CharCommonAccessor
/*    */{
	/*    */VarcharAccessor(OracleStatement paramOracleStatement, int paramInt1,
			short paramShort, int paramInt2, boolean paramBoolean)
	/*    */throws SQLException
	/*    */{
		/* 18 */int i = 4000;
		/*    */
		/* 20 */if (paramOracleStatement.sqlKind == 1) {
			/* 21 */i = 32512;
			/*    */}
		/* 23 */init(paramOracleStatement, 1, 9, paramInt1, paramShort,
				paramInt2, paramBoolean, i, 2000);
		/*    */}

	/*    */
	/*    */VarcharAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort)
	/*    */throws SQLException
	/*    */{
		/* 35 */int i = 4000;
		/*    */
		/* 37 */if (paramOracleStatement.sqlKind == 1) {
			/* 38 */i = 32512;
			/*    */}
		/* 40 */init(paramOracleStatement, 1, 9, paramInt1, paramBoolean,
				paramInt2, paramInt3, paramInt4, paramInt5, paramInt6,
				paramShort, i, 2000);
		/*    */}
	/*    */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.VarcharAccessor
 * JD-Core Version: 0.6.0
 */