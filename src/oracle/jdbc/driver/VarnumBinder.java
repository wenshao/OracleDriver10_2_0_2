/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ abstract class VarnumBinder extends Binder
/*       */ {
/*       */   static final boolean DEBUG = false;
/*       */   static final boolean SLOW_CONVERSIONS = true;
/* 11768 */   Binder theVarnumCopyingBinder = OraclePreparedStatementReadOnly.theStaticVarnumCopyingBinder;
/*       */   static final int LNXSGNBT = 128;
/*       */   static final byte LNXDIGS = 20;
/*       */   static final byte LNXEXPBS = 64;
/*       */   static final int LNXEXPMX = 127;
/* 11810 */   static final double[] factorTable = { 9.999999999999999E+253D, 1.E+252D, 9.999999999999999E+249D, 1.0E+248D, 1.E+246D, 1.E+244D, 1.E+242D, 1.0E+240D, 1.0E+238D, 1.E+236D, 1.0E+234D, 1.E+232D, 1.E+230D, 9.999999999999999E+227D, 1.0E+226D, 1.0E+224D, 1.0E+222D, 1.0E+220D, 1.E+218D, 1.0E+216D, 1.0E+214D, 9.999999999999999E+211D, 9.999999999999999E+209D, 1.0E+208D, 1.0E+206D, 1.0E+204D, 9.999999999999999E+201D, 1.0E+200D, 1.0E+198D, 1.0E+196D, 9.999999999999999E+193D, 1.0E+192D, 1.E+190D, 1.0E+188D, 1.0E+186D, 1.0E+184D, 1.E+182D, 1.0E+180D, 1.E+178D, 1.0E+176D, 1.E+174D, 1.E+172D, 1.0E+170D, 9.999999999999999E+167D, 9.999999999999999E+165D, 1.0E+164D, 9.999999999999999E+161D, 1.0E+160D, 1.0E+158D, 1.0E+156D, 1.0E+154D, 1.0E+152D, 1.0E+150D, 1.0E+148D, 9.999999999999999E+145D, 1.0E+144D, 1.E+142D, 1.E+140D, 1.0E+138D, 1.E+136D, 9.999999999999999E+133D, 1.0E+132D, 1.E+130D, 1.E+128D, 9.999999999999999E+125D, 1.0E+124D, 1.0E+122D, 1.0E+120D, 1.0E+118D, 1.0E+116D, 1.0E+114D, 9.999999999999999E+111D, 1.0E+110D, 1.0E+108D, 1.E+106D, 1.0E+104D, 1.0E+102D, 1.0E+100D, 1.0E+98D, 1.0E+96D, 1.0E+94D, 1.0E+92D, 1.0E+90D, 1.0E+88D, 1.0E+86D, 1.E+84D, 1.0E+82D, 1.0E+80D, 1.0E+78D, 1.0E+76D, 1.0E+74D, 9.999999999999999E+71D, 1.E+70D, 1.0E+68D, 1.0E+66D, 1.0E+64D, 1.0E+62D, 1.0E+60D, 9.999999999999999E+57D, 1.E+56D, 1.E+54D, 1.0E+52D, 1.E+50D, 1.0E+48D, 1.0E+46D, 1.E+44D, 1.0E+42D, 1.0E+40D, 1.0E+38D, 1.0E+36D, 1.0E+34D, 1.E+32D, 1.0E+30D, 1.0E+28D, 1.0E+26D, 1.0E+24D, 1.0E+22D, 1.0E+20D, 1.0E+18D, 10000000000000000.0D, 100000000000000.0D, 1000000000000.0D, 10000000000.0D, 100000000.0D, 1000000.0D, 10000.0D, 100.0D, 1.0D, 0.01D, 0.0001D, 1.0E-06D, 1.0E-08D, 1.0E-10D, 1.0E-12D, 1.0E-14D, 1.0E-16D, 1.E-18D, 1.0E-20D, 1.0E-22D, 9.999999999999999E-25D, 1.0E-26D, 1.0E-28D, 1.E-30D, 1.E-32D, 9.999999999999999E-35D, 9.999999999999999E-37D, 1.0E-38D, 9.999999999999999E-41D, 1.0E-42D, 1.0E-44D, 1.0E-46D, 1.0E-48D, 1.0E-50D, 1.0E-52D, 1.0E-54D, 1.0E-56D, 1.0E-58D, 1.0E-60D, 1.0E-62D, 1.0E-64D, 1.0E-66D, 1.E-68D, 1.0E-70D, 1.0E-72D, 1.0E-74D, 9.999999999999999E-77D, 1.0E-78D, 1.0E-80D, 1.0E-82D, 1.0E-84D, 1.E-86D, 9.999999999999999E-89D, 1.0E-90D, 1.0E-92D, 1.0E-94D, 9.999999999999999E-97D, 9.999999999999999E-99D, 1.0E-100D, 9.999999999999999E-103D, 9.999999999999999E-105D, 9.999999999999999E-107D, 1.0E-108D, 1.E-110D, 1.0E-112D, 1.E-114D, 1.0E-116D, 1.0E-118D, 1.0E-120D, 1.E-122D, 9.999999999999999E-125D, 1.0E-126D, 1.E-128D, 1.E-130D, 1.0E-132D, 1.0E-134D, 1.0E-136D, 1.E-138D, 1.0E-140D, 1.0E-142D, 1.0E-144D, 1.0E-146D, 9.999999999999999E-149D, 1.0E-150D, 1.E-152D, 1.0E-154D, 1.0E-156D, 1.E-158D, 1.0E-160D, 1.0E-162D, 1.0E-164D, 1.0E-166D, 1.0E-168D, 1.0E-170D, 1.0E-172D, 1.0E-174D, 1.0E-176D, 1.0E-178D, 1.0E-180D, 1.0E-182D, 1.E-184D, 9.999999999999999E-187D, 1.0E-188D, 1.0E-190D, 1.E-192D, 1.0E-194D, 1.0E-196D, 9.999999999999999E-199D, 1.0E-200D, 1.0E-202D, 1.0E-204D, 1.0E-206D, 1.E-208D, 1.0E-210D, 1.0E-212D, 9.999999999999999E-215D, 1.0E-216D, 1.0E-218D, 1.0E-220D, 1.0E-222D, 1.0E-224D, 9.999999999999999E-227D, 1.0E-228D, 1.0E-230D, 1.0E-232D, 1.0E-234D, 1.0E-236D, 1.0E-238D, 1.0E-240D, 1.0E-242D, 9.999999999999999E-245D, 1.0E-246D, 1.0E-248D, 1.E-250D, 9.999999999999999E-253D, 9.999999999999999E-255D };
/*       */   static final int MANTISSA_SIZE = 53;
/*       */   static final int expShift = 52;
/*       */   static final long fractHOB = 4503599627370496L;
/*       */   static final long fractMask = 4503599627370495L;
/*       */   static final int expBias = 1023;
/*       */   static final int maxSmallBinExp = 62;
/*       */   static final int minSmallBinExp = -21;
/*       */   static final long expOne = 4607182418800017408L;
/*       */   static final long highbyte = -72057594037927936L;
/*       */   static final long highbit = -9223372036854775808L;
/*       */   static final long lowbytes = 72057594037927935L;
/* 11859 */   static final int[] small5pow = { 1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125 };
/*       */ 
/* 11871 */   static final long[] long5pow = { 1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L };
/*       */ 
/* 11903 */   static final int[] n5bits = { 0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61 };
/*       */   static FDBigInt[] b5p;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 11773 */     paramBinder.type = 6;
/* 11774 */     paramBinder.bytelen = 22;
/*       */   }
/*       */ 
/*       */   VarnumBinder()
/*       */   {
/* 11779 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 11784 */     return this.theVarnumCopyingBinder;
/*       */   }
/*       */ 
/*       */   static int setLongInternal(byte[] paramArrayOfByte, int paramInt, long paramLong)
/*       */   {
/* 11912 */     if (paramLong == 0L)
/*       */     {
/* 11914 */       paramArrayOfByte[paramInt] = -128;
/*       */ 
/* 11916 */       return 1;
/*       */     }
/*       */ 
/* 11919 */     if (paramLong == -9223372036854775808L)
/*       */     {
/* 11921 */       paramArrayOfByte[paramInt] = 53;
/* 11922 */       paramArrayOfByte[(paramInt + 1)] = 92;
/* 11923 */       paramArrayOfByte[(paramInt + 2)] = 79;
/* 11924 */       paramArrayOfByte[(paramInt + 3)] = 68;
/* 11925 */       paramArrayOfByte[(paramInt + 4)] = 29;
/* 11926 */       paramArrayOfByte[(paramInt + 5)] = 98;
/* 11927 */       paramArrayOfByte[(paramInt + 6)] = 33;
/* 11928 */       paramArrayOfByte[(paramInt + 7)] = 47;
/* 11929 */       paramArrayOfByte[(paramInt + 8)] = 24;
/* 11930 */       paramArrayOfByte[(paramInt + 9)] = 43;
/* 11931 */       paramArrayOfByte[(paramInt + 10)] = 93;
/* 11932 */       paramArrayOfByte[(paramInt + 11)] = 102;
/*       */ 
/* 11934 */       return 12;
/*       */     }
/*       */ 
/* 11937 */     int i = 10;
/* 11938 */     int j = 0;
/*       */ 
/* 11940 */     if (paramLong / 1000000000000000000L == 0L)
/*       */     {
/* 11942 */       i--;
/*       */ 
/* 11944 */       if (paramLong / 10000000000000000L == 0L)
/*       */       {
/* 11946 */         i--;
/*       */ 
/* 11948 */         if (paramLong / 100000000000000L == 0L)
/*       */         {
/* 11950 */           i--;
/*       */ 
/* 11952 */           if (paramLong / 1000000000000L == 0L)
/*       */           {
/* 11954 */             i--;
/*       */ 
/* 11956 */             if (paramLong / 10000000000L == 0L)
/*       */             {
/* 11958 */               i--;
/*       */ 
/* 11960 */               if (paramLong / 100000000L == 0L)
/*       */               {
/* 11962 */                 i--;
/*       */ 
/* 11964 */                 if (paramLong / 1000000L == 0L)
/*       */                 {
/* 11966 */                   i--;
/*       */ 
/* 11968 */                   if (paramLong / 10000L == 0L)
/*       */                   {
/* 11970 */                     i--;
/*       */ 
/* 11972 */                     if (paramLong / 100L == 0L)
/*       */                     {
/* 11974 */                       i--;
/*       */                     }
/*       */                   }
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 11985 */     int k = paramInt + i;
/*       */     int m;
/* 11988 */     if (paramLong < 0L)
/*       */     {
/* 11990 */       paramLong = -paramLong;
/* 11991 */       paramArrayOfByte[paramInt] = (byte)(63 - i);
/*       */       while (true)
/*       */       {
/* 11995 */         m = (int)(paramLong % 100L);
/*       */ 
/* 11997 */         if (j == 0)
/*       */         {
/* 11999 */           if (m != 0)
/*       */           {
/* 12001 */             paramArrayOfByte[(k + 1)] = 102;
/* 12002 */             j = k + 2 - paramInt;
/* 12003 */             paramArrayOfByte[k] = (byte)(101 - m);
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/* 12008 */           paramArrayOfByte[k] = (byte)(101 - m);
/*       */         }
/*       */ 
/* 12011 */         k--; if (k == paramInt) {
/*       */           break;
/*       */         }
/* 12014 */         paramLong /= 100L;
/*       */       }
/*       */ 
/*       */     }
/*       */ 
/* 12020 */     paramArrayOfByte[paramInt] = (byte)(192 + i);
/*       */     while (true)
/*       */     {
/* 12024 */       m = (int)(paramLong % 100L);
/*       */ 
/* 12026 */       if (j == 0)
/*       */       {
/* 12028 */         if (m != 0)
/*       */         {
/* 12030 */           j = k + 1 - paramInt;
/* 12031 */           paramArrayOfByte[k] = (byte)(m + 1);
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 12036 */         paramArrayOfByte[k] = (byte)(m + 1);
/*       */       }
/*       */ 
/* 12039 */       k--; if (k == paramInt) {
/*       */         break;
/*       */       }
/* 12042 */       paramLong /= 100L;
/*       */     }
/*       */ 
/* 12047 */     return j;
/*       */   }
/*       */ 
/*       */   static int countBits(long paramLong)
/*       */   {
/* 12058 */     if (paramLong == 0L) {
/* 12059 */       return 0;
/*       */     }
/* 12061 */     while ((paramLong & 0x0) == 0L)
/*       */     {
/* 12063 */       paramLong <<= 8;
/*       */     }
/*       */ 
/* 12066 */     while (paramLong > 0L)
/*       */     {
/* 12068 */       paramLong <<= 1;
/*       */     }
/*       */ 
/* 12071 */     int i = 0;
/*       */ 
/* 12073 */     while ((paramLong & 0xFFFFFFFF) != 0L)
/*       */     {
/* 12075 */       paramLong <<= 8;
/* 12076 */       i += 8;
/*       */     }
/*       */ 
/* 12079 */     while (paramLong != 0L)
/*       */     {
/* 12081 */       paramLong <<= 1;
/* 12082 */       i++;
/*       */     }
/*       */ 
/* 12085 */     return i;
/*       */   }
/*       */ 
/*       */   boolean roundup(char[] paramArrayOfChar, int paramInt)
/*       */   {
/* 12097 */     int i = paramInt - 1;
/* 12098 */     int j = paramArrayOfChar[i];
/*       */ 
/* 12100 */     if (j == 57)
/*       */     {
/* 12102 */       while ((j == 57) && (i > 0))
/*       */       {
/* 12104 */         paramArrayOfChar[i] = '0';
/* 12105 */         i--; j = paramArrayOfChar[i];
/*       */       }
/*       */ 
/* 12108 */       if (j == 57)
/*       */       {
/* 12112 */         paramArrayOfChar[0] = '1';
/*       */ 
/* 12114 */         return true;
/*       */       }
/*       */ 
/*       */     }
/*       */ 
/* 12120 */     paramArrayOfChar[i] = (char)(j + 1);
/*       */ 
/* 12122 */     return false;
/*       */   }
/*       */ 
/*       */   static synchronized FDBigInt big5pow(int paramInt)
/*       */   {
/* 12129 */     if (paramInt < 0) {
/* 12130 */       throw new RuntimeException("Assertion botch: negative power of 5");
/*       */     }
/* 12132 */     if (b5p == null)
/*       */     {
/* 12134 */       b5p = new FDBigInt[paramInt + 1];
/*       */     }
/* 12136 */     else if (b5p.length <= paramInt)
/*       */     {
/* 12138 */       FDBigInt[] arrayOfFDBigInt = new FDBigInt[paramInt + 1];
/*       */ 
/* 12140 */       System.arraycopy(b5p, 0, arrayOfFDBigInt, 0, b5p.length);
/*       */ 
/* 12142 */       b5p = arrayOfFDBigInt;
/*       */     }
/*       */ 
/* 12145 */     if (b5p[paramInt] != null)
/* 12146 */       return b5p[paramInt];
/* 12147 */     if (paramInt < small5pow.length)
/* 12148 */       return b5p[paramInt] =  = new FDBigInt(small5pow[paramInt]);
/* 12149 */     if (paramInt < long5pow.length) {
/* 12150 */       return b5p[paramInt] =  = new FDBigInt(long5pow[paramInt]);
/*       */     }
/*       */ 
/* 12162 */     int i = paramInt >> 1;
/* 12163 */     int j = paramInt - i;
/*       */ 
/* 12165 */     FDBigInt localFDBigInt1 = b5p[i];
/*       */ 
/* 12167 */     if (localFDBigInt1 == null) {
/* 12168 */       localFDBigInt1 = big5pow(i);
/*       */     }
/* 12170 */     if (j < small5pow.length)
/*       */     {
/* 12172 */       return b5p[paramInt] =  = localFDBigInt1.mult(small5pow[j]);
/*       */     }
/*       */ 
/* 12176 */     FDBigInt localFDBigInt2 = b5p[j];
/*       */ 
/* 12178 */     if (localFDBigInt2 == null) {
/* 12179 */       localFDBigInt2 = big5pow(j);
/*       */     }
/* 12181 */     return b5p[paramInt] =  = localFDBigInt1.mult(localFDBigInt2);
/*       */   }
/*       */ 
/*       */   static FDBigInt multPow52(FDBigInt paramFDBigInt, int paramInt1, int paramInt2)
/*       */   {
/* 12188 */     if (paramInt1 != 0)
/*       */     {
/* 12190 */       if (paramInt1 < small5pow.length)
/*       */       {
/* 12192 */         paramFDBigInt = paramFDBigInt.mult(small5pow[paramInt1]);
/*       */       }
/*       */       else
/*       */       {
/* 12196 */         paramFDBigInt = paramFDBigInt.mult(big5pow(paramInt1));
/*       */       }
/*       */     }
/*       */ 
/* 12200 */     if (paramInt2 != 0)
/*       */     {
/* 12202 */       paramFDBigInt.lshiftMe(paramInt2);
/*       */     }
/*       */ 
/* 12205 */     return paramFDBigInt;
/*       */   }
/*       */ 
/*       */   static FDBigInt constructPow52(int paramInt1, int paramInt2)
/*       */   {
/* 12213 */     FDBigInt localFDBigInt = new FDBigInt(big5pow(paramInt1));
/*       */ 
/* 12215 */     if (paramInt2 != 0)
/*       */     {
/* 12217 */       localFDBigInt.lshiftMe(paramInt2);
/*       */     }
/*       */ 
/* 12220 */     return localFDBigInt;
/*       */   }
/*       */ 
/*       */   int dtoa(byte[] paramArrayOfByte, int paramInt1, double paramDouble, boolean paramBoolean1, boolean paramBoolean2, char[] paramArrayOfChar, int paramInt2, long paramLong, int paramInt3)
/*       */   {
/* 12226 */     int i = 2147483647;
/* 12227 */     int j = -1;
/* 12228 */     int k = countBits(paramLong);
/*       */ 
/* 12230 */     int m = k - paramInt2 - 1;
/*       */ 
/* 12233 */     int i1 = 0;
/*       */ 
/* 12239 */     if (m < 0)
/* 12240 */       m = 0;
/*       */     int i11;
/*       */     int i8;
/*       */     int i9;
/*       */     int i10;
/* 12242 */     if ((paramInt2 <= 62) && (paramInt2 >= -21))
/*       */     {
/* 12248 */       if ((m < long5pow.length) && (k + n5bits[m] < 64))
/*       */       {
/* 12269 */         if (m == 0)
/*       */         {
/*       */           long l1;
/* 12271 */           if (paramInt2 > paramInt3)
/*       */           {
/* 12273 */             l1 = 1L << paramInt2 - paramInt3 - 1;
/*       */           }
/*       */           else
/*       */           {
/* 12277 */             l1 = 0L;
/*       */           }
/*       */ 
/* 12280 */           if (paramInt2 >= 52)
/*       */           {
/* 12282 */             paramLong <<= paramInt2 - 52;
/*       */           }
/*       */           else
/*       */           {
/* 12286 */             paramLong >>>= 52 - paramInt2;
/*       */           }
/*       */ 
/* 12290 */           i = 0;
/*       */ 
/* 12292 */           long l2 = paramLong;
/* 12293 */           long l3 = l1;
/*       */ 
/* 12303 */           for (i11 = 0; l3 >= 10L; i11++) {
/* 12304 */             l3 /= 10L;
/*       */           }
/* 12306 */           if (i11 != 0)
/*       */           {
/* 12308 */             long l4 = long5pow[i11] << i11;
/* 12309 */             long l5 = l2 % l4;
/*       */ 
/* 12311 */             l2 /= l4;
/* 12312 */             i += i11;
/*       */ 
/* 12314 */             if (l5 >= l4 >> 1)
/*       */             {
/* 12318 */               l2 += 1L;
/*       */             }
/*       */           }
/*       */ 
/* 12322 */           if (l2 <= 2147483647L)
/*       */           {
/* 12327 */             int i12 = (int)l2;
/*       */ 
/* 12329 */             i8 = 10;
/* 12330 */             i9 = i8 - 1;
/* 12331 */             i10 = i12 % 10;
/* 12332 */             i12 /= 10;
/*       */ 
/* 12334 */             while (i10 == 0)
/*       */             {
/* 12336 */               i++;
/* 12337 */               i10 = i12 % 10;
/* 12338 */               i12 /= 10;
/*       */             }
/*       */ 
/* 12341 */             while (i12 != 0)
/*       */             {
/* 12343 */               paramArrayOfChar[(i9--)] = (char)(i10 + 48);
/* 12344 */               i++;
/* 12345 */               i10 = i12 % 10;
/* 12346 */               i12 /= 10;
/*       */             }
/*       */ 
/* 12349 */             paramArrayOfChar[i9] = (char)(i10 + 48);
/*       */           }
/*       */           else
/*       */           {
/* 12356 */             i8 = 20;
/* 12357 */             i9 = i8 - 1;
/* 12358 */             i10 = (int)(l2 % 10L);
/* 12359 */             l2 /= 10L;
/*       */ 
/* 12361 */             while (i10 == 0)
/*       */             {
/* 12363 */               i++;
/* 12364 */               i10 = (int)(l2 % 10L);
/* 12365 */               l2 /= 10L;
/*       */             }
/*       */ 
/* 12368 */             while (l2 != 0L)
/*       */             {
/* 12370 */               paramArrayOfChar[(i9--)] = (char)(i10 + 48);
/* 12371 */               i++;
/* 12372 */               i10 = (int)(l2 % 10L);
/* 12373 */               l2 /= 10L;
/*       */             }
/*       */ 
/* 12376 */             paramArrayOfChar[i9] = (char)(i10 + 48);
/*       */           }
/*       */ 
/* 12379 */           i8 -= i9;
/*       */ 
/* 12381 */           if (i9 != 0) {
/* 12382 */             System.arraycopy(paramArrayOfChar, i9, paramArrayOfChar, 0, i8);
/*       */           }
/* 12384 */           i += 1;
/* 12385 */           j = i8;
/*       */ 
/* 12388 */           i1 = 1;
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 12393 */     if (i1 == 0)
/*       */     {
/* 12428 */       double d = Double.longBitsToDouble(0x0 | paramLong & 0xFFFFFFFF);
/*       */ 
/* 12430 */       int n = (int)Math.floor((d - 1.5D) * 0.289529654D + 0.176091259D + paramInt2 * 0.301029995663981D);
/*       */ 
/* 12440 */       i5 = Math.max(0, -n);
/* 12441 */       i4 = i5 + m + paramInt2;
/*       */ 
/* 12443 */       int i7 = Math.max(0, n);
/* 12444 */       i6 = i7 + m;
/*       */ 
/* 12446 */       i9 = i5;
/* 12447 */       i8 = i4 - paramInt3;
/*       */ 
/* 12457 */       paramLong >>>= 53 - k;
/* 12458 */       i4 -= k - 1;
/*       */ 
/* 12460 */       int i13 = Math.min(i4, i6);
/*       */ 
/* 12462 */       i4 -= i13;
/* 12463 */       i6 -= i13;
/* 12464 */       i8 -= i13;
/*       */ 
/* 12472 */       if (k == 1) {
/* 12473 */         i8--;
/*       */       }
/* 12475 */       if (i8 < 0)
/*       */       {
/* 12481 */         i4 -= i8;
/* 12482 */         i6 -= i8;
/* 12483 */         i8 = 0;
/*       */       }
/*       */ 
/* 12494 */       int i14 = 0;
/*       */ 
/* 12514 */       i10 = k + i4 + (i5 < n5bits.length ? n5bits[i5] : i5 * 3);
/* 12515 */       i11 = i6 + 1 + (i7 + 1 < n5bits.length ? n5bits[(i7 + 1)] : (i7 + 1) * 3);
/*       */       int i19;
/*       */       int i17;
/*       */       int i15;
/*       */       int i16;
/*       */       long l6;
/* 12519 */       if ((i10 < 64) && (i11 < 64))
/*       */       {
/* 12521 */         if ((i10 < 32) && (i11 < 32))
/*       */         {
/* 12525 */           int i18 = (int)paramLong * small5pow[i5] << i4;
/* 12526 */           i19 = small5pow[i7] << i6;
/* 12527 */           int i20 = small5pow[i9] << i8;
/* 12528 */           int i21 = i19 * 10;
/*       */ 
/* 12535 */           i14 = 0;
/* 12536 */           i17 = i18 / i19;
/* 12537 */           i18 = 10 * (i18 % i19);
/* 12538 */           i20 *= 10;
/* 12539 */           i15 = i18 < i20 ? 1 : 0;
/* 12540 */           i16 = i18 + i20 > i21 ? 1 : 0;
/*       */ 
/* 12542 */           if ((i17 == 0) && (i16 == 0))
/*       */           {
/* 12546 */             n--;
/*       */           }
/*       */           else
/*       */           {
/* 12550 */             paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */           }
/*       */ 
/* 12559 */           if ((n <= -3) || (n >= 8))
/*       */           {
/* 12561 */             i16 = i15 = 0;
/*       */           }
/*       */ 
/* 12564 */           while ((i15 == 0) && (i16 == 0))
/*       */           {
/* 12566 */             i17 = i18 / i19;
/* 12567 */             i18 = 10 * (i18 % i19);
/* 12568 */             i20 *= 10;
/*       */ 
/* 12570 */             if (i20 > 0L)
/*       */             {
/* 12572 */               i15 = i18 < i20 ? 1 : 0;
/* 12573 */               i16 = i18 + i20 > i21 ? 1 : 0;
/*       */             }
/*       */             else
/*       */             {
/* 12583 */               i15 = 1;
/* 12584 */               i16 = 1;
/*       */             }
/*       */ 
/* 12587 */             paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */           }
/*       */ 
/* 12590 */           l6 = (i18 << 1) - i21;
/*       */         }
/*       */         else
/*       */         {
/* 12596 */           long l7 = paramLong * long5pow[i5] << i4;
/* 12597 */           long l8 = long5pow[i7] << i6;
/* 12598 */           long l9 = long5pow[i9] << i8;
/* 12599 */           long l10 = l8 * 10L;
/*       */ 
/* 12606 */           i14 = 0;
/* 12607 */           i17 = (int)(l7 / l8);
/* 12608 */           l7 = 10L * (l7 % l8);
/* 12609 */           l9 *= 10L;
/* 12610 */           i15 = l7 < l9 ? 1 : 0;
/* 12611 */           i16 = l7 + l9 > l10 ? 1 : 0;
/*       */ 
/* 12613 */           if ((i17 == 0) && (i16 == 0))
/*       */           {
/* 12617 */             n--;
/*       */           }
/*       */           else
/*       */           {
/* 12621 */             paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */           }
/*       */ 
/* 12630 */           if ((n <= -3) || (n >= 8))
/*       */           {
/* 12632 */             i16 = i15 = 0;
/*       */           }
/*       */ 
/* 12635 */           while ((i15 == 0) && (i16 == 0))
/*       */           {
/* 12637 */             i17 = (int)(l7 / l8);
/* 12638 */             l7 = 10L * (l7 % l8);
/* 12639 */             l9 *= 10L;
/*       */ 
/* 12641 */             if (l9 > 0L)
/*       */             {
/* 12643 */               i15 = l7 < l9 ? 1 : 0;
/* 12644 */               i16 = l7 + l9 > l10 ? 1 : 0;
/*       */             }
/*       */             else
/*       */             {
/* 12654 */               i15 = 1;
/* 12655 */               i16 = 1;
/*       */             }
/*       */ 
/* 12658 */             paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */           }
/*       */ 
/* 12661 */           l6 = (l7 << 1) - l10;
/*       */         }
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/* 12673 */         FDBigInt localFDBigInt2 = multPow52(new FDBigInt(paramLong), i5, i4);
/* 12674 */         FDBigInt localFDBigInt1 = constructPow52(i7, i6);
/* 12675 */         FDBigInt localFDBigInt3 = constructPow52(i9, i8);
/*       */ 
/* 12679 */         localFDBigInt2.lshiftMe(i19 = localFDBigInt1.normalizeMe());
/* 12680 */         localFDBigInt3.lshiftMe(i19);
/*       */ 
/* 12682 */         FDBigInt localFDBigInt4 = localFDBigInt1.mult(10);
/*       */ 
/* 12689 */         i14 = 0;
/* 12690 */         i17 = localFDBigInt2.quoRemIteration(localFDBigInt1);
/* 12691 */         localFDBigInt3 = localFDBigInt3.mult(10);
/* 12692 */         i15 = localFDBigInt2.cmp(localFDBigInt3) < 0 ? 1 : 0;
/* 12693 */         i16 = localFDBigInt2.add(localFDBigInt3).cmp(localFDBigInt4) > 0 ? 1 : 0;
/*       */ 
/* 12695 */         if ((i17 == 0) && (i16 == 0))
/*       */         {
/* 12699 */           n--;
/*       */         }
/*       */         else
/*       */         {
/* 12703 */           paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */         }
/*       */ 
/* 12712 */         if ((n <= -3) || (n >= 8))
/*       */         {
/* 12714 */           i16 = i15 = 0;
/*       */         }
/*       */ 
/* 12717 */         while ((i15 == 0) && (i16 == 0))
/*       */         {
/* 12719 */           i17 = localFDBigInt2.quoRemIteration(localFDBigInt1);
/* 12720 */           localFDBigInt3 = localFDBigInt3.mult(10);
/* 12721 */           i15 = localFDBigInt2.cmp(localFDBigInt3) < 0 ? 1 : 0;
/* 12722 */           i16 = localFDBigInt2.add(localFDBigInt3).cmp(localFDBigInt4) > 0 ? 1 : 0;
/* 12723 */           paramArrayOfChar[(i14++)] = (char)(48 + i17);
/*       */         }
/*       */ 
/* 12726 */         if ((i16 != 0) && (i15 != 0))
/*       */         {
/* 12728 */           localFDBigInt2.lshiftMe(1);
/*       */ 
/* 12730 */           l6 = localFDBigInt2.cmp(localFDBigInt4);
/*       */         }
/*       */         else {
/* 12733 */           l6 = 0L;
/*       */         }
/*       */       }
/* 12736 */       i = n + 1;
/* 12737 */       j = i14;
/*       */ 
/* 12742 */       if (i16 != 0)
/*       */       {
/* 12744 */         if (i15 != 0)
/*       */         {
/* 12746 */           if (l6 == 0L)
/*       */           {
/* 12751 */             if ((paramArrayOfChar[(j - 1)] & 0x1) != 0)
/*       */             {
/* 12753 */               if (roundup(paramArrayOfChar, j))
/* 12754 */                 i++;
/*       */             }
/*       */           }
/* 12757 */           else if (l6 > 0L)
/*       */           {
/* 12759 */             if (roundup(paramArrayOfChar, j)) {
/* 12760 */               i++;
/*       */             }
/*       */           }
/*       */ 
/*       */         }
/* 12765 */         else if (roundup(paramArrayOfChar, j)) {
/* 12766 */           i++;
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/* 12771 */     while ((j - i > 0) && (paramArrayOfChar[(j - 1)] == '0')) j--;
/*       */ 
/* 12773 */     int i2 = i % 2 != 0 ? 1 : 0;
/*       */     int i3;
/* 12776 */     if (i2 != 0)
/*       */     {
/* 12778 */       if (j % 2 == 0) {
/* 12779 */         paramArrayOfChar[(j++)] = '0';
/*       */       }
/* 12781 */       i3 = (i - 1) / 2;
/*       */     }
/*       */     else
/*       */     {
/* 12785 */       if (j % 2 == 1) {
/* 12786 */         paramArrayOfChar[(j++)] = '0';
/*       */       }
/* 12788 */       i3 = (i - 2) / 2;
/*       */     }
/*       */ 
/* 12791 */     int i4 = 117 - i3;
/*       */ 
/* 12843 */     int i5 = 0;
/* 12844 */     int i6 = 1;
/*       */ 
/* 12846 */     if (paramBoolean1)
/*       */     {
/* 12851 */       paramArrayOfByte[paramInt1] = (byte)(62 - i3);
/*       */ 
/* 12853 */       if (i2 != 0)
/*       */       {
/* 12855 */         paramArrayOfByte[(paramInt1 + i6)] = (byte)(101 - (paramArrayOfChar[(i5++)] - '0'));
/* 12856 */         i6++;
/*       */       }
/*       */ 
/* 12859 */       while (i5 < j)
/*       */       {
/* 12861 */         paramArrayOfByte[(paramInt1 + i6)] = (byte)(101 - ((paramArrayOfChar[i5] - '0') * 10 + (paramArrayOfChar[(i5 + 1)] - '0')));
/*       */ 
/* 12864 */         i5 += 2;
/* 12865 */         i6++;
/*       */       }
/*       */ 
/* 12868 */       paramArrayOfByte[(paramInt1 + i6++)] = 102;
/*       */     }
/*       */     else
/*       */     {
/* 12874 */       paramArrayOfByte[paramInt1] = (byte)(192 + (i3 + 1));
/*       */ 
/* 12876 */       if (i2 != 0)
/*       */       {
/* 12878 */         paramArrayOfByte[(paramInt1 + i6)] = (byte)(paramArrayOfChar[(i5++)] - '0' + 1);
/* 12879 */         i6++;
/*       */       }
/*       */ 
/* 12882 */       while (i5 < j)
/*       */       {
/* 12884 */         paramArrayOfByte[(paramInt1 + i6)] = (byte)((paramArrayOfChar[i5] - '0') * 10 + (paramArrayOfChar[(i5 + 1)] - '0') + 1);
/*       */ 
/* 12887 */         i5 += 2;
/* 12888 */         i6++;
/*       */       }
/*       */     }
/*       */ 
/* 12892 */     return i6;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.VarnumBinder
 * JD-Core Version:    0.6.0
 */