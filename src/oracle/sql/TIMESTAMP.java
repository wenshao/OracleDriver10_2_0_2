/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class TIMESTAMP extends Datum
/*     */   implements Serializable
/*     */ {
/*     */   private static final int CENTURY_DEFAULT = 119;
/*     */   private static final int DECADE_DEFAULT = 100;
/*     */   private static final int MONTH_DEFAULT = 1;
/*     */   private static final int DAY_DEFAULT = 1;
/*     */   private static final int DECADE_INIT = 170;
/*     */   private static final int JAVA_YEAR = 1970;
/*     */   private static final int JAVA_MONTH = 0;
/*     */   private static final int JAVA_DATE = 1;
/*     */   private static final int SIZE_TIMESTAMP = 11;
/*     */   private static final int SIZE_TIMESTAMP_NOFRAC = 7;
/*     */   private static final int SIZE_DATE = 7;
/*     */   private static final int MINYEAR = -4712;
/*     */   private static final int MAXYEAR = 9999;
/*     */   private static final int JANMONTH = 1;
/*     */   private static final int DECMONTH = 12;
/*     */   private static final int MINDAYS = 1;
/*     */   private static final int MAXDAYS = 31;
/*     */   private static final int MINHOURS = 1;
/*     */   private static final int MAXHOURS = 24;
/*     */   private static final int MINMINUTES = 1;
/*     */   private static final int MAXMINUTES = 60;
/*     */   private static final int MINSECONDS = 1;
/*     */   private static final int MAXSECONDS = 60;
/* 886 */   private static final int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*     */   static final long serialVersionUID = -7964732752952728545L;
/*     */ 
/*     */   public TIMESTAMP()
/*     */   {
/*  69 */     super(initTimestamp());
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(byte[] paramArrayOfByte)
/*     */   {
/*  83 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(Time paramTime)
/*     */   {
/*  98 */     super(toBytes(paramTime));
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(java.sql.Date paramDate)
/*     */   {
/* 112 */     super(toBytes(paramDate));
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(Timestamp paramTimestamp)
/*     */   {
/* 126 */     super(toBytes(paramTimestamp));
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(DATE paramDATE)
/*     */   {
/* 141 */     super(toBytes(paramDATE));
/*     */   }
/*     */ 
/*     */   public TIMESTAMP(String paramString)
/*     */   {
/* 157 */     super(toBytes(paramString));
/*     */   }
/*     */ 
/*     */   public static java.sql.Date toDate(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 189 */     int i = paramArrayOfByte.length;
/*     */     int[] arrayOfInt;
/* 191 */     if (i == 11)
/* 192 */       arrayOfInt = new int[11];
/*     */     else {
/* 194 */       arrayOfInt = new int[7];
/*     */     }
/* 196 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/* 197 */       paramArrayOfByte[j] &= 255;
/*     */     }
/*     */ 
/* 202 */     j = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*     */ 
/* 206 */     Calendar localCalendar = Calendar.getInstance();
/*     */ 
/* 208 */     localCalendar.set(1, j);
/* 209 */     localCalendar.set(2, arrayOfInt[2] - 1);
/* 210 */     localCalendar.set(5, arrayOfInt[3]);
/* 211 */     localCalendar.set(11, arrayOfInt[4] - 1);
/* 212 */     localCalendar.set(12, arrayOfInt[5] - 1);
/* 213 */     localCalendar.set(13, arrayOfInt[6] - 1);
/* 214 */     localCalendar.set(14, 0);
/*     */ 
/* 220 */     long l = localCalendar.getTime().getTime();
/*     */ 
/* 224 */     return new java.sql.Date(l);
/*     */   }
/*     */ 
/*     */   public static Time toTime(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 246 */     int i = paramArrayOfByte[4] & 0xFF;
/* 247 */     int j = paramArrayOfByte[5] & 0xFF;
/* 248 */     int k = paramArrayOfByte[6] & 0xFF;
/*     */ 
/* 250 */     return new Time(i - 1, j - 1, k - 1);
/*     */   }
/*     */ 
/*     */   public static Timestamp toTimestamp(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 269 */     int i = paramArrayOfByte.length;
/*     */     int[] arrayOfInt;
/* 271 */     if (i == 11)
/* 272 */       arrayOfInt = new int[11];
/*     */     else {
/* 274 */       arrayOfInt = new int[7];
/*     */     }
/* 276 */     for (int j = 0; j < paramArrayOfByte.length; j++) {
/* 277 */       paramArrayOfByte[j] &= 255;
/*     */     }
/*     */ 
/* 281 */     j = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*     */ 
/* 286 */     Calendar localCalendar = Calendar.getInstance();
/*     */ 
/* 288 */     localCalendar.set(1, j);
/* 289 */     localCalendar.set(2, arrayOfInt[2] - 1);
/* 290 */     localCalendar.set(5, arrayOfInt[3]);
/* 291 */     localCalendar.set(11, arrayOfInt[4] - 1);
/* 292 */     localCalendar.set(12, arrayOfInt[5] - 1);
/* 293 */     localCalendar.set(13, arrayOfInt[6] - 1);
/* 294 */     localCalendar.set(14, 0);
/*     */ 
/* 298 */     long l = localCalendar.getTime().getTime();
/*     */ 
/* 302 */     Timestamp localTimestamp = new Timestamp(l);
/*     */ 
/* 306 */     if (i == 11)
/*     */     {
/* 308 */       int k = arrayOfInt[7] << 24;
/* 309 */       k |= arrayOfInt[8] << 16;
/* 310 */       k |= arrayOfInt[9] << 8;
/* 311 */       k |= arrayOfInt[10] & 0xFF;
/*     */ 
/* 314 */       localTimestamp.setNanos(k);
/*     */     }
/*     */     else {
/* 317 */       localTimestamp.setNanos(0);
/*     */     }
/*     */ 
/* 320 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   public static DATE toDATE(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 338 */     byte[] arrayOfByte = new byte[7];
/*     */ 
/* 340 */     System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, 7);
/*     */ 
/* 342 */     return new DATE(arrayOfByte);
/*     */   }
/*     */ 
/*     */   public Timestamp timestampValue()
/*     */     throws SQLException
/*     */   {
/* 355 */     return toTimestamp(getBytes());
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] paramArrayOfByte)
/*     */   {
/* 369 */     int i = 0;
/*     */ 
/* 372 */     int j = paramArrayOfByte.length;
/*     */     int[] arrayOfInt;
/* 374 */     if (j == 11)
/* 375 */       arrayOfInt = new int[11];
/*     */     else {
/* 377 */       arrayOfInt = new int[7];
/*     */     }
/*     */ 
/* 381 */     for (int k = 0; k < paramArrayOfByte.length; k++)
/*     */     {
/* 383 */       if (paramArrayOfByte[k] < 0)
/* 384 */         paramArrayOfByte[k] += 256;
/*     */       else {
/* 386 */         arrayOfInt[k] = paramArrayOfByte[k];
/*     */       }
/*     */     }
/* 389 */     arrayOfInt[4] -= 1;
/* 390 */     arrayOfInt[5] -= 1;
/* 391 */     arrayOfInt[6] -= 1;
/*     */ 
/* 393 */     int m = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*     */ 
/* 395 */     if (j == 11)
/*     */     {
/* 397 */       i = (arrayOfInt[7] & 0xFF) << 24;
/* 398 */       i |= (arrayOfInt[8] & 0xFF) << 16;
/* 399 */       i |= (arrayOfInt[9] & 0xFF) << 8;
/* 400 */       i |= arrayOfInt[10] & 0xFF & 0xFF;
/*     */     }
/*     */ 
/* 403 */     return m + "-" + arrayOfInt[2] + "-" + arrayOfInt[3] + " " + arrayOfInt[4] + "." + arrayOfInt[5] + "." + arrayOfInt[6] + "." + i;
/*     */   }
/*     */ 
/*     */   public byte[] toBytes()
/*     */   {
/* 420 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(Time paramTime)
/*     */   {
/* 435 */     if (paramTime == null) {
/* 436 */       return null;
/*     */     }
/* 438 */     byte[] arrayOfByte = new byte[7];
/* 439 */     Calendar localCalendar = Calendar.getInstance();
/* 440 */     localCalendar.setTime(paramTime);
/*     */ 
/* 442 */     arrayOfByte[0] = 119;
/* 443 */     arrayOfByte[1] = 100;
/* 444 */     arrayOfByte[2] = 1;
/* 445 */     arrayOfByte[3] = 1;
/* 446 */     arrayOfByte[4] = (byte)(localCalendar.get(11) + 1);
/* 447 */     arrayOfByte[5] = (byte)(localCalendar.get(12) + 1);
/* 448 */     arrayOfByte[6] = (byte)(localCalendar.get(13) + 1);
/*     */ 
/* 450 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(java.sql.Date paramDate)
/*     */   {
/* 464 */     if (paramDate == null) {
/* 465 */       return null;
/*     */     }
/* 467 */     byte[] arrayOfByte = new byte[7];
/* 468 */     Calendar localCalendar = Calendar.getInstance();
/*     */ 
/* 470 */     localCalendar.setTime(paramDate);
/*     */ 
/* 472 */     arrayOfByte[0] = (byte)(localCalendar.get(1) / 100 + 100);
/* 473 */     arrayOfByte[1] = (byte)(localCalendar.get(1) % 100 + 100);
/* 474 */     arrayOfByte[2] = (byte)(localCalendar.get(2) + 1);
/* 475 */     arrayOfByte[3] = (byte)localCalendar.get(5);
/* 476 */     arrayOfByte[4] = 1;
/* 477 */     arrayOfByte[5] = 1;
/* 478 */     arrayOfByte[6] = 1;
/*     */ 
/* 480 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(Timestamp paramTimestamp)
/*     */   {
/* 493 */     if (paramTimestamp == null) {
/* 494 */       return null;
/*     */     }
/*     */ 
/* 498 */     int i = paramTimestamp.getNanos();
/*     */     byte[] arrayOfByte;
/* 502 */     if (i == 0)
/* 503 */       arrayOfByte = new byte[7];
/*     */     else {
/* 505 */       arrayOfByte = new byte[11];
/*     */     }
/* 507 */     Calendar localCalendar = Calendar.getInstance();
/*     */ 
/* 509 */     localCalendar.setTime(paramTimestamp);
/*     */ 
/* 512 */     arrayOfByte[0] = (byte)(localCalendar.get(1) / 100 + 100);
/* 513 */     arrayOfByte[1] = (byte)(localCalendar.get(1) % 100 + 100);
/* 514 */     arrayOfByte[2] = (byte)(localCalendar.get(2) + 1);
/* 515 */     arrayOfByte[3] = (byte)localCalendar.get(5);
/* 516 */     arrayOfByte[4] = (byte)(localCalendar.get(11) + 1);
/* 517 */     arrayOfByte[5] = (byte)(localCalendar.get(12) + 1);
/* 518 */     arrayOfByte[6] = (byte)(localCalendar.get(13) + 1);
/*     */ 
/* 525 */     if (i != 0)
/*     */     {
/* 527 */       arrayOfByte[7] = (byte)(i >> 24);
/* 528 */       arrayOfByte[8] = (byte)(i >> 16 & 0xFF);
/* 529 */       arrayOfByte[9] = (byte)(i >> 8 & 0xFF);
/* 530 */       arrayOfByte[10] = (byte)(i & 0xFF);
/*     */     }
/*     */ 
/* 533 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(DATE paramDATE)
/*     */   {
/* 546 */     if (paramDATE == null) {
/* 547 */       return null;
/*     */     }
/* 549 */     byte[] arrayOfByte = new byte[7];
/*     */ 
/* 551 */     System.arraycopy(paramDATE.getBytes(), 0, arrayOfByte, 0, 7);
/*     */ 
/* 553 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static byte[] toBytes(String paramString)
/*     */   {
/* 565 */     return toBytes(Timestamp.valueOf(paramString));
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 577 */     return timestampValue();
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 588 */     Timestamp[] arrayOfTimestamp = new Timestamp[paramInt];
/*     */ 
/* 590 */     return arrayOfTimestamp;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 606 */     return (paramClass.getName().compareTo("java.sql.Date") == 0) || (paramClass.getName().compareTo("java.sql.Time") == 0) || (paramClass.getName().compareTo("java.sql.Timestamp") == 0) || (paramClass.getName().compareTo("java.lang.String") == 0);
/*     */   }
/*     */ 
/*     */   public static TIMESTAMP TimeZoneConvert(Connection paramConnection, TIMESTAMP paramTIMESTAMP, TimeZone paramTimeZone1, TimeZone paramTimeZone2)
/*     */     throws SQLException
/*     */   {
/* 636 */     byte[] arrayOfByte = paramTIMESTAMP.getBytes();
/*     */ 
/* 638 */     int i = arrayOfByte.length;
/*     */     int[] arrayOfInt;
/* 640 */     if (i == 11)
/* 641 */       arrayOfInt = new int[11];
/*     */     else {
/* 643 */       arrayOfInt = new int[7];
/*     */     }
/*     */ 
/* 647 */     for (int j = 0; j < i; j++) {
/* 648 */       arrayOfByte[j] &= 255;
/*     */     }
/*     */ 
/* 652 */     TimeZone localTimeZone = TimeZone.getDefault();
/*     */ 
/* 656 */     int k = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*     */ 
/* 658 */     Calendar localCalendar1 = Calendar.getInstance(paramTimeZone1);
/*     */ 
/* 660 */     Calendar localCalendar2 = Calendar.getInstance(paramTimeZone2);
/*     */ 
/* 662 */     TimeZone.setDefault(localCalendar1.getTimeZone());
/*     */ 
/* 664 */     localCalendar1.set(1, k);
/* 665 */     localCalendar1.set(2, arrayOfInt[2] - 1);
/* 666 */     localCalendar1.set(5, arrayOfInt[3]);
/* 667 */     localCalendar1.set(11, arrayOfInt[4] - 1);
/* 668 */     localCalendar1.set(12, arrayOfInt[5] - 1);
/* 669 */     localCalendar1.set(13, arrayOfInt[6] - 1);
/*     */ 
/* 674 */     localCalendar1.set(14, 0);
/*     */ 
/* 677 */     TIMESTAMPLTZ.TimeZoneAdjust(paramConnection, localCalendar1, localCalendar2);
/*     */ 
/* 682 */     long l = localCalendar2.getTime().getTime();
/*     */ 
/* 686 */     TimeZone.setDefault(localTimeZone);
/*     */ 
/* 690 */     Timestamp localTimestamp = new Timestamp(l);
/* 691 */     int m = 0;
/*     */ 
/* 696 */     if (i == 11)
/*     */     {
/* 698 */       m = arrayOfInt[7] << 24;
/* 699 */       m |= arrayOfInt[8] << 16;
/* 700 */       m |= arrayOfInt[9] << 8;
/* 701 */       m |= arrayOfInt[10] & 0xFF;
/* 702 */       localTimestamp.setNanos(m);
/*     */     }
/*     */     else
/*     */     {
/* 706 */       localTimestamp.setNanos(0);
/*     */     }
/*     */ 
/* 709 */     return new TIMESTAMP(localTimestamp);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 719 */     return toString(getBytes());
/*     */   }
/*     */ 
/*     */   public java.sql.Date dateValue()
/*     */     throws SQLException
/*     */   {
/* 730 */     return toDate(getBytes());
/*     */   }
/*     */ 
/*     */   public Time timeValue()
/*     */     throws SQLException
/*     */   {
/* 741 */     return toTime(getBytes());
/*     */   }
/*     */ 
/*     */   private static byte[] initTimestamp()
/*     */   {
/* 756 */     byte[] arrayOfByte = new byte[11];
/*     */ 
/* 758 */     arrayOfByte[0] = 119;
/* 759 */     arrayOfByte[1] = -86;
/* 760 */     arrayOfByte[2] = 1;
/* 761 */     arrayOfByte[3] = 1;
/* 762 */     arrayOfByte[4] = 1;
/* 763 */     arrayOfByte[5] = 1;
/* 764 */     arrayOfByte[6] = 1;
/*     */ 
/* 766 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   private boolean isLeapYear(int paramInt)
/*     */   {
/* 777 */     return (paramInt % 4 == 0) && (paramInt <= 1582 ? paramInt == -4712 : (paramInt % 100 != 0) || (paramInt % 400 == 0));
/*     */   }
/*     */ 
/*     */   private boolean isValid()
/*     */   {
/* 787 */     byte[] arrayOfByte = getBytes();
/* 788 */     if ((arrayOfByte.length != 11) && (arrayOfByte.length != 7))
/*     */     {
/* 791 */       return false;
/*     */     }
/*     */ 
/* 794 */     int i = ((arrayOfByte[0] & 0xFF) - 100) * 100 + ((arrayOfByte[1] & 0xFF) - 100);
/* 795 */     if ((i < -4712) || (i > 9999)) {
/* 796 */       return false;
/*     */     }
/*     */ 
/* 799 */     if (i == 0) {
/* 800 */       return false;
/*     */     }
/*     */ 
/* 803 */     int j = arrayOfByte[2] & 0xFF;
/* 804 */     if ((j < 1) || (j > 12)) {
/* 805 */       return false;
/*     */     }
/*     */ 
/* 808 */     int k = arrayOfByte[3] & 0xFF;
/* 809 */     if ((k < 1) || (k > 31))
/* 810 */       return false;
/* 811 */     if ((k > daysInMonth[(j - 1)]) && (
/* 812 */       (!isLeapYear(i)) || (j != 2) || (k != 29))) {
/* 813 */       return false;
/*     */     }
/*     */ 
/* 816 */     if ((i == 1582) && (j == 10) && (k >= 5) && (k < 15)) {
/* 817 */       return false;
/*     */     }
/*     */ 
/* 820 */     int m = arrayOfByte[4] & 0xFF;
/* 821 */     if ((m < 1) || (m > 24)) {
/* 822 */       return false;
/*     */     }
/*     */ 
/* 825 */     int n = arrayOfByte[5] & 0xFF;
/* 826 */     if ((n < 1) || (n > 60)) {
/* 827 */       return false;
/*     */     }
/*     */ 
/* 830 */     int i1 = arrayOfByte[6] & 0xFF;
/*     */ 
/* 832 */     return (i1 >= 1) && (i1 <= 60);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 844 */     paramObjectInputStream.defaultReadObject();
/* 845 */     if (!isValid())
/* 846 */       throw new IOException("Invalid TIMESTAMP");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TIMESTAMP
 * JD-Core Version:    0.6.0
 */