/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class RowidCopyingBinder extends ByteCopyingBinder
/*       */ {
/*       */   RowidCopyingBinder()
/*       */   {
/* 16329 */     RowidBinder.init(this);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RowidCopyingBinder
 * JD-Core Version:    0.6.0
 */