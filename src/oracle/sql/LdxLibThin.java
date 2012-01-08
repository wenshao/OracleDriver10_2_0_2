/*      */ package oracle.sql;
/*      */ 
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Locale;
/*      */ import oracle.core.lmx.CoreException;
/*      */ 
/*      */ class LdxLibThin
/*      */   implements LdxLib
/*      */ {
/*      */   private static final int LDXFDLSZ = 50;
/*      */   private static final byte LDX_CC = 1;
/*      */   private static final byte LDX_SCC = 2;
/*      */   private static final byte LDX_I = 3;
/*      */   private static final byte LDX_Y = 4;
/*      */   private static final byte LDX_IY = 5;
/*      */   private static final byte LDX_YY = 6;
/*      */   private static final byte LDX_IYY = 7;
/*      */   private static final byte LDX_YYY = 8;
/*      */   private static final byte LDX_IYYY = 9;
/*      */   private static final byte LDX_YYYY = 10;
/*      */   private static final byte LDX_YCYYY = 11;
/*      */   private static final byte LDX_SYYYY = 12;
/*      */   private static final byte LDX_SYCYYY = 13;
/*      */   private static final byte LDX_YEAR = 14;
/*      */   private static final byte LDX_SYEAR = 15;
/*      */   private static final byte LDX_Q = 16;
/*      */   private static final byte LDX_MM = 17;
/*      */   private static final byte LDX_IW = 18;
/*      */   private static final byte LDX_WW = 19;
/*      */   private static final byte LDX_W = 20;
/*      */   private static final byte LDX_D = 21;
/*      */   private static final byte LDX_DD = 22;
/*      */   private static final byte LDX_DDD = 23;
/*      */   private static final byte LDX_HH24 = 24;
/*      */   private static final byte LDX_HH = 25;
/*      */   private static final byte LDX_MI = 26;
/*      */   private static final byte LDX_SS = 27;
/*      */   private static final byte LDX_SSSSS = 28;
/*      */   private static final byte LDX_J = 29;
/*      */   private static final byte LDX_MONTH = 30;
/*      */   private static final byte LDX_MON = 31;
/*      */   private static final byte LDX_DAY = 32;
/*      */   private static final byte LDX_DY = 33;
/*      */   private static final byte LDX_AMPM = 34;
/*      */   private static final byte LDX_A_M_P_M = 35;
/*      */   private static final byte LDX_BCAD = 36;
/*      */   private static final byte LDX_B_C_A_D = 37;
/*      */   private static final byte LDX_RM = 38;
/*      */   private static final byte LDX_FM = 39;
/*      */   private static final byte LDX_RR = 40;
/*      */   private static final byte LDX_RRRR = 41;
/*      */   private static final byte LDX_FX = 42;
/*      */   private static final byte LDX_E = 43;
/*      */   private static final byte LDX_EE = 44;
/*      */   private static final byte LDX_LIT = 45;
/*      */   private static final byte LDX_JUS = 16;
/*      */   private static final byte LDX_NTH = 1;
/*      */   private static final byte LDX_SPL = 2;
/*      */   private static final byte LDX_CAP = 4;
/*      */   private static final byte LDX_UPR = 8;
/*      */   private static final byte LDX_QUO = 1;
/*      */   private static final byte LDX_SPA = 2;
/*      */   private static final byte LDX_PUN = 4;
/*      */   private static final byte LDX_ALPHA = -128;
/*      */   private static final byte LDXFNJUS = 64;
/*      */   private static final byte LDX_NEG = 32;
/*      */   private static final byte LDX_COMMA = 16;
/*      */   private static final byte LDX_LEN = 15;
/*      */   private static final byte LDXFL_NOT = 0;
/*      */   private static final byte LDXFL_FLEX = 1;
/*      */   private static final byte LDXFL_STD = 2;
/*      */   private static final byte LDXFL_MDONE = 4;
/*      */   private static final byte LDXFL_YDONE = 8;
/*      */   private static final byte LDXFL_PUNC = 16;
/*      */   private static final byte LDXFL_MSEC = 32;
/*      */   private static final int LDXSBUFFERSIZE = 64;
/*      */   private static final int LDXWBUFSIZE = 64;
/*      */   private static final int LDXTCE = 0;
/*      */   private static final int LDXTYE = 1;
/*      */   private static final int LDXTMO = 2;
/*      */   private static final int LDXTDA = 3;
/*      */   private static final int LDXTHO = 4;
/*      */   private static final int LDXTMI = 5;
/*      */   private static final int LDXTSO = 6;
/*      */   private static final int LDXTSIZ = 7;
/*      */   private static final int LDX_SUNDAY = 2445029;
/* 2008 */   private final int LDXPMXYR = 9999;
/* 2009 */   private final int LDXPMNYR = -4712;
/*      */ 
/* 2013 */   private static final char[][] ldxfda = { { 'A', '.', 'D', '.' }, { 'A', '.', 'M', '.' }, { 'A', 'D' }, { 'A', 'M' }, { 'B', '.', 'C', '.' }, { 'B', 'C' }, { 'C', 'C' }, { 'D' }, { 'D', 'A', 'Y' }, { 'D', 'D' }, { 'D', 'D', 'D' }, { 'D', 'Y' }, { 'E' }, { 'E', 'E' }, { 'F', 'M' }, { 'F', 'X' }, { 'H', 'H' }, { 'H', 'H', '1', '2' }, { 'H', 'H', '2', '4' }, { 'I' }, { 'I', 'W' }, { 'I', 'Y' }, { 'I', 'Y', 'Y' }, { 'I', 'Y', 'Y', 'Y' }, { 'J' }, { 'M', 'I' }, { 'M', 'M' }, { 'M', 'O', 'N' }, { 'M', 'O', 'N', 'T', 'H' }, { 'P', '.', 'M', '.' }, { 'P', 'M' }, { 'Q' }, { 'R', 'M' }, { 'R', 'R' }, { 'R', 'R', 'R', 'R' }, { 'S', 'C', 'C' }, { 'S', 'S' }, { 'S', 'S', 'S', 'S', 'S' }, { 'S', 'Y', ',', 'Y', 'Y', 'Y' }, { 'S', 'Y', 'E', 'A', 'R' }, { 'S', 'Y', 'Y', 'Y', 'Y' }, { 'W' }, { 'W', 'W' }, { 'Y' }, { 'Y', ',', 'Y', 'Y', 'Y' }, { 'Y', 'E', 'A', 'R' }, { 'Y', 'Y' }, { 'Y', 'Y', 'Y' }, { 'Y', 'Y', 'Y', 'Y' }, { '\000' } };
/*      */ 
/* 2069 */   private static final byte[] ldxfdc = { 37, 35, 36, 34, 37, 36, 1, 21, 32, 22, 23, 33, 43, 44, 39, 42, 25, 25, 24, 3, 18, 5, 7, 9, 29, 26, 17, 31, 30, 35, 34, 16, 38, 40, 41, 2, 27, 28, 13, 15, 12, 20, 19, 4, 11, 14, 6, 8, 10, 0 };
/*      */ 
/* 2132 */   private static final byte[] ldxfcdlen = { 0, 2, 35, 1, 1, 2, 2, 3, 3, 4, 4, 21, 37, 54, -60, -27, 1, 2, 2, 2, 1, 1, 2, 3, 2, 2, 2, 2, 5, 7, -128, -128, -128, -128, -62, -60, -62, -60, -124, 0, 2, 4, 0, -113, -98, -128, -128 };
/*      */ 
/* 2187 */   private static int[] ldxfdi = { 0, 4, 6, 7, 12, 14, -2147483648, 16, 19, 24, -2147483648, -2147483648, 25, -2147483648, -2147483648, 29, 31, 32, 35, -2147483648, -2147483648, -2147483648, 41, -2147483648, 43, -2147483648 };
/*      */ 
/* 2217 */   private static final char[][] ldxfdx = { { 'S', 'P' }, { 'S', 'P', 'T', 'H' }, { 'T', 'H' }, { 'T', 'H', 'S', 'P' }, { '\000' } };
/*      */ 
/* 2226 */   private static final byte[] ldxfdxc = { 2, 3, 1, 3, 0 };
/*      */ 
/* 2237 */   private static final byte[] NULLFMT = { 0, 16 };
/*      */ 
/* 2242 */   private static final byte[] DEFAULT_FORMAT = { 22, 24, 46, 4, 47, 31, 24, 46, 4, 47, 10, 24 };
/*      */ 
/* 2249 */   private static final String[] ldxpaa = { "A.D.", "A.M.", "B.C.", "P.M." };
/*      */ 
/* 2258 */   private static final int[] ldxdom = { 0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };
/*      */ 
/* 2276 */   private final byte[][] ldxpmxa = { { 23, 29 }, { 4, 6, 8, 10, 12, 11, 13 }, { 25, 24 }, { 34, 35 }, { 36, 37 }, { 30, 31, 17, 38 }, { 32, 33, 21 }, { 34, 35, 24 }, { 12, 13, 36, 37 } };
/*      */ 
/*      */   public byte[] ldxadm(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException
/*      */   {
/*   78 */     int j = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);
/*      */ 
/*   80 */     int i = j;
/*      */ 
/*   82 */     int k = paramArrayOfByte[2] & 0xFF;
/*      */ 
/*   84 */     int m = paramArrayOfByte[3] & 0xFF;
/*      */ 
/*   86 */     paramInt += (paramArrayOfByte[2] & 0xFF) + i * 12;
/*      */ 
/*   89 */     if (paramInt > 0)
/*      */     {
/*   91 */       if ((i = paramInt / 12) > 9999) {
/*   92 */         throw new SQLException(CoreException.getMessage(8));
/*      */       }
/*   94 */       if (paramInt %= 12 == 0)
/*      */       {
/*   96 */         i--;
/*   97 */         paramInt = 12;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  102 */       if ((i = paramInt / 12 - 1) < -4712) {
/*  103 */         throw new SQLException(CoreException.getMessage(8));
/*      */       }
/*  105 */       paramInt = paramInt % 12 + 12;
/*      */     }
/*      */ 
/*  109 */     int i1 = (paramInt == 2) && (ldxisl(j)) ? 29 : ldxdom[(k + 1)] - ldxdom[k];
/*      */ 
/*  113 */     paramArrayOfByte[0] = (byte)(i / 100 + 100);
/*  114 */     paramArrayOfByte[1] = (byte)(i % 100 + 100);
/*      */ 
/*  116 */     paramArrayOfByte[2] = (byte)paramInt;
/*  117 */     int n = (paramInt == 2) && (ldxisl(i)) ? 29 : ldxdom[(paramInt + 1)] - ldxdom[paramInt];
/*      */ 
/*  120 */     paramArrayOfByte[3] = (byte)((m == i1) || (m > n) ? n : m);
/*      */ 
/*  122 */     return paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] ldxads(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  142 */     if (paramInt2 != 0)
/*      */     {
/*  144 */       paramInt2 += ((paramArrayOfByte[4] - 1) * 60 + (paramArrayOfByte[5] - 1)) * 60 + (paramArrayOfByte[6] - 1);
/*  145 */       paramInt1 += paramInt2 / 86400;
/*      */ 
/*  147 */       if (paramInt2 %= 86400 < 0)
/*      */       {
/*  149 */         paramInt2 += 86400;
/*  150 */         paramInt1--;
/*      */       }
/*      */ 
/*  153 */       paramArrayOfByte[4] = (byte)(paramInt2 / 3600 + 1);
/*  154 */       paramArrayOfByte[5] = (byte)(paramInt2 % 3600 / 60 + 1);
/*  155 */       paramArrayOfByte[6] = (byte)(paramInt2 % 3600 % 60 + 1);
/*      */     }
/*      */ 
/*  159 */     int i = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);
/*      */ 
/*  162 */     if (paramInt1 != 0)
/*      */     {
/*  164 */       paramInt1 += ldxctj(i, paramArrayOfByte[2], paramArrayOfByte[3]);
/*      */ 
/*  166 */       if (paramInt1 < 1) {
/*  167 */         throw new SQLException(CoreException.getMessage(8));
/*      */       }
/*      */ 
/*  171 */       paramArrayOfByte = ldxjtc(paramInt1, paramArrayOfByte);
/*      */     }
/*      */ 
/*  175 */     i = ((paramArrayOfByte[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte[1] & 0xFF) - 100);
/*      */ 
/*  178 */     if (i > 9999) {
/*  179 */       throw new SQLException(CoreException.getMessage(8));
/*      */     }
/*      */ 
/*  182 */     return paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   public int ldxchk(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  214 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxdfd(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  230 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public void ldxdtd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/*  246 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public String ldxdts(byte[] paramArrayOfByte, String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/*  270 */     return ldxdts(paramArrayOfByte, ldxsto(paramString1, paramString2), paramString2);
/*      */   }
/*      */ 
/*      */   public String ldxdts(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
/*      */     throws SQLException
/*      */   {
/*  294 */     int i = 0;
/*      */ 
/*  296 */     int k = 0;
/*  297 */     int m = 0;
/*      */ 
/*  299 */     int i1 = 0;
/*      */ 
/*  301 */     int i3 = 0;
/*  302 */     int i4 = 0;
/*  303 */     String str1 = null;
/*      */ 
/*  306 */     StringBuffer localStringBuffer1 = new StringBuffer(64);
/*      */ 
/*  308 */     StringBuffer localStringBuffer2 = null;
/*      */ 
/*  320 */     if ((paramArrayOfByte2.length == 0) || (paramArrayOfByte2 == null) || ((paramArrayOfByte2[0] == 0) && (paramArrayOfByte2[1] == 16)))
/*      */     {
/*  323 */       paramArrayOfByte2 = DEFAULT_FORMAT;
/*      */     }
/*      */ 
/*  326 */     int n = paramArrayOfByte2.length;
/*      */     Locale localLocale;
/*  330 */     if ((paramString != null) && (paramString.compareTo("") != 0))
/*      */     {
/*  338 */       i4 = paramString.indexOf("_");
/*  339 */       if (i4 == -1)
/*      */       {
/*  341 */         localLocale = LxMetaData.getJavaLocale(paramString, "");
/*      */       }
/*      */       else
/*      */       {
/*  346 */         String str2 = paramString.substring(0, i4);
/*  347 */         String str3 = paramString.substring(i4 + 1);
/*  348 */         localLocale = LxMetaData.getJavaLocale(str2, str3);
/*      */       }
/*  350 */       if (localLocale == null)
/*      */       {
/*  352 */         localLocale = Locale.getDefault();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  357 */       localLocale = Locale.getDefault();
/*      */     }
/*      */ 
/*  365 */     i3 = ((paramArrayOfByte1[0] & 0xFF) - 100) * 100 + ((paramArrayOfByte1[1] & 0xFF) - 100);
/*      */ 
/*  370 */     while (m < n)
/*      */     {
/*  373 */       i = paramArrayOfByte2[(m++)];
/*  374 */       k = paramArrayOfByte2[(m++)];
/*      */ 
/*  377 */       if (i == 0)
/*      */       {
/*      */         break;
/*      */       }
/*  381 */       if (i >= 45)
/*      */       {
/*  384 */         int i2 = i - 45;
/*      */ 
/*  388 */         localStringBuffer1.append(new String(paramArrayOfByte2, m, i2));
/*      */ 
/*  390 */         m += i2;
/*      */ 
/*  392 */         continue;
/*      */       }
/*      */ 
/*  396 */       int j = ldxfcdlen[i];
/*      */       StringBuffer localStringBuffer3;
/*  402 */       if ((((j & 0xFFFFFF80) != 0) && ((k & 0xC) != 0)) || (((k & 0x10) != 0) && ((j & 0x40) == 0)) || (((k & 0xC) != 0) && ((k & 0x3) != 0)))
/*      */       {
/*  408 */         localStringBuffer2 = new StringBuffer(64);
/*  409 */         localStringBuffer3 = localStringBuffer2;
/*      */       }
/*      */       else {
/*  412 */         localStringBuffer3 = localStringBuffer1;
/*      */       }
/*      */       int i6;
/*      */       int i7;
/*  414 */       switch (i)
/*      */       {
/*      */       case 37:
/*  417 */         if ((localLocale.getCountry().compareTo("US") == 0) && (localLocale.getLanguage().compareTo("en") == 0))
/*      */         {
/*  420 */           str1 = paramArrayOfByte1[0] < 100 ? ldxpaa[2] : ldxpaa[0];
/*      */         }
/*      */         else
/*  423 */           i--;
/*      */       case 36:
/*  425 */         localStringBuffer3.append(new DateFormatSymbols(localLocale).getEras()[1]);
/*      */ 
/*  430 */         i6 = localStringBuffer3.length();
/*  431 */         break;
/*      */       case 35:
/*  433 */         if ((localLocale.getCountry().compareTo("US") == 0) && (localLocale.getLanguage().compareTo("en") == 0))
/*      */         {
/*  436 */           str1 = paramArrayOfByte1[4] - 1 >= 12 ? ldxpaa[3] : ldxpaa[1];
/*      */         }
/*      */         else
/*  439 */           i--;
/*      */       case 34:
/*  441 */         localStringBuffer3.append(new DateFormatSymbols(localLocale).getAmPmStrings()[0]);
/*      */ 
/*  445 */         i6 = localStringBuffer3.length();
/*  446 */         break;
/*      */       case 29:
/*  448 */         i4 = ldxctj(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]);
/*  449 */         break;
/*      */       case 21:
/*  451 */         i4 = ldxdow(i3, paramArrayOfByte1[2], paramArrayOfByte1[3], localLocale);
/*  452 */         break;
/*      */       case 32:
/*      */       case 33:
/*  456 */         i7 = ldxdow(i3, paramArrayOfByte1[2], paramArrayOfByte1[3], localLocale);
/*      */ 
/*  460 */         Calendar localCalendar = GregorianCalendar.getInstance(localLocale);
/*  461 */         if (localCalendar.getFirstDayOfWeek() > 1)
/*  462 */           i7++;
/*  463 */         if (i7 > 7)
/*  464 */           i7 -= 7;
/*  465 */         if (i7 == 0)
/*  466 */           i7++;
/*      */         String[] arrayOfString1;
/*  469 */         if (i == 32)
/*  470 */           arrayOfString1 = new DateFormatSymbols(localLocale).getWeekdays();
/*      */         else {
/*  472 */           arrayOfString1 = new DateFormatSymbols(localLocale).getShortWeekdays();
/*      */         }
/*  474 */         localStringBuffer3.append(arrayOfString1[i7]);
/*  475 */         i6 = localStringBuffer3.length();
/*  476 */         break;
/*      */       case 1:
/*      */       case 2:
/*  481 */         if ((i4 = i3) > 0)
/*  482 */           i4 = (i4 - 1) / 100 + 1;
/*      */         else
/*  484 */           i4 = -((-1 - i4) / 100) - 1;
/*  485 */         break;
/*      */       case 22:
/*  487 */         i4 = paramArrayOfByte1[3];
/*  488 */         break;
/*      */       case 43:
/*      */       case 44:
/*  491 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 24:
/*      */       case 25:
/*  496 */         i4 = paramArrayOfByte1[4] - 1;
/*  497 */         if (i == 25)
/*  498 */           i4 = i4 == 0 ? 12 : i4 > 12 ? i4 - 12 : i4; break;
/*      */       case 26:
/*  501 */         i4 = paramArrayOfByte1[5] - 1;
/*  502 */         break;
/*      */       case 16:
/*  504 */         i4 = (paramArrayOfByte1[2] + 2) / 3;
/*  505 */         break;
/*      */       case 17:
/*  507 */         i4 = paramArrayOfByte1[2];
/*  508 */         break;
/*      */       case 30:
/*      */       case 31:
/*  511 */         i4 = paramArrayOfByte1[2];
/*      */         String[] arrayOfString2;
/*  512 */         if (i == 30)
/*  513 */           arrayOfString2 = new DateFormatSymbols(localLocale).getMonths();
/*      */         else {
/*  515 */           arrayOfString2 = new DateFormatSymbols(localLocale).getShortMonths();
/*      */         }
/*      */ 
/*  518 */         i4--; localStringBuffer3.append(arrayOfString2[i4]);
/*  519 */         i6 = localStringBuffer3.length();
/*  520 */         break;
/*      */       case 38:
/*  522 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 27:
/*  526 */         i4 = paramArrayOfByte1[6] - 1;
/*  527 */         break;
/*      */       case 28:
/*  529 */         i4 = ((paramArrayOfByte1[4] - 1) * 60 + (paramArrayOfByte1[5] - 1)) * 60 + (paramArrayOfByte1[6] - 1);
/*      */ 
/*  531 */         break;
/*      */       case 20:
/*  533 */         i4 = (paramArrayOfByte1[3] + 6) / 7;
/*  534 */         break;
/*      */       case 23:
/*  536 */         i4 = ldxcty(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]);
/*  537 */         break;
/*      */       case 18:
/*  539 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 19:
/*  543 */         i4 = (ldxcty(i3, paramArrayOfByte1[2], paramArrayOfByte1[3]) + 6) / 7;
/*  544 */         break;
/*      */       case 3:
/*  546 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 4:
/*  550 */         i4 = i3 % 10;
/*  551 */         break;
/*      */       case 5:
/*  553 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 6:
/*      */       case 40:
/*  558 */         i4 = i3 % 100;
/*  559 */         break;
/*      */       case 7:
/*  561 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 8:
/*  565 */         i4 = i3 % 1000;
/*  566 */         break;
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 41:
/*  572 */         i4 = i3;
/*  573 */         break;
/*      */       case 9:
/*  575 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 14:
/*      */       case 15:
/*  580 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 42:
/*  584 */         break;
/*      */       case 39:
/*      */       default:
/*  586 */         throw new SQLException(CoreException.getMessage(7));
/*      */ 
/*  591 */         if ((j & 0xFFFFFF80) == 0)
/*      */         {
/*  596 */           if ((j & 0x20) == 0)
/*      */           {
/*  598 */             if (i4 < 0) {
/*  599 */               i4 = -i4;
/*      */             }
/*      */ 
/*      */           }
/*  607 */           else if (i4 >= 0)
/*      */           {
/*  609 */             localStringBuffer3.insert(0, " ");
/*  610 */             j = (byte)(j - 1);
/*      */           }
/*  612 */           if ((k & 0x2) != 0)
/*      */           {
/*  615 */             throw new SQLException(CoreException.getMessage(1));
/*      */           }
/*      */ 
/*  622 */           if ((k & 0x10) != 0)
/*  623 */             i6 = j & 0xF;
/*      */           else {
/*  625 */             i6 = 0;
/*      */           }
/*      */ 
/*  631 */           String str4 = lxi42b(64, i4, i6, (j & 0x10) != 0, localLocale);
/*      */ 
/*  634 */           localStringBuffer3.append(str4);
/*      */ 
/*  636 */           if ((k & 0x1) != 0)
/*      */           {
/*  639 */             throw new SQLException(CoreException.getMessage(1));
/*      */           }
/*      */ 
/*      */         }
/*  651 */         else if (((k & 0x10) != 0) && ((j & 0x40) == 0))
/*      */         {
/*      */           String[] arrayOfString3;
/*  656 */           switch (i)
/*      */           {
/*      */           case 33:
/*  659 */             arrayOfString3 = new DateFormatSymbols(localLocale).getShortWeekdays();
/*  660 */             break;
/*      */           case 32:
/*  662 */             arrayOfString3 = new DateFormatSymbols(localLocale).getWeekdays();
/*  663 */             break;
/*      */           case 31:
/*  665 */             arrayOfString3 = new DateFormatSymbols(localLocale).getShortMonths();
/*  666 */             break;
/*      */           case 30:
/*  668 */             arrayOfString3 = new DateFormatSymbols(localLocale).getMonths();
/*  669 */             break;
/*      */           default:
/*  672 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/*  677 */           i7 = 0;
/*      */ 
/*  680 */           for (int i8 = 0; i8 < arrayOfString3.length; i8++)
/*      */           {
/*  682 */             int i5 = arrayOfString3[i8].length();
/*  683 */             if (i5 > i7) {
/*  684 */               i7 = i5;
/*      */             }
/*      */           }
/*      */ 
/*  688 */           i7 -= localStringBuffer3.length();
/*  689 */           for (i8 = 0; i8 < i7; i8++) {
/*  690 */             localStringBuffer3.append(" ");
/*      */           }
/*      */         }
/*      */ 
/*  694 */         if (str1 != null)
/*      */         {
/*  697 */           i6 = 4;
/*      */ 
/*  699 */           if ((k & 0xC) == 0)
/*  700 */             localStringBuffer3 = localStringBuffer1;
/*  701 */           localStringBuffer3.append(str1.toLowerCase(localLocale));
/*  702 */           str1 = null;
/*      */         }
/*      */ 
/*  707 */         if ((k & 0x4) != 0)
/*      */         {
/*  710 */           if (Character.isLowerCase(localStringBuffer3.charAt(0))) {
/*  711 */             localStringBuffer3.setCharAt(0, Character.toUpperCase(localStringBuffer3.charAt(0)));
/*      */           }
/*  713 */           localStringBuffer1.append(localStringBuffer3.toString()); continue;
/*      */         }
/*      */ 
/*  716 */         if ((k & 0x8) != 0)
/*      */         {
/*  718 */           localStringBuffer1.append(localStringBuffer3.toString().toUpperCase()); continue;
/*      */         }
/*  720 */         if (localStringBuffer3 == localStringBuffer1)
/*      */           continue;
/*  722 */         localStringBuffer1.append(localStringBuffer3.toString());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  728 */     return localStringBuffer1.toString();
/*      */   }
/*      */ 
/*      */   private int ldxdow(int paramInt1, int paramInt2, int paramInt3, Locale paramLocale)
/*      */   {
/*  751 */     Calendar localCalendar = GregorianCalendar.getInstance(paramLocale);
/*      */ 
/*  754 */     int i = ldxctj(paramInt1, paramInt2, paramInt3);
/*      */ 
/*  757 */     int j = (i - (2445029 + (localCalendar.getFirstDayOfWeek() - 1))) % 7;
/*  758 */     if (j < 0)
/*  759 */       j += 7;
/*  760 */     return j + 1;
/*      */   }
/*      */ 
/*      */   private int ldxctj(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*      */     int i;
/*  776 */     if (paramInt1 == -4712) {
/*  777 */       i = 0;
/*      */     }
/*      */     else {
/*  780 */       i = paramInt1 + 4712;
/*      */ 
/*  786 */       i = 365 * i + (i - 1) / 4;
/*      */     }
/*      */ 
/*  790 */     if (paramInt1 >= 1583) {
/*  791 */       i = i - 10 - (paramInt1 - 1501) / 100 + (paramInt1 - 1201) / 400;
/*      */     }
/*      */ 
/*  795 */     i += ldxcty(paramInt1, paramInt2, paramInt3);
/*      */ 
/*  798 */     if ((paramInt1 == 1582) && (((paramInt2 == 10) && (paramInt3 >= 15)) || (paramInt2 >= 11))) {
/*  799 */       i -= 10;
/*      */     }
/*  801 */     return i;
/*      */   }
/*      */ 
/*      */   private byte[] ldxjtc(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  818 */     if (paramInt < 1) {
/*  819 */       throw new SQLException(CoreException.getMessage(10));
/*      */     }
/*  821 */     if (paramInt < 366)
/*      */     {
/*  824 */       paramArrayOfByte[0] = 53;
/*  825 */       paramArrayOfByte[1] = 28;
/*      */ 
/*  828 */       paramArrayOfByte = ldxdyc(-4712, paramInt, paramArrayOfByte);
/*      */     }
/*      */     else
/*      */     {
/*      */       int k;
/*      */       int i;
/*      */       int j;
/*  831 */       if (paramInt < 2299161)
/*      */       {
/*  835 */         paramInt -= 366;
/*      */ 
/*  840 */         k = -4711 + paramInt / 1461 * 4;
/*  841 */         paramInt %= 1461;
/*      */ 
/*  843 */         i = paramInt / 365;
/*  844 */         j = paramInt % 365;
/*      */ 
/*  846 */         if ((j == 0) && (i == 4))
/*      */         {
/*  849 */           j = 366;
/*  850 */           i = 3;
/*      */         }
/*      */         else {
/*  853 */           j++;
/*      */         }
/*  855 */         k += i;
/*      */ 
/*  857 */         paramArrayOfByte = ldxdyc(k, j, paramArrayOfByte);
/*      */ 
/*  859 */         paramArrayOfByte[0] = (byte)(k / 100 + 100);
/*  860 */         paramArrayOfByte[1] = (byte)(k % 100 + 100);
/*      */       }
/*      */       else
/*      */       {
/*  866 */         paramInt = 4 * (paramInt - 1721119) - 1;
/*  867 */         k = paramInt / 146097;
/*  868 */         paramInt %= 146097;
/*      */ 
/*  870 */         j = paramInt / 4;
/*      */ 
/*  872 */         paramInt = 4 * j + 3;
/*  873 */         j = paramInt % 1461;
/*  874 */         paramInt /= 1461;
/*      */ 
/*  876 */         j /= 4;
/*  877 */         j++;
/*      */ 
/*  879 */         i = 5 * j - 3;
/*  880 */         j = i % 153;
/*  881 */         i /= 153;
/*      */ 
/*  883 */         j /= 5;
/*  884 */         j++;
/*      */ 
/*  886 */         k *= 100;
/*  887 */         k += paramInt;
/*      */ 
/*  889 */         if (i < 10) {
/*  890 */           i += 3;
/*      */         }
/*      */         else {
/*  893 */           i -= 9;
/*  894 */           k++;
/*      */         }
/*      */ 
/*  897 */         paramArrayOfByte[3] = (byte)j;
/*  898 */         paramArrayOfByte[2] = (byte)i;
/*  899 */         paramArrayOfByte[0] = (byte)(k / 100 + 100);
/*  900 */         paramArrayOfByte[1] = (byte)(k % 100 + 100);
/*      */       }
/*      */     }
/*      */ 
/*  904 */     return paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   private byte[] ldxdyc(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  923 */     boolean bool = ldxisl(paramInt1);
/*      */ 
/*  925 */     if ((paramInt2 == 366) && (!bool)) {
/*  926 */       throw new SQLException(CoreException.getMessage(9));
/*      */     }
/*      */ 
/*  929 */     int i = paramInt2;
/*      */ 
/*  931 */     if (paramInt2 > 59 + (bool ? 1 : 0)) {
/*  932 */       i += 2 - (bool ? 1 : 0);
/*      */     }
/*  934 */     i += 91; int j = i * 100;
/*  935 */     paramArrayOfByte[3] = (byte)(i - (j - j % 3055) / 100);
/*  936 */     paramArrayOfByte[2] = (byte)(j / 3055 - 2);
/*      */ 
/*  938 */     return paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   private int ldxcty(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  957 */     return ldxdom[paramInt2] + paramInt3 + ((paramInt2 >= 3) && (ldxisl(paramInt1)) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   private boolean ldxisl(int paramInt)
/*      */   {
/*  970 */     return (paramInt % 4 == 0) && (paramInt <= 1582 ? paramInt == -4712 : (paramInt % 100 != 0) || (paramInt % 400 == 0));
/*      */   }
/*      */ 
/*      */   private String lxi42b(int paramInt1, long paramLong, int paramInt2, boolean paramBoolean, Locale paramLocale)
/*      */     throws SQLException
/*      */   {
/*  998 */     int i = 0;
/*  999 */     int j = 0;
/* 1000 */     long l = paramLong;
/* 1001 */     NumberFormat localNumberFormat = NumberFormat.getInstance(paramLocale);
/* 1002 */     DecimalFormat localDecimalFormat = (DecimalFormat)localNumberFormat;
/* 1003 */     StringBuffer localStringBuffer = new StringBuffer();
/* 1004 */     int k = localDecimalFormat.getGroupingSize();
/* 1005 */     char c1 = '\000';
/* 1006 */     char c2 = localDecimalFormat.getDecimalFormatSymbols().getZeroDigit();
/*      */ 
/* 1009 */     for (j = 1; l /= 10L != 0L; j++);
/* 1011 */     if (paramLong < 0L) {
/* 1012 */       j++;
/*      */     }
/* 1014 */     if (paramBoolean) {
/* 1015 */       j += (j - 1) / k;
/*      */     }
/*      */ 
/* 1018 */     if (!paramBoolean) {
/* 1019 */       localNumberFormat.setGroupingUsed(false);
/*      */     }
/* 1021 */     localStringBuffer.append(localDecimalFormat.format(paramLong));
/*      */ 
/* 1023 */     if ((j > paramInt1) || (paramInt2 > paramInt1) || ((paramInt2 != 0) && (j > paramInt2)))
/*      */     {
/* 1025 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/* 1028 */     if (paramBoolean) {
/* 1029 */       c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
/*      */     }
/* 1031 */     if (paramInt2 != 0)
/*      */     {
/* 1033 */       for (paramInt2 -= j; paramInt2-- != 0; )
/*      */       {
/* 1035 */         if ((paramBoolean) && (i++ == k) && (paramInt2 != 0))
/*      */         {
/* 1037 */           localStringBuffer.insert(0, c1);
/* 1038 */           i = 1;
/* 1039 */           paramInt2--;
/*      */         }
/* 1041 */         localStringBuffer.insert(0, c2);
/*      */       }
/*      */     }
/*      */ 
/* 1045 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public byte[] ldxsto(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 1060 */     int i = 0;
/* 1061 */     int j = 0;
/*      */ 
/* 1063 */     int m = 0;
/* 1064 */     byte[] arrayOfByte1 = new byte[512];
/* 1065 */     int n = 16;
/* 1066 */     int i1 = 0;
/* 1067 */     int i2 = 0;
/* 1068 */     char[] arrayOfChar = new char[256];
/* 1069 */     int i3 = 0;
/*      */ 
/* 1072 */     int i4 = 0;
/*      */ 
/* 1075 */     if ((paramString1 == null) || (paramString1.compareTo("") == 0))
/*      */     {
/* 1078 */       return NULLFMT;
/*      */     }
/*      */ 
/* 1081 */     int k = paramString1.length();
/*      */ 
/* 1084 */     while (i < k)
/*      */     {
/* 1087 */       n = 16;
/* 1088 */       i2 = 0;
/*      */       do
/*      */       {
/* 1094 */         if ((i < k) && (paramString1.charAt(i) == '|'))
/*      */         {
/* 1096 */           i++;
/* 1097 */           break;
/*      */         }
/*      */ 
/* 1101 */         for (i3 = 0; (i < k) && (!Character.isLetterOrDigit(paramString1.charAt(i))); )
/*      */         {
/* 1104 */           if (paramString1.charAt(i) == '"')
/*      */           {
/* 1107 */             i1 = 1;
/*      */ 
/* 1109 */             while (i != k) { i++; if (paramString1.charAt(i) == '"')
/*      */                 break;
/* 1111 */               arrayOfChar[(i2++)] = paramString1.charAt(i);
/* 1112 */               i3++;
/*      */             }
/* 1114 */             if (paramString1.charAt(i) == '"') {
/* 1115 */               i++; continue;
/*      */             }
/*      */           }
/*      */ 
/* 1119 */           arrayOfChar[(i2++)] = paramString1.charAt(i++);
/* 1120 */           i3++;
/*      */         }
/*      */         int i5;
/* 1125 */         if (i3 > 0)
/*      */         {
/* 1127 */           if (i3 > 210)
/*      */           {
/* 1130 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/* 1135 */           if (Character.isWhitespace(arrayOfChar[0]))
/*      */           {
/* 1140 */             int i10 = 0;
/*      */ 
/* 1142 */             i5 = i3; int i8 = 0;
/* 1143 */             while ((i5 > 0) && (Character.isWhitespace(arrayOfChar[i10]))) {
/* 1144 */               i10++; i8++; i5--;
/* 1145 */             }arrayOfByte1[(j++)] = (byte)(45 + i8);
/*      */ 
/* 1147 */             arrayOfByte1[(j++)] = 2;
/*      */ 
/* 1153 */             byte[] arrayOfByte4 = new String(arrayOfChar, 0, i8).getBytes();
/* 1154 */             System.arraycopy(arrayOfByte4, 0, arrayOfByte1, j, arrayOfByte4.length);
/*      */ 
/* 1157 */             j += arrayOfByte4.length;
/*      */ 
/* 1159 */             i3 -= i8;
/* 1160 */             if (i3 == 0)
/*      */             {
/*      */               continue;
/*      */             }
/*      */ 
/* 1166 */             i += i8 + 1;
/*      */ 
/* 1168 */             i2 = i10;
/*      */           }
/*      */           else {
/* 1171 */             i2 = 0;
/*      */           }
/* 1173 */           if (i1 != 1) {
/* 1174 */             i1 = 4;
/*      */           }
/* 1176 */           arrayOfByte1[(j++)] = (byte)(45 + i3);
/* 1177 */           arrayOfByte1[(j++)] = i1;
/*      */ 
/* 1183 */           byte[] arrayOfByte2 = new String(arrayOfChar, 0, i3).getBytes();
/* 1184 */           System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, arrayOfByte2.length);
/*      */ 
/* 1186 */           j += arrayOfByte2.length;
/*      */         }
/*      */         else
/*      */         {
/* 1193 */           if (!Character.isLetterOrDigit(paramString1.charAt(i)))
/*      */           {
/* 1195 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/* 1199 */           char c = Character.toUpperCase(paramString1.charAt(i));
/* 1200 */           i5 = c - 'A';
/* 1201 */           if ((i5 > 25) || (ldxfdi[i5] == -2147483648))
/*      */           {
/* 1204 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/* 1209 */           i4 = ldxfdi[i5];
/*      */ 
/* 1214 */           i5 = 50;
/*      */           int i9;
/*      */           int i6;
/*      */           int i7;
/* 1215 */           for (; i4 < ldxfda.length; i4++)
/*      */           {
/* 1218 */             i9 = ldxfda[i4].length;
/* 1219 */             i6 = 0; i7 = i;
/* 1220 */             for (; (i6 < i9) && (i7 < k); i7++)
/*      */             {
/* 1222 */               if (Character.toUpperCase(paramString1.charAt(i7)) != ldxfda[i4][i6])
/*      */                 break;
/* 1220 */               i6++;
/*      */             }
/*      */ 
/* 1227 */             if (i6 == i9)
/*      */             {
/* 1229 */               i5 = i4;
/*      */             }
/*      */ 
/* 1232 */             if (ldxfda[(i4 + 1)][0] != c) {
/*      */               break;
/*      */             }
/*      */           }
/* 1236 */           i4 = i5;
/*      */ 
/* 1239 */           if (i4 >= ldxfda.length)
/*      */           {
/* 1241 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/* 1246 */           if (k - i > 1)
/*      */           {
/* 1248 */             if (Character.isUpperCase(paramString1.charAt(i)))
/*      */             {
/* 1250 */               c = Character.isLetterOrDigit(paramString1.charAt(i + 1)) ? paramString1.charAt(i + 1) : paramString1.charAt(i + 2);
/*      */ 
/* 1253 */               if (Character.isLowerCase(c))
/* 1254 */                 n = (byte)(n | 0x4);
/*      */               else {
/* 1256 */                 n = (byte)(n | 0x8);
/*      */               }
/*      */             }
/*      */           }
/* 1260 */           i += ldxfda[i4].length;
/* 1261 */           m = ldxfdc[i4];
/*      */ 
/* 1263 */           if ((ldxfcdlen[m] & 0xFFFFFF80) == 0)
/*      */           {
/* 1265 */             i4 = 0; for (i6 = -1; i4 < ldxfdx.length; i4++)
/*      */             {
/* 1267 */               i9 = ldxfdx[i4].length;
/* 1268 */               i5 = 0; i7 = i;
/* 1269 */               for (; (i5 < i9) && (i7 < k); i7++)
/*      */               {
/* 1272 */                 if (Character.toUpperCase(paramString1.charAt(i7)) != ldxfdx[i4][i5])
/*      */                   break;
/* 1269 */                 i5++;
/*      */               }
/*      */ 
/* 1278 */               if (i5 != i9)
/*      */                 continue;
/* 1280 */               i6 = i4;
/*      */             }
/*      */ 
/* 1284 */             i4 = i6;
/*      */ 
/* 1287 */             if ((i4 >= 0) && (i4 < ldxfdx.length))
/*      */             {
/* 1289 */               n = (byte)(n | ldxfdxc[i4]);
/* 1290 */               i += ldxfdx[i4].length;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1295 */           if (512 - j < 2)
/*      */           {
/* 1299 */             throw new SQLException(CoreException.getMessage(7));
/*      */           }
/*      */ 
/* 1303 */           arrayOfByte1[(j++)] = (byte)m;
/* 1304 */           arrayOfByte1[(j++)] = n;
/*      */         }
/*      */ 
/* 1308 */         if (m == 39)
/* 1309 */           n = (n & 0x10) == 1 ? 0 : 16;
/*      */       }
/* 1311 */       while (m == 39);
/*      */     }
/*      */ 
/* 1315 */     byte[] arrayOfByte3 = new byte[j];
/* 1316 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte3.length);
/* 1317 */     return arrayOfByte3;
/*      */   }
/*      */ 
/*      */   public byte[] ldxdyf(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1327 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public void ldxftd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/* 1339 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxgdt()
/*      */     throws SQLException
/*      */   {
/* 1350 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxldd(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1361 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxnxd(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1375 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxrnd(byte[] paramArrayOfByte, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1389 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxsbm(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*      */     throws SQLException
/*      */   {
/* 1403 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public void ldxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/* 1417 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   public byte[] ldxstd(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 1499 */     byte[] arrayOfByte1 = null;
/* 1500 */     byte[] arrayOfByte2 = null;
/* 1501 */     int i = 0;
/* 1502 */     int j = 0;
/* 1503 */     char[] arrayOfChar = new char[512];
/* 1504 */     int k = 0;
/* 1505 */     int m = 0;
/* 1506 */     int n = 0;
/* 1507 */     int i1 = 0;
/* 1508 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 1509 */     SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat();
/*      */ 
/* 1512 */     arrayOfByte2 = ldxsto(paramString2, paramString3);
/*      */ 
/* 1515 */     ldxsti(arrayOfByte2);
/*      */ 
/* 1517 */     m = arrayOfByte2.length;
/*      */ 
/* 1521 */     while (k < m)
/*      */     {
/* 1523 */       i = arrayOfByte2[(k++)];
/* 1524 */       j = arrayOfByte2[(k++)];
/*      */       int i2;
/* 1526 */       switch (i)
/*      */       {
/*      */       case 43:
/*      */       case 44:
/* 1530 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 41:
/* 1534 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 40:
/* 1538 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 4:
/* 1542 */         arrayOfChar[(n++)] = 'y';
/* 1543 */         break;
/*      */       case 6:
/* 1546 */         for (i2 = 0; i2 < 2; i2++) {
/* 1547 */           arrayOfChar[(n++)] = 'y';
/*      */         }
/*      */ 
/*      */       case 8:
/* 1551 */         for (i2 = 0; i2 < 3; i2++) {
/* 1552 */           arrayOfChar[(n++)] = 'y';
/*      */         }
/*      */ 
/*      */       case 10:
/* 1556 */         for (i2 = 0; i2 < 4; i2++) {
/* 1557 */           arrayOfChar[(n++)] = 'y';
/*      */         }
/*      */ 
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/* 1563 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 38:
/* 1567 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 17:
/* 1571 */         arrayOfChar[(n++)] = 'M';
/* 1572 */         arrayOfChar[(n++)] = 'M';
/* 1573 */         break;
/*      */       case 31:
/* 1576 */         for (i2 = 0; i2 < 3; i2++) {
/* 1577 */           arrayOfChar[(n++)] = 'M';
/*      */         }
/*      */ 
/*      */       case 30:
/* 1581 */         for (i2 = 0; i2 < 4; i2++) {
/* 1582 */           arrayOfChar[(n++)] = 'M';
/*      */         }
/*      */ 
/*      */       case 21:
/*      */       case 33:
/* 1587 */         arrayOfChar[(n++)] = 'E';
/* 1588 */         break;
/*      */       case 32:
/* 1591 */         for (i2 = 0; i2 < 4; i2++) {
/* 1592 */           arrayOfChar[(n++)] = 'E';
/*      */         }
/*      */ 
/*      */       case 22:
/* 1596 */         arrayOfChar[(n++)] = 'd';
/* 1597 */         break;
/*      */       case 23:
/* 1600 */         arrayOfChar[(n++)] = 'D';
/* 1601 */         break;
/*      */       case 29:
/* 1604 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 25:
/* 1608 */         arrayOfChar[(n++)] = 'h';
/* 1609 */         break;
/*      */       case 24:
/* 1612 */         arrayOfChar[(n++)] = 'H';
/* 1613 */         break;
/*      */       case 26:
/* 1616 */         arrayOfChar[(n++)] = 'm';
/* 1617 */         break;
/*      */       case 27:
/* 1620 */         arrayOfChar[(n++)] = 's';
/* 1621 */         break;
/*      */       case 28:
/* 1624 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 34:
/*      */       case 35:
/* 1629 */         arrayOfChar[(n++)] = 'a';
/* 1630 */         break;
/*      */       case 36:
/*      */       case 37:
/* 1634 */         arrayOfChar[(n++)] = 'G';
/* 1635 */         break;
/*      */       case 39:
/*      */       case 42:
/* 1638 */         throw new SQLException(CoreException.getMessage(1));
/*      */       case 5:
/*      */       case 7:
/*      */       case 9:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       default:
/* 1641 */         i1 = i - 45;
/* 1642 */         str = new String(arrayOfByte2, k, i1);
/*      */ 
/* 1644 */         if (j == 1)
/*      */         {
/* 1646 */           arrayOfChar[(n++)] = '\'';
/* 1647 */           System.arraycopy(str.toCharArray(), 0, arrayOfChar, n, i1);
/*      */ 
/* 1649 */           n += i1;
/* 1650 */           k += i1;
/* 1651 */           arrayOfChar[(n++)] = '\''; continue;
/*      */         }
/*      */ 
/* 1655 */         System.arraycopy(str.toCharArray(), 0, arrayOfChar, n, i1);
/*      */ 
/* 1657 */         n += i1;
/* 1658 */         k += i1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1667 */     String str = new String(arrayOfChar, 0, n);
/* 1668 */     localSimpleDateFormat.applyPattern(str);
/* 1669 */     localSimpleDateFormat.setLenient(false);
/* 1670 */     Date localDate = localSimpleDateFormat.parse(paramString1, localParsePosition);
/* 1671 */     if (localDate != null)
/*      */     {
/* 1673 */       return arrayOfByte1 = DATE.toBytes(new Timestamp(localDate.getTime()));
/*      */     }
/*      */ 
/* 1676 */     throw new SQLException(CoreException.getMessage(6));
/*      */   }
/*      */ 
/*      */   public byte[] ldxtrn(byte[] paramArrayOfByte, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1691 */     throw new SQLException(CoreException.getMessage(1));
/*      */   }
/*      */ 
/*      */   private void ldxsti(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1698 */     int[] arrayOfInt = new int[46];
/*      */     int j;
/* 1701 */     for (int i = 0; i < paramArrayOfByte.length; i += 2)
/*      */     {
/* 1704 */       if (paramArrayOfByte[i] < 45)
/*      */       {
/* 1706 */         j = paramArrayOfByte[i];
/*      */ 
/* 1711 */         if ((paramArrayOfByte[i] != 42) && (paramArrayOfByte[i] != 39) && (arrayOfInt[paramArrayOfByte[i]] != 0))
/*      */         {
/* 1713 */           throw new SQLException(CoreException.getMessage(7));
/*      */         }
/*      */ 
/* 1718 */         arrayOfInt[paramArrayOfByte[i]] += 1;
/*      */ 
/* 1721 */         switch (paramArrayOfByte[i])
/*      */         {
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 5:
/*      */         case 7:
/*      */         case 9:
/*      */         case 14:
/*      */         case 15:
/*      */         case 16:
/*      */         case 18:
/*      */         case 19:
/*      */         case 20:
/* 1735 */           throw new SQLException(CoreException.getMessage(7));
/*      */         case 4:
/*      */         case 6:
/*      */         case 8:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/* 1742 */         case 17: }  } else { i += paramArrayOfByte[i] - 45;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1755 */     for (i = 0; i < this.ldxpmxa.length; i++)
/*      */     {
/* 1757 */       j = 0;
/*      */ 
/* 1759 */       for (int k = 0; k < this.ldxpmxa[i].length; k++) {
/* 1760 */         j += arrayOfInt[this.ldxpmxa[i][k]];
/*      */       }
/* 1762 */       if (j <= 1)
/*      */         continue;
/* 1764 */       throw new SQLException(CoreException.getMessage(7));
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.LdxLibThin
 * JD-Core Version:    0.6.0
 */