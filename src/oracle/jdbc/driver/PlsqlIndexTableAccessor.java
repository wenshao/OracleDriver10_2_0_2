/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CHAR;
/*     */ import oracle.sql.CharacterSet;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.NUMBER;
/*     */ 
/*     */ class PlsqlIndexTableAccessor extends Accessor
/*     */ {
/*     */   int elementInternalType;
/*     */   int maxNumberOfElements;
/*     */   int elementMaxLen;
/*     */   int ibtValueIndex;
/*     */   int ibtIndicatorIndex;
/*     */   int ibtLengthIndex;
/*     */   int ibtMetaIndex;
/*     */   int ibtByteLength;
/*     */   int ibtCharLength;
/*     */ 
/*     */   PlsqlIndexTableAccessor(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  43 */     init(paramOracleStatement, 998, 998, paramShort, paramBoolean);
/*     */ 
/*  45 */     this.elementInternalType = paramInt2;
/*  46 */     this.maxNumberOfElements = paramInt4;
/*  47 */     this.elementMaxLen = paramInt3;
/*     */ 
/*  49 */     initForDataAccess(paramInt1, paramInt3, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  55 */     if (paramInt1 != 0) {
/*  56 */       this.externalType = paramInt1;
/*     */     }
/*  58 */     switch (this.elementInternalType)
/*     */     {
/*     */     case 1:
/*     */     case 96:
/*  64 */       this.internalTypeMaxLength = 2000;
/*     */ 
/*  69 */       this.elementMaxLen = ((paramInt2 == 0 ? this.internalTypeMaxLength : paramInt2) + 1);
/*  70 */       this.ibtCharLength = (this.elementMaxLen * this.maxNumberOfElements);
/*  71 */       this.elementInternalType = 9;
/*     */ 
/*  73 */       break;
/*     */     case 6:
/*  76 */       this.internalTypeMaxLength = 21;
/*  77 */       this.elementMaxLen = (this.internalTypeMaxLength + 1);
/*  78 */       this.ibtByteLength = (this.elementMaxLen * this.maxNumberOfElements);
/*     */ 
/*  80 */       break;
/*     */     default:
/*  83 */       DatabaseError.throwSqlException(97);
/*     */     }
/*     */   }
/*     */ 
/*     */   Object[] getPlsqlIndexTable(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  98 */     Object localObject = null;
/*  99 */     short[] arrayOfShort = this.statement.ibtBindIndicators;
/* 100 */     int i = (arrayOfShort[(this.ibtMetaIndex + 4)] >> 16) + (arrayOfShort[(this.ibtMetaIndex + 5)] & 0xFFFF);
/*     */ 
/* 103 */     int j = this.ibtValueIndex;
/*     */     char[] arrayOfChar;
/*     */     int k;
/* 106 */     switch (this.elementInternalType)
/*     */     {
/*     */     case 9:
/* 110 */       localObject = new String[i];
/* 111 */       arrayOfChar = this.statement.ibtBindChars;
/*     */ 
/* 113 */       for (k = 0; k < i; )
/*     */       {
/* 115 */         if (arrayOfShort[(this.ibtIndicatorIndex + k)] == -1)
/*     */         {
/* 117 */           localObject[k] = null;
/*     */         }
/*     */         else
/*     */         {
/* 121 */           localObject[k] = new String(arrayOfChar, j + 1, arrayOfChar[j] >> '\001');
/*     */         }
/*     */ 
/* 126 */         j += this.elementMaxLen;
/*     */ 
/* 113 */         k++; continue;
/*     */ 
/* 132 */         localObject = new BigDecimal[i];
/* 133 */         arrayOfByte1 = this.statement.ibtBindBytes;
/*     */ 
/* 135 */         for (m = 0; m < i; )
/*     */         {
/* 137 */           if (arrayOfShort[(this.ibtIndicatorIndex + m)] == -1)
/*     */           {
/* 139 */             localObject[m] = null;
/*     */           }
/*     */           else
/*     */           {
/* 143 */             int n = arrayOfByte1[j];
/* 144 */             byte[] arrayOfByte2 = new byte[n];
/*     */ 
/* 146 */             System.arraycopy(arrayOfByte1, j + 1, arrayOfByte2, 0, n);
/*     */ 
/* 148 */             localObject[m] = NUMBER.toBigDecimal(arrayOfByte2);
/*     */           }
/*     */ 
/* 151 */           j += this.elementMaxLen;
/*     */ 
/* 135 */           m++; continue;
/*     */ 
/* 157 */           DatabaseError.throwSqlException(97);
/*     */         }
/*     */       }
/*     */     case 6:
/*     */     }
/*     */     byte[] arrayOfByte1;
/*     */     int m;
/* 160 */     return (Object)localObject;
/*     */   }
/*     */ 
/*     */   Datum[] getOraclePlsqlIndexTable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 174 */     Object localObject = null;
/* 175 */     short[] arrayOfShort = this.statement.ibtBindIndicators;
/* 176 */     int i = (arrayOfShort[(this.ibtMetaIndex + 4)] >> 16) + (arrayOfShort[(this.ibtMetaIndex + 5)] & 0xFFFF);
/*     */ 
/* 179 */     int j = this.ibtValueIndex;
/*     */     CharacterSet localCharacterSet;
/*     */     char[] arrayOfChar;
/*     */     int k;
/* 182 */     switch (this.elementInternalType)
/*     */     {
/*     */     case 9:
/* 186 */       localObject = new CHAR[i];
/*     */ 
/* 188 */       localCharacterSet = CharacterSet.make(2000);
/* 189 */       arrayOfChar = this.statement.ibtBindChars;
/*     */ 
/* 191 */       for (k = 0; k < i; )
/*     */       {
/* 193 */         if (arrayOfShort[(this.ibtIndicatorIndex + k)] == -1)
/*     */         {
/* 195 */           localObject[k] = null;
/*     */         }
/*     */         else
/*     */         {
/* 199 */           m = arrayOfChar[j];
/* 200 */           byte[] arrayOfByte2 = new byte[m];
/*     */ 
/* 202 */           DBConversion.javaCharsToUcs2Bytes(arrayOfChar, j + 1, arrayOfByte2, 0, m >> 1);
/*     */ 
/* 205 */           localObject[k] = new CHAR(arrayOfByte2, localCharacterSet);
/*     */         }
/*     */ 
/* 208 */         j += this.elementMaxLen;
/*     */ 
/* 191 */         k++; continue;
/*     */ 
/* 214 */         localObject = new NUMBER[i];
/* 215 */         arrayOfByte1 = this.statement.ibtBindBytes;
/*     */ 
/* 217 */         for (m = 0; m < i; )
/*     */         {
/* 219 */           if (arrayOfShort[(this.ibtIndicatorIndex + m)] == -1)
/*     */           {
/* 221 */             localObject[m] = null;
/*     */           }
/*     */           else
/*     */           {
/* 225 */             int n = arrayOfByte1[j];
/* 226 */             byte[] arrayOfByte3 = new byte[n];
/*     */ 
/* 228 */             System.arraycopy(arrayOfByte1, j + 1, arrayOfByte3, 0, n);
/*     */ 
/* 230 */             localObject[m] = new NUMBER(arrayOfByte3);
/*     */           }
/*     */ 
/* 233 */           j += this.elementMaxLen;
/*     */ 
/* 217 */           m++; continue;
/*     */ 
/* 239 */           DatabaseError.throwSqlException(97);
/*     */         }
/*     */       }
/*     */     case 6:
/*     */     }
/*     */     int m;
/*     */     byte[] arrayOfByte1;
/* 242 */     return (Datum)localObject;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.PlsqlIndexTableAccessor
 * JD-Core Version:    0.6.0
 */