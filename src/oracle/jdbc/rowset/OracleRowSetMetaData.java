/*      */ package oracle.jdbc.rowset;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import javax.sql.RowSetMetaData;
/*      */ 
/*      */ public class OracleRowSetMetaData
/*      */   implements RowSetMetaData, Serializable
/*      */ {
/*      */   private int columnCount;
/*      */   private int[] nullable;
/*      */   private int[] columnDisplaySize;
/*      */   private int[] precision;
/*      */   private int[] scale;
/*      */   private int[] columnType;
/*      */   private boolean[] searchable;
/*      */   private boolean[] caseSensitive;
/*      */   private boolean[] readOnly;
/*      */   private boolean[] writable;
/*      */   private boolean[] definatelyWritable;
/*      */   private boolean[] currency;
/*      */   private boolean[] autoIncrement;
/*      */   private boolean[] signed;
/*      */   private String[] columnLabel;
/*      */   private String[] schemaName;
/*      */   private String[] columnName;
/*      */   private String[] tableName;
/*      */   private String[] columnTypeName;
/*      */   private String[] catalogName;
/*      */   private String[] columnClassName;
/*      */ 
/*      */   OracleRowSetMetaData(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  166 */     this.columnCount = paramInt;
/*  167 */     this.searchable = new boolean[this.columnCount];
/*  168 */     this.caseSensitive = new boolean[this.columnCount];
/*  169 */     this.readOnly = new boolean[this.columnCount];
/*  170 */     this.nullable = new int[this.columnCount];
/*  171 */     this.signed = new boolean[this.columnCount];
/*  172 */     this.columnDisplaySize = new int[this.columnCount];
/*  173 */     this.columnType = new int[this.columnCount];
/*  174 */     this.columnLabel = new String[this.columnCount];
/*  175 */     this.columnName = new String[this.columnCount];
/*  176 */     this.schemaName = new String[this.columnCount];
/*  177 */     this.precision = new int[this.columnCount];
/*  178 */     this.scale = new int[this.columnCount];
/*  179 */     this.tableName = new String[this.columnCount];
/*  180 */     this.columnTypeName = new String[this.columnCount];
/*  181 */     this.writable = new boolean[this.columnCount];
/*  182 */     this.definatelyWritable = new boolean[this.columnCount];
/*  183 */     this.currency = new boolean[this.columnCount];
/*  184 */     this.autoIncrement = new boolean[this.columnCount];
/*  185 */     this.catalogName = new String[this.columnCount];
/*  186 */     this.columnClassName = new String[this.columnCount];
/*      */ 
/*  188 */     for (int i = 0; i < this.columnCount; i++)
/*      */     {
/*  190 */       this.searchable[i] = false;
/*  191 */       this.caseSensitive[i] = false;
/*  192 */       this.readOnly[i] = false;
/*  193 */       this.nullable[i] = 1;
/*  194 */       this.signed[i] = false;
/*  195 */       this.columnDisplaySize[i] = 0;
/*  196 */       this.columnType[i] = 0;
/*  197 */       this.columnLabel[i] = "";
/*  198 */       this.columnName[i] = "";
/*  199 */       this.schemaName[i] = "";
/*  200 */       this.precision[i] = 0;
/*  201 */       this.scale[i] = 0;
/*  202 */       this.tableName[i] = "";
/*  203 */       this.columnTypeName[i] = "";
/*  204 */       this.writable[i] = false;
/*  205 */       this.definatelyWritable[i] = false;
/*  206 */       this.currency[i] = false;
/*  207 */       this.autoIncrement[i] = true;
/*  208 */       this.catalogName[i] = "";
/*  209 */       this.columnClassName[i] = "";
/*      */     }
/*      */   }
/*      */ 
/*      */   OracleRowSetMetaData(ResultSetMetaData paramResultSetMetaData)
/*      */     throws SQLException
/*      */   {
/*  236 */     this.columnCount = paramResultSetMetaData.getColumnCount();
/*  237 */     this.searchable = new boolean[this.columnCount];
/*  238 */     this.caseSensitive = new boolean[this.columnCount];
/*  239 */     this.readOnly = new boolean[this.columnCount];
/*  240 */     this.nullable = new int[this.columnCount];
/*  241 */     this.signed = new boolean[this.columnCount];
/*  242 */     this.columnDisplaySize = new int[this.columnCount];
/*  243 */     this.columnType = new int[this.columnCount];
/*  244 */     this.columnLabel = new String[this.columnCount];
/*  245 */     this.columnName = new String[this.columnCount];
/*  246 */     this.schemaName = new String[this.columnCount];
/*  247 */     this.precision = new int[this.columnCount];
/*  248 */     this.scale = new int[this.columnCount];
/*  249 */     this.tableName = new String[this.columnCount];
/*  250 */     this.columnTypeName = new String[this.columnCount];
/*  251 */     this.writable = new boolean[this.columnCount];
/*  252 */     this.definatelyWritable = new boolean[this.columnCount];
/*  253 */     this.currency = new boolean[this.columnCount];
/*  254 */     this.autoIncrement = new boolean[this.columnCount];
/*  255 */     this.catalogName = new String[this.columnCount];
/*  256 */     this.columnClassName = new String[this.columnCount];
/*      */ 
/*  258 */     for (int i = 0; i < this.columnCount; i++)
/*      */     {
/*  260 */       this.searchable[i] = paramResultSetMetaData.isSearchable(i + 1);
/*  261 */       this.caseSensitive[i] = paramResultSetMetaData.isCaseSensitive(i + 1);
/*  262 */       this.readOnly[i] = paramResultSetMetaData.isReadOnly(i + 1);
/*  263 */       this.nullable[i] = paramResultSetMetaData.isNullable(i + 1);
/*  264 */       this.signed[i] = paramResultSetMetaData.isSigned(i + 1);
/*  265 */       this.columnDisplaySize[i] = paramResultSetMetaData.getColumnDisplaySize(i + 1);
/*  266 */       this.columnType[i] = paramResultSetMetaData.getColumnType(i + 1);
/*  267 */       this.columnLabel[i] = paramResultSetMetaData.getColumnLabel(i + 1);
/*  268 */       this.columnName[i] = paramResultSetMetaData.getColumnName(i + 1);
/*  269 */       this.schemaName[i] = paramResultSetMetaData.getSchemaName(i + 1);
/*      */ 
/*  271 */       if ((this.columnType[i] == 2) || (this.columnType[i] == 2) || (this.columnType[i] == -5) || (this.columnType[i] == 3) || (this.columnType[i] == 8) || (this.columnType[i] == 6) || (this.columnType[i] == 4))
/*      */       {
/*  279 */         this.precision[i] = paramResultSetMetaData.getPrecision(i + 1);
/*  280 */         this.scale[i] = paramResultSetMetaData.getScale(i + 1);
/*      */       }
/*      */       else
/*      */       {
/*  284 */         this.precision[i] = 0;
/*  285 */         this.scale[i] = 0;
/*      */       }
/*      */ 
/*  288 */       this.tableName[i] = paramResultSetMetaData.getTableName(i + 1);
/*  289 */       this.columnTypeName[i] = paramResultSetMetaData.getColumnTypeName(i + 1);
/*  290 */       this.writable[i] = paramResultSetMetaData.isWritable(i + 1);
/*  291 */       this.definatelyWritable[i] = paramResultSetMetaData.isDefinitelyWritable(i + 1);
/*  292 */       this.currency[i] = paramResultSetMetaData.isCurrency(i + 1);
/*  293 */       this.autoIncrement[i] = paramResultSetMetaData.isAutoIncrement(i + 1);
/*  294 */       this.catalogName[i] = paramResultSetMetaData.getCatalogName(i + 1);
/*  295 */       this.columnClassName[i] = paramResultSetMetaData.getColumnClassName(i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateColumnIndex(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  317 */     if ((paramInt < 1) || (paramInt > this.columnCount))
/*  318 */       throw new SQLException("Invalid column index : " + paramInt);
/*      */   }
/*      */ 
/*      */   public int getColumnCount()
/*      */     throws SQLException
/*      */   {
/*  349 */     return this.columnCount;
/*      */   }
/*      */ 
/*      */   public boolean isAutoIncrement(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  377 */     validateColumnIndex(paramInt);
/*  378 */     return this.autoIncrement[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isCaseSensitive(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  406 */     validateColumnIndex(paramInt);
/*  407 */     return this.caseSensitive[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isSearchable(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  435 */     validateColumnIndex(paramInt);
/*  436 */     return this.searchable[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isCurrency(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  464 */     validateColumnIndex(paramInt);
/*  465 */     return this.currency[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public int isNullable(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  494 */     validateColumnIndex(paramInt);
/*  495 */     return this.nullable[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isSigned(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  522 */     validateColumnIndex(paramInt);
/*  523 */     return this.signed[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public int getColumnDisplaySize(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  552 */     validateColumnIndex(paramInt);
/*  553 */     return this.columnDisplaySize[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getColumnLabel(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  582 */     validateColumnIndex(paramInt);
/*  583 */     return this.columnLabel[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getColumnName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  611 */     validateColumnIndex(paramInt);
/*  612 */     return this.columnName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getSchemaName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  640 */     validateColumnIndex(paramInt);
/*  641 */     return this.schemaName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public int getPrecision(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  669 */     validateColumnIndex(paramInt);
/*  670 */     return this.precision[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public int getScale(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  698 */     validateColumnIndex(paramInt);
/*  699 */     return this.scale[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getTableName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  727 */     validateColumnIndex(paramInt);
/*  728 */     return this.tableName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getCatalogName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  756 */     validateColumnIndex(paramInt);
/*  757 */     return this.catalogName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public int getColumnType(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  786 */     validateColumnIndex(paramInt);
/*  787 */     return this.columnType[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getColumnTypeName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  816 */     validateColumnIndex(paramInt);
/*  817 */     return this.columnTypeName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isReadOnly(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  845 */     validateColumnIndex(paramInt);
/*  846 */     return this.readOnly[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isWritable(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  874 */     validateColumnIndex(paramInt);
/*  875 */     return this.writable[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public boolean isDefinitelyWritable(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  903 */     validateColumnIndex(paramInt);
/*  904 */     return this.definatelyWritable[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getColumnClassName(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  942 */     validateColumnIndex(paramInt);
/*  943 */     return this.columnClassName[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public void setAutoIncrement(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  968 */     validateColumnIndex(paramInt);
/*  969 */     this.autoIncrement[(paramInt - 1)] = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setCaseSensitive(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  990 */     validateColumnIndex(paramInt);
/*  991 */     this.caseSensitive[(paramInt - 1)] = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setCatalogName(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1012 */     validateColumnIndex(paramInt);
/* 1013 */     this.catalogName[(paramInt - 1)] = paramString;
/*      */   }
/*      */ 
/*      */   public void setColumnCount(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1034 */     this.columnCount = paramInt;
/*      */   }
/*      */ 
/*      */   public void setColumnDisplaySize(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1055 */     validateColumnIndex(paramInt1);
/* 1056 */     this.columnDisplaySize[(paramInt1 - 1)] = paramInt2;
/*      */   }
/*      */ 
/*      */   public void setColumnLabel(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1077 */     validateColumnIndex(paramInt);
/* 1078 */     this.columnLabel[(paramInt - 1)] = paramString;
/*      */   }
/*      */ 
/*      */   public void setColumnName(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1099 */     validateColumnIndex(paramInt);
/* 1100 */     this.columnName[(paramInt - 1)] = paramString;
/*      */   }
/*      */ 
/*      */   public void setColumnType(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1121 */     validateColumnIndex(paramInt1);
/* 1122 */     this.columnType[(paramInt1 - 1)] = paramInt2;
/*      */   }
/*      */ 
/*      */   public void setColumnTypeName(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1143 */     validateColumnIndex(paramInt);
/* 1144 */     this.columnTypeName[(paramInt - 1)] = paramString;
/*      */   }
/*      */ 
/*      */   public void setCurrency(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1165 */     validateColumnIndex(paramInt);
/* 1166 */     this.currency[(paramInt - 1)] = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setNullable(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1187 */     validateColumnIndex(paramInt1);
/* 1188 */     this.nullable[(paramInt1 - 1)] = paramInt2;
/*      */   }
/*      */ 
/*      */   public void setPrecision(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1209 */     validateColumnIndex(paramInt1);
/* 1210 */     this.precision[(paramInt1 - 1)] = paramInt2;
/*      */   }
/*      */ 
/*      */   public void setScale(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1231 */     validateColumnIndex(paramInt1);
/* 1232 */     this.scale[(paramInt1 - 1)] = paramInt2;
/*      */   }
/*      */ 
/*      */   public void setSchemaName(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1253 */     validateColumnIndex(paramInt);
/* 1254 */     this.schemaName[(paramInt - 1)] = paramString;
/*      */   }
/*      */ 
/*      */   public void setSearchable(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1275 */     validateColumnIndex(paramInt);
/* 1276 */     this.searchable[(paramInt - 1)] = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setSigned(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1297 */     validateColumnIndex(paramInt);
/* 1298 */     this.signed[(paramInt - 1)] = paramBoolean;
/*      */   }
/*      */ 
/*      */   public void setTableName(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1319 */     validateColumnIndex(paramInt);
/* 1320 */     this.tableName[(paramInt - 1)] = paramString;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleRowSetMetaData
 * JD-Core Version:    0.6.0
 */