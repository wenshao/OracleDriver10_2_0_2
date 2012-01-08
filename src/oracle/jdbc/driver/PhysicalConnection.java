package oracle.jdbc.driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import javax.transaction.xa.XAResource;
import oracle.jdbc.OracleOCIFailover;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCLOB;
import oracle.jdbc.oracore.Util;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.CharacterSet;
import oracle.sql.ClobDBAccess;
import oracle.sql.CustomDatum;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;
import oracle.sql.TypeDescriptor;

abstract class PhysicalConnection extends OracleConnection {
	/* 54 */char[][] charOutput = new char[1][];
	/* 55 */byte[][] byteOutput = new byte[1][];
	/* 56 */short[][] shortOutput = new short[1][];
	String url;
	String user;
	String savedUser;
	String database;
	boolean autoCommitSet;
	String protocol;
	/* 65 */int streamChunkSize = 16384;

	/* 76 */public int protocolId = -3;
	OracleTimeout timeout;
	/* 79 */boolean spawnNewThreadToCancel = false;
	DBConversion conversion;
	boolean xaWantsError;
	boolean usingXA;
	/* 102 */int txnMode = 0;
	byte[] fdo;
	Boolean bigEndian;
	OracleStatement statements;
	int lifecycle;
	static final int OPEN = 1;
	static final int CLOSING = 2;
	static final int CLOSED = 4;
	static final int ABORTED = 8;
	/* 132 */boolean clientIdSet = false;
	/* 133 */String clientId = null;
	int defaultBatch;
	int defaultRowPrefetch;
	boolean reportRemarks;
	/* 140 */boolean includeSynonyms = false;

	/* 143 */boolean restrictGetTables = false;

	/* 146 */boolean accumulateBatchResult = true;

	/* 149 */boolean j2ee13Compliant = false;
	int txnLevel;
	Map map;
	Map javaObjectMap;
	Hashtable descriptorCache;
	OracleStatement statementHoldingLine;
	/* 172 */oracle.jdbc.OracleDatabaseMetaData databaseMetaData = null;
	LogicalConnection logicalConnectionAttached;
	/* 182 */boolean isProxy = false;

	/* 194 */boolean useFetchSizeWithLongColumn = false;

	/* 197 */OracleSql sqlObj = null;

	/* 200 */SQLWarning sqlWarning = null;

	/* 203 */boolean readOnly = false;

	/* 208 */LRUStatementCache statementCache = null;

	/* 211 */boolean clearStatementMetaData = false;

	/* 217 */boolean processEscapes = true;

	/* 220 */boolean defaultAutoRefetch = true;

	/* 223 */OracleCloseCallback closeCallback = null;
	/* 224 */Object privateData = null;

	/* 227 */boolean defaultFixedString = false;

	/* 230 */boolean defaultNChar = false;

	/* 233 */Statement savepointStatement = null;

	/* 259 */static final int[] endToEndMaxLength = new int[4];

	/* 269 */boolean endToEndAnyChanged = false;
	/* 270 */final boolean[] endToEndHasChanged = new boolean[4];
	/* 271 */short endToEndECIDSequenceNumber = -32768;

	/* 277 */String[] endToEndValues = null;

	/* 279 */final boolean useDMSForEndToEnd = this.endToEndValues != null;

	/* 282 */oracle.jdbc.OracleConnection wrapper = null;

	/* 285 */Properties connectionProperties = null;

	/* 289 */boolean wellBehavedStatementReuse = false;
	int minVcsBindSize;
	int maxRawBytesSql;
	int maxRawBytesPlsql;
	int maxVcsCharsSql;
	int maxVcsNCharsSql;
	int maxVcsBytesPlsql;
	OracleDriverExtension driverExtension;
	static final String uninitializedMarker = "";
	/* 339 */String databaseProductVersion = "";
	/* 340 */short versionNumber = -1;
	boolean v8Compatible;
	/* 342 */boolean looseTimestampDateCheck = false;
	/* 343 */boolean isMemoryFreedOnEnteringCache = false;
	/* 344 */String ressourceManagerId = "0000";
	int namedTypeAccessorByteLen;
	int refTypeAccessorByteLen;
	/* 348 */boolean disableDefineColumnType = false;
	/* 349 */boolean convertNcharLiterals = true;
	CharacterSet setCHARCharSetObj;
	CharacterSet setCHARNCharSetObj;
	/* 355 */boolean plsqlCompilerWarnings = false;
	static final String DATABASE_NAME = "DATABASE_NAME";
	static final String SERVER_HOST = "SERVER_HOST";
	static final String INSTANCE_NAME = "INSTANCE_NAME";
	static final String SERVICE_NAME = "SERVICE_NAME";
	Hashtable clientData;
	/* 4487 */String sessionTimeZone = null;
	/* 4488 */Calendar dbTzCalendar = null;
	private static final String _Copyright_2004_Oracle_All_Rights_Reserved_;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";

	PhysicalConnection() {
	}

	PhysicalConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties,
			OracleDriverExtension paramOracleDriverExtension) throws SQLException {
		/* 380 */this.driverExtension = paramOracleDriverExtension;

		/* 382 */String str1 = null;
		String str3;
		/* 384 */if (paramProperties != null) {
			/* 386 */str1 = (String) paramProperties.get("protocol");

			/* 388 */String str2 = paramProperties.getProperty("processEscapes");

			/* 391 */if ((str2 != null) && (str2.equalsIgnoreCase("false"))) {
				/* 394 */this.processEscapes = false;
			}

			/* 397 */str3 = (String) paramProperties.get("RessourceManagerId");

			/* 399 */if (str3 != null) {
				/* 400 */this.ressourceManagerId = str3;
			}
			/* 402 */this.connectionProperties = ((Properties) paramProperties.clone());
		}

		/* 409 */initialize(paramString1, paramString2, str1, null, null, null, paramString4);
		/* 410 */initializePassword(paramString3);

		/* 412 */this.logicalConnectionAttached = null;
		try {
			/* 416 */needLine();

			/* 420 */logon();

			/* 427 */boolean bool = true;

			/* 429 */if (paramProperties != null) {
				/* 431 */str3 = paramProperties.getProperty("autoCommit");

				/* 433 */if ((str3 != null) && (str3.equalsIgnoreCase("false"))) {
					/* 435 */bool = false;
				}

				/* 441 */str3 = this.connectionProperties.getProperty("wellBehavedStatementReuse");

				/* 444 */if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
					/* 445 */this.wellBehavedStatementReuse = true;
				}
			}
			/* 448 */setAutoCommit(bool);

			/* 451 */if (getVersionNumber() >= 10000) {
				/* 453 */this.minVcsBindSize = 4001;
				/* 454 */this.maxRawBytesSql = 2000;
				/* 455 */this.maxRawBytesPlsql = 32512;
				/* 456 */this.maxVcsCharsSql = 32766;
				/* 457 */this.maxVcsNCharsSql = 32766;
				/* 458 */this.maxVcsBytesPlsql = 32512;
			}
			/* 460 */else if (getVersionNumber() >= 9200) {
				/* 462 */this.minVcsBindSize = 4001;
				/* 463 */this.maxRawBytesSql = 2000;
				/* 464 */this.maxRawBytesPlsql = 32512;
				/* 465 */this.maxVcsCharsSql = 32766;
				/* 466 */this.maxVcsNCharsSql = 32766;
				/* 467 */this.maxVcsBytesPlsql = 32512;
			} else {
				/* 471 */this.minVcsBindSize = 4001;
				/* 472 */this.maxRawBytesSql = 2000;
				/* 473 */this.maxRawBytesPlsql = 2000;
				/* 474 */this.maxVcsCharsSql = 4000;
				/* 475 */this.maxVcsNCharsSql = 4000;
				/* 476 */this.maxVcsBytesPlsql = 4000;
			}

			/* 480 */str3 = paramProperties != null ? paramProperties.getProperty("oracle.jdbc.V8Compatible") : null;

			/* 484 */if (str3 == null) {
				/* 485 */str3 = OracleDriver.getSystemPropertyV8Compatible();
			}

			/* 488 */if (str3 != null) {
				/* 489 */this.v8Compatible = str3.equalsIgnoreCase("true");
			}
			/* 491 */String str4 = paramProperties != null ? paramProperties.getProperty("oracle.jdbc.StreamChunkSize") : null;

			/* 499 */if (str4 != null) {
				/* 500 */this.streamChunkSize = Math.max(4096, Integer.parseInt(str4));
			}

			/* 505 */str4 = paramProperties != null ? paramProperties.getProperty("oracle.jdbc.internal.permitBindDateDefineTimestampMismatch") : null;

			/* 510 */if ((str4 != null) && (str4.equalsIgnoreCase("true"))) {
				/* 512 */this.looseTimestampDateCheck = true;
			}

			str4 = paramProperties != null ? paramProperties.getProperty("oracle.jdbc.FreeMemoryOnEnterImplicitCache") : null;
			this.isMemoryFreedOnEnteringCache = ((str4 != null) && (str4.equalsIgnoreCase("true")));

			/* 520 */initializeSetCHARCharSetObjs();

			/* 523 */this.spawnNewThreadToCancel = "true".equalsIgnoreCase(paramProperties.getProperty("oracle.jdbc.spawnNewThreadToCancel"));
		} catch (SQLException localSQLException1) {
			try {
				/* 536 */logoff();
			} catch (SQLException localSQLException2) {
			}
			/* 540 */throw localSQLException1;
		}

		/* 544 */this.txnMode = 0;
	}

	abstract void initializePassword(String paramString) throws SQLException;

	public Properties getProperties() {
		/* 561 */return OracleDataSource.filterConnectionProperties(this.connectionProperties);
	}

	/** @deprecated */
	public synchronized Connection _getPC() {
		/* 580 */return null;
	}

	public synchronized oracle.jdbc.internal.OracleConnection getPhysicalConnection() {
		/* 594 */return this;
	}

	public synchronized boolean isLogicalConnection() {
		/* 605 */return false;
	}

	void initialize(String paramString1, String paramString2, String paramString3, Hashtable paramHashtable, Map paramMap1, Map paramMap2, String paramString4) throws SQLException {
		/* 623 */this.clearStatementMetaData = false;
		/* 624 */this.database = paramString4;

		/* 626 */this.url = paramString1;

		/* 628 */if ((paramString2 == null) || (paramString2.startsWith("\""))) {
			/* 630 */this.user = paramString2;
		}
		/* 632 */else
			this.user = paramString2.toUpperCase();

		/* 634 */this.protocol = paramString3;

		/* 637 */this.defaultRowPrefetch = DEFAULT_ROW_PREFETCH;
		/* 638 */this.defaultBatch = 1;

		/* 644 */if (paramHashtable != null)
			/* 645 */this.descriptorCache = paramHashtable;
		else {
			/* 647 */this.descriptorCache = new Hashtable(10);
		}
		/* 649 */this.map = paramMap1;

		/* 651 */if (paramMap2 != null)
			/* 652 */this.javaObjectMap = paramMap2;
		else {
			/* 654 */this.javaObjectMap = new Hashtable(10);
		}
		/* 656 */this.lifecycle = 1;
		/* 657 */this.txnLevel = 2;

		/* 660 */this.xaWantsError = false;
		/* 661 */this.usingXA = false;

		/* 664 */this.clientIdSet = false;
	}

	void initializeSetCHARCharSetObjs() {
		/* 672 */this.setCHARNCharSetObj = this.conversion.getDriverNCharSetObj();
		/* 673 */this.setCHARCharSetObj = this.conversion.getDriverCharSetObj();
	}

	OracleTimeout getTimeout() throws SQLException {
		/* 685 */if (this.timeout == null) {
			/* 687 */this.timeout = OracleTimeout.newTimeout(this.url);
		}

		/* 690 */return this.timeout;
	}

	public synchronized Statement createStatement() throws SQLException {
		/* 707 */return createStatement(-1, -1);
	}

	public synchronized Statement createStatement(int paramInt1, int paramInt2) throws SQLException {
		/* 730 */if (this.lifecycle != 1) {
			/* 731 */DatabaseError.throwSqlException(8);
		}
		/* 733 */OracleStatement localOracleStatement = null;

		/* 739 */localOracleStatement = this.driverExtension.allocateStatement(this, paramInt1, paramInt2);

		/* 749 */return localOracleStatement;
	}

	public synchronized PreparedStatement prepareStatement(String paramString) throws SQLException {
		/* 768 */return prepareStatement(paramString, -1, -1);
	}

	/** @deprecated */
	public synchronized PreparedStatement prepareStatementWithKey(String paramString) throws SQLException {
		/* 787 */if (this.lifecycle != 1) {
			/* 788 */DatabaseError.throwSqlException(8);
		}
		/* 790 */if (paramString == null) {
			/* 791 */return null;
		}
		/* 793 */if (!isStatementCacheInitialized()) {
			/* 794 */DatabaseError.throwSqlException(95);
		}

		/* 800 */OraclePreparedStatement localOraclePreparedStatement = null;

		/* 802 */localOraclePreparedStatement = (OraclePreparedStatement) this.statementCache.searchExplicitCache(paramString);

		/* 813 */return localOraclePreparedStatement;
	}

	public synchronized PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if ((sql == null) || (sql == "")) {
			DatabaseError.throwSqlException(104);
		}
		if (this.lifecycle != 1) {
			DatabaseError.throwSqlException(8);
		}
		OraclePreparedStatement localOraclePreparedStatement = null;

		if (this.statementCache != null) {
			localOraclePreparedStatement = (OraclePreparedStatement) this.statementCache.searchImplicitCache(sql, 1,
					(resultSetType != -1) || (resultSetConcurrency != -1) ? ResultSetUtil.getRsetTypeCode(resultSetType, resultSetConcurrency) : 1);
		}

		if (localOraclePreparedStatement == null) {
			localOraclePreparedStatement = this.driverExtension.allocatePreparedStatement(this, sql, resultSetType, resultSetConcurrency);
		}

		return localOraclePreparedStatement;
	}

	public synchronized CallableStatement prepareCall(String paramString) throws SQLException {
		/* 894 */return prepareCall(paramString, -1, -1);
	}

	public synchronized CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) throws SQLException {
		/* 924 */if ((paramString == null) || (paramString == "")) {
			/* 925 */DatabaseError.throwSqlException(104);
		}
		/* 927 */if (this.lifecycle != 1) {
			/* 928 */DatabaseError.throwSqlException(8);
		}
		/* 930 */OracleCallableStatement localOracleCallableStatement = null;

		/* 932 */if (this.statementCache != null) {
			/* 933 */localOracleCallableStatement = (OracleCallableStatement) this.statementCache.searchImplicitCache(paramString, 2,
					(paramInt1 != -1) || (paramInt2 != -1) ? ResultSetUtil.getRsetTypeCode(paramInt1, paramInt2) : 1);
		}

		/* 939 */if (localOracleCallableStatement == null) {
			/* 940 */localOracleCallableStatement = this.driverExtension.allocateCallableStatement(this, paramString, paramInt1, paramInt2);
		}

		/* 954 */return localOracleCallableStatement;
	}

	public synchronized CallableStatement prepareCallWithKey(String paramString) throws SQLException {
		/* 972 */if (this.lifecycle != 1) {
			/* 973 */DatabaseError.throwSqlException(8);
		}
		/* 975 */if (paramString == null) {
			/* 976 */return null;
		}
		/* 978 */if (!isStatementCacheInitialized()) {
			/* 979 */DatabaseError.throwSqlException(95);
		}

		/* 985 */OracleCallableStatement localOracleCallableStatement = null;

		/* 987 */localOracleCallableStatement = (OracleCallableStatement) this.statementCache.searchExplicitCache(paramString);

		/* 997 */return localOracleCallableStatement;
	}

	public String nativeSQL(String paramString) throws SQLException {
		/* 1010 */if (this.sqlObj == null) {
			/* 1012 */this.sqlObj = new OracleSql(this.conversion);
			/* 1013 */this.sqlObj.isV8Compatible = this.v8Compatible;
		}

		/* 1016 */this.sqlObj.initialize(paramString);

		/* 1018 */String str = this.sqlObj.getSql(this.processEscapes, this.convertNcharLiterals);

		/* 1023 */return str;
	}

	public synchronized void setAutoCommit(boolean paramBoolean) throws SQLException {
		/* 1034 */if (paramBoolean) {
			/* 1035 */disallowGlobalTxnMode(116);
		}
		/* 1037 */if (this.lifecycle != 1) {
			/* 1038 */DatabaseError.throwSqlException(8);
		}
		/* 1040 */needLine();
		/* 1041 */doSetAutoCommit(paramBoolean);

		/* 1043 */this.autoCommitSet = paramBoolean;
	}

	public boolean getAutoCommit() throws SQLException {
		/* 1055 */return this.autoCommitSet;
	}

	public void cancel() throws SQLException {
		/* 1075 */OracleStatement localOracleStatement = this.statements;

		/* 1077 */if (this.lifecycle != 1) {
			/* 1078 */DatabaseError.throwSqlException(8);
		}
		/* 1080 */while (localOracleStatement != null) {
			try {
				/* 1088 */localOracleStatement.cancel();
			} catch (SQLException localSQLException) {
			}

			/* 1097 */localOracleStatement = localOracleStatement.next;
		}
	}

	public synchronized void commit() throws SQLException {
		/* 1109 */disallowGlobalTxnMode(114);

		/* 1111 */if (this.lifecycle != 1) {
			/* 1112 */DatabaseError.throwSqlException(8);
		}
		/* 1114 */OracleStatement localOracleStatement = this.statements;

		/* 1116 */while (localOracleStatement != null) {
			/* 1121 */if (!localOracleStatement.closed) {
				/* 1122 */localOracleStatement.sendBatch();
			}
			/* 1124 */localOracleStatement = localOracleStatement.next;
		}

		/* 1127 */needLine();
		/* 1128 */doCommit();
	}

	public synchronized void rollback() throws SQLException {
		/* 1139 */disallowGlobalTxnMode(115);

		/* 1141 */if (this.lifecycle != 1) {
			/* 1142 */DatabaseError.throwSqlException(8);
		}
		/* 1144 */needLine();
		/* 1145 */doRollback();
	}

	public synchronized void close() throws SQLException {
		/* 1160 */if ((this.lifecycle == 2) || (this.lifecycle == 4)) {
			/* 1161 */return;
		}
		/* 1163 */if (this.lifecycle == 1)
			this.lifecycle = 2;

		try {
			/* 1167 */if (this.closeCallback != null) {
				/* 1168 */this.closeCallback.beforeClose(this, this.privateData);
			}
			/* 1170 */closeStatements(true);

			/* 1176 */needLine();

			/* 1179 */if (this.isProxy) {
				/* 1181 */close(1);
			}

			/* 1184 */logoff();
			/* 1185 */cleanup();

			/* 1187 */if (this.timeout != null) {
				/* 1188 */this.timeout.close();
			}
			/* 1190 */if (this.closeCallback != null)
				/* 1191 */this.closeCallback.afterClose(this.privateData);
		} finally {
			/* 1195 */this.lifecycle = 4;
		}
	}

	public void closeInternal(boolean paramBoolean) throws SQLException {
		/* 1202 */DatabaseError.throwSqlException(152);
	}

	synchronized void closeLogicalConnection() throws SQLException {
		/* 1207 */closeStatements(false);

		/* 1209 */if (this.clientIdSet) {
			/* 1210 */clearClientIdentifier(this.clientId);
		}
		/* 1212 */this.logicalConnectionAttached = null;
	}

	public synchronized void close(Properties paramProperties) throws SQLException {
		/* 1229 */DatabaseError.throwSqlException(152);
	}

	public synchronized void close(int paramInt) throws SQLException {
		/* 1246 */if ((paramInt & 0x1000) != 0) {
			/* 1248 */close();

			/* 1250 */return;
		}

		/* 1253 */if (((paramInt & 0x1) != 0) && (this.isProxy)) {
			/* 1255 */closeProxySession();

			/* 1257 */this.isProxy = false;
		}
	}

	public void abort() throws SQLException {
		/* 1265 */if ((this.lifecycle == 4) || (this.lifecycle == 8)) {
			/* 1266 */return;
		}

		/* 1269 */this.lifecycle = 8;

		/* 1272 */doAbort();
	}

	abstract void doAbort() throws SQLException;

	void closeProxySession() throws SQLException {
		/* 1284 */DatabaseError.throwSqlException(23);
	}

	public Properties getServerSessionInfo() throws SQLException {
		/* 1302 */DatabaseError.throwSqlException(23);

		/* 1306 */return null;
	}

	public synchronized void applyConnectionAttributes(Properties paramProperties) throws SQLException {
		/* 1315 */DatabaseError.throwSqlException(152);
	}

	public synchronized Properties getConnectionAttributes() throws SQLException {
		/* 1324 */DatabaseError.throwSqlException(152);

		/* 1326 */return null;
	}

	public synchronized Properties getUnMatchedConnectionAttributes() throws SQLException {
		/* 1335 */DatabaseError.throwSqlException(152);

		/* 1337 */return null;
	}

	public synchronized void setAbandonedTimeoutEnabled(boolean paramBoolean) throws SQLException {
		/* 1347 */DatabaseError.throwSqlException(152);
	}

	public synchronized void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt)
			throws SQLException {
		/* 1356 */DatabaseError.throwSqlException(152);
	}

	public OracleConnectionCacheCallback getConnectionCacheCallbackObj() throws SQLException {
		/* 1365 */DatabaseError.throwSqlException(152);

		/* 1367 */return null;
	}

	public Object getConnectionCacheCallbackPrivObj() throws SQLException {
		/* 1375 */DatabaseError.throwSqlException(152);

		/* 1377 */return null;
	}

	public int getConnectionCacheCallbackFlag() throws SQLException {
		/* 1385 */DatabaseError.throwSqlException(152);

		/* 1387 */return 0;
	}

	public synchronized void setConnectionReleasePriority(int paramInt) throws SQLException {
		/* 1396 */DatabaseError.throwSqlException(152);
	}

	public int getConnectionReleasePriority() throws SQLException {
		/* 1404 */DatabaseError.throwSqlException(152);

		/* 1406 */return 0;
	}

	public synchronized boolean isClosed() throws SQLException {
		/* 1420 */return this.lifecycle != 1;
	}

	public synchronized boolean isProxySession() {
		/* 1425 */return this.isProxy;
	}

	public synchronized void openProxySession(int paramInt, Properties paramProperties) throws SQLException {
		/* 1434 */if (this.isProxy) {
			/* 1435 */DatabaseError.throwSqlException(149);
		}
		/* 1437 */String str1 = paramProperties.getProperty("PROXY_USER_NAME");
		/* 1438 */String str2 = paramProperties.getProperty("PROXY_USER_PASSWORD");
		/* 1439 */String str3 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");

		/* 1441 */Object localObject = paramProperties.get("PROXY_CERTIFICATE");

		/* 1443 */if (paramInt == 1) {
			/* 1445 */if ((str1 == null) && (str2 == null))
				/* 1446 */DatabaseError.throwSqlException(150);
		}
		/* 1448 */else if (paramInt == 2) {
			/* 1450 */if (str3 == null)
				/* 1451 */DatabaseError.throwSqlException(150);
		}
		/* 1453 */else if (paramInt == 3) {
			/* 1455 */if (localObject == null) {
				/* 1456 */DatabaseError.throwSqlException(150);
			}
			try {
				/* 1460 */byte[] arrayOfByte = (byte[]) localObject;
			} catch (ClassCastException localClassCastException) {
				/* 1464 */DatabaseError.throwSqlException(150);
			}
		} else {
			/* 1468 */DatabaseError.throwSqlException(150);
		}

		/* 1471 */doProxySession(paramInt, paramProperties);
	}

	void doProxySession(int paramInt, Properties paramProperties) throws SQLException {
		/* 1480 */DatabaseError.throwSqlException(23);
	}

	void cleanup() {
		/* 1490 */this.fdo = null;
		/* 1491 */this.conversion = null;
		/* 1492 */this.statements = null;
		/* 1493 */this.descriptorCache = null;
		/* 1494 */this.map = null;
		/* 1495 */this.javaObjectMap = null;
		/* 1496 */this.statementHoldingLine = null;
		/* 1497 */this.sqlObj = null;
		/* 1498 */this.isProxy = false;
	}

	public synchronized DatabaseMetaData getMetaData() throws SQLException {
		/* 1516 */if (this.lifecycle != 1) {
			/* 1517 */DatabaseError.throwSqlException(8);
		}
		/* 1519 */if (this.databaseMetaData == null) {
			/* 1520 */this.databaseMetaData = new OracleDatabaseMetaData(this);
		}
		/* 1522 */return this.databaseMetaData;
	}

	public void setReadOnly(boolean paramBoolean) throws SQLException {
		/* 1539 */this.readOnly = paramBoolean;
	}

	public boolean isReadOnly() throws SQLException {
		/* 1556 */return this.readOnly;
	}

	public void setCatalog(String paramString) throws SQLException {
	}

	public String getCatalog() throws SQLException {
		/* 1580 */return null;
	}

	public synchronized void setTransactionIsolation(int paramInt) throws SQLException {
		/* 1591 */OracleStatement localOracleStatement = (OracleStatement) createStatement();
		try {
			/* 1595 */switch (paramInt) {
			case 2:
				/* 1600 */localOracleStatement.execute("ALTER SESSION SET ISOLATION_LEVEL = READ COMMITTED");

				/* 1602 */this.txnLevel = 2;

				/* 1604 */break;
			case 8:
				/* 1609 */localOracleStatement.execute("ALTER SESSION SET ISOLATION_LEVEL = SERIALIZABLE");

				/* 1611 */this.txnLevel = 8;

				/* 1613 */break;
			default:
				/* 1617 */DatabaseError.throwSqlException(30);
			}

		} finally {
			/* 1624 */localOracleStatement.close();
		}
	}

	public int getTransactionIsolation() throws SQLException {
		/* 1635 */return this.txnLevel;
	}

	public synchronized void setAutoClose(boolean paramBoolean) throws SQLException {
		/* 1649 */if (!paramBoolean)
			/* 1650 */DatabaseError.throwSqlException(31);
	}

	public boolean getAutoClose() throws SQLException {
		/* 1663 */return true;
	}

	public SQLWarning getWarnings() throws SQLException {
		/* 1675 */return this.sqlWarning;
	}

	public void clearWarnings() throws SQLException {
		/* 1684 */this.sqlWarning = null;
	}

	public void setWarnings(SQLWarning paramSQLWarning) {
		/* 1693 */this.sqlWarning = paramSQLWarning;
	}

	public void setDefaultRowPrefetch(int paramInt) throws SQLException {
		/* 1739 */if (paramInt <= 0) {
			/* 1740 */DatabaseError.throwSqlException(20);
		}
		/* 1742 */this.defaultRowPrefetch = paramInt;
	}

	public int getDefaultRowPrefetch() {
		/* 1771 */return this.defaultRowPrefetch;
	}

	public synchronized void setDefaultExecuteBatch(int paramInt) throws SQLException {
		/* 1815 */if (paramInt <= 0) {
			/* 1816 */DatabaseError.throwSqlException(42);
		}
		/* 1818 */this.defaultBatch = paramInt;
	}

	public synchronized int getDefaultExecuteBatch() {
		/* 1848 */return this.defaultBatch;
	}

	public synchronized void setRemarksReporting(boolean paramBoolean) {
		/* 1873 */this.reportRemarks = paramBoolean;
	}

	public synchronized boolean getRemarksReporting() {
		/* 1888 */return this.reportRemarks;
	}

	public void setIncludeSynonyms(boolean paramBoolean) {
		/* 1911 */this.includeSynonyms = paramBoolean;
	}

	public synchronized String[] getEndToEndMetrics() throws SQLException {
		String[] arrayOfString;
		/* 1926 */if (this.endToEndValues == null) {
			/* 1928 */arrayOfString = null;
		} else {
			/* 1932 */arrayOfString = new String[this.endToEndValues.length];

			/* 1934 */System.arraycopy(this.endToEndValues, 0, arrayOfString, 0, this.endToEndValues.length);
		}

		/* 1939 */return arrayOfString;
	}

	public short getEndToEndECIDSequenceNumber() throws SQLException {
		/* 1955 */return this.endToEndECIDSequenceNumber;
	}

	public synchronized void setEndToEndMetrics(String[] paramArrayOfString, short paramShort) throws SQLException {
		/* 1969 */if (!this.useDMSForEndToEnd) {
			/* 1971 */String[] arrayOfString = new String[paramArrayOfString.length];

			/* 1973 */System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramArrayOfString.length);
			/* 1974 */setEndToEndMetricsInternal(arrayOfString, paramShort);
		}
	}

	void setEndToEndMetricsInternal(String[] paramArrayOfString, short paramShort) throws SQLException {
		/* 1992 */if (paramArrayOfString != this.endToEndValues) {
			/* 1994 */if (paramArrayOfString.length != 4) {
				/* 1999 */DatabaseError.throwSqlException(156);
			}
			String str;
			/* 2002 */for (int i = 0; i < 4; i++) {
				/* 2004 */str = paramArrayOfString[i];

				/* 2006 */if ((str == null) || (str.length() <= endToEndMaxLength[i]))
					continue;
				/* 2008 */DatabaseError.throwSqlException(159, str);
			}

			/* 2013 */if (this.endToEndValues != null) {
				/* 2015 */for (i = 0; i < 4; i++) {
					/* 2017 */str = paramArrayOfString[i];

					/* 2019 */if (((str != null) || (this.endToEndValues[i] == null)) && ((str == null) || (str.equals(this.endToEndValues[i])))) {
						continue;
					}
					/* 2022 */this.endToEndHasChanged[i] = true;
					/* 2023 */this.endToEndAnyChanged = true;
				}

				/* 2028 */this.endToEndHasChanged[0] |= this.endToEndHasChanged[3];
			} else {
				/* 2033 */for (i = 0; i < 4; i++) {
					/* 2035 */this.endToEndHasChanged[i] = true;
				}

				/* 2038 */this.endToEndAnyChanged = true;
			}

			/* 2042 */this.endToEndValues = paramArrayOfString;
		}

		/* 2045 */this.endToEndECIDSequenceNumber = paramShort;
	}

	void updateEndToEndMetrics() throws SQLException {
	}

	public boolean getIncludeSynonyms() {
		/* 2082 */return this.includeSynonyms;
	}

	public void setRestrictGetTables(boolean paramBoolean) {
		/* 2123 */this.restrictGetTables = paramBoolean;
	}

	public boolean getRestrictGetTables() {
		/* 2139 */return this.restrictGetTables;
	}

	public void setDefaultFixedString(boolean paramBoolean) {
		/* 2168 */this.defaultFixedString = paramBoolean;
	}

	public void setDefaultNChar(boolean paramBoolean) {
		/* 2177 */this.defaultNChar = paramBoolean;
	}

	public boolean getDefaultFixedString() {
		/* 2205 */return this.defaultFixedString;
	}

	public int getNlsRatio() {
		/* 2218 */return 1;
	}

	public int getC2SNlsRatio() {
		/* 2223 */return 1;
	}

	synchronized void addStatement(OracleStatement paramOracleStatement) {
		/* 2230 */if (paramOracleStatement.next != null) {
			/* 2231 */throw new Error("add_statement called twice on " + paramOracleStatement);
		}
		/* 2233 */paramOracleStatement.next = this.statements;

		/* 2235 */if (this.statements != null) {
			/* 2236 */this.statements.prev = paramOracleStatement;
		}
		/* 2238 */this.statements = paramOracleStatement;
	}

	synchronized void removeStatement(OracleStatement paramOracleStatement) {
		/* 2255 */OracleStatement localOracleStatement1 = paramOracleStatement.prev;
		/* 2256 */OracleStatement localOracleStatement2 = paramOracleStatement.next;

		/* 2258 */if (localOracleStatement1 == null) {
			/* 2260 */if (this.statements != paramOracleStatement) {
				/* 2261 */return;
			}
			/* 2263 */this.statements = localOracleStatement2;
		} else {
			/* 2266 */localOracleStatement1.next = localOracleStatement2;
		}
		/* 2268 */if (localOracleStatement2 != null) {
			/* 2269 */localOracleStatement2.prev = localOracleStatement1;
		}
		/* 2271 */paramOracleStatement.next = null;
		/* 2272 */paramOracleStatement.prev = null;
	}

	synchronized void closeStatements(boolean paramBoolean) throws SQLException {
		/* 2287 */if ((paramBoolean) && (isStatementCacheInitialized())) {
			/* 2291 */this.statementCache.close();

			/* 2293 */this.statementCache = null;
			/* 2294 */this.clearStatementMetaData = true;
		}

		/* 2299 */Object localObject = this.statements;
		OracleStatement localOracleStatement;
		/* 2301 */while (localObject != null) {
			/* 2303 */localOracleStatement = ((OracleStatement) localObject).nextChild;

			/* 2305 */if (((OracleStatement) localObject).serverCursor) {
				/* 2307 */((OracleStatement) localObject).close();
				/* 2308 */removeStatement((OracleStatement) localObject);
			}

			/* 2311 */localObject = localOracleStatement;
		}

		/* 2315 */localObject = this.statements;

		/* 2317 */while (localObject != null) {
			/* 2319 */localOracleStatement = ((OracleStatement) localObject).next;

			/* 2321 */((OracleStatement) localObject).close();
			/* 2322 */removeStatement((OracleStatement) localObject);

			/* 2324 */localObject = localOracleStatement;
		}
	}

	synchronized void needLine() throws SQLException {
		/* 2339 */if (this.statementHoldingLine != null) {
			/* 2344 */this.statementHoldingLine.freeLine();
		}
	}

	synchronized void holdLine(oracle.jdbc.internal.OracleStatement paramOracleStatement) {
		/* 2351 */holdLine((OracleStatement) paramOracleStatement);
	}

	synchronized void holdLine(OracleStatement paramOracleStatement) {
		/* 2360 */this.statementHoldingLine = paramOracleStatement;
	}

	synchronized void releaseLine() {
		/* 2368 */releaseLineForCancel();
	}

	void releaseLineForCancel() {
		/* 2377 */this.statementHoldingLine = null;
	}

	public synchronized void startup(String paramString, int paramInt) throws SQLException {
		/* 2383 */if (this.lifecycle != 1) {
			/* 2384 */DatabaseError.throwSqlException(8);
		}
		/* 2386 */DatabaseError.throwSqlException(23);
	}

	public synchronized void shutdown(int paramInt) throws SQLException {
		/* 2391 */if (this.lifecycle != 1) {
			/* 2392 */DatabaseError.throwSqlException(8);
		}
		/* 2394 */DatabaseError.throwSqlException(23);
	}

	public synchronized void archive(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 2401 */if (this.lifecycle != 1) {
			/* 2402 */DatabaseError.throwSqlException(8);
		}
		/* 2404 */DatabaseError.throwSqlException(23);
	}

	public synchronized void registerSQLType(String paramString1, String paramString2) throws SQLException {
		/* 2415 */if ((paramString1 == null) || (paramString2 == null)) {
			/* 2416 */DatabaseError.throwSqlException(68);
		}
		try {
			/* 2420 */registerSQLType(paramString1, Class.forName(paramString2));
		} catch (ClassNotFoundException localClassNotFoundException) {
			/* 2424 */DatabaseError.throwSqlException(1, "Class not found: " + paramString2);
		}
	}

	public synchronized void registerSQLType(String paramString, Class paramClass) throws SQLException {
		/* 2433 */if ((paramString == null) || (paramClass == null)) {
			/* 2434 */DatabaseError.throwSqlException(68);
		}
		/* 2436 */ensureClassMapExists();

		/* 2438 */this.map.put(paramString, paramClass);
		/* 2439 */this.map.put(paramClass.getName(), paramString);
	}

	void ensureClassMapExists() {
		/* 2444 */if (this.map == null)
			/* 2445 */initializeClassMap();
	}

	void initializeClassMap() {
		/* 2450 */Hashtable localHashtable = new Hashtable(10);

		/* 2452 */addDefaultClassMapEntriesTo(localHashtable);

		/* 2454 */this.map = localHashtable;
	}

	public synchronized String getSQLType(Object paramObject) throws SQLException {
		/* 2460 */if ((paramObject != null) && (this.map != null)) {
			/* 2462 */String str = paramObject.getClass().getName();

			/* 2464 */return (String) this.map.get(str);
		}

		/* 2467 */return null;
	}

	public synchronized Object getJavaObject(String paramString) throws SQLException {
		/* 2473 */Object localObject = null;
		try {
			/* 2477 */if ((paramString != null) && (this.map != null)) {
				/* 2479 */Class localClass = (Class) this.map.get(paramString);

				/* 2481 */localObject = localClass.newInstance();
			}
		} catch (IllegalAccessException localIllegalAccessException) {
			/* 2486 */localIllegalAccessException.printStackTrace();
		} catch (InstantiationException localInstantiationException) {
			/* 2490 */localInstantiationException.printStackTrace();
		}

		/* 2493 */return localObject;
	}

	public synchronized void putDescriptor(String paramString, Object paramObject) throws SQLException {
		/* 2506 */if ((paramString != null) && (paramObject != null)) {
			/* 2508 */if (this.descriptorCache == null) {
				/* 2509 */this.descriptorCache = new Hashtable(10);
			}
			/* 2511 */((TypeDescriptor) paramObject).fixupConnection(this);
			/* 2512 */this.descriptorCache.put(paramString, paramObject);
		} else {
			/* 2515 */DatabaseError.throwSqlException(68);
		}
	}

	public synchronized Object getDescriptor(String paramString) {
		/* 2524 */Object localObject = null;

		/* 2526 */if ((paramString != null) && (this.descriptorCache != null)) {
			/* 2527 */localObject = this.descriptorCache.get(paramString);
		}

		/* 2532 */return localObject;
	}

	/** @deprecated */
	public synchronized void removeDecriptor(String paramString) {
		/* 2542 */removeDescriptor(paramString);
	}

	public synchronized void removeDescriptor(String paramString) {
		/* 2556 */if ((paramString != null) && (this.descriptorCache != null))
			/* 2557 */this.descriptorCache.remove(paramString);
	}

	public synchronized void removeAllDescriptor() {
		/* 2570 */if (this.descriptorCache != null)
			/* 2571 */this.descriptorCache.clear();
	}

	public int numberOfDescriptorCacheEntries() {
		/* 2584 */if (this.descriptorCache != null) {
			/* 2585 */return this.descriptorCache.size();
		}
		/* 2587 */return 0;
	}

	public Enumeration descriptorCacheKeys() {
		/* 2598 */if (this.descriptorCache != null) {
			/* 2599 */return this.descriptorCache.keys();
		}
		/* 2601 */return null;
	}

	public synchronized void putDescriptor(byte[] paramArrayOfByte, Object paramObject) throws SQLException {
		/* 2614 */if ((paramArrayOfByte != null) && (paramObject != null)) {
			/* 2616 */if (this.descriptorCache == null) {
				/* 2617 */this.descriptorCache = new Hashtable(10);
			}
			/* 2619 */this.descriptorCache.put(new ByteArrayKey(paramArrayOfByte), paramObject);
		} else {
			/* 2622 */DatabaseError.throwSqlException(68);
		}
	}

	public synchronized Object getDescriptor(byte[] paramArrayOfByte) {
		/* 2631 */Object localObject = null;

		/* 2633 */if ((paramArrayOfByte != null) && (this.descriptorCache != null)) {
			/* 2634 */localObject = this.descriptorCache.get(new ByteArrayKey(paramArrayOfByte));
		}

		/* 2639 */return localObject;
	}

	public synchronized void removeDecriptor(byte[] paramArrayOfByte) {
		/* 2652 */if ((paramArrayOfByte != null) && (this.descriptorCache != null))
			/* 2653 */this.descriptorCache.remove(new ByteArrayKey(paramArrayOfByte));
	}

	public short getJdbcCsId() throws SQLException {
		/* 2672 */if (this.conversion == null) {
			/* 2674 */DatabaseError.throwSqlException(65);
		}

		/* 2677 */return this.conversion.getClientCharSet();
	}

	public short getDbCsId() throws SQLException {
		/* 2690 */if (this.conversion == null) {
			/* 2692 */DatabaseError.throwSqlException(65);
		}

		/* 2695 */return this.conversion.getServerCharSetId();
	}

	public short getNCsId() throws SQLException {
		/* 2700 */if (this.conversion == null) {
			/* 2702 */DatabaseError.throwSqlException(65);
		}

		/* 2705 */return this.conversion.getNCharSetId();
	}

	public short getStructAttrCsId() throws SQLException {
		/* 2719 */return getDbCsId();
	}

	public short getStructAttrNCsId() throws SQLException {
		/* 2724 */return getNCsId();
	}

	public synchronized Map getTypeMap() {
		/* 2732 */ensureClassMapExists();

		/* 2734 */return this.map;
	}

	public synchronized void setTypeMap(Map paramMap) {
		/* 2739 */addDefaultClassMapEntriesTo(paramMap);

		/* 2741 */this.map = paramMap;
	}

	void addDefaultClassMapEntriesTo(Map paramMap) {
		/* 2746 */if (paramMap != null) {
			/* 2748 */addClassMapEntry("SYS.XMLTYPE", "oracle.xdb.XMLTypeFactory", paramMap);
		}
	}

	void addClassMapEntry(String paramString1, String paramString2, Map paramMap) {
		/* 2754 */if (containsKey(paramMap, paramString1)) {
			/* 2755 */return;
		}
		try {
			/* 2759 */Class localClass = safelyGetClassForName(paramString2);

			/* 2761 */paramMap.put(paramString1, localClass);
		} catch (ClassNotFoundException localClassNotFoundException) {
		}
	}

	public synchronized void setUsingXAFlag(boolean paramBoolean) {
		/* 2771 */this.usingXA = paramBoolean;
	}

	public synchronized boolean getUsingXAFlag() {
		/* 2776 */return this.usingXA;
	}

	public synchronized void setXAErrorFlag(boolean paramBoolean) {
		/* 2782 */this.xaWantsError = paramBoolean;
	}

	public synchronized boolean getXAErrorFlag() {
		/* 2787 */return this.xaWantsError;
	}

	void initUserName() throws SQLException {
		/* 2793 */if (this.user != null) {
			/* 2794 */return;
		}
		/* 2796 */Statement localStatement = null;
		/* 2797 */ResultSet localResultSet = null;
		try {
			/* 2800 */localStatement = createStatement();
			/* 2801 */((OracleStatement) localStatement).setRowPrefetch(1);
			/* 2802 */localResultSet = localStatement.executeQuery("SELECT USER FROM DUAL");
			/* 2803 */if (localResultSet.next())
				/* 2804 */this.user = localResultSet.getString(1);
		} finally {
			/* 2808 */if (localResultSet != null)
				/* 2809 */localResultSet.close();
			/* 2810 */if (localStatement != null)
				/* 2811 */localStatement.close();
			/* 2812 */localResultSet = null;
			/* 2813 */localStatement = null;
		}
	}

	public synchronized String getUserName() throws SQLException {
		/* 2819 */if (this.user == null) {
			/* 2820 */initUserName();
		}
		/* 2822 */return this.user;
	}

	public synchronized void setStartTime(long paramLong) throws SQLException {
		/* 2837 */DatabaseError.throwSqlException(152);
	}

	public synchronized long getStartTime() throws SQLException {
		/* 2849 */DatabaseError.throwSqlException(152);

		/* 2851 */return -1L;
	}

	void registerHeartbeat() throws SQLException {
		/* 2863 */if (this.logicalConnectionAttached != null)
			/* 2864 */this.logicalConnectionAttached.registerHeartbeat();
	}

	public int getHeartbeatNoChangeCount() throws SQLException {
		/* 2875 */DatabaseError.throwSqlException(152);

		/* 2877 */return -1;
	}

	public synchronized byte[] getFDO(boolean paramBoolean) throws SQLException {
		/* 2886 */if ((this.fdo == null) && (paramBoolean)) {
			/* 2888 */CallableStatement localCallableStatement = null;
			try {
				/* 2892 */localCallableStatement = prepareCall("begin ? := dbms_pickler.get_format (?); end;");

				/* 2895 */localCallableStatement.registerOutParameter(1, 2);
				/* 2896 */localCallableStatement.registerOutParameter(2, -4);
				/* 2897 */localCallableStatement.execute();

				/* 2899 */this.fdo = localCallableStatement.getBytes(2);
			} finally {
				/* 2903 */if (localCallableStatement != null) {
					/* 2904 */localCallableStatement.close();
				}
				/* 2906 */localCallableStatement = null;
			}
		}

		/* 2910 */return this.fdo;
	}

	public synchronized void setFDO(byte[] paramArrayOfByte) throws SQLException {
		/* 2919 */this.fdo = paramArrayOfByte;
	}

	public synchronized boolean getBigEndian() throws SQLException {
		/* 2928 */if (this.bigEndian == null) {
			/* 2930 */int[] arrayOfInt = Util.toJavaUnsignedBytes(getFDO(true));

			/* 2934 */int i = arrayOfInt[(6 + arrayOfInt[5] + arrayOfInt[6] + 5)];

			/* 2939 */int j = (byte) (i & 0x10);

			/* 2941 */if (j < 0) {
				/* 2942 */j += 256;
			}
			/* 2944 */if (j > 0)
				/* 2945 */this.bigEndian = new Boolean(true);
			else {
				/* 2947 */this.bigEndian = new Boolean(false);
			}
		}
		/* 2950 */return this.bigEndian.booleanValue();
	}

	public void setHoldability(int paramInt) throws SQLException {
	}

	public int getHoldability() throws SQLException {
		/* 3004 */return 1;
	}

	public synchronized Savepoint setSavepoint() throws SQLException {
		/* 3020 */return oracleSetSavepoint();
	}

	public synchronized Savepoint setSavepoint(String paramString) throws SQLException {
		/* 3030 */return oracleSetSavepoint(paramString);
	}

	public synchronized void rollback(Savepoint paramSavepoint) throws SQLException {
		/* 3041 */disallowGlobalTxnMode(122);

		/* 3044 */if (this.autoCommitSet) {
			/* 3045 */DatabaseError.throwSqlException(121);
		}

		/* 3049 */if ((this.savepointStatement == null) || (((OracleStatement) this.savepointStatement).closed)) {
			/* 3052 */this.savepointStatement = createStatement();
		}

		/* 3055 */String str = null;
		try {
			/* 3059 */str = paramSavepoint.getSavepointName();
		} catch (SQLException localSQLException) {
			/* 3063 */str = "ORACLE_SVPT_" + paramSavepoint.getSavepointId();
		}

		/* 3066 */this.savepointStatement.executeUpdate("ROLLBACK TO " + str);
	}

	public synchronized void releaseSavepoint(Savepoint paramSavepoint) throws SQLException {
		/* 3075 */DatabaseError.throwUnsupportedFeatureSqlException();
	}

	public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 3122 */return createStatement(paramInt1, paramInt2);
	}

	public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 3171 */return prepareStatement(paramString, paramInt1, paramInt2);
	}

	public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 3217 */return prepareCall(paramString, paramInt1, paramInt2);
	}

	public PreparedStatement prepareStatement(String paramString, int paramInt) throws SQLException {
		/* 3264 */if ((paramInt == 2) || (!AutoKeyInfo.isInsertSqlStmt(paramString))) {
			/* 3266 */return prepareStatement(paramString);
		}
		/* 3268 */if (paramInt != 1) {
			/* 3269 */DatabaseError.throwSqlException(68);
		}
		/* 3271 */AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString);

		/* 3273 */String str = localAutoKeyInfo.getNewSql();
		/* 3274 */OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement) prepareStatement(str);

		/* 3277 */localOraclePreparedStatement.isAutoGeneratedKey = true;
		/* 3278 */localOraclePreparedStatement.autoKeyInfo = localAutoKeyInfo;
		/* 3279 */localOraclePreparedStatement.registerReturnParamsForAutoKey();
		/* 3280 */return localOraclePreparedStatement;
	}

	public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfInt) throws SQLException {
		/* 3327 */if (!AutoKeyInfo.isInsertSqlStmt(paramString))
			return prepareStatement(paramString);

		/* 3329 */if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0)) {
			/* 3330 */DatabaseError.throwSqlException(68);
		}
		/* 3332 */AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfInt);

		/* 3335 */doDescribeTable(localAutoKeyInfo);

		/* 3337 */String str = localAutoKeyInfo.getNewSql();
		/* 3338 */OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement) prepareStatement(str);

		/* 3341 */localOraclePreparedStatement.isAutoGeneratedKey = true;
		/* 3342 */localOraclePreparedStatement.autoKeyInfo = localAutoKeyInfo;
		/* 3343 */localOraclePreparedStatement.registerReturnParamsForAutoKey();
		/* 3344 */return localOraclePreparedStatement;
	}

	public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString) throws SQLException {
		/* 3391 */if (!AutoKeyInfo.isInsertSqlStmt(paramString))
			return prepareStatement(paramString);

		/* 3393 */if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
			/* 3394 */DatabaseError.throwSqlException(68);
		}
		/* 3396 */AutoKeyInfo localAutoKeyInfo = new AutoKeyInfo(paramString, paramArrayOfString);

		/* 3399 */doDescribeTable(localAutoKeyInfo);

		/* 3401 */String str = localAutoKeyInfo.getNewSql();
		/* 3402 */OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement) prepareStatement(str);

		/* 3405 */localOraclePreparedStatement.isAutoGeneratedKey = true;
		/* 3406 */localOraclePreparedStatement.autoKeyInfo = localAutoKeyInfo;
		/* 3407 */localOraclePreparedStatement.registerReturnParamsForAutoKey();
		/* 3408 */return localOraclePreparedStatement;
	}

	public synchronized oracle.jdbc.OracleSavepoint oracleSetSavepoint() throws SQLException {
		/* 3434 */disallowGlobalTxnMode(117);

		/* 3437 */if (this.autoCommitSet) {
			/* 3438 */DatabaseError.throwSqlException(120);
		}

		/* 3442 */if ((this.savepointStatement == null) || (((OracleStatement) this.savepointStatement).closed)) {
			/* 3445 */this.savepointStatement = createStatement();
		}

		/* 3448 */OracleSavepoint localOracleSavepoint = new OracleSavepoint();

		/* 3450 */String str = "SAVEPOINT ORACLE_SVPT_" + localOracleSavepoint.getSavepointId();

		/* 3452 */this.savepointStatement.executeUpdate(str);

		/* 3457 */return localOracleSavepoint;
	}

	public synchronized oracle.jdbc.OracleSavepoint oracleSetSavepoint(String paramString) throws SQLException {
		/* 3484 */disallowGlobalTxnMode(117);

		/* 3487 */if (this.autoCommitSet) {
			/* 3488 */DatabaseError.throwSqlException(120);
		}

		/* 3492 */if ((this.savepointStatement == null) || (((OracleStatement) this.savepointStatement).closed)) {
			/* 3495 */this.savepointStatement = createStatement();
		}

		/* 3498 */OracleSavepoint localOracleSavepoint = new OracleSavepoint(paramString);

		/* 3500 */String str = "SAVEPOINT " + localOracleSavepoint.getSavepointName();

		/* 3502 */this.savepointStatement.executeUpdate(str);

		/* 3507 */return localOracleSavepoint;
	}

	public synchronized void oracleRollback(oracle.jdbc.OracleSavepoint paramOracleSavepoint) throws SQLException {
		/* 3535 */disallowGlobalTxnMode(115);

		/* 3538 */if (this.autoCommitSet) {
			/* 3539 */DatabaseError.throwSqlException(121);
		}

		/* 3543 */if ((this.savepointStatement == null) || (((OracleStatement) this.savepointStatement).closed)) {
			/* 3546 */this.savepointStatement = createStatement();
		}

		/* 3549 */String str = null;
		try {
			/* 3553 */str = paramOracleSavepoint.getSavepointName();
		} catch (SQLException localSQLException) {
			/* 3557 */str = "ORACLE_SVPT_" + paramOracleSavepoint.getSavepointId();
		}

		/* 3560 */this.savepointStatement.executeUpdate("ROLLBACK TO " + str);
	}

	public synchronized void oracleReleaseSavepoint(oracle.jdbc.OracleSavepoint paramOracleSavepoint) throws SQLException {
		/* 3587 */DatabaseError.throwUnsupportedFeatureSqlException();
	}

	void disallowGlobalTxnMode(int paramInt) throws SQLException {
		/* 3605 */if (this.txnMode == 1)
			/* 3606 */DatabaseError.throwSqlException(paramInt);
	}

	public void setTxnMode(int paramInt) {
		/* 3617 */this.txnMode = paramInt;
	}

	public int getTxnMode() {
		/* 3622 */return this.txnMode;
	}

	public synchronized Object getClientData(Object paramObject) {
		/* 3651 */if (this.clientData == null) {
			/* 3653 */return null;
		}

		/* 3656 */return this.clientData.get(paramObject);
	}

	public synchronized Object setClientData(Object paramObject1, Object paramObject2) {
		/* 3683 */if (this.clientData == null) {
			/* 3685 */this.clientData = new Hashtable();
		}

		/* 3688 */return this.clientData.put(paramObject1, paramObject2);
	}

	public synchronized Object removeClientData(Object paramObject) {
		/* 3709 */if (this.clientData == null) {
			/* 3711 */return null;
		}

		/* 3714 */return this.clientData.remove(paramObject);
	}

	public BlobDBAccess createBlobDBAccess() throws SQLException {
		/* 3719 */DatabaseError.throwSqlException(23);

		/* 3721 */return null;
	}

	public ClobDBAccess createClobDBAccess() throws SQLException {
		/* 3726 */DatabaseError.throwSqlException(23);

		/* 3728 */return null;
	}

	public BfileDBAccess createBfileDBAccess() throws SQLException {
		/* 3733 */DatabaseError.throwSqlException(23);

		/* 3735 */return null;
	}

	public void printState() {
		try {
			/* 3760 */int i = getJdbcCsId();

			/* 3765 */int j = getDbCsId();

			/* 3770 */int k = getStructAttrCsId();
		} catch (SQLException localSQLException) {
			/* 3777 */localSQLException.printStackTrace();
		}
	}

	public String getProtocolType() {
		/* 3787 */return this.protocol;
	}

	public String getURL() {
		/* 3796 */return this.url;
	}

	/** @deprecated */
	public synchronized void setStmtCacheSize(int paramInt) throws SQLException {
		/* 3810 */setStatementCacheSize(paramInt);
		/* 3811 */setImplicitCachingEnabled(true);
		/* 3812 */setExplicitCachingEnabled(true);
	}

	/** @deprecated */
	public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean) throws SQLException {
		/* 3827 */setStatementCacheSize(paramInt);
		/* 3828 */setImplicitCachingEnabled(true);

		/* 3831 */setExplicitCachingEnabled(true);

		/* 3833 */this.clearStatementMetaData = paramBoolean;
	}

	/** @deprecated */
	public synchronized int getStmtCacheSize() {
		/* 3847 */int i = 0;
		try {
			/* 3851 */i = getStatementCacheSize();
		} catch (SQLException localSQLException) {
		}

		/* 3861 */if (i == -1) {
			/* 3866 */i = 0;
		}

		/* 3869 */return i;
	}

	public synchronized void setStatementCacheSize(int paramInt) throws SQLException {
		/* 3890 */if (this.statementCache == null) {
			/* 3892 */this.statementCache = new LRUStatementCache(paramInt);
		} else {
			/* 3899 */this.statementCache.resize(paramInt);
		}
	}

	public synchronized int getStatementCacheSize() throws SQLException {
		/* 3917 */if (this.statementCache == null) {
			/* 3918 */return -1;
		}
		/* 3920 */return this.statementCache.getCacheSize();
	}

	public synchronized void setImplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 3943 */if (this.statementCache == null) {
			/* 3948 */this.statementCache = new LRUStatementCache(0);
		}

		/* 3955 */this.statementCache.setImplicitCachingEnabled(paramBoolean);
	}

	public synchronized boolean getImplicitCachingEnabled() throws SQLException {
		/* 3974 */if (this.statementCache == null) {
			/* 3975 */return false;
		}
		/* 3977 */return this.statementCache.getImplicitCachingEnabled();
	}

	public synchronized void setExplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 4000 */if (this.statementCache == null) {
			/* 4005 */this.statementCache = new LRUStatementCache(0);
		}

		/* 4012 */this.statementCache.setExplicitCachingEnabled(paramBoolean);
	}

	public synchronized boolean getExplicitCachingEnabled() throws SQLException {
		/* 4031 */if (this.statementCache == null) {
			/* 4032 */return false;
		}
		/* 4034 */return this.statementCache.getExplicitCachingEnabled();
	}

	public synchronized void purgeImplicitCache() throws SQLException {
		/* 4053 */if (this.statementCache != null)
			/* 4054 */this.statementCache.purgeImplicitCache();
	}

	public synchronized void purgeExplicitCache() throws SQLException {
		/* 4073 */if (this.statementCache != null)
			/* 4074 */this.statementCache.purgeExplicitCache();
	}

	public synchronized PreparedStatement getStatementWithKey(String paramString) throws SQLException {
		/* 4096 */if (this.statementCache != null) {
			/* 4098 */OracleStatement localOracleStatement = this.statementCache.searchExplicitCache(paramString);

			/* 4101 */if ((localOracleStatement == null) || (localOracleStatement.statementType == 1)) {
				/* 4102 */return (PreparedStatement) localOracleStatement;
			}

			/* 4105 */DatabaseError.throwSqlException(125);

			/* 4107 */return null;
		}

		/* 4111 */return null;
	}

	public synchronized CallableStatement getCallWithKey(String paramString) throws SQLException {
		/* 4133 */if (this.statementCache != null) {
			/* 4135 */OracleStatement localOracleStatement = this.statementCache.searchExplicitCache(paramString);

			/* 4138 */if ((localOracleStatement == null) || (localOracleStatement.statementType == 2)) {
				/* 4139 */return (CallableStatement) localOracleStatement;
			}

			/* 4142 */DatabaseError.throwSqlException(125);

			/* 4144 */return null;
		}

		/* 4148 */return null;
	}

	public synchronized void cacheImplicitStatement(OraclePreparedStatement paramOraclePreparedStatement, String paramString, int paramInt1, int paramInt2) throws SQLException {
		if (this.statementCache == null)
			DatabaseError.throwSqlException(95);
		else
			this.statementCache.addToImplicitCache(paramOraclePreparedStatement, paramString, paramInt1, paramInt2);
	}

	public synchronized void cacheExplicitStatement(OraclePreparedStatement paramOraclePreparedStatement, String paramString) throws SQLException {
		if (this.statementCache == null)
			DatabaseError.throwSqlException(95);
		else
			this.statementCache.addToExplicitCache(paramOraclePreparedStatement, paramString);
	}

	public synchronized boolean isStatementCacheInitialized() {
		/* 4200 */if (this.statementCache == null) {
			/* 4201 */return false;
		}
		/* 4203 */return this.statementCache.getCacheSize() != 0;
	}

	public void setDefaultAutoRefetch(boolean paramBoolean) throws SQLException {
		/* 4230 */this.defaultAutoRefetch = paramBoolean;
	}

	public boolean getDefaultAutoRefetch() throws SQLException {
		/* 4245 */return this.defaultAutoRefetch;
	}

	public synchronized void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject) throws SQLException {
		/* 4265 */DatabaseError.throwSqlException(23);
	}

	public String getDatabaseProductVersion() throws SQLException {
		/* 4275 */if (this.databaseProductVersion == "") {
			/* 4277 */needLine();

			/* 4279 */this.databaseProductVersion = doGetDatabaseProductVersion();
		}

		/* 4282 */return this.databaseProductVersion;
	}

	public synchronized boolean getReportRemarks() {
		/* 4287 */return this.reportRemarks;
	}

	public short getVersionNumber() throws SQLException {
		/* 4292 */if (this.versionNumber == -1) {
			/* 4294 */synchronized (this) {
				/* 4296 */if (this.versionNumber == -1) {
					/* 4298 */needLine();

					/* 4300 */this.versionNumber = doGetVersionNumber();
				}
			}
		}

		/* 4305 */return this.versionNumber;
	}

	public synchronized void registerCloseCallback(OracleCloseCallback paramOracleCloseCallback, Object paramObject) {
		/* 4311 */this.closeCallback = paramOracleCloseCallback;
		/* 4312 */this.privateData = paramObject;
	}

	public void setCreateStatementAsRefCursor(boolean paramBoolean) {
	}

	public boolean getCreateStatementAsRefCursor() {
		/* 4360 */return false;
	}

	public int pingDatabase(int timeout) throws SQLException {
		if (this.lifecycle != 1) {
			return -1;
		}
		Statement stmt = null;
		try {
			stmt = createStatement();

			((OracleStatement) stmt).defineColumnType(1, 12, 1);
			stmt.executeQuery("SELECT 'x' FROM DUAL");
		} catch (SQLException localSQLException) {
			int i = -1;
			return i;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return 0;
	}

	public synchronized Map getJavaObjectTypeMap() {
		/* 4407 */return this.javaObjectMap;
	}

	public synchronized void setJavaObjectTypeMap(Map paramMap) {
		/* 4415 */this.javaObjectMap = paramMap;
	}

	/** @deprecated */
	public void clearClientIdentifier(String paramString) throws SQLException {
		/* 4428 */if ((!this.useDMSForEndToEnd) && (paramString != null) && (paramString != "")) {
			/* 4434 */String[] arrayOfString = getEndToEndMetrics();

			/* 4436 */if ((arrayOfString != null) && (paramString.equals(arrayOfString[1]))) {
				/* 4439 */arrayOfString[1] = null;

				/* 4441 */setEndToEndMetrics(arrayOfString, getEndToEndECIDSequenceNumber());
			}
		}
	}

	/** @deprecated */
	public void setClientIdentifier(String paramString) throws SQLException {
		/* 4463 */if (!this.useDMSForEndToEnd) {
			/* 4465 */String[] arrayOfString = getEndToEndMetrics();

			/* 4467 */if (arrayOfString == null) {
				/* 4469 */arrayOfString = new String[4];
			}

			/* 4472 */arrayOfString[1] = paramString;

			/* 4474 */setEndToEndMetrics(arrayOfString, getEndToEndECIDSequenceNumber());
		}
	}

	public void setSessionTimeZone(String paramString) throws SQLException {
		/* 4514 */Statement localStatement = null;
		/* 4515 */ResultSet localResultSet = null;
		try {
			/* 4519 */localStatement = createStatement();

			/* 4521 */localStatement.executeUpdate("ALTER SESSION SET TIME_ZONE = '" + paramString + "'");

			/* 4526 */localResultSet = localStatement.executeQuery("SELECT DBTIMEZONE FROM DUAL");

			/* 4528 */localResultSet.next();

			/* 4530 */String str = localResultSet.getString(1);

			/* 4532 */setDbTzCalendar(str);
		} catch (SQLException localSQLException) {
			/* 4540 */throw localSQLException;
		} finally {
			/* 4544 */if (localStatement != null) {
				/* 4545 */localStatement.close();
			}
		}
		/* 4548 */this.sessionTimeZone = paramString;
	}

	public String getSessionTimeZone() {
		/* 4561 */return this.sessionTimeZone;
	}

	void setDbTzCalendar(String paramString) {
		/* 4577 */int i = paramString.charAt(0);

		/* 4579 */if ((i == 45) || (i == 43))
			/* 4580 */paramString = "GMT" + paramString;
		else {
			/* 4582 */paramString = "GMT+" + paramString;
		}

		/* 4587 */TimeZone localTimeZone = TimeZone.getTimeZone(paramString);

		/* 4589 */this.dbTzCalendar = new GregorianCalendar(localTimeZone);
	}

	public Calendar getDbTzCalendar() {
		/* 4600 */return this.dbTzCalendar;
	}

	public void setAccumulateBatchResult(boolean paramBoolean) {
		/* 4615 */this.accumulateBatchResult = paramBoolean;
	}

	public boolean isAccumulateBatchResult() {
		/* 4631 */return this.accumulateBatchResult;
	}

	public void setJ2EE13Compliant(boolean paramBoolean) {
		/* 4646 */this.j2ee13Compliant = paramBoolean;
	}

	public boolean getJ2EE13Compliant() {
		/* 4661 */return this.j2ee13Compliant;
	}

	public Class classForNameAndSchema(String paramString1, String paramString2) throws ClassNotFoundException {
		/* 4679 */return Class.forName(paramString1);
	}

	public Class safelyGetClassForName(String paramString) throws ClassNotFoundException {
		/* 4697 */return Class.forName(paramString);
	}

	public int getHeapAllocSize() throws SQLException {
		/* 4709 */if (this.lifecycle != 1) {
			/* 4710 */DatabaseError.throwSqlException(8);
		}
		/* 4712 */DatabaseError.throwSqlException(23);

		/* 4715 */return -1;
	}

	public int getOCIEnvHeapAllocSize() throws SQLException {
		/* 4726 */if (this.lifecycle != 1) {
			/* 4727 */DatabaseError.throwSqlException(8);
		}
		/* 4729 */DatabaseError.throwSqlException(23);

		/* 4732 */return -1;
	}

	public static OracleConnection unwrapCompletely(oracle.jdbc.OracleConnection paramOracleConnection) {
		/* 4744 */Object localObject1 = paramOracleConnection;
		/* 4745 */Object localObject2 = localObject1;
		while (true) {
			/* 4749 */if (localObject2 == null) {
				/* 4750 */return (OracleConnection) localObject1;
			}
			/* 4752 */localObject1 = localObject2;
			/* 4753 */localObject2 = ((oracle.jdbc.OracleConnection) localObject1).unwrap();
		}
	}

	public void setWrapper(oracle.jdbc.OracleConnection paramOracleConnection) {
		/* 4759 */this.wrapper = paramOracleConnection;
	}

	public oracle.jdbc.OracleConnection unwrap() {
		/* 4764 */return null;
	}

	public oracle.jdbc.OracleConnection getWrapper() {
		/* 4769 */if (this.wrapper != null) {
			/* 4770 */return this.wrapper;
		}
		/* 4772 */return this;
	}

	static oracle.jdbc.internal.OracleConnection _physicalConnectionWithin(Connection paramConnection) {
		/* 4788 */OracleConnection localOracleConnection = null;

		/* 4794 */if (paramConnection != null) {
			/* 4796 */localOracleConnection = unwrapCompletely((oracle.jdbc.OracleConnection) paramConnection);
		}

		/* 4803 */return localOracleConnection;
	}

	public oracle.jdbc.internal.OracleConnection physicalConnectionWithin() {
		/* 4808 */return this;
	}

	public long getTdoCState(String paramString1, String paramString2) throws SQLException {
		/* 4814 */return 0L;
	}

	public void getOracleTypeADT(OracleTypeADT paramOracleTypeADT) throws SQLException {
	}

	public Datum toDatum(CustomDatum paramCustomDatum) throws SQLException {
		/* 4822 */return paramCustomDatum.toDatum(this);
	}

	public short getNCharSet() {
		/* 4827 */return this.conversion.getNCharSetId();
	}

	public ResultSet newArrayDataResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap) throws SQLException {
		/* 4833 */return new ArrayDataResultSet(this, paramArrayOfDatum, paramLong, paramInt, paramMap);
	}

	public ResultSet newArrayDataResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap) throws SQLException {
		/* 4839 */return new ArrayDataResultSet(this, paramARRAY, paramLong, paramInt, paramMap);
	}

	public ResultSet newArrayLocatorResultSet(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap) throws SQLException {
		/* 4847 */return new ArrayLocatorResultSet(this, paramArrayDescriptor, paramArrayOfByte, paramLong, paramInt, paramMap);
	}

	public ResultSetMetaData newStructMetaData(StructDescriptor paramStructDescriptor) throws SQLException {
		/* 4853 */return new StructMetaData(paramStructDescriptor);
	}

	public int CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar) throws SQLException {
		/* 4859 */int[] arrayOfInt = new int[1];

		/* 4861 */arrayOfInt[0] = paramInt;

		/* 4863 */return this.conversion.CHARBytesToJavaChars(paramArrayOfByte, 0, paramArrayOfChar, 0, arrayOfInt, paramArrayOfChar.length);
	}

	public int NCHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar) throws SQLException {
		/* 4870 */int[] arrayOfInt = new int[1];

		/* 4872 */return this.conversion.NCHARBytesToJavaChars(paramArrayOfByte, 0, paramArrayOfChar, 0, arrayOfInt, paramArrayOfChar.length);
	}

	public boolean IsNCharFixedWith() {
		/* 4878 */return this.conversion.IsNCharFixedWith();
	}

	public short getDriverCharSet() {
		/* 4883 */return this.conversion.getClientCharSet();
	}

	public int getMaxCharSize() throws SQLException {
		/* 4888 */DatabaseError.throwSqlException(58);

		/* 4891 */return -1;
	}

	public int getMaxCharbyteSize() {
		/* 4896 */return this.conversion.getMaxCharbyteSize();
	}

	public int getMaxNCharbyteSize() {
		/* 4901 */return this.conversion.getMaxNCharbyteSize();
	}

	public boolean isCharSetMultibyte(short paramShort) {
		/* 4906 */return DBConversion.isCharSetMultibyte(paramShort);
	}

	public int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 4912 */return this.conversion.javaCharsToCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
	}

	public int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 4918 */return this.conversion.javaCharsToNCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
	}

	public abstract void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection) throws SQLException;

	final void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection, String paramString) throws SQLException {
		/* 4928 */Hashtable localHashtable = new Hashtable();

		/* 4930 */localHashtable.put("obj_type_map", this.javaObjectMap);

		/* 4932 */Properties localProperties = new Properties();

		/* 4934 */localProperties.put("user", this.user);
		/* 4935 */localProperties.put("password", paramString);

		/* 4937 */localProperties.put("connection_url", this.url);
		/* 4938 */localProperties.put("connect_auto_commit", "" + this.autoCommitSet);

		/* 4941 */localProperties.put("trans_isolation", "" + this.txnLevel);

		/* 4943 */if (getStatementCacheSize() != -1) {
			/* 4945 */localProperties.put("stmt_cache_size", "" + getStatementCacheSize());

			/* 4947 */localProperties.put("implicit_cache_enabled", "" + getImplicitCachingEnabled());

			/* 4949 */localProperties.put("explict_cache_enabled", "" + getExplicitCachingEnabled());
		}

		/* 4953 */localProperties.put("defaultExecuteBatch", "" + this.defaultBatch);
		/* 4954 */localProperties.put("prefetch", "" + this.defaultRowPrefetch);
		/* 4955 */localProperties.put("remarks", "" + this.reportRemarks);
		/* 4956 */localProperties.put("AccumulateBatchResult", "" + this.accumulateBatchResult);
		/* 4957 */localProperties.put("oracle.jdbc.J2EE13Compliant", "" + this.j2ee13Compliant);
		/* 4958 */localProperties.put("processEscapes", "" + this.processEscapes);

		/* 4960 */localProperties.put("restrictGetTables", "" + this.restrictGetTables);
		/* 4961 */localProperties.put("synonyms", "" + this.includeSynonyms);
		/* 4962 */localProperties.put("fixedString", "" + this.defaultFixedString);

		/* 4964 */localHashtable.put("connection_properties", localProperties);

		/* 4966 */paramOraclePooledConnection.setProperties(localHashtable);
	}

	public Properties getDBAccessProperties() throws SQLException {
		/* 4990 */DatabaseError.throwSqlException(23);

		/* 4992 */return null;
	}

	public Properties getOCIHandles() throws SQLException {
		/* 5005 */DatabaseError.throwSqlException(23);

		/* 5007 */return null;
	}

	abstract void logon() throws SQLException;

	void logoff() throws SQLException {
	}

	abstract void open(OracleStatement paramOracleStatement) throws SQLException;

	abstract void doCancel() throws SQLException;

	abstract void doSetAutoCommit(boolean paramBoolean) throws SQLException;

	abstract void doCommit() throws SQLException;

	abstract void doRollback() throws SQLException;

	abstract String doGetDatabaseProductVersion() throws SQLException;

	abstract short doGetVersionNumber() throws SQLException;

	int getDefaultStreamChunkSize() {
		/* 5104 */return this.streamChunkSize;
	}

	abstract OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement) throws SQLException;

	public oracle.jdbc.internal.OracleStatement refCursorCursorToStatement(int paramInt) throws SQLException {
		/* 5113 */DatabaseError.throwSqlException(23);

		/* 5115 */return null;
	}

	public Connection getLogicalConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean) throws SQLException {
		/* 5125 */if ((this.logicalConnectionAttached != null) || (paramOraclePooledConnection.getPhysicalHandle() != this)) {
			/* 5126 */DatabaseError.throwSqlException(143);
		}
		/* 5128 */LogicalConnection localLogicalConnection = new LogicalConnection(paramOraclePooledConnection, this, paramBoolean);

		/* 5133 */this.logicalConnectionAttached = localLogicalConnection;

		/* 5135 */return localLogicalConnection;
	}

	public void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt) throws SQLException {
	}

	public CLOB createClob(byte[] paramArrayOfByte) throws SQLException {
		/* 5145 */return new CLOB(this, paramArrayOfByte);
	}

	public CLOB createClob(byte[] paramArrayOfByte, short paramShort) throws SQLException {
		/* 5151 */return new CLOB(this, paramArrayOfByte, paramShort);
	}

	public BLOB createBlob(byte[] paramArrayOfByte) throws SQLException {
		/* 5156 */return new BLOB(this, paramArrayOfByte);
	}

	public BFILE createBfile(byte[] paramArrayOfByte) throws SQLException {
		/* 5161 */return new BFILE(this, paramArrayOfByte);
	}

	public boolean isDescriptorSharable(oracle.jdbc.internal.OracleConnection paramOracleConnection) throws SQLException {
		/* 5175 */PhysicalConnection localPhysicalConnection1 = this;
		/* 5176 */PhysicalConnection localPhysicalConnection2 = (PhysicalConnection) paramOracleConnection.getPhysicalConnection();

		/* 5178 */return (localPhysicalConnection1 == localPhysicalConnection2) || (localPhysicalConnection1.url.equals(localPhysicalConnection2.url))
				|| ((localPhysicalConnection2.protocol != null) && (localPhysicalConnection2.protocol.equals("kprb")));
	}

	boolean useLittleEndianSetCHARBinder() throws SQLException {
		/* 5185 */return false;
	}

	public void setPlsqlWarnings(String paramString) throws SQLException {
		/* 5204 */if (paramString == null) {
			/* 5205 */DatabaseError.throwSqlException(68);
		}
		/* 5207 */if ((paramString != null) && ((paramString = paramString.trim()).length() > 0) && (!OracleSql.isValidPlsqlWarning(paramString))) {
			/* 5210 */DatabaseError.throwSqlException(68);
		}
		/* 5212 */String str1 = "ALTER SESSION SET PLSQL_WARNINGS=" + paramString;

		/* 5214 */String str2 = "ALTER SESSION SET EVENTS='10933 TRACE NAME CONTEXT LEVEL 32768'";

		/* 5217 */Statement localStatement = null;
		try {
			/* 5220 */localStatement = createStatement(-1, -1);

			/* 5222 */localStatement.execute(str1);

			/* 5224 */if (paramString.equals("'DISABLE:ALL'")) {
				/* 5226 */this.plsqlCompilerWarnings = false;
			} else {
				/* 5230 */localStatement.execute(str2);

				/* 5232 */this.plsqlCompilerWarnings = true;
			}
		} finally {
			/* 5236 */if (localStatement != null)
				/* 5237 */localStatement.close();
		}
	}

	void internalClose() throws SQLException {
		/* 5248 */this.lifecycle = 4;
	}

	public XAResource getXAResource() throws SQLException {
		/* 5262 */DatabaseError.throwSqlException(164);

		/* 5264 */return null;
	}

	protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo) throws SQLException {
		/* 5276 */DatabaseError.throwSqlException(23);
	}

	public void setApplicationContext(String paramString1, String paramString2, String paramString3) throws SQLException {
		/* 5289 */if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null)) {
			/* 5292 */throw new NullPointerException();
		}
		/* 5294 */if (paramString1.equals("")) {
			/* 5295 */DatabaseError.throwSqlException(170);
		}

		/* 5298 */if (paramString1.compareToIgnoreCase("CLIENTCONTEXT") != 0) {
			/* 5299 */DatabaseError.throwSqlException(174);
		}

		/* 5302 */if (paramString2.length() > 30) {
			/* 5303 */DatabaseError.throwSqlException(171);
		}

		/* 5306 */if (paramString3.length() > 4000) {
			/* 5307 */DatabaseError.throwSqlException(172);
		}

		/* 5310 */doSetApplicationContext(paramString1, paramString2, paramString3);
	}

	void doSetApplicationContext(String paramString1, String paramString2, String paramString3) throws SQLException {
		/* 5323 */DatabaseError.throwSqlException(23);
	}

	public void clearAllApplicationContext(String paramString) throws SQLException {
		/* 5334 */if (paramString == null)
			/* 5335 */throw new NullPointerException();
		/* 5336 */if (paramString.equals("")) {
			/* 5337 */DatabaseError.throwSqlException(170);
		}

		/* 5340 */doClearAllApplicationContext(paramString);
	}

	void doClearAllApplicationContext(String paramString) throws SQLException {
		/* 5350 */DatabaseError.throwSqlException(23);
	}

	public boolean isV8Compatible() throws SQLException {
		/* 5367 */return this.v8Compatible;
	}

	static {
		/* 263 */endToEndMaxLength[0] = 32;
		/* 264 */endToEndMaxLength[1] = 64;
		/* 265 */endToEndMaxLength[2] = 64;
		/* 266 */endToEndMaxLength[3] = 48;

		/* 5374 */_Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.PhysicalConnection JD-Core Version: 0.6.0
 */