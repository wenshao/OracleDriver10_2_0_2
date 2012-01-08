/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class BINARY_DOUBLE extends Datum
/*     */ {
/* 268 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*     */ 
/*     */   public BINARY_DOUBLE()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BINARY_DOUBLE(byte[] paramArrayOfByte)
/*     */   {
/*  49 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public BINARY_DOUBLE(double paramDouble)
/*     */   {
/*  64 */     super(doubleToCanonicalFormatBytes(paramDouble));
/*     */   }
/*     */ 
/*     */   public BINARY_DOUBLE(Double paramDouble)
/*     */   {
/*  79 */     super(doubleToCanonicalFormatBytes(paramDouble.doubleValue()));
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/*  98 */     return new Double(canonicalFormatBytesToDouble(getBytes()));
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 117 */     String str = paramClass.getName();
/*     */ 
/* 119 */     return (str.compareTo("java.lang.String") == 0) || (str.compareTo("java.lang.Double") == 0);
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 134 */     String str = Double.toString(canonicalFormatBytesToDouble(getBytes()));
/*     */ 
/* 138 */     return str;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 150 */     return new Double[paramInt];
/*     */   }
/*     */ 
/*     */   static byte[] doubleToCanonicalFormatBytes(double paramDouble)
/*     */   {
/* 156 */     double d = paramDouble;
/*     */ 
/* 158 */     if (d == 0.0D)
/* 159 */       d = 0.0D;
/* 160 */     else if (d != d) {
/* 161 */       d = (0.0D / 0.0D);
/*     */     }
/* 163 */     long l = Double.doubleToLongBits(d);
/* 164 */     byte[] arrayOfByte = new byte[8];
/* 165 */     int i = (int)l;
/* 166 */     int j = (int)(l >> 32);
/*     */ 
/* 168 */     int k = i;
/*     */ 
/* 171 */     i >>= 8;
/*     */ 
/* 173 */     int m = i;
/*     */ 
/* 175 */     i >>= 8;
/*     */ 
/* 177 */     int n = i;
/*     */ 
/* 179 */     i >>= 8;
/*     */ 
/* 181 */     int i1 = i;
/*     */ 
/* 183 */     int i2 = j;
/*     */ 
/* 185 */     j >>= 8;
/*     */ 
/* 187 */     int i3 = j;
/*     */ 
/* 189 */     j >>= 8;
/*     */ 
/* 191 */     int i4 = j;
/*     */ 
/* 193 */     j >>= 8;
/*     */ 
/* 195 */     int i5 = j;
/*     */ 
/* 197 */     if ((i5 & 0x80) == 0)
/*     */     {
/* 199 */       i5 |= 128;
/*     */     }
/*     */     else
/*     */     {
/* 203 */       i5 ^= -1;
/* 204 */       i4 ^= -1;
/* 205 */       i3 ^= -1;
/* 206 */       i2 ^= -1;
/* 207 */       i1 ^= -1;
/* 208 */       n ^= -1;
/* 209 */       m ^= -1;
/* 210 */       k ^= -1;
/*     */     }
/*     */ 
/* 213 */     arrayOfByte[7] = (byte)k;
/* 214 */     arrayOfByte[6] = (byte)m;
/* 215 */     arrayOfByte[5] = (byte)n;
/* 216 */     arrayOfByte[4] = (byte)i1;
/* 217 */     arrayOfByte[3] = (byte)i2;
/* 218 */     arrayOfByte[2] = (byte)i3;
/* 219 */     arrayOfByte[1] = (byte)i4;
/* 220 */     arrayOfByte[0] = (byte)i5;
/*     */ 
/* 222 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   static double canonicalFormatBytesToDouble(byte[] paramArrayOfByte)
/*     */   {
/* 227 */     int i = paramArrayOfByte[0];
/* 228 */     int j = paramArrayOfByte[1];
/* 229 */     int k = paramArrayOfByte[2];
/* 230 */     int m = paramArrayOfByte[3];
/* 231 */     int n = paramArrayOfByte[4];
/* 232 */     int i1 = paramArrayOfByte[5];
/* 233 */     int i2 = paramArrayOfByte[6];
/* 234 */     int i3 = paramArrayOfByte[7];
/*     */ 
/* 236 */     if ((i & 0x80) != 0)
/*     */     {
/* 238 */       i &= 127;
/* 239 */       j &= 255;
/* 240 */       k &= 255;
/* 241 */       m &= 255;
/* 242 */       n &= 255;
/* 243 */       i1 &= 255;
/* 244 */       i2 &= 255;
/* 245 */       i3 &= 255;
/*     */     }
/*     */     else
/*     */     {
/* 249 */       i = (i ^ 0xFFFFFFFF) & 0xFF;
/* 250 */       j = (j ^ 0xFFFFFFFF) & 0xFF;
/* 251 */       k = (k ^ 0xFFFFFFFF) & 0xFF;
/* 252 */       m = (m ^ 0xFFFFFFFF) & 0xFF;
/* 253 */       n = (n ^ 0xFFFFFFFF) & 0xFF;
/* 254 */       i1 = (i1 ^ 0xFFFFFFFF) & 0xFF;
/* 255 */       i2 = (i2 ^ 0xFFFFFFFF) & 0xFF;
/* 256 */       i3 = (i3 ^ 0xFFFFFFFF) & 0xFF;
/*     */     }
/*     */ 
/* 259 */     int i4 = i << 24 | j << 16 | k << 8 | m;
/* 260 */     int i5 = n << 24 | i1 << 16 | i2 << 8 | i3;
/* 261 */     long l = i4 << 32 | i5 & 0xFFFFFFFF;
/*     */ 
/* 263 */     return Double.longBitsToDouble(l);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.BINARY_DOUBLE
 * JD-Core Version:    0.6.0
 */