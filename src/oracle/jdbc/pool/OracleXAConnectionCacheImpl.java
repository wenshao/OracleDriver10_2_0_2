package oracle.jdbc.pool;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.xa.OracleXAConnection;
import oracle.jdbc.xa.OracleXADataSource;

public class OracleXAConnectionCacheImpl extends OracleConnectionCacheImpl implements XADataSource, Serializable {
	private boolean nativeXA = false;
	private static final String clientXADS = "oracle.jdbc.xa.client.OracleXADataSource";
	private static final String serverXADS = "oracle.jdbc.xa.server.OracleXADataSource";
	private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	public OracleXAConnectionCacheImpl() throws SQLException {
		/* 60 */this(null);
	}

	public OracleXAConnectionCacheImpl(ConnectionPoolDataSource paramConnectionPoolDataSource) throws SQLException {
		/* 81 */super(paramConnectionPoolDataSource);

		/* 87 */this.dataSourceName = "OracleXAConnectionCacheImpl";
	}

	void initializeConnectionPoolDataSource() throws SQLException {
		/* 93 */if (this.cpds == null) {
			/* 97 */if ((this.user == null) || (this.password == null)) {
				/* 98 */DatabaseError.throwSqlException(79);
			}
			/* 100 */String str = null;

			/* 103 */if (OracleDriver.getSystemPropertyJserverVersion() == null)
				/* 104 */str = "oracle.jdbc.xa.client.OracleXADataSource";
			else {
				/* 106 */str = "oracle.jdbc.xa.server.OracleXADataSource";
			}
			try {
				/* 110 */this.cpds = ((OracleXADataSource) Class.forName(str).newInstance());
			} catch (Exception localException) {
				/* 115 */localException.printStackTrace();
				/* 116 */DatabaseError.throwSqlException(1);
			}

			/* 120 */copy((OracleDataSource) this.cpds);

			/* 123 */((OracleXADataSource) this.cpds).setNativeXA(this.nativeXA);
		}
	}

	PooledConnection getNewPoolOrXAConnection(Properties paramProperties) throws SQLException {
		/* 136 */XAConnection localXAConnection = ((OracleXADataSource) this.cpds).getXAConnection(paramProperties);

		/* 139 */((OracleXAConnection) localXAConnection).setStmtCacheSize(this.stmtCacheSize, this.stmtClearMetaData);

		/* 145 */return localXAConnection;
	}

	public synchronized XAConnection getXAConnection() throws SQLException {
		XAConnection localXAConnection = (XAConnection) super.getPooledConnection(this.user, this.password);

		return localXAConnection;
	}

	public synchronized XAConnection getXAConnection(String paramString1, String paramString2) throws SQLException {
		XAConnection localXAConnection = (XAConnection) super.getPooledConnection(paramString1, paramString2);

		/* 192 */return localXAConnection;
	}

	public synchronized boolean getNativeXA() {
		/* 206 */return this.nativeXA;
	}

	public synchronized void setNativeXA(boolean paramBoolean) {
		/* 220 */this.nativeXA = paramBoolean;
	}

	public synchronized void closeActualConnection(PooledConnection paramPooledConnection) throws SQLException {
		/* 233 */((OracleXAConnection) paramPooledConnection).close();
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.pool.OracleXAConnectionCacheImpl JD-Core Version: 0.6.0
 */