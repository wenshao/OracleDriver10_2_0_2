/*     */ package oracle.jdbc.connector;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import javax.resource.ResourceException;
/*     */ import javax.resource.spi.EISSystemException;
/*     */ import javax.resource.spi.IllegalStateException;
/*     */ import javax.resource.spi.LocalTransaction;
/*     */ import javax.resource.spi.LocalTransactionException;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ 
/*     */ public class OracleLocalTransaction
/*     */   implements LocalTransaction
/*     */ {
/*  33 */   private OracleManagedConnection managedConnection = null;
/*  34 */   private Connection connection = null;
/*  35 */   boolean isBeginCalled = false;
/*     */   private static final String RAERR_LTXN_COMMIT = "commit without begin";
/*     */   private static final String RAERR_LTXN_ROLLBACK = "rollback without begin";
/* 207 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";
/*     */ 
/*     */   OracleLocalTransaction(OracleManagedConnection paramOracleManagedConnection)
/*     */     throws ResourceException
/*     */   {
/*  50 */     this.managedConnection = paramOracleManagedConnection;
/*  51 */     this.connection = paramOracleManagedConnection.getPhysicalConnection();
/*  52 */     this.isBeginCalled = false;
/*     */   }
/*     */ 
/*     */   public void begin()
/*     */     throws ResourceException
/*     */   {
/*     */     try
/*     */     {
/*  78 */       if (((OracleConnection)this.connection).getTxnMode() == 1)
/*     */       {
/*  81 */         IllegalStateException localIllegalStateException = new IllegalStateException("Could not start a new transaction inside an active transaction");
/*     */ 
/*  84 */         throw localIllegalStateException;
/*     */       }
/*     */ 
/*  87 */       if (this.connection.getAutoCommit()) {
/*  88 */         this.connection.setAutoCommit(false);
/*     */       }
/*  90 */       this.isBeginCalled = true;
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*  97 */       EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());
/*     */ 
/* 100 */       localEISSystemException.setLinkedException(localSQLException);
/*     */ 
/* 102 */       throw localEISSystemException;
/*     */     }
/*     */ 
/* 108 */     this.managedConnection.eventOccurred(2);
/*     */   }
/*     */ 
/*     */   public void commit()
/*     */     throws ResourceException
/*     */   {
/* 129 */     if (!this.isBeginCalled) {
/* 130 */       throw new LocalTransactionException("begin() must be called before commit()", "commit without begin");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 135 */       this.connection.commit();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 142 */       EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());
/*     */ 
/* 145 */       localEISSystemException.setLinkedException(localSQLException);
/*     */ 
/* 147 */       throw localEISSystemException;
/*     */     }
/*     */ 
/* 153 */     this.isBeginCalled = false;
/*     */ 
/* 155 */     this.managedConnection.eventOccurred(3);
/*     */   }
/*     */ 
/*     */   public void rollback()
/*     */     throws ResourceException
/*     */   {
/* 176 */     if (!this.isBeginCalled) {
/* 177 */       throw new LocalTransactionException("begin() must be called before rollback()", "rollback without begin");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 182 */       this.connection.rollback();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 189 */       EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());
/*     */ 
/* 192 */       localEISSystemException.setLinkedException(localSQLException);
/*     */ 
/* 194 */       throw localEISSystemException;
/*     */     }
/*     */ 
/* 200 */     this.isBeginCalled = false;
/*     */ 
/* 202 */     this.managedConnection.eventOccurred(4);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.connector.OracleLocalTransaction
 * JD-Core Version:    0.6.0
 */