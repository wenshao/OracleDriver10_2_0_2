/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class ShortBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 13057 */     byte[] arrayOfByte = paramArrayOfByte;
/* 13058 */     int i = paramInt6 + 1;
/* 13059 */     int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
/* 13060 */     int k = 0;
/*       */ 
/* 13064 */     if (j == 0)
/*       */     {
/* 13066 */       arrayOfByte[i] = -128;
/* 13067 */       k = 1;
/*       */     }
/*       */     else
/*       */     {
/*       */       int m;
/* 13069 */       if (j < 0)
/*       */       {
/* 13071 */         if (-j < 100)
/*       */         {
/* 13073 */           arrayOfByte[i] = 62;
/* 13074 */           arrayOfByte[(i + 1)] = (byte)(101 + j);
/* 13075 */           arrayOfByte[(i + 2)] = 102;
/* 13076 */           k = 3;
/*       */         }
/* 13078 */         else if (-j < 10000)
/*       */         {
/* 13080 */           arrayOfByte[i] = 61;
/* 13081 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 100);
/* 13082 */           m = -j % 100;
/*       */ 
/* 13084 */           if (m != 0)
/*       */           {
/* 13086 */             arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13087 */             arrayOfByte[(i + 3)] = 102;
/* 13088 */             k = 4;
/*       */           }
/*       */           else
/*       */           {
/* 13092 */             arrayOfByte[(i + 2)] = 102;
/* 13093 */             k = 3;
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/* 13098 */           arrayOfByte[i] = 60;
/* 13099 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 10000);
/* 13100 */           m = -j % 100;
/*       */ 
/* 13102 */           if (m != 0)
/*       */           {
/* 13104 */             arrayOfByte[(i + 2)] = (byte)(101 - -j % 10000 / 100);
/* 13105 */             arrayOfByte[(i + 3)] = (byte)(101 - m);
/* 13106 */             arrayOfByte[(i + 4)] = 102;
/* 13107 */             k = 5;
/*       */           }
/*       */           else
/*       */           {
/* 13111 */             m = -j % 10000 / 100;
/*       */ 
/* 13113 */             if (m != 0)
/*       */             {
/* 13115 */               arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13116 */               arrayOfByte[(i + 3)] = 102;
/* 13117 */               k = 4;
/*       */             }
/*       */             else
/*       */             {
/* 13121 */               arrayOfByte[(i + 2)] = 102;
/* 13122 */               k = 3;
/*       */             }
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*       */       }
/* 13129 */       else if (j < 100)
/*       */       {
/* 13131 */         arrayOfByte[i] = -63;
/* 13132 */         arrayOfByte[(i + 1)] = (byte)(j + 1);
/* 13133 */         k = 2;
/*       */       }
/* 13135 */       else if (j < 10000)
/*       */       {
/* 13137 */         arrayOfByte[i] = -62;
/* 13138 */         arrayOfByte[(i + 1)] = (byte)(j / 100 + 1);
/* 13139 */         m = j % 100;
/*       */ 
/* 13141 */         if (m != 0)
/*       */         {
/* 13143 */           arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13144 */           k = 3;
/*       */         }
/*       */         else
/*       */         {
/* 13148 */           k = 2;
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 13153 */         arrayOfByte[i] = -61;
/* 13154 */         arrayOfByte[(i + 1)] = (byte)(j / 10000 + 1);
/* 13155 */         m = j % 100;
/*       */ 
/* 13157 */         if (m != 0)
/*       */         {
/* 13159 */           arrayOfByte[(i + 2)] = (byte)(j % 10000 / 100 + 1);
/* 13160 */           arrayOfByte[(i + 3)] = (byte)(m + 1);
/* 13161 */           k = 4;
/*       */         }
/*       */         else
/*       */         {
/* 13165 */           m = j % 10000 / 100;
/*       */ 
/* 13167 */           if (m != 0)
/*       */           {
/* 13169 */             arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13170 */             k = 3;
/*       */           }
/*       */           else
/*       */           {
/* 13174 */             k = 2;
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 13180 */     arrayOfByte[paramInt6] = (byte)k;
/* 13181 */     paramArrayOfShort[paramInt9] = 0;
/* 13182 */     paramArrayOfShort[paramInt8] = (short)(k + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ShortBinder
 * JD-Core Version:    0.6.0
 */