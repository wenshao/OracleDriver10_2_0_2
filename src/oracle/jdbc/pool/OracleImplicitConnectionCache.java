/*      */ package oracle.jdbc.pool;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.sql.PooledConnection;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.jdbc.xa.client.OracleXADataSource;
/*      */ 
/*      */ class OracleImplicitConnectionCache
/*      */ {
/*   49 */   protected OracleDataSource cacheEnabledDS = null;
/*   50 */   protected String cacheName = null;
/*   51 */   protected OracleConnectionPoolDataSource connectionPoolDS = null;
/*   52 */   protected boolean fastConnectionFailoverEnabled = false;
/*      */ 
/*   55 */   protected String defaultUser = null;
/*   56 */   protected String defaultPassword = null;
/*      */   protected static final int DEFAULT_MIN_LIMIT = 0;
/*      */   protected static final int DEFAULT_MAX_LIMIT = 2147483647;
/*      */   protected static final int DEFAULT_INITIAL_LIMIT = 0;
/*      */   protected static final int DEFAULT_MAX_STATEMENTS_LIMIT = 0;
/*      */   protected static final int DEFAULT_INACTIVITY_TIMEOUT = 0;
/*      */   protected static final int DEFAULT_TIMETOLIVE_TIMEOUT = 0;
/*      */   protected static final int DEFAULT_ABANDONED_CONN_TIMEOUT = 0;
/*      */   protected static final int DEFAULT_CONNECTION_WAIT_TIMEOUT = 0;
/*      */   protected static final String DEFAULT_ATTRIBUTE_WEIGHT = "0";
/*      */   protected static final int DEFAULT_LOWER_THRESHOLD_LIMIT = 20;
/*      */   protected static final int DEFAULT_PROPERTY_CHECK_INTERVAL = 900;
/*      */   protected static final int CLOSE_AND_REMOVE_ALL_CONNECTIONS = 1;
/*      */   protected static final int CLOSE_AND_REMOVE_FAILOVER_CONNECTIONS = 2;
/*      */   protected static final int PROCESS_INACTIVITY_TIMEOUT = 4;
/*      */   protected static final int CLOSE_AND_REMOVE_N_CONNECTIONS = 8;
/*      */   protected static final int DISABLE_STATEMENT_CACHING = 16;
/*      */   protected static final int RESET_STATEMENT_CACHE_SIZE = 18;
/*      */   protected static final int CLOSE_AND_REMOVE_RLB_CONNECTIONS = 24;
/*      */   private static final String ATTRKEY_DELIM = "0xffff";
/*   82 */   protected int cacheMinLimit = 0;
/*   83 */   protected int cacheMaxLimit = 2147483647;
/*   84 */   protected int cacheInitialLimit = 0;
/*   85 */   protected int cacheMaxStatementsLimit = 0;
/*   86 */   protected Properties cacheAttributeWeights = null;
/*   87 */   protected int cacheInactivityTimeout = 0;
/*   88 */   protected int cacheTimeToLiveTimeout = 0;
/*   89 */   protected int cacheAbandonedConnectionTimeout = 0;
/*   90 */   protected int cacheLowerThresholdLimit = 20;
/*   91 */   protected int cachePropertyCheckInterval = 900;
/*   92 */   protected boolean cacheClosestConnectionMatch = false;
/*   93 */   protected boolean cacheValidateConnection = false;
/*   94 */   protected int cacheConnectionWaitTimeout = 0;
/*      */   static final String MIN_LIMIT_KEY = "MinLimit";
/*      */   static final String MAX_LIMIT_KEY = "MaxLimit";
/*      */   static final String INITIAL_LIMIT_KEY = "InitialLimit";
/*      */   static final String MAX_STATEMENTS_LIMIT_KEY = "MaxStatementsLimit";
/*      */   static final String ATTRIBUTE_WEIGHTS_KEY = "AttributeWeights";
/*      */   static final String INACTIVITY_TIMEOUT_KEY = "InactivityTimeout";
/*      */   static final String TIME_TO_LIVE_TIMEOUT_KEY = "TimeToLiveTimeout";
/*      */   static final String ABANDONED_CONNECTION_TIMEOUT_KEY = "AbandonedConnectionTimeout";
/*      */   static final String LOWER_THRESHOLD_LIMIT_KEY = "LowerThresholdLimit";
/*      */   static final String PROPERTY_CHECK_INTERVAL_KEY = "PropertyCheckInterval";
/*      */   static final String VALIDATE_CONNECTION_KEY = "ValidateConnection";
/*      */   static final String CLOSEST_CONNECTION_MATCH_KEY = "ClosestConnectionMatch";
/*      */   static final String CONNECTION_WAIT_TIMEOUT_KEY = "ConnectionWaitTimeout";
/*      */   static final int INSTANCE_GOOD = 1;
/*      */   static final int INSTANCE_UNKNOWN = 2;
/*      */   static final int INSTANCE_VIOLATING = 3;
/*      */   static final int INSTANCE_NO_DATA = 4;
/*      */   static final int INSTANCE_BLOCKED = 5;
/*      */   static final int RLB_NUMBER_OF_HITS_PER_INSTANCE = 1000;
/*  121 */   int dbInstancePercentTotal = 0;
/*  122 */   boolean useGoodGroup = false;
/*  123 */   LinkedList instancesToRetireList = null;
/*  124 */   OracleDatabaseInstance instanceToRetire = null;
/*  125 */   int retireConnectionsCount = 0;
/*  126 */   int countTotal = 0;
/*      */ 
/*  128 */   protected OracleConnectionCacheManager cacheManager = null;
/*  129 */   protected boolean disableConnectionRequest = false;
/*  130 */   protected OracleImplicitConnectionCacheThread timeoutThread = null;
/*      */ 
/*  137 */   protected OracleRuntimeLoadBalancingEventHandlerThread runtimeLoadBalancingThread = null;
/*      */ 
/*  140 */   protected OracleGravitateConnectionCacheThread gravitateCacheThread = null;
/*  141 */   protected int connectionsToRemove = 0;
/*      */ 
/*  145 */   private HashMap userMap = null;
/*  146 */   Vector checkedOutConnectionList = null;
/*      */ 
/*  149 */   LinkedList databaseInstancesList = null;
/*      */ 
/*  151 */   int cacheSize = 0;
/*      */   protected static final String EVENT_DELIMITER = " ";
/*  155 */   protected boolean isEntireServiceDownProcessed = false;
/*  156 */   protected int defaultUserPreFailureSize = 0;
/*  157 */   protected String dataSourceServiceName = null;
/*  158 */   protected OracleFailoverWorkerThread failoverWorkerThread = null;
/*  159 */   protected Random rand = null;
/*  160 */   protected int downEventCount = 0;
/*  161 */   protected int upEventCount = 0;
/*  162 */   protected int pendingCreationRequests = 0;
/*      */ 
/*  164 */   protected int connectionClosedCount = 0;
/*  165 */   protected int connectionCreatedCount = 0;
/*      */ 
/* 3318 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*      */ 
/*      */   OracleImplicitConnectionCache(OracleDataSource paramOracleDataSource, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  176 */     this.cacheEnabledDS = paramOracleDataSource;
/*      */ 
/*  178 */     initializeConnectionCache();
/*  179 */     setConnectionCacheProperties(paramProperties);
/*      */ 
/*  186 */     defaultUserPrePopulateCache(this.cacheInitialLimit);
/*      */   }
/*      */ 
/*      */   private void defaultUserPrePopulateCache(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  199 */     if (paramInt > 0)
/*      */     {
/*  201 */       String str1 = this.defaultUser;
/*  202 */       String str2 = this.defaultPassword;
/*      */ 
/*  204 */       validateUser(str1, str2);
/*      */ 
/*  206 */       OraclePooledConnection localOraclePooledConnection = null;
/*      */ 
/*  208 */       for (int i = 0; i < paramInt; i++)
/*      */       {
/*  210 */         if (getTotalCachedConnections() >= this.cacheMaxLimit)
/*      */           continue;
/*  212 */         localOraclePooledConnection = makeCacheConnection(str1, str2);
/*      */ 
/*  214 */         if (localOraclePooledConnection == null)
/*      */           continue;
/*  216 */         synchronized (this)
/*      */         {
/*  218 */           this.connectionCreatedCount += 1;
/*      */ 
/*  221 */           this.cacheSize -= 1;
/*      */         }
/*  223 */         storeCacheConnection(null, localOraclePooledConnection);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initializeConnectionCache()
/*      */     throws SQLException
/*      */   {
/*  239 */     this.userMap = new HashMap();
/*  240 */     this.checkedOutConnectionList = new Vector();
/*      */ 
/*  242 */     if (this.cacheManager == null) {
/*  243 */       this.cacheManager = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
/*      */     }
/*      */ 
/*  246 */     this.defaultUser = this.cacheEnabledDS.getUser();
/*  247 */     this.defaultPassword = this.cacheEnabledDS.getPassword();
/*  248 */     if (this.defaultPassword != null) {
/*  249 */       this.defaultPassword = this.defaultPassword.toUpperCase();
/*      */     }
/*  251 */     if (this.connectionPoolDS == null)
/*      */     {
/*  253 */       if ((this.cacheEnabledDS instanceof OracleXADataSource))
/*      */       {
/*  255 */         this.connectionPoolDS = new OracleXADataSource();
/*      */       }
/*      */       else
/*      */       {
/*  259 */         this.connectionPoolDS = new OracleConnectionPoolDataSource();
/*      */       }
/*      */ 
/*  263 */       this.cacheEnabledDS.copy(this.connectionPoolDS);
/*      */     }
/*      */ 
/*  266 */     if ((this.fastConnectionFailoverEnabled = this.cacheEnabledDS.getFastConnectionFailoverEnabled()))
/*      */     {
/*  269 */       this.rand = new Random(0L);
/*  270 */       this.cacheManager.failoverEnabledCacheCount += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateUser(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/*  285 */     if ((paramString1 == null) || (paramString2 == null))
/*  286 */       DatabaseError.throwSqlException(79);
/*      */   }
/*      */ 
/*      */   protected Connection getConnection(String paramString1, String paramString2, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  305 */     OraclePooledConnection localOraclePooledConnection = null;
/*  306 */     Connection localConnection = null;
/*      */     try
/*      */     {
/*  315 */       if (this.disableConnectionRequest)
/*      */       {
/*  317 */         DatabaseError.throwSqlException(142);
/*      */       }
/*      */ 
/*  321 */       validateUser(paramString1, paramString2);
/*      */ 
/*  324 */       if (!paramString1.startsWith("\"")) {
/*  325 */         paramString1 = paramString1.toLowerCase();
/*      */       }
/*      */ 
/*  340 */       if (getNumberOfCheckedOutConnections() < this.cacheMaxLimit) {
/*  341 */         localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
/*      */       }
/*      */ 
/*  344 */       if (localOraclePooledConnection == null)
/*      */       {
/*  351 */         processConnectionCacheCallback();
/*      */ 
/*  353 */         if (this.cacheSize > 0) {
/*  354 */           localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
/*      */         }
/*      */ 
/*  363 */         if ((localOraclePooledConnection == null) && (this.cacheConnectionWaitTimeout > 0))
/*      */         {
/*  365 */           long l1 = this.cacheConnectionWaitTimeout * 1000L;
/*  366 */           long l2 = System.currentTimeMillis();
/*  367 */           long l3 = 0L;
/*      */           do
/*      */           {
/*  370 */             processConnectionWaitTimeout(l1);
/*      */ 
/*  372 */             if (this.cacheSize > 0) {
/*  373 */               localOraclePooledConnection = getCacheConnection(paramString1, paramString2, paramProperties);
/*      */             }
/*  375 */             l3 = System.currentTimeMillis();
/*  376 */             l1 -= System.currentTimeMillis() - l2;
/*  377 */             l2 = l3;
/*      */           }
/*      */ 
/*  380 */           while ((localOraclePooledConnection == null) && (l1 > 0L));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  385 */       if ((localOraclePooledConnection != null) && (localOraclePooledConnection.physicalConn != null))
/*      */       {
/*  389 */         localConnection = localOraclePooledConnection.getConnection();
/*      */ 
/*  391 */         if (localConnection != null)
/*      */         {
/*  393 */           if ((this.cacheValidateConnection) && (testDatabaseConnection((OracleConnection)localConnection) != 0))
/*      */           {
/*  397 */             ((OracleConnection)localConnection).close(4096);
/*  398 */             DatabaseError.throwSqlException(143);
/*      */           }
/*      */ 
/*  402 */           if (this.cacheAbandonedConnectionTimeout > 0) {
/*  403 */             ((OracleConnection)localConnection).setAbandonedTimeoutEnabled(true);
/*      */           }
/*      */ 
/*  406 */           if (this.cacheTimeToLiveTimeout > 0) {
/*  407 */             ((OracleConnection)localConnection).setStartTime(System.currentTimeMillis());
/*      */           }
/*  409 */           synchronized (this)
/*      */           {
/*  413 */             this.cacheSize -= 1;
/*  414 */             this.checkedOutConnectionList.addElement(localOraclePooledConnection);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*  420 */       synchronized (this)
/*      */       {
/*  422 */         if (localOraclePooledConnection != null)
/*      */         {
/*  424 */           this.cacheSize -= 1;
/*  425 */           abortConnection(localOraclePooledConnection);
/*      */         }
/*      */       }
/*  428 */       throw localSQLException;
/*      */     }
/*      */ 
/*  435 */     return (Connection)localConnection;
/*      */   }
/*      */ 
/*      */   private OraclePooledConnection getCacheConnection(String paramString1, String paramString2, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  451 */     OraclePooledConnection localOraclePooledConnection = retrieveCacheConnection(paramString1, paramString2, paramProperties);
/*  452 */     int i = 0;
/*      */ 
/*  454 */     if (localOraclePooledConnection == null)
/*      */     {
/*  456 */       synchronized (this)
/*      */       {
/*  459 */         if (getTotalCachedConnections() + this.pendingCreationRequests < this.cacheMaxLimit)
/*      */         {
/*  462 */           this.pendingCreationRequests += 1;
/*  463 */           i = 1;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  468 */     if (i != 0)
/*      */     {
/*      */       try
/*      */       {
/*  472 */         localOraclePooledConnection = makeCacheConnection(paramString1, paramString2);
/*      */       }
/*      */       finally
/*      */       {
/*  480 */         synchronized (this)
/*      */         {
/*  482 */           if (localOraclePooledConnection != null)
/*  483 */             this.connectionCreatedCount += 1;
/*  484 */           this.pendingCreationRequests -= 1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  489 */       if ((paramProperties != null) && (!paramProperties.isEmpty())) {
/*  490 */         setUnMatchedAttributes(paramProperties, localOraclePooledConnection);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  501 */     return localOraclePooledConnection;
/*      */   }
/*      */ 
/*      */   protected int getTotalCachedConnections()
/*      */   {
/*  510 */     return this.cacheSize + getNumberOfCheckedOutConnections();
/*      */   }
/*      */ 
/*      */   protected int getNumberOfCheckedOutConnections()
/*      */   {
/*  519 */     return this.checkedOutConnectionList.size();
/*      */   }
/*      */ 
/*      */   private synchronized OraclePooledConnection retrieveCacheConnection(String paramString1, String paramString2, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  532 */     OraclePooledConnection localOraclePooledConnection = null;
/*  533 */     OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)this.userMap.get(paramString1 + paramString2.toUpperCase());
/*      */ 
/*  536 */     if (localOracleConnectionCacheEntry != null)
/*      */     {
/*  541 */       if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
/*      */       {
/*  543 */         if (localOracleConnectionCacheEntry.userConnList != null)
/*  544 */           localOraclePooledConnection = retrieveFromConnectionList(localOracleConnectionCacheEntry.userConnList);
/*      */       }
/*  546 */       else if (localOracleConnectionCacheEntry.attrConnMap != null)
/*      */       {
/*  548 */         String str = buildAttrKey(paramProperties);
/*  549 */         Vector localVector = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(str);
/*      */ 
/*  552 */         if (localVector != null)
/*      */         {
/*  559 */           localOraclePooledConnection = retrieveFromConnectionList(localVector);
/*      */         }
/*      */ 
/*  567 */         if ((localOraclePooledConnection == null) && (this.cacheClosestConnectionMatch)) {
/*  568 */           localOraclePooledConnection = retrieveClosestConnectionMatch(localOracleConnectionCacheEntry.attrConnMap, paramProperties);
/*      */         }
/*      */ 
/*  573 */         if ((localOraclePooledConnection == null) && (localOracleConnectionCacheEntry.userConnList != null)) {
/*  574 */           localOraclePooledConnection = retrieveFromConnectionList(localOracleConnectionCacheEntry.userConnList);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  579 */     if (localOraclePooledConnection != null)
/*      */     {
/*  581 */       if ((paramProperties != null) && (!paramProperties.isEmpty())) {
/*  582 */         setUnMatchedAttributes(paramProperties, localOraclePooledConnection);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  589 */     return localOraclePooledConnection;
/*      */   }
/*      */ 
/*      */   private OraclePooledConnection retrieveClosestConnectionMatch(HashMap paramHashMap, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  602 */     OraclePooledConnection localOraclePooledConnection1 = null;
/*  603 */     OraclePooledConnection localOraclePooledConnection2 = null;
/*      */ 
/*  605 */     int i = paramProperties.size();
/*  606 */     int j = 0;
/*  607 */     int k = 0;
/*  608 */     int m = 0;
/*  609 */     int n = 0;
/*  610 */     int i1 = 0;
/*      */ 
/*  612 */     if (this.cacheAttributeWeights != null) {
/*  613 */       j = getAttributesWeightCount(paramProperties, null);
/*      */     }
/*  615 */     if ((paramHashMap != null) && (!paramHashMap.isEmpty()))
/*      */     {
/*  619 */       Iterator localIterator = paramHashMap.entrySet().iterator();
/*      */ 
/*  621 */       while (localIterator.hasNext())
/*      */       {
/*  623 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/*      */ 
/*  625 */         Vector localVector = (Vector)localEntry.getValue();
/*  626 */         Object[] arrayOfObject = localVector.toArray();
/*  627 */         int i2 = localVector.size();
/*      */ 
/*  629 */         for (int i3 = 0; i3 < i2; i3++)
/*      */         {
/*  631 */           localOraclePooledConnection1 = (OraclePooledConnection)arrayOfObject[i3];
/*      */ 
/*  638 */           if ((localOraclePooledConnection1.cachedConnectionAttributes == null) || (localOraclePooledConnection1.cachedConnectionAttributes.isEmpty()) || (localOraclePooledConnection1.cachedConnectionAttributes.size() > i))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/*  652 */           if (j > 0)
/*      */           {
/*  654 */             m = getAttributesWeightCount(paramProperties, localOraclePooledConnection1.cachedConnectionAttributes);
/*      */ 
/*  658 */             if (m <= k)
/*      */               continue;
/*  660 */             localOraclePooledConnection2 = localOraclePooledConnection1;
/*  661 */             k = m;
/*      */           }
/*      */           else
/*      */           {
/*  666 */             i1 = getAttributesMatchCount(paramProperties, localOraclePooledConnection1.cachedConnectionAttributes);
/*      */ 
/*  669 */             if (i1 <= n)
/*      */               continue;
/*  671 */             localOraclePooledConnection2 = localOraclePooledConnection1;
/*  672 */             n = i1;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  680 */     return localOraclePooledConnection2;
/*      */   }
/*      */ 
/*      */   private int getAttributesMatchCount(Properties paramProperties1, Properties paramProperties2)
/*      */     throws SQLException
/*      */   {
/*  696 */     int i = 0;
/*  697 */     Map.Entry localEntry = null;
/*  698 */     Object localObject1 = null;
/*  699 */     Object localObject2 = null;
/*      */ 
/*  701 */     Iterator localIterator = paramProperties1.entrySet().iterator();
/*      */ 
/*  703 */     while (localIterator.hasNext())
/*      */     {
/*  705 */       localEntry = (Map.Entry)localIterator.next();
/*  706 */       localObject1 = localEntry.getKey();
/*  707 */       localObject2 = localEntry.getValue();
/*      */ 
/*  709 */       if ((!paramProperties2.containsKey(localObject1)) || (!localObject2.equals(paramProperties2.get(localObject1))))
/*      */         continue;
/*  711 */       i++;
/*      */     }
/*      */ 
/*  714 */     return i;
/*      */   }
/*      */ 
/*      */   private int getAttributesWeightCount(Properties paramProperties1, Properties paramProperties2)
/*      */     throws SQLException
/*      */   {
/*  744 */     Map.Entry localEntry = null;
/*  745 */     Object localObject1 = null;
/*  746 */     Object localObject2 = null;
/*  747 */     int i = 0;
/*      */ 
/*  749 */     Iterator localIterator = paramProperties1.entrySet().iterator();
/*      */ 
/*  751 */     while (localIterator.hasNext())
/*      */     {
/*  753 */       localEntry = (Map.Entry)localIterator.next();
/*  754 */       localObject1 = localEntry.getKey();
/*  755 */       localObject2 = localEntry.getValue();
/*      */ 
/*  758 */       if (paramProperties2 == null)
/*      */       {
/*  760 */         if (!this.cacheAttributeWeights.containsKey(localObject1))
/*      */           continue;
/*  762 */         i += Integer.parseInt((String)this.cacheAttributeWeights.get(localObject1)); continue;
/*      */       }
/*      */ 
/*  766 */       if ((!paramProperties2.containsKey(localObject1)) || (!localObject2.equals(paramProperties2.get(localObject1)))) {
/*      */         continue;
/*      */       }
/*  769 */       if (this.cacheAttributeWeights.containsKey(localObject1))
/*      */       {
/*  771 */         i += Integer.parseInt((String)this.cacheAttributeWeights.get(localObject1)); continue;
/*      */       }
/*      */ 
/*  778 */       i++;
/*      */     }
/*      */ 
/*  783 */     return i;
/*      */   }
/*      */ 
/*      */   private void setUnMatchedAttributes(Properties paramProperties, OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/*  798 */     if (paramOraclePooledConnection.unMatchedCachedConnAttr == null)
/*  799 */       paramOraclePooledConnection.unMatchedCachedConnAttr = new Properties();
/*      */     else {
/*  801 */       paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
/*      */     }
/*  803 */     if (!this.cacheClosestConnectionMatch)
/*      */     {
/*  805 */       paramOraclePooledConnection.unMatchedCachedConnAttr.putAll(paramProperties);
/*      */     }
/*      */     else
/*      */     {
/*  809 */       Properties localProperties = paramOraclePooledConnection.cachedConnectionAttributes;
/*  810 */       Map.Entry localEntry = null;
/*  811 */       Object localObject1 = null;
/*  812 */       Object localObject2 = null;
/*      */ 
/*  814 */       Iterator localIterator = paramProperties.entrySet().iterator();
/*      */ 
/*  816 */       while (localIterator.hasNext())
/*      */       {
/*  818 */         localEntry = (Map.Entry)localIterator.next();
/*  819 */         localObject1 = localEntry.getKey();
/*  820 */         localObject2 = localEntry.getValue();
/*      */ 
/*  822 */         if ((localProperties.containsKey(localObject1)) || (localObject2.equals(localProperties.get(localObject1))))
/*      */           continue;
/*  824 */         paramOraclePooledConnection.unMatchedCachedConnAttr.put(localObject1, localObject2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private OraclePooledConnection retrieveFromConnectionList(Vector paramVector)
/*      */     throws SQLException
/*      */   {
/*  842 */     if (paramVector.isEmpty()) {
/*  843 */       return null;
/*      */     }
/*  845 */     Object localObject1 = null;
/*      */ 
/*  847 */     if (this.fastConnectionFailoverEnabled)
/*      */     {
/*  850 */       if ((this.useGoodGroup) && (this.databaseInstancesList != null) && (this.databaseInstancesList.size() > 0))
/*      */       {
/*  853 */         label234: synchronized (this.databaseInstancesList)
/*      */         {
/*  855 */           i = this.databaseInstancesList.size();
/*  856 */           localObject2 = null;
/*  857 */           localObject3 = 0;
/*      */ 
/*  859 */           boolean[] arrayOfBoolean = new boolean[i];
/*  860 */           int j = this.dbInstancePercentTotal;
/*      */ 
/*  862 */           for (int k = 0; k < i; k++)
/*      */           {
/*  864 */             Object localObject4 = 0;
/*      */ 
/*  867 */             if (j <= 1)
/*  868 */               localObject3 = 0;
/*      */             else {
/*  870 */               localObject3 = this.rand.nextInt(j - 1);
/*      */             }
/*      */ 
/*  873 */             for (int m = 0; m < i; m++)
/*      */             {
/*  875 */               localObject2 = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
/*      */ 
/*  877 */               if ((arrayOfBoolean[m] != 0) || (((OracleDatabaseInstance)localObject2).flag > 3))
/*      */                 continue;
/*  879 */               localObject4 += ((OracleDatabaseInstance)localObject2).percent;
/*      */ 
/*  884 */               if (localObject3 > localObject4) {
/*      */                 continue;
/*      */               }
/*  887 */               if (k == 0) localObject2.attemptedConnRequestCount += 1;
/*      */ 
/*  889 */               if ((localObject1 = selectConnectionFromList(paramVector, (OracleDatabaseInstance)localObject2)) != null)
/*      */               {
/*      */                 break label234;
/*      */               }
/*      */ 
/*  895 */               j -= ((OracleDatabaseInstance)localObject2).percent;
/*  896 */               arrayOfBoolean[m] = true;
/*  897 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  913 */       ??? = paramVector.size();
/*  914 */       int i = this.rand.nextInt(???);
/*  915 */       Object localObject2 = null;
/*      */ 
/*  917 */       for (Object localObject3 = 0; localObject3 < ???; localObject3++)
/*      */       {
/*  919 */         localObject2 = (OraclePooledConnection)paramVector.get((i++ + ???) % ???);
/*      */ 
/*  922 */         if (((OraclePooledConnection)localObject2).connectionMarkedDown)
/*      */           continue;
/*  924 */         localObject1 = localObject2;
/*  925 */         paramVector.remove(localObject1);
/*  926 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  933 */     localObject1 = (OraclePooledConnection)paramVector.remove(0);
/*      */ 
/*  936 */     return (OraclePooledConnection)(OraclePooledConnection)localObject1;
/*      */   }
/*      */ 
/*      */   private OraclePooledConnection selectConnectionFromList(Vector paramVector, OracleDatabaseInstance paramOracleDatabaseInstance)
/*      */   {
/*  947 */     Object localObject = null;
/*  948 */     OraclePooledConnection localOraclePooledConnection = null;
/*      */ 
/*  951 */     int i = paramVector.size();
/*  952 */     for (int j = 0; j < i; j++)
/*      */     {
/*  954 */       localOraclePooledConnection = (OraclePooledConnection)paramVector.get(j);
/*      */ 
/*  956 */       if ((localOraclePooledConnection.connectionMarkedDown) || (localOraclePooledConnection.dataSourceDbUniqNameKey != paramOracleDatabaseInstance.databaseUniqName) || (localOraclePooledConnection.dataSourceInstanceNameKey != paramOracleDatabaseInstance.instanceName))
/*      */       {
/*      */         continue;
/*      */       }
/*  960 */       localObject = localOraclePooledConnection;
/*  961 */       paramVector.remove(localObject);
/*  962 */       break;
/*      */     }
/*      */ 
/*  970 */     return localObject;
/*      */   }
/*      */ 
/*      */   private void removeCacheConnection(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/*  982 */     boolean bool = false;
/*      */ 
/*  984 */     OracleConnectionCacheEntry localOracleConnectionCacheEntry = paramOraclePooledConnection.removeFromImplictCache(this.userMap);
/*      */ 
/*  987 */     if (localOracleConnectionCacheEntry != null)
/*      */     {
/*  989 */       Properties localProperties = paramOraclePooledConnection.cachedConnectionAttributes;
/*      */ 
/*  991 */       if ((localProperties == null) || ((localProperties != null) && (localProperties.isEmpty())))
/*      */       {
/*  993 */         if (localOracleConnectionCacheEntry.userConnList != null)
/*  994 */           bool = localOracleConnectionCacheEntry.userConnList.removeElement(paramOraclePooledConnection);
/*      */       }
/*  996 */       else if (localOracleConnectionCacheEntry.attrConnMap != null)
/*      */       {
/*  998 */         String str = buildAttrKey(localProperties);
/*      */ 
/* 1000 */         Vector localVector = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(str);
/*      */ 
/* 1003 */         if (localVector != null)
/*      */         {
/* 1007 */           if (paramOraclePooledConnection.unMatchedCachedConnAttr != null)
/*      */           {
/* 1009 */             paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
/* 1010 */             paramOraclePooledConnection.unMatchedCachedConnAttr = null;
/*      */           }
/*      */ 
/* 1013 */           if (paramOraclePooledConnection.cachedConnectionAttributes != null)
/*      */           {
/* 1015 */             paramOraclePooledConnection.cachedConnectionAttributes.clear();
/* 1016 */             paramOraclePooledConnection.cachedConnectionAttributes = null;
/*      */           }
/*      */ 
/* 1019 */           localProperties = null;
/* 1020 */           bool = localVector.removeElement(paramOraclePooledConnection);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1026 */     if (bool)
/*      */     {
/* 1028 */       this.cacheSize -= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doForEveryCachedConnection(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1054 */     int i = 0;
/*      */ 
/* 1056 */     synchronized (this)
/*      */     {
/* 1058 */       if ((this.userMap != null) && (!this.userMap.isEmpty()))
/*      */       {
/* 1060 */         Iterator localIterator = this.userMap.entrySet().iterator();
/*      */ 
/* 1062 */         while (localIterator.hasNext())
/*      */         {
/* 1064 */           Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 1065 */           OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)localEntry.getValue();
/*      */           Object localObject2;
/*      */           OraclePooledConnection localOraclePooledConnection;
/* 1068 */           if ((localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
/*      */           {
/* 1070 */             localObject1 = localOracleConnectionCacheEntry.userConnList;
/* 1071 */             localObject2 = ((Vector)localObject1).toArray();
/*      */ 
/* 1073 */             for (int j = 0; j < localObject2.length; j++)
/*      */             {
/* 1075 */               localOraclePooledConnection = (OraclePooledConnection)localObject2[j];
/*      */ 
/* 1077 */               if ((localOraclePooledConnection != null) && (performPooledConnectionTask(localOraclePooledConnection, paramInt))) {
/* 1078 */                 i++;
/*      */               }
/*      */             }
/*      */           }
/* 1082 */           if ((localOracleConnectionCacheEntry.attrConnMap == null) || (localOracleConnectionCacheEntry.attrConnMap.isEmpty()))
/*      */             continue;
/* 1084 */           Object localObject1 = localOracleConnectionCacheEntry.attrConnMap.entrySet().iterator();
/*      */ 
/* 1086 */           while (((Iterator)localObject1).hasNext())
/*      */           {
/* 1088 */             localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*      */ 
/* 1090 */             Vector localVector = (Vector)((Map.Entry)localObject2).getValue();
/* 1091 */             Object[] arrayOfObject = localVector.toArray();
/*      */ 
/* 1093 */             for (int k = 0; k < arrayOfObject.length; k++)
/*      */             {
/* 1095 */               localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[k];
/*      */ 
/* 1097 */               if ((localOraclePooledConnection != null) && (performPooledConnectionTask(localOraclePooledConnection, paramInt))) {
/* 1098 */                 i++;
/*      */               }
/*      */             }
/*      */           }
/* 1102 */           if (paramInt == 1) {
/* 1103 */             localOracleConnectionCacheEntry.attrConnMap.clear();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1108 */         if (paramInt == 1)
/*      */         {
/* 1110 */           this.userMap.clear();
/*      */ 
/* 1112 */           this.cacheSize = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1122 */     if (i > 0)
/*      */     {
/* 1125 */       defaultUserPrePopulateCache(i);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean performPooledConnectionTask(OraclePooledConnection paramOraclePooledConnection, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1146 */     int i = 0;
/*      */ 
/* 1148 */     switch (paramInt)
/*      */     {
/*      */     case 2:
/* 1154 */       if (!paramOraclePooledConnection.connectionMarkedDown)
/*      */       {
/*      */         break;
/*      */       }
/* 1158 */       paramOraclePooledConnection.needToAbort = true;
/* 1159 */       closeAndRemovePooledConnection(paramOraclePooledConnection); break;
/*      */     case 8:
/* 1168 */       if (this.connectionsToRemove <= 0)
/*      */         break;
/* 1170 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/*      */ 
/* 1172 */       this.connectionsToRemove -= 1; break;
/*      */     case 24:
/* 1181 */       if (this.retireConnectionsCount <= 0)
/*      */         break;
/* 1183 */       if ((this.instanceToRetire.databaseUniqName != paramOraclePooledConnection.dataSourceDbUniqNameKey) || (this.instanceToRetire.instanceName != paramOraclePooledConnection.dataSourceInstanceNameKey)) {
/*      */         break;
/*      */       }
/* 1186 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/* 1187 */       this.retireConnectionsCount -= 1;
/*      */ 
/* 1189 */       if (getTotalCachedConnections() >= this.cacheMinLimit) break;
/* 1190 */       i = 1; break;
/*      */     case 4096:
/* 1199 */       Connection localConnection = paramOraclePooledConnection.getLogicalHandle();
/*      */ 
/* 1201 */       if ((localConnection == null) && ((localConnection = paramOraclePooledConnection.getPhysicalHandle()) == null)) {
/*      */         break;
/*      */       }
/* 1204 */       if (testDatabaseConnection((OracleConnection)localConnection) == 0) {
/*      */         break;
/*      */       }
/* 1207 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/*      */ 
/* 1209 */       i = 1; break;
/*      */     case 8192:
/* 1218 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/*      */ 
/* 1220 */       i = 1;
/*      */ 
/* 1222 */       break;
/*      */     case 1:
/* 1227 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/*      */ 
/* 1229 */       break;
/*      */     case 4:
/* 1234 */       processInactivityTimeout(paramOraclePooledConnection);
/*      */ 
/* 1236 */       break;
/*      */     case 16:
/* 1241 */       setStatementCaching(paramOraclePooledConnection, this.cacheMaxStatementsLimit, false);
/*      */ 
/* 1243 */       break;
/*      */     case 18:
/* 1248 */       setStatementCaching(paramOraclePooledConnection, this.cacheMaxStatementsLimit, true);
/*      */ 
/* 1250 */       break;
/*      */     }
/*      */ 
/* 1257 */     return i;
/*      */   }
/*      */ 
/*      */   protected synchronized void doForEveryCheckedOutConnection(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1274 */     int i = this.checkedOutConnectionList.size();
/*      */     int j;
/* 1277 */     if (paramInt == 1)
/*      */     {
/* 1279 */       for (j = 0; j < i; j++)
/*      */       {
/* 1281 */         closeCheckedOutConnection((OraclePooledConnection)this.checkedOutConnectionList.get(j), false);
/*      */       }
/*      */ 
/* 1285 */       this.checkedOutConnectionList.removeAllElements();
/*      */     }
/* 1287 */     else if (paramInt == 24)
/*      */     {
/* 1289 */       for (j = 0; (j < i) && (this.retireConnectionsCount > 0); j++)
/*      */       {
/* 1291 */         OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)this.checkedOutConnectionList.get(j);
/* 1292 */         if ((this.instanceToRetire.databaseUniqName != localOraclePooledConnection.dataSourceDbUniqNameKey) || (this.instanceToRetire.instanceName != localOraclePooledConnection.dataSourceInstanceNameKey)) {
/*      */           continue;
/*      */         }
/* 1295 */         localOraclePooledConnection.closeOption = 4096;
/*      */ 
/* 1298 */         this.retireConnectionsCount -= 2;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void closeCheckedOutConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1321 */     if (paramOraclePooledConnection != null)
/*      */     {
/* 1323 */       OracleConnection localOracleConnection = (OracleConnection)paramOraclePooledConnection.getLogicalHandle();
/*      */       try
/*      */       {
/* 1328 */         if ((!localOracleConnection.getAutoCommit()) && (!paramOraclePooledConnection.needToAbort)) {
/* 1329 */           localOracleConnection.rollback();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException localSQLException1)
/*      */       {
/*      */       }
/*      */ 
/* 1337 */       if (paramBoolean)
/*      */       {
/*      */         try
/*      */         {
/* 1342 */           localOracleConnection.close();
/*      */         }
/*      */         catch (SQLException localSQLException2)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1352 */         actualPooledConnectionClose(paramOraclePooledConnection);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void storeCacheConnection(Properties paramProperties, OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 1372 */     boolean bool = false;
/*      */ 
/* 1374 */     if ((paramOraclePooledConnection == null) || (paramOraclePooledConnection.physicalConn == null))
/*      */     {
/* 1376 */       return;
/*      */     }
/*      */ 
/* 1379 */     if (this.cacheInactivityTimeout > 0)
/*      */     {
/* 1381 */       paramOraclePooledConnection.setLastAccessedTime(System.currentTimeMillis());
/*      */     }
/*      */ 
/* 1384 */     if (paramOraclePooledConnection.unMatchedCachedConnAttr != null)
/*      */     {
/* 1386 */       paramOraclePooledConnection.unMatchedCachedConnAttr.clear();
/* 1387 */       paramOraclePooledConnection.unMatchedCachedConnAttr = null;
/*      */     }
/*      */ 
/* 1390 */     OracleConnectionCacheEntry localOracleConnectionCacheEntry = paramOraclePooledConnection.removeFromImplictCache(this.userMap);
/*      */     Object localObject1;
/*      */     Object localObject2;
/* 1393 */     if (localOracleConnectionCacheEntry != null)
/*      */     {
/* 1395 */       if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
/*      */       {
/* 1397 */         if (localOracleConnectionCacheEntry.userConnList == null) {
/* 1398 */           localOracleConnectionCacheEntry.userConnList = new Vector();
/*      */         }
/* 1400 */         bool = localOracleConnectionCacheEntry.userConnList.add(paramOraclePooledConnection);
/*      */       }
/*      */       else
/*      */       {
/* 1406 */         paramOraclePooledConnection.cachedConnectionAttributes = paramProperties;
/*      */ 
/* 1408 */         if (localOracleConnectionCacheEntry.attrConnMap == null) {
/* 1409 */           localOracleConnectionCacheEntry.attrConnMap = new HashMap();
/*      */         }
/* 1411 */         localObject1 = buildAttrKey(paramProperties);
/* 1412 */         localObject2 = (Vector)localOracleConnectionCacheEntry.attrConnMap.get(localObject1);
/*      */ 
/* 1415 */         if (localObject2 != null)
/*      */         {
/* 1417 */           bool = ((Vector)localObject2).add(paramOraclePooledConnection);
/*      */         }
/*      */         else
/*      */         {
/* 1421 */           localObject2 = new Vector();
/*      */ 
/* 1423 */           bool = ((Vector)localObject2).add(paramOraclePooledConnection);
/* 1424 */           localOracleConnectionCacheEntry.attrConnMap.put(localObject1, localObject2);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1430 */       localOracleConnectionCacheEntry = new OracleConnectionCacheEntry();
/*      */ 
/* 1432 */       paramOraclePooledConnection.addToImplicitCache(this.userMap, localOracleConnectionCacheEntry);
/*      */ 
/* 1434 */       if ((paramProperties == null) || ((paramProperties != null) && (paramProperties.isEmpty())))
/*      */       {
/* 1436 */         localObject1 = new Vector();
/*      */ 
/* 1438 */         bool = ((Vector)localObject1).add(paramOraclePooledConnection);
/*      */ 
/* 1440 */         localOracleConnectionCacheEntry.userConnList = ((Vector)localObject1);
/*      */       }
/*      */       else
/*      */       {
/* 1444 */         localObject1 = buildAttrKey(paramProperties);
/*      */ 
/* 1447 */         paramOraclePooledConnection.cachedConnectionAttributes = paramProperties;
/*      */ 
/* 1449 */         localObject2 = new HashMap();
/* 1450 */         Vector localVector = new Vector();
/*      */ 
/* 1452 */         bool = localVector.add(paramOraclePooledConnection);
/* 1453 */         ((HashMap)localObject2).put(localObject1, localVector);
/*      */ 
/* 1455 */         localOracleConnectionCacheEntry.attrConnMap = ((HashMap)localObject2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1460 */     if (bool) {
/* 1461 */       this.cacheSize += 1;
/*      */     }
/*      */ 
/* 1472 */     if (this.cacheConnectionWaitTimeout > 0)
/*      */     {
/* 1474 */       notifyAll();
/*      */     }
/*      */   }
/*      */ 
/*      */   private String buildAttrKey(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/* 1492 */     int i = paramProperties.keySet().size();
/* 1493 */     Object[] arrayOfObject = paramProperties.keySet().toArray();
/* 1494 */     int j = 1;
/* 1495 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/* 1498 */     while (j != 0)
/*      */     {
/* 1500 */       j = 0;
/*      */ 
/* 1502 */       for (k = 0; k < i - 1; k++)
/*      */       {
/* 1504 */         if (((String)arrayOfObject[k]).compareTo((String)arrayOfObject[(k + 1)]) <= 0)
/*      */           continue;
/* 1506 */         j = 1;
/*      */ 
/* 1508 */         Object localObject = arrayOfObject[k];
/*      */ 
/* 1510 */         arrayOfObject[k] = arrayOfObject[(k + 1)];
/* 1511 */         arrayOfObject[(k + 1)] = localObject;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1517 */     for (int k = 0; k < i; k++) {
/* 1518 */       localStringBuffer.append(arrayOfObject[k] + "0xffff" + paramProperties.get(arrayOfObject[k]));
/*      */     }
/*      */ 
/* 1523 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   protected OraclePooledConnection makeCacheConnection(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 1537 */     OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)this.connectionPoolDS.getPooledConnection(paramString1, paramString2);
/*      */ 
/* 1541 */     if (localOraclePooledConnection != null)
/*      */     {
/* 1549 */       if (this.cacheMaxStatementsLimit > 0) {
/* 1550 */         setStatementCaching(localOraclePooledConnection, this.cacheMaxStatementsLimit, true);
/*      */       }
/*      */ 
/* 1553 */       localOraclePooledConnection.registerImplicitCacheConnectionEventListener(new OracleConnectionCacheEventListener(this));
/*      */ 
/* 1556 */       localOraclePooledConnection.cachedConnectionAttributes = new Properties();
/*      */ 
/* 1559 */       if (this.fastConnectionFailoverEnabled)
/*      */       {
/* 1561 */         initFailoverParameters(localOraclePooledConnection);
/*      */       }
/*      */ 
/* 1565 */       synchronized (this)
/*      */       {
/* 1567 */         this.cacheSize += 1;
/*      */ 
/* 1572 */         if ((this.fastConnectionFailoverEnabled) && (this.runtimeLoadBalancingThread == null))
/*      */         {
/* 1575 */           this.runtimeLoadBalancingThread = new OracleRuntimeLoadBalancingEventHandlerThread(this.dataSourceServiceName);
/*      */ 
/* 1577 */           this.cacheManager.checkAndStartThread(this.runtimeLoadBalancingThread);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1583 */     return localOraclePooledConnection;
/*      */   }
/*      */ 
/*      */   private void setStatementCaching(OraclePooledConnection paramOraclePooledConnection, int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1591 */     if (paramInt > 0) {
/* 1592 */       paramOraclePooledConnection.setStatementCacheSize(paramInt);
/*      */     }
/* 1594 */     paramOraclePooledConnection.setImplicitCachingEnabled(paramBoolean);
/* 1595 */     paramOraclePooledConnection.setExplicitCachingEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */   protected synchronized void reusePooledConnection(PooledConnection paramPooledConnection)
/*      */     throws SQLException
/*      */   {
/* 1616 */     OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)paramPooledConnection;
/* 1617 */     if ((localOraclePooledConnection != null) && (localOraclePooledConnection.physicalConn != null))
/*      */     {
/* 1619 */       storeCacheConnection(localOraclePooledConnection.cachedConnectionAttributes, localOraclePooledConnection);
/*      */ 
/* 1622 */       this.checkedOutConnectionList.removeElement(localOraclePooledConnection);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void closePooledConnection(PooledConnection paramPooledConnection)
/*      */     throws SQLException
/*      */   {
/* 1646 */     if (paramPooledConnection != null)
/*      */     {
/* 1648 */       actualPooledConnectionClose((OraclePooledConnection)paramPooledConnection);
/*      */ 
/* 1654 */       if (((OraclePooledConnection)paramPooledConnection).closeOption == 4096) {
/* 1655 */         this.checkedOutConnectionList.removeElement(paramPooledConnection);
/*      */       }
/* 1657 */       paramPooledConnection = null;
/*      */ 
/* 1659 */       if (getTotalCachedConnections() < this.cacheMinLimit)
/* 1660 */         defaultUserPrePopulateCache(1);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void refreshCacheConnections(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1678 */     doForEveryCachedConnection(paramInt);
/*      */   }
/*      */ 
/*      */   protected synchronized void reinitializeCacheConnections(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/* 1694 */     this.defaultUser = this.cacheEnabledDS.getUser();
/* 1695 */     this.defaultPassword = this.cacheEnabledDS.getPassword();
/* 1696 */     if (this.defaultPassword != null)
/* 1697 */       this.defaultPassword = this.defaultPassword.toUpperCase();
/* 1698 */     this.fastConnectionFailoverEnabled = this.cacheEnabledDS.getFastConnectionFailoverEnabled();
/*      */ 
/* 1706 */     cleanupTimeoutThread();
/*      */ 
/* 1710 */     doForEveryCheckedOutConnection(1);
/*      */ 
/* 1713 */     int i = this.cacheInitialLimit;
/* 1714 */     int j = this.cacheMaxLimit;
/* 1715 */     int k = this.cacheMaxStatementsLimit;
/*      */ 
/* 1718 */     setConnectionCacheProperties(paramProperties);
/*      */ 
/* 1721 */     if (this.cacheInitialLimit > i)
/*      */     {
/* 1723 */       int m = this.cacheInitialLimit - i;
/*      */ 
/* 1725 */       defaultUserPrePopulateCache(m);
/*      */     }
/*      */ 
/* 1732 */     if (j != 2147483647)
/*      */     {
/* 1734 */       if ((this.cacheMaxLimit < j) && (this.cacheSize > this.cacheMaxLimit))
/*      */       {
/* 1736 */         this.connectionsToRemove = (this.cacheSize - this.cacheMaxLimit);
/*      */ 
/* 1738 */         doForEveryCachedConnection(8);
/*      */ 
/* 1740 */         this.connectionsToRemove = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1746 */     if (this.cacheMaxStatementsLimit != k)
/*      */     {
/* 1748 */       if (this.cacheMaxStatementsLimit == 0)
/* 1749 */         doForEveryCachedConnection(16);
/*      */       else
/* 1751 */         doForEveryCachedConnection(18);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected synchronized void setConnectionCacheProperties(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 1778 */       if (paramProperties != null)
/*      */       {
/* 1780 */         String str = null;
/*      */ 
/* 1783 */         if ((str = paramProperties.getProperty("MinLimit")) != null)
/*      */         {
/* 1785 */           if ((this.cacheMinLimit = Integer.parseInt(str)) < 0) {
/* 1786 */             this.cacheMinLimit = 0;
/*      */           }
/*      */         }
/*      */ 
/* 1790 */         if ((str = paramProperties.getProperty("MaxLimit")) != null)
/*      */         {
/* 1792 */           if ((this.cacheMaxLimit = Integer.parseInt(str)) < 0) {
/* 1793 */             this.cacheMaxLimit = 2147483647;
/*      */           }
/*      */         }
/*      */ 
/* 1797 */         if (this.cacheMaxLimit < this.cacheMinLimit) {
/* 1798 */           this.cacheMinLimit = this.cacheMaxLimit;
/*      */         }
/*      */ 
/* 1801 */         if ((str = paramProperties.getProperty("InitialLimit")) != null)
/*      */         {
/* 1803 */           if ((this.cacheInitialLimit = Integer.parseInt(str)) < 0) {
/* 1804 */             this.cacheInitialLimit = 0;
/*      */           }
/*      */         }
/* 1807 */         if (this.cacheInitialLimit > this.cacheMaxLimit) {
/* 1808 */           this.cacheInitialLimit = this.cacheMaxLimit;
/*      */         }
/*      */ 
/* 1811 */         if ((str = paramProperties.getProperty("MaxStatementsLimit")) != null)
/*      */         {
/* 1813 */           if ((this.cacheMaxStatementsLimit = Integer.parseInt(str)) < 0) {
/* 1814 */             this.cacheMaxStatementsLimit = 0;
/*      */           }
/*      */         }
/*      */ 
/* 1818 */         Properties localProperties = (Properties)paramProperties.get("AttributeWeights");
/*      */ 
/* 1821 */         if (localProperties != null)
/*      */         {
/* 1823 */           Map.Entry localEntry = null;
/* 1824 */           int i = 0;
/* 1825 */           Object localObject = null;
/*      */ 
/* 1827 */           Iterator localIterator = localProperties.entrySet().iterator();
/*      */ 
/* 1829 */           while (localIterator.hasNext())
/*      */           {
/* 1831 */             localEntry = (Map.Entry)localIterator.next();
/* 1832 */             localObject = localEntry.getKey();
/*      */ 
/* 1834 */             if (((str = (String)localProperties.get(localObject)) == null) || 
/* 1836 */               ((i = Integer.parseInt(str)) >= 0)) continue;
/* 1837 */             localProperties.put(localObject, "0");
/*      */           }
/*      */ 
/* 1841 */           if (this.cacheAttributeWeights == null) {
/* 1842 */             this.cacheAttributeWeights = new Properties();
/*      */           }
/* 1844 */           this.cacheAttributeWeights.putAll(localProperties);
/*      */         }
/*      */ 
/* 1848 */         if ((str = paramProperties.getProperty("InactivityTimeout")) != null)
/*      */         {
/* 1850 */           if ((this.cacheInactivityTimeout = Integer.parseInt(str)) < 0) {
/* 1851 */             this.cacheInactivityTimeout = 0;
/*      */           }
/*      */         }
/*      */ 
/* 1855 */         if ((str = paramProperties.getProperty("TimeToLiveTimeout")) != null)
/*      */         {
/* 1857 */           if ((this.cacheTimeToLiveTimeout = Integer.parseInt(str)) < 0) {
/* 1858 */             this.cacheTimeToLiveTimeout = 0;
/*      */           }
/*      */         }
/*      */ 
/* 1862 */         if ((str = paramProperties.getProperty("AbandonedConnectionTimeout")) != null)
/*      */         {
/* 1864 */           if ((this.cacheAbandonedConnectionTimeout = Integer.parseInt(str)) < 0) {
/* 1865 */             this.cacheAbandonedConnectionTimeout = 0;
/*      */           }
/*      */         }
/*      */ 
/* 1869 */         if ((str = paramProperties.getProperty("LowerThresholdLimit")) != null)
/*      */         {
/* 1871 */           this.cacheLowerThresholdLimit = Integer.parseInt(str);
/*      */ 
/* 1873 */           if ((this.cacheLowerThresholdLimit < 0) || (this.cacheLowerThresholdLimit > 100))
/*      */           {
/* 1875 */             this.cacheLowerThresholdLimit = 20;
/*      */           }
/*      */         }
/*      */ 
/* 1879 */         if ((str = paramProperties.getProperty("PropertyCheckInterval")) != null)
/*      */         {
/* 1881 */           if ((this.cachePropertyCheckInterval = Integer.parseInt(str)) < 0) {
/* 1882 */             this.cachePropertyCheckInterval = 900;
/*      */           }
/*      */         }
/*      */ 
/* 1886 */         if ((str = paramProperties.getProperty("ValidateConnection")) != null) {
/* 1887 */           this.cacheValidateConnection = Boolean.valueOf(str).booleanValue();
/*      */         }
/*      */ 
/* 1891 */         if ((str = paramProperties.getProperty("ClosestConnectionMatch")) != null)
/*      */         {
/* 1893 */           this.cacheClosestConnectionMatch = Boolean.valueOf(str).booleanValue();
/*      */         }
/*      */ 
/* 1897 */         if ((str = paramProperties.getProperty("ConnectionWaitTimeout")) != null)
/*      */         {
/* 1899 */           if ((this.cacheConnectionWaitTimeout = Integer.parseInt(str)) < 0) {
/* 1900 */             this.cacheConnectionWaitTimeout = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1906 */         this.cacheMinLimit = 0;
/* 1907 */         this.cacheMaxLimit = 2147483647;
/* 1908 */         this.cacheInitialLimit = 0;
/* 1909 */         this.cacheMaxStatementsLimit = 0;
/* 1910 */         this.cacheAttributeWeights = null;
/* 1911 */         this.cacheInactivityTimeout = 0;
/* 1912 */         this.cacheTimeToLiveTimeout = 0;
/* 1913 */         this.cacheAbandonedConnectionTimeout = 0;
/* 1914 */         this.cacheLowerThresholdLimit = 20;
/* 1915 */         this.cachePropertyCheckInterval = 900;
/* 1916 */         this.cacheClosestConnectionMatch = false;
/* 1917 */         this.cacheValidateConnection = false;
/* 1918 */         this.cacheConnectionWaitTimeout = 0;
/*      */       }
/*      */ 
/* 1922 */       if ((this.cacheInactivityTimeout > 0) || (this.cacheTimeToLiveTimeout > 0) || (this.cacheAbandonedConnectionTimeout > 0))
/*      */       {
/* 1925 */         if (this.timeoutThread == null) {
/* 1926 */           this.timeoutThread = new OracleImplicitConnectionCacheThread(this);
/*      */         }
/* 1928 */         this.cacheManager.checkAndStartThread(this.timeoutThread);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException)
/*      */     {
/* 1933 */       DatabaseError.throwSqlException(139);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Properties getConnectionCacheProperties()
/*      */     throws SQLException
/*      */   {
/* 1946 */     Properties localProperties = new Properties();
/*      */ 
/* 1948 */     localProperties.setProperty("MinLimit", String.valueOf(this.cacheMinLimit));
/* 1949 */     localProperties.setProperty("MaxLimit", String.valueOf(this.cacheMaxLimit));
/* 1950 */     localProperties.setProperty("InitialLimit", String.valueOf(this.cacheInitialLimit));
/* 1951 */     localProperties.setProperty("MaxStatementsLimit", String.valueOf(this.cacheMaxStatementsLimit));
/*      */ 
/* 1954 */     if (this.cacheAttributeWeights != null)
/* 1955 */       localProperties.put("AttributeWeights", this.cacheAttributeWeights);
/*      */     else {
/* 1957 */       localProperties.setProperty("AttributeWeights", "NULL");
/*      */     }
/* 1959 */     localProperties.setProperty("InactivityTimeout", String.valueOf(this.cacheInactivityTimeout));
/*      */ 
/* 1961 */     localProperties.setProperty("TimeToLiveTimeout", String.valueOf(this.cacheTimeToLiveTimeout));
/*      */ 
/* 1963 */     localProperties.setProperty("AbandonedConnectionTimeout", String.valueOf(this.cacheAbandonedConnectionTimeout));
/*      */ 
/* 1965 */     localProperties.setProperty("LowerThresholdLimit", String.valueOf(this.cacheLowerThresholdLimit));
/*      */ 
/* 1967 */     localProperties.setProperty("PropertyCheckInterval", String.valueOf(this.cachePropertyCheckInterval));
/*      */ 
/* 1969 */     localProperties.setProperty("ConnectionWaitTimeout", String.valueOf(this.cacheConnectionWaitTimeout));
/*      */ 
/* 1971 */     localProperties.setProperty("ValidateConnection", String.valueOf(this.cacheValidateConnection));
/*      */ 
/* 1973 */     localProperties.setProperty("ClosestConnectionMatch", String.valueOf(this.cacheClosestConnectionMatch));
/*      */ 
/* 1976 */     return localProperties;
/*      */   }
/*      */ 
/*      */   protected int testDatabaseConnection(OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/* 1989 */     return paramOracleConnection.pingDatabase(0);
/*      */   }
/*      */ 
/*      */   protected synchronized void closeConnectionCache()
/*      */     throws SQLException
/*      */   {
/* 2006 */     cleanupTimeoutThread();
/*      */ 
/* 2010 */     purgeCacheConnections(true);
/*      */ 
/* 2017 */     this.connectionPoolDS = null;
/* 2018 */     this.cacheEnabledDS = null;
/* 2019 */     this.checkedOutConnectionList = null;
/* 2020 */     this.userMap = null;
/* 2021 */     this.cacheManager = null;
/*      */   }
/*      */ 
/*      */   protected synchronized void disableConnectionCache()
/*      */     throws SQLException
/*      */   {
/* 2033 */     this.disableConnectionRequest = true;
/*      */   }
/*      */ 
/*      */   protected synchronized void enableConnectionCache()
/*      */     throws SQLException
/*      */   {
/* 2047 */     this.disableConnectionRequest = false;
/*      */   }
/*      */ 
/*      */   protected void initFailoverParameters(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 2062 */     String str1 = null;
/* 2063 */     String str2 = null;
/* 2064 */     String str3 = null;
/*      */ 
/* 2066 */     Properties localProperties = ((OracleConnection)paramOraclePooledConnection.getPhysicalHandle()).getServerSessionInfo();
/*      */ 
/* 2068 */     str3 = localProperties.getProperty("INSTANCE_NAME");
/* 2069 */     if (str3 != null) {
/* 2070 */       str1 = paramOraclePooledConnection.dataSourceInstanceNameKey = str3.trim().toLowerCase().intern();
/*      */     }
/*      */ 
/* 2075 */     str3 = localProperties.getProperty("SERVER_HOST");
/* 2076 */     if (str3 != null) {
/* 2077 */       paramOraclePooledConnection.dataSourceHostNameKey = str3.trim().toLowerCase().intern();
/*      */     }
/*      */ 
/* 2083 */     str3 = localProperties.getProperty("SERVICE_NAME");
/* 2084 */     if (str3 != null) {
/* 2085 */       this.dataSourceServiceName = str3.trim();
/*      */     }
/*      */ 
/* 2090 */     str3 = localProperties.getProperty("DATABASE_NAME");
/* 2091 */     if (str3 != null) {
/* 2092 */       str2 = paramOraclePooledConnection.dataSourceDbUniqNameKey = str3.trim().toLowerCase().intern();
/*      */     }
/*      */ 
/* 2100 */     if (this.databaseInstancesList == null) {
/* 2101 */       this.databaseInstancesList = new LinkedList();
/*      */     }
/* 2103 */     int i = this.databaseInstancesList.size();
/* 2104 */     synchronized (this.databaseInstancesList)
/*      */     {
/* 2106 */       OracleDatabaseInstance localOracleDatabaseInstance1 = null;
/* 2107 */       int j = 0;
/*      */ 
/* 2109 */       for (int k = 0; k < i; k++)
/*      */       {
/* 2111 */         localOracleDatabaseInstance1 = (OracleDatabaseInstance)this.databaseInstancesList.get(k);
/* 2112 */         if ((localOracleDatabaseInstance1.databaseUniqName != str2) || (localOracleDatabaseInstance1.instanceName != str1)) {
/*      */           continue;
/*      */         }
/* 2115 */         localOracleDatabaseInstance1.numberOfConnectionsCount += 1;
/* 2116 */         j = 1;
/* 2117 */         break;
/*      */       }
/*      */ 
/* 2121 */       if (j == 0)
/*      */       {
/* 2123 */         OracleDatabaseInstance localOracleDatabaseInstance2 = new OracleDatabaseInstance(str2, str1);
/*      */ 
/* 2126 */         localOracleDatabaseInstance2.numberOfConnectionsCount += 1;
/* 2127 */         this.databaseInstancesList.add(localOracleDatabaseInstance2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void processFailoverEvent(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
/*      */   {
/* 2154 */     if (paramInt1 == 256)
/*      */     {
/* 2156 */       if ((paramString4.equalsIgnoreCase("down")) || (paramString4.equalsIgnoreCase("not_restarting")) || (paramString4.equalsIgnoreCase("restart_failed")))
/*      */       {
/* 2160 */         this.downEventCount += 1;
/*      */ 
/* 2162 */         markDownLostConnections(true, false, paramString1, paramString2, paramString3, paramString4);
/*      */ 
/* 2164 */         cleanupFailoverConnections(true, false, paramString1, paramString2, paramString3, paramString4);
/*      */       }
/* 2167 */       else if (paramString4.equalsIgnoreCase("up"))
/*      */       {
/* 2171 */         if (this.downEventCount > 0) {
/* 2172 */           this.upEventCount += 1;
/*      */         }
/*      */         try
/*      */         {
/* 2176 */           processUpEvent(paramInt2);
/*      */         }
/*      */         catch (Exception localException)
/*      */         {
/*      */         }
/*      */ 
/* 2185 */         this.isEntireServiceDownProcessed = false;
/*      */       }
/*      */     }
/* 2188 */     else if ((paramInt1 == 512) && (paramString4.equalsIgnoreCase("nodedown")))
/*      */     {
/* 2191 */       markDownLostConnections(false, true, paramString1, paramString2, paramString3, paramString4);
/*      */ 
/* 2193 */       cleanupFailoverConnections(false, true, paramString1, paramString2, paramString3, paramString4);
/*      */     }
/*      */   }
/*      */ 
/*      */   synchronized void processUpEvent(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2272 */     int i = 0;
/* 2273 */     int j = 0;
/* 2274 */     int k = getTotalCachedConnections();
/* 2275 */     boolean bool = false;
/*      */ 
/* 2278 */     if (paramInt <= 1) {
/* 2279 */       paramInt = 2;
/*      */     }
/*      */ 
/* 2286 */     if ((this.downEventCount == 0) && (this.upEventCount == 0) && (getNumberOfDefaultUserConnections() > 0))
/*      */     {
/* 2290 */       i = (int)(this.cacheSize * 0.25D);
/*      */     }
/*      */     else
/*      */     {
/* 2294 */       i = this.defaultUserPreFailureSize;
/*      */     }
/*      */ 
/* 2306 */     if (i <= 0)
/*      */     {
/* 2308 */       if (getNumberOfDefaultUserConnections() > 0)
/*      */       {
/* 2310 */         j = (int)(this.cacheSize * 0.25D);
/* 2311 */         bool = true;
/*      */       }
/*      */       else
/*      */       {
/* 2315 */         return;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2320 */       j = i / paramInt;
/*      */ 
/* 2324 */       if (j + k > this.cacheMaxLimit) {
/* 2325 */         bool = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2331 */     if (j > 0) {
/* 2332 */       loadBalanceConnections(j, bool);
/*      */     }
/*      */ 
/* 2345 */     if (this.downEventCount == this.upEventCount)
/*      */     {
/* 2347 */       this.defaultUserPreFailureSize = 0;
/* 2348 */       this.downEventCount = 0;
/* 2349 */       this.upEventCount = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void loadBalanceConnections(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2362 */     if (paramBoolean)
/*      */     {
/* 2364 */       this.connectionsToRemove = paramInt;
/*      */ 
/* 2366 */       doForEveryCachedConnection(8);
/*      */ 
/* 2368 */       this.connectionsToRemove = 0;
/*      */     }
/*      */ 
/* 2372 */     if (paramInt <= 10)
/*      */     {
/*      */       try
/*      */       {
/* 2376 */         defaultUserPrePopulateCache(paramInt);
/*      */       }
/*      */       catch (Exception localException1)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2389 */       int i = (int)(paramInt * 0.25D);
/*      */ 
/* 2391 */       for (int j = 0; j < 4; j++)
/*      */       {
/*      */         try
/*      */         {
/* 2395 */           defaultUserPrePopulateCache(i);
/*      */         }
/*      */         catch (Exception localException2)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getNumberOfDefaultUserConnections()
/*      */   {
/* 2414 */     int i = 0;
/*      */ 
/* 2416 */     if ((this.userMap != null) && (!this.userMap.isEmpty()))
/*      */     {
/* 2418 */       OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)this.userMap.get(this.defaultUser + this.defaultPassword.toUpperCase());
/*      */ 
/* 2422 */       if ((localOracleConnectionCacheEntry != null) && (localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
/*      */       {
/* 2425 */         i = localOracleConnectionCacheEntry.userConnList.size();
/*      */       }
/*      */     }
/*      */ 
/* 2429 */     return i;
/*      */   }
/*      */ 
/*      */   synchronized void markDownLostConnections(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/* 2450 */     if (!this.isEntireServiceDownProcessed)
/*      */     {
/* 2452 */       if ((this.userMap != null) && (!this.userMap.isEmpty()))
/*      */       {
/* 2456 */         Iterator localIterator1 = this.userMap.entrySet().iterator();
/*      */ 
/* 2464 */         while (localIterator1.hasNext())
/*      */         {
/* 2466 */           int i = 0;
/*      */ 
/* 2468 */           Map.Entry localEntry = (Map.Entry)localIterator1.next();
/* 2469 */           String str = null;
/* 2470 */           if ((this.defaultUser != null) && (this.defaultPassword != null)) {
/* 2471 */             str = this.defaultUser + this.defaultPassword;
/*      */           }
/*      */ 
/* 2474 */           if ((str != null) && (str.equalsIgnoreCase((String)localEntry.getKey())))
/*      */           {
/* 2476 */             i = 1;
/*      */           }
/* 2478 */           OracleConnectionCacheEntry localOracleConnectionCacheEntry = (OracleConnectionCacheEntry)localEntry.getValue();
/*      */           Object localObject1;
/*      */           Object localObject2;
/* 2482 */           if ((localOracleConnectionCacheEntry != null) && (localOracleConnectionCacheEntry.userConnList != null) && (!localOracleConnectionCacheEntry.userConnList.isEmpty()))
/*      */           {
/* 2485 */             boolean bool = false;
/* 2486 */             localObject1 = localOracleConnectionCacheEntry.userConnList.iterator();
/*      */ 
/* 2488 */             while (((Iterator)localObject1).hasNext())
/*      */             {
/* 2490 */               localObject2 = (OraclePooledConnection)((Iterator)localObject1).next();
/*      */ 
/* 2492 */               if (paramBoolean1) {
/* 2493 */                 bool = markDownConnectionsForServiceEvent(paramString1, paramString2, (OraclePooledConnection)localObject2);
/*      */               }
/* 2495 */               else if (paramBoolean2) {
/* 2496 */                 bool = markDownConnectionsForHostEvent(paramString3, (OraclePooledConnection)localObject2);
/*      */               }
/*      */ 
/* 2500 */               if ((bool) && (i != 0)) {
/* 2501 */                 this.defaultUserPreFailureSize += 1;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 2506 */           if ((localOracleConnectionCacheEntry == null) || (localOracleConnectionCacheEntry.attrConnMap == null) || (localOracleConnectionCacheEntry.attrConnMap.isEmpty())) {
/*      */             continue;
/*      */           }
/* 2509 */           Iterator localIterator2 = localOracleConnectionCacheEntry.attrConnMap.entrySet().iterator();
/*      */ 
/* 2511 */           while (localIterator2.hasNext())
/*      */           {
/* 2513 */             localObject1 = (Map.Entry)localIterator2.next();
/* 2514 */             localObject2 = ((Vector)((Map.Entry)localObject1).getValue()).iterator();
/*      */ 
/* 2516 */             while (((Iterator)localObject2).hasNext())
/*      */             {
/* 2518 */               OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)((Iterator)localObject2).next();
/*      */ 
/* 2520 */               if (paramBoolean1) {
/* 2521 */                 markDownConnectionsForServiceEvent(paramString1, paramString2, localOraclePooledConnection); continue;
/*      */               }
/* 2523 */               if (paramBoolean2) {
/* 2524 */                 markDownConnectionsForHostEvent(paramString3, localOraclePooledConnection);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2531 */       if (paramString1 == null)
/* 2532 */         this.isEntireServiceDownProcessed = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean markDownConnectionsForServiceEvent(String paramString1, String paramString2, OraclePooledConnection paramOraclePooledConnection)
/*      */   {
/* 2544 */     int i = 0;
/*      */ 
/* 2546 */     if ((paramString1 == null) || ((paramString2 == paramOraclePooledConnection.dataSourceDbUniqNameKey) && (paramString1 == paramOraclePooledConnection.dataSourceInstanceNameKey)))
/*      */     {
/* 2550 */       paramOraclePooledConnection.connectionMarkedDown = true;
/* 2551 */       i = 1;
/*      */     }
/*      */ 
/* 2554 */     return i;
/*      */   }
/*      */ 
/*      */   private boolean markDownConnectionsForHostEvent(String paramString, OraclePooledConnection paramOraclePooledConnection)
/*      */   {
/* 2563 */     int i = 0;
/*      */ 
/* 2565 */     if (paramString == paramOraclePooledConnection.dataSourceHostNameKey)
/*      */     {
/* 2567 */       paramOraclePooledConnection.connectionMarkedDown = true;
/* 2568 */       paramOraclePooledConnection.needToAbort = true;
/* 2569 */       i = 1;
/*      */     }
/*      */ 
/* 2572 */     return i;
/*      */   }
/*      */ 
/*      */   synchronized void cleanupFailoverConnections(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/* 2594 */     OraclePooledConnection localOraclePooledConnection = null;
/* 2595 */     Object[] arrayOfObject = this.checkedOutConnectionList.toArray();
/* 2596 */     int i = this.checkedOutConnectionList.size();
/*      */ 
/* 2598 */     OraclePooledConnection[] arrayOfOraclePooledConnection = new OraclePooledConnection[i];
/* 2599 */     int j = 0;
/*      */ 
/* 2601 */     for (int k = 0; k < i; k++)
/*      */     {
/*      */       try
/*      */       {
/* 2605 */         localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[k];
/*      */ 
/* 2607 */         if (((paramBoolean1) && ((paramString1 == null) || (paramString1 == localOraclePooledConnection.dataSourceInstanceNameKey)) && (paramString2 == localOraclePooledConnection.dataSourceDbUniqNameKey)) || ((paramBoolean2) && (paramString3 == localOraclePooledConnection.dataSourceHostNameKey)))
/*      */         {
/* 2614 */           if ((localOraclePooledConnection.isSameUser(this.defaultUser, this.defaultPassword)) && (localOraclePooledConnection.cachedConnectionAttributes != null) && (localOraclePooledConnection.cachedConnectionAttributes.isEmpty()))
/*      */           {
/* 2617 */             this.defaultUserPreFailureSize += 1;
/*      */           }
/*      */ 
/* 2620 */           this.checkedOutConnectionList.removeElement(localOraclePooledConnection);
/*      */ 
/* 2627 */           abortConnection(localOraclePooledConnection);
/* 2628 */           localOraclePooledConnection.needToAbort = true;
/*      */ 
/* 2631 */           arrayOfOraclePooledConnection[(j++)] = localOraclePooledConnection;
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2642 */     for (k = 0; k < j; k++)
/*      */     {
/*      */       try
/*      */       {
/* 2646 */         closeCheckedOutConnection(arrayOfOraclePooledConnection[k], false);
/*      */       }
/*      */       catch (SQLException localSQLException2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2656 */     if ((this.checkedOutConnectionList.size() < i) && (this.cacheConnectionWaitTimeout > 0))
/*      */     {
/* 2659 */       notifyAll();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2665 */       doForEveryCachedConnection(2);
/*      */     }
/*      */     catch (SQLException localSQLException1)
/*      */     {
/*      */     }
/*      */ 
/* 2675 */     if ((this.databaseInstancesList != null) && ((i = this.databaseInstancesList.size()) > 0))
/*      */     {
/* 2678 */       synchronized (this.databaseInstancesList)
/*      */       {
/* 2680 */         OracleDatabaseInstance localOracleDatabaseInstance = null;
/* 2681 */         arrayOfObject = this.databaseInstancesList.toArray();
/*      */ 
/* 2683 */         for (int m = 0; m < i; m++)
/*      */         {
/* 2685 */           localOracleDatabaseInstance = (OracleDatabaseInstance)arrayOfObject[m];
/*      */ 
/* 2687 */           if ((localOracleDatabaseInstance.databaseUniqName != paramString2) || (localOracleDatabaseInstance.instanceName != paramString1))
/*      */           {
/*      */             continue;
/*      */           }
/* 2691 */           if (localOracleDatabaseInstance.flag <= 3)
/* 2692 */             this.dbInstancePercentTotal -= localOracleDatabaseInstance.percent;
/* 2693 */           this.databaseInstancesList.remove(localOracleDatabaseInstance);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void zapRLBInfo()
/*      */   {
/* 2705 */     this.databaseInstancesList.clear();
/*      */   }
/*      */ 
/*      */   protected synchronized void closeAndRemovePooledConnection(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 2717 */     if (paramOraclePooledConnection != null)
/*      */     {
/* 2719 */       if (paramOraclePooledConnection.needToAbort) {
/* 2720 */         abortConnection(paramOraclePooledConnection);
/*      */       }
/* 2722 */       actualPooledConnectionClose(paramOraclePooledConnection);
/* 2723 */       removeCacheConnection(paramOraclePooledConnection);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void abortConnection(OraclePooledConnection paramOraclePooledConnection)
/*      */   {
/*      */     try
/*      */     {
/* 2740 */       ((OracleConnection)paramOraclePooledConnection.getPhysicalHandle()).abort();
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void actualPooledConnectionClose(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 2763 */     int i = 0;
/* 2764 */     if ((this.databaseInstancesList != null) && ((i = this.databaseInstancesList.size()) > 0))
/*      */     {
/* 2767 */       synchronized (this.databaseInstancesList)
/*      */       {
/* 2769 */         OracleDatabaseInstance localOracleDatabaseInstance = null;
/*      */ 
/* 2771 */         for (int j = 0; j < i; j++)
/*      */         {
/* 2773 */           localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(j);
/* 2774 */           if ((localOracleDatabaseInstance.databaseUniqName != paramOraclePooledConnection.dataSourceDbUniqNameKey) || (localOracleDatabaseInstance.instanceName != paramOraclePooledConnection.dataSourceInstanceNameKey)) {
/*      */             continue;
/*      */           }
/* 2777 */           if (localOracleDatabaseInstance.numberOfConnectionsCount <= 0) break;
/* 2778 */           localOracleDatabaseInstance.numberOfConnectionsCount -= 1; break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2792 */       this.connectionClosedCount += 1;
/* 2793 */       paramOraclePooledConnection.close();
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int getCacheTimeToLiveTimeout()
/*      */   {
/* 2804 */     return this.cacheTimeToLiveTimeout;
/*      */   }
/*      */ 
/*      */   protected int getCacheInactivityTimeout()
/*      */   {
/* 2809 */     return this.cacheInactivityTimeout;
/*      */   }
/*      */ 
/*      */   protected int getCachePropertyCheckInterval()
/*      */   {
/* 2814 */     return this.cachePropertyCheckInterval;
/*      */   }
/*      */ 
/*      */   protected int getCacheAbandonedTimeout()
/*      */   {
/* 2819 */     return this.cacheAbandonedConnectionTimeout;
/*      */   }
/*      */ 
/*      */   private synchronized void processConnectionCacheCallback()
/*      */     throws SQLException
/*      */   {
/* 2834 */     float f = this.cacheMaxLimit / 100.0F;
/* 2835 */     int i = (int)(this.cacheLowerThresholdLimit * f);
/*      */ 
/* 2838 */     releaseBasedOnPriority(1024, i);
/*      */ 
/* 2841 */     if (this.cacheSize < i)
/* 2842 */       releaseBasedOnPriority(512, i);
/*      */   }
/*      */ 
/*      */   private void releaseBasedOnPriority(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 2855 */     Object[] arrayOfObject = this.checkedOutConnectionList.toArray();
/*      */ 
/* 2857 */     for (int i = 0; (i < arrayOfObject.length) && (this.cacheSize < paramInt2); i++)
/*      */     {
/* 2859 */       OraclePooledConnection localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[i];
/* 2860 */       OracleConnection localOracleConnection = null;
/*      */ 
/* 2862 */       if (localOraclePooledConnection != null) {
/* 2863 */         localOracleConnection = (OracleConnection)localOraclePooledConnection.getLogicalHandle();
/*      */       }
/* 2865 */       if (localOracleConnection == null)
/*      */         continue;
/* 2867 */       OracleConnectionCacheCallback localOracleConnectionCacheCallback = localOracleConnection.getConnectionCacheCallbackObj();
/*      */ 
/* 2870 */       if ((localOracleConnectionCacheCallback == null) || ((localOracleConnection.getConnectionCacheCallbackFlag() != 2) && (localOracleConnection.getConnectionCacheCallbackFlag() != 4)))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 2876 */       if (paramInt1 != localOracleConnection.getConnectionReleasePriority())
/*      */         continue;
/* 2878 */       Object localObject = localOracleConnection.getConnectionCacheCallbackPrivObj();
/* 2879 */       localOracleConnectionCacheCallback.releaseConnection(localOracleConnection, localObject);
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void processConnectionWaitTimeout(long paramLong)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 2901 */       wait(paramLong);
/*      */     }
/*      */     catch (InterruptedException localInterruptedException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processInactivityTimeout(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 2925 */     long l1 = paramOraclePooledConnection.getLastAccessedTime();
/* 2926 */     long l2 = System.currentTimeMillis();
/*      */ 
/* 2934 */     if ((getTotalCachedConnections() > this.cacheMinLimit) && (l2 - l1 > this.cacheInactivityTimeout * 1000))
/*      */     {
/* 2941 */       closeAndRemovePooledConnection(paramOraclePooledConnection);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cleanupTimeoutThread()
/*      */     throws SQLException
/*      */   {
/* 2956 */     if (this.timeoutThread != null)
/*      */     {
/* 2958 */       this.timeoutThread.timeToLive = false;
/*      */ 
/* 2962 */       if (this.timeoutThread.isSleeping) {
/* 2963 */         this.timeoutThread.interrupt();
/*      */       }
/* 2965 */       this.timeoutThread = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void purgeCacheConnections(boolean paramBoolean)
/*      */   {
/*      */     try
/*      */     {
/* 2980 */       if (paramBoolean) {
/* 2981 */         doForEveryCheckedOutConnection(1);
/*      */       }
/*      */ 
/* 2985 */       doForEveryCachedConnection(1);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void updateDatabaseInstance(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*      */   {
/* 3004 */     if (this.databaseInstancesList == null) {
/* 3005 */       this.databaseInstancesList = new LinkedList();
/*      */     }
/* 3007 */     synchronized (this.databaseInstancesList)
/*      */     {
/* 3009 */       int i = this.databaseInstancesList.size();
/* 3010 */       int j = 0;
/*      */ 
/* 3012 */       for (int k = 0; k < i; k++)
/*      */       {
/* 3014 */         OracleDatabaseInstance localOracleDatabaseInstance2 = (OracleDatabaseInstance)this.databaseInstancesList.get(k);
/* 3015 */         if ((localOracleDatabaseInstance2.databaseUniqName != paramString1) || (localOracleDatabaseInstance2.instanceName != paramString2)) {
/*      */           continue;
/*      */         }
/* 3018 */         localOracleDatabaseInstance2.percent = paramInt1;
/* 3019 */         localOracleDatabaseInstance2.flag = paramInt2;
/* 3020 */         j = 1;
/* 3021 */         break;
/*      */       }
/*      */ 
/* 3025 */       if (j == 0)
/*      */       {
/* 3027 */         OracleDatabaseInstance localOracleDatabaseInstance1 = new OracleDatabaseInstance(paramString1, paramString2);
/*      */ 
/* 3030 */         localOracleDatabaseInstance1.percent = paramInt1;
/* 3031 */         localOracleDatabaseInstance1.flag = paramInt2;
/*      */ 
/* 3033 */         this.databaseInstancesList.add(localOracleDatabaseInstance1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void processDatabaseInstances()
/*      */     throws SQLException
/*      */   {
/* 3049 */     OracleDatabaseInstance localOracleDatabaseInstance = null;
/* 3050 */     LinkedList localLinkedList = new LinkedList();
/*      */ 
/* 3052 */     if (this.databaseInstancesList != null)
/*      */     {
/* 3054 */       synchronized (this.databaseInstancesList)
/*      */       {
/* 3056 */         int i = 0;
/* 3057 */         int j = 0;
/*      */ 
/* 3060 */         this.useGoodGroup = false;
/*      */ 
/* 3063 */         int k = this.databaseInstancesList.size();
/*      */ 
/* 3065 */         for (int m = 0; m < k; m++)
/*      */         {
/* 3067 */           localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
/*      */ 
/* 3071 */           if (localOracleDatabaseInstance.flag <= 3) {
/* 3072 */             i += localOracleDatabaseInstance.percent;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3080 */         if (i > 0)
/*      */         {
/* 3082 */           this.dbInstancePercentTotal = i;
/* 3083 */           this.useGoodGroup = true;
/*      */         }
/*      */ 
/* 3090 */         if (k > 1)
/*      */         {
/* 3093 */           for (m = 0; m < k; m++)
/*      */           {
/* 3095 */             localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
/* 3096 */             this.countTotal += localOracleDatabaseInstance.attemptedConnRequestCount;
/*      */           }
/*      */ 
/* 3104 */           if (this.countTotal > k * 1000)
/*      */           {
/* 3106 */             for (m = 0; m < k; m++)
/*      */             {
/* 3108 */               localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
/*      */ 
/* 3111 */               float f1 = localOracleDatabaseInstance.attemptedConnRequestCount / this.countTotal;
/*      */ 
/* 3113 */               float f2 = localOracleDatabaseInstance.numberOfConnectionsCount / getTotalCachedConnections();
/*      */ 
/* 3127 */               if (f2 <= f1 * 2.0F)
/*      */               {
/*      */                 continue;
/*      */               }
/*      */ 
/* 3132 */               if ((int)(localOracleDatabaseInstance.numberOfConnectionsCount * 0.25D) >= 1) {
/* 3133 */                 localLinkedList.add(localOracleDatabaseInstance);
/*      */               }
/* 3135 */               j = 1;
/*      */             }
/*      */ 
/* 3140 */             if (j != 0)
/*      */             {
/* 3142 */               for (m = 0; m < k; m++)
/*      */               {
/* 3144 */                 localOracleDatabaseInstance = (OracleDatabaseInstance)this.databaseInstancesList.get(m);
/* 3145 */                 localOracleDatabaseInstance.attemptedConnRequestCount = 0;
/*      */               }
/* 3147 */               j = 0;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3159 */       if (localLinkedList.size() > 0)
/*      */       {
/* 3161 */         this.instancesToRetireList = localLinkedList;
/*      */ 
/* 3163 */         if (this.gravitateCacheThread != null)
/*      */         {
/*      */           try
/*      */           {
/* 3167 */             this.gravitateCacheThread.interrupt();
/* 3168 */             this.gravitateCacheThread.join();
/*      */           }
/*      */           catch (InterruptedException localInterruptedException)
/*      */           {
/*      */           }
/*      */ 
/* 3176 */           this.gravitateCacheThread = null;
/*      */         }
/*      */ 
/* 3180 */         this.gravitateCacheThread = new OracleGravitateConnectionCacheThread(this);
/*      */ 
/* 3183 */         this.cacheManager.checkAndStartThread(this.gravitateCacheThread);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void gravitateCache()
/*      */   {
/* 3199 */     if (this.instancesToRetireList != null)
/*      */     {
/* 3201 */       synchronized (this.instancesToRetireList)
/*      */       {
/* 3203 */         int i = this.instancesToRetireList.size();
/*      */ 
/* 3205 */         for (int j = 0; j < i; j++)
/*      */         {
/* 3207 */           this.instanceToRetire = ((OracleDatabaseInstance)this.instancesToRetireList.get(j));
/* 3208 */           this.retireConnectionsCount = (int)(this.instanceToRetire.numberOfConnectionsCount * 0.25D);
/*      */           try
/*      */           {
/* 3217 */             doForEveryCachedConnection(24);
/*      */           }
/*      */           catch (SQLException localSQLException1)
/*      */           {
/*      */           }
/*      */ 
/* 3232 */           if (this.retireConnectionsCount <= 0)
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/* 3239 */             doForEveryCheckedOutConnection(24);
/*      */           }
/*      */           catch (SQLException localSQLException2)
/*      */           {
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3252 */     this.retireConnectionsCount = 0;
/* 3253 */     this.instanceToRetire = null;
/* 3254 */     this.instancesToRetireList = null;
/* 3255 */     this.countTotal = 0;
/*      */   }
/*      */ 
/*      */   protected void cleanupRLBThreads()
/*      */   {
/* 3272 */     if (this.gravitateCacheThread != null)
/*      */     {
/*      */       try
/*      */       {
/* 3276 */         this.gravitateCacheThread.interrupt();
/* 3277 */         this.gravitateCacheThread.join();
/*      */       }
/*      */       catch (InterruptedException localInterruptedException)
/*      */       {
/*      */       }
/*      */ 
/* 3285 */       this.gravitateCacheThread = null;
/*      */     }
/*      */ 
/* 3288 */     if (this.runtimeLoadBalancingThread != null)
/*      */     {
/*      */       try
/*      */       {
/* 3292 */         this.runtimeLoadBalancingThread.interrupt();
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 3300 */       this.runtimeLoadBalancingThread = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   Map getStatistics()
/*      */     throws SQLException
/*      */   {
/* 3310 */     HashMap localHashMap = new HashMap(2);
/* 3311 */     localHashMap.put("PhysicalConnectionClosedCount", new Integer(this.connectionClosedCount));
/* 3312 */     localHashMap.put("PhysicalConnectionCreatedCount", new Integer(this.connectionCreatedCount));
/* 3313 */     return localHashMap;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleImplicitConnectionCache
 * JD-Core Version:    0.6.0
 */