/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T4CTTIoses extends T4CTTIfun
/*    */ {
/*    */   static final int OSESSWS = 1;
/*    */   static final int OSESDET = 3;
/* 75 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*    */ 
/*    */   T4CTTIoses(T4CMAREngine paramT4CMAREngine)
/*    */     throws IOException, SQLException
/*    */   {
/* 47 */     super(17, 0);
/*    */ 
/* 49 */     setMarshalingEngine(paramT4CMAREngine);
/* 50 */     setFunCode(107);
/*    */   }
/*    */ 
/*    */   void marshal(int paramInt1, int paramInt2, int paramInt3)
/*    */     throws IOException, SQLException
/*    */   {
/* 61 */     if ((paramInt3 != 1) && (paramInt3 != 3)) {
/* 62 */       throw new SQLException("Wrong operation : can only do switch or detach");
/*    */     }
/* 64 */     marshalFunHeader();
/* 65 */     this.meg.marshalUB4(paramInt1);
/* 66 */     this.meg.marshalUB4(paramInt2);
/* 67 */     this.meg.marshalUB4(paramInt3);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIoses
 * JD-Core Version:    0.6.0
 */