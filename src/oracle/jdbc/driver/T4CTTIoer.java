/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.io.PrintStream;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CTTIoer extends T4CTTIMsg
/*     */{
	/* 35 */final int MAXERRBUF = 512;
	/*     */long curRowNumber;
	/*     */int retCode;
	/*     */int arrayElemWError;
	/*     */int arrayElemErrno;
	/*     */int currCursorID;
	/*     */short errorPosition;
	/*     */short sqlType;
	/*     */byte oerFatal;
	/*     */short flags;
	/*     */short userCursorOpt;
	/*     */short upiParam;
	/*     */short warningFlag;
	/*     */int osError;
	/*     */short stmtNumber;
	/*     */short callNumber;
	/*     */int pad1;
	/*     */long successIters;
	/*     */int partitionId;
	/*     */int tableId;
	/*     */int slotNumber;
	/*     */long rba;
	/*     */long blockNumber;
	/* 61 */int warnLength = 0;
	/* 62 */int warnFlag = 0;
	/*     */
	/* 66 */int[] errorLength = new int[1];
	/*     */byte[] errorMsg;
	/*     */T4CConnection connection;
	/*     */static final int ORA1403 = 1403;
	/* 421 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";

	/*     */
	/*     */T4CTTIoer(T4CMAREngine paramT4CMAREngine)
	/*     */{
		/* 85 */setMarshalingEngine(paramT4CMAREngine);
		/*     */}

	/*     */
	/*     */T4CTTIoer(T4CMAREngine paramT4CMAREngine,
			T4CConnection paramT4CConnection)
	/*     */{
		/* 92 */setMarshalingEngine(paramT4CMAREngine);
		/*     */
		/* 94 */this.connection = paramT4CConnection;
		/*     */}

	/*     */
	/*     */void init()
	/*     */{
		/* 104 */this.retCode = 0;
		/* 105 */this.errorMsg = null;
		/*     */}

	/*     */
	/*     */int unmarshal()
	/*     */throws IOException, SQLException
	/*     */{
		/* 124 */if (this.meg.versionNumber >= 10000)
		/*     */{
			/* 126 */short s = (short) this.meg.unmarshalUB2();
			/*     */
			/* 128 */this.connection.endToEndECIDSequenceNumber = s;
			/*     */}
		/*     */
		/* 131 */this.curRowNumber = this.meg.unmarshalUB4();
		/* 132 */this.retCode = this.meg.unmarshalUB2();
		/* 133 */this.arrayElemWError = this.meg.unmarshalUB2();
		/* 134 */this.arrayElemErrno = this.meg.unmarshalUB2();
		/* 135 */this.currCursorID = this.meg.unmarshalUB2();
		/* 136 */this.errorPosition = this.meg.unmarshalSB2();
		/* 137 */this.sqlType = this.meg.unmarshalUB1();
		/* 138 */this.oerFatal = this.meg.unmarshalSB1();
		/* 139 */this.flags = this.meg.unmarshalSB2();
		/* 140 */this.userCursorOpt = this.meg.unmarshalSB2();
		/* 141 */this.upiParam = this.meg.unmarshalUB1();
		/* 142 */this.warningFlag = this.meg.unmarshalUB1();
		/*     */
		/* 145 */this.rba = this.meg.unmarshalUB4();
		/* 146 */this.partitionId = this.meg.unmarshalUB2();
		/* 147 */this.tableId = this.meg.unmarshalUB1();
		/* 148 */this.blockNumber = this.meg.unmarshalUB4();
		/* 149 */this.slotNumber = this.meg.unmarshalUB2();
		/*     */
		/* 151 */this.osError = this.meg.unmarshalSWORD();
		/* 152 */this.stmtNumber = this.meg.unmarshalUB1();
		/* 153 */this.callNumber = this.meg.unmarshalUB1();
		/* 154 */this.pad1 = this.meg.unmarshalUB2();
		/* 155 */this.successIters = this.meg.unmarshalUB4();
		/*     */
		/* 158 */if (this.retCode != 0)
		/*     */{
			/* 160 */this.errorMsg = this.meg.unmarshalCLRforREFS();
			/* 161 */this.errorLength[0] = this.errorMsg.length;
			/*     */}
		/*     */
		/* 168 */return this.currCursorID;
		/*     */}

	/*     */
	/*     */void unmarshalWarning()
	/*     */throws IOException, SQLException
	/*     */{
		/* 178 */this.retCode = this.meg.unmarshalUB2();
		/* 179 */this.warnLength = this.meg.unmarshalUB2();
		/* 180 */this.warnFlag = this.meg.unmarshalUB2();
		/*     */
		/* 183 */if ((this.retCode != 0) && (this.warnLength > 0))
		/*     */{
			/* 185 */this.errorMsg = this.meg.unmarshalCHR(this.warnLength);
			/* 186 */this.errorLength[0] = this.warnLength;
			/*     */}
		/*     */}

	/*     */
	/*     */void print(int paramInt1, int paramInt2, int paramInt3)
	/*     */throws SQLException
	/*     */{
		/* 257 */System.out.println("**** Retcode is " + this.retCode);
		/*     */
		/* 259 */if (this.retCode != 0) {
			/* 260 */System.out.println("**** Error Message: "
					+ this.meg.conv.CharBytesToString(this.errorMsg,
							this.errorLength[0], true));
			/*     */}
		/* 265 */else if (this.warnFlag != 0)
			/* 266 */OracleLog.print(
					this,
					paramInt1,
					paramInt2,
					paramInt3,
					"Warning Message: "
							+ this.meg.conv.CharBytesToString(this.errorMsg,
									this.warnLength, true));
		/*     */}

	/*     */
	/*     */void processError()
	/*     */throws SQLException
	/*     */{
		/* 278 */processError(true);
		/*     */}

	/*     */
	/*     */void processError(boolean paramBoolean) throws SQLException
	/*     */{
		/* 283 */processError(paramBoolean, null);
		/*     */}

	/*     */
	/*     */void processError(OracleStatement paramOracleStatement)
			throws SQLException
	/*     */{
		/* 288 */processError(true, paramOracleStatement);
		/*     */}

	/*     */
	/*     */void processError(boolean paramBoolean,
			OracleStatement paramOracleStatement)
	/*     */throws SQLException
	/*     */{
		/* 303 */if (this.retCode != 0)
		/*     */{
			/* 311 */switch (this.retCode)
			/*     */{
			/*     */case 28:
				/*     */
			case 600:
				/*     */
			case 1012:
				/*     */
			case 3113:
				/*     */
			case 3114:
				/* 323 */this.connection.internalClose();
				/*     */}
			/*     */
			/* 328 */if (paramBoolean)
			/*     */{
				/* 331 */DatabaseError.throwSqlException(this.meg.conv
						.CharBytesToString(this.errorMsg, this.errorLength[0],
								true), DatabaseError
						.ErrorToSQLState(this.retCode), this.retCode);
				/*     */}
			/*     */else
			/*     */{
				/* 335 */return;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 341 */if (!paramBoolean) {
			/* 342 */return;
			/*     */}
		/*     */
		/* 351 */if ((this.warningFlag & 0x1) == 1)
		/*     */{
			/* 353 */int i = this.warningFlag & 0xFFFFFFFE;
			/*     */
			/* 356 */if (((i & 0x20) == 32) || ((i & 0x4) == 4)) {
				/* 357 */throw DatabaseError.newSqlException(110);
				/*     */}
			/*     */}
		/*     */
		/* 361 */if ((this.connection != null)
				&& (this.connection.plsqlCompilerWarnings))
		/*     */{
			/* 363 */if ((this.flags & 0x4) == 4)
				/* 364 */paramOracleStatement.foundPlsqlCompilerWarning();
			/*     */}
		/*     */}

	/*     */
	/*     */void processWarning()
	/*     */throws SQLException
	/*     */{
		/* 379 */if (this.retCode != 0)
		/*     */{
			/* 389 */throw DatabaseError.newSqlWarning(
					this.meg.conv.CharBytesToString(this.errorMsg,
							this.errorLength[0], true), DatabaseError
							.ErrorToSQLState(this.retCode), this.retCode);
			/*     */}
		/*     */}

	/*     */
	/*     */int getCurRowNumber()
	/*     */throws SQLException
	/*     */{
		/* 404 */return (int) this.curRowNumber;
		/*     */}

	/*     */
	/*     */int getRetCode()
	/*     */{
		/* 416 */return this.retCode;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CTTIoer
 * JD-Core Version: 0.6.0
 */