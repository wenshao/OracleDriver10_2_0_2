package oracle.jdbc.internal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import javax.transaction.xa.XAResource;
import oracle.jdbc.oracore.OracleTypeADT;
import oracle.jdbc.oracore.OracleTypeCLOB;
import oracle.jdbc.pool.OracleConnectionCacheCallback;
import oracle.jdbc.pool.OraclePooledConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.BfileDBAccess;
import oracle.sql.BlobDBAccess;
import oracle.sql.CLOB;
import oracle.sql.ClobDBAccess;
import oracle.sql.CustomDatum;
import oracle.sql.Datum;
import oracle.sql.StructDescriptor;

public abstract interface OracleConnection extends oracle.jdbc.OracleConnection {
	public static final int CHAR_TO_ASCII = 0;
	public static final int CHAR_TO_UNICODE = 1;
	public static final int RAW_TO_ASCII = 2;
	public static final int RAW_TO_UNICODE = 3;
	public static final int UNICODE_TO_CHAR = 4;
	public static final int ASCII_TO_CHAR = 5;
	public static final int NONE = 6;
	public static final int JAVACHAR_TO_CHAR = 7;
	public static final int RAW_TO_JAVACHAR = 8;
	public static final int CHAR_TO_JAVACHAR = 9;
	public static final int GLOBAL_TXN = 1;
	public static final int NO_GLOBAL_TXN = 0;

	public abstract short getStructAttrNCsId() throws SQLException;

	public abstract Map getTypeMap() throws SQLException;

	public abstract Properties getDBAccessProperties() throws SQLException;

	public abstract Properties getOCIHandles() throws SQLException;

	public abstract String getDatabaseProductVersion() throws SQLException;

	public abstract void cancel() throws SQLException;

	public abstract String getURL() throws SQLException;

	public abstract short getVersionNumber() throws SQLException;

	public abstract Map getJavaObjectTypeMap();

	public abstract void setJavaObjectTypeMap(Map paramMap);

	public abstract BfileDBAccess createBfileDBAccess() throws SQLException;

	public abstract BlobDBAccess createBlobDBAccess() throws SQLException;

	public abstract ClobDBAccess createClobDBAccess() throws SQLException;

	public abstract void setDefaultFixedString(boolean paramBoolean);

	public abstract boolean getDefaultFixedString();

	public abstract oracle.jdbc.OracleConnection getWrapper();

	public abstract Class classForNameAndSchema(String paramString1,
			String paramString2) throws ClassNotFoundException;

	public abstract void setFDO(byte[] paramArrayOfByte) throws SQLException;

	public abstract byte[] getFDO(boolean paramBoolean) throws SQLException;

	public abstract boolean getBigEndian() throws SQLException;

	public abstract Object getDescriptor(byte[] paramArrayOfByte);

	public abstract void putDescriptor(byte[] paramArrayOfByte,
			Object paramObject) throws SQLException;

	public abstract OracleConnection getPhysicalConnection();

	public abstract void removeDescriptor(String paramString);

	public abstract void removeAllDescriptor();

	public abstract int numberOfDescriptorCacheEntries();

	public abstract Enumeration descriptorCacheKeys();

	public abstract long getTdoCState(String paramString1, String paramString2)
			throws SQLException;

	public abstract Datum toDatum(CustomDatum paramCustomDatum)
			throws SQLException;

	public abstract short getDbCsId() throws SQLException;

	public abstract short getJdbcCsId() throws SQLException;

	public abstract short getNCharSet();

	public abstract ResultSet newArrayDataResultSet(Datum[] paramArrayOfDatum,
			long paramLong, int paramInt, Map paramMap) throws SQLException;

	public abstract ResultSet newArrayDataResultSet(ARRAY paramARRAY,
			long paramLong, int paramInt, Map paramMap) throws SQLException;

	public abstract ResultSet newArrayLocatorResultSet(
			ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte,
			long paramLong, int paramInt, Map paramMap) throws SQLException;

	public abstract ResultSetMetaData newStructMetaData(
			StructDescriptor paramStructDescriptor) throws SQLException;

	public abstract void getForm(OracleTypeADT paramOracleTypeADT,
			OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
			throws SQLException;

	public abstract int CHARBytesToJavaChars(byte[] paramArrayOfByte,
			int paramInt, char[] paramArrayOfChar) throws SQLException;

	public abstract int NCHARBytesToJavaChars(byte[] paramArrayOfByte,
			int paramInt, char[] paramArrayOfChar) throws SQLException;

	public abstract boolean IsNCharFixedWith();

	public abstract short getDriverCharSet();

	public abstract int getC2SNlsRatio();

	public abstract int getMaxCharSize() throws SQLException;

	public abstract int getMaxCharbyteSize();

	public abstract int getMaxNCharbyteSize();

	public abstract boolean isCharSetMultibyte(short paramShort);

	public abstract int javaCharsToCHARBytes(char[] paramArrayOfChar,
			int paramInt, byte[] paramArrayOfByte) throws SQLException;

	public abstract int javaCharsToNCHARBytes(char[] paramArrayOfChar,
			int paramInt, byte[] paramArrayOfByte) throws SQLException;

	public abstract void setStartTime(long paramLong) throws SQLException;

	public abstract long getStartTime() throws SQLException;

	public abstract boolean isStatementCacheInitialized();

	public abstract void getPropertyForPooledConnection(
			OraclePooledConnection paramOraclePooledConnection)
			throws SQLException;

	public abstract void setTypeMap(Map paramMap) throws SQLException;

	public abstract String getProtocolType();

	public abstract Connection getLogicalConnection(
			OraclePooledConnection paramOraclePooledConnection,
			boolean paramBoolean) throws SQLException;

	public abstract void setTxnMode(int paramInt);

	public abstract int getTxnMode();

	public abstract int getHeapAllocSize() throws SQLException;

	public abstract int getOCIEnvHeapAllocSize() throws SQLException;

	public abstract void setAbandonedTimeoutEnabled(boolean paramBoolean)
			throws SQLException;

	public abstract int getHeartbeatNoChangeCount() throws SQLException;

	public abstract void closeInternal(boolean paramBoolean)
			throws SQLException;

	public abstract OracleConnectionCacheCallback getConnectionCacheCallbackObj()
			throws SQLException;

	public abstract Object getConnectionCacheCallbackPrivObj()
			throws SQLException;

	public abstract int getConnectionCacheCallbackFlag() throws SQLException;

	public abstract Properties getServerSessionInfo() throws SQLException;

	public abstract CLOB createClob(byte[] paramArrayOfByte)
			throws SQLException;

	public abstract CLOB createClob(byte[] paramArrayOfByte, short paramShort)
			throws SQLException;

	public abstract BLOB createBlob(byte[] paramArrayOfByte)
			throws SQLException;

	public abstract BFILE createBfile(byte[] paramArrayOfByte)
			throws SQLException;

	public abstract boolean isDescriptorSharable(
			OracleConnection paramOracleConnection) throws SQLException;

	public abstract OracleStatement refCursorCursorToStatement(int paramInt)
			throws SQLException;

	public abstract XAResource getXAResource() throws SQLException;

	public abstract void abort() throws SQLException;

	public abstract void setApplicationContext(String paramString1,
			String paramString2, String paramString3) throws SQLException;

	public abstract void clearAllApplicationContext(String paramString)
			throws SQLException;

	public abstract boolean isV8Compatible() throws SQLException;
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.internal.OracleConnection JD-Core Version: 0.6.0
 */