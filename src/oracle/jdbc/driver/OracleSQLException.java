/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class OracleSQLException extends SQLException
/*     */ {
/*     */   private Object[] m_parameters;
/* 128 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleSQLException()
/*     */   {
/*  29 */     this(null, null, 0);
/*     */   }
/*     */ 
/*     */   public OracleSQLException(String paramString)
/*     */   {
/*  38 */     this(paramString, null, 0);
/*     */   }
/*     */ 
/*     */   public OracleSQLException(String paramString1, String paramString2)
/*     */   {
/*  48 */     this(paramString1, paramString2, 0);
/*     */   }
/*     */ 
/*     */   public OracleSQLException(String paramString1, String paramString2, int paramInt)
/*     */   {
/*  57 */     this(paramString1, paramString2, paramInt, null);
/*     */   }
/*     */ 
/*     */   public OracleSQLException(String paramString1, String paramString2, int paramInt, Object[] paramArrayOfObject)
/*     */   {
/*  67 */     super(paramString1, paramString2, paramInt);
/*     */ 
/*  72 */     this.m_parameters = paramArrayOfObject;
/*     */   }
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  87 */     if (this.m_parameters == null) {
/*  88 */       this.m_parameters = new Object[0];
/*     */     }
/*     */ 
/*  93 */     return this.m_parameters;
/*     */   }
/*     */ 
/*     */   public int getNumParameters()
/*     */   {
/* 105 */     if (this.m_parameters == null) {
/* 106 */       this.m_parameters = new Object[0];
/*     */     }
/*     */ 
/* 111 */     return this.m_parameters.length;
/*     */   }
/*     */ 
/*     */   public void setParameters(Object[] paramArrayOfObject)
/*     */   {
/* 123 */     this.m_parameters = paramArrayOfObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleSQLException
 * JD-Core Version:    0.6.0
 */