/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Driver;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.DriverPropertyInfo;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import oracle.jdbc.OracleDatabaseMetaData;
/*      */ import oracle.security.pki.OracleSecretStore;
/*      */ import oracle.security.pki.OracleWallet;
/*      */ 
/*      */ public class OracleDriver
/*      */   implements Driver
/*      */ {
/*      */   public static final char slash_character = '/';
/*      */   public static final char at_sign_character = '@';
/*      */   public static final char left_square_bracket_character = '[';
/*      */   public static final char right_square_bracket_character = ']';
/*      */   public static final String oracle_string = "oracle";
/*      */   public static final String protocol_string = "protocol";
/*      */   public static final String user_string = "user";
/*      */   public static final String password_string = "password";
/*      */   public static final String database_string = "database";
/*      */   public static final String server_string = "server";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String access_string = "access";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String protocolFullName_string = "protocolFullName";
/*      */   public static final String logon_as_internal_str = "internal_logon";
/*      */   public static final String proxy_client_name = "PROXY_CLIENT_NAME";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String prefetch_string = "prefetch";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String row_prefetch_string = "rowPrefetch";
/*      */   public static final String default_row_prefetch_string = "defaultRowPrefetch";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String batch_string = "batch";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String execute_batch_string = "executeBatch";
/*      */   public static final String default_execute_batch_string = "defaultExecuteBatch";
/*      */   public static final String process_escapes_string = "processEscapes";
/*      */   public static final String dms_parent_name_string = "DMSName";
/*      */   public static final String dms_parent_type_string = "DMSType";
/*      */   public static final String accumulate_batch_result = "AccumulateBatchResult";
/*      */   public static final String j2ee_compliance = "oracle.jdbc.J2EE13Compliant";
/*      */   public static final String v8compatible_string = "oracle.jdbc.V8Compatible";
/*      */   public static final String permit_timestamp_date_mismatch_string = "oracle.jdbc.internal.permitBindDateDefineTimestampMismatch";
/*      */   public static final String StreamChunkSize_string = "oracle.jdbc.StreamChunkSize";
/*      */   public static final String SetFloatAndDoubleUseBinary_string = "SetFloatAndDoubleUseBinary";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String xa_trans_loose = "oracle.jdbc.XATransLoose";
/*      */   public static final String tcp_no_delay = "oracle.jdbc.TcpNoDelay";
/*      */   public static final String read_timeout = "oracle.jdbc.ReadTimeout";
/*      */   public static final String defaultnchar_string = "oracle.jdbc.defaultNChar";
/*      */   public static final String defaultncharprop_string = "defaultNChar";
/*      */   public static final String useFetchSizeWithLongColumn_prop_string = "useFetchSizeWithLongColumn";
/*      */   public static final String useFetchSizeWithLongColumn_string = "oracle.jdbc.useFetchSizeWithLongColumn";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String remarks_string = "remarks";
/*      */   public static final String report_remarks_string = "remarksReporting";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String synonyms_string = "synonyms";
/*      */   public static final String include_synonyms_string = "includeSynonyms";
/*      */   public static final String restrict_getTables_string = "restrictGetTables";
/*      */   public static final String fixed_string_string = "fixedString";
/*      */   public static final String dll_string = "oracle.jdbc.ocinativelibrary";
/*      */   public static final String nls_lang_backdoor = "oracle.jdbc.ociNlsLangBackwardCompatible";
/*      */   public static final String disable_defineColumnType_string = "disableDefineColumnType";
/*      */   public static final String convert_nchar_literals_string = "oracle.jdbc.convertNcharLiterals";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String dataSizeUnitsPropertyName = "";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String dataSizeBytes = "";
/*      */ 
/*      */   /** @deprecated */
/*      */   public static final String dataSizeChars = "";
/*      */   public static final String dms_stmt_metrics_string = "oracle.jdbc.DMSStatementMetrics";
/*      */   public static final String dms_stmt_caching_metrics_string = "oracle.jdbc.DMSStatementCachingMetrics";
/*      */   public static final String set_new_password_string = "OCINewPassword";
/*      */   public static final String retain_v9_bind_behavior_string = "oracle.jdbc.RetainV9LongBindBehavior";
/*      */   public static final String no_caching_buffers = "oracle.jdbc.FreeMemoryOnEnterImplicitCache";
/*      */   public static final String secret_store_connect = "oracle.security.client.connect_string";
/*      */   public static final String secret_store_username = "oracle.security.client.username";
/*      */   public static final String secret_store_password = "oracle.security.client.password";
/*      */   public static final String secret_store_default_username = "oracle.security.client.default_username";
/*      */   public static final String secret_store_default_password = "oracle.security.client.default_password";
/*      */   public static final String wallet_location_string = "oracle.net.wallet_location";
/*  371 */   private static String walletLocation = null;
/*      */   static final int EXTENSION_TYPE_ORACLE_ERROR = -3;
/*      */   static final int EXTENSION_TYPE_GEN_ERROR = -2;
/*      */   static final int EXTENSION_TYPE_TYPE4_CLIENT = 0;
/*      */   static final int EXTENSION_TYPE_TYPE4_SERVER = 1;
/*      */   static final int EXTENSION_TYPE_TYPE2_CLIENT = 2;
/*      */   static final int EXTENSION_TYPE_TYPE2_SERVER = 3;
/*      */   private static final int NUMBER_OF_EXTENSION_TYPES = 4;
/*  403 */   private OracleDriverExtension[] driverExtensions = new OracleDriverExtension[4];
/*      */   private static final String DRIVER_PACKAGE_STRING = "driver";
/*  419 */   private static final String[] driverExtensionClassNames = { "oracle.jdbc.driver.T4CDriverExtension", "oracle.jdbc.driver.T4CDriverExtension", "oracle.jdbc.driver.T2CDriverExtension", "oracle.jdbc.driver.T2SDriverExtension" };
/*      */   private static Properties driverAccess;
/*  438 */   protected static Connection defaultConn = null;
/*  439 */   private static OracleDriver defaultDriver = null;
/*      */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*      */ 
/*      */   public Connection connect(String paramString, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  510 */     if (paramString.regionMatches(0, "jdbc:default:connection", 0, 23))
/*      */     {
/*  512 */       String str1 = "jdbc:oracle:kprb";
/*  513 */       int j = paramString.length();
/*      */ 
/*  515 */       if (j > 23)
/*  516 */         paramString = str1.concat(paramString.substring(23, paramString.length()));
/*      */       else {
/*  518 */         paramString = str1.concat(":");
/*      */       }
/*  520 */       str1 = null;
/*      */     }
/*      */ 
/*  532 */     int i = oracleDriverExtensionTypeFromURL(paramString);
/*      */ 
/*  534 */     if (i == -2) {
/*  535 */       return null;
/*      */     }
/*  537 */     if (i == -3) {
/*  538 */       DatabaseError.throwSqlException(67);
/*      */     }
/*  540 */     OracleDriverExtension localOracleDriverExtension = null;
/*      */ 
/*  542 */     localOracleDriverExtension = this.driverExtensions[i];
/*      */ 
/*  544 */     if (localOracleDriverExtension == null)
/*      */     {
/*      */       try
/*      */       {
/*  549 */         synchronized (this)
/*      */         {
/*  551 */           if (localOracleDriverExtension == null)
/*      */           {
/*  557 */             localOracleDriverExtension = (OracleDriverExtension)Class.forName(driverExtensionClassNames[i]).newInstance();
/*      */ 
/*  559 */             this.driverExtensions[i] = localOracleDriverExtension;
/*      */           }
/*      */           else
/*      */           {
/*  563 */             localOracleDriverExtension = this.driverExtensions[i];
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  572 */         throw new SQLException(localException.toString());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  592 */     walletLocation = paramProperties.getProperty("oracle.net.wallet_location");
/*      */ 
/*  594 */     if (walletLocation == null)
/*      */     {
/*  596 */       walletLocation = getSystemProperty("oracle.net.wallet_location", null);
/*      */     }
/*      */ 
/*  608 */     Hashtable localHashtable = parseUrl(paramString);
/*      */ 
/*  610 */     if (localHashtable == null)
/*      */     {
/*  612 */       return null;
/*      */     }
/*      */ 
/*  615 */     String str2 = paramProperties.getProperty("user");
/*  616 */     String str3 = paramProperties.getProperty("password");
/*  617 */     String str4 = paramProperties.getProperty("database");
/*      */ 
/*  619 */     if (str4 == null) {
/*  620 */       str4 = paramProperties.getProperty("server");
/*      */     }
/*  622 */     if (str2 == null) {
/*  623 */       str2 = (String)localHashtable.get("user");
/*      */     }
/*  625 */     str2 = parseLoginOption(str2, paramProperties);
/*      */ 
/*  627 */     if (str3 == null) {
/*  628 */       str3 = (String)localHashtable.get("password");
/*      */     }
/*  630 */     if (str4 == null) {
/*  631 */       str4 = (String)localHashtable.get("database");
/*      */     }
/*  633 */     String str5 = (String)localHashtable.get("protocol");
/*      */ 
/*  635 */     paramProperties.put("protocol", str5);
/*      */ 
/*  640 */     if (str5 == null)
/*      */     {
/*  642 */       DatabaseError.throwSqlException(40, "Protocol is not specified in URL");
/*      */ 
/*  645 */       return null;
/*      */     }
/*      */ 
/*  651 */     if ((str5.equals("oci8")) || (str5.equals("oci"))) {
/*  652 */       str4 = translateConnStr(str4);
/*      */     }
/*  654 */     String str6 = paramProperties.getProperty("oracle.jdbc.TcpNoDelay");
/*      */ 
/*  656 */     if (str6 == null)
/*      */     {
/*  658 */       str6 = getSystemProperty("oracle.jdbc.TcpNoDelay", null);
/*      */     }
/*  660 */     if ((str6 != null) && (str6.equalsIgnoreCase("true"))) {
/*  661 */       paramProperties.put("TCP.NODELAY", "YES");
/*      */     }
/*  663 */     String str7 = paramProperties.getProperty("oracle.jdbc.ReadTimeout");
/*  664 */     if (str7 != null) {
/*  665 */       paramProperties.put("oracle.net.READ_TIMEOUT", str7);
/*      */     }
/*      */ 
/*  677 */     int k = DriverManager.getLoginTimeout();
/*      */ 
/*  679 */     if ((k != 0) && (paramProperties.get("oracle.net.CONNECT_TIMEOUT") == null))
/*      */     {
/*  681 */       paramProperties.put("oracle.net.CONNECT_TIMEOUT", "" + k * 1000);
/*      */     }
/*      */ 
/*  687 */     String str8 = paramProperties.getProperty("prefetch");
/*      */ 
/*  689 */     if (str8 == null) {
/*  690 */       str8 = paramProperties.getProperty("rowPrefetch");
/*      */     }
/*  692 */     if (str8 == null) {
/*  693 */       str8 = paramProperties.getProperty("defaultRowPrefetch");
/*      */     }
/*  695 */     if ((str8 != null) && (Integer.parseInt(str8) <= 0)) {
/*  696 */       str8 = null;
/*      */     }
/*  698 */     String str9 = paramProperties.getProperty("batch");
/*      */ 
/*  700 */     if (str9 == null) {
/*  701 */       str9 = paramProperties.getProperty("executeBatch");
/*      */     }
/*  703 */     if (str9 == null) {
/*  704 */       str9 = paramProperties.getProperty("defaultExecuteBatch");
/*      */     }
/*  706 */     if ((str9 != null) && (Integer.parseInt(str9) <= 0)) {
/*  707 */       str9 = null;
/*      */     }
/*      */ 
/*  711 */     String str10 = paramProperties.getProperty("defaultNChar");
/*      */ 
/*  713 */     if (str10 == null) {
/*  714 */       str10 = getSystemProperty("oracle.jdbc.defaultNChar", null);
/*      */     }
/*  716 */     String str11 = paramProperties.getProperty("useFetchSizeWithLongColumn");
/*      */ 
/*  719 */     if (str11 == null)
/*      */     {
/*  721 */       str11 = getSystemProperty("oracle.jdbc.useFetchSizeWithLongColumn", null);
/*      */     }
/*      */ 
/*  726 */     String str12 = paramProperties.getProperty("remarks");
/*      */ 
/*  728 */     if (str12 == null) {
/*  729 */       str12 = paramProperties.getProperty("remarksReporting");
/*      */     }
/*      */ 
/*  733 */     String str13 = paramProperties.getProperty("synonyms");
/*      */ 
/*  735 */     if (str13 == null) {
/*  736 */       str13 = paramProperties.getProperty("includeSynonyms");
/*      */     }
/*      */ 
/*  740 */     String str14 = paramProperties.getProperty("restrictGetTables");
/*      */ 
/*  743 */     String str15 = paramProperties.getProperty("fixedString");
/*      */ 
/*  746 */     String str16 = paramProperties.getProperty("AccumulateBatchResult");
/*      */ 
/*  748 */     if (str16 == null) {
/*  749 */       str16 = "true";
/*      */     }
/*      */ 
/*  753 */     String str17 = paramProperties.getProperty("disableDefineColumnType");
/*      */ 
/*  756 */     if (str17 == null) {
/*  757 */       str17 = "false";
/*      */     }
/*  759 */     String str18 = paramProperties.getProperty("oracle.jdbc.convertNcharLiterals");
/*      */ 
/*  762 */     if (str18 == null) {
/*  763 */       str18 = getSystemProperty("oracle.jdbc.convertNcharLiterals", "false");
/*      */     }
/*      */ 
/*  777 */     Enumeration localEnumeration = DriverManager.getDrivers();
/*      */ 
/*  780 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*  782 */       localObject2 = (Driver)localEnumeration.nextElement();
/*      */ 
/*  784 */       if ((localObject2 instanceof OracleDriver)) {
/*  785 */         break;
/*      */       }
/*      */     }
/*      */ 
/*  789 */     while (localEnumeration.hasMoreElements())
/*      */     {
/*  791 */       localObject2 = (Driver)localEnumeration.nextElement();
/*      */ 
/*  793 */       if ((localObject2 instanceof OracleDriver)) {
/*  794 */         DriverManager.deregisterDriver((Driver)localObject2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  801 */     Object localObject2 = (PhysicalConnection)localOracleDriverExtension.getConnection(paramString, str2, str3, str4, paramProperties);
/*      */ 
/*  805 */     if (str8 != null) {
/*  806 */       ((PhysicalConnection)localObject2).setDefaultRowPrefetch(Integer.parseInt(str8));
/*      */     }
/*  808 */     if (str9 != null) {
/*  809 */       ((PhysicalConnection)localObject2).setDefaultExecuteBatch(Integer.parseInt(str9));
/*      */     }
/*  811 */     if (str12 != null) {
/*  812 */       ((PhysicalConnection)localObject2).setRemarksReporting(str12.equalsIgnoreCase("true"));
/*      */     }
/*  814 */     if (str13 != null) {
/*  815 */       ((PhysicalConnection)localObject2).setIncludeSynonyms(str13.equalsIgnoreCase("true"));
/*      */     }
/*  817 */     if (str14 != null) {
/*  818 */       ((PhysicalConnection)localObject2).setRestrictGetTables(str14.equalsIgnoreCase("true"));
/*      */     }
/*  820 */     if (str15 != null) {
/*  821 */       ((PhysicalConnection)localObject2).setDefaultFixedString(str15.equalsIgnoreCase("true"));
/*      */     }
/*  823 */     if (str10 != null) {
/*  824 */       ((PhysicalConnection)localObject2).setDefaultNChar(str10.equalsIgnoreCase("true"));
/*      */     }
/*  826 */     if (str11 != null) {
/*  827 */       ((PhysicalConnection)localObject2).useFetchSizeWithLongColumn = str11.equalsIgnoreCase("true");
/*      */     }
/*      */ 
/*  832 */     if (str16 != null) {
/*  833 */       ((PhysicalConnection)localObject2).setAccumulateBatchResult(str16.equalsIgnoreCase("true"));
/*      */     }
/*      */ 
/*  840 */     String str20 = getSystemProperty("oracle.jdbc.J2EE13Compliant", null);
/*      */     String str19;
/*  843 */     if (str20 == null)
/*      */     {
/*  845 */       str19 = paramProperties.getProperty("oracle.jdbc.J2EE13Compliant");
/*      */ 
/*  847 */       if (str19 == null)
/*      */       {
/*  849 */         str19 = "false";
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  857 */       str19 = str20;
/*      */     }
/*  859 */     ((PhysicalConnection)localObject2).setJ2EE13Compliant(str19.equalsIgnoreCase("true"));
/*      */ 
/*  861 */     ((PhysicalConnection)localObject2).disableDefineColumnType = str17.equalsIgnoreCase("true");
/*      */ 
/*  864 */     ((PhysicalConnection)localObject2).convertNcharLiterals = str18.equalsIgnoreCase("true");
/*      */ 
/*  867 */     localHashtable = null;
/*      */ 
/*  869 */     ((PhysicalConnection)localObject2).protocolId = i;
/*      */ 
/*  874 */     return (Connection)localObject2;
/*      */   }
/*      */ 
/*      */   public Connection defaultConnection()
/*      */     throws SQLException
/*      */   {
/*  889 */     if ((defaultConn == null) || (defaultConn.isClosed()))
/*      */     {
/*  891 */       synchronized (OracleDriver.class)
/*      */       {
/*  893 */         if ((defaultConn == null) || (defaultConn.isClosed()))
/*      */         {
/*  895 */           defaultConn = DriverManager.getConnection("jdbc:oracle:kprb:");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  900 */     return defaultConn;
/*      */   }
/*      */ 
/*      */   private int oracleDriverExtensionTypeFromURL(String paramString)
/*      */   {
/*  925 */     int i = paramString.indexOf(':') + 1;
/*      */ 
/*  927 */     if (i == 0) {
/*  928 */       return -2;
/*      */     }
/*  930 */     int j = paramString.indexOf(':', i);
/*      */ 
/*  932 */     if (j == -1) {
/*  933 */       return -2;
/*      */     }
/*  935 */     if (!paramString.regionMatches(true, i, "oracle", 0, j - i))
/*      */     {
/*  937 */       return -2;
/*      */     }
/*  939 */     j++;
/*      */ 
/*  941 */     int k = paramString.indexOf(':', j);
/*      */ 
/*  943 */     if (k == -1) {
/*  944 */       return -3;
/*      */     }
/*  946 */     String str = paramString.substring(j, k);
/*      */ 
/*  953 */     if (str.equals("thin")) {
/*  954 */       return 0;
/*      */     }
/*  956 */     if ((str.equals("oci8")) || (str.equals("oci"))) {
/*  957 */       return 2;
/*      */     }
/*      */ 
/*  961 */     return -3;
/*      */   }
/*      */ 
/*      */   public boolean acceptsURL(String paramString)
/*      */   {
/*  981 */     if (paramString.startsWith("jdbc:oracle:"))
/*      */     {
/*  983 */       return oracleDriverExtensionTypeFromURL(paramString) > -2;
/*      */     }
/*      */ 
/*  986 */     return false;
/*      */   }
/*      */ 
/*      */   public DriverPropertyInfo[] getPropertyInfo(String paramString, Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  995 */     return new DriverPropertyInfo[0];
/*      */   }
/*      */ 
/*      */   public int getMajorVersion()
/*      */   {
/* 1000 */     return OracleDatabaseMetaData.getDriverMajorVersionInfo();
/*      */   }
/*      */ 
/*      */   public int getMinorVersion()
/*      */   {
/* 1005 */     return OracleDatabaseMetaData.getDriverMinorVersionInfo();
/*      */   }
/*      */ 
/*      */   public boolean jdbcCompliant()
/*      */   {
/* 1010 */     return true;
/*      */   }
/*      */ 
/*      */   public String processSqlEscapes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1023 */     OracleSql localOracleSql = new OracleSql(null);
/*      */ 
/* 1027 */     localOracleSql.initialize(paramString);
/*      */ 
/* 1029 */     return localOracleSql.parse(paramString);
/*      */   }
/*      */ 
/*      */   private String parseLoginOption(String paramString, Properties paramProperties)
/*      */   {
/* 1045 */     int j = 0;
/* 1046 */     String str1 = null;
/* 1047 */     String str2 = null;
/*      */ 
/* 1050 */     if (paramString == null) {
/* 1051 */       return null;
/*      */     }
/* 1053 */     int k = paramString.length();
/*      */ 
/* 1055 */     if (k == 0) {
/* 1056 */       return null;
/*      */     }
/*      */ 
/* 1059 */     int i = paramString.indexOf('[');
/* 1060 */     if (i > 0) {
/* 1061 */       j = paramString.indexOf(']');
/* 1062 */       str2 = paramString.substring(i + 1, j);
/* 1063 */       str2 = str2.trim();
/*      */ 
/* 1065 */       if (str2.length() > 0) {
/* 1066 */         paramProperties.put("PROXY_CLIENT_NAME", str2);
/*      */       }
/*      */ 
/* 1069 */       paramString = paramString.substring(0, i) + paramString.substring(j + 1, k);
/*      */     }
/*      */ 
/* 1074 */     String str3 = paramString.toLowerCase();
/*      */ 
/* 1077 */     i = str3.lastIndexOf(" as ");
/*      */ 
/* 1079 */     if ((i == -1) || (i < str3.lastIndexOf("\""))) {
/* 1080 */       return paramString;
/*      */     }
/*      */ 
/* 1085 */     str1 = paramString.substring(0, i);
/*      */ 
/* 1087 */     i += 4;
/*      */ 
/* 1090 */     while ((i < k) && (str3.charAt(i) == ' ')) {
/* 1091 */       i++;
/*      */     }
/* 1093 */     if (i == k) {
/* 1094 */       return paramString;
/*      */     }
/* 1096 */     String str4 = str3.substring(i).trim();
/*      */ 
/* 1098 */     if (str4.length() > 0) {
/* 1099 */       paramProperties.put("internal_logon", str4);
/*      */     }
/* 1101 */     return str1;
/*      */   }
/*      */ 
/*      */   private Hashtable parseUrl(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1123 */     Hashtable localHashtable = new Hashtable(5);
/* 1124 */     int i = paramString.indexOf(':', paramString.indexOf(58) + 1) + 1;
/* 1125 */     int j = paramString.length();
/*      */ 
/* 1128 */     if (i == j) {
/* 1129 */       return localHashtable;
/*      */     }
/* 1131 */     int k = paramString.indexOf(':', i);
/*      */ 
/* 1134 */     if (k == -1) {
/* 1135 */       return localHashtable;
/*      */     }
/* 1137 */     localHashtable.put("protocol", paramString.substring(i, k));
/*      */ 
/* 1139 */     int m = k + 1;
/* 1140 */     int n = paramString.indexOf('/', m);
/*      */ 
/* 1142 */     int i1 = paramString.indexOf('@', m);
/*      */ 
/* 1148 */     if ((i1 > m) && (m > i) && (n == -1))
/*      */     {
/* 1150 */       return null;
/*      */     }
/*      */ 
/* 1153 */     if (i1 == -1) {
/* 1154 */       i1 = j;
/*      */     }
/* 1156 */     if (n == -1) {
/* 1157 */       n = i1;
/*      */     }
/* 1159 */     if ((n < i1) && (n != m) && (i1 != m))
/*      */     {
/* 1161 */       localHashtable.put("user", paramString.substring(m, n));
/* 1162 */       localHashtable.put("password", paramString.substring(n + 1, i1));
/*      */     }
/*      */ 
/* 1172 */     if ((n <= i1) && ((n == m) || (i1 == m)))
/*      */     {
/* 1179 */       if (i1 < j)
/*      */       {
/* 1181 */         String str = paramString.substring(i1 + 1);
/* 1182 */         String[] arrayOfString = getSecretStoreCredentials(str);
/* 1183 */         if ((arrayOfString[0] != null) || (arrayOfString[1] != null))
/*      */         {
/* 1185 */           localHashtable.put("user", arrayOfString[0]);
/* 1186 */           localHashtable.put("password", arrayOfString[1]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1194 */     if (i1 < j) {
/* 1195 */       localHashtable.put("database", paramString.substring(i1 + 1));
/*      */     }
/*      */ 
/* 1200 */     return localHashtable;
/*      */   }
/*      */ 
/*      */   private String[] getSecretStoreCredentials(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1229 */     String[] arrayOfString = new String[2];
/* 1230 */     arrayOfString[0] = null;
/* 1231 */     arrayOfString[1] = null;
/*      */ 
/* 1233 */     if (walletLocation != null)
/*      */     {
/*      */       try
/*      */       {
/* 1237 */         OracleWallet localOracleWallet = new OracleWallet();
/* 1238 */         if (localOracleWallet.exists(walletLocation))
/*      */         {
/* 1240 */           localOracleWallet.open(walletLocation, null);
/* 1241 */           OracleSecretStore localOracleSecretStore = localOracleWallet.getSecretStore();
/*      */ 
/* 1245 */           if (localOracleSecretStore.containsAlias("oracle.security.client.default_username")) {
/* 1246 */             arrayOfString[0] = new String(localOracleSecretStore.getSecret("oracle.security.client.default_username"));
/*      */           }
/* 1248 */           if (localOracleSecretStore.containsAlias("oracle.security.client.default_password")) {
/* 1249 */             arrayOfString[1] = new String(localOracleSecretStore.getSecret("oracle.security.client.default_password"));
/*      */           }
/*      */ 
/* 1252 */           Enumeration localEnumeration = localOracleWallet.getSecretStore().internalAliases();
/*      */ 
/* 1254 */           String str1 = null;
/* 1255 */           while (localEnumeration.hasMoreElements())
/*      */           {
/* 1257 */             str1 = (String)localEnumeration.nextElement();
/* 1258 */             if ((!str1.startsWith("oracle.security.client.connect_string")) || 
/* 1260 */               (!paramString.equalsIgnoreCase(new String(localOracleSecretStore.getSecret(str1))))) {
/*      */               continue;
/*      */             }
/* 1263 */             String str2 = str1.substring("oracle.security.client.connect_string".length());
/* 1264 */             arrayOfString[0] = new String(localOracleSecretStore.getSecret("oracle.security.client.username" + str2));
/*      */ 
/* 1266 */             arrayOfString[1] = new String(localOracleSecretStore.getSecret("oracle.security.client.password" + str2));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (NoClassDefFoundError localNoClassDefFoundError)
/*      */       {
/* 1278 */         DatabaseError.throwSqlException(167);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/* 1283 */         if ((localException instanceof RuntimeException)) throw ((RuntimeException)localException);
/*      */ 
/* 1295 */         DatabaseError.throwSqlException(168);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1301 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */   public static String getCompileTime()
/*      */   {
/* 1316 */     return "Tue_Jan_24_08:54:29_PST_2006";
/*      */   }
/*      */ 
/*      */   private String translateConnStr(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1335 */     int i = 0;
/* 1336 */     int j = 0;
/*      */ 
/* 1338 */     if (paramString == null) {
/* 1339 */       return paramString;
/*      */     }
/*      */ 
/* 1344 */     if (((i = paramString.indexOf(':')) == -1) || ((j = paramString.indexOf(':', i + 1)) == -1))
/*      */     {
/* 1347 */       return paramString;
/*      */     }
/*      */ 
/* 1351 */     if (paramString.indexOf(':', j + 1) != -1)
/*      */     {
/* 1353 */       DatabaseError.throwSqlException(67, paramString);
/*      */     }
/*      */ 
/* 1356 */     String str2 = paramString.substring(0, i);
/* 1357 */     String str3 = paramString.substring(i + 1, j);
/* 1358 */     String str4 = paramString.substring(j + 1, paramString.length());
/*      */ 
/* 1360 */     String str1 = "(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=" + str2 + ")(PORT=" + str3 + "))(CONNECT_DATA=(SID=" + str4 + ")))";
/*      */ 
/* 1370 */     return str1;
/*      */   }
/*      */ 
/*      */   protected static String getSystemPropertyUserName()
/*      */   {
/* 1375 */     return getSystemProperty("user.name", null);
/*      */   }
/*      */ 
/*      */   protected static String getSystemPropertyV8Compatible()
/*      */   {
/* 1380 */     return getSystemProperty("oracle.jdbc.V8Compatible", null);
/*      */   }
/*      */ 
/*      */   protected static String getSystemPropertyPollInterval()
/*      */   {
/* 1385 */     return getSystemProperty("oracle.jdbc.TimeoutPollInterval", "1000");
/*      */   }
/*      */ 
/*      */   public static String getSystemPropertyFastConnectionFailover(String paramString)
/*      */   {
/* 1391 */     return getSystemProperty("oracle.jdbc.FastConnectionFailover", paramString);
/*      */   }
/*      */ 
/*      */   public static String getSystemPropertyJserverVersion()
/*      */   {
/* 1397 */     return getSystemProperty("oracle.jserver.version", null);
/*      */   }
/*      */ 
/*      */   private static String getSystemProperty(String paramString)
/*      */   {
/* 1410 */     return getSystemProperty(paramString, null);
/*      */   }
/*      */ 
/*      */   private static String getSystemProperty(String paramString1, String paramString2)
/*      */   {
/* 1418 */     if (paramString1 != null)
/*      */     {
/* 1420 */       String str1 = paramString1;
/* 1421 */       String str2 = paramString2;
/* 1422 */       String[] arrayOfString = { paramString2 };
/* 1423 */       AccessController.doPrivileged(new PrivilegedAction(arrayOfString, str1, str2) { private final String[] val$rets;
/*      */         private final String val$fstr;
/*      */         private final String val$fdefaultValue;
/*      */ 
/* 1427 */         public Object run() { this.val$rets[0] = System.getProperty(this.val$fstr, this.val$fdefaultValue);
/* 1428 */           return null;
/*      */         }
/*      */       });
/* 1431 */       return arrayOfString[0];
/*      */     }
/*      */ 
/* 1436 */     return paramString2;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  462 */     Timestamp localTimestamp = Timestamp.valueOf("2000-01-01 00:00:00.0");
/*      */     try
/*      */     {
/*  468 */       if (defaultDriver == null)
/*      */       {
/*  470 */         defaultDriver = new OracleDriver();
/*      */ 
/*  472 */         DriverManager.registerDriver(defaultDriver);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RuntimeException localRuntimeException)
/*      */     {
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */ 
/* 1445 */     _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleDriver
 * JD-Core Version:    0.6.0
 */