/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ class T4C8TTIBlob extends T4C8TTILob
/*     */ {
/* 274 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4C8TTIBlob(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */   {
/* 106 */     super(paramT4CMAREngine, paramT4CTTIoer);
/*     */   }
/*     */ 
/*     */   Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 131 */     if (paramInt == 12) {
/* 132 */       DatabaseError.throwSqlException(158);
/*     */     }
/*     */ 
/* 136 */     BLOB localBLOB = null;
/*     */ 
/* 139 */     initializeLobdef();
/*     */ 
/* 142 */     this.lobops = 272L;
/* 143 */     this.sourceLobLocator = new byte[86];
/* 144 */     this.sourceLobLocator[1] = 84;
/*     */ 
/* 147 */     this.characterSet = 1;
/*     */ 
/* 150 */     this.lobamt = paramInt;
/* 151 */     this.sendLobamt = true;
/*     */ 
/* 155 */     this.destinationOffset = 113L;
/*     */ 
/* 160 */     this.nullO2U = true;
/*     */ 
/* 162 */     marshalFunHeader();
/*     */ 
/* 165 */     marshalOlobops();
/*     */ 
/* 168 */     receiveReply();
/*     */ 
/* 172 */     if (this.sourceLobLocator != null)
/*     */     {
/* 174 */       localBLOB = new BLOB((OracleConnection)paramConnection, this.sourceLobLocator);
/*     */     }
/*     */ 
/* 181 */     return localBLOB;
/*     */   }
/*     */ 
/*     */   boolean open(byte[] paramArrayOfByte, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 201 */     boolean bool = false;
/*     */ 
/* 205 */     int i = 2;
/*     */ 
/* 207 */     if (paramInt == 0) {
/* 208 */       i = 1;
/*     */     }
/* 210 */     bool = _open(paramArrayOfByte, i, 32768);
/*     */ 
/* 215 */     return bool;
/*     */   }
/*     */ 
/*     */   boolean close(byte[] paramArrayOfByte)
/*     */     throws SQLException, IOException
/*     */   {
/* 235 */     boolean bool = false;
/*     */ 
/* 237 */     bool = _close(paramArrayOfByte, 65536);
/*     */ 
/* 242 */     return bool;
/*     */   }
/*     */ 
/*     */   boolean isOpen(byte[] paramArrayOfByte)
/*     */     throws SQLException, IOException
/*     */   {
/* 262 */     boolean bool = false;
/*     */ 
/* 264 */     bool = _isOpen(paramArrayOfByte, 69632);
/*     */ 
/* 269 */     return bool;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTIBlob
 * JD-Core Version:    0.6.0
 */