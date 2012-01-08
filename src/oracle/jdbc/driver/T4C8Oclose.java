/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T4C8Oclose extends T4CTTIfun
/*    */ {
/* 72 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*    */ 
/*    */   T4C8Oclose(T4CMAREngine paramT4CMAREngine)
/*    */     throws IOException, SQLException
/*    */   {
/* 28 */     super(17, 0);
/*    */ 
/* 30 */     setMarshalingEngine(paramT4CMAREngine);
/* 31 */     setFunCode(120);
/*    */   }
/*    */ 
/*    */   void initCloseQuery()
/*    */     throws IOException, SQLException
/*    */   {
/* 39 */     setFunCode(120);
/*    */   }
/*    */ 
/*    */   void initCloseStatement()
/*    */     throws IOException, SQLException
/*    */   {
/* 47 */     setFunCode(105);
/*    */   }
/*    */ 
/*    */   void marshal(int[] paramArrayOfInt, int paramInt)
/*    */     throws IOException
/*    */   {
/* 55 */     marshalFunHeader();
/*    */ 
/* 58 */     this.meg.marshalPTR();
/* 59 */     this.meg.marshalUB4(paramInt);
/*    */ 
/* 61 */     for (int i = 0; i < paramInt; i++)
/*    */     {
/* 63 */       this.meg.marshalUB4(paramArrayOfInt[i]);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8Oclose
 * JD-Core Version:    0.6.0
 */