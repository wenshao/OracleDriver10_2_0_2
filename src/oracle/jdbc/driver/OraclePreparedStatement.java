package oracle.jdbc.driver;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.Reader;

import java.io.StringReader;

import java.math.BigDecimal;

import java.net.URL;

import java.sql.Array;

import java.sql.Blob;

import java.sql.Clob;

import java.sql.Date;

import java.sql.ParameterMetaData;

import java.sql.Ref;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import java.sql.SQLData;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Time;

import java.sql.Timestamp;

import java.util.Calendar;

import java.util.Properties;

import oracle.jdbc.internal.ObjectData;

import oracle.jdbc.oracore.OracleTypeADT;

import oracle.jdbc.oracore.OracleTypeCOLLECTION;

import oracle.jdbc.oracore.OracleTypeNUMBER;

import oracle.sql.ARRAY;

import oracle.sql.ArrayDescriptor;

import oracle.sql.BFILE;

import oracle.sql.BINARY_DOUBLE;

import oracle.sql.BINARY_FLOAT;

import oracle.sql.BLOB;

import oracle.sql.CHAR;

import oracle.sql.CLOB;

import oracle.sql.CharacterSet;

import oracle.sql.CustomDatum;

import oracle.sql.DATE;

import oracle.sql.Datum;

import oracle.sql.INTERVALDS;

import oracle.sql.INTERVALYM;

import oracle.sql.NUMBER;

import oracle.sql.OPAQUE;

import oracle.sql.ORAData;

import oracle.sql.OpaqueDescriptor;

import oracle.sql.RAW;

import oracle.sql.REF;

import oracle.sql.ROWID;

import oracle.sql.STRUCT;

import oracle.sql.StructDescriptor;

import oracle.sql.TIMESTAMP;

import oracle.sql.TIMESTAMPLTZ;

import oracle.sql.TIMESTAMPTZ;

public abstract class OraclePreparedStatement extends OracleStatement implements oracle.jdbc.internal.OraclePreparedStatement, ScrollRsetStatement {
	int numberOfBindRowsAllocated;
	/* 92 */static Binder theStaticVarnumCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarnumCopyingBinder;

	/* 94 */static Binder theStaticVarnumNullBinder = OraclePreparedStatementReadOnly.theStaticVarnumNullBinder;

	/* 96 */Binder theVarnumNullBinder = theStaticVarnumNullBinder;

	/* 98 */static Binder theStaticBooleanBinder = OraclePreparedStatementReadOnly.theStaticBooleanBinder;

	/* 100 */Binder theBooleanBinder = theStaticBooleanBinder;

	/* 102 */static Binder theStaticByteBinder = OraclePreparedStatementReadOnly.theStaticByteBinder;

	/* 104 */Binder theByteBinder = theStaticByteBinder;

	/* 106 */static Binder theStaticShortBinder = OraclePreparedStatementReadOnly.theStaticShortBinder;

	/* 108 */Binder theShortBinder = theStaticShortBinder;

	/* 110 */static Binder theStaticIntBinder = OraclePreparedStatementReadOnly.theStaticIntBinder;

	/* 112 */Binder theIntBinder = theStaticIntBinder;

	/* 114 */static Binder theStaticLongBinder = OraclePreparedStatementReadOnly.theStaticLongBinder;

	/* 116 */Binder theLongBinder = theStaticLongBinder;

	/* 118 */static Binder theStaticFloatBinder = OraclePreparedStatementReadOnly.theStaticFloatBinder;

	/* 120 */Binder theFloatBinder = null;

	/* 122 */static Binder theStaticDoubleBinder = OraclePreparedStatementReadOnly.theStaticDoubleBinder;

	/* 124 */Binder theDoubleBinder = null;

	/* 126 */static Binder theStaticBigDecimalBinder = OraclePreparedStatementReadOnly.theStaticBigDecimalBinder;

	/* 128 */Binder theBigDecimalBinder = theStaticBigDecimalBinder;

	/* 130 */static Binder theStaticVarcharCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarcharCopyingBinder;

	/* 132 */static Binder theStaticVarcharNullBinder = OraclePreparedStatementReadOnly.theStaticVarcharNullBinder;

	/* 134 */Binder theVarcharNullBinder = theStaticVarcharNullBinder;

	/* 136 */static Binder theStaticStringBinder = OraclePreparedStatementReadOnly.theStaticStringBinder;

	/* 138 */Binder theStringBinder = theStaticStringBinder;

	/* 140 */static Binder theStaticSetCHARCopyingBinder = OraclePreparedStatementReadOnly.theStaticSetCHARCopyingBinder;

	/* 142 */static Binder theStaticSetCHARBinder = OraclePreparedStatementReadOnly.theStaticSetCHARBinder;

	/* 144 */static Binder theStaticLittleEndianSetCHARBinder = OraclePreparedStatementReadOnly.theStaticLittleEndianSetCHARBinder;

	/* 146 */static Binder theStaticSetCHARNullBinder = OraclePreparedStatementReadOnly.theStaticSetCHARNullBinder;
	Binder theSetCHARBinder;
	/* 149 */Binder theSetCHARNullBinder = theStaticSetCHARNullBinder;

	/* 151 */static Binder theStaticFixedCHARCopyingBinder = OraclePreparedStatementReadOnly.theStaticFixedCHARCopyingBinder;

	/* 153 */static Binder theStaticFixedCHARBinder = OraclePreparedStatementReadOnly.theStaticFixedCHARBinder;

	/* 155 */static Binder theStaticFixedCHARNullBinder = OraclePreparedStatementReadOnly.theStaticFixedCHARNullBinder;

	/* 157 */Binder theFixedCHARBinder = theStaticFixedCHARBinder;
	/* 158 */Binder theFixedCHARNullBinder = theStaticFixedCHARNullBinder;

	/* 160 */static Binder theStaticDateCopyingBinder = OraclePreparedStatementReadOnly.theStaticDateCopyingBinder;

	/* 162 */static Binder theStaticDateBinder = OraclePreparedStatementReadOnly.theStaticDateBinder;

	/* 164 */static Binder theStaticDateNullBinder = OraclePreparedStatementReadOnly.theStaticDateNullBinder;

	/* 166 */Binder theDateBinder = theStaticDateBinder;
	/* 167 */Binder theDateNullBinder = theStaticDateNullBinder;

	/* 169 */static Binder theStaticTimeCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimeCopyingBinder;

	/* 171 */static Binder theStaticTimeBinder = OraclePreparedStatementReadOnly.theStaticTimeBinder;

	/* 173 */Binder theTimeBinder = theStaticTimeBinder;

	/* 175 */static Binder theStaticTimestampCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimestampCopyingBinder;

	/* 177 */static Binder theStaticTimestampBinder = OraclePreparedStatementReadOnly.theStaticTimestampBinder;

	/* 179 */static Binder theStaticTimestampNullBinder = OraclePreparedStatementReadOnly.theStaticTimestampNullBinder;

	/* 181 */Binder theTimestampBinder = theStaticTimestampBinder;
	/* 182 */Binder theTimestampNullBinder = theStaticTimestampNullBinder;

	/* 184 */static Binder theStaticOracleNumberBinder = OraclePreparedStatementReadOnly.theStaticOracleNumberBinder;

	/* 186 */Binder theOracleNumberBinder = theStaticOracleNumberBinder;

	/* 188 */static Binder theStaticOracleDateBinder = OraclePreparedStatementReadOnly.theStaticOracleDateBinder;

	/* 190 */Binder theOracleDateBinder = theStaticOracleDateBinder;

	/* 192 */static Binder theStaticOracleTimestampBinder = OraclePreparedStatementReadOnly.theStaticOracleTimestampBinder;

	/* 194 */Binder theOracleTimestampBinder = theStaticOracleTimestampBinder;

	/* 196 */static Binder theStaticTSTZCopyingBinder = OraclePreparedStatementReadOnly.theStaticTSTZCopyingBinder;

	/* 198 */static Binder theStaticTSTZBinder = OraclePreparedStatementReadOnly.theStaticTSTZBinder;

	/* 200 */static Binder theStaticTSTZNullBinder = OraclePreparedStatementReadOnly.theStaticTSTZNullBinder;

	/* 202 */Binder theTSTZBinder = theStaticTSTZBinder;
	/* 203 */Binder theTSTZNullBinder = theStaticTSTZNullBinder;

	/* 205 */static Binder theStaticTSLTZCopyingBinder = OraclePreparedStatementReadOnly.theStaticTSLTZCopyingBinder;

	/* 207 */static Binder theStaticTSLTZBinder = OraclePreparedStatementReadOnly.theStaticTSLTZBinder;

	/* 209 */static Binder theStaticTSLTZNullBinder = OraclePreparedStatementReadOnly.theStaticTSLTZNullBinder;

	/* 211 */Binder theTSLTZBinder = theStaticTSLTZBinder;
	/* 212 */Binder theTSLTZNullBinder = theStaticTSLTZNullBinder;

	/* 214 */static Binder theStaticRowidCopyingBinder = OraclePreparedStatementReadOnly.theStaticRowidCopyingBinder;

	/* 216 */static Binder theStaticRowidBinder = OraclePreparedStatementReadOnly.theStaticRowidBinder;

	/* 218 */static Binder theStaticLittleEndianRowidBinder = OraclePreparedStatementReadOnly.theStaticLittleEndianRowidBinder;

	/* 220 */static Binder theStaticRowidNullBinder = OraclePreparedStatementReadOnly.theStaticRowidNullBinder;
	Binder theRowidBinder;
	/* 223 */Binder theRowidNullBinder = theStaticRowidNullBinder;

	/* 225 */static Binder theStaticIntervalDSCopyingBinder = OraclePreparedStatementReadOnly.theStaticIntervalDSCopyingBinder;

	/* 227 */static Binder theStaticIntervalDSBinder = OraclePreparedStatementReadOnly.theStaticIntervalDSBinder;

	/* 229 */static Binder theStaticIntervalDSNullBinder = OraclePreparedStatementReadOnly.theStaticIntervalDSNullBinder;

	/* 231 */Binder theIntervalDSBinder = theStaticIntervalDSBinder;
	/* 232 */Binder theIntervalDSNullBinder = theStaticIntervalDSNullBinder;

	/* 234 */static Binder theStaticIntervalYMCopyingBinder = OraclePreparedStatementReadOnly.theStaticIntervalYMCopyingBinder;

	/* 236 */static Binder theStaticIntervalYMBinder = OraclePreparedStatementReadOnly.theStaticIntervalYMBinder;

	/* 238 */static Binder theStaticIntervalYMNullBinder = OraclePreparedStatementReadOnly.theStaticIntervalYMNullBinder;

	/* 240 */Binder theIntervalYMBinder = theStaticIntervalYMBinder;
	/* 241 */Binder theIntervalYMNullBinder = theStaticIntervalYMNullBinder;

	/* 243 */static Binder theStaticBfileCopyingBinder = OraclePreparedStatementReadOnly.theStaticBfileCopyingBinder;

	/* 245 */static Binder theStaticBfileBinder = OraclePreparedStatementReadOnly.theStaticBfileBinder;

	/* 247 */static Binder theStaticBfileNullBinder = OraclePreparedStatementReadOnly.theStaticBfileNullBinder;

	/* 249 */Binder theBfileBinder = theStaticBfileBinder;
	/* 250 */Binder theBfileNullBinder = theStaticBfileNullBinder;

	/* 252 */static Binder theStaticBlobCopyingBinder = OraclePreparedStatementReadOnly.theStaticBlobCopyingBinder;

	/* 254 */static Binder theStaticBlobBinder = OraclePreparedStatementReadOnly.theStaticBlobBinder;

	/* 256 */static Binder theStaticBlobNullBinder = OraclePreparedStatementReadOnly.theStaticBlobNullBinder;

	/* 258 */Binder theBlobBinder = theStaticBlobBinder;
	/* 259 */Binder theBlobNullBinder = theStaticBlobNullBinder;

	/* 261 */static Binder theStaticClobCopyingBinder = OraclePreparedStatementReadOnly.theStaticClobCopyingBinder;

	/* 263 */static Binder theStaticClobBinder = OraclePreparedStatementReadOnly.theStaticClobBinder;

	/* 265 */static Binder theStaticClobNullBinder = OraclePreparedStatementReadOnly.theStaticClobNullBinder;

	/* 267 */Binder theClobBinder = theStaticClobBinder;
	/* 268 */Binder theClobNullBinder = theStaticClobNullBinder;

	/* 270 */static Binder theStaticRawCopyingBinder = OraclePreparedStatementReadOnly.theStaticRawCopyingBinder;

	/* 272 */static Binder theStaticRawBinder = OraclePreparedStatementReadOnly.theStaticRawBinder;

	/* 274 */static Binder theStaticRawNullBinder = OraclePreparedStatementReadOnly.theStaticRawNullBinder;

	/* 276 */Binder theRawBinder = theStaticRawBinder;
	/* 277 */Binder theRawNullBinder = theStaticRawNullBinder;

	/* 279 */static Binder theStaticPlsqlRawCopyingBinder = OraclePreparedStatementReadOnly.theStaticPlsqlRawCopyingBinder;

	/* 281 */static Binder theStaticPlsqlRawBinder = OraclePreparedStatementReadOnly.theStaticPlsqlRawBinder;

	/* 283 */Binder thePlsqlRawBinder = theStaticPlsqlRawBinder;

	/* 285 */static Binder theStaticBinaryFloatCopyingBinder = OraclePreparedStatementReadOnly.theStaticBinaryFloatCopyingBinder;

	/* 287 */static Binder theStaticBinaryFloatBinder = OraclePreparedStatementReadOnly.theStaticBinaryFloatBinder;

	/* 289 */static Binder theStaticBinaryFloatNullBinder = OraclePreparedStatementReadOnly.theStaticBinaryFloatNullBinder;

	/* 291 */Binder theBinaryFloatBinder = theStaticBinaryFloatBinder;
	/* 292 */Binder theBinaryFloatNullBinder = theStaticBinaryFloatNullBinder;

	/* 294 */static Binder theStaticBINARY_FLOATCopyingBinder = OraclePreparedStatementReadOnly.theStaticBINARY_FLOATCopyingBinder;

	/* 296 */static Binder theStaticBINARY_FLOATBinder = OraclePreparedStatementReadOnly.theStaticBINARY_FLOATBinder;

	/* 298 */static Binder theStaticBINARY_FLOATNullBinder = OraclePreparedStatementReadOnly.theStaticBINARY_FLOATNullBinder;

	/* 300 */Binder theBINARY_FLOATBinder = theStaticBINARY_FLOATBinder;
	/* 301 */Binder theBINARY_FLOATNullBinder = theStaticBINARY_FLOATNullBinder;

	/* 303 */static Binder theStaticBinaryDoubleCopyingBinder = OraclePreparedStatementReadOnly.theStaticBinaryDoubleCopyingBinder;

	/* 305 */static Binder theStaticBinaryDoubleBinder = OraclePreparedStatementReadOnly.theStaticBinaryDoubleBinder;

	/* 307 */static Binder theStaticBinaryDoubleNullBinder = OraclePreparedStatementReadOnly.theStaticBinaryDoubleNullBinder;

	/* 309 */Binder theBinaryDoubleBinder = theStaticBinaryDoubleBinder;
	/* 310 */Binder theBinaryDoubleNullBinder = theStaticBinaryDoubleNullBinder;

	/* 312 */static Binder theStaticBINARY_DOUBLECopyingBinder = OraclePreparedStatementReadOnly.theStaticBINARY_DOUBLECopyingBinder;

	/* 314 */static Binder theStaticBINARY_DOUBLEBinder = OraclePreparedStatementReadOnly.theStaticBINARY_DOUBLEBinder;

	/* 316 */static Binder theStaticBINARY_DOUBLENullBinder = OraclePreparedStatementReadOnly.theStaticBINARY_DOUBLENullBinder;

	/* 318 */Binder theBINARY_DOUBLEBinder = theStaticBINARY_DOUBLEBinder;
	/* 319 */Binder theBINARY_DOUBLENullBinder = theStaticBINARY_DOUBLENullBinder;

	/* 321 */static Binder theStaticLongStreamBinder = OraclePreparedStatementReadOnly.theStaticLongStreamBinder;

	/* 323 */Binder theLongStreamBinder = theStaticLongStreamBinder;

	/* 325 */static Binder theStaticLongRawStreamBinder = OraclePreparedStatementReadOnly.theStaticLongRawStreamBinder;

	/* 327 */Binder theLongRawStreamBinder = theStaticLongRawStreamBinder;

	/* 329 */static Binder theStaticNamedTypeCopyingBinder = OraclePreparedStatementReadOnly.theStaticNamedTypeCopyingBinder;

	/* 331 */static Binder theStaticNamedTypeBinder = OraclePreparedStatementReadOnly.theStaticNamedTypeBinder;

	/* 333 */static Binder theStaticNamedTypeNullBinder = OraclePreparedStatementReadOnly.theStaticNamedTypeNullBinder;

	/* 335 */Binder theNamedTypeBinder = theStaticNamedTypeBinder;
	/* 336 */Binder theNamedTypeNullBinder = theStaticNamedTypeNullBinder;

	/* 338 */static Binder theStaticRefTypeCopyingBinder = OraclePreparedStatementReadOnly.theStaticRefTypeCopyingBinder;

	/* 340 */static Binder theStaticRefTypeBinder = OraclePreparedStatementReadOnly.theStaticRefTypeBinder;

	/* 342 */static Binder theStaticRefTypeNullBinder = OraclePreparedStatementReadOnly.theStaticRefTypeNullBinder;

	/* 344 */Binder theRefTypeBinder = theStaticRefTypeBinder;
	/* 345 */Binder theRefTypeNullBinder = theStaticRefTypeNullBinder;

	/* 347 */static Binder theStaticPlsqlIbtCopyingBinder = OraclePreparedStatementReadOnly.theStaticPlsqlIbtCopyingBinder;

	/* 349 */static Binder theStaticPlsqlIbtBinder = OraclePreparedStatementReadOnly.theStaticPlsqlIbtBinder;

	/* 351 */static Binder theStaticPlsqlIbtNullBinder = OraclePreparedStatementReadOnly.theStaticPlsqlIbtNullBinder;

	/* 353 */Binder thePlsqlIbtBinder = theStaticPlsqlIbtBinder;
	/* 354 */Binder thePlsqlNullBinder = theStaticPlsqlIbtNullBinder;

	/* 356 */static Binder theStaticOutBinder = OraclePreparedStatementReadOnly.theStaticOutBinder;

	/* 358 */Binder theOutBinder = theStaticOutBinder;

	/* 360 */static Binder theStaticReturnParamBinder = OraclePreparedStatementReadOnly.theStaticReturnParamBinder;

	/* 362 */Binder theReturnParamBinder = theStaticReturnParamBinder;

	/* 364 */static Binder theStaticT4CRowidBinder = OraclePreparedStatementReadOnly.theStaticT4CRowidBinder;

	/* 366 */static Binder theStaticT4CRowidNullBinder = OraclePreparedStatementReadOnly.theStaticT4CRowidNullBinder;
	public static final int TypeBinder_BYTELEN = 24;
	/* 375 */char[] digits = new char[20];
	Binder[][] binders;
	int[][] parameterInt;
	long[][] parameterLong;
	float[][] parameterFloat;
	double[][] parameterDouble;
	BigDecimal[][] parameterBigDecimal;
	String[][] parameterString;
	Date[][] parameterDate;
	Time[][] parameterTime;
	Timestamp[][] parameterTimestamp;
	byte[][][] parameterDatum;
	OracleTypeADT[][] parameterOtype;
	PlsqlIbtBindInfo[][] parameterPlsqlIbt;
	Binder[] currentRowBinders;
	int[] currentRowCharLens;
	Accessor[] currentRowBindAccessors;
	short[] currentRowFormOfUse;
	/* 487 */boolean currentRowNeedToPrepareBinds = true;
	int[] currentBatchCharLens;
	Accessor[] currentBatchBindAccessors;
	short[] currentBatchFormOfUse;
	boolean currentBatchNeedToPrepareBinds;
	PushedBatch pushedBatches;
	PushedBatch pushedBatchesTail;
	int totalBindByteLength;
	int totalBindCharLength;
	int totalBindIndicatorLength;
	static final int BIND_METADATA_NUMBER_OF_BIND_POSITIONS_OFFSET = 0;
	static final int BIND_METADATA_BIND_BUFFER_CAPACITY_OFFSET = 1;
	static final int BIND_METADATA_NUMBER_OF_BOUND_ROWS_OFFSET = 2;
	static final int BIND_METADATA_PER_POSITION_DATA_OFFSET = 3;
	static final int BIND_METADATA_TYPE_OFFSET = 0;
	static final int BIND_METADATA_BYTE_PITCH_OFFSET = 1;
	static final int BIND_METADATA_CHAR_PITCH_OFFSET = 2;
	static final int BIND_METADATA_VALUE_DATA_OFFSET_HI = 3;
	static final int BIND_METADATA_VALUE_DATA_OFFSET_LO = 4;
	static final int BIND_METADATA_NULL_INDICATORS_OFFSET_HI = 5;
	static final int BIND_METADATA_NULL_INDICATORS_OFFSET_LO = 6;
	static final int BIND_METADATA_VALUE_LENGTHS_OFFSET_HI = 7;
	static final int BIND_METADATA_VALUE_LENGTHS_OFFSET_LO = 8;
	static final int BIND_METADATA_FORM_OF_USE_OFFSET = 9;
	static final int BIND_METADATA_PER_POSITION_SIZE = 10;
	int bindBufferCapacity;
	int numberOfBoundRows;
	int indicatorsOffset;
	int valueLengthsOffset;
	boolean preparedAllBinds;
	boolean preparedCharBinds;
	Binder[] lastBinders;
	byte[] lastBoundBytes;
	int lastBoundByteOffset;
	char[] lastBoundChars;
	int lastBoundCharOffset;
	int[] lastBoundByteOffsets;
	int[] lastBoundCharOffsets;
	int[] lastBoundByteLens;
	int[] lastBoundCharLens;
	short[] lastBoundInds;
	short[] lastBoundLens;
	/* 928 */boolean lastBoundNeeded = false;
	byte[][] lastBoundTypeBytes;
	OracleTypeADT[] lastBoundTypeOtypes;
	private static final int STREAM_MAX_BYTES_SQL = 2147483647;
	int maxRawBytesSql;
	int maxRawBytesPlsql;
	int maxVcsCharsSql;
	int maxVcsNCharsSql;
	int maxVcsBytesPlsql;
	/* 1028 */private int maxCharSize = 0;

	/* 1034 */private int maxNCharSize = 0;

	/* 1042 */private int charMaxCharsSql = 0;

	/* 1050 */private int charMaxNCharsSql = 0;

	/* 1057 */private int maxVcsCharsPlsql = 0;

	/* 1064 */private int maxVcsNCharsPlsql = 0;

	/* 1072 */private int maxStreamCharsSql = 0;

	/* 1080 */private int maxStreamNCharsSql = 0;

	/* 1086 */private boolean isServerCharSetFixedWidth = false;

	/* 1092 */private boolean isServerNCharSetFixedWidth = false;
	int minVcsBindSize;
	int prematureBatchCount;
	/* 1143 */boolean checkBindTypes = true;
	boolean scrollRsetTypeSolved;
	/* 5281 */int SetBigStringTryClob = 0;
	static final int BSTYLE_UNKNOWN = 0;
	static final int BSTYLE_ORACLE = 1;
	static final int BSTYLE_JDBC = 2;
	/* 10370 */int m_batchStyle = 0;

	/* 11467 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";

	OraclePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2) throws SQLException {
		/* 1163 */this(paramPhysicalConnection, paramString, paramInt1, paramInt2, 1003, 1007);
	}

	OraclePreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
		/* 1178 */super(paramPhysicalConnection, paramInt1, paramInt2, paramInt3, paramInt4);

		/* 1184 */this.theSetCHARBinder = (paramPhysicalConnection.useLittleEndianSetCHARBinder() ? theStaticLittleEndianSetCHARBinder : theStaticSetCHARBinder);

		/* 1188 */this.theRowidBinder = (paramPhysicalConnection.useLittleEndianSetCHARBinder() ? theStaticLittleEndianRowidBinder : theStaticRowidBinder);

		/* 1193 */this.statementType = 1;
		/* 1194 */this.currentRow = -1;
		/* 1195 */this.needToParse = true;

		/* 1197 */this.processEscapes = paramPhysicalConnection.processEscapes;
		/* 1198 */this.sqlObject.initialize(paramString);

		/* 1200 */this.sqlKind = this.sqlObject.getSqlKind();

		/* 1206 */this.clearParameters = true;
		/* 1207 */this.scrollRsetTypeSolved = false;
		/* 1208 */this.prematureBatchCount = 0;

		/* 1210 */initializeBinds();

		/* 1212 */this.minVcsBindSize = paramPhysicalConnection.minVcsBindSize;
		/* 1213 */this.maxRawBytesSql = paramPhysicalConnection.maxRawBytesSql;
		/* 1214 */this.maxRawBytesPlsql = paramPhysicalConnection.maxRawBytesPlsql;
		/* 1215 */this.maxVcsCharsSql = paramPhysicalConnection.maxVcsCharsSql;
		/* 1216 */this.maxVcsNCharsSql = paramPhysicalConnection.maxVcsNCharsSql;
		/* 1217 */this.maxVcsBytesPlsql = paramPhysicalConnection.maxVcsBytesPlsql;

		/* 1219 */this.maxCharSize = this.connection.conversion.sMaxCharSize;
		/* 1220 */this.maxNCharSize = this.connection.conversion.maxNCharSize;
		/* 1221 */this.maxVcsCharsPlsql = (this.maxVcsBytesPlsql / this.maxCharSize);
		/* 1222 */this.maxVcsNCharsPlsql = (this.maxVcsBytesPlsql / this.maxNCharSize);
		/* 1223 */this.maxStreamCharsSql = (2147483647 / this.maxCharSize);
		/* 1224 */this.maxStreamNCharsSql = (this.maxRawBytesSql / this.maxNCharSize);
		/* 1225 */this.isServerCharSetFixedWidth = this.connection.conversion.isServerCharSetFixedWidth;
		/* 1226 */this.isServerNCharSetFixedWidth = this.connection.conversion.isServerNCharSetFixedWidth;
	}

	void allocBinds(int paramInt) throws SQLException {
		/* 1238 */int i = paramInt > this.numberOfBindRowsAllocated ? 1 : 0;

		/* 1242 */initializeIndicatorSubRange();

		/* 1246 */int j = this.bindIndicatorSubRange + 3 + this.numberOfBindPositions * 10;

		/* 1253 */int k = paramInt * this.numberOfBindPositions;

		/* 1256 */int m = j + 2 * k;

		/* 1262 */if (m > this.totalBindIndicatorLength) {
			/* 1264 */short[] arrayOfShort = this.bindIndicators;
			/* 1265 */i1 = this.bindIndicatorOffset;

			/* 1267 */this.bindIndicatorOffset = 0;
			/* 1268 */this.bindIndicators = new short[m];
			/* 1269 */this.totalBindIndicatorLength = m;

			/* 1271 */if ((arrayOfShort != null) && (i != 0)) {
				/* 1274 */System.arraycopy(arrayOfShort, i1, this.bindIndicators, this.bindIndicatorOffset, j);
			}

		}

		/* 1279 */this.bindIndicatorSubRange += this.bindIndicatorOffset;

		/* 1286 */this.bindIndicators[(this.bindIndicatorSubRange + 0)] = (short) this.numberOfBindPositions;

		/* 1290 */this.indicatorsOffset = (this.bindIndicatorOffset + j);
		/* 1291 */this.valueLengthsOffset = (this.indicatorsOffset + k);

		/* 1293 */int n = this.indicatorsOffset;
		/* 1294 */int i1 = this.valueLengthsOffset;
		/* 1295 */int i2 = this.bindIndicatorSubRange + 3;

		/* 1299 */for (int i3 = 0; i3 < this.numberOfBindPositions; i3++) {
			/* 1303 */this.bindIndicators[(i2 + 5)] = (short) (n >> 16);

			/* 1306 */this.bindIndicators[(i2 + 6)] = (short) (n & 0xFFFF);

			/* 1309 */this.bindIndicators[(i2 + 7)] = (short) (i1 >> 16);

			/* 1311 */this.bindIndicators[(i2 + 8)] = (short) (i1 & 0xFFFF);

			/* 1315 */n += paramInt;
			/* 1316 */i1 += paramInt;
			/* 1317 */i2 += 10;
		}
	}

	void initializeBinds() throws SQLException {
		/* 1335 */this.numberOfBindPositions = this.sqlObject.getParameterCount();

		/* 1337 */if (this.numberOfBindPositions == 0) {
			/* 1341 */this.currentRowNeedToPrepareBinds = false;

			/* 1343 */return;
		}

		/* 1349 */this.numberOfBindRowsAllocated = this.batch;

		/* 1352 */this.binders = new Binder[this.numberOfBindRowsAllocated][this.numberOfBindPositions];

		/* 1354 */this.currentRowBinders = this.binders[0];

		/* 1356 */this.currentRowCharLens = new int[this.numberOfBindPositions];
		/* 1357 */this.currentBatchCharLens = new int[this.numberOfBindPositions];

		/* 1359 */this.currentRowFormOfUse = new short[this.numberOfBindPositions];
		/* 1360 */this.currentBatchFormOfUse = new short[this.numberOfBindPositions];

		/* 1362 */int i = 1;

		/* 1364 */if (this.connection.defaultNChar) {
			/* 1365 */i = 2;
		}
		/* 1367 */for (int j = 0; j < this.numberOfBindPositions; j++) {
			/* 1369 */this.currentRowFormOfUse[j] = i;
			/* 1370 */this.currentBatchFormOfUse[j] = i;
		}

		/* 1373 */this.lastBinders = new Binder[this.numberOfBindPositions];
		/* 1374 */this.lastBoundCharLens = new int[this.numberOfBindPositions];
		/* 1375 */this.lastBoundByteOffsets = new int[this.numberOfBindPositions];
		/* 1376 */this.lastBoundCharOffsets = new int[this.numberOfBindPositions];
		/* 1377 */this.lastBoundByteLens = new int[this.numberOfBindPositions];
		/* 1378 */this.lastBoundInds = new short[this.numberOfBindPositions];
		/* 1379 */this.lastBoundLens = new short[this.numberOfBindPositions];

		/* 1381 */this.lastBoundTypeBytes = new byte[this.numberOfBindPositions][];
		/* 1382 */this.lastBoundTypeOtypes = new OracleTypeADT[this.numberOfBindPositions];

		/* 1385 */allocBinds(this.numberOfBindRowsAllocated);
	}

	void growBinds(int paramInt) throws SQLException {
		/* 1402 */Binder[][] arrayOfBinder = this.binders;

		/* 1404 */this.binders = new Binder[paramInt][];

		/* 1407 */if (arrayOfBinder != null) {
			/* 1408 */System.arraycopy(arrayOfBinder, 0, this.binders, 0, this.numberOfBindRowsAllocated);
		}

		/* 1412 */for (int i = this.numberOfBindRowsAllocated; i < paramInt;) {
			/* 1414 */this.binders[i] = new Binder[this.numberOfBindPositions];

			/* 1413 */i++;
		}

		/* 1420 */allocBinds(paramInt);
		Object localObject;
		/* 1424 */if (this.parameterInt != null) {
			/* 1426 */localObject = this.parameterInt;

			/* 1428 */this.parameterInt = new int[paramInt][];

			/* 1430 */System.arraycopy(localObject, 0, this.parameterInt, 0, this.numberOfBindRowsAllocated);

			/* 1433 */i = this.numberOfBindRowsAllocated;
			/* 1434 */for (; i < paramInt; i++) {
				/* 1435 */this.parameterInt[i] = new int[this.numberOfBindPositions];
			}
		}
		/* 1438 */if (this.parameterLong != null) {
			/* 1440 */localObject = this.parameterLong;

			/* 1442 */this.parameterLong = new long[paramInt][];

			/* 1444 */System.arraycopy(localObject, 0, this.parameterLong, 0, this.numberOfBindRowsAllocated);

			/* 1447 */i = this.numberOfBindRowsAllocated;
			/* 1448 */for (; i < paramInt; i++) {
				/* 1449 */this.parameterLong[i] = new long[this.numberOfBindPositions];
			}
		}
		/* 1452 */if (this.parameterFloat != null) {
			/* 1454 */localObject = this.parameterFloat;

			/* 1456 */this.parameterFloat = new float[paramInt][];

			/* 1458 */System.arraycopy(localObject, 0, this.parameterFloat, 0, this.numberOfBindRowsAllocated);

			/* 1461 */i = this.numberOfBindRowsAllocated;
			/* 1462 */for (; i < paramInt; i++) {
				/* 1463 */this.parameterFloat[i] = new float[this.numberOfBindPositions];
			}
		}
		/* 1466 */if (this.parameterDouble != null) {
			/* 1468 */localObject = this.parameterDouble;

			/* 1470 */this.parameterDouble = new double[paramInt][];

			/* 1472 */System.arraycopy(localObject, 0, this.parameterDouble, 0, this.numberOfBindRowsAllocated);

			/* 1475 */i = this.numberOfBindRowsAllocated;
			/* 1476 */for (; i < paramInt; i++) {
				/* 1477 */this.parameterDouble[i] = new double[this.numberOfBindPositions];
			}
		}
		/* 1480 */if (this.parameterBigDecimal != null) {
			/* 1482 */localObject = this.parameterBigDecimal;

			/* 1484 */this.parameterBigDecimal = new BigDecimal[paramInt][];

			/* 1487 */System.arraycopy(localObject, 0, this.parameterBigDecimal, 0, this.numberOfBindRowsAllocated);

			/* 1490 */i = this.numberOfBindRowsAllocated;
			/* 1491 */for (; i < paramInt; i++) {
				/* 1492 */this.parameterBigDecimal[i] = new BigDecimal[this.numberOfBindPositions];
			}
		}
		/* 1495 */if (this.parameterString != null) {
			/* 1497 */localObject = this.parameterString;

			/* 1499 */this.parameterString = new String[paramInt][];

			/* 1501 */System.arraycopy(localObject, 0, this.parameterString, 0, this.numberOfBindRowsAllocated);

			/* 1504 */i = this.numberOfBindRowsAllocated;
			/* 1505 */for (; i < paramInt; i++) {
				/* 1506 */this.parameterString[i] = new String[this.numberOfBindPositions];
			}
		}
		/* 1509 */if (this.parameterDate != null) {
			/* 1511 */localObject = this.parameterDate;

			/* 1513 */this.parameterDate = new Date[paramInt][];

			/* 1515 */System.arraycopy(localObject, 0, this.parameterDate, 0, this.numberOfBindRowsAllocated);

			/* 1518 */i = this.numberOfBindRowsAllocated;
			/* 1519 */for (; i < paramInt; i++) {
				/* 1520 */this.parameterDate[i] = new Date[this.numberOfBindPositions];
			}
		}
		/* 1523 */if (this.parameterTime != null) {
			/* 1525 */localObject = this.parameterTime;

			/* 1527 */this.parameterTime = new Time[paramInt][];

			/* 1529 */System.arraycopy(localObject, 0, this.parameterTime, 0, this.numberOfBindRowsAllocated);

			/* 1532 */i = this.numberOfBindRowsAllocated;
			/* 1533 */for (; i < paramInt; i++) {
				/* 1534 */this.parameterTime[i] = new Time[this.numberOfBindPositions];
			}
		}
		/* 1537 */if (this.parameterTimestamp != null) {
			/* 1539 */localObject = this.parameterTimestamp;

			/* 1541 */this.parameterTimestamp = new Timestamp[paramInt][];

			/* 1543 */System.arraycopy(localObject, 0, this.parameterTimestamp, 0, this.numberOfBindRowsAllocated);

			/* 1546 */i = this.numberOfBindRowsAllocated;
			/* 1547 */for (; i < paramInt; i++) {
				/* 1548 */this.parameterTimestamp[i] = new Timestamp[this.numberOfBindPositions];
			}
		}
		/* 1551 */if (this.parameterDatum != null) {
			/* 1553 */localObject = this.parameterDatum;

			/* 1555 */this.parameterDatum = new byte[paramInt][][];

			/* 1557 */System.arraycopy(localObject, 0, this.parameterDatum, 0, this.numberOfBindRowsAllocated);

			/* 1560 */i = this.numberOfBindRowsAllocated;
			/* 1561 */for (; i < paramInt; i++) {
				/* 1562 */this.parameterDatum[i] = new byte[this.numberOfBindPositions][];
			}
		}
		/* 1565 */if (this.parameterOtype != null) {
			/* 1567 */localObject = this.parameterOtype;

			/* 1569 */this.parameterOtype = new OracleTypeADT[paramInt][];

			/* 1571 */System.arraycopy(localObject, 0, this.parameterOtype, 0, this.numberOfBindRowsAllocated);

			/* 1574 */i = this.numberOfBindRowsAllocated;
			/* 1575 */for (; i < paramInt; i++) {
				/* 1576 */this.parameterOtype[i] = new OracleTypeADT[this.numberOfBindPositions];
			}
		}
		/* 1579 */if (this.parameterStream != null) {
			/* 1581 */localObject = this.parameterStream;

			/* 1583 */this.parameterStream = new InputStream[paramInt][];

			/* 1585 */System.arraycopy(localObject, 0, this.parameterStream, 0, this.numberOfBindRowsAllocated);

			/* 1588 */i = this.numberOfBindRowsAllocated;
			/* 1589 */for (; i < paramInt; i++) {
				/* 1590 */this.parameterStream[i] = new InputStream[this.numberOfBindPositions];
			}
		}
		/* 1593 */if (this.parameterPlsqlIbt != null) {
			/* 1595 */localObject = this.parameterPlsqlIbt;

			/* 1597 */this.parameterPlsqlIbt = new PlsqlIbtBindInfo[paramInt][];

			/* 1600 */System.arraycopy(localObject, 0, this.parameterPlsqlIbt, 0, this.numberOfBindRowsAllocated);

			/* 1603 */i = this.numberOfBindRowsAllocated;
			/* 1604 */for (; i < paramInt; i++) {
				/* 1605 */this.parameterPlsqlIbt[i] = new PlsqlIbtBindInfo[this.numberOfBindPositions];
			}
		}

		/* 1609 */this.numberOfBindRowsAllocated = paramInt;

		/* 1612 */this.currentRowNeedToPrepareBinds = true;
	}

	void processCompletedBindRow(int paramInt, boolean paramBoolean) throws SQLException {
		/* 1679 */if (this.numberOfBindPositions == 0) {
			/* 1682 */return;
		}

		/* 1688 */int j = 0;
		/* 1689 */int k = 0;
		/* 1690 */int m = this.currentRank == this.firstRowInBatch ? 1 : 0;

		/* 1697 */Binder[] arrayOfBinder = this.currentRank == 0 ? this.lastBinders : this.lastBinders[0] == null ? null : this.binders[(this.currentRank - 1)];
		Object localObject1;
		Object localObject2;
		/* 1701 */if (this.currentRowBindAccessors == null) {
			/* 1705 */if (arrayOfBinder == null) {
				/* 1709 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 1710 */if (this.currentRowBinders[i] == null) {
						/* 1711 */DatabaseError.throwSqlException(41, new Integer(i + 1));
					}
				}
			}
			/* 1715 */if (this.checkBindTypes) {
				/* 1720 */localObject1 = this.parameterOtype == null ? null : this.currentRank == 0 ? this.lastBoundTypeOtypes : this.parameterOtype[(this.currentRank - 1)];

				/* 1724 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 1726 */localObject2 = this.currentRowBinders[i];

					/* 1728 */if (localObject2 == null) {
						/* 1733 */if (this.clearParameters) {
							/* 1734 */DatabaseError.throwSqlException(41, new Integer(i + 1));
						}

						/* 1744 */this.currentRowBinders[i] = arrayOfBinder[i].copyingBinder();

						/* 1746 */this.currentRowCharLens[i] = -1;

						/* 1748 */if (m != 0) {
							/* 1754 */this.lastBoundNeeded = true;
						}
					} else {
						/* 1758 */int i3 = ((Binder) localObject2).type;

						/* 1760 */if ((i3 == arrayOfBinder[i].type)
								&& (((i3 != 109) && (i3 != 111)) || (this.parameterOtype[this.currentRank][i].isInHierarchyOf(localObject1[i])))) {
							if (i3 == 9) {
								/* 1760 */if ((((Binder) localObject2).bytelen == 0 ? 1 : 0) == (arrayOfBinder[i].bytelen == 0 ? 1 : 0))
									;
							}

						} else {
							/* 1769 */j = 1;
						}
					}
					/* 1772 */if (this.currentBatchFormOfUse[i] == this.currentRowFormOfUse[i]) {
						continue;
					}
					/* 1776 */j = 1;
				}

			}

			/* 1785 */for (i = 0; i < this.numberOfBindPositions; i++) {
				/* 1787 */localObject1 = this.currentRowBinders[i];

				/* 1789 */if (localObject1 != null) {
					continue;
				}

				/* 1794 */if (this.clearParameters) {
					/* 1795 */DatabaseError.throwSqlException(41, new Integer(i + 1));
				}

				/* 1805 */this.currentRowBinders[i] = arrayOfBinder[i].copyingBinder();

				/* 1807 */this.currentRowCharLens[i] = -1;

				/* 1809 */if (m == 0) {
					continue;
				}

				/* 1815 */this.lastBoundNeeded = true;
			}

		}

		/* 1824 */if (arrayOfBinder == null) {
			/* 1830 */for (i = 0; i < this.numberOfBindPositions; i++) {
				/* 1832 */localObject1 = this.currentRowBinders[i];
				/* 1833 */localObject2 = this.currentRowBindAccessors[i];

				/* 1835 */if (localObject1 == null) {
					/* 1840 */if (localObject2 == null) {
						/* 1844 */DatabaseError.throwSqlException(41, new Integer(i + 1));
					} else {
						/* 1851 */this.currentRowBinders[i] = this.theOutBinder;
					}
				} else {
					/* 1853 */if ((localObject2 == null) || (((Accessor) localObject2).defineType == ((Binder) localObject1).type)) {
						continue;
					}

					/* 1861 */if ((this.connection.looseTimestampDateCheck) && (((Binder) localObject1).type == 180) && (((Accessor) localObject2).defineType == 12)) {
						continue;
					}
					/* 1865 */k = 1;
				}
			}
		}

		/* 1870 */if (this.checkBindTypes) {
			/* 1877 */localObject1 = this.parameterOtype == null ? null : this.currentRank == 0 ? this.lastBoundTypeOtypes : this.parameterOtype[(this.currentRank - 1)];

			/* 1881 */for (i = 0; i < this.numberOfBindPositions; i++) {
				/* 1883 */localObject2 = this.currentRowBinders[i];
				/* 1884 */Object localObject3 = this.currentRowBindAccessors[i];

				/* 1886 */if (localObject2 == null) {
					/* 1892 */if ((this.clearParameters) && (arrayOfBinder[i] != this.theOutBinder)) {
						/* 1894 */DatabaseError.throwSqlException(41, new Integer(i + 1));
					}

					/* 1906 */localObject2 = arrayOfBinder[i];
					/* 1907 */this.currentRowBinders[i] = localObject2;
					/* 1908 */this.currentRowCharLens[i] = -1;

					/* 1910 */if ((m != 0) && (localObject2 != this.theOutBinder)) {
						/* 1916 */this.lastBoundNeeded = true;
					}
				} else {
					/* 1920 */int i4 = ((Binder) localObject2).type;

					/* 1922 */if ((i4 == arrayOfBinder[i].type) && (((i4 != 109) && (i4 != 111)) || (this.parameterOtype[this.currentRank][i].isInHierarchyOf(localObject1[i])))) {
						if (i4 == 9) {
							/* 1922 */if ((((Binder) localObject2).bytelen == 0 ? 1 : 0) == (arrayOfBinder[i].bytelen == 0 ? 1 : 0))
								;
						}

					} else {
						/* 1931 */j = 1;
					}
				}
				/* 1934 */if (this.currentBatchFormOfUse[i] != this.currentRowFormOfUse[i]) {
					/* 1938 */j = 1;
				}
				/* 1940 */Accessor localAccessor = this.currentBatchBindAccessors[i];

				/* 1942 */if (localObject3 == null) {
					/* 1948 */localObject3 = localAccessor;
					/* 1949 */this.currentRowBindAccessors[i] = localObject3;
				}
				/* 1951 */else if ((localAccessor != null) && (((Accessor) localObject3).defineType != localAccessor.defineType)) {
					/* 1956 */j = 1;
				}
				/* 1958 */if ((localObject3 == null) || (localObject2 == this.theOutBinder) || (((Accessor) localObject3).defineType == ((Binder) localObject2).type)) {
					continue;
				}

				/* 1967 */if ((this.connection.looseTimestampDateCheck) && (((Binder) localObject2).type == 180) && (((Accessor) localObject3).defineType == 12)) {
					continue;
				}
				/* 1971 */k = 1;
			}

		}

		/* 1982 */for (int i = 0; i < this.numberOfBindPositions; i++) {
			/* 1984 */localObject1 = this.currentRowBinders[i];

			/* 1986 */if (localObject1 == null) {
				/* 1992 */if ((this.clearParameters) && (arrayOfBinder[i] != this.theOutBinder)) {
					/* 1993 */DatabaseError.throwSqlException(41, new Integer(i + 1));
				}

				/* 2004 */localObject1 = arrayOfBinder[i];
				/* 2005 */this.currentRowBinders[i] = localObject1;
				/* 2006 */this.currentRowCharLens[i] = -1;

				/* 2008 */if ((m != 0) && (localObject1 != this.theOutBinder)) {
					/* 2014 */this.lastBoundNeeded = true;
				}
			}
			/* 2017 */if (this.currentRowBindAccessors[i] != null) {
				continue;
			}

			/* 2022 */this.currentRowBindAccessors[i] = this.currentBatchBindAccessors[i];
		}
		int n;
		/* 2028 */if (j != 0) {
			/* 2032 */if (m == 0) {
				/* 2038 */if (this.m_batchStyle == 2) {
					/* 2045 */pushBatch(false);
				} else {
					/* 2054 */n = this.validRows;

					/* 2056 */this.prematureBatchCount = sendBatch();
					/* 2057 */this.validRows = n;
				}

			}

			/* 2063 */this.needToParse = true;

			/* 2067 */this.currentRowNeedToPrepareBinds = true;
		}
		/* 2070 */else if (paramBoolean) {
			/* 2076 */pushBatch(false);

			/* 2080 */this.needToParse = false;

			/* 2088 */this.currentBatchNeedToPrepareBinds = false;
		}

		/* 2091 */if (k != 0) {
			/* 2097 */DatabaseError.throwSqlException(12);
		}

		/* 2108 */for (i = 0; i < this.numberOfBindPositions; i++) {
			/* 2110 */n = this.currentRowCharLens[i];

			/* 2112 */if (n == -1) {
				/* 2113 */n = this.lastBoundCharLens[i];
			}
			/* 2115 */if (this.currentBatchCharLens[i] < n) {
				/* 2116 */this.currentBatchCharLens[i] = n;
			}
			/* 2118 */this.currentRowCharLens[i] = 0;
			/* 2119 */this.currentBatchFormOfUse[i] = this.currentRowFormOfUse[i];
		}

		/* 2124 */if (this.currentRowNeedToPrepareBinds) {
			/* 2125 */this.currentBatchNeedToPrepareBinds = true;
		}

		/* 2134 */if (this.currentRowBindAccessors != null) {
			/* 2136 */Accessor[] arrayOfAccessor = this.currentBatchBindAccessors;

			/* 2138 */this.currentBatchBindAccessors = this.currentRowBindAccessors;

			/* 2140 */if (arrayOfAccessor == null)
				/* 2141 */arrayOfAccessor = new Accessor[this.numberOfBindPositions];
			else {
				/* 2143 */for (i = 0; i < this.numberOfBindPositions; i++)
					/* 2144 */arrayOfAccessor[i] = null;
			}
			/* 2146 */this.currentRowBindAccessors = arrayOfAccessor;
		}

		/* 2149 */int i1 = this.currentRank + 1;

		/* 2151 */if (i1 < paramInt) {
			/* 2156 */if (i1 >= this.numberOfBindRowsAllocated) {
				/* 2162 */int i2 = this.numberOfBindRowsAllocated << 1;

				/* 2164 */if (i2 <= i1) {
					/* 2165 */i2 = i1 + 1;
				}
				/* 2167 */growBinds(i2);

				/* 2169 */this.currentBatchNeedToPrepareBinds = true;
			}

			/* 2173 */this.currentRowBinders = this.binders[i1];
		} else {
			/* 2182 */setupBindBuffers(0, paramInt);

			/* 2189 */this.currentRowBinders = this.binders[0];
		}

		/* 2194 */this.currentRowNeedToPrepareBinds = false;

		/* 2196 */this.clearParameters = false;
	}

	void processPlsqlIndexTabBinds(int paramInt) throws SQLException {
		/* 2201 */int i = 0;
		/* 2202 */int j = 0;
		/* 2203 */int k = 0;
		/* 2204 */int m = 0;

		/* 2206 */Binder[] arrayOfBinder = this.binders[paramInt];
		/* 2207 */PlsqlIbtBindInfo[] arrayOfPlsqlIbtBindInfo = this.parameterPlsqlIbt == null ? null : this.parameterPlsqlIbt[paramInt];
		Accessor localAccessor3;
		/* 2211 */for (Accessor localAccessor1 = 0; localAccessor1 < this.numberOfBindPositions; localAccessor1++) {
			/* 2213 */Binder localBinder1 = arrayOfBinder[localAccessor1];
			/* 2214 */localAccessor3 = this.currentBatchBindAccessors == null ? null : this.currentBatchBindAccessors[localAccessor1];

			/* 2216 */PlsqlIndexTableAccessor localPlsqlIndexTableAccessor1 = (localAccessor3 == null) || (localAccessor3.defineType != 998) ? null
					: (PlsqlIndexTableAccessor) localAccessor3;

			/* 2222 */if (localBinder1.type == 998) {
				/* 2224 */PlsqlIbtBindInfo localPlsqlIbtBindInfo1 = arrayOfPlsqlIbtBindInfo[localAccessor1];

				/* 2226 */if (localPlsqlIndexTableAccessor1 != null) {
					/* 2228 */if (localPlsqlIbtBindInfo1.element_internal_type != localPlsqlIndexTableAccessor1.elementInternalType) {
						/* 2230 */DatabaseError.throwSqlException(12);
					}
					/* 2232 */if (localPlsqlIbtBindInfo1.maxLen < localPlsqlIndexTableAccessor1.maxNumberOfElements) {
						/* 2233 */localPlsqlIbtBindInfo1.maxLen = localPlsqlIndexTableAccessor1.maxNumberOfElements;
					}
					/* 2235 */if (localPlsqlIbtBindInfo1.elemMaxLen < localPlsqlIndexTableAccessor1.elementMaxLen) {
						/* 2236 */localPlsqlIbtBindInfo1.elemMaxLen = localPlsqlIndexTableAccessor1.elementMaxLen;
					}
					/* 2238 */if (localPlsqlIbtBindInfo1.ibtByteLength > 0) {
						/* 2239 */localPlsqlIbtBindInfo1.ibtByteLength = (localPlsqlIbtBindInfo1.elemMaxLen * localPlsqlIbtBindInfo1.maxLen);
					} else {
						/* 2242 */localPlsqlIbtBindInfo1.ibtCharLength = (localPlsqlIbtBindInfo1.elemMaxLen * localPlsqlIbtBindInfo1.maxLen);
					}
				}

				/* 2246 */i++;
				/* 2247 */k += localPlsqlIbtBindInfo1.ibtByteLength;
				/* 2248 */m += localPlsqlIbtBindInfo1.ibtCharLength;
				/* 2249 */j += localPlsqlIbtBindInfo1.maxLen;
			} else {
				/* 2251 */if (localPlsqlIndexTableAccessor1 == null)
					continue;
				/* 2253 */i++;
				/* 2254 */k += localPlsqlIndexTableAccessor1.ibtByteLength;
				/* 2255 */m += localPlsqlIndexTableAccessor1.ibtCharLength;
				/* 2256 */j += localPlsqlIndexTableAccessor1.maxNumberOfElements;
			}
		}

		/* 2260 */if (i == 0) {
			/* 2261 */return;
		}

		/* 2288 */this.ibtBindIndicatorSize = (6 + i * 8 + j * 2);

		/* 2291 */this.ibtBindIndicators = new short[this.ibtBindIndicatorSize];
		/* 2292 */this.ibtBindIndicatorOffset = 0;

		/* 2294 */if (k > 0)
			/* 2295 */this.ibtBindBytes = new byte[k];
		/* 2296 */this.ibtBindByteOffset = 0;

		/* 2298 */if (m > 0)
			/* 2299 */this.ibtBindChars = new char[m];
		/* 2300 */this.ibtBindCharOffset = 0;

		/* 2302 */localAccessor1 = this.ibtBindByteOffset;
		/* 2303 */Accessor localAccessor2 = this.ibtBindCharOffset;

		/* 2305 */int n = this.ibtBindIndicatorOffset;
		/* 2306 */int i1 = n + 6 + i * 8;

		/* 2308 */this.ibtBindIndicators[(n++)] = (short) (i >> 16);
		/* 2309 */this.ibtBindIndicators[(n++)] = (short) (i & 0xFFFF);

		/* 2311 */this.ibtBindIndicators[(n++)] = (short) (k >> 16);
		/* 2312 */this.ibtBindIndicators[(n++)] = (short) (k & 0xFFFF);
		/* 2313 */this.ibtBindIndicators[(n++)] = (short) (m >> 16);
		/* 2314 */this.ibtBindIndicators[(n++)] = (short) (m & 0xFFFF);

		/* 2317 */for (int i2 = 0; i2 < this.numberOfBindPositions; i2++) {
			/* 2319 */Binder localBinder2 = arrayOfBinder[i2];
			/* 2320 */Accessor localAccessor4 = this.currentBatchBindAccessors == null ? null : this.currentBatchBindAccessors[i2];

			/* 2322 */PlsqlIndexTableAccessor localPlsqlIndexTableAccessor2 = (localAccessor4 == null) || (localAccessor4.defineType != 998) ? null
					: (PlsqlIndexTableAccessor) localAccessor4;

			/* 2328 */if (localBinder2.type == 998) {
				/* 2330 */PlsqlIbtBindInfo localPlsqlIbtBindInfo2 = arrayOfPlsqlIbtBindInfo[i2];
				/* 2331 */int i4 = localPlsqlIbtBindInfo2.maxLen;

				/* 2333 */this.ibtBindIndicators[(n++)] = (short) localPlsqlIbtBindInfo2.element_internal_type;

				/* 2335 */this.ibtBindIndicators[(n++)] = (short) localPlsqlIbtBindInfo2.elemMaxLen;
				/* 2336 */this.ibtBindIndicators[(n++)] = (short) (i4 >> 16);
				/* 2337 */this.ibtBindIndicators[(n++)] = (short) (i4 & 0xFFFF);
				/* 2338 */this.ibtBindIndicators[(n++)] = (short) (localPlsqlIbtBindInfo2.curLen >> 16);
				/* 2339 */this.ibtBindIndicators[(n++)] = (short) (localPlsqlIbtBindInfo2.curLen & 0xFFFF);

				/* 2341 */if (localPlsqlIbtBindInfo2.ibtByteLength > 0) {
					/* 2343 */localAccessor3 = localAccessor1;
					/* 2344 */localAccessor1 += localPlsqlIbtBindInfo2.ibtByteLength;
				} else {
					/* 2348 */localAccessor3 = localAccessor2;
					/* 2349 */localAccessor2 += localPlsqlIbtBindInfo2.ibtCharLength;
				}

				/* 2352 */this.ibtBindIndicators[(n++)] = (short) (localAccessor3 >> 16);
				/* 2353 */this.ibtBindIndicators[(n++)] = (short) (localAccessor3 & 0xFFFF);
				/* 2354 */localPlsqlIbtBindInfo2.ibtValueIndex = localAccessor3;

				/* 2356 */localPlsqlIbtBindInfo2.ibtIndicatorIndex = i1;
				/* 2357 */localPlsqlIbtBindInfo2.ibtLengthIndex = (i1 + i4);

				/* 2359 */if (localPlsqlIndexTableAccessor2 != null) {
					/* 2361 */localPlsqlIndexTableAccessor2.ibtIndicatorIndex = localPlsqlIbtBindInfo2.ibtIndicatorIndex;
					/* 2362 */localPlsqlIndexTableAccessor2.ibtLengthIndex = localPlsqlIbtBindInfo2.ibtLengthIndex;
					/* 2363 */localPlsqlIndexTableAccessor2.ibtMetaIndex = (n - 8);
					/* 2364 */localPlsqlIndexTableAccessor2.ibtValueIndex = localAccessor3;
				}

				/* 2367 */i1 += 2 * i4;
			} else {
				/* 2369 */if (localPlsqlIndexTableAccessor2 == null) {
					continue;
				}
				/* 2372 */int i3 = localPlsqlIndexTableAccessor2.maxNumberOfElements;

				/* 2374 */this.ibtBindIndicators[(n++)] = (short) localPlsqlIndexTableAccessor2.elementInternalType;

				/* 2376 */this.ibtBindIndicators[(n++)] = (short) localPlsqlIndexTableAccessor2.elementMaxLen;
				/* 2377 */this.ibtBindIndicators[(n++)] = (short) (i3 >> 16);
				/* 2378 */this.ibtBindIndicators[(n++)] = (short) (i3 & 0xFFFF);
				/* 2379 */this.ibtBindIndicators[(n++)] = 0;
				/* 2380 */this.ibtBindIndicators[(n++)] = 0;

				/* 2382 */if (localPlsqlIndexTableAccessor2.ibtByteLength > 0) {
					/* 2384 */localAccessor3 = localAccessor1;
					/* 2385 */localAccessor1 += localPlsqlIndexTableAccessor2.ibtByteLength;
				} else {
					/* 2389 */localAccessor3 = localAccessor2;
					/* 2390 */localAccessor2 += localPlsqlIndexTableAccessor2.ibtCharLength;
				}

				/* 2393 */this.ibtBindIndicators[(n++)] = (short) (localAccessor3 >> 16);
				/* 2394 */this.ibtBindIndicators[(n++)] = (short) (localAccessor3 & 0xFFFF);
				/* 2395 */localPlsqlIndexTableAccessor2.ibtValueIndex = localAccessor3;

				/* 2397 */localPlsqlIndexTableAccessor2.ibtIndicatorIndex = i1;
				/* 2398 */localPlsqlIndexTableAccessor2.ibtLengthIndex = (i1 + i3);
				/* 2399 */localPlsqlIndexTableAccessor2.ibtMetaIndex = (n - 8);

				/* 2401 */i1 += 2 * i3;
			}
		}
	}

	void initializeBindSubRanges(int paramInt1, int paramInt2) {
		/* 2424 */this.bindByteSubRange = 0;
		/* 2425 */this.bindCharSubRange = 0;
	}

	void initializeIndicatorSubRange() {
		/* 2430 */this.bindIndicatorSubRange = 0;
	}

	void prepareBindPreambles(int paramInt1, int paramInt2) {
	}

	void setupBindBuffers(int paramInt1, int paramInt2) throws SQLException {
		try {
			/* 2486 */if (this.numberOfBindPositions == 0) {
				/* 2489 */return;
			}

			/* 2499 */this.preparedAllBinds = this.currentBatchNeedToPrepareBinds;
			/* 2500 */this.preparedCharBinds = false;

			/* 2504 */this.currentBatchNeedToPrepareBinds = false;

			/* 2507 */this.numberOfBoundRows = paramInt2;
			/* 2508 */this.bindIndicators[(this.bindIndicatorSubRange + 2)] = (short) this.numberOfBoundRows;

			/* 2516 */int j = this.bindBufferCapacity;

			/* 2518 */if (this.numberOfBoundRows > this.bindBufferCapacity) {
				/* 2520 */j = this.numberOfBoundRows;
				/* 2521 */this.preparedAllBinds = true;
			}

			/* 2524 */if (this.currentBatchBindAccessors != null) {
				/* 2531 */if (this.outBindAccessors == null) {
					/* 2532 */this.outBindAccessors = new Accessor[this.numberOfBindPositions];
				}
				/* 2534 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 2536 */Accessor localAccessor1 = this.currentBatchBindAccessors[i];

					/* 2538 */this.outBindAccessors[i] = localAccessor1;

					/* 2540 */if (localAccessor1 == null)
						continue;
					/* 2542 */m = localAccessor1.charLength;

					/* 2544 */if (this.currentBatchCharLens[i] >= m) {
						continue;
					}

					/* 2549 */this.currentBatchCharLens[i] = m;
				}

			}

			/* 2555 */int k = 0;
			/* 2556 */int m = 0;

			/* 2562 */int n = this.bindIndicatorSubRange + 3;

			/* 2564 */int i1 = n;

			/* 2566 */if (this.preparedAllBinds) {
				/* 2571 */this.preparedCharBinds = true;

				/* 2577 */Binder[] arrayOfBinder = this.binders[paramInt1];

				/* 2579 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 2581 */Binder localBinder = arrayOfBinder[i];

					/* 2584 */i6 = this.currentBatchCharLens[i];

					/* 2586 */if (localBinder == this.theOutBinder) {
						/* 2588 */Accessor localAccessor2 = this.currentBatchBindAccessors[i];
						/* 2589 */i5 = localAccessor2.byteLength;
						/* 2590 */i4 = (short) localAccessor2.defineType;
					} else {
						/* 2594 */i5 = localBinder.bytelen;
						/* 2595 */i4 = localBinder.type;
					}

					/* 2598 */m += i5;
					/* 2599 */k += i6;
					/* 2600 */this.bindIndicators[(i1 + 0)] = i4;
					/* 2601 */this.bindIndicators[(i1 + 1)] = (short) i5;

					/* 2603 */this.bindIndicators[(i1 + 2)] = (short) i6;

					/* 2605 */this.bindIndicators[(i1 + 9)] = this.currentBatchFormOfUse[i];

					/* 2607 */i1 += 10;
				}
			}
			/* 2610 */if (this.preparedCharBinds) {
				/* 2616 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 2618 */i2 = this.currentBatchCharLens[i];

					/* 2620 */k += i2;
					/* 2621 */this.bindIndicators[(i1 + 2)] = (short) i2;

					/* 2623 */i1 += 10;
				}

			}

			/* 2638 */for (int i = 0; i < this.numberOfBindPositions; i++) {
				/* 2640 */i2 = i1 + 2;
				/* 2641 */i3 = this.currentBatchCharLens[i];
				/* 2642 */i4 = this.bindIndicators[i2];

				/* 2644 */if ((i4 >= i3) && (!this.preparedCharBinds)) {
					/* 2646 */this.currentBatchCharLens[i] = i4;
					/* 2647 */k += i4;
				} else {
					/* 2651 */this.bindIndicators[i2] = (short) i3;
					/* 2652 */k += i3;
					/* 2653 */this.preparedCharBinds = true;
				}

				/* 2656 */i1 += 10;
			}

			/* 2668 */if (this.preparedCharBinds) {
				/* 2669 */initializeBindSubRanges(this.numberOfBoundRows, j);
			}
			/* 2671 */if (this.preparedAllBinds) {
				/* 2677 */i2 = this.bindByteSubRange + m * j;

				/* 2682 */if ((this.lastBoundNeeded) || (i2 > this.totalBindByteLength)) {
					/* 2686 */this.bindByteOffset = 0;
					/* 2687 */this.bindBytes = new byte[i2];

					/* 2690 */this.totalBindByteLength = i2;
				}

				/* 2694 */this.bindBufferCapacity = j;

				/* 2697 */this.bindIndicators[(this.bindIndicatorSubRange + 1)] = (short) this.bindBufferCapacity;
			}

			/* 2703 */if (this.preparedCharBinds) {
				/* 2709 */i2 = this.bindCharSubRange + k * this.bindBufferCapacity;

				/* 2713 */if ((this.lastBoundNeeded) || (i2 > this.totalBindCharLength)) {
					/* 2717 */this.bindCharOffset = 0;
					/* 2718 */this.bindChars = new char[i2];

					/* 2721 */this.totalBindCharLength = i2;
				}

				/* 2726 */this.bindByteSubRange += this.bindByteOffset;
				/* 2727 */this.bindCharSubRange += this.bindCharOffset;
			}

			/* 2731 */int i2 = this.bindByteSubRange;
			/* 2732 */int i3 = this.bindCharSubRange;
			/* 2733 */int i4 = this.indicatorsOffset;
			/* 2734 */int i5 = this.valueLengthsOffset;

			/* 2736 */i1 = n;

			/* 2738 */if (this.preparedCharBinds) {
				/* 2740 */if (this.currentBatchBindAccessors == null) {
					/* 2744 */for (i = 0; i < this.numberOfBindPositions; i++) {
						/* 2748 */i6 = this.bindIndicators[(i1 + 1)];

						/* 2750 */i7 = this.currentBatchCharLens[i];

						/* 2752 */i8 = i7 == 0 ? i2 : i3;

						/* 2755 */this.bindIndicators[(i1 + 3)] = (short) (i8 >> 16);

						/* 2758 */this.bindIndicators[(i1 + 4)] = (short) (i8 & 0xFFFF);

						/* 2763 */i2 += i6 * this.bindBufferCapacity;
						/* 2764 */i3 += i7 * this.bindBufferCapacity;
						/* 2765 */i1 += 10;
					}

				}

				/* 2774 */for (i = 0; i < this.numberOfBindPositions; i++) {
					/* 2778 */i6 = this.bindIndicators[(i1 + 1)];

					/* 2780 */i7 = this.currentBatchCharLens[i];

					/* 2782 */i8 = i7 == 0 ? i2 : i3;

					/* 2785 */this.bindIndicators[(i1 + 3)] = (short) (i8 >> 16);

					/* 2788 */this.bindIndicators[(i1 + 4)] = (short) (i8 & 0xFFFF);

					/* 2793 */localObject = this.currentBatchBindAccessors[i];

					/* 2795 */if (localObject != null) {
						/* 2800 */if (i7 > 0) {
							/* 2802 */((Accessor) localObject).columnIndex = i3;
							/* 2803 */((Accessor) localObject).charLength = i7;
						} else {
							/* 2807 */((Accessor) localObject).columnIndex = i2;
							/* 2808 */((Accessor) localObject).byteLength = i6;
						}

						/* 2811 */((Accessor) localObject).lengthIndex = i5;
						/* 2812 */((Accessor) localObject).indicatorIndex = i4;
						/* 2813 */((Accessor) localObject).rowSpaceByte = this.bindBytes;
						/* 2814 */((Accessor) localObject).rowSpaceChar = this.bindChars;
						/* 2815 */((Accessor) localObject).rowSpaceIndicator = this.bindIndicators;

						/* 2817 */if ((((Accessor) localObject).defineType == 109) || (((Accessor) localObject).defineType == 111)) {
							/* 2822 */((Accessor) localObject).setOffsets(this.bindBufferCapacity);
						}

					}

					/* 2827 */i2 += i6 * this.bindBufferCapacity;
					/* 2828 */i3 += i7 * this.bindBufferCapacity;
					/* 2829 */i4 += this.numberOfBindRowsAllocated;
					/* 2830 */i5 += this.numberOfBindRowsAllocated;
					/* 2831 */i1 += 10;
				}

				/* 2837 */i2 = this.bindByteSubRange;
				/* 2838 */i3 = this.bindCharSubRange;
				/* 2839 */i4 = this.indicatorsOffset;
				/* 2840 */i5 = this.valueLengthsOffset;
				/* 2841 */i1 = n;
			}

			/* 2845 */int i6 = this.bindBufferCapacity - this.numberOfBoundRows;
			/* 2846 */int i7 = this.numberOfBoundRows - 1;
			/* 2847 */int i8 = i7 + paramInt1;
			/* 2848 */Object localObject = this.binders[i8];

			/* 2850 */if (this.parameterOtype != null) {
				/* 2852 */System.arraycopy(this.parameterDatum[i8], 0, this.lastBoundTypeBytes, 0, this.numberOfBindPositions);

				/* 2854 */System.arraycopy(this.parameterOtype[i8], 0, this.lastBoundTypeOtypes, 0, this.numberOfBindPositions);
			}

			/* 2858 */if (this.hasIbtBind) {
				/* 2859 */processPlsqlIndexTabBinds(paramInt1);
			}
			/* 2861 */if (this.returnParamAccessors != null)
				processDmlReturningBind();

			/* 2863 */boolean bool = ((this.sqlKind != 1) && (this.sqlKind != 4)) || (this.currentRowBindAccessors == null);

			/* 2873 */for (i = 0; i < this.numberOfBindPositions; i++) {
				/* 2877 */int i9 = this.bindIndicators[(i1 + 1)];

				/* 2879 */int i10 = this.currentBatchCharLens[i];

				/* 2882 */this.lastBinders[i] = localObject[i];
				/* 2883 */this.lastBoundByteLens[i] = i9;

				/* 2892 */for (int i11 = 0; i11 < this.numberOfBoundRows;) {
					/* 2895 */int i12 = paramInt1 + i11;

					/* 2897 */this.binders[i12][i].bind(this, i, i11, i12, this.bindBytes, this.bindChars, this.bindIndicators, i9, i10, i2, i3, i5 + i11, i4 + i11, bool);

					/* 2904 */this.binders[i12][i] = null;
					/* 2905 */i2 += i9;
					/* 2906 */i3 += i10;

					/* 2893 */i11++;
				}

				/* 2910 */this.lastBoundByteOffsets[i] = (i2 - i9);
				/* 2911 */this.lastBoundCharOffsets[i] = (i3 - i10);
				/* 2912 */this.lastBoundInds[i] = this.bindIndicators[(i4 + i7)];
				/* 2913 */this.lastBoundLens[i] = this.bindIndicators[(i5 + i7)];

				/* 2918 */this.lastBoundCharLens[i] = 0;

				/* 2921 */i2 += i6 * i9;
				/* 2922 */i3 += i6 * i10;
				/* 2923 */i4 += this.numberOfBindRowsAllocated;
				/* 2924 */i5 += this.numberOfBindRowsAllocated;
				/* 2925 */i1 += 10;
			}

			/* 2929 */this.lastBoundBytes = this.bindBytes;
			/* 2930 */this.lastBoundByteOffset = this.bindByteOffset;
			/* 2931 */this.lastBoundChars = this.bindChars;
			/* 2932 */this.lastBoundCharOffset = this.bindCharOffset;

			/* 2936 */int[] arrayOfInt = this.currentBatchCharLens;

			/* 2938 */this.currentBatchCharLens = this.lastBoundCharLens;
			/* 2939 */this.lastBoundCharLens = arrayOfInt;

			/* 2942 */this.lastBoundNeeded = false;

			/* 2946 */prepareBindPreambles(this.numberOfBoundRows, this.bindBufferCapacity);
		} catch (NullPointerException localNullPointerException) {
			/* 2952 */DatabaseError.throwSqlException(89);
		}
	}

	public void enterImplicitCache() throws SQLException {
		alwaysOnClose();

		if (!this.connection.isClosed()) {
			cleanAllTempLobs();
		}

		if (this.connection.clearStatementMetaData) {
			this.lastBoundBytes = null;
			this.lastBoundChars = null;
		}

		clearParameters();

		this.cacheState = 2;
		this.creationState = 1;

		this.currentResultSet = null;
		this.lastIndex = 0;

		this.queryTimeout = 0;
		this.autoRollback = 2;

		this.rowPrefetchChanged = false;
		this.currentRank = 0;
		this.currentRow = -1;
		this.validRows = 0;
		this.maxRows = 0;
		this.totalRowsVisited = 0;
		this.maxFieldSize = 0;
		this.gotLastBatch = false;
		this.clearParameters = true;
		this.scrollRset = null;
		this.needToAddIdentifier = false;
		this.defaultFetchDirection = 1000;
		this.defaultTZ = null;

		if (this.sqlKind == 3) {
			this.needToParse = true;
			this.needToPrepareDefineBuffer = true;
			this.columnsDefinedByUser = false;
		}

		if ((this.connection.isMemoryFreedOnEnteringCache) && (this.defineIndicators != null)) {
			this.cachedDefineByteSize = (this.defineBytes != null ? this.defineBytes.length : 0);
			this.cachedDefineCharSize = (this.defineChars != null ? this.defineChars.length : 0);
			this.cachedDefineIndicatorSize = (this.defineIndicators != null ? this.defineIndicators.length : 0);
			this.defineChars = null;
			this.defineBytes = null;
			this.defineIndicators = null;
		}

		if (this.accessors != null) {
			int i = this.accessors.length;

			for (int j = 0; j < i; j++) {
				if (this.accessors[j] == null)
					continue;
				if (this.connection.isMemoryFreedOnEnteringCache) {
					this.accessors[j].rowSpaceByte = null;
					this.accessors[j].rowSpaceChar = null;
					this.accessors[j].rowSpaceIndicator = null;
				}

				if (this.columnsDefinedByUser) {
					this.accessors[j].externalType = 0;
				}

			}

		}

		this.fixedString = this.connection.getDefaultFixedString();
		this.defaultRowPrefetch = this.rowPrefetch;

		if (this.connection.clearStatementMetaData) {
			this.sqlStringChanged = true;
			this.needToParse = true;
			this.needToPrepareDefineBuffer = true;
			this.columnsDefinedByUser = false;

			if (this.userRsetType == 0) {
				this.userRsetType = 1;
				this.realRsetType = 1;
			}
			this.currentRowNeedToPrepareBinds = true;
		}
	}

	public void enterExplicitCache() throws SQLException {
		/* 3105 */this.cacheState = 2;
		/* 3106 */this.creationState = 2;
		/* 3107 */this.defaultTZ = null;

		/* 3109 */alwaysOnClose();
	}

	public void exitImplicitCacheToActive() throws SQLException {
		this.cacheState = 1;
		this.closed = false;

		if (this.rowPrefetch != this.connection.getDefaultRowPrefetch()) {
			if (this.streamList == null) {
				this.rowPrefetch = this.connection.getDefaultRowPrefetch();
				this.defaultRowPrefetch = this.rowPrefetch;

				this.rowPrefetchChanged = true;
			}

		}
		if (this.batch != this.connection.getDefaultExecuteBatch()) {
			resetBatch();
		}

		if (this.autoRefetch != this.connection.getDefaultAutoRefetch()) {
			this.autoRefetch = this.connection.getDefaultAutoRefetch();
		}

		this.processEscapes = this.connection.processEscapes;

		if ((this.connection.isMemoryFreedOnEnteringCache) && (this.cachedDefineIndicatorSize != 0)) {
			this.defineBytes = new byte[this.cachedDefineByteSize];
			this.defineChars = new char[this.cachedDefineCharSize];
			this.defineIndicators = new short[this.cachedDefineIndicatorSize];
			if (this.accessors != null) {
				int i = this.accessors.length;

				for (int j = 0; j < i; j++) {
					if (this.accessors[j] == null)
						continue;
					if (!this.connection.isMemoryFreedOnEnteringCache)
						continue;
					this.accessors[j].rowSpaceByte = this.defineBytes;
					this.accessors[j].rowSpaceChar = this.defineChars;
					this.accessors[j].rowSpaceIndicator = this.defineIndicators;
				}
			}
		}
	}

	public void exitExplicitCacheToActive() throws SQLException {
		/* 3222 */this.cacheState = 1;
		/* 3223 */this.closed = false;
	}

	public void exitImplicitCacheToClose() throws SQLException {
		this.cacheState = 0;
		this.closed = false;

		synchronized (this) {
			hardClose();
		}
	}

	public void exitExplicitCacheToClose() throws SQLException {
		/* 3261 */this.cacheState = 0;
		/* 3262 */this.closed = false;

		/* 3264 */synchronized (this) {
			/* 3266 */hardClose();
		}
	}

	public void closeWithKey(String paramString) throws SQLException {
		/* 3284 */synchronized (this.connection) {
			/* 3286 */synchronized (this) {
				/* 3288 */closeOrCache(paramString);
			}
		}
	}

	int executeInternal() throws SQLException {
		this.noMoreUpdateCounts = false;

		ensureOpen();

		if ((this.currentRank > 0) && (this.m_batchStyle == 2)) {
			DatabaseError.throwSqlException(81, "batch must be either executed or cleared");
		}

		int i = this.userRsetType == 1 ? 1 : 0;

		prepareForNewResults(true, false);

		processCompletedBindRow(this.sqlKind == 0 ? 1 : this.batch, false);

		if ((i == 0) && (!this.scrollRsetTypeSolved)) {
			return doScrollPstmtExecuteUpdate() + this.prematureBatchCount;
		}
		/* 3316 */doExecuteWithTimeout();

		/* 3318 */int j = (this.prematureBatchCount != 0) && (this.validRows > 0) ? 1 : 0;

		/* 3320 */if (i == 0) {
			/* 3322 */this.currentResultSet = new OracleResultSetImpl(this.connection, this);
			/* 3323 */this.scrollRset = ResultSetUtil.createScrollResultSet(this, this.currentResultSet, this.realRsetType);

			/* 3327 */if (!this.connection.accumulateBatchResult) {
				/* 3328 */j = 0;
			}
		}
		/* 3331 */if (j != 0) {
			/* 3334 */this.validRows += this.prematureBatchCount;
			/* 3335 */this.prematureBatchCount = 0;
		}

		/* 3338 */return this.validRows;
	}

	public ResultSet executeQuery() throws SQLException {
		/* 3354 */synchronized (this.connection) {
			/* 3356 */synchronized (this) {
				/* 3359 */this.executionType = 1;

				/* 3361 */executeInternal();

				/* 3363 */if (this.userRsetType == 1) {
					/* 3365 */this.currentResultSet = new OracleResultSetImpl(this.connection, this);

					/* 3367 */return this.currentResultSet;
				}

				/* 3371 */if (this.scrollRset == null) {
					/* 3373 */this.currentResultSet = new OracleResultSetImpl(this.connection, this);
					/* 3374 */this.scrollRset = this.currentResultSet;
				}

				/* 3377 */return this.scrollRset;
			}
		}
	}

	public int executeUpdate() throws SQLException {
		/* 3394 */synchronized (this.connection) {
			/* 3396 */synchronized (this) {
				/* 3398 */this.executionType = 2;

				/* 3400 */return executeInternal();
			}
		}
	}

	public boolean execute() throws SQLException {
		/* 3416 */synchronized (this.connection) {
			/* 3418 */synchronized (this) {
				/* 3420 */this.executionType = 3;

				/* 3422 */executeInternal();

				/* 3424 */return this.sqlKind == 0;
			}
		}
	}

	void slideDownCurrentRow(int paramInt) {
		/* 3441 */if (this.binders != null) {
			/* 3443 */this.binders[paramInt] = this.binders[0];
			/* 3444 */this.binders[0] = this.currentRowBinders;
		}
		Object localObject;
		/* 3448 */if (this.parameterInt != null) {
			/* 3450 */localObject = this.parameterInt[0];

			/* 3452 */this.parameterInt[0] = this.parameterInt[paramInt];
			/* 3453 */this.parameterInt[paramInt] = localObject;
		}

		/* 3456 */if (this.parameterLong != null) {
			/* 3458 */localObject = this.parameterLong[0];

			/* 3460 */this.parameterLong[0] = this.parameterLong[paramInt];
			/* 3461 */this.parameterLong[paramInt] = localObject;
		}

		/* 3464 */if (this.parameterFloat != null) {
			/* 3466 */localObject = this.parameterFloat[0];

			/* 3468 */this.parameterFloat[0] = this.parameterFloat[paramInt];
			/* 3469 */this.parameterFloat[paramInt] = localObject;
		}

		/* 3472 */if (this.parameterDouble != null) {
			/* 3474 */localObject = this.parameterDouble[0];

			/* 3476 */this.parameterDouble[0] = this.parameterDouble[paramInt];
			/* 3477 */this.parameterDouble[paramInt] = localObject;
		}

		/* 3480 */if (this.parameterBigDecimal != null) {
			/* 3482 */localObject = this.parameterBigDecimal[0];

			/* 3484 */this.parameterBigDecimal[0] = this.parameterBigDecimal[paramInt];
			/* 3485 */this.parameterBigDecimal[paramInt] = localObject;
		}

		/* 3488 */if (this.parameterString != null) {
			/* 3490 */localObject = this.parameterString[0];

			/* 3492 */this.parameterString[0] = this.parameterString[paramInt];
			/* 3493 */this.parameterString[paramInt] = localObject;
		}

		/* 3496 */if (this.parameterDate != null) {
			/* 3498 */localObject = this.parameterDate[0];

			/* 3500 */this.parameterDate[0] = this.parameterDate[paramInt];
			/* 3501 */this.parameterDate[paramInt] = localObject;
		}

		/* 3504 */if (this.parameterTime != null) {
			/* 3506 */localObject = this.parameterTime[0];

			/* 3508 */this.parameterTime[0] = this.parameterTime[paramInt];
			/* 3509 */this.parameterTime[paramInt] = localObject;
		}

		/* 3512 */if (this.parameterTimestamp != null) {
			/* 3514 */localObject = this.parameterTimestamp[0];

			/* 3516 */this.parameterTimestamp[0] = this.parameterTimestamp[paramInt];
			/* 3517 */this.parameterTimestamp[paramInt] = localObject;
		}

		/* 3520 */if (this.parameterDatum != null) {
			/* 3522 */localObject = this.parameterDatum[0];

			/* 3524 */this.parameterDatum[0] = this.parameterDatum[paramInt];
			/* 3525 */this.parameterDatum[paramInt] = localObject;
		}

		/* 3528 */if (this.parameterOtype != null) {
			/* 3530 */localObject = this.parameterOtype[0];

			/* 3532 */this.parameterOtype[0] = this.parameterOtype[paramInt];
			/* 3533 */this.parameterOtype[paramInt] = localObject;
		}

		/* 3536 */if (this.parameterStream != null) {
			/* 3538 */localObject = this.parameterStream[0];

			/* 3540 */this.parameterStream[0] = this.parameterStream[paramInt];
			/* 3541 */this.parameterStream[paramInt] = localObject;
		}
	}

	void resetBatch() {
		/* 3547 */this.batch = this.connection.getDefaultExecuteBatch();
	}

	public int sendBatch() throws SQLException {
		/* 3575 */if (isJdbcBatchStyle()) {
			/* 3577 */return 0;
		}

		/* 3582 */synchronized (this.connection) {
			/* 3584 */synchronized (this) {
				try {
					/* 3600 */ensureOpen();

					/* 3604 */if (this.currentRank <= 0) {
						/* 3605 */i = this.connection.accumulateBatchResult ? 0 : this.validRows;

						/* 3655 */this.currentRank = 0;
						return i;
					}
					/* 3613 */int i = this.batch;
					try {
						/* 3617 */j = this.currentRank;

						/* 3619 */if (this.batch != this.currentRank) {
							/* 3620 */this.batch = this.currentRank;
						}
						/* 3622 */setupBindBuffers(0, this.currentRank);

						/* 3624 */this.currentRank -= 1;

						/* 3626 */doExecuteWithTimeout();

						/* 3630 */slideDownCurrentRow(j);

						/* 3635 */if (this.batch != i)
							/* 3636 */this.batch = i;
					} finally {
						/* 3635 */if (this.batch != i) {
							/* 3636 */this.batch = i;
						}

					}

					/* 3641 */if (this.connection.accumulateBatchResult) {
						/* 3644 */this.validRows += this.prematureBatchCount;
						/* 3645 */this.prematureBatchCount = 0;
					}

					/* 3648 */int j = this.validRows;

					/* 3655 */this.currentRank = 0;
					return j;
				} finally {
					this.currentRank = 0;
				}
			}
		}
	}

	public synchronized void setExecuteBatch(int paramInt) throws SQLException {
		/* 3700 */setOracleBatchStyle();
		/* 3701 */set_execute_batch(paramInt);
	}

	synchronized void set_execute_batch(int paramInt) throws SQLException {
		/* 3714 */if (paramInt <= 0) {
			/* 3715 */DatabaseError.throwSqlException(42);
		}
		/* 3717 */if (paramInt == this.batch) {
			/* 3718 */return;
		}

		/* 3724 */if (this.currentRank > 0) {
			/* 3729 */i = this.validRows;

			/* 3731 */this.prematureBatchCount = sendBatch();
			/* 3732 */this.validRows = i;
		}

		/* 3735 */int i = this.batch;

		/* 3737 */this.batch = paramInt;

		/* 3739 */if (this.numberOfBindRowsAllocated < this.batch)
			/* 3740 */growBinds(this.batch);
	}

	public final int getExecuteBatch() {
		/* 3753 */return this.batch;
	}

	public synchronized void defineParameterTypeBytes(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 3788 */if (paramInt3 < 0) {
			/* 3789 */DatabaseError.throwSqlException(53);
		}
		/* 3791 */if (paramInt1 < 1) {
			/* 3792 */DatabaseError.throwSqlException(3);
		}
		/* 3794 */switch (paramInt2) {
		case -7:

		case -6:

		case -5:

		case 2:

		case 3:

		case 4:

		case 5:

		case 6:

		case 7:

		case 8:
			/* 3819 */paramInt2 = 6;

			/* 3821 */break;
		case 1:
			/* 3826 */paramInt2 = 96;

			/* 3828 */break;
		case 12:
			/* 3833 */paramInt2 = 1;

			/* 3835 */break;
		case 91:

		case 92:
			/* 3842 */paramInt2 = 12;

			/* 3844 */break;
		case -103:
			/* 3849 */paramInt2 = 182;

			/* 3851 */break;
		case -104:
			/* 3856 */paramInt2 = 183;

			/* 3858 */break;
		case -100:

		case 93:
			/* 3865 */paramInt2 = 180;

			/* 3867 */break;
		case -101:
			/* 3872 */paramInt2 = 181;

			/* 3874 */break;
		case -102:
			/* 3879 */paramInt2 = 231;

			/* 3881 */break;
		case -3:

		case -2:
			/* 3888 */paramInt2 = 23;

			/* 3890 */break;
		case 100:
			/* 3895 */paramInt2 = 100;

			/* 3897 */break;
		case 101:
			/* 3902 */paramInt2 = 101;

			/* 3904 */break;
		case -8:
			/* 3909 */paramInt2 = 104;

			/* 3911 */break;
		case 2004:
			/* 3916 */paramInt2 = 113;

			/* 3918 */break;
		case 2005:
			/* 3923 */paramInt2 = 112;

			/* 3925 */break;
		case -13:
			/* 3930 */paramInt2 = 114;

			/* 3932 */break;
		case -10:
			/* 3937 */paramInt2 = 102;

			/* 3939 */break;
		case 0:
			/* 3943 */DatabaseError.throwSqlException(4);
		default:
			/* 3946 */DatabaseError.throwSqlException(23);
		}
	}

	public synchronized void defineParameterTypeChars(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 3987 */int i = this.connection.getNlsRatio();

		/* 3989 */if ((paramInt2 == 1) || (paramInt2 == 12))
			/* 3990 */defineParameterTypeBytes(paramInt1, paramInt2, paramInt3 * i);
		else
			/* 3992 */defineParameterTypeBytes(paramInt1, paramInt2, paramInt3);
	}

	public synchronized void defineParameterType(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 4009 */defineParameterTypeBytes(paramInt1, paramInt2, paramInt3);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		/* 4023 */ResultSet localResultSet = getResultSet();

		/* 4025 */if (localResultSet != null) {
			/* 4026 */return localResultSet.getMetaData();
		}
		/* 4028 */return null;
	}

	public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 4068 */setNullInternal(paramInt1, paramInt2, paramString);
	}

	void setNullInternal(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 4074 */int i = paramInt1 - 1;

		/* 4076 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 4077 */DatabaseError.throwSqlException(3);
		}
		/* 4079 */if ((paramInt2 == 2002) || (paramInt2 == 2008) || (paramInt2 == 2003) || (paramInt2 == 2007) || (paramInt2 == 2006)) {
			/* 4085 */synchronized (this.connection) {
				/* 4087 */synchronized (this) {
					/* 4089 */setNullCritial(i, paramInt2, paramString);

					/* 4091 */this.currentRowCharLens[i] = 0;
				}

			}

		}

		/* 4098 */setNullInternal(paramInt1, paramInt2);

		/* 4100 */return;
	}

	synchronized void setNullInternal(int paramInt1, int paramInt2) throws SQLException {
		/* 4107 */setNullCritical(paramInt1, paramInt2);
	}

	void setNullCritial(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 4113 */Object localObject1 = null;
		/* 4114 */Binder localBinder = this.theNamedTypeNullBinder;
		Object localObject2;
		/* 4116 */switch (paramInt2) {
		case 2006:
			/* 4120 */localBinder = this.theRefTypeNullBinder;
		case 2002:

		case 2008:
			/* 4126 */localObject2 = StructDescriptor.createDescriptor(paramString, this.connection);

			/* 4129 */localObject1 = ((StructDescriptor) localObject2).getOracleTypeADT();

			/* 4131 */break;
		case 2003:
			/* 4136 */localObject2 = ArrayDescriptor.createDescriptor(paramString, this.connection);

			/* 4139 */localObject1 = ((ArrayDescriptor) localObject2).getOracleTypeCOLLECTION();

			/* 4141 */break;
		case 2007:
			/* 4146 */localObject2 = OpaqueDescriptor.createDescriptor(paramString, this.connection);

			/* 4149 */localObject1 = (OracleTypeADT) ((OpaqueDescriptor) localObject2).getPickler();
		case 2004:

		case 2005:
		}

		/* 4155 */this.currentRowBinders[paramInt1] = localBinder;

		/* 4157 */if (this.parameterDatum == null) {
			/* 4158 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4163 */this.parameterDatum[this.currentRank][paramInt1] = null;

		/* 4165 */((OracleTypeADT) localObject1).getTOID();

		/* 4167 */if (this.parameterOtype == null) {
			/* 4168 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4171 */this.parameterOtype[this.currentRank][paramInt1] = localObject1;
	}

	public void setNullAtName(String paramString1, int paramInt, String paramString2) throws SQLException {
		/* 4191 */String str = paramString1.intern();
		/* 4192 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4193 */int i = 0;
		/* 4194 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4196 */for (int k = 0; k < j; k++) {
			/* 4197 */if (arrayOfString[k] != str)
				continue;
			/* 4199 */setNullInternal(k + 1, paramInt, paramString2);

			/* 4201 */i = 1;
		}

		/* 4204 */if (i == 0)
			/* 4205 */DatabaseError.throwSqlException(147, paramString1);
	}

	public synchronized void setNull(int paramInt1, int paramInt2) throws SQLException {
		/* 4227 */setNullCritical(paramInt1, paramInt2);
	}

	void setNullCritical(int paramInt1, int paramInt2) throws SQLException {
		/* 4232 */int i = paramInt1 - 1;

		/* 4234 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 4235 */DatabaseError.throwSqlException(3);
		}
		/* 4237 */Binder localBinder = null;
		/* 4238 */int j = getInternalType(paramInt2);

		/* 4240 */switch (j) {
		case 6:
			/* 4244 */localBinder = this.theVarnumNullBinder;

			/* 4246 */break;
		case 1:

		case 8:

		case 96:

		case 995:
			/* 4255 */localBinder = this.theVarcharNullBinder;
			/* 4256 */this.currentRowCharLens[i] = 1;

			/* 4258 */break;
		case 999:
			/* 4261 */localBinder = this.theFixedCHARNullBinder;

			/* 4263 */break;
		case 12:
			/* 4266 */localBinder = this.theDateNullBinder;

			/* 4268 */break;
		case 180:
			/* 4271 */localBinder = this.connection.v8Compatible ? this.theDateNullBinder : this.theTimestampNullBinder;

			/* 4273 */break;
		case 181:
			/* 4276 */localBinder = this.theTSTZNullBinder;

			/* 4278 */break;
		case 231:
			/* 4281 */localBinder = this.theTSLTZNullBinder;

			/* 4283 */break;
		case 104:
			/* 4286 */localBinder = this.theRowidNullBinder;

			/* 4288 */break;
		case 183:
			/* 4291 */localBinder = this.theIntervalDSNullBinder;

			/* 4293 */break;
		case 182:
			/* 4296 */localBinder = this.theIntervalYMNullBinder;

			/* 4298 */break;
		case 23:

		case 24:
			/* 4303 */localBinder = this.theRawNullBinder;

			/* 4305 */break;
		case 100:
			/* 4308 */localBinder = this.theBinaryFloatNullBinder;

			/* 4310 */break;
		case 101:
			/* 4313 */localBinder = this.theBinaryDoubleNullBinder;

			/* 4315 */break;
		case 113:
			/* 4318 */localBinder = this.theBlobNullBinder;

			/* 4320 */break;
		case 112:
			/* 4323 */localBinder = this.theClobNullBinder;

			/* 4325 */break;
		case 114:
			/* 4328 */localBinder = this.theBfileNullBinder;

			/* 4330 */break;
		case 109:

		case 111:
			/* 4335 */DatabaseError.throwSqlException(4, "sqlType=" + paramInt2);
		case 102:

		case 998:

		default:
			/* 4345 */DatabaseError.throwSqlException(23, "sqlType=" + paramInt2);
		}

		/* 4349 */this.currentRowBinders[i] = localBinder;
	}

	public void setNullAtName(String paramString, int paramInt) throws SQLException {
		/* 4366 */String str = paramString.intern();
		/* 4367 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4368 */int i = 0;
		/* 4369 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4371 */for (int k = 0; k < j; k++) {
			/* 4372 */if (arrayOfString[k] != str)
				continue;
			/* 4374 */setNull(k + 1, paramInt);

			/* 4376 */i = 1;
		}

		/* 4379 */if (i == 0)
			/* 4380 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
		/* 4399 */setBooleanInternal(paramInt, paramBoolean);
	}

	void setBooleanInternal(int paramInt, boolean paramBoolean) throws SQLException {
		/* 4404 */int i = paramInt - 1;

		/* 4406 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4407 */DatabaseError.throwSqlException(3);
		}
		/* 4409 */this.currentRowCharLens[i] = 0;

		/* 4411 */this.currentRowBinders[i] = this.theBooleanBinder;

		/* 4413 */if (this.parameterInt == null) {
			/* 4414 */this.parameterInt = new int[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4418 */this.parameterInt[this.currentRank][i] = (paramBoolean ? 1 : 0);
	}

	public void setBooleanAtName(String paramString, boolean paramBoolean) throws SQLException {
		/* 4435 */String str = paramString.intern();
		/* 4436 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4437 */int i = 0;
		/* 4438 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4440 */for (int k = 0; k < j; k++) {
			/* 4441 */if (arrayOfString[k] != str)
				continue;
			/* 4443 */setBoolean(k + 1, paramBoolean);

			/* 4445 */i = 1;
		}

		/* 4448 */if (i == 0)
			/* 4449 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setByte(int paramInt, byte paramByte) throws SQLException {
		/* 4467 */setByteInternal(paramInt, paramByte);
	}

	void setByteInternal(int paramInt, byte paramByte) throws SQLException {
		/* 4472 */int i = paramInt - 1;

		/* 4474 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4475 */DatabaseError.throwSqlException(3);
		}
		/* 4477 */this.currentRowCharLens[i] = 0;

		/* 4479 */this.currentRowBinders[i] = this.theByteBinder;

		/* 4481 */if (this.parameterInt == null) {
			/* 4482 */this.parameterInt = new int[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4486 */this.parameterInt[this.currentRank][i] = paramByte;
	}

	public void setByteAtName(String paramString, byte paramByte) throws SQLException {
		/* 4503 */String str = paramString.intern();
		/* 4504 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4505 */int i = 0;
		/* 4506 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4508 */for (int k = 0; k < j; k++) {
			/* 4509 */if (arrayOfString[k] != str)
				continue;
			/* 4511 */setByte(k + 1, paramByte);

			/* 4513 */i = 1;
		}

		/* 4516 */if (i == 0)
			/* 4517 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setShort(int paramInt, short paramShort) throws SQLException {
		/* 4536 */setShortInternal(paramInt, paramShort);
	}

	void setShortInternal(int paramInt, short paramShort) throws SQLException {
		/* 4541 */int i = paramInt - 1;

		/* 4543 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4544 */DatabaseError.throwSqlException(3);
		}
		/* 4546 */this.currentRowCharLens[i] = 0;

		/* 4548 */this.currentRowBinders[i] = this.theShortBinder;

		/* 4550 */if (this.parameterInt == null) {
			/* 4551 */this.parameterInt = new int[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4555 */this.parameterInt[this.currentRank][i] = paramShort;
	}

	public void setShortAtName(String paramString, short paramShort) throws SQLException {
		/* 4572 */String str = paramString.intern();
		/* 4573 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4574 */int i = 0;
		/* 4575 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4577 */for (int k = 0; k < j; k++) {
			/* 4578 */if (arrayOfString[k] != str)
				continue;
			/* 4580 */setShort(k + 1, paramShort);

			/* 4582 */i = 1;
		}

		/* 4585 */if (i == 0)
			/* 4586 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setInt(int paramInt1, int paramInt2) throws SQLException {
		/* 4604 */setIntInternal(paramInt1, paramInt2);
	}

	void setIntInternal(int paramInt1, int paramInt2) throws SQLException {
		/* 4609 */int i = paramInt1 - 1;

		/* 4611 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 4612 */DatabaseError.throwSqlException(3);
		}
		/* 4614 */this.currentRowCharLens[i] = 0;

		/* 4616 */this.currentRowBinders[i] = this.theIntBinder;

		/* 4618 */if (this.parameterInt == null) {
			/* 4619 */this.parameterInt = new int[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4623 */this.parameterInt[this.currentRank][i] = paramInt2;
	}

	public void setIntAtName(String paramString, int paramInt) throws SQLException {
		/* 4640 */String str = paramString.intern();
		/* 4641 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4642 */int i = 0;
		/* 4643 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4645 */for (int k = 0; k < j; k++) {
			/* 4646 */if (arrayOfString[k] != str)
				continue;
			/* 4648 */setInt(k + 1, paramInt);

			/* 4650 */i = 1;
		}

		/* 4653 */if (i == 0)
			/* 4654 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setLong(int paramInt, long paramLong) throws SQLException {
		/* 4672 */setLongInternal(paramInt, paramLong);
	}

	void setLongInternal(int paramInt, long paramLong) throws SQLException {
		/* 4677 */int i = paramInt - 1;

		/* 4679 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4680 */DatabaseError.throwSqlException(3);
		}
		/* 4682 */this.currentRowCharLens[i] = 0;

		/* 4684 */this.currentRowBinders[i] = this.theLongBinder;

		/* 4686 */if (this.parameterLong == null) {
			/* 4687 */this.parameterLong = new long[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4692 */this.parameterLong[this.currentRank][i] = paramLong;
	}

	public void setLongAtName(String paramString, long paramLong) throws SQLException {
		/* 4709 */String str = paramString.intern();
		/* 4710 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4711 */int i = 0;
		/* 4712 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4714 */for (int k = 0; k < j; k++) {
			/* 4715 */if (arrayOfString[k] != str)
				continue;
			/* 4717 */setLong(k + 1, paramLong);

			/* 4719 */i = 1;
		}

		/* 4722 */if (i == 0)
			/* 4723 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setFloat(int paramInt, float paramFloat) throws SQLException {
		/* 4743 */setFloatInternal(paramInt, paramFloat);
	}

	void setFloatInternal(int paramInt, float paramFloat) throws SQLException {
		/* 4748 */int i = paramInt - 1;

		/* 4750 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4751 */DatabaseError.throwSqlException(3);
		}
		/* 4753 */if (this.theFloatBinder == null) {
			/* 4755 */this.theFloatBinder = theStaticFloatBinder;

			/* 4757 */Properties localProperties = this.connection.connectionProperties;

			/* 4759 */if (localProperties != null) {
				/* 4761 */String str = localProperties.getProperty("SetFloatAndDoubleUseBinary");

				/* 4763 */if ((str != null) && (str.equalsIgnoreCase("true"))) {
					/* 4764 */this.theFloatBinder = theStaticBinaryFloatBinder;
				}
			}
		}
		/* 4768 */this.currentRowCharLens[i] = 0;

		/* 4770 */this.currentRowBinders[i] = this.theFloatBinder;

		/* 4772 */if (this.theFloatBinder == theStaticFloatBinder) {
			/* 4774 */if (this.parameterDouble == null) {
				/* 4775 */this.parameterDouble = new double[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 4780 */this.parameterDouble[this.currentRank][i] = paramFloat;
		} else {
			/* 4784 */if (this.parameterFloat == null) {
				/* 4785 */this.parameterFloat = new float[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 4790 */this.parameterFloat[this.currentRank][i] = paramFloat;
		}
	}

	public synchronized void setBinaryFloat(int paramInt, float paramFloat) throws SQLException {
		/* 4808 */setBinaryFloatInternal(paramInt, paramFloat);
	}

	void setBinaryFloatInternal(int paramInt, float paramFloat) throws SQLException {
		/* 4813 */int i = paramInt - 1;

		/* 4815 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4816 */DatabaseError.throwSqlException(3);
		}
		/* 4818 */this.currentRowCharLens[i] = 0;

		/* 4820 */this.currentRowBinders[i] = this.theBinaryFloatBinder;

		/* 4822 */if (this.parameterFloat == null) {
			/* 4823 */this.parameterFloat = new float[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4828 */this.parameterFloat[this.currentRank][i] = paramFloat;
	}

	public void setBinaryFloatAtName(String paramString, float paramFloat) throws SQLException {
		/* 4846 */String str = paramString.intern();
		/* 4847 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4848 */int i = 0;
		/* 4849 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4851 */for (int k = 0; k < j; k++) {
			/* 4852 */if (arrayOfString[k] != str)
				continue;
			/* 4854 */setBinaryFloat(k + 1, paramFloat);

			/* 4856 */i = 1;
		}

		/* 4859 */if (i == 0)
			/* 4860 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT) throws SQLException {
		/* 4870 */setBinaryFloatInternal(paramInt, paramBINARY_FLOAT);
	}

	void setBinaryFloatInternal(int paramInt, BINARY_FLOAT paramBINARY_FLOAT) throws SQLException {
		/* 4876 */int i = paramInt - 1;

		/* 4878 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4879 */DatabaseError.throwSqlException(3);
		}
		/* 4881 */if (paramBINARY_FLOAT == null) {
			/* 4883 */this.currentRowBinders[i] = this.theBINARY_FLOATNullBinder;
		} else {
			/* 4887 */this.currentRowBinders[i] = this.theBINARY_FLOATBinder;

			/* 4889 */if (this.parameterDatum == null) {
				/* 4891 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 4896 */this.parameterDatum[this.currentRank][i] = paramBINARY_FLOAT.getBytes();
		}

		/* 4899 */this.currentRowCharLens[i] = 0;
	}

	public void setBinaryFloatAtName(String paramString, BINARY_FLOAT paramBINARY_FLOAT) throws SQLException {
		/* 4918 */String str = paramString.intern();
		/* 4919 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 4920 */int i = 0;
		/* 4921 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 4923 */for (int k = 0; k < j; k++) {
			/* 4924 */if (arrayOfString[k] != str)
				continue;
			/* 4926 */setBinaryFloat(k + 1, paramBINARY_FLOAT);

			/* 4928 */i = 1;
		}

		/* 4931 */if (i == 0)
			/* 4932 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBinaryDouble(int paramInt, double paramDouble) throws SQLException {
		/* 4950 */setBinaryDoubleInternal(paramInt, paramDouble);
	}

	void setBinaryDoubleInternal(int paramInt, double paramDouble) throws SQLException {
		/* 4955 */int i = paramInt - 1;

		/* 4957 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4958 */DatabaseError.throwSqlException(3);
		}
		/* 4960 */this.currentRowBinders[i] = this.theBinaryDoubleBinder;

		/* 4962 */if (this.parameterDouble == null) {
			/* 4963 */this.parameterDouble = new double[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 4966 */this.currentRowCharLens[i] = 0;

		/* 4970 */this.parameterDouble[this.currentRank][i] = paramDouble;
	}

	public synchronized void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE) throws SQLException {
		/* 4989 */setBinaryDoubleInternal(paramInt, paramBINARY_DOUBLE);
	}

	void setBinaryDoubleInternal(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE) throws SQLException {
		/* 4995 */int i = paramInt - 1;

		/* 4997 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 4998 */DatabaseError.throwSqlException(3);
		}
		/* 5000 */if (paramBINARY_DOUBLE == null) {
			/* 5002 */this.currentRowBinders[i] = this.theBINARY_DOUBLENullBinder;
		} else {
			/* 5006 */this.currentRowBinders[i] = this.theBINARY_DOUBLEBinder;

			/* 5008 */if (this.parameterDatum == null) {
				/* 5010 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 5015 */this.parameterDatum[this.currentRank][i] = paramBINARY_DOUBLE.getBytes();
		}

		/* 5018 */this.currentRowCharLens[i] = 0;
	}

	public void setBinaryDoubleAtName(String paramString, double paramDouble) throws SQLException {
		/* 5037 */String str = paramString.intern();
		/* 5038 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5039 */int i = 0;
		/* 5040 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5042 */for (int k = 0; k < j; k++) {
			/* 5043 */if (arrayOfString[k] != str)
				continue;
			/* 5045 */setBinaryDouble(k + 1, paramDouble);

			/* 5047 */i = 1;
		}

		/* 5050 */if (i == 0)
			/* 5051 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setFloatAtName(String paramString, float paramFloat) throws SQLException {
		/* 5069 */String str = paramString.intern();
		/* 5070 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5071 */int i = 0;
		/* 5072 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5074 */for (int k = 0; k < j; k++) {
			/* 5075 */if (arrayOfString[k] != str)
				continue;
			/* 5077 */setFloat(k + 1, paramFloat);

			/* 5079 */i = 1;
		}

		/* 5082 */if (i == 0)
			/* 5083 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setBinaryDoubleAtName(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE) throws SQLException {
		/* 5102 */String str = paramString.intern();
		/* 5103 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5104 */int i = 0;
		/* 5105 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5107 */for (int k = 0; k < j; k++) {
			/* 5108 */if (arrayOfString[k] != str)
				continue;
			/* 5110 */setBinaryDouble(k + 1, paramBINARY_DOUBLE);

			/* 5112 */i = 1;
		}

		/* 5115 */if (i == 0)
			/* 5116 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setDouble(int paramInt, double paramDouble) throws SQLException {
		/* 5134 */setDoubleInternal(paramInt, paramDouble);
	}

	void setDoubleInternal(int paramInt, double paramDouble) throws SQLException {
		/* 5139 */int i = paramInt - 1;

		/* 5141 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 5142 */DatabaseError.throwSqlException(3);
		}
		/* 5144 */if (this.theDoubleBinder == null) {
			/* 5146 */this.theDoubleBinder = theStaticDoubleBinder;

			/* 5148 */Properties localProperties = this.connection.connectionProperties;

			/* 5150 */if (localProperties != null) {
				/* 5152 */String str = localProperties.getProperty("SetFloatAndDoubleUseBinary");

				/* 5154 */if ((str != null) && (str.equalsIgnoreCase("true"))) {
					/* 5155 */this.theDoubleBinder = theStaticBinaryDoubleBinder;
				}
			}
		}
		/* 5159 */this.currentRowCharLens[i] = 0;

		/* 5161 */this.currentRowBinders[i] = this.theDoubleBinder;

		/* 5163 */if (this.parameterDouble == null) {
			/* 5164 */this.parameterDouble = new double[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 5169 */this.parameterDouble[this.currentRank][i] = paramDouble;
	}

	public void setDoubleAtName(String paramString, double paramDouble) throws SQLException {
		/* 5186 */String str = paramString.intern();
		/* 5187 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5188 */int i = 0;
		/* 5189 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5191 */for (int k = 0; k < j; k++) {
			/* 5192 */if (arrayOfString[k] != str)
				continue;
			/* 5194 */setDouble(k + 1, paramDouble);

			/* 5196 */i = 1;
		}

		/* 5199 */if (i == 0)
			/* 5200 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
		/* 5219 */setBigDecimalInternal(paramInt, paramBigDecimal);
	}

	void setBigDecimalInternal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
		/* 5224 */int i = paramInt - 1;

		/* 5226 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 5227 */DatabaseError.throwSqlException(3);
		}
		/* 5229 */if (paramBigDecimal == null) {
			/* 5230 */this.currentRowBinders[i] = this.theVarnumNullBinder;
		} else {
			/* 5233 */this.currentRowBinders[i] = this.theBigDecimalBinder;

			/* 5235 */if (this.parameterBigDecimal == null) {
				/* 5236 */this.parameterBigDecimal = new BigDecimal[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 5241 */this.parameterBigDecimal[this.currentRank][i] = paramBigDecimal;
		}

		/* 5244 */this.currentRowCharLens[i] = 0;
	}

	public void setBigDecimalAtName(String paramString, BigDecimal paramBigDecimal) throws SQLException {
		/* 5263 */String str = paramString.intern();
		/* 5264 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5265 */int i = 0;
		/* 5266 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5268 */for (int k = 0; k < j; k++) {
			/* 5269 */if (arrayOfString[k] != str)
				continue;
			/* 5271 */setBigDecimal(k + 1, paramBigDecimal);

			/* 5273 */i = 1;
		}

		/* 5276 */if (i == 0)
			/* 5277 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setString(int paramInt, String paramString) throws SQLException {
		/* 5298 */setStringInternal(paramInt, paramString);
	}

	void setStringInternal(int paramInt, String paramString) throws SQLException {
		int i = paramInt - 1;
		if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			DatabaseError.throwSqlException(3);
		}
		int j = paramString != null ? paramString.length() : 0;

		if (j == 0) {
			basicBindNullString(paramInt);
		} else {
			int k;
			if (this.currentRowFormOfUse[(paramInt - 1)] == 1) {
				if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
					if ((j > this.maxVcsBytesPlsql) || ((j > this.maxVcsCharsPlsql) && (this.isServerCharSetFixedWidth))) {
						setStringForClobCritical(paramInt, paramString);
					} else if (j > this.maxVcsCharsPlsql) {
						k = this.connection.conversion.encodedByteLength(paramString, false);
						if (k > this.maxVcsBytesPlsql) {
							setStringForClobCritical(paramInt, paramString);
						} else
							basicBindString(paramInt, paramString);
					} else {
						basicBindString(paramInt, paramString);
					}

				} else if (j <= this.maxVcsCharsSql)
					basicBindString(paramInt, paramString);
				else if (j <= this.maxStreamCharsSql)
					basicBindCharacterStream(paramInt, new StringReader(paramString), j);
				else {
					setStringForClobCritical(paramInt, paramString);
				}

			} else if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
				if ((j > this.maxVcsBytesPlsql) || ((j > this.maxVcsNCharsPlsql) && (this.isServerNCharSetFixedWidth))) {
					setStringForClobCritical(paramInt, paramString);
				} else if (j > this.maxVcsNCharsPlsql) {
					k = this.connection.conversion.encodedByteLength(paramString, true);
					if (k > this.maxVcsBytesPlsql) {
						setStringForClobCritical(paramInt, paramString);
					} else
						basicBindString(paramInt, paramString);
				} else {
					/* 5366 */basicBindString(paramInt, paramString);
				}

			}
			/* 5371 */else if (j <= this.maxVcsCharsSql)
				/* 5372 */basicBindString(paramInt, paramString);
			/* 5373 */else if (j <= this.maxStreamNCharsSql) {
				/* 5376 */setStringForClobCritical(paramInt, paramString);
			} else
				/* 5379 */setStringForClobCritical(paramInt, paramString);
		}
	}

	void basicBindNullString(int paramInt) throws SQLException {
		/* 5390 */int i = paramInt - 1;
		/* 5391 */this.currentRowBinders[i] = this.theVarcharNullBinder;

		/* 5393 */if ((this.sqlKind == 1) || (this.sqlKind == 4))
			/* 5394 */this.currentRowCharLens[i] = this.minVcsBindSize;
		else
			/* 5396 */this.currentRowCharLens[i] = 1;
	}

	void basicBindString(int paramInt, String paramString) throws SQLException {
		/* 5403 */int i = paramInt - 1;
		/* 5404 */this.currentRowBinders[i] = this.theStringBinder;
		/* 5405 */int j = paramString.length();

		/* 5407 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 5409 */int k = this.connection.minVcsBindSize;
			/* 5410 */int m = j + 1;

			/* 5412 */this.currentRowCharLens[i] = (m < k ? k : m);
		} else {
			/* 5415 */this.currentRowCharLens[i] = (j + 1);
		}
		/* 5417 */if (this.parameterString == null) {
			/* 5418 */this.parameterString = new String[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 5421 */this.parameterString[this.currentRank][i] = paramString;
	}

	public void setStringAtName(String paramString1, String paramString2) throws SQLException {
		/* 5438 */String str = paramString1.intern();
		/* 5439 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5440 */int i = 0;
		/* 5441 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5443 */for (int k = 0; k < j; k++) {
			/* 5444 */if (arrayOfString[k] != str)
				continue;
			/* 5446 */setString(k + 1, paramString2);

			/* 5448 */i = 1;
		}

		/* 5451 */if (i == 0)
			/* 5452 */DatabaseError.throwSqlException(147, paramString1);
	}

	public void setStringForClob(int paramInt, String paramString) throws SQLException {
		/* 5472 */if (paramString == null) {
			/* 5474 */setNull(paramInt, 1);
			/* 5475 */return;
		}
		/* 5477 */int i = paramString.length();
		/* 5478 */if (i == 0) {
			/* 5480 */setNull(paramInt, 1);
			/* 5481 */return;
		}

		/* 5484 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 5486 */if (i <= this.maxVcsCharsPlsql) {
				/* 5488 */setStringInternal(paramInt, paramString);
			} else {
				/* 5492 */setStringForClobCritical(paramInt, paramString);
			}

		}
		/* 5497 */else if (i <= this.maxVcsCharsSql) {
			/* 5499 */setStringInternal(paramInt, paramString);
		} else {
			/* 5503 */setStringForClobCritical(paramInt, paramString);
		}
	}

	void setStringForClobCritical(int paramInt, String paramString) throws SQLException {
		/* 5510 */synchronized (this.connection) {
			/* 5512 */synchronized (this) {
				/* 5514 */CLOB localCLOB = CLOB.createTemporary(this.connection, true, 10, this.currentRowFormOfUse[(paramInt - 1)]);

				/* 5517 */localCLOB.setString(1L, paramString);
				/* 5518 */addToTempLobsToFree(localCLOB);
				/* 5519 */setCLOBInternal(paramInt, localCLOB);
			}
		}
	}

	void setReaderContentsForClobCritical(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
		/* 5529 */synchronized (this.connection) {
			/* 5531 */synchronized (this) {
				/* 5533 */CLOB localCLOB = CLOB.createTemporary(this.connection, true, 10, this.currentRowFormOfUse[(paramInt1 - 1)]);

				/* 5536 */OracleClobWriter localOracleClobWriter = (OracleClobWriter) localCLOB.setCharacterStream(1L);
				/* 5537 */int i = localCLOB.getBufferSize();
				/* 5538 */char[] arrayOfChar = new char[i];
				/* 5539 */int j = paramInt2;
				try {
					/* 5541 */while (j >= i) {
						/* 5543 */paramReader.read(arrayOfChar);
						/* 5544 */localOracleClobWriter.write(arrayOfChar);
						/* 5545 */j -= i;
					}
					/* 5547 */if (j > 0) {
						/* 5549 */paramReader.read(arrayOfChar, 0, j);
						/* 5550 */localOracleClobWriter.write(arrayOfChar, 0, j);
					}
					/* 5552 */localOracleClobWriter.flush();
				} catch (IOException localIOException) {
				}
				/* 5554 */addToTempLobsToFree(localCLOB);
				/* 5555 */setCLOBInternal(paramInt1, localCLOB);
			}
		}
	}

	void setAsciiStreamContentsForClobCritical(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 5565 */synchronized (this.connection) {
			/* 5567 */synchronized (this) {
				/* 5569 */CLOB localCLOB = CLOB.createTemporary(this.connection, true, 10, this.currentRowFormOfUse[(paramInt1 - 1)]);

				/* 5572 */OracleClobWriter localOracleClobWriter = (OracleClobWriter) localCLOB.setCharacterStream(1L);
				/* 5573 */int i = localCLOB.getBufferSize();
				/* 5574 */byte[] arrayOfByte = new byte[i];
				/* 5575 */char[] arrayOfChar = new char[i];
				/* 5576 */int j = paramInt2;
				try {
					/* 5578 */while (j >= i) {
						/* 5580 */paramInputStream.read(arrayOfByte);
						/* 5581 */DBConversion.asciiBytesToJavaChars(arrayOfByte, i, arrayOfChar);

						/* 5583 */localOracleClobWriter.write(arrayOfChar);
						/* 5584 */j -= i;
					}
					/* 5586 */if (j > 0) {
						/* 5588 */paramInputStream.read(arrayOfByte, 0, j);
						/* 5589 */DBConversion.asciiBytesToJavaChars(arrayOfByte, j, arrayOfChar);

						/* 5591 */localOracleClobWriter.write(arrayOfChar, 0, j);
					}
					/* 5593 */localOracleClobWriter.flush();
				} catch (IOException localIOException) {
				}
				/* 5595 */addToTempLobsToFree(localCLOB);
				/* 5596 */setCLOBInternal(paramInt1, localCLOB);
			}
		}
	}

	public void setStringForClobAtName(String paramString1, String paramString2) throws SQLException {
		/* 5617 */String str = paramString1.intern();
		/* 5618 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5619 */int i = 0;
		/* 5620 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5622 */for (int k = 0; k < j; k++) {
			/* 5623 */if (arrayOfString[k] != str)
				continue;
			/* 5625 */setStringForClob(k + 1, paramString2);

			/* 5627 */i = 1;
		}

		/* 5630 */if (i == 0)
			/* 5631 */DatabaseError.throwSqlException(147, paramString1);
	}

	public synchronized void setFixedCHAR(int paramInt, String paramString) throws SQLException {
		/* 5653 */setFixedCHARInternal(paramInt, paramString);
	}

	void setFixedCHARInternal(int paramInt, String paramString) throws SQLException {
		/* 5658 */int i = paramInt - 1;

		/* 5660 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 5661 */DatabaseError.throwSqlException(3);
		}
		/* 5663 */int j = 0;

		/* 5665 */if (paramString != null) {
			/* 5666 */j = paramString.length();
		}

		/* 5671 */if (j > 32766) {
			/* 5672 */DatabaseError.throwSqlException(157);
		}
		/* 5674 */if (paramString == null) {
			/* 5676 */this.currentRowBinders[i] = this.theFixedCHARNullBinder;
			/* 5677 */this.currentRowCharLens[i] = 1;
		} else {
			/* 5681 */this.currentRowBinders[i] = this.theFixedCHARBinder;
			/* 5682 */this.currentRowCharLens[i] = (j + 1);

			/* 5684 */if (this.parameterString == null) {
				/* 5685 */this.parameterString = new String[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 5688 */this.parameterString[this.currentRank][i] = paramString;
		}
	}

	public void setFixedCHARAtName(String paramString1, String paramString2) throws SQLException {
		/* 5707 */String str = paramString1.intern();
		/* 5708 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5709 */int i = 0;
		/* 5710 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5712 */for (int k = 0; k < j; k++) {
			/* 5713 */if (arrayOfString[k] != str)
				continue;
			/* 5715 */setFixedCHAR(k + 1, paramString2);

			/* 5717 */i = 1;
		}

		/* 5720 */if (i == 0)
			/* 5721 */DatabaseError.throwSqlException(147, paramString1);
	}

	/** @deprecated */
	public synchronized void setCursor(int paramInt, ResultSet paramResultSet) throws SQLException {
		/* 5741 */setCursorInternal(paramInt, paramResultSet);
	}

	void setCursorInternal(int paramInt, ResultSet paramResultSet) throws SQLException {
		/* 5746 */throw DatabaseError.newSqlException(23);
	}

	public void setCursorAtName(String paramString, ResultSet paramResultSet) throws SQLException {
		/* 5765 */String str = paramString.intern();
		/* 5766 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5767 */int i = 0;
		/* 5768 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5770 */for (int k = 0; k < j; k++) {
			/* 5771 */if (arrayOfString[k] != str)
				continue;
			/* 5773 */setCursor(k + 1, paramResultSet);

			/* 5775 */i = 1;
		}

		/* 5778 */if (i == 0)
			/* 5779 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setROWID(int paramInt, ROWID paramROWID) throws SQLException {
		/* 5800 */setROWIDInternal(paramInt, paramROWID);
	}

	void setROWIDInternal(int paramInt, ROWID paramROWID) throws SQLException {
		/* 5805 */int i = paramInt - 1;

		/* 5807 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 5808 */DatabaseError.throwSqlException(3);
		}
		/* 5810 */if (paramROWID == null) {
			/* 5812 */this.currentRowBinders[i] = this.theRowidNullBinder;
		} else {
			/* 5816 */this.currentRowBinders[i] = this.theRowidBinder;

			/* 5818 */if (this.parameterDatum == null) {
				/* 5820 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 5825 */this.parameterDatum[this.currentRank][i] = paramROWID.getBytes();
		}

		/* 5828 */this.currentRowCharLens[i] = 0;
	}

	public void setROWIDAtName(String paramString, ROWID paramROWID) throws SQLException {
		/* 5846 */String str = paramString.intern();
		/* 5847 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5848 */int i = 0;
		/* 5849 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5851 */for (int k = 0; k < j; k++) {
			/* 5852 */if (arrayOfString[k] != str)
				continue;
			/* 5854 */setROWID(k + 1, paramROWID);

			/* 5856 */i = 1;
		}

		/* 5859 */if (i == 0)
			/* 5860 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setArray(int paramInt, Array paramArray) throws SQLException {
		/* 5881 */setARRAYInternal(paramInt, (ARRAY) paramArray);
	}

	void setArrayInternal(int paramInt, Array paramArray) throws SQLException {
		/* 5886 */setARRAYInternal(paramInt, (ARRAY) paramArray);
	}

	public void setArrayAtName(String paramString, Array paramArray) throws SQLException {
		/* 5904 */String str = paramString.intern();
		/* 5905 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 5906 */int i = 0;
		/* 5907 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 5909 */for (int k = 0; k < j; k++) {
			/* 5910 */if (arrayOfString[k] != str)
				continue;
			/* 5912 */setArrayInternal(k + 1, paramArray);

			/* 5914 */i = 1;
		}

		/* 5917 */if (i == 0)
			/* 5918 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setARRAY(int paramInt, ARRAY paramARRAY) throws SQLException {
		/* 5939 */setARRAYInternal(paramInt, paramARRAY);
	}

	void setARRAYInternal(int paramInt, ARRAY paramARRAY) throws SQLException {
		/* 5944 */int i = paramInt - 1;

		/* 5946 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 5947 */DatabaseError.throwSqlException(3);
		}
		/* 5949 */if (paramARRAY == null) {
			/* 5951 */DatabaseError.throwSqlException(68);
		} else {
			/* 5955 */synchronized (this.connection) {
				/* 5957 */synchronized (this) {
					/* 5959 */setArrayCritical(i, paramARRAY);

					/* 5961 */this.currentRowCharLens[i] = 0;
				}
			}
		}
	}

	void setArrayCritical(int paramInt, ARRAY paramARRAY) throws SQLException {
		/* 5977 */ArrayDescriptor localArrayDescriptor = paramARRAY.getDescriptor();

		/* 5979 */if (localArrayDescriptor == null) {
			/* 5984 */DatabaseError.throwSqlException(61);
		}

		/* 5990 */this.currentRowBinders[paramInt] = this.theNamedTypeBinder;

		/* 5992 */if (this.parameterDatum == null) {
			/* 5994 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 5999 */this.parameterDatum[this.currentRank][paramInt] = paramARRAY.toBytes();

		/* 6001 */OracleTypeCOLLECTION localOracleTypeCOLLECTION = localArrayDescriptor.getOracleTypeCOLLECTION();

		/* 6003 */localOracleTypeCOLLECTION.getTOID();

		/* 6005 */if (this.parameterOtype == null) {
			/* 6007 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6011 */this.parameterOtype[this.currentRank][paramInt] = localOracleTypeCOLLECTION;
	}

	public void setARRAYAtName(String paramString, ARRAY paramARRAY) throws SQLException {
		/* 6028 */String str = paramString.intern();
		/* 6029 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6030 */int i = 0;
		/* 6031 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6033 */for (int k = 0; k < j; k++) {
			/* 6034 */if (arrayOfString[k] != str)
				continue;
			/* 6036 */setARRAYInternal(k + 1, paramARRAY);

			/* 6038 */i = 1;
		}

		/* 6041 */if (i == 0)
			/* 6042 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setOPAQUE(int paramInt, OPAQUE paramOPAQUE) throws SQLException {
		/* 6063 */setOPAQUEInternal(paramInt, paramOPAQUE);
	}

	void setOPAQUEInternal(int paramInt, OPAQUE paramOPAQUE) throws SQLException {
		/* 6068 */int i = paramInt - 1;

		/* 6070 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6071 */DatabaseError.throwSqlException(3);
		}
		/* 6073 */if (paramOPAQUE == null) {
			/* 6075 */DatabaseError.throwSqlException(68);
		} else {
			/* 6079 */synchronized (this.connection) {
				/* 6081 */synchronized (this) {
					/* 6083 */setOPAQUECritical(i, paramOPAQUE);

					/* 6085 */this.currentRowCharLens[i] = 0;
				}
			}
		}
	}

	void setOPAQUECritical(int paramInt, OPAQUE paramOPAQUE) throws SQLException {
		/* 6101 */OpaqueDescriptor localOpaqueDescriptor = paramOPAQUE.getDescriptor();

		/* 6103 */if (localOpaqueDescriptor == null) {
			/* 6108 */DatabaseError.throwSqlException(61);
		}

		/* 6114 */this.currentRowBinders[paramInt] = this.theNamedTypeBinder;

		/* 6116 */if (this.parameterDatum == null) {
			/* 6118 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6123 */this.parameterDatum[this.currentRank][paramInt] = paramOPAQUE.toBytes();

		/* 6125 */OracleTypeADT localOracleTypeADT = (OracleTypeADT) localOpaqueDescriptor.getPickler();

		/* 6127 */localOracleTypeADT.getTOID();

		/* 6129 */if (this.parameterOtype == null) {
			/* 6131 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6135 */this.parameterOtype[this.currentRank][paramInt] = localOracleTypeADT;
	}

	public void setOPAQUEAtName(String paramString, OPAQUE paramOPAQUE) throws SQLException {
		/* 6152 */String str = paramString.intern();
		/* 6153 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6154 */int i = 0;
		/* 6155 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6157 */for (int k = 0; k < j; k++) {
			/* 6158 */if (arrayOfString[k] != str)
				continue;
			/* 6160 */setOPAQUEInternal(k + 1, paramOPAQUE);

			/* 6162 */i = 1;
		}

		/* 6165 */if (i == 0)
			/* 6166 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor) throws SQLException {
		/* 6187 */setStructDescriptorInternal(paramInt, paramStructDescriptor);
	}

	void setStructDescriptorInternal(int paramInt, StructDescriptor paramStructDescriptor) throws SQLException {
		/* 6193 */int i = paramInt - 1;

		/* 6195 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6196 */DatabaseError.throwSqlException(3);
		}
		/* 6198 */if (paramStructDescriptor == null) {
			/* 6199 */DatabaseError.throwSqlException(68);
		}
		/* 6201 */synchronized (this.connection) {
			/* 6203 */synchronized (this) {
				/* 6205 */setStructDescriptorCritical(i, paramStructDescriptor);

				/* 6207 */this.currentRowCharLens[i] = 0;
			}
		}
	}

	void setStructDescriptorCritical(int paramInt, StructDescriptor paramStructDescriptor) throws SQLException {
		/* 6222 */this.currentRowBinders[paramInt] = this.theNamedTypeBinder;

		/* 6224 */if (this.parameterDatum == null) {
			/* 6225 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6228 */OracleTypeADT localOracleTypeADT = paramStructDescriptor.getOracleTypeADT();

		/* 6230 */localOracleTypeADT.getTOID();

		/* 6232 */if (this.parameterOtype == null) {
			/* 6233 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6237 */this.parameterOtype[this.currentRank][paramInt] = localOracleTypeADT;
	}

	public void setStructDescriptorAtName(String paramString, StructDescriptor paramStructDescriptor) throws SQLException {
		/* 6257 */String str = paramString.intern();
		/* 6258 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6259 */int i = 0;
		/* 6260 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6262 */for (int k = 0; k < j; k++) {
			/* 6263 */if (arrayOfString[k] != str)
				continue;
			/* 6265 */setStructDescriptorInternal(k + 1, paramStructDescriptor);

			/* 6267 */i = 1;
		}

		/* 6270 */if (i == 0)
			/* 6271 */DatabaseError.throwSqlException(147, paramString);
	}

	synchronized void setPreBindsCompelete() throws SQLException {
	}

	public void setSTRUCT(int paramInt, STRUCT paramSTRUCT) throws SQLException {
		/* 6299 */setSTRUCTInternal(paramInt, paramSTRUCT);
	}

	void setSTRUCTInternal(int paramInt, STRUCT paramSTRUCT) throws SQLException {
		/* 6304 */int i = paramInt - 1;

		/* 6306 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6307 */DatabaseError.throwSqlException(3);
		}
		/* 6309 */if (paramSTRUCT == null) {
			/* 6311 */DatabaseError.throwSqlException(68);
		} else {
			/* 6315 */synchronized (this.connection) {
				/* 6317 */synchronized (this) {
					/* 6319 */setSTRUCTCritical(i, paramSTRUCT);

					/* 6321 */this.currentRowCharLens[i] = 0;
				}
			}
		}
	}

	void setSTRUCTCritical(int paramInt, STRUCT paramSTRUCT) throws SQLException {
		/* 6338 */StructDescriptor localStructDescriptor = paramSTRUCT.getDescriptor();

		/* 6340 */if (localStructDescriptor == null) {
			/* 6345 */DatabaseError.throwSqlException(61);
		}

		/* 6351 */this.currentRowBinders[paramInt] = this.theNamedTypeBinder;

		/* 6353 */if (this.parameterDatum == null) {
			/* 6355 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6360 */this.parameterDatum[this.currentRank][paramInt] = paramSTRUCT.toBytes();

		/* 6365 */OracleTypeADT localOracleTypeADT = localStructDescriptor.getOracleTypeADT();

		/* 6367 */localOracleTypeADT.getTOID();

		/* 6369 */if (this.parameterOtype == null) {
			/* 6371 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 6375 */this.parameterOtype[this.currentRank][paramInt] = localOracleTypeADT;
	}

	public void setSTRUCTAtName(String paramString, STRUCT paramSTRUCT) throws SQLException {
		/* 6393 */String str = paramString.intern();
		/* 6394 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6395 */int i = 0;
		/* 6396 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6398 */for (int k = 0; k < j; k++) {
			/* 6399 */if (arrayOfString[k] != str)
				continue;
			/* 6401 */setSTRUCTInternal(k + 1, paramSTRUCT);

			/* 6403 */i = 1;
		}

		/* 6406 */if (i == 0)
			/* 6407 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setRAW(int paramInt, RAW paramRAW) throws SQLException {
		/* 6427 */setRAWInternal(paramInt, paramRAW);
	}

	void setRAWInternal(int paramInt, RAW paramRAW) throws SQLException {
		/* 6432 */int i = paramInt - 1;

		/* 6434 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6435 */DatabaseError.throwSqlException(3);
		}
		/* 6437 */this.currentRowCharLens[i] = 0;

		/* 6439 */if (paramRAW == null)
			/* 6440 */this.currentRowBinders[i] = this.theRawNullBinder;
		else
			/* 6442 */setBytesInternal(paramInt, paramRAW.getBytes());
	}

	public void setRAWAtName(String paramString, RAW paramRAW) throws SQLException {
		/* 6459 */String str = paramString.intern();
		/* 6460 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6461 */int i = 0;
		/* 6462 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6464 */for (int k = 0; k < j; k++) {
			/* 6465 */if (arrayOfString[k] != str)
				continue;
			/* 6467 */setRAW(k + 1, paramRAW);

			/* 6469 */i = 1;
		}

		/* 6472 */if (i == 0)
			/* 6473 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setCHAR(int paramInt, CHAR paramCHAR) throws SQLException {
		/* 6490 */setCHARInternal(paramInt, paramCHAR);
	}

	void setCHARInternal(int paramInt, CHAR paramCHAR) throws SQLException {
		/* 6495 */int i = paramInt - 1;

		/* 6497 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6498 */DatabaseError.throwSqlException(3);
		}
		/* 6500 */if ((paramCHAR == null) || (paramCHAR.shareBytes().length == 0)) {
			/* 6506 */this.currentRowBinders[i] = this.theSetCHARNullBinder;
			/* 6507 */this.currentRowCharLens[i] = 1;
		} else {
			/* 6511 */int j = (short) paramCHAR.oracleId();

			/* 6516 */this.currentRowBinders[i] = this.theSetCHARBinder;

			/* 6518 */if (this.parameterDatum == null) {
				/* 6520 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 6524 */CharacterSet localCharacterSet = this.currentRowFormOfUse[i] == 2 ? this.connection.setCHARNCharSetObj : this.connection.setCHARCharSetObj;
			byte[] arrayOfByte1;
			/* 6531 */if ((localCharacterSet != null) && (localCharacterSet.getOracleId() != j)) {
				/* 6533 */byte[] arrayOfByte2 = paramCHAR.shareBytes();

				/* 6535 */arrayOfByte1 = localCharacterSet.convert(paramCHAR.getCharacterSet(), arrayOfByte2, 0, arrayOfByte2.length);
			} else {
				/* 6538 */arrayOfByte1 = paramCHAR.getBytes();
			}
			/* 6540 */this.parameterDatum[this.currentRank][i] = arrayOfByte1;
			/* 6541 */this.currentRowCharLens[i] = ((arrayOfByte1.length + 1 >> 1) + 1);
		}

		/* 6544 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 6548 */if (this.currentRowCharLens[i] < this.minVcsBindSize)
				/* 6549 */this.currentRowCharLens[i] = this.minVcsBindSize;
		}
	}

	public void setCHARAtName(String paramString, CHAR paramCHAR) throws SQLException {
		/* 6568 */String str = paramString.intern();
		/* 6569 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6570 */int i = 0;
		/* 6571 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6573 */for (int k = 0; k < j; k++) {
			/* 6574 */if (arrayOfString[k] != str)
				continue;
			/* 6576 */setCHAR(k + 1, paramCHAR);

			/* 6578 */i = 1;
		}

		/* 6581 */if (i == 0)
			/* 6582 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setDATE(int paramInt, DATE paramDATE) throws SQLException {
		/* 6603 */setDATEInternal(paramInt, paramDATE);
	}

	void setDATEInternal(int paramInt, DATE paramDATE) throws SQLException {
		/* 6608 */int i = paramInt - 1;

		/* 6610 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6611 */DatabaseError.throwSqlException(3);
		}
		/* 6613 */this.currentRowCharLens[i] = 0;

		/* 6615 */if (paramDATE == null) {
			/* 6617 */this.currentRowBinders[i] = this.theDateNullBinder;
		} else {
			/* 6621 */this.currentRowBinders[i] = this.theOracleDateBinder;

			/* 6623 */if (this.parameterDatum == null) {
				/* 6625 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 6630 */this.parameterDatum[this.currentRank][i] = paramDATE.getBytes();
		}
	}

	public void setDATEAtName(String paramString, DATE paramDATE) throws SQLException {
		/* 6648 */String str = paramString.intern();
		/* 6649 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6650 */int i = 0;
		/* 6651 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6653 */for (int k = 0; k < j; k++) {
			/* 6654 */if (arrayOfString[k] != str)
				continue;
			/* 6656 */setDATE(k + 1, paramDATE);

			/* 6658 */i = 1;
		}

		/* 6661 */if (i == 0)
			/* 6662 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setNUMBER(int paramInt, NUMBER paramNUMBER) throws SQLException {
		/* 6683 */setNUMBERInternal(paramInt, paramNUMBER);
	}

	void setNUMBERInternal(int paramInt, NUMBER paramNUMBER) throws SQLException {
		/* 6688 */int i = paramInt - 1;

		/* 6690 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6691 */DatabaseError.throwSqlException(3);
		}
		/* 6693 */this.currentRowCharLens[i] = 0;

		/* 6695 */if (paramNUMBER == null) {
			/* 6697 */this.currentRowBinders[i] = this.theVarnumNullBinder;
		} else {
			/* 6701 */this.currentRowBinders[i] = this.theOracleNumberBinder;

			/* 6703 */if (this.parameterDatum == null) {
				/* 6705 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 6710 */this.parameterDatum[this.currentRank][i] = paramNUMBER.getBytes();
		}
	}

	public void setNUMBERAtName(String paramString, NUMBER paramNUMBER) throws SQLException {
		/* 6728 */String str = paramString.intern();
		/* 6729 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6730 */int i = 0;
		/* 6731 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6733 */for (int k = 0; k < j; k++) {
			/* 6734 */if (arrayOfString[k] != str)
				continue;
			/* 6736 */setNUMBER(k + 1, paramNUMBER);

			/* 6738 */i = 1;
		}

		/* 6741 */if (i == 0)
			/* 6742 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBLOB(int paramInt, BLOB paramBLOB) throws SQLException {
		/* 6763 */setBLOBInternal(paramInt, paramBLOB);
	}

	void setBLOBInternal(int paramInt, BLOB paramBLOB) throws SQLException {
		/* 6768 */int i = paramInt - 1;

		/* 6770 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6771 */DatabaseError.throwSqlException(3);
		}
		/* 6773 */this.currentRowCharLens[i] = 0;

		/* 6775 */if (paramBLOB == null) {
			/* 6776 */this.currentRowBinders[i] = this.theBlobNullBinder;
		} else {
			/* 6779 */this.currentRowBinders[i] = this.theBlobBinder;

			/* 6781 */if (this.parameterDatum == null) {
				/* 6782 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 6787 */this.parameterDatum[this.currentRank][i] = paramBLOB.getBytes();
		}
	}

	public void setBLOBAtName(String paramString, BLOB paramBLOB) throws SQLException {
		/* 6805 */String str = paramString.intern();
		/* 6806 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6807 */int i = 0;
		/* 6808 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6810 */for (int k = 0; k < j; k++) {
			/* 6811 */if (arrayOfString[k] != str)
				continue;
			/* 6813 */setBLOB(k + 1, paramBLOB);

			/* 6815 */i = 1;
		}

		/* 6818 */if (i == 0)
			/* 6819 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBlob(int paramInt, Blob paramBlob) throws SQLException {
		/* 6836 */setBLOBInternal(paramInt, (BLOB) paramBlob);
	}

	void setBlobInternal(int paramInt, Blob paramBlob) throws SQLException {
		/* 6841 */setBLOBInternal(paramInt, (BLOB) paramBlob);
	}

	public void setBlobAtName(String paramString, Blob paramBlob) throws SQLException {
		/* 6859 */String str = paramString.intern();
		/* 6860 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6861 */int i = 0;
		/* 6862 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6864 */for (int k = 0; k < j; k++) {
			/* 6865 */if (arrayOfString[k] != str)
				continue;
			/* 6867 */setBlob(k + 1, paramBlob);

			/* 6869 */i = 1;
		}

		/* 6872 */if (i == 0)
			/* 6873 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setCLOB(int paramInt, CLOB paramCLOB) throws SQLException {
		/* 6894 */setCLOBInternal(paramInt, paramCLOB);
	}

	void setCLOBInternal(int paramInt, CLOB paramCLOB) throws SQLException {
		/* 6899 */int i = paramInt - 1;

		/* 6901 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 6902 */DatabaseError.throwSqlException(3);
		}
		/* 6904 */this.currentRowCharLens[i] = 0;

		/* 6906 */if (paramCLOB == null) {
			/* 6907 */this.currentRowBinders[i] = this.theClobNullBinder;
		} else {
			/* 6910 */this.currentRowBinders[i] = this.theClobBinder;

			/* 6912 */if (this.parameterDatum == null) {
				/* 6913 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 6918 */this.parameterDatum[this.currentRank][i] = paramCLOB.getBytes();
		}
	}

	public void setCLOBAtName(String paramString, CLOB paramCLOB) throws SQLException {
		/* 6936 */String str = paramString.intern();
		/* 6937 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6938 */int i = 0;
		/* 6939 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6941 */for (int k = 0; k < j; k++) {
			/* 6942 */if (arrayOfString[k] != str)
				continue;
			/* 6944 */setCLOB(k + 1, paramCLOB);

			/* 6946 */i = 1;
		}

		/* 6949 */if (i == 0)
			/* 6950 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setClob(int paramInt, Clob paramClob) throws SQLException {
		/* 6967 */setCLOBInternal(paramInt, (CLOB) paramClob);
	}

	void setClobInternal(int paramInt, Clob paramClob) throws SQLException {
		/* 6972 */setCLOBInternal(paramInt, (CLOB) paramClob);
	}

	public void setClobAtName(String paramString, Clob paramClob) throws SQLException {
		/* 6989 */String str = paramString.intern();
		/* 6990 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 6991 */int i = 0;
		/* 6992 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 6994 */for (int k = 0; k < j; k++) {
			/* 6995 */if (arrayOfString[k] != str)
				continue;
			/* 6997 */setClob(k + 1, paramClob);

			/* 6999 */i = 1;
		}

		/* 7002 */if (i == 0)
			/* 7003 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBFILE(int paramInt, BFILE paramBFILE) throws SQLException {
		/* 7024 */setBFILEInternal(paramInt, paramBFILE);
	}

	void setBFILEInternal(int paramInt, BFILE paramBFILE) throws SQLException {
		/* 7029 */int i = paramInt - 1;

		/* 7031 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7032 */DatabaseError.throwSqlException(3);
		}
		/* 7034 */this.currentRowCharLens[i] = 0;

		/* 7036 */if (paramBFILE == null) {
			/* 7037 */this.currentRowBinders[i] = this.theBfileNullBinder;
		} else {
			/* 7040 */this.currentRowBinders[i] = this.theBfileBinder;

			/* 7042 */if (this.parameterDatum == null) {
				/* 7043 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7048 */this.parameterDatum[this.currentRank][i] = paramBFILE.getBytes();
		}
	}

	public void setBFILEAtName(String paramString, BFILE paramBFILE) throws SQLException {
		/* 7066 */String str = paramString.intern();
		/* 7067 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7068 */int i = 0;
		/* 7069 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7071 */for (int k = 0; k < j; k++) {
			/* 7072 */if (arrayOfString[k] != str)
				continue;
			/* 7074 */setBFILE(k + 1, paramBFILE);

			/* 7076 */i = 1;
		}

		/* 7079 */if (i == 0)
			/* 7080 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setBfile(int paramInt, BFILE paramBFILE) throws SQLException {
		/* 7097 */setBFILEInternal(paramInt, paramBFILE);
	}

	void setBfileInternal(int paramInt, BFILE paramBFILE) throws SQLException {
		/* 7102 */setBFILEInternal(paramInt, paramBFILE);
	}

	public void setBfileAtName(String paramString, BFILE paramBFILE) throws SQLException {
		/* 7114 */setBFILEAtName(paramString, paramBFILE);
	}

	public synchronized void setBytes(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7132 */setBytesInternal(paramInt, paramArrayOfByte);
	}

	void setBytesInternal(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7137 */int i = paramInt - 1;

		/* 7139 */if ((i < 0) || (paramInt > this.numberOfBindPositions))
			/* 7140 */DatabaseError.throwSqlException(3);
		/* 7141 */int j = paramArrayOfByte != null ? paramArrayOfByte.length : 0;
		/* 7142 */if (j == 0) {
			/* 7144 */setNullInternal(paramInt, -2);
		}
		/* 7148 */else if (this.sqlKind == 1) {
			/* 7150 */if (j > this.maxRawBytesPlsql)
				/* 7151 */setBytesForBlobCritical(paramInt, paramArrayOfByte);
			else
				/* 7153 */basicBindBytes(paramInt, paramArrayOfByte);
		}
		/* 7155 */else if (this.sqlKind == 4) {
			/* 7157 */if (j > this.maxRawBytesPlsql)
				/* 7158 */setBytesForBlobCritical(paramInt, paramArrayOfByte);
			else {
				/* 7160 */basicBindBytes(paramInt, paramArrayOfByte);
			}

		}
		/* 7164 */else if (j > this.maxRawBytesSql) {
			/* 7166 */bindBytesAsStream(paramInt, paramArrayOfByte);
		} else
			/* 7169 */basicBindBytes(paramInt, paramArrayOfByte);
	}

	void bindBytesAsStream(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7176 */int i = paramArrayOfByte.length;
		/* 7177 */byte[] arrayOfByte = new byte[i];
		/* 7178 */System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);
		/* 7179 */basicBindBinaryStream(paramInt, new ByteArrayInputStream(arrayOfByte), i);
	}

	void basicBindBytes(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7185 */int i = paramInt - 1;
		/* 7186 */Binder localBinder = (this.sqlKind == 1) || (this.sqlKind == 4) ? this.thePlsqlRawBinder : this.theRawBinder;

		/* 7188 */this.currentRowBinders[i] = localBinder;

		/* 7190 */if (this.parameterDatum == null) {
			/* 7191 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 7194 */this.parameterDatum[this.currentRank][i] = paramArrayOfByte;
		/* 7195 */this.currentRowCharLens[i] = 0;
	}

	void basicBindBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 7202 */int i = paramInt1 - 1;
		/* 7203 */this.currentRowBinders[i] = this.theLongRawStreamBinder;

		/* 7205 */if (this.parameterStream == null) {
			/* 7206 */this.parameterStream = new InputStream[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 7209 */this.parameterStream[this.currentRank][i] = this.connection.conversion.ConvertStream(paramInputStream, 6, paramInt2);

		/* 7212 */this.currentRowCharLens[i] = 0;
	}

	public void setBytesAtName(String paramString, byte[] paramArrayOfByte) throws SQLException {
		/* 7230 */String str = paramString.intern();
		/* 7231 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7232 */int i = 0;
		/* 7233 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7235 */for (int k = 0; k < j; k++) {
			/* 7236 */if (arrayOfString[k] != str)
				continue;
			/* 7238 */setBytes(k + 1, paramArrayOfByte);

			/* 7240 */i = 1;
		}

		/* 7243 */if (i == 0)
			/* 7244 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setBytesForBlob(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7263 */if (paramArrayOfByte == null) {
			/* 7265 */setNull(paramInt, -2);
			/* 7266 */return;
		}
		/* 7268 */int i = paramArrayOfByte.length;
		/* 7269 */if (i == 0) {
			/* 7271 */setNull(paramInt, -2);
			/* 7272 */return;
		}
		/* 7274 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 7276 */if (i <= this.maxRawBytesPlsql) {
				/* 7278 */setBytes(paramInt, paramArrayOfByte);
			} else {
				/* 7282 */setBytesForBlobCritical(paramInt, paramArrayOfByte);
			}

		}
		/* 7287 */else if (i <= this.maxRawBytesSql) {
			/* 7289 */setBytes(paramInt, paramArrayOfByte);
		} else {
			/* 7298 */setBytesForBlobCritical(paramInt, paramArrayOfByte);
		}
	}

	void setBytesForBlobCritical(int paramInt, byte[] paramArrayOfByte) throws SQLException {
		/* 7307 */synchronized (this.connection) {
			/* 7309 */synchronized (this) {
				/* 7311 */BLOB localBLOB = BLOB.createTemporary(this.connection, true, 10);

				/* 7313 */localBLOB.putBytes(1L, paramArrayOfByte);
				/* 7314 */addToTempLobsToFree(localBLOB);
				/* 7315 */setBLOBInternal(paramInt, localBLOB);
			}
		}
	}

	void setBinaryStreamContentsForBlobCritical(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 7325 */synchronized (this.connection) {
			/* 7327 */synchronized (this) {
				/* 7329 */BLOB localBLOB = BLOB.createTemporary(this.connection, true, 10);

				/* 7332 */OracleBlobOutputStream localOracleBlobOutputStream = (OracleBlobOutputStream) localBLOB.setBinaryStream(1L);
				/* 7333 */int i = localBLOB.getBufferSize();
				/* 7334 */byte[] arrayOfByte = new byte[i];
				/* 7335 */int j = paramInt2;
				try {
					/* 7337 */while (j >= i) {
						/* 7339 */paramInputStream.read(arrayOfByte);
						/* 7340 */localOracleBlobOutputStream.write(arrayOfByte);
						/* 7341 */j -= i;
					}
					/* 7343 */if (j > 0) {
						/* 7345 */paramInputStream.read(arrayOfByte, 0, j);
						/* 7346 */localOracleBlobOutputStream.write(arrayOfByte, 0, j);
					}
					/* 7348 */localOracleBlobOutputStream.flush();
				} catch (IOException localIOException) {
				}
				/* 7350 */addToTempLobsToFree(localBLOB);
				/* 7351 */setBLOBInternal(paramInt1, localBLOB);
			}
		}
	}

	public void setBytesForBlobAtName(String paramString, byte[] paramArrayOfByte) throws SQLException {
		/* 7372 */String str = paramString.intern();
		/* 7373 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7374 */int i = 0;
		/* 7375 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7377 */for (int k = 0; k < j; k++) {
			/* 7378 */if (arrayOfString[k] != str)
				continue;
			/* 7380 */setBytesForBlob(k + 1, paramArrayOfByte);

			/* 7382 */i = 1;
		}

		/* 7385 */if (i == 0)
			/* 7386 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setInternalBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2) throws SQLException {
		/* 7403 */setInternalBytesInternal(paramInt1, paramArrayOfByte, paramInt2);
	}

	void setInternalBytesInternal(int paramInt1, byte[] paramArrayOfByte, int paramInt2) throws SQLException {
		/* 7411 */DatabaseError.throwSqlException(23);
	}

	public synchronized void setDate(int paramInt, Date paramDate) throws SQLException {
		/* 7428 */setDateInternal(paramInt, paramDate);
	}

	void setDateInternal(int paramInt, Date paramDate) throws SQLException {
		/* 7433 */int i = paramInt - 1;

		/* 7435 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7436 */DatabaseError.throwSqlException(3);
		}
		/* 7438 */if (paramDate == null) {
			/* 7439 */this.currentRowBinders[i] = this.theDateNullBinder;
		} else {
			/* 7442 */this.currentRowBinders[i] = this.theDateBinder;

			/* 7444 */if (this.parameterDate == null) {
				/* 7445 */this.parameterDate = new Date[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7450 */this.parameterDate[this.currentRank][i] = paramDate;
		}

		/* 7453 */this.currentRowCharLens[i] = 0;
	}

	public void setDateAtName(String paramString, Date paramDate) throws SQLException {
		/* 7471 */String str = paramString.intern();
		/* 7472 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7473 */int i = 0;
		/* 7474 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7476 */for (int k = 0; k < j; k++) {
			/* 7477 */if (arrayOfString[k] != str)
				continue;
			/* 7479 */setDate(k + 1, paramDate);

			/* 7481 */i = 1;
		}

		/* 7484 */if (i == 0)
			/* 7485 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setTime(int paramInt, Time paramTime) throws SQLException {
		/* 7504 */setTimeInternal(paramInt, paramTime);
	}

	void setTimeInternal(int paramInt, Time paramTime) throws SQLException {
		/* 7509 */int i = paramInt - 1;

		/* 7511 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7512 */DatabaseError.throwSqlException(3);
		}
		/* 7514 */if (paramTime == null) {
			/* 7515 */this.currentRowBinders[i] = this.theDateNullBinder;
		} else {
			/* 7518 */this.currentRowBinders[i] = this.theTimeBinder;

			/* 7520 */if (this.parameterTime == null) {
				/* 7521 */this.parameterTime = new Time[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7526 */this.parameterTime[this.currentRank][i] = paramTime;
		}

		/* 7529 */this.currentRowCharLens[i] = 0;
	}

	public void setTimeAtName(String paramString, Time paramTime) throws SQLException {
		/* 7547 */String str = paramString.intern();
		/* 7548 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7549 */int i = 0;
		/* 7550 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7552 */for (int k = 0; k < j; k++) {
			/* 7553 */if (arrayOfString[k] != str)
				continue;
			/* 7555 */setTime(k + 1, paramTime);

			/* 7557 */i = 1;
		}

		/* 7560 */if (i == 0)
			/* 7561 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
		/* 7580 */setTimestampInternal(paramInt, paramTimestamp);
	}

	void setTimestampInternal(int paramInt, Timestamp paramTimestamp) throws SQLException {
		/* 7586 */if (this.connection.v8Compatible) {
			/* 7590 */if (paramTimestamp == null) {
				/* 7592 */setDATEInternal(paramInt, null);
			} else {
				/* 7596 */DATE localDATE = new DATE(paramTimestamp);

				/* 7598 */setDATEInternal(paramInt, localDATE);
			}
		} else {
			/* 7603 */int i = paramInt - 1;

			/* 7605 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
				/* 7606 */DatabaseError.throwSqlException(3);
			}

			/* 7609 */if (paramTimestamp == null) {
				/* 7610 */this.currentRowBinders[i] = this.theTimestampNullBinder;
			} else {
				/* 7613 */this.currentRowBinders[i] = this.theTimestampBinder;

				/* 7615 */if (this.parameterTimestamp == null) {
					/* 7616 */this.parameterTimestamp = new Timestamp[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
				}

				/* 7621 */this.parameterTimestamp[this.currentRank][i] = paramTimestamp;
			}

			/* 7624 */this.currentRowCharLens[i] = 0;
		}
	}

	public void setTimestampAtName(String paramString, Timestamp paramTimestamp) throws SQLException {
		/* 7644 */String str = paramString.intern();
		/* 7645 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7646 */int i = 0;
		/* 7647 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7649 */for (int k = 0; k < j; k++) {
			/* 7650 */if (arrayOfString[k] != str)
				continue;
			/* 7652 */setTimestamp(k + 1, paramTimestamp);

			/* 7654 */i = 1;
		}

		/* 7657 */if (i == 0)
			/* 7658 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM) throws SQLException {
		/* 7683 */setINTERVALYMInternal(paramInt, paramINTERVALYM);
	}

	void setINTERVALYMInternal(int paramInt, INTERVALYM paramINTERVALYM) throws SQLException {
		/* 7688 */int i = paramInt - 1;

		/* 7690 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7691 */DatabaseError.throwSqlException(3);
		}
		/* 7693 */if (paramINTERVALYM == null) {
			/* 7695 */this.currentRowBinders[i] = this.theIntervalYMNullBinder;
		} else {
			/* 7699 */this.currentRowBinders[i] = this.theIntervalYMBinder;

			/* 7701 */if (this.parameterDatum == null) {
				/* 7703 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7708 */this.parameterDatum[this.currentRank][i] = paramINTERVALYM.getBytes();
		}

		/* 7711 */this.currentRowCharLens[i] = 0;
	}

	public void setINTERVALYMAtName(String paramString, INTERVALYM paramINTERVALYM) throws SQLException {
		/* 7730 */String str = paramString.intern();
		/* 7731 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7732 */int i = 0;
		/* 7733 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7735 */for (int k = 0; k < j; k++) {
			/* 7736 */if (arrayOfString[k] != str)
				continue;
			/* 7738 */setINTERVALYM(k + 1, paramINTERVALYM);

			/* 7740 */i = 1;
		}

		/* 7743 */if (i == 0)
			/* 7744 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS) throws SQLException {
		/* 7769 */setINTERVALDSInternal(paramInt, paramINTERVALDS);
	}

	void setINTERVALDSInternal(int paramInt, INTERVALDS paramINTERVALDS) throws SQLException {
		/* 7774 */int i = paramInt - 1;

		/* 7776 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7777 */DatabaseError.throwSqlException(3);
		}
		/* 7779 */if (paramINTERVALDS == null) {
			/* 7781 */this.currentRowBinders[i] = this.theIntervalDSNullBinder;
		} else {
			/* 7785 */this.currentRowBinders[i] = this.theIntervalDSBinder;

			/* 7787 */if (this.parameterDatum == null) {
				/* 7789 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7794 */this.parameterDatum[this.currentRank][i] = paramINTERVALDS.getBytes();
		}

		/* 7797 */this.currentRowCharLens[i] = 0;
	}

	public void setINTERVALDSAtName(String paramString, INTERVALDS paramINTERVALDS) throws SQLException {
		/* 7816 */String str = paramString.intern();
		/* 7817 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7818 */int i = 0;
		/* 7819 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7821 */for (int k = 0; k < j; k++) {
			/* 7822 */if (arrayOfString[k] != str)
				continue;
			/* 7824 */setINTERVALDS(k + 1, paramINTERVALDS);

			/* 7826 */i = 1;
		}

		/* 7829 */if (i == 0)
			/* 7830 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP) throws SQLException {
		/* 7854 */setTIMESTAMPInternal(paramInt, paramTIMESTAMP);
	}

	void setTIMESTAMPInternal(int paramInt, TIMESTAMP paramTIMESTAMP) throws SQLException {
		/* 7859 */int i = paramInt - 1;

		/* 7861 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7862 */DatabaseError.throwSqlException(3);
		}
		/* 7864 */if (paramTIMESTAMP == null) {
			/* 7866 */this.currentRowBinders[i] = this.theTimestampNullBinder;
		} else {
			/* 7870 */this.currentRowBinders[i] = this.theOracleTimestampBinder;

			/* 7872 */if (this.parameterDatum == null) {
				/* 7874 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7879 */this.parameterDatum[this.currentRank][i] = paramTIMESTAMP.getBytes();
		}

		/* 7882 */this.currentRowCharLens[i] = 0;
	}

	public void setTIMESTAMPAtName(String paramString, TIMESTAMP paramTIMESTAMP) throws SQLException {
		/* 7901 */String str = paramString.intern();
		/* 7902 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7903 */int i = 0;
		/* 7904 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7906 */for (int k = 0; k < j; k++) {
			/* 7907 */if (arrayOfString[k] != str)
				continue;
			/* 7909 */setTIMESTAMP(k + 1, paramTIMESTAMP);

			/* 7911 */i = 1;
		}

		/* 7914 */if (i == 0)
			/* 7915 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ) throws SQLException {
		/* 7940 */setTIMESTAMPTZInternal(paramInt, paramTIMESTAMPTZ);
	}

	void setTIMESTAMPTZInternal(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ) throws SQLException {
		/* 7946 */int i = paramInt - 1;

		/* 7948 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 7949 */DatabaseError.throwSqlException(3);
		}
		/* 7951 */if (paramTIMESTAMPTZ == null) {
			/* 7953 */this.currentRowBinders[i] = this.theTSTZNullBinder;
		} else {
			/* 7957 */this.currentRowBinders[i] = this.theTSTZBinder;

			/* 7959 */if (this.parameterDatum == null) {
				/* 7961 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 7966 */this.parameterDatum[this.currentRank][i] = paramTIMESTAMPTZ.getBytes();
		}

		/* 7969 */this.currentRowCharLens[i] = 0;
	}

	public void setTIMESTAMPTZAtName(String paramString, TIMESTAMPTZ paramTIMESTAMPTZ) throws SQLException {
		/* 7988 */String str = paramString.intern();
		/* 7989 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 7990 */int i = 0;
		/* 7991 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 7993 */for (int k = 0; k < j; k++) {
			/* 7994 */if (arrayOfString[k] != str)
				continue;
			/* 7996 */setTIMESTAMPTZ(k + 1, paramTIMESTAMPTZ);

			/* 7998 */i = 1;
		}

		/* 8001 */if (i == 0)
			/* 8002 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ) throws SQLException {
		/* 8030 */setTIMESTAMPLTZInternal(paramInt, paramTIMESTAMPLTZ);
	}

	void setTIMESTAMPLTZInternal(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ) throws SQLException {
		/* 8036 */if (this.connection.getSessionTimeZone() == null) {
			/* 8038 */DatabaseError.throwSqlException(105);
		}

		/* 8041 */int i = paramInt - 1;

		/* 8043 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 8044 */DatabaseError.throwSqlException(3);
		}
		/* 8046 */if (paramTIMESTAMPLTZ == null) {
			/* 8048 */this.currentRowBinders[i] = this.theTSLTZNullBinder;
		} else {
			/* 8052 */this.currentRowBinders[i] = this.theTSLTZBinder;

			/* 8054 */if (this.parameterDatum == null) {
				/* 8056 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 8061 */this.parameterDatum[this.currentRank][i] = paramTIMESTAMPLTZ.getBytes();
		}

		/* 8064 */this.currentRowCharLens[i] = 0;
	}

	public void setTIMESTAMPLTZAtName(String paramString, TIMESTAMPLTZ paramTIMESTAMPLTZ) throws SQLException {
		/* 8083 */String str = paramString.intern();
		/* 8084 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8085 */int i = 0;
		/* 8086 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 8088 */for (int k = 0; k < j; k++) {
			/* 8089 */if (arrayOfString[k] != str)
				continue;
			/* 8091 */setTIMESTAMPLTZ(k + 1, paramTIMESTAMPLTZ);

			/* 8093 */i = 1;
		}

		/* 8096 */if (i == 0)
			/* 8097 */DatabaseError.throwSqlException(147, paramString);
	}

	public synchronized void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8128 */setAsciiStreamInternal(paramInt1, paramInputStream, paramInt2);
	}

	void setAsciiStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8134 */int i = paramInt1 - 1;

		/* 8136 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 8137 */DatabaseError.throwSqlException(3);
		}

		/* 8142 */set_execute_batch(1);

		/* 8144 */if (paramInputStream == null)
			/* 8145 */basicBindNullString(paramInt1);
		/* 8146 */else if ((this.userRsetType != 1) && (paramInt2 > this.maxVcsCharsSql))
			/* 8147 */DatabaseError.throwSqlException(169);
		/* 8148 */else if (this.currentRowFormOfUse[i] == 1) {
			/* 8150 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
				/* 8152 */if (paramInt2 <= this.maxVcsCharsPlsql) {
					/* 8154 */setAsciiStreamContentsForStringInternal(paramInt1, paramInputStream, paramInt2);
				} else {
					/* 8158 */setAsciiStreamContentsForClobCritical(paramInt1, paramInputStream, paramInt2);
				}

			}
			/* 8163 */else if (paramInt2 <= this.maxVcsCharsSql) {
				/* 8165 */setAsciiStreamContentsForStringInternal(paramInt1, paramInputStream, paramInt2);
			} else {
				/* 8169 */basicBindAsciiStream(paramInt1, paramInputStream, paramInt2);
			}

		}
		/* 8175 */else if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 8177 */if (paramInt2 <= this.maxVcsNCharsPlsql) {
				/* 8179 */setAsciiStreamContentsForStringInternal(paramInt1, paramInputStream, paramInt2);
			} else {
				/* 8183 */setAsciiStreamContentsForClobCritical(paramInt1, paramInputStream, paramInt2);
			}

		}
		/* 8188 */else if (paramInt2 <= this.maxVcsNCharsSql) {
			/* 8190 */setAsciiStreamContentsForStringInternal(paramInt1, paramInputStream, paramInt2);
		} else {
			/* 8194 */setAsciiStreamContentsForClobCritical(paramInt1, paramInputStream, paramInt2);
		}
	}

	void basicBindAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8205 */if (this.userRsetType != 1)
			/* 8206 */DatabaseError.throwSqlException(169);
		/* 8207 */int i = paramInt1 - 1;
		/* 8208 */this.currentRowBinders[i] = this.theLongStreamBinder;

		/* 8210 */if (this.parameterStream == null) {
			/* 8211 */this.parameterStream = new InputStream[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 8214 */this.parameterStream[this.currentRank][i] = this.connection.conversion.ConvertStream(paramInputStream, 5, paramInt2);

		/* 8218 */this.currentRowCharLens[i] = 0;
	}

	void setAsciiStreamContentsForStringInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8226 */byte[] arrayOfByte = new byte[paramInt2];
		/* 8227 */int i = 0;
		try {
			/* 8231 */i = paramInputStream.read(arrayOfByte, 0, paramInt2);

			/* 8233 */if (i == -1) {
				/* 8234 */i = 0;
			}

		} catch (IOException localIOException) {
			/* 8241 */DatabaseError.throwSqlException(localIOException);
		}

		/* 8247 */if (i == 0)
			basicBindNullString(paramInt1);

		/* 8249 */char[] arrayOfChar = new char[paramInt2];

		/* 8251 */DBConversion.asciiBytesToJavaChars(arrayOfByte, i, arrayOfChar);

		/* 8257 */basicBindString(paramInt1, new String(arrayOfChar));
	}

	public void setAsciiStreamAtName(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
		/* 8277 */String str = paramString.intern();
		/* 8278 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8279 */int i = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);
		/* 8280 */int j = 1;

		/* 8282 */for (int k = 0; k < i; k++) {
			/* 8284 */if (arrayOfString[k] != str)
				continue;
			/* 8286 */if (j != 0) {
				/* 8288 */setAsciiStream(k + 1, paramInputStream, paramInt);

				/* 8290 */j = 0;
			} else {
				/* 8294 */DatabaseError.throwSqlException(135);
			}
		}
	}

	public synchronized void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8327 */setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
	}

	void setBinaryStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8333 */int i = paramInt1 - 1;

		/* 8335 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 8336 */DatabaseError.throwSqlException(3);
		}

		/* 8340 */set_execute_batch(1);

		/* 8342 */if (paramInputStream == null)
			/* 8343 */setRAWInternal(paramInt1, null);
		/* 8344 */else if ((this.userRsetType != 1) && (paramInt2 > this.maxRawBytesSql))
			/* 8345 */DatabaseError.throwSqlException(169);
		/* 8346 */else if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 8348 */if (paramInt2 > this.maxRawBytesPlsql) {
				/* 8350 */setBinaryStreamContentsForBlobCritical(paramInt1, paramInputStream, paramInt2);
			} else {
				/* 8354 */setBinaryStreamContentsForByteArrayInternal(paramInt1, paramInputStream, paramInt2);
			}

		}
		/* 8359 */else if (paramInt2 > this.maxRawBytesSql) {
			/* 8361 */basicBindBinaryStream(paramInt1, paramInputStream, paramInt2);
		} else {
			/* 8365 */setBinaryStreamContentsForByteArrayInternal(paramInt1, paramInputStream, paramInt2);
		}
	}

	void setBinaryStreamContentsForByteArrayInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8376 */Object localObject = new byte[paramInt2];
		/* 8377 */int i = 0;
		try {
			/* 8381 */i = paramInputStream.read(localObject, 0, paramInt2);

			/* 8383 */if (i == -1)
				/* 8384 */i = 0;
		} catch (IOException localIOException) {
			/* 8388 */DatabaseError.throwSqlException(localIOException);
		}

		/* 8391 */if (i != paramInt2) {
			/* 8393 */byte[] arrayOfByte = new byte[i];

			/* 8395 */System.arraycopy(localObject, 0, arrayOfByte, 0, i);

			/* 8397 */localObject = arrayOfByte;
		}
		/* 8399 */setBytesInternal(paramInt1, localObject);
	}

	public void setBinaryStreamAtName(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
		/* 8419 */String str = paramString.intern();
		/* 8420 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8421 */int i = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);
		/* 8422 */int j = 1;

		/* 8424 */for (int k = 0; k < i; k++) {
			/* 8426 */if (arrayOfString[k] != str)
				continue;
			/* 8428 */if (j != 0) {
				/* 8430 */setBinaryStream(k + 1, paramInputStream, paramInt);

				/* 8432 */j = 0;
			} else {
				/* 8436 */DatabaseError.throwSqlException(135);
			}
		}
	}

	/** @deprecated */
	public synchronized void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8473 */setUnicodeStreamInternal(paramInt1, paramInputStream, paramInt2);
	}

	void setUnicodeStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
		/* 8479 */int i = paramInt1 - 1;

		/* 8481 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 8482 */DatabaseError.throwSqlException(3);
		}

		/* 8487 */set_execute_batch(1);

		/* 8489 */if (paramInputStream == null) {
			/* 8490 */setStringInternal(paramInt1, null);
			/* 8491 */} else if ((this.userRsetType != 1) && (paramInt2 > this.maxVcsCharsSql)) {
			/* 8492 */DatabaseError.throwSqlException(169);
			/* 8493 */} else if ((this.sqlKind == 1) || (this.sqlKind == 4) || (paramInt2 <= this.maxVcsCharsSql)) {
			/* 8498 */byte[] arrayOfByte = new byte[paramInt2];
			/* 8499 */int j = 0;
			try {
				/* 8503 */j = paramInputStream.read(arrayOfByte, 0, paramInt2);

				/* 8505 */if (j == -1)
					/* 8506 */j = 0;
			} catch (IOException localIOException) {
				/* 8510 */DatabaseError.throwSqlException(localIOException);
			}

			/* 8513 */char[] arrayOfChar = new char[j >> 1];

			/* 8515 */DBConversion.ucs2BytesToJavaChars(arrayOfByte, j, arrayOfChar);

			/* 8518 */setStringInternal(paramInt1, new String(arrayOfChar));
		} else {
			/* 8522 */this.currentRowBinders[i] = this.theLongStreamBinder;

			/* 8524 */if (this.parameterStream == null) {
				/* 8525 */this.parameterStream = new InputStream[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
			}

			/* 8528 */this.parameterStream[this.currentRank][i] = this.connection.conversion.ConvertStream(paramInputStream, 4, paramInt2);

			/* 8532 */this.currentRowCharLens[i] = 0;
		}
	}

	public void setUnicodeStreamAtName(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
		/* 8554 */String str = paramString.intern();
		/* 8555 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8556 */int i = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);
		/* 8557 */int j = 1;

		/* 8559 */for (int k = 0; k < i; k++) {
			/* 8561 */if (arrayOfString[k] != str)
				continue;
			/* 8563 */if (j != 0) {
				/* 8565 */setUnicodeStream(k + 1, paramInputStream, paramInt);

				/* 8567 */j = 0;
			} else {
				/* 8571 */DatabaseError.throwSqlException(135);
			}
		}
	}

	/** @deprecated */
	public void setCustomDatum(int paramInt, CustomDatum paramCustomDatum) throws SQLException {
		/* 8595 */synchronized (this.connection) {
			/* 8597 */synchronized (this) {
				/* 8602 */setObjectInternal(paramInt, this.connection.toDatum(paramCustomDatum));
			}
		}
	}

	void setCustomDatumInternal(int paramInt, CustomDatum paramCustomDatum) throws SQLException {
		/* 8610 */synchronized (this.connection) {
			/* 8612 */synchronized (this) {
				/* 8614 */Datum localDatum = this.connection.toDatum(paramCustomDatum);
				/* 8615 */int i = sqlTypeForObject(localDatum);

				/* 8617 */setObjectCritical(paramInt, localDatum, i, 0);

				/* 8619 */this.currentRowCharLens[(paramInt - 1)] = 0;
			}
		}
	}

	public void setCustomDatumAtName(String paramString, CustomDatum paramCustomDatum) throws SQLException {
		/* 8639 */String str = paramString.intern();
		/* 8640 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8641 */int i = 0;
		/* 8642 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 8644 */for (int k = 0; k < j; k++) {
			/* 8645 */if (arrayOfString[k] != str)
				continue;
			/* 8647 */setCustomDatumInternal(k + 1, paramCustomDatum);

			/* 8649 */i = 1;
		}

		/* 8652 */if (i == 0)
			/* 8653 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setORAData(int paramInt, ORAData paramORAData) throws SQLException {
		/* 8674 */setORADataInternal(paramInt, paramORAData);
	}

	void setORADataInternal(int paramInt, ORAData paramORAData) throws SQLException {
		/* 8679 */synchronized (this.connection) {
			/* 8681 */synchronized (this) {
				/* 8683 */Datum localDatum = paramORAData.toDatum(this.connection);
				/* 8684 */int i = sqlTypeForObject(localDatum);

				/* 8686 */setObjectCritical(paramInt, localDatum, i, 0);

				/* 8688 */this.currentRowCharLens[(paramInt - 1)] = 0;
			}
		}
	}

	public void setORADataAtName(String paramString, ORAData paramORAData) throws SQLException {
		/* 8707 */String str = paramString.intern();
		/* 8708 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 8709 */int i = 0;
		/* 8710 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 8712 */for (int k = 0; k < j; k++) {
			/* 8713 */if (arrayOfString[k] != str)
				continue;
			/* 8715 */setORADataInternal(k + 1, paramORAData);

			/* 8717 */i = 1;
		}

		/* 8720 */if (i == 0)
			/* 8721 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
		/* 8753 */setObjectInternal(paramInt1, paramObject, paramInt2, paramInt3);
	}

	void setObjectInternal(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
		/* 8762 */if ((paramObject == null) && (paramInt2 != 2002) && (paramInt2 != 2008) && (paramInt2 != 2003) && (paramInt2 != 2007) && (paramInt2 != 2006)) {
			/* 8768 */setNullInternal(paramInt1, paramInt2);
		} else {
			/* 8772 */if ((paramInt2 == 2002) || (paramInt2 == 2008) || (paramInt2 == 2003)) {
				/* 8776 */synchronized (this.connection) {
					/* 8778 */synchronized (this) {
						/* 8780 */setObjectCritical(paramInt1, paramObject, paramInt2, paramInt3);

						/* 8782 */this.currentRowCharLens[(paramInt1 - 1)] = 0;
					}
				}

			}

			/* 8788 */synchronized (this) {
				/* 8790 */setObjectCritical(paramInt1, paramObject, paramInt2, paramInt3);
			}
		}
	}

	void setObjectCritical(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
		/* 8814 */switch (paramInt2) {
		case 1:
			/* 8818 */if ((paramObject instanceof CHAR))
				/* 8819 */setCHARInternal(paramInt1, (CHAR) paramObject);
			/* 8820 */else if ((paramObject instanceof String))
				/* 8821 */setStringInternal(paramInt1, (String) paramObject);
			/* 8822 */else if ((paramObject instanceof Boolean)) {
				/* 8823 */setStringInternal(paramInt1, "" + (((Boolean) paramObject).booleanValue() ? 1 : 0));
			}
			/* 8825 */else if ((paramObject instanceof Integer))
				/* 8826 */setStringInternal(paramInt1, "" + ((Integer) paramObject).intValue());
			/* 8827 */else if ((paramObject instanceof Long))
				/* 8828 */setStringInternal(paramInt1, "" + ((Long) paramObject).longValue());
			/* 8829 */else if ((paramObject instanceof Float))
				/* 8830 */setStringInternal(paramInt1, "" + ((Float) paramObject).floatValue());
			/* 8831 */else if ((paramObject instanceof Double))
				/* 8832 */setStringInternal(paramInt1, "" + ((Double) paramObject).doubleValue());
			/* 8833 */else if ((paramObject instanceof BigDecimal))
				/* 8834 */setStringInternal(paramInt1, ((BigDecimal) paramObject).toString());
			/* 8835 */else if ((paramObject instanceof Date))
				/* 8836 */setStringInternal(paramInt1, "" + ((Date) paramObject).toString());
			/* 8837 */else if ((paramObject instanceof Time))
				/* 8838 */setStringInternal(paramInt1, "" + ((Time) paramObject).toString());
			/* 8839 */else if ((paramObject instanceof Timestamp))
				/* 8840 */setStringInternal(paramInt1, "" + ((Timestamp) paramObject).toString());
			else {
				/* 8842 */DatabaseError.throwSqlException(132);
			}

			/* 8845 */break;
		case 12:
			/* 8848 */if ((paramObject instanceof String))
				/* 8849 */setStringInternal(paramInt1, (String) paramObject);
			/* 8850 */else if ((paramObject instanceof Boolean)) {
				/* 8851 */setStringInternal(paramInt1, "" + (((Boolean) paramObject).booleanValue() ? 1 : 0));
			}
			/* 8853 */else if ((paramObject instanceof Integer))
				/* 8854 */setStringInternal(paramInt1, "" + ((Integer) paramObject).intValue());
			/* 8855 */else if ((paramObject instanceof Long))
				/* 8856 */setStringInternal(paramInt1, "" + ((Long) paramObject).longValue());
			/* 8857 */else if ((paramObject instanceof Float))
				/* 8858 */setStringInternal(paramInt1, "" + ((Float) paramObject).floatValue());
			/* 8859 */else if ((paramObject instanceof Double))
				/* 8860 */setStringInternal(paramInt1, "" + ((Double) paramObject).doubleValue());
			/* 8861 */else if ((paramObject instanceof BigDecimal))
				/* 8862 */setStringInternal(paramInt1, ((BigDecimal) paramObject).toString());
			/* 8863 */else if ((paramObject instanceof Date))
				/* 8864 */setStringInternal(paramInt1, "" + ((Date) paramObject).toString());
			/* 8865 */else if ((paramObject instanceof Time))
				/* 8866 */setStringInternal(paramInt1, "" + ((Time) paramObject).toString());
			/* 8867 */else if ((paramObject instanceof Timestamp))
				/* 8868 */setStringInternal(paramInt1, "" + ((Timestamp) paramObject).toString());
			else {
				/* 8870 */DatabaseError.throwSqlException(132);
			}

			/* 8873 */break;
		case 999:
			/* 8876 */setFixedCHARInternal(paramInt1, (String) paramObject);

			/* 8878 */break;
		case -1:
			/* 8881 */if ((paramObject instanceof String))
				/* 8882 */setStringInternal(paramInt1, (String) paramObject);
			/* 8883 */else if ((paramObject instanceof Boolean)) {
				/* 8884 */setStringInternal(paramInt1, "" + (((Boolean) paramObject).booleanValue() ? 1 : 0));
			}
			/* 8887 */else if ((paramObject instanceof Integer)) {
				/* 8888 */setStringInternal(paramInt1, "" + ((Integer) paramObject).intValue());
			}
			/* 8890 */else if ((paramObject instanceof Long)) {
				/* 8891 */setStringInternal(paramInt1, "" + ((Long) paramObject).longValue());
			}
			/* 8893 */else if ((paramObject instanceof Float)) {
				/* 8894 */setStringInternal(paramInt1, "" + ((Float) paramObject).floatValue());
			}
			/* 8896 */else if ((paramObject instanceof Double)) {
				/* 8897 */setStringInternal(paramInt1, "" + ((Double) paramObject).doubleValue());
			}
			/* 8899 */else if ((paramObject instanceof BigDecimal))
				/* 8900 */setStringInternal(paramInt1, ((BigDecimal) paramObject).toString());
			/* 8901 */else if ((paramObject instanceof Date))
				/* 8902 */setStringInternal(paramInt1, "" + ((Date) paramObject).toString());
			/* 8903 */else if ((paramObject instanceof Time))
				/* 8904 */setStringInternal(paramInt1, "" + ((Time) paramObject).toString());
			/* 8905 */else if ((paramObject instanceof Timestamp)) {
				/* 8906 */setStringInternal(paramInt1, "" + ((Timestamp) paramObject).toString());
			} else {
				/* 8909 */DatabaseError.throwSqlException(132);
			}

			/* 8912 */break;
		case 2:
			/* 8917 */if ((paramObject instanceof NUMBER))
				/* 8918 */setNUMBERInternal(paramInt1, (NUMBER) paramObject);
			/* 8919 */else if ((paramObject instanceof Integer))
				/* 8920 */setIntInternal(paramInt1, ((Integer) paramObject).intValue());
			/* 8921 */else if ((paramObject instanceof Long))
				/* 8922 */setLongInternal(paramInt1, ((Long) paramObject).longValue());
			/* 8923 */else if ((paramObject instanceof Float))
				/* 8924 */setFloatInternal(paramInt1, ((Float) paramObject).floatValue());
			/* 8925 */else if ((paramObject instanceof Double))
				/* 8926 */setDoubleInternal(paramInt1, ((Double) paramObject).doubleValue());
			/* 8927 */else if ((paramObject instanceof BigDecimal))
				/* 8928 */setBigDecimalInternal(paramInt1, (BigDecimal) paramObject);
			/* 8929 */else if ((paramObject instanceof String))
				/* 8930 */setNUMBERInternal(paramInt1, new NUMBER((String) paramObject, 0));
			/* 8931 */else if ((paramObject instanceof Boolean))
				/* 8932 */setIntInternal(paramInt1, ((Boolean) paramObject).booleanValue() ? 1 : 0);
			else {
				/* 8934 */DatabaseError.throwSqlException(132);
			}

			/* 8937 */break;
		case 3:
			/* 8940 */if ((paramObject instanceof BigDecimal))
				/* 8941 */setBigDecimalInternal(paramInt1, (BigDecimal) paramObject);
			/* 8942 */else if ((paramObject instanceof Number)) {
				/* 8943 */setBigDecimalInternal(paramInt1, new BigDecimal(((Number) paramObject).doubleValue()));
			}
			/* 8945 */else if ((paramObject instanceof NUMBER))
				/* 8946 */setBigDecimalInternal(paramInt1, ((NUMBER) paramObject).bigDecimalValue());
			/* 8947 */else if ((paramObject instanceof String))
				/* 8948 */setBigDecimalInternal(paramInt1, new BigDecimal((String) paramObject));
			/* 8949 */else if ((paramObject instanceof Boolean)) {
				/* 8950 */setBigDecimalInternal(paramInt1, new BigDecimal(((Boolean) paramObject).booleanValue() ? 1.0D : 0.0D));
			} else {
				/* 8954 */DatabaseError.throwSqlException(132);
			}

			/* 8957 */break;
		case -7:
			/* 8960 */if ((paramObject instanceof Boolean)) {
				/* 8961 */setByteInternal(paramInt1, (byte) (((Boolean) paramObject).booleanValue() ? 1 : 0));
			}
			/* 8963 */else if ((paramObject instanceof String)) {
				/* 8964 */setByteInternal(paramInt1, (byte) (("true".equalsIgnoreCase((String) paramObject)) || ("1".equals(paramObject)) ? 1 : 0));
			}
			/* 8968 */else if ((paramObject instanceof Number))
				/* 8969 */setIntInternal(paramInt1, ((Number) paramObject).byteValue() != 0 ? 1 : 0);
			else {
				/* 8971 */DatabaseError.throwSqlException(132);
			}

			/* 8974 */break;
		case -6:
			/* 8979 */if ((paramObject instanceof Number))
				/* 8980 */setByteInternal(paramInt1, ((Number) paramObject).byteValue());
			/* 8981 */else if ((paramObject instanceof String))
				/* 8982 */setByteInternal(paramInt1, Byte.parseByte((String) paramObject));
			/* 8983 */else if ((paramObject instanceof Boolean)) {
				/* 8984 */setByteInternal(paramInt1, (byte) (((Boolean) paramObject).booleanValue() ? 1 : 0));
			} else {
				/* 8987 */DatabaseError.throwSqlException(132);
			}

			/* 8990 */break;
		case 5:
			/* 8995 */if ((paramObject instanceof Number))
				/* 8996 */setShortInternal(paramInt1, ((Number) paramObject).shortValue());
			/* 8997 */else if ((paramObject instanceof String))
				/* 8998 */setShortInternal(paramInt1, Short.parseShort((String) paramObject));
			/* 8999 */else if ((paramObject instanceof Boolean)) {
				/* 9000 */setShortInternal(paramInt1, (short) (((Boolean) paramObject).booleanValue() ? 1 : 0));
			} else {
				/* 9003 */DatabaseError.throwSqlException(132);
			}

			/* 9006 */break;
		case 4:
			/* 9009 */if ((paramObject instanceof Number))
				/* 9010 */setIntInternal(paramInt1, ((Number) paramObject).intValue());
			/* 9011 */else if ((paramObject instanceof String))
				/* 9012 */setIntInternal(paramInt1, Integer.parseInt((String) paramObject));
			/* 9013 */else if ((paramObject instanceof Boolean))
				/* 9014 */setIntInternal(paramInt1, ((Boolean) paramObject).booleanValue() ? 1 : 0);
			else {
				/* 9016 */DatabaseError.throwSqlException(132);
			}

			/* 9019 */break;
		case -5:
			/* 9022 */if ((paramObject instanceof Number))
				/* 9023 */setLongInternal(paramInt1, ((Number) paramObject).longValue());
			/* 9024 */else if ((paramObject instanceof String))
				/* 9025 */setLongInternal(paramInt1, Long.parseLong((String) paramObject));
			/* 9026 */else if ((paramObject instanceof Boolean))
				/* 9027 */setLongInternal(paramInt1, ((Boolean) paramObject).booleanValue() ? 1L : 0L);
			else {
				/* 9029 */DatabaseError.throwSqlException(132);
			}

			/* 9032 */break;
		case 7:
			/* 9035 */if ((paramObject instanceof Number))
				/* 9036 */setFloatInternal(paramInt1, ((Number) paramObject).floatValue());
			/* 9037 */else if ((paramObject instanceof String))
				/* 9038 */setFloatInternal(paramInt1, Float.valueOf((String) paramObject).floatValue());
			/* 9039 */else if ((paramObject instanceof Boolean))
				/* 9040 */setFloatInternal(paramInt1, ((Boolean) paramObject).booleanValue() ? 1.0F : 0.0F);
			else {
				/* 9042 */DatabaseError.throwSqlException(132);
			}

			/* 9045 */break;
		case 6:

		case 8:
			/* 9050 */if ((paramObject instanceof Number))
				/* 9051 */setDoubleInternal(paramInt1, ((Number) paramObject).doubleValue());
			/* 9052 */else if ((paramObject instanceof String)) {
				/* 9053 */setDoubleInternal(paramInt1, Double.valueOf((String) paramObject).doubleValue());
			}
			/* 9055 */else if ((paramObject instanceof Boolean)) {
				/* 9056 */setDoubleInternal(paramInt1, ((Boolean) paramObject).booleanValue() ? 1.0D : 0.0D);
			} else {
				/* 9059 */DatabaseError.throwSqlException(132);
			}

			/* 9062 */break;
		case -2:
			/* 9065 */if ((paramObject instanceof RAW))
				/* 9066 */setRAWInternal(paramInt1, (RAW) paramObject);
			else {
				/* 9068 */setBytesInternal(paramInt1, (byte[]) paramObject);
			}
			/* 9070 */break;
		case -3:
			/* 9073 */setBytesInternal(paramInt1, (byte[]) paramObject);

			/* 9075 */break;
		case -4:
			/* 9078 */setBytesInternal(paramInt1, (byte[]) paramObject);

			/* 9080 */break;
		case 91:
			/* 9083 */if ((paramObject instanceof DATE))
				/* 9084 */setDATEInternal(paramInt1, (DATE) paramObject);
			/* 9085 */else if ((paramObject instanceof Date))
				/* 9086 */setDateInternal(paramInt1, (Date) paramObject);
			/* 9087 */else if ((paramObject instanceof Timestamp))
				/* 9088 */setTimestampInternal(paramInt1, (Timestamp) paramObject);
			/* 9089 */else if ((paramObject instanceof String))
				/* 9090 */setDateInternal(paramInt1, Date.valueOf((String) paramObject));
			else {
				/* 9092 */DatabaseError.throwSqlException(132);
			}

			/* 9095 */break;
		case 92:
			/* 9098 */if ((paramObject instanceof Time))
				/* 9099 */setTimeInternal(paramInt1, (Time) paramObject);
			/* 9100 */else if ((paramObject instanceof Timestamp)) {
				/* 9101 */setTimeInternal(paramInt1, new Time(((Timestamp) paramObject).getTime()));
			}
			/* 9103 */else if ((paramObject instanceof Date))
				/* 9104 */setTimeInternal(paramInt1, new Time(((Date) paramObject).getTime()));
			/* 9105 */else if ((paramObject instanceof String))
				/* 9106 */setTimeInternal(paramInt1, Time.valueOf((String) paramObject));
			else {
				/* 9108 */DatabaseError.throwSqlException(132);
			}

			/* 9111 */break;
		case 93:
			/* 9114 */if ((paramObject instanceof TIMESTAMP))
				/* 9115 */setTIMESTAMPInternal(paramInt1, (TIMESTAMP) paramObject);
			/* 9116 */else if ((paramObject instanceof Timestamp))
				/* 9117 */setTimestampInternal(paramInt1, (Timestamp) paramObject);
			/* 9118 */else if ((paramObject instanceof Date))
				/* 9119 */setDateInternal(paramInt1, (Date) paramObject);
			/* 9120 */else if ((paramObject instanceof DATE))
				/* 9121 */setDATEInternal(paramInt1, (DATE) paramObject);
			/* 9122 */else if ((paramObject instanceof String))
				/* 9123 */setTimestampInternal(paramInt1, Timestamp.valueOf((String) paramObject));
			else {
				/* 9125 */DatabaseError.throwSqlException(132);
			}

			/* 9128 */break;
		case -100:
			/* 9131 */setTIMESTAMPInternal(paramInt1, (TIMESTAMP) paramObject);

			/* 9133 */break;
		case -101:
			/* 9136 */setTIMESTAMPTZInternal(paramInt1, (TIMESTAMPTZ) paramObject);

			/* 9138 */break;
		case -102:
			/* 9141 */setTIMESTAMPLTZInternal(paramInt1, (TIMESTAMPLTZ) paramObject);

			/* 9143 */break;
		case -103:
			/* 9146 */setINTERVALYMInternal(paramInt1, (INTERVALYM) paramObject);

			/* 9148 */break;
		case -104:
			/* 9151 */setINTERVALDSInternal(paramInt1, (INTERVALDS) paramObject);

			/* 9153 */break;
		case -8:
			/* 9156 */setROWIDInternal(paramInt1, (ROWID) paramObject);

			/* 9158 */break;
		case 100:
			/* 9161 */setBinaryFloatInternal(paramInt1, (BINARY_FLOAT) paramObject);

			/* 9163 */break;
		case 101:
			/* 9166 */setBinaryDoubleInternal(paramInt1, (BINARY_DOUBLE) paramObject);

			/* 9168 */break;
		case 2004:
			/* 9171 */setBLOBInternal(paramInt1, (BLOB) paramObject);

			/* 9173 */break;
		case 2005:
			/* 9176 */setCLOBInternal(paramInt1, (CLOB) paramObject);

			/* 9178 */break;
		case -13:
			/* 9181 */setBFILEInternal(paramInt1, (BFILE) paramObject);

			/* 9183 */break;
		case 2002:

		case 2008:
			/* 9188 */setSTRUCTInternal(paramInt1, STRUCT.toSTRUCT(paramObject, this.connection));

			/* 9190 */break;
		case 2003:
			/* 9193 */setARRAYInternal(paramInt1, ARRAY.toARRAY(paramObject, this.connection));

			/* 9196 */break;
		case 2007:
			/* 9199 */setOPAQUEInternal(paramInt1, (OPAQUE) paramObject);

			/* 9201 */break;
		case 2006:
			/* 9204 */setREFInternal(paramInt1, (REF) paramObject);

			/* 9206 */break;
		default:
			/* 9209 */DatabaseError.throwSqlException(4);
		}
	}

	public void setObjectAtName(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
		/* 9230 */String str = paramString.intern();
		/* 9231 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 9232 */int i = 0;
		/* 9233 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 9235 */for (int k = 0; k < j; k++) {
			/* 9236 */if (arrayOfString[k] != str)
				continue;
			/* 9238 */setObjectInternal(k + 1, paramObject);

			/* 9240 */i = 1;
		}

		/* 9243 */if (i == 0)
			/* 9244 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
		/* 9263 */setObjectInternal(paramInt1, paramObject, paramInt2, 0);
	}

	void setObjectInternal(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
		/* 9270 */setObjectInternal(paramInt1, paramObject, paramInt2, 0);
	}

	public void setObjectAtName(String paramString, Object paramObject, int paramInt) throws SQLException {
		/* 9284 */setObjectAtName(paramString, paramObject, paramInt, 0);
	}

	public void setRefType(int paramInt, REF paramREF) throws SQLException {
		/* 9299 */setREFInternal(paramInt, paramREF);
	}

	void setRefTypeInternal(int paramInt, REF paramREF) throws SQLException {
		/* 9304 */setREFInternal(paramInt, paramREF);
	}

	public void setRefTypeAtName(String paramString, REF paramREF) throws SQLException {
		/* 9316 */setREFAtName(paramString, paramREF);
	}

	public void setRef(int paramInt, Ref paramRef) throws SQLException {
		/* 9332 */setREFInternal(paramInt, (REF) paramRef);
	}

	void setRefInternal(int paramInt, Ref paramRef) throws SQLException {
		/* 9338 */setREFInternal(paramInt, (REF) paramRef);
	}

	public void setRefAtName(String paramString, Ref paramRef) throws SQLException {
		/* 9351 */setREFAtName(paramString, (REF) paramRef);
	}

	public void setREF(int paramInt, REF paramREF) throws SQLException {
		/* 9370 */setREFInternal(paramInt, paramREF);
	}

	void setREFInternal(int paramInt, REF paramREF) throws SQLException {
		/* 9375 */int i = paramInt - 1;

		/* 9377 */if ((i < 0) || (paramInt > this.numberOfBindPositions)) {
			/* 9378 */DatabaseError.throwSqlException(3);
		}
		/* 9380 */if (paramREF == null) {
			/* 9382 */DatabaseError.throwSqlException(68);
		} else {
			/* 9386 */synchronized (this.connection) {
				/* 9388 */synchronized (this) {
					/* 9390 */setREFCritical(i, paramREF);

					/* 9392 */this.currentRowCharLens[i] = 0;
				}
			}
		}
	}

	void setREFCritical(int paramInt, REF paramREF) throws SQLException {
		/* 9408 */StructDescriptor localStructDescriptor = paramREF.getDescriptor();

		/* 9413 */if (localStructDescriptor == null) {
			/* 9414 */DatabaseError.throwSqlException(52);
		}

		/* 9419 */this.currentRowBinders[paramInt] = this.theRefTypeBinder;

		/* 9421 */if (this.parameterDatum == null) {
			/* 9423 */this.parameterDatum = new byte[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 9428 */this.parameterDatum[this.currentRank][paramInt] = paramREF.getBytes();

		/* 9430 */OracleTypeADT localOracleTypeADT = localStructDescriptor.getOracleTypeADT();

		/* 9432 */localOracleTypeADT.getTOID();

		/* 9434 */if (this.parameterOtype == null) {
			/* 9436 */this.parameterOtype = new OracleTypeADT[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 9440 */this.parameterOtype[this.currentRank][paramInt] = localOracleTypeADT;
	}

	public void setREFAtName(String paramString, REF paramREF) throws SQLException {
		/* 9458 */String str = paramString.intern();
		/* 9459 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 9460 */int i = 0;
		/* 9461 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 9463 */for (int k = 0; k < j; k++) {
			/* 9464 */if (arrayOfString[k] != str)
				continue;
			/* 9466 */setREFInternal(k + 1, paramREF);

			/* 9468 */i = 1;
		}

		/* 9471 */if (i == 0)
			/* 9472 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setObject(int paramInt, Object paramObject) throws SQLException {
		/* 9495 */setObjectInternal(paramInt, paramObject);
	}

	void setObjectInternal(int paramInt, Object paramObject) throws SQLException {
		/* 9500 */if ((paramObject instanceof ORAData)) {
			/* 9502 */setORADataInternal(paramInt, (ORAData) paramObject);
		}
		/* 9504 */else if ((paramObject instanceof CustomDatum)) {
			/* 9506 */setCustomDatumInternal(paramInt, (CustomDatum) paramObject);
		} else {
			/* 9510 */int i = sqlTypeForObject(paramObject);

			/* 9512 */setObjectInternal(paramInt, paramObject, i, 0);
		}
	}

	public void setObjectAtName(String paramString, Object paramObject) throws SQLException {
		/* 9530 */String str = paramString.intern();
		/* 9531 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 9532 */int i = 0;
		/* 9533 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 9535 */for (int k = 0; k < j; k++) {
			/* 9536 */if (arrayOfString[k] != str)
				continue;
			/* 9538 */setObjectInternal(k + 1, paramObject);

			/* 9540 */i = 1;
		}

		/* 9543 */if (i == 0)
			/* 9544 */DatabaseError.throwSqlException(147, paramString);
	}

	public void setOracleObject(int paramInt, Datum paramDatum) throws SQLException {
		/* 9561 */setObjectInternal(paramInt, paramDatum);
	}

	void setOracleObjectInternal(int paramInt, Datum paramDatum) throws SQLException {
		/* 9567 */setObjectInternal(paramInt, paramDatum);
	}

	public void setOracleObjectAtName(String paramString, Datum paramDatum) throws SQLException {
		/* 9580 */setObjectAtName(paramString, paramDatum);
	}

	public synchronized void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
		/* 9607 */setPlsqlIndexTableInternal(paramInt1, paramObject, paramInt2, paramInt3, paramInt4, paramInt5);
	}

	void setPlsqlIndexTableInternal(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws SQLException {
		/* 9618 */int i = paramInt1 - 1;

		/* 9620 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 9621 */DatabaseError.throwSqlException(3);
		}

		/* 9625 */int j = getInternalType(paramInt4);

		/* 9627 */Object localObject = null;

		/* 9630 */switch (j) {
		case 1:

		case 96:
			/* 9637 */String[] arrayOfString = null;
			/* 9638 */int k = 0;

			/* 9641 */if ((paramObject instanceof CHAR[])) {
				/* 9643 */CHAR[] arrayOfCHAR = (CHAR[]) paramObject;
				/* 9644 */k = arrayOfCHAR.length;

				/* 9646 */arrayOfString = new String[k];

				/* 9648 */for (int n = 0; n < k; n++) {
					/* 9650 */CHAR localCHAR = arrayOfCHAR[n];
					/* 9651 */if (localCHAR != null)
						/* 9652 */arrayOfString[n] = localCHAR.getString();
				}
			}
			/* 9655 */if ((paramObject instanceof String[])) {
				/* 9657 */arrayOfString = (String[]) paramObject;
				/* 9658 */k = arrayOfString.length;
			}

			/* 9661 */if ((paramInt5 == 0) && (arrayOfString != null)) {
				/* 9662 */for (int m = 0; m < k; m++) {
					/* 9664 */String str = arrayOfString[m];
					/* 9665 */if ((str != null) && (paramInt5 < str.length()))
						/* 9666 */paramInt5 = str.length();
				}
			}
			/* 9669 */localObject = arrayOfString;

			/* 9671 */break;
		case 2:

		case 6:
			/* 9678 */localObject = OracleTypeNUMBER.toNUMBERArray(paramObject, this.connection, 1L, paramInt3);

			/* 9681 */if ((paramInt5 == 0) && (localObject != null)) {
				/* 9683 */paramInt5 = 22;
			}

			/* 9686 */this.currentRowCharLens[i] = 0;

			/* 9688 */break;
		default:
			/* 9706 */DatabaseError.throwSqlException(97);

			/* 9708 */return;
		}

		/* 9711 */this.currentRowBinders[i] = this.thePlsqlIbtBinder;

		/* 9713 */if (this.parameterPlsqlIbt == null) {
			/* 9714 */this.parameterPlsqlIbt = new PlsqlIbtBindInfo[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}

		/* 9717 */this.parameterPlsqlIbt[this.currentRank][i] = new PlsqlIbtBindInfo(localObject, paramInt2, paramInt3, j, paramInt5);

		/* 9724 */this.hasIbtBind = true;
	}

	public synchronized void setPlsqlIndexTableAtName(String paramString, Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws SQLException {
		/* 9751 */String str = paramString.intern();
		/* 9752 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 9753 */int i = 0;
		/* 9754 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 9756 */for (int k = 0; k < j; k++) {
			/* 9757 */if (arrayOfString[k] != str)
				continue;
			/* 9759 */setPlsqlIndexTableInternal(k + 1, paramObject, paramInt1, paramInt2, paramInt3, paramInt4);

			/* 9762 */i = 1;
		}

		/* 9765 */if (i == 0)
			/* 9766 */DatabaseError.throwSqlException(147, paramString);
	}

	void endOfResultSet(boolean paramBoolean) throws SQLException {
		/* 9785 */if (!paramBoolean) {
			/* 9789 */prepareForNewResults(false, false);
		}
	}

	int sqlTypeForObject(Object paramObject) {
		/* 9799 */if (paramObject == null) {
			/* 9801 */return 0;
		}

		/* 9804 */if (!(paramObject instanceof Datum)) {
			/* 9806 */if ((paramObject instanceof String)) {
				/* 9809 */return this.fixedString ? 999 : 12;
			}
			/* 9811 */if ((paramObject instanceof BigDecimal)) {
				/* 9812 */return 2;
			}
			/* 9814 */if ((paramObject instanceof Boolean)) {
				/* 9815 */return -7;
			}
			/* 9817 */if ((paramObject instanceof Integer)) {
				/* 9818 */return 4;
			}
			/* 9820 */if ((paramObject instanceof Long)) {
				/* 9821 */return -5;
			}
			/* 9823 */if ((paramObject instanceof Float)) {
				/* 9824 */return 6;
			}
			/* 9826 */if ((paramObject instanceof Double)) {
				/* 9827 */return 8;
			}
			/* 9829 */if ((paramObject instanceof byte[])) {
				/* 9830 */return -3;
			}

			/* 9834 */if ((paramObject instanceof Short)) {
				/* 9835 */return 5;
			}
			/* 9837 */if ((paramObject instanceof Byte)) {
				/* 9838 */return -6;
			}
			/* 9840 */if ((paramObject instanceof Date)) {
				/* 9841 */return 91;
			}
			/* 9843 */if ((paramObject instanceof Time)) {
				/* 9844 */return 92;
			}
			/* 9846 */if ((paramObject instanceof Timestamp)) {
				/* 9847 */return 93;
			}
			/* 9849 */if ((paramObject instanceof SQLData)) {
				/* 9850 */return 2002;
			}
			/* 9852 */if ((paramObject instanceof ObjectData))
				/* 9853 */return 2002;
		} else {
			/* 9857 */if ((paramObject instanceof BINARY_FLOAT)) {
				/* 9858 */return 100;
			}
			/* 9860 */if ((paramObject instanceof BINARY_DOUBLE)) {
				/* 9861 */return 101;
			}
			/* 9863 */if ((paramObject instanceof BLOB)) {
				/* 9864 */return 2004;
			}
			/* 9866 */if ((paramObject instanceof CLOB)) {
				/* 9867 */return 2005;
			}
			/* 9869 */if ((paramObject instanceof BFILE)) {
				/* 9870 */return -13;
			}
			/* 9872 */if ((paramObject instanceof ROWID)) {
				/* 9873 */return -8;
			}
			/* 9875 */if ((paramObject instanceof NUMBER)) {
				/* 9876 */return 2;
			}
			/* 9878 */if ((paramObject instanceof DATE)) {
				/* 9879 */return 91;
			}
			/* 9881 */if ((paramObject instanceof TIMESTAMP)) {
				/* 9882 */return 93;
			}
			/* 9884 */if ((paramObject instanceof TIMESTAMPTZ)) {
				/* 9885 */return -101;
			}
			/* 9887 */if ((paramObject instanceof TIMESTAMPLTZ)) {
				/* 9888 */return -102;
			}
			/* 9890 */if ((paramObject instanceof REF)) {
				/* 9891 */return 2006;
			}
			/* 9893 */if ((paramObject instanceof CHAR)) {
				/* 9894 */return 1;
			}
			/* 9896 */if ((paramObject instanceof RAW)) {
				/* 9897 */return -2;
			}
			/* 9899 */if ((paramObject instanceof ARRAY)) {
				/* 9900 */return 2003;
			}
			/* 9902 */if ((paramObject instanceof STRUCT)) {
				/* 9903 */return 2002;
			}
			/* 9905 */if ((paramObject instanceof OPAQUE)) {
				/* 9906 */return 2007;
			}
			/* 9908 */if ((paramObject instanceof INTERVALYM)) {
				/* 9909 */return -103;
			}
			/* 9911 */if ((paramObject instanceof INTERVALDS)) {
				/* 9912 */return -104;
			}
		}
		/* 9915 */return 1111;
	}

	public synchronized void clearParameters() throws SQLException {
		/* 9935 */this.clearParameters = true;

		/* 9937 */for (int i = 0; i < this.numberOfBindPositions; i++)
			/* 9938 */this.currentRowBinders[i] = null;
	}

	void printByteArray(byte[] paramArrayOfByte) {
		/* 9948 */if (paramArrayOfByte != null) {
			/* 9956 */int j = paramArrayOfByte.length;

			/* 9958 */for (int i = 0; i < j; i++) {
				/* 9960 */int k = paramArrayOfByte[i] & 0xFF;

				/* 9962 */if (k >= 16)
					continue;
			}
		}
	}

	public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
		/* 10015 */setCharacterStreamInternal(paramInt1, paramReader, paramInt2);
	}

	void setCharacterStreamInternal(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
		/* 10021 */int i = paramInt1 - 1;

		/* 10023 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 10024 */DatabaseError.throwSqlException(3);
		}

		/* 10029 */set_execute_batch(1);

		/* 10031 */if (paramReader == null) {
			/* 10032 */basicBindNullString(paramInt1);
			/* 10033 */} else if ((this.userRsetType != 1) && (paramInt2 > this.maxVcsCharsSql)) {
			/* 10034 */DatabaseError.throwSqlException(169);
			/* 10035 */} else if (this.currentRowFormOfUse[i] == 1) {
			/* 10037 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
				/* 10039 */if (paramInt2 <= this.maxVcsBytesPlsql) {
					if (!(paramInt2 > this.maxVcsCharsPlsql & this.isServerCharSetFixedWidth))
						;
				} else {
					/* 10042 */setReaderContentsForClobCritical(paramInt1, paramReader, paramInt2);
					return;
				}
				/* 10044 */if (paramInt2 <= this.maxVcsCharsPlsql) {
					/* 10046 */setReaderContentsForStringInternal(paramInt1, paramReader, paramInt2);
				} else {
					/* 10050 */setReaderContentsForStringOrClobInVariableWidthCase(paramInt1, paramReader, paramInt2, false);
				}

			}
			/* 10055 */else if (paramInt2 <= this.maxVcsCharsSql) {
				/* 10057 */setReaderContentsForStringInternal(paramInt1, paramReader, paramInt2);
			} else {
				/* 10061 */basicBindCharacterStream(paramInt1, paramReader, paramInt2);
			}

		}
		/* 10067 */else if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
			/* 10069 */if (paramInt2 <= this.maxVcsBytesPlsql) {
				if (!(paramInt2 > this.maxVcsNCharsPlsql & this.isServerCharSetFixedWidth))
					;
			} else {
				/* 10072 */setReaderContentsForClobCritical(paramInt1, paramReader, paramInt2);
				return;
			}
			/* 10074 */if (paramInt2 <= this.maxVcsNCharsPlsql) {
				/* 10076 */setReaderContentsForStringInternal(paramInt1, paramReader, paramInt2);
			} else {
				/* 10080 */setReaderContentsForStringOrClobInVariableWidthCase(paramInt1, paramReader, paramInt2, true);
			}

		}
		/* 10085 */else if (paramInt2 <= this.maxVcsCharsSql) {
			/* 10087 */setReaderContentsForStringInternal(paramInt1, paramReader, paramInt2);
		} else {
			/* 10091 */setReaderContentsForClobCritical(paramInt1, paramReader, paramInt2);
		}
	}

	void basicBindCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
		/* 10101 */int i = paramInt1 - 1;
		/* 10102 */this.currentRowBinders[i] = this.theLongStreamBinder;
		/* 10103 */if (this.parameterStream == null) {
			/* 10104 */this.parameterStream = new InputStream[this.numberOfBindRowsAllocated][this.numberOfBindPositions];
		}
		/* 10106 */this.parameterStream[this.currentRank][i] = this.connection.conversion.ConvertStream(paramReader, 7, paramInt2, this.currentRowFormOfUse[i]);

		/* 10109 */this.currentRowCharLens[i] = 0;
	}

	void setReaderContentsForStringOrClobInVariableWidthCase(int paramInt1, Reader paramReader, int paramInt2, boolean paramBoolean) throws SQLException {
		/* 10126 */Object localObject = new char[paramInt2];
		/* 10127 */int i = 0;
		try {
			/* 10130 */i = paramReader.read(localObject, 0, paramInt2);

			/* 10132 */if (i == -1)
				/* 10133 */i = 0;
		} catch (IOException localIOException) {
			/* 10137 */DatabaseError.throwSqlException(localIOException);
		}
		/* 10139 */if (i != paramInt2) {
			/* 10141 */char[] arrayOfChar = new char[i];

			/* 10143 */System.arraycopy(localObject, 0, arrayOfChar, 0, i);

			/* 10145 */localObject = arrayOfChar;
		}
		/* 10147 */int j = this.connection.conversion.encodedByteLength(localObject, paramBoolean);
		/* 10148 */if (j < this.maxVcsBytesPlsql) {
			/* 10150 */setStringInternal(paramInt1, new String(localObject));
		} else {
			/* 10154 */setStringForClobCritical(paramInt1, new String(localObject));
		}
	}

	void setReaderContentsForStringInternal(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
		/* 10165 */Object localObject = new char[paramInt2];
		/* 10166 */int i = 0;
		try {
			/* 10170 */i = paramReader.read(localObject, 0, paramInt2);

			/* 10172 */if (i == -1)
				/* 10173 */i = 0;
		} catch (IOException localIOException) {
			/* 10177 */DatabaseError.throwSqlException(localIOException);
		}

		/* 10180 */if (i != paramInt2) {
			/* 10182 */char[] arrayOfChar = new char[i];

			/* 10184 */System.arraycopy(localObject, 0, arrayOfChar, 0, i);

			/* 10186 */localObject = arrayOfChar;
		}
		/* 10188 */setStringInternal(paramInt1, new String(localObject));
	}

	public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
		/* 10204 */setDATEInternal(paramInt, paramDate == null ? null : new DATE(paramDate, paramCalendar));
	}

	void setDateInternal(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
		/* 10210 */setDATEInternal(paramInt, paramDate == null ? null : new DATE(paramDate, paramCalendar));
	}

	public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
		/* 10225 */setDATEInternal(paramInt, paramTime == null ? null : new DATE(paramTime, paramCalendar));
	}

	void setTimeInternal(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
		/* 10231 */setDATEInternal(paramInt, paramTime == null ? null : new DATE(paramTime, paramCalendar));
	}

	public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
		/* 10246 */setTimestampInternal(paramInt, paramTimestamp, paramCalendar);
	}

	void setTimestampInternal(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
		/* 10252 */if (this.connection.v8Compatible) {
			/* 10256 */if (paramTimestamp == null) {
				/* 10258 */setDATEInternal(paramInt, null);
			} else {
				/* 10262 */DATE localDATE = new DATE(paramTimestamp, paramCalendar);

				/* 10264 */setDATEInternal(paramInt, localDATE);
			}

			/* 10267 */return;
		}

		/* 10276 */if (paramTimestamp == null) {
			/* 10278 */setTIMESTAMPInternal(paramInt, null);
		} else {
			/* 10284 */int i = paramTimestamp.getNanos();
			byte[] arrayOfByte;
			/* 10288 */if (i == 0)
				/* 10289 */arrayOfByte = new byte[7];
			else {
				/* 10291 */arrayOfByte = new byte[11];
			}
			/* 10293 */if (paramCalendar == null) {
				/* 10294 */paramCalendar = Calendar.getInstance();
			}
			/* 10296 */paramCalendar.clear();
			/* 10297 */paramCalendar.setTime(paramTimestamp);

			/* 10300 */int j = paramCalendar.get(1);

			/* 10303 */if (paramCalendar.get(0) == 0) {
				/* 10304 */j = -(j - 1);
			}
			/* 10306 */arrayOfByte[0] = (byte) (j / 100 + 100);
			/* 10307 */arrayOfByte[1] = (byte) (j % 100 + 100);
			/* 10308 */arrayOfByte[2] = (byte) (paramCalendar.get(2) + 1);
			/* 10309 */arrayOfByte[3] = (byte) paramCalendar.get(5);
			/* 10310 */arrayOfByte[4] = (byte) (paramCalendar.get(11) + 1);
			/* 10311 */arrayOfByte[5] = (byte) (paramCalendar.get(12) + 1);
			/* 10312 */arrayOfByte[6] = (byte) (paramCalendar.get(13) + 1);

			/* 10319 */if (i != 0) {
				/* 10321 */arrayOfByte[7] = (byte) (i >> 24);
				/* 10322 */arrayOfByte[8] = (byte) (i >> 16 & 0xFF);
				/* 10323 */arrayOfByte[9] = (byte) (i >> 8 & 0xFF);
				/* 10324 */arrayOfByte[10] = (byte) (i & 0xFF);
			}

			/* 10327 */setTIMESTAMPInternal(paramInt, new TIMESTAMP(arrayOfByte));
		}
	}

	public void setCheckBindTypes(boolean paramBoolean) {
		/* 10342 */this.checkBindTypes = paramBoolean;
	}

	final void setOracleBatchStyle() throws SQLException {
		/* 10380 */if (this.m_batchStyle == 2) {
			/* 10382 */DatabaseError.throwSqlException(90, "operation cannot be mixed with JDBC-2.0-style batching");
		}
		/* 10385 */else if (this.m_batchStyle != 0)
			;
		/* 10392 */this.m_batchStyle = 1;
	}

	final void setJdbcBatchStyle() throws SQLException {
		/* 10403 */if (this.m_batchStyle == 1) {
			/* 10405 */DatabaseError.throwSqlException(90, "operation cannot be mixed with Oracle-style batching");
		}

		/* 10409 */this.m_batchStyle = 2;
	}

	final void checkIfJdbcBatchExists() throws SQLException {
		/* 10423 */if (doesJdbcBatchExist()) {
			/* 10425 */DatabaseError.throwSqlException(81, "batch must be either executed or cleared");
		}
	}

	boolean doesJdbcBatchExist() {
		/* 10433 */return (this.currentRank > 0) && (this.m_batchStyle == 2);
	}

	boolean isJdbcBatchStyle() {
		/* 10440 */return this.m_batchStyle == 2;
	}

	public synchronized void addBatch() throws SQLException {
		/* 10478 */setJdbcBatchStyle();

		/* 10488 */processCompletedBindRow(this.currentRank + 2, (this.currentRank > 0) && ((this.sqlKind == 1) || (this.sqlKind == 4)));

		/* 10493 */this.currentRank += 1;
	}

	public synchronized void addBatch(String paramString) throws SQLException {
		/* 10501 */DatabaseError.throwSqlException(23);
	}

	public synchronized void clearBatch() throws SQLException {
		/* 10521 */for (int i = this.currentRank; i >= 0; i--) {
			/* 10522 */for (int j = 0; j < this.numberOfBindPositions; j++)
				/* 10523 */this.binders[i][j] = null;
		}
		/* 10525 */this.currentRank = 0;

		/* 10527 */if (this.binders != null) {
			/* 10528 */this.currentRowBinders = this.binders[0];
		}
		/* 10530 */this.pushedBatches = null;
		/* 10531 */this.pushedBatchesTail = null;
		/* 10532 */this.firstRowInBatch = 0;

		/* 10534 */this.clearParameters = true;
	}

	public int[] executeBatch() throws SQLException {
		/* 10562 */synchronized (this.connection) {
			/* 10564 */synchronized (this) {
				/* 10566 */int i = 0;

				/* 10571 */cleanOldTempLobs();
				/* 10572 */setJdbcBatchStyle();

				/* 10574 */int[] arrayOfInt = new int[this.currentRank];

				/* 10576 */if (this.currentRank > 0) {
					/* 10580 */ensureOpen();

					/* 10583 */prepareForNewResults(true, true);

					/* 10585 */if (this.sqlKind == 0) {
						/* 10587 */DatabaseError.throwBatchUpdateException(80, 0, null);
					}

					/* 10593 */this.noMoreUpdateCounts = false;

					/* 10595 */int j = 0;
					try {
						/* 10602 */this.connection.needLine();

						/* 10604 */if (!this.isOpen) {
							/* 10606 */this.connection.open(this);

							/* 10608 */this.isOpen = true;
						}

						/* 10614 */int k = this.currentRank;

						/* 10616 */if (this.pushedBatches == null) {
							/* 10621 */setupBindBuffers(0, this.currentRank);
							/* 10622 */executeForRows(false);
						} else {
							/* 10630 */if (this.currentRank > this.firstRowInBatch) {
								/* 10634 */pushBatch(true);
							}
							/* 10636 */boolean bool = this.needToParse;
							do {
								/* 10644 */PushedBatch localPushedBatch = this.pushedBatches;

								/* 10646 */this.currentBatchCharLens = localPushedBatch.currentBatchCharLens;
								/* 10647 */this.lastBoundCharLens = localPushedBatch.lastBoundCharLens;
								/* 10648 */this.lastBoundNeeded = localPushedBatch.lastBoundNeeded;
								/* 10649 */this.currentBatchBindAccessors = localPushedBatch.currentBatchBindAccessors;
								/* 10650 */this.needToParse = localPushedBatch.need_to_parse;
								/* 10651 */this.currentBatchNeedToPrepareBinds = localPushedBatch.current_batch_need_to_prepare_binds;

								/* 10653 */this.firstRowInBatch = localPushedBatch.first_row_in_batch;

								/* 10655 */setupBindBuffers(localPushedBatch.first_row_in_batch, localPushedBatch.number_of_rows_to_be_bound);

								/* 10660 */this.currentRank = localPushedBatch.number_of_rows_to_be_bound;

								/* 10662 */executeForRows(false);

								/* 10664 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
									/* 10666 */j += this.validRows;
									/* 10667 */arrayOfInt[(i++)] = this.validRows;
								}

								/* 10670 */this.pushedBatches = localPushedBatch.next;
							}

							/* 10673 */while (this.pushedBatches != null);

							/* 10676 */this.pushedBatchesTail = null;
							/* 10677 */this.firstRowInBatch = 0;

							/* 10679 */this.needToParse = bool;
						}

						/* 10684 */slideDownCurrentRow(k);
					} catch (SQLException localSQLException) {
						/* 10691 */clearBatch();
						/* 10692 */this.needToParse = true;

						/* 10694 */if ((this.sqlKind != 1) && (this.sqlKind != 4)) {
							/* 10695 */for (i = 0; i < arrayOfInt.length; i++)
								/* 10696 */arrayOfInt[i] = -3;
						}
						/* 10698 */DatabaseError.throwBatchUpdateException(localSQLException, (this.sqlKind == 1) || (this.sqlKind == 4) ? i : arrayOfInt.length, arrayOfInt);
					} finally {
						/* 10707 */if ((this.sqlKind == 1) || (this.sqlKind == 4)) {
							/* 10708 */this.validRows = j;
						}
						/* 10710 */checkValidRowsStatus();

						/* 10712 */this.currentRank = 0;
					}

					/* 10716 */if (this.validRows < 0) {
						/* 10718 */for (i = 0; i < arrayOfInt.length; i++) {
							/* 10719 */arrayOfInt[i] = -3;
						}
						/* 10721 */DatabaseError.throwBatchUpdateException(81, 0, arrayOfInt);
					}
					/* 10725 */else if ((this.sqlKind != 1) && (this.sqlKind != 4)) {
						/* 10727 */for (i = 0; i < arrayOfInt.length; i++) {
							/* 10728 */arrayOfInt[i] = -2;
						}
					}
				}
				/* 10732 */return arrayOfInt;
			}
		}
	}

	void pushBatch(boolean paramBoolean) {
		/* 10739 */PushedBatch localPushedBatch = new PushedBatch();

		/* 10741 */localPushedBatch.currentBatchCharLens = new int[this.numberOfBindPositions];

		/* 10743 */System.arraycopy(this.currentBatchCharLens, 0, localPushedBatch.currentBatchCharLens, 0, this.numberOfBindPositions);

		/* 10746 */localPushedBatch.lastBoundCharLens = new int[this.numberOfBindPositions];

		/* 10748 */System.arraycopy(this.lastBoundCharLens, 0, localPushedBatch.lastBoundCharLens, 0, this.numberOfBindPositions);

		/* 10751 */if (this.currentBatchBindAccessors != null) {
			/* 10753 */localPushedBatch.currentBatchBindAccessors = new Accessor[this.numberOfBindPositions];

			/* 10755 */System.arraycopy(this.currentBatchBindAccessors, 0, localPushedBatch.currentBatchBindAccessors, 0, this.numberOfBindPositions);
		}

		/* 10759 */localPushedBatch.lastBoundNeeded = this.lastBoundNeeded;
		/* 10760 */localPushedBatch.need_to_parse = this.needToParse;
		/* 10761 */localPushedBatch.current_batch_need_to_prepare_binds = this.currentBatchNeedToPrepareBinds;
		/* 10762 */localPushedBatch.first_row_in_batch = this.firstRowInBatch;
		/* 10763 */localPushedBatch.number_of_rows_to_be_bound = (this.currentRank - this.firstRowInBatch);

		/* 10765 */if (this.pushedBatches == null)
			/* 10766 */this.pushedBatches = localPushedBatch;
		else {
			/* 10768 */this.pushedBatchesTail.next = localPushedBatch;
		}
		/* 10770 */this.pushedBatchesTail = localPushedBatch;

		/* 10772 */if (!paramBoolean) {
			/* 10778 */int[] arrayOfInt = this.currentBatchCharLens;

			/* 10780 */this.currentBatchCharLens = this.lastBoundCharLens;
			/* 10781 */this.lastBoundCharLens = arrayOfInt;

			/* 10783 */this.lastBoundNeeded = false;

			/* 10785 */for (int i = 0; i < this.numberOfBindPositions; i++) {
				/* 10786 */this.currentBatchCharLens[i] = 0;
			}
			/* 10788 */this.firstRowInBatch = this.currentRank;
		}
	}

	int doScrollPstmtExecuteUpdate() throws SQLException {
		/* 10804 */doScrollExecuteCommon();

		/* 10806 */if (this.sqlKind == 0) {
			/* 10807 */this.scrollRsetTypeSolved = true;
		}

		/* 10812 */return this.validRows;
	}

	public int copyBinds(Statement paramStatement, int paramInt) throws SQLException {
		/* 10825 */if (this.numberOfBindPositions > 0) {
			/* 10827 */OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement) paramStatement;

			/* 10829 */Binder[] arrayOfBinder = this.binders[0];
			/* 10830 */int i = this.bindIndicatorSubRange + 3;

			/* 10832 */int j = this.bindByteSubRange;
			/* 10833 */int k = this.bindCharSubRange;
			/* 10834 */int m = this.indicatorsOffset;
			/* 10835 */int n = this.valueLengthsOffset;

			/* 10837 */for (int i1 = 0; i1 < this.numberOfBindPositions; i1++) {
				/* 10839 */short s = this.bindIndicators[(i + 0)];

				/* 10841 */int i2 = this.bindIndicators[(i + 1)];

				/* 10843 */int i3 = this.bindIndicators[(i + 2)];

				/* 10846 */int i4 = i1 + paramInt;

				/* 10848 */if (this.bindIndicators[m] == -1) {
					/* 10850 */localOraclePreparedStatement.currentRowBinders[i4] = copiedNullBinder(s, i2);

					/* 10852 */if (i3 > 0)
						/* 10853 */localOraclePreparedStatement.currentRowCharLens[i4] = 1;
				}
				/* 10855 */else if ((s == 109) || (s == 111)) {
					/* 10857 */localOraclePreparedStatement.currentRowBinders[i4] = (s == 109 ? this.theNamedTypeBinder : this.theRefTypeBinder);

					/* 10862 */byte[] arrayOfByte1 = this.parameterDatum[0][i1];
					/* 10863 */int i5 = arrayOfByte1.length;
					/* 10864 */byte[] arrayOfByte2 = new byte[i5];

					/* 10866 */localOraclePreparedStatement.parameterDatum[0][i4] = arrayOfByte2;

					/* 10868 */System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i5);

					/* 10870 */localOraclePreparedStatement.parameterOtype[0][i4] = this.parameterOtype[0][i1];
				}
				/* 10872 */else if (i2 > 0) {
					/* 10874 */localOraclePreparedStatement.currentRowBinders[i4] = copiedByteBinder(s, this.bindBytes, j, i2, this.bindIndicators[n]);
				}
				/* 10877 */else if (i3 > 0) {
					/* 10879 */localOraclePreparedStatement.currentRowBinders[i4] = copiedCharBinder(s, this.bindChars, k, i3, this.bindIndicators[n]);

					/* 10881 */localOraclePreparedStatement.currentRowCharLens[i4] = i3;
				} else {
					/* 10884 */throw new Error("copyBinds doesn't understand type " + s);
				}
				/* 10886 */j += this.bindBufferCapacity * i2;
				/* 10887 */k += this.bindBufferCapacity * i3;
				/* 10888 */m += this.numberOfBindRowsAllocated;
				/* 10889 */n += this.numberOfBindRowsAllocated;
				/* 10890 */i += 10;
			}
		}

		/* 10894 */return this.numberOfBindPositions;
	}

	Binder copiedNullBinder(short paramShort, int paramInt) throws SQLException {
		/* 10899 */return new CopiedNullBinder(paramShort, paramInt);
	}

	Binder copiedByteBinder(short paramShort1, byte[] paramArrayOfByte, int paramInt1, int paramInt2, short paramShort2) throws SQLException {
		/* 10905 */byte[] arrayOfByte = new byte[paramInt2];

		/* 10907 */System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);

		/* 10909 */return new CopiedByteBinder(paramShort1, paramInt2, arrayOfByte, paramShort2);
	}

	Binder copiedCharBinder(short paramShort1, char[] paramArrayOfChar, int paramInt1, int paramInt2, short paramShort2) throws SQLException {
		/* 10915 */char[] arrayOfChar = new char[paramInt2];

		/* 10917 */System.arraycopy(paramArrayOfChar, paramInt1, arrayOfChar, 0, paramInt2);

		/* 10919 */return new CopiedCharBinder(paramShort1, arrayOfChar, paramShort2);
	}

	protected void hardClose() throws SQLException {
		super.hardClose();

		this.bindBytes = null;
		this.bindChars = null;
		this.bindIndicators = null;

		if (!this.connection.isClosed()) {
			cleanAllTempLobs();
		}

		this.lastBoundBytes = null;
		this.lastBoundChars = null;

		clearParameters();
	}

	protected void alwaysOnClose() throws SQLException {
		/* 10962 */if (this.currentRank > 0) {
			/* 10964 */if (this.m_batchStyle == 2) {
				/* 10965 */clearBatch();
			} else {
				/* 10971 */int i = this.validRows;

				/* 10973 */this.prematureBatchCount = sendBatch();
				/* 10974 */this.validRows = i;
			}
		}

		/* 10978 */super.alwaysOnClose();
	}

	public synchronized void setDisableStmtCaching(boolean paramBoolean) {
		/* 10993 */if (paramBoolean == true)
			/* 10994 */this.cacheState = 3;
	}

	public synchronized void setFormOfUse(int paramInt, short paramShort) {
		/* 11006 */int i = paramInt - 1;

		/* 11008 */if (this.currentRowFormOfUse[i] != paramShort) {
			/* 11010 */this.currentRowFormOfUse[i] = paramShort;
			Accessor localAccessor;
			/* 11014 */if (this.currentRowBindAccessors != null) {
				/* 11016 */localAccessor = this.currentRowBindAccessors[i];

				/* 11018 */if (localAccessor != null) {
					/* 11019 */localAccessor.setFormOfUse(paramShort);
				}
			}

			/* 11023 */if (this.returnParamAccessors != null) {
				/* 11025 */localAccessor = this.returnParamAccessors[i];

				/* 11027 */if (localAccessor != null)
					/* 11028 */localAccessor.setFormOfUse(paramShort);
			}
		}
	}

	public synchronized void setURL(int paramInt, URL paramURL) throws SQLException {
		/* 11055 */setURLInternal(paramInt, paramURL);
	}

	void setURLInternal(int paramInt, URL paramURL) throws SQLException {
		/* 11060 */setStringInternal(paramInt, paramURL.toString());
	}

	public void setURLAtName(String paramString, URL paramURL) throws SQLException {
		/* 11080 */String str = paramString.intern();
		/* 11081 */String[] arrayOfString = this.sqlObject.getParameterList();
		/* 11082 */int i = 0;
		/* 11083 */int j = Math.min(this.sqlObject.getParameterCount(), arrayOfString.length);

		/* 11085 */for (int k = 0; k < j; k++) {
			/* 11086 */if (arrayOfString[k] != str)
				continue;
			/* 11088 */setURL(k + 1, paramURL);

			/* 11090 */i = 1;
		}

		/* 11093 */if (i == 0)
			/* 11094 */DatabaseError.throwSqlException(147, paramString);
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		/* 11104 */return new OracleParameterMetaData(this.sqlObject.getParameterCount());
	}

	public oracle.jdbc.OracleParameterMetaData OracleGetParameterMetaData() throws SQLException {
		/* 11127 */DatabaseError.throwUnsupportedFeatureSqlException();

		/* 11131 */return null;
	}

	public void registerReturnParameter(int paramInt1, int paramInt2) throws SQLException {
		/* 11143 */if (this.numberOfBindPositions <= 0) {
			/* 11144 */DatabaseError.throwSqlException(90);
		}
		/* 11146 */if (this.numReturnParams <= 0) {
			/* 11148 */this.numReturnParams = getReturnParameterCount(this.sqlObject.getOriginalSql());
			/* 11149 */if (this.numReturnParams <= 0) {
				/* 11150 */DatabaseError.throwSqlException(90);
			}
		}
		/* 11153 */int i = paramInt1 - 1;
		/* 11154 */if ((i < this.numberOfBindPositions - this.numReturnParams) || (paramInt1 > this.numberOfBindPositions)) {
			/* 11156 */DatabaseError.throwSqlException(3);
		}
		/* 11158 */int j = getInternalTypeForDmlReturning(paramInt2);

		/* 11160 */short s = 0;
		/* 11161 */if ((this.currentRowFormOfUse != null) && (this.currentRowFormOfUse[i] != 0)) {
			/* 11162 */s = this.currentRowFormOfUse[i];
		}
		/* 11164 */registerReturnParameterInternal(i, j, paramInt2, -1, s, null);

		/* 11167 */this.currentRowBinders[i] = this.theReturnParamBinder;
	}

	public void registerReturnParameter(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
		/* 11178 */if (this.numberOfBindPositions <= 0) {
			/* 11179 */DatabaseError.throwSqlException(90);
		}
		/* 11181 */int i = paramInt1 - 1;
		/* 11182 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 11183 */DatabaseError.throwSqlException(3);
		}
		/* 11185 */if ((paramInt2 != 1) && (paramInt2 != 12) && (paramInt2 != -1) && (paramInt2 != -2) && (paramInt2 != -3) && (paramInt2 != -4) && (paramInt2 != 12)) {
			/* 11193 */DatabaseError.throwSqlException(68);
		}

		/* 11196 */if (paramInt3 <= 0) {
			/* 11198 */DatabaseError.throwSqlException(68);
		}

		/* 11201 */int j = getInternalTypeForDmlReturning(paramInt2);

		/* 11203 */short s = 0;
		/* 11204 */if ((this.currentRowFormOfUse != null) && (this.currentRowFormOfUse[i] != 0)) {
			/* 11205 */s = this.currentRowFormOfUse[i];
		}
		/* 11207 */registerReturnParameterInternal(i, j, paramInt2, paramInt3, s, null);

		/* 11210 */this.currentRowBinders[i] = this.theReturnParamBinder;
	}

	public void registerReturnParameter(int paramInt1, int paramInt2, String paramString) throws SQLException {
		/* 11221 */if (this.numberOfBindPositions <= 0) {
			/* 11222 */DatabaseError.throwSqlException(90);
		}
		/* 11224 */int i = paramInt1 - 1;
		/* 11225 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 11226 */DatabaseError.throwSqlException(3);
		}
		/* 11228 */int j = getInternalTypeForDmlReturning(paramInt2);
		/* 11229 */if ((j != 111) && (j != 109)) {
			/* 11232 */DatabaseError.throwSqlException(68);
		}

		/* 11235 */registerReturnParameterInternal(i, j, paramInt2, -1, 0, paramString);

		/* 11238 */this.currentRowBinders[i] = this.theReturnParamBinder;
	}

	public ResultSet getReturnResultSet() throws SQLException {
		/* 11247 */if (this.closed) {
			/* 11248 */DatabaseError.throwSqlException(9);
		}
		/* 11250 */if ((this.returnParamAccessors == null) || (this.numReturnParams == 0)) {
			/* 11251 */DatabaseError.throwSqlException(144);
		}
		/* 11253 */if (this.returnResultSet == null) {
			/* 11255 */this.returnResultSet = new OracleReturnResultSet(this);
		}

		/* 11258 */return this.returnResultSet;
	}

	int getInternalTypeForDmlReturning(int paramInt) throws SQLException {
		/* 11270 */int i = 0;

		/* 11272 */switch (paramInt) {
		case -7:

		case -6:

		case -5:

		case 2:

		case 3:

		case 4:

		case 5:

		case 6:

		case 7:

		case 8:
			/* 11284 */i = 6;
			/* 11285 */break;
		case 100:
			/* 11288 */i = 100;
			/* 11289 */break;
		case 101:
			/* 11292 */i = 101;
			/* 11293 */break;
		case 1:
			/* 11296 */i = 96;
			/* 11297 */break;
		case 12:
			/* 11300 */i = 1;
			/* 11301 */break;
		case -1:
			/* 11304 */i = 8;
			/* 11305 */break;
		case 91:

		case 92:
			/* 11309 */i = 12;
			/* 11310 */break;
		case 93:
			/* 11313 */i = 180;
			/* 11314 */break;
		case -101:
			/* 11317 */i = 181;
			/* 11318 */break;
		case -102:
			/* 11321 */i = 231;
			/* 11322 */break;
		case -103:
			/* 11325 */i = 182;
			/* 11326 */break;
		case -104:
			/* 11329 */i = 183;
			/* 11330 */break;
		case -3:

		case -2:
			/* 11334 */i = 23;
			/* 11335 */break;
		case -4:
			/* 11338 */i = 24;
			/* 11339 */break;
		case -8:
			/* 11342 */i = 104;
			/* 11343 */break;
		case 2004:
			/* 11346 */i = 113;
			/* 11347 */break;
		case 2005:
			/* 11350 */i = 112;
			/* 11351 */break;
		case -13:
			/* 11354 */i = 114;
			/* 11355 */break;
		case 2002:

		case 2003:

		case 2007:

		case 2008:
			/* 11361 */i = 109;
			/* 11362 */break;
		case 2006:
			/* 11365 */i = 111;
			/* 11366 */break;
		case 70:
			/* 11369 */i = 1;
			/* 11370 */break;
		default:
			/* 11375 */DatabaseError.throwSqlException(4);
		}

		/* 11381 */return i;
	}

	static int getReturnParameterCount(String paramString) {
		/* 11391 */int i = -1;
		/* 11392 */String str = paramString.toUpperCase();
		/* 11393 */int j = str.indexOf("RETURNING");
		/* 11394 */if (j >= 0) {
			/* 11396 */char[] arrayOfChar = new char[str.length() - j];
			/* 11397 */str.getChars(j, str.length(), arrayOfChar, 0);

			/* 11399 */i = 0;
			/* 11400 */for (int k = 0; k < arrayOfChar.length; k++) {
				/* 11402 */if (arrayOfChar[k] != '?')
					continue;
				i++;
			}
		}

		/* 11406 */return i;
	}

	void registerReturnParamsForAutoKey() throws SQLException {
		/* 11413 */int[] arrayOfInt1 = this.autoKeyInfo.returnTypes;
		/* 11414 */short[] arrayOfShort = this.autoKeyInfo.tableFormOfUses;
		/* 11415 */int[] arrayOfInt2 = this.autoKeyInfo.columnIndexes;

		/* 11417 */int i = arrayOfInt1.length;

		/* 11420 */int j = this.numberOfBindPositions - i;

		/* 11423 */for (int k = 0; k < i; k++) {
			/* 11425 */int m = j + k;
			/* 11426 */this.currentRowBinders[m] = this.theReturnParamBinder;

			/* 11428 */short s = 0;

			/* 11430 */if ((arrayOfShort != null) && (arrayOfInt2 != null)) {
				/* 11432 */if (arrayOfShort[(arrayOfInt2[k] - 1)] == 2) {
					/* 11435 */s = 2;
					/* 11436 */setFormOfUse(m + 1, s);
				}
			}

			/* 11440 */checkTypeForAutoKey(arrayOfInt1[k]);

			/* 11442 */String str = null;
			/* 11443 */if (arrayOfInt1[k] == 111) {
				/* 11444 */str = this.autoKeyInfo.tableTypeNames[(arrayOfInt2[k] - 1)];
			}
			/* 11446 */registerReturnParameterInternal(m, arrayOfInt1[k], arrayOfInt1[k], -1, s, str);
		}
	}

	void cleanOldTempLobs() {
		/* 11457 */if ((this.m_batchStyle != 1) || (this.currentRank == this.batch - 1)) {
			/* 11461 */super.cleanOldTempLobs();
		}
	}

	class PushedBatch {
		int[] currentBatchCharLens;
		int[] lastBoundCharLens;
		Accessor[] currentBatchBindAccessors;
		boolean lastBoundNeeded;
		boolean need_to_parse;
		boolean current_batch_need_to_prepare_binds;
		int first_row_in_batch;
		int number_of_rows_to_be_bound;
		PushedBatch next;

		PushedBatch() {
		}

	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.OraclePreparedStatement JD-Core Version: 0.6.0
 */