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
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.RAW;
/*     */ 
/*     */ public class OracleTypeRAW extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -6083664758336974576L;
/*     */   int length;
/* 342 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   public OracleTypeRAW()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeRAW(int paramInt)
/*     */   {
/*  45 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  64 */     RAW localRAW = null;
/*     */ 
/*  66 */     if (paramObject != null)
/*     */     {
/*     */       try
/*     */       {
/*  70 */         if ((paramObject instanceof RAW))
/*  71 */           localRAW = (RAW)paramObject;
/*     */         else
/*  73 */           localRAW = new RAW(paramObject);
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/*  77 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  82 */     return localRAW;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  97 */     Datum[] arrayOfDatum = null;
/*     */ 
/*  99 */     if (paramObject != null)
/*     */     {
/* 101 */       if ((paramObject instanceof char[][]))
/*     */       {
/* 103 */         char[][] arrayOfChar = (char[][])paramObject;
/* 104 */         int i = (int)(paramInt == -1 ? arrayOfChar.length : Math.min(arrayOfChar.length - paramLong + 1L, paramInt));
/*     */ 
/* 107 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 109 */         for (int j = 0; j < i; j++) {
/* 110 */           arrayOfDatum[j] = toDatum(new String(arrayOfChar[((int)paramLong + j - 1)]), paramOracleConnection);
/*     */         }
/*     */       }
/* 113 */       if ((paramObject instanceof Object[]))
/*     */       {
/* 115 */         return super.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */       }
/*     */ 
/* 118 */       DatabaseError.throwSqlException(59, paramObject);
/*     */     }
/*     */ 
/* 122 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/* 130 */     return -2;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/* 144 */     super.parseTDSrec(paramTDSReader);
/*     */ 
/* 148 */     this.length = paramTDSReader.readShort();
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 168 */     switch (paramInt1)
/*     */     {
/*     */     case 1:
/* 172 */       if (paramUnpickleContext.isNull(this.nullOffset)) {
/* 173 */         return null;
/*     */       }
/* 175 */       paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
/*     */ 
/* 177 */       if (paramInt2 == 9)
/*     */       {
/* 179 */         paramUnpickleContext.skipBytes(4);
/*     */ 
/* 181 */         return null;
/*     */       }
/*     */ 
/* 184 */       paramUnpickleContext.markAndSkip();
/*     */ 
/* 186 */       byte[] arrayOfByte = paramUnpickleContext.readLengthBytes();
/*     */ 
/* 188 */       paramUnpickleContext.reset();
/*     */ 
/* 190 */       return toObject(arrayOfByte, paramInt2, paramMap);
/*     */     case 2:
/* 193 */       if (((paramUnpickleContext.readByte() & 0x1) == 1) || (paramInt2 == 9))
/*     */       {
/* 195 */         paramUnpickleContext.skipLengthBytes();
/*     */ 
/* 197 */         return null;
/*     */       }
/*     */ 
/* 201 */       return toObject(paramUnpickleContext.readLengthBytes(), paramInt2, paramMap);
/*     */     case 3:
/* 205 */       if (paramInt2 == 9)
/*     */       {
/* 207 */         paramUnpickleContext.skipLengthBytes();
/*     */ 
/* 209 */         return null;
/*     */       }
/*     */ 
/* 213 */       return toObject(paramUnpickleContext.readLengthBytes(), paramInt2, paramMap);
/*     */     }
/*     */ 
/* 217 */     DatabaseError.throwSqlException(1, "format=" + paramInt1);
/*     */ 
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 249 */     if (paramDatum.getLength() > this.length) {
/* 250 */       DatabaseError.throwSqlException(72, this);
/*     */     }
/* 252 */     int i = paramPickleContext.writeLength((int)paramDatum.getLength());
/*     */ 
/* 254 */     i += paramPickleContext.writeData(paramDatum.shareBytes());
/*     */ 
/* 256 */     return i;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 265 */     return this.length;
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 272 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 273 */       return null;
/*     */     }
/* 275 */     switch (paramInt)
/*     */     {
/*     */     case 1:
/* 279 */       return new RAW(paramArrayOfByte);
/*     */     case 2:
/*     */     case 3:
/* 284 */       return paramArrayOfByte;
/*     */     }
/*     */ 
/* 287 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 304 */     paramObjectOutputStream.writeInt(this.length);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 314 */     this.length = paramObjectInputStream.readInt();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeRAW
 * JD-Core Version:    0.6.0
 */