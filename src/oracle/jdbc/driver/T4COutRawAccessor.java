/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4COutRawAccessor extends OutRawAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  73 */   final int[] meta = new int[1];
/*     */ 
/* 311 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4COutRawAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  33 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2);
/*     */ 
/*  35 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  43 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  50 */       this.mare.unmarshalUB2();
/*  51 */       this.mare.unmarshalUB2();
/*     */     }
/*  53 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  57 */       this.mare.unmarshalSB2();
/*     */ 
/*  59 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  61 */         this.mare.unmarshalSB2();
/*     */       }
/*  63 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  66 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/*  91 */     if (this.isUseLess)
/*     */     {
/*  93 */       this.lastRowProcessed += 1;
/*     */ 
/*  95 */       return false;
/*     */     }
/*     */ 
/* 100 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 104 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 106 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 107 */       processIndicator(this.meta[0]);
/*     */ 
/* 109 */       this.lastRowProcessed += 1;
/*     */ 
/* 111 */       return false;
/*     */     }
/*     */ 
/* 115 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 116 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 120 */     if (this.isNullByDescribe)
/*     */     {
/* 122 */       this.rowSpaceIndicator[i] = -1;
/* 123 */       this.rowSpaceIndicator[j] = 0;
/* 124 */       this.lastRowProcessed += 1;
/*     */ 
/* 126 */       if (this.mare.versionNumber < 9200) {
/* 127 */         processIndicator(0);
/*     */       }
/*     */ 
/* 132 */       return false;
/*     */     }
/*     */ 
/* 135 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 139 */     this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta, this.byteLength);
/*     */ 
/* 144 */     processIndicator(this.meta[0]);
/*     */ 
/* 146 */     if (this.meta[0] == 0)
/*     */     {
/* 150 */       this.rowSpaceIndicator[i] = -1;
/* 151 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 155 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/* 156 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 159 */     this.lastRowProcessed += 1;
/*     */ 
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 170 */     if (this.lastRowProcessed == 0)
/* 171 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 173 */       i = this.lastRowProcessed;
/*     */     }
/* 175 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 176 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 178 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 179 */     int n = this.indicatorIndex + i - 1;
/* 180 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 181 */     int i2 = this.lengthIndex + i - 1;
/* 182 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 184 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 185 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 188 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 191 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 201 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 203 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 205 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 206 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 207 */     int n = this.lengthIndex + paramInt2 - 1;
/* 208 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 209 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 211 */     this.rowSpaceIndicator[n] = (short)i2;
/* 212 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 215 */     if (i2 != 0)
/*     */     {
/* 217 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 233 */     String str = super.getString(paramInt);
/*     */ 
/* 235 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 237 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 239 */     return str;
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 246 */     byte[] arrayOfByte = null;
/*     */ 
/* 248 */     if (this.rowSpaceIndicator == null) {
/* 249 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 253 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 255 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 256 */       int j = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 258 */       arrayOfByte = new byte[i];
/*     */ 
/* 260 */       System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
/*     */     }
/*     */ 
/* 263 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt) throws SQLException
/*     */   {
/* 268 */     if (this.definedColumnType == 0) {
/* 269 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 272 */     Object localObject = null;
/*     */ 
/* 274 */     if (this.rowSpaceIndicator == null) {
/* 275 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 277 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 279 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 287 */         return getString(paramInt);
/*     */       case -2:
/* 290 */         return getRAW(paramInt);
/*     */       case -4:
/*     */       case -3:
/* 295 */         return getBytes(paramInt);
/*     */       case 0:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 298 */       case 11: } DatabaseError.throwSqlException(4);
/*     */ 
/* 300 */       return null;
/*     */     }
/*     */ 
/* 304 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4COutRawAccessor
 * JD-Core Version:    0.6.0
 */