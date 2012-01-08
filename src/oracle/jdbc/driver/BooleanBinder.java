/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BooleanBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 12933 */     byte[] arrayOfByte = paramArrayOfByte;
/* 12934 */     int i = paramInt6 + 1;
/* 12935 */     int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
/* 12936 */     int k = 0;
/*       */ 
/* 12938 */     if (j != 0)
/*       */     {
/* 12940 */       arrayOfByte[i] = -63;
/* 12941 */       arrayOfByte[(i + 1)] = 2;
/* 12942 */       k = 2;
/*       */     }
/*       */     else
/*       */     {
/* 12946 */       arrayOfByte[i] = -128;
/* 12947 */       k = 1;
/*       */     }
/*       */ 
/* 12950 */     arrayOfByte[paramInt6] = (byte)k;
/* 12951 */     paramArrayOfShort[paramInt9] = 0;
/* 12952 */     paramArrayOfShort[paramInt8] = (short)(k + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BooleanBinder
 * JD-Core Version:    0.6.0
 */