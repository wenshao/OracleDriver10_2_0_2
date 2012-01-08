/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.DATE;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ 
/*     */ public class OracleTypeDATE extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -5858803341118747965L;
/* 341 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   public OracleTypeDATE()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeDATE(int paramInt)
/*     */   {
/*  40 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  55 */     DATE localDATE = null;
/*     */ 
/*  57 */     if (paramObject != null)
/*     */     {
/*     */       try
/*     */       {
/*  61 */         if ((paramObject instanceof DATE))
/*  62 */           localDATE = (DATE)paramObject;
/*  63 */         else if ((paramObject instanceof TIMESTAMP))
/*  64 */           localDATE = new DATE(((TIMESTAMP)paramObject).timestampValue());
/*     */         else
/*  66 */           localDATE = new DATE(paramObject);
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/*  70 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  77 */     return localDATE;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  92 */     Datum[] arrayOfDatum = null;
/*     */ 
/*  94 */     if (paramObject != null)
/*     */     {
/*  96 */       if ((paramObject instanceof char[][]))
/*     */       {
/*  98 */         char[][] arrayOfChar = (char[][])paramObject;
/*  99 */         int i = (int)(paramInt == -1 ? arrayOfChar.length : Math.min(arrayOfChar.length - paramLong + 1L, paramInt));
/*     */ 
/* 102 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 104 */         for (int j = 0; j < i; j++) {
/* 105 */           arrayOfDatum[j] = toDatum(new String(arrayOfChar[((int)paramLong + j - 1)]), paramOracleConnection);
/*     */         }
/*     */       }
/* 108 */       if ((paramObject instanceof Object[]))
/*     */       {
/* 110 */         return super.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */       }
/*     */ 
/* 113 */       DatabaseError.throwSqlException(59, paramObject);
/*     */     }
/*     */ 
/* 119 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/* 127 */     return 91;
/*     */   }
/*     */ 
/*     */   public int getSizeLDS(byte[] paramArrayOfByte)
/*     */   {
/* 142 */     if (this.sizeForLds == 0)
/*     */     {
/* 144 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 11);
/* 145 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 11);
/*     */     }
/*     */ 
/* 150 */     return this.sizeForLds;
/*     */   }
/*     */ 
/*     */   public int getAlignLDS(byte[] paramArrayOfByte)
/*     */   {
/* 159 */     if (this.sizeForLds == 0)
/*     */     {
/* 161 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 11);
/* 162 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 11);
/*     */     }
/*     */ 
/* 167 */     return this.alignForLds;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 185 */     switch (paramInt1)
/*     */     {
/*     */     case 1:
/* 189 */       if (paramUnpickleContext.isNull(this.nullOffset)) {
/* 190 */         return null;
/*     */       }
/* 192 */       paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
/*     */ 
/* 194 */       break;
/*     */     case 2:
/* 198 */       if ((paramUnpickleContext.readByte() & 0x1) != 1)
/*     */         break;
/* 200 */       paramUnpickleContext.skipBytes(8);
/*     */ 
/* 202 */       return null;
/*     */     case 3:
/* 206 */       break;
/*     */     }
/*     */ 
/* 209 */     DatabaseError.throwSqlException(1, "format=" + paramInt1);
/*     */ 
/* 214 */     if (paramInt2 == 9)
/*     */     {
/* 216 */       paramUnpickleContext.skipBytes(8);
/*     */ 
/* 218 */       return null;
/*     */     }
/*     */ 
/* 222 */     byte[] arrayOfByte1 = paramUnpickleContext.image();
/* 223 */     int i = paramUnpickleContext.absoluteOffset();
/*     */ 
/* 227 */     byte[] arrayOfByte2 = new byte[7];
/*     */     int j;
/* 230 */     if (paramUnpickleContext.bigEndian)
/* 231 */       j = (arrayOfByte1[i] & 0xFF) * 256 + (arrayOfByte1[(i + 1)] & 0xFF);
/*     */     else {
/* 233 */       j = (arrayOfByte1[(i + 1)] & 0xFF) * 256 + (arrayOfByte1[i] & 0xFF);
/*     */     }
/* 235 */     if (j < 0)
/*     */     {
/* 240 */       arrayOfByte2[0] = (byte)(100 - -j / 100);
/* 241 */       arrayOfByte2[1] = (byte)(100 - -j % 100);
/*     */     }
/*     */     else
/*     */     {
/* 245 */       arrayOfByte2[0] = (byte)(j / 100 + 100);
/* 246 */       arrayOfByte2[1] = (byte)(j % 100 + 100);
/*     */     }
/*     */ 
/* 249 */     arrayOfByte2[2] = arrayOfByte1[(i + 2)];
/* 250 */     arrayOfByte2[3] = arrayOfByte1[(i + 3)];
/* 251 */     arrayOfByte2[4] = (byte)(arrayOfByte1[(i + 4)] + 1);
/* 252 */     arrayOfByte2[5] = (byte)(arrayOfByte1[(i + 5)] + 1);
/* 253 */     arrayOfByte2[6] = (byte)(arrayOfByte1[(i + 6)] + 1);
/*     */ 
/* 255 */     paramUnpickleContext.skipBytes(8);
/*     */ 
/* 257 */     return toObject(arrayOfByte2, paramInt2, paramMap);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 281 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 282 */       return null;
/*     */     }
/* 284 */     if (paramInt == 1)
/* 285 */       return new DATE(paramArrayOfByte);
/* 286 */     if (paramInt == 2)
/* 287 */       return DATE.toTimestamp(paramArrayOfByte);
/* 288 */     if (paramInt == 3) {
/* 289 */       return paramArrayOfByte;
/*     */     }
/* 291 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 294 */     return null;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeDATE
 * JD-Core Version:    0.6.0
 */