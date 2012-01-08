package oracle.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import oracle.jdbc.internal.OracleConnection;

public class OracleConnectionPoolDataSource extends OracleDataSource implements ConnectionPoolDataSource {
	/* 175 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	public OracleConnectionPoolDataSource() throws SQLException {
		/* 46 */this.dataSourceName = "OracleConnectionPoolDataSource";
		/* 47 */this.isOracleDataSource = false;

		/* 50 */this.connCachingEnabled = false;

		/* 53 */this.fastConnFailover = false;
	}

	public PooledConnection getPooledConnection() throws SQLException {
		String str1 = null;
		String str2 = null;
		synchronized (this) {
			str1 = this.user;
			str2 = this.password;
		}
		return getPooledConnection(str1, str2);
	}

	public PooledConnection getPooledConnection(String paramString1, String paramString2) throws SQLException {
		/* 94 */Connection localConnection = getPhysicalConnection(this.url, paramString1, paramString2);
		/* 95 */OraclePooledConnection localOraclePooledConnection = new OraclePooledConnection(localConnection);

		/* 98 */if (paramString2 == null)
			/* 99 */paramString2 = this.password;
		/* 100 */localOraclePooledConnection.setUserName(!paramString1.startsWith("\"") ? paramString1.toLowerCase() : paramString1, paramString2.toUpperCase());

		/* 106 */return localOraclePooledConnection;
	}

	PooledConnection getPooledConnection(Properties paramProperties) throws SQLException {
		/* 115 */Connection localConnection = getPhysicalConnection(paramProperties);
		/* 116 */OraclePooledConnection localOraclePooledConnection = new OraclePooledConnection(localConnection);

		/* 118 */String str1 = paramProperties.getProperty("user");
		/* 119 */if (str1 == null)
			/* 120 */str1 = ((OracleConnection) localConnection).getUserName();
		/* 121 */String str2 = paramProperties.getProperty("password");
		/* 122 */if (str2 == null)
			/* 123 */str2 = this.password;
		/* 124 */localOraclePooledConnection.setUserName(!str1.startsWith("\"") ? str1.toLowerCase() : str1, str2.toUpperCase());

		/* 130 */return localOraclePooledConnection;
	}

	protected Connection getPhysicalConnection() throws SQLException {
		/* 142 */return super.getConnection(this.user, this.password);
	}

	protected Connection getPhysicalConnection(String paramString1, String paramString2, String paramString3) throws SQLException {
		/* 155 */this.url = paramString1;

		/* 157 */return super.getConnection(paramString2, paramString3);
	}

	protected Connection getPhysicalConnection(String paramString1, String paramString2) throws SQLException {
		/* 170 */return super.getConnection(paramString1, paramString2);
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.pool.OracleConnectionPoolDataSource JD-Core Version: 0.6.0
 */