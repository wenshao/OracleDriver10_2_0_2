/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import oracle.jdbc.internal.OracleStatement;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.jdbc.oracore.OracleTypeCLOB;
/*     */ import oracle.jdbc.pool.OracleConnectionCacheCallback;
/*     */ import oracle.jdbc.pool.OraclePooledConnection;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.ArrayDescriptor;
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.BfileDBAccess;
/*     */ import oracle.sql.BlobDBAccess;
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.ClobDBAccess;
/*     */ import oracle.sql.CustomDatum;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.StructDescriptor;
/*     */ 
/*     */ class LogicalConnection extends OracleConnection
/*     */ {
/*  53 */   static final ClosedConnection closedConnection = new ClosedConnection();
/*     */   PhysicalConnection internalConnection;
/*     */   OraclePooledConnection pooledConnection;
/*     */   boolean closed;
/*  63 */   OracleCloseCallback closeCallback = null;
/*  64 */   Object privateData = null;
/*     */ 
/*  67 */   long startTime = 0L;
/*     */ 
/*  71 */   OracleConnectionCacheCallback connectionCacheCallback = null;
/*  72 */   Object connectionCacheCallbackUserObj = null;
/*  73 */   int callbackFlag = 0;
/*  74 */   int releasePriority = 0;
/*     */ 
/*  77 */   int heartbeatCount = 0;
/*  78 */   int heartbeatLastCount = 0;
/*  79 */   int heartbeatNoChangeCount = 0;
/*  80 */   boolean isAbandonedTimeoutEnabled = false;
/*     */ 
/* 960 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   LogicalConnection(OraclePooledConnection paramOraclePooledConnection, PhysicalConnection paramPhysicalConnection, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  90 */     this.internalConnection = paramPhysicalConnection;
/*  91 */     this.pooledConnection = paramOraclePooledConnection;
/*  92 */     this.connection = this.internalConnection;
/*     */ 
/*  94 */     this.connection.setWrapper(this);
/*     */ 
/*  96 */     this.closed = false;
/*     */ 
/*  98 */     this.internalConnection.setAutoCommit(paramBoolean);
/*     */   }
/*     */ 
/*     */   void registerHeartbeat()
/*     */     throws SQLException
/*     */   {
/* 117 */     if (this.isAbandonedTimeoutEnabled)
/*     */     {
/*     */       try
/*     */       {
/* 121 */         this.heartbeatCount += 1;
/*     */       }
/*     */       catch (ArithmeticException localArithmeticException)
/*     */       {
/* 125 */         this.heartbeatCount = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getHeartbeatNoChangeCount()
/*     */     throws SQLException
/*     */   {
/* 139 */     if (this.heartbeatCount == this.heartbeatLastCount)
/*     */     {
/* 141 */       this.heartbeatNoChangeCount += 1;
/*     */     }
/*     */     else
/*     */     {
/* 145 */       this.heartbeatLastCount = this.heartbeatCount;
/* 146 */       this.heartbeatNoChangeCount = 0;
/*     */     }
/*     */ 
/* 149 */     return this.heartbeatNoChangeCount;
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.internal.OracleConnection physicalConnectionWithin()
/*     */   {
/* 154 */     return this.internalConnection;
/*     */   }
/*     */ 
/*     */   public synchronized void registerCloseCallback(OracleCloseCallback paramOracleCloseCallback, Object paramObject)
/*     */   {
/* 160 */     this.closeCallback = paramOracleCloseCallback;
/* 161 */     this.privateData = paramObject;
/*     */   }
/*     */ 
/*     */   public Connection _getPC()
/*     */   {
/* 166 */     return this.internalConnection;
/*     */   }
/*     */ 
/*     */   public synchronized boolean isLogicalConnection()
/*     */   {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.internal.OracleConnection getPhysicalConnection()
/*     */   {
/* 182 */     return this.internalConnection;
/*     */   }
/*     */ 
/*     */   public Connection getLogicalConnection(OraclePooledConnection paramOraclePooledConnection, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 192 */     DatabaseError.throwSqlException(153);
/*     */ 
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
/*     */     throws SQLException
/*     */   {
/* 203 */     DatabaseError.throwSqlException(153);
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */     throws SQLException
/*     */   {
/* 214 */     closeInternal(true);
/*     */   }
/*     */ 
/*     */   public void closeInternal(boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 226 */     if (this.closed) {
/* 227 */       return;
/*     */     }
/* 229 */     if (this.closeCallback != null) {
/* 230 */       this.closeCallback.beforeClose(this, this.privateData);
/*     */     }
/*     */ 
/* 234 */     this.internalConnection.closeLogicalConnection();
/*     */ 
/* 237 */     this.startTime = 0L;
/*     */ 
/* 239 */     this.closed = true;
/*     */ 
/* 242 */     if ((this.pooledConnection != null) && (paramBoolean)) {
/* 243 */       this.pooledConnection.logicalClose();
/*     */     }
/*     */ 
/* 247 */     this.internalConnection = closedConnection;
/* 248 */     this.connection = closedConnection;
/*     */ 
/* 250 */     if (this.closeCallback != null)
/* 251 */       this.closeCallback.afterClose(this.privateData);
/*     */   }
/*     */ 
/*     */   public void abort() throws SQLException
/*     */   {
/* 256 */     if (this.closed)
/* 257 */       return;
/* 258 */     this.internalConnection.abort();
/* 259 */     this.closed = true;
/*     */ 
/* 261 */     this.internalConnection = closedConnection;
/* 262 */     this.connection = closedConnection;
/*     */   }
/*     */ 
/*     */   public synchronized void close(Properties paramProperties)
/*     */     throws SQLException
/*     */   {
/* 277 */     if (this.pooledConnection != null)
/*     */     {
/* 279 */       this.pooledConnection.cachedConnectionAttributes.clear();
/* 280 */       this.pooledConnection.cachedConnectionAttributes.putAll(paramProperties);
/*     */     }
/*     */ 
/* 283 */     close();
/*     */   }
/*     */ 
/*     */   public synchronized void close(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 292 */     if ((paramInt & 0x1000) != 0)
/*     */     {
/* 296 */       if (this.pooledConnection != null) {
/* 297 */         this.pooledConnection.closeOption = paramInt;
/*     */       }
/* 299 */       close();
/*     */ 
/* 301 */       return;
/*     */     }
/*     */ 
/* 304 */     if ((paramInt & 0x1) != 0)
/*     */     {
/* 308 */       this.internalConnection.close(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void applyConnectionAttributes(Properties paramProperties)
/*     */     throws SQLException
/*     */   {
/* 324 */     if (this.pooledConnection != null)
/* 325 */       this.pooledConnection.cachedConnectionAttributes.putAll(paramProperties);
/*     */   }
/*     */ 
/*     */   public synchronized Properties getConnectionAttributes()
/*     */     throws SQLException
/*     */   {
/* 341 */     if (this.pooledConnection != null) {
/* 342 */       return this.pooledConnection.cachedConnectionAttributes;
/*     */     }
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */   public synchronized Properties getUnMatchedConnectionAttributes()
/*     */     throws SQLException
/*     */   {
/* 358 */     if (this.pooledConnection != null) {
/* 359 */       return this.pooledConnection.unMatchedCachedConnAttr;
/*     */     }
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   public synchronized void setAbandonedTimeoutEnabled(boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 371 */     this.isAbandonedTimeoutEnabled = true;
/*     */   }
/*     */ 
/*     */   public synchronized void registerConnectionCacheCallback(OracleConnectionCacheCallback paramOracleConnectionCacheCallback, Object paramObject, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 384 */     this.connectionCacheCallback = paramOracleConnectionCacheCallback;
/* 385 */     this.connectionCacheCallbackUserObj = paramObject;
/* 386 */     this.callbackFlag = paramInt;
/*     */   }
/*     */ 
/*     */   public OracleConnectionCacheCallback getConnectionCacheCallbackObj()
/*     */     throws SQLException
/*     */   {
/* 395 */     return this.connectionCacheCallback;
/*     */   }
/*     */ 
/*     */   public Object getConnectionCacheCallbackPrivObj()
/*     */     throws SQLException
/*     */   {
/* 403 */     return this.connectionCacheCallbackUserObj;
/*     */   }
/*     */ 
/*     */   public int getConnectionCacheCallbackFlag()
/*     */     throws SQLException
/*     */   {
/* 411 */     return this.callbackFlag;
/*     */   }
/*     */ 
/*     */   public synchronized void setConnectionReleasePriority(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 423 */     this.releasePriority = paramInt;
/*     */   }
/*     */ 
/*     */   public int getConnectionReleasePriority()
/*     */     throws SQLException
/*     */   {
/* 435 */     return this.releasePriority;
/*     */   }
/*     */ 
/*     */   public synchronized boolean isClosed()
/*     */     throws SQLException
/*     */   {
/* 449 */     return this.closed;
/*     */   }
/*     */ 
/*     */   public void setStartTime(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 469 */     if (paramLong <= 0L)
/*     */     {
/* 471 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */     else
/*     */     {
/* 475 */       this.startTime = paramLong;
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getStartTime()
/*     */     throws SQLException
/*     */   {
/* 492 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public Properties getServerSessionInfo()
/*     */     throws SQLException
/*     */   {
/* 500 */     return this.internalConnection.getServerSessionInfo();
/*     */   }
/*     */ 
/*     */   public Object getClientData(Object paramObject)
/*     */   {
/* 505 */     return this.internalConnection.getClientData(paramObject);
/*     */   }
/*     */ 
/*     */   public Object setClientData(Object paramObject1, Object paramObject2)
/*     */   {
/* 510 */     return this.internalConnection.setClientData(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   public Object removeClientData(Object paramObject)
/*     */   {
/* 515 */     return this.internalConnection.removeClientData(paramObject);
/*     */   }
/*     */ 
/*     */   public void setClientIdentifier(String paramString) throws SQLException
/*     */   {
/* 520 */     this.internalConnection.setClientIdentifier(paramString);
/*     */   }
/*     */ 
/*     */   public void clearClientIdentifier(String paramString) throws SQLException
/*     */   {
/* 525 */     this.internalConnection.clearClientIdentifier(paramString);
/*     */   }
/*     */ 
/*     */   public short getStructAttrNCsId() throws SQLException
/*     */   {
/* 530 */     return this.internalConnection.getStructAttrNCsId();
/*     */   }
/*     */ 
/*     */   public Map getTypeMap() throws SQLException
/*     */   {
/* 535 */     return this.internalConnection.getTypeMap();
/*     */   }
/*     */ 
/*     */   public Properties getDBAccessProperties() throws SQLException
/*     */   {
/* 540 */     return this.internalConnection.getDBAccessProperties();
/*     */   }
/*     */ 
/*     */   public Properties getOCIHandles() throws SQLException
/*     */   {
/* 545 */     return this.internalConnection.getOCIHandles();
/*     */   }
/*     */ 
/*     */   public String getDatabaseProductVersion() throws SQLException
/*     */   {
/* 550 */     return this.internalConnection.getDatabaseProductVersion();
/*     */   }
/*     */ 
/*     */   public void cancel() throws SQLException
/*     */   {
/* 555 */     this.internalConnection.cancel();
/*     */   }
/*     */ 
/*     */   public String getURL() throws SQLException
/*     */   {
/* 560 */     return this.internalConnection.getURL();
/*     */   }
/*     */ 
/*     */   public boolean getIncludeSynonyms()
/*     */   {
/* 565 */     return this.internalConnection.getIncludeSynonyms();
/*     */   }
/*     */ 
/*     */   public boolean getRemarksReporting()
/*     */   {
/* 570 */     return this.internalConnection.getRemarksReporting();
/*     */   }
/*     */ 
/*     */   public boolean getRestrictGetTables()
/*     */   {
/* 575 */     return this.internalConnection.getRestrictGetTables();
/*     */   }
/*     */ 
/*     */   public short getVersionNumber() throws SQLException
/*     */   {
/* 580 */     return this.internalConnection.getVersionNumber();
/*     */   }
/*     */ 
/*     */   public Map getJavaObjectTypeMap()
/*     */   {
/* 585 */     return this.internalConnection.getJavaObjectTypeMap();
/*     */   }
/*     */ 
/*     */   public void setJavaObjectTypeMap(Map paramMap)
/*     */   {
/* 590 */     this.internalConnection.setJavaObjectTypeMap(paramMap);
/*     */   }
/*     */ 
/*     */   public BfileDBAccess createBfileDBAccess() throws SQLException
/*     */   {
/* 595 */     return this.internalConnection.createBfileDBAccess();
/*     */   }
/*     */ 
/*     */   public BlobDBAccess createBlobDBAccess() throws SQLException
/*     */   {
/* 600 */     return this.internalConnection.createBlobDBAccess();
/*     */   }
/*     */ 
/*     */   public ClobDBAccess createClobDBAccess() throws SQLException
/*     */   {
/* 605 */     return this.internalConnection.createClobDBAccess();
/*     */   }
/*     */ 
/*     */   public void setDefaultFixedString(boolean paramBoolean)
/*     */   {
/* 610 */     this.internalConnection.setDefaultFixedString(paramBoolean);
/*     */   }
/*     */ 
/*     */   public boolean getDefaultFixedString()
/*     */   {
/* 615 */     return this.internalConnection.getDefaultFixedString();
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.OracleConnection getWrapper()
/*     */   {
/* 620 */     return this;
/*     */   }
/*     */ 
/*     */   public Class classForNameAndSchema(String paramString1, String paramString2)
/*     */     throws ClassNotFoundException
/*     */   {
/* 626 */     return this.internalConnection.classForNameAndSchema(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   public void setFDO(byte[] paramArrayOfByte) throws SQLException
/*     */   {
/* 631 */     this.internalConnection.setFDO(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public byte[] getFDO(boolean paramBoolean) throws SQLException
/*     */   {
/* 636 */     return this.internalConnection.getFDO(paramBoolean);
/*     */   }
/*     */ 
/*     */   public boolean getBigEndian() throws SQLException
/*     */   {
/* 641 */     return this.internalConnection.getBigEndian();
/*     */   }
/*     */ 
/*     */   public Object getDescriptor(byte[] paramArrayOfByte)
/*     */   {
/* 646 */     return this.internalConnection.getDescriptor(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public void putDescriptor(byte[] paramArrayOfByte, Object paramObject) throws SQLException
/*     */   {
/* 651 */     this.internalConnection.putDescriptor(paramArrayOfByte, paramObject);
/*     */   }
/*     */ 
/*     */   public void removeDescriptor(String paramString)
/*     */   {
/* 656 */     this.internalConnection.removeDescriptor(paramString);
/*     */   }
/*     */ 
/*     */   public void removeAllDescriptor()
/*     */   {
/* 661 */     this.internalConnection.removeAllDescriptor();
/*     */   }
/*     */ 
/*     */   public int numberOfDescriptorCacheEntries()
/*     */   {
/* 666 */     return this.internalConnection.numberOfDescriptorCacheEntries();
/*     */   }
/*     */ 
/*     */   public Enumeration descriptorCacheKeys()
/*     */   {
/* 671 */     return this.internalConnection.descriptorCacheKeys();
/*     */   }
/*     */ 
/*     */   public void getOracleTypeADT(OracleTypeADT paramOracleTypeADT)
/*     */     throws SQLException
/*     */   {
/* 677 */     this.internalConnection.getOracleTypeADT(paramOracleTypeADT);
/*     */   }
/*     */ 
/*     */   public short getDbCsId() throws SQLException
/*     */   {
/* 682 */     return this.internalConnection.getDbCsId();
/*     */   }
/*     */ 
/*     */   public short getJdbcCsId() throws SQLException
/*     */   {
/* 687 */     return this.internalConnection.getJdbcCsId();
/*     */   }
/*     */ 
/*     */   public short getNCharSet()
/*     */   {
/* 692 */     return this.internalConnection.getNCharSet();
/*     */   }
/*     */ 
/*     */   public ResultSet newArrayDataResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 698 */     return this.internalConnection.newArrayDataResultSet(paramArrayOfDatum, paramLong, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   public ResultSet newArrayDataResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 704 */     return this.internalConnection.newArrayDataResultSet(paramARRAY, paramLong, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   public ResultSet newArrayLocatorResultSet(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 712 */     return this.internalConnection.newArrayLocatorResultSet(paramArrayDescriptor, paramArrayOfByte, paramLong, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   public ResultSetMetaData newStructMetaData(StructDescriptor paramStructDescriptor)
/*     */     throws SQLException
/*     */   {
/* 719 */     return this.internalConnection.newStructMetaData(paramStructDescriptor);
/*     */   }
/*     */ 
/*     */   public void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 726 */     this.internalConnection.getForm(paramOracleTypeADT, paramOracleTypeCLOB, paramInt);
/*     */   }
/*     */ 
/*     */   public int CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
/*     */     throws SQLException
/*     */   {
/* 732 */     return this.internalConnection.CHARBytesToJavaChars(paramArrayOfByte, paramInt, paramArrayOfChar);
/*     */   }
/*     */ 
/*     */   public int NCHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
/*     */     throws SQLException
/*     */   {
/* 738 */     return this.internalConnection.NCHARBytesToJavaChars(paramArrayOfByte, paramInt, paramArrayOfChar);
/*     */   }
/*     */ 
/*     */   public boolean IsNCharFixedWith()
/*     */   {
/* 743 */     return this.internalConnection.IsNCharFixedWith();
/*     */   }
/*     */ 
/*     */   public short getDriverCharSet()
/*     */   {
/* 748 */     return this.internalConnection.getDriverCharSet();
/*     */   }
/*     */ 
/*     */   public int getC2SNlsRatio()
/*     */   {
/* 753 */     return this.internalConnection.getC2SNlsRatio();
/*     */   }
/*     */ 
/*     */   public int getMaxCharSize() throws SQLException
/*     */   {
/* 758 */     return this.internalConnection.getMaxCharSize();
/*     */   }
/*     */ 
/*     */   public int getMaxCharbyteSize()
/*     */   {
/* 763 */     return this.internalConnection.getMaxCharbyteSize();
/*     */   }
/*     */ 
/*     */   public int getMaxNCharbyteSize()
/*     */   {
/* 768 */     return this.internalConnection.getMaxNCharbyteSize();
/*     */   }
/*     */ 
/*     */   public boolean isCharSetMultibyte(short paramShort)
/*     */   {
/* 773 */     return this.internalConnection.isCharSetMultibyte(paramShort);
/*     */   }
/*     */ 
/*     */   public int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 779 */     return this.internalConnection.javaCharsToCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 785 */     return this.internalConnection.javaCharsToNCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public int getStmtCacheSize()
/*     */   {
/* 790 */     return this.internalConnection.getStmtCacheSize();
/*     */   }
/*     */ 
/*     */   public int getStatementCacheSize() throws SQLException
/*     */   {
/* 795 */     return this.internalConnection.getStatementCacheSize();
/*     */   }
/*     */ 
/*     */   public boolean getImplicitCachingEnabled() throws SQLException
/*     */   {
/* 800 */     return this.internalConnection.getImplicitCachingEnabled();
/*     */   }
/*     */ 
/*     */   public boolean getExplicitCachingEnabled() throws SQLException
/*     */   {
/* 805 */     return this.internalConnection.getExplicitCachingEnabled();
/*     */   }
/*     */ 
/*     */   public void purgeImplicitCache() throws SQLException
/*     */   {
/* 810 */     this.internalConnection.purgeImplicitCache();
/*     */   }
/*     */ 
/*     */   public void purgeExplicitCache() throws SQLException
/*     */   {
/* 815 */     this.internalConnection.purgeExplicitCache();
/*     */   }
/*     */ 
/*     */   public PreparedStatement getStatementWithKey(String paramString)
/*     */     throws SQLException
/*     */   {
/* 821 */     return this.internalConnection.getStatementWithKey(paramString);
/*     */   }
/*     */ 
/*     */   public CallableStatement getCallWithKey(String paramString)
/*     */     throws SQLException
/*     */   {
/* 827 */     return this.internalConnection.getCallWithKey(paramString);
/*     */   }
/*     */ 
/*     */   public boolean isStatementCacheInitialized()
/*     */   {
/* 832 */     return this.internalConnection.isStatementCacheInitialized();
/*     */   }
/*     */ 
/*     */   public void setTypeMap(Map paramMap)
/*     */   {
/* 837 */     this.internalConnection.setTypeMap(paramMap);
/*     */   }
/*     */ 
/*     */   public String getProtocolType()
/*     */   {
/* 842 */     return this.internalConnection.getProtocolType();
/*     */   }
/*     */ 
/*     */   public void setTxnMode(int paramInt)
/*     */   {
/* 849 */     this.internalConnection.setTxnMode(paramInt);
/*     */   }
/*     */ 
/*     */   public int getTxnMode()
/*     */   {
/* 854 */     return this.internalConnection.getTxnMode();
/*     */   }
/*     */ 
/*     */   public int getHeapAllocSize()
/*     */     throws SQLException
/*     */   {
/* 860 */     return this.internalConnection.getHeapAllocSize();
/*     */   }
/*     */ 
/*     */   public int getOCIEnvHeapAllocSize() throws SQLException
/*     */   {
/* 865 */     return this.internalConnection.getOCIEnvHeapAllocSize();
/*     */   }
/*     */ 
/*     */   public CLOB createClob(byte[] paramArrayOfByte) throws SQLException
/*     */   {
/* 870 */     return this.internalConnection.createClob(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public CLOB createClob(byte[] paramArrayOfByte, short paramShort)
/*     */     throws SQLException
/*     */   {
/* 876 */     return this.internalConnection.createClob(paramArrayOfByte, paramShort);
/*     */   }
/*     */ 
/*     */   public BLOB createBlob(byte[] paramArrayOfByte) throws SQLException
/*     */   {
/* 881 */     return this.internalConnection.createBlob(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public BFILE createBfile(byte[] paramArrayOfByte) throws SQLException
/*     */   {
/* 886 */     return this.internalConnection.createBfile(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public boolean isDescriptorSharable(oracle.jdbc.internal.OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 892 */     return this.internalConnection.isDescriptorSharable(paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public OracleStatement refCursorCursorToStatement(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 898 */     return this.internalConnection.refCursorCursorToStatement(paramInt);
/*     */   }
/*     */ 
/*     */   public long getTdoCState(String paramString1, String paramString2) throws SQLException
/*     */   {
/* 903 */     return this.internalConnection.getTdoCState(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   public Datum toDatum(CustomDatum paramCustomDatum) throws SQLException {
/* 907 */     return this.internalConnection.toDatum(paramCustomDatum);
/*     */   }
/*     */ 
/*     */   public XAResource getXAResource()
/*     */     throws SQLException
/*     */   {
/* 924 */     return this.pooledConnection.getXAResource();
/*     */   }
/*     */ 
/*     */   public void setApplicationContext(String paramString1, String paramString2, String paramString3)
/*     */     throws SQLException
/*     */   {
/* 933 */     this.internalConnection.setApplicationContext(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   public void clearAllApplicationContext(String paramString)
/*     */     throws SQLException
/*     */   {
/* 940 */     this.internalConnection.clearAllApplicationContext(paramString);
/*     */   }
/*     */ 
/*     */   public boolean isV8Compatible()
/*     */     throws SQLException
/*     */   {
/* 954 */     return this.internalConnection.isV8Compatible();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LogicalConnection
 * JD-Core Version:    0.6.0
 */