package oracle.jdbc.driver;

import java.math.BigDecimal;

import java.math.BigInteger;

import java.sql.SQLException;

import java.util.Map;

import oracle.sql.BINARY_DOUBLE;

import oracle.sql.Datum;

import oracle.sql.NUMBER;

class BinaryDoubleAccessor extends Accessor {
	static final int MAXLENGTH = 8;

	BinaryDoubleAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean) throws SQLException {
		/* 27 */init(paramOracleStatement, 101, 101, paramShort, paramBoolean);
		/* 28 */initForDataAccess(paramInt2, paramInt1, null);
	}

	BinaryDoubleAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6,
			short paramShort) throws SQLException {
		/* 35 */init(paramOracleStatement, 101, 101, paramShort, false);
		/* 36 */initForDescribe(101, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);

		/* 39 */int i = paramOracleStatement.maxFieldSize;

		/* 41 */if ((i > 0) && ((paramInt1 == 0) || (i < paramInt1))) {
			/* 42 */paramInt1 = i;
		}
		/* 44 */initForDataAccess(0, paramInt1, null);
	}

	void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, short paramShort, int paramInt4) throws SQLException {
		/* 50 */init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
		/* 51 */initForDataAccess(paramInt4, paramInt3, null);
	}

	void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6, int paramInt7,
			int paramInt8, short paramShort) throws SQLException {
		/* 58 */init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
		/* 59 */initForDescribe(paramInt1, paramInt3, paramBoolean, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramShort, null);

		/* 62 */int i = paramOracleStatement.maxFieldSize;

		/* 64 */if ((i > 0) && ((paramInt3 == 0) || (i < paramInt3))) {
			/* 65 */paramInt3 = i;
		}
		/* 67 */initForDataAccess(0, paramInt3, null);
	}

	void initForDataAccess(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 73 */if (paramInt1 != 0) {
			/* 74 */this.externalType = paramInt1;
		}
		/* 76 */this.internalTypeMaxLength = 8;

		/* 78 */if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
			/* 79 */this.internalTypeMaxLength = paramInt2;
		}
		/* 81 */this.byteLength = this.internalTypeMaxLength;
	}

	double getDouble(int paramInt) throws SQLException {
		/* 112 */if (this.rowSpaceIndicator == null) {
			/* 117 */DatabaseError.throwSqlException(21);
		}

		/* 123 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 124 */return 0.0D;
		}
		/* 126 */int i = this.columnIndex + this.byteLength * paramInt;
		/* 127 */int j = this.rowSpaceByte[i];
		/* 128 */int k = this.rowSpaceByte[(i + 1)];
		/* 129 */int m = this.rowSpaceByte[(i + 2)];
		/* 130 */int n = this.rowSpaceByte[(i + 3)];
		/* 131 */int i1 = this.rowSpaceByte[(i + 4)];
		/* 132 */int i2 = this.rowSpaceByte[(i + 5)];
		/* 133 */int i3 = this.rowSpaceByte[(i + 6)];
		/* 134 */int i4 = this.rowSpaceByte[(i + 7)];

		/* 136 */if ((j & 0x80) != 0) {
			/* 138 */j &= 127;
			/* 139 */k &= 255;
			/* 140 */m &= 255;
			/* 141 */n &= 255;
			/* 142 */i1 &= 255;
			/* 143 */i2 &= 255;
			/* 144 */i3 &= 255;
			/* 145 */i4 &= 255;
		} else {
			/* 149 */j = (j ^ 0xFFFFFFFF) & 0xFF;
			/* 150 */k = (k ^ 0xFFFFFFFF) & 0xFF;
			/* 151 */m = (m ^ 0xFFFFFFFF) & 0xFF;
			/* 152 */n = (n ^ 0xFFFFFFFF) & 0xFF;
			/* 153 */i1 = (i1 ^ 0xFFFFFFFF) & 0xFF;
			/* 154 */i2 = (i2 ^ 0xFFFFFFFF) & 0xFF;
			/* 155 */i3 = (i3 ^ 0xFFFFFFFF) & 0xFF;
			/* 156 */i4 = (i4 ^ 0xFFFFFFFF) & 0xFF;
		}

		/* 159 */int i5 = j << 24 | k << 16 | m << 8 | n;
		/* 160 */int i6 = i1 << 24 | i2 << 16 | i3 << 8 | i4;
		/* 161 */long l = i5 << 32 | i6 & 0xFFFFFFFF;

		/* 163 */return Double.longBitsToDouble(l);
	}

	String getString(int paramInt) throws SQLException {
		/* 175 */if (this.rowSpaceIndicator == null) {
			/* 180 */DatabaseError.throwSqlException(21);
		}

		/* 186 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 187 */return Double.toString(getDouble(paramInt));
		}
		/* 189 */return null;
	}

	Object getObject(int paramInt) throws SQLException {
		/* 201 */if (this.rowSpaceIndicator == null) {
			/* 206 */DatabaseError.throwSqlException(21);
		}

		/* 212 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 213 */return new Double(getDouble(paramInt));
		}
		/* 215 */return null;
	}

	Object getObject(int paramInt, Map paramMap) throws SQLException {
		/* 228 */return new Double(getDouble(paramInt));
	}

	Datum getOracleObject(int paramInt) throws SQLException {
		/* 240 */return getBINARY_DOUBLE(paramInt);
	}

	BINARY_DOUBLE getBINARY_DOUBLE(int paramInt) throws SQLException {
		/* 252 */BINARY_DOUBLE localBINARY_DOUBLE = null;

		/* 254 */if (this.rowSpaceIndicator == null) {
			/* 259 */DatabaseError.throwSqlException(21);
		}

		/* 265 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 267 */int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
			/* 268 */int j = this.columnIndex + this.byteLength * paramInt;
			/* 269 */byte[] arrayOfByte = new byte[i];

			/* 271 */System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);

			/* 273 */localBINARY_DOUBLE = new BINARY_DOUBLE(arrayOfByte);
		}

		/* 276 */return localBINARY_DOUBLE;
	}

	NUMBER getNUMBER(int paramInt) throws SQLException {
		/* 281 */if (this.rowSpaceIndicator == null) {
			/* 286 */DatabaseError.throwSqlException(21);
		}

		/* 292 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 293 */return new NUMBER(getDouble(paramInt));
		}
		/* 295 */return null;
	}

	BigInteger getBigInteger(int paramInt) throws SQLException {
		/* 300 */if (this.rowSpaceIndicator == null) {
			/* 305 */DatabaseError.throwSqlException(21);
		}

		/* 311 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 312 */return new BigInteger(getString(paramInt));
		}
		/* 314 */return null;
	}

	BigDecimal getBigDecimal(int paramInt) throws SQLException {
		/* 319 */if (this.rowSpaceIndicator == null) {
			/* 324 */DatabaseError.throwSqlException(21);
		}

		/* 330 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 331 */return new BigDecimal(getString(paramInt));
		}
		/* 333 */return null;
	}

	byte getByte(int paramInt) throws SQLException {
		/* 338 */return (byte) (int) getDouble(paramInt);
	}

	short getShort(int paramInt) throws SQLException {
		/* 343 */return (short) (int) getDouble(paramInt);
	}

	int getInt(int paramInt) throws SQLException {
		/* 348 */return (int) getDouble(paramInt);
	}

	long getLong(int paramInt) throws SQLException {
		/* 353 */return (long) getDouble(paramInt);
	}

	float getFloat(int paramInt) throws SQLException {
		/* 358 */return (float) getDouble(paramInt);
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.BinaryDoubleAccessor JD-Core Version: 0.6.0
 */