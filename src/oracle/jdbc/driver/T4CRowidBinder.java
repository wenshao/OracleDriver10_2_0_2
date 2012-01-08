/*       */package oracle.jdbc.driver;

/*       */
/*       */class T4CRowidBinder extends RowidBinder
/*       */{
	/*       */static void init(Binder paramBinder)
	/*       */{
		/* 17182 */paramBinder.type = 104;
		/* 17183 */paramBinder.bytelen = 130;
		/*       */}

	/*       */
	/*       */T4CRowidBinder()
	/*       */{
		/* 17188 */this.theRowidCopyingBinder = OraclePreparedStatementReadOnly.theStaticT4CRowidCopyingBinder;
		/*       */
		/* 17191 */init(this);
		/*       */}
	/*       */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CRowidBinder
 * JD-Core Version: 0.6.0
 */