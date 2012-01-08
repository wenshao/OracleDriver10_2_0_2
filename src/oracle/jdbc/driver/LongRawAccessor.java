/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class LongRawAccessor extends RawCommonAccessor
/*     */ {
/*     */   OracleInputStream stream;
/*  30 */   int columnPosition = 0;
/*     */ 
/* 368 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   LongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3)
/*     */     throws SQLException
/*     */   {
/*  36 */     init(paramOracleStatement, 24, 24, paramShort, false);
/*     */ 
/*  38 */     this.columnPosition = paramInt1;
/*     */ 
/*  40 */     initForDataAccess(paramInt3, paramInt2, null);
/*     */   }
/*     */ 
/*     */   LongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  47 */     init(paramOracleStatement, 24, 24, paramShort, false);
/*     */ 
/*  49 */     this.columnPosition = paramInt1;
/*     */ 
/*  51 */     initForDescribe(24, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, null);
/*     */ 
/*  54 */     int i = paramOracleStatement.maxFieldSize;
/*     */ 
/*  56 */     if ((i > 0) && ((paramInt2 == 0) || (i < paramInt2))) {
/*  57 */       paramInt2 = i;
/*     */     }
/*  59 */     initForDataAccess(0, paramInt2, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  65 */     if (paramInt1 != 0) {
/*  66 */       this.externalType = paramInt1;
/*     */     }
/*  68 */     this.isStream = true;
/*  69 */     this.isColumnNumberAware = true;
/*  70 */     this.internalTypeMaxLength = 2147483647;
/*     */ 
/*  72 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  73 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  75 */     this.byteLength = 0;
/*     */ 
/*  78 */     this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);
/*     */   }
/*     */ 
/*     */   OracleInputStream initForNewRow()
/*     */     throws SQLException
/*     */   {
/*  93 */     this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);
/*     */ 
/*  99 */     return this.stream;
/*     */   }
/*     */ 
/*     */   void updateColumnNumber(int paramInt)
/*     */   {
/* 106 */     paramInt++;
/*     */ 
/* 109 */     this.columnPosition = paramInt;
/*     */ 
/* 111 */     if (this.stream != null)
/* 112 */       this.stream.columnIndex = paramInt;
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 132 */     byte[] arrayOfByte1 = null;
/*     */ 
/* 134 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 139 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 145 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 147 */       if (this.stream != null)
/*     */       {
/* 154 */         if (this.stream.closed) {
/* 155 */           DatabaseError.throwSqlException(27);
/*     */         }
/* 157 */         ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);
/* 158 */         byte[] arrayOfByte2 = new byte[1024];
/*     */         try
/*     */         {
/*     */           int i;
/* 163 */           while ((i = this.stream.read(arrayOfByte2)) != -1)
/*     */           {
/* 165 */             localByteArrayOutputStream.write(arrayOfByte2, 0, i);
/*     */           }
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/* 170 */           DatabaseError.throwSqlException(localIOException);
/*     */         }
/*     */ 
/* 173 */         arrayOfByte1 = localByteArrayOutputStream.toByteArray();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 180 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */   InputStream getAsciiStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 198 */     InputStream localInputStream = null;
/*     */ 
/* 200 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 205 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 211 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 213 */       if (this.stream.closed) {
/* 214 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 216 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 218 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 2);
/*     */     }
/*     */ 
/* 224 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   InputStream getUnicodeStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 240 */     InputStream localInputStream = null;
/*     */ 
/* 242 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 247 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 253 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 255 */       if (this.stream.closed) {
/* 256 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 258 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 260 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 3);
/*     */     }
/*     */ 
/* 266 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   Reader getCharacterStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 282 */     Reader localReader = null;
/*     */ 
/* 284 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 289 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 295 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 297 */       if (this.stream.closed) {
/* 298 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 300 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 302 */       localReader = localPhysicalConnection.conversion.ConvertCharacterStream(this.stream, 8);
/*     */     }
/*     */ 
/* 309 */     return localReader;
/*     */   }
/*     */ 
/*     */   InputStream getBinaryStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 325 */     InputStream localInputStream = null;
/*     */ 
/* 327 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 332 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 338 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 340 */       if (this.stream.closed) {
/* 341 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 343 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 345 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 6);
/*     */     }
/*     */ 
/* 351 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 359 */     return "LongRawAccessor@" + Integer.toHexString(hashCode()) + "{columnPosition = " + this.columnPosition + "}";
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LongRawAccessor
 * JD-Core Version:    0.6.0
 */