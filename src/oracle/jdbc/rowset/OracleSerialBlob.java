/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class OracleSerialBlob
/*     */   implements Blob, Serializable, Cloneable
/*     */ {
/*     */   private byte[] buffer;
/*     */   private long length;
/*     */ 
/*     */   public OracleSerialBlob(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/*  39 */     this.length = paramArrayOfByte.length;
/*  40 */     this.buffer = new byte[(int)this.length];
/*  41 */     for (int i = 0; i < this.length; i++)
/*  42 */       this.buffer[i] = paramArrayOfByte[i];
/*     */   }
/*     */ 
/*     */   public OracleSerialBlob(Blob paramBlob)
/*     */     throws SQLException
/*     */   {
/*  63 */     this.length = paramBlob.length();
/*  64 */     this.buffer = new byte[(int)this.length];
/*  65 */     BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramBlob.getBinaryStream());
/*     */     try
/*     */     {
/*  69 */       int i = 0;
/*  70 */       int j = 0;
/*     */       do
/*     */       {
/*  89 */         i = localBufferedInputStream.read(this.buffer, j, (int)(this.length - j));
/*     */ 
/*  91 */         j += i;
/*  92 */       }while (i > 0);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  96 */       throw new SQLException("SerialBlob: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public InputStream getBinaryStream()
/*     */     throws SQLException
/*     */   {
/* 117 */     return new ByteArrayInputStream(this.buffer);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 144 */     if ((paramLong < 0L) || (paramInt > this.length) || (paramLong + paramInt > this.length)) {
/* 145 */       throw new SQLException("Invalid Arguments");
/*     */     }
/*     */ 
/* 148 */     byte[] arrayOfByte = new byte[paramInt];
/* 149 */     System.arraycopy(this.buffer, (int)paramLong, arrayOfByte, 0, paramInt);
/*     */ 
/* 167 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws SQLException
/*     */   {
/* 193 */     return this.length;
/*     */   }
/*     */ 
/*     */   public long position(byte[] paramArrayOfByte, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 221 */     if ((paramLong < 0L) || (paramLong > this.length) || (paramLong + paramArrayOfByte.length > this.length))
/* 222 */       throw new SQLException("Invalid Arguments"); int i = (int)(paramLong - 1L);
/* 224 */     int j = 0;
/* 225 */     long l1 = paramArrayOfByte.length;
/*     */ 
/* 227 */     if ((paramLong < 0L) || (paramLong > this.length));
/*     */     while (true) { return -1L;
/*     */       while (true)
/* 230 */         if (i < this.length)
/*     */         {
/* 232 */           int k = 0;
/* 233 */           long l2 = i + 1;
/* 234 */           if (paramArrayOfByte[(k++)] == this.buffer[(i++)]) {
/* 235 */             if (k != l1) break;
/* 236 */             return l2;
/*     */           }
/*     */         } }
/* 238 */     return -1L;
/*     */   }
/*     */ 
/*     */   public long position(Blob paramBlob, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 259 */     return position(paramBlob.getBytes(0L, (int)paramBlob.length()), paramLong);
/*     */   }
/*     */ 
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 285 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 286 */     return -1;
/*     */   }
/*     */ 
/*     */   public int setBytes(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 315 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 316 */     return -1;
/*     */   }
/*     */ 
/*     */   public OutputStream setBinaryStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 338 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */   public void truncate(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 357 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleSerialBlob
 * JD-Core Version:    0.6.0
 */