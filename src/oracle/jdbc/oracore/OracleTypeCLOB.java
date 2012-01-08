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
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeCLOB extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1122821330765834411L;
/*  33 */   static int fixedDataSize = 86;
/*     */   transient OracleConnection connection;
/*     */   int form;
/* 205 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   protected OracleTypeCLOB()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeCLOB(OracleConnection paramOracleConnection)
/*     */   {
/*  48 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  60 */     CLOB localCLOB = null;
/*     */ 
/*  62 */     if (paramObject != null)
/*     */     {
/*  64 */       if ((paramObject instanceof CLOB))
/*  65 */         localCLOB = (CLOB)paramObject;
/*     */       else {
/*  67 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/*  71 */     return localCLOB;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/*  79 */     return 2005;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 101 */     return OracleTypeBLOB.lobUnpickle80rec(this, this.nullOffset, this.ldsOffset, paramUnpickleContext, paramInt1, paramInt2, fixedDataSize);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 122 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 123 */       return null;
/*     */     }
/* 125 */     if ((paramInt == 1) || (paramInt == 2)) {
/* 126 */       return this.connection.createClob(paramArrayOfByte);
/*     */     }
/*     */ 
/* 129 */     if (paramInt == 3) {
/* 130 */       return paramArrayOfByte;
/*     */     }
/* 132 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 135 */     return null;
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
/* 159 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ 
/*     */   public boolean isNCHAR()
/*     */     throws SQLException
/*     */   {
/* 169 */     return this.form == 2;
/*     */   }
/*     */ 
/*     */   public void setForm(int paramInt)
/*     */   {
/* 174 */     this.form = paramInt;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeCLOB
 * JD-Core Version:    0.6.0
 */