/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class T4CRowidNullBinder extends T4CRowidBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 17209 */     paramArrayOfShort[paramInt9] = -1;
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 17214 */     return this;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CRowidNullBinder
 * JD-Core Version:    0.6.0
 */