/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.oracore.OracleType;
/*     */ 
/*     */ abstract class TypeAccessor extends Accessor
/*     */ {
/*     */   byte[][] pickledBytes;
/* 115 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   abstract OracleType otypeFromName(String paramString)
/*     */     throws SQLException;
/*     */ 
/*     */   void initForDescribe(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort, String paramString)
/*     */     throws SQLException
/*     */   {
/*  29 */     this.describeTypeName = paramString;
/*     */ 
/*  31 */     initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt4, paramInt5, paramInt3, paramInt6, paramInt7, paramShort);
/*     */   }
/*     */ 
/*     */   void setOffsets(int paramInt)
/*     */   {
/*  37 */     if (!this.outBind)
/*     */     {
/*  39 */       this.columnIndex = this.statement.defineByteSubRange;
/*  40 */       this.statement.defineByteSubRange = (this.columnIndex + paramInt * this.byteLength);
/*     */     }
/*     */ 
/*  43 */     if ((this.pickledBytes == null) || (this.pickledBytes.length < paramInt))
/*  44 */       this.pickledBytes = new byte[paramInt][];
/*     */   }
/*     */ 
/*     */   byte[] pickledBytes(int paramInt)
/*     */   {
/*  50 */     return this.pickledBytes[paramInt];
/*     */   }
/*     */ 
/*     */   void initForDataAccess(int paramInt1, int paramInt2, String paramString)
/*     */     throws SQLException
/*     */   {
/*  56 */     if (paramInt1 != 0) {
/*  57 */       this.externalType = paramInt1;
/*     */     }
/*  59 */     this.internalTypeMaxLength = 0;
/*  60 */     this.internalTypeName = paramString;
/*     */   }
/*     */ 
/*     */   void initMetadata()
/*     */     throws SQLException
/*     */   {
/*  75 */     if ((this.describeOtype == null) && (this.describeTypeName != null)) {
/*  76 */       this.describeOtype = otypeFromName(this.describeTypeName);
/*     */     }
/*  78 */     if ((this.internalOtype == null) && (this.internalTypeName != null))
/*  79 */       this.internalOtype = otypeFromName(this.internalTypeName);
/*     */   }
/*     */ 
/*     */   byte[] getBytes(int paramInt) throws SQLException
/*     */   {
/*  84 */     byte[] arrayOfByte1 = null;
/*     */ 
/*  86 */     if (this.rowSpaceIndicator == null)
/*     */     {
/*  91 */       DatabaseError.throwSqlException(21);
/*     */     }
/*     */ 
/*  98 */     if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
/*     */     {
/* 100 */       byte[] arrayOfByte2 = pickledBytes(paramInt);
/* 101 */       int i = arrayOfByte2.length;
/*     */ 
/* 103 */       arrayOfByte1 = new byte[i];
/*     */ 
/* 105 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, i);
/*     */     }
/*     */ 
/* 110 */     return arrayOfByte1;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TypeAccessor
 * JD-Core Version:    0.6.0
 */