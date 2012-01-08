package oracle.jdbc.driver;

import java.util.TimeZone;

abstract class DateCommonBinder extends Binder {
	static final int GREGORIAN_CUTOVER_YEAR = 1582;
	static final long GREGORIAN_CUTOVER = -12219292800000L;
	static final int JAN_1_1_JULIAN_DAY = 1721426;
	static final int EPOCH_JULIAN_DAY = 2440588;
	static final int ONE_SECOND = 1000;
	static final int ONE_MINUTE = 60000;
	static final int ONE_HOUR = 3600000;
	static final long ONE_DAY = 86400000L;
	/* 15392 */static final int[] NUM_DAYS = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };

	/* 15396 */static final int[] LEAP_NUM_DAYS = { 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 };

	/* 15400 */static final int[] MONTH_LENGTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/* 15404 */static final int[] LEAP_MONTH_LENGTH = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	static final int ORACLE_DATE_CENTURY = 0;
	static final int ORACLE_DATE_YEAR = 1;
	static final int ORACLE_DATE_MONTH = 2;
	static final int ORACLE_DATE_DAY = 3;
	static final int ORACLE_DATE_HOUR = 4;
	static final int ORACLE_DATE_MIN = 5;
	static final int ORACLE_DATE_SEC = 6;
	static final int ORACLE_DATE_NANO1 = 7;
	static final int ORACLE_DATE_NANO2 = 8;
	static final int ORACLE_DATE_NANO3 = 9;
	static final int ORACLE_DATE_NANO4 = 10;

	static final long floorDivide(long paramLong1, long paramLong2) {
		/* 15423 */return paramLong1 >= 0L ? paramLong1 / paramLong2 : (paramLong1 + 1L) / paramLong2 - 1L;
	}

	static final int floorDivide(int paramInt1, int paramInt2) {
		/* 15429 */return paramInt1 >= 0 ? paramInt1 / paramInt2 : (paramInt1 + 1) / paramInt2 - 1;
	}

	static final int floorDivide(int paramInt1, int paramInt2, int[] paramArrayOfInt) {
		/* 15435 */if (paramInt1 >= 0) {
			/* 15437 */paramArrayOfInt[0] = (paramInt1 % paramInt2);

			/* 15439 */return paramInt1 / paramInt2;
		}

		/* 15442 */int i = (paramInt1 + 1) / paramInt2 - 1;

		/* 15444 */paramArrayOfInt[0] = (paramInt1 - i * paramInt2);

		/* 15446 */return i;
	}

	static final int floorDivide(long paramLong, int paramInt, int[] paramArrayOfInt) {
		/* 15453 */if (paramLong >= 0L) {
			/* 15455 */paramArrayOfInt[0] = (int) (paramLong % paramInt);

			/* 15457 */return (int) (paramLong / paramInt);
		}

		/* 15460 */int i = (int) ((paramLong + 1L) / paramInt - 1L);

		/* 15462 */paramArrayOfInt[0] = (int) (paramLong - i * paramInt);

		/* 15464 */return i;
	}

	static final long zoneOffset(TimeZone paramTimeZone, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
		/* 15472 */return paramTimeZone.getOffset(paramInt1 < 0 ? 0 : 1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
	}

	static void setOracleNanos(long paramLong, byte[] paramArrayOfByte, int paramInt) {
		/* 15480 */paramArrayOfByte[(10 + paramInt)] = (byte) (int) (paramLong & 0xFF);
		/* 15481 */paramArrayOfByte[(9 + paramInt)] = (byte) (int) (paramLong >> 8 & 0xFF);
		/* 15482 */paramArrayOfByte[(8 + paramInt)] = (byte) (int) (paramLong >> 16 & 0xFF);
		/* 15483 */paramArrayOfByte[(7 + paramInt)] = (byte) (int) (paramLong >> 24 & 0xFF);
	}

	static void setOracleHMS(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
		/* 15492 */paramInt1 /= 1000;
		/* 15493 */paramArrayOfByte[(6 + paramInt2)] = (byte) (paramInt1 % 60 + 1);
		/* 15494 */paramInt1 /= 60;
		/* 15495 */paramArrayOfByte[(5 + paramInt2)] = (byte) (paramInt1 % 60 + 1);
		/* 15496 */paramInt1 /= 60;
		/* 15497 */paramArrayOfByte[(4 + paramInt2)] = (byte) (paramInt1 + 1);
	}

	static int setOracleCYMD(long paramLong, byte[] paramArrayOfByte, int paramInt, OraclePreparedStatement paramOraclePreparedStatement) {
		/* 15505 */TimeZone localTimeZone = paramOraclePreparedStatement.getDefaultTimeZone();

		/* 15507 */int i2 = localTimeZone.getRawOffset();
		/* 15508 */long l1 = paramLong + i2;
		long l2;
		int n;
		int i;
		int i1;
		int m;
		int i7;
		long l3;
		/* 15511 */if (l1 >= -12219292800000L) {
			/* 15515 */l2 = 2440588L + floorDivide(l1, 86400000L) - 1721426L;
			int i5;
			int i6;
			int i8;
			/* 15525 */if (l2 > 0L) {
				/* 15527 */i5 = (int) (l2 / 146097L);
				/* 15528 */n = (int) (l2 % 146097L);
				/* 15529 */i6 = n / 36524;
				/* 15530 */n %= 36524;
				/* 15531 */i7 = n / 1461;
				/* 15532 */n %= 1461;
				/* 15533 */i8 = n / 365;
				/* 15534 */n %= 365;
			} else {
				/* 15538 */int[] arrayOfInt = new int[1];

				/* 15541 */i5 = floorDivide(l2, 146097, arrayOfInt);
				/* 15542 */i6 = floorDivide(arrayOfInt[0], 36524, arrayOfInt);
				/* 15543 */i7 = floorDivide(arrayOfInt[0], 1461, arrayOfInt);
				/* 15544 */i8 = floorDivide(arrayOfInt[0], 365, arrayOfInt);
				/* 15545 */n = arrayOfInt[0];
			}

			/* 15548 */i = 400 * i5 + 100 * i6 + 4 * i7 + i8;

			/* 15550 */if ((i6 == 4) || (i8 == 4)) {
				/* 15552 */n = 365;
			} else {
				/* 15556 */i++;
			}

			/* 15559 */i1 = ((i & 0x3) == 0) && ((i % 100 != 0) || (i % 400 == 0)) ? 1 : 0;

			/* 15562 */m = (int) ((l2 + 1L) % 7L);
		} else {
			/* 15569 */l2 = 2440588L + floorDivide(l1, 86400000L) - 1721424L;

			/* 15573 */i = (int) floorDivide(4L * l2 + 1464L, 1461L);

			/* 15576 */l3 = 365 * (i - 1) + floorDivide(i - 1, 4);

			/* 15578 */n = (int) (l2 - l3);
			/* 15579 */i1 = (i & 0x3) == 0 ? 1 : 0;

			/* 15582 */m = (int) ((l2 - 1L) % 7L);
		}

		/* 15586 */int i3 = 0;
		/* 15587 */int i4 = i1 != 0 ? 60 : 59;

		/* 15589 */if (n >= i4) {
			/* 15591 */i3 = i1 != 0 ? 1 : 2;
		}

		/* 15594 */int j = (12 * (n + i3) + 6) / 367;
		/* 15595 */int k = n - (i1 != 0 ? LEAP_NUM_DAYS[j] : NUM_DAYS[j]) + 1;

		/* 15599 */m += (m < 0 ? 8 : 1);

		l3 = l1 / 86400000L;
		i7 = (int) (l1 - l3 * 86400000L);

		/* 15604 */if (i7 < 0) {
			/* 15605 */i7 = (int) (i7 + 86400000L);
		}

		/* 15610 */long l4 = zoneOffset(localTimeZone, i, j, k, m, i7) - i2;

		/* 15615 */i7 = (int) (i7 + l4);

		/* 15619 */if (i7 >= 86400000L) {
			/* 15621 */i7 = (int) (i7 - 86400000L);

			/* 15623 */k++;
			if (k > (i1 != 0 ? LEAP_MONTH_LENGTH[j] : MONTH_LENGTH[j])) {
				/* 15625 */k = 1;

				/* 15627 */j++;
				if (j == 12) {
					/* 15629 */j = 0;
					/* 15630 */i++;
				}
			}

		}

		/* 15636 */if (i <= 0) {
			/* 15637 */i--;
		}
		/* 15639 */paramArrayOfByte[(0 + paramInt)] = (byte) (i / 100 + 100);
		/* 15640 */paramArrayOfByte[(1 + paramInt)] = (byte) (i % 100 + 100);
		/* 15641 */paramArrayOfByte[(2 + paramInt)] = (byte) (j + 1);
		/* 15642 */paramArrayOfByte[(3 + paramInt)] = (byte) k;

		/* 15644 */return i7;
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.DateCommonBinder
 * JD-Core Version: 0.6.0
 */