/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4C8TTIrxh extends T4CTTIMsg
/*     */ {
/*     */   short flags;
/*     */   int numRqsts;
/*     */   int iterNum;
/*     */   int numItersThisTime;
/*     */   int uacBufLength;
/*     */   static final byte FU2O = 1;
/*     */   static final byte FEOR = 2;
/*     */   static final byte PLSV = 4;
/* 180 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4C8TTIrxh(T4CMAREngine paramT4CMAREngine)
/*     */   {
/*  93 */     setMarshalingEngine(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void unmarshalV10(T4CTTIrxd paramT4CTTIrxd)
/*     */     throws SQLException, IOException
/*     */   {
/* 103 */     this.flags = this.meg.unmarshalUB1();
/* 104 */     this.numRqsts = this.meg.unmarshalUB2();
/* 105 */     this.iterNum = this.meg.unmarshalUB2();
/*     */ 
/* 109 */     this.numRqsts += this.iterNum * 256;
/*     */ 
/* 111 */     this.numItersThisTime = this.meg.unmarshalUB2();
/* 112 */     this.uacBufLength = this.meg.unmarshalUB2();
/*     */ 
/* 115 */     byte[] arrayOfByte1 = this.meg.unmarshalDALC();
/* 116 */     paramT4CTTIrxd.readBitVector(arrayOfByte1);
/*     */ 
/* 119 */     byte[] arrayOfByte2 = this.meg.unmarshalDALC();
/*     */   }
/*     */ 
/*     */   void init()
/*     */   {
/* 125 */     this.flags = 0;
/* 126 */     this.numRqsts = 0;
/* 127 */     this.iterNum = 0;
/* 128 */     this.numItersThisTime = 0;
/* 129 */     this.uacBufLength = 0;
/*     */   }
/*     */ 
/*     */   void print(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTIrxh
 * JD-Core Version:    0.6.0
 */