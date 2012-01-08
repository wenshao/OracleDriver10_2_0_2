/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4C8TTIpro extends T4CTTIMsg
/*     */ {
/*     */   short svrCharSet;
/*     */   short svrCharSetElem;
/*     */   byte svrFlags;
/*     */   byte[] proSvrStr;
/*     */   byte proSvrVer;
/*  67 */   short oVersion = -1;
/*     */ 
/*  69 */   boolean svrInfoAvailable = false;
/*     */ 
/*  72 */   byte[] proCliVerTTC8 = { 6, 5, 4, 3, 2, 1, 0 };
/*     */ 
/*  78 */   byte[] proCliStrTTC8 = { 74, 97, 118, 97, 95, 84, 84, 67, 45, 56, 46, 50, 46, 48, 0 };
/*     */ 
/*  85 */   short NCHAR_CHARSET = 0;
/*     */ 
/* 352 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4C8TTIpro(T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException, IOException
/*     */   {
/*  96 */     super(1);
/*     */ 
/* 100 */     setMarshalingEngine(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void receive()
/*     */     throws SQLException, IOException
/*     */   {
/* 131 */     if (this.meg.unmarshalSB1() != 1) {
/* 132 */       DatabaseError.throwSqlException(401);
/*     */     }
/* 134 */     this.proSvrVer = this.meg.unmarshalSB1();
/* 135 */     this.meg.proSvrVer = this.proSvrVer;
/*     */ 
/* 140 */     switch (this.proSvrVer)
/*     */     {
/*     */     case 4:
/* 144 */       this.oVersion = 7230;
/*     */ 
/* 146 */       break;
/*     */     case 5:
/* 150 */       this.oVersion = 8030;
/*     */ 
/* 152 */       break;
/*     */     case 6:
/* 155 */       this.oVersion = 8100;
/*     */ 
/* 157 */       break;
/*     */     default:
/* 160 */       DatabaseError.throwSqlException(444);
/*     */     }
/*     */ 
/* 163 */     this.meg.unmarshalSB1();
/*     */ 
/* 165 */     this.proSvrStr = this.meg.unmarshalTEXT(50);
/* 166 */     this.oVersion = getOracleVersion();
/*     */ 
/* 169 */     this.svrCharSet = (short)this.meg.unmarshalUB2();
/* 170 */     this.svrFlags = (byte)this.meg.unmarshalUB1();
/*     */ 
/* 173 */     if ((this.svrCharSetElem = (short)this.meg.unmarshalUB2()) > 0)
/*     */     {
/* 180 */       this.meg.unmarshalNBytes(this.svrCharSetElem * 5);
/*     */     }
/*     */ 
/* 183 */     this.svrInfoAvailable = true;
/*     */ 
/* 186 */     if (this.proSvrVer < 5) {
/* 187 */       return;
/*     */     }
/*     */ 
/* 194 */     byte b = this.meg.types.getRep(1);
/*     */ 
/* 199 */     this.meg.types.setRep(1, 0);
/*     */ 
/* 201 */     int i = this.meg.unmarshalUB2();
/*     */ 
/* 207 */     this.meg.types.setRep(1, b);
/*     */ 
/* 209 */     byte[] arrayOfByte = this.meg.unmarshalNBytes(i);
/*     */ 
/* 212 */     int j = 6 + (arrayOfByte[5] & 0xFF) + (arrayOfByte[6] & 0xFF);
/*     */ 
/* 214 */     this.NCHAR_CHARSET = (short)((arrayOfByte[(j + 3)] & 0xFF) << 8);
/* 215 */     this.NCHAR_CHARSET = (short)(this.NCHAR_CHARSET | (short)(arrayOfByte[(j + 4)] & 0xFF));
/*     */ 
/* 217 */     if (this.proSvrVer < 6) {
/* 218 */       return;
/*     */     }
/*     */ 
/* 223 */     int k = this.meg.unmarshalUB1();
/*     */ 
/* 225 */     for (int m = 0; m < k; m++) {
/* 226 */       this.meg.unmarshalUB1();
/*     */     }
/*     */ 
/* 229 */     k = this.meg.unmarshalUB1();
/*     */ 
/* 231 */     for (m = 0; m < k; m++)
/* 232 */       this.meg.unmarshalUB1();
/*     */   }
/*     */ 
/*     */   short getOracleVersion()
/*     */   {
/* 253 */     return this.oVersion;
/*     */   }
/*     */ 
/*     */   short getCharacterSet()
/*     */   {
/* 267 */     return this.svrCharSet;
/*     */   }
/*     */ 
/*     */   short getncharCHARSET()
/*     */   {
/* 276 */     return this.NCHAR_CHARSET;
/*     */   }
/*     */ 
/*     */   byte getFlags()
/*     */   {
/* 290 */     return this.svrFlags;
/*     */   }
/*     */ 
/*     */   void marshal()
/*     */     throws SQLException, IOException
/*     */   {
/* 299 */     marshalTTCcode();
/*     */ 
/* 301 */     this.meg.marshalB1Array(this.proCliVerTTC8);
/* 302 */     this.meg.marshalB1Array(this.proCliStrTTC8);
/*     */   }
/*     */ 
/*     */   void printServerInfo(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 314 */     OracleLog.print(this, paramInt1, paramInt2, paramInt3, " ---- Server's Information ---- ");
/*     */ 
/* 317 */     if (this.svrInfoAvailable)
/*     */     {
/* 319 */       int i = 0;
/*     */ 
/* 321 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "Protocol version :" + this.proSvrVer);
/*     */ 
/* 323 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "oVersion :" + this.oVersion);
/*     */ 
/* 325 */       StringWriter localStringWriter = new StringWriter();
/*     */ 
/* 327 */       localStringWriter.write("Protocol string  :");
/*     */ 
/* 329 */       while (i < this.proSvrStr.length) {
/* 330 */         localStringWriter.write((char)this.proSvrStr[(i++)]);
/*     */       }
/* 332 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, localStringWriter.toString());
/* 333 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "Caracter Set ID  :" + this.svrCharSet);
/*     */ 
/* 335 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "Remote flags     :" + this.svrFlags);
/*     */ 
/* 337 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "Number of Elements in Server's Character Set Graph :" + this.svrCharSetElem);
/*     */ 
/* 340 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, "Don't expect the graph, we threw it away :-)");
/*     */     }
/*     */     else
/*     */     {
/* 344 */       OracleLog.print(this, paramInt1, paramInt2, paramInt3, " Not Available !!");
/*     */     }
/* 346 */     OracleLog.print(this, paramInt1, paramInt2, paramInt3, " ---- -------------------- ---- ");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTIpro
 * JD-Core Version:    0.6.0
 */