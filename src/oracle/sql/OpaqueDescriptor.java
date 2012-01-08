/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.oracore.OracleNamedType;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.jdbc.oracore.OracleTypeOPAQUE;
/*     */ 
/*     */ public class OpaqueDescriptor extends TypeDescriptor
/*     */   implements Serializable
/*     */ {
/*     */   static final boolean DEBUG = false;
/*     */   static final long serialVersionUID = 1013921343538311063L;
/* 506 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public static OpaqueDescriptor createDescriptor(String paramString, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/*  79 */     if ((paramString == null) || (paramString.length() == 0) || (paramConnection == null))
/*     */     {
/*  84 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*     */     }
/*     */ 
/*  88 */     SQLName localSQLName = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);
/*  89 */     return createDescriptor(localSQLName, paramConnection);
/*     */   }
/*     */ 
/*     */   public static OpaqueDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 106 */     String str = paramSQLName.getName();
/*     */ 
/* 109 */     OpaqueDescriptor localOpaqueDescriptor = (OpaqueDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);
/*     */ 
/* 113 */     if (localOpaqueDescriptor == null)
/*     */     {
/* 115 */       localOpaqueDescriptor = new OpaqueDescriptor(paramSQLName, paramConnection);
/*     */ 
/* 117 */       ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localOpaqueDescriptor);
/*     */     }
/*     */ 
/* 122 */     return localOpaqueDescriptor;
/*     */   }
/*     */ 
/*     */   public OpaqueDescriptor(String paramString, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 135 */     super(paramString, paramConnection);
/*     */ 
/* 139 */     initPickler();
/*     */   }
/*     */ 
/*     */   public OpaqueDescriptor(SQLName paramSQLName, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 155 */     super(paramSQLName, paramConnection);
/*     */ 
/* 159 */     initPickler();
/*     */   }
/*     */ 
/*     */   public OpaqueDescriptor(SQLName paramSQLName, OracleTypeOPAQUE paramOracleTypeOPAQUE, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 176 */     super(paramSQLName, paramOracleTypeOPAQUE, paramConnection);
/*     */   }
/*     */ 
/*     */   private void initPickler()
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 189 */       this.pickler = new OracleTypeADT(getName(), this.connection);
/*     */ 
/* 191 */       ((OracleTypeADT)this.pickler).init(this.connection);
/*     */ 
/* 193 */       this.pickler = ((OracleTypeOPAQUE)((OracleTypeADT)this.pickler).cleanup());
/*     */ 
/* 195 */       this.pickler.setDescriptor(this);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 202 */       if ((localException instanceof SQLException)) {
/* 203 */         throw ((SQLException)localException);
/*     */       }
/* 205 */       DatabaseError.throwSqlException(60, "Unable to resolve type \"" + getName() + "\"");
/*     */     }
/*     */   }
/*     */ 
/*     */   public OpaqueDescriptor(OracleTypeADT paramOracleTypeADT, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 225 */     super(paramOracleTypeADT, paramConnection);
/*     */   }
/*     */ 
/*     */   byte[] toBytes(OPAQUE paramOPAQUE, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 236 */     byte[] arrayOfByte = null;
/*     */ 
/* 238 */     if (paramOPAQUE.shareBytes() != null)
/*     */     {
/* 240 */       arrayOfByte = paramOPAQUE.shareBytes();
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 246 */         arrayOfByte = this.pickler.linearize(paramOPAQUE);
/*     */       }
/*     */       finally
/*     */       {
/* 250 */         if (!paramBoolean) {
/* 251 */           paramOPAQUE.setShareBytes(null);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 257 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   byte[] toValue(OPAQUE paramOPAQUE, boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 265 */     byte[] arrayOfByte = null;
/*     */ 
/* 267 */     if (paramOPAQUE.value != null)
/*     */     {
/* 269 */       arrayOfByte = paramOPAQUE.value;
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 275 */         this.pickler.unlinearize(paramOPAQUE.shareBytes(), 0L, paramOPAQUE, 1, null);
/*     */ 
/* 277 */         arrayOfByte = paramOPAQUE.value;
/*     */       }
/*     */       finally
/*     */       {
/* 281 */         if (!paramBoolean) {
/* 282 */           paramOPAQUE.value = null;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/* 299 */     return 2007;
/*     */   }
/*     */ 
/*     */   public long getMaxLength()
/*     */     throws SQLException
/*     */   {
/* 310 */     long l = hasUnboundedSize() ? 0L : ((OracleTypeOPAQUE)this.pickler).getMaxLength();
/*     */ 
/* 315 */     return l;
/*     */   }
/*     */ 
/*     */   public boolean isTrustedLibrary()
/*     */     throws SQLException
/*     */   {
/* 332 */     return ((OracleTypeOPAQUE)this.pickler).isTrustedLibrary();
/*     */   }
/*     */ 
/*     */   public boolean isModeledInC()
/*     */     throws SQLException
/*     */   {
/* 346 */     return ((OracleTypeOPAQUE)this.pickler).isModeledInC();
/*     */   }
/*     */ 
/*     */   public boolean hasUnboundedSize()
/*     */     throws SQLException
/*     */   {
/* 360 */     return ((OracleTypeOPAQUE)this.pickler).isUnboundedSized();
/*     */   }
/*     */ 
/*     */   public boolean hasFixedSize()
/*     */     throws SQLException
/*     */   {
/* 375 */     return ((OracleTypeOPAQUE)this.pickler).isFixedSized();
/*     */   }
/*     */ 
/*     */   public String descType()
/*     */     throws SQLException
/*     */   {
/* 388 */     StringBuffer localStringBuffer = new StringBuffer();
/* 389 */     String str = descType(localStringBuffer, 0);
/*     */ 
/* 393 */     return str;
/*     */   }
/*     */ 
/*     */   String descType(StringBuffer paramStringBuffer, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 401 */     String str1 = "";
/*     */ 
/* 403 */     for (int i = 0; i < paramInt; i++) {
/* 404 */       str1 = str1 + "  ";
/*     */     }
/* 406 */     String str2 = str1 + "  ";
/*     */ 
/* 408 */     paramStringBuffer.append(str1);
/* 409 */     paramStringBuffer.append(getTypeName());
/* 410 */     paramStringBuffer.append(" maxLen=" + getMaxLength() + " isTrusted=" + isTrustedLibrary() + " hasUnboundedSize=" + hasUnboundedSize() + " hasFixedSize=" + hasFixedSize());
/*     */ 
/* 413 */     paramStringBuffer.append("\n");
/*     */ 
/* 415 */     String str3 = paramStringBuffer.toString();
/*     */ 
/* 420 */     return str3;
/*     */   }
/*     */ 
/*     */   public Class getClass(Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 435 */     Object localObject = null;
/*     */ 
/* 440 */     String str1 = getName();
/*     */ 
/* 443 */     Class localClass = (Class)paramMap.get(str1);
/*     */ 
/* 445 */     String str2 = getSchemaName();
/* 446 */     String str3 = getTypeName();
/*     */ 
/* 448 */     if (localClass == null)
/*     */     {
/* 450 */       if (this.connection.getUserName().equals(str2)) {
/* 451 */         localClass = (Class)paramMap.get(str3);
/*     */       }
/*     */     }
/* 454 */     if (!SQLName.s_parseAllFormat)
/*     */     {
/* 456 */       localObject = localClass;
/*     */     }
/*     */     else
/*     */     {
/* 460 */       if (localClass == null)
/*     */       {
/* 462 */         if (this.connection.getUserName().equals(str2)) {
/* 463 */           localClass = (Class)paramMap.get("\"" + str3 + "\"");
/*     */         }
/*     */       }
/* 466 */       if (localClass == null)
/*     */       {
/* 468 */         localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + "\"" + str3 + "\"");
/*     */       }
/*     */ 
/* 471 */       if (localClass == null)
/*     */       {
/* 473 */         localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + str3);
/*     */       }
/*     */ 
/* 476 */       if (localClass == null)
/*     */       {
/* 478 */         localClass = (Class)paramMap.get(str2 + "." + "\"" + str3 + "\"");
/*     */       }
/*     */ 
/* 481 */       localObject = localClass;
/*     */     }
/*     */ 
/* 486 */     return localObject;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.OpaqueDescriptor
 * JD-Core Version:    0.6.0
 */