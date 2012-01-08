/*     */package oracle.jdbc.oracore;

/*     */
/*     */import java.io.IOException;
/*     */
import java.io.ObjectInputStream;
/*     */
import java.io.ObjectOutputStream;
/*     */
import java.io.Serializable;
/*     */
import java.sql.SQLException;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.driver.DatabaseError;
/*     */
import oracle.jdbc.internal.OracleConnection;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.NUMBER;

/*     */
/*     */public class OracleTypeNUMBER extends OracleType
/*     */implements Serializable
/*     */{
	/*     */static final long serialVersionUID = -7182242886677299812L;
	/*     */int precision;
	/*     */int scale;
	/* 766 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";

	/*     */
	/*     */protected OracleTypeNUMBER()
	/*     */{
		/*     */}

	/*     */
	/*     */protected OracleTypeNUMBER(int paramInt)
	/*     */{
		/* 41 */super(paramInt);
		/*     */}

	/*     */
	/*     */public Datum toDatum(Object paramObject,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 54 */return toNUMBER(paramObject, paramOracleConnection);
		/*     */}

	/*     */
	/*     */public Datum[] toDatumArray(Object paramObject,
			OracleConnection paramOracleConnection, long paramLong, int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 66 */return toNUMBERArray(paramObject, paramOracleConnection,
				paramLong, paramInt);
		/*     */}

	/*     */
	/*     */public void parseTDSrec(TDSReader paramTDSReader)
	/*     */throws SQLException
	/*     */{
		/* 78 */this.nullOffset = paramTDSReader.nullOffset;
		/* 79 */this.ldsOffset = paramTDSReader.ldsOffset;
		/* 80 */paramTDSReader.nullOffset += 1;
		/* 81 */paramTDSReader.ldsOffset += 1;
		/*     */
		/* 83 */this.precision = paramTDSReader.readUnsignedByte();
		/*     */
		/* 86 */this.scale = paramTDSReader.readByte();
		/*     */}

	/*     */
	/*     */public int getSizeLDS(byte[] paramArrayOfByte)
	/*     */{
		/* 95 */if (this.sizeForLds == 0)
		/*     */{
			/* 97 */this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 12);
			/* 98 */this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 12);
			/*     */}
		/*     */
		/* 103 */return this.sizeForLds;
		/*     */}

	/*     */
	/*     */public int getAlignLDS(byte[] paramArrayOfByte)
	/*     */{
		/* 112 */if (this.sizeForLds == 0)
		/*     */{
			/* 114 */this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 12);
			/* 115 */this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 12);
			/*     */}
		/*     */
		/* 120 */return this.alignForLds;
		/*     */}

	/*     */
	/*     */protected Object unpickle80rec(UnpickleContext paramUnpickleContext,
			int paramInt1, int paramInt2, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 133 */return numericUnpickle80rec(this.ldsOffset, this.nullOffset,
				paramUnpickleContext, paramInt1, paramInt2, paramMap);
		/*     */}

	/*     */
	/*     */protected static Object numericUnpickle80rec(int paramInt1,
			int paramInt2, UnpickleContext paramUnpickleContext, int paramInt3,
			int paramInt4, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 144 */switch (paramInt3)
		/*     */{
		/*     */case 1:
			/* 148 */if (paramUnpickleContext.isNull(paramInt2)) {
				/* 149 */return null;
				/*     */}
			/* 151 */paramUnpickleContext
					.skipTo(paramUnpickleContext.ldsOffsets[paramInt1]);
			/*     */
			/* 153 */break;
		/*     */case 2:
			/* 156 */if ((paramUnpickleContext.readByte() & 0x1) != 1)
				/*     */break;
			/* 158 */paramUnpickleContext.skipBytes(22);
			/*     */
			/* 160 */return null;
			/*     */case 3:
			/* 166 */break;
		/*     */default:
			/* 169 */DatabaseError.throwSqlException(1, "format=" + paramInt3);
			/*     */}
		/*     */
		/* 173 */if (paramInt4 == 9)
		/*     */{
			/* 175 */paramUnpickleContext.skipBytes(22);
			/*     */
			/* 177 */return null;
			/*     */}
		/*     */
		/* 181 */return toNumericObject(paramUnpickleContext.readVarNumBytes(),
				paramInt4, paramMap);
		/*     */}

	/*     */
	/*     */protected static Object unpickle80NativeArray(
			UnpickleContext paramUnpickleContext, long paramLong,
			int paramInt1, int paramInt2, int paramInt3)
	/*     */throws SQLException
	/*     */{
		/* 201 */int i = 0;
		/*     */
		/* 203 */switch (paramInt3)
		/*     */{
		/*     */case 2:
			/* 207 */i = 23;
			/*     */
			/* 209 */break;
		/*     */case 3:
			/* 212 */i = 22;
			/*     */
			/* 214 */break;
		/*     */default:
			/* 217 */DatabaseError.throwSqlException(1, "format=" + paramInt3);
			/*     */}
		/*     */
		/* 221 */if (paramInt1 > 0) {
			/* 222 */paramUnpickleContext.skipBytes(i * ((int) paramLong - 1));
			/*     */}
		/* 224 */int j = paramUnpickleContext.absoluteOffset();
		/* 225 */byte[] arrayOfByte1 = paramUnpickleContext.image();
		/* 226 */int k = 0;
		/*     */Object localObject;
		/*     */int m;
		/*     */byte[] arrayOfByte2;
		/* 228 */switch (paramInt2)
		/*     */{
		/*     */case 4:
			/* 233 */localObject = new int[paramInt1];
			/*     */
			/* 235 */for (m = 0; m < paramInt1; m++)
			/*     */{
				/* 239 */k = j + m * i;
				/*     */
				/* 242 */if ((paramInt3 != 3)
						&& ((arrayOfByte1[(k++)] & 0x1) != 0))
				/*     */{
					/*     */continue;
					/*     */}
				/*     */
				/* 247 */arrayOfByte2 = new byte[arrayOfByte1[(k++)]];
				/*     */
				/* 250 */System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0,
						arrayOfByte2.length);
				/*     */
				/* 253 */localObject[m] = NUMBER.toInt(arrayOfByte2);
				/*     */}
			/*     */
			/* 258 */paramUnpickleContext.skipBytes(i * paramInt1);
			/*     */
			/* 260 */return localObject;
			/*     */case 5:
			/* 265 */localObject = new double[paramInt1];
			/*     */
			/* 267 */for (m = 0; m < paramInt1; m++)
			/*     */{
				/* 269 */k = j + m * i;
				/*     */
				/* 271 */if ((paramInt3 != 3)
						&& ((arrayOfByte1[(k++)] & 0x1) != 0)) {
					/*     */continue;
					/*     */}
				/* 274 */arrayOfByte2 = new byte[arrayOfByte1[(k++)]];
				/*     */
				/* 276 */System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0,
						arrayOfByte2.length);
				/*     */
				/* 278 */localObject[m] = NUMBER.toDouble(arrayOfByte2);
				/*     */}
			/*     */
			/* 282 */paramUnpickleContext.skipBytes(i * paramInt1);
			/*     */
			/* 284 */return localObject;
			/*     */case 7:
			/* 289 */localObject = new long[paramInt1];
			/*     */
			/* 291 */for (m = 0; m < paramInt1; m++)
			/*     */{
				/* 293 */k = j + m * i;
				/*     */
				/* 295 */if ((paramInt3 != 3)
						&& ((arrayOfByte1[(k++)] & 0x1) != 0)) {
					/*     */continue;
					/*     */}
				/* 298 */arrayOfByte2 = new byte[arrayOfByte1[(k++)]];
				/*     */
				/* 300 */System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0,
						arrayOfByte2.length);
				/*     */
				/* 302 */localObject[m] = NUMBER.toLong(arrayOfByte2);
				/*     */}
			/*     */
			/* 306 */paramUnpickleContext.skipBytes(i * paramInt1);
			/*     */
			/* 308 */return localObject;
			/*     */case 6:
			/* 313 */localObject = new float[paramInt1];
			/*     */
			/* 315 */for (m = 0; m < paramInt1; m++)
			/*     */{
				/* 317 */k = j + m * i;
				/*     */
				/* 319 */if ((paramInt3 != 3)
						&& ((arrayOfByte1[(k++)] & 0x1) != 0)) {
					/*     */continue;
					/*     */}
				/* 322 */arrayOfByte2 = new byte[arrayOfByte1[(k++)]];
				/*     */
				/* 324 */System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0,
						arrayOfByte2.length);
				/*     */
				/* 326 */localObject[m] = NUMBER.toFloat(arrayOfByte2);
				/*     */}
			/*     */
			/* 330 */paramUnpickleContext.skipBytes(i * paramInt1);
			/*     */
			/* 332 */return localObject;
			/*     */case 8:
			/* 337 */localObject = new short[paramInt1];
			/*     */
			/* 339 */for (m = 0; m < paramInt1; m++)
			/*     */{
				/* 341 */k = j + m * i;
				/*     */
				/* 343 */if ((paramInt3 != 3)
						&& ((arrayOfByte1[(k++)] & 0x1) != 0)) {
					/*     */continue;
					/*     */}
				/* 346 */arrayOfByte2 = new byte[arrayOfByte1[(k++)]];
				/*     */
				/* 348 */System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0,
						arrayOfByte2.length);
				/*     */
				/* 350 */localObject[m] = NUMBER.toShort(arrayOfByte2);
				/*     */}
			/*     */
			/* 354 */paramUnpickleContext.skipBytes(i * paramInt1);
			/*     */
			/* 356 */return localObject;
			/*     */}
		/*     */
		/* 360 */DatabaseError.throwSqlException(23);
		/*     */
		/* 363 */return null;
		/*     */}

	/*     */
	/*     */protected static Object unpickle81NativeArray(
			PickleContext paramPickleContext, long paramLong, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 379 */for (int i = 1; (i < paramLong) && (paramInt1 > 0); i++) {
			/* 380 */paramPickleContext.skipDataValue();
			/*     */}
		/* 382 */byte[] arrayOfByte = null;
		/*     */Object localObject;
		/*     */int j;
		/* 384 */switch (paramInt2)
		/*     */{
		/*     */case 4:
			/* 393 */localObject = new int[paramInt1];
			/*     */
			/* 395 */for (j = 0; j < paramInt1; j++)
			/*     */{
				/* 415 */if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
					/* 416 */localObject[j] = NUMBER.toInt(arrayOfByte);
					/*     */}
				/*     */
				/*     */}
			/*     */
			/* 421 */return localObject;
			/*     */case 5:
			/* 426 */localObject = new double[paramInt1];
			/*     */
			/* 428 */for (j = 0; j < paramInt1; j++)
			/*     */{
				/* 430 */if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
					/* 431 */localObject[j] = NUMBER.toDouble(arrayOfByte);
					/*     */}
				/*     */}
			/* 434 */return localObject;
			/*     */case 7:
			/* 439 */localObject = new long[paramInt1];
			/*     */
			/* 441 */for (j = 0; j < paramInt1; j++)
			/*     */{
				/* 443 */if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
					/* 444 */localObject[j] = NUMBER.toLong(arrayOfByte);
					/*     */}
				/*     */}
			/* 447 */return localObject;
			/*     */case 6:
			/* 452 */localObject = new float[paramInt1];
			/*     */
			/* 454 */for (j = 0; j < paramInt1; j++)
			/*     */{
				/* 456 */if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
					/* 457 */localObject[j] = NUMBER.toFloat(arrayOfByte);
					/*     */}
				/*     */}
			/* 460 */return localObject;
			/*     */case 8:
			/* 465 */localObject = new short[paramInt1];
			/*     */
			/* 467 */for (j = 0; j < paramInt1; j++)
			/*     */{
				/* 469 */if ((arrayOfByte = paramPickleContext.readDataValue()) != null) {
					/* 470 */localObject[j] = NUMBER.toShort(arrayOfByte);
					/*     */}
				/*     */}
			/* 473 */return localObject;
			/*     */}
		/*     */
		/* 477 */DatabaseError.throwSqlException(23);
		/*     */
		/* 480 */return null;
		/*     */}

	/*     */
	/*     */protected Object toObject(byte[] paramArrayOfByte, int paramInt,
			Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 490 */return toNumericObject(paramArrayOfByte, paramInt, paramMap);
		/*     */}

	/*     */
	/*     */static Object toNumericObject(byte[] paramArrayOfByte, int paramInt,
			Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 496 */if ((paramArrayOfByte == null)
				|| (paramArrayOfByte.length == 0)) {
			/* 497 */return null;
			/*     */}
		/* 499 */switch (paramInt)
		/*     */{
		/*     */case 1:
			/* 503 */return new NUMBER(paramArrayOfByte);
			/*     */case 2:
			/* 506 */return NUMBER.toBigDecimal(paramArrayOfByte);
			/*     */case 3:
			/* 509 */return paramArrayOfByte;
			/*     */}
		/*     */
		/* 512 */DatabaseError.throwSqlException(23);
		/*     */
		/* 515 */return null;
		/*     */}

	/*     */
	/*     */public static NUMBER toNUMBER(Object paramObject,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 527 */NUMBER localNUMBER = null;
		/*     */
		/* 529 */if (paramObject != null)
		/*     */{
			/*     */try
			/*     */{
				/* 533 */if ((paramObject instanceof NUMBER))
					/* 534 */localNUMBER = (NUMBER) paramObject;
				/*     */else
					/* 536 */localNUMBER = new NUMBER(paramObject);
				/*     */}
			/*     */catch (SQLException localSQLException)
			/*     */{
				/* 540 */DatabaseError.throwSqlException(59, paramObject);
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 547 */return localNUMBER;
		/*     */}

	/*     */
	/*     */public static Datum[] toNUMBERArray(Object paramObject,
			OracleConnection paramOracleConnection, long paramLong, int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 563 */Datum[] arrayOfDatum = null;
		/*     */Object[] arrayOfObject;
		/*     */int j;
		/* 565 */if (paramObject != null)
		/*     */{
			/* 567 */if (((paramObject instanceof Object[]))
					&& (!(paramObject instanceof char[][])))
			/*     */{
				/* 569 */arrayOfObject = (Object[]) paramObject;
				/*     */
				/* 571 */int i = (int) (paramInt == -1 ? arrayOfObject.length
						: Math.min(arrayOfObject.length - paramLong + 1L,
								paramInt));
				/*     */
				/* 574 */arrayOfDatum = new Datum[i];
				/*     */
				/* 576 */for (j = 0; j < i;) {
					/* 577 */arrayOfDatum[j] = toNUMBER(
							arrayOfObject[((int) paramLong + j - 1)],
							paramOracleConnection);
					/*     */
					/* 576 */j++;
					continue;
					/*     */
					/* 580 */arrayOfDatum = cArrayToNUMBERArray(paramObject,
							paramOracleConnection, paramLong, paramInt);
					/*     */}
				/*     */}
			/*     */}
		/*     */
		/* 585 */return arrayOfDatum;
		/*     */}

	/*     */
	/*     */static Datum[] cArrayToNUMBERArray(Object paramObject,
			OracleConnection paramOracleConnection, long paramLong, int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 602 */Datum[] arrayOfDatum = null;
		/*     */
		/* 604 */if (paramObject != null)
		/*     */{
			/*     */Object localObject;
			/*     */int i;
			/*     */int j;
			/* 606 */if ((paramObject instanceof short[]))
			/*     */{
				/* 608 */localObject = (short[]) paramObject;
				/* 609 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 612 */arrayOfDatum = new Datum[i];
				/*     */
				/* 614 */for (j = 0; j < i; j++)
					/* 615 */arrayOfDatum[j] = new NUMBER(
							localObject[((int) paramLong + j - 1)]);
				/*     */}
			/* 617 */if ((paramObject instanceof int[]))
			/*     */{
				/* 619 */localObject = (int[]) paramObject;
				/* 620 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 623 */arrayOfDatum = new Datum[i];
				/*     */
				/* 625 */for (j = 0; j < i; j++)
					/* 626 */arrayOfDatum[j] = new NUMBER(
							localObject[((int) paramLong + j - 1)]);
				/*     */}
			/* 628 */if ((paramObject instanceof long[]))
			/*     */{
				/* 630 */localObject = (long[]) paramObject;
				/* 631 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 634 */arrayOfDatum = new Datum[i];
				/*     */
				/* 636 */for (j = 0; j < i; j++)
					/* 637 */arrayOfDatum[j] = new NUMBER(
							localObject[((int) paramLong + j - 1)]);
				/*     */}
			/* 639 */if ((paramObject instanceof float[]))
			/*     */{
				/* 641 */localObject = (float[]) paramObject;
				/* 642 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 645 */arrayOfDatum = new Datum[i];
				/*     */
				/* 647 */for (j = 0; j < i; j++)
					/* 648 */arrayOfDatum[j] = new NUMBER(
							localObject[((int) paramLong + j - 1)]);
				/*     */}
			/* 650 */if ((paramObject instanceof double[]))
			/*     */{
				/* 652 */localObject = (double[]) paramObject;
				/* 653 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 656 */arrayOfDatum = new Datum[i];
				/*     */
				/* 658 */for (j = 0; j < i; j++)
					/* 659 */arrayOfDatum[j] = new NUMBER(
							localObject[((int) paramLong + j - 1)]);
				/*     */}
			/* 661 */if ((paramObject instanceof boolean[]))
			/*     */{
				/* 663 */localObject = (boolean[]) paramObject;
				/* 664 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 667 */arrayOfDatum = new Datum[i];
				/*     */
				/* 669 */for (j = 0; j < i; j++) {
					/* 670 */arrayOfDatum[j] = new NUMBER(new Boolean(
							localObject[((int) paramLong + j - 1)]));
					/*     */}
				/*     */}
			/* 673 */if ((paramObject instanceof char[][]))
			/*     */{
				/* 675 */localObject = (char[][]) paramObject;
				/* 676 */i = (int) (paramInt == -1 ? localObject.length : Math
						.min(localObject.length - paramLong + 1L, paramInt));
				/*     */
				/* 679 */arrayOfDatum = new Datum[i];
				/*     */
				/* 681 */for (j = 0; j < i; j++) {
					/* 682 */arrayOfDatum[j] = new NUMBER(new String(
							localObject[((int) paramLong + j - 1)]));
					/*     */}
				/*     */
				/*     */}
			/*     */
			/* 687 */DatabaseError.throwSqlException(59, paramObject);
			/*     */}
		/*     */
		/* 694 */return (Datum) arrayOfDatum;
		/*     */}

	/*     */
	/*     */public int getPrecision()
	/*     */{
		/* 699 */return this.precision;
		/*     */}

	/*     */
	/*     */public int getScale()
	/*     */{
		/* 704 */return this.scale;
		/*     */}

	/*     */
	/*     */private void writeObject(ObjectOutputStream paramObjectOutputStream)
	/*     */throws IOException
	/*     */{
		/* 717 */paramObjectOutputStream.writeInt(this.scale);
		/* 718 */paramObjectOutputStream.writeInt(this.precision);
		/*     */}

	/*     */
	/*     */private void readObject(ObjectInputStream paramObjectInputStream)
	/*     */throws IOException, ClassNotFoundException
	/*     */{
		/* 728 */this.scale = paramObjectInputStream.readInt();
		/* 729 */this.precision = paramObjectInputStream.readInt();
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.oracore.OracleTypeNUMBER JD-Core Version: 0.6.0
 */