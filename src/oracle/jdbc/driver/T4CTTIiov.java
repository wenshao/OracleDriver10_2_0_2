/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ 
/*     */ class T4CTTIiov extends T4CTTIMsg
/*     */ {
/*     */   T4C8TTIrxh rxh;
/*     */   T4CTTIrxd rxd;
/*  41 */   byte bindtype = 0;
/*     */   byte[] iovector;
/*  44 */   int bindcnt = 0;
/*  45 */   int inbinds = 0;
/*  46 */   int outbinds = 0;
/*     */   static final byte BV_IN_V = 32;
/*     */   static final byte BV_OUT_V = 16;
/* 273 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIiov(T4CMAREngine paramT4CMAREngine, T4C8TTIrxh paramT4C8TTIrxh, T4CTTIrxd paramT4CTTIrxd)
/*     */     throws SQLException, IOException
/*     */   {
/*  64 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  66 */     this.rxh = paramT4C8TTIrxh;
/*  67 */     this.rxd = paramT4CTTIrxd;
/*     */   }
/*     */ 
/*     */   void init()
/*     */     throws SQLException, IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   Accessor[] processRXD(Accessor[] paramArrayOfAccessor, int paramInt1, byte[] paramArrayOfByte1, char[] paramArrayOfChar1, short[] paramArrayOfShort1, int paramInt2, DBConversion paramDBConversion, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, InputStream[][] paramArrayOfInputStream, byte[][][] paramArrayOfByte, OracleTypeADT[][] paramArrayOfOracleTypeADT, OracleStatement paramOracleStatement, byte[] paramArrayOfByte4, char[] paramArrayOfChar2, short[] paramArrayOfShort2)
/*     */     throws SQLException, IOException
/*     */   {
/* 104 */     if (paramArrayOfByte3 != null)
/*     */     {
/* 106 */       for (int i = 0; i < paramArrayOfByte3.length; i++)
/*     */       {
/* 108 */         if (((paramArrayOfByte3[i] & 0x10) != 0) && ((paramArrayOfAccessor == null) || (paramArrayOfAccessor.length <= i) || (paramArrayOfAccessor[i] == null)))
/*     */         {
/* 117 */           int j = paramInt2 + 3 + 10 * i;
/*     */ 
/* 122 */           int k = paramArrayOfShort1[(j + 0)] & 0xFFFF;
/*     */ 
/* 125 */           int m = k;
/*     */ 
/* 127 */           if (k == 9) {
/* 128 */             k = 1;
/*     */           }
/* 130 */           Accessor localAccessor = paramOracleStatement.allocateAccessor(k, k, i, 0, 0, null, false);
/*     */ 
/* 139 */           localAccessor.rowSpaceIndicator = null;
/*     */ 
/* 141 */           if (paramArrayOfAccessor == null)
/*     */           {
/* 143 */             paramArrayOfAccessor = new Accessor[i + 1];
/* 144 */             paramArrayOfAccessor[i] = localAccessor;
/*     */           }
/* 146 */           else if (paramArrayOfAccessor.length <= i)
/*     */           {
/* 148 */             Accessor[] arrayOfAccessor = new Accessor[i + 1];
/*     */ 
/* 150 */             arrayOfAccessor[i] = localAccessor;
/*     */ 
/* 152 */             for (int n = 0; n < paramArrayOfAccessor.length; n++)
/*     */             {
/* 154 */               if (paramArrayOfAccessor[n] != null) {
/* 155 */                 arrayOfAccessor[n] = paramArrayOfAccessor[n];
/*     */               }
/*     */             }
/* 158 */             paramArrayOfAccessor = arrayOfAccessor;
/*     */           }
/*     */           else
/*     */           {
/* 164 */             paramArrayOfAccessor[i] = localAccessor;
/*     */           }
/*     */         } else {
/* 167 */           if (((paramArrayOfByte3[i] & 0x10) != 0) || (paramArrayOfAccessor == null) || (i >= paramArrayOfAccessor.length) || (paramArrayOfAccessor[i] == null))
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/* 173 */           paramArrayOfAccessor[i].isUseLess = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 180 */     return paramArrayOfAccessor;
/*     */   }
/*     */ 
/*     */   void unmarshalV10()
/*     */     throws IOException, SQLException
/*     */   {
/* 190 */     this.rxh.unmarshalV10(this.rxd);
/*     */ 
/* 192 */     this.bindcnt = this.rxh.numRqsts;
/*     */ 
/* 210 */     this.iovector = new byte[this.bindcnt];
/*     */ 
/* 212 */     for (int i = 0; i < this.bindcnt; i++)
/*     */     {
/* 216 */       if ((this.bindtype = this.meg.unmarshalSB1()) == 0)
/*     */       {
/* 221 */         DatabaseError.throwSqlException(401);
/*     */       }
/*     */ 
/* 227 */       if ((this.bindtype & 0x20) > 0)
/*     */       {
/*     */         int tmp78_77 = i;
/*     */         byte[] tmp78_74 = this.iovector; tmp78_74[tmp78_77] = (byte)(tmp78_74[tmp78_77] | 0x20);
/* 230 */         this.inbinds += 1;
/*     */       }
/*     */ 
/* 233 */       if ((this.bindtype & 0x10) <= 0)
/*     */         continue;
/*     */       int tmp110_109 = i;
/*     */       byte[] tmp110_106 = this.iovector; tmp110_106[tmp110_109] = (byte)(tmp110_106[tmp110_109] | 0x10);
/* 236 */       this.outbinds += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   byte[] getIOVector()
/*     */   {
/* 256 */     return this.iovector;
/*     */   }
/*     */ 
/*     */   boolean isIOVectorEmpty()
/*     */   {
/* 268 */     return this.iovector.length == 0;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIiov
 * JD-Core Version:    0.6.0
 */