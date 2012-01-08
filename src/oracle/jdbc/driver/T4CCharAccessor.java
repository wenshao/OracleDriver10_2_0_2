/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CCharAccessor extends CharAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  29 */   boolean underlyingLong = false;
/*     */ 
/* 108 */   final int[] meta = new int[1];
/* 109 */   final int[] tmp = new int[1];
/*     */ 
/* 421 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CCharAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  37 */     this.mare = paramT4CMAREngine;
/*     */ 
/*  39 */     calculateSizeTmpByteArray();
/*     */   }
/*     */ 
/*     */   T4CCharAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, int paramInt9, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  51 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  54 */     this.mare = paramT4CMAREngine;
/*  55 */     this.definedColumnType = paramInt8;
/*  56 */     this.definedColumnSize = paramInt9;
/*     */ 
/*  58 */     calculateSizeTmpByteArray();
/*     */ 
/*  64 */     this.oacmxl = paramInt7;
/*     */ 
/*  66 */     if (this.oacmxl == -1)
/*     */     {
/*  68 */       this.underlyingLong = true;
/*  69 */       this.oacmxl = 4000;
/*     */     }
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  78 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  85 */       this.mare.unmarshalUB2();
/*  86 */       this.mare.unmarshalUB2();
/*     */     }
/*  88 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  92 */       this.mare.unmarshalSB2();
/*     */ 
/*  94 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  96 */         this.mare.unmarshalSB2();
/*     */       }
/*  98 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/* 101 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 122 */     if (this.isUseLess)
/*     */     {
/* 124 */       this.lastRowProcessed += 1;
/*     */ 
/* 126 */       return false;
/*     */     }
/*     */ 
/* 130 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 131 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 135 */     byte[] arrayOfByte1 = this.statement.tmpByteArray;
/* 136 */     int k = this.columnIndex + this.lastRowProcessed * this.charLength;
/*     */ 
/* 140 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 144 */       byte[] arrayOfByte2 = new byte[16000];
/*     */ 
/* 146 */       this.mare.unmarshalCLR(arrayOfByte2, 0, this.meta);
/* 147 */       processIndicator(this.meta[0]);
/*     */ 
/* 149 */       this.lastRowProcessed += 1;
/*     */ 
/* 151 */       return false;
/*     */     }
/*     */ 
/* 156 */     if (this.isNullByDescribe)
/*     */     {
/* 158 */       this.rowSpaceIndicator[i] = -1;
/* 159 */       this.rowSpaceIndicator[j] = 0;
/* 160 */       this.lastRowProcessed += 1;
/*     */ 
/* 162 */       if (this.mare.versionNumber < 9200) {
/* 163 */         processIndicator(0);
/*     */       }
/*     */ 
/* 168 */       return false;
/*     */     }
/*     */ 
/* 171 */     if (this.statement.maxFieldSize > 0)
/* 172 */       this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta, this.statement.maxFieldSize);
/*     */     else {
/* 174 */       this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
/*     */     }
/* 176 */     this.tmp[0] = this.meta[0];
/*     */ 
/* 178 */     int m = 0;
/*     */ 
/* 180 */     if (this.formOfUse == 2) {
/* 181 */       m = this.statement.connection.conversion.NCHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
/*     */     }
/*     */     else
/*     */     {
/* 195 */       m = this.statement.connection.conversion.CHARBytesToJavaChars(arrayOfByte1, 0, this.rowSpaceChar, k + 1, this.tmp, this.charLength - 1);
/*     */     }
/*     */ 
/* 202 */     this.rowSpaceChar[k] = (char)(m * 2);
/*     */ 
/* 206 */     processIndicator(this.meta[0]);
/*     */ 
/* 208 */     if (this.meta[0] == 0)
/*     */     {
/* 212 */       this.rowSpaceIndicator[i] = -1;
/* 213 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 217 */       this.rowSpaceIndicator[j] = (short)(this.meta[0] * 2);
/*     */ 
/* 221 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 224 */     this.lastRowProcessed += 1;
/*     */ 
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 235 */     if (this.lastRowProcessed == 0)
/* 236 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 238 */       i = this.lastRowProcessed;
/*     */     }
/* 240 */     int j = this.columnIndex + this.lastRowProcessed * this.charLength;
/* 241 */     int k = this.columnIndex + (i - 1) * this.charLength;
/*     */ 
/* 243 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 244 */     int n = this.indicatorIndex + i - 1;
/* 245 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 246 */     int i2 = this.lengthIndex + i - 1;
/* 247 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 249 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 250 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 253 */     System.arraycopy(this.rowSpaceChar, k, this.rowSpaceChar, j, this.rowSpaceChar[k] / '\002' + 1);
/*     */ 
/* 257 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 267 */     int i = this.columnIndex + (paramInt2 - 1) * this.charLength;
/*     */ 
/* 269 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.charLength;
/*     */ 
/* 271 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 272 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 273 */     int n = this.lengthIndex + paramInt2 - 1;
/* 274 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 275 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 277 */     this.rowSpaceIndicator[n] = (short)i2;
/* 278 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 281 */     if (i2 != 0)
/*     */     {
/* 283 */       System.arraycopy(paramArrayOfChar, j, this.rowSpaceChar, i, paramArrayOfChar[j] / '\002' + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   void calculateSizeTmpByteArray()
/*     */   {
/*     */     int i;
/* 306 */     if (this.formOfUse == 2)
/*     */     {
/* 310 */       i = (this.charLength - 1) * this.statement.connection.conversion.maxNCharSize;
/*     */     }
/*     */     else
/*     */     {
/* 314 */       i = (this.charLength - 1) * this.statement.connection.conversion.cMaxCharSize;
/*     */     }
/*     */ 
/* 317 */     if (this.statement.sizeTmpByteArray < i)
/* 318 */       this.statement.sizeTmpByteArray = i;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 328 */     String str = super.getString(paramInt);
/*     */ 
/* 330 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 332 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 334 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 340 */     if (this.definedColumnType == 0) {
/* 341 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 344 */     Object localObject = null;
/*     */ 
/* 346 */     if (this.rowSpaceIndicator == null) {
/* 347 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 349 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 351 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 359 */         return getString(paramInt);
/*     */       case 2:
/*     */       case 3:
/* 364 */         return getBigDecimal(paramInt);
/*     */       case 4:
/* 367 */         return new Integer(getInt(paramInt));
/*     */       case -6:
/* 370 */         return new Byte(getByte(paramInt));
/*     */       case 5:
/* 373 */         return new Short(getShort(paramInt));
/*     */       case -7:
/*     */       case 16:
/* 378 */         return new Boolean(getBoolean(paramInt));
/*     */       case -5:
/* 381 */         return new Long(getLong(paramInt));
/*     */       case 7:
/* 384 */         return new Float(getFloat(paramInt));
/*     */       case 6:
/*     */       case 8:
/* 389 */         return new Double(getDouble(paramInt));
/*     */       case 91:
/* 392 */         return getDate(paramInt);
/*     */       case 92:
/* 395 */         return getTime(paramInt);
/*     */       case 93:
/* 398 */         return getTimestamp(paramInt);
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 405 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 408 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 410 */       return null;
/*     */     }
/*     */ 
/* 414 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CCharAccessor
 * JD-Core Version:    0.6.0
 */