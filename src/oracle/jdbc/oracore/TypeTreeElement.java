/*    */ package oracle.jdbc.oracore;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ public class TypeTreeElement
/*    */ {
/* 21 */   String schemaName = null;
/* 22 */   String typeName = null;
/* 23 */   String[] childSchemaNames = null;
/* 24 */   String[] childTypeNames = null;
/* 25 */   int size = 0;
/*    */ 
/*    */   public TypeTreeElement(String paramString1, String paramString2)
/*    */   {
/* 29 */     this.schemaName = paramString1;
/* 30 */     this.typeName = paramString2;
/*    */   }
/*    */ 
/*    */   public void putChild(String paramString1, String paramString2, int paramInt)
/*    */   {
/*    */     int i;
/* 35 */     if (this.childTypeNames == null)
/*    */     {
/* 37 */       i = 10;
/* 38 */       if (paramInt > i) i = paramInt * 2;
/* 39 */       this.childSchemaNames = new String[i];
/* 40 */       this.childTypeNames = new String[i];
/*    */     }
/* 42 */     if (paramInt >= this.childTypeNames.length)
/*    */     {
/* 44 */       i = (paramInt + 10) * 2;
/* 45 */       String[] arrayOfString = new String[i];
/* 46 */       System.arraycopy(this.childSchemaNames, 0, arrayOfString, 0, this.childSchemaNames.length);
/* 47 */       this.childSchemaNames = arrayOfString;
/* 48 */       arrayOfString = new String[i];
/* 49 */       System.arraycopy(this.childTypeNames, 0, arrayOfString, 0, this.childTypeNames.length);
/* 50 */       this.childTypeNames = arrayOfString;
/*    */     }
/* 52 */     this.childSchemaNames[paramInt] = paramString1;
/* 53 */     this.childTypeNames[paramInt] = paramString2;
/* 54 */     if (paramInt > this.size) this.size = paramInt;
/*    */   }
/*    */ 
/*    */   public String getChildSchemaName(int paramInt)
/*    */   {
/* 59 */     return this.childSchemaNames[paramInt];
/*    */   }
/*    */ 
/*    */   public String getChildTypeName(int paramInt)
/*    */   {
/* 64 */     return this.childTypeNames[paramInt];
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 69 */     StringWriter localStringWriter = new StringWriter();
/* 70 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 71 */     localPrintWriter.println("schemaName: " + this.schemaName + " typeName: " + this.typeName);
/* 72 */     for (int i = 0; i < this.size; i++)
/*    */     {
/* 74 */       localPrintWriter.println("index: " + i + " schema name: " + this.childSchemaNames[i] + " type name: " + this.childTypeNames[i]);
/*    */     }
/*    */ 
/* 79 */     return localStringWriter.toString();
/*    */   }
/*    */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.TypeTreeElement
 * JD-Core Version:    0.6.0
 */