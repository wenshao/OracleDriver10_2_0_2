/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Clob;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ class ClobAccessor extends Accessor
/*     */ {
/*     */   static final int maxLength = 4000;
/*     */ 
/*     */   ClobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  30 */     init(paramOracleStatement, 112, 112, paramShort, paramBoolean);
/*  31 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   ClobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  38 */     init(paramOracleStatement, 112, 112, paramShort, false);
/*  39 */     initForDescribe(112, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  41 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  47 */     if (paramInt1 != 0) {
/*  48 */       this.externalType = paramInt1;
/*     */     }
/*  50 */     this.internalTypeMaxLength = 4000;
/*     */ 
/*  52 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  53 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  55 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  74 */     return getCLOB(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*  89 */     return getCLOB(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 109 */     return getCLOB(paramInt);
/*     */   }
/*     */ 
/*     */   CLOB getCLOB(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 125 */     CLOB localCLOB = null;
/*     */ 
/* 127 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 132 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 139 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 141 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 142 */       int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*     */ 
/* 144 */       byte[] arrayOfByte = new byte[j];
/*     */ 
/* 146 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
/*     */ 
/* 149 */       localCLOB = new CLOB(this.statement.connection, arrayOfByte, this.formOfUse);
/*     */     }
/*     */ 
/* 152 */     return localCLOB;
/*     */   }
/*     */ 
/*     */   InputStream getAsciiStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 166 */     CLOB localCLOB = getCLOB(paramInt);
/*     */ 
/* 168 */     if (localCLOB == null) {
/* 169 */       return null;
/*     */     }
/* 171 */     return localCLOB.getAsciiStream();
/*     */   }
/*     */ 
/*     */   Reader getCharacterStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 185 */     CLOB localCLOB = getCLOB(paramInt);
/*     */ 
/* 187 */     if (localCLOB == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     return localCLOB.getCharacterStream();
/*     */   }
/*     */ 
/*     */   InputStream getBinaryStream(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 204 */     CLOB localCLOB = getCLOB(paramInt);
/*     */ 
/* 206 */     if (localCLOB == null) {
/* 207 */       return null;
/*     */     }
/* 209 */     return localCLOB.getAsciiStream();
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 224 */     CLOB localCLOB = getCLOB(paramInt);
/*     */ 
/* 226 */     if (localCLOB == null) {
/* 227 */       return null;
/*     */     }
/* 229 */     Reader localReader = localCLOB.getCharacterStream();
/* 230 */     int i = localCLOB.getBufferSize();
/* 231 */     int j = 0;
/* 232 */     StringWriter localStringWriter = new StringWriter(i);
/* 233 */     char[] arrayOfChar = new char[i];
/*     */     try
/*     */     {
/* 237 */       while ((j = localReader.read(arrayOfChar)) != -1)
/*     */       {
/* 239 */         localStringWriter.write(arrayOfChar, 0, j);
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 244 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */     catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*     */     {
/* 248 */       DatabaseError.throwSqlException(151);
/*     */     }
/*     */ 
/* 251 */     if (localCLOB.isTemporary()) this.statement.addToTempLobsToFree(localCLOB);
/* 252 */     return localStringWriter.toString();
/*     */   }
/*     */ 
/*     */   byte[] privateGetBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 264 */     return super.getBytes(paramInt);
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 279 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 280 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ClobAccessor
 * JD-Core Version:    0.6.0
 */