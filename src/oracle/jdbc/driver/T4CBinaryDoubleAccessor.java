/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CBinaryDoubleAccessor extends BinaryDoubleAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  29 */   boolean underlyingLongRaw = false;
/*     */ 
/* 113 */   final int[] meta = new int[1];
/*     */ 
/* 337 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4CBinaryDoubleAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  37 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CBinaryDoubleAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
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
/*  65 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  72 */       this.mare.unmarshalUB2();
/*  73 */       this.mare.unmarshalUB2();
/*     */     }
/*  75 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  79 */       this.mare.unmarshalSB2();
/*     */ 
/*  81 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  83 */         this.mare.unmarshalSB2();
/*     */       }
/*  85 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  88 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 100 */     String str = super.getString(paramInt);
/*     */ 
/* 102 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 104 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 106 */     return str;
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 131 */     if (this.isUseLess)
/*     */     {
/* 133 */       this.lastRowProcessed += 1;
/*     */ 
/* 135 */       return false;
/*     */     }
/*     */ 
/* 140 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 144 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 146 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 147 */       processIndicator(this.meta[0]);
/*     */ 
/* 149 */       this.lastRowProcessed += 1;
/*     */ 
/* 151 */       return false;
/*     */     }
/*     */ 
/* 155 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 156 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 160 */     if (this.isNullByDescribe)
/*     */     {
/* 162 */       this.rowSpaceIndicator[i] = -1;
/* 163 */       this.rowSpaceIndicator[j] = 0;
/* 164 */       this.lastRowProcessed += 1;
/*     */ 
/* 166 */       if (this.mare.versionNumber < 9200) {
/* 167 */         processIndicator(0);
/*     */       }
/*     */ 
/* 172 */       return false;
/*     */     }
/*     */ 
/* 175 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 179 */     this.mare.unmarshalCLR(this.rowSpaceByte, k, this.meta, this.byteLength);
/*     */ 
/* 184 */     processIndicator(this.meta[0]);
/*     */ 
/* 186 */     if (this.meta[0] == 0)
/*     */     {
/* 190 */       this.rowSpaceIndicator[i] = -1;
/* 191 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 195 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/* 196 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 199 */     this.lastRowProcessed += 1;
/*     */ 
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 210 */     if (this.lastRowProcessed == 0)
/* 211 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 213 */       i = this.lastRowProcessed;
/*     */     }
/* 215 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 216 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 218 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 219 */     int n = this.indicatorIndex + i - 1;
/* 220 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 221 */     int i2 = this.lengthIndex + i - 1;
/* 222 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 224 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 225 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 228 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 231 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 241 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 243 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 245 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 246 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 247 */     int n = this.lengthIndex + paramInt2 - 1;
/* 248 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 249 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 251 */     this.rowSpaceIndicator[n] = (short)i2;
/* 252 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 255 */     if (i2 != 0)
/*     */     {
/* 257 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
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
/*     */       case 2:
/*     */       case 3:
/* 292 */         return getBigDecimal(paramInt);
/*     */       case 6:
/*     */       case 8:
/* 297 */         return new Double(getDouble(paramInt));
/*     */       case 101:
/* 300 */         return getBINARY_DOUBLE(paramInt);
/*     */       case 7:
/* 303 */         return new Float(getFloat(paramInt));
/*     */       case -5:
/* 306 */         return new Long(getLong(paramInt));
/*     */       case -6:
/* 309 */         return new Byte(getByte(paramInt));
/*     */       case 5:
/* 312 */         return new Short(getShort(paramInt));
/*     */       case 4:
/* 315 */         return new Integer(getInt(paramInt));
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 322 */         return getBytes(paramInt);
/*     */       }
/*     */ 
/* 325 */       DatabaseError.throwSqlException(4);
/*     */ 
/* 327 */       return null;
/*     */     }
/*     */ 
/* 331 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CBinaryDoubleAccessor
 * JD-Core Version:    0.6.0
 */