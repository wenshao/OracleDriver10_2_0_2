/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CNamedTypeAccessor extends NamedTypeAccessor
/*     */ {
/*     */   static final int maxLength = 2147483647;
/*  25 */   final int[] meta = new int[1];
/*     */   T4CMAREngine mare;
/* 181 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   void processIndicator(int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/*  33 */     if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
/*     */     {
/*  40 */       this.mare.unmarshalUB2();
/*  41 */       this.mare.unmarshalUB2();
/*     */     }
/*  43 */     else if (this.mare.versionNumber < 9200)
/*     */     {
/*  47 */       this.mare.unmarshalSB2();
/*     */ 
/*  49 */       if ((this.statement.sqlKind != 1) && (this.statement.sqlKind != 4))
/*     */       {
/*  51 */         this.mare.unmarshalSB2();
/*     */       }
/*  53 */     } else if ((this.statement.sqlKind == 1) || (this.statement.sqlKind == 4) || (this.isDMLReturnedParam))
/*     */     {
/*  56 */       this.mare.processIndicator(paramInt <= 0, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   String getString(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  68 */     String str = super.getString(paramInt);
/*     */ 
/*  70 */     if ((str != null) && (this.definedColumnSize > 0) && (str.length() > this.definedColumnSize))
/*     */     {
/*  72 */       str = str.substring(0, this.definedColumnSize);
/*     */     }
/*  74 */     return str;
/*     */   }
/*     */ 
/*     */   T4CNamedTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  83 */     super(paramOracleStatement, paramString, paramShort, paramInt, paramBoolean);
/*     */ 
/*  85 */     this.mare = paramT4CMAREngine;
/*     */   }
/*     */ 
/*     */   T4CNamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException
/*     */   {
/*  97 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);
/*     */ 
/* 100 */     this.mare = paramT4CMAREngine;
/* 101 */     this.definedColumnType = paramInt7;
/* 102 */     this.definedColumnSize = paramInt8;
/*     */   }
/*     */ 
/*     */   boolean unmarshalOneRow()
/*     */     throws SQLException, IOException
/*     */   {
/* 123 */     if (this.isUseLess)
/*     */     {
/* 125 */       this.lastRowProcessed += 1;
/*     */ 
/* 127 */       return false;
/*     */     }
/*     */ 
/* 130 */     byte[] arrayOfByte1 = this.mare.unmarshalDALC();
/*     */ 
/* 132 */     byte[] arrayOfByte2 = this.mare.unmarshalDALC();
/*     */ 
/* 134 */     byte[] arrayOfByte3 = this.mare.unmarshalDALC();
/*     */ 
/* 136 */     int i = this.mare.unmarshalUB2();
/* 137 */     long l = this.mare.unmarshalUB4();
/* 138 */     int j = this.mare.unmarshalUB2();
/*     */ 
/* 140 */     byte[] arrayOfByte4 = null;
/*     */ 
/* 142 */     if (l > 0L)
/* 143 */       arrayOfByte4 = this.mare.unmarshalCLR((int)l, this.meta);
/*     */     else {
/* 145 */       arrayOfByte4 = new byte[0];
/*     */     }
/* 147 */     this.pickledBytes[this.lastRowProcessed] = arrayOfByte4;
/*     */ 
/* 149 */     processIndicator(this.meta[0]);
/*     */ 
/* 152 */     int k = this.indicatorIndex + this.lastRowProcessed;
/* 153 */     int m = this.lengthIndex + this.lastRowProcessed;
/*     */ 
/* 155 */     if (this.meta[0] == 0)
/*     */     {
/* 159 */       this.rowSpaceIndicator[k] = -1;
/* 160 */       this.rowSpaceIndicator[m] = 0;
/*     */     }
/*     */     else
/*     */     {
/* 164 */       this.rowSpaceIndicator[m] = (short)this.meta[0];
/*     */ 
/* 169 */       this.rowSpaceIndicator[k] = 0;
/*     */     }
/*     */ 
/* 172 */     this.lastRowProcessed += 1;
/*     */ 
/* 176 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CNamedTypeAccessor
 * JD-Core Version:    0.6.0
 */