/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class VarcharBinder extends Binder
/*       */ {
/* 15057 */   Binder theVarcharCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarcharCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15062 */     paramBinder.type = 9;
/* 15063 */     paramBinder.bytelen = 0;
/*       */   }
/*       */ 
/*       */   VarcharBinder()
/*       */   {
/* 15068 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15073 */     return this.theVarcharCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.VarcharBinder
 * JD-Core Version:    0.6.0
 */