/*      */ package oracle.jdbc.pool;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.sql.ConnectionEvent;
/*      */ import javax.sql.ConnectionEventListener;
/*      */ import javax.sql.PooledConnection;
/*      */ import javax.transaction.xa.XAResource;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.driver.OracleCloseCallback;
/*      */ import oracle.jdbc.driver.OracleDriver;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ 
/*      */ public class OraclePooledConnection
/*      */   implements PooledConnection, Serializable
/*      */ {
/*      */   public static final String url_string = "connection_url";
/*      */   public static final String pool_auto_commit_string = "pool_auto_commit";
/*      */   public static final String object_type_map = "obj_type_map";
/*      */   public static final String transaction_isolation = "trans_isolation";
/*      */   public static final String statement_cache_size = "stmt_cache_size";
/*      */   public static final String isClearMetaData = "stmt_cache_clear_metadata";
/*      */   public static final String ImplicitStatementCachingEnabled = "ImplicitStatementCachingEnabled";
/*      */   public static final String ExplicitStatementCachingEnabled = "ExplicitStatementCachingEnabled";
/*      */   public static final String LoginTimeout = "LoginTimeout";
/*      */   public static final String connect_auto_commit_string = "connect_auto_commit";
/*      */   public static final String implicit_caching_enabled = "implicit_cache_enabled";
/*      */   public static final String explicit_caching_enabled = "explict_cache_enabled";
/*      */   public static final String connection_properties_string = "connection_properties";
/*      */   public static final String event_listener_string = "event_listener";
/*      */   public static final String sql_exception_string = "sql_exception";
/*      */   public static final String close_callback_string = "close_callback";
/*      */   public static final String private_data = "private_data";
/*   73 */   private Hashtable eventListeners = null;
/*   74 */   private SQLException sqlException = null;
/*   75 */   protected boolean autoCommit = true;
/*      */ 
/*   79 */   private ConnectionEventListener iccEventListener = null;
/*      */ 
/*   82 */   protected transient OracleConnection logicalHandle = null;
/*      */ 
/*   85 */   protected transient OracleConnection physicalConn = null;
/*      */ 
/*   87 */   private Hashtable connectionProperty = null;
/*      */ 
/*   89 */   public Properties cachedConnectionAttributes = null;
/*   90 */   public Properties unMatchedCachedConnAttr = null;
/*   91 */   public int closeOption = 0;
/*      */ 
/*   93 */   private String pcKey = null;
/*      */ 
/*   96 */   private OracleCloseCallback closeCallback = null;
/*   97 */   private Object privateData = null;
/*      */ 
/*  100 */   private long lastAccessedTime = 0L;
/*      */ 
/*  103 */   protected String dataSourceInstanceNameKey = null;
/*  104 */   protected String dataSourceHostNameKey = null;
/*  105 */   protected String dataSourceDbUniqNameKey = null;
/*  106 */   protected boolean connectionMarkedDown = false;
/*  107 */   protected boolean needToAbort = false;
/*      */ 
/*  109 */   protected transient OracleDriver oracleDriver = new OracleDriver();
/*      */ 
/* 1031 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*      */ 
/*      */   public OraclePooledConnection()
/*      */   {
/*  114 */     this((Connection)null);
/*      */   }
/*      */ 
/*      */   public OraclePooledConnection(String paramString)
/*      */     throws SQLException
/*      */   {
/*  131 */     Connection localConnection = this.oracleDriver.connect(paramString, new Properties());
/*      */ 
/*  133 */     if (localConnection == null) {
/*  134 */       DatabaseError.throwSqlException(67);
/*      */     }
/*  136 */     initialize(localConnection);
/*      */   }
/*      */ 
/*      */   public OraclePooledConnection(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/*  153 */     Properties localProperties = new Properties();
/*      */ 
/*  155 */     localProperties.put("user", paramString2);
/*  156 */     localProperties.put("password", paramString3);
/*      */ 
/*  158 */     Connection localConnection = this.oracleDriver.connect(paramString1, localProperties);
/*      */ 
/*  160 */     if (localConnection == null) {
/*  161 */       DatabaseError.throwSqlException(67);
/*      */     }
/*  163 */     initialize(localConnection);
/*      */   }
/*      */ 
/*      */   public OraclePooledConnection(Connection paramConnection)
/*      */   {
/*  177 */     initialize(paramConnection);
/*      */   }
/*      */ 
/*      */   public OraclePooledConnection(Connection paramConnection, boolean paramBoolean)
/*      */   {
/*  188 */     this(paramConnection);
/*      */ 
/*  193 */     this.autoCommit = paramBoolean;
/*      */   }
/*      */ 
/*      */   private void initialize(Connection paramConnection)
/*      */   {
/*  203 */     this.physicalConn = ((OracleConnection)paramConnection);
/*  204 */     this.eventListeners = new Hashtable(10);
/*      */ 
/*  206 */     this.closeCallback = null;
/*  207 */     this.privateData = null;
/*  208 */     this.lastAccessedTime = 0L;
/*      */   }
/*      */ 
/*      */   public synchronized void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
/*      */   {
/*  223 */     if (this.eventListeners == null)
/*  224 */       this.sqlException = new SQLException("Listener Hashtable Null");
/*      */     else
/*  226 */       this.eventListeners.put(paramConnectionEventListener, paramConnectionEventListener);
/*      */   }
/*      */ 
/*      */   public synchronized void close()
/*      */     throws SQLException
/*      */   {
/*  240 */     if (this.closeCallback != null) {
/*  241 */       this.closeCallback.beforeClose(this.physicalConn, this.privateData);
/*      */     }
/*  243 */     if (this.physicalConn != null)
/*      */     {
/*      */       try
/*      */       {
/*  247 */         this.physicalConn.close();
/*      */       }
/*      */       catch (SQLException localSQLException)
/*      */       {
/*      */       }
/*      */ 
/*  254 */       this.physicalConn = null;
/*      */     }
/*      */ 
/*  257 */     if (this.closeCallback != null) {
/*  258 */       this.closeCallback.afterClose(this.privateData);
/*      */     }
/*      */ 
/*  261 */     this.lastAccessedTime = 0L;
/*      */ 
/*  263 */     this.iccEventListener = null;
/*      */ 
/*  266 */     callListener(2);
/*      */   }
/*      */ 
/*      */   public synchronized Connection getConnection()
/*      */     throws SQLException
/*      */   {
/*  285 */     if (this.physicalConn == null)
/*      */     {
/*  287 */       this.sqlException = new SQLException("Physical Connection doesn't exis");
/*      */ 
/*  290 */       callListener(2);
/*      */ 
/*  292 */       DatabaseError.throwSqlException(8);
/*      */ 
/*  294 */       return null;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  301 */       if (this.logicalHandle != null)
/*      */       {
/*  306 */         this.logicalHandle.closeInternal(false);
/*      */       }
/*      */ 
/*  310 */       this.logicalHandle = ((OracleConnection)this.physicalConn.getLogicalConnection(this, this.autoCommit));
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  316 */       this.sqlException = localSQLException;
/*      */ 
/*  319 */       callListener(2);
/*  320 */       callImplicitCacheListener(102);
/*      */ 
/*  325 */       DatabaseError.throwSqlException(8);
/*      */ 
/*  327 */       return null;
/*      */     }
/*      */ 
/*  333 */     return this.logicalHandle;
/*      */   }
/*      */ 
/*      */   public Connection getLogicalHandle()
/*      */     throws SQLException
/*      */   {
/*  345 */     return this.logicalHandle;
/*      */   }
/*      */ 
/*      */   public Connection getPhysicalHandle() throws SQLException
/*      */   {
/*  350 */     return this.physicalConn;
/*      */   }
/*      */ 
/*      */   public synchronized void setLastAccessedTime(long paramLong)
/*      */     throws SQLException
/*      */   {
/*  368 */     this.lastAccessedTime = paramLong;
/*      */   }
/*      */ 
/*      */   public long getLastAccessedTime()
/*      */     throws SQLException
/*      */   {
/*  384 */     return this.lastAccessedTime;
/*      */   }
/*      */ 
/*      */   public synchronized void registerCloseCallback(OracleCloseCallback paramOracleCloseCallback, Object paramObject)
/*      */   {
/*  401 */     this.closeCallback = paramOracleCloseCallback;
/*  402 */     this.privateData = paramObject;
/*      */   }
/*      */ 
/*      */   public synchronized void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
/*      */   {
/*  416 */     if (this.eventListeners == null)
/*  417 */       this.sqlException = new SQLException("Listener Hashtable Null");
/*      */     else
/*  419 */       this.eventListeners.remove(paramConnectionEventListener);
/*      */   }
/*      */ 
/*      */   public synchronized void registerImplicitCacheConnectionEventListener(ConnectionEventListener paramConnectionEventListener)
/*      */   {
/*  433 */     if (this.iccEventListener != null) {
/*  434 */       this.sqlException = new SQLException("Implicit cache listener already registered");
/*      */     }
/*      */     else
/*  437 */       this.iccEventListener = paramConnectionEventListener;
/*      */   }
/*      */ 
/*      */   public void logicalCloseForImplicitConnectionCache()
/*      */   {
/*  459 */     if (this.closeOption == 4096)
/*      */     {
/*  461 */       callImplicitCacheListener(102);
/*      */     }
/*      */     else
/*      */     {
/*  465 */       callImplicitCacheListener(101);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void logicalClose()
/*      */   {
/*  479 */     if (this.cachedConnectionAttributes != null)
/*      */     {
/*  481 */       logicalCloseForImplicitConnectionCache();
/*      */     }
/*      */     else
/*      */     {
/*  487 */       synchronized (this)
/*      */       {
/*  491 */         callListener(1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void callListener(int paramInt)
/*      */   {
/*  506 */     if (this.eventListeners == null) {
/*  507 */       return;
/*      */     }
/*      */ 
/*  511 */     Enumeration localEnumeration = this.eventListeners.keys();
/*      */ 
/*  513 */     ConnectionEvent localConnectionEvent = new ConnectionEvent(this, this.sqlException);
/*      */ 
/*  515 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*  517 */       ConnectionEventListener localConnectionEventListener1 = (ConnectionEventListener)localEnumeration.nextElement();
/*      */ 
/*  519 */       ConnectionEventListener localConnectionEventListener2 = (ConnectionEventListener)this.eventListeners.get(localConnectionEventListener1);
/*      */ 
/*  523 */       if (paramInt == 1) {
/*  524 */         localConnectionEventListener2.connectionClosed(localConnectionEvent); continue;
/*  525 */       }if (paramInt == 2)
/*  526 */         localConnectionEventListener2.connectionErrorOccurred(localConnectionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void callImplicitCacheListener(int paramInt)
/*      */   {
/*  539 */     if (this.iccEventListener == null) {
/*  540 */       return;
/*      */     }
/*  542 */     ConnectionEvent localConnectionEvent = new ConnectionEvent(this, this.sqlException);
/*      */ 
/*  545 */     switch (paramInt)
/*      */     {
/*      */     case 101:
/*  550 */       this.iccEventListener.connectionClosed(localConnectionEvent);
/*      */ 
/*  552 */       break;
/*      */     case 102:
/*  557 */       this.iccEventListener.connectionErrorOccurred(localConnectionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized void setStmtCacheSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  587 */     setStmtCacheSize(paramInt, false);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  614 */     if (paramInt < 0) {
/*  615 */       DatabaseError.throwSqlException(68);
/*      */     }
/*      */ 
/*  619 */     if (this.physicalConn != null)
/*  620 */       this.physicalConn.setStmtCacheSize(paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized int getStmtCacheSize()
/*      */   {
/*  635 */     if (this.physicalConn != null) {
/*  636 */       return this.physicalConn.getStmtCacheSize();
/*      */     }
/*  638 */     return 0;
/*      */   }
/*      */ 
/*      */   public void setStatementCacheSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  658 */     if (this.physicalConn != null)
/*  659 */       this.physicalConn.setStatementCacheSize(paramInt);
/*      */   }
/*      */ 
/*      */   public int getStatementCacheSize()
/*      */     throws SQLException
/*      */   {
/*  675 */     if (this.physicalConn != null) {
/*  676 */       return this.physicalConn.getStatementCacheSize();
/*      */     }
/*  678 */     return 0;
/*      */   }
/*      */ 
/*      */   public void setImplicitCachingEnabled(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  698 */     if (this.physicalConn != null)
/*  699 */       this.physicalConn.setImplicitCachingEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */   public boolean getImplicitCachingEnabled()
/*      */     throws SQLException
/*      */   {
/*  714 */     if (this.physicalConn != null) {
/*  715 */       return this.physicalConn.getImplicitCachingEnabled();
/*      */     }
/*  717 */     return false;
/*      */   }
/*      */ 
/*      */   public void setExplicitCachingEnabled(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  737 */     if (this.physicalConn != null)
/*  738 */       this.physicalConn.setExplicitCachingEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */   public boolean getExplicitCachingEnabled()
/*      */     throws SQLException
/*      */   {
/*  753 */     if (this.physicalConn != null) {
/*  754 */       return this.physicalConn.getExplicitCachingEnabled();
/*      */     }
/*  756 */     return false;
/*      */   }
/*      */ 
/*      */   public void purgeImplicitCache()
/*      */     throws SQLException
/*      */   {
/*  772 */     if (this.physicalConn != null)
/*  773 */       this.physicalConn.purgeImplicitCache();
/*      */   }
/*      */ 
/*      */   public void purgeExplicitCache()
/*      */     throws SQLException
/*      */   {
/*  789 */     if (this.physicalConn != null)
/*  790 */       this.physicalConn.purgeExplicitCache();
/*      */   }
/*      */ 
/*      */   public PreparedStatement getStatementWithKey(String paramString)
/*      */     throws SQLException
/*      */   {
/*  810 */     if (this.physicalConn != null) {
/*  811 */       return this.physicalConn.getStatementWithKey(paramString);
/*      */     }
/*  813 */     return null;
/*      */   }
/*      */ 
/*      */   public CallableStatement getCallWithKey(String paramString)
/*      */     throws SQLException
/*      */   {
/*  833 */     if (this.physicalConn != null) {
/*  834 */       return this.physicalConn.getCallWithKey(paramString);
/*      */     }
/*  836 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean isStatementCacheInitialized()
/*      */   {
/*  846 */     if (this.physicalConn != null) {
/*  847 */       return this.physicalConn.isStatementCacheInitialized();
/*      */     }
/*  849 */     return false;
/*      */   }
/*      */ 
/*      */   public final void setProperties(Hashtable paramHashtable)
/*      */   {
/*  854 */     this.connectionProperty = paramHashtable;
/*      */   }
/*      */ 
/*      */   public final void setUserName(String paramString1, String paramString2)
/*      */   {
/*  862 */     this.pcKey = (paramString1 + paramString2);
/*      */   }
/*      */ 
/*      */   final OracleConnectionCacheEntry addToImplicitCache(HashMap paramHashMap, OracleConnectionCacheEntry paramOracleConnectionCacheEntry)
/*      */   {
/*  871 */     return (OracleConnectionCacheEntry)paramHashMap.put(this.pcKey, paramOracleConnectionCacheEntry);
/*      */   }
/*      */ 
/*      */   final OracleConnectionCacheEntry removeFromImplictCache(HashMap paramHashMap)
/*      */   {
/*  879 */     return (OracleConnectionCacheEntry)paramHashMap.get(this.pcKey);
/*      */   }
/*      */ 
/*      */   final boolean isSameUser(String paramString1, String paramString2)
/*      */   {
/*  887 */     return (paramString1 != null) && (paramString2 != null) && (this.pcKey.equalsIgnoreCase(paramString1 + paramString2.toUpperCase()));
/*      */   }
/*      */ 
/*      */   public XAResource getXAResource()
/*      */     throws SQLException
/*      */   {
/*  903 */     DatabaseError.throwSqlException(23);
/*      */ 
/*  905 */     return null;
/*      */   }
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*  915 */     paramObjectOutputStream.defaultWriteObject();
/*      */     try
/*      */     {
/*  924 */       this.physicalConn.getPropertyForPooledConnection(this);
/*      */ 
/*  926 */       if (this.eventListeners != null) {
/*  927 */         this.connectionProperty.put("event_listener", this.eventListeners);
/*      */       }
/*  929 */       if (this.sqlException != null) {
/*  930 */         this.connectionProperty.put("sql_exception", this.sqlException);
/*      */       }
/*  932 */       this.connectionProperty.put("pool_auto_commit", "" + this.autoCommit);
/*      */ 
/*  934 */       if (this.closeCallback != null) {
/*  935 */         this.connectionProperty.put("close_callback", this.closeCallback);
/*      */       }
/*  937 */       if (this.privateData != null) {
/*  938 */         this.connectionProperty.put("private_data", this.privateData);
/*      */       }
/*  940 */       paramObjectOutputStream.writeObject(this.connectionProperty);
/*  941 */       this.physicalConn.close();
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  945 */       localSQLException.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException, SQLException
/*      */   {
/*  957 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*  959 */     this.connectionProperty = ((Hashtable)paramObjectInputStream.readObject());
/*      */     try
/*      */     {
/*  966 */       Properties localProperties = (Properties)this.connectionProperty.get("connection_properties");
/*      */ 
/*  968 */       String str1 = localProperties.getProperty("connection_url");
/*      */ 
/*  970 */       this.oracleDriver = new OracleDriver();
/*      */ 
/*  972 */       Connection localConnection = this.oracleDriver.connect(str1, localProperties);
/*      */ 
/*  976 */       initialize(localConnection);
/*      */ 
/*  978 */       this.eventListeners = ((Hashtable)this.connectionProperty.get("event_listener"));
/*      */ 
/*  980 */       this.sqlException = ((SQLException)this.connectionProperty.get("sql_exception"));
/*      */ 
/*  982 */       this.autoCommit = ((String)this.connectionProperty.get("pool_auto_commit")).equals("true");
/*      */ 
/*  984 */       this.closeCallback = ((OracleCloseCallback)this.connectionProperty.get("close_callback"));
/*      */ 
/*  986 */       this.privateData = this.connectionProperty.get("private_data");
/*      */ 
/*  988 */       Map localMap = (Map)this.connectionProperty.get("obj_type_map");
/*      */ 
/*  991 */       if (localMap != null) {
/*  992 */         ((OracleConnection)localConnection).setTypeMap(localMap);
/*      */       }
/*  994 */       String str2 = localProperties.getProperty("trans_isolation");
/*      */ 
/*  996 */       localConnection.setTransactionIsolation(Integer.parseInt(str2));
/*      */ 
/*  998 */       str2 = localProperties.getProperty("stmt_cache_size");
/*      */ 
/* 1000 */       int i = Integer.parseInt(str2);
/*      */ 
/* 1002 */       if (i != -1)
/*      */       {
/* 1004 */         setStatementCacheSize(i);
/*      */ 
/* 1006 */         str2 = localProperties.getProperty("implicit_cache_enabled");
/* 1007 */         if ((str2 != null) && (str2.equalsIgnoreCase("true")))
/* 1008 */           setImplicitCachingEnabled(true);
/*      */         else {
/* 1010 */           setImplicitCachingEnabled(false);
/*      */         }
/* 1012 */         str2 = localProperties.getProperty("explict_cache_enabled");
/* 1013 */         if ((str2 != null) && (str2.equalsIgnoreCase("true")))
/* 1014 */           setExplicitCachingEnabled(true);
/*      */         else
/* 1016 */           setExplicitCachingEnabled(false);
/*      */       }
/* 1018 */       this.physicalConn.setAutoCommit(((String)localProperties.get("connect_auto_commit")).equals("true"));
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1022 */       localException.printStackTrace();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OraclePooledConnection
 * JD-Core Version:    0.6.0
 */