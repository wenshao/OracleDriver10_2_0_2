/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class OracleSavepoint
/*     */   implements oracle.jdbc.OracleSavepoint
/*     */ {
/*     */   private static final int MAX_ID_VALUE = 65535;
/*     */   private static final int INVALID_ID_VALUE = -1;
/*     */   static final int NAMED_SAVEPOINT_TYPE = 2;
/*     */   static final int UNNAMED_SAVEPOINT_TYPE = 1;
/*     */   static final int UNKNOWN_SAVEPOINT_TYPE = 0;
/*  33 */   private static int s_seedId = 0;
/*  34 */   private int m_id = -1;
/*  35 */   private String m_name = null;
/*  36 */   private int m_type = 0;
/*     */ 
/* 124 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   OracleSavepoint()
/*     */   {
/*  46 */     this.m_type = 1;
/*  47 */     this.m_id = getNextId();
/*  48 */     this.m_name = null;
/*     */   }
/*     */ 
/*     */   OracleSavepoint(String paramString)
/*     */     throws SQLException
/*     */   {
/*  60 */     if ((paramString != null) && (paramString.length() != 0) && (!OracleSql.isValidObjectName(paramString)))
/*     */     {
/*  63 */       DatabaseError.throwSqlException(68);
/*     */     }
/*  65 */     this.m_type = 2;
/*  66 */     this.m_name = paramString;
/*  67 */     this.m_id = -1;
/*     */   }
/*     */ 
/*     */   public int getSavepointId()
/*     */     throws SQLException
/*     */   {
/*  82 */     if (this.m_type == 2) {
/*  83 */       DatabaseError.throwSqlException(118);
/*     */     }
/*  85 */     return this.m_id;
/*     */   }
/*     */ 
/*     */   public String getSavepointName()
/*     */     throws SQLException
/*     */   {
/* 100 */     if (this.m_type == 1) {
/* 101 */       DatabaseError.throwSqlException(119);
/*     */     }
/* 103 */     return this.m_name;
/*     */   }
/*     */ 
/*     */   int getType()
/*     */   {
/* 112 */     return this.m_type;
/*     */   }
/*     */ 
/*     */   private synchronized int getNextId()
/*     */   {
/* 117 */     s_seedId = (s_seedId + 1) % 65535;
/*     */ 
/* 119 */     return s_seedId;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleSavepoint
 * JD-Core Version:    0.6.0
 */