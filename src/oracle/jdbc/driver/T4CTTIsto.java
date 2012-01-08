/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4CTTIsto extends T4CTTIfun
/*     */ {
/*     */   static final int OV6STRT = 48;
/*     */   static final int OV6STOP = 49;
/*     */   static final int STOMFDBA = 1;
/*     */   static final int STOMFACA = 2;
/*     */   static final int STOMFALO = 4;
/*     */   static final int STOMFSHU = 8;
/*     */   static final int STOMFFRC = 16;
/*     */   static final int STOMFPOL = 32;
/*     */   static final int STOMFABO = 64;
/*     */   static final int STOMFATX = 128;
/*     */   static final int STOMFLTX = 256;
/*     */   static final int STOSDONE = 1;
/*     */   static final int STOSINPR = 2;
/*     */   static final int STOSERR = 3;
/*     */   T4CTTIoer oer;
/* 177 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIsto(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */     throws IOException, SQLException
/*     */   {
/*  92 */     super(3, 0, 0);
/*     */ 
/*  94 */     this.oer = paramT4CTTIoer;
/*     */ 
/*  96 */     setMarshalingEngine(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void marshal(int paramInt) throws IOException, SQLException
/*     */   {
/* 101 */     if ((paramInt == 1) || (paramInt == 16))
/* 102 */       this.funCode = 48;
/* 103 */     else if ((paramInt == 2) || (paramInt == 4) || (paramInt == 8) || (paramInt == 32) || (paramInt == 64) || (paramInt == 128) || (paramInt == 256))
/*     */     {
/* 107 */       this.funCode = 49;
/*     */     }
/* 109 */     else throw new SQLException("Invalid mode.");
/*     */ 
/* 111 */     super.marshalFunHeader();
/* 112 */     this.meg.marshalSWORD(paramInt);
/* 113 */     this.meg.marshalPTR();
/*     */   }
/*     */ 
/*     */   int receive()
/*     */     throws SQLException, IOException
/*     */   {
/* 125 */     int j = 0;
/*     */     try
/*     */     {
/* 129 */       i = this.meg.unmarshalSB1();
/*     */ 
/* 131 */       if (i == 8)
/*     */       {
/* 133 */         j = (int)this.meg.unmarshalUB4();
/*     */ 
/* 135 */         if (j == 3) {
/* 136 */           throw new SQLException("An error occurred during startup/shutdown");
/*     */         }
/* 138 */         int k = this.meg.unmarshalUB1();
/*     */       }
/* 140 */       else if (i == 4)
/*     */       {
/* 142 */         this.oer.init();
/* 143 */         this.oer.unmarshal();
/*     */         try
/*     */         {
/* 147 */           this.oer.processError();
/*     */         }
/*     */         catch (SQLException localSQLException)
/*     */         {
/* 151 */           throw localSQLException;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 156 */         DatabaseError.throwSqlException(401);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (BreakNetException localBreakNetException)
/*     */     {
/* 163 */       int i = this.meg.unmarshalSB1();
/* 164 */       if (i == 4)
/*     */       {
/* 166 */         this.oer.init();
/* 167 */         this.oer.unmarshal();
/* 168 */         this.oer.processError();
/*     */       }
/*     */     }
/*     */ 
/* 172 */     return j;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIsto
 * JD-Core Version:    0.6.0
 */