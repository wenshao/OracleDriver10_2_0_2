/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.net.ns.BreakNetException;
/*     */ 
/*     */ class T4CLongAccessor extends LongAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*     */   static final int t4MaxLength = 2147483647;
/*     */   static final int t4PlsqlMaxLength = 32760;
/*  28 */   byte[][] data = (byte[][])null;
/*  29 */   int[] nbBytesRead = null;
/*  30 */   int[] bytesReadSoFar = null;
/*     */ 
/* 116 */   final int[] escapeSequenceArr = new int[1];
/* 117 */   final boolean[] readHeaderArr = new boolean[1];
/* 118 */   final boolean[] readAsNonStreamArr = new boolean[1];
/*     */ 
/* 435 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CLongAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, short paramShort, int paramInt3, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  36 */     super(paramOracleStatement, paramInt1, paramInt2, paramShort, paramInt3);
/*     */ 
/*  38 */     this.mare = paramT4CMAREngine;
/*     */ 
/*  40 */     if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
/*     */     {
/*  42 */       this.data = new byte[paramOracleStatement.rowPrefetch][];
/*     */ 
/*  44 */       for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
/*  45 */         this.data[i] = new byte[4080];
/*     */       }
/*  47 */       this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
/*  48 */       this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
/*     */     }
/*     */   }
/*     */ 
/*     */   T4CLongAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  61 */     super(paramOracleStatement, paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort);
/*     */ 
/*  64 */     this.mare = paramT4CMAREngine;
/*  65 */     this.definedColumnType = paramInt8;
/*  66 */     this.definedColumnSize = paramInt9;
/*     */ 
/*  68 */     if (paramOracleStatement.connection.useFetchSizeWithLongColumn)
/*     */     {
/*  70 */       this.data = new byte[paramOracleStatement.rowPrefetch][];
/*     */ 
/*  72 */       for (int i = 0; i < paramOracleStatement.rowPrefetch; i++) {
/*  73 */         this.data[i] = new byte[4080];
/*     */       }
/*  75 */       this.nbBytesRead = new int[paramOracleStatement.rowPrefetch];
/*  76 */       this.bytesReadSoFar = new int[paramOracleStatement.rowPrefetch];
/*     */     }
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  87 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  94 */       this.mare.unmarshalUB2();
/*  95 */       this.mare.unmarshalUB2();
/*     */     }
/*  97 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/* 101 */       this.mare.unmarshalSB2();
/*     */ 
/* 103 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/* 105 */         this.mare.unmarshalSB2();
/*     */       }
/* 107 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/* 110 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 135 */     if (this.isUseLess)
/*     */     {
/* 137 */       this.lastRowProcessed += 1;
/*     */ 
/* 139 */       return false;
/*     */     }
/*     */ 
/* 142 */     int i = 0;
/* 143 */     int j = this.indicatorIndex + this.lastRowProcessed;
/*     */ 
/* 149 */     this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
/*     */     int k;
/* 152 */     if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
/*     */     {
/* 156 */       this.rowSpaceIndicator[j] = -1;
/*     */ 
/* 158 */       this.mare.processIndicator(false, 0);
/*     */ 
/* 160 */       k = (int)this.mare.unmarshalUB4();
/*     */ 
/* 162 */       i = 0;
/* 163 */       this.escapeSequenceArr[0] = 0;
/* 164 */       this.lastRowProcessed += 1;
/*     */     }
/*     */     else
/*     */     {
/* 170 */       this.rowSpaceIndicator[j] = 0;
/* 171 */       this.readHeaderArr[0] = true;
/* 172 */       this.readAsNonStreamArr[0] = false;
/*     */ 
/* 175 */       if (this.statement.connection.useFetchSizeWithLongColumn)
/*     */       {
/* 180 */         k = 0;
/*     */ 
/* 182 */         while (k != -1)
/*     */         {
/* 186 */           if (this.data[this.lastRowProcessed].length < this.nbBytesRead[this.lastRowProcessed] + 255)
/*     */           {
/* 189 */             byte[] arrayOfByte = new byte[this.data[this.lastRowProcessed].length * 4];
/*     */ 
/* 191 */             System.arraycopy(this.data[this.lastRowProcessed], 0, arrayOfByte, 0, this.nbBytesRead[this.lastRowProcessed]);
/*     */ 
/* 194 */             this.data[this.lastRowProcessed] = arrayOfByte;
/*     */           }
/*     */ 
/* 197 */           k = readStreamFromWire(this.data[this.lastRowProcessed], this.nbBytesRead[this.lastRowProcessed], 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 204 */           if (k == -1)
/*     */             continue;
/* 206 */           this.nbBytesRead[this.lastRowProcessed] += k;
/*     */         }
/*     */ 
/* 210 */         this.lastRowProcessed += 1;
/*     */       }
/*     */       else
/*     */       {
/* 214 */         i = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 222 */     return i;
/*     */   }
/*     */ 
/*     */   void fetchNextColumns()
/*     */     throws SQLException
/*     */   {
/* 237 */     this.statement.continueReadRow(this.columnPosition);
/*     */   }
/*     */ 
/*     */   int readStream(byte[] paramArrayOfByte, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 254 */     int i = this.statement.currentRow;
/*     */ 
/* 256 */     if (this.statement.connection.useFetchSizeWithLongColumn)
/*     */     {
/* 258 */       byte[] arrayOfByte = this.data[i];
/* 259 */       int k = this.nbBytesRead[i];
/* 260 */       int m = this.bytesReadSoFar[i];
/*     */ 
/* 262 */       if (m == k) {
/* 263 */         return -1;
/*     */       }
/*     */ 
/* 266 */       int n = 0;
/*     */ 
/* 268 */       if (paramInt <= k - m)
/* 269 */         n = paramInt;
/*     */       else {
/* 271 */         n = k - m;
/*     */       }
/* 273 */       System.arraycopy(arrayOfByte, m, paramArrayOfByte, 0, n);
/*     */ 
/* 275 */       this.bytesReadSoFar[i] += n;
/*     */ 
/* 277 */       return n;
/*     */     }
/*     */ 
/* 282 */     int j = readStreamFromWire(paramArrayOfByte, 0, paramInt, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 286 */     return j;
/*     */   }
/*     */ 
/*     */   protected static final int readStreamFromWire(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */     throws SQLException, IOException
/*     */   {
/* 307 */     int i = -1;
/*     */     try
/*     */     {
/* 311 */       if (paramArrayOfBoolean2[0] == 0)
/*     */       {
/* 315 */         if ((paramInt2 > 255) || (paramInt2 < 0))
/*     */         {
/* 323 */           DatabaseError.throwSqlException(433);
/*     */         }
/*     */ 
/* 326 */         if (paramArrayOfBoolean1[0] != 0)
/*     */         {
/* 331 */           if (paramArrayOfInt[0] == 254)
/*     */           {
/* 336 */             i = paramT4CMAREngine.unmarshalUB1();
/*     */           }
/*     */           else
/*     */           {
/* 340 */             if (paramArrayOfInt[0] == 0)
/*     */             {
/* 346 */               return 0;
/*     */             }
/*     */ 
/* 351 */             paramArrayOfBoolean2[0] = true;
/* 352 */             i = paramArrayOfInt[0];
/*     */           }
/*     */ 
/* 355 */           paramArrayOfBoolean1[0] = false;
/* 356 */           paramArrayOfInt[0] = 0;
/*     */         }
/*     */         else
/*     */         {
/* 360 */           i = paramT4CMAREngine.unmarshalUB1();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 367 */         paramArrayOfBoolean2[0] = false;
/*     */       }
/*     */ 
/* 371 */       if (i > 0)
/*     */       {
/* 375 */         paramT4CMAREngine.unmarshalNBytes(paramArrayOfByte, paramInt1, i);
/*     */       }
/*     */       else
/*     */       {
/* 379 */         i = -1;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (BreakNetException localBreakNetException)
/*     */     {
/* 390 */       i = paramT4CMAREngine.unmarshalSB1();
/* 391 */       if (i == 4)
/*     */       {
/* 393 */         paramT4CTTIoer.init();
/* 394 */         paramT4CTTIoer.processError();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 399 */     if (i == -1)
/*     */     {
/* 401 */       paramArrayOfBoolean1[0] = true;
/*     */ 
/* 405 */       paramT4CMAREngine.unmarshalUB2();
/* 406 */       paramT4CMAREngine.unmarshalUB2();
/*     */     }
/*     */ 
/* 409 */     return i;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 422 */     String str = super.getString(paramInt);
/*     */ 
/* 424 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 426 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 428 */     return str;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CLongAccessor
 * JD-Core Version:    0.6.0
 */