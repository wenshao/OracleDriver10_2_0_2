/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeFLOAT extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 4088841548269771109L;
/*     */   int precision;
/* 227 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  47 */     return OracleTypeNUMBER.toNUMBER(paramObject, paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  59 */     return OracleTypeNUMBER.toNUMBERArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/*  67 */     return 6;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/*  76 */     this.nullOffset = paramTDSReader.nullOffset;
/*  77 */     this.ldsOffset = paramTDSReader.ldsOffset;
/*     */ 
/*  79 */     paramTDSReader.nullOffset += 1;
/*  80 */     paramTDSReader.ldsOffset += 1;
/*     */ 
/*  86 */     this.precision = paramTDSReader.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   public int getScale()
/*     */   {
/*  97 */     return -127;
/*     */   }
/*     */ 
/*     */   public int getPrecision()
/*     */   {
/* 102 */     return this.precision;
/*     */   }
/*     */ 
/*     */   public int getSizeLDS(byte[] paramArrayOfByte)
/*     */   {
/* 109 */     if (this.sizeForLds == 0)
/*     */     {
/* 111 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 12);
/* 112 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 12);
/*     */     }
/*     */ 
/* 115 */     return this.sizeForLds;
/*     */   }
/*     */ 
/*     */   public int getAlignLDS(byte[] paramArrayOfByte)
/*     */   {
/* 122 */     if (this.sizeForLds == 0)
/*     */     {
/* 124 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 12);
/* 125 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 12);
/*     */     }
/*     */ 
/* 128 */     return this.alignForLds;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 141 */     return OracleTypeNUMBER.numericUnpickle80rec(this.ldsOffset, this.nullOffset, paramUnpickleContext, paramInt1, paramInt2, paramMap);
/*     */   }
/*     */ 
/*     */   protected static Object unpickle80NativeArray(UnpickleContext paramUnpickleContext, long paramLong, int paramInt1, int paramInt2, int paramInt3)
/*     */     throws SQLException
/*     */   {
/* 148 */     return OracleTypeNUMBER.unpickle80NativeArray(paramUnpickleContext, paramLong, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   protected static Object unpickle81NativeArray(PickleContext paramPickleContext, long paramLong, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 164 */     return OracleTypeNUMBER.unpickle81NativeArray(paramPickleContext, paramLong, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 175 */     return OracleTypeNUMBER.toNumericObject(paramArrayOfByte, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 188 */     paramObjectOutputStream.writeInt(this.precision);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 198 */     this.precision = paramObjectInputStream.readInt();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeFLOAT
 * JD-Core Version:    0.6.0
 */