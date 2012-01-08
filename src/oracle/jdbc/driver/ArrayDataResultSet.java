package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.io.Reader;

import java.math.BigDecimal;

import java.net.URL;

import java.sql.Array;

import java.sql.Blob;

import java.sql.Clob;

import java.sql.Date;
import java.sql.NClob;

import java.sql.Ref;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;
import java.sql.RowId;

import java.sql.SQLException;
import java.sql.SQLXML;

import java.sql.Statement;

import java.sql.Time;

import java.sql.Timestamp;

import java.util.Calendar;

import java.util.Map;

import oracle.sql.ARRAY;

import oracle.sql.BFILE;

import oracle.sql.BLOB;

import oracle.sql.CHAR;

import oracle.sql.CLOB;

import oracle.sql.CustomDatum;

import oracle.sql.CustomDatumFactory;

import oracle.sql.DATE;

import oracle.sql.Datum;

import oracle.sql.NUMBER;

import oracle.sql.OPAQUE;

import oracle.sql.ORAData;

import oracle.sql.ORADataFactory;

import oracle.sql.RAW;

import oracle.sql.REF;

import oracle.sql.ROWID;
import oracle.sql.STRUCT;

public class ArrayDataResultSet extends BaseResultSet {
	Datum[] data;
	Map map;
	private int currentIndex;
	private int lastIndex;
	boolean closed;
	PhysicalConnection connection;
	private Boolean wasNull;
	/* 38 */private static Boolean BOOLEAN_TRUE = new Boolean(true);
	/* 39 */private static Boolean BOOLEAN_FALSE = new Boolean(false);
	private int fetchSize;
	ARRAY array;
	/* 1122 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";

	public ArrayDataResultSet(OracleConnection paramOracleConnection, Datum[] paramArrayOfDatum, Map paramMap) throws SQLException {
		/* 59 */this.connection = ((PhysicalConnection) paramOracleConnection);
		/* 60 */this.data = paramArrayOfDatum;
		/* 61 */this.map = paramMap;
		/* 62 */this.currentIndex = 0;
		/* 63 */this.lastIndex = (this.data == null ? 0 : this.data.length);
		/* 64 */this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
	}

	public ArrayDataResultSet(OracleConnection paramOracleConnection, Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap) throws SQLException {
		/* 83 */this.connection = ((PhysicalConnection) paramOracleConnection);
		/* 84 */this.data = paramArrayOfDatum;
		/* 85 */this.map = paramMap;
		/* 86 */this.currentIndex = ((int) paramLong - 1);

		/* 88 */int i = this.data == null ? 0 : this.data.length;

		/* 90 */this.lastIndex = (this.currentIndex + Math.min(i - this.currentIndex, paramInt));

		/* 92 */this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
	}

	public ArrayDataResultSet(OracleConnection paramOracleConnection, ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap) throws SQLException {
		/* 102 */this.connection = ((PhysicalConnection) paramOracleConnection);
		/* 103 */this.array = paramARRAY;
		/* 104 */this.map = paramMap;
		/* 105 */this.currentIndex = ((int) paramLong - 1);

		/* 107 */int i = this.array == null ? 0 : paramARRAY.length();

		/* 109 */this.lastIndex = (this.currentIndex + (paramInt == -1 ? i - this.currentIndex : Math.min(i - this.currentIndex, paramInt)));

		/* 112 */this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
	}

	public boolean next() throws SQLException {
		/* 121 */if (this.closed) {
			/* 122 */DatabaseError.throwSqlException(10, "next");
		}

		/* 127 */this.currentIndex += 1;

		/* 129 */return this.currentIndex <= this.lastIndex;
	}

	public synchronized void close() throws SQLException {
		/* 138 */this.closed = true;
	}

	public synchronized boolean wasNull() throws SQLException {
		/* 147 */if (this.wasNull == null) {
			/* 148 */DatabaseError.throwSqlException(24, null);
		}
		/* 150 */return this.wasNull.booleanValue();
	}

	public synchronized String getString(int paramInt) throws SQLException {
		/* 159 */Datum localDatum = getOracleObject(paramInt);

		/* 161 */if (localDatum != null) {
			/* 162 */return localDatum.stringValue();
		}
		/* 164 */return null;
	}

	public synchronized ResultSet getCursor(int paramInt) throws SQLException {
		/* 173 */DatabaseError.throwSqlException(4, "getCursor");

		/* 176 */return null;
	}

	public synchronized Datum getOracleObject(int paramInt) throws SQLException {
		/* 185 */if (this.currentIndex <= 0) {
			/* 186 */DatabaseError.throwSqlException(14, null);
		}

		/* 189 */if (paramInt == 1) {
			/* 191 */this.wasNull = BOOLEAN_FALSE;

			/* 193 */return new NUMBER(this.currentIndex);
		}
		/* 195 */if (paramInt == 2) {
			/* 197 */if (this.data != null) {
				/* 199 */this.wasNull = (this.data[(this.currentIndex - 1)] == null ? BOOLEAN_TRUE : BOOLEAN_FALSE);

				/* 202 */return this.data[(this.currentIndex - 1)];
			}
			/* 204 */if (this.array != null) {
				/* 208 */Datum[] arrayOfDatum = this.array.getOracleArray(this.currentIndex, 1);

				/* 210 */if ((arrayOfDatum != null) && (arrayOfDatum.length >= 1)) {
					/* 212 */this.wasNull = (arrayOfDatum[0] == null ? BOOLEAN_TRUE : BOOLEAN_FALSE);

					/* 214 */return arrayOfDatum[0];
				}
			}

			/* 218 */DatabaseError.throwSqlException(1, "Out of sync");
		}

		/* 221 */DatabaseError.throwSqlException(3, null);

		/* 224 */return null;
	}

	public synchronized ROWID getROWID(int paramInt) throws SQLException {
		/* 233 */Datum localDatum = getOracleObject(paramInt);

		/* 235 */if (localDatum != null) {
			/* 237 */if ((localDatum instanceof ROWID)) {
				/* 238 */return (ROWID) localDatum;
			}
			/* 240 */DatabaseError.throwSqlException(4, "getROWID");
		}

		/* 244 */return null;
	}

	public synchronized NUMBER getNUMBER(int paramInt) throws SQLException {
		/* 253 */Datum localDatum = getOracleObject(paramInt);

		/* 255 */if (localDatum != null) {
			/* 257 */if ((localDatum instanceof NUMBER)) {
				/* 258 */return (NUMBER) localDatum;
			}
			/* 260 */DatabaseError.throwSqlException(4, "getNUMBER");
		}

		/* 264 */return null;
	}

	public synchronized DATE getDATE(int paramInt) throws SQLException {
		/* 273 */Datum localDatum = getOracleObject(paramInt);

		/* 275 */if (localDatum != null) {
			/* 277 */if ((localDatum instanceof DATE)) {
				/* 278 */return (DATE) localDatum;
			}
			/* 280 */DatabaseError.throwSqlException(4, "getDATE");
		}

		/* 284 */return null;
	}

	public synchronized ARRAY getARRAY(int paramInt) throws SQLException {
		/* 293 */Datum localDatum = getOracleObject(paramInt);

		/* 295 */if (localDatum != null) {
			/* 297 */if ((localDatum instanceof ARRAY)) {
				/* 298 */return (ARRAY) localDatum;
			}
			/* 300 */DatabaseError.throwSqlException(4, "getARRAY");
		}

		/* 304 */return null;
	}

	public synchronized STRUCT getSTRUCT(int paramInt) throws SQLException {
		/* 313 */Datum localDatum = getOracleObject(paramInt);

		/* 315 */if (localDatum != null) {
			/* 317 */if ((localDatum instanceof STRUCT)) {
				/* 318 */return (STRUCT) localDatum;
			}
			/* 320 */DatabaseError.throwSqlException(4, "getSTRUCT");
		}

		/* 324 */return null;
	}

	public synchronized OPAQUE getOPAQUE(int paramInt) throws SQLException {
		/* 333 */Datum localDatum = getOracleObject(paramInt);

		/* 335 */if (localDatum != null) {
			/* 337 */if ((localDatum instanceof OPAQUE)) {
				/* 338 */return (OPAQUE) localDatum;
			}
			/* 340 */DatabaseError.throwSqlException(4, "getOPAQUE");
		}

		/* 344 */return null;
	}

	public synchronized REF getREF(int paramInt) throws SQLException {
		/* 353 */Datum localDatum = getOracleObject(paramInt);

		/* 355 */if (localDatum != null) {
			/* 357 */if ((localDatum instanceof REF)) {
				/* 358 */return (REF) localDatum;
			}
			/* 360 */DatabaseError.throwSqlException(4, "getREF");
		}

		/* 364 */return null;
	}

	public synchronized CHAR getCHAR(int paramInt) throws SQLException {
		/* 373 */Datum localDatum = getOracleObject(paramInt);

		/* 375 */if (localDatum != null) {
			/* 377 */if ((localDatum instanceof CHAR)) {
				/* 378 */return (CHAR) localDatum;
			}
			/* 380 */DatabaseError.throwSqlException(4, "getCHAR");
		}

		/* 384 */return null;
	}

	public synchronized RAW getRAW(int paramInt) throws SQLException {
		/* 393 */Datum localDatum = getOracleObject(paramInt);

		/* 395 */if (localDatum != null) {
			/* 397 */if ((localDatum instanceof RAW)) {
				/* 398 */return (RAW) localDatum;
			}
			/* 400 */DatabaseError.throwSqlException(4, "getRAW");
		}

		/* 404 */return null;
	}

	public synchronized BLOB getBLOB(int paramInt) throws SQLException {
		/* 413 */Datum localDatum = getOracleObject(paramInt);

		/* 415 */if (localDatum != null) {
			/* 417 */if ((localDatum instanceof BLOB)) {
				/* 418 */return (BLOB) localDatum;
			}
			/* 420 */DatabaseError.throwSqlException(4, "getBLOB");
		}

		/* 424 */return null;
	}

	public synchronized CLOB getCLOB(int paramInt) throws SQLException {
		/* 433 */Datum localDatum = getOracleObject(paramInt);

		/* 435 */if (localDatum != null) {
			/* 437 */if ((localDatum instanceof CLOB)) {
				/* 438 */return (CLOB) localDatum;
			}
			/* 440 */DatabaseError.throwSqlException(4, "getCLOB");
		}

		/* 444 */return null;
	}

	public synchronized BFILE getBFILE(int paramInt) throws SQLException {
		/* 453 */Datum localDatum = getOracleObject(paramInt);

		/* 455 */if (localDatum != null) {
			/* 457 */if ((localDatum instanceof BFILE)) {
				/* 458 */return (BFILE) localDatum;
			}
			/* 460 */DatabaseError.throwSqlException(4, "getBFILE");
		}

		/* 464 */return null;
	}

	public synchronized BFILE getBfile(int paramInt) throws SQLException {
		/* 474 */return getBFILE(paramInt);
	}

	public synchronized boolean getBoolean(int paramInt) throws SQLException {
		/* 483 */Datum localDatum = getOracleObject(paramInt);

		/* 485 */if (localDatum != null) {
			/* 486 */return localDatum.booleanValue();
		}
		/* 488 */return false;
	}

	public synchronized byte getByte(int paramInt) throws SQLException {
		/* 497 */Datum localDatum = getOracleObject(paramInt);

		/* 499 */if (localDatum != null) {
			/* 500 */return localDatum.byteValue();
		}
		/* 502 */return 0;
	}

	public synchronized short getShort(int paramInt) throws SQLException {
		/* 511 */long l = getLong(paramInt);

		/* 513 */if ((l > 65537L) || (l < -65538L)) {
			/* 514 */DatabaseError.throwSqlException(26, "getShort");
		}

		/* 517 */return (short) (int) l;
	}

	public synchronized int getInt(int paramInt) throws SQLException {
		/* 526 */Datum localDatum = getOracleObject(paramInt);

		/* 528 */if (localDatum != null) {
			/* 530 */return localDatum.intValue();
		}

		/* 533 */return 0;
	}

	public synchronized long getLong(int paramInt) throws SQLException {
		/* 542 */Datum localDatum = getOracleObject(paramInt);

		/* 544 */if (localDatum != null) {
			/* 546 */return localDatum.longValue();
		}

		/* 549 */return 0L;
	}

	public synchronized float getFloat(int paramInt) throws SQLException {
		/* 558 */Datum localDatum = getOracleObject(paramInt);

		/* 560 */if (localDatum != null) {
			/* 562 */return localDatum.floatValue();
		}

		/* 565 */return 0.0F;
	}

	public synchronized double getDouble(int paramInt) throws SQLException {
		/* 574 */Datum localDatum = getOracleObject(paramInt);

		/* 576 */if (localDatum != null) {
			/* 578 */return localDatum.doubleValue();
		}

		/* 581 */return 0.0D;
	}

	public synchronized BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
		/* 591 */Datum localDatum = getOracleObject(paramInt1);

		/* 593 */if (localDatum != null) {
			/* 595 */return localDatum.bigDecimalValue();
		}

		/* 598 */return null;
	}

	public synchronized byte[] getBytes(int paramInt) throws SQLException {
		/* 607 */Datum localDatum = getOracleObject(paramInt);

		/* 609 */if (localDatum != null) {
			/* 611 */if ((localDatum instanceof RAW)) {
				/* 612 */return ((RAW) localDatum).shareBytes();
			}
			/* 614 */DatabaseError.throwSqlException(4, "getBytes");
		}

		/* 618 */return null;
	}

	public synchronized Date getDate(int paramInt) throws SQLException {
		/* 627 */Datum localDatum = getOracleObject(paramInt);

		/* 629 */if (localDatum != null) {
			/* 631 */return localDatum.dateValue();
		}

		/* 634 */return null;
	}

	public synchronized Time getTime(int paramInt) throws SQLException {
		/* 643 */Datum localDatum = getOracleObject(paramInt);

		/* 645 */if (localDatum != null) {
			/* 647 */return localDatum.timeValue();
		}

		/* 650 */return null;
	}

	public synchronized Timestamp getTimestamp(int paramInt) throws SQLException {
		/* 660 */Datum localDatum = getOracleObject(paramInt);

		/* 662 */if (localDatum != null) {
			/* 664 */return localDatum.timestampValue();
		}

		/* 667 */return null;
	}

	public synchronized InputStream getAsciiStream(int paramInt) throws SQLException {
		/* 677 */Datum localDatum = getOracleObject(paramInt);

		/* 679 */if (localDatum != null) {
			/* 681 */localDatum.asciiStreamValue();
		}

		/* 684 */return null;
	}

	public synchronized InputStream getUnicodeStream(int paramInt) throws SQLException {
		/* 694 */Datum localDatum = getOracleObject(paramInt);

		/* 696 */if (localDatum != null) {
			/* 698 */DBConversion localDBConversion = this.connection.conversion;
			/* 699 */byte[] arrayOfByte = localDatum.shareBytes();

			/* 701 */if ((localDatum instanceof RAW)) {
				/* 703 */return localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 3);
			}

			/* 706 */if ((localDatum instanceof CHAR)) {
				/* 708 */return localDBConversion.ConvertStream(new ByteArrayInputStream(arrayOfByte), 1);
			}

			/* 712 */DatabaseError.throwSqlException(4, "getUnicodeStream");
		}

		/* 716 */return null;
	}

	public synchronized InputStream getBinaryStream(int paramInt) throws SQLException {
		/* 726 */Datum localDatum = getOracleObject(paramInt);

		/* 728 */if (localDatum != null) {
			/* 730 */return localDatum.binaryStreamValue();
		}

		/* 733 */return null;
	}

	public synchronized Object getObject(int paramInt) throws SQLException {
		/* 743 */return getObject(paramInt, this.map);
	}

	/** @deprecated */
	public synchronized CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory) throws SQLException {
		/* 756 */Datum localDatum = getOracleObject(paramInt);

		/* 761 */return paramCustomDatumFactory.create(localDatum, 0);
	}

	public synchronized ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory) throws SQLException {
		/* 771 */Datum localDatum = getOracleObject(paramInt);

		/* 776 */return paramORADataFactory.create(localDatum, 0);
	}

	public synchronized ResultSetMetaData getMetaData() throws SQLException {
		/* 788 */if (this.closed) {
			/* 789 */DatabaseError.throwSqlException(10, "getMetaData");
		}

		/* 794 */DatabaseError.throwSqlException(23, "getMetaData");

		/* 797 */return null;
	}

	public synchronized int findColumn(String paramString) throws SQLException {
		/* 806 */if (paramString.equalsIgnoreCase("index"))
			/* 807 */return 1;
		/* 808 */if (paramString.equalsIgnoreCase("value")) {
			/* 809 */return 2;
		}
		/* 811 */DatabaseError.throwSqlException(6, "get_column_index");

		/* 815 */return 0;
	}

	public synchronized Statement getStatement() throws SQLException {
		/* 828 */return null;
	}

	public synchronized Object getObject(int paramInt, Map paramMap) throws SQLException {
		/* 838 */Datum localDatum = getOracleObject(paramInt);

		/* 840 */if (localDatum != null) {
			/* 842 */if ((localDatum instanceof STRUCT)) {
				/* 843 */return ((STRUCT) localDatum).toJdbc(paramMap);
			}
			/* 845 */return localDatum.toJdbc();
		}

		/* 848 */return null;
	}

	public synchronized Ref getRef(int paramInt) throws SQLException {
		/* 857 */return getREF(paramInt);
	}

	public synchronized Blob getBlob(int paramInt) throws SQLException {
		/* 866 */return getBLOB(paramInt);
	}

	public synchronized Clob getClob(int paramInt) throws SQLException {
		/* 875 */return getCLOB(paramInt);
	}

	public synchronized Array getArray(int paramInt) throws SQLException {
		/* 885 */return getARRAY(paramInt);
	}

	public synchronized Reader getCharacterStream(int paramInt) throws SQLException {
		/* 902 */Datum localDatum = getOracleObject(paramInt);

		/* 904 */if (localDatum != null) {
			/* 906 */return localDatum.characterStreamValue();
		}

		/* 909 */return null;
	}

	public synchronized BigDecimal getBigDecimal(int paramInt) throws SQLException {
		/* 919 */Datum localDatum = getOracleObject(paramInt);

		/* 921 */if (localDatum != null) {
			/* 923 */return localDatum.bigDecimalValue();
		}

		/* 926 */return null;
	}

	public synchronized Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 936 */Datum localDatum = getOracleObject(paramInt);

		/* 938 */if (localDatum != null) {
			/* 940 */DATE localDATE = null;

			/* 942 */if ((localDatum instanceof DATE))
				/* 943 */localDATE = (DATE) localDatum;
			else {
				/* 945 */localDATE = new DATE(localDatum.stringValue());
			}
			/* 947 */if (localDATE != null) {
				/* 948 */return localDATE.dateValue(paramCalendar);
			}
		}
		/* 951 */return null;
	}

	public synchronized Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 961 */Datum localDatum = getOracleObject(paramInt);

		/* 963 */if (localDatum != null) {
			/* 965 */DATE localDATE = null;

			/* 967 */if ((localDatum instanceof DATE))
				/* 968 */localDATE = (DATE) localDatum;
			else {
				/* 970 */localDATE = new DATE(localDatum.stringValue());
			}
			/* 972 */if (localDATE != null) {
				/* 973 */return localDATE.timeValue(paramCalendar);
			}
		}
		/* 976 */return null;
	}

	public synchronized Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 986 */Datum localDatum = getOracleObject(paramInt);

		/* 988 */if (localDatum != null) {
			/* 990 */DATE localDATE = null;

			/* 992 */if ((localDatum instanceof DATE))
				/* 993 */localDATE = (DATE) localDatum;
			else {
				/* 995 */localDATE = new DATE(localDatum.stringValue());
			}
			/* 997 */if (localDATE != null) {
				/* 998 */return localDATE.timestampValue(paramCalendar);
			}
		}
		/* 1001 */return null;
	}

	public synchronized URL getURL(int paramInt) throws SQLException {
		/* 1041 */throw new SQLException("Conversion to java.net.URL not supported.");
	}

	public boolean isBeforeFirst() throws SQLException {
		/* 1054 */return this.currentIndex < 1;
	}

	public boolean isAfterLast() throws SQLException {
		/* 1063 */return this.currentIndex > this.lastIndex;
	}

	public boolean isFirst() throws SQLException {
		/* 1072 */return this.currentIndex == 1;
	}

	public boolean isLast() throws SQLException {
		/* 1081 */return this.currentIndex == this.lastIndex;
	}

	public int getRow() throws SQLException {
		/* 1090 */return this.currentIndex;
	}

	public void setFetchSize(int paramInt) throws SQLException {
		/* 1103 */if (paramInt < 0)
			/* 1104 */DatabaseError.throwSqlException(68);
		/* 1105 */else if (paramInt == 0)
			/* 1106 */this.fetchSize = OracleConnection.DEFAULT_ROW_PREFETCH;
		else
			/* 1108 */this.fetchSize = paramInt;
	}

	public int getFetchSize() throws SQLException {
		/* 1117 */return this.fetchSize;
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.ArrayDataResultSet JD-Core Version: 0.6.0
 */