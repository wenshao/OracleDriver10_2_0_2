package oracle.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLXML;
import java.sql.Struct;

import java.sql.Connection;

import java.sql.DatabaseMetaData;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.sql.SQLWarning;

import java.sql.Savepoint;

import java.sql.Statement;

import java.util.Map;

import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheCallback;

public class OracleConnectionWrapper implements OracleConnection {
	protected OracleConnection connection;
	/* 679 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";

	public OracleConnectionWrapper() {
	}

	public OracleConnectionWrapper(OracleConnection paramOracleConnection) {
		/* 41 */this.connection = paramOracleConnection;

		/* 43 */paramOracleConnection.setWrapper(this);
	}

	public OracleConnection unwrap() {
		/* 53 */return this.connection;
	}

	public oracle.jdbc.internal.OracleConnection physicalConnectionWithin() {
		/* 61 */return this.connection.physicalConnectionWithin();
	}

	public void setWrapper(OracleConnection paramOracleConnection) {
		/* 76 */this.connection.setWrapper(paramOracleConnection);
	}

	public Statement createStatement() throws SQLException {
		/* 87 */return this.connection.createStatement();
	}

	public PreparedStatement prepareStatement(String paramString) throws SQLException {
		/* 92 */return this.connection.prepareStatement(paramString);
	}

	public CallableStatement prepareCall(String paramString) throws SQLException {
		/* 97 */return this.connection.prepareCall(paramString);
	}

	public String nativeSQL(String paramString) throws SQLException {
		/* 102 */return this.connection.nativeSQL(paramString);
	}

	public void setAutoCommit(boolean paramBoolean) throws SQLException {
		/* 107 */this.connection.setAutoCommit(paramBoolean);
	}

	public boolean getAutoCommit() throws SQLException {
		/* 112 */return this.connection.getAutoCommit();
	}

	public void commit() throws SQLException {
		/* 117 */this.connection.commit();
	}

	public void rollback() throws SQLException {
		/* 122 */this.connection.rollback();
	}

	public void close() throws SQLException {
		/* 127 */this.connection.close();
	}

	public boolean isClosed() throws SQLException {
		/* 132 */return this.connection.isClosed();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		/* 137 */return this.connection.getMetaData();
	}

	public void setReadOnly(boolean paramBoolean) throws SQLException {
		/* 142 */this.connection.setReadOnly(paramBoolean);
	}

	public boolean isReadOnly() throws SQLException {
		/* 147 */return this.connection.isReadOnly();
	}

	public void setCatalog(String paramString) throws SQLException {
		/* 152 */this.connection.setCatalog(paramString);
	}

	public String getCatalog() throws SQLException {
		/* 157 */return this.connection.getCatalog();
	}

	public void setTransactionIsolation(int paramInt) throws SQLException {
		/* 162 */this.connection.setTransactionIsolation(paramInt);
	}

	public int getTransactionIsolation() throws SQLException {
		/* 167 */return this.connection.getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException {
		/* 172 */return this.connection.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		/* 177 */this.connection.clearWarnings();
	}

	public Statement createStatement(int paramInt1, int paramInt2) throws SQLException {
		/* 183 */return this.connection.createStatement(paramInt1, paramInt2);
	}

	public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2) throws SQLException {
		/* 189 */return this.connection.prepareStatement(paramString, paramInt1, paramInt2);
	}

	public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) throws SQLException {
		/* 196 */return this.connection.prepareCall(paramString, paramInt1, paramInt2);
	}

	public Map getTypeMap() throws SQLException {
		/* 201 */return this.connection.getTypeMap();
	}

	public void setTypeMap(Map paramMap) throws SQLException {
		/* 206 */this.connection.setTypeMap(paramMap);
	}

	public boolean isProxySession() {
		/* 215 */return this.connection.isProxySession();
	}

	public void openProxySession(int paramInt, Properties paramProperties) throws SQLException {
		/* 221 */this.connection.openProxySession(paramInt, paramProperties);
	}

	public void archive(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 226 */this.connection.archive(paramInt1, paramInt2, paramString);
	}

	public boolean getAutoClose() throws SQLException {
		/* 231 */return this.connection.getAutoClose();
	}

	public CallableStatement getCallWithKey(String paramString) throws SQLException {
		/* 236 */return this.connection.getCallWithKey(paramString);
	}

	public int getDefaultExecuteBatch() {
		/* 241 */return this.connection.getDefaultExecuteBatch();
	}

	public int getDefaultRowPrefetch() {
		/* 246 */return this.connection.getDefaultRowPrefetch();
	}

	public Object getDescriptor(String paramString) {
		/* 251 */return this.connection.getDescriptor(paramString);
	}

	public String[] getEndToEndMetrics() throws SQLException {
		/* 256 */return this.connection.getEndToEndMetrics();
	}

	public short getEndToEndECIDSequenceNumber() throws SQLException {
		/* 261 */return this.connection.getEndToEndECIDSequenceNumber();
	}

	public boolean getIncludeSynonyms() {
		/* 266 */return this.connection.getIncludeSynonyms();
	}

	public boolean getRestrictGetTables() {
		/* 271 */return this.connection.getRestrictGetTables();
	}

	public boolean getImplicitCachingEnabled() throws SQLException {
		/* 276 */return this.connection.getImplicitCachingEnabled();
	}

	public boolean getExplicitCachingEnabled() throws SQLException {
		/* 281 */return this.connection.getExplicitCachingEnabled();
	}

	public Object getJavaObject(String paramString) throws SQLException {
		/* 287 */return this.connection.getJavaObject(paramString);
	}

	public boolean getRemarksReporting() {
		/* 292 */return this.connection.getRemarksReporting();
	}

	public String getSQLType(Object paramObject) throws SQLException {
		/* 297 */return this.connection.getSQLType(paramObject);
	}

	public int getStmtCacheSize() {
		/* 302 */return this.connection.getStmtCacheSize();
	}

	public int getStatementCacheSize() throws SQLException {
		/* 307 */return this.connection.getStatementCacheSize();
	}

	public PreparedStatement getStatementWithKey(String paramString) throws SQLException {
		/* 312 */return this.connection.getStatementWithKey(paramString);
	}

	public short getStructAttrCsId() throws SQLException {
		/* 317 */return this.connection.getStructAttrCsId();
	}

	public String getUserName() throws SQLException {
		/* 322 */return this.connection.getUserName();
	}

	public boolean getUsingXAFlag() {
		/* 327 */return this.connection.getUsingXAFlag();
	}

	public boolean getXAErrorFlag() {
		/* 332 */return this.connection.getXAErrorFlag();
	}

	public OracleSavepoint oracleSetSavepoint() throws SQLException {
		/* 337 */return this.connection.oracleSetSavepoint();
	}

	public OracleSavepoint oracleSetSavepoint(String paramString) throws SQLException {
		/* 342 */return this.connection.oracleSetSavepoint(paramString);
	}

	public void oracleRollback(OracleSavepoint paramOracleSavepoint) throws SQLException {
		/* 347 */this.connection.oracleRollback(paramOracleSavepoint);
	}

	public void oracleReleaseSavepoint(OracleSavepoint paramOracleSavepoint) throws SQLException {
		/* 353 */this.connection.oracleReleaseSavepoint(paramOracleSavepoint);
	}

	public int pingDatabase(int paramInt) throws SQLException {
		/* 360 */return this.connection.pingDatabase(paramInt);
	}

	public void purgeExplicitCache() throws SQLException {
		/* 365 */this.connection.purgeExplicitCache();
	}

	public void purgeImplicitCache() throws SQLException {
		/* 370 */this.connection.purgeImplicitCache();
	}

	public void putDescriptor(String paramString, Object paramObject) throws SQLException {
		/* 375 */this.connection.putDescriptor(paramString, paramObject);
	}

	public void registerSQLType(String paramString, Class paramClass) throws SQLException {
		/* 381 */this.connection.registerSQLType(paramString, paramClass);
	}

	public void registerSQLType(String paramString1, String paramString2) throws SQLException {
		/* 387 */this.connection.registerSQLType(paramString1, paramString2);
	}

	public void setAutoClose(boolean paramBoolean) throws SQLException {
		/* 392 */this.connection.setAutoClose(paramBoolean);
	}

	public void setDefaultExecuteBatch(int paramInt) throws SQLException {
		/* 397 */this.connection.setDefaultExecuteBatch(paramInt);
	}

	public void setDefaultRowPrefetch(int paramInt) throws SQLException {
		/* 402 */this.connection.setDefaultRowPrefetch(paramInt);
	}

	public void setEndToEndMetrics(String[] paramArrayOfString, short paramShort) throws SQLException {
		/* 408 */this.connection.setEndToEndMetrics(paramArrayOfString, paramShort);
	}

	public void setExplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 413 */this.connection.setExplicitCachingEnabled(paramBoolean);
	}

	public void setImplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 418 */this.connection.setImplicitCachingEnabled(paramBoolean);
	}

	public void setIncludeSynonyms(boolean paramBoolean) {
		/* 423 */this.connection.setIncludeSynonyms(paramBoolean);
	}

	public void setRemarksReporting(boolean paramBoolean) {
		/* 428 */this.connection.setRemarksReporting(paramBoolean);
	}

	public void setRestrictGetTables(boolean paramBoolean) {
		/* 433 */this.connection.setRestrictGetTables(paramBoolean);
	}

	public void setStmtCacheSize(int paramInt) throws SQLException {
		/* 438 */this.connection.setStmtCacheSize(paramInt);
	}

	public void setStatementCacheSize(int paramInt) throws SQLException {
		/* 443 */this.connection.setStatementCacheSize(paramInt);
	}

	public void setStmtCacheSize(int paramInt, boolean paramBoolean) throws SQLException {
		/* 449 */this.connection.setStmtCacheSize(paramInt, paramBoolean);
	}

	public void setUsingXAFlag(boolean paramBoolean) {
		/* 454 */this.connection.setUsingXAFlag(paramBoolean);
	}

	public void setXAErrorFlag(boolean paramBoolean) {
		/* 459 */this.connection.setXAErrorFlag(paramBoolean);
	}

	public void shutdown(int paramInt) throws SQLException {
		/* 464 */this.connection.shutdown(paramInt);
	}

	public void startup(String paramString, int paramInt) throws SQLException {
		/* 469 */this.connection.startup(paramString, paramInt);
	}

	public PreparedStatement prepareStatementWithKey(String paramString) throws SQLException {
		/* 475 */return this.connection.prepareStatementWithKey(paramString);
	}

	public CallableStatement prepareCallWithKey(String paramString) throws SQLException {
		/* 480 */return this.connection.prepareCallWithKey(paramString);
	}

	public void setCreateStatementAsRefCursor(boolean paramBoolean) {
		/* 486 */this.connection.setCreateStatementAsRefCursor(paramBoolean);
	}

	public boolean getCreateStatementAsRefCursor() {
		/* 491 */return this.connection.getCreateStatementAsRefCursor();
	}

	public void setSessionTimeZone(String paramString) throws SQLException {
		/* 496 */this.connection.setSessionTimeZone(paramString);
	}

	public String getSessionTimeZone() {
		/* 501 */return this.connection.getSessionTimeZone();
	}

	public Connection _getPC() {
		/* 506 */return this.connection._getPC();
	}

	public boolean isLogicalConnection() {
		/* 511 */return this.connection.isLogicalConnection();
	}

	public void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject) throws SQLException {
		/* 517 */this.connection.registerTAFCallback(paramOracleOCIFailover, paramObject);
	}

	public Properties getProperties() {
		/* 523 */return this.connection.getProperties();
	}

	public void close(Properties paramProperties) throws SQLException {
		/* 529 */this.connection.close(paramProperties);
	}

	public void close(int paramInt) throws SQLException {
		/* 534 */this.connection.close(paramInt);
	}

	public void applyConnectionAttributes(Properties paramProperties) throws SQLException {
		/* 540 */this.connection.applyConnectionAttributes(paramProperties);
	}

	public Properties getConnectionAttributes() throws SQLException {
		/* 545 */return this.connection.getConnectionAttributes();
	}

	public Properties getUnMatchedConnectionAttributes() throws SQLException {
		/* 551 */return this.connection.getUnMatchedConnectionAttributes();
	}

	public void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt) throws SQLException {
		/* 558 */this.connection.registerConnectionCacheCallback(paramOracleConnectionCacheCallback, paramObject, paramInt);
	}

	public void setConnectionReleasePriority(int paramInt) throws SQLException {
		/* 563 */this.connection.setConnectionReleasePriority(paramInt);
	}

	public int getConnectionReleasePriority() throws SQLException {
		/* 568 */return this.connection.getConnectionReleasePriority();
	}

	public void setPlsqlWarnings(String paramString) throws SQLException {
		/* 573 */this.connection.setPlsqlWarnings(paramString);
	}

	public void setHoldability(int paramInt) throws SQLException {
		/* 578 */this.connection.setHoldability(paramInt);
	}

	public int getHoldability() throws SQLException {
		/* 583 */return this.connection.getHoldability();
	}

	public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 588 */return this.connection.createStatement(paramInt1, paramInt2, paramInt3);
	}

	public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 596 */return this.connection.prepareStatement(paramString, paramInt1, paramInt2, paramInt3);
	}

	public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 605 */return this.connection.prepareCall(paramString, paramInt1, paramInt2, paramInt3);
	}

	public synchronized Savepoint setSavepoint() throws SQLException {
		/* 626 */return this.connection.setSavepoint();
	}

	public synchronized Savepoint setSavepoint(String paramString) throws SQLException {
		/* 632 */return this.connection.setSavepoint(paramString);
	}

	public synchronized void rollback(Savepoint paramSavepoint) throws SQLException {
		/* 638 */this.connection.rollback(paramSavepoint);
	}

	public synchronized void releaseSavepoint(Savepoint paramSavepoint) throws SQLException {
		/* 644 */this.connection.releaseSavepoint(paramSavepoint);
	}

	public PreparedStatement prepareStatement(String paramString, int paramInt) throws SQLException {
		/* 655 */return this.connection.prepareStatement(paramString, paramInt);
	}

	public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfInt) throws SQLException {
		/* 661 */return this.connection.prepareStatement(paramString, paramArrayOfInt);
	}

	public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString) throws SQLException {
		/* 667 */return this.connection.prepareStatement(paramString, paramArrayOfString);
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
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
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.OracleConnectionWrapper
 * JD-Core Version: 0.6.0
 */