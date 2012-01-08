/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ 
/*     */ class TimestampAccessor extends DateTimeCommonAccessor
/*     */ {
/*     */   TimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  27 */     init(paramOracleStatement, 180, 180, paramShort, paramBoolean);
/*  28 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   TimestampAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  35 */     init(paramOracleStatement, 180, 180, paramShort, false);
/*  36 */     initForDescribe(180, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  38 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  44 */     if (paramInt1 != 0) {
/*  45 */       this.externalType = paramInt1;
/*     */     }
/*  47 */     this.internalTypeMaxLength = 11;
/*     */ 
/*  49 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  50 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  52 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  58 */     String str = null;
/*     */ 
/*  60 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  65 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  72 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/*  74 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/*  75 */       int j = this.columnIndex + this.byteLength * paramInt;
/*  76 */       int k = ((this.rowSpaceByte[(0 + j)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + j)] & 0xFF) - 100;
/*     */ 
/*  79 */       int m = 0;
/*     */ 
/*  81 */       if (i == 11)
/*     */       {
/*  83 */         m = oracleNanos(j);
/*     */       }
/*     */ 
/*  86 */       str = k + "-" + this.rowSpaceByte[(2 + j)] + "-" + this.rowSpaceByte[(3 + j)] + "." + (this.rowSpaceByte[(4 + j)] - 1) + "." + (this.rowSpaceByte[(5 + j)] - 1) + ". " + (this.rowSpaceByte[(6 + j)] - 1) + ". " + m;
/*     */     }
/*     */ 
/*  97 */     return str;
/*     */   }
/*     */ 
/*     */   Timestamp getTimestamp(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 103 */     Timestamp localTimestamp = null;
/*     */ 
/* 105 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 110 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 117 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 119 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 120 */       int j = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 124 */       TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/*     */ 
/* 127 */       int k = ((this.rowSpaceByte[(0 + j)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + j)] & 0xFF) - 100;
/*     */ 
/* 131 */       if (k <= 0) {
/* 132 */         k++;
/*     */       }
/* 134 */       localTimestamp = new Timestamp(getMillis(k, oracleMonth(j), oracleDay(j), oracleTime(j), localTimeZone));
/*     */ 
/* 137 */       if (i == 11)
/*     */       {
/* 139 */         localTimestamp.setNanos(oracleNanos(j));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 144 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 150 */     Object localObject = null;
/*     */ 
/* 152 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 157 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 163 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 165 */       if (this.externalType == 0)
/*     */       {
/* 167 */         if (this.statement.connection.j2ee13Compliant)
/*     */         {
/* 170 */           localObject = getTimestamp(paramInt);
/*     */         }
/*     */         else
/*     */         {
/* 174 */           localObject = getTIMESTAMP(paramInt);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 179 */         switch (this.externalType)
/*     */         {
/*     */         case 93:
/* 182 */           return getTimestamp(paramInt);
/*     */         }
/*     */ 
/* 187 */         DatabaseError.throwSqlException(4);
/*     */ 
/* 189 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 197 */     return localObject;
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 203 */     return getTIMESTAMP(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 209 */     return getObject(paramInt);
/*     */   }
/*     */ 
/*     */   TIMESTAMP getTIMESTAMP(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 215 */     TIMESTAMP localTIMESTAMP = null;
/*     */ 
/* 217 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 222 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 229 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 231 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 232 */       int j = this.columnIndex + this.byteLength * paramInt;
/* 233 */       byte[] arrayOfByte = new byte[i];
/* 234 */       System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
/* 235 */       localTIMESTAMP = new TIMESTAMP(arrayOfByte);
/*     */     }
/*     */ 
/* 238 */     return localTIMESTAMP;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TimestampAccessor
 * JD-Core Version:    0.6.0
 */