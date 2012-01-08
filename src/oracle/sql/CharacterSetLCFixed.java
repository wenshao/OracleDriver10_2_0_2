/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSetLCFixed extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterLCFixed";
/*     */   static final int CHARLENGTH = 4;
/*     */   static Class m_charConvSuperclass;
/*     */   char[] m_leadingCodes;
/*     */ 
/*     */   CharacterSetLCFixed(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  83 */     super(paramInt, paramCharacterConverters);
/*     */ 
/*  85 */     this.m_leadingCodes = paramCharacterConverters.getLeadingCodes();
/*     */   }
/*     */ 
/*     */   static CharacterSetLCFixed getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  94 */     if (paramCharacterConverters.getGroupId() == 3)
/*     */     {
/*  96 */       return new CharacterSetLCFixed(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 106 */     if (paramCharacterWalker.bytes.length - paramCharacterWalker.next < 4)
/*     */     {
/* 108 */       throw new SQLException("destination too small");
/*     */     }
/*     */ 
/* 112 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)];
/*     */ 
/* 115 */     for (int j = 0; j < this.m_leadingCodes.length; j++)
/*     */     {
/* 117 */       if (i != this.m_leadingCodes[j])
/*     */       {
/*     */         continue;
/*     */       }
/* 121 */       int k = 0;
/*     */ 
/* 123 */       for (int m = 0; m < 4; m++)
/*     */       {
/* 125 */         k = k << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next++)];
/*     */       }
/*     */ 
/* 128 */       return k;
/*     */     }
/*     */ 
/* 133 */     throw new SQLException("Leading code invalid");
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 140 */     int i = paramInt >> 16;
/*     */ 
/* 142 */     for (int j = 0; j < this.m_leadingCodes.length; j++)
/*     */     {
/* 144 */       if (i != this.m_leadingCodes[j])
/*     */       {
/*     */         continue;
/*     */       }
/* 148 */       need(paramCharacterBuffer, 4);
/*     */ 
/* 150 */       for (int k = 3; k >= 0; k--)
/*     */       {
/* 152 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> 8 * k & 0xFF);
/*     */       }
/*     */ 
/* 155 */       return;
/*     */     }
/*     */ 
/* 160 */     throw new SQLException("Leading code invalid");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetLCFixed
 * JD-Core Version:    0.6.0
 */