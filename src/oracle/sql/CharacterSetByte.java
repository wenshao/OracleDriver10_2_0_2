/*     */ package oracle.sql;
/*     */ 
/*     */ import B;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetByte extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*     */   CharacterSetByte(int paramInt)
/*     */   {
/* 231 */     super(paramInt);
/*     */ 
/* 233 */     this.rep = 1;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 238 */     return paramCharacterSet.rep != 1;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 243 */     return paramCharacterSet.rep <= 1024;
/*     */   }
/*     */ 
/*     */   private String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, char paramChar)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 251 */       return new String(paramArrayOfByte, paramInt1, paramInt2, "ASCII");
/*     */     }
/*     */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*     */     {
/*     */     }
/*     */ 
/* 257 */     throw new SQLException("ascii not supported");
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/* 265 */       return toString(paramArrayOfByte, paramInt1, paramInt2, '?');
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 271 */     throw new Error("CharacterSetByte.toString");
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 278 */     return toString(paramArrayOfByte, paramInt1, paramInt2, '\000');
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString) throws SQLException
/*     */   {
/* 283 */     int i = paramString.length();
/* 284 */     char[] arrayOfChar = new char[paramString.length()];
/*     */ 
/* 286 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */ 
/* 288 */     return charsToBytes(arrayOfChar, 0);
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 293 */     int i = paramString.length();
/* 294 */     char[] arrayOfChar = new char[paramString.length()];
/*     */ 
/* 296 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */     try
/*     */     {
/* 300 */       return charsToBytes(arrayOfChar, 63);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 306 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 315 */     if (paramCharacterSet.rep == 1)
/*     */     {
/* 317 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject;
/* 319 */       if (paramCharacterSet.rep == 2)
/*     */       {
/* 321 */         localObject = CharacterSetUTF.UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 323 */         arrayOfByte = charsToBytes(localObject, 0);
/*     */       }
/*     */       else
/*     */       {
/* 327 */         localObject = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/* 328 */         char[] arrayOfChar = ((String)localObject).toCharArray();
/*     */ 
/* 330 */         arrayOfByte = charsToBytes(arrayOfChar, 0);
/*     */       }
/*     */     }
/* 333 */     return (B)arrayOfByte;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */   {
/* 338 */     int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
/*     */ 
/* 340 */     paramCharacterWalker.next += 1;
/*     */ 
/* 342 */     return i;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 347 */     need(paramCharacterBuffer, 1);
/*     */ 
/* 349 */     if (paramInt < 256)
/*     */     {
/* 351 */       paramCharacterBuffer.bytes[paramCharacterBuffer.next] = (byte)paramInt;
/* 352 */       paramCharacterBuffer.next += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   static byte[] charsToBytes(char[] paramArrayOfChar, byte paramByte)
/*     */     throws SQLException
/*     */   {
/* 363 */     byte[] arrayOfByte = new byte[paramArrayOfChar.length];
/*     */ 
/* 365 */     for (int i = 0; i < paramArrayOfChar.length; i++)
/*     */     {
/* 367 */       if (paramArrayOfChar[i] > 'ÿ')
/*     */       {
/* 369 */         arrayOfByte[i] = paramByte;
/*     */ 
/* 371 */         if (paramByte != 0)
/*     */           continue;
/* 373 */         failCharacterConversion(CharacterSet.make(31));
/*     */       }
/*     */       else
/*     */       {
/* 378 */         arrayOfByte[i] = (byte)paramArrayOfChar[i];
/*     */       }
/*     */     }
/*     */ 
/* 382 */     return arrayOfByte;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetByte
 * JD-Core Version:    0.6.0
 */