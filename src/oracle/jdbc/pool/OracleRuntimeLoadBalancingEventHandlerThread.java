 package oracle.jdbc.pool;
 
 import java.security.AccessController;
 import java.security.PrivilegedActionException;
 import java.security.PrivilegedExceptionAction;
 import java.sql.SQLException;
 import oracle.ons.Notification;
 import oracle.ons.ONSException;
 import oracle.ons.Subscriber;
 
 class OracleRuntimeLoadBalancingEventHandlerThread extends Thread
 {
/*  37 */   private Notification event = null;
/*  38 */   private OracleConnectionCacheManager cacheManager = null;
   String m_service;
/* 141 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
   public static final boolean TRACE = false;
   public static final boolean PRIVATE_TRACE = false;
   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
 
   OracleRuntimeLoadBalancingEventHandlerThread(String paramString)
     throws SQLException
   {
/*  46 */     this.m_service = paramString;
 
/*  48 */     this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
   }
 
   public void run()
   {
/*  55 */     Subscriber localSubscriber = null;
 
/*  58 */     String str = "%\"eventType=database/event/servicemetrics/" + this.m_service + "\"";
 
/*  61 */     while (this.cacheManager.failoverEnabledCacheExists())
     {
       try
       {
/*  66 */         localSubscriber = (Subscriber)AccessController.doPrivileged(new PrivilegedExceptionAction(str)
         {
           private final String val$type;
 
           public Object run()
             throws ONSException
           {
/*  74 */             return new Subscriber(this.val$type, "", 30000L);
           }
 
         });
       }
       catch (PrivilegedActionException localPrivilegedActionException)
       {
       }
 
/*  84 */       if (localSubscriber != null)
       {
         try
         {
/*  88 */           while (this.cacheManager.failoverEnabledCacheExists())
           {
/*  94 */             if ((this.event = localSubscriber.receive(300000L)) != null)
/*  95 */               handleEvent(this.event);
           }
         }
         catch (ONSException localONSException)
         {
/* 100 */           localSubscriber.close();
         }
 
       }
 
       try
       {
/* 110 */         Thread.currentThread(); Thread.sleep(10000L);
       }
       catch (InterruptedException localInterruptedException)
       {
       }
     }
   }
 
   void handleEvent(Notification paramNotification)
   {
     try
     {
/* 129 */       this.cacheManager.parseRuntimeLoadBalancingEvent(this.m_service, paramNotification == null ? null : paramNotification.body());
     }
     catch (SQLException localSQLException)
     {
     }
   }
 }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleRuntimeLoadBalancingEventHandlerThread
 * JD-Core Version:    0.6.0
 */