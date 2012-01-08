/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class ReturnParamBinder extends Binder
/*       */ {
/*       */   Binder copyingBinder()
/*       */   {
/* 17151 */     return this;
/*       */   }
/*       */ 
/*       */   ReturnParamBinder()
/*       */   {
/* 17156 */     this.type = 994;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ReturnParamBinder
 * JD-Core Version:    0.6.0
 */