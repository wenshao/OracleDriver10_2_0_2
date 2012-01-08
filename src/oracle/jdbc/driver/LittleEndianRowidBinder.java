/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class LittleEndianRowidBinder extends RowidBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 16301 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 16302 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 16304 */     if (paramBoolean) {
/* 16305 */       arrayOfByte[paramInt1] = null;
/*       */     }
/* 16307 */     if (arrayOfByte1 == null) {
/* 16308 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else {
/* 16311 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 16313 */       int i = arrayOfByte1.length;
/*       */ 
/* 16315 */       System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt6 + 2, i);
/*       */ 
/* 16317 */       paramArrayOfByte[(paramInt6 + 1)] = (byte)(i >> 8);
/* 16318 */       paramArrayOfByte[paramInt6] = (byte)(i & 0xFF);
/* 16319 */       paramArrayOfShort[paramInt8] = (short)(i + 2);
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LittleEndianRowidBinder
 * JD-Core Version:    0.6.0
 */