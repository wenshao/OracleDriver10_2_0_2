/*     */ package oracle.jdbc.xa;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import javax.sql.XAConnection;
/*     */ import javax.sql.XADataSource;
/*     */ import oracle.jdbc.pool.OracleConnectionPoolDataSource;
/*     */ import oracle.jdbc.pool.OracleDataSource;
/*     */ 
/*     */ public abstract class OracleXADataSource extends OracleConnectionPoolDataSource
/*     */   implements XADataSource
/*     */ {
/*  35 */   protected boolean useNativeXA = false;
/*  36 */   protected boolean thinUseNativeXA = true;
/*     */ 
/* 126 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public OracleXADataSource()
/*     */     throws SQLException
/*     */   {
/*  50 */     this.dataSourceName = "OracleXADataSource";
/*     */   }
/*     */ 
/*     */   public abstract XAConnection getXAConnection()
/*     */     throws SQLException;
/*     */ 
/*     */   public abstract XAConnection getXAConnection(String paramString1, String paramString2)
/*     */     throws SQLException;
/*     */ 
/*     */   public abstract XAConnection getXAConnection(Properties paramProperties)
/*     */     throws SQLException;
/*     */ 
/*     */   public synchronized void setNativeXA(boolean paramBoolean)
/*     */   {
/*  93 */     this.useNativeXA = paramBoolean;
/*  94 */     this.thinUseNativeXA = paramBoolean;
/*     */   }
/*     */ 
/*     */   public synchronized boolean getNativeXA()
/*     */   {
/* 109 */     return this.useNativeXA;
/*     */   }
/*     */ 
/*     */   protected void copy(OracleDataSource paramOracleDataSource)
/*     */     throws SQLException
/*     */   {
/* 118 */     super.copy(paramOracleDataSource);
/*     */ 
/* 120 */     ((OracleXADataSource)paramOracleDataSource).useNativeXA = this.useNativeXA;
/* 121 */     ((OracleXADataSource)paramOracleDataSource).thinUseNativeXA = this.thinUseNativeXA;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.OracleXADataSource
 * JD-Core Version:    0.6.0
 */