/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class OracleTimestampBinder extends DatumBinder
/*       */ {
/* 16105 */   Binder theTimestampCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimestampCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16110 */     paramBinder.type = 180;
/* 16111 */     paramBinder.bytelen = 11;
/*       */   }
/*       */ 
/*       */   OracleTimestampBinder()
/*       */   {
/* 16116 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16121 */     return this.theTimestampCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleTimestampBinder
 * JD-Core Version:    0.6.0
 */