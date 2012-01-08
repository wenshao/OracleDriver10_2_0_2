/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetUTF extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*  53 */   private static int[] m_byteLen = { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 2, 2, 3, 0 };
/*     */ 
/*     */   CharacterSetUTF(int paramInt)
/*     */   {
/*  65 */     super(paramInt);
/*     */ 
/*  67 */     this.rep = 2;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  74 */     return !paramCharacterSet.isUnicode();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  81 */     int i = paramCharacterSet.rep <= 1024 ? 1 : 0;
/*     */ 
/*  83 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isUnicode()
/*     */   {
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/* 118 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/* 119 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 121 */       arrayOfInt[0] = paramInt2;
/*     */ 
/* 123 */       int i = CharacterSet.convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, true);
/*     */ 
/* 126 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 134 */     return "";
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 167 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/* 168 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 170 */       arrayOfInt[0] = paramInt2;
/*     */ 
/* 172 */       int i = CharacterSet.convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, false);
/*     */ 
/* 175 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 179 */       failUTFConversion();
/*     */     }
/* 181 */     return "";
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 205 */     return stringToUTF(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString)
/*     */     throws SQLException
/*     */   {
/* 227 */     return stringToUTF(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 249 */     if (paramCharacterSet.rep == 2)
/*     */     {
/* 251 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/* 256 */       String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 258 */       arrayOfByte = stringToUTF(str);
/*     */     }
/*     */ 
/* 261 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 285 */     byte[] arrayOfByte = paramCharacterWalker.bytes;
/* 286 */     int i = paramCharacterWalker.next;
/* 287 */     int j = paramCharacterWalker.end;
/*     */ 
/* 289 */     if (i >= j)
/*     */     {
/* 291 */       failUTFConversion();
/*     */     }
/*     */ 
/* 294 */     int k = arrayOfByte[i];
/* 295 */     int m = getUTFByteLength((byte)k);
/*     */ 
/* 297 */     if ((m == 0) || (i + (m - 1) >= j))
/*     */     {
/* 299 */       failUTFConversion();
/*     */     }
/*     */ 
/* 303 */     if ((m == 3) && (isHiSurrogate((byte)k, arrayOfByte[(i + 1)])) && (i + 5 < j))
/*     */     {
/* 306 */       m = 6;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 312 */       char[] arrayOfChar = new char[2];
/* 313 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 315 */       arrayOfInt[0] = m;
/*     */ 
/* 317 */       int n = CharacterSet.convertUTFBytesToJavaChars(arrayOfByte, i, arrayOfChar, 0, arrayOfInt, false);
/*     */ 
/* 320 */       paramCharacterWalker.next += m;
/*     */ 
/* 322 */       if (n == 1)
/*     */       {
/* 325 */         return arrayOfChar[0];
/*     */       }
/*     */ 
/* 330 */       return arrayOfChar[0] << '\020' | arrayOfChar[1];
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 335 */       failUTFConversion();
/*     */     }
/*     */ 
/* 338 */     return 0;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     char[] arrayOfChar;
/*     */     int i;
/* 361 */     if ((paramInt & 0xFFFF0000) != 0)
/*     */     {
/* 363 */       need(paramCharacterBuffer, 6);
/*     */ 
/* 365 */       arrayOfChar = new char[] { (char)(paramInt >>> 16), (char)paramInt };
/*     */ 
/* 369 */       i = CharacterSet.convertJavaCharsToUTFBytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 2);
/*     */     }
/*     */     else
/*     */     {
/* 374 */       need(paramCharacterBuffer, 3);
/*     */ 
/* 376 */       arrayOfChar = new char[] { (char)paramInt };
/*     */ 
/* 380 */       i = CharacterSet.convertJavaCharsToUTFBytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 1);
/*     */     }
/*     */ 
/* 384 */     paramCharacterBuffer.next += i;
/*     */   }
/*     */ 
/*     */   private static int getUTFByteLength(byte paramByte)
/*     */   {
/* 402 */     return m_byteLen[(paramByte >>> 4 & 0xF)];
/*     */   }
/*     */ 
/*     */   private static boolean isHiSurrogate(byte paramByte1, byte paramByte2)
/*     */   {
/* 407 */     return (paramByte1 == -19) && (paramByte2 >= -96);
/*     */   }
/*     */ 
/*     */   public int encodedByteLength(String paramString)
/*     */   {
/* 420 */     return CharacterSet.stringUTFLength(paramString);
/*     */   }
/*     */ 
/*     */   public int encodedByteLength(char[] paramArrayOfChar)
/*     */   {
/* 433 */     return CharacterSet.charArrayUTF8Length(paramArrayOfChar);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetUTF
 * JD-Core Version:    0.6.0
 */