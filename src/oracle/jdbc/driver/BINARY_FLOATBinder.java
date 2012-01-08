/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BINARY_FLOATBinder extends DatumBinder
/*       */ {
/* 15921 */   Binder theBINARY_FLOATCopyingBinder = OraclePreparedStatementReadOnly.theStaticBINARY_FLOATCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15926 */     paramBinder.type = 100;
/* 15927 */     paramBinder.bytelen = 4;
/*       */   }
/*       */ 
/*       */   BINARY_FLOATBinder()
/*       */   {
/* 15932 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15937 */     return this.theBINARY_FLOATCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BINARY_FLOATBinder
 * JD-Core Version:    0.6.0
 */