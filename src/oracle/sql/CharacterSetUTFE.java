/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetUTFE extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*     */   static final int MAXBYTEPERCHAR = 4;
/*  44 */   static byte[][] utf8m2utfe = { { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 21, 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31 }, { 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97 }, { -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111 }, { 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42 }, { -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, -83, -32, -67, 95, 109 }, { 121, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106 }, { -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, -64, 79, -48, -95, 7 }, { 32, 33, 34, 35, 36, 37, 6, 23, 40, 41, 42, 43, 44, 9, 10, 27 }, { 48, 49, 26, 51, 52, 53, 54, 8, 56, 57, 58, 59, 4, 20, 62, -1 }, { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 81, 82, 83, 84, 85, 86 }, { 87, 88, 89, 98, 99, 100, 101, 102, 103, 104, 105, 106, 112, 113, 114, 115 }, { 116, 117, 118, 119, 120, -128, -118, -117, -116, -115, -114, -113, -112, -102, -101, -100 }, { -99, -98, -97, -96, -86, -85, -84, -82, -81, -80, -79, -78, -77, -76, -75, -74 }, { -73, -72, -71, -70, -69, -68, -66, -65, -54, -53, -52, -51, -50, -49, -38, -37 }, { -36, -35, -34, -33, -31, -22, -21, -20, -19, -18, -17, -6, -5, -4, -3, -2 } };
/*     */ 
/* 142 */   static byte[][] utfe2utf8m = { { 0, 1, 2, 3, -100, 9, -122, 127, -105, -115, -114, 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, -99, 10, 8, -121, 24, 25, -110, -113, 28, 29, 30, 31 }, { -128, -127, -126, -125, -124, -123, 23, 27, -120, -119, -118, -117, -116, 5, 6, 7 }, { -112, -111, 22, -109, -108, -107, -106, 4, -104, -103, -102, -101, 20, 21, -98, 26 }, { 32, -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, 46, 60, 40, 43, 124 }, { 38, -86, -85, -84, -83, -82, -81, -80, -79, -78, 33, 36, 42, 41, 59, 94 }, { 45, 47, -77, -76, -75, -74, -73, -72, -71, -70, -69, 44, 37, 95, 62, 63 }, { -68, -67, -66, -65, -64, -63, -62, -61, -60, 96, 58, 35, 64, 39, 61, 34 }, { -59, 97, 98, 99, 100, 101, 102, 103, 104, 105, -58, -57, -56, -55, -54, -53 }, { -52, 106, 107, 108, 109, 110, 111, 112, 113, 114, -51, -50, -49, -48, -47, -46 }, { -45, 126, 115, 116, 117, 118, 119, 120, 121, 122, -44, -43, -42, 88, -41, -40 }, { -39, -38, -37, -36, -35, -34, -33, -32, -31, -30, -29, -28, -27, 93, -26, -25 }, { 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, -24, -23, -22, -21, -20, -19 }, { 13, 74, 75, 76, 77, 78, 79, 80, 81, 82, -18, -17, -16, -15, -14, -13 }, { 92, -12, 83, 84, 85, 86, 87, 88, 89, 90, -11, -10, -9, -8, -7, -6 }, { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -5, -4, -3, -2, -1, -97 } };
/*     */ 
/* 241 */   private static int[] m_byteLen = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 3, 4 };
/*     */ 
/*     */   CharacterSetUTFE(int paramInt)
/*     */   {
/* 248 */     super(paramInt);
/*     */ 
/* 250 */     this.rep = 3;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 257 */     return !paramCharacterSet.isUnicode();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 264 */     int i = paramCharacterSet.rep <= 1024 ? 1 : 0;
/*     */ 
/* 266 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isUnicode()
/*     */   {
/* 272 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 282 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/* 283 */       int i = UTFEToJavaChar(paramArrayOfByte, paramInt1, paramInt2, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPORT_ERROR);
/*     */ 
/* 286 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException) {
/*     */     }
/* 290 */     throw new SQLException(localSQLException.getMessage());
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/* 300 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/* 301 */       int i = UTFEToJavaChar(paramArrayOfByte, paramInt1, paramInt2, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPLACEMENT);
/*     */ 
/* 304 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 310 */     throw new IllegalStateException(localSQLException.getMessage());
/*     */   }
/*     */ 
/*     */   int UTFEToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char[] paramArrayOfChar, CharacterSet.CharacterConverterBehavior paramCharacterConverterBehavior)
/*     */     throws SQLException
/*     */   {
/* 318 */     int i = paramInt1;
/* 319 */     int j = paramInt1 + paramInt2;
/* 320 */     int k = 0;
/*     */ 
/* 326 */     while (i < j)
/*     */     {
/* 328 */       int m = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/*     */       byte b1;
/*     */       byte b2;
/* 330 */       switch (m >>> 4 & 0xF)
/*     */       {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/* 349 */         paramArrayOfChar[(k++)] = (char)(m & 0x7F);
/*     */ 
/* 351 */         break;
/*     */       case 8:
/*     */       case 9:
/* 357 */         paramArrayOfChar[(k++)] = (char)(m & 0x1F);
/*     */ 
/* 359 */         break;
/*     */       case 12:
/*     */       case 13:
/* 366 */         if (i >= j)
/*     */         {
/* 368 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 370 */           i = j;
/*     */ 
/* 372 */           continue;
/*     */         }
/*     */ 
/* 375 */         m = (byte)(m & 0x1F);
/* 376 */         b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/*     */ 
/* 379 */         if (!is101xxxxx(b1))
/*     */         {
/* 381 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 383 */           paramArrayOfChar[(k++)] = 65533;
/*     */ 
/* 386 */           continue;
/*     */         }
/*     */ 
/* 389 */         paramArrayOfChar[(k++)] = (char)(m << 5 | b1 & 0x1F);
/*     */ 
/* 391 */         break;
/*     */       case 14:
/* 396 */         if (i + 1 >= j)
/*     */         {
/* 398 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 400 */           i = j;
/*     */ 
/* 402 */           continue;
/*     */         }
/*     */ 
/* 405 */         m = (byte)(m & 0xF);
/* 406 */         b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/* 407 */         b2 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/*     */ 
/* 410 */         if ((!is101xxxxx(b1)) || (!is101xxxxx(b2)))
/*     */         {
/* 412 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 414 */           paramArrayOfChar[(k++)] = 65533;
/*     */ 
/* 417 */           continue;
/*     */         }
/*     */ 
/* 420 */         paramArrayOfChar[(k++)] = (char)(m << 10 | (b1 & 0x1F) << 5 | b2 & 0x1F);
/*     */ 
/* 423 */         break;
/*     */       case 15:
/* 428 */         if (i + 2 >= j)
/*     */         {
/* 430 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 432 */           i = j;
/*     */ 
/* 434 */           continue;
/*     */         }
/*     */ 
/* 437 */         m = (byte)(m & 0x1);
/* 438 */         b1 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/* 439 */         b2 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/* 440 */         byte b3 = utfe2utf8m[high(paramArrayOfByte[i])][low(paramArrayOfByte[(i++)])];
/*     */ 
/* 443 */         if ((!is101xxxxx(b1)) || (!is101xxxxx(b2)) || (!is101xxxxx(b3)))
/*     */         {
/* 445 */           paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 447 */           paramArrayOfChar[(k++)] = 65533;
/*     */ 
/* 450 */           continue;
/*     */         }
/*     */ 
/* 453 */         paramArrayOfChar[(k++)] = (char)(m << 15 | (b1 & 0x1F) << 10 | (b2 & 0x1F) << 5 | b3 & 0x1F);
/*     */ 
/* 456 */         break;
/*     */       case 10:
/*     */       case 11:
/*     */       default:
/* 461 */         paramCharacterConverterBehavior.onFailConversion();
/*     */ 
/* 463 */         paramArrayOfChar[(k++)] = 65533;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 470 */     return k;
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 483 */     char[] arrayOfChar = paramString.toCharArray();
/* 484 */     byte[] arrayOfByte1 = new byte[arrayOfChar.length * 4];
/*     */ 
/* 486 */     int i = javaCharsToUTFE(arrayOfChar, 0, arrayOfChar.length, arrayOfByte1, 0);
/* 487 */     byte[] arrayOfByte2 = new byte[i];
/*     */ 
/* 489 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
/*     */ 
/* 491 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString)
/*     */     throws SQLException
/*     */   {
/* 506 */     return convertWithReplacement(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 515 */     if (paramCharacterSet.rep == 3)
/*     */     {
/* 517 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/* 522 */       String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 524 */       arrayOfByte = convert(str);
/*     */     }
/*     */ 
/* 527 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   int javaCharsToUTFE(char[] paramArrayOfChar, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
/*     */   {
/* 534 */     int i = paramInt1 + paramInt2;
/*     */ 
/* 536 */     int k = 0;
/*     */ 
/* 538 */     for (int m = paramInt1; m < i; m++)
/*     */     {
/* 541 */       int n = paramArrayOfChar[m];
/*     */       int j;
/* 543 */       if (n <= 31)
/*     */       {
/* 547 */         j = n | 0x80;
/* 548 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/*     */       }
/* 550 */       else if (n <= 127)
/*     */       {
/* 554 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(n)][low(n)];
/*     */       }
/* 556 */       else if (n <= 1023)
/*     */       {
/* 560 */         j = (n & 0x3E0) >> 5 | 0xC0;
/* 561 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 562 */         j = n & 0x1F | 0xA0;
/* 563 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/*     */       }
/* 565 */       else if (n <= 16383)
/*     */       {
/* 569 */         j = (n & 0x3C00) >> 10 | 0xE0;
/* 570 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 571 */         j = (n & 0x3E0) >> 5 | 0xA0;
/* 572 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 573 */         j = n & 0x1F | 0xA0;
/* 574 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/*     */       }
/*     */       else
/*     */       {
/* 580 */         j = (n & 0x8000) >> 15 | 0xF0;
/* 581 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 582 */         j = (n & 0x7C00) >> 10 | 0xA0;
/* 583 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 584 */         j = (n & 0x3E0) >> 5 | 0xA0;
/* 585 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/* 586 */         j = n & 0x1F | 0xA0;
/* 587 */         paramArrayOfByte[(k++)] = utf8m2utfe[high(j)][low(j)];
/*     */       }
/*     */     }
/*     */ 
/* 591 */     return k;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 597 */     byte[] arrayOfByte = paramCharacterWalker.bytes;
/* 598 */     int i = paramCharacterWalker.next;
/* 599 */     int j = paramCharacterWalker.end;
/* 600 */     int k = 0;
/*     */ 
/* 602 */     if (i >= j)
/*     */     {
/* 604 */       failUTFConversion();
/*     */     }
/*     */ 
/* 607 */     int m = arrayOfByte[i];
/* 608 */     int n = getUTFByteLength((byte)m);
/*     */ 
/* 610 */     if ((n == 0) || (i + (n - 1) >= j))
/*     */     {
/* 612 */       failUTFConversion();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 618 */       char[] arrayOfChar = new char[2];
/* 619 */       int i1 = UTFEToJavaChar(arrayOfByte, i, n, arrayOfChar, CharacterSet.CharacterConverterBehavior.REPORT_ERROR);
/*     */ 
/* 622 */       paramCharacterWalker.next += n;
/*     */ 
/* 624 */       if (i1 == 1)
/*     */       {
/* 627 */         return arrayOfChar[0];
/*     */       }
/*     */ 
/* 632 */       return arrayOfChar[0] << '\020' | arrayOfChar[1];
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 637 */       failUTFConversion();
/*     */ 
/* 640 */       paramCharacterWalker.next = i;
/*     */     }
/* 642 */     return k;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 650 */     if ((paramInt & 0xFFFF0000) != 0)
/*     */     {
/* 652 */       failUTFConversion();
/*     */     }
/*     */     else
/*     */     {
/* 657 */       char[] arrayOfChar = { (char)paramInt };
/*     */       int i;
/* 662 */       if (paramInt <= 31)
/*     */       {
/* 666 */         need(paramCharacterBuffer, 1);
/*     */ 
/* 668 */         i = paramInt | 0x80;
/* 669 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/*     */       }
/* 671 */       else if (paramInt <= 127)
/*     */       {
/* 675 */         need(paramCharacterBuffer, 1);
/*     */ 
/* 677 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(paramInt)][low(paramInt)];
/*     */       }
/* 679 */       else if (paramInt <= 1023)
/*     */       {
/* 683 */         need(paramCharacterBuffer, 2);
/*     */ 
/* 685 */         i = (paramInt & 0x3E0) >> 5 | 0xC0;
/* 686 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 687 */         i = paramInt & 0x1F | 0xA0;
/* 688 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/*     */       }
/* 690 */       else if (paramInt <= 16383)
/*     */       {
/* 694 */         need(paramCharacterBuffer, 3);
/*     */ 
/* 696 */         i = (paramInt & 0x3C00) >> 10 | 0xE0;
/* 697 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 698 */         i = (paramInt & 0x3E0) >> 5 | 0xA0;
/* 699 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 700 */         i = paramInt & 0x1F | 0xA0;
/* 701 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/*     */       }
/*     */       else
/*     */       {
/* 707 */         need(paramCharacterBuffer, 4);
/*     */ 
/* 709 */         i = (paramInt & 0x8000) >> 15 | 0xF0;
/* 710 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 711 */         i = (paramInt & 0x7C00) >> 10 | 0xA0;
/* 712 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 713 */         i = (paramInt & 0x3E0) >> 5 | 0xA0;
/* 714 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/* 715 */         i = paramInt & 0x1F | 0xA0;
/* 716 */         paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = utf8m2utfe[high(i)][low(i)];
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int high(int paramInt)
/*     */   {
/* 724 */     return paramInt >> 4 & 0xF;
/*     */   }
/*     */ 
/*     */   private static int low(int paramInt)
/*     */   {
/* 730 */     return paramInt & 0xF;
/*     */   }
/*     */ 
/*     */   private static boolean is101xxxxx(byte paramByte)
/*     */   {
/* 736 */     return (paramByte & 0xFFFFFFE0) == -96;
/*     */   }
/*     */ 
/*     */   private static int getUTFByteLength(byte paramByte)
/*     */   {
/* 754 */     return m_byteLen[(utfe2utf8m[high(paramByte)][low(paramByte)] >>> 4 & 0xF)];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetUTFE
 * JD-Core Version:    0.6.0
 */