/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class OracleParameterMetaData
/*     */   implements oracle.jdbc.OracleParameterMetaData
/*     */ {
/*  26 */   int parameterCount = 0;
/*     */ 
/*  75 */   int parameterNoNulls = 0;
/*     */ 
/*  83 */   int parameterNullable = 1;
/*     */ 
/*  91 */   int parameterNullableUnknown = 2;
/*     */ 
/* 208 */   int parameterModeUnknown = 0;
/*     */ 
/* 215 */   int parameterModeIn = 1;
/*     */ 
/* 222 */   int parameterModeInOut = 2;
/*     */ 
/* 229 */   int parameterModeOut = 4;
/*     */ 
/* 254 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   OracleParameterMetaData(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  30 */     this.parameterCount = paramInt;
/*     */   }
/*     */ 
/*     */   public int getParameterCount()
/*     */     throws SQLException
/*     */   {
/*  46 */     return this.parameterCount;
/*     */   }
/*     */ 
/*     */   public int isNullable(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  64 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/*  66 */     return 2;
/*     */   }
/*     */ 
/*     */   public boolean isSigned(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 105 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPrecision(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 122 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 124 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getScale(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 139 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 141 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getParameterType(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 157 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 159 */     return 9999;
/*     */   }
/*     */ 
/*     */   public String getParameterTypeName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 175 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   public String getParameterClassName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 198 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */   public int getParameterMode(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 247 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */ 
/* 249 */     return -1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleParameterMetaData
 * JD-Core Version:    0.6.0
 */