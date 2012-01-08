/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import oracle.jdbc.oci.OracleOCIConnection;
/*     */ 
/*     */ class T2CDriverExtension extends OracleDriverExtension
/*     */ {
/*     */   static final int T2C_DEFAULT_BATCHSIZE = 1;
/* 209 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   Connection getConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties)
/*     */     throws SQLException
/*     */   {
/*  59 */     Object localObject = null;
/*     */ 
/*  65 */     if (paramProperties.getProperty("is_connection_pooling") == "true")
/*     */     {
/*  68 */       paramProperties.put("database", paramString4 == null ? "" : paramString4);
/*     */ 
/*  71 */       localObject = new OracleOCIConnection(paramString1, paramString2, paramString3, paramString4, paramProperties, this);
/*     */     }
/*     */     else
/*     */     {
/*  78 */       localObject = new T2CConnection(paramString1, paramString2, paramString3, paramString4, paramProperties, this);
/*     */     }
/*     */ 
/*  84 */     return (Connection)localObject;
/*     */   }
/*     */ 
/*     */   OracleStatement allocateStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 100 */     T2CStatement localT2CStatement = null;
/*     */ 
/* 107 */     localT2CStatement = new T2CStatement((T2CConnection)paramPhysicalConnection, 1, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
/*     */ 
/* 114 */     return localT2CStatement;
/*     */   }
/*     */ 
/*     */   OraclePreparedStatement allocatePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 130 */     T2CPreparedStatement localT2CPreparedStatement = null;
/*     */ 
/* 136 */     localT2CPreparedStatement = new T2CPreparedStatement((T2CConnection)paramPhysicalConnection, paramString, paramPhysicalConnection.defaultBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
/*     */ 
/* 144 */     return localT2CPreparedStatement;
/*     */   }
/*     */ 
/*     */   OracleCallableStatement allocateCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 160 */     T2CCallableStatement localT2CCallableStatement = null;
/*     */ 
/* 162 */     Object localObject = null;
/*     */ 
/* 168 */     localT2CCallableStatement = new T2CCallableStatement((T2CConnection)paramPhysicalConnection, paramString, paramPhysicalConnection.defaultBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
/*     */ 
/* 176 */     return localT2CCallableStatement;
/*     */   }
/*     */ 
/*     */   OracleInputStream createInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
/*     */     throws SQLException
/*     */   {
/* 192 */     T2CInputStream localT2CInputStream = null;
/*     */ 
/* 199 */     localT2CInputStream = new T2CInputStream(paramOracleStatement, paramInt, paramAccessor);
/*     */ 
/* 204 */     return localT2CInputStream;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CDriverExtension
 * JD-Core Version:    0.6.0
 */