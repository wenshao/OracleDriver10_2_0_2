/*      */ package oracle.sql;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ 
/*      */ public class DATE extends Datum
/*      */ {
/*      */   public static final int BDA = 1;
/*      */   public static final int BDAL = 2;
/*      */   public static final int BMO = 4;
/*      */   public static final int BMOL = 8;
/*      */   public static final int BYR = 16;
/*      */   public static final int BYRL = 32;
/*      */   public static final int BHR = 64;
/*      */   public static final int BHRL = 128;
/*      */   public static final int BMN = 256;
/*      */   public static final int BMNL = 512;
/*      */   public static final int BSC = 1024;
/*      */   public static final int BSCL = 2048;
/*      */   public static final int MSD = 4096;
/*      */   public static final int YR0 = 8192;
/*      */   public static final int BDT = 32768;
/*      */   public static final int HRZER0 = 65536;
/*      */   public static final int MIZERO = 131072;
/*      */   public static final int SEZERO = 262144;
/*      */   private static final byte LDXTCE = 0;
/*      */   private static final byte LDXTYE = 1;
/*      */   private static final byte LDXTMO = 2;
/*      */   private static final byte LDXTDA = 3;
/*      */   private static final byte LDXTHO = 4;
/*      */   private static final byte LDXTMI = 5;
/*      */   private static final byte LDXTSE = 6;
/*      */   private static LdxLib _sldxlib;
/*      */ 
/*      */   public DATE()
/*      */   {
/*   59 */     super(_initDate());
/*      */   }
/*      */ 
/*      */   public DATE(byte[] paramArrayOfByte)
/*      */   {
/*   70 */     super(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public DATE(java.sql.Date paramDate)
/*      */   {
/*   82 */     super(toBytes(paramDate));
/*      */   }
/*      */ 
/*      */   public DATE(Time paramTime)
/*      */   {
/*   94 */     super(toBytes(paramTime));
/*      */   }
/*      */ 
/*      */   public DATE(Timestamp paramTimestamp)
/*      */   {
/*  106 */     super(toBytes(paramTimestamp));
/*      */   }
/*      */ 
/*      */   public DATE(java.sql.Date paramDate, Calendar paramCalendar)
/*      */   {
/*  120 */     super(toBytes(paramDate, paramCalendar));
/*      */   }
/*      */ 
/*      */   public DATE(Time paramTime, Calendar paramCalendar)
/*      */   {
/*  134 */     super(toBytes(paramTime, paramCalendar));
/*      */   }
/*      */ 
/*      */   public DATE(Timestamp paramTimestamp, Calendar paramCalendar)
/*      */   {
/*  148 */     super(toBytes(paramTimestamp, paramCalendar));
/*      */   }
/*      */ 
/*      */   public DATE(String paramString)
/*      */   {
/*  161 */     super(toBytes(paramString));
/*      */   }
/*      */ 
/*      */   public DATE(String paramString, boolean paramBoolean)
/*      */     throws ParseException
/*      */   {
/*  177 */     super(toBytes(paramString));
/*  178 */     if (!paramBoolean)
/*      */     {
/*  182 */       SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
/*      */ 
/*  185 */       localSimpleDateFormat.setLenient(false);
/*      */ 
/*  187 */       java.util.Date localDate = localSimpleDateFormat.parse(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   public DATE(String paramString, Calendar paramCalendar)
/*      */   {
/*  202 */     super(toBytes(paramString, paramCalendar));
/*      */   }
/*      */ 
/*      */   public DATE(Object paramObject)
/*      */     throws SQLException
/*      */   {
/*  215 */     if ((paramObject instanceof java.sql.Date))
/*  216 */       setShareBytes(toBytes((java.sql.Date)paramObject));
/*  217 */     else if ((paramObject instanceof Time))
/*  218 */       setShareBytes(toBytes((Time)paramObject));
/*  219 */     else if ((paramObject instanceof Timestamp))
/*  220 */       setShareBytes(toBytes((Timestamp)paramObject));
/*  221 */     else if ((paramObject instanceof String))
/*  222 */       setShareBytes(toBytes((String)paramObject));
/*      */     else
/*  224 */       throw new SQLException("Initialization failed");
/*      */   }
/*      */ 
/*      */   public DATE(Object paramObject, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  240 */     if ((paramObject instanceof java.sql.Date))
/*  241 */       setShareBytes(toBytes((java.sql.Date)paramObject, paramCalendar));
/*  242 */     else if ((paramObject instanceof Time))
/*  243 */       setShareBytes(toBytes((Time)paramObject, paramCalendar));
/*  244 */     else if ((paramObject instanceof Timestamp))
/*  245 */       setShareBytes(toBytes((Timestamp)paramObject, paramCalendar));
/*  246 */     else if ((paramObject instanceof String))
/*  247 */       setShareBytes(toBytes((String)paramObject, paramCalendar));
/*      */     else
/*  249 */       throw new SQLException("Initialization failed");
/*      */   }
/*      */ 
/*      */   public static java.sql.Date toDate(byte[] paramArrayOfByte)
/*      */   {
/*  270 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  275 */     for (int i = 0; i < 7; i++) {
/*  276 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*      */ 
/*  280 */     i = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*  281 */     int j = i - 1900;
/*      */ 
/*  283 */     if (i <= 0) {
/*  284 */       j++;
/*      */     }
/*  286 */     return new java.sql.Date(j, arrayOfInt[2] - 1, arrayOfInt[3]);
/*      */   }
/*      */ 
/*      */   public static Time toTime(byte[] paramArrayOfByte)
/*      */   {
/*  298 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  303 */     for (int i = 0; i < 7; i++) {
/*  304 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*  306 */     return new Time(arrayOfInt[4] - 1, arrayOfInt[5] - 1, arrayOfInt[6] - 1);
/*      */   }
/*      */ 
/*      */   public static Timestamp toTimestamp(byte[] paramArrayOfByte)
/*      */   {
/*  319 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  324 */     for (int i = 0; i < 7; i++) {
/*  325 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*      */ 
/*  328 */     i = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*  329 */     int j = i - 1900;
/*      */ 
/*  331 */     if (i <= 0) {
/*  332 */       j++;
/*      */     }
/*  334 */     return new Timestamp(j, arrayOfInt[2] - 1, arrayOfInt[3], arrayOfInt[4] - 1, arrayOfInt[5] - 1, arrayOfInt[6] - 1, 0);
/*      */   }
/*      */ 
/*      */   public static java.sql.Date toDate(byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */   {
/*  351 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  356 */     for (int i = 0; i < 7; i++) {
/*  357 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*      */ 
/*  360 */     i = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*      */ 
/*  362 */     if (paramCalendar == null) {
/*  363 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  365 */     paramCalendar.clear();
/*      */ 
/*  367 */     paramCalendar.set(1, i);
/*  368 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  369 */     paramCalendar.set(5, arrayOfInt[3]);
/*  370 */     paramCalendar.set(11, 0);
/*  371 */     paramCalendar.set(12, 0);
/*  372 */     paramCalendar.set(13, 0);
/*  373 */     paramCalendar.set(14, 0);
/*      */ 
/*  375 */     java.sql.Date localDate = new java.sql.Date(paramCalendar.getTime().getTime());
/*      */ 
/*  377 */     return localDate;
/*      */   }
/*      */ 
/*      */   public static Time toTime(byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */   {
/*  392 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  397 */     for (int i = 0; i < 7; i++) {
/*  398 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*  400 */     i = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*      */ 
/*  402 */     if (paramCalendar == null) {
/*  403 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  405 */     paramCalendar.clear();
/*      */ 
/*  407 */     paramCalendar.set(1, 1970);
/*  408 */     paramCalendar.set(2, 0);
/*  409 */     paramCalendar.set(5, 1);
/*  410 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  411 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  412 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*  413 */     paramCalendar.set(14, 0);
/*      */ 
/*  415 */     Time localTime = new Time(paramCalendar.getTime().getTime());
/*      */ 
/*  417 */     return localTime;
/*      */   }
/*      */ 
/*      */   public static Timestamp toTimestamp(byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */   {
/*  431 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  436 */     for (int i = 0; i < 7; i++) {
/*  437 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*      */ 
/*  440 */     i = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*      */ 
/*  442 */     if (paramCalendar == null) {
/*  443 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  445 */     paramCalendar.clear();
/*      */ 
/*  447 */     paramCalendar.set(1, i);
/*  448 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  449 */     paramCalendar.set(5, arrayOfInt[3]);
/*  450 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  451 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  452 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*  453 */     paramCalendar.set(14, 0);
/*      */ 
/*  455 */     Timestamp localTimestamp = new Timestamp(paramCalendar.getTime().getTime());
/*      */ 
/*  458 */     return localTimestamp;
/*      */   }
/*      */ 
/*      */   public static String toString(byte[] paramArrayOfByte)
/*      */   {
/*  470 */     int[] arrayOfInt = new int[7];
/*      */ 
/*  473 */     for (int i = 0; i < 7; i++)
/*      */     {
/*  475 */       if (paramArrayOfByte[i] < 0)
/*  476 */         paramArrayOfByte[i] += 256;
/*      */       else {
/*  478 */         arrayOfInt[i] = paramArrayOfByte[i];
/*      */       }
/*      */     }
/*  481 */     arrayOfInt[4] -= 1;
/*  482 */     arrayOfInt[5] -= 1;
/*  483 */     arrayOfInt[6] -= 1;
/*      */ 
/*  485 */     int j = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*      */ 
/*  487 */     return arrayOfInt[2] + "/" + arrayOfInt[3] + "/" + j + " " + arrayOfInt[4] + ":" + arrayOfInt[5] + ":" + arrayOfInt[6];
/*      */   }
/*      */ 
/*      */   public byte[] toBytes()
/*      */   {
/*  501 */     return getBytes();
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(java.sql.Date paramDate)
/*      */   {
/*  512 */     if (paramDate == null) {
/*  513 */       return null;
/*      */     }
/*  515 */     byte[] arrayOfByte = new byte[7];
/*  516 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  519 */     localCalendar.setTime(paramDate);
/*      */ 
/*  522 */     int i = localCalendar.get(1);
/*      */ 
/*  524 */     if (localCalendar.get(0) == 0) {
/*  525 */       i = -(i - 1);
/*      */     }
/*  527 */     arrayOfByte[0] = (byte)(i / 100 + 100);
/*  528 */     arrayOfByte[1] = (byte)(i % 100 + 100);
/*  529 */     arrayOfByte[2] = (byte)(localCalendar.get(2) + 1);
/*  530 */     arrayOfByte[3] = (byte)localCalendar.get(5);
/*  531 */     arrayOfByte[4] = 1;
/*  532 */     arrayOfByte[5] = 1;
/*  533 */     arrayOfByte[6] = 1;
/*      */ 
/*  535 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Time paramTime)
/*      */   {
/*  546 */     if (paramTime == null) {
/*  547 */       return null;
/*      */     }
/*  549 */     byte[] arrayOfByte = new byte[7];
/*  550 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  552 */     localCalendar.setTime(paramTime);
/*      */ 
/*  555 */     int i = localCalendar.get(1);
/*      */ 
/*  557 */     arrayOfByte[0] = 119;
/*  558 */     arrayOfByte[1] = 100;
/*  559 */     arrayOfByte[2] = 1;
/*  560 */     arrayOfByte[3] = 1;
/*  561 */     arrayOfByte[4] = (byte)(localCalendar.get(11) + 1);
/*  562 */     arrayOfByte[5] = (byte)(localCalendar.get(12) + 1);
/*  563 */     arrayOfByte[6] = (byte)(localCalendar.get(13) + 1);
/*      */ 
/*  565 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Timestamp paramTimestamp)
/*      */   {
/*  576 */     if (paramTimestamp == null) {
/*  577 */       return null;
/*      */     }
/*  579 */     byte[] arrayOfByte = new byte[7];
/*  580 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  582 */     localCalendar.setTime(paramTimestamp);
/*      */ 
/*  585 */     int i = localCalendar.get(1);
/*      */ 
/*  588 */     if (localCalendar.get(0) == 0)
/*  589 */       i = -(i - 1);
/*  590 */     arrayOfByte[0] = (byte)(i / 100 + 100);
/*  591 */     arrayOfByte[1] = (byte)(i % 100 + 100);
/*  592 */     arrayOfByte[2] = (byte)(localCalendar.get(2) + 1);
/*  593 */     arrayOfByte[3] = (byte)localCalendar.get(5);
/*  594 */     arrayOfByte[4] = (byte)(localCalendar.get(11) + 1);
/*  595 */     arrayOfByte[5] = (byte)(localCalendar.get(12) + 1);
/*  596 */     arrayOfByte[6] = (byte)(localCalendar.get(13) + 1);
/*      */ 
/*  598 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(java.sql.Date paramDate, Calendar paramCalendar)
/*      */   {
/*  611 */     if (paramDate == null) {
/*  612 */       return null;
/*      */     }
/*  614 */     if (paramCalendar == null) {
/*  615 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  617 */     paramCalendar.clear();
/*  618 */     paramCalendar.setTime(paramDate);
/*      */ 
/*  620 */     byte[] arrayOfByte = new byte[7];
/*      */ 
/*  623 */     int i = paramCalendar.get(1);
/*      */ 
/*  627 */     if (paramCalendar.get(0) == 0) {
/*  628 */       i = -(i - 1);
/*      */     }
/*  630 */     arrayOfByte[0] = (byte)(i / 100 + 100);
/*  631 */     arrayOfByte[1] = (byte)(i % 100 + 100);
/*  632 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  633 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  634 */     arrayOfByte[4] = 1;
/*  635 */     arrayOfByte[5] = 1;
/*  636 */     arrayOfByte[6] = 1;
/*      */ 
/*  638 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Time paramTime, Calendar paramCalendar)
/*      */   {
/*  651 */     if (paramTime == null) {
/*  652 */       return null;
/*      */     }
/*  654 */     if (paramCalendar == null) {
/*  655 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  657 */     paramCalendar.clear();
/*  658 */     paramCalendar.setTime(paramTime);
/*      */ 
/*  660 */     byte[] arrayOfByte = new byte[7];
/*      */ 
/*  662 */     arrayOfByte[0] = 119;
/*  663 */     arrayOfByte[1] = 100;
/*  664 */     arrayOfByte[2] = 1;
/*  665 */     arrayOfByte[3] = 1;
/*  666 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  667 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  668 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  670 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Timestamp paramTimestamp, Calendar paramCalendar)
/*      */   {
/*  683 */     if (paramTimestamp == null) {
/*  684 */       return null;
/*      */     }
/*  686 */     if (paramCalendar == null) {
/*  687 */       paramCalendar = Calendar.getInstance();
/*      */     }
/*  689 */     paramCalendar.clear();
/*  690 */     paramCalendar.setTime(paramTimestamp);
/*      */ 
/*  693 */     byte[] arrayOfByte = new byte[7];
/*      */ 
/*  696 */     int i = paramCalendar.get(1);
/*      */ 
/*  699 */     if (paramCalendar.get(0) == 0) {
/*  700 */       i = -(i - 1);
/*      */     }
/*  702 */     arrayOfByte[0] = (byte)(i / 100 + 100);
/*  703 */     arrayOfByte[1] = (byte)(i % 100 + 100);
/*  704 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  705 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  706 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  707 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  708 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  710 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(String paramString)
/*      */   {
/*  720 */     return toBytes(Timestamp.valueOf(paramString));
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(String paramString, Calendar paramCalendar)
/*      */   {
/*  732 */     return toBytes(Timestamp.valueOf(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public java.sql.Date dateValue()
/*      */   {
/*  748 */     return toDate(getBytes());
/*      */   }
/*      */ 
/*      */   public Time timeValue()
/*      */   {
/*  758 */     return toTime(getBytes());
/*      */   }
/*      */ 
/*      */   public Timestamp timestampValue()
/*      */   {
/*  768 */     return toTimestamp(getBytes());
/*      */   }
/*      */ 
/*      */   public java.sql.Date dateValue(Calendar paramCalendar)
/*      */   {
/*  779 */     return toDate(getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   public Time timeValue(Calendar paramCalendar)
/*      */   {
/*  790 */     return toTime(getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   public Timestamp timestampValue(Calendar paramCalendar)
/*      */   {
/*  802 */     return toTimestamp(getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   public String stringValue()
/*      */   {
/*  812 */     return toString(getBytes());
/*      */   }
/*      */ 
/*      */   public Object toJdbc()
/*      */   {
/*  822 */     return timestampValue();
/*      */   }
/*      */ 
/*      */   public Object makeJdbcArray(int paramInt)
/*      */   {
/*  833 */     Timestamp[] arrayOfTimestamp = new Timestamp[paramInt];
/*      */ 
/*  835 */     return arrayOfTimestamp;
/*      */   }
/*      */ 
/*      */   public boolean isConvertibleTo(Class paramClass)
/*      */   {
/*  851 */     return (paramClass.getName().compareTo("java.sql.Date") == 0) || (paramClass.getName().compareTo("java.sql.Time") == 0) || (paramClass.getName().compareTo("java.sql.Timestamp") == 0) || (paramClass.getName().compareTo("java.lang.String") == 0);
/*      */   }
/*      */ 
/*      */   public DATE addJulianDays(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  872 */     return new DATE(_getLdxLib().ldxads(shareBytes(), paramInt1, paramInt2));
/*      */   }
/*      */ 
/*      */   public DATE addMonths(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  888 */     return new DATE(_getLdxLib().ldxadm(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public void diffInJulianDays(DATE paramDATE, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/*  904 */     _getLdxLib().ldxsub(shareBytes(), paramDATE.shareBytes(), paramArrayOfInt1, paramArrayOfInt2);
/*      */   }
/*      */ 
/*      */   public NUMBER diffInMonths(DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/*  918 */     return new NUMBER(_getLdxLib().ldxsbm(shareBytes(), paramDATE.shareBytes()));
/*      */   }
/*      */ 
/*      */   public static DATE getCurrentDate()
/*      */     throws SQLException
/*      */   {
/*  931 */     return new DATE(_getLdxLib().ldxgdt());
/*      */   }
/*      */ 
/*      */   public static int checkValidity(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  965 */     return _getLdxLib().ldxchk(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public static DATE fromJulianDays(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  981 */     return new DATE(_getLdxLib().ldxdfd(paramInt1, paramInt2));
/*      */   }
/*      */ 
/*      */   public static DATE fromText(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 1065 */     return new DATE(_getLdxLib().ldxstd(paramString1, paramString2, paramString3));
/*      */   }
/*      */ 
/*      */   public DATE lastDayOfMonth()
/*      */     throws SQLException
/*      */   {
/* 1077 */     return new DATE(_getLdxLib().ldxldd(shareBytes()));
/*      */   }
/*      */ 
/*      */   public static void numberToJulianDays(NUMBER paramNUMBER, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/* 1092 */     _getLdxLib().ldxftd(paramNUMBER.toBytes(), paramArrayOfInt1, paramArrayOfInt2);
/*      */   }
/*      */ 
/*      */   public DATE round(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1105 */     return new DATE(_getLdxLib().ldxrnd(shareBytes(), paramString));
/*      */   }
/*      */ 
/*      */   public DATE setDayOfWeek(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1119 */     return new DATE(_getLdxLib().ldxnxd(shareBytes(), paramInt));
/*      */   }
/*      */ 
/*      */   public void toJulianDays(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */     throws SQLException
/*      */   {
/* 1134 */     _getLdxLib().ldxdtd(shareBytes(), paramArrayOfInt1, paramArrayOfInt2);
/*      */   }
/*      */ 
/*      */   public NUMBER toNumber()
/*      */     throws SQLException
/*      */   {
/* 1146 */     return new NUMBER(_getLdxLib().ldxdyf(shareBytes()));
/*      */   }
/*      */ 
/*      */   public String toText(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 1161 */     return _getLdxLib().ldxdts(shareBytes(), paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   public String toText(byte[] paramArrayOfByte, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1176 */     return _getLdxLib().ldxdts(shareBytes(), paramArrayOfByte, paramString);
/*      */   }
/*      */ 
/*      */   public static byte[] parseFormat(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 1192 */     return (byte[])_getLdxLib().ldxsto(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   public DATE truncate(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1206 */     return new DATE(_getLdxLib().ldxtrn(shareBytes(), paramString));
/*      */   }
/*      */ 
/*      */   public int compareTo(DATE paramDATE)
/*      */   {
/* 1220 */     return compareBytes(shareBytes(), paramDATE.shareBytes());
/*      */   }
/*      */ 
/*      */   private static byte[] _initDate()
/*      */   {
/* 1374 */     byte[] arrayOfByte = new byte[7];
/*      */ 
/* 1376 */     arrayOfByte[0] = 119;
/* 1377 */     arrayOfByte[1] = -86;
/* 1378 */     arrayOfByte[2] = 1;
/* 1379 */     arrayOfByte[3] = 1;
/* 1380 */     arrayOfByte[4] = 1;
/* 1381 */     arrayOfByte[5] = 1;
/* 1382 */     arrayOfByte[6] = 1;
/*      */ 
/* 1384 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   private static LdxLib _getLdxLib()
/*      */   {
/* 1394 */     if (_sldxlib == null)
/*      */     {
/*      */       try
/*      */       {
/* 1399 */         if (System.getProperty("oracle.jserver.version") != null)
/*      */         {
/* 1401 */           _sldxlib = new LdxLibServer();
/*      */         }
/*      */         else
/*      */         {
/* 1405 */           _sldxlib = new LdxLibThin();
/*      */         }
/*      */       }
/*      */       catch (SecurityException localSecurityException)
/*      */       {
/* 1410 */         _sldxlib = new LdxLibThin();
/*      */       }
/*      */     }
/*      */ 
/* 1414 */     return _sldxlib;
/*      */   }
/*      */ 
/*      */   private static void _printBytes(byte[] paramArrayOfByte)
/*      */   {
/* 1426 */     System.out.println(toString(paramArrayOfByte));
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.DATE
 * JD-Core Version:    0.6.0
 */