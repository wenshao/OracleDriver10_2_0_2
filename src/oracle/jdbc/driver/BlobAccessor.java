/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ class BlobAccessor extends Accessor
/*     */ {
/*     */   static final int maxLength = 4000;
/*     */ 
/*     */   BlobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  31 */     init(paramOracleStatement, 113, 113, paramShort, paramBoolean);
/*  32 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   BlobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  39 */     init(paramOracleStatement, 113, 113, paramShort, false);
/*  40 */     initForDescribe(113, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  42 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  48 */     if (paramInt1 != 0) {
/*  49 */       this.externalType = paramInt1;
/*     */     }
/*  51 */     this.internalTypeMaxLength = 4000;
/*     */ 
/*  53 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  54 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  56 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  75 */     return getBLOB(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*  90 */     return getBLOB(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 110 */     return getBLOB(paramInt);
/*     */   }
/*     */ 
/*     */   BLOB getBLOB(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 126 */     BLOB localBLOB = null;
/*     */ 
/* 128 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 133 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 140 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 142 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 143 */       int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*     */ 
/* 145 */       byte[] arrayOfByte = new byte[j];
/* 146 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
/*     */ 
/* 149 */       localBLOB = new BLOB(this.statement.connection, arrayOfByte);
/*     */     }
/*     */ 
/* 152 */     return localBLOB;
/*     */   }
/*     */ 
/*     */   InputStream getAsciiStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 166 */     BLOB localBLOB = getBLOB(paramInt);
/*     */ 
/* 168 */     if (localBLOB == null) {
/* 169 */       return null;
/*     */     }
/* 171 */     return localBLOB.asciiStreamValue();
/*     */   }
/*     */ 
/*     */   Reader getCharacterStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 185 */     BLOB localBLOB = getBLOB(paramInt);
/*     */ 
/* 187 */     if (localBLOB == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     return localBLOB.characterStreamValue();
/*     */   }
/*     */ 
/*     */   InputStream getBinaryStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 204 */     BLOB localBLOB = getBLOB(paramInt);
/*     */ 
/* 206 */     if (localBLOB == null) {
/* 207 */       return null;
/*     */     }
/* 209 */     return localBLOB.getBinaryStream();
/*     */   }
/*     */ 
/*     */   byte[] privateGetBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 221 */     return super.getBytes(paramInt);
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 236 */     BLOB localBLOB = getBLOB(paramInt);
/*     */ 
/* 238 */     if (localBLOB == null) {
/* 239 */       return null;
/*     */     }
/* 241 */     InputStream localInputStream = localBLOB.getBinaryStream();
/* 242 */     int i = localBLOB.getBufferSize();
/* 243 */     int j = 0;
/* 244 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
/* 245 */     byte[] arrayOfByte = new byte[i];
/*     */     try
/*     */     {
/* 249 */       while ((j = localInputStream.read(arrayOfByte)) != -1)
/*     */       {
/* 251 */         localByteArrayOutputStream.write(arrayOfByte, 0, j);
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 256 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */     catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*     */     {
/* 260 */       DatabaseError.throwSqlException(151);
/*     */     }
/* 262 */     if (localBLOB.isTemporary()) this.statement.addToTempLobsToFree(localBLOB);
/* 263 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BlobAccessor
 * JD-Core Version:    0.6.0
 */