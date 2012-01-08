/*      */ package oracle.jdbc.pool;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.Reference;
/*      */ import javax.naming.StringRefAddr;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.driver.OracleDriver;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.jdbc.oci.OracleOCIConnection;
/*      */ 
/*      */ public class OracleOCIConnectionPool extends OracleDataSource
/*      */ {
/*      */   public OracleOCIConnection m_connection_pool;
/*      */   public static final String IS_CONNECTION_POOLING = "is_connection_pooling";
/*   45 */   private int m_conn_min_limit = 0;
/*   46 */   private int m_conn_max_limit = 0;
/*   47 */   private int m_conn_increment = 0;
/*   48 */   private int m_conn_active_size = 0;
/*   49 */   private int m_conn_pool_size = 0;
/*   50 */   private int m_conn_timeout = 0;
/*   51 */   private String m_conn_nowait = "false";
/*   52 */   private int m_is_transactions_distributed = 0;
/*      */   public static final String CONNPOOL_OBJECT = "connpool_object";
/*      */   public static final String CONNPOOL_LOGON_MODE = "connection_pool";
/*      */   public static final String CONNECTION_POOL = "connection_pool";
/*      */   public static final String CONNPOOL_CONNECTION = "connpool_connection";
/*      */   public static final String CONNPOOL_PROXY_CONNECTION = "connpool_proxy_connection";
/*      */   public static final String CONNPOOL_ALIASED_CONNECTION = "connpool_alias_connection";
/*      */   public static final String PROXY_USER_NAME = "proxy_user_name";
/*      */   public static final String PROXY_DISTINGUISHED_NAME = "proxy_distinguished_name";
/*      */   public static final String PROXY_CERTIFICATE = "proxy_certificate";
/*      */   public static final String PROXY_ROLES = "proxy_roles";
/*      */   public static final String PROXY_NUM_ROLES = "proxy_num_roles";
/*      */   public static final String PROXY_PASSWORD = "proxy_password";
/*      */   public static final String PROXYTYPE = "proxytype";
/*      */   public static final String PROXYTYPE_USER_NAME = "proxytype_user_name";
/*      */   public static final String PROXYTYPE_DISTINGUISHED_NAME = "proxytype_distinguished_name";
/*      */   public static final String PROXYTYPE_CERTIFICATE = "proxytype_certificate";
/*      */   public static final String CONNECTION_ID = "connection_id";
/*   88 */   public static String CONNPOOL_MIN_LIMIT = "connpool_min_limit";
/*   89 */   public static String CONNPOOL_MAX_LIMIT = "connpool_max_limit";
/*   90 */   public static String CONNPOOL_INCREMENT = "connpool_increment";
/*   91 */   public static String CONNPOOL_ACTIVE_SIZE = "connpool_active_size";
/*   92 */   public static String CONNPOOL_POOL_SIZE = "connpool_pool_size";
/*   93 */   public static String CONNPOOL_TIMEOUT = "connpool_timeout";
/*   94 */   public static String CONNPOOL_NOWAIT = "connpool_nowait";
/*   95 */   public static String CONNPOOL_IS_POOLCREATED = "connpool_is_poolcreated";
/*      */   public static final String TRANSACTIONS_DISTRIBUTED = "transactions_distributed";
/*  100 */   private Hashtable m_lconnections = null;
/*      */ 
/*  105 */   private boolean m_poolCreated = false;
/*      */ 
/*  107 */   private OracleDriver m_oracleDriver = new OracleDriver();
/*      */ 
/*  110 */   protected int m_stmtCacheSize = 0;
/*  111 */   protected boolean m_stmtClearMetaData = false;
/*      */ 
/* 1033 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*      */ 
/*      */   public OracleOCIConnectionPool(String paramString1, String paramString2, String paramString3, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  128 */     this();
/*      */ 
/*  132 */     setURL(paramString3);
/*  133 */     setUser(paramString1);
/*  134 */     setPassword(paramString2);
/*      */ 
/*  136 */     createConnectionPool(paramProperties);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public OracleOCIConnectionPool(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/*  147 */     this();
/*      */ 
/*  151 */     setURL(paramString3);
/*  152 */     setUser(paramString1);
/*  153 */     setPassword(paramString2);
/*      */ 
/*  155 */     createConnectionPool(null);
/*      */   }
/*      */ 
/*      */   public OracleOCIConnectionPool()
/*      */     throws SQLException
/*      */   {
/*  174 */     this.isOracleDataSource = false;
/*  175 */     this.m_lconnections = new Hashtable(10);
/*      */ 
/*  177 */     setDriverType("oci8");
/*      */   }
/*      */ 
/*      */   public synchronized Connection getConnection()
/*      */     throws SQLException
/*      */   {
/*  196 */     if (!isPoolCreated()) {
/*  197 */       createConnectionPool(null);
/*      */     }
/*      */ 
/*  201 */     Connection localConnection = getConnection(this.user, this.password);
/*      */ 
/*  206 */     return localConnection;
/*      */   }
/*      */ 
/*      */   public synchronized Connection getConnection(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/*  222 */     if (!isPoolCreated()) {
/*  223 */       createConnectionPool(null);
/*      */     }
/*  225 */     Properties localProperties = new Properties();
/*      */ 
/*  227 */     localProperties.put("is_connection_pooling", "true");
/*  228 */     localProperties.put("user", paramString1);
/*  229 */     localProperties.put("password", paramString2);
/*  230 */     localProperties.put("connection_pool", "connpool_connection");
/*  231 */     localProperties.put("connpool_object", this.m_connection_pool);
/*      */ 
/*  233 */     OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, localProperties);
/*      */ 
/*  236 */     if (localOracleOCIConnection == null) {
/*  237 */       DatabaseError.throwSqlException(67);
/*      */     }
/*      */ 
/*  242 */     localOracleOCIConnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);
/*      */ 
/*  244 */     this.m_lconnections.put(localOracleOCIConnection, localOracleOCIConnection);
/*  245 */     localOracleOCIConnection.setConnectionPool(this);
/*      */ 
/*  250 */     return localOracleOCIConnection;
/*      */   }
/*      */ 
/*      */   public synchronized Reference getReference()
/*      */     throws NamingException
/*      */   {
/*  263 */     Reference localReference = new Reference(getClass().getName(), "oracle.jdbc.pool.OracleDataSourceFactory", null);
/*      */ 
/*  267 */     super.addRefProperties(localReference);
/*      */ 
/*  271 */     localReference.add(new StringRefAddr(CONNPOOL_MIN_LIMIT, String.valueOf(this.m_conn_min_limit)));
/*      */ 
/*  274 */     localReference.add(new StringRefAddr(CONNPOOL_MAX_LIMIT, String.valueOf(this.m_conn_max_limit)));
/*      */ 
/*  276 */     localReference.add(new StringRefAddr(CONNPOOL_INCREMENT, String.valueOf(this.m_conn_increment)));
/*      */ 
/*  279 */     localReference.add(new StringRefAddr(CONNPOOL_ACTIVE_SIZE, String.valueOf(this.m_conn_active_size)));
/*      */ 
/*  282 */     localReference.add(new StringRefAddr(CONNPOOL_POOL_SIZE, String.valueOf(this.m_conn_pool_size)));
/*      */ 
/*  285 */     localReference.add(new StringRefAddr(CONNPOOL_TIMEOUT, String.valueOf(this.m_conn_timeout)));
/*      */ 
/*  288 */     localReference.add(new StringRefAddr(CONNPOOL_NOWAIT, this.m_conn_nowait));
/*      */ 
/*  290 */     localReference.add(new StringRefAddr(CONNPOOL_IS_POOLCREATED, String.valueOf(isPoolCreated())));
/*      */ 
/*  293 */     localReference.add(new StringRefAddr("transactions_distributed", String.valueOf(isDistributedTransEnabled())));
/*      */ 
/*  299 */     return localReference;
/*      */   }
/*      */ 
/*      */   public synchronized OracleConnection getProxyConnection(String paramString, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  331 */     if (!isPoolCreated()) {
/*  332 */       createConnectionPool(null);
/*      */     }
/*      */ 
/*  337 */     if (paramString == "proxytype_user_name")
/*  338 */       paramProperties.put("user", paramProperties.getProperty("proxy_user_name"));
/*  339 */     else if (paramString == "proxytype_distinguished_name") {
/*  340 */       paramProperties.put("user", paramProperties.getProperty("proxy_distinguished_name"));
/*      */     }
/*  342 */     else if (paramString == "proxytype_certificate") {
/*  343 */       paramProperties.put("user", String.valueOf(paramProperties.getProperty("proxy_user_name")));
/*      */     }
/*      */     else {
/*  346 */       DatabaseError.throwSqlException(107, "null properties");
/*      */     }
/*      */ 
/*  349 */     paramProperties.put("is_connection_pooling", "true");
/*  350 */     paramProperties.put("proxytype", paramString);
/*      */     String[] arrayOfString;
/*  352 */     if ((arrayOfString = (String[])paramProperties.get("proxy_roles")) != null)
/*      */     {
/*  354 */       paramProperties.put("proxy_num_roles", new Integer(arrayOfString.length));
/*      */     }
/*      */     else
/*      */     {
/*  358 */       paramProperties.put("proxy_num_roles", new Integer(0));
/*      */     }
/*      */ 
/*  361 */     paramProperties.put("connection_pool", "connpool_proxy_connection");
/*  362 */     paramProperties.put("connpool_object", this.m_connection_pool);
/*      */ 
/*  364 */     OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, paramProperties);
/*      */ 
/*  367 */     if (localOracleOCIConnection == null) {
/*  368 */       DatabaseError.throwSqlException(67);
/*      */     }
/*      */ 
/*  372 */     localOracleOCIConnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);
/*      */ 
/*  374 */     this.m_lconnections.put(localOracleOCIConnection, localOracleOCIConnection);
/*  375 */     localOracleOCIConnection.setConnectionPool(this);
/*      */ 
/*  380 */     return localOracleOCIConnection;
/*      */   }
/*      */ 
/*      */   public synchronized OracleConnection getAliasedConnection(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  400 */     if (!isPoolCreated()) {
/*  401 */       createConnectionPool(null);
/*      */     }
/*  403 */     Properties localProperties = new Properties();
/*      */ 
/*  405 */     localProperties.put("is_connection_pooling", "true");
/*  406 */     localProperties.put("connection_id", paramArrayOfByte);
/*  407 */     localProperties.put("connection_pool", "connpool_alias_connection");
/*  408 */     localProperties.put("connpool_object", this.m_connection_pool);
/*      */ 
/*  410 */     OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)this.m_oracleDriver.connect(this.url, localProperties);
/*      */ 
/*  413 */     if (localOracleOCIConnection == null) {
/*  414 */       DatabaseError.throwSqlException(67);
/*      */     }
/*      */ 
/*  419 */     localOracleOCIConnection.setStmtCacheSize(this.m_stmtCacheSize, this.m_stmtClearMetaData);
/*      */ 
/*  421 */     this.m_lconnections.put(localOracleOCIConnection, localOracleOCIConnection);
/*  422 */     localOracleOCIConnection.setConnectionPool(this);
/*      */ 
/*  427 */     return localOracleOCIConnection;
/*      */   }
/*      */ 
/*      */   public synchronized void close()
/*      */     throws SQLException
/*      */   {
/*  442 */     if (!isPoolCreated()) {
/*  443 */       return;
/*      */     }
/*      */ 
/*  447 */     Enumeration localEnumeration = this.m_lconnections.elements();
/*      */ 
/*  449 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*  451 */       OracleOCIConnection localOracleOCIConnection = (OracleOCIConnection)localEnumeration.nextElement();
/*      */ 
/*  453 */       if ((localOracleOCIConnection == null) || (localOracleOCIConnection == this.m_connection_pool))
/*      */         continue;
/*  455 */       localOracleOCIConnection.close();
/*      */     }
/*      */ 
/*  460 */     this.m_connection_pool.close();
/*      */   }
/*      */ 
/*      */   public synchronized void setPoolConfig(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  481 */     if (paramProperties == null) {
/*  482 */       DatabaseError.throwSqlException(106, "null properties");
/*      */     }
/*      */ 
/*  487 */     if (!isPoolCreated())
/*      */     {
/*  489 */       createConnectionPool(paramProperties);
/*      */     }
/*      */     else
/*      */     {
/*  493 */       Properties localProperties = new Properties();
/*      */ 
/*  495 */       checkPoolConfig(paramProperties, localProperties);
/*      */ 
/*  497 */       int[] arrayOfInt = new int[6];
/*      */ 
/*  499 */       readPoolConfig(localProperties, arrayOfInt);
/*      */ 
/*  502 */       this.m_connection_pool.setConnectionPoolInfo(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[5]);
/*      */     }
/*      */ 
/*  508 */     storePoolProperties();
/*      */   }
/*      */ 
/*      */   public static void readPoolConfig(Properties paramProperties, int[] paramArrayOfInt)
/*      */   {
/*  518 */     for (int i = 0; i < 6; i++) {
/*  519 */       paramArrayOfInt[i] = 0;
/*      */     }
/*  521 */     String str = paramProperties.getProperty(CONNPOOL_MIN_LIMIT);
/*      */ 
/*  523 */     if (str != null) {
/*  524 */       paramArrayOfInt[0] = Integer.parseInt(str);
/*      */     }
/*  526 */     str = paramProperties.getProperty(CONNPOOL_MAX_LIMIT);
/*      */ 
/*  528 */     if (str != null) {
/*  529 */       paramArrayOfInt[1] = Integer.parseInt(str);
/*      */     }
/*  531 */     str = paramProperties.getProperty(CONNPOOL_INCREMENT);
/*      */ 
/*  533 */     if (str != null) {
/*  534 */       paramArrayOfInt[2] = Integer.parseInt(str);
/*      */     }
/*  536 */     str = paramProperties.getProperty(CONNPOOL_TIMEOUT);
/*      */ 
/*  538 */     if (str != null) {
/*  539 */       paramArrayOfInt[3] = Integer.parseInt(str);
/*      */     }
/*  541 */     str = paramProperties.getProperty(CONNPOOL_NOWAIT);
/*      */ 
/*  543 */     if ((str != null) && (str.equalsIgnoreCase("true"))) {
/*  544 */       paramArrayOfInt[4] = 1;
/*      */     }
/*  546 */     str = paramProperties.getProperty("transactions_distributed");
/*      */ 
/*  548 */     if ((str != null) && (str.equalsIgnoreCase("true")))
/*  549 */       paramArrayOfInt[5] = 1;
/*      */   }
/*      */ 
/*      */   private void checkPoolConfig(Properties paramProperties1, Properties paramProperties2)
/*      */     throws SQLException
/*      */   {
/*  559 */     String str1 = (String)paramProperties1.get("transactions_distributed");
/*  560 */     String str2 = (String)paramProperties1.get(CONNPOOL_NOWAIT);
/*      */ 
/*  562 */     if (((str1 != null) && (!str1.equalsIgnoreCase("true"))) || ((str2 != null) && (!str2.equalsIgnoreCase("true"))) || (paramProperties1.get(CONNPOOL_MIN_LIMIT) == null) || (paramProperties1.get(CONNPOOL_MAX_LIMIT) == null) || (paramProperties1.get(CONNPOOL_INCREMENT) == null) || (Integer.decode((String)paramProperties1.get(CONNPOOL_MIN_LIMIT)).intValue() < 0) || (Integer.decode((String)paramProperties1.get(CONNPOOL_MAX_LIMIT)).intValue() < 0) || (Integer.decode((String)paramProperties1.get(CONNPOOL_INCREMENT)).intValue() < 0))
/*      */     {
/*  579 */       DatabaseError.throwSqlException(106, "");
/*      */     }
/*      */ 
/*  585 */     Enumeration localEnumeration = paramProperties1.propertyNames();
/*      */ 
/*  589 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*  591 */       String str3 = (String)localEnumeration.nextElement();
/*  592 */       String str4 = paramProperties1.getProperty(str3);
/*      */ 
/*  597 */       if ((str3 == "transactions_distributed") || (str3 == CONNPOOL_NOWAIT)) {
/*  598 */         paramProperties2.put(str3, "true"); continue;
/*      */       }
/*  600 */       paramProperties2.put(str3, str4);
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void storePoolProperties()
/*      */     throws SQLException
/*      */   {
/*  610 */     Properties localProperties = getPoolConfig();
/*      */ 
/*  612 */     this.m_conn_min_limit = Integer.decode(localProperties.getProperty(CONNPOOL_MIN_LIMIT)).intValue();
/*      */ 
/*  614 */     this.m_conn_max_limit = Integer.decode(localProperties.getProperty(CONNPOOL_MAX_LIMIT)).intValue();
/*      */ 
/*  616 */     this.m_conn_increment = Integer.decode(localProperties.getProperty(CONNPOOL_INCREMENT)).intValue();
/*      */ 
/*  618 */     this.m_conn_active_size = Integer.decode(localProperties.getProperty(CONNPOOL_ACTIVE_SIZE)).intValue();
/*      */ 
/*  620 */     this.m_conn_pool_size = Integer.decode(localProperties.getProperty(CONNPOOL_POOL_SIZE)).intValue();
/*      */ 
/*  622 */     this.m_conn_timeout = Integer.decode(localProperties.getProperty(CONNPOOL_TIMEOUT)).intValue();
/*      */ 
/*  624 */     this.m_conn_nowait = localProperties.getProperty(CONNPOOL_NOWAIT);
/*      */   }
/*      */ 
/*      */   public synchronized Properties getPoolConfig()
/*      */     throws SQLException
/*      */   {
/*  633 */     if (!isPoolCreated()) {
/*  634 */       createConnectionPool(null);
/*      */     }
/*      */ 
/*  638 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  640 */     localProperties.put(CONNPOOL_IS_POOLCREATED, String.valueOf(isPoolCreated()));
/*      */ 
/*  645 */     return localProperties;
/*      */   }
/*      */ 
/*      */   public synchronized int getActiveSize()
/*      */     throws SQLException
/*      */   {
/*  662 */     if (!isPoolCreated()) {
/*  663 */       createConnectionPool(null);
/*      */     }
/*  665 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  667 */     String str = localProperties.getProperty(CONNPOOL_ACTIVE_SIZE);
/*  668 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  673 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized int getPoolSize()
/*      */     throws SQLException
/*      */   {
/*  690 */     if (!isPoolCreated()) {
/*  691 */       createConnectionPool(null);
/*      */     }
/*  693 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  695 */     String str = localProperties.getProperty(CONNPOOL_POOL_SIZE);
/*  696 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  701 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized int getTimeout()
/*      */     throws SQLException
/*      */   {
/*  719 */     if (!isPoolCreated()) {
/*  720 */       createConnectionPool(null);
/*      */     }
/*  722 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  724 */     String str = localProperties.getProperty(CONNPOOL_TIMEOUT);
/*  725 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  730 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized String getNoWait()
/*      */     throws SQLException
/*      */   {
/*  751 */     if (!isPoolCreated()) {
/*  752 */       createConnectionPool(null);
/*      */     }
/*  754 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  756 */     return localProperties.getProperty(CONNPOOL_NOWAIT);
/*      */   }
/*      */ 
/*      */   public synchronized int getMinLimit()
/*      */     throws SQLException
/*      */   {
/*  772 */     if (!isPoolCreated()) {
/*  773 */       createConnectionPool(null);
/*      */     }
/*  775 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  777 */     String str = localProperties.getProperty(CONNPOOL_MIN_LIMIT);
/*  778 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  783 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized int getMaxLimit()
/*      */     throws SQLException
/*      */   {
/*  797 */     if (!isPoolCreated()) {
/*  798 */       createConnectionPool(null);
/*      */     }
/*  800 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  802 */     String str = localProperties.getProperty(CONNPOOL_MAX_LIMIT);
/*  803 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  808 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized int getConnectionIncrement()
/*      */     throws SQLException
/*      */   {
/*  822 */     if (!isPoolCreated()) {
/*  823 */       createConnectionPool(null);
/*      */     }
/*  825 */     Properties localProperties = this.m_connection_pool.getConnectionPoolInfo();
/*      */ 
/*  827 */     String str = localProperties.getProperty(CONNPOOL_INCREMENT);
/*  828 */     int i = Integer.decode(str).intValue();
/*      */ 
/*  833 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized boolean isDistributedTransEnabled()
/*      */   {
/*  844 */     return this.m_is_transactions_distributed == 1;
/*      */   }
/*      */ 
/*      */   private void createConnectionPool(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  859 */     if (isPoolCreated()) {
/*  860 */       return;
/*      */     }
/*  862 */     if ((this.user == null) || (this.password == null))
/*      */     {
/*  864 */       DatabaseError.throwSqlException(106, " ");
/*      */     }
/*      */     else
/*      */     {
/*  869 */       Properties localProperties = new Properties();
/*      */ 
/*  872 */       if (paramProperties != null) {
/*  873 */         checkPoolConfig(paramProperties, localProperties);
/*      */       }
/*  875 */       localProperties.put("is_connection_pooling", "true");
/*  876 */       localProperties.put("user", this.user);
/*  877 */       localProperties.put("password", this.password);
/*  878 */       localProperties.put("connection_pool", "connection_pool");
/*      */ 
/*  880 */       if (getURL() == null) {
/*  881 */         makeURL();
/*      */       }
/*      */ 
/*  888 */       this.m_connection_pool = ((OracleOCIConnection)this.m_oracleDriver.connect(this.url, localProperties));
/*      */ 
/*  891 */       if (this.m_connection_pool == null) {
/*  892 */         DatabaseError.throwSqlException(67);
/*      */       }
/*      */ 
/*  898 */       this.m_poolCreated = true;
/*      */ 
/*  900 */       this.m_connection_pool.setConnectionPool(this);
/*      */ 
/*  903 */       this.m_lconnections.put(this.m_connection_pool, this.m_connection_pool);
/*      */ 
/*  906 */       storePoolProperties();
/*      */ 
/*  911 */       if (paramProperties != null)
/*      */       {
/*  913 */         if (paramProperties.getProperty("transactions_distributed") != null)
/*  914 */           this.m_is_transactions_distributed = 1;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized boolean isPoolCreated()
/*      */   {
/*  930 */     return this.m_poolCreated;
/*      */   }
/*      */ 
/*      */   public synchronized void connectionClosed(OracleOCIConnection paramOracleOCIConnection)
/*      */     throws SQLException
/*      */   {
/*  941 */     if (this.m_lconnections.remove(paramOracleOCIConnection) == null)
/*  942 */       DatabaseError.throwSqlException(1, "internal OracleOCIConnectionPool error");
/*      */   }
/*      */ 
/*      */   public synchronized void setStmtCacheSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  967 */     setStmtCacheSize(paramInt, false);
/*      */   }
/*      */ 
/*      */   public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  993 */     if (paramInt < 0) {
/*  994 */       DatabaseError.throwSqlException(68);
/*      */     }
/*  996 */     this.m_stmtCacheSize = paramInt;
/*  997 */     this.m_stmtClearMetaData = paramBoolean;
/*      */   }
/*      */ 
/*      */   public synchronized int getStmtCacheSize()
/*      */   {
/* 1011 */     return this.m_stmtCacheSize;
/*      */   }
/*      */ 
/*      */   public synchronized boolean isStmtCacheEnabled()
/*      */   {
/* 1025 */     return this.m_stmtCacheSize > 0;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleOCIConnectionPool
 * JD-Core Version:    0.6.0
 */