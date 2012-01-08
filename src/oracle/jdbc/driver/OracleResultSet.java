/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Map;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.BFILE;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.CHAR;
/*      */ import oracle.sql.CLOB;
/*      */ import oracle.sql.CustomDatum;
/*      */ import oracle.sql.CustomDatumFactory;
/*      */ import oracle.sql.DATE;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.INTERVALDS;
/*      */ import oracle.sql.INTERVALYM;
/*      */ import oracle.sql.NUMBER;
/*      */ import oracle.sql.OPAQUE;
/*      */ import oracle.sql.ORAData;
/*      */ import oracle.sql.ORADataFactory;
/*      */ import oracle.sql.RAW;
/*      */ import oracle.sql.REF;
/*      */ import oracle.sql.ROWID;
/*      */ import oracle.sql.STRUCT;
/*      */ import oracle.sql.TIMESTAMP;
/*      */ import oracle.sql.TIMESTAMPLTZ;
/*      */ import oracle.sql.TIMESTAMPTZ;
/*      */ 
/*      */ public abstract class OracleResultSet
/*      */   implements oracle.jdbc.internal.OracleResultSet
/*      */ {
/*      */   static final boolean DEBUG = false;
/*      */   public static final int FETCH_FORWARD = 1000;
/*      */   public static final int FETCH_REVERSE = 1001;
/*      */   public static final int FETCH_UNKNOWN = 1002;
/*      */   public static final int TYPE_FORWARD_ONLY = 1003;
/*      */   public static final int TYPE_SCROLL_INSENSITIVE = 1004;
/*      */   public static final int TYPE_SCROLL_SENSITIVE = 1005;
/*      */   public static final int CONCUR_READ_ONLY = 1007;
/*      */   public static final int CONCUR_UPDATABLE = 1008;
/* 3046 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*      */ 
/*      */   int getFirstUserColumnIndex()
/*      */     throws SQLException
/*      */   {
/*   83 */     return 0;
/*      */   }
/*      */ 
/*      */   public abstract void closeStatementOnClose()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ResultSet getCursor(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Datum getOracleObject(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ROWID getROWID(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract NUMBER getNUMBER(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract DATE getDATE(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ARRAY getARRAY(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract STRUCT getSTRUCT(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract OPAQUE getOPAQUE(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract REF getREF(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract CHAR getCHAR(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract RAW getRAW(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract BLOB getBLOB(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract CLOB getCLOB(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract BFILE getBFILE(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract BFILE getBfile(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   /** @deprecated */
/*      */   public abstract CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory)
/*      */     throws SQLException;
/*      */ 
/*      */   public ResultSet getCursor(String paramString)
/*      */     throws SQLException
/*      */   {
/*  353 */     return getCursor(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public ROWID getROWID(String paramString)
/*      */     throws SQLException
/*      */   {
/*  367 */     return getROWID(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public NUMBER getNUMBER(String paramString)
/*      */     throws SQLException
/*      */   {
/*  386 */     return getNUMBER(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public DATE getDATE(String paramString)
/*      */     throws SQLException
/*      */   {
/*  400 */     return getDATE(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Datum getOracleObject(String paramString)
/*      */     throws SQLException
/*      */   {
/*  420 */     return getOracleObject(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public ARRAY getARRAY(String paramString)
/*      */     throws SQLException
/*      */   {
/*  435 */     return getARRAY(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public STRUCT getSTRUCT(String paramString)
/*      */     throws SQLException
/*      */   {
/*  451 */     return getSTRUCT(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public OPAQUE getOPAQUE(String paramString)
/*      */     throws SQLException
/*      */   {
/*  468 */     return getOPAQUE(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public REF getREF(String paramString)
/*      */     throws SQLException
/*      */   {
/*  484 */     return getREF(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public CHAR getCHAR(String paramString)
/*      */     throws SQLException
/*      */   {
/*  506 */     return getCHAR(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public RAW getRAW(String paramString)
/*      */     throws SQLException
/*      */   {
/*  521 */     return getRAW(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public BLOB getBLOB(String paramString)
/*      */     throws SQLException
/*      */   {
/*  537 */     return getBLOB(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public CLOB getCLOB(String paramString)
/*      */     throws SQLException
/*      */   {
/*  553 */     return getCLOB(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public BFILE getBFILE(String paramString)
/*      */     throws SQLException
/*      */   {
/*  573 */     return getBFILE(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public BFILE getBfile(String paramString)
/*      */     throws SQLException
/*      */   {
/*  593 */     return getBfile(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public CustomDatum getCustomDatum(String paramString, CustomDatumFactory paramCustomDatumFactory)
/*      */     throws SQLException
/*      */   {
/*  614 */     return getCustomDatum(findColumn(paramString), paramCustomDatumFactory);
/*      */   }
/*      */ 
/*      */   public ORAData getORAData(String paramString, ORADataFactory paramORADataFactory)
/*      */     throws SQLException
/*      */   {
/*  632 */     return getORAData(findColumn(paramString), paramORADataFactory);
/*      */   }
/*      */ 
/*      */   public abstract void updateOracleObject(int paramInt, Datum paramDatum)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateROWID(int paramInt, ROWID paramROWID)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateNUMBER(int paramInt, NUMBER paramNUMBER)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateDATE(int paramInt, DATE paramDATE)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateARRAY(int paramInt, ARRAY paramARRAY)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateOPAQUE(int paramInt, OPAQUE paramOPAQUE)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateREF(int paramInt, REF paramREF)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateCHAR(int paramInt, CHAR paramCHAR)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateRAW(int paramInt, RAW paramRAW)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBLOB(int paramInt, BLOB paramBLOB)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateCLOB(int paramInt, CLOB paramCLOB)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBFILE(int paramInt, BFILE paramBFILE)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBfile(int paramInt, BFILE paramBFILE)
/*      */     throws SQLException;
/*      */ 
/*      */   /** @deprecated */
/*      */   public abstract void updateCustomDatum(int paramInt, CustomDatum paramCustomDatum)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateORAData(int paramInt, ORAData paramORAData)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateRef(int paramInt, Ref paramRef)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateClob(int paramInt, Clob paramClob)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateArray(int paramInt, Array paramArray)
/*      */     throws SQLException;
/*      */ 
/*      */   public void updateROWID(String paramString, ROWID paramROWID)
/*      */     throws SQLException
/*      */   {
/*  945 */     updateROWID(findColumn(paramString), paramROWID);
/*      */   }
/*      */ 
/*      */   public void updateNUMBER(String paramString, NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/*  959 */     updateNUMBER(findColumn(paramString), paramNUMBER);
/*      */   }
/*      */ 
/*      */   public void updateDATE(String paramString, DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/*  973 */     updateDATE(findColumn(paramString), paramDATE);
/*      */   }
/*      */ 
/*      */   public void updateOracleObject(String paramString, Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/*  987 */     updateOracleObject(findColumn(paramString), paramDatum);
/*      */   }
/*      */ 
/*      */   public void updateARRAY(String paramString, ARRAY paramARRAY)
/*      */     throws SQLException
/*      */   {
/* 1001 */     updateARRAY(findColumn(paramString), paramARRAY);
/*      */   }
/*      */ 
/*      */   public void updateSTRUCT(String paramString, STRUCT paramSTRUCT)
/*      */     throws SQLException
/*      */   {
/* 1015 */     updateSTRUCT(findColumn(paramString), paramSTRUCT);
/*      */   }
/*      */ 
/*      */   public void updateOPAQUE(String paramString, OPAQUE paramOPAQUE)
/*      */     throws SQLException
/*      */   {
/* 1030 */     updateOPAQUE(findColumn(paramString), paramOPAQUE);
/*      */   }
/*      */ 
/*      */   public void updateREF(String paramString, REF paramREF)
/*      */     throws SQLException
/*      */   {
/* 1044 */     updateREF(findColumn(paramString), paramREF);
/*      */   }
/*      */ 
/*      */   public void updateCHAR(String paramString, CHAR paramCHAR)
/*      */     throws SQLException
/*      */   {
/* 1058 */     updateCHAR(findColumn(paramString), paramCHAR);
/*      */   }
/*      */ 
/*      */   public void updateRAW(String paramString, RAW paramRAW)
/*      */     throws SQLException
/*      */   {
/* 1072 */     updateRAW(findColumn(paramString), paramRAW);
/*      */   }
/*      */ 
/*      */   public void updateBLOB(String paramString, BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 1086 */     updateBLOB(findColumn(paramString), paramBLOB);
/*      */   }
/*      */ 
/*      */   public void updateCLOB(String paramString, CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 1100 */     updateCLOB(findColumn(paramString), paramCLOB);
/*      */   }
/*      */ 
/*      */   public void updateBFILE(String paramString, BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1114 */     updateBFILE(findColumn(paramString), paramBFILE);
/*      */   }
/*      */ 
/*      */   public void updateBfile(String paramString, BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1130 */     updateBfile(findColumn(paramString), paramBFILE);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void updateCustomDatum(String paramString, CustomDatum paramCustomDatum)
/*      */     throws SQLException
/*      */   {
/* 1146 */     updateCustomDatum(findColumn(paramString), paramCustomDatum);
/*      */   }
/*      */ 
/*      */   public void updateORAData(String paramString, ORAData paramORAData)
/*      */     throws SQLException
/*      */   {
/* 1160 */     updateORAData(findColumn(paramString), paramORAData);
/*      */   }
/*      */ 
/*      */   public void updateRef(String paramString, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 1174 */     updateRef(findColumn(paramString), paramRef);
/*      */   }
/*      */ 
/*      */   public void updateBlob(String paramString, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 1188 */     updateBlob(findColumn(paramString), paramBlob);
/*      */   }
/*      */ 
/*      */   public void updateClob(String paramString, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 1202 */     updateClob(findColumn(paramString), paramClob);
/*      */   }
/*      */ 
/*      */   public void updateArray(String paramString, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 1217 */     updateArray(findColumn(paramString), paramArray);
/*      */   }
/*      */ 
/*      */   public abstract void setAutoRefetch(boolean paramBoolean)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean getAutoRefetch()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract SQLWarning getWarnings()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void clearWarnings()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract String getCursorName()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ResultSetMetaData getMetaData()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int findColumn(String paramString)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean next()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void close()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean wasNull()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Object getObject(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract String getString(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean getBoolean(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract byte getByte(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract short getShort(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getInt(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract long getLong(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract float getFloat(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract double getDouble(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract BigDecimal getBigDecimal(int paramInt1, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract byte[] getBytes(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Date getDate(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Time getTime(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Timestamp getTimestamp(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract InputStream getAsciiStream(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract InputStream getUnicodeStream(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract InputStream getBinaryStream(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public Object getObject(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1503 */     return getObject(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public String getString(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1515 */     return getString(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1527 */     return getBoolean(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public byte getByte(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1539 */     return getByte(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public short getShort(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1551 */     return getShort(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public int getInt(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1563 */     return getInt(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public long getLong(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1575 */     return getLong(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public float getFloat(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1587 */     return getFloat(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public double getDouble(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1599 */     return getDouble(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1613 */     return getBigDecimal(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1625 */     return getBytes(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1637 */     return getDate(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1649 */     return getTime(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1661 */     return getTimestamp(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1674 */     return getAsciiStream(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1687 */     return getUnicodeStream(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1700 */     return getBinaryStream(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public abstract Object getObject(int paramInt, Map paramMap)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Ref getRef(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Blob getBlob(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Clob getClob(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Array getArray(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Reader getCharacterStream(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract BigDecimal getBigDecimal(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Date getDate(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Time getTime(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException;
/*      */ 
/*      */   public TIMESTAMP getTIMESTAMP(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1864 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 1866 */     return null;
/*      */   }
/*      */ 
/*      */   public TIMESTAMP getTIMESTAMP(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1872 */     return getTIMESTAMP(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1878 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 1880 */     return null;
/*      */   }
/*      */ 
/*      */   public TIMESTAMPTZ getTIMESTAMPTZ(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1886 */     return getTIMESTAMPTZ(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1892 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 1894 */     return null;
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ getTIMESTAMPLTZ(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1900 */     return getTIMESTAMPLTZ(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public INTERVALYM getINTERVALYM(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1906 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 1908 */     return null;
/*      */   }
/*      */ 
/*      */   public INTERVALYM getINTERVALYM(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1914 */     return getINTERVALYM(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public INTERVALDS getINTERVALDS(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1920 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 1922 */     return null;
/*      */   }
/*      */ 
/*      */   public INTERVALDS getINTERVALDS(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1928 */     return getINTERVALDS(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1950 */     return getObject(findColumn(paramString), paramMap);
/*      */   }
/*      */ 
/*      */   public Ref getRef(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1964 */     return getRef(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Blob getBlob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1979 */     return getBlob(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Clob getClob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1994 */     return getClob(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Reader getCharacterStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2007 */     return getCharacterStream(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Array getArray(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2023 */     return getARRAY(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2042 */     return getBigDecimal(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2062 */     return getDate(findColumn(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2082 */     return getTime(findColumn(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2102 */     return getTimestamp(findColumn(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public abstract boolean isBeforeFirst()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean isAfterLast()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean isFirst()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean isLast()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void beforeFirst()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void afterLast()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean first()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean last()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean absolute(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean relative(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean previous()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void setFetchDirection(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getFetchDirection()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void setFetchSize(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getFetchSize()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getType()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract int getConcurrency()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void insertRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void deleteRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void refreshRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void moveToInsertRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void cancelRowUpdates()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void moveToCurrentRow()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract Statement getStatement()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean rowUpdated()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean rowInserted()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract boolean rowDeleted()
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateNull(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateByte(int paramInt, byte paramByte)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateShort(int paramInt, short paramShort)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateInt(int paramInt1, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateLong(int paramInt, long paramLong)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateFloat(int paramInt, float paramFloat)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateDouble(int paramInt, double paramDouble)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateString(int paramInt, String paramString)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateDate(int paramInt, Date paramDate)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateTime(int paramInt, Time paramTime)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract void updateObject(int paramInt, Object paramObject)
/*      */     throws SQLException;
/*      */ 
/*      */   public void updateNull(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2735 */     updateNull(findColumn(paramString));
/*      */   }
/*      */ 
/*      */   public void updateBoolean(String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2749 */     updateBoolean(findColumn(paramString), paramBoolean);
/*      */   }
/*      */ 
/*      */   public void updateByte(String paramString, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 2763 */     updateByte(findColumn(paramString), paramByte);
/*      */   }
/*      */ 
/*      */   public void updateShort(String paramString, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 2777 */     updateShort(findColumn(paramString), paramShort);
/*      */   }
/*      */ 
/*      */   public void updateInt(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2791 */     updateInt(findColumn(paramString), paramInt);
/*      */   }
/*      */ 
/*      */   public void updateLong(String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2805 */     updateLong(findColumn(paramString), paramLong);
/*      */   }
/*      */ 
/*      */   public void updateFloat(String paramString, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 2819 */     updateFloat(findColumn(paramString), paramFloat);
/*      */   }
/*      */ 
/*      */   public void updateDouble(String paramString, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 2833 */     updateDouble(findColumn(paramString), paramDouble);
/*      */   }
/*      */ 
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 2848 */     updateBigDecimal(findColumn(paramString), paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void updateString(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2862 */     updateString(findColumn(paramString1), paramString2);
/*      */   }
/*      */ 
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2876 */     updateBytes(findColumn(paramString), paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void updateDate(String paramString, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 2890 */     updateDate(findColumn(paramString), paramDate);
/*      */   }
/*      */ 
/*      */   public void updateTime(String paramString, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 2904 */     updateTime(findColumn(paramString), paramTime);
/*      */   }
/*      */ 
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 2919 */     updateTimestamp(findColumn(paramString), paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2935 */     updateAsciiStream(findColumn(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2951 */     updateBinaryStream(findColumn(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2967 */     updateCharacterStream(findColumn(paramString), paramReader, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2985 */     updateObject(findColumn(paramString), paramObject, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 2999 */     updateObject(findColumn(paramString), paramObject);
/*      */   }
/*      */ 
/*      */   public abstract URL getURL(int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   public URL getURL(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3040 */     return getURL(findColumn(paramString));
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleResultSet
 * JD-Core Version:    0.6.0
 */