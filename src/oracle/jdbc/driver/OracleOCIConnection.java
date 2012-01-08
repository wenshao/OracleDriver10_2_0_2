/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import oracle.jdbc.pool.OracleOCIConnectionPool;
/*     */ 
/*     */ public abstract class OracleOCIConnection extends T2CConnection
/*     */ {
/*  32 */   OracleOCIConnectionPool ociConnectionPool = null;
/*  33 */   boolean isPool = false;
/*  34 */   boolean aliasing = false;
/*     */ 
/* 132 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleOCIConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties, Object paramObject)
/*     */     throws SQLException
/*     */   {
/*  45 */     this(paramString1, paramString2, paramString3, paramString4, paramProperties, (OracleDriverExtension)paramObject);
/*     */   }
/*     */ 
/*     */   OracleOCIConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
/*     */     throws SQLException
/*     */   {
/*  59 */     super(paramString1, paramString2, paramString3, paramString4, paramProperties, paramOracleDriverExtension);
/*     */   }
/*     */ 
/*     */   public synchronized byte[] getConnectionId()
/*     */     throws SQLException
/*     */   {
/*  68 */     byte[] arrayOfByte = t2cGetConnectionId(this.m_nativeState);
/*     */ 
/*  70 */     this.aliasing = true;
/*     */ 
/*  72 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public synchronized void passwordChange(String paramString1, String paramString2, String paramString3)
/*     */     throws SQLException, IOException
/*     */   {
/*  89 */     ociPasswordChange(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */     throws SQLException
/*     */   {
/* 100 */     if ((this.lifecycle == 2) || (this.lifecycle == 4) || (this.aliasing)) {
/* 101 */       return;
/*     */     }
/* 103 */     super.close();
/*     */ 
/* 105 */     this.ociConnectionPool.connectionClosed((oracle.jdbc.oci.OracleOCIConnection)this);
/*     */   }
/*     */ 
/*     */   public synchronized void setConnectionPool(OracleOCIConnectionPool paramOracleOCIConnectionPool)
/*     */   {
/* 113 */     this.ociConnectionPool = paramOracleOCIConnectionPool;
/*     */   }
/*     */ 
/*     */   public synchronized void setStmtCacheSize(int paramInt, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 127 */     super.setStmtCacheSize(paramInt, paramBoolean);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleOCIConnection
 * JD-Core Version:    0.6.0
 */