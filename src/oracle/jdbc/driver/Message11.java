/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class Message11
/*    */   implements Message
/*    */ {
/*    */   private static ResourceBundle bundle;
/*    */   private static final String messageFile = "oracle.jdbc.driver.Messages";
/* 71 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*    */   public static final boolean TRACE = false;
/*    */   public static final boolean PRIVATE_TRACE = false;
/*    */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*    */ 
/*    */   public String msg(String paramString, Object paramObject)
/*    */   {
/* 36 */     if (bundle == null)
/*    */     {
/*    */       try
/*    */       {
/* 40 */         bundle = ResourceBundle.getBundle("oracle.jdbc.driver.Messages");
/*    */       }
/*    */       catch (Exception localException1)
/*    */       {
/* 48 */         return "Message file 'oracle.jdbc.driver.Messages' is missing.";
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 58 */       if (paramObject != null) {
/* 59 */         return bundle.getString(paramString) + ": " + paramObject;
/*    */       }
/* 61 */       return bundle.getString(paramString);
/*    */     }
/*    */     catch (Exception localException2) {
/*    */     }
/* 65 */     return "Message [" + paramString + "] not found in '" + "oracle.jdbc.driver.Messages" + "'.";
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.Message11
 * JD-Core Version:    0.6.0
 */