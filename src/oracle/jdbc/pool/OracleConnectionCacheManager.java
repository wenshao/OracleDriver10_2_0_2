/*      */ package oracle.jdbc.pool;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.sql.ConnectionPoolDataSource;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.ons.ONS;
/*      */ import oracle.ons.ONSException;
/*      */ 
/*      */ public class OracleConnectionCacheManager
/*      */ {
/*   56 */   private static OracleConnectionCacheManager cacheManagerInstance = null;
/*      */ 
/*   58 */   protected Hashtable m_connCache = null;
/*      */   public static final int REFRESH_INVALID_CONNECTIONS = 4096;
/*      */   public static final int REFRESH_ALL_CONNECTIONS = 8192;
/*      */   public static final String PHYSICAL_CONNECTION_CREATED_COUNT = "PhysicalConnectionCreatedCount";
/*      */   public static final String PHYSICAL_CONNECTION_CLOSED_COUNT = "PhysicalConnectionClosedCount";
/*      */   protected static final int FAILOVER_EVENT_TYPE_SERVICE = 256;
/*      */   protected static final int FAILOVER_EVENT_TYPE_HOST = 512;
/*      */   protected static final String EVENT_DELIMITER = "{} =";
/*   76 */   protected OracleFailoverEventHandlerThread failoverEventHandlerThread = null;
/*      */ 
/*   81 */   private static boolean isONSInitializedForRemoteSubscription = false;
/*      */   static final int ORAERROR_END_OF_FILE_ON_COM_CHANNEL = 3113;
/*      */   static final int ORAERROR_NOT_CONNECTED_TO_ORACLE = 3114;
/*      */   static final int ORAERROR_INIT_SHUTDOWN_IN_PROGRESS = 1033;
/*      */   static final int ORAERROR_ORACLE_NOT_AVAILABLE = 1034;
/*      */   static final int ORAERROR_IMMEDIATE_SHUTDOWN_IN_PROGRESS = 1089;
/*      */   static final int ORAERROR_SHUTDOWN_IN_PROGRESS_NO_CONN = 1090;
/*      */   static final int ORAERROR_NET_IO_EXCEPTION = 17002;
/*   94 */   protected int[] fatalErrorCodes = null;
/*   95 */   protected int failoverEnabledCacheCount = 0;
/*      */ 
/* 1170 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*      */ 
/*      */   private OracleConnectionCacheManager()
/*      */   {
/*  105 */     this.m_connCache = new Hashtable();
/*      */   }
/*      */ 
/*      */   public static synchronized OracleConnectionCacheManager getConnectionCacheManagerInstance()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  122 */       if (cacheManagerInstance == null) {
/*  123 */         cacheManagerInstance = new OracleConnectionCacheManager();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RuntimeException localRuntimeException)
/*      */     {
/*      */     }
/*      */ 
/*  131 */     return cacheManagerInstance;
/*      */   }
/*      */ 
/*      */   public String createCache(OracleDataSource paramOracleDataSource, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  151 */     String str = null;
/*      */ 
/*  153 */     if ((paramOracleDataSource == null) || (!paramOracleDataSource.getConnectionCachingEnabled()))
/*      */     {
/*  155 */       DatabaseError.throwSqlException(137);
/*      */     }
/*      */ 
/*  162 */     if (paramOracleDataSource.connCacheName != null)
/*      */     {
/*  164 */       str = paramOracleDataSource.connCacheName;
/*      */     }
/*      */     else
/*      */     {
/*  168 */       str = paramOracleDataSource.dataSourceName + "#0x" + Integer.toHexString(this.m_connCache.size());
/*      */     }
/*      */ 
/*  172 */     createCache(str, paramOracleDataSource, paramProperties);
/*      */ 
/*  174 */     return str;
/*      */   }
/*      */ 
/*      */   public void createCache(String paramString, OracleDataSource paramOracleDataSource, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  195 */     if ((paramOracleDataSource == null) || (!paramOracleDataSource.getConnectionCachingEnabled()))
/*      */     {
/*  197 */       DatabaseError.throwSqlException(137);
/*      */     }
/*      */ 
/*  200 */     if (paramString == null)
/*      */     {
/*  202 */       DatabaseError.throwSqlException(138);
/*      */     }
/*      */ 
/*  206 */     if (this.m_connCache.containsKey(paramString))
/*      */     {
/*  208 */       DatabaseError.throwSqlException(140);
/*      */     }
/*      */ 
/*  211 */     boolean bool = paramOracleDataSource.getFastConnectionFailoverEnabled();
/*      */ 
/*  219 */     if ((bool) && (this.failoverEventHandlerThread == null))
/*      */     {
/*  223 */       localObject1 = paramOracleDataSource.getONSConfiguration();
/*      */ 
/*  227 */       if ((localObject1 != null) && (!((String)localObject1).equals("")))
/*      */       {
/*  235 */         synchronized (this)
/*      */         {
/*  237 */           if (!isONSInitializedForRemoteSubscription)
/*      */           {
/*      */             try
/*      */             {
/*  242 */               AccessController.doPrivileged(new PrivilegedExceptionAction((String)localObject1)
/*      */               {
/*      */                 private final String val$onsConfigStr;
/*      */ 
/*      */                 public Object run()
/*      */                   throws ONSException
/*      */                 {
/*  250 */                   ONS localONS = new ONS(this.val$onsConfigStr);
/*  251 */                   return null;
/*      */                 }
/*      */               });
/*      */             }
/*      */             catch (PrivilegedActionException localPrivilegedActionException) {
/*  257 */               DatabaseError.throwSqlException(175, localPrivilegedActionException);
/*      */             }
/*      */ 
/*  261 */             isONSInitializedForRemoteSubscription = true;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  266 */       this.failoverEventHandlerThread = new OracleFailoverEventHandlerThread();
/*      */     }
/*      */ 
/*  270 */     Object localObject1 = new OracleImplicitConnectionCache(paramOracleDataSource, paramProperties);
/*      */ 
/*  273 */     ((OracleImplicitConnectionCache)localObject1).cacheName = paramString;
/*  274 */     paramOracleDataSource.odsCache = ((OracleImplicitConnectionCache)localObject1);
/*      */ 
/*  277 */     this.m_connCache.put(paramString, localObject1);
/*      */ 
/*  285 */     if (bool)
/*      */     {
/*  290 */       checkAndStartThread(this.failoverEventHandlerThread);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeCache(String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  314 */     OracleImplicitConnectionCache localOracleImplicitConnectionCache = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.remove(paramString) : null;
/*      */ 
/*  317 */     if (localOracleImplicitConnectionCache != null)
/*      */     {
/*  319 */       localOracleImplicitConnectionCache.disableConnectionCache();
/*      */ 
/*  322 */       if (paramLong > 0L)
/*      */       {
/*      */         try
/*      */         {
/*  326 */           Thread.currentThread(); Thread.sleep(paramLong * 1000L);
/*      */         }
/*      */         catch (InterruptedException localInterruptedException)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  336 */       if (localOracleImplicitConnectionCache.cacheEnabledDS.getFastConnectionFailoverEnabled()) {
/*  337 */         cleanupFCFThreads(localOracleImplicitConnectionCache);
/*      */       }
/*      */ 
/*  340 */       localOracleImplicitConnectionCache.closeConnectionCache();
/*      */ 
/*  342 */       localOracleImplicitConnectionCache = null;
/*      */     }
/*      */     else
/*      */     {
/*  346 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reinitializeCache(String paramString, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  364 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  367 */     if (localObject != null)
/*      */     {
/*  369 */       disableCache(paramString);
/*  370 */       localObject.reinitializeCacheConnections(paramProperties);
/*  371 */       enableCache(paramString);
/*      */     }
/*      */     else
/*      */     {
/*  375 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean existsCache(String paramString)
/*      */     throws SQLException
/*      */   {
/*  394 */     return this.m_connCache.containsKey(paramString);
/*      */   }
/*      */ 
/*      */   public void enableCache(String paramString)
/*      */     throws SQLException
/*      */   {
/*  409 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  412 */     if (localObject != null)
/*      */     {
/*  414 */       localObject.enableConnectionCache();
/*      */     }
/*      */     else
/*      */     {
/*  418 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void disableCache(String paramString)
/*      */     throws SQLException
/*      */   {
/*  436 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  439 */     if (localObject != null)
/*      */     {
/*  441 */       localObject.disableConnectionCache();
/*      */     }
/*      */     else
/*      */     {
/*  445 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refreshCache(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  465 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  468 */     if (localObject != null)
/*      */     {
/*  470 */       if ((paramInt == 4096) || (paramInt == 8192))
/*      */       {
/*  472 */         localObject.refreshCacheConnections(paramInt);
/*      */       }
/*  474 */       else DatabaseError.throwSqlException(68);
/*      */     }
/*      */     else
/*      */     {
/*  478 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void purgeCache(String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  500 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  503 */     if (localObject != null)
/*      */     {
/*  505 */       localObject.purgeCacheConnections(paramBoolean);
/*      */     }
/*      */     else
/*      */     {
/*  509 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Properties getCacheProperties(String paramString)
/*      */     throws SQLException
/*      */   {
/*  529 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  532 */     if (localObject != null)
/*      */     {
/*  534 */       return localObject.getConnectionCacheProperties();
/*      */     }
/*      */ 
/*  538 */     DatabaseError.throwSqlException(141);
/*      */ 
/*  541 */     return null;
/*      */   }
/*      */ 
/*      */   public String[] getCacheNameList()
/*      */     throws SQLException
/*      */   {
/*  556 */     return (String[])this.m_connCache.keySet().toArray(new String[this.m_connCache.size()]);
/*      */   }
/*      */ 
/*      */   public int getNumberOfAvailableConnections(String paramString)
/*      */     throws SQLException
/*      */   {
/*  573 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  576 */     if (localObject != null)
/*      */     {
/*  578 */       return localObject.cacheSize;
/*      */     }
/*      */ 
/*  582 */     DatabaseError.throwSqlException(141);
/*      */ 
/*  585 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getNumberOfActiveConnections(String paramString)
/*      */     throws SQLException
/*      */   {
/*  600 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  603 */     if (localObject != null)
/*      */     {
/*  605 */       return localObject.getNumberOfCheckedOutConnections();
/*      */     }
/*      */ 
/*  609 */     DatabaseError.throwSqlException(141);
/*      */ 
/*  612 */     return 0;
/*      */   }
/*      */ 
/*      */   public synchronized void setConnectionPoolDataSource(String paramString, ConnectionPoolDataSource paramConnectionPoolDataSource)
/*      */     throws SQLException
/*      */   {
/*  636 */     Object localObject = paramString != null ? (OracleImplicitConnectionCache)this.m_connCache.get(paramString) : null;
/*      */ 
/*  639 */     if (localObject != null)
/*      */     {
/*  641 */       if (localObject.cacheSize > 0)
/*      */       {
/*  643 */         DatabaseError.throwSqlException(78);
/*      */       }
/*      */       else
/*      */       {
/*  647 */         ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).makeURL();
/*  648 */         ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).setURL(((OracleConnectionPoolDataSource)paramConnectionPoolDataSource).url);
/*      */ 
/*  650 */         localObject.connectionPoolDS = ((OracleConnectionPoolDataSource)paramConnectionPoolDataSource);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  655 */       DatabaseError.throwSqlException(141);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void verifyAndHandleEvent(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  676 */     Object localObject1 = null;
/*  677 */     String str1 = null;
/*  678 */     String str2 = null;
/*  679 */     String str3 = null;
/*  680 */     Object localObject2 = null;
/*      */ 
/*  682 */     int i = 0;
/*  683 */     StringTokenizer localStringTokenizer = null;
/*      */     try
/*      */     {
/*  690 */       localStringTokenizer = new StringTokenizer(new String(paramArrayOfByte, "UTF-8"), "{} =", true);
/*      */     }
/*      */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*      */     {
/*      */     }
/*      */ 
/*  699 */     String str4 = null;
/*  700 */     String str5 = null;
/*  701 */     String str6 = null;
/*      */ 
/*  705 */     while (localStringTokenizer.hasMoreTokens())
/*      */     {
/*  707 */       str5 = null;
/*  708 */       str4 = localStringTokenizer.nextToken();
/*  709 */       if ((str4.equals("=")) && (localStringTokenizer.hasMoreTokens()))
/*      */       {
/*  711 */         str5 = localStringTokenizer.nextToken();
/*      */       }
/*      */       else
/*      */       {
/*  715 */         str6 = str4;
/*      */       }
/*      */ 
/*  718 */       if ((str6.equalsIgnoreCase("version")) && (str5 != null) && (!str5.equals("1.0")))
/*      */       {
/*  721 */         DatabaseError.throwSqlException(146);
/*      */       }
/*      */ 
/*  724 */       if ((str6.equalsIgnoreCase("service")) && (str5 != null)) {
/*  725 */         localObject1 = str5;
/*      */       }
/*  727 */       if ((str6.equalsIgnoreCase("instance")) && (str5 != null) && (!str5.equals(" ")))
/*      */       {
/*  730 */         str1 = str5.toLowerCase().intern();
/*      */       }
/*      */ 
/*  733 */       if ((str6.equalsIgnoreCase("database")) && (str5 != null)) {
/*  734 */         str2 = str5.toLowerCase().intern();
/*      */       }
/*  736 */       if ((str6.equalsIgnoreCase("host")) && (str5 != null)) {
/*  737 */         str3 = str5.toLowerCase().intern();
/*      */       }
/*  739 */       if ((str6.equalsIgnoreCase("status")) && (str5 != null)) {
/*  740 */         localObject2 = str5;
/*      */       }
/*  742 */       if ((!str6.equalsIgnoreCase("card")) || (str5 == null))
/*      */         continue;
/*      */       try
/*      */       {
/*  746 */         i = Integer.parseInt(str5);
/*      */       }
/*      */       catch (NumberFormatException localNumberFormatException)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  756 */     invokeFailoverProcessingThreads(paramInt, localObject1, str1, str2, str3, localObject2, i);
/*      */ 
/*  759 */     localStringTokenizer = null;
/*      */   }
/*      */ 
/*      */   private void invokeFailoverProcessingThreads(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  774 */     OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;
/*  775 */     int i = 0;
/*  776 */     int j = 0;
/*      */ 
/*  778 */     if (paramInt1 == 256) {
/*  779 */       i = 1;
/*      */     }
/*  781 */     if (paramInt1 == 512) {
/*  782 */       j = 1;
/*      */     }
/*  784 */     Iterator localIterator = this.m_connCache.values().iterator();
/*      */ 
/*  786 */     while (localIterator.hasNext())
/*      */     {
/*  788 */       localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)localIterator.next();
/*      */ 
/*  798 */       if (((i == 0) || (!paramString1.equalsIgnoreCase(localOracleImplicitConnectionCache.dataSourceServiceName))) && (j == 0))
/*      */       {
/*      */         continue;
/*      */       }
/*  802 */       OracleFailoverWorkerThread localOracleFailoverWorkerThread = new OracleFailoverWorkerThread(localOracleImplicitConnectionCache, paramInt1, paramString2, paramString3, paramString4, paramString5, paramInt2);
/*      */ 
/*  806 */       checkAndStartThread(localOracleFailoverWorkerThread);
/*      */ 
/*  808 */       localOracleImplicitConnectionCache.failoverWorkerThread = localOracleFailoverWorkerThread;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void checkAndStartThread(Thread paramThread)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  825 */       if (!paramThread.isAlive())
/*      */       {
/*  827 */         paramThread.setDaemon(true);
/*  828 */         paramThread.start();
/*      */       }
/*      */     }
/*      */     catch (IllegalThreadStateException localIllegalThreadStateException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean failoverEnabledCacheExists()
/*      */   {
/*  847 */     return this.failoverEnabledCacheCount > 0;
/*      */   }
/*      */ 
/*      */   protected void parseRuntimeLoadBalancingEvent(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  863 */     OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;
/*  864 */     Enumeration localEnumeration = this.m_connCache.elements();
/*      */ 
/*  866 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*      */       try
/*      */       {
/*  870 */         localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)localEnumeration.nextElement();
/*  871 */         if (paramString.equalsIgnoreCase(localOracleImplicitConnectionCache.dataSourceServiceName))
/*      */         {
/*  873 */           if (paramArrayOfByte == null)
/*  874 */             localOracleImplicitConnectionCache.zapRLBInfo();
/*      */           else
/*  876 */             retrieveServiceMetrics(localOracleImplicitConnectionCache, paramArrayOfByte);
/*      */         }
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void retrieveServiceMetrics(OracleImplicitConnectionCache paramOracleImplicitConnectionCache, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  901 */     StringTokenizer localStringTokenizer = null;
/*  902 */     String str1 = null;
/*  903 */     String str2 = null;
/*  904 */     int i = 0;
/*  905 */     int j = 0;
/*  906 */     int k = 0;
/*      */     try
/*      */     {
/*  910 */       localStringTokenizer = new StringTokenizer(new String(paramArrayOfByte, "UTF-8"), "{} =", true);
/*      */     }
/*      */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*      */     {
/*      */     }
/*      */ 
/*  919 */     String str3 = null;
/*  920 */     String str4 = null;
/*  921 */     String str5 = null;
/*      */ 
/*  923 */     while (localStringTokenizer.hasMoreTokens())
/*      */     {
/*  925 */       str4 = null;
/*  926 */       str3 = localStringTokenizer.nextToken();
/*      */ 
/*  928 */       if ((str3.equals("=")) && (localStringTokenizer.hasMoreTokens()))
/*      */       {
/*  930 */         str4 = localStringTokenizer.nextToken();
/*      */       } else {
/*  932 */         if (str3.equals("}"))
/*      */         {
/*  935 */           if (k == 0)
/*      */             continue;
/*  937 */           paramOracleImplicitConnectionCache.updateDatabaseInstance(str2, str1, i, j);
/*  938 */           k = 0; continue;
/*      */         }
/*      */ 
/*  942 */         if ((str3.equals("{")) || (str3.equals(" ")))
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  948 */         str5 = str3;
/*  949 */         k = 1;
/*      */       }
/*      */ 
/*  952 */       if ((str5.equalsIgnoreCase("version")) && (str4 != null))
/*      */       {
/*  954 */         if (!str4.equals("1.0"))
/*      */         {
/*  956 */           DatabaseError.throwSqlException(146);
/*      */         }
/*      */       }
/*      */ 
/*  960 */       if ((str5.equalsIgnoreCase("database")) && (str4 != null)) {
/*  961 */         str2 = str4.toLowerCase().intern();
/*      */       }
/*  963 */       if ((str5.equalsIgnoreCase("instance")) && (str4 != null)) {
/*  964 */         str1 = str4.toLowerCase().intern();
/*      */       }
/*  966 */       if ((str5.equalsIgnoreCase("percent")) && (str4 != null))
/*      */       {
/*      */         try
/*      */         {
/*  970 */           i = Integer.parseInt(str4);
/*  971 */           if (i == 0) i = 1;
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  980 */       if ((!str5.equalsIgnoreCase("flag")) || (str4 == null))
/*      */         continue;
/*  982 */       if (str4.equalsIgnoreCase("good")) {
/*  983 */         j = 1; continue;
/*  984 */       }if (str4.equalsIgnoreCase("violating")) {
/*  985 */         j = 3; continue;
/*  986 */       }if (str4.equalsIgnoreCase("NO_DATA")) {
/*  987 */         j = 4; continue;
/*  988 */       }if (str4.equalsIgnoreCase("UNKNOWN")) {
/*  989 */         j = 2; continue;
/*  990 */       }if (str4.equalsIgnoreCase("BLOCKED")) {
/*  991 */         j = 5;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  996 */     paramOracleImplicitConnectionCache.processDatabaseInstances();
/*      */   }
/*      */ 
/*      */   protected void cleanupFCFThreads(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
/*      */     throws SQLException
/*      */   {
/* 1007 */     cleanupFCFWorkerThread(paramOracleImplicitConnectionCache);
/* 1008 */     paramOracleImplicitConnectionCache.cleanupRLBThreads();
/*      */ 
/* 1011 */     if (this.failoverEnabledCacheCount <= 0) {
/* 1012 */       cleanupFCFEventHandlerThread();
/*      */     }
/*      */ 
/* 1015 */     this.failoverEnabledCacheCount -= 1;
/*      */   }
/*      */ 
/*      */   protected void cleanupFCFWorkerThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
/*      */     throws SQLException
/*      */   {
/* 1028 */     if (paramOracleImplicitConnectionCache.failoverWorkerThread != null)
/*      */     {
/*      */       try
/*      */       {
/* 1032 */         if (paramOracleImplicitConnectionCache.failoverWorkerThread.isAlive()) {
/* 1033 */           paramOracleImplicitConnectionCache.failoverWorkerThread.join();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (InterruptedException localInterruptedException)
/*      */       {
/*      */       }
/*      */ 
/* 1041 */       paramOracleImplicitConnectionCache.failoverWorkerThread = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void cleanupFCFEventHandlerThread()
/*      */     throws SQLException
/*      */   {
/* 1058 */     if (this.failoverEventHandlerThread != null)
/*      */     {
/*      */       try
/*      */       {
/* 1062 */         this.failoverEventHandlerThread.interrupt();
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 1070 */       this.failoverEventHandlerThread = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isFatalConnectionError(SQLException paramSQLException)
/*      */   {
/* 1086 */     int i = 0;
/* 1087 */     int j = paramSQLException.getErrorCode();
/*      */ 
/* 1090 */     if ((j == 3113) || (j == 3114) || (j == 1033) || (j == 1034) || (j == 1089) || (j == 1090) || (j == 17002))
/*      */     {
/* 1098 */       i = 1;
/*      */     }
/*      */ 
/* 1102 */     if ((i == 0) && (this.fatalErrorCodes != null))
/*      */     {
/* 1104 */       for (int k = 0; k < this.fatalErrorCodes.length; k++) {
/* 1105 */         if (j != this.fatalErrorCodes[k])
/*      */           continue;
/* 1107 */         i = 1;
/* 1108 */         break;
/*      */       }
/*      */     }
/*      */ 
/* 1112 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized void setConnectionErrorCodes(int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/* 1125 */     if (paramArrayOfInt != null)
/* 1126 */       this.fatalErrorCodes = paramArrayOfInt;
/*      */   }
/*      */ 
/*      */   public int[] getConnectionErrorCodes()
/*      */     throws SQLException
/*      */   {
/* 1140 */     return this.fatalErrorCodes;
/*      */   }
/*      */ 
/*      */   public Map getStatistics(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1157 */     Map localMap = null;
/* 1158 */     OracleImplicitConnectionCache localOracleImplicitConnectionCache = null;
/*      */ 
/* 1160 */     if ((this.m_connCache != null) && ((localOracleImplicitConnectionCache = (OracleImplicitConnectionCache)this.m_connCache.get(paramString)) != null))
/*      */     {
/* 1162 */       localMap = localOracleImplicitConnectionCache.getStatistics();
/*      */     }
/* 1164 */     return localMap;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleConnectionCacheManager
 * JD-Core Version:    0.6.0
 */