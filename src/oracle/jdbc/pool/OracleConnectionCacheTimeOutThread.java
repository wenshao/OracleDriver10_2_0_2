/*     */ package oracle.jdbc.pool;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Stack;
/*     */ import javax.sql.PooledConnection;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ 
/*     */ public class OracleConnectionCacheTimeOutThread extends Thread
/*     */   implements Serializable
/*     */ {
/*  41 */   private OracleConnectionCacheImpl occi = null;
/*     */ 
/* 260 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   OracleConnectionCacheTimeOutThread(OracleConnectionCacheImpl paramOracleConnectionCacheImpl)
/*     */     throws SQLException
/*     */   {
/*  49 */     this.occi = paramOracleConnectionCacheImpl;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  58 */     long l1 = 0L;
/*  59 */     long l2 = 0L;
/*  60 */     int i = 1;
/*     */     try
/*     */     {
/*  72 */       while (i != 0)
/*     */       {
/*  76 */         if ((l1 = this.occi.getCacheTimeToLiveTimeout()) > 0L) {
/*  77 */           runTimeToLiveTimeOut(l1);
/*     */         }
/*     */ 
/*  81 */         if ((l2 = this.occi.getCacheInactivityTimeout()) > 0L) {
/*  82 */           runInactivityTimeOut(l2);
/*     */         }
/*     */ 
/*  86 */         sleep(this.occi.getThreadWakeUpInterval() * 1000L);
/*     */ 
/*  91 */         if ((this.occi.cache != null) && ((l1 > 0L) || (l2 > 0L)))
/*     */           continue;
/*  93 */         i = 0;
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void runTimeToLiveTimeOut(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 112 */     long l1 = 0L;
/* 113 */     long l2 = 0L;
/* 114 */     int i = 0;
/* 115 */     PooledConnection localPooledConnection = null;
/*     */ 
/* 121 */     if ((i = this.occi.getActiveSize()) > 0)
/*     */     {
/* 125 */       Enumeration localEnumeration = this.occi.activeCache.keys();
/*     */ 
/* 127 */       while (localEnumeration.hasMoreElements())
/*     */       {
/* 129 */         localPooledConnection = (PooledConnection)localEnumeration.nextElement();
/*     */ 
/* 131 */         Connection localConnection = ((OraclePooledConnection)localPooledConnection).getLogicalHandle();
/*     */ 
/* 134 */         if (localConnection != null) {
/* 135 */           l2 = ((OracleConnection)localConnection).getStartTime();
/*     */         }
/* 137 */         l1 = System.currentTimeMillis();
/*     */ 
/* 140 */         if (l1 - l2 <= paramLong * 1000L)
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 151 */           ((OracleConnection)localConnection).cancel();
/*     */         }
/*     */         catch (SQLException localSQLException1)
/*     */         {
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 164 */           if (!localConnection.getAutoCommit()) {
/* 165 */             ((OracleConnection)localConnection).rollback();
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (SQLException localSQLException2)
/*     */         {
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 178 */           localConnection.close();
/*     */         }
/*     */         catch (SQLException localSQLException3)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void runInactivityTimeOut(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 197 */     long l1 = 0L;
/* 198 */     long l2 = 0L;
/* 199 */     long l3 = paramLong * 1000L;
/* 200 */     OraclePooledConnection localOraclePooledConnection = null;
/*     */     try
/*     */     {
/* 210 */       if ((this.occi.cache != null) && (this.occi.cache.size() > 0) && (this.occi.cacheSize > this.occi._MIN_LIMIT))
/*     */       {
/* 213 */         Enumeration localEnumeration = this.occi.cache.elements();
/*     */ 
/* 216 */         while ((this.occi.cacheSize > this.occi._MIN_LIMIT) && (localEnumeration.hasMoreElements()))
/*     */         {
/* 218 */           localOraclePooledConnection = (OraclePooledConnection)localEnumeration.nextElement();
/* 219 */           l1 = localOraclePooledConnection.getLastAccessedTime();
/* 220 */           l2 = System.currentTimeMillis();
/*     */ 
/* 224 */           if (l2 - l1 <= l3)
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 234 */             this.occi.closeSingleConnection(localOraclePooledConnection, false);
/*     */           }
/*     */           catch (SQLException localSQLException)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (NoSuchElementException localNoSuchElementException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionCacheTimeOutThread
 * JD-Core Version:    0.6.0
 */