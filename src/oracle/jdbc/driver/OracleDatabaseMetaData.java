/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import oracle.jdbc.internal.OracleResultSet;
/*     */ 
/*     */ public class OracleDatabaseMetaData extends oracle.jdbc.OracleDatabaseMetaData
/*     */ {
/* 601 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleDatabaseMetaData(oracle.jdbc.internal.OracleConnection paramOracleConnection)
/*     */   {
/*  34 */     super(paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public OracleDatabaseMetaData(OracleConnection paramOracleConnection)
/*     */   {
/*  39 */     this(paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public String getTimeDateFunctions()
/*     */     throws SQLException
/*     */   {
/*  56 */     if (((PhysicalConnection)this.connection).v8Compatible) {
/*  57 */       return "MONTH,YEAR";
/*     */     }
/*  59 */     return "HOUR,MINUTE,SECOND,MONTH,YEAR";
/*     */   }
/*     */ 
/*     */   public synchronized ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4)
/*     */     throws SQLException
/*     */   {
/* 129 */     String str1 = "SELECT NULL AS table_cat,\n";
/*     */ 
/* 131 */     String str2 = "       t.owner AS table_schem,\n       t.table_name AS table_name,\n";
/*     */ 
/* 134 */     String str3 = "       DECODE(s.table_owner, NULL, t.owner, s.table_owner)\n              AS table_schem,\n       DECODE(s.synonym_name, NULL, t.table_name, s.synonym_name)\n              AS table_name,\n";
/*     */ 
/* 140 */     String str4 = "       t.column_name AS column_name,\n       DECODE (t.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3,\n               'LONG', -1, 'DATE', " + (((PhysicalConnection)this.connection).v8Compatible ? "93" : "91") + ", 'RAW', -3, 'LONG RAW', -4,  \n" + "               'BLOB', 2004, 'CLOB', 2005, 'BFILE', -13, 'FLOAT', 6, \n" + "               'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, \n" + "               'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, \n" + "               'INTERVAL YEAR(2) TO MONTH', -103, \n" + "               'INTERVAL DAY(2) TO SECOND(6)', -104, \n" + "               'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101, \n" + "               1111)\n" + "              AS data_type,\n" + "       t.data_type AS type_name,\n" + "       DECODE (t.data_precision, null, t.data_length, t.data_precision)\n" + "              AS column_size,\n" + "       0 AS buffer_length,\n" + "       t.data_scale AS decimal_digits,\n" + "       10 AS num_prec_radix,\n" + "       DECODE (t.nullable, 'N', 0, 1) AS nullable,\n";
/*     */ 
/* 160 */     String str5 = "       c.comments AS remarks,\n";
/*     */ 
/* 162 */     String str6 = "       NULL AS remarks,\n";
/*     */ 
/* 164 */     String str7 = "       t.data_default AS column_def,\n       0 AS sql_data_type,\n       0 AS sql_datetime_sub,\n       t.data_length AS char_octet_length,\n       t.column_id AS ordinal_position,\n       DECODE (t.nullable, 'N', 'NO', 'YES') AS is_nullable\n";
/*     */ 
/* 171 */     String str8 = "FROM all_tab_columns t";
/*     */ 
/* 173 */     String str9 = ", all_synonyms s";
/* 174 */     String str10 = ", all_col_comments c";
/*     */ 
/* 176 */     String str11 = "WHERE t.owner LIKE :1 ESCAPE '/'\n  AND t.table_name LIKE :2 ESCAPE '/'\n  AND t.column_name LIKE :3 ESCAPE '/'\n";
/*     */ 
/* 180 */     String str12 = "WHERE (t.owner LIKE :4 ESCAPE '/' OR\n       (s.owner LIKE :5 ESCAPE '/' AND t.owner = s.table_owner))\n  AND (t.table_name LIKE :6 ESCAPE '/' OR\n       s.synonym_name LIKE :7 ESCAPE '/')\n  AND t.column_name LIKE :8 ESCAPE '/'\n";
/*     */ 
/* 187 */     String str13 = "  AND t.owner = c.owner (+)\n  AND t.table_name = c.table_name (+)\n  AND t.column_name = c.column_name (+)\n";
/*     */ 
/* 191 */     String str14 = "  AND s.table_name (+) = t.table_name\n  AND ((DECODE(s.owner, t.owner, 'OK',\n                       'PUBLIC', 'OK',\n                       NULL, 'OK',\n                       'NOT OK') = 'OK') OR\n       (s.owner LIKE :9 AND t.owner = s.table_owner))";
/*     */ 
/* 199 */     String str15 = "ORDER BY table_schem, table_name, ordinal_position\n";
/*     */ 
/* 205 */     String str16 = str1;
/*     */ 
/* 207 */     if (this.connection.getIncludeSynonyms())
/* 208 */       str16 = str16 + str3;
/*     */     else {
/* 210 */       str16 = str16 + str2;
/*     */     }
/* 212 */     str16 = str16 + str4;
/*     */ 
/* 214 */     if (this.connection.getRemarksReporting())
/* 215 */       str16 = str16 + str5;
/*     */     else {
/* 217 */       str16 = str16 + str6;
/*     */     }
/* 219 */     str16 = str16 + str7 + str8;
/*     */ 
/* 221 */     if (this.connection.getRemarksReporting()) {
/* 222 */       str16 = str16 + str10;
/*     */     }
/* 224 */     if (this.connection.getIncludeSynonyms()) {
/* 225 */       str16 = str16 + str9;
/*     */     }
/* 227 */     if (this.connection.getIncludeSynonyms())
/* 228 */       str16 = str16 + "\n" + str12;
/*     */     else {
/* 230 */       str16 = str16 + "\n" + str11;
/*     */     }
/* 232 */     if (this.connection.getRemarksReporting()) {
/* 233 */       str16 = str16 + str13;
/*     */     }
/* 235 */     if (this.connection.getIncludeSynonyms()) {
/* 236 */       str16 = str16 + str14;
/*     */     }
/* 238 */     str16 = str16 + "\n" + str15;
/*     */ 
/* 244 */     PreparedStatement localPreparedStatement = this.connection.prepareStatement(str16);
/*     */ 
/* 246 */     if (this.connection.getIncludeSynonyms())
/*     */     {
/* 248 */       localPreparedStatement.setString(1, paramString2 == null ? "%" : paramString2);
/* 249 */       localPreparedStatement.setString(2, paramString2 == null ? "%" : paramString2);
/* 250 */       localPreparedStatement.setString(3, paramString3 == null ? "%" : paramString3);
/* 251 */       localPreparedStatement.setString(4, paramString3 == null ? "%" : paramString3);
/* 252 */       localPreparedStatement.setString(5, paramString4 == null ? "%" : paramString4);
/* 253 */       localPreparedStatement.setString(6, paramString2 == null ? "%" : paramString2);
/*     */     }
/*     */     else
/*     */     {
/* 257 */       localPreparedStatement.setString(1, paramString2 == null ? "%" : paramString2);
/* 258 */       localPreparedStatement.setString(2, paramString3 == null ? "%" : paramString3);
/* 259 */       localPreparedStatement.setString(3, paramString4 == null ? "%" : paramString4);
/*     */     }
/*     */ 
/* 262 */     OracleResultSet localOracleResultSet = (OracleResultSet)localPreparedStatement.executeQuery();
/*     */ 
/* 265 */     localOracleResultSet.closeStatementOnClose();
/*     */ 
/* 267 */     return localOracleResultSet;
/*     */   }
/*     */ 
/*     */   public ResultSet getTypeInfo()
/*     */     throws SQLException
/*     */   {
/* 321 */     Statement localStatement = this.connection.createStatement();
/* 322 */     int i = this.connection.getVersionNumber();
/*     */ 
/* 396 */     String str1 = "union select\n 'CHAR' as type_name, 1 as data_type, " + (i >= 8100 ? 2000 : 255) + " as precision,\n" + " '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 1 as case_sensitive, 3 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'CHAR' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 406 */     String str2 = "union select\n 'VARCHAR2' as type_name, 12 as data_type, " + (i >= 8100 ? 4000 : 2000) + " as precision,\n" + " '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 1 as case_sensitive, 3 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'VARCHAR2' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 426 */     String str3 = "union select\n 'DATE' as type_name, " + (((PhysicalConnection)this.connection).v8Compatible ? "93" : "91") + "as data_type, 7 as precision,\n" + " NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 0 as case_sensitive, 3 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'DATE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 498 */     String str4 = "union select\n 'RAW' as type_name, -3 as data_type, " + (i >= 8100 ? 2000 : 255) + " as precision,\n" + " '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 0 as case_sensitive, 3 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'RAW' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 518 */     String str5 = "-1";
/*     */ 
/* 520 */     String str6 = "union select\n 'BLOB' as type_name, 2004 as data_type, " + str5 + " as precision,\n" + " null as literal_prefix, null as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 0 as case_sensitive, 0 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'BLOB' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 530 */     String str7 = "union select\n 'CLOB' as type_name, 2005 as data_type, " + str5 + " as precision,\n" + " '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n" + " 1 as nullable, 1 as case_sensitive, 0 as searchable,\n" + " 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n" + " 'CLOB' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n" + " NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\n" + "from dual\n";
/*     */ 
/* 573 */     String str8 = "select\n 'NUMBER' as type_name, 2 as data_type, 38 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n 'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + str1 + str2 + str3 + "union select\n 'DATE' as type_name, 92 as data_type, 7 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'DATE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'TIMESTAMP' as type_name, 93 as data_type, 11 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'TIMESTAMP WITH TIME ZONE' as type_name, -101 as data_type, 13 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP WITH TIME ZONE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'TIMESTAMP WITH LOCAL TIME ZONE' as type_name, -102 as data_type, 11 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'TIMESTAMP WITH LOCAL TIME ZONE' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'INTERVALYM' as type_name, -103 as data_type, 5 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'INTERVALYM' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'INTERVALDS' as type_name, -104 as data_type, 4 as precision,\n NULL as literal_prefix, NULL as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 3 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'INTERVALDS' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + str4 + "union select\n 'LONG' as type_name, -1 as data_type, 2147483647 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'LONG' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'LONG RAW' as type_name, -4 as data_type, 2147483647 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 0 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'LONG RAW' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'NUMBER' as type_name, -7 as data_type, 1 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(1)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'NUMBER' as type_name, -6 as data_type, 3 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(3)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'NUMBER' as type_name, 5 as data_type, 5 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(5)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'NUMBER' as type_name, 4 as data_type, 10 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \n'(10)' as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'NUMBER' as type_name, -5 as data_type, 38 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'NUMBER' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'FLOAT' as type_name, 6 as data_type, 63 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'FLOAT' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select 'REAL' as type_name, 7 as data_type, 63 as precision,\nNULL as literal_prefix, NULL as literal_suffix, \nNULL as create_params, 1 as nullable, 0 as case_sensitive, 3 as searchable,\n0 as unsigned_attribute, 1 as fixed_prec_scale, 0 as auto_increment,\n'REAL' as local_type_name, -84 as minimum_scale, 127 as maximum_scale,\nNULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + (i >= 8100 ? str6 + str7 + "union select\n 'REF' as type_name, 2006 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'REF' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'ARRAY' as type_name, 2003 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'ARRAY' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" + "union select\n 'STRUCT' as type_name, 2002 as data_type, 0 as precision,\n '''' as literal_prefix, '''' as literal_suffix, NULL as create_params,\n 1 as nullable, 1 as case_sensitive, 0 as searchable,\n 0 as unsigned_attribute, 0 as fixed_prec_scale, 0 as auto_increment,\n 'STRUCT' as local_type_name, 0 as minimum_scale, 0 as maximum_scale,\n NULL as sql_data_type, NULL as sql_datetime_sub, 10 as num_prec_radix\nfrom dual\n" : "") + "order by data_type\n";
/*     */ 
/* 591 */     OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str8);
/*     */ 
/* 594 */     localOracleResultSet.closeStatementOnClose();
/*     */ 
/* 596 */     return localOracleResultSet;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleDatabaseMetaData
 * JD-Core Version:    0.6.0
 */