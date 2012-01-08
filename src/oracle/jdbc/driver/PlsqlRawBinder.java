/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class PlsqlRawBinder extends DatumBinder
/*       */ {
/* 16667 */   Binder thePlsqlRawCopyingBinder = OraclePreparedStatementReadOnly.theStaticPlsqlRawCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16672 */     paramBinder.type = 23;
/* 16673 */     paramBinder.bytelen = 32767;
/*       */   }
/*       */ 
/*       */   PlsqlRawBinder()
/*       */   {
/* 16678 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16683 */     return this.thePlsqlRawCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.PlsqlRawBinder
 * JD-Core Version:    0.6.0
 */