/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class IntervalDSBinder extends DatumBinder
/*       */ {
/* 16359 */   Binder theIntervalDSCopyingBinder = OraclePreparedStatementReadOnly.theStaticIntervalDSCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16364 */     paramBinder.type = 183;
/* 16365 */     paramBinder.bytelen = 11;
/*       */   }
/*       */ 
/*       */   IntervalDSBinder()
/*       */   {
/* 16370 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16375 */     return this.theIntervalDSCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.IntervalDSBinder
 * JD-Core Version:    0.6.0
 */