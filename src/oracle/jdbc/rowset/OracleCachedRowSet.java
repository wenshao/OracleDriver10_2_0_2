/*      */ package oracle.jdbc.rowset;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PipedInputStream;
/*      */ import java.io.PipedOutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringBufferInputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.naming.NamingException;
/*      */ import javax.sql.DataSource;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.RowSetEvent;
/*      */ import javax.sql.RowSetInternal;
/*      */ import javax.sql.RowSetMetaData;
/*      */ import javax.sql.RowSetReader;
/*      */ import javax.sql.RowSetWriter;
/*      */ import javax.sql.rowset.CachedRowSet;
/*      */ import javax.sql.rowset.RowSetWarning;
/*      */ import javax.sql.rowset.spi.SyncFactory;
/*      */ import javax.sql.rowset.spi.SyncFactoryException;
/*      */ import javax.sql.rowset.spi.SyncProvider;
/*      */ import javax.sql.rowset.spi.SyncProviderException;
/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.jdbc.OracleSavepoint;
/*      */ import oracle.jdbc.driver.OracleDriver;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.CLOB;
/*      */ 
/*      */ public class OracleCachedRowSet extends OracleRowSet
/*      */   implements RowSet, RowSetInternal, Serializable, Cloneable, CachedRowSet
/*      */ {
/*      */   private SQLWarning sqlWarning;
/*      */   private RowSetWarning rowsetWarning;
/*      */   protected int presentRow;
/*      */   private int currentPage;
/*      */   private boolean isPopulateDone;
/*      */   private boolean previousColumnWasNull;
/*      */   private OracleRow insertRow;
/*      */   private int insertRowPosition;
/*      */   private boolean insertRowFlag;
/*      */   private int updateRowPosition;
/*      */   private boolean updateRowFlag;
/*      */   protected ResultSetMetaData rowsetMetaData;
/*      */   private transient ResultSet resultSet;
/*      */   private transient Connection connection;
/*  323 */   private transient boolean isConnectionStayingOpenForTxnControl = false;
/*      */   protected Vector rows;
/*      */   private Vector param;
/*      */   private String[] metaData;
/*      */   protected int colCount;
/*      */   protected int rowCount;
/*      */   private RowSetReader reader;
/*      */   private RowSetWriter writer;
/*      */   private int[] keyColumns;
/*      */   private int pageSize;
/*      */   private SyncProvider syncProvider;
/*      */   private static final String DEFAULT_SYNCPROVIDER = "com.sun.rowset.providers.RIOptimisticProvider";
/*      */   private String tableName;
/*  412 */   private boolean driverManagerInitialized = false;
/*      */ 
/*      */   public OracleCachedRowSet()
/*      */     throws SQLException
/*      */   {
/*  445 */     this.insertRowFlag = false;
/*  446 */     this.updateRowFlag = false;
/*      */ 
/*  448 */     this.presentRow = 0;
/*  449 */     this.previousColumnWasNull = false;
/*      */ 
/*  451 */     this.param = new Vector();
/*      */ 
/*  453 */     this.rows = new Vector();
/*      */ 
/*  455 */     this.sqlWarning = new SQLWarning();
/*      */     try
/*      */     {
/*  465 */       this.syncProvider = SyncFactory.getInstance("com.sun.rowset.providers.RIOptimisticProvider");
/*      */     }
/*      */     catch (SyncFactoryException localSyncFactoryException)
/*      */     {
/*  474 */       throw new SQLException("SyncProvider instance not constructed.");
/*      */     }
/*      */ 
/*  482 */     setReader(new OracleCachedRowSetReader());
/*  483 */     setWriter(new OracleCachedRowSetWriter());
/*      */ 
/*  485 */     this.currentPage = 0;
/*  486 */     this.pageSize = 0;
/*  487 */     this.isPopulateDone = false;
/*      */ 
/*  489 */     this.keyColumns = null;
/*  490 */     this.tableName = null;
/*      */   }
/*      */ 
/*      */   public Connection getConnection()
/*      */     throws SQLException
/*      */   {
/*  522 */     return getConnectionInternal();
/*      */   }
/*      */ 
/*      */   Connection getConnectionInternal()
/*      */     throws SQLException
/*      */   {
/*  528 */     if ((this.connection == null) || (this.connection.isClosed()))
/*      */     {
/*  530 */       String str1 = getUsername();
/*  531 */       String str2 = getPassword();
/*  532 */       if (getDataSourceName() != null)
/*      */       {
/*      */         try
/*      */         {
/*  536 */           InitialContext localInitialContext = null;
/*      */           try
/*      */           {
/*  555 */             Properties localProperties = System.getProperties();
/*  556 */             localInitialContext = new InitialContext(localProperties);
/*      */           }
/*      */           catch (SecurityException localSecurityException) {
/*      */           }
/*  560 */           if (localInitialContext == null)
/*  561 */             localInitialContext = new InitialContext();
/*  562 */           DataSource localDataSource = (DataSource)localInitialContext.lookup(getDataSourceName());
/*      */ 
/*  580 */           if ((this.username == null) || (str2 == null))
/*  581 */             this.connection = localDataSource.getConnection();
/*      */           else
/*  583 */             this.connection = localDataSource.getConnection(this.username, str2);
/*      */         }
/*      */         catch (NamingException localNamingException)
/*      */         {
/*  587 */           throw new SQLException("Unable to connect through the DataSource\n" + localNamingException.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*  591 */       else if (getUrl() != null)
/*      */       {
/*  593 */         if (!this.driverManagerInitialized)
/*      */         {
/*  595 */           DriverManager.registerDriver(new OracleDriver());
/*  596 */           this.driverManagerInitialized = true;
/*      */         }
/*  598 */         String str3 = getUrl();
/*      */ 
/*  616 */         if ((str3.equals("")) || (str1.equals("")) || (str2.equals("")))
/*      */         {
/*  618 */           throw new SQLException("One or more of the authenticating parameter not set");
/*      */         }
/*      */ 
/*  621 */         this.connection = DriverManager.getConnection(str3, str1, str2);
/*      */       }
/*      */       else
/*      */       {
/*  625 */         throw new SQLException("Authentication parameters not set");
/*      */       }
/*      */     }
/*      */ 
/*  629 */     return this.connection;
/*      */   }
/*      */ 
/*      */   public Statement getStatement()
/*      */     throws SQLException
/*      */   {
/*  656 */     if (this.resultSet == null)
/*      */     {
/*  661 */       throw new SQLException("ResultSet not open");
/*      */     }
/*      */ 
/*  666 */     return this.resultSet.getStatement();
/*      */   }
/*      */ 
/*      */   public RowSetReader getReader()
/*      */   {
/*  685 */     return this.reader;
/*      */   }
/*      */ 
/*      */   public RowSetWriter getWriter()
/*      */   {
/*  704 */     return this.writer;
/*      */   }
/*      */ 
/*      */   public void setFetchDirection(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  762 */     if (this.rowsetType == 1005) {
/*  763 */       throw new SQLException("Fetch direction cannot be applied when RowSet type is TYPE_SCROLL_SENSITIVE");
/*      */     }
/*  765 */     switch (paramInt)
/*      */     {
/*      */     case 1000:
/*      */     case 1002:
/*  769 */       this.presentRow = 0;
/*  770 */       break;
/*      */     case 1001:
/*  772 */       if (this.rowsetType == 1003) {
/*  773 */         throw new SQLException("FETCH_REVERSE cannot be applied when RowSet type is TYPE_FORWARD_ONLY");
/*      */       }
/*  775 */       this.presentRow = (this.rowCount + 1);
/*  776 */       break;
/*      */     default:
/*  778 */       throw new SQLException("Illegal fetch direction");
/*      */     }
/*      */ 
/*  782 */     super.setFetchDirection(paramInt);
/*      */   }
/*      */ 
/*      */   public void setReader(RowSetReader paramRowSetReader)
/*      */   {
/*  802 */     this.reader = paramRowSetReader;
/*      */   }
/*      */ 
/*      */   public void setWriter(RowSetWriter paramRowSetWriter)
/*      */   {
/*  821 */     this.writer = paramRowSetWriter;
/*      */   }
/*      */ 
/*      */   private final int getColumnIndex(String paramString)
/*      */     throws SQLException
/*      */   {
/*  854 */     paramString = paramString.toUpperCase();
/*  855 */     int i = 0;
/*  856 */     for (; i < this.metaData.length; i++)
/*      */     {
/*  858 */       if (paramString.equals(this.metaData[i]))
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  877 */     if (i >= this.metaData.length) {
/*  878 */       throw new SQLException("Invalid column name: " + paramString);
/*      */     }
/*  880 */     return i + 1;
/*      */   }
/*      */ 
/*      */   private final void checkColumnIndex(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  911 */     if (this.readOnly)
/*  912 */       throw new SQLException("The RowSet is not write enabled");
/*  913 */     if ((paramInt < 1) || (paramInt > this.colCount))
/*  914 */       throw new SQLException("invalid index : " + paramInt);
/*      */   }
/*      */ 
/*      */   private final boolean isUpdated(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  947 */     if ((paramInt < 1) || (paramInt > this.colCount))
/*  948 */       throw new SQLException("Invalid index : " + paramInt);
/*  949 */     return getCurrentRow().isColumnChanged(paramInt);
/*      */   }
/*      */ 
/*      */   private final void checkParamIndex(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  975 */     if (paramInt < 1)
/*  976 */       throw new SQLException("Invalid parameter index : " + paramInt);
/*      */   }
/*      */ 
/*      */   private final void populateInit(ResultSet paramResultSet)
/*      */     throws SQLException
/*      */   {
/* 1004 */     this.resultSet = paramResultSet;
/* 1005 */     Statement localStatement = paramResultSet.getStatement();
/*      */ 
/* 1022 */     this.maxFieldSize = localStatement.getMaxFieldSize();
/*      */ 
/* 1025 */     this.fetchSize = localStatement.getFetchSize();
/* 1026 */     this.queryTimeout = localStatement.getQueryTimeout();
/*      */ 
/* 1028 */     this.connection = localStatement.getConnection();
/*      */ 
/* 1045 */     this.transactionIsolation = this.connection.getTransactionIsolation();
/*      */ 
/* 1062 */     this.typeMap = this.connection.getTypeMap();
/* 1063 */     DatabaseMetaData localDatabaseMetaData = this.connection.getMetaData();
/*      */ 
/* 1082 */     this.url = localDatabaseMetaData.getURL();
/* 1083 */     this.username = localDatabaseMetaData.getUserName();
/*      */   }
/*      */ 
/*      */   private synchronized InputStream getStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1125 */     Object localObject = getObject(paramInt);
/*      */ 
/* 1143 */     if (localObject == null) {
/* 1144 */       return null;
/*      */     }
/* 1146 */     if ((localObject instanceof InputStream)) {
/* 1147 */       return (InputStream)localObject;
/*      */     }
/* 1149 */     if ((localObject instanceof String))
/*      */     {
/* 1151 */       return new ByteArrayInputStream(((String)localObject).getBytes());
/*      */     }
/* 1153 */     if ((localObject instanceof byte[]))
/*      */     {
/* 1155 */       return new ByteArrayInputStream((byte[])localObject);
/*      */     }
/* 1157 */     if ((localObject instanceof OracleSerialClob))
/* 1158 */       return ((OracleSerialClob)localObject).getAsciiStream();
/* 1159 */     if ((localObject instanceof OracleSerialBlob))
/* 1160 */       return ((OracleSerialBlob)localObject).getBinaryStream();
/* 1161 */     if ((localObject instanceof Reader))
/*      */     {
/*      */       try
/*      */       {
/* 1165 */         BufferedReader localBufferedReader = new BufferedReader((Reader)localObject);
/* 1166 */         int i = 0;
/* 1167 */         PipedInputStream localPipedInputStream = new PipedInputStream();
/* 1168 */         PipedOutputStream localPipedOutputStream = new PipedOutputStream(localPipedInputStream);
/* 1169 */         while ((i = localBufferedReader.read()) != -1)
/* 1170 */           localPipedOutputStream.write(i);
/* 1171 */         localPipedOutputStream.close();
/* 1172 */         return localPipedInputStream;
/*      */       } catch (IOException localIOException) {
/* 1174 */         throw new SQLException("Error during conversion: " + localIOException.getMessage());
/*      */       }
/*      */     }
/*      */ 
/* 1178 */     throw new SQLException("Could not convert the column into a stream type");
/*      */   }
/*      */ 
/*      */   protected synchronized void notifyCursorMoved()
/*      */   {
/* 1221 */     if (this.insertRowFlag)
/*      */     {
/* 1223 */       this.insertRowFlag = false;
/* 1224 */       this.insertRow.setRowUpdated(false);
/* 1225 */       this.sqlWarning.setNextWarning(new SQLWarning("Cancelling insertion, due to cursor movement."));
/*      */     }
/* 1233 */     else if (this.updateRowFlag)
/*      */     {
/*      */       try
/*      */       {
/* 1237 */         this.updateRowFlag = false;
/* 1238 */         int i = this.presentRow;
/* 1239 */         this.presentRow = this.updateRowPosition;
/* 1240 */         getCurrentRow().setRowUpdated(false);
/* 1241 */         this.presentRow = i;
/* 1242 */         this.sqlWarning.setNextWarning(new SQLWarning("Cancelling all updates, due to cursor movement."));
/*      */       }
/*      */       catch (SQLException localSQLException)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1252 */     super.notifyCursorMoved();
/*      */   }
/*      */ 
/*      */   protected void checkAndFilterObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/*      */   }
/*      */ 
/*      */   OracleRow getCurrentRow()
/*      */     throws SQLException
/*      */   {
/* 1276 */     int i = this.presentRow - 1;
/*      */ 
/* 1299 */     if ((this.presentRow < 1) || (this.presentRow > this.rowCount)) {
/* 1300 */       throw new SQLException("Operation with out calling next/previous");
/*      */     }
/*      */ 
/* 1303 */     return (OracleRow)this.rows.elementAt(this.presentRow - 1);
/*      */   }
/*      */ 
/*      */   boolean isConnectionStayingOpen()
/*      */   {
/* 1310 */     return this.isConnectionStayingOpenForTxnControl;
/*      */   }
/*      */ 
/*      */   void setOriginal()
/*      */     throws SQLException
/*      */   {
/* 1319 */     int i = 1;
/*      */     do
/*      */     {
/* 1323 */       boolean bool = setOriginalRowInternal(i);
/*      */ 
/* 1325 */       if (bool)
/*      */         continue;
/* 1327 */       i++;
/*      */     }
/*      */ 
/* 1330 */     while (i <= this.rowCount);
/*      */ 
/* 1332 */     notifyRowSetChanged();
/*      */ 
/* 1336 */     this.presentRow = 0;
/*      */   }
/*      */ 
/*      */   boolean setOriginalRowInternal(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1345 */     if ((paramInt < 1) || (paramInt > this.rowCount)) {
/* 1346 */       throw new SQLException("Invalid cursor position, try next/previous first");
/*      */     }
/* 1348 */     int i = 0;
/*      */ 
/* 1350 */     OracleRow localOracleRow = (OracleRow)this.rows.elementAt(paramInt - 1);
/*      */ 
/* 1352 */     if (localOracleRow.isRowDeleted())
/*      */     {
/* 1354 */       this.rows.remove(paramInt - 1);
/* 1355 */       this.rowCount -= 1;
/* 1356 */       i = 1;
/*      */     }
/*      */     else
/*      */     {
/* 1361 */       if (localOracleRow.isRowInserted())
/*      */       {
/* 1363 */         localOracleRow.setInsertedFlag(false);
/*      */       }
/*      */ 
/* 1366 */       if (localOracleRow.isRowUpdated())
/*      */       {
/* 1368 */         localOracleRow.makeUpdatesOriginal();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1375 */     return i;
/*      */   }
/*      */ 
/*      */   public boolean next()
/*      */     throws SQLException
/*      */   {
/* 1432 */     if (this.rowCount < 0)
/*      */     {
/* 1436 */       return false;
/*      */     }
/*      */ 
/* 1441 */     if ((this.fetchDirection == 1000) || (this.fetchDirection == 1002))
/*      */     {
/* 1443 */       if (this.presentRow + 1 <= this.rowCount)
/*      */       {
/* 1445 */         this.presentRow += 1;
/* 1446 */         if ((!this.showDeleted) && (getCurrentRow().isRowDeleted())) {
/* 1447 */           return next();
/*      */         }
/* 1449 */         notifyCursorMoved();
/* 1450 */         return true;
/*      */       }
/*      */ 
/* 1457 */       this.presentRow = (this.rowCount + 1);
/* 1458 */       return false;
/*      */     }
/*      */ 
/* 1461 */     if (this.fetchDirection == 1001)
/*      */     {
/* 1463 */       if (this.presentRow - 1 > 0)
/*      */       {
/* 1465 */         this.presentRow -= 1;
/* 1466 */         if ((!this.showDeleted) && (getCurrentRow().isRowDeleted()))
/* 1467 */           return next();
/* 1468 */         notifyCursorMoved();
/* 1469 */         return true;
/*      */       }
/*      */ 
/* 1476 */       this.presentRow = 0;
/* 1477 */       return false;
/*      */     }
/*      */ 
/* 1485 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean previous()
/*      */     throws SQLException
/*      */   {
/* 1517 */     if (this.rowCount < 0)
/*      */     {
/* 1521 */       return false;
/*      */     }
/*      */ 
/* 1526 */     if (this.fetchDirection == 1001)
/*      */     {
/* 1528 */       if (this.presentRow + 1 <= this.rowCount)
/*      */       {
/* 1530 */         this.presentRow += 1;
/* 1531 */         if ((!this.showDeleted) && (getCurrentRow().isRowDeleted()))
/* 1532 */           return previous();
/* 1533 */         notifyCursorMoved();
/* 1534 */         return true;
/*      */       }
/*      */ 
/* 1541 */       this.presentRow = (this.rowCount + 1);
/* 1542 */       return false;
/*      */     }
/*      */ 
/* 1546 */     if ((this.fetchDirection == 1000) || (this.fetchDirection == 1002))
/*      */     {
/* 1548 */       if (this.presentRow - 1 > 0)
/*      */       {
/* 1550 */         this.presentRow -= 1;
/* 1551 */         if ((!this.showDeleted) && (getCurrentRow().isRowDeleted()))
/* 1552 */           return previous();
/* 1553 */         notifyCursorMoved();
/* 1554 */         return true;
/*      */       }
/*      */ 
/* 1561 */       this.presentRow = 0;
/* 1562 */       return false;
/*      */     }
/*      */ 
/* 1570 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isBeforeFirst()
/*      */     throws SQLException
/*      */   {
/* 1599 */     return (this.rowCount > 0) && (this.presentRow == 0);
/*      */   }
/*      */ 
/*      */   public boolean isAfterLast()
/*      */     throws SQLException
/*      */   {
/* 1625 */     return (this.rowCount > 0) && (this.presentRow == this.rowCount + 1);
/*      */   }
/*      */ 
/*      */   public boolean isFirst()
/*      */     throws SQLException
/*      */   {
/* 1645 */     return this.presentRow == 1;
/*      */   }
/*      */ 
/*      */   public boolean isLast()
/*      */     throws SQLException
/*      */   {
/* 1671 */     return this.presentRow == this.rowCount;
/*      */   }
/*      */ 
/*      */   public void beforeFirst()
/*      */     throws SQLException
/*      */   {
/* 1695 */     this.presentRow = 0;
/*      */   }
/*      */ 
/*      */   public void afterLast() throws SQLException
/*      */   {
/* 1700 */     this.presentRow = (this.rowCount + 1);
/*      */   }
/*      */ 
/*      */   public boolean first()
/*      */     throws SQLException
/*      */   {
/* 1739 */     return absolute(1);
/*      */   }
/*      */ 
/*      */   public boolean last()
/*      */     throws SQLException
/*      */   {
/* 1759 */     return absolute(-1);
/*      */   }
/*      */ 
/*      */   public boolean absolute(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1792 */     if (this.rowsetType == 1003)
/* 1793 */       throw new SQLException("The RowSet type is TYPE_FORWARD_ONLY");
/* 1794 */     if ((paramInt == 0) || (Math.abs(paramInt) > this.rowCount)) return false;
/* 1795 */     this.presentRow = (paramInt < 0 ? this.rowCount + paramInt + 1 : paramInt);
/* 1796 */     notifyCursorMoved();
/* 1797 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean relative(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1821 */     return absolute(this.presentRow + paramInt);
/*      */   }
/*      */ 
/*      */   public synchronized void populate(ResultSet paramResultSet)
/*      */     throws SQLException
/*      */   {
/* 1870 */     if (this.rows == null)
/*      */     {
/* 1875 */       this.rows = new Vector(50, 10);
/*      */     }
/*      */     else
/*      */     {
/* 1880 */       this.rows.clear();
/* 1881 */     }this.rowsetMetaData = new OracleRowSetMetaData(paramResultSet.getMetaData());
/* 1882 */     this.metaData = new String[this.colCount = this.rowsetMetaData.getColumnCount()];
/* 1883 */     for (int i = 0; i < this.colCount; i++) {
/* 1884 */       this.metaData[i] = this.rowsetMetaData.getColumnName(i + 1);
/*      */     }
/*      */ 
/* 1901 */     if (!(paramResultSet instanceof OracleCachedRowSet)) {
/* 1902 */       populateInit(paramResultSet);
/*      */     }
/*      */ 
/* 1924 */     i = (this.fetchDirection == 1000) || (this.fetchDirection == 1002) ? 1 : 0;
/*      */ 
/* 1927 */     this.rowCount = 0;
/* 1928 */     OracleRow localOracleRow = null;
/*      */     int j;
/* 1934 */     if ((this.maxRows == 0) && (this.pageSize == 0))
/*      */     {
/* 1936 */       j = 2147483647;
/*      */     }
/* 1938 */     else if ((this.maxRows == 0) || (this.pageSize == 0))
/*      */     {
/* 1940 */       j = Math.max(this.maxRows, this.pageSize);
/*      */     }
/*      */     else
/*      */     {
/* 1944 */       j = Math.min(this.maxRows, this.pageSize);
/*      */     }
/*      */ 
/* 1954 */     if ((paramResultSet.getType() != 1003) && (this.rows.size() == 0))
/*      */     {
/* 1956 */       if (i == 0)
/*      */       {
/* 1958 */         paramResultSet.afterLast();
/*      */       }
/*      */     }
/*      */ 
/* 1962 */     while (this.rowCount < j)
/*      */     {
/* 1964 */       if (i != 0 ? 
/* 1966 */         !paramResultSet.next() : 
/* 1971 */         !paramResultSet.previous())
/*      */       {
/*      */         break;
/*      */       }
/* 1975 */       localOracleRow = new OracleRow(this.colCount);
/* 1976 */       for (int k = 1; k <= this.colCount; k++)
/*      */       {
/* 1978 */         Object localObject = null;
/*      */         try
/*      */         {
/* 1981 */           localObject = paramResultSet.getObject(k, this.typeMap);
/*      */         }
/*      */         catch (Exception localException)
/*      */         {
/* 1985 */           localObject = paramResultSet.getObject(k);
/*      */         }
/*      */         catch (AbstractMethodError localAbstractMethodError)
/*      */         {
/* 1989 */           localObject = paramResultSet.getObject(k);
/*      */         }
/*      */ 
/* 1992 */         if (((localObject instanceof Clob)) || ((localObject instanceof CLOB))) {
/* 1993 */           localOracleRow.setColumnValue(k, new OracleSerialClob((Clob)localObject));
/*      */         }
/* 1995 */         else if (((localObject instanceof Blob)) || ((localObject instanceof BLOB))) {
/* 1996 */           localOracleRow.setColumnValue(k, new OracleSerialBlob((Blob)localObject));
/*      */         }
/*      */         else {
/* 1999 */           localOracleRow.setColumnValue(k, localObject);
/*      */         }
/* 2001 */         localOracleRow.markOriginalNull(k, paramResultSet.wasNull());
/*      */       }
/*      */ 
/* 2004 */       if (i != 0)
/*      */       {
/* 2006 */         this.rows.add(localOracleRow);
/*      */       }
/*      */       else
/*      */       {
/* 2011 */         this.rows.add(1, localOracleRow);
/*      */       }
/*      */ 
/* 2014 */       this.rowCount += 1;
/*      */     }
/*      */ 
/* 2017 */     if (((i != 0) && (paramResultSet.isAfterLast())) || ((i == 0) && (paramResultSet.isBeforeFirst())))
/*      */     {
/* 2020 */       this.isPopulateDone = true;
/*      */     }
/*      */ 
/* 2040 */     this.connection = null;
/*      */ 
/* 2042 */     notifyRowSetChanged();
/*      */   }
/*      */ 
/*      */   public String getCursorName()
/*      */     throws SQLException
/*      */   {
/* 2063 */     throw new SQLException("Getting the cursor name is not supported.");
/*      */   }
/*      */ 
/*      */   public synchronized void clearParameters()
/*      */     throws SQLException
/*      */   {
/* 2069 */     this.param = null;
/* 2070 */     this.param = new Vector();
/*      */   }
/*      */ 
/*      */   public boolean wasNull()
/*      */     throws SQLException
/*      */   {
/* 2109 */     return this.previousColumnWasNull;
/*      */   }
/*      */ 
/*      */   public void close()
/*      */     throws SQLException
/*      */   {
/* 2133 */     release();
/*      */   }
/*      */ 
/*      */   public SQLWarning getWarnings()
/*      */     throws SQLException
/*      */   {
/* 2158 */     return this.sqlWarning;
/*      */   }
/*      */ 
/*      */   public void clearWarnings()
/*      */     throws SQLException
/*      */   {
/* 2178 */     this.sqlWarning = null;
/*      */   }
/*      */ 
/*      */   public ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/* 2203 */     return this.rowsetMetaData;
/*      */   }
/*      */ 
/*      */   public int findColumn(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2223 */     return getColumnIndex(paramString);
/*      */   }
/*      */ 
/*      */   public Object[] getParams()
/*      */     throws SQLException
/*      */   {
/* 2243 */     return this.param.toArray();
/*      */   }
/*      */ 
/*      */   public void setMetaData(RowSetMetaData paramRowSetMetaData)
/*      */     throws SQLException
/*      */   {
/* 2263 */     this.rowsetMetaData = paramRowSetMetaData;
/*      */ 
/* 2267 */     if (paramRowSetMetaData != null)
/*      */     {
/* 2269 */       this.colCount = paramRowSetMetaData.getColumnCount();
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void execute()
/*      */     throws SQLException
/*      */   {
/* 2291 */     this.isConnectionStayingOpenForTxnControl = false;
/* 2292 */     getReader().readData(this);
/*      */   }
/*      */ 
/*      */   public void acceptChanges()
/*      */     throws SyncProviderException
/*      */   {
/*      */     try
/*      */     {
/* 2331 */       getWriter().writeData(this);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/* 2339 */       throw new SyncProviderException(localSQLException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void acceptChanges(Connection paramConnection)
/*      */     throws SyncProviderException
/*      */   {
/* 2368 */     this.connection = paramConnection;
/*      */ 
/* 2372 */     this.isConnectionStayingOpenForTxnControl = true;
/* 2373 */     acceptChanges();
/*      */   }
/*      */ 
/*      */   public Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/*      */     try
/*      */     {
/* 2397 */       return createCopy();
/*      */     } catch (SQLException localSQLException) {
/*      */     }
/* 2400 */     throw new CloneNotSupportedException("SQL Error occured while cloning,\n" + localSQLException.getMessage());
/*      */   }
/*      */ 
/*      */   public CachedRowSet createCopy()
/*      */     throws SQLException
/*      */   {
/* 2426 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createShared();
/* 2427 */     int i = this.rows.size();
/* 2428 */     localOracleCachedRowSet.rows = new Vector(i);
/* 2429 */     for (int j = 0; j < i; j++) {
/* 2430 */       localOracleCachedRowSet.rows.add(((OracleRow)this.rows.elementAt(j)).createCopy());
/*      */     }
/*      */ 
/* 2446 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public RowSet createShared()
/*      */     throws SQLException
/*      */   {
/* 2466 */     OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();
/*      */ 
/* 2471 */     localOracleCachedRowSet.rows = this.rows;
/*      */ 
/* 2477 */     localOracleCachedRowSet.setDataSource(getDataSource());
/* 2478 */     localOracleCachedRowSet.setDataSourceName(getDataSourceName());
/* 2479 */     localOracleCachedRowSet.setUsername(getUsername());
/* 2480 */     localOracleCachedRowSet.setPassword(getPassword());
/* 2481 */     localOracleCachedRowSet.setUrl(getUrl());
/* 2482 */     localOracleCachedRowSet.setTypeMap(getTypeMap());
/* 2483 */     localOracleCachedRowSet.setMaxFieldSize(getMaxFieldSize());
/* 2484 */     localOracleCachedRowSet.setMaxRows(getMaxRows());
/* 2485 */     localOracleCachedRowSet.setQueryTimeout(getQueryTimeout());
/* 2486 */     localOracleCachedRowSet.setFetchSize(getFetchSize());
/* 2487 */     localOracleCachedRowSet.setEscapeProcessing(getEscapeProcessing());
/* 2488 */     localOracleCachedRowSet.setConcurrency(getConcurrency());
/* 2489 */     localOracleCachedRowSet.setReadOnly(this.readOnly);
/*      */ 
/* 2494 */     this.rowsetType = getType();
/* 2495 */     this.fetchDirection = getFetchDirection();
/* 2496 */     localOracleCachedRowSet.setCommand(getCommand());
/* 2497 */     localOracleCachedRowSet.setTransactionIsolation(getTransactionIsolation());
/*      */ 
/* 2500 */     localOracleCachedRowSet.presentRow = this.presentRow;
/* 2501 */     localOracleCachedRowSet.colCount = this.colCount;
/* 2502 */     localOracleCachedRowSet.rowCount = this.rowCount;
/* 2503 */     localOracleCachedRowSet.showDeleted = this.showDeleted;
/*      */ 
/* 2509 */     localOracleCachedRowSet.syncProvider = this.syncProvider;
/*      */ 
/* 2512 */     localOracleCachedRowSet.currentPage = this.currentPage;
/* 2513 */     localOracleCachedRowSet.pageSize = this.pageSize;
/* 2514 */     localOracleCachedRowSet.tableName = (this.tableName == null ? null : new String(this.tableName));
/* 2515 */     localOracleCachedRowSet.keyColumns = (this.keyColumns == null ? null : (int[])this.keyColumns.clone());
/*      */ 
/* 2517 */     int i = this.listener.size();
/* 2518 */     for (int j = 0; j < i; j++) {
/* 2519 */       localOracleCachedRowSet.listener.add(this.listener.elementAt(j));
/*      */     }
/*      */ 
/* 2528 */     localOracleCachedRowSet.rowsetMetaData = new OracleRowSetMetaData(this.rowsetMetaData);
/*      */ 
/* 2530 */     i = this.param.size();
/* 2531 */     for (j = 0; j < i; j++) {
/* 2532 */       localOracleCachedRowSet.param.add(this.param.elementAt(j));
/*      */     }
/* 2534 */     localOracleCachedRowSet.metaData = new String[this.metaData.length];
/* 2535 */     System.arraycopy(this.metaData, 0, localOracleCachedRowSet.metaData, 0, this.metaData.length);
/*      */ 
/* 2554 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public void release()
/*      */     throws SQLException
/*      */   {
/* 2574 */     this.rows = null;
/* 2575 */     this.rows = new Vector();
/* 2576 */     if ((this.connection != null) && (!this.connection.isClosed()))
/* 2577 */       this.connection.close();
/* 2578 */     this.rowCount = 0;
/* 2579 */     this.presentRow = 0;
/* 2580 */     notifyRowSetChanged();
/*      */   }
/*      */ 
/*      */   public void restoreOriginal()
/*      */     throws SQLException
/*      */   {
/* 2600 */     int i = 0;
/* 2601 */     for (int j = 0; j < this.rowCount; j++)
/*      */     {
/* 2603 */       OracleRow localOracleRow = (OracleRow)this.rows.elementAt(j);
/* 2604 */       if (localOracleRow.isRowInserted())
/*      */       {
/* 2606 */         this.rows.remove(j);
/* 2607 */         this.rowCount -= 1;
/*      */ 
/* 2612 */         j--;
/* 2613 */         i = 1;
/*      */       }
/* 2615 */       else if (localOracleRow.isRowUpdated())
/*      */       {
/* 2617 */         localOracleRow.setRowUpdated(false);
/* 2618 */         i = 1;
/*      */       } else {
/* 2620 */         if (!localOracleRow.isRowDeleted())
/*      */           continue;
/* 2622 */         localOracleRow.setRowDeleted(false);
/* 2623 */         i = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2642 */     if (i == 0) {
/* 2643 */       throw new SQLException("None of the rows are changed");
/*      */     }
/*      */ 
/* 2646 */     notifyRowSetChanged();
/*      */ 
/* 2649 */     this.presentRow = 0;
/*      */   }
/*      */ 
/*      */   public Collection toCollection()
/*      */     throws SQLException
/*      */   {
/* 2670 */     Map localMap = Collections.synchronizedMap(new TreeMap());
/*      */     try
/*      */     {
/* 2674 */       for (int i = 0; i < this.rowCount; i++)
/*      */       {
/* 2676 */         localMap.put(new Integer(i), ((OracleRow)this.rows.elementAt(i)).toCollection());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 2685 */       localMap = null;
/* 2686 */       throw new SQLException("Map operation failed in toCollection()");
/*      */     }
/*      */ 
/* 2706 */     return localMap.values();
/*      */   }
/*      */ 
/*      */   public Collection toCollection(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2726 */     if ((paramInt < 1) || (paramInt > this.colCount)) {
/* 2727 */       throw new SQLException("invalid index : " + paramInt);
/*      */     }
/* 2729 */     Vector localVector = new Vector(this.rowCount);
/* 2730 */     for (int i = 0; i < this.rowCount; i++)
/*      */     {
/* 2732 */       OracleRow localOracleRow = (OracleRow)this.rows.elementAt(i);
/* 2733 */       Object localObject = localOracleRow.isColumnChanged(paramInt) ? localOracleRow.getModifiedColumn(paramInt) : localOracleRow.getColumn(paramInt);
/*      */ 
/* 2736 */       localVector.add(localObject);
/*      */     }
/*      */ 
/* 2754 */     return localVector;
/*      */   }
/*      */ 
/*      */   public Collection toCollection(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2760 */     return toCollection(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public int getRow()
/*      */     throws SQLException
/*      */   {
/* 2793 */     if (this.presentRow > this.rowCount) {
/* 2794 */       return this.rowCount;
/*      */     }
/*      */ 
/* 2802 */     return this.presentRow;
/*      */   }
/*      */ 
/*      */   public void cancelRowInsert()
/*      */     throws SQLException
/*      */   {
/* 2822 */     if (getCurrentRow().isRowInserted())
/*      */     {
/* 2824 */       this.rows.remove(--this.presentRow);
/* 2825 */       this.rowCount -= 1;
/* 2826 */       notifyRowChanged();
/*      */     }
/*      */     else {
/* 2829 */       throw new SQLException("The row is not inserted");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cancelRowDelete()
/*      */     throws SQLException
/*      */   {
/* 2850 */     if (getCurrentRow().isRowDeleted())
/*      */     {
/* 2852 */       getCurrentRow().setRowDeleted(false);
/* 2853 */       notifyRowChanged();
/*      */     }
/*      */     else {
/* 2856 */       throw new SQLException("The row is not deleted");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cancelRowUpdates()
/*      */     throws SQLException
/*      */   {
/* 2877 */     if (getCurrentRow().isRowUpdated())
/*      */     {
/* 2879 */       this.updateRowFlag = false;
/* 2880 */       getCurrentRow().setRowUpdated(false);
/* 2881 */       notifyRowChanged();
/*      */     }
/*      */     else {
/* 2884 */       throw new SQLException("The row is not updated.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void insertRow()
/*      */     throws SQLException
/*      */   {
/* 2910 */     if (!this.insertRowFlag) {
/* 2911 */       throw new SQLException("Current row not inserted/updated.");
/*      */     }
/* 2913 */     if (!this.insertRow.isRowFullyPopulated()) {
/* 2914 */       throw new SQLException("All the columns of the row are not set");
/*      */     }
/*      */ 
/* 2918 */     this.insertRow.insertRow();
/* 2919 */     this.rows.insertElementAt(this.insertRow, this.insertRowPosition - 1);
/* 2920 */     this.insertRowFlag = false;
/* 2921 */     this.rowCount += 1;
/* 2922 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void updateRow()
/*      */     throws SQLException
/*      */   {
/* 2948 */     if (this.updateRowFlag)
/*      */     {
/* 2950 */       this.updateRowFlag = false;
/* 2951 */       getCurrentRow().setRowUpdated(true);
/* 2952 */       notifyRowChanged();
/*      */     }
/*      */     else {
/* 2955 */       throw new SQLException("Current row not updated");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteRow()
/*      */     throws SQLException
/*      */   {
/* 2975 */     getCurrentRow().setRowDeleted(true);
/* 2976 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void refreshRow()
/*      */     throws SQLException
/*      */   {
/* 2996 */     OracleRow localOracleRow = getCurrentRow();
/* 2997 */     if (localOracleRow.isRowUpdated())
/* 2998 */       localOracleRow.cancelRowUpdates();
/*      */   }
/*      */ 
/*      */   public void moveToInsertRow()
/*      */     throws SQLException
/*      */   {
/* 3032 */     this.insertRow = new OracleRow(this.colCount, true);
/* 3033 */     this.insertRowFlag = true;
/*      */ 
/* 3035 */     if (isAfterLast())
/* 3036 */       this.insertRowPosition = this.presentRow;
/*      */     else
/* 3038 */       this.insertRowPosition = (this.presentRow + 1);
/*      */   }
/*      */ 
/*      */   public void moveToCurrentRow()
/*      */     throws SQLException
/*      */   {
/* 3077 */     this.insertRowFlag = false;
/* 3078 */     this.updateRowFlag = false;
/* 3079 */     absolute(this.presentRow);
/*      */   }
/*      */ 
/*      */   public boolean rowUpdated()
/*      */     throws SQLException
/*      */   {
/* 3099 */     return getCurrentRow().isRowUpdated();
/*      */   }
/*      */ 
/*      */   public boolean rowInserted()
/*      */     throws SQLException
/*      */   {
/* 3119 */     return getCurrentRow().isRowInserted();
/*      */   }
/*      */ 
/*      */   public boolean rowDeleted()
/*      */     throws SQLException
/*      */   {
/* 3139 */     return getCurrentRow().isRowDeleted();
/*      */   }
/*      */ 
/*      */   public ResultSet getOriginalRow()
/*      */     throws SQLException
/*      */   {
/* 3162 */     OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();
/* 3163 */     localOracleCachedRowSet.rowsetMetaData = this.rowsetMetaData;
/* 3164 */     localOracleCachedRowSet.rowCount = 1;
/* 3165 */     localOracleCachedRowSet.colCount = this.colCount;
/* 3166 */     localOracleCachedRowSet.presentRow = 0;
/* 3167 */     localOracleCachedRowSet.setReader(null);
/* 3168 */     localOracleCachedRowSet.setWriter(null);
/* 3169 */     OracleRow localOracleRow = new OracleRow(this.rowsetMetaData.getColumnCount(), getCurrentRow().getOriginalRow());
/*      */ 
/* 3172 */     localOracleCachedRowSet.rows.add(localOracleRow);
/*      */ 
/* 3189 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getOriginal()
/*      */     throws SQLException
/*      */   {
/* 3212 */     OracleCachedRowSet localOracleCachedRowSet = new OracleCachedRowSet();
/* 3213 */     localOracleCachedRowSet.rowsetMetaData = this.rowsetMetaData;
/* 3214 */     localOracleCachedRowSet.rowCount = this.rowCount;
/* 3215 */     localOracleCachedRowSet.colCount = this.colCount;
/*      */ 
/* 3218 */     localOracleCachedRowSet.presentRow = 0;
/*      */ 
/* 3221 */     localOracleCachedRowSet.setType(1004);
/* 3222 */     localOracleCachedRowSet.setConcurrency(1008);
/*      */ 
/* 3224 */     localOracleCachedRowSet.setReader(null);
/* 3225 */     localOracleCachedRowSet.setWriter(null);
/* 3226 */     int i = this.rowsetMetaData.getColumnCount();
/* 3227 */     OracleRow localOracleRow = null;
/*      */ 
/* 3229 */     Iterator localIterator = this.rows.iterator();
/* 3230 */     while (localIterator.hasNext())
/*      */     {
/* 3232 */       localOracleRow = new OracleRow(i, ((OracleRow)localIterator.next()).getOriginalRow());
/*      */ 
/* 3231 */       localOracleCachedRowSet.rows.add(localOracleRow);
/*      */     }
/*      */ 
/* 3249 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public void setNull(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3280 */     checkParamIndex(paramInt1);
/* 3281 */     this.param.add(paramInt1 - 1, null);
/*      */   }
/*      */ 
/*      */   public void setNull(int paramInt1, int paramInt2, String paramString)
/*      */     throws SQLException
/*      */   {
/* 3307 */     checkParamIndex(paramInt1);
/* 3308 */     Object[] arrayOfObject = { new Integer(paramInt2), paramString };
/* 3309 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 3335 */     checkParamIndex(paramInt);
/* 3336 */     this.param.add(paramInt - 1, new Boolean(paramBoolean));
/*      */   }
/*      */ 
/*      */   public void setByte(int paramInt, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 3362 */     checkParamIndex(paramInt);
/* 3363 */     this.param.add(paramInt - 1, new Byte(paramByte));
/*      */   }
/*      */ 
/*      */   public void setShort(int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 3389 */     checkParamIndex(paramInt);
/* 3390 */     this.param.add(paramInt - 1, new Short(paramShort));
/*      */   }
/*      */ 
/*      */   public void setInt(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3416 */     checkParamIndex(paramInt1);
/* 3417 */     this.param.add(paramInt1 - 1, new Integer(paramInt2));
/*      */   }
/*      */ 
/*      */   public void setLong(int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 3443 */     checkParamIndex(paramInt);
/* 3444 */     this.param.add(paramInt - 1, new Long(paramLong));
/*      */   }
/*      */ 
/*      */   public void setFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 3470 */     checkParamIndex(paramInt);
/* 3471 */     this.param.add(paramInt - 1, new Float(paramFloat));
/*      */   }
/*      */ 
/*      */   public void setDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 3497 */     checkParamIndex(paramInt);
/* 3498 */     this.param.add(paramInt - 1, new Double(paramDouble));
/*      */   }
/*      */ 
/*      */   public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 3524 */     checkParamIndex(paramInt);
/* 3525 */     this.param.add(paramInt - 1, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void setString(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 3551 */     checkParamIndex(paramInt);
/* 3552 */     this.param.add(paramInt - 1, paramString);
/*      */   }
/*      */ 
/*      */   public void setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 3578 */     checkParamIndex(paramInt);
/* 3579 */     this.param.add(paramInt - 1, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void setDate(int paramInt, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 3605 */     checkParamIndex(paramInt);
/* 3606 */     this.param.add(paramInt - 1, paramDate);
/*      */   }
/*      */ 
/*      */   public void setTime(int paramInt, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 3632 */     checkParamIndex(paramInt);
/* 3633 */     this.param.add(paramInt - 1, paramTime);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 3659 */     checkParamIndex(paramInt);
/* 3660 */     this.param.add(paramInt - 1, paramObject);
/*      */   }
/*      */ 
/*      */   public void setRef(int paramInt, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 3686 */     checkParamIndex(paramInt);
/* 3687 */     this.param.add(paramInt - 1, paramRef);
/*      */   }
/*      */ 
/*      */   public void setBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 3713 */     checkParamIndex(paramInt);
/* 3714 */     this.param.add(paramInt - 1, paramBlob);
/*      */   }
/*      */ 
/*      */   public void setClob(int paramInt, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 3740 */     checkParamIndex(paramInt);
/* 3741 */     this.param.add(paramInt - 1, paramClob);
/*      */   }
/*      */ 
/*      */   public void setArray(int paramInt, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 3767 */     checkParamIndex(paramInt);
/* 3768 */     this.param.add(paramInt - 1, paramArray);
/*      */   }
/*      */ 
/*      */   public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3796 */     checkParamIndex(paramInt1);
/* 3797 */     Object[] arrayOfObject = { paramInputStream, new Integer(paramInt2), new Integer(546) };
/*      */ 
/* 3799 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3826 */     checkParamIndex(paramInt);
/* 3827 */     Object[] arrayOfObject = { paramTime, paramCalendar };
/* 3828 */     this.param.add(paramInt - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3855 */     checkParamIndex(paramInt);
/* 3856 */     Object[] arrayOfObject = { paramTimestamp, paramCalendar };
/* 3857 */     this.param.add(paramInt - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 3883 */     checkParamIndex(paramInt);
/* 3884 */     this.param.add(paramInt - 1, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3911 */     checkParamIndex(paramInt1);
/* 3912 */     Object[] arrayOfObject = { paramInputStream, new Integer(paramInt2), new Integer(819) };
/*      */ 
/* 3914 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3941 */     checkParamIndex(paramInt1);
/* 3942 */     Object[] arrayOfObject = { paramInputStream, new Integer(paramInt2), new Integer(273) };
/*      */ 
/* 3944 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3972 */     checkParamIndex(paramInt1);
/* 3973 */     Object[] arrayOfObject = { paramReader, new Integer(paramInt2) };
/* 3974 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 4004 */     checkParamIndex(paramInt1);
/* 4005 */     Object[] arrayOfObject = { paramObject, new Integer(paramInt2), new Integer(paramInt3) };
/* 4006 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4033 */     checkParamIndex(paramInt1);
/* 4034 */     Object[] arrayOfObject = { paramObject, new Integer(paramInt2) };
/* 4035 */     this.param.add(paramInt1 - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setDate(int paramInt, java.sql.Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4062 */     checkParamIndex(paramInt);
/* 4063 */     Object[] arrayOfObject = { paramDate, paramCalendar };
/* 4064 */     this.param.add(paramInt - 1, arrayOfObject);
/*      */   }
/*      */ 
/*      */   public synchronized Object getObject(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4095 */     int i = this.presentRow * this.colCount + paramInt - 1;
/* 4096 */     Object localObject = null;
/*      */ 
/* 4098 */     if (!isUpdated(paramInt))
/* 4099 */       localObject = getCurrentRow().getColumn(paramInt);
/*      */     else
/* 4101 */       localObject = getCurrentRow().getModifiedColumn(paramInt);
/* 4102 */     this.previousColumnWasNull = (localObject == null);
/*      */ 
/* 4119 */     return localObject;
/*      */   }
/*      */ 
/*      */   private synchronized Number getNumber(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4141 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4159 */     if ((localObject == null) || ((localObject instanceof BigDecimal)) || ((localObject instanceof Number)))
/*      */     {
/* 4162 */       return (Number)localObject;
/*      */     }
/* 4164 */     if ((localObject instanceof Boolean))
/* 4165 */       return new Integer(((Boolean)localObject).booleanValue() ? 1 : 0);
/* 4166 */     if ((localObject instanceof String))
/*      */     {
/*      */       try
/*      */       {
/* 4170 */         return new BigDecimal((String)localObject);
/*      */       }
/*      */       catch (NumberFormatException localNumberFormatException)
/*      */       {
/* 4174 */         throw new SQLException("Fail to convert to internal representation");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4179 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public Object getObject(int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 4207 */     return getObject(paramInt);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4228 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4246 */     if (localObject == null) {
/* 4247 */       return false;
/*      */     }
/* 4249 */     if ((localObject instanceof Boolean)) {
/* 4250 */       return ((Boolean)localObject).booleanValue();
/*      */     }
/* 4252 */     if ((localObject instanceof Number)) {
/* 4253 */       return ((Number)localObject).doubleValue() != 0.0D;
/*      */     }
/*      */ 
/* 4256 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public byte getByte(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4281 */     Object localObject1 = getObject(paramInt);
/*      */ 
/* 4299 */     if (localObject1 == null) {
/* 4300 */       return 0;
/*      */     }
/* 4302 */     if ((localObject1 instanceof Number)) {
/* 4303 */       return ((Number)localObject1).byteValue();
/*      */     }
/* 4305 */     if ((localObject1 instanceof String))
/* 4306 */       return ((String)localObject1).getBytes()[0];
/*      */     Object localObject2;
/* 4308 */     if ((localObject1 instanceof OracleSerialBlob))
/*      */     {
/* 4310 */       localObject2 = (OracleSerialBlob)localObject1;
/* 4311 */       return localObject2.getBytes(0L, 1)[0];
/*      */     }
/* 4313 */     if ((localObject1 instanceof OracleSerialClob))
/*      */     {
/* 4315 */       localObject2 = (OracleSerialClob)localObject1;
/* 4316 */       return localObject2.getSubString(0L, 1).getBytes()[0];
/*      */     }
/*      */ 
/* 4319 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public short getShort(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4340 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4357 */     return localNumber == null ? 0 : localNumber.shortValue();
/*      */   }
/*      */ 
/*      */   public int getInt(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4378 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4396 */     return localNumber == null ? 0 : localNumber.intValue();
/*      */   }
/*      */ 
/*      */   public long getLong(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4417 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4435 */     return localNumber == null ? 0L : localNumber.longValue();
/*      */   }
/*      */ 
/*      */   public float getFloat(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4456 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4473 */     return localNumber == null ? 0.0F : localNumber.floatValue();
/*      */   }
/*      */ 
/*      */   public double getDouble(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4494 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4512 */     return localNumber == null ? 0.0D : localNumber.doubleValue();
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4533 */     Number localNumber = getNumber(paramInt);
/*      */ 
/* 4551 */     if ((localNumber == null) || ((localNumber instanceof BigDecimal)))
/* 4552 */       return (BigDecimal)localNumber;
/* 4553 */     if ((localNumber instanceof Number)) {
/* 4554 */       return new BigDecimal(((Number)localNumber).doubleValue());
/*      */     }
/*      */ 
/* 4559 */     return null;
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4583 */     return getBigDecimal(paramInt1);
/*      */   }
/*      */ 
/*      */   public java.sql.Date getDate(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4604 */     Object localObject1 = getObject(paramInt);
/*      */ 
/* 4622 */     if (localObject1 == null)
/* 4623 */       return (java.sql.Date)localObject1;
/*      */     Object localObject2;
/* 4625 */     if ((localObject1 instanceof Time))
/*      */     {
/* 4627 */       localObject2 = (Time)localObject1;
/* 4628 */       return new java.sql.Date(((Time)localObject2).getTime());
/*      */     }
/*      */ 
/* 4631 */     if ((localObject1 instanceof java.util.Date))
/*      */     {
/* 4633 */       localObject2 = (java.util.Date)localObject1;
/* 4634 */       return new java.sql.Date(((java.util.Date)localObject2).getYear(), ((java.util.Date)localObject2).getMonth(), ((java.util.Date)localObject2).getDate());
/*      */     }
/*      */ 
/* 4637 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public java.sql.Date getDate(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4658 */     return getDate(paramInt);
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4680 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4698 */     if (localObject == null)
/* 4699 */       return (Time)localObject;
/* 4700 */     if ((localObject instanceof java.util.Date))
/*      */     {
/* 4702 */       java.util.Date localDate = (java.util.Date)localObject;
/* 4703 */       return new Time(localDate.getHours(), localDate.getMinutes(), localDate.getSeconds());
/*      */     }
/*      */ 
/* 4707 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4728 */     return getTime(paramInt);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4749 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4767 */     if ((localObject == null) || ((localObject instanceof Timestamp)))
/* 4768 */       return (Timestamp)localObject;
/* 4769 */     if ((localObject instanceof java.util.Date)) {
/* 4770 */       return new Timestamp(((java.util.Date)localObject).getTime());
/*      */     }
/* 4772 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4793 */     return getTimestamp(paramInt);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4814 */     Object localObject1 = getObject(paramInt);
/*      */ 
/* 4832 */     if (localObject1 == null) {
/* 4833 */       return (byte[])localObject1;
/*      */     }
/* 4835 */     if ((localObject1 instanceof byte[])) {
/* 4836 */       return (byte[])localObject1;
/*      */     }
/* 4838 */     if ((localObject1 instanceof String)) {
/* 4839 */       return (byte[])((String)localObject1).getBytes();
/*      */     }
/* 4841 */     if ((localObject1 instanceof Number)) {
/* 4842 */       return (byte[])((Number)localObject1).toString().getBytes();
/*      */     }
/* 4844 */     if ((localObject1 instanceof BigDecimal))
/* 4845 */       return (byte[])((BigDecimal)localObject1).toString().getBytes();
/*      */     Object localObject2;
/* 4847 */     if ((localObject1 instanceof OracleSerialBlob))
/*      */     {
/* 4849 */       localObject2 = (OracleSerialBlob)localObject1;
/* 4850 */       return ((OracleSerialBlob)localObject2).getBytes(0L, (int)((OracleSerialBlob)localObject2).length());
/*      */     }
/* 4852 */     if ((localObject1 instanceof OracleSerialClob))
/*      */     {
/* 4854 */       localObject2 = (OracleSerialClob)localObject1;
/* 4855 */       return ((OracleSerialClob)localObject2).getSubString(0L, (int)((OracleSerialClob)localObject2).length()).getBytes();
/*      */     }
/*      */ 
/* 4858 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public Ref getRef(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4878 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4895 */     if ((localObject == null) || ((localObject instanceof Ref))) {
/* 4896 */       return (Ref)localObject;
/*      */     }
/* 4898 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public Blob getBlob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4918 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4935 */     if ((localObject instanceof OracleSerialBlob)) {
/* 4936 */       return localObject == null ? null : (Blob)localObject;
/*      */     }
/* 4938 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public Clob getClob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4958 */     Object localObject = getObject(paramInt);
/*      */ 
/* 4975 */     if ((localObject instanceof OracleSerialClob)) {
/* 4976 */       return localObject == null ? null : (Clob)localObject;
/*      */     }
/* 4978 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public Array getArray(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4998 */     Object localObject = getObject(paramInt);
/*      */ 
/* 5015 */     if ((localObject == null) || ((localObject instanceof Array))) {
/* 5016 */       return (Array)localObject;
/*      */     }
/* 5018 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public String getString(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5037 */     Object localObject1 = getObject(paramInt);
/*      */ 
/* 5055 */     if (localObject1 == null) {
/* 5056 */       return (String)localObject1;
/*      */     }
/* 5058 */     if ((localObject1 instanceof String)) {
/* 5059 */       return (String)localObject1;
/*      */     }
/* 5061 */     if (((localObject1 instanceof Number)) || ((localObject1 instanceof BigDecimal))) {
/* 5062 */       return localObject1.toString();
/*      */     }
/* 5064 */     if ((localObject1 instanceof java.sql.Date)) {
/* 5065 */       return ((java.sql.Date)localObject1).toString();
/*      */     }
/* 5067 */     if ((localObject1 instanceof Timestamp)) {
/* 5068 */       return ((java.sql.Date)localObject1).toString();
/*      */     }
/* 5070 */     if ((localObject1 instanceof byte[]))
/* 5071 */       return new String((byte[])localObject1);
/*      */     Object localObject2;
/* 5073 */     if ((localObject1 instanceof OracleSerialClob))
/*      */     {
/* 5075 */       localObject2 = (OracleSerialClob)localObject1;
/* 5076 */       return ((OracleSerialClob)localObject2).getSubString(0L, (int)((OracleSerialClob)localObject2).length());
/*      */     }
/*      */ 
/* 5079 */     if ((localObject1 instanceof OracleSerialBlob))
/*      */     {
/* 5081 */       localObject2 = (OracleSerialBlob)localObject1;
/* 5082 */       return new String(((OracleSerialBlob)localObject2).getBytes(0L, (int)((OracleSerialBlob)localObject2).length()));
/*      */     }
/*      */ 
/* 5086 */     if ((localObject1 instanceof URL)) {
/* 5087 */       return ((URL)localObject1).toString();
/*      */     }
/* 5089 */     if ((localObject1 instanceof Reader))
/*      */     {
/*      */       try
/*      */       {
/* 5093 */         localObject2 = (Reader)localObject1;
/* 5094 */         char[] arrayOfChar = new char[1024];
/* 5095 */         int i = 0;
/* 5096 */         StringBuffer localStringBuffer = new StringBuffer(1024);
/* 5097 */         while ((i = ((Reader)localObject2).read(arrayOfChar)) > 0)
/* 5098 */           localStringBuffer.append(arrayOfChar, 0, i);
/* 5099 */         return localStringBuffer.substring(0, localStringBuffer.length());
/*      */       } catch (IOException localIOException) {
/* 5101 */         throw new SQLException("Error during conversion: " + localIOException.getMessage());
/*      */       }
/*      */     }
/*      */ 
/* 5105 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5125 */     InputStream localInputStream = getStream(paramInt);
/*      */ 
/* 5142 */     return localInputStream == null ? null : localInputStream;
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5162 */     Object localObject = getObject(paramInt);
/*      */ 
/* 5179 */     if (localObject == null) {
/* 5180 */       return (InputStream)localObject;
/*      */     }
/*      */ 
/* 5183 */     if ((localObject instanceof String)) {
/* 5184 */       return new StringBufferInputStream((String)localObject);
/*      */     }
/* 5186 */     throw new SQLException("Fail to convert to internal representation");
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5207 */     InputStream localInputStream = getStream(paramInt);
/*      */ 
/* 5224 */     return localInputStream == null ? null : localInputStream;
/*      */   }
/*      */ 
/*      */   public synchronized Reader getCharacterStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 5247 */       InputStream localInputStream = getAsciiStream(paramInt);
/*      */ 
/* 5264 */       if (localInputStream == null)
/* 5265 */         return null;
/* 5266 */       StringBuffer localStringBuffer = new StringBuffer();
/* 5267 */       int i = 0;
/* 5268 */       while ((i = localInputStream.read()) != -1) {
/* 5269 */         localStringBuffer.append((char)i);
/*      */       }
/*      */ 
/* 5286 */       char[] arrayOfChar = new char[localStringBuffer.length()];
/* 5287 */       localStringBuffer.getChars(0, localStringBuffer.length(), arrayOfChar, 0);
/* 5288 */       CharArrayReader localCharArrayReader = new CharArrayReader(arrayOfChar);
/* 5289 */       arrayOfChar = null;
/*      */ 
/* 5306 */       return localCharArrayReader;
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*      */     }
/*      */ 
/* 5312 */     throw new SQLException("Error: could not read from the stream");
/*      */   }
/*      */ 
/*      */   public synchronized Object getObject(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5343 */     return getObject(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5363 */     return getBoolean(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public byte getByte(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5383 */     return getByte(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public short getShort(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5403 */     return getShort(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public int getInt(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5423 */     return getInt(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public long getLong(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5443 */     return getLong(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public float getFloat(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5463 */     return getFloat(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public double getDouble(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5483 */     return getDouble(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5504 */     return getBigDecimal(getColumnIndex(paramString), paramInt);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5524 */     return getBytes(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public java.sql.Date getDate(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5544 */     return getDate(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5564 */     return getTime(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5584 */     return getTimestamp(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 5611 */     return getTime(getColumnIndex(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public java.sql.Date getDate(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 5639 */     return getDate(getColumnIndex(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5659 */     return getAsciiStream(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5679 */     return getUnicodeStream(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public String getString(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5699 */     return getString(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5719 */     return getBinaryStream(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Reader getCharacterStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5739 */     return getCharacterStream(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5759 */     return getBigDecimal(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 5786 */     return getTimestamp(getColumnIndex(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 5813 */     return getObject(getColumnIndex(paramString), paramMap);
/*      */   }
/*      */ 
/*      */   public Ref getRef(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5833 */     return getRef(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Blob getBlob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5853 */     return getBlob(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Clob getClob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5873 */     return getClob(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public Array getArray(String paramString)
/*      */     throws SQLException
/*      */   {
/* 5893 */     return getArray(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public synchronized void updateObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 5931 */     checkColumnIndex(paramInt);
/* 5932 */     if (this.insertRowFlag)
/*      */     {
/* 5934 */       checkAndFilterObject(paramInt, paramObject);
/* 5935 */       this.insertRow.updateObject(paramInt, paramObject);
/*      */     }
/* 5937 */     else if ((!isBeforeFirst()) && (!isAfterLast()))
/*      */     {
/* 5954 */       this.updateRowFlag = true;
/* 5955 */       this.updateRowPosition = this.presentRow;
/* 5956 */       getCurrentRow().updateObject(paramInt, paramObject);
/*      */     } else {
/* 5958 */       throw new SQLException("Updation not allowed on this column");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateNull(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 5979 */     updateObject(paramInt, null);
/*      */   }
/*      */ 
/*      */   public synchronized void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 6007 */     checkColumnIndex(paramInt1);
/*      */     try
/*      */     {
/* 6010 */       char[] arrayOfChar = new char[paramInt2];
/* 6011 */       int i = 0;
/*      */       do
/*      */       {
/* 6029 */         i += paramReader.read(arrayOfChar, i, paramInt2 - i);
/*      */       }
/* 6031 */       while (i < paramInt2);
/* 6032 */       updateObject(paramInt1, new CharArrayReader(arrayOfChar));
/* 6033 */       arrayOfChar = null;
/*      */     }
/*      */     catch (IOException localIOException) {
/* 6036 */       throw new SQLException("Error while reading the Stream\n" + localIOException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6065 */     updateCharacterStream(getColumnIndex(paramString), paramReader, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 6092 */     updateTimestamp(getColumnIndex(paramString), paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6119 */     updateBinaryStream(getColumnIndex(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public synchronized void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 6149 */       byte[] arrayOfByte = new byte[paramInt2];
/* 6150 */       int i = 0;
/*      */       do
/*      */       {
/* 6168 */         i += paramInputStream.read(arrayOfByte, i, paramInt2 - i);
/*      */       }
/* 6170 */       while (i < paramInt2);
/* 6171 */       updateObject(paramInt1, new ByteArrayInputStream(arrayOfByte));
/* 6172 */       arrayOfByte = null;
/*      */     }
/*      */     catch (IOException localIOException) {
/* 6175 */       throw new SQLException("Error while reading the Stream\n" + localIOException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 6206 */       InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
/* 6207 */       char[] arrayOfChar = new char[paramInt2];
/* 6208 */       int i = 0;
/*      */       do
/*      */       {
/* 6226 */         i += localInputStreamReader.read(arrayOfChar, i, paramInt2 - i);
/*      */       }
/* 6228 */       while (i < paramInt2);
/* 6229 */       updateObject(paramInt1, new CharArrayReader(arrayOfChar));
/* 6230 */       arrayOfChar = null;
/*      */     }
/*      */     catch (IOException localIOException) {
/* 6233 */       throw new SQLException("Error while reading the Stream\n" + localIOException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 6262 */     updateObject(paramInt, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void updateBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 6283 */     updateObject(paramInt, new Boolean(paramBoolean));
/*      */   }
/*      */ 
/*      */   public void updateByte(int paramInt, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 6304 */     updateObject(paramInt, new Byte(paramByte));
/*      */   }
/*      */ 
/*      */   public void updateShort(int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 6325 */     updateObject(paramInt, new Short(paramShort));
/*      */   }
/*      */ 
/*      */   public void updateInt(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 6346 */     updateObject(paramInt1, new Integer(paramInt2));
/*      */   }
/*      */ 
/*      */   public void updateLong(int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 6367 */     updateObject(paramInt, new Long(paramLong));
/*      */   }
/*      */ 
/*      */   public void updateFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 6388 */     updateObject(paramInt, new Float(paramFloat));
/*      */   }
/*      */ 
/*      */   public void updateDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 6409 */     updateObject(paramInt, new Double(paramDouble));
/*      */   }
/*      */ 
/*      */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 6430 */     updateObject(paramInt, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void updateString(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 6451 */     updateObject(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public void updateBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 6473 */     updateObject(paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void updateDate(int paramInt, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 6500 */     updateObject(paramInt, paramDate);
/*      */   }
/*      */ 
/*      */   public void updateTime(int paramInt, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 6527 */     updateObject(paramInt, new Timestamp(0, 0, 0, paramTime.getHours(), paramTime.getMinutes(), paramTime.getSeconds(), 0));
/*      */   }
/*      */ 
/*      */   public void updateObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 6557 */     if (!(paramObject instanceof Number))
/* 6558 */       throw new SQLException("Passed object is not Numeric type");
/* 6559 */     updateObject(paramInt1, new BigDecimal(new BigInteger(((Number)paramObject).toString()), paramInt2));
/*      */   }
/*      */ 
/*      */   public void updateNull(String paramString)
/*      */     throws SQLException
/*      */   {
/* 6587 */     updateNull(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6614 */     updateAsciiStream(getColumnIndex(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateBoolean(String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 6635 */     updateBoolean(getColumnIndex(paramString), paramBoolean);
/*      */   }
/*      */ 
/*      */   public void updateByte(String paramString, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 6656 */     updateByte(getColumnIndex(paramString), paramByte);
/*      */   }
/*      */ 
/*      */   public void updateShort(String paramString, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 6677 */     updateShort(getColumnIndex(paramString), paramShort);
/*      */   }
/*      */ 
/*      */   public void updateInt(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6698 */     updateInt(getColumnIndex(paramString), paramInt);
/*      */   }
/*      */ 
/*      */   public void updateLong(String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 6719 */     updateLong(getColumnIndex(paramString), paramLong);
/*      */   }
/*      */ 
/*      */   public void updateFloat(String paramString, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 6740 */     updateFloat(getColumnIndex(paramString), paramFloat);
/*      */   }
/*      */ 
/*      */   public void updateDouble(String paramString, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 6761 */     updateDouble(getColumnIndex(paramString), paramDouble);
/*      */   }
/*      */ 
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 6782 */     updateBigDecimal(getColumnIndex(paramString), paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void updateString(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 6803 */     updateString(getColumnIndex(paramString1), paramString2);
/*      */   }
/*      */ 
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 6824 */     updateBytes(getColumnIndex(paramString), paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void updateDate(String paramString, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 6851 */     updateDate(getColumnIndex(paramString), paramDate);
/*      */   }
/*      */ 
/*      */   public void updateTime(String paramString, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 6878 */     updateTime(getColumnIndex(paramString), paramTime);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 6906 */     updateObject(getColumnIndex(paramString), paramObject);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6933 */     updateObject(getColumnIndex(paramString), paramObject, paramInt);
/*      */   }
/*      */ 
/*      */   public URL getURL(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 6957 */     Object localObject = getObject(paramInt);
/*      */ 
/* 6959 */     if (localObject == null) {
/* 6960 */       return (URL)localObject;
/*      */     }
/* 6962 */     if ((localObject instanceof URL)) {
/* 6963 */       return (URL)localObject;
/*      */     }
/* 6965 */     throw new SQLException("Invalid column type");
/*      */   }
/*      */ 
/*      */   public URL getURL(String paramString)
/*      */     throws SQLException
/*      */   {
/* 6986 */     return getURL(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public void updateRef(int paramInt, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 7006 */     updateObject(paramInt, paramRef);
/*      */   }
/*      */ 
/*      */   public void updateRef(String paramString, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 7026 */     updateRef(getColumnIndex(paramString), paramRef);
/*      */   }
/*      */ 
/*      */   public void updateBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 7046 */     updateObject(paramInt, paramBlob);
/*      */   }
/*      */ 
/*      */   public void updateBlob(String paramString, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 7066 */     updateBlob(getColumnIndex(paramString), paramBlob);
/*      */   }
/*      */ 
/*      */   public void updateClob(int paramInt, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 7086 */     updateObject(paramInt, paramClob);
/*      */   }
/*      */ 
/*      */   public void updateClob(String paramString, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 7106 */     updateClob(getColumnIndex(paramString), paramClob);
/*      */   }
/*      */ 
/*      */   public void updateArray(int paramInt, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 7126 */     updateObject(paramInt, paramArray);
/*      */   }
/*      */ 
/*      */   public void updateArray(String paramString, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 7146 */     updateArray(getColumnIndex(paramString), paramArray);
/*      */   }
/*      */ 
/*      */   public int[] getKeyColumns()
/*      */     throws SQLException
/*      */   {
/* 7168 */     return this.keyColumns;
/*      */   }
/*      */ 
/*      */   public void setKeyColumns(int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/* 7190 */     int i = 0;
/*      */ 
/* 7192 */     if (this.rowsetMetaData != null)
/*      */     {
/* 7194 */       i = this.rowsetMetaData.getColumnCount();
/* 7195 */       if ((paramArrayOfInt == null) || (paramArrayOfInt.length > i))
/*      */       {
/* 7197 */         throw new SQLException("Invalid number of key columns");
/*      */       }
/*      */     }
/*      */ 
/* 7201 */     int j = paramArrayOfInt.length;
/* 7202 */     this.keyColumns = new int[j];
/*      */ 
/* 7204 */     for (int k = 0; k < j; k++)
/*      */     {
/* 7206 */       if ((paramArrayOfInt[k] <= 0) || (paramArrayOfInt[k] > i))
/*      */       {
/* 7208 */         throw new SQLException("Invalid column index: " + paramArrayOfInt[k]);
/*      */       }
/*      */ 
/* 7211 */       this.keyColumns[k] = paramArrayOfInt[k];
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getPageSize()
/*      */   {
/* 7222 */     return this.pageSize;
/*      */   }
/*      */ 
/*      */   public void setPageSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 7239 */     if ((paramInt < 0) || ((this.maxRows > 0) && (paramInt > this.maxRows)))
/*      */     {
/* 7242 */       throw new SQLException("Invalid page size specified");
/*      */     }
/*      */ 
/* 7245 */     this.pageSize = paramInt;
/*      */   }
/*      */ 
/*      */   public SyncProvider getSyncProvider()
/*      */     throws SQLException
/*      */   {
/* 7263 */     return this.syncProvider;
/*      */   }
/*      */ 
/*      */   public void setSyncProvider(String paramString)
/*      */     throws SQLException
/*      */   {
/* 7278 */     this.syncProvider = SyncFactory.getInstance(paramString);
/* 7279 */     this.reader = this.syncProvider.getRowSetReader();
/* 7280 */     this.writer = this.syncProvider.getRowSetWriter();
/*      */   }
/*      */ 
/*      */   public String getTableName()
/*      */     throws SQLException
/*      */   {
/* 7298 */     return this.tableName;
/*      */   }
/*      */ 
/*      */   public void setTableName(String paramString)
/*      */     throws SQLException
/*      */   {
/* 7314 */     this.tableName = paramString;
/*      */   }
/*      */ 
/*      */   public CachedRowSet createCopyNoConstraints()
/*      */     throws SQLException
/*      */   {
/* 7343 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();
/*      */ 
/* 7347 */     localOracleCachedRowSet.initializeProperties();
/*      */ 
/* 7350 */     localOracleCachedRowSet.listener = new Vector();
/*      */     try
/*      */     {
/* 7354 */       localOracleCachedRowSet.unsetMatchColumn(localOracleCachedRowSet.getMatchColumnIndexes());
/* 7355 */       localOracleCachedRowSet.unsetMatchColumn(localOracleCachedRowSet.getMatchColumnNames());
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */ 
/* 7361 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public CachedRowSet createCopySchema()
/*      */     throws SQLException
/*      */   {
/* 7386 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();
/*      */ 
/* 7389 */     localOracleCachedRowSet.rows = null;
/* 7390 */     localOracleCachedRowSet.rowCount = 0;
/* 7391 */     localOracleCachedRowSet.currentPage = 0;
/*      */ 
/* 7393 */     return localOracleCachedRowSet;
/*      */   }
/*      */ 
/*      */   public boolean columnUpdated(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 7409 */     if (this.insertRowFlag)
/*      */     {
/* 7411 */       throw new SQLException("Trying to mark an inserted row as original");
/*      */     }
/*      */ 
/* 7414 */     return getCurrentRow().isColumnChanged(paramInt);
/*      */   }
/*      */ 
/*      */   public boolean columnUpdated(String paramString)
/*      */     throws SQLException
/*      */   {
/* 7430 */     return columnUpdated(getColumnIndex(paramString));
/*      */   }
/*      */ 
/*      */   public synchronized void execute(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 7459 */     this.connection = paramConnection;
/* 7460 */     execute();
/*      */   }
/*      */ 
/*      */   public void commit()
/*      */     throws SQLException
/*      */   {
/* 7484 */     getConnectionInternal().commit();
/*      */   }
/*      */ 
/*      */   public void rollback()
/*      */     throws SQLException
/*      */   {
/* 7501 */     getConnectionInternal().rollback();
/*      */   }
/*      */ 
/*      */   public void rollback(Savepoint paramSavepoint)
/*      */     throws SQLException
/*      */   {
/* 7524 */     Connection localConnection = getConnectionInternal();
/* 7525 */     boolean bool = localConnection.getAutoCommit();
/*      */     try
/*      */     {
/* 7528 */       localConnection.setAutoCommit(false);
/* 7529 */       localConnection.rollback(paramSavepoint);
/*      */     }
/*      */     finally {
/* 7532 */       localConnection.setAutoCommit(bool);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void oracleRollback(OracleSavepoint paramOracleSavepoint)
/*      */     throws SQLException
/*      */   {
/* 7556 */     ((OracleConnection)getConnectionInternal()).oracleRollback(paramOracleSavepoint);
/*      */   }
/*      */ 
/*      */   public void setOriginalRow()
/*      */     throws SQLException
/*      */   {
/* 7574 */     if (this.insertRowFlag)
/*      */     {
/* 7576 */       throw new SQLException("Invalid operation on this row before insertRow is called");
/*      */     }
/*      */ 
/* 7580 */     setOriginalRowInternal(this.presentRow);
/*      */   }
/*      */ 
/*      */   public int size()
/*      */   {
/* 7591 */     return this.rowCount;
/*      */   }
/*      */ 
/*      */   public boolean nextPage()
/*      */     throws SQLException
/*      */   {
/* 7609 */     if ((this.fetchDirection == 1001) && (this.resultSet != null) && (this.resultSet.getType() == 1003))
/*      */     {
/* 7611 */       throw new SQLException("The underlying ResultSet does not support this operation");
/*      */     }
/* 7613 */     if ((this.rows.size() == 0) && (!this.isPopulateDone)) {
/* 7614 */       throw new SQLException("This operation can not be called without previous paging operations");
/*      */     }
/*      */ 
/* 7617 */     populate(this.resultSet);
/* 7618 */     this.currentPage += 1;
/*      */ 
/* 7620 */     return !this.isPopulateDone;
/*      */   }
/*      */ 
/*      */   public boolean previousPage()
/*      */     throws SQLException
/*      */   {
/* 7638 */     if ((this.resultSet != null) && (this.resultSet.getType() == 1003)) {
/* 7639 */       throw new SQLException("The underlying ResultSet does not support this operation");
/*      */     }
/*      */ 
/* 7642 */     if ((this.rows.size() == 0) && (!this.isPopulateDone)) {
/* 7643 */       throw new SQLException("This operation can not be called without previous paging operations");
/*      */     }
/*      */ 
/* 7647 */     if (this.fetchDirection == 1001)
/*      */     {
/* 7649 */       this.resultSet.relative(this.pageSize * 2);
/*      */     }
/*      */     else
/*      */     {
/* 7653 */       this.resultSet.relative(-2 * this.pageSize);
/*      */     }
/*      */ 
/* 7656 */     populate(this.resultSet);
/*      */ 
/* 7658 */     if (this.currentPage > 0)
/*      */     {
/* 7660 */       this.currentPage -= 1;
/*      */     }
/*      */ 
/* 7663 */     return this.currentPage != 0;
/*      */   }
/*      */ 
/*      */   public void rowSetPopulated(RowSetEvent paramRowSetEvent, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 7669 */     if ((paramInt <= 0) || (paramInt < this.fetchSize))
/*      */     {
/* 7671 */       throw new SQLException("Invalid number of row parameter specified");
/*      */     }
/*      */ 
/* 7674 */     if (this.rowCount % paramInt == 0)
/*      */     {
/* 7676 */       this.rowsetEvent = new RowSetEvent(this);
/* 7677 */       notifyRowSetChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   public RowSetWarning getRowSetWarnings()
/*      */     throws SQLException
/*      */   {
/* 7688 */     return this.rowsetWarning;
/*      */   }
/*      */ 
/*      */   public void populate(ResultSet paramResultSet, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 7696 */     if (paramInt < 0) {
/* 7697 */       throw new SQLException("The start position should not be negative");
/*      */     }
/* 7699 */     if (paramResultSet == null) {
/* 7700 */       throw new SQLException("Null ResultSet supplied to populate");
/*      */     }
/* 7702 */     int i = paramResultSet.getType();
/*      */ 
/* 7704 */     if (i == 1003)
/*      */     {
/* 7706 */       int j = 0;
/* 7707 */       while ((paramResultSet.next()) && (j < paramInt))
/*      */       {
/* 7709 */         j++;
/*      */       }
/*      */ 
/* 7712 */       if (j < paramInt)
/* 7713 */         throw new SQLException("Too few rows to start populating at this position");
/*      */     }
/*      */     else
/*      */     {
/* 7717 */       paramResultSet.absolute(paramInt);
/*      */     }
/*      */ 
/* 7720 */     populate(paramResultSet);
/*      */   }
/*      */ 
/*      */   public void undoDelete()
/*      */     throws SQLException
/*      */   {
/* 7726 */     cancelRowDelete();
/*      */   }
/*      */ 
/*      */   public void undoInsert()
/*      */     throws SQLException
/*      */   {
/* 7732 */     cancelRowInsert();
/*      */   }
/*      */ 
/*      */   public void undoUpdate()
/*      */     throws SQLException
/*      */   {
/* 7738 */     cancelRowUpdates();
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleCachedRowSet
 * JD-Core Version:    0.6.0
 */