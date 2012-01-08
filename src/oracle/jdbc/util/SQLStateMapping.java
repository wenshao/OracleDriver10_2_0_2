/*    */package oracle.jdbc.util;

/*    */
/*    */public class SQLStateMapping
/*    */{
	/*    */public int err;
	/*    */public String sqlState;
	/* 36 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*    */public static final boolean TRACE = false;
	/*    */public static final boolean PRIVATE_TRACE = false;
	/*    */public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	/*    */
	/*    */public SQLStateMapping(int paramInt, String paramString)
	/*    */{
		/* 30 */this.err = paramInt;
		/* 31 */this.sqlState = paramString;
		/*    */}
	/*    */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.util.SQLStateMapping
 * JD-Core Version: 0.6.0
 */