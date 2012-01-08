/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringBufferInputStream;
/*     */ import java.io.Writer;
/*     */ import java.sql.Clob;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class OracleSerialClob
/*     */   implements Clob, Serializable, Cloneable
/*     */ {
/*     */   private char[] buffer;
/*     */   private long length;
/*     */ 
/*     */   public OracleSerialClob(char[] paramArrayOfChar)
/*     */     throws SQLException
/*     */   {
/*  41 */     this.length = paramArrayOfChar.length;
/*  42 */     this.buffer = new char[(int)this.length];
/*  43 */     for (int i = 0; i < this.length; i++)
/*  44 */       this.buffer[i] = paramArrayOfChar[i];
/*     */   }
/*     */ 
/*     */   public OracleSerialClob(Clob paramClob)
/*     */     throws SQLException
/*     */   {
/*  65 */     this.length = paramClob.length();
/*  66 */     this.buffer = new char[(int)this.length];
/*  67 */     BufferedReader localBufferedReader = new BufferedReader(paramClob.getCharacterStream());
/*     */     try
/*     */     {
/*  71 */       int i = 0;
/*  72 */       int j = 0;
/*     */       do
/*     */       {
/*  92 */         i = localBufferedReader.read(this.buffer, j, (int)(this.length - j));
/*     */ 
/*  94 */         j += i;
/*  95 */       }while (i > 0);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  99 */       throw new SQLException("SerialClob: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public InputStream getAsciiStream()
/*     */     throws SQLException
/*     */   {
/* 120 */     return new StringBufferInputStream(new String(this.buffer));
/*     */   }
/*     */ 
/*     */   public Reader getCharacterStream()
/*     */     throws SQLException
/*     */   {
/* 140 */     return new CharArrayReader(this.buffer);
/*     */   }
/*     */ 
/*     */   public String getSubString(long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 167 */     if ((paramLong < 0L) || (paramInt > this.length) || (paramLong + paramInt > this.length)) {
/* 168 */       throw new SQLException("Invalid Arguments");
/*     */     }
/* 170 */     return new String(this.buffer, (int)paramLong, paramInt);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws SQLException
/*     */   {
/* 190 */     return this.length;
/*     */   }
/*     */ 
/*     */   public long position(String paramString, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 217 */     if ((paramLong < 0L) || (paramLong > this.length) || (paramLong + paramString.length() > this.length))
/* 218 */       throw new SQLException("Invalid Arguments"); char[] arrayOfChar = paramString.toCharArray();
/* 220 */     int i = (int)(paramLong - 1L);
/* 221 */     int j = 0;
/* 222 */     long l1 = arrayOfChar.length;
/*     */ 
/* 224 */     if ((paramLong < 0L) || (paramLong > this.length));
/*     */     while (true) { return -1L;
/*     */       while (true)
/* 227 */         if (i < this.length)
/*     */         {
/* 229 */           int k = 0;
/* 230 */           long l2 = i + 1;
/* 231 */           if (arrayOfChar[(k++)] == this.buffer[(i++)]) {
/* 232 */             if (k != l1) break;
/* 233 */             return l2;
/*     */           }
/*     */         } }
/* 236 */     return -1L;
/*     */   }
/*     */ 
/*     */   public long position(Clob paramClob, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 262 */     return position(paramClob.getSubString(0L, (int)paramClob.length()), paramLong);
/*     */   }
/*     */ 
/*     */   public int setString(long paramLong, String paramString)
/*     */     throws SQLException
/*     */   {
/* 288 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 289 */     return -1;
/*     */   }
/*     */ 
/*     */   public int setString(long paramLong, String paramString, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 315 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 316 */     return -1;
/*     */   }
/*     */ 
/*     */   public OutputStream setAsciiStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 338 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */   public Writer setCharacterStream(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 362 */     DatabaseError.throwUnsupportedFeatureSqlException();
/* 363 */     return null;
/*     */   }
/*     */ 
/*     */   public void truncate(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 382 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleSerialClob
 * JD-Core Version:    0.6.0
 */