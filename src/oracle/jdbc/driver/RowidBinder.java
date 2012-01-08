/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class RowidBinder extends Binder
/*       */ {
/* 16239 */   Binder theRowidCopyingBinder = OraclePreparedStatementReadOnly.theStaticRowidCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16244 */     paramBinder.type = 9;
/* 16245 */     paramBinder.bytelen = 130;
/*       */   }
/*       */ 
/*       */   RowidBinder()
/*       */   {
/* 16250 */     init(this);
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 16259 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 16260 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 16262 */     if (paramBoolean) {
/* 16263 */       arrayOfByte[paramInt1] = null;
/*       */     }
/* 16265 */     if (arrayOfByte1 == null) {
/* 16266 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else {
/* 16269 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 16271 */       int i = arrayOfByte1.length;
/*       */ 
/* 16273 */       System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt6 + 2, i);
/*       */ 
/* 16275 */       paramArrayOfByte[paramInt6] = (byte)(i >> 8);
/* 16276 */       paramArrayOfByte[(paramInt6 + 1)] = (byte)(i & 0xFF);
/* 16277 */       paramArrayOfShort[paramInt8] = (short)(i + 2);
/*       */     }
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16283 */     return this.theRowidCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.RowidBinder
 * JD-Core Version:    0.6.0
 */