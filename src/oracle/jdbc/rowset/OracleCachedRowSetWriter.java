/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.RowSetWriter;
/*     */ 
/*     */ public class OracleCachedRowSetWriter
/*     */   implements RowSetWriter, Serializable
/*     */ {
/*  59 */   private StringBuffer updateClause = new StringBuffer("");
/*     */ 
/*  65 */   private StringBuffer deleteClause = new StringBuffer("");
/*     */ 
/*  71 */   private StringBuffer insertClause = new StringBuffer("");
/*     */   private PreparedStatement insertStmt;
/*     */   private PreparedStatement updateStmt;
/*     */   private PreparedStatement deleteStmt;
/*     */   private ResultSetMetaData rsmd;
/*     */   private transient Connection connection;
/*     */   private int columnCount;
/*     */ 
/*     */   private String getSchemaName(RowSet paramRowSet)
/*     */     throws SQLException
/*     */   {
/* 137 */     return paramRowSet.getUsername();
/*     */   }
/*     */ 
/*     */   private String getTableName(RowSet paramRowSet)
/*     */     throws SQLException
/*     */   {
/* 163 */     String str1 = ((OracleCachedRowSet)paramRowSet).getTableName();
/* 164 */     if (str1 != null) {
/* 165 */       return str1;
/*     */     }
/* 167 */     String str2 = paramRowSet.getCommand().toUpperCase();
/*     */ 
/* 184 */     int i = str2.indexOf(" FROM ");
/*     */ 
/* 201 */     if (i == -1) {
/* 202 */       throw new SQLException("Could not parse the SQL String to get the table name.\n" + (str2 != "" ? str2 : "Please use RowSet.setCommand (String) to set the SQL query string."));
/*     */     }
/*     */ 
/* 209 */     String str3 = str2.substring(i + 6).trim();
/*     */ 
/* 226 */     StringTokenizer localStringTokenizer = new StringTokenizer(str3);
/* 227 */     if (localStringTokenizer.hasMoreTokens()) {
/* 228 */       str3 = localStringTokenizer.nextToken();
/*     */     }
/*     */ 
/* 245 */     return str3;
/*     */   }
/*     */ 
/*     */   private void initSQLStatement(RowSet paramRowSet)
/*     */     throws SQLException
/*     */   {
/* 269 */     this.insertClause = new StringBuffer("INSERT INTO " + getTableName(paramRowSet) + "(");
/* 270 */     this.updateClause = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");
/* 271 */     this.deleteClause = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");
/*     */ 
/* 274 */     this.rsmd = paramRowSet.getMetaData();
/* 275 */     this.columnCount = this.rsmd.getColumnCount();
/*     */ 
/* 294 */     for (int i = 0; i < this.columnCount; i++)
/*     */     {
/* 296 */       if (i != 0) this.insertClause.append(", ");
/* 297 */       this.insertClause.append(this.rsmd.getColumnName(i + 1));
/*     */ 
/* 299 */       if (i != 0) this.updateClause.append(", ");
/* 300 */       this.updateClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);
/*     */ 
/* 302 */       if (i != 0) this.deleteClause.append(" AND ");
/* 303 */       this.deleteClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);
/*     */     }
/* 305 */     this.insertClause.append(") VALUES (");
/* 306 */     this.updateClause.append(" WHERE ");
/*     */ 
/* 308 */     for (i = 0; i < this.columnCount; i++)
/*     */     {
/* 310 */       if (i != 0) this.insertClause.append(", ");
/* 311 */       this.insertClause.append(":" + i);
/*     */ 
/* 313 */       if (i != 0) this.updateClause.append(" AND ");
/* 314 */       this.updateClause.append(this.rsmd.getColumnName(i + 1) + " = :" + i);
/*     */     }
/* 316 */     this.insertClause.append(")");
/*     */ 
/* 318 */     this.insertStmt = this.connection.prepareStatement(this.insertClause.substring(0, this.insertClause.length()));
/*     */ 
/* 320 */     this.updateStmt = this.connection.prepareStatement(this.updateClause.substring(0, this.updateClause.length()));
/*     */ 
/* 322 */     this.deleteStmt = this.connection.prepareStatement(this.deleteClause.substring(0, this.deleteClause.length()));
/*     */   }
/*     */ 
/*     */   private boolean insertRow(OracleRow paramOracleRow)
/*     */     throws SQLException
/*     */   {
/* 363 */     this.insertStmt.clearParameters();
/* 364 */     for (int i = 1; i <= this.columnCount; i++)
/*     */     {
/* 366 */       Object localObject = null;
/* 367 */       localObject = paramOracleRow.isColumnChanged(i) ? paramOracleRow.getModifiedColumn(i) : paramOracleRow.getColumn(i);
/*     */ 
/* 384 */       if (localObject == null)
/* 385 */         this.insertStmt.setNull(i, this.rsmd.getColumnType(i));
/*     */       else
/* 387 */         this.insertStmt.setObject(i, localObject);
/*     */     }
/* 389 */     return this.insertStmt.executeUpdate() == 1;
/*     */   }
/*     */ 
/*     */   private boolean updateRow(RowSet paramRowSet, OracleRow paramOracleRow)
/*     */     throws SQLException
/*     */   {
/* 413 */     this.updateStmt.clearParameters();
/* 414 */     for (int i = 1; i <= this.columnCount; i++)
/*     */     {
/* 416 */       Object localObject = null;
/* 417 */       localObject = paramOracleRow.isColumnChanged(i) ? paramOracleRow.getModifiedColumn(i) : paramOracleRow.getColumn(i);
/*     */ 
/* 434 */       if (localObject == null)
/* 435 */         this.updateStmt.setNull(i, this.rsmd.getColumnType(i));
/*     */       else
/* 437 */         this.updateStmt.setObject(i, localObject);
/*     */     }
/* 439 */     for (i = 1; i <= this.columnCount; i++)
/*     */     {
/* 441 */       if (paramOracleRow.isOriginalNull(i)) {
/* 442 */         return updateRowWithNull(paramRowSet, paramOracleRow);
/*     */       }
/* 444 */       this.updateStmt.setObject(i + this.columnCount, paramOracleRow.getColumn(i));
/*     */     }
/*     */ 
/* 447 */     return this.updateStmt.executeUpdate() == 1;
/*     */   }
/*     */ 
/*     */   private boolean updateRowWithNull(RowSet paramRowSet, OracleRow paramOracleRow)
/*     */     throws SQLException
/*     */   {
/* 468 */     int i = 0;
/* 469 */     StringBuffer localStringBuffer = new StringBuffer("UPDATE " + getTableName(paramRowSet) + " SET ");
/*     */ 
/* 472 */     for (int j = 1; j <= this.columnCount; j++)
/*     */     {
/* 474 */       if (j != 1) {
/* 475 */         localStringBuffer.append(", ");
/*     */       }
/* 477 */       localStringBuffer.append(this.rsmd.getColumnName(j) + " = :" + j);
/*     */     }
/*     */ 
/* 480 */     localStringBuffer.append(" WHERE ");
/*     */ 
/* 482 */     for (j = 1; j <= this.columnCount; j++)
/*     */     {
/* 484 */       if (j != 1)
/* 485 */         localStringBuffer.append(" AND ");
/* 486 */       if (paramOracleRow.isOriginalNull(j))
/* 487 */         localStringBuffer.append(this.rsmd.getColumnName(j) + " IS NULL ");
/*     */       else {
/* 489 */         localStringBuffer.append(this.rsmd.getColumnName(j) + " = :" + j);
/*     */       }
/*     */     }
/* 492 */     PreparedStatement localPreparedStatement = null;
/*     */     try
/*     */     {
/* 495 */       localPreparedStatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));
/*     */ 
/* 498 */       for (int k = 1; k <= this.columnCount; k++)
/*     */       {
/* 500 */         Object localObject1 = null;
/* 501 */         localObject1 = paramOracleRow.isColumnChanged(k) ? paramOracleRow.getModifiedColumn(k) : paramOracleRow.getColumn(k);
/*     */ 
/* 518 */         if (localObject1 == null)
/* 519 */           localPreparedStatement.setNull(k, this.rsmd.getColumnType(k));
/*     */         else {
/* 521 */           localPreparedStatement.setObject(k, localObject1);
/*     */         }
/*     */       }
/* 524 */       k = 1; for (int m = 1; k <= this.columnCount; k++)
/*     */       {
/* 526 */         if (paramOracleRow.isOriginalNull(k)) {
/*     */           continue;
/*     */         }
/* 529 */         localPreparedStatement.setObject(m + this.columnCount, paramOracleRow.getColumn(k));
/*     */ 
/* 531 */         m++;
/*     */       }
/* 533 */       i = localPreparedStatement.executeUpdate() == 1 ? 1 : 0;
/*     */     }
/*     */     finally {
/* 536 */       if (localPreparedStatement != null) {
/* 537 */         localPreparedStatement.close();
/*     */       }
/*     */     }
/* 540 */     return i;
/*     */   }
/*     */ 
/*     */   private boolean deleteRow(RowSet paramRowSet, OracleRow paramOracleRow)
/*     */     throws SQLException
/*     */   {
/* 565 */     this.deleteStmt.clearParameters();
/* 566 */     for (int i = 1; i <= this.columnCount; i++)
/*     */     {
/* 568 */       if (paramOracleRow.isOriginalNull(i)) {
/* 569 */         return deleteRowWithNull(paramRowSet, paramOracleRow);
/*     */       }
/* 571 */       Object localObject = paramOracleRow.getColumn(i);
/* 572 */       if (localObject == null)
/* 573 */         this.deleteStmt.setNull(i, this.rsmd.getColumnType(i));
/*     */       else {
/* 575 */         this.deleteStmt.setObject(i, localObject);
/*     */       }
/*     */     }
/* 578 */     return this.deleteStmt.executeUpdate() == 1;
/*     */   }
/*     */ 
/*     */   private boolean deleteRowWithNull(RowSet paramRowSet, OracleRow paramOracleRow)
/*     */     throws SQLException
/*     */   {
/* 600 */     int i = 0;
/* 601 */     StringBuffer localStringBuffer = new StringBuffer("DELETE FROM " + getTableName(paramRowSet) + " WHERE ");
/*     */ 
/* 604 */     for (int j = 1; j <= this.columnCount; j++)
/*     */     {
/* 606 */       if (j != 1)
/* 607 */         localStringBuffer.append(" AND ");
/* 608 */       if (paramOracleRow.isOriginalNull(j))
/* 609 */         localStringBuffer.append(this.rsmd.getColumnName(j) + " IS NULL ");
/*     */       else {
/* 611 */         localStringBuffer.append(this.rsmd.getColumnName(j) + " = :" + j);
/*     */       }
/*     */     }
/* 614 */     PreparedStatement localPreparedStatement = null;
/*     */     try
/*     */     {
/* 617 */       localPreparedStatement = this.connection.prepareStatement(localStringBuffer.substring(0, localStringBuffer.length()));
/*     */ 
/* 621 */       int k = 1; for (int m = 1; k <= this.columnCount; k++)
/*     */       {
/* 623 */         if (paramOracleRow.isOriginalNull(k)) {
/*     */           continue;
/*     */         }
/* 626 */         localPreparedStatement.setObject(m++, paramOracleRow.getColumn(k));
/*     */       }
/* 628 */       i = localPreparedStatement.executeUpdate() == 1 ? 1 : 0;
/*     */     }
/*     */     finally {
/* 631 */       if (localPreparedStatement != null) {
/* 632 */         localPreparedStatement.close();
/*     */       }
/*     */     }
/* 635 */     return i;
/*     */   }
/*     */ 
/*     */   public synchronized boolean writeData(RowSetInternal paramRowSetInternal)
/*     */     throws SQLException
/*     */   {
/* 656 */     OracleCachedRowSet localOracleCachedRowSet = (OracleCachedRowSet)paramRowSetInternal;
/* 657 */     this.connection = ((OracleCachedRowSetReader)localOracleCachedRowSet.getReader()).getConnection(paramRowSetInternal);
/*     */ 
/* 675 */     if (this.connection == null)
/* 676 */       throw new SQLException("Unable to get Connection");
/* 677 */     if (this.connection.getAutoCommit())
/* 678 */       this.connection.setAutoCommit(false);
/* 679 */     this.connection.setTransactionIsolation(localOracleCachedRowSet.getTransactionIsolation());
/* 680 */     initSQLStatement(localOracleCachedRowSet);
/* 681 */     if (this.columnCount < 1)
/*     */     {
/* 683 */       this.connection.close();
/* 684 */       return true;
/*     */     }
/* 686 */     boolean bool = localOracleCachedRowSet.getShowDeleted();
/* 687 */     localOracleCachedRowSet.setShowDeleted(true);
/* 688 */     localOracleCachedRowSet.beforeFirst();
/* 689 */     int i = 1;
/* 690 */     int j = 1;
/* 691 */     int k = 1;
/* 692 */     OracleRow localOracleRow = null;
/* 693 */     while (localOracleCachedRowSet.next())
/*     */     {
/* 695 */       if (localOracleCachedRowSet.rowInserted())
/*     */       {
/* 698 */         if (localOracleCachedRowSet.rowDeleted())
/*     */           continue;
/* 700 */         localOracleRow = localOracleCachedRowSet.getCurrentRow();
/*     */ 
/* 702 */         j = (insertRow(localOracleRow)) || (j != 0) ? 1 : 0; continue;
/*     */       }
/* 704 */       if (localOracleCachedRowSet.rowUpdated())
/*     */       {
/* 706 */         localOracleRow = localOracleCachedRowSet.getCurrentRow();
/*     */ 
/* 708 */         i = (updateRow(localOracleCachedRowSet, localOracleRow)) || (i != 0) ? 1 : 0; continue;
/*     */       }
/* 710 */       if (!localOracleCachedRowSet.rowDeleted())
/*     */         continue;
/* 712 */       localOracleRow = localOracleCachedRowSet.getCurrentRow();
/*     */ 
/* 714 */       k = (deleteRow(localOracleCachedRowSet, localOracleRow)) || (k != 0) ? 1 : 0;
/*     */     }
/*     */ 
/* 740 */     if ((i != 0) && (j != 0) && (k != 0))
/*     */     {
/* 745 */       this.connection.commit();
/*     */ 
/* 747 */       localOracleCachedRowSet.setOriginal();
/*     */     }
/*     */     else {
/* 750 */       this.connection.rollback();
/*     */     }
/* 752 */     this.insertStmt.close();
/* 753 */     this.updateStmt.close();
/* 754 */     this.deleteStmt.close();
/*     */ 
/* 758 */     if (!localOracleCachedRowSet.isConnectionStayingOpen())
/*     */     {
/* 760 */       this.connection.close();
/*     */     }
/*     */ 
/* 763 */     localOracleCachedRowSet.setShowDeleted(bool);
/* 764 */     return true;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleCachedRowSetWriter
 * JD-Core Version:    0.6.0
 */