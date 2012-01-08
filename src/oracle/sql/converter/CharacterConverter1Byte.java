/*     */ package oracle.sql.converter;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class CharacterConverter1Byte extends CharacterConverterJDBC
/*     */ {
/*     */   static final int ORACHARMASK = 255;
/*     */   static final int UCSCHARWIDTH = 16;
/* 112 */   public int m_ucsReplacement = 0;
/* 113 */   public int[] m_ucsChar = null;
/* 114 */   public char[] m_oraCharLevel1 = null;
/* 115 */   public char[] m_oraCharSurrogateLevel = null;
/* 116 */   public char[] m_oraCharLevel2 = null;
/* 117 */   public byte m_oraCharReplacement = 0;
/* 118 */   protected transient boolean noSurrogate = true;
/* 119 */   protected transient boolean strictASCII = true;
/* 120 */   protected transient int m_oraCharLevel2Size = 0;
/*     */ 
/*     */   public CharacterConverter1Byte()
/*     */   {
/* 127 */     this.m_groupId = 0;
/*     */   }
/*     */ 
/*     */   int toUnicode(byte paramByte)
/*     */     throws SQLException
/*     */   {
/* 143 */     int i = this.m_ucsChar[(paramByte & 0xFF)];
/*     */ 
/* 145 */     if (i == -1)
/*     */     {
/* 147 */       DatabaseError.throwSqlException(154);
/*     */ 
/* 150 */       return -1;
/*     */     }
/*     */ 
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */   int toUnicodeWithReplacement(byte paramByte)
/*     */   {
/* 165 */     int i = this.m_ucsChar[(paramByte & 0xFF)];
/*     */ 
/* 167 */     if (i == -1)
/*     */     {
/* 169 */       return this.m_ucsReplacement;
/*     */     }
/*     */ 
/* 173 */     return i;
/*     */   }
/*     */ 
/*     */   byte toOracleCharacter(char paramChar1, char paramChar2)
/*     */     throws SQLException
/*     */   {
/* 189 */     int i = paramChar1 >>> '\b' & 0xFF;
/* 190 */     int j = paramChar1 & 0xFF;
/* 191 */     int k = paramChar2 >>> '\b' & 0xFF;
/* 192 */     int m = paramChar2 & 0xFF;
/*     */ 
/* 194 */     if ((this.m_oraCharLevel1[i] != (char)this.m_oraCharLevel2Size) && (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] != 65535) && (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] != 65535) && (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] != 65535))
/*     */     {
/* 200 */       return (byte)this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)];
/*     */     }
/*     */ 
/* 204 */     DatabaseError.throwSqlException(155);
/*     */ 
/* 209 */     return 0;
/*     */   }
/*     */ 
/*     */   byte toOracleCharacter(char paramChar)
/*     */     throws SQLException
/*     */   {
/* 223 */     int i = paramChar >>> '\b';
/* 224 */     int j = paramChar & 0xFF;
/*     */     int k;
/* 227 */     if ((k = this.m_oraCharLevel2[(this.m_oraCharLevel1[i] + j)]) != 65535)
/*     */     {
/* 230 */       return (byte)k;
/*     */     }
/*     */ 
/* 234 */     DatabaseError.throwSqlException(155);
/*     */ 
/* 237 */     return 0;
/*     */   }
/*     */ 
/*     */   byte toOracleCharacterWithReplacement(char paramChar1, char paramChar2)
/*     */   {
/* 251 */     int i = paramChar1 >>> '\b' & 0xFF;
/* 252 */     int j = paramChar1 & 0xFF;
/* 253 */     int k = paramChar2 >>> '\b' & 0xFF;
/* 254 */     int m = paramChar2 & 0xFF;
/*     */ 
/* 256 */     if ((this.m_oraCharLevel1[i] != (char)this.m_oraCharLevel2Size) && (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] != 65535) && (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] != 65535) && (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] != 65535))
/*     */     {
/* 262 */       return (byte)this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)];
/*     */     }
/*     */ 
/* 266 */     return this.m_oraCharReplacement;
/*     */   }
/*     */ 
/*     */   byte toOracleCharacterWithReplacement(char paramChar)
/*     */   {
/* 280 */     int i = paramChar >>> '\b';
/* 281 */     int j = paramChar & 0xFF;
/*     */     int k;
/* 284 */     if ((k = this.m_oraCharLevel2[(this.m_oraCharLevel1[i] + j)]) != 65535)
/*     */     {
/* 287 */       return (byte)k;
/*     */     }
/*     */ 
/* 291 */     return this.m_oraCharReplacement;
/*     */   }
/*     */ 
/*     */   public String toUnicodeString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 309 */     int i = paramInt1 + paramInt2;
/*     */ 
/* 311 */     StringBuffer localStringBuffer = new StringBuffer(paramInt2);
/* 312 */     int j = 0;
/*     */ 
/* 316 */     for (int k = paramInt1; k < i; k++)
/*     */     {
/* 318 */       j = this.m_ucsChar[(paramArrayOfByte[k] & 0xFF)];
/*     */ 
/* 320 */       if (j == this.m_ucsReplacement)
/*     */       {
/* 322 */         DatabaseError.throwSqlException(154);
/*     */       }
/*     */ 
/* 327 */       if ((j & 0xFFFFFFFF) > 65535L)
/*     */       {
/* 329 */         localStringBuffer.append((char)(j >>> 16));
/* 330 */         localStringBuffer.append((char)(j & 0xFFFF));
/*     */       }
/*     */       else
/*     */       {
/* 334 */         localStringBuffer.append((char)j);
/*     */       }
/*     */     }
/*     */ 
/* 338 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public String toUnicodeStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 349 */     int i = paramInt1 + paramInt2;
/* 350 */     StringBuffer localStringBuffer = new StringBuffer(paramInt2);
/* 351 */     int j = 0;
/*     */ 
/* 355 */     for (int k = paramInt1; k < i; k++)
/*     */     {
/* 357 */       j = this.m_ucsChar[(paramArrayOfByte[k] & 0xFF)];
/*     */ 
/* 359 */       if (j == -1)
/* 360 */         localStringBuffer.append((char)this.m_ucsReplacement);
/*     */       else {
/* 362 */         localStringBuffer.append((char)j);
/*     */       }
/*     */     }
/* 365 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public byte[] toOracleString(String paramString)
/*     */     throws SQLException
/*     */   {
/* 377 */     int i = paramString.length();
/*     */ 
/* 379 */     if (i == 0)
/*     */     {
/* 381 */       return new byte[0];
/*     */     }
/*     */ 
/* 384 */     char[] arrayOfChar = new char[i];
/*     */ 
/* 386 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */ 
/* 388 */     byte[] arrayOfByte1 = new byte[i * 4];
/*     */ 
/* 390 */     int j = 0;
/*     */ 
/* 392 */     for (int k = 0; k < i; k++)
/*     */     {
/* 394 */       if ((arrayOfChar[k] >= 55296) && (arrayOfChar[k] < 56320))
/*     */       {
/* 396 */         if ((k + 1 < i) && (arrayOfChar[(k + 1)] >= 56320) && (arrayOfChar[(k + 1)] <= 57343))
/*     */         {
/* 401 */           if (this.noSurrogate)
/*     */           {
/* 403 */             DatabaseError.throwSqlException(155);
/*     */           }
/*     */           else
/*     */           {
/* 407 */             arrayOfByte1[(j++)] = toOracleCharacter(arrayOfChar[k], arrayOfChar[(k + 1)]);
/*     */           }
/*     */ 
/* 411 */           k++;
/*     */         }
/*     */         else
/*     */         {
/* 417 */           DatabaseError.throwSqlException(155);
/*     */         }
/*     */ 
/*     */       }
/* 422 */       else if ((arrayOfChar[k] < '') && (this.strictASCII))
/*     */       {
/* 424 */         arrayOfByte1[(j++)] = (byte)arrayOfChar[k];
/*     */       }
/*     */       else {
/* 427 */         arrayOfByte1[(j++)] = toOracleCharacter(arrayOfChar[k]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 432 */     if (j < arrayOfByte1.length)
/*     */     {
/* 436 */       byte[] arrayOfByte2 = new byte[j];
/*     */ 
/* 438 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
/*     */ 
/* 440 */       return arrayOfByte2;
/*     */     }
/*     */ 
/* 444 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */   public byte[] toOracleStringWithReplacement(String paramString)
/*     */   {
/* 455 */     int i = paramString.length();
/*     */ 
/* 457 */     if (i == 0)
/*     */     {
/* 459 */       return new byte[0];
/*     */     }
/*     */ 
/* 462 */     char[] arrayOfChar = new char[i];
/*     */ 
/* 464 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */ 
/* 466 */     byte[] arrayOfByte1 = new byte[i * 4];
/*     */ 
/* 468 */     int j = 0;
/*     */ 
/* 470 */     for (int k = 0; k < i; k++)
/*     */     {
/* 472 */       if ((arrayOfChar[k] >= 55296) && (arrayOfChar[k] < 56320))
/*     */       {
/* 474 */         if ((k + 1 < i) && (arrayOfChar[(k + 1)] >= 56320) && (arrayOfChar[(k + 1)] <= 57343))
/*     */         {
/* 479 */           if (this.noSurrogate)
/*     */           {
/* 481 */             arrayOfByte1[(j++)] = this.m_oraCharReplacement;
/*     */           }
/*     */           else
/*     */           {
/* 485 */             arrayOfByte1[(j++)] = toOracleCharacterWithReplacement(arrayOfChar[k], arrayOfChar[(k + 1)]);
/*     */           }
/*     */ 
/* 489 */           k++;
/*     */         }
/*     */         else
/*     */         {
/* 495 */           arrayOfByte1[(j++)] = this.m_oraCharReplacement;
/*     */         }
/*     */ 
/*     */       }
/* 502 */       else if ((arrayOfChar[k] < '') && (this.strictASCII))
/*     */       {
/* 504 */         arrayOfByte1[(j++)] = (byte)arrayOfChar[k];
/*     */       }
/*     */       else {
/* 507 */         arrayOfByte1[(j++)] = toOracleCharacterWithReplacement(arrayOfChar[k]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 512 */     if (j < arrayOfByte1.length)
/*     */     {
/* 516 */       byte[] arrayOfByte2 = new byte[j];
/*     */ 
/* 518 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
/*     */ 
/* 520 */       return arrayOfByte2;
/*     */     }
/*     */ 
/* 524 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */   public void buildUnicodeToOracleMapping()
/*     */   {
/* 557 */     this.m_oraCharLevel1 = new char[256];
/* 558 */     this.m_oraCharSurrogateLevel = null;
/* 559 */     this.m_oraCharLevel2 = null;
/*     */ 
/* 561 */     Vector localVector = new Vector(45055, 12287);
/* 562 */     Hashtable localHashtable1 = new Hashtable();
/* 563 */     Hashtable localHashtable2 = new Hashtable();
/*     */ 
/* 572 */     int i1 = this.m_ucsChar.length;
/* 573 */     int i2 = 0;
/* 574 */     int i3 = 0;
/*     */ 
/* 577 */     for (int i4 = 0; i4 < 256; i4++)
/*     */     {
/* 579 */       this.m_oraCharLevel1[i4] = 65535;
/*     */     }
/*     */     int n;
/* 582 */     for (i4 = 0; i4 < i1; i4++)
/*     */     {
/* 586 */       n = this.m_ucsChar[i4];
/*     */ 
/* 588 */       if (n == -1)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 593 */       localObject1 = new int[2];
/*     */ 
/* 595 */       localObject1[0] = n;
/* 596 */       localObject1[1] = i4;
/*     */ 
/* 598 */       localVector.addElement(localObject1);
/* 599 */       storeMappingRange(n, localHashtable1, localHashtable2);
/*     */     }
/*     */ 
/* 602 */     if (this.extraUnicodeToOracleMapping != null)
/*     */     {
/* 604 */       i1 = this.extraUnicodeToOracleMapping.length;
/*     */ 
/* 606 */       for (i4 = 0; i4 < i1; i4++)
/*     */       {
/* 610 */         n = this.extraUnicodeToOracleMapping[i4][0];
/*     */ 
/* 612 */         storeMappingRange(n, localHashtable1, localHashtable2);
/*     */       }
/*     */     }
/*     */ 
/* 616 */     Object localObject1 = localHashtable1.keys();
/*     */ 
/* 619 */     int i5 = 0;
/* 620 */     int i6 = 0;
/*     */     Object localObject2;
/*     */     char[] arrayOfChar;
/* 622 */     while (((Enumeration)localObject1).hasMoreElements())
/*     */     {
/* 624 */       localObject2 = ((Enumeration)localObject1).nextElement();
/* 625 */       arrayOfChar = (char[])localHashtable1.get(localObject2);
/*     */ 
/* 627 */       if (arrayOfChar == null)
/*     */       {
/*     */         continue;
/*     */       }
/* 631 */       i5 += 256;
/*     */     }
/*     */ 
/* 635 */     localObject1 = localHashtable2.keys();
/*     */ 
/* 637 */     while (((Enumeration)localObject1).hasMoreElements())
/*     */     {
/* 639 */       localObject2 = ((Enumeration)localObject1).nextElement();
/* 640 */       arrayOfChar = (char[])localHashtable2.get(localObject2);
/*     */ 
/* 642 */       if (arrayOfChar == null)
/*     */       {
/*     */         continue;
/*     */       }
/* 646 */       i6 += 256;
/*     */     }
/*     */ 
/* 650 */     if (i5 != 0)
/*     */     {
/* 652 */       this.m_oraCharSurrogateLevel = new char[i5];
/*     */     }
/*     */ 
/* 655 */     if (i6 != 0)
/*     */     {
/* 657 */       this.m_oraCharLevel2 = new char[i6 + 256];
/*     */     }
/*     */ 
/* 660 */     for (i4 = 0; i4 < i5; i4++)
/*     */     {
/* 662 */       this.m_oraCharSurrogateLevel[i4] = 65535;
/*     */     }
/*     */ 
/* 665 */     for (i4 = 0; i4 < i6 + 256; i4++)
/*     */     {
/* 667 */       this.m_oraCharLevel2[i4] = 65535;
/*     */     }
/*     */     int i;
/*     */     int j;
/*     */     int k;
/*     */     int m;
/* 670 */     for (i4 = 0; i4 < localVector.size(); i4++)
/*     */     {
/* 672 */       int[] arrayOfInt = (int[])localVector.elementAt(i4);
/*     */ 
/* 674 */       i = arrayOfInt[0] >> 24 & 0xFF;
/* 675 */       j = arrayOfInt[0] >> 16 & 0xFF;
/* 676 */       k = arrayOfInt[0] >> 8 & 0xFF;
/* 677 */       m = arrayOfInt[0] & 0xFF;
/*     */ 
/* 679 */       if ((i >= 216) && (i < 220))
/*     */       {
/* 681 */         if (this.m_oraCharLevel1[i] == 65535)
/*     */         {
/* 683 */           this.m_oraCharLevel1[i] = i3;
/* 684 */           i3 = (char)(i3 + 256);
/*     */         }
/*     */ 
/* 687 */         if (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] == 65535)
/*     */         {
/* 690 */           this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] = i3;
/*     */ 
/* 692 */           i3 = (char)(i3 + 256);
/*     */         }
/*     */ 
/* 695 */         if (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] == 65535)
/*     */         {
/* 698 */           this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] = i2;
/*     */ 
/* 700 */           i2 = (char)(i2 + 256);
/*     */         }
/*     */ 
/* 703 */         if (this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] != 65535) {
/*     */           continue;
/*     */         }
/* 706 */         this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] = (char)(arrayOfInt[1] & 0xFFFF);
/*     */       }
/*     */       else
/*     */       {
/* 717 */         if (this.m_oraCharLevel1[k] == 65535)
/*     */         {
/* 719 */           this.m_oraCharLevel1[k] = i2;
/* 720 */           i2 = (char)(i2 + 256);
/*     */         }
/*     */ 
/* 723 */         if (this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] != 65535) {
/*     */           continue;
/*     */         }
/* 726 */         this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] = (char)(arrayOfInt[1] & 0xFFFF);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 737 */     if (this.extraUnicodeToOracleMapping != null)
/*     */     {
/* 739 */       i1 = this.extraUnicodeToOracleMapping.length;
/*     */ 
/* 741 */       for (i4 = 0; i4 < i1; i4++)
/*     */       {
/* 745 */         n = this.extraUnicodeToOracleMapping[i4][0];
/*     */ 
/* 749 */         i = n >>> 24 & 0xFF;
/* 750 */         j = n >>> 16 & 0xFF;
/* 751 */         k = n >>> 8 & 0xFF;
/* 752 */         m = n & 0xFF;
/*     */ 
/* 754 */         if ((i >= 216) && (i < 220))
/*     */         {
/* 756 */           if (this.m_oraCharLevel1[i] == 65535)
/*     */           {
/* 760 */             this.m_oraCharLevel1[i] = i3;
/* 761 */             i3 = (char)(i3 + 256);
/*     */           }
/*     */ 
/* 764 */           if (this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] == 65535)
/*     */           {
/* 767 */             this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] = i3;
/*     */ 
/* 769 */             i3 = (char)(i3 + 256);
/*     */           }
/*     */ 
/* 772 */           if (this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] == 65535)
/*     */           {
/* 775 */             this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] = i2;
/*     */ 
/* 777 */             i2 = (char)(i2 + 256);
/*     */           }
/*     */ 
/* 780 */           this.m_oraCharLevel2[(this.m_oraCharSurrogateLevel[(this.m_oraCharSurrogateLevel[(this.m_oraCharLevel1[i] + j)] + k)] + m)] = (char)(this.extraUnicodeToOracleMapping[i4][1] & 0xFF);
/*     */         }
/*     */         else
/*     */         {
/* 785 */           if (this.m_oraCharLevel1[k] == 65535)
/*     */           {
/* 787 */             this.m_oraCharLevel1[k] = i2;
/* 788 */             i2 = (char)(i2 + 256);
/*     */           }
/*     */ 
/* 791 */           this.m_oraCharLevel2[(this.m_oraCharLevel1[k] + m)] = (char)(this.extraUnicodeToOracleMapping[i4][1] & 0xFFFF);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 797 */     if (this.m_oraCharSurrogateLevel == null)
/* 798 */       this.noSurrogate = true;
/*     */     else {
/* 800 */       this.noSurrogate = false;
/*     */     }
/* 802 */     this.strictASCII = true;
/*     */ 
/* 804 */     for (i4 = 0; i4 < 128; i4++)
/*     */     {
/* 806 */       if (this.m_oraCharLevel2[i4] == i4)
/*     */         continue;
/* 808 */       this.strictASCII = false;
/*     */ 
/* 810 */       break;
/*     */     }
/*     */ 
/* 814 */     for (i4 = 0; i4 < 256; i4++)
/*     */     {
/* 816 */       if (this.m_oraCharLevel1[i4] == 65535) {
/* 817 */         this.m_oraCharLevel1[i4] = (char)i6;
/*     */       }
/*     */     }
/* 820 */     this.m_oraCharLevel2Size = i6;
/*     */   }
/*     */ 
/*     */   public void extractCodepoints(Vector paramVector)
/*     */   {
/* 828 */     int i = 0;
/* 829 */     int j = 255;
/*     */ 
/* 832 */     for (int k = i; k <= j; k++)
/*     */     {
/*     */       try
/*     */       {
/* 836 */         int[] arrayOfInt = new int[2];
/* 837 */         arrayOfInt[0] = k;
/* 838 */         arrayOfInt[1] = toUnicode((byte)k);
/*     */ 
/* 842 */         paramVector.addElement(arrayOfInt);
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void extractExtraMappings(Vector paramVector)
/*     */   {
/* 857 */     if (this.extraUnicodeToOracleMapping == null)
/*     */     {
/* 859 */       return;
/*     */     }
/*     */ 
/* 864 */     for (int i = 0; i < this.extraUnicodeToOracleMapping.length; i++)
/*     */     {
/* 866 */       int[] arrayOfInt = new int[2];
/* 867 */       arrayOfInt[0] = this.extraUnicodeToOracleMapping[i][0];
/* 868 */       arrayOfInt[1] = this.extraUnicodeToOracleMapping[i][1];
/*     */ 
/* 870 */       paramVector.addElement(arrayOfInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hasExtraMappings()
/*     */   {
/* 876 */     return this.extraUnicodeToOracleMapping != null;
/*     */   }
/*     */ 
/*     */   public char getOraChar1ByteRep()
/*     */   {
/* 881 */     return (char)this.m_oraCharReplacement;
/*     */   }
/*     */ 
/*     */   public char getOraChar2ByteRep()
/*     */   {
/* 886 */     return '\000';
/*     */   }
/*     */ 
/*     */   public int getUCS2CharRep()
/*     */   {
/* 891 */     return this.m_ucsReplacement;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterConverter1Byte
 * JD-Core Version:    0.6.0
 */