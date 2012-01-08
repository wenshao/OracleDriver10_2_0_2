/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.jdbc.oracore.OracleNamedType;
/*     */ import oracle.jdbc.oracore.OracleType;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.ArrayDescriptor;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.JAVA_STRUCT;
/*     */ import oracle.sql.OPAQUE;
/*     */ import oracle.sql.OpaqueDescriptor;
/*     */ import oracle.sql.STRUCT;
/*     */ import oracle.sql.StructDescriptor;
/*     */ import oracle.sql.TypeDescriptor;
/*     */ 
/*     */ class T2CNamedTypeAccessor extends NamedTypeAccessor
/*     */ {
/*  31 */   int columnNumber = 0;
/*     */ 
/*     */   T2CNamedTypeAccessor(OracleStatement paramOracleStatement, String paramString, short paramShort, int paramInt1, boolean paramBoolean, int paramInt2)
/*     */     throws SQLException
/*     */   {
/*  37 */     super(paramOracleStatement, paramString, paramShort, paramInt1, paramBoolean);
/*     */ 
/*  39 */     this.columnNumber = paramInt2;
/*  40 */     this.isColumnNumberAware = true;
/*     */   }
/*     */ 
/*     */   T2CNamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString)
/*     */     throws SQLException
/*     */   {
/*  49 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString);
/*     */   }
/*     */ 
/*     */   T2CNamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, String paramString, OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/*  58 */     super(paramOracleStatement, paramInt1, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort, paramString, paramOracleType);
/*     */   }
/*     */ 
/*     */   OracleType otypeFromName(String paramString)
/*     */     throws SQLException
/*     */   {
/*  64 */     if ("SYS.ANYDATA".equals(paramString)) {
/*  65 */       return StructDescriptor.createDescriptor(paramString, this.statement.connection).getPickler();
/*     */     }
/*     */ 
/*  68 */     return super.otypeFromName(paramString);
/*     */   }
/*     */ 
/*     */   Object getAnyDataEmbeddedObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  86 */     byte[] arrayOfByte1 = getBytes(paramInt);
/*     */ 
/*  88 */     if (arrayOfByte1 == null) {
/*  89 */       return null;
/*     */     }
/*  91 */     Object localObject1 = null;
/*     */ 
/*  93 */     int[] arrayOfInt = new int[2];
/*     */ 
/* 104 */     String str = getAllAnydataTypeInfo(this.statement.c_state, paramInt, this.columnNumber, arrayOfInt);
/*     */ 
/* 106 */     int i = arrayOfInt[0];
/* 107 */     OracleConnection localOracleConnection = ((OracleNamedType)this.internalOtype).getConnection();
/*     */ 
/* 110 */     switch (i)
/*     */     {
/*     */     case 2003:
/* 114 */       localObject1 = ArrayDescriptor.createDescriptor(str, localOracleConnection);
/*     */ 
/* 116 */       break;
/*     */     case 2002:
/*     */     case 2008:
/* 121 */       localObject1 = StructDescriptor.createDescriptor(str, localOracleConnection);
/*     */ 
/* 123 */       break;
/*     */     case 2007:
/* 126 */       localObject1 = OpaqueDescriptor.createDescriptor(str, localOracleConnection);
/*     */ 
/* 128 */       break;
/*     */     case 2004:
/*     */     case 2005:
/*     */     case 2006:
/*     */     default:
/* 131 */       DatabaseError.throwSqlException(23);
/*     */     }
/*     */ 
/* 134 */     Object localObject2 = null;
/*     */ 
/* 136 */     int j = arrayOfInt[1];
/*     */ 
/* 138 */     int k = arrayOfByte1.length - j;
/* 139 */     byte[] arrayOfByte2 = new byte[k];
/*     */ 
/* 141 */     System.arraycopy(arrayOfByte1, j, arrayOfByte2, 0, k);
/*     */ 
/* 143 */     switch (((TypeDescriptor)localObject1).getTypeCode())
/*     */     {
/*     */     case 2003:
/* 147 */       localObject2 = new ARRAY((ArrayDescriptor)localObject1, arrayOfByte2, this.statement.connection);
/*     */ 
/* 149 */       break;
/*     */     case 2002:
/* 152 */       localObject2 = new STRUCT((StructDescriptor)localObject1, arrayOfByte2, this.statement.connection);
/*     */ 
/* 154 */       break;
/*     */     case 2007:
/* 157 */       localObject2 = new OPAQUE((OpaqueDescriptor)localObject1, arrayOfByte2, this.statement.connection);
/*     */ 
/* 159 */       break;
/*     */     case 2008:
/* 162 */       localObject2 = new JAVA_STRUCT((StructDescriptor)localObject1, arrayOfByte2, this.statement.connection);
/*     */ 
/* 165 */       break;
/*     */     case 2004:
/*     */     case 2005:
/*     */     case 2006:
/*     */     default:
/* 168 */       DatabaseError.throwSqlException(4);
/*     */     }
/*     */ 
/* 171 */     return ((Datum)localObject2).toJdbc();
/*     */   }
/*     */ 
/*     */   native String getAllAnydataTypeInfo(long paramLong, int paramInt1, int paramInt2, int[] paramArrayOfInt);
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CNamedTypeAccessor
 * JD-Core Version:    0.6.0
 */