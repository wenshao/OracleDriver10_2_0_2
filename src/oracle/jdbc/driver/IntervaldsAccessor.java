/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.INTERVALDS;
/*     */ 
/*     */ class IntervaldsAccessor extends Accessor
/*     */ {
/*     */   static final int maxLength = 11;
/*     */ 
/*     */   IntervaldsAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  27 */     init(paramOracleStatement, 183, 183, paramShort, paramBoolean);
/*  28 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   IntervaldsAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  35 */     init(paramOracleStatement, 183, 183, paramShort, false);
/*  36 */     initForDescribe(183, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  38 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  44 */     if (paramInt1 != 0) {
/*  45 */       this.externalType = paramInt1;
/*     */     }
/*  47 */     this.internalTypeMaxLength = 11;
/*     */ 
/*  49 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  50 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  52 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  71 */     String str = null;
/*     */ 
/*  73 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  78 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  85 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/*  87 */       int i = this.columnIndex + this.byteLength * paramInt;
/*  88 */       int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*     */ 
/*  90 */       byte[] arrayOfByte = new byte[j];
/*     */ 
/*  92 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
/*     */ 
/*  94 */       str = new INTERVALDS(arrayOfByte).toString();
/*     */     }
/*     */ 
/*  97 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 111 */     return getINTERVALDS(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 131 */     return getINTERVALDS(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 146 */     return getINTERVALDS(paramInt);
/*     */   }
/*     */ 
/*     */   INTERVALDS getINTERVALDS(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 162 */     INTERVALDS localINTERVALDS = null;
/*     */ 
/* 164 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 169 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 176 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 178 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 179 */       int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*     */ 
/* 181 */       byte[] arrayOfByte = new byte[j];
/*     */ 
/* 183 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
/*     */ 
/* 185 */       localINTERVALDS = new INTERVALDS(arrayOfByte);
/*     */     }
/*     */ 
/* 188 */     return localINTERVALDS;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.IntervaldsAccessor
 * JD-Core Version:    0.6.0
 */