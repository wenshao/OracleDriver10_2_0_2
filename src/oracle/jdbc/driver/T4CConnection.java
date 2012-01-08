/*      */package oracle.jdbc.driver;

/*      */
/*      */import java.io.IOException;
/*      */
import java.io.InputStream;
/*      */
import java.io.OutputStream;
/*      */
import java.io.Reader;
/*      */
import java.io.UnsupportedEncodingException;
/*      */
import java.io.Writer;
/*      */
import java.sql.Connection;
/*      */
import java.sql.SQLException;
/*      */
import java.util.Collection;
/*      */
import java.util.Hashtable;
/*      */
import java.util.Properties;
/*      */
import oracle.jdbc.pool.OraclePooledConnection;
/*      */
import oracle.net.ns.Communication;
/*      */
import oracle.net.ns.NSProtocol;
/*      */
import oracle.net.ns.NetException;
/*      */
import oracle.sql.BFILE;
/*      */
import oracle.sql.BLOB;
/*      */
import oracle.sql.BfileDBAccess;
/*      */
import oracle.sql.BlobDBAccess;
/*      */
import oracle.sql.CLOB;
/*      */
import oracle.sql.ClobDBAccess;
/*      */
import oracle.sql.LobPlsqlUtil;

/*      */
/*      */class T4CConnection extends PhysicalConnection
/*      */implements BfileDBAccess, BlobDBAccess, ClobDBAccess
/*      */{
	/*      */static final short MIN_OVERSION_SUPPORTED = 7230;
	/*      */static final short MIN_TTCVER_SUPPORTED = 4;
	/*      */static final short V8_TTCVER_SUPPORTED = 5;
	/*      */static final short MAX_TTCVER_SUPPORTED = 6;
	/*      */static final int DEFAULT_LONG_PREFETCH_SIZE = 4080;
	/*      */static final String DEFAULT_CONNECT_STRING = "localhost:1521:orcl";
	/*      */static final int STREAM_CHUNK_SIZE = 255;
	/*      */static final int REFCURSOR_SIZE = 5;
	/* 78 */long LOGON_MODE = 0L;
	/*      */static final long SYSDBA = 8L;
	/*      */static final long SYSOPER = 16L;
	/*      */boolean isLoggedOn;
	/*      */private String password;
	/*      */Communication net;
	/*      */boolean readAsNonStream;
	/*      */T4CTTIoer oer;
	/*      */T4CMAREngine mare;
	/*      */T4C8TTIpro pro;
	/*      */T4C8TTIdty dty;
	/*      */T4CTTIrxd rxd;
	/*      */T4CTTIsto sto;
	/*      */T4CTTIoauthenticate auth;
	/*      */T4C7Oversion ver;
	/*      */T4C8Odscrarr describe;
	/*      */T4C8Oall all8;
	/*      */T4C8Oclose close8;
	/*      */T4C7Ocommoncall commoncall;
	/*      */T4C8TTIBfile bfileMsg;
	/*      */T4C8TTIBlob blobMsg;
	/*      */T4C8TTIClob clobMsg;
	/*      */T4CTTIoses oses;
	/* 122 */byte[] EMPTY_BYTE = new byte[0];
	/*      */T4CTTIOtxen otxen;
	/*      */T4CTTIOtxse otxse;
	/*      */T4CTTIk2rpc k2rpc;
	/*      */T4CTTIoscid oscid;
	/*      */T4CTTIokeyval okeyval;
	/*      */int[] cursorToClose;
	/*      */int cursorToCloseOffset;
	/*      */int[] queryToClose;
	/*      */int queryToCloseOffset;
	/*      */int sessionId;
	/*      */int serialNumber;
	/*      */boolean retainV9BehaviorForLong;
	/*      */Hashtable namespaces;
	/* 3326 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*      */
	/*      */T4CConnection(String paramString1, String paramString2,
			String paramString3, String paramString4,
			Properties paramProperties,
			OracleDriverExtension paramOracleDriverExtension)
	/*      */throws SQLException
	/*      */{
		/* 165 */super(paramString1, paramString2, paramString3, paramString4,
				paramProperties, paramOracleDriverExtension);
		/* 166 */String str = (String) paramProperties
				.get("oracle.jdbc.RetainV9LongBindBehavior");
		/* 167 */this.retainV9BehaviorForLong = ((str != null) && (str
				.equalsIgnoreCase("true")));
		/*      */
		/* 169 */this.cursorToClose = new int[4];
		/* 170 */this.cursorToCloseOffset = 0;
		/*      */
		/* 173 */this.queryToClose = new int[10];
		/* 174 */this.queryToCloseOffset = 0;
		/* 175 */this.minVcsBindSize = 0;
		/* 176 */this.streamChunkSize = 255;
		/*      */
		/* 178 */this.namespaces = new Hashtable(5);
		/*      */}

	/*      */
	/*      */final void initializePassword(String paramString) throws SQLException
	/*      */{
		/* 183 */this.password = paramString;
		/*      */}

	/*      */
	/*      */void logon()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 219 */if (this.isLoggedOn)
			/*      */{
				/* 224 */DatabaseError.throwSqlException(428);
				/*      */}
			/*      */
			/* 231 */if ((this.user == null) || (this.password == null))
			/*      */{
				/* 236 */DatabaseError.throwSqlException(433);
				/*      */}
			/*      */
			/* 240 */if ((this.user.length() == 0)
					|| (this.password.length() == 0))
			/*      */{
				/* 245 */DatabaseError.throwSqlException(443);
				/*      */}
			/*      */
			/* 252 */if (this.database == null)
			/*      */{
				/* 254 */this.database = "localhost:1521:orcl";
				/*      */}
			/*      */
			/* 268 */connect(this.database, this.connectionProperties);
			/*      */
			/* 270 */this.all8 = new T4C8Oall(this.mare, this, this.oer);
			/* 271 */this.close8 = new T4C8Oclose(this.mare);
			/*      */
			/* 273 */this.sto = new T4CTTIsto(this.mare, this.oer);
			/* 274 */this.commoncall = new T4C7Ocommoncall(this.mare, this.oer,
					this);
			/* 275 */this.describe = new T4C8Odscrarr(this.mare, this.oer);
			/* 276 */this.bfileMsg = new T4C8TTIBfile(this.mare, this.oer);
			/* 277 */this.blobMsg = new T4C8TTIBlob(this.mare, this.oer);
			/* 278 */this.clobMsg = new T4C8TTIClob(this.mare, this.oer);
			/* 279 */this.otxen = new T4CTTIOtxen(this.mare, this.oer, this);
			/* 280 */this.otxse = new T4CTTIOtxse(this.mare, this.oer, this);
			/* 281 */this.k2rpc = new T4CTTIk2rpc(this.mare, this.oer, this);
			/* 282 */this.oses = new T4CTTIoses(this.mare);
			/* 283 */this.okeyval = new T4CTTIokeyval(this.mare);
			/*      */
			/* 286 */this.oscid = new T4CTTIoscid(this.mare);
			/*      */
			/* 290 */this.dty = new T4C8TTIdty(this.mare);
			/*      */
			/* 292 */this.dty.marshal();
			/*      */
			/* 297 */this.dty.receive();
			/*      */
			/* 301 */this.ver = new T4C7Oversion(this.mare, this.oer, this);
			/*      */
			/* 303 */this.ver.marshal();
			/* 304 */this.ver.receive();
			/*      */
			/* 307 */this.versionNumber = this.ver.getVersionNumber();
			/* 308 */this.mare.versionNumber = this.versionNumber;
			/*      */
			/* 313 */if (this.versionNumber < 7230)
			/*      */{
				/* 317 */DatabaseError.throwSqlException(441);
				/*      */}
			/*      */
			/* 326 */this.mare.types.setVersion(this.versionNumber);
			/*      */
			/* 329 */String str = (String) this.connectionProperties
					.get("internal_logon");
			/*      */
			/* 331 */if (str != null)
			/*      */{
				/* 335 */if (str.equalsIgnoreCase("sysoper"))
				/*      */{
					/* 337 */this.LOGON_MODE = 64L;
					/*      */}
				/* 341 */else if (str.equalsIgnoreCase("sysdba"))
				/*      */{
					/* 343 */this.LOGON_MODE = 32L;
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 349 */this.LOGON_MODE = 0L;
				/*      */}
			/*      */
			/* 352 */this.auth = new T4CTTIoauthenticate(this.mare, this.user,
					this.password, this.connectionProperties, this.LOGON_MODE,
					this.ressourceManagerId, this.oer, this);
			/*      */
			/* 356 */this.auth.marshalOsesskey();
			/* 357 */this.auth.receiveOsesskey();
			/*      */
			/* 361 */this.auth.marshalOauth();
			/* 362 */this.auth.receiveOauth();
			/*      */
			/* 364 */this.sessionId = this.auth.getSessionId();
			/* 365 */this.serialNumber = this.auth.getSerialNumber();
			/*      */
			/* 376 */this.isLoggedOn = true;
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 383 */handleIOException(localIOException);
			/*      */
			/* 387 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */catch (SQLException localSQLException)
		/*      */{
			/*      */try
			/*      */{
				/* 397 */this.net.disconnect();
				/*      */}
			/*      */catch (Exception localException)
			/*      */{
				/*      */}
			/*      */
			/* 406 */this.isLoggedOn = false;
			/*      */
			/* 410 */throw localSQLException;
			/*      */}
		/*      */}

	/*      */
	/*      */void handleIOException(IOException paramIOException)
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 421 */this.net.disconnect();
			/*      */}
		/*      */catch (Exception localException)
		/*      */{
			/*      */}
		/*      */
		/* 430 */this.isLoggedOn = false;
		/* 431 */this.lifecycle = 4;
		/*      */}

	/*      */
	/*      */synchronized void logoff()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 457 */assertLoggedOn("T4CConnection.logoff");
			/* 458 */if (this.lifecycle == 8)
				/*      */return;
			/* 461 */sendPiggyBackedMessages();
			/* 462 */this.commoncall.init(9);
			/* 463 */this.commoncall.marshal();
			/* 464 */this.commoncall.receive();
			/*      */
			/* 466 */this.net.disconnect();
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 476 */handleIOException(localIOException);
			/*      */
			/* 478 */if (this.lifecycle != 8)
			/*      */{
				/* 480 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */finally
		/*      */{
			/* 488 */this.isLoggedOn = false;
			/*      */}
		/*      */}

	/*      */
	/*      */synchronized void doCommit()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 519 */assertLoggedOn("T4CConnection.do_commit");
			/* 520 */sendPiggyBackedMessages();
			/* 521 */this.commoncall.init(14);
			/* 522 */this.commoncall.marshal();
			/* 523 */this.commoncall.receive();
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 532 */handleIOException(localIOException);
			/* 533 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */synchronized void doRollback()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 563 */assertLoggedOn("T4CConnection.do_rollback");
			/* 564 */sendPiggyBackedMessages();
			/* 565 */this.commoncall.init(15);
			/* 566 */this.commoncall.marshal();
			/* 567 */this.commoncall.receive();
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 576 */handleIOException(localIOException);
			/* 577 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */synchronized void doSetAutoCommit(boolean paramBoolean)
	/*      */throws SQLException
	/*      */{
		/*      */}

	/*      */
	/*      */public synchronized void open(OracleStatement paramOracleStatement)
	/*      */throws SQLException
	/*      */{
		/* 643 */assertLoggedOn("T4CConnection.open");
		/*      */
		/* 647 */paramOracleStatement.setCursorId(0);
		/*      */}

	/*      */
	/*      */synchronized String doGetDatabaseProductVersion()
	/*      */throws SQLException
	/*      */{
		/* 662 */assertLoggedOn("T4CConnection.do_getDatabaseProductVersion");
		/*      */
		/* 664 */String str = null;
		/* 665 */byte[] arrayOfByte = this.ver.getVersion();
		/*      */try
		/*      */{
			/* 669 */str = new String(arrayOfByte, "UTF8");
			/*      */}
		/*      */catch (UnsupportedEncodingException localUnsupportedEncodingException)
		/*      */{
			/* 678 */DatabaseError
					.throwSqlException(localUnsupportedEncodingException);
			/*      */}
		/*      */
		/* 686 */return str;
		/*      */}

	/*      */
	/*      */synchronized short doGetVersionNumber()
	/*      */throws SQLException
	/*      */{
		/* 697 */assertLoggedOn("T4CConnection.do_getVersionNumber");
		/*      */
		/* 699 */int i = this.ver.getVersionNumber();
		/*      */
		/* 703 */return i;
		/*      */}

	/*      */
	/*      */OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte,
			OracleStatement paramOracleStatement)
	/*      */throws SQLException
	/*      */{
		/* 709 */T4CStatement localT4CStatement = new T4CStatement(this, -1, -1);
		/*      */try
		/*      */{
			/* 713 */int i = this.mare.unmarshalRefCursor(paramArrayOfByte);
			/*      */
			/* 715 */localT4CStatement.setCursorId(i);
			/*      */
			/* 717 */localT4CStatement.isOpen = true;
			/*      */
			/* 721 */localT4CStatement.sqlObject = paramOracleStatement.sqlObject;
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 728 */handleIOException(localIOException);
			/* 729 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 735 */localT4CStatement.sqlStringChanged = false;
		/* 736 */localT4CStatement.needToParse = false;
		/*      */
		/* 738 */return localT4CStatement;
		/*      */}

	/*      */
	/*      */void doCancel()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 755 */this.net.sendBreak();
			/*      */}
		/*      */catch (NetException localNetException)
		/*      */{
			/* 764 */DatabaseError.throwSqlException(localNetException);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 776 */handleIOException(localIOException);
			/* 777 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */void connect(String paramString, Properties paramProperties)
	/*      */throws IOException, SQLException
	/*      */{
		/* 824 */if ((paramString == null) || (paramProperties == null))
		/*      */{
			/* 831 */DatabaseError.throwSqlException(433);
			/*      */}
		/*      */
		/* 840 */this.net = new NSProtocol();
		/*      */try
		/*      */{
			/* 844 */this.net.connect(paramString, paramProperties);
			/*      */}
		/*      */catch (NetException localNetException)
		/*      */{
			/* 851 */throw new IOException(localNetException.getMessage());
			/*      */}
		/*      */
		/* 857 */this.mare = new T4CMAREngine(this.net);
		/* 858 */this.oer = new T4CTTIoer(this.mare, this);
		/*      */
		/* 864 */this.pro = new T4C8TTIpro(this.mare);
		/*      */
		/* 866 */this.pro.marshal();
		/* 867 */this.pro.receive();
		/*      */
		/* 873 */short s1 = this.pro.getOracleVersion();
		/* 874 */short s2 = this.pro.getCharacterSet();
		/* 875 */short s3 = DBConversion.findDriverCharSet(s2, s1);
		/*      */
		/* 878 */this.conversion = new DBConversion(s2, s3,
				this.pro.getncharCHARSET());
		/*      */
		/* 881 */this.mare.types.setServerConversion(s3 != s2);
		/*      */
		/* 885 */this.mare.types.setVersion(s1);
		/*      */
		/* 889 */if (DBConversion.isCharSetMultibyte(s3))
		/*      */{
			/* 895 */if (DBConversion.isCharSetMultibyte(this.pro
					.getCharacterSet()))
				/* 896 */this.mare.types.setFlags(1);
			/*      */else
				/* 898 */this.mare.types.setFlags(2);
			/*      */}
		/*      */else {
			/* 901 */this.mare.types.setFlags(this.pro.getFlags());
			/*      */}
		/* 903 */this.mare.conv = this.conversion;
		/*      */}

	/*      */
	/*      */void sendPiggyBackedMessages()
	/*      */throws SQLException, IOException
	/*      */{
		/* 938 */sendPiggyBackedClose();
		/*      */
		/* 946 */if ((this.endToEndAnyChanged) && (this.versionNumber >= 10000))
		/*      */{
			/* 949 */this.oscid.marshal(this.endToEndHasChanged,
					this.endToEndValues, this.endToEndECIDSequenceNumber);
			/*      */
			/* 952 */for (int i = 0; i < 4; i++) {
				/* 953 */if (this.endToEndHasChanged[i] != 0)
					/* 954 */this.endToEndHasChanged[i] = false;
				/*      */}
			/*      */}
		/* 957 */this.endToEndAnyChanged = false;
		/*      */
		/* 959 */if (!this.namespaces.isEmpty())
		/*      */{
			/* 961 */if (this.versionNumber >= 10200)
			/*      */{
				/* 963 */Object[] arrayOfObject = this.namespaces.values()
						.toArray();
				/* 964 */for (int j = 0; j < arrayOfObject.length; j++)
				/*      */{
					/* 966 */this.okeyval.marshal((Namespace) arrayOfObject[j]);
					/*      */}
				/*      */}
			/* 969 */this.namespaces.clear();
			/*      */}
		/*      */}

	/*      */
	/*      */private void sendPiggyBackedClose()
	/*      */throws SQLException, IOException
	/*      */{
		/* 977 */if (this.queryToCloseOffset > 0)
		/*      */{
			/* 979 */this.close8.initCloseQuery();
			/* 980 */this.close8.marshal(this.queryToClose,
					this.queryToCloseOffset);
			/*      */
			/* 982 */this.queryToCloseOffset = 0;
			/*      */}
		/*      */
		/* 986 */if (this.cursorToCloseOffset > 0)
		/*      */{
			/* 988 */this.close8.initCloseStatement();
			/* 989 */this.close8.marshal(this.cursorToClose,
					this.cursorToCloseOffset);
			/*      */
			/* 991 */this.cursorToCloseOffset = 0;
			/*      */}
		/*      */}

	/*      */
	/*      */void doProxySession(int paramInt, Properties paramProperties)
			throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 999 */sendPiggyBackedMessages();
			/*      */
			/* 1003 */this.auth.marshalOauth(paramInt, paramProperties,
					this.sessionId, this.serialNumber);
			/* 1004 */this.auth.receiveOauth();
			/*      */
			/* 1006 */int i = this.auth.getSessionId();
			/* 1007 */int j = this.auth.getSerialNumber();
			/*      */
			/* 1010 */this.oses.marshal(i, j, 1);
			/*      */
			/* 1012 */this.savedUser = this.user;
			/*      */
			/* 1014 */if (paramInt == 1)
				/* 1015 */this.user = paramProperties
						.getProperty("PROXY_USER_NAME");
			/*      */else {
				/* 1017 */this.user = null;
				/*      */}
			/* 1019 */this.isProxy = true;
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1026 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */void closeProxySession()
	/*      */throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 1037 */sendPiggyBackedMessages();
			/* 1038 */this.commoncall.init(9);
			/* 1039 */this.commoncall.marshal();
			/* 1040 */this.commoncall.receive();
			/*      */
			/* 1043 */this.oses.marshal(this.sessionId, this.serialNumber, 1);
			/*      */
			/* 1045 */this.user = this.savedUser;
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1052 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public Properties getServerSessionInfo()
	/*      */throws SQLException
	/*      */{
		/* 1061 */Properties localProperties = new Properties();
		/* 1062 */localProperties.setProperty("SERVER_HOST",
				this.auth.connectionValues.getProperty("AUTH_SC_SERVER_HOST",
						""));
		/*      */
		/* 1066 */localProperties.setProperty("INSTANCE_NAME",
				this.auth.connectionValues.getProperty("AUTH_SC_INSTANCE_NAME",
						""));
		/*      */
		/* 1070 */localProperties.setProperty("DATABASE_NAME",
				this.auth.connectionValues.getProperty("AUTH_SC_DBUNIQUE_NAME",
						""));
		/*      */
		/* 1074 */localProperties.setProperty("SERVICE_NAME",
				this.auth.connectionValues.getProperty("AUTH_SC_SERVICE_NAME",
						""));
		/*      */
		/* 1078 */return localProperties;
		/*      */}

	/*      */
	/*      */public synchronized BlobDBAccess createBlobDBAccess()
	/*      */throws SQLException
	/*      */{
		/* 1095 */return this;
		/*      */}

	/*      */
	/*      */public synchronized ClobDBAccess createClobDBAccess()
	/*      */throws SQLException
	/*      */{
		/* 1101 */return this;
		/*      */}

	/*      */
	/*      */public synchronized BfileDBAccess createBfileDBAccess()
	/*      */throws SQLException
	/*      */{
		/* 1107 */return this;
		/*      */}

	/*      */
	/*      */public synchronized long length(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1130 */assertLoggedOn("length");
		/* 1131 */assertNotNull(paramBFILE.shareBytes(), "length");
		/*      */
		/* 1133 */needLine();
		/*      */
		/* 1135 */long l = 0L;
		/*      */try
		/*      */{
			/* 1139 */l = this.bfileMsg.getLength(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1148 */handleIOException(localIOException);
			/* 1149 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1157 */return l;
		/*      */}

	/*      */
	/*      */public synchronized long position(BFILE paramBFILE,
			byte[] paramArrayOfByte, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1179 */if (paramLong < 1L)
		/*      */{
			/* 1181 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 1188 */long l = LobPlsqlUtil.hasPattern(paramBFILE,
				paramArrayOfByte, paramLong);
		/*      */
		/* 1190 */l = l == 0L ? -1L : l;
		/*      */
		/* 1195 */return l;
		/*      */}

	/*      */
	/*      */public long position(BFILE paramBFILE1, BFILE paramBFILE2,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1217 */if (paramLong < 1L)
		/*      */{
			/* 1219 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 1226 */long l = LobPlsqlUtil.isSubLob(paramBFILE1, paramBFILE2,
				paramLong);
		/*      */
		/* 1228 */l = l == 0L ? -1L : l;
		/*      */
		/* 1233 */return l;
		/*      */}

	/*      */
	/*      */public synchronized int getBytes(BFILE paramBFILE, long paramLong,
			int paramInt, byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 1251 */assertLoggedOn("getBytes");
		/*      */
		/* 1256 */if (paramLong < 1L)
		/*      */{
			/* 1258 */DatabaseError.throwSqlException(68, "getBytes()");
			/*      */}
		/*      */
		/* 1266 */if ((paramInt <= 0) || (paramArrayOfByte == null)) {
			/* 1267 */return 0;
			/*      */}
		/* 1269 */needLine();
		/*      */
		/* 1271 */long l = 0L;
		/*      */
		/* 1273 */if (paramInt != 0)
		/*      */{
			/*      */try
			/*      */{
				/* 1277 */l = this.bfileMsg.read(paramBFILE.shareBytes(),
						paramLong, paramInt, paramArrayOfByte);
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 1287 */handleIOException(localIOException);
				/* 1288 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1297 */return (int) l;
		/*      */}

	/*      */
	/*      */public String getName(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1313 */assertLoggedOn("getName");
		/* 1314 */assertNotNull(paramBFILE.shareBytes(), "getName");
		/*      */
		/* 1316 */String str = LobPlsqlUtil.fileGetName(paramBFILE);
		/*      */
		/* 1320 */return str;
		/*      */}

	/*      */
	/*      */public String getDirAlias(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1336 */assertLoggedOn("getDirAlias");
		/* 1337 */assertNotNull(paramBFILE.shareBytes(), "getDirAlias");
		/*      */
		/* 1339 */String str = LobPlsqlUtil.fileGetDirAlias(paramBFILE);
		/*      */
		/* 1343 */return str;
		/*      */}

	/*      */
	/*      */public synchronized void openFile(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1358 */assertLoggedOn("openFile");
		/* 1359 */assertNotNull(paramBFILE.shareBytes(), "openFile");
		/*      */
		/* 1361 */needLine();
		/*      */try
		/*      */{
			/* 1365 */this.bfileMsg.open(paramBFILE.shareBytes(), 11);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1375 */handleIOException(localIOException);
			/* 1376 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean isFileOpen(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1400 */assertLoggedOn("openFile");
		/* 1401 */assertNotNull(paramBFILE.shareBytes(), "openFile");
		/*      */
		/* 1403 */needLine();
		/*      */
		/* 1405 */boolean bool = false;
		/*      */try
		/*      */{
			/* 1409 */bool = this.bfileMsg.isOpen(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1419 */handleIOException(localIOException);
			/* 1420 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1428 */return bool;
		/*      */}

	/*      */
	/*      */public synchronized boolean fileExists(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1446 */assertLoggedOn("fileExists");
		/* 1447 */assertNotNull(paramBFILE.shareBytes(), "fileExists");
		/*      */
		/* 1449 */needLine();
		/*      */
		/* 1451 */boolean bool = false;
		/*      */try
		/*      */{
			/* 1455 */bool = this.bfileMsg.doesExist(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1465 */handleIOException(localIOException);
			/* 1466 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1474 */return bool;
		/*      */}

	/*      */
	/*      */public synchronized void closeFile(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1489 */assertLoggedOn("closeFile");
		/* 1490 */assertNotNull(paramBFILE.shareBytes(), "closeFile");
		/*      */
		/* 1492 */needLine();
		/*      */try
		/*      */{
			/* 1496 */this.bfileMsg.close(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1506 */handleIOException(localIOException);
			/* 1507 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void open(BFILE paramBFILE, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1530 */assertLoggedOn("open");
		/* 1531 */assertNotNull(paramBFILE.shareBytes(), "open");
		/*      */
		/* 1533 */needLine();
		/*      */try
		/*      */{
			/* 1537 */this.bfileMsg.open(paramBFILE.shareBytes(), paramInt);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1547 */handleIOException(localIOException);
			/* 1548 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void close(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1568 */assertLoggedOn("close");
		/* 1569 */assertNotNull(paramBFILE.shareBytes(), "close");
		/*      */
		/* 1571 */needLine();
		/*      */try
		/*      */{
			/* 1575 */this.bfileMsg.close(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1585 */handleIOException(localIOException);
			/* 1586 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean isOpen(BFILE paramBFILE)
	/*      */throws SQLException
	/*      */{
		/* 1607 */assertLoggedOn("isOpen");
		/* 1608 */assertNotNull(paramBFILE.shareBytes(), "isOpen");
		/*      */
		/* 1610 */needLine();
		/*      */
		/* 1612 */boolean bool = false;
		/*      */try
		/*      */{
			/* 1616 */bool = this.bfileMsg.isOpen(paramBFILE.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1626 */handleIOException(localIOException);
			/* 1627 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1635 */return bool;
		/*      */}

	/*      */
	/*      */public InputStream newInputStream(BFILE paramBFILE, int paramInt,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1655 */if (paramLong == 0L)
		/*      */{
			/* 1657 */return new OracleBlobInputStream(paramBFILE, paramInt);
			/*      */}
		/*      */
		/* 1661 */return new OracleBlobInputStream(paramBFILE, paramInt,
				paramLong);
		/*      */}

	/*      */
	/*      */public InputStream newConversionInputStream(BFILE paramBFILE,
			int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1682 */assertNotNull(paramBFILE.shareBytes(),
				"newConversionInputStream");
		/*      */
		/* 1684 */OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(
				this.conversion, paramBFILE.getBinaryStream(), paramInt);
		/*      */
		/* 1689 */return localOracleConversionInputStream;
		/*      */}

	/*      */
	/*      */public Reader newConversionReader(BFILE paramBFILE, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 1709 */assertNotNull(paramBFILE.shareBytes(), "newConversionReader");
		/*      */
		/* 1711 */OracleConversionReader localOracleConversionReader = new OracleConversionReader(
				this.conversion, paramBFILE.getBinaryStream(), paramInt);
		/*      */
		/* 1716 */return localOracleConversionReader;
		/*      */}

	/*      */
	/*      */public synchronized long length(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 1740 */assertLoggedOn("length");
		/* 1741 */assertNotNull(paramBLOB.shareBytes(), "length");
		/*      */
		/* 1743 */needLine();
		/*      */
		/* 1745 */long l = 0L;
		/*      */try
		/*      */{
			/* 1749 */l = this.blobMsg.getLength(paramBLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 1758 */handleIOException(localIOException);
			/* 1759 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 1767 */return l;
		/*      */}

	/*      */
	/*      */public long position(BLOB paramBLOB, byte[] paramArrayOfByte,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1786 */assertLoggedOn("position");
		/* 1787 */assertNotNull(paramBLOB.shareBytes(), "position");
		/*      */
		/* 1792 */if (paramLong < 1L)
		/*      */{
			/* 1794 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 1801 */long l = LobPlsqlUtil.hasPattern(paramBLOB, paramArrayOfByte,
				paramLong);
		/*      */
		/* 1803 */l = l == 0L ? -1L : l;
		/*      */
		/* 1807 */return l;
		/*      */}

	/*      */
	/*      */public long position(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 1825 */assertLoggedOn("position");
		/* 1826 */assertNotNull(paramBLOB1.shareBytes(), "position");
		/* 1827 */assertNotNull(paramBLOB2.shareBytes(), "position");
		/*      */
		/* 1832 */if (paramLong < 1L)
		/*      */{
			/* 1834 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 1841 */long l = LobPlsqlUtil.isSubLob(paramBLOB1, paramBLOB2,
				paramLong);
		/*      */
		/* 1843 */l = l == 0L ? -1L : l;
		/*      */
		/* 1847 */return l;
		/*      */}

	/*      */
	/*      */public synchronized int getBytes(BLOB paramBLOB, long paramLong,
			int paramInt, byte[] paramArrayOfByte)
	/*      */throws SQLException
	/*      */{
		/* 1866 */assertLoggedOn("getBytes");
		/* 1867 */assertNotNull(paramBLOB.shareBytes(), "getBytes");
		/*      */
		/* 1872 */if (paramLong < 1L)
		/*      */{
			/* 1874 */DatabaseError.throwSqlException(68, "getBytes()");
			/*      */}
		/*      */
		/* 1881 */if ((paramInt <= 0) || (paramArrayOfByte == null)) {
			/* 1882 */return 0;
			/*      */}
		/* 1884 */needLine();
		/*      */
		/* 1886 */long l = 0L;
		/*      */
		/* 1888 */if (paramInt != 0)
		/*      */{
			/*      */try
			/*      */{
				/* 1892 */l = this.blobMsg.read(paramBLOB.shareBytes(),
						paramLong, paramInt, paramArrayOfByte);
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 1902 */handleIOException(localIOException);
				/* 1903 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1912 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized int putBytes(BLOB paramBLOB, long paramLong,
			byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 1933 */assertLoggedOn("putBytes");
		/* 1934 */assertNotNull(paramBLOB.shareBytes(), "putBytes");
		/*      */
		/* 1939 */if (paramLong < 1L)
		/*      */{
			/* 1941 */DatabaseError.throwSqlException(68, "putBytes()");
			/*      */}
		/*      */
		/* 1948 */if ((paramArrayOfByte == null) || (paramInt2 <= 0)) {
			/* 1949 */return 0;
			/*      */}
		/* 1951 */needLine();
		/*      */
		/* 1953 */long l = 0L;
		/*      */
		/* 1955 */if (paramInt2 != 0)
		/*      */{
			/*      */try
			/*      */{
				/* 1959 */l = this.blobMsg.write(paramBLOB.shareBytes(),
						paramLong, paramArrayOfByte, paramInt1, paramInt2);
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 1970 */handleIOException(localIOException);
				/* 1971 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1980 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized int getChunkSize(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 1994 */assertLoggedOn("getChunkSize");
		/* 1995 */assertNotNull(paramBLOB.shareBytes(), "getChunkSize");
		/*      */
		/* 1997 */needLine();
		/*      */
		/* 1999 */long l = 0L;
		/*      */try
		/*      */{
			/* 2003 */l = this.blobMsg.getChunkSize(paramBLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2013 */handleIOException(localIOException);
			/* 2014 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2022 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized void trim(BLOB paramBLOB, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2038 */assertLoggedOn("trim");
		/* 2039 */assertNotNull(paramBLOB.shareBytes(), "trim");
		/*      */
		/* 2044 */if (paramLong < 0L)
		/*      */{
			/* 2046 */DatabaseError.throwSqlException(68, "trim()");
			/*      */}
		/*      */
		/* 2053 */needLine();
		/*      */try
		/*      */{
			/* 2057 */this.blobMsg.trim(paramBLOB.shareBytes(), paramLong);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2067 */handleIOException(localIOException);
			/* 2068 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized BLOB createTemporaryBlob(
			Connection paramConnection, boolean paramBoolean, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2094 */assertLoggedOn("createTemporaryBlob");
		/*      */
		/* 2096 */needLine();
		/*      */
		/* 2098 */BLOB localBLOB = null;
		/*      */try
		/*      */{
			/* 2102 */localBLOB = (BLOB) this.blobMsg.createTemporaryLob(this,
					paramBoolean, paramInt);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2112 */handleIOException(localIOException);
			/* 2113 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2121 */return localBLOB;
		/*      */}

	/*      */
	/*      */public synchronized void freeTemporary(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 2137 */assertLoggedOn("freeTemporary");
		/* 2138 */assertNotNull(paramBLOB.shareBytes(), "freeTemporary");
		/*      */
		/* 2140 */needLine();
		/*      */try
		/*      */{
			/* 2144 */this.blobMsg.freeTemporaryLob(paramBLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2154 */handleIOException(localIOException);
			/* 2155 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public boolean isTemporary(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 2185 */int i = 0;
		/* 2186 */byte[] arrayOfByte = paramBLOB.shareBytes();
		/*      */
		/* 2188 */if (((arrayOfByte[7] & 0x1) > 0)
				|| ((arrayOfByte[4] & 0x40) > 0)) {
			/* 2189 */i = 1;
			/*      */}
		/*      */
		/* 2194 */return i;
		/*      */}

	/*      */
	/*      */public synchronized void open(BLOB paramBLOB, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2209 */assertLoggedOn("open");
		/* 2210 */assertNotNull(paramBLOB.shareBytes(), "open");
		/*      */
		/* 2212 */needLine();
		/*      */try
		/*      */{
			/* 2216 */this.blobMsg.open(paramBLOB.shareBytes(), paramInt);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2226 */handleIOException(localIOException);
			/* 2227 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void close(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 2247 */assertLoggedOn("close");
		/* 2248 */assertNotNull(paramBLOB.shareBytes(), "close");
		/*      */
		/* 2250 */needLine();
		/*      */try
		/*      */{
			/* 2254 */this.blobMsg.close(paramBLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2264 */handleIOException(localIOException);
			/* 2265 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean isOpen(BLOB paramBLOB)
	/*      */throws SQLException
	/*      */{
		/* 2286 */assertLoggedOn("isOpen");
		/* 2287 */assertNotNull(paramBLOB.shareBytes(), "isOpen");
		/*      */
		/* 2289 */needLine();
		/*      */
		/* 2291 */boolean bool = false;
		/*      */try
		/*      */{
			/* 2295 */bool = this.blobMsg.isOpen(paramBLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2305 */handleIOException(localIOException);
			/* 2306 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2314 */return bool;
		/*      */}

	/*      */
	/*      */public InputStream newInputStream(BLOB paramBLOB, int paramInt,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2334 */if (paramLong == 0L)
		/*      */{
			/* 2336 */return new OracleBlobInputStream(paramBLOB, paramInt);
			/*      */}
		/*      */
		/* 2340 */return new OracleBlobInputStream(paramBLOB, paramInt,
				paramLong);
		/*      */}

	/*      */
	/*      */public OutputStream newOutputStream(BLOB paramBLOB, int paramInt,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2361 */if (paramLong == 0L)
		/*      */{
			/* 2363 */return new OracleBlobOutputStream(paramBLOB, paramInt);
			/*      */}
		/*      */
		/* 2367 */return new OracleBlobOutputStream(paramBLOB, paramInt,
				paramLong);
		/*      */}

	/*      */
	/*      */public InputStream newConversionInputStream(BLOB paramBLOB,
			int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2388 */assertNotNull(paramBLOB.shareBytes(),
				"newConversionInputStream");
		/*      */
		/* 2390 */OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(
				this.conversion, paramBLOB.getBinaryStream(), paramInt);
		/*      */
		/* 2395 */return localOracleConversionInputStream;
		/*      */}

	/*      */
	/*      */public Reader newConversionReader(BLOB paramBLOB, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2415 */assertNotNull(paramBLOB.shareBytes(), "newConversionReader");
		/*      */
		/* 2417 */OracleConversionReader localOracleConversionReader = new OracleConversionReader(
				this.conversion, paramBLOB.getBinaryStream(), paramInt);
		/*      */
		/* 2422 */return localOracleConversionReader;
		/*      */}

	/*      */
	/*      */public synchronized long length(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2449 */assertLoggedOn("length");
		/* 2450 */assertNotNull(paramCLOB.shareBytes(), "length");
		/*      */
		/* 2452 */needLine();
		/*      */
		/* 2454 */long l = 0L;
		/*      */try
		/*      */{
			/* 2458 */l = this.clobMsg.getLength(paramCLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2467 */handleIOException(localIOException);
			/* 2468 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2476 */return l;
		/*      */}

	/*      */
	/*      */public long position(CLOB paramCLOB, String paramString, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2495 */if (paramString == null) {
			/* 2496 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 2499 */assertLoggedOn("position");
		/* 2500 */assertNotNull(paramCLOB.shareBytes(), "position");
		/*      */
		/* 2505 */if (paramLong < 1L)
		/*      */{
			/* 2507 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 2514 */char[] arrayOfChar = new char[paramString.length()];
		/*      */
		/* 2516 */paramString.getChars(0, arrayOfChar.length, arrayOfChar, 0);
		/*      */
		/* 2518 */long l = LobPlsqlUtil.hasPattern(paramCLOB, arrayOfChar,
				paramLong);
		/*      */
		/* 2520 */l = l == 0L ? -1L : l;
		/*      */
		/* 2524 */return l;
		/*      */}

	/*      */
	/*      */public long position(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2541 */if (paramCLOB2 == null) {
			/* 2542 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 2545 */assertLoggedOn("position");
		/* 2546 */assertNotNull(paramCLOB1.shareBytes(), "position");
		/* 2547 */assertNotNull(paramCLOB2.shareBytes(), "position");
		/*      */
		/* 2549 */if (paramLong < 1L)
		/*      */{
			/* 2551 */DatabaseError.throwSqlException(68, "position()");
			/*      */}
		/*      */
		/* 2555 */long l = LobPlsqlUtil.isSubLob(paramCLOB1, paramCLOB2,
				paramLong);
		/*      */
		/* 2557 */l = l == 0L ? -1L : l;
		/*      */
		/* 2561 */return l;
		/*      */}

	/*      */
	/*      */public synchronized int getChars(CLOB paramCLOB, long paramLong,
			int paramInt, char[] paramArrayOfChar)
	/*      */throws SQLException
	/*      */{
		/* 2580 */assertLoggedOn("getChars");
		/* 2581 */assertNotNull(paramCLOB.shareBytes(), "getChars");
		/*      */
		/* 2583 */if (paramLong < 1L)
		/*      */{
			/* 2585 */DatabaseError.throwSqlException(68, "getChars()");
			/*      */}
		/*      */
		/* 2589 */if ((paramInt <= 0) || (paramArrayOfChar == null)) {
			/* 2590 */return 0;
			/*      */}
		/* 2592 */needLine();
		/*      */
		/* 2594 */long l = 0L;
		/*      */
		/* 2596 */if (paramInt != 0)
		/*      */{
			/*      */try
			/*      */{
				/* 2600 */boolean bool = paramCLOB.isNCLOB();
				/*      */
				/* 2602 */l = this.clobMsg.read(paramCLOB.shareBytes(),
						paramLong, paramInt, bool, paramArrayOfChar);
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 2612 */handleIOException(localIOException);
				/* 2613 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2622 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized int putChars(CLOB paramCLOB, long paramLong,
			char[] paramArrayOfChar, int paramInt1, int paramInt2)
	/*      */throws SQLException
	/*      */{
		/* 2644 */assertLoggedOn("putChars");
		/* 2645 */assertNotNull(paramCLOB.shareBytes(), "putChars");
		/*      */
		/* 2647 */if (paramLong < 1L)
		/*      */{
			/* 2649 */DatabaseError.throwSqlException(68, "putChars()");
			/*      */}
		/*      */
		/* 2653 */if ((paramArrayOfChar == null) || (paramInt2 <= 0)) {
			/* 2654 */return 0;
			/*      */}
		/* 2656 */needLine();
		/*      */
		/* 2658 */long l = 0L;
		/*      */
		/* 2660 */if (paramInt2 != 0)
		/*      */{
			/*      */try
			/*      */{
				/* 2664 */boolean bool = paramCLOB.isNCLOB();
				/*      */
				/* 2666 */l = this.clobMsg
						.write(paramCLOB.shareBytes(), paramLong, bool,
								paramArrayOfChar, paramInt1, paramInt2);
				/*      */}
			/*      */catch (IOException localIOException)
			/*      */{
				/* 2677 */handleIOException(localIOException);
				/* 2678 */DatabaseError.throwSqlException(localIOException);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2687 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized int getChunkSize(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2701 */assertLoggedOn("getChunkSize");
		/* 2702 */assertNotNull(paramCLOB.shareBytes(), "getChunkSize");
		/*      */
		/* 2704 */needLine();
		/*      */
		/* 2706 */long l = 0L;
		/*      */try
		/*      */{
			/* 2710 */l = this.clobMsg.getChunkSize(paramCLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2720 */handleIOException(localIOException);
			/* 2721 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2729 */return (int) l;
		/*      */}

	/*      */
	/*      */public synchronized void trim(CLOB paramCLOB, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 2745 */assertLoggedOn("trim");
		/* 2746 */assertNotNull(paramCLOB.shareBytes(), "trim");
		/*      */
		/* 2751 */if (paramLong < 0L)
		/*      */{
			/* 2753 */DatabaseError.throwSqlException(68, "trim()");
			/*      */}
		/*      */
		/* 2760 */needLine();
		/*      */try
		/*      */{
			/* 2764 */this.clobMsg.trim(paramCLOB.shareBytes(), paramLong);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2774 */handleIOException(localIOException);
			/* 2775 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized CLOB createTemporaryClob(
			Connection paramConnection, boolean paramBoolean, int paramInt,
			short paramShort)
	/*      */throws SQLException
	/*      */{
		/* 2803 */assertLoggedOn("createTemporaryClob");
		/*      */
		/* 2805 */needLine();
		/*      */
		/* 2807 */CLOB localCLOB = null;
		/*      */try
		/*      */{
			/* 2811 */localCLOB = (CLOB) this.clobMsg.createTemporaryLob(this,
					paramBoolean, paramInt, paramShort);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2822 */handleIOException(localIOException);
			/* 2823 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 2831 */return localCLOB;
		/*      */}

	/*      */
	/*      */public synchronized void freeTemporary(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2847 */assertLoggedOn("freeTemporary");
		/* 2848 */assertNotNull(paramCLOB.shareBytes(), "freeTemporary");
		/*      */
		/* 2850 */needLine();
		/*      */try
		/*      */{
			/* 2854 */this.clobMsg.freeTemporaryLob(paramCLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2864 */handleIOException(localIOException);
			/* 2865 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public boolean isTemporary(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2895 */int i = 0;
		/* 2896 */byte[] arrayOfByte = paramCLOB.shareBytes();
		/*      */
		/* 2898 */if (((arrayOfByte[7] & 0x1) > 0)
				|| ((arrayOfByte[4] & 0x40) > 0)) {
			/* 2899 */i = 1;
			/*      */}
		/*      */
		/* 2904 */return i;
		/*      */}

	/*      */
	/*      */public synchronized void open(CLOB paramCLOB, int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2919 */assertLoggedOn("open");
		/* 2920 */assertNotNull(paramCLOB.shareBytes(), "open");
		/*      */
		/* 2922 */needLine();
		/*      */try
		/*      */{
			/* 2926 */this.clobMsg.open(paramCLOB.shareBytes(), paramInt);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2936 */handleIOException(localIOException);
			/* 2937 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized void close(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2957 */assertLoggedOn("close");
		/* 2958 */assertNotNull(paramCLOB.shareBytes(), "close");
		/*      */
		/* 2960 */needLine();
		/*      */try
		/*      */{
			/* 2964 */this.clobMsg.close(paramCLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 2974 */handleIOException(localIOException);
			/* 2975 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */public synchronized boolean isOpen(CLOB paramCLOB)
	/*      */throws SQLException
	/*      */{
		/* 2996 */assertLoggedOn("isOpen");
		/* 2997 */assertNotNull(paramCLOB.shareBytes(), "isOpen");
		/*      */
		/* 2999 */boolean bool = false;
		/*      */
		/* 3001 */needLine();
		/*      */try
		/*      */{
			/* 3005 */bool = this.clobMsg.isOpen(paramCLOB.shareBytes());
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 3015 */handleIOException(localIOException);
			/* 3016 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 3024 */return bool;
		/*      */}

	/*      */
	/*      */public InputStream newInputStream(CLOB paramCLOB, int paramInt,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 3045 */if (paramLong == 0L)
		/*      */{
			/* 3047 */return new OracleClobInputStream(paramCLOB, paramInt);
			/*      */}
		/*      */
		/* 3051 */return new OracleClobInputStream(paramCLOB, paramInt,
				paramLong);
		/*      */}

	/*      */
	/*      */public OutputStream newOutputStream(CLOB paramCLOB, int paramInt,
			long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 3072 */if (paramLong == 0L)
		/*      */{
			/* 3074 */return new OracleClobOutputStream(paramCLOB, paramInt);
			/*      */}
		/*      */
		/* 3078 */return new OracleClobOutputStream(paramCLOB, paramInt,
				paramLong);
		/*      */}

	/*      */
	/*      */public Reader newReader(CLOB paramCLOB, int paramInt, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 3099 */if (paramLong == 0L)
		/*      */{
			/* 3101 */return new OracleClobReader(paramCLOB, paramInt);
			/*      */}
		/*      */
		/* 3105 */return new OracleClobReader(paramCLOB, paramInt, paramLong);
		/*      */}

	/*      */
	/*      */public Writer newWriter(CLOB paramCLOB, int paramInt, long paramLong)
	/*      */throws SQLException
	/*      */{
		/* 3126 */if (paramLong == 0L)
		/*      */{
			/* 3128 */return new OracleClobWriter(paramCLOB, paramInt);
			/*      */}
		/*      */
		/* 3132 */return new OracleClobWriter(paramCLOB, paramInt, paramLong);
		/*      */}

	/*      */
	/*      */void assertLoggedOn(String paramString)
	/*      */throws SQLException
	/*      */{
		/* 3156 */if (!this.isLoggedOn)
		/*      */{
			/* 3161 */DatabaseError.throwSqlException(430);
			/*      */}
		/*      */}

	/*      */
	/*      */void assertNotNull(byte[] paramArrayOfByte, String paramString)
	/*      */throws NullPointerException
	/*      */{
		/* 3178 */if (paramArrayOfByte == null)
		/*      */{
			/* 3183 */throw new NullPointerException("bytes are null");
			/*      */}
		/*      */}

	/*      */
	/*      */void internalClose()
	/*      */throws SQLException
	/*      */{
		/* 3192 */super.internalClose();
		/*      */
		/* 3194 */this.isLoggedOn = false;
		/*      */}

	/*      */
	/*      */void doAbort() throws SQLException
	/*      */{
		/*      */try
		/*      */{
			/* 3201 */this.net.abort();
			/*      */}
		/*      */catch (NetException localNetException)
		/*      */{
			/* 3210 */DatabaseError.throwSqlException(localNetException);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 3222 */handleIOException(localIOException);
			/* 3223 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */}

	/*      */
	/*      */protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo)
	/*      */throws SQLException
	/*      */{
		/* 3233 */T4CStatement localT4CStatement = new T4CStatement(this, -1,
				-1);
		/* 3234 */localT4CStatement.open();
		/*      */
		/* 3236 */String str1 = paramAutoKeyInfo.getTableName();
		/* 3237 */String str2 = "SELECT * FROM " + str1;
		/*      */
		/* 3239 */localT4CStatement.sqlObject.initialize(str2);
		/*      */
		/* 3241 */Accessor[] arrayOfAccessor = null;
		/*      */try
		/*      */{
			/* 3245 */this.describe.init(localT4CStatement, 0);
			/* 3246 */this.describe.sqltext = localT4CStatement.sqlObject
					.getSqlBytes(false, false);
			/* 3247 */this.describe.marshal();
			/* 3248 */arrayOfAccessor = this.describe.receive(arrayOfAccessor);
			/*      */}
		/*      */catch (IOException localIOException)
		/*      */{
			/* 3252 */handleIOException(localIOException);
			/* 3253 */DatabaseError.throwSqlException(localIOException);
			/*      */}
		/*      */
		/* 3256 */int i = this.describe.numuds;
		/*      */
		/* 3267 */paramAutoKeyInfo.allocateSpaceForDescribedData(i);
		/*      */
		/* 3269 */for (int i1 = 0; i1 < i; i1++)
		/*      */{
			/* 3271 */Accessor localAccessor = arrayOfAccessor[i1];
			/* 3272 */String str3 = localAccessor.columnName;
			/* 3273 */int j = localAccessor.describeType;
			/* 3274 */int k = localAccessor.describeMaxLength;
			/* 3275 */boolean bool = localAccessor.nullable;
			/* 3276 */short s = localAccessor.formOfUse;
			/* 3277 */int m = localAccessor.precision;
			/* 3278 */int n = localAccessor.scale;
			/* 3279 */String str4 = localAccessor.describeTypeName;
			/*      */
			/* 3281 */paramAutoKeyInfo.fillDescribedData(i1, str3, j, k, bool,
					s, m, n, str4);
			/*      */}
		/*      */
		/* 3285 */localT4CStatement.close();
		/*      */}

	/*      */
	/*      */void doSetApplicationContext(String paramString1, String paramString2,
			String paramString3)
	/*      */throws SQLException
	/*      */{
		/* 3294 */Namespace localNamespace = (Namespace) this.namespaces
				.get(paramString1);
		/* 3295 */if (localNamespace == null)
		/*      */{
			/* 3297 */localNamespace = new Namespace(paramString1);
			/* 3298 */this.namespaces.put(paramString1, localNamespace);
			/*      */}
		/* 3300 */localNamespace.setAttribute(paramString2, paramString3);
		/*      */}

	/*      */
	/*      */void doClearAllApplicationContext(String paramString)
	/*      */throws SQLException
	/*      */{
		/* 3307 */Namespace localNamespace = new Namespace(paramString);
		/* 3308 */localNamespace.clear();
		/* 3309 */this.namespaces.put(paramString, localNamespace);
		/*      */}

	/*      */
	/*      */public void getPropertyForPooledConnection(
			OraclePooledConnection paramOraclePooledConnection)
	/*      */throws SQLException
	/*      */{
		/* 3315 */super.getPropertyForPooledConnection(
				paramOraclePooledConnection, this.password);
		/*      */}

	/*      */
	/*      */final void getPasswordInternal(T4CXAResource paramT4CXAResource)
	/*      */throws SQLException
	/*      */{
		/* 3321 */paramT4CXAResource.setPasswordInternal(this.password);
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CConnection
 * JD-Core Version: 0.6.0
 */