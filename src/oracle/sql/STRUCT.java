/*      */ package oracle.sql;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLOutput;
/*      */ import java.sql.Struct;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ public class STRUCT extends DatumWithConnection
/*      */   implements Struct
/*      */ {
/*      */   StructDescriptor descriptor;
/*      */   Datum[] datumArray;
/*      */   Object[] objectArray;
/*   99 */   boolean enableLocalCache = false;
/*      */   long imageOffset;
/*      */   long imageLength;
/*  953 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*      */ 
/*      */   public STRUCT(StructDescriptor paramStructDescriptor, Connection paramConnection, Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  131 */     assertNotNull(paramStructDescriptor);
/*      */ 
/*  133 */     this.descriptor = paramStructDescriptor;
/*      */ 
/*  135 */     assertNotNull(paramConnection);
/*      */ 
/*  137 */     if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
/*      */     {
/*  141 */       throw new SQLException("Cannot construct STRUCT instance, invalid connection");
/*      */     }
/*      */ 
/*  145 */     paramStructDescriptor.setConnection(paramConnection);
/*      */ 
/*  147 */     if (!this.descriptor.isInstantiable())
/*      */     {
/*  152 */       throw new SQLException("Cannot construct STRUCT instance for a non-instantiable object type");
/*      */     }
/*      */ 
/*  156 */     setPhysicalConnectionOf(paramConnection);
/*      */ 
/*  158 */     if (paramArrayOfObject != null)
/*  159 */       this.datumArray = this.descriptor.toArray(paramArrayOfObject);
/*      */     else
/*  161 */       this.datumArray = new Datum[0];
/*      */   }
/*      */ 
/*      */   public STRUCT(StructDescriptor paramStructDescriptor, Connection paramConnection, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  173 */     assertNotNull(paramStructDescriptor);
/*      */ 
/*  175 */     this.descriptor = paramStructDescriptor;
/*      */ 
/*  177 */     assertNotNull(paramConnection);
/*      */ 
/*  179 */     if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
/*      */     {
/*  183 */       throw new SQLException("Cannot construct STRUCT instance, invalid connection");
/*      */     }
/*      */ 
/*  187 */     paramStructDescriptor.setConnection(paramConnection);
/*      */ 
/*  189 */     if (!this.descriptor.isInstantiable())
/*      */     {
/*  194 */       throw new SQLException("Cannot construct STRUCT instance for a non-instantiable object type");
/*      */     }
/*      */ 
/*  198 */     setPhysicalConnectionOf(paramConnection);
/*      */ 
/*  200 */     this.datumArray = this.descriptor.toOracleArray(paramMap);
/*      */   }
/*      */ 
/*      */   public STRUCT(StructDescriptor paramStructDescriptor, byte[] paramArrayOfByte, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  219 */     super(paramArrayOfByte);
/*      */ 
/*  223 */     assertNotNull(paramStructDescriptor);
/*      */ 
/*  225 */     this.descriptor = paramStructDescriptor;
/*      */ 
/*  227 */     assertNotNull(paramConnection);
/*      */ 
/*  229 */     if (!paramStructDescriptor.getInternalConnection().isDescriptorSharable(((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin()))
/*      */     {
/*  233 */       throw new SQLException("Cannot construct STRUCT instance, invalid connection");
/*      */     }
/*      */ 
/*  237 */     paramStructDescriptor.setConnection(paramConnection);
/*  238 */     setPhysicalConnectionOf(paramConnection);
/*      */ 
/*  240 */     this.datumArray = null;
/*      */   }
/*      */ 
/*      */   public synchronized String getSQLTypeName()
/*      */     throws SQLException
/*      */   {
/*  266 */     String str = this.descriptor.getName();
/*      */ 
/*  270 */     return str;
/*      */   }
/*      */ 
/*      */   public synchronized Object[] getAttributes()
/*      */     throws SQLException
/*      */   {
/*  299 */     Object[] arrayOfObject = getAttributes(getMap());
/*      */ 
/*  303 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   public synchronized Object[] getAttributes(Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  335 */     Object[] arrayOfObject = this.descriptor.toArray(this, paramMap, this.enableLocalCache);
/*      */ 
/*  339 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   public synchronized StructDescriptor getDescriptor()
/*      */     throws SQLException
/*      */   {
/*  359 */     return this.descriptor;
/*      */   }
/*      */ 
/*      */   public synchronized void setDescriptor(StructDescriptor paramStructDescriptor)
/*      */   {
/*  371 */     this.descriptor = paramStructDescriptor;
/*      */   }
/*      */ 
/*      */   public synchronized Datum[] getOracleAttributes()
/*      */     throws SQLException
/*      */   {
/*  388 */     Datum[] arrayOfDatum = this.descriptor.toOracleArray(this, this.enableLocalCache);
/*      */ 
/*  392 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public Map getMap()
/*      */   {
/*  405 */     Map localMap = null;
/*      */     try
/*      */     {
/*  409 */       localMap = getInternalConnection().getTypeMap();
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */ 
/*  420 */     return localMap;
/*      */   }
/*      */ 
/*      */   public synchronized byte[] toBytes()
/*      */     throws SQLException
/*      */   {
/*  436 */     byte[] arrayOfByte = this.descriptor.toBytes(this, this.enableLocalCache);
/*      */ 
/*  440 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public synchronized void setDatumArray(Datum[] paramArrayOfDatum)
/*      */   {
/*  455 */     this.datumArray = (paramArrayOfDatum == null ? new Datum[0] : paramArrayOfDatum);
/*      */   }
/*      */ 
/*      */   public synchronized void setObjArray(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  470 */     this.objectArray = (paramArrayOfObject == null ? new Object[0] : paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public static STRUCT toSTRUCT(Object paramObject, oracle.jdbc.OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  491 */     STRUCT localSTRUCT = null;
/*      */ 
/*  493 */     if (paramObject != null) {
/*  494 */       if ((paramObject instanceof STRUCT))
/*      */       {
/*  496 */         localSTRUCT = (STRUCT)paramObject;
/*      */       }
/*  498 */       else if ((paramObject instanceof ORAData))
/*      */       {
/*  500 */         localSTRUCT = (STRUCT)((ORAData)paramObject).toDatum(paramOracleConnection);
/*      */       }
/*  505 */       else if ((paramObject instanceof CustomDatum))
/*      */       {
/*  507 */         localSTRUCT = (STRUCT)((oracle.jdbc.internal.OracleConnection)paramOracleConnection).toDatum((CustomDatum)paramObject);
/*      */       }
/*  513 */       else if ((paramObject instanceof SQLData))
/*      */       {
/*  515 */         SQLData localSQLData = (SQLData)paramObject;
/*      */ 
/*  517 */         StructDescriptor localStructDescriptor = StructDescriptor.createDescriptor(localSQLData.getSQLTypeName(), paramOracleConnection);
/*      */ 
/*  520 */         SQLOutput localSQLOutput = localStructDescriptor.toJdbc2SQLOutput();
/*      */ 
/*  522 */         localSQLData.writeSQL(localSQLOutput);
/*      */ 
/*  524 */         localSTRUCT = ((OracleSQLOutput)localSQLOutput).getSTRUCT();
/*      */       }
/*      */       else
/*      */       {
/*  528 */         DatabaseError.throwSqlException(59, paramObject);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  534 */     return localSTRUCT;
/*      */   }
/*      */ 
/*      */   public Object toJdbc()
/*      */     throws SQLException
/*      */   {
/*  551 */     Map localMap = getMap();
/*  552 */     Object localObject = toJdbc(localMap);
/*      */ 
/*  556 */     return localObject;
/*      */   }
/*      */ 
/*      */   public Object toJdbc(Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  570 */     Object localObject = this;
/*      */ 
/*  572 */     if (paramMap != null)
/*      */     {
/*  574 */       Class localClass = this.descriptor.getClass(paramMap);
/*      */ 
/*  576 */       if (localClass != null) {
/*  577 */         localObject = toClass(localClass, paramMap);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  582 */     return localObject;
/*      */   }
/*      */ 
/*      */   public Object toClass(Class paramClass)
/*      */     throws SQLException
/*      */   {
/*  595 */     Object localObject = toClass(paramClass, getMap());
/*      */ 
/*  599 */     return localObject;
/*      */   }
/*      */ 
/*      */   public Object toClass(Class paramClass, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  624 */     Object localObject1 = null;
/*      */     try
/*      */     {
/*  628 */       if ((paramClass == null) || (paramClass == STRUCT.class) || (paramClass == Struct.class))
/*      */       {
/*  630 */         localObject1 = this;
/*      */       }
/*      */       else
/*      */       {
/*  635 */         Object localObject2 = paramClass.newInstance();
/*      */ 
/*  637 */         if ((localObject2 instanceof SQLData))
/*      */         {
/*  639 */           ((SQLData)localObject2).readSQL(this.descriptor.toJdbc2SQLInput(this, paramMap), this.descriptor.getName());
/*      */ 
/*  642 */           localObject1 = localObject2;
/*      */         }
/*      */         else
/*      */         {
/*      */           Object localObject3;
/*  644 */           if ((localObject2 instanceof ORADataFactory))
/*      */           {
/*  646 */             localObject3 = (ORADataFactory)localObject2;
/*      */ 
/*  648 */             localObject1 = ((ORADataFactory)localObject3).create(this, 2002);
/*      */           }
/*  653 */           else if ((localObject2 instanceof CustomDatumFactory))
/*      */           {
/*  655 */             localObject3 = (CustomDatumFactory)localObject2;
/*      */ 
/*  657 */             localObject1 = ((CustomDatumFactory)localObject3).create(this, 2002);
/*      */           }
/*      */           else
/*      */           {
/*  667 */             DatabaseError.throwSqlException(49, this.descriptor.getName());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (InstantiationException localInstantiationException)
/*      */     {
/*  677 */       DatabaseError.throwSqlException(49, "InstantiationException: " + localInstantiationException.getMessage());
/*      */     }
/*      */     catch (IllegalAccessException localIllegalAccessException)
/*      */     {
/*  686 */       DatabaseError.throwSqlException(49, "IllegalAccessException: " + localIllegalAccessException.getMessage());
/*      */     }
/*      */ 
/*  693 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public boolean isConvertibleTo(Class paramClass)
/*      */   {
/*  708 */     return false;
/*      */   }
/*      */ 
/*      */   public Object makeJdbcArray(int paramInt)
/*      */   {
/*  719 */     return new Object[paramInt];
/*      */   }
/*      */ 
/*      */   public synchronized void setAutoBuffering(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  740 */     this.enableLocalCache = paramBoolean;
/*      */   }
/*      */ 
/*      */   public synchronized boolean getAutoBuffering()
/*      */     throws SQLException
/*      */   {
/*  757 */     return this.enableLocalCache;
/*      */   }
/*      */ 
/*      */   public void setImage(byte[] paramArrayOfByte, long paramLong1, long paramLong2)
/*      */     throws SQLException
/*      */   {
/*  770 */     setShareBytes(paramArrayOfByte);
/*      */ 
/*  772 */     this.imageOffset = paramLong1;
/*  773 */     this.imageLength = paramLong2;
/*      */   }
/*      */ 
/*      */   public void setImageLength(long paramLong)
/*      */     throws SQLException
/*      */   {
/*  788 */     this.imageLength = paramLong;
/*      */   }
/*      */ 
/*      */   public long getImageOffset()
/*      */   {
/*  800 */     return this.imageOffset;
/*      */   }
/*      */ 
/*      */   public long getImageLength()
/*      */   {
/*  813 */     return this.imageLength;
/*      */   }
/*      */ 
/*      */   public CustomDatumFactory getFactory(Hashtable paramHashtable, String paramString)
/*      */     throws SQLException
/*      */   {
/*  830 */     String str = getSQLTypeName();
/*  831 */     Object localObject = paramHashtable.get(str);
/*      */ 
/*  833 */     if (localObject == null)
/*      */     {
/*  838 */       throw new SQLException("Unable to convert a \"" + str + "\" to a \"" + paramString + "\" or a subclass of \"" + paramString + "\"");
/*      */     }
/*      */ 
/*  843 */     CustomDatumFactory localCustomDatumFactory = (CustomDatumFactory)localObject;
/*      */ 
/*  847 */     return localCustomDatumFactory;
/*      */   }
/*      */ 
/*      */   public ORADataFactory getORADataFactory(Hashtable paramHashtable, String paramString)
/*      */     throws SQLException
/*      */   {
/*  864 */     String str = getSQLTypeName();
/*  865 */     Object localObject = paramHashtable.get(str);
/*      */ 
/*  867 */     if (localObject == null)
/*      */     {
/*  872 */       throw new SQLException("Unable to convert a \"" + str + "\" to a \"" + paramString + "\" or a subclass of \"" + paramString + "\"");
/*      */     }
/*      */ 
/*  877 */     ORADataFactory localORADataFactory = (ORADataFactory)localObject;
/*      */ 
/*  881 */     return localORADataFactory;
/*      */   }
/*      */ 
/*      */   public String debugString()
/*      */   {
/*  896 */     StringWriter localStringWriter = new StringWriter();
/*  897 */     String str = null;
/*      */     try
/*      */     {
/*  901 */       StructDescriptor localStructDescriptor = getDescriptor();
/*      */ 
/*  903 */       localStringWriter.write("name = " + localStructDescriptor.getName());
/*      */       int i;
/*  904 */       localStringWriter.write(" length = " + (i = localStructDescriptor.getLength()));
/*      */ 
/*  906 */       Object[] arrayOfObject = getAttributes();
/*      */ 
/*  908 */       for (int j = 0; j < i; j++)
/*      */       {
/*  910 */         localStringWriter.write(" attribute[" + j + "] = " + arrayOfObject[j]);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  918 */       str = "StructDescriptor missing or bad";
/*      */     }
/*      */ 
/*  921 */     str = localStringWriter.toString();
/*      */ 
/*  925 */     return str;
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(String paramString)
/*      */     throws SQLException
/*      */   {
/*  939 */     boolean bool = getDescriptor().isInHierarchyOf(paramString);
/*      */ 
/*  943 */     return bool;
/*      */   }
/*      */ 
/*      */   public Connection getJavaSqlConnection() throws SQLException
/*      */   {
/*  948 */     return super.getJavaSqlConnection();
/*      */   }
/*      */ 
/*      */   public String dump()
/*      */     throws SQLException
/*      */   {
/*  972 */     return dump(this);
/*      */   }
/*      */ 
/*      */   public static String dump(Object paramObject) throws SQLException
/*      */   {
/*  977 */     StringWriter localStringWriter = new StringWriter();
/*  978 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/*  979 */     dump(paramObject, localPrintWriter);
/*  980 */     return localStringWriter.toString();
/*      */   }
/*      */ 
/*      */   public static void dump(Object paramObject, PrintStream paramPrintStream) throws SQLException
/*      */   {
/*  985 */     dump(paramObject, new PrintWriter(paramPrintStream, true));
/*      */   }
/*      */ 
/*      */   public static void dump(Object paramObject, PrintWriter paramPrintWriter) throws SQLException
/*      */   {
/*  990 */     dump(paramObject, paramPrintWriter, 0);
/*      */   }
/*      */ 
/*      */   static void dump(Object paramObject, PrintWriter paramPrintWriter, int paramInt) throws SQLException
/*      */   {
/*  995 */     if ((paramObject instanceof STRUCT)) { dump((STRUCT)paramObject, paramPrintWriter, paramInt); return; }
/*  996 */     if ((paramObject instanceof ARRAY)) { ARRAY.dump((ARRAY)paramObject, paramPrintWriter, paramInt); return; }
/*  997 */     paramPrintWriter.println(paramObject.toString());
/*      */   }
/*      */ 
/*      */   static void dump(STRUCT paramSTRUCT, PrintWriter paramPrintWriter, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1004 */     StructDescriptor localStructDescriptor = paramSTRUCT.getDescriptor();
/* 1005 */     ResultSetMetaData localResultSetMetaData = localStructDescriptor.getMetaData();
/*      */ 
/* 1007 */     for (int j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
/* 1008 */     paramPrintWriter.println("name = " + localStructDescriptor.getName());
/*      */ 
/* 1010 */     for (j = 0; j < paramInt; j++) paramPrintWriter.print(' ');
/*      */     int i;
/* 1011 */     paramPrintWriter.println("length = " + (i = localStructDescriptor.getLength()));
/* 1012 */     Object[] arrayOfObject = paramSTRUCT.getAttributes();
/* 1013 */     for (j = 0; j < i; j++)
/*      */     {
/* 1015 */       for (int k = 0; k < paramInt; k++) paramPrintWriter.print(' ');
/* 1016 */       paramPrintWriter.print(localResultSetMetaData.getColumnName(j + 1) + " = ");
/* 1017 */       dump(arrayOfObject[j], paramPrintWriter, paramInt + 1);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.STRUCT
 * JD-Core Version:    0.6.0
 */