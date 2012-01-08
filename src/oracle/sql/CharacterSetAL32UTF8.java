/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetAL32UTF8 extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*  57 */   private static int[] m_byteLen = { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 2, 2, 3, 4 };
/*     */ 
/*     */   CharacterSetAL32UTF8(int paramInt)
/*     */   {
/*  64 */     super(paramInt);
/*     */ 
/*  66 */     this.rep = 6;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  73 */     return !paramCharacterSet.isUnicode();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  80 */     int i = paramCharacterSet.rep <= 1024 ? 1 : 0;
/*     */ 
/*  82 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isUnicode()
/*     */   {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/* 116 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/*     */ 
/* 122 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 124 */       arrayOfInt[0] = paramInt2;
/*     */ 
/* 126 */       int i = CharacterSet.convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, true);
/*     */ 
/* 129 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 137 */     return "";
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 170 */       char[] arrayOfChar = new char[paramArrayOfByte.length];
/* 171 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 173 */       arrayOfInt[0] = paramInt2;
/*     */ 
/* 175 */       int i = CharacterSet.convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, arrayOfInt, false);
/*     */ 
/* 178 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 182 */       failUTFConversion();
/*     */     }
/* 184 */     return "";
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 208 */     return stringToAL32UTF8(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString)
/*     */     throws SQLException
/*     */   {
/* 230 */     return stringToAL32UTF8(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 252 */     if (paramCharacterSet.rep == 6)
/*     */     {
/* 254 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/* 259 */       String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 261 */       arrayOfByte = stringToAL32UTF8(str);
/*     */     }
/*     */ 
/* 264 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 288 */     byte[] arrayOfByte = paramCharacterWalker.bytes;
/* 289 */     int i = paramCharacterWalker.next;
/* 290 */     int j = paramCharacterWalker.end;
/*     */ 
/* 292 */     if (i >= j)
/*     */     {
/* 294 */       failUTFConversion();
/*     */     }
/*     */ 
/* 297 */     int k = arrayOfByte[i];
/* 298 */     int m = getUTFByteLength((byte)k);
/*     */ 
/* 300 */     if ((m == 0) || (i + (m - 1) >= j))
/*     */     {
/* 302 */       failUTFConversion();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 308 */       char[] arrayOfChar = new char[2];
/* 309 */       int[] arrayOfInt = new int[1];
/*     */ 
/* 311 */       arrayOfInt[0] = m;
/*     */ 
/* 313 */       int n = CharacterSet.convertAL32UTF8BytesToJavaChars(arrayOfByte, i, arrayOfChar, 0, arrayOfInt, false);
/*     */ 
/* 316 */       paramCharacterWalker.next += m;
/*     */ 
/* 318 */       if (n == 1)
/*     */       {
/* 321 */         return arrayOfChar[0];
/*     */       }
/*     */ 
/* 326 */       return arrayOfChar[0] << '\020' | arrayOfChar[1];
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 331 */       failUTFConversion();
/*     */     }
/*     */ 
/* 334 */     return 0;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     char[] arrayOfChar;
/*     */     int i;
/* 357 */     if ((paramInt & 0xFFFF0000) != 0)
/*     */     {
/* 359 */       need(paramCharacterBuffer, 4);
/*     */ 
/* 361 */       arrayOfChar = new char[] { (char)(paramInt >>> 16), (char)paramInt };
/*     */ 
/* 365 */       i = CharacterSet.convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 2);
/*     */     }
/*     */     else
/*     */     {
/* 370 */       need(paramCharacterBuffer, 3);
/*     */ 
/* 372 */       arrayOfChar = new char[] { (char)paramInt };
/*     */ 
/* 376 */       i = CharacterSet.convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, paramCharacterBuffer.bytes, paramCharacterBuffer.next, 1);
/*     */     }
/*     */ 
/* 380 */     paramCharacterBuffer.next += i;
/*     */   }
/*     */ 
/*     */   private static int getUTFByteLength(byte paramByte)
/*     */   {
/* 398 */     return m_byteLen[(paramByte >>> 4 & 0xF)];
/*     */   }
/*     */ 
/*     */   public int encodedByteLength(String paramString)
/*     */   {
/* 411 */     return CharacterSet.string32UTF8Length(paramString);
/*     */   }
/*     */ 
/*     */   public int encodedByteLength(char[] paramArrayOfChar)
/*     */   {
/* 423 */     return CharacterSet.charArray32UTF8Length(paramArrayOfChar);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetAL32UTF8
 * JD-Core Version:    0.6.0
 */