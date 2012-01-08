package oracle.jdbc.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

class OracleResultSetImpl extends BaseResultSet {
	PhysicalConnection connection;
	OracleStatement statement;
	boolean closed;
	boolean explicitly_closed;
	boolean m_emptyRset;
	/* 1939 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";

	OracleResultSetImpl(PhysicalConnection paramPhysicalConnection, OracleStatement paramOracleStatement) throws SQLException {
		/* 65 */this.connection = paramPhysicalConnection;
		/* 66 */this.statement = paramOracleStatement;
		/* 67 */this.close_statement_on_close = false;
		/* 68 */this.closed = false;
		/* 69 */this.explicitly_closed = false;
		/* 70 */this.m_emptyRset = false;
	}

	public synchronized void close() throws SQLException {
		internal_close(false);

		if (this.close_statement_on_close) {
			try {
				this.statement.close();
			} catch (SQLException localSQLException) {
			}
		}

		this.explicitly_closed = true;
	}

	public synchronized boolean wasNull() throws SQLException {
		return this.statement.wasNullValue();
	}

	public synchronized ResultSetMetaData getMetaData() throws SQLException {
		/* 123 */if (this.explicitly_closed) {
			/* 124 */DatabaseError.throwSqlException(10, "getMetaData");
		}

		/* 127 */if (this.statement.closed) {
			/* 128 */DatabaseError.throwSqlException(9, "getMetaData");
		}

		/* 134 */if (!this.statement.isOpen) {
			/* 135 */DatabaseError.throwSqlException(144, "getMetaData");
		}

		/* 138 */return new OracleResultSetMetaData(this.connection, this.statement);
	}

	public synchronized Statement getStatement() throws SQLException {
		/* 148 */return this.statement;
	}

	public synchronized boolean next() throws SQLException {
		boolean bool = true;

		PhysicalConnection localPhysicalConnection = this.statement.connection;

		if (this.explicitly_closed) {
			DatabaseError.throwSqlException(10, "next");
		}

		if ((localPhysicalConnection == null) || (localPhysicalConnection.lifecycle != 1)) {
			DatabaseError.throwSqlException(8, "next");
		}

		if (this.statement.closed) {
			DatabaseError.throwSqlException(9, "next");
		}

		if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4)) {
			DatabaseError.throwSqlException(166, "next");
		}

		if (this.closed) {
			return false;
		}

		this.statement.currentRow += 1;
		this.statement.totalRowsVisited += 1;

		if ((this.statement.maxRows != 0) && (this.statement.totalRowsVisited > this.statement.maxRows)) {
			internal_close(false);

			return false;
		}

		if (this.statement.currentRow >= this.statement.validRows) {
			bool = close_or_fetch_from_next(false);
		}
		if ((bool) && (localPhysicalConnection.useFetchSizeWithLongColumn)) {
			this.statement.reopenStreams();
		}
		return bool;
	}

	private boolean close_or_fetch_from_next(boolean close) throws SQLException {
		if (close) {
			internal_close(false);

			return false;
		}

		if (this.statement.gotLastBatch) {
			internal_close(false);

			return false;
		}

		this.statement.check_row_prefetch_changed();

		PhysicalConnection localPhysicalConnection = this.statement.connection;

		if (localPhysicalConnection.protocolId == 3) {
			this.sqlWarning = null;
		} else {
			if (this.statement.streamList != null) {
				while (this.statement.nextStream != null) {
					try {
						this.statement.nextStream.close();
					} catch (IOException localIOException) {
						DatabaseError.throwSqlException(localIOException);
					}

					this.statement.nextStream = this.statement.nextStream.nextStream;
				}
			}

			clearWarnings();

			localPhysicalConnection.needLine();
		}

		synchronized (localPhysicalConnection) {
			this.statement.fetch();
		}

		if (this.statement.validRows == 0) {
			internal_close(false);

			return false;
		}

		this.statement.currentRow = 0;

		this.statement.checkValidRowsStatus();

		return true;
	}

	public boolean isBeforeFirst() throws SQLException {
		/* 321 */return (!isEmptyResultSet()) && (this.statement.currentRow == -1) && (!this.closed);
	}

	public boolean isAfterLast() throws SQLException {
		/* 331 */return (!isEmptyResultSet()) && (this.closed);
	}

	public boolean isFirst() throws SQLException {
		/* 340 */return getRow() == 1;
	}

	public boolean isLast() throws SQLException {
		/* 349 */DatabaseError.throwSqlException(75, "isLast");

		/* 352 */return false;
	}

	public int getRow() throws SQLException {
		/* 361 */return this.statement.totalRowsVisited;
	}

	public synchronized String getString(int paramInt) throws SQLException {
		/* 378 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 379 */DatabaseError.throwSqlException(3);
		}
		/* 381 */if (this.closed) {
			/* 382 */DatabaseError.throwSqlException(11);
		}
		/* 384 */int i = this.statement.currentRow;

		/* 386 */if (i < 0) {
			/* 387 */DatabaseError.throwSqlException(14);
		}
		/* 389 */this.statement.lastIndex = paramInt;

		/* 391 */if (this.statement.streamList != null) {
			/* 393 */this.statement.closeUsedStreams(paramInt);
		}

		/* 397 */return this.statement.accessors[(paramInt - 1)].getString(i);
	}

	public synchronized boolean getBoolean(int paramInt) throws SQLException {
		/* 410 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 411 */DatabaseError.throwSqlException(3);
		}
		/* 413 */if (this.closed) {
			/* 414 */DatabaseError.throwSqlException(11);
		}
		/* 416 */int i = this.statement.currentRow;

		/* 418 */if (i < 0) {
			/* 419 */DatabaseError.throwSqlException(14);
		}
		/* 421 */this.statement.lastIndex = paramInt;

		/* 423 */if (this.statement.streamList != null) {
			/* 425 */this.statement.closeUsedStreams(paramInt);
		}

		/* 429 */return this.statement.accessors[(paramInt - 1)].getBoolean(i);
	}

	public synchronized byte getByte(int paramInt) throws SQLException {
		/* 439 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 440 */DatabaseError.throwSqlException(3);
		}
		/* 442 */if (this.closed) {
			/* 443 */DatabaseError.throwSqlException(11);
		}
		/* 445 */int i = this.statement.currentRow;

		/* 447 */if (i < 0) {
			/* 448 */DatabaseError.throwSqlException(14);
		}
		/* 450 */this.statement.lastIndex = paramInt;

		/* 452 */if (this.statement.streamList != null) {
			/* 454 */this.statement.closeUsedStreams(paramInt);
		}

		/* 458 */return this.statement.accessors[(paramInt - 1)].getByte(i);
	}

	public synchronized short getShort(int paramInt) throws SQLException {
		/* 468 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 469 */DatabaseError.throwSqlException(3);
		}
		/* 471 */if (this.closed) {
			/* 472 */DatabaseError.throwSqlException(11);
		}
		/* 474 */int i = this.statement.currentRow;

		/* 476 */if (i < 0) {
			/* 477 */DatabaseError.throwSqlException(14);
		}
		/* 479 */this.statement.lastIndex = paramInt;

		/* 481 */if (this.statement.streamList != null) {
			/* 483 */this.statement.closeUsedStreams(paramInt);
		}

		/* 487 */return this.statement.accessors[(paramInt - 1)].getShort(i);
	}

	public synchronized int getInt(int paramInt) throws SQLException {
		/* 502 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 503 */DatabaseError.throwSqlException(3);
		}
		/* 505 */if (this.closed) {
			/* 506 */DatabaseError.throwSqlException(11);
		}
		/* 508 */int i = this.statement.currentRow;

		/* 510 */if (i < 0) {
			/* 511 */DatabaseError.throwSqlException(14);
		}
		/* 513 */this.statement.lastIndex = paramInt;

		/* 515 */if (this.statement.streamList != null) {
			/* 517 */this.statement.closeUsedStreams(paramInt);
		}

		/* 521 */return this.statement.accessors[(paramInt - 1)].getInt(i);
	}

	public synchronized long getLong(int paramInt) throws SQLException {
		/* 533 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 534 */DatabaseError.throwSqlException(3);
		}
		/* 536 */if (this.closed) {
			/* 537 */DatabaseError.throwSqlException(11);
		}
		/* 539 */int i = this.statement.currentRow;

		/* 541 */if (i < 0) {
			/* 542 */DatabaseError.throwSqlException(14);
		}
		/* 544 */this.statement.lastIndex = paramInt;

		/* 546 */if (this.statement.streamList != null) {
			/* 548 */this.statement.closeUsedStreams(paramInt);
		}

		/* 552 */return this.statement.accessors[(paramInt - 1)].getLong(i);
	}

	public synchronized float getFloat(int paramInt) throws SQLException {
		/* 562 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 563 */DatabaseError.throwSqlException(3);
		}
		/* 565 */if (this.closed) {
			/* 566 */DatabaseError.throwSqlException(11);
		}
		/* 568 */int i = this.statement.currentRow;

		/* 570 */if (i < 0) {
			/* 571 */DatabaseError.throwSqlException(14);
		}
		/* 573 */this.statement.lastIndex = paramInt;

		/* 575 */if (this.statement.streamList != null) {
			/* 577 */this.statement.closeUsedStreams(paramInt);
		}

		/* 581 */return this.statement.accessors[(paramInt - 1)].getFloat(i);
	}

	public synchronized double getDouble(int paramInt) throws SQLException {
		/* 591 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 592 */DatabaseError.throwSqlException(3);
		}
		/* 594 */if (this.closed) {
			/* 595 */DatabaseError.throwSqlException(11);
		}
		/* 597 */int i = this.statement.currentRow;

		/* 599 */if (i < 0) {
			/* 600 */DatabaseError.throwSqlException(14);
		}
		/* 602 */this.statement.lastIndex = paramInt;

		/* 604 */if (this.statement.streamList != null) {
			/* 606 */this.statement.closeUsedStreams(paramInt);
		}

		/* 610 */return this.statement.accessors[(paramInt - 1)].getDouble(i);
	}

	public synchronized BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
		/* 621 */if ((paramInt1 <= 0) || (paramInt1 > this.statement.numberOfDefinePositions)) {
			/* 622 */DatabaseError.throwSqlException(3);
		}
		/* 624 */if (this.closed) {
			/* 625 */DatabaseError.throwSqlException(11);
		}
		/* 627 */int i = this.statement.currentRow;

		/* 629 */if (i < 0) {
			/* 630 */DatabaseError.throwSqlException(14);
		}
		/* 632 */this.statement.lastIndex = paramInt1;

		/* 634 */if (this.statement.streamList != null) {
			/* 636 */this.statement.closeUsedStreams(paramInt1);
		}

		/* 640 */return this.statement.accessors[(paramInt1 - 1)].getBigDecimal(this.statement.currentRow, paramInt2);
	}

	synchronized byte[] privateGetBytes(int paramInt) throws SQLException {
		/* 660 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 661 */DatabaseError.throwSqlException(3);
		}
		/* 663 */if (this.closed) {
			/* 664 */DatabaseError.throwSqlException(11);
		}
		/* 666 */int i = this.statement.currentRow;

		/* 668 */if (i < 0) {
			/* 669 */DatabaseError.throwSqlException(14);
		}
		/* 671 */this.statement.lastIndex = paramInt;

		/* 673 */if (this.statement.streamList != null) {
			/* 675 */this.statement.closeUsedStreams(paramInt);
		}

		/* 679 */return this.statement.accessors[(paramInt - 1)].privateGetBytes(i);
	}

	public synchronized byte[] getBytes(int paramInt) throws SQLException {
		/* 689 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 690 */DatabaseError.throwSqlException(3);
		}
		/* 692 */if (this.closed) {
			/* 693 */DatabaseError.throwSqlException(11);
		}
		/* 695 */int i = this.statement.currentRow;

		/* 697 */if (i < 0) {
			/* 698 */DatabaseError.throwSqlException(14);
		}
		/* 700 */this.statement.lastIndex = paramInt;

		/* 702 */if (this.statement.streamList != null) {
			/* 704 */this.statement.closeUsedStreams(paramInt);
		}

		/* 708 */return this.statement.accessors[(paramInt - 1)].getBytes(i);
	}

	public synchronized Date getDate(int paramInt) throws SQLException {
		/* 718 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 719 */DatabaseError.throwSqlException(3);
		}
		/* 721 */if (this.closed) {
			/* 722 */DatabaseError.throwSqlException(11);
		}
		/* 724 */int i = this.statement.currentRow;

		/* 726 */if (i < 0) {
			/* 727 */DatabaseError.throwSqlException(14);
		}
		/* 729 */this.statement.lastIndex = paramInt;

		/* 731 */if (this.statement.streamList != null) {
			/* 733 */this.statement.closeUsedStreams(paramInt);
		}

		/* 737 */return this.statement.accessors[(paramInt - 1)].getDate(i);
	}

	public synchronized Time getTime(int paramInt) throws SQLException {
		/* 747 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 748 */DatabaseError.throwSqlException(3);
		}
		/* 750 */if (this.closed) {
			/* 751 */DatabaseError.throwSqlException(11);
		}
		/* 753 */int i = this.statement.currentRow;

		/* 755 */if (i < 0) {
			/* 756 */DatabaseError.throwSqlException(14);
		}
		/* 758 */this.statement.lastIndex = paramInt;

		/* 760 */if (this.statement.streamList != null) {
			/* 762 */this.statement.closeUsedStreams(paramInt);
		}

		/* 766 */return this.statement.accessors[(paramInt - 1)].getTime(i);
	}

	public synchronized Timestamp getTimestamp(int paramInt) throws SQLException {
		/* 777 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 778 */DatabaseError.throwSqlException(3);
		}
		/* 780 */if (this.closed) {
			/* 781 */DatabaseError.throwSqlException(11);
		}
		/* 783 */int i = this.statement.currentRow;

		/* 785 */if (i < 0) {
			/* 786 */DatabaseError.throwSqlException(14);
		}
		/* 788 */this.statement.lastIndex = paramInt;

		/* 790 */if (this.statement.streamList != null) {
			/* 792 */this.statement.closeUsedStreams(paramInt);
		}

		/* 796 */return this.statement.accessors[(paramInt - 1)].getTimestamp(i);
	}

	public synchronized InputStream getAsciiStream(int paramInt) throws SQLException {
		/* 807 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 808 */DatabaseError.throwSqlException(3);
		}
		/* 810 */if (this.closed) {
			/* 811 */DatabaseError.throwSqlException(11);
		}
		/* 813 */int i = this.statement.currentRow;

		/* 815 */if (i < 0) {
			/* 816 */DatabaseError.throwSqlException(14);
		}
		/* 818 */this.statement.lastIndex = paramInt;

		/* 820 */if (this.statement.streamList != null) {
			/* 822 */this.statement.closeUsedStreams(paramInt);
		}

		/* 826 */return this.statement.accessors[(paramInt - 1)].getAsciiStream(i);
	}

	public synchronized InputStream getUnicodeStream(int paramInt) throws SQLException {
		/* 837 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 838 */DatabaseError.throwSqlException(3);
		}
		/* 840 */if (this.closed) {
			/* 841 */DatabaseError.throwSqlException(11);
		}
		/* 843 */int i = this.statement.currentRow;

		/* 845 */if (i < 0) {
			/* 846 */DatabaseError.throwSqlException(14);
		}
		/* 848 */this.statement.lastIndex = paramInt;

		/* 850 */if (this.statement.streamList != null) {
			/* 852 */this.statement.closeUsedStreams(paramInt);
		}

		/* 856 */return this.statement.accessors[(paramInt - 1)].getUnicodeStream(i);
	}

	public synchronized InputStream getBinaryStream(int paramInt) throws SQLException {
		/* 867 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 868 */DatabaseError.throwSqlException(3);
		}
		/* 870 */if (this.closed) {
			/* 871 */DatabaseError.throwSqlException(11);
		}
		/* 873 */int i = this.statement.currentRow;

		/* 875 */if (i < 0) {
			/* 876 */DatabaseError.throwSqlException(14);
		}
		/* 878 */this.statement.lastIndex = paramInt;

		/* 880 */if (this.statement.streamList != null) {
			/* 882 */this.statement.closeUsedStreams(paramInt);
		}

		/* 886 */return this.statement.accessors[(paramInt - 1)].getBinaryStream(i);
	}

	public synchronized Object getObject(int paramInt) throws SQLException {
		/* 896 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 897 */DatabaseError.throwSqlException(3);
		}
		/* 899 */if (this.closed) {
			/* 900 */DatabaseError.throwSqlException(11);
		}
		/* 902 */int i = this.statement.currentRow;

		/* 904 */if (i < 0) {
			/* 905 */DatabaseError.throwSqlException(14);
		}
		/* 907 */this.statement.lastIndex = paramInt;

		/* 909 */if (this.statement.streamList != null) {
			/* 911 */this.statement.closeUsedStreams(paramInt);
		}

		/* 915 */return this.statement.accessors[(paramInt - 1)].getObject(i);
	}

	public synchronized ResultSet getCursor(int paramInt) throws SQLException {
		/* 926 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 927 */DatabaseError.throwSqlException(3);
		}
		/* 929 */if (this.closed) {
			/* 930 */DatabaseError.throwSqlException(11);
		}
		/* 932 */int i = this.statement.currentRow;

		/* 934 */if (i < 0) {
			/* 935 */DatabaseError.throwSqlException(14);
		}
		/* 937 */this.statement.lastIndex = paramInt;

		/* 939 */if (this.statement.streamList != null) {
			/* 941 */this.statement.closeUsedStreams(paramInt);
		}

		/* 945 */return this.statement.accessors[(paramInt - 1)].getCursor(i);
	}

	public synchronized Datum getOracleObject(int paramInt) throws SQLException {
		/* 956 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 957 */DatabaseError.throwSqlException(3);
		}
		/* 959 */if (this.closed) {
			/* 960 */DatabaseError.throwSqlException(11);
		}
		/* 962 */int i = this.statement.currentRow;

		/* 964 */if (i < 0) {
			/* 965 */DatabaseError.throwSqlException(14);
		}
		/* 967 */this.statement.lastIndex = paramInt;

		/* 969 */if (this.statement.streamList != null) {
			/* 971 */this.statement.closeUsedStreams(paramInt);
		}

		/* 975 */return this.statement.accessors[(paramInt - 1)].getOracleObject(i);
	}

	public synchronized ROWID getROWID(int paramInt) throws SQLException {
		/* 986 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 987 */DatabaseError.throwSqlException(3);
		}
		/* 989 */if (this.closed) {
			/* 990 */DatabaseError.throwSqlException(11);
		}
		/* 992 */int i = this.statement.currentRow;

		/* 994 */if (i < 0) {
			/* 995 */DatabaseError.throwSqlException(14);
		}
		/* 997 */this.statement.lastIndex = paramInt;

		/* 999 */if (this.statement.streamList != null) {
			/* 1001 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1005 */return this.statement.accessors[(paramInt - 1)].getROWID(i);
	}

	public synchronized NUMBER getNUMBER(int paramInt) throws SQLException {
		/* 1016 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1017 */DatabaseError.throwSqlException(3);
		}
		/* 1019 */if (this.closed) {
			/* 1020 */DatabaseError.throwSqlException(11);
		}
		/* 1022 */int i = this.statement.currentRow;

		/* 1024 */if (i < 0) {
			/* 1025 */DatabaseError.throwSqlException(14);
		}
		/* 1027 */this.statement.lastIndex = paramInt;

		/* 1029 */if (this.statement.streamList != null) {
			/* 1031 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1035 */return this.statement.accessors[(paramInt - 1)].getNUMBER(i);
	}

	public synchronized DATE getDATE(int paramInt) throws SQLException {
		/* 1046 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1047 */DatabaseError.throwSqlException(3);
		}
		/* 1049 */if (this.closed) {
			/* 1050 */DatabaseError.throwSqlException(11);
		}
		/* 1052 */int i = this.statement.currentRow;

		/* 1054 */if (i < 0) {
			/* 1055 */DatabaseError.throwSqlException(14);
		}
		/* 1057 */this.statement.lastIndex = paramInt;

		/* 1059 */if (this.statement.streamList != null) {
			/* 1061 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1065 */return this.statement.accessors[(paramInt - 1)].getDATE(i);
	}

	public synchronized ARRAY getARRAY(int paramInt) throws SQLException {
		/* 1076 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1077 */DatabaseError.throwSqlException(3);
		}
		/* 1079 */if (this.closed) {
			/* 1080 */DatabaseError.throwSqlException(11);
		}
		/* 1082 */int i = this.statement.currentRow;

		/* 1084 */if (i < 0) {
			/* 1085 */DatabaseError.throwSqlException(14);
		}
		/* 1087 */this.statement.lastIndex = paramInt;

		/* 1089 */if (this.statement.streamList != null) {
			/* 1091 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1095 */return this.statement.accessors[(paramInt - 1)].getARRAY(i);
	}

	public synchronized STRUCT getSTRUCT(int paramInt) throws SQLException {
		/* 1106 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1107 */DatabaseError.throwSqlException(3);
		}
		/* 1109 */if (this.closed) {
			/* 1110 */DatabaseError.throwSqlException(11);
		}
		/* 1112 */int i = this.statement.currentRow;

		/* 1114 */if (i < 0) {
			/* 1115 */DatabaseError.throwSqlException(14);
		}
		/* 1117 */this.statement.lastIndex = paramInt;

		/* 1119 */if (this.statement.streamList != null) {
			/* 1121 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1125 */return this.statement.accessors[(paramInt - 1)].getSTRUCT(i);
	}

	public synchronized OPAQUE getOPAQUE(int paramInt) throws SQLException {
		/* 1136 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1137 */DatabaseError.throwSqlException(3);
		}
		/* 1139 */if (this.closed) {
			/* 1140 */DatabaseError.throwSqlException(11);
		}
		/* 1142 */int i = this.statement.currentRow;

		/* 1144 */if (i < 0) {
			/* 1145 */DatabaseError.throwSqlException(14);
		}
		/* 1147 */this.statement.lastIndex = paramInt;

		/* 1149 */if (this.statement.streamList != null) {
			/* 1151 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1155 */return this.statement.accessors[(paramInt - 1)].getOPAQUE(i);
	}

	public synchronized REF getREF(int paramInt) throws SQLException {
		/* 1166 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1167 */DatabaseError.throwSqlException(3);
		}
		/* 1169 */if (this.closed) {
			/* 1170 */DatabaseError.throwSqlException(11);
		}
		/* 1172 */int i = this.statement.currentRow;

		/* 1174 */if (i < 0) {
			/* 1175 */DatabaseError.throwSqlException(14);
		}
		/* 1177 */this.statement.lastIndex = paramInt;

		/* 1179 */if (this.statement.streamList != null) {
			/* 1181 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1185 */return this.statement.accessors[(paramInt - 1)].getREF(i);
	}

	public synchronized CHAR getCHAR(int paramInt) throws SQLException {
		/* 1196 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1197 */DatabaseError.throwSqlException(3);
		}
		/* 1199 */if (this.closed) {
			/* 1200 */DatabaseError.throwSqlException(11);
		}
		/* 1202 */int i = this.statement.currentRow;

		/* 1204 */if (i < 0) {
			/* 1205 */DatabaseError.throwSqlException(14);
		}
		/* 1207 */this.statement.lastIndex = paramInt;

		/* 1209 */if (this.statement.streamList != null) {
			/* 1211 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1215 */return this.statement.accessors[(paramInt - 1)].getCHAR(i);
	}

	public synchronized RAW getRAW(int paramInt) throws SQLException {
		/* 1226 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1227 */DatabaseError.throwSqlException(3);
		}
		/* 1229 */if (this.closed) {
			/* 1230 */DatabaseError.throwSqlException(11);
		}
		/* 1232 */int i = this.statement.currentRow;

		/* 1234 */if (i < 0) {
			/* 1235 */DatabaseError.throwSqlException(14);
		}
		/* 1237 */this.statement.lastIndex = paramInt;

		/* 1239 */if (this.statement.streamList != null) {
			/* 1241 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1245 */return this.statement.accessors[(paramInt - 1)].getRAW(i);
	}

	public synchronized BLOB getBLOB(int paramInt) throws SQLException {
		/* 1256 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1257 */DatabaseError.throwSqlException(3);
		}
		/* 1259 */if (this.closed) {
			/* 1260 */DatabaseError.throwSqlException(11);
		}
		/* 1262 */int i = this.statement.currentRow;

		/* 1264 */if (i < 0) {
			/* 1265 */DatabaseError.throwSqlException(14);
		}
		/* 1267 */this.statement.lastIndex = paramInt;

		/* 1269 */if (this.statement.streamList != null) {
			/* 1271 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1275 */return this.statement.accessors[(paramInt - 1)].getBLOB(i);
	}

	public synchronized CLOB getCLOB(int paramInt) throws SQLException {
		/* 1286 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1287 */DatabaseError.throwSqlException(3);
		}
		/* 1289 */if (this.closed) {
			/* 1290 */DatabaseError.throwSqlException(11);
		}
		/* 1292 */int i = this.statement.currentRow;

		/* 1294 */if (i < 0) {
			/* 1295 */DatabaseError.throwSqlException(14);
		}
		/* 1297 */this.statement.lastIndex = paramInt;

		/* 1299 */if (this.statement.streamList != null) {
			/* 1301 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1305 */return this.statement.accessors[(paramInt - 1)].getCLOB(i);
	}

	public synchronized BFILE getBFILE(int paramInt) throws SQLException {
		/* 1316 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1317 */DatabaseError.throwSqlException(3);
		}
		/* 1319 */if (this.closed) {
			/* 1320 */DatabaseError.throwSqlException(11);
		}
		/* 1322 */int i = this.statement.currentRow;

		/* 1324 */if (i < 0) {
			/* 1325 */DatabaseError.throwSqlException(14);
		}
		/* 1327 */this.statement.lastIndex = paramInt;

		/* 1329 */if (this.statement.streamList != null) {
			/* 1331 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1335 */return this.statement.accessors[(paramInt - 1)].getBFILE(i);
	}

	public synchronized BFILE getBfile(int paramInt) throws SQLException {
		/* 1346 */return getBFILE(paramInt);
	}

	public synchronized CustomDatum getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory) throws SQLException {
		/* 1358 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1359 */DatabaseError.throwSqlException(3);
		}
		/* 1361 */if (this.closed) {
			/* 1362 */DatabaseError.throwSqlException(11);
		}
		/* 1364 */int i = this.statement.currentRow;

		/* 1366 */if (i < 0) {
			/* 1367 */DatabaseError.throwSqlException(14);
		}
		/* 1369 */this.statement.lastIndex = paramInt;

		/* 1371 */if (this.statement.streamList != null) {
			/* 1373 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1377 */return this.statement.accessors[(paramInt - 1)].getCustomDatum(this.statement.currentRow, paramCustomDatumFactory);
	}

	public synchronized ORAData getORAData(int paramInt, ORADataFactory paramORADataFactory) throws SQLException {
		/* 1389 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1390 */DatabaseError.throwSqlException(3);
		}
		/* 1392 */if (this.closed) {
			/* 1393 */DatabaseError.throwSqlException(11);
		}
		/* 1395 */int i = this.statement.currentRow;

		/* 1397 */if (i < 0) {
			/* 1398 */DatabaseError.throwSqlException(14);
		}
		/* 1400 */this.statement.lastIndex = paramInt;

		/* 1402 */if (this.statement.streamList != null) {
			/* 1404 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1408 */return this.statement.accessors[(paramInt - 1)].getORAData(this.statement.currentRow, paramORADataFactory);
	}

	public synchronized Object getObject(int paramInt, Map paramMap) throws SQLException {
		/* 1421 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1422 */DatabaseError.throwSqlException(3);
		}
		/* 1424 */if (this.closed) {
			/* 1425 */DatabaseError.throwSqlException(11);
		}
		/* 1427 */int i = this.statement.currentRow;

		/* 1429 */if (i < 0) {
			/* 1430 */DatabaseError.throwSqlException(14);
		}
		/* 1432 */this.statement.lastIndex = paramInt;

		/* 1434 */if (this.statement.streamList != null) {
			/* 1436 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1440 */return this.statement.accessors[(paramInt - 1)].getObject(this.statement.currentRow, paramMap);
	}

	public synchronized Ref getRef(int paramInt) throws SQLException {
		/* 1451 */return getREF(paramInt);
	}

	public synchronized Blob getBlob(int paramInt) throws SQLException {
		/* 1461 */return getBLOB(paramInt);
	}

	public synchronized Clob getClob(int paramInt) throws SQLException {
		/* 1471 */return getCLOB(paramInt);
	}

	public synchronized Array getArray(int paramInt) throws SQLException {
		/* 1482 */return getARRAY(paramInt);
	}

	public synchronized Reader getCharacterStream(int paramInt) throws SQLException {
		/* 1494 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1495 */DatabaseError.throwSqlException(3);
		}
		/* 1497 */if (this.closed) {
			/* 1498 */DatabaseError.throwSqlException(11);
		}
		/* 1500 */int i = this.statement.currentRow;

		/* 1502 */if (i < 0) {
			/* 1503 */DatabaseError.throwSqlException(14);
		}
		/* 1505 */this.statement.lastIndex = paramInt;

		/* 1507 */if (this.statement.streamList != null) {
			/* 1509 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1513 */return this.statement.accessors[(paramInt - 1)].getCharacterStream(i);
	}

	public BigDecimal getBigDecimal(int paramInt) throws SQLException {
		/* 1524 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1525 */DatabaseError.throwSqlException(3);
		}
		/* 1527 */if (this.closed) {
			/* 1528 */DatabaseError.throwSqlException(11);
		}
		/* 1530 */int i = this.statement.currentRow;

		/* 1532 */if (i < 0) {
			/* 1533 */DatabaseError.throwSqlException(14);
		}
		/* 1535 */this.statement.lastIndex = paramInt;

		/* 1537 */if (this.statement.streamList != null) {
			/* 1539 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1543 */return this.statement.accessors[(paramInt - 1)].getBigDecimal(i);
	}

	public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 1555 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1556 */DatabaseError.throwSqlException(3);
		}
		/* 1558 */if (this.closed) {
			/* 1559 */DatabaseError.throwSqlException(11);
		}
		/* 1561 */int i = this.statement.currentRow;

		/* 1563 */if (i < 0) {
			/* 1564 */DatabaseError.throwSqlException(14);
		}
		/* 1566 */this.statement.lastIndex = paramInt;

		/* 1568 */if (this.statement.streamList != null) {
			/* 1570 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1574 */return this.statement.accessors[(paramInt - 1)].getDate(this.statement.currentRow, paramCalendar);
	}

	public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 1587 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1588 */DatabaseError.throwSqlException(3);
		}
		/* 1590 */if (this.closed) {
			/* 1591 */DatabaseError.throwSqlException(11);
		}
		/* 1593 */int i = this.statement.currentRow;

		/* 1595 */if (i < 0) {
			/* 1596 */DatabaseError.throwSqlException(14);
		}
		/* 1598 */this.statement.lastIndex = paramInt;

		/* 1600 */if (this.statement.streamList != null) {
			/* 1602 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1606 */return this.statement.accessors[(paramInt - 1)].getTime(this.statement.currentRow, paramCalendar);
	}

	public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
		/* 1619 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1620 */DatabaseError.throwSqlException(3);
		}
		/* 1622 */if (this.closed) {
			/* 1623 */DatabaseError.throwSqlException(11);
		}
		/* 1625 */int i = this.statement.currentRow;

		/* 1627 */if (i < 0) {
			/* 1628 */DatabaseError.throwSqlException(14);
		}
		/* 1630 */this.statement.lastIndex = paramInt;

		/* 1632 */if (this.statement.streamList != null) {
			/* 1634 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1638 */return this.statement.accessors[(paramInt - 1)].getTimestamp(this.statement.currentRow, paramCalendar);
	}

	public INTERVALYM getINTERVALYM(int paramInt) throws SQLException {
		/* 1650 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1651 */DatabaseError.throwSqlException(3);
		}
		/* 1653 */if (this.closed) {
			/* 1654 */DatabaseError.throwSqlException(11);
		}
		/* 1656 */int i = this.statement.currentRow;

		/* 1658 */if (i < 0) {
			/* 1659 */DatabaseError.throwSqlException(14);
		}
		/* 1661 */this.statement.lastIndex = paramInt;

		/* 1663 */if (this.statement.streamList != null) {
			/* 1665 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1669 */return this.statement.accessors[(paramInt - 1)].getINTERVALYM(i);
	}

	public INTERVALDS getINTERVALDS(int paramInt) throws SQLException {
		/* 1691 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1692 */DatabaseError.throwSqlException(3);
		}
		/* 1694 */if (this.closed) {
			/* 1695 */DatabaseError.throwSqlException(11);
		}
		/* 1697 */int i = this.statement.currentRow;

		/* 1699 */if (i < 0) {
			/* 1700 */DatabaseError.throwSqlException(14);
		}
		/* 1702 */this.statement.lastIndex = paramInt;

		/* 1704 */if (this.statement.streamList != null) {
			/* 1706 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1710 */return this.statement.accessors[(paramInt - 1)].getINTERVALDS(i);
	}

	public TIMESTAMP getTIMESTAMP(int paramInt) throws SQLException {
		/* 1721 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1722 */DatabaseError.throwSqlException(3);
		}
		/* 1724 */if (this.closed) {
			/* 1725 */DatabaseError.throwSqlException(11);
		}
		/* 1727 */int i = this.statement.currentRow;

		/* 1729 */if (i < 0) {
			/* 1730 */DatabaseError.throwSqlException(14);
		}
		/* 1732 */this.statement.lastIndex = paramInt;

		/* 1734 */if (this.statement.streamList != null) {
			/* 1736 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1740 */return this.statement.accessors[(paramInt - 1)].getTIMESTAMP(i);
	}

	public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt) throws SQLException {
		/* 1750 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1751 */DatabaseError.throwSqlException(3);
		}
		/* 1753 */if (this.closed) {
			/* 1754 */DatabaseError.throwSqlException(11);
		}
		/* 1756 */int i = this.statement.currentRow;

		/* 1758 */if (i < 0) {
			/* 1759 */DatabaseError.throwSqlException(14);
		}
		/* 1761 */this.statement.lastIndex = paramInt;

		/* 1763 */if (this.statement.streamList != null) {
			/* 1765 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1769 */return this.statement.accessors[(paramInt - 1)].getTIMESTAMPTZ(i);
	}

	public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt) throws SQLException {
		/* 1779 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1780 */DatabaseError.throwSqlException(3);
		}
		/* 1782 */if (this.closed) {
			/* 1783 */DatabaseError.throwSqlException(11);
		}
		/* 1785 */int i = this.statement.currentRow;

		/* 1787 */if (i < 0) {
			/* 1788 */DatabaseError.throwSqlException(14);
		}
		/* 1790 */this.statement.lastIndex = paramInt;

		/* 1792 */if (this.statement.streamList != null) {
			/* 1794 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1798 */return this.statement.accessors[(paramInt - 1)].getTIMESTAMPLTZ(i);
	}

	public synchronized URL getURL(int paramInt) throws SQLException {
		/* 1809 */if ((paramInt <= 0) || (paramInt > this.statement.numberOfDefinePositions)) {
			/* 1810 */DatabaseError.throwSqlException(3);
		}
		/* 1812 */if (this.closed) {
			/* 1813 */DatabaseError.throwSqlException(11);
		}
		/* 1815 */int i = this.statement.currentRow;

		/* 1817 */if (i < 0) {
			/* 1818 */DatabaseError.throwSqlException(14);
		}
		/* 1820 */this.statement.lastIndex = paramInt;

		/* 1822 */if (this.statement.streamList != null) {
			/* 1824 */this.statement.closeUsedStreams(paramInt);
		}

		/* 1828 */return this.statement.accessors[(paramInt - 1)].getURL(i);
	}

	public void setFetchSize(int paramInt) throws SQLException {
		/* 1842 */this.statement.setPrefetchInternal(paramInt, false, false);
	}

	public int getFetchSize() throws SQLException {
		/* 1852 */return this.statement.getPrefetchInternal(false);
	}

	void internal_close(boolean paramBoolean) throws SQLException {
		if (this.closed) {
			return;
		}
		this.closed = true;

		if ((this.statement.gotLastBatch) && (this.statement.validRows == 0)) {
			this.m_emptyRset = true;
		}
		PhysicalConnection localPhysicalConnection = this.statement.connection;
		try {
			/* 1883 */localPhysicalConnection.needLine();

			/* 1885 */synchronized (localPhysicalConnection) {
				/* 1890 */this.statement.closeQuery();
			}

		} catch (SQLException localSQLException) {
		}

		/* 1901 */this.statement.endOfResultSet(paramBoolean);
	}

	public synchronized int findColumn(String paramString) throws SQLException {
		/* 1914 */return this.statement.getColumnIndex(paramString);
	}

	boolean isEmptyResultSet() {
		return (this.m_emptyRset) || ((!this.m_emptyRset) && (this.statement.gotLastBatch) && (this.statement.validRows == 0));
	}

	int getValidRows() {
		return this.statement.validRows;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.OracleResultSetImpl JD-Core Version: 0.6.0
 */