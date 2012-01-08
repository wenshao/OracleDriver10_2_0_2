/*      */package oracle.sql;

/*      */
/*      */import B;
/*      */
import java.sql.Connection;
/*      */
import java.sql.SQLException;
/*      */
import java.sql.Time;
/*      */
import java.sql.Timestamp;
/*      */
import java.text.ParseException;
/*      */
import java.text.SimpleDateFormat;
/*      */
import java.util.Calendar;
/*      */
import java.util.SimpleTimeZone;
/*      */
import java.util.TimeZone;
/*      */
import oracle.jdbc.OracleConnection;

/*      */
/*      */public class TIMESTAMPTZ extends Datum
/*      */{
	/* 1738 */private static int CENTURY_DEFAULT = 119;
	/* 1739 */private static int DECADE_DEFAULT = 100;
	/* 1740 */private static int MONTH_DEFAULT = 1;
	/* 1741 */private static int DAY_DEFAULT = 1;
	/*      */
	/* 1744 */private static int DECADE_INIT = 170;
	/*      */
	/* 1747 */private static int HOUR_MILLISECOND = 3600000;
	/*      */
	/* 1750 */private static int MINUTE_MILLISECOND = 60000;
	/*      */
	/* 1754 */private static int JAVA_YEAR = 1970;
	/* 1755 */private static int JAVA_MONTH = 0;
	/* 1756 */private static int JAVA_DATE = 1;
	/*      */
	/* 1760 */private static int SIZE_TIMESTAMPTZ = 13;
	/*      */
	/* 1764 */private static int SIZE_TIMESTAMP = 11;
	/*      */
	/* 1768 */private static int OFFSET_HOUR = 20;
	/* 1769 */private static int OFFSET_MINUTE = 60;
	/*      */
	/* 1794 */private static byte REGIONIDBIT = -128;

	/*      */
	/*      */public TIMESTAMPTZ()
	/*      */{
		/* 76 */super(initTimestamptz());
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(byte[] paramArrayOfByte)
	/*      */{
		/* 91 */super(paramArrayOfByte);
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, java.sql.Date paramDate)
	/*      */throws SQLException
	/*      */{
		/* 109 */super(toBytes(paramConnection, paramDate));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection,
			java.sql.Date paramDate, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 129 */super(toBytes(paramConnection, paramDate, paramCalendar));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, Time paramTime)
	/*      */throws SQLException
	/*      */{
		/* 146 */super(toBytes(paramConnection, paramTime));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, Time paramTime,
			Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 166 */super(toBytes(paramConnection, paramTime, paramCalendar));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, Timestamp paramTimestamp)
	/*      */throws SQLException
	/*      */{
		/* 184 */super(toBytes(paramConnection, paramTimestamp));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection,
			Timestamp paramTimestamp, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 203 */super(toBytes(paramConnection, paramTimestamp, paramCalendar));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, DATE paramDATE)
	/*      */throws SQLException
	/*      */{
		/* 221 */super(toBytes(paramConnection, paramDATE));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, String paramString)
	/*      */throws SQLException
	/*      */{
		/* 239 */super(toBytes(paramConnection, paramString));
		/*      */}

	/*      */
	/*      */public TIMESTAMPTZ(Connection paramConnection, String paramString,
			Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 256 */super(toBytes(paramConnection, paramString, paramCalendar));
		/*      */}

	/*      */
	/*      */public static java.sql.Date toDate(Connection paramConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 286 */int[] arrayOfInt = new int[SIZE_TIMESTAMPTZ];
		/*      */
		/* 289 */for (int j = 0; j < SIZE_TIMESTAMPTZ; j++) {
			/* 290 */paramArrayOfByte[j] &= 255;
			/*      */}
		/*      */
		/* 294 */j = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
		/*      */
		/* 299 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 301 */localCalendar.set(1, j);
		/* 302 */localCalendar.set(2, arrayOfInt[2] - 1);
		/* 303 */localCalendar.set(5, arrayOfInt[3]);
		/* 304 */localCalendar.set(11, arrayOfInt[4] - 1);
		/* 305 */localCalendar.set(12, arrayOfInt[5] - 1);
		/* 306 */localCalendar.set(13, arrayOfInt[6] - 1);
		/* 307 */localCalendar.set(14, 0);
		/*      */
		/* 311 */if ((arrayOfInt[11] & REGIONIDBIT) != 0)
		/*      */{
			/* 318 */int k = getHighOrderbits(arrayOfInt[11]);
			/* 319 */k += getLowOrderbits(arrayOfInt[12]);
			/*      */
			/* 323 */if (TIMEZONETAB.checkID(k)) {
				/* 324 */TIMEZONETAB.updateTable(paramConnection, k);
				/*      */}
			/*      */
			/* 328 */int i = TIMEZONETAB.getOffset(localCalendar, k);
			/*      */
			/* 332 */localCalendar.add(10, i / HOUR_MILLISECOND);
			/* 333 */localCalendar.add(12, i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND);
			/*      */}
		/*      */else
		/*      */{
			/* 339 */localCalendar.add(10, arrayOfInt[11] - OFFSET_HOUR);
			/* 340 */localCalendar.add(12, arrayOfInt[12] - OFFSET_MINUTE);
			/*      */}
		/*      */
		/* 345 */long l = localCalendar.getTime().getTime();
		/*      */
		/* 349 */return new java.sql.Date(l);
		/*      */}

	/*      */
	/*      */public static Time toTime(Connection paramConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 369 */int[] arrayOfInt = new int[SIZE_TIMESTAMPTZ];
		/*      */
		/* 373 */for (int j = 0; j < SIZE_TIMESTAMPTZ; j++) {
			/* 374 */paramArrayOfByte[j] &= 255;
			/*      */}
		/*      */
		/* 377 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 382 */int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
		/*      */
		/* 384 */localCalendar.set(1, k);
		/* 385 */localCalendar.set(2, arrayOfInt[2] - 1);
		/* 386 */localCalendar.set(5, arrayOfInt[3]);
		/* 387 */localCalendar.set(11, arrayOfInt[4] - 1);
		/* 388 */localCalendar.set(12, arrayOfInt[5] - 1);
		/* 389 */localCalendar.set(13, arrayOfInt[6] - 1);
		/* 390 */localCalendar.set(14, 0);
		/*      */
		/* 393 */if ((arrayOfInt[11] & REGIONIDBIT) != 0)
		/*      */{
			/* 396 */int m = getHighOrderbits(arrayOfInt[11]);
			/* 397 */m += getLowOrderbits(arrayOfInt[12]);
			/*      */
			/* 402 */if (TIMEZONETAB.checkID(m)) {
				/* 403 */TIMEZONETAB.updateTable(paramConnection, m);
				/*      */}
			/*      */
			/* 406 */int i = TIMEZONETAB.getOffset(localCalendar, m);
			/*      */
			/* 411 */localCalendar.add(10, i / HOUR_MILLISECOND);
			/* 412 */localCalendar.add(12, i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND);
			/*      */}
		/*      */else
		/*      */{
			/* 418 */localCalendar.add(10, arrayOfInt[11] - OFFSET_HOUR);
			/* 419 */localCalendar.add(12, arrayOfInt[12] - OFFSET_MINUTE);
			/*      */}
		/*      */
		/* 426 */return new Time(localCalendar.get(11), localCalendar.get(12),
				localCalendar.get(13));
		/*      */}

	/*      */
	/*      */public static DATE toDATE(Connection paramConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 446 */int[] arrayOfInt = new int[SIZE_TIMESTAMPTZ];
		/*      */
		/* 449 */for (int j = 0; j < SIZE_TIMESTAMPTZ; j++) {
			/* 450 */paramArrayOfByte[j] &= 255;
			/*      */}
		/*      */
		/* 453 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 456 */int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
		/*      */
		/* 458 */localCalendar.set(1, k);
		/* 459 */localCalendar.set(2, arrayOfInt[2] - 1);
		/* 460 */localCalendar.set(5, arrayOfInt[3]);
		/* 461 */localCalendar.set(11, arrayOfInt[4] - 1);
		/* 462 */localCalendar.set(12, arrayOfInt[5] - 1);
		/* 463 */localCalendar.set(13, arrayOfInt[6] - 1);
		/* 464 */localCalendar.set(14, 0);
		/*      */
		/* 466 */if ((arrayOfInt[11] & REGIONIDBIT) != 0)
		/*      */{
			/* 469 */int m = getHighOrderbits(arrayOfInt[11]);
			/* 470 */m += getLowOrderbits(arrayOfInt[12]);
			/*      */
			/* 474 */if (TIMEZONETAB.checkID(m)) {
				/* 475 */TIMEZONETAB.updateTable(paramConnection, m);
				/*      */}
			/* 477 */int i = TIMEZONETAB.getOffset(localCalendar, m);
			/*      */
			/* 481 */localCalendar.add(10, i / HOUR_MILLISECOND);
			/* 482 */localCalendar.add(12, i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND);
			/*      */}
		/*      */else
		/*      */{
			/* 488 */localCalendar.add(10, arrayOfInt[11] - OFFSET_HOUR);
			/* 489 */localCalendar.add(12, arrayOfInt[12] - OFFSET_MINUTE);
			/*      */}
		/*      */
		/* 494 */long l = localCalendar.getTime().getTime();
		/*      */
		/* 498 */return new DATE(new java.sql.Date(l));
		/*      */}

	/*      */
	/*      */public static Timestamp toTimestamp(Connection paramConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 516 */int[] arrayOfInt = new int[SIZE_TIMESTAMPTZ];
		/*      */
		/* 518 */for (int j = 0; j < SIZE_TIMESTAMPTZ; j++) {
			/* 519 */paramArrayOfByte[j] &= 255;
			/*      */}
		/*      */
		/* 525 */Calendar localCalendar1 = Calendar.getInstance();
		/*      */
		/* 530 */Calendar localCalendar2 = Calendar.getInstance();
		/*      */
		/* 535 */int k = getJavaYear(arrayOfInt[0], arrayOfInt[1]);
		/*      */
		/* 537 */localCalendar1.set(1, k);
		/* 538 */localCalendar1.set(2, arrayOfInt[2] - 1);
		/* 539 */localCalendar1.set(5, arrayOfInt[3]);
		/* 540 */localCalendar1.set(11, arrayOfInt[4] - 1);
		/* 541 */localCalendar1.set(12, arrayOfInt[5] - 1);
		/* 542 */localCalendar1.set(13, arrayOfInt[6] - 1);
		/* 543 */localCalendar1.set(14, 0);
		/*      */
		/* 545 */long l = localCalendar1.getTime().getTime();
		/*      */
		/* 547 */if ((arrayOfInt[11] & REGIONIDBIT) != 0)
		/*      */{
			/* 550 */int m = getHighOrderbits(arrayOfInt[11]);
			/* 551 */m += getLowOrderbits(arrayOfInt[12]);
			/*      */
			/* 555 */if (TIMEZONETAB.checkID(m)) {
				/* 556 */TIMEZONETAB.updateTable(paramConnection, m);
				/*      */}
			/* 558 */int i = TIMEZONETAB.getOffset(localCalendar1, m);
			/*      */
			/* 562 */l += i;
			/*      */TimeZone localTimeZone;
			/* 569 */if ((!localCalendar1.getTimeZone().inDaylightTime(
					localCalendar1.getTime()))
					&& (localCalendar2.getTimeZone().inDaylightTime(
							new Timestamp(l)) == true))
			/*      */{
				/* 573 */localTimeZone = localCalendar2.getTimeZone();
				/* 574 */if ((localTimeZone instanceof SimpleTimeZone))
					/* 575 */l -= ((SimpleTimeZone) localTimeZone)
							.getDSTSavings();
				/*      */else {
					/* 577 */l -= 3600000L;
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 587 */if ((localCalendar1.getTimeZone().inDaylightTime(
					localCalendar1.getTime()) == true)
					&& (!localCalendar2.getTimeZone().inDaylightTime(
							new Timestamp(l))))
			/*      */{
				/* 591 */localTimeZone = localCalendar2.getTimeZone();
				/* 592 */if ((localTimeZone instanceof SimpleTimeZone))
					/* 593 */l += ((SimpleTimeZone) localTimeZone)
							.getDSTSavings();
				/*      */else
					/* 595 */l += 3600000L;
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 600 */localCalendar1.add(10, arrayOfInt[11] - OFFSET_HOUR);
			/* 601 */localCalendar1.add(12, arrayOfInt[12] - OFFSET_MINUTE);
			/*      */
			/* 603 */l = localCalendar1.getTime().getTime();
			/*      */}
		/*      */
		/* 607 */Timestamp localTimestamp = new Timestamp(l);
		/*      */
		/* 611 */int n = arrayOfInt[7] << 24;
		/* 612 */n |= arrayOfInt[8] << 16;
		/* 613 */n |= arrayOfInt[9] << 8;
		/* 614 */n |= arrayOfInt[10] & 0xFF;
		/*      */
		/* 618 */localTimestamp.setNanos(n);
		/*      */
		/* 620 */return localTimestamp;
		/*      */}

	/*      */
	/*      */public static String toString(Connection paramConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 638 */Timestamp localTimestamp = toTimestamp(paramConnection,
				paramArrayOfByte);
		/*      */
		/* 640 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 642 */localCalendar.setTime(localTimestamp);
		/*      */
		/* 646 */int i = localCalendar.get(1);
		/* 647 */int j = localCalendar.get(2) + 1;
		/* 648 */int k = localCalendar.get(5);
		/* 649 */int m = localCalendar.get(11);
		/* 650 */int n = localCalendar.get(12);
		/* 651 */int i1 = localCalendar.get(13);
		/* 652 */int i2 = 0;
		/* 653 */i2 = (paramArrayOfByte[7] & 0xFF) << 24;
		/* 654 */i2 |= (paramArrayOfByte[8] & 0xFF) << 16;
		/* 655 */i2 |= (paramArrayOfByte[9] & 0xFF) << 8;
		/* 656 */i2 |= paramArrayOfByte[10] & 0xFF & 0xFF;
		/*      */int i3;
		/*      */String str;
		/* 658 */if ((paramArrayOfByte[11] & REGIONIDBIT) != 0)
		/*      */{
			/* 661 */i3 = getHighOrderbits(paramArrayOfByte[11]);
			/* 662 */i3 += getLowOrderbits(paramArrayOfByte[12]);
			/* 663 */str = new String(ZONEIDMAP.getRegion(i3));
			/*      */}
		/*      */else
		/*      */{
			/* 667 */i3 = paramArrayOfByte[11] - OFFSET_HOUR;
			/* 668 */int i4 = paramArrayOfByte[12] - OFFSET_MINUTE;
			/* 669 */str = new String(i3 + ":" + i4);
			/*      */}
		/*      */
		/* 672 */return i + "-" + j + "-" + k + " " + m + "." + n + "." + i1
				+ "." + i2 + " " + str;
		/*      */}

	/*      */
	/*      */public Timestamp timestampValue(Connection paramConnection)
	/*      */throws SQLException
	/*      */{
		/* 688 */return toTimestamp(paramConnection, getBytes());
		/*      */}

	/*      */
	/*      */public byte[] toBytes()
	/*      */{
		/* 700 */return getBytes();
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			java.sql.Date paramDate)
	/*      */throws SQLException
	/*      */{
		/* 718 */if (paramDate == null) {
			/* 719 */return null;
			/*      */}
		/* 721 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 727 */String str1 = ((OracleConnection) paramConnection)
				.getSessionTimeZone();
		/*      */Calendar localCalendar;
		/* 729 */if (str1 == null)
			/* 730 */localCalendar = Calendar.getInstance();
		/*      */else {
			/* 732 */localCalendar = Calendar.getInstance(TimeZone
					.getTimeZone(str1));
			/*      */}
		/* 734 */localCalendar.setTime(paramDate);
		/*      */int j;
		/* 739 */if (localCalendar.getTimeZone().inDaylightTime(paramDate))
			/* 740 */j = 1;
		/*      */else {
			/* 742 */j = 0;
			/*      */}
		/* 744 */localCalendar.set(11, 0);
		/* 745 */localCalendar.set(12, 0);
		/* 746 */localCalendar.set(13, 0);
		/*      */int i;
		/* 748 */if (localCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 750 */i = localCalendar.getTimeZone().getRawOffset();
			/* 751 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 752 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 759 */String str2 = new String(localCalendar.getTimeZone()
					.getID());
			/*      */
			/* 763 */int k = ZONEIDMAP.getID(str2);
			/*      */
			/* 765 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 767 */if (localCalendar.getTimeZone().useDaylightTime()) {
					/* 768 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 771 */i = localCalendar.getTimeZone().getRawOffset();
				/* 772 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 773 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 779 */if (TIMEZONETAB.checkID(k)) {
					/* 780 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 783 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 787 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/* 788 */i = localOffsetDST.getOFFSET();
				/*      */
				/* 790 */if ((j != 0) && (m == 1))
				/*      */{
					/* 792 */if (localOffsetDST.getDSTFLAG() == 0)
						/* 793 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 795 */throw new SQLException();
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 800 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str2));
				/*      */byte[] tmp343_340 = arrayOfByte;
				tmp343_340[11] = (byte) (tmp343_340[11] | REGIONIDBIT);
				/* 802 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str2));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 807 */localCalendar.add(10, -(i / HOUR_MILLISECOND));
		/* 808 */localCalendar.add(12, -(i % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 810 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 811 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 812 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 813 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 814 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 815 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 816 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/* 817 */arrayOfByte[7] = 0;
		/* 818 */arrayOfByte[8] = 0;
		/* 819 */arrayOfByte[9] = 0;
		/* 820 */arrayOfByte[10] = 0;
		/*      */
		/* 825 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			java.sql.Date paramDate, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 841 */if (paramDate == null) {
			/* 842 */return null;
			/*      */}
		/* 844 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 847 */Calendar localCalendar = Calendar.getInstance();
		/* 848 */localCalendar.setTime(paramDate);
		/*      */int j;
		/* 852 */if (paramCalendar.getTimeZone().inDaylightTime(paramDate))
			/* 853 */j = 1;
		/*      */else {
			/* 855 */j = 0;
			/*      */}
		/* 857 */localCalendar.set(11, 0);
		/* 858 */localCalendar.set(12, 0);
		/* 859 */localCalendar.set(13, 0);
		/*      */int i;
		/* 862 */if (paramCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 864 */i = paramCalendar.getTimeZone().getRawOffset();
			/* 865 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 866 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 874 */String str = new String(paramCalendar.getTimeZone()
					.getID());
			/*      */
			/* 878 */int k = ZONEIDMAP.getID(str);
			/*      */
			/* 880 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 882 */if (paramCalendar.getTimeZone().useDaylightTime()) {
					/* 883 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 886 */i = paramCalendar.getTimeZone().getRawOffset();
				/* 887 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 888 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 894 */if (TIMEZONETAB.checkID(k)) {
					/* 895 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 898 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 902 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/* 903 */i = localOffsetDST.getOFFSET();
				/*      */
				/* 905 */if ((j != 0) && (m == 1))
				/*      */{
					/* 907 */if (localOffsetDST.getDSTFLAG() == 0)
						/* 908 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 910 */throw new SQLException();
						/*      */}
					/*      */}
				/*      */
				/* 914 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str));
				/*      */byte[] tmp315_311 = arrayOfByte;
				tmp315_311[11] = (byte) (tmp315_311[11] | REGIONIDBIT);
				/* 916 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 921 */localCalendar.add(10, -(i / HOUR_MILLISECOND));
		/* 922 */localCalendar.add(12, -(i % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 925 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 926 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 927 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 928 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 929 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 930 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 931 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/* 932 */arrayOfByte[7] = 0;
		/* 933 */arrayOfByte[8] = 0;
		/* 934 */arrayOfByte[9] = 0;
		/* 935 */arrayOfByte[10] = 0;
		/*      */
		/* 938 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection, Time paramTime)
	/*      */throws SQLException
	/*      */{
		/* 953 */if (paramTime == null) {
			/* 954 */return null;
			/*      */}
		/*      */
		/* 960 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 964 */String str1 = ((OracleConnection) paramConnection)
				.getSessionTimeZone();
		/*      */Calendar localCalendar;
		/* 966 */if (str1 == null)
			/* 967 */localCalendar = Calendar.getInstance();
		/*      */else
			/* 969 */localCalendar = Calendar.getInstance(TimeZone
					.getTimeZone(str1));
		/* 970 */localCalendar.setTime(paramTime);
		/*      */int i;
		/* 973 */if (localCalendar.getTimeZone().inDaylightTime(paramTime))
			/* 974 */i = 1;
		/*      */else {
			/* 976 */i = 0;
			/*      */}
		/* 978 */localCalendar.set(1, (CENTURY_DEFAULT - 100) * 100
				+ DECADE_DEFAULT % 100);
		/* 979 */localCalendar.set(2, MONTH_DEFAULT - 1);
		/* 980 */localCalendar.set(5, DAY_DEFAULT);
		/*      */int j;
		/* 983 */if (localCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 985 */j = localCalendar.getTimeZone().getRawOffset();
			/* 986 */arrayOfByte[11] = (byte) (j / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 987 */arrayOfByte[12] = (byte) (j % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 994 */String str2 = new String(localCalendar.getTimeZone()
					.getID());
			/*      */
			/* 998 */int k = ZONEIDMAP.getID(str2);
			/*      */
			/* 1000 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 1002 */if (localCalendar.getTimeZone().useDaylightTime()) {
					/* 1003 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 1006 */j = localCalendar.getTimeZone().getRawOffset();
				/* 1007 */arrayOfByte[11] = (byte) (j / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 1008 */arrayOfByte[12] = (byte) (j % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 1014 */if (TIMEZONETAB.checkID(k)) {
					/* 1015 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 1018 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 1022 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/*      */
				/* 1024 */j = localOffsetDST.getOFFSET();
				/*      */
				/* 1027 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str2));
				/*      */byte[] tmp324_320 = arrayOfByte;
				tmp324_320[11] = (byte) (tmp324_320[11] | REGIONIDBIT);
				/* 1029 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str2));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1034 */localCalendar.add(10, -(j / HOUR_MILLISECOND));
		/* 1035 */localCalendar.add(12, -(j % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 1037 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 1038 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 1039 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 1040 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 1041 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 1042 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 1043 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/* 1044 */arrayOfByte[7] = 0;
		/* 1045 */arrayOfByte[8] = 0;
		/* 1046 */arrayOfByte[9] = 0;
		/* 1047 */arrayOfByte[10] = 0;
		/*      */
		/* 1051 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			Time paramTime, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 1068 */if (paramTime == null) {
			/* 1069 */return null;
			/*      */}
		/*      */
		/* 1072 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 1076 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 1078 */localCalendar.setTime(paramTime);
		/*      */int j;
		/* 1081 */if (paramCalendar.getTimeZone().inDaylightTime(paramTime))
			/* 1082 */j = 1;
		/*      */else {
			/* 1084 */j = 0;
			/*      */}
		/*      */
		/* 1087 */localCalendar.set(1, (CENTURY_DEFAULT - 100) * 100
				+ DECADE_DEFAULT % 100);
		/* 1088 */localCalendar.set(2, MONTH_DEFAULT - 1);
		/* 1089 */localCalendar.set(5, DAY_DEFAULT);
		/*      */int i;
		/* 1093 */if (paramCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 1095 */i = paramCalendar.getTimeZone().getRawOffset();
			/* 1096 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 1097 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 1104 */String str = new String(paramCalendar.getTimeZone()
					.getID());
			/*      */
			/* 1108 */int k = ZONEIDMAP.getID(str);
			/*      */
			/* 1110 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 1112 */if (paramCalendar.getTimeZone().useDaylightTime()) {
					/* 1113 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 1116 */i = paramCalendar.getTimeZone().getRawOffset();
				/* 1117 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 1118 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 1125 */if (TIMEZONETAB.checkID(k)) {
					/* 1126 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 1129 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 1133 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/*      */
				/* 1135 */i = localOffsetDST.getOFFSET();
				/*      */
				/* 1138 */if ((j != 0) && (m == 1))
				/*      */{
					/* 1140 */if (localOffsetDST.getDSTFLAG() == 0)
						/* 1141 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 1143 */throw new SQLException();
						/*      */}
					/*      */}
				/* 1146 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str));
				/*      */byte[] tmp336_332 = arrayOfByte;
				tmp336_332[11] = (byte) (tmp336_332[11] | REGIONIDBIT);
				/* 1148 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1153 */localCalendar.add(11, -(i / HOUR_MILLISECOND));
		/* 1154 */localCalendar.add(12, -(i % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 1156 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 1157 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 1158 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 1159 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 1160 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 1161 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 1162 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/* 1163 */arrayOfByte[7] = 0;
		/* 1164 */arrayOfByte[8] = 0;
		/* 1165 */arrayOfByte[9] = 0;
		/* 1166 */arrayOfByte[10] = 0;
		/*      */
		/* 1168 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			Timestamp paramTimestamp)
	/*      */throws SQLException
	/*      */{
		/* 1183 */if (paramTimestamp == null) {
			/* 1184 */return null;
			/*      */}
		/* 1186 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 1193 */long l = 0L;
		/*      */
		/* 1197 */String str1 = ((OracleConnection) paramConnection)
				.getSessionTimeZone();
		/*      */Object localObject2;
		/*      */int n;
		/* 1199 */if (str1 == null)
		/*      */{
			/* 1203 */str1 = TimeZone.getDefault().getID();
			/*      */
			/* 1207 */l = paramTimestamp.getTime();
			/*      */}
		/*      */else
		/*      */{
			/* 1214 */int k = paramTimestamp.getNanos();
			/*      */
			/* 1216 */Calendar localCalendar2 = Calendar.getInstance();
			/*      */
			/* 1219 */localObject2 = TimeZone.getTimeZone(str1);
			/*      */
			/* 1221 */localCalendar2.setTimeZone((TimeZone) localObject2);
			/* 1222 */localCalendar2.setTime(paramTimestamp);
			/*      */
			/* 1224 */n = localCalendar2.get(1);
			/* 1225 */int i1 = localCalendar2.get(2) + 1;
			/* 1226 */int i2 = localCalendar2.get(5);
			/* 1227 */int i3 = localCalendar2.get(11);
			/* 1228 */int i4 = localCalendar2.get(12);
			/* 1229 */int i5 = localCalendar2.get(13);
			/* 1230 */double d = k / 1000000;
			/*      */
			/* 1232 */String str2 = new Integer(n).toString() + "/"
					+ new Integer(i1).toString() + "/"
					+ new Integer(i2).toString() + " "
					+ new Integer(i3).toString() + ":"
					+ new Integer(i4).toString() + ":"
					+ new Integer(i5).toString() + ":"
					+ new Double(d).toString();
			/*      */
			/* 1242 */SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
					"y/M/d H:m:s:S");
			/*      */
			/* 1245 */java.util.Date localDate = null;
			/*      */try
			/*      */{
				/* 1253 */localDate = localSimpleDateFormat.parse(str2);
				/*      */}
			/*      */catch (ParseException localParseException)
			/*      */{
				/* 1257 */throw new SQLException(
						localParseException.getMessage());
				/*      */}
			/*      */
			/* 1262 */l = localDate.getTime();
			/*      */}
		/*      */
		/* 1267 */Calendar localCalendar1 = Calendar.getInstance(TimeZone
				.getTimeZone(str1));
		/*      */int j;
		/* 1270 */if (localCalendar1.getTimeZone().inDaylightTime(
				paramTimestamp))
			/* 1271 */j = 1;
		/*      */else {
			/* 1273 */j = 0;
			/*      */}
		/* 1275 */localCalendar1.setTime(paramTimestamp);
		/*      */int i;
		/* 1277 */if (localCalendar1.getTimeZone().getID() == "Custom")
		/*      */{
			/* 1279 */i = localCalendar1.getTimeZone().getRawOffset();
			/* 1280 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 1281 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 1288 */localObject1 = new String(localCalendar1.getTimeZone()
					.getID());
			/*      */
			/* 1292 */int m = ZONEIDMAP.getID((String) localObject1);
			/*      */
			/* 1294 */if (m == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 1296 */if (localCalendar1.getTimeZone().useDaylightTime()) {
					/* 1297 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 1300 */i = localCalendar1.getTimeZone().getRawOffset();
				/* 1301 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 1302 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 1308 */if (TIMEZONETAB.checkID(m)) {
					/* 1309 */TIMEZONETAB.updateTable(paramConnection, m);
					/*      */}
				/*      */
				/* 1312 */localObject2 = new OffsetDST();
				/*      */
				/* 1316 */n = TIMEZONETAB.getLocalOffset(localCalendar1, m,
						(OffsetDST) localObject2);
				/*      */
				/* 1318 */i = ((OffsetDST) localObject2).getOFFSET();
				/*      */
				/* 1320 */if ((j != 0) && (n == 1))
				/*      */{
					/* 1322 */if (((OffsetDST) localObject2).getDSTFLAG() == 0)
						/* 1323 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 1325 */throw new SQLException();
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 1330 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID((String) localObject1));
				/*      */byte[] tmp618_615 = arrayOfByte;
				tmp618_615[11] = (byte) (tmp618_615[11] | REGIONIDBIT);
				/* 1332 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID((String) localObject1));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1340 */l -= i;
		/*      */
		/* 1342 */Object localObject1 = new Timestamp(l);
		/* 1343 */localCalendar1.setTime((java.util.Date) localObject1);
		/* 1344 */localCalendar1.setTimeZone(TimeZone.getDefault());
		/*      */
		/* 1346 */arrayOfByte[0] = (byte) (localCalendar1.get(1) / 100 + 100);
		/* 1347 */arrayOfByte[1] = (byte) (localCalendar1.get(1) % 100 + 100);
		/* 1348 */arrayOfByte[2] = (byte) (localCalendar1.get(2) + 1);
		/* 1349 */arrayOfByte[3] = (byte) localCalendar1.get(5);
		/* 1350 */arrayOfByte[4] = (byte) (localCalendar1.get(11) + 1);
		/* 1351 */arrayOfByte[5] = (byte) (localCalendar1.get(12) + 1);
		/* 1352 */arrayOfByte[6] = (byte) (localCalendar1.get(13) + 1);
		/*      */
		/* 1354 */arrayOfByte[7] = (byte) (paramTimestamp.getNanos() >> 24);
		/* 1355 */arrayOfByte[8] = (byte) (paramTimestamp.getNanos() >> 16 & 0xFF);
		/* 1356 */arrayOfByte[9] = (byte) (paramTimestamp.getNanos() >> 8 & 0xFF);
		/* 1357 */arrayOfByte[10] = (byte) (paramTimestamp.getNanos() & 0xFF);
		/*      */
		/* 1359 */return (B) (B) arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			Timestamp paramTimestamp, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 1374 */if (paramTimestamp == null) {
			/* 1375 */return null;
			/*      */}
		/* 1377 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/* 1378 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 1382 */localCalendar.setTime(paramTimestamp);
		/*      */int j;
		/* 1387 */if (paramCalendar.getTimeZone()
				.inDaylightTime(paramTimestamp))
			/* 1388 */j = 1;
		/*      */else
			/* 1390 */j = 0;
		/*      */int i;
		/* 1393 */if (paramCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 1395 */i = paramCalendar.getTimeZone().getRawOffset();
			/* 1396 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 1397 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 1404 */String str = new String(paramCalendar.getTimeZone()
					.getID());
			/*      */
			/* 1409 */int k = ZONEIDMAP.getID(str);
			/*      */
			/* 1411 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 1413 */if (paramCalendar.getTimeZone().useDaylightTime()) {
					/* 1414 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 1417 */i = paramCalendar.getTimeZone().getRawOffset();
				/* 1418 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 1419 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 1425 */if (TIMEZONETAB.checkID(k)) {
					/* 1426 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 1429 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 1433 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/*      */
				/* 1435 */i = localOffsetDST.getOFFSET();
				/*      */
				/* 1437 */if ((j != 0) && (m == 1))
				/*      */{
					/* 1439 */if (localOffsetDST.getDSTFLAG() == 0)
						/* 1440 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 1442 */throw new SQLException();
						/*      */}
					/*      */}
				/* 1445 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str));
				/*      */byte[] tmp293_290 = arrayOfByte;
				tmp293_290[11] = (byte) (tmp293_290[11] | REGIONIDBIT);
				/* 1447 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1454 */localCalendar.add(10, -(i / HOUR_MILLISECOND));
		/* 1455 */localCalendar.add(12, -(i % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 1458 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 1459 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 1460 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 1461 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 1462 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 1463 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 1464 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/*      */
		/* 1471 */arrayOfByte[7] = (byte) (paramTimestamp.getNanos() >> 24);
		/* 1472 */arrayOfByte[8] = (byte) (paramTimestamp.getNanos() >> 16 & 0xFF);
		/* 1473 */arrayOfByte[9] = (byte) (paramTimestamp.getNanos() >> 8 & 0xFF);
		/* 1474 */arrayOfByte[10] = (byte) (paramTimestamp.getNanos() & 0xFF);
		/*      */
		/* 1476 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection, DATE paramDATE)
	/*      */throws SQLException
	/*      */{
		/* 1492 */if (paramDATE == null) {
			/* 1493 */return null;
			/*      */}
		/* 1495 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/*      */
		/* 1499 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 1502 */localCalendar.setTime(DATE.toDate(paramDATE.toBytes()));
		/*      */int j;
		/* 1505 */if (localCalendar.getTimeZone().inDaylightTime(
				DATE.toDate(paramDATE.toBytes())))
			/* 1506 */j = 1;
		/*      */else
			/* 1508 */j = 0;
		/*      */int i;
		/* 1513 */if (localCalendar.getTimeZone().getID() == "Custom")
		/*      */{
			/* 1515 */i = localCalendar.getTimeZone().getRawOffset();
			/* 1516 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
			/* 1517 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
					/ MINUTE_MILLISECOND + OFFSET_MINUTE);
			/*      */}
		/*      */else
		/*      */{
			/* 1524 */String str = new String(localCalendar.getTimeZone()
					.getID());
			/*      */
			/* 1528 */int k = ZONEIDMAP.getID(str);
			/*      */
			/* 1530 */if (k == ZONEIDMAP.INV_ZONEID)
			/*      */{
				/* 1532 */if (localCalendar.getTimeZone().useDaylightTime()) {
					/* 1533 */throw new SQLException("Timezone not supported");
					/*      */}
				/*      */
				/* 1536 */i = localCalendar.getTimeZone().getRawOffset();
				/* 1537 */arrayOfByte[11] = (byte) (i / HOUR_MILLISECOND + OFFSET_HOUR);
				/* 1538 */arrayOfByte[12] = (byte) (i % HOUR_MILLISECOND
						/ MINUTE_MILLISECOND + OFFSET_MINUTE);
				/*      */}
			/*      */else
			/*      */{
				/* 1544 */if (TIMEZONETAB.checkID(k)) {
					/* 1545 */TIMEZONETAB.updateTable(paramConnection, k);
					/*      */}
				/*      */
				/* 1548 */OffsetDST localOffsetDST = new OffsetDST();
				/*      */
				/* 1552 */int m = TIMEZONETAB.getLocalOffset(localCalendar, k,
						localOffsetDST);
				/*      */
				/* 1554 */i = localOffsetDST.getOFFSET();
				/*      */
				/* 1556 */if ((j != 0) && (m == 1))
				/*      */{
					/* 1558 */if (localOffsetDST.getDSTFLAG() == 0)
						/* 1559 */i += HOUR_MILLISECOND;
					/*      */else {
						/* 1561 */throw new SQLException();
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 1566 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
						.getID(str));
				/*      */byte[] tmp302_299 = arrayOfByte;
				tmp302_299[11] = (byte) (tmp302_299[11] | REGIONIDBIT);
				/* 1568 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP
						.getID(str));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1573 */localCalendar.add(10, -(i / HOUR_MILLISECOND));
		/* 1574 */localCalendar.add(12, -(i % HOUR_MILLISECOND)
				/ MINUTE_MILLISECOND);
		/*      */
		/* 1576 */arrayOfByte[0] = (byte) (localCalendar.get(1) / 100 + 100);
		/* 1577 */arrayOfByte[1] = (byte) (localCalendar.get(1) % 100 + 100);
		/* 1578 */arrayOfByte[2] = (byte) (localCalendar.get(2) + 1);
		/* 1579 */arrayOfByte[3] = (byte) localCalendar.get(5);
		/* 1580 */arrayOfByte[4] = (byte) (localCalendar.get(11) + 1);
		/* 1581 */arrayOfByte[5] = (byte) (localCalendar.get(12) + 1);
		/* 1582 */arrayOfByte[6] = (byte) (localCalendar.get(13) + 1);
		/* 1583 */arrayOfByte[7] = 0;
		/* 1584 */arrayOfByte[8] = 0;
		/* 1585 */arrayOfByte[9] = 0;
		/* 1586 */arrayOfByte[10] = 0;
		/*      */
		/* 1588 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			String paramString)
	/*      */throws SQLException
	/*      */{
		/* 1601 */return toBytes(paramConnection,
				Timestamp.valueOf(paramString));
		/*      */}

	/*      */
	/*      */public static byte[] toBytes(Connection paramConnection,
			String paramString, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 1616 */return toBytes(paramConnection,
				Timestamp.valueOf(paramString), paramCalendar);
		/*      */}

	/*      */
	/*      */public String stringValue(Connection paramConnection)
	/*      */throws SQLException
	/*      */{
		/* 1628 */return toString(paramConnection, getBytes());
		/*      */}

	/*      */
	/*      */public java.sql.Date dateValue(Connection paramConnection)
	/*      */throws SQLException
	/*      */{
		/* 1641 */return toDate(paramConnection, getBytes());
		/*      */}

	/*      */
	/*      */public Time timeValue(Connection paramConnection)
	/*      */throws SQLException
	/*      */{
		/* 1652 */return toTime(paramConnection, getBytes());
		/*      */}

	/*      */
	/*      */private static byte[] initTimestamptz()
	/*      */{
		/* 1665 */byte[] arrayOfByte = new byte[SIZE_TIMESTAMPTZ];
		/* 1666 */Calendar localCalendar = Calendar.getInstance();
		/*      */
		/* 1668 */arrayOfByte[0] = (byte) CENTURY_DEFAULT;
		/* 1669 */arrayOfByte[1] = (byte) DECADE_INIT;
		/* 1670 */arrayOfByte[2] = (byte) MONTH_DEFAULT;
		/* 1671 */arrayOfByte[3] = (byte) DAY_DEFAULT;
		/* 1672 */arrayOfByte[4] = 1;
		/* 1673 */arrayOfByte[5] = 1;
		/* 1674 */arrayOfByte[6] = 1;
		/* 1675 */arrayOfByte[7] = 0;
		/* 1676 */arrayOfByte[8] = 0;
		/* 1677 */arrayOfByte[9] = 0;
		/* 1678 */arrayOfByte[10] = 0;
		/*      */
		/* 1680 */String str = new String(localCalendar.getTimeZone().getID());
		/*      */
		/* 1683 */arrayOfByte[11] = (byte) setHighOrderbits(ZONEIDMAP
				.getID(str));
		/* 1684 */arrayOfByte[11] = (byte) (arrayOfByte[11] | REGIONIDBIT);
		/* 1685 */arrayOfByte[12] = (byte) setLowOrderbits(ZONEIDMAP.getID(str));
		/*      */
		/* 1688 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public Object toJdbc()
	/*      */throws SQLException
	/*      */{
		/* 1700 */return null;
		/*      */}

	/*      */
	/*      */public Object makeJdbcArray(int paramInt)
	/*      */{
		/* 1712 */Timestamp[] arrayOfTimestamp = new Timestamp[paramInt];
		/*      */
		/* 1714 */return arrayOfTimestamp;
		/*      */}

	/*      */
	/*      */public boolean isConvertibleTo(Class paramClass)
	/*      */{
		/* 1730 */return (paramClass.getName().compareTo("java.sql.Date") == 0)
				|| (paramClass.getName().compareTo("java.sql.Time") == 0)
				|| (paramClass.getName().compareTo("java.sql.Timestamp") == 0)
				|| (paramClass.getName().compareTo("java.lang.String") == 0);
		/*      */}

	/*      */
	/*      */private static int setHighOrderbits(int paramInt)
	/*      */{
		/* 1775 */return (paramInt & 0x1FC0) >> 6;
		/*      */}

	/*      */
	/*      */private static int setLowOrderbits(int paramInt)
	/*      */{
		/* 1780 */return (paramInt & 0x3F) << 2;
		/*      */}

	/*      */
	/*      */private static int getHighOrderbits(int paramInt)
	/*      */{
		/* 1785 */return (paramInt & 0x7F) << 6;
		/*      */}

	/*      */
	/*      */private static int getLowOrderbits(int paramInt)
	/*      */{
		/* 1790 */return (paramInt & 0xFC) >> 2;
		/*      */}

	/*      */
	/*      */private static int getJavaYear(int paramInt1, int paramInt2)
	/*      */{
		/* 1800 */return (paramInt1 - 100) * 100 + (paramInt2 - 100);
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.TIMESTAMPTZ JD-Core
 * Version: 0.6.0
 */