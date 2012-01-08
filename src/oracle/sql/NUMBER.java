/*      */ package oracle.sql;
/*      */ 
/*      */ import B;
/*      */ import java.io.PrintStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.sql.SQLException;
/*      */ import oracle.core.lmx.CoreException;
/*      */ 
/*      */ public class NUMBER extends Datum
/*      */ {
/*  352 */   static byte[] MAX_LONG = toBytes(9223372036854775807L);
/*  353 */   static byte[] MIN_LONG = toBytes(-9223372036854775808L);
/*      */   private static final int CHARACTER_ZERO = 48;
/* 2906 */   private static final BigDecimal BIGDEC_NEGZERO = new BigDecimal("-0");
/* 2907 */   private static final BigDecimal BIGDEC_ZERO = BigDecimal.valueOf(0L);
/* 2908 */   private static final BigDecimal BIGDEC_ONE = BigDecimal.valueOf(1L);
/* 2909 */   private static final BigInteger BIGINT_ZERO = BigInteger.valueOf(0L);
/* 2910 */   private static final BigInteger BIGINT_HUND = BigInteger.valueOf(100L);
/*      */   private static final byte DIGEND = 21;
/*      */   private static final byte ODIGEND = 9;
/*      */   private static final int HUNDIGMAX = 66;
/*      */   private static final int BIGINTARRAYMAX = 54;
/*      */   private static final double BIGRATIO = 0.150514997831991D;
/*      */   private static final int BIGLENMAX = 22;
/*      */   static final byte LNXM_NUM = 22;
/*      */   static final int LNXSGNBT = 128;
/*      */   static final byte LNXDIGS = 20;
/*      */   static final byte LNXEXPBS = 64;
/*      */   static final double ORANUM_FBASE = 100.0D;
/*      */   static final int LNXBASE = 100;
/*      */   static final byte IEEE_DBL_DIG = 15;
/*      */   private static final byte IEEE_FLT_DIG = 6;
/*      */   static final int LNXEXPMX = 127;
/*      */   static final int LNXEXPMN = 0;
/*      */   static final int LNXMXOUT = 40;
/*      */   static final int LNXMXFMT = 64;
/*      */   private static final byte BYTE_MAX_VALUE = 127;
/*      */   private static final byte BYTE_MIN_VALUE = -128;
/*      */   private static final short SHORT_MAX_VALUE = 32767;
/*      */   private static final short SHORT_MIN_VALUE = -32768;
/* 3006 */   private static final byte[] PI = { -63, 4, 15, 16, 93, 66, 36, 90, 80, 33, 39, 47, 27, 44, 39, 33, 80, 51, 29, 85, 21 };
/*      */ 
/* 3010 */   private static final byte[] E = { -63, 3, 72, 83, 82, 83, 85, 60, 5, 53, 36, 37, 3, 88, 48, 14, 53, 67, 25, 98, 77 };
/*      */ 
/* 3014 */   private static final byte[] LN10 = { -63, 3, 31, 26, 86, 10, 30, 95, 5, 57, 85, 2, 80, 92, 46, 47, 85, 37, 43, 8, 61 };
/*      */   private static LnxLib _slnxlib;
/* 3027 */   private static LnxLib _thinlib = null;
/*      */ 
/* 3030 */   private static int DBL_MAX = 40;
/*      */ 
/* 3032 */   private static int INT_MAX = 15;
/* 3033 */   private static float FLOAT_MAX_INT = 2.147484E+09F;
/* 3034 */   private static float FLOAT_MIN_INT = -2.147484E+09F;
/* 3035 */   private static double DOUBLE_MAX_INT = 2147483647.0D;
/* 3036 */   private static double DOUBLE_MIN_INT = -2147483648.0D;
/* 3037 */   private static double DOUBLE_MAX_INT_2 = 2147483649.0D;
/* 3038 */   private static double DOUBLE_MIN_INT_2 = -2147483649.0D;
/*      */ 
/* 3403 */   private static Object drvType = null;
/*      */   private static String LANGID;
/*      */ 
/*      */   public NUMBER()
/*      */   {
/*  113 */     super(_makeZero());
/*      */   }
/*      */ 
/*      */   public NUMBER(byte[] paramArrayOfByte)
/*      */   {
/*  125 */     super(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public NUMBER(byte paramByte)
/*      */   {
/*  135 */     super(toBytes(paramByte));
/*      */   }
/*      */ 
/*      */   public NUMBER(int paramInt)
/*      */   {
/*  145 */     super(toBytes(paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER(long paramLong)
/*      */   {
/*  154 */     super(toBytes(paramLong));
/*      */   }
/*      */ 
/*      */   public NUMBER(short paramShort)
/*      */   {
/*  163 */     super(toBytes(paramShort));
/*      */   }
/*      */ 
/*      */   public NUMBER(float paramFloat)
/*      */   {
/*  172 */     super(toBytes(paramFloat));
/*      */   }
/*      */ 
/*      */   public NUMBER(double paramDouble)
/*      */     throws SQLException
/*      */   {
/*  183 */     super(toBytes(paramDouble));
/*      */   }
/*      */ 
/*      */   public NUMBER(BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/*  210 */     super(toBytes(paramBigDecimal));
/*      */   }
/*      */ 
/*      */   public NUMBER(BigInteger paramBigInteger)
/*      */     throws SQLException
/*      */   {
/*  225 */     super(toBytes(paramBigInteger));
/*      */   }
/*      */ 
/*      */   public NUMBER(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  241 */     super(toBytes(paramString, paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER(boolean paramBoolean)
/*      */   {
/*  251 */     super(toBytes(paramBoolean));
/*      */   }
/*      */ 
/*      */   public NUMBER(Object paramObject)
/*      */     throws SQLException
/*      */   {
/*  262 */     if ((paramObject instanceof Integer))
/*  263 */       setShareBytes(toBytes(((Integer)paramObject).intValue()));
/*  264 */     else if ((paramObject instanceof Long))
/*  265 */       setShareBytes(toBytes(((Long)paramObject).longValue()));
/*  266 */     else if ((paramObject instanceof Float))
/*  267 */       setShareBytes(toBytes(((Float)paramObject).floatValue()));
/*  268 */     else if ((paramObject instanceof Double))
/*  269 */       setShareBytes(toBytes(((Double)paramObject).doubleValue()));
/*  270 */     else if ((paramObject instanceof BigInteger))
/*  271 */       setShareBytes(toBytes((BigInteger)paramObject));
/*  272 */     else if ((paramObject instanceof BigDecimal))
/*  273 */       setShareBytes(toBytes((BigDecimal)paramObject));
/*  274 */     else if ((paramObject instanceof Boolean))
/*  275 */       setShareBytes(toBytes(((Boolean)paramObject).booleanValue()));
/*  276 */     else if ((paramObject instanceof String)) {
/*  277 */       setShareBytes(stringToBytes((String)paramObject));
/*      */     }
/*      */     else
/*  280 */       throw new SQLException("Initialization failed");
/*      */   }
/*      */ 
/*      */   public static double toDouble(byte[] paramArrayOfByte)
/*      */   {
/*  303 */     if (_isZero(paramArrayOfByte)) {
/*  304 */       return 0.0D;
/*      */     }
/*  306 */     if (_isPosInf(paramArrayOfByte)) {
/*  307 */       return (1.0D / 0.0D);
/*      */     }
/*  309 */     if (_isNegInf(paramArrayOfByte)) {
/*  310 */       return (-1.0D / 0.0D);
/*      */     }
/*      */ 
/*  317 */     String str = null;
/*      */     try
/*      */     {
/*  321 */       if (drvType == null)
/*      */       {
/*  323 */         str = _slnxlib.lnxnuc(paramArrayOfByte, DBL_MAX, null);
/*      */       }
/*  325 */       else str = _slnxlib.lnxnuc(paramArrayOfByte, DBL_MAX, LANGID);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/*  331 */     double d = Double.valueOf(str).doubleValue();
/*      */ 
/*  333 */     return d;
/*      */   }
/*      */ 
/*      */   public static float toFloat(byte[] paramArrayOfByte)
/*      */   {
/*  344 */     return (float)toDouble(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public static long toLong(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  368 */     if (_isZero(paramArrayOfByte)) {
/*  369 */       return 0L;
/*      */     }
/*      */ 
/*  373 */     if ((_isInf(paramArrayOfByte)) || (compareBytes(paramArrayOfByte, MAX_LONG) > 0) || (compareBytes(paramArrayOfByte, MIN_LONG) < 0))
/*      */     {
/*  376 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*  378 */     return _getLnxLib().lnxsni(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public static int toInt(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  394 */     if (_isInf(paramArrayOfByte))
/*  395 */       throw new SQLException(CoreException.getMessage(3));
/*      */     String str;
/*  410 */     if (drvType == null)
/*      */     {
/*  412 */       str = _slnxlib.lnxnuc(paramArrayOfByte, INT_MAX, null);
/*      */     }
/*  414 */     else str = _slnxlib.lnxnuc(paramArrayOfByte, INT_MAX, LANGID);
/*      */ 
/*  417 */     double d = Double.valueOf(str).doubleValue();
/*      */ 
/*  420 */     if (((float)d > FLOAT_MAX_INT) || ((float)d < FLOAT_MIN_INT)) {
/*  421 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/*  424 */     if ((d > DOUBLE_MAX_INT) && (d <= DOUBLE_MAX_INT_2)) {
/*  425 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*  427 */     if ((d < DOUBLE_MIN_INT) && (d >= DOUBLE_MIN_INT_2))
/*  428 */       throw new SQLException(CoreException.getMessage(3));
/*  429 */     int i = (int)d;
/*  430 */     return i;
/*      */   }
/*      */ 
/*      */   public static short toShort(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  442 */     long l = 0L;
/*      */     try
/*      */     {
/*  445 */       l = toLong(paramArrayOfByte);
/*      */ 
/*  447 */       if ((l > 32767L) || (l < -32768L)) {
/*      */         throw new SQLException(CoreException.getMessage(3));
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  455 */     return (short)(int)l;
/*      */   }
/*      */ 
/*      */   public static byte toByte(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  466 */     long l = 0L;
/*      */     try
/*      */     {
/*  469 */       l = toLong(paramArrayOfByte);
/*      */ 
/*  471 */       if ((l > 127L) || (l < -128L)) {
/*      */         throw new SQLException(CoreException.getMessage(3));
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  479 */     return (byte)(int)l;
/*      */   }
/*      */ 
/*      */   public static BigInteger toBigInteger(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  491 */     long[] arrayOfLong = new long[10];
/*  492 */     int i = 9;
/*  493 */     int j = 1;
/*      */ 
/*  498 */     int i1 = 0;
/*      */ 
/*  504 */     if (_isZero(paramArrayOfByte)) {
/*  505 */       return BIGINT_ZERO;
/*      */     }
/*  507 */     if (_isInf(paramArrayOfByte)) {
/*  508 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/*  514 */     boolean bool = _isPositive(paramArrayOfByte);
/*      */ 
/*  517 */     byte[] arrayOfByte1 = _fromLnxFmt(paramArrayOfByte);
/*      */ 
/*  520 */     if (arrayOfByte1[0] < 0)
/*      */     {
/*  522 */       return BIGINT_ZERO;
/*      */     }
/*      */ 
/*  528 */     int i2 = Math.min(arrayOfByte1[0] + 1, arrayOfByte1.length - 1);
/*  529 */     int k = i2;
/*      */ 
/*  532 */     if ((i2 & 0x1) == 1)
/*      */     {
/*  534 */       arrayOfLong[i] = arrayOfByte1[j];
/*  535 */       j = (byte)(j + 1);
/*  536 */       k--;
/*      */     }
/*      */     else
/*      */     {
/*  540 */       arrayOfLong[i] = (arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)]);
/*  541 */       j = (byte)(j + 2);
/*  542 */       k -= 2;
/*      */     }
/*      */ 
/*  546 */     int m = i;
/*  547 */     while (k != 0)
/*      */     {
/*  549 */       long l = arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)];
/*      */ 
/*  551 */       for (i = 9; i >= m; i = (byte)(i - 1))
/*      */       {
/*  553 */         l += arrayOfLong[i] * 10000L;
/*  554 */         arrayOfLong[i] = (l & 0xFFFF);
/*  555 */         l >>= 16;
/*      */       }
/*      */ 
/*  558 */       if (l != 0L);
/*  560 */       m = (byte)(m - 1);
/*  561 */       arrayOfLong[m] = l;
/*      */ 
/*  564 */       j = (byte)(j + 2);
/*  565 */       k -= 2;
/*      */     }
/*      */     int n;
/*  569 */     if (arrayOfLong[m] >> 8 != 0L)
/*  570 */       n = 2 * (9 - m) + 2;
/*      */     else {
/*  572 */       n = 2 * (9 - m) + 1;
/*      */     }
/*  574 */     byte[] arrayOfByte2 = new byte[n];
/*      */ 
/*  576 */     if ((n & 0x1) == 1)
/*      */     {
/*  578 */       arrayOfByte2[i1] = (byte)(int)arrayOfLong[m];
/*  579 */       i1++;
/*      */     }
/*      */     else
/*      */     {
/*  583 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] >> 8);
/*  584 */       i1++;
/*  585 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] & 0xFF);
/*  586 */       i1++;
/*      */     }
/*      */ 
/*  591 */     for (m = (byte)(m + 1); m <= 9; m = (byte)(m + 1))
/*      */     {
/*  593 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] >> 8);
/*  594 */       arrayOfByte2[(i1 + 1)] = (byte)(int)(arrayOfLong[m] & 0xFF);
/*  595 */       i1 += 2;
/*      */     }
/*      */ 
/*  599 */     BigInteger localBigInteger = new BigInteger(bool ? 1 : -1, arrayOfByte2);
/*      */ 
/*  602 */     int i3 = arrayOfByte1[0] - (i2 - 1);
/*  603 */     return localBigInteger.multiply(BIGINT_HUND.pow(i3));
/*      */   }
/*      */ 
/*      */   public static BigDecimal toBigDecimal(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  617 */     long[] arrayOfLong = new long[10];
/*  618 */     int i = 9;
/*  619 */     int j = 1;
/*      */ 
/*  624 */     int i1 = 0;
/*      */ 
/*  630 */     if (_isZero(paramArrayOfByte)) {
/*  631 */       return BIGDEC_ZERO;
/*      */     }
/*  633 */     if (_isInf(paramArrayOfByte)) {
/*  634 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/*  638 */     boolean bool = _isPositive(paramArrayOfByte);
/*      */ 
/*  641 */     byte[] arrayOfByte1 = _fromLnxFmt(paramArrayOfByte);
/*      */     int i2;
/*  645 */     int k = i2 = arrayOfByte1.length - 1;
/*      */ 
/*  648 */     if ((i2 & 0x1) == 1)
/*      */     {
/*  650 */       arrayOfLong[i] = arrayOfByte1[j];
/*  651 */       j = (byte)(j + 1);
/*  652 */       k--;
/*      */     }
/*      */     else
/*      */     {
/*  656 */       arrayOfLong[i] = (arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)]);
/*  657 */       j = (byte)(j + 2);
/*  658 */       k -= 2;
/*      */     }
/*      */ 
/*  662 */     int m = i;
/*  663 */     while (k != 0)
/*      */     {
/*  665 */       long l = arrayOfByte1[j] * 100 + arrayOfByte1[(j + 1)];
/*      */ 
/*  667 */       for (i = 9; i >= m; i = (byte)(i - 1))
/*      */       {
/*  669 */         l += arrayOfLong[i] * 10000L;
/*  670 */         arrayOfLong[i] = (l & 0xFFFF);
/*  671 */         l >>= 16;
/*      */       }
/*      */ 
/*  674 */       if (l != 0L);
/*  676 */       m = (byte)(m - 1);
/*  677 */       arrayOfLong[m] = l;
/*      */ 
/*  680 */       j = (byte)(j + 2);
/*  681 */       k -= 2;
/*      */     }
/*      */     int n;
/*  685 */     if (arrayOfLong[m] >> 8 != 0L)
/*  686 */       n = 2 * (9 - m) + 2;
/*      */     else {
/*  688 */       n = 2 * (9 - m) + 1;
/*      */     }
/*  690 */     byte[] arrayOfByte2 = new byte[n];
/*      */ 
/*  692 */     if ((n & 0x1) == 1)
/*      */     {
/*  694 */       arrayOfByte2[i1] = (byte)(int)arrayOfLong[m];
/*  695 */       i1++;
/*      */     }
/*      */     else
/*      */     {
/*  699 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] >> 8);
/*  700 */       i1++;
/*  701 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] & 0xFF);
/*  702 */       i1++;
/*      */     }
/*      */ 
/*  707 */     for (m = (byte)(m + 1); m <= 9; m = (byte)(m + 1))
/*      */     {
/*  709 */       arrayOfByte2[i1] = (byte)(int)(arrayOfLong[m] >> 8);
/*  710 */       arrayOfByte2[(i1 + 1)] = (byte)(int)(arrayOfLong[m] & 0xFF);
/*  711 */       i1 += 2;
/*      */     }
/*      */ 
/*  715 */     BigInteger localBigInteger = new BigInteger(bool ? 1 : -1, arrayOfByte2);
/*  716 */     BigDecimal localBigDecimal = new BigDecimal(localBigInteger);
/*      */ 
/*  719 */     int i3 = arrayOfByte1[0] - i2 + 1;
/*      */ 
/*  722 */     localBigDecimal = localBigDecimal.movePointRight(i3 * 2);
/*      */ 
/*  728 */     if ((i3 < 0) && (arrayOfByte1[i2] % 10 == 0))
/*  729 */       localBigDecimal = localBigDecimal.setScale(-(i3 * 2 + 1));
/*  730 */     return localBigDecimal;
/*      */   }
/*      */ 
/*      */   public static String toString(byte[] paramArrayOfByte)
/*      */   {
/*  746 */     int i = 0;
/*      */ 
/*  751 */     if (_isZero(paramArrayOfByte)) {
/*  752 */       return new String("0");
/*      */     }
/*  754 */     if (_isPosInf(paramArrayOfByte)) {
/*  755 */       return new Double((1.0D / 0.0D)).toString();
/*      */     }
/*  757 */     if (_isNegInf(paramArrayOfByte)) {
/*  758 */       return new Double((-1.0D / 0.0D)).toString();
/*      */     }
/*      */ 
/*  761 */     byte[] arrayOfByte = _fromLnxFmt(paramArrayOfByte);
/*      */ 
/*  763 */     int k = arrayOfByte[0];
/*  764 */     int m = arrayOfByte.length - 1;
/*  765 */     int n = k - (m - 1);
/*      */     int i2;
/*  791 */     if (n >= 0)
/*      */     {
/*  793 */       i2 = 2 * (k + 1) + 1;
/*      */     }
/*  797 */     else if (k >= 0)
/*  798 */       i2 = 2 * (m + 1);
/*      */     else {
/*  800 */       i2 = 2 * (m - k) + 3;
/*      */     }
/*      */ 
/*  807 */     char[] arrayOfChar = new char[i2];
/*      */ 
/*  809 */     if (!_isPositive(paramArrayOfByte))
/*      */     {
/*  811 */       arrayOfChar[(i++)] = '-';
/*      */     }
/*      */     int j;
/*  814 */     if (n >= 0)
/*      */     {
/*  816 */       i += _byteToChars(arrayOfByte[1], arrayOfChar, i);
/*      */ 
/*  818 */       for (j = 2; j <= m; k--)
/*      */       {
/*  820 */         _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
/*  821 */         i += 2;
/*      */ 
/*  818 */         j++;
/*      */       }
/*      */ 
/*  825 */       if (k > 0)
/*  826 */         for (; k > 0; k--)
/*      */         {
/*  828 */           arrayOfChar[(i++)] = '0';
/*  829 */           arrayOfChar[(i++)] = '0';
/*      */         }
/*      */     }
/*      */     else
/*      */     {
/*  834 */       int i1 = m + n;
/*      */ 
/*  836 */       if (i1 > 0)
/*      */       {
/*  838 */         i += _byteToChars(arrayOfByte[1], arrayOfChar, i);
/*      */ 
/*  840 */         if (i1 == 1)
/*      */         {
/*  842 */           arrayOfChar[(i++)] = '.';
/*      */         }
/*      */ 
/*  845 */         for (j = 2; j < m; j++)
/*      */         {
/*  847 */           _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
/*  848 */           i += 2;
/*      */ 
/*  850 */           if (i1 != j)
/*      */             continue;
/*  852 */           arrayOfChar[(i++)] = '.';
/*      */         }
/*      */ 
/*  855 */         if (arrayOfByte[j] % 10 == 0)
/*      */         {
/*  857 */           i += _byteToChars((byte)(arrayOfByte[j] / 10), arrayOfChar, i);
/*      */         }
/*      */         else
/*      */         {
/*  861 */           _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
/*  862 */           i += 2;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  867 */         arrayOfChar[(i++)] = '0';
/*  868 */         arrayOfChar[(i++)] = '.';
/*      */ 
/*  870 */         for (; i1 < 0; i1++)
/*      */         {
/*  872 */           arrayOfChar[(i++)] = '0';
/*  873 */           arrayOfChar[(i++)] = '0';
/*      */         }
/*      */ 
/*  876 */         for (j = 1; j < m; j++)
/*      */         {
/*  878 */           _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
/*  879 */           i += 2;
/*      */         }
/*      */ 
/*  882 */         if (arrayOfByte[j] % 10 == 0)
/*      */         {
/*  884 */           i += _byteToChars((byte)(arrayOfByte[j] / 10), arrayOfChar, i);
/*      */         }
/*      */         else
/*      */         {
/*  888 */           _byteTo2Chars(arrayOfByte[j], arrayOfChar, i);
/*  889 */           i += 2;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  894 */     return new String(arrayOfChar, 0, i);
/*      */   }
/*      */ 
/*      */   public static boolean toBoolean(byte[] paramArrayOfByte)
/*      */   {
/*  907 */     return !_isZero(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(double paramDouble)
/*      */     throws SQLException
/*      */   {
/*  925 */     if (Double.isNaN(paramDouble)) {
/*  926 */       throw new IllegalArgumentException(CoreException.getMessage(11));
/*      */     }
/*      */ 
/*  931 */     if ((paramDouble == 0.0D) || (paramDouble == -0.0D)) {
/*  932 */       return _makeZero();
/*      */     }
/*  934 */     if (paramDouble == (1.0D / 0.0D)) {
/*  935 */       return _makePosInf();
/*      */     }
/*      */ 
/*  938 */     if (paramDouble == (-1.0D / 0.0D)) {
/*  939 */       return _makeNegInf();
/*      */     }
/*      */ 
/*  944 */     return _getThinLib().lnxren(paramDouble);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(float paramFloat)
/*      */   {
/*  958 */     if (Float.isNaN(paramFloat)) {
/*  959 */       throw new IllegalArgumentException(CoreException.getMessage(11));
/*      */     }
/*      */ 
/*  964 */     if ((paramFloat == 0.0F) || (paramFloat == -0.0F)) {
/*  965 */       return _makeZero();
/*      */     }
/*  967 */     if (paramFloat == (1.0F / 1.0F)) {
/*  968 */       return _makePosInf();
/*      */     }
/*  970 */     if (paramFloat == (1.0F / -1.0F)) {
/*  971 */       return _makeNegInf();
/*      */     }
/*      */ 
/*  976 */     String str = Float.toString(paramFloat);
/*      */     try
/*      */     {
/*  986 */       return _getLnxLib().lnxcpn(str, false, 0, false, 0, "AMERICAN_AMERICA");
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*  990 */     return null;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(long paramLong)
/*      */   {
/* 1005 */     return _getLnxLib().lnxmin(paramLong);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(int paramInt)
/*      */   {
/* 1017 */     return toBytes(paramInt);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(short paramShort)
/*      */   {
/* 1029 */     return toBytes(paramShort);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(byte paramByte)
/*      */   {
/* 1041 */     return toBytes(paramByte);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(BigInteger paramBigInteger)
/*      */     throws SQLException
/*      */   {
/* 1059 */     byte[] arrayOfByte1 = new byte[66];
/* 1060 */     long[] arrayOfLong1 = new long[54];
/* 1061 */     long[] arrayOfLong2 = new long[22];
/* 1062 */     int i = 21;
/* 1063 */     int j = 0;
/*      */ 
/* 1065 */     int m = 21;
/*      */ 
/* 1067 */     int n = 0;
/*      */ 
/* 1069 */     int i2 = 0;
/*      */ 
/* 1071 */     boolean bool = true;
/*      */ 
/* 1078 */     if (paramBigInteger.signum() == 0)
/*      */     {
/* 1080 */       return _makeZero();
/*      */     }
/*      */     byte[] arrayOfByte2;
/*      */     int i3;
/* 1086 */     if (paramBigInteger.signum() == -1)
/*      */     {
/* 1088 */       localObject = paramBigInteger.abs();
/* 1089 */       bool = false;
/* 1090 */       arrayOfByte2 = ((BigInteger)localObject).toByteArray();
/* 1091 */       i3 = (int)Math.floor(((BigInteger)localObject).bitLength() * 0.150514997831991D);
/*      */     }
/*      */     else
/*      */     {
/* 1095 */       arrayOfByte2 = paramBigInteger.toByteArray();
/* 1096 */       i3 = (int)Math.floor(paramBigInteger.bitLength() * 0.150514997831991D);
/*      */     }
/*      */ 
/* 1108 */     if (paramBigInteger.compareTo(BIGINT_HUND.pow(i3)) < 0) {
/* 1109 */       i3--;
/*      */     }
/* 1111 */     if (arrayOfByte2.length > 54) {
/* 1112 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/* 1117 */     for (int i4 = 0; i4 < arrayOfByte2.length; i4++)
/*      */     {
/* 1119 */       if (arrayOfByte2[i4] < 0)
/* 1120 */         arrayOfByte2[i4] += 256;
/*      */       else {
/* 1122 */         arrayOfLong1[i4] = arrayOfByte2[i4];
/*      */       }
/*      */     }
/* 1125 */     int k = arrayOfByte2.length;
/*      */     long l;
/* 1128 */     switch (k % 3)
/*      */     {
/*      */     case 2:
/* 1131 */       arrayOfLong2[i] = ((arrayOfLong1[j] << 8) + arrayOfLong1[(j + 1)]);
/* 1132 */       j = (byte)(j + 2);
/* 1133 */       k -= 2;
/* 1134 */       break;
/*      */     case 1:
/* 1136 */       arrayOfLong2[i] = arrayOfLong1[j];
/* 1137 */       j = (byte)(j + 1);
/* 1138 */       k--;
/* 1139 */       break;
/*      */     default:
/* 1141 */       l = (arrayOfLong1[j] << 16) + (arrayOfLong1[(j + 1)] << 8) + arrayOfLong1[(j + 2)];
/* 1142 */       arrayOfLong2[i] = (l % 1000000L);
/* 1143 */       arrayOfLong2[(i - 1)] = (l / 1000000L);
/* 1144 */       m = (byte)(m - (arrayOfLong2[(i - 1)] != 0L ? 1 : 0));
/* 1145 */       j = (byte)(j + 3);
/* 1146 */       k -= 3;
/*      */     }
/*      */ 
/* 1151 */     while (k != 0)
/*      */     {
/* 1153 */       l = (arrayOfLong1[j] << 4) + (arrayOfLong1[(j + 1)] >> 4);
/*      */ 
/* 1155 */       for (i = 21; i >= m; i = (byte)(i - 1))
/*      */       {
/* 1157 */         l += (arrayOfLong2[i] << 12);
/* 1158 */         arrayOfLong2[i] = (l % 1000000L);
/* 1159 */         l /= 1000000L;
/*      */       }
/*      */ 
/* 1162 */       if (l != 0L)
/*      */       {
/* 1164 */         m = (byte)(m - 1);
/* 1165 */         arrayOfLong2[m] = l;
/*      */       }
/*      */ 
/* 1168 */       l = ((arrayOfLong1[(j + 1)] & 0xF) << 8) + arrayOfLong1[(j + 2)];
/*      */ 
/* 1170 */       for (i = 21; i >= m; i = (byte)(i - 1))
/*      */       {
/* 1172 */         l += (arrayOfLong2[i] << 12);
/* 1173 */         arrayOfLong2[i] = (l % 1000000L);
/* 1174 */         l /= 1000000L;
/*      */       }
/*      */ 
/* 1177 */       if (l != 0L)
/*      */       {
/* 1179 */         m = (byte)(m - 1);
/* 1180 */         arrayOfLong2[m] = l;
/*      */       }
/*      */ 
/* 1183 */       j = (byte)(j + 3);
/* 1184 */       k -= 3;
/*      */     }
/*      */     int i1;
/* 1189 */     if ((arrayOfByte1[i2] = (byte)(int)(arrayOfLong2[m] / 10000L)) != 0)
/*      */     {
/* 1191 */       i1 = 3 * (21 - m) + 3;
/* 1192 */       arrayOfByte1[(i2 + 1)] = (byte)(int)(arrayOfLong2[m] % 10000L / 100L);
/* 1193 */       arrayOfByte1[(i2 + 2)] = (byte)(int)(arrayOfLong2[m] % 100L);
/* 1194 */       i2 += 3;
/*      */     }
/* 1198 */     else if ((arrayOfByte1[i2] = (byte)(int)(arrayOfLong2[m] % 10000L / 100L)) != 0)
/*      */     {
/* 1200 */       i1 = 3 * (21 - m) + 2;
/* 1201 */       arrayOfByte1[(i2 + 1)] = (byte)(int)(arrayOfLong2[m] % 100L);
/* 1202 */       i2 += 2;
/*      */     }
/*      */     else
/*      */     {
/* 1206 */       arrayOfByte1[i2] = (byte)(int)arrayOfLong2[m];
/* 1207 */       i1 = 3 * (21 - m) + 1;
/* 1208 */       i2++;
/*      */     }
/*      */ 
/* 1214 */     for (i = (byte)(m + 1); i <= 21; i = (byte)(i + 1))
/*      */     {
/* 1216 */       arrayOfByte1[i2] = (byte)(int)(arrayOfLong2[i] / 10000L);
/* 1217 */       arrayOfByte1[(i2 + 1)] = (byte)(int)(arrayOfLong2[i] % 10000L / 100L);
/* 1218 */       arrayOfByte1[(i2 + 2)] = (byte)(int)(arrayOfLong2[i] % 100L);
/* 1219 */       i2 += 3;
/*      */     }
/*      */ 
/* 1224 */     for (i4 = i2 - 1; i4 >= 0; i4--)
/*      */     {
/* 1226 */       if (arrayOfByte1[i4] != 0)
/*      */         break;
/* 1228 */       i1--;
/*      */     }
/*      */ 
/* 1237 */     if (i1 > 19)
/*      */     {
/* 1239 */       i4 = 20;
/* 1240 */       i1 = 19;
/* 1241 */       if (arrayOfByte1[i4] >= 50)
/*      */       {
/* 1243 */         i4--;
/*      */         int tmp851_849 = i4;
/*      */         byte[] tmp851_848 = arrayOfByte1; tmp851_848[tmp851_849] = (byte)(tmp851_848[tmp851_849] + 1);
/* 1245 */         while (arrayOfByte1[i4] == 100)
/*      */         {
/* 1247 */           if (i4 == 0)
/*      */           {
/* 1249 */             i3++;
/* 1250 */             arrayOfByte1[i4] = 1;
/* 1251 */             break;
/*      */           }
/* 1253 */           arrayOfByte1[i4] = 0;
/* 1254 */           i4--;
/*      */           int tmp893_891 = i4;
/*      */           byte[] tmp893_890 = arrayOfByte1; tmp893_890[tmp893_891] = (byte)(tmp893_890[tmp893_891] + 1);
/*      */         }
/*      */ 
/* 1258 */         for (i4 = i1 - 1; i4 >= 0; i4--)
/*      */         {
/* 1260 */           if (arrayOfByte1[i4] != 0) break;
/* 1261 */           i1--;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1268 */     if (i3 > 62) {
/* 1269 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/* 1274 */     int tmp893_891 = new byte[i1 + 1];
/* 1275 */     tmp893_891[0] = (byte)i3;
/* 1276 */     System.arraycopy(arrayOfByte1, 0, tmp893_891, 1, i1);
/*      */ 
/* 1280 */     return (B)_toLnxFmt(tmp893_891, bool);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 1296 */     byte[] arrayOfByte1 = new byte[66];
/* 1297 */     long[] arrayOfLong1 = new long[54];
/* 1298 */     long[] arrayOfLong2 = new long[22];
/* 1299 */     int i = 21;
/* 1300 */     int j = 0;
/*      */ 
/* 1302 */     int m = 21;
/*      */ 
/* 1305 */     int i1 = 0;
/*      */ 
/* 1308 */     int i2 = 0;
/*      */ 
/* 1312 */     BigDecimal localBigDecimal1 = paramBigDecimal.abs();
/*      */ 
/* 1314 */     int i6 = 0;
/*      */ 
/* 1320 */     if (paramBigDecimal.signum() == 0)
/*      */     {
/* 1322 */       return _makeZero();
/*      */     }
/*      */ 
/* 1326 */     boolean bool = paramBigDecimal.signum() != -1;
/*      */ 
/* 1329 */     int i4 = paramBigDecimal.scale();
/*      */ 
/* 1333 */     int i5 = localBigDecimal1.compareTo(BIGDEC_ONE);
/* 1334 */     int i7 = 0;
/*      */     BigDecimal localBigDecimal2;
/* 1336 */     if (i5 == -1)
/*      */     {
/*      */       do {
/* 1339 */         i7++;
/* 1340 */         localBigDecimal2 = localBigDecimal1.movePointRight(i7);
/* 1341 */       }while (localBigDecimal2.compareTo(BIGDEC_ONE) < 0);
/*      */ 
/* 1343 */       i6 = -i7;
/*      */     }
/*      */     else
/*      */     {
/*      */       do {
/* 1348 */         i7++;
/* 1349 */         localBigDecimal2 = localBigDecimal1.movePointLeft(i7);
/* 1350 */       }while (localBigDecimal2.compareTo(BIGDEC_ONE) >= 0);
/*      */ 
/* 1352 */       i6 = i7;
/*      */     }
/*      */ 
/* 1357 */     byte[] arrayOfByte2 = localBigDecimal1.movePointRight(i4).toBigInteger().toByteArray();
/*      */ 
/* 1360 */     if (arrayOfByte2.length > 54) {
/* 1361 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/* 1369 */     for (int i3 = 0; i3 < arrayOfByte2.length; i3++)
/*      */     {
/* 1371 */       if (arrayOfByte2[i3] < 0)
/* 1372 */         arrayOfByte2[i3] += 256;
/*      */       else {
/* 1374 */         arrayOfLong1[i3] = arrayOfByte2[i3];
/*      */       }
/*      */     }
/* 1377 */     int k = arrayOfByte2.length;
/*      */     long l;
/* 1380 */     switch (k % 3)
/*      */     {
/*      */     case 2:
/* 1383 */       arrayOfLong2[i] = ((arrayOfLong1[j] << 8) + arrayOfLong1[(j + 1)]);
/* 1384 */       j = (byte)(j + 2);
/* 1385 */       k -= 2;
/* 1386 */       break;
/*      */     case 1:
/* 1388 */       arrayOfLong2[i] = arrayOfLong1[j];
/* 1389 */       j = (byte)(j + 1);
/* 1390 */       k--;
/* 1391 */       break;
/*      */     default:
/* 1393 */       l = (arrayOfLong1[j] << 16) + (arrayOfLong1[(j + 1)] << 8) + arrayOfLong1[(j + 2)];
/* 1394 */       arrayOfLong2[i] = (l % 1000000L);
/* 1395 */       arrayOfLong2[(i - 1)] = (l / 1000000L);
/* 1396 */       m = (byte)(m - (arrayOfLong2[(i - 1)] != 0L ? 1 : 0));
/* 1397 */       j = (byte)(j + 3);
/* 1398 */       k -= 3;
/*      */     }
/*      */ 
/* 1403 */     while (k != 0)
/*      */     {
/* 1405 */       l = (arrayOfLong1[j] << 4) + (arrayOfLong1[(j + 1)] >> 4);
/*      */ 
/* 1407 */       for (i = 21; i >= m; i = (byte)(i - 1))
/*      */       {
/* 1409 */         l += (arrayOfLong2[i] << 12);
/* 1410 */         arrayOfLong2[i] = (l % 1000000L);
/* 1411 */         l /= 1000000L;
/*      */       }
/*      */ 
/* 1414 */       if (l != 0L)
/*      */       {
/* 1416 */         m = (byte)(m - 1);
/* 1417 */         arrayOfLong2[m] = l;
/*      */       }
/*      */ 
/* 1420 */       l = ((arrayOfLong1[(j + 1)] & 0xF) << 8) + arrayOfLong1[(j + 2)];
/*      */ 
/* 1422 */       for (i = 21; i >= m; i = (byte)(i - 1))
/*      */       {
/* 1424 */         l += (arrayOfLong2[i] << 12);
/* 1425 */         arrayOfLong2[i] = (l % 1000000L);
/* 1426 */         l /= 1000000L;
/*      */       }
/*      */ 
/* 1429 */       if (l != 0L)
/*      */       {
/* 1431 */         m = (byte)(m - 1);
/* 1432 */         arrayOfLong2[m] = l;
/*      */       }
/*      */ 
/* 1435 */       j = (byte)(j + 3);
/* 1436 */       k -= 3;
/*      */     }
/*      */     int n;
/* 1441 */     if ((arrayOfByte1[i1] = (byte)(int)(arrayOfLong2[m] / 10000L)) != 0)
/*      */     {
/* 1443 */       n = 3 * (21 - m) + 3;
/* 1444 */       arrayOfByte1[(i1 + 1)] = (byte)(int)(arrayOfLong2[m] % 10000L / 100L);
/* 1445 */       arrayOfByte1[(i1 + 2)] = (byte)(int)(arrayOfLong2[m] % 100L);
/* 1446 */       i1 += 3;
/*      */     }
/* 1450 */     else if ((arrayOfByte1[i1] = (byte)(int)(arrayOfLong2[m] % 10000L / 100L)) != 0)
/*      */     {
/* 1452 */       n = 3 * (21 - m) + 2;
/* 1453 */       arrayOfByte1[(i1 + 1)] = (byte)(int)(arrayOfLong2[m] % 100L);
/* 1454 */       i1 += 2;
/*      */     }
/*      */     else
/*      */     {
/* 1458 */       arrayOfByte1[i1] = (byte)(int)arrayOfLong2[m];
/* 1459 */       n = 3 * (21 - m) + 1;
/* 1460 */       i1++;
/*      */     }
/*      */ 
/* 1466 */     for (i = (byte)(m + 1); i <= 21; i = (byte)(i + 1))
/*      */     {
/* 1468 */       arrayOfByte1[i1] = (byte)(int)(arrayOfLong2[i] / 10000L);
/* 1469 */       arrayOfByte1[(i1 + 1)] = (byte)(int)(arrayOfLong2[i] % 10000L / 100L);
/* 1470 */       arrayOfByte1[(i1 + 2)] = (byte)(int)(arrayOfLong2[i] % 100L);
/* 1471 */       i1 += 3;
/*      */     }
/*      */ 
/* 1476 */     for (i3 = i1 - 1; i3 >= 0; i3--)
/*      */     {
/* 1478 */       if (arrayOfByte1[i3] != 0)
/*      */         break;
/* 1480 */       n--;
/*      */     }
/*      */ 
/* 1491 */     if ((i4 > 0) && ((i4 & 0x1) != 0))
/*      */     {
/* 1494 */       int i8 = n;
/* 1495 */       byte[] arrayOfByte4 = new byte[i8 + 1];
/*      */ 
/* 1497 */       if (arrayOfByte1[0] <= 9)
/*      */       {
/* 1499 */         for (i3 = 0; i3 < i8 - 1; i3++)
/*      */         {
/* 1501 */           arrayOfByte4[i3] = (byte)(arrayOfByte1[i3] % 10 * 10 + arrayOfByte1[(i3 + 1)] / 10);
/*      */         }
/* 1503 */         arrayOfByte4[i3] = (byte)(arrayOfByte1[i3] % 10 * 10);
/* 1504 */         if (arrayOfByte4[(i8 - 1)] == 0)
/*      */         {
/* 1506 */           n--;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1511 */         arrayOfByte4[i8] = (byte)(arrayOfByte1[(i8 - 1)] % 10 * 10);
/* 1512 */         for (i3 = i8 - 1; i3 > 0; i3--)
/*      */         {
/* 1514 */           arrayOfByte4[i3] = (byte)(arrayOfByte1[i3] / 10 + arrayOfByte1[(i3 - 1)] % 10 * 10);
/*      */         }
/* 1516 */         arrayOfByte4[i3] = (byte)(arrayOfByte1[i3] / 10);
/* 1517 */         if (arrayOfByte4[i8] > 0)
/*      */         {
/* 1519 */           n++;
/*      */         }
/*      */       }
/* 1522 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte1, 0, n);
/*      */     }
/*      */ 
/* 1526 */     if (n > 20)
/*      */     {
/* 1528 */       i3 = 20;
/* 1529 */       n = 20;
/* 1530 */       if (arrayOfByte1[i3] >= 50)
/*      */       {
/* 1532 */         i3--;
/*      */         int tmp1090_1088 = i3;
/*      */         byte[] tmp1090_1087 = arrayOfByte1; tmp1090_1087[tmp1090_1088] = (byte)(tmp1090_1087[tmp1090_1088] + 1);
/* 1534 */         while (arrayOfByte1[i3] == 100)
/*      */         {
/* 1536 */           if (i3 == 0)
/*      */           {
/* 1538 */             i6++;
/* 1539 */             arrayOfByte1[i3] = 1;
/* 1540 */             break;
/*      */           }
/* 1542 */           arrayOfByte1[i3] = 0;
/* 1543 */           i3--;
/*      */           int tmp1132_1130 = i3;
/*      */           byte[] tmp1132_1129 = arrayOfByte1; tmp1132_1129[tmp1132_1130] = (byte)(tmp1132_1129[tmp1132_1130] + 1);
/*      */         }
/*      */ 
/* 1547 */         for (i3 = n - 1; i3 >= 0; i3--)
/*      */         {
/* 1549 */           if (arrayOfByte1[i3] != 0) break;
/* 1550 */           n--;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1557 */     if (i6 <= 0)
/*      */     {
/* 1559 */       if (arrayOfByte1[0] < 10)
/* 1560 */         i2 = -(2 - i6) / 2 + 1;
/*      */       else {
/* 1562 */         i2 = -(2 - i6) / 2;
/*      */       }
/*      */     }
/*      */     else {
/* 1566 */       i2 = (i6 - 1) / 2;
/*      */     }
/*      */ 
/* 1570 */     if (i2 > 62) {
/* 1571 */       throw new SQLException(CoreException.getMessage(3));
/*      */     }
/*      */ 
/* 1574 */     if (i2 <= -65) {
/* 1575 */       throw new SQLException(CoreException.getMessage(2));
/*      */     }
/*      */ 
/* 1579 */     byte[] arrayOfByte3 = new byte[n + 1];
/* 1580 */     arrayOfByte3[0] = (byte)i2;
/* 1581 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 1, n);
/*      */ 
/* 1584 */     return _toLnxFmt(arrayOfByte3, bool);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1613 */     int i = 0;
/* 1614 */     int j = 0;
/*      */ 
/* 1617 */     byte[] arrayOfByte1 = new byte[22];
/*      */ 
/* 1619 */     int n = 0;
/*      */ 
/* 1621 */     boolean bool = true;
/* 1622 */     int i1 = 0;
/* 1623 */     int i2 = 0;
/* 1624 */     int i3 = 0;
/*      */ 
/* 1626 */     int i5 = 0;
/* 1627 */     int i6 = 0;
/* 1628 */     int i7 = 40;
/*      */ 
/* 1631 */     int i8 = 0;
/* 1632 */     int i9 = 0;
/* 1633 */     int i10 = 0;
/*      */ 
/* 1635 */     int i12 = 0;
/*      */ 
/* 1638 */     paramString = paramString.trim();
/*      */ 
/* 1640 */     int i11 = paramString.length();
/*      */ 
/* 1644 */     if (paramString.charAt(0) == '-')
/*      */     {
/* 1646 */       i11--;
/* 1647 */       bool = false;
/* 1648 */       i10 = 1;
/*      */     }
/*      */ 
/* 1651 */     i = i11;
/*      */ 
/* 1655 */     char[] arrayOfChar = new char[i11];
/* 1656 */     paramString.getChars(i10, i11 + i10, arrayOfChar, 0);
/*      */ 
/* 1659 */     for (int k = 0; k < i11; k++)
/*      */     {
/* 1661 */       if (arrayOfChar[k] != '.')
/*      */         continue;
/* 1663 */       i2 = 1;
/* 1664 */       break;
/*      */     }
/*      */ 
/* 1669 */     while ((j < i) && (arrayOfChar[j] == '0'))
/*      */     {
/* 1671 */       j++;
/* 1672 */       if (i2 == 1) {
/* 1673 */         i12++;
/*      */       }
/*      */     }
/* 1676 */     if (j == i)
/* 1677 */       return _makeZero();
/*      */     int m;
/* 1681 */     if ((i11 >= 2) && (arrayOfChar[j] == '.'))
/*      */     {
/* 1686 */       while ((i > 0) && (arrayOfChar[(i - 1)] == '0')) i--;
/*      */ 
/* 1690 */       j++;
/*      */ 
/* 1693 */       if (j == i)
/*      */       {
/* 1695 */         return _makeZero();
/*      */       }
/*      */ 
/* 1706 */       i3--;
/*      */ 
/* 1708 */       while ((j < i - 1) && (arrayOfChar[j] == '0') && (arrayOfChar[(j + 1)] == '0'))
/*      */       {
/* 1711 */         i3--;
/* 1712 */         i6 += 2;
/*      */ 
/* 1709 */         j += 2;
/*      */       }
/*      */ 
/* 1717 */       if (i3 <= -65) {
/* 1718 */         throw new SQLException(CoreException.getMessage(2));
/*      */       }
/*      */ 
/* 1724 */       if (i - j > i7)
/*      */       {
/* 1727 */         m = 2 + i7;
/*      */ 
/* 1730 */         if (i6 > 0) {
/* 1731 */           m += i6;
/*      */         }
/* 1733 */         if (m <= i)
/* 1734 */           i = m;
/* 1735 */         i8 = i;
/* 1736 */         i1 = 1;
/*      */       }
/*      */ 
/* 1742 */       n = i - j >> 1;
/*      */ 
/* 1744 */       if ((i - j) % 2 != 0)
/*      */       {
/* 1746 */         arrayOfByte1[n] = (byte)(Integer.parseInt(new String(arrayOfChar, i - 1, 1)) * 10);
/*      */ 
/* 1748 */         i5++;
/* 1749 */         i--;
/*      */       }
/*      */     }
/*      */     int i13;
/* 1754 */     while (i > j)
/*      */     {
/* 1756 */       n--;
/* 1757 */       arrayOfByte1[n] = (byte)Integer.parseInt(new String(arrayOfChar, i - 2, 2));
/*      */ 
/* 1759 */       i -= 2;
/* 1760 */       i5++; continue;
/*      */ 
/* 1776 */       while ((paramInt > 0) && (i > 0) && (arrayOfChar[(i - 1)] == '0'))
/*      */       {
/* 1778 */         i--;
/* 1779 */         paramInt--;
/*      */       }
/*      */ 
/* 1783 */       if ((paramInt == 0) && (i > 1))
/*      */       {
/* 1785 */         if (arrayOfChar[(i - 1)] == '.') {
/* 1786 */           i--;
/*      */         }
/* 1788 */         if (j == i) {
/* 1789 */           return _makeZero();
/*      */         }
/*      */ 
/* 1793 */         while ((i > 1) && (arrayOfChar[(i - 2)] == '0') && (arrayOfChar[(i - 1)] == '0'))
/*      */         {
/* 1795 */           i -= 2;
/* 1796 */           i3++;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1803 */       if (i3 > 62) {
/* 1804 */         throw new SQLException(CoreException.getMessage(3));
/*      */       }
/*      */ 
/* 1808 */       if (i - j - (i2 != 0 ? 1 : 0) > i7)
/*      */       {
/* 1811 */         m = i7 + (i2 != 0 ? 1 : 0);
/* 1812 */         i13 = i - m;
/* 1813 */         i = m;
/* 1814 */         paramInt -= i13;
/* 1815 */         if (paramInt < 0)
/* 1816 */           paramInt = 0;
/* 1817 */         i1 = 1;
/* 1818 */         i8 = i;
/*      */       }
/*      */ 
/* 1825 */       int i4 = paramInt == 0 ? i - j : i - paramInt - 1;
/*      */ 
/* 1828 */       if (i12 > 0) {
/* 1829 */         i4 -= i12;
/*      */       }
/*      */ 
/* 1833 */       if (i4 % 2 != 0)
/*      */       {
/* 1835 */         i9 = Integer.parseInt(new String(arrayOfChar, j, 1));
/* 1836 */         j++;
/* 1837 */         i4--;
/* 1838 */         if (i - 1 == i7) {
/* 1839 */           paramInt--;
/* 1840 */           i--;
/* 1841 */           i1 = 1;
/* 1842 */           i8 = i;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1847 */         i9 = Integer.parseInt(new String(arrayOfChar, j, 2));
/* 1848 */         j += 2;
/* 1849 */         i4 -= 2;
/*      */       }
/*      */ 
/* 1852 */       arrayOfByte1[n] = (byte)i9;
/* 1853 */       n++;
/* 1854 */       i5++;
/*      */ 
/* 1858 */       while (i4 > 0)
/*      */       {
/* 1860 */         arrayOfByte1[n] = (byte)Integer.parseInt(new String(arrayOfChar, j, 2));
/*      */ 
/* 1862 */         n++;
/* 1863 */         j += 2;
/* 1864 */         i3++;
/* 1865 */         i4 -= 2;
/* 1866 */         i5++;
/*      */       }
/*      */ 
/* 1871 */       if (j < i)
/*      */       {
/* 1873 */         if (paramInt % 2 != 0)
/*      */         {
/* 1875 */           n += paramInt / 2;
/* 1876 */           arrayOfByte1[n] = (byte)(Integer.parseInt(new String(arrayOfChar, i - 1, 1)) * 10);
/*      */ 
/* 1878 */           i--;
/* 1879 */           paramInt--;
/*      */         }
/*      */         else
/*      */         {
/* 1883 */           n += paramInt / 2 - 1;
/* 1884 */           arrayOfByte1[n] = (byte)Integer.parseInt(new String(arrayOfChar, i - 2, 2));
/*      */ 
/* 1886 */           i -= 2;
/* 1887 */           paramInt -= 2;
/*      */         }
/*      */ 
/* 1890 */         i5++;
/* 1891 */         n--;
/*      */       }
/*      */ 
/* 1896 */       while (paramInt > 0)
/*      */       {
/* 1898 */         arrayOfByte1[n] = (byte)Integer.parseInt(new String(arrayOfChar, i - 2, 2));
/*      */ 
/* 1900 */         n--;
/* 1901 */         i -= 2;
/* 1902 */         paramInt -= 2;
/* 1903 */         i5++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1909 */     if (i1 != 0)
/*      */     {
/* 1911 */       i13 = i5;
/* 1912 */       i9 = Integer.parseInt(new String(arrayOfChar, i8, 1));
/* 1913 */       if (i9 >= 5)
/*      */       {
/* 1915 */         i13--;
/*      */         int tmp917_915 = i13;
/*      */         byte[] tmp917_913 = arrayOfByte1; tmp917_913[tmp917_915] = (byte)(tmp917_913[tmp917_915] + 1);
/* 1917 */         while (arrayOfByte1[i13] == 100)
/*      */         {
/* 1919 */           if (i13 == 0)
/*      */           {
/* 1921 */             i3++;
/* 1922 */             arrayOfByte1[i13] = 1;
/* 1923 */             break;
/*      */           }
/* 1925 */           arrayOfByte1[i13] = 0;
/* 1926 */           i13--;
/*      */           int tmp963_961 = i13;
/*      */           byte[] tmp963_959 = arrayOfByte1; tmp963_959[tmp963_961] = (byte)(tmp963_959[tmp963_961] + 1);
/*      */         }
/*      */ 
/* 1935 */         for (k = i5 - 1; k >= 0; k--)
/*      */         {
/* 1937 */           if (arrayOfByte1[k] != 0) break;
/* 1938 */           i5--;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1947 */     byte[] arrayOfByte2 = new byte[i5 + 1];
/* 1948 */     arrayOfByte2[0] = (byte)i3;
/* 1949 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 1, i5);
/*      */ 
/* 1953 */     return _toLnxFmt(arrayOfByte2, bool);
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(boolean paramBoolean)
/*      */   {
/* 1967 */     if (paramBoolean) {
/* 1968 */       return toBytes(1L);
/*      */     }
/* 1970 */     return toBytes(0L);
/*      */   }
/*      */ 
/*      */   public byte[] toBytes()
/*      */   {
/* 1988 */     return getBytes();
/*      */   }
/*      */ 
/*      */   public double doubleValue()
/*      */   {
/* 1998 */     return toDouble(shareBytes());
/*      */   }
/*      */ 
/*      */   public float floatValue()
/*      */   {
/* 2008 */     return toFloat(shareBytes());
/*      */   }
/*      */ 
/*      */   public long longValue()
/*      */     throws SQLException
/*      */   {
/* 2020 */     return toLong(shareBytes());
/*      */   }
/*      */ 
/*      */   public int intValue()
/*      */     throws SQLException
/*      */   {
/* 2032 */     return toInt(shareBytes());
/*      */   }
/*      */ 
/*      */   public short shortValue()
/*      */     throws SQLException
/*      */   {
/* 2044 */     return toShort(shareBytes());
/*      */   }
/*      */ 
/*      */   public byte byteValue()
/*      */     throws SQLException
/*      */   {
/* 2056 */     return toByte(shareBytes());
/*      */   }
/*      */ 
/*      */   public BigInteger bigIntegerValue()
/*      */     throws SQLException
/*      */   {
/* 2067 */     return toBigInteger(shareBytes());
/*      */   }
/*      */ 
/*      */   public BigDecimal bigDecimalValue()
/*      */     throws SQLException
/*      */   {
/* 2080 */     return toBigDecimal(shareBytes());
/*      */   }
/*      */ 
/*      */   public String stringValue()
/*      */   {
/* 2090 */     return toString(shareBytes());
/*      */   }
/*      */ 
/*      */   public boolean booleanValue()
/*      */   {
/* 2100 */     return toBoolean(shareBytes());
/*      */   }
/*      */ 
/*      */   public Object toJdbc()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 2114 */       return bigDecimalValue();
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*      */     }
/* 2118 */     return new SQLException(localSQLException.getMessage());
/*      */   }
/*      */ 
/*      */   public Object makeJdbcArray(int paramInt)
/*      */   {
/* 2132 */     BigDecimal[] arrayOfBigDecimal = new BigDecimal[paramInt];
/*      */ 
/* 2134 */     return arrayOfBigDecimal;
/*      */   }
/*      */ 
/*      */   public boolean isConvertibleTo(Class paramClass)
/*      */   {
/* 2146 */     String str = paramClass.getName();
/*      */ 
/* 2156 */     return (str.compareTo("java.lang.Integer") == 0) || (str.compareTo("java.lang.Long") == 0) || (str.compareTo("java.lang.Float") == 0) || (str.compareTo("java.lang.Double") == 0) || (str.compareTo("java.math.BigInteger") == 0) || (str.compareTo("java.math.BigDecimal") == 0) || (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Boolean") == 0);
/*      */   }
/*      */ 
/*      */   public NUMBER abs()
/*      */     throws SQLException
/*      */   {
/* 2175 */     return new NUMBER(_getLnxLib().lnxabs(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER acos()
/*      */     throws SQLException
/*      */   {
/* 2187 */     return new NUMBER(_getLnxLib().lnxacos(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER add(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2200 */     return new NUMBER(_getLnxLib().lnxadd(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER asin()
/*      */     throws SQLException
/*      */   {
/* 2212 */     return new NUMBER(_getLnxLib().lnxasin(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER atan()
/*      */     throws SQLException
/*      */   {
/* 2225 */     return new NUMBER(_getLnxLib().lnxatan(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER atan2(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2237 */     return new NUMBER(_getLnxLib().lnxatan2(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER ceil()
/*      */     throws SQLException
/*      */   {
/* 2250 */     return new NUMBER(_getLnxLib().lnxceil(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER cos()
/*      */     throws SQLException
/*      */   {
/* 2262 */     return new NUMBER(_getLnxLib().lnxcos(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER cosh()
/*      */     throws SQLException
/*      */   {
/* 2275 */     return new NUMBER(_getLnxLib().lnxcsh(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER decrement()
/*      */     throws SQLException
/*      */   {
/* 2290 */     return new NUMBER(_getLnxLib().lnxdec(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER div(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2303 */     return new NUMBER(_getLnxLib().lnxdiv(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER exp()
/*      */     throws SQLException
/*      */   {
/* 2316 */     return new NUMBER(_getLnxLib().lnxexp(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER floatingPointRound(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2329 */     return new NUMBER(_getLnxLib().lnxfpr(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER floor()
/*      */     throws SQLException
/*      */   {
/* 2341 */     return new NUMBER(_getLnxLib().lnxflo(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER increment()
/*      */     throws SQLException
/*      */   {
/* 2356 */     return new NUMBER(_getLnxLib().lnxinc(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER ln()
/*      */     throws SQLException
/*      */   {
/* 2369 */     return new NUMBER(_getLnxLib().lnxln(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER log(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2382 */     return new NUMBER(_getLnxLib().lnxlog(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER mod(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2396 */     return new NUMBER(_getLnxLib().lnxmod(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER mul(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2408 */     return new NUMBER(_getLnxLib().lnxmul(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER negate()
/*      */     throws SQLException
/*      */   {
/* 2420 */     return new NUMBER(_getLnxLib().lnxneg(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER pow(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2433 */     return new NUMBER(_getLnxLib().lnxbex(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER pow(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2448 */     return new NUMBER(_getLnxLib().lnxpow(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER round(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2461 */     return new NUMBER(_getLnxLib().lnxrou(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER scale(int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
/*      */     throws SQLException
/*      */   {
/* 2483 */     return new NUMBER(_getLnxLib().lnxsca(shareBytes(), paramInt1, paramInt2, paramArrayOfBoolean));
/*      */   }
/*      */ 
/*      */   public NUMBER shift(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2501 */     return new NUMBER(_getLnxLib().lnxshift(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public NUMBER sin()
/*      */     throws SQLException
/*      */   {
/* 2512 */     return new NUMBER(_getLnxLib().lnxsin(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER sinh()
/*      */     throws SQLException
/*      */   {
/* 2523 */     return new NUMBER(_getLnxLib().lnxsnh(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER sqroot()
/*      */     throws SQLException
/*      */   {
/* 2534 */     return new NUMBER(_getLnxLib().lnxsqr(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER sub(NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 2545 */     return new NUMBER(_getLnxLib().lnxsub(shareBytes(), paramNUMBER.shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER tan()
/*      */     throws SQLException
/*      */   {
/* 2556 */     return new NUMBER(_getLnxLib().lnxtan(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER tanh()
/*      */     throws SQLException
/*      */   {
/* 2568 */     return new NUMBER(_getLnxLib().lnxtnh(shareBytes()));
/*      */   }
/*      */ 
/*      */   public NUMBER truncate(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2581 */     return new NUMBER(_getLnxLib().lnxtru(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public static NUMBER formattedTextToNumber(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 2604 */     return new NUMBER(_getLnxLib().lnxfcn(paramString1, paramString2, paramString3));
/*      */   }
/*      */ 
/*      */   public static NUMBER textToPrecisionNumber(String paramString1, boolean paramBoolean1, int paramInt1, boolean paramBoolean2, int paramInt2, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2642 */     return new NUMBER(_getLnxLib().lnxcpn(paramString1, paramBoolean1, paramInt1, paramBoolean2, paramInt2, paramString2));
/*      */   }
/*      */ 
/*      */   public String toFormattedText(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2665 */     return _getLnxLib().lnxnfn(shareBytes(), paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   public String toText(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 2682 */     return _getLnxLib().lnxnuc(shareBytes(), paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public int compareTo(NUMBER paramNUMBER)
/*      */   {
/* 2696 */     return compareBytes(shareBytes(), paramNUMBER.shareBytes());
/*      */   }
/*      */ 
/*      */   public boolean isInf()
/*      */   {
/* 2707 */     return _isInf(shareBytes());
/*      */   }
/*      */ 
/*      */   public boolean isNegInf()
/*      */   {
/* 2717 */     return _isNegInf(shareBytes());
/*      */   }
/*      */ 
/*      */   public boolean isPosInf()
/*      */   {
/* 2727 */     return _isPosInf(shareBytes());
/*      */   }
/*      */ 
/*      */   public boolean isInt()
/*      */   {
/* 2737 */     return _isInt(shareBytes());
/*      */   }
/*      */ 
/*      */   public static boolean isValid(byte[] paramArrayOfByte)
/*      */   {
/* 2750 */     int i = (byte)paramArrayOfByte.length;
/*      */     int j;
/* 2752 */     if (_isPositive(paramArrayOfByte))
/*      */     {
/* 2754 */       if (i == 1) {
/* 2755 */         return _isZero(paramArrayOfByte);
/*      */       }
/*      */ 
/* 2758 */       if ((paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101))
/*      */       {
/* 2760 */         return i == 2;
/*      */       }
/* 2762 */       if (i > 21) {
/* 2763 */         return false;
/*      */       }
/*      */ 
/* 2766 */       if ((paramArrayOfByte[1] < 2) || (paramArrayOfByte[(i - 1)] < 2)) {
/* 2767 */         return false;
/*      */       }
/*      */ 
/* 2770 */       for (k = 1; k < i; k++)
/*      */       {
/* 2772 */         j = paramArrayOfByte[k];
/* 2773 */         if ((j < 1) || (j > 100)) {
/* 2774 */           return false;
/*      */         }
/*      */       }
/* 2777 */       return true;
/*      */     }
/*      */ 
/* 2781 */     if (i < 3) {
/* 2782 */       return _isNegInf(paramArrayOfByte);
/*      */     }
/* 2784 */     if (i > 21) {
/* 2785 */       return false;
/*      */     }
/*      */ 
/* 2788 */     if (paramArrayOfByte[(i - 1)] != 102)
/*      */     {
/* 2790 */       if (i <= 20) {
/* 2791 */         return false;
/*      */       }
/*      */     }
/*      */     else {
/* 2795 */       i = (byte)(i - 1);
/*      */     }
/*      */ 
/* 2799 */     if ((paramArrayOfByte[1] > 100) || (paramArrayOfByte[(i - 1)] > 100)) {
/* 2800 */       return false;
/*      */     }
/*      */ 
/* 2803 */     for (int k = 1; k < i; k++)
/*      */     {
/* 2805 */       j = paramArrayOfByte[k];
/*      */ 
/* 2807 */       if ((j < 2) || (j > 101)) {
/* 2808 */         return false;
/*      */       }
/*      */     }
/* 2811 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isZero()
/*      */   {
/* 2822 */     return _isZero(shareBytes());
/*      */   }
/*      */ 
/*      */   public static NUMBER e()
/*      */   {
/* 2832 */     return new NUMBER(E);
/*      */   }
/*      */ 
/*      */   public static NUMBER ln10()
/*      */   {
/* 2842 */     return new NUMBER(LN10);
/*      */   }
/*      */ 
/*      */   public static NUMBER negInf()
/*      */   {
/* 2852 */     return new NUMBER(_makeNegInf());
/*      */   }
/*      */ 
/*      */   public static NUMBER pi()
/*      */   {
/* 2862 */     return new NUMBER(PI);
/*      */   }
/*      */ 
/*      */   public static NUMBER posInf()
/*      */   {
/* 2872 */     return new NUMBER(_makePosInf());
/*      */   }
/*      */ 
/*      */   public static NUMBER zero()
/*      */   {
/* 2882 */     return new NUMBER(_makeZero());
/*      */   }
/*      */ 
/*      */   public int sign()
/*      */   {
/* 2893 */     if (_isZero(shareBytes())) {
/* 2894 */       return 0;
/*      */     }
/* 2896 */     return _isPositive(shareBytes()) ? 1 : -1;
/*      */   }
/*      */ 
/*      */   static boolean _isInf(byte[] paramArrayOfByte)
/*      */   {
/* 3052 */     return ((paramArrayOfByte.length == 2) && (paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101)) || ((paramArrayOfByte[0] == 0) && (paramArrayOfByte.length == 1));
/*      */   }
/*      */ 
/*      */   private static boolean _isInt(byte[] paramArrayOfByte)
/*      */   {
/* 3064 */     if (_isZero(paramArrayOfByte)) {
/* 3065 */       return true;
/*      */     }
/* 3067 */     if (_isInf(paramArrayOfByte)) {
/* 3068 */       return false;
/*      */     }
/* 3070 */     byte[] arrayOfByte = _fromLnxFmt(paramArrayOfByte);
/* 3071 */     int i = arrayOfByte[0];
/* 3072 */     int j = (byte)(arrayOfByte.length - 1);
/*      */ 
/* 3075 */     return j <= i + 1;
/*      */   }
/*      */ 
/*      */   static boolean _isNegInf(byte[] paramArrayOfByte)
/*      */   {
/* 3087 */     return (paramArrayOfByte[0] == 0) && (paramArrayOfByte.length == 1);
/*      */   }
/*      */ 
/*      */   static boolean _isPosInf(byte[] paramArrayOfByte)
/*      */   {
/* 3101 */     return (paramArrayOfByte.length == 2) && (paramArrayOfByte[0] == -1) && (paramArrayOfByte[1] == 101);
/*      */   }
/*      */ 
/*      */   static boolean _isPositive(byte[] paramArrayOfByte)
/*      */   {
/* 3113 */     return (paramArrayOfByte[0] & 0xFFFFFF80) != 0;
/*      */   }
/*      */ 
/*      */   static boolean _isZero(byte[] paramArrayOfByte)
/*      */   {
/* 3125 */     return (paramArrayOfByte[0] == -128) && (paramArrayOfByte.length == 1);
/*      */   }
/*      */ 
/*      */   static byte[] _makePosInf()
/*      */   {
/* 3135 */     byte[] arrayOfByte = new byte[2];
/*      */ 
/* 3138 */     arrayOfByte[0] = -1;
/* 3139 */     arrayOfByte[1] = 101;
/*      */ 
/* 3141 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   static byte[] _makeNegInf()
/*      */   {
/* 3149 */     byte[] arrayOfByte = new byte[1];
/*      */ 
/* 3152 */     arrayOfByte[0] = 0;
/*      */ 
/* 3154 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   static byte[] _makeZero()
/*      */   {
/* 3162 */     byte[] arrayOfByte = new byte[1];
/*      */ 
/* 3165 */     arrayOfByte[0] = -128;
/*      */ 
/* 3167 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   static byte[] _fromLnxFmt(byte[] paramArrayOfByte)
/*      */   {
/* 3178 */     int j = paramArrayOfByte.length;
/*      */     byte[] arrayOfByte;
/* 3183 */     if (_isPositive(paramArrayOfByte))
/*      */     {
/* 3185 */       arrayOfByte = new byte[j];
/* 3186 */       arrayOfByte[0] = (byte)((paramArrayOfByte[0] & 0xFFFFFF7F) - 65);
/*      */ 
/* 3188 */       for (i = 1; i < j; i++) {
/* 3189 */         arrayOfByte[i] = (byte)(paramArrayOfByte[i] - 1);
/*      */       }
/*      */     }
/*      */ 
/* 3193 */     if ((j - 1 == 20) && (paramArrayOfByte[(j - 1)] != 102))
/*      */     {
/* 3195 */       arrayOfByte = new byte[j];
/*      */     }
/* 3197 */     else arrayOfByte = new byte[j - 1];
/*      */ 
/* 3199 */     arrayOfByte[0] = (byte)(((paramArrayOfByte[0] ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);
/*      */ 
/* 3201 */     for (int i = 1; i < arrayOfByte.length; i++) {
/* 3202 */       arrayOfByte[i] = (byte)(101 - paramArrayOfByte[i]);
/*      */     }
/*      */ 
/* 3205 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   static byte[] _toLnxFmt(byte[] paramArrayOfByte, boolean paramBoolean)
/*      */   {
/* 3221 */     int j = paramArrayOfByte.length;
/*      */     byte[] arrayOfByte;
/* 3226 */     if (paramBoolean)
/*      */     {
/* 3228 */       arrayOfByte = new byte[j];
/* 3229 */       arrayOfByte[0] = (byte)(paramArrayOfByte[0] + 128 + 64 + 1);
/*      */ 
/* 3231 */       for (i = 1; i < j; i++) {
/* 3232 */         arrayOfByte[i] = (byte)(paramArrayOfByte[i] + 1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3238 */     if (j - 1 < 20)
/* 3239 */       arrayOfByte = new byte[j + 1];
/*      */     else {
/* 3241 */       arrayOfByte = new byte[j];
/*      */     }
/* 3243 */     arrayOfByte[0] = (byte)(paramArrayOfByte[0] + 128 + 64 + 1 ^ 0xFFFFFFFF);
/*      */ 
/* 3245 */     for (int i = 1; i < j; i++) {
/* 3246 */       arrayOfByte[i] = (byte)(101 - paramArrayOfByte[i]);
/*      */     }
/* 3248 */     if (i <= 20) {
/* 3249 */       arrayOfByte[i] = 102;
/*      */     }
/*      */ 
/* 3252 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   private static LnxLib _getLnxLib()
/*      */   {
/* 3262 */     if (_slnxlib == null)
/*      */     {
/*      */       try
/*      */       {
/* 3267 */         if (System.getProperty("oracle.jserver.version") != null)
/*      */         {
/* 3269 */           _slnxlib = new LnxLibServer();
/*      */         }
/*      */         else
/*      */         {
/* 3273 */           _slnxlib = new LnxLibThin();
/*      */         }
/*      */       }
/*      */       catch (SecurityException localSecurityException)
/*      */       {
/* 3278 */         _slnxlib = new LnxLibThin();
/*      */       }
/*      */     }
/*      */ 
/* 3282 */     return _slnxlib;
/*      */   }
/*      */ 
/*      */   private static LnxLib _getThinLib()
/*      */   {
/* 3291 */     if (_thinlib == null)
/*      */     {
/* 3293 */       _thinlib = new LnxLibThin();
/*      */     }
/*      */ 
/* 3296 */     return _thinlib;
/*      */   }
/*      */ 
/*      */   private static int _byteToChars(byte paramByte, char[] paramArrayOfChar, int paramInt)
/*      */   {
/* 3306 */     if (paramByte < 0)
/*      */     {
/* 3308 */       return 0;
/*      */     }
/* 3310 */     if (paramByte < 10)
/*      */     {
/* 3312 */       paramArrayOfChar[paramInt] = (char)(48 + paramByte);
/* 3313 */       return 1;
/*      */     }
/* 3315 */     if (paramByte < 100)
/*      */     {
/* 3317 */       paramArrayOfChar[paramInt] = (char)(48 + paramByte / 10);
/* 3318 */       paramArrayOfChar[(paramInt + 1)] = (char)(48 + paramByte % 10);
/* 3319 */       return 2;
/*      */     }
/*      */ 
/* 3323 */     paramArrayOfChar[paramInt] = '1';
/* 3324 */     paramArrayOfChar[(paramInt + 1)] = (char)(48 + paramByte / 10 - 10);
/* 3325 */     paramArrayOfChar[(paramInt + 2)] = (char)(48 + paramByte % 10);
/* 3326 */     return 3;
/*      */   }
/*      */ 
/*      */   private static void _byteTo2Chars(byte paramByte, char[] paramArrayOfChar, int paramInt)
/*      */   {
/* 3335 */     if (paramByte < 0)
/*      */     {
/* 3338 */       paramArrayOfChar[paramInt] = '0';
/* 3339 */       paramArrayOfChar[(paramInt + 1)] = '0';
/*      */     }
/* 3341 */     else if (paramByte < 10)
/*      */     {
/* 3343 */       paramArrayOfChar[paramInt] = '0';
/* 3344 */       paramArrayOfChar[(paramInt + 1)] = (char)(48 + paramByte);
/*      */     }
/* 3346 */     else if (paramByte < 100)
/*      */     {
/* 3348 */       paramArrayOfChar[paramInt] = (char)(48 + paramByte / 10);
/* 3349 */       paramArrayOfChar[(paramInt + 1)] = (char)(48 + paramByte % 10);
/*      */     }
/*      */     else
/*      */     {
/* 3354 */       paramArrayOfChar[paramInt] = '0';
/* 3355 */       paramArrayOfChar[(paramInt + 1)] = '0';
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void _printBytes(byte[] paramArrayOfByte)
/*      */   {
/* 3367 */     int i = paramArrayOfByte.length;
/*      */ 
/* 3369 */     System.out.print(i + ": ");
/*      */ 
/* 3371 */     for (int j = 0; j < i; j++)
/*      */     {
/* 3373 */       System.out.print(paramArrayOfByte[j] + " ");
/*      */     }
/*      */ 
/* 3376 */     System.out.println();
/*      */   }
/*      */ 
/*      */   private byte[] stringToBytes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3387 */     int i = 0;
/*      */ 
/* 3389 */     paramString = paramString.trim();
/*      */ 
/* 3391 */     if (paramString.indexOf('.') >= 0)
/*      */     {
/* 3394 */       i = paramString.length() - 1 - paramString.indexOf('.');
/*      */     }
/*      */ 
/* 3398 */     return toBytes(paramString, i);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 3409 */       drvType = System.getProperty("oracle.jserver.version");
/*      */     }
/*      */     catch (SecurityException localSecurityException)
/*      */     {
/* 3414 */       drvType = null;
/*      */     }
/*      */ 
/* 3418 */     LANGID = "AMERICAN";
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.NUMBER
 * JD-Core Version:    0.6.0
 */