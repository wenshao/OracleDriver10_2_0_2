/*      */package oracle.jdbc.driver;

/*      */
/*      */import java.io.ByteArrayInputStream;
/*      */
import java.io.IOException;
/*      */
import java.io.InputStream;
/*      */
import java.io.Reader;
/*      */
import java.math.BigDecimal;
/*      */
import java.net.MalformedURLException;
/*      */
import java.net.URL;
/*      */
import java.sql.Array;
/*      */
import java.sql.Blob;
/*      */
import java.sql.Clob;
/*      */
import java.sql.Date;
/*      */
import java.sql.Ref;
/*      */
import java.sql.ResultSet;
/*      */
import java.sql.ResultSetMetaData;
/*      */
import java.sql.SQLException;
/*      */
import java.sql.Statement;
/*      */
import java.sql.Time;
/*      */
import java.sql.Timestamp;
/*      */
import java.util.Calendar;
/*      */
import java.util.Map;
/*      */
import java.util.Vector;
/*      */
import oracle.sql.ARRAY;
/*      */
import oracle.sql.BFILE;
/*      */
import oracle.sql.BLOB;
/*      */
import oracle.sql.CHAR;
/*      */
import oracle.sql.CLOB;
/*      */
import oracle.sql.CustomDatum;
/*      */
import oracle.sql.CustomDatumFactory;
/*      */
import oracle.sql.DATE;
/*      */
import oracle.sql.Datum;
/*      */
import oracle.sql.INTERVALDS;
/*      */
import oracle.sql.INTERVALYM;
/*      */
import oracle.sql.NUMBER;
/*      */
import oracle.sql.OPAQUE;
/*      */
import oracle.sql.ORAData;
/*      */
import oracle.sql.ORADataFactory;
/*      */
import oracle.sql.RAW;
/*      */
import oracle.sql.REF;
/*      */
import oracle.sql.ROWID;
/*      */
import oracle.sql.STRUCT;
/*      */
import oracle.sql.TIMESTAMP;
/*      */
import oracle.sql.TIMESTAMPLTZ;
/*      */
import oracle.sql.TIMESTAMPTZ;

/*      */
/*      */class ScrollableResultSet extends BaseResultSet
/*      */{
	/*      */PhysicalConnection connection;
	/*      */OracleResultSetImpl resultSet;
	/*      */ScrollRsetStatement scrollStmt;
	/*      */ResultSetMetaData metadata;
	/*      */private int rsetType;
	/*      */private int rsetConcurency;
	/*      */private int beginColumnIndex;
	/*      */private int columnCount;
	/*      */private int wasNull;
	/*      */OracleResultSetCache rsetCache;
	/*      */int currentRow;
	/*      */private int numRowsCached;
	/*      */private boolean allRowsCached;
	/*      */private int lastRefetchSz;
	/*      */private Vector refetchRowids;
	/*      */private OraclePreparedStatement refetchStmt;
	/*      */private int usrFetchDirection;
	/* 2055 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";

	/*      */
	/*      */ScrollableResultSet(ScrollRsetStatement paramScrollRsetStatement,
			OracleResultSetImpl paramOracleResultSetImpl, int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 72 */this.connection = ((OracleStatement) paramScrollRsetStatement).connection;
		/* 73 */this.resultSet = paramOracleResultSetImpl;
		/* 74 */this.metadata = null;
		/* 75 */this.scrollStmt = paramScrollRsetStatement;
		/* 76 */this.rsetType = paramInt1;
		/* 77 */this.rsetConcurency = paramInt2;
		/* 78 */this.autoRefetch = paramScrollRsetStatement.getAutoRefetch();
		/* 79 */this.beginColumnIndex = (needIdentifier(paramInt1, paramInt2) ? 1
				: 0);
		/* 80 */this.columnCount = 0;
		/* 81 */this.wasNull = -1;
		/* 82 */this.rsetCache = paramScrollRsetStatement.getResultSetCache();
		/*      */
		/* 84 */if (this.rsetCache == null)
		/*      */{
			/* 86 */this.rsetCache = new OracleResultSetCacheImpl();
			/*      */}
		/*      */else
		/*      */{
			/*      */try
			/*      */{
				/* 92 */this.rsetCache.clear();
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 96 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */}
		/*      */
		/* 100 */this.currentRow = 0;
		/* 101 */this.numRowsCached = 0;
		/* 102 */this.allRowsCached = false;
		/* 103 */this.lastRefetchSz = 0;
		/* 104 */this.refetchRowids = null;
		/* 105 */this.refetchStmt = null;
		/* 106 */this.usrFetchDirection = 1000;
		/*      */}

	/*      */
	/*      */public void close()
	/*      */throws SQLException
	/*      */{
		/* 118 */synchronized (this.connection)
		/*      */{
			/* 120 */synchronized (this)
			/*      */{
				/* 125 */if (this.resultSet != null) {
					/* 126 */this.resultSet.close();
					/*      */}
				/* 128 */if (this.refetchStmt != null) {
					/* 129 */this.refetchStmt.close();
					/*      */}
				/* 131 */if (this.scrollStmt != null) {
					/* 132 */this.scrollStmt.notifyCloseRset();
					/*      */}
				/* 134 */if (this.refetchRowids != null) {
					/* 135 */this.refetchRowids.removeAllElements();
					/*      */}
				/* 137 */this.resultSet = null;
				/* 138 */this.scrollStmt = null;
				/* 139 */this.refetchStmt = null;
				/* 140 */this.refetchRowids = null;
				/* 141 */this.metadata = null;
				/*      */try
				/*      */{
					/* 145 */if (this.rsetCache != null)
					/*      */{
						/* 147 */this.rsetCache.clear();
						/* 148 */this.rsetCache.close();
						/*      */}
					/*      */}
				/*      */catch (IOException localIOException)
				/*      */{
					/* 153 */DatabaseError.throwSqlException(localIOException);
					/*      */}
				/*      */
				/* 156 */this.rsetCache = null;
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean wasNull()
	/*      */throws SQLException
	/*      */{
		/* 167 */if (this.wasNull == -1) {
			/* 168 */DatabaseError.throwSqlException(24);
			/*      */}
		/* 170 */return this.wasNull == 1;
		/*      */}

	/*      */
	/*      */public synchronized Statement getStatement()
	/*      */throws SQLException
	/*      */{
		/* 180 */return (Statement) this.scrollStmt;
		/*      */}

	/*      */
	/*      */synchronized void resetBeginColumnIndex()
	/*      */{
		/* 192 */this.beginColumnIndex = 0;
		/*      */}

	/*      */
	/*      */synchronized ResultSet getResultSet()
	/*      */{
		/* 204 */return this.resultSet;
		/*      */}

	/*      */
	/*      */synchronized int removeRowInCache(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 213 */if ((!isEmptyResultSet()) && (isValidRow(paramInt)))
		/*      */{
			/* 215 */removeCachedRowAt(paramInt);
			/*      */
			/* 217 */this.numRowsCached -= 1;
			/*      */
			/* 219 */if (paramInt >= this.currentRow) {
				/* 220 */this.currentRow -= 1;
				/*      */}
			/* 222 */return 1;
			/*      */}
		/*      */
		/* 225 */return 0;
		/*      */}

	/*      */
	/*      */int refreshRowsInCache(int paramInt1, int paramInt2, int paramInt3)
	/*      */throws SQLException
	/*      */{
		/* 238 */synchronized (this.connection)
		/*      */{
			/* 240 */synchronized (this)
			/*      */{
				/* 242 */OracleResultSet localOracleResultSet = null;
				/* 243 */int i = 0;
				/*      */
				/* 246 */i = get_refetch_size(paramInt1, paramInt2, paramInt3);
				/*      */try
				/*      */{
					/* 250 */if (i > 0)
					/*      */{
						/* 254 */if (i != this.lastRefetchSz)
						/*      */{
							/* 256 */if (this.refetchStmt != null) {
								/* 257 */this.refetchStmt.close();
								/*      */}
							/* 259 */this.refetchStmt = prepare_refetch_statement(i);
							/* 260 */this.refetchStmt
									.setQueryTimeout(((OracleStatement) this.scrollStmt)
											.getQueryTimeout());
							/* 261 */this.lastRefetchSz = i;
							/*      */}
						/*      */
						/* 265 */prepare_refetch_binds(this.refetchStmt, i);
						/*      */
						/* 268 */localOracleResultSet = (OracleResultSet) this.refetchStmt
								.executeQuery();
						/*      */
						/* 271 */save_refetch_results(localOracleResultSet,
								paramInt1, i, paramInt3);
						/*      */}
					/*      */
					/*      */}
				/*      */finally
				/*      */{
					/* 277 */if (localOracleResultSet != null) {
						/* 278 */localOracleResultSet.close();
						/*      */}
					/*      */}
				/* 281 */return i;
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean next()
	/*      */throws SQLException
	/*      */{
		/* 296 */if (isEmptyResultSet())
		/*      */{
			/* 298 */return false;
			/*      */}
		/*      */
		/* 302 */if (this.currentRow < 1)
		/*      */{
			/* 304 */this.currentRow = 1;
			/*      */}
		/*      */else
		/*      */{
			/* 308 */this.currentRow += 1;
			/*      */}
		/*      */
		/* 311 */return isValidRow(this.currentRow);
		/*      */}

	/*      */
	/*      */public synchronized boolean isBeforeFirst()
	/*      */throws SQLException
	/*      */{
		/* 321 */return (!isEmptyResultSet()) && (this.currentRow < 1);
		/*      */}

	/*      */
	/*      */public synchronized boolean isAfterLast()
	/*      */throws SQLException
	/*      */{
		/* 330 */return (!isEmptyResultSet()) && (this.currentRow > 0)
				&& (!isValidRow(this.currentRow));
		/*      */}

	/*      */
	/*      */public synchronized boolean isFirst()
	/*      */throws SQLException
	/*      */{
		/* 339 */return this.currentRow == 1;
		/*      */}

	/*      */
	/*      */public synchronized boolean isLast()
	/*      */throws SQLException
	/*      */{
		/* 348 */return (!isEmptyResultSet()) && (isValidRow(this.currentRow))
				&& (!isValidRow(this.currentRow + 1));
		/*      */}

	/*      */
	/*      */public synchronized void beforeFirst()
	/*      */throws SQLException
	/*      */{
		/* 358 */if (!isEmptyResultSet())
			/* 359 */this.currentRow = 0;
		/*      */}

	/*      */
	/*      */public synchronized void afterLast()
	/*      */throws SQLException
	/*      */{
		/* 368 */if (!isEmptyResultSet())
			/* 369 */this.currentRow = (getLastRow() + 1);
		/*      */}

	/*      */
	/*      */public synchronized boolean first()
	/*      */throws SQLException
	/*      */{
		/* 378 */if (isEmptyResultSet())
		/*      */{
			/* 380 */return false;
			/*      */}
		/*      */
		/* 384 */this.currentRow = 1;
		/*      */
		/* 386 */return isValidRow(this.currentRow);
		/*      */}

	/*      */
	/*      */public synchronized boolean last()
	/*      */throws SQLException
	/*      */{
		/* 396 */if (isEmptyResultSet())
		/*      */{
			/* 398 */return false;
			/*      */}
		/*      */
		/* 402 */this.currentRow = getLastRow();
		/*      */
		/* 404 */return isValidRow(this.currentRow);
		/*      */}

	/*      */
	/*      */public synchronized int getRow()
	/*      */throws SQLException
	/*      */{
		/* 414 */if (isValidRow(this.currentRow)) {
			/* 415 */return this.currentRow;
			/*      */}
		/* 417 */return 0;
		/*      */}

	/*      */
	/*      */public synchronized boolean absolute(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 426 */if (paramInt == 0) {
			/* 427 */DatabaseError.throwSqlException(68, "absolute (0)");
			/*      */}
		/*      */
		/* 430 */if (isEmptyResultSet())
		/*      */{
			/* 432 */return false;
			/*      */}
		/* 434 */if (paramInt > 0)
		/*      */{
			/* 436 */this.currentRow = paramInt;
			/*      */}
		/* 438 */else if (paramInt < 0)
		/*      */{
			/* 440 */this.currentRow = (getLastRow() + 1 + paramInt);
			/*      */}
		/*      */
		/* 443 */return isValidRow(this.currentRow);
		/*      */}

	/*      */
	/*      */public synchronized boolean relative(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 452 */if (isEmptyResultSet())
		/*      */{
			/* 454 */return false;
			/*      */}
		/* 456 */if (isValidRow(this.currentRow))
		/*      */{
			/* 458 */this.currentRow += paramInt;
			/*      */
			/* 460 */return isValidRow(this.currentRow);
			/*      */}
		/*      */
		/* 464 */DatabaseError.throwSqlException(82, "relative");
		/*      */
		/* 467 */return false;
		/*      */}

	/*      */
	/*      */public synchronized boolean previous()
	/*      */throws SQLException
	/*      */{
		/* 477 */if (isEmptyResultSet())
		/*      */{
			/* 479 */return false;
			/*      */}
		/* 481 */if (isAfterLast())
		/*      */{
			/* 483 */this.currentRow = getLastRow();
			/*      */}
		/*      */else
		/*      */{
			/* 487 */this.currentRow -= 1;
			/*      */}
		/*      */
		/* 490 */return isValidRow(this.currentRow);
		/*      */}

	/*      */
	/*      */public synchronized Datum getOracleObject(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 503 */this.wasNull = -1;
		/*      */
		/* 505 */if (!isValidRow(this.currentRow)) {
			/* 506 */DatabaseError.throwSqlException(11);
			/*      */}
		/* 508 */if ((paramInt < 1) || (paramInt > getColumnCount())) {
			/* 509 */DatabaseError.throwSqlException(3);
			/*      */}
		/* 511 */Datum localDatum = getCachedDatumValueAt(this.currentRow,
				paramInt + this.beginColumnIndex);
		/*      */
		/* 514 */this.wasNull = (localDatum == null ? 1 : 0);
		/*      */
		/* 516 */return localDatum;
		/*      */}

	/*      */
	/*      */public synchronized String getString(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 525 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 527 */if (localDatum != null) {
			/* 528 */return localDatum.stringValue();
			/*      */}
		/* 530 */return null;
		/*      */}

	/*      */
	/*      */public synchronized boolean getBoolean(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 539 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 541 */if (localDatum != null)
		/*      */{
			/* 543 */return localDatum.booleanValue();
			/*      */}
		/*      */
		/* 546 */return false;
		/*      */}

	/*      */
	/*      */public synchronized byte getByte(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 555 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 557 */if (localDatum != null) {
			/* 558 */return localDatum.byteValue();
			/*      */}
		/* 560 */return 0;
		/*      */}

	/*      */
	/*      */public synchronized short getShort(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 569 */long l = getLong(paramInt);
		/*      */
		/* 571 */if ((l > 65537L) || (l < -65538L)) {
			/* 572 */DatabaseError.throwSqlException(26, "getShort");
			/*      */}
		/*      */
		/* 575 */return (short) (int) l;
		/*      */}

	/*      */
	/*      */public synchronized int getInt(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 584 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 586 */if (localDatum != null)
		/*      */{
			/* 588 */return localDatum.intValue();
			/*      */}
		/*      */
		/* 591 */return 0;
		/*      */}

	/*      */
	/*      */public synchronized long getLong(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 600 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 602 */if (localDatum != null)
		/*      */{
			/* 604 */return localDatum.longValue();
			/*      */}
		/*      */
		/* 607 */return 0L;
		/*      */}

	/*      */
	/*      */public synchronized float getFloat(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 616 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 618 */if (localDatum != null)
		/*      */{
			/* 620 */return localDatum.floatValue();
			/*      */}
		/*      */
		/* 623 */return 0.0F;
		/*      */}

	/*      */
	/*      */public synchronized double getDouble(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 632 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 634 */if (localDatum != null)
		/*      */{
			/* 636 */return localDatum.doubleValue();
			/*      */}
		/*      */
		/* 639 */return 0.0D;
		/*      */}

	/*      */
	/*      */public synchronized BigDecimal getBigDecimal(int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 649 */Datum localDatum = getOracleObject(paramInt1);
		/*      */
		/* 651 */if (localDatum != null)
		/*      */{
			/* 653 */return localDatum.bigDecimalValue();
			/*      */}
		/*      */
		/* 656 */return null;
		/*      */}

	/*      */
	/*      */public synchronized byte[] getBytes(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 665 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 667 */if (localDatum != null)
		/*      */{
			/* 669 */if ((localDatum instanceof RAW)) {
				/* 670 */return ((RAW) localDatum).shareBytes();
				/*      */}
			/* 672 */DatabaseError.throwSqlException(4, "getBytes");
			/*      */}
		/*      */
		/* 676 */return null;
		/*      */}

	/*      */
	/*      */public synchronized Date getDate(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 685 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 687 */if (localDatum != null)
		/*      */{
			/* 689 */return localDatum.dateValue();
			/*      */}
		/*      */
		/* 692 */return null;
		/*      */}

	/*      */
	/*      */public synchronized Time getTime(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 701 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 703 */if (localDatum != null)
		/*      */{
			/* 705 */return localDatum.timeValue();
			/*      */}
		/*      */
		/* 708 */return null;
		/*      */}

	/*      */
	/*      */public synchronized Timestamp getTimestamp(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 718 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 720 */if (localDatum != null)
		/*      */{
			/* 722 */return localDatum.timestampValue();
			/*      */}
		/*      */
		/* 725 */return null;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getAsciiStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 735 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 737 */if (localDatum != null)
		/*      */{
			/* 739 */return localDatum.asciiStreamValue();
			/*      */}
		/*      */
		/* 742 */return null;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getUnicodeStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 752 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 754 */if (localDatum != null)
		/*      */{
			/* 756 */DBConversion localDBConversion = this.connection.conversion;
			/* 757 */byte[] arrayOfByte = localDatum.shareBytes();
			/*      */
			/* 759 */if ((localDatum instanceof RAW))
			/*      */{
				/* 761 */return localDBConversion.ConvertStream(
						new ByteArrayInputStream(arrayOfByte), 3);
				/*      */}
			/*      */
			/* 764 */if ((localDatum instanceof CHAR))
			/*      */{
				/* 766 */return localDBConversion.ConvertStream(
						new ByteArrayInputStream(arrayOfByte), 1);
				/*      */}
			/*      */
			/* 770 */DatabaseError.throwSqlException(4, "getUnicodeStream");
			/*      */}
		/*      */
		/* 774 */return null;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getBinaryStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 784 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 786 */if (localDatum != null)
		/*      */{
			/* 788 */return localDatum.binaryStreamValue();
			/*      */}
		/*      */
		/* 791 */return null;
		/*      */}

	/*      */
	/*      */public Object getObject(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 801 */return getObject(paramInt, this.connection.getTypeMap());
		/*      */}

	/*      */
	/*      */public synchronized Reader getCharacterStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 812 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 814 */if (localDatum != null)
		/*      */{
			/* 816 */return localDatum.characterStreamValue();
			/*      */}
		/*      */
		/* 819 */return null;
		/*      */}

	/*      */
	/*      */public BigDecimal getBigDecimal(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 829 */return getBigDecimal(paramInt, 0);
		/*      */}

	/*      */
	/*      */public synchronized Object getObject(int paramInt, Map paramMap)
	/*      */throws SQLException
	/*      */{
		/* 840 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 842 */if (localDatum != null)
		/*      */{
			/* 844 */if ((localDatum instanceof STRUCT)) {
				/* 845 */return ((STRUCT) localDatum).toJdbc(paramMap);
				/*      */}
			/* 847 */return localDatum.toJdbc();
			/*      */}
		/*      */
		/* 850 */return null;
		/*      */}

	/*      */
	/*      */public synchronized Ref getRef(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 860 */return getREF(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Blob getBlob(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 870 */return getBLOB(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Clob getClob(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 880 */return getCLOB(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Array getArray(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 891 */return getARRAY(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Date getDate(int paramInt, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 902 */DATE localDATE = getDATE(paramInt);
		/*      */
		/* 904 */return localDATE != null ? localDATE.dateValue(paramCalendar)
				: null;
		/*      */}

	/*      */
	/*      */public synchronized Time getTime(int paramInt, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 915 */DATE localDATE = getDATE(paramInt);
		/*      */
		/* 917 */return localDATE != null ? localDATE.timeValue(paramCalendar)
				: null;
		/*      */}

	/*      */
	/*      */public synchronized Timestamp getTimestamp(int paramInt,
			Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 928 */DATE localDATE = getDATE(paramInt);
		/*      */
		/* 930 */return localDATE != null ? localDATE
				.timestampValue(paramCalendar) : null;
		/*      */}

	/*      */
	/*      */public synchronized URL getURL(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 940 */URL localURL = null;
		/*      */
		/* 942 */int i = getInternalMetadata().getColumnType(paramInt);
		/* 943 */int j = SQLUtil.getInternalType(i);
		/*      */
		/* 946 */if ((j == 96) || (j == 1) || (j == 8))
		/*      */{
			/*      */try
			/*      */{
				/* 951 */localURL = new URL(getString(paramInt));
				/*      */}
			/*      */catch (MalformedURLException localMalformedURLException)
			/*      */{
				/* 955 */DatabaseError.throwSqlException(136);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 960 */throw new SQLException(
					"Conversion to java.net.URL not supported.");
			/*      */}
		/*      */
		/* 963 */return localURL;
		/*      */}

	/*      */
	/*      */public synchronized ResultSet getCursor(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 973 */DatabaseError.throwSqlException(4, "getCursor");
		/*      */
		/* 976 */return null;
		/*      */}

	/*      */
	/*      */public synchronized ROWID getROWID(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 986 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 988 */if (localDatum != null)
		/*      */{
			/* 990 */if ((localDatum instanceof ROWID)) {
				/* 991 */return (ROWID) localDatum;
				/*      */}
			/* 993 */DatabaseError.throwSqlException(4, "getROWID");
			/*      */}
		/*      */
		/* 997 */return null;
		/*      */}

	/*      */
	/*      */public synchronized NUMBER getNUMBER(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1007 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1009 */if (localDatum != null)
		/*      */{
			/* 1011 */if ((localDatum instanceof NUMBER)) {
				/* 1012 */return (NUMBER) localDatum;
				/*      */}
			/* 1014 */DatabaseError.throwSqlException(4, "getNUMBER");
			/*      */}
		/*      */
		/* 1018 */return null;
		/*      */}

	/*      */
	/*      */public synchronized DATE getDATE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1028 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1030 */if (localDatum != null)
		/*      */{
			/* 1032 */if ((localDatum instanceof DATE)) {
				/* 1033 */return (DATE) localDatum;
				/*      */}
			/* 1035 */DatabaseError.throwSqlException(4, "getDATE");
			/*      */}
		/*      */
		/* 1039 */return null;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMP getTIMESTAMP(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1049 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1051 */if (localDatum != null)
		/*      */{
			/* 1053 */if ((localDatum instanceof TIMESTAMP)) {
				/* 1054 */return (TIMESTAMP) localDatum;
				/*      */}
			/* 1056 */DatabaseError.throwSqlException(4, "getTIMESTAMP");
			/*      */}
		/*      */
		/* 1060 */return null;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1070 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1072 */if (localDatum != null)
		/*      */{
			/* 1074 */if ((localDatum instanceof TIMESTAMPTZ)) {
				/* 1075 */return (TIMESTAMPTZ) localDatum;
				/*      */}
			/* 1077 */DatabaseError.throwSqlException(4, "getTIMESTAMPTZ");
			/*      */}
		/*      */
		/* 1081 */return null;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1091 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1093 */if (localDatum != null)
		/*      */{
			/* 1095 */if ((localDatum instanceof TIMESTAMPLTZ)) {
				/* 1096 */return (TIMESTAMPLTZ) localDatum;
				/*      */}
			/* 1098 */DatabaseError.throwSqlException(4, "getTIMESTAMPLTZ");
			/*      */}
		/*      */
		/* 1102 */return null;
		/*      */}

	/*      */
	/*      */public synchronized INTERVALDS getINTERVALDS(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1112 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1114 */if (localDatum != null)
		/*      */{
			/* 1116 */if ((localDatum instanceof INTERVALDS)) {
				/* 1117 */return (INTERVALDS) localDatum;
				/*      */}
			/* 1119 */DatabaseError.throwSqlException(4, "getINTERVALDS");
			/*      */}
		/*      */
		/* 1123 */return null;
		/*      */}

	/*      */
	/*      */public synchronized INTERVALYM getINTERVALYM(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1133 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1135 */if (localDatum != null)
		/*      */{
			/* 1137 */if ((localDatum instanceof INTERVALYM)) {
				/* 1138 */return (INTERVALYM) localDatum;
				/*      */}
			/* 1140 */DatabaseError.throwSqlException(4, "getINTERVALYM");
			/*      */}
		/*      */
		/* 1144 */return null;
		/*      */}

	/*      */
	/*      */public synchronized ARRAY getARRAY(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1154 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1156 */if (localDatum != null)
		/*      */{
			/* 1158 */if ((localDatum instanceof ARRAY)) {
				/* 1159 */return (ARRAY) localDatum;
				/*      */}
			/* 1161 */DatabaseError.throwSqlException(4, "getARRAY");
			/*      */}
		/*      */
		/* 1165 */return null;
		/*      */}

	/*      */
	/*      */public synchronized STRUCT getSTRUCT(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1175 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1177 */if (localDatum != null)
		/*      */{
			/* 1179 */if ((localDatum instanceof STRUCT)) {
				/* 1180 */return (STRUCT) localDatum;
				/*      */}
			/* 1182 */DatabaseError.throwSqlException(4, "getSTRUCT");
			/*      */}
		/*      */
		/* 1186 */return null;
		/*      */}

	/*      */
	/*      */public synchronized OPAQUE getOPAQUE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1196 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1198 */if (localDatum != null)
		/*      */{
			/* 1200 */if ((localDatum instanceof OPAQUE)) {
				/* 1201 */return (OPAQUE) localDatum;
				/*      */}
			/* 1203 */DatabaseError.throwSqlException(4, "getOPAQUE");
			/*      */}
		/*      */
		/* 1207 */return null;
		/*      */}

	/*      */
	/*      */public synchronized REF getREF(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1217 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1219 */if (localDatum != null)
		/*      */{
			/* 1221 */if ((localDatum instanceof REF)) {
				/* 1222 */return (REF) localDatum;
				/*      */}
			/* 1224 */DatabaseError.throwSqlException(4, "getREF");
			/*      */}
		/*      */
		/* 1228 */return null;
		/*      */}

	/*      */
	/*      */public synchronized CHAR getCHAR(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1238 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1240 */if (localDatum != null)
		/*      */{
			/* 1242 */if ((localDatum instanceof CHAR)) {
				/* 1243 */return (CHAR) localDatum;
				/*      */}
			/* 1245 */DatabaseError.throwSqlException(4, "getCHAR");
			/*      */}
		/*      */
		/* 1249 */return null;
		/*      */}

	/*      */
	/*      */public synchronized RAW getRAW(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1259 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1261 */if (localDatum != null)
		/*      */{
			/* 1263 */if ((localDatum instanceof RAW)) {
				/* 1264 */return (RAW) localDatum;
				/*      */}
			/* 1266 */DatabaseError.throwSqlException(4, "getRAW");
			/*      */}
		/*      */
		/* 1270 */return null;
		/*      */}

	/*      */
	/*      */public synchronized BLOB getBLOB(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1280 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1282 */if (localDatum != null)
		/*      */{
			/* 1284 */if ((localDatum instanceof BLOB)) {
				/* 1285 */return (BLOB) localDatum;
				/*      */}
			/* 1287 */DatabaseError.throwSqlException(4, "getBLOB");
			/*      */}
		/*      */
		/* 1291 */return null;
		/*      */}

	/*      */
	/*      */public synchronized CLOB getCLOB(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1301 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1303 */if (localDatum != null)
		/*      */{
			/* 1305 */if ((localDatum instanceof CLOB)) {
				/* 1306 */return (CLOB) localDatum;
				/*      */}
			/* 1308 */DatabaseError.throwSqlException(4, "getCLOB");
			/*      */}
		/*      */
		/* 1312 */return null;
		/*      */}

	/*      */
	/*      */public synchronized BFILE getBFILE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1322 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1324 */if (localDatum != null)
		/*      */{
			/* 1326 */if ((localDatum instanceof BFILE)) {
				/* 1327 */return (BFILE) localDatum;
				/*      */}
			/* 1329 */DatabaseError.throwSqlException(4, "getBFILE");
			/*      */}
		/*      */
		/* 1333 */return null;
		/*      */}

	/*      */
	/*      */public synchronized BFILE getBfile(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1344 */return getBFILE(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized CustomDatum getCustomDatum(int paramInt,
			CustomDatumFactory paramCustomDatumFactory)
	/*      */throws SQLException
	/*      */{
		/* 1355 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1360 */return paramCustomDatumFactory.create(localDatum, 0);
		/*      */}

	/*      */
	/*      */public synchronized ORAData getORAData(int paramInt,
			ORADataFactory paramORADataFactory)
	/*      */throws SQLException
	/*      */{
		/* 1371 */Datum localDatum = getOracleObject(paramInt);
		/*      */
		/* 1376 */return paramORADataFactory.create(localDatum, 0);
		/*      */}

	/*      */
	/*      */public ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/* 1389 */     synchronized (this.connection)
/*      */     {
/* 1391 */       synchronized (this)
/*      */       {
/*      */       }
/*      */ 
/* 1396 */       monitorexit; throw localObject1;
/*      */     }
/*      */   }

	/*      */
	/*      */public synchronized int findColumn(String paramString)
	/*      */throws SQLException
	/*      */{
		/* 1406 */return this.resultSet.findColumn(paramString)
				- this.beginColumnIndex;
		/*      */}

	/*      */
	/*      */public synchronized void setFetchDirection(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1419 */if (paramInt == 1000)
		/*      */{
			/* 1421 */this.usrFetchDirection = paramInt;
			/*      */}
		/* 1423 */else if ((paramInt == 1001) || (paramInt == 1002))
		/*      */{
			/* 1426 */this.usrFetchDirection = paramInt;
			/*      */
			/* 1428 */this.sqlWarning = DatabaseError.addSqlWarning(
					this.sqlWarning, 87);
			/*      */}
		/*      */else
		/*      */{
			/* 1434 */DatabaseError.throwSqlException(68, "setFetchDirection");
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized int getFetchDirection()
	/*      */throws SQLException
	/*      */{
		/* 1446 */return 1000;
		/*      */}

	/*      */
	/*      */public synchronized void setFetchSize(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1455 */this.resultSet.setFetchSize(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized int getFetchSize()
	/*      */throws SQLException
	/*      */{
		/* 1464 */return this.resultSet.getFetchSize();
		/*      */}

	/*      */
	/*      */public int getType()
	/*      */throws SQLException
	/*      */{
		/* 1473 */return this.rsetType;
		/*      */}

	/*      */
	/*      */public int getConcurrency()
	/*      */throws SQLException
	/*      */{
		/* 1482 */return this.rsetConcurency;
		/*      */}

	/*      */
	/*      */public void refreshRow()
	/*      */throws SQLException
	/*      */{
		/* 1498 */if (!needIdentifier(this.rsetType, this.rsetConcurency)) {
			/* 1499 */DatabaseError.throwSqlException(23, "refreshRow");
			/*      */}
		/*      */
		/* 1502 */if (isValidRow(this.currentRow))
		/*      */{
			/* 1504 */int i = getFetchDirection();
			/*      */try
			/*      */{
				/* 1508 */refreshRowsInCache(this.currentRow, getFetchSize(), i);
				/*      */}
			/*      */catch (SQLException localSQLException)
			/*      */{
				/* 1512 */DatabaseError.throwSqlException(localSQLException,
						90, "Unsupported syntax for refreshRow()");
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1517 */DatabaseError.throwSqlException(82, "refreshRow");
			/*      */}
		/*      */}

	/*      */
	/*      */public void setCurrentRowValueAt(Object paramObject1, int paramInt,
			Object paramObject2)
	/*      */throws SQLException
	/*      */{
		/* 1532 */if ((paramObject1 instanceof UpdatableResultSet))
			/* 1533 */putCachedValueAt(this.currentRow, paramInt, paramObject2);
		/*      */else
			/* 1535 */DatabaseError.throwSqlException(1);
		/*      */}

	/*      */
	/*      */private boolean isEmptyResultSet()
	/*      */throws SQLException
	/*      */{
		/* 1547 */if (this.numRowsCached != 0)
		/*      */{
			/* 1549 */return false;
			/*      */}
		/* 1551 */if ((this.numRowsCached == 0) && (this.allRowsCached))
		/*      */{
			/* 1553 */return true;
			/*      */}
		/*      */
		/* 1557 */return !isValidRow(1);
		/*      */}

	/*      */
	/*      */boolean isValidRow(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1571 */if ((paramInt > 0) && (paramInt <= this.numRowsCached))
		/*      */{
			/* 1573 */return true;
			/*      */}
		/* 1575 */if (paramInt <= 0)
		/*      */{
			/* 1577 */return false;
			/*      */}
		/*      */
		/* 1581 */return cacheRowAt(paramInt);
		/*      */}

	/*      */
	/*      */private boolean cacheRowAt(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1598 */while ((this.numRowsCached < paramInt)
				&& (this.resultSet.next()))
		/*      */{
			/* 1600 */for (int i = 0; i < getColumnCount(); i++)
			/*      */{
				/* 1603 */byte[] arrayOfByte = this.resultSet
						.privateGetBytes(i + 1);
				/*      */
				/* 1605 */putCachedValueAt(this.numRowsCached + 1, i + 1,
						arrayOfByte);
				/*      */}
			/*      */
			/* 1608 */this.numRowsCached += 1;
			/*      */}
		/*      */
		/* 1611 */if (this.numRowsCached < paramInt)
		/*      */{
			/* 1613 */this.allRowsCached = true;
			/*      */
			/* 1615 */return false;
			/*      */}
		/*      */
		/* 1619 */return true;
		/*      */}

	/*      */
	/*      */private int cacheAllRows()
	/*      */throws SQLException
	/*      */{
		/* 1632 */while (this.resultSet.next())
		/*      */{
			/* 1634 */for (int i = 0; i < getColumnCount(); i++) {
				/* 1635 */putCachedValueAt(this.numRowsCached + 1, i + 1,
						this.resultSet.privateGetBytes(i + 1));
				/*      */}
			/* 1637 */this.numRowsCached += 1;
			/*      */}
		/*      */
		/* 1640 */this.allRowsCached = true;
		/*      */
		/* 1642 */return this.numRowsCached;
		/*      */}

	/*      */
	/*      */int getColumnCount()
	/*      */throws SQLException
	/*      */{
		/* 1654 */if (this.columnCount == 0)
		/*      */{
			/* 1658 */int i = this.resultSet.statement.numberOfDefinePositions;
			/*      */
			/* 1660 */if ((this.resultSet.statement.accessors != null)
					&& (i > 0))
				/* 1661 */this.columnCount = i;
			/*      */else {
				/* 1663 */this.columnCount = getInternalMetadata()
						.getColumnCount();
				/*      */}
			/*      */}
		/* 1666 */return this.columnCount;
		/*      */}

	/*      */
	/*      */private ResultSetMetaData getInternalMetadata()
	/*      */throws SQLException
	/*      */{
		/* 1678 */if (this.metadata == null) {
			/* 1679 */this.metadata = this.resultSet.getMetaData();
			/*      */}
		/* 1681 */return this.metadata;
		/*      */}

	/*      */
	/*      */private int getLastRow()
	/*      */throws SQLException
	/*      */{
		/* 1693 */if (!this.allRowsCached) {
			/* 1694 */cacheAllRows();
			/*      */}
		/* 1696 */return this.numRowsCached;
		/*      */}

	/*      */
	/*      */private int get_refetch_size(int paramInt1, int paramInt2,
			int paramInt3)
	/*      */throws SQLException
	/*      */{
		/* 1710 */int i = paramInt3 == 1001 ? -1 : 1;
		/*      */
		/* 1713 */int j = 0;
		/*      */
		/* 1715 */if (this.refetchRowids == null)
			/* 1716 */this.refetchRowids = new Vector(10);
		/*      */else {
			/* 1718 */this.refetchRowids.removeAllElements();
			/*      */}
		/*      */
		/* 1721 */while ((j < paramInt2) && (isValidRow(paramInt1 + j * i)))
		/*      */{
			/* 1723 */this.refetchRowids.addElement(getCachedDatumValueAt(
					paramInt1 + j * i, 1));
			/*      */
			/* 1726 */j++;
			/*      */}
		/*      */
		/* 1729 */return j;
		/*      */}

	/*      */
	/*      */private OraclePreparedStatement prepare_refetch_statement(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1747 */if (paramInt < 1) {
			/* 1748 */DatabaseError.throwSqlException(68);
			/*      */}
		/*      */
		/* 1753 */return (OraclePreparedStatement) this.connection
				.prepareStatement(((OracleStatement) this.scrollStmt).sqlObject
						.getRefetchSqlForScrollableResultSet(this, paramInt));
		/*      */}

	/*      */
	/*      */private void prepare_refetch_binds(
			OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1770 */int i = this.scrollStmt.copyBinds(
				paramOraclePreparedStatement, 0);
		/*      */
		/* 1791 */for (int j = 0; j < paramInt; j++)
		/*      */{
			/* 1793 */paramOraclePreparedStatement.setROWID(i + j + 1,
					(ROWID) this.refetchRowids.elementAt(j));
			/*      */}
		/*      */}

	/*      */
	/*      */private void save_refetch_results(
			OracleResultSet paramOracleResultSet, int paramInt1, int paramInt2,
			int paramInt3)
	/*      */throws SQLException
	/*      */{
		/* 1809 */int i = paramInt3 == 1001 ? -1 : 1;
		/*      */
		/* 1811 */while (paramOracleResultSet.next())
		/*      */{
			/* 1813 */ROWID localROWID = paramOracleResultSet.getROWID(1);
			/*      */
			/* 1815 */int j = 0;
			/* 1816 */int k = paramInt1;
			/*      */
			/* 1818 */while ((j == 0) && (k < paramInt1 + paramInt2 * i))
			/*      */{
				/* 1820 */if (((ROWID) getCachedDatumValueAt(k, 1))
						.stringValue().equals(localROWID.stringValue()))
				/*      */{
					/* 1822 */j = 1;
					continue;
					/*      */}
				/* 1824 */k += i;
				/*      */}
			/*      */
			/* 1827 */if (j == 0)
				/*      */continue;
			/* 1829 */for (int m = 0; m < getColumnCount(); m++)
			/*      */{
				/* 1831 */putCachedValueAt(k, m + 1,
						paramOracleResultSet.getOracleObject(m + 1));
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */private Object getCachedValueAt(int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 1846 */return this.rsetCache.get(paramInt1, paramInt2);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1850 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1853 */return null;
		/*      */}

	/*      */
	/*      */private Datum getCachedDatumValueAt(int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 1862 */Object localObject = null;
		/*      */try
		/*      */{
			/* 1866 */localObject = this.rsetCache.get(paramInt1, paramInt2);
			/*      */}
		/*      */catch (IOException localIOException1)
		/*      */{
			/* 1870 */DatabaseError.throwSqlException(localIOException1);
			/*      */}
		/*      */
		/* 1873 */Datum localDatum = null;
		/*      */
		/* 1875 */if (localObject != null)
		/*      */{
			/* 1877 */if ((localObject instanceof Datum))
			/*      */{
				/* 1879 */localDatum = (Datum) localObject;
				/*      */}
			/* 1881 */else if (((byte[]) localObject).length > 0)
			/*      */{
				/* 1883 */int i = getInternalMetadata()
						.getColumnType(paramInt2);
				/* 1884 */int j = getInternalMetadata().getColumnDisplaySize(
						paramInt2);
				/*      */
				/* 1888 */int k = this.scrollStmt.getMaxFieldSize();
				/*      */
				/* 1890 */if ((k > 0) && (k < j)) {
					/* 1891 */j = k;
					/*      */}
				/* 1893 */String str = null;
				/*      */
				/* 1895 */if ((i == 2006) || (i == 2002) || (i == 2008)
						|| (i == 2007) || (i == 2003))
				/*      */{
					/* 1898 */str = getInternalMetadata().getColumnTypeName(
							paramInt2);
					/*      */}
				/* 1900 */int m = SQLUtil.getInternalType(i);
				/*      */
				/* 1903 */short s = this.resultSet.statement.accessors[(paramInt2 - 1)].formOfUse;
				/*      */
				/* 1905 */if ((s == 2)
						&& ((m == 96) || (m == 1) || (m == 8) || (m == 112)))
				/*      */{
					/* 1914 */localDatum = SQLUtil.makeNDatum(this.connection,
							(byte[]) localObject, m, str, s, j);
					/*      */}
				/*      */else
				/*      */{
					/* 1920 */localDatum = SQLUtil.makeDatum(this.connection,
							(byte[]) localObject, m, str, j);
					/*      */}
				/*      */
				/*      */try
				/*      */{
					/* 1926 */this.rsetCache.put(paramInt1, paramInt2,
							localDatum);
					/*      */}
				/*      */catch (IOException localIOException3)
				/*      */{
					/* 1930 */DatabaseError
							.throwSqlException(localIOException3);
					/*      */}
				/*      */}
			/*      */else
			/*      */{
				/*      */try
				/*      */{
					/* 1937 */this.rsetCache.put(paramInt1, paramInt2, null);
					/*      */}
				/*      */catch (IOException localIOException2)
				/*      */{
					/* 1941 */DatabaseError
							.throwSqlException(localIOException2);
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1949 */return localDatum;
		/*      */}

	/*      */
	/*      */private void putCachedValueAt(int paramInt1, int paramInt2,
			Object paramObject)
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 1961 */this.rsetCache.put(paramInt1, paramInt2, paramObject);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1965 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */private void removeCachedRowAt(int paramInt)
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 1977 */this.rsetCache.remove(paramInt);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1981 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public static boolean needIdentifier(int paramInt1, int paramInt2)
	/*      */{
		/* 1997 */return ((paramInt1 != 1003) || (paramInt2 != 1007))
				&& ((paramInt1 != 1004) || (paramInt2 != 1007));
		/*      */}

	/*      */
	/*      */public static boolean needCache(int paramInt1, int paramInt2)
	/*      */{
		/* 2012 */return (paramInt1 != 1003)
				&& ((paramInt1 != 1004) || (paramInt2 != 1007));
		/*      */}

	/*      */
	/*      */public static boolean supportRefreshRow(int paramInt1, int paramInt2)
	/*      */{
		/* 2027 */return (paramInt1 != 1003)
				&& ((paramInt1 != 1004) || (paramInt2 != 1007));
		/*      */}

	/*      */
	/*      */int getFirstUserColumnIndex()
	/*      */{
		/* 2049 */return this.beginColumnIndex;
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.ScrollableResultSet JD-Core Version: 0.6.0
 */