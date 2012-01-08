/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class TSTZBinder extends DatumBinder
/*       */ {
/* 16128 */   Binder theTSTZCopyingBinder = OraclePreparedStatementReadOnly.theStaticTSTZCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16133 */     paramBinder.type = 181;
/* 16134 */     paramBinder.bytelen = 13;
/*       */   }
/*       */ 
/*       */   TSTZBinder()
/*       */   {
/* 16139 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16144 */     return this.theTSTZCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TSTZBinder
 * JD-Core Version:    0.6.0
 */