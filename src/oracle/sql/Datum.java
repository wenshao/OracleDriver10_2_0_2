/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ 
/*     */ public abstract class Datum
/*     */   implements Serializable
/*     */ {
/*     */   private byte[] data;
/*     */   static final long serialVersionUID = 4645732484621936751L;
/*     */ 
/*     */   public Datum()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Datum(byte[] paramArrayOfByte)
/*     */   {
/*  43 */     this.data = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  57 */     if (this == paramObject)
/*  58 */       return true;
/*  59 */     if ((paramObject == null) || (!(paramObject instanceof Datum)))
/*  60 */       return false;
/*  61 */     if (getClass() == paramObject.getClass())
/*     */     {
/*  63 */       Datum localDatum = (Datum)paramObject;
/*     */ 
/*  67 */       if ((this.data == null) && (localDatum.data == null))
/*  68 */         return true;
/*  69 */       if (((this.data == null) && (localDatum.data != null)) || ((this.data != null) && (localDatum.data == null)))
/*     */       {
/*  71 */         return false;
/*     */       }
/*  73 */       if (this.data.length != localDatum.data.length) {
/*  74 */         return false;
/*     */       }
/*  76 */       for (int i = 0; i < this.data.length; i++)
/*     */       {
/*  78 */         if (this.data[i] != localDatum.data[i]) {
/*  79 */           return false;
/*     */         }
/*     */       }
/*  82 */       return true;
/*     */     }
/*     */ 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   public byte[] shareBytes()
/*     */   {
/*  98 */     return this.data;
/*     */   }
/*     */ 
/*     */   public long getLength()
/*     */   {
/* 108 */     if (null == this.data) {
/* 109 */       return 0L;
/*     */     }
/* 111 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   public void setBytes(byte[] paramArrayOfByte)
/*     */   {
/* 121 */     int i = paramArrayOfByte.length;
/* 122 */     this.data = new byte[i];
/* 123 */     System.arraycopy(paramArrayOfByte, 0, this.data, 0, i);
/*     */   }
/*     */ 
/*     */   public void setShareBytes(byte[] paramArrayOfByte)
/*     */   {
/* 133 */     this.data = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 156 */     byte[] arrayOfByte = new byte[this.data.length];
/* 157 */     System.arraycopy(this.data, 0, arrayOfByte, 0, this.data.length);
/* 158 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public InputStream getStream()
/*     */   {
/* 170 */     return new ByteArrayInputStream(this.data);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */     throws SQLException
/*     */   {
/* 181 */     throw new SQLException("Conversion to String failed");
/*     */   }
/*     */ 
/*     */   public boolean booleanValue()
/*     */     throws SQLException
/*     */   {
/* 192 */     throw new SQLException("Conversion to boolean failed");
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */     throws SQLException
/*     */   {
/* 203 */     throw new SQLException("Conversion to integer failed");
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */     throws SQLException
/*     */   {
/* 214 */     throw new SQLException("Conversion to long failed");
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */     throws SQLException
/*     */   {
/* 225 */     throw new SQLException("Conversion to float failed");
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */     throws SQLException
/*     */   {
/* 236 */     throw new SQLException("Conversion to double failed");
/*     */   }
/*     */ 
/*     */   public byte byteValue()
/*     */     throws SQLException
/*     */   {
/* 247 */     throw new SQLException("Conversion to byte failed");
/*     */   }
/*     */ 
/*     */   public BigDecimal bigDecimalValue()
/*     */     throws SQLException
/*     */   {
/* 258 */     throw new SQLException("Conversion to BigDecimal failed");
/*     */   }
/*     */ 
/*     */   public Date dateValue()
/*     */     throws SQLException
/*     */   {
/* 269 */     throw new SQLException("Conversion to Date failed");
/*     */   }
/*     */ 
/*     */   public Time timeValue()
/*     */     throws SQLException
/*     */   {
/* 280 */     throw new SQLException("Conversion to Time failed");
/*     */   }
/*     */ 
/*     */   public Timestamp timestampValue()
/*     */     throws SQLException
/*     */   {
/* 291 */     throw new SQLException("Conversion to Timestamp failed");
/*     */   }
/*     */ 
/*     */   public Reader characterStreamValue()
/*     */     throws SQLException
/*     */   {
/* 302 */     throw new SQLException("Conversion to character stream failed");
/*     */   }
/*     */ 
/*     */   public InputStream asciiStreamValue()
/*     */     throws SQLException
/*     */   {
/* 313 */     throw new SQLException("Conversion to ascii stream failed");
/*     */   }
/*     */ 
/*     */   public InputStream binaryStreamValue()
/*     */     throws SQLException
/*     */   {
/* 324 */     throw new SQLException("Conversion to binary stream failed");
/*     */   }
/*     */ 
/*     */   public abstract boolean isConvertibleTo(Class paramClass);
/*     */ 
/*     */   public abstract Object toJdbc()
/*     */     throws SQLException;
/*     */ 
/*     */   public abstract Object makeJdbcArray(int paramInt);
/*     */ 
/*     */   protected static int compareBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 362 */     int i = paramArrayOfByte1.length;
/* 363 */     int j = paramArrayOfByte2.length;
/* 364 */     int k = 0;
/* 365 */     int m = Math.min(i, j);
/* 366 */     int n = 0;
/* 367 */     int i1 = 0;
/*     */ 
/* 369 */     while (k < m)
/*     */     {
/* 372 */       n = paramArrayOfByte1[k] & 0xFF;
/* 373 */       i1 = paramArrayOfByte2[k] & 0xFF;
/*     */ 
/* 375 */       if (n != i1)
/*     */       {
/* 377 */         if (n < i1) {
/* 378 */           return -1;
/*     */         }
/* 380 */         return 1;
/*     */       }
/*     */ 
/* 383 */       k++;
/*     */     }
/*     */ 
/* 388 */     if (i == j)
/* 389 */       return 0;
/* 390 */     if (i > j) {
/* 391 */       return 1;
/*     */     }
/* 393 */     return -1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.Datum
 * JD-Core Version:    0.6.0
 */