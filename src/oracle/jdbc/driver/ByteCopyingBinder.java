/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class ByteCopyingBinder extends Binder
/*       */ {
/*       */   Binder copyingBinder()
/*       */   {
/* 11712 */     return this;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/*       */     byte[] arrayOfByte;
/*       */     int i;
/*       */     int j;
/* 11725 */     if (paramInt2 == 0)
/*       */     {
/* 11727 */       arrayOfByte = paramOraclePreparedStatement.lastBoundBytes;
/* 11728 */       i = paramOraclePreparedStatement.lastBoundByteOffsets[paramInt1];
/* 11729 */       paramArrayOfShort[paramInt9] = paramOraclePreparedStatement.lastBoundInds[paramInt1];
/* 11730 */       paramArrayOfShort[paramInt8] = paramOraclePreparedStatement.lastBoundLens[paramInt1];
/*       */ 
/* 11732 */       if ((arrayOfByte == paramArrayOfByte) && (i == paramInt6)) {
/* 11733 */         return;
/*       */       }
/* 11735 */       j = paramOraclePreparedStatement.lastBoundByteLens[paramInt1];
/*       */ 
/* 11737 */       if (j > paramInt4)
/* 11738 */         j = paramInt4;
/*       */     }
/*       */     else
/*       */     {
/* 11742 */       arrayOfByte = paramArrayOfByte;
/* 11743 */       i = paramInt6 - paramInt4;
/* 11744 */       paramArrayOfShort[paramInt9] = paramArrayOfShort[(paramInt9 - 1)];
/* 11745 */       paramArrayOfShort[paramInt8] = paramArrayOfShort[(paramInt8 - 1)];
/* 11746 */       j = paramInt4;
/*       */     }
/*       */ 
/* 11749 */     System.arraycopy(arrayOfByte, i, paramArrayOfByte, paramInt6, j);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ByteCopyingBinder
 * JD-Core Version:    0.6.0
 */