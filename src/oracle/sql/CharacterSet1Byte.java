/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSet1Byte extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter1Byte";
/*     */   static Class m_charConvSuperclass;
/*     */ 
/*     */   CharacterSet1Byte(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  77 */     super(paramInt, paramCharacterConverters);
/*     */   }
/*     */ 
/*     */   static CharacterSet1Byte getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  86 */     if (paramCharacterConverters.getGroupId() == 0)
/*     */     {
/*  88 */       return new CharacterSet1Byte(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/*  98 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 100 */     paramCharacterWalker.next += 1;
/*     */ 
/* 102 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 107 */     need(paramCharacterBuffer, 1);
/*     */ 
/* 109 */     if (paramInt < 256)
/*     */     {
/* 111 */       paramCharacterBuffer.bytes[paramCharacterBuffer.next] = (byte)paramInt;
/* 112 */       paramCharacterBuffer.next += 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSet1Byte
 * JD-Core Version:    0.6.0
 */