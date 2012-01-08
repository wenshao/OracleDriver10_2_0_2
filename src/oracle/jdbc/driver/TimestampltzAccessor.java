/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.sql.SQLException;
/*     */
import java.sql.Time;
/*     */
import java.sql.Timestamp;
/*     */
import java.util.Calendar;
/*     */
import java.util.Map;
/*     */
import java.util.TimeZone;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.OffsetDST;
/*     */
import oracle.sql.TIMESTAMPLTZ;
/*     */
import oracle.sql.TIMEZONETAB;
/*     */
import oracle.sql.ZONEIDMAP;

/*     */
/*     */class TimestampltzAccessor extends DateTimeCommonAccessor
/*     */{
	/* 489 */static int INV_ZONEID = -1;

	/*     */
	/*     */TimestampltzAccessor(OracleStatement paramOracleStatement,
			int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
	/*     */throws SQLException
	/*     */{
		/* 33 */init(paramOracleStatement, 231, 231, paramShort, paramBoolean);
		/* 34 */initForDataAccess(paramInt2, paramInt1, null);
		/*     */}

	/*     */
	/*     */TimestampltzAccessor(OracleStatement paramOracleStatement,
			int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5, int paramInt6, short paramShort)
	/*     */throws SQLException
	/*     */{
		/* 41 */init(paramOracleStatement, 231, 231, paramShort, false);
		/* 42 */initForDescribe(231, paramInt1, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
		/*     */
		/* 44 */initForDataAccess(0, paramInt1, null);
		/*     */}

	/*     */
	/*     */void initForDataAccess(int paramInt1, int paramInt2, String paramString)
	/*     */throws SQLException
	/*     */{
		/* 50 */if (paramInt1 != 0) {
			/* 51 */this.externalType = paramInt1;
			/*     */}
		/* 53 */this.internalTypeMaxLength = 11;
		/*     */
		/* 55 */if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
			/* 56 */this.internalTypeMaxLength = paramInt2;
			/*     */}
		/* 58 */this.byteLength = this.internalTypeMaxLength;
		/*     */}

	/*     */
	/*     */String getString(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 64 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 69 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 76 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 77 */return null;
			/*     */}
		/*     */
		/* 80 */Calendar localCalendar1 = this.statement.connection
				.getDbTzCalendar();
		/*     */
		/* 84 */String str = this.statement.connection.getSessionTimeZone();
		/*     */
		/* 86 */if (str == null)
		/*     */{
			/* 88 */throw new SQLException("Session Time Zone not set!");
			/*     */}
		/*     */
		/* 91 */TimeZone localTimeZone = this.statement.getDefaultTimeZone();
		/*     */
		/* 93 */localTimeZone.setID(str);
		/*     */
		/* 95 */Calendar localCalendar2 = Calendar.getInstance(localTimeZone);
		/*     */
		/* 97 */int i = this.columnIndex + this.byteLength * paramInt;
		/* 98 */int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
		/*     */
		/* 100 */int k = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100
				+ (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
		/*     */
		/* 103 */localCalendar1.set(1, k);
		/* 104 */localCalendar1.set(2, oracleMonth(i));
		/* 105 */localCalendar1.set(5, oracleDay(i));
		/* 106 */localCalendar1.set(11, oracleHour(i));
		/* 107 */localCalendar1.set(12, oracleMin(i));
		/* 108 */localCalendar1.set(13, oracleSec(i));
		/* 109 */localCalendar1.set(14, 0);
		/*     */
		/* 112 */TimeZoneAdjust(localCalendar1, localCalendar2);
		/*     */
		/* 115 */k = localCalendar2.get(1);
		/*     */
		/* 117 */int m = localCalendar2.get(2) + 1;
		/* 118 */int n = localCalendar2.get(5);
		/* 119 */int i1 = localCalendar2.get(11);
		/* 120 */int i2 = localCalendar2.get(12);
		/* 121 */int i3 = localCalendar2.get(13);
		/* 122 */int i4 = 0;
		/*     */
		/* 124 */if (j == 11)
		/*     */{
			/* 126 */i4 = oracleNanos(i);
			/*     */}
		/*     */
		/* 129 */return k + "-" + m + "-" + n + " " + i1 + "." + i2 + "." + i3
				+ "." + i4;
		/*     */}

	/*     */
	/*     */java.sql.Date getDate(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 136 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 141 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 148 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 149 */return null;
			/*     */}
		/*     */
		/* 152 */Calendar localCalendar1 = this.statement.connection
				.getDbTzCalendar();
		/*     */
		/* 156 */String str = this.statement.connection.getSessionTimeZone();
		/*     */
		/* 158 */if (str == null)
		/*     */{
			/* 160 */throw new SQLException("Session Time Zone not set!");
			/*     */}
		/*     */
		/* 163 */TimeZone localTimeZone = this.statement.getDefaultTimeZone();
		/*     */
		/* 165 */localTimeZone.setID(str);
		/*     */
		/* 167 */Calendar localCalendar2 = Calendar.getInstance(localTimeZone);
		/*     */
		/* 169 */int i = this.columnIndex + this.byteLength * paramInt;
		/* 170 */int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100
				+ (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
		/*     */
		/* 173 */localCalendar1.set(1, j);
		/* 174 */localCalendar1.set(2, oracleMonth(i));
		/* 175 */localCalendar1.set(5, oracleDay(i));
		/* 176 */localCalendar1.set(11, oracleHour(i));
		/* 177 */localCalendar1.set(12, oracleMin(i));
		/* 178 */localCalendar1.set(13, oracleSec(i));
		/* 179 */localCalendar1.set(14, 0);
		/*     */
		/* 182 */TimeZoneAdjust(localCalendar1, localCalendar2);
		/*     */
		/* 184 */long l = localCalendar2.getTime().getTime();
		/*     */
		/* 186 */return new java.sql.Date(l);
		/*     */}

	/*     */
	/*     */Time getTime(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 192 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 197 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 203 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 204 */return null;
			/*     */}
		/*     */
		/* 207 */Calendar localCalendar1 = this.statement.connection
				.getDbTzCalendar();
		/*     */
		/* 211 */String str = this.statement.connection.getSessionTimeZone();
		/*     */
		/* 213 */if (str == null)
		/*     */{
			/* 215 */throw new SQLException("Session Time Zone not set!");
			/*     */}
		/*     */
		/* 218 */TimeZone localTimeZone = this.statement.getDefaultTimeZone();
		/*     */
		/* 220 */localTimeZone.setID(str);
		/*     */
		/* 222 */Calendar localCalendar2 = Calendar.getInstance(localTimeZone);
		/*     */
		/* 224 */int i = this.columnIndex + this.byteLength * paramInt;
		/* 225 */int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100
				+ (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
		/*     */
		/* 228 */localCalendar1.set(1, j);
		/* 229 */localCalendar1.set(2, oracleMonth(i));
		/* 230 */localCalendar1.set(5, oracleDay(i));
		/* 231 */localCalendar1.set(11, oracleHour(i));
		/* 232 */localCalendar1.set(12, oracleMin(i));
		/* 233 */localCalendar1.set(13, oracleSec(i));
		/* 234 */localCalendar1.set(14, 0);
		/*     */
		/* 237 */TimeZoneAdjust(localCalendar1, localCalendar2);
		/*     */
		/* 239 */long l = localCalendar2.getTime().getTime();
		/*     */
		/* 241 */return new Time(l);
		/*     */}

	/*     */
	/*     */Timestamp getTimestamp(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 247 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 252 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 259 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 260 */return null;
			/*     */}
		/*     */
		/* 263 */Calendar localCalendar1 = this.statement.connection
				.getDbTzCalendar();
		/*     */
		/* 267 */String str = this.statement.connection.getSessionTimeZone();
		/*     */
		/* 269 */if (str == null)
		/*     */{
			/* 271 */throw new SQLException("Session Time Zone not set!");
			/*     */}
		/*     */
		/* 274 */TimeZone localTimeZone = this.statement.getDefaultTimeZone();
		/*     */
		/* 276 */localTimeZone.setID(str);
		/*     */
		/* 278 */Calendar localCalendar2 = Calendar.getInstance(localTimeZone);
		/*     */
		/* 280 */int i = this.columnIndex + this.byteLength * paramInt;
		/* 281 */int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
		/*     */
		/* 283 */int k = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100
				+ (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
		/*     */
		/* 286 */localCalendar1.set(1, k);
		/* 287 */localCalendar1.set(2, oracleMonth(i));
		/* 288 */localCalendar1.set(5, oracleDay(i));
		/* 289 */localCalendar1.set(11, oracleHour(i));
		/* 290 */localCalendar1.set(12, oracleMin(i));
		/* 291 */localCalendar1.set(13, oracleSec(i));
		/* 292 */localCalendar1.set(14, 0);
		/*     */
		/* 295 */TimeZoneAdjust(localCalendar1, localCalendar2);
		/*     */
		/* 297 */long l = localCalendar2.getTime().getTime();
		/* 298 */Timestamp localTimestamp = new Timestamp(l);
		/*     */
		/* 300 */if (j == 11)
		/*     */{
			/* 302 */localTimestamp.setNanos(oracleNanos(i));
			/*     */}
		/*     */
		/* 305 */return localTimestamp;
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 311 */return getTIMESTAMPLTZ(paramInt);
		/*     */}

	/*     */
	/*     */Datum getOracleObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 317 */return getTIMESTAMPLTZ(paramInt);
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 323 */return getTIMESTAMPLTZ(paramInt);
		/*     */}

	/*     */
	/*     */TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 329 */TIMESTAMPLTZ localTIMESTAMPLTZ = null;
		/*     */
		/* 331 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 336 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 343 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 345 */int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
			/* 346 */int j = this.columnIndex + this.byteLength * paramInt;
			/* 347 */byte[] arrayOfByte = new byte[i];
			/*     */
			/* 349 */System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
			/*     */
			/* 351 */localTIMESTAMPLTZ = new TIMESTAMPLTZ(arrayOfByte);
			/*     */}
		/*     */
		/* 354 */return localTIMESTAMPLTZ;
		/*     */}

	/*     */
	/*     */void TimeZoneAdjust(Calendar paramCalendar1, Calendar paramCalendar2)
	/*     */throws SQLException
	/*     */{
		/* 365 */String str1 = paramCalendar1.getTimeZone().getID();
		/* 366 */String str2 = paramCalendar2.getTimeZone().getID();
		/*     */
		/* 369 */if (!str2.equals(str1))
		/*     */{
			/* 371 */OffsetDST localOffsetDST = new OffsetDST();
			/*     */
			/* 374 */k = getZoneOffset(paramCalendar1, localOffsetDST);
			/*     */
			/* 376 */m = localOffsetDST.getOFFSET();
			/*     */
			/* 379 */paramCalendar1.add(11, -(m / 3600000));
			/* 380 */paramCalendar1.add(12, -(m % 3600000) / 60000);
			/*     */int i;
			/* 382 */if ((str2.equals("Custom"))
					|| ((str2.startsWith("GMT")) && (str2.length() > 3)))
			/*     */{
				/* 386 */i = paramCalendar2.getTimeZone().getRawOffset();
				/*     */}
			/*     */else
			/*     */{
				/* 391 */n = ZONEIDMAP.getID(str2);
				/*     */
				/* 393 */if (n == INV_ZONEID) {
					/* 394 */throw new SQLException("Timezone not supported");
					/*     */}
				/* 396 */if (TIMEZONETAB.checkID(n)) {
					/* 397 */TIMEZONETAB.updateTable(this.statement.connection,
							n);
					/*     */}
				/*     */
				/* 401 */i = TIMEZONETAB.getOffset(paramCalendar1, n);
				/*     */}
			/*     */
			/* 405 */paramCalendar1.add(11, i / 3600000);
			/* 406 */paramCalendar1.add(12, i % 3600000 / 60000);
			/*     */}
		/*     */
		/* 410 */if (((str2.equals("Custom")) && (str1.equals("Custom")))
				|| ((str2.startsWith("GMT")) && (str2.length() > 3)
						&& (str1.startsWith("GMT")) && (str1.length() > 3)))
		/*     */{
			/* 415 */j = paramCalendar1.getTimeZone().getRawOffset();
			/* 416 */k = paramCalendar2.getTimeZone().getRawOffset();
			/* 417 */m = 0;
			/*     */
			/* 420 */if (j != k)
			/*     */{
				/* 423 */m = j - k;
				/* 424 */m = m > 0 ? m : -m;
				/*     */}
			/*     */
			/* 427 */if (j > k) {
				/* 428 */m = -m;
				/*     */}
			/* 430 */paramCalendar1.add(11, m / 3600000);
			/* 431 */paramCalendar1.add(12, m % 3600000 / 60000);
			/*     */}
		/*     */
		/* 435 */int j = paramCalendar1.get(1);
		/* 436 */int k = paramCalendar1.get(2);
		/* 437 */int m = paramCalendar1.get(5);
		/* 438 */int n = paramCalendar1.get(11);
		/* 439 */int i1 = paramCalendar1.get(12);
		/* 440 */int i2 = paramCalendar1.get(13);
		/* 441 */int i3 = paramCalendar1.get(14);
		/*     */
		/* 444 */paramCalendar2.set(1, j);
		/* 445 */paramCalendar2.set(2, k);
		/* 446 */paramCalendar2.set(5, m);
		/* 447 */paramCalendar2.set(11, n);
		/* 448 */paramCalendar2.set(12, i1);
		/* 449 */paramCalendar2.set(13, i2);
		/* 450 */paramCalendar2.set(14, i3);
		/*     */}

	/*     */
	/*     */byte getZoneOffset(Calendar paramCalendar, OffsetDST paramOffsetDST)
	/*     */throws SQLException
	/*     */{
		/* 456 */int i = 0;
		/*     */
		/* 459 */String str = paramCalendar.getTimeZone().getID();
		/*     */
		/* 462 */if ((str == "Custom")
				|| ((str.startsWith("GMT")) && (str.length() > 3)))
		/*     */{
			/* 467 */paramOffsetDST.setOFFSET(paramCalendar.getTimeZone()
					.getRawOffset());
			/*     */}
		/*     */else
		/*     */{
			/* 473 */int j = ZONEIDMAP.getID(str);
			/*     */
			/* 475 */if (j == INV_ZONEID) {
				/* 476 */throw new SQLException("Timezone not supported");
				/*     */}
			/* 478 */if (TIMEZONETAB.checkID(j)) {
				/* 479 */TIMEZONETAB.updateTable(this.statement.connection, j);
				/*     */}
			/*     */
			/* 483 */i = TIMEZONETAB.getLocalOffset(paramCalendar, j,
					paramOffsetDST);
			/*     */}
		/*     */
		/* 486 */return i;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.TimestampltzAccessor JD-Core Version: 0.6.0
 */