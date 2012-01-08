/*     */ package oracle.jdbc.connector;
/*     */ 
/*     */ import javax.resource.NotSupportedException;
/*     */ import javax.resource.ResourceException;
/*     */ import javax.resource.spi.ActivationSpec;
/*     */ import javax.resource.spi.BootstrapContext;
/*     */ import javax.resource.spi.ResourceAdapter;
/*     */ import javax.resource.spi.ResourceAdapterInternalException;
/*     */ import javax.resource.spi.endpoint.MessageEndpointFactory;
/*     */ import javax.transaction.xa.XAResource;
/*     */ 
/*     */ public class OracleResourceAdapter
/*     */   implements ResourceAdapter
/*     */ {
/* 120 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";
/*     */ 
/*     */   public void start(BootstrapContext paramBootstrapContext)
/*     */     throws ResourceAdapterInternalException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endpointActivation(MessageEndpointFactory paramMessageEndpointFactory, ActivationSpec paramActivationSpec)
/*     */     throws NotSupportedException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endpointDeactivation(MessageEndpointFactory paramMessageEndpointFactory, ActivationSpec paramActivationSpec)
/*     */   {
/*     */   }
/*     */ 
/*     */   public XAResource[] getXAResources(ActivationSpec[] paramArrayOfActivationSpec)
/*     */     throws ResourceException
/*     */   {
/* 113 */     return new XAResource[0];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.connector.OracleResourceAdapter
 * JD-Core Version:    0.6.0
 */