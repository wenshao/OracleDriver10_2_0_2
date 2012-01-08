/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.pool.OraclePooledConnection;
/*     */ 
/*     */ class ClosedConnection extends PhysicalConnection
/*     */ {
/*     */   ClosedConnection()
/*     */   {
/*  28 */     this.lifecycle = 4;
/*     */   }
/*     */ 
/*     */   void initializePassword(String paramString)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
/*     */     throws SQLException
/*     */   {
/*  39 */     DatabaseError.throwSqlException(8);
/*     */ 
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   int getDefaultStreamChunkSize()
/*     */   {
/*  46 */     return -1;
/*     */   }
/*     */ 
/*     */   short doGetVersionNumber() throws SQLException
/*     */   {
/*  51 */     DatabaseError.throwSqlException(8);
/*     */ 
/*  53 */     return -1;
/*     */   }
/*     */ 
/*     */   String doGetDatabaseProductVersion() throws SQLException
/*     */   {
/*  58 */     DatabaseError.throwSqlException(8);
/*     */ 
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   void doRollback() throws SQLException
/*     */   {
/*  65 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   void doCommit() throws SQLException
/*     */   {
/*  70 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   void doSetAutoCommit(boolean paramBoolean) throws SQLException
/*     */   {
/*  75 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   void doCancel() throws SQLException
/*     */   {
/*  80 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   void doAbort()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   void open(OracleStatement paramOracleStatement) throws SQLException
/*     */   {
/*  90 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   void logon() throws SQLException
/*     */   {
/*  95 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ 
/*     */   public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
/*     */     throws SQLException
/*     */   {
/* 101 */     DatabaseError.throwSqlException(8);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ClosedConnection
 * JD-Core Version:    0.6.0
 */