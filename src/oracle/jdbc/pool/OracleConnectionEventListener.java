/*     */ package oracle.jdbc.pool;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.ConnectionEvent;
/*     */ import javax.sql.ConnectionEventListener;
/*     */ import javax.sql.DataSource;
/*     */ import javax.sql.PooledConnection;
/*     */ 
/*     */ public class OracleConnectionEventListener
/*     */   implements ConnectionEventListener, Serializable
/*     */ {
/*     */   static final int _CLOSED_EVENT = 1;
/*     */   static final int _ERROROCCURED_EVENT = 2;
/*  55 */   private DataSource dataSource = null;
/*     */ 
/*  57 */   protected long lastCleanupTime = -1L;
/*     */ 
/* 181 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   public OracleConnectionEventListener()
/*     */   {
/*  68 */     this.dataSource = null;
/*     */   }
/*     */ 
/*     */   public OracleConnectionEventListener(DataSource paramDataSource)
/*     */   {
/*  83 */     this.dataSource = paramDataSource;
/*     */   }
/*     */ 
/*     */   public void setDataSource(DataSource paramDataSource)
/*     */   {
/*  97 */     this.dataSource = paramDataSource;
/*     */   }
/*     */ 
/*     */   public void connectionClosed(ConnectionEvent paramConnectionEvent)
/*     */   {
/*     */     try
/*     */     {
/* 118 */       if ((this.dataSource != null) && ((this.dataSource instanceof OracleConnectionCache)))
/* 119 */         ((OracleConnectionCache)this.dataSource).reusePooledConnection((PooledConnection)paramConnectionEvent.getSource());
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void connectionErrorOccurred(ConnectionEvent paramConnectionEvent)
/*     */   {
/*     */     try
/*     */     {
/* 141 */       if ((this.dataSource != null) && ((this.dataSource instanceof OracleConnectionCache)))
/* 142 */         ((OracleConnectionCache)this.dataSource).closePooledConnection((PooledConnection)paramConnectionEvent.getSource());
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 146 */       cleanupInvalidConnections(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized void cleanupInvalidConnections(SQLException paramSQLException)
/*     */   {
/*     */     try
/*     */     {
/* 159 */       if ((this.dataSource != null) && ((this.dataSource instanceof OracleConnectionCacheImpl)))
/*     */       {
/* 162 */         long l1 = System.currentTimeMillis();
/* 163 */         long l2 = ((OracleConnectionCacheImpl)this.dataSource).getConnectionCleanupInterval();
/*     */ 
/* 166 */         if (l1 - this.lastCleanupTime > l2 * 1000L)
/*     */         {
/* 168 */           if (((OracleConnectionCacheImpl)this.dataSource).isFatalConnectionError(paramSQLException)) {
/* 169 */             ((OracleConnectionCacheImpl)this.dataSource).closeConnections();
/*     */           }
/* 171 */           this.lastCleanupTime = System.currentTimeMillis();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionEventListener
 * JD-Core Version:    0.6.0
 */