/*     */ package oracle.sql;
/*     */ 
/*     */ class CharacterSetFactoryThin extends CharacterSetFactory
/*     */ {
/*     */   public CharacterSet make(int paramInt)
/*     */   {
/*  86 */     if (paramInt == -1)
/*     */     {
/*  90 */       paramInt = 31;
/*     */     }
/*     */ 
/* 101 */     if (paramInt == 2000)
/*     */     {
/* 103 */       return new CharacterSetAL16UTF16(paramInt);
/*     */     }
/* 105 */     if ((paramInt == 870) || (paramInt == 871))
/*     */     {
/* 107 */       return new CharacterSetUTF(paramInt);
/*     */     }
/* 109 */     if (paramInt == 873)
/*     */     {
/* 111 */       return new CharacterSetAL32UTF8(paramInt);
/*     */     }
/* 113 */     if (paramInt == 872)
/*     */     {
/* 115 */       return new CharacterSetUTFE(paramInt);
/*     */     }
/* 117 */     if (paramInt == 2002)
/*     */     {
/* 119 */       return new CharacterSetAL16UTF16LE(paramInt);
/*     */     }
/*     */ 
/* 123 */     CharacterSet localCharacterSet = CharacterSetWithConverter.getInstance(paramInt);
/*     */ 
/* 125 */     if (localCharacterSet != null)
/*     */     {
/* 127 */       return localCharacterSet;
/*     */     }
/*     */ 
/* 131 */     return new CharacterSetUnknown(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetFactoryThin
 * JD-Core Version:    0.6.0
 */