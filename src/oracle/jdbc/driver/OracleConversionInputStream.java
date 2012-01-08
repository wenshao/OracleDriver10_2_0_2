/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class OracleConversionInputStream extends OracleBufferedStream
/*     */ {
/*     */   static final int CHUNK_SIZE = 4096;
/*     */   DBConversion converter;
/*     */   int conversion;
/*     */   InputStream istream;
/*     */   Reader reader;
/*     */   byte[] convbuf;
/*     */   char[] javaChars;
/*     */   int maxSize;
/*     */   int totalSize;
/*     */   int numUnconvertedBytes;
/*     */   boolean endOfStream;
/*     */   private short csform;
/*     */   int[] nbytes;
/* 499 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt)
/*     */   {
/*  52 */     this(paramDBConversion, paramInputStream, paramInt, 1);
/*     */   }
/*     */ 
/*     */   public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt, short paramShort)
/*     */   {
/*  60 */     super(4096);
/*     */ 
/*  65 */     this.istream = paramInputStream;
/*  66 */     this.conversion = paramInt;
/*  67 */     this.converter = paramDBConversion;
/*  68 */     this.maxSize = 0;
/*  69 */     this.totalSize = 0;
/*  70 */     this.numUnconvertedBytes = 0;
/*  71 */     this.endOfStream = false;
/*  72 */     this.nbytes = new int[1];
/*  73 */     this.csform = paramShort;
/*     */     int i;
/*  75 */     switch (paramInt)
/*     */     {
/*     */     case 0:
/*  80 */       this.javaChars = new char[4096];
/*  81 */       this.convbuf = new byte[4096];
/*     */ 
/*  84 */       break;
/*     */     case 1:
/*  88 */       this.convbuf = new byte[2048];
/*  89 */       this.javaChars = new char[2048];
/*     */ 
/*  92 */       break;
/*     */     case 2:
/*  96 */       this.convbuf = new byte[2048];
/*  97 */       this.javaChars = new char[4096];
/*     */ 
/* 100 */       break;
/*     */     case 3:
/* 104 */       this.convbuf = new byte[1024];
/* 105 */       this.javaChars = new char[2048];
/*     */ 
/* 108 */       break;
/*     */     case 4:
/* 112 */       i = 4096 / this.converter.getMaxCharbyteSize();
/*     */ 
/* 114 */       this.convbuf = new byte[i * 2];
/* 115 */       this.javaChars = new char[i];
/*     */ 
/* 118 */       break;
/*     */     case 5:
/* 122 */       if (this.converter.isUcs2CharSet())
/*     */       {
/* 124 */         this.convbuf = new byte[2048];
/* 125 */         this.javaChars = new char[2048];
/*     */       }
/*     */       else
/*     */       {
/* 129 */         this.convbuf = new byte[4096];
/* 130 */         this.javaChars = new char[4096];
/*     */       }
/*     */ 
/* 134 */       break;
/*     */     case 7:
/* 138 */       i = 4096 / (paramShort == 2 ? this.converter.getMaxNCharbyteSize() : this.converter.getMaxCharbyteSize());
/*     */ 
/* 140 */       this.javaChars = new char[i];
/*     */ 
/* 143 */       break;
/*     */     case 6:
/*     */     default:
/* 147 */       this.convbuf = new byte[4096];
/* 148 */       this.javaChars = new char[4096];
/*     */     }
/*     */   }
/*     */ 
/*     */   public OracleConversionInputStream(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt1, int paramInt2)
/*     */   {
/* 161 */     this(paramDBConversion, paramInputStream, paramInt1, 1);
/*     */ 
/* 166 */     this.maxSize = paramInt2;
/* 167 */     this.totalSize = 0;
/*     */   }
/*     */ 
/*     */   public OracleConversionInputStream(DBConversion paramDBConversion, Reader paramReader, int paramInt1, int paramInt2, short paramShort)
/*     */   {
/* 178 */     this(paramDBConversion, (InputStream)null, paramInt1, paramShort);
/*     */ 
/* 183 */     this.reader = paramReader;
/* 184 */     this.maxSize = paramInt2;
/* 185 */     this.totalSize = 0;
/*     */   }
/*     */ 
/*     */   public void setFormOfUse(short paramShort)
/*     */   {
/* 197 */     this.csform = paramShort;
/*     */   }
/*     */ 
/*     */   public boolean needBytes()
/*     */     throws IOException
/*     */   {
/* 210 */     if (this.closed) {
/* 211 */       return false;
/*     */     }
/*     */ 
/* 215 */     if (this.pos < this.count) {
/* 216 */       return true;
/*     */     }
/* 218 */     if (this.istream != null)
/*     */     {
/* 220 */       return needBytesFromStream();
/*     */     }
/* 222 */     if (this.reader != null)
/*     */     {
/* 224 */       return needBytesFromReader();
/*     */     }
/*     */ 
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean needBytesFromReader()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 238 */       int i = 0;
/*     */ 
/* 240 */       if (this.maxSize == 0)
/*     */       {
/* 242 */         i = this.javaChars.length;
/*     */       }
/*     */       else
/*     */       {
/* 246 */         i = Math.min(this.maxSize - this.totalSize, this.javaChars.length);
/*     */       }
/*     */ 
/* 249 */       if (i <= 0)
/*     */       {
/* 251 */         this.reader.close();
/* 252 */         close();
/*     */ 
/* 254 */         return false;
/*     */       }
/*     */ 
/* 257 */       int j = this.reader.read(this.javaChars, 0, i);
/*     */ 
/* 259 */       if (j == -1)
/*     */       {
/* 261 */         this.reader.close();
/* 262 */         close();
/*     */ 
/* 264 */         return false;
/*     */       }
/*     */ 
/* 268 */       this.totalSize += j;
/*     */ 
/* 272 */       switch (this.conversion)
/*     */       {
/*     */       case 7:
/* 277 */         if (this.csform == 2)
/* 278 */           this.count = this.converter.javaCharsToNCHARBytes(this.javaChars, j, this.buf);
/*     */         else {
/* 280 */           this.count = this.converter.javaCharsToCHARBytes(this.javaChars, j, this.buf);
/*     */         }
/*     */ 
/* 283 */         break;
/*     */       default:
/* 287 */         System.arraycopy(this.convbuf, 0, this.buf, 0, j);
/*     */ 
/* 289 */         this.count = j;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 295 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */ 
/* 299 */     this.pos = 0;
/*     */ 
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean needBytesFromStream()
/*     */     throws IOException
/*     */   {
/* 311 */     if (!this.endOfStream)
/*     */     {
/*     */       try
/*     */       {
/* 315 */         int i = 0;
/*     */ 
/* 317 */         if (this.maxSize == 0)
/*     */         {
/* 319 */           i = this.convbuf.length;
/*     */         }
/*     */         else
/*     */         {
/* 323 */           i = Math.min(this.maxSize - this.totalSize, this.convbuf.length);
/*     */         }
/*     */ 
/* 326 */         int j = 0;
/*     */ 
/* 328 */         if (i <= 0)
/*     */         {
/* 332 */           this.endOfStream = true;
/*     */ 
/* 334 */           this.istream.close();
/*     */ 
/* 336 */           if (this.numUnconvertedBytes != 0) {
/* 337 */             DatabaseError.throwSqlException(55);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 343 */           j = this.istream.read(this.convbuf, this.numUnconvertedBytes, i - this.numUnconvertedBytes);
/*     */         }
/*     */ 
/* 350 */         if (j == -1)
/*     */         {
/* 354 */           this.endOfStream = true;
/*     */ 
/* 356 */           this.istream.close();
/*     */ 
/* 358 */           if (this.numUnconvertedBytes != 0)
/* 359 */             DatabaseError.throwSqlException(55);
/*     */         }
/*     */         else
/*     */         {
/* 363 */           j += this.numUnconvertedBytes;
/* 364 */           this.totalSize += j;
/*     */         }
/*     */ 
/* 367 */         if (j <= 0)
/*     */         {
/* 369 */           return false;
/*     */         }
/*     */         int k;
/*     */         int m;
/* 375 */         switch (this.conversion)
/*     */         {
/*     */         case 0:
/* 382 */           this.nbytes[0] = j;
/*     */ 
/* 384 */           k = this.converter.CHARBytesToJavaChars(this.convbuf, 0, this.javaChars, 0, this.nbytes, this.javaChars.length);
/*     */ 
/* 389 */           this.numUnconvertedBytes = this.nbytes[0];
/*     */ 
/* 391 */           for (m = 0; m < this.numUnconvertedBytes; m++) {
/* 392 */             this.convbuf[m] = this.convbuf[(j - this.numUnconvertedBytes)];
/*     */           }
/*     */ 
/* 395 */           this.count = DBConversion.javaCharsToAsciiBytes(this.javaChars, k, this.buf);
/*     */ 
/* 398 */           break;
/*     */         case 1:
/* 404 */           this.nbytes[0] = j;
/*     */ 
/* 406 */           k = this.converter.CHARBytesToJavaChars(this.convbuf, 0, this.javaChars, 0, this.nbytes, this.javaChars.length);
/*     */ 
/* 411 */           this.numUnconvertedBytes = this.nbytes[0];
/*     */ 
/* 413 */           for (m = 0; m < this.numUnconvertedBytes; m++) {
/* 414 */             this.convbuf[m] = this.convbuf[(j - this.numUnconvertedBytes)];
/*     */           }
/*     */ 
/* 417 */           this.count = DBConversion.javaCharsToUcs2Bytes(this.javaChars, k, this.buf);
/*     */ 
/* 420 */           break;
/*     */         case 2:
/* 426 */           k = DBConversion.RAWBytesToHexChars(this.convbuf, j, this.javaChars);
/*     */ 
/* 430 */           this.count = DBConversion.javaCharsToAsciiBytes(this.javaChars, k, this.buf);
/*     */ 
/* 433 */           break;
/*     */         case 3:
/* 439 */           k = DBConversion.RAWBytesToHexChars(this.convbuf, j, this.javaChars);
/*     */ 
/* 443 */           this.count = DBConversion.javaCharsToUcs2Bytes(this.javaChars, k, this.buf);
/*     */ 
/* 446 */           break;
/*     */         case 4:
/* 452 */           k = DBConversion.ucs2BytesToJavaChars(this.convbuf, j, this.javaChars);
/*     */ 
/* 456 */           this.count = this.converter.javaCharsToCHARBytes(this.javaChars, k, this.buf);
/*     */ 
/* 459 */           break;
/*     */         case 5:
/* 465 */           DBConversion.asciiBytesToJavaChars(this.convbuf, j, this.javaChars);
/*     */ 
/* 468 */           this.count = this.converter.javaCharsToCHARBytes(this.javaChars, j, this.buf);
/*     */ 
/* 471 */           break;
/*     */         default:
/* 475 */           System.arraycopy(this.convbuf, 0, this.buf, 0, j);
/*     */ 
/* 477 */           this.count = j;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 484 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */ 
/* 488 */       this.pos = 0;
/*     */ 
/* 491 */       return true;
/*     */     }
/*     */ 
/* 494 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleConversionInputStream
 * JD-Core Version:    0.6.0
 */