/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class ResultSetUtil
/*     */ {
/*  25 */   static final int[][] allRsetTypes = { { 0, 0 }, { 1003, 1007 }, { 1003, 1008 }, { 1004, 1007 }, { 1004, 1008 }, { 1005, 1007 }, { 1005, 1008 } };
/*     */ 
/* 233 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   static OracleResultSet createScrollResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSet paramOracleResultSet, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  64 */     switch (paramInt)
/*     */     {
/*     */     case 1:
/*  68 */       return paramOracleResultSet;
/*     */     case 2:
/*  71 */       return new UpdatableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */     case 3:
/*  77 */       return new ScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */     case 4:
/*  83 */       ScrollableResultSet localScrollableResultSet = new ScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */ 
/*  87 */       return new UpdatableResultSet(paramScrollRsetStatement, localScrollableResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */     case 5:
/*  91 */       return new SensitiveScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */     case 6:
/*  97 */       SensitiveScrollableResultSet localSensitiveScrollableResultSet = new SensitiveScrollableResultSet(paramScrollRsetStatement, (OracleResultSetImpl)paramOracleResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */ 
/* 101 */       return new UpdatableResultSet(paramScrollRsetStatement, localSensitiveScrollableResultSet, getScrollType(paramInt), getUpdateConcurrency(paramInt));
/*     */     }
/*     */ 
/* 105 */     DatabaseError.throwSqlException(23, null);
/*     */ 
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   static int getScrollType(int paramInt)
/*     */   {
/* 121 */     return allRsetTypes[paramInt][0];
/*     */   }
/*     */ 
/*     */   static int getUpdateConcurrency(int paramInt)
/*     */   {
/* 133 */     return allRsetTypes[paramInt][1];
/*     */   }
/*     */ 
/*     */   static int getRsetTypeCode(int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 147 */     for (int i = 0; i < allRsetTypes.length; i++)
/*     */     {
/* 149 */       if ((allRsetTypes[i][0] == paramInt1) && (allRsetTypes[i][1] == paramInt2))
/*     */       {
/* 156 */         return i;
/*     */       }
/*     */     }
/*     */ 
/* 160 */     DatabaseError.throwSqlException(68);
/*     */ 
/* 162 */     return 0;
/*     */   }
/*     */ 
/*     */   static boolean needIdentifier(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 176 */     return (paramInt != 1) && (paramInt != 3);
/*     */   }
/*     */ 
/*     */   static boolean needIdentifier(int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 185 */     return needIdentifier(getRsetTypeCode(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */   static boolean needCache(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 198 */     return paramInt >= 3;
/*     */   }
/*     */ 
/*     */   static boolean needCache(int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 207 */     return needCache(getRsetTypeCode(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */   static boolean supportRefreshRow(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 219 */     return paramInt >= 4;
/*     */   }
/*     */ 
/*     */   static boolean supportRefreshRow(int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 228 */     return supportRefreshRow(getRsetTypeCode(paramInt1, paramInt2));
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ResultSetUtil
 * JD-Core Version:    0.6.0
 */