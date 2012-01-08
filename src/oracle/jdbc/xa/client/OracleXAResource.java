/*     */ package oracle.jdbc.xa.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.Xid;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.jdbc.oracore.Util;
/*     */ import oracle.jdbc.xa.OracleXAConnection;
/*     */ import oracle.jdbc.xa.OracleXAException;
/*     */ 
/*     */ public class OracleXAResource extends oracle.jdbc.xa.OracleXAResource
/*     */ {
/*  36 */   private short m_version = 0;
/*     */ 
/*  39 */   private static String xa_start_816 = "begin ? := JAVA_XA.xa_start(?,?,?,?); end;";
/*     */ 
/*  41 */   private static String xa_start_post_816 = "begin ? := JAVA_XA.xa_start_new(?,?,?,?,?); end;";
/*     */ 
/*  45 */   private static String xa_end_816 = "begin ? := JAVA_XA.xa_end(?,?); end;";
/*  46 */   private static String xa_end_post_816 = "begin ? := JAVA_XA.xa_end_new(?,?,?,?); end;";
/*     */ 
/*  49 */   private static String xa_commit_816 = "begin ? := JAVA_XA.xa_commit (?,?,?); end;";
/*     */ 
/*  51 */   private static String xa_commit_post_816 = "begin ? := JAVA_XA.xa_commit_new (?,?,?,?); end;";
/*     */ 
/*  54 */   private static String xa_prepare_816 = "begin ? := JAVA_XA.xa_prepare (?,?); end;";
/*     */ 
/*  56 */   private static String xa_prepare_post_816 = "begin ? := JAVA_XA.xa_prepare_new (?,?,?); end;";
/*     */ 
/*  59 */   private static String xa_rollback_816 = "begin ? := JAVA_XA.xa_rollback (?,?); end;";
/*     */ 
/*  61 */   private static String xa_rollback_post_816 = "begin ? := JAVA_XA.xa_rollback_new (?,?,?); end;";
/*     */ 
/*  64 */   private static String xa_forget_816 = "begin ? := JAVA_XA.xa_forget (?,?); end;";
/*     */ 
/*  66 */   private static String xa_forget_post_816 = "begin ? := JAVA_XA.xa_forget_new (?,?,?); end;";
/*     */ 
/*  74 */   boolean isTransLoose = false;
/*     */ 
/* 927 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public OracleXAResource()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleXAResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection)
/*     */     throws XAException
/*     */   {
/*  90 */     super(paramConnection, paramOracleXAConnection);
/*     */     try
/*     */     {
/*  97 */       this.m_version = ((OracleConnection)paramConnection).getVersionNumber();
/*     */     }
/*     */     catch (SQLException localSQLException) {
/*     */     }
/* 101 */     if (this.m_version < 8170)
/*     */     {
/* 111 */       throw new XAException(-6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/* 153 */     int i = -1;
/* 154 */     Object localObject = null;
/*     */     try
/*     */     {
/* 164 */       if (paramXid == null)
/*     */       {
/* 170 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 174 */       int j = paramInt & 0xFF00;
/*     */ 
/* 176 */       paramInt &= -65281;
/*     */ 
/* 178 */       int k = paramInt & 0x10000 | (this.isTransLoose ? 65536 : 0);
/*     */ 
/* 180 */       paramInt &= -65537;
/*     */ 
/* 187 */       if (((paramInt & 0x8200002) != paramInt) || ((k != 0) && ((k & 0x10000) != 65536)))
/*     */       {
/* 195 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 206 */       if (((j & 0xFF00) != 0) && (j != 256) && (j != 512) && (j != 1024))
/*     */       {
/* 213 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 218 */       if (((paramInt & 0x8200000) != 0) && (((j & 0xFF00) != 0) || ((k & 0x10000) != 0)))
/*     */       {
/* 226 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 230 */       paramInt |= j | k;
/*     */ 
/* 232 */       saveAndAlterAutoCommitModeForGlobalTransaction();
/*     */ 
/* 236 */       i = doStart(paramXid, paramInt);
/*     */ 
/* 244 */       checkError(i);
/*     */ 
/* 248 */       super.push(paramXid);
/*     */     }
/*     */     catch (XAException localXAException)
/*     */     {
/* 254 */       restoreAutoCommitModeForGlobalTransaction();
/*     */ 
/* 256 */       throw localXAException;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int doStart(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/* 267 */     int i = -1;
/* 268 */     CallableStatement localCallableStatement = null;
/*     */     try
/*     */     {
/* 272 */       localCallableStatement = this.connection.prepareCall(xa_start_post_816);
/*     */ 
/* 274 */       localCallableStatement.registerOutParameter(1, 2);
/* 275 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 276 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 277 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/* 278 */       localCallableStatement.setInt(5, this.timeout);
/* 279 */       localCallableStatement.setInt(6, paramInt);
/*     */ 
/* 281 */       localCallableStatement.execute();
/*     */ 
/* 283 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 287 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 297 */       if (i == 0) {
/* 298 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 308 */         if (localCallableStatement != null)
/* 309 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 313 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 316 */     return i;
/*     */   }
/*     */ 
/*     */   public void end(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/* 343 */     int i = -1;
/*     */     try
/*     */     {
/* 350 */       if (paramXid == null)
/*     */       {
/* 356 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 360 */       if ((paramInt != 33554432) && (paramInt != 67108864) && (paramInt != 536870912) && ((paramInt & 0x2) != 2))
/*     */       {
/* 367 */         throw new XAException(-5);
/*     */       }
/*     */ 
/* 371 */       Xid localXid = super.suspendStacked(paramXid, paramInt);
/*     */ 
/* 374 */       super.pop();
/*     */ 
/* 376 */       i = doEnd(paramXid, paramInt);
/*     */ 
/* 379 */       super.resumeStacked(localXid);
/*     */ 
/* 385 */       checkError(i);
/*     */     }
/*     */     finally
/*     */     {
/* 389 */       restoreAutoCommitModeForGlobalTransaction();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int doEnd(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/* 399 */     CallableStatement localCallableStatement = null;
/* 400 */     int i = -1;
/*     */     try
/*     */     {
/* 406 */       localCallableStatement = this.connection.prepareCall(xa_end_post_816);
/*     */ 
/* 408 */       localCallableStatement.registerOutParameter(1, 2);
/* 409 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 410 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 411 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/* 412 */       localCallableStatement.setInt(5, paramInt);
/* 413 */       localCallableStatement.execute();
/*     */ 
/* 415 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 419 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 429 */       if (i == 0) {
/* 430 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 440 */         if (localCallableStatement != null)
/* 441 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 445 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 448 */     return i;
/*     */   }
/*     */ 
/*     */   public void commit(Xid paramXid, boolean paramBoolean)
/*     */     throws XAException
/*     */   {
/* 471 */     int i = -1;
/* 472 */     int j = 0;
/*     */ 
/* 478 */     if (paramXid == null)
/*     */     {
/* 484 */       throw new XAException(-5);
/*     */     }
/*     */     int k;
/* 487 */     if (paramBoolean)
/* 488 */       k = 1;
/*     */     else {
/* 490 */       k = 0;
/*     */     }
/*     */ 
/* 493 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 495 */     i = doCommit(paramXid, k);
/*     */ 
/* 498 */     super.resumeStacked(localXid);
/*     */ 
/* 504 */     checkError(i);
/*     */   }
/*     */ 
/*     */   protected int doCommit(Xid paramXid, int paramInt)
/*     */     throws XAException
/*     */   {
/* 513 */     int i = -1;
/* 514 */     CallableStatement localCallableStatement = null;
/* 515 */     int j = 0;
/*     */     try
/*     */     {
/* 521 */       localCallableStatement = this.connection.prepareCall(xa_commit_post_816);
/*     */ 
/* 523 */       localCallableStatement.registerOutParameter(1, 2);
/* 524 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 525 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 526 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/* 527 */       localCallableStatement.setInt(5, paramInt);
/*     */ 
/* 529 */       localCallableStatement.execute();
/*     */ 
/* 531 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 535 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 545 */       if (i == 0) {
/* 546 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 556 */         if (localCallableStatement != null)
/* 557 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 561 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 564 */     return i;
/*     */   }
/*     */ 
/*     */   public int prepare(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 585 */     int i = 0;
/*     */ 
/* 590 */     if (paramXid == null)
/*     */     {
/* 596 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 600 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 602 */     i = doPrepare(paramXid);
/*     */ 
/* 605 */     super.resumeStacked(localXid);
/*     */ 
/* 611 */     int j = i == 0 ? 0 : OracleXAException.errorConvert(i);
/*     */ 
/* 617 */     if ((j != 0) && (j != 3)) {
/* 618 */       throw new OracleXAException(i);
/*     */     }
/*     */ 
/* 623 */     return j;
/*     */   }
/*     */ 
/*     */   protected int doPrepare(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 629 */     int i = 0;
/* 630 */     int j = 0;
/* 631 */     CallableStatement localCallableStatement = null;
/*     */     try
/*     */     {
/* 637 */       localCallableStatement = this.connection.prepareCall(xa_prepare_post_816);
/*     */ 
/* 639 */       localCallableStatement.registerOutParameter(1, 2);
/* 640 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 641 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 642 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/*     */ 
/* 644 */       localCallableStatement.execute();
/*     */ 
/* 646 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 652 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 662 */       if (i == 0) {
/* 663 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 673 */         if (localCallableStatement != null)
/* 674 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 678 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 681 */     return i;
/*     */   }
/*     */ 
/*     */   public void forget(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 695 */     int i = 0;
/*     */ 
/* 700 */     if (paramXid == null)
/*     */     {
/* 706 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 709 */     i = doForget(paramXid);
/*     */ 
/* 715 */     checkError(i);
/*     */   }
/*     */ 
/*     */   protected int doForget(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 726 */     int i = 0;
/* 727 */     int j = 0;
/* 728 */     CallableStatement localCallableStatement = null;
/*     */     try
/*     */     {
/* 734 */       localCallableStatement = this.connection.prepareCall(xa_forget_post_816);
/*     */ 
/* 736 */       localCallableStatement.registerOutParameter(1, 2);
/* 737 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 738 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 739 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/*     */ 
/* 741 */       localCallableStatement.execute();
/*     */ 
/* 743 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 747 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 757 */       if (i == 0) {
/* 758 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 768 */         if (localCallableStatement != null)
/* 769 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 773 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 776 */     return i;
/*     */   }
/*     */ 
/*     */   public void rollback(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 791 */     int i = 0;
/* 792 */     int j = 0;
/*     */ 
/* 797 */     if (paramXid == null)
/*     */     {
/* 803 */       throw new XAException(-5);
/*     */     }
/*     */ 
/* 807 */     Xid localXid = super.suspendStacked(paramXid);
/*     */ 
/* 809 */     i = doRollback(paramXid);
/*     */ 
/* 812 */     super.resumeStacked(localXid);
/*     */ 
/* 818 */     checkError(i);
/*     */   }
/*     */ 
/*     */   protected int doRollback(Xid paramXid)
/*     */     throws XAException
/*     */   {
/* 829 */     int i = 0;
/* 830 */     int j = 0;
/* 831 */     CallableStatement localCallableStatement = null;
/*     */     try
/*     */     {
/* 837 */       localCallableStatement = this.connection.prepareCall(xa_rollback_post_816);
/*     */ 
/* 839 */       localCallableStatement.registerOutParameter(1, 2);
/* 840 */       localCallableStatement.setInt(2, paramXid.getFormatId());
/* 841 */       localCallableStatement.setBytes(3, paramXid.getGlobalTransactionId());
/* 842 */       localCallableStatement.setBytes(4, paramXid.getBranchQualifier());
/*     */ 
/* 844 */       localCallableStatement.execute();
/*     */ 
/* 846 */       i = localCallableStatement.getInt(1);
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 850 */       i = localSQLException1.getErrorCode();
/*     */ 
/* 860 */       if (i == 0) {
/* 861 */         throw new XAException(-6);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 871 */         if (localCallableStatement != null)
/* 872 */           localCallableStatement.close();
/*     */       }
/*     */       catch (SQLException localSQLException2) {
/*     */       }
/* 876 */       localCallableStatement = null;
/*     */     }
/*     */ 
/* 879 */     return i;
/*     */   }
/*     */ 
/*     */   public void doTwoPhaseAction(int paramInt1, int paramInt2, String[] paramArrayOfString, Xid[] paramArrayOfXid)
/*     */     throws XAException
/*     */   {
/* 885 */     doDoTwoPhaseAction(paramInt1, paramInt2, paramArrayOfString, paramArrayOfXid);
/*     */   }
/*     */ 
/*     */   protected int doDoTwoPhaseAction(int paramInt1, int paramInt2, String[] paramArrayOfString, Xid[] paramArrayOfXid)
/*     */     throws XAException
/*     */   {
/* 891 */     throw new XAException(-6);
/*     */   }
/*     */ 
/*     */   private static byte[] getSerializedBytes(Xid paramXid)
/*     */   {
/*     */     try
/*     */     {
/* 909 */       return Util.serializeObject(paramXid);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */ 
/* 918 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.client.OracleXAResource
 * JD-Core Version:    0.6.0
 */