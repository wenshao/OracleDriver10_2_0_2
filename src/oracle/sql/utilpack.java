/*     */ package oracle.sql;
/*     */ 
/*     */ class utilpack
/*     */ {
/* 106 */   private static int INTYM3BYTE = 24;
/* 107 */   private static int INTYM2BYTE = 16;
/* 108 */   private static int INTYM1BYTE = 8;
/*     */ 
/*     */   protected static int LEFTSHIFTFIRSTNIBBLE(byte paramByte)
/*     */   {
/*  31 */     int i = 0;
/*     */ 
/*  33 */     i = (paramByte & 0xFF) << INTYM3BYTE;
/*     */ 
/*  35 */     return i;
/*     */   }
/*     */ 
/*     */   protected static int LEFTSHIFTSECONDNIBBLE(byte paramByte)
/*     */   {
/*  42 */     int i = 0;
/*     */ 
/*  44 */     i = (paramByte & 0xFF) << INTYM2BYTE;
/*     */ 
/*  46 */     return i;
/*     */   }
/*     */ 
/*     */   protected static int LEFTSHIFTTHIRDNIBBLE(byte paramByte)
/*     */   {
/*  52 */     int i = 0;
/*     */ 
/*  54 */     i = (paramByte & 0xFF) << INTYM1BYTE;
/*     */ 
/*  56 */     return i;
/*     */   }
/*     */ 
/*     */   protected static byte RIGHTSHIFTFIRSTNIBBLE(int paramInt)
/*     */   {
/*  63 */     int i = 0;
/*     */ 
/*  66 */     i = (byte)(paramInt >> INTYM3BYTE & 0xFF);
/*     */ 
/*  68 */     return i;
/*     */   }
/*     */ 
/*     */   protected static byte RIGHTSHIFTSECONDNIBBLE(int paramInt)
/*     */   {
/*  75 */     int i = 0;
/*     */ 
/*  78 */     i = (byte)(paramInt >> INTYM2BYTE & 0xFF);
/*     */ 
/*  80 */     return i;
/*     */   }
/*     */ 
/*     */   protected static byte RIGHTSHIFTTHIRDNIBBLE(int paramInt)
/*     */   {
/*  86 */     int i = 0;
/*     */ 
/*  89 */     i = (byte)(paramInt >> INTYM1BYTE & 0xFF);
/*     */ 
/*  91 */     return i;
/*     */   }
/*     */ 
/*     */   protected static byte RIGHTSHIFTFOURTHNIBBLE(int paramInt)
/*     */   {
/*  98 */     int i = 0;
/*     */ 
/* 101 */     i = (byte)(paramInt & 0xFF);
/*     */ 
/* 103 */     return i;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.utilpack
 * JD-Core Version:    0.6.0
 */