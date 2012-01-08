/*    */package oracle.jdbc.driver;

/*    */
/*    */import java.io.IOException;
/*    */
import java.sql.SQLException;

/*    */
/*    */class T4CInputStream extends OracleInputStream
/*    */{
	/* 95 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*    */public static final boolean TRACE = false;
	/*    */public static final boolean PRIVATE_TRACE = false;
	/*    */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*    */
	/*    */T4CInputStream(OracleStatement paramOracleStatement, int paramInt,
			Accessor paramAccessor)
	/*    */{
		/* 27 */super(paramOracleStatement, paramInt, paramAccessor);
		/*    */}

	/*    */
	/*    */public boolean isNull()
	/*    */throws IOException
	/*    */{
		/* 35 */if (!this.statement.connection.useFetchSizeWithLongColumn) {
			/* 36 */return super.isNull();
			/*    */}
		/* 38 */boolean bool = false;
		/*    */try
		/*    */{
			/* 42 */int i = this.statement.currentRow;
			/*    */
			/* 44 */if (i < 0) {
				/* 45 */i = 0;
				/*    */}
			/* 47 */if (i >= this.statement.validRows) {
				/* 48 */return true;
				/*    */}
			/* 50 */bool = this.accessor.isNull(i);
			/*    */}
		/*    */catch (SQLException localSQLException)
		/*    */{
			/* 54 */DatabaseError.SQLToIOException(localSQLException);
			/*    */}
		/*    */
		/* 57 */return bool;
		/*    */}

	/*    */
	/*    */public int getBytes() throws IOException
	/*    */{
		/* 62 */synchronized (this.statement.connection)
		/*    */{
			/* 64 */synchronized (this)
			/*    */{
				/* 66 */int i = 0;
				/*    */try
				/*    */{
					/* 70 */i = this.accessor.readStream(this.buf,
							this.chunkSize);
					/*    */}
				/*    */catch (SQLException localSQLException1)
				/*    */{
					/* 74 */throw new IOException(
							localSQLException1.getMessage());
					/*    */}
				/*    */catch (IOException localIOException)
				/*    */{
					/*    */try
					/*    */{
						/* 81 */((T4CConnection) this.statement.connection)
								.handleIOException(localIOException);
						/*    */}
					/*    */catch (SQLException localSQLException2) {
						/*    */}
					/* 85 */throw localIOException;
					/*    */}
				/*    */
				/* 88 */return i;
				/*    */}
			/*    */}
		/*    */}
	/*    */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CInputStream
 * JD-Core Version: 0.6.0
 */