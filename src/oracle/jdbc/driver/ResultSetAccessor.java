/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class ResultSetAccessor extends Accessor
/*    */ {
/*    */   static final int maxLength = 16;
/*    */ 
/*    */   ResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*    */     throws SQLException
/*    */   {
/* 21 */     init(paramOracleStatement, 102, 116, paramShort, paramBoolean);
/* 22 */     initForDataAccess(paramInt2, paramInt1, null);
/*    */   }
/*    */ 
/*    */   ResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*    */     throws SQLException
/*    */   {
/* 29 */     init(paramOracleStatement, 102, 116, paramShort, false);
/* 30 */     initForDescribe(102, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*    */ 
/* 32 */     initForDataAccess(0, paramInt1, null);
/*    */   }
/*    */ 
/*    */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*    */     throws SQLException
/*    */   {
/* 38 */     if (paramInt1 != 0) {
/* 39 */       this.externalType = paramInt1;
/*    */     }
/* 41 */     this.internalTypeMaxLength = 16;
/*    */ 
/* 43 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/* 44 */       this.internalTypeMaxLength = paramInt2;
/*    */     }
/* 46 */     this.byteLength = this.internalTypeMaxLength;
/*    */   }
/*    */ 
/*    */   ResultSet getCursor(int paramInt)
/*    */     throws SQLException
/*    */   {
/* 59 */     byte[] arrayOfByte = getBytes(paramInt);
/* 60 */     OracleStatement localOracleStatement = this.statement.connection.RefCursorBytesToStatement(arrayOfByte, this.statement);
/*    */ 
/* 63 */     localOracleStatement.doDescribe(false);
/* 64 */     localOracleStatement.prepareAccessors();
/*    */ 
/* 66 */     OracleResultSetImpl localOracleResultSetImpl = new OracleResultSetImpl(localOracleStatement.connection, localOracleStatement);
/*    */ 
/* 69 */     localOracleResultSetImpl.close_statement_on_close = true;
/* 70 */     localOracleStatement.currentResultSet = localOracleResultSetImpl;
/*    */ 
/* 72 */     return localOracleResultSetImpl;
/*    */   }
/*    */ 
/*    */   Object getObject(int paramInt)
/*    */     throws SQLException
/*    */   {
/* 85 */     return getCursor(paramInt);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ResultSetAccessor
 * JD-Core Version:    0.6.0
 */