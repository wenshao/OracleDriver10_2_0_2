/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CLOB;
/*     */ 
/*     */ public class OracleClobOutputStream extends OutputStream
/*     */ {
/*     */   long lobOffset;
/*     */   CLOB clob;
/*     */   byte[] buf;
/*     */   int count;
/*     */   int bufSize;
/*     */   boolean isClosed;
/* 240 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*     */ 
/*     */   public OracleClobOutputStream(CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/*  43 */     this(paramCLOB, ((PhysicalConnection)paramCLOB.getJavaSqlConnection()).getDefaultStreamChunkSize(), 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobOutputStream(CLOB paramCLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  60 */     this(paramCLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobOutputStream(CLOB paramCLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  81 */     if ((paramCLOB == null) || (paramInt <= 0) || (paramLong < 1L))
/*     */     {
/*  83 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  86 */     this.clob = paramCLOB;
/*  87 */     this.lobOffset = paramLong;
/*     */ 
/*  89 */     this.buf = new byte[paramInt];
/*  90 */     this.count = 0;
/*  91 */     this.bufSize = paramInt;
/*     */ 
/*  93 */     this.isClosed = false;
/*     */   }
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 108 */     ensureOpen();
/*     */ 
/* 110 */     if (this.count >= this.buf.length) {
/* 111 */       flushBuffer();
/*     */     }
/* 113 */     this.buf[(this.count++)] = (byte)paramInt;
/*     */   }
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 131 */     ensureOpen();
/*     */ 
/* 133 */     int i = paramInt1;
/* 134 */     int j = i + Math.min(paramInt2, paramArrayOfByte.length - paramInt1);
/*     */ 
/* 136 */     while (i < j)
/*     */     {
/* 138 */       int k = Math.min(this.bufSize - this.count, j - i);
/*     */ 
/* 140 */       System.arraycopy(paramArrayOfByte, i, this.buf, this.count, k);
/*     */ 
/* 142 */       i += k;
/* 143 */       this.count += k;
/*     */ 
/* 145 */       if (this.count >= this.bufSize)
/* 146 */         flushBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 164 */     ensureOpen();
/*     */ 
/* 166 */     flushBuffer();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 181 */     flushBuffer();
/*     */ 
/* 183 */     this.isClosed = true;
/*     */   }
/*     */ 
/*     */   private void flushBuffer()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 198 */       if (this.count > 0)
/*     */       {
/* 200 */         char[] arrayOfChar = new char[this.count];
/*     */ 
/* 202 */         for (int i = 0; i < this.count; i++) {
/* 203 */           arrayOfChar[i] = (char)this.buf[i];
/*     */         }
/* 205 */         this.lobOffset += this.clob.putChars(this.lobOffset, arrayOfChar);
/*     */ 
/* 207 */         this.count = 0;
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 212 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 229 */       if (this.isClosed)
/* 230 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 234 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleClobOutputStream
 * JD-Core Version:    0.6.0
 */