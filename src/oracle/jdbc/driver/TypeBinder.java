/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class TypeBinder extends Binder
/*       */ {
/*       */   public static final int BYTELEN = 24;
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 16782 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 16783 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 16785 */     if (arrayOfByte1 == null)
/* 16786 */       paramArrayOfShort[paramInt9] = -1;
/*       */     else
/* 16788 */       paramArrayOfShort[paramInt9] = 0;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TypeBinder
 * JD-Core Version:    0.6.0
 */