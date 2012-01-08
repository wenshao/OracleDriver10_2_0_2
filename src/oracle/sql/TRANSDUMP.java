/*    */ package oracle.sql;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import oracle.jdbc.OracleCallableStatement;
/*    */ 
/*    */ public class TRANSDUMP
/*    */ {
/*    */   public static byte[] getTransitions(Connection paramConnection, int paramInt)
/*    */     throws SQLException
/*    */   {
/* 41 */     OracleCallableStatement localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall("begin dbms_utility.get_tz_transitions(?,?); end;");
/*    */ 
/* 48 */     NUMBER localNUMBER = new NUMBER(paramInt);
/*    */ 
/* 52 */     localOracleCallableStatement.setNUMBER(1, localNUMBER);
/*    */ 
/* 56 */     localOracleCallableStatement.registerOutParameter(2, -2);
/*    */ 
/* 60 */     localOracleCallableStatement.execute();
/*    */ 
/* 64 */     byte[] arrayOfByte = localOracleCallableStatement.getBytes(2);
/*    */ 
/* 66 */     localOracleCallableStatement.close();
/*    */ 
/* 68 */     return arrayOfByte;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TRANSDUMP
 * JD-Core Version:    0.6.0
 */