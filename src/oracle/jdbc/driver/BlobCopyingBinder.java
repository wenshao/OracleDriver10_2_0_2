/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BlobCopyingBinder extends ByteCopyingBinder
/*       */ {
/*       */   BlobCopyingBinder()
/*       */   {
/* 16550 */     BlobBinder.init(this);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BlobCopyingBinder
 * JD-Core Version:    0.6.0
 */