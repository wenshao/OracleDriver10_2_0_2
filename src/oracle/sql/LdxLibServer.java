/*    */ package oracle.sql;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ class LdxLibServer
/*    */   implements LdxLib
/*    */ {
/*    */   public native byte[] ldxadm(byte[] paramArrayOfByte, int paramInt)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxads(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native int ldxchk(byte[] paramArrayOfByte)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxdfd(int paramInt1, int paramInt2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native void ldxdtd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native String ldxdts(byte[] paramArrayOfByte, String paramString1, String paramString2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native String ldxdts(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxsto(String paramString1, String paramString2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxdyf(byte[] paramArrayOfByte)
/*    */     throws SQLException;
/*    */ 
/*    */   public native void ldxftd(byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxgdt()
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxldd(byte[] paramArrayOfByte)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxnxd(byte[] paramArrayOfByte, int paramInt)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxrnd(byte[] paramArrayOfByte, String paramString)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxsbm(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native void ldxsub(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxstd(String paramString1, String paramString2, String paramString3)
/*    */     throws SQLException;
/*    */ 
/*    */   public native byte[] ldxtrn(byte[] paramArrayOfByte, String paramString)
/*    */     throws SQLException;
/*    */ 
/*    */   static
/*    */   {
/* 44 */     LoadCorejava.init();
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.LdxLibServer
 * JD-Core Version:    0.6.0
 */