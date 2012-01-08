/*      */ package oracle.jdbc.rowset;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
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
/*      */ import java.util.Map;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.naming.NamingException;
/*      */ import javax.sql.DataSource;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.rowset.JdbcRowSet;
/*      */ import javax.sql.rowset.RowSetWarning;
/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.jdbc.OracleResultSet;
/*      */ import oracle.jdbc.OracleSavepoint;
/*      */ import oracle.jdbc.driver.OracleDriver;
/*      */ 
/*      */ public class OracleJDBCRowSet extends OracleRowSet
/*      */   implements RowSet, JdbcRowSet
/*      */ {
/*      */   private Connection connection;
/*      */   private static boolean driverManagerInitialized;
/*      */   private PreparedStatement preparedStatement;
/*      */   private ResultSet resultSet;
/*      */ 
/*      */   public OracleJDBCRowSet()
/*      */     throws SQLException
/*      */   {
/*  199 */     driverManagerInitialized = false;
/*      */   }
/*      */ 
/*      */   public OracleJDBCRowSet(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  213 */     this();
/*  214 */     this.connection = paramConnection;
/*      */   }
/*      */ 
/*      */   public void execute()
/*      */     throws SQLException
/*      */   {
/*  234 */     this.connection = getConnection(this);
/*      */ 
/*  250 */     this.connection.setTransactionIsolation(getTransactionIsolation());
/*  251 */     this.connection.setTypeMap(getTypeMap());
/*      */ 
/*  253 */     if (this.preparedStatement == null) {
/*  254 */       this.preparedStatement = this.connection.prepareStatement(getCommand(), getType(), getConcurrency());
/*      */     }
/*  256 */     this.preparedStatement.setFetchSize(getFetchSize());
/*  257 */     this.preparedStatement.setFetchDirection(getFetchDirection());
/*  258 */     this.preparedStatement.setMaxFieldSize(getMaxFieldSize());
/*  259 */     this.preparedStatement.setMaxRows(getMaxRows());
/*  260 */     this.preparedStatement.setQueryTimeout(getQueryTimeout());
/*  261 */     this.preparedStatement.setEscapeProcessing(getEscapeProcessing());
/*      */ 
/*  278 */     this.resultSet = this.preparedStatement.executeQuery();
/*  279 */     notifyRowSetChanged();
/*      */   }
/*      */ 
/*      */   public void close()
/*      */     throws SQLException
/*      */   {
/*  307 */     if (this.resultSet != null) {
/*  308 */       this.resultSet.close();
/*      */     }
/*  310 */     if (this.preparedStatement != null) {
/*  311 */       this.preparedStatement.close();
/*      */     }
/*  313 */     if ((this.connection != null) && (!this.connection.isClosed()))
/*      */     {
/*  315 */       this.connection.commit();
/*  316 */       this.connection.close();
/*      */     }
/*  318 */     notifyRowSetChanged();
/*      */   }
/*      */ 
/*      */   private Connection getConnection(RowSet paramRowSet)
/*      */     throws SQLException
/*      */   {
/*  347 */     Connection localConnection = null;
/*      */ 
/*  364 */     if ((this.connection != null) && (!this.connection.isClosed()))
/*      */     {
/*  366 */       localConnection = this.connection;
/*      */     }
/*      */     else
/*      */     {
/*      */       Object localObject;
/*  368 */       if (paramRowSet.getDataSourceName() != null)
/*      */       {
/*      */         try
/*      */         {
/*  372 */           InitialContext localInitialContext = new InitialContext();
/*  373 */           localObject = (DataSource)localInitialContext.lookup(paramRowSet.getDataSourceName());
/*      */ 
/*  392 */           if ((paramRowSet.getUsername() == null) || (paramRowSet.getPassword() == null))
/*      */           {
/*  395 */             localConnection = ((DataSource)localObject).getConnection();
/*      */           }
/*      */           else
/*      */           {
/*  399 */             localConnection = ((DataSource)localObject).getConnection(paramRowSet.getUsername(), paramRowSet.getPassword());
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (NamingException localNamingException)
/*      */         {
/*  405 */           throw new SQLException("Unable to connect through the DataSource\n" + localNamingException.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*  409 */       else if (paramRowSet.getUrl() != null)
/*      */       {
/*  411 */         if (!driverManagerInitialized)
/*      */         {
/*  413 */           DriverManager.registerDriver(new OracleDriver());
/*  414 */           driverManagerInitialized = true;
/*      */         }
/*  416 */         String str1 = paramRowSet.getUrl();
/*  417 */         localObject = paramRowSet.getUsername();
/*  418 */         String str2 = paramRowSet.getPassword();
/*      */ 
/*  435 */         if ((str1.equals("")) || (((String)localObject).equals("")) || (str2.equals("")))
/*      */         {
/*  437 */           throw new SQLException("One or more of the authenticating parameter not set");
/*      */         }
/*      */ 
/*  440 */         localConnection = DriverManager.getConnection(str1, (String)localObject, str2);
/*      */       }
/*      */     }
/*  443 */     return (Connection)localConnection;
/*      */   }
/*      */ 
/*      */   public boolean wasNull()
/*      */     throws SQLException
/*      */   {
/*  466 */     return this.resultSet.wasNull();
/*      */   }
/*      */ 
/*      */   public SQLWarning getWarnings()
/*      */     throws SQLException
/*      */   {
/*  486 */     return this.resultSet.getWarnings();
/*      */   }
/*      */ 
/*      */   public void clearWarnings()
/*      */     throws SQLException
/*      */   {
/*  506 */     this.resultSet.clearWarnings();
/*      */   }
/*      */ 
/*      */   public String getCursorName()
/*      */     throws SQLException
/*      */   {
/*  526 */     return this.resultSet.getCursorName();
/*      */   }
/*      */ 
/*      */   public ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/*  546 */     return new OracleRowSetMetaData(this.resultSet.getMetaData());
/*      */   }
/*      */ 
/*      */   public int findColumn(String paramString)
/*      */     throws SQLException
/*      */   {
/*  567 */     return this.resultSet.findColumn(paramString);
/*      */   }
/*      */ 
/*      */   public void clearParameters()
/*      */     throws SQLException
/*      */   {
/*  587 */     this.preparedStatement.clearParameters();
/*      */   }
/*      */ 
/*      */   public Statement getStatement()
/*      */     throws SQLException
/*      */   {
/*  612 */     return this.resultSet.getStatement();
/*      */   }
/*      */ 
/*      */   public void setCommand(String paramString)
/*      */     throws SQLException
/*      */   {
/*  640 */     super.setCommand(paramString);
/*      */ 
/*  642 */     if ((this.connection == null) || (this.connection.isClosed())) {
/*  643 */       this.connection = getConnection(this);
/*      */     }
/*  645 */     if (this.preparedStatement != null)
/*      */     {
/*      */       try
/*      */       {
/*  649 */         this.preparedStatement.close();
/*  650 */         this.preparedStatement = null;
/*      */       } catch (SQLException localSQLException) {
/*      */       }
/*      */     }
/*  654 */     this.preparedStatement = this.connection.prepareStatement(paramString, getType(), getConcurrency());
/*      */   }
/*      */ 
/*      */   public void setReadOnly(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  676 */     super.setReadOnly(paramBoolean);
/*      */ 
/*  678 */     if (this.connection != null)
/*      */     {
/*  680 */       this.connection.setReadOnly(paramBoolean);
/*      */     }
/*      */     else
/*      */     {
/*  684 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setFetchDirection(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  706 */     super.setFetchDirection(paramInt);
/*      */ 
/*  708 */     this.resultSet.setFetchDirection(this.fetchDirection);
/*      */   }
/*      */ 
/*      */   public void setShowDeleted(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  728 */     if (paramBoolean)
/*      */     {
/*  730 */       throw new SQLException("This JdbcRowSet implementation does not allow deleted rows to be visible");
/*      */     }
/*      */ 
/*  734 */     super.setShowDeleted(paramBoolean);
/*      */   }
/*      */ 
/*      */   public boolean next()
/*      */     throws SQLException
/*      */   {
/*  761 */     boolean bool = this.resultSet.next();
/*      */ 
/*  777 */     if (bool)
/*  778 */       notifyCursorMoved();
/*  779 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean previous()
/*      */     throws SQLException
/*      */   {
/*  799 */     boolean bool = this.resultSet.previous();
/*      */ 
/*  815 */     if (bool)
/*  816 */       notifyCursorMoved();
/*  817 */     return bool;
/*      */   }
/*      */ 
/*      */   public void beforeFirst()
/*      */     throws SQLException
/*      */   {
/*  837 */     if (!isBeforeFirst())
/*      */     {
/*  839 */       this.resultSet.beforeFirst();
/*  840 */       notifyCursorMoved();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void afterLast()
/*      */     throws SQLException
/*      */   {
/*  861 */     if (!isAfterLast())
/*      */     {
/*  863 */       this.resultSet.afterLast();
/*  864 */       notifyCursorMoved();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean first()
/*      */     throws SQLException
/*      */   {
/*  885 */     boolean bool = this.resultSet.first();
/*      */ 
/*  901 */     if (bool)
/*  902 */       notifyCursorMoved();
/*  903 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean last()
/*      */     throws SQLException
/*      */   {
/*  923 */     boolean bool = this.resultSet.last();
/*      */ 
/*  939 */     if (bool)
/*  940 */       notifyCursorMoved();
/*  941 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean absolute(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  963 */     boolean bool = this.resultSet.absolute(paramInt);
/*      */ 
/*  980 */     if (bool)
/*  981 */       notifyCursorMoved();
/*  982 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean relative(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1003 */     boolean bool = this.resultSet.relative(paramInt);
/*      */ 
/* 1020 */     if (bool)
/* 1021 */       notifyCursorMoved();
/* 1022 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean isBeforeFirst()
/*      */     throws SQLException
/*      */   {
/* 1042 */     return this.resultSet.isBeforeFirst();
/*      */   }
/*      */ 
/*      */   public boolean isAfterLast()
/*      */     throws SQLException
/*      */   {
/* 1062 */     return this.resultSet.isAfterLast();
/*      */   }
/*      */ 
/*      */   public boolean isFirst()
/*      */     throws SQLException
/*      */   {
/* 1082 */     return this.resultSet.isFirst();
/*      */   }
/*      */ 
/*      */   public boolean isLast()
/*      */     throws SQLException
/*      */   {
/* 1102 */     return this.resultSet.isLast();
/*      */   }
/*      */ 
/*      */   public void insertRow()
/*      */     throws SQLException
/*      */   {
/* 1127 */     this.resultSet.insertRow();
/* 1128 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void updateRow()
/*      */     throws SQLException
/*      */   {
/* 1148 */     this.resultSet.updateRow();
/* 1149 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void deleteRow()
/*      */     throws SQLException
/*      */   {
/* 1169 */     this.resultSet.deleteRow();
/* 1170 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void refreshRow()
/*      */     throws SQLException
/*      */   {
/* 1190 */     this.resultSet.refreshRow();
/*      */   }
/*      */ 
/*      */   public void cancelRowUpdates()
/*      */     throws SQLException
/*      */   {
/* 1210 */     this.resultSet.cancelRowUpdates();
/* 1211 */     notifyRowChanged();
/*      */   }
/*      */ 
/*      */   public void moveToInsertRow()
/*      */     throws SQLException
/*      */   {
/* 1231 */     this.resultSet.moveToInsertRow();
/*      */   }
/*      */ 
/*      */   public void moveToCurrentRow()
/*      */     throws SQLException
/*      */   {
/* 1251 */     this.resultSet.moveToCurrentRow();
/*      */   }
/*      */ 
/*      */   public int getRow()
/*      */     throws SQLException
/*      */   {
/* 1271 */     return this.resultSet.getRow();
/*      */   }
/*      */ 
/*      */   public boolean rowUpdated()
/*      */     throws SQLException
/*      */   {
/* 1291 */     return this.resultSet.rowUpdated();
/*      */   }
/*      */ 
/*      */   public boolean rowInserted()
/*      */     throws SQLException
/*      */   {
/* 1311 */     return this.resultSet.rowInserted();
/*      */   }
/*      */ 
/*      */   public boolean rowDeleted()
/*      */     throws SQLException
/*      */   {
/* 1331 */     return this.resultSet.rowDeleted();
/*      */   }
/*      */ 
/*      */   public void setNull(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1359 */     this.preparedStatement.setNull(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setNull(int paramInt1, int paramInt2, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1380 */     this.preparedStatement.setNull(paramInt1, paramInt2, paramString);
/*      */   }
/*      */ 
/*      */   public void setBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1401 */     this.preparedStatement.setBoolean(paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */   public void setByte(int paramInt, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 1422 */     this.preparedStatement.setByte(paramInt, paramByte);
/*      */   }
/*      */ 
/*      */   public void setShort(int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 1443 */     this.preparedStatement.setShort(paramInt, paramShort);
/*      */   }
/*      */ 
/*      */   public void setInt(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1464 */     this.preparedStatement.setInt(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setLong(int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1485 */     this.preparedStatement.setLong(paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public void setFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 1506 */     this.preparedStatement.setFloat(paramInt, paramFloat);
/*      */   }
/*      */ 
/*      */   public void setDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 1527 */     this.preparedStatement.setDouble(paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 1548 */     this.preparedStatement.setBigDecimal(paramInt, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void setString(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1569 */     this.preparedStatement.setString(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public void setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1597 */     this.preparedStatement.setBytes(paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void setDate(int paramInt, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 1624 */     this.preparedStatement.setDate(paramInt, paramDate);
/*      */   }
/*      */ 
/*      */   public void setTime(int paramInt, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 1651 */     this.preparedStatement.setTime(paramInt, paramTime);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 1678 */     this.preparedStatement.setObject(paramInt, paramObject);
/*      */   }
/*      */ 
/*      */   public void setRef(int paramInt, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 1705 */     this.preparedStatement.setRef(paramInt, paramRef);
/*      */   }
/*      */ 
/*      */   public void setBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 1732 */     this.preparedStatement.setBlob(paramInt, paramBlob);
/*      */   }
/*      */ 
/*      */   public void setClob(int paramInt, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 1759 */     this.preparedStatement.setClob(paramInt, paramClob);
/*      */   }
/*      */ 
/*      */   public void setArray(int paramInt, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 1780 */     this.preparedStatement.setArray(paramInt, paramArray);
/*      */   }
/*      */ 
/*      */   public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1808 */     this.preparedStatement.setBinaryStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1835 */     this.preparedStatement.setTime(paramInt, paramTime, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1864 */     this.preparedStatement.setTimestamp(paramInt, paramTimestamp, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 1885 */     this.preparedStatement.setTimestamp(paramInt, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1913 */     this.preparedStatement.setAsciiStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1935 */     this.preparedStatement.setCharacterStream(paramInt1, paramReader, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 1962 */     this.preparedStatement.setObject(paramInt1, paramObject, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1989 */     this.preparedStatement.setObject(paramInt1, paramObject, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2016 */     this.preparedStatement.setDate(paramInt, paramDate, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Object getObject(int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 2047 */     return this.resultSet.getObject(paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2068 */     return this.resultSet.getBigDecimal(paramInt);
/*      */   }
/*      */ 
/*      */   public Ref getRef(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2089 */     return this.resultSet.getRef(paramInt);
/*      */   }
/*      */ 
/*      */   public Blob getBlob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2110 */     return this.resultSet.getBlob(paramInt);
/*      */   }
/*      */ 
/*      */   public Clob getClob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2131 */     return this.resultSet.getClob(paramInt);
/*      */   }
/*      */ 
/*      */   public Array getArray(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2152 */     return this.resultSet.getArray(paramInt);
/*      */   }
/*      */ 
/*      */   public Date getDate(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2179 */     return this.resultSet.getDate(paramInt, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Reader getCharacterStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2200 */     return this.resultSet.getCharacterStream(paramInt);
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2227 */     return this.resultSet.getTime(paramInt, paramCalendar);
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2248 */     return this.resultSet.getBinaryStream(paramInt);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2275 */     return this.resultSet.getTimestamp(paramInt, paramCalendar);
/*      */   }
/*      */ 
/*      */   public String getString(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2295 */     return this.resultSet.getString(paramInt);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2316 */     return this.resultSet.getBoolean(paramInt);
/*      */   }
/*      */ 
/*      */   public byte getByte(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2337 */     return this.resultSet.getByte(paramInt);
/*      */   }
/*      */ 
/*      */   public short getShort(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2358 */     return this.resultSet.getShort(paramInt);
/*      */   }
/*      */ 
/*      */   public long getLong(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2379 */     return this.resultSet.getLong(paramInt);
/*      */   }
/*      */ 
/*      */   public float getFloat(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2400 */     return this.resultSet.getFloat(paramInt);
/*      */   }
/*      */ 
/*      */   public double getDouble(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2421 */     return this.resultSet.getDouble(paramInt);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 2442 */     return this.resultSet.getBigDecimal(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2463 */     return this.resultSet.getBytes(paramInt);
/*      */   }
/*      */ 
/*      */   public Date getDate(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2484 */     return this.resultSet.getDate(paramInt);
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2505 */     return this.resultSet.getTime(paramInt);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2526 */     return this.resultSet.getTimestamp(paramInt);
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2547 */     return this.resultSet.getAsciiStream(paramInt);
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2568 */     return this.resultSet.getUnicodeStream(paramInt);
/*      */   }
/*      */ 
/*      */   public int getInt(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2589 */     return this.resultSet.getInt(paramInt);
/*      */   }
/*      */ 
/*      */   public Object getObject(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2611 */     return this.resultSet.getObject(paramInt);
/*      */   }
/*      */ 
/*      */   public int getInt(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2639 */     return this.resultSet.getInt(paramString);
/*      */   }
/*      */ 
/*      */   public long getLong(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2660 */     return this.resultSet.getLong(paramString);
/*      */   }
/*      */ 
/*      */   public float getFloat(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2681 */     return this.resultSet.getFloat(paramString);
/*      */   }
/*      */ 
/*      */   public double getDouble(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2702 */     return this.resultSet.getDouble(paramString);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2723 */     return this.resultSet.getBigDecimal(paramString, paramInt);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2744 */     return this.resultSet.getBytes(paramString);
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2765 */     return this.resultSet.getDate(paramString);
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2786 */     return this.resultSet.getTime(paramString);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2807 */     return this.resultSet.getTimestamp(paramString);
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2828 */     return this.resultSet.getAsciiStream(paramString);
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2849 */     return this.resultSet.getUnicodeStream(paramString);
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2870 */     return this.resultSet.getObject(paramString);
/*      */   }
/*      */ 
/*      */   public Reader getCharacterStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2891 */     return this.resultSet.getCharacterStream(paramString);
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 2919 */     return this.resultSet.getObject(paramString, paramMap);
/*      */   }
/*      */ 
/*      */   public Ref getRef(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2940 */     return this.resultSet.getRef(paramString);
/*      */   }
/*      */ 
/*      */   public Blob getBlob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2961 */     return this.resultSet.getBlob(paramString);
/*      */   }
/*      */ 
/*      */   public Clob getClob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 2982 */     return this.resultSet.getClob(paramString);
/*      */   }
/*      */ 
/*      */   public Array getArray(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3003 */     return this.resultSet.getArray(paramString);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3024 */     return this.resultSet.getBigDecimal(paramString);
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3051 */     return this.resultSet.getDate(paramString, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3078 */     return this.resultSet.getTime(paramString, paramCalendar);
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3099 */     return this.resultSet.getBinaryStream(paramString);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3126 */     return this.resultSet.getTimestamp(paramString, paramCalendar);
/*      */   }
/*      */ 
/*      */   public String getString(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3146 */     return this.resultSet.getString(paramString);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3167 */     return this.resultSet.getBoolean(paramString);
/*      */   }
/*      */ 
/*      */   public byte getByte(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3188 */     return this.resultSet.getByte(paramString);
/*      */   }
/*      */ 
/*      */   public short getShort(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3209 */     return this.resultSet.getShort(paramString);
/*      */   }
/*      */ 
/*      */   public void updateNull(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3237 */     this.resultSet.updateNull(paramInt);
/*      */   }
/*      */ 
/*      */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3259 */     this.resultSet.updateCharacterStream(paramInt1, paramReader, paramInt2);
/*      */   }
/*      */ 
/*      */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 3280 */     this.resultSet.updateTimestamp(paramInt, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3308 */     this.resultSet.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3336 */     this.resultSet.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public void updateBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 3357 */     this.resultSet.updateBoolean(paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */   public void updateByte(int paramInt, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 3378 */     this.resultSet.updateByte(paramInt, paramByte);
/*      */   }
/*      */ 
/*      */   public void updateShort(int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 3399 */     this.resultSet.updateShort(paramInt, paramShort);
/*      */   }
/*      */ 
/*      */   public void updateInt(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3420 */     this.resultSet.updateInt(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void updateLong(int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 3441 */     this.resultSet.updateLong(paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public void updateFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 3462 */     this.resultSet.updateFloat(paramInt, paramFloat);
/*      */   }
/*      */ 
/*      */   public void updateDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 3483 */     this.resultSet.updateDouble(paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 3504 */     this.resultSet.updateBigDecimal(paramInt, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void updateString(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 3525 */     this.resultSet.updateString(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public void updateBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 3553 */     this.resultSet.updateBytes(paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void updateDate(int paramInt, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 3580 */     this.resultSet.updateDate(paramInt, paramDate);
/*      */   }
/*      */ 
/*      */   public void updateTime(int paramInt, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 3607 */     this.resultSet.updateTime(paramInt, paramTime);
/*      */   }
/*      */ 
/*      */   public void updateObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 3634 */     this.resultSet.updateObject(paramInt, paramObject);
/*      */   }
/*      */ 
/*      */   public void updateObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3661 */     this.resultSet.updateObject(paramInt1, paramObject, paramInt2);
/*      */   }
/*      */ 
/*      */   public void updateNull(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3687 */     this.resultSet.updateNull(paramString);
/*      */   }
/*      */ 
/*      */   public void updateBoolean(String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 3708 */     this.resultSet.updateBoolean(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   public void updateByte(String paramString, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 3729 */     this.resultSet.updateByte(paramString, paramByte);
/*      */   }
/*      */ 
/*      */   public void updateShort(String paramString, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 3750 */     this.resultSet.updateShort(paramString, paramShort);
/*      */   }
/*      */ 
/*      */   public void updateInt(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3771 */     this.resultSet.updateInt(paramString, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateLong(String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 3792 */     this.resultSet.updateLong(paramString, paramLong);
/*      */   }
/*      */ 
/*      */   public void updateFloat(String paramString, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 3813 */     this.resultSet.updateFloat(paramString, paramFloat);
/*      */   }
/*      */ 
/*      */   public void updateDouble(String paramString, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 3834 */     this.resultSet.updateDouble(paramString, paramDouble);
/*      */   }
/*      */ 
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 3855 */     this.resultSet.updateBigDecimal(paramString, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void updateString(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 3876 */     this.resultSet.updateString(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 3904 */     this.resultSet.updateBytes(paramString, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void updateDate(String paramString, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 3931 */     this.resultSet.updateDate(paramString, paramDate);
/*      */   }
/*      */ 
/*      */   public void updateTime(String paramString, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 3958 */     this.resultSet.updateTime(paramString, paramTime);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 3985 */     this.resultSet.updateObject(paramString, paramObject);
/*      */   }
/*      */ 
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4006 */     this.resultSet.updateObject(paramString, paramObject, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4034 */     this.resultSet.updateBinaryStream(paramString, paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4062 */     this.resultSet.updateAsciiStream(paramString, paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 4089 */     this.resultSet.updateTimestamp(paramString, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4117 */     this.resultSet.updateCharacterStream(paramString, paramReader, paramInt);
/*      */   }
/*      */ 
/*      */   public URL getURL(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4141 */     return ((OracleResultSet)this.resultSet).getURL(paramInt);
/*      */   }
/*      */ 
/*      */   public URL getURL(String paramString)
/*      */     throws SQLException
/*      */   {
/* 4162 */     return ((OracleResultSet)this.resultSet).getURL(paramString);
/*      */   }
/*      */ 
/*      */   public void updateRef(int paramInt, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 4182 */     ((OracleResultSet)this.resultSet).updateRef(paramInt, paramRef);
/*      */   }
/*      */ 
/*      */   public void updateRef(String paramString, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 4202 */     ((OracleResultSet)this.resultSet).updateRef(paramString, paramRef);
/*      */   }
/*      */ 
/*      */   public void updateBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 4222 */     ((OracleResultSet)this.resultSet).updateBlob(paramInt, paramBlob);
/*      */   }
/*      */ 
/*      */   public void updateBlob(String paramString, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 4242 */     ((OracleResultSet)this.resultSet).updateBlob(paramString, paramBlob);
/*      */   }
/*      */ 
/*      */   public void updateClob(int paramInt, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 4262 */     ((OracleResultSet)this.resultSet).updateClob(paramInt, paramClob);
/*      */   }
/*      */ 
/*      */   public void updateClob(String paramString, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 4282 */     ((OracleResultSet)this.resultSet).updateClob(paramString, paramClob);
/*      */   }
/*      */ 
/*      */   public void updateArray(int paramInt, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 4302 */     ((OracleResultSet)this.resultSet).updateArray(paramInt, paramArray);
/*      */   }
/*      */ 
/*      */   public void updateArray(String paramString, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 4322 */     ((OracleResultSet)this.resultSet).updateArray(paramString, paramArray);
/*      */   }
/*      */ 
/*      */   public void commit()
/*      */     throws SQLException
/*      */   {
/* 4343 */     if (this.connection != null)
/*      */     {
/* 4345 */       this.connection.commit();
/*      */     }
/*      */     else
/*      */     {
/* 4349 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rollback()
/*      */     throws SQLException
/*      */   {
/* 4368 */     if (this.connection != null)
/*      */     {
/* 4370 */       this.connection.rollback();
/*      */     }
/*      */     else
/*      */     {
/* 4374 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rollback(Savepoint paramSavepoint)
/*      */     throws SQLException
/*      */   {
/* 4399 */     if (this.connection != null)
/*      */     {
/* 4401 */       this.connection.rollback(paramSavepoint);
/*      */     }
/*      */     else
/*      */     {
/* 4405 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void oracleRollback(OracleSavepoint paramOracleSavepoint)
/*      */     throws SQLException
/*      */   {
/* 4430 */     if (this.connection != null)
/*      */     {
/* 4432 */       ((OracleConnection)this.connection).oracleRollback(paramOracleSavepoint);
/*      */     }
/*      */     else
/*      */     {
/* 4436 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getAutoCommit()
/*      */     throws SQLException
/*      */   {
/* 4453 */     if (this.connection != null)
/*      */     {
/* 4455 */       return this.connection.getAutoCommit();
/*      */     }
/*      */ 
/* 4459 */     throw new SQLException("Connection not open");
/*      */   }
/*      */ 
/*      */   public void setAutoCommit(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 4480 */     if (this.connection != null)
/*      */     {
/* 4482 */       this.connection.setAutoCommit(paramBoolean);
/*      */     }
/*      */     else
/*      */     {
/* 4486 */       throw new SQLException("Connection not open");
/*      */     }
/*      */   }
/*      */ 
/*      */   public RowSetWarning getRowSetWarnings()
/*      */     throws SQLException
/*      */   {
/* 4502 */     return null;
/*      */   }
/*      */ 
/*      */   String getTableName()
/*      */     throws SQLException
/*      */   {
/* 4514 */     return getMetaData().getTableName(getMatchColumnIndexes()[0]);
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleJDBCRowSet
 * JD-Core Version:    0.6.0
 */