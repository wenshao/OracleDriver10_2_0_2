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
import java.sql.SQLWarning;
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
/*      */class UpdatableResultSet extends BaseResultSet
/*      */{
	/*      */static final int concurrencyType = 1008;
	/*      */static final int beginColumnIndex = 1;
	/*      */PhysicalConnection connection;
	/*      */OracleResultSet resultSet;
	/*      */boolean isCachedRset;
	/*      */ScrollRsetStatement scrollStmt;
	/*      */ResultSetMetaData rsetMetaData;
	/*      */private int rsetType;
	/*      */private int columnCount;
	/*      */private OraclePreparedStatement deleteStmt;
	/*      */private OraclePreparedStatement insertStmt;
	/*      */private OraclePreparedStatement updateStmt;
	/*      */private int[] indexColsChanged;
	/*      */private Object[] rowBuffer;
	/*      */private boolean[] m_nullIndicator;
	/*      */private int[][] typeInfo;
	/*      */private boolean isInserting;
	/*      */private boolean isUpdating;
	/*      */private int wasNull;
	/*      */private static final int VALUE_NULL = 1;
	/*      */private static final int VALUE_NOT_NULL = 2;
	/*      */private static final int VALUE_UNKNOWN = 3;
	/*      */private static final int VALUE_IN_RSET = 4;
	/*      */private static final int ASCII_STREAM = 1;
	/*      */private static final int BINARY_STREAM = 2;
	/*      */private static final int UNICODE_STREAM = 3;
	/* 80 */private static int _MIN_STREAM_SIZE = 4000;
	/*      */
	/* 2860 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	/*      */
	/*      */UpdatableResultSet(ScrollRsetStatement paramScrollRsetStatement,
			ScrollableResultSet paramScrollableResultSet, int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 92 */init(paramScrollRsetStatement, paramScrollableResultSet,
				paramInt1, paramInt2);
		/*      */
		/* 95 */paramScrollableResultSet.resetBeginColumnIndex();
		/*      */
		/* 97 */this.isCachedRset = true;
		/*      */}

	/*      */
	/*      */UpdatableResultSet(ScrollRsetStatement paramScrollRsetStatement,
			OracleResultSetImpl paramOracleResultSetImpl, int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 106 */init(paramScrollRsetStatement, paramOracleResultSetImpl,
				paramInt1, paramInt2);
		/*      */
		/* 108 */this.isCachedRset = false;
		/*      */}

	/*      */
	/*      */private void init(ScrollRsetStatement paramScrollRsetStatement,
			OracleResultSet paramOracleResultSet, int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 121 */if ((paramScrollRsetStatement == null)
				|| (paramOracleResultSet == null) || (paramInt2 != 1008)) {
			/* 122 */DatabaseError.throwSqlException(68);
			/*      */}
		/* 124 */this.connection = ((OracleStatement) paramScrollRsetStatement).connection;
		/* 125 */this.resultSet = paramOracleResultSet;
		/* 126 */this.scrollStmt = paramScrollRsetStatement;
		/* 127 */this.rsetType = paramInt1;
		/* 128 */this.autoRefetch = paramScrollRsetStatement.getAutoRefetch();
		/* 129 */this.deleteStmt = null;
		/* 130 */this.insertStmt = null;
		/* 131 */this.updateStmt = null;
		/* 132 */this.indexColsChanged = null;
		/* 133 */this.rowBuffer = null;
		/* 134 */this.m_nullIndicator = null;
		/* 135 */this.typeInfo = ((int[][]) null);
		/* 136 */this.isInserting = false;
		/* 137 */this.isUpdating = false;
		/* 138 */this.wasNull = -1;
		/* 139 */this.rsetMetaData = null;
		/* 140 */this.columnCount = 0;
		/*      */}

	/*      */
	/*      */public synchronized void close()
	/*      */throws SQLException
	/*      */{
		/* 155 */if (this.resultSet != null) {
			/* 156 */this.resultSet.close();
			/*      */}
		/* 158 */if (this.insertStmt != null) {
			/* 159 */this.insertStmt.close();
			/*      */}
		/* 161 */if (this.updateStmt != null) {
			/* 162 */this.updateStmt.close();
			/*      */}
		/* 164 */if (this.deleteStmt != null) {
			/* 165 */this.deleteStmt.close();
			/*      */}
		/* 167 */if (this.scrollStmt != null) {
			/* 168 */this.scrollStmt.notifyCloseRset();
			/*      */}
		/* 170 */cancelRowInserts();
		/*      */
		/* 172 */this.connection = null;
		/* 173 */this.resultSet = null;
		/* 174 */this.scrollStmt = null;
		/* 175 */this.rsetMetaData = null;
		/* 176 */this.scrollStmt = null;
		/* 177 */this.deleteStmt = null;
		/* 178 */this.insertStmt = null;
		/* 179 */this.updateStmt = null;
		/* 180 */this.indexColsChanged = null;
		/* 181 */this.rowBuffer = null;
		/* 182 */this.m_nullIndicator = null;
		/* 183 */this.typeInfo = ((int[][]) null);
		/*      */}

	/*      */
	/*      */public synchronized boolean wasNull() throws SQLException
	/*      */{
		/* 188 */switch (this.wasNull)
		/*      */{
		/*      */case 1:
			/* 192 */return true;
			/*      */case 2:
			/* 195 */return false;
			/*      */case 4:
			/* 198 */return this.resultSet.wasNull();
			/*      */case 3:
			/*      */}
		/*      */
		/* 203 */DatabaseError.throwSqlException(24);
		/*      */
		/* 206 */return false;
		/*      */}

	/*      */
	/*      */int getFirstUserColumnIndex()
	/*      */{
		/* 219 */return 1;
		/*      */}

	/*      */
	/*      */public synchronized Statement getStatement()
	/*      */throws SQLException
	/*      */{
		/* 225 */return (Statement) this.scrollStmt;
		/*      */}

	/*      */
	/*      */public SQLWarning getWarnings() throws SQLException
	/*      */{
		/* 230 */SQLWarning localSQLWarning1 = this.resultSet.getWarnings();
		/*      */
		/* 232 */if (this.sqlWarning == null) {
			/* 233 */return localSQLWarning1;
			/*      */}
		/*      */
		/* 236 */SQLWarning localSQLWarning2 = this.sqlWarning;
		/*      */
		/* 238 */while (localSQLWarning2.getNextWarning() != null) {
			/* 239 */localSQLWarning2 = localSQLWarning2.getNextWarning();
			/*      */}
		/* 241 */localSQLWarning2.setNextWarning(localSQLWarning1);
		/*      */
		/* 244 */return this.sqlWarning;
		/*      */}

	/*      */
	/*      */public void clearWarnings() throws SQLException
	/*      */{
		/* 249 */this.sqlWarning = null;
		/*      */
		/* 251 */this.resultSet.clearWarnings();
		/*      */}

	/*      */
	/*      */public synchronized boolean next()
	/*      */throws SQLException
	/*      */{
		/* 260 */cancelRowChanges();
		/*      */
		/* 262 */return this.resultSet.next();
		/*      */}

	/*      */
	/*      */public synchronized boolean isBeforeFirst() throws SQLException
	/*      */{
		/* 267 */return this.resultSet.isBeforeFirst();
		/*      */}

	/*      */
	/*      */public synchronized boolean isAfterLast() throws SQLException
	/*      */{
		/* 272 */return this.resultSet.isAfterLast();
		/*      */}

	/*      */
	/*      */public synchronized boolean isFirst() throws SQLException
	/*      */{
		/* 277 */return this.resultSet.isFirst();
		/*      */}

	/*      */
	/*      */public synchronized boolean isLast() throws SQLException
	/*      */{
		/* 282 */return this.resultSet.isLast();
		/*      */}

	/*      */
	/*      */public synchronized void beforeFirst() throws SQLException
	/*      */{
		/* 287 */cancelRowChanges();
		/* 288 */this.resultSet.beforeFirst();
		/*      */}

	/*      */
	/*      */public synchronized void afterLast() throws SQLException
	/*      */{
		/* 293 */cancelRowChanges();
		/* 294 */this.resultSet.afterLast();
		/*      */}

	/*      */
	/*      */public synchronized boolean first() throws SQLException
	/*      */{
		/* 299 */cancelRowChanges();
		/*      */
		/* 301 */return this.resultSet.first();
		/*      */}

	/*      */
	/*      */public synchronized boolean last() throws SQLException
	/*      */{
		/* 306 */cancelRowChanges();
		/*      */
		/* 308 */return this.resultSet.last();
		/*      */}

	/*      */
	/*      */public synchronized int getRow() throws SQLException
	/*      */{
		/* 313 */return this.resultSet.getRow();
		/*      */}

	/*      */
	/*      */public boolean absolute(int paramInt) throws SQLException
	/*      */{
		/* 318 */synchronized (this.connection)
		/*      */{
			/* 320 */synchronized (this)
			/*      */{
				/* 322 */cancelRowChanges();
				/*      */
				/* 324 */return this.resultSet.absolute(paramInt);
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean relative(int paramInt) throws SQLException
	/*      */{
		/* 331 */cancelRowChanges();
		/*      */
		/* 333 */return this.resultSet.relative(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized boolean previous() throws SQLException
	/*      */{
		/* 338 */cancelRowChanges();
		/*      */
		/* 340 */return this.resultSet.previous();
		/*      */}

	/*      */
	/*      */public synchronized Datum getOracleObject(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 349 */Datum localDatum = null;
		/*      */
		/* 351 */setIsNull(3);
		/*      */
		/* 353 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 356 */setIsNull(localDatum == null);
			/*      */
			/* 358 */localDatum = getRowBufferDatumAt(paramInt);
			/*      */}
		/*      */else
		/*      */{
			/* 362 */setIsNull(4);
			/*      */
			/* 364 */localDatum = this.resultSet.getOracleObject(paramInt + 1);
			/*      */}
		/*      */
		/* 367 */return localDatum;
		/*      */}

	/*      */
	/*      */public synchronized String getString(int paramInt) throws SQLException
	/*      */{
		/* 372 */String str = null;
		/*      */
		/* 374 */setIsNull(3);
		/*      */
		/* 376 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 379 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 381 */setIsNull(localDatum == null);
			/*      */
			/* 383 */if (localDatum != null)
				/* 384 */str = localDatum.stringValue();
			/*      */}
		/*      */else
		/*      */{
			/* 388 */setIsNull(4);
			/*      */
			/* 390 */str = this.resultSet.getString(paramInt + 1);
			/*      */}
		/*      */
		/* 393 */return str;
		/*      */}

	/*      */
	/*      */public synchronized boolean getBoolean(int paramInt)
			throws SQLException
	/*      */{
		/* 398 */boolean bool = false;
		/*      */
		/* 400 */setIsNull(3);
		/*      */
		/* 402 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 405 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 407 */setIsNull(localDatum == null);
			/*      */
			/* 409 */if (localDatum != null)
				/* 410 */bool = localDatum.booleanValue();
			/*      */}
		/*      */else
		/*      */{
			/* 414 */setIsNull(4);
			/*      */
			/* 416 */bool = this.resultSet.getBoolean(paramInt + 1);
			/*      */}
		/*      */
		/* 419 */return bool;
		/*      */}

	/*      */
	/*      */public synchronized byte getByte(int paramInt) throws SQLException
	/*      */{
		/* 424 */int i = 0;
		/*      */
		/* 426 */setIsNull(3);
		/*      */
		/* 428 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 431 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 433 */setIsNull(localDatum == null);
			/*      */
			/* 435 */if (localDatum != null)
				/* 436 */i = localDatum.byteValue();
			/*      */}
		/*      */else
		/*      */{
			/* 440 */setIsNull(4);
			/*      */
			/* 442 */i = this.resultSet.getByte(paramInt + 1);
			/*      */}
		/*      */
		/* 445 */return i;
		/*      */}

	/*      */
	/*      */public synchronized short getShort(int paramInt) throws SQLException
	/*      */{
		/* 450 */int i = 0;
		/*      */
		/* 452 */setIsNull(3);
		/*      */
		/* 454 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 457 */long l = getLong(paramInt);
			/*      */
			/* 459 */if ((l > 65537L) || (l < -65538L)) {
				/* 460 */DatabaseError.throwSqlException(26, "getShort");
				/*      */}
			/*      */
			/* 463 */i = (short) (int) l;
			/*      */}
		/*      */else
		/*      */{
			/* 467 */setIsNull(4);
			/*      */
			/* 469 */i = this.resultSet.getShort(paramInt + 1);
			/*      */}
		/*      */
		/* 472 */return i;
		/*      */}

	/*      */
	/*      */public synchronized int getInt(int paramInt) throws SQLException
	/*      */{
		/* 477 */int i = 0;
		/*      */
		/* 479 */setIsNull(3);
		/*      */
		/* 481 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 484 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 486 */setIsNull(localDatum == null);
			/*      */
			/* 488 */if (localDatum != null)
				/* 489 */i = localDatum.intValue();
			/*      */}
		/*      */else
		/*      */{
			/* 493 */setIsNull(4);
			/*      */
			/* 495 */i = this.resultSet.getInt(paramInt + 1);
			/*      */}
		/*      */
		/* 498 */return i;
		/*      */}

	/*      */
	/*      */public synchronized long getLong(int paramInt) throws SQLException
	/*      */{
		/* 503 */long l = 0L;
		/*      */
		/* 505 */setIsNull(3);
		/*      */
		/* 507 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 510 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 512 */setIsNull(localDatum == null);
			/*      */
			/* 514 */if (localDatum != null)
				/* 515 */l = localDatum.longValue();
			/*      */}
		/*      */else
		/*      */{
			/* 519 */setIsNull(4);
			/*      */
			/* 521 */l = this.resultSet.getLong(paramInt + 1);
			/*      */}
		/*      */
		/* 524 */return l;
		/*      */}

	/*      */
	/*      */public synchronized float getFloat(int paramInt) throws SQLException
	/*      */{
		/* 529 */float f = 0.0F;
		/*      */
		/* 531 */setIsNull(3);
		/*      */
		/* 533 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 536 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 538 */setIsNull(localDatum == null);
			/*      */
			/* 540 */if (localDatum != null)
				/* 541 */f = localDatum.floatValue();
			/*      */}
		/*      */else
		/*      */{
			/* 545 */setIsNull(4);
			/*      */
			/* 547 */f = this.resultSet.getFloat(paramInt + 1);
			/*      */}
		/*      */
		/* 550 */return f;
		/*      */}

	/*      */
	/*      */public synchronized double getDouble(int paramInt) throws SQLException
	/*      */{
		/* 555 */double d = 0.0D;
		/*      */
		/* 557 */setIsNull(3);
		/*      */
		/* 559 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 562 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 564 */setIsNull(localDatum == null);
			/*      */
			/* 566 */if (localDatum != null)
				/* 567 */d = localDatum.doubleValue();
			/*      */}
		/*      */else
		/*      */{
			/* 571 */setIsNull(4);
			/*      */
			/* 573 */d = this.resultSet.getDouble(paramInt + 1);
			/*      */}
		/*      */
		/* 576 */return d;
		/*      */}

	/*      */
	/*      */public synchronized BigDecimal getBigDecimal(int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 582 */BigDecimal localBigDecimal = null;
		/*      */
		/* 584 */setIsNull(3);
		/*      */
		/* 586 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt1))))
		/*      */{
			/* 589 */Datum localDatum = getRowBufferDatumAt(paramInt1);
			/*      */
			/* 591 */setIsNull(localDatum == null);
			/*      */
			/* 593 */if (localDatum != null)
				/* 594 */localBigDecimal = localDatum.bigDecimalValue();
			/*      */}
		/*      */else
		/*      */{
			/* 598 */setIsNull(4);
			/*      */
			/* 600 */localBigDecimal = this.resultSet
					.getBigDecimal(paramInt1 + 1);
			/*      */}
		/*      */
		/* 603 */return localBigDecimal;
		/*      */}

	/*      */
	/*      */public synchronized byte[] getBytes(int paramInt) throws SQLException
	/*      */{
		/* 608 */byte[] arrayOfByte = null;
		/*      */
		/* 610 */setIsNull(3);
		/*      */
		/* 612 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 615 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 617 */setIsNull(localDatum == null);
			/*      */
			/* 619 */if (localDatum != null)
				/* 620 */arrayOfByte = localDatum.getBytes();
			/*      */}
		/*      */else
		/*      */{
			/* 624 */setIsNull(4);
			/*      */
			/* 626 */arrayOfByte = this.resultSet.getBytes(paramInt + 1);
			/*      */}
		/*      */
		/* 629 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */public synchronized Date getDate(int paramInt) throws SQLException
	/*      */{
		/* 634 */Date localDate = null;
		/*      */
		/* 636 */setIsNull(3);
		/*      */
		/* 638 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 641 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 643 */setIsNull(localDatum == null);
			/*      */
			/* 645 */if (localDatum != null)
				/* 646 */localDate = localDatum.dateValue();
			/*      */}
		/*      */else
		/*      */{
			/* 650 */setIsNull(4);
			/*      */
			/* 652 */localDate = this.resultSet.getDate(paramInt + 1);
			/*      */}
		/*      */
		/* 655 */return localDate;
		/*      */}

	/*      */
	/*      */public synchronized Time getTime(int paramInt) throws SQLException
	/*      */{
		/* 660 */Time localTime = null;
		/*      */
		/* 662 */setIsNull(3);
		/*      */
		/* 664 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 667 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 669 */setIsNull(localDatum == null);
			/*      */
			/* 671 */if (localDatum != null)
				/* 672 */localTime = localDatum.timeValue();
			/*      */}
		/*      */else
		/*      */{
			/* 676 */setIsNull(4);
			/*      */
			/* 678 */localTime = this.resultSet.getTime(paramInt + 1);
			/*      */}
		/*      */
		/* 681 */return localTime;
		/*      */}

	/*      */
	/*      */public synchronized Timestamp getTimestamp(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 687 */Timestamp localTimestamp = null;
		/*      */
		/* 689 */setIsNull(3);
		/*      */
		/* 691 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 694 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 696 */setIsNull(localDatum == null);
			/*      */
			/* 698 */if (localDatum != null)
				/* 699 */localTimestamp = localDatum.timestampValue();
			/*      */}
		/*      */else
		/*      */{
			/* 703 */setIsNull(4);
			/*      */
			/* 705 */localTimestamp = this.resultSet.getTimestamp(paramInt + 1);
			/*      */}
		/*      */
		/* 708 */return localTimestamp;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getAsciiStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 714 */InputStream localInputStream = null;
		/*      */
		/* 716 */setIsNull(3);
		/*      */
		/* 718 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 721 */Object localObject = getRowBufferAt(paramInt);
			/*      */
			/* 723 */setIsNull(localObject == null);
			/*      */
			/* 725 */if (localObject != null)
			/*      */{
				/* 727 */if ((localObject instanceof InputStream))
				/*      */{
					/* 729 */localInputStream = (InputStream) localObject;
					/*      */}
				/*      */else
				/*      */{
					/* 733 */Datum localDatum = getRowBufferDatumAt(paramInt);
					/*      */
					/* 735 */localInputStream = localDatum.asciiStreamValue();
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 741 */setIsNull(4);
			/*      */
			/* 743 */localInputStream = this.resultSet
					.getAsciiStream(paramInt + 1);
			/*      */}
		/*      */
		/* 746 */return localInputStream;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getUnicodeStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 752 */InputStream localInputStream = null;
		/*      */
		/* 754 */setIsNull(3);
		/*      */
		/* 756 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 759 */Object localObject = getRowBufferAt(paramInt);
			/*      */
			/* 761 */setIsNull(localObject == null);
			/*      */
			/* 763 */if (localObject != null)
			/*      */{
				/* 765 */if ((localObject instanceof InputStream))
				/*      */{
					/* 767 */localInputStream = (InputStream) localObject;
					/*      */}
				/*      */else
				/*      */{
					/* 771 */Datum localDatum = getRowBufferDatumAt(paramInt);
					/* 772 */DBConversion localDBConversion = this.connection.conversion;
					/* 773 */byte[] arrayOfByte = localDatum.shareBytes();
					/*      */
					/* 775 */if ((localDatum instanceof RAW))
					/*      */{
						/* 777 */localInputStream = localDBConversion
								.ConvertStream(new ByteArrayInputStream(
										arrayOfByte), 3);
						/*      */}
					/* 780 */else if ((localDatum instanceof CHAR))
					/*      */{
						/* 782 */localInputStream = localDBConversion
								.ConvertStream(new ByteArrayInputStream(
										arrayOfByte), 1);
						/*      */}
					/*      */else
					/*      */{
						/* 786 */DatabaseError.throwSqlException(4,
								"getUnicodeStream");
						/*      */}
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 793 */setIsNull(4);
			/*      */
			/* 795 */localInputStream = this.resultSet
					.getUnicodeStream(paramInt + 1);
			/*      */}
		/*      */
		/* 798 */return localInputStream;
		/*      */}

	/*      */
	/*      */public synchronized InputStream getBinaryStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 804 */InputStream localInputStream = null;
		/*      */
		/* 806 */setIsNull(3);
		/*      */
		/* 808 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 811 */Object localObject = getRowBufferAt(paramInt);
			/*      */
			/* 813 */setIsNull(localObject == null);
			/*      */
			/* 815 */if (localObject != null)
			/*      */{
				/* 817 */if ((localObject instanceof InputStream))
				/*      */{
					/* 819 */localInputStream = (InputStream) localObject;
					/*      */}
				/*      */else
				/*      */{
					/* 823 */Datum localDatum = getRowBufferDatumAt(paramInt);
					/*      */
					/* 825 */localInputStream = localDatum.binaryStreamValue();
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 831 */setIsNull(4);
			/*      */
			/* 833 */localInputStream = this.resultSet
					.getBinaryStream(paramInt + 1);
			/*      */}
		/*      */
		/* 836 */return localInputStream;
		/*      */}

	/*      */
	/*      */public synchronized Object getObject(int paramInt) throws SQLException
	/*      */{
		/* 841 */Object localObject = null;
		/*      */
		/* 843 */setIsNull(3);
		/*      */
		/* 845 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 848 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 850 */setIsNull(localDatum == null);
			/*      */
			/* 852 */if (localDatum != null)
				/* 853 */localObject = localDatum.toJdbc();
			/*      */}
		/*      */else
		/*      */{
			/* 857 */setIsNull(4);
			/*      */
			/* 859 */localObject = this.resultSet.getObject(paramInt + 1);
			/*      */}
		/*      */
		/* 862 */return localObject;
		/*      */}

	/*      */
	/*      */public synchronized Reader getCharacterStream(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 869 */Reader localReader = null;
		/*      */
		/* 871 */setIsNull(3);
		/*      */
		/* 873 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 876 */Object localObject = getRowBufferAt(paramInt);
			/*      */
			/* 878 */setIsNull(localObject == null);
			/*      */
			/* 880 */if (localObject != null)
			/*      */{
				/* 882 */if ((localObject instanceof Reader))
				/*      */{
					/* 884 */localReader = (Reader) localObject;
					/*      */}
				/*      */else
				/*      */{
					/* 888 */Datum localDatum = getRowBufferDatumAt(paramInt);
					/*      */
					/* 890 */localReader = localDatum.characterStreamValue();
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 896 */setIsNull(4);
			/*      */
			/* 898 */localReader = this.resultSet
					.getCharacterStream(paramInt + 1);
			/*      */}
		/*      */
		/* 901 */return localReader;
		/*      */}

	/*      */
	/*      */public synchronized BigDecimal getBigDecimal(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 908 */BigDecimal localBigDecimal = null;
		/*      */
		/* 910 */setIsNull(3);
		/*      */
		/* 912 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 915 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 917 */setIsNull(localDatum == null);
			/*      */
			/* 919 */if (localDatum != null)
				/* 920 */localBigDecimal = localDatum.bigDecimalValue();
			/*      */}
		/*      */else
		/*      */{
			/* 924 */setIsNull(4);
			/*      */
			/* 926 */localBigDecimal = this.resultSet
					.getBigDecimal(paramInt + 1);
			/*      */}
		/*      */
		/* 929 */return localBigDecimal;
		/*      */}

	/*      */
	/*      */public synchronized Object getObject(int paramInt, Map paramMap)
	/*      */throws SQLException
	/*      */{
		/* 936 */Object localObject = null;
		/*      */
		/* 938 */setIsNull(3);
		/*      */
		/* 940 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 943 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 945 */setIsNull(localDatum == null);
			/*      */
			/* 947 */if (localDatum != null)
			/*      */{
				/* 949 */if ((localDatum instanceof STRUCT))
					/* 950 */localObject = ((STRUCT) localDatum)
							.toJdbc(paramMap);
				/*      */else
					/* 952 */localObject = localDatum.toJdbc();
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 957 */setIsNull(4);
			/*      */
			/* 959 */localObject = this.resultSet.getObject(paramInt + 1,
					paramMap);
			/*      */}
		/*      */
		/* 962 */return localObject;
		/*      */}

	/*      */
	/*      */public synchronized Ref getRef(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 968 */return getREF(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Blob getBlob(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 974 */return getBLOB(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Clob getClob(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 980 */return getCLOB(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Array getArray(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 987 */return getARRAY(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized Date getDate(int paramInt, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 994 */Date localDate = null;
		/*      */
		/* 996 */setIsNull(3);
		/*      */
		/* 998 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1001 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 1003 */setIsNull(localDatum == null);
			/*      */
			/* 1005 */if (localDatum != null)
			/*      */{
				/* 1007 */if ((localDatum instanceof DATE)) {
					/* 1008 */localDate = ((DATE) localDatum)
							.dateValue(paramCalendar);
					/*      */}
				/*      */else {
					/* 1011 */DATE localDATE = new DATE(
							localDatum.stringValue());
					/*      */
					/* 1013 */if (localDATE != null)
						/* 1014 */localDate = localDATE
								.dateValue(paramCalendar);
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1020 */setIsNull(4);
			/*      */
			/* 1022 */localDate = this.resultSet.getDate(paramInt + 1,
					paramCalendar);
			/*      */}
		/*      */
		/* 1025 */return localDate;
		/*      */}

	/*      */
	/*      */public synchronized Time getTime(int paramInt, Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 1032 */Time localTime = null;
		/*      */
		/* 1034 */setIsNull(3);
		/*      */
		/* 1036 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1039 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 1041 */setIsNull(localDatum == null);
			/*      */
			/* 1043 */if (localDatum != null)
			/*      */{
				/* 1045 */if ((localDatum instanceof DATE)) {
					/* 1046 */localTime = ((DATE) localDatum)
							.timeValue(paramCalendar);
					/*      */}
				/*      */else {
					/* 1049 */DATE localDATE = new DATE(
							localDatum.stringValue());
					/*      */
					/* 1051 */if (localDATE != null)
						/* 1052 */localTime = localDATE
								.timeValue(paramCalendar);
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1058 */setIsNull(4);
			/*      */
			/* 1060 */localTime = this.resultSet.getTime(paramInt + 1,
					paramCalendar);
			/*      */}
		/*      */
		/* 1063 */return localTime;
		/*      */}

	/*      */
	/*      */public synchronized Timestamp getTimestamp(int paramInt,
			Calendar paramCalendar)
	/*      */throws SQLException
	/*      */{
		/* 1070 */Timestamp localTimestamp = null;
		/*      */
		/* 1072 */setIsNull(3);
		/*      */
		/* 1074 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1077 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 1079 */setIsNull(localDatum == null);
			/*      */
			/* 1081 */if (localDatum != null)
			/*      */{
				/* 1083 */if ((localDatum instanceof DATE)) {
					/* 1084 */localTimestamp = ((DATE) localDatum)
							.timestampValue(paramCalendar);
					/*      */}
				/*      */else {
					/* 1087 */DATE localDATE = new DATE(
							localDatum.stringValue());
					/*      */
					/* 1089 */if (localDATE != null)
						/* 1090 */localTimestamp = localDATE
								.timestampValue(paramCalendar);
					/*      */}
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1096 */setIsNull(4);
			/*      */
			/* 1098 */localTimestamp = this.resultSet.getTimestamp(
					paramInt + 1, paramCalendar);
			/*      */}
		/*      */
		/* 1101 */return localTimestamp;
		/*      */}

	/*      */
	/*      */public synchronized URL getURL(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1111 */URL localURL = null;
		/*      */
		/* 1113 */int i = getInternalMetadata().getColumnType(paramInt);
		/* 1114 */int j = SQLUtil.getInternalType(i);
		/*      */
		/* 1117 */if ((j == 96) || (j == 1) || (j == 8))
		/*      */{
			/*      */try
			/*      */{
				/* 1122 */localURL = new URL(getString(paramInt));
				/*      */}
			/*      */catch (MalformedURLException localMalformedURLException)
			/*      */{
				/* 1126 */DatabaseError.throwSqlException(136);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1131 */throw new SQLException(
					"Conversion to java.net.URL not supported.");
			/*      */}
		/*      */
		/* 1134 */return localURL;
		/*      */}

	/*      */
	/*      */public synchronized ResultSet getCursor(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1140 */ResultSet localResultSet = null;
		/*      */
		/* 1142 */setIsNull(3);
		/*      */
		/* 1144 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1147 */Datum localDatum = getOracleObject(paramInt);
			/*      */
			/* 1149 */setIsNull(localDatum == null);
			/* 1150 */DatabaseError.throwSqlException(4, "getCursor");
			/*      */}
		/*      */else
		/*      */{
			/* 1155 */setIsNull(4);
			/*      */
			/* 1157 */localResultSet = this.resultSet.getCursor(paramInt + 1);
			/*      */}
		/*      */
		/* 1160 */return localResultSet;
		/*      */}

	/*      */
	/*      */public synchronized ROWID getROWID(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1166 */ROWID localROWID = null;
		/*      */
		/* 1168 */setIsNull(3);
		/*      */
		/* 1170 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1173 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1175 */setIsNull(localDatum == null);
			/*      */
			/* 1177 */if ((localDatum != null)
					&& (!(localDatum instanceof ROWID))) {
				/* 1178 */DatabaseError.throwSqlException(4, "getROWID");
				/*      */}
			/*      */
			/* 1181 */localROWID = (ROWID) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1185 */setIsNull(4);
			/*      */
			/* 1187 */localROWID = this.resultSet.getROWID(paramInt + 1);
			/*      */}
		/*      */
		/* 1190 */return localROWID;
		/*      */}

	/*      */
	/*      */public synchronized NUMBER getNUMBER(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1196 */NUMBER localNUMBER = null;
		/*      */
		/* 1198 */setIsNull(3);
		/*      */
		/* 1200 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1203 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1205 */setIsNull(localDatum == null);
			/*      */
			/* 1207 */if ((localDatum != null)
					&& (!(localDatum instanceof NUMBER))) {
				/* 1208 */DatabaseError.throwSqlException(4, "getNUMBER");
				/*      */}
			/*      */
			/* 1211 */localNUMBER = (NUMBER) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1215 */setIsNull(4);
			/*      */
			/* 1217 */localNUMBER = this.resultSet.getNUMBER(paramInt + 1);
			/*      */}
		/*      */
		/* 1220 */return localNUMBER;
		/*      */}

	/*      */
	/*      */public synchronized DATE getDATE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1226 */DATE localDATE = null;
		/*      */
		/* 1228 */setIsNull(3);
		/*      */
		/* 1230 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1233 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1235 */setIsNull(localDatum == null);
			/*      */
			/* 1237 */if ((localDatum != null)
					&& (!(localDatum instanceof DATE))) {
				/* 1238 */DatabaseError.throwSqlException(4, "getDATE");
				/*      */}
			/*      */
			/* 1241 */localDATE = (DATE) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1245 */setIsNull(4);
			/*      */
			/* 1247 */localDATE = this.resultSet.getDATE(paramInt + 1);
			/*      */}
		/*      */
		/* 1250 */return localDATE;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMP getTIMESTAMP(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1256 */TIMESTAMP localTIMESTAMP = null;
		/*      */
		/* 1258 */setIsNull(3);
		/*      */
		/* 1260 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1263 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1265 */setIsNull(localDatum == null);
			/*      */
			/* 1267 */if ((localDatum != null)
					&& (!(localDatum instanceof TIMESTAMP))) {
				/* 1268 */DatabaseError.throwSqlException(4, "getTIMESTAMP");
				/*      */}
			/*      */
			/* 1271 */localTIMESTAMP = (TIMESTAMP) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1275 */setIsNull(4);
			/*      */
			/* 1277 */localTIMESTAMP = this.resultSet
					.getTIMESTAMP(paramInt + 1);
			/*      */}
		/*      */
		/* 1280 */return localTIMESTAMP;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1286 */TIMESTAMPTZ localTIMESTAMPTZ = null;
		/*      */
		/* 1288 */setIsNull(3);
		/*      */
		/* 1290 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1293 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1295 */setIsNull(localDatum == null);
			/*      */
			/* 1297 */if ((localDatum != null)
					&& (!(localDatum instanceof TIMESTAMPTZ))) {
				/* 1298 */DatabaseError.throwSqlException(4, "getTIMESTAMPTZ");
				/*      */}
			/*      */
			/* 1301 */localTIMESTAMPTZ = (TIMESTAMPTZ) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1305 */setIsNull(4);
			/*      */
			/* 1307 */localTIMESTAMPTZ = this.resultSet
					.getTIMESTAMPTZ(paramInt + 1);
			/*      */}
		/*      */
		/* 1310 */return localTIMESTAMPTZ;
		/*      */}

	/*      */
	/*      */public synchronized TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1316 */TIMESTAMPLTZ localTIMESTAMPLTZ = null;
		/*      */
		/* 1318 */setIsNull(3);
		/*      */
		/* 1320 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1323 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1325 */setIsNull(localDatum == null);
			/*      */
			/* 1327 */if ((localDatum != null)
					&& (!(localDatum instanceof TIMESTAMPLTZ))) {
				/* 1328 */DatabaseError.throwSqlException(4, "getTIMESTAMPLTZ");
				/*      */}
			/*      */
			/* 1331 */localTIMESTAMPLTZ = (TIMESTAMPLTZ) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1335 */setIsNull(4);
			/*      */
			/* 1337 */localTIMESTAMPLTZ = this.resultSet
					.getTIMESTAMPLTZ(paramInt + 1);
			/*      */}
		/*      */
		/* 1340 */return localTIMESTAMPLTZ;
		/*      */}

	/*      */
	/*      */public synchronized INTERVALDS getINTERVALDS(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1346 */INTERVALDS localINTERVALDS = null;
		/*      */
		/* 1348 */setIsNull(3);
		/*      */
		/* 1350 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1353 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1355 */setIsNull(localDatum == null);
			/*      */
			/* 1357 */if ((localDatum != null)
					&& (!(localDatum instanceof INTERVALDS))) {
				/* 1358 */DatabaseError.throwSqlException(4, "getINTERVALDS");
				/*      */}
			/*      */
			/* 1361 */localINTERVALDS = (INTERVALDS) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1365 */setIsNull(4);
			/*      */
			/* 1367 */localINTERVALDS = this.resultSet
					.getINTERVALDS(paramInt + 1);
			/*      */}
		/*      */
		/* 1370 */return localINTERVALDS;
		/*      */}

	/*      */
	/*      */public synchronized INTERVALYM getINTERVALYM(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1376 */INTERVALYM localINTERVALYM = null;
		/*      */
		/* 1378 */setIsNull(3);
		/*      */
		/* 1380 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1383 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1385 */setIsNull(localDatum == null);
			/*      */
			/* 1387 */if ((localDatum != null)
					&& (!(localDatum instanceof INTERVALYM))) {
				/* 1388 */DatabaseError.throwSqlException(4, "getINTERVALYM");
				/*      */}
			/*      */
			/* 1391 */localINTERVALYM = (INTERVALYM) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1395 */setIsNull(4);
			/*      */
			/* 1397 */localINTERVALYM = this.resultSet
					.getINTERVALYM(paramInt + 1);
			/*      */}
		/*      */
		/* 1400 */return localINTERVALYM;
		/*      */}

	/*      */
	/*      */public synchronized ARRAY getARRAY(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1406 */ARRAY localARRAY = null;
		/*      */
		/* 1408 */setIsNull(3);
		/*      */
		/* 1410 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1413 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1415 */setIsNull(localDatum == null);
			/*      */
			/* 1417 */if ((localDatum != null)
					&& (!(localDatum instanceof ARRAY))) {
				/* 1418 */DatabaseError.throwSqlException(4, "getARRAY");
				/*      */}
			/*      */
			/* 1421 */localARRAY = (ARRAY) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1425 */setIsNull(4);
			/*      */
			/* 1427 */localARRAY = this.resultSet.getARRAY(paramInt + 1);
			/*      */}
		/*      */
		/* 1430 */return localARRAY;
		/*      */}

	/*      */
	/*      */public synchronized STRUCT getSTRUCT(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1436 */STRUCT localSTRUCT = null;
		/*      */
		/* 1438 */setIsNull(3);
		/*      */
		/* 1440 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1443 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1445 */setIsNull(localDatum == null);
			/*      */
			/* 1447 */if ((localDatum != null)
					&& (!(localDatum instanceof STRUCT))) {
				/* 1448 */DatabaseError.throwSqlException(4, "getSTRUCT");
				/*      */}
			/*      */
			/* 1451 */localSTRUCT = (STRUCT) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1455 */setIsNull(4);
			/*      */
			/* 1457 */localSTRUCT = this.resultSet.getSTRUCT(paramInt + 1);
			/*      */}
		/*      */
		/* 1460 */return localSTRUCT;
		/*      */}

	/*      */
	/*      */public synchronized OPAQUE getOPAQUE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1466 */OPAQUE localOPAQUE = null;
		/*      */
		/* 1468 */setIsNull(3);
		/*      */
		/* 1470 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1473 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1475 */setIsNull(localDatum == null);
			/*      */
			/* 1477 */if ((localDatum != null)
					&& (!(localDatum instanceof OPAQUE))) {
				/* 1478 */DatabaseError.throwSqlException(4, "getOPAQUE");
				/*      */}
			/*      */
			/* 1481 */localOPAQUE = (OPAQUE) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1485 */setIsNull(4);
			/*      */
			/* 1487 */localOPAQUE = this.resultSet.getOPAQUE(paramInt + 1);
			/*      */}
		/*      */
		/* 1490 */return localOPAQUE;
		/*      */}

	/*      */
	/*      */public synchronized REF getREF(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1496 */REF localREF = null;
		/*      */
		/* 1498 */setIsNull(3);
		/*      */
		/* 1500 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1503 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1505 */setIsNull(localDatum == null);
			/*      */
			/* 1507 */if ((localDatum != null)
					&& (!(localDatum instanceof REF))) {
				/* 1508 */DatabaseError.throwSqlException(4, "getREF");
				/*      */}
			/*      */
			/* 1511 */localREF = (REF) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1515 */setIsNull(4);
			/*      */
			/* 1517 */localREF = this.resultSet.getREF(paramInt + 1);
			/*      */}
		/*      */
		/* 1520 */return localREF;
		/*      */}

	/*      */
	/*      */public synchronized CHAR getCHAR(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1526 */CHAR localCHAR = null;
		/*      */
		/* 1528 */setIsNull(3);
		/*      */
		/* 1530 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1533 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1535 */setIsNull(localDatum == null);
			/*      */
			/* 1537 */if ((localDatum != null)
					&& (!(localDatum instanceof CHAR))) {
				/* 1538 */DatabaseError.throwSqlException(4, "getCHAR");
				/*      */}
			/*      */
			/* 1541 */localCHAR = (CHAR) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1545 */setIsNull(4);
			/*      */
			/* 1547 */localCHAR = this.resultSet.getCHAR(paramInt + 1);
			/*      */}
		/*      */
		/* 1550 */return localCHAR;
		/*      */}

	/*      */
	/*      */public synchronized RAW getRAW(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1556 */RAW localRAW = null;
		/*      */
		/* 1558 */setIsNull(3);
		/*      */
		/* 1560 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1563 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1565 */setIsNull(localDatum == null);
			/*      */
			/* 1567 */if ((localDatum != null)
					&& (!(localDatum instanceof RAW))) {
				/* 1568 */DatabaseError.throwSqlException(4, "getRAW");
				/*      */}
			/*      */
			/* 1571 */localRAW = (RAW) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1575 */setIsNull(4);
			/*      */
			/* 1577 */localRAW = this.resultSet.getRAW(paramInt + 1);
			/*      */}
		/*      */
		/* 1580 */return localRAW;
		/*      */}

	/*      */
	/*      */public synchronized BLOB getBLOB(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1586 */BLOB localBLOB = null;
		/*      */
		/* 1588 */setIsNull(3);
		/*      */
		/* 1590 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1593 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1595 */setIsNull(localDatum == null);
			/*      */
			/* 1597 */if ((localDatum != null)
					&& (!(localDatum instanceof BLOB))) {
				/* 1598 */DatabaseError.throwSqlException(4, "getBLOB");
				/*      */}
			/*      */
			/* 1601 */localBLOB = (BLOB) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1605 */setIsNull(4);
			/*      */
			/* 1607 */localBLOB = this.resultSet.getBLOB(paramInt + 1);
			/*      */}
		/*      */
		/* 1610 */return localBLOB;
		/*      */}

	/*      */
	/*      */public synchronized CLOB getCLOB(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1616 */CLOB localCLOB = null;
		/*      */
		/* 1618 */setIsNull(3);
		/*      */
		/* 1620 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1623 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1625 */setIsNull(localDatum == null);
			/*      */
			/* 1627 */if ((localDatum != null)
					&& (!(localDatum instanceof CLOB))) {
				/* 1628 */DatabaseError.throwSqlException(4, "getCLOB");
				/*      */}
			/*      */
			/* 1631 */localCLOB = (CLOB) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1635 */setIsNull(4);
			/*      */
			/* 1637 */localCLOB = this.resultSet.getCLOB(paramInt + 1);
			/*      */}
		/*      */
		/* 1640 */return localCLOB;
		/*      */}

	/*      */
	/*      */public synchronized BFILE getBFILE(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1646 */BFILE localBFILE = null;
		/*      */
		/* 1648 */setIsNull(3);
		/*      */
		/* 1650 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1653 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1655 */setIsNull(localDatum == null);
			/*      */
			/* 1657 */if ((localDatum != null)
					&& (!(localDatum instanceof BFILE))) {
				/* 1658 */DatabaseError.throwSqlException(4, "getBFILE");
				/*      */}
			/*      */
			/* 1661 */localBFILE = (BFILE) localDatum;
			/*      */}
		/*      */else
		/*      */{
			/* 1665 */setIsNull(4);
			/*      */
			/* 1667 */localBFILE = this.resultSet.getBFILE(paramInt + 1);
			/*      */}
		/*      */
		/* 1670 */return localBFILE;
		/*      */}

	/*      */
	/*      */public synchronized BFILE getBfile(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1676 */return getBFILE(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized CustomDatum getCustomDatum(int paramInt,
			CustomDatumFactory paramCustomDatumFactory)
	/*      */throws SQLException
	/*      */{
		/* 1683 */if (paramCustomDatumFactory == null) {
			/* 1684 */DatabaseError.throwSqlException(68);
			/*      */}
		/* 1686 */CustomDatum localCustomDatum = null;
		/*      */
		/* 1688 */setIsNull(3);
		/*      */
		/* 1690 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1693 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1695 */setIsNull(localDatum == null);
			/*      */
			/* 1697 */localCustomDatum = paramCustomDatumFactory.create(
					localDatum, 0);
			/*      */}
		/*      */else
		/*      */{
			/* 1701 */setIsNull(4);
			/*      */
			/* 1703 */localCustomDatum = this.resultSet.getCustomDatum(
					paramInt + 1, paramCustomDatumFactory);
			/*      */}
		/*      */
		/* 1706 */return localCustomDatum;
		/*      */}

	/*      */
	/*      */public synchronized ORAData getORAData(int paramInt,
			ORADataFactory paramORADataFactory)
	/*      */throws SQLException
	/*      */{
		/* 1713 */if (paramORADataFactory == null) {
			/* 1714 */DatabaseError.throwSqlException(68);
			/*      */}
		/* 1716 */ORAData localORAData = null;
		/*      */
		/* 1718 */setIsNull(3);
		/*      */
		/* 1720 */if ((isOnInsertRow())
				|| ((isUpdatingRow()) && (isRowBufferUpdatedAt(paramInt))))
		/*      */{
			/* 1723 */Datum localDatum = getRowBufferDatumAt(paramInt);
			/*      */
			/* 1725 */setIsNull(localDatum == null);
			/*      */
			/* 1727 */localORAData = paramORADataFactory.create(localDatum, 0);
			/*      */}
		/*      */else
		/*      */{
			/* 1731 */setIsNull(4);
			/*      */
			/* 1733 */localORAData = this.resultSet.getORAData(paramInt + 1,
					paramORADataFactory);
			/*      */}
		/*      */
		/* 1736 */return localORAData;
		/*      */}

	/*      */
	/*      */public ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/* 1745 */     if (((OracleStatement)this.scrollStmt).closed) {
/* 1746 */       DatabaseError.throwSqlException(9, "getMetaData");
/*      */     }
/*      */ 
/* 1749 */     synchronized (this.connection)
/*      */     {
/* 1751 */       synchronized (this)
/*      */       {
/*      */       }
/*      */ 
/* 1756 */       monitorexit; throw localObject1;
/*      */     }
/*      */   }

	/*      */
	/*      */public synchronized int findColumn(String paramString)
			throws SQLException
	/*      */{
		/* 1762 */return this.resultSet.findColumn(paramString) - 1;
		/*      */}

	/*      */
	/*      */public synchronized void setFetchDirection(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1771 */this.resultSet.setFetchDirection(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized int getFetchDirection() throws SQLException
	/*      */{
		/* 1776 */return this.resultSet.getFetchDirection();
		/*      */}

	/*      */
	/*      */public synchronized void setFetchSize(int paramInt)
			throws SQLException
	/*      */{
		/* 1781 */this.resultSet.setFetchSize(paramInt);
		/*      */}

	/*      */
	/*      */public synchronized int getFetchSize() throws SQLException
	/*      */{
		/* 1786 */return this.resultSet.getFetchSize();
		/*      */}

	/*      */
	/*      */public int getType() throws SQLException
	/*      */{
		/* 1791 */return this.rsetType;
		/*      */}

	/*      */
	/*      */public int getConcurrency() throws SQLException
	/*      */{
		/* 1796 */return 1008;
		/*      */}

	/*      */
	/*      */public boolean rowUpdated()
	/*      */throws SQLException
	/*      */{
		/* 1805 */return false;
		/*      */}

	/*      */
	/*      */public boolean rowInserted() throws SQLException
	/*      */{
		/* 1810 */return false;
		/*      */}

	/*      */
	/*      */public boolean rowDeleted() throws SQLException
	/*      */{
		/* 1815 */return false;
		/*      */}

	/*      */
	/*      */public synchronized void insertRow() throws SQLException
	/*      */{
		/* 1820 */if (!isOnInsertRow()) {
			/* 1821 */DatabaseError.throwSqlException(83);
			/*      */}
		/* 1823 */prepareInsertRowStatement();
		/* 1824 */prepareInsertRowBinds();
		/* 1825 */executeInsertRow();
		/*      */}

	/*      */
	/*      */public void updateRow()
	/*      */throws SQLException
	/*      */{
		/* 1831 */synchronized (this.connection)
		/*      */{
			/* 1833 */synchronized (this)
			/*      */{
				/* 1836 */if (isOnInsertRow()) {
					/* 1837 */DatabaseError.throwSqlException(84);
					/*      */}
				/* 1839 */int i = getNumColumnsChanged();
				/*      */
				/* 1841 */if (i > 0)
				/*      */{
					/* 1843 */prepareUpdateRowStatement(i);
					/* 1844 */prepareUpdateRowBinds(i);
					/* 1845 */executeUpdateRow();
					/*      */}
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void deleteRow()
	/*      */throws SQLException
	/*      */{
		/* 1855 */if (isOnInsertRow()) {
			/* 1856 */DatabaseError.throwSqlException(84);
			/*      */}
		/* 1858 */prepareDeleteRowStatement();
		/* 1859 */prepareDeleteRowBinds();
		/* 1860 */executeDeleteRow();
		/*      */}

	/*      */
	/*      */public void refreshRow() throws SQLException
	/*      */{
		/* 1865 */synchronized (this.connection)
		/*      */{
			/* 1867 */synchronized (this)
			/*      */{
				/* 1871 */if (isOnInsertRow()) {
					/* 1872 */DatabaseError.throwSqlException(84);
					/*      */}
				/* 1874 */this.resultSet.refreshRow();
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void cancelRowUpdates() throws SQLException
	/*      */{
		/* 1881 */if (this.isUpdating)
		/*      */{
			/* 1883 */this.isUpdating = false;
			/*      */
			/* 1885 */clearRowBuffer();
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void moveToInsertRow() throws SQLException
	/*      */{
		/* 1891 */if (isOnInsertRow()) {
			/* 1892 */return;
			/*      */}
		/* 1894 */this.isInserting = true;
		/*      */
		/* 1897 */if (this.rowBuffer == null) {
			/* 1898 */this.rowBuffer = new Object[getColumnCount()];
			/*      */}
		/* 1900 */if (this.m_nullIndicator == null) {
			/* 1901 */this.m_nullIndicator = new boolean[getColumnCount()];
			/*      */}
		/* 1903 */clearRowBuffer();
		/*      */}

	/*      */
	/*      */public synchronized void moveToCurrentRow() throws SQLException
	/*      */{
		/* 1908 */cancelRowInserts();
		/*      */}

	/*      */
	/*      */public synchronized void updateString(int paramInt, String paramString)
	/*      */throws SQLException
	/*      */{
		/* 1914 */updateObject(paramInt, paramString);
		/*      */}

	/*      */
	/*      */public synchronized void updateNull(int paramInt) throws SQLException
	/*      */{
		/* 1919 */setRowBufferAt(paramInt, null);
		/*      */}

	/*      */
	/*      */public void updateBoolean(int paramInt, boolean paramBoolean)
			throws SQLException
	/*      */{
		/* 1924 */updateObject(paramInt, new Boolean(paramBoolean));
		/*      */}

	/*      */
	/*      */public void updateByte(int paramInt, byte paramByte)
			throws SQLException
	/*      */{
		/* 1929 */updateObject(paramInt, new Integer(paramByte));
		/*      */}

	/*      */
	/*      */public void updateShort(int paramInt, short paramShort)
			throws SQLException
	/*      */{
		/* 1934 */updateObject(paramInt, new Integer(paramShort));
		/*      */}

	/*      */
	/*      */public void updateInt(int paramInt1, int paramInt2)
			throws SQLException
	/*      */{
		/* 1939 */updateObject(paramInt1, new Integer(paramInt2));
		/*      */}

	/*      */
	/*      */public void updateLong(int paramInt, long paramLong)
			throws SQLException
	/*      */{
		/* 1944 */updateObject(paramInt, new Long(paramLong));
		/*      */}

	/*      */
	/*      */public void updateFloat(int paramInt, float paramFloat)
			throws SQLException
	/*      */{
		/* 1949 */updateObject(paramInt, new Float(paramFloat));
		/*      */}

	/*      */
	/*      */public void updateDouble(int paramInt, double paramDouble)
			throws SQLException
	/*      */{
		/* 1954 */updateObject(paramInt, new Double(paramDouble));
		/*      */}

	/*      */
	/*      */public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal)
	/*      */throws SQLException
	/*      */{
		/* 1960 */updateObject(paramInt, paramBigDecimal);
		/*      */}

	/*      */
	/*      */public void updateBytes(int paramInt, byte[] paramArrayOfByte)
			throws SQLException
	/*      */{
		/* 1965 */updateObject(paramInt, paramArrayOfByte);
		/*      */}

	/*      */
	/*      */public void updateDate(int paramInt, Date paramDate)
			throws SQLException
	/*      */{
		/* 1970 */updateObject(paramInt, paramDate);
		/*      */}

	/*      */
	/*      */public void updateTime(int paramInt, Time paramTime)
			throws SQLException
	/*      */{
		/* 1975 */updateObject(paramInt, paramTime);
		/*      */}

	/*      */
	/*      */public void updateTimestamp(int paramInt, Timestamp paramTimestamp)
	/*      */throws SQLException
	/*      */{
		/* 1981 */updateObject(paramInt, paramTimestamp);
		/*      */}

	/*      */
	/*      */public void updateAsciiStream(int paramInt1,
			InputStream paramInputStream, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 1987 */int i = getInternalMetadata().getColumnType(1 + paramInt1);
		/*      */
		/* 1989 */if ((paramInputStream != null) && (paramInt2 > 0))
		/*      */{
			/*      */Object localObject;
			/* 1991 */if (!isStreamType(i))
			/*      */{
				/* 1993 */localObject = new byte[paramInt2];
				/*      */try
				/*      */{
					/* 1997 */int j = paramInputStream.read(localObject);
					/*      */
					/* 1999 */paramInputStream.close();
					/*      */
					/* 2001 */String str = new String(localObject, 0, j);
					/*      */
					/* 2003 */updateString(paramInt1, str);
					/*      */}
				/*      */catch (IOException localIOException)
				/*      */{
					/* 2007 */DatabaseError.throwSqlException(localIOException);
					/*      */}
				/*      */}
			/*      */else
			/*      */{
				/* 2012 */localObject = new int[] { paramInt2, 1 };
				/*      */
				/* 2017 */setRowBufferAt(paramInt1, paramInputStream,
						localObject);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 2022 */setRowBufferAt(paramInt1, null);
			/*      */}
		/*      */}

	/*      */
	/*      */final boolean isStreamType(int paramInt)
	/*      */{
		/* 2028 */return (paramInt == 2004) || (paramInt == 2005)
				|| (paramInt == -4) || (paramInt == -1);
		/*      */}

	/*      */
	/*      */public void updateBinaryStream(int paramInt1,
			InputStream paramInputStream, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 2037 */int i = getInternalMetadata().getColumnType(1 + paramInt1);
		/*      */
		/* 2039 */if ((paramInputStream != null) && (paramInt2 > 0))
		/*      */{
			/*      */Object localObject;
			/* 2041 */if (!isStreamType(i))
			/*      */{
				/* 2043 */localObject = new byte[paramInt2];
				/*      */try
				/*      */{
					/* 2047 */int j = paramInputStream.read(localObject);
					/*      */
					/* 2049 */paramInputStream.close();
					/* 2050 */updateBytes(paramInt1, localObject);
					/*      */}
				/*      */catch (IOException localIOException)
				/*      */{
					/* 2054 */DatabaseError.throwSqlException(localIOException);
					/*      */}
				/*      */}
			/*      */else
			/*      */{
				/* 2059 */localObject = new int[] { paramInt2, 2 };
				/*      */
				/* 2064 */setRowBufferAt(paramInt1, paramInputStream,
						localObject);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 2069 */setRowBufferAt(paramInt1, null);
			/*      */}
		/*      */}

	/*      */
	/*      */public void updateCharacterStream(int paramInt1, Reader paramReader,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 2076 */int i = getInternalMetadata().getColumnType(1 + paramInt1);
		/*      */
		/* 2078 */if ((paramReader != null) && (paramInt2 > 0))
		/*      */{
			/*      */Object localObject;
			/* 2080 */if (!isStreamType(i))
			/*      */{
				/* 2082 */localObject = new char[paramInt2];
				/*      */try
				/*      */{
					/* 2086 */int j = paramReader.read(localObject);
					/*      */
					/* 2088 */paramReader.close();
					/* 2089 */updateString(paramInt1, new String(localObject));
					/*      */}
				/*      */catch (IOException localIOException)
				/*      */{
					/* 2093 */DatabaseError.throwSqlException(localIOException);
					/*      */}
				/*      */}
			/*      */else
			/*      */{
				/* 2098 */localObject = new int[] { paramInt2 };
				/*      */
				/* 2103 */setRowBufferAt(paramInt1, paramReader, localObject);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 2108 */setRowBufferAt(paramInt1, null);
			/*      */}
		/*      */}

	/*      */
	/*      */public void updateObject(int paramInt1, Object paramObject,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 2115 */updateObject(paramInt1, paramObject);
		/*      */}

	/*      */
	/*      */public synchronized void updateObject(int paramInt, Object paramObject)
	/*      */throws SQLException
	/*      */{
		/* 2121 */Datum localDatum = null;
		/*      */
		/* 2123 */if (paramObject != null)
		/*      */{
			/* 2125 */if ((paramObject instanceof Datum)) {
				/* 2126 */localDatum = (Datum) paramObject;
				/*      */}
			/*      */else {
				/* 2129 */OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData) getInternalMetadata();
				/* 2130 */int i = paramInt + 1;
				/* 2131 */localDatum = SQLUtil.makeOracleDatum(this.connection,
						paramObject,
						localOracleResultSetMetaData.getColumnType(i), null,
						localOracleResultSetMetaData.isNCHAR(i));
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2136 */setRowBufferAt(paramInt, localDatum);
		/*      */}

	/*      */
	/*      */public synchronized void updateOracleObject(int paramInt,
			Datum paramDatum)
	/*      */throws SQLException
	/*      */{
		/* 2142 */setRowBufferAt(paramInt, paramDatum);
		/*      */}

	/*      */
	/*      */public void updateROWID(int paramInt, ROWID paramROWID)
			throws SQLException
	/*      */{
		/* 2147 */updateOracleObject(paramInt, paramROWID);
		/*      */}

	/*      */
	/*      */public void updateNUMBER(int paramInt, NUMBER paramNUMBER)
			throws SQLException
	/*      */{
		/* 2152 */updateOracleObject(paramInt, paramNUMBER);
		/*      */}

	/*      */
	/*      */public void updateDATE(int paramInt, DATE paramDATE)
			throws SQLException
	/*      */{
		/* 2157 */updateOracleObject(paramInt, paramDATE);
		/*      */}

	/*      */
	/*      */public void updateINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
	/*      */throws SQLException
	/*      */{
		/* 2163 */updateOracleObject(paramInt, paramINTERVALYM);
		/*      */}

	/*      */
	/*      */public void updateINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
	/*      */throws SQLException
	/*      */{
		/* 2169 */updateOracleObject(paramInt, paramINTERVALDS);
		/*      */}

	/*      */
	/*      */public void updateTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
			throws SQLException
	/*      */{
		/* 2174 */updateOracleObject(paramInt, paramTIMESTAMP);
		/*      */}

	/*      */
	/*      */public void updateTIMESTAMPTZ(int paramInt,
			TIMESTAMPTZ paramTIMESTAMPTZ)
	/*      */throws SQLException
	/*      */{
		/* 2180 */updateOracleObject(paramInt, paramTIMESTAMPTZ);
		/*      */}

	/*      */
	/*      */public void updateTIMESTAMPLTZ(int paramInt,
			TIMESTAMPLTZ paramTIMESTAMPLTZ)
	/*      */throws SQLException
	/*      */{
		/* 2186 */updateOracleObject(paramInt, paramTIMESTAMPLTZ);
		/*      */}

	/*      */
	/*      */public void updateARRAY(int paramInt, ARRAY paramARRAY)
			throws SQLException
	/*      */{
		/* 2191 */updateOracleObject(paramInt, paramARRAY);
		/*      */}

	/*      */
	/*      */public void updateSTRUCT(int paramInt, STRUCT paramSTRUCT)
			throws SQLException
	/*      */{
		/* 2196 */updateOracleObject(paramInt, paramSTRUCT);
		/*      */}

	/*      */
	/*      */public void updateOPAQUE(int paramInt, OPAQUE paramOPAQUE)
			throws SQLException
	/*      */{
		/* 2201 */updateOracleObject(paramInt, paramOPAQUE);
		/*      */}

	/*      */
	/*      */public void updateREF(int paramInt, REF paramREF) throws SQLException
	/*      */{
		/* 2206 */updateOracleObject(paramInt, paramREF);
		/*      */}

	/*      */
	/*      */public void updateCHAR(int paramInt, CHAR paramCHAR)
			throws SQLException
	/*      */{
		/* 2211 */updateOracleObject(paramInt, paramCHAR);
		/*      */}

	/*      */
	/*      */public void updateRAW(int paramInt, RAW paramRAW) throws SQLException
	/*      */{
		/* 2216 */updateOracleObject(paramInt, paramRAW);
		/*      */}

	/*      */
	/*      */public void updateBLOB(int paramInt, BLOB paramBLOB)
			throws SQLException
	/*      */{
		/* 2221 */updateOracleObject(paramInt, paramBLOB);
		/*      */}

	/*      */
	/*      */public void updateCLOB(int paramInt, CLOB paramCLOB)
			throws SQLException
	/*      */{
		/* 2226 */updateOracleObject(paramInt, paramCLOB);
		/*      */}

	/*      */
	/*      */public void updateBFILE(int paramInt, BFILE paramBFILE)
			throws SQLException
	/*      */{
		/* 2231 */updateOracleObject(paramInt, paramBFILE);
		/*      */}

	/*      */
	/*      */public void updateBfile(int paramInt, BFILE paramBFILE)
			throws SQLException
	/*      */{
		/* 2236 */updateOracleObject(paramInt, paramBFILE);
		/*      */}

	/*      */
	/*      */public void updateCustomDatum(int paramInt,
			CustomDatum paramCustomDatum)
	/*      */throws SQLException
	/*      */{
		/* 2242 */throw new Error(
				"wanna do datum = ((CustomDatum) x).toDatum(m_comm)");
		/*      */}

	/*      */
	/*      */public void updateORAData(int paramInt, ORAData paramORAData)
	/*      */throws SQLException
	/*      */{
		/* 2250 */Datum localDatum = paramORAData.toDatum(this.connection);
		/*      */
		/* 2252 */updateOracleObject(paramInt, localDatum);
		/*      */}

	/*      */
	/*      */public void updateRef(int paramInt, Ref paramRef) throws SQLException
	/*      */{
		/* 2257 */updateREF(paramInt, (REF) paramRef);
		/*      */}

	/*      */
	/*      */public void updateBlob(int paramInt, Blob paramBlob)
			throws SQLException
	/*      */{
		/* 2262 */updateBLOB(paramInt, (BLOB) paramBlob);
		/*      */}

	/*      */
	/*      */public void updateClob(int paramInt, Clob paramClob)
			throws SQLException
	/*      */{
		/* 2267 */updateCLOB(paramInt, (CLOB) paramClob);
		/*      */}

	/*      */
	/*      */public void updateArray(int paramInt, Array paramArray)
			throws SQLException
	/*      */{
		/* 2272 */updateARRAY(paramInt, (ARRAY) paramArray);
		/*      */}

	/*      */
	/*      */int getColumnCount()
	/*      */throws SQLException
	/*      */{
		/* 2284 */if (this.columnCount == 0)
		/*      */{
			/* 2286 */if ((this.resultSet instanceof OracleResultSetImpl))
			/*      */{
				/* 2288 */if (((OracleResultSetImpl) this.resultSet).statement.accessors != null) {
					/* 2289 */this.columnCount = ((OracleResultSetImpl) this.resultSet).statement.numberOfDefinePositions;
					/*      */}
				/*      */else
					/* 2292 */this.columnCount = getInternalMetadata()
							.getColumnCount();
				/*      */}
			/*      */else {
				/* 2295 */this.columnCount = ((ScrollableResultSet) this.resultSet)
						.getColumnCount();
				/*      */}
			/*      */}
		/* 2298 */return this.columnCount;
		/*      */}

	/*      */
	/*      */ResultSetMetaData getInternalMetadata()
	/*      */throws SQLException
	/*      */{
		/* 2306 */if (this.rsetMetaData == null) {
			/* 2307 */this.rsetMetaData = this.resultSet.getMetaData();
			/*      */}
		/* 2309 */return this.rsetMetaData;
		/*      */}

	/*      */
	/*      */private synchronized void cancelRowChanges() throws SQLException
	/*      */{
		/* 2314 */synchronized (this.connection)
		/*      */{
			/* 2316 */synchronized (this)
			/*      */{
				/* 2318 */if (this.isInserting) {
					/* 2319 */cancelRowInserts();
					/*      */}
				/* 2321 */if (this.isUpdating)
					/* 2322 */cancelRowUpdates();
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */boolean isOnInsertRow()
	/*      */{
		/* 2332 */return this.isInserting;
		/*      */}

	/*      */
	/*      */private void cancelRowInserts()
	/*      */{
		/* 2340 */if (this.isInserting)
		/*      */{
			/* 2342 */this.isInserting = false;
			/*      */
			/* 2344 */clearRowBuffer();
			/*      */}
		/*      */}

	/*      */
	/*      */boolean isUpdatingRow()
	/*      */{
		/* 2353 */return this.isUpdating;
		/*      */}

	/*      */
	/*      */private void clearRowBuffer()
	/*      */{
		/*      */int i;
		/* 2361 */if (this.rowBuffer != null)
		/*      */{
			/* 2363 */for (i = 0; i < this.rowBuffer.length; i++) {
				/* 2364 */this.rowBuffer[i] = null;
				/*      */}
			/*      */}
		/* 2367 */if (this.m_nullIndicator != null)
		/*      */{
			/* 2369 */for (i = 0; i < this.m_nullIndicator.length; i++) {
				/* 2370 */this.m_nullIndicator[i] = false;
				/*      */}
			/*      */}
		/* 2373 */if (this.typeInfo != null)
		/*      */{
			/* 2375 */for (i = 0; i < this.typeInfo.length; i++)
				/* 2376 */if (this.typeInfo[i] != null)
					/* 2377 */for (int j = 0; j < this.typeInfo[i].length; j++)
						/* 2378 */this.typeInfo[i][j] = 0;
			/*      */}
		/*      */}

	/*      */
	/*      */private void setRowBufferAt(int paramInt, Datum paramDatum)
	/*      */throws SQLException
	/*      */{
		/* 2387 */setRowBufferAt(paramInt, paramDatum, (int[]) null);
		/*      */}

	/*      */
	/*      */private void setRowBufferAt(int paramInt, Object paramObject,
			int[] paramArrayOfInt)
	/*      */throws SQLException
	/*      */{
		/* 2396 */if (!this.isInserting)
		/*      */{
			/* 2398 */if ((isBeforeFirst()) || (isAfterLast())
					|| (getRow() == 0))
				/* 2399 */DatabaseError.throwSqlException(82);
			/*      */else {
				/* 2401 */this.isUpdating = true;
				/*      */}
			/*      */}
		/* 2404 */if ((paramInt < 1) || (paramInt > getColumnCount() - 1)) {
			/* 2405 */DatabaseError.throwSqlException(68, "setRowBufferAt");
			/*      */}
		/*      */
		/* 2408 */if (this.rowBuffer == null) {
			/* 2409 */this.rowBuffer = new Object[getColumnCount()];
			/*      */}
		/* 2411 */if (this.m_nullIndicator == null)
		/*      */{
			/* 2413 */this.m_nullIndicator = new boolean[getColumnCount()];
			/*      */
			/* 2415 */for (int i = 0; i < getColumnCount(); i++) {
				/* 2416 */this.m_nullIndicator[i] = false;
				/*      */}
			/*      */}
		/* 2419 */if (paramArrayOfInt != null)
		/*      */{
			/* 2421 */if (this.typeInfo == null)
			/*      */{
				/* 2423 */this.typeInfo = new int[getColumnCount()][];
				/*      */}
			/*      */
			/* 2426 */this.typeInfo[paramInt] = paramArrayOfInt;
			/*      */}
		/*      */
		/* 2429 */this.rowBuffer[paramInt] = paramObject;
		/* 2430 */this.m_nullIndicator[paramInt] = (paramObject == null ? 1
				: false);
		/*      */}

	/*      */
	/*      */private Datum getRowBufferDatumAt(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2438 */if ((paramInt < 1) || (paramInt > getColumnCount() - 1)) {
			/* 2439 */DatabaseError
					.throwSqlException(68, "getRowBufferDatumAt");
			/*      */}
		/*      */
		/* 2442 */Datum localDatum = null;
		/*      */
		/* 2444 */if (this.rowBuffer != null)
		/*      */{
			/* 2446 */Object localObject = this.rowBuffer[paramInt];
			/*      */
			/* 2448 */if (localObject != null)
			/*      */{
				/* 2450 */if ((localObject instanceof Datum))
				/*      */{
					/* 2452 */localDatum = (Datum) localObject;
					/*      */}
				/*      */else
				/*      */{
					/* 2456 */OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData) getInternalMetadata();
					/* 2457 */int i = paramInt + 1;
					/* 2458 */localDatum = SQLUtil.makeOracleDatum(
							this.connection, localObject,
							localOracleResultSetMetaData.getColumnType(i),
							null, localOracleResultSetMetaData.isNCHAR(i));
					/*      */
					/* 2462 */this.rowBuffer[paramInt] = localDatum;
					/*      */}
				/*      */}
			/*      */}
		/*      */
		/* 2467 */return localDatum;
		/*      */}

	/*      */
	/*      */private Object getRowBufferAt(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2475 */if ((paramInt < 1) || (paramInt > getColumnCount() - 1)) {
			/* 2476 */DatabaseError
					.throwSqlException(68, "getRowBufferDatumAt");
			/*      */}
		/*      */
		/* 2479 */if (this.rowBuffer != null)
		/*      */{
			/* 2481 */return this.rowBuffer[paramInt];
			/*      */}
		/*      */
		/* 2484 */return null;
		/*      */}

	/*      */
	/*      */private boolean isRowBufferUpdatedAt(int paramInt)
	/*      */{
		/* 2489 */if (this.rowBuffer == null) {
			/* 2490 */return false;
			/*      */}
		/* 2492 */return (this.rowBuffer[paramInt] != null)
				|| (this.m_nullIndicator[paramInt] != 0);
		/*      */}

	/*      */
	/*      */private void prepareInsertRowStatement()
	/*      */throws SQLException
	/*      */{
		/* 2500 */if (this.insertStmt == null)
		/*      */{
			/* 2505 */this.insertStmt = ((OraclePreparedStatement) this.connection
					.prepareStatement(((OracleStatement) this.scrollStmt).sqlObject
							.getInsertSqlForUpdatableResultSet(this)));
			/*      */
			/* 2508 */this.insertStmt
					.setQueryTimeout(((Statement) this.scrollStmt)
							.getQueryTimeout());
			/*      */}
		/*      */}

	/*      */
	/*      */private void prepareInsertRowBinds()
	/*      */throws SQLException
	/*      */{
		/* 2518 */int i = 1;
		/*      */
		/* 2521 */i = prepareSubqueryBinds(this.insertStmt, i);
		/*      */
		/* 2523 */OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData) getInternalMetadata();
		/*      */
		/* 2525 */for (int j = 1; j < getColumnCount(); j++)
		/*      */{
			/* 2527 */Object localObject = getRowBufferAt(j);
			/*      */
			/* 2529 */if (localObject != null)
			/*      */{
				/* 2531 */if ((localObject instanceof Reader))
				/*      */{
					/* 2533 */this.insertStmt.setCharacterStream(i + j - 1,
							(Reader) localObject, this.typeInfo[j][0]);
					/*      */}
				/* 2536 */else if ((localObject instanceof InputStream))
				/*      */{
					/* 2538 */if (this.typeInfo[j][1] == 2) {
						/* 2539 */this.insertStmt.setBinaryStream(i + j - 1,
								(InputStream) localObject, this.typeInfo[j][0]);
						/*      */}
					/* 2542 */else if (this.typeInfo[j][1] == 1) {
						/* 2543 */this.insertStmt.setAsciiStream(i + j - 1,
								(InputStream) localObject, this.typeInfo[j][0]);
						/*      */}
					/*      */
					/*      */}
				/*      */else
				/*      */{
					/* 2549 */Datum localDatum = getRowBufferDatumAt(j);
					/*      */
					/* 2551 */if (localOracleResultSetMetaData.isNCHAR(j + 1))
						/* 2552 */this.insertStmt.setFormOfUse(i + j - 1, 2);
					/* 2553 */this.insertStmt.setOracleObject(i + j - 1,
							localDatum);
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 2562 */int k = getInternalMetadata().getColumnType(j + 1);
				/*      */
				/* 2564 */if ((k == 2006) || (k == 2002) || (k == 2008)
						|| (k == 2007) || (k == 2003))
				/*      */{
					/* 2568 */this.insertStmt.setNull(i + j - 1, k,
							getInternalMetadata().getColumnTypeName(j + 1));
					/*      */}
				/*      */else
				/*      */{
					/* 2573 */this.insertStmt.setNull(i + j - 1, k);
					/*      */}
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */private void executeInsertRow()
	/*      */throws SQLException
	/*      */{
		/* 2584 */if (this.insertStmt.executeUpdate() != 1)
			/* 2585 */DatabaseError.throwSqlException(85);
		/*      */}

	/*      */
	/*      */private int getNumColumnsChanged()
	/*      */throws SQLException
	/*      */{
		/* 2595 */int i = 0;
		/*      */
		/* 2597 */if (this.indexColsChanged == null) {
			/* 2598 */this.indexColsChanged = new int[getColumnCount()];
			/*      */}
		/* 2600 */if (this.rowBuffer != null)
		/*      */{
			/* 2602 */for (int j = 1; j < getColumnCount(); j++)
			/*      */{
				/* 2604 */if ((this.rowBuffer[j] == null)
						&& ((this.rowBuffer[j] != null) || (this.m_nullIndicator[j] == 0))) {
					/*      */continue;
					/*      */}
				/* 2607 */this.indexColsChanged[(i++)] = j;
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2615 */return i;
		/*      */}

	/*      */
	/*      */private void prepareUpdateRowStatement(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2627 */if (this.updateStmt != null) {
			/* 2628 */this.updateStmt.close();
			/*      */}
		/*      */
		/* 2633 */this.updateStmt = ((OraclePreparedStatement) this.connection
				.prepareStatement(((OracleStatement) this.scrollStmt).sqlObject
						.getUpdateSqlForUpdatableResultSet(this, paramInt,
								this.rowBuffer, this.indexColsChanged)));
		/*      */
		/* 2637 */this.updateStmt.setQueryTimeout(((Statement) this.scrollStmt)
				.getQueryTimeout());
		/*      */}

	/*      */
	/*      */private void prepareUpdateRowBinds(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2645 */int i = 1;
		/*      */
		/* 2648 */i = prepareSubqueryBinds(this.updateStmt, i);
		/*      */
		/* 2650 */OracleResultSetMetaData localOracleResultSetMetaData = (OracleResultSetMetaData) getInternalMetadata();
		/*      */
		/* 2652 */for (int j = 0; j < paramInt; j++)
		/*      */{
			/* 2654 */int k = this.indexColsChanged[j];
			/* 2655 */Object localObject = getRowBufferAt(k);
			/*      */
			/* 2657 */if (localObject != null)
			/*      */{
				/* 2659 */if ((localObject instanceof Reader))
				/*      */{
					/* 2661 */this.updateStmt.setCharacterStream(i++,
							(Reader) localObject, this.typeInfo[k][0]);
					/*      */}
				/* 2664 */else if ((localObject instanceof InputStream))
				/*      */{
					/* 2666 */if (this.typeInfo[k][1] == 2)
					/*      */{
						/* 2669 */this.updateStmt.setBinaryStream(i++,
								(InputStream) localObject, this.typeInfo[k][0]);
						/*      */}
					/* 2671 */else if (this.typeInfo[k][1] == 1) {
						/* 2672 */this.updateStmt.setAsciiStream(i++,
								(InputStream) localObject, this.typeInfo[k][0]);
						/*      */}
					/*      */}
				/*      */else
				/*      */{
					/* 2677 */Datum localDatum = getRowBufferDatumAt(k);
					/*      */
					/* 2679 */if (localOracleResultSetMetaData.isNCHAR(k + 1))
						/* 2680 */this.updateStmt.setFormOfUse(i, 2);
					/* 2681 */this.updateStmt.setOracleObject(i++, localDatum);
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 2687 */int m = getInternalMetadata().getColumnType(k + 1);
				/*      */
				/* 2689 */if ((m == 2006) || (m == 2002) || (m == 2008)
						|| (m == 2007) || (m == 2003))
				/*      */{
					/* 2693 */this.updateStmt.setNull(i++, m,
							getInternalMetadata().getColumnTypeName(k + 1));
					/*      */}
				/*      */else
				/*      */{
					/* 2698 */this.updateStmt.setNull(i++, m);
					/*      */}
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2704 */prepareCompareSelfBinds(this.updateStmt, i);
		/*      */}

	/*      */
	/*      */private void executeUpdateRow()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 2714 */if (this.updateStmt.executeUpdate() == 0) {
				/* 2715 */DatabaseError.throwSqlException(85);
				/*      */}
			/*      */
			/* 2719 */if (this.isCachedRset)
			/*      */{
				/* 2721 */if (this.autoRefetch)
				/*      */{
					/* 2723 */((ScrollableResultSet) this.resultSet)
							.refreshRowsInCache(getRow(), 1, 1000);
					/*      */
					/* 2725 */cancelRowUpdates();
					/*      */}
				/*      */else
				/*      */{
					/* 2729 */if (this.rowBuffer != null)
					/*      */{
						/* 2731 */for (int i = 1; i < getColumnCount(); i++)
						/*      */{
							/* 2733 */if ((this.rowBuffer[i] == null)
									&& ((this.rowBuffer[i] != null) || (this.m_nullIndicator[i] == 0))) {
								/*      */continue;
								/*      */}
							/* 2736 */((ScrollableResultSet) this.resultSet)
									.setCurrentRowValueAt(this, i + 1,
											this.rowBuffer[i]);
							/*      */}
						/*      */
						/*      */}
					/*      */
					/* 2742 */cancelRowUpdates();
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */finally
		/*      */{
			/* 2753 */if (this.updateStmt != null)
			/*      */{
				/* 2755 */this.updateStmt.close();
				/*      */
				/* 2757 */this.updateStmt = null;
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */private void prepareDeleteRowStatement()
	/*      */throws SQLException
	/*      */{
		/* 2767 */if (this.deleteStmt == null)
		/*      */{
			/* 2769 */StringBuffer localStringBuffer = new StringBuffer();
			/*      */
			/* 2774 */this.deleteStmt = ((OraclePreparedStatement) this.connection
					.prepareStatement(((OracleStatement) this.scrollStmt).sqlObject
							.getDeleteSqlForUpdatableResultSet(this)));
			/*      */
			/* 2777 */this.deleteStmt
					.setQueryTimeout(((Statement) this.scrollStmt)
							.getQueryTimeout());
			/*      */}
		/*      */}

	/*      */
	/*      */private void prepareDeleteRowBinds()
	/*      */throws SQLException
	/*      */{
		/* 2786 */int i = 1;
		/*      */
		/* 2789 */i = prepareSubqueryBinds(this.deleteStmt, i);
		/*      */
		/* 2792 */prepareCompareSelfBinds(this.deleteStmt, i);
		/*      */}

	/*      */
	/*      */private void executeDeleteRow()
	/*      */throws SQLException
	/*      */{
		/* 2802 */if (this.deleteStmt.executeUpdate() == 0) {
			/* 2803 */DatabaseError.throwSqlException(85);
			/*      */}
		/*      */
		/* 2807 */if (this.isCachedRset)
			/* 2808 */((ScrollableResultSet) this.resultSet)
					.removeRowInCache(getRow());
		/*      */}

	/*      */
	/*      */private int prepareCompareSelfBinds(
			OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2814 */Datum localDatum = this.resultSet.getOracleObject(1);
		/*      */
		/* 2816 */paramOraclePreparedStatement.setOracleObject(paramInt,
				this.resultSet.getOracleObject(1));
		/*      */
		/* 2818 */return paramInt + 1;
		/*      */}

	/*      */
	/*      */private int prepareSubqueryBinds(
			OraclePreparedStatement paramOraclePreparedStatement, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2824 */int i = this.scrollStmt.copyBinds(
				paramOraclePreparedStatement, paramInt - 1);
		/*      */
		/* 2842 */return i + 1;
		/*      */}

	/*      */
	/*      */private void setIsNull(int paramInt)
	/*      */{
		/* 2847 */this.wasNull = paramInt;
		/*      */}

	/*      */
	/*      */private void setIsNull(boolean paramBoolean)
	/*      */{
		/* 2852 */if (paramBoolean)
			/* 2853 */this.wasNull = 1;
		/*      */else
			/* 2855 */this.wasNull = 2;
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.UpdatableResultSet JD-Core Version: 0.6.0
 */