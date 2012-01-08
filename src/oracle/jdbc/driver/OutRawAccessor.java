/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class OutRawAccessor extends RawCommonAccessor
/*    */ {
/*    */   static final int MAXLENGTH_NEW = 32767;
/*    */   static final int MAXLENGTH_OLD = 32767;
/*    */ 
/*    */   OutRawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2)
/*    */     throws SQLException
/*    */   {
/* 27 */     init(paramOracleStatement, 23, 23, paramShort, true);
/* 28 */     initForDataAccess(paramInt2, paramInt1, null);
/*    */   }
/*    */ 
/*    */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*    */     throws SQLException
/*    */   {
/* 34 */     if (paramInt1 != 0) {
/* 35 */       this.externalType = paramInt1;
/*    */     }
/* 37 */     if (this.statement.connection.getVersionNumber() >= 8000)
/* 38 */       this.internalTypeMaxLength = 32767;
/*    */     else {
/* 40 */       this.internalTypeMaxLength = 32767;
/*    */     }
/* 42 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/* 43 */       this.internalTypeMaxLength = paramInt2;
/*    */     }
/* 45 */     this.byteLength = this.internalTypeMaxLength;
/*    */   }
/*    */ 
/*    */   byte[] getBytes(int paramInt) throws SQLException
/*    */   {
/* 50 */     byte[] arrayOfByte = null;
/*    */ 
/* 52 */     if (this.rowSpaceIndicator == null)
/*    */     {
/* 57 */       DatabaseError.throwSqlException(21);
/*    */     }
/*    */ 
/* 64 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*    */     {
/* 66 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 67 */       int j = this.columnIndex + this.byteLength * paramInt;
/*    */ 
/* 69 */       arrayOfByte = new byte[i];
/*    */ 
/* 71 */       System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
/*    */     }
/*    */ 
/* 74 */     return arrayOfByte;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OutRawAccessor
 * JD-Core Version:    0.6.0
 */