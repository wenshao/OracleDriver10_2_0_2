/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ 
/*     */ class T4CTTIoac
/*     */ {
/*     */   static final short UACFIND = 1;
/*     */   static final short UACFALN = 2;
/*     */   static final short UACFRCP = 4;
/*     */   static final short UACFBBV = 8;
/*     */   static final short UACFNCP = 16;
/*     */   static final short UACFBLP = 32;
/*     */   static final short UACFARR = 64;
/*     */   static final short UACFIGN = 128;
/*     */   static final short UACFNSCL = 1;
/*     */   static final short UACFBUC = 2;
/*     */   static final short UACFSKP = 4;
/*     */   static final short UACFCHRCNT = 8;
/*     */   static final short UACFNOADJ = 16;
/*     */   static final short UACFCUS = 4096;
/*  53 */   static final byte[] NO_BYTES = new byte[0];
/*     */   boolean isStream;
/*     */   int ncs;
/*     */   short formOfUse;
/*     */   static int maxBindArrayLength;
/*     */   T4CMAREngine meg;
/*     */   short oacdty;
/*     */   short oacflg;
/*     */   short oacpre;
/*     */   short oacscl;
/*     */   int oacmxl;
/*  74 */   int oacmxlc = 0;
/*     */   int oacmal;
/*     */   int oacfl2;
/*     */   byte[] oactoid;
/*     */   int oactoidl;
/*     */   int oacvsn;
/* 518 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIoac(T4CMAREngine paramT4CMAREngine)
/*     */   {
/*  86 */     this.meg = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CTTIoac(T4CTTIoac paramT4CTTIoac)
/*     */   {
/*  92 */     this.meg = paramT4CTTIoac.meg;
/*  93 */     this.isStream = paramT4CTTIoac.isStream;
/*  94 */     this.ncs = paramT4CTTIoac.ncs;
/*  95 */     this.formOfUse = paramT4CTTIoac.formOfUse;
/*  96 */     this.oacdty = paramT4CTTIoac.oacdty;
/*  97 */     this.oacflg = paramT4CTTIoac.oacflg;
/*  98 */     this.oacpre = paramT4CTTIoac.oacpre;
/*  99 */     this.oacscl = paramT4CTTIoac.oacscl;
/* 100 */     this.oacmxl = paramT4CTTIoac.oacmxl;
/* 101 */     this.oacmal = paramT4CTTIoac.oacmal;
/* 102 */     this.oacfl2 = paramT4CTTIoac.oacfl2;
/* 103 */     this.oactoid = paramT4CTTIoac.oactoid;
/* 104 */     this.oactoidl = paramT4CTTIoac.oactoidl;
/* 105 */     this.oacvsn = paramT4CTTIoac.oacvsn;
/*     */   }
/*     */ 
/*     */   boolean isOldSufficient(T4CTTIoac paramT4CTTIoac)
/*     */   {
/* 111 */     int i = 0;
/*     */ 
/* 113 */     if ((this.oactoidl != 0) || (paramT4CTTIoac.oactoidl != 0)) {
/* 114 */       return false;
/*     */     }
/* 116 */     if ((this.isStream == paramT4CTTIoac.isStream) && (this.ncs == paramT4CTTIoac.ncs) && (this.formOfUse == paramT4CTTIoac.formOfUse) && (this.oacdty == paramT4CTTIoac.oacdty) && (this.oacflg == paramT4CTTIoac.oacflg) && (this.oacpre == paramT4CTTIoac.oacpre) && (this.oacscl == paramT4CTTIoac.oacscl) && ((this.oacmxl == paramT4CTTIoac.oacmxl) || ((paramT4CTTIoac.oacmxl > this.oacmxl) && (paramT4CTTIoac.oacmxl < 4000))) && (this.oacmxlc == paramT4CTTIoac.oacmxlc) && (this.oacmal == paramT4CTTIoac.oacmal) && (this.oacfl2 == paramT4CTTIoac.oacfl2) && (this.oacvsn == paramT4CTTIoac.oacvsn))
/*     */     {
/* 123 */       i = 1;
/*     */     }
/* 125 */     return i;
/*     */   }
/*     */ 
/*     */   boolean isNType()
/*     */   {
/* 138 */     return this.formOfUse == 2;
/*     */   }
/*     */ 
/*     */   void unmarshal()
/*     */     throws IOException, SQLException
/*     */   {
/* 153 */     this.oacdty = this.meg.unmarshalUB1();
/* 154 */     this.oacflg = this.meg.unmarshalUB1();
/* 155 */     this.oacpre = this.meg.unmarshalUB1();
/*     */ 
/* 169 */     if ((this.oacdty == 2) || (this.oacdty == 180) || (this.oacdty == 181) || (this.oacdty == 231) || (this.oacdty == 183))
/*     */     {
/* 174 */       this.oacscl = (short)this.meg.unmarshalUB2();
/*     */     }
/* 176 */     else this.oacscl = this.meg.unmarshalUB1();
/*     */ 
/* 178 */     this.oacmxl = this.meg.unmarshalSB4();
/* 179 */     this.oacmal = this.meg.unmarshalSB4();
/* 180 */     this.oacfl2 = this.meg.unmarshalSB4();
/*     */ 
/* 187 */     if (this.oacmxl > 0)
/*     */     {
/* 189 */       switch (this.oacdty)
/*     */       {
/*     */       case 2:
/* 198 */         this.oacmxl = 22;
/*     */ 
/* 200 */         break;
/*     */       case 12:
/* 203 */         this.oacmxl = 7;
/*     */ 
/* 205 */         break;
/*     */       case 181:
/* 208 */         this.oacmxl = 13;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 214 */     if (this.oacdty == 11) {
/* 215 */       this.oacdty = 104;
/*     */     }
/* 217 */     this.oactoid = this.meg.unmarshalDALC();
/* 218 */     this.oactoidl = (this.oactoid == null ? 0 : this.oactoid.length);
/* 219 */     this.oacvsn = this.meg.unmarshalUB2();
/* 220 */     this.ncs = this.meg.unmarshalUB2();
/* 221 */     this.formOfUse = this.meg.unmarshalUB1();
/*     */ 
/* 223 */     if (this.meg.versionNumber >= 9000)
/*     */     {
/* 227 */       this.oacmxlc = (int)this.meg.unmarshalUB4();
/*     */     }
/*     */   }
/*     */ 
/*     */   void init(NamedTypeAccessor paramNamedTypeAccessor)
/*     */   {
/* 247 */     this.oacdty = (short)paramNamedTypeAccessor.internalType;
/* 248 */     this.oacflg = 3;
/* 249 */     this.oacpre = 0;
/* 250 */     this.oacscl = 0;
/* 251 */     this.oacmxl = paramNamedTypeAccessor.internalTypeMaxLength;
/* 252 */     this.oacmal = 0;
/* 253 */     this.oacfl2 = 0;
/* 254 */     this.isStream = paramNamedTypeAccessor.isStream;
/*     */ 
/* 260 */     OracleTypeADT localOracleTypeADT = (OracleTypeADT)paramNamedTypeAccessor.internalOtype;
/*     */ 
/* 262 */     if (localOracleTypeADT != null)
/*     */     {
/* 264 */       this.oactoid = localOracleTypeADT.getTOID();
/* 265 */       this.oacvsn = localOracleTypeADT.getTypeVersion();
/* 266 */       this.ncs = localOracleTypeADT.getCharSet();
/* 267 */       this.formOfUse = (short)localOracleTypeADT.getCharSetForm();
/*     */     }
/*     */     else
/*     */     {
/* 271 */       this.oactoid = NO_BYTES;
/* 272 */       this.oactoidl = this.oactoid.length;
/* 273 */       this.oacvsn = 0;
/* 274 */       this.formOfUse = paramNamedTypeAccessor.formOfUse;
/*     */ 
/* 277 */       if (isNType())
/* 278 */         this.ncs = this.meg.conv.getNCharSetId();
/*     */       else {
/* 280 */         this.ncs = this.meg.conv.getServerCharSetId();
/*     */       }
/*     */     }
/* 283 */     if (this.oacdty == 102)
/* 284 */       this.oacmxl = 1;
/*     */   }
/*     */ 
/*     */   void init(PlsqlIndexTableAccessor paramPlsqlIndexTableAccessor)
/*     */   {
/* 295 */     initIbt((short)paramPlsqlIndexTableAccessor.elementInternalType, paramPlsqlIndexTableAccessor.maxNumberOfElements, paramPlsqlIndexTableAccessor.elementMaxLen);
/*     */   }
/*     */ 
/*     */   void initIbt(short paramShort, int paramInt1, int paramInt2)
/*     */   {
/* 305 */     this.oacflg = 67;
/* 306 */     this.oacpre = 0;
/* 307 */     this.oacscl = 0;
/* 308 */     this.oacmal = paramInt1;
/* 309 */     this.oacfl2 = 0;
/* 310 */     this.oacmxl = paramInt2;
/* 311 */     this.oactoid = null;
/* 312 */     this.oacvsn = 0;
/* 313 */     this.ncs = 0;
/* 314 */     this.formOfUse = 0;
/*     */ 
/* 316 */     if ((paramShort == 9) || (paramShort == 96) || (paramShort == 1))
/*     */     {
/* 319 */       if (this.oacdty != 96) {
/* 320 */         this.oacdty = 1;
/*     */       }
/* 322 */       this.oacfl2 = 16;
/*     */     }
/*     */     else {
/* 325 */       this.oacdty = 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   void init(OracleTypeADT paramOracleTypeADT, int paramInt1, int paramInt2)
/*     */   {
/* 345 */     this.oacdty = (short)paramInt1;
/* 346 */     this.oacflg = 3;
/* 347 */     this.oacpre = 0;
/* 348 */     this.oacscl = 0;
/* 349 */     this.oacmxl = paramInt2;
/* 350 */     this.oacmal = 0;
/* 351 */     this.oacfl2 = 0;
/* 352 */     this.isStream = false;
/*     */ 
/* 354 */     this.oactoid = paramOracleTypeADT.getTOID();
/* 355 */     this.oacvsn = paramOracleTypeADT.getTypeVersion();
/* 356 */     this.ncs = 2;
/* 357 */     this.formOfUse = (short)paramOracleTypeADT.getCharSetForm();
/*     */ 
/* 359 */     if (this.oacdty == 102)
/* 360 */       this.oacmxl = 1;
/*     */   }
/*     */ 
/*     */   void init(short paramShort1, int paramInt, short paramShort2, short paramShort3, short paramShort4)
/*     */     throws IOException, SQLException
/*     */   {
/* 382 */     this.oacflg = 3;
/* 383 */     this.oacpre = 0;
/* 384 */     this.oacscl = 0;
/* 385 */     this.oacmal = 0;
/* 386 */     this.oacfl2 = 0;
/* 387 */     this.oacdty = paramShort1;
/* 388 */     this.oacmxl = paramInt;
/*     */ 
/* 390 */     if ((this.oacdty == 96) || (this.oacdty == 9) || (this.oacdty == 1))
/*     */     {
/* 393 */       if (this.oacdty != 96) {
/* 394 */         this.oacdty = 1;
/*     */       }
/* 396 */       this.oacfl2 = 16;
/*     */     }
/* 398 */     else if (this.oacdty == 104)
/*     */     {
/* 400 */       this.oacdty = 11;
/*     */     }
/* 402 */     else if (this.oacdty == 102) {
/* 403 */       this.oacmxl = 1;
/*     */     }
/* 405 */     this.oactoid = NO_BYTES;
/* 406 */     this.oactoidl = 0;
/* 407 */     this.oacvsn = 0;
/*     */ 
/* 409 */     this.formOfUse = paramShort4;
/*     */ 
/* 412 */     this.ncs = paramShort2;
/*     */ 
/* 414 */     if (isNType())
/* 415 */       this.ncs = paramShort3;
/*     */   }
/*     */ 
/*     */   void marshal()
/*     */     throws IOException, SQLException
/*     */   {
/* 430 */     this.meg.marshalUB1(this.oacdty);
/* 431 */     this.meg.marshalUB1(this.oacflg);
/* 432 */     this.meg.marshalUB1(this.oacpre);
/* 433 */     this.meg.marshalUB1(this.oacscl);
/*     */ 
/* 435 */     this.meg.marshalUB4(this.oacmxl);
/*     */ 
/* 437 */     this.meg.marshalSB4(this.oacmal);
/* 438 */     this.meg.marshalSB4(this.oacfl2);
/*     */ 
/* 440 */     this.meg.marshalDALC(this.oactoid);
/*     */ 
/* 442 */     this.meg.marshalUB2(this.oacvsn);
/* 443 */     this.meg.marshalUB2(this.ncs);
/* 444 */     this.meg.marshalUB1(this.formOfUse);
/*     */ 
/* 446 */     if (this.meg.versionNumber >= 9000)
/*     */     {
/* 450 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean isStream()
/*     */     throws SQLException
/*     */   {
/* 462 */     return this.isStream;
/*     */   }
/*     */ 
/*     */   void print(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIoac
 * JD-Core Version:    0.6.0
 */