/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public final class CharacterBuffer
/*     */ {
/*     */   CharacterSet charSet;
/*     */   byte[] bytes;
/*     */   int next;
/*     */ 
/*     */   public CharacterBuffer(CharacterSet paramCharacterSet)
/*     */   {
/*  91 */     this.charSet = paramCharacterSet;
/*  92 */     this.next = 0;
/*     */ 
/*  95 */     this.bytes = new byte[32];
/*     */   }
/*     */ 
/*     */   public void append(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 106 */     this.charSet.encode(this, paramInt);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 116 */     return CharacterSet.useOrCopy(this.bytes, 0, this.next);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterBuffer
 * JD-Core Version:    0.6.0
 */