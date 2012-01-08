/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import oracle.jdbc.OracleOCIFailover;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ import oracle.jdbc.oracore.OracleTypeCLOB;
/*      */ import oracle.jdbc.pool.OracleOCIConnectionPool;
/*      */ import oracle.jdbc.pool.OraclePooledConnection;
/*      */ import oracle.sql.BFILE;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.BfileDBAccess;
/*      */ import oracle.sql.BlobDBAccess;
/*      */ import oracle.sql.CLOB;
/*      */ import oracle.sql.ClobDBAccess;
/*      */ import oracle.sql.LobPlsqlUtil;
/*      */ import oracle.sql.SQLName;
/*      */ import oracle.sql.converter.CharacterSetMetaData;
/*      */ 
/*      */ public class T2CConnection extends PhysicalConnection
/*      */   implements BfileDBAccess, BlobDBAccess, ClobDBAccess
/*      */ {
/*   48 */   short[] queryMetaData1 = null;
/*   49 */   byte[] queryMetaData2 = null;
/*   50 */   int queryMetaData1Offset = 0;
/*   51 */   int queryMetaData2Offset = 0;
/*      */   private String password;
/*   53 */   int fatalErrorNumber = 0;
/*   54 */   String fatalErrorMessage = null;
/*      */   static final int QMD_dbtype = 0;
/*      */   static final int QMD_dbsize = 1;
/*      */   static final int QMD_nullok = 2;
/*      */   static final int QMD_precision = 3;
/*      */   static final int QMD_scale = 4;
/*      */   static final int QMD_formOfUse = 5;
/*      */   static final int QMD_columnNameLength = 6;
/*      */   static final int QMD_tdo0 = 7;
/*      */   static final int QMD_tdo1 = 8;
/*      */   static final int QMD_tdo2 = 9;
/*      */   static final int QMD_tdo3 = 10;
/*      */   static final int QMD_charLength = 11;
/*      */   static final int QMD_typeNameLength = 12;
/*      */   static final int T2C_LOCATOR_MAX_LEN = 16;
/*      */   static final int T2C_LINEARIZED_LOCATOR_MAX_LEN = 4000;
/*      */   static final int T2C_LINEARIZED_BFILE_LOCATOR_MAX_LEN = 530;
/*      */   static final int METADATA1_INDICES_PER_COLUMN = 13;
/*      */   protected static final int SIZEOF_QUERYMETADATA2 = 8;
/*   86 */   int queryMetaData1Size = 100;
/*   87 */   int queryMetaData2Size = 800;
/*      */   long m_nativeState;
/*      */   short m_clientCharacterSet;
/*      */   byte byteAlign;
/*      */   private static final int EOJ_SUCCESS = 0;
/*      */   private static final int EOJ_ERROR = -1;
/*      */   private static final int EOJ_WARNING = 1;
/*      */   private static final String OCILIBRARY = "ocijdbc10";
/*  103 */   private int logon_mode = 0;
/*      */   static final int LOGON_MODE_DEFAULT = 0;
/*      */   static final int LOGON_MODE_SYSDBA = 2;
/*      */   static final int LOGON_MODE_SYSOPER = 4;
/*      */   static final int LOGON_MODE_CONNECTION_POOL = 5;
/*      */   static final int LOGON_MODE_CONNPOOL_CONNECTION = 6;
/*      */   static final int LOGON_MODE_CONNPOOL_PROXY_CONNECTION = 7;
/*      */   static final int LOGON_MODE_CONNPOOL_ALIASED_CONNECTION = 8;
/*      */   static final int T2C_PROXYTYPE_NONE = 0;
/*      */   static final int T2C_PROXYTYPE_USER_NAME = 1;
/*      */   static final int T2C_PROXYTYPE_DISTINGUISHED_NAME = 2;
/*      */   static final int T2C_PROXYTYPE_CERTIFICATE = 3;
/*      */   private static boolean isLibraryLoaded;
/*  123 */   OracleOCIFailover appCallback = null;
/*  124 */   Object appCallbackObject = null;
/*      */   private Properties nativeInfo;
/* 3357 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*      */ 
/*      */   protected T2CConnection(String paramString1, String paramString2, String paramString3, String paramString4, Properties paramProperties, OracleDriverExtension paramOracleDriverExtension)
/*      */     throws SQLException
/*      */   {
/*  132 */     super(paramString1, paramString2, paramString3, paramString4, paramProperties, paramOracleDriverExtension);
/*      */ 
/*  134 */     initialize();
/*      */   }
/*      */ 
/*      */   final void initializePassword(String paramString) throws SQLException
/*      */   {
/*  139 */     this.password = paramString;
/*      */   }
/*      */ 
/*      */   protected void initialize()
/*      */   {
/*  144 */     allocQueryMetaDataBuffers();
/*      */   }
/*      */ 
/*      */   private void allocQueryMetaDataBuffers()
/*      */   {
/*  174 */     this.queryMetaData1Offset = 0;
/*  175 */     this.queryMetaData1 = new short[this.queryMetaData1Size * 13];
/*      */ 
/*  180 */     this.queryMetaData2Offset = 0;
/*  181 */     this.queryMetaData2 = new byte[this.queryMetaData2Size];
/*      */ 
/*  183 */     this.namedTypeAccessorByteLen = 0;
/*  184 */     this.refTypeAccessorByteLen = 0;
/*      */   }
/*      */ 
/*      */   void reallocateQueryMetaData(int paramInt1, int paramInt2)
/*      */   {
/*  189 */     this.queryMetaData1 = null;
/*  190 */     this.queryMetaData2 = null;
/*      */ 
/*  192 */     this.queryMetaData1Size = Math.max(paramInt1, this.queryMetaData1Size);
/*  193 */     this.queryMetaData2Size = Math.max(paramInt2, this.queryMetaData2Size);
/*      */ 
/*  195 */     allocQueryMetaDataBuffers();
/*      */   }
/*      */ 
/*      */   protected void logon()
/*      */     throws SQLException
/*      */   {
/*  217 */     if (this.database == null) {
/*  218 */       DatabaseError.throwSqlException(64);
/*      */     }
/*  220 */     if (!isLibraryLoaded) {
/*  221 */       loadNativeLibrary(this.connectionProperties);
/*      */     }
/*      */ 
/*  225 */     if (this.connectionProperties.getProperty("is_connection_pooling") == "true")
/*      */     {
/*  228 */       processOCIConnectionPooling();
/*      */     }
/*      */     else
/*      */     {
/*  235 */       long l1 = 0L;
/*  236 */       long l2 = 0L;
/*  237 */       long l3 = 0L;
/*      */       String str1;
/*      */       String str2;
/*  240 */       if (((str1 = this.connectionProperties.getProperty("OCISvcCtxHandle")) != null) && ((str2 = this.connectionProperties.getProperty("OCIEnvHandle")) != null))
/*      */       {
/*  245 */         l1 = Long.parseLong(str1);
/*  246 */         l2 = Long.parseLong(str2);
/*      */         String str3;
/*  248 */         if ((str3 = this.connectionProperties.getProperty("OCIErrHandle")) != null)
/*      */         {
/*  250 */           l3 = Long.parseLong(str3);
/*      */         }
/*      */ 
/*  255 */         if ((str4 = this.connectionProperties.getProperty("JDBCDriverCharSetId")) != null)
/*      */         {
/*  257 */           this.m_clientCharacterSet = new Integer(str4).shortValue();
/*      */         }
/*      */         else
/*      */         {
/*  263 */           DatabaseError.throwSqlException(89);
/*      */         }
/*      */ 
/*  270 */         this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/*  273 */         localObject = new short[3];
/*      */ 
/*  275 */         this.sqlWarning = checkError(t2cUseConnection(this.m_nativeState, l2, l1, l3, localObject), this.sqlWarning);
/*      */ 
/*  279 */         this.conversion = new DBConversion(localObject[0], this.m_clientCharacterSet, localObject[1]);
/*  280 */         this.byteAlign = (byte)(localObject[2] & 0xFF);
/*      */ 
/*  282 */         return;
/*      */       }
/*      */ 
/*  287 */       String str4 = this.connectionProperties.getProperty("internal_logon");
/*      */ 
/*  290 */       if (str4 == null)
/*  291 */         this.logon_mode = 0;
/*  292 */       else if (str4.equalsIgnoreCase("SYSDBA"))
/*  293 */         this.logon_mode = 2;
/*  294 */       else if (str4.equalsIgnoreCase("SYSOPER")) {
/*  295 */         this.logon_mode = 4;
/*      */       }
/*  297 */       Object localObject = this.connectionProperties.getProperty("oracle.jdbc.ociNlsLangBackwardCompatible");
/*      */ 
/*  300 */       byte[] arrayOfByte1 = null;
/*  301 */       byte[] arrayOfByte2 = null;
/*  302 */       String str5 = null;
/*  303 */       byte[] arrayOfByte3 = new byte[0];
/*  304 */       if (this.connectionProperties != null)
/*      */       {
/*  306 */         str5 = (String)this.connectionProperties.get("OCINewPassword");
/*      */       }
/*      */ 
/*  310 */       if ((localObject != null) && (((String)localObject).equalsIgnoreCase("true")))
/*      */       {
/*  314 */         this.m_clientCharacterSet = getDriverCharSetIdFromNLS_LANG(this.connectionProperties);
/*      */       }
/*      */       else
/*      */       {
/*  319 */         this.m_clientCharacterSet = getClientCharSetId();
/*      */       }
/*      */ 
/*  322 */       if (str5 != null) {
/*  323 */         arrayOfByte3 = DBConversion.stringToAsciiBytes(str5);
/*      */       }
/*  325 */       arrayOfByte1 = this.user == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */ 
/*  329 */       arrayOfByte2 = this.password == null ? new byte[0] : DBConversion.stringToAsciiBytes(this.password);
/*      */ 
/*  332 */       byte[] arrayOfByte4 = DBConversion.stringToAsciiBytes(this.database);
/*      */ 
/*  334 */       short[] arrayOfShort = new short[3];
/*  335 */       String str6 = null;
/*  336 */       byte[] arrayOfByte5 = (str6 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault())) != null ? str6.getBytes() : null;
/*      */ 
/*  338 */       byte[] arrayOfByte6 = (str6 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault())) != null ? str6.getBytes() : null;
/*      */ 
/*  341 */       if (arrayOfByte5 == null) {
/*  342 */         DatabaseError.throwSqlException(176);
/*      */       }
/*      */ 
/*  345 */       this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/*  348 */       if (this.m_nativeState == 0L)
/*      */       {
/*  350 */         this.sqlWarning = checkError(t2cCreateState(arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, this.m_clientCharacterSet, this.logon_mode, arrayOfShort, arrayOfByte5, arrayOfByte6), this.sqlWarning);
/*      */       }
/*      */       else
/*      */       {
/*  364 */         this.sqlWarning = checkError(t2cLogon(this.m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, this.logon_mode, arrayOfShort, arrayOfByte5, arrayOfByte6), this.sqlWarning);
/*      */       }
/*      */ 
/*  375 */       this.conversion = new DBConversion(arrayOfShort[0], this.m_clientCharacterSet, arrayOfShort[1]);
/*  376 */       this.byteAlign = (byte)(arrayOfShort[2] & 0xFF);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void logoff()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  393 */       if (this.m_nativeState != 0L)
/*      */       {
/*  395 */         checkError(t2cLogoff(this.m_nativeState));
/*      */       }
/*      */     }
/*      */     catch (NullPointerException localNullPointerException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void open(OracleStatement paramOracleStatement)
/*      */     throws SQLException
/*      */   {
/*  417 */     byte[] arrayOfByte = paramOracleStatement.sqlObject.getSql(paramOracleStatement.processEscapes, paramOracleStatement.convertNcharLiterals).getBytes();
/*      */ 
/*  420 */     checkError(t2cCreateStatement(this.m_nativeState, 0L, arrayOfByte, arrayOfByte.length, paramOracleStatement, false, paramOracleStatement.rowPrefetch));
/*      */   }
/*      */ 
/*      */   void doCancel()
/*      */     throws SQLException
/*      */   {
/*  437 */     checkError(t2cCancel(this.m_nativeState));
/*      */   }
/*      */ 
/*      */   native int t2cAbort(long paramLong);
/*      */ 
/*      */   void doAbort() throws SQLException {
/*  444 */     checkError(t2cAbort(this.m_nativeState));
/*      */   }
/*      */ 
/*      */   protected void doSetAutoCommit(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  461 */     checkError(t2cSetAutoCommit(this.m_nativeState, paramBoolean));
/*      */   }
/*      */ 
/*      */   protected void doCommit()
/*      */     throws SQLException
/*      */   {
/*  475 */     checkError(t2cCommit(this.m_nativeState));
/*      */   }
/*      */ 
/*      */   protected void doRollback()
/*      */     throws SQLException
/*      */   {
/*  489 */     checkError(t2cRollback(this.m_nativeState));
/*      */   }
/*      */ 
/*      */   protected String doGetDatabaseProductVersion() throws SQLException
/*      */   {
/*  494 */     byte[] arrayOfByte = t2cGetProductionVersion(this.m_nativeState);
/*      */ 
/*  496 */     return this.conversion.CharBytesToString(arrayOfByte, arrayOfByte.length);
/*      */   }
/*      */ 
/*      */   protected short doGetVersionNumber() throws SQLException
/*      */   {
/*  501 */     int i = 0;
/*      */     try
/*      */     {
/*  505 */       String str1 = doGetDatabaseProductVersion();
/*      */ 
/*  507 */       StringTokenizer localStringTokenizer = new StringTokenizer(str1.trim(), " .", false);
/*  508 */       String str2 = null;
/*  509 */       int j = 0;
/*  510 */       int k = 0;
/*      */ 
/*  512 */       while (localStringTokenizer.hasMoreTokens())
/*      */       {
/*  514 */         str2 = localStringTokenizer.nextToken();
/*      */         try
/*      */         {
/*  518 */           k = Integer.decode(str2).shortValue();
/*  519 */           i = (short)(i * 10 + k);
/*  520 */           j++;
/*      */ 
/*  523 */           if (j == 4) {
/*  524 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (NoSuchElementException localNoSuchElementException)
/*      */     {
/*      */     }
/*      */ 
/*  540 */     if (i == -1) {
/*  541 */       i = 0;
/*      */     }
/*      */ 
/*  546 */     return i;
/*      */   }
/*      */ 
/*      */   public ClobDBAccess createClobDBAccess()
/*      */   {
/*  553 */     return this;
/*      */   }
/*      */ 
/*      */   public BlobDBAccess createBlobDBAccess()
/*      */   {
/*  560 */     return this;
/*      */   }
/*      */ 
/*      */   public BfileDBAccess createBfileDBAccess()
/*      */   {
/*  567 */     return this;
/*      */   }
/*      */ 
/*      */   public CLOB createClob(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  574 */     return new CLOB(this, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public CLOB createClob(byte[] paramArrayOfByte, short paramShort)
/*      */     throws SQLException
/*      */   {
/*  583 */     return new CLOB(this, paramArrayOfByte, paramShort);
/*      */   }
/*      */ 
/*      */   public BLOB createBlob(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  590 */     return new BLOB(this, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public BFILE createBfile(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  597 */     return new BFILE(this, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   protected SQLWarning checkError(int paramInt) throws SQLException
/*      */   {
/*  602 */     return checkError(paramInt, null);
/*      */   }
/*      */ 
/*      */   protected SQLWarning checkError(int paramInt, SQLWarning paramSQLWarning)
/*      */     throws SQLException
/*      */   {
/*  610 */     switch (paramInt)
/*      */     {
/*      */     case 0:
/*  613 */       break;
/*      */     case -1:
/*      */     case 1:
/*  618 */       T2CError localT2CError = new T2CError();
/*      */ 
/*  624 */       int i = -1;
/*      */ 
/*  626 */       if (this.m_nativeState != 0L)
/*      */       {
/*  628 */         i = t2cDescribeError(this.m_nativeState, localT2CError, localT2CError.m_errorMessage);
/*      */       }
/*  631 */       else if (this.fatalErrorNumber != 0)
/*      */       {
/*  633 */         String str1 = DatabaseError.ErrorToSQLState(this.fatalErrorNumber);
/*      */ 
/*  635 */         DatabaseError.throwSqlException(this.fatalErrorMessage, str1, localT2CError.m_errorNumber);
/*      */       }
/*      */       else
/*      */       {
/*  640 */         DatabaseError.throwSqlException(8);
/*      */       }
/*      */ 
/*  645 */       switch (localT2CError.m_errorNumber)
/*      */       {
/*      */       case 28:
/*      */       case 600:
/*      */       case 1012:
/*      */       case 1041:
/*      */       case 3113:
/*      */       case 3114:
/*  653 */         internalClose();
/*      */       }
/*      */ 
/*  658 */       if (i == -1) {
/*  659 */         DatabaseError.throwSqlException(1, "Fetch error message failed!");
/*      */       }
/*      */ 
/*  664 */       int j = 0;
/*      */ 
/*  666 */       while ((j < localT2CError.m_errorMessage.length) && (localT2CError.m_errorMessage[j] != 0)) {
/*  667 */         j++;
/*      */       }
/*  669 */       String str2 = this.conversion.CharBytesToString(localT2CError.m_errorMessage, j, true);
/*      */ 
/*  672 */       String str3 = DatabaseError.ErrorToSQLState(localT2CError.m_errorNumber);
/*      */ 
/*  674 */       if (paramInt == -1)
/*      */       {
/*  676 */         DatabaseError.throwSqlException(str2, str3, localT2CError.m_errorNumber);
/*      */       }
/*      */       else
/*      */       {
/*  681 */         paramSQLWarning = DatabaseError.addSqlWarning(paramSQLWarning, str2, str3, localT2CError.m_errorNumber);
/*      */       }
/*      */ 
/*  685 */       break;
/*      */     }
/*      */ 
/*  691 */     return paramSQLWarning;
/*      */   }
/*      */ 
/*      */   OracleStatement RefCursorBytesToStatement(byte[] paramArrayOfByte, OracleStatement paramOracleStatement)
/*      */     throws SQLException
/*      */   {
/*  697 */     T2CStatement localT2CStatement = new T2CStatement(this, 1, this.defaultRowPrefetch, -1, -1);
/*      */ 
/*  699 */     localT2CStatement.needToParse = false;
/*  700 */     localT2CStatement.serverCursor = true;
/*  701 */     localT2CStatement.isOpen = true;
/*  702 */     localT2CStatement.processEscapes = false;
/*      */ 
/*  704 */     localT2CStatement.prepareForNewResults(true, false);
/*  705 */     localT2CStatement.sqlObject.initialize("select unknown as ref cursor from whatever");
/*      */ 
/*  707 */     localT2CStatement.sqlKind = 0;
/*      */ 
/*  709 */     checkError(t2cCreateStatement(this.m_nativeState, paramOracleStatement.c_state, paramArrayOfByte, paramArrayOfByte.length, localT2CStatement, true, this.defaultRowPrefetch));
/*      */ 
/*  713 */     return localT2CStatement;
/*      */   }
/*      */ 
/*      */   public void getForm(OracleTypeADT paramOracleTypeADT, OracleTypeCLOB paramOracleTypeCLOB, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  720 */     int i = 0;
/*      */ 
/*  722 */     if (paramOracleTypeCLOB != null)
/*      */     {
/*  724 */       String[] arrayOfString1 = new String[1];
/*  725 */       String[] arrayOfString2 = new String[1];
/*      */ 
/*  727 */       SQLName.parse(paramOracleTypeADT.getFullName(), arrayOfString1, arrayOfString2, true);
/*      */ 
/*  729 */       String str = "\"" + arrayOfString1[0] + "\".\"" + arrayOfString2[0] + "\"";
/*      */ 
/*  732 */       byte[] arrayOfByte = this.conversion.StringToCharBytes(str);
/*      */ 
/*  734 */       int j = t2cGetFormOfUse(this.m_nativeState, paramOracleTypeCLOB, arrayOfByte, arrayOfByte.length, paramInt);
/*      */ 
/*  740 */       if (j < 0) {
/*  741 */         checkError(j);
/*      */       }
/*      */ 
/*  746 */       paramOracleTypeCLOB.setForm(j);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getTdoCState(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/*  765 */     String str = "\"" + paramString1 + "\".\"" + paramString2 + "\"";
/*  766 */     byte[] arrayOfByte = this.conversion.StringToCharBytes(str);
/*  767 */     int[] arrayOfInt = new int[1];
/*  768 */     long l = t2cGetTDO(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfInt);
/*  769 */     if (l == 0L)
/*      */     {
/*  773 */       checkError(arrayOfInt[0]);
/*      */     }
/*      */ 
/*  777 */     return l;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Properties getDBAccessProperties()
/*      */     throws SQLException
/*      */   {
/*  791 */     return getOCIHandles();
/*      */   }
/*      */ 
/*      */   public synchronized Properties getOCIHandles() throws SQLException
/*      */   {
/*  796 */     if (this.nativeInfo == null)
/*      */     {
/*  798 */       long[] arrayOfLong = new long[3];
/*      */ 
/*  804 */       checkError(t2cGetHandles(this.m_nativeState, arrayOfLong));
/*      */ 
/*  807 */       this.nativeInfo = new Properties();
/*      */ 
/*  809 */       this.nativeInfo.put("OCIEnvHandle", String.valueOf(arrayOfLong[0]));
/*  810 */       this.nativeInfo.put("OCISvcCtxHandle", String.valueOf(arrayOfLong[1]));
/*  811 */       this.nativeInfo.put("OCIErrHandle", String.valueOf(arrayOfLong[2]));
/*  812 */       this.nativeInfo.put("ClientCharSet", String.valueOf(this.m_clientCharacterSet));
/*      */     }
/*      */ 
/*  815 */     return this.nativeInfo;
/*      */   }
/*      */ 
/*      */   public Properties getServerSessionInfo() throws SQLException
/*      */   {
/*  820 */     if (this.lifecycle != 1) {
/*  821 */       DatabaseError.throwSqlException(8);
/*      */     }
/*  823 */     Properties localProperties = new Properties();
/*  824 */     checkError(t2cGetServerSessionInfo(this.m_nativeState, localProperties));
/*      */ 
/*  826 */     return localProperties;
/*      */   }
/*      */ 
/*      */   public Properties getConnectionPoolInfo()
/*      */     throws SQLException
/*      */   {
/*  834 */     if (this.lifecycle != 1)
/*  835 */       DatabaseError.throwSqlException(8);
/*  836 */     Properties localProperties = new Properties();
/*      */ 
/*  838 */     checkError(t2cGetConnPoolInfo(this.m_nativeState, localProperties));
/*      */ 
/*  840 */     return localProperties;
/*      */   }
/*      */ 
/*      */   public void setConnectionPoolInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */     throws SQLException
/*      */   {
/*  848 */     checkError(t2cSetConnPoolInfo(this.m_nativeState, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*      */   }
/*      */ 
/*      */   public void ociPasswordChange(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/*  856 */     if (this.lifecycle != 1)
/*  857 */       DatabaseError.throwSqlException(8);
/*  858 */     byte[] arrayOfByte1 = paramString1 == null ? new byte[0] : DBConversion.stringToDriverCharBytes(paramString1, this.m_clientCharacterSet);
/*      */ 
/*  862 */     byte[] arrayOfByte2 = paramString2 == null ? new byte[0] : DBConversion.stringToAsciiBytes(paramString2);
/*      */ 
/*  865 */     byte[] arrayOfByte3 = paramString3 == null ? new byte[0] : DBConversion.stringToAsciiBytes(paramString3);
/*      */ 
/*  868 */     this.sqlWarning = checkError(t2cPasswordChange(this.m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length), this.sqlWarning);
/*      */   }
/*      */ 
/*      */   private void processOCIConnectionPooling()
/*      */     throws SQLException
/*      */   {
/*  877 */     if (this.lifecycle != 1)
/*  878 */       DatabaseError.throwSqlException(8);
/*  879 */     byte[] arrayOfByte1 = null;
/*      */ 
/*  881 */     byte[] arrayOfByte2 = this.password == null ? new byte[0] : DBConversion.stringToAsciiBytes(this.password);
/*      */ 
/*  884 */     byte[] arrayOfByte3 = DBConversion.stringToAsciiBytes(this.database);
/*      */ 
/*  886 */     byte[] arrayOfByte4 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault()).getBytes();
/*      */ 
/*  888 */     byte[] arrayOfByte5 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault()).getBytes();
/*      */ 
/*  892 */     String str1 = this.connectionProperties.getProperty("connection_pool");
/*      */ 
/*  895 */     short[] arrayOfShort = new short[3];
/*      */     Object localObject1;
/*      */     Object localObject2;
/*  897 */     if (str1 == "connection_pool")
/*      */     {
/*  899 */       localObject1 = this.connectionProperties.getProperty("oracle.jdbc.ociNlsLangBackwardCompatible");
/*      */ 
/*  902 */       if ((localObject1 != null) && (((String)localObject1).equalsIgnoreCase("true")))
/*      */       {
/*  906 */         this.m_clientCharacterSet = getDriverCharSetIdFromNLS_LANG(this.connectionProperties);
/*      */       }
/*      */       else
/*      */       {
/*  911 */         this.m_clientCharacterSet = getClientCharSetId();
/*      */       }
/*      */ 
/*  914 */       arrayOfByte1 = this.user == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */ 
/*  920 */       this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/*  923 */       this.logon_mode = 5;
/*      */ 
/*  925 */       if (this.m_nativeState == 0L)
/*      */       {
/*  927 */         localObject2 = new int[6];
/*      */ 
/*  929 */         OracleOCIConnectionPool.readPoolConfig(this.connectionProperties, localObject2);
/*      */ 
/*  931 */         this.sqlWarning = checkError(t2cCreateConnPool(arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, this.m_clientCharacterSet, this.logon_mode, localObject2[0], localObject2[1], localObject2[2], localObject2[3], localObject2[4], localObject2[5]), this.sqlWarning);
/*      */ 
/*  942 */         this.versionNumber = 10000;
/*      */       }
/*      */       else
/*      */       {
/*  949 */         throw DatabaseError.newSqlException(0, "Internal Error: ");
/*      */       }
/*      */ 
/*      */     }
/*  955 */     else if (str1 == "connpool_connection")
/*      */     {
/*  957 */       this.logon_mode = 6;
/*      */ 
/*  959 */       localObject1 = (T2CConnection)this.connectionProperties.get("connpool_object");
/*      */ 
/*  962 */       this.m_clientCharacterSet = ((T2CConnection)localObject1).m_clientCharacterSet;
/*      */ 
/*  964 */       arrayOfByte1 = this.user == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */ 
/*  970 */       this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/*  973 */       this.sqlWarning = checkError(t2cConnPoolLogon(((T2CConnection)localObject1).m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, this.logon_mode, 0, 0, null, null, 0, null, 0, null, 0, null, 0, null, 0, arrayOfShort, arrayOfByte4, arrayOfByte5), this.sqlWarning);
/*      */     }
/* 1001 */     else if (str1 == "connpool_alias_connection")
/*      */     {
/* 1003 */       this.logon_mode = 8;
/*      */ 
/* 1006 */       localObject1 = null;
/*      */ 
/* 1008 */       localObject1 = (byte[])this.connectionProperties.get("connection_id");
/*      */ 
/* 1011 */       localObject2 = (T2CConnection)this.connectionProperties.get("connpool_object");
/*      */ 
/* 1014 */       this.m_clientCharacterSet = ((T2CConnection)localObject2).m_clientCharacterSet;
/*      */ 
/* 1016 */       arrayOfByte1 = this.user == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */ 
/* 1022 */       this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/* 1025 */       this.sqlWarning = checkError(t2cConnPoolLogon(((T2CConnection)localObject2).m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, this.logon_mode, 0, 0, null, null, 0, null, 0, null, 0, null, 0, localObject1, localObject1 == null ? 0 : localObject1.length, arrayOfShort, arrayOfByte4, arrayOfByte5), this.sqlWarning);
/*      */     }
/* 1053 */     else if (str1 == "connpool_proxy_connection")
/*      */     {
/* 1055 */       this.logon_mode = 7;
/*      */ 
/* 1058 */       localObject1 = this.connectionProperties.getProperty("proxytype");
/*      */ 
/* 1062 */       localObject2 = (Integer)this.connectionProperties.get("proxy_num_roles");
/*      */ 
/* 1064 */       int i = ((Integer)localObject2).intValue();
/*      */ 
/* 1066 */       String[] arrayOfString = null;
/*      */ 
/* 1068 */       if (i > 0)
/*      */       {
/* 1070 */         arrayOfString = (String[])this.connectionProperties.get("proxy_roles");
/*      */       }
/*      */ 
/* 1074 */       byte[] arrayOfByte6 = null;
/* 1075 */       byte[] arrayOfByte7 = null;
/* 1076 */       byte[] arrayOfByte8 = null;
/* 1077 */       byte[] arrayOfByte9 = null;
/*      */ 
/* 1080 */       int j = 0;
/*      */       String str2;
/* 1083 */       if (localObject1 == "proxytype_user_name")
/*      */       {
/* 1085 */         j = 1;
/*      */ 
/* 1087 */         str2 = this.connectionProperties.getProperty("proxy_user_name");
/*      */ 
/* 1090 */         if (str2 != null) {
/* 1091 */           arrayOfByte6 = str2.getBytes();
/*      */         }
/* 1093 */         str2 = this.connectionProperties.getProperty("proxy_password");
/*      */ 
/* 1096 */         if (str2 != null)
/* 1097 */           arrayOfByte7 = str2.getBytes();
/*      */       }
/* 1099 */       else if (localObject1 == "proxytype_distinguished_name")
/*      */       {
/* 1102 */         j = 2;
/*      */ 
/* 1104 */         str2 = this.connectionProperties.getProperty("proxy_distinguished_name");
/*      */ 
/* 1107 */         if (str2 != null)
/* 1108 */           arrayOfByte8 = str2.getBytes();
/*      */       }
/* 1110 */       else if (localObject1 == "proxytype_certificate")
/*      */       {
/* 1112 */         j = 3;
/*      */ 
/* 1114 */         arrayOfByte9 = (byte[])this.connectionProperties.get("proxy_certificate");
/*      */       }
/*      */       else
/*      */       {
/* 1122 */         DatabaseError.throwSqlException(107);
/*      */       }
/*      */ 
/* 1129 */       T2CConnection localT2CConnection = (T2CConnection)this.connectionProperties.get("connpool_object");
/*      */ 
/* 1132 */       this.m_clientCharacterSet = localT2CConnection.m_clientCharacterSet;
/*      */ 
/* 1134 */       arrayOfByte1 = this.user == null ? new byte[0] : DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */ 
/* 1140 */       this.conversion = new DBConversion(this.m_clientCharacterSet, this.m_clientCharacterSet, this.m_clientCharacterSet);
/*      */ 
/* 1143 */       this.sqlWarning = checkError(t2cConnPoolLogon(localT2CConnection.m_nativeState, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, this.logon_mode, j, i, arrayOfString, arrayOfByte6, arrayOfByte6 == null ? 0 : arrayOfByte6.length, arrayOfByte7, arrayOfByte7 == null ? 0 : arrayOfByte7.length, arrayOfByte8, arrayOfByte8 == null ? 0 : arrayOfByte8.length, arrayOfByte9, arrayOfByte9 == null ? 0 : arrayOfByte9.length, null, 0, arrayOfShort, arrayOfByte4, arrayOfByte5), this.sqlWarning);
/*      */     }
/*      */     else
/*      */     {
/* 1167 */       DatabaseError.throwSqlException(23, "connection-pool-logon");
/*      */     }
/*      */ 
/* 1173 */     this.conversion = new DBConversion(arrayOfShort[0], this.m_clientCharacterSet, arrayOfShort[1]);
/* 1174 */     this.byteAlign = (byte)(arrayOfShort[2] & 0xFF);
/*      */   }
/*      */ 
/*      */   public boolean isDescriptorSharable(OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/* 1188 */     T2CConnection localT2CConnection = this;
/* 1189 */     PhysicalConnection localPhysicalConnection = (PhysicalConnection)paramOracleConnection.getPhysicalConnection();
/*      */ 
/* 1191 */     return localT2CConnection == localPhysicalConnection;
/*      */   }
/*      */ 
/*      */   native int blobRead(long paramLong1, byte[] paramArrayOfByte1, int paramInt1, long paramLong2, int paramInt2, byte[] paramArrayOfByte2, int paramInt3);
/*      */ 
/*      */   native int clobRead(long paramLong1, byte[] paramArrayOfByte, int paramInt1, long paramLong2, int paramInt2, char[] paramArrayOfChar, int paramInt3, boolean paramBoolean);
/*      */ 
/*      */   native int blobWrite(long paramLong1, byte[] paramArrayOfByte1, int paramInt1, long paramLong2, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[][] paramArrayOfByte);
/*      */ 
/*      */   native int clobWrite(long paramLong1, byte[] paramArrayOfByte, int paramInt1, long paramLong2, int paramInt2, char[] paramArrayOfChar, int paramInt3, byte[][] paramArrayOfByte1, boolean paramBoolean);
/*      */ 
/*      */   native long lobGetLength(long paramLong, byte[] paramArrayOfByte, int paramInt);
/*      */ 
/*      */   native int bfileOpen(long paramLong, byte[] paramArrayOfByte, int paramInt, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   native int bfileIsOpen(long paramLong, byte[] paramArrayOfByte, int paramInt, boolean[] paramArrayOfBoolean);
/*      */ 
/*      */   native int bfileExists(long paramLong, byte[] paramArrayOfByte, int paramInt, boolean[] paramArrayOfBoolean);
/*      */ 
/*      */   native String bfileGetName(long paramLong, byte[] paramArrayOfByte, int paramInt);
/*      */ 
/*      */   native String bfileGetDirAlias(long paramLong, byte[] paramArrayOfByte, int paramInt);
/*      */ 
/*      */   native int bfileClose(long paramLong, byte[] paramArrayOfByte, int paramInt, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   native int lobGetChunkSize(long paramLong, byte[] paramArrayOfByte, int paramInt);
/*      */ 
/*      */   native int lobTrim(long paramLong1, int paramInt1, long paramLong2, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   native int lobCreateTemporary(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2, short paramShort, byte[][] paramArrayOfByte);
/*      */ 
/*      */   native int lobFreeTemporary(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   native int lobIsTemporary(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, boolean[] paramArrayOfBoolean);
/*      */ 
/*      */   native int lobOpen(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   native int lobIsOpen(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, boolean[] paramArrayOfBoolean);
/*      */ 
/*      */   native int lobClose(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, byte[][] paramArrayOfByte1);
/*      */ 
/*      */   private long lobLength(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1265 */     long l = 0L;
/*      */ 
/* 1270 */     l = lobGetLength(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length);
/*      */ 
/* 1272 */     checkError((int)l);
/*      */ 
/* 1274 */     return l;
/*      */   }
/*      */ 
/*      */   private int blobRead(byte[] paramArrayOfByte1, long paramLong, int paramInt, byte[] paramArrayOfByte2)
/*      */     throws SQLException
/*      */   {
/* 1288 */     int i = 0;
/*      */ 
/* 1290 */     i = blobRead(this.m_nativeState, paramArrayOfByte1, paramArrayOfByte1.length, paramLong, paramInt, paramArrayOfByte2, paramArrayOfByte2.length);
/*      */ 
/* 1293 */     checkError(i);
/*      */ 
/* 1295 */     return i;
/*      */   }
/*      */ 
/*      */   private int blobWrite(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2, byte[][] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1311 */     int i = 0;
/*      */ 
/* 1313 */     i = blobWrite(this.m_nativeState, paramArrayOfByte1, paramArrayOfByte1.length, paramLong, paramInt2, paramArrayOfByte2, paramInt1, paramArrayOfByte);
/*      */ 
/* 1316 */     checkError(i);
/*      */ 
/* 1318 */     return i;
/*      */   }
/*      */ 
/*      */   private int clobWrite(byte[] paramArrayOfByte, long paramLong, char[] paramArrayOfChar, byte[][] paramArrayOfByte1, boolean paramBoolean, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1335 */     int i = 0;
/*      */ 
/* 1337 */     i = clobWrite(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length, paramLong, paramInt2, paramArrayOfChar, paramInt1, paramArrayOfByte1, paramBoolean);
/*      */ 
/* 1340 */     checkError(i);
/*      */ 
/* 1342 */     return i;
/*      */   }
/*      */ 
/*      */   private int lobGetChunkSize(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1348 */     int i = 0;
/*      */ 
/* 1353 */     i = lobGetChunkSize(this.m_nativeState, paramArrayOfByte, paramArrayOfByte.length);
/*      */ 
/* 1355 */     checkError(i);
/*      */ 
/* 1357 */     return i;
/*      */   }
/*      */ 
/*      */   public long length(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1368 */     byte[] arrayOfByte = null;
/*      */ 
/* 1373 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1374 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1377 */     return lobLength(arrayOfByte);
/*      */   }
/*      */ 
/*      */   public long position(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1395 */     if (paramLong < 1L)
/*      */     {
/* 1399 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 1403 */     long l = LobPlsqlUtil.hasPattern(paramBFILE, paramArrayOfByte, paramLong);
/*      */ 
/* 1405 */     l = l == 0L ? -1L : l;
/*      */ 
/* 1410 */     return l;
/*      */   }
/*      */ 
/*      */   public long position(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1428 */     if (paramLong < 1L)
/*      */     {
/* 1432 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 1436 */     long l = LobPlsqlUtil.isSubLob(paramBFILE1, paramBFILE2, paramLong);
/*      */ 
/* 1438 */     l = l == 0L ? -1L : l;
/*      */ 
/* 1443 */     return l;
/*      */   }
/*      */ 
/*      */   public int getBytes(BFILE paramBFILE, long paramLong, int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1457 */     byte[] arrayOfByte = null;
/*      */ 
/* 1462 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1463 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1466 */     if ((paramInt <= 0) || (paramArrayOfByte == null)) {
/* 1467 */       return 0;
/*      */     }
/* 1469 */     if (paramInt > paramArrayOfByte.length) {
/* 1470 */       paramInt = paramArrayOfByte.length;
/*      */     }
/* 1472 */     return blobRead(arrayOfByte, paramLong, paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public String getName(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1484 */     byte[] arrayOfByte = null;
/* 1485 */     String str = null;
/*      */ 
/* 1490 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1491 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1494 */     str = bfileGetName(this.m_nativeState, arrayOfByte, arrayOfByte.length);
/*      */ 
/* 1496 */     checkError(str.length());
/*      */ 
/* 1498 */     return str;
/*      */   }
/*      */ 
/*      */   public String getDirAlias(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1510 */     byte[] arrayOfByte = null;
/* 1511 */     String str = null;
/*      */ 
/* 1516 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1517 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1520 */     str = bfileGetDirAlias(this.m_nativeState, arrayOfByte, arrayOfByte.length);
/*      */ 
/* 1522 */     checkError(str.length());
/*      */ 
/* 1524 */     return str;
/*      */   }
/*      */ 
/*      */   public void openFile(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1535 */     byte[] arrayOfByte = null;
/*      */ 
/* 1540 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1541 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1544 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 1546 */     checkError(bfileOpen(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 1549 */     paramBFILE.setLocator(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isFileOpen(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1564 */     byte[] arrayOfByte = null;
/*      */ 
/* 1569 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1570 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1573 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 1575 */     checkError(bfileIsOpen(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 1577 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public boolean fileExists(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1591 */     byte[] arrayOfByte = null;
/*      */ 
/* 1596 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1597 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1600 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 1602 */     checkError(bfileExists(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 1604 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public void closeFile(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1615 */     byte[] arrayOfByte = null;
/*      */ 
/* 1620 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1621 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.getLocator()) != null), 54);
/*      */ 
/* 1624 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 1626 */     checkError(bfileClose(this.m_nativeState, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 1629 */     paramBFILE.setLocator(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public void open(BFILE paramBFILE, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1642 */     byte[] arrayOfByte = null;
/*      */ 
/* 1647 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1648 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);
/*      */ 
/* 1651 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 1653 */     checkError(lobOpen(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));
/*      */ 
/* 1656 */     paramBFILE.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public void close(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1667 */     byte[] arrayOfByte = null;
/*      */ 
/* 1672 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1673 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);
/*      */ 
/* 1676 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 1678 */     checkError(lobClose(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 1681 */     paramBFILE.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isOpen(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 1693 */     byte[] arrayOfByte = null;
/*      */ 
/* 1698 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1699 */     checkTrue((paramBFILE != null) && ((arrayOfByte = paramBFILE.shareBytes()) != null), 54);
/*      */ 
/* 1702 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 1704 */     checkError(lobIsOpen(this.m_nativeState, 114, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 1707 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public InputStream newInputStream(BFILE paramBFILE, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1727 */     if (paramLong == 0L)
/*      */     {
/* 1729 */       return new OracleBlobInputStream(paramBFILE, paramInt);
/*      */     }
/*      */ 
/* 1733 */     return new OracleBlobInputStream(paramBFILE, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public InputStream newConversionInputStream(BFILE paramBFILE, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1753 */     checkTrue((paramBFILE != null) && (paramBFILE.shareBytes() != null), 54);
/*      */ 
/* 1756 */     OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBFILE.getBinaryStream(), paramInt);
/*      */ 
/* 1761 */     return localOracleConversionInputStream;
/*      */   }
/*      */ 
/*      */   public Reader newConversionReader(BFILE paramBFILE, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1781 */     checkTrue((paramBFILE != null) && (paramBFILE.shareBytes() != null), 54);
/*      */ 
/* 1784 */     OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBFILE.getBinaryStream(), paramInt);
/*      */ 
/* 1789 */     return localOracleConversionReader;
/*      */   }
/*      */ 
/*      */   public long length(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 1801 */     byte[] arrayOfByte = null;
/*      */ 
/* 1806 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1807 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);
/*      */ 
/* 1810 */     return lobLength(arrayOfByte);
/*      */   }
/*      */ 
/*      */   public long position(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1829 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1830 */     checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);
/*      */ 
/* 1833 */     if (paramLong < 1L)
/*      */     {
/* 1838 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 1842 */     long l = LobPlsqlUtil.hasPattern(paramBLOB, paramArrayOfByte, paramLong);
/*      */ 
/* 1844 */     l = l == 0L ? -1L : l;
/*      */ 
/* 1848 */     return l;
/*      */   }
/*      */ 
/*      */   public long position(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1865 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1866 */     checkTrue((paramBLOB1 != null) && (paramBLOB1.shareBytes() != null), 54);
/*      */ 
/* 1868 */     checkTrue((paramBLOB2 != null) && (paramBLOB2.shareBytes() != null), 54);
/*      */ 
/* 1871 */     if (paramLong < 1L)
/*      */     {
/* 1876 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 1880 */     long l = LobPlsqlUtil.isSubLob(paramBLOB1, paramBLOB2, paramLong);
/*      */ 
/* 1882 */     l = l == 0L ? -1L : l;
/*      */ 
/* 1886 */     return l;
/*      */   }
/*      */ 
/*      */   public int getBytes(BLOB paramBLOB, long paramLong, int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1901 */     byte[] arrayOfByte = null;
/*      */ 
/* 1906 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1907 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);
/*      */ 
/* 1910 */     if ((paramInt <= 0) || (paramArrayOfByte == null)) {
/* 1911 */       return 0;
/*      */     }
/* 1913 */     if (paramInt > paramArrayOfByte.length) {
/* 1914 */       paramInt = paramArrayOfByte.length;
/*      */     }
/* 1916 */     return blobRead(arrayOfByte, paramLong, paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public int putBytes(BLOB paramBLOB, long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1933 */     checkTrue(paramLong >= 0L, 68);
/*      */ 
/* 1935 */     int i = 0;
/*      */ 
/* 1937 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0) || (paramInt2 <= 0)) {
/* 1938 */       i = 0;
/*      */     }
/*      */     else {
/* 1941 */       byte[] arrayOfByte = null;
/*      */ 
/* 1943 */       checkTrue(this.m_nativeState != 0L, 8);
/* 1944 */       checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);
/*      */ 
/* 1947 */       if (paramArrayOfByte == null) {
/* 1948 */         return 0;
/*      */       }
/* 1950 */       byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 1952 */       i = blobWrite(arrayOfByte, paramLong, paramArrayOfByte, arrayOfByte1, paramInt1, paramInt2);
/*      */ 
/* 1955 */       paramBLOB.setLocator(arrayOfByte1[0]);
/*      */     }
/*      */ 
/* 1958 */     return i;
/*      */   }
/*      */ 
/*      */   public int getChunkSize(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 1968 */     byte[] arrayOfByte = null;
/*      */ 
/* 1973 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1974 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.getLocator()) != null), 54);
/*      */ 
/* 1977 */     return lobGetChunkSize(arrayOfByte);
/*      */   }
/*      */ 
/*      */   public void trim(BLOB paramBLOB, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1989 */     byte[] arrayOfByte = null;
/*      */ 
/* 1994 */     checkTrue(this.m_nativeState != 0L, 8);
/* 1995 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 1998 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2000 */     checkError(lobTrim(this.m_nativeState, 113, paramLong, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2003 */     paramBLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2020 */     BLOB localBLOB = null;
/*      */ 
/* 2025 */     checkTrue(this.m_nativeState != 0L, 8);
/*      */ 
/* 2027 */     localBLOB = new BLOB((PhysicalConnection)paramConnection);
/*      */ 
/* 2029 */     byte[][] arrayOfByte = new byte[1][];
/*      */ 
/* 2031 */     checkError(lobCreateTemporary(this.m_nativeState, 113, paramBoolean, paramInt, 0, arrayOfByte));
/*      */ 
/* 2034 */     localBLOB.setShareBytes(arrayOfByte[0]);
/*      */ 
/* 2036 */     return localBLOB;
/*      */   }
/*      */ 
/*      */   public void freeTemporary(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 2048 */     byte[] arrayOfByte = null;
/*      */ 
/* 2053 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2054 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 2057 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2059 */     checkError(lobFreeTemporary(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2062 */     paramBLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isTemporary(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 2077 */     byte[] arrayOfByte = null;
/*      */ 
/* 2082 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 2085 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 2087 */     checkError(lobIsTemporary(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 2090 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public void open(BLOB paramBLOB, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2101 */     byte[] arrayOfByte = null;
/*      */ 
/* 2106 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2107 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 2110 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2112 */     checkError(lobOpen(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));
/*      */ 
/* 2115 */     paramBLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public void close(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 2126 */     byte[] arrayOfByte = null;
/*      */ 
/* 2131 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2132 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 2135 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2137 */     checkError(lobClose(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2140 */     paramBLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isOpen(BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 2152 */     byte[] arrayOfByte = null;
/*      */ 
/* 2157 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2158 */     checkTrue((paramBLOB != null) && ((arrayOfByte = paramBLOB.shareBytes()) != null), 54);
/*      */ 
/* 2161 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 2163 */     checkError(lobIsOpen(this.m_nativeState, 113, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 2166 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public InputStream newInputStream(BLOB paramBLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2185 */     if (paramLong == 0L)
/*      */     {
/* 2187 */       return new OracleBlobInputStream(paramBLOB, paramInt);
/*      */     }
/*      */ 
/* 2191 */     return new OracleBlobInputStream(paramBLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public OutputStream newOutputStream(BLOB paramBLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2211 */     if (paramLong == 0L)
/*      */     {
/* 2213 */       return new OracleBlobOutputStream(paramBLOB, paramInt);
/*      */     }
/*      */ 
/* 2217 */     return new OracleBlobOutputStream(paramBLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public InputStream newConversionInputStream(BLOB paramBLOB, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2238 */     checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);
/*      */ 
/* 2241 */     OracleConversionInputStream localOracleConversionInputStream = new OracleConversionInputStream(this.conversion, paramBLOB.getBinaryStream(), paramInt);
/*      */ 
/* 2246 */     return localOracleConversionInputStream;
/*      */   }
/*      */ 
/*      */   public Reader newConversionReader(BLOB paramBLOB, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2265 */     checkTrue((paramBLOB != null) && (paramBLOB.shareBytes() != null), 54);
/*      */ 
/* 2268 */     OracleConversionReader localOracleConversionReader = new OracleConversionReader(this.conversion, paramBLOB.getBinaryStream(), paramInt);
/*      */ 
/* 2273 */     return localOracleConversionReader;
/*      */   }
/*      */ 
/*      */   public long length(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2289 */     byte[] arrayOfByte = null;
/*      */ 
/* 2294 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2295 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);
/*      */ 
/* 2298 */     return lobLength(arrayOfByte);
/*      */   }
/*      */ 
/*      */   public long position(CLOB paramCLOB, String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2316 */     if (paramString == null) {
/* 2317 */       throw new SQLException("pattern cannot be null.");
/*      */     }
/* 2319 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2320 */     checkTrue((paramCLOB != null) && (paramCLOB.shareBytes() != null), 54);
/*      */ 
/* 2323 */     if (paramLong < 1L)
/*      */     {
/* 2328 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 2332 */     char[] arrayOfChar = new char[paramString.length()];
/*      */ 
/* 2334 */     paramString.getChars(0, arrayOfChar.length, arrayOfChar, 0);
/*      */ 
/* 2336 */     long l = LobPlsqlUtil.hasPattern(paramCLOB, arrayOfChar, paramLong);
/*      */ 
/* 2338 */     l = l == 0L ? -1L : l;
/*      */ 
/* 2342 */     return l;
/*      */   }
/*      */ 
/*      */   public long position(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2359 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2360 */     checkTrue((paramCLOB1 != null) && (paramCLOB1.shareBytes() != null), 54);
/*      */ 
/* 2362 */     checkTrue((paramCLOB2 != null) && (paramCLOB2.shareBytes() != null), 54);
/*      */ 
/* 2365 */     if (paramLong < 1L)
/*      */     {
/* 2370 */       DatabaseError.throwSqlException(68, "position()");
/*      */     }
/*      */ 
/* 2374 */     long l = LobPlsqlUtil.isSubLob(paramCLOB1, paramCLOB2, paramLong);
/*      */ 
/* 2376 */     l = l == 0L ? -1L : l;
/*      */ 
/* 2380 */     return l;
/*      */   }
/*      */ 
/*      */   public int getChars(CLOB paramCLOB, long paramLong, int paramInt, char[] paramArrayOfChar)
/*      */     throws SQLException
/*      */   {
/* 2395 */     byte[] arrayOfByte = null;
/*      */ 
/* 2400 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2401 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);
/*      */ 
/* 2404 */     if ((paramInt <= 0) || (paramArrayOfChar == null)) {
/* 2405 */       return 0;
/*      */     }
/* 2407 */     if (paramInt > paramArrayOfChar.length) {
/* 2408 */       paramInt = paramArrayOfChar.length;
/*      */     }
/* 2410 */     int i = 0;
/*      */ 
/* 2412 */     i = clobRead(this.m_nativeState, arrayOfByte, arrayOfByte.length, paramLong, paramInt, paramArrayOfChar, paramArrayOfChar.length, paramCLOB.isNCLOB());
/*      */ 
/* 2415 */     checkError(i);
/*      */ 
/* 2417 */     return i;
/*      */   }
/*      */ 
/*      */   public int putChars(CLOB paramCLOB, long paramLong, char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 2436 */     byte[] arrayOfByte = null;
/*      */ 
/* 2441 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2442 */     checkTrue(paramLong >= 0L, 68);
/*      */ 
/* 2444 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);
/*      */ 
/* 2447 */     if (paramArrayOfChar == null) {
/* 2448 */       return 0;
/*      */     }
/* 2450 */     byte[][] arrayOfByte1 = new byte[1][];
/* 2451 */     int i = clobWrite(arrayOfByte, paramLong, paramArrayOfChar, arrayOfByte1, paramCLOB.isNCLOB(), paramInt1, paramInt2);
/*      */ 
/* 2454 */     paramCLOB.setLocator(arrayOfByte1[0]);
/*      */ 
/* 2456 */     return i;
/*      */   }
/*      */ 
/*      */   public int getChunkSize(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2466 */     byte[] arrayOfByte = null;
/*      */ 
/* 2471 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2472 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.getLocator()) != null), 54);
/*      */ 
/* 2475 */     return lobGetChunkSize(arrayOfByte);
/*      */   }
/*      */ 
/*      */   public void trim(CLOB paramCLOB, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2487 */     byte[] arrayOfByte = null;
/*      */ 
/* 2492 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2493 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2496 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2498 */     checkError(lobTrim(this.m_nativeState, 112, paramLong, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2501 */     paramCLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public CLOB createTemporaryClob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 2519 */     CLOB localCLOB = null;
/*      */ 
/* 2524 */     checkTrue(this.m_nativeState != 0L, 8);
/*      */ 
/* 2526 */     localCLOB = new CLOB((PhysicalConnection)paramConnection);
/*      */ 
/* 2528 */     byte[][] arrayOfByte = new byte[1][];
/*      */ 
/* 2530 */     checkError(lobCreateTemporary(this.m_nativeState, 112, paramBoolean, paramInt, paramShort, arrayOfByte));
/*      */ 
/* 2533 */     localCLOB.setShareBytes(arrayOfByte[0]);
/*      */ 
/* 2535 */     return localCLOB;
/*      */   }
/*      */ 
/*      */   public void freeTemporary(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2547 */     byte[] arrayOfByte = null;
/*      */ 
/* 2552 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2553 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2556 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2558 */     checkError(lobFreeTemporary(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2561 */     paramCLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isTemporary(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2577 */     byte[] arrayOfByte = null;
/*      */ 
/* 2582 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2585 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 2587 */     checkError(lobIsTemporary(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 2590 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public void open(CLOB paramCLOB, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2601 */     byte[] arrayOfByte = null;
/*      */ 
/* 2606 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2607 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2610 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2612 */     checkError(lobOpen(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, paramInt, arrayOfByte1));
/*      */ 
/* 2615 */     paramCLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public void close(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2626 */     byte[] arrayOfByte = null;
/*      */ 
/* 2631 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2632 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2635 */     byte[][] arrayOfByte1 = new byte[1][];
/*      */ 
/* 2637 */     checkError(lobClose(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfByte1));
/*      */ 
/* 2640 */     paramCLOB.setShareBytes(arrayOfByte1[0]);
/*      */   }
/*      */ 
/*      */   public boolean isOpen(CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 2652 */     byte[] arrayOfByte = null;
/*      */ 
/* 2657 */     checkTrue(this.m_nativeState != 0L, 8);
/* 2658 */     checkTrue((paramCLOB != null) && ((arrayOfByte = paramCLOB.shareBytes()) != null), 54);
/*      */ 
/* 2661 */     boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/* 2663 */     checkError(lobIsOpen(this.m_nativeState, 112, arrayOfByte, arrayOfByte.length, arrayOfBoolean));
/*      */ 
/* 2666 */     return arrayOfBoolean[0];
/*      */   }
/*      */ 
/*      */   public InputStream newInputStream(CLOB paramCLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2686 */     if (paramLong == 0L)
/*      */     {
/* 2688 */       return new OracleClobInputStream(paramCLOB, paramInt);
/*      */     }
/*      */ 
/* 2692 */     return new OracleClobInputStream(paramCLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public OutputStream newOutputStream(CLOB paramCLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2712 */     if (paramLong == 0L)
/*      */     {
/* 2714 */       return new OracleClobOutputStream(paramCLOB, paramInt);
/*      */     }
/*      */ 
/* 2718 */     return new OracleClobOutputStream(paramCLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public Reader newReader(CLOB paramCLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2738 */     if (paramLong == 0L)
/*      */     {
/* 2740 */       return new OracleClobReader(paramCLOB, paramInt);
/*      */     }
/*      */ 
/* 2744 */     return new OracleClobReader(paramCLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public Writer newWriter(CLOB paramCLOB, int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2764 */     if (paramLong == 0L)
/*      */     {
/* 2766 */       return new OracleClobWriter(paramCLOB, paramInt);
/*      */     }
/*      */ 
/* 2770 */     return new OracleClobWriter(paramCLOB, paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public synchronized void registerTAFCallback(OracleOCIFailover paramOracleOCIFailover, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 2795 */     this.appCallback = paramOracleOCIFailover;
/* 2796 */     this.appCallbackObject = paramObject;
/*      */ 
/* 2798 */     checkError(t2cRegisterTAFCallback(this.m_nativeState));
/*      */   }
/*      */ 
/*      */   synchronized int callTAFCallbackMethod(int paramInt1, int paramInt2)
/*      */   {
/* 2806 */     int i = 0;
/*      */ 
/* 2809 */     if (this.appCallback != null) {
/* 2810 */       i = this.appCallback.callbackFn(this, this.appCallbackObject, paramInt1, paramInt2);
/*      */     }
/* 2812 */     return i;
/*      */   }
/*      */ 
/*      */   public int getHeapAllocSize()
/*      */     throws SQLException
/*      */   {
/* 2826 */     if (this.lifecycle != 1) {
/* 2827 */       DatabaseError.throwSqlException(8);
/*      */     }
/*      */ 
/* 2831 */     int i = t2cGetHeapAllocSize(this.m_nativeState);
/*      */ 
/* 2833 */     if (i < 0)
/*      */     {
/* 2835 */       if (i == -999) {
/* 2836 */         DatabaseError.throwSqlException(23);
/*      */       }
/*      */ 
/* 2840 */       DatabaseError.throwSqlException(89);
/*      */     }
/*      */ 
/* 2843 */     return i;
/*      */   }
/*      */ 
/*      */   public int getOCIEnvHeapAllocSize()
/*      */     throws SQLException
/*      */   {
/* 2858 */     if (this.lifecycle != 1) {
/* 2859 */       DatabaseError.throwSqlException(8);
/*      */     }
/*      */ 
/* 2863 */     int i = t2cGetOciEnvHeapAllocSize(this.m_nativeState);
/*      */ 
/* 2865 */     if (i < 0)
/*      */     {
/* 2867 */       if (i == -999)
/* 2868 */         DatabaseError.throwSqlException(23);
/*      */       else {
/* 2870 */         checkError(i);
/*      */       }
/*      */ 
/* 2873 */       DatabaseError.throwSqlException(89);
/*      */     }
/*      */ 
/* 2876 */     return i;
/*      */   }
/*      */ 
/*      */   public static final short getClientCharSetId()
/*      */   {
/* 2885 */     return 871;
/*      */   }
/*      */ 
/*      */   public static short getDriverCharSetIdFromNLS_LANG(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/* 2901 */     if (!isLibraryLoaded) {
/* 2902 */       loadNativeLibrary(paramProperties);
/*      */     }
/*      */ 
/* 2907 */     int i = t2cGetDriverCharSetFromNlsLang();
/*      */ 
/* 2912 */     if (i < 0) {
/* 2913 */       DatabaseError.throwSqlException(8);
/*      */     }
/*      */ 
/* 2918 */     return i;
/*      */   }
/*      */ 
/*      */   void doProxySession(int paramInt, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/* 2929 */     Object localObject1 = (byte[][])null;
/*      */ 
/* 2935 */     int i = 0;
/*      */ 
/* 2937 */     this.savedUser = this.user;
/* 2938 */     this.user = null;
/*      */     byte[] arrayOfByte4;
/*      */     byte[] arrayOfByte3;
/*      */     byte[] arrayOfByte2;
/* 2940 */     byte[] arrayOfByte1 = arrayOfByte2 = arrayOfByte3 = arrayOfByte4 = new byte[0];
/*      */ 
/* 2942 */     switch (paramInt)
/*      */     {
/*      */     case 1:
/* 2945 */       this.user = paramProperties.getProperty("PROXY_USER_NAME");
/* 2946 */       String str1 = paramProperties.getProperty("PROXY_USER_PASSWORD");
/* 2947 */       if (this.user != null) {
/* 2948 */         arrayOfByte1 = DBConversion.stringToDriverCharBytes(this.user, this.m_clientCharacterSet);
/*      */       }
/* 2950 */       if (str1 == null) break;
/* 2951 */       arrayOfByte2 = DBConversion.stringToAsciiBytes(str1); break;
/*      */     case 2:
/* 2954 */       String str2 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");
/* 2955 */       if (str2 == null) break;
/* 2956 */       arrayOfByte3 = DBConversion.stringToDriverCharBytes(str2, this.m_clientCharacterSet); break;
/*      */     case 3:
/* 2960 */       Object localObject2 = paramProperties.get("PROXY_CERTIFICATE");
/* 2961 */       arrayOfByte4 = (byte[])localObject2;
/*      */     }
/*      */ 
/* 2964 */     String[] arrayOfString = (String[])paramProperties.get("PROXY_ROLES");
/*      */ 
/* 2966 */     if (arrayOfString != null)
/*      */     {
/* 2968 */       i = arrayOfString.length;
/* 2969 */       localObject1 = new byte[i][];
/* 2970 */       for (int j = 0; j < i; j++)
/*      */       {
/* 2972 */         if (arrayOfString[j] == null) {
/* 2973 */           DatabaseError.throwSqlException(150);
/*      */         }
/* 2975 */         localObject1[j] = DBConversion.stringToDriverCharBytes(arrayOfString[j], this.m_clientCharacterSet);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2980 */     this.sqlWarning = checkError(t2cDoProxySession(this.m_nativeState, paramInt, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, i, localObject1), this.sqlWarning);
/*      */ 
/* 2994 */     this.isProxy = true;
/*      */   }
/*      */ 
/*      */   void closeProxySession() throws SQLException
/*      */   {
/* 2999 */     checkError(t2cCloseProxySession(this.m_nativeState));
/* 3000 */     this.user = this.savedUser;
/*      */   }
/*      */ 
/*      */   protected void doDescribeTable(AutoKeyInfo paramAutoKeyInfo) throws SQLException
/*      */   {
/* 3006 */     String str = paramAutoKeyInfo.getTableName();
/*      */ 
/* 3008 */     byte[] arrayOfByte = DBConversion.stringToDriverCharBytes(str, this.m_clientCharacterSet);
/*      */ 
/* 3011 */     int i = 0;
/*      */     int j;
/*      */     do {
/* 3016 */       j = t2cDescribeTable(this.m_nativeState, arrayOfByte, arrayOfByte.length, this.queryMetaData1, this.queryMetaData2, this.queryMetaData1Offset, this.queryMetaData2Offset, this.queryMetaData1Size, this.queryMetaData2Size);
/*      */ 
/* 3026 */       if (j == -1)
/*      */       {
/* 3028 */         checkError(j);
/*      */       }
/*      */ 
/* 3032 */       if (j != T2CStatement.T2C_EXTEND_BUFFER)
/*      */         continue;
/* 3034 */       i = 1;
/*      */ 
/* 3036 */       reallocateQueryMetaData(this.queryMetaData1Size * 2, this.queryMetaData2Size * 2);
/*      */     }
/*      */ 
/* 3039 */     while (i != 0);
/*      */ 
/* 3041 */     processDescribeTableData(j, paramAutoKeyInfo);
/*      */   }
/*      */ 
/*      */   private void processDescribeTableData(int paramInt, AutoKeyInfo paramAutoKeyInfo)
/*      */     throws SQLException
/*      */   {
/* 3048 */     short[] arrayOfShort = this.queryMetaData1;
/* 3049 */     byte[] arrayOfByte = this.queryMetaData2;
/* 3050 */     int i = this.queryMetaData1Offset;
/* 3051 */     int j = this.queryMetaData2Offset;
/*      */ 
/* 3065 */     paramAutoKeyInfo.allocateSpaceForDescribedData(paramInt);
/*      */ 
/* 3067 */     for (int i5 = 0; i5 < paramInt; i5++)
/*      */     {
/* 3069 */       int m = arrayOfShort[(i + 0)];
/* 3070 */       int k = arrayOfShort[(i + 6)];
/* 3071 */       String str1 = bytes2String(arrayOfByte, j, k, this.conversion);
/*      */ 
/* 3074 */       int n = arrayOfShort[(i + 1)];
/* 3075 */       int i1 = arrayOfShort[(i + 11)];
/* 3076 */       boolean bool = arrayOfShort[(i + 2)] != 0;
/* 3077 */       short s = arrayOfShort[(i + 5)];
/* 3078 */       int i2 = arrayOfShort[(i + 3)];
/* 3079 */       int i3 = arrayOfShort[(i + 4)];
/* 3080 */       int i4 = arrayOfShort[(i + 12)];
/*      */ 
/* 3082 */       j += k;
/* 3083 */       i += 13;
/*      */ 
/* 3085 */       String str2 = null;
/* 3086 */       if (i4 > 0)
/*      */       {
/* 3088 */         str2 = bytes2String(arrayOfByte, j, i4, this.conversion);
/*      */ 
/* 3090 */         j += i4;
/*      */       }
/*      */ 
/* 3094 */       paramAutoKeyInfo.fillDescribedData(i5, str1, m, i1 > 0 ? i1 : n, bool, s, i2, i3, str2);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doSetApplicationContext(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 3106 */     checkError(t2cSetApplicationContext(this.m_nativeState, paramString1, paramString2, paramString3));
/*      */   }
/*      */ 
/*      */   void doClearAllApplicationContext(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3113 */     checkError(t2cClearAllApplicationContext(this.m_nativeState, paramString));
/*      */   }
/*      */ 
/*      */   private static final void loadNativeLibrary(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/* 3123 */     String str = null;
/* 3124 */     if (paramProperties != null) {
/* 3125 */       str = paramProperties.getProperty("oracle.jdbc.ocinativelibrary");
/*      */     }
/*      */ 
/* 3129 */     if ((str == null) || (str.equals("ocijdbc10")))
/*      */     {
/* 3131 */       synchronized (T2CConnection.class)
/*      */       {
/* 3133 */         if (!isLibraryLoaded)
/*      */         {
/* 3135 */           AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run()
/*      */             {
/* 3139 */               System.loadLibrary("ocijdbc10");
/* 3140 */               return null;
/*      */             }
/*      */           });
/* 3143 */           isLibraryLoaded = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3152 */     synchronized (T2CConnection.class)
/*      */     {
/*      */       try
/*      */       {
/* 3156 */         System.loadLibrary(str);
/* 3157 */         isLibraryLoaded = true;
/*      */       }
/*      */       catch (SecurityException localSecurityException)
/*      */       {
/* 3161 */         if (!isLibraryLoaded)
/*      */         {
/* 3163 */           System.loadLibrary(str);
/* 3164 */           isLibraryLoaded = true;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private final void checkTrue(boolean paramBoolean, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3177 */     if (!paramBoolean)
/* 3178 */       DatabaseError.throwSqlException(paramInt);
/*      */   }
/*      */ 
/*      */   boolean useLittleEndianSetCHARBinder() throws SQLException
/*      */   {
/* 3183 */     return t2cPlatformIsLittleEndian(this.m_nativeState);
/*      */   }
/*      */ 
/*      */   public void getPropertyForPooledConnection(OraclePooledConnection paramOraclePooledConnection)
/*      */     throws SQLException
/*      */   {
/* 3189 */     super.getPropertyForPooledConnection(paramOraclePooledConnection, this.password);
/*      */   }
/*      */ 
/*      */   static final char[] getCharArray(String paramString)
/*      */   {
/* 3194 */     char[] arrayOfChar = null;
/*      */ 
/* 3196 */     if (paramString == null)
/*      */     {
/* 3198 */       arrayOfChar = new char[0];
/*      */     }
/*      */     else
/*      */     {
/* 3202 */       arrayOfChar = new char[paramString.length()];
/*      */ 
/* 3204 */       paramString.getChars(0, paramString.length(), arrayOfChar, 0);
/*      */     }
/*      */ 
/* 3207 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */   static String bytes2String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, DBConversion paramDBConversion)
/*      */     throws SQLException
/*      */   {
/* 3215 */     byte[] arrayOfByte = new byte[paramInt2];
/* 3216 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/*      */ 
/* 3218 */     return paramDBConversion.CharBytesToString(arrayOfByte, paramInt2);
/*      */   }
/*      */ 
/*      */   static native short t2cGetServerSessionInfo(long paramLong, Properties paramProperties);
/*      */ 
/*      */   static native short t2cGetDriverCharSetFromNlsLang();
/*      */ 
/*      */   native int t2cDescribeError(long paramLong, T2CError paramT2CError, byte[] paramArrayOfByte);
/*      */ 
/*      */   native int t2cCreateState(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, short paramShort, int paramInt5, short[] paramArrayOfShort, byte[] paramArrayOfByte5, byte[] paramArrayOfByte6);
/*      */ 
/*      */   native int t2cLogon(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, int paramInt5, short[] paramArrayOfShort, byte[] paramArrayOfByte5, byte[] paramArrayOfByte6);
/*      */ 
/*      */   private native int t2cLogoff(long paramLong);
/*      */ 
/*      */   private native int t2cCancel(long paramLong);
/*      */ 
/*      */   private native int t2cCreateStatement(long paramLong1, long paramLong2, byte[] paramArrayOfByte, int paramInt1, OracleStatement paramOracleStatement, boolean paramBoolean, int paramInt2);
/*      */ 
/*      */   private native int t2cSetAutoCommit(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native int t2cCommit(long paramLong);
/*      */ 
/*      */   private native int t2cRollback(long paramLong);
/*      */ 
/*      */   private native byte[] t2cGetProductionVersion(long paramLong);
/*      */ 
/*      */   private native int t2cGetVersionNumber(long paramLong);
/*      */ 
/*      */   private native int t2cGetDefaultStreamChunkSize(long paramLong);
/*      */ 
/*      */   native int t2cGetFormOfUse(long paramLong, OracleTypeCLOB paramOracleTypeCLOB, byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*      */ 
/*      */   native long t2cGetTDO(long paramLong, byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt);
/*      */ 
/*      */   native int t2cCreateConnPool(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, short paramShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);
/*      */ 
/*      */   native int t2cConnPoolLogon(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String[] paramArrayOfString, byte[] paramArrayOfByte4, int paramInt7, byte[] paramArrayOfByte5, int paramInt8, byte[] paramArrayOfByte6, int paramInt9, byte[] paramArrayOfByte7, int paramInt10, byte[] paramArrayOfByte8, int paramInt11, short[] paramArrayOfShort, byte[] paramArrayOfByte9, byte[] paramArrayOfByte10);
/*      */ 
/*      */   native int t2cGetConnPoolInfo(long paramLong, Properties paramProperties);
/*      */ 
/*      */   native int t2cSetConnPoolInfo(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*      */ 
/*      */   native int t2cPasswordChange(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3);
/*      */ 
/*      */   protected native byte[] t2cGetConnectionId(long paramLong);
/*      */ 
/*      */   native int t2cGetHandles(long paramLong, long[] paramArrayOfLong);
/*      */ 
/*      */   native int t2cUseConnection(long paramLong1, long paramLong2, long paramLong3, long paramLong4, short[] paramArrayOfShort);
/*      */ 
/*      */   native boolean t2cPlatformIsLittleEndian(long paramLong);
/*      */ 
/*      */   native int t2cRegisterTAFCallback(long paramLong);
/*      */ 
/*      */   native int t2cGetHeapAllocSize(long paramLong);
/*      */ 
/*      */   native int t2cGetOciEnvHeapAllocSize(long paramLong);
/*      */ 
/*      */   native int t2cDoProxySession(long paramLong, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, int paramInt4, byte[] paramArrayOfByte4, int paramInt5, int paramInt6, byte[][] paramArrayOfByte);
/*      */ 
/*      */   native int t2cCloseProxySession(long paramLong);
/*      */ 
/*      */   static native int t2cDescribeTable(long paramLong, byte[] paramArrayOfByte1, int paramInt1, short[] paramArrayOfShort, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*      */ 
/*      */   native int t2cSetApplicationContext(long paramLong, String paramString1, String paramString2, String paramString3);
/*      */ 
/*      */   native int t2cClearAllApplicationContext(long paramLong, String paramString);
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CConnection
 * JD-Core Version:    0.6.0
 */