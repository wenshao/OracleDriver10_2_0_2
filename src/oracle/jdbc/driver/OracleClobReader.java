/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CLOB;
/*     */ 
/*     */ public class OracleClobReader extends Reader
/*     */ {
/*     */   CLOB clob;
/*     */   DBConversion dbConversion;
/*     */   long lobOffset;
/*     */   long markedChar;
/*     */   char[] buf;
/*     */   int pos;
/*     */   int count;
/*     */   int chunkSize;
/*     */   boolean isClosed;
/*     */   boolean endOfStream;
/* 395 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleClobReader(CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/*  49 */     this(paramCLOB, ((PhysicalConnection)paramCLOB.getInternalConnection()).getDefaultStreamChunkSize() / 3);
/*     */   }
/*     */ 
/*     */   public OracleClobReader(CLOB paramCLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  66 */     this(paramCLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobReader(CLOB paramCLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  87 */     if ((paramCLOB == null) || (paramInt <= 0) || (paramCLOB.getInternalConnection() == null) || (paramLong < 1L))
/*     */     {
/*  90 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  93 */     this.dbConversion = ((PhysicalConnection)paramCLOB.getInternalConnection()).conversion;
/*     */ 
/*  96 */     this.clob = paramCLOB;
/*  97 */     this.lobOffset = paramLong;
/*  98 */     this.markedChar = -1L;
/*     */ 
/* 100 */     this.buf = new char[paramInt];
/* 101 */     this.pos = (this.count = 0);
/* 102 */     this.chunkSize = paramInt;
/*     */ 
/* 104 */     this.isClosed = false;
/*     */   }
/*     */ 
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 127 */     ensureOpen();
/*     */ 
/* 129 */     if (!needChars()) {
/* 130 */       return -1;
/*     */     }
/* 132 */     int i = paramInt1;
/* 133 */     int j = i + Math.min(paramInt2, paramArrayOfChar.length - paramInt1);
/*     */ 
/* 135 */     i += writeChars(paramArrayOfChar, i, j - i);
/*     */ 
/* 137 */     while ((i < j) && (needChars()))
/*     */     {
/* 139 */       i += writeChars(paramArrayOfChar, i, j - i);
/*     */     }
/*     */ 
/* 142 */     return i - paramInt1;
/*     */   }
/*     */ 
/*     */   protected boolean needChars()
/*     */     throws IOException
/*     */   {
/* 157 */     ensureOpen();
/*     */ 
/* 159 */     if (this.pos >= this.count)
/*     */     {
/* 161 */       if (!this.endOfStream)
/*     */       {
/*     */         try
/*     */         {
/* 167 */           this.count = this.clob.getChars(this.lobOffset, this.chunkSize, this.buf);
/*     */ 
/* 170 */           if (this.count < this.chunkSize) {
/* 171 */             this.endOfStream = true;
/*     */           }
/* 173 */           if (this.count > 0)
/*     */           {
/* 175 */             this.pos = 0;
/* 176 */             this.lobOffset += this.count;
/*     */ 
/* 178 */             return true;
/*     */           }
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 183 */           DatabaseError.SQLToIOException(localSQLException);
/*     */         }
/*     */       }
/*     */ 
/* 187 */       return false;
/*     */     }
/*     */ 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   protected int writeChars(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 206 */     int i = Math.min(paramInt2, this.count - this.pos);
/*     */ 
/* 208 */     System.arraycopy(this.buf, this.pos, paramArrayOfChar, paramInt1, i);
/*     */ 
/* 210 */     this.pos += i;
/*     */ 
/* 212 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean ready()
/*     */     throws IOException
/*     */   {
/* 230 */     ensureOpen();
/*     */ 
/* 232 */     return this.pos < this.count;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 248 */     this.isClosed = true;
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 264 */       if (this.isClosed)
/* 265 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 269 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 282 */     return true;
/*     */   }
/*     */ 
/*     */   public void mark(int paramInt)
/*     */     throws IOException
/*     */   {
/* 304 */     if (paramInt < 0) {
/* 305 */       throw new IllegalArgumentException("Read-ahead limit < 0");
/*     */     }
/* 307 */     this.markedChar = (this.lobOffset - this.count + this.pos);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 329 */     ensureOpen();
/*     */ 
/* 331 */     if (this.markedChar < 0L) {
/* 332 */       throw new IOException("Mark invalid or stream not marked.");
/*     */     }
/* 334 */     this.lobOffset = this.markedChar;
/* 335 */     this.pos = this.count;
/* 336 */     this.endOfStream = false;
/*     */   }
/*     */ 
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 355 */     ensureOpen();
/*     */ 
/* 357 */     long l1 = 0L;
/*     */ 
/* 359 */     if (this.count - this.pos >= paramLong)
/*     */     {
/* 361 */       this.pos = (int)(this.pos + paramLong);
/* 362 */       l1 += paramLong;
/*     */     }
/*     */     else
/*     */     {
/* 366 */       l1 += this.count - this.pos;
/* 367 */       this.pos = this.count;
/*     */       try
/*     */       {
/* 371 */         long l2 = this.clob.length() - this.lobOffset + 1L;
/*     */ 
/* 373 */         if (l2 >= paramLong - l1)
/*     */         {
/* 375 */           this.lobOffset += paramLong - l1;
/* 376 */           l1 += paramLong - l1;
/*     */         }
/*     */         else
/*     */         {
/* 380 */           this.lobOffset += l2;
/* 381 */           l1 += l2;
/*     */         }
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 386 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */     }
/*     */ 
/* 390 */     return l1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleClobReader
 * JD-Core Version:    0.6.0
 */