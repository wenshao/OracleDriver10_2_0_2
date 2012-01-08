/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ class BfileAccessor extends Accessor
/*     */ {
/*     */   static final int maxLength = 530;
/*     */ 
/*     */   BfileAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  31 */     init(paramOracleStatement, 114, 114, paramShort, paramBoolean);
/*  32 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   BfileAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  39 */     init(paramOracleStatement, 114, 114, paramShort, false);
/*  40 */     initForDescribe(114, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
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
/*  51 */     this.internalTypeMaxLength = 530;
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
/*  75 */     return getBFILE(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*  90 */     return getBFILE(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 110 */     return getBFILE(paramInt);
/*     */   }
/*     */ 
/*     */   BFILE getBFILE(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 126 */     BFILE localBFILE = null;
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
/*     */ 
/* 147 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
/*     */ 
/* 150 */       localBFILE = new BFILE(this.statement.connection, arrayOfByte);
/*     */     }
/*     */ 
/* 153 */     return localBFILE;
/*     */   }
/*     */ 
/*     */   InputStream getAsciiStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 167 */     BFILE localBFILE = getBFILE(paramInt);
/*     */ 
/* 169 */     if (localBFILE == null) {
/* 170 */       return null;
/*     */     }
/* 172 */     return localBFILE.asciiStreamValue();
/*     */   }
/*     */ 
/*     */   Reader getCharacterStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 186 */     BFILE localBFILE = getBFILE(paramInt);
/*     */ 
/* 188 */     if (localBFILE == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     return localBFILE.characterStreamValue();
/*     */   }
/*     */ 
/*     */   InputStream getBinaryStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 205 */     BFILE localBFILE = getBFILE(paramInt);
/*     */ 
/* 207 */     if (localBFILE == null) {
/* 208 */       return null;
/*     */     }
/* 210 */     return localBFILE.getBinaryStream();
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
/* 236 */     BFILE localBFILE = getBFILE(paramInt);
/*     */ 
/* 238 */     if (localBFILE == null) {
/* 239 */       return null;
/*     */     }
/* 241 */     InputStream localInputStream = localBFILE.getBinaryStream();
/* 242 */     int i = 4096;
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
/*     */ 
/* 263 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BfileAccessor
 * JD-Core Version:    0.6.0
 */