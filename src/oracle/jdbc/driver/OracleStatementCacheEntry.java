/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class OracleStatementCacheEntry
/*     */ {
/*  48 */   protected OracleStatementCacheEntry applicationNext = null;
/*  49 */   protected OracleStatementCacheEntry applicationPrev = null;
/*     */ 
/*  52 */   protected OracleStatementCacheEntry explicitNext = null;
/*  53 */   protected OracleStatementCacheEntry explicitPrev = null;
/*     */ 
/*  56 */   protected OracleStatementCacheEntry implicitNext = null;
/*  57 */   protected OracleStatementCacheEntry implicitPrev = null;
/*     */   boolean onImplicit;
/*     */   String sql;
/*     */   int statementType;
/*     */   int scrollType;
/*     */   OraclePreparedStatement statement;
/* 100 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public void print()
/*     */     throws SQLException
/*     */   {
/*  80 */     System.out.println("Cache entry " + this);
/*  81 */     System.out.println("  Key: " + this.sql + "$$" + this.statementType + "$$" + this.scrollType);
/*     */ 
/*  83 */     System.out.println("  Statement: " + this.statement);
/*  84 */     System.out.println("  onImplicit: " + this.onImplicit);
/*  85 */     System.out.println("  applicationNext: " + this.applicationNext + "  applicationPrev: " + this.applicationPrev);
/*     */ 
/*  87 */     System.out.println("  implicitNext: " + this.implicitNext + "  implicitPrev: " + this.implicitPrev);
/*     */ 
/*  89 */     System.out.println("  explicitNext: " + this.explicitNext + "  explicitPrev: " + this.explicitPrev);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleStatementCacheEntry
 * JD-Core Version:    0.6.0
 */