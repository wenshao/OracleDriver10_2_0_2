/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class OracleDateBinder extends DatumBinder
/*       */ {
/* 16082 */   Binder theDateCopyingBinder = OraclePreparedStatementReadOnly.theStaticDateCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16087 */     paramBinder.type = 12;
/* 16088 */     paramBinder.bytelen = 7;
/*       */   }
/*       */ 
/*       */   OracleDateBinder()
/*       */   {
/* 16093 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16098 */     return this.theDateCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleDateBinder
 * JD-Core Version:    0.6.0
 */