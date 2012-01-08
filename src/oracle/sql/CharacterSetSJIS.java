/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSetSJIS extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterSJIS";
/*     */   static final short MAX_7BIT = 127;
/*     */   static final short MIN_8BIT_SB = 161;
/*     */   static final short MAX_8BIT_SB = 223;
/*     */   static Class m_charConvSuperclass;
/*     */ 
/*     */   CharacterSetSJIS(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  80 */     super(paramInt, paramCharacterConverters);
/*     */   }
/*     */ 
/*     */   static CharacterSetSJIS getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  89 */     if (paramCharacterConverters.getGroupId() == 4)
/*     */     {
/*  91 */       return new CharacterSetSJIS(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 101 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 103 */     paramCharacterWalker.next += 1;
/*     */ 
/* 105 */     if ((i > 223) || ((i > 127) && (i < 161)))
/*     */     {
/* 109 */       if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
/*     */       {
/* 111 */         i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
/* 112 */         paramCharacterWalker.next += 1;
/*     */       }
/*     */       else
/*     */       {
/* 116 */         throw new SQLException("destination too small");
/*     */       }
/*     */     }
/*     */ 
/* 120 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 125 */     int i = 0;
/* 126 */     int j = 1;
/*     */ 
/* 128 */     while (paramInt >> i != 0)
/*     */     {
/* 130 */       i = (short)(i + 8);
/* 131 */       j = (short)(j + 1);
/*     */     }
/*     */ 
/* 137 */     need(paramCharacterBuffer, j);
/*     */ 
/* 139 */     while (i >= 0)
/*     */     {
/* 141 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> i & 0xFF);
/* 142 */       i = (short)(i - 8);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetSJIS
 * JD-Core Version:    0.6.0
 */