/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class RefTypeBinder extends TypeBinder
/*       */ {
/* 16882 */   Binder theRefTypeCopyingBinder = OraclePreparedStatementReadOnly.theStaticRefTypeCopyingBinder;
/*       */ 
/*       */   RefTypeBinder()
/*       */   {
/* 16888 */     init(this);
/*       */   }
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16893 */     paramBinder.type = 111;
/* 16894 */     paramBinder.bytelen = 24;
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16899 */     return this.theRefTypeCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RefTypeBinder
 * JD-Core Version:    0.6.0
 */