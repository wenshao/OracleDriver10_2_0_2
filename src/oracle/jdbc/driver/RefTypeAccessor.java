/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.oracore.OracleType;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.REF;
/*     */ import oracle.sql.StructDescriptor;
/*     */ import oracle.sql.TypeDescriptor;
/*     */ 
/*     */ class RefTypeAccessor extends TypeAccessor
/*     */ {
/*     */   RefTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  29 */     init(paramOracleStatement, 111, 111, paramShort, paramBoolean);
/*  30 */     initForDataAccess(paramInt, 0, paramString);
/*     */   }
/*     */ 
/*     */   RefTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString)
/*     */     throws SQLException
/*     */   {
/*  38 */     init(paramOracleStatement, 111, 111, paramShort, false);
/*  39 */     initForDescribe(111, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);
/*     */ 
/*  41 */     initForDataAccess(0, paramInt1, paramString);
/*     */   }
/*     */ 
/*     */   RefTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/*  49 */     init(paramOracleStatement, 111, 111, paramShort, false);
/*     */ 
/*  51 */     this.describeOtype = paramOracleType;
/*     */ 
/*  53 */     initForDescribe(111, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);
/*     */ 
/*  56 */     this.internalOtype = paramOracleType;
/*     */ 
/*  58 */     initForDataAccess(0, paramInt1, paramString);
/*     */   }
/*     */ 
/*     */   OracleType otypeFromName(String paramString) throws SQLException
/*     */   {
/*  63 */     if (!this.outBind) {
/*  64 */       return TypeDescriptor.getTypeDescriptor(paramString, this.statement.connection).getPickler();
/*     */     }
/*     */ 
/*  67 */     return StructDescriptor.createDescriptor(paramString, this.statement.connection).getOracleTypeADT();
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  74 */     super.initForDataAccess(paramInt1, paramInt2, paramString);
/*     */ 
/*  76 */     this.byteLength = this.statement.connection.refTypeAccessorByteLen;
/*     */   }
/*     */ 
/*     */   REF getREF(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  90 */     REF localREF = null;
/*     */ 
/*  92 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  97 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 104 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 106 */       byte[] arrayOfByte = pickledBytes(paramInt);
/* 107 */       OracleTypeADT localOracleTypeADT = (OracleTypeADT)this.internalOtype;
/*     */ 
/* 109 */       localREF = new REF(localOracleTypeADT.getFullName(), this.statement.connection, arrayOfByte);
/*     */     }
/*     */ 
/* 112 */     return localREF;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 118 */     return getREF(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 124 */     return getREF(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 130 */     return getREF(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RefTypeAccessor
 * JD-Core Version:    0.6.0
 */