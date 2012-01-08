/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CVarnumAccessor extends VarnumAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  29 */   boolean underlyingLongRaw = false;
/*     */ 
/*  94 */   final int[] meta = new int[1];
/*     */ 
/* 365 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CVarnumAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  37 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CVarnumAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
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
/* 118 */     if (this.isUseLess)
/*     */     {
/* 120 */       this.lastRowProcessed += 1;
/*     */ 
/* 122 */       return false;
/*     */     }
/*     */ 
/* 127 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 131 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 133 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 134 */       processIndicator(this.meta[0]);
/*     */ 
/* 136 */       this.lastRowProcessed += 1;
/*     */ 
/* 138 */       return false;
/*     */     }
/*     */ 
/* 142 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 143 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 147 */     if (this.isNullByDescribe)
/*     */     {
/* 149 */       this.rowSpaceIndicator[i] = -1;
/* 150 */       this.rowSpaceIndicator[j] = 0;
/* 151 */       this.lastRowProcessed += 1;
/*     */ 
/* 153 */       if (this.mare.versionNumber < 9200) {
/* 154 */         processIndicator(0);
/*     */       }
/*     */ 
/* 159 */       return false;
/*     */     }
/*     */ 
/* 162 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 166 */     this.mare.unmarshalCLR(this.rowSpaceByte, k + 1, this.meta, this.byteLength - 1);
/*     */ 
/* 170 */     this.rowSpaceByte[k] = (byte)this.meta[0];
/*     */ 
/* 179 */     processIndicator(this.meta[0]);
/*     */ 
/* 181 */     if (this.meta[0] == 0)
/*     */     {
/* 185 */       this.rowSpaceIndicator[i] = -1;
/* 186 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 190 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/*     */ 
/* 195 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 198 */     this.lastRowProcessed += 1;
/*     */ 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 209 */     if (this.lastRowProcessed == 0)
/* 210 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 212 */       i = this.lastRowProcessed;
/*     */     }
/* 214 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 215 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 217 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 218 */     int n = this.indicatorIndex + i - 1;
/* 219 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 220 */     int i2 = this.lengthIndex + i - 1;
/* 221 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 223 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 224 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 227 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3 + 1);
/*     */ 
/* 230 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 240 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 242 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 244 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 245 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 246 */     int n = this.lengthIndex + paramInt2 - 1;
/* 247 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 248 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 250 */     this.rowSpaceIndicator[n] = (short)i2;
/* 251 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 254 */     if (i2 != 0)
/*     */     {
/* 256 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2 + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 272 */     String str = super.getString(paramInt);
/*     */ 
/* 274 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 276 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 278 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 285 */     if (this.definedColumnType == 0) {
/* 286 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 289 */     Object localObject = null;
/*     */ 
/* 291 */     if (this.rowSpaceIndicator == null) {
/* 292 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 294 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 296 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 304 */         return getString(paramInt);
/*     */       case 2:
/*     */       case 3:
/* 309 */         return getBigDecimal(paramInt);
/*     */       case 4:
/* 312 */         return new Integer(getInt(paramInt));
/*     */       case -6:
/* 315 */         return new Byte(getByte(paramInt));
/*     */       case 5:
/* 318 */         return new Short(getShort(paramInt));
/*     */       case -7:
/*     */       case 16:
/* 323 */         return new Boolean(getBoolean(paramInt));
/*     */       case -5:
/* 326 */         return new Long(getLong(paramInt));
/*     */       case 7:
/* 329 */         return new Float(getFloat(paramInt));
/*     */       case 6:
/*     */       case 8:
/* 334 */         return new Double(getDouble(paramInt));
/*     */       case 91:
/* 337 */         return getDate(paramInt);
/*     */       case 92:
/* 340 */         return getTime(paramInt);
/*     */       case 93:
/* 343 */         return getTimestamp(paramInt);
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 350 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 353 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 355 */       return null;
/*     */     }
/*     */ 
/* 359 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CVarnumAccessor
 * JD-Core Version:    0.6.0
 */