/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class NamedTypeBinder extends TypeBinder
/*       */ {
/* 16826 */   Binder theNamedTypeCopyingBinder = OraclePreparedStatementReadOnly.theStaticNamedTypeCopyingBinder;
/*       */ 
/*       */   NamedTypeBinder()
/*       */   {
/* 16832 */     init(this);
/*       */   }
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16837 */     paramBinder.type = 109;
/* 16838 */     paramBinder.bytelen = 24;
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16843 */     return this.theNamedTypeCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.NamedTypeBinder
 * JD-Core Version:    0.6.0
 */