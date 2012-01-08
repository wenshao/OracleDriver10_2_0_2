/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class LittleEndianSetCHARBinder extends SetCHARBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15228 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 15229 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 15231 */     if (paramBoolean) {
/* 15232 */       arrayOfByte[paramInt1] = null;
/*       */     }
/* 15234 */     if (arrayOfByte1 == null)
/*       */     {
/* 15236 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15240 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15242 */       int i = arrayOfByte1.length;
/*       */ 
/* 15244 */       paramArrayOfChar[paramInt7] = (char)i;
/*       */ 
/* 15246 */       if (i > 65532)
/* 15247 */         paramArrayOfShort[paramInt8] = -2;
/*       */       else {
/* 15249 */         paramArrayOfShort[paramInt8] = (short)(i + 2);
/*       */       }
/* 15251 */       int j = paramInt7 + (i >> 1);
/*       */ 
/* 15253 */       if (i % 2 == 1) {
/* 15254 */         i--; paramArrayOfChar[(j + 1)] = (char)(arrayOfByte1[i] & 0xFF);
/*       */       }
/* 15256 */       while (i > 0)
/*       */       {
/* 15258 */         i -= 2;
/* 15259 */         paramArrayOfChar[(j--)] = (char)(arrayOfByte1[i] & 0xFF | arrayOfByte1[(i + 1)] << 8);
/*       */       }
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.LittleEndianSetCHARBinder
 * JD-Core Version:    0.6.0
 */