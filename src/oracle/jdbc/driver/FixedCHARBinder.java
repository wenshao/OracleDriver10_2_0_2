/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class FixedCHARBinder extends Binder
/*       */ {
/* 15300 */   Binder theFixedCHARCopyingBinder = OraclePreparedStatementReadOnly.theStaticFixedCHARCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15305 */     paramBinder.type = 96;
/* 15306 */     paramBinder.bytelen = 0;
/*       */   }
/*       */ 
/*       */   FixedCHARBinder()
/*       */   {
/* 15311 */     init(this);
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15320 */     String[] arrayOfString = paramOraclePreparedStatement.parameterString[paramInt3];
/* 15321 */     String str = arrayOfString[paramInt1];
/*       */ 
/* 15323 */     if (paramBoolean) {
/* 15324 */       arrayOfString[paramInt1] = null;
/*       */     }
/* 15326 */     if (str == null)
/*       */     {
/* 15328 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15332 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15334 */       int i = str.length();
/*       */ 
/* 15336 */       str.getChars(0, i, paramArrayOfChar, paramInt7);
/*       */ 
/* 15338 */       i <<= 1;
/*       */ 
/* 15340 */       if (i > 65534) {
/* 15341 */         i = 65534;
/*       */       }
/* 15343 */       paramArrayOfShort[paramInt8] = (short)i;
/*       */     }
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15349 */     return this.theFixedCHARCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.FixedCHARBinder
 * JD-Core Version:    0.6.0
 */