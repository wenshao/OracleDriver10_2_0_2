/*      */package oracle.jdbc.driver;

/*      */
/*      */import java.io.IOException;
/*      */
import java.io.InputStream;
/*      */
import java.sql.SQLException;
/*      */
import oracle.jdbc.oracore.OracleTypeADT;
/*      */
import oracle.net.ns.BreakNetException;

/*      */
/*      */class T4C8Oall extends T4CTTIfun
/*      */{
	/* 122 */static final byte[] EMPTY_BYTES = new byte[0];
	/*      */static final int UOPF_PRS = 1;
	/*      */static final int UOPF_BND = 8;
	/*      */static final int UOPF_EXE = 32;
	/*      */static final int UOPF_FEX = 512;
	/*      */static final int UOPF_FCH = 64;
	/*      */static final int UOPF_CAN = 128;
	/*      */static final int UOPF_COM = 256;
	/*      */static final int UOPF_DSY = 8192;
	/*      */static final int UOPF_SIO = 1024;
	/*      */static final int UOPF_NPL = 32768;
	/*      */static final int UOPF_DFN = 16;
	/* 146 */int receiveState = 0;
	/*      */static final int IDLE_RECEIVE_STATE = 0;
	/*      */static final int ACTIVE_RECEIVE_STATE = 1;
	/*      */static final int READROW_RECEIVE_STATE = 2;
	/*      */static final int STREAM_RECEIVE_STATE = 3;
	/*      */int rowsProcessed;
	/*      */int numberOfDefinePositions;
	/*      */long options;
	/*      */int cursor;
	/* 168 */byte[] sqlStmt = new byte[0];
	/* 169 */final long[] al8i4 = new long[13];
	/*      */
	/* 172 */boolean plsql = false;
	/*      */Accessor[] definesAccessors;
	/*      */int definesLength;
	/*      */Accessor[] outBindAccessors;
	/*      */int numberOfBindPositions;
	/*      */InputStream[][] parameterStream;
	/*      */byte[][][] parameterDatum;
	/*      */OracleTypeADT[][] parameterOtype;
	/*      */short[] bindIndicators;
	/*      */byte[] bindBytes;
	/*      */char[] bindChars;
	/*      */int bindIndicatorSubRange;
	/*      */byte[] tmpBindsByteArray;
	/*      */DBConversion conversion;
	/*      */byte[] ibtBindBytes;
	/*      */char[] ibtBindChars;
	/*      */short[] ibtBindIndicators;
	/* 211 */boolean sendBindsDefinition = false;
	/*      */OracleStatement oracleStatement;
	/*      */short dbCharSet;
	/*      */short NCharSet;
	/*      */T4CTTIrxd rxd;
	/*      */T4C8TTIrxh rxh;
	/*      */T4CTTIoac oac;
	/*      */T4CTTIdcb dcb;
	/*      */T4CTTIofetch ofetch;
	/*      */T4CTTIoexec oexec;
	/*      */T4CTTIfob fob;
	/*      */byte typeOfStatement;
	/* 234 */boolean sendDefines = false;
	/* 235 */int defCols = 0;
	/*      */int rowsToFetch;
	/*      */T4CTTIoac[] oacdefBindsSent;
	/*      */T4CTTIoac[] oacdefDefines;
	/*      */int[] definedColumnSize;
	/*      */int[] definedColumnType;
	/*      */int[] definedColumnFormOfUse;
	/* 1630 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";

	/*      */
	/*      */T4C8Oall(T4CMAREngine paramT4CMAREngine,
			T4CConnection paramT4CConnection, T4CTTIoer paramT4CTTIoer)
	/*      */throws IOException, SQLException
	/*      */{
		/* 253 */super(3, 0, 94);
		/*      */
		/* 255 */setMarshalingEngine(paramT4CMAREngine);
		/*      */
		/* 257 */this.rxh = new T4C8TTIrxh(this.meg);
		/* 258 */this.rxd = new T4CTTIrxd(this.meg);
		/* 259 */this.oer = paramT4CTTIoer;
		/* 260 */this.oac = new T4CTTIoac(this.meg);
		/* 261 */this.dcb = new T4CTTIdcb(this.meg);
		/* 262 */this.ofetch = new T4CTTIofetch(this.meg);
		/* 263 */this.oexec = new T4CTTIoexec(this.meg);
		/* 264 */this.fob = new T4CTTIfob(this.meg);
		/*      */}

	/*      */
	/*      */T4CTTIoac[] marshal(boolean paramBoolean1, boolean paramBoolean2,
			boolean paramBoolean3, boolean paramBoolean4, byte paramByte,
			int paramInt1, byte[] paramArrayOfByte1, int paramInt2,
			Accessor[] paramArrayOfAccessor1, int paramInt3,
			Accessor[] paramArrayOfAccessor2, int paramInt4,
			byte[] paramArrayOfByte2, char[] paramArrayOfChar1,
			short[] paramArrayOfShort1, int paramInt5,
			DBConversion paramDBConversion, byte[] paramArrayOfByte3,
			InputStream[][] paramArrayOfInputStream,
			byte[][][] paramArrayOfByte,
			OracleTypeADT[][] paramArrayOfOracleTypeADT,
			OracleStatement paramOracleStatement, byte[] paramArrayOfByte4,
			char[] paramArrayOfChar2, short[] paramArrayOfShort2,
			T4CTTIoac[] paramArrayOfT4CTTIoac, int[] paramArrayOfInt1,
			int[] paramArrayOfInt2, int[] paramArrayOfInt3)
	/*      */throws SQLException, IOException
	/*      */{
		/* 287 */this.typeOfStatement = paramByte;
		/* 288 */this.cursor = paramInt1;
		/* 289 */this.sqlStmt = (paramBoolean1 ? paramArrayOfByte1
				: EMPTY_BYTES);
		/* 290 */this.rowsToFetch = paramInt2;
		/* 291 */this.outBindAccessors = paramArrayOfAccessor1;
		/* 292 */this.numberOfBindPositions = paramInt3;
		/* 293 */this.definesAccessors = paramArrayOfAccessor2;
		/* 294 */this.definesLength = paramInt4;
		/* 295 */this.bindBytes = paramArrayOfByte2;
		/* 296 */this.bindChars = paramArrayOfChar1;
		/* 297 */this.bindIndicators = paramArrayOfShort1;
		/* 298 */this.bindIndicatorSubRange = paramInt5;
		/* 299 */this.conversion = paramDBConversion;
		/* 300 */this.tmpBindsByteArray = paramArrayOfByte3;
		/* 301 */this.parameterStream = paramArrayOfInputStream;
		/* 302 */this.parameterDatum = paramArrayOfByte;
		/* 303 */this.parameterOtype = paramArrayOfOracleTypeADT;
		/* 304 */this.oracleStatement = paramOracleStatement;
		/* 305 */this.ibtBindBytes = paramArrayOfByte4;
		/* 306 */this.ibtBindChars = paramArrayOfChar2;
		/* 307 */this.ibtBindIndicators = paramArrayOfShort2;
		/* 308 */this.oacdefBindsSent = paramArrayOfT4CTTIoac;
		/* 309 */this.definedColumnType = paramArrayOfInt1;
		/* 310 */this.definedColumnSize = paramArrayOfInt2;
		/* 311 */this.definedColumnFormOfUse = paramArrayOfInt3;
		/*      */
		/* 314 */this.dbCharSet = paramDBConversion.getServerCharSetId();
		/* 315 */this.NCharSet = paramDBConversion.getNCharSetId();
		/*      */
		/* 318 */int i = 0;
		/* 319 */if (this.bindIndicators != null) {
			/* 320 */i = this.bindIndicators[(this.bindIndicatorSubRange + 2)] & 0xFFFF;
			/*      */}
		/*      */
		/* 324 */if (paramArrayOfByte1 == null)
		/*      */{
			/* 328 */DatabaseError.throwSqlException(431);
			/*      */}
		/* 330 */if ((this.typeOfStatement != 2) && (i > 1))
		/*      */{
			/* 335 */DatabaseError.throwSqlException(433);
			/*      */}
		/*      */
		/* 339 */this.rowsProcessed = 0;
		/* 340 */this.options = 0L;
		/* 341 */this.plsql = ((this.typeOfStatement == 1) || (this.typeOfStatement == 4));
		/*      */
		/* 343 */this.sendBindsDefinition = false;
		/*      */
		/* 346 */if (this.receiveState != 0)
		/*      */{
			/* 351 */this.receiveState = 0;
			/* 352 */DatabaseError.throwSqlException(447);
			/*      */}
		/*      */
		/* 355 */this.rxh.init();
		/* 356 */this.rxd.init();
		/* 357 */this.oer.init();
		/*      */
		/* 360 */this.sendDefines = false;
		/* 361 */if ((paramBoolean1) && (paramBoolean2) && (!paramBoolean3)
				&& (this.definedColumnType != null))
		/*      */{
			/* 363 */this.sendDefines = true;
			/* 364 */initDefinesDefinition();
			/*      */}
		/*      */
		/* 368 */if ((this.numberOfBindPositions > 0)
				&& (this.bindIndicators != null))
		/*      */{
			/* 370 */if (this.oacdefBindsSent == null)
				/* 371 */this.oacdefBindsSent = new T4CTTIoac[this.numberOfBindPositions];
			/* 372 */this.sendBindsDefinition = initBindsDefinition(this.oacdefBindsSent);
			/*      */}
		/*      */
		/* 382 */this.options = setOptions(paramBoolean1, paramBoolean2,
				paramBoolean3);
		/*      */
		/* 387 */if ((this.options & 1L) > 0L)
			/* 388 */this.al8i4[0] = 1L;
		/*      */else {
			/* 390 */this.al8i4[0] = 0L;
			/*      */}
		/*      */
		/* 393 */if ((this.plsql) || (this.typeOfStatement == 3))
			/* 394 */this.al8i4[1] = 1L;
		/* 395 */else if (paramBoolean4)
		/*      */{
			/* 397 */if ((paramBoolean3)
					&& (this.oracleStatement.connection.useFetchSizeWithLongColumn))
				/* 398 */this.al8i4[1] = this.rowsToFetch;
			/*      */else
				/* 400 */this.al8i4[1] = 0L;
			/*      */}
		/* 402 */else if (this.typeOfStatement == 2)
		/*      */{
			/* 408 */this.al8i4[1] = i;
			/*      */}
		/* 410 */else if ((paramBoolean3) && (!paramBoolean4))
		/*      */{
			/* 412 */this.al8i4[1] = this.rowsToFetch;
			/*      */}
		/* 414 */else
			this.al8i4[1] = 0L;
		/*      */
		/* 417 */if (this.typeOfStatement == 0)
			/* 418 */this.al8i4[7] = 1L;
		/*      */else {
			/* 420 */this.al8i4[7] = 0L;
			/*      */}
		/* 422 */marshalAll();
		/*      */
		/* 433 */return this.oacdefBindsSent;
		/*      */}

	/*      */
	/*      */void receive()
	/*      */throws SQLException, IOException
	/*      */{
		/* 449 */if ((this.receiveState != 0) && (this.receiveState != 2))
		/*      */{
			/* 456 */DatabaseError.throwSqlException(447);
			/*      */}
		/*      */
		/* 459 */this.receiveState = 1;
		/*      */
		/* 461 */int i = 0;
		/* 462 */int j = 0;
		/* 463 */int k = 0;
		/*      */
		/* 466 */this.rowsProcessed = 0;
		/*      */
		/* 470 */this.rxd.setNumberOfColumns(this.definesLength);
		/*      */while (true)
		/*      */{
			/*      */try
			/*      */{
				/* 478 */int m = this.meg.unmarshalSB1();
				/*      */int i1;
				/*      */int i4;
				/*      */int i5;
				/* 484 */switch (m)
				/*      */{
				/*      */case 21:
					/* 494 */int n = this.meg.unmarshalUB2();
					/*      */
					/* 497 */this.rxd.unmarshalBVC(n);
					/*      */
					/* 499 */break;
				/*      */case 11:
					/* 507 */T4CTTIiov localT4CTTIiov = new T4CTTIiov(this.meg,
							this.rxh, this.rxd);
					/*      */
					/* 509 */localT4CTTIiov.init();
					/* 510 */localT4CTTIiov.unmarshalV10();
					/*      */
					/* 513 */if ((this.oracleStatement.returnParamAccessors != null)
							|| (localT4CTTIiov.isIOVectorEmpty())) {
						/*      */continue;
						/*      */}
					/* 516 */byte[] arrayOfByte = localT4CTTIiov.getIOVector();
					/*      */
					/* 521 */this.outBindAccessors = localT4CTTIiov.processRXD(
							this.outBindAccessors, this.numberOfBindPositions,
							this.bindBytes, this.bindChars,
							this.bindIndicators, this.bindIndicatorSubRange,
							this.conversion, this.tmpBindsByteArray,
							arrayOfByte, this.parameterStream,
							this.parameterDatum, this.parameterOtype,
							this.oracleStatement, null, null, null);
					/*      */
					/* 531 */k = 1;
					/*      */
					/* 533 */break;
				/*      */case 6:
					/* 551 */this.rxh.init();
					/* 552 */this.rxh.unmarshalV10(this.rxd);
					/*      */
					/* 554 */if (this.rxh.uacBufLength <= 0)
					/*      */{
						/*      */continue;
						/*      */}
					/*      */
					/* 559 */DatabaseError.throwSqlException(405);
					/*      */
					/* 562 */j = 1;
					/*      */
					/* 564 */break;
				/*      */case 7:
					/* 578 */if (this.receiveState == 1)
						/*      */continue;
					/* 580 */DatabaseError.throwSqlException(447);
					/*      */
					/* 583 */this.receiveState = 2;
					/*      */
					/* 587 */if ((this.oracleStatement.returnParamAccessors == null)
							|| (this.numberOfBindPositions <= 0)) {
						/*      */continue;
						/*      */}
					/* 590 */i1 = 0;
					/* 591 */int i2 = 0;
					if (i2 >= this.oracleStatement.numberOfBindPositions)
						/*      */continue;
					/* 593 */Accessor localAccessor = this.oracleStatement.returnParamAccessors[i2];
					/* 594 */if (localAccessor == null)
						/*      */continue;
					/* 596 */i4 = (int) this.meg.unmarshalUB4();
					/* 597 */if (i1 != 0)
						/*      */continue;
					/* 599 */this.oracleStatement.rowsDmlReturned = i4;
					/* 600 */this.oracleStatement.allocateDmlReturnStorage();
					/* 601 */this.oracleStatement.setupReturnParamAccessors();
					/* 602 */i1 = 1;
					/*      */
					/* 605 */i5 = 0;
					if (i5 >= i4)
					/*      */{
						/*      */continue;
						/*      */}
					/*      */
					/* 610 */localAccessor.unmarshalOneRow();
					/*      */
					/* 605 */i5++;
					continue;
					/*      */
					/* 591 */i2++;
					continue;
					/*      */
					/* 613 */this.oracleStatement.returnParamsFetched = true;
					continue;
					/*      */
					/* 616 */if ((k == 0)
							&& ((this.outBindAccessors == null) || (this.definesAccessors != null)))
					/*      */{
						/*      */continue;
						/*      */}
					/* 620 */if (!this.rxd.unmarshal(this.outBindAccessors,
							this.numberOfBindPositions))
						/*      */continue;
					/* 622 */this.receiveState = 3;
					/*      */
					/* 624 */return;
					/*      */
					/* 628 */if (!this.rxd.unmarshal(this.definesAccessors,
							this.definesLength))
					/*      */{
						/*      */continue;
						/*      */}
					/*      */
					/* 634 */this.receiveState = 3;
					/*      */
					/* 636 */return;
					/*      */
					/* 641 */this.receiveState = 1;
					/*      */
					/* 643 */break;
				/*      */case 8:
					/* 655 */if (i == 0)
					/*      */{
						/*      */continue;
						/*      */}
					/*      */
					/* 660 */DatabaseError.throwSqlException(401);
					/*      */
					/* 664 */i1 = this.meg.unmarshalUB2();
					/* 665 */int[] arrayOfInt = new int[i1];
					/*      */
					/* 667 */int i3 = 0;
					if (i3 < i1) {
						/* 668 */arrayOfInt[i3] = (int) this.meg.unmarshalUB4();
						/*      */
						/* 667 */i3++;
						continue;
						/*      */}
					/*      */
					/* 672 */this.cursor = arrayOfInt[2];
					/*      */
					/* 675 */this.meg.unmarshalUB2();
					/*      */
					/* 677 */i3 = this.meg.unmarshalUB2();
					/*      */
					/* 679 */if (i3 <= 0)
						/*      */continue;
					/* 681 */i4 = 0;
					if (i4 >= i3)
						/*      */continue;
					/* 683 */i5 = (int) this.meg.unmarshalUB4();
					/*      */
					/* 685 */this.meg.unmarshalDALC();
					/*      */
					/* 687 */int i6 = this.meg.unmarshalUB2();
					/*      */
					/* 681 */i4++;
					continue;
					/*      */
					/* 691 */i = 1;
					/*      */
					/* 693 */break;
				/*      */case 16:
					/* 701 */this.dcb.init(this.oracleStatement, 0);
					/*      */
					/* 703 */this.definesAccessors = this.dcb
							.receive(this.definesAccessors);
					/* 704 */this.numberOfDefinePositions = this.dcb.numuds;
					/* 705 */this.definesLength = this.numberOfDefinePositions;
					/*      */
					/* 707 */this.rxd
							.setNumberOfColumns(this.numberOfDefinePositions);
					/*      */
					/* 709 */break;
				/*      */case 19:
					/* 712 */this.fob.marshal();
					/* 713 */break;
				/*      */case 4:
					/* 725 */this.oer.init();
					/*      */
					/* 727 */this.cursor = this.oer.unmarshal();
					/*      */
					/* 731 */this.rowsProcessed = this.oer.getCurRowNumber();
					/*      */
					/* 734 */if ((this.typeOfStatement == 0)
							&& ((this.typeOfStatement != 0) || (this.oer.retCode == 1403)))
					/*      */{
						/*      */continue;
						/*      */}
					/*      */
					/*      */try
					/*      */{
						/* 743 */this.oer.processError(this.oracleStatement);
						/*      */}
					/*      */catch (SQLException localSQLException)
					/*      */{
						/* 751 */this.receiveState = 0;
						/*      */
						/* 753 */throw localSQLException;
						/*      */}
					/*      */
					/* 757 */break;
				/*      */case 5:
					/*      */
				case 9:
					/*      */
				case 10:
					/*      */
				case 12:
					/*      */
				case 13:
					/*      */
				case 14:
					/*      */
				case 15:
					/*      */
				case 17:
					/*      */
				case 18:
					/*      */
				case 20:
					/*      */
				default:
					/* 764 */DatabaseError.throwSqlException(401);
					/*      */}
				/*      */
				/* 770 */continue;
			} catch (BreakNetException localBreakNetException) {
				/*      */}
			/*      */}
		/* 773 */if (this.receiveState != 1)
		/*      */{
			/* 779 */DatabaseError.throwSqlException(447);
			/*      */}
		/*      */
		/* 784 */this.receiveState = 0;
		/*      */}

	/*      */
	/*      */int getCursorId()
	/*      */{
		/* 798 */return this.cursor;
		/*      */}

	/*      */
	/*      */void continueReadRow(int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 816 */this.receiveState = 2;
		/*      */
		/* 818 */if (this.rxd.unmarshal(this.definesAccessors, paramInt,
				this.definesLength))
		/*      */{
			/* 824 */this.receiveState = 3;
			/*      */
			/* 828 */return;
			/*      */}
		/*      */
		/* 832 */receive();
		/*      */}

	/*      */
	/*      */int getNumRows()
	/*      */{
		/* 849 */int i = 0;
		/*      */
		/* 851 */if (this.receiveState == 3) {
			/* 852 */i = -2;
			/*      */}
		/*      */else {
			/* 855 */switch (this.typeOfStatement)
			/*      */{
			/*      */case 1:
				/*      */
			case 2:
				/*      */
			case 3:
				/*      */
			case 4:
				/* 865 */i = this.rowsProcessed;
				/*      */
				/* 867 */break;
			/*      */case 0:
				/* 870 */i = (this.definesAccessors != null)
						&& (this.definesLength > 0) ? this.definesAccessors[0].lastRowProcessed
						: 0;
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 880 */return i;
		/*      */}

	/*      */
	/*      */void marshalAll()
	/*      */throws SQLException, IOException
	/*      */{
		/* 896 */if (((this.options & 0x40) != 0L)
				&& ((this.options & 0x20) == 0L) && ((this.options & 1L) == 0L)
				&& ((this.options & 0x8) == 0L)
				&& (!this.oracleStatement.needToSendOalToFetch))
		/*      */{
			/* 900 */this.ofetch.marshal(this.cursor, (int) this.al8i4[1]);
			/*      */}
		/* 902 */else if ((this.meg.versionNumber >= 10000)
				&& ((this.options & 0x20) != 0L) && ((this.options & 1L) == 0L)
				&& ((this.options & 0x40) == 0L)
				&& ((this.options & 0x8) == 0L) && (this.typeOfStatement != 2)
				&& (this.typeOfStatement != 1) && (this.typeOfStatement != 4))
		/*      */{
			/* 909 */int i = 0;
			/*      */
			/* 911 */if ((this.options & 0x100) != 0L) {
				/* 912 */i = 1;
				/*      */}
			/* 914 */this.oexec.marshal(this.cursor, (int) this.al8i4[1], i);
			/*      */
			/* 916 */if ((this.numberOfBindPositions > 0)
					&& (this.bindIndicators != null))
			/*      */{
				/* 920 */int[] arrayOfInt2 = new int[this.numberOfBindPositions];
				/*      */
				/* 922 */for (int k = 0; k < this.numberOfBindPositions; k++)
				/*      */{
					/* 924 */arrayOfInt2[k] = this.oacdefBindsSent[k].oacmxl;
					/*      */}
				/*      */
				/* 927 */marshalBinds(arrayOfInt2);
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 932 */if (this.oracleStatement.needToSendOalToFetch) {
				/* 933 */this.oracleStatement.needToSendOalToFetch = false;
				/*      */}
			/*      */
			/* 937 */marshalPisdef();
			/*      */
			/* 943 */this.meg.marshalCHR(this.sqlStmt);
			/*      */
			/* 946 */this.meg.marshalUB4Array(this.al8i4);
			/*      */
			/* 949 */int[] arrayOfInt1 = new int[this.numberOfBindPositions];
			/*      */
			/* 951 */for (int j = 0; j < this.numberOfBindPositions; j++)
			/*      */{
				/* 953 */arrayOfInt1[j] = this.oacdefBindsSent[j].oacmxl;
				/*      */}
			/*      */
			/* 957 */if (((this.options & 0x8) != 0L)
					&& (this.numberOfBindPositions > 0)
					&& (this.bindIndicators != null)
					&& (this.sendBindsDefinition))
			/*      */{
				/* 959 */marshalBindsTypes(this.oacdefBindsSent);
				/*      */}
			/*      */
			/* 963 */if ((this.meg.versionNumber >= 9000) && (this.sendDefines)) {
				/* 964 */for (j = 0; j < this.defCols; j++) {
					/* 965 */this.oacdefDefines[j].marshal();
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 971 */if (((this.options & 0x20) != 0L)
					&& (this.numberOfBindPositions > 0)
					&& (this.bindIndicators != null))
			/*      */{
				/* 973 */marshalBinds(arrayOfInt1);
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalPisdef()
	/*      */throws IOException, SQLException
	/*      */{
		/* 991 */super.marshalFunHeader();
		/*      */
		/* 1000 */this.meg.marshalUB4(this.options);
		/*      */
		/* 1005 */this.meg.marshalSWORD(this.cursor);
		/*      */
		/* 1011 */if (this.sqlStmt.length == 0)
			/* 1012 */this.meg.marshalNULLPTR();
		/*      */else {
			/* 1014 */this.meg.marshalPTR();
			/*      */}
		/*      */
		/* 1017 */this.meg.marshalSWORD(this.sqlStmt.length);
		/*      */
		/* 1023 */if (this.al8i4.length == 0)
			/* 1024 */this.meg.marshalNULLPTR();
		/*      */else {
			/* 1026 */this.meg.marshalPTR();
			/*      */}
		/*      */
		/* 1029 */this.meg.marshalSWORD(this.al8i4.length);
		/*      */
		/* 1036 */this.meg.marshalNULLPTR();
		/*      */
		/* 1039 */this.meg.marshalNULLPTR();
		/*      */
		/* 1042 */this.meg.marshalUB4(0L);
		/*      */
		/* 1045 */this.meg.marshalUB4(0L);
		/*      */
		/* 1048 */if ((this.typeOfStatement != 1)
				&& (this.typeOfStatement != 4))
		/*      */{
			/* 1050 */this.meg.marshalUB4(2147483647L);
			/*      */}
		/* 1052 */else
			this.meg.marshalUB4(32760L);
		/*      */
		/* 1057 */if (((this.options & 0x8) != 0L)
				&& (this.numberOfBindPositions > 0)
				&& (this.sendBindsDefinition))
		/*      */{
			/* 1062 */this.meg.marshalPTR();
			/*      */
			/* 1065 */this.meg.marshalSWORD(this.numberOfBindPositions);
			/*      */}
		/*      */else
		/*      */{
			/* 1071 */this.meg.marshalNULLPTR();
			/*      */
			/* 1074 */this.meg.marshalSWORD(0);
			/*      */}
		/*      */
		/* 1078 */this.meg.marshalNULLPTR();
		/*      */
		/* 1081 */this.meg.marshalNULLPTR();
		/*      */
		/* 1084 */this.meg.marshalNULLPTR();
		/*      */
		/* 1087 */this.meg.marshalNULLPTR();
		/*      */
		/* 1090 */this.meg.marshalNULLPTR();
		/*      */
		/* 1098 */if (this.meg.versionNumber >= 9000)
		/*      */{
			/* 1100 */if ((this.defCols > 0) && (this.sendDefines))
			/*      */{
				/* 1104 */this.meg.marshalPTR();
				/*      */
				/* 1107 */this.meg.marshalSWORD(this.defCols);
				/*      */}
			/*      */else
			/*      */{
				/* 1113 */this.meg.marshalNULLPTR();
				/*      */
				/* 1116 */this.meg.marshalSWORD(0);
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */boolean initBindsDefinition(T4CTTIoac[] paramArrayOfT4CTTIoac)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1131 */int i = 0;
		/*      */
		/* 1133 */if (paramArrayOfT4CTTIoac.length != this.numberOfBindPositions)
		/*      */{
			/* 1135 */i = 1;
			/* 1136 */paramArrayOfT4CTTIoac = new T4CTTIoac[this.numberOfBindPositions];
			/*      */}
		/*      */
		/* 1140 */short[] arrayOfShort = this.bindIndicators;
		/*      */
		/* 1143 */int k = 0;
		/*      */
		/* 1146 */int n = 0;
		/*      */
		/* 1149 */for (int i1 = 0; i1 < this.numberOfBindPositions; i1++)
		/*      */{
			/* 1159 */int j = this.bindIndicatorSubRange + 3 + 10 * i1;
			/*      */
			/* 1163 */short s = arrayOfShort[(j + 9)];
			/*      */
			/* 1166 */int m = arrayOfShort[(j + 0)] & 0xFFFF;
			/*      */
			/* 1171 */if ((m == 8) || (m == 24))
			/*      */{
				/* 1173 */if (this.plsql)
					/* 1174 */k = 32760;
				/*      */else
					/* 1176 */k = 2147483647;
				/* 1177 */this.oac.init((short) m, k, this.dbCharSet,
						this.NCharSet, s);
				/*      */}
			/* 1180 */else if (m == 998)
			/*      */{
				/* 1182 */if ((this.outBindAccessors != null)
						&& (this.outBindAccessors[i1] != null))
				/*      */{
					/* 1184 */this.oac
							.init((PlsqlIndexTableAccessor) this.outBindAccessors[i1]);
					/* 1185 */n++;
					/*      */}
				/* 1187 */else if (this.ibtBindIndicators[(6 + n * 8)] != 0)
				/*      */{
					/* 1189 */int i2 = this.ibtBindIndicators[(6 + n * 8)];
					/* 1190 */int i4 = this.ibtBindIndicators[(6 + n * 8 + 2)] << 16
							& 0xFFFF000
							| this.ibtBindIndicators[(6 + n * 8 + 3)];
					/*      */
					/* 1194 */k = this.ibtBindIndicators[(6 + n * 8 + 1)];
					/*      */
					/* 1196 */this.oac.initIbt((short) i2, i4, k);
					/*      */
					/* 1198 */n++;
					/*      */}
				/*      */else {
					/* 1201 */DatabaseError
							.throwSqlException(
									"INTERNAL ERROR: Binding PLSQL index-by table but no type defined",
									null, -1);
					/*      */}
				/*      */
				/*      */}
			/* 1205 */else if ((m == 109) || (m == 111))
			/*      */{
				/* 1207 */if ((this.outBindAccessors != null)
						&& (this.outBindAccessors[i1] != null))
				/*      */{
					/* 1209 */if (this.outBindAccessors[i1].internalOtype != null) {
						/* 1210 */this.oac
								.init((OracleTypeADT) ((TypeAccessor) this.outBindAccessors[i1]).internalOtype,
										m, m == 109 ? 11 : 4000);
						/*      */}
					/*      */
					/*      */}
				/* 1215 */else if ((this.parameterOtype != null)
						&& (this.parameterOtype[0] != null))
				/*      */{
					/* 1217 */this.oac.init(this.parameterOtype[0][i1], m,
							m == 109 ? 11 : 4000);
					/*      */}
				/*      */else
				/*      */{
					/* 1225 */DatabaseError
							.throwSqlException(
									"INTERNAL ERROR: Binding NAMED_TYPE but no type defined",
									null, -1);
					/*      */}
				/*      */
				/*      */}
			/* 1230 */else if (m == 994)
			/*      */{
				/* 1232 */int[] arrayOfInt = this.oracleStatement.returnParamMeta;
				/* 1233 */m = arrayOfInt[(3 + i1 * 3 + 0)];
				/*      */
				/* 1238 */k = arrayOfInt[(3 + i1 * 3 + 2)];
				/*      */
				/* 1243 */if ((m == 109) || (m == 111))
				/*      */{
					/* 1245 */TypeAccessor localTypeAccessor = (TypeAccessor) this.oracleStatement.returnParamAccessors[i1];
					/*      */
					/* 1248 */this.oac.init(
							(OracleTypeADT) localTypeAccessor.internalOtype,
							(short) m, m == 109 ? 11 : 4000);
					/*      */}
				/*      */else
				/*      */{
					/* 1256 */this.oac.init((short) m, k, this.dbCharSet,
							this.NCharSet, s);
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 1270 */k = arrayOfShort[(j + 1)] & 0xFFFF;
				/*      */
				/* 1275 */if (k == 0)
				/*      */{
					/* 1279 */if ((this.typeOfStatement == 1)
							|| ((this.oracleStatement.connection.versionNumber >= 10200) && (this.typeOfStatement == 4)))
					/*      */{
						/* 1283 */k = 32512;
						/*      */}
					/* 1285 */else if (this.typeOfStatement == 4)
					/*      */{
						/* 1291 */int i3 = arrayOfShort[(j + 2)] & 0xFFFF;
						/*      */
						/* 1295 */k = i3 > 4001 ? i3 : 4001;
						/*      */}
					/*      */else
					/*      */{
						/* 1300 */k = arrayOfShort[(j + 2)] & 0xFFFF;
						/*      */
						/* 1304 */if (m == 996) {
							/* 1305 */k *= 2;
							/*      */}
						/* 1307 */else if (k > 1) {
							/* 1308 */k--;
							/*      */}
						/*      */
						/* 1314 */if (s == 2)
						/*      */{
							/* 1316 */k *= this.conversion.maxNCharSize;
							/*      */}
						/* 1329 */else if ((((T4CConnection) this.oracleStatement.connection).retainV9BehaviorForLong)
								&& (k <= 4000))
						/*      */{
							/* 1333 */k = Math.min(k
									* this.conversion.sMaxCharSize, 4000);
							/*      */}
						/*      */else
						/*      */{
							/* 1338 */k *= this.conversion.sMaxCharSize;
							/*      */}
						/*      */
						/*      */}
					/*      */
					/* 1347 */if (k == 0) {
						/* 1348 */k = 32;
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 1354 */if ((m == 9) || (m == 996)) {
					/* 1355 */m = 1;
					/*      */}
				/* 1357 */this.oac.init((short) m, k, this.dbCharSet,
						this.NCharSet, s);
				/*      */}
			/*      */
			/* 1362 */if ((paramArrayOfT4CTTIoac[i1] != null)
					&& (this.oac.isOldSufficient(paramArrayOfT4CTTIoac[i1])))
			/*      */{
				/*      */continue;
				/*      */}
			/*      */
			/* 1367 */T4CTTIoac localT4CTTIoac = new T4CTTIoac(this.oac);
			/*      */
			/* 1369 */paramArrayOfT4CTTIoac[i1] = localT4CTTIoac;
			/* 1370 */i = 1;
			/*      */}
		/*      */
		/* 1376 */if (i != 0) {
			/* 1377 */this.oracleStatement.nbPostPonedColumns[0] = 0;
			/*      */}
		/* 1379 */return i;
		/*      */}

	/*      */
	/*      */void initDefinesDefinition()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1391 */this.defCols = 0;
		/* 1392 */for (int i = 0; i < this.definedColumnType.length; i++)
		/*      */{
			/* 1394 */if (this.definedColumnType[i] == 0)
				/*      */break;
			/* 1396 */this.defCols += 1;
			/*      */}
		/* 1398 */this.oacdefDefines = new T4CTTIoac[this.defCols];
		/* 1399 */for (i = 0; i < this.oacdefDefines.length; i++)
		/*      */{
			/* 1401 */this.oacdefDefines[i] = new T4CTTIoac(this.meg);
			/* 1402 */short s1 = (short) this.oracleStatement
					.getInternalType(this.definedColumnType[i]);
			/* 1403 */int j = 2147483647;
			/*      */
			/* 1405 */short s2 = 1;
			/* 1406 */if ((this.definedColumnFormOfUse != null)
					&& (this.definedColumnFormOfUse.length > i)
					&& (this.definedColumnFormOfUse[i] == 2))
			/*      */{
				/* 1410 */s2 = 2;
				/*      */}
			/* 1412 */if (s1 == 8) {
				/* 1413 */s1 = 1;
				/* 1414 */} else if (s1 == 24) {
				/* 1415 */s1 = 23;
				/* 1416 */} else if ((s1 == 1) || (s1 == 96))
			/*      */{
				/* 1419 */s1 = 1;
				/*      */
				/* 1425 */j = 4000 * this.conversion.sMaxCharSize;
				/*      */
				/* 1427 */if ((this.definedColumnSize != null)
						&& (this.definedColumnSize.length > i)
						&& (this.definedColumnSize[i] > 0))
				/*      */{
					/* 1431 */j = this.definedColumnSize[i]
							* this.conversion.sMaxCharSize;
					/*      */}
				/* 1433 */} else if (s1 == 23) {
				/* 1434 */j = 4000;
				/*      */}
			/* 1436 */this.oacdefDefines[i].init(s1, j, this.dbCharSet,
					this.NCharSet, s2);
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalBindsTypes(T4CTTIoac[] paramArrayOfT4CTTIoac)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1451 */if (paramArrayOfT4CTTIoac == null) {
			/* 1452 */return;
			/*      */}
		/* 1454 */for (int i = 0; i < paramArrayOfT4CTTIoac.length; i++)
		/*      */{
			/* 1456 */paramArrayOfT4CTTIoac[i].marshal();
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalBinds(int[] paramArrayOfInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1478 */int i = this.bindIndicators[(this.bindIndicatorSubRange + 2)] & 0xFFFF;
		/*      */
		/* 1486 */for (int j = 0; j < i; j++)
		/*      */{
			/* 1488 */int k = this.oracleStatement.firstRowInBatch + j;
			/* 1489 */InputStream[] arrayOfInputStream = null;
			/* 1490 */if (this.parameterStream != null)
				/* 1491 */arrayOfInputStream = this.parameterStream[k];
			/* 1492 */byte[][] arrayOfByte = (byte[][]) null;
			/* 1493 */if (this.parameterDatum != null)
				/* 1494 */arrayOfByte = this.parameterDatum[k];
			/* 1495 */OracleTypeADT[] arrayOfOracleTypeADT = null;
			/* 1496 */if (this.parameterOtype != null) {
				/* 1497 */arrayOfOracleTypeADT = this.parameterOtype[k];
				/*      */}
			/*      */
			/* 1500 */this.rxd.marshal(this.bindBytes, this.bindChars,
					this.bindIndicators, this.bindIndicatorSubRange,
					this.tmpBindsByteArray, this.conversion,
					arrayOfInputStream, arrayOfByte, arrayOfOracleTypeADT,
					this.ibtBindBytes, this.ibtBindChars,
					this.ibtBindIndicators, null, j, paramArrayOfInt,
					this.plsql, this.oracleStatement.returnParamMeta,
					this.oracleStatement.nbPostPonedColumns,
					this.oracleStatement.indexOfPostPonedColumn);
			/*      */}
		/*      */}

	/*      */
	/*      */long setOptions(boolean paramBoolean1, boolean paramBoolean2,
			boolean paramBoolean3)
	/*      */throws SQLException
	/*      */{
		/* 1521 */long l = 0L;
		/*      */
		/* 1524 */if ((paramBoolean1) && (!paramBoolean2) && (!paramBoolean3)) {
			/* 1525 */l |= 1L;
			/* 1526 */} else if ((paramBoolean1) && (paramBoolean2)
				&& (!paramBoolean3)) {
			/* 1527 */l = 32801L;
		} else {
			/* 1528 */if ((paramBoolean2) && (paramBoolean3))
			/*      */{
				/* 1530 */if (paramBoolean1) {
					/* 1531 */l |= 1L;
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 1536 */switch (this.typeOfStatement)
			/*      */{
			/*      */case 0:
				/* 1540 */l |= 32864L;
				/*      */
				/* 1542 */break;
			/*      */case 1:
				/*      */
			case 4:
				/* 1547 */if (this.numberOfBindPositions > 0)
				/*      */{
					/* 1550 */l |= 0x420 | (this.oracleStatement.connection.autoCommitSet ? 256
							: 0);
					/*      */
					/* 1553 */if (!this.sendBindsDefinition)
						break;
					/* 1554 */l |= 8L;
					/*      */}
				/*      */else {
					/* 1557 */l |= 0x20 | (this.oracleStatement.connection.autoCommitSet ? 256
							: 0);
					/*      */}
				/*      */
				/* 1561 */break;
			/*      */case 2:
				/*      */
			case 3:
				/* 1568 */if (this.oracleStatement.returnParamAccessors != null) {
					/* 1569 */l |= 0x420 | (this.oracleStatement.connection.autoCommitSet ? 256
							: 0);
					/*      */}
				/*      */else {
					/* 1572 */l |= 0x8020 | (this.oracleStatement.connection.autoCommitSet ? 256
							: 0);
					/*      */}
				/*      */
				/* 1575 */break;
			/*      */default:
				/* 1582 */DatabaseError.throwSqlException(432);
				break;
				/*      */
				/* 1585 */if ((!paramBoolean1) && (!paramBoolean2)
						&& (paramBoolean3)) {
					/* 1586 */l = 32832L;
					/*      */}
				/*      */else
				/*      */{
					/* 1593 */DatabaseError.throwSqlException(432);
					/*      */}
				/*      */}
			/*      */}
		/* 1597 */if ((this.typeOfStatement != 1)
				&& (this.typeOfStatement != 4))
		/*      */{
			/* 1602 */if ((paramBoolean1) || (paramBoolean2)
					|| (!paramBoolean3))
			/*      */{
				/* 1609 */if ((this.numberOfBindPositions > 0)
						&& (this.sendBindsDefinition)) {
					/* 1610 */l |= 8L;
					/*      */}
				/*      */}
			/* 1613 */if ((this.meg.versionNumber >= 9000)
					&& (this.sendDefines))
			/*      */{
				/* 1615 */l |= 16L;
				/*      */}
			/*      */}
		/*      */
		/* 1619 */l &= -1L;
		/*      */
		/* 1625 */return l;
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4C8Oall JD-Core
 * Version: 0.6.0
 */