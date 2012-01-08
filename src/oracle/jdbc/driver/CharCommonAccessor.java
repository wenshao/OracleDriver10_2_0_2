package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import oracle.sql.CHAR;
import oracle.sql.CharacterSet;
import oracle.sql.Datum;

abstract class CharCommonAccessor extends Accessor {
	int internalMaxLengthNewer;
	int internalMaxLengthOlder;
	static final int MAX_NB_CHAR_PLSQL = 32512;

	void setOffsets(int paramInt) {
		/* 41 */this.columnIndex = this.statement.defineCharSubRange;
		/* 42 */this.statement.defineCharSubRange = (this.columnIndex + paramInt * this.charLength);
	}

	void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, short paramShort, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6)
			throws SQLException {
		/* 49 */if (paramBoolean) {
			/* 51 */if (paramInt1 != 23) {
				/* 52 */paramInt1 = 1;
			}
			/* 54 */if ((paramInt3 == -1) || (paramInt3 < paramOracleStatement.maxFieldSize)) {
				/* 55 */paramInt3 = paramOracleStatement.maxFieldSize;
			}
		}
		/* 58 */init(paramOracleStatement, paramInt1, paramInt2, paramShort, paramBoolean);

		/* 61 */if ((paramBoolean) && (paramOracleStatement.connection.defaultNChar)) {
			/* 62 */this.formOfUse = 2;
		}
		/* 64 */this.internalMaxLengthNewer = paramInt5;
		/* 65 */this.internalMaxLengthOlder = paramInt6;

		/* 67 */initForDataAccess(paramInt4, paramInt3, null);
	}

	void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5, int paramInt6, int paramInt7,
			int paramInt8, short paramShort, int paramInt9, int paramInt10) throws SQLException {
		/* 75 */init(paramOracleStatement, paramInt1, paramInt2, paramShort, false);
		/* 76 */initForDescribe(paramInt1, paramInt3, paramBoolean, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramShort, null);

		/* 79 */int i = paramOracleStatement.maxFieldSize;

		/* 81 */if ((i != 0) && (i <= paramInt3)) {
			/* 82 */paramInt3 = i;
		}
		/* 84 */this.internalMaxLengthNewer = paramInt9;
		/* 85 */this.internalMaxLengthOlder = paramInt10;

		/* 87 */initForDataAccess(0, paramInt3, null);
	}

	void initForDataAccess(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 93 */if (paramInt1 != 0) {
			/* 94 */this.externalType = paramInt1;
		}
		/* 96 */if (this.statement.connection.getVersionNumber() >= 8000)
			/* 97 */this.internalTypeMaxLength = this.internalMaxLengthNewer;
		else {
			/* 99 */this.internalTypeMaxLength = this.internalMaxLengthOlder;
		}
		/* 101 */if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
			/* 102 */this.internalTypeMaxLength = paramInt2;
		}
		/* 104 */this.charLength = (this.internalTypeMaxLength + 1);
	}

	int getInt(int paramInt) throws SQLException {
		/* 110 */int i = 0;

		/* 112 */if (this.rowSpaceIndicator == null) {
			/* 117 */DatabaseError.throwSqlException(21);
		}

		/* 124 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 128 */i = Integer.parseInt(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 132 */DatabaseError.throwSqlException(59);
			}

		}

		/* 137 */return i;
	}

	boolean getBoolean(int paramInt) throws SQLException {
		/* 143 */BigDecimal localBigDecimal = getBigDecimal(paramInt);

		/* 145 */return (localBigDecimal != null) && (localBigDecimal.signum() != 0);
	}

	short getShort(int paramInt) throws SQLException {
		/* 151 */int i = 0;

		/* 153 */if (this.rowSpaceIndicator == null) {
			/* 158 */DatabaseError.throwSqlException(21);
		}

		/* 165 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 169 */i = Short.parseShort(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 173 */DatabaseError.throwSqlException(59);
			}

		}

		/* 178 */return (short) i;
	}

	byte getByte(int paramInt) throws SQLException {
		/* 184 */int i = 0;

		/* 186 */if (this.rowSpaceIndicator == null) {
			/* 191 */DatabaseError.throwSqlException(21);
		}

		/* 198 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 202 */i = Byte.parseByte(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 206 */DatabaseError.throwSqlException(59);
			}

		}

		/* 211 */return (byte) i;
	}

	long getLong(int paramInt) throws SQLException {
		/* 217 */long l = 0L;

		/* 219 */if (this.rowSpaceIndicator == null) {
			/* 224 */DatabaseError.throwSqlException(21);
		}

		/* 231 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 235 */l = Long.parseLong(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 239 */DatabaseError.throwSqlException(59);
			}

		}

		/* 244 */return l;
	}

	float getFloat(int paramInt) throws SQLException {
		/* 250 */float f = 0.0F;

		/* 252 */if (this.rowSpaceIndicator == null) {
			/* 257 */DatabaseError.throwSqlException(21);
		}

		/* 264 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 268 */f = Float.parseFloat(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 272 */DatabaseError.throwSqlException(59);
			}

		}

		/* 277 */return f;
	}

	double getDouble(int paramInt) throws SQLException {
		/* 283 */double d = 0.0D;

		/* 285 */if (this.rowSpaceIndicator == null) {
			/* 290 */DatabaseError.throwSqlException(21);
		}

		/* 297 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 301 */d = Double.parseDouble(getString(paramInt).trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 305 */DatabaseError.throwSqlException(59);
			}

		}

		/* 310 */return d;
	}

	BigDecimal getBigDecimal(int paramInt) throws SQLException {
		/* 316 */BigDecimal localBigDecimal = null;

		/* 318 */if (this.rowSpaceIndicator == null) {
			/* 323 */DatabaseError.throwSqlException(21);
		}

		/* 330 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 334 */String str = getString(paramInt);

				/* 336 */if (str != null)
					/* 337 */localBigDecimal = new BigDecimal(str.trim());
			} catch (NumberFormatException localNumberFormatException) {
				/* 341 */DatabaseError.throwSqlException(59);
			}

		}

		/* 346 */return localBigDecimal;
	}

	BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
		/* 352 */BigDecimal localBigDecimal = getBigDecimal(paramInt1);

		/* 354 */if (localBigDecimal != null) {
			/* 355 */localBigDecimal.setScale(paramInt2, 6);
		}
		/* 357 */return localBigDecimal;
	}

	String getString(int paramInt) throws SQLException {
		/* 363 */String str = null;

		/* 365 */if (this.rowSpaceIndicator == null) {
			/* 370 */DatabaseError.throwSqlException(21);
		}

		/* 377 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 379 */int i = this.columnIndex + this.charLength * paramInt;
			/* 380 */int j = this.rowSpaceChar[i] >> '\001';

			/* 382 */if (j > this.internalTypeMaxLength) {
				/* 383 */j = this.internalTypeMaxLength;
			}
			/* 385 */str = new String(this.rowSpaceChar, i + 1, j);
		}

		/* 388 */return str;
	}

	Date getDate(int paramInt) throws SQLException {
		/* 394 */Date localDate = null;

		/* 396 */if (this.rowSpaceIndicator == null) {
			/* 401 */DatabaseError.throwSqlException(21);
		}

		/* 408 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 410 */localDate = Date.valueOf(getString(paramInt).trim());
		}

		/* 413 */return localDate;
	}

	Time getTime(int paramInt) throws SQLException {
		/* 419 */Time localTime = null;

		/* 421 */if (this.rowSpaceIndicator == null) {
			/* 426 */DatabaseError.throwSqlException(21);
		}

		/* 433 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 435 */localTime = Time.valueOf(getString(paramInt).trim());
		}

		/* 438 */return localTime;
	}

	Timestamp getTimestamp(int paramInt) throws SQLException {
		/* 444 */Timestamp localTimestamp = null;

		/* 446 */if (this.rowSpaceIndicator == null) {
			/* 451 */DatabaseError.throwSqlException(21);
		}

		/* 458 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 460 */localTimestamp = Timestamp.valueOf(getString(paramInt).trim());
		}

		/* 463 */return localTimestamp;
	}

	byte[] getBytes(int paramInt) throws SQLException {
		/* 475 */byte[] arrayOfByte1 = null;

		/* 477 */if (this.rowSpaceIndicator == null) {
			/* 482 */DatabaseError.throwSqlException(21);
		}

		/* 489 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 491 */int i = this.columnIndex + this.charLength * paramInt;
			/* 492 */int j = this.rowSpaceChar[i] >> '\001';

			/* 494 */if (j > this.internalTypeMaxLength) {
				/* 495 */j = this.internalTypeMaxLength;
			}
			/* 497 */DBConversion localDBConversion = this.statement.connection.conversion;

			/* 501 */byte[] arrayOfByte2 = new byte[j * 6];
			/* 502 */int k = this.formOfUse == 2 ? localDBConversion.javaCharsToNCHARBytes(this.rowSpaceChar, i + 1, arrayOfByte2, 0, j) : localDBConversion.javaCharsToCHARBytes(
					this.rowSpaceChar, i + 1, arrayOfByte2, 0, j);

			/* 508 */arrayOfByte1 = new byte[k];

			/* 510 */System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, k);
		}

		/* 513 */return arrayOfByte1;
	}

	InputStream getAsciiStream(int paramInt) throws SQLException {
		/* 528 */InputStream localInputStream = null;

		/* 530 */if (this.rowSpaceIndicator == null) {
			/* 535 */DatabaseError.throwSqlException(21);
		}

		/* 541 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 543 */int i = this.columnIndex + this.charLength * paramInt;
			/* 544 */int j = this.rowSpaceChar[i] >> '\001';

			/* 546 */if (j > this.internalTypeMaxLength) {
				/* 547 */j = this.internalTypeMaxLength;
			}
			/* 549 */PhysicalConnection localPhysicalConnection = this.statement.connection;

			/* 551 */localInputStream = localPhysicalConnection.conversion.CharsToStream(this.rowSpaceChar, i + 1, j, 10);
		}

		/* 555 */return localInputStream;
	}

	InputStream getUnicodeStream(int paramInt) throws SQLException {
		/* 567 */InputStream localInputStream = null;

		/* 569 */if (this.rowSpaceIndicator == null) {
			/* 574 */DatabaseError.throwSqlException(21);
		}

		/* 580 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 582 */int i = this.columnIndex + this.charLength * paramInt;
			/* 583 */int j = this.rowSpaceChar[i] >> '\001';

			/* 585 */if (j > this.internalTypeMaxLength) {
				/* 586 */j = this.internalTypeMaxLength;
			}
			/* 588 */PhysicalConnection localPhysicalConnection = this.statement.connection;

			/* 590 */localInputStream = localPhysicalConnection.conversion.CharsToStream(this.rowSpaceChar, i + 1, j << 1, 11);
		}

		/* 595 */return localInputStream;
	}

	Reader getCharacterStream(int paramInt) throws SQLException {
		/* 607 */CharArrayReader localCharArrayReader = null;
		/* 608 */if (this.rowSpaceIndicator == null) {
			/* 613 */DatabaseError.throwSqlException(21);
		}

		/* 619 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 621 */int i = this.columnIndex + this.charLength * paramInt;
			/* 622 */int j = this.rowSpaceChar[i] >> '\001';

			/* 624 */if (j > this.internalTypeMaxLength) {
				/* 625 */j = this.internalTypeMaxLength;
			}
			/* 627 */localCharArrayReader = new CharArrayReader(this.rowSpaceChar, i + 1, j);
		}
		/* 629 */return localCharArrayReader;
	}

	InputStream getBinaryStream(int paramInt) throws SQLException {
		/* 641 */ByteArrayInputStream localByteArrayInputStream = null;

		/* 643 */if (this.rowSpaceIndicator == null) {
			/* 648 */DatabaseError.throwSqlException(21);
		}

		/* 654 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 656 */int i = this.columnIndex + this.charLength * paramInt;
			/* 657 */int j = this.rowSpaceChar[i] >> '\001';

			/* 659 */if (j > this.internalTypeMaxLength) {
				/* 660 */j = this.internalTypeMaxLength;
			}
			/* 662 */DBConversion localDBConversion = this.statement.connection.conversion;

			/* 666 */byte[] arrayOfByte = new byte[j * 6];
			/* 667 */int k = this.formOfUse == 2 ? localDBConversion.javaCharsToNCHARBytes(this.rowSpaceChar, i + 1, arrayOfByte, 0, j) : localDBConversion.javaCharsToCHARBytes(
					this.rowSpaceChar, i + 1, arrayOfByte, 0, j);

			/* 673 */localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte, 0, k);
		}

		/* 676 */return localByteArrayInputStream;
	}

	Object getObject(int paramInt) throws SQLException {
		/* 688 */return getString(paramInt);
	}

	Object getObject(int paramInt, Map paramMap) throws SQLException {
		/* 701 */return getString(paramInt);
	}

	Datum getOracleObject(int paramInt) throws SQLException {
		/* 713 */return getCHAR(paramInt);
	}

	CHAR getCHAR(int paramInt) throws SQLException {
		/* 725 */byte[] arrayOfByte = getBytes(paramInt);

		/* 728 */if ((arrayOfByte == null) || (arrayOfByte.length == 0)) {
			/* 730 */return null;
		}
		CharacterSet localCharacterSet;
		/* 733 */if (this.formOfUse == 2) {
			/* 735 */localCharacterSet = this.statement.connection.conversion.getDriverNCharSetObj();
		} else {
			/* 739 */localCharacterSet = this.statement.connection.conversion.getDriverCharSetObj();
		}

		/* 742 */return new CHAR(arrayOfByte, localCharacterSet);
	}

	URL getURL(int paramInt) throws SQLException {
		/* 754 */URL localURL = null;

		/* 756 */if (this.rowSpaceIndicator == null) {
			/* 761 */DatabaseError.throwSqlException(21);
		}

		/* 768 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			try {
				/* 772 */localURL = new URL(getString(paramInt));
			} catch (MalformedURLException localMalformedURLException) {
				/* 776 */DatabaseError.throwSqlException(136);
			}
		}

		/* 780 */return localURL;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.CharCommonAccessor JD-Core Version: 0.6.0
 */