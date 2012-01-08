/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public final class UnpickleContext
/*     */ {
/*     */   byte[] image;
/*     */   int absoluteOffset;
/*     */   int beginOffset;
/*     */   int markedOffset;
/*     */   Vector patches;
/*     */   long[] ldsOffsets;
/*     */   boolean[] nullIndicators;
/*     */   boolean bigEndian;
/* 315 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   public UnpickleContext()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UnpickleContext(byte[] paramArrayOfByte, int paramInt, boolean[] paramArrayOfBoolean, long[] paramArrayOfLong, boolean paramBoolean)
/*     */   {
/*  40 */     this.image = paramArrayOfByte;
/*  41 */     this.beginOffset = paramInt;
/*  42 */     this.absoluteOffset = paramInt;
/*  43 */     this.bigEndian = paramBoolean;
/*  44 */     this.nullIndicators = paramArrayOfBoolean;
/*  45 */     this.patches = null;
/*  46 */     this.ldsOffsets = paramArrayOfLong;
/*     */   }
/*     */ 
/*     */   public byte readByte()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/*  56 */       int i = this.image[this.absoluteOffset];
/*     */       return i; } finally { this.absoluteOffset += 1; } throw localObject;
/*     */   }
/*     */ 
/*     */   public byte[] readVarNumBytes()
/*     */     throws SQLException
/*     */   {
/*  70 */     byte[] arrayOfByte = new byte[this.image[this.absoluteOffset] & 0xFF];
/*     */     try
/*     */     {
/*  74 */       System.arraycopy(this.image, this.absoluteOffset + 1, arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/*     */     finally
/*     */     {
/*  79 */       this.absoluteOffset += 22;
/*     */     }
/*     */ 
/*  84 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public byte[] readPtrBytes()
/*     */     throws SQLException
/*     */   {
/*  93 */     byte[] arrayOfByte = new byte[(this.image[this.absoluteOffset] & 0xFF) * 256 + (this.image[(this.absoluteOffset + 1)] & 0xFF) + 2];
/*     */ 
/*  96 */     System.arraycopy(this.image, this.absoluteOffset, arrayOfByte, 0, arrayOfByte.length);
/*     */ 
/*  98 */     this.absoluteOffset += arrayOfByte.length;
/*     */ 
/* 102 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public void skipPtrBytes()
/*     */     throws SQLException
/*     */   {
/* 112 */     this.absoluteOffset += (this.image[this.absoluteOffset] & 0xFF) * 256 + (this.image[(this.absoluteOffset + 1)] & 0xFF) + 2;
/*     */   }
/*     */ 
/*     */   public byte[] readBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 124 */       byte[] arrayOfByte1 = new byte[paramInt];
/*     */ 
/* 126 */       System.arraycopy(this.image, this.absoluteOffset, arrayOfByte1, 0, paramInt);
/*     */ 
/* 128 */       byte[] arrayOfByte2 = arrayOfByte1;
/*     */       return arrayOfByte2; } finally { this.absoluteOffset += paramInt; } throw localObject;
/*     */   }
/*     */ 
/*     */   public long readLong()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 144 */       long l = (((this.image[this.absoluteOffset] & 0xFF) * 256 + (this.image[(this.absoluteOffset + 1)] & 0xFF)) * 256 + (this.image[(this.absoluteOffset + 2)] & 0xFF)) * 256 + (this.image[(this.absoluteOffset + 3)] & 0xFF);
/*     */       return l; } finally { this.absoluteOffset += 4; } throw localObject;
/*     */   }
/*     */ 
/*     */   public short readShort()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 161 */       int i = (short)((this.image[this.absoluteOffset] & 0xFF) * 256 + (this.image[(this.absoluteOffset + 1)] & 0xFF));
/*     */       return i; } finally { this.absoluteOffset += 2; } throw localObject;
/*     */   }
/*     */ 
/*     */   public byte[] readLengthBytes()
/*     */     throws SQLException
/*     */   {
/* 176 */     long l = readLong();
/*     */ 
/* 178 */     return readBytes((int)l);
/*     */   }
/*     */ 
/*     */   public void skipLengthBytes()
/*     */     throws SQLException
/*     */   {
/* 188 */     long l = readLong();
/*     */ 
/* 190 */     this.absoluteOffset = (int)(this.absoluteOffset + l);
/*     */   }
/*     */ 
/*     */   public void skipTo(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 201 */     if (paramLong > this.absoluteOffset - this.beginOffset)
/* 202 */       this.absoluteOffset = (this.beginOffset + (int)paramLong);
/*     */   }
/*     */ 
/*     */   public void skipTo(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 210 */     if (paramInt > this.absoluteOffset - this.beginOffset)
/* 211 */       this.absoluteOffset = (this.beginOffset + paramInt);
/*     */   }
/*     */ 
/*     */   public void mark()
/*     */     throws SQLException
/*     */   {
/* 220 */     this.markedOffset = this.absoluteOffset;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */     throws SQLException
/*     */   {
/* 229 */     this.absoluteOffset = this.markedOffset;
/*     */   }
/*     */ 
/*     */   public void markAndSkip()
/*     */     throws SQLException
/*     */   {
/* 238 */     this.markedOffset = (this.absoluteOffset + 4);
/* 239 */     this.absoluteOffset = (this.beginOffset + (int)readLong());
/*     */   }
/*     */ 
/*     */   public void markAndSkip(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 249 */     this.markedOffset = this.absoluteOffset;
/* 250 */     this.absoluteOffset = (this.beginOffset + (int)paramLong);
/*     */   }
/*     */ 
/*     */   public void skipBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 260 */     if (paramInt >= 0)
/* 261 */       this.absoluteOffset += paramInt;
/*     */   }
/*     */ 
/*     */   public boolean isNull(int paramInt)
/*     */   {
/* 267 */     return this.nullIndicators[paramInt];
/*     */   }
/*     */ 
/*     */   public int absoluteOffset()
/*     */     throws SQLException
/*     */   {
/* 277 */     return this.absoluteOffset;
/*     */   }
/*     */ 
/*     */   public int offset()
/*     */     throws SQLException
/*     */   {
/* 286 */     return this.absoluteOffset - this.beginOffset;
/*     */   }
/*     */ 
/*     */   public byte[] image()
/*     */     throws SQLException
/*     */   {
/* 292 */     return this.image;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.UnpickleContext
 * JD-Core Version:    0.6.0
 */