/*    */ package oracle.sql;
/*    */ 
/*    */ import java.sql.Timestamp;
/*    */ 
/*    */ public class OffsetDST
/*    */ {
/*    */   private Timestamp timestamp;
/*    */   private int offset;
/*    */   private byte DSTflag;
/*    */ 
/*    */   public OffsetDST(Timestamp paramTimestamp, int paramInt, byte paramByte)
/*    */   {
/* 38 */     this.timestamp = paramTimestamp;
/* 39 */     this.offset = paramInt;
/* 40 */     this.DSTflag = paramByte;
/*    */   }
/*    */ 
/*    */   public OffsetDST()
/*    */   {
/* 47 */     this.timestamp = new Timestamp(0L);
/* 48 */     this.offset = 0;
/* 49 */     this.DSTflag = 0;
/*    */   }
/*    */ 
/*    */   public int getOFFSET()
/*    */   {
/* 58 */     return this.offset;
/*    */   }
/*    */ 
/*    */   public byte getDSTFLAG()
/*    */   {
/* 63 */     return this.DSTflag;
/*    */   }
/*    */ 
/*    */   public Timestamp getTimestamp()
/*    */   {
/* 68 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   public void setOFFSET(int paramInt)
/*    */   {
/* 74 */     this.offset = paramInt;
/*    */   }
/*    */ 
/*    */   public void setDSTFLAG(byte paramByte)
/*    */   {
/* 79 */     this.DSTflag = paramByte;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.OffsetDST
 * JD-Core Version:    0.6.0
 */