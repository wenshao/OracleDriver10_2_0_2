/*    */ package oracle.jdbc.util;
/*    */ 
/*    */ public class SQLStateRange
/*    */ {
/*    */   public int low;
/*    */   public int high;
/*    */   public String sqlState;
/* 38 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*    */ 
/*    */   public SQLStateRange(int paramInt1, int paramInt2, String paramString)
/*    */   {
/* 31 */     this.low = paramInt1;
/* 32 */     this.high = paramInt2;
/* 33 */     this.sqlState = paramString;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.util.SQLStateRange
 * JD-Core Version:    0.6.0
 */