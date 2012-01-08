/*       */package oracle.jdbc.driver;

/*       */
/*       */class CopiedCharBinder extends Binder
/*       */{
	/*       */char[] value;
	/*       */short len;

	/*       */
	/*       */CopiedCharBinder(short paramShort1, char[] paramArrayOfChar,
			short paramShort2)
	/*       */{
		/* 11682 */this.type = paramShort1;
		/* 11683 */this.bytelen = 0;
		/* 11684 */this.value = paramArrayOfChar;
		/* 11685 */this.len = paramShort2;
		/*       */}

	/*       */
	/*       */Binder copyingBinder()
	/*       */{
		/* 11690 */return this;
		/*       */}

	/*       */
	/*       */void bind(OraclePreparedStatement paramOraclePreparedStatement,
			int paramInt1, int paramInt2, int paramInt3,
			byte[] paramArrayOfByte, char[] paramArrayOfChar,
			short[] paramArrayOfShort, int paramInt4, int paramInt5,
			int paramInt6, int paramInt7, int paramInt8, int paramInt9,
			boolean paramBoolean)
	/*       */{
		/* 11699 */paramArrayOfShort[paramInt9] = 0;
		/* 11700 */paramArrayOfShort[paramInt8] = this.len;
		/*       */
		/* 11702 */System.arraycopy(this.value, 0, paramArrayOfChar, paramInt7,
				this.value.length);
		/*       */}
	/*       */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.CopiedCharBinder
 * JD-Core Version: 0.6.0
 */