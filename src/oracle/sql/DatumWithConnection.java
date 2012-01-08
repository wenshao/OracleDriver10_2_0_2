/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.driver.OracleDriver;
/*     */ 
/*     */ public abstract class DatumWithConnection extends Datum
/*     */ {
/*  29 */   private oracle.jdbc.internal.OracleConnection physicalConnection = null;
/*     */ 
/* 212 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   oracle.jdbc.internal.OracleConnection getPhysicalConnection()
/*     */   {
/*  39 */     if (this.physicalConnection == null)
/*     */     {
/*     */       try
/*     */       {
/*  54 */         this.physicalConnection = ((oracle.jdbc.internal.OracleConnection)new OracleDriver().defaultConnection());
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  67 */     return this.physicalConnection;
/*     */   }
/*     */ 
/*     */   public DatumWithConnection(byte[] paramArrayOfByte) throws SQLException
/*     */   {
/*  72 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public DatumWithConnection()
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void assertNotNull(Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/*  82 */     if (paramConnection == null)
/*  83 */       DatabaseError.throwSqlException(68, "Connection is null");
/*     */   }
/*     */ 
/*     */   public static void assertNotNull(TypeDescriptor paramTypeDescriptor)
/*     */     throws SQLException
/*     */   {
/*  89 */     if (paramTypeDescriptor == null)
/*  90 */       DatabaseError.throwSqlException(61);
/*     */   }
/*     */ 
/*     */   public void setPhysicalConnectionOf(Connection paramConnection)
/*     */   {
/* 105 */     this.physicalConnection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
/*     */   }
/*     */ 
/*     */   public Connection getJavaSqlConnection()
/*     */     throws SQLException
/*     */   {
/* 126 */     return getPhysicalConnection().getWrapper();
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.OracleConnection getOracleConnection()
/*     */     throws SQLException
/*     */   {
/* 145 */     return getPhysicalConnection().getWrapper();
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.internal.OracleConnection getInternalConnection()
/*     */     throws SQLException
/*     */   {
/* 167 */     return getPhysicalConnection();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public oracle.jdbc.driver.OracleConnection getConnection()
/*     */     throws SQLException
/*     */   {
/* 191 */     oracle.jdbc.driver.OracleConnection localOracleConnection = null;
/*     */     try
/*     */     {
/* 195 */       localOracleConnection = (oracle.jdbc.driver.OracleConnection)((oracle.jdbc.driver.OracleConnection)this.physicalConnection).getWrapper();
/*     */     }
/*     */     catch (ClassCastException localClassCastException)
/*     */     {
/* 202 */       DatabaseError.throwSqlException(103);
/*     */     }
/*     */ 
/* 207 */     return localOracleConnection;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.DatumWithConnection
 * JD-Core Version:    0.6.0
 */