/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CIntervaldsAccessor extends IntervaldsAccessor
/*     */{
	/*     */T4CMAREngine mare;
	/* 24 */static int maxLength = 11;
	/*     */
	/* 107 */final int[] meta = new int[1];
	/*     */
	/* 324 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*     */
	/*     */T4CIntervaldsAccessor(OracleStatement paramOracleStatement,
			int paramInt1, short paramShort, int paramInt2,
			boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 37 */super(paramOracleStatement, maxLength, paramShort, paramInt2,
				paramBoolean);
		/* 38 */this.mare = paramT4CMAREngine;
		/*     */}

	/*     */
	/*     */T4CIntervaldsAccessor(OracleStatement paramOracleStatement,
			int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5, int paramInt6, short paramShort,
			int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
	/*     */throws SQLException
	/*     */{
		/* 57 */super(paramOracleStatement, maxLength, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
		/*     */
		/* 66 */this.mare = paramT4CMAREngine;
		/* 67 */this.definedColumnType = paramInt7;
		/* 68 */this.definedColumnSize = paramInt8;
		/*     */}

	/*     */
	/*     */void processIndicator(int paramInt)
	/*     */throws IOException, SQLException
	/*     */{
		/* 77 */if (((this.internalType == 1) && (this.describeType == 112))
				|| ((this.internalType == 23) && (this.describeType == 113)))
		/*     */{
			/* 84 */this.mare.unmarshalUB2();
			/* 85 */this.mare.unmarshalUB2();
			/*     */}
		/* 87 */else if (this.mare.versionNumber < 9200)
		/*     */{
			/* 91 */this.mare.unmarshalSB2();
			/*     */
			/* 93 */if ((this.statement.sqlKind != 1)
					&& (this.statement.sqlKind != 4))
			/*     */{
				/* 95 */this.mare.unmarshalSB2();
				/*     */}
			/* 97 */} else if ((this.statement.sqlKind == 1)
				|| (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
		/*     */{
			/* 100 */this.mare.processIndicator(paramInt <= 0, paramInt);
			/*     */}
		/*     */}

	/*     */
	/*     */boolean unmarshalOneRow()
	/*     */throws SQLException, IOException
	/*     */{
		/* 125 */if (this.isUseLess)
		/*     */{
			/* 127 */this.lastRowProcessed += 1;
			/*     */
			/* 129 */return false;
			/*     */}
		/*     */
		/* 134 */if (this.rowSpaceIndicator == null)
		/*     */{
			/* 138 */byte[] arrayOfByte = new byte[16000];
			/*     */
			/* 140 */this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
			/* 141 */processIndicator(this.meta[0]);
			/*     */
			/* 143 */this.lastRowProcessed += 1;
			/*     */
			/* 145 */return false;
			/*     */}
		/*     */
		/* 149 */int i = this.indicatorIndex + this.lastRowProcessed;
		/* 150 */int j = this.lengthIndex + this.lastRowProcessed;
		/*     */
		/* 154 */if (this.isNullByDescribe)
		/*     */{
			/* 156 */this.rowSpaceIndicator[i] = -1;
			/* 157 */this.rowSpaceIndicator[j] = 0;
			/* 158 */this.lastRowProcessed += 1;
			/*     */
			/* 160 */if (this.mare.versionNumber < 9200) {
				/* 161 */processIndicator(0);
				/*     */}
			/*     */
			/* 166 */return false;
			/*     */}
		/*     */
		/* 169 */int k = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/*     */
		/* 173 */this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta,
				this.byteLength);
		/*     */
		/* 178 */processIndicator(this.meta[0]);
		/*     */
		/* 180 */if (this.meta[0] == 0)
		/*     */{
			/* 184 */this.rowSpaceIndicator[i] = -1;
			/* 185 */this.rowSpaceIndicator[j] = 0;
			/*     */}
		/*     */else
		/*     */{
			/* 189 */this.rowSpaceIndicator[j] = (short) this.meta[0];
			/* 190 */this.rowSpaceIndicator[i] = 0;
			/*     */}
		/*     */
		/* 193 */this.lastRowProcessed += 1;
		/*     */
		/* 197 */return false;
		/*     */}

	/*     */
	/*     */void copyRow()
	/*     */throws SQLException, IOException
	/*     */{
		/*     */int i;
		/* 204 */if (this.lastRowProcessed == 0)
			/* 205 */i = this.statement.rowPrefetch;
		/*     */else {
			/* 207 */i = this.lastRowProcessed;
			/*     */}
		/* 209 */int j = this.columnIndex + this.lastRowProcessed
				* this.byteLength;
		/* 210 */int k = this.columnIndex + (i - 1) * this.byteLength;
		/*     */
		/* 212 */int m = this.indicatorIndex + this.lastRowProcessed;
		/* 213 */int n = this.indicatorIndex + i - 1;
		/* 214 */int i1 = this.lengthIndex + this.lastRowProcessed;
		/* 215 */int i2 = this.lengthIndex + i - 1;
		/* 216 */int i3 = this.rowSpaceIndicator[i2];
		/*     */
		/* 218 */this.rowSpaceIndicator[i1] = (short) i3;
		/* 219 */this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
		/*     */
		/* 222 */System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j,
				i3);
		/*     */
		/* 225 */this.lastRowProcessed += 1;
		/*     */}

	/*     */
	/*     */void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte,
			char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1,
			int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 235 */int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
		/*     */
		/* 237 */int j = this.columnIndexLastRow + (paramInt1 - 1)
				* this.byteLength;
		/*     */
		/* 239 */int k = this.indicatorIndex + paramInt2 - 1;
		/* 240 */int m = this.indicatorIndexLastRow + paramInt1 - 1;
		/* 241 */int n = this.lengthIndex + paramInt2 - 1;
		/* 242 */int i1 = this.lengthIndexLastRow + paramInt1 - 1;
		/* 243 */int i2 = paramArrayOfShort[i1];
		/*     */
		/* 245 */this.rowSpaceIndicator[n] = (short) i2;
		/* 246 */this.rowSpaceIndicator[k] = paramArrayOfShort[m];
		/*     */
		/* 249 */if (i2 != 0)
		/*     */{
			/* 251 */System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte,
					i, i2);
			/*     */}
		/*     */}

	/*     */
	/*     */String getString(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 267 */String str = super.getString(paramInt);
		/*     */
		/* 269 */if ((str != null) && (this.definedColumnSize > 0)
				&& (str.length() > this.definedColumnSize))
		/*     */{
			/* 271 */str = str.substring(0, this.definedColumnSize);
			/*     */}
		/* 273 */return str;
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 280 */if (this.definedColumnType == 0) {
			/* 281 */return super.getObject(paramInt);
			/*     */}
		/*     */
		/* 284 */Object localObject = null;
		/*     */
		/* 286 */if (this.rowSpaceIndicator == null) {
			/* 287 */DatabaseError.throwSqlException(21);
			/*     */}
		/* 289 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 291 */switch (this.definedColumnType)
			/*     */{
			/*     */case -1:
				/*     */
			case 1:
				/*     */
			case 12:
				/* 299 */return getString(paramInt);
				/*     */case -104:
				/* 302 */return getINTERVALDS(paramInt);
				/*     */case -4:
				/*     */
			case -3:
				/*     */
			case -2:
				/* 309 */return getBytes(paramInt);
				/*     */}
			/*     */
			/* 312 */DatabaseError.throwSqlException(4);
			/*     */
			/* 314 */return null;
			/*     */}
		/*     */
		/* 318 */return localObject;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.T4CIntervaldsAccessor JD-Core Version: 0.6.0
 */