/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CTTIfun extends T4CTTIMsg
/*     */ {
/*     */   static final short OOPEN = 2;
/*     */   static final short OEXEC = 4;
/*     */   static final short OFETCH = 5;
/*     */   static final short OCLOSE = 8;
/*     */   static final short OLOGOFF = 9;
/*     */   static final short OCOMON = 12;
/*     */   static final short OCOMOFF = 13;
/*     */   static final short OCOMMIT = 14;
/*     */   static final short OROLLBACK = 15;
/*     */   static final short OCANCEL = 20;
/*     */   static final short ODSCRARR = 43;
/*     */   static final short OVERSION = 59;
/*     */   static final short OK2RPC = 67;
/*     */   static final short OALL7 = 71;
/*     */   static final short OSQL7 = 74;
/*     */   static final short O3LOGON = 81;
/*     */   static final short O3LOGA = 82;
/*     */   static final short OKOD = 92;
/*     */   static final short OALL8 = 94;
/*     */   static final short OLOBOPS = 96;
/*     */   static final short ODNY = 98;
/*     */   static final short OTXSE = 103;
/*     */   static final short OTXEN = 104;
/*     */   static final short OCCA = 105;
/*     */   static final short O80SES = 107;
/*     */   static final short OAUTH = 115;
/*     */   static final short OSESSKEY = 118;
/*     */   static final short OCANA = 120;
/*     */   static final short OOTCM = 127;
/*     */   static final short OSCID = 135;
/*     */   static final short OKPFC = 139;
/*     */   static final short OKEYVAL = 154;
/*     */   short funCode;
/*     */   byte seqNumber;
/*     */   T4CTTIoer oer;
/* 132 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIfun(byte paramByte, int paramInt)
/*     */   {
/*  83 */     super(paramByte);
/*     */ 
/*  85 */     this.seqNumber = (byte)paramInt;
/*     */   }
/*     */ 
/*     */   T4CTTIfun(byte paramByte, int paramInt, short paramShort)
/*     */   {
/*  90 */     super(paramByte);
/*     */ 
/*  92 */     this.funCode = paramShort;
/*  93 */     this.seqNumber = (byte)paramInt;
/*     */   }
/*     */ 
/*     */   void setFunCode(short paramShort)
/*     */   {
/* 101 */     this.funCode = paramShort;
/*     */   }
/*     */ 
/*     */   void marshalFunHeader()
/*     */     throws IOException
/*     */   {
/* 110 */     marshalTTCcode();
/* 111 */     this.meg.marshalUB1(this.funCode);
/* 112 */     this.meg.marshalUB1((short)this.seqNumber);
/*     */   }
/*     */ 
/*     */   void marshal()
/*     */     throws IOException, SQLException
/*     */   {
/* 124 */     marshalFunHeader();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIfun
 * JD-Core Version:    0.6.0
 */