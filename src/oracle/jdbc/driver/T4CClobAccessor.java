/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CClobAccessor extends ClobAccessor
/*     */{
	/*     */T4CMAREngine mare;
	/* 114 */final int[] meta = new int[1];
	/*     */
	/* 342 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*     */
	/*     */T4CClobAccessor(OracleStatement paramOracleStatement, int paramInt1,
			short paramShort, int paramInt2, boolean paramBoolean,
			T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 37 */super(paramOracleStatement, 4000, paramShort, paramInt2,
				paramBoolean);
		/*     */
		/* 39 */this.mare = paramT4CMAREngine;
		/*     */}

	/*     */
	/*     */T4CClobAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort, int paramInt7,
			int paramInt8, T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 51 */super(paramOracleStatement, 4000, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
		/*     */
		/* 55 */this.mare = paramT4CMAREngine;
		/* 56 */this.definedColumnType = paramInt7;
		/* 57 */this.definedColumnSize = paramInt8;
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
		/* 131 */if (this.isUseLess)
		/*     */{
			/* 133 */this.lastRowProcessed += 1;
			/*     */
			/* 135 */return false;
			/*     */}
		/*     */
		/* 140 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 142 */i = (int) this.mare.unmarshalUB4();
			/*     */
			/* 144 */if (i == 0)
			/*     */{
				/* 146 */this.meta[0] = -1;
				/*     */
				/* 148 */processIndicator(0);
				/*     */
				/* 150 */this.lastRowProcessed += 1;
				/*     */
				/* 152 */return false;
				/*     */}
			/*     */
			/* 156 */byte[] arrayOfByte = new byte[16000];
			/*     */
			/* 158 */this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
			/* 159 */processIndicator(this.meta[0]);
			/*     */
			/* 161 */this.lastRowProcessed += 1;
			/*     */
			/* 163 */return false;
			/*     */}
		/*     */
		/* 166 */int i = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/* 167 */int j = this.indicatorIndex + this.lastRowProcessed;
		/* 168 */int k = this.lengthIndex + this.lastRowProcessed;
		/*     */
		/* 172 */if (this.isNullByDescribe)
		/*     */{
			/* 174 */this.rowSpaceIndicator[j] = -1;
			/* 175 */this.rowSpaceIndicator[k] = 0;
			/* 176 */this.lastRowProcessed += 1;
			/*     */
			/* 178 */if (this.mare.versionNumber < 9200) {
				/* 179 */processIndicator(0);
				/*     */}
			/*     */
			/* 184 */return false;
			/*     */}
		/*     */
		/* 187 */int m = (int) this.mare.unmarshalUB4();
		/*     */
		/* 189 */if (m == 0)
		/*     */{
			/* 191 */this.meta[0] = -1;
			/*     */
			/* 193 */processIndicator(0);
			/*     */
			/* 196 */this.rowSpaceIndicator[j] = -1;
			/* 197 */this.rowSpaceIndicator[k] = 0;
			/*     */
			/* 201 */this.lastRowProcessed += 1;
			/*     */
			/* 203 */return false;
			/*     */}
		/*     */
		/* 206 */this.mare.unmarshalCLR(this.rowSpaceByte, i, this.meta,
				this.byteLength);
		/*     */
		/* 209 */processIndicator(this.meta[0]);
		/*     */
		/* 214 */if (this.meta[0] == 0)
		/*     */{
			/* 218 */this.rowSpaceIndicator[j] = -1;
			/* 219 */this.rowSpaceIndicator[k] = 0;
			/*     */}
		/*     */else
		/*     */{
			/* 223 */this.rowSpaceIndicator[k] = (short) this.meta[0];
			/* 224 */this.rowSpaceIndicator[j] = 0;
			/*     */}
		/*     */
		/* 227 */this.lastRowProcessed += 1;
		/*     */
		/* 231 */return false;
		/*     */}

	/*     */
	/*     */void copyRow()
	/*     */throws SQLException, IOException
	/*     */{
		/*     */int i;
		/* 238 */if (this.lastRowProcessed == 0)
			/* 239 */i = this.statement.rowPrefetch;
		/*     */else {
			/* 241 */i = this.lastRowProcessed;
			/*     */}
		/* 243 */int j = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/* 244 */int k = this.columnIndex + (i - 1) * this.byteLength;
		/*     */
		/* 246 */int m = this.indicatorIndex + this.lastRowProcessed;
		/* 247 */int n = this.indicatorIndex + i - 1;
		/* 248 */int i1 = this.lengthIndex + this.lastRowProcessed;
		/* 249 */int i2 = this.lengthIndex + i - 1;
		/* 250 */int i3 = this.rowSpaceIndicator[i2];
		/*     */
		/* 252 */this.rowSpaceIndicator[i1] = (short) i3;
		/* 253 */this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
		/*     */
		/* 256 */System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j,
				i3);
		/*     */
		/* 259 */this.lastRowProcessed += 1;
		/*     */}

	/*     */
	/*     */void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte,
			char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 269 */int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
		/*     */
		/* 271 */int j = this.columnIndexLastRow + (paramInt1 - 1)
				* this.byteLength;
		/*     */
		/* 273 */int k = this.indicatorIndex + paramInt2 - 1;
		/* 274 */int m = this.indicatorIndexLastRow + paramInt1 - 1;
		/* 275 */int n = this.lengthIndex + paramInt2 - 1;
		/* 276 */int i1 = this.lengthIndexLastRow + paramInt1 - 1;
		/* 277 */int i2 = paramArrayOfShort[i1];
		/*     */
		/* 279 */this.rowSpaceIndicator[n] = (short) i2;
		/* 280 */this.rowSpaceIndicator[k] = paramArrayOfShort[m];
		/*     */
		/* 283 */if (i2 != 0)
		/*     */{
			/* 285 */System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte,
					i, i2);
			/*     */}
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 298 */if (this.definedColumnType == 0) {
			/* 299 */return super.getObject(paramInt);
			/*     */}
		/*     */
		/* 302 */Object localObject = null;
		/*     */
		/* 304 */if (this.rowSpaceIndicator == null) {
			/* 305 */DatabaseError.throwSqlException(21);
			/*     */}
		/* 307 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 309 */switch (this.definedColumnType)
			/*     */{
			/*     */case 2005:
				/* 313 */return getCLOB(paramInt);
				/*     */case -1:
				/*     */
			case 1:
				/*     */
			case 12:
				/* 320 */return getString(paramInt);
				/*     */case -4:
				/*     */
			case -3:
				/*     */
			case -2:
				/* 327 */return getBytes(paramInt);
				/*     */}
			/*     */
			/* 330 */DatabaseError.throwSqlException(4);
			/*     */
			/* 332 */return null;
			/*     */}
		/*     */
		/* 336 */return localObject;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CClobAccessor
 * JD-Core Version: 0.6.0
 */