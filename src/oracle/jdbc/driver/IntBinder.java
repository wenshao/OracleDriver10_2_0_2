/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class IntBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 13200 */     byte[] arrayOfByte = paramArrayOfByte;
/* 13201 */     int i = paramInt6 + 1;
/* 13202 */     int j = paramOraclePreparedStatement.parameterInt[paramInt3][paramInt1];
/* 13203 */     int k = 0;
/*       */ 
/* 13207 */     if (j == 0)
/*       */     {
/* 13209 */       arrayOfByte[i] = -128;
/* 13210 */       k = 1;
/*       */     }
/*       */     else
/*       */     {
/*       */       int m;
/* 13212 */       if (j < 0)
/*       */       {
/* 13214 */         if (j == -2147483648)
/*       */         {
/* 13216 */           arrayOfByte[i] = 58;
/* 13217 */           arrayOfByte[(i + 1)] = 80;
/* 13218 */           arrayOfByte[(i + 2)] = 54;
/*       */ 
/* 13220 */           arrayOfByte[(i + 3)] = 53;
/* 13221 */           arrayOfByte[(i + 4)] = 65;
/* 13222 */           arrayOfByte[(i + 5)] = 53;
/* 13223 */           arrayOfByte[(i + 6)] = 102;
/* 13224 */           k = 7;
/*       */         }
/* 13226 */         else if (-j < 100)
/*       */         {
/* 13228 */           arrayOfByte[i] = 62;
/* 13229 */           arrayOfByte[(i + 1)] = (byte)(101 + j);
/* 13230 */           arrayOfByte[(i + 2)] = 102;
/* 13231 */           k = 3;
/*       */         }
/* 13233 */         else if (-j < 10000)
/*       */         {
/* 13235 */           arrayOfByte[i] = 61;
/* 13236 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 100);
/* 13237 */           m = -j % 100;
/*       */ 
/* 13239 */           if (m != 0)
/*       */           {
/* 13241 */             arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13242 */             arrayOfByte[(i + 3)] = 102;
/* 13243 */             k = 4;
/*       */           }
/*       */           else
/*       */           {
/* 13247 */             arrayOfByte[(i + 2)] = 102;
/* 13248 */             k = 3;
/*       */           }
/*       */         }
/* 13251 */         else if (-j < 1000000)
/*       */         {
/* 13253 */           arrayOfByte[i] = 60;
/* 13254 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 10000);
/* 13255 */           m = -j % 100;
/*       */ 
/* 13257 */           if (m != 0)
/*       */           {
/* 13259 */             arrayOfByte[(i + 2)] = (byte)(101 - -j % 10000 / 100);
/* 13260 */             arrayOfByte[(i + 3)] = (byte)(101 - m);
/* 13261 */             arrayOfByte[(i + 4)] = 102;
/* 13262 */             k = 5;
/*       */           }
/*       */           else
/*       */           {
/* 13266 */             m = -j % 10000 / 100;
/*       */ 
/* 13268 */             if (m != 0)
/*       */             {
/* 13270 */               arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13271 */               arrayOfByte[(i + 3)] = 102;
/* 13272 */               k = 4;
/*       */             }
/*       */             else
/*       */             {
/* 13276 */               arrayOfByte[(i + 2)] = 102;
/* 13277 */               k = 3;
/*       */             }
/*       */           }
/*       */         }
/* 13281 */         else if (-j < 100000000)
/*       */         {
/* 13283 */           arrayOfByte[i] = 59;
/* 13284 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 1000000);
/* 13285 */           m = -j % 100;
/*       */ 
/* 13287 */           if (m != 0)
/*       */           {
/* 13289 */             arrayOfByte[(i + 2)] = (byte)(101 - -j % 1000000 / 10000);
/* 13290 */             arrayOfByte[(i + 3)] = (byte)(101 - -j % 10000 / 100);
/* 13291 */             arrayOfByte[(i + 4)] = (byte)(101 - m);
/* 13292 */             arrayOfByte[(i + 5)] = 102;
/* 13293 */             k = 6;
/*       */           }
/*       */           else
/*       */           {
/* 13297 */             m = -j % 10000 / 100;
/*       */ 
/* 13299 */             if (m != 0)
/*       */             {
/* 13301 */               arrayOfByte[(i + 2)] = (byte)(101 - -j % 1000000 / 10000);
/* 13302 */               arrayOfByte[(i + 3)] = (byte)(101 - m);
/* 13303 */               arrayOfByte[(i + 4)] = 102;
/* 13304 */               k = 5;
/*       */             }
/*       */             else
/*       */             {
/* 13308 */               m = -j % 1000000 / 10000;
/*       */ 
/* 13310 */               if (m != 0)
/*       */               {
/* 13312 */                 arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13313 */                 arrayOfByte[(i + 3)] = 102;
/* 13314 */                 k = 4;
/*       */               }
/*       */               else
/*       */               {
/* 13318 */                 arrayOfByte[(i + 2)] = 102;
/* 13319 */                 k = 3;
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/* 13326 */           arrayOfByte[i] = 58;
/* 13327 */           arrayOfByte[(i + 1)] = (byte)(101 - -j / 100000000);
/* 13328 */           m = -j % 100;
/*       */ 
/* 13330 */           if (m != 0)
/*       */           {
/* 13332 */             arrayOfByte[(i + 2)] = (byte)(101 - -j % 100000000 / 1000000);
/* 13333 */             arrayOfByte[(i + 3)] = (byte)(101 - -j % 1000000 / 10000);
/* 13334 */             arrayOfByte[(i + 4)] = (byte)(101 - -j % 10000 / 100);
/* 13335 */             arrayOfByte[(i + 5)] = (byte)(101 - m);
/* 13336 */             arrayOfByte[(i + 6)] = 102;
/* 13337 */             k = 7;
/*       */           }
/*       */           else
/*       */           {
/* 13341 */             m = -j % 10000 / 100;
/*       */ 
/* 13343 */             if (m != 0)
/*       */             {
/* 13345 */               arrayOfByte[(i + 2)] = (byte)(101 - -j % 100000000 / 1000000);
/* 13346 */               arrayOfByte[(i + 3)] = (byte)(101 - -j % 1000000 / 10000);
/* 13347 */               arrayOfByte[(i + 4)] = (byte)(101 - m);
/* 13348 */               arrayOfByte[(i + 5)] = 102;
/* 13349 */               k = 6;
/*       */             }
/*       */             else
/*       */             {
/* 13353 */               m = -j % 1000000 / 10000;
/*       */ 
/* 13355 */               if (m != 0)
/*       */               {
/* 13357 */                 arrayOfByte[(i + 2)] = (byte)(101 - -j % 100000000 / 1000000);
/* 13358 */                 arrayOfByte[(i + 3)] = (byte)(101 - m);
/* 13359 */                 arrayOfByte[(i + 4)] = 102;
/* 13360 */                 k = 5;
/*       */               }
/*       */               else
/*       */               {
/* 13364 */                 m = -j % 100000000 / 1000000;
/*       */ 
/* 13366 */                 if (m != 0)
/*       */                 {
/* 13368 */                   arrayOfByte[(i + 2)] = (byte)(101 - m);
/* 13369 */                   arrayOfByte[(i + 3)] = 102;
/* 13370 */                   k = 4;
/*       */                 }
/*       */                 else
/*       */                 {
/* 13374 */                   arrayOfByte[(i + 2)] = 102;
/* 13375 */                   k = 3;
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*       */       }
/* 13384 */       else if (j < 100)
/*       */       {
/* 13386 */         arrayOfByte[i] = -63;
/* 13387 */         arrayOfByte[(i + 1)] = (byte)(j + 1);
/* 13388 */         k = 2;
/*       */       }
/* 13390 */       else if (j < 10000)
/*       */       {
/* 13392 */         arrayOfByte[i] = -62;
/* 13393 */         arrayOfByte[(i + 1)] = (byte)(j / 100 + 1);
/* 13394 */         m = j % 100;
/*       */ 
/* 13396 */         if (m != 0)
/*       */         {
/* 13398 */           arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13399 */           k = 3;
/*       */         }
/*       */         else
/*       */         {
/* 13403 */           k = 2;
/*       */         }
/*       */       }
/* 13406 */       else if (j < 1000000)
/*       */       {
/* 13408 */         arrayOfByte[i] = -61;
/* 13409 */         arrayOfByte[(i + 1)] = (byte)(j / 10000 + 1);
/* 13410 */         m = j % 100;
/*       */ 
/* 13412 */         if (m != 0)
/*       */         {
/* 13414 */           arrayOfByte[(i + 2)] = (byte)(j % 10000 / 100 + 1);
/* 13415 */           arrayOfByte[(i + 3)] = (byte)(m + 1);
/* 13416 */           k = 4;
/*       */         }
/*       */         else
/*       */         {
/* 13420 */           m = j % 10000 / 100;
/*       */ 
/* 13422 */           if (m != 0)
/*       */           {
/* 13424 */             arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13425 */             k = 3;
/*       */           }
/*       */           else
/*       */           {
/* 13429 */             k = 2;
/*       */           }
/*       */         }
/*       */       }
/* 13433 */       else if (j < 100000000)
/*       */       {
/* 13435 */         arrayOfByte[i] = -60;
/* 13436 */         arrayOfByte[(i + 1)] = (byte)(j / 1000000 + 1);
/* 13437 */         m = j % 100;
/*       */ 
/* 13439 */         if (m != 0)
/*       */         {
/* 13441 */           arrayOfByte[(i + 2)] = (byte)(j % 1000000 / 10000 + 1);
/* 13442 */           arrayOfByte[(i + 3)] = (byte)(j % 10000 / 100 + 1);
/* 13443 */           arrayOfByte[(i + 4)] = (byte)(m + 1);
/* 13444 */           k = 5;
/*       */         }
/*       */         else
/*       */         {
/* 13448 */           m = j % 10000 / 100;
/*       */ 
/* 13450 */           if (m != 0)
/*       */           {
/* 13452 */             arrayOfByte[(i + 2)] = (byte)(j % 1000000 / 10000 + 1);
/* 13453 */             arrayOfByte[(i + 3)] = (byte)(m + 1);
/* 13454 */             k = 4;
/*       */           }
/*       */           else
/*       */           {
/* 13458 */             m = j % 1000000 / 10000;
/*       */ 
/* 13460 */             if (m != 0)
/*       */             {
/* 13462 */               arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13463 */               k = 3;
/*       */             }
/*       */             else
/*       */             {
/* 13467 */               k = 2;
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 13474 */         arrayOfByte[i] = -59;
/* 13475 */         arrayOfByte[(i + 1)] = (byte)(j / 100000000 + 1);
/* 13476 */         m = j % 100;
/*       */ 
/* 13478 */         if (m != 0)
/*       */         {
/* 13480 */           arrayOfByte[(i + 2)] = (byte)(j % 100000000 / 1000000 + 1);
/* 13481 */           arrayOfByte[(i + 3)] = (byte)(j % 1000000 / 10000 + 1);
/* 13482 */           arrayOfByte[(i + 4)] = (byte)(j % 10000 / 100 + 1);
/* 13483 */           arrayOfByte[(i + 5)] = (byte)(m + 1);
/* 13484 */           k = 6;
/*       */         }
/*       */         else
/*       */         {
/* 13488 */           m = j % 10000 / 100;
/*       */ 
/* 13490 */           if (m != 0)
/*       */           {
/* 13492 */             arrayOfByte[(i + 2)] = (byte)(j % 100000000 / 1000000 + 1);
/* 13493 */             arrayOfByte[(i + 3)] = (byte)(j % 1000000 / 10000 + 1);
/* 13494 */             arrayOfByte[(i + 4)] = (byte)(m + 1);
/* 13495 */             k = 5;
/*       */           }
/*       */           else
/*       */           {
/* 13499 */             m = j % 1000000 / 10000;
/*       */ 
/* 13501 */             if (m != 0)
/*       */             {
/* 13503 */               arrayOfByte[(i + 2)] = (byte)(j % 100000000 / 1000000 + 1);
/* 13504 */               arrayOfByte[(i + 3)] = (byte)(m + 1);
/* 13505 */               k = 4;
/*       */             }
/*       */             else
/*       */             {
/* 13509 */               m = j % 100000000 / 1000000;
/*       */ 
/* 13511 */               if (m != 0)
/*       */               {
/* 13513 */                 arrayOfByte[(i + 2)] = (byte)(m + 1);
/* 13514 */                 k = 3;
/*       */               }
/*       */               else
/*       */               {
/* 13518 */                 k = 2;
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 13526 */     arrayOfByte[paramInt6] = (byte)k;
/* 13527 */     paramArrayOfShort[paramInt9] = 0;
/* 13528 */     paramArrayOfShort[paramInt8] = (short)(k + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.IntBinder
 * JD-Core Version:    0.6.0
 */