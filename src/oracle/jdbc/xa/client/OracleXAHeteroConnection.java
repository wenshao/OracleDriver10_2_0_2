/*     */ package oracle.jdbc.xa.client;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import oracle.jdbc.xa.OracleXAResource;
/*     */ 
/*     */ public class OracleXAHeteroConnection extends OracleXAConnection
/*     */ {
/*  37 */   private int rmid = -1;
/*  38 */   private String xaCloseString = null;
/*     */ 
/* 178 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public OracleXAHeteroConnection()
/*     */     throws XAException
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleXAHeteroConnection(Connection paramConnection)
/*     */     throws XAException
/*     */   {
/*  62 */     super(paramConnection);
/*     */   }
/*     */ 
/*     */   public synchronized XAResource getXAResource()
/*     */   {
/*     */     try
/*     */     {
/*  86 */       this.xaResource = new OracleXAHeteroResource(this.physicalConn, this);
/*     */ 
/*  92 */       ((OracleXAHeteroResource)this.xaResource).setRmid(this.rmid);
/*     */ 
/*  94 */       if (this.logicalHandle != null)
/*     */       {
/*  98 */         ((OracleXAResource)this.xaResource).setLogicalConnection(this.logicalHandle);
/*     */       }
/*     */     }
/*     */     catch (XAException localXAException)
/*     */     {
/* 103 */       this.xaResource = null;
/*     */     }
/*     */ 
/* 109 */     return this.xaResource;
/*     */   }
/*     */ 
/*     */   synchronized void setRmid(int paramInt)
/*     */   {
/* 126 */     this.rmid = paramInt;
/*     */   }
/*     */ 
/*     */   synchronized int getRmid()
/*     */   {
/* 141 */     return this.rmid;
/*     */   }
/*     */ 
/*     */   synchronized void setXaCloseString(String paramString)
/*     */   {
/* 158 */     this.xaCloseString = paramString;
/*     */   }
/*     */ 
/*     */   synchronized String getXaCloseString()
/*     */   {
/* 173 */     return this.xaCloseString;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.client.OracleXAHeteroConnection
 * JD-Core Version:    0.6.0
 */