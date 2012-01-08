/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class NumberAccessor extends NumberCommonAccessor
/*    */ {
/*    */   NumberAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*    */     throws SQLException
/*    */   {
/* 18 */     init(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*    */   }
/*    */ 
/*    */   NumberAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*    */     throws SQLException
/*    */   {
/* 29 */     init(paramOracleStatement, 2, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.NumberAccessor
 * JD-Core Version:    0.6.0
 */