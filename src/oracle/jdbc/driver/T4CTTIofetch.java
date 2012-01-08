/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T4CTTIofetch extends T4CTTIfun
/*    */ {
/* 42 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*    */ 
/*    */   T4CTTIofetch(T4CMAREngine paramT4CMAREngine)
/*    */     throws IOException, SQLException
/*    */   {
/* 28 */     super(3, 0, 5);
/*    */ 
/* 30 */     setMarshalingEngine(paramT4CMAREngine);
/*    */   }
/*    */ 
/*    */   void marshal(int paramInt1, int paramInt2) throws IOException, SQLException
/*    */   {
/* 35 */     super.marshalFunHeader();
/* 36 */     this.meg.marshalSWORD(paramInt1);
/* 37 */     this.meg.marshalSWORD(paramInt2);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIofetch
 * JD-Core Version:    0.6.0
 */