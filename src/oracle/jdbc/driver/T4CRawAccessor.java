/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import B;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CRawAccessor extends RawAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  29 */   boolean underlyingLongRaw = false;
/*     */ 
/*  94 */   final int[] meta = new int[1];
/*  95 */   final int[] escapeSequenceArr = new int[1];
/*  96 */   final boolean[] readHeaderArr = new boolean[1];
/*  97 */   final boolean[] readAsNonStreamArr = new boolean[1];
/*     */ 
/* 405 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CRawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  37 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CRawAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  48 */     super(paramOracleStatement, paramInt1 == -1 ? paramInt8 : paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  51 */     this.mare = paramT4CMAREngine;
/*  52 */     this.definedColumnType = paramInt7;
/*  53 */     this.definedColumnSize = paramInt8;
/*     */ 
/*  55 */     if (paramInt1 == -1)
/*  56 */       this.underlyingLongRaw = true;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  64 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  71 */       this.mare.unmarshalUB2();
/*  72 */       this.mare.unmarshalUB2();
/*     */     }
/*  74 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  78 */       this.mare.unmarshalSB2();
/*     */ 
/*  80 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  82 */         this.mare.unmarshalSB2();
/*     */       }
/*  84 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  87 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 115 */     if (this.isUseLess)
/*     */     {
/* 117 */       this.lastRowProcessed += 1;
/*     */ 
/* 119 */       return false;
/*     */     }
/*     */ 
/* 123 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 124 */     int j = this.lengthIndex + this.lastRowProcessed;
/* 125 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 127 */     if (!this.underlyingLongRaw)
/*     */     {
/* 132 */       if (this.rowSpaceIndicator == null)
/*     */       {
/* 136 */         byte[] arrayOfByte1 = new byte[16000];
/*     */ 
/* 138 */         this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
/* 139 */         processIndicator(this.meta[0]);
/*     */ 
/* 141 */         this.lastRowProcessed += 1;
/*     */ 
/* 143 */         return false;
/*     */       }
/*     */ 
/* 148 */       if (this.isNullByDescribe)
/*     */       {
/* 150 */         this.rowSpaceIndicator[i] = -1;
/* 151 */         this.rowSpaceIndicator[j] = 0;
/* 152 */         this.lastRowProcessed += 1;
/*     */ 
/* 154 */         if (this.mare.versionNumber < 9200) {
/* 155 */           processIndicator(0);
/*     */         }
/*     */ 
/* 160 */         return false;
/*     */       }
/*     */ 
/* 165 */       this.mare.unmarshalCLR(this.rowSpaceByte, k + 2, this.meta, this.byteLength - 2);
/*     */     }
/*     */     else
/*     */     {
/* 172 */       this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
/*     */       int m;
/* 175 */       if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
/*     */       {
/* 179 */         this.meta[0] = 0;
/*     */ 
/* 181 */         this.mare.processIndicator(false, 0);
/*     */ 
/* 183 */         m = (int)this.mare.unmarshalUB4();
/*     */       }
/*     */       else
/*     */       {
/* 187 */         m = 0;
/* 188 */         int n = 0;
/* 189 */         byte[] arrayOfByte2 = this.rowSpaceByte;
/* 190 */         int i1 = k + 2;
/*     */ 
/* 192 */         this.readHeaderArr[0] = true;
/* 193 */         this.readAsNonStreamArr[0] = false;
/*     */ 
/* 195 */         while (m != -1)
/*     */         {
/* 197 */           if ((arrayOfByte2 == this.rowSpaceByte) && (n + 255 > this.byteLength - 2))
/*     */           {
/* 200 */             arrayOfByte2 = new byte['ÿ'];
/*     */           }
/* 202 */           if (arrayOfByte2 != this.rowSpaceByte) {
/* 203 */             i1 = 0;
/*     */           }
/* 205 */           m = T4CLongRawAccessor.readStreamFromWire(arrayOfByte2, i1, 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 209 */           if (m == -1)
/*     */             continue;
/* 211 */           if (arrayOfByte2 == this.rowSpaceByte)
/*     */           {
/* 213 */             n += m;
/* 214 */             i1 += m; continue;
/*     */           }
/* 216 */           if (this.byteLength - 2 - n <= 0)
/*     */           {
/*     */             continue;
/*     */           }
/* 220 */           int i2 = this.byteLength - 2 - n;
/*     */ 
/* 222 */           System.arraycopy(arrayOfByte2, 0, this.rowSpaceByte, i1, i2);
/*     */ 
/* 225 */           n += i2;
/*     */         }
/*     */ 
/* 230 */         if (arrayOfByte2 != this.rowSpaceByte) {
/* 231 */           arrayOfByte2 = null;
/*     */         }
/* 233 */         this.meta[0] = n;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 238 */     this.rowSpaceByte[k] = (byte)((this.meta[0] & 0xFF00) >> 8);
/* 239 */     this.rowSpaceByte[(k + 1)] = (byte)(this.meta[0] & 0xFF);
/*     */ 
/* 248 */     if (!this.underlyingLongRaw) {
/* 249 */       processIndicator(this.meta[0]);
/*     */     }
/* 251 */     if (this.meta[0] == 0)
/*     */     {
/* 255 */       this.rowSpaceIndicator[i] = -1;
/* 256 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 260 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/*     */ 
/* 265 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 268 */     this.lastRowProcessed += 1;
/*     */ 
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 279 */     if (this.lastRowProcessed == 0)
/* 280 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 282 */       i = this.lastRowProcessed;
/*     */     }
/* 284 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 285 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 287 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 288 */     int n = this.indicatorIndex + i - 1;
/* 289 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 290 */     int i2 = this.lengthIndex + i - 1;
/* 291 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 293 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 294 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 297 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3 + 2);
/*     */ 
/* 300 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 310 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 312 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 314 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 315 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 316 */     int n = this.lengthIndex + paramInt2 - 1;
/* 317 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 318 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 320 */     this.rowSpaceIndicator[n] = (short)i2;
/* 321 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 324 */     if (i2 != 0)
/*     */     {
/* 326 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2 + 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 337 */     String str = super.getString(paramInt);
/*     */ 
/* 341 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize * 2))
/*     */     {
/* 343 */       str = str.substring(0, this.definedColumnSize * 2);
/*     */     }
/* 345 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt) throws SQLException
/*     */   {
/* 350 */     if (this.definedColumnType == 0) {
/* 351 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 354 */     Object localObject = null;
/*     */ 
/* 356 */     if (this.rowSpaceIndicator == null) {
/* 357 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 359 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 361 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 369 */         return getString(paramInt);
/*     */       case -2:
/* 372 */         return getRAW(paramInt);
/*     */       }
/*     */ 
/* 375 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 377 */       return null;
/*     */     }
/*     */ 
/* 381 */     return localObject;
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 387 */     Object localObject = super.getBytes(paramInt);
/*     */ 
/* 389 */     if ((localObject != null) && (this.definedColumnSize > 0) && (this.definedColumnSize < localObject.length))
/*     */     {
/* 393 */       byte[] arrayOfByte = new byte[this.definedColumnSize];
/*     */ 
/* 395 */       System.arraycopy(localObject, 0, arrayOfByte, 0, this.definedColumnSize);
/*     */ 
/* 397 */       localObject = arrayOfByte;
/*     */     }
/*     */ 
/* 400 */     return (B)localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CRawAccessor
 * JD-Core Version:    0.6.0
 */