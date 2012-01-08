/*      */ package oracle.sql;
/*      */ 
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ class CharacterSet$2 extends CharacterSet.CharacterConverterBehavior
/*      */ {
/*      */   public void onFailConversion()
/*      */     throws SQLException
/*      */   {
/* 2767 */     DatabaseError.throwSqlException(55);
/*      */   }
/*      */ 
/*      */   public void onFailConversion(char paramChar)
/*      */     throws SQLException
/*      */   {
/* 2773 */     if (paramChar == 65533)
/*      */     {
/* 2775 */       DatabaseError.throwSqlException(55);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSet.2
 * JD-Core Version:    0.6.0
 */