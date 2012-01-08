/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CRowidAccessor extends RowidAccessor
/*     */{
	/*     */T4CMAREngine mare;
	/*     */static final int maxLength = 128;
	/* 90 */final int[] meta = new int[1];
	/*     */static final int KGRD_EXTENDED_OBJECT = 6;
	/*     */static final int KGRD_EXTENDED_BLOCK = 6;
	/*     */static final int KGRD_EXTENDED_FILE = 3;
	/*     */static final int KGRD_EXTENDED_SLOT = 3;
	/*     */static final int kd4_ubridtype_physicall = 1;
	/*     */static final int kd4_ubridtype_logical = 2;
	/*     */static final int kd4_ubridtype_remote = 3;
	/*     */static final int kd4_ubridtype_exttab = 4;
	/*     */static final int kd4_ubridlen_typeind = 1;
	/* 735 */static final byte[] kgrd_indbyte_char = { 65, 42, 45, 40, 41 };
	/*     */
	/* 742 */static final byte[] kgrd_basis_64 = { 65, 66, 67, 68, 69, 70, 71,
			72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
			89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
			109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121,
			122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
	/*     */
	/* 757 */static final byte[] kgrd_index_64 = { -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
			-1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
	/*     */
	/* 784 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";

	/*     */
	/*     */T4CRowidAccessor(OracleStatement paramOracleStatement, int paramInt1,
			short paramShort, int paramInt2, boolean paramBoolean,
			T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 31 */super(paramOracleStatement, paramInt1, paramShort, paramInt2,
				paramBoolean);
		/*     */
		/* 33 */this.mare = paramT4CMAREngine;
		/* 34 */this.defineType = 104;
		/*     */}

	/*     */
	/*     */T4CRowidAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort, int paramInt7,
			int paramInt8, T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 46 */super(paramOracleStatement, paramInt1, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
		/*     */
		/* 49 */this.mare = paramT4CMAREngine;
		/* 50 */this.definedColumnType = paramInt7;
		/* 51 */this.definedColumnSize = paramInt8;
		/* 52 */this.defineType = 104;
		/*     */}

	/*     */
	/*     */void processIndicator(int paramInt)
	/*     */throws IOException, SQLException
	/*     */{
		/* 60 */if (((this.internalType == 1) && (this.describeType == 112))
				|| ((this.internalType == 23) && (this.describeType == 113)))
		/*     */{
			/* 67 */this.mare.unmarshalUB2();
			/* 68 */this.mare.unmarshalUB2();
			/*     */}
		/* 70 */else if (this.mare.versionNumber < 9200)
		/*     */{
			/* 74 */this.mare.unmarshalSB2();
			/*     */
			/* 76 */if ((this.statement.sqlKind != 1)
					&& (this.statement.sqlKind != 4))
			/*     */{
				/* 78 */this.mare.unmarshalSB2();
				/*     */}
			/* 80 */} else if ((this.statement.sqlKind == 1)
				|| (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
		/*     */{
			/* 83 */this.mare.processIndicator(paramInt <= 0, paramInt);
			/*     */}
		/*     */}

	/*     */
	/*     */boolean unmarshalOneRow()
	/*     */throws SQLException, IOException
	/*     */{
		/* 108 */if (this.isUseLess)
		/*     */{
			/* 110 */this.lastRowProcessed += 1;
			/*     */
			/* 112 */return false;
			/*     */}
		/*     */int m;
		/*     */int i2;
		/* 117 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 119 */i = this.mare.unmarshalUB1();
			/* 120 */long l1 = 0L;
			/* 121 */m = 0;
			/* 122 */int n = 0;
			/* 123 */long l3 = 0L;
			/* 124 */i2 = 0;
			/*     */
			/* 128 */if (i > 0)
			/*     */{
				/* 130 */l1 = this.mare.unmarshalUB4();
				/* 131 */m = this.mare.unmarshalUB2();
				/* 132 */n = this.mare.unmarshalUB1();
				/* 133 */l3 = this.mare.unmarshalUB4();
				/* 134 */i2 = this.mare.unmarshalUB2();
				/*     */}
			/*     */
			/* 137 */processIndicator(this.meta[0]);
			/*     */
			/* 139 */this.lastRowProcessed += 1;
			/*     */
			/* 141 */return false;
			/*     */}
		/*     */
		/* 145 */int i = this.indicatorIndex + this.lastRowProcessed;
		/* 146 */int j = this.lengthIndex + this.lastRowProcessed;
		/*     */
		/* 148 */if (this.isNullByDescribe)
		/*     */{
			/* 150 */this.rowSpaceIndicator[i] = -1;
			/* 151 */this.rowSpaceIndicator[j] = 0;
			/* 152 */this.lastRowProcessed += 1;
			/*     */
			/* 154 */if (this.mare.versionNumber < 9200) {
				/* 155 */processIndicator(0);
				/*     */}
			/* 157 */return false;
			/*     */}
		/*     */
		/* 160 */int k = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/*     */
		/* 165 */if (this.describeType != 208)
		/*     */{
			/* 167 */m = this.mare.unmarshalUB1();
			/* 168 */long l2 = 0L;
			/* 169 */int i1 = 0;
			/* 170 */i2 = 0;
			/* 171 */long l4 = 0L;
			/* 172 */int i3 = 0;
			/*     */
			/* 176 */if (m > 0)
			/*     */{
				/* 178 */l2 = this.mare.unmarshalUB4();
				/* 179 */i1 = this.mare.unmarshalUB2();
				/* 180 */i2 = this.mare.unmarshalUB1();
				/* 181 */l4 = this.mare.unmarshalUB4();
				/* 182 */i3 = this.mare.unmarshalUB2();
				/*     */}
			/*     */
			/* 187 */if ((l2 == 0L) && (i1 == 0) && (i2 == 0) && (l4 == 0L)
					&& (i3 == 0))
			/*     */{
				/* 189 */this.meta[0] = 0;
				/*     */}
			/*     */else {
				/* 192 */long[] arrayOfLong = { l2, i1, l4, i3 };
				/*     */
				/* 196 */byte[] arrayOfByte = rowidToString(arrayOfLong);
				/* 197 */int i4 = 18;
				/*     */
				/* 199 */if (this.byteLength - 2 < 18) {
					/* 200 */i4 = this.byteLength - 2;
					/*     */}
				/* 202 */System.arraycopy(arrayOfByte, 0, this.rowSpaceByte,
						k + 2, i4);
				/*     */
				/* 205 */this.meta[0] = i4;
				/*     */}
			/*     */
			/*     */}
		/* 210 */else if ((this.meta[0] = (int) this.mare.unmarshalUB4()) > 0) {
			/* 211 */this.mare
					.unmarshalCLR(this.rowSpaceByte, k + 2, this.meta);
			/*     */}
		/*     */
		/* 214 */this.rowSpaceByte[k] = (byte) ((this.meta[0] & 0xFF00) >> 8);
		/* 215 */this.rowSpaceByte[(k + 1)] = (byte) (this.meta[0] & 0xFF);
		/*     */
		/* 224 */processIndicator(this.meta[0]);
		/*     */
		/* 226 */if (this.meta[0] == 0)
		/*     */{
			/* 230 */this.rowSpaceIndicator[i] = -1;
			/* 231 */this.rowSpaceIndicator[j] = 0;
			/*     */}
		/*     */else
		/*     */{
			/* 235 */this.rowSpaceIndicator[j] = (short) this.meta[0];
			/*     */
			/* 240 */this.rowSpaceIndicator[i] = 0;
			/*     */}
		/*     */
		/* 243 */this.lastRowProcessed += 1;
		/*     */
		/* 247 */return false;
		/*     */}

	/*     */
	/*     */String getString(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 256 */String str = null;
		/*     */
		/* 258 */if (this.rowSpaceIndicator == null) {
			/* 259 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 263 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 265 */int i = this.columnIndex + this.byteLength * paramInt;
			/*     */
			/* 269 */int j = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
			/*     */
			/* 271 */if ((this.describeType != 208)
					|| (this.rowSpaceByte[i] == 1))
			/*     */{
				/* 274 */str = new String(this.rowSpaceByte, i + 2, j);
				/*     */
				/* 276 */long[] arrayOfLong = stringToRowid(str.getBytes(), 0,
						str.length());
				/*     */
				/* 282 */str = new String(rowidToString(arrayOfLong));
				/*     */}
			/*     */else
			/*     */{
				/* 286 */str = kgrdub2c(this.rowSpaceByte, j, i + 2);
				/*     */}
			/*     */}
		/*     */
		/* 290 */return str;
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt) throws SQLException
	/*     */{
		/* 295 */if (this.definedColumnType == 0) {
			/* 296 */return super.getObject(paramInt);
			/*     */}
		/*     */
		/* 299 */Object localObject = null;
		/*     */
		/* 301 */if (this.rowSpaceIndicator == null) {
			/* 302 */DatabaseError.throwSqlException(21);
			/*     */}
		/* 304 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 306 */switch (this.definedColumnType)
			/*     */{
			/*     */case -1:
				/*     */
			case 1:
				/*     */
			case 12:
				/* 314 */return getString(paramInt);
				/*     */case -8:
				/* 317 */return getROWID(paramInt);
				/*     */}
			/*     */
			/* 320 */DatabaseError.throwSqlException(4);
			/*     */
			/* 322 */return null;
			/*     */}
		/*     */
		/* 326 */return localObject;
		/*     */}

	/*     */
	/*     */void copyRow()
	/*     */throws SQLException, IOException
	/*     */{
		/*     */int i;
		/* 335 */if (this.lastRowProcessed == 0)
			/* 336 */i = this.statement.rowPrefetch;
		/*     */else {
			/* 338 */i = this.lastRowProcessed;
			/*     */}
		/* 340 */int j = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/* 341 */int k = this.columnIndex + (i - 1) * this.byteLength;
		/*     */
		/* 343 */int m = this.indicatorIndex + this.lastRowProcessed;
		/* 344 */int n = this.indicatorIndex + i - 1;
		/* 345 */int i1 = this.lengthIndex + this.lastRowProcessed;
		/* 346 */int i2 = this.lengthIndex + i - 1;
		/* 347 */int i3 = this.rowSpaceIndicator[i2];
		/*     */
		/* 349 */this.rowSpaceIndicator[i1] = (short) i3;
		/* 350 */this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
		/*     */
		/* 353 */System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j,
				i3 + 2);
		/*     */
		/* 356 */this.lastRowProcessed += 1;
		/*     */}

	/*     */
	/*     */void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte,
			char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 366 */int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
		/*     */
		/* 368 */int j = this.columnIndexLastRow + (paramInt1 - 1)
				* this.byteLength;
		/*     */
		/* 370 */int k = this.indicatorIndex + paramInt2 - 1;
		/* 371 */int m = this.indicatorIndexLastRow + paramInt1 - 1;
		/* 372 */int n = this.lengthIndex + paramInt2 - 1;
		/* 373 */int i1 = this.lengthIndexLastRow + paramInt1 - 1;
		/* 374 */int i2 = paramArrayOfShort[i1];
		/*     */
		/* 376 */this.rowSpaceIndicator[n] = (short) i2;
		/* 377 */this.rowSpaceIndicator[k] = paramArrayOfShort[m];
		/*     */
		/* 380 */if (i2 != 0)
		/*     */{
			/* 382 */System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte,
					i, i2 + 2);
			/*     */}
		/*     */}

	/*     */
	/*     */static final byte[] rowidToString(long[] paramArrayOfLong)
	/*     */{
		/* 395 */long l1 = paramArrayOfLong[0];
		/*     */
		/* 398 */long l2 = paramArrayOfLong[1];
		/*     */
		/* 401 */long l3 = paramArrayOfLong[2];
		/*     */
		/* 404 */long l4 = paramArrayOfLong[3];
		/*     */
		/* 406 */int i = 18;
		/*     */
		/* 410 */byte[] arrayOfByte = new byte[i];
		/* 411 */int j = 0;
		/*     */
		/* 413 */j = kgrd42b(arrayOfByte, l1, 6, j);
		/*     */
		/* 416 */j = kgrd42b(arrayOfByte, l2, 3, j);
		/*     */
		/* 419 */j = kgrd42b(arrayOfByte, l3, 6, j);
		/*     */
		/* 422 */j = kgrd42b(arrayOfByte, l4, 3, j);
		/*     */
		/* 426 */return arrayOfByte;
		/*     */}

	/*     */
	/*     */static final long[] rcToRowid(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 440 */int i = 18;
		/*     */
		/* 442 */if (paramInt2 != i)
		/*     */{
			/* 444 */throw new SQLException("Rowid size incorrect.");
			/*     */}
		/*     */
		/* 447 */long[] arrayOfLong = new long[3];
		/* 448 */String str = new String(paramArrayOfByte, paramInt1, paramInt2);
		/*     */
		/* 451 */long l1 = Long.parseLong(str.substring(0, 8), 16);
		/* 452 */long l2 = Long.parseLong(str.substring(9, 13), 16);
		/* 453 */long l3 = Long.parseLong(str.substring(14, 8), 16);
		/*     */
		/* 455 */arrayOfLong[0] = l3;
		/* 456 */arrayOfLong[1] = l1;
		/* 457 */arrayOfLong[2] = l2;
		/*     */
		/* 459 */return arrayOfLong;
		/*     */}

	/*     */
	/*     */static final byte[] kgrdr2rc(long[] paramArrayOfLong)
	/*     */{
		/* 474 */long l1 = paramArrayOfLong[1];
		/*     */
		/* 477 */long l2 = paramArrayOfLong[2];
		/*     */
		/* 480 */long l3 = paramArrayOfLong[3];
		/* 481 */int i = 18;
		/* 482 */byte[] arrayOfByte = new byte[i];
		/* 483 */int j = 0;
		/*     */
		/* 485 */j = lmx42h(arrayOfByte, l2, 8, j);
		/* 486 */arrayOfByte[(j++)] = 46;
		/* 487 */j = lmx42h(arrayOfByte, l3, 4, j);
		/* 488 */arrayOfByte[(j++)] = 46;
		/* 489 */j = lmx42h(arrayOfByte, l1, 4, j);
		/*     */
		/* 491 */return arrayOfByte;
		/*     */}

	/*     */
	/*     */static final int lmx42h(byte[] paramArrayOfByte, long paramLong,
			int paramInt1, int paramInt2)
	/*     */{
		/* 502 */String str = Long.toHexString(paramLong);
		/*     */
		/* 505 */int i = paramInt1;
		/* 506 */int j = 0;
		/*     */do
		/*     */{
			/* 510 */if (j < str.length())
			/*     */{
				/* 512 */paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = (byte) str
						.charAt(str.length() - j - 1);
				/*     */
				/* 514 */j++;
				/*     */}
			/*     */else
			/*     */{
				/* 518 */paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = 48;
				/*     */}
			/*     */
			/* 521 */paramInt1--;
		} while (paramInt1 > 0);
		/*     */
		/* 523 */return i + paramInt2;
		/*     */}

	/*     */
	/*     */static final long[] stringToRowid(byte[] paramArrayOfByte,
			int paramInt1, int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 532 */int i = 18;
		/*     */
		/* 537 */if (paramInt2 != i)
		/*     */{
			/* 539 */throw new SQLException("Rowid size incorrect.");
			/*     */}
		/*     */
		/* 542 */long[] arrayOfLong = new long[4];
		/*     */try
		/*     */{
			/* 548 */arrayOfLong[0] = kgrdb42(paramArrayOfByte, 6, paramInt1);
			/*     */
			/* 551 */paramInt1 += 6;
			/*     */
			/* 554 */arrayOfLong[1] = kgrdb42(paramArrayOfByte, 3, paramInt1);
			/*     */
			/* 557 */paramInt1 += 3;
			/*     */
			/* 560 */arrayOfLong[2] = kgrdb42(paramArrayOfByte, 6, paramInt1);
			/*     */
			/* 563 */paramInt1 += 6;
			/*     */
			/* 566 */arrayOfLong[3] = kgrdb42(paramArrayOfByte, 3, paramInt1);
			/*     */
			/* 569 */paramInt1 += 3;
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/* 573 */arrayOfLong[0] = 0L;
			/* 574 */arrayOfLong[1] = 0L;
			/* 575 */arrayOfLong[2] = 0L;
			/* 576 */arrayOfLong[3] = 0L;
			/*     */}
		/*     */
		/* 579 */return arrayOfLong;
		/*     */}

	/*     */
	/*     */static final int kgrd42b(byte[] paramArrayOfByte, long paramLong,
			int paramInt1, int paramInt2)
	/*     */{
		/* 589 */int i = paramInt1;
		/* 590 */long l = paramLong;
		/*     */
		/* 592 */for (; paramInt1 > 0; paramInt1--)
		/*     */{
			/* 594 */paramArrayOfByte[(paramInt2 + paramInt1 - 1)] = kgrd_basis_64[((int) l & 0x3F)];
			/* 595 */l = l >>> 6 & 0x3FFFFFF;
			/*     */}
		/*     */
		/* 601 */return i + paramInt2;
		/*     */}

	/*     */
	/*     */static final long kgrdb42(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 610 */long l = 0L;
		/*     */
		/* 612 */for (int i = 0; i < paramInt1; i++)
		/*     */{
			/* 614 */int j = paramArrayOfByte[(paramInt2 + i)];
			/*     */
			/* 616 */j = kgrd_index_64[j];
			/*     */
			/* 618 */if (j == -1) {
				/* 619 */throw new SQLException(
						"Char data to rowid conversion failed.");
				/*     */}
			/* 621 */l <<= 6;
			/* 622 */l |= j;
			/*     */}
		/*     */
		/* 625 */return l;
		/*     */}

	/*     */
	/*     */static final String kgrdub2c(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 631 */int i = paramArrayOfByte[paramInt2];
		/* 632 */char[] arrayOfChar = new char[512];
		/* 633 */int j = 0;
		/* 634 */int k = paramInt1 - 1;
		/* 635 */int m = 4 * (paramInt1 / 3)
				+ (paramInt1 % 3 == 0 ? paramInt1 % 3 + 1 : 0);
		/*     */
		/* 637 */int n = 1 + m - 1;
		/*     */
		/* 639 */if (n != 0)
		/*     */{
			/* 641 */arrayOfChar[0] = (char) kgrd_indbyte_char[(i - 1)];
			/*     */
			/* 655 */int i1 = paramInt2 + 1;
			/*     */
			/* 657 */j = 1;
			/*     */
			/* 659 */int i2 = 0;
			/*     */
			/* 661 */while (k > 0)
			/*     */{
				/* 663 */arrayOfChar[(j++)] = (char) kgrd_basis_64[((paramArrayOfByte[i1] & 0xFF) >> 2)];
				/*     */
				/* 667 */if (k == 1)
				/*     */{
					/* 671 */arrayOfChar[(j++)] = (char) kgrd_basis_64[((paramArrayOfByte[i1] & 0x3) << 4)];
					/*     */
					/* 674 */break;
					/*     */}
				/*     */
				/* 678 */i2 = (byte) (paramArrayOfByte[(i1 + 1)] & 0xFF);
				/*     */
				/* 680 */arrayOfChar[(j++)] = (char) kgrd_basis_64[((paramArrayOfByte[i1] & 0x3) << 4 | (i2 & 0xF0) >> 4)];
				/*     */
				/* 684 */if (k == 2)
				/*     */{
					/* 686 */arrayOfChar[(j++)] = (char) kgrd_basis_64[((i2 & 0xF) << 2)];
					/*     */
					/* 689 */break;
					/*     */}
				/*     */
				/* 696 */i1 += 2;
				/* 697 */arrayOfChar[(j++)] = (char) kgrd_basis_64[((i2 & 0xF) << 2 | (paramArrayOfByte[i1] & 0xC0) >> 6)];
				/*     */
				/* 701 */arrayOfChar[j] = (char) kgrd_basis_64[(paramArrayOfByte[i1] & 0x3F)];
				/*     */
				/* 705 */k -= 3;
				/* 706 */i1++;
				/* 707 */j++;
				/*     */}
			/*     */}
		/*     */
		/* 711 */return new String(arrayOfChar, 0, j);
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CRowidAccessor
 * JD-Core Version: 0.6.0
 */