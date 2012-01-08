/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4CLongRawAccessor extends LongRawAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  26 */   byte[][] data = (byte[][])null;
/*  27 */   int[] nbBytesRead = null;
/*  28 */   int[] bytesReadSoFar = null;
/*     */ 
/* 114 */   final int[] escapeSequenceArr = new int[1];
/* 115 */   final boolean[] readHeaderArr = new boolean[1];
/* 116 */   final boolean[] readAsNonStreamArr = new boolean[1];
/*     */ 
/* 428 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CLongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  34 */     super(paramOracleStatement, paramInt1, paramInt2, paramShort, paramInt3);
/*     */ 
/*  36 */     this.mare = paramT4CMAREngine;
/*     */ 
/*  38 */     if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
/*     */     {
/*  40 */       this.data = new byte[paramOracleStatement.rowPrefetch][];
/*     */ 
/*  42 */       for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
/*  43 */         this.data[i] = new byte[4080];
/*     */       }
/*  45 */       this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
/*  46 */       this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
/*     */     }
/*     */   }
/*     */ 
/*     */   T4CLongRawAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  59 */     super(paramOracleStatement, paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort);
/*     */ 
/*  62 */     this.mare = paramT4CMAREngine;
/*  63 */     this.definedColumnType = paramInt8;
/*  64 */     this.definedColumnSize = paramInt9;
/*     */ 
/*  66 */     if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
/*     */     {
/*  68 */       this.data = new byte[paramOracleStatement.rowPrefetch][];
/*     */ 
/*  70 */       for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
/*  71 */         this.data[i] = new byte[4080];
/*     */       }
/*  73 */       this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
/*  74 */       this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
/*     */     }
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  85 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  92 */       this.mare.unmarshalUB2();
/*  93 */       this.mare.unmarshalUB2();
/*     */     }
/*  95 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  99 */       this.mare.unmarshalSB2();
/*     */ 
/* 101 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/* 103 */         this.mare.unmarshalSB2();
/*     */       }
/* 105 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/* 108 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 133 */     if (this.isUseLess)
/*     */     {
/* 135 */       this.lastRowProcessed += 1;
/*     */ 
/* 137 */       return false;
/*     */     }
/*     */ 
/* 140 */     int i = 0;
/* 141 */     int j = this.indicatorIndex + this.lastRowProcessed;
/*     */ 
/* 147 */     this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
/*     */     int k;
/* 150 */     if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
/*     */     {
/* 154 */       this.rowSpaceIndicator[j] = -1;
/*     */ 
/* 156 */       this.mare.processIndicator(false, 0);
/*     */ 
/* 158 */       k = (int)this.mare.unmarshalUB4();
/*     */ 
/* 160 */       i = 0;
/* 161 */       this.escapeSequenceArr[0] = 0;
/* 162 */       this.lastRowProcessed += 1;
/*     */     }
/*     */     else
/*     */     {
/* 168 */       this.rowSpaceIndicator[j] = 0;
/* 169 */       this.readHeaderArr[0] = true;
/* 170 */       this.readAsNonStreamArr[0] = false;
/*     */ 
/* 173 */       if (this.statement.connection.useFetchSizeWithLongColumn)
/*     */       {
/* 178 */         k = 0;
/*     */ 
/* 180 */         while (k != -1)
/*     */         {
/* 184 */           if (this.data[this.lastRowProcessed].length < this.nbBytesRead[this.lastRowProcessed] + 255)
/*     */           {
/* 187 */             byte[] arrayOfByte = new byte[this.data[this.lastRowProcessed].length * 4];
/*     */ 
/* 189 */             System.arraycopy(this.data[this.lastRowProcessed], 0, arrayOfByte, 0, this.nbBytesRead[this.lastRowProcessed]);
/*     */ 
/* 192 */             this.data[this.lastRowProcessed] = arrayOfByte;
/*     */           }
/*     */ 
/* 195 */           k = readStreamFromWire(this.data[this.lastRowProcessed], this.nbBytesRead[this.lastRowProcessed], 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 202 */           if (k == -1)
/*     */             continue;
/* 204 */           this.nbBytesRead[this.lastRowProcessed] += k;
/*     */         }
/*     */ 
/* 208 */         this.lastRowProcessed += 1;
/*     */       }
/*     */       else
/*     */       {
/* 212 */         i = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 220 */     return i;
/*     */   }
/*     */ 
/*     */   void fetchNextColumns()
/*     */     throws SQLException
/*     */   {
/* 235 */     this.statement.continueReadRow(this.columnPosition);
/*     */   }
/*     */ 
/*     */   int readStream(byte[] paramArrayOfByte, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 252 */     int i = this.statement.currentRow;
/*     */ 
/* 254 */     if (this.statement.connection.useFetchSizeWithLongColumn)
/*     */     {
/* 256 */       byte[] arrayOfByte = this.data[i];
/* 257 */       int k = this.nbBytesRead[i];
/* 258 */       int m = this.bytesReadSoFar[i];
/*     */ 
/* 260 */       if (m == k) {
/* 261 */         return -1;
/*     */       }
/*     */ 
/* 264 */       int n = 0;
/*     */ 
/* 266 */       if (paramInt <= k - m)
/* 267 */         n = paramInt;
/*     */       else {
/* 269 */         n = k - m;
/*     */       }
/* 271 */       System.arraycopy(arrayOfByte, m, paramArrayOfByte, 0, n);
/*     */ 
/* 273 */       this.bytesReadSoFar[i] += n;
/*     */ 
/* 275 */       return n;
/*     */     }
/*     */ 
/* 280 */     int j = readStreamFromWire(paramArrayOfByte, 0, paramInt, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 284 */     return j;
/*     */   }
/*     */ 
/*     */   protected static final int readStreamFromWire(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */     throws SQLException, IOException
/*     */   {
/* 305 */     int i = -1;
/*     */     try
/*     */     {
/* 309 */       if (paramArrayOfBoolean2[0] == 0)
/*     */       {
/* 313 */         if ((paramInt2 > 255) || (paramInt2 < 0))
/*     */         {
/* 321 */           DatabaseError.throwSqlException(433);
/*     */         }
/*     */ 
/* 324 */         if (paramArrayOfBoolean1[0] != 0)
/*     */         {
/* 329 */           if (paramArrayOfInt[0] == 254)
/*     */           {
/* 334 */             i = paramT4CMAREngine.unmarshalUB1();
/*     */           }
/*     */           else
/*     */           {
/* 338 */             if (paramArrayOfInt[0] == 0)
/*     */             {
/* 344 */               return 0;
/*     */             }
/*     */ 
/* 349 */             paramArrayOfBoolean2[0] = true;
/* 350 */             i = paramArrayOfInt[0];
/*     */           }
/*     */ 
/* 353 */           paramArrayOfBoolean1[0] = false;
/* 354 */           paramArrayOfInt[0] = 0;
/*     */         }
/*     */         else
/*     */         {
/* 358 */           i = paramT4CMAREngine.unmarshalUB1();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 365 */         paramArrayOfBoolean2[0] = false;
/*     */       }
/*     */ 
/* 369 */       if (i > 0)
/*     */       {
/* 373 */         paramT4CMAREngine.unmarshalNBytes(paramArrayOfByte, paramInt1, i);
/*     */       }
/*     */       else
/*     */       {
/* 377 */         i = -1;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (BreakNetException localBreakNetException)
/*     */     {
/* 388 */       i = paramT4CMAREngine.unmarshalSB1();
/* 389 */       if (i == 4)
/*     */       {
/* 391 */         paramT4CTTIoer.init();
/* 392 */         paramT4CTTIoer.processError();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 397 */     if (i == -1)
/*     */     {
/* 399 */       paramArrayOfBoolean1[0] = true;
/*     */ 
/* 403 */       paramT4CMAREngine.unmarshalUB2();
/* 404 */       paramT4CMAREngine.unmarshalUB2();
/*     */     }
/*     */ 
/* 407 */     return i;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 415 */     String str = super.getString(paramInt);
/*     */ 
/* 419 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize * 2))
/*     */     {
/* 421 */       str = str.substring(0, this.definedColumnSize * 2);
/*     */     }
/* 423 */     return str;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CLongRawAccessor
 * JD-Core Version:    0.6.0
 */