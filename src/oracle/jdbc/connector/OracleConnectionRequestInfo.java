/*     */ package oracle.jdbc.connector;
/*     */ 
/*     */ import javax.resource.spi.ConnectionRequestInfo;
/*     */ 
/*     */ public class OracleConnectionRequestInfo
/*     */   implements ConnectionRequestInfo
/*     */ {
/*  24 */   private String user = null;
/*  25 */   private String password = null;
/*     */ 
/* 127 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";
/*     */ 
/*     */   public OracleConnectionRequestInfo(String paramString1, String paramString2)
/*     */   {
/*  38 */     this.user = paramString1;
/*  39 */     this.password = paramString2;
/*     */   }
/*     */ 
/*     */   public String getUser()
/*     */   {
/*  50 */     return this.user;
/*     */   }
/*     */ 
/*     */   public void setUser(String paramString)
/*     */   {
/*  61 */     this.user = paramString;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  72 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String paramString)
/*     */   {
/*  83 */     this.password = paramString;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 116 */     if (!(paramObject instanceof OracleConnectionRequestInfo)) {
/* 117 */       return false;
/*     */     }
/* 119 */     OracleConnectionRequestInfo localOracleConnectionRequestInfo = (OracleConnectionRequestInfo)paramObject;
/*     */ 
/* 122 */     return (this.user == localOracleConnectionRequestInfo.getUser()) && (this.password == localOracleConnectionRequestInfo.getPassword());
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.connector.OracleConnectionRequestInfo
 * JD-Core Version:    0.6.0
 */