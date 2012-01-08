package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import oracle.jdbc.oracore.OracleType;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

abstract class Accessor {
	static final int FIXED_CHAR = 999;
	static final int CHAR = 96;
	static final int VARCHAR = 1;
	static final int VCS = 9;
	static final int LONG = 8;
	static final int NUMBER = 2;
	static final int VARNUM = 6;
	static final int BINARY_FLOAT = 100;
	static final int BINARY_DOUBLE = 101;
	static final int RAW = 23;
	static final int VBI = 15;
	static final int LONG_RAW = 24;
	static final int ROWID = 104;
	static final int ROWID_THIN = 11;
	static final int RESULT_SET = 102;
	static final int RSET = 116;
	static final int DATE = 12;
	static final int BLOB = 113;
	static final int CLOB = 112;
	static final int BFILE = 114;
	static final int NAMED_TYPE = 109;
	static final int REF_TYPE = 111;
	static final int TIMESTAMP = 180;
	static final int TIMESTAMPTZ = 181;
	static final int TIMESTAMPLTZ = 231;
	static final int INTERVALYM = 182;
	static final int INTERVALDS = 183;
	static final int UROWID = 208;
	static final int PLSQL_INDEX_TABLE = 998;
	static final int T2S_OVERLONG_RAW = 997;
	static final int SET_CHAR_BYTES = 996;
	static final int NULL_TYPE = 995;
	static final int DML_RETURN_PARAM = 994;
	static final int ONLY_FORM_USABLE = 0;
	static final int NOT_USABLE = 1;
	static final int NO_NEED_TO_PREPARE = 2;
	static final int NEED_TO_PREPARE = 3;
	OracleStatement statement;
	boolean outBind;
	int internalType;
	int internalTypeMaxLength;
	boolean isStream = false;

	boolean isColumnNumberAware = false;

	short formOfUse = 2;
	OracleType internalOtype;
	int externalType;
	String internalTypeName;
	String columnName;
	int describeType;
	int describeMaxLength;
	boolean nullable;
	int precision;
	int scale;
	int flags;
	int contflag;
	int total_elems;
	OracleType describeOtype;
	String describeTypeName;
	/* 166 */int definedColumnType = 0;
	/* 167 */int definedColumnSize = 0;
	/* 168 */int oacmxl = 0;

	byte[] rowSpaceByte = null;
	char[] rowSpaceChar = null;
	/* 177 */short[] rowSpaceIndicator = null;
	/* 178 */int columnIndex = 0;
	/* 179 */int lengthIndex = 0;
	/* 180 */int indicatorIndex = 0;
	/* 181 */int columnIndexLastRow = 0;
	/* 182 */int lengthIndexLastRow = 0;
	/* 183 */int indicatorIndexLastRow = 0;
	/* 184 */int byteLength = 0;
	/* 185 */int charLength = 0;
	int defineType;
	boolean isDMLReturnedParam = false;

	int lastRowProcessed = 0;

	boolean isUseLess = false;

	int physicalColumnIndex = -2;

	boolean isNullByDescribe = false;

	void setOffsets(int paramInt) {
		this.columnIndex = this.statement.defineByteSubRange;
		this.statement.defineByteSubRange = (this.columnIndex + paramInt * this.byteLength);
	}

	void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, boolean paramBoolean) throws SQLException {
		/* 208 */this.statement = paramOracleStatement;
		/* 209 */this.outBind = paramBoolean;
		/* 210 */this.internalType = paramInt1;
		/* 211 */this.defineType = paramInt2;
		/* 212 */this.formOfUse = paramShort;
	}

	abstract void initForDataAccess(int paramInt1, int paramInt2, String paramString) throws SQLException;

	void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
			throws SQLException {
		/* 222 */this.describeType = paramInt1;
		/* 223 */this.describeMaxLength = paramInt2;
		/* 224 */this.nullable = paramBoolean;
		/* 225 */this.precision = paramInt4;
		/* 226 */this.scale = paramInt5;
		/* 227 */this.flags = paramInt3;
		/* 228 */this.contflag = paramInt6;
		/* 229 */this.total_elems = paramInt7;
		/* 230 */this.formOfUse = paramShort;
	}

	void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort,
			String paramString) throws SQLException {
		/* 237 */this.describeTypeName = paramString;
		/* 238 */this.describeOtype = null;

		/* 240 */initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort);
	}

	OracleInputStream initForNewRow() throws SQLException {
		/* 254 */unimpl("initForNewRow");
		/* 255 */return null;
	}

	int useForDataAccessIfPossible(int paramInt1, int paramInt2, int paramInt3, String paramString) throws SQLException {
		/* 270 */int i = 3;
		/* 271 */int j = 0;
		/* 272 */int k = 0;

		/* 274 */if (this.internalType != 0) {
			/* 276 */if (this.internalType != paramInt1) {
				/* 277 */i = 0;
				/* 278 */} else if (this.rowSpaceIndicator != null) {
				/* 280 */j = this.byteLength;
				/* 281 */k = this.charLength;
			}
		}

		/* 285 */if (i == 3) {
			/* 287 */initForDataAccess(paramInt2, paramInt3, paramString);

			/* 289 */if ((!this.outBind) && (j >= this.byteLength) && (k >= this.charLength)) {
				/* 291 */i = 2;
			}
		}
		/* 294 */return i;
	}

	boolean useForDescribeIfPossible(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7,
			short paramShort, String paramString) throws SQLException {
		/* 304 */if ((this.externalType == 0) && (paramInt1 != this.describeType)) {
			/* 305 */return false;
		}
		/* 307 */initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, paramString);

		/* 310 */return true;
	}

	void setFormOfUse(short paramShort) {
		/* 317 */this.formOfUse = paramShort;
	}

	void updateColumnNumber(int paramInt) {
	}

	public String toString() {
		/* 334 */return super.toString() + ", statement=" + this.statement + ", outBind=" + this.outBind + ", internalType=" + this.internalType + ", internalTypeMaxLength="
				+ this.internalTypeMaxLength + ", isStream=" + this.isStream + ", formOfUse=" + this.formOfUse + ", internalOtype=" + this.internalOtype + ", externalType="
				+ this.externalType + ", internalTypeName=" + this.internalTypeName + ", columnName=" + this.columnName + ", describeType=" + this.describeType
				+ ", describeMaxLength=" + this.describeMaxLength + ", nullable=" + this.nullable + ", precision=" + this.precision + ", scale=" + this.scale + ", flags="
				+ this.flags + ", contflag=" + this.contflag + ", total_elems=" + this.total_elems + ", describeOtype=" + this.describeOtype + ", describeTypeName="
				+ this.describeTypeName + ", rowSpaceByte=" + this.rowSpaceByte + ", rowSpaceChar=" + this.rowSpaceChar + ", rowSpaceIndicator=" + this.rowSpaceIndicator
				+ ", columnIndex=" + this.columnIndex + ", lengthIndex=" + this.lengthIndex + ", indicatorIndex=" + this.indicatorIndex + ", byteLength=" + this.byteLength
				+ ", charLength=" + this.charLength;
	}

	void unimpl(String paramString) throws SQLException {
		/* 358 */DatabaseError.throwSqlException(4, paramString + " not implemented for " + getClass());
	}

	boolean getBoolean(int paramInt) throws SQLException {
		/* 370 */if (this.rowSpaceIndicator == null) {
			/* 375 */DatabaseError.throwSqlException(21);
		}

		/* 381 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 382 */return false;
		}
		/* 384 */unimpl("getBoolean");

		/* 389 */return false;
	}

	byte getByte(int paramInt) throws SQLException {
		/* 398 */if (this.rowSpaceIndicator == null) {
			/* 403 */DatabaseError.throwSqlException(21);
		}

		/* 409 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 410 */return 0;
		}
		/* 412 */unimpl("getByte");

		/* 417 */return 0;
	}

	short getShort(int paramInt) throws SQLException {
		/* 426 */if (this.rowSpaceIndicator == null) {
			/* 431 */DatabaseError.throwSqlException(21);
		}

		/* 437 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 438 */return 0;
		}
		/* 440 */unimpl("getShort");

		/* 445 */return 0;
	}

	int getInt(int paramInt) throws SQLException {
		/* 454 */if (this.rowSpaceIndicator == null) {
			/* 459 */DatabaseError.throwSqlException(21);
		}

		/* 465 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 466 */return 0;
		}
		/* 468 */unimpl("getInt");

		/* 473 */return 0;
	}

	long getLong(int paramInt) throws SQLException {
		/* 482 */if (this.rowSpaceIndicator == null) {
			/* 487 */DatabaseError.throwSqlException(21);
		}

		/* 493 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 494 */return 0L;
		}
		/* 496 */unimpl("getLong");

		/* 501 */return 0L;
	}

	float getFloat(int paramInt) throws SQLException {
		/* 510 */if (this.rowSpaceIndicator == null) {
			/* 515 */DatabaseError.throwSqlException(21);
		}

		/* 521 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 522 */return 0.0F;
		}
		/* 524 */unimpl("getFloat");

		/* 529 */return 0.0F;
	}

	double getDouble(int paramInt) throws SQLException {
		/* 538 */if (this.rowSpaceIndicator == null) {
			/* 543 */DatabaseError.throwSqlException(21);
		}

		/* 549 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 550 */return 0.0D;
		}
		/* 552 */unimpl("getDouble");

		/* 557 */return 0.0D;
	}

	BigDecimal getBigDecimal(int paramInt) throws SQLException {
		/* 566 */if (this.rowSpaceIndicator == null) {
			/* 571 */DatabaseError.throwSqlException(21);
		}

		/* 577 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 578 */return null;
		}
		/* 580 */unimpl("getBigDecimal");

		/* 585 */return null;
	}

	BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
		/* 594 */if (this.rowSpaceIndicator == null) {
			/* 599 */DatabaseError.throwSqlException(21);
		}

		/* 605 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt1)] == -1) {
			/* 606 */return null;
		}
		/* 608 */unimpl("getBigDecimal");

		/* 613 */return null;
	}

	String getString(int paramInt) throws SQLException {
		/* 631 */return null;
	}

	Date getDate(int paramInt) throws SQLException {
		/* 637 */if (this.rowSpaceIndicator == null) {
			/* 642 */DatabaseError.throwSqlException(21);
		}

		/* 648 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 649 */return null;
		}
		/* 651 */unimpl("getDate");

		/* 656 */return null;
	}

	Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 665 */if (this.rowSpaceIndicator == null) {
			/* 670 */DatabaseError.throwSqlException(21);
		}

		/* 676 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 677 */return null;
		}
		/* 679 */unimpl("getDate");

		/* 684 */return null;
	}

	Time getTime(int paramInt) throws SQLException {
		/* 693 */if (this.rowSpaceIndicator == null) {
			/* 698 */DatabaseError.throwSqlException(21);
		}

		/* 704 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 705 */return null;
		}
		/* 707 */unimpl("getTime");

		/* 712 */return null;
	}

	Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 721 */if (this.rowSpaceIndicator == null) {
			/* 726 */DatabaseError.throwSqlException(21);
		}

		/* 732 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 733 */return null;
		}
		/* 735 */unimpl("getTime");

		/* 740 */return null;
	}

	Timestamp getTimestamp(int paramInt) throws SQLException {
		/* 749 */if (this.rowSpaceIndicator == null) {
			/* 754 */DatabaseError.throwSqlException(21);
		}

		/* 760 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 761 */return null;
		}
		/* 763 */unimpl("getTimestamp");

		/* 768 */return null;
	}

	Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 777 */if (this.rowSpaceIndicator == null) {
			/* 782 */DatabaseError.throwSqlException(21);
		}

		/* 788 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 789 */return null;
		}
		/* 791 */unimpl("getTimestamp");

		/* 796 */return null;
	}

	byte[] privateGetBytes(int paramInt) throws SQLException {
		/* 811 */return getBytes(paramInt);
	}

	byte[] getBytes(int paramInt) throws SQLException {
		/* 821 */byte[] arrayOfByte = null;

		/* 823 */if (this.rowSpaceIndicator == null) {
			/* 828 */DatabaseError.throwSqlException(21);
		}

		/* 834 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1) {
			/* 836 */int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
			/* 837 */int j = this.columnIndex + this.byteLength * paramInt;

			/* 839 */arrayOfByte = new byte[i];
			/* 840 */System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
		}

		/* 843 */return arrayOfByte;
	}

	InputStream getAsciiStream(int paramInt) throws SQLException {
		/* 849 */if (this.rowSpaceIndicator == null) {
			/* 854 */DatabaseError.throwSqlException(21);
		}

		/* 860 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 861 */return null;
		}
		/* 863 */unimpl("getAsciiStream");

		/* 868 */return null;
	}

	InputStream getUnicodeStream(int paramInt) throws SQLException {
		/* 877 */if (this.rowSpaceIndicator == null) {
			/* 882 */DatabaseError.throwSqlException(21);
		}

		/* 888 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 889 */return null;
		}
		/* 891 */unimpl("getUnicodeStream");

		/* 896 */return null;
	}

	InputStream getBinaryStream(int paramInt) throws SQLException {
		/* 905 */if (this.rowSpaceIndicator == null) {
			/* 910 */DatabaseError.throwSqlException(21);
		}

		/* 916 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 917 */return null;
		}
		/* 919 */unimpl("getBinaryStream");

		/* 924 */return null;
	}

	Object getObject(int paramInt) throws SQLException {
		/* 933 */if (this.rowSpaceIndicator == null) {
			/* 938 */DatabaseError.throwSqlException(21);
		}

		/* 944 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 945 */return null;
		}
		/* 947 */unimpl("getObject");

		/* 952 */return null;
	}

	Object getAnyDataEmbeddedObject(int paramInt) throws SQLException {
		/* 964 */if (this.rowSpaceIndicator == null) {
			/* 969 */DatabaseError.throwSqlException(21);
		}

		/* 975 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 976 */return null;
		}
		/* 978 */unimpl("getAnyDataEmbeddedObject");

		/* 983 */return null;
	}

	ResultSet getCursor(int paramInt) throws SQLException {
		/* 992 */DatabaseError.throwSqlException(4);

		/* 997 */return null;
	}

	Datum getOracleObject(int paramInt) throws SQLException {
		/* 1006 */if (this.rowSpaceIndicator == null) {
			/* 1011 */DatabaseError.throwSqlException(21);
		}

		/* 1017 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1018 */return null;
		}
		/* 1020 */unimpl("getOracleObject");

		/* 1025 */return null;
	}

	ROWID getROWID(int paramInt) throws SQLException {
		/* 1034 */if (this.rowSpaceIndicator == null) {
			/* 1039 */DatabaseError.throwSqlException(21);
		}

		/* 1045 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1046 */return null;
		}
		/* 1048 */DatabaseError.throwSqlException(4);

		/* 1053 */return null;
	}

	NUMBER getNUMBER(int paramInt) throws SQLException {
		/* 1062 */if (this.rowSpaceIndicator == null) {
			/* 1067 */DatabaseError.throwSqlException(21);
		}

		/* 1073 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1074 */return null;
		}
		/* 1076 */unimpl("getNUMBER");

		/* 1081 */return null;
	}

	DATE getDATE(int paramInt) throws SQLException {
		/* 1090 */if (this.rowSpaceIndicator == null) {
			/* 1095 */DatabaseError.throwSqlException(21);
		}

		/* 1101 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1102 */return null;
		}
		/* 1104 */unimpl("getDATE");

		/* 1109 */return null;
	}

	ARRAY getARRAY(int paramInt) throws SQLException {
		/* 1118 */if (this.rowSpaceIndicator == null) {
			/* 1123 */DatabaseError.throwSqlException(21);
		}

		/* 1129 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1130 */return null;
		}
		/* 1132 */unimpl("getARRAY");

		/* 1137 */return null;
	}

	STRUCT getSTRUCT(int paramInt) throws SQLException {
		/* 1146 */if (this.rowSpaceIndicator == null) {
			/* 1151 */DatabaseError.throwSqlException(21);
		}

		/* 1157 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1158 */return null;
		}
		/* 1160 */unimpl("getSTRUCT");

		/* 1165 */return null;
	}

	OPAQUE getOPAQUE(int paramInt) throws SQLException {
		/* 1174 */if (this.rowSpaceIndicator == null) {
			/* 1179 */DatabaseError.throwSqlException(21);
		}

		/* 1185 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1186 */return null;
		}
		/* 1188 */unimpl("getOPAQUE");

		/* 1193 */return null;
	}

	REF getREF(int paramInt) throws SQLException {
		/* 1202 */if (this.rowSpaceIndicator == null) {
			/* 1207 */DatabaseError.throwSqlException(21);
		}

		/* 1213 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1214 */return null;
		}
		/* 1216 */unimpl("getREF");

		/* 1221 */return null;
	}

	CHAR getCHAR(int paramInt) throws SQLException {
		/* 1230 */if (this.rowSpaceIndicator == null) {
			/* 1235 */DatabaseError.throwSqlException(21);
		}

		/* 1241 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1242 */return null;
		}
		/* 1244 */unimpl("getCHAR");

		/* 1249 */return null;
	}

	RAW getRAW(int paramInt) throws SQLException {
		/* 1258 */if (this.rowSpaceIndicator == null) {
			/* 1263 */DatabaseError.throwSqlException(21);
		}

		/* 1269 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1270 */return null;
		}
		/* 1272 */unimpl("getRAW");

		/* 1277 */return null;
	}

	BLOB getBLOB(int paramInt) throws SQLException {
		/* 1286 */if (this.rowSpaceIndicator == null) {
			/* 1291 */DatabaseError.throwSqlException(21);
		}

		/* 1297 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1298 */return null;
		}
		/* 1300 */unimpl("getBLOB");

		/* 1305 */return null;
	}

	CLOB getCLOB(int paramInt) throws SQLException {
		/* 1314 */if (this.rowSpaceIndicator == null) {
			/* 1319 */DatabaseError.throwSqlException(21);
		}

		/* 1325 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1326 */return null;
		}
		/* 1328 */unimpl("getCLOB");

		/* 1333 */return null;
	}

	BFILE getBFILE(int paramInt) throws SQLException {
		/* 1342 */if (this.rowSpaceIndicator == null) {
			/* 1347 */DatabaseError.throwSqlException(21);
		}

		/* 1353 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1354 */return null;
		}
		/* 1356 */unimpl("getBFILE");

		/* 1361 */return null;
	}

	CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory) throws SQLException {
		/* 1371 */Datum localDatum = getOracleObject(paramInt);

		/* 1376 */return paramCustomDatumFactory.create(localDatum, 0);
	}

	ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory) throws SQLException {
		/* 1382 */Datum localDatum = getOracleObject(paramInt);

		/* 1387 */return paramORADataFactory.create(localDatum, 0);
	}

	Object getObject(int paramInt, Map paramMap) throws SQLException {
		/* 1393 */if (this.rowSpaceIndicator == null) {
			/* 1398 */DatabaseError.throwSqlException(21);
		}

		/* 1404 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1405 */return null;
		}
		/* 1407 */unimpl("getObject");

		/* 1412 */return null;
	}

	Reader getCharacterStream(int paramInt) throws SQLException {
		/* 1421 */if (this.rowSpaceIndicator == null) {
			/* 1426 */DatabaseError.throwSqlException(21);
		}

		/* 1432 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1433 */return null;
		}
		/* 1435 */unimpl("getCharacterStream");

		/* 1440 */return null;
	}

	INTERVALYM getINTERVALYM(int paramInt) throws SQLException {
		/* 1449 */if (this.rowSpaceIndicator == null) {
			/* 1454 */DatabaseError.throwSqlException(21);
		}

		/* 1460 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1461 */return null;
		}
		/* 1463 */unimpl("getINTERVALYM");

		/* 1468 */return null;
	}

	INTERVALDS getINTERVALDS(int paramInt) throws SQLException {
		/* 1477 */if (this.rowSpaceIndicator == null) {
			/* 1482 */DatabaseError.throwSqlException(21);
		}

		/* 1488 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1489 */return null;
		}
		/* 1491 */unimpl("getINTERVALDS");

		/* 1496 */return null;
	}

	TIMESTAMP getTIMESTAMP(int paramInt) throws SQLException {
		/* 1505 */if (this.rowSpaceIndicator == null) {
			/* 1510 */DatabaseError.throwSqlException(21);
		}

		/* 1516 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1517 */return null;
		}
		/* 1519 */unimpl("getTIMESTAMP");

		/* 1524 */return null;
	}

	TIMESTAMPTZ getTIMESTAMPTZ(int paramInt) throws SQLException {
		/* 1533 */if (this.rowSpaceIndicator == null) {
			/* 1538 */DatabaseError.throwSqlException(21);
		}

		/* 1544 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1545 */return null;
		}
		/* 1547 */unimpl("getTIMESTAMPTZ");

		/* 1552 */return null;
	}

	TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt) throws SQLException {
		/* 1561 */if (this.rowSpaceIndicator == null) {
			/* 1566 */DatabaseError.throwSqlException(21);
		}

		/* 1572 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1573 */return null;
		}
		/* 1575 */unimpl("getTIMESTAMPLTZ");

		/* 1580 */return null;
	}

	URL getURL(int paramInt) throws SQLException {
		/* 1589 */if (this.rowSpaceIndicator == null) {
			/* 1594 */DatabaseError.throwSqlException(21);
		}

		/* 1600 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1601 */return null;
		}
		/* 1603 */unimpl("getURL");

		/* 1608 */return null;
	}

	Datum[] getOraclePlsqlIndexTable(int paramInt) throws SQLException {
		/* 1617 */if (this.rowSpaceIndicator == null) {
			/* 1622 */DatabaseError.throwSqlException(21);
		}

		/* 1628 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
			/* 1629 */return null;
		}
		/* 1631 */unimpl("getOraclePlsqlIndexTable");

		/* 1636 */return null;
	}

	boolean isNull(int paramInt) throws SQLException {
		/* 1644 */if (this.rowSpaceIndicator == null) {
			/* 1649 */DatabaseError.throwSqlException(21);
		}

		/* 1655 */return this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1;
	}

	void setNull(int paramInt) throws SQLException {
		/* 1660 */if (this.rowSpaceIndicator == null) {
			/* 1665 */DatabaseError.throwSqlException(21);
		}

		/* 1671 */this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] = -1;
	}

	void fetchNextColumns() throws SQLException {
	}

	void calculateSizeTmpByteArray() {
	}

	boolean unmarshalOneRow() throws SQLException, IOException {
		/* 1699 */DatabaseError.throwSqlException(148);
		/* 1700 */return false;
	}

	void copyRow() throws SQLException, IOException {
		/* 1713 */DatabaseError.throwSqlException(148);
	}

	int readStream(byte[] paramArrayOfByte, int paramInt) throws SQLException, IOException {
		/* 1724 */DatabaseError.throwSqlException(148);
		/* 1725 */return -1;
	}

	void initMetadata() throws SQLException {
	}

	void setDisplaySize(int paramInt) throws SQLException {
		/* 1740 */this.describeMaxLength = paramInt;
	}

	void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2) throws SQLException {
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.Accessor JD-Core
 * Version: 0.6.0
 */