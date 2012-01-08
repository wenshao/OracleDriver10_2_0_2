/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ 
/*     */ public class OPAQUE extends DatumWithConnection
/*     */ {
/*     */   OpaqueDescriptor descriptor;
/*     */   byte[] value;
/*     */   long imageOffset;
/*     */   long imageLength;
/* 472 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public OPAQUE(OpaqueDescriptor paramOpaqueDescriptor, Connection paramConnection, Object paramObject)
/*     */     throws SQLException
/*     */   {
/*  73 */     if (paramOpaqueDescriptor != null) {
/*  74 */       this.descriptor = paramOpaqueDescriptor;
/*     */     }
/*     */     else
/*     */     {
/*  80 */       DatabaseError.throwSqlException(61, "OPAQUE");
/*     */     }
/*     */ 
/*  84 */     setPhysicalConnectionOf(paramConnection);
/*     */ 
/*  86 */     if ((paramObject instanceof byte[])) {
/*  87 */       this.value = ((byte[])paramObject);
/*     */     }
/*     */     else
/*     */     {
/*  93 */       DatabaseError.throwSqlException(59, "OPAQUE()");
/*     */     }
/*     */   }
/*     */ 
/*     */   public OPAQUE(OpaqueDescriptor paramOpaqueDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 113 */     super(paramArrayOfByte);
/*     */ 
/* 118 */     setPhysicalConnectionOf(paramConnection);
/*     */ 
/* 120 */     this.descriptor = paramOpaqueDescriptor;
/* 121 */     this.value = null;
/*     */   }
/*     */ 
/*     */   public String getSQLTypeName()
/*     */     throws SQLException
/*     */   {
/* 143 */     String str = this.descriptor.getName();
/*     */ 
/* 147 */     return str;
/*     */   }
/*     */ 
/*     */   public OpaqueDescriptor getDescriptor()
/*     */     throws SQLException
/*     */   {
/* 167 */     return this.descriptor;
/*     */   }
/*     */ 
/*     */   public void setDescriptor(OpaqueDescriptor paramOpaqueDescriptor)
/*     */   {
/* 179 */     this.descriptor = paramOpaqueDescriptor;
/*     */   }
/*     */ 
/*     */   public byte[] toBytes()
/*     */     throws SQLException
/*     */   {
/* 193 */     return this.descriptor.toBytes(this, false);
/*     */   }
/*     */ 
/*     */   public Object getValue()
/*     */     throws SQLException
/*     */   {
/* 205 */     return this.descriptor.toValue(this, false);
/*     */   }
/*     */ 
/*     */   public byte[] getBytesValue()
/*     */     throws SQLException
/*     */   {
/* 218 */     return this.descriptor.toValue(this, false);
/*     */   }
/*     */ 
/*     */   public void setValue(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 229 */     this.value = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 255 */     return new Object[paramInt];
/*     */   }
/*     */ 
/*     */   public Map getMap()
/*     */   {
/*     */     try
/*     */     {
/* 269 */       return getInternalConnection().getTypeMap();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/*     */     }
/*     */ 
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 293 */     Map localMap = getMap();
/* 294 */     Object localObject = toJdbc(localMap);
/*     */ 
/* 298 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object toJdbc(Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 311 */     Object localObject = this;
/*     */ 
/* 313 */     if (paramMap != null)
/*     */     {
/* 315 */       Class localClass = this.descriptor.getClass(paramMap);
/*     */ 
/* 317 */       if (localClass != null) {
/* 318 */         localObject = toClass(localClass, paramMap);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 323 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object toClass(Class paramClass)
/*     */     throws SQLException
/*     */   {
/* 336 */     Object localObject = toClass(paramClass, getMap());
/*     */ 
/* 340 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object toClass(Class paramClass, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 361 */     Object localObject1 = null;
/*     */     try
/*     */     {
/* 365 */       if ((paramClass == null) || (paramClass == OPAQUE.class)) {
/* 366 */         localObject1 = this;
/*     */       }
/*     */       else {
/* 369 */         ORAData localORAData = null;
/* 370 */         Object localObject2 = paramClass.newInstance();
/*     */ 
/* 372 */         if ((localObject2 instanceof ORADataFactory))
/*     */         {
/* 374 */           ORADataFactory localORADataFactory = (ORADataFactory)localObject2;
/*     */ 
/* 376 */           localORAData = localORADataFactory.create(this, 2007);
/*     */         }
/*     */         else
/*     */         {
/* 383 */           DatabaseError.throwSqlException(49, this.descriptor.getName());
/*     */         }
/*     */ 
/* 387 */         localObject1 = localORAData;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (InstantiationException localInstantiationException)
/*     */     {
/* 395 */       DatabaseError.throwSqlException(49, "InstantiationException: " + localInstantiationException.getMessage());
/*     */     }
/*     */     catch (IllegalAccessException localIllegalAccessException)
/*     */     {
/* 404 */       DatabaseError.throwSqlException(49, "IllegalAccessException: " + localIllegalAccessException.getMessage());
/*     */     }
/*     */ 
/* 411 */     return localObject1;
/*     */   }
/*     */ 
/*     */   public void setImage(byte[] paramArrayOfByte, long paramLong1, long paramLong2)
/*     */     throws SQLException
/*     */   {
/* 423 */     setShareBytes(paramArrayOfByte);
/*     */ 
/* 425 */     this.imageOffset = paramLong1;
/* 426 */     this.imageLength = paramLong2;
/*     */   }
/*     */ 
/*     */   public void setImageLength(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 440 */     this.imageLength = paramLong;
/*     */   }
/*     */ 
/*     */   public long getImageOffset()
/*     */   {
/* 451 */     return this.imageOffset;
/*     */   }
/*     */ 
/*     */   public long getImageLength()
/*     */   {
/* 462 */     return this.imageLength;
/*     */   }
/*     */ 
/*     */   public Connection getJavaSqlConnection() throws SQLException
/*     */   {
/* 467 */     return super.getJavaSqlConnection();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.OPAQUE
 * JD-Core Version:    0.6.0
 */