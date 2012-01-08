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
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeBFILE extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -707073491109554687L;
/*  31 */   static int fixedDataSize = 530;
/*     */   transient OracleConnection connection;
/* 192 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   public OracleTypeBFILE()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeBFILE(OracleConnection paramOracleConnection)
/*     */   {
/*  48 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  63 */     BFILE localBFILE = null;
/*     */ 
/*  65 */     if (paramObject != null)
/*     */     {
/*  67 */       if ((paramObject instanceof BFILE))
/*  68 */         localBFILE = (BFILE)paramObject;
/*     */       else {
/*  70 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/*  74 */     return localBFILE;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/*  82 */     return -13;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 104 */     return OracleTypeBLOB.lobUnpickle80rec(this, this.nullOffset, this.ldsOffset, paramUnpickleContext, paramInt1, paramInt2, fixedDataSize);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 125 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 126 */       return null;
/*     */     }
/* 128 */     if ((paramInt == 1) || (paramInt == 2)) {
/* 129 */       return this.connection.createBfile(paramArrayOfByte);
/*     */     }
/*     */ 
/* 132 */     if (paramInt == 3) {
/* 133 */       return paramArrayOfByte;
/*     */     }
/* 135 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 138 */     return null;
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
/* 162 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeBFILE
 * JD-Core Version:    0.6.0
 */