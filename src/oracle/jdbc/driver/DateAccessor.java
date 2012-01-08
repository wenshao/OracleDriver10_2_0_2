/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ 
/*     */ class DateAccessor extends DateTimeCommonAccessor
/*     */ {
/*     */   static final int maxLength = 7;
/*     */ 
/*     */   DateAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/*  29 */     init(paramOracleStatement, 12, 12, paramShort, paramBoolean);
/*  30 */     initForDataAccess(paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */   DateAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort)
/*     */     throws SQLException
/*     */   {
/*  37 */     init(paramOracleStatement, 12, 12, paramShort, false);
/*  38 */     initForDescribe(12, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, null);
/*     */ 
/*  40 */     initForDataAccess(0, paramInt1, null);
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  46 */     if (paramInt1 != 0) {
/*  47 */       this.externalType = paramInt1;
/*     */     }
/*  49 */     this.internalTypeMaxLength = 7;
/*     */ 
/*  51 */     if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
/*  52 */       this.internalTypeMaxLength = paramInt2;
/*     */     }
/*  54 */     this.byteLength = this.internalTypeMaxLength;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  60 */     String str = null;
/*     */ 
/*  62 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  67 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  74 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/*  76 */       int i = this.columnIndex + this.byteLength * paramInt;
/*  77 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/*  80 */       str = j + "-" + toStr(this.rowSpaceByte[(2 + i)]) + "-" + toStr(this.rowSpaceByte[(3 + i)]) + " " + toStr(this.rowSpaceByte[(4 + i)] - 1) + ":" + toStr(this.rowSpaceByte[(5 + i)] - 1) + ":" + toStr(this.rowSpaceByte[(6 + i)] - 1) + ".0";
/*     */     }
/*     */ 
/*  87 */     return str;
/*     */   }
/*     */ 
/*     */   Timestamp getTimestamp(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  93 */     Timestamp localTimestamp = null;
/*     */ 
/*  95 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 100 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 107 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 109 */       int i = this.columnIndex + this.byteLength * paramInt;
/*     */ 
/* 113 */       TimeZone localTimeZone = this.statement.getDefaultTimeZone();
/*     */ 
/* 116 */       int j = ((this.rowSpaceByte[(0 + i)] & 0xFF) - 100) * 100 + (this.rowSpaceByte[(1 + i)] & 0xFF) - 100;
/*     */ 
/* 120 */       if (j <= 0) {
/* 121 */         j++;
/*     */       }
/* 123 */       localTimestamp = new Timestamp(getMillis(j, oracleMonth(i), oracleDay(i), oracleTime(i), localTimeZone));
/*     */     }
/*     */ 
/* 127 */     return localTimestamp;
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 133 */     Object localObject = null;
/*     */ 
/* 135 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 140 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 146 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 148 */       if (this.externalType == 0)
/*     */       {
/* 151 */         if (this.statement.connection.v8Compatible)
/* 152 */           localObject = getTimestamp(paramInt);
/*     */         else
/* 154 */           localObject = getDate(paramInt);
/*     */       }
/*     */       else
/*     */       {
/* 158 */         switch (this.externalType)
/*     */         {
/*     */         case 91:
/* 161 */           return getDate(paramInt);
/*     */         case 92:
/* 163 */           return getTime(paramInt);
/*     */         case 93:
/* 165 */           return getTimestamp(paramInt);
/*     */         }
/*     */ 
/* 170 */         DatabaseError.throwSqlException(4);
/*     */ 
/* 172 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 180 */     return localObject;
/*     */   }
/*     */ 
/*     */   Datum getOracleObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 186 */     return getDATE(paramInt);
/*     */   }
/*     */ 
/*     */   Object getObject(int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 192 */     return getObject(paramInt);
/*     */   }
/*     */ 
/*     */   TIMESTAMP getTIMESTAMP(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 198 */     if ((this.statement.connection.v8Compatible != true) || (this.externalType != 93))
/*     */     {
/* 200 */       return super.getTIMESTAMP(paramInt);
/*     */     }
/*     */ 
/* 205 */     TIMESTAMP localTIMESTAMP = null;
/*     */ 
/* 207 */     if (this.rowSpaceIndicator == null)
/*     */     {
/* 212 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/* 219 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 221 */       int i = this.rowSpaceIndicator[(this.lengthIndex + paramInt)];
/* 222 */       int j = this.columnIndex + this.byteLength * paramInt;
/* 223 */       byte[] arrayOfByte = new byte[i];
/*     */ 
/* 225 */       System.arraycopy(this.rowSpaceByte, j, arrayOfByte, 0, i);
/*     */ 
/* 227 */       localTIMESTAMP = new TIMESTAMP(arrayOfByte);
/*     */     }
/*     */ 
/* 230 */     return localTIMESTAMP;
/*     */   }
/*     */ 
/*     */   static String toStr(int paramInt)
/*     */   {
/* 235 */     return paramInt < 10 ? "0" + paramInt : Integer.toString(paramInt);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.DateAccessor
 * JD-Core Version:    0.6.0
 */