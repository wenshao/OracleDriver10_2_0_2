/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class RawAccessor extends RawCommonAccessor
/*    */ {
/*    */   static final int MAXLENGTH_NEW = 2000;
/*    */   static final int MAXLENGTH_OLD = 255;
/*    */ 
/*    */   RawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*    */     throws SQLException
/*    */   {
/* 27 */     init(paramOracleStatement, 23, 15, paramShort, paramBoolean);
/* 28 */     initForDataAccess(paramInt2, paramInt1, null);
/*    */   }
/*    */ 
/*    */   RawAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*    */     throws SQLException
/*    */   {
/* 35 */     init(paramOracleStatement, 23, 15, paramShort, false);
/* 36 */     initForDescribe(23, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*    */ 
/* 39 */     int i = paramOracleStatement.maxFieldSize;
/*    */ 
/* 41 */     if ((i > 0) && ((paramInt1 == 0) || (i < paramInt1))) {
/* 42 */       paramInt1 = i;
/*    */     }
/* 44 */     initForDataAccess(0, paramInt1, null);
/*    */   }
/*    */ 
/*    */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*    */     throws SQLException
/*    */   {
/* 50 */     if (paramInt1 != 0) {
/* 51 */       this.externalType = paramInt1;
/*    */     }
/* 53 */     if (this.statement.connection.getVersionNumber() >= 8000)
/* 54 */       this.internalTypeMaxLength = 2000;
/*    */     else {
/* 56 */       this.internalTypeMaxLength = 255;
/*    */     }
/* 58 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/* 59 */       this.internalTypeMaxLength = paramInt2;
/*    */     }
/* 61 */     this.byteLength = (this.internalTypeMaxLength + 2);
/*    */   }
/*    */ 
/*    */   byte[] getBytes(int paramInt) throws SQLException
/*    */   {
/* 66 */     byte[] arrayOfByte = null;
/*    */ 
/* 68 */     if (this.rowSpaceIndicator == null)
/*    */     {
/* 73 */       DatabaseError.throwSqlException(21);
/*    */     }
/*    */ 
/* 80 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*    */     {
/* 84 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 85 */       int j = this.columnIndex + this.byteLength * paramInt;
/*    */ 
/* 87 */       arrayOfByte = new byte[i];
/*    */ 
/* 89 */       System.arraycopy(this.rowSpaceByte, j + 2, arrayOfByte, 0, i);
/*    */     }
/*    */ 
/* 92 */     return arrayOfByte;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RawAccessor
 * JD-Core Version:    0.6.0
 */