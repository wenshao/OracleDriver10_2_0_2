/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DBConversion;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.util.RepConversion;
/*     */ 
/*     */ public class RAW extends Datum
/*     */ {
/* 546 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   static int hexDigit2Nibble(char paramChar)
/*     */     throws SQLException
/*     */   {
/*  65 */     int i = Character.digit(paramChar, 16);
/*     */ 
/*  67 */     if (i == -1)
/*     */     {
/*  69 */       throw new SQLException("Invalid hex digit: " + paramChar);
/*     */     }
/*     */ 
/*  75 */     return i;
/*     */   }
/*     */ 
/*     */   public static byte[] hexString2Bytes(String paramString)
/*     */     throws SQLException
/*     */   {
/*  95 */     int i = paramString.length();
/*  96 */     char[] arrayOfChar = new char[i];
/*     */ 
/*  98 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */ 
/* 101 */     int j = 0;
/* 102 */     int k = 0;
/*     */ 
/* 104 */     if (i == 0)
/* 105 */       return new byte[0];
/*     */     byte[] arrayOfByte;
/* 107 */     if (i % 2 > 0)
/*     */     {
/* 109 */       arrayOfByte = new byte[(i + 1) / 2];
/* 110 */       arrayOfByte[(j++)] = (byte)hexDigit2Nibble(arrayOfChar[(k++)]);
/*     */     }
/*     */     else
/*     */     {
/* 114 */       arrayOfByte = new byte[i / 2];
/*     */     }
/*     */ 
/* 117 */     for (; j < arrayOfByte.length; j++)
/*     */     {
/* 119 */       arrayOfByte[j] = (byte)(hexDigit2Nibble(arrayOfChar[(k++)]) << 4 | hexDigit2Nibble(arrayOfChar[(k++)]));
/*     */     }
/*     */ 
/* 126 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public static RAW newRAW(Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 158 */     RAW localRAW = new RAW(paramObject);
/*     */ 
/* 163 */     return localRAW;
/*     */   }
/*     */ 
/*     */   public static RAW oldRAW(Object paramObject)
/*     */     throws SQLException
/*     */   {
/*     */     RAW localRAW;
/* 195 */     if ((paramObject instanceof String))
/*     */     {
/* 197 */       String str = (String)paramObject;
/* 198 */       byte[] arrayOfByte = null;
/*     */       try
/*     */       {
/* 202 */         arrayOfByte = str.getBytes("ISO8859_1");
/*     */       }
/*     */       catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*     */       {
/* 211 */         DatabaseError.throwSqlException(109, "ISO8859_1 character encoding not found");
/*     */       }
/*     */ 
/* 215 */       localRAW = new RAW(arrayOfByte);
/*     */     }
/*     */     else
/*     */     {
/* 219 */       localRAW = new RAW(paramObject);
/*     */     }
/*     */ 
/* 225 */     return localRAW;
/*     */   }
/*     */ 
/*     */   public RAW()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RAW(byte[] paramArrayOfByte)
/*     */   {
/* 255 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public RAW(Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 283 */     this();
/*     */ 
/* 288 */     if ((paramObject instanceof byte[]))
/*     */     {
/* 290 */       setShareBytes((byte[])paramObject);
/*     */     }
/* 292 */     else if ((paramObject instanceof String))
/*     */     {
/* 294 */       setShareBytes(hexString2Bytes((String)paramObject));
/*     */     }
/*     */     else
/*     */     {
/* 298 */       DatabaseError.throwSqlException(59, paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 325 */     return getBytes();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 344 */     String str = paramClass.getName();
/*     */ 
/* 350 */     return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.io.Reader") == 0);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 369 */     String str = RepConversion.bArray2String(getBytes());
/*     */ 
/* 373 */     return str;
/*     */   }
/*     */ 
/*     */   public Reader characterStreamValue()
/*     */     throws SQLException
/*     */   {
/* 388 */     int i = (int)getLength();
/* 389 */     char[] arrayOfChar = new char[i * 2];
/* 390 */     byte[] arrayOfByte = shareBytes();
/*     */ 
/* 392 */     DBConversion.RAWBytesToHexChars(arrayOfByte, i, arrayOfChar);
/*     */ 
/* 394 */     CharArrayReader localCharArrayReader = new CharArrayReader(arrayOfChar);
/*     */ 
/* 398 */     return localCharArrayReader;
/*     */   }
/*     */ 
/*     */   public InputStream asciiStreamValue()
/*     */     throws SQLException
/*     */   {
/* 413 */     int i = (int)getLength();
/* 414 */     char[] arrayOfChar = new char[i * 2];
/* 415 */     byte[] arrayOfByte1 = shareBytes();
/*     */ 
/* 417 */     DBConversion.RAWBytesToHexChars(arrayOfByte1, i, arrayOfChar);
/*     */ 
/* 419 */     byte[] arrayOfByte2 = new byte[i * 2];
/*     */ 
/* 421 */     DBConversion.javaCharsToAsciiBytes(arrayOfChar, i * 2, arrayOfByte2);
/*     */ 
/* 423 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte2);
/*     */ 
/* 427 */     return localByteArrayInputStream;
/*     */   }
/*     */ 
/*     */   public InputStream binaryStreamValue()
/*     */     throws SQLException
/*     */   {
/* 442 */     return getStream();
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 466 */     return new byte[paramInt][];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.RAW
 * JD-Core Version:    0.6.0
 */