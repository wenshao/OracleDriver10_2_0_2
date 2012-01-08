/*    */ package oracle.jdbc.pool;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.ConnectionEvent;
/*    */ import javax.sql.ConnectionEventListener;
/*    */ import javax.sql.PooledConnection;
/*    */ 
/*    */ class OracleConnectionCacheEventListener
/*    */   implements ConnectionEventListener, Serializable
/*    */ {
/*    */   static final int CONNECTION_CLOSED_EVENT = 101;
/*    */   static final int CONNECTION_ERROROCCURED_EVENT = 102;
/* 39 */   protected OracleImplicitConnectionCache implicitCache = null;
/*    */ 
/* 99 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*    */ 
/*    */   public OracleConnectionCacheEventListener()
/*    */   {
/* 44 */     this(null);
/*    */   }
/*    */ 
/*    */   public OracleConnectionCacheEventListener(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
/*    */   {
/* 50 */     this.implicitCache = paramOracleImplicitConnectionCache;
/*    */   }
/*    */ 
/*    */   public synchronized void connectionClosed(ConnectionEvent paramConnectionEvent)
/*    */   {
/*    */     try
/*    */     {
/* 69 */       if (this.implicitCache != null)
/*    */       {
/* 71 */         this.implicitCache.reusePooledConnection((PooledConnection)paramConnectionEvent.getSource());
/*    */       }
/*    */     }
/*    */     catch (SQLException localSQLException)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   public synchronized void connectionErrorOccurred(ConnectionEvent paramConnectionEvent)
/*    */   {
/*    */     try
/*    */     {
/* 89 */       if (this.implicitCache != null)
/*    */       {
/* 91 */         this.implicitCache.closePooledConnection((PooledConnection)paramConnectionEvent.getSource());
/*    */       }
/*    */     }
/*    */     catch (SQLException localSQLException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionCacheEventListener
 * JD-Core Version:    0.6.0
 */