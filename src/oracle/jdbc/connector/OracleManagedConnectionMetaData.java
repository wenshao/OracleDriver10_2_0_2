/*     */package oracle.jdbc.connector;

/*     */
/*     */import java.sql.SQLException;
/*     */
import javax.resource.ResourceException;
/*     */
import javax.resource.spi.EISSystemException;
/*     */
import javax.resource.spi.ManagedConnectionMetaData;
/*     */
import oracle.jdbc.driver.OracleConnection;
/*     */
import oracle.jdbc.driver.OracleDatabaseMetaData;

/*     */
/*     */public class OracleManagedConnectionMetaData
/*     */implements ManagedConnectionMetaData
/*     */{
	/* 31 */private OracleManagedConnection managedConnection = null;
	/* 32 */private OracleDatabaseMetaData databaseMetaData = null;
	/*     */
	/* 223 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";

	/*     */
	/*     */OracleManagedConnectionMetaData(
			OracleManagedConnection paramOracleManagedConnection)
	/*     */throws ResourceException
	/*     */{
		/*     */try
		/*     */{
			/* 47 */this.managedConnection = paramOracleManagedConnection;
			/*     */
			/* 49 */OracleConnection localOracleConnection = (OracleConnection) paramOracleManagedConnection
					.getPhysicalConnection();
			/*     */
			/* 51 */this.databaseMetaData = ((OracleDatabaseMetaData) localOracleConnection
					.getMetaData());
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/* 55 */EISSystemException localEISSystemException = new EISSystemException(
					"Exception: " + localException.getMessage());
			/*     */
			/* 58 */localEISSystemException.setLinkedException(localException);
			/*     */
			/* 60 */throw localEISSystemException;
			/*     */}
		/*     */}

	/*     */
	/*     */public String getEISProductName()
	/*     */throws ResourceException
	/*     */{
		/*     */EISSystemException localEISSystemException;
		/*     */try
		/*     */{
			/* 83 */return this.databaseMetaData.getDatabaseProductName();
			/*     */}
		/*     */catch (SQLException localSQLException)
		/*     */{
			/* 91 */localEISSystemException = new EISSystemException(
					"SQLException: " + localSQLException.getMessage());
			/*     */
			/* 94 */localEISSystemException
					.setLinkedException(localSQLException);
			/*     */}
		/* 96 */throw localEISSystemException;
		/*     */}

	/*     */
	/*     */public String getEISProductVersion()
	/*     */throws ResourceException
	/*     */{
		/*     */EISSystemException localEISSystemException;
		/*     */try
		/*     */{
			/* 121 */return this.databaseMetaData.getDatabaseProductVersion();
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/* 128 */localEISSystemException = new EISSystemException(
					"Exception: " + localException.getMessage());
			/*     */
			/* 131 */localEISSystemException.setLinkedException(localException);
			/*     */}
		/* 133 */throw localEISSystemException;
		/*     */}

	/*     */
	/*     */public int getMaxConnections()
	/*     */throws ResourceException
	/*     */{
		/*     */EISSystemException localEISSystemException;
		/*     */try
		/*     */{
			/* 160 */return this.databaseMetaData.getMaxConnections();
			/*     */}
		/*     */catch (SQLException localSQLException)
		/*     */{
			/* 168 */localEISSystemException = new EISSystemException(
					"SQLException: " + localSQLException.getMessage());
			/*     */
			/* 171 */localEISSystemException
					.setLinkedException(localSQLException);
			/*     */}
		/* 173 */throw localEISSystemException;
		/*     */}

	/*     */
	/*     */public String getUserName()
	/*     */throws ResourceException
	/*     */{
		/*     */EISSystemException localEISSystemException;
		/*     */try
		/*     */{
			/* 200 */return this.databaseMetaData.getUserName();
			/*     */}
		/*     */catch (SQLException localSQLException)
		/*     */{
			/* 208 */localEISSystemException = new EISSystemException(
					"SQLException: " + localSQLException.getMessage());
			/*     */
			/* 211 */localEISSystemException
					.setLinkedException(localSQLException);
			/*     */}
		/* 213 */throw localEISSystemException;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.connector.OracleManagedConnectionMetaData JD-Core Version: 0.6.0
 */