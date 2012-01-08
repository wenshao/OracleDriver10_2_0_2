/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4C7Oversion extends T4CTTIfun
/*     */ {
/*  40 */   byte[] rdbmsVersion = { 78, 111, 116, 32, 100, 101, 116, 101, 114, 109, 105, 110, 101, 100, 32, 121, 101, 116 };
/*     */ 
/*  47 */   boolean rdbmsVersionO2U = false;
/*     */ 
/*  49 */   int bufLen = 256;
/*  50 */   boolean retVerLenO2U = false;
/*  51 */   int retVerLen = 0;
/*  52 */   boolean retVerNumO2U = false;
/*  53 */   long retVerNum = 0L;
/*     */   T4CConnection connection;
/* 272 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   T4C7Oversion(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer, T4CConnection paramT4CConnection)
/*     */     throws IOException, SQLException
/*     */   {
/*  64 */     super(3, 0, 59);
/*     */ 
/*  66 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  68 */     this.oer = paramT4CTTIoer;
/*     */ 
/*  71 */     this.rdbmsVersionO2U = true;
/*  72 */     this.retVerLenO2U = true;
/*  73 */     this.retVerNumO2U = true;
/*  74 */     this.connection = paramT4CConnection;
/*     */   }
/*     */ 
/*     */   void receive()
/*     */     throws SQLException, IOException
/*     */   {
/* 101 */     int i = 0;
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 114 */         int j = this.meg.unmarshalSB1();
/*     */ 
/* 120 */         switch (j)
/*     */         {
/*     */         case 8:
/* 126 */           if (i != 0) {
/* 127 */             DatabaseError.throwSqlException(401);
/*     */           }
/*     */ 
/* 131 */           this.retVerLen = this.meg.unmarshalUB2();
/* 132 */           this.rdbmsVersion = this.meg.unmarshalCHR(this.retVerLen);
/*     */ 
/* 134 */           if (this.rdbmsVersion == null) {
/* 135 */             DatabaseError.throwSqlException(438);
/*     */           }
/* 137 */           this.retVerNum = this.meg.unmarshalUB4();
/*     */ 
/* 140 */           i = 1;
/*     */ 
/* 151 */           break;
/*     */         case 4:
/* 158 */           this.oer.init();
/* 159 */           this.oer.unmarshal();
/*     */ 
/* 163 */           this.oer.processError();
/*     */ 
/* 165 */           break;
/*     */         case 9:
/* 171 */           if (getVersionNumber() < 10000)
/*     */             continue;
/* 173 */           short s = (short)this.meg.unmarshalUB2();
/*     */ 
/* 175 */           this.connection.endToEndECIDSequenceNumber = s;
/*     */ 
/* 178 */           break;
/*     */         default:
/* 185 */           DatabaseError.throwSqlException(401);
/*     */         }
/*     */ 
/* 191 */         continue;
/*     */       }
/*     */       catch (BreakNetException localBreakNetException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   byte[] getVersion()
/*     */   {
/* 206 */     return this.rdbmsVersion;
/*     */   }
/*     */ 
/*     */   short getVersionNumber()
/*     */   {
/* 221 */     int i = 0;
/*     */ 
/* 223 */     i = (int)(i + (this.retVerNum >>> 24 & 0xFF) * 1000L);
/* 224 */     i = (int)(i + (this.retVerNum >>> 20 & 0xF) * 100L);
/* 225 */     i = (int)(i + (this.retVerNum >>> 12 & 0xF) * 10L);
/* 226 */     i = (int)(i + (this.retVerNum >>> 8 & 0xF));
/*     */ 
/* 231 */     return (short)i;
/*     */   }
/*     */ 
/*     */   long getVersionNumberasIs()
/*     */   {
/* 242 */     return this.retVerNum;
/*     */   }
/*     */ 
/*     */   void marshal()
/*     */     throws IOException
/*     */   {
/* 257 */     marshalFunHeader();
/*     */ 
/* 260 */     this.meg.marshalO2U(this.rdbmsVersionO2U);
/* 261 */     this.meg.marshalSWORD(this.bufLen);
/* 262 */     this.meg.marshalO2U(this.retVerLenO2U);
/* 263 */     this.meg.marshalO2U(this.retVerNumO2U);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C7Oversion
 * JD-Core Version:    0.6.0
 */