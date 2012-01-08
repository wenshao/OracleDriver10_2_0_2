package oracle.jdbc.driver;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import oracle.jdbc.util.RepConversion;
import oracle.sql.CharacterSet;
import oracle.sql.converter.CharacterSetMetaData;

public class DBConversion {
	public static final boolean DO_CONVERSION_WITH_REPLACEMENT = true;
	public static final short ORACLE8_PROD_VERSION = 8030;
	protected short serverNCharSetId;
	protected short serverCharSetId;
	protected short clientCharSetId;
	protected CharacterSet serverCharSet;
	protected CharacterSet serverNCharSet;
	protected CharacterSet clientCharSet;
	protected CharacterSet asciiCharSet;
	protected boolean isServerCharSetFixedWidth;
	protected boolean isServerNCharSetFixedWidth;
	protected int c2sNlsRatio;
	protected int s2cNlsRatio;
	protected int sMaxCharSize;
	protected int cMaxCharSize;
	protected int maxNCharSize;
	protected boolean isServerCSMultiByte;
	public static final short DBCS_CHARSET = -1;
	public static final short UCS2_CHARSET = -5;
	public static final short ASCII_CHARSET = 1;
	public static final short ISO_LATIN_1_CHARSET = 31;
	public static final short AL24UTFFSS_CHARSET = 870;
	public static final short UTF8_CHARSET = 871;
	public static final short AL32UTF8_CHARSET = 873;
	public static final short AL16UTF16_CHARSET = 2000;
	/* 2225 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";

	public DBConversion(short paramShort1, short paramShort2, short paramShort3) throws SQLException {
		/* 114 */switch (paramShort2) {
		case -5:
		case -1:
		case 1:
		case 2:
		case 31:
		case 178:
		case 870:
		case 871:
		case 873:
			/* 135 */break;
		default:
			/* 138 */unexpectedCharset(paramShort2);
		}

		/* 143 */this.serverCharSetId = paramShort1;
		/* 144 */this.clientCharSetId = paramShort2;
		/* 145 */this.serverCharSet = CharacterSet.make(this.serverCharSetId);

		/* 147 */this.serverNCharSetId = paramShort3;
		/* 148 */this.serverNCharSet = CharacterSet.make(this.serverNCharSetId);

		/* 153 */if (paramShort2 == -1)
			/* 154 */this.clientCharSet = this.serverCharSet;
		else {
			/* 156 */this.clientCharSet = CharacterSet.make(this.clientCharSetId);
		}
		/* 158 */this.c2sNlsRatio = CharacterSetMetaData.getRatio(paramShort1, paramShort2);
		/* 159 */this.s2cNlsRatio = CharacterSetMetaData.getRatio(paramShort2, paramShort1);
		/* 160 */this.sMaxCharSize = CharacterSetMetaData.getRatio(paramShort1, 1);
		/* 161 */this.cMaxCharSize = CharacterSetMetaData.getRatio(paramShort2, 1);
		/* 162 */this.maxNCharSize = CharacterSetMetaData.getRatio(paramShort3, 1);
		/* 163 */this.isServerCSMultiByte = (CharacterSetMetaData.getRatio(paramShort1, 1) != 1);
		/* 164 */this.isServerCharSetFixedWidth = CharacterSetMetaData.isFixedWidth(paramShort1);
		/* 165 */this.isServerNCharSetFixedWidth = CharacterSetMetaData.isFixedWidth(paramShort3);
	}

	public short getServerCharSetId() {
		/* 182 */return this.serverCharSetId;
	}

	public short getNCharSetId() {
		/* 196 */return this.serverNCharSetId;
	}

	public boolean IsNCharFixedWith() {
		/* 207 */return this.serverNCharSetId == 2000;
	}

	public short getClientCharSet() {
		/* 225 */if (this.clientCharSetId == -1) {
			/* 226 */return this.serverCharSetId;
		}
		/* 228 */return this.clientCharSetId;
	}

	public CharacterSet getDbCharSetObj() {
		/* 245 */return this.serverCharSet;
	}

	public CharacterSet getDriverCharSetObj() {
		/* 263 */return this.clientCharSet;
	}

	public CharacterSet getDriverNCharSetObj() {
		/* 272 */return this.serverNCharSet;
	}

	public static final short findDriverCharSet(short paramShort1, short paramShort2) {
		/* 336 */int i = 0;

		/* 338 */switch (paramShort1) {
		case 1:
		case 2:
		case 31:
		case 178:
		case 873:
			/* 348 */i = paramShort1;

			/* 350 */break;
		default:
			/* 353 */i = paramShort2 >= 8030 ? 871 : 870;
		}

		/* 359 */return (short) i;
	}

	public static final byte[] stringToDriverCharBytes(String paramString, short paramShort) throws SQLException {
		/* 395 */if (paramString == null) {
			/* 397 */return null;
		}

		/* 400 */byte[] arrayOfByte = null;

		/* 402 */switch (paramShort) {
		case -5:
		case 2000:
			/* 408 */arrayOfByte = CharacterSet.stringToAL16UTF16Bytes(paramString);

			/* 411 */break;
		case 1:
		case 2:
		case 31:
		case 178:
			/* 420 */arrayOfByte = CharacterSet.stringToASCII(paramString);

			/* 423 */break;
		case 870:
		case 871:
			/* 428 */arrayOfByte = CharacterSet.stringToUTF(paramString);

			/* 431 */break;
		case 873:
			/* 434 */arrayOfByte = CharacterSet.stringToAL32UTF8(paramString);

			/* 437 */break;
		case -1:
		default:
			/* 443 */unexpectedCharset(paramShort);
		}

		/* 451 */return arrayOfByte;
	}

	public byte[] StringToCharBytes(String paramString) throws SQLException {
		/* 475 */if (paramString.length() == 0) {
			/* 476 */return null;
		}
		/* 478 */if (this.clientCharSetId == -1) {
			/* 482 */return this.serverCharSet.convertWithReplacement(paramString);
		}

		/* 492 */return stringToDriverCharBytes(paramString, this.clientCharSetId);
	}

	public String CharBytesToString(byte[] paramArrayOfByte, int paramInt) throws SQLException {
		/* 542 */return CharBytesToString(paramArrayOfByte, paramInt, false);
	}

	public String CharBytesToString(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean) throws SQLException {
		/* 552 */String str = null;
		/* 553 */if (paramArrayOfByte.length == 0) {
			/* 554 */return str;
		}
		/* 556 */switch (this.clientCharSetId) {
		case -5:
			/* 560 */str = CharacterSet.AL16UTF16BytesToString(paramArrayOfByte, paramInt);

			/* 563 */break;
		case 1:
		case 2:
		case 31:
		case 178:
			/* 573 */str = new String(paramArrayOfByte, 0, 0, paramInt);

			/* 575 */break;
		case 870:
		case 871:
			/* 580 */str = CharacterSet.UTFToString(paramArrayOfByte, 0, paramInt, paramBoolean);

			/* 583 */break;
		case 873:
			/* 589 */str = CharacterSet.AL32UTF8ToString(paramArrayOfByte, 0, paramInt, paramBoolean);

			/* 591 */break;
		case -1:
			/* 594 */str = this.serverCharSet.toStringWithReplacement(paramArrayOfByte, 0, paramInt);

			/* 596 */break;
		default:
			/* 599 */unexpectedCharset(this.clientCharSetId);
		}

		/* 607 */return str;
	}

	public String NCharBytesToString(byte[] paramArrayOfByte, int paramInt) throws SQLException {
		/* 616 */String str = null;

		/* 618 */if (this.clientCharSetId == -1) {
			/* 622 */str = this.serverNCharSet.toStringWithReplacement(paramArrayOfByte, 0, paramInt);
		} else {
			/* 626 */switch (this.serverNCharSetId) {
			case -5:
			case 2000:
				/* 632 */str = CharacterSet.AL16UTF16BytesToString(paramArrayOfByte, paramInt);

				/* 635 */break;
			case 1:
			case 2:
			case 31:
			case 178:
				/* 645 */str = new String(paramArrayOfByte, 0, 0, paramInt);

				/* 647 */break;
			case 870:
			case 871:
				/* 652 */str = CharacterSet.UTFToString(paramArrayOfByte, 0, paramInt);

				/* 655 */break;
			case 873:
				/* 661 */str = CharacterSet.AL32UTF8ToString(paramArrayOfByte, 0, paramInt);

				/* 663 */break;
			case -1:
				/* 666 */str = this.serverCharSet.toStringWithReplacement(paramArrayOfByte, 0, paramInt);

				/* 668 */break;
			default:
				/* 671 */unexpectedCharset(this.clientCharSetId);
			}

		}

		/* 680 */return str;
	}

	public int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 708 */return javaCharsToCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte, this.clientCharSetId);
	}

	public int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3) throws SQLException {
		/* 719 */return javaCharsToCHARBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, this.clientCharSetId, paramInt3);
	}

	public int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 730 */return javaCharsToCHARBytes(paramArrayOfChar, paramInt, paramArrayOfByte, this.serverNCharSetId);
	}

	public int javaCharsToNCHARBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3) throws SQLException {
		/* 741 */return javaCharsToCHARBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, this.serverNCharSetId, paramInt3);
	}

	protected int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte, short paramShort) throws SQLException {
		/* 751 */return javaCharsToCHARBytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramShort, paramInt);
	}

	protected int javaCharsToCHARBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, short paramShort, int paramInt3) throws SQLException {
		/* 758 */int i = 0;

		/* 763 */switch (paramShort) {
		case -5:
		case 2000:
			/* 771 */i = CharacterSet.convertJavaCharsToAL16UTF16Bytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 774 */break;
		case 2:
		case 178:
			/* 780 */byte[] arrayOfByte = new byte[paramInt3];
			/* 781 */arrayOfByte = this.clientCharSet.convertWithReplacement(new String(paramArrayOfChar, paramInt1, paramInt3));
			/* 782 */System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, arrayOfByte.length);

			/* 784 */i = arrayOfByte.length;

			/* 786 */break;
		case 1:
			/* 792 */i = CharacterSet.convertJavaCharsToASCIIBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 795 */break;
		case 31:
			/* 798 */i = CharacterSet.convertJavaCharsToISOLATIN1Bytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 801 */break;
		case 870:
		case 871:
			/* 808 */i = CharacterSet.convertJavaCharsToUTFBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 811 */break;
		case 873:
			/* 817 */i = CharacterSet.convertJavaCharsToAL32UTF8Bytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 820 */break;
		case -1:
			/* 823 */i = javaCharsToDbCsBytes(paramArrayOfChar, paramInt1, paramArrayOfByte, paramInt2, paramInt3);

			/* 825 */break;
		default:
			/* 828 */unexpectedCharset(this.clientCharSetId);
		}

		/* 836 */return i;
	}

	public int CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, int paramInt3) throws SQLException {
		/* 868 */return _CHARBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, this.clientCharSetId, paramArrayOfInt, paramInt3, this.serverCharSet,
				this.serverNCharSet, this.clientCharSet, false);
	}

	public int NCHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, int paramInt3) throws SQLException {
		/* 887 */return _CHARBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, this.serverNCharSetId, paramArrayOfInt, paramInt3, this.serverCharSet,
				this.serverNCharSet, this.clientCharSet, true);
	}

	static final int _CHARBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, short paramShort, int[] paramArrayOfInt, int paramInt3,
			CharacterSet paramCharacterSet1, CharacterSet paramCharacterSet2, CharacterSet paramCharacterSet3, boolean paramBoolean) throws SQLException {
		/* 915 */int i = 0;
		/* 916 */int j = 0;

		/* 918 */switch (paramShort) {
		case -5:
		case 2000:
			/* 924 */j = paramArrayOfInt[0] - paramArrayOfInt[0] % 2;

			/* 927 */if (paramInt3 > paramArrayOfChar.length - paramInt2) {
				/* 928 */paramInt3 = paramArrayOfChar.length - paramInt2;
			}

			/* 932 */if (paramInt3 * 2 < j) {
				/* 933 */j = paramInt3 * 2;
			}
			/* 935 */i = CharacterSet.convertAL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, j, true);

			/* 939 */paramArrayOfInt[0] -= j;

			/* 941 */break;
		case 1:
		case 2:
		case 31:
		case 178:
			/* 951 */j = paramArrayOfInt[0];

			/* 954 */if (paramInt3 > paramArrayOfChar.length - paramInt2) {
				/* 955 */paramInt3 = paramArrayOfChar.length - paramInt2;
			}

			/* 959 */if (paramInt3 < j) {
				/* 960 */j = paramInt3;
			}
			/* 962 */i = CharacterSet.convertASCIIBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, j);

			/* 964 */paramArrayOfInt[0] -= j;

			/* 966 */break;
		case 870:
		case 871:
			/* 973 */if (paramInt3 > paramArrayOfChar.length - paramInt2) {
				/* 974 */paramInt3 = paramArrayOfChar.length - paramInt2;
			}
			/* 976 */i = CharacterSet.convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, true, paramInt3);

			/* 980 */break;
		case 873:
			/* 985 */if (paramInt3 > paramArrayOfChar.length - paramInt2) {
				/* 986 */paramInt3 = paramArrayOfChar.length - paramInt2;
			}
			/* 988 */i = CharacterSet.convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, true, paramInt3);

			/* 992 */break;
		case -1:
			/* 997 */i = dbCsBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt[0], paramCharacterSet1);

			/* 999 */paramArrayOfInt[0] = 0;

			/* 1001 */break;
		default:
			/* 1006 */CharacterSet localCharacterSet = paramCharacterSet3;

			/* 1008 */if (paramBoolean) {
				/* 1009 */localCharacterSet = paramCharacterSet2;
			}
			/* 1011 */String str = localCharacterSet.toString(paramArrayOfByte, paramInt1, paramArrayOfInt[0]);
			/* 1012 */char[] arrayOfChar = str.toCharArray();
			/* 1013 */int k = arrayOfChar.length;

			/* 1015 */if (k > paramInt3) {
				/* 1016 */k = paramInt3;
			}
			/* 1018 */System.arraycopy(arrayOfChar, 0, paramArrayOfChar, paramInt2, k);
		}

		/* 1026 */return i;
	}

	public byte[] asciiBytesToCHARBytes(byte[] paramArrayOfByte) {
		/* 1053 */byte[] arrayOfByte = null;
		int i;
		int j;
		/* 1064 */switch (this.clientCharSetId) {
		case -5:
			/* 1068 */arrayOfByte = new byte[paramArrayOfByte.length * 2];

			/* 1070 */i = 0;
			for (j = 0; i < paramArrayOfByte.length;) {
				/* 1072 */arrayOfByte[(j++)] = 0;
				/* 1073 */arrayOfByte[(j++)] = paramArrayOfByte[i];

				/* 1070 */i++;
				continue;
			}
			/* 1079 */if (this.asciiCharSet == null) {
				/* 1080 */this.asciiCharSet = CharacterSet.make(1);
			}
			try {
				/* 1084 */arrayOfByte = this.serverCharSet.convert(this.asciiCharSet, paramArrayOfByte, 0, paramArrayOfByte.length);
			} catch (SQLException localSQLException) {
			}

			/* 1096 */arrayOfByte = paramArrayOfByte;

		case -1:
		}

		/* 1104 */return arrayOfByte;
	}

	public int javaCharsToDbCsBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 1147 */int i = javaCharsToDbCsBytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramInt);

		/* 1152 */return i;
	}

	public int javaCharsToDbCsBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3) throws SQLException {
		/* 1192 */int i = 0;

		/* 1195 */catchCharsLen(paramArrayOfChar, paramInt1, paramInt3);

		/* 1197 */String str = new String(paramArrayOfChar, paramInt1, paramInt3);
		/* 1198 */byte[] arrayOfByte = this.serverCharSet.convertWithReplacement(str);

		/* 1200 */str = null;

		/* 1202 */if (arrayOfByte != null) {
			/* 1208 */i = arrayOfByte.length;

			/* 1210 */catchBytesLen(paramArrayOfByte, paramInt2, i);
			/* 1211 */System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt2, i);

			/* 1213 */arrayOfByte = null;
		}

		/* 1219 */return i;
	}

	static final int dbCsBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, CharacterSet paramCharacterSet)
			throws SQLException {
		/* 1273 */int i = 0;

		/* 1275 */catchBytesLen(paramArrayOfByte, paramInt1, paramInt3);

		/* 1277 */String str = paramCharacterSet.toStringWithReplacement(paramArrayOfByte, paramInt1, paramInt3);

		/* 1280 */if (str != null) {
			/* 1282 */i = str.length();

			/* 1284 */catchCharsLen(paramArrayOfChar, paramInt2, i);
			/* 1285 */str.getChars(0, i, paramArrayOfChar, paramInt2);

			/* 1287 */str = null;
		}

		/* 1290 */return i;
	}

	public static final int javaCharsToUcs2Bytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 1326 */int i = javaCharsToUcs2Bytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramInt);

		/* 1331 */return i;
	}

	public static final int javaCharsToUcs2Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3) throws SQLException {
		/* 1368 */catchCharsLen(paramArrayOfChar, paramInt1, paramInt3);
		/* 1369 */catchBytesLen(paramArrayOfByte, paramInt2, paramInt3 * 2);

		/* 1371 */int k = paramInt3 + paramInt1;

		/* 1373 */int i = paramInt1;
		int j = paramInt2;
		for (; i < k; i++) {
			/* 1375 */paramArrayOfByte[(j++)] = (byte) (paramArrayOfChar[i] >> '\b' & 0xFF);
			/* 1376 */paramArrayOfByte[(j++)] = (byte) (paramArrayOfChar[i] & 0xFF);
		}

		/* 1382 */return j - paramInt2;
	}

	public static final int ucs2BytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar) throws SQLException {
		/* 1434 */return CharacterSet.AL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt, paramArrayOfChar);
	}

	public static final byte[] stringToAsciiBytes(String paramString) {
		/* 1459 */return CharacterSet.stringToASCII(paramString);
	}

	public static final int asciiBytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar) throws SQLException {
		/* 1487 */return CharacterSet.convertASCIIBytesToJavaChars(paramArrayOfByte, 0, paramArrayOfChar, 0, paramInt);
	}

	public static final int javaCharsToAsciiBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 1517 */return CharacterSet.convertJavaCharsToASCIIBytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramInt);
	}

	public static final boolean isCharSetMultibyte(short paramShort) {
		/* 1720 */switch (paramShort) {
		case 1:
		case 31:
			/* 1726 */return false;
		case -5:
		case -1:
		case 870:
		case 871:
		case 873:
			/* 1737 */return true;
		}

		/* 1740 */return false;
	}

	public int getMaxCharbyteSize() {
		/* 1780 */return _getMaxCharbyteSize(this.clientCharSetId);
	}

	public int getMaxNCharbyteSize() {
		/* 1789 */return _getMaxCharbyteSize(this.serverNCharSetId);
	}

	public int _getMaxCharbyteSize(short paramShort) {
		/* 1798 */switch (paramShort) {
		case 1:
			/* 1802 */return 1;
		case 31:
			/* 1805 */return 1;
		case 870:
		case 871:
			/* 1810 */return 3;
		case -5:
		case 2000:
			/* 1815 */return 2;
		case -1:
			/* 1818 */return 4;
		case 873:
			/* 1821 */return 4;
		}

		/* 1824 */return 1;
	}

	public boolean isUcs2CharSet() {
		/* 1837 */return this.clientCharSetId == -5;
	}

	public static final int RAWBytesToHexChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar) {
		/* 1850 */int i = 0;
		int j = 0;
		for (; i < paramInt; i++) {
			/* 1852 */paramArrayOfChar[(j++)] = (char) RepConversion.nibbleToHex((byte) (paramArrayOfByte[i] >> 4 & 0xF));

			/* 1855 */paramArrayOfChar[(j++)] = (char) RepConversion.nibbleToHex((byte) (paramArrayOfByte[i] & 0xF));
		}

		/* 1862 */return j;
	}

	public InputStream ConvertStream(InputStream paramInputStream, int paramInt) {
		/* 1871 */return new OracleConversionInputStream(this, paramInputStream, paramInt);
	}

	public InputStream ConvertStream(InputStream paramInputStream, int paramInt1, int paramInt2) {
		/* 1881 */return new OracleConversionInputStream(this, paramInputStream, paramInt1, paramInt2);
	}

	public InputStream ConvertStream(Reader paramReader, int paramInt1, int paramInt2, short paramShort) {
		/* 1904 */OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this, paramReader, paramInt1, paramInt2, paramShort);

		/* 1910 */return localOracleConversionInputStream;
	}

	public Reader ConvertCharacterStream(InputStream paramInputStream, int paramInt) throws SQLException {
		/* 1920 */return new OracleConversionReader(this, paramInputStream, paramInt);
	}

	public Reader ConvertCharacterStream(InputStream paramInputStream, int paramInt, short paramShort) throws SQLException {
		/* 1930 */OracleConversionReader localOracleConversionReader = new OracleConversionReader(this, paramInputStream, paramInt);

		/* 1933 */localOracleConversionReader.setFormOfUse(paramShort);

		/* 1938 */return localOracleConversionReader;
	}

	public InputStream CharsToStream(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 1948 */if (paramInt3 == 10) {
			/* 1949 */return new AsciiStream(paramArrayOfChar, paramInt1, paramInt2);
		}
		/* 1951 */if (paramInt3 == 11) {
			/* 1952 */return new UnicodeStream(paramArrayOfChar, paramInt1, paramInt2);
		}
		/* 1954 */DatabaseError.throwSqlException(39, "unknownConversion");

		/* 1960 */return null;
	}

	static final void unexpectedCharset(short paramShort) throws SQLException {
		/* 2029 */DatabaseError.throwSqlException(35, "DBConversion");
	}

	protected static final void catchBytesLen(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws SQLException {
		/* 2059 */if (paramInt1 + paramInt2 > paramArrayOfByte.length) {
			/* 2065 */DatabaseError.throwSqlException(39, "catchBytesLen");
		}
	}

	protected static final void catchCharsLen(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws SQLException {
		/* 2095 */if (paramInt1 + paramInt2 > paramArrayOfChar.length) {
			/* 2101 */DatabaseError.throwSqlException(39, "catchCharsLen");
		}
	}

	public static final int getUtfLen(char paramChar) {
		/* 2119 */int i = 0;

		/* 2121 */if ((paramChar & 0xFF80) == 0) {
			/* 2123 */i = 1;
		}
		/* 2125 */else if ((paramChar & 0xF800) == 0) {
			/* 2127 */i = 2;
		} else {
			/* 2131 */i = 3;
		}

		/* 2137 */return i;
	}

	int encodedByteLength(String paramString, boolean paramBoolean) {
		/* 2151 */int i = 0;
		/* 2152 */if (paramString != null) {
			/* 2154 */i = paramString.length();
			/* 2155 */if (i != 0) {
				/* 2157 */if (paramBoolean) {
					/* 2159 */i = this.isServerNCharSetFixedWidth ? i * this.maxNCharSize : this.serverNCharSet.encodedByteLength(paramString);
				} else {
					/* 2163 */i = this.isServerCharSetFixedWidth ? i * this.sMaxCharSize : this.serverCharSet.encodedByteLength(paramString);
				}
			}
		}
		/* 2167 */return i;
	}

	int encodedByteLength(char[] paramArrayOfChar, boolean paramBoolean) {
		/* 2180 */int i = 0;
		/* 2181 */if (paramArrayOfChar != null) {
			/* 2183 */i = paramArrayOfChar.length;
			/* 2184 */if (i != 0) {
				/* 2186 */if (paramBoolean) {
					/* 2188 */i = this.isServerNCharSetFixedWidth ? i * this.maxNCharSize : this.serverNCharSet.encodedByteLength(paramArrayOfChar);
				} else {
					/* 2192 */i = this.isServerCharSetFixedWidth ? i * this.sMaxCharSize : this.serverCharSet.encodedByteLength(paramArrayOfChar);
				}
			}
		}
		/* 2196 */return i;
	}

	class UnicodeStream extends OracleBufferedStream {
		UnicodeStream(char[] paramInt1, int paramInt2, int arg4) {
			/* 1992 */super(0);
//
//			/* 1994 */int j = paramInt2;
//			for (int k = 0; k < i;) {
//				/* 1996 */int m = paramInt1[(j++)];
//
//				/* 1998 */this.buf[(k++)] = (byte) (m >> 8 & 0xFF);
//				/* 1999 */this.buf[(k++)] = (byte) (m & 0xFF);
//			}
//
//			/* 2002 */this.count = i;
		}

		public boolean needBytes() {
			/* 2007 */return (!this.closed) && (this.pos < this.count);
		}
	}

	class AsciiStream extends OracleBufferedStream {
		AsciiStream(char[] paramInt1, int paramInt2, int arg4) {
			super(0);

			// /* 1974 */int j = paramInt2;
			// for (int k = 0; k < i; k++) {
			// /* 1975 */this.buf[k] = (byte) paramInt1[(j++)];
			// }
			// /* 1977 */this.count = i;
		}

		public boolean needBytes() {
			/* 1982 */return (!this.closed) && (this.pos < this.count);
		}
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.DBConversion
 * JD-Core Version: 0.6.0
 */