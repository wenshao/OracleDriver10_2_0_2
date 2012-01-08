/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import javax.transaction.xa.XAException;
/*    */ import javax.transaction.xa.XAResource;
/*    */ import oracle.jdbc.xa.OracleXAResource;
/*    */ import oracle.jdbc.xa.client.OracleXAConnection;
/*    */ 
/*    */ public class T4CXAConnection extends OracleXAConnection
/*    */ {
/*    */   T4CTTIOtxen otxen;
/*    */   T4CTTIOtxse otxse;
/*    */   T4CConnection physicalConnection;
/* 79 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*    */ 
/*    */   public T4CXAConnection(Connection paramConnection)
/*    */     throws XAException
/*    */   {
/* 36 */     super(paramConnection);
/*    */ 
/* 40 */     this.physicalConnection = ((T4CConnection)paramConnection);
/* 41 */     this.xaResource = null;
/*    */   }
/*    */ 
/*    */   public synchronized XAResource getXAResource()
/*    */   {
/*    */     try
/*    */     {
/* 51 */       if (this.xaResource == null)
/*    */       {
/* 53 */         this.xaResource = new T4CXAResource(this.physicalConnection, this, this.isXAResourceTransLoose);
/*    */ 
/* 56 */         if (this.logicalHandle != null)
/*    */         {
/* 60 */           ((OracleXAResource)this.xaResource).setLogicalConnection(this.logicalHandle);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/* 69 */       this.xaResource = null;
/*    */     }
/*    */ 
/* 74 */     return this.xaResource;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CXAConnection
 * JD-Core Version:    0.6.0
 */