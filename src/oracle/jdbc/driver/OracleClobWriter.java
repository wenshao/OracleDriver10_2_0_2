/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CLOB;
/*     */ 
/*     */ public class OracleClobWriter extends Writer
/*     */ {
/*     */   DBConversion dbConversion;
/*     */   CLOB clob;
/*     */   long lobOffset;
/*     */   char[] charBuf;
/*     */   byte[] nativeBuf;
/*     */   int pos;
/*     */   int count;
/*     */   int chunkSize;
/*     */   boolean isClosed;
/* 235 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleClobWriter(CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/*  42 */     this(paramCLOB, ((PhysicalConnection)paramCLOB.getInternalConnection()).getDefaultStreamChunkSize() / 3, 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobWriter(CLOB paramCLOB, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  60 */     this(paramCLOB, paramInt, 1L);
/*     */   }
/*     */ 
/*     */   public OracleClobWriter(CLOB paramCLOB, int paramInt, long paramLong)
/*     */     throws SQLException
/*     */   {
/*  82 */     if ((paramCLOB == null) || (paramInt <= 0) || (paramCLOB.getJavaSqlConnection() == null) || (paramLong < 1L))
/*     */     {
/*  85 */       throw new IllegalArgumentException("Illegal Arguments");
/*     */     }
/*     */ 
/*  88 */     this.dbConversion = ((PhysicalConnection)paramCLOB.getInternalConnection()).conversion;
/*     */ 
/*  91 */     this.clob = paramCLOB;
/*  92 */     this.lobOffset = paramLong;
/*     */ 
/*  94 */     this.charBuf = new char[paramInt];
/*  95 */     this.nativeBuf = new byte[paramInt * 3];
/*  96 */     this.pos = (this.count = 0);
/*  97 */     this.chunkSize = paramInt;
/*     */ 
/*  99 */     this.isClosed = false;
/*     */   }
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 117 */     synchronized (this.lock)
/*     */     {
/* 119 */       ensureOpen();
/*     */ 
/* 121 */       int i = paramInt1;
/* 122 */       int j = i + Math.min(paramInt2, paramArrayOfChar.length - paramInt1);
/*     */ 
/* 124 */       while (i < j)
/*     */       {
/* 126 */         int k = Math.min(this.chunkSize - this.count, j - i);
/*     */ 
/* 128 */         System.arraycopy(paramArrayOfChar, i, this.charBuf, this.count, k);
/*     */ 
/* 130 */         i += k;
/* 131 */         this.count += k;
/*     */ 
/* 133 */         if (this.count >= this.chunkSize)
/* 134 */           flushBuffer();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 154 */     synchronized (this.lock)
/*     */     {
/* 156 */       ensureOpen();
/* 157 */       flushBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 174 */     synchronized (this.lock)
/*     */     {
/* 176 */       flushBuffer();
/*     */ 
/* 178 */       this.isClosed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void flushBuffer()
/*     */     throws IOException
/*     */   {
/* 191 */     synchronized (this.lock)
/*     */     {
/*     */       try
/*     */       {
/* 195 */         if (this.count > 0)
/*     */         {
/* 197 */           this.clob.setString(this.lobOffset, new String(this.charBuf, 0, this.count));
/*     */ 
/* 199 */           this.lobOffset += this.count;
/*     */ 
/* 201 */           this.count = 0;
/*     */         }
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 206 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void ensureOpen()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 224 */       if (this.isClosed)
/* 225 */         DatabaseError.throwSqlException(57, null);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 229 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleClobWriter
 * JD-Core Version:    0.6.0
 */