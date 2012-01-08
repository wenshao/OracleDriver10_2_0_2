/*      */ package oracle.jdbc.rowset;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.RowSetEvent;
/*      */ import javax.sql.RowSetListener;
/*      */ import javax.sql.rowset.Joinable;
/*      */ 
/*      */ abstract class OracleRowSet
/*      */   implements Serializable, Cloneable, Joinable
/*      */ {
/*      */   protected String dataSource;
/*      */   protected String dataSourceName;
/*      */   protected String url;
/*      */   protected String username;
/*      */   protected String password;
/*      */   protected Map typeMap;
/*      */   protected int maxFieldSize;
/*      */   protected int maxRows;
/*      */   protected int queryTimeout;
/*      */   protected int fetchSize;
/*      */   protected int transactionIsolation;
/*      */   protected boolean escapeProcessing;
/*      */   protected String command;
/*      */   protected int concurrency;
/*      */   protected boolean readOnly;
/*      */   protected int fetchDirection;
/*      */   protected int rowsetType;
/*      */   protected boolean showDeleted;
/*      */   protected Vector listener;
/*      */   protected RowSetEvent rowsetEvent;
/*      */   protected Vector matchColumnIndexes;
/*      */   protected Vector matchColumnNames;
/*      */ 
/*      */   protected OracleRowSet()
/*      */     throws SQLException
/*      */   {
/*  259 */     initializeProperties();
/*      */ 
/*  264 */     this.matchColumnIndexes = new Vector(10);
/*  265 */     this.matchColumnNames = new Vector(10);
/*      */ 
/*  267 */     this.listener = new Vector();
/*  268 */     this.rowsetEvent = new RowSetEvent((RowSet)this);
/*      */   }
/*      */ 
/*      */   protected void initializeProperties()
/*      */   {
/*  277 */     this.command = null;
/*  278 */     this.concurrency = 1007;
/*  279 */     this.dataSource = null;
/*  280 */     this.dataSourceName = null;
/*      */ 
/*  282 */     this.escapeProcessing = true;
/*  283 */     this.fetchDirection = 1002;
/*  284 */     this.fetchSize = 0;
/*  285 */     this.maxFieldSize = 0;
/*  286 */     this.maxRows = 0;
/*  287 */     this.queryTimeout = 0;
/*  288 */     this.readOnly = true;
/*  289 */     this.showDeleted = false;
/*  290 */     this.transactionIsolation = 2;
/*  291 */     this.rowsetType = 1005;
/*  292 */     this.typeMap = new HashMap();
/*  293 */     this.username = null;
/*  294 */     this.password = null;
/*  295 */     this.url = null;
/*      */   }
/*      */ 
/*      */   public String getCommand()
/*      */   {
/*  321 */     return this.command;
/*      */   }
/*      */ 
/*      */   public int getConcurrency()
/*      */     throws SQLException
/*      */   {
/*  341 */     return this.concurrency;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getDataSource()
/*      */   {
/*  363 */     return this.dataSource;
/*      */   }
/*      */ 
/*      */   public String getDataSourceName()
/*      */   {
/*  382 */     return this.dataSourceName;
/*      */   }
/*      */ 
/*      */   public boolean getEscapeProcessing()
/*      */     throws SQLException
/*      */   {
/*  402 */     return this.escapeProcessing;
/*      */   }
/*      */ 
/*      */   public int getFetchDirection()
/*      */     throws SQLException
/*      */   {
/*  422 */     return this.fetchDirection;
/*      */   }
/*      */ 
/*      */   public int getFetchSize()
/*      */     throws SQLException
/*      */   {
/*  442 */     return this.fetchSize;
/*      */   }
/*      */ 
/*      */   public int getMaxFieldSize()
/*      */     throws SQLException
/*      */   {
/*  462 */     return this.maxFieldSize;
/*      */   }
/*      */ 
/*      */   public int getMaxRows()
/*      */     throws SQLException
/*      */   {
/*  482 */     return this.maxRows;
/*      */   }
/*      */ 
/*      */   public String getPassword()
/*      */   {
/*  501 */     return this.password;
/*      */   }
/*      */ 
/*      */   public int getQueryTimeout()
/*      */     throws SQLException
/*      */   {
/*  521 */     return this.queryTimeout;
/*      */   }
/*      */ 
/*      */   public boolean getReadOnly()
/*      */   {
/*  527 */     return isReadOnly();
/*      */   }
/*      */ 
/*      */   public boolean isReadOnly()
/*      */   {
/*  551 */     return this.readOnly;
/*      */   }
/*      */ 
/*      */   public boolean getShowDeleted()
/*      */   {
/*  570 */     return this.showDeleted;
/*      */   }
/*      */ 
/*      */   public int getTransactionIsolation()
/*      */   {
/*  589 */     return this.transactionIsolation;
/*      */   }
/*      */ 
/*      */   public int getType()
/*      */     throws SQLException
/*      */   {
/*  609 */     return this.rowsetType;
/*      */   }
/*      */ 
/*      */   public Map getTypeMap()
/*      */     throws SQLException
/*      */   {
/*  635 */     return this.typeMap;
/*      */   }
/*      */ 
/*      */   public String getUrl()
/*      */   {
/*  654 */     return this.url;
/*      */   }
/*      */ 
/*      */   public String getUsername()
/*      */   {
/*  673 */     return this.username;
/*      */   }
/*      */ 
/*      */   public void setCommand(String paramString)
/*      */     throws SQLException
/*      */   {
/*  694 */     this.command = paramString;
/*      */   }
/*      */ 
/*      */   public void setConcurrency(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  714 */     if ((paramInt == 1007) || (paramInt == 1008))
/*  715 */       this.concurrency = paramInt;
/*      */     else
/*  717 */       throw new SQLException("Invalid concurrancy mode");
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setDataSource(String paramString)
/*      */   {
/*  743 */     this.dataSource = paramString;
/*      */   }
/*      */ 
/*      */   public void setDataSourceName(String paramString)
/*      */     throws SQLException
/*      */   {
/*  763 */     this.dataSourceName = paramString;
/*      */   }
/*      */ 
/*      */   public void setEscapeProcessing(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  783 */     this.escapeProcessing = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setFetchDirection(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  824 */     this.fetchDirection = paramInt;
/*      */   }
/*      */ 
/*      */   public void setFetchSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  844 */     this.fetchSize = paramInt;
/*      */   }
/*      */ 
/*      */   public void setMaxFieldSize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  865 */     this.maxFieldSize = paramInt;
/*      */   }
/*      */ 
/*      */   public void setMaxRows(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  885 */     this.maxRows = paramInt;
/*      */   }
/*      */ 
/*      */   public void setPassword(String paramString)
/*      */     throws SQLException
/*      */   {
/*  905 */     this.password = paramString;
/*      */   }
/*      */ 
/*      */   public void setQueryTimeout(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  925 */     this.queryTimeout = paramInt;
/*      */   }
/*      */ 
/*      */   public void setReadOnly(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  946 */     this.readOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setShowDeleted(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  966 */     this.showDeleted = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setTransactionIsolation(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  986 */     this.transactionIsolation = paramInt;
/*      */   }
/*      */ 
/*      */   public void setType(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1006 */     if ((paramInt == 1003) || (paramInt == 1004) || (paramInt == 1005))
/*      */     {
/* 1009 */       this.rowsetType = paramInt;
/*      */     }
/* 1011 */     else throw new SQLException("Unknown RowSet type");
/*      */   }
/*      */ 
/*      */   public void setTypeMap(Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1032 */     this.typeMap = paramMap;
/*      */   }
/*      */ 
/*      */   public void setUrl(String paramString)
/*      */   {
/* 1051 */     this.url = paramString;
/*      */   }
/*      */ 
/*      */   public void setUsername(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1071 */     this.username = paramString;
/*      */   }
/*      */ 
/*      */   public void addRowSetListener(RowSetListener paramRowSetListener)
/*      */   {
/* 1102 */     for (int i = 0; i < this.listener.size(); i++)
/* 1103 */       if (this.listener.elementAt(i).equals(paramRowSetListener))
/* 1104 */         return;
/* 1105 */     this.listener.add(paramRowSetListener);
/*      */   }
/*      */ 
/*      */   public void removeRowSetListener(RowSetListener paramRowSetListener)
/*      */   {
/* 1125 */     for (int i = 0; i < this.listener.size(); i++)
/* 1126 */       if (this.listener.elementAt(i).equals(paramRowSetListener))
/* 1127 */         this.listener.remove(i);
/*      */   }
/*      */ 
/*      */   protected synchronized void notifyCursorMoved()
/*      */   {
/* 1151 */     int i = this.listener.size();
/* 1152 */     if (i > 0)
/* 1153 */       for (int j = 0; j < i; j++)
/* 1154 */         ((RowSetListener)this.listener.elementAt(j)).cursorMoved(this.rowsetEvent);
/*      */   }
/*      */ 
/*      */   protected void notifyRowChanged()
/*      */   {
/* 1177 */     int i = this.listener.size();
/* 1178 */     if (i > 0)
/* 1179 */       for (int j = 0; j < i; j++)
/*      */       {
/* 1181 */         ((RowSetListener)this.listener.elementAt(j)).rowChanged(this.rowsetEvent);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void notifyRowSetChanged()
/*      */   {
/* 1205 */     int i = this.listener.size();
/* 1206 */     if (i > 0)
/* 1207 */       for (int j = 0; j < i; j++)
/*      */       {
/* 1209 */         ((RowSetListener)this.listener.elementAt(j)).rowSetChanged(this.rowsetEvent);
/*      */       }
/*      */   }
/*      */ 
/*      */   public int[] getMatchColumnIndexes()
/*      */     throws SQLException
/*      */   {
/* 1234 */     if ((this.matchColumnIndexes.size() == 0) && (this.matchColumnNames.size() == 0)) {
/* 1235 */       throw new SQLException("No match column indexes were set");
/*      */     }
/*      */ 
/* 1240 */     if (this.matchColumnNames.size() > 0)
/*      */     {
/* 1242 */       String[] arrayOfString = getMatchColumnNames();
/* 1243 */       i = arrayOfString.length;
/* 1244 */       arrayOfInt = new int[i];
/*      */ 
/* 1246 */       for (k = 0; k < i; k++)
/*      */       {
/* 1248 */         arrayOfInt[k] = findColumn(arrayOfString[k]);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1253 */     int i = this.matchColumnIndexes.size();
/* 1254 */     int[] arrayOfInt = new int[i];
/* 1255 */     int j = -1;
/*      */ 
/* 1257 */     for (int k = 0; k < i; k++)
/*      */     {
/*      */       try
/*      */       {
/* 1261 */         j = ((Integer)this.matchColumnIndexes.get(k)).intValue();
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1268 */         throw new SQLException("Invalid match column index");
/*      */       }
/*      */ 
/* 1277 */       if (j <= 0)
/*      */       {
/* 1279 */         throw new SQLException("Invalid match column index");
/*      */       }
/*      */ 
/* 1286 */       arrayOfInt[k] = j;
/*      */     }
/*      */ 
/* 1291 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   public String[] getMatchColumnNames()
/*      */     throws SQLException
/*      */   {
/* 1306 */     checkIfMatchColumnNamesSet();
/*      */ 
/* 1308 */     int i = this.matchColumnNames.size();
/* 1309 */     String[] arrayOfString = new String[i];
/* 1310 */     String str = null;
/*      */ 
/* 1312 */     for (int j = 0; j < i; j++)
/*      */     {
/*      */       try
/*      */       {
/* 1316 */         str = (String)this.matchColumnNames.get(j);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1323 */         throw new SQLException("Invalid match column name");
/*      */       }
/*      */ 
/* 1332 */       if ((str == null) || (str.equals("")))
/*      */       {
/* 1334 */         throw new SQLException("Invalid match column name");
/*      */       }
/*      */ 
/* 1341 */       arrayOfString[j] = str;
/*      */     }
/*      */ 
/* 1345 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */   public void setMatchColumn(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1360 */     if (paramInt <= 0)
/*      */     {
/* 1362 */       throw new SQLException("The match column index should be greater than 0");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1368 */       this.matchColumnIndexes.clear();
/* 1369 */       this.matchColumnNames.clear();
/*      */ 
/* 1371 */       this.matchColumnIndexes.add(0, new Integer(paramInt));
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1378 */       throw new SQLException("The match column index could not be set");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatchColumn(int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/* 1398 */     this.matchColumnIndexes.clear();
/* 1399 */     this.matchColumnNames.clear();
/*      */ 
/* 1401 */     if (paramArrayOfInt == null)
/*      */     {
/* 1403 */       throw new SQLException("The match column parameter is null");
/*      */     }
/*      */ 
/* 1406 */     for (int i = 0; i < paramArrayOfInt.length; i++)
/*      */     {
/* 1408 */       if (paramArrayOfInt[i] <= 0)
/*      */       {
/* 1410 */         throw new SQLException("The match column index should be greater than 0");
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1415 */         this.matchColumnIndexes.add(i, new Integer(paramArrayOfInt[i]));
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1422 */         throw new SQLException("The match column index could not be set");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatchColumn(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1443 */     if ((paramString == null) || (paramString.equals("")))
/*      */     {
/* 1445 */       throw new SQLException("The match column name should be non-empty");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1451 */       this.matchColumnIndexes.clear();
/* 1452 */       this.matchColumnNames.clear();
/*      */ 
/* 1454 */       this.matchColumnNames.add(0, paramString.trim());
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1461 */       throw new SQLException("The match column name could not be set");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setMatchColumn(String[] paramArrayOfString)
/*      */     throws SQLException
/*      */   {
/* 1482 */     this.matchColumnIndexes.clear();
/* 1483 */     this.matchColumnNames.clear();
/*      */ 
/* 1485 */     for (int i = 0; i < paramArrayOfString.length; i++)
/*      */     {
/* 1487 */       if ((paramArrayOfString[i] == null) || (paramArrayOfString[i].equals("")))
/*      */       {
/* 1489 */         throw new SQLException("The match column name should be non-empty");
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1494 */         this.matchColumnNames.add(i, paramArrayOfString[i].trim());
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1501 */         throw new SQLException("The match column name could not be set");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void unsetMatchColumn(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1522 */     checkIfMatchColumnIndexesSet();
/*      */ 
/* 1524 */     if (paramInt <= 0)
/*      */     {
/* 1526 */       throw new SQLException("The match column index should be greater than 0");
/*      */     }
/*      */ 
/* 1529 */     int i = -1;
/*      */     try
/*      */     {
/* 1533 */       i = ((Integer)this.matchColumnIndexes.get(0)).intValue();
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1540 */       throw new SQLException("No match column indexes were set");
/*      */     }
/*      */ 
/* 1546 */     if (i != paramInt)
/*      */     {
/* 1548 */       throw new SQLException("The column index being unset has not been set");
/*      */     }
/*      */ 
/* 1552 */     this.matchColumnIndexes.clear();
/* 1553 */     this.matchColumnNames.clear();
/*      */   }
/*      */ 
/*      */   public void unsetMatchColumn(int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/* 1568 */     checkIfMatchColumnIndexesSet();
/*      */ 
/* 1570 */     if (paramArrayOfInt == null)
/*      */     {
/* 1572 */       throw new SQLException("The match column parameter is null");
/*      */     }
/*      */ 
/* 1575 */     int i = -1;
/*      */ 
/* 1577 */     for (int j = 0; j < paramArrayOfInt.length; j++)
/*      */     {
/* 1579 */       if (paramArrayOfInt[j] <= 0)
/*      */       {
/* 1581 */         throw new SQLException("The match column index should be greater than 0");
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1586 */         i = ((Integer)this.matchColumnIndexes.get(j)).intValue();
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1593 */         throw new SQLException("No match column indexes were set");
/*      */       }
/*      */ 
/* 1599 */       if (i == paramArrayOfInt[j])
/*      */         continue;
/* 1601 */       throw new SQLException("The column index being unset has not been set");
/*      */     }
/*      */ 
/* 1606 */     this.matchColumnIndexes.clear();
/* 1607 */     this.matchColumnNames.clear();
/*      */   }
/*      */ 
/*      */   public void unsetMatchColumn(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1623 */     checkIfMatchColumnNamesSet();
/*      */ 
/* 1625 */     if ((paramString == null) || (paramString.equals("")))
/*      */     {
/* 1627 */       throw new SQLException("The match column name should be non-empty");
/*      */     }
/*      */ 
/* 1630 */     String str = null;
/*      */     try
/*      */     {
/* 1634 */       str = (String)this.matchColumnNames.get(0);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1641 */       throw new SQLException("No match column names were set");
/*      */     }
/*      */ 
/* 1648 */     if (!str.equals(paramString.trim()))
/*      */     {
/* 1650 */       throw new SQLException("The column name being unset has not been set");
/*      */     }
/*      */ 
/* 1654 */     this.matchColumnIndexes.clear();
/* 1655 */     this.matchColumnNames.clear();
/*      */   }
/*      */ 
/*      */   public void unsetMatchColumn(String[] paramArrayOfString)
/*      */     throws SQLException
/*      */   {
/* 1670 */     checkIfMatchColumnNamesSet();
/*      */ 
/* 1672 */     if (paramArrayOfString == null)
/*      */     {
/* 1674 */       throw new SQLException("The match column parameter is null");
/*      */     }
/*      */ 
/* 1677 */     String str = null;
/*      */ 
/* 1679 */     for (int i = 0; i < paramArrayOfString.length; i++)
/*      */     {
/* 1681 */       if ((paramArrayOfString[i] == null) || (paramArrayOfString[i].equals("")))
/*      */       {
/* 1683 */         throw new SQLException("The match column name should be non-empty");
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1688 */         str = (String)this.matchColumnNames.get(i);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1695 */         throw new SQLException("No match column names were set");
/*      */       }
/*      */ 
/* 1702 */       if (str.equals(paramArrayOfString[i]))
/*      */         continue;
/* 1704 */       throw new SQLException("The column name being unset has not been set");
/*      */     }
/*      */ 
/* 1709 */     this.matchColumnIndexes.clear();
/* 1710 */     this.matchColumnNames.clear();
/*      */   }
/*      */ 
/*      */   protected void checkIfMatchColumnIndexesSet()
/*      */     throws SQLException
/*      */   {
/* 1721 */     if (this.matchColumnIndexes.size() == 0)
/* 1722 */       throw new SQLException("No match column indexes were set");
/*      */   }
/*      */ 
/*      */   protected void checkIfMatchColumnNamesSet()
/*      */     throws SQLException
/*      */   {
/* 1729 */     if (this.matchColumnNames.size() == 0)
/* 1730 */       throw new SQLException("No match column names were set");
/*      */   }
/*      */ 
/*      */   public abstract int findColumn(String paramString)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract ResultSetMetaData getMetaData()
/*      */     throws SQLException;
/*      */ 
/*      */   abstract String getTableName()
/*      */     throws SQLException;
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleRowSet
 * JD-Core Version:    0.6.0
 */