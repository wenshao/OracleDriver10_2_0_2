/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BfileBinder extends DatumBinder
/*       */ {
/* 16470 */   Binder theBfileCopyingBinder = OraclePreparedStatementReadOnly.theStaticBfileCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16475 */     paramBinder.type = 114;
/* 16476 */     paramBinder.bytelen = 530;
/*       */   }
/*       */ 
/*       */   BfileBinder()
/*       */   {
/* 16481 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16486 */     return this.theBfileCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BfileBinder
 * JD-Core Version:    0.6.0
 */