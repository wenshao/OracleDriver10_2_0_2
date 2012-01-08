/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CRefTypeAccessor extends RefTypeAccessor
/*     */ {
/*     */   static final int maxLength = 4000;
/*     */   T4CMAREngine mare;
/* 130 */   final int[] meta = new int[1];
/*     */ 
/* 198 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CRefTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  39 */     super(paramOracleStatement, paramString, paramShort, paramInt, paramBoolean);
/*  40 */     this.mare = paramT4CMAREngine;
/*  41 */     this.byteLength = 4000;
/*     */   }
/*     */ 
/*     */   T4CRefTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  61 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);
/*     */ 
/*  71 */     this.mare = paramT4CMAREngine;
/*  72 */     this.definedColumnType = paramInt7;
/*  73 */     this.definedColumnSize = paramInt8;
/*  74 */     this.byteLength = 4000;
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
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 117 */     String str = super.getString(paramInt);
/*     */ 
/* 119 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 121 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 123 */     return str;
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 147 */     if (this.isUseLess)
/*     */     {
/* 149 */       this.lastRowProcessed += 1;
/*     */ 
/* 151 */       return false;
/*     */     }
/*     */ 
/* 154 */     int i = this.columnIndex + this.lastRowProcessed * this.byteLength;
/*     */ 
/* 158 */     byte[] arrayOfByte = this.mare.unmarshalCLRforREFS();
/*     */ 
/* 160 */     if (arrayOfByte == null) {
/* 161 */       arrayOfByte = new byte[0];
/*     */     }
/* 163 */     this.pickledBytes[this.lastRowProcessed] = arrayOfByte;
/* 164 */     this.meta[0] = arrayOfByte.length;
/*     */ 
/* 166 */     processIndicator(this.meta[0]);
/*     */ 
/* 169 */     int j = this.indicatorIndex + this.lastRowProcessed;
/* 170 */     int k = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 172 */     if (this.meta[0] == 0)
/*     */     {
/* 176 */       this.rowSpaceIndicator[j] = -1;
/* 177 */       this.rowSpaceIndicator[k] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 181 */       this.rowSpaceIndicator[k] = (short)this.meta[0];
/*     */ 
/* 186 */       this.rowSpaceIndicator[j] = 0;
/*     */     }
/*     */ 
/* 189 */     this.lastRowProcessed += 1;
/*     */ 
/* 193 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CRefTypeAccessor
 * JD-Core Version:    0.6.0
 */