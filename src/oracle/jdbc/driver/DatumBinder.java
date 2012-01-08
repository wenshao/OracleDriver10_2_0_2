/*       */package oracle.jdbc.driver;

/*       */
/*       */abstract class DatumBinder extends Binder
/*       */{
	/*       */void bind(OraclePreparedStatement paramOraclePreparedStatement,
			int paramInt1, int paramInt2, int paramInt3,
			byte[] paramArrayOfByte, char[] paramArrayOfChar,
			short[] paramArrayOfShort, int paramInt4, int paramInt5,
			int paramInt6, int paramInt7, int paramInt8, int paramInt9,
			boolean paramBoolean)
	/*       */{
		/* 15897 */byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
		/* 15898 */byte[] arrayOfByte1 = arrayOfByte[paramInt1];
		/*       */
		/* 15900 */if (paramBoolean) {
			/* 15901 */arrayOfByte[paramInt1] = null;
			/*       */}
		/* 15903 */if (arrayOfByte1 == null)
		/*       */{
			/* 15905 */paramArrayOfShort[paramInt9] = -1;
			/*       */}
		/*       */else
		/*       */{
			/* 15909 */paramArrayOfShort[paramInt9] = 0;
			/*       */
			/* 15911 */System.arraycopy(arrayOfByte1, 0, paramArrayOfByte,
					paramInt6, arrayOfByte1.length);
			/*       */
			/* 15913 */paramArrayOfShort[paramInt8] = (short) arrayOfByte1.length;
			/*       */}
		/*       */}
	/*       */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.DatumBinder
 * JD-Core Version: 0.6.0
 */