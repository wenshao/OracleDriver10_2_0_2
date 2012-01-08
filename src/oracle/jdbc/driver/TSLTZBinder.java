/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class TSLTZBinder extends DatumBinder
/*       */ {
/* 16184 */   Binder theTSLTZCopyingBinder = OraclePreparedStatementReadOnly.theStaticTSLTZCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16189 */     paramBinder.type = 231;
/* 16190 */     paramBinder.bytelen = 11;
/*       */   }
/*       */ 
/*       */   TSLTZBinder()
/*       */   {
/* 16195 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16200 */     return this.theTSLTZCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TSLTZBinder
 * JD-Core Version:    0.6.0
 */