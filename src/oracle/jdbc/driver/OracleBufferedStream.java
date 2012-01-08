/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class OracleBufferedStream extends InputStream
/*     */ {
/*     */   byte[] buf;
/*     */   int pos;
/*     */   int count;
/*     */   boolean closed;
/*     */   int chunkSize;
/*     */   OracleStatement statement;
/* 271 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleBufferedStream(int paramInt)
/*     */   {
/*  39 */     this.pos = 0;
/*  40 */     this.count = 0;
/*  41 */     this.closed = false;
/*  42 */     this.chunkSize = paramInt;
/*  43 */     this.buf = new byte[paramInt];
/*     */   }
/*     */ 
/*     */   public OracleBufferedStream(OracleStatement paramOracleStatement, int paramInt)
/*     */   {
/*  52 */     this(paramInt);
/*     */ 
/*  56 */     this.statement = paramOracleStatement;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  68 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   public boolean needBytes()
/*     */     throws IOException
/*     */   {
/*  79 */     throw new IOException("You should not call this method");
/*     */   }
/*     */ 
/*     */   public int flushBytes(int paramInt)
/*     */   {
/*  84 */     int i = paramInt > this.count - this.pos ? this.count - this.pos : paramInt;
/*     */ 
/*  86 */     this.pos += i;
/*     */ 
/*  88 */     return i;
/*     */   }
/*     */ 
/*     */   public int writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  93 */     int i = paramInt2 > this.count - this.pos ? this.count - this.pos : paramInt2;
/*     */ 
/*  95 */     System.arraycopy(this.buf, this.pos, paramArrayOfByte, paramInt1, i);
/*     */ 
/*  97 */     this.pos += i;
/*     */ 
/*  99 */     return i;
/*     */   }
/*     */ 
/*     */   public int read() throws IOException
/*     */   {
/* 104 */     if (this.statement == null)
/*     */     {
/* 106 */       synchronized (this)
/*     */       {
/* 108 */         return readInternal();
/*     */       }
/*     */     }
/*     */ 
/* 112 */     synchronized (this.statement.connection)
/*     */     {
/* 114 */       synchronized (this)
/*     */       {
/*     */       }
/* 117 */       monitorexit; throw localObject2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final int readInternal()
/*     */     throws IOException
/*     */   {
/* 127 */     if ((this.closed) || (isNull())) {
/* 128 */       return -1;
/*     */     }
/* 130 */     if (needBytes()) {
/* 131 */       return this.buf[(this.pos++)] & 0xFF;
/*     */     }
/* 133 */     return -1;
/*     */   }
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 142 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 148 */     if (this.statement == null)
/*     */     {
/* 150 */       synchronized (this)
/*     */       {
/* 152 */         return readInternal(paramArrayOfByte, paramInt1, paramInt2);
/*     */       }
/*     */     }
/*     */ 
/* 156 */     synchronized (this.statement.connection)
/*     */     {
/* 158 */       synchronized (this)
/*     */       {
/*     */       }
/* 161 */       monitorexit; throw localObject2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final int readInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 170 */     int j = paramInt1;
/*     */ 
/* 175 */     if ((this.closed) || (isNull()))
/* 176 */       return -1;
/*     */     int i;
/* 178 */     if (paramInt2 > paramArrayOfByte.length)
/* 179 */       i = j + paramArrayOfByte.length;
/*     */     else {
/* 181 */       i = j + paramInt2;
/*     */     }
/* 183 */     if (!needBytes()) {
/* 184 */       return -1;
/*     */     }
/* 186 */     j += writeBytes(paramArrayOfByte, j, i - j);
/*     */ 
/* 188 */     while ((j < i) && (needBytes()))
/*     */     {
/* 190 */       j += writeBytes(paramArrayOfByte, j, i - j);
/*     */     }
/*     */ 
/* 193 */     return j - paramInt1;
/*     */   }
/*     */ 
/*     */   public int available() throws IOException
/*     */   {
/* 198 */     if ((this.closed) || (isNull())) {
/* 199 */       return 0;
/*     */     }
/* 201 */     return this.count - this.pos;
/*     */   }
/*     */ 
/*     */   public boolean isNull() throws IOException
/*     */   {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   public synchronized void mark(int paramInt) {
/*     */   }
/*     */ 
/*     */   public synchronized void reset() throws IOException {
/* 213 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   public long skip(int paramInt)
/*     */     throws IOException
/*     */   {
/* 229 */     if (this.statement == null)
/*     */     {
/* 231 */       synchronized (this)
/*     */       {
/* 233 */         return skipInternal(paramInt);
/*     */       }
/*     */     }
/*     */ 
/* 237 */     synchronized (this.statement.connection)
/*     */     {
/* 239 */       synchronized (this)
/*     */       {
/*     */       }
/* 242 */       monitorexit; throw localObject2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final int skipInternal(int paramInt)
/*     */     throws IOException
/*     */   {
/* 249 */     int i = 0;
/* 250 */     int j = paramInt;
/*     */ 
/* 255 */     if ((this.closed) || (isNull())) {
/* 256 */       return -1;
/*     */     }
/* 258 */     if (!needBytes()) {
/* 259 */       return -1;
/*     */     }
/* 261 */     while ((i < j) && (needBytes()))
/*     */     {
/* 263 */       i += flushBytes(j - i);
/*     */     }
/*     */ 
/* 266 */     return i;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleBufferedStream
 * JD-Core Version:    0.6.0
 */