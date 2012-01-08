/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class SetCHARBinder extends Binder
/*       */ {
/* 15152 */   Binder theSetCHARCopyingBinder = OraclePreparedStatementReadOnly.theStaticSetCHARCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15157 */     paramBinder.type = 996;
/* 15158 */     paramBinder.bytelen = 0;
/*       */   }
/*       */ 
/*       */   SetCHARBinder()
/*       */   {
/* 15163 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15168 */     return this.theSetCHARCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15177 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 15178 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 15180 */     if (paramBoolean) {
/* 15181 */       arrayOfByte[paramInt1] = null;
/*       */     }
/* 15183 */     if (arrayOfByte1 == null)
/*       */     {
/* 15185 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15189 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15191 */       int i = arrayOfByte1.length;
/*       */ 
/* 15193 */       paramArrayOfChar[paramInt7] = (char)i;
/*       */ 
/* 15195 */       if (i > 65532)
/* 15196 */         paramArrayOfShort[paramInt8] = -2;
/*       */       else {
/* 15198 */         paramArrayOfShort[paramInt8] = (short)(i + 2);
/*       */       }
/* 15200 */       int j = paramInt7 + (i >> 1);
/*       */ 
/* 15202 */       if (i % 2 == 1) {
/* 15203 */         i--; paramArrayOfChar[(j + 1)] = (char)(arrayOfByte1[i] << 8);
/*       */       }
/* 15205 */       while (i > 0)
/*       */       {
/* 15207 */         i -= 2;
/* 15208 */         paramArrayOfChar[(j--)] = (char)(arrayOfByte1[i] << 8 | arrayOfByte1[(i + 1)] & 0xFF);
/*       */       }
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.SetCHARBinder
 * JD-Core Version:    0.6.0
 */