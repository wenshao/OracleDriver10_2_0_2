/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSet12Byte extends CharacterSetWithConverter
/*     */ {
/*     */   static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter12Byte";
/*     */   static final int MAX_7BIT = 127;
/*     */   static Class m_charConvSuperclass;
/*     */ 
/*     */   CharacterSet12Byte(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  81 */     super(paramInt, paramCharacterConverters);
/*     */   }
/*     */ 
/*     */   static CharacterSet12Byte getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  90 */     if (paramCharacterConverters.getGroupId() == 1)
/*     */     {
/*  92 */       return new CharacterSet12Byte(paramInt, paramCharacterConverters);
/*     */     }
/*     */ 
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 102 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 104 */     paramCharacterWalker.next += 1;
/*     */ 
/* 106 */     if (i > 127)
/*     */     {
/* 110 */       if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
/*     */       {
/* 112 */         i = i << 8 | paramCharacterWalker.bytes[paramCharacterWalker.next];
/* 113 */         paramCharacterWalker.next += 1;
/*     */       }
/*     */       else
/*     */       {
/* 117 */         throw new SQLException("destination too small");
/*     */       }
/*     */     }
/*     */ 
/* 121 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 126 */     int i = 0;
/* 127 */     int j = 1;
/*     */ 
/* 129 */     while (paramInt >> i != 0)
/*     */     {
/* 131 */       i = (short)(i + 8);
/* 132 */       j = (short)(j + 1);
/*     */     }
/*     */ 
/* 138 */     need(paramCharacterBuffer, j);
/*     */ 
/* 140 */     while (i >= 0)
/*     */     {
/* 142 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> i & 0xFF);
/* 143 */       i = (short)(i - 8);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSet12Byte
 * JD-Core Version:    0.6.0
 */