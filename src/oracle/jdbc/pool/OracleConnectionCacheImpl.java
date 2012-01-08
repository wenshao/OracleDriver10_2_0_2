package oracle.jdbc.pool;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Stack;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;

/** @deprecated */
public class OracleConnectionCacheImpl extends OracleDataSource implements OracleConnectionCache, Serializable, Referenceable {
	/* 48 */protected ConnectionPoolDataSource cpds = null;

	/* 50 */protected static int _DEFAULT_MIN_LIMIT = 0;

	/* 53 */protected static int _DEFAULT_MAX_LIMIT = 2147483647;

	/* 58 */protected int _MIN_LIMIT = _DEFAULT_MIN_LIMIT;
	/* 59 */protected int _MAX_LIMIT = _DEFAULT_MAX_LIMIT;
	protected static final int DEFAULT_CACHE_TIMEOUT = -1;
	protected static final int DEFAULT_THREAD_INTERVAL = 900;
	public static final int ORAERROR_END_OF_FILE_ON_COCHANNEL = 3113;
	public static final int ORAERROR_NOT_CONNECTED_TO_ORACLE = 3114;
	public static final int ORAERROR_INIT_SHUTDOWN_IN_PROGRESS = 1033;
	public static final int ORAERROR_ORACLE_NOT_AVAILABLE = 1034;
	public static final int ORAERROR_IMMEDIATE_SHUTDOWN_IN_PROGRESS = 1089;
	public static final int ORAERROR_SHUTDOWN_IN_PROGRESS_NO_CONN = 1090;
	public static final int ORAERROR_NET_IO_EXCEPTION = 17002;
	protected long cacheTTLTimeOut = -1L;
	protected long cacheInactivityTimeOut = -1L;
	protected long cacheFixedWaitTimeOut = -1L;
	protected long threadInterval = 900L;

	Stack cache = new Stack();
	Hashtable activeCache = new Hashtable(50);

	private Object CACHE_SIZE_LOCK = new String("");
	protected int cacheSize = 0;
	protected int activeSize = 0;
	protected int cacheScheme;
	protected long cleanupInterval = 30L;
	protected int[] fatalErrorCodes = null;
	public static final long DEFAULT_FIXED_WAIT_IDLE_TIME = 30L;
	protected long fixedWaitIdleTime = -1L;
	public static final int DYNAMIC_SCHEME = 1;
	public static final int FIXED_WAIT_SCHEME = 2;
	public static final int FIXED_RETURN_NULL_SCHEME = 3;
	protected OracleConnectionEventListener ocel = null;

	protected int stmtCacheSize = 0;
	protected boolean stmtClearMetaData = false;

	protected OracleConnectionCacheTimeOutThread timeOutThread = null;

	SQLWarning warning = null;

	private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	/** @deprecated */
	public OracleConnectionCacheImpl() throws SQLException {
		/* 142 */this(null);
	}

	/** @deprecated */
	public OracleConnectionCacheImpl(ConnectionPoolDataSource paramConnectionPoolDataSource) throws SQLException {
		/* 170 */this.cacheScheme = 1;

		/* 173 */this.cpds = paramConnectionPoolDataSource;

		/* 176 */this.ocel = new OracleConnectionEventListener(this);

		/* 179 */this.dataSourceName = "OracleConnectionCacheImpl";
		/* 180 */this.isOracleDataSource = false;
	}

	/** @deprecated */
	public synchronized void setConnectionPoolDataSource(ConnectionPoolDataSource paramConnectionPoolDataSource) throws SQLException {
		/* 213 */if (this.cacheSize > 0) {
			/* 214 */DatabaseError.throwSqlException(78);
		}
		/* 216 */this.cpds = paramConnectionPoolDataSource;
	}

	/** @deprecated */
	public Connection getConnection() throws SQLException {
		return getConnection(this.user, this.password);
	}

	/** @deprecated */
	public Connection getConnection(String user, String password) throws SQLException {
		Connection localConnection = null;

		PooledConnection localPooledConnection = getPooledConnection(user, password);

		if (localPooledConnection != null) {
			localConnection = localPooledConnection.getConnection();
		}

		if (localConnection != null) {
			((OracleConnection) localConnection).setStartTime(System.currentTimeMillis());
		}
		return localConnection;
	}

	protected PooledConnection getPooledConnection(String user, String password) throws SQLException {
		PooledConnection localPooledConnection = null;
		int i = 0;
		int j = 0;
		long l = 9223372036854775807L;
		Properties localProperties = null;

		synchronized (this) {
			if (!this.cache.empty()) {
				checkCredentials(user, password);

				localPooledConnection = removeConnectionFromCache();
			} else if ((this.cacheSize < this._MAX_LIMIT) || (this.cacheScheme == 1)) {
				String str1 = null;
				String str2 = null;

				if (this.cpds != null) {
					str1 = ((OracleConnectionPoolDataSource) this.cpds).getUser();
					str2 = ((OracleConnectionPoolDataSource) this.cpds).getPassword();
				}

				if ((this.cacheSize > 0) && (user != null) && (!user.equalsIgnoreCase(str1))) {
					/* 337 */DatabaseError.throwSqlException(79);
				}

				if ((this.cacheSize > 0) && (password != null) && (!password.equalsIgnoreCase(str2))) {
					DatabaseError.throwSqlException(79);
				}

				i = 1;
				localProperties = new Properties();
				if (this.url != null)
					localProperties.setProperty("connection_url", this.url);
				if (this.user != null)
					localProperties.setProperty("user", this.user);
				else if ((this.cpds != null) && (((OracleDataSource) this.cpds).user != null)) {
					localProperties.setProperty("user", ((OracleDataSource) this.cpds).user);
				}
				/* 361 */if (this.password != null)
					/* 362 */localProperties.setProperty("password", this.password);
				/* 363 */else if ((this.cpds != null) && (((OracleDataSource) this.cpds).password != null)) {
					/* 364 */localProperties.setProperty("password", ((OracleDataSource) this.cpds).password);
				}
				if ((this.stmtCacheSize == 0) && (this.cpds != null) && (((OracleDataSource) this.cpds).maxStatements != 0)) {
					localProperties.setProperty("stmt_cache_size", "" + ((OracleDataSource) this.cpds).maxStatements);
				} else {
					/* 374 */localProperties.setProperty("stmt_cache_size", "" + this.stmtCacheSize);

					/* 376 */localProperties.setProperty("stmt_cache_clear_metadata", "" + this.stmtClearMetaData);

					/* 378 */localProperties.setProperty("ImplicitStatementCachingEnabled", "" + this.implicitCachingEnabled);

					/* 380 */localProperties.setProperty("ExplicitStatementCachingEnabled", "" + this.explicitCachingEnabled);
				}

				if (this.loginTimeout != 0) {
					localProperties.setProperty("LoginTimeout", "" + this.loginTimeout);
				}
				synchronized (this.CACHE_SIZE_LOCK) {
					this.cacheSize += 1;
				}
				if (this.cpds == null) {
					initializeConnectionPoolDataSource();
				}

			} else if (this.cacheScheme != 3) {
				checkCredentials(user, password);

				l = System.currentTimeMillis();
				j = 1;
			}

		}

		if (i != 0) {
			try {
				localPooledConnection = getNewPoolOrXAConnection(localProperties);
			} catch (SQLException e) {
				synchronized (this.CACHE_SIZE_LOCK) {
					this.cacheSize -= 1;
				}

				/* 434 */throw e;
			}
		} else {
			/* 438 */if (j != 0) {
				/* 440 */while ((localPooledConnection == null) && (this.cache.empty())) {
					/* 445 */synchronized (this) {
						/* 453 */if ((this.cacheFixedWaitTimeOut > 0L) && (System.currentTimeMillis() - l > this.cacheFixedWaitTimeOut * 1000L)) {
							/* 461 */DatabaseError.throwSqlException(126);
						}

					}

					/* 467 */synchronized (this.cache) {
						try {
							/* 471 */this.cache.wait((this.fixedWaitIdleTime == -1L ? 30L : this.fixedWaitIdleTime) * 1000L);
						} catch (InterruptedException localInterruptedException) {
						}

						/* 484 */if (!this.cache.empty()) {
							/* 485 */localPooledConnection = removeConnectionFromCache();
						}
					}
				}

			}

			/* 492 */if ((localPooledConnection != null) && (this.stmtCacheSize > 0)) {
				/* 495 */if ((!this.explicitCachingEnabled) && (!this.implicitCachingEnabled)) {
					/* 497 */((OraclePooledConnection) localPooledConnection).setStmtCacheSize(this.stmtCacheSize);
				} else {
					/* 500 */((OraclePooledConnection) localPooledConnection).setStatementCacheSize(this.stmtCacheSize);
					/* 501 */((OraclePooledConnection) localPooledConnection).setExplicitCachingEnabled(this.explicitCachingEnabled);

					/* 503 */((OraclePooledConnection) localPooledConnection).setImplicitCachingEnabled(this.implicitCachingEnabled);
				}
			}

		}

		if (localPooledConnection != null) {
			/* 511 */if (i == 0) {
				/* 514 */((OraclePooledConnection) localPooledConnection).physicalConn.setDefaultRowPrefetch(10);
				/* 515 */((OraclePooledConnection) localPooledConnection).physicalConn.setDefaultExecuteBatch(1);
			}

			/* 519 */localPooledConnection.addConnectionEventListener(this.ocel);

			/* 522 */this.activeCache.put(localPooledConnection, localPooledConnection);

			/* 524 */synchronized (this) {
				/* 526 */this.activeSize = this.activeCache.size();
			}

		}

		return localPooledConnection;
	}

	PooledConnection getNewPoolOrXAConnection(Properties paramProperties) throws SQLException {
		PooledConnection localPooledConnection = ((OracleConnectionPoolDataSource) this.cpds).getPooledConnection(paramProperties);

		return localPooledConnection;
	}

	/** @deprecated */
	public void reusePooledConnection(PooledConnection paramPooledConnection) throws SQLException {
		/* 574 */detachSingleConnection(paramPooledConnection);

		/* 580 */if ((this.cache.size() >= this._MAX_LIMIT) && (this.cacheScheme == 1)) {
			/* 582 */closeSingleConnection(paramPooledConnection, false);
		}
		/* 584 */else
			putConnectionToCache(paramPooledConnection);
	}

	/** @deprecated */
	public void closePooledConnection(PooledConnection paramPooledConnection) throws SQLException {
		/* 606 */detachSingleConnection(paramPooledConnection);

		/* 609 */closeSingleConnection(paramPooledConnection, false);
	}

	private void detachSingleConnection(PooledConnection paramPooledConnection) {
		/* 622 */paramPooledConnection.removeConnectionEventListener(this.ocel);

		/* 625 */this.activeCache.remove(paramPooledConnection);

		/* 628 */this.activeSize = this.activeCache.size();
	}

	/** @deprecated */
	public void closeSingleConnection(PooledConnection paramPooledConnection) throws SQLException {
		/* 645 */closeSingleConnection(paramPooledConnection, true);
	}

	final void closeSingleConnection(PooledConnection paramPooledConnection, boolean paramBoolean) throws SQLException {
		/* 655 */if ((!removeConnectionFromCache(paramPooledConnection)) && (paramBoolean)) {
			/* 656 */return;
		}

		try {
			/* 662 */paramPooledConnection.close();
		} catch (SQLException localSQLException) {
			/* 668 */this.warning = DatabaseError.addSqlWarning(this.warning, new SQLWarning(localSQLException.getMessage()));
		}

		/* 672 */synchronized (this.CACHE_SIZE_LOCK) {
			/* 676 */this.cacheSize -= 1;
		}
	}

	/** @deprecated */
	public synchronized void close() throws SQLException {
		/* 702 */closeConnections();

		/* 709 */this.cache = null;
		/* 710 */this.activeCache = null;
		/* 711 */this.ocel = null;
		/* 712 */this.cpds = null;
		/* 713 */this.timeOutThread = null;

		/* 715 */clearWarnings();
	}

	public void closeConnections() {
		/* 732 */Enumeration localEnumeration = this.activeCache.keys();

		/* 734 */while (localEnumeration.hasMoreElements()) {
			try {
				/* 738 */OraclePooledConnection localOraclePooledConnection2 = (OraclePooledConnection) localEnumeration.nextElement();
				/* 739 */OraclePooledConnection localOraclePooledConnection1 = (OraclePooledConnection) this.activeCache.get(localOraclePooledConnection2);

				/* 741 */if (localOraclePooledConnection1 == null) {
					continue;
				}
				/* 744 */OracleConnection localOracleConnection = (OracleConnection) localOraclePooledConnection1.getLogicalHandle();

				/* 748 */localOracleConnection.close();
			} catch (Exception localException) {
			}

		}

		/* 760 */while (!this.cache.empty()) {
			try {
				/* 764 */closeSingleConnection((PooledConnection) this.cache.peek(), false);
			} catch (SQLException localSQLException) {
			}
		}
	}

	public synchronized void setConnectionCleanupInterval(long paramLong) throws SQLException {
		/* 795 */if (paramLong > 0L)
			/* 796 */this.cleanupInterval = paramLong;
	}

	public long getConnectionCleanupInterval() throws SQLException {
		/* 812 */return this.cleanupInterval;
	}

	public synchronized void setConnectionErrorCodes(int[] paramArrayOfInt) throws SQLException {
		/* 828 */if (paramArrayOfInt != null)
			/* 829 */paramArrayOfInt = paramArrayOfInt;
	}

	public int[] getConnectionErrorCodes() throws SQLException {
		/* 845 */return this.fatalErrorCodes;
	}

	public boolean isFatalConnectionError(SQLException paramSQLException) {
		if (this.cleanupInterval < 0L) {
			return false;
		}
		int i = 0;
		int j = paramSQLException.getErrorCode();

		if ((j == 3113) || (j == 3114) || (j == 1033) || (j == 1034) || (j == 1089) || (j == 1090) || (j == 17002)) {
			i = 1;
		} else if (this.fatalErrorCodes != null) {
			for (int k = 0; k < this.fatalErrorCodes.length; k++) {
				if (j == this.fatalErrorCodes[k]) {
					i = 1;
				}
			}
		}
		return i != 0;
	}

	/** @deprecated */
	public synchronized void setMinLimit(int paramInt) throws SQLException {
		/* 907 */if ((paramInt < 0) || (paramInt > this._MAX_LIMIT)) {
			/* 908 */DatabaseError.throwSqlException(68);
		}
		/* 910 */this._MIN_LIMIT = paramInt;
		/* 911 */if (this.cpds == null) {
			/* 913 */initializeConnectionPoolDataSource();
		}

		/* 916 */if (this.cacheSize < this._MIN_LIMIT) {
			/* 918 */Properties localProperties = new Properties();
			/* 919 */if (this.url != null)
				/* 920 */localProperties.setProperty("connection_url", this.url);
			/* 921 */if (this.user != null)
				/* 922 */localProperties.setProperty("user", this.user);
			/* 923 */if (this.password != null) {
				/* 924 */localProperties.setProperty("password", this.password);
			}
			/* 926 */if ((this.stmtCacheSize == 0) && (this.maxStatements != 0)) {
				/* 928 */localProperties.setProperty("stmt_cache_size", "" + this.maxStatements);
			} else {
				/* 933 */localProperties.setProperty("stmt_cache_size", "" + this.stmtCacheSize);
				/* 934 */localProperties.setProperty("stmt_cache_clear_metadata", "" + this.stmtClearMetaData);

				/* 936 */localProperties.setProperty("ImplicitStatementCachingEnabled", "" + this.implicitCachingEnabled);

				/* 938 */localProperties.setProperty("ExplicitStatementCachingEnabled", "" + this.explicitCachingEnabled);
			}

			/* 941 */localProperties.setProperty("LoginTimeout", "" + this.loginTimeout);

			/* 944 */for (int i = this.cacheSize; i < this._MIN_LIMIT; i++) {
				/* 946 */PooledConnection localPooledConnection = getNewPoolOrXAConnection(localProperties);
				/* 947 */putConnectionToCache(localPooledConnection);
			}

			/* 950 */this.cacheSize = this._MIN_LIMIT;
		}
	}

	void initializeConnectionPoolDataSource() throws SQLException {
		/* 962 */if (this.cpds == null) {
			/* 965 */if ((this.user == null) || (this.password == null)) {
				/* 966 */DatabaseError.throwSqlException(79);
			}
			/* 968 */this.cpds = new OracleConnectionPoolDataSource();

			/* 970 */copy((OracleDataSource) this.cpds);
		}
	}

	/** @deprecated */
	public synchronized int getMinLimit() {
		/* 986 */return this._MIN_LIMIT;
	}

	/** @deprecated */
	public synchronized void setMaxLimit(int paramInt) throws SQLException {
		/* 1002 */if ((paramInt < 0) || (paramInt < this._MIN_LIMIT)) {
			/* 1003 */DatabaseError.throwSqlException(68);
		}
		/* 1005 */this._MAX_LIMIT = paramInt;

		/* 1007 */if ((this.cacheSize > this._MAX_LIMIT) && (this.cacheScheme != 1)) {
			/* 1009 */for (int i = this._MAX_LIMIT; i < this.cacheSize; i++) {
				/* 1011 */if (this.cache.empty())
					/* 1012 */DatabaseError.throwSqlException(78);
				else {
					/* 1014 */removeConnectionFromCache().close();
				}
			}
			/* 1017 */this.cacheSize = this._MAX_LIMIT;
		}
	}

	/** @deprecated */
	public synchronized int getMaxLimit() {
		/* 1038 */return this._MAX_LIMIT;
	}

	/** @deprecated */
	public synchronized int getCacheScheme() {
		/* 1054 */return this.cacheScheme;
	}

	/** @deprecated */
	public synchronized void setCacheScheme(int paramInt) throws SQLException {
		/* 1073 */if ((paramInt == 1) || (paramInt == 3) || (paramInt == 2)) {
			/* 1076 */this.cacheScheme = paramInt;

			/* 1078 */return;
		}

		/* 1081 */DatabaseError.throwSqlException(68);
	}

	/** @deprecated */
	public synchronized void setCacheScheme(String paramString) throws SQLException {
		/* 1100 */if (paramString.equalsIgnoreCase("DYNAMIC_SCHEME"))
			/* 1101 */this.cacheScheme = 1;
		/* 1102 */else if (paramString.equalsIgnoreCase("FIXED_RETURN_NULL_SCHEME"))
			/* 1103 */this.cacheScheme = 3;
		/* 1104 */else if (paramString.equalsIgnoreCase("FIXED_WAIT_SCHEME"))
			/* 1105 */this.cacheScheme = 2;
		else
			/* 1107 */DatabaseError.throwSqlException(68);
	}

	/** @deprecated */
	public synchronized int getActiveSize() {
		return this.activeSize;
	}

	/** @deprecated */
	public synchronized int getCacheSize() {
		/* 1141 */return this.cacheSize;
	}

	/** @deprecated */
	public synchronized void setCacheTimeToLiveTimeout(long paramLong) throws SQLException {
		/* 1162 */if (this.timeOutThread == null) {
			/* 1163 */this.timeOutThread = new OracleConnectionCacheTimeOutThread(this);
		}

		/* 1167 */if (paramLong <= 0L) {
			/* 1169 */this.cacheTTLTimeOut = -1L;
			/* 1170 */this.warning = DatabaseError.addSqlWarning(this.warning, 111);
		} else {
			/* 1176 */this.cacheTTLTimeOut = paramLong;

			/* 1178 */checkAndStartTimeOutThread();
		}
	}

	/** @deprecated */
	public synchronized void setCacheInactivityTimeout(long paramLong) throws SQLException {
		/* 1200 */if (this.timeOutThread == null) {
			/* 1201 */this.timeOutThread = new OracleConnectionCacheTimeOutThread(this);
		}

		/* 1205 */if (paramLong <= 0L) {
			/* 1207 */this.cacheInactivityTimeOut = -1L;
			/* 1208 */this.warning = DatabaseError.addSqlWarning(this.warning, 124);
		} else {
			/* 1214 */this.cacheInactivityTimeOut = paramLong;

			/* 1216 */checkAndStartTimeOutThread();
		}
	}

	/** @deprecated */
	public synchronized void setCacheFixedWaitTimeout(long paramLong) throws SQLException {
		/* 1238 */if (paramLong <= 0L) {
			/* 1240 */this.cacheFixedWaitTimeOut = -1L;
			/* 1241 */this.warning = DatabaseError.addSqlWarning(this.warning, 127);
		} else {
			/* 1247 */this.cacheFixedWaitTimeOut = paramLong;
		}
	}

	/** @deprecated */
	public long getCacheTimeToLiveTimeout() throws SQLException {
		/* 1266 */return this.cacheTTLTimeOut;
	}

	/** @deprecated */
	public long getCacheInactivityTimeout() throws SQLException {
		/* 1284 */return this.cacheInactivityTimeOut;
	}

	/** @deprecated */
	public long getCacheFixedWaitTimeout() throws SQLException {
		/* 1302 */return this.cacheFixedWaitTimeOut;
	}

	/** @deprecated */
	public synchronized void setThreadWakeUpInterval(long paramLong) throws SQLException {
		/* 1323 */if (paramLong <= 0L) {
			/* 1325 */this.threadInterval = 900L;
			/* 1326 */this.warning = DatabaseError.addSqlWarning(this.warning, 112);
		} else {
			/* 1332 */this.threadInterval = paramLong;
		}

		/* 1336 */if (((this.cacheTTLTimeOut > 0L) && (this.threadInterval > this.cacheTTLTimeOut))
				|| ((this.cacheInactivityTimeOut > 0L) && (this.threadInterval > this.cacheInactivityTimeOut))) {
			/* 1340 */this.warning = DatabaseError.addSqlWarning(this.warning, 113);
		}
	}

	/** @deprecated */
	public long getThreadWakeUpInterval() throws SQLException {
		/* 1361 */return this.threadInterval;
	}

	private void checkAndStartTimeOutThread() throws SQLException {
		try {
			if (!this.timeOutThread.isAlive()) {
				this.timeOutThread.setDaemon(true);
				this.timeOutThread.start();
			}
		} catch (IllegalThreadStateException e) {
		}
	}

	/** @deprecated */
	public SQLWarning getWarnings() throws SQLException {
		/* 1402 */return this.warning;
	}

	/** @deprecated */
	public void clearWarnings() throws SQLException {
		/* 1416 */this.warning = null;
	}

	private final void checkCredentials(String pUser, String pPassword) throws SQLException {
		String user = null;
		String password = null;

		if (this.cpds != null) {
			user = ((OracleConnectionPoolDataSource) this.cpds).getUser();
			password = ((OracleConnectionPoolDataSource) this.cpds).getPassword();
		}

		if (((pUser != null) && (!pUser.equals(user))) || ((pPassword != null) && (!pPassword.equals(password)))) {
			DatabaseError.throwSqlException(79);
		}
	}

	public synchronized Reference getReference() throws NamingException {
		Reference localReference = new Reference(getClass().getName(), "oracle.jdbc.pool.OracleDataSourceFactory", null);

		super.addRefProperties(localReference);

		if (this._MIN_LIMIT != _DEFAULT_MIN_LIMIT) {
			localReference.add(new StringRefAddr("minLimit", Integer.toString(this._MIN_LIMIT)));
		}
		if (this._MAX_LIMIT != _DEFAULT_MAX_LIMIT) {
			localReference.add(new StringRefAddr("maxLimit", Integer.toString(this._MAX_LIMIT)));
		}
		if (this.cacheScheme != 1) {
			localReference.add(new StringRefAddr("cacheScheme", Integer.toString(this.cacheScheme)));
		}

		return localReference;
	}

	/** @deprecated */
	public synchronized void setStmtCacheSize(int paramInt) throws SQLException {
		/* 1506 */setStmtCacheSize(paramInt, false);
	}

	/** @deprecated */
	public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean) throws SQLException {
		/* 1535 */if (paramInt < 0) {
			/* 1536 */DatabaseError.throwSqlException(68);
		}
		/* 1538 */this.stmtCacheSize = paramInt;
		/* 1539 */this.stmtClearMetaData = paramBoolean;
	}

	/** @deprecated */
	public synchronized int getStmtCacheSize() {
		/* 1555 */return this.stmtCacheSize;
	}

	synchronized boolean isStmtCacheEnabled() {
		/* 1569 */return this.stmtCacheSize > 0;
	}

	private void putConnectionToCache(PooledConnection paramPooledConnection) throws SQLException {
		/* 1591 */((OraclePooledConnection) paramPooledConnection).setLastAccessedTime(System.currentTimeMillis());
		/* 1592 */this.cache.push(paramPooledConnection);

		/* 1595 */synchronized (this.cache) {
			/* 1597 */this.cache.notify();
		}
	}

	private PooledConnection removeConnectionFromCache() throws SQLException {
		/* 1610 */return (PooledConnection) this.cache.pop();
	}

	private boolean removeConnectionFromCache(PooledConnection paramPooledConnection) throws SQLException {
		/* 1623 */return this.cache.removeElement(paramPooledConnection);
	}

	/** @deprecated */
	public synchronized void setCacheFixedWaitIdleTime(long paramLong) throws SQLException {
		/* 1649 */if (this.cacheScheme == 2) {
			/* 1651 */if (paramLong <= 0L) {
				/* 1653 */DatabaseError.addSqlWarning(this.warning, 68);

				/* 1656 */this.fixedWaitIdleTime = 30L;
			} else {
				/* 1659 */this.fixedWaitIdleTime = paramLong;
			}
		}
		/* 1662 */else
			DatabaseError.addSqlWarning(this.warning, new SQLWarning("Caching scheme is not FIXED_WAIT_SCHEME"));
	}

	/** @deprecated */
	public long getCacheFixedWaitIdleTime() throws SQLException {
		/* 1681 */return this.fixedWaitIdleTime == -1L ? 30L : this.fixedWaitIdleTime;
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.pool.OracleConnectionCacheImpl JD-Core Version: 0.6.0
 */