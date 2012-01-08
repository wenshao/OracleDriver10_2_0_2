/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class IntervalYMBinder extends DatumBinder
/*       */ {
/* 16414 */   Binder theIntervalYMCopyingBinder = OraclePreparedStatementReadOnly.theStaticIntervalYMCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16419 */     paramBinder.type = 182;
/* 16420 */     paramBinder.bytelen = 5;
/*       */   }
/*       */ 
/*       */   IntervalYMBinder()
/*       */   {
/* 16425 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16430 */     return this.theIntervalYMCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.IntervalYMBinder
 * JD-Core Version:    0.6.0
 */