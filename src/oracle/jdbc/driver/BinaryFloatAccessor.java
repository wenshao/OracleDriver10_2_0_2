/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.BINARY_FLOAT;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.NUMBER;
/*     */ 
/*     */ class BinaryFloatAccessor extends Accessor
/*     */ {
/*     */   static final int MAXLENGTH = 4;
/*     */ 
/*     */   BinaryFloatAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  27 */     init(paramOracleStatement, 100, 100, paramShort, paramBoolean);
/*  28 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   BinaryFloatAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  35 */     init(paramOracleStatement, 100, 100, paramShort, false);
/*  36 */     initForDescribe(100, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  39 */     int i = paramOracleStatement.maxFieldSize;
/*     */ 
/*  41 */     if ((i > 0) && ((paramInt1 == 0) || (i < paramInt1))) {
/*  42 */       paramInt1 = i;
/*     */     }
/*  44 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, short paramShort, int paramInt4)
/*     */     throws SQLException
/*     */   {
/*  50 */     init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
/*  51 */     initForDataAccess(paramInt4, paramInt3, null);
/*     */   }
/*     */ 
/*     */   void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  58 */     init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
/*  59 */     initForDescribe(paramInt1, paramInt3, paramBoolean, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramShort, null);
/*     */ 
/*  62 */     int i = paramOracleStatement.maxFieldSize;
/*     */ 
/*  64 */     if ((i > 0) && ((paramInt3 == 0) || (i < paramInt3))) {
/*  65 */       paramInt3 = i;
/*     */     }
/*  67 */     initForDataAccess(0, paramInt3, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  73 */     if (paramInt1 != 0) {
/*  74 */       this.externalType = paramInt1;
/*     */     }
/*  76 */     this.internalTypeMaxLength = 4;
/*     */ 
/*  78 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  79 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  81 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   float getFloat(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 120 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 125 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 131 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
/* 132 */       return 0.0F;
/*     */     }
/* 134 */     int i = this.columnIndex + this.byteLength * paramInt;
/* 135 */     int j = this.rowSpaceByte[i];
/* 136 */     int k = this.rowSpaceByte[(i + 1)];
/* 137 */     int m = this.rowSpaceByte[(i + 2)];
/* 138 */     int n = this.rowSpaceByte[(i + 3)];
/*     */ 
/* 140 */     if ((j & 0x80) != 0)
/*     */     {
/* 142 */       j &= 127;
/* 143 */       k &= 255;
/* 144 */       m &= 255;
/* 145 */       n &= 255;
/*     */     }
/*     */     else
/*     */     {
/* 149 */       j = (j ^ 0xFFFFFFFF) & 0xFF;
/* 150 */       k = (k ^ 0xFFFFFFFF) & 0xFF;
/* 151 */       m = (m ^ 0xFFFFFFFF) & 0xFF;
/* 152 */       n = (n ^ 0xFFFFFFFF) & 0xFF;
/*     */     }
/*     */ 
/* 155 */     int i1 = j << 24 | k << 16 | m << 8 | n;
/*     */ 
/* 157 */     return Float.intBitsToFloat(i1);
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 169 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 174 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 180 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 181 */       return Float.toString(getFloat(paramInt));
/*     */     }
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 195 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 200 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 206 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 207 */       return new Float(getFloat(paramInt));
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 222 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 227 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 233 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 234 */       return new Float(getFloat(paramInt));
/*     */     }
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 248 */     return getBINARY_FLOAT(paramInt);
/*     */   }
/*     */ 
/*     */   BINARY_FLOAT getBINARY_FLOAT(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 260 */     BINARY_FLOAT localBINARY_FLOAT = null;
/*     */ 
/* 262 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 267 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 273 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 275 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 276 */       int j = this.columnIndex + this.byteLength * paramInt;
/* 277 */       byte[] arrayOfByte = new byte[i];
/*     */ 
/* 279 */       System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
/*     */ 
/* 281 */       localBINARY_FLOAT = new BINARY_FLOAT(arrayOfByte);
/*     */     }
/*     */ 
/* 284 */     return localBINARY_FLOAT;
/*     */   }
/*     */ 
/*     */   NUMBER getNUMBER(int paramInt) throws SQLException
/*     */   {
/* 289 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 294 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 300 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 301 */       return new NUMBER(getFloat(paramInt));
/*     */     }
/* 303 */     return null;
/*     */   }
/*     */ 
/*     */   BigInteger getBigInteger(int paramInt) throws SQLException
/*     */   {
/* 308 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 313 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 319 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 320 */       return new BigInteger(getString(paramInt));
/*     */     }
/* 322 */     return null;
/*     */   }
/*     */ 
/*     */   BigDecimal getBigDecimal(int paramInt) throws SQLException
/*     */   {
/* 327 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 332 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 338 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
/* 339 */       return new BigDecimal(getString(paramInt));
/*     */     }
/* 341 */     return null;
/*     */   }
/*     */ 
/*     */   byte getByte(int paramInt) throws SQLException
/*     */   {
/* 346 */     return (byte)(int)getFloat(paramInt);
/*     */   }
/*     */ 
/*     */   short getShort(int paramInt) throws SQLException
/*     */   {
/* 351 */     return (short)(int)getFloat(paramInt);
/*     */   }
/*     */ 
/*     */   int getInt(int paramInt) throws SQLException
/*     */   {
/* 356 */     return (int)getFloat(paramInt);
/*     */   }
/*     */ 
/*     */   long getLong(int paramInt) throws SQLException
/*     */   {
/* 361 */     return (long)getFloat(paramInt);
/*     */   }
/*     */ 
/*     */   double getDouble(int paramInt) throws SQLException
/*     */   {
/* 366 */     return getFloat(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BinaryFloatAccessor
 * JD-Core Version:    0.6.0
 */