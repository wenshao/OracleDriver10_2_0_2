/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T2CInputStream extends OracleInputStream
/*     */ {
/* 186 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   native int t2cGetBytes(long paramLong, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte2, int paramInt3, char[] paramArrayOfChar, int paramInt4, short[] paramArrayOfShort, int paramInt5);
/*     */ 
/*     */   T2CInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
/*     */   {
/* 106 */     super(paramOracleStatement, paramInt, paramAccessor);
/*     */   }
/*     */ 
/*     */   T2CInputStream(int paramInt)
/*     */   {
/* 117 */     super(null, paramInt, null);
/*     */   }
/*     */ 
/*     */   public int getBytes()
/*     */     throws IOException
/*     */   {
/* 133 */     synchronized (this.statement.connection)
/*     */     {
/* 135 */       synchronized (this)
/*     */       {
/* 142 */         int i = t2cGetBytes(this.statement.c_state, this.columnIndex, this.buf, this.chunkSize, this.statement.accessors, this.statement.defineBytes, this.statement.accessorByteOffset, this.statement.defineChars, this.statement.accessorCharOffset, this.statement.defineIndicators, this.statement.accessorShortOffset);
/*     */ 
/* 159 */         if (i == -2)
/*     */         {
/*     */           try
/*     */           {
/* 163 */             this.accessor.setNull(this.statement.currentRow);
/*     */           }
/*     */           catch (SQLException localSQLException)
/*     */           {
/* 167 */             DatabaseError.SQLToIOException(localSQLException);
/*     */           }
/*     */ 
/* 170 */           i = 0;
/*     */         }
/*     */ 
/* 176 */         if (i <= 0) {
/* 177 */           i = -1;
/*     */         }
/* 179 */         return i;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CInputStream
 * JD-Core Version:    0.6.0
 */