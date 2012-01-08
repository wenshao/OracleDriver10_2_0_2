/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CBfileAccessor extends BfileAccessor
/*     */ {
/*     */   static final int maxLength = 530;
/*     */   T4CMAREngine mare;
/* 116 */   final int[] meta = new int[1];
/*     */ 
/* 331 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CBfileAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  39 */     super(paramOracleStatement, 530, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  41 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CBfileAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  53 */     super(paramOracleStatement, 530, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  57 */     this.mare = paramT4CMAREngine;
/*  58 */     this.definedColumnType = paramInt7;
/*  59 */     this.definedColumnSize = paramInt8;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  72 */     String str = super.getString(paramInt);
/*     */ 
/*  74 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/*  76 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/*  78 */     return str;
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
/* 142 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 144 */       i = (int)this.mare.unmarshalUB4();
/*     */ 
/* 146 */       if (i == 0)
/*     */       {
/* 148 */         this.meta[0] = -1;
/*     */ 
/* 150 */         processIndicator(0);
/*     */ 
/* 152 */         this.lastRowProcessed += 1;
/*     */ 
/* 154 */         return false;
/*     */       }
/*     */ 
/* 158 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 160 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 161 */       processIndicator(this.meta[0]);
/*     */ 
/* 163 */       this.lastRowProcessed += 1;
/*     */ 
/* 165 */       return false;
/*     */     }
/*     */ 
/* 168 */     int i = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 169 */     int j = this.indicatorIndex + this.lastRowProcessed;
/* 170 */     int k = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 174 */     if (this.isNullByDescribe)
/*     */     {
/* 176 */       this.rowSpaceIndicator[j] = -1;
/* 177 */       this.rowSpaceIndicator[k] = 0;
/* 178 */       this.lastRowProcessed += 1;
/*     */ 
/* 180 */       if (this.mare.versionNumber < 9200) {
/* 181 */         processIndicator(0);
/*     */       }
/*     */ 
/* 186 */       return false;
/*     */     }
/*     */ 
/* 189 */     int m = (int)this.mare.unmarshalUB4();
/*     */ 
/* 191 */     if (m == 0)
/*     */     {
/* 193 */       this.meta[0] = -1;
/*     */ 
/* 195 */       processIndicator(0);
/*     */ 
/* 198 */       this.rowSpaceIndicator[j] = -1;
/* 199 */       this.rowSpaceIndicator[k] = 0;
/*     */ 
/* 203 */       this.lastRowProcessed += 1;
/*     */ 
/* 205 */       return false;
/*     */     }
/*     */ 
/* 208 */     this.mare.unmarshalCLR(this.rowSpaceByte, i, this.meta, this.byteLength);
/*     */ 
/* 211 */     processIndicator(this.meta[0]);
/*     */ 
/* 216 */     if (this.meta[0] == 0)
/*     */     {
/* 220 */       this.rowSpaceIndicator[j] = -1;
/* 221 */       this.rowSpaceIndicator[k] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 225 */       this.rowSpaceIndicator[k] = (short)this.meta[0];
/* 226 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */ 
/* 229 */     this.lastRowProcessed += 1;
/*     */ 
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 240 */     if (this.lastRowProcessed == 0)
/* 241 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 243 */       i = this.lastRowProcessed;
/*     */     }
/* 245 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 246 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 248 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 249 */     int n = this.indicatorIndex + i - 1;
/* 250 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 251 */     int i2 = this.lengthIndex + i - 1;
/* 252 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 254 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 255 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 258 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 261 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 271 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 273 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 275 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 276 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 277 */     int n = this.lengthIndex + paramInt2 - 1;
/* 278 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 279 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 281 */     this.rowSpaceIndicator[n] = (short)i2;
/* 282 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 285 */     if (i2 != 0)
/*     */     {
/* 287 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 301 */     if (this.definedColumnType == 0) {
/* 302 */       return super.getObject(paramInt);
/*     */     }
/*     */ 
/* 305 */     Object localObject = null;
/*     */ 
/* 307 */     if (this.rowSpaceIndicator == null) {
/* 308 */       DatabaseError.throwSqlException(21);
/*     */     }
/* 310 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 312 */       switch (this.definedColumnType)
/*     */       {
/*     */       case -13:
/* 316 */         return getBFILE(paramInt);
/*     */       }
/*     */ 
/* 319 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 321 */       return null;
/*     */     }
/*     */ 
/* 325 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CBfileAccessor
 * JD-Core Version:    0.6.0
 */