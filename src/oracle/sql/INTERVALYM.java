/*     */ package oracle.sql;
/*     */ 
/*     */ public class INTERVALYM extends Datum
/*     */ {
/* 379 */   private static int MASKVAL = 255;
/* 380 */   private static int INTYMYEAROFFSET = -2147483648;
/* 381 */   private static int INTYMMONTHOFFSET = 60;
/* 382 */   private static int INTERVALYMMAXLENGTH = 5;
/* 383 */   private static int MAXYEARPREC = 9;
/* 384 */   private static int MAXMONTH = 12;
/*     */ 
/*     */   public INTERVALYM()
/*     */   {
/*  72 */     super(_initIntervalYM());
/*     */   }
/*     */ 
/*     */   public INTERVALYM(byte[] paramArrayOfByte)
/*     */   {
/*  90 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public INTERVALYM(String paramString)
/*     */   {
/* 108 */     super(toBytes(paramString));
/*     */   }
/*     */ 
/*     */   public byte[] toBytes()
/*     */   {
/* 123 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(String paramString)
/*     */   {
/* 140 */     byte[] arrayOfByte = new byte[INTERVALYMMAXLENGTH];
/*     */ 
/* 144 */     String str1 = paramString.trim();
/*     */ 
/* 147 */     int j = str1.charAt(0);
/*     */     int i;
/* 149 */     if ((j != 45) && (j != 43))
/* 150 */       i = 0;
/*     */     else {
/* 152 */       i = 1;
/*     */     }
/*     */ 
/* 155 */     str1 = str1.substring(i);
/*     */ 
/* 157 */     int k = str1.indexOf('-');
/*     */ 
/* 159 */     String str2 = str1.substring(0, k);
/*     */ 
/* 161 */     if (str2.length() > MAXYEARPREC) {
/* 162 */       throw new NumberFormatException();
/*     */     }
/* 164 */     int m = Integer.valueOf(str2).intValue();
/*     */ 
/* 168 */     String str3 = str1.substring(k + 1);
/*     */ 
/* 170 */     int n = Integer.valueOf(str3).intValue();
/*     */ 
/* 172 */     if (n >= MAXMONTH) {
/* 173 */       throw new NumberFormatException();
/*     */     }
/*     */ 
/* 176 */     if (j == 45)
/*     */     {
/* 178 */       m = -1 * m;
/* 179 */       n = -1 * n;
/*     */     }
/*     */ 
/* 186 */     m += INTYMYEAROFFSET;
/*     */ 
/* 191 */     arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(m);
/* 192 */     arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(m);
/* 193 */     arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(m);
/* 194 */     arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(m);
/*     */ 
/* 198 */     arrayOfByte[4] = (byte)(n + INTYMMONTHOFFSET);
/*     */ 
/* 200 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] paramArrayOfByte)
/*     */   {
/* 218 */     int i = 1;
/*     */ 
/* 225 */     int j = utilpack.LEFTSHIFTFIRSTNIBBLE(paramArrayOfByte[0]);
/* 226 */     j |= utilpack.LEFTSHIFTSECONDNIBBLE(paramArrayOfByte[1]);
/* 227 */     j |= utilpack.LEFTSHIFTTHIRDNIBBLE(paramArrayOfByte[2]);
/* 228 */     j |= paramArrayOfByte[3] & 0xFF;
/*     */ 
/* 232 */     j -= INTYMYEAROFFSET;
/*     */ 
/* 236 */     int k = paramArrayOfByte[4] - INTYMMONTHOFFSET;
/*     */ 
/* 239 */     if (j < 0)
/*     */     {
/* 241 */       i = 0;
/* 242 */       j = -j;
/*     */     }
/*     */ 
/* 246 */     if (k < 0)
/*     */     {
/* 248 */       i = 0;
/* 249 */       k = -k;
/*     */     }
/*     */     String str;
/* 254 */     if (i != 0)
/* 255 */       str = new String(j + "-" + k);
/*     */     else {
/* 257 */       str = new String("-" + j + "-" + k);
/*     */     }
/* 259 */     return str;
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */   {
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 286 */     return toString(getBytes());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 299 */     return toString(getBytes());
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 315 */     INTERVALYM[] arrayOfINTERVALYM = new INTERVALYM[paramInt];
/*     */ 
/* 317 */     return arrayOfINTERVALYM;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 334 */     return paramClass.getName().compareTo("java.lang.String") == 0;
/*     */   }
/*     */ 
/*     */   private static byte[] _initIntervalYM()
/*     */   {
/* 349 */     byte[] arrayOfByte = new byte[INTERVALYMMAXLENGTH];
/*     */ 
/* 353 */     int i = 0;
/* 354 */     int j = 0;
/*     */ 
/* 358 */     i += INTYMYEAROFFSET;
/*     */ 
/* 363 */     arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(i);
/* 364 */     arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(i);
/* 365 */     arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(i);
/* 366 */     arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(i);
/*     */ 
/* 371 */     arrayOfByte[4] = (byte)(j + INTYMMONTHOFFSET);
/*     */ 
/* 375 */     return arrayOfByte;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.INTERVALYM
 * JD-Core Version:    0.6.0
 */