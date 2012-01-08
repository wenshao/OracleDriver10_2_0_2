/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class OracleNumberBinder extends DatumBinder
/*       */ {
/* 16032 */   Binder theVarnumCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarnumCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16037 */     paramBinder.type = 6;
/* 16038 */     paramBinder.bytelen = 22;
/*       */   }
/*       */ 
/*       */   OracleNumberBinder()
/*       */   {
/* 16043 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 16048 */     return this.theVarnumCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 16057 */     byte[][] arrayOfByte = paramOraclePreparedStatement.parameterDatum[paramInt3];
/* 16058 */     byte[] arrayOfByte1 = arrayOfByte[paramInt1];
/*       */ 
/* 16060 */     if (paramBoolean) {
/* 16061 */       arrayOfByte[paramInt1] = null;
/*       */     }
/* 16063 */     if (arrayOfByte1 == null)
/*       */     {
/* 16065 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 16069 */       paramArrayOfShort[paramInt9] = 0;
/* 16070 */       paramArrayOfByte[paramInt6] = (byte)arrayOfByte1.length;
/*       */ 
/* 16072 */       System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt6 + 1, arrayOfByte1.length);
/*       */ 
/* 16074 */       paramArrayOfShort[paramInt8] = (short)(arrayOfByte1.length + 1);
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleNumberBinder
 * JD-Core Version:    0.6.0
 */