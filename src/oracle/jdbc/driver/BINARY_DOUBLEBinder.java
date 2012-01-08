/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BINARY_DOUBLEBinder extends DatumBinder
/*       */ {
/* 15977 */   Binder theBINARY_DOUBLECopyingBinder = OraclePreparedStatementReadOnly.theStaticBINARY_DOUBLECopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15982 */     paramBinder.type = 101;
/* 15983 */     paramBinder.bytelen = 8;
/*       */   }
/*       */ 
/*       */   BINARY_DOUBLEBinder()
/*       */   {
/* 15988 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15993 */     return this.theBINARY_DOUBLECopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BINARY_DOUBLEBinder
 * JD-Core Version:    0.6.0
 */