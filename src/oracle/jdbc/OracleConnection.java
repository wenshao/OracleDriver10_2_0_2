package oracle.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleConnectionCacheCallback;

public abstract interface OracleConnection extends Connection
{
  public static final int DATABASE_OK = 0;
  public static final int DATABASE_CLOSED = -1;
  public static final int DATABASE_NOTOK = -2;
  public static final int DATABASE_TIMEOUT = -3;
  public static final int INVALID_CONNECTION = 4096;
  public static final int PROXY_SESSION = 1;
  public static final int ABANDONED_CONNECTION_CALLBACK = 1;
  public static final int RELEASE_CONNECTION_CALLBACK = 2;
  public static final int ALL_CONNECTION_CALLBACKS = 4;
  public static final int CONNECTION_RELEASE_LOCKED = 256;
  public static final int CONNECTION_RELEASE_LOW = 512;
  public static final int CONNECTION_RELEASE_HIGH = 1024;
  public static final int PROXYTYPE_USER_NAME = 1;
  public static final int PROXYTYPE_DISTINGUISHED_NAME = 2;
  public static final int PROXYTYPE_CERTIFICATE = 3;
  public static final String PROXY_USER_NAME = "PROXY_USER_NAME";
  public static final String PROXY_USER_PASSWORD = "PROXY_USER_PASSWORD";
  public static final String PROXY_DISTINGUISHED_NAME = "PROXY_DISTINGUISHED_NAME";
  public static final String PROXY_CERTIFICATE = "PROXY_CERTIFICATE";
  public static final String PROXY_ROLES = "PROXY_ROLES";
  public static final int END_TO_END_ACTION_INDEX = 0;
  public static final int END_TO_END_CLIENTID_INDEX = 1;
  public static final int END_TO_END_ECID_INDEX = 2;
  public static final int END_TO_END_MODULE_INDEX = 3;
  public static final int END_TO_END_STATE_INDEX_MAX = 4;
  public static final int CACHE_SIZE_NOT_SET = -1;

  /** @deprecated */
  public abstract void archive(int paramInt1, int paramInt2, String paramString)
    throws SQLException;

  public abstract void openProxySession(int paramInt, Properties paramProperties)
    throws SQLException;

  public abstract boolean getAutoClose()
    throws SQLException;

  public abstract int getDefaultExecuteBatch();

  public abstract int getDefaultRowPrefetch();

  public abstract Object getDescriptor(String paramString);

  public abstract String[] getEndToEndMetrics()
    throws SQLException;

  public abstract short getEndToEndECIDSequenceNumber()
    throws SQLException;

  public abstract boolean getIncludeSynonyms();

  public abstract boolean getRestrictGetTables();

  /** @deprecated */
  public abstract Object getJavaObject(String paramString)
    throws SQLException;

  public abstract boolean getRemarksReporting();

  /** @deprecated */
  public abstract String getSQLType(Object paramObject)
    throws SQLException;

  /** @deprecated */
  public abstract int getStmtCacheSize();

  public abstract short getStructAttrCsId()
    throws SQLException;

  public abstract String getUserName()
    throws SQLException;

  /** @deprecated */
  public abstract boolean getUsingXAFlag();

  /** @deprecated */
  public abstract boolean getXAErrorFlag();

  public abstract int pingDatabase(int paramInt)
    throws SQLException;

  public abstract void putDescriptor(String paramString, Object paramObject)
    throws SQLException;

  /** @deprecated */
  public abstract void registerSQLType(String paramString, Class paramClass)
    throws SQLException;

  /** @deprecated */
  public abstract void registerSQLType(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setAutoClose(boolean paramBoolean)
    throws SQLException;

  public abstract void setDefaultExecuteBatch(int paramInt)
    throws SQLException;

  public abstract void setDefaultRowPrefetch(int paramInt)
    throws SQLException;

  public abstract void setEndToEndMetrics(String[] paramArrayOfString, short paramShort)
    throws SQLException;

  public abstract void setIncludeSynonyms(boolean paramBoolean);

  public abstract void setRemarksReporting(boolean paramBoolean);

  public abstract void setRestrictGetTables(boolean paramBoolean);

  /** @deprecated */
  public abstract void setStmtCacheSize(int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract void setStmtCacheSize(int paramInt, boolean paramBoolean)
    throws SQLException;

  public abstract void setStatementCacheSize(int paramInt)
    throws SQLException;

  public abstract int getStatementCacheSize()
    throws SQLException;

  public abstract void setImplicitCachingEnabled(boolean paramBoolean)
    throws SQLException;

  public abstract boolean getImplicitCachingEnabled()
    throws SQLException;

  public abstract void setExplicitCachingEnabled(boolean paramBoolean)
    throws SQLException;

  public abstract boolean getExplicitCachingEnabled()
    throws SQLException;

  public abstract void purgeImplicitCache()
    throws SQLException;

  public abstract void purgeExplicitCache()
    throws SQLException;

  public abstract PreparedStatement getStatementWithKey(String paramString)
    throws SQLException;

  public abstract CallableStatement getCallWithKey(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract void setUsingXAFlag(boolean paramBoolean);

  /** @deprecated */
  public abstract void setXAErrorFlag(boolean paramBoolean);

  /** @deprecated */
  public abstract void shutdown(int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract void startup(String paramString, int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract PreparedStatement prepareStatementWithKey(String paramString)
    throws SQLException;

  /** @deprecated */
  public abstract CallableStatement prepareCallWithKey(String paramString)
    throws SQLException;

  public abstract void setCreateStatementAsRefCursor(boolean paramBoolean);

  public abstract boolean getCreateStatementAsRefCursor();

  public abstract void setSessionTimeZone(String paramString)
    throws SQLException;

  public abstract String getSessionTimeZone();

  public abstract Properties getProperties();

  public abstract Connection _getPC();

  public abstract boolean isLogicalConnection();

  public abstract void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject)
    throws SQLException;

  public abstract OracleConnection unwrap();

  public abstract void setWrapper(OracleConnection paramOracleConnection);

  public abstract oracle.jdbc.internal.OracleConnection physicalConnectionWithin();

  public abstract OracleSavepoint oracleSetSavepoint()
    throws SQLException;

  public abstract OracleSavepoint oracleSetSavepoint(String paramString)
    throws SQLException;

  public abstract void oracleRollback(OracleSavepoint paramOracleSavepoint)
    throws SQLException;

  public abstract void oracleReleaseSavepoint(OracleSavepoint paramOracleSavepoint)
    throws SQLException;

  public abstract void close(Properties paramProperties)
    throws SQLException;

  public abstract void close(int paramInt)
    throws SQLException;

  public abstract boolean isProxySession();

  public abstract void applyConnectionAttributes(Properties paramProperties)
    throws SQLException;

  public abstract Properties getConnectionAttributes()
    throws SQLException;

  public abstract Properties getUnMatchedConnectionAttributes()
    throws SQLException;

  public abstract void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt)
    throws SQLException;

  public abstract void setConnectionReleasePriority(int paramInt)
    throws SQLException;

  public abstract int getConnectionReleasePriority()
    throws SQLException;

  public abstract void setPlsqlWarnings(String paramString)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleConnection
 * JD-Core Version:    0.6.0
 */