/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class ByteBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 12970 */     byte[] arrayOfByte = paramArrayOfByte;
/* 12971 */     int i = paramInt6 + 1;
/* 12972 */     int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
/* 12973 */     int k = 0;
/*       */ 
/* 12977 */     if (j == 0)
/*       */     {
/* 12979 */       arrayOfByte[i] = -128;
/* 12980 */       k = 1;
/*       */     }
/*       */     else
/*       */     {
/*       */       int m;
/* 12982 */       if (j < 0)
/*       */       {
/* 12984 */         if (-j < 100)
/*       */         {
/* 12986 */           arrayOfByte[i] = 62;
/* 12987 */           arrayOfByte[(i + 1)] = (byte)(101 + j);
/* 12988 */           arrayOfByte[(i + 2)] = 102;
/* 12989 */           k = 3;
/*       */         }
/*       */         else
/*       */         {
/* 12993 */           arrayOfByte[i] = 61;
/* 12994 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 100);
/* 12995 */           m = -j % 100;
/*       */ 
/* 12997 */           if (m != 0)
/*       */           {
/* 12999 */             arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13000 */             arrayOfByte[(i + 3)] = 102;
/* 13001 */             k = 4;
/*       */           }
/*       */           else
/*       */           {
/* 13005 */             arrayOfByte[(i + 2)] = 102;
/* 13006 */             k = 3;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*       */       }
/* 13012 */       else if (j < 100)
/*       */       {
/* 13014 */         arrayOfByte[i] = -63;
/* 13015 */         arrayOfByte[(i + 1)] = (byte)(j + 1);
/* 13016 */         k = 2;
/*       */       }
/*       */       else
/*       */       {
/* 13020 */         arrayOfByte[i] = -62;
/* 13021 */         arrayOfByte[(i + 1)] = (byte)(j / 100 + 1);
/* 13022 */         m = j % 100;
/*       */ 
/* 13024 */         if (m != 0)
/*       */         {
/* 13026 */           arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13027 */           k = 3;
/*       */         }
/*       */         else
/*       */         {
/* 13031 */           arrayOfByte[(i + 2)] = 102;
/* 13032 */           k = 2;
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 13037 */     arrayOfByte[paramInt6] = (byte)k;
/* 13038 */     paramArrayOfShort[paramInt9] = 0;
/* 13039 */     paramArrayOfShort[paramInt8] = (short)(k + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ByteBinder
 * JD-Core Version:    0.6.0
 */