/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.ROWID;
/*     */ 
/*     */ class RowidAccessor extends Accessor
/*     */ {
/*     */   static final int maxLength = 128;
/*     */ 
/*     */   RowidAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  27 */     init(paramOracleStatement, 104, 9, paramShort, paramBoolean);
/*  28 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   RowidAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  35 */     init(paramOracleStatement, 104, 9, paramShort, false);
/*  36 */     initForDescribe(104, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
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
/*  47 */     this.internalTypeMaxLength = 128;
/*     */ 
/*  51 */     this.byteLength = (this.internalTypeMaxLength + 2);
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  65 */     String str = null;
/*     */ 
/*  67 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  72 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  79 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/*  81 */       int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/*  85 */       int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*     */ 
/*  87 */       str = new String(this.rowSpaceByte, i + 2, j);
/*     */     }
/*     */ 
/*  90 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 104 */     return getROWID(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 118 */     return getROWID(paramInt);
/*     */   }
/*     */ 
/*     */   ROWID getROWID(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 132 */     byte[] arrayOfByte = getBytes(paramInt);
/*     */ 
/* 134 */     return arrayOfByte == null ? null : new ROWID(arrayOfByte);
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt) throws SQLException
/*     */   {
/* 139 */     byte[] arrayOfByte = null;
/*     */ 
/* 141 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 146 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 153 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 158 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 159 */       int j = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 161 */       arrayOfByte = new byte[i];
/*     */ 
/* 163 */       System.arraycopy(this.rowSpaceByte, j + 2, arrayOfByte, 0, i);
/*     */     }
/*     */ 
/* 173 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 188 */     return getROWID(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RowidAccessor
 * JD-Core Version:    0.6.0
 */