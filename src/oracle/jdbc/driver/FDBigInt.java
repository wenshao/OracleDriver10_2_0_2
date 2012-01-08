/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class FDBigInt
/*       */ {
/*       */   int nWords;
/*       */   int[] data;
/*       */ 
/*       */   FDBigInt(int paramInt)
/*       */   {
/* 14082 */     this.nWords = 1;
/* 14083 */     this.data = new int[1];
/* 14084 */     this.data[0] = paramInt;
/*       */   }
/*       */ 
/*       */   FDBigInt(long paramLong)
/*       */   {
/* 14089 */     this.data = new int[2];
/* 14090 */     this.data[0] = (int)paramLong;
/* 14091 */     this.data[1] = (int)(paramLong >>> 32);
/* 14092 */     this.nWords = (this.data[1] == 0 ? 1 : 2);
/*       */   }
/*       */ 
/*       */   FDBigInt(FDBigInt paramFDBigInt)
/*       */   {
/* 14097 */     this.data = new int[this.nWords = paramFDBigInt.nWords];
/*       */ 
/* 14099 */     System.arraycopy(paramFDBigInt.data, 0, this.data, 0, this.nWords);
/*       */   }
/*       */ 
/*       */   FDBigInt(int[] paramArrayOfInt, int paramInt)
/*       */   {
/* 14104 */     this.data = paramArrayOfInt;
/* 14105 */     this.nWords = paramInt;
/*       */   }
/*       */ 
/*       */   void lshiftMe(int paramInt)
/*       */     throws IllegalArgumentException
/*       */   {
/* 14114 */     if (paramInt <= 0)
/*       */     {
/* 14116 */       if (paramInt == 0) {
/* 14117 */         return;
/*       */       }
/* 14119 */       throw new IllegalArgumentException("negative shift count");
/*       */     }
/*       */ 
/* 14122 */     int i = paramInt >> 5;
/* 14123 */     int j = paramInt & 0x1F;
/* 14124 */     int k = 32 - j;
/* 14125 */     int[] arrayOfInt1 = this.data;
/* 14126 */     int[] arrayOfInt2 = this.data;
/*       */ 
/* 14128 */     if (this.nWords + i + 1 > arrayOfInt1.length)
/*       */     {
/* 14132 */       arrayOfInt1 = new int[this.nWords + i + 1];
/*       */     }
/*       */ 
/* 14135 */     int m = this.nWords + i;
/* 14136 */     int n = this.nWords - 1;
/*       */ 
/* 14138 */     if (j == 0)
/*       */     {
/* 14142 */       System.arraycopy(arrayOfInt2, 0, arrayOfInt1, i, this.nWords);
/*       */ 
/* 14144 */       m = i - 1;
/*       */     }
/*       */     else
/*       */     {
/* 14148 */       arrayOfInt1[(m--)] = (arrayOfInt2[n] >>> k);
/*       */ 
/* 14150 */       while (n >= 1)
/*       */       {
/* 14152 */         n--; arrayOfInt1[(m--)] = (arrayOfInt2[n] << j | arrayOfInt2[n] >>> k);
/*       */       }
/*       */ 
/* 14155 */       arrayOfInt1[(m--)] = (arrayOfInt2[n] << j);
/*       */     }
/*       */ 
/* 14158 */     while (m >= 0)
/*       */     {
/* 14160 */       arrayOfInt1[(m--)] = 0;
/*       */     }
/*       */ 
/* 14163 */     this.data = arrayOfInt1;
/* 14164 */     this.nWords += i + 1;
/*       */ 
/* 14168 */     while ((this.nWords > 1) && (this.data[(this.nWords - 1)] == 0))
/* 14169 */       this.nWords -= 1;
/*       */   }
/*       */ 
/*       */   int normalizeMe()
/*       */     throws IllegalArgumentException
/*       */   {
/* 14186 */     int j = 0;
/* 14187 */     int k = 0;
/* 14188 */     int m = 0;
/*       */ 
/* 14190 */     for (int i = this.nWords - 1; (i >= 0) && ((m = this.data[i]) == 0); i--)
/*       */     {
/* 14192 */       j++;
/*       */     }
/*       */ 
/* 14195 */     if (i < 0)
/*       */     {
/* 14199 */       throw new IllegalArgumentException("zero value");
/*       */     }
/*       */ 
/* 14208 */     this.nWords -= j;
/*       */ 
/* 14215 */     if ((m & 0xF0000000) != 0)
/*       */     {
/* 14220 */       for (k = 32; (m & 0xF0000000) != 0; k--) {
/* 14221 */         m >>>= 1;
/*       */       }
/*       */     }
/*       */ 
/* 14225 */     while (m <= 1048575)
/*       */     {
/* 14229 */       m <<= 8;
/* 14230 */       k += 8;
/*       */     }
/*       */ 
/* 14233 */     while (m <= 134217727)
/*       */     {
/* 14235 */       m <<= 1;
/* 14236 */       k++;
/*       */     }
/*       */ 
/* 14240 */     if (k != 0) {
/* 14241 */       lshiftMe(k);
/*       */     }
/* 14243 */     return k;
/*       */   }
/*       */ 
/*       */   FDBigInt mult(int paramInt)
/*       */   {
/* 14252 */     long l1 = paramInt;
/*       */ 
/* 14257 */     int[] arrayOfInt = new int[l1 * (this.data[(this.nWords - 1)] & 0xFFFFFFFF) > 268435455L ? this.nWords + 1 : this.nWords];
/*       */ 
/* 14261 */     long l2 = 0L;
/*       */ 
/* 14263 */     for (int i = 0; i < this.nWords; i++)
/*       */     {
/* 14265 */       l2 += l1 * (this.data[i] & 0xFFFFFFFF);
/* 14266 */       arrayOfInt[i] = (int)l2;
/* 14267 */       l2 >>>= 32;
/*       */     }
/*       */ 
/* 14270 */     if (l2 == 0L)
/*       */     {
/* 14272 */       return new FDBigInt(arrayOfInt, this.nWords);
/*       */     }
/*       */ 
/* 14276 */     arrayOfInt[this.nWords] = (int)l2;
/*       */ 
/* 14278 */     return new FDBigInt(arrayOfInt, this.nWords + 1);
/*       */   }
/*       */ 
/*       */   FDBigInt mult(FDBigInt paramFDBigInt)
/*       */   {
/* 14290 */     int[] arrayOfInt = new int[this.nWords + paramFDBigInt.nWords];
/*       */ 
/* 14295 */     for (int i = 0; i < this.nWords; i++)
/*       */     {
/* 14297 */       long l1 = this.data[i] & 0xFFFFFFFF;
/* 14298 */       long l2 = 0L;
/*       */ 
/* 14301 */       for (int j = 0; j < paramFDBigInt.nWords; j++)
/*       */       {
/* 14303 */         l2 += (arrayOfInt[(i + j)] & 0xFFFFFFFF) + l1 * (paramFDBigInt.data[j] & 0xFFFFFFFF);
/*       */ 
/* 14307 */         arrayOfInt[(i + j)] = (int)l2;
/* 14308 */         l2 >>>= 32;
/*       */       }
/*       */ 
/* 14311 */       arrayOfInt[(i + j)] = (int)l2;
/*       */     }
/*       */ 
/* 14315 */     for (i = arrayOfInt.length - 1; (i > 0) && 
/* 14316 */       (arrayOfInt[i] == 0); i--);
/* 14319 */     return new FDBigInt(arrayOfInt, i + 1);
/*       */   }
/*       */ 
/*       */   FDBigInt add(FDBigInt paramFDBigInt)
/*       */   {
/* 14330 */     long l = 0L;
/*       */     int[] arrayOfInt1;
/*       */     int j;
/*       */     int[] arrayOfInt2;
/*       */     int k;
/* 14334 */     if (this.nWords >= paramFDBigInt.nWords)
/*       */     {
/* 14336 */       arrayOfInt1 = this.data;
/* 14337 */       j = this.nWords;
/* 14338 */       arrayOfInt2 = paramFDBigInt.data;
/* 14339 */       k = paramFDBigInt.nWords;
/*       */     }
/*       */     else
/*       */     {
/* 14343 */       arrayOfInt1 = paramFDBigInt.data;
/* 14344 */       j = paramFDBigInt.nWords;
/* 14345 */       arrayOfInt2 = this.data;
/* 14346 */       k = this.nWords;
/*       */     }
/*       */ 
/* 14349 */     int[] arrayOfInt3 = new int[j];
/*       */ 
/* 14351 */     for (int i = 0; i < j; i++)
/*       */     {
/* 14353 */       l += (arrayOfInt1[i] & 0xFFFFFFFF);
/*       */ 
/* 14355 */       if (i < k)
/*       */       {
/* 14357 */         l += (arrayOfInt2[i] & 0xFFFFFFFF);
/*       */       }
/*       */ 
/* 14360 */       arrayOfInt3[i] = (int)l;
/* 14361 */       l >>= 32;
/*       */     }
/*       */ 
/* 14364 */     if (l != 0L)
/*       */     {
/* 14368 */       int[] arrayOfInt4 = new int[arrayOfInt3.length + 1];
/*       */ 
/* 14370 */       System.arraycopy(arrayOfInt3, 0, arrayOfInt4, 0, arrayOfInt3.length);
/*       */ 
/* 14372 */       arrayOfInt4[(i++)] = (int)l;
/*       */ 
/* 14374 */       return new FDBigInt(arrayOfInt4, i);
/*       */     }
/*       */ 
/* 14377 */     return new FDBigInt(arrayOfInt3, i);
/*       */   }
/*       */ 
/*       */   int cmp(FDBigInt paramFDBigInt)
/*       */   {
/* 14390 */     if (this.nWords > paramFDBigInt.nWords)
/*       */     {
/* 14395 */       j = paramFDBigInt.nWords - 1;
/*       */ 
/* 14397 */       for (i = this.nWords - 1; i > j; i--)
/* 14398 */         if (this.data[i] != 0)
/* 14399 */           return 1;
/*       */     }
/* 14401 */     if (this.nWords < paramFDBigInt.nWords)
/*       */     {
/* 14406 */       j = this.nWords - 1;
/*       */ 
/* 14408 */       for (i = paramFDBigInt.nWords - 1; i > j; i--) {
/* 14409 */         if (paramFDBigInt.data[i] != 0) {
/* 14410 */           return -1;
/*       */         }
/*       */       }
/*       */     }
/* 14414 */     int i = this.nWords - 1;
/*       */ 
/* 14417 */     while ((i > 0) && 
/* 14418 */       (this.data[i] == paramFDBigInt.data[i])) {
/* 14417 */       i--;
/*       */     }
/*       */ 
/* 14424 */     int j = this.data[i];
/* 14425 */     int k = paramFDBigInt.data[i];
/*       */ 
/* 14427 */     if (j < 0)
/*       */     {
/* 14431 */       if (k < 0)
/*       */       {
/* 14433 */         return j - k;
/*       */       }
/*       */ 
/* 14437 */       return 1;
/*       */     }
/*       */ 
/* 14444 */     if (k < 0)
/*       */     {
/* 14448 */       return -1;
/*       */     }
/*       */ 
/* 14452 */     return j - k;
/*       */   }
/*       */ 
/*       */   int quoRemIteration(FDBigInt paramFDBigInt)
/*       */     throws IllegalArgumentException
/*       */   {
/* 14474 */     if (this.nWords != paramFDBigInt.nWords)
/*       */     {
/* 14476 */       throw new IllegalArgumentException("disparate values");
/*       */     }
/*       */ 
/* 14482 */     int i = this.nWords - 1;
/* 14483 */     long l1 = (this.data[i] & 0xFFFFFFFF) / paramFDBigInt.data[i];
/* 14484 */     long l2 = 0L;
/*       */ 
/* 14486 */     for (int j = 0; j <= i; j++)
/*       */     {
/* 14488 */       l2 += (this.data[j] & 0xFFFFFFFF) - l1 * (paramFDBigInt.data[j] & 0xFFFFFFFF);
/*       */ 
/* 14490 */       this.data[j] = (int)l2;
/* 14491 */       l2 >>= 32;
/*       */     }
/*       */ 
/* 14494 */     if (l2 != 0L)
/*       */     {
/* 14500 */       l3 = 0L;
/*       */ 
/* 14502 */       while (l3 == 0L)
/*       */       {
/* 14504 */         l3 = 0L;
/*       */ 
/* 14506 */         for (k = 0; k <= i; k++)
/*       */         {
/* 14508 */           l3 += (this.data[k] & 0xFFFFFFFF) + (paramFDBigInt.data[k] & 0xFFFFFFFF);
/*       */ 
/* 14510 */           this.data[k] = (int)l3;
/* 14511 */           l3 >>= 32;
/*       */         }
/*       */ 
/* 14523 */         if ((l3 != 0L) && (l3 != 1L)) {
/* 14524 */           throw new RuntimeException("Assertion botch: " + l3 + " carry out of division correction");
/*       */         }
/*       */ 
/* 14527 */         l1 -= 1L;
/*       */       }
/*       */ 
/*       */     }
/*       */ 
/* 14534 */     long l3 = 0L;
/*       */ 
/* 14536 */     for (int k = 0; k <= i; k++)
/*       */     {
/* 14538 */       l3 += 10L * (this.data[k] & 0xFFFFFFFF);
/* 14539 */       this.data[k] = (int)l3;
/* 14540 */       l3 >>= 32;
/*       */     }
/*       */ 
/* 14543 */     if (l3 != 0L) {
/* 14544 */       throw new RuntimeException("Assertion botch: carry out of *10");
/*       */     }
/* 14546 */     return (int)l1;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.FDBigInt
 * JD-Core Version:    0.6.0
 */