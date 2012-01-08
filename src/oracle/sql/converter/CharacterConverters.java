/*     */ package oracle.sql.converter;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class CharacterConverters
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8064827627847332101L;
/*     */   public static final int CHARCONV1BYTEID = 0;
/*     */   public static final int CHARCONV12BYTEID = 1;
/*     */   public static final int CHARCONVJAEUCID = 2;
/*     */   public static final int CHARCONVLCFIXEDID = 3;
/*     */   public static final int CHARCONVSJISID = 4;
/*     */   public static final int CHARCONVZHTEUCID = 5;
/*     */   public static final int CHARCONV2BYTEFIXEDID = 6;
/*     */   public static final int CHARCONVSHIFTID = 7;
/*     */   public static final int CHARCONVLCID = 8;
/*     */   public static final int CHARCONVGB18030ID = 9;
/*     */   public static final int CHARCONVAL16UTF16ID = 10;
/*     */   public static final int CHARCONVMSOLISO2022JPFWID = 11;
/*     */   public static final int CHARCONVMSOLISO2022JPHWID = 12;
/*     */   public static final int CHARCONVGBKID = 13;
/*     */   public int m_groupId;
/*     */   public int m_oracleId;
/* 128 */   public int[][] extraUnicodeToOracleMapping = (int[][])null;
/*     */ 
/*     */   protected abstract void storeMappingRange(int paramInt, Hashtable paramHashtable1, Hashtable paramHashtable2);
/*     */ 
/*     */   public int getGroupId()
/*     */   {
/* 150 */     return this.m_groupId;
/*     */   }
/*     */ 
/*     */   public int getOracleId()
/*     */   {
/* 160 */     return this.m_oracleId;
/*     */   }
/*     */ 
/*     */   public abstract String toUnicodeString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException;
/*     */ 
/*     */   public abstract String toUnicodeStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract byte[] toOracleString(String paramString)
/*     */     throws SQLException;
/*     */ 
/*     */   public abstract byte[] toOracleStringWithReplacement(String paramString);
/*     */ 
/*     */   public abstract void buildUnicodeToOracleMapping();
/*     */ 
/*     */   public abstract void extractCodepoints(Vector paramVector);
/*     */ 
/*     */   public abstract void extractExtraMappings(Vector paramVector);
/*     */ 
/*     */   public abstract boolean hasExtraMappings();
/*     */ 
/*     */   public abstract char getOraChar1ByteRep();
/*     */ 
/*     */   public abstract char getOraChar2ByteRep();
/*     */ 
/*     */   public abstract int getUCS2CharRep();
/*     */ 
/*     */   public char[] getLeadingCodes()
/*     */   {
/* 266 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterConverters
 * JD-Core Version:    0.6.0
 */