/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ class OracleTimeoutPollingThread extends Thread
/*     */ {
/*     */   protected static final String threadName = "OracleTimeoutPollingThread";
/*     */   public static final String pollIntervalProperty = "oracle.jdbc.TimeoutPollInterval";
/*     */   public static final String pollIntervalDefault = "1000";
/*     */   private OracleTimeoutThreadPerVM[] knownTimeouts;
/*     */   private int count;
/*     */   private long sleepMillis;
/* 228 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   public OracleTimeoutPollingThread()
/*     */   {
/*  72 */     super("OracleTimeoutPollingThread");
/*     */ 
/*  76 */     setDaemon(true);
/*  77 */     setPriority(10);
/*     */ 
/*  79 */     this.knownTimeouts = new OracleTimeoutThreadPerVM[2];
/*  80 */     this.count = 0;
/*  81 */     this.sleepMillis = Long.parseLong(OracleDriver.getSystemPropertyPollInterval());
/*     */ 
/*  84 */     start();
/*     */   }
/*     */ 
/*     */   public synchronized void addTimeout(OracleTimeoutThreadPerVM paramOracleTimeoutThreadPerVM)
/*     */   {
/*  96 */     int i = 0;
/*     */ 
/*  98 */     if (this.count >= this.knownTimeouts.length)
/*     */     {
/* 103 */       OracleTimeoutThreadPerVM[] arrayOfOracleTimeoutThreadPerVM = new OracleTimeoutThreadPerVM[this.knownTimeouts.length * 4];
/*     */ 
/* 106 */       System.arraycopy(this.knownTimeouts, 0, arrayOfOracleTimeoutThreadPerVM, 0, this.knownTimeouts.length);
/*     */ 
/* 108 */       i = this.knownTimeouts.length;
/* 109 */       this.knownTimeouts = arrayOfOracleTimeoutThreadPerVM;
/*     */     }
/*     */ 
/* 112 */     for (; i < this.knownTimeouts.length; i++)
/*     */     {
/* 114 */       if (this.knownTimeouts[i] != null)
/*     */         continue;
/* 116 */       this.knownTimeouts[i] = paramOracleTimeoutThreadPerVM;
/* 117 */       this.count += 1;
/*     */ 
/* 121 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void removeTimeout(OracleTimeoutThreadPerVM paramOracleTimeoutThreadPerVM)
/*     */   {
/* 135 */     for (int i = 0; i < this.knownTimeouts.length; i++)
/*     */     {
/* 137 */       if (this.knownTimeouts[i] != paramOracleTimeoutThreadPerVM)
/*     */         continue;
/* 139 */       this.knownTimeouts[i] = null;
/* 140 */       this.count -= 1;
/*     */ 
/* 144 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 158 */         Thread.sleep(this.sleepMillis);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*     */       }
/*     */ 
/* 166 */       pollOnce();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void pollOnce()
/*     */   {
/* 205 */     if (this.count > 0)
/*     */     {
/* 207 */       long l = System.currentTimeMillis();
/*     */ 
/* 209 */       for (int i = 0; i < this.knownTimeouts.length; i++)
/*     */       {
/*     */         try
/*     */         {
/* 213 */           if (this.knownTimeouts[i] != null)
/* 214 */             this.knownTimeouts[i].interruptIfAppropriate(l);
/*     */         }
/*     */         catch (NullPointerException localNullPointerException)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleTimeoutPollingThread
 * JD-Core Version:    0.6.0
 */