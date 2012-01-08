/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CBlobAccessor extends BlobAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/* 113 */   final int[] meta = new int[1];
/*     */ 
/* 328 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CBlobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  36 */     super(paramOracleStatement, 4000, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  38 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CBlobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  50 */     super(paramOracleStatement, 4000, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  54 */     this.mare = paramT4CMAREngine;
/*  55 */     this.definedColumnType = paramInt7;
/*  56 */     this.definedColumnSize = paramInt8;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  69 */     String str = super.getString(paramInt);
/*     */ 
/*  71 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/*  73 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/*  75 */     return str;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  82 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  89 */       this.mare.unmarshalUB2();
/*  90 */       this.mare.unmarshalUB2();
/*     */     }
/*  92 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  96 */       this.mare.unmarshalSB2();
/*     */ 
/*  98 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/* 100 */         this.mare.unmarshalSB2();
/*     */       }
/* 102 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/* 105 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 130 */     if (this.isUseLess)
/*     */     {
/* 132 */       this.lastRowProcessed += 1;
/*     */ 
/* 134 */       return false;
/*     */     }
/*     */ 
/* 139 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 141 */       i = (int)this.mare.unmarshalUB4();
/*     */ 
/* 143 */       if (i == 0)
/*     */       {
/* 145 */         this.meta[0] = -1;
/*     */ 
/* 147 */         processIndicator(0);
/*     */ 
/* 149 */         this.lastRowProcessed += 1;
/*     */ 
/* 151 */         return false;
/*     */       }
/*     */ 
/* 155 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 157 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 158 */       processIndicator(this.meta[0]);
/*     */ 
/* 160 */       this.lastRowProcessed += 1;
/*     */ 
/* 162 */       return false;
/*     */     }
/*     */ 
/* 165 */     int i = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 166 */     int j = this.indicatorIndex + this.lastRowProcessed;
/* 167 */     int k = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 171 */     if (this.isNullByDescribe)
/*     */     {
/* 173 */       this.rowSpaceIndicator[j] = -1;
/* 174 */       this.rowSpaceIndicator[k] = 0;
/* 175 */       this.lastRowProcessed += 1;
/*     */ 
/* 177 */       if (this.mare.versionNumber < 9200) {
/* 178 */         processIndicator(0);
/*     */       }
/*     */ 
/* 183 */       return false;
/*     */     }
/*     */ 
/* 186 */     int m = (int)this.mare.unmarshalUB4();
/*     */ 
/* 188 */     if (m == 0)
/*     */     {
/* 190 */       this.meta[0] = -1;
/*     */ 
/* 192 */       processIndicator(0);
/*     */ 
/* 195 */       this.rowSpaceIndicator[j] = -1;
/* 196 */       this.rowSpaceIndicator[k] = 0;
/*     */ 
/* 200 */       this.lastRowProcessed += 1;
/*     */ 
/* 202 */       return false;
/*     */     }
/*     */ 
/* 205 */     this.mare.unmarshalCLR(this.rowSpaceByte, i, this.meta, this.byteLength);
/*     */ 
/* 208 */     processIndicator(this.meta[0]);
/*     */ 
/* 213 */     if (this.meta[0] == 0)
/*     */     {
/* 217 */       this.rowSpaceIndicator[j] = -1;
/* 218 */       this.rowSpaceIndicator[k] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 222 */       this.rowSpaceIndicator[k] = (short)this.meta[0];
/* 223 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */ 
/* 226 */     this.lastRowProcessed += 1;
/*     */ 
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 237 */     if (this.lastRowProcessed == 0)
/* 238 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 240 */       i = this.lastRowProcessed;
/*     */     }
/* 242 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 243 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 245 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 246 */     int n = this.indicatorIndex + i - 1;
/* 247 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 248 */     int i2 = this.lengthIndex + i - 1;
/* 249 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 251 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 252 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 255 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 258 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 268 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 270 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 272 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 273 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 274 */     int n = this.lengthIndex + paramInt2 - 1;
/* 275 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 276 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 278 */     this.rowSpaceIndicator[n] = (short)i2;
/* 279 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 282 */     if (i2 != 0)
/*     */     {
/* 284 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 298 */     if (this.definedColumnType == 0) {
/* 299 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 302 */     Object localObject = null;
/*     */ 
/* 304 */     if (this.rowSpaceIndicator == null) {
/* 305 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 307 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 309 */       switch (this.definedColumnType)
/*     */       {
/*     */       case 2004:
/* 313 */         return getBLOB(paramInt);
/*     */       }
/*     */ 
/* 316 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 318 */       return null;
/*     */     }
/*     */ 
/* 322 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CBlobAccessor
 * JD-Core Version:    0.6.0
 */