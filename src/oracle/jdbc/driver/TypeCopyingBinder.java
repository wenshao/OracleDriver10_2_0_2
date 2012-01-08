/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class TypeCopyingBinder extends Binder
/*       */ {
/*       */   Binder copyingBinder()
/*       */   {
/* 16797 */     return this;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 16806 */     if (paramInt2 == 0)
/*       */     {
/* 16808 */       paramOraclePreparedStatement.parameterDatum[0][paramInt1] = paramOraclePreparedStatement.lastBoundTypeBytes[paramInt1];
/*       */ 
/* 16810 */       paramOraclePreparedStatement.parameterOtype[0][paramInt1] = paramOraclePreparedStatement.lastBoundTypeOtypes[paramInt1];
/*       */     }
/*       */     else
/*       */     {
/* 16815 */       paramOraclePreparedStatement.parameterDatum[paramInt2][paramInt1] = paramOraclePreparedStatement.parameterDatum[(paramInt2 - 1)][paramInt1];
/*       */ 
/* 16817 */       paramOraclePreparedStatement.parameterOtype[paramInt2][paramInt1] = paramOraclePreparedStatement.parameterOtype[(paramInt2 - 1)][paramInt1];
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TypeCopyingBinder
 * JD-Core Version:    0.6.0
 */