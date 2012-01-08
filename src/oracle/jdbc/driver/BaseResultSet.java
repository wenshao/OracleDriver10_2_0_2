/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.CHAR;
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.CustomDatum;
/*     */ import oracle.sql.DATE;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.INTERVALDS;
/*     */ import oracle.sql.INTERVALYM;
/*     */ import oracle.sql.NUMBER;
/*     */ import oracle.sql.OPAQUE;
/*     */ import oracle.sql.ORAData;
/*     */ import oracle.sql.RAW;
/*     */ import oracle.sql.REF;
/*     */ import oracle.sql.ROWID;
/*     */ import oracle.sql.STRUCT;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ import oracle.sql.TIMESTAMPLTZ;
/*     */ import oracle.sql.TIMESTAMPTZ;
/*     */ 
/*     */ abstract class BaseResultSet extends OracleResultSet
/*     */ {
/*  24 */   SQLWarning sqlWarning = null;
/*  25 */   boolean autoRefetch = true;
/*     */ 
/*  29 */   boolean close_statement_on_close = false;
/*     */ 
/* 582 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   public synchronized String getCursorName()
/*     */     throws SQLException
/*     */   {
/*  37 */     DatabaseError.throwSqlException(23, "getCursorName");
/*     */ 
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   public void closeStatementOnClose()
/*     */   {
/*  53 */     this.close_statement_on_close = true;
/*     */   }
/*     */ 
/*     */   public void beforeFirst()
/*     */     throws SQLException
/*     */   {
/*  62 */     DatabaseError.throwSqlException(75, "beforeFirst");
/*     */   }
/*     */ 
/*     */   public void afterLast()
/*     */     throws SQLException
/*     */   {
/*  68 */     DatabaseError.throwSqlException(75, "afterLast");
/*     */   }
/*     */ 
/*     */   public boolean first()
/*     */     throws SQLException
/*     */   {
/*  74 */     DatabaseError.throwSqlException(75, "first");
/*     */ 
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean last() throws SQLException
/*     */   {
/*  82 */     DatabaseError.throwSqlException(75, "last");
/*     */ 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean absolute(int paramInt) throws SQLException
/*     */   {
/*  90 */     DatabaseError.throwSqlException(75, "absolute");
/*     */ 
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean relative(int paramInt) throws SQLException
/*     */   {
/*  98 */     DatabaseError.throwSqlException(75, "relative");
/*     */ 
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean previous() throws SQLException
/*     */   {
/* 106 */     DatabaseError.throwSqlException(75, "previous");
/*     */ 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   public void setFetchDirection(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 122 */     if (paramInt == 1000)
/* 123 */       return;
/* 124 */     if ((paramInt == 1001) || (paramInt == 1002)) {
/* 125 */       DatabaseError.throwSqlException(75, "setFetchDirection(FETCH_REVERSE, FETCH_UNKNOWN)");
/*     */     }
/*     */     else
/* 128 */       DatabaseError.throwSqlException(68, "setFetchDirection");
/*     */   }
/*     */ 
/*     */   public int getFetchDirection()
/*     */     throws SQLException
/*     */   {
/* 138 */     return 1000;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */     throws SQLException
/*     */   {
/* 147 */     return 1003;
/*     */   }
/*     */ 
/*     */   public int getConcurrency()
/*     */     throws SQLException
/*     */   {
/* 156 */     return 1007;
/*     */   }
/*     */ 
/*     */   public SQLWarning getWarnings()
/*     */     throws SQLException
/*     */   {
/* 169 */     return this.sqlWarning;
/*     */   }
/*     */ 
/*     */   public void clearWarnings()
/*     */     throws SQLException
/*     */   {
/* 178 */     this.sqlWarning = null;
/*     */   }
/*     */ 
/*     */   public boolean rowUpdated()
/*     */     throws SQLException
/*     */   {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean rowInserted()
/*     */     throws SQLException
/*     */   {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean rowDeleted()
/*     */     throws SQLException
/*     */   {
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   public void updateNull(int paramInt) throws SQLException
/*     */   {
/* 214 */     DatabaseError.throwSqlException(76, "updateNull");
/*     */   }
/*     */ 
/*     */   public void updateBoolean(int paramInt, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 220 */     DatabaseError.throwSqlException(76, "updateBoolean");
/*     */   }
/*     */ 
/*     */   public void updateByte(int paramInt, byte paramByte)
/*     */     throws SQLException
/*     */   {
/* 226 */     DatabaseError.throwSqlException(76, "updateByte");
/*     */   }
/*     */ 
/*     */   public void updateShort(int paramInt, short paramShort)
/*     */     throws SQLException
/*     */   {
/* 232 */     DatabaseError.throwSqlException(76, "updateShort");
/*     */   }
/*     */ 
/*     */   public void updateInt(int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 238 */     DatabaseError.throwSqlException(76, "updateInt");
/*     */   }
/*     */ 
/*     */   public void updateLong(int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 244 */     DatabaseError.throwSqlException(76, "updateLong");
/*     */   }
/*     */ 
/*     */   public void updateFloat(int paramInt, float paramFloat)
/*     */     throws SQLException
/*     */   {
/* 250 */     DatabaseError.throwSqlException(76, "updateFloat");
/*     */   }
/*     */ 
/*     */   public void updateDouble(int paramInt, double paramDouble)
/*     */     throws SQLException
/*     */   {
/* 256 */     DatabaseError.throwSqlException(76, "updateDouble");
/*     */   }
/*     */ 
/*     */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*     */     throws SQLException
/*     */   {
/* 263 */     DatabaseError.throwSqlException(76, "updateBigDecimal");
/*     */   }
/*     */ 
/*     */   public void updateString(int paramInt, String paramString)
/*     */     throws SQLException
/*     */   {
/* 269 */     DatabaseError.throwSqlException(76, "updateString");
/*     */   }
/*     */ 
/*     */   public void updateBytes(int paramInt, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 275 */     DatabaseError.throwSqlException(76, "updateBytes");
/*     */   }
/*     */ 
/*     */   public void updateDate(int paramInt, Date paramDate)
/*     */     throws SQLException
/*     */   {
/* 281 */     DatabaseError.throwSqlException(76, "updateDate");
/*     */   }
/*     */ 
/*     */   public void updateTime(int paramInt, Time paramTime)
/*     */     throws SQLException
/*     */   {
/* 287 */     DatabaseError.throwSqlException(76, "updateTime");
/*     */   }
/*     */ 
/*     */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
/*     */     throws SQLException
/*     */   {
/* 294 */     DatabaseError.throwSqlException(76, "updateTimestamp");
/*     */   }
/*     */ 
/*     */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 301 */     DatabaseError.throwSqlException(76, "updateAsciiStream");
/*     */   }
/*     */ 
/*     */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 309 */     DatabaseError.throwSqlException(76, "updateBinaryStream");
/*     */   }
/*     */ 
/*     */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 316 */     DatabaseError.throwSqlException(76, "updateCharacterStream");
/*     */   }
/*     */ 
/*     */   public void updateObject(int paramInt1, Object paramObject, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 323 */     DatabaseError.throwSqlException(76, "updateObject");
/*     */   }
/*     */ 
/*     */   public void updateObject(int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 329 */     DatabaseError.throwSqlException(76, "updateObject");
/*     */   }
/*     */ 
/*     */   public void updateOracleObject(int paramInt, Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 335 */     DatabaseError.throwSqlException(76, "updateOracleObject");
/*     */   }
/*     */ 
/*     */   public void updateROWID(int paramInt, ROWID paramROWID)
/*     */     throws SQLException
/*     */   {
/* 341 */     DatabaseError.throwSqlException(76, "updateROWID");
/*     */   }
/*     */ 
/*     */   public void updateNUMBER(int paramInt, NUMBER paramNUMBER)
/*     */     throws SQLException
/*     */   {
/* 347 */     DatabaseError.throwSqlException(76, "updateNUMBER");
/*     */   }
/*     */ 
/*     */   public void updateDATE(int paramInt, DATE paramDATE)
/*     */     throws SQLException
/*     */   {
/* 353 */     DatabaseError.throwSqlException(76, "updateDATE");
/*     */   }
/*     */ 
/*     */   public void updateARRAY(int paramInt, ARRAY paramARRAY)
/*     */     throws SQLException
/*     */   {
/* 359 */     DatabaseError.throwSqlException(76, "updateARRAY");
/*     */   }
/*     */ 
/*     */   public void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
/*     */     throws SQLException
/*     */   {
/* 365 */     DatabaseError.throwSqlException(76, "updateSTRUCT");
/*     */   }
/*     */ 
/*     */   public void updateOPAQUE(int paramInt, OPAQUE paramOPAQUE)
/*     */     throws SQLException
/*     */   {
/* 371 */     DatabaseError.throwSqlException(76, "updateOPAQUE");
/*     */   }
/*     */ 
/*     */   public void updateREF(int paramInt, REF paramREF)
/*     */     throws SQLException
/*     */   {
/* 377 */     DatabaseError.throwSqlException(76, "updateREF");
/*     */   }
/*     */ 
/*     */   public void updateCHAR(int paramInt, CHAR paramCHAR)
/*     */     throws SQLException
/*     */   {
/* 383 */     DatabaseError.throwSqlException(76, "updateCHAR");
/*     */   }
/*     */ 
/*     */   public void updateRAW(int paramInt, RAW paramRAW)
/*     */     throws SQLException
/*     */   {
/* 389 */     DatabaseError.throwSqlException(76, "updateRAW");
/*     */   }
/*     */ 
/*     */   public void updateBLOB(int paramInt, BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/* 395 */     DatabaseError.throwSqlException(76, "updateBLOB");
/*     */   }
/*     */ 
/*     */   public void updateCLOB(int paramInt, CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/* 401 */     DatabaseError.throwSqlException(76, "updateCLOB");
/*     */   }
/*     */ 
/*     */   public void updateBFILE(int paramInt, BFILE paramBFILE)
/*     */     throws SQLException
/*     */   {
/* 407 */     DatabaseError.throwSqlException(76, "updateBFILE");
/*     */   }
/*     */ 
/*     */   public void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
/*     */     throws SQLException
/*     */   {
/* 414 */     DatabaseError.throwSqlException(76, "updateINTERVALYM");
/*     */   }
/*     */ 
/*     */   public void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
/*     */     throws SQLException
/*     */   {
/* 421 */     DatabaseError.throwSqlException(76, "updateINTERVALDS");
/*     */   }
/*     */ 
/*     */   public void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
/*     */     throws SQLException
/*     */   {
/* 427 */     DatabaseError.throwSqlException(76, "updateTIMESTAMP");
/*     */   }
/*     */ 
/*     */   public void updateTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
/*     */     throws SQLException
/*     */   {
/* 434 */     DatabaseError.throwSqlException(76, "updateTIMESTAMPTZ");
/*     */   }
/*     */ 
/*     */   public void updateTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
/*     */     throws SQLException
/*     */   {
/* 441 */     DatabaseError.throwSqlException(76, "updateTIMESTAMPLTZ");
/*     */   }
/*     */ 
/*     */   public void updateBfile(int paramInt, BFILE paramBFILE)
/*     */     throws SQLException
/*     */   {
/* 447 */     DatabaseError.throwSqlException(76, "updateBfile");
/*     */   }
/*     */ 
/*     */   public void updateCustomDatum(int paramInt, CustomDatum paramCustomDatum)
/*     */     throws SQLException
/*     */   {
/* 454 */     DatabaseError.throwSqlException(76, "updateCustomDatum");
/*     */   }
/*     */ 
/*     */   public void updateORAData(int paramInt, ORAData paramORAData)
/*     */     throws SQLException
/*     */   {
/* 460 */     DatabaseError.throwSqlException(76, "updateORAData");
/*     */   }
/*     */ 
/*     */   public void updateRef(int paramInt, Ref paramRef)
/*     */     throws SQLException
/*     */   {
/* 466 */     DatabaseError.throwSqlException(76, "updateRef");
/*     */   }
/*     */ 
/*     */   public void updateBlob(int paramInt, Blob paramBlob)
/*     */     throws SQLException
/*     */   {
/* 472 */     DatabaseError.throwSqlException(76, "updateBlob");
/*     */   }
/*     */ 
/*     */   public void updateClob(int paramInt, Clob paramClob)
/*     */     throws SQLException
/*     */   {
/* 478 */     DatabaseError.throwSqlException(76, "updateClob");
/*     */   }
/*     */ 
/*     */   public void updateArray(int paramInt, Array paramArray)
/*     */     throws SQLException
/*     */   {
/* 484 */     DatabaseError.throwSqlException(76, "updateArray");
/*     */   }
/*     */ 
/*     */   public void insertRow()
/*     */     throws SQLException
/*     */   {
/* 491 */     DatabaseError.throwSqlException(76, "insertRow");
/*     */   }
/*     */ 
/*     */   public void updateRow()
/*     */     throws SQLException
/*     */   {
/* 497 */     DatabaseError.throwSqlException(76, "updateRow");
/*     */   }
/*     */ 
/*     */   public void deleteRow()
/*     */     throws SQLException
/*     */   {
/* 503 */     DatabaseError.throwSqlException(76, "deleteRow");
/*     */   }
/*     */ 
/*     */   public void refreshRow()
/*     */     throws SQLException
/*     */   {
/* 509 */     DatabaseError.throwSqlException(23, null);
/*     */   }
/*     */ 
/*     */   public void cancelRowUpdates()
/*     */     throws SQLException
/*     */   {
/* 515 */     DatabaseError.throwSqlException(76, "cancelRowUpdates");
/*     */   }
/*     */ 
/*     */   public void moveToInsertRow()
/*     */     throws SQLException
/*     */   {
/* 521 */     DatabaseError.throwSqlException(76, "moveToInsertRow");
/*     */   }
/*     */ 
/*     */   public void moveToCurrentRow()
/*     */     throws SQLException
/*     */   {
/* 527 */     DatabaseError.throwSqlException(76, "moveToCurrentRow");
/*     */   }
/*     */ 
/*     */   public void setAutoRefetch(boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 558 */     this.autoRefetch = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean getAutoRefetch()
/*     */     throws SQLException
/*     */   {
/* 577 */     return this.autoRefetch;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BaseResultSet
 * JD-Core Version:    0.6.0
 */