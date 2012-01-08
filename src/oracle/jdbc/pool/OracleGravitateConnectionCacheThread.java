/*    */ package oracle.jdbc.pool;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class OracleGravitateConnectionCacheThread extends Thread
/*    */ {
/* 20 */   protected OracleImplicitConnectionCache implicitCache = null;
/*    */ 
/* 38 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*    */ 
/*    */   OracleGravitateConnectionCacheThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
/*    */     throws SQLException
/*    */   {
/* 27 */     this.implicitCache = paramOracleImplicitConnectionCache;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 33 */     this.implicitCache.gravitateCache();
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleGravitateConnectionCacheThread
 * JD-Core Version:    0.6.0
 */