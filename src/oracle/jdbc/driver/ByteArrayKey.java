/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ class ByteArrayKey
/*    */ {
/*    */   private byte[] theBytes;
/* 69 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*    */ 
/*    */   public ByteArrayKey(byte[] paramArrayOfByte)
/*    */   {
/* 27 */     this.theBytes = paramArrayOfByte;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 32 */     if (paramObject == this) {
/* 33 */       return true;
/*    */     }
/* 35 */     if (!(paramObject instanceof ByteArrayKey))
/*    */     {
/* 40 */       return false;
/*    */     }
/*    */ 
/* 45 */     byte[] arrayOfByte = ((ByteArrayKey)paramObject).theBytes;
/*    */ 
/* 47 */     if (this.theBytes.length != arrayOfByte.length) {
/* 48 */       return false;
/*    */     }
/* 50 */     for (int i = 0; i < this.theBytes.length; i++) {
/* 51 */       if (this.theBytes[i] != arrayOfByte[i])
/* 52 */         return false;
/*    */     }
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 59 */     int i = 0;
/*    */ 
/* 61 */     for (int j = 0; j < this.theBytes.length; j++) {
/* 62 */       i += this.theBytes[j];
/*    */     }
/* 64 */     return i;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ByteArrayKey
 * JD-Core Version:    0.6.0
 */