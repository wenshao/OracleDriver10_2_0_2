/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;
/*     */
import oracle.net.ns.BreakNetException;

/*     */
/*     */class T4CTTIk2rpc extends T4CTTIfun
/*     */{
	/*     */T4CConnection connection;
	/*     */static final int K2RPClogon = 1;
	/*     */static final int K2RPCbegin = 2;
	/*     */static final int K2RPCend = 3;
	/*     */static final int K2RPCrecover = 4;
	/*     */static final int K2RPCsession = 5;
	/* 161 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";

	/*     */
	/*     */T4CTTIk2rpc(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer,
			T4CConnection paramT4CConnection)
	/*     */throws SQLException
	/*     */{
		/* 64 */super(3, 0, 67);
		/*     */
		/* 66 */this.oer = paramT4CTTIoer;
		/*     */
		/* 68 */setMarshalingEngine(paramT4CMAREngine);
		/*     */
		/* 70 */this.connection = paramT4CConnection;
		/*     */}

	/*     */
	/*     */void marshal(int paramInt1, int paramInt2)
	/*     */throws IOException, SQLException
	/*     */{
		/* 78 */super.marshalFunHeader();
		/*     */
		/* 81 */this.meg.marshalUB4(0L);
		/* 82 */this.meg.marshalUB4(paramInt1);
		/* 83 */this.meg.marshalPTR();
		/* 84 */this.meg.marshalUB4(3L);
		/* 85 */this.meg.marshalNULLPTR();
		/* 86 */this.meg.marshalUB4(0L);
		/* 87 */this.meg.marshalNULLPTR();
		/* 88 */this.meg.marshalUB4(0L);
		/* 89 */this.meg.marshalPTR();
		/* 90 */this.meg.marshalUB4(3L);
		/* 91 */this.meg.marshalPTR();
		/* 92 */this.meg.marshalNULLPTR();
		/* 93 */this.meg.marshalUB4(0L);
		/* 94 */this.meg.marshalNULLPTR();
		/* 95 */this.meg.marshalNULLPTR();
		/* 96 */this.meg.marshalUB4(0L);
		/* 97 */this.meg.marshalNULLPTR();
		/*     */
		/* 101 */this.meg.marshalUB4(paramInt2);
		/* 102 */this.meg.marshalUB4(0L);
		/* 103 */this.meg.marshalUB4(0L);
		/*     */}

	/*     */
	/*     */void receive()
	/*     */throws SQLException, IOException
	/*     */{
		/*     */while (true)
			/*     */try
			/*     */{
				/* 117 */int i = this.meg.unmarshalSB1();
				/*     */int j;
				/* 119 */switch (i)
				/*     */{
				/*     */case 9:
					/* 123 */if (this.meg.versionNumber < 10000)
						/*     */continue;
					/* 125 */j = (short) this.meg.unmarshalUB2();
					/*     */
					/* 127 */this.connection.endToEndECIDSequenceNumber = j;
					/*     */
					/* 130 */break;
				/*     */case 8:
					/* 133 */j = this.meg.unmarshalUB2();
					/*     */
					/* 135 */int k = 0;
					if (k >= j)
						/*     */continue;
					/* 137 */this.meg.unmarshalUB4();
					/*     */
					/* 135 */k++;
					break;
				/*     */default:
					/* 145 */DatabaseError.throwSqlException(401);
					/*     */}
				/*     */
				/* 151 */continue;
				/*     */}
			/*     */catch (BreakNetException localBreakNetException)
			/*     */{
				/*     */}
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CTTIk2rpc
 * JD-Core Version: 0.6.0
 */