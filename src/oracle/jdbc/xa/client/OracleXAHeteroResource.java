/*     */ package oracle.jdbc.xa.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.Xid;
/*     */ import oracle.jdbc.oracore.Util;
/*     */ 
/*     */ public class OracleXAHeteroResource extends OracleXAResource
/*     */ {
/*  34 */   private int rmid = -1;
/*     */ 
/* 534 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public OracleXAHeteroResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection)
/*     */     throws XAException
/*     */   {
/*  46 */     this.connection = paramConnection;
/*  47 */     this.xaconnection = paramOracleXAConnection;
/*     */ 
/*  49 */     if (this.connection == null)
/*  50 */       throw new XAException(-7);
/*     */   }
/*     */ 
/*     */   public void start(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/*  88 */     if (paramXid == null)
/*     */     {
/*  94 */       throw new XAException(-5);
/*     */     }
/*     */ 
/*  98 */     int i = paramInt & 0xFF00;
/*     */ 
/* 100 */     paramInt &= -65281;
/*     */ 
/* 103 */     if ((paramInt & 0x8200002) != paramInt)
/*     */     {
/* 109 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 113 */     if (((i & 0xFF00) != 0) && (i != 256) && (i != 512) && (i != 1024))
/*     */     {
/* 120 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 124 */     if (((i & 0xFF00) != 0) && ((paramInt & 0x8200000) != 0))
/*     */     {
/* 131 */       throw new XAException(-5);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 136 */       saveAndAlterAutoCommitModeForGlobalTransaction();
/*     */ 
/* 139 */       paramInt |= i;
/*     */ 
/* 141 */       int j = paramXid.getFormatId();
/* 142 */       byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 143 */       byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 145 */       int k = doXaStart(j, arrayOfByte1, arrayOfByte2, this.rmid, paramInt, 0);
/*     */ 
/* 151 */       checkStatus(k);
/*     */ 
/* 154 */       super.push(paramXid);
/*     */     }
/*     */     catch (XAException localXAException)
/*     */     {
/* 158 */       restoreAutoCommitModeForGlobalTransaction();
/* 159 */       throw localXAException;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void end(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/*     */     try
/*     */     {
/* 197 */       if (paramXid == null)
/*     */       {
/* 203 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 207 */       if ((paramInt != 33554432) && (paramInt != 67108864) && (paramInt != 536870912) && ((paramInt & 0x2) != 2))
/*     */       {
/* 214 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 217 */       int i = paramXid.getFormatId();
/* 218 */       byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 219 */       byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 222 */       Xid localXid = super.suspendStacked(paramXid, paramInt);
/*     */ 
/* 225 */       super.pop();
/*     */ 
/* 227 */       int j = doXaEnd(i, arrayOfByte1, arrayOfByte2, this.rmid, paramInt, 0);
/*     */ 
/* 230 */       super.resumeStacked(localXid);
/*     */ 
/* 236 */       checkStatus(j);
/*     */     }
/*     */     finally
/*     */     {
/* 240 */       restoreAutoCommitModeForGlobalTransaction();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commit(Xid paramXid, boolean paramBoolean)
/*     */     throws XAException
/*     */   {
/* 269 */     if (paramXid == null)
/*     */     {
/* 275 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 278 */     int i = paramBoolean ? 1073741824 : 0;
/*     */ 
/* 280 */     int j = paramXid.getFormatId();
/* 281 */     byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 282 */     byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 285 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 287 */     int k = doXaCommit(j, arrayOfByte1, arrayOfByte2, this.rmid, i, 0);
/*     */ 
/* 290 */     super.resumeStacked(localXid);
/*     */ 
/* 296 */     checkStatus(k);
/*     */   }
/*     */ 
/*     */   public int prepare(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 321 */     if (paramXid == null)
/*     */     {
/* 327 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 330 */     int i = paramXid.getFormatId();
/* 331 */     byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 332 */     byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 335 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 337 */     int j = doXaPrepare(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);
/*     */ 
/* 340 */     super.resumeStacked(localXid);
/*     */ 
/* 346 */     if ((j != 0) && (j != 3))
/*     */     {
/* 348 */       checkStatus(j);
/*     */     }
/*     */ 
/* 354 */     return j;
/*     */   }
/*     */ 
/*     */   public void forget(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 372 */     if (paramXid == null)
/*     */     {
/* 378 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 381 */     int i = paramXid.getFormatId();
/* 382 */     byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 383 */     byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 385 */     int j = doXaForget(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);
/*     */ 
/* 391 */     checkStatus(j);
/*     */   }
/*     */ 
/*     */   public void rollback(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 410 */     if (paramXid == null)
/*     */     {
/* 416 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 419 */     int i = paramXid.getFormatId();
/* 420 */     byte[] arrayOfByte1 = paramXid.getGlobalTransactionId();
/* 421 */     byte[] arrayOfByte2 = paramXid.getBranchQualifier();
/*     */ 
/* 424 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 426 */     int j = doXaRollback(i, arrayOfByte1, arrayOfByte2, this.rmid, 0, 0);
/*     */ 
/* 429 */     super.resumeStacked(localXid);
/*     */ 
/* 435 */     checkStatus(j);
/*     */   }
/*     */ 
/*     */   private native int doXaStart(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native int doXaEnd(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native int doXaCommit(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native int doXaPrepare(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native int doXaForget(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native int doXaRollback(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   synchronized void setRmid(int paramInt)
/*     */   {
/* 473 */     this.rmid = paramInt;
/*     */   }
/*     */ 
/*     */   synchronized int getRmid()
/*     */   {
/* 488 */     return this.rmid;
/*     */   }
/*     */ 
/*     */   private static byte[] getSerializedBytes(Xid paramXid)
/*     */   {
/*     */     try
/*     */     {
/* 506 */       return Util.serializeObject(paramXid);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 512 */       localIOException.printStackTrace();
/*     */     }
/*     */ 
/* 515 */     return null;
/*     */   }
/*     */ 
/*     */   private void checkStatus(int paramInt)
/*     */     throws XAException
/*     */   {
/* 528 */     if (paramInt != 0)
/* 529 */       throw new XAException(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.client.OracleXAHeteroResource
 * JD-Core Version:    0.6.0
 */