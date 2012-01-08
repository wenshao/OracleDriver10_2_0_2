/*      */ package oracle.jdbc;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.driver.OracleSql;
/*      */ import oracle.jdbc.internal.OracleResultSet;
/*      */ import oracle.sql.SQLName;
/*      */ 
/*      */ public class OracleDatabaseMetaData
/*      */   implements DatabaseMetaData
/*      */ {
/*   42 */   private static String DRIVER_NAME = "Oracle JDBC driver";
/*   43 */   private static String DRIVER_VERSION = "10.2.0.2.0";
/*   44 */   private static int DRIVER_MAJOR_VERSION = 10;
/*   45 */   private static int DRIVER_MINOR_VERSION = 2;
/*   46 */   private static String LOB_MAXSIZE = "4294967295";
/*   47 */   private static long LOB_MAXLENGTH_32BIT = 4294967295L;
/*      */   protected oracle.jdbc.internal.OracleConnection connection;
/* 2025 */   int procedureResultUnknown = 0;
/*      */ 
/* 2030 */   int procedureNoResult = 1;
/*      */ 
/* 2035 */   int procedureReturnsResult = 2;
/*      */ 
/* 2251 */   int procedureColumnUnknown = 0;
/*      */ 
/* 2256 */   int procedureColumnIn = 1;
/*      */ 
/* 2261 */   int procedureColumnInOut = 2;
/*      */ 
/* 2266 */   int procedureColumnOut = 4;
/*      */ 
/* 2271 */   int procedureColumnReturn = 5;
/*      */ 
/* 2276 */   int procedureColumnResult = 3;
/*      */ 
/* 2281 */   int procedureNoNulls = 0;
/*      */ 
/* 2286 */   int procedureNullable = 1;
/*      */ 
/* 2291 */   int procedureNullableUnknown = 2;
/*      */ 
/* 2625 */   int columnNoNulls = 0;
/*      */ 
/* 2630 */   int columnNullable = 1;
/*      */ 
/* 2635 */   int columnNullableUnknown = 2;
/*      */   static final int bestRowTemporary = 0;
/*      */   static final int bestRowTransaction = 1;
/*      */   static final int bestRowSession = 2;
/*      */   static final int bestRowUnknown = 0;
/*      */   static final int bestRowNotPseudo = 1;
/*      */   static final int bestRowPseudo = 2;
/* 2962 */   int versionColumnUnknown = 0;
/*      */ 
/* 2967 */   int versionColumnNotPseudo = 1;
/*      */ 
/* 2972 */   int versionColumnPseudo = 2;
/*      */ 
/* 3162 */   int importedKeyCascade = 0;
/*      */ 
/* 3168 */   int importedKeyRestrict = 1;
/*      */ 
/* 3174 */   int importedKeySetNull = 2;
/*      */ 
/* 3369 */   int typeNoNulls = 0;
/*      */ 
/* 3374 */   int typeNullable = 1;
/*      */ 
/* 3379 */   int typeNullableUnknown = 2;
/*      */ 
/* 3384 */   int typePredNone = 0;
/*      */ 
/* 3389 */   int typePredChar = 1;
/*      */ 
/* 3394 */   int typePredBasic = 2;
/*      */ 
/* 3399 */   int typeSearchable = 3;
/*      */ 
/* 3554 */   short tableIndexStatistic = 0;
/*      */ 
/* 3559 */   short tableIndexClustered = 1;
/*      */ 
/* 3564 */   short tableIndexHashed = 2;
/*      */ 
/* 3569 */   short tableIndexOther = 3;
/*      */ 
/* 4105 */   short attributeNoNulls = 0;
/*      */ 
/* 4116 */   short attributeNullable = 1;
/*      */ 
/* 4128 */   short attributeNullableUnknown = 2;
/*      */ 
/* 4338 */   int sqlStateXOpen = 1;
/*      */ 
/* 4349 */   int sqlStateSQL99 = 2;
/*      */ 
/* 4486 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*      */ 
/*      */   public OracleDatabaseMetaData(OracleConnection paramOracleConnection)
/*      */   {
/*   52 */     this.connection = paramOracleConnection.physicalConnectionWithin();
/*      */   }
/*      */ 
/*      */   public boolean allProceduresAreCallable()
/*      */     throws SQLException
/*      */   {
/*   70 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean allTablesAreSelectable()
/*      */     throws SQLException
/*      */   {
/*   85 */     return false;
/*      */   }
/*      */ 
/*      */   public String getURL()
/*      */     throws SQLException
/*      */   {
/*   96 */     return this.connection.getURL();
/*      */   }
/*      */ 
/*      */   public String getUserName()
/*      */     throws SQLException
/*      */   {
/*  110 */     return this.connection.getUserName();
/*      */   }
/*      */ 
/*      */   public boolean isReadOnly()
/*      */     throws SQLException
/*      */   {
/*  124 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean nullsAreSortedHigh()
/*      */     throws SQLException
/*      */   {
/*  138 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean nullsAreSortedLow()
/*      */     throws SQLException
/*      */   {
/*  152 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean nullsAreSortedAtStart()
/*      */     throws SQLException
/*      */   {
/*  166 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean nullsAreSortedAtEnd()
/*      */     throws SQLException
/*      */   {
/*  180 */     return false;
/*      */   }
/*      */ 
/*      */   public String getDatabaseProductName()
/*      */     throws SQLException
/*      */   {
/*  191 */     return "Oracle";
/*      */   }
/*      */ 
/*      */   public String getDatabaseProductVersion()
/*      */     throws SQLException
/*      */   {
/*  205 */     return this.connection.getDatabaseProductVersion();
/*      */   }
/*      */ 
/*      */   public String getDriverName()
/*      */     throws SQLException
/*      */   {
/*  219 */     return DRIVER_NAME;
/*      */   }
/*      */ 
/*      */   public String getDriverVersion()
/*      */     throws SQLException
/*      */   {
/*  233 */     return DRIVER_VERSION;
/*      */   }
/*      */ 
/*      */   public int getDriverMajorVersion()
/*      */   {
/*  246 */     return DRIVER_MAJOR_VERSION;
/*      */   }
/*      */ 
/*      */   public int getDriverMinorVersion()
/*      */   {
/*  259 */     return DRIVER_MINOR_VERSION;
/*      */   }
/*      */ 
/*      */   public boolean usesLocalFiles()
/*      */     throws SQLException
/*      */   {
/*  273 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean usesLocalFilePerTable()
/*      */     throws SQLException
/*      */   {
/*  287 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsMixedCaseIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  304 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean storesUpperCaseIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  319 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean storesLowerCaseIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  334 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean storesMixedCaseIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  349 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsMixedCaseQuotedIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  366 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean storesUpperCaseQuotedIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  381 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean storesLowerCaseQuotedIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  396 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean storesMixedCaseQuotedIdentifiers()
/*      */     throws SQLException
/*      */   {
/*  411 */     return true;
/*      */   }
/*      */ 
/*      */   public String getIdentifierQuoteString()
/*      */     throws SQLException
/*      */   {
/*  428 */     return "\"";
/*      */   }
/*      */ 
/*      */   public String getSQLKeywords()
/*      */     throws SQLException
/*      */   {
/*  443 */     return "ACCESS, ADD, ALTER, AUDIT, CLUSTER, COLUMN, COMMENT, COMPRESS, CONNECT, DATE, DROP, EXCLUSIVE, FILE, IDENTIFIED, IMMEDIATE, INCREMENT, INDEX, INITIAL, INTERSECT, LEVEL, LOCK, LONG, MAXEXTENTS, MINUS, MODE, NOAUDIT, NOCOMPRESS, NOWAIT, NUMBER, OFFLINE, ONLINE, PCTFREE, PRIOR, all_PL_SQL_reserved_ words";
/*      */   }
/*      */ 
/*      */   public String getNumericFunctions()
/*      */     throws SQLException
/*      */   {
/*  460 */     return "ABS,ACOS,ASIN,ATAN,ATAN2,CEILING,COS,EXP,FLOOR,LOG,LOG10,MOD,PI,POWER,ROUND,SIGN,SIN,SQRT,TAN,TRUNCATE";
/*      */   }
/*      */ 
/*      */   public String getStringFunctions()
/*      */     throws SQLException
/*      */   {
/*  478 */     return "ASCII,CHAR,CONCAT,LCASE,LENGTH,LTRIM,REPLACE,RTRIM,SOUNDEX,SUBSTRING,UCASE";
/*      */   }
/*      */ 
/*      */   public String getSystemFunctions()
/*      */     throws SQLException
/*      */   {
/*  496 */     return "USER";
/*      */   }
/*      */ 
/*      */   public String getTimeDateFunctions()
/*      */     throws SQLException
/*      */   {
/*  509 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/*  511 */     return null;
/*      */   }
/*      */ 
/*      */   public String getSearchStringEscape()
/*      */     throws SQLException
/*      */   {
/*  529 */     return "//";
/*      */   }
/*      */ 
/*      */   public String getExtraNameCharacters()
/*      */     throws SQLException
/*      */   {
/*  544 */     return "$#";
/*      */   }
/*      */ 
/*      */   public boolean supportsAlterTableWithAddColumn()
/*      */     throws SQLException
/*      */   {
/*  561 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsAlterTableWithDropColumn()
/*      */     throws SQLException
/*      */   {
/*  575 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsColumnAliasing()
/*      */     throws SQLException
/*      */   {
/*  595 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean nullPlusNonNullIsNull()
/*      */     throws SQLException
/*      */   {
/*  611 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsConvert()
/*      */     throws SQLException
/*      */   {
/*  625 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsConvert(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  642 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsTableCorrelationNames()
/*      */     throws SQLException
/*      */   {
/*  658 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsDifferentTableCorrelationNames()
/*      */     throws SQLException
/*      */   {
/*  673 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsExpressionsInOrderBy()
/*      */     throws SQLException
/*      */   {
/*  687 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsOrderByUnrelated()
/*      */     throws SQLException
/*      */   {
/*  701 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsGroupBy()
/*      */     throws SQLException
/*      */   {
/*  715 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsGroupByUnrelated()
/*      */     throws SQLException
/*      */   {
/*  729 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsGroupByBeyondSelect()
/*      */     throws SQLException
/*      */   {
/*  744 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsLikeEscapeClause()
/*      */     throws SQLException
/*      */   {
/*  760 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsMultipleResultSets()
/*      */     throws SQLException
/*      */   {
/*  774 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsMultipleTransactions()
/*      */     throws SQLException
/*      */   {
/*  789 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsNonNullableColumns()
/*      */     throws SQLException
/*      */   {
/*  805 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsMinimumSQLGrammar()
/*      */     throws SQLException
/*      */   {
/*  821 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsCoreSQLGrammar()
/*      */     throws SQLException
/*      */   {
/*  835 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsExtendedSQLGrammar()
/*      */     throws SQLException
/*      */   {
/*  849 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsANSI92EntryLevelSQL()
/*      */     throws SQLException
/*      */   {
/*  865 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsANSI92IntermediateSQL()
/*      */     throws SQLException
/*      */   {
/*  879 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsANSI92FullSQL()
/*      */     throws SQLException
/*      */   {
/*  893 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsIntegrityEnhancementFacility()
/*      */     throws SQLException
/*      */   {
/*  907 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsOuterJoins()
/*      */     throws SQLException
/*      */   {
/*  921 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsFullOuterJoins()
/*      */     throws SQLException
/*      */   {
/*  935 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsLimitedOuterJoins()
/*      */     throws SQLException
/*      */   {
/*  950 */     return true;
/*      */   }
/*      */ 
/*      */   public String getSchemaTerm()
/*      */     throws SQLException
/*      */   {
/*  964 */     return "schema";
/*      */   }
/*      */ 
/*      */   public String getProcedureTerm()
/*      */     throws SQLException
/*      */   {
/*  978 */     return "procedure";
/*      */   }
/*      */ 
/*      */   public String getCatalogTerm()
/*      */     throws SQLException
/*      */   {
/*  992 */     return "";
/*      */   }
/*      */ 
/*      */   public boolean isCatalogAtStart()
/*      */     throws SQLException
/*      */   {
/* 1007 */     return false;
/*      */   }
/*      */ 
/*      */   public String getCatalogSeparator()
/*      */     throws SQLException
/*      */   {
/* 1021 */     return "";
/*      */   }
/*      */ 
/*      */   public boolean supportsSchemasInDataManipulation()
/*      */     throws SQLException
/*      */   {
/* 1035 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSchemasInProcedureCalls()
/*      */     throws SQLException
/*      */   {
/* 1049 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSchemasInTableDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1063 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSchemasInIndexDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1077 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSchemasInPrivilegeDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1091 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsCatalogsInDataManipulation()
/*      */     throws SQLException
/*      */   {
/* 1105 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsCatalogsInProcedureCalls()
/*      */     throws SQLException
/*      */   {
/* 1119 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsCatalogsInTableDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1133 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsCatalogsInIndexDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1147 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsCatalogsInPrivilegeDefinitions()
/*      */     throws SQLException
/*      */   {
/* 1161 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsPositionedDelete()
/*      */     throws SQLException
/*      */   {
/* 1184 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsPositionedUpdate()
/*      */     throws SQLException
/*      */   {
/* 1206 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsSelectForUpdate()
/*      */     throws SQLException
/*      */   {
/* 1220 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsStoredProcedures()
/*      */     throws SQLException
/*      */   {
/* 1235 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSubqueriesInComparisons()
/*      */     throws SQLException
/*      */   {
/* 1251 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSubqueriesInExists()
/*      */     throws SQLException
/*      */   {
/* 1267 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSubqueriesInIns()
/*      */     throws SQLException
/*      */   {
/* 1283 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsSubqueriesInQuantifieds()
/*      */     throws SQLException
/*      */   {
/* 1299 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsCorrelatedSubqueries()
/*      */     throws SQLException
/*      */   {
/* 1315 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsUnion()
/*      */     throws SQLException
/*      */   {
/* 1329 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsUnionAll()
/*      */     throws SQLException
/*      */   {
/* 1343 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsOpenCursorsAcrossCommit()
/*      */     throws SQLException
/*      */   {
/* 1358 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsOpenCursorsAcrossRollback()
/*      */     throws SQLException
/*      */   {
/* 1373 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsOpenStatementsAcrossCommit()
/*      */     throws SQLException
/*      */   {
/* 1388 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsOpenStatementsAcrossRollback()
/*      */     throws SQLException
/*      */   {
/* 1403 */     return false;
/*      */   }
/*      */ 
/*      */   public int getMaxBinaryLiteralLength()
/*      */     throws SQLException
/*      */   {
/* 1425 */     return 1000;
/*      */   }
/*      */ 
/*      */   public int getMaxCharLiteralLength()
/*      */     throws SQLException
/*      */   {
/* 1439 */     return 2000;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnNameLength()
/*      */     throws SQLException
/*      */   {
/* 1453 */     return 30;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnsInGroupBy()
/*      */     throws SQLException
/*      */   {
/* 1467 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnsInIndex()
/*      */     throws SQLException
/*      */   {
/* 1481 */     return 32;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnsInOrderBy()
/*      */     throws SQLException
/*      */   {
/* 1495 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnsInSelect()
/*      */     throws SQLException
/*      */   {
/* 1509 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxColumnsInTable()
/*      */     throws SQLException
/*      */   {
/* 1523 */     return 1000;
/*      */   }
/*      */ 
/*      */   public int getMaxConnections()
/*      */     throws SQLException
/*      */   {
/* 1537 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxCursorNameLength()
/*      */     throws SQLException
/*      */   {
/* 1551 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxIndexLength()
/*      */     throws SQLException
/*      */   {
/* 1565 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxSchemaNameLength()
/*      */     throws SQLException
/*      */   {
/* 1579 */     return 30;
/*      */   }
/*      */ 
/*      */   public int getMaxProcedureNameLength()
/*      */     throws SQLException
/*      */   {
/* 1593 */     return 30;
/*      */   }
/*      */ 
/*      */   public int getMaxCatalogNameLength()
/*      */     throws SQLException
/*      */   {
/* 1607 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxRowSize()
/*      */     throws SQLException
/*      */   {
/* 1621 */     return 2000;
/*      */   }
/*      */ 
/*      */   public boolean doesMaxRowSizeIncludeBlobs()
/*      */     throws SQLException
/*      */   {
/* 1636 */     return true;
/*      */   }
/*      */ 
/*      */   public int getMaxStatementLength()
/*      */     throws SQLException
/*      */   {
/* 1650 */     return 65535;
/*      */   }
/*      */ 
/*      */   public int getMaxStatements()
/*      */     throws SQLException
/*      */   {
/* 1665 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxTableNameLength()
/*      */     throws SQLException
/*      */   {
/* 1679 */     return 30;
/*      */   }
/*      */ 
/*      */   public int getMaxTablesInSelect()
/*      */     throws SQLException
/*      */   {
/* 1693 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getMaxUserNameLength()
/*      */     throws SQLException
/*      */   {
/* 1707 */     return 30;
/*      */   }
/*      */ 
/*      */   public int getDefaultTransactionIsolation()
/*      */     throws SQLException
/*      */   {
/* 1725 */     return 2;
/*      */   }
/*      */ 
/*      */   public boolean supportsTransactions()
/*      */     throws SQLException
/*      */   {
/* 1740 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsTransactionIsolationLevel(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1757 */     return (paramInt == 2) || (paramInt == 8);
/*      */   }
/*      */ 
/*      */   public boolean supportsDataDefinitionAndDataManipulationTransactions()
/*      */     throws SQLException
/*      */   {
/* 1774 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsDataManipulationTransactionsOnly()
/*      */     throws SQLException
/*      */   {
/* 1789 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean dataDefinitionCausesTransactionCommit()
/*      */     throws SQLException
/*      */   {
/* 1804 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean dataDefinitionIgnoredInTransactions()
/*      */     throws SQLException
/*      */   {
/* 1818 */     return false;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getProcedures(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 1875 */     String str1 = "SELECT\n  -- Standalone procedures and functions\n  NULL AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Standalone procedure or function' AS remarks,\n  DECODE(object_type, 'PROCEDURE', 1,\n                      'FUNCTION', 2,\n                      0) AS procedure_type\nFROM all_objects\nWHERE (object_type = 'PROCEDURE' OR object_type = 'FUNCTION')\n  AND owner LIKE :1 ESCAPE '/'\n  AND object_name LIKE :2 ESCAPE '/'\n";
/*      */ 
/* 1888 */     String str2 = "SELECT\n  -- Packaged procedures with no arguments\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged procedure' AS remarks,\n  1 AS procedure_type\nFROM all_arguments\nWHERE argument_name IS NULL\n  AND data_type IS NULL\n  AND ";
/*      */ 
/* 1898 */     String str3 = "SELECT\n  -- Packaged procedures with arguments\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged procedure' AS remarks,\n  1 AS procedure_type\nFROM all_arguments\nWHERE argument_name IS NOT NULL\n  AND position = 1\n  AND position = sequence\n  AND ";
/*      */ 
/* 1908 */     String str4 = "SELECT\n  -- Packaged functions\n  package_name AS procedure_cat,\n  owner AS procedure_schem,\n  object_name AS procedure_name,\n  NULL,\n  NULL,\n  NULL,\n  'Packaged function' AS remarks,\n  2 AS procedure_type\nFROM all_arguments\nWHERE argument_name IS NULL\n  AND in_out = 'OUT'\n  AND ";
/*      */ 
/* 1920 */     String str5 = "package_name LIKE :3 ESCAPE '/'\n  AND owner LIKE :4 ESCAPE '/'\n  AND object_name LIKE :5 ESCAPE '/'\n";
/*      */ 
/* 1923 */     String str6 = "package_name IS NOT NULL\n  AND owner LIKE :6 ESCAPE '/'\n  AND object_name LIKE :7 ESCAPE '/'\n";
/*      */ 
/* 1928 */     String str7 = "ORDER BY procedure_schem, procedure_name\n";
/*      */ 
/* 1930 */     PreparedStatement localPreparedStatement = null;
/* 1931 */     String str8 = null;
/*      */ 
/* 1934 */     String str9 = paramString2;
/*      */ 
/* 1936 */     if (paramString2 == null)
/* 1937 */       str9 = "%";
/* 1938 */     else if (paramString2.equals("")) {
/* 1939 */       str9 = getUserName().toUpperCase();
/*      */     }
/* 1941 */     String str10 = paramString3;
/*      */ 
/* 1943 */     if (paramString3 == null)
/* 1944 */       str10 = "%";
/* 1945 */     else if (paramString3.equals("")) {
/* 1946 */       DatabaseError.throwSqlException(74);
/*      */     }
/* 1948 */     if (paramString1 == null)
/*      */     {
/* 1953 */       str8 = str1 + "UNION ALL " + str2 + str6 + "UNION ALL " + str3 + str6 + "UNION ALL " + str4 + str6 + str7;
/*      */ 
/* 1962 */       localPreparedStatement = this.connection.prepareStatement(str8);
/*      */ 
/* 1964 */       localPreparedStatement.setString(1, str9);
/* 1965 */       localPreparedStatement.setString(2, str10);
/* 1966 */       localPreparedStatement.setString(3, str9);
/* 1967 */       localPreparedStatement.setString(4, str10);
/* 1968 */       localPreparedStatement.setString(5, str9);
/* 1969 */       localPreparedStatement.setString(6, str10);
/* 1970 */       localPreparedStatement.setString(7, str9);
/* 1971 */       localPreparedStatement.setString(8, str10);
/*      */     }
/* 1973 */     else if (paramString1.equals(""))
/*      */     {
/* 1978 */       str8 = str1;
/*      */ 
/* 1983 */       localPreparedStatement = this.connection.prepareStatement(str8);
/*      */ 
/* 1985 */       localPreparedStatement.setString(1, str9);
/* 1986 */       localPreparedStatement.setString(2, str10);
/*      */     }
/*      */     else
/*      */     {
/* 1992 */       str8 = str2 + str5 + "UNION ALL " + str3 + str5 + "UNION ALL " + str4 + str5 + str7;
/*      */ 
/* 2000 */       localPreparedStatement = this.connection.prepareStatement(str8);
/*      */ 
/* 2002 */       localPreparedStatement.setString(1, paramString1);
/* 2003 */       localPreparedStatement.setString(2, str9);
/* 2004 */       localPreparedStatement.setString(3, str10);
/* 2005 */       localPreparedStatement.setString(4, paramString1);
/* 2006 */       localPreparedStatement.setString(5, str9);
/* 2007 */       localPreparedStatement.setString(6, str10);
/* 2008 */       localPreparedStatement.setString(7, paramString1);
/* 2009 */       localPreparedStatement.setString(8, str9);
/* 2010 */       localPreparedStatement.setString(9, str10);
/*      */     }
/*      */ 
/* 2014 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2017 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2019 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getProcedureColumns(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */     throws SQLException
/*      */   {
/* 2111 */     String str1 = "SELECT package_name AS procedure_cat,\n       owner AS procedure_schem,\n       object_name AS procedure_name,\n       argument_name AS column_name,\n       DECODE(position, 0, 5,\n                        DECODE(in_out, 'IN', 1,\n                                       'OUT', 4,\n                                       'IN/OUT', 2,\n                                       0)) AS column_type,\n       DECODE (data_type, 'CHAR', 1,\n                          'VARCHAR2', 12,\n                          'NUMBER', 3,\n                          'LONG', -1,\n                          'DATE', " + (this.connection.isV8Compatible() ? "93,\n" : "91,\n") + "                          'RAW', -3,\n" + "                          'LONG RAW', -4,\n" + "                          'TIMESTAMP', 93, \n" + "                          'TIMESTAMP WITH TIME ZONE', -101, \n" + "               'TIMESTAMP WITH LOCAL TIME ZONE', -102, \n" + "               'INTERVAL YEAR TO MONTH', -103, \n" + "               'INTERVAL DAY TO SECOND', -104, \n" + "               'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101," + "               1111) AS data_type,\n" + "       DECODE(data_type, 'OBJECT', type_owner || '.' || type_name, " + "              data_type) AS type_name,\n" + "       DECODE (data_precision, NULL, data_length,\n" + "                               data_precision) AS precision,\n" + "       data_length AS length,\n" + "       data_scale AS scale,\n" + "       10 AS radix,\n" + "       1 AS nullable,\n" + "       NULL AS remarks,\n" + "       sequence,\n" + "       overload,\n" + "       default_value\n" + " FROM all_arguments\n" + "WHERE owner LIKE :1 ESCAPE '/'\n" + "  AND object_name LIKE :2 ESCAPE '/'\n";
/*      */ 
/* 2147 */     String str2 = "  AND package_name LIKE :3 ESCAPE '/'\n";
/* 2148 */     String str3 = "  AND package_name IS NULL\n";
/*      */ 
/* 2150 */     String str4 = "  AND argument_name LIKE :4 ESCAPE '/'\n";
/* 2151 */     String str5 = "  AND (argument_name LIKE :5 ESCAPE '/'\n       OR (argument_name IS NULL\n           AND data_type IS NOT NULL))\n";
/*      */ 
/* 2157 */     String str6 = "ORDER BY procedure_schem, procedure_name, overload, sequence\n";
/*      */ 
/* 2160 */     String str7 = null;
/* 2161 */     PreparedStatement localPreparedStatement = null;
/* 2162 */     String str8 = null;
/*      */ 
/* 2165 */     String str9 = paramString2;
/*      */ 
/* 2167 */     if (paramString2 == null)
/* 2168 */       str9 = "%";
/* 2169 */     else if (paramString2.equals("")) {
/* 2170 */       str9 = getUserName().toUpperCase();
/*      */     }
/* 2172 */     String str10 = paramString3;
/*      */ 
/* 2174 */     if (paramString3 == null)
/* 2175 */       str10 = "%";
/* 2176 */     else if (paramString3.equals("")) {
/* 2177 */       DatabaseError.throwSqlException(74);
/*      */     }
/* 2179 */     String str11 = paramString4;
/*      */ 
/* 2181 */     if ((paramString4 == null) || (paramString4.equals("%")))
/*      */     {
/* 2183 */       str11 = "%";
/* 2184 */       str8 = str5;
/*      */     }
/* 2186 */     else if (paramString4.equals("")) {
/* 2187 */       DatabaseError.throwSqlException(74);
/*      */     } else {
/* 2189 */       str8 = str4;
/*      */     }
/* 2191 */     if (paramString1 == null)
/*      */     {
/* 2196 */       str7 = str1 + str8 + str6;
/*      */ 
/* 2200 */       localPreparedStatement = this.connection.prepareStatement(str7);
/*      */ 
/* 2202 */       localPreparedStatement.setString(1, str9);
/* 2203 */       localPreparedStatement.setString(2, str10);
/* 2204 */       localPreparedStatement.setString(3, str11);
/*      */     }
/* 2206 */     else if (paramString1.equals(""))
/*      */     {
/* 2211 */       str7 = str1 + str3 + str8 + str6;
/*      */ 
/* 2216 */       localPreparedStatement = this.connection.prepareStatement(str7);
/*      */ 
/* 2218 */       localPreparedStatement.setString(1, str9);
/* 2219 */       localPreparedStatement.setString(2, str10);
/* 2220 */       localPreparedStatement.setString(3, str11);
/*      */     }
/*      */     else
/*      */     {
/* 2226 */       str7 = str1 + str2 + str8 + str6;
/*      */ 
/* 2231 */       localPreparedStatement = this.connection.prepareStatement(str7);
/*      */ 
/* 2233 */       localPreparedStatement.setString(1, str9);
/* 2234 */       localPreparedStatement.setString(2, str10);
/* 2235 */       localPreparedStatement.setString(3, paramString1);
/* 2236 */       localPreparedStatement.setString(4, str11);
/*      */     }
/*      */ 
/* 2240 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2243 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2245 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getTables(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString)
/*      */     throws SQLException
/*      */   {
/* 2336 */     String str1 = "SELECT NULL AS table_cat,\n       o.owner AS table_schem,\n       o.object_name AS table_name,\n       o.object_type AS table_type,\n";
/*      */ 
/* 2341 */     String str2 = "       c.comments AS remarks\n";
/* 2342 */     String str3 = "       NULL AS remarks\n";
/*      */ 
/* 2344 */     String str4 = "  FROM all_objects o, all_tab_comments c\n";
/* 2345 */     String str5 = "  FROM all_objects o\n";
/*      */ 
/* 2347 */     String str6 = "  WHERE o.owner LIKE :1 ESCAPE '/'\n    AND o.object_name LIKE :2 ESCAPE '/'\n";
/*      */ 
/* 2350 */     String str7 = "    AND o.owner = c.owner (+)\n    AND o.object_name = c.table_name (+)\n";
/*      */ 
/* 2353 */     int i = 0;
/*      */ 
/* 2355 */     String str8 = "";
/* 2356 */     String str9 = "";
/*      */ 
/* 2359 */     if (paramArrayOfString != null)
/*      */     {
/* 2367 */       str8 = "    AND o.object_type IN ('xxx'";
/* 2368 */       str9 = "    AND o.object_type IN ('xxx'";
/*      */ 
/* 2370 */       for (int j = 0; j < paramArrayOfString.length; j++)
/*      */       {
/* 2372 */         if (paramArrayOfString[j].equals("SYNONYM"))
/*      */         {
/* 2376 */           str8 = str8 + ", '" + paramArrayOfString[j] + "'";
/* 2377 */           i = 1;
/*      */         }
/*      */         else
/*      */         {
/* 2383 */           str8 = str8 + ", '" + paramArrayOfString[j] + "'";
/* 2384 */           str9 = str9 + ", '" + paramArrayOfString[j] + "'";
/*      */         }
/*      */       }
/*      */ 
/* 2388 */       str8 = str8 + ")\n";
/* 2389 */       str9 = str9 + ")\n";
/*      */     }
/*      */     else
/*      */     {
/* 2393 */       i = 1;
/* 2394 */       str8 = "    AND o.object_type IN ('TABLE', 'SYNONYM', 'VIEW')\n";
/* 2395 */       str9 = "    AND o.object_type IN ('TABLE', 'VIEW')\n";
/*      */     }
/*      */ 
/* 2398 */     String str10 = "  ORDER BY table_type, table_schem, table_name\n";
/*      */ 
/* 2400 */     String str11 = "SELECT NULL AS table_cat,\n       s.owner AS table_schem,\n       s.synonym_name AS table_name,\n       'SYNONYM' AS table_table_type,\n";
/*      */ 
/* 2405 */     String str12 = "       c.comments AS remarks\n";
/* 2406 */     String str13 = "       NULL AS remarks\n";
/*      */ 
/* 2408 */     String str14 = "  FROM all_synonyms s, all_objects o, all_tab_comments c\n";
/*      */ 
/* 2410 */     String str15 = "  FROM all_synonyms s, all_objects o\n";
/*      */ 
/* 2412 */     String str16 = "  WHERE s.owner LIKE :3 ESCAPE '/'\n    AND s.synonym_name LIKE :4 ESCAPE '/'\n    AND s.table_owner = o.owner\n    AND s.table_name = o.object_name\n    AND o.object_type IN ('TABLE', 'VIEW')\n";
/*      */ 
/* 2419 */     String str17 = "";
/*      */ 
/* 2421 */     str17 = str17 + str1;
/*      */ 
/* 2423 */     if (this.connection.getRemarksReporting())
/* 2424 */       str17 = str17 + str2 + str4;
/*      */     else {
/* 2426 */       str17 = str17 + str3 + str5;
/*      */     }
/* 2428 */     str17 = str17 + str6;
/*      */ 
/* 2430 */     if (this.connection.getRestrictGetTables())
/* 2431 */       str17 = str17 + str9;
/*      */     else {
/* 2433 */       str17 = str17 + str8;
/*      */     }
/* 2435 */     if (this.connection.getRemarksReporting()) {
/* 2436 */       str17 = str17 + str7;
/*      */     }
/* 2438 */     if ((i != 0) && (this.connection.getRestrictGetTables()))
/*      */     {
/* 2442 */       str17 = str17 + "UNION\n" + str11;
/*      */ 
/* 2444 */       if (this.connection.getRemarksReporting())
/* 2445 */         str17 = str17 + str12 + str14;
/*      */       else {
/* 2447 */         str17 = str17 + str13 + str15;
/*      */       }
/* 2449 */       str17 = str17 + str16;
/*      */ 
/* 2451 */       if (this.connection.getRemarksReporting()) {
/* 2452 */         str17 = str17 + str7;
/*      */       }
/*      */     }
/* 2455 */     str17 = str17 + str10;
/*      */ 
/* 2460 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement(str17);
/*      */ 
/* 2462 */     localPreparedStatement.setString(1, paramString2 == null ? "%" : paramString2);
/* 2463 */     localPreparedStatement.setString(2, paramString3 == null ? "%" : paramString3);
/*      */ 
/* 2465 */     if ((i != 0) && (this.connection.getRestrictGetTables()))
/*      */     {
/* 2467 */       localPreparedStatement.setString(3, paramString2 == null ? "%" : paramString2);
/* 2468 */       localPreparedStatement.setString(4, paramString3 == null ? "%" : paramString3);
/*      */     }
/*      */ 
/* 2471 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2474 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2476 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getSchemas()
/*      */     throws SQLException
/*      */   {
/* 2494 */     Statement localStatement = this.connection.createStatement();
/* 2495 */     String str = "SELECT username AS table_schem FROM all_users ORDER BY table_schem";
/*      */ 
/* 2497 */     OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
/*      */ 
/* 2500 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2502 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getCatalogs()
/*      */     throws SQLException
/*      */   {
/* 2523 */     Statement localStatement = this.connection.createStatement();
/* 2524 */     String str = "select 'nothing' as table_cat from dual where 1 = 2";
/* 2525 */     OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
/*      */ 
/* 2528 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2530 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getTableTypes()
/*      */     throws SQLException
/*      */   {
/* 2550 */     Statement localStatement = this.connection.createStatement();
/* 2551 */     String str = "select 'TABLE' as table_type from dual\nunion select 'VIEW' as table_type from dual\nunion select 'SYNONYM' as table_type from dual\n";
/*      */ 
/* 2555 */     OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
/*      */ 
/* 2558 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2560 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */     throws SQLException
/*      */   {
/* 2617 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 2619 */     return null;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getColumnPrivileges(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */     throws SQLException
/*      */   {
/* 2670 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       table_schema AS table_schem,\n       table_name,\n       column_name,\n       grantor,\n       grantee,\n       privilege,\n       grantable AS is_grantable\nFROM all_col_privs\nWHERE table_schema LIKE :1 ESCAPE '/'\n  AND table_name LIKE :2 ESCAPE '/'\n  AND column_name LIKE :3 ESCAPE '/'\nORDER BY column_name, privilege\n");
/*      */ 
/* 2684 */     localPreparedStatement.setString(1, paramString2 == null ? "%" : paramString2);
/* 2685 */     localPreparedStatement.setString(2, paramString3 == null ? "%" : paramString3);
/* 2686 */     localPreparedStatement.setString(3, paramString4 == null ? "%" : paramString4);
/*      */ 
/* 2688 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2691 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2693 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getTablePrivileges(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 2730 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       table_schema AS table_schem,\n       table_name,\n       grantor,\n       grantee,\n       privilege,\n       grantable AS is_grantable\nFROM all_tab_privs\nWHERE table_schema LIKE :1 ESCAPE '/'\n  AND table_name LIKE :2 ESCAPE '/'\nORDER BY table_schem, table_name, privilege\n");
/*      */ 
/* 2738 */     localPreparedStatement.setString(1, paramString2 == null ? "%" : paramString2);
/* 2739 */     localPreparedStatement.setString(2, paramString3 == null ? "%" : paramString3);
/*      */ 
/* 2741 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2744 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2746 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getBestRowIdentifier(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2791 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT 1 AS scope, 'ROWID' AS column_name, -8 AS data_type,\n 'ROWID' AS type_name, 0 AS column_size, 0 AS buffer_length,\n       0 AS decimal_digits, 2 AS pseudo_column\nFROM DUAL\nWHERE :1 = 1\nUNION\nSELECT 2 AS scope,\n  t.column_name,\n DECODE (t.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n 'LONG', -1, 'DATE', " + (this.connection.isV8Compatible() ? "93,\n" : "91,\n") + " 'RAW', -3, 'LONG RAW', -4, \n" + " 'TIMESTAMP(6)', 93, " + " 'TIMESTAMP(6) WITH TIME ZONE', -101, \n" + " 'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n" + " 'INTERVAL YEAR(2) TO MONTH', -103, \n" + " 'INTERVAL DAY(2) TO SECOND(6)', -104, \n" + " 'BINARY_FLOAT', 100, " + " 'BINARY_DOUBLE', 101," + " 1111)\n" + " AS data_type,\n" + " t.data_type AS type_name,\n" + " DECODE (t.data_precision, null, t.data_length, t.data_precision)\n" + "  AS column_size,\n" + "  0 AS buffer_length,\n" + "  t.data_scale AS decimal_digits,\n" + "       1 AS pseudo_column\n" + "FROM all_tab_columns t, all_ind_columns i\n" + "WHERE :2 = 1\n" + "  AND t.table_name = :3\n" + "  AND t.owner like :4 escape '/'\n" + "  AND t.nullable != :5\n" + "  AND t.owner = i.table_owner\n" + "  AND t.table_name = i.table_name\n" + "  AND t.column_name = i.column_name\n");
/*      */ 
/* 2828 */     switch (paramInt)
/*      */     {
/*      */     case 0:
/* 2832 */       localPreparedStatement.setInt(1, 0);
/* 2833 */       localPreparedStatement.setInt(2, 0);
/*      */ 
/* 2835 */       break;
/*      */     case 1:
/* 2838 */       localPreparedStatement.setInt(1, 1);
/* 2839 */       localPreparedStatement.setInt(2, 1);
/*      */ 
/* 2841 */       break;
/*      */     case 2:
/* 2844 */       localPreparedStatement.setInt(1, 0);
/* 2845 */       localPreparedStatement.setInt(2, 1);
/*      */     }
/*      */ 
/* 2850 */     localPreparedStatement.setString(3, paramString3);
/* 2851 */     localPreparedStatement.setString(4, paramString2 == null ? "%" : paramString2);
/* 2852 */     localPreparedStatement.setString(5, paramBoolean ? "X" : "Y");
/*      */ 
/* 2854 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2857 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2859 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getVersionColumns(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 2925 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT 0 AS scope,\n t.column_name,\n DECODE (c.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n  'LONG', -1, 'DATE',  " + (this.connection.isV8Compatible() ? "93,\n" : "91,\n") + "  'RAW', -3, 'LONG RAW', -4, " + "  'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, \n" + "  'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n" + "  'INTERVAL YEAR(2) TO MONTH', -103, \n" + "  'INTERVAL DAY(2) TO SECOND(6)', -104, \n" + "  'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101," + "   1111)\n " + " AS data_type,\n" + "       c.data_type AS type_name,\n" + " DECODE (c.data_precision, null, c.data_length, c.data_precision)\n" + "   AS column_size,\n" + "       0 as buffer_length,\n" + "   c.data_scale as decimal_digits,\n" + "   0 as pseudo_column\n" + "FROM all_trigger_cols t, all_tab_columns c\n" + "WHERE t.table_name = :1\n" + "  AND c.owner like :2 escape '/'\n" + " AND t.table_owner = c.owner\n" + "  AND t.table_name = c.table_name\n" + " AND t.column_name = c.column_name\n");
/*      */ 
/* 2948 */     localPreparedStatement.setString(1, paramString3);
/* 2949 */     localPreparedStatement.setString(2, paramString2 == null ? "%" : paramString2);
/*      */ 
/* 2951 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 2954 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 2956 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet getPrimaryKeys(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 2999 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT NULL AS table_cat,\n       c.owner AS table_schem,\n       c.table_name,\n       c.column_name,\n       c.position AS key_seq,\n       c.constraint_name AS pk_name\nFROM all_cons_columns c, all_constraints k\nWHERE k.constraint_type = 'P'\n  AND k.table_name = :1\n  AND k.owner like :2 escape '/'\n  AND k.constraint_name = c.constraint_name \n  AND k.table_name = c.table_name \n  AND k.owner = c.owner \nORDER BY column_name\n");
/*      */ 
/* 3010 */     localPreparedStatement.setString(1, paramString3);
/* 3011 */     localPreparedStatement.setString(2, paramString2 == null ? "%" : paramString2);
/*      */ 
/* 3013 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 3016 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 3018 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   ResultSet keys_query(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*      */     throws SQLException
/*      */   {
/* 3029 */     int i = 1;
/* 3030 */     int j = paramString2 != null ? i++ : 0;
/* 3031 */     int k = paramString4 != null ? i++ : 0;
/* 3032 */     int m = (paramString1 != null) && (paramString1.length() > 0) ? i++ : 0;
/* 3033 */     int n = (paramString3 != null) && (paramString3.length() > 0) ? i++ : 0;
/*      */ 
/* 3036 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement("SELECT NULL AS pktable_cat,\n       p.owner as pktable_schem,\n       p.table_name as pktable_name,\n       pc.column_name as pkcolumn_name,\n       NULL as fktable_cat,\n       f.owner as fktable_schem,\n       f.table_name as fktable_name,\n       fc.column_name as fkcolumn_name,\n       fc.position as key_seq,\n       NULL as update_rule,\n       decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) as delete_rule,\n       f.constraint_name as fk_name,\n       p.constraint_name as pk_name,\n       decode(f.deferrable,       'DEFERRABLE',5      ,'NOT DEFERRABLE',7      , 'DEFERRED', 6      ) deferrability \n      FROM all_cons_columns pc, all_constraints p,\n      all_cons_columns fc, all_constraints f\nWHERE 1 = 1\n" + (j != 0 ? "  AND p.table_name = :1\n" : "") + (k != 0 ? "  AND f.table_name = :2\n" : "") + (m != 0 ? "  AND p.owner = :3\n" : "") + (n != 0 ? "  AND f.owner = :4\n" : "") + "  AND f.constraint_type = 'R'\n" + "  AND p.owner = f.r_owner\n" + "  AND p.constraint_name = f.r_constraint_name\n" + "  AND p.constraint_type = 'P'\n" + "  AND pc.owner = p.owner\n" + "  AND pc.constraint_name = p.constraint_name\n" + "  AND pc.table_name = p.table_name\n" + "  AND fc.owner = f.owner\n" + "  AND fc.constraint_name = f.constraint_name\n" + "  AND fc.table_name = f.table_name\n" + "  AND fc.position = pc.position\n" + paramString5);
/*      */ 
/* 3066 */     if (j != 0)
/*      */     {
/* 3068 */       localPreparedStatement.setString(j, paramString2);
/*      */     }
/*      */ 
/* 3071 */     if (k != 0)
/*      */     {
/* 3073 */       localPreparedStatement.setString(k, paramString4);
/*      */     }
/*      */ 
/* 3076 */     if (m != 0)
/*      */     {
/* 3078 */       localPreparedStatement.setString(m, paramString1);
/*      */     }
/*      */ 
/* 3081 */     if (n != 0)
/*      */     {
/* 3083 */       localPreparedStatement.setString(n, paramString3);
/*      */     }
/*      */ 
/* 3086 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 3089 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 3091 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getImportedKeys(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 3153 */     return keys_query(null, null, paramString2, paramString3, "ORDER BY pktable_schem, pktable_name, key_seq");
/*      */   }
/*      */ 
/*      */   public ResultSet getExportedKeys(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 3235 */     return keys_query(paramString2, paramString3, null, null, "ORDER BY fktable_schem, fktable_name, key_seq");
/*      */   }
/*      */ 
/*      */   public ResultSet getCrossReference(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
/*      */     throws SQLException
/*      */   {
/* 3309 */     return keys_query(paramString2, paramString3, paramString5, paramString6, "ORDER BY fktable_schem, fktable_name, key_seq");
/*      */   }
/*      */ 
/*      */   public ResultSet getTypeInfo()
/*      */     throws SQLException
/*      */   {
/* 3361 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 3363 */     return null;
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getIndexInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/* 3460 */     Statement localStatement = this.connection.createStatement();
/*      */ 
/* 3468 */     if (((paramString2 != null) && (paramString2.length() != 0) && (!OracleSql.isValidObjectName(paramString2))) || ((paramString3 != null) && (paramString3.length() != 0) && (!OracleSql.isValidObjectName(paramString3))))
/*      */     {
/* 3475 */       DatabaseError.throwSqlException(68);
/*      */     }
/*      */ 
/* 3478 */     String str1 = "analyze table " + (paramString2 == null ? "" : new StringBuffer().append(paramString2).append(".").toString()) + paramString3 + (paramBoolean2 ? " estimate statistics" : " compute statistics");
/*      */ 
/* 3485 */     localStatement.executeUpdate(str1);
/*      */ 
/* 3488 */     String str2 = "select null as table_cat,\n       owner as table_schem,\n       table_name,\n       0 as NON_UNIQUE,\n       null as index_qualifier,\n       null as index_name, 0 as type,\n       0 as ordinal_position, null as column_name,\n       null as asc_or_desc,\n       num_rows as cardinality,\n       blocks as pages,\n       null as filter_condition\nfrom all_tables\nwhere table_name = '" + paramString3 + "'\n";
/*      */ 
/* 3498 */     String str3 = "";
/*      */ 
/* 3500 */     if ((paramString2 != null) && (paramString2.length() > 0)) {
/* 3501 */       str3 = "  and owner = '" + paramString2 + "'\n";
/*      */     }
/*      */ 
/* 3505 */     String str4 = "select null as table_cat,\n       i.owner as table_schem,\n       i.table_name,\n       decode (i.uniqueness, 'UNIQUE', 0, 1),\n       null as index_qualifier,\n       i.index_name,\n       1 as type,\n       c.column_position as ordinal_position,\n       c.column_name,\n       null as asc_or_desc,\n       i.distinct_keys as cardinality,\n       i.leaf_blocks as pages,\n       null as filter_condition\nfrom all_indexes i, all_ind_columns c\nwhere i.table_name = '" + paramString3 + "'\n";
/*      */ 
/* 3520 */     String str5 = "";
/*      */ 
/* 3522 */     if ((paramString2 != null) && (paramString2.length() > 0)) {
/* 3523 */       str5 = "  and i.owner = '" + paramString2 + "'\n";
/*      */     }
/* 3525 */     String str6 = "";
/*      */ 
/* 3527 */     if (paramBoolean1) {
/* 3528 */       str6 = "  and i.uniqueness = 'UNIQUE'\n";
/*      */     }
/* 3530 */     String str7 = "  and i.index_name = c.index_name\n  and i.table_owner = c.table_owner\n  and i.table_name = c.table_name\n  and i.owner = c.index_owner\n";
/*      */ 
/* 3535 */     String str8 = "order by non_unique, type, index_name, ordinal_position\n";
/*      */ 
/* 3538 */     String str9 = str2 + str3 + "union\n" + str4 + str5 + str6 + str7 + str8;
/*      */ 
/* 3542 */     OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str9);
/*      */ 
/* 3545 */     localOracleResultSet.closeStatementOnClose();
/*      */ 
/* 3547 */     return localOracleResultSet;
/*      */   }
/*      */ 
/*      */   SQLException fail()
/*      */   {
/* 3573 */     SQLException localSQLException = new SQLException("Not implemented yet");
/*      */ 
/* 3575 */     return localSQLException;
/*      */   }
/*      */ 
/*      */   public boolean supportsResultSetType(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3596 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsResultSetConcurrency(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 3616 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean ownUpdatesAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3633 */     return paramInt != 1003;
/*      */   }
/*      */ 
/*      */   public boolean ownDeletesAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3650 */     return paramInt != 1003;
/*      */   }
/*      */ 
/*      */   public boolean ownInsertsAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3667 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean othersUpdatesAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3685 */     return paramInt == 1005;
/*      */   }
/*      */ 
/*      */   public boolean othersDeletesAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3706 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean othersInsertsAreVisible(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3725 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean updatesAreDetected(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3744 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean deletesAreDetected(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3762 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean insertsAreDetected(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3781 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsBatchUpdates()
/*      */     throws SQLException
/*      */   {
/* 3798 */     return true;
/*      */   }
/*      */ 
/*      */   public ResultSet getUDTs(String paramString1, String paramString2, String paramString3, int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/* 3845 */     int i = 0;
/*      */ 
/* 3847 */     if ((paramString3 == null) || (paramString3.length() == 0))
/*      */     {
/* 3849 */       i = 0;
/*      */     }
/* 3853 */     else if (paramArrayOfInt == null)
/*      */     {
/* 3855 */       i = 1;
/*      */     }
/*      */     else
/*      */     {
/* 3859 */       for (int j = 0; j < paramArrayOfInt.length; j++)
/*      */       {
/* 3861 */         if (paramArrayOfInt[j] != 2002)
/*      */           continue;
/* 3863 */         i = 1;
/*      */ 
/* 3865 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3872 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/* 3874 */     localStringBuffer.append("SELECT NULL AS TYPE_CAT, owner AS TYPE_SCHEM, type_name, NULL AS CLASS_NAME, 'STRUCT' AS DATA_TYPE, NULL AS REMARKS FROM all_types ");
/*      */ 
/* 3876 */     if (i != 0)
/*      */     {
/* 3878 */       localStringBuffer.append("WHERE typecode = 'OBJECT' AND owner LIKE :1 ESCAPE '/' AND type_name LIKE :2 ESCAPE '/'");
/*      */     }
/*      */     else
/*      */     {
/* 3882 */       localStringBuffer.append("WHERE 1 = 2");
/*      */     }
/*      */ 
/* 3886 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));
/*      */ 
/* 3889 */     if (i != 0)
/*      */     {
/* 3894 */       localObject = new String[1];
/* 3895 */       String[] arrayOfString = new String[1];
/*      */ 
/* 3897 */       if (SQLName.parse(paramString3, localObject, arrayOfString))
/*      */       {
/* 3899 */         localPreparedStatement.setString(1, localObject[0]);
/* 3900 */         localPreparedStatement.setString(2, arrayOfString[0]);
/*      */       }
/*      */       else
/*      */       {
/* 3904 */         if (paramString2 != null)
/* 3905 */           localPreparedStatement.setString(1, paramString2);
/*      */         else {
/* 3907 */           localPreparedStatement.setNull(1, 12);
/*      */         }
/* 3909 */         localPreparedStatement.setString(2, paramString3);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3914 */     Object localObject = (OracleResultSet)localPreparedStatement.executeQuery();
/*      */ 
/* 3917 */     ((OracleResultSet)localObject).closeStatementOnClose();
/*      */ 
/* 3919 */     return (ResultSet)localObject;
/*      */   }
/*      */ 
/*      */   public Connection getConnection()
/*      */     throws SQLException
/*      */   {
/* 3932 */     return this.connection.getWrapper();
/*      */   }
/*      */ 
/*      */   public boolean supportsSavepoints()
/*      */     throws SQLException
/*      */   {
/* 3950 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsNamedParameters()
/*      */     throws SQLException
/*      */   {
/* 3966 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsMultipleOpenResults()
/*      */     throws SQLException
/*      */   {
/* 3984 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean supportsGetGeneratedKeys()
/*      */     throws SQLException
/*      */   {
/* 4000 */     return true;
/*      */   }
/*      */ 
/*      */   public ResultSet getSuperTypes(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 4048 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 4050 */     return null;
/*      */   }
/*      */ 
/*      */   public ResultSet getSuperTables(String paramString1, String paramString2, String paramString3)
/*      */     throws SQLException
/*      */   {
/* 4091 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 4093 */     return null;
/*      */   }
/*      */ 
/*      */   public ResultSet getAttributes(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */     throws SQLException
/*      */   {
/* 4206 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 4208 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean supportsResultSetHoldability(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4236 */     return paramInt == 1;
/*      */   }
/*      */ 
/*      */   public int getResultSetHoldability()
/*      */     throws SQLException
/*      */   {
/* 4265 */     return 1;
/*      */   }
/*      */ 
/*      */   public int getDatabaseMajorVersion()
/*      */     throws SQLException
/*      */   {
/* 4281 */     return this.connection.getVersionNumber() / 1000;
/*      */   }
/*      */ 
/*      */   public int getDatabaseMinorVersion()
/*      */     throws SQLException
/*      */   {
/* 4295 */     return this.connection.getVersionNumber() % 1000 / 100;
/*      */   }
/*      */ 
/*      */   public int getJDBCMajorVersion()
/*      */     throws SQLException
/*      */   {
/* 4310 */     return DRIVER_MAJOR_VERSION;
/*      */   }
/*      */ 
/*      */   public int getJDBCMinorVersion()
/*      */     throws SQLException
/*      */   {
/* 4325 */     return DRIVER_MINOR_VERSION;
/*      */   }
/*      */ 
/*      */   public int getSQLStateType()
/*      */     throws SQLException
/*      */   {
/* 4364 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean locatorsUpdateCopy()
/*      */     throws SQLException
/*      */   {
/* 4374 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean supportsStatementPooling()
/*      */     throws SQLException
/*      */   {
/* 4384 */     return true;
/*      */   }
/*      */ 
/*      */   public static String getDriverNameInfo()
/*      */     throws SQLException
/*      */   {
/* 4400 */     return DRIVER_NAME;
/*      */   }
/*      */ 
/*      */   public static String getDriverVersionInfo()
/*      */     throws SQLException
/*      */   {
/* 4411 */     return DRIVER_VERSION;
/*      */   }
/*      */ 
/*      */   public static int getDriverMajorVersionInfo()
/*      */   {
/* 4421 */     return DRIVER_MAJOR_VERSION;
/*      */   }
/*      */ 
/*      */   public static int getDriverMinorVersionInfo()
/*      */   {
/* 4431 */     return DRIVER_MINOR_VERSION;
/*      */   }
/*      */ 
/*      */   public static void setGetLobPrecision(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*      */   }
/*      */ 
/*      */   public static boolean getGetLobPrecision()
/*      */     throws SQLException
/*      */   {
/* 4466 */     return false;
/*      */   }
/*      */ 
/*      */   public static String getLobPrecision() throws SQLException
/*      */   {
/* 4471 */     return getGetLobPrecision() ? LOB_MAXSIZE : "-1";
/*      */   }
/*      */ 
/*      */   public long getLobMaxLength()
/*      */     throws SQLException
/*      */   {
/* 4481 */     return this.connection.getVersionNumber() >= 10000 ? 9223372036854775807L : LOB_MAXLENGTH_32BIT;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleDatabaseMetaData
 * JD-Core Version:    0.6.0
 */