/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CIntervalymAccessor extends IntervalymAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  24 */   static int maxLength = 11;
/*     */ 
/* 106 */   final int[] meta = new int[1];
/*     */ 
/* 323 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CIntervalymAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  37 */     super(paramOracleStatement, maxLength, paramShort, paramInt2, paramBoolean);
/*  38 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CIntervalymAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  57 */     super(paramOracleStatement, maxLength, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  66 */     this.mare = paramT4CMAREngine;
/*  67 */     this.definedColumnType = paramInt7;
/*  68 */     this.definedColumnSize = paramInt8;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  76 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  83 */       this.mare.unmarshalUB2();
/*  84 */       this.mare.unmarshalUB2();
/*     */     }
/*  86 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  90 */       this.mare.unmarshalSB2();
/*     */ 
/*  92 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  94 */         this.mare.unmarshalSB2();
/*     */       }
/*  96 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  99 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 124 */     if (this.isUseLess)
/*     */     {
/* 126 */       this.lastRowProcessed += 1;
/*     */ 
/* 128 */       return false;
/*     */     }
/*     */ 
/* 133 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 137 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 139 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 140 */       processIndicator(this.meta[0]);
/*     */ 
/* 142 */       this.lastRowProcessed += 1;
/*     */ 
/* 144 */       return false;
/*     */     }
/*     */ 
/* 148 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 149 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 153 */     if (this.isNullByDescribe)
/*     */     {
/* 155 */       this.rowSpaceIndicator[i] = -1;
/* 156 */       this.rowSpaceIndicator[j] = 0;
/* 157 */       this.lastRowProcessed += 1;
/*     */ 
/* 159 */       if (this.mare.versionNumber < 9200) {
/* 160 */         processIndicator(0);
/*     */       }
/*     */ 
/* 165 */       return false;
/*     */     }
/*     */ 
/* 168 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 172 */     this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta, this.byteLength);
/*     */ 
/* 177 */     processIndicator(this.meta[0]);
/*     */ 
/* 179 */     if (this.meta[0] == 0)
/*     */     {
/* 183 */       this.rowSpaceIndicator[i] = -1;
/* 184 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 188 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/* 189 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 192 */     this.lastRowProcessed += 1;
/*     */ 
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 203 */     if (this.lastRowProcessed == 0)
/* 204 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 206 */       i = this.lastRowProcessed;
/*     */     }
/* 208 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 209 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 211 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 212 */     int n = this.indicatorIndex + i - 1;
/* 213 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 214 */     int i2 = this.lengthIndex + i - 1;
/* 215 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 217 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 218 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 221 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 224 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 234 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 236 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 238 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 239 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 240 */     int n = this.lengthIndex + paramInt2 - 1;
/* 241 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 242 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 244 */     this.rowSpaceIndicator[n] = (short)i2;
/* 245 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 248 */     if (i2 != 0)
/*     */     {
/* 250 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 266 */     String str = super.getString(paramInt);
/*     */ 
/* 268 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 270 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 272 */     return str;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 279 */     if (this.definedColumnType == 0) {
/* 280 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 283 */     Object localObject = null;
/*     */ 
/* 285 */     if (this.rowSpaceIndicator == null) {
/* 286 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 288 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 290 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 298 */         return getString(paramInt);
/*     */       case -103:
/* 301 */         return getINTERVALYM(paramInt);
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 308 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 311 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 313 */       return null;
/*     */     }
/*     */ 
/* 317 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CIntervalymAccessor
 * JD-Core Version:    0.6.0
 */