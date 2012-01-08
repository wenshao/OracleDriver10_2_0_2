/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class LongBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 13546 */     byte[] arrayOfByte = paramArrayOfByte;
/* 13547 */     int i = paramInt6 + 1;
/* 13548 */     long l = paramOraclePreparedStatement.parameterLong[paramInt3][paramInt1];
/* 13549 */     int j = setLongInternal(arrayOfByte, i, l);
/*       */ 
/* 13551 */     arrayOfByte[paramInt6] = (byte)j;
/* 13552 */     paramArrayOfShort[paramInt9] = 0;
/* 13553 */     paramArrayOfShort[paramInt8] = (short)(j + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LongBinder
 * JD-Core Version:    0.6.0
 */