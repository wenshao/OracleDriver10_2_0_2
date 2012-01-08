/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class ClobBinder extends DatumBinder
/*       */ {
/* 16580 */   Binder theClobCopyingBinder = OraclePreparedStatementReadOnly.theStaticClobCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16585 */     paramBinder.type = 112;
/* 16586 */     paramBinder.bytelen = 4000;
/*       */   }
/*       */ 
/*       */   ClobBinder()
/*       */   {
/* 16591 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16596 */     return this.theClobCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ClobBinder
 * JD-Core Version:    0.6.0
 */