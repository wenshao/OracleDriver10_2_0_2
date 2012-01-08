/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;
/*     */
import oracle.net.ns.BreakNetException;

/*     */
/*     */class T4CTTIOtxen extends T4CTTIfun
/*     */{
	/*     */static final int OTXCOMIT = 1;
	/*     */static final int OTXABORT = 2;
	/*     */static final int OTXPREPA = 3;
	/*     */static final int OTXFORGT = 4;
	/*     */static final int OTXRECOV = 5;
	/*     */static final int OTXMLPRE = 6;
	/*     */static final int K2CMDprepare = 0;
	/*     */static final int K2CMDrqcommit = 1;
	/*     */static final int K2CMDcommit = 2;
	/*     */static final int K2CMDabort = 3;
	/*     */static final int K2CMDrdonly = 4;
	/*     */static final int K2CMDforget = 5;
	/*     */static final int K2CMDrecovered = 7;
	/*     */static final int K2CMDtimeout = 8;
	/*     */static final int K2STAidle = 0;
	/*     */static final int K2STAcollecting = 1;
	/*     */static final int K2STAprepared = 2;
	/*     */static final int K2STAcommitted = 3;
	/*     */static final int K2STAhabort = 4;
	/*     */static final int K2STAhcommit = 5;
	/*     */static final int K2STAhdamage = 6;
	/*     */static final int K2STAtimeout = 7;
	/*     */static final int K2STAinactive = 9;
	/*     */static final int K2STAactive = 10;
	/*     */static final int K2STAptprepared = 11;
	/*     */static final int K2STAptcommitted = 12;
	/*     */static final int K2STAmax = 13;
	/*     */T4CConnection connection;
	/* 280 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";

	/*     */
	/*     */T4CTTIOtxen(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer,
			T4CConnection paramT4CConnection)
	/*     */throws SQLException
	/*     */{
		/* 129 */super(3, 0, 104);
		/*     */
		/* 131 */this.oer = paramT4CTTIoer;
		/*     */
		/* 133 */setMarshalingEngine(paramT4CMAREngine);
		/*     */
		/* 135 */this.connection = paramT4CConnection;
		/*     */}

	/*     */
	/*     */void marshal(int paramInt1, byte[] paramArrayOfByte1,
			byte[] paramArrayOfByte2, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5, int paramInt6)
	/*     */throws IOException, SQLException
	/*     */{
		/* 146 */if ((paramInt1 != 1) && (paramInt1 != 2) && (paramInt1 != 3)
				&& (paramInt1 != 4) && (paramInt1 != 5) && (paramInt1 != 6))
		/*     */{
			/* 151 */throw new SQLException("Invalid operation.");
			/*     */}
		/* 153 */super.marshalFunHeader();
		/*     */
		/* 156 */int i = paramInt1;
		/*     */
		/* 158 */this.meg.marshalSWORD(i);
		/*     */
		/* 161 */if (paramArrayOfByte1 == null)
			/* 162 */this.meg.marshalNULLPTR();
		/*     */else {
			/* 164 */this.meg.marshalPTR();
			/*     */}
		/*     */
		/* 167 */if (paramArrayOfByte1 == null)
			/* 168 */this.meg.marshalUB4(0L);
		/*     */else {
			/* 170 */this.meg.marshalUB4(paramArrayOfByte1.length);
			/*     */}
		/*     */
		/* 173 */this.meg.marshalUB4(paramInt2);
		/*     */
		/* 176 */this.meg.marshalUB4(paramInt3);
		/*     */
		/* 179 */this.meg.marshalUB4(paramInt4);
		/*     */
		/* 182 */if (paramArrayOfByte2 != null)
			/* 183 */this.meg.marshalPTR();
		/*     */else {
			/* 185 */this.meg.marshalNULLPTR();
			/*     */}
		/*     */
		/* 188 */if (paramArrayOfByte2 != null)
			/* 189 */this.meg.marshalUB4(paramArrayOfByte2.length);
		/*     */else {
			/* 191 */this.meg.marshalUB4(0L);
			/*     */}
		/*     */
		/* 194 */this.meg.marshalUWORD(paramInt5);
		/*     */
		/* 197 */this.meg.marshalUB4(paramInt6);
		/*     */
		/* 200 */this.meg.marshalPTR();
		/*     */
		/* 204 */if (paramArrayOfByte1 != null) {
			/* 205 */this.meg.marshalB1Array(paramArrayOfByte1);
			/*     */}
		/* 207 */if (paramArrayOfByte2 != null)
			/* 208 */this.meg.marshalB1Array(paramArrayOfByte2);
		/*     */}

	/*     */
	/*     */int receive(int[] paramArrayOfInt)
	/*     */throws IOException, SQLException
	/*     */{
		/* 221 */int j = -1;
		/*     */
		/* 223 */paramArrayOfInt[0] = -1;
		/*     */while (true)
		/*     */{
			/*     */try
			/*     */{
				/* 229 */int i = this.meg.unmarshalSB1();
				/*     */
				/* 231 */switch (i)
				/*     */{
				/*     */case 8:
					/* 235 */j = (int) this.meg.unmarshalUB4();
					/*     */
					/* 237 */break;
				/*     */case 9:
					/* 240 */if (this.meg.versionNumber < 10000)
						/*     */continue;
					/* 242 */short s = (short) this.meg.unmarshalUB2();
					/*     */
					/* 244 */this.connection.endToEndECIDSequenceNumber = s;
					/*     */
					/* 247 */break;
				/*     */case 4:
					/* 250 */this.oer.init();
					/* 251 */this.oer.unmarshal();
					/*     */
					/* 255 */this.oer.processError(false);
					/*     */
					/* 257 */paramArrayOfInt[0] = this.oer.retCode;
					/*     */
					/* 259 */break;
				/*     */default:
					/* 264 */DatabaseError.throwSqlException(401);
					/*     */}
				/*     */
				/* 270 */continue;
				/*     */}
			/*     */catch (BreakNetException localBreakNetException) {
				/*     */}
			/*     */}
		/* 275 */return j;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CTTIOtxen
 * JD-Core Version: 0.6.0
 */