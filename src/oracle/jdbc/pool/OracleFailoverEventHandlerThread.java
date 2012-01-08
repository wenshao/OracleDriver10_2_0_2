/*     */ package oracle.jdbc.pool;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.sql.SQLException;
/*     */ import oracle.ons.Notification;
/*     */ import oracle.ons.ONSException;
/*     */ import oracle.ons.Subscriber;
/*     */ 
/*     */ class OracleFailoverEventHandlerThread extends Thread
/*     */ {
/*  37 */   private Notification event = null;
/*  38 */   private OracleConnectionCacheManager cacheManager = null;
/*     */ 
/* 142 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   OracleFailoverEventHandlerThread()
/*     */     throws SQLException
/*     */   {
/*  44 */     this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  51 */     Subscriber localSubscriber = null;
/*     */ 
/*  56 */     while (this.cacheManager.failoverEnabledCacheExists())
/*     */     {
/*     */       try
/*     */       {
/*  61 */         localSubscriber = (Subscriber)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Object run()
/*     */             throws ONSException
/*     */           {
/*  69 */             return new Subscriber("(%\"eventType=database/event/service\")|(%\"eventType=database/event/host\")", "", 30000L);
/*     */           }
/*     */ 
/*     */         });
/*     */       }
/*     */       catch (PrivilegedActionException localPrivilegedActionException)
/*     */       {
/*     */       }
/*     */ 
/*  79 */       if (localSubscriber != null)
/*     */       {
/*     */         try
/*     */         {
/*  83 */           while (this.cacheManager.failoverEnabledCacheExists())
/*     */           {
/*  86 */             if ((this.event = localSubscriber.receive(true)) != null)
/*  87 */               handleEvent(this.event);
/*     */           }
/*     */         }
/*     */         catch (ONSException localONSException)
/*     */         {
/*  92 */           localSubscriber.close();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 102 */         Thread.currentThread(); Thread.sleep(10000L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void handleEvent(Notification paramNotification)
/*     */   {
/*     */     try
/*     */     {
/* 122 */       int i = 0;
/*     */ 
/* 124 */       if (paramNotification.type().equalsIgnoreCase("database/event/service"))
/* 125 */         i = 256;
/* 126 */       else if (paramNotification.type().equalsIgnoreCase("database/event/host")) {
/* 127 */         i = 512;
/*     */       }
/* 129 */       if (i != 0)
/* 130 */         this.cacheManager.verifyAndHandleEvent(i, paramNotification.body());
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleFailoverEventHandlerThread
 * JD-Core Version:    0.6.0
 */