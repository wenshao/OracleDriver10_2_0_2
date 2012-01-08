/*    */ package oracle.sql.converter;
/*    */ 
/*    */ public class CharacterConverterFactoryJDBC extends CharacterConverterFactory
/*    */ {
/*    */   public CharacterConverters make(int paramInt)
/*    */   {
/* 45 */     return CharacterConverterJDBC.getInstance(paramInt);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterConverterFactoryJDBC
 * JD-Core Version:    0.6.0
 */