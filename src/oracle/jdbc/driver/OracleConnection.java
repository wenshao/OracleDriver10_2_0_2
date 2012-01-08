/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import oracle.jdbc.OracleConnectionWrapper;
/*    */ import oracle.jdbc.internal.ClientDataSupport;
/*    */ 
/*    */ public abstract class OracleConnection extends OracleConnectionWrapper
/*    */   implements oracle.jdbc.internal.OracleConnection, ClientDataSupport
/*    */ {
/* 31 */   static int DEFAULT_ROW_PREFETCH = 10;
/*    */   static final String svptPrefix = "ORACLE_SVPT_";
/*    */   static final int BINARYSTREAM = 0;
/*    */   static final int ASCIISTREAM = 1;
/*    */   static final int UNICODESTREAM = 2;
/*    */   static final int EOJ_NON = 0;
/*    */   static final int EOJ_B_TO_A = 1;
/*    */   static final int EOJ_B_TO_U = 2;
/*    */   static final int EOJ_A_TO_U = 3;
/*    */   static final int EOJ_8_TO_A = 4;
/*    */   static final int EOJ_8_TO_U = 5;
/*    */   static final int EOJ_U_TO_A = 6;
/*    */   static final int ASCII_CHARSET = 0;
/*    */   static final int NLS_CHARSET = 1;
/*    */   static final int CHAR_TO_ASCII = 0;
/*    */   static final int CHAR_TO_UNICODE = 1;
/*    */   static final int RAW_TO_ASCII = 2;
/*    */   static final int RAW_TO_UNICODE = 3;
/*    */   static final int UNICODE_TO_CHAR = 4;
/*    */   static final int ASCII_TO_CHAR = 5;
/*    */   static final int NONE = 6;
/*    */   static final int JAVACHAR_TO_CHAR = 7;
/*    */   static final int RAW_TO_JAVACHAR = 8;
/*    */   static final int CHAR_TO_JAVACHAR = 9;
/*    */   static final int JAVACHAR_TO_ASCII = 10;
/*    */   static final int JAVACHAR_TO_UNICODE = 11;
/*    */   static final String RESOURCE_MANAGER_ID_DEFAULT = "0000";
/*    */ 
/*    */   static boolean containsKey(Map paramMap, Object paramObject)
/*    */   {
/* 73 */     return paramMap.get(paramObject) != null;
/*    */   }
/*    */ 
/*    */   public abstract Object getClientData(Object paramObject);
/*    */ 
/*    */   public abstract Object setClientData(Object paramObject1, Object paramObject2);
/*    */ 
/*    */   public abstract Object removeClientData(Object paramObject);
/*    */ 
/*    */   /** @deprecated */
/*    */   public abstract void setClientIdentifier(String paramString)
/*    */     throws SQLException;
/*    */ 
/*    */   /** @deprecated */
/*    */   public abstract void clearClientIdentifier(String paramString)
/*    */     throws SQLException;
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleConnection
 * JD-Core Version:    0.6.0
 */