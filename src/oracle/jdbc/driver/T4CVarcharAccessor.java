/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CVarcharAccessor extends VarcharAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*     */   static final int t4MaxLength = 4000;
/*     */   static final int t4CallMaxLength = 4001;
/*     */   static final int t4PlsqlMaxLength = 32512;
/*     */   static final int t4SqlMinLength = 32;
/*  37 */   boolean underlyingLong = false;
/*     */ 
/* 116 */   final int[] meta = new int[1];
/* 117 */   final int[] tmp = new int[1];
/* 118 */   final int[] escapeSequenceArr = new int[1];
/* 119 */   final boolean[] readHeaderArr = new boolean[1];
/* 120 */   final boolean[] readAsNonStreamArr = new boolean[1];
/*     */ 
/* 505 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CVarcharAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  43 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  45 */     this.mare = paramT4CMAREngine;
/*     */ 
/*  47 */     calculateSizeTmpByteArray();
/*     */   }
/*     */ 
/*     */   T4CVarcharAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  59 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  62 */     this.mare = paramT4CMAREngine;
/*  63 */     this.definedColumnType = paramInt8;
/*  64 */     this.definedColumnSize = paramInt9;
/*     */ 
/*  66 */     calculateSizeTmpByteArray();
/*     */ 
/*  72 */     this.oacmxl = paramInt7;
/*     */ 
/*  74 */     if (this.oacmxl == -1)
/*     */     {
/*  76 */       this.underlyingLong = true;
/*  77 */       this.oacmxl = 4000;
/*     */     }
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  86 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  93 */       this.mare.unmarshalUB2();
/*  94 */       this.mare.unmarshalUB2();
/*     */     }
/*  96 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/* 100 */       this.mare.unmarshalSB2();
/*     */ 
/* 102 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/* 104 */         this.mare.unmarshalSB2();
/*     */       }
/* 106 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/* 109 */       this.mare.processIndicator(paramInt <= 0, paramInt);
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
/* 141 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 142 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 146 */     byte[] arrayOfByte1 = this.statement.tmpByteArray;
/* 147 */     int k = this.columnIndex + this.lastRowProcessed * this.charLength;
/*     */ 
/* 149 */     if (!this.underlyingLong)
/*     */     {
/* 154 */       if (this.rowSpaceIndicator == null)
/*     */       {
/* 158 */         byte[] arrayOfByte2 = new byte[16000];
/*     */ 
/* 160 */         this.mare.unmarshalCLR(arrayOfByte2, 0, this.meta);
/* 161 */         processIndicator(this.meta[0]);
/*     */ 
/* 163 */         this.lastRowProcessed += 1;
/*     */ 
/* 165 */         return false;
/*     */       }
/*     */ 
/* 170 */       if (this.isNullByDescribe)
/*     */       {
/* 172 */         this.rowSpaceIndicator[i] = -1;
/* 173 */         this.rowSpaceIndicator[j] = 0;
/* 174 */         this.lastRowProcessed += 1;
/*     */ 
/* 176 */         if (this.mare.versionNumber < 9200) {
/* 177 */           processIndicator(0);
/*     */         }
/*     */ 
/* 182 */         return false;
/*     */       }
/*     */ 
/* 185 */       if (this.statement.maxFieldSize > 0)
/* 186 */         this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta, this.statement.maxFieldSize);
/*     */       else {
/* 188 */         this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 194 */       this.escapeSequenceArr[0] = this.mare.unmarshalUB1();
/*     */ 
/* 197 */       if (this.mare.escapeSequenceNull(this.escapeSequenceArr[0]))
/*     */       {
/* 201 */         this.meta[0] = 0;
/*     */ 
/* 203 */         this.mare.processIndicator(false, 0);
/*     */ 
/* 205 */         m = this.mare.unmarshalUB2();
/*     */       }
/*     */       else
/*     */       {
/* 209 */         m = 0;
/* 210 */         int n = 0;
/* 211 */         byte[] arrayOfByte3 = arrayOfByte1;
/* 212 */         int i1 = 0;
/*     */ 
/* 214 */         this.readHeaderArr[0] = true;
/* 215 */         this.readAsNonStreamArr[0] = false;
/*     */ 
/* 217 */         while (m != -1)
/*     */         {
/* 219 */           if ((arrayOfByte3 == arrayOfByte1) && (n + 255 > arrayOfByte1.length))
/*     */           {
/* 223 */             arrayOfByte3 = new byte['ÿ'];
/*     */           }
/* 225 */           if (arrayOfByte3 == arrayOfByte1)
/* 226 */             i1 = n;
/*     */           else {
/* 228 */             i1 = 0;
/*     */           }
/* 230 */           m = T4CLongAccessor.readStreamFromWire(arrayOfByte3, i1, 255, this.escapeSequenceArr, this.readHeaderArr, this.readAsNonStreamArr, this.mare, ((T4CConnection)this.statement.connection).oer);
/*     */ 
/* 234 */           if (m == -1)
/*     */             continue;
/* 236 */           if (arrayOfByte3 == arrayOfByte1) {
/* 237 */             n += m; continue;
/* 238 */           }if (arrayOfByte1.length - n <= 0)
/*     */           {
/*     */             continue;
/*     */           }
/* 242 */           int i2 = arrayOfByte1.length - n;
/*     */ 
/* 244 */           System.arraycopy(arrayOfByte3, 0, arrayOfByte1, n, i2);
/*     */ 
/* 247 */           n += i2;
/*     */         }
/*     */ 
/* 252 */         if (arrayOfByte3 != arrayOfByte1) {
/* 253 */           arrayOfByte3 = null;
/*     */         }
/* 255 */         this.meta[0] = n;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 260 */     this.tmp[0] = this.meta[0];
/*     */ 
/* 262 */     int m = 0;
/*     */ 
/* 264 */     if (this.formOfUse == 2) {
/* 265 */       m = this.statement.connection.conversion.NCHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
/*     */     }
/*     */     else
/*     */     {
/* 278 */       m = this.statement.connection.conversion.CHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
/*     */     }
/*     */ 
/* 285 */     this.rowSpaceChar[k] = (char)(m * 2);
/*     */ 
/* 289 */     if (!this.underlyingLong) {
/* 290 */       processIndicator(this.meta[0]);
/*     */     }
/* 292 */     if (this.meta[0] == 0)
/*     */     {
/* 296 */       this.rowSpaceIndicator[i] = -1;
/* 297 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 301 */       this.rowSpaceIndicator[j] = (short)(this.meta[0] * 2);
/*     */ 
/* 305 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 308 */     this.lastRowProcessed += 1;
/*     */ 
/* 312 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 319 */     if (this.lastRowProcessed == 0)
/* 320 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 322 */       i = this.lastRowProcessed;
/*     */     }
/* 324 */     int j = this.columnIndex + this.lastRowProcessed * this.charLength;
/* 325 */     int k = this.columnIndex + (i - 1) * this.charLength;
/*     */ 
/* 327 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 328 */     int n = this.indicatorIndex + i - 1;
/* 329 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 330 */     int i2 = this.lengthIndex + i - 1;
/* 331 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 333 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 334 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 337 */     System.arraycopy(this.rowSpaceChar, k, this.rowSpaceChar, j, this.rowSpaceChar[k] / '\002' + 1);
/*     */ 
/* 341 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 351 */     int i = this.columnIndex + (paramInt2 - 1) * this.charLength;
/*     */ 
/* 353 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.charLength;
/*     */ 
/* 355 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 356 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 357 */     int n = this.lengthIndex + paramInt2 - 1;
/* 358 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 359 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 361 */     this.rowSpaceIndicator[n] = (short)i2;
/* 362 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 365 */     if (i2 != 0)
/*     */     {
/* 367 */       System.arraycopy(paramArrayOfChar, j, this.rowSpaceChar, i, paramArrayOfChar[j] / '\002' + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   void calculateSizeTmpByteArray()
/*     */   {
/*     */     int i;
/* 390 */     if (this.formOfUse == 2)
/*     */     {
/* 394 */       i = (this.charLength - 1) * this.statement.connection.conversion.maxNCharSize;
/*     */     }
/*     */     else
/*     */     {
/* 398 */       i = (this.charLength - 1) * this.statement.connection.conversion.cMaxCharSize;
/*     */     }
/*     */ 
/* 401 */     if (this.statement.sizeTmpByteArray < i)
/* 402 */       this.statement.sizeTmpByteArray = i;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 412 */     String str = super.getString(paramInt);
/*     */ 
/* 414 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 416 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 418 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 425 */     if (this.definedColumnType == 0) {
/* 426 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 429 */     Object localObject = null;
/*     */ 
/* 431 */     if (this.rowSpaceIndicator == null) {
/* 432 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 434 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 436 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 444 */         return getString(paramInt);
/*     */       case 2:
/*     */       case 3:
/* 449 */         return getBigDecimal(paramInt);
/*     */       case 4:
/* 452 */         return new Integer(getInt(paramInt));
/*     */       case -6:
/* 455 */         return new Byte(getByte(paramInt));
/*     */       case 5:
/* 458 */         return new Short(getShort(paramInt));
/*     */       case -7:
/*     */       case 16:
/* 463 */         return new Boolean(getBoolean(paramInt));
/*     */       case -5:
/* 466 */         return new Long(getLong(paramInt));
/*     */       case 7:
/* 469 */         return new Float(getFloat(paramInt));
/*     */       case 6:
/*     */       case 8:
/* 474 */         return new Double(getDouble(paramInt));
/*     */       case 91:
/* 477 */         return getDate(paramInt);
/*     */       case 92:
/* 480 */         return getTime(paramInt);
/*     */       case 93:
/* 483 */         return getTimestamp(paramInt);
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 490 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 493 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 495 */       return null;
/*     */     }
/*     */ 
/* 499 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CVarcharAccessor
 * JD-Core Version:    0.6.0
 */