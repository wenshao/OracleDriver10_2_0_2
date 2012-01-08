/*      */package oracle.jdbc.driver;

/*      */
/*      */import java.io.IOException;
/*      */
import java.sql.SQLException;
/*      */
import oracle.jdbc.oracore.OracleTypeADT;

/*      */
/*      */class T2CCallableStatement extends OracleCallableStatement
/*      */{
	/* 40 */T2CConnection connection = null;
	/* 41 */int userResultSetType = -1;
	/* 42 */int userResultSetConcur = -1;
	/*      */
	/* 48 */static int T2C_EXTEND_BUFFER = -3;
	/*      */
	/* 61 */long[] t2cOutput = new long[10];
	/*      */
	/* 1287 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";

	/*      */
	/*      */T2CCallableStatement(T2CConnection paramT2CConnection,
			String paramString, int paramInt1, int paramInt2, int paramInt3,
			int paramInt4)
	/*      */throws SQLException
	/*      */{
		/* 70 */super(paramT2CConnection, paramString, paramInt1, paramInt2,
				paramInt3, paramInt4);
		/*      */
		/* 73 */this.userResultSetType = paramInt3;
		/* 74 */this.userResultSetConcur = paramInt4;
		/*      */
		/* 77 */this.connection = paramT2CConnection;
		/*      */}

	/*      */
	/*      */String bytes2String(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 93 */byte[] arrayOfByte = new byte[paramInt2];
		/*      */
		/* 95 */System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0,
				paramInt2);
		/*      */
		/* 97 */return this.connection.conversion.CharBytesToString(
				arrayOfByte, paramInt2);
		/*      */}

	/*      */
	/*      */void processDescribeData()
	/*      */throws SQLException
	/*      */{
		/* 113 */this.described = true;
		/* 114 */this.describedWithNames = true;
		/*      */
		/* 125 */if ((this.accessors == null)
				|| (this.numberOfDefinePositions > this.accessors.length)) {
			/* 126 */this.accessors = new Accessor[this.numberOfDefinePositions];
			/*      */}
		/*      */
		/* 142 */int i = this.connection.queryMetaData1Offset;
		/* 143 */int j = this.connection.queryMetaData2Offset;
		/* 144 */short[] arrayOfShort = this.connection.queryMetaData1;
		/* 145 */byte[] arrayOfByte = this.connection.queryMetaData2;
		/*      */
		/* 147 */for (int k = 0; k < this.numberOfDefinePositions;)
		/*      */{
			/* 150 */int m = arrayOfShort[(i + 0)];
			/* 151 */int n = arrayOfShort[(i + 1)];
			/* 152 */int i1 = arrayOfShort[(i + 11)];
			/* 153 */boolean bool = arrayOfShort[(i + 2)] != 0;
			/* 154 */int i2 = arrayOfShort[(i + 3)];
			/* 155 */int i3 = arrayOfShort[(i + 4)];
			/* 156 */int i4 = 0;
			/* 157 */int i5 = 0;
			/* 158 */int i6 = 0;
			/* 159 */short s = arrayOfShort[(i + 5)];
			/* 160 */int i7 = arrayOfShort[(i + 6)];
			/* 161 */String str1 = bytes2String(arrayOfByte, j, i7);
			/* 162 */int i8 = arrayOfShort[(i + 12)];
			/* 163 */String str2 = null;
			/* 164 */OracleTypeADT localOracleTypeADT = null;
			/*      */
			/* 166 */j += i7;
			/*      */
			/* 168 */if (i8 > 0)
			/*      */{
				/* 170 */str2 = bytes2String(arrayOfByte, j, i8);
				/* 171 */j += i8;
				/* 172 */localOracleTypeADT = new OracleTypeADT(str2,
						this.connection);
				/* 173 */localOracleTypeADT.tdoCState = ((arrayOfShort[(i + 7)] & 0xFFFF) << 48
						| (arrayOfShort[(i + 8)] & 0xFFFF) << 32
						| (arrayOfShort[(i + 9)] & 0xFFFF) << 16 | arrayOfShort[(i + 10)] & 0xFFFF);
				/*      */}
			/*      */
			/* 181 */Object localObject = this.accessors[k];
			/*      */
			/* 183 */if ((localObject != null)
					&& (!((Accessor) localObject).useForDescribeIfPossible(m,
							n, bool, i4, i2, i3, i5, i6, s, str2)))
			/*      */{
				/* 187 */localObject = null;
				/*      */}
			/* 189 */if (localObject == null)
			/*      */{
				/* 191 */switch (m)
				/*      */{
				/*      */case 1:
					/* 195 */localObject = new VarcharAccessor(this, n, bool,
							i4, i2, i3, i5, i6, s);
					/*      */
					/* 199 */if (i1 <= 0)
						break;
					/* 200 */((Accessor) localObject).setDisplaySize(i1);
					break;
				/*      */case 96:
					/* 205 */localObject = new CharAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 209 */if (i1 <= 0)
						break;
					/* 210 */((Accessor) localObject).setDisplaySize(i1);
					break;
				/*      */case 2:
					/* 215 */localObject = new NumberAccessor(this, n, bool,
							i4, i2, i3, i5, i6, s);
					/*      */
					/* 219 */break;
				/*      */case 23:
					/* 222 */localObject = new RawAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 226 */break;
				/*      */case 100:
					/* 229 */localObject = new BinaryFloatAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 233 */break;
				/*      */case 101:
					/* 236 */localObject = new BinaryDoubleAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 240 */break;
				/*      */case 8:
					/* 243 */localObject = new LongAccessor(this, k + 1, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 250 */this.rowPrefetch = 1;
					/*      */
					/* 252 */break;
				/*      */case 24:
					/* 255 */localObject = new LongRawAccessor(this, k + 1, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 262 */this.rowPrefetch = 1;
					/*      */
					/* 264 */break;
				/*      */case 104:
					/* 267 */localObject = new RowidAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 271 */break;
				/*      */case 102:
					/*      */
				case 116:
					/* 276 */localObject = new T2CResultSetAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 280 */break;
				/*      */case 12:
					/* 283 */localObject = new DateAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 287 */break;
				/*      */case 180:
					/* 290 */localObject = new TimestampAccessor(this, n, bool,
							i4, i2, i3, i5, i6, s);
					/*      */
					/* 294 */break;
				/*      */case 181:
					/* 297 */localObject = new TimestamptzAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 301 */break;
				/*      */case 231:
					/* 304 */localObject = new TimestampltzAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 308 */break;
				/*      */case 182:
					/* 311 */localObject = new IntervalymAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 315 */break;
				/*      */case 183:
					/* 318 */localObject = new IntervaldsAccessor(this, n,
							bool, i4, i2, i3, i5, i6, s);
					/*      */
					/* 322 */break;
				/*      */case 112:
					/* 325 */localObject = new ClobAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 329 */break;
				/*      */case 113:
					/* 332 */localObject = new BlobAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 336 */break;
				/*      */case 114:
					/* 339 */localObject = new BfileAccessor(this, n, bool, i4,
							i2, i3, i5, i6, s);
					/*      */
					/* 343 */break;
				/*      */case 109:
					/* 346 */localObject = new NamedTypeAccessor(this, n, bool,
							i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
					/*      */
					/* 351 */break;
				/*      */case 111:
					/* 354 */localObject = new RefTypeAccessor(this, n, bool,
							i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
					/*      */
					/* 359 */break;
				/*      */default:
					/* 365 */throw new SQLException(
							"Unknown or unimplemented accessor type: " + m);
					/*      */}
				/*      */
				/* 373 */this.accessors[k] = localObject;
				/*      */}
			/* 375 */else if (localOracleTypeADT != null)
			/*      */{
				/* 377 */((Accessor) localObject).initMetadata();
				/*      */}
			/*      */
			/* 380 */((Accessor) localObject).columnName = str1;
			/*      */
			/* 148 */k++;
			i += 13;
			/*      */}
		/*      */}

	/*      */
	/*      */void doDescribe(boolean paramBoolean)
	/*      */throws SQLException
	/*      */{
		/* 403 */if (this.closed) {
			/* 404 */DatabaseError.throwSqlException(9);
			/*      */}
		/* 406 */if (this.described == true)
		/*      */{
			/* 408 */return;
			/*      */}
		/*      */
		/* 411 */if (!this.isOpen)
		/*      */{
			/* 413 */DatabaseError.throwSqlException(144);
			/*      */}
		/*      */
		/*      */int i;
		/*      */do
		/*      */{
			/* 420 */i = 0;
			/*      */
			/* 425 */this.numberOfDefinePositions = T2CStatement.t2cDescribe(
					this.c_state, this.connection.queryMetaData1,
					this.connection.queryMetaData2,
					this.connection.queryMetaData1Offset,
					this.connection.queryMetaData2Offset,
					this.connection.queryMetaData1Size,
					this.connection.queryMetaData2Size);
			/*      */
			/* 438 */if (this.numberOfDefinePositions == -1)
			/*      */{
				/* 440 */this.connection
						.checkError(this.numberOfDefinePositions);
				/*      */}
			/*      */
			/* 444 */if (this.numberOfDefinePositions != T2C_EXTEND_BUFFER)
				/*      */continue;
			/* 446 */i = 1;
			/*      */
			/* 450 */this.connection.reallocateQueryMetaData(
					this.connection.queryMetaData1Size * 2,
					this.connection.queryMetaData2Size * 2);
			/*      */}
		/*      */
		/* 454 */while (i != 0);
		/*      */
		/* 456 */processDescribeData();
		/*      */}

	/*      */void executeForDescribe() throws SQLException {
		/* 498 */this.t2cOutput[0] = 0L;
		/* 499 */this.t2cOutput[2] = 0L;
		/*      */
		/* 501 */boolean bool1 = !this.described;
		/* 502 */boolean bool2 = false;
		/*      */int i;
		/*      */do {
			/* 507 */i = 0;
			/*      */
			/* 510 */if (this.connection.endToEndAnyChanged)
			/*      */{
				/* 512 */pushEndToEndValues();
				/*      */
				/* 514 */this.connection.endToEndAnyChanged = false;
				/*      */}
			/*      */
			/* 518 */byte[] arrayOfByte = this.sqlObject.getSqlBytes(
					this.processEscapes, this.convertNcharLiterals);
			/* 519 */int j = T2CStatement.t2cParseExecuteDescribe(this,
					this.c_state, this.numberOfBindPositions,
					this.numberOfBindRowsAllocated, this.firstRowInBatch,
					this.currentRowBindAccessors != null, this.needToParse,
					bool1, bool2, arrayOfByte, arrayOfByte.length,
					this.sqlKind, this.rowPrefetch, this.batch,
					this.bindIndicators, this.bindIndicatorOffset,
					this.bindBytes, this.bindChars, this.bindByteOffset,
					this.bindCharOffset, this.ibtBindIndicators,
					this.ibtBindIndicatorOffset, this.ibtBindIndicatorSize,
					this.ibtBindBytes, this.ibtBindChars,
					this.ibtBindByteOffset, this.ibtBindCharOffset,
					this.returnParamMeta, this.connection.queryMetaData1,
					this.connection.queryMetaData2,
					this.connection.queryMetaData1Offset,
					this.connection.queryMetaData2Offset,
					this.connection.queryMetaData1Size,
					this.connection.queryMetaData2Size, this.preparedAllBinds,
					this.preparedCharBinds, this.outBindAccessors,
					this.parameterDatum, this.t2cOutput, this.defineBytes,
					this.accessorByteOffset, this.defineChars,
					this.accessorCharOffset, this.defineIndicators,
					this.accessorShortOffset,
					this.connection.plsqlCompilerWarnings);
			/*      */
			/* 568 */this.validRows = (int) this.t2cOutput[1];
			/*      */
			/* 570 */if (j == -1)
			/*      */{
				/* 572 */this.connection.checkError(j);
				/*      */}
			/* 574 */else if (j == T2C_EXTEND_BUFFER)
			/*      */{
				/* 576 */j = this.connection.queryMetaData1Size * 2;
				/*      */}
			/*      */
			/* 581 */if (this.t2cOutput[3] != 0L)
			/*      */{
				/* 583 */foundPlsqlCompilerWarning();
				/*      */}
			/* 585 */else if (this.t2cOutput[2] != 0L)
			/*      */{
				/* 587 */this.sqlWarning = this.connection.checkError(1,
						this.sqlWarning);
				/*      */}
			/*      */
			/* 592 */this.connection.endToEndECIDSequenceNumber = (short) (int) this.t2cOutput[4];
			/*      */
			/* 595 */this.needToParse = false;
			/* 596 */bool2 = true;
			/*      */
			/* 598 */if (this.sqlKind == 0)
			/*      */{
				/* 600 */this.numberOfDefinePositions = j;
				/*      */
				/* 602 */if (this.numberOfDefinePositions <= this.connection.queryMetaData1Size)
					/*      */continue;
				/* 604 */i = 1;
				/* 605 */bool2 = true;
				/*      */
				/* 608 */this.connection.reallocateQueryMetaData(
						this.numberOfDefinePositions,
						this.numberOfDefinePositions * 8);
				/*      */}
			/*      */else
			/*      */{
				/* 615 */this.numberOfDefinePositions = 0;
				/* 616 */this.validRows = j;
				/*      */}
			/*      */}
		/* 619 */while (i != 0);
		/*      */
		/* 621 */processDescribeData();
		/*      */}

	/*      */
	/*      */void pushEndToEndValues()
	/*      */throws SQLException
	/*      */{
		/* 633 */T2CConnection localT2CConnection = this.connection;
		/* 634 */byte[] arrayOfByte1 = new byte[0];
		/* 635 */byte[] arrayOfByte2 = new byte[0];
		/* 636 */byte[] arrayOfByte3 = new byte[0];
		/* 637 */byte[] arrayOfByte4 = new byte[0];
		/*      */
		/* 639 */if (localT2CConnection.endToEndValues != null)
		/*      */{
			/*      */String str;
			/* 641 */if (localT2CConnection.endToEndHasChanged[0] != 0)
			/*      */{
				/* 643 */str = localT2CConnection.endToEndValues[0];
				/*      */
				/* 645 */if (str != null) {
					/* 646 */arrayOfByte1 = DBConversion
							.stringToDriverCharBytes(str,
									localT2CConnection.m_clientCharacterSet);
					/*      */}
				/*      */
				/* 649 */localT2CConnection.endToEndHasChanged[0] = false;
				/*      */}
			/*      */
			/* 652 */if (localT2CConnection.endToEndHasChanged[1] != 0)
			/*      */{
				/* 654 */str = localT2CConnection.endToEndValues[1];
				/*      */
				/* 656 */if (str != null) {
					/* 657 */arrayOfByte2 = DBConversion
							.stringToDriverCharBytes(str,
									localT2CConnection.m_clientCharacterSet);
					/*      */}
				/*      */
				/* 660 */localT2CConnection.endToEndHasChanged[1] = false;
				/*      */}
			/*      */
			/* 663 */if (localT2CConnection.endToEndHasChanged[2] != 0)
			/*      */{
				/* 665 */str = localT2CConnection.endToEndValues[2];
				/*      */
				/* 667 */if (str != null) {
					/* 668 */arrayOfByte3 = DBConversion
							.stringToDriverCharBytes(str,
									localT2CConnection.m_clientCharacterSet);
					/*      */}
				/*      */
				/* 671 */localT2CConnection.endToEndHasChanged[2] = false;
				/*      */}
			/*      */
			/* 674 */if (localT2CConnection.endToEndHasChanged[3] != 0)
			/*      */{
				/* 676 */str = localT2CConnection.endToEndValues[3];
				/*      */
				/* 678 */if (str != null) {
					/* 679 */arrayOfByte4 = DBConversion
							.stringToDriverCharBytes(str,
									localT2CConnection.m_clientCharacterSet);
					/*      */}
				/*      */
				/* 682 */localT2CConnection.endToEndHasChanged[3] = false;
				/*      */}
			/*      */
			/* 685 */T2CStatement.t2cEndToEndUpdate(this.c_state, arrayOfByte1,
					arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length,
					arrayOfByte3, arrayOfByte3.length, arrayOfByte4,
					arrayOfByte4.length,
					localT2CConnection.endToEndECIDSequenceNumber);
			/*      */}
		/*      */}

	/*      */
	/*      */void executeForRows(boolean paramBoolean)
	/*      */throws SQLException
	/*      */{
		/* 743 */if (this.connection.endToEndAnyChanged)
		/*      */{
			/* 745 */pushEndToEndValues();
			/*      */
			/* 747 */this.connection.endToEndAnyChanged = false;
			/*      */}
		/*      */
		/* 751 */if (!paramBoolean)
		/*      */{
			/* 759 */if (this.numberOfDefinePositions > 0)
			/*      */{
				/* 761 */doDefineExecuteFetch();
				/*      */}
			/*      */else
			/*      */{
				/* 765 */executeForDescribe();
				/*      */}
			/*      */}
		/* 768 */else if (this.numberOfDefinePositions > 0) {
			/* 769 */doDefineFetch();
			/*      */}
		/*      */
		/* 773 */this.needToPrepareDefineBuffer = false;
		/*      */}

	/*      */
	/*      */void setupForDefine()
	/*      */throws SQLException
	/*      */{
		/* 784 */if (this.numberOfDefinePositions > this.connection.queryMetaData1Size)
		/*      */{
			/* 787 */this.connection.reallocateQueryMetaData(
					this.connection.queryMetaData1Size * 2,
					this.connection.queryMetaData2Size * 2 * 8);
			/*      */}
		/*      */
		/* 792 */short[] arrayOfShort = this.connection.queryMetaData1;
		/* 793 */int i = this.connection.queryMetaData1Offset;
		/*      */
		/* 796 */for (int j = 0; j < this.numberOfDefinePositions;)
		/*      */{
			/* 799 */Accessor localAccessor = this.accessors[j];
			/*      */
			/* 801 */if (localAccessor == null) {
				/* 802 */DatabaseError.throwSqlException(21);
				/*      */}
			/* 804 */arrayOfShort[(i + 0)] = (short) localAccessor.defineType;
			/*      */
			/* 806 */arrayOfShort[(i + 11)] = (short) localAccessor.charLength;
			/*      */
			/* 808 */arrayOfShort[(i + 1)] = (short) localAccessor.byteLength;
			/*      */
			/* 810 */arrayOfShort[(i + 5)] = localAccessor.formOfUse;
			/*      */
			/* 813 */if (localAccessor.internalOtype != null)
			/*      */{
				/* 815 */long l = ((OracleTypeADT) localAccessor.internalOtype)
						.getTdoCState();
				/*      */
				/* 818 */arrayOfShort[(i + 7)] = (short) (int) ((l & 0x0) >> 48);
				/*      */
				/* 820 */arrayOfShort[(i + 8)] = (short) (int) ((l & 0x0) >> 32);
				/*      */
				/* 822 */arrayOfShort[(i + 9)] = (short) (int) ((l & 0xFFFF0000) >> 16);
				/*      */
				/* 824 */arrayOfShort[(i + 10)] = (short) (int) (l & 0xFFFF);
				/*      */}
			/* 797 */j++;
			i += 13;
			/*      */}
		/*      */}

	/*      */
	/*      */void doDefineFetch()
	/*      */throws SQLException
	/*      */{
		/* 832 */if (!this.needToPrepareDefineBuffer) {
			/* 833 */throw new Error(
					"doDefineFetch called when needToPrepareDefineBuffer=false "
							+ this.sqlObject.getSql(this.processEscapes,
									this.convertNcharLiterals));
			/*      */}
		/*      */
		/* 836 */setupForDefine();
		/*      */
		/* 838 */this.t2cOutput[2] = 0L;
		/* 839 */this.validRows = T2CStatement.t2cDefineFetch(this.c_state,
				this.rowPrefetch, this.connection.queryMetaData1,
				this.connection.queryMetaData2,
				this.connection.queryMetaData1Offset,
				this.connection.queryMetaData2Offset, this.accessors,
				this.defineBytes, this.accessorByteOffset, this.defineChars,
				this.accessorCharOffset, this.defineIndicators,
				this.accessorShortOffset, this.t2cOutput);
		/*      */
		/* 848 */if (this.validRows == -1) {
			/* 849 */this.connection.checkError(this.validRows);
			/*      */}
		/*      */
		/* 852 */if (this.t2cOutput[2] != 0L)
		/*      */{
			/* 854 */this.sqlWarning = this.connection.checkError(1,
					this.sqlWarning);
			/*      */}
		/*      */}

	/*      */
	/*      */void doDefineExecuteFetch()
	/*      */throws SQLException
	/*      */{
		/* 865 */short[] arrayOfShort = null;
		/*      */
		/* 867 */if ((this.needToPrepareDefineBuffer) || (this.needToParse))
		/*      */{
			/* 869 */setupForDefine();
			/*      */
			/* 871 */arrayOfShort = this.connection.queryMetaData1;
			/*      */}
		/*      */
		/* 874 */this.t2cOutput[0] = 0L;
		/* 875 */this.t2cOutput[2] = 0L;
		/*      */
		/* 877 */byte[] arrayOfByte = this.sqlObject.getSqlBytes(
				this.processEscapes, this.convertNcharLiterals);
		/*      */
		/* 879 */this.validRows = T2CStatement.t2cDefineExecuteFetch(this,
				this.c_state, this.numberOfDefinePositions,
				this.numberOfBindPositions, this.numberOfBindRowsAllocated,
				this.firstRowInBatch, this.currentRowBindAccessors != null,
				this.needToParse, arrayOfByte, arrayOfByte.length,
				this.sqlKind, this.rowPrefetch, this.batch,
				this.bindIndicators, this.bindIndicatorOffset, this.bindBytes,
				this.bindChars, this.bindByteOffset, this.bindCharOffset,
				arrayOfShort, this.connection.queryMetaData2,
				this.connection.queryMetaData1Offset,
				this.connection.queryMetaData2Offset, this.preparedAllBinds,
				this.preparedCharBinds, this.outBindAccessors,
				this.parameterDatum, this.t2cOutput, this.defineBytes,
				this.accessorByteOffset, this.defineChars,
				this.accessorCharOffset, this.defineIndicators,
				this.accessorShortOffset);
		/*      */
		/* 915 */if (this.validRows == -1) {
			/* 916 */this.connection.checkError(this.validRows);
			/*      */}
		/* 918 */if (this.t2cOutput[2] != 0L) {
			/* 919 */this.sqlWarning = this.connection.checkError(1,
					this.sqlWarning);
			/*      */}
		/*      */
		/* 924 */this.connection.endToEndECIDSequenceNumber = (short) (int) this.t2cOutput[4];
		/*      */
		/* 926 */this.needToParse = false;
		/*      */}

	/*      */
	/*      */void fetch()
	/*      */throws SQLException
	/*      */{
		/* 971 */if (this.numberOfDefinePositions > 0)
		/*      */{
			/* 973 */if (this.needToPrepareDefineBuffer) {
				/* 974 */doDefineFetch();
				/*      */}
			/*      */else {
				/* 977 */this.t2cOutput[2] = 0L;
				/* 978 */this.validRows = T2CStatement.t2cFetch(this.c_state,
						this.needToPrepareDefineBuffer, this.rowPrefetch,
						this.accessors, this.defineBytes,
						this.accessorByteOffset, this.defineChars,
						this.accessorCharOffset, this.defineIndicators,
						this.accessorShortOffset, this.t2cOutput);
				/*      */
				/* 984 */if (this.validRows == -1) {
					/* 985 */this.connection.checkError(this.validRows);
					/*      */}
				/* 987 */if (this.t2cOutput[2] != 0L)
				/*      */{
					/* 989 */this.sqlWarning = this.connection.checkError(1,
							this.sqlWarning);
					/*      */}
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */void doClose()
	/*      */throws SQLException
	/*      */{
		/* 1027 */if (this.defineBytes != null)
		/*      */{
			/* 1029 */this.defineBytes = null;
			/* 1030 */this.accessorByteOffset = 0;
			/*      */}
		/*      */
		/* 1033 */if (this.defineChars != null)
		/*      */{
			/* 1035 */this.defineChars = null;
			/* 1036 */this.accessorCharOffset = 0;
			/*      */}
		/*      */
		/* 1039 */if (this.defineIndicators != null)
		/*      */{
			/* 1041 */this.defineIndicators = null;
			/* 1042 */this.accessorShortOffset = 0;
			/*      */}
		/*      */
		/* 1046 */int i = T2CStatement.t2cCloseStatement(this.c_state);
		/*      */
		/* 1048 */if (i != 0) {
			/* 1049 */this.connection.checkError(i);
			/*      */}
		/* 1051 */this.t2cOutput = null;
		/*      */}

	/*      */
	/*      */void closeQuery()
	/*      */throws SQLException
	/*      */{
		/* 1073 */if (this.streamList != null)
		/*      */{
			/* 1075 */while (this.nextStream != null)
			/*      */{
				/*      */try
				/*      */{
					/* 1079 */this.nextStream.close();
					/*      */}
				/*      */catch (IOException localIOException)
				/*      */{
					/* 1083 */DatabaseError.throwSqlException(localIOException);
					/*      */}
				/*      */
				/* 1086 */this.nextStream = this.nextStream.nextStream;
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, short paramShort, String paramString,
			boolean paramBoolean)
	/*      */throws SQLException
	/*      */{
		/*      */Object localObject;
		/* 1108 */if (paramInt1 == 109)
		/*      */{
			/* 1110 */if (paramString == null) {
				/* 1111 */if (paramBoolean) {
					/* 1112 */DatabaseError.throwSqlException(12, "sqlType="
							+ paramInt2);
					/*      */}
				/*      */else {
					/* 1115 */DatabaseError.throwSqlException(60,
							"Unable to resolve type \"null\"");
					/*      */}
				/*      */}
			/* 1118 */localObject = new T2CNamedTypeAccessor(this, paramString,
					paramShort, paramInt2, paramBoolean, paramInt3 - 1);
			/*      */
			/* 1121 */((Accessor) localObject).initMetadata();
			/*      */
			/* 1123 */return localObject;
			/*      */}
		/* 1125 */if ((paramInt1 == 116) || (paramInt1 == 102))
		/*      */{
			/* 1128 */if ((paramBoolean) && (paramString != null)) {
				/* 1129 */DatabaseError.throwSqlException(12, "sqlType="
						+ paramInt2);
				/*      */}
			/*      */
			/* 1132 */localObject = new T2CResultSetAccessor(this, paramInt4,
					paramShort, paramInt2, paramBoolean);
			/*      */
			/* 1135 */return localObject;
			/*      */}
		/*      */
		/* 1138 */return (Accessor) super.allocateAccessor(paramInt1,
				paramInt2, paramInt3, paramInt4, paramShort, paramString,
				paramBoolean);
		/*      */}

	/*      */
	/*      */void closeUsedStreams(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1144 */while ((this.nextStream != null)
				&& (this.nextStream.columnIndex < paramInt))
		/*      */{
			/*      */try
			/*      */{
				/* 1151 */this.nextStream.close();
				/*      */}
			/*      */catch (IOException localIOException1)
			/*      */{
				/* 1155 */DatabaseError.throwSqlException(localIOException1);
				/*      */}
			/*      */
			/* 1158 */this.nextStream = this.nextStream.nextStream;
			/*      */}
		/*      */
		/* 1161 */if (this.nextStream != null)
			/*      */try
			/*      */{
				/* 1164 */this.nextStream.needBytes();
				/*      */}
			/*      */catch (IOException localIOException2)
			/*      */{
				/* 1168 */DatabaseError.throwSqlException(localIOException2);
				/*      */}
		/*      */}

	/*      */
	/*      */void fetchDmlReturnParams()
	/*      */throws SQLException
	/*      */{
		/* 1175 */this.rowsDmlReturned = T2CStatement
				.t2cGetRowsDmlReturned(this.c_state);
		/*      */
		/* 1177 */if (this.rowsDmlReturned != 0)
		/*      */{
			/* 1179 */allocateDmlReturnStorage();
			/*      */
			/* 1181 */int i = T2CStatement.t2cFetchDmlReturnParams(
					this.c_state, this.returnParamAccessors,
					this.returnParamBytes, this.returnParamChars,
					this.returnParamIndicators);
			/*      */}
		/*      */
		/* 1190 */this.returnParamsFetched = true;
		/*      */}

	/*      */
	/*      */void initializeIndicatorSubRange()
	/*      */{
		/* 1202 */this.bindIndicatorSubRange = (this.numberOfBindPositions * 5);
		/*      */}

	/*      */
	/*      */void prepareBindPreambles(int paramInt1, int paramInt2)
	/*      */{
		/* 1213 */int i = this.bindIndicatorSubRange;
		/*      */
		/* 1215 */initializeIndicatorSubRange();
		/*      */
		/* 1217 */int j = this.bindIndicatorSubRange;
		/*      */
		/* 1219 */this.bindIndicatorSubRange = i;
		/*      */
		/* 1221 */int k = this.bindIndicatorSubRange - j;
		/* 1222 */OracleTypeADT[] arrayOfOracleTypeADT = this.parameterOtype == null ? null
				: this.parameterOtype[this.firstRowInBatch];
		/*      */
		/* 1226 */for (int m = 0; m < this.numberOfBindPositions; m++)
		/*      */{
			/* 1229 */Binder localBinder = this.lastBinders[m];
			/*      */OracleTypeADT localOracleTypeADT;
			/*      */int n;
			/* 1232 */if (localBinder == this.theReturnParamBinder)
			/*      */{
				/* 1234 */localOracleTypeADT = (OracleTypeADT) this.returnParamAccessors[m].internalOtype;
				/* 1235 */n = 0;
				/*      */}
			/*      */else
			/*      */{
				/* 1239 */localOracleTypeADT = arrayOfOracleTypeADT == null ? null
						: arrayOfOracleTypeADT[m];
				/*      */
				/* 1241 */if (this.outBindAccessors == null) {
					/* 1242 */n = 0;
					/*      */}
				/*      */else {
					/* 1245 */Accessor localAccessor = this.outBindAccessors[m];
					/*      */
					/* 1247 */if (localAccessor == null) {
						/* 1248 */n = 0;
						/* 1249 */} else if (localBinder == this.theOutBinder)
					/*      */{
						/* 1251 */n = 1;
						/*      */
						/* 1253 */if (localOracleTypeADT == null)
							/* 1254 */localOracleTypeADT = (OracleTypeADT) localAccessor.internalOtype;
						/*      */}
					/*      */else {
						/* 1257 */n = 2;
						/*      */}
					/*      */}
				/* 1260 */if (localBinder == this.theSetCHARBinder) {
					/* 1261 */n = (short) (n | 0x4);
					/*      */}
				/*      */}
			/* 1264 */this.bindIndicators[(k++)] = n;
			/*      */
			/* 1266 */if (localOracleTypeADT != null)
			/*      */{
				/* 1268 */long l = localOracleTypeADT.getTdoCState();
				/*      */
				/* 1270 */this.bindIndicators[(k + 0)] = (short) (int) (l >> 48 & 0xFFFF);
				/*      */
				/* 1272 */this.bindIndicators[(k + 1)] = (short) (int) (l >> 32 & 0xFFFF);
				/*      */
				/* 1274 */this.bindIndicators[(k + 2)] = (short) (int) (l >> 16 & 0xFFFF);
				/*      */
				/* 1276 */this.bindIndicators[(k + 3)] = (short) (int) (l & 0xFFFF);
				/*      */}
			/*      */
			/* 1279 */k += 4;
			/*      */}
		/*      */}

	/*      */
	/*      */void registerOutParameterInternal(int paramInt1, int paramInt2,
			int paramInt3, int paramInt4, String paramString)
	/*      */throws SQLException
	/*      */{
		/* 1303 */int i = paramInt1 - 1;
		/*      */
		/* 1305 */if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
			/* 1306 */DatabaseError.throwSqlException(3);
			/*      */}
		/* 1308 */int j = getInternalType(paramInt2);
		/*      */
		/* 1310 */if (j == 995) {
			/* 1311 */DatabaseError.throwSqlException(4);
			/*      */}
		/* 1313 */resetBatch();
		/*      */
		/* 1315 */this.currentRowNeedToPrepareBinds = true;
		/*      */
		/* 1317 */if (this.currentRowBindAccessors == null) {
			/* 1318 */this.currentRowBindAccessors = new Accessor[this.numberOfBindPositions];
			/*      */}
		/*      */
		/* 1321 */switch (paramInt2)
		/*      */{
		/*      */case -4:
			/*      */
		case -3:
			/*      */
		case -1:
			/*      */
		case 1:
			/*      */
		case 12:
			/* 1328 */break;
		/*      */default:
			/* 1331 */paramInt4 = 0;
			/*      */}
		/*      */
		/* 1335 */this.currentRowBindAccessors[i] = allocateAccessor(j,
				paramInt2, paramInt1, paramInt4, this.currentRowFormOfUse[i],
				paramString, true);
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.T2CCallableStatement JD-Core Version: 0.6.0
 */