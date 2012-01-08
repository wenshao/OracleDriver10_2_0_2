/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleBlobInputStream extends OracleBufferedStream
/*     */ {
/*     */   long lobOffset;
/*     */   Datum lob;
/*     */   long markedByte;
/*  33 */   boolean endOfStream = false;
/*     */ 
/* 339 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleBlobInputStream(BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/*  42 */     this(paramBLOB, ((PhysicalConnection)paramBLOB.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
/*     */   }
/*     */ 
/*     */   public OracleBlobInputStream(BLOB paramBLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  59 */     this(paramBLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleBlobInputStream(BLOB paramBLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  76 */     super(paramInt);
/*     */ 
/*  81 */     if ((paramBLOB == null) || (paramInt <= 0) || (paramLong < 1L))
/*     */     {
/*  83 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  86 */     this.lob = paramBLOB;
/*  87 */     this.markedByte = -1L;
/*  88 */     this.lobOffset = paramLong;
/*     */   }
/*     */ 
/*     */   public OracleBlobInputStream(BFILE paramBFILE)
/*     */     throws SQLException
/*     */   {
/*  98 */     this(paramBFILE, ((PhysicalConnection)paramBFILE.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
/*     */   }
/*     */ 
/*     */   public OracleBlobInputStream(BFILE paramBFILE, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 115 */     this(paramBFILE, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleBlobInputStream(BFILE paramBFILE, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 132 */     super(paramInt);
/*     */ 
/* 137 */     if ((paramBFILE == null) || (paramInt <= 0) || (paramLong < 1L))
/*     */     {
/* 139 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/* 142 */     this.lob = paramBFILE;
/* 143 */     this.markedByte = -1L;
/* 144 */     this.lobOffset = paramLong;
/*     */   }
/*     */ 
/*     */   public boolean needBytes()
/*     */     throws IOException
/*     */   {
/* 156 */     ensureOpen();
/*     */ 
/* 158 */     if (this.pos >= this.count)
/*     */     {
/* 160 */       if (!this.endOfStream)
/*     */       {
/*     */         try
/*     */         {
/* 166 */           if ((this.lob instanceof BLOB))
/* 167 */             this.count = ((BLOB)this.lob).getBytes(this.lobOffset, this.chunkSize, this.buf);
/*     */           else {
/* 169 */             this.count = ((BFILE)this.lob).getBytes(this.lobOffset, this.chunkSize, this.buf);
/*     */           }
/*     */ 
/* 172 */           if (this.count < this.chunkSize) {
/* 173 */             this.endOfStream = true;
/*     */           }
/* 175 */           if (this.count > 0)
/*     */           {
/* 179 */             this.pos = 0;
/* 180 */             this.lobOffset += this.count;
/*     */ 
/* 182 */             return true;
/*     */           }
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 187 */           DatabaseError.SQLToIOException(localSQLException);
/*     */         }
/*     */       }
/*     */ 
/* 191 */       return false;
/*     */     }
/*     */ 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 210 */       if (this.closed)
/* 211 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 215 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 251 */     if (paramInt < 0) {
/* 252 */       throw new IllegalArgumentException("Read-ahead limit < 0");
/*     */     }
/* 254 */     this.markedByte = (this.lobOffset - this.count + this.pos);
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 267 */     ensureOpen();
/*     */ 
/* 269 */     if (this.markedByte < 0L) {
/* 270 */       throw new IOException("Mark invalid or stream not marked.");
/*     */     }
/* 272 */     this.lobOffset = this.markedByte;
/* 273 */     this.pos = this.count;
/* 274 */     this.endOfStream = false;
/*     */   }
/*     */ 
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 294 */     ensureOpen();
/*     */ 
/* 296 */     long l1 = 0L;
/*     */ 
/* 298 */     if (this.count - this.pos >= paramLong)
/*     */     {
/* 300 */       this.pos = (int)(this.pos + paramLong);
/* 301 */       l1 += paramLong;
/*     */     }
/*     */     else
/*     */     {
/* 305 */       l1 += this.count - this.pos;
/* 306 */       this.pos = this.count;
/*     */       try
/*     */       {
/* 310 */         long l2 = 0L;
/*     */ 
/* 312 */         if ((this.lob instanceof BLOB))
/* 313 */           l2 = ((BLOB)this.lob).length() - this.lobOffset + 1L;
/*     */         else {
/* 315 */           l2 = ((BFILE)this.lob).length() - this.lobOffset + 1L;
/*     */         }
/* 317 */         if (l2 >= paramLong - l1)
/*     */         {
/* 319 */           this.lobOffset += paramLong - l1;
/* 320 */           l1 += paramLong - l1;
/*     */         }
/*     */         else
/*     */         {
/* 324 */           this.lobOffset += l2;
/* 325 */           l1 += l2;
/*     */         }
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 330 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */     }
/*     */ 
/* 334 */     return l1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleBlobInputStream
 * JD-Core Version:    0.6.0
 */