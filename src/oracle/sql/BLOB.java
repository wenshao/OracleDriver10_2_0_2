/*     */ package oracle.sql;
/*     */ 
/*     */ import B;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class BLOB extends DatumWithConnection
/*     */   implements Blob
/*     */ {
/*     */   public static final int MAX_CHUNK_SIZE = 32768;
/*     */   public static final int DURATION_SESSION = 10;
/*     */   public static final int DURATION_CALL = 12;
/*     */   static final int OLD_WRONG_DURATION_SESSION = 1;
/*     */   static final int OLD_WRONG_DURATION_CALL = 2;
/*     */   public static final int MODE_READONLY = 0;
/*     */   public static final int MODE_READWRITE = 1;
/*     */   BlobDBAccess dbaccess;
/*     */   int dbChunkSize;
/* 982 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*     */ 
/*     */   protected BLOB()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BLOB(oracle.jdbc.OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 115 */     this(paramOracleConnection, null);
/*     */   }
/*     */ 
/*     */   public BLOB(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 130 */     super(paramArrayOfByte);
/*     */ 
/* 134 */     assertNotNull(paramOracleConnection);
/* 135 */     setPhysicalConnectionOf(paramOracleConnection);
/*     */ 
/* 137 */     this.dbaccess = getPhysicalConnection().createBlobDBAccess();
/* 138 */     this.dbChunkSize = -1;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws SQLException
/*     */   {
/* 155 */     return getDBAccess().length(this);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 174 */     if ((paramInt < 0) || (paramLong < 1L))
/*     */     {
/* 179 */       DatabaseError.throwSqlException(68, "getBytes()");
/*     */     }
/*     */ 
/* 183 */     Object localObject = null;
/*     */ 
/* 185 */     if (paramInt == 0) {
/* 186 */       localObject = new byte[0];
/*     */     }
/*     */     else {
/* 189 */       long l = 0L;
/* 190 */       byte[] arrayOfByte = new byte[paramInt];
/*     */ 
/* 192 */       l = getBytes(paramLong, paramInt, arrayOfByte);
/*     */ 
/* 194 */       if (l > 0L)
/*     */       {
/* 196 */         if (l == paramInt)
/*     */         {
/* 198 */           localObject = arrayOfByte;
/*     */         }
/*     */         else
/*     */         {
/* 205 */           localObject = new byte[(int)l];
/*     */ 
/* 207 */           System.arraycopy(arrayOfByte, 0, localObject, 0, (int)l);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 214 */     return (B)localObject;
/*     */   }
/*     */ 
/*     */   public InputStream getBinaryStream()
/*     */     throws SQLException
/*     */   {
/* 229 */     return getDBAccess().newInputStream(this, getBufferSize(), 0L);
/*     */   }
/*     */ 
/*     */   public long position(byte[] paramArrayOfByte, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 248 */     return getDBAccess().position(this, paramArrayOfByte, paramLong);
/*     */   }
/*     */ 
/*     */   public long position(Blob paramBlob, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 267 */     return getDBAccess().position(this, (BLOB)paramBlob, paramLong);
/*     */   }
/*     */ 
/*     */   public int getBytes(long paramLong, int paramInt, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 288 */     return getDBAccess().getBytes(this, paramLong, paramInt, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int putBytes(long paramLong, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 308 */     return setBytes(paramLong, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int putBytes(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 331 */     return setBytes(paramLong, paramArrayOfByte, 0, paramInt);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public OutputStream getBinaryOutputStream()
/*     */     throws SQLException
/*     */   {
/* 348 */     return setBinaryStream(0L);
/*     */   }
/*     */ 
/*     */   public byte[] getLocator()
/*     */   {
/* 360 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public void setLocator(byte[] paramArrayOfByte)
/*     */   {
/* 372 */     setBytes(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public int getChunkSize()
/*     */     throws SQLException
/*     */   {
/* 387 */     if (this.dbChunkSize <= 0)
/*     */     {
/* 389 */       this.dbChunkSize = getDBAccess().getChunkSize(this);
/*     */     }
/*     */ 
/* 394 */     return this.dbChunkSize;
/*     */   }
/*     */ 
/*     */   public int getBufferSize()
/*     */     throws SQLException
/*     */   {
/* 410 */     int i = getChunkSize();
/* 411 */     int j = i;
/*     */ 
/* 413 */     if ((i >= 32768) || (i <= 0))
/*     */     {
/* 415 */       j = 32768;
/*     */     }
/*     */     else
/*     */     {
/* 419 */       j = 32768 / i * i;
/*     */     }
/*     */ 
/* 424 */     return j;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static BLOB empty_lob()
/*     */     throws SQLException
/*     */   {
/* 439 */     return getEmptyBLOB();
/*     */   }
/*     */ 
/*     */   public static BLOB getEmptyBLOB()
/*     */     throws SQLException
/*     */   {
/* 471 */     byte[] arrayOfByte = new byte[86];
/*     */ 
/* 473 */     arrayOfByte[1] = 84;
/* 474 */     arrayOfByte[5] = 24;
/*     */ 
/* 476 */     BLOB localBLOB = new BLOB();
/*     */ 
/* 478 */     localBLOB.setShareBytes(arrayOfByte);
/*     */ 
/* 482 */     return localBLOB;
/*     */   }
/*     */ 
/*     */   public boolean isEmptyLob()
/*     */     throws SQLException
/*     */   {
/* 498 */     int i = (shareBytes()[5] & 0x10) != 0 ? 1 : 0;
/*     */ 
/* 502 */     return i;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public OutputStream getBinaryOutputStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 520 */     return setBinaryStream(paramLong);
/*     */   }
/*     */ 
/*     */   public InputStream getBinaryStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 535 */     return getDBAccess().newInputStream(this, getBufferSize(), paramLong);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void trim(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 552 */     truncate(paramLong);
/*     */   }
/*     */ 
/*     */   public static BLOB createTemporary(Connection paramConnection, boolean paramBoolean, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 570 */     int i = paramInt;
/*     */ 
/* 572 */     if (paramInt == 1) {
/* 573 */       i = 10;
/*     */     }
/* 575 */     if (paramInt == 2) {
/* 576 */       i = 12;
/*     */     }
/* 578 */     if ((paramConnection == null) || ((i != 10) && (i != 12)))
/*     */     {
/* 584 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 587 */     oracle.jdbc.internal.OracleConnection localOracleConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
/*     */ 
/* 590 */     return getDBAccess(localOracleConnection).createTemporaryBlob(localOracleConnection, paramBoolean, i);
/*     */   }
/*     */ 
/*     */   public static void freeTemporary(BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/* 605 */     if (paramBLOB == null) {
/* 606 */       return;
/*     */     }
/* 608 */     paramBLOB.freeTemporary();
/*     */   }
/*     */ 
/*     */   public static boolean isTemporary(BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/* 625 */     if (paramBLOB == null) {
/* 626 */       return false;
/*     */     }
/* 628 */     return paramBLOB.isTemporary();
/*     */   }
/*     */ 
/*     */   public void freeTemporary()
/*     */     throws SQLException
/*     */   {
/* 643 */     getDBAccess().freeTemporary(this);
/*     */   }
/*     */ 
/*     */   public boolean isTemporary()
/*     */     throws SQLException
/*     */   {
/* 659 */     return getDBAccess().isTemporary(this);
/*     */   }
/*     */ 
/*     */   public void open(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 672 */     getDBAccess().open(this, paramInt);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws SQLException
/*     */   {
/* 684 */     getDBAccess().close(this);
/*     */   }
/*     */ 
/*     */   public boolean isOpen()
/*     */     throws SQLException
/*     */   {
/* 697 */     return getDBAccess().isOpen(this);
/*     */   }
/*     */ 
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 725 */     return getDBAccess().putBytes(this, paramLong, paramArrayOfByte, 0, paramArrayOfByte != null ? paramArrayOfByte.length : 0);
/*     */   }
/*     */ 
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 757 */     return getDBAccess().putBytes(this, paramLong, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public OutputStream setBinaryStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 781 */     return getDBAccess().newOutputStream(this, getBufferSize(), paramLong);
/*     */   }
/*     */ 
/*     */   public void truncate(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 801 */     if (paramLong < 0L)
/*     */     {
/* 806 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 809 */     getDBAccess().trim(this, paramLong);
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 834 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 855 */     String str = paramClass.getName();
/*     */ 
/* 857 */     return (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.io.Reader") == 0);
/*     */   }
/*     */ 
/*     */   public Reader characterStreamValue()
/*     */     throws SQLException
/*     */   {
/* 873 */     getInternalConnection(); return getDBAccess().newConversionReader(this, 8);
/*     */   }
/*     */ 
/*     */   public InputStream asciiStreamValue()
/*     */     throws SQLException
/*     */   {
/* 889 */     getInternalConnection(); return getDBAccess().newConversionInputStream(this, 2);
/*     */   }
/*     */ 
/*     */   public InputStream binaryStreamValue()
/*     */     throws SQLException
/*     */   {
/* 905 */     return getBinaryStream();
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 928 */     return new BLOB[paramInt];
/*     */   }
/*     */ 
/*     */   public BlobDBAccess getDBAccess()
/*     */     throws SQLException
/*     */   {
/* 941 */     if (this.dbaccess == null)
/*     */     {
/* 943 */       if (isEmptyLob())
/*     */       {
/* 948 */         DatabaseError.throwSqlException(98);
/*     */       }
/*     */ 
/* 951 */       this.dbaccess = getInternalConnection().createBlobDBAccess();
/*     */     }
/*     */ 
/* 954 */     if (getPhysicalConnection().isClosed()) {
/* 955 */       DatabaseError.throwSqlException(8);
/*     */     }
/*     */ 
/* 959 */     return this.dbaccess;
/*     */   }
/*     */ 
/*     */   public static BlobDBAccess getDBAccess(Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 972 */     return ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().createBlobDBAccess();
/*     */   }
/*     */ 
/*     */   public Connection getJavaSqlConnection() throws SQLException
/*     */   {
/* 977 */     return super.getJavaSqlConnection();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.BLOB
 * JD-Core Version:    0.6.0
 */