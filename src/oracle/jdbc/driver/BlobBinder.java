/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BlobBinder extends DatumBinder
/*       */ {
/* 16525 */   Binder theBlobCopyingBinder = OraclePreparedStatementReadOnly.theStaticBlobCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16530 */     paramBinder.type = 113;
/* 16531 */     paramBinder.bytelen = 4000;
/*       */   }
/*       */ 
/*       */   BlobBinder()
/*       */   {
/* 16536 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16541 */     return this.theBlobCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BlobBinder
 * JD-Core Version:    0.6.0
 */