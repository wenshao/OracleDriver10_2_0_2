/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class StringBinder extends VarcharBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15110 */     String[] arrayOfString = paramOraclePreparedStatement.parameterString[paramInt3];
/* 15111 */     String str = arrayOfString[paramInt1];
/*       */ 
/* 15113 */     if (paramBoolean) {
/* 15114 */       arrayOfString[paramInt1] = null;
/*       */     }
/* 15116 */     if (str == null)
/*       */     {
/* 15118 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15122 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15124 */       int i = str.length();
/*       */ 
/* 15126 */       str.getChars(0, i, paramArrayOfChar, paramInt7 + 1);
/*       */ 
/* 15128 */       i <<= 1;
/* 15129 */       paramArrayOfChar[paramInt7] = (char)i;
/*       */ 
/* 15131 */       if (i > 65532)
/* 15132 */         paramArrayOfShort[paramInt8] = -2;
/*       */       else
/* 15134 */         paramArrayOfShort[paramInt8] = (short)(i + 2);
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.StringBinder
 * JD-Core Version:    0.6.0
 */