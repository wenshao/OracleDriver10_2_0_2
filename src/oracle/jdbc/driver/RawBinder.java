/*       */package oracle.jdbc.driver;

/*       */
/*       */class RawBinder extends DatumBinder
/*       */{
	/* 16635 */Binder theRawCopyingBinder = OraclePreparedStatementReadOnly.theStaticRawCopyingBinder;

	/*       */
	/*       */static void init(Binder paramBinder)
	/*       */{
		/* 16640 */paramBinder.type = 23;
		/* 16641 */paramBinder.bytelen = 2000;
		/*       */}

	/*       */
	/*       */RawBinder()
	/*       */{
		/* 16646 */init(this);
		/*       */}

	/*       */
	/*       */Binder copyingBinder()
	/*       */{
		/* 16651 */return this.theRawCopyingBinder;
		/*       */}
	/*       */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.RawBinder
 * JD-Core Version: 0.6.0
 */