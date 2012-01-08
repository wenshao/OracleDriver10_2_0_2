/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class Util
/*     */ {
/* 225 */   private static int[] ldsRoundTable = { 0, 1, 0, 2, 0, 0, 0, 3, 0 };
/*     */ 
/* 287 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   static void checkNextByte(InputStream paramInputStream, byte paramByte)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/*  39 */       if (paramInputStream.read() != paramByte) {
/*  40 */         DatabaseError.throwSqlException(47, "parseTDS");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  45 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int[] toJavaUnsignedBytes(byte[] paramArrayOfByte)
/*     */   {
/*  62 */     int[] arrayOfInt = new int[paramArrayOfByte.length];
/*     */ 
/*  64 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/*  65 */       paramArrayOfByte[i] &= 255;
/*     */     }
/*  67 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   static byte[] readBytes(InputStream paramInputStream, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  75 */     byte[] arrayOfByte1 = new byte[paramInt];
/*     */     try
/*     */     {
/*  79 */       int i = paramInputStream.read(arrayOfByte1);
/*     */ 
/*  81 */       if (i != paramInt)
/*     */       {
/*  83 */         byte[] arrayOfByte2 = new byte[i];
/*     */ 
/*  85 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
/*     */ 
/*  87 */         return arrayOfByte2;
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  92 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/*  95 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */   static void writeBytes(OutputStream paramOutputStream, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 106 */       paramOutputStream.write(paramArrayOfByte);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 110 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */   }
/*     */ 
/*     */   static void skipBytes(InputStream paramInputStream, int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 121 */       paramInputStream.skip(paramInt);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 125 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */   }
/*     */ 
/*     */   static long readLong(InputStream paramInputStream) throws SQLException
/*     */   {
/* 131 */     byte[] arrayOfByte = new byte[4];
/*     */     try
/*     */     {
/* 135 */       paramInputStream.read(arrayOfByte);
/*     */ 
/* 137 */       return (((arrayOfByte[0] & 0xFF) * 256 + (arrayOfByte[1] & 0xFF)) * 256 + (arrayOfByte[2] & 0xFF)) * 256 + (arrayOfByte[3] & 0xFF);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 142 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/* 145 */     return 0L;
/*     */   }
/*     */ 
/*     */   static short readShort(InputStream paramInputStream) throws SQLException
/*     */   {
/* 150 */     byte[] arrayOfByte = new byte[2];
/*     */     try
/*     */     {
/* 154 */       paramInputStream.read(arrayOfByte);
/*     */ 
/* 156 */       return (short)((arrayOfByte[0] & 0xFF) * 256 + (arrayOfByte[1] & 0xFF));
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 160 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   static byte readByte(InputStream paramInputStream) throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 170 */       return (byte)paramInputStream.read();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 174 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */   static byte fdoGetSize(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 185 */     int i = fdoGetEntry(paramArrayOfByte, paramInt);
/*     */ 
/* 188 */     return (byte)(i >> 3 & 0x1F);
/*     */   }
/*     */ 
/*     */   static byte fdoGetAlign(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 196 */     int i = fdoGetEntry(paramArrayOfByte, paramInt);
/*     */ 
/* 199 */     return (byte)(i & 0x7);
/*     */   }
/*     */ 
/*     */   static int ldsRound(int paramInt1, int paramInt2)
/*     */   {
/* 209 */     int i = ldsRoundTable[paramInt2];
/*     */ 
/* 211 */     return (paramInt1 >> i) + 1 << i;
/*     */   }
/*     */ 
/*     */   private static byte fdoGetEntry(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 219 */     int i = getUnsignedByte(paramArrayOfByte[5]);
/* 220 */     int j = paramArrayOfByte[(6 + i + paramInt)];
/*     */ 
/* 222 */     return j;
/*     */   }
/*     */ 
/*     */   public static short getUnsignedByte(byte paramByte)
/*     */   {
/* 232 */     return (short)(paramByte & 0xFF);
/*     */   }
/*     */ 
/*     */   public static byte[] serializeObject(Object paramObject) throws IOException
/*     */   {
/* 237 */     if (paramObject == null) {
/* 238 */       return null;
/*     */     }
/* 240 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 241 */     ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
/*     */ 
/* 243 */     localObjectOutputStream.writeObject(paramObject);
/* 244 */     localObjectOutputStream.flush();
/*     */ 
/* 246 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */   public static Object deserializeObject(byte[] paramArrayOfByte)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 252 */     if (paramArrayOfByte == null) {
/* 253 */       return null;
/*     */     }
/* 255 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
/*     */ 
/* 257 */     return new ObjectInputStream(localByteArrayInputStream).readObject();
/*     */   }
/*     */ 
/*     */   public static void printByteArray(byte[] paramArrayOfByte)
/*     */   {
/* 264 */     System.out.println("DONT CALL THIS -- oracle.jdbc.oracore.Util.printByteArray");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.Util
 * JD-Core Version:    0.6.0
 */