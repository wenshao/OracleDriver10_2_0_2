/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.Connection;
/*     */
import java.sql.SQLException;
/*     */
import oracle.sql.Datum;

/*     */
/*     */class T4C8TTIBfile extends T4C8TTILob
/*     */{
	/* 259 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*     */
	/*     */T4C8TTIBfile(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
	/*     */{
		/* 100 */super(paramT4CMAREngine, paramT4CTTIoer);
		/*     */}

	/*     */
	/*     */Datum createTemporaryLob(Connection paramConnection,
			boolean paramBoolean, int paramInt)
	/*     */throws SQLException, IOException
	/*     */{
		/* 121 */Object localObject = null;
		/*     */
		/* 123 */DatabaseError.throwSqlException(
				"cannot create a temporary BFILE",
				DatabaseError.ErrorToSQLState(-1), -1);
		/*     */
		/* 129 */return localObject;
		/*     */}

	/*     */
	/*     */boolean open(byte[] paramArrayOfByte, int paramInt)
	/*     */throws SQLException, IOException
	/*     */{
		/* 149 */boolean bool = false;
		/*     */
		/* 152 */bool = _open(paramArrayOfByte, 11, 256);
		/*     */
		/* 157 */return bool;
		/*     */}

	/*     */
	/*     */boolean close(byte[] paramArrayOfByte)
	/*     */throws SQLException, IOException
	/*     */{
		/* 177 */boolean bool = false;
		/*     */
		/* 179 */bool = _close(paramArrayOfByte, 512);
		/*     */
		/* 184 */return bool;
		/*     */}

	/*     */
	/*     */boolean isOpen(byte[] paramArrayOfByte)
	/*     */throws SQLException, IOException
	/*     */{
		/* 204 */boolean bool = _isOpen(paramArrayOfByte, 1024);
		/*     */
		/* 209 */return bool;
		/*     */}

	/*     */
	/*     */boolean doesExist(byte[] paramArrayOfByte)
	/*     */throws SQLException, IOException
	/*     */{
		/* 229 */boolean bool = false;
		/*     */
		/* 232 */initializeLobdef();
		/*     */
		/* 235 */this.sourceLobLocator = paramArrayOfByte;
		/* 236 */this.lobops = 2048L;
		/* 237 */this.nullO2U = true;
		/*     */
		/* 240 */marshalFunHeader();
		/*     */
		/* 243 */marshalOlobops();
		/*     */
		/* 246 */receiveReply();
		/*     */
		/* 249 */bool = this.lobnull;
		/*     */
		/* 254 */return bool;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4C8TTIBfile
 * JD-Core Version: 0.6.0
 */