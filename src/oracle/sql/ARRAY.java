/*      */ package oracle.sql;
/*      */ 
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Array;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ public class ARRAY extends DatumWithConnection
/*      */   implements Array
/*      */ {
/*      */   static final byte KOPUP_INLINE_COLL = 1;
/*      */   ArrayDescriptor descriptor;
/*      */   Object objArray;
/*      */   Datum[] datumArray;
/*      */   byte[] locator;
/*      */   byte prefixFlag;
/*      */   byte[] prefixSegment;
/*  102 */   int numElems = -1;
/*      */ 
/*  104 */   boolean enableBuffering = false;
/*  105 */   boolean enableIndexing = false;
/*      */   public static final int ACCESS_FORWARD = 1;
/*      */   public static final int ACCESS_REVERSE = 2;
/*      */   public static final int ACCESS_UNKNOWN = 3;
/*  110 */   int accessDirection = 3;
/*      */   long lastIndex;
/*      */   long lastOffset;
/*      */   long[] indexArray;
/*      */   long imageOffset;
/*      */   long imageLength;
/* 1300 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*      */ 
/*      */   public ARRAY(ArrayDescriptor paramArrayDescriptor, Connection paramConnection, Object paramObject)
/*      */     throws SQLException
/*      */   {
/*  131 */     assertNotNull(paramArrayDescriptor);
/*      */ 
/*  133 */     this.descriptor = paramArrayDescriptor;
/*      */ 
/*  135 */     assertNotNull(paramConnection);
/*      */ 
/*  137 */     if (!paramArrayDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
/*      */     {
/*  141 */       throw new SQLException("Cannot construct ARRAY instance, invalid connection");
/*      */     }
/*      */ 
/*  145 */     paramArrayDescriptor.setConnection(paramConnection);
/*  146 */     setPhysicalConnectionOf(paramConnection);
/*      */ 
/*  149 */     if (paramObject == null)
/*  150 */       this.datumArray = new Datum[0];
/*      */     else
/*  152 */       this.datumArray = this.descriptor.toOracleArray(paramObject, 1L, -1);
/*      */   }
/*      */ 
/*      */   public ARRAY(ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  170 */     super(paramArrayOfByte);
/*      */ 
/*  175 */     assertNotNull(paramArrayDescriptor);
/*      */ 
/*  177 */     this.descriptor = paramArrayDescriptor;
/*      */ 
/*  179 */     assertNotNull(paramConnection);
/*      */ 
/*  181 */     if (!paramArrayDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
/*      */     {
/*  185 */       throw new SQLException("Cannot construct ARRAY instance, invalid connection");
/*      */     }
/*      */ 
/*  189 */     paramArrayDescriptor.setConnection(paramConnection);
/*  190 */     setPhysicalConnectionOf(paramConnection);
/*      */ 
/*  192 */     this.datumArray = null;
/*  193 */     this.locator = null;
/*      */   }
/*      */ 
/*      */   public static ARRAY toARRAY(Object paramObject, oracle.jdbc.OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  214 */     ARRAY localARRAY = null;
/*      */ 
/*  216 */     if (paramObject != null) {
/*  217 */       if ((paramObject instanceof ARRAY))
/*      */       {
/*  219 */         localARRAY = (ARRAY)paramObject;
/*      */       }
/*  221 */       else if ((paramObject instanceof ORAData))
/*      */       {
/*  223 */         localARRAY = (ARRAY)((ORAData)paramObject).toDatum(paramOracleConnection);
/*      */       }
/*  228 */       else if ((paramObject instanceof CustomDatum))
/*      */       {
/*  230 */         localARRAY = (ARRAY)((oracle.jdbc.internal.OracleConnection)paramOracleConnection).toDatum((CustomDatum)paramObject);
/*      */       }
/*      */       else
/*      */       {
/*  238 */         DatabaseError.throwSqlException(59, paramObject);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  244 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   public synchronized String getBaseTypeName()
/*      */     throws SQLException
/*      */   {
/*  274 */     String str = this.descriptor.getBaseName();
/*      */ 
/*  278 */     return str;
/*      */   }
/*      */ 
/*      */   public synchronized int getBaseType()
/*      */     throws SQLException
/*      */   {
/*  297 */     return this.descriptor.getBaseType();
/*      */   }
/*      */ 
/*      */   public synchronized Object getArray()
/*      */     throws SQLException
/*      */   {
/*  315 */     return this.descriptor.toJavaArray(this, 1L, -1, getMap(), this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized Object getArray(Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  333 */     return this.descriptor.toJavaArray(this, 1L, -1, paramMap, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized Object getArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  356 */     if ((paramLong < 1L) || (paramInt < 0))
/*      */     {
/*  361 */       DatabaseError.throwSqlException(68, "getArray()");
/*      */     }
/*      */ 
/*  365 */     return this.descriptor.toJavaArray(this, paramLong, paramInt, getMap(), false);
/*      */   }
/*      */ 
/*      */   public synchronized Object getArray(long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  389 */     if ((paramLong < 1L) || (paramInt < 0))
/*      */     {
/*  394 */       DatabaseError.throwSqlException(68, "getArray()");
/*      */     }
/*      */ 
/*  398 */     return this.descriptor.toJavaArray(this, paramLong, paramInt, paramMap, false);
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getResultSet()
/*      */     throws SQLException
/*      */   {
/*  428 */     return getResultSet(getInternalConnection().getTypeMap());
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getResultSet(Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  460 */     return this.descriptor.toResultSet(this, 1L, -1, paramMap, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getResultSet(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  498 */     return getResultSet(paramLong, paramInt, getInternalConnection().getTypeMap());
/*      */   }
/*      */ 
/*      */   public synchronized ResultSet getResultSet(long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  539 */     if ((paramLong < 1L) || (paramInt < -1))
/*      */     {
/*  541 */       DatabaseError.throwSqlException(68, "getResultSet()");
/*      */     }
/*      */ 
/*  545 */     return this.descriptor.toResultSet(this, paramLong, paramInt, paramMap, false);
/*      */   }
/*      */ 
/*      */   public synchronized Datum[] getOracleArray()
/*      */     throws SQLException
/*      */   {
/*  565 */     return this.descriptor.toOracleArray(this, 1L, -1, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized int length()
/*      */     throws SQLException
/*      */   {
/*  580 */     return this.descriptor.toLength(this);
/*      */   }
/*      */ 
/*      */   public synchronized Datum[] getOracleArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  599 */     if ((paramLong < 1L) || (paramInt < 0))
/*      */     {
/*  601 */       DatabaseError.throwSqlException(68, "getOracleArray()");
/*      */     }
/*      */ 
/*  605 */     return this.descriptor.toOracleArray(this, paramLong, paramInt, false);
/*      */   }
/*      */ 
/*      */   public synchronized String getSQLTypeName()
/*      */     throws SQLException
/*      */   {
/*  625 */     String str = null;
/*      */ 
/*  627 */     if (this.descriptor != null)
/*      */     {
/*  629 */       str = this.descriptor.getName();
/*      */     }
/*      */     else
/*      */     {
/*  633 */       DatabaseError.throwSqlException(61, "ARRAY");
/*      */     }
/*      */ 
/*  638 */     return str;
/*      */   }
/*      */ 
/*      */   public Map getMap()
/*      */     throws SQLException
/*      */   {
/*  650 */     return getInternalConnection().getTypeMap();
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor getDescriptor()
/*      */     throws SQLException
/*      */   {
/*  667 */     return this.descriptor;
/*      */   }
/*      */ 
/*      */   public synchronized byte[] toBytes()
/*      */     throws SQLException
/*      */   {
/*  680 */     return this.descriptor.toBytes(this, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized void setDatumArray(Datum[] paramArrayOfDatum)
/*      */   {
/*  695 */     this.datumArray = paramArrayOfDatum;
/*      */   }
/*      */ 
/*      */   public synchronized void setObjArray(Object paramObject)
/*      */     throws SQLException
/*      */   {
/*  715 */     if (paramObject == null)
/*      */     {
/*  720 */       DatabaseError.throwSqlException(1);
/*      */     }
/*      */ 
/*  723 */     this.objArray = paramObject;
/*      */   }
/*      */ 
/*      */   public synchronized void setLocator(byte[] paramArrayOfByte)
/*      */   {
/*  743 */     if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
/*  744 */       this.locator = paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   public synchronized void setPrefixSegment(byte[] paramArrayOfByte)
/*      */   {
/*  760 */     if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
/*  761 */       this.prefixSegment = paramArrayOfByte;
/*      */   }
/*      */ 
/*      */   public synchronized void setPrefixFlag(byte paramByte)
/*      */   {
/*  777 */     this.prefixFlag = paramByte;
/*      */   }
/*      */ 
/*      */   public byte[] getLocator()
/*      */   {
/*  790 */     return this.locator;
/*      */   }
/*      */ 
/*      */   public synchronized void setLength(int paramInt)
/*      */   {
/*  801 */     this.numElems = paramInt;
/*      */   }
/*      */ 
/*      */   public boolean hasDataSeg()
/*      */   {
/*  814 */     return this.locator == null;
/*      */   }
/*      */ 
/*      */   public boolean isInline()
/*      */   {
/*  827 */     return (this.prefixFlag & 0x1) == 1;
/*      */   }
/*      */ 
/*      */   public Object toJdbc()
/*      */     throws SQLException
/*      */   {
/*  844 */     return this;
/*      */   }
/*      */ 
/*      */   public boolean isConvertibleTo(Class paramClass)
/*      */   {
/*  861 */     return false;
/*      */   }
/*      */ 
/*      */   public Object makeJdbcArray(int paramInt)
/*      */   {
/*  872 */     return new Object[paramInt][];
/*      */   }
/*      */ 
/*      */   public synchronized int[] getIntArray()
/*      */     throws SQLException
/*      */   {
/*  887 */     return (int[])this.descriptor.toNumericArray(this, 1L, -1, 4, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized int[] getIntArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  905 */     return (int[])this.descriptor.toNumericArray(this, paramLong, paramInt, 4, false);
/*      */   }
/*      */ 
/*      */   public synchronized double[] getDoubleArray()
/*      */     throws SQLException
/*      */   {
/*  920 */     return (double[])this.descriptor.toNumericArray(this, 1L, -1, 5, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized double[] getDoubleArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  939 */     return (double[])this.descriptor.toNumericArray(this, paramLong, paramInt, 5, false);
/*      */   }
/*      */ 
/*      */   public synchronized short[] getShortArray()
/*      */     throws SQLException
/*      */   {
/*  954 */     return (short[])this.descriptor.toNumericArray(this, 1L, -1, 8, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized short[] getShortArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  963 */     return (short[])this.descriptor.toNumericArray(this, paramLong, paramInt, 8, false);
/*      */   }
/*      */ 
/*      */   public synchronized long[] getLongArray()
/*      */     throws SQLException
/*      */   {
/*  978 */     return (long[])this.descriptor.toNumericArray(this, 1L, -1, 7, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized long[] getLongArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  996 */     return (long[])this.descriptor.toNumericArray(this, paramLong, paramInt, 7, false);
/*      */   }
/*      */ 
/*      */   public synchronized float[] getFloatArray()
/*      */     throws SQLException
/*      */   {
/* 1011 */     return (float[])this.descriptor.toNumericArray(this, 1L, -1, 6, this.enableBuffering);
/*      */   }
/*      */ 
/*      */   public synchronized float[] getFloatArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1029 */     return (float[])this.descriptor.toNumericArray(this, paramLong, paramInt, 6, false);
/*      */   }
/*      */ 
/*      */   public synchronized void setAutoBuffering(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1050 */     this.enableBuffering = paramBoolean;
/*      */   }
/*      */ 
/*      */   public boolean getAutoBuffering()
/*      */     throws SQLException
/*      */   {
/* 1067 */     return this.enableBuffering;
/*      */   }
/*      */ 
/*      */   public synchronized void setAutoIndexing(boolean paramBoolean, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1092 */     this.enableIndexing = paramBoolean;
/* 1093 */     this.accessDirection = paramInt;
/*      */   }
/*      */ 
/*      */   public synchronized void setAutoIndexing(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1113 */     this.enableIndexing = paramBoolean;
/* 1114 */     this.accessDirection = 3;
/*      */   }
/*      */ 
/*      */   public boolean getAutoIndexing()
/*      */     throws SQLException
/*      */   {
/* 1132 */     return this.enableIndexing;
/*      */   }
/*      */ 
/*      */   public int getAccessDirection()
/*      */     throws SQLException
/*      */   {
/* 1150 */     return this.accessDirection;
/*      */   }
/*      */ 
/*      */   public void setLastIndexOffset(long paramLong1, long paramLong2)
/*      */     throws SQLException
/*      */   {
/* 1167 */     this.lastIndex = paramLong1;
/* 1168 */     this.lastOffset = paramLong2;
/*      */   }
/*      */ 
/*      */   public void setIndexOffset(long paramLong1, long paramLong2)
/*      */     throws SQLException
/*      */   {
/* 1181 */     if (this.indexArray == null) {
/* 1182 */       this.indexArray = new long[this.numElems];
/*      */     }
/* 1184 */     this.indexArray[((int)paramLong1 - 1)] = paramLong2;
/*      */   }
/*      */ 
/*      */   public long getLastIndex()
/*      */     throws SQLException
/*      */   {
/* 1196 */     return this.lastIndex;
/*      */   }
/*      */ 
/*      */   public long getLastOffset()
/*      */     throws SQLException
/*      */   {
/* 1208 */     return this.lastOffset;
/*      */   }
/*      */ 
/*      */   public long getOffset(long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1220 */     long l = -1L;
/*      */ 
/* 1222 */     if (this.indexArray != null) {
/* 1223 */       l = this.indexArray[((int)paramLong - 1)];
/*      */     }
/*      */ 
/* 1228 */     return l;
/*      */   }
/*      */ 
/*      */   public void setImage(byte[] paramArrayOfByte, long paramLong1, long paramLong2)
/*      */     throws SQLException
/*      */   {
/* 1246 */     setShareBytes(paramArrayOfByte);
/*      */ 
/* 1248 */     this.imageOffset = paramLong1;
/* 1249 */     this.imageLength = paramLong2;
/*      */   }
/*      */ 
/*      */   public void setImageLength(long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1265 */     this.imageLength = paramLong;
/*      */   }
/*      */ 
/*      */   public long getImageOffset()
/*      */   {
/* 1278 */     return this.imageOffset;
/*      */   }
/*      */ 
/*      */   public long getImageLength()
/*      */   {
/* 1290 */     return this.imageLength;
/*      */   }
/*      */ 
/*      */   public Connection getJavaSqlConnection() throws SQLException
/*      */   {
/* 1295 */     return super.getJavaSqlConnection();
/*      */   }
/*      */ 
/*      */   public String dump()
/*      */     throws SQLException
/*      */   {
/* 1318 */     return STRUCT.dump(this);
/*      */   }
/*      */ 
/*      */   static void dump(ARRAY paramARRAY, PrintWriter paramPrintWriter, int paramInt) throws SQLException
/*      */   {
/* 1323 */     if (paramInt > 0) paramPrintWriter.println();
/*      */ 
/* 1326 */     ArrayDescriptor localArrayDescriptor = paramARRAY.getDescriptor();
/* 1327 */     for (int j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
/* 1328 */     paramPrintWriter.println("name = " + localArrayDescriptor.getName());
/*      */ 
/* 1330 */     for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
/* 1331 */     paramPrintWriter.println("max length = " + localArrayDescriptor.getMaxLength());
/* 1332 */     Object[] arrayOfObject = (Object[])paramARRAY.getArray();
/* 1333 */     for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
/*      */     int i;
/* 1334 */     paramPrintWriter.println("length = " + (i = arrayOfObject.length));
/* 1335 */     for (j = 0; j < i; j++)
/*      */     {
/* 1337 */       for (int k = 0; k < paramInt; k++) paramPrintWriter.print(' ');
/* 1338 */       paramPrintWriter.print("element[" + j + "] = ");
/* 1339 */       STRUCT.dump(arrayOfObject[j], paramPrintWriter, paramInt + 4);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ARRAY
 * JD-Core Version:    0.6.0
 */