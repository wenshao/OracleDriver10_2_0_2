/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.SQLException;
/*       */ import oracle.sql.Datum;
/*       */ 
/*       */ class PlsqlIbtBinder extends Binder
/*       */ {
/* 16938 */   Binder thePlsqlIbtCopyingBinder = OraclePreparedStatementReadOnly.theStaticPlsqlIbtCopyingBinder;
/*       */ 
/*       */   PlsqlIbtBinder()
/*       */   {
/* 16943 */     init(this);
/*       */   }
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 16948 */     paramBinder.type = 998;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */     throws SQLException
/*       */   {
/* 16957 */     PlsqlIbtBindInfo localPlsqlIbtBindInfo = paramOraclePreparedStatement.parameterPlsqlIbt[paramInt3][paramInt1];
/*       */ 
/* 16959 */     if (paramBoolean) {
/* 16960 */       paramOraclePreparedStatement.parameterPlsqlIbt[paramInt3][paramInt1] = null;
/*       */     }
/* 16962 */     int i = localPlsqlIbtBindInfo.ibtValueIndex;
/*       */     int j;
/* 16964 */     switch (localPlsqlIbtBindInfo.element_internal_type)
/*       */     {
/*       */     case 9:
/* 16968 */       for (j = 0; j < localPlsqlIbtBindInfo.curLen; )
/*       */       {
/* 16970 */         int k = 0;
/* 16971 */         String str = (String)localPlsqlIbtBindInfo.arrayData[j];
/*       */ 
/* 16973 */         if (str != null)
/*       */         {
/* 16975 */           k = str.length();
/*       */ 
/* 16977 */           if (k > localPlsqlIbtBindInfo.elemMaxLen - 1) {
/* 16978 */             k = localPlsqlIbtBindInfo.elemMaxLen - 1;
/*       */           }
/* 16980 */           str.getChars(0, k, paramOraclePreparedStatement.ibtBindChars, i + 1);
/*       */ 
/* 16982 */           paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = 0;
/* 16983 */           k <<= 1;
/* 16984 */           paramOraclePreparedStatement.ibtBindChars[i] = (char)k;
/*       */ 
/* 16987 */           paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtLengthIndex + j)] = (k == 0 ? 3 : (short)(k + 2));
/*       */         }
/*       */         else
/*       */         {
/* 16993 */           paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = -1;
/*       */         }
/* 16995 */         i += localPlsqlIbtBindInfo.elemMaxLen;
/*       */ 
/* 16968 */         j++; continue;
/*       */ 
/* 17002 */         for (j = 0; j < localPlsqlIbtBindInfo.curLen; )
/*       */         {
/* 17004 */           byte[] arrayOfByte = null;
/*       */ 
/* 17006 */           if (localPlsqlIbtBindInfo.arrayData[j] != null) {
/* 17007 */             arrayOfByte = ((Datum)localPlsqlIbtBindInfo.arrayData[j]).getBytes();
/*       */           }
/* 17009 */           if (arrayOfByte == null)
/*       */           {
/* 17011 */             paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = -1;
/*       */           }
/*       */           else
/*       */           {
/* 17015 */             paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtIndicatorIndex + j)] = 0;
/* 17016 */             paramOraclePreparedStatement.ibtBindIndicators[(localPlsqlIbtBindInfo.ibtLengthIndex + j)] = (short)(arrayOfByte.length + 1);
/*       */ 
/* 17018 */             paramOraclePreparedStatement.ibtBindBytes[i] = (byte)arrayOfByte.length;
/*       */ 
/* 17020 */             System.arraycopy(arrayOfByte, 0, paramOraclePreparedStatement.ibtBindBytes, i + 1, arrayOfByte.length);
/*       */           }
/*       */ 
/* 17024 */           i += localPlsqlIbtBindInfo.elemMaxLen;
/*       */ 
/* 17002 */           j++; continue;
/*       */ 
/* 17030 */           DatabaseError.throwSqlException(97);
/*       */         }
/*       */       }case 6:
/*       */     }
/*       */   }
/*       */ 
/*       */   Binder copyingBinder() {
/* 17036 */     return this.thePlsqlIbtCopyingBinder;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.PlsqlIbtBinder
 * JD-Core Version:    0.6.0
 */