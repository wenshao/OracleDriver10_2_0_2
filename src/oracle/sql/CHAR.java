/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class CHAR extends Datum
/*     */ {
/*  65 */   public static final CharacterSet DEFAULT_CHARSET = CharacterSet.make(-1);
/*     */   private CharacterSet charSet;
/*     */   private int oracleId;
/* 325 */   private static final byte[] empty = new byte[0];
/*     */ 
/* 825 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   protected CHAR()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CHAR(byte[] paramArrayOfByte, CharacterSet paramCharacterSet)
/*     */   {
/* 110 */     setValue(paramArrayOfByte, paramCharacterSet);
/*     */   }
/*     */ 
/*     */   public CHAR(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CharacterSet paramCharacterSet)
/*     */   {
/* 130 */     byte[] arrayOfByte = new byte[paramInt2];
/*     */ 
/* 132 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/* 133 */     setValue(arrayOfByte, paramCharacterSet);
/*     */   }
/*     */ 
/*     */   public CHAR(String paramString, CharacterSet paramCharacterSet)
/*     */     throws SQLException
/*     */   {
/* 154 */     if (paramCharacterSet == null)
/*     */     {
/* 156 */       paramCharacterSet = DEFAULT_CHARSET;
/*     */     }
/*     */ 
/* 159 */     setValue(paramCharacterSet.convertWithReplacement(paramString), paramCharacterSet);
/*     */   }
/*     */ 
/*     */   public CHAR(Object paramObject, CharacterSet paramCharacterSet)
/*     */     throws SQLException
/*     */   {
/* 179 */     this(paramObject.toString(), paramCharacterSet);
/*     */   }
/*     */ 
/*     */   public CharacterSet getCharacterSet()
/*     */   {
/* 194 */     if (this.charSet == null)
/*     */     {
/* 198 */       if (this.oracleId == 0)
/*     */       {
/* 200 */         this.oracleId = -1;
/*     */       }
/*     */ 
/* 203 */       if ((DEFAULT_CHARSET != null) && ((this.oracleId == -1) || (this.oracleId == DEFAULT_CHARSET.getOracleId())))
/*     */       {
/* 206 */         this.charSet = DEFAULT_CHARSET;
/*     */       }
/* 208 */       else this.charSet = CharacterSet.make(this.oracleId);
/*     */     }
/*     */ 
/* 211 */     return this.charSet;
/*     */   }
/*     */ 
/*     */   public int oracleId()
/*     */   {
/* 224 */     return this.oracleId;
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */     throws SQLException
/*     */   {
/* 240 */     String str = getCharacterSet().toString(shareBytes(), 0, (int)getLength());
/*     */ 
/* 244 */     return str;
/*     */   }
/*     */ 
/*     */   public String getStringWithReplacement()
/*     */   {
/* 264 */     byte[] arrayOfByte = shareBytes();
/* 265 */     String str = getCharacterSet().toStringWithReplacement(arrayOfByte, 0, arrayOfByte.length);
/*     */ 
/* 270 */     return str;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 283 */     return getStringWithReplacement();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 295 */     return ((paramObject instanceof CHAR)) && (getCharacterSet().equals(((CHAR)paramObject).getCharacterSet())) && (super.equals(paramObject));
/*     */   }
/*     */ 
/*     */   void setValue(byte[] paramArrayOfByte, CharacterSet paramCharacterSet)
/*     */   {
/* 315 */     this.charSet = (paramCharacterSet == null ? DEFAULT_CHARSET : paramCharacterSet);
/* 316 */     this.oracleId = this.charSet.getOracleId();
/*     */ 
/* 322 */     setShareBytes(paramArrayOfByte == null ? empty : paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 346 */     return stringValue();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 365 */     String str = paramClass.getName();
/*     */ 
/* 377 */     return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Long") == 0) || (str.compareTo("java.math.BigDecimal") == 0) || (str.compareTo("java.io.InputStream") == 0) || (str.compareTo("java.sql.Date") == 0) || (str.compareTo("java.sql.Time") == 0) || (str.compareTo("java.sql.Timestamp") == 0) || (str.compareTo("java.io.Reader") == 0);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 396 */     return toString();
/*     */   }
/*     */ 
/*     */   public boolean booleanValue()
/*     */     throws SQLException
/*     */   {
/* 410 */     int i = 0;
/*     */ 
/* 412 */     String str = stringValue();
/*     */ 
/* 414 */     if (str == null)
/*     */     {
/* 416 */       i = 0;
/*     */     }
/* 418 */     else if ((str.length() == 1) && (str.charAt(0) == '0'))
/*     */     {
/* 420 */       i = 0;
/*     */     }
/*     */     else
/*     */     {
/* 424 */       i = 1;
/*     */     }
/*     */ 
/* 429 */     return i;
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */     throws SQLException
/*     */   {
/* 443 */     long l = longValue();
/*     */ 
/* 445 */     if ((l > 2147483647L) || (l < -2147483648L))
/*     */     {
/* 450 */       DatabaseError.throwSqlException(26);
/*     */     }
/*     */ 
/* 453 */     int i = (int)l;
/*     */ 
/* 457 */     return i;
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */     throws SQLException
/*     */   {
/* 471 */     long l = 0L;
/*     */     try
/*     */     {
/* 475 */       l = Long.valueOf(stringValue()).longValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/* 482 */       DatabaseError.throwSqlException(59);
/*     */     }
/*     */ 
/* 487 */     return l;
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */     throws SQLException
/*     */   {
/* 501 */     float f = 0.0F;
/*     */     try
/*     */     {
/* 505 */       f = Float.valueOf(stringValue()).floatValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/* 512 */       DatabaseError.throwSqlException(59);
/*     */     }
/*     */ 
/* 517 */     return f;
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */     throws SQLException
/*     */   {
/* 531 */     double d = 0.0D;
/*     */     try
/*     */     {
/* 535 */       d = Double.valueOf(stringValue()).doubleValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/* 542 */       DatabaseError.throwSqlException(59);
/*     */     }
/*     */ 
/* 547 */     return d;
/*     */   }
/*     */ 
/*     */   public byte byteValue()
/*     */     throws SQLException
/*     */   {
/* 561 */     long l = longValue();
/*     */ 
/* 563 */     if ((l > 127L) || (l < -128L))
/*     */     {
/* 568 */       DatabaseError.throwSqlException(26);
/*     */     }
/*     */ 
/* 571 */     int i = (byte)(int)l;
/*     */ 
/* 575 */     return i;
/*     */   }
/*     */ 
/*     */   public Date dateValue()
/*     */     throws SQLException
/*     */   {
/* 589 */     Date localDate = Date.valueOf(stringValue());
/*     */ 
/* 593 */     return localDate;
/*     */   }
/*     */ 
/*     */   public Time timeValue()
/*     */     throws SQLException
/*     */   {
/* 607 */     Time localTime = Time.valueOf(stringValue());
/*     */ 
/* 611 */     return localTime;
/*     */   }
/*     */ 
/*     */   public Timestamp timestampValue()
/*     */     throws SQLException
/*     */   {
/* 625 */     Timestamp localTimestamp = Timestamp.valueOf(stringValue());
/*     */ 
/* 629 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   public BigDecimal bigDecimalValue()
/*     */     throws SQLException
/*     */   {
/* 643 */     BigDecimal localBigDecimal = null;
/*     */     try
/*     */     {
/* 647 */       localBigDecimal = new BigDecimal(stringValue());
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException)
/*     */     {
/* 654 */       DatabaseError.throwSqlException(12, "bigDecimalValue");
/*     */     }
/*     */ 
/* 660 */     return localBigDecimal;
/*     */   }
/*     */ 
/*     */   public Reader characterStreamValue()
/*     */     throws SQLException
/*     */   {
/* 674 */     StringReader localStringReader = new StringReader(getString());
/*     */ 
/* 678 */     return localStringReader;
/*     */   }
/*     */ 
/*     */   public InputStream asciiStreamValue()
/*     */     throws SQLException
/*     */   {
/* 692 */     return getStream();
/*     */   }
/*     */ 
/*     */   public InputStream binaryStreamValue()
/*     */     throws SQLException
/*     */   {
/* 706 */     return getStream();
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 730 */     return new String[paramInt];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CHAR
 * JD-Core Version:    0.6.0
 */