/*    */ package oracle.sql.converter;
/*    */ 
/*    */ import oracle.i18n.text.converter.CharacterConverterOGS;
/*    */ 
/*    */ public class CharacterConverterFactoryOGS extends CharacterConverterFactory
/*    */ {
/*    */   public CharacterConverters make(int paramInt)
/*    */   {
/* 48 */     return CharacterConverterOGS.getInstance(paramInt);
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterConverterFactoryOGS
 * JD-Core Version:    0.6.0
 */