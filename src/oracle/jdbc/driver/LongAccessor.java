/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class LongAccessor extends CharCommonAccessor
/*     */ {
/*     */   OracleInputStream stream;
/*  29 */   int columnPosition = 0;
/*     */ 
/* 412 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   LongAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3)
/*     */     throws SQLException
/*     */   {
/*  34 */     init(paramOracleStatement, 8, 8, paramShort, false);
/*     */ 
/*  36 */     this.columnPosition = paramInt1;
/*     */ 
/*  38 */     initForDataAccess(paramInt3, paramInt2, null);
/*     */   }
/*     */ 
/*     */   LongAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  45 */     init(paramOracleStatement, 8, 8, paramShort, false);
/*     */ 
/*  47 */     this.columnPosition = paramInt1;
/*     */ 
/*  49 */     initForDescribe(8, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, null);
/*     */ 
/*  52 */     int i = paramOracleStatement.maxFieldSize;
/*     */ 
/*  54 */     if ((i > 0) && ((paramInt2 == 0) || (i < paramInt2))) {
/*  55 */       paramInt2 = i;
/*     */     }
/*  57 */     initForDataAccess(0, paramInt2, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  63 */     if (paramInt1 != 0) {
/*  64 */       this.externalType = paramInt1;
/*     */     }
/*  66 */     this.isStream = true;
/*  67 */     this.isColumnNumberAware = true;
/*  68 */     this.internalTypeMaxLength = 2147483647;
/*     */ 
/*  70 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  71 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*     */ 
/*  74 */     this.charLength = 0;
/*     */ 
/*  77 */     this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);
/*     */   }
/*     */ 
/*     */   OracleInputStream initForNewRow()
/*     */     throws SQLException
/*     */   {
/*  90 */     this.stream = this.statement.connection.driverExtension.createInputStream(this.statement, this.columnPosition, this);
/*     */ 
/*  96 */     return this.stream;
/*     */   }
/*     */ 
/*     */   void updateColumnNumber(int paramInt)
/*     */   {
/* 103 */     paramInt++;
/*     */ 
/* 106 */     this.columnPosition = paramInt;
/*     */ 
/* 108 */     if (this.stream != null)
/* 109 */       this.stream.columnIndex = paramInt;
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 128 */     byte[] arrayOfByte1 = null;
/*     */ 
/* 130 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 135 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 141 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 143 */       if (this.stream != null)
/*     */       {
/* 150 */         if (this.stream.closed) {
/* 151 */           DatabaseError.throwSqlException(27);
/*     */         }
/* 153 */         ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);
/* 154 */         byte[] arrayOfByte2 = new byte[1024];
/*     */         try
/*     */         {
/*     */           int i;
/* 159 */           while ((i = this.stream.read(arrayOfByte2)) != -1)
/*     */           {
/* 161 */             localByteArrayOutputStream.write(arrayOfByte2, 0, i);
/*     */           }
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/* 166 */           DatabaseError.throwSqlException(localIOException);
/*     */         }
/*     */ 
/* 169 */         arrayOfByte1 = localByteArrayOutputStream.toByteArray();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 176 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 191 */     String str = null;
/*     */ 
/* 193 */     byte[] arrayOfByte = getBytes(paramInt);
/*     */ 
/* 195 */     if (arrayOfByte != null)
/*     */     {
/* 197 */       int i = Math.min(arrayOfByte.length, this.internalTypeMaxLength);
/*     */ 
/* 201 */       if (i == 0)
/*     */       {
/* 208 */         str = "";
/*     */       }
/* 213 */       else if (this.formOfUse == 2) {
/* 214 */         str = this.statement.connection.conversion.NCharBytesToString(arrayOfByte, i);
/*     */       }
/*     */       else {
/* 217 */         str = this.statement.connection.conversion.CharBytesToString(arrayOfByte, i);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 224 */     return str;
/*     */   }
/*     */ 
/*     */   InputStream getAsciiStream(int paramInt)
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
/* 260 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 0);
/*     */     }
/*     */ 
/* 266 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   InputStream getUnicodeStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 282 */     InputStream localInputStream = null;
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
/* 302 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 1);
/*     */     }
/*     */ 
/* 308 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   Reader getCharacterStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 324 */     Reader localReader = null;
/*     */ 
/* 326 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 331 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 337 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 339 */       if (this.stream.closed) {
/* 340 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 342 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 344 */       localReader = localPhysicalConnection.conversion.ConvertCharacterStream(this.stream, 9, this.formOfUse);
/*     */     }
/*     */ 
/* 351 */     return localReader;
/*     */   }
/*     */ 
/*     */   InputStream getBinaryStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 367 */     InputStream localInputStream = null;
/*     */ 
/* 369 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 374 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 380 */     if ((this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) && (this.stream != null))
/*     */     {
/* 382 */       if (this.stream.closed) {
/* 383 */         DatabaseError.throwSqlException(27);
/*     */       }
/* 385 */       PhysicalConnection localPhysicalConnection = this.statement.connection;
/*     */ 
/* 387 */       localInputStream = localPhysicalConnection.conversion.ConvertStream(this.stream, 6);
/*     */     }
/*     */ 
/* 393 */     return localInputStream;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 402 */     return "LongAccessor@" + Integer.toHexString(hashCode()) + "{columnPosition = " + this.columnPosition + "}";
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LongAccessor
 * JD-Core Version:    0.6.0
 */