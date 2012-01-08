/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public final class CharacterWalker
/*     */ {
/*     */   CharacterSet charSet;
/*     */   byte[] bytes;
/*     */   int next;
/*     */   int end;
/*     */   int shiftstate;
/*     */ 
/*     */   public CharacterWalker(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 109 */     this.charSet = paramCharacterSet;
/* 110 */     this.bytes = paramArrayOfByte;
/* 111 */     this.next = paramInt1;
/* 112 */     this.end = (paramInt1 + paramInt2);
/*     */ 
/* 114 */     if (this.next < 0)
/*     */     {
/* 116 */       this.next = 0;
/*     */     }
/*     */ 
/* 119 */     if (this.end > paramArrayOfByte.length)
/*     */     {
/* 121 */       this.end = paramArrayOfByte.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int nextCharacter()
/*     */     throws NoSuchElementException
/*     */   {
/*     */     try
/*     */     {
/* 137 */       return this.charSet.decode(this);
/*     */     }
/*     */     catch (SQLException localSQLException) {
/*     */     }
/* 141 */     throw new NoSuchElementException(localSQLException.getMessage());
/*     */   }
/*     */ 
/*     */   public boolean hasMoreCharacters()
/*     */   {
/* 152 */     return this.next < this.end;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterWalker
 * JD-Core Version:    0.6.0
 */