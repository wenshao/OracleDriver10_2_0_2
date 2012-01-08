/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class ROWID extends Datum
/*     */ {
/* 235 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public ROWID()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ROWID(byte[] paramArrayOfByte)
/*     */   {
/*  71 */     super(paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 116 */     String str = paramClass.getName();
/*     */ 
/* 120 */     return str.compareTo("java.lang.String") == 0;
/*     */   }
/*     */ 
/*     */   public String stringValue()
/*     */   {
/* 144 */     byte[] arrayOfByte = null;
/*     */ 
/* 146 */     arrayOfByte = getBytes();
/*     */ 
/* 148 */     String str = new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */ 
/* 153 */     return str;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 178 */     return new byte[paramInt][];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ROWID
 * JD-Core Version:    0.6.0
 */