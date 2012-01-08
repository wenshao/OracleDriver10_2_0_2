/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.BLOB;
/*     */ 
/*     */ public class OracleBlobOutputStream extends OutputStream
/*     */ {
/*     */   long lobOffset;
/*     */   BLOB blob;
/*     */   byte[] buf;
/*     */   int count;
/*     */   int bufSize;
/*     */   boolean isClosed;
/* 242 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleBlobOutputStream(BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/*  42 */     this(paramBLOB, ((PhysicalConnection)paramBLOB.getJavaSqlConnection()).getDefaultStreamChunkSize());
/*     */   }
/*     */ 
/*     */   public OracleBlobOutputStream(BLOB paramBLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  58 */     this(paramBLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleBlobOutputStream(BLOB paramBLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  79 */     if ((paramBLOB == null) || (paramInt <= 0) || (paramLong < 1L))
/*     */     {
/*  81 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  84 */     this.blob = paramBLOB;
/*  85 */     this.lobOffset = paramLong;
/*     */ 
/*  87 */     this.buf = new byte[paramInt];
/*  88 */     this.count = 0;
/*  89 */     this.bufSize = paramInt;
/*     */ 
/*  91 */     this.isClosed = false;
/*     */   }
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 106 */     ensureOpen();
/*     */ 
/* 108 */     if (this.count >= this.buf.length) {
/* 109 */       flushBuffer();
/*     */     }
/* 111 */     this.buf[(this.count++)] = (byte)paramInt;
/*     */   }
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 129 */     ensureOpen();
/*     */ 
/* 131 */     int i = paramInt1;
/* 132 */     int j = i + Math.min(paramInt2, paramArrayOfByte.length - paramInt1);
/*     */ 
/* 134 */     while (i < j)
/*     */     {
/* 136 */       int k = Math.min(this.bufSize - this.count, j - i);
/*     */ 
/* 138 */       System.arraycopy(paramArrayOfByte, i, this.buf, this.count, k);
/*     */ 
/* 140 */       i += k;
/* 141 */       this.count += k;
/*     */ 
/* 143 */       if (this.count >= this.bufSize)
/* 144 */         flushBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 162 */     ensureOpen();
/*     */ 
/* 164 */     flushBuffer();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 179 */     flushBuffer();
/*     */ 
/* 181 */     this.isClosed = true;
/*     */   }
/*     */ 
/*     */   private void flushBuffer()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 196 */       if (this.count > 0)
/*     */       {
/* 198 */         if (this.count < this.buf.length)
/*     */         {
/* 200 */           byte[] arrayOfByte = new byte[this.count];
/*     */ 
/* 202 */           System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
/*     */ 
/* 204 */           this.lobOffset += this.blob.putBytes(this.lobOffset, arrayOfByte);
/*     */         }
/*     */         else {
/* 207 */           this.lobOffset += this.blob.putBytes(this.lobOffset, this.buf);
/*     */         }
/* 209 */         this.count = 0;
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 214 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 231 */       if (this.isClosed)
/* 232 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 236 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleBlobOutputStream
 * JD-Core Version:    0.6.0
 */