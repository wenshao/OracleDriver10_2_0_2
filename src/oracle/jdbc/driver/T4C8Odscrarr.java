/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4C8Odscrarr extends T4CTTIfun
/*     */ {
/*  42 */   byte operationflags = 7;
/*  43 */   byte[] sqltext = new byte[0];
/*  44 */   long sqlparseversion = 2L;
/*     */   T4CTTIdcb dcb;
/*  47 */   int cursor_id = 0;
/*     */ 
/*  50 */   int numuds = 0;
/*     */ 
/*  53 */   boolean numitemsO2U = true;
/*     */ 
/*  56 */   boolean udsarrayO2U = true;
/*  57 */   boolean numudsO2U = true;
/*  58 */   boolean colnameO2U = true;
/*  59 */   boolean lencolsO2U = true;
/*     */ 
/*  61 */   OracleStatement statement = null;
/*     */ 
/* 247 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   T4C8Odscrarr(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */     throws IOException, SQLException
/*     */   {
/*  72 */     super(3, 0, 43);
/*     */ 
/*  74 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  76 */     this.oer = paramT4CTTIoer;
/*  77 */     this.funCode = 98;
/*  78 */     this.dcb = new T4CTTIdcb(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void init(OracleStatement paramOracleStatement, int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  87 */     this.numuds = 0;
/*     */ 
/*  90 */     this.numitemsO2U = true;
/*  91 */     this.udsarrayO2U = true;
/*  92 */     this.numudsO2U = true;
/*  93 */     this.colnameO2U = true;
/*  94 */     this.lencolsO2U = true;
/*     */ 
/*  96 */     this.oer.init();
/*     */ 
/*  98 */     this.cursor_id = paramOracleStatement.cursorId;
/*  99 */     this.statement = paramOracleStatement;
/*     */ 
/* 101 */     this.operationflags = 7;
/* 102 */     this.sqltext = new byte[0];
/* 103 */     this.sqlparseversion = 2L;
/*     */ 
/* 105 */     this.dcb.init(paramOracleStatement, paramInt);
/*     */   }
/*     */ 
/*     */   void marshal()
/*     */     throws IOException
/*     */   {
/* 130 */     marshalFunHeader();
/*     */ 
/* 134 */     this.meg.marshalUB1((short)this.operationflags);
/* 135 */     this.meg.marshalSWORD(this.cursor_id);
/*     */ 
/* 138 */     if (this.sqltext.length == 0)
/* 139 */       this.meg.marshalNULLPTR();
/*     */     else {
/* 141 */       this.meg.marshalPTR();
/*     */     }
/* 143 */     this.meg.marshalSB4(this.sqltext.length);
/*     */ 
/* 145 */     this.meg.marshalUB4(this.sqlparseversion);
/*     */ 
/* 147 */     this.meg.marshalO2U(this.udsarrayO2U);
/* 148 */     this.meg.marshalO2U(this.numudsO2U);
/*     */ 
/* 152 */     this.meg.marshalCHR(this.sqltext);
/*     */   }
/*     */ 
/*     */   Accessor[] receive(Accessor[] paramArrayOfAccessor)
/*     */     throws SQLException, IOException
/*     */   {
/* 193 */     int i = 0;
/*     */ 
/* 196 */     while (i == 0)
/*     */     {
/*     */       try
/*     */       {
/* 200 */         int j = this.meg.unmarshalSB1();
/*     */ 
/* 202 */         switch (j)
/*     */         {
/*     */         case 8:
/* 206 */           paramArrayOfAccessor = this.dcb.receiveCommon(paramArrayOfAccessor, true);
/* 207 */           this.numuds = this.dcb.numuds;
/*     */ 
/* 209 */           break;
/*     */         case 4:
/* 212 */           this.oer.init();
/* 213 */           this.oer.unmarshal();
/* 214 */           this.oer.processError();
/*     */ 
/* 216 */           i = 1;
/*     */ 
/* 218 */           break;
/*     */         case 9:
/* 221 */           i = 1;
/*     */ 
/* 224 */           break;
/*     */         default:
/* 231 */           DatabaseError.throwSqlException(401);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (BreakNetException localBreakNetException)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 242 */     return paramArrayOfAccessor;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8Odscrarr
 * JD-Core Version:    0.6.0
 */