/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class OracleTimeoutThreadPerVM extends OracleTimeout
/*     */ {
/*  30 */   private static final OracleTimeoutPollingThread watchdog = new OracleTimeoutPollingThread();
/*     */   private OracleStatement statement;
/*     */   private long interruptAfter;
/*     */   private String name;
/* 202 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   OracleTimeoutThreadPerVM(String paramString)
/*     */   {
/*  49 */     this.name = paramString;
/*  50 */     this.interruptAfter = 9223372036854775807L;
/*     */ 
/*  52 */     watchdog.addTimeout(this);
/*     */   }
/*     */ 
/*     */   void close()
/*     */   {
/*  67 */     watchdog.removeTimeout(this);
/*     */   }
/*     */ 
/*     */   synchronized void setTimeout(long paramLong, OracleStatement paramOracleStatement)
/*     */     throws SQLException
/*     */   {
/*  87 */     if (this.interruptAfter != 9223372036854775807L)
/*     */     {
/*  93 */       DatabaseError.throwSqlException(131);
/*     */     }
/*     */ 
/* 100 */     this.statement = paramOracleStatement;
/* 101 */     this.interruptAfter = (System.currentTimeMillis() + paramLong);
/*     */   }
/*     */ 
/*     */   synchronized void cancelTimeout()
/*     */     throws SQLException
/*     */   {
/* 126 */     this.statement = null;
/* 127 */     this.interruptAfter = 9223372036854775807L;
/*     */   }
/*     */ 
/*     */   void interruptIfAppropriate(long paramLong)
/*     */   {
/* 155 */     if (paramLong > this.interruptAfter)
/*     */     {
/* 157 */       synchronized (this)
/*     */       {
/* 159 */         if (paramLong > this.interruptAfter)
/*     */         {
/* 163 */           if (this.statement.connection.spawnNewThreadToCancel) {
/* 164 */             OracleStatement localOracleStatement = this.statement;
/* 165 */             Thread localThread = new Thread(new Runnable(localOracleStatement) { private final OracleStatement val$s;
/*     */ 
/*     */               public void run() { try { this.val$s.cancel();
/*     */                 }
/*     */                 catch (Throwable localThrowable)
/*     */                 {
/*     */                 }
/*     */               }
/*     */             });
/* 176 */             localThread.setName("interruptIfAppropriate_" + this);
/* 177 */             localThread.setDaemon(true);
/* 178 */             localThread.setPriority(10);
/* 179 */             localThread.start();
/*     */           }
/*     */           else {
/*     */             try {
/* 183 */               this.statement.cancel();
/*     */             }
/*     */             catch (Throwable localThrowable)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 191 */           this.statement = null;
/* 192 */           this.interruptAfter = 9223372036854775807L;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleTimeoutThreadPerVM
 * JD-Core Version:    0.6.0
 */