/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.ByteArrayInputStream;
/*     */
import java.io.CharArrayReader;
/*     */
import java.io.InputStream;
/*     */
import java.io.Reader;
/*     */
import java.sql.SQLException;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.util.RepConversion;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.RAW;

/*     */
/*     */class RawCommonAccessor extends Accessor
/*     */{
	/*     */void init(OracleStatement paramOracleStatement, int paramInt1,
			int paramInt2, int paramInt3, short paramShort, int paramInt4)
	/*     */throws SQLException
	/*     */{
		/* 32 */init(paramOracleStatement, paramInt1, paramInt2, paramShort,
				false);
		/* 33 */initForDataAccess(paramInt4, paramInt3, null);
		/*     */}

	/*     */
	/*     */void init(OracleStatement paramOracleStatement, int paramInt1,
			int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4,
			int paramInt5, int paramInt6, int paramInt7, int paramInt8,
			short paramShort)
	/*     */throws SQLException
	/*     */{
		/* 40 */init(paramOracleStatement, paramInt1, paramInt2, paramShort,
				false);
		/* 41 */initForDescribe(paramInt1, paramInt3, paramBoolean, paramInt4,
				paramInt5, paramInt6, paramInt7, paramInt8, paramShort, null);
		/*     */
		/* 44 */int i = paramOracleStatement.maxFieldSize;
		/*     */
		/* 46 */if ((i > 0) && ((paramInt3 == 0) || (i < paramInt3))) {
			/* 47 */paramInt3 = i;
			/*     */}
		/* 49 */initForDataAccess(0, paramInt3, null);
		/*     */}

	/*     */
	/*     */void initForDataAccess(int paramInt1, int paramInt2, String paramString)
	/*     */throws SQLException
	/*     */{
		/* 55 */if (paramInt1 != 0) {
			/* 56 */this.externalType = paramInt1;
			/*     */}
		/* 58 */this.internalTypeMaxLength = 2147483647;
		/*     */
		/* 60 */if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength))
			/* 61 */this.internalTypeMaxLength = paramInt2;
		/*     */}

	/*     */
	/*     */String getString(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 81 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 83 */if (arrayOfByte == null) {
			/* 84 */return null;
			/*     */}
		/* 86 */int i = arrayOfByte.length;
		/*     */
		/* 88 */if (i == 0) {
			/* 89 */return null;
			/*     */}
		/* 91 */return RepConversion.bArray2String(arrayOfByte);
		/*     */}

	/*     */
	/*     */InputStream getAsciiStream(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 103 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 105 */if (arrayOfByte == null) {
			/* 106 */return null;
			/*     */}
		/* 108 */PhysicalConnection localPhysicalConnection = this.statement.connection;
		/*     */
		/* 110 */return localPhysicalConnection.conversion.ConvertStream(
				new ByteArrayInputStream(arrayOfByte), 2);
		/*     */}

	/*     */
	/*     */InputStream getUnicodeStream(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 123 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 125 */if (arrayOfByte == null) {
			/* 126 */return null;
			/*     */}
		/* 128 */PhysicalConnection localPhysicalConnection = this.statement.connection;
		/*     */
		/* 130 */return localPhysicalConnection.conversion.ConvertStream(
				new ByteArrayInputStream(arrayOfByte), 3);
		/*     */}

	/*     */
	/*     */Reader getCharacterStream(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 143 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 145 */if (arrayOfByte == null) {
			/* 146 */return null;
			/*     */}
		/* 148 */int i = arrayOfByte.length;
		/*     */
		/* 150 */char[] arrayOfChar = new char[i << 1];
		/*     */
		/* 152 */int j = DBConversion.RAWBytesToHexChars(arrayOfByte, i,
				arrayOfChar);
		/*     */
		/* 155 */return new CharArrayReader(arrayOfChar, 0, j);
		/*     */}

	/*     */
	/*     */InputStream getBinaryStream(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 167 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 169 */if (arrayOfByte == null) {
			/* 170 */return null;
			/*     */}
		/* 172 */return new ByteArrayInputStream(arrayOfByte);
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 184 */return getBytes(paramInt);
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 197 */return getBytes(paramInt);
		/*     */}

	/*     */
	/*     */Datum getOracleObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 209 */return getRAW(paramInt);
		/*     */}

	/*     */
	/*     */RAW getRAW(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 221 */byte[] arrayOfByte = getBytes(paramInt);
		/*     */
		/* 223 */if (arrayOfByte == null) {
			/* 224 */return null;
			/*     */}
		/* 226 */return new RAW(arrayOfByte);
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.RawCommonAccessor JD-Core Version: 0.6.0
 */