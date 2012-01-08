/*     */ package oracle.jdbc;
/*     */ 
/*     */ public class OracleDriver extends oracle.jdbc.driver.OracleDriver
/*     */ {
/*     */   public static final boolean isDMS()
/*     */   {
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   public static final boolean isInServer()
/*     */   {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   public static final boolean isJDK14()
/*     */   {
/* 328 */     return true;
/*     */   }
/*     */ 
/*     */   public static final boolean isDebug()
/*     */   {
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */   public static final boolean isPrivateDebug()
/*     */   {
/* 343 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleDriver
 * JD-Core Version:    0.6.0
 */