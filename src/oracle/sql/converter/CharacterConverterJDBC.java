/*     */ package oracle.sql.converter;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import oracle.sql.ConverterArchive;
/*     */ 
/*     */ public abstract class CharacterConverterJDBC extends CharacterConverters
/*     */ {
/*     */   static final String CONVERTERNAMEPREFIX = "converter_xcharset/lx2";
/*     */   static final String CONVERTERIDPREFIX = "0000";
/*     */   static final int HIBYTEMASK = 65280;
/*     */   static final int LOWBYTEMASK = 255;
/*     */   static final int STORE_INCREMENT = 10;
/*     */   static final int INVALID_ORA_CHAR = -1;
/*     */   static final int FIRSTBSHIFT = 24;
/*     */   static final int SECONDBSHIFT = 16;
/*     */   static final int THIRDBSHIFT = 8;
/*     */   static final int UB2MASK = 65535;
/*     */   static final int UB4MASK = 65535;
/*  69 */   static final HashMap m_converterStore = new HashMap();
/*     */ 
/*     */   public static CharacterConverters getInstance(int paramInt)
/*     */   {
/*  85 */     CharacterConverterJDBC localCharacterConverterJDBC = null;
/*  86 */     int i = 0;
/*  87 */     int j = 0;
/*  88 */     String str1 = Integer.toHexString(paramInt);
/*     */ 
/*  90 */     synchronized (m_converterStore)
/*     */     {
/*  92 */       localCharacterConverterJDBC = (CharacterConverterJDBC)m_converterStore.get(str1);
/*     */ 
/*  94 */       if (localCharacterConverterJDBC != null)
/*     */       {
/*  96 */         return localCharacterConverterJDBC;
/*     */       }
/*     */ 
/* 100 */       String str2 = "converter_xcharset/lx2" + "0000".substring(0, 4 - str1.length()) + str1;
/*     */ 
/* 103 */       ConverterArchive localConverterArchive = new ConverterArchive();
/*     */ 
/* 105 */       localCharacterConverterJDBC = (CharacterConverterJDBC)localConverterArchive.readObj(str2 + ".glb");
/*     */ 
/* 108 */       if (localCharacterConverterJDBC == null)
/*     */       {
/* 110 */         return null;
/*     */       }
/*     */ 
/* 113 */       localCharacterConverterJDBC.buildUnicodeToOracleMapping();
/* 114 */       m_converterStore.put(str1, localCharacterConverterJDBC);
/*     */ 
/* 116 */       return localCharacterConverterJDBC;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void storeMappingRange(int paramInt, Hashtable paramHashtable1, Hashtable paramHashtable2)
/*     */   {
/* 131 */     int i = paramInt >> 24 & 0xFF;
/* 132 */     int j = paramInt >> 16 & 0xFF;
/* 133 */     int k = paramInt >> 8 & 0xFF;
/* 134 */     int m = paramInt & 0xFF;
/* 135 */     Integer localInteger1 = new Integer(i);
/* 136 */     Integer localInteger2 = new Integer(paramInt >> 16 & 0xFFFF);
/* 137 */     Integer localInteger3 = new Integer(paramInt >> 8 & 0xFFFFFF);
/*     */ 
/* 141 */     if (paramInt >>> 26 == 54)
/*     */     {
/* 145 */       arrayOfChar = (char[])paramHashtable1.get(localInteger1);
/*     */ 
/* 147 */       if (arrayOfChar == null)
/*     */       {
/* 149 */         arrayOfChar = new char[] { 'ÿ', '\000' };
/*     */       }
/*     */ 
/* 155 */       if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
/*     */       {
/* 157 */         arrayOfChar[0] = (char)j;
/* 158 */         arrayOfChar[1] = (char)j;
/*     */       }
/*     */       else
/*     */       {
/* 162 */         if (j < (arrayOfChar[0] & 0xFFFF))
/*     */         {
/* 164 */           arrayOfChar[0] = (char)j;
/*     */         }
/*     */ 
/* 167 */         if (j > (arrayOfChar[0] & 0xFFFF))
/*     */         {
/* 169 */           arrayOfChar[1] = (char)j;
/*     */         }
/*     */       }
/*     */ 
/* 173 */       paramHashtable1.put(localInteger1, arrayOfChar);
/*     */ 
/* 176 */       arrayOfChar = (char[])paramHashtable1.get(localInteger2);
/*     */ 
/* 178 */       if (arrayOfChar == null)
/*     */       {
/* 180 */         arrayOfChar = new char[] { 'ÿ', '\000' };
/*     */       }
/*     */ 
/* 186 */       if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
/*     */       {
/* 188 */         arrayOfChar[0] = (char)k;
/* 189 */         arrayOfChar[1] = (char)k;
/*     */       }
/*     */       else
/*     */       {
/* 193 */         if (k < (arrayOfChar[0] & 0xFFFF))
/*     */         {
/* 195 */           arrayOfChar[0] = (char)k;
/*     */         }
/*     */ 
/* 198 */         if (k > (arrayOfChar[0] & 0xFFFF))
/*     */         {
/* 200 */           arrayOfChar[1] = (char)k;
/*     */         }
/*     */       }
/*     */ 
/* 204 */       paramHashtable1.put(localInteger2, arrayOfChar);
/*     */     }
/*     */ 
/* 208 */     char[] arrayOfChar = (char[])paramHashtable2.get(localInteger3);
/*     */ 
/* 210 */     if (arrayOfChar == null)
/*     */     {
/* 212 */       arrayOfChar = new char[] { 'ÿ', '\000' };
/*     */     }
/*     */ 
/* 218 */     if ((arrayOfChar[0] == 'ÿ') && (arrayOfChar[1] == 0))
/*     */     {
/* 220 */       arrayOfChar[0] = (char)m;
/* 221 */       arrayOfChar[1] = (char)m;
/*     */     }
/*     */     else
/*     */     {
/* 225 */       if (m < (arrayOfChar[0] & 0xFFFF))
/*     */       {
/* 227 */         arrayOfChar[0] = (char)m;
/*     */       }
/*     */ 
/* 230 */       if (m > (arrayOfChar[0] & 0xFFFF))
/*     */       {
/* 232 */         arrayOfChar[1] = (char)m;
/*     */       }
/*     */     }
/*     */ 
/* 236 */     paramHashtable2.put(localInteger3, arrayOfChar);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterConverterJDBC
 * JD-Core Version:    0.6.0
 */