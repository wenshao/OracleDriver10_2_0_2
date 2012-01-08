/*     */ package oracle.jdbc.xa;
/*     */ 
/*     */ import javax.transaction.xa.XAException;
/*     */ 
/*     */ public class OracleXAException extends XAException
/*     */ {
/*  27 */   private int xaError = 0;
/*  28 */   private int primary = 0;
/*  29 */   private int secondary = 0;
/*     */ 
/* 268 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public OracleXAException()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleXAException(int paramInt)
/*     */   {
/*  44 */     super(errorConvert(paramInt));
/*     */ 
/*  49 */     this.xaError = errorConvert(paramInt);
/*  50 */     this.primary = (paramInt & 0xFFFF);
/*  51 */     this.secondary = (paramInt >> 16);
/*     */   }
/*     */ 
/*     */   public static int errorConvert(int paramInt)
/*     */   {
/*  64 */     switch (paramInt & 0xFFFF)
/*     */     {
/*     */     case 24756:
/*  68 */       return -4;
/*     */     case 25351:
/*  71 */       return 4;
/*     */     case 24764:
/*  74 */       return 7;
/*     */     case 24765:
/*  77 */       return 6;
/*     */     case 24766:
/*  80 */       return 5;
/*     */     case 24767:
/*  83 */       return 3;
/*     */     case 1033:
/*     */     case 1034:
/*     */     case 1041:
/*     */     case 1089:
/*     */     case 1090:
/*     */     case 3113:
/*     */     case 3114:
/*     */     case 12571:
/*     */     case 17002:
/*     */     case 17410:
/*     */     case 24768:
/* 107 */       return -7;
/*     */     case 24763:
/*     */     case 24769:
/*     */     case 24770:
/*     */     case 24776:
/* 117 */       return -6;
/*     */     case 2091:
/*     */     case 2092:
/*     */     case 24761:
/* 124 */       return 100;
/*     */     }
/*     */ 
/* 127 */     return -3;
/*     */   }
/*     */ 
/*     */   public int getXAError()
/*     */   {
/* 144 */     return this.xaError;
/*     */   }
/*     */ 
/*     */   public int getOracleError()
/*     */   {
/* 160 */     return this.primary;
/*     */   }
/*     */ 
/*     */   public int getOracleSQLError()
/*     */   {
/* 177 */     return this.secondary;
/*     */   }
/*     */ 
/*     */   public static String getXAErrorMessage(int paramInt)
/*     */   {
/* 187 */     switch (paramInt)
/*     */     {
/*     */     case 7:
/* 191 */       return "The transaction branch has been heuristically committed.";
/*     */     case 8:
/* 194 */       return "The transaction branch may have been heuristically completed.";
/*     */     case 5:
/* 197 */       return "The transaction branch has been heuristically committed and rolled back.";
/*     */     case 6:
/* 201 */       return "The transaction branch has been heuristically rolled back.";
/*     */     case 9:
/* 204 */       return "Resumption must occur where suspension occured.";
/*     */     case 100:
/* 207 */       return "The inclusive lower bound oof the rollback codes.";
/*     */     case 101:
/* 210 */       return "Rollback was caused by communication failure.";
/*     */     case 102:
/* 213 */       return "A deadlock was detected.";
/*     */     case 107:
/* 216 */       return "The inclusive upper bound of the rollback error code.";
/*     */     case 103:
/* 219 */       return "A condition that violates the integrity of the resource was detected.";
/*     */     case 104:
/* 223 */       return "The resource manager rolled back the transaction branch for a reason not on this list.";
/*     */     case 105:
/* 227 */       return "A protocol error occured in the resource manager.";
/*     */     case 106:
/* 230 */       return "A transaction branch took too long.";
/*     */     case 3:
/* 233 */       return "The transaction branch has been read-only and has been committed.";
/*     */     case 4:
/* 236 */       return "Routine returned with no effect and may be reissued.";
/*     */     case -2:
/* 239 */       return "Asynchronous operation already outstanding.";
/*     */     case -8:
/* 242 */       return "The XID already exists.";
/*     */     case -5:
/* 245 */       return "Invalid arguments were given.";
/*     */     case -4:
/* 248 */       return "The XID is not valid.";
/*     */     case -9:
/* 251 */       return "The resource manager is doing work outside global transaction.";
/*     */     case -6:
/* 254 */       return "Routine was invoked in an inproper context.";
/*     */     case -3:
/* 257 */       return "A resource manager error has occured in the transaction branch.";
/*     */     case -7:
/* 260 */       return "Resource manager is unavailable.";
/*     */     }
/*     */ 
/* 263 */     return "Internal XA Error";
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.OracleXAException
 * JD-Core Version:    0.6.0
 */