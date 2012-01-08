/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class CharacterSetAL16UTF16LE extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*     */   CharacterSetAL16UTF16LE(int paramInt)
/*     */   {
/*  54 */     super(paramInt);
/*     */ 
/*  56 */     this.rep = 5;
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  63 */     return !paramCharacterSet.isUnicode();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/*  70 */     int i = paramCharacterSet.rep <= 1024 ? 1 : 0;
/*     */ 
/*  72 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isUnicode()
/*     */   {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/*  85 */       char[] arrayOfChar = new char[Math.min(paramArrayOfByte.length - paramInt1 >>> 1, paramInt2 >>> 1)];
/*     */ 
/*  87 */       int i = CharacterSet.convertAL16UTF16LEBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, true);
/*     */ 
/*  90 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/*  98 */     return "";
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 113 */       char[] arrayOfChar = new char[Math.min(paramArrayOfByte.length - paramInt1 >>> 1, paramInt2 >>> 1)];
/*     */ 
/* 115 */       int i = CharacterSet.convertAL16UTF16LEBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar, 0, paramInt2, false);
/*     */ 
/* 118 */       return new String(arrayOfChar, 0, i);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 122 */       failUTFConversion();
/*     */     }
/* 124 */     return "";
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString)
/*     */     throws SQLException
/*     */   {
/* 130 */     return stringToAL16UTF16LEBytes(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 135 */     return stringToAL16UTF16LEBytes(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*     */     byte[] arrayOfByte;
/* 143 */     if (paramCharacterSet.rep == 5)
/*     */     {
/* 145 */       arrayOfByte = useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */     else
/*     */     {
/* 149 */       String str = paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 151 */       arrayOfByte = stringToAL16UTF16LEBytes(str);
/*     */     }
/*     */ 
/* 154 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker)
/*     */     throws SQLException
/*     */   {
/* 166 */     byte[] arrayOfByte = paramCharacterWalker.bytes;
/* 167 */     int k = paramCharacterWalker.next;
/* 168 */     int m = paramCharacterWalker.end;
/*     */ 
/* 171 */     if (k + 2 >= m)
/*     */     {
/* 173 */       failUTFConversion();
/*     */     }
/*     */ 
/* 176 */     int i = arrayOfByte[(k++)];
/* 177 */     int j = arrayOfByte[(k++)];
/* 178 */     int n = i << 8 & 0xFF00 | j;
/* 179 */     paramCharacterWalker.next = k;
/*     */ 
/* 181 */     return n;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 186 */     if (paramInt > 65535)
/*     */     {
/* 188 */       failUTFConversion();
/*     */     }
/*     */     else
/*     */     {
/* 192 */       need(paramCharacterBuffer, 2);
/*     */ 
/* 194 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt >> 8 & 0xFF);
/* 195 */       paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte)(paramInt & 0xFF);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetAL16UTF16LE
 * JD-Core Version:    0.6.0
 */