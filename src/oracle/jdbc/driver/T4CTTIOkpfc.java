/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4CTTIOkpfc extends T4CTTIfun
/*     */ {
/*     */   static final int KPFCI_CONNECT = 1;
/*     */   static final int KPFCI_REVERT = 2;
/*     */   static final int KPFCI_TEST = 4;
/*     */   static final int KPFCI_STRM = 8;
/*     */   static final int KPFCI_PING = 16;
/*     */   static final int KPFCI_TCPCONNECT = 32;
/*     */   static final int KPFCI_TCPREVERT = 64;
/*     */   static final int KPFCV_VENDOR = 0;
/*     */   static final int KPFCV_PROTO = 1;
/*     */   static final int KPFCV_MAJOR = 2;
/*     */   static final int KPFCV_MINOR = 3;
/*     */   static final int KPFCO_CONNECT = 1;
/*     */   static final int KPFCO_REVERT = 2;
/*     */   static final int KPFCO_TCPCONNECT = 4;
/*     */   static final int KPFCO_TCPREVERT = 8;
/*     */ 
/*     */   T4CTTIOkpfc(T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  80 */     super(3, 0, 139);
/*     */ 
/*  82 */     setMarshalingEngine(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void marshal(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  88 */     if ((paramInt != 1) && (paramInt != 2) && (paramInt != 4) && (paramInt != 8) && (paramInt != 16) && (paramInt != 32) && (paramInt != 64))
/*     */     {
/*  95 */       throw new SQLException("Invalid operation.");
/*     */     }
/*  97 */     super.marshalFunHeader();
/*  98 */     this.meg.marshalUB4(paramInt);
/*  99 */     this.meg.marshalNULLPTR();
/* 100 */     this.meg.marshalUB4(0L);
/* 101 */     this.meg.marshalUB4(0L);
/* 102 */     this.meg.marshalUB4(0L);
/* 103 */     this.meg.marshalNULLPTR();
/* 104 */     this.meg.marshalUB4(0L);
/* 105 */     this.meg.marshalNULLPTR();
/* 106 */     this.meg.marshalUB4(0L);
/* 107 */     this.meg.marshalNULLPTR();
/* 108 */     this.meg.marshalPTR();
/*     */   }
/*     */ 
/*     */   void receive()
/*     */     throws SQLException, IOException
/*     */   {
/* 114 */     byte[] arrayOfByte = null;
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 120 */         int i = this.meg.unmarshalSB1();
/*     */ 
/* 122 */         switch (i)
/*     */         {
/*     */         case 8:
/* 128 */           int j = (int)this.meg.unmarshalUB4();
/*     */ 
/* 130 */           arrayOfByte = new byte[j];
/*     */ 
/* 132 */           int k = 0; if (k >= j) continue;
/* 133 */           arrayOfByte[k] = (byte)(int)this.meg.unmarshalUB4();
/*     */ 
/* 132 */           k++; break;
/*     */         case 4:
/* 138 */           this.oer.init();
/* 139 */           this.oer.unmarshal();
/*     */ 
/* 141 */           break;
/*     */         default:
/* 146 */           DatabaseError.throwSqlException(401);
/*     */         }
/*     */ 
/* 152 */         continue;
/*     */       }
/*     */       catch (BreakNetException localBreakNetException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIOkpfc
 * JD-Core Version:    0.6.0
 */