/*     */ package oracle.jdbc.pool;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ 
/*     */ class OracleImplicitConnectionCacheThread extends Thread
/*     */ {
/*  31 */   private OracleImplicitConnectionCache implicitCache = null;
/*  32 */   protected boolean timeToLive = true;
/*  33 */   protected boolean isSleeping = false;
/*     */ 
/* 283 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   OracleImplicitConnectionCacheThread(OracleImplicitConnectionCache paramOracleImplicitConnectionCache)
/*     */     throws SQLException
/*     */   {
/*  41 */     this.implicitCache = paramOracleImplicitConnectionCache; } 
/*     */   // ERROR //
/*     */   public void run() { // Byte code:
/*     */     //   0: lconst_0
/*     */     //   1: lstore_1
/*     */     //   2: lconst_0
/*     */     //   3: lstore_3
/*     */     //   4: lconst_0
/*     */     //   5: lstore 5
/*     */     //   7: aload_0
/*     */     //   8: getfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   11: ifeq +161 -> 172
/*     */     //   14: aload_0
/*     */     //   15: getfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   18: ifeq +23 -> 41
/*     */     //   21: aload_0
/*     */     //   22: getfield 2	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:implicitCache	Loracle/jdbc/pool/OracleImplicitConnectionCache;
/*     */     //   25: invokevirtual 5	oracle/jdbc/pool/OracleImplicitConnectionCache:getCacheTimeToLiveTimeout	()I
/*     */     //   28: i2l
/*     */     //   29: dup2
/*     */     //   30: lstore_1
/*     */     //   31: lconst_0
/*     */     //   32: lcmp
/*     */     //   33: ifle +8 -> 41
/*     */     //   36: aload_0
/*     */     //   37: lload_1
/*     */     //   38: invokespecial 6	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:runTimeToLiveTimeout	(J)V
/*     */     //   41: aload_0
/*     */     //   42: getfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   45: ifeq +22 -> 67
/*     */     //   48: aload_0
/*     */     //   49: getfield 2	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:implicitCache	Loracle/jdbc/pool/OracleImplicitConnectionCache;
/*     */     //   52: invokevirtual 7	oracle/jdbc/pool/OracleImplicitConnectionCache:getCacheInactivityTimeout	()I
/*     */     //   55: i2l
/*     */     //   56: dup2
/*     */     //   57: lstore_3
/*     */     //   58: lconst_0
/*     */     //   59: lcmp
/*     */     //   60: ifle +7 -> 67
/*     */     //   63: aload_0
/*     */     //   64: invokespecial 8	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:runInactivityTimeout	()V
/*     */     //   67: aload_0
/*     */     //   68: getfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   71: ifeq +25 -> 96
/*     */     //   74: aload_0
/*     */     //   75: getfield 2	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:implicitCache	Loracle/jdbc/pool/OracleImplicitConnectionCache;
/*     */     //   78: invokevirtual 9	oracle/jdbc/pool/OracleImplicitConnectionCache:getCacheAbandonedTimeout	()I
/*     */     //   81: i2l
/*     */     //   82: dup2
/*     */     //   83: lstore 5
/*     */     //   85: lconst_0
/*     */     //   86: lcmp
/*     */     //   87: ifle +9 -> 96
/*     */     //   90: aload_0
/*     */     //   91: lload 5
/*     */     //   93: invokespecial 10	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:runAbandonedTimeout	(J)V
/*     */     //   96: aload_0
/*     */     //   97: getfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   100: ifeq +33 -> 133
/*     */     //   103: aload_0
/*     */     //   104: iconst_1
/*     */     //   105: putfield 4	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:isSleeping	Z
/*     */     //   108: aload_0
/*     */     //   109: getfield 2	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:implicitCache	Loracle/jdbc/pool/OracleImplicitConnectionCache;
/*     */     //   112: invokevirtual 11	oracle/jdbc/pool/OracleImplicitConnectionCache:getCachePropertyCheckInterval	()I
/*     */     //   115: sipush 1000
/*     */     //   118: imul
/*     */     //   119: i2l
/*     */     //   120: invokestatic 12	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:sleep	(J)V
/*     */     //   123: goto +5 -> 128
/*     */     //   126: astore 7
/*     */     //   128: aload_0
/*     */     //   129: iconst_0
/*     */     //   130: putfield 4	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:isSleeping	Z
/*     */     //   133: aload_0
/*     */     //   134: getfield 2	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:implicitCache	Loracle/jdbc/pool/OracleImplicitConnectionCache;
/*     */     //   137: ifnull +22 -> 159
/*     */     //   140: lload_1
/*     */     //   141: lconst_0
/*     */     //   142: lcmp
/*     */     //   143: ifgt +21 -> 164
/*     */     //   146: lload_3
/*     */     //   147: lconst_0
/*     */     //   148: lcmp
/*     */     //   149: ifgt +15 -> 164
/*     */     //   152: lload 5
/*     */     //   154: lconst_0
/*     */     //   155: lcmp
/*     */     //   156: ifgt +8 -> 164
/*     */     //   159: aload_0
/*     */     //   160: iconst_0
/*     */     //   161: putfield 3	oracle/jdbc/pool/OracleImplicitConnectionCacheThread:timeToLive	Z
/*     */     //   164: goto -157 -> 7
/*     */     //   167: astore 7
/*     */     //   169: goto -162 -> 7
/*     */     //   172: return
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   108	123	126	java/lang/InterruptedException
/*     */     //   14	164	167	java/sql/SQLException } 
/* 123 */   private void runTimeToLiveTimeout(long paramLong) throws SQLException { long l1 = 0L;
/* 124 */     long l2 = 0L;
/*     */ 
/* 130 */     if (this.implicitCache.getNumberOfCheckedOutConnections() > 0)
/*     */     {
/* 132 */       OraclePooledConnection localOraclePooledConnection = null;
/*     */ 
/* 137 */       synchronized (this.implicitCache)
/*     */       {
/* 141 */         Object[] arrayOfObject = this.implicitCache.checkedOutConnectionList.toArray();
/* 142 */         int i = this.implicitCache.checkedOutConnectionList.size();
/*     */ 
/* 144 */         for (int j = 0; j < i; j++)
/*     */         {
/* 146 */           localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[j];
/*     */ 
/* 148 */           Connection localConnection = localOraclePooledConnection.getLogicalHandle();
/*     */ 
/* 150 */           if (localConnection == null)
/*     */             continue;
/* 152 */           l2 = ((OracleConnection)localConnection).getStartTime();
/*     */ 
/* 154 */           l1 = System.currentTimeMillis();
/*     */ 
/* 157 */           if (l1 - l2 <= paramLong * 1000L)
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 165 */             this.implicitCache.closeCheckedOutConnection(localOraclePooledConnection, true);
/*     */           }
/*     */           catch (SQLException localSQLException)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void runInactivityTimeout()
/*     */   {
/*     */     try
/*     */     {
/* 187 */       this.implicitCache.doForEveryCachedConnection(4);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void runAbandonedTimeout(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 206 */     if (this.implicitCache.getNumberOfCheckedOutConnections() > 0)
/*     */     {
/* 208 */       OraclePooledConnection localOraclePooledConnection = null;
/*     */ 
/* 213 */       synchronized (this.implicitCache)
/*     */       {
/* 215 */         Object[] arrayOfObject = this.implicitCache.checkedOutConnectionList.toArray();
/*     */ 
/* 218 */         for (int i = 0; i < arrayOfObject.length; i++)
/*     */         {
/* 220 */           localOraclePooledConnection = (OraclePooledConnection)arrayOfObject[i];
/*     */ 
/* 222 */           OracleConnection localOracleConnection = (OracleConnection)localOraclePooledConnection.getLogicalHandle();
/*     */ 
/* 224 */           if (localOracleConnection == null)
/*     */           {
/*     */             continue;
/*     */           }
/* 228 */           OracleConnectionCacheCallback localOracleConnectionCacheCallback = localOracleConnection.getConnectionCacheCallbackObj();
/*     */ 
/* 231 */           if ((localOracleConnectionCacheCallback != null) && ((localOracleConnection.getConnectionCacheCallbackFlag() == 4) || (localOracleConnection.getConnectionCacheCallbackFlag() == 1)))
/*     */           {
/*     */             try
/*     */             {
/* 238 */               localOracleConnectionCacheCallback.handleAbandonedConnection(localOracleConnection, localOracleConnection.getConnectionCacheCallbackPrivObj());
/*     */             }
/*     */             catch (SQLException localSQLException1)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 252 */             if (localOracleConnection.getHeartbeatNoChangeCount() * this.implicitCache.getCachePropertyCheckInterval() <= paramLong)
/*     */             {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             try
/*     */             {
/* 261 */               this.implicitCache.closeCheckedOutConnection(localOraclePooledConnection, true);
/*     */             }
/*     */             catch (SQLException localSQLException2)
/*     */             {
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleImplicitConnectionCacheThread
 * JD-Core Version:    0.6.0
 */