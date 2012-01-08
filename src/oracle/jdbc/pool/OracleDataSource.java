package oracle.jdbc.pool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleLog;

public class OracleDataSource implements DataSource, Serializable, Referenceable {
	/* 50 */protected PrintWriter logWriter = null;
	/* 51 */protected int loginTimeout = 0;

	/* 54 */protected String databaseName = null;
	/* 55 */protected String serviceName = null;
	/* 56 */protected String dataSourceName = "OracleDataSource";
	/* 57 */protected String description = null;
	/* 58 */protected String networkProtocol = "tcp";
	/* 59 */protected int portNumber = 0;
	/* 60 */protected String user = null;
	/* 61 */protected String password = null;
	/* 62 */protected String serverName = null;
	/* 63 */protected String url = null;
	/* 64 */protected String driverType = null;
	/* 65 */protected String tnsEntry = null;
	/* 66 */protected int maxStatements = 0;
	/* 67 */protected boolean implicitCachingEnabled = false;
	/* 68 */protected boolean explicitCachingEnabled = false;

	/* 70 */protected transient OracleImplicitConnectionCache odsCache = null;
	/* 71 */protected transient OracleConnectionCacheManager cacheManager = null;
	/* 72 */protected String connCacheName = null;
	/* 73 */protected Properties connCacheProperties = null;
	/* 74 */protected Properties connectionProperties = null;
	/* 75 */protected boolean connCachingEnabled = false;
	/* 76 */protected boolean fastConnFailover = false;
	/* 77 */protected String onsConfigStr = null;
	/* 78 */public boolean isOracleDataSource = true;

	/* 97 */private static final boolean fastConnectionFailoverSysProperty = "true".equalsIgnoreCase(OracleDriver.getSystemPropertyFastConnectionFailover("false"));

	/* 101 */private boolean urlExplicit = false;
	/* 102 */private boolean useDefaultConnection = false;
	/* 103 */protected transient OracleDriver driver = new OracleDriver();
	private static final String spawnNewThreadToCancelProperty = "oracle.jdbc.spawnNewThreadToCancel";
	/* 1972 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	public OracleDataSource() throws SQLException {
		/* 111 */processFastConnectionFailoverSysProperty();
	}

	void processFastConnectionFailoverSysProperty() {
		/* 118 */if ((this.isOracleDataSource) && (fastConnectionFailoverSysProperty)) {
			/* 122 */this.connCachingEnabled = true;

			/* 125 */if (this.cacheManager == null) {
				try {
					/* 129 */this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
				} catch (SQLException localSQLException) {
				}

			}

			/* 141 */this.fastConnFailover = true;
			/* 142 */setSpawnNewThreadToCancel(true);
		}
	}

	public Connection getConnection() throws SQLException {
		/* 157 */String str1 = null;
		/* 158 */String str2 = null;
		/* 159 */synchronized (this) {
			/* 161 */str1 = this.user;
			/* 162 */str2 = this.password;
		}

		/* 165 */return getConnection(str1, str2);
	}

	public Connection getConnection(String user, String password) throws SQLException {
		/* 184 */Connection localConnection = null;
		/* 185 */Properties localProperties = null;
		/* 186 */if (this.connCachingEnabled) {
			/* 189 */localConnection = getConnection(user, password, null);
		} else {
			/* 193 */synchronized (this) {
				/* 195 */makeURL();

				/* 200 */localProperties = this.connectionProperties == null ? new Properties() : (Properties) this.connectionProperties.clone();

				/* 208 */if (this.url != null)
					/* 209 */localProperties.setProperty("connection_url", this.url);
				/* 210 */if (user != null)
					/* 211 */localProperties.setProperty("user", user);
				/* 212 */if (password != null)
					/* 213 */localProperties.setProperty("password", password);
				/* 214 */if (this.loginTimeout != 0) {
					/* 215 */localProperties.setProperty("LoginTimeout", "" + this.loginTimeout);
				}
				/* 217 */if (this.maxStatements != 0) {
					/* 218 */localProperties.setProperty("stmt_cache_size", "" + this.maxStatements);
				}
			}
			/* 221 */localConnection = getPhysicalConnection(localProperties);
			/* 222 */if (localConnection == null) {
				/* 223 */DatabaseError.throwSqlException(67);
			}

		}

		/* 229 */return localConnection;
	}

	protected Connection getPhysicalConnection(Properties paramProperties) throws SQLException {
		/* 238 */Connection localConnection = null;
		/* 239 */Properties localProperties = paramProperties;
		/* 240 */String str1 = paramProperties.getProperty("connection_url");
		/* 241 */String str2 = paramProperties.getProperty("user");
		/* 242 */String str3 = localProperties.getProperty("password");
		/* 243 */String str4 = null;
		/* 244 */boolean bool1 = false;

		/* 250 */synchronized (this) {
			/* 256 */if (this.connectionProperties != null) {
				/* 258 */localProperties = (Properties) this.connectionProperties.clone();

				/* 260 */if (str2 != null) {
					/* 261 */localProperties.put("user", str2);
				}
				/* 263 */if (str3 != null)
					/* 264 */localProperties.put("password", str3);
			}
			if ((str2 == null) && (this.user != null))
				localProperties.put("user", this.user);

			if ((str3 == null) && (this.password != null)) {
				localProperties.put("password", this.password);
			}
			/* 271 */if (str1 == null) {
				/* 272 */str1 = this.url;
			}
			/* 274 */String str5 = paramProperties.getProperty("LoginTimeout");

			/* 276 */if (str5 != null) {
				/* 277 */localProperties.put("oracle.net.CONNECT_TIMEOUT", "" + Integer.parseInt(str5) * 1000);
			}

			bool1 = this.useDefaultConnection;

			if (this.driver == null) {
				this.driver = new OracleDriver();
			}

		}

		/* 291 */if (bool1) {
			/* 293 */localConnection = this.driver.defaultConnection();
		} else {
			/* 297 */localConnection = this.driver.connect(str1, localProperties);
		}

		/* 300 */if (localConnection == null) {
			/* 301 */DatabaseError.throwSqlException(67);
		}

		/* 305 */str4 = paramProperties.getProperty("stmt_cache_size");

		/* 307 */int i = 0;
		/* 308 */if (str4 != null) {
			/* 309 */((OracleConnection) localConnection).setStatementCacheSize(i = Integer.parseInt(str4));
		}

		/* 312 */boolean explicitStatementCachingEnabled = false;
		/* 313 */str4 = paramProperties.getProperty("ExplicitStatementCachingEnabled");

		/* 315 */if (str4 != null) {
			/* 316 */((OracleConnection) localConnection).setExplicitCachingEnabled(explicitStatementCachingEnabled = str4.equals("true"));
		}

		/* 319 */boolean implicitStatementCachingEnabled = false;
		/* 320 */str4 = paramProperties.getProperty("ImplicitStatementCachingEnabled");

		/* 322 */if (str4 != null) {
			/* 324 */((OracleConnection) localConnection).setImplicitCachingEnabled(implicitStatementCachingEnabled = str4.equals("true"));
		}

		/* 329 */if ((i > 0) && (!explicitStatementCachingEnabled) && (!implicitStatementCachingEnabled)) {
			/* 333 */((OracleConnection) localConnection).setImplicitCachingEnabled(true);
			/* 334 */((OracleConnection) localConnection).setExplicitCachingEnabled(true);
		}

		/* 340 */return localConnection;
	}

	public Connection getConnection(Properties paramProperties) throws SQLException {
		String str1 = null;
		String str2 = null;
		synchronized (this) {
			if (!this.connCachingEnabled) {
				DatabaseError.throwSqlException(137);
			}
			str1 = this.user;
			str2 = this.password;
		}

		/* 369 */Connection conn = getConnection(str1, str2, paramProperties);

		/* 374 */return (Connection) conn;
	}

	public Connection getConnection(String paramString1, String paramString2, Properties paramProperties) throws SQLException {
		/* 392 */if (!this.connCachingEnabled) {
			/* 394 */DatabaseError.throwSqlException(137);
		}

		/* 401 */if (this.odsCache == null) {
			/* 402 */cacheInitialize();
		}
		/* 404 */Connection localConnection = this.odsCache.getConnection(paramString1, paramString2, paramProperties);

		/* 409 */return localConnection;
	}

	private synchronized void cacheInitialize() throws SQLException {
		/* 422 */if (this.odsCache == null) {
			/* 424 */if (this.connCacheName != null)
				/* 425 */this.cacheManager.createCache(this.connCacheName, this, this.connCacheProperties);
			else
				/* 427 */this.connCacheName = this.cacheManager.createCache(this, this.connCacheProperties);
		}
	}

	public synchronized void close() throws SQLException {
		/* 445 */if ((this.connCachingEnabled) && (this.odsCache != null)) {
			/* 447 */this.cacheManager.removeCache(this.odsCache.cacheName, 0L);

			/* 449 */this.odsCache = null;
		}
	}

	public synchronized void setConnectionCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 467 */if (this.isOracleDataSource) {
			/* 469 */if (paramBoolean) {
				/* 471 */this.connCachingEnabled = true;

				/* 473 */if (this.cacheManager == null) {
					/* 475 */this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
				}

			}
			/* 484 */else if (this.odsCache == null) {
				/* 486 */this.connCachingEnabled = false;
				/* 487 */this.fastConnFailover = false;
				/* 488 */setSpawnNewThreadToCancel(false);
				/* 489 */this.connCacheName = null;
				/* 490 */this.connCacheProperties = null;
			}

		} else {
			/* 504 */DatabaseError.throwSqlException(137);
		}
	}

	public boolean getConnectionCachingEnabled() throws SQLException {
		/* 523 */return this.connCachingEnabled;
	}

	public synchronized void setConnectionCacheName(String paramString) throws SQLException {
		/* 538 */if (this.connCachingEnabled) {
			/* 540 */if (paramString == null) {
				/* 542 */DatabaseError.throwSqlException(138);
			} else {
				/* 546 */this.connCacheName = paramString;
			}
		}
	}

	public String getConnectionCacheName() throws SQLException {
		/* 566 */if ((this.connCachingEnabled) && (this.odsCache != null)) {
			/* 567 */return this.odsCache.cacheName;
		}
		/* 569 */return this.connCacheName;
	}

	public synchronized void setConnectionCacheProperties(Properties paramProperties) throws SQLException {
		/* 649 */if (this.connCachingEnabled)
			/* 650 */this.connCacheProperties = paramProperties;
	}

	public Properties getConnectionCacheProperties() throws SQLException {
		/* 667 */if ((this.connCachingEnabled) && (this.odsCache != null)) {
			/* 668 */return this.odsCache.getConnectionCacheProperties();
		}
		/* 670 */return this.connCacheProperties;
	}

	public synchronized void setFastConnectionFailoverEnabled(boolean paramBoolean) throws SQLException {
		/* 695 */if ((this.connCachingEnabled) && (!this.fastConnFailover)) {
			/* 697 */this.fastConnFailover = paramBoolean;
			/* 698 */setSpawnNewThreadToCancel(paramBoolean);
		} else {
			/* 706 */DatabaseError.throwSqlException(137);
		}
	}

	public boolean getFastConnectionFailoverEnabled() throws SQLException {
		/* 724 */return this.fastConnFailover;
	}

	public String getONSConfiguration() throws SQLException {
		/* 740 */return this.onsConfigStr;
	}

	public synchronized void setONSConfiguration(String paramString) throws SQLException {
		/* 771 */this.onsConfigStr = paramString;
	}

	public synchronized int getLoginTimeout() {
		/* 792 */return this.loginTimeout;
	}

	public synchronized void setLoginTimeout(int paramInt) {
		/* 812 */this.loginTimeout = paramInt;
	}

	public synchronized void setLogWriter(PrintWriter paramPrintWriter) {
		/* 838 */this.logWriter = paramPrintWriter;

		/* 840 */OracleLog.setLogWriter(paramPrintWriter);
	}

	public synchronized PrintWriter getLogWriter() {
		/* 865 */return this.logWriter;
	}

	public synchronized void setTNSEntryName(String paramString) {
		/* 882 */this.tnsEntry = paramString;
	}

	public synchronized String getTNSEntryName() {
		/* 898 */return this.tnsEntry;
	}

	public synchronized void setDataSourceName(String paramString) {
		/* 915 */this.dataSourceName = paramString;
	}

	public synchronized String getDataSourceName() {
		/* 931 */return this.dataSourceName;
	}

	public synchronized String getDatabaseName() {
		/* 945 */return this.databaseName;
	}

	public synchronized void setDatabaseName(String paramString) {
		/* 960 */this.databaseName = paramString;
	}

	public synchronized void setServiceName(String paramString) {
		/* 979 */this.serviceName = paramString;
	}

	public synchronized String getServiceName() {
		/* 998 */return this.serviceName;
	}

	public synchronized void setServerName(String paramString) {
		/* 1015 */this.serverName = paramString;
	}

	public synchronized String getServerName() {
		/* 1029 */return this.serverName;
	}

	public synchronized void setURL(String paramString) {
		/* 1045 */this.url = paramString;

		/* 1047 */if (this.url != null)
			/* 1048 */this.urlExplicit = true;
	}

	public synchronized String getURL() throws SQLException {
		/* 1063 */if (!this.urlExplicit) {
			/* 1064 */makeURL();
		}

		/* 1069 */return this.url;
	}

	public synchronized void setUser(String paramString) {
		/* 1083 */this.user = paramString;
	}

	public synchronized String getUser() {
		/* 1096 */return this.user;
	}

	public synchronized void setPassword(String paramString) {
		/* 1111 */this.password = paramString;
	}

	protected synchronized String getPassword() {
		/* 1120 */return this.password;
	}

	public synchronized String getDescription() {
		/* 1134 */return this.description;
	}

	public synchronized void setDescription(String paramString) {
		/* 1148 */this.description = paramString;
	}

	public synchronized String getDriverType() {
		/* 1163 */return this.driverType;
	}

	public synchronized void setDriverType(String paramString) {
		/* 1185 */this.driverType = paramString;
	}

	public synchronized String getNetworkProtocol() {
		/* 1200 */return this.networkProtocol;
	}

	public synchronized void setNetworkProtocol(String paramString) {
		/* 1216 */this.networkProtocol = paramString;
	}

	public synchronized void setPortNumber(int paramInt) {
		/* 1231 */this.portNumber = paramInt;
	}

	public synchronized int getPortNumber() {
		/* 1245 */return this.portNumber;
	}

	public synchronized Reference getReference() throws NamingException {
		/* 1257 */Reference localReference = new Reference(getClass().getName(), "oracle.jdbc.pool.OracleDataSourceFactory", null);

		/* 1261 */addRefProperties(localReference);

		/* 1266 */return localReference;
	}

	protected void addRefProperties(Reference paramReference) {
		/* 1276 */if (this.url != null) {
			/* 1277 */paramReference.add(new StringRefAddr("url", this.url));
		}
		/* 1279 */if (this.user != null) {
			/* 1280 */paramReference.add(new StringRefAddr("userName", this.user));
		}
		/* 1282 */if (this.password != null) {
			/* 1283 */paramReference.add(new StringRefAddr("passWord", this.password));
		}
		/* 1285 */if (this.description != null) {
			/* 1286 */paramReference.add(new StringRefAddr("description", this.description));
		}
		/* 1288 */if (this.driverType != null) {
			/* 1289 */paramReference.add(new StringRefAddr("driverType", this.driverType));
		}
		/* 1291 */if (this.serverName != null) {
			/* 1292 */paramReference.add(new StringRefAddr("serverName", this.serverName));
		}
		/* 1294 */if (this.databaseName != null) {
			/* 1295 */paramReference.add(new StringRefAddr("databaseName", this.databaseName));
		}
		/* 1297 */if (this.serviceName != null) {
			/* 1298 */paramReference.add(new StringRefAddr("serviceName", this.serviceName));
		}
		/* 1300 */if (this.networkProtocol != null) {
			/* 1301 */paramReference.add(new StringRefAddr("networkProtocol", this.networkProtocol));
		}
		/* 1303 */if (this.portNumber != 0) {
			/* 1304 */paramReference.add(new StringRefAddr("portNumber", Integer.toString(this.portNumber)));
		}
		/* 1306 */if (this.tnsEntry != null) {
			/* 1307 */paramReference.add(new StringRefAddr("tnsentryname", this.tnsEntry));
		}
		/* 1309 */if (this.maxStatements != 0) {
			/* 1310 */paramReference.add(new StringRefAddr("maxStatements", Integer.toString(this.maxStatements)));
		}

		/* 1313 */if (this.implicitCachingEnabled) {
			/* 1314 */paramReference.add(new StringRefAddr("implicitCachingEnabled", "true"));
		}
		/* 1316 */if (this.explicitCachingEnabled) {
			/* 1317 */paramReference.add(new StringRefAddr("explicitCachingEnabled", "true"));
		}

		/* 1321 */if (this.connCachingEnabled) {
			/* 1322 */paramReference.add(new StringRefAddr("connectionCachingEnabled", "true"));
		}
		/* 1324 */if (this.connCacheName != null) {
			/* 1325 */paramReference.add(new StringRefAddr("connectionCacheName", this.connCacheName));
		}
		/* 1327 */if (this.connCacheProperties != null) {
			/* 1328 */paramReference.add(new StringRefAddr("connectionCacheProperties", this.connCacheProperties.toString()));
		}

		/* 1331 */if (this.fastConnFailover) {
			/* 1332 */paramReference.add(new StringRefAddr("fastConnectionFailoverEnabled", "true"));
		}
		/* 1334 */if (this.onsConfigStr != null)
			/* 1335 */paramReference.add(new StringRefAddr("onsConfigStr", this.onsConfigStr));
	}

	void makeURL() throws SQLException {
		/* 1345 */if (this.urlExplicit) {
			/* 1346 */return;
		}

		/* 1350 */if ((this.driverType == null)
				|| ((!this.driverType.equals("oci8")) && (!this.driverType.equals("oci")) && (!this.driverType.equals("thin")) && (!this.driverType.equals("kprb")))) {
			/* 1353 */DatabaseError.throwSqlException(67, "OracleDataSource.makeURL");
		}

		/* 1358 */if (this.driverType.equals("kprb")) {
			/* 1360 */this.useDefaultConnection = true;
			/* 1361 */this.url = "jdbc:oracle:kprb:@";

			/* 1366 */return;
		}

		/* 1371 */if (((this.driverType.equals("oci8")) || (this.driverType.equals("oci"))) && (this.networkProtocol != null) && (this.networkProtocol.equals("ipc"))) {
			/* 1374 */this.url = "jdbc:oracle:oci:@";

			/* 1379 */return;
		}

		/* 1383 */if (this.tnsEntry != null) {
			/* 1385 */this.url = ("jdbc:oracle:" + this.driverType + ":@" + this.tnsEntry);

			/* 1390 */return;
		}

		/* 1394 */if (this.serviceName != null) {
			/* 1396 */this.url = ("jdbc:oracle:" + this.driverType + ":@(DESCRIPTION=(ADDRESS=(PROTOCOL=" + this.networkProtocol + ")(PORT=" + this.portNumber + ")(HOST="
					+ this.serverName + "))(CONNECT_DATA=(SERVICE_NAME=" + this.serviceName + ")))");
		} else {
			/* 1402 */this.url = ("jdbc:oracle:" + this.driverType + ":@(DESCRIPTION=(ADDRESS=(PROTOCOL=" + this.networkProtocol + ")(PORT=" + this.portNumber + ")(HOST="
					+ this.serverName + "))(CONNECT_DATA=(SID=" + this.databaseName + ")))");

			/* 1409 */DatabaseError.addSqlWarning(null, new SQLWarning(
					"URL with SID jdbc:subprotocol:@host:port:sid will be deprecated in 10i\nPlease use URL with SERVICE_NAME as jdbc:subprotocol:@//host:port/service_name"));

			/* 1417 */if (this.fastConnFailover) {
				/* 1419 */DatabaseError.throwSqlException(67, "OracleDataSource.makeURL");
			}
		}
	}

	protected void trace(String paramString) {
		/* 1437 */if (this.logWriter != null) {
			/* 1443 */OracleLog.print(this, 2, 2, 32, "OracleDataSource.trace(s): logWriter is not null");
		}
	}

	protected void copy(OracleDataSource paramOracleDataSource) throws SQLException {
		/* 1459 */paramOracleDataSource.setUser(this.user);
		/* 1460 */paramOracleDataSource.setPassword(this.password);
		/* 1461 */paramOracleDataSource.setTNSEntryName(this.tnsEntry);
		/* 1462 */makeURL();
		/* 1463 */paramOracleDataSource.setURL(this.url);
		/* 1464 */paramOracleDataSource.connectionProperties = this.connectionProperties;
	}

	/** @deprecated */
	public void setMaxStatements(int paramInt) throws SQLException {
		/* 1490 */this.maxStatements = paramInt;
	}

	public int getMaxStatements() throws SQLException {
		/* 1504 */return this.maxStatements;
	}

	public void setImplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 1524 */this.implicitCachingEnabled = paramBoolean;
	}

	public boolean getImplicitCachingEnabled() throws SQLException {
		/* 1538 */return this.implicitCachingEnabled;
	}

	public void setExplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 1559 */this.explicitCachingEnabled = paramBoolean;
	}

	public boolean getExplicitCachingEnabled() throws SQLException {
		/* 1573 */return this.explicitCachingEnabled;
	}

	public void setConnectionProperties(Properties paramProperties) throws SQLException {
		/* 1874 */if (paramProperties == null)
			this.connectionProperties = paramProperties;
		else
			/* 1875 */this.connectionProperties = ((Properties) paramProperties.clone());
		/* 1876 */setSpawnNewThreadToCancel(this.fastConnFailover);
	}

	public Properties getConnectionProperties() throws SQLException {
		/* 1889 */return filterConnectionProperties(this.connectionProperties);
	}

	public static final Properties filterConnectionProperties(Properties paramProperties) {
		/* 1899 */Properties localProperties = null;

		/* 1902 */if (paramProperties != null) {
			/* 1904 */localProperties = (Properties) paramProperties.clone();
			/* 1905 */Enumeration localEnumeration = localProperties.propertyNames();
			/* 1906 */Object localObject = null;
			/* 1907 */while (localEnumeration.hasMoreElements()) {
				/* 1909 */String str = (String) localEnumeration.nextElement();

				/* 1914 */if ((str == null) || (!str.matches(".*[P,p][A,a][S,s][S,s][W,w][O,o][R,r][D,d].*")))
					continue;
				/* 1916 */localProperties.remove(str);
			}

			/* 1921 */paramProperties.remove("oracle.jdbc.spawnNewThreadToCancel");
		}

		/* 1924 */return localProperties;
	}

	private void setSpawnNewThreadToCancel(boolean paramBoolean) {
		/* 1929 */if (paramBoolean) {
			/* 1930 */if (this.connectionProperties == null)
				this.connectionProperties = new Properties();
			/* 1931 */this.connectionProperties.setProperty("oracle.jdbc.spawnNewThreadToCancel", "true");
		}
		/* 1933 */else if (this.connectionProperties != null) {
			/* 1934 */this.connectionProperties.remove("oracle.jdbc.spawnNewThreadToCancel");
		}
	}

	private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
		/* 1944 */paramObjectOutputStream.defaultWriteObject();
	}

	private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException, SQLException {
		/* 1960 */paramObjectInputStream.defaultReadObject();

		/* 1965 */if (this.connCachingEnabled)
			/* 1966 */setConnectionCachingEnabled(this.connCachingEnabled);
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.pool.OracleDataSource
 * JD-Core Version: 0.6.0
 */