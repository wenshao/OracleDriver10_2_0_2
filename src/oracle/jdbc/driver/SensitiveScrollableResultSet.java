/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class SensitiveScrollableResultSet extends ScrollableResultSet
/*     */ {
/*     */   int beginLastFetchedIndex;
/*     */   int endLastFetchedIndex;
/* 301 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   SensitiveScrollableResultSet(ScrollRsetStatement paramScrollRsetStatement, OracleResultSetImpl paramOracleResultSetImpl, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*  37 */     super(paramScrollRsetStatement, paramOracleResultSetImpl, paramInt1, paramInt2);
/*     */ 
/*  42 */     int i = paramOracleResultSetImpl.getValidRows();
/*     */ 
/*  44 */     if (i > 0)
/*     */     {
/*  46 */       this.beginLastFetchedIndex = 1;
/*  47 */       this.endLastFetchedIndex = i;
/*     */     }
/*     */     else
/*     */     {
/*  51 */       this.beginLastFetchedIndex = 0;
/*  52 */       this.endLastFetchedIndex = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean next()
/*     */     throws SQLException
/*     */   {
/*  69 */     synchronized (this.connection)
/*     */     {
/*  71 */       synchronized (this)
/*     */       {
/*  73 */         if (super.next())
/*     */         {
/*  75 */           handle_refetch();
/*     */ 
/*  77 */           return true;
/*     */         }
/*     */ 
/*  81 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean first()
/*     */     throws SQLException
/*     */   {
/*  89 */     synchronized (this.connection)
/*     */     {
/*  91 */       synchronized (this)
/*     */       {
/*  93 */         if (super.first())
/*     */         {
/*  95 */           handle_refetch();
/*     */ 
/*  97 */           return true;
/*     */         }
/*     */ 
/* 101 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean last()
/*     */     throws SQLException
/*     */   {
/* 109 */     synchronized (this.connection)
/*     */     {
/* 111 */       synchronized (this)
/*     */       {
/* 113 */         if (super.last())
/*     */         {
/* 115 */           handle_refetch();
/*     */ 
/* 117 */           return true;
/*     */         }
/*     */ 
/* 121 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean absolute(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 129 */     synchronized (this.connection)
/*     */     {
/* 131 */       synchronized (this)
/*     */       {
/* 133 */         if (super.absolute(paramInt))
/*     */         {
/* 135 */           handle_refetch();
/*     */ 
/* 137 */           return true;
/*     */         }
/*     */ 
/* 141 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean relative(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 149 */     synchronized (this.connection)
/*     */     {
/* 151 */       synchronized (this)
/*     */       {
/* 153 */         if (super.relative(paramInt))
/*     */         {
/* 155 */           handle_refetch();
/*     */ 
/* 157 */           return true;
/*     */         }
/*     */ 
/* 161 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean previous()
/*     */     throws SQLException
/*     */   {
/* 169 */     synchronized (this.connection)
/*     */     {
/* 171 */       synchronized (this)
/*     */       {
/* 173 */         if (super.previous())
/*     */         {
/* 175 */           handle_refetch();
/*     */ 
/* 177 */           return true;
/*     */         }
/*     */ 
/* 181 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void refreshRow()
/*     */     throws SQLException
/*     */   {
/* 189 */     synchronized (this.connection)
/*     */     {
/* 191 */       synchronized (this)
/*     */       {
/* 197 */         if (!isValidRow(this.currentRow)) {
/* 198 */           DatabaseError.throwSqlException(11);
/*     */         }
/* 200 */         int i = getFetchDirection();
/* 201 */         int j = 0;
/*     */         try
/*     */         {
/* 205 */           j = refreshRowsInCache(this.currentRow, getFetchSize(), i);
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 209 */           DatabaseError.throwSqlException(localSQLException, 90, "Unsupported syntax for refreshRow()");
/*     */         }
/*     */ 
/* 213 */         if (j != 0)
/*     */         {
/* 215 */           this.beginLastFetchedIndex = this.currentRow;
/*     */ 
/* 220 */           this.endLastFetchedIndex = (this.currentRow + j - 1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized int removeRowInCache(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 238 */     int i = super.removeRowInCache(paramInt);
/*     */ 
/* 240 */     if (i != 0)
/*     */     {
/* 242 */       if ((paramInt >= this.beginLastFetchedIndex) && (paramInt <= this.endLastFetchedIndex) && (this.beginLastFetchedIndex != this.endLastFetchedIndex))
/*     */       {
/* 245 */         this.endLastFetchedIndex -= 1;
/*     */       }
/*     */       else
/*     */       {
/* 260 */         this.beginLastFetchedIndex = (this.endLastFetchedIndex = 0);
/*     */       }
/*     */     }
/* 263 */     return i;
/*     */   }
/*     */ 
/*     */   private boolean handle_refetch()
/*     */     throws SQLException
/*     */   {
/* 272 */     synchronized (this.connection)
/*     */     {
/* 274 */       synchronized (this)
/*     */       {
/* 278 */         if (((this.currentRow >= this.beginLastFetchedIndex) && (this.currentRow <= this.endLastFetchedIndex)) || ((this.currentRow >= this.endLastFetchedIndex) && (this.currentRow <= this.beginLastFetchedIndex)))
/*     */         {
/* 284 */           return false;
/*     */         }
/*     */ 
/* 290 */         refreshRow();
/*     */ 
/* 292 */         return true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.SensitiveScrollableResultSet
 * JD-Core Version:    0.6.0
 */