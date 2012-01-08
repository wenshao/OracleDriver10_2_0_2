/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T4CTTIoexec extends T4CTTIfun
/*    */ {
/*    */   static final int EXE_COMMIT_ON_SUCCESS = 1;
/*    */   static final int EXE_LEAVE_CUR_MAPPED = 2;
/*    */   static final int EXE_BATCH_DML_ERRORS = 4;
/*    */   static final int EXE_SCROL_READ_ONLY = 8;
/* 73 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*    */ 
/*    */   T4CTTIoexec(T4CMAREngine paramT4CMAREngine)
/*    */     throws IOException, SQLException
/*    */   {
/* 56 */     super(3, 0, 4);
/*    */ 
/* 58 */     setMarshalingEngine(paramT4CMAREngine);
/*    */   }
/*    */ 
/*    */   void marshal(int paramInt1, int paramInt2, int paramInt3)
/*    */     throws IOException, SQLException
/*    */   {
/* 64 */     super.marshalFunHeader();
/* 65 */     this.meg.marshalSWORD(paramInt1);
/* 66 */     this.meg.marshalSWORD(paramInt2);
/* 67 */     this.meg.marshalSWORD(0);
/* 68 */     this.meg.marshalSWORD(paramInt3);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIoexec
 * JD-Core Version:    0.6.0
 */