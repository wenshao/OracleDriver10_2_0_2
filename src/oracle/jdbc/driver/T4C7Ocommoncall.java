/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4C7Ocommoncall extends T4CTTIfun
/*     */ {
/*     */   T4CConnection connection;
/* 165 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   T4C7Ocommoncall(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer, T4CConnection paramT4CConnection)
/*     */     throws IOException, SQLException
/*     */   {
/*  57 */     super(3, 0);
/*     */ 
/*  59 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  61 */     this.oer = paramT4CTTIoer;
/*  62 */     this.connection = paramT4CConnection;
/*     */   }
/*     */ 
/*     */   void init(short paramShort)
/*     */     throws IOException, SQLException
/*     */   {
/*  71 */     this.oer.init();
/*     */ 
/*  73 */     this.funCode = paramShort;
/*     */   }
/*     */ 
/*     */   void receive()
/*     */     throws SQLException, IOException
/*     */   {
/*  96 */     int i = 0;
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 106 */         int j = this.meg.unmarshalSB1();
/*     */ 
/* 112 */         switch (j)
/*     */         {
/*     */         case 9:
/* 119 */           if (this.meg.versionNumber < 10000)
/*     */             continue;
/* 121 */           short s = (short)this.meg.unmarshalUB2();
/*     */ 
/* 123 */           this.connection.endToEndECIDSequenceNumber = s;
/*     */ 
/* 126 */           break;
/*     */         case 4:
/* 133 */           this.oer.init();
/* 134 */           this.oer.unmarshal();
/*     */ 
/* 140 */           if (this.oer.retCode != 2089) {
/* 141 */             this.oer.processError();
/*     */           }
/* 143 */           break;
/*     */         default:
/* 150 */           DatabaseError.throwSqlException(401);
/*     */ 
/* 156 */           continue;
/*     */         }
/*     */       }
/*     */       catch (BreakNetException localBreakNetException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C7Ocommoncall
 * JD-Core Version:    0.6.0
 */