/*      */package oracle.sql;

/*      */
/*      */import java.io.InputStream;
/*      */
import java.io.OutputStream;
/*      */
import java.io.Reader;
/*      */
import java.io.Writer;
/*      */
import java.sql.Clob;
/*      */
import java.sql.Connection;
/*      */
import java.sql.SQLException;
/*      */
import oracle.jdbc.driver.DatabaseError;

/*      */
/*      */public class CLOB extends DatumWithConnection
/*      */implements Clob
/*      */{
	/*      */public static final int MAX_CHUNK_SIZE = 32768;
	/*      */public static final int DURATION_SESSION = 10;
	/*      */public static final int DURATION_CALL = 12;
	/*      */static final int OLD_WRONG_DURATION_SESSION = 1;
	/*      */static final int OLD_WRONG_DURATION_CALL = 2;
	/*      */public static final int MODE_READONLY = 0;
	/*      */public static final int MODE_READWRITE = 1;
	/*      */ClobDBAccess dbaccess;
	/*      */private int dbChunkSize;
	/*      */private short csform;
	/* 1224 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";

	/*      */
	/*      */protected CLOB()
	/*      */{
		/*      */}

	/*      */
	/*      */public CLOB(oracle.jdbc.OracleConnection paramOracleConnection)
	/*      */throws SQLException
	/*      */{
		/* 129 */this(paramOracleConnection, null);
		/*      */}

	/*      */
	/*      */public CLOB(oracle.jdbc.OracleConnection paramOracleConnection,
			byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 141 */super(paramArrayOfByte);
		/*      */
		/* 144 */if (paramArrayOfByte != null)
		/*      */{
			/* 146 */if ((paramArrayOfByte[5] & 0xC0) == 64)
				/* 147 */this.csform = 2;
			/*      */else {
				/* 149 */this.csform = 1;
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 154 */assertNotNull(paramOracleConnection);
		/* 155 */setPhysicalConnectionOf(paramOracleConnection);
		/*      */
		/* 157 */this.dbaccess = ((oracle.jdbc.internal.OracleConnection) paramOracleConnection)
				.createClobDBAccess();
		/*      */
		/* 159 */this.dbChunkSize = -1;
		/*      */}

	/*      */
	/*      */public CLOB(oracle.jdbc.OracleConnection paramOracleConnection,
			byte[] paramArrayOfByte, short paramShort)
	/*      */throws SQLException
	/*      */{
		/* 168 */this(paramOracleConnection, paramArrayOfByte);
		/*      */
		/* 172 */this.csform = paramShort;
		/*      */}

	/*      */
	/*      */public boolean isNCLOB()
	/*      */{
		/* 181 */int i = this.csform == 2 ? 1 : 0;
		/*      */
		/* 185 */return i;
		/*      */}

	/*      */
	/*      */public long length()
	/*      */throws SQLException
	/*      */{
		/* 205 */return getDBAccess().length(this);
		/*      */}

	/*      */
	/*      */public String getSubString(long paramLong, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 230 */if ((paramInt < 0) || (paramLong < 1L))
		/*      */{
			/* 235 */DatabaseError.throwSqlException(68, "getSubString");
			/*      */}
		/*      */
		/* 239 */String str = null;
		/*      */
		/* 241 */if (paramInt == 0) {
			/* 242 */str = new String();
			/*      */}
		/*      */else {
			/* 245 */char[] arrayOfChar = new char[paramInt];
			/*      */
			/* 247 */int i = getChars(paramLong, paramInt, arrayOfChar);
			/*      */
			/* 249 */if (i > 0)
			/*      */{
				/* 251 */str = new String(arrayOfChar, 0, i);
				/*      */}
			/*      */else
			/*      */{
				/* 255 */str = new String();
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 261 */return str;
		/*      */}

	/*      */
	/*      */public Reader getCharacterStream()
	/*      */throws SQLException
	/*      */{
		/* 278 */return getDBAccess().newReader(this, getBufferSize(), 0L);
		/*      */}

	/*      */
	/*      */public InputStream getAsciiStream()
	/*      */throws SQLException
	/*      */{
		/* 295 */return getDBAccess().newInputStream(this, getBufferSize(), 0L);
		/*      */}

	/*      */
	/*      */public long position(String paramString, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 314 */return getDBAccess().position(this, paramString, paramLong);
		/*      */}

	/*      */
	/*      */public long position(Clob paramClob, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 333 */return getDBAccess().position(this, (CLOB) paramClob,
				paramLong);
		/*      */}

	/*      */
	/*      */public int getChars(long paramLong, int paramInt,
			char[] paramArrayOfChar)
	/*      */throws SQLException
	/*      */{
		/* 354 */return getDBAccess().getChars(this, paramLong, paramInt,
				paramArrayOfChar);
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public Writer getCharacterOutputStream()
	/*      */throws SQLException
	/*      */{
		/* 372 */return setCharacterStream(0L);
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public OutputStream getAsciiOutputStream()
	/*      */throws SQLException
	/*      */{
		/* 389 */return setAsciiStream(0L);
		/*      */}

	/*      */
	/*      */public byte[] getLocator()
	/*      */{
		/* 401 */return getBytes();
		/*      */}

	/*      */
	/*      */public void setLocator(byte[] paramArrayOfByte)
	/*      */{
		/* 413 */setBytes(paramArrayOfByte);
		/*      */}

	/*      */
	/*      */public int putChars(long paramLong, char[] paramArrayOfChar)
	/*      */throws SQLException
	/*      */{
		/* 433 */int i = getDBAccess().putChars(this, paramLong,
				paramArrayOfChar, 0,
				paramArrayOfChar != null ? paramArrayOfChar.length : 0);
		/*      */
		/* 438 */return i;
		/*      */}

	/*      */
	/*      */public int putChars(long paramLong, char[] paramArrayOfChar,
			int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 460 */return getDBAccess().putChars(this, paramLong,
				paramArrayOfChar, 0, paramInt);
		/*      */}

	/*      */
	/*      */public int putChars(long paramLong, char[] paramArrayOfChar,
			int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 481 */return getDBAccess().putChars(this, paramLong,
				paramArrayOfChar, paramInt1, paramInt2);
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public int putString(long paramLong, String paramString)
	/*      */throws SQLException
	/*      */{
		/* 499 */return setString(paramLong, paramString);
		/*      */}

	/*      */
	/*      */public int getChunkSize()
	/*      */throws SQLException
	/*      */{
		/* 514 */if (this.dbChunkSize <= 0)
		/*      */{
			/* 516 */this.dbChunkSize = getDBAccess().getChunkSize(this);
			/*      */}
		/*      */
		/* 519 */return this.dbChunkSize;
		/*      */}

	/*      */
	/*      */public int getBufferSize()
	/*      */throws SQLException
	/*      */{
		/* 534 */int i = getChunkSize();
		/* 535 */int j = 0;
		/*      */
		/* 537 */if ((i >= 32768) || (i <= 0))
		/*      */{
			/* 539 */j = 32768;
			/*      */}
		/*      */else
		/*      */{
			/* 543 */j = 32768 / i * i;
			/*      */}
		/*      */
		/* 548 */return j;
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public static CLOB empty_lob()
	/*      */throws SQLException
	/*      */{
		/* 563 */return getEmptyCLOB();
		/*      */}

	/*      */
	/*      */public static CLOB getEmptyCLOB()
	/*      */throws SQLException
	/*      */{
		/* 594 */byte[] arrayOfByte = new byte[86];
		/*      */
		/* 596 */arrayOfByte[1] = 84;
		/* 597 */arrayOfByte[5] = 24;
		/*      */
		/* 599 */CLOB localCLOB = new CLOB();
		/*      */
		/* 601 */localCLOB.setShareBytes(arrayOfByte);
		/*      */
		/* 603 */return localCLOB;
		/*      */}

	/*      */
	/*      */public boolean isEmptyLob()
	/*      */throws SQLException
	/*      */{
		/* 617 */return (shareBytes()[5] & 0x10) != 0;
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public OutputStream getAsciiOutputStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 634 */return setAsciiStream(paramLong);
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public Writer getCharacterOutputStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 651 */return setCharacterStream(paramLong);
		/*      */}

	/*      */
	/*      */public InputStream getAsciiStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 666 */return getDBAccess().newInputStream(this, getBufferSize(),
				paramLong);
		/*      */}

	/*      */
	/*      */public Reader getCharacterStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 681 */return getDBAccess().newReader(this, getBufferSize(),
				paramLong);
		/*      */}

	/*      */
	/*      *//** @deprecated */
	/*      */public void trim(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 698 */truncate(paramLong);
		/*      */}

	/*      */
	/*      */public static CLOB createTemporary(Connection paramConnection,
			boolean paramBoolean, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 716 */return createTemporary(paramConnection, paramBoolean,
				paramInt, 1);
		/*      */}

	/*      */
	/*      */public static CLOB createTemporary(Connection paramConnection,
			boolean paramBoolean, int paramInt, short paramShort)
	/*      */throws SQLException
	/*      */{
		/* 737 */int i = paramInt;
		/*      */
		/* 739 */if (paramInt == 1) {
			/* 740 */i = 10;
			/*      */}
		/* 742 */if (paramInt == 2) {
			/* 743 */i = 12;
			/*      */}
		/* 745 */if ((paramConnection == null) || ((i != 10) && (i != 12)))
		/*      */{
			/* 751 */DatabaseError.throwSqlException(68);
			/*      */}
		/*      */
		/* 754 */oracle.jdbc.internal.OracleConnection localOracleConnection = ((oracle.jdbc.OracleConnection) paramConnection)
				.physicalConnectionWithin();
		/*      */
		/* 757 */CLOB localCLOB = getDBAccess(localOracleConnection)
				.createTemporaryClob(localOracleConnection, paramBoolean, i,
						paramShort);
		/*      */
		/* 760 */localCLOB.csform = paramShort;
		/*      */
		/* 762 */return localCLOB;
		/*      */}

	/*      */
	/*      */public static void freeTemporary(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 777 */if (paramCLOB == null) {
			/* 778 */return;
			/*      */}
		/* 780 */paramCLOB.freeTemporary();
		/*      */}

	/*      */
	/*      */public static boolean isTemporary(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 797 */if (paramCLOB == null) {
			/* 798 */return false;
			/*      */}
		/* 800 */return paramCLOB.isTemporary();
		/*      */}

	/*      */
	/*      */public void freeTemporary()
	/*      */throws SQLException
	/*      */{
		/* 815 */getDBAccess().freeTemporary(this);
		/*      */}

	/*      */
	/*      */public boolean isTemporary()
	/*      */throws SQLException
	/*      */{
		/* 831 */return getDBAccess().isTemporary(this);
		/*      */}

	/*      */
	/*      */public void open(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 844 */getDBAccess().open(this, paramInt);
		/*      */}

	/*      */
	/*      */public void close()
	/*      */throws SQLException
	/*      */{
		/* 857 */getDBAccess().close(this);
		/*      */}

	/*      */
	/*      */public boolean isOpen()
	/*      */throws SQLException
	/*      */{
		/* 870 */return getDBAccess().isOpen(this);
		/*      */}

	/*      */
	/*      */public int setString(long paramLong, String paramString)
	/*      */throws SQLException
	/*      */{
		/* 898 */if (paramLong < 1L)
		/*      */{
			/* 903 */DatabaseError.throwSqlException(68, "setString()");
			/*      */}
		/*      */
		/* 907 */int i = 0;
		/*      */
		/* 909 */if ((paramString != null) && (paramString.length() != 0)) {
			/* 910 */i = putChars(paramLong, paramString.toCharArray());
			/*      */}
		/*      */
		/* 915 */return i;
		/*      */}

	/*      */
	/*      */public int setString(long paramLong, String paramString,
			int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 946 */if (paramLong < 1L)
		/*      */{
			/* 951 */DatabaseError.throwSqlException(68, "setString()");
			/*      */}
		/*      */
		/* 955 */if (paramInt1 < 0)
		/*      */{
			/* 960 */DatabaseError.throwSqlException(68, "setString()");
			/*      */}
		/*      */
		/* 964 */if (paramInt1 + paramInt2 > paramString.length())
		/*      */{
			/* 969 */DatabaseError.throwSqlException(68, "setString()");
			/*      */}
		/*      */
		/* 973 */int i = 0;
		/*      */
		/* 975 */if ((paramString != null) && (paramString.length() != 0)) {
			/* 976 */i = putChars(paramLong, paramString.toCharArray(),
					paramInt1, paramInt2);
			/*      */}
		/*      */
		/* 981 */return i;
		/*      */}

	/*      */
	/*      */public OutputStream setAsciiStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1003 */return getDBAccess().newOutputStream(this, getBufferSize(),
				paramLong);
		/*      */}

	/*      */
	/*      */public Writer setCharacterStream(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1026 */return getDBAccess().newWriter(this, getBufferSize(),
				paramLong);
		/*      */}

	/*      */
	/*      */public void truncate(long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1047 */if (paramLong < 0L)
		/*      */{
			/* 1052 */DatabaseError.throwSqlException(68);
			/*      */}
		/*      */
		/* 1055 */getDBAccess().trim(this, paramLong);
		/*      */}

	/*      */
	/*      */public Object toJdbc()
	/*      */throws SQLException
	/*      */{
		/* 1078 */return this;
		/*      */}

	/*      */
	/*      */public boolean isConvertibleTo(Class paramClass)
	/*      */{
		/* 1097 */String str = paramClass.getName();
		/*      */
		/* 1099 */return (str.compareTo("java.io.InputStream") == 0)
				|| (str.compareTo("java.io.Reader") == 0);
		/*      */}

	/*      */
	/*      */public Reader characterStreamValue()
	/*      */throws SQLException
	/*      */{
		/* 1115 */return getCharacterStream();
		/*      */}

	/*      */
	/*      */public InputStream asciiStreamValue()
	/*      */throws SQLException
	/*      */{
		/* 1130 */return getAsciiStream();
		/*      */}

	/*      */
	/*      */public InputStream binaryStreamValue()
	/*      */throws SQLException
	/*      */{
		/* 1145 */return getAsciiStream();
		/*      */}

	/*      */
	/*      */public Object makeJdbcArray(int paramInt)
	/*      */{
		/* 1169 */return new CLOB[paramInt];
		/*      */}

	/*      */
	/*      */public ClobDBAccess getDBAccess()
	/*      */throws SQLException
	/*      */{
		/* 1182 */if (this.dbaccess == null)
		/*      */{
			/* 1184 */if (isEmptyLob())
			/*      */{
				/* 1189 */DatabaseError.throwSqlException(98);
				/*      */}
			/*      */
			/* 1192 */this.dbaccess = getInternalConnection()
					.createClobDBAccess();
			/*      */}
		/*      */
		/* 1195 */if (getPhysicalConnection().isClosed()) {
			/* 1196 */DatabaseError.throwSqlException(8);
			/*      */}
		/*      */
		/* 1200 */return this.dbaccess;
		/*      */}

	/*      */
	/*      */public static ClobDBAccess getDBAccess(Connection paramConnection)
	/*      */throws SQLException
	/*      */{
		/* 1213 */return ((oracle.jdbc.OracleConnection) paramConnection)
				.physicalConnectionWithin().createClobDBAccess();
		/*      */}

	/*      */
	/*      */public Connection getJavaSqlConnection()
	/*      */throws SQLException
	/*      */{
		/* 1219 */return super.getJavaSqlConnection();
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CLOB JD-Core Version:
 * 0.6.0
 */