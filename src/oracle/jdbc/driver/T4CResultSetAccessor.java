/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CResultSetAccessor extends ResultSetAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  24 */   OracleStatement[] newstmt = new OracleStatement[10];
/*  25 */   byte[] empty = { 0 };
/*     */ 
/*  34 */   boolean underlyingLongRaw = false;
/*     */ 
/* 117 */   final int[] meta = new int[1];
/*     */ 
/* 332 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  40 */     super(paramOracleStatement, paramInt1, paramShort, paramInt2, paramBoolean);
/*     */ 
/*  42 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CResultSetAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  53 */     super(paramOracleStatement, paramInt1 == -1 ? paramInt8 : paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);
/*     */ 
/*  56 */     this.mare = paramT4CMAREngine;
/*  57 */     this.definedColumnType = paramInt7;
/*  58 */     this.definedColumnSize = paramInt8;
/*     */ 
/*  60 */     if (paramInt1 == -1)
/*  61 */       this.underlyingLongRaw = true;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  69 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  76 */       this.mare.unmarshalUB2();
/*  77 */       this.mare.unmarshalUB2();
/*     */     }
/*  79 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  83 */       this.mare.unmarshalSB2();
/*     */ 
/*  85 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  87 */         this.mare.unmarshalSB2();
/*     */       }
/*  89 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  92 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 104 */     String str = super.getString(paramInt);
/*     */ 
/* 106 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 108 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 110 */     return str;
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 135 */     if (this.isUseLess)
/*     */     {
/* 137 */       this.lastRowProcessed += 1;
/*     */ 
/* 139 */       return false;
/*     */     }
/*     */ 
/* 144 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 148 */       byte[] arrayOfByte = new byte[16000];
/*     */ 
/* 150 */       this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
/* 151 */       processIndicator(this.meta[0]);
/*     */ 
/* 153 */       this.lastRowProcessed += 1;
/*     */ 
/* 155 */       return false;
/*     */     }
/*     */ 
/* 159 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 160 */     int j = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 162 */     if (this.isNullByDescribe)
/*     */     {
/* 164 */       this.rowSpaceIndicator[i] = -1;
/* 165 */       this.rowSpaceIndicator[j] = 0;
/* 166 */       this.lastRowProcessed += 1;
/*     */ 
/* 168 */       processIndicator(0);
/*     */ 
/* 170 */       return false;
/*     */     }
/*     */ 
/* 173 */     int k = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 175 */     if (this.newstmt.length <= this.lastRowProcessed)
/*     */     {
/* 177 */       localObject = new OracleStatement[this.newstmt.length * 4];
/*     */ 
/* 179 */       System.arraycopy(this.newstmt, 0, localObject, 0, this.newstmt.length);
/*     */ 
/* 181 */       this.newstmt = ((OracleStatement)localObject);
/*     */     }
/*     */ 
/* 184 */     this.newstmt[this.lastRowProcessed] = this.statement.connection.RefCursorBytesToStatement(this.empty, this.statement);
/*     */ 
/* 186 */     this.newstmt[this.lastRowProcessed].needToSendOalToFetch = true;
/*     */ 
/* 188 */     Object localObject = new T4CTTIdcb(this.mare);
/*     */ 
/* 190 */     ((T4CTTIdcb)localObject).init(this.newstmt[this.lastRowProcessed], 0);
/*     */ 
/* 192 */     this.newstmt[this.lastRowProcessed].accessors = ((T4CTTIdcb)localObject).receiveFromRefCursor(this.newstmt[this.lastRowProcessed].accessors);
/*     */ 
/* 194 */     this.newstmt[this.lastRowProcessed].numberOfDefinePositions = this.newstmt[this.lastRowProcessed].accessors.length;
/*     */ 
/* 196 */     this.newstmt[this.lastRowProcessed].describedWithNames = true;
/* 197 */     this.newstmt[this.lastRowProcessed].described = true;
/*     */ 
/* 199 */     int m = (int)this.mare.unmarshalUB4();
/*     */ 
/* 201 */     this.newstmt[this.lastRowProcessed].setCursorId(m);
/*     */ 
/* 203 */     if (m > 0)
/*     */     {
/* 205 */       this.rowSpaceByte[k] = 1;
/* 206 */       this.rowSpaceByte[(k + 1)] = (byte)m;
/*     */ 
/* 210 */       this.meta[0] = 2;
/*     */     }
/*     */     else
/*     */     {
/* 214 */       this.newstmt[this.lastRowProcessed].close();
/*     */ 
/* 216 */       this.newstmt[this.lastRowProcessed] = null;
/* 217 */       this.meta[0] = 0;
/*     */     }
/*     */ 
/* 223 */     processIndicator(this.meta[0]);
/*     */ 
/* 225 */     if (this.meta[0] == 0)
/*     */     {
/* 229 */       this.rowSpaceIndicator[i] = -1;
/* 230 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 234 */       this.rowSpaceIndicator[j] = (short)this.meta[0];
/* 235 */       this.rowSpaceIndicator[i] = 0;
/*     */     }
/*     */ 
/* 238 */     this.lastRowProcessed += 1;
/*     */ 
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   void copyRow()
/*     */     throws SQLException, IOException
/*     */   {
/*     */     int i;
/* 249 */     if (this.lastRowProcessed == 0)
/* 250 */       i = this.statement.rowPrefetch;
/*     */     else {
/* 252 */       i = this.lastRowProcessed;
/*     */     }
/* 254 */     int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
/* 255 */     int k = this.columnIndex + (i - 1) * this.byteLength;
/*     */ 
/* 257 */     int m = this.indicatorIndex + this.lastRowProcessed;
/* 258 */     int n = this.indicatorIndex + i - 1;
/* 259 */     int i1 = this.lengthIndex + this.lastRowProcessed;
/* 260 */     int i2 = this.lengthIndex + i - 1;
/* 261 */     int i3 = this.rowSpaceIndicator[i2];
/*     */ 
/* 263 */     this.rowSpaceIndicator[i1] = (short)i3;
/* 264 */     this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];
/*     */ 
/* 267 */     System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
/*     */ 
/* 270 */     this.lastRowProcessed += 1;
/*     */   }
/*     */ 
/*     */   void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 280 */     int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;
/*     */ 
/* 282 */     int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;
/*     */ 
/* 284 */     int k = this.indicatorIndex + paramInt2 - 1;
/* 285 */     int m = this.indicatorIndexLastRow + paramInt1 - 1;
/* 286 */     int n = this.lengthIndex + paramInt2 - 1;
/* 287 */     int i1 = this.lengthIndexLastRow + paramInt1 - 1;
/* 288 */     int i2 = paramArrayOfShort[i1];
/*     */ 
/* 290 */     this.rowSpaceIndicator[n] = (short)i2;
/* 291 */     this.rowSpaceIndicator[k] = paramArrayOfShort[m];
/*     */ 
/* 294 */     if (i2 != 0)
/*     */     {
/* 296 */       System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
/*     */     }
/*     */   }
/*     */ 
/*     */   ResultSet getCursor(int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     OracleResultSetImpl localOracleResultSetImpl1;
/* 306 */     if (this.newstmt[paramInt] != null)
/*     */     {
/* 308 */       for (int i = 0; i < this.newstmt[paramInt].numberOfDefinePositions; i++) {
/* 309 */         this.newstmt[paramInt].accessors[i].initMetadata();
/*     */       }
/* 311 */       this.newstmt[paramInt].prepareAccessors();
/*     */ 
/* 313 */       OracleResultSetImpl localOracleResultSetImpl2 = new OracleResultSetImpl(this.newstmt[paramInt].connection, this.newstmt[paramInt]);
/*     */ 
/* 317 */       localOracleResultSetImpl2.close_statement_on_close = true;
/* 318 */       this.newstmt[paramInt].currentResultSet = localOracleResultSetImpl2;
/* 319 */       localOracleResultSetImpl1 = localOracleResultSetImpl2;
/*     */     }
/*     */     else
/*     */     {
/* 323 */       throw new SQLException("Cursor is closed.");
/*     */     }
/*     */ 
/* 326 */     return localOracleResultSetImpl1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CResultSetAccessor
 * JD-Core Version:    0.6.0
 */