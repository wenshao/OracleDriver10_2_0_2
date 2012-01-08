/*     */ package oracle.sql;
/*     */ 
/*     */ import B;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class BFILE extends DatumWithConnection
/*     */ {
/*     */   public static final int MAX_CHUNK_SIZE = 32512;
/*     */   public static final int MODE_READONLY = 0;
/*     */   public static final int MODE_READWRITE = 1;
/*     */   BfileDBAccess dbaccess;
/* 606 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*     */ 
/*     */   protected BFILE()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BFILE(oracle.jdbc.OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  75 */     this(paramOracleConnection, null);
/*     */   }
/*     */ 
/*     */   public BFILE(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/*  88 */     super(paramArrayOfByte);
/*     */ 
/*  93 */     setPhysicalConnectionOf(paramOracleConnection);
/*     */ 
/*  95 */     this.dbaccess = getInternalConnection().createBfileDBAccess();
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws SQLException
/*     */   {
/* 111 */     return getDBAccess().length(this);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 127 */     if ((paramLong < 1L) || (paramInt < 0))
/*     */     {
/* 132 */       DatabaseError.throwSqlException(68, null);
/*     */     }
/*     */ 
/* 136 */     Object localObject = null;
/*     */ 
/* 138 */     if (paramInt == 0)
/*     */     {
/* 140 */       localObject = new byte[0];
/*     */     }
/*     */     else
/*     */     {
/* 144 */       long l = 0L;
/* 145 */       byte[] arrayOfByte = new byte[paramInt];
/*     */ 
/* 147 */       l = getBytes(paramLong, paramInt, arrayOfByte);
/*     */ 
/* 149 */       if (l > 0L)
/*     */       {
/* 151 */         if (l == paramInt)
/*     */         {
/* 153 */           localObject = arrayOfByte;
/*     */         }
/*     */         else
/*     */         {
/* 160 */           localObject = new byte[(int)l];
/*     */ 
/* 162 */           System.arraycopy(arrayOfByte, 0, localObject, 0, (int)l);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 167 */         localObject = new byte[0];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 173 */     return (B)localObject;
/*     */   }
/*     */ 
/*     */   public int getBytes(long paramLong, int paramInt, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 190 */     int i = getDBAccess().getBytes(this, paramLong, paramInt, paramArrayOfByte);
/*     */ 
/* 194 */     return i;
/*     */   }
/*     */ 
/*     */   public InputStream getBinaryStream()
/*     */     throws SQLException
/*     */   {
/* 207 */     InputStream localInputStream = getDBAccess().newInputStream(this, 32512, 0L);
/*     */ 
/* 211 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   public long position(byte[] paramArrayOfByte, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 228 */     long l = getDBAccess().position(this, paramArrayOfByte, paramLong);
/*     */ 
/* 232 */     return l;
/*     */   }
/*     */ 
/*     */   public long position(BFILE paramBFILE, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 249 */     long l = getDBAccess().position(this, paramBFILE, paramLong);
/*     */ 
/* 253 */     return l;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */     throws SQLException
/*     */   {
/* 268 */     String str = getDBAccess().getName(this);
/*     */ 
/* 272 */     return str;
/*     */   }
/*     */ 
/*     */   public String getDirAlias()
/*     */     throws SQLException
/*     */   {
/* 287 */     String str = getDBAccess().getDirAlias(this);
/*     */ 
/* 291 */     return str;
/*     */   }
/*     */ 
/*     */   public void openFile()
/*     */     throws SQLException
/*     */   {
/* 305 */     getDBAccess().openFile(this);
/*     */   }
/*     */ 
/*     */   public boolean isFileOpen()
/*     */     throws SQLException
/*     */   {
/* 321 */     boolean bool = getDBAccess().isFileOpen(this);
/*     */ 
/* 325 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean fileExists()
/*     */     throws SQLException
/*     */   {
/* 342 */     boolean bool = getDBAccess().fileExists(this);
/*     */ 
/* 346 */     return bool;
/*     */   }
/*     */ 
/*     */   public void closeFile()
/*     */     throws SQLException
/*     */   {
/* 360 */     getDBAccess().closeFile(this);
/*     */   }
/*     */ 
/*     */   public byte[] getLocator()
/*     */   {
/* 372 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public void setLocator(byte[] paramArrayOfByte)
/*     */   {
/* 384 */     setBytes(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public InputStream getBinaryStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 399 */     return getDBAccess().newInputStream(this, 32512, paramLong);
/*     */   }
/*     */ 
/*     */   public void open()
/*     */     throws SQLException
/*     */   {
/* 412 */     getDBAccess().open(this, 0);
/*     */   }
/*     */ 
/*     */   public void open(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 426 */     if (paramInt != 0)
/*     */     {
/* 431 */       DatabaseError.throwSqlException(102);
/*     */     }
/*     */ 
/* 434 */     getDBAccess().open(this, paramInt);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws SQLException
/*     */   {
/* 446 */     getDBAccess().close(this);
/*     */   }
/*     */ 
/*     */   public boolean isOpen()
/*     */     throws SQLException
/*     */   {
/* 459 */     return getDBAccess().isOpen(this);
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 500 */     String str = paramClass.getName();
/*     */ 
/* 502 */     int i = (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.io.Reader") == 0) ? 1 : 0;
/*     */ 
/* 507 */     return i;
/*     */   }
/*     */ 
/*     */   public Reader characterStreamValue()
/*     */     throws SQLException
/*     */   {
/* 522 */     getInternalConnection(); return getDBAccess().newConversionReader(this, 8);
/*     */   }
/*     */ 
/*     */   public InputStream asciiStreamValue()
/*     */     throws SQLException
/*     */   {
/* 537 */     getInternalConnection(); return getDBAccess().newConversionInputStream(this, 2);
/*     */   }
/*     */ 
/*     */   public InputStream binaryStreamValue()
/*     */     throws SQLException
/*     */   {
/* 553 */     return getBinaryStream();
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 577 */     return new BFILE[paramInt];
/*     */   }
/*     */ 
/*     */   public BfileDBAccess getDBAccess()
/*     */     throws SQLException
/*     */   {
/* 590 */     if (this.dbaccess == null) {
/* 591 */       this.dbaccess = getInternalConnection().createBfileDBAccess();
/*     */     }
/* 593 */     if (getPhysicalConnection().isClosed()) {
/* 594 */       DatabaseError.throwSqlException(8);
/*     */     }
/* 596 */     return this.dbaccess;
/*     */   }
/*     */ 
/*     */   public Connection getJavaSqlConnection() throws SQLException
/*     */   {
/* 601 */     return super.getJavaSqlConnection();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.BFILE
 * JD-Core Version:    0.6.0
 */