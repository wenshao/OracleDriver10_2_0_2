/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CPlsqlIndexTableAccessor extends PlsqlIndexTableAccessor
/*     */ {
/*     */   T4CMAREngine mare;
/*  80 */   final int[] meta = new int[1];
/*  81 */   final int[] tmp = new int[1];
/*     */ 
/* 288 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CPlsqlIndexTableAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  35 */     super(paramOracleStatement, paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramBoolean);
/*     */ 
/*  38 */     calculateSizeTmpByteArray();
/*     */ 
/*  40 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  50 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  57 */       this.mare.unmarshalUB2();
/*  58 */       this.mare.unmarshalUB2();
/*     */     }
/*  60 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  64 */       this.mare.unmarshalSB2();
/*     */ 
/*  66 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  68 */         this.mare.unmarshalSB2();
/*     */       }
/*  70 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  73 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 105 */     if (this.isUseLess)
/*     */     {
/* 107 */       this.lastRowProcessed += 1;
/*     */ 
/* 109 */       return false;
/*     */     }
/*     */ 
/* 114 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 118 */       byte[] arrayOfByte1 = new byte[16000];
/*     */ 
/* 120 */       this.mare.unmarshalCLR(arrayOfByte1, 0, this.meta);
/* 121 */       processIndicator(this.meta[0]);
/*     */ 
/* 123 */       this.lastRowProcessed += 1;
/*     */ 
/* 125 */       return false;
/*     */     }
/*     */ 
/* 128 */     int i = this.indicatorIndex + this.lastRowProcessed;
/* 129 */     int j = this.lengthIndex + this.lastRowProcessed;
/* 130 */     byte[] arrayOfByte2 = this.statement.ibtBindBytes;
/* 131 */     char[] arrayOfChar = this.statement.ibtBindChars;
/* 132 */     short[] arrayOfShort = this.statement.ibtBindIndicators;
/* 133 */     int k = this.statement.ibtBindByteOffset;
/* 134 */     int m = this.statement.ibtBindCharOffset;
/* 135 */     int n = this.statement.ibtBindIndicatorOffset;
/*     */ 
/* 139 */     if (this.isNullByDescribe)
/*     */     {
/* 141 */       this.rowSpaceIndicator[i] = -1;
/* 142 */       this.rowSpaceIndicator[j] = 0;
/* 143 */       this.lastRowProcessed += 1;
/*     */ 
/* 145 */       if (this.mare.versionNumber < 9200) {
/* 146 */         processIndicator(0);
/*     */       }
/*     */ 
/* 151 */       return false;
/*     */     }
/*     */ 
/* 154 */     int i1 = (int)this.mare.unmarshalUB4();
/*     */ 
/* 156 */     arrayOfShort[(this.ibtMetaIndex + 4)] = (short)(i1 & 0xFFFFFFFF & 0xFFFF);
/*     */ 
/* 158 */     arrayOfShort[(this.ibtMetaIndex + 5)] = (short)(i1 & 0xFFFF);
/*     */     byte[] arrayOfByte3;
/*     */     int i3;
/* 160 */     if ((this.elementInternalType == 9) || (this.elementInternalType == 96) || (this.elementInternalType == 1))
/*     */     {
/* 167 */       arrayOfByte3 = this.statement.tmpByteArray;
/*     */ 
/* 169 */       for (i3 = 0; i3 < i1; )
/*     */       {
/* 171 */         int i4 = this.ibtValueIndex + this.elementMaxLen * i3;
/*     */ 
/* 173 */         this.mare.unmarshalCLR(arrayOfByte3, 0, this.meta);
/*     */ 
/* 175 */         this.tmp[0] = this.meta[0];
/*     */ 
/* 177 */         int i5 = this.statement.connection.conversion.CHARBytesToJavaChars(arrayOfByte3, 0, arrayOfChar, i4 + 1, this.tmp, arrayOfChar.length - i4 - 1);
/*     */ 
/* 182 */         arrayOfChar[i4] = (char)(i5 * 2);
/*     */ 
/* 186 */         processIndicator(this.meta[0]);
/*     */ 
/* 188 */         if (this.meta[0] == 0)
/*     */         {
/* 192 */           arrayOfShort[(this.ibtIndicatorIndex + i3)] = -1;
/* 193 */           arrayOfShort[(this.ibtLengthIndex + i3)] = 0;
/*     */         }
/*     */         else
/*     */         {
/* 197 */           arrayOfShort[(this.ibtLengthIndex + i3)] = (short)(this.meta[0] * 2);
/*     */ 
/* 201 */           arrayOfShort[(this.ibtIndicatorIndex + i3)] = 0;
/*     */         }
/* 169 */         i3++; continue;
/*     */ 
/* 209 */         for (int i2 = 0; i2 < i1; i2++)
/*     */         {
/* 211 */           i3 = this.ibtValueIndex + this.elementMaxLen * i2;
/*     */ 
/* 213 */           this.mare.unmarshalCLR(arrayOfByte2, i3 + 1, this.meta);
/*     */ 
/* 215 */           arrayOfByte2[i3] = (byte)this.meta[0];
/*     */ 
/* 217 */           processIndicator(this.meta[0]);
/*     */ 
/* 219 */           if (this.meta[0] == 0)
/*     */           {
/* 223 */             arrayOfShort[(this.ibtIndicatorIndex + i2)] = -1;
/* 224 */             arrayOfShort[(this.ibtLengthIndex + i2)] = 0;
/*     */           }
/*     */           else
/*     */           {
/* 228 */             arrayOfShort[(this.ibtLengthIndex + i2)] = (short)this.meta[0];
/* 229 */             arrayOfShort[(this.ibtIndicatorIndex + i2)] = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 234 */     this.lastRowProcessed += 1;
/*     */ 
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */   void calculateSizeTmpByteArray()
/*     */   {
/* 254 */     if ((this.elementInternalType == 9) || (this.elementInternalType == 96) || (this.elementInternalType == 1))
/*     */     {
/* 258 */       int i = this.ibtCharLength * this.statement.connection.conversion.cMaxCharSize / this.maxNumberOfElements;
/*     */ 
/* 262 */       if (this.statement.sizeTmpByteArray < i)
/* 263 */         this.statement.sizeTmpByteArray = i;
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 276 */     String str = super.getString(paramInt);
/*     */ 
/* 278 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/* 280 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/* 282 */     return str;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CPlsqlIndexTableAccessor
 * JD-Core Version:    0.6.0
 */