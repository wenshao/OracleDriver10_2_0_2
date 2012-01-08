/*    */ package oracle.jdbc.driver;
/*    */ 
/*    */ class Namespace
/*    */ {
/*    */   static final int ATTRIBUTE_MAX_LENGTH = 30;
/*    */   static final int VALUE_MAX_LENGTH = 4000;
/*    */   String name;
/*    */   boolean clear;
/*    */   String[] keys;
/*    */   String[] values;
/*    */   int nbPairs;
/*    */ 
/*    */   Namespace(String paramString)
/*    */   {
/* 49 */     if (paramString == null) {
/* 50 */       throw new NullPointerException();
/*    */     }
/* 52 */     this.name = paramString;
/* 53 */     this.clear = false;
/* 54 */     this.nbPairs = 0;
/* 55 */     this.keys = new String[5];
/* 56 */     this.values = new String[5];
/*    */   }
/*    */ 
/*    */   void clear()
/*    */   {
/* 61 */     this.clear = true;
/*    */ 
/* 63 */     for (int i = 0; i < this.nbPairs; i++)
/*    */     {
/* 65 */       this.keys[i] = null;
/* 66 */       this.values[i] = null;
/*    */     }
/* 68 */     this.nbPairs = 0;
/*    */   }
/*    */ 
/*    */   void setAttribute(String paramString1, String paramString2)
/*    */   {
/* 73 */     if ((paramString1 == null) || (paramString2 == null) || (paramString1.equals(""))) {
/* 74 */       throw new NullPointerException();
/*    */     }
/* 76 */     if (this.nbPairs == this.keys.length)
/*    */     {
/* 78 */       String[] arrayOfString1 = new String[this.keys.length * 2];
/* 79 */       String[] arrayOfString2 = new String[this.keys.length * 2];
/* 80 */       System.arraycopy(this.keys, 0, arrayOfString1, 0, this.keys.length);
/* 81 */       System.arraycopy(this.values, 0, arrayOfString2, 0, this.values.length);
/* 82 */       this.keys = arrayOfString1;
/* 83 */       this.values = arrayOfString2;
/*    */     }
/* 85 */     this.keys[this.nbPairs] = paramString1;
/* 86 */     this.values[this.nbPairs] = paramString2;
/* 87 */     this.nbPairs += 1;
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.Namespace
 * JD-Core Version:    0.6.0
 */