/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ class TableClass extends Hashtable
/*     */ {
/* 634 */   private Vector v = new Vector();
/*     */ 
/*     */   public Object put(Object paramObject, Integer paramInteger)
/*     */   {
/* 610 */     if (this.v.size() < paramInteger.intValue() + 1) {
/* 611 */       this.v.setSize(paramInteger.intValue() + 1);
/*     */     }
/* 613 */     if (this.v.elementAt(paramInteger.intValue()) != null) {
/* 614 */       return super.get(paramObject);
/*     */     }
/*     */ 
/* 617 */     super.put(paramObject, paramInteger);
/* 618 */     this.v.setElementAt(paramObject, paramInteger.intValue());
/* 619 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getKey(int paramInt)
/*     */   {
/* 625 */     return this.v.elementAt(paramInt);
/*     */   }
/*     */ 
/*     */   public void dispTable()
/*     */   {
/* 631 */     for (int i = 0; i < this.v.size(); i++)
/* 632 */       System.out.println(i + "   " + this.v.elementAt(i));
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TableClass
 * JD-Core Version:    0.6.0
 */