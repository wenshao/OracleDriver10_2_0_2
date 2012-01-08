/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.TimeZone;
/*     */ import oracle.sql.DATE;
/*     */ 
/*     */ abstract class DateTimeCommonAccessor extends Accessor
/*     */ {
/*     */   static final int GREGORIAN_CUTOVER_YEAR = 1582;
/*     */   static final long GREGORIAN_CUTOVER = -12219292800000L;
/*     */   static final int JAN_1_1_JULIAN_DAY = 1721426;
/*     */   static final int EPOCH_JULIAN_DAY = 2440588;
/*     */   static final int ONE_SECOND = 1000;
/*     */   static final int ONE_MINUTE = 60000;
/*     */   static final int ONE_HOUR = 3600000;
/*     */   static final long ONE_DAY = 86400000L;
/*  41 */   static final int[] NUM_DAYS = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };
/*     */ 
/*  46 */   static final int[] LEAP_NUM_DAYS = { 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 };
/*     */   static final int ORACLE_CENTURY = 0;
/*     */   static final int ORACLE_YEAR = 1;
/*     */   static final int ORACLE_MONTH = 2;
/*     */   static final int ORACLE_DAY = 3;
/*     */   static final int ORACLE_HOUR = 4;
/*     */   static final int ORACLE_MIN = 5;
/*     */   static final int ORACLE_SEC = 6;
/*     */   static final int ORACLE_NANO1 = 7;
/*     */   static final int ORACLE_NANO2 = 8;
/*     */   static final int ORACLE_NANO3 = 9;
/*     */   static final int ORACLE_NANO4 = 10;
/*     */   static final int ORACLE_TZ1 = 11;
/*     */   static final int ORACLE_TZ2 = 12;
/*     */   static final int SIZE_DATE = 7;
/*     */   static final int MAX_TIMESTAMP_LENGTH = 11;
/*     */   static TimeZone epochTimeZone;
/*     */   static long epochTimeZoneOffset;
/*     */ 
/*     */   java.sql.Date getDate(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  75 */     java.sql.Date localDate = null;
/*     */ 
/*  77 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  82 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  89 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/*  91 */       int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/*  95 */       TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/*     */ 
/*  98 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 102 */       if (j <= 0)
/* 103 */         j++;
/*     */       try
/*     */       {
/* 106 */         localDate = new java.sql.Date(getMillis(j, oracleMonth(i), oracleDay(i), 0, localTimeZone));
/*     */       }
/*     */       catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*     */       {
/* 110 */         DatabaseError.throwSqlException(132);
/*     */       }
/*     */     }
/*     */ 
/* 114 */     return localDate;
/*     */   }
/*     */ 
/*     */   Time getTime(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 121 */     Time localTime = null;
/*     */ 
/* 123 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 128 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 135 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 137 */       int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 141 */       TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/*     */ 
/* 143 */       if (localTimeZone != epochTimeZone)
/*     */       {
/* 145 */         epochTimeZoneOffset = calculateEpochOffset(localTimeZone);
/* 146 */         epochTimeZone = localTimeZone;
/*     */       }
/*     */ 
/* 149 */       localTime = new Time(oracleTime(i) - epochTimeZoneOffset);
/*     */     }
/*     */ 
/* 152 */     return localTime;
/*     */   }
/*     */ 
/*     */   java.sql.Date getDate(int paramInt, Calendar paramCalendar)
/*     */     throws SQLException
/*     */   {
/* 159 */     if (paramCalendar == null) {
/* 160 */       return getDate(paramInt);
/*     */     }
/* 162 */     java.sql.Date localDate = null;
/*     */ 
/* 164 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 169 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 176 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 178 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 179 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 184 */       paramCalendar.set(1, j);
/* 185 */       paramCalendar.set(2, oracleMonth(i));
/* 186 */       paramCalendar.set(5, oracleDay(i));
/* 187 */       paramCalendar.set(11, oracleHour(i));
/* 188 */       paramCalendar.set(12, oracleMin(i));
/* 189 */       paramCalendar.set(13, oracleSec(i));
/* 190 */       paramCalendar.set(14, 0);
/*     */ 
/* 192 */       localDate = new java.sql.Date(paramCalendar.getTime().getTime());
/*     */     }
/*     */ 
/* 195 */     return localDate;
/*     */   }
/*     */ 
/*     */   Time getTime(int paramInt, Calendar paramCalendar)
/*     */     throws SQLException
/*     */   {
/* 202 */     if (paramCalendar == null) {
/* 203 */       return getTime(paramInt);
/*     */     }
/* 205 */     Time localTime = null;
/*     */ 
/* 207 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 212 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 219 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 221 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 222 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 227 */       paramCalendar.set(1, 1970);
/* 228 */       paramCalendar.set(2, 0);
/* 229 */       paramCalendar.set(5, 1);
/* 230 */       paramCalendar.set(11, oracleHour(i));
/* 231 */       paramCalendar.set(12, oracleMin(i));
/* 232 */       paramCalendar.set(13, oracleSec(i));
/* 233 */       paramCalendar.set(14, 0);
/*     */ 
/* 235 */       localTime = new Time(paramCalendar.getTime().getTime());
/*     */     }
/*     */ 
/* 238 */     return localTime;
/*     */   }
/*     */ 
/*     */   Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
/*     */     throws SQLException
/*     */   {
/* 245 */     if (paramCalendar == null) {
/* 246 */       return getTimestamp(paramInt);
/*     */     }
/* 248 */     Timestamp localTimestamp = null;
/*     */ 
/* 250 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 255 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 262 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 264 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 265 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 270 */       paramCalendar.set(1, j);
/* 271 */       paramCalendar.set(2, oracleMonth(i));
/* 272 */       paramCalendar.set(5, oracleDay(i));
/* 273 */       paramCalendar.set(11, oracleHour(i));
/* 274 */       paramCalendar.set(12, oracleMin(i));
/* 275 */       paramCalendar.set(13, oracleSec(i));
/* 276 */       paramCalendar.set(14, 0);
/*     */ 
/* 278 */       localTimestamp = new Timestamp(paramCalendar.getTime().getTime());
/*     */ 
/* 280 */       int k = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 281 */       if (k >= 11)
/*     */       {
/* 283 */         localTimestamp.setNanos(oracleNanos(i));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   DATE getDATE(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 296 */     DATE localDATE = null;
/*     */ 
/* 298 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 303 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 310 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 312 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 313 */       byte[] arrayOfByte = new byte[7];
/*     */ 
/* 315 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, 7);
/*     */ 
/* 317 */       localDATE = new DATE(arrayOfByte);
/*     */     }
/*     */ 
/* 320 */     return localDATE;
/*     */   }
/*     */ 
/*     */   final int oracleYear(int paramInt)
/*     */   {
/* 327 */     int i = ((this.rowSpaceByte[(0 + paramInt)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + paramInt)] & 0xFF) - 100;
/*     */ 
/* 331 */     return i <= 0 ? i + 1 : i;
/*     */   }
/*     */ 
/*     */   final int oracleMonth(int paramInt)
/*     */   {
/* 337 */     return this.rowSpaceByte[(2 + paramInt)] - 1;
/*     */   }
/*     */ 
/*     */   final int oracleDay(int paramInt)
/*     */   {
/* 343 */     return this.rowSpaceByte[(3 + paramInt)];
/*     */   }
/*     */ 
/*     */   final int oracleHour(int paramInt)
/*     */   {
/* 349 */     return this.rowSpaceByte[(4 + paramInt)] - 1;
/*     */   }
/*     */ 
/*     */   final int oracleMin(int paramInt)
/*     */   {
/* 355 */     return this.rowSpaceByte[(5 + paramInt)] - 1;
/*     */   }
/*     */ 
/*     */   final int oracleSec(int paramInt)
/*     */   {
/* 361 */     return this.rowSpaceByte[(6 + paramInt)] - 1;
/*     */   }
/*     */ 
/*     */   final int oracleTZ1(int paramInt)
/*     */   {
/* 367 */     return this.rowSpaceByte[(11 + paramInt)];
/*     */   }
/*     */ 
/*     */   final int oracleTZ2(int paramInt)
/*     */   {
/* 373 */     return this.rowSpaceByte[(12 + paramInt)];
/*     */   }
/*     */ 
/*     */   final int oracleTime(int paramInt)
/*     */   {
/* 378 */     int i = oracleHour(paramInt);
/*     */ 
/* 380 */     i *= 60;
/* 381 */     i += oracleMin(paramInt);
/* 382 */     i *= 60;
/* 383 */     i += oracleSec(paramInt);
/* 384 */     i *= 1000;
/*     */ 
/* 386 */     return i;
/*     */   }
/*     */ 
/*     */   final int oracleNanos(int paramInt)
/*     */   {
/* 391 */     int i = (this.rowSpaceByte[(7 + paramInt)] & 0xFF) << 24;
/*     */ 
/* 393 */     i |= (this.rowSpaceByte[(8 + paramInt)] & 0xFF) << 16;
/* 394 */     i |= (this.rowSpaceByte[(9 + paramInt)] & 0xFF) << 8;
/* 395 */     i |= this.rowSpaceByte[(10 + paramInt)] & 0xFF & 0xFF;
/*     */ 
/* 397 */     return i;
/*     */   }
/*     */ 
/*     */   static final long computeJulianDay(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 403 */     int i = paramInt1 % 4 == 0 ? 1 : 0;
/* 404 */     int j = paramInt1 - 1;
/* 405 */     long l = 365L * j + floorDivide(j, 4L) + 1721423L;
/*     */ 
/* 407 */     if (paramBoolean)
/*     */     {
/* 409 */       i = (i != 0) && ((paramInt1 % 100 != 0) || (paramInt1 % 400 == 0)) ? 1 : 0;
/*     */ 
/* 412 */       l += floorDivide(j, 400L) - floorDivide(j, 100L) + 2L;
/*     */     }
/*     */ 
/* 419 */     return l + paramInt3 + (i != 0 ? LEAP_NUM_DAYS[paramInt2] : NUM_DAYS[paramInt2]);
/*     */   }
/*     */ 
/*     */   static final long floorDivide(long paramLong1, long paramLong2)
/*     */   {
/* 424 */     return paramLong1 >= 0L ? paramLong1 / paramLong2 : (paramLong1 + 1L) / paramLong2 - 1L;
/*     */   }
/*     */ 
/*     */   static final long julianDayToMillis(long paramLong)
/*     */   {
/* 430 */     return (paramLong - 2440588L) * 86400000L;
/*     */   }
/*     */ 
/*     */   static final long zoneOffset(TimeZone paramTimeZone, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 438 */     return paramTimeZone.getOffset(paramInt1 < 0 ? 0 : 1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */   static long getMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TimeZone paramTimeZone)
/*     */   {
/* 457 */     boolean bool = paramInt1 >= 1582;
/* 458 */     long l1 = computeJulianDay(bool, paramInt1, paramInt2, paramInt3);
/* 459 */     long l2 = (l1 - 2440588L) * 86400000L;
/*     */ 
/* 463 */     if (bool != l2 >= -12219292800000L)
/*     */     {
/* 465 */       l1 = computeJulianDay(!bool, paramInt1, paramInt2, paramInt3);
/* 466 */       l2 = (l1 - 2440588L) * 86400000L;
/*     */     }
/*     */ 
/* 471 */     l2 += paramInt4;
/*     */ 
/* 476 */     return l2 - zoneOffset(paramTimeZone, paramInt1, paramInt2, paramInt3, julianDayToDayOfWeek(l1), paramInt4);
/*     */   }
/*     */ 
/*     */   static final int julianDayToDayOfWeek(long paramLong)
/*     */   {
/* 492 */     int i = (int)((paramLong + 1L) % 7L);
/*     */ 
/* 494 */     return i + (i < 0 ? 8 : 1);
/*     */   }
/*     */ 
/*     */   static long calculateEpochOffset(TimeZone paramTimeZone)
/*     */   {
/* 511 */     return zoneOffset(paramTimeZone, 1970, 0, 1, 5, 0);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.DateTimeCommonAccessor
 * JD-Core Version:    0.6.0
 */