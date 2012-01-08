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
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeBLOB extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -2311211431562030662L;
/*  33 */   static int fixedDataSize = 86;
/*     */   transient OracleConnection connection;
/* 272 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   protected OracleTypeBLOB()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeBLOB(OracleConnection paramOracleConnection)
/*     */   {
/*  51 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  66 */     BLOB localBLOB = null;
/*     */ 
/*  68 */     if (paramObject != null)
/*     */     {
/*  70 */       if ((paramObject instanceof BLOB))
/*  71 */         localBLOB = (BLOB)paramObject;
/*     */       else {
/*  73 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/*  77 */     return localBLOB;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/*  85 */     return 2004;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 107 */     return lobUnpickle80rec(this, this.nullOffset, this.ldsOffset, paramUnpickleContext, paramInt1, paramInt2, fixedDataSize);
/*     */   }
/*     */ 
/*     */   protected static Object lobUnpickle80rec(OracleType paramOracleType, int paramInt1, int paramInt2, UnpickleContext paramUnpickleContext, int paramInt3, int paramInt4, int paramInt5)
/*     */     throws SQLException
/*     */   {
/* 118 */     switch (paramInt3)
/*     */     {
/*     */     case 1:
/* 123 */       if (paramUnpickleContext.isNull(paramInt1)) {
/* 124 */         return null;
/*     */       }
/* 126 */       paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[paramInt2]);
/*     */ 
/* 128 */       if (paramInt4 == 9)
/*     */       {
/* 130 */         paramUnpickleContext.skipBytes(4);
/*     */ 
/* 132 */         return null;
/*     */       }
/*     */ 
/* 135 */       paramUnpickleContext.markAndSkip();
/*     */ 
/* 137 */       byte[] arrayOfByte1 = paramUnpickleContext.readPtrBytes();
/*     */ 
/* 139 */       paramUnpickleContext.reset();
/*     */ 
/* 141 */       return paramOracleType.toObject(arrayOfByte1, paramInt4, null);
/*     */     case 2:
/* 145 */       if ((paramUnpickleContext.readByte() & 0x1) != 1)
/*     */         break;
/* 147 */       paramUnpickleContext.skipPtrBytes();
/*     */ 
/* 149 */       return null;
/*     */     case 3:
/* 156 */       long l = paramUnpickleContext.offset() + paramInt5;
/*     */ 
/* 158 */       if (paramInt4 == 9)
/*     */       {
/* 160 */         paramUnpickleContext.skipTo(l);
/*     */ 
/* 162 */         return null;
/*     */       }
/*     */ 
/* 165 */       byte[] arrayOfByte2 = paramUnpickleContext.readPtrBytes();
/*     */ 
/* 167 */       if (paramUnpickleContext.offset() < l) {
/* 168 */         paramUnpickleContext.skipTo(l);
/*     */       }
/* 170 */       return paramOracleType.toObject(arrayOfByte2, paramInt4, null);
/*     */     }
/*     */ 
/* 173 */     DatabaseError.throwSqlException(1, "format=" + paramInt3);
/*     */ 
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 196 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 197 */       return null;
/*     */     }
/* 199 */     switch (paramInt)
/*     */     {
/*     */     case 1:
/*     */     case 2:
/* 207 */       return this.connection.createBlob(paramArrayOfByte);
/*     */     case 3:
/* 210 */       return paramArrayOfByte;
/*     */     }
/*     */ 
/* 213 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 217 */     return null;
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
/*     */ 
/*     */   public void setConnection(OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 241 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeBLOB
 * JD-Core Version:    0.6.0
 */