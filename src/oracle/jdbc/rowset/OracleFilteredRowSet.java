/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.rowset.FilteredRowSet;
/*     */ import javax.sql.rowset.Predicate;
/*     */ 
/*     */ public class OracleFilteredRowSet extends OracleWebRowSet
/*     */   implements FilteredRowSet
/*     */ {
/*     */   private Predicate predicate;
/*     */ 
/*     */   public OracleFilteredRowSet()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setFilter(Predicate paramPredicate)
/*     */     throws SQLException
/*     */   {
/*  97 */     this.predicate = paramPredicate;
/*     */   }
/*     */ 
/*     */   public Predicate getFilter()
/*     */   {
/* 115 */     return this.predicate;
/*     */   }
/*     */ 
/*     */   public boolean next()
/*     */     throws SQLException
/*     */   {
/* 141 */     if (this.rowCount <= 0) {
/* 142 */       return false;
/*     */     }
/* 144 */     if (this.presentRow >= this.rowCount) {
/* 145 */       return false;
/*     */     }
/* 147 */     int i = 0;
/*     */     do
/*     */     {
/* 151 */       this.presentRow += 1;
/*     */ 
/* 153 */       if ((this.predicate != null) && ((this.predicate == null) || (!this.predicate.evaluate(this)))) {
/*     */         continue;
/*     */       }
/* 156 */       i = 1;
/* 157 */       break;
/*     */     }
/*     */ 
/* 160 */     while (this.presentRow <= this.rowCount);
/*     */ 
/* 162 */     if (i != 0)
/*     */     {
/* 164 */       notifyCursorMoved();
/* 165 */       return true;
/*     */     }
/*     */ 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean previous()
/*     */     throws SQLException
/*     */   {
/* 196 */     if (this.rowsetType == 1003) {
/* 197 */       throw new SQLException("The RowSet type is TYPE_FORWARD_ONLY");
/*     */     }
/* 199 */     if (this.rowCount <= 0) {
/* 200 */       return false;
/*     */     }
/* 202 */     if (this.presentRow <= 1) {
/* 203 */       return false;
/*     */     }
/* 205 */     int i = 0;
/*     */     do
/*     */     {
/* 209 */       this.presentRow -= 1;
/*     */ 
/* 211 */       if ((this.predicate != null) && ((this.predicate == null) || (!this.predicate.evaluate(this)))) {
/*     */         continue;
/*     */       }
/* 214 */       i = 1;
/* 215 */       break;
/*     */     }
/*     */ 
/* 218 */     while (this.presentRow >= 1);
/*     */ 
/* 220 */     if (i != 0)
/*     */     {
/* 222 */       notifyCursorMoved();
/* 223 */       return true;
/*     */     }
/*     */ 
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean absolute(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 254 */     if (this.rowsetType == 1003) {
/* 255 */       throw new SQLException("The RowSet type is TYPE_FORWARD_ONLY");
/*     */     }
/* 257 */     if ((paramInt == 0) || (Math.abs(paramInt) > this.rowCount)) {
/* 258 */       return false;
/*     */     }
/*     */ 
/* 261 */     int i = paramInt < 0 ? this.rowCount + paramInt + 1 : paramInt;
/*     */ 
/* 263 */     int j = 0;
/* 264 */     this.presentRow = 0;
/*     */ 
/* 267 */     while ((j < i) && (this.presentRow <= this.rowCount))
/*     */     {
/* 269 */       if (next()) {
/* 270 */         j++; continue;
/*     */       }
/* 272 */       return false;
/*     */     }
/*     */ 
/* 275 */     if (j == i)
/*     */     {
/* 277 */       notifyCursorMoved();
/* 278 */       return true;
/*     */     }
/*     */ 
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */   protected void checkAndFilterObject(int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 288 */     if ((this.predicate != null) && (!this.predicate.evaluate(paramObject, paramInt)))
/* 289 */       throw new SQLException("The object does not satisfy filtering criterion");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleFilteredRowSet
 * JD-Core Version:    0.6.0
 */