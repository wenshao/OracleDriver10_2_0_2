/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class OracleConversionReader extends Reader
/*     */ {
/*     */   static final int CHUNK_SIZE = 4096;
/*     */   DBConversion dbConversion;
/*     */   int conversion;
/*     */   InputStream istream;
/*     */   char[] buf;
/*     */   byte[] byteBuf;
/*     */   int pos;
/*     */   int count;
/*     */   int numUnconvertedBytes;
/*     */   boolean isClosed;
/*     */   boolean endOfStream;
/*     */   private short csform;
/*     */   int[] nbytes;
/* 329 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleConversionReader(DBConversion paramDBConversion, InputStream paramInputStream, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  62 */     if ((paramDBConversion == null) || (paramInputStream == null) || ((paramInt != 8) && (paramInt != 9)))
/*     */     {
/*  65 */       DatabaseError.throwSqlException(68);
/*     */     }
/*  67 */     this.dbConversion = paramDBConversion;
/*  68 */     this.conversion = paramInt;
/*  69 */     this.istream = paramInputStream;
/*  70 */     this.pos = (this.count = 0);
/*  71 */     this.numUnconvertedBytes = 0;
/*     */ 
/*  73 */     this.isClosed = false;
/*  74 */     this.nbytes = new int[1];
/*     */ 
/*  76 */     if (paramInt == 8)
/*     */     {
/*  78 */       this.byteBuf = new byte[2048];
/*  79 */       this.buf = new char[4096];
/*     */     }
/*  81 */     else if (paramInt == 9)
/*     */     {
/*  83 */       this.byteBuf = new byte[4096];
/*  84 */       this.buf = new char[4096];
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFormOfUse(short paramShort)
/*     */   {
/*  97 */     this.csform = paramShort;
/*     */   }
/*     */ 
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 120 */     ensureOpen();
/*     */ 
/* 122 */     if (!needChars()) {
/* 123 */       return -1;
/*     */     }
/* 125 */     int i = paramInt1;
/* 126 */     int j = i + Math.min(paramInt2, paramArrayOfChar.length - paramInt1);
/*     */ 
/* 128 */     i += writeChars(paramArrayOfChar, i, j - i);
/*     */ 
/* 130 */     while ((i < j) && (needChars()))
/*     */     {
/* 132 */       i += writeChars(paramArrayOfChar, i, j - i);
/*     */     }
/*     */ 
/* 135 */     return i - paramInt1;
/*     */   }
/*     */ 
/*     */   protected boolean needChars()
/*     */     throws IOException
/*     */   {
/* 150 */     ensureOpen();
/*     */ 
/* 152 */     if (this.pos >= this.count)
/*     */     {
/* 156 */       if (!this.endOfStream)
/*     */       {
/*     */         try
/*     */         {
/* 160 */           int i = this.istream.read(this.byteBuf, this.numUnconvertedBytes, this.byteBuf.length - this.numUnconvertedBytes);
/*     */ 
/* 163 */           if (i == -1)
/*     */           {
/* 165 */             this.endOfStream = true;
/*     */ 
/* 167 */             this.istream.close();
/*     */ 
/* 169 */             if (this.numUnconvertedBytes != 0) {
/* 170 */               DatabaseError.throwSqlException(55);
/*     */             }
/*     */           }
/* 173 */           i += this.numUnconvertedBytes;
/*     */ 
/* 175 */           if (i > 0)
/*     */           {
/*     */             int j;
/* 177 */             switch (this.conversion)
/*     */             {
/*     */             case 8:
/* 182 */               this.count = DBConversion.RAWBytesToHexChars(this.byteBuf, i, this.buf);
/*     */ 
/* 184 */               break;
/*     */             case 9:
/* 189 */               this.nbytes[0] = i;
/*     */ 
/* 191 */               if (this.csform == 2) {
/* 192 */                 this.count = this.dbConversion.NCHARBytesToJavaChars(this.byteBuf, 0, this.buf, 0, this.nbytes, this.buf.length);
/*     */               }
/*     */               else
/*     */               {
/* 197 */                 this.count = this.dbConversion.CHARBytesToJavaChars(this.byteBuf, 0, this.buf, 0, this.nbytes, this.buf.length);
/*     */               }
/*     */ 
/* 202 */               this.numUnconvertedBytes = this.nbytes[0];
/*     */ 
/* 204 */               for (j = 0; j < this.numUnconvertedBytes; ) {
/* 205 */                 this.byteBuf[j] = this.byteBuf[(i - this.numUnconvertedBytes + j)];
/*     */ 
/* 204 */                 j++; continue;
/*     */ 
/* 211 */                 DatabaseError.throwSqlException(23);
/*     */               }
/*     */             }
/* 214 */             if (this.count > 0)
/*     */             {
/* 216 */               this.pos = 0;
/*     */ 
/* 218 */               return true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 226 */           DatabaseError.SQLToIOException(localSQLException);
/*     */         }
/*     */       }
/*     */ 
/* 230 */       return false;
/*     */     }
/*     */ 
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   protected int writeChars(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 249 */     int i = Math.min(paramInt2, this.count - this.pos);
/*     */ 
/* 251 */     System.arraycopy(this.buf, this.pos, paramArrayOfChar, paramInt1, i);
/*     */ 
/* 253 */     this.pos += i;
/*     */ 
/* 258 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean ready()
/*     */     throws IOException
/*     */   {
/* 276 */     ensureOpen();
/*     */ 
/* 281 */     return this.pos < this.count;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 297 */     if (!this.isClosed)
/*     */     {
/* 299 */       this.isClosed = true;
/*     */ 
/* 301 */       this.istream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 318 */       if (this.isClosed)
/* 319 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 323 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleConversionReader
 * JD-Core Version:    0.6.0
 */