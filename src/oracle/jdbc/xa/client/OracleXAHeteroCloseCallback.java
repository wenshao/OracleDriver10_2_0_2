/*    */ package oracle.jdbc.xa.client;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import oracle.jdbc.driver.DatabaseError;
/*    */ import oracle.jdbc.driver.OracleCloseCallback;
/*    */ import oracle.jdbc.internal.OracleConnection;
/*    */ 
/*    */ public class OracleXAHeteroCloseCallback
/*    */   implements OracleCloseCallback
/*    */ {
/* 89 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*    */ 
/*    */   public synchronized void beforeClose(OracleConnection paramOracleConnection, Object paramObject)
/*    */   {
/*    */   }
/*    */ 
/*    */   public synchronized void afterClose(Object paramObject)
/*    */   {
/* 56 */     int i = ((OracleXAHeteroConnection)paramObject).getRmid();
/* 57 */     String str = ((OracleXAHeteroConnection)paramObject).getXaCloseString();
/*    */     try
/*    */     {
/* 62 */       int j = doXaClose(str, i, 0, 0);
/*    */ 
/* 68 */       if (j != 0)
/*    */       {
/* 70 */         DatabaseError.throwSqlException(-1 * j);
/*    */       }
/*    */     }
/*    */     catch (SQLException localSQLException)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   private native int doXaClose(String paramString, int paramInt1, int paramInt2, int paramInt3);
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.client.OracleXAHeteroCloseCallback
 * JD-Core Version:    0.6.0
 */