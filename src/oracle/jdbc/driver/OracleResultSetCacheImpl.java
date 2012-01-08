/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.util.Vector;

/*     */
/*     */class OracleResultSetCacheImpl
/*     */implements OracleResultSetCache
/*     */{
	/* 25 */private static int DEFAULT_WIDTH = 5;
	/* 26 */private static int DEFAULT_SIZE = 5;
	/*     */
	/* 28 */Vector m_rows = null;
	/*     */int m_width;
	/* 150 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";

	/*     */
	/*     */OracleResultSetCacheImpl()
	/*     */{
		/* 33 */this(DEFAULT_WIDTH);
		/*     */}

	/*     */
	/*     */OracleResultSetCacheImpl(int paramInt)
	/*     */{
		/* 45 */if (paramInt > 0) {
			/* 46 */this.m_width = paramInt;
			/*     */}
		/* 48 */this.m_rows = new Vector(DEFAULT_SIZE);
		/*     */}

	/*     */
	/*     */public void put(int paramInt1, int paramInt2, Object paramObject)
	/*     */{
		/* 61 */Vector localVector = null;
		/*     */
		/* 63 */while (this.m_rows.size() < paramInt1)
		/*     */{
			/* 65 */localVector = new Vector(this.m_width);
			/*     */
			/* 67 */this.m_rows.addElement(localVector);
			/*     */}
		/*     */
		/* 70 */localVector = (Vector) this.m_rows.elementAt(paramInt1 - 1);
		/*     */
		/* 72 */while (localVector.size() < paramInt2) {
			/* 73 */localVector.addElement(null);
			/*     */}
		/* 75 */localVector.setElementAt(paramObject, paramInt2 - 1);
		/*     */}

	/*     */
	/*     */public Object get(int paramInt1, int paramInt2)
	/*     */{
		/* 84 */Vector localVector = (Vector) this.m_rows
				.elementAt(paramInt1 - 1);
		/*     */
		/* 86 */return localVector.elementAt(paramInt2 - 1);
		/*     */}

	/*     */
	/*     */public void remove(int paramInt)
	/*     */{
		/* 95 */this.m_rows.removeElementAt(paramInt - 1);
		/*     */}

	/*     */
	/*     */public void remove(int paramInt1, int paramInt2)
	/*     */{
		/* 100 */this.m_rows.removeElementAt(paramInt1 - 1);
		/*     */}

	/*     */
	/*     */public void clear()
	/*     */{
		/*     */}

	/*     */
	/*     */public void close() {
		/*     */}

	/*     */
	/*     */public int getLength() {
		/* 111 */return 0;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.OracleResultSetCacheImpl JD-Core Version: 0.6.0
 */