/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ class CharacterSetGB18030 extends CharacterSetWithConverter
/*     */ {
/*     */   static final int MAX_7BIT = 127;
/*     */   static Class m_charConvSuperclass;
/*     */ 
/*     */   CharacterSetGB18030(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  77 */     super(paramInt, paramCharacterConverters);
/*     */   }
/*     */ 
/*     */   static CharacterSetGB18030 getInstance(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  86 */     if (paramCharacterConverters.getGroupId() == 9)
/*     */     {
/*  88 */       return new CharacterSetGB18030(paramInt, paramCharacterConverters);
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
/* 100 */     if (i > 127)
/*     */     {
/* 104 */       if (paramCharacterWalker.bytes.length > paramCharacterWalker.next + 1)
/*     */       {
/* 106 */         if (((paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) >= 129) && ((paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) <= 254) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) >= 48) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) <= 57))
/*     */         {
/* 113 */           if (paramCharacterWalker.bytes.length > paramCharacterWalker.next + 3)
/*     */           {
/* 115 */             if (((paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) >= 129) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) <= 254) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF) >= 48) && ((paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF) <= 57))
/*     */             {
/* 120 */               i = (paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) << 24 | (paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF) << 16 | (paramCharacterWalker.bytes[(paramCharacterWalker.next + 2)] & 0xFF) << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 3)] & 0xFF;
/*     */ 
/* 124 */               paramCharacterWalker.next += 4;
/*     */             }
/*     */             else
/*     */             {
/* 130 */               i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/* 131 */               paramCharacterWalker.next += 1;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 136 */             throw new SQLException("destination too small");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 143 */           i = (paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF) << 8 | paramCharacterWalker.bytes[(paramCharacterWalker.next + 1)] & 0xFF;
/*     */ 
/* 145 */           paramCharacterWalker.next += 2;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 150 */         throw new SQLException("destination too small");
/*     */       }
/*     */     }
/*     */ 
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 159 */     int i = 0;
/* 160 */     int j = 0;
/*     */ 
/* 162 */     while (paramInt >> i != 0)
/*     */     {
/* 164 */       i = (short)(i + 8);
/* 165 */       j = (short)(j + 1);
/*     */     }
/*     */ 
/* 168 */     if (paramInt >> 16 != 0)
/*     */     {
/* 170 */       i = 3;
/* 171 */       j = 4;
/*     */     }
/* 173 */     else if (paramInt >> 8 != 0)
/*     */     {
/* 175 */       i = 1;
/* 176 */       j = 2;
/*     */     }
/*     */     else
/*     */     {
/* 180 */       i = 0;
/* 181 */       j = 1;
/*     */     }
/*     */ 
/* 184 */     need(paramCharacterBuffer, j);
/*     */ 
/* 186 */     while (i >= 0)
/*     */     {
/* 188 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> i & 0xFF);
/* 189 */       i = (short)(i - 8);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetGB18030
 * JD-Core Version:    0.6.0
 */