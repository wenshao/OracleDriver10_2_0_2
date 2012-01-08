/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T2CResultSetAccessor extends ResultSetAccessor
/*    */ {
/*    */   T2CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*    */     throws SQLException
/*    */   {
/* 22 */     super(paramOracleStatement, paramInt1 * 2, paramShort, paramInt2, paramBoolean);
/*    */   }
/*    */ 
/*    */   T2CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*    */     throws SQLException
/*    */   {
/* 29 */     super(paramOracleStatement, paramInt1 * 2, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*    */   }
/*    */ 
/*    */   byte[] getBytes(int paramInt)
/*    */     throws SQLException
/*    */   {
/* 43 */     byte[] arrayOfByte = null;
/*    */ 
/* 45 */     if (this.rowSpaceIndicator == null)
/*    */     {
/* 50 */       DatabaseError.throwSqlException(21);
/*    */     }
/*    */ 
/* 56 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*    */     {
/* 58 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 59 */       int j = ((T2CConnection)this.statement.connection).byteAlign;
/* 60 */       int k = this.columnIndex + (j - 1) & (j - 1 ^ 0xFFFFFFFF);
/*    */ 
/* 62 */       int m = k + i * paramInt;
/*    */ 
/* 64 */       arrayOfByte = new byte[i];
/* 65 */       System.arraycopy(this.rowSpaceByte, m, arrayOfByte, 0, i);
/*    */     }
/*    */ 
/* 68 */     return arrayOfByte;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CResultSetAccessor
 * JD-Core Version:    0.6.0
 */