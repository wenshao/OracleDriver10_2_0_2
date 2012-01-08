/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ 
/*      */ public class OracleSql
/*      */ {
/*      */   static final int UNINITIALIZED = -1;
/*   39 */   static final String[] EMPTY_LIST = new String[0];
/*      */   DBConversion conversion;
/*      */   String originalSql;
/*      */   String parameterSql;
/*      */   String utickSql;
/*      */   String processedSql;
/*      */   String rowidSql;
/*      */   String actualSql;
/*      */   byte[] sqlBytes;
/*   52 */   byte sqlKind = -1;
/*   53 */   int parameterCount = -1;
/*   54 */   boolean currentConvertNcharLiterals = true;
/*   55 */   boolean currentProcessEscapes = true;
/*   56 */   boolean includeRowid = false;
/*   57 */   String[] parameterList = EMPTY_LIST;
/*   58 */   char[] currentParameter = null;
/*   59 */   boolean isV8Compatible = false;
/*      */ 
/*   61 */   int bindParameterCount = -1;
/*   62 */   String[] bindParameterList = null;
/*   63 */   int cachedBindParameterCount = -1;
/*   64 */   String[] cachedBindParameterList = null;
/*      */   String cachedParameterSql;
/*      */   String cachedUtickSql;
/*      */   String cachedProcessedSql;
/*      */   String cachedRowidSql;
/*      */   String cachedActualSql;
/*      */   byte[] cachedSqlBytes;
/*   71 */   int selectEndIndex = -1;
/*   72 */   int orderByStartIndex = -1;
/*   73 */   int orderByEndIndex = -1;
/*   74 */   int whereStartIndex = -1;
/*   75 */   int whereEndIndex = -1;
/*   76 */   int forUpdateStartIndex = -1;
/*   77 */   int forUpdateEndIndex = -1;
/*      */ 
/*   79 */   final int[] ncharLiteralLocation = new int[513];
/*   80 */   int lastNcharLiteralLocation = -1;
/*      */   static final String paramPrefix = "rowid";
/*   83 */   int paramSuffix = 0;
/*      */ 
/*  384 */   StringBuffer stringBufferForScrollableStatement = null;
/*      */   private static final int cMax = 127;
/*  712 */   private static final int[][] TRANSITION = OracleSqlReadOnly.TRANSITION;
/*  713 */   private static final int[][] ACTION = OracleSqlReadOnly.ACTION;
/*      */   private static final int NO_ACTION = 0;
/*      */   private static final int DML_ACTION = 1;
/*      */   private static final int PLSQL_ACTION = 2;
/*      */   private static final int CALL_ACTION = 3;
/*      */   private static final int SELECT_ACTION = 4;
/*      */   private static final int ORDER_ACTION = 7;
/*      */   private static final int ORDER_BY_ACTION = 8;
/*      */   private static final int WHERE_ACTION = 6;
/*      */   private static final int FOR_ACTION = 9;
/*      */   private static final int FOR_UPDATE_ACTION = 10;
/*      */   private static final int OTHER_ACTION = 5;
/*      */   private static final int QUESTION_ACTION = 11;
/*      */   private static final int PARAMETER_ACTION = 12;
/*      */   private static final int END_PARAMETER_ACTION = 13;
/*      */   private static final int START_NCHAR_LITERAL_ACTION = 14;
/*      */   private static final int END_NCHAR_LITERAL_ACTION = 15;
/*      */   int current_argument;
/*      */   int i;
/*      */   int length;
/*      */   char c;
/*      */   boolean first;
/*      */   boolean in_string;
/*      */   String odbc_sql;
/*      */   StringBuffer oracle_sql;
/*      */   StringBuffer token_buffer;
/*  956 */   boolean isLocate = false;
/*      */ 
/* 1662 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*      */ 
/*      */   OracleSql(DBConversion paramDBConversion)
/*      */   {
/*   93 */     this.conversion = paramDBConversion;
/*      */   }
/*      */ 
/*      */   void initialize(String paramString)
/*      */     throws SQLException
/*      */   {
/*  111 */     if ((paramString == null) || (paramString == "")) {
/*  112 */       DatabaseError.throwSqlException(104);
/*      */     }
/*  114 */     this.originalSql = paramString;
/*  115 */     this.utickSql = null;
/*  116 */     this.processedSql = null;
/*  117 */     this.rowidSql = null;
/*  118 */     this.actualSql = null;
/*  119 */     this.sqlBytes = null;
/*  120 */     this.sqlKind = -1;
/*  121 */     this.parameterCount = -1;
/*  122 */     this.includeRowid = false;
/*      */ 
/*  124 */     this.parameterSql = this.originalSql;
/*  125 */     this.bindParameterCount = -1;
/*  126 */     this.bindParameterList = null;
/*  127 */     this.cachedBindParameterCount = -1;
/*  128 */     this.cachedBindParameterList = null;
/*  129 */     this.cachedParameterSql = null;
/*  130 */     this.cachedActualSql = null;
/*  131 */     this.cachedProcessedSql = null;
/*  132 */     this.cachedRowidSql = null;
/*  133 */     this.cachedSqlBytes = null;
/*  134 */     this.selectEndIndex = -1;
/*  135 */     this.orderByStartIndex = -1;
/*  136 */     this.orderByEndIndex = -1;
/*  137 */     this.whereStartIndex = -1;
/*  138 */     this.whereEndIndex = -1;
/*  139 */     this.forUpdateStartIndex = -1;
/*  140 */     this.forUpdateEndIndex = -1;
/*      */   }
/*      */ 
/*      */   String getOriginalSql()
/*      */   {
/*  155 */     return this.originalSql;
/*      */   }
/*      */ 
/*      */   boolean setNamedParameters(int paramInt, String[] paramArrayOfString)
/*      */     throws SQLException
/*      */   {
/*  165 */     int j = 0;
/*      */ 
/*  167 */     if (paramInt == 0)
/*      */     {
/*  169 */       this.bindParameterCount = -1;
/*  170 */       j = this.bindParameterCount != this.cachedBindParameterCount ? 1 : 0;
/*      */     }
/*      */     else
/*      */     {
/*  174 */       this.bindParameterCount = paramInt;
/*  175 */       this.bindParameterList = paramArrayOfString;
/*  176 */       j = this.bindParameterCount != this.cachedBindParameterCount ? 1 : 0;
/*      */ 
/*  178 */       if (j == 0) {
/*  179 */         for (int k = 0; k < paramInt; k++) {
/*  180 */           if (this.bindParameterList[k] == this.cachedBindParameterList[k])
/*      */             continue;
/*  182 */           j = 1;
/*      */ 
/*  184 */           break;
/*      */         }
/*      */       }
/*  187 */       if (j != 0)
/*      */       {
/*  189 */         if (this.bindParameterCount != getParameterCount()) {
/*  190 */           throw new SQLException("Incorrectly set or registered parameters.");
/*      */         }
/*  192 */         char[] arrayOfChar = this.originalSql.toCharArray();
/*  193 */         StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/*  195 */         int m = 0;
/*      */ 
/*  197 */         for (int n = 0; n < arrayOfChar.length; n++)
/*      */         {
/*  199 */           if (arrayOfChar[n] != '?')
/*      */           {
/*  201 */             localStringBuffer.append(arrayOfChar[n]);
/*      */           }
/*      */           else
/*      */           {
/*  205 */             localStringBuffer.append(this.bindParameterList[(m++)]);
/*  206 */             localStringBuffer.append("=>?");
/*      */           }
/*      */         }
/*      */ 
/*  210 */         if (m != this.bindParameterCount) {
/*  211 */           throw new SQLException("Incorrectly set or registered parameters.");
/*      */         }
/*  213 */         this.parameterSql = new String(localStringBuffer);
/*  214 */         this.actualSql = null;
/*  215 */         this.utickSql = null;
/*  216 */         this.processedSql = null;
/*  217 */         this.rowidSql = null;
/*  218 */         this.sqlBytes = null;
/*      */       }
/*      */       else
/*      */       {
/*  222 */         this.parameterSql = this.cachedParameterSql;
/*  223 */         this.actualSql = this.cachedActualSql;
/*  224 */         this.utickSql = this.cachedUtickSql;
/*  225 */         this.processedSql = this.cachedProcessedSql;
/*  226 */         this.rowidSql = this.cachedRowidSql;
/*  227 */         this.sqlBytes = this.cachedSqlBytes;
/*      */       }
/*      */     }
/*      */ 
/*  231 */     this.cachedBindParameterList = null;
/*  232 */     this.cachedParameterSql = null;
/*  233 */     this.cachedActualSql = null;
/*  234 */     this.cachedUtickSql = null;
/*  235 */     this.cachedProcessedSql = null;
/*  236 */     this.cachedRowidSql = null;
/*  237 */     this.cachedSqlBytes = null;
/*      */ 
/*  239 */     return j;
/*      */   }
/*      */ 
/*      */   void resetNamedParameters()
/*      */   {
/*  247 */     this.cachedBindParameterCount = this.bindParameterCount;
/*      */ 
/*  249 */     if (this.bindParameterCount != -1)
/*      */     {
/*  251 */       if ((this.cachedBindParameterList == null) || (this.cachedBindParameterList == this.bindParameterList) || (this.cachedBindParameterList.length < this.bindParameterCount))
/*      */       {
/*  254 */         this.cachedBindParameterList = new String[this.bindParameterCount];
/*      */       }
/*  256 */       System.arraycopy(this.bindParameterList, 0, this.cachedBindParameterList, 0, this.bindParameterCount);
/*      */ 
/*  259 */       this.cachedParameterSql = this.parameterSql;
/*  260 */       this.cachedActualSql = this.actualSql;
/*  261 */       this.cachedUtickSql = this.utickSql;
/*  262 */       this.cachedProcessedSql = this.processedSql;
/*  263 */       this.cachedRowidSql = this.rowidSql;
/*  264 */       this.cachedSqlBytes = this.sqlBytes;
/*      */ 
/*  266 */       this.bindParameterCount = -1;
/*  267 */       this.bindParameterList = null;
/*  268 */       this.parameterSql = this.originalSql;
/*  269 */       this.actualSql = null;
/*  270 */       this.utickSql = null;
/*  271 */       this.processedSql = null;
/*  272 */       this.rowidSql = null;
/*  273 */       this.sqlBytes = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   String getSql(boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  290 */     if (this.sqlKind == -1) computeBasicInfo(this.parameterSql);
/*  291 */     if ((paramBoolean1 != this.currentProcessEscapes) || (paramBoolean2 != this.currentConvertNcharLiterals))
/*      */     {
/*  297 */       if (paramBoolean2 != this.currentConvertNcharLiterals) this.utickSql = null;
/*  298 */       this.processedSql = null;
/*  299 */       this.rowidSql = null;
/*  300 */       this.actualSql = null;
/*  301 */       this.sqlBytes = null;
/*      */     }
/*      */ 
/*  304 */     this.currentConvertNcharLiterals = paramBoolean2;
/*  305 */     this.currentProcessEscapes = paramBoolean1;
/*      */ 
/*  307 */     if (this.actualSql == null)
/*      */     {
/*  309 */       if (this.utickSql == null)
/*  310 */         this.utickSql = (this.currentConvertNcharLiterals ? convertNcharLiterals(this.parameterSql) : this.parameterSql);
/*  311 */       if (this.processedSql == null)
/*  312 */         this.processedSql = (this.currentProcessEscapes ? parse(this.utickSql) : this.utickSql);
/*  313 */       if (this.rowidSql == null)
/*  314 */         this.rowidSql = (this.includeRowid ? addRowid(this.processedSql) : this.processedSql);
/*  315 */       this.actualSql = this.rowidSql;
/*      */     }
/*      */ 
/*  319 */     return this.actualSql;
/*      */   }
/*      */ 
/*      */   String getRevisedSql()
/*      */     throws SQLException
/*      */   {
/*  326 */     String str = this.originalSql;
/*      */ 
/*  328 */     if (this.sqlKind == -1) computeBasicInfo(this.parameterSql);
/*      */ 
/*  330 */     str = removeForUpdate(str);
/*      */ 
/*  332 */     return addRowid(str);
/*      */   }
/*      */ 
/*      */   String removeForUpdate(String paramString)
/*      */     throws SQLException
/*      */   {
/*  345 */     if ((this.orderByStartIndex != -1) && ((this.forUpdateStartIndex == -1) || (this.forUpdateStartIndex > this.orderByStartIndex)))
/*      */     {
/*  349 */       paramString = paramString.substring(0, this.orderByStartIndex);
/*      */     }
/*  351 */     else if (this.forUpdateStartIndex != -1)
/*      */     {
/*  353 */       paramString = paramString.substring(0, this.forUpdateStartIndex);
/*      */     }
/*      */ 
/*  356 */     return paramString;
/*      */   }
/*      */ 
/*      */   void appendForUpdate(StringBuffer paramStringBuffer)
/*      */     throws SQLException
/*      */   {
/*  372 */     if ((this.orderByStartIndex != -1) && ((this.forUpdateStartIndex == -1) || (this.forUpdateStartIndex > this.orderByStartIndex)))
/*      */     {
/*  376 */       paramStringBuffer.append(this.originalSql.substring(this.orderByStartIndex));
/*      */     }
/*  378 */     else if (this.forUpdateStartIndex != -1)
/*      */     {
/*  380 */       paramStringBuffer.append(this.originalSql.substring(this.forUpdateStartIndex));
/*      */     }
/*      */   }
/*      */ 
/*      */   String getInsertSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet)
/*      */     throws SQLException
/*      */   {
/*  396 */     String str = getOriginalSql();
/*      */ 
/*  398 */     if (this.stringBufferForScrollableStatement == null)
/*  399 */       this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
/*      */     else {
/*  401 */       this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
/*      */     }
/*  403 */     this.stringBufferForScrollableStatement.append("insert into (");
/*      */ 
/*  405 */     this.stringBufferForScrollableStatement.append(removeForUpdate(str));
/*  406 */     this.stringBufferForScrollableStatement.append(") values ( ");
/*      */ 
/*  408 */     int j = 1;
/*  409 */     while (j < paramUpdatableResultSet.getColumnCount())
/*      */     {
/*  412 */       if (j != 1) {
/*  413 */         this.stringBufferForScrollableStatement.append(", ");
/*      */       }
/*  415 */       this.stringBufferForScrollableStatement.append(":" + generateParameterName());
/*      */ 
/*  410 */       j++;
/*      */     }
/*      */ 
/*  418 */     this.stringBufferForScrollableStatement.append(")");
/*      */ 
/*  420 */     return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
/*      */   }
/*      */ 
/*      */   String getRefetchSqlForScrollableResultSet(ScrollableResultSet paramScrollableResultSet, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  438 */     String str = getRevisedSql();
/*      */ 
/*  440 */     if (this.stringBufferForScrollableStatement == null)
/*  441 */       this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
/*      */     else {
/*  443 */       this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
/*      */     }
/*      */ 
/*  446 */     this.stringBufferForScrollableStatement.append(str);
/*      */ 
/*  448 */     this.stringBufferForScrollableStatement.append(" WHERE ( ROWID = :" + generateParameterName());
/*      */ 
/*  453 */     for (int j = 0; j < paramInt - 1; j++) {
/*  454 */       this.stringBufferForScrollableStatement.append(" OR ROWID = :" + generateParameterName());
/*      */     }
/*  456 */     this.stringBufferForScrollableStatement.append(" ) ");
/*      */ 
/*  459 */     appendForUpdate(this.stringBufferForScrollableStatement);
/*      */ 
/*  461 */     return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
/*      */   }
/*      */ 
/*      */   String getUpdateSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet, int paramInt, Object[] paramArrayOfObject, int[] paramArrayOfInt)
/*      */     throws SQLException
/*      */   {
/*  477 */     String str = getRevisedSql();
/*      */ 
/*  479 */     if (this.stringBufferForScrollableStatement == null)
/*  480 */       this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
/*      */     else {
/*  482 */       this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
/*      */     }
/*      */ 
/*  486 */     this.stringBufferForScrollableStatement.append("update (");
/*  487 */     this.stringBufferForScrollableStatement.append(str);
/*  488 */     this.stringBufferForScrollableStatement.append(") set ");
/*      */ 
/*  491 */     if (paramArrayOfObject != null)
/*      */     {
/*  493 */       for (int j = 0; j < paramInt; j++)
/*      */       {
/*  495 */         if (j > 0) {
/*  496 */           this.stringBufferForScrollableStatement.append(", ");
/*      */         }
/*  498 */         this.stringBufferForScrollableStatement.append(paramUpdatableResultSet.getInternalMetadata().getColumnName(paramArrayOfInt[j] + 1));
/*      */ 
/*  501 */         this.stringBufferForScrollableStatement.append(" = :" + generateParameterName());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  506 */     this.stringBufferForScrollableStatement.append(" WHERE ");
/*  507 */     this.stringBufferForScrollableStatement.append(" ROWID = :" + generateParameterName());
/*      */ 
/*  511 */     return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
/*      */   }
/*      */ 
/*      */   String getDeleteSqlForUpdatableResultSet(UpdatableResultSet paramUpdatableResultSet)
/*      */     throws SQLException
/*      */   {
/*  525 */     String str = getRevisedSql();
/*      */ 
/*  527 */     if (this.stringBufferForScrollableStatement == null)
/*  528 */       this.stringBufferForScrollableStatement = new StringBuffer(str.length() + 100);
/*      */     else {
/*  530 */       this.stringBufferForScrollableStatement.delete(0, this.stringBufferForScrollableStatement.length());
/*      */     }
/*      */ 
/*  534 */     this.stringBufferForScrollableStatement.append("delete from (");
/*  535 */     this.stringBufferForScrollableStatement.append(str);
/*  536 */     this.stringBufferForScrollableStatement.append(") where ");
/*      */ 
/*  538 */     this.stringBufferForScrollableStatement.append(" ROWID = :" + generateParameterName());
/*      */ 
/*  542 */     return this.stringBufferForScrollableStatement.substring(0, this.stringBufferForScrollableStatement.length());
/*      */   }
/*      */ 
/*      */   byte[] getSqlBytes(boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  555 */     if ((this.sqlBytes == null) || (paramBoolean1 != this.currentProcessEscapes))
/*      */     {
/*  557 */       this.sqlBytes = this.conversion.StringToCharBytes(getSql(paramBoolean1, paramBoolean2));
/*      */     }
/*      */ 
/*  561 */     return this.sqlBytes;
/*      */   }
/*      */ 
/*      */   byte getSqlKind()
/*      */     throws SQLException
/*      */   {
/*  573 */     if (this.sqlKind == -1) {
/*  574 */       computeBasicInfo(this.parameterSql);
/*      */     }
/*  576 */     return this.sqlKind;
/*      */   }
/*      */ 
/*      */   int getParameterCount()
/*      */     throws SQLException
/*      */   {
/*  588 */     if (this.parameterCount == -1)
/*      */     {
/*  590 */       computeBasicInfo(this.parameterSql);
/*      */     }
/*      */ 
/*  593 */     return this.parameterCount;
/*      */   }
/*      */ 
/*      */   String[] getParameterList()
/*      */     throws SQLException
/*      */   {
/*  608 */     if (this.parameterCount == -1)
/*      */     {
/*  610 */       computeBasicInfo(this.parameterSql);
/*      */     }
/*      */ 
/*  613 */     return this.parameterList;
/*      */   }
/*      */ 
/*      */   void setIncludeRowid(boolean paramBoolean)
/*      */   {
/*  626 */     if (paramBoolean != this.includeRowid)
/*      */     {
/*  628 */       this.includeRowid = paramBoolean;
/*  629 */       this.rowidSql = null;
/*  630 */       this.actualSql = null;
/*  631 */       this.sqlBytes = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  640 */     return this.parameterSql == null ? "null" : this.parameterSql;
/*      */   }
/*      */ 
/*      */   private String hexUnicode(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  656 */     String str = Integer.toHexString(paramInt);
/*  657 */     switch (str.length()) {
/*      */     case 0:
/*  659 */       return "\\0000";
/*      */     case 1:
/*  660 */       return "\\000" + str;
/*      */     case 2:
/*  661 */       return "\\00" + str;
/*      */     case 3:
/*  662 */       return "\\0" + str;
/*      */     case 4:
/*  663 */       return "\\" + str;
/*      */     }
/*  665 */     DatabaseError.throwSqlException(89, "Unexpected case in OracleSql.hexUnicode: " + paramInt);
/*      */ 
/*  667 */     return "never happen";
/*      */   }
/*      */ 
/*      */   String convertNcharLiterals(String paramString)
/*      */     throws SQLException
/*      */   {
/*  687 */     if (this.lastNcharLiteralLocation <= 2) return paramString;
/*  688 */     String str = "";
/*  689 */     int j = 0;
/*      */     while (true)
/*      */     {
/*  692 */       int k = this.ncharLiteralLocation[(j++)];
/*  693 */       int m = this.ncharLiteralLocation[(j++)];
/*      */ 
/*  695 */       str = str + paramString.substring(k, m);
/*  696 */       if (j >= this.lastNcharLiteralLocation) break;
/*  697 */       k = this.ncharLiteralLocation[j];
/*  698 */       str = str + "u'";
/*      */ 
/*  700 */       for (int n = m + 2; n < k; n++)
/*      */       {
/*  702 */         char c1 = paramString.charAt(n);
/*  703 */         if (c1 == '\\') str = str + "\\\\";
/*  704 */         else if (c1 < '') str = str + c1; else
/*  705 */           str = str + hexUnicode(c1);
/*      */       }
/*      */     }
/*  708 */     return str;
/*      */   }
/*      */ 
/*      */   void computeBasicInfo(String paramString)
/*      */     throws SQLException
/*      */   {
/*  764 */     this.parameterCount = 0;
/*      */ 
/*  766 */     this.lastNcharLiteralLocation = 0;
/*  767 */     this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = 0;
/*      */ 
/*  769 */     int j = 0;
/*      */ 
/*  771 */     int k = 0;
/*  772 */     int m = paramString.length();
/*  773 */     int n = -1;
/*  774 */     int i1 = -1;
/*  775 */     int i2 = m + 1;
/*      */ 
/*  778 */     for (int i3 = 0; i3 < i2; i3++)
/*      */     {
/*  780 */       char c1 = i3 < m ? paramString.charAt(i3) : ' ';
/*  781 */       char c2 = c1;
/*      */ 
/*  783 */       if (c1 > '')
/*      */       {
/*  789 */         if (Character.isLetterOrDigit(c1))
/*  790 */           c2 = 'X';
/*      */         else {
/*  792 */           c2 = ' ';
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  797 */       switch (ACTION[k][c2])
/*      */       {
/*      */       case 0:
/*  801 */         break;
/*      */       case 1:
/*  804 */         this.sqlKind = 2;
/*      */ 
/*  806 */         break;
/*      */       case 2:
/*  809 */         this.sqlKind = 1;
/*      */ 
/*  811 */         break;
/*      */       case 3:
/*  814 */         this.sqlKind = 4;
/*      */ 
/*  816 */         break;
/*      */       case 4:
/*  819 */         this.sqlKind = 0;
/*  820 */         this.selectEndIndex = i3;
/*      */ 
/*  822 */         break;
/*      */       case 5:
/*  825 */         this.sqlKind = 3;
/*      */ 
/*  827 */         break;
/*      */       case 6:
/*  830 */         this.whereStartIndex = (i3 - 5);
/*  831 */         this.whereEndIndex = i3;
/*      */ 
/*  833 */         break;
/*      */       case 7:
/*  836 */         n = i3 - 5;
/*      */ 
/*  838 */         break;
/*      */       case 8:
/*  841 */         this.orderByStartIndex = n;
/*  842 */         this.orderByEndIndex = i3;
/*      */ 
/*  844 */         break;
/*      */       case 9:
/*  847 */         i1 = i3 - 3;
/*      */ 
/*  849 */         break;
/*      */       case 10:
/*  852 */         this.forUpdateStartIndex = i1;
/*  853 */         this.forUpdateEndIndex = i3;
/*      */ 
/*  855 */         break;
/*      */       case 11:
/*  858 */         this.parameterCount += 1;
/*      */ 
/*  860 */         break;
/*      */       case 12:
/*  863 */         if (this.currentParameter == null) {
/*  864 */           this.currentParameter = new char[32];
/*      */         }
/*  866 */         if (j >= this.currentParameter.length) {
/*  867 */           DatabaseError.throwSqlException(134, new String(this.currentParameter));
/*      */         }
/*      */ 
/*  870 */         this.currentParameter[(j++)] = c1;
/*      */ 
/*  872 */         break;
/*      */       case 13:
/*  875 */         if (j <= 0)
/*      */           break;
/*  877 */         if (this.parameterList == EMPTY_LIST)
/*      */         {
/*  879 */           this.parameterList = new String[8];
/*      */         }
/*  881 */         else if (this.parameterList.length <= this.parameterCount)
/*      */         {
/*  883 */           String[] arrayOfString = new String[this.parameterList.length * 4];
/*      */ 
/*  885 */           System.arraycopy(this.parameterList, 0, arrayOfString, 0, this.parameterList.length);
/*      */ 
/*  888 */           this.parameterList = arrayOfString;
/*      */         }
/*      */ 
/*  891 */         this.parameterList[this.parameterCount] = new String(this.currentParameter, 0, j).intern();
/*      */ 
/*  893 */         j = 0;
/*  894 */         this.parameterCount += 1; break;
/*      */       case 14:
/*  900 */         this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = (i3 - 1);
/*      */ 
/*  902 */         break;
/*      */       case 15:
/*  905 */         this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = (i3 + 1);
/*      */       }
/*      */ 
/*  911 */       k = TRANSITION[k][c2];
/*      */     }
/*      */ 
/*  914 */     this.ncharLiteralLocation[(this.lastNcharLiteralLocation++)] = m;
/*  915 */     this.ncharLiteralLocation[this.lastNcharLiteralLocation] = m;
/*      */   }
/*      */ 
/*      */   private String addRowid(String paramString)
/*      */     throws SQLException
/*      */   {
/*  927 */     if (this.selectEndIndex == -1) {
/*  928 */       DatabaseError.throwSqlException(88);
/*      */     }
/*  930 */     String str = "select rowid," + paramString.substring(this.selectEndIndex);
/*      */ 
/*  934 */     return str;
/*      */   }
/*      */ 
/*      */   String parse(String paramString)
/*      */     throws SQLException
/*      */   {
/*  965 */     this.current_argument = 1;
/*  966 */     this.i = 0;
/*  967 */     this.first = true;
/*  968 */     this.in_string = false;
/*  969 */     this.odbc_sql = paramString;
/*  970 */     this.length = this.odbc_sql.length();
/*      */ 
/*  972 */     if (this.oracle_sql == null)
/*      */     {
/*  974 */       this.oracle_sql = new StringBuffer(this.length);
/*  975 */       this.token_buffer = new StringBuffer(32);
/*      */     }
/*      */     else
/*      */     {
/*  979 */       this.oracle_sql.ensureCapacity(this.length);
/*      */     }
/*      */ 
/*  982 */     this.oracle_sql.delete(0, this.oracle_sql.length());
/*  983 */     skipSpace();
/*  984 */     handleODBC();
/*      */ 
/*  986 */     if (this.i < this.length)
/*      */     {
/*  988 */       Integer localInteger = new Integer(this.i);
/*      */ 
/*  990 */       DatabaseError.throwSqlException(33, localInteger);
/*      */     }
/*      */ 
/*  993 */     return this.oracle_sql.substring(0, this.oracle_sql.length());
/*      */   }
/*      */ 
/*      */   void handleODBC() throws SQLException
/*      */   {
/*  998 */     StringBuffer localStringBuffer1 = null;
/*  999 */     StringBuffer localStringBuffer2 = null;
/* 1000 */     int j = 0;
/*      */ 
/* 1002 */     while (this.i < this.length)
/*      */     {
/* 1004 */       this.c = this.odbc_sql.charAt(this.i);
/*      */ 
/* 1006 */       if (this.in_string)
/*      */       {
/* 1008 */         this.oracle_sql.append(this.c);
/*      */ 
/* 1010 */         if (this.c == '\'') {
/* 1011 */           this.in_string = false;
/*      */         }
/* 1013 */         this.i += 1; continue;
/*      */       }
/*      */ 
/* 1016 */       switch (this.c)
/*      */       {
/*      */       case '\'':
/* 1020 */         if (this.isLocate)
/*      */         {
/* 1022 */           if (localStringBuffer1 == null)
/*      */           {
/* 1024 */             localStringBuffer1 = new StringBuffer();
/* 1025 */             localStringBuffer2 = new StringBuffer();
/*      */           }
/*      */ 
/* 1028 */           if (j == 0)
/* 1029 */             localStringBuffer1.append(this.c);
/*      */           else {
/* 1031 */             localStringBuffer2.append(this.c);
/*      */           }
/* 1033 */           this.i += 1;
/*      */ 
/* 1035 */           continue;
/*      */         }
/*      */ 
/* 1038 */         this.oracle_sql.append(this.c);
/*      */ 
/* 1040 */         this.in_string = true;
/* 1041 */         this.i += 1;
/* 1042 */         this.first = false;
/*      */ 
/* 1044 */         break;
/*      */       case '{':
/* 1047 */         this.token_buffer.delete(0, this.token_buffer.length());
/*      */ 
/* 1049 */         this.i += 1;
/*      */ 
/* 1051 */         skipSpace();
/*      */ 
/* 1055 */         while ((this.i < this.length) && ((Character.isJavaLetterOrDigit(this.c = this.odbc_sql.charAt(this.i))) || (this.c == '?')))
/*      */         {
/* 1058 */           this.token_buffer.append(this.c);
/*      */ 
/* 1060 */           this.i += 1;
/*      */         }
/*      */ 
/* 1064 */         handleToken(this.token_buffer.substring(0, this.token_buffer.length()));
/*      */ 
/* 1066 */         this.c = this.odbc_sql.charAt(this.i);
/*      */ 
/* 1068 */         if (this.c != '}')
/*      */         {
/* 1070 */           String str = new String(this.i + ": Expecting \"}\" got \"" + this.c + "\"");
/*      */ 
/* 1072 */           DatabaseError.throwSqlException(33, str);
/*      */         }
/*      */ 
/* 1076 */         this.i += 1;
/*      */ 
/* 1078 */         break;
/*      */       case '}':
/* 1081 */         return;
/*      */       default:
/* 1090 */         if ((this.c != ' ') && (this.c != '(') && (this.isLocate))
/*      */         {
/* 1092 */           if (this.c == ')')
/*      */           {
/* 1094 */             if (localStringBuffer2.substring(0, localStringBuffer2.length()).trim().equals("?"))
/* 1095 */               this.oracle_sql.append(nextArgument());
/*      */             else {
/* 1097 */               this.oracle_sql.append(localStringBuffer2);
/*      */             }
/* 1099 */             this.oracle_sql.append(", ");
/*      */ 
/* 1101 */             if (localStringBuffer1.substring(0, localStringBuffer1.length()).trim().equals("?"))
/* 1102 */               this.oracle_sql.append(nextArgument());
/*      */             else {
/* 1104 */               this.oracle_sql.append(localStringBuffer1);
/*      */             }
/* 1106 */             appendChar(this.oracle_sql, this.c);
/*      */ 
/* 1108 */             this.isLocate = false;
/*      */           }
/*      */ 
/* 1111 */           if (localStringBuffer1 == null)
/*      */           {
/* 1113 */             localStringBuffer1 = new StringBuffer();
/* 1114 */             localStringBuffer2 = new StringBuffer();
/*      */           }
/*      */ 
/* 1117 */           if (this.c == ',')
/*      */           {
/* 1119 */             j = 1;
/* 1120 */             this.i += 1;
/* 1121 */             this.first = false;
/*      */ 
/* 1123 */             continue;
/*      */           }
/*      */ 
/* 1126 */           if (j == 0)
/* 1127 */             localStringBuffer1.append(this.c);
/*      */           else {
/* 1129 */             localStringBuffer2.append(this.c);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1134 */           appendChar(this.oracle_sql, this.c);
/*      */         }
/*      */ 
/* 1137 */         this.i += 1;
/* 1138 */         this.first = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void handleToken(String paramString) throws SQLException
/*      */   {
/* 1145 */     if (paramString.equalsIgnoreCase("?")) {
/* 1146 */       handleFunction();
/* 1147 */     } else if (paramString.equalsIgnoreCase("call")) {
/* 1148 */       handleCall();
/* 1149 */     } else if (paramString.equalsIgnoreCase("ts")) {
/* 1150 */       handleTimestamp();
/* 1151 */     } else if (paramString.equalsIgnoreCase("t")) {
/* 1152 */       handleTime();
/* 1153 */     } else if (paramString.equalsIgnoreCase("d")) {
/* 1154 */       handleDate();
/* 1155 */     } else if (paramString.equalsIgnoreCase("escape")) {
/* 1156 */       handleEscape();
/* 1157 */     } else if (paramString.equalsIgnoreCase("fn")) {
/* 1158 */       handleScalarFunction();
/* 1159 */     } else if (paramString.equalsIgnoreCase("oj")) {
/* 1160 */       handleOuterJoin();
/*      */     }
/*      */     else {
/* 1163 */       String str = new String(this.i + ": " + paramString);
/*      */ 
/* 1165 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/*      */   }
/*      */ 
/*      */   void handleFunction()
/*      */     throws SQLException
/*      */   {
/* 1173 */     boolean bool = this.first;
/*      */ 
/* 1175 */     if (bool) {
/* 1176 */       this.oracle_sql.append("BEGIN ");
/*      */     }
/* 1178 */     appendChar(this.oracle_sql, '?');
/* 1179 */     skipSpace();
/*      */     String str;
/* 1181 */     if (this.c != '=')
/*      */     {
/* 1183 */       str = new String(this.i + ". Expecting \"=\" got \"" + this.c + "\"");
/*      */ 
/* 1185 */       DatabaseError.throwSqlException(33, str);
/*      */     }
/*      */ 
/* 1188 */     this.i += 1;
/*      */ 
/* 1190 */     skipSpace();
/*      */ 
/* 1192 */     if (!this.odbc_sql.startsWith("call", this.i))
/*      */     {
/* 1194 */       str = new String(this.i + ". Expecting \"call\"");
/*      */ 
/* 1196 */       DatabaseError.throwSqlException(33, str);
/*      */     }
/*      */ 
/* 1199 */     this.i += 4;
/*      */ 
/* 1201 */     this.oracle_sql.append(" := ");
/* 1202 */     skipSpace();
/* 1203 */     handleODBC();
/*      */ 
/* 1205 */     if (bool)
/* 1206 */       this.oracle_sql.append("; END;");
/*      */   }
/*      */ 
/*      */   void handleCall()
/*      */     throws SQLException
/*      */   {
/* 1212 */     boolean bool = this.first;
/*      */ 
/* 1214 */     if (bool) {
/* 1215 */       this.oracle_sql.append("BEGIN ");
/*      */     }
/* 1217 */     skipSpace();
/* 1218 */     handleODBC();
/* 1219 */     skipSpace();
/*      */ 
/* 1221 */     if (bool)
/* 1222 */       this.oracle_sql.append("; END;");
/*      */   }
/*      */ 
/*      */   void handleTimestamp() throws SQLException
/*      */   {
/* 1227 */     if (this.isV8Compatible)
/*      */     {
/* 1229 */       this.oracle_sql.append("TO_DATE (");
/* 1230 */       skipSpace();
/*      */ 
/* 1232 */       int j = 0;
/*      */ 
/* 1234 */       while ((this.i < this.length) && ((this.c = this.odbc_sql.charAt(this.i)) != '}'))
/*      */       {
/* 1236 */         if (j == 0)
/*      */         {
/* 1238 */           if (this.c == '.')
/* 1239 */             j = 1;
/*      */           else {
/* 1241 */             this.oracle_sql.append(this.c);
/*      */           }
/*      */         }
/* 1244 */         this.i += 1;
/*      */       }
/*      */ 
/* 1247 */       if (j != 0) {
/* 1248 */         this.oracle_sql.append('\'');
/*      */       }
/* 1250 */       this.oracle_sql.append(", 'YYYY-MM-DD HH24:MI:SS')");
/*      */     }
/*      */     else
/*      */     {
/* 1254 */       this.oracle_sql.append("TO_TIMESTAMP (");
/* 1255 */       skipSpace();
/* 1256 */       handleODBC();
/* 1257 */       this.oracle_sql.append(", 'YYYY-MM-DD HH24:MI:SS.FF')");
/*      */     }
/*      */   }
/*      */ 
/*      */   void handleTime() throws SQLException
/*      */   {
/* 1263 */     this.oracle_sql.append("TO_DATE (");
/* 1264 */     skipSpace();
/* 1265 */     handleODBC();
/* 1266 */     this.oracle_sql.append(", 'HH24:MI:SS')");
/*      */   }
/*      */ 
/*      */   void handleDate() throws SQLException
/*      */   {
/* 1271 */     this.oracle_sql.append("TO_DATE (");
/* 1272 */     skipSpace();
/* 1273 */     handleODBC();
/* 1274 */     this.oracle_sql.append(", 'YYYY-MM-DD')");
/*      */   }
/*      */ 
/*      */   void handleEscape() throws SQLException
/*      */   {
/* 1279 */     this.oracle_sql.append("ESCAPE ");
/* 1280 */     skipSpace();
/* 1281 */     handleODBC();
/*      */   }
/*      */ 
/*      */   void handleScalarFunction() throws SQLException
/*      */   {
/* 1286 */     this.token_buffer.delete(0, this.token_buffer.length());
/*      */ 
/* 1288 */     this.i += 1;
/*      */ 
/* 1290 */     skipSpace();
/*      */ 
/* 1294 */     while ((this.i < this.length) && ((Character.isJavaLetterOrDigit(this.c = this.odbc_sql.charAt(this.i))) || (this.c == '?')))
/*      */     {
/* 1297 */       this.token_buffer.append(this.c);
/*      */ 
/* 1299 */       this.i += 1;
/*      */     }
/*      */ 
/* 1305 */     String str = this.token_buffer.substring(0, this.token_buffer.length()).toUpperCase().intern();
/*      */ 
/* 1314 */     if (str == "ABS") {
/* 1315 */       usingFunctionName(str);
/* 1316 */     } else if (str == "ACOS") {
/* 1317 */       usingFunctionName(str);
/* 1318 */     } else if (str == "ASIN") {
/* 1319 */       usingFunctionName(str);
/* 1320 */     } else if (str == "ATAN") {
/* 1321 */       usingFunctionName(str);
/* 1322 */     } else if (str == "ATAN2") {
/* 1323 */       usingFunctionName(str);
/* 1324 */     } else if (str == "CEILING") {
/* 1325 */       usingFunctionName("CEIL");
/* 1326 */     } else if (str == "COS") {
/* 1327 */       usingFunctionName(str);
/* 1328 */     } else if (str == "COT") {
/* 1329 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1331 */     else if (str == "DEGREES") {
/* 1332 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1334 */     else if (str == "EXP") {
/* 1335 */       usingFunctionName(str);
/* 1336 */     } else if (str == "FLOOR") {
/* 1337 */       usingFunctionName(str);
/* 1338 */     } else if (str == "LOG") {
/* 1339 */       usingFunctionName("LN");
/* 1340 */     } else if (str == "LOG10") {
/* 1341 */       replacingFunctionPrefix("LOG ( 10, ");
/* 1342 */     } else if (str == "MOD") {
/* 1343 */       usingFunctionName(str);
/* 1344 */     } else if (str == "PI") {
/* 1345 */       replacingFunctionPrefix("( 3.141592653589793238462643383279502884197169399375 ");
/* 1346 */     } else if (str == "POWER") {
/* 1347 */       usingFunctionName(str);
/* 1348 */     } else if (str == "RADIANS") {
/* 1349 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1351 */     else if (str == "RAND") {
/* 1352 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1354 */     else if (str == "ROUND") {
/* 1355 */       usingFunctionName(str);
/* 1356 */     } else if (str == "SIGN") {
/* 1357 */       usingFunctionName(str);
/* 1358 */     } else if (str == "SIN") {
/* 1359 */       usingFunctionName(str);
/* 1360 */     } else if (str == "SQRT") {
/* 1361 */       usingFunctionName(str);
/* 1362 */     } else if (str == "TAN") {
/* 1363 */       usingFunctionName(str);
/* 1364 */     } else if (str == "TRUNCATE") {
/* 1365 */       usingFunctionName("TRUNC");
/*      */     }
/* 1368 */     else if (str == "ASCII") {
/* 1369 */       usingFunctionName(str);
/* 1370 */     } else if (str == "CHAR") {
/* 1371 */       usingFunctionName("CHR");
/* 1372 */     } else if (str == "CONCAT") {
/* 1373 */       usingFunctionName(str);
/* 1374 */     } else if (str == "DIFFERENCE") {
/* 1375 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1377 */     else if (str == "INSERT") {
/* 1378 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1380 */     else if (str == "LCASE") {
/* 1381 */       usingFunctionName("LOWER");
/* 1382 */     } else if (str == "LEFT") {
/* 1383 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1385 */     else if (str == "LENGTH") {
/* 1386 */       usingFunctionName(str);
/* 1387 */     } else if (str == "LOCATE")
/*      */     {
/* 1389 */       this.isLocate = true;
/*      */ 
/* 1391 */       usingFunctionName("INSTR");
/*      */     }
/* 1393 */     else if (str == "LTRIM") {
/* 1394 */       usingFunctionName(str);
/* 1395 */     } else if (str == "REPEAT") {
/* 1396 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1398 */     else if (str == "REPLACE") {
/* 1399 */       usingFunctionName(str);
/* 1400 */     } else if (str == "RIGHT") {
/* 1401 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1403 */     else if (str == "RTRIM") {
/* 1404 */       usingFunctionName(str);
/* 1405 */     } else if (str == "SOUNDEX") {
/* 1406 */       usingFunctionName(str);
/* 1407 */     } else if (str == "SPACE") {
/* 1408 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1410 */     else if (str == "SUBSTRING") {
/* 1411 */       usingFunctionName("SUBSTR");
/* 1412 */     } else if (str == "UCASE") {
/* 1413 */       usingFunctionName("UPPER");
/*      */     }
/* 1416 */     else if (str == "CURDATE") {
/* 1417 */       replacingFunctionPrefix("(CURRENT_DATE");
/* 1418 */     } else if (str == "CURTIME") {
/* 1419 */       replacingFunctionPrefix("(CURRENT_TIMESTAMP");
/* 1420 */     } else if (str == "DAYNAME") {
/* 1421 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1423 */     else if (str == "DAYOFMONTH") {
/* 1424 */       replacingFunctionPrefix("EXTRACT ( DAY FROM ");
/* 1425 */     } else if (str == "DAYOFWEEK") {
/* 1426 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1428 */     else if (str == "DAYOFYEAR") {
/* 1429 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1431 */     else if (str == "HOUR") {
/* 1432 */       replacingFunctionPrefix("EXTRACT ( HOUR FROM ");
/* 1433 */     } else if (str == "MINUTE") {
/* 1434 */       replacingFunctionPrefix("EXTRACT ( MINUTE FROM ");
/* 1435 */     } else if (str == "MONTH") {
/* 1436 */       replacingFunctionPrefix("EXTRACT ( MONTH FROM ");
/* 1437 */     } else if (str == "MONTHNAME") {
/* 1438 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1440 */     else if (str == "NOW") {
/* 1441 */       replacingFunctionPrefix("(CURRENT_TIMESTAMP");
/* 1442 */     } else if (str == "QUARTER") {
/* 1443 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1445 */     else if (str == "SECOND") {
/* 1446 */       replacingFunctionPrefix("EXTRACT ( SECOND FROM ");
/* 1447 */     } else if (str == "TIMESTAMPADD") {
/* 1448 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1450 */     else if (str == "TIMESTAMPDIFF") {
/* 1451 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1453 */     else if (str == "WEEK") {
/* 1454 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1456 */     else if (str == "YEAR") {
/* 1457 */       replacingFunctionPrefix("EXTRACT ( YEAR FROM ");
/*      */     }
/* 1460 */     else if (str == "DATABASE") {
/* 1461 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1463 */     else if (str == "IFNULL") {
/* 1464 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/* 1466 */     else if (str == "USER") {
/* 1467 */       replacingFunctionPrefix("(USER");
/*      */     }
/* 1470 */     else if (str == "CONVERT") {
/* 1471 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/*      */     else
/*      */     {
/* 1476 */       DatabaseError.throwSqlException(34, str);
/*      */     }
/*      */   }
/*      */ 
/*      */   void usingFunctionName(String paramString) throws SQLException
/*      */   {
/* 1482 */     this.oracle_sql.append(paramString);
/* 1483 */     skipSpace();
/* 1484 */     handleODBC();
/*      */   }
/*      */ 
/*      */   void replacingFunctionPrefix(String paramString) throws SQLException
/*      */   {
/* 1489 */     skipSpace();
/*      */ 
/* 1492 */     if ((this.i < this.length) && ((this.c = this.odbc_sql.charAt(this.i)) == '('))
/* 1493 */       this.i += 1;
/*      */     else {
/* 1495 */       DatabaseError.throwSqlException(33);
/*      */     }
/* 1497 */     this.oracle_sql.append(paramString);
/* 1498 */     skipSpace();
/* 1499 */     handleODBC();
/*      */   }
/*      */ 
/*      */   void handleOuterJoin() throws SQLException
/*      */   {
/* 1504 */     this.oracle_sql.append(" ( ");
/* 1505 */     skipSpace();
/* 1506 */     handleODBC();
/* 1507 */     this.oracle_sql.append(" ) ");
/*      */   }
/*      */ 
/*      */   String nextArgument()
/*      */   {
/* 1512 */     String str = ":" + this.current_argument;
/*      */ 
/* 1514 */     this.current_argument += 1;
/*      */ 
/* 1516 */     return str;
/*      */   }
/*      */ 
/*      */   void appendChar(StringBuffer paramStringBuffer, char paramChar)
/*      */   {
/* 1521 */     if (paramChar == '?')
/* 1522 */       paramStringBuffer.append(nextArgument());
/*      */     else
/* 1524 */       paramStringBuffer.append(paramChar);
/*      */   }
/*      */ 
/*      */   void skipSpace()
/*      */   {
/* 1529 */     while ((this.i < this.length) && ((this.c = this.odbc_sql.charAt(this.i)) == ' '))
/* 1530 */       this.i += 1;
/*      */   }
/*      */ 
/*      */   String generateParameterName()
/*      */   {
/* 1540 */     if ((this.parameterCount == 0) || (this.parameterList == null))
/*      */     {
/* 1542 */       return "rowid" + this.paramSuffix++;
/*      */     }
/*      */ 
/* 1548 */     String str = "rowid" + this.paramSuffix++;
/* 1549 */     for (int j = 0; ; j++) { if (j >= this.parameterList.length) break label109;
/* 1551 */       if (str.equals(this.parameterList[j]))
/*      */         break; }
/* 1553 */     label109: return str;
/*      */   }
/*      */ 
/*      */   static boolean isValidPlsqlWarning(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1575 */     return paramString.matches("('\\s*([a-zA-Z0-9:,\\(\\)\\s])*')\\s*(,\\s*'([a-zA-Z0-9:,\\(\\)\\s])*')*");
/*      */   }
/*      */ 
/*      */   public static boolean isValidObjectName(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1596 */     return paramString.matches("([a-zA-Z]{1}\\w*(\\$|\\#)*\\w*)|(\".*)");
/*      */   }
/*      */ 
/*      */   public static void main(String[] paramArrayOfString)
/*      */   {
/* 1607 */     String[] arrayOfString1 = { "IS_UNINITIALIZED", "IS_SELECT", "IS_PLSQL_BLOCK", "IS_DML", "IS_OTHER", "IS_CALL_BLOCK" };
/*      */     try
/*      */     {
/* 1612 */       OracleSql localOracleSql = new OracleSql(null);
/*      */ 
/* 1614 */       boolean bool1 = paramArrayOfString[0].equals("true");
/* 1615 */       boolean bool2 = paramArrayOfString[1].equals("true");
/*      */ 
/* 1617 */       localOracleSql.initialize(paramArrayOfString[2]);
/* 1618 */       String str = localOracleSql.getSql(bool1, bool2);
/*      */ 
/* 1620 */       System.out.println(arrayOfString1[(localOracleSql.sqlKind + 1)] + ", " + localOracleSql.parameterCount);
/*      */ 
/* 1622 */       String[] arrayOfString2 = localOracleSql.getParameterList();
/*      */       int j;
/* 1624 */       if (arrayOfString2 == EMPTY_LIST)
/* 1625 */         System.out.println("parameterList is empty");
/*      */       else {
/* 1627 */         for (j = 0; j < arrayOfString2.length; j++)
/* 1628 */           System.out.println("parameterList[" + j + "] = " + arrayOfString2[j]);
/*      */       }
/* 1630 */       if (localOracleSql.lastNcharLiteralLocation == 2) { System.out.println("No NCHAR literals");
/*      */       } else
/*      */       {
/* 1633 */         System.out.println("NCHAR Literals");
/* 1634 */         for (j = 1; j < localOracleSql.lastNcharLiteralLocation - 1; )
/* 1635 */           System.out.println(str.substring(localOracleSql.ncharLiteralLocation[(j++)], localOracleSql.ncharLiteralLocation[(j++)]));
/*      */       }
/* 1637 */       System.out.println("Keywords");
/* 1638 */       if (localOracleSql.selectEndIndex == -1) System.out.println("no select"); else
/* 1639 */         System.out.println("'" + str.substring(localOracleSql.selectEndIndex - 6, localOracleSql.selectEndIndex) + "'");
/* 1640 */       if (localOracleSql.orderByStartIndex == -1) System.out.println("no order by"); else
/* 1641 */         System.out.println("'" + str.substring(localOracleSql.orderByStartIndex, localOracleSql.orderByEndIndex) + "'");
/* 1642 */       if (localOracleSql.whereStartIndex == -1) System.out.println("no where"); else
/* 1643 */         System.out.println("'" + str.substring(localOracleSql.whereStartIndex, localOracleSql.whereEndIndex) + "'");
/* 1644 */       if (localOracleSql.forUpdateStartIndex == -1) System.out.println("no for update"); else {
/* 1645 */         System.out.println("'" + str.substring(localOracleSql.forUpdateStartIndex, localOracleSql.forUpdateEndIndex) + "'");
/*      */       }
/* 1647 */       System.out.println("\"" + str + "\"");
/* 1648 */       System.out.println("\"" + localOracleSql.getRevisedSql() + "\"");
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1652 */       localException.printStackTrace(System.out);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleSql
 * JD-Core Version:    0.6.0
 */