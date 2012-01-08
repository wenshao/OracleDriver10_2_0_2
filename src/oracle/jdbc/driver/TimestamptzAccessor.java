/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.TIMESTAMPTZ;
/*     */ import oracle.sql.TIMEZONETAB;
/*     */ import oracle.sql.ZONEIDMAP;
/*     */ 
/*     */ class TimestamptzAccessor extends DateTimeCommonAccessor
/*     */ {
/*     */   static final int maxLength = 13;
/* 394 */   static int OFFSET_HOUR = 20;
/* 395 */   static int OFFSET_MINUTE = 60;
/*     */ 
/* 398 */   static byte REGIONIDBIT = -128;
/*     */ 
/*     */   TimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  34 */     init(paramOracleStatement, 181, 181, paramShort, paramBoolean);
/*  35 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   TimestamptzAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  42 */     init(paramOracleStatement, 181, 181, paramShort, false);
/*  43 */     initForDescribe(181, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  45 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  51 */     if (paramInt1 != 0) {
/*  52 */       this.externalType = paramInt1;
/*     */     }
/*  54 */     this.internalTypeMaxLength = 13;
/*     */ 
/*  56 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  57 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  59 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  65 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  70 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  77 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
/*  78 */       return null;
/*     */     }
/*  80 */     int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/*  82 */     TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/*  83 */     Calendar localCalendar = Calendar.getInstance(localTimeZone);
/*     */ 
/*  85 */     int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/*  88 */     localCalendar.set(1, j);
/*  89 */     localCalendar.set(2, oracleMonth(i));
/*  90 */     localCalendar.set(5, oracleDay(i));
/*  91 */     localCalendar.set(11, oracleHour(i));
/*  92 */     localCalendar.set(12, oracleMin(i));
/*  93 */     localCalendar.set(13, oracleSec(i));
/*  94 */     localCalendar.set(14, 0);
/*     */     String str;
/*  98 */     if ((oracleTZ1(i) & REGIONIDBIT) != 0)
/*     */     {
/* 102 */       k = getHighOrderbits(oracleTZ1(i));
/* 103 */       k += getLowOrderbits(oracleTZ2(i));
/*     */ 
/* 106 */       if (TIMEZONETAB.checkID(k)) {
/* 107 */         TIMEZONETAB.updateTable(this.statement.connection, k);
/*     */       }
/* 109 */       m = TIMEZONETAB.getOffset(localCalendar, k);
/*     */ 
/* 112 */       localCalendar.add(10, m / 3600000);
/* 113 */       localCalendar.add(12, m % 3600000 / 60000);
/*     */ 
/* 115 */       str = new String(ZONEIDMAP.getRegion(k));
/*     */     }
/*     */     else
/*     */     {
/* 119 */       localCalendar.add(10, oracleTZ1(i) - OFFSET_HOUR);
/* 120 */       localCalendar.add(12, oracleTZ2(i) - OFFSET_MINUTE);
/*     */ 
/* 122 */       k = oracleTZ1(i) - OFFSET_HOUR;
/* 123 */       m = oracleTZ2(i) - OFFSET_MINUTE;
/*     */ 
/* 125 */       str = new String(k + ":" + m);
/*     */     }
/*     */ 
/* 129 */     j = localCalendar.get(1);
/*     */ 
/* 131 */     int k = localCalendar.get(2) + 1;
/* 132 */     int m = localCalendar.get(5);
/* 133 */     int n = localCalendar.get(11);
/* 134 */     int i1 = localCalendar.get(12);
/* 135 */     int i2 = localCalendar.get(13);
/* 136 */     int i3 = oracleNanos(i);
/*     */ 
/* 138 */     return j + "-" + k + "-" + m + " " + n + "." + i1 + "." + i2 + "." + i3 + " " + str;
/*     */   }
/*     */ 
/*     */   java.sql.Date getDate(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 145 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 150 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 157 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
/* 158 */       return null;
/*     */     }
/* 160 */     int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 162 */     TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/* 163 */     Calendar localCalendar = Calendar.getInstance(localTimeZone);
/*     */ 
/* 165 */     int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 168 */     localCalendar.set(1, j);
/* 169 */     localCalendar.set(2, oracleMonth(i));
/* 170 */     localCalendar.set(5, oracleDay(i));
/* 171 */     localCalendar.set(11, oracleHour(i));
/* 172 */     localCalendar.set(12, oracleMin(i));
/* 173 */     localCalendar.set(13, oracleSec(i));
/* 174 */     localCalendar.set(14, 0);
/*     */ 
/* 176 */     if ((oracleTZ1(i) & REGIONIDBIT) != 0)
/*     */     {
/* 180 */       int k = getHighOrderbits(oracleTZ1(i));
/* 181 */       k += getLowOrderbits(oracleTZ2(i));
/*     */ 
/* 184 */       if (TIMEZONETAB.checkID(k)) {
/* 185 */         TIMEZONETAB.updateTable(this.statement.connection, k);
/*     */       }
/* 187 */       int m = TIMEZONETAB.getOffset(localCalendar, k);
/*     */ 
/* 190 */       localCalendar.add(10, m / 3600000);
/* 191 */       localCalendar.add(12, m % 3600000 / 60000);
/*     */     }
/*     */     else
/*     */     {
/* 195 */       localCalendar.add(10, oracleTZ1(i) - OFFSET_HOUR);
/* 196 */       localCalendar.add(12, oracleTZ2(i) - OFFSET_MINUTE);
/*     */     }
/*     */ 
/* 200 */     long l = localCalendar.getTime().getTime();
/*     */ 
/* 203 */     return new java.sql.Date(l);
/*     */   }
/*     */ 
/*     */   Time getTime(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 209 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 214 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 221 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
/* 222 */       return null;
/*     */     }
/* 224 */     int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 226 */     TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/* 227 */     Calendar localCalendar = Calendar.getInstance(localTimeZone);
/*     */ 
/* 229 */     int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 232 */     localCalendar.set(1, j);
/* 233 */     localCalendar.set(2, oracleMonth(i));
/* 234 */     localCalendar.set(5, oracleDay(i));
/* 235 */     localCalendar.set(11, oracleHour(i));
/* 236 */     localCalendar.set(12, oracleMin(i));
/* 237 */     localCalendar.set(13, oracleSec(i));
/* 238 */     localCalendar.set(14, 0);
/*     */ 
/* 240 */     if ((oracleTZ1(i) & REGIONIDBIT) != 0)
/*     */     {
/* 244 */       int k = getHighOrderbits(oracleTZ1(i));
/* 245 */       k += getLowOrderbits(oracleTZ2(i));
/*     */ 
/* 248 */       if (TIMEZONETAB.checkID(k)) {
/* 249 */         TIMEZONETAB.updateTable(this.statement.connection, k);
/*     */       }
/* 251 */       int m = TIMEZONETAB.getOffset(localCalendar, k);
/*     */ 
/* 254 */       localCalendar.add(10, m / 3600000);
/* 255 */       localCalendar.add(12, m % 3600000 / 60000);
/*     */     }
/*     */     else
/*     */     {
/* 259 */       localCalendar.add(10, oracleTZ1(i) - OFFSET_HOUR);
/* 260 */       localCalendar.add(12, oracleTZ2(i) - OFFSET_MINUTE);
/*     */     }
/*     */ 
/* 264 */     long l = localCalendar.getTime().getTime();
/*     */ 
/* 267 */     return new Time(l);
/*     */   }
/*     */ 
/*     */   Timestamp getTimestamp(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 273 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 278 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 285 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
/* 286 */       return null;
/*     */     }
/* 288 */     int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 290 */     TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/* 291 */     Calendar localCalendar = Calendar.getInstance(localTimeZone);
/*     */ 
/* 293 */     int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 296 */     localCalendar.set(1, j);
/* 297 */     localCalendar.set(2, oracleMonth(i));
/* 298 */     localCalendar.set(5, oracleDay(i));
/* 299 */     localCalendar.set(11, oracleHour(i));
/* 300 */     localCalendar.set(12, oracleMin(i));
/* 301 */     localCalendar.set(13, oracleSec(i));
/* 302 */     localCalendar.set(14, 0);
/*     */ 
/* 304 */     if ((oracleTZ1(i) & REGIONIDBIT) != 0)
/*     */     {
/* 308 */       int k = getHighOrderbits(oracleTZ1(i));
/* 309 */       k += getLowOrderbits(oracleTZ2(i));
/*     */ 
/* 312 */       if (TIMEZONETAB.checkID(k)) {
/* 313 */         TIMEZONETAB.updateTable(this.statement.connection, k);
/*     */       }
/* 315 */       int m = TIMEZONETAB.getOffset(localCalendar, k);
/*     */ 
/* 318 */       localCalendar.add(10, m / 3600000);
/* 319 */       localCalendar.add(12, m % 3600000 / 60000);
/*     */     }
/*     */     else
/*     */     {
/* 323 */       localCalendar.add(10, oracleTZ1(i) - OFFSET_HOUR);
/* 324 */       localCalendar.add(12, oracleTZ2(i) - OFFSET_MINUTE);
/*     */     }
/*     */ 
/* 328 */     long l = localCalendar.getTime().getTime();
/*     */ 
/* 331 */     Timestamp localTimestamp = new Timestamp(l);
/*     */ 
/* 334 */     int n = oracleNanos(i);
/*     */ 
/* 337 */     localTimestamp.setNanos(n);
/*     */ 
/* 339 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 345 */     return getTIMESTAMPTZ(paramInt);
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 351 */     return getTIMESTAMPTZ(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 357 */     return getTIMESTAMPTZ(paramInt);
/*     */   }
/*     */ 
/*     */   TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 363 */     TIMESTAMPTZ localTIMESTAMPTZ = null;
/*     */ 
/* 365 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 370 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 377 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 379 */       int i = this.columnIndex + this.byteLength * paramInt;
/* 380 */       byte[] arrayOfByte = new byte[13];
/*     */ 
/* 382 */       System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, 13);
/*     */ 
/* 384 */       localTIMESTAMPTZ = new TIMESTAMPTZ(arrayOfByte);
/*     */     }
/*     */ 
/* 387 */     return localTIMESTAMPTZ;
/*     */   }
/*     */ 
/*     */   static int setHighOrderbits(int paramInt)
/*     */   {
/* 407 */     return (paramInt & 0x1FC0) >> 6;
/*     */   }
/*     */ 
/*     */   static int setLowOrderbits(int paramInt)
/*     */   {
/* 412 */     return (paramInt & 0x3F) << 2;
/*     */   }
/*     */ 
/*     */   static int getHighOrderbits(int paramInt)
/*     */   {
/* 417 */     return (paramInt & 0x7F) << 6;
/*     */   }
/*     */ 
/*     */   static int getLowOrderbits(int paramInt)
/*     */   {
/* 422 */     return (paramInt & 0xFC) >> 2;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TimestamptzAccessor
 * JD-Core Version:    0.6.0
 */