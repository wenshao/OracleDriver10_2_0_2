/*      */ package oracle.sql;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.jdbc.OraclePreparedStatement;
/*      */ 
/*      */ public class TIMESTAMPLTZ extends Datum
/*      */ {
/* 1662 */   private static int SIZE_TIMESTAMPLTZ = 11;
/* 1663 */   private static int SIZE_TIMESTAMPLTZ_NOFRAC = 7;
/*      */ 
/* 1666 */   private static int SIZE_DATE = 7;
/*      */ 
/* 1671 */   private static int CENTURY_DEFAULT = 119;
/* 1672 */   private static int DECADE_DEFAULT = 100;
/* 1673 */   private static int MONTH_DEFAULT = 1;
/* 1674 */   private static int DAY_DEFAULT = 1;
/*      */ 
/* 1678 */   private static int DECADE_INIT = 170;
/*      */ 
/* 1681 */   private static int HOUR_MILLISECOND = 3600000;
/*      */ 
/* 1684 */   private static int MINUTE_MILLISECOND = 60000;
/*      */ 
/* 1688 */   private static int JAVA_YEAR = 1970;
/* 1689 */   private static int JAVA_MONTH = 0;
/* 1690 */   private static int JAVA_DATE = 1;
/*      */ 
/* 1921 */   private static boolean cached = false;
/*      */   private static Calendar dbtz;
/*      */ 
/*      */   public TIMESTAMPLTZ()
/*      */   {
/*   73 */     super(initTimestampltz());
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(byte[] paramArrayOfByte)
/*      */   {
/*   88 */     super(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  106 */     super(toBytes(paramConnection, paramTime, paramCalendar));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  124 */     super(toBytes(paramConnection, paramDate, paramCalendar));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  142 */     super(toBytes(paramConnection, paramTimestamp, paramCalendar));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, DATE paramDATE, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  161 */     super(toBytes(paramConnection, paramDATE, paramCalendar));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  182 */     super(toBytes(paramConnection, paramString, paramCalendar));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, Time paramTime)
/*      */     throws SQLException
/*      */   {
/*  204 */     super(toBytes(paramConnection, paramCalendar, paramTime));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/*  221 */     super(toBytes(paramConnection, paramCalendar, paramDate));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/*  238 */     super(toBytes(paramConnection, paramCalendar, paramTimestamp));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/*  256 */     super(toBytes(paramConnection, paramCalendar, paramDATE));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Calendar paramCalendar, String paramString)
/*      */     throws SQLException
/*      */   {
/*  274 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), paramString));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Time paramTime)
/*      */     throws SQLException
/*      */   {
/*  291 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), paramTime));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/*  307 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), paramDate));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/*  324 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), paramTimestamp));
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ(Connection paramConnection, DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/*  341 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), paramDATE));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public TIMESTAMPLTZ(Connection paramConnection, String paramString)
/*      */     throws SQLException
/*      */   {
/*  357 */     super(toBytes(paramConnection, getSessCalendar(paramConnection), Timestamp.valueOf(paramString)));
/*      */   }
/*      */ 
/*      */   public static java.sql.Date toDate(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  386 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/*  388 */     if (i == SIZE_TIMESTAMPLTZ)
/*  389 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/*  391 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/*  393 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/*  394 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/*  398 */     j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/*  402 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  404 */     TimeZone.setDefault(paramCalendar.getTimeZone());
/*      */ 
/*  406 */     paramCalendar.set(1, j);
/*  407 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  408 */     paramCalendar.set(5, arrayOfInt[3]);
/*  409 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  410 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  411 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*  412 */     paramCalendar.set(14, 0);
/*      */ 
/*  415 */     TimeZoneAdjust(paramConnection, paramCalendar, localCalendar);
/*      */ 
/*  419 */     long l = localCalendar.getTime().getTime();
/*      */ 
/*  424 */     return new java.sql.Date(l);
/*      */   }
/*      */ 
/*      */   public static Time toTime(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  449 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/*  451 */     if (i == SIZE_TIMESTAMPLTZ)
/*  452 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/*  454 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/*  456 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/*  457 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/*  461 */     j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/*  465 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  467 */     TimeZone.setDefault(paramCalendar.getTimeZone());
/*      */ 
/*  469 */     paramCalendar.set(1, j);
/*  470 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  471 */     paramCalendar.set(5, arrayOfInt[3]);
/*  472 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  473 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  474 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*      */ 
/*  478 */     TimeZoneAdjust(paramConnection, paramCalendar, localCalendar);
/*      */ 
/*  482 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*      */ 
/*  488 */     return new Time(localCalendar.get(11), localCalendar.get(12), localCalendar.get(13));
/*      */   }
/*      */ 
/*      */   public static Timestamp toTimestamp(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  508 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/*  510 */     if (i == SIZE_TIMESTAMPLTZ)
/*  511 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/*  513 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/*  515 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/*  516 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/*  520 */     j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/*  525 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  527 */     TimeZone.setDefault(paramCalendar.getTimeZone());
/*      */ 
/*  529 */     paramCalendar.set(1, j);
/*  530 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  531 */     paramCalendar.set(5, arrayOfInt[3]);
/*  532 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  533 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  534 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*  535 */     paramCalendar.set(14, 0);
/*      */ 
/*  537 */     TimeZoneAdjust(paramConnection, paramCalendar, localCalendar);
/*      */ 
/*  542 */     long l = localCalendar.getTime().getTime();
/*      */ 
/*  545 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*      */ 
/*  549 */     Timestamp localTimestamp = new Timestamp(l);
/*  550 */     int k = 0;
/*      */ 
/*  553 */     if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ)
/*      */     {
/*  555 */       k = arrayOfInt[7] << 24;
/*  556 */       k |= arrayOfInt[8] << 16;
/*  557 */       k |= arrayOfInt[9] << 8;
/*  558 */       k |= arrayOfInt[10] & 0xFF;
/*  559 */       localTimestamp.setNanos(k);
/*      */     }
/*      */     else
/*      */     {
/*  563 */       localTimestamp.setNanos(0);
/*      */     }
/*  565 */     return localTimestamp;
/*      */   }
/*      */ 
/*      */   public static DATE toDATE(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  586 */     int[] arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */ 
/*  588 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/*  589 */       paramArrayOfByte[i] &= 255;
/*      */     }
/*      */ 
/*  593 */     i = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/*  597 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  599 */     TimeZone.setDefault(paramCalendar.getTimeZone());
/*      */ 
/*  601 */     paramCalendar.set(1, i);
/*  602 */     paramCalendar.set(2, arrayOfInt[2] - 1);
/*  603 */     paramCalendar.set(5, arrayOfInt[3]);
/*  604 */     paramCalendar.set(11, arrayOfInt[4] - 1);
/*  605 */     paramCalendar.set(12, arrayOfInt[5] - 1);
/*  606 */     paramCalendar.set(13, arrayOfInt[6] - 1);
/*      */ 
/*  609 */     TimeZoneAdjust(paramConnection, paramCalendar, localCalendar);
/*      */ 
/*  613 */     long l = localCalendar.getTime().getTime();
/*      */ 
/*  615 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*      */ 
/*  621 */     return new DATE(new java.sql.Date(l));
/*      */   }
/*      */ 
/*      */   public Timestamp timestampValue(Connection paramConnection, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  640 */     return toTimestamp(paramConnection, getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static String toString(Connection paramConnection, byte[] paramArrayOfByte, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  658 */     Timestamp localTimestamp = toTimestamp(paramConnection, paramArrayOfByte, paramCalendar);
/*      */ 
/*  660 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  662 */     localCalendar.setTime(localTimestamp);
/*      */ 
/*  666 */     int i = localCalendar.get(1);
/*  667 */     int j = localCalendar.get(2) + 1;
/*  668 */     int k = localCalendar.get(5);
/*  669 */     int m = localCalendar.get(11);
/*  670 */     int n = localCalendar.get(12);
/*  671 */     int i1 = localCalendar.get(13);
/*  672 */     int i2 = 0;
/*      */ 
/*  674 */     if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ)
/*      */     {
/*  676 */       i2 = (paramArrayOfByte[7] & 0xFF) << 24;
/*  677 */       i2 |= (paramArrayOfByte[8] & 0xFF) << 16;
/*  678 */       i2 |= (paramArrayOfByte[9] & 0xFF) << 8;
/*  679 */       i2 |= paramArrayOfByte[10] & 0xFF & 0xFF;
/*      */     }
/*      */ 
/*  683 */     return i + "-" + j + "-" + k + " " + m + "." + n + "." + i1 + "." + i2;
/*      */   }
/*      */ 
/*      */   public byte[] toBytes()
/*      */   {
/*  699 */     return getBytes();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static byte[] toBytes(Connection paramConnection, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  717 */     if (paramTime == null) {
/*  718 */       return null;
/*      */     }
/*  720 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*  721 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  723 */     localCalendar.setTime(paramTime);
/*      */     int i;
/*  731 */     if (paramCalendar.getTimeZone().inDaylightTime(paramTime))
/*  732 */       i = 1;
/*      */     else {
/*  734 */       i = 0;
/*      */     }
/*  736 */     localCalendar.set(1, (CENTURY_DEFAULT - 100) * 100 + DECADE_DEFAULT % 100);
/*  737 */     localCalendar.set(2, MONTH_DEFAULT - 1);
/*  738 */     localCalendar.set(5, DAY_DEFAULT);
/*      */ 
/*  741 */     TimeZoneAdjust(paramConnection, localCalendar, paramCalendar);
/*      */ 
/*  743 */     arrayOfByte[0] = (byte)(paramCalendar.get(1) / 100 + 100);
/*  744 */     arrayOfByte[1] = (byte)(paramCalendar.get(1) % 100 + 100);
/*  745 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  746 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  747 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  748 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  749 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  751 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*      */ 
/*  753 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static byte[] toBytes(Connection paramConnection, java.sql.Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  770 */     if (paramDate == null) {
/*  771 */       return null;
/*      */     }
/*  773 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*  774 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  776 */     localCalendar.setTime(paramDate);
/*      */     int i;
/*  781 */     if (localCalendar.getTimeZone().inDaylightTime(paramDate))
/*  782 */       i = 1;
/*      */     else {
/*  784 */       i = 0;
/*      */     }
/*  786 */     localCalendar.set(11, 0);
/*  787 */     localCalendar.set(12, 0);
/*  788 */     localCalendar.set(13, 0);
/*      */ 
/*  791 */     TimeZoneAdjust(paramConnection, localCalendar, paramCalendar);
/*      */ 
/*  794 */     arrayOfByte[0] = (byte)(paramCalendar.get(1) / 100 + 100);
/*  795 */     arrayOfByte[1] = (byte)(paramCalendar.get(1) % 100 + 100);
/*  796 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  797 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  798 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  799 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  800 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  802 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*  803 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static byte[] toBytes(Connection paramConnection, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  820 */     if (paramTimestamp == null) {
/*  821 */       return null;
/*      */     }
/*      */ 
/*  825 */     int i = paramTimestamp.getNanos();
/*      */     byte[] arrayOfByte;
/*  829 */     if (i == 0)
/*  830 */       arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     else {
/*  832 */       arrayOfByte = new byte[SIZE_TIMESTAMPLTZ];
/*      */     }
/*      */ 
/*  835 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  837 */     localCalendar.setTime(paramTimestamp);
/*      */     int j;
/*  842 */     if (localCalendar.getTimeZone().inDaylightTime(paramTimestamp))
/*  843 */       j = 1;
/*      */     else {
/*  845 */       j = 0;
/*      */     }
/*      */ 
/*  849 */     TimeZoneAdjust(paramConnection, localCalendar, paramCalendar);
/*      */ 
/*  851 */     arrayOfByte[0] = (byte)(paramCalendar.get(1) / 100 + 100);
/*  852 */     arrayOfByte[1] = (byte)(paramCalendar.get(1) % 100 + 100);
/*  853 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  854 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  855 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  856 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  857 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  863 */     if (i != 0)
/*      */     {
/*  865 */       arrayOfByte[7] = (byte)(paramTimestamp.getNanos() >> 24);
/*  866 */       arrayOfByte[8] = (byte)(paramTimestamp.getNanos() >> 16 & 0xFF);
/*  867 */       arrayOfByte[9] = (byte)(paramTimestamp.getNanos() >> 8 & 0xFF);
/*  868 */       arrayOfByte[10] = (byte)(paramTimestamp.getNanos() & 0xFF);
/*      */     }
/*      */ 
/*  871 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*      */ 
/*  873 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static byte[] toBytes(Connection paramConnection, DATE paramDATE, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  890 */     if (paramDATE == null) {
/*  891 */       return null;
/*      */     }
/*  893 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */ 
/*  895 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/*  897 */     localCalendar.setTime(DATE.toDate(paramDATE.toBytes()));
/*      */     int i;
/*  902 */     if (localCalendar.getTimeZone().inDaylightTime(new java.sql.Date(localCalendar.getTime().getTime())))
/*  903 */       i = 1;
/*      */     else {
/*  905 */       i = 0;
/*      */     }
/*      */ 
/*  908 */     TimeZoneAdjust(paramConnection, localCalendar, paramCalendar);
/*      */ 
/*  910 */     arrayOfByte[0] = (byte)(paramCalendar.get(1) / 100 + 100);
/*  911 */     arrayOfByte[1] = (byte)(paramCalendar.get(1) % 100 + 100);
/*  912 */     arrayOfByte[2] = (byte)(paramCalendar.get(2) + 1);
/*  913 */     arrayOfByte[3] = (byte)paramCalendar.get(5);
/*  914 */     arrayOfByte[4] = (byte)(paramCalendar.get(11) + 1);
/*  915 */     arrayOfByte[5] = (byte)(paramCalendar.get(12) + 1);
/*  916 */     arrayOfByte[6] = (byte)(paramCalendar.get(13) + 1);
/*      */ 
/*  918 */     TimeZone.setDefault(localCalendar.getTimeZone());
/*  919 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/*  934 */     return toBytes(paramConnection, Timestamp.valueOf(paramString), paramCalendar);
/*      */   }
/*      */ 
/*      */   public static java.sql.Date toDate(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  959 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/*  961 */     if (i == SIZE_TIMESTAMPLTZ)
/*  962 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/*  964 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/*  966 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/*  967 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/*  970 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/*  973 */     getDbTimeZone(paramConnection);
/*      */ 
/*  976 */     Calendar localCalendar = getSessCalendar(paramConnection);
/*      */ 
/*  980 */     int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/*  982 */     TimeZone.setDefault(dbtz.getTimeZone());
/*      */ 
/*  984 */     dbtz.set(1, k);
/*  985 */     dbtz.set(2, arrayOfInt[2] - 1);
/*  986 */     dbtz.set(5, arrayOfInt[3]);
/*  987 */     dbtz.set(11, arrayOfInt[4] - 1);
/*  988 */     dbtz.set(12, arrayOfInt[5] - 1);
/*  989 */     dbtz.set(13, arrayOfInt[6] - 1);
/*  990 */     dbtz.set(14, 0);
/*      */ 
/*  993 */     TimeZoneAdjust(paramConnection, dbtz, localCalendar);
/*      */ 
/*  997 */     long l = localCalendar.getTime().getTime();
/*      */ 
/*  999 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1003 */     return new java.sql.Date(l);
/*      */   }
/*      */ 
/*      */   public static Time toTime(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1023 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/* 1025 */     if (i == SIZE_TIMESTAMPLTZ)
/* 1026 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/* 1028 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/* 1030 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/* 1031 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/* 1034 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1037 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1040 */     Calendar localCalendar = getSessCalendar(paramConnection);
/*      */ 
/* 1044 */     int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/* 1047 */     TimeZone.setDefault(dbtz.getTimeZone());
/*      */ 
/* 1049 */     dbtz.set(1, k);
/* 1050 */     dbtz.set(2, arrayOfInt[2] - 1);
/* 1051 */     dbtz.set(5, arrayOfInt[3]);
/* 1052 */     dbtz.set(11, arrayOfInt[4] - 1);
/* 1053 */     dbtz.set(12, arrayOfInt[5] - 1);
/* 1054 */     dbtz.set(13, arrayOfInt[6] - 1);
/*      */ 
/* 1058 */     TimeZoneAdjust(paramConnection, dbtz, localCalendar);
/*      */ 
/* 1061 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1068 */     return new Time(localCalendar.get(11), localCalendar.get(12), localCalendar.get(13));
/*      */   }
/*      */ 
/*      */   public static Timestamp toTimestamp(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1085 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/* 1087 */     if (i == SIZE_TIMESTAMPLTZ)
/* 1088 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/* 1090 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/* 1092 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/* 1093 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/* 1096 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1100 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1103 */     Calendar localCalendar = getSessCalendar(paramConnection);
/*      */ 
/* 1107 */     int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/* 1110 */     TimeZone.setDefault(dbtz.getTimeZone());
/*      */ 
/* 1112 */     dbtz.set(1, k);
/* 1113 */     dbtz.set(2, arrayOfInt[2] - 1);
/* 1114 */     dbtz.set(5, arrayOfInt[3]);
/* 1115 */     dbtz.set(11, arrayOfInt[4] - 1);
/* 1116 */     dbtz.set(12, arrayOfInt[5] - 1);
/* 1117 */     dbtz.set(13, arrayOfInt[6] - 1);
/* 1118 */     dbtz.set(14, 0);
/*      */ 
/* 1121 */     TimeZoneAdjust(paramConnection, dbtz, localCalendar);
/*      */ 
/* 1126 */     long l = localCalendar.getTime().getTime();
/*      */ 
/* 1129 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1133 */     Timestamp localTimestamp = new Timestamp(l);
/* 1134 */     int m = 0;
/*      */ 
/* 1137 */     if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ)
/*      */     {
/* 1139 */       m = arrayOfInt[7] << 24;
/* 1140 */       m |= arrayOfInt[8] << 16;
/* 1141 */       m |= arrayOfInt[9] << 8;
/* 1142 */       m |= arrayOfInt[10] & 0xFF;
/* 1143 */       localTimestamp.setNanos(m);
/*      */     }
/*      */     else
/*      */     {
/* 1147 */       localTimestamp.setNanos(m);
/*      */     }
/* 1149 */     return localTimestamp;
/*      */   }
/*      */ 
/*      */   public static DATE toDATE(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1169 */     int i = paramArrayOfByte.length;
/*      */     int[] arrayOfInt;
/* 1171 */     if (i == SIZE_TIMESTAMPLTZ)
/* 1172 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ];
/*      */     else {
/* 1174 */       arrayOfInt = new int[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     }
/* 1176 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/* 1177 */       paramArrayOfByte[j] &= 255;
/*      */     }
/*      */ 
/* 1180 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1183 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1186 */     Calendar localCalendar = getSessCalendar(paramConnection);
/*      */ 
/* 1190 */     int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
/*      */ 
/* 1192 */     TimeZone.setDefault(dbtz.getTimeZone());
/*      */ 
/* 1194 */     dbtz.set(1, k);
/* 1195 */     dbtz.set(2, arrayOfInt[2] - 1);
/* 1196 */     dbtz.set(5, arrayOfInt[3]);
/* 1197 */     dbtz.set(11, arrayOfInt[4] - 1);
/* 1198 */     dbtz.set(12, arrayOfInt[5] - 1);
/* 1199 */     dbtz.set(13, arrayOfInt[6] - 1);
/*      */ 
/* 1202 */     TimeZoneAdjust(paramConnection, dbtz, localCalendar);
/*      */ 
/* 1206 */     long l = localCalendar.getTime().getTime();
/*      */ 
/* 1208 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1214 */     return new DATE(new java.sql.Date(l));
/*      */   }
/*      */ 
/*      */   public static String toString(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1230 */     Timestamp localTimestamp = toTimestamp(paramConnection, paramArrayOfByte);
/* 1231 */     Calendar localCalendar = Calendar.getInstance();
/*      */ 
/* 1233 */     localCalendar.setTime(localTimestamp);
/*      */ 
/* 1237 */     int i = localCalendar.get(1);
/* 1238 */     int j = localCalendar.get(2) + 1;
/* 1239 */     int k = localCalendar.get(5);
/* 1240 */     int m = localCalendar.get(11);
/* 1241 */     int n = localCalendar.get(12);
/* 1242 */     int i1 = localCalendar.get(13);
/* 1243 */     int i2 = 0;
/*      */ 
/* 1245 */     if (paramArrayOfByte.length == SIZE_TIMESTAMPLTZ)
/*      */     {
/* 1247 */       i2 = (paramArrayOfByte[7] & 0xFF) << 24;
/* 1248 */       i2 |= (paramArrayOfByte[8] & 0xFF) << 16;
/* 1249 */       i2 |= (paramArrayOfByte[9] & 0xFF) << 8;
/* 1250 */       i2 |= paramArrayOfByte[10] & 0xFF;
/*      */     }
/*      */ 
/* 1253 */     return i + "-" + j + "-" + k + " " + m + "." + n + "." + i1 + "." + i2;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 1274 */     if (paramTime == null) {
/* 1275 */       return null;
/*      */     }
/* 1277 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */ 
/* 1280 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1283 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1285 */     paramCalendar.setTime(paramTime);
/*      */     int i;
/* 1293 */     if (dbtz.getTimeZone().inDaylightTime(paramTime))
/* 1294 */       i = 1;
/*      */     else {
/* 1296 */       i = 0;
/*      */     }
/* 1298 */     paramCalendar.set(1, (CENTURY_DEFAULT - 100) * 100 + DECADE_DEFAULT % 100);
/* 1299 */     paramCalendar.set(2, MONTH_DEFAULT - 1);
/* 1300 */     paramCalendar.set(5, DAY_DEFAULT);
/*      */ 
/* 1303 */     TimeZoneAdjust(paramConnection, paramCalendar, dbtz);
/*      */ 
/* 1305 */     arrayOfByte[0] = (byte)(dbtz.get(1) / 100 + 100);
/* 1306 */     arrayOfByte[1] = (byte)(dbtz.get(1) % 100 + 100);
/* 1307 */     arrayOfByte[2] = (byte)(dbtz.get(2) + 1);
/* 1308 */     arrayOfByte[3] = (byte)dbtz.get(5);
/* 1309 */     arrayOfByte[4] = (byte)(dbtz.get(11) + 1);
/* 1310 */     arrayOfByte[5] = (byte)(dbtz.get(12) + 1);
/* 1311 */     arrayOfByte[6] = (byte)(dbtz.get(13) + 1);
/*      */ 
/* 1313 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1315 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, java.sql.Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 1331 */     if (paramDate == null) {
/* 1332 */       return null;
/*      */     }
/* 1334 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */ 
/* 1337 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1340 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1342 */     paramCalendar.setTime(paramDate);
/*      */     int i;
/* 1347 */     if (paramCalendar.getTimeZone().inDaylightTime(paramDate))
/* 1348 */       i = 1;
/*      */     else {
/* 1350 */       i = 0;
/*      */     }
/* 1352 */     paramCalendar.set(11, 0);
/* 1353 */     paramCalendar.set(12, 0);
/* 1354 */     paramCalendar.set(13, 0);
/*      */ 
/* 1357 */     TimeZoneAdjust(paramConnection, paramCalendar, dbtz);
/*      */ 
/* 1360 */     arrayOfByte[0] = (byte)(dbtz.get(1) / 100 + 100);
/* 1361 */     arrayOfByte[1] = (byte)(dbtz.get(1) % 100 + 100);
/* 1362 */     arrayOfByte[2] = (byte)(dbtz.get(2) + 1);
/* 1363 */     arrayOfByte[3] = (byte)dbtz.get(5);
/* 1364 */     arrayOfByte[4] = (byte)(dbtz.get(11) + 1);
/* 1365 */     arrayOfByte[5] = (byte)(dbtz.get(12) + 1);
/* 1366 */     arrayOfByte[6] = (byte)(dbtz.get(13) + 1);
/*      */ 
/* 1368 */     TimeZone.setDefault(localTimeZone);
/* 1369 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 1385 */     if (paramTimestamp == null) {
/* 1386 */       return null;
/*      */     }
/*      */ 
/* 1390 */     int i = paramTimestamp.getNanos();
/*      */     byte[] arrayOfByte;
/* 1394 */     if (i == 0)
/* 1395 */       arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */     else {
/* 1397 */       arrayOfByte = new byte[SIZE_TIMESTAMPLTZ];
/*      */     }
/*      */ 
/* 1400 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1403 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1405 */     paramCalendar.setTime(paramTimestamp);
/*      */     int j;
/* 1410 */     if (paramCalendar.getTimeZone().inDaylightTime(paramTimestamp))
/* 1411 */       j = 1;
/*      */     else {
/* 1413 */       j = 0;
/*      */     }
/*      */ 
/* 1417 */     TimeZoneAdjust(paramConnection, paramCalendar, dbtz);
/*      */ 
/* 1419 */     arrayOfByte[0] = (byte)(dbtz.get(1) / 100 + 100);
/* 1420 */     arrayOfByte[1] = (byte)(dbtz.get(1) % 100 + 100);
/* 1421 */     arrayOfByte[2] = (byte)(dbtz.get(2) + 1);
/* 1422 */     arrayOfByte[3] = (byte)dbtz.get(5);
/* 1423 */     arrayOfByte[4] = (byte)(dbtz.get(11) + 1);
/* 1424 */     arrayOfByte[5] = (byte)(dbtz.get(12) + 1);
/* 1425 */     arrayOfByte[6] = (byte)(dbtz.get(13) + 1);
/*      */ 
/* 1431 */     if (i != 0)
/*      */     {
/* 1433 */       arrayOfByte[7] = (byte)(paramTimestamp.getNanos() >> 24);
/* 1434 */       arrayOfByte[8] = (byte)(paramTimestamp.getNanos() >> 16 & 0xFF);
/* 1435 */       arrayOfByte[9] = (byte)(paramTimestamp.getNanos() >> 8 & 0xFF);
/* 1436 */       arrayOfByte[10] = (byte)(paramTimestamp.getNanos() & 0xFF);
/*      */     }
/*      */ 
/* 1439 */     TimeZone.setDefault(localTimeZone);
/*      */ 
/* 1441 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/* 1457 */     if (paramDATE == null) {
/* 1458 */       return null;
/*      */     }
/* 1460 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ_NOFRAC];
/*      */ 
/* 1463 */     TimeZone localTimeZone = TimeZone.getDefault();
/*      */ 
/* 1466 */     getDbTimeZone(paramConnection);
/*      */ 
/* 1468 */     paramCalendar.setTime(DATE.toDate(paramDATE.toBytes()));
/*      */     int i;
/* 1473 */     if (paramCalendar.getTimeZone().inDaylightTime(new java.sql.Date(paramCalendar.getTime().getTime())))
/* 1474 */       i = 1;
/*      */     else {
/* 1476 */       i = 0;
/*      */     }
/*      */ 
/* 1479 */     TimeZoneAdjust(paramConnection, paramCalendar, dbtz);
/*      */ 
/* 1481 */     arrayOfByte[0] = (byte)(dbtz.get(1) / 100 + 100);
/* 1482 */     arrayOfByte[1] = (byte)(dbtz.get(1) % 100 + 100);
/* 1483 */     arrayOfByte[2] = (byte)(dbtz.get(2) + 1);
/* 1484 */     arrayOfByte[3] = (byte)dbtz.get(5);
/* 1485 */     arrayOfByte[4] = (byte)(dbtz.get(11) + 1);
/* 1486 */     arrayOfByte[5] = (byte)(dbtz.get(12) + 1);
/* 1487 */     arrayOfByte[6] = (byte)(dbtz.get(13) + 1);
/*      */ 
/* 1489 */     TimeZone.setDefault(localTimeZone);
/* 1490 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static byte[] toBytes(Connection paramConnection, Calendar paramCalendar, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1505 */     return toBytes(paramConnection, paramCalendar, Timestamp.valueOf(paramString));
/*      */   }
/*      */ 
/*      */   public String stringValue(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 1518 */     return toString(paramConnection, getBytes());
/*      */   }
/*      */ 
/*      */   public String stringValue(Connection paramConnection, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1533 */     return toString(paramConnection, getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   public java.sql.Date dateValue(Connection paramConnection, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1548 */     return toDate(paramConnection, getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   public java.sql.Date dateValue(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 1562 */     return toDate(paramConnection, getBytes());
/*      */   }
/*      */ 
/*      */   public Time timeValue(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 1573 */     return toTime(paramConnection, getBytes());
/*      */   }
/*      */ 
/*      */   public Time timeValue(Connection paramConnection, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1584 */     return toTime(paramConnection, getBytes(), paramCalendar);
/*      */   }
/*      */ 
/*      */   private static byte[] initTimestampltz()
/*      */   {
/* 1600 */     byte[] arrayOfByte = new byte[SIZE_TIMESTAMPLTZ];
/*      */ 
/* 1602 */     arrayOfByte[0] = (byte)CENTURY_DEFAULT;
/* 1603 */     arrayOfByte[1] = (byte)DECADE_INIT;
/* 1604 */     arrayOfByte[2] = (byte)MONTH_DEFAULT;
/* 1605 */     arrayOfByte[3] = (byte)DAY_DEFAULT;
/* 1606 */     arrayOfByte[4] = 1;
/* 1607 */     arrayOfByte[5] = 1;
/* 1608 */     arrayOfByte[6] = 1;
/*      */ 
/* 1610 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public Object toJdbc()
/*      */     throws SQLException
/*      */   {
/* 1624 */     return timestampValue();
/*      */   }
/*      */ 
/*      */   public Object makeJdbcArray(int paramInt)
/*      */   {
/* 1635 */     Timestamp[] arrayOfTimestamp = new Timestamp[paramInt];
/*      */ 
/* 1637 */     return arrayOfTimestamp;
/*      */   }
/*      */ 
/*      */   public boolean isConvertibleTo(Class paramClass)
/*      */   {
/* 1653 */     return (paramClass.getName().compareTo("java.sql.Date") == 0) || (paramClass.getName().compareTo("java.sql.Time") == 0) || (paramClass.getName().compareTo("java.sql.Timestamp") == 0) || (paramClass.getName().compareTo("java.lang.String") == 0);
/*      */   }
/*      */ 
/*      */   static void TimeZoneAdjust(Connection paramConnection, Calendar paramCalendar1, Calendar paramCalendar2)
/*      */     throws SQLException
/*      */   {
/* 1701 */     String str1 = new String(paramCalendar1.getTimeZone().getID());
/* 1702 */     String str2 = new String(paramCalendar2.getTimeZone().getID());
/*      */ 
/* 1706 */     if ((!str2.equals(str1)) && ((!str2.equals("Custom")) || (!str1.equals("Custom"))))
/*      */     {
/* 1710 */       OffsetDST localOffsetDST = new OffsetDST();
/*      */ 
/* 1715 */       k = getZoneOffset(paramConnection, paramCalendar1, localOffsetDST);
/*      */ 
/* 1717 */       m = localOffsetDST.getOFFSET();
/*      */ 
/* 1720 */       paramCalendar1.add(11, -(m / HOUR_MILLISECOND));
/* 1721 */       paramCalendar1.add(12, -(m % HOUR_MILLISECOND) / MINUTE_MILLISECOND);
/*      */       int i;
/* 1723 */       if (str2.equals("Custom")) {
/* 1724 */         i = paramCalendar2.getTimeZone().getRawOffset();
/*      */       }
/*      */       else
/*      */       {
/* 1730 */         n = ZONEIDMAP.getID(str2);
/*      */ 
/* 1732 */         if (n == ZONEIDMAP.INV_ZONEID)
/*      */         {
/* 1734 */           if (paramCalendar2.getTimeZone().useDaylightTime())
/* 1735 */             throw new SQLException("Timezone not supported");
/* 1736 */           i = paramCalendar2.getTimeZone().getRawOffset();
/*      */         }
/*      */         else
/*      */         {
/* 1740 */           if (TIMEZONETAB.checkID(n)) {
/* 1741 */             TIMEZONETAB.updateTable(paramConnection, n);
/*      */           }
/*      */ 
/* 1744 */           i = TIMEZONETAB.getOffset(paramCalendar1, n);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1750 */       paramCalendar1.add(11, i / HOUR_MILLISECOND);
/* 1751 */       paramCalendar1.add(12, i % HOUR_MILLISECOND / MINUTE_MILLISECOND);
/*      */     }
/*      */ 
/* 1758 */     if ((str2.equals("Custom")) && (str1.equals("Custom")))
/*      */     {
/* 1760 */       j = paramCalendar1.getTimeZone().getRawOffset();
/* 1761 */       k = paramCalendar2.getTimeZone().getRawOffset();
/* 1762 */       m = 0;
/*      */ 
/* 1764 */       if (j != k)
/*      */       {
/* 1767 */         m = j - k;
/* 1768 */         m = m > 0 ? m : -m;
/*      */       }
/*      */ 
/* 1771 */       if (j > k) {
/* 1772 */         m = -m;
/*      */       }
/* 1774 */       paramCalendar1.add(11, m / HOUR_MILLISECOND);
/* 1775 */       paramCalendar1.add(12, m % HOUR_MILLISECOND / MINUTE_MILLISECOND);
/*      */     }
/*      */ 
/* 1780 */     int j = paramCalendar1.get(1);
/* 1781 */     int k = paramCalendar1.get(2);
/* 1782 */     int m = paramCalendar1.get(5);
/* 1783 */     int n = paramCalendar1.get(11);
/* 1784 */     int i1 = paramCalendar1.get(12);
/* 1785 */     int i2 = paramCalendar1.get(13);
/* 1786 */     int i3 = paramCalendar1.get(14);
/*      */ 
/* 1789 */     TimeZone.setDefault(paramCalendar2.getTimeZone());
/*      */ 
/* 1794 */     paramCalendar2.set(1, j);
/* 1795 */     paramCalendar2.set(2, k);
/* 1796 */     paramCalendar2.set(5, m);
/* 1797 */     paramCalendar2.set(11, n);
/* 1798 */     paramCalendar2.set(12, i1);
/* 1799 */     paramCalendar2.set(13, i2);
/* 1800 */     paramCalendar2.set(14, i3);
/*      */   }
/*      */ 
/*      */   private static int getJavaYear(int paramInt1, int paramInt2)
/*      */   {
/* 1809 */     return (paramInt1 - 100) * 100 + (paramInt2 - 100);
/*      */   }
/*      */ 
/*      */   private static byte getZoneOffset(Connection paramConnection, Calendar paramCalendar, OffsetDST paramOffsetDST)
/*      */     throws SQLException
/*      */   {
/* 1816 */     int i = 0;
/*      */ 
/* 1820 */     if (paramCalendar.getTimeZone().getID() == "Custom") {
/* 1821 */       paramOffsetDST.setOFFSET(paramCalendar.getTimeZone().getRawOffset());
/*      */     }
/*      */     else
/*      */     {
/* 1826 */       String str = new String(paramCalendar.getTimeZone().getID());
/*      */ 
/* 1831 */       int j = ZONEIDMAP.getID(str);
/* 1832 */       if (j == ZONEIDMAP.INV_ZONEID)
/*      */       {
/* 1834 */         if (paramCalendar.getTimeZone().useDaylightTime()) {
/* 1835 */           throw new SQLException("Timezone not supported");
/*      */         }
/* 1837 */         paramOffsetDST.setOFFSET(paramCalendar.getTimeZone().getRawOffset());
/*      */       }
/*      */       else
/*      */       {
/* 1841 */         if (TIMEZONETAB.checkID(j)) {
/* 1842 */           TIMEZONETAB.updateTable(paramConnection, j);
/*      */         }
/*      */ 
/* 1845 */         i = TIMEZONETAB.getLocalOffset(paramCalendar, j, paramOffsetDST);
/*      */       }
/*      */     }
/*      */ 
/* 1849 */     return i;
/*      */   }
/*      */ 
/*      */   private static Calendar getDbTzCalendar(String paramString)
/*      */   {
/* 1859 */     int i = paramString.charAt(0);
/*      */     String str;
/* 1861 */     if ((i == 43) || (i == 45))
/*      */     {
/* 1863 */       str = "GMT" + paramString;
/*      */     }
/*      */     else
/*      */     {
/* 1867 */       str = paramString;
/*      */     }
/*      */ 
/* 1872 */     TimeZone localTimeZone = TimeZone.getTimeZone(str);
/*      */ 
/* 1874 */     return new GregorianCalendar(localTimeZone);
/*      */   }
/*      */ 
/*      */   private static Calendar getSessCalendar(Connection paramConnection)
/*      */   {
/* 1881 */     String str = ((OracleConnection)paramConnection).getSessionTimeZone();
/*      */     Calendar localCalendar;
/* 1884 */     if (str == null) {
/* 1885 */       localCalendar = Calendar.getInstance();
/*      */     }
/*      */     else {
/* 1888 */       TimeZone localTimeZone = TimeZone.getDefault();
/* 1889 */       localTimeZone.setID(str);
/* 1890 */       localCalendar = Calendar.getInstance(localTimeZone);
/*      */     }
/*      */ 
/* 1893 */     return localCalendar;
/*      */   }
/*      */ 
/*      */   private static synchronized void getDbTimeZone(Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 1905 */     if (!cached)
/*      */     {
/* 1907 */       OraclePreparedStatement localOraclePreparedStatement = null;
/* 1908 */       ResultSet localResultSet = null;
/* 1909 */       localOraclePreparedStatement = (OraclePreparedStatement)paramConnection.prepareStatement("SELECT DBTIMEZONE FROM DUAL");
/*      */ 
/* 1911 */       localResultSet = localOraclePreparedStatement.executeQuery();
/* 1912 */       localResultSet.next();
/* 1913 */       String str = localResultSet.getString(1);
/* 1914 */       localResultSet.close();
/* 1915 */       localOraclePreparedStatement.close();
/* 1916 */       dbtz = getDbTzCalendar(str);
/* 1917 */       cached = true;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TIMESTAMPLTZ
 * JD-Core Version:    0.6.0
 */