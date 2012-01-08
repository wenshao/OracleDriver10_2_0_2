/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class TimestampCopyingBinder extends ByteCopyingBinder
/*       */ {
/*       */   TimestampCopyingBinder()
/*       */   {
/* 15884 */     TimestampBinder.init(this);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TimestampCopyingBinder
 * JD-Core Version:    0.6.0
 */