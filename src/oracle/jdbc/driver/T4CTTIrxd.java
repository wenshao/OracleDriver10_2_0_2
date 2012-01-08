/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.BitSet;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ 
/*     */ class T4CTTIrxd extends T4CTTIMsg
/*     */ {
/*  47 */   static final byte[] NO_BYTES = new byte[0];
/*     */   byte[] buffer;
/*     */   byte[] bufferCHAR;
/*  57 */   BitSet bvcColSent = null;
/*  58 */   int nbOfColumns = 0;
/*  59 */   boolean bvcFound = false;
/*     */   boolean isFirstCol;
/* 951 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIrxd(T4CMAREngine paramT4CMAREngine)
/*     */   {
/*  74 */     super(7);
/*     */ 
/*  76 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  78 */     this.isFirstCol = true;
/*     */   }
/*     */ 
/*     */   void init()
/*     */   {
/*  86 */     this.isFirstCol = true;
/*     */   }
/*     */ 
/*     */   void setNumberOfColumns(int paramInt)
/*     */   {
/*  94 */     this.nbOfColumns = paramInt;
/*  95 */     this.bvcFound = false;
/*     */ 
/*  99 */     this.bvcColSent = new BitSet(this.nbOfColumns);
/*     */   }
/*     */ 
/*     */   void unmarshalBVC(int paramInt) throws SQLException, IOException
/*     */   {
/* 104 */     int i = 0;
/*     */ 
/* 107 */     for (int j = 0; j < this.bvcColSent.length(); j++) {
/* 108 */       this.bvcColSent.clear(j);
/*     */     }
/*     */ 
/* 112 */     j = this.nbOfColumns / 8 + (this.nbOfColumns % 8 != 0 ? 1 : 0);
/*     */ 
/* 115 */     for (int k = 0; k < j; k++)
/*     */     {
/* 117 */       int m = (byte)(this.meg.unmarshalUB1() & 0xFF);
/*     */ 
/* 119 */       for (int n = 0; n < 8; n++)
/*     */       {
/* 121 */         if ((m & 1 << n) == 0)
/*     */           continue;
/* 123 */         this.bvcColSent.set(k * 8 + n);
/*     */ 
/* 125 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 130 */     if (i != paramInt)
/*     */     {
/* 136 */       DatabaseError.throwSqlException(-1, "INTERNAL ERROR: oracle.jdbc.driver.T4CTTIrxd.unmarshalBVC: bits missing in vector");
/*     */     }
/*     */ 
/* 141 */     this.bvcFound = true;
/*     */   }
/*     */ 
/*     */   void readBitVector(byte[] paramArrayOfByte)
/*     */     throws SQLException, IOException
/*     */   {
/* 153 */     for (int i = 0; i < this.bvcColSent.length(); i++) {
/* 154 */       this.bvcColSent.clear(i);
/*     */     }
/* 156 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 157 */       this.bvcFound = false;
/*     */     }
/*     */     else {
/* 160 */       for (i = 0; i < paramArrayOfByte.length; i++) {
/* 161 */         int j = paramArrayOfByte[i];
/* 162 */         for (int k = 0; k < 8; k++) {
/* 163 */           if ((j & 1 << k) != 0)
/* 164 */             this.bvcColSent.set(i * 8 + k);
/*     */         }
/*     */       }
/* 167 */       this.bvcFound = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   void marshal(byte[] paramArrayOfByte1, char[] paramArrayOfChar1, short[] paramArrayOfShort1, int paramInt1, byte[] paramArrayOfByte2, DBConversion paramDBConversion, InputStream[] paramArrayOfInputStream, byte[][] paramArrayOfByte, OracleTypeADT[] paramArrayOfOracleTypeADT, byte[] paramArrayOfByte3, char[] paramArrayOfChar2, short[] paramArrayOfShort2, byte[] paramArrayOfByte4, int paramInt2, int[] paramArrayOfInt1, boolean paramBoolean, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[][] paramArrayOfInt)
/*     */     throws IOException, SQLException
/*     */   {
/* 197 */     marshalTTCcode();
/*     */ 
/* 200 */     int i = paramArrayOfShort1[(paramInt1 + 0)] & 0xFFFF;
/*     */ 
/* 222 */     int i12 = 0;
/* 223 */     int i13 = paramArrayOfInt3[0];
/* 224 */     Object localObject1 = paramArrayOfInt[0];
/*     */ 
/* 226 */     int i14 = 0;
/*     */     int i16;
/*     */     int j;
/*     */     int i9;
/*     */     int k;
/*     */     int i11;
/*     */     int i10;
/*     */     int i24;
/*     */     int i6;
/*     */     int i8;
/*     */     int i26;
/*     */     int i7;
/*     */     int i3;
/*     */     int i5;
/*     */     int i19;
/*     */     int i21;
/* 228 */     for (int i15 = 0; i15 < i; i15++)
/*     */     {
/* 230 */       if ((i12 < i13) && (localObject1[i12] == i15))
/*     */       {
/* 233 */         i12++;
/*     */       }
/*     */       else
/*     */       {
/* 237 */         i16 = 0;
/*     */ 
/* 244 */         j = paramInt1 + 3 + 10 * i15;
/*     */ 
/* 249 */         i9 = paramArrayOfShort1[(j + 0)] & 0xFFFF;
/*     */ 
/* 254 */         if ((paramArrayOfByte4 != null) && ((paramArrayOfByte4[i15] & 0x20) == 0))
/*     */         {
/* 259 */           if (i9 == 998) {
/* 260 */             i14++;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 265 */           k = ((paramArrayOfShort1[(j + 7)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 8)] & 0xFFFF) + paramInt2;
/*     */ 
/* 269 */           i11 = ((paramArrayOfShort1[(j + 5)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 6)] & 0xFFFF) + paramInt2;
/*     */ 
/* 274 */           int m = paramArrayOfShort1[k] & 0xFFFF;
/*     */ 
/* 276 */           i10 = paramArrayOfShort1[i11];
/*     */ 
/* 281 */           if (i9 == 116)
/*     */           {
/* 283 */             this.meg.marshalUB1(1);
/* 284 */             this.meg.marshalUB1(0);
/*     */           }
/*     */           else
/*     */           {
/* 288 */             if (i9 == 994)
/*     */             {
/* 290 */               i10 = -1;
/* 291 */               int i17 = paramArrayOfInt2[(3 + i15 * 3 + 0)];
/*     */ 
/* 295 */               if (i17 == 109)
/* 296 */                 i16 = 1;
/*     */             }
/* 298 */             else if ((i9 == 8) || (i9 == 24) || ((!paramBoolean) && (paramArrayOfInt1 != null) && (paramArrayOfInt1.length > i15) && (paramArrayOfInt1[i15] > 4000)))
/*     */             {
/* 315 */               if (i13 >= localObject1.length)
/*     */               {
/* 317 */                 int[] arrayOfInt = new int[localObject1.length << 1];
/*     */ 
/* 320 */                 System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);
/*     */ 
/* 324 */                 localObject1 = arrayOfInt;
/*     */               }
/*     */ 
/* 327 */               localObject1[(i13++)] = i15;
/*     */ 
/* 332 */               continue;
/*     */             }
/*     */ 
/* 336 */             if (i10 == -1)
/*     */             {
/* 338 */               if ((i9 == 109) || (i16 != 0))
/*     */               {
/* 342 */                 this.meg.marshalDALC(NO_BYTES);
/* 343 */                 this.meg.marshalDALC(NO_BYTES);
/* 344 */                 this.meg.marshalDALC(NO_BYTES);
/* 345 */                 this.meg.marshalUB2(0);
/* 346 */                 this.meg.marshalUB4(0L);
/* 347 */                 this.meg.marshalUB2(1);
/*     */ 
/* 349 */                 continue;
/*     */               }
/* 351 */               if (i9 == 998)
/*     */               {
/* 353 */                 i14++;
/* 354 */                 this.meg.marshalUB4(0L);
/* 355 */                 continue;
/*     */               }
/* 357 */               if ((i9 == 112) || (i9 == 113) || (i9 == 114))
/*     */               {
/* 359 */                 this.meg.marshalUB4(0L);
/* 360 */                 continue;
/*     */               }
/* 362 */               if ((i9 != 8) && (i9 != 24))
/*     */               {
/* 372 */                 this.meg.marshalUB1(0);
/*     */ 
/* 374 */                 continue;
/*     */               }
/*     */             }
/*     */             int i20;
/*     */             int i27;
/* 378 */             if (i9 == 998)
/*     */             {
/* 381 */               int i18 = (paramArrayOfShort2[(6 + i14 * 8 + 4)] & 0xFFFF) << 16 & 0xFFFF000 | paramArrayOfShort2[(6 + i14 * 8 + 5)] & 0xFFFF;
/*     */ 
/* 384 */               i20 = (paramArrayOfShort2[(6 + i14 * 8 + 6)] & 0xFFFF) << 16 & 0xFFFF000 | paramArrayOfShort2[(6 + i14 * 8 + 7)] & 0xFFFF;
/*     */ 
/* 387 */               int i22 = paramArrayOfShort2[(6 + i14 * 8)] & 0xFFFF;
/* 388 */               i24 = paramArrayOfShort2[(6 + i14 * 8 + 1)] & 0xFFFF;
/*     */ 
/* 390 */               this.meg.marshalUB4(i18);
/*     */ 
/* 392 */               for (int i25 = 0; i25 < i18; i25++)
/*     */               {
/* 394 */                 i27 = i20 + i25 * i24;
/*     */ 
/* 396 */                 if (i22 == 9)
/*     */                 {
/* 398 */                   i6 = paramArrayOfChar2[i27] / '\002';
/* 399 */                   i8 = 0;
/* 400 */                   i8 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar2, i27 + 1, paramArrayOfByte2, 0, i6);
/*     */ 
/* 406 */                   this.meg.marshalCLR(paramArrayOfByte2, i8);
/*     */                 }
/*     */                 else
/*     */                 {
/* 410 */                   m = paramArrayOfByte3[i27];
/*     */ 
/* 412 */                   if (m < 1)
/* 413 */                     this.meg.marshalUB1(0);
/*     */                   else {
/* 415 */                     this.meg.marshalCLR(paramArrayOfByte3, i27 + 1, m);
/*     */                   }
/*     */                 }
/*     */               }
/* 419 */               i14++;
/*     */             }
/*     */             else
/*     */             {
/* 428 */               int i2 = paramArrayOfShort1[(j + 1)] & 0xFFFF;
/*     */               int n;
/* 432 */               if (i2 != 0)
/*     */               {
/* 439 */                 int i4 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + i2 * paramInt2;
/*     */ 
/* 446 */                 if (i9 == 6)
/*     */                 {
/* 448 */                   i4++;
/* 449 */                   m--;
/*     */                 }
/* 451 */                 else if (i9 == 9)
/*     */                 {
/* 453 */                   i4 += 2;
/*     */ 
/* 455 */                   m -= 2;
/*     */                 }
/* 457 */                 else if ((i9 == 114) || (i9 == 113) || (i9 == 112))
/*     */                 {
/* 460 */                   this.meg.marshalUB4(m);
/*     */                 }
/*     */                 Object localObject2;
/* 464 */                 if ((i9 == 109) || (i9 == 111))
/*     */                 {
/* 466 */                   if (paramArrayOfByte == null)
/*     */                   {
/* 471 */                     DatabaseError.throwSqlException(-1, "INTERNAL ERROR: oracle.jdbc.driver.T4CTTIrxd.marshal: parameterDatum is null");
/*     */                   }
/*     */ 
/* 476 */                   localObject2 = paramArrayOfByte[i15];
/*     */ 
/* 478 */                   n = localObject2 == null ? 0 : localObject2.length;
/*     */ 
/* 480 */                   if (i9 == 109)
/*     */                   {
/* 482 */                     this.meg.marshalDALC(NO_BYTES);
/* 483 */                     this.meg.marshalDALC(NO_BYTES);
/* 484 */                     this.meg.marshalDALC(NO_BYTES);
/* 485 */                     this.meg.marshalUB2(0);
/*     */ 
/* 487 */                     this.meg.marshalUB4(n);
/* 488 */                     this.meg.marshalUB2(1);
/*     */                   }
/*     */ 
/* 491 */                   if (n > 0)
/* 492 */                     this.meg.marshalCLR(localObject2, 0, n);
/*     */                 }
/* 494 */                 else if (i9 == 104)
/*     */                 {
/* 498 */                   i4 += 2;
/*     */ 
/* 500 */                   localObject2 = T4CRowidAccessor.stringToRowid(paramArrayOfByte1, i4, 18);
/*     */ 
/* 502 */                   i20 = 14;
/* 503 */                   long l1 = localObject2[0];
/* 504 */                   i26 = (int)localObject2[1];
/* 505 */                   i27 = 0;
/* 506 */                   long l2 = localObject2[2];
/* 507 */                   int i28 = (int)localObject2[3];
/*     */ 
/* 511 */                   if ((l1 == 0L) && (i26 == 0) && (l2 == 0L) && (i28 == 0))
/*     */                   {
/* 517 */                     this.meg.marshalUB1(0);
/*     */                   }
/*     */                   else
/*     */                   {
/* 521 */                     this.meg.marshalUB1(i20);
/* 522 */                     this.meg.marshalUB4(l1);
/* 523 */                     this.meg.marshalUB2(i26);
/* 524 */                     this.meg.marshalUB1(i27);
/* 525 */                     this.meg.marshalUB4(l2);
/* 526 */                     this.meg.marshalUB2(i28);
/*     */                   }
/*     */ 
/*     */                 }
/* 531 */                 else if (n < 1) {
/* 532 */                   this.meg.marshalUB1(0);
/*     */                 } else {
/* 534 */                   this.meg.marshalCLR(paramArrayOfByte1, i4, n);
/*     */                 }
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 544 */                 i7 = paramArrayOfShort1[(j + 9)] & 0xFFFF;
/*     */ 
/* 552 */                 i3 = paramArrayOfShort1[(j + 2)] & 0xFFFF;
/*     */ 
/* 555 */                 i5 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + i3 * paramInt2 + 1;
/*     */ 
/* 560 */                 if (i9 == 996)
/*     */                 {
/* 569 */                   i19 = paramArrayOfChar1[(i5 - 1)];
/*     */ 
/* 571 */                   if ((this.bufferCHAR == null) || (this.bufferCHAR.length < i19)) {
/* 572 */                     this.bufferCHAR = new byte[i19];
/*     */                   }
/* 574 */                   for (i21 = 0; i21 < i19; i21++)
/*     */                   {
/* 576 */                     this.bufferCHAR[i21] = (byte)((paramArrayOfChar1[(i5 + i21 / 2)] & 0xFF00) >> '\b' & 0xFF);
/*     */ 
/* 580 */                     if (i21 >= i19 - 1)
/*     */                       continue;
/* 582 */                     this.bufferCHAR[(i21 + 1)] = (byte)(paramArrayOfChar1[(i5 + i21 / 2)] & 0xFF & 0xFF);
/*     */ 
/* 585 */                     i21++;
/*     */                   }
/*     */ 
/* 589 */                   this.meg.marshalCLR(this.bufferCHAR, i19);
/*     */ 
/* 591 */                   if (this.bufferCHAR.length > 4000) {
/* 592 */                     this.bufferCHAR = null;
/*     */                   }
/*     */ 
/*     */                 }
/*     */                 else
/*     */                 {
/* 603 */                   if (i9 == 96)
/*     */                   {
/* 607 */                     i6 = n / 2;
/* 608 */                     i5--;
/*     */                   }
/*     */                   else
/*     */                   {
/* 612 */                     i6 = (n - 2) / 2;
/*     */                   }
/*     */ 
/* 616 */                   i8 = 0;
/*     */ 
/* 621 */                   if (i7 == 2)
/*     */                   {
/* 623 */                     i8 = paramDBConversion.javaCharsToNCHARBytes(paramArrayOfChar1, i5, paramArrayOfByte2, 0, i6);
/*     */                   }
/*     */                   else
/*     */                   {
/* 628 */                     i8 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar1, i5, paramArrayOfByte2, 0, i6);
/*     */                   }
/*     */ 
/* 634 */                   this.meg.marshalCLR(paramArrayOfByte2, i8);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 644 */     if (i13 > 0)
/*     */     {
/* 646 */       for (i15 = 0; i15 < i13; i15++)
/*     */       {
/* 648 */         i16 = localObject1[i15];
/*     */ 
/* 650 */         j = paramInt1 + 3 + 10 * i16;
/*     */ 
/* 654 */         i9 = paramArrayOfShort1[(j + 0)] & 0xFFFF;
/*     */ 
/* 658 */         k = ((paramArrayOfShort1[(j + 7)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 8)] & 0xFFFF) + paramInt2;
/*     */ 
/* 662 */         i11 = ((paramArrayOfShort1[(j + 5)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 6)] & 0xFFFF) + paramInt2;
/*     */ 
/* 666 */         i10 = paramArrayOfShort1[i11];
/* 667 */         int i1 = paramArrayOfShort1[k] & 0xFFFF;
/*     */ 
/* 669 */         i3 = paramArrayOfShort1[(j + 2)] & 0xFFFF;
/*     */ 
/* 672 */         i5 = ((paramArrayOfShort1[(j + 3)] & 0xFFFF) << 16) + (paramArrayOfShort1[(j + 4)] & 0xFFFF) + i3 * paramInt2 + 1;
/*     */ 
/* 677 */         if (i10 == -1)
/*     */         {
/* 679 */           this.meg.marshalUB1(0);
/*     */         }
/* 683 */         else if (i9 == 996)
/*     */         {
/* 692 */           i19 = paramArrayOfChar1[(i5 - 1)];
/*     */ 
/* 694 */           if ((this.bufferCHAR == null) || (this.bufferCHAR.length < i19)) {
/* 695 */             this.bufferCHAR = new byte[i19];
/*     */           }
/* 697 */           for (i21 = 0; i21 < i19; i21++)
/*     */           {
/* 699 */             this.bufferCHAR[i21] = (byte)((paramArrayOfChar1[(i5 + i21 / 2)] & 0xFF00) >> '\b' & 0xFF);
/*     */ 
/* 703 */             if (i21 >= i19 - 1)
/*     */               continue;
/* 705 */             this.bufferCHAR[(i21 + 1)] = (byte)(paramArrayOfChar1[(i5 + i21 / 2)] & 0xFF & 0xFF);
/*     */ 
/* 707 */             i21++;
/*     */           }
/*     */ 
/* 711 */           this.meg.marshalCLR(this.bufferCHAR, i19);
/*     */ 
/* 713 */           if (this.bufferCHAR.length > 4000)
/* 714 */             this.bufferCHAR = null;
/*     */         }
/* 716 */         else if ((i9 != 8) && (i9 != 24))
/*     */         {
/* 723 */           if (i9 == 96)
/*     */           {
/* 727 */             i6 = i1 / 2;
/* 728 */             i5--;
/*     */           }
/*     */           else
/*     */           {
/* 732 */             i6 = (i1 - 2) / 2;
/*     */           }
/*     */ 
/* 735 */           i7 = paramArrayOfShort1[(j + 9)] & 0xFFFF;
/*     */ 
/* 740 */           i8 = 0;
/*     */ 
/* 745 */           if (i7 == 2)
/*     */           {
/* 747 */             i8 = paramDBConversion.javaCharsToNCHARBytes(paramArrayOfChar1, i5, paramArrayOfByte2, 0, i6);
/*     */           }
/*     */           else
/*     */           {
/* 752 */             i8 = paramDBConversion.javaCharsToCHARBytes(paramArrayOfChar1, i5, paramArrayOfByte2, 0, i6);
/*     */           }
/*     */ 
/* 758 */           this.meg.marshalCLR(paramArrayOfByte2, i8);
/*     */         }
/*     */         else
/*     */         {
/* 763 */           i19 = i16;
/*     */ 
/* 767 */           if (paramArrayOfInputStream == null)
/*     */             continue;
/* 769 */           InputStream localInputStream = paramArrayOfInputStream[i19];
/*     */ 
/* 771 */           if (localInputStream == null)
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/* 776 */           int i23 = 64;
/*     */ 
/* 778 */           if (this.buffer == null) {
/* 779 */             this.buffer = new byte[i23];
/*     */           }
/* 781 */           i24 = 0;
/*     */ 
/* 784 */           this.meg.marshalUB1(254);
/*     */ 
/* 786 */           i26 = 0;
/*     */ 
/* 788 */           while (i26 == 0)
/*     */           {
/* 790 */             i24 = localInputStream.read(this.buffer, 0, i23);
/*     */ 
/* 792 */             if (i24 < i23) {
/* 793 */               i26 = 1;
/*     */             }
/* 795 */             if (i24 <= 0)
/*     */             {
/*     */               continue;
/*     */             }
/* 799 */             this.meg.marshalUB1((short)(i24 & 0xFF));
/*     */ 
/* 802 */             this.meg.marshalB1Array(this.buffer, 0, i24);
/*     */           }
/*     */ 
/* 807 */           this.meg.marshalSB1(0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 826 */     paramArrayOfInt3[0] = i13;
/* 827 */     paramArrayOfInt[0] = localObject1;
/*     */   }
/*     */ 
/*     */   boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 843 */     return unmarshal(paramArrayOfAccessor, 0, paramInt);
/*     */   }
/*     */ 
/*     */   boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt1, int paramInt2)
/*     */     throws SQLException, IOException
/*     */   {
/* 861 */     if (paramInt1 == 0) {
/* 862 */       this.isFirstCol = true;
/*     */     }
/* 864 */     for (int i = paramInt1; (i < paramInt2) && (i < paramArrayOfAccessor.length); i++)
/*     */     {
/* 866 */       if (paramArrayOfAccessor[i] == null)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 883 */       if (paramArrayOfAccessor[i].physicalColumnIndex < 0)
/*     */       {
/* 887 */         int j = 0;
/*     */ 
/* 889 */         for (int k = 0; (k < paramInt2) && (k < paramArrayOfAccessor.length); k++)
/*     */         {
/* 891 */           if (paramArrayOfAccessor[k] == null)
/*     */             continue;
/* 893 */           paramArrayOfAccessor[k].physicalColumnIndex = j;
/*     */ 
/* 895 */           if (!paramArrayOfAccessor[k].isUseLess) {
/* 896 */             j++;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 901 */       if ((this.bvcFound) && (!paramArrayOfAccessor[i].isUseLess))
/*     */       {
/* 903 */         if (this.bvcColSent.get(paramArrayOfAccessor[i].physicalColumnIndex))
/*     */         {
/* 905 */           if (paramArrayOfAccessor[i].unmarshalOneRow()) {
/* 906 */             return true;
/*     */           }
/* 908 */           this.isFirstCol = false;
/*     */         }
/*     */         else
/*     */         {
/* 914 */           paramArrayOfAccessor[i].copyRow();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 919 */         if (paramArrayOfAccessor[i].unmarshalOneRow()) {
/* 920 */           return true;
/*     */         }
/* 922 */         this.isFirstCol = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 927 */     this.bvcFound = false;
/*     */ 
/* 931 */     return false;
/*     */   }
/*     */ 
/*     */   boolean unmarshal(Accessor[] paramArrayOfAccessor, int paramInt1, int paramInt2, int paramInt3)
/*     */     throws SQLException, IOException
/*     */   {
/* 946 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIrxd
 * JD-Core Version:    0.6.0
 */