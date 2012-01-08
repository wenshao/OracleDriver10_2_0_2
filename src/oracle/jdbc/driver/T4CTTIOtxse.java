/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4CTTIOtxse extends T4CTTIfun
/*     */ {
/*     */   static final int OTXSTA = 1;
/*     */   static final int OTXDET = 2;
/*     */   static final int OCI_TRANS_NEW = 1;
/*     */   static final int OCI_TRANS_JOIN = 2;
/*     */   static final int OCI_TRANS_RESUME = 4;
/*     */   static final int OCI_TRANS_STARTMASK = 255;
/*     */   static final int OCI_TRANS_READONLY = 256;
/*     */   static final int OCI_TRANS_READWRITE = 512;
/*     */   static final int OCI_TRANS_SERIALIZABLE = 1024;
/*     */   static final int OCI_TRANS_ISOLMASK = 65280;
/*     */   static final int OCI_TRANS_LOOSE = 65536;
/*     */   static final int OCI_TRANS_TIGHT = 131072;
/*     */   static final int OCI_TRANS_TYPEMASK = 983040;
/*     */   static final int OCI_TRANS_NOMIGRATE = 1048576;
/*     */   static final int OCI_TRANS_SEPARABLE = 2097152;
/* 115 */   boolean sendTransactionContext = false;
/*     */   T4CConnection connection;
/* 292 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIOtxse(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer, T4CConnection paramT4CConnection)
/*     */     throws SQLException
/*     */   {
/* 121 */     super(3, 0, 103);
/*     */ 
/* 123 */     this.oer = paramT4CTTIoer;
/*     */ 
/* 125 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/* 127 */     this.connection = paramT4CConnection;
/*     */   }
/*     */ 
/*     */   void marshal(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     throws IOException, SQLException
/*     */   {
/* 138 */     if ((paramInt1 != 1) && (paramInt1 != 2)) {
/* 139 */       throw new SQLException("Invalid operation.");
/*     */     }
/* 141 */     super.marshalFunHeader();
/*     */ 
/* 144 */     int i = paramInt1;
/*     */ 
/* 146 */     this.meg.marshalSWORD(i);
/*     */ 
/* 149 */     if (paramInt1 == 2)
/*     */     {
/* 151 */       if (paramArrayOfByte1 == null) {
/* 152 */         throw new SQLException("Transaction context cannot be null when detach is called.");
/*     */       }
/* 154 */       this.sendTransactionContext = true;
/*     */ 
/* 156 */       this.meg.marshalPTR();
/*     */     }
/*     */     else
/*     */     {
/* 160 */       this.sendTransactionContext = false;
/*     */ 
/* 162 */       this.meg.marshalNULLPTR();
/*     */     }
/*     */ 
/* 166 */     if (paramArrayOfByte1 == null)
/* 167 */       this.meg.marshalUB4(0L);
/*     */     else {
/* 169 */       this.meg.marshalUB4(paramArrayOfByte1.length);
/*     */     }
/*     */ 
/* 172 */     this.meg.marshalUB4(paramInt2);
/*     */ 
/* 175 */     this.meg.marshalUB4(paramInt3);
/*     */ 
/* 178 */     this.meg.marshalUB4(paramInt4);
/*     */ 
/* 181 */     if (paramArrayOfByte2 != null)
/* 182 */       this.meg.marshalPTR();
/*     */     else {
/* 184 */       this.meg.marshalNULLPTR();
/*     */     }
/*     */ 
/* 187 */     if (paramArrayOfByte2 != null)
/* 188 */       this.meg.marshalUB4(paramArrayOfByte2.length);
/*     */     else {
/* 190 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */ 
/* 193 */     this.meg.marshalUB4(paramInt6);
/*     */ 
/* 196 */     this.meg.marshalUWORD(paramInt5);
/*     */ 
/* 199 */     this.meg.marshalPTR();
/*     */ 
/* 201 */     this.meg.marshalPTR();
/* 202 */     this.meg.marshalPTR();
/*     */ 
/* 206 */     if (this.sendTransactionContext) {
/* 207 */       this.meg.marshalB1Array(paramArrayOfByte1);
/*     */     }
/* 209 */     if (paramArrayOfByte2 != null) {
/* 210 */       this.meg.marshalB1Array(paramArrayOfByte2);
/*     */     }
/* 212 */     this.meg.marshalUB4(0L);
/*     */   }
/*     */ 
/*     */   byte[] receive(int[] paramArrayOfInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 227 */     byte[] arrayOfByte = null;
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 233 */         int i = this.meg.unmarshalSB1();
/*     */ 
/* 235 */         switch (i)
/*     */         {
/*     */         case 8:
/* 239 */           paramArrayOfInt[0] = (int)this.meg.unmarshalUB4();
/*     */ 
/* 242 */           int j = this.meg.unmarshalUB2();
/*     */ 
/* 244 */           arrayOfByte = this.meg.unmarshalNBytes(j);
/*     */ 
/* 246 */           break;
/*     */         case 9:
/* 249 */           if (this.meg.versionNumber < 10000)
/*     */             continue;
/* 251 */           short s = (short)this.meg.unmarshalUB2();
/*     */ 
/* 253 */           this.connection.endToEndECIDSequenceNumber = s;
/*     */ 
/* 256 */           break;
/*     */         case 4:
/* 259 */           this.oer.init();
/* 260 */           this.oer.unmarshal();
/*     */           try
/*     */           {
/* 264 */             this.oer.processError();
/*     */           }
/*     */           catch (SQLException localSQLException)
/*     */           {
/* 268 */             throw localSQLException;
/*     */           }
/*     */ 
/* 271 */           break;
/*     */         default:
/* 276 */           DatabaseError.throwSqlException(401);
/*     */         }
/*     */ 
/* 282 */         continue;
/*     */       }
/*     */       catch (BreakNetException localBreakNetException) {
/*     */       }
/*     */     }
/* 287 */     return arrayOfByte;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIOtxse
 * JD-Core Version:    0.6.0
 */