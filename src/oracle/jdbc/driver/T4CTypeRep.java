/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CTypeRep
/*     */ {
/*     */   short oVersion;
/*     */   static final byte PRO = 1;
/*     */   static final byte DTY = 2;
/*     */   static final byte RXH = 3;
/*     */   static final byte UDS = 4;
/*     */   static final byte OAC = 5;
/*     */   static final byte DSC = 1;
/*     */   static final byte DTYDTY = 20;
/*     */   static final byte DTYRXH8 = 21;
/*     */   static final byte DTYUDS8 = 22;
/*     */   static final byte DTYOAC8 = 23;
/*     */   static final byte NATIVE = 0;
/*     */   static final byte UNIVERSAL = 1;
/*     */   static final byte LSB = 2;
/*     */   static final byte MAXREP = 3;
/*     */   static final byte B1 = 0;
/*     */   static final byte B2 = 1;
/*     */   static final byte B4 = 2;
/*     */   static final byte B8 = 3;
/*     */   static final byte PTR = 4;
/*     */   static final byte MAXTYPE = 4;
/*     */   byte[] rep;
/* 172 */   final byte NUMREPS = 5;
/*     */   byte conversionFlags;
/*     */   boolean serverConversion;
/* 359 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTypeRep()
/*     */   {
/* 190 */     this.conversionFlags = 0;
/* 191 */     this.serverConversion = false;
/* 192 */     this.rep = new byte[5];
/*     */ 
/* 195 */     this.rep[0] = 0;
/* 196 */     this.rep[1] = 1;
/* 197 */     this.rep[2] = 1;
/* 198 */     this.rep[3] = 1;
/* 199 */     this.rep[4] = 1;
/*     */   }
/*     */ 
/*     */   void setRep(byte paramByte1, byte paramByte2)
/*     */     throws SQLException
/*     */   {
/* 217 */     if ((paramByte1 < 0) || (paramByte1 > 4) || (paramByte2 > 3))
/*     */     {
/* 224 */       DatabaseError.throwSqlException(407);
/*     */     }
/*     */ 
/* 232 */     this.rep[paramByte1] = paramByte2;
/*     */   }
/*     */ 
/*     */   byte getRep(byte paramByte)
/*     */     throws SQLException
/*     */   {
/* 252 */     if ((paramByte < 0) || (paramByte > 4))
/*     */     {
/* 257 */       DatabaseError.throwSqlException(408);
/*     */     }
/*     */ 
/* 262 */     return this.rep[paramByte];
/*     */   }
/*     */ 
/*     */   void setFlags(byte paramByte)
/*     */   {
/* 276 */     this.conversionFlags = paramByte;
/*     */   }
/*     */ 
/*     */   byte getFlags()
/*     */   {
/* 289 */     return this.conversionFlags;
/*     */   }
/*     */ 
/*     */   boolean isConvNeeded()
/*     */   {
/* 299 */     int i = (this.conversionFlags & 0x2) > 0 ? 1 : 0;
/*     */ 
/* 304 */     return i;
/*     */   }
/*     */ 
/*     */   void setServerConversion(boolean paramBoolean)
/*     */   {
/* 318 */     this.serverConversion = paramBoolean;
/*     */   }
/*     */ 
/*     */   boolean isServerConversion()
/*     */   {
/* 330 */     return this.serverConversion;
/*     */   }
/*     */ 
/*     */   void setVersion(short paramShort)
/*     */   {
/* 343 */     this.oVersion = paramShort;
/*     */   }
/*     */ 
/*     */   short getVersion()
/*     */   {
/* 354 */     return this.oVersion;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTypeRep
 * JD-Core Version:    0.6.0
 */