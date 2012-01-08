/*    */ package oracle.jdbc.pool;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class OracleFailoverWorkerThread extends Thread
/*    */ {
/* 21 */   protected OracleImplicitConnectionCache implicitCache = null;
/* 22 */   protected int eventType = 0;
/* 23 */   protected String eventServiceName = null;
/* 24 */   protected String instanceNameKey = null;
/* 25 */   protected String databaseNameKey = null;
/* 26 */   protected String hostNameKey = null;
/* 27 */   protected String status = null;
/* 28 */   protected int cardinality = 0;
/*    */ 
/* 66 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*    */ 
/*    */   OracleFailoverWorkerThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
/*    */     throws SQLException
/*    */   {
/* 37 */     this.implicitCache = paramOracleImplicitConnectionCache;
/* 38 */     this.eventType = paramInt1;
/* 39 */     this.instanceNameKey = paramString1;
/* 40 */     this.databaseNameKey = paramString2;
/* 41 */     this.hostNameKey = paramString3;
/* 42 */     this.status = paramString4;
/* 43 */     this.cardinality = paramInt2;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 51 */       if (this.status != null)
/*    */       {
/* 53 */         this.implicitCache.processFailoverEvent(this.eventType, this.instanceNameKey, this.databaseNameKey, this.hostNameKey, this.status, this.cardinality);
/*    */       }
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleFailoverWorkerThread
 * JD-Core Version:    0.6.0
 */