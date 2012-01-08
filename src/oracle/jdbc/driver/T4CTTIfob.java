/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class T4CTTIfob extends T4CTTIMsg
/*    */ {
/*    */   T4CTTIfob(T4CMAREngine paramT4CMAREngine)
/*    */     throws IOException, SQLException
/*    */   {
/* 37 */     super(19);
/* 38 */     setMarshalingEngine(paramT4CMAREngine);
/*    */   }
/*    */ 
/*    */   void marshal() throws IOException, SQLException
/*    */   {
/* 43 */     this.meg.marshalUB1(19);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIfob
 * JD-Core Version:    0.6.0
 */