/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetMetaData;
/*     */ import javax.sql.rowset.CachedRowSet;
/*     */ import javax.sql.rowset.JoinRowSet;
/*     */ import javax.sql.rowset.Joinable;
/*     */ 
/*     */ public class OracleJoinRowSet extends OracleWebRowSet
/*     */   implements JoinRowSet
/*     */ {
/*     */   private static final String MATCH_COLUMN_SUFFIX = "#MATCH_COLUMN";
/*  58 */   private static boolean[] supportedJoins = { false, true, false, false, false };
/*     */   private int joinType;
/*     */   private Vector addedRowSets;
/*     */   private Vector addedRowSetNames;
/*     */   private Object lockForJoinActions;
/*     */ 
/*     */   public OracleJoinRowSet()
/*     */     throws SQLException
/*     */   {
/*  75 */     this.joinType = 1;
/*  76 */     this.addedRowSets = new Vector();
/*  77 */     this.addedRowSetNames = new Vector();
/*     */   }
/*     */ 
/*     */   public synchronized void addRowSet(Joinable paramJoinable)
/*     */     throws SQLException
/*     */   {
/* 115 */     if (paramJoinable == null) {
/* 116 */       throw new SQLException("Invalid empty RowSet parameter");
/*     */     }
/* 118 */     if (!(paramJoinable instanceof RowSet)) {
/* 119 */       throw new SQLException("The parameter is not a RowSet instance");
/*     */     }
/* 121 */     OracleCachedRowSet localOracleCachedRowSet = checkAndWrapRowSet((RowSet)paramJoinable);
/* 122 */     String str = getMatchColumnTableName((RowSet)paramJoinable);
/*     */ 
/* 124 */     switch (this.joinType)
/*     */     {
/*     */     case 1:
/* 127 */       doInnerJoin(localOracleCachedRowSet);
/*     */ 
/* 129 */       this.addedRowSets.add(paramJoinable);
/* 130 */       this.addedRowSetNames.add(str);
/* 131 */       break;
/*     */     case 0:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     default:
/* 138 */       throw new SQLException("Join type is not supported");
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void addRowSet(RowSet paramRowSet, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 161 */     ((OracleRowSet)paramRowSet).setMatchColumn(paramInt);
/*     */ 
/* 166 */     addRowSet((Joinable)paramRowSet);
/*     */   }
/*     */ 
/*     */   public synchronized void addRowSet(RowSet paramRowSet, String paramString)
/*     */     throws SQLException
/*     */   {
/* 190 */     ((OracleRowSet)paramRowSet).setMatchColumn(paramString);
/*     */ 
/* 195 */     addRowSet((Joinable)paramRowSet);
/*     */   }
/*     */ 
/*     */   public synchronized void addRowSet(RowSet[] paramArrayOfRowSet, int[] paramArrayOfInt)
/*     */     throws SQLException
/*     */   {
/* 228 */     if (paramArrayOfRowSet.length != paramArrayOfInt.length) {
/* 229 */       throw new SQLException("Number of elements in rowsets is not equal to match columns");
/*     */     }
/* 231 */     for (int i = 0; i < paramArrayOfRowSet.length; i++)
/*     */     {
/* 233 */       ((OracleRowSet)paramArrayOfRowSet[i]).setMatchColumn(paramArrayOfInt[i]);
/*     */ 
/* 238 */       addRowSet((Joinable)paramArrayOfRowSet[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void addRowSet(RowSet[] paramArrayOfRowSet, String[] paramArrayOfString)
/*     */     throws SQLException
/*     */   {
/* 273 */     if (paramArrayOfRowSet.length != paramArrayOfString.length) {
/* 274 */       throw new SQLException("Number of elements in rowsets is not equal to match columns");
/*     */     }
/* 276 */     for (int i = 0; i < paramArrayOfRowSet.length; i++)
/*     */     {
/* 278 */       ((OracleRowSet)paramArrayOfRowSet[i]).setMatchColumn(paramArrayOfString[i]);
/*     */ 
/* 283 */       addRowSet((Joinable)paramArrayOfRowSet[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getRowSets()
/*     */     throws SQLException
/*     */   {
/* 303 */     return this.addedRowSets;
/*     */   }
/*     */ 
/*     */   public String[] getRowSetNames()
/*     */     throws SQLException
/*     */   {
/* 318 */     Object[] arrayOfObject = this.addedRowSetNames.toArray();
/* 319 */     String[] arrayOfString = new String[arrayOfObject.length];
/* 320 */     for (int i = 0; i < arrayOfObject.length; i++)
/*     */     {
/* 322 */       arrayOfString[i] = ((String)arrayOfObject[i]);
/*     */     }
/* 324 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   public CachedRowSet toCachedRowSet()
/*     */     throws SQLException
/*     */   {
/* 357 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)createCopy();
/*     */ 
/* 360 */     localOracleCachedRowSet.setCommand("");
/*     */ 
/* 362 */     return localOracleCachedRowSet;
/*     */   }
/*     */ 
/*     */   public int getJoinType()
/*     */   {
/* 377 */     return this.joinType;
/*     */   }
/*     */ 
/*     */   public boolean supportsCrossJoin()
/*     */   {
/* 387 */     return supportedJoins[0];
/*     */   }
/*     */ 
/*     */   public boolean supportsInnerJoin()
/*     */   {
/* 397 */     return supportedJoins[1];
/*     */   }
/*     */ 
/*     */   public boolean supportsLeftOuterJoin()
/*     */   {
/* 407 */     return supportedJoins[2];
/*     */   }
/*     */ 
/*     */   public boolean supportsRightOuterJoin()
/*     */   {
/* 417 */     return supportedJoins[3];
/*     */   }
/*     */ 
/*     */   public boolean supportsFullJoin()
/*     */   {
/* 427 */     return supportedJoins[4];
/*     */   }
/*     */ 
/*     */   public void setJoinType(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 442 */     if (paramInt != 1) {
/* 443 */       throw new SQLException("Join type is not supported");
/*     */     }
/* 445 */     this.joinType = paramInt;
/*     */   }
/*     */ 
/*     */   public synchronized String getWhereClause()
/*     */     throws SQLException
/*     */   {
/* 460 */     if (this.addedRowSets.size() < 2) {
/* 461 */       return "WHERE";
/*     */     }
/* 463 */     StringBuffer localStringBuffer = new StringBuffer();
/* 464 */     localStringBuffer.append("WHERE\n");
/*     */ 
/* 466 */     Object localObject1 = (OracleRowSet)this.addedRowSets.get(0);
/* 467 */     Object localObject2 = ((OracleRowSet)localObject1).getMatchColumnIndexes();
/* 468 */     Object localObject3 = ((OracleRowSet)localObject1).getMetaData();
/* 469 */     Object localObject4 = ((OracleRowSet)localObject1).getTableName();
/*     */ 
/* 476 */     for (int i = 1; i < this.addedRowSets.size(); i++)
/*     */     {
/* 478 */       if (i > 1)
/*     */       {
/* 480 */         localStringBuffer.append("\nAND\n");
/*     */       }
/*     */ 
/* 483 */       OracleRowSet localOracleRowSet = (OracleRowSet)this.addedRowSets.get(i);
/* 484 */       int[] arrayOfInt = localOracleRowSet.getMatchColumnIndexes();
/* 485 */       ResultSetMetaData localResultSetMetaData = localOracleRowSet.getMetaData();
/* 486 */       String str = localOracleRowSet.getTableName();
/*     */ 
/* 488 */       for (int j = 0; j < localObject2.length; j++)
/*     */       {
/* 490 */         if (j > 0)
/*     */         {
/* 492 */           localStringBuffer.append("\nAND\n");
/*     */         }
/*     */ 
/* 495 */         localStringBuffer.append("(" + (String)localObject4 + "." + ((ResultSetMetaData)localObject3).getColumnName(localObject2[j]) + " = " + str + "." + localResultSetMetaData.getColumnName(arrayOfInt[j]) + ")");
/*     */       }
/*     */ 
/* 502 */       localObject1 = localOracleRowSet;
/* 503 */       localObject2 = arrayOfInt;
/* 504 */       localObject3 = localResultSetMetaData;
/* 505 */       localObject4 = str;
/*     */     }
/*     */ 
/* 508 */     localStringBuffer.append(";");
/*     */ 
/* 510 */     return (String)(String)(String)(String)localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   private void doInnerJoin(OracleCachedRowSet paramOracleCachedRowSet)
/*     */     throws SQLException
/*     */   {
/* 533 */     if (this.addedRowSets.isEmpty())
/*     */     {
/* 536 */       setMetaData((RowSetMetaData)paramOracleCachedRowSet.getMetaData());
/* 537 */       populate(paramOracleCachedRowSet);
/*     */ 
/* 539 */       setMatchColumn(paramOracleCachedRowSet.getMatchColumnIndexes());
/*     */     }
/*     */     else
/*     */     {
/* 543 */       Vector localVector = new Vector(100);
/* 544 */       OracleRowSetMetaData localOracleRowSetMetaData = new OracleRowSetMetaData(10);
/*     */ 
/* 546 */       int[] arrayOfInt1 = getMatchColumnIndexes();
/* 547 */       int[] arrayOfInt2 = paramOracleCachedRowSet.getMatchColumnIndexes();
/*     */ 
/* 550 */       int i = getMetaData().getColumnCount() + paramOracleCachedRowSet.getMetaData().getColumnCount() - arrayOfInt2.length;
/*     */ 
/* 553 */       localOracleRowSetMetaData.setColumnCount(i);
/*     */ 
/* 556 */       String str = getTableName() + "#" + paramOracleCachedRowSet.getTableName();
/*     */       boolean bool;
/* 559 */       for (int j = 1; j <= this.colCount; j++)
/*     */       {
/* 561 */         bool = false;
/* 562 */         for (k = 0; k < arrayOfInt1.length; k++)
/*     */         {
/* 564 */           if (j != arrayOfInt1[k])
/*     */             continue;
/* 566 */           bool = true;
/* 567 */           break;
/*     */         }
/*     */ 
/* 571 */         setNewColumnMetaData(j, localOracleRowSetMetaData, j, (RowSetMetaData)this.rowsetMetaData, bool, str);
/*     */       }
/*     */ 
/* 576 */       RowSetMetaData localRowSetMetaData = (RowSetMetaData)paramOracleCachedRowSet.getMetaData();
/*     */ 
/* 578 */       int k = localRowSetMetaData.getColumnCount();
/*     */ 
/* 581 */       int m = this.colCount + 1;
/* 582 */       int[] arrayOfInt3 = new int[k];
/*     */ 
/* 584 */       for (int n = 1; n <= k; n++)
/*     */       {
/* 586 */         bool = false;
/* 587 */         for (i1 = 0; i1 < arrayOfInt2.length; i1++)
/*     */         {
/* 589 */           if (n != arrayOfInt1[i1])
/*     */             continue;
/* 591 */           bool = true;
/* 592 */           break;
/*     */         }
/*     */ 
/* 596 */         if (!bool)
/*     */         {
/* 598 */           setNewColumnMetaData(m, localOracleRowSetMetaData, n, localRowSetMetaData, bool, str);
/*     */ 
/* 602 */           arrayOfInt3[(n - 1)] = m;
/* 603 */           m++;
/*     */         }
/*     */         else
/*     */         {
/* 608 */           arrayOfInt3[(n - 1)] = -1;
/*     */         }
/*     */       }
/*     */ 
/* 612 */       beforeFirst();
/*     */ 
/* 615 */       n = paramOracleCachedRowSet.size();
/* 616 */       int i1 = 0;
/*     */ 
/* 618 */       for (int i2 = 1; i2 <= this.rowCount; i2++)
/*     */       {
/* 620 */         next();
/* 621 */         paramOracleCachedRowSet.beforeFirst();
/*     */ 
/* 623 */         for (int i3 = 1; i3 <= n; i3++)
/*     */         {
/* 625 */           paramOracleCachedRowSet.next();
/*     */ 
/* 627 */           i1 = 1;
/* 628 */           for (int i4 = 0; i4 < arrayOfInt1.length; i4++)
/*     */           {
/* 630 */             Object localObject1 = getObject(arrayOfInt1[i4]);
/* 631 */             Object localObject2 = paramOracleCachedRowSet.getObject(arrayOfInt2[i4]);
/* 632 */             if (localObject1.equals(localObject2))
/*     */               continue;
/* 634 */             i1 = 0;
/* 635 */             break;
/*     */           }
/*     */ 
/* 639 */           if (i1 == 0)
/*     */             continue;
/* 641 */           OracleRow localOracleRow = new OracleRow(i, true);
/*     */ 
/* 644 */           for (int i5 = 1; i5 <= this.colCount; i5++)
/*     */           {
/* 646 */             localOracleRow.updateObject(i5, getObject(i5));
/*     */           }
/*     */ 
/* 649 */           for (i5 = 1; i5 <= k; i5++)
/*     */           {
/* 651 */             if (arrayOfInt3[(i5 - 1)] == -1)
/*     */               continue;
/* 653 */             localOracleRow.updateObject(arrayOfInt3[(i5 - 1)], paramOracleCachedRowSet.getObject(i5));
/*     */           }
/*     */ 
/* 658 */           localVector.add(localOracleRow);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 663 */       this.rows = localVector;
/* 664 */       this.presentRow = 0;
/* 665 */       this.rowCount = this.rows.size();
/* 666 */       setMetaData(localOracleRowSetMetaData);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setNewColumnMetaData(int paramInt1, RowSetMetaData paramRowSetMetaData1, int paramInt2, RowSetMetaData paramRowSetMetaData2, boolean paramBoolean, String paramString)
/*     */     throws SQLException
/*     */   {
/* 698 */     paramRowSetMetaData1.setAutoIncrement(paramInt1, paramRowSetMetaData2.isAutoIncrement(paramInt2));
/* 699 */     paramRowSetMetaData1.setCaseSensitive(paramInt1, paramRowSetMetaData2.isCaseSensitive(paramInt2));
/* 700 */     paramRowSetMetaData1.setCatalogName(paramInt1, paramRowSetMetaData2.getCatalogName(paramInt2));
/* 701 */     paramRowSetMetaData1.setColumnDisplaySize(paramInt1, paramRowSetMetaData2.getColumnDisplaySize(paramInt2));
/*     */ 
/* 704 */     if (paramBoolean)
/*     */     {
/* 706 */       paramRowSetMetaData1.setColumnName(paramInt1, paramRowSetMetaData2.getColumnName(paramInt1) + "#MATCH_COLUMN");
/*     */     }
/*     */     else
/*     */     {
/* 711 */       paramRowSetMetaData1.setColumnName(paramInt1, paramRowSetMetaData2.getColumnName(paramInt2));
/*     */     }
/*     */ 
/* 714 */     paramRowSetMetaData1.setColumnLabel(paramInt1, paramRowSetMetaData1.getColumnName(paramInt2));
/*     */ 
/* 716 */     paramRowSetMetaData1.setColumnType(paramInt1, paramRowSetMetaData2.getColumnType(paramInt2));
/* 717 */     paramRowSetMetaData1.setColumnTypeName(paramInt1, paramRowSetMetaData2.getColumnTypeName(paramInt2));
/* 718 */     paramRowSetMetaData1.setCurrency(paramInt1, paramRowSetMetaData2.isCurrency(paramInt2));
/* 719 */     paramRowSetMetaData1.setNullable(paramInt1, paramRowSetMetaData2.isNullable(paramInt2));
/* 720 */     paramRowSetMetaData1.setPrecision(paramInt1, paramRowSetMetaData2.getPrecision(paramInt2));
/* 721 */     paramRowSetMetaData1.setScale(paramInt1, paramRowSetMetaData2.getScale(paramInt2));
/* 722 */     paramRowSetMetaData1.setSchemaName(paramInt1, paramRowSetMetaData2.getSchemaName(paramInt2));
/* 723 */     paramRowSetMetaData1.setSearchable(paramInt1, paramRowSetMetaData2.isSearchable(paramInt2));
/* 724 */     paramRowSetMetaData1.setSigned(paramInt1, paramRowSetMetaData2.isSigned(paramInt2));
/*     */ 
/* 726 */     if (paramBoolean)
/*     */     {
/* 728 */       paramRowSetMetaData1.setTableName(paramInt1, paramString);
/*     */     }
/*     */     else
/*     */     {
/* 732 */       paramRowSetMetaData1.setTableName(paramInt1, paramRowSetMetaData2.getTableName(paramInt2));
/*     */     }
/*     */   }
/*     */ 
/*     */   private OracleCachedRowSet checkAndWrapRowSet(RowSet paramRowSet)
/*     */     throws SQLException
/*     */   {
/*     */     OracleCachedRowSet localOracleCachedRowSet;
/* 755 */     if ((paramRowSet instanceof OracleCachedRowSet))
/*     */     {
/* 757 */       localOracleCachedRowSet = (OracleCachedRowSet)paramRowSet;
/*     */     }
/* 759 */     else if ((paramRowSet instanceof OracleJDBCRowSet))
/*     */     {
/* 761 */       localOracleCachedRowSet = new OracleCachedRowSet();
/* 762 */       localOracleCachedRowSet.populate(paramRowSet);
/*     */ 
/* 764 */       int[] arrayOfInt = ((OracleJDBCRowSet)paramRowSet).getMatchColumnIndexes();
/* 765 */       localOracleCachedRowSet.setMatchColumn(arrayOfInt);
/*     */     }
/*     */     else {
/* 768 */       throw new SQLException("Third-party RowSet Join not yet supported");
/*     */     }
/* 770 */     return localOracleCachedRowSet;
/*     */   }
/*     */ 
/*     */   private String getMatchColumnTableName(RowSet paramRowSet)
/*     */     throws SQLException
/*     */   {
/* 777 */     String str = null;
/* 778 */     if ((paramRowSet instanceof OracleRowSet))
/*     */     {
/* 780 */       str = ((OracleRowSet)paramRowSet).getTableName();
/*     */     }
/*     */ 
/* 783 */     return str;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleJoinRowSet
 * JD-Core Version:    0.6.0
 */