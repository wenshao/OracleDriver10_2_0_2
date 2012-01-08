/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSetZHTEUC extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterZHTEUC";
/*     */   static final int MAX_7BIT = 127;
/*     */   static final int CHARLENGTH = 4;
/*     */   static Class m_charConvSuperclass;
/*     */   char[] m_leadingCodes;
/*     */ 
/*     */   CharacterSetZHTEUC(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  83 */     super(paramInt, paramCharacterConverters);
/*     */ 
/*  85 */     this.m_leadingCodes = paramCharacterConverters.getLeadingCodes();
/*     */   }
/*     */ 
/*     */   static CharacterSetZHTEUC getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  94 */     if (paramCharacterConverters.getGroupId() == 5)
/*     */     {
/*  96 */       return new CharacterSetZHTEUC(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 111 */     if (paramCharacterWalker.next + 1 < paramCharacterWalker.bytes.length)
/*     */     {
/* 115 */       i = paramCharacterWalker.bytes[paramCharacterWalker.next] << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)];
/*     */ 
/* 118 */       for (int j = 0; j < this.m_leadingCodes.length; j++)
/*     */       {
/* 120 */         if (i != this.m_leadingCodes[j])
/*     */         {
/*     */           continue;
/*     */         }
/* 124 */         if (paramCharacterWalker.bytes.length - paramCharacterWalker.next < 4)
/*     */         {
/* 126 */           throw new SQLException("destination too small");
/*     */         }
/*     */ 
/* 129 */         int k = 0;
/*     */ 
/* 131 */         for (int m = 0; m < 4; m++)
/*     */         {
/* 133 */           k = k << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next++)];
/*     */         }
/*     */ 
/* 136 */         return k;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 145 */     paramCharacterWalker.next += 1;
/*     */ 
/* 147 */     if (i > 127)
/*     */     {
/* 151 */       if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
/*     */       {
/* 153 */         i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
/* 154 */         paramCharacterWalker.next += 1;
/*     */       }
/*     */       else
/*     */       {
/* 158 */         throw new SQLException("destination too small");
/*     */       }
/*     */     }
/*     */ 
/* 162 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 169 */     int i = paramInt >> 16;
/*     */ 
/* 171 */     for (int j = 0; j < this.m_leadingCodes.length; j++)
/*     */     {
/* 173 */       if (i != this.m_leadingCodes[j])
/*     */       {
/*     */         continue;
/*     */       }
/* 177 */       need(paramCharacterBuffer, 4);
/*     */ 
/* 179 */       for (int k = 0; k < 4; k++)
/*     */       {
/* 181 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)paramInt;
/* 182 */         paramInt >>= 8;
/*     */       }
/*     */ 
/* 185 */       return;
/*     */     }
/*     */ 
/* 190 */     throw new SQLException();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetZHTEUC
 * JD-Core Version:    0.6.0
 */