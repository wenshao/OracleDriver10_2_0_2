/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Properties;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import javax.sql.DataSource;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.RowSetReader;
/*     */ import oracle.jdbc.driver.OracleDriver;
/*     */ 
/*     */ public class OracleCachedRowSetReader
/*     */   implements RowSetReader, Serializable
/*     */ {
/*     */   static final transient int UNICODESTREAM = 273;
/*     */   static final transient int BINARYSTREAM = 546;
/*     */   static final transient int ASCIISTREAM = 819;
/*     */   static final transient int TWO_PARAMETERS = 2;
/*     */   static final transient int THREE_PARAMETERS = 3;
/*  94 */   private static transient boolean driverManagerInitialized = false;
/*     */ 
/*     */   Connection getConnection(RowSetInternal paramRowSetInternal)
/*     */     throws SQLException
/*     */   {
/* 120 */     Object localObject1 = null;
/*     */ 
/* 140 */     Connection localConnection = paramRowSetInternal.getConnection();
/* 141 */     if ((localConnection != null) && (!localConnection.isClosed()))
/*     */     {
/* 143 */       localObject1 = localConnection;
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject2;
/*     */       String str2;
/* 145 */       if (((RowSet)paramRowSetInternal).getDataSourceName() != null)
/*     */       {
/*     */         try
/*     */         {
/* 149 */           InitialContext localInitialContext = null;
/*     */           try
/*     */           {
/* 168 */             Properties localProperties = System.getProperties();
/* 169 */             localInitialContext = new InitialContext(localProperties);
/*     */           }
/*     */           catch (SecurityException localSecurityException) {
/*     */           }
/* 173 */           if (localInitialContext == null)
/* 174 */             localInitialContext = new InitialContext();
/* 175 */           localObject2 = (DataSource)localInitialContext.lookup(((RowSet)paramRowSetInternal).getDataSourceName());
/*     */ 
/* 193 */           str2 = ((RowSet)paramRowSetInternal).getUsername();
/* 194 */           String str3 = ((RowSet)paramRowSetInternal).getPassword();
/* 195 */           if ((str2 == null) && (str3 == null))
/*     */           {
/* 197 */             localObject1 = ((DataSource)localObject2).getConnection();
/*     */           }
/*     */           else
/*     */           {
/* 201 */             localObject1 = ((DataSource)localObject2).getConnection(str2, str3);
/*     */           }
/*     */         }
/*     */         catch (NamingException localNamingException)
/*     */         {
/* 206 */           throw new SQLException("Unable to connect through the DataSource\n" + localNamingException.getMessage());
/*     */         }
/*     */ 
/*     */       }
/* 210 */       else if (((RowSet)paramRowSetInternal).getUrl() != null)
/*     */       {
/* 212 */         if (!driverManagerInitialized)
/*     */         {
/* 214 */           DriverManager.registerDriver(new OracleDriver());
/* 215 */           driverManagerInitialized = true;
/*     */         }
/* 217 */         String str1 = ((RowSet)paramRowSetInternal).getUrl();
/* 218 */         localObject2 = ((RowSet)paramRowSetInternal).getUsername();
/* 219 */         str2 = ((RowSet)paramRowSetInternal).getPassword();
/*     */ 
/* 237 */         if ((str1.equals("")) || (((String)localObject2).equals("")) || (str2.equals("")))
/*     */         {
/* 239 */           throw new SQLException("One or more of the authenticating parameter not set");
/*     */         }
/*     */ 
/* 242 */         localObject1 = DriverManager.getConnection(str1, (String)localObject2, str2);
/*     */       }
/*     */     }
/* 245 */     return (Connection)(Connection)localObject1;
/*     */   }
/*     */ 
/*     */   private void setParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement)
/*     */     throws SQLException
/*     */   {
/* 270 */     for (int i = 0; i < paramArrayOfObject.length; i++)
/*     */     {
/* 272 */       int j = 0;
/*     */       try
/*     */       {
/* 294 */         j = Array.getLength(paramArrayOfObject[i]);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException)
/*     */       {
/* 298 */         paramPreparedStatement.setObject(i + 1, paramArrayOfObject[i]);
/* 299 */         continue;
/*     */       }
/*     */ 
/* 302 */       Object[] arrayOfObject = (Object[])paramArrayOfObject[i];
/*     */ 
/* 319 */       if (j == 2)
/*     */       {
/* 321 */         if (arrayOfObject[0] == null) {
/* 322 */           paramPreparedStatement.setNull(i + 1, ((Integer)arrayOfObject[1]).intValue());
/*     */         }
/* 325 */         else if (((arrayOfObject[0] instanceof Date)) || ((arrayOfObject[0] instanceof Time)) || ((arrayOfObject[0] instanceof Timestamp)))
/*     */         {
/* 329 */           if ((arrayOfObject[1] instanceof Calendar))
/*     */           {
/* 331 */             paramPreparedStatement.setDate(i + 1, (Date)arrayOfObject[0], (Calendar)arrayOfObject[1]);
/*     */           }
/*     */           else
/*     */           {
/* 336 */             throw new SQLException("Unable to deduce param type");
/*     */           }
/*     */ 
/*     */         }
/* 341 */         else if ((arrayOfObject[0] instanceof Reader)) {
/* 342 */           paramPreparedStatement.setCharacterStream(i + 1, (Reader)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */         }
/* 345 */         else if ((arrayOfObject[1] instanceof Integer)) {
/* 346 */           paramPreparedStatement.setObject(i + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 351 */         if (j != 3)
/*     */           continue;
/* 353 */         if (arrayOfObject[0] == null)
/*     */         {
/* 355 */           paramPreparedStatement.setNull(i + 1, ((Integer)arrayOfObject[1]).intValue(), (String)arrayOfObject[2]);
/*     */         }
/* 360 */         else if ((arrayOfObject[0] instanceof InputStream)) {
/* 361 */           switch (((Integer)arrayOfObject[2]).intValue())
/*     */           {
/*     */           case 273:
/* 364 */             paramPreparedStatement.setUnicodeStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */           case 546:
/* 370 */             paramPreparedStatement.setBinaryStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */           case 819:
/* 376 */             paramPreparedStatement.setAsciiStream(i + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */           }
/*     */ 
/* 382 */           throw new SQLException("Unable to deduce parameter type");
/*     */         }
/* 384 */         if (((arrayOfObject[1] instanceof Integer)) && ((arrayOfObject[2] instanceof Integer)))
/*     */         {
/* 387 */           paramPreparedStatement.setObject(i + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue(), ((Integer)arrayOfObject[2]).intValue());
/*     */         }
/*     */         else
/*     */         {
/* 392 */           throw new SQLException("Unable to deduce param type");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void readData(RowSetInternal paramRowSetInternal)
/*     */     throws SQLException
/*     */   {
/* 418 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)paramRowSetInternal;
/*     */ 
/* 420 */     Connection localConnection = getConnection(paramRowSetInternal);
/*     */ 
/* 437 */     if ((localConnection == null) || (localOracleCachedRowSet.getCommand() == null))
/* 438 */       throw new SQLException("Sorry, Could not obtain connection");
/*     */     try
/*     */     {
/* 441 */       localConnection.setTransactionIsolation(localOracleCachedRowSet.getTransactionIsolation());
/*     */     }
/*     */     catch (Exception localException1) {
/*     */     }
/* 445 */     PreparedStatement localPreparedStatement = localConnection.prepareStatement(localOracleCachedRowSet.getCommand());
/*     */ 
/* 463 */     setParams(paramRowSetInternal.getParams(), localPreparedStatement);
/*     */     try
/*     */     {
/* 466 */       localPreparedStatement.setMaxRows(localOracleCachedRowSet.getMaxRows());
/* 467 */       localPreparedStatement.setMaxFieldSize(localOracleCachedRowSet.getMaxFieldSize());
/* 468 */       localPreparedStatement.setEscapeProcessing(localOracleCachedRowSet.getEscapeProcessing());
/* 469 */       localPreparedStatement.setQueryTimeout(localOracleCachedRowSet.getQueryTimeout());
/*     */     } catch (Exception localException2) {
/*     */     }
/* 472 */     ResultSet localResultSet = localPreparedStatement.executeQuery();
/* 473 */     localOracleCachedRowSet.populate(localResultSet);
/* 474 */     localResultSet.close();
/* 475 */     localPreparedStatement.close();
/*     */     try
/*     */     {
/* 478 */       localConnection.commit();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/* 483 */     if (!localOracleCachedRowSet.isConnectionStayingOpen())
/*     */     {
/* 485 */       localConnection.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleCachedRowSetReader
 * JD-Core Version:    0.6.0
 */