package oracle.jdbc.driver;

import java.io.PrintStream;

import java.sql.SQLException;

public class LRUStatementCache {
	private int cacheSize;
	private int numElements;
	private OracleStatementCacheEntry applicationCacheStart;
	private OracleStatementCacheEntry applicationCacheEnd;
	private OracleStatementCacheEntry implicitCacheStart;
	private OracleStatementCacheEntry explicitCacheStart;
	boolean implicitCacheEnabled;
	boolean explicitCacheEnabled;
	/* 50 */private boolean debug = false;

	/* 741 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";

	protected LRUStatementCache(int paramInt) throws SQLException {
		/* 69 */if (paramInt < 0) {
			/* 70 */DatabaseError.throwSqlException(123);
		}
		/* 72 */this.cacheSize = paramInt;
		/* 73 */this.numElements = 0;

		/* 75 */this.implicitCacheStart = null;
		/* 76 */this.explicitCacheStart = null;

		/* 78 */this.implicitCacheEnabled = false;
		/* 79 */this.explicitCacheEnabled = false;
	}

	protected void resize(int paramInt) throws SQLException {
		/* 99 */if (paramInt < 0) {
			/* 100 */DatabaseError.throwSqlException(123);
		}
		/* 102 */if ((paramInt >= this.cacheSize) || (paramInt >= this.numElements)) {
			/* 105 */this.cacheSize = paramInt;
		} else {
			/* 111 */OracleStatementCacheEntry localOracleStatementCacheEntry = this.applicationCacheEnd;
			/* 112 */for (; this.numElements > paramInt; localOracleStatementCacheEntry = localOracleStatementCacheEntry.applicationPrev) {
				/* 113 */purgeCacheEntry(localOracleStatementCacheEntry);
			}
			/* 115 */this.cacheSize = paramInt;
		}
	}

	public void setImplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 138 */if (!paramBoolean) {
			/* 139 */purgeImplicitCache();
		}
		/* 141 */this.implicitCacheEnabled = paramBoolean;
	}

	public boolean getImplicitCachingEnabled() throws SQLException {
		boolean bool;
		/* 156 */if (this.cacheSize == 0)
			/* 157 */bool = false;
		else {
			/* 159 */bool = this.implicitCacheEnabled;
		}

		/* 164 */return bool;
	}

	public void setExplicitCachingEnabled(boolean paramBoolean) throws SQLException {
		/* 183 */if (!paramBoolean) {
			/* 184 */purgeExplicitCache();
		}
		/* 186 */this.explicitCacheEnabled = paramBoolean;
	}

	public boolean getExplicitCachingEnabled() throws SQLException {
		boolean bool;
		/* 201 */if (this.cacheSize == 0)
			/* 202 */bool = false;
		else {
			/* 204 */bool = this.explicitCacheEnabled;
		}

		/* 209 */return bool;
	}

	protected void addToImplicitCache(OraclePreparedStatement stmt, String sql, int paramInt1, int paramInt2) throws SQLException {
		if ((!this.implicitCacheEnabled) || (this.cacheSize == 0) || (stmt.cacheState == 2)) {
			return;
		}

		if (this.numElements == this.cacheSize) {
			purgeCacheEntry(this.applicationCacheEnd);
		}

		stmt.enterImplicitCache();

		OracleStatementCacheEntry cacheEntry = new OracleStatementCacheEntry();

		cacheEntry.statement = stmt;
		cacheEntry.onImplicit = true;

		cacheEntry.sql = sql;
		cacheEntry.statementType = paramInt1;
		cacheEntry.scrollType = paramInt2;

		cacheEntry.applicationNext = this.applicationCacheStart;
		cacheEntry.applicationPrev = null;

		if (this.applicationCacheStart != null) {
			this.applicationCacheStart.applicationPrev = cacheEntry;
		}
		this.applicationCacheStart = cacheEntry;

		cacheEntry.implicitNext = this.implicitCacheStart;
		cacheEntry.implicitPrev = null;

		if (this.implicitCacheStart != null) {
			this.implicitCacheStart.implicitPrev = cacheEntry;
		}
		this.implicitCacheStart = cacheEntry;

		if (this.applicationCacheEnd == null) {
			this.applicationCacheEnd = cacheEntry;
		}

		this.numElements += 1;
	}

	protected void addToExplicitCache(OraclePreparedStatement paramOraclePreparedStatement, String paramString) throws SQLException {
		if ((!this.explicitCacheEnabled) || (this.cacheSize == 0) || (paramOraclePreparedStatement.cacheState == 2)) {
			return;
		}

		if (this.numElements == this.cacheSize) {
			purgeCacheEntry(this.applicationCacheEnd);
		}

		paramOraclePreparedStatement.enterExplicitCache();

		OracleStatementCacheEntry cacheEntry = new OracleStatementCacheEntry();

		cacheEntry.statement = paramOraclePreparedStatement;
		cacheEntry.sql = paramString;
		cacheEntry.onImplicit = false;

		cacheEntry.applicationNext = this.applicationCacheStart;
		cacheEntry.applicationPrev = null;

		if (this.applicationCacheStart != null) {
			this.applicationCacheStart.applicationPrev = cacheEntry;
		}
		this.applicationCacheStart = cacheEntry;

		cacheEntry.explicitNext = this.explicitCacheStart;
		cacheEntry.explicitPrev = null;

		if (this.explicitCacheStart != null) {
			this.explicitCacheStart.explicitPrev = cacheEntry;
		}
		this.explicitCacheStart = cacheEntry;

		if (this.applicationCacheEnd == null) {
			this.applicationCacheEnd = cacheEntry;
		}

		this.numElements += 1;
	}

	protected OracleStatement searchImplicitCache(String paramString, int paramInt1, int paramInt2) throws SQLException {
		if (!this.implicitCacheEnabled) {
			return null;
		}

		OracleStatementCacheEntry localOracleStatementCacheEntry = null;

		for (localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext) {
			if ((localOracleStatementCacheEntry.statementType == paramInt1) && (localOracleStatementCacheEntry.scrollType == paramInt2)
					&& (localOracleStatementCacheEntry.sql.equals(paramString))) {
				break;
			}
		}
		if (localOracleStatementCacheEntry != null) {
			if (localOracleStatementCacheEntry.applicationPrev != null) {
				localOracleStatementCacheEntry.applicationPrev.applicationNext = localOracleStatementCacheEntry.applicationNext;
			}
			if (localOracleStatementCacheEntry.applicationNext != null) {
				localOracleStatementCacheEntry.applicationNext.applicationPrev = localOracleStatementCacheEntry.applicationPrev;
			}
			if (this.applicationCacheStart == localOracleStatementCacheEntry) {
				this.applicationCacheStart = localOracleStatementCacheEntry.applicationNext;
			}
			if (this.applicationCacheEnd == localOracleStatementCacheEntry) {
				this.applicationCacheEnd = localOracleStatementCacheEntry.applicationPrev;
			}
			if (localOracleStatementCacheEntry.implicitPrev != null) {
				localOracleStatementCacheEntry.implicitPrev.implicitNext = localOracleStatementCacheEntry.implicitNext;
			}
			if (localOracleStatementCacheEntry.implicitNext != null) {
				localOracleStatementCacheEntry.implicitNext.implicitPrev = localOracleStatementCacheEntry.implicitPrev;
			}
			if (this.implicitCacheStart == localOracleStatementCacheEntry) {
				this.implicitCacheStart = localOracleStatementCacheEntry.implicitNext;
			}

			this.numElements -= 1;

			localOracleStatementCacheEntry.statement.exitImplicitCacheToActive();

			return localOracleStatementCacheEntry.statement;
		}

		return null;
	}

	protected OracleStatement searchExplicitCache(String paramString) throws SQLException {
		/* 477 */if (!this.explicitCacheEnabled) {
			/* 483 */return null;
		}

		/* 487 */OracleStatementCacheEntry localOracleStatementCacheEntry = null;

		/* 489 */for (localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext) {
			/* 491 */if (localOracleStatementCacheEntry.sql.equals(paramString)) {
				break;
			}
		}
		/* 495 */if (localOracleStatementCacheEntry != null) {
			/* 506 */if (localOracleStatementCacheEntry.applicationPrev != null) {
				/* 507 */localOracleStatementCacheEntry.applicationPrev.applicationNext = localOracleStatementCacheEntry.applicationNext;
			}
			/* 509 */if (localOracleStatementCacheEntry.applicationNext != null) {
				/* 510 */localOracleStatementCacheEntry.applicationNext.applicationPrev = localOracleStatementCacheEntry.applicationPrev;
			}
			/* 512 */if (this.applicationCacheStart == localOracleStatementCacheEntry) {
				/* 513 */this.applicationCacheStart = localOracleStatementCacheEntry.applicationNext;
			}
			/* 515 */if (this.applicationCacheEnd == localOracleStatementCacheEntry) {
				/* 516 */this.applicationCacheEnd = localOracleStatementCacheEntry.applicationPrev;
			}
			/* 518 */if (localOracleStatementCacheEntry.explicitPrev != null) {
				/* 519 */localOracleStatementCacheEntry.explicitPrev.explicitNext = localOracleStatementCacheEntry.explicitNext;
			}
			/* 521 */if (localOracleStatementCacheEntry.explicitNext != null) {
				/* 522 */localOracleStatementCacheEntry.explicitNext.explicitPrev = localOracleStatementCacheEntry.explicitPrev;
			}
			/* 524 */if (this.explicitCacheStart == localOracleStatementCacheEntry) {
				/* 525 */this.explicitCacheStart = localOracleStatementCacheEntry.explicitNext;
			}

			/* 529 */this.numElements -= 1;

			/* 532 */localOracleStatementCacheEntry.statement.exitExplicitCacheToActive();

			/* 538 */return localOracleStatementCacheEntry.statement;
		}

		/* 549 */return null;
	}

	protected void purgeImplicitCache() throws SQLException {
		/* 566 */for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null;) {
			/* 568 */purgeCacheEntry(localOracleStatementCacheEntry);

			/* 567 */localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext;
		}

		/* 570 */this.implicitCacheStart = null;
	}

	protected void purgeExplicitCache() throws SQLException {
		/* 587 */for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null;) {
			/* 589 */purgeCacheEntry(localOracleStatementCacheEntry);

			/* 588 */localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext;
		}

		/* 591 */this.explicitCacheStart = null;
	}

	private void purgeCacheEntry(OracleStatementCacheEntry paramOracleStatementCacheEntry) throws SQLException {
		/* 610 */if (paramOracleStatementCacheEntry.applicationNext != null) {
			/* 611 */paramOracleStatementCacheEntry.applicationNext.applicationPrev = paramOracleStatementCacheEntry.applicationPrev;
		}
		/* 613 */if (paramOracleStatementCacheEntry.applicationPrev != null) {
			/* 614 */paramOracleStatementCacheEntry.applicationPrev.applicationNext = paramOracleStatementCacheEntry.applicationNext;
		}
		/* 616 */if (this.applicationCacheStart == paramOracleStatementCacheEntry) {
			/* 617 */this.applicationCacheStart = paramOracleStatementCacheEntry.applicationNext;
		}
		/* 619 */if (this.applicationCacheEnd == paramOracleStatementCacheEntry) {
			/* 620 */this.applicationCacheEnd = paramOracleStatementCacheEntry.applicationPrev;
		}
		/* 622 */if (paramOracleStatementCacheEntry.onImplicit) {
			/* 624 */if (paramOracleStatementCacheEntry.implicitNext != null) {
				/* 625 */paramOracleStatementCacheEntry.implicitNext.implicitPrev = paramOracleStatementCacheEntry.implicitPrev;
			}
			/* 627 */if (paramOracleStatementCacheEntry.implicitPrev != null) {
				/* 628 */paramOracleStatementCacheEntry.implicitPrev.implicitNext = paramOracleStatementCacheEntry.implicitNext;
			}
			/* 630 */if (this.implicitCacheStart == paramOracleStatementCacheEntry)
				/* 631 */this.implicitCacheStart = paramOracleStatementCacheEntry.implicitNext;
		} else {
			/* 635 */if (paramOracleStatementCacheEntry.explicitNext != null) {
				/* 636 */paramOracleStatementCacheEntry.explicitNext.explicitPrev = paramOracleStatementCacheEntry.explicitPrev;
			}
			/* 638 */if (paramOracleStatementCacheEntry.explicitPrev != null) {
				/* 639 */paramOracleStatementCacheEntry.explicitPrev.explicitNext = paramOracleStatementCacheEntry.explicitNext;
			}
			/* 641 */if (this.explicitCacheStart == paramOracleStatementCacheEntry) {
				/* 642 */this.explicitCacheStart = paramOracleStatementCacheEntry.explicitNext;
			}
		}

		/* 646 */this.numElements -= 1;

		/* 649 */if (paramOracleStatementCacheEntry.onImplicit)
			/* 650 */paramOracleStatementCacheEntry.statement.exitImplicitCacheToClose();
		else
			/* 652 */paramOracleStatementCacheEntry.statement.exitExplicitCacheToClose();
	}

	public int getCacheSize() {
		/* 665 */return this.cacheSize;
	}

	public void printCache(String paramString) throws SQLException {
		/* 681 */System.out.println("*** Start of Statement Cache Dump (" + paramString + ") ***");
		/* 682 */System.out.println("cache size: " + this.cacheSize + " num elements: " + this.numElements + " implicit enabled: " + this.implicitCacheEnabled
				+ " explicit enabled: " + this.explicitCacheEnabled);

		/* 686 */System.out.println("applicationStart: " + this.applicationCacheStart + "  applicationEnd: " + this.applicationCacheEnd);

		/* 689 */for (OracleStatementCacheEntry localOracleStatementCacheEntry = this.applicationCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.applicationNext) {
			/* 690 */localOracleStatementCacheEntry.print();
		}
		/* 692 */System.out.println("implicitStart: " + this.implicitCacheStart);

		/* 694 */for (localOracleStatementCacheEntry = this.implicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.implicitNext) {
			/* 695 */localOracleStatementCacheEntry.print();
		}
		/* 697 */System.out.println("explicitStart: " + this.explicitCacheStart);

		/* 699 */for (localOracleStatementCacheEntry = this.explicitCacheStart; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.explicitNext) {
			/* 700 */localOracleStatementCacheEntry.print();
		}
		/* 702 */System.out.println("*** End of Statement Cache Dump (" + paramString + ") ***");
	}

	public void close() throws SQLException {
		/* 718 */OracleStatementCacheEntry localOracleStatementCacheEntry = this.applicationCacheStart;
		/* 719 */for (; localOracleStatementCacheEntry != null; localOracleStatementCacheEntry = localOracleStatementCacheEntry.applicationNext) {
			/* 723 */if (localOracleStatementCacheEntry.onImplicit)
				/* 724 */localOracleStatementCacheEntry.statement.exitImplicitCacheToClose();
			else {
				/* 726 */localOracleStatementCacheEntry.statement.exitExplicitCacheToClose();
			}

		}

		/* 732 */this.applicationCacheStart = null;
		/* 733 */this.applicationCacheEnd = null;
		/* 734 */this.implicitCacheStart = null;
		/* 735 */this.explicitCacheStart = null;
		/* 736 */this.numElements = 0;
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.LRUStatementCache JD-Core Version: 0.6.0
 */