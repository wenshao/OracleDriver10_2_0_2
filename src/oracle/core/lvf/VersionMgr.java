/*     */ package oracle.core.lvf;
/*     */ 
/*     */ public final class VersionMgr
/*     */ {
/*     */   public static final byte ALPHA = 1;
/*     */   public static final byte BETA = 2;
/*     */   public static final byte PROD = 3;
/*     */   public static final byte NONE = 4;
/*  51 */   private final byte MAX_LEN = 64;
/*  52 */   private final byte MAX_PRODLEN = 30;
/*  53 */   private final byte MAX_VERLEN = 15;
/*  54 */   private final byte MAX_DISTLEN = 5;
/*  55 */   private final String alpha = "Alpha";
/*  56 */   private final String beta = "Beta";
/*  57 */   private final String prod = "Production";
/*     */   private String version;
/*     */ 
/*     */   public void setVersion(String paramString1, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, char paramChar, String paramString2, byte paramByte6, int paramInt)
/*     */   {
/*  82 */     char[] arrayOfChar = new char[64];
/*     */ 
/*  87 */     String str2 = "";
/*     */     int k;
/*  90 */     if ((k = (byte)paramString1.length()) > 30) {
/*  91 */       k = 30;
/*     */     }
/*     */ 
/*  94 */     int j = 0;
/*  95 */     while (0 < k--)
/*     */     {
/*  97 */       arrayOfChar[j] = paramString1.charAt(j);
/*  98 */       j = (byte)(j + 1);
/*     */     }
/*     */ 
/* 102 */     j = (byte)(j + 1); arrayOfChar[j] = '\t';
/*     */ 
/* 105 */     if (paramByte1 < 0)
/* 106 */       paramByte1 = 0;
/* 107 */     if (paramByte2 < 0)
/* 108 */       paramByte2 = 0;
/* 109 */     if (paramByte3 < 0)
/* 110 */       paramByte3 = 0;
/* 111 */     if (paramByte4 < 0)
/* 112 */       paramByte4 = 0;
/* 113 */     if (paramByte5 < 0) {
/* 114 */       paramByte5 = 0;
/*     */     }
/*     */ 
/* 117 */     if (paramByte1 > 99)
/* 118 */       paramByte1 = 99;
/* 119 */     if (paramByte2 > 99)
/* 120 */       paramByte2 = 99;
/* 121 */     if (paramByte3 > 99)
/* 122 */       paramByte3 = 99;
/* 123 */     if (paramByte4 > 99)
/* 124 */       paramByte4 = 99;
/* 125 */     if (paramByte5 > 99)
/* 126 */       paramByte5 = 99;
/*     */     String str1;
/* 129 */     if (paramChar != 0)
/* 130 */       str1 = paramByte1 + "." + paramByte2 + "." + paramByte3 + "." + paramByte4 + "." + paramByte5 + paramChar;
/*     */     else
/* 132 */       str1 = paramByte1 + "." + paramByte2 + "." + paramByte3 + "." + paramByte4 + "." + paramByte5;
/* 133 */     int m = (byte)str1.length();
/*     */ 
/* 136 */     int i = 0;
/* 137 */     while (0 < m--) {
/* 138 */       j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = str1.charAt(i);
/*     */     }
/* 140 */     if (paramByte6 != 4)
/*     */     {
/* 143 */       j = (byte)(j + 1); arrayOfChar[j] = '\t';
/*     */ 
/* 145 */       if (paramString2 != null)
/*     */       {
/* 147 */         int i1 = 0;
/*     */ 
/* 150 */         if ((i1 = (byte)paramString2.length()) > 5) {
/* 151 */           i1 = 5;
/*     */         }
/*     */ 
/* 154 */         i = 0;
/* 155 */         while (0 < i1--) {
/* 156 */           j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = paramString2.charAt(i);
/*     */         }
/*     */ 
/* 159 */         j = (byte)(j + 1); arrayOfChar[j] = '\t';
/*     */       }
/*     */ 
/* 163 */       switch (paramByte6)
/*     */       {
/*     */       case 1:
/* 166 */         str2 = "Alpha";
/* 167 */         break;
/*     */       case 2:
/* 169 */         str2 = "Beta";
/* 170 */         break;
/*     */       case 3:
/* 172 */         str2 = "Production";
/*     */       }
/*     */ 
/* 176 */       i = 0;
/* 177 */       int n = (byte)str2.length();
/*     */ 
/* 180 */       while (0 < n--) {
/* 181 */         j = (byte)(j + 1); i = (byte)(i + 1); arrayOfChar[j] = str2.charAt(i);
/*     */       }
/*     */     }
/*     */ 
/* 185 */     this.version = new String(arrayOfChar, 0, j);
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 194 */     return this.version;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.core.lvf.VersionMgr
 * JD-Core Version:    0.6.0
 */