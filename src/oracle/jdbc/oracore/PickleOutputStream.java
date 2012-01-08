/*    */ package oracle.jdbc.oracore;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ public class PickleOutputStream extends ByteArrayOutputStream
/*    */ {
/* 69 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*    */ 
/*    */   public PickleOutputStream()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PickleOutputStream(int paramInt)
/*    */   {
/* 26 */     super(paramInt);
/*    */   }
/*    */ 
/*    */   public synchronized int offset()
/*    */   {
/* 31 */     return this.count;
/*    */   }
/*    */ 
/*    */   public synchronized void overwrite(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*    */   {
/* 38 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length) || (paramInt3 < 0) || (paramInt2 + paramInt3 > paramArrayOfByte.length) || (paramInt2 + paramInt3 < 0) || (paramInt1 + paramInt3 > this.buf.length))
/*    */     {
/* 41 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 43 */     if (paramInt3 == 0)
/*    */     {
/* 45 */       return;
/*    */     }
/*    */ 
/* 48 */     for (int i = 0; i < paramInt3; i++)
/*    */     {
/* 50 */       this.buf[(paramInt1 + i)] = paramArrayOfByte[(paramInt2 + i)];
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.PickleOutputStream
 * JD-Core Version:    0.6.0
 */