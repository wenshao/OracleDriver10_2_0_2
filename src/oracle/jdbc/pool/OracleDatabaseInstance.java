package oracle.jdbc.pool;

class OracleDatabaseInstance {
	/* 35 */String databaseUniqName = null;
	/* 36 */String instanceName = null;
	/* 37 */int percent = 0;
	/* 38 */int flag = 0;
	/* 39 */int attemptedConnRequestCount = 0;
	/* 40 */int numberOfConnectionsCount = 0;

	/* 51 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	OracleDatabaseInstance(String databaseUniqName, String instanceName) {
		/* 45 */this.databaseUniqName = databaseUniqName;
		/* 46 */this.instanceName = instanceName;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.pool.OracleDatabaseInstance JD-Core Version: 0.6.0
 */