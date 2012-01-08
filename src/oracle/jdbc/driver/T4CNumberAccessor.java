/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CNumberAccessor extends NumberAccessor
/*     */{
	/*     */T4CMAREngine mare;
	/* 30 */boolean underlyingLongRaw = false;
	/*     */
	/* 114 */final int[] meta = new int[1];
	/*     */
	/* 367 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*     */
	/*     */T4CNumberAccessor(OracleStatement paramOracleStatement, int paramInt1,
			short paramShort, int paramInt2, boolean paramBoolean,
			T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 36 */super(paramOracleStatement, paramInt1, paramShort, paramInt2,
				paramBoolean);
		/*     */
		/* 38 */this.mare = paramT4CMAREngine;
		/*     */}

	/*     */
	/*     */T4CNumberAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort, int paramInt7,
			int paramInt8, T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 49 */super(paramOracleStatement, paramInt1 == -1 ? paramInt8
				: paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4,
				paramInt5, paramInt6, paramShort);
		/*     */
		/* 52 */this.mare = paramT4CMAREngine;
		/* 53 */this.definedColumnType = paramInt7;
		/* 54 */this.definedColumnSize = paramInt8;
		/*     */
		/* 56 */if (paramInt1 == -1)
			/* 57 */this.underlyingLongRaw = true;
		/*     */}

	/*     */
	/*     */String getString(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 70 */String str = super.getString(paramInt);
		/*     */
		/* 72 */if ((str != null) && (this.definedColumnSize > 0)
				&& (str.length() > this.definedColumnSize))
		/*     */{
			/* 74 */str = str.substring(0, this.definedColumnSize);
			/*     */}
		/* 76 */return str;
		/*     */}

	/*     */
	/*     */void processIndicator(int paramInt)
	/*     */throws IOException, SQLException
	/*     */{
		/* 83 */if (((this.internalType == 1) && (this.describeType == 112))
				|| ((this.internalType == 23) && (this.describeType == 113)))
		/*     */{
			/* 90 */this.mare.unmarshalUB2();
			/* 91 */this.mare.unmarshalUB2();
			/*     */}
		/* 93 */else if (this.mare.versionNumber < 9200)
		/*     */{
			/* 97 */this.mare.unmarshalSB2();
			/*     */
			/* 99 */if ((this.statement.sqlKind != 1)
					&& (this.statement.sqlKind != 4))
			/*     */{
				/* 101 */this.mare.unmarshalSB2();
				/*     */}
			/* 103 */} else if ((this.statement.sqlKind == 1)
				|| (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
		/*     */{
			/* 106 */this.mare.processIndicator(paramInt <= 0, paramInt);
			/*     */}
		/*     */}

	/*     */
	/*     */boolean unmarshalOneRow()
	/*     */throws SQLException, IOException
	/*     */{
		/* 138 */if (this.isUseLess)
		/*     */{
			/* 140 */this.lastRowProcessed += 1;
			/*     */
			/* 142 */return false;
			/*     */}
		/*     */
		/* 147 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 151 */byte[] arrayOfByte = new byte[16000];
			/*     */
			/* 153 */this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
			/* 154 */processIndicator(this.meta[0]);
			/*     */
			/* 156 */this.lastRowProcessed += 1;
			/*     */
			/* 158 */return false;
			/*     */}
		/*     */
		/* 162 */int i = this.indicatorIndex + this.lastRowProcessed;
		/* 163 */int j = this.lengthIndex + this.lastRowProcessed;
		/*     */
		/* 167 */if (this.isNullByDescribe)
		/*     */{
			/* 169 */this.rowSpaceIndicator[i] = -1;
			/* 170 */this.rowSpaceIndicator[j] = 0;
			/* 171 */this.lastRowProcessed += 1;
			/*     */
			/* 173 */if (this.mare.versionNumber < 9200) {
				/* 174 */processIndicator(0);
				/*     */}
			/*     */
			/* 179 */return false;
			/*     */}
		/*     */
		/* 182 */int k = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/*     */
		/* 186 */this.mare.unmarshalCLR(this.rowSpaceByte, k + 1, this.meta,
				this.byteLength - 1);
		/*     */
		/* 190 */this.rowSpaceByte[k] = (byte) this.meta[0];
		/*     */
		/* 199 */processIndicator(this.meta[0]);
		/*     */
		/* 201 */if (this.meta[0] == 0)
		/*     */{
			/* 205 */this.rowSpaceIndicator[i] = -1;
			/* 206 */this.rowSpaceIndicator[j] = 0;
			/*     */}
		/*     */else
		/*     */{
			/* 210 */this.rowSpaceIndicator[j] = (short) this.meta[0];
			/*     */
			/* 215 */this.rowSpaceIndicator[i] = 0;
			/*     */}
		/*     */
		/* 218 */this.lastRowProcessed += 1;
		/*     */
		/* 222 */return false;
		/*     */}

	/*     */
	/*     */void copyRow()
	/*     */throws SQLException, IOException
	/*     */{
		/*     */int i;
		/* 229 */if (this.lastRowProcessed == 0)
			/* 230 */i = this.statement.rowPrefetch;
		/*     */else {
			/* 232 */i = this.lastRowProcessed;
			/*     */}
		/* 234 */int j = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/* 235 */int k = this.columnIndex + (i - 1) * this.byteLength;
		/*     */
		/* 237 */int m = this.indicatorIndex + this.lastRowProcessed;
		/* 238 */int n = this.indicatorIndex + i - 1;
		/* 239 */int i1 = this.lengthIndex + this.lastRowProcessed;
		/* 240 */int i2 = this.lengthIndex + i - 1;
		/* 241 */int i3 = this.rowSpaceIndicator[i2];
		/*     */
		/* 243 */this.rowSpaceIndicator[i1] = (short) i3;
		/* 244 */this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
		/*     */
		/* 247 */System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j,
				i3 + 1);
		/*     */
		/* 250 */this.lastRowProcessed += 1;
		/*     */}

	/*     */
	/*     */void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte,
			char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 260 */int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
		/*     */
		/* 262 */int j = this.columnIndexLastRow + (paramInt1 - 1)
				* this.byteLength;
		/*     */
		/* 264 */int k = this.indicatorIndex + paramInt2 - 1;
		/* 265 */int m = this.indicatorIndexLastRow + paramInt1 - 1;
		/* 266 */int n = this.lengthIndex + paramInt2 - 1;
		/* 267 */int i1 = this.lengthIndexLastRow + paramInt1 - 1;
		/* 268 */int i2 = paramArrayOfShort[i1];
		/*     */
		/* 270 */this.rowSpaceIndicator[n] = (short) i2;
		/* 271 */this.rowSpaceIndicator[k] = paramArrayOfShort[m];
		/*     */
		/* 274 */if (i2 != 0)
		/*     */{
			/* 276 */System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte,
					i, i2 + 1);
			/*     */}
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 287 */if (this.definedColumnType == 0) {
			/* 288 */return super.getObject(paramInt);
			/*     */}
		/*     */
		/* 291 */Object localObject = null;
		/*     */
		/* 293 */if (this.rowSpaceIndicator == null) {
			/* 294 */DatabaseError.throwSqlException(21);
			/*     */}
		/* 296 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 298 */switch (this.definedColumnType)
			/*     */{
			/*     */case -1:
				/*     */
			case 1:
				/*     */
			case 12:
				/* 306 */return getString(paramInt);
				/*     */case 2:
				/*     */
			case 3:
				/* 311 */return getBigDecimal(paramInt);
				/*     */case 4:
				/* 314 */return new Integer(getInt(paramInt));
				/*     */case -6:
				/* 317 */return new Byte(getByte(paramInt));
				/*     */case 5:
				/* 320 */return new Short(getShort(paramInt));
				/*     */case -7:
				/*     */
			case 16:
				/* 325 */return new Boolean(getBoolean(paramInt));
				/*     */case -5:
				/* 328 */return new Long(getLong(paramInt));
				/*     */case 7:
				/* 331 */return new Float(getFloat(paramInt));
				/*     */case 6:
				/*     */
			case 8:
				/* 336 */return new Double(getDouble(paramInt));
				/*     */case 91:
				/* 339 */return getDate(paramInt);
				/*     */case 92:
				/* 342 */return getTime(paramInt);
				/*     */case 93:
				/* 345 */return getTimestamp(paramInt);
				/*     */case -4:
				/*     */
			case -3:
				/*     */
			case -2:
				/* 352 */return getBytes(paramInt);
				/*     */}
			/*     */
			/* 355 */DatabaseError.throwSqlException(4);
			/*     */
			/* 357 */return null;
			/*     */}
		/*     */
		/* 361 */return localObject;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.T4CNumberAccessor JD-Core Version: 0.6.0
 */