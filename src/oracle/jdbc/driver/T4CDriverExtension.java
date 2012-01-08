/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ class T4CDriverExtension extends OracleDriverExtension
/*     */ {
/* 113 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   Connection getConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties)
/*     */     throws SQLException
/*     */   {
/*  35 */     return new T4CConnection(paramString1, paramString2, paramString3, paramString4, paramProperties, this);
/*     */   }
/*     */ 
/*     */   OracleStatement allocateStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*  51 */     return new T4CStatement(paramPhysicalConnection, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   OraclePreparedStatement allocatePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*  70 */     return new T4CPreparedStatement(paramPhysicalConnection, paramString, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   OracleCallableStatement allocateCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*  90 */     return new T4CCallableStatement(paramPhysicalConnection, paramString, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   OracleInputStream createInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
/*     */     throws SQLException
/*     */   {
/* 108 */     return new T4CInputStream(paramOracleStatement, paramInt, paramAccessor);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CDriverExtension
 * JD-Core Version:    0.6.0
 */