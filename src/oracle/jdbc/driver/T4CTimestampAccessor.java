/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CTimestampAccessor extends TimestampAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  29 */   boolean underlyingLongRaw = false;
/*     */ 
/*  94 */   final int[] meta = new int[1];
/*     */ 
/* 317 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  37 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CTimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
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
/* 112 */     if (this.isUseLess)
/*     */     {
/* 114 */       this.lastRowProcessed += 1;
/*     */ 
/* 116 */       return false;
/*     */     }
/*     */ 
/* 121 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 125 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 127 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 128 */       processIndicator(this.meta[0]);
/*     */ 
/* 130 */       this.lastRowProcessed += 1;
/*     */ 
/* 132 */       return false;
/*     */     }
/*     */ 
/* 136 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 137 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 141 */     if (this.isNullByDescribe)
/*     */     {
/* 143 */       this.rowSpaceIndicator[i] = -1;
/* 144 */       this.rowSpaceIndicator[j] = 0;
/* 145 */       this.lastRowProcessed += 1;
/*     */ 
/* 147 */       if (this.mare.versionNumber < 9200) {
/* 148 */         processIndicator(0);
/*     */       }
/*     */ 
/* 153 */       return false;
/*     */     }
/*     */ 
/* 156 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 160 */     this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta, this.byteLength);
/*     */ 
/* 165 */     processIndicator(this.meta[0]);
/*     */ 
/* 167 */     if (this.meta[0] == 0)
/*     */     {
/* 171 */       this.rowSpaceIndicator[i] = -1;
/* 172 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 176 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/* 177 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 180 */     this.lastRowProcessed += 1;
/*     */ 
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 191 */     if (this.lastRowProcessed == 0)
/* 192 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 194 */       i = this.lastRowProcessed;
/*     */     }
/* 196 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 197 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 199 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 200 */     int n = this.indicatorIndex + i - 1;
/* 201 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 202 */     int i2 = this.lengthIndex + i - 1;
/* 203 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 205 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 206 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 209 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 212 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 222 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 224 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 226 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 227 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 228 */     int n = this.lengthIndex + paramInt2 - 1;
/* 229 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 230 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 232 */     this.rowSpaceIndicator[n] = (short)i2;
/* 233 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 236 */     if (i2 != 0)
/*     */     {
/* 238 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 254 */     String str = super.getString(paramInt);
/*     */ 
/* 256 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 258 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 260 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 267 */     if (this.definedColumnType == 0) {
/* 268 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 271 */     Object localObject = null;
/*     */ 
/* 273 */     if (this.rowSpaceIndicator == null) {
/* 274 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 276 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 278 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 286 */         return getString(paramInt);
/*     */       case 93:
/* 289 */         return getTimestamp(paramInt);
/*     */       case 91:
/* 292 */         return getDate(paramInt);
/*     */       case 92:
/* 295 */         return getTime(paramInt);
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 302 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 305 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 307 */       return null;
/*     */     }
/*     */ 
/* 311 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTimestampAccessor
 * JD-Core Version:    0.6.0
 */