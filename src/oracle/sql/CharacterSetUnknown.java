/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ class CharacterSetUnknown extends CharacterSet
/*     */   implements CharacterRepConstants
/*     */ {
/*     */   CharacterSetUnknown(int paramInt)
/*     */   {
/* 144 */     super(paramInt);
/*     */ 
/* 146 */     this.rep = (1024 + paramInt);
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 151 */     return paramCharacterSet.getOracleId() != getOracleId();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 156 */     return paramCharacterSet.getOracleId() == getOracleId();
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 161 */     return "???";
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 167 */     failCharsetUnknown(this);
/*     */ 
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString) throws SQLException
/*     */   {
/* 174 */     failCharsetUnknown(this);
/*     */ 
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 181 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 187 */     if (paramCharacterSet.getOracleId() == getOracleId())
/*     */     {
/* 189 */       return useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */ 
/* 193 */     failCharsetUnknown(this);
/*     */ 
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */   int decode(CharacterWalker paramCharacterWalker) throws SQLException
/*     */   {
/* 201 */     failCharsetUnknown(this);
/*     */ 
/* 203 */     return 0;
/*     */   }
/*     */ 
/*     */   void encode(CharacterBuffer paramCharacterBuffer, int paramInt) throws SQLException
/*     */   {
/* 208 */     failCharsetUnknown(this);
/*     */   }
/*     */ 
/*     */   static void failCharsetUnknown(CharacterSet paramCharacterSet)
/*     */     throws SQLException
/*     */   {
/* 215 */     DatabaseError.throwSqlException(56, paramCharacterSet);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetUnknown
 * JD-Core Version:    0.6.0
 */