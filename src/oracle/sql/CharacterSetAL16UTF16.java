/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetAL16UTF16 extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*     */   CharacterSetAL16UTF16(int paramInt)
/*     */   {
/*  55 */     super(paramInt);
/*     */ 
/*  57 */     this.rep = 4;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  64 */     return !paramCharacterSet.isUnicode();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  71 */     int i = paramCharacterSet.rep <= 1024 ? 1 : 0;
/*     */ 
/*  73 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isUnicode()
/*     */   {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/*  90 */       char[] arrayOfChar = new char[paramInt2 >>> 1];
/*  91 */       int i = CharacterSet.convertAL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, true);
/*     */ 
/*  94 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 102 */     return "";
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 116 */       char[] arrayOfChar = new char[paramInt2 >>> 1];
/*     */ 
/* 121 */       int i = CharacterSet.convertAL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, false);
/*     */ 
/* 124 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 128 */       failUTFConversion();
/*     */     }
/* 130 */     return "";
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString)
/*     */     throws SQLException
/*     */   {
/* 136 */     return stringToAL16UTF16Bytes(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 141 */     return stringToAL16UTF16Bytes(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 149 */     if (paramCharacterSet.rep == 4)
/*     */     {
/* 151 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/* 155 */       String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 157 */       arrayOfByte = stringToAL16UTF16Bytes(str);
/*     */     }
/*     */ 
/* 160 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 172 */     byte[] arrayOfByte = paramCharacterWalker.bytes;
/* 173 */     int k = paramCharacterWalker.next;
/* 174 */     int m = paramCharacterWalker.end;
/*     */ 
/* 177 */     if (k + 2 >= m)
/*     */     {
/* 179 */       failUTFConversion();
/*     */     }
/*     */ 
/* 182 */     int i = arrayOfByte[(k++)];
/* 183 */     int j = arrayOfByte[(k++)];
/* 184 */     int n = i << 8 & 0xFF00 | j;
/* 185 */     paramCharacterWalker.next = k;
/*     */ 
/* 187 */     return n;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 192 */     if (paramInt > 65535)
/*     */     {
/* 194 */       failUTFConversion();
/*     */     }
/*     */     else
/*     */     {
/* 198 */       need(paramCharacterBuffer, 2);
/*     */ 
/* 200 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> 8 & 0xFF);
/* 201 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt & 0xFF);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetAL16UTF16
 * JD-Core Version:    0.6.0
 */