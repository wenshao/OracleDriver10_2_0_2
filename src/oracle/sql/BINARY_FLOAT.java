/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class BINARY_FLOAT extends Datum
/*     */ {
/* 225 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*     */ 
/*     */   public BINARY_FLOAT()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BINARY_FLOAT(byte[] paramArrayOfByte)
/*     */   {
/*  48 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public BINARY_FLOAT(float paramFloat)
/*     */   {
/*  63 */     super(floatToCanonicalFormatBytes(paramFloat));
/*     */   }
/*     */ 
/*     */   public BINARY_FLOAT(Float paramFloat)
/*     */   {
/*  78 */     super(floatToCanonicalFormatBytes(paramFloat.floatValue()));
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/*  97 */     return new Float(canonicalFormatBytesToFloat(getBytes()));
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 116 */     String str = paramClass.getName();
/*     */ 
/* 118 */     return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Float") == 0);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 133 */     String str = Float.toString(canonicalFormatBytesToFloat(getBytes()));
/*     */ 
/* 137 */     return str;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 148 */     return new Float[paramInt];
/*     */   }
/*     */ 
/*     */   static byte[] floatToCanonicalFormatBytes(float paramFloat)
/*     */   {
/* 153 */     float f = paramFloat;
/*     */ 
/* 155 */     if (f == 0.0F)
/* 156 */       f = 0.0F;
/* 157 */     else if (f != f) {
/* 158 */       f = (0.0F / 0.0F);
/*     */     }
/* 160 */     int i = Float.floatToIntBits(f);
/* 161 */     byte[] arrayOfByte = new byte[4];
/*     */ 
/* 163 */     int j = i;
/*     */ 
/* 165 */     i >>= 8;
/*     */ 
/* 167 */     int k = i;
/*     */ 
/* 169 */     i >>= 8;
/*     */ 
/* 171 */     int m = i;
/*     */ 
/* 173 */     i >>= 8;
/*     */ 
/* 175 */     int n = i;
/*     */ 
/* 177 */     if ((n & 0x80) == 0) {
/* 178 */       n |= 128;
/*     */     }
/*     */     else {
/* 181 */       n ^= -1;
/* 182 */       m ^= -1;
/* 183 */       k ^= -1;
/* 184 */       j ^= -1;
/*     */     }
/*     */ 
/* 187 */     arrayOfByte[3] = (byte)j;
/* 188 */     arrayOfByte[2] = (byte)k;
/* 189 */     arrayOfByte[1] = (byte)m;
/* 190 */     arrayOfByte[0] = (byte)n;
/*     */ 
/* 192 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   static float canonicalFormatBytesToFloat(byte[] paramArrayOfByte)
/*     */   {
/* 198 */     int i = paramArrayOfByte[0];
/* 199 */     int j = paramArrayOfByte[1];
/* 200 */     int k = paramArrayOfByte[2];
/* 201 */     int m = paramArrayOfByte[3];
/*     */ 
/* 203 */     if ((i & 0x80) != 0)
/*     */     {
/* 205 */       i &= 127;
/* 206 */       j &= 255;
/* 207 */       k &= 255;
/* 208 */       m &= 255;
/*     */     }
/*     */     else
/*     */     {
/* 212 */       i = (i ^ 0xFFFFFFFF) & 0xFF;
/* 213 */       j = (j ^ 0xFFFFFFFF) & 0xFF;
/* 214 */       k = (k ^ 0xFFFFFFFF) & 0xFF;
/* 215 */       m = (m ^ 0xFFFFFFFF) & 0xFF;
/*     */     }
/*     */ 
/* 218 */     int n = i << 24 | j << 16 | k << 8 | m;
/*     */ 
/* 220 */     return Float.intBitsToFloat(n);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.BINARY_FLOAT
 * JD-Core Version:    0.6.0
 */