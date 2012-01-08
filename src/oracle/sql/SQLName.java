/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class SQLName
/*     */   implements Serializable
/*     */ {
/*  60 */   static boolean DEBUG = false;
/*  61 */   static boolean s_parseAllFormat = false;
/*     */   static final long serialVersionUID = 2266340348729491526L;
/*     */   String name;
/*     */   String schema;
/*     */   String simple;
/*     */   int version;
/*     */   boolean synonym;
/* 421 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   protected SQLName()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SQLName(String paramString, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  90 */     init(paramString, paramOracleConnection);
/*     */ 
/*  92 */     this.version = 2;
/*  93 */     this.synonym = false;
/*     */   }
/*     */ 
/*     */   public SQLName(String paramString1, String paramString2, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 111 */     this.schema = paramString1;
/* 112 */     this.simple = paramString2;
/* 113 */     this.name = (this.schema + "." + this.simple);
/*     */ 
/* 115 */     this.version = 2;
/* 116 */     this.synonym = false;
/*     */   }
/*     */ 
/*     */   private void init(String paramString, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 132 */     String[] arrayOfString1 = new String[1];
/* 133 */     String[] arrayOfString2 = new String[1];
/*     */ 
/* 135 */     if (parse(paramString, arrayOfString1, arrayOfString2, true))
/*     */     {
/* 137 */       this.schema = arrayOfString1[0];
/* 138 */       this.simple = arrayOfString2[0];
/*     */     }
/*     */     else
/*     */     {
/* 142 */       this.schema = paramOracleConnection.getUserName();
/* 143 */       this.simple = arrayOfString2[0];
/*     */     }
/*     */ 
/* 146 */     this.name = (this.schema + "." + this.simple);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */     throws SQLException
/*     */   {
/* 161 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getSchema()
/*     */     throws SQLException
/*     */   {
/* 172 */     return this.schema;
/*     */   }
/*     */ 
/*     */   public String getSimpleName()
/*     */     throws SQLException
/*     */   {
/* 183 */     return this.simple;
/*     */   }
/*     */ 
/*     */   public int getVersion()
/*     */     throws SQLException
/*     */   {
/* 194 */     return this.version;
/*     */   }
/*     */ 
/*     */   public static boolean parse(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */     throws SQLException
/*     */   {
/* 215 */     return parse(paramString, paramArrayOfString1, paramArrayOfString2, s_parseAllFormat);
/*     */   }
/*     */ 
/*     */   public static boolean parse(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 226 */     if (paramString == null) {
/* 227 */       return false;
/*     */     }
/* 229 */     if ((paramArrayOfString1 == null) || (paramArrayOfString1.length < 1) || (paramArrayOfString2 == null) || (paramArrayOfString2.length < 1))
/*     */     {
/* 235 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 253 */     if (!paramBoolean)
/*     */     {
/* 255 */       i = paramString.indexOf(".");
/*     */ 
/* 257 */       if (i < 0)
/*     */       {
/* 259 */         paramArrayOfString2[0] = paramString;
/*     */ 
/* 261 */         return false;
/*     */       }
/*     */ 
/* 265 */       paramArrayOfString1[0] = paramString.substring(0, i);
/* 266 */       paramArrayOfString2[0] = paramString.substring(i + 1);
/*     */ 
/* 268 */       return true;
/*     */     }
/*     */ 
/* 273 */     int i = paramString.length();
/* 274 */     int j = paramString.indexOf("\"");
/* 275 */     int k = paramString.indexOf("\"", j + 1);
/* 276 */     int m = -1;
/*     */ 
/* 278 */     if (j < 0)
/*     */     {
/* 280 */       m = paramString.indexOf(".");
/*     */ 
/* 282 */       if (m < 0)
/*     */       {
/* 284 */         paramArrayOfString2[0] = paramString;
/*     */ 
/* 286 */         return false;
/*     */       }
/*     */ 
/* 290 */       paramArrayOfString1[0] = paramString.substring(0, m);
/* 291 */       paramArrayOfString2[0] = paramString.substring(m + 1);
/*     */ 
/* 293 */       return true;
/*     */     }
/*     */ 
/* 296 */     if (j == 0)
/*     */     {
/* 298 */       if (k == i - 1)
/*     */       {
/* 300 */         paramArrayOfString2[0] = paramString.substring(j + 1, k);
/*     */ 
/* 302 */         return false;
/*     */       }
/*     */ 
/* 306 */       m = paramString.indexOf(".", k);
/* 307 */       paramArrayOfString1[0] = paramString.substring(j + 1, k);
/*     */ 
/* 309 */       j = paramString.indexOf("\"", m);
/* 310 */       k = paramString.indexOf("\"", j + 1);
/*     */ 
/* 312 */       if (j < 0)
/*     */       {
/* 314 */         paramArrayOfString2[0] = paramString.substring(m + 1);
/*     */ 
/* 316 */         return true;
/*     */       }
/*     */ 
/* 322 */       paramArrayOfString2[0] = paramString.substring(j + 1, k);
/*     */ 
/* 324 */       return true;
/*     */     }
/*     */ 
/* 330 */     m = paramString.indexOf(".");
/* 331 */     paramArrayOfString1[0] = paramString.substring(0, m);
/* 332 */     paramArrayOfString2[0] = paramString.substring(j + 1, k);
/*     */ 
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */   public static void setHandleDoubleQuote(boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 354 */     s_parseAllFormat = paramBoolean;
/*     */   }
/*     */ 
/*     */   public static boolean getHandleDoubleQuote()
/*     */     throws SQLException
/*     */   {
/* 369 */     return s_parseAllFormat;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 374 */     if (paramObject == this) return true;
/* 375 */     if (!(paramObject instanceof SQLName)) return false;
/* 376 */     return ((SQLName)paramObject).name.equals(this.name);
/*     */   }
/*     */   public int hashCode() {
/* 379 */     return this.name.hashCode();
/*     */   }
/*     */   public String toString() {
/* 382 */     return this.name;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 393 */     paramObjectOutputStream.writeUTF(this.name);
/* 394 */     paramObjectOutputStream.writeUTF(this.schema);
/* 395 */     paramObjectOutputStream.writeUTF(this.simple);
/* 396 */     paramObjectOutputStream.writeInt(this.version);
/* 397 */     paramObjectOutputStream.writeBoolean(this.synonym);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 409 */     this.name = paramObjectInputStream.readUTF();
/* 410 */     this.schema = paramObjectInputStream.readUTF();
/* 411 */     this.simple = paramObjectInputStream.readUTF();
/* 412 */     this.version = paramObjectInputStream.readInt();
/* 413 */     this.synonym = paramObjectInputStream.readBoolean();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.SQLName
 * JD-Core Version:    0.6.0
 */