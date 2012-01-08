/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class TDSReader
/*     */ {
/*     */   static final int KOPT_NONE_FINAL_TYPE = 1;
/*     */   static final int KOPT_JAVA_OBJECT = 2;
/*     */   int nullOffset;
/*     */   int ldsOffset;
/*     */   long fixedDataSize;
/*     */   Vector patches;
/*     */   byte[] tds;
/*     */   int beginIndex;
/*     */   int index;
/* 277 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   TDSReader(byte[] paramArrayOfByte, long paramLong)
/*     */   {
/*  48 */     this.nullOffset = 0;
/*  49 */     this.ldsOffset = 0;
/*  50 */     this.fixedDataSize = 0L;
/*  51 */     this.patches = null;
/*     */ 
/*  53 */     this.tds = paramArrayOfByte;
/*  54 */     this.beginIndex = (int)paramLong;
/*  55 */     this.index = (int)paramLong;
/*     */   }
/*     */ 
/*     */   void skipBytes(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  68 */     this.index += paramInt;
/*     */   }
/*     */ 
/*     */   void checkNextByte(byte paramByte)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/*  80 */       if (paramByte != this.tds[this.index]) {
/*  81 */         DatabaseError.throwSqlException(47, "parseTDS");
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*  88 */       this.index += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   byte readByte()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 101 */       int i = this.tds[this.index];
/*     */       return i; } finally { this.index += 1; } throw localObject;
/*     */   }
/*     */ 
/*     */   int readUnsignedByte()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 120 */       int i = this.tds[this.index] & 0xFF;
/*     */       return i; } finally { this.index += 1; } throw localObject;
/*     */   }
/*     */ 
/*     */   short readShort()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 140 */       int i = (short)((this.tds[this.index] & 0xFF) * 256 + (this.tds[(this.index + 1)] & 0xFF));
/*     */       return i; } finally { this.index += 2; } throw localObject;
/*     */   }
/*     */ 
/*     */   long readLong()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 161 */       long l = (((this.tds[this.index] & 0xFF) * 256 + (this.tds[(this.index + 1)] & 0xFF)) * 256 + (this.tds[(this.index + 2)] & 0xFF)) * 256 + (this.tds[(this.index + 3)] & 0xFF);
/*     */       return l; } finally { this.index += 4; } throw localObject;
/*     */   }
/*     */ 
/*     */   void addNormalPatch(long paramLong, byte paramByte, OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/* 184 */     addPatch(new TDSPatch(0, paramOracleType, paramLong, paramByte));
/*     */   }
/*     */ 
/*     */   void addSimplePatch(long paramLong, OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/* 195 */     addPatch(new TDSPatch(1, paramOracleType, paramLong, 0));
/*     */   }
/*     */ 
/*     */   void addPatch(TDSPatch paramTDSPatch) throws SQLException
/*     */   {
/* 200 */     if (this.patches == null) {
/* 201 */       this.patches = new Vector(5);
/*     */     }
/* 203 */     this.patches.addElement(paramTDSPatch);
/*     */   }
/*     */ 
/*     */   long moveToPatchPos(TDSPatch paramTDSPatch) throws SQLException
/*     */   {
/* 208 */     long l = paramTDSPatch.getPosition();
/*     */ 
/* 210 */     if (this.beginIndex + l > this.tds.length) {
/* 211 */       DatabaseError.throwSqlException(47, "parseTDS");
/*     */     }
/*     */ 
/* 214 */     skip_to(l);
/*     */ 
/* 216 */     return l;
/*     */   }
/*     */ 
/*     */   TDSPatch getNextPatch()
/*     */     throws SQLException
/*     */   {
/* 224 */     TDSPatch localTDSPatch = null;
/*     */ 
/* 226 */     if (this.patches != null)
/*     */     {
/* 228 */       if (this.patches.size() > 0)
/*     */       {
/* 230 */         localTDSPatch = (TDSPatch)this.patches.firstElement();
/*     */ 
/* 232 */         this.patches.removeElementAt(0);
/*     */       }
/*     */     }
/*     */ 
/* 236 */     return localTDSPatch;
/*     */   }
/*     */ 
/*     */   void skip_to(long paramLong)
/*     */   {
/* 242 */     this.index = (this.beginIndex + (int)paramLong);
/*     */   }
/*     */ 
/*     */   long offset()
/*     */     throws SQLException
/*     */   {
/* 248 */     return this.index - this.beginIndex;
/*     */   }
/*     */ 
/*     */   long absoluteOffset() throws SQLException
/*     */   {
/* 253 */     return this.index;
/*     */   }
/*     */ 
/*     */   byte[] tds() throws SQLException
/*     */   {
/* 258 */     return this.tds;
/*     */   }
/*     */ 
/*     */   boolean isJavaObject(int paramInt, byte paramByte)
/*     */   {
/* 265 */     return (paramInt >= 3) && ((paramByte & 0x2) != 0);
/*     */   }
/*     */ 
/*     */   boolean isFinalType(int paramInt, byte paramByte)
/*     */   {
/* 271 */     return (paramInt >= 3) && ((paramByte & 0x1) == 0);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.TDSReader
 * JD-Core Version:    0.6.0
 */