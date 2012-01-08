/*     */ package oracle.sql;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class INTERVALDS extends Datum
/*     */ {
/* 449 */   private static int MAXLEADPREC = 9;
/* 450 */   private static int MAXHOUR = 23;
/* 451 */   private static int MAXMINUTE = 59;
/* 452 */   private static int MAXSECOND = 59;
/* 453 */   private static int INTERVALDSMAXLENGTH = 11;
/* 454 */   private static int INTERVALDSOFFSET = 60;
/* 455 */   private static int INTERVALDAYOFFSET = -2147483648;
/*     */ 
/*     */   public INTERVALDS()
/*     */   {
/*  86 */     super(_initIntervalDS());
/*     */   }
/*     */ 
/*     */   public INTERVALDS(byte[] paramArrayOfByte)
/*     */   {
/* 102 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public INTERVALDS(String paramString)
/*     */   {
/* 118 */     super(toBytes(paramString));
/*     */   }
/*     */ 
/*     */   public byte[] toBytes()
/*     */   {
/* 132 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(String paramString)
/*     */   {
/* 147 */     int j = 0; int k = 0; int m = 0; int n = 0; int i1 = 0;
/* 148 */     byte[] arrayOfByte = new byte[INTERVALDSMAXLENGTH];
/*     */ 
/* 150 */     String str5 = null;
/*     */ 
/* 153 */     String str6 = paramString.trim();
/*     */ 
/* 156 */     int i2 = str6.charAt(0);
/*     */     int i;
/* 158 */     if ((i2 != 45) && (i2 != 43))
/* 159 */       i = 0;
/*     */     else {
/* 161 */       i = 1;
/*     */     }
/*     */ 
/* 164 */     str6 = str6.substring(i);
/*     */ 
/* 167 */     int i3 = str6.indexOf(' ');
/*     */ 
/* 169 */     String str1 = str6.substring(0, i3);
/*     */ 
/* 171 */     if (str1.length() > MAXLEADPREC) {
/* 172 */       throw new NumberFormatException();
/*     */     }
/* 174 */     int i4 = Integer.valueOf(str1).intValue();
/*     */ 
/* 177 */     String str7 = str6.substring(i3 + 1);
/*     */ 
/* 181 */     StringTokenizer localStringTokenizer = new StringTokenizer(str7, ":.");
/*     */ 
/* 183 */     if (localStringTokenizer.hasMoreTokens()) { String str2;
/*     */       String str3;
/*     */       String str4;
/*     */       try { str2 = localStringTokenizer.nextToken();
/* 188 */         str3 = localStringTokenizer.nextToken();
/* 189 */         str4 = localStringTokenizer.nextToken();
/* 190 */         str5 = localStringTokenizer.nextToken();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 194 */         throw new NumberFormatException();
/*     */       }
/*     */ 
/* 199 */       j = Integer.valueOf(str1).intValue();
/* 200 */       k = Integer.valueOf(str2).intValue();
/* 201 */       m = Integer.valueOf(str3).intValue();
/* 202 */       n = Integer.valueOf(str4).intValue();
/*     */ 
/* 206 */       if (k > MAXHOUR) {
/* 207 */         throw new NumberFormatException();
/*     */       }
/* 209 */       if (m > MAXMINUTE) {
/* 210 */         throw new NumberFormatException();
/*     */       }
/* 212 */       if (n > MAXSECOND) {
/* 213 */         throw new NumberFormatException();
/*     */       }
/* 215 */       if (str5.length() <= MAXLEADPREC)
/* 216 */         i1 = Integer.valueOf(str5).intValue();
/*     */       else
/* 218 */         throw new NumberFormatException();
/*     */     }
/*     */     else
/*     */     {
/* 222 */       throw new NumberFormatException();
/*     */     }
/*     */ 
/* 225 */     if (i2 == 45)
/*     */     {
/* 227 */       j = -j;
/* 228 */       k = -k;
/* 229 */       m = -m;
/* 230 */       n = -n;
/* 231 */       i1 = -i1;
/*     */     }
/*     */ 
/* 236 */     j += INTERVALDAYOFFSET;
/*     */ 
/* 240 */     arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(j);
/* 241 */     arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(j);
/* 242 */     arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(j);
/* 243 */     arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(j);
/*     */ 
/* 246 */     arrayOfByte[4] = (byte)(k + INTERVALDSOFFSET);
/*     */ 
/* 249 */     arrayOfByte[5] = (byte)(m + INTERVALDSOFFSET);
/*     */ 
/* 252 */     arrayOfByte[6] = (byte)(n + INTERVALDSOFFSET);
/*     */ 
/* 258 */     i1 += INTERVALDAYOFFSET;
/*     */ 
/* 260 */     arrayOfByte[7] = utilpack.RIGHTSHIFTFIRSTNIBBLE(i1);
/* 261 */     arrayOfByte[8] = utilpack.RIGHTSHIFTSECONDNIBBLE(i1);
/* 262 */     arrayOfByte[9] = utilpack.RIGHTSHIFTTHIRDNIBBLE(i1);
/* 263 */     arrayOfByte[10] = utilpack.RIGHTSHIFTFOURTHNIBBLE(i1);
/*     */ 
/* 265 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] paramArrayOfByte)
/*     */   {
/* 283 */     int i = 1;
/*     */ 
/* 285 */     int i1 = 0;
/*     */ 
/* 289 */     int j = utilpack.LEFTSHIFTFIRSTNIBBLE(paramArrayOfByte[0]);
/* 290 */     j |= utilpack.LEFTSHIFTSECONDNIBBLE(paramArrayOfByte[1]);
/* 291 */     j |= utilpack.LEFTSHIFTTHIRDNIBBLE(paramArrayOfByte[2]);
/* 292 */     j |= paramArrayOfByte[3] & 0xFF;
/*     */ 
/* 295 */     j -= INTERVALDAYOFFSET;
/*     */ 
/* 297 */     int k = paramArrayOfByte[4] - INTERVALDSOFFSET;
/*     */ 
/* 299 */     int m = paramArrayOfByte[5] - INTERVALDSOFFSET;
/*     */ 
/* 301 */     int n = paramArrayOfByte[6] - INTERVALDSOFFSET;
/*     */ 
/* 303 */     i1 = utilpack.LEFTSHIFTFIRSTNIBBLE(paramArrayOfByte[7]);
/* 304 */     i1 |= utilpack.LEFTSHIFTSECONDNIBBLE(paramArrayOfByte[8]);
/* 305 */     i1 |= utilpack.LEFTSHIFTTHIRDNIBBLE(paramArrayOfByte[9]);
/* 306 */     i1 |= paramArrayOfByte[10] & 0xFF;
/*     */ 
/* 311 */     i1 -= INTERVALDAYOFFSET;
/*     */ 
/* 313 */     if (j < 0)
/*     */     {
/* 315 */       i = 0;
/* 316 */       j = -j;
/* 317 */       k = -k;
/* 318 */       m = -m;
/* 319 */       n = -n;
/* 320 */       i1 = -i1;
/*     */     }
/*     */ 
/* 324 */     if (i != 0) {
/* 325 */       return new String(j + " " + k + ":" + m + ":" + n + "." + i1);
/*     */     }
/*     */ 
/* 328 */     return new String("-" + j + " " + k + ":" + m + ":" + n + "." + i1);
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */   {
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 354 */     return toString(getBytes());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 367 */     return toString(getBytes());
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 381 */     return paramClass.getName().compareTo("java.lang.String") == 0;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 395 */     INTERVALDS[] arrayOfINTERVALDS = new INTERVALDS[paramInt];
/*     */ 
/* 397 */     return arrayOfINTERVALDS;
/*     */   }
/*     */ 
/*     */   private static byte[] _initIntervalDS()
/*     */   {
/* 408 */     byte[] arrayOfByte = new byte[INTERVALDSMAXLENGTH];
/*     */ 
/* 412 */     int i = 0;
/* 413 */     int j = 0;
/* 414 */     int k = 0;
/* 415 */     int m = 0;
/* 416 */     int n = 0;
/*     */ 
/* 420 */     i += INTERVALDAYOFFSET;
/*     */ 
/* 425 */     arrayOfByte[0] = utilpack.RIGHTSHIFTFIRSTNIBBLE(i);
/* 426 */     arrayOfByte[1] = utilpack.RIGHTSHIFTSECONDNIBBLE(i);
/* 427 */     arrayOfByte[2] = utilpack.RIGHTSHIFTTHIRDNIBBLE(i);
/* 428 */     arrayOfByte[3] = utilpack.RIGHTSHIFTFOURTHNIBBLE(i);
/*     */ 
/* 433 */     arrayOfByte[4] = (byte)(j + INTERVALDSOFFSET);
/* 434 */     arrayOfByte[5] = (byte)(k + INTERVALDSOFFSET);
/* 435 */     arrayOfByte[6] = (byte)(m + INTERVALDSOFFSET);
/*     */ 
/* 437 */     n += INTERVALDAYOFFSET;
/*     */ 
/* 439 */     arrayOfByte[7] = utilpack.RIGHTSHIFTFIRSTNIBBLE(n);
/* 440 */     arrayOfByte[8] = utilpack.RIGHTSHIFTSECONDNIBBLE(n);
/* 441 */     arrayOfByte[9] = utilpack.RIGHTSHIFTTHIRDNIBBLE(n);
/* 442 */     arrayOfByte[10] = utilpack.RIGHTSHIFTFOURTHNIBBLE(n);
/*     */ 
/* 446 */     return arrayOfByte;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.INTERVALDS
 * JD-Core Version:    0.6.0
 */