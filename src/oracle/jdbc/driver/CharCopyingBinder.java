/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class CharCopyingBinder extends Binder
/*       */ {
/*       */   Binder copyingBinder()
/*       */   {
/* 15004 */     return this;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/*       */     char[] arrayOfChar;
/*       */     int i;
/*       */     int j;
/* 15017 */     if (paramInt2 == 0)
/*       */     {
/* 15019 */       arrayOfChar = paramOraclePreparedStatement.lastBoundChars;
/* 15020 */       i = paramOraclePreparedStatement.lastBoundCharOffsets[paramInt1];
/* 15021 */       paramArrayOfShort[paramInt9] = paramOraclePreparedStatement.lastBoundInds[paramInt1];
/* 15022 */       paramArrayOfShort[paramInt8] = paramOraclePreparedStatement.lastBoundLens[paramInt1];
/*       */ 
/* 15024 */       if ((arrayOfChar == paramArrayOfChar) && (i == paramInt7)) {
/* 15025 */         return;
/*       */       }
/* 15027 */       j = paramOraclePreparedStatement.lastBoundCharLens[paramInt1];
/*       */ 
/* 15029 */       if (j > paramInt5)
/* 15030 */         j = paramInt5;
/*       */     }
/*       */     else
/*       */     {
/* 15034 */       arrayOfChar = paramArrayOfChar;
/* 15035 */       i = paramInt7 - paramInt5;
/* 15036 */       paramArrayOfShort[paramInt9] = paramArrayOfShort[(paramInt9 - 1)];
/* 15037 */       paramArrayOfShort[paramInt8] = paramArrayOfShort[(paramInt8 - 1)];
/* 15038 */       j = paramInt5;
/*       */     }
/*       */ 
/* 15041 */     System.arraycopy(arrayOfChar, i, paramArrayOfChar, paramInt7, j);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.CharCopyingBinder
 * JD-Core Version:    0.6.0
 */