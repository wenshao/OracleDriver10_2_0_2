/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSetJAEUC extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterJAEUC";
/*     */   static final transient short MAX_7BIT = 127;
/*     */   static final transient short LEADINGCODE = 143;
/*     */   static Class m_charConvSuperclass;
/*     */ 
/*     */   CharacterSetJAEUC(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  79 */     super(paramInt, paramCharacterConverters);
/*     */   }
/*     */ 
/*     */   static CharacterSetJAEUC getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  88 */     if (paramCharacterConverters.getGroupId() == 2)
/*     */     {
/*  90 */       return new CharacterSetJAEUC(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 100 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 102 */     paramCharacterWalker.next += 1;
/*     */ 
/* 104 */     if (i > 127)
/*     */     {
/* 106 */       if (i != 143)
/*     */       {
/* 110 */         if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
/*     */         {
/* 112 */           i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
/* 113 */           paramCharacterWalker.next += 1;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 120 */         for (int j = 0; j < 2; j++)
/*     */         {
/* 122 */           if (paramCharacterWalker.bytes.length <= paramCharacterWalker.next)
/*     */             continue;
/* 124 */           i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
/* 125 */           paramCharacterWalker.next += 1;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 131 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 136 */     int i = 0;
/* 137 */     int j = 1;
/*     */ 
/* 139 */     while (paramInt >> i != 0)
/*     */     {
/* 141 */       i = (short)(i + 8);
/* 142 */       j = (short)(j + 1);
/*     */     }
/*     */ 
/* 148 */     need(paramCharacterBuffer, j);
/*     */ 
/* 150 */     while (i >= 0)
/*     */     {
/* 152 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> i & 0xFF);
/* 153 */       i = (short)(i - 8);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetJAEUC
 * JD-Core Version:    0.6.0
 */