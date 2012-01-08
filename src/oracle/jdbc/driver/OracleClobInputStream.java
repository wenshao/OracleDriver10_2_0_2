/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CLOB;
/*     */ 
/*     */ public class OracleClobInputStream extends OracleBufferedStream
/*     */ {
/*     */   protected long lobOffset;
/*     */   protected CLOB clob;
/*     */   protected long markedByte;
/*     */   protected boolean endOfStream;
/*     */   protected char[] charBuf;
/* 273 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleClobInputStream(CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/*  37 */     this(paramCLOB, ((PhysicalConnection)paramCLOB.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobInputStream(CLOB paramCLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  54 */     this(paramCLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobInputStream(CLOB paramCLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  70 */     super(paramInt);
/*     */ 
/*  75 */     if ((paramCLOB == null) || (paramInt <= 0) || (paramLong < 1L))
/*     */     {
/*  77 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  80 */     this.lobOffset = paramLong;
/*  81 */     this.clob = paramCLOB;
/*  82 */     this.markedByte = -1L;
/*  83 */     this.endOfStream = false;
/*  84 */     this.charBuf = new char[paramInt];
/*     */   }
/*     */ 
/*     */   public boolean needBytes()
/*     */     throws IOException
/*     */   {
/*  98 */     ensureOpen();
/*     */ 
/* 100 */     if (this.pos >= this.count)
/*     */     {
/* 102 */       if (!this.endOfStream)
/*     */       {
/*     */         try
/*     */         {
/* 106 */           this.count = this.clob.getChars(this.lobOffset, this.chunkSize, this.charBuf);
/*     */ 
/* 109 */           for (int i = 0; i < this.count; i++) {
/* 110 */             this.buf[i] = (byte)this.charBuf[i];
/*     */           }
/* 112 */           if (this.count < this.chunkSize) {
/* 113 */             this.endOfStream = true;
/*     */           }
/* 115 */           if (this.count > 0)
/*     */           {
/* 117 */             this.pos = 0;
/* 118 */             this.lobOffset += this.count;
/*     */ 
/* 120 */             return true;
/*     */           }
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 125 */           DatabaseError.SQLToIOException(localSQLException);
/*     */         }
/*     */       }
/*     */ 
/* 129 */       return false;
/*     */     }
/*     */ 
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   protected void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 148 */       if (this.closed)
/* 149 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 153 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 188 */     if (paramInt < 0) {
/* 189 */       throw new IllegalArgumentException("Read-ahead limit < 0");
/*     */     }
/* 191 */     this.markedByte = (this.lobOffset - this.count + this.pos);
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 204 */     ensureOpen();
/*     */ 
/* 206 */     if (this.markedByte < 0L) {
/* 207 */       throw new IOException("Mark invalid or stream not marked.");
/*     */     }
/* 209 */     this.lobOffset = this.markedByte;
/* 210 */     this.pos = this.count;
/* 211 */     this.endOfStream = false;
/*     */   }
/*     */ 
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 231 */     ensureOpen();
/*     */ 
/* 233 */     long l1 = 0L;
/*     */ 
/* 235 */     if (this.count - this.pos >= paramLong)
/*     */     {
/* 237 */       this.pos = (int)(this.pos + paramLong);
/* 238 */       l1 += paramLong;
/*     */     }
/*     */     else
/*     */     {
/* 242 */       l1 += this.count - this.pos;
/* 243 */       this.pos = this.count;
/*     */       try
/*     */       {
/* 247 */         long l2 = 0L;
/*     */ 
/* 249 */         l2 = this.clob.length() - this.lobOffset + 1L;
/*     */ 
/* 251 */         if (l2 >= paramLong - l1)
/*     */         {
/* 253 */           this.lobOffset += paramLong - l1;
/* 254 */           l1 += paramLong - l1;
/*     */         }
/*     */         else
/*     */         {
/* 258 */           this.lobOffset += l2;
/* 259 */           l1 += l2;
/*     */         }
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 264 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */     }
/*     */ 
/* 268 */     return l1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleClobInputStream
 * JD-Core Version:    0.6.0
 */