/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import B;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.jdbc.oracore.OracleNamedType;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ import oracle.jdbc.oracore.OracleTypeCOLLECTION;
/*      */ import oracle.jdbc.oracore.OracleTypeOPAQUE;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import oracle.sql.BFILE;
/*      */ import oracle.sql.BINARY_DOUBLE;
/*      */ import oracle.sql.BINARY_FLOAT;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.CHAR;
/*      */ import oracle.sql.CLOB;
/*      */ import oracle.sql.CharacterSet;
/*      */ import oracle.sql.CustomDatum;
/*      */ import oracle.sql.CustomDatumFactory;
/*      */ import oracle.sql.DATE;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.INTERVALDS;
/*      */ import oracle.sql.INTERVALYM;
/*      */ import oracle.sql.NUMBER;
/*      */ import oracle.sql.OPAQUE;
/*      */ import oracle.sql.ORAData;
/*      */ import oracle.sql.ORADataFactory;
/*      */ import oracle.sql.OpaqueDescriptor;
/*      */ import oracle.sql.RAW;
/*      */ import oracle.sql.REF;
/*      */ import oracle.sql.ROWID;
/*      */ import oracle.sql.SQLName;
/*      */ import oracle.sql.STRUCT;
/*      */ import oracle.sql.StructDescriptor;
/*      */ import oracle.sql.TIMESTAMP;
/*      */ import oracle.sql.TIMESTAMPLTZ;
/*      */ import oracle.sql.TIMESTAMPTZ;
/*      */ import oracle.sql.TypeDescriptor;
/*      */ import oracle.sql.converter.CharacterSetMetaData;
/*      */ 
/*      */ public class SQLUtil
/*      */ {
/* 1681 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*      */   private static final int CLASS_NOT_FOUND = -1;
/*      */   private static final int CLASS_STRING = 0;
/*      */   private static final int CLASS_BOOLEAN = 1;
/*      */   private static final int CLASS_INTEGER = 2;
/*      */   private static final int CLASS_LONG = 3;
/*      */   private static final int CLASS_FLOAT = 4;
/*      */   private static final int CLASS_DOUBLE = 5;
/*      */   private static final int CLASS_BIGDECIMAL = 6;
/*      */   private static final int CLASS_DATE = 7;
/*      */   private static final int CLASS_TIME = 8;
/*      */   private static final int CLASS_TIMESTAMP = 9;
/*      */   private static final int TOTAL_CLASSES = 10;
/* 1710 */   private static Hashtable classTable = new Hashtable(10);
/*      */ 
/*      */   public static Object SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, Class paramClass, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*   89 */     Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
/*   90 */     Object localObject = SQLToJava(paramOracleConnection, localDatum, paramClass, paramMap);
/*      */ 
/*   94 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static CustomDatum SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, CustomDatumFactory paramCustomDatumFactory)
/*      */     throws SQLException
/*      */   {
/*  132 */     Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
/*  133 */     CustomDatum localCustomDatum = paramCustomDatumFactory.create(localDatum, paramInt);
/*      */ 
/*  137 */     return localCustomDatum;
/*      */   }
/*      */ 
/*      */   public static ORAData SQLToJava(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt, String paramString, ORADataFactory paramORADataFactory)
/*      */     throws SQLException
/*      */   {
/*  175 */     Datum localDatum = makeDatum(paramOracleConnection, paramArrayOfByte, paramInt, paramString, 0);
/*  176 */     ORAData localORAData = paramORADataFactory.create(localDatum, paramInt);
/*      */ 
/*  180 */     return localORAData;
/*      */   }
/*      */ 
/*      */   public static Object SQLToJava(OracleConnection paramOracleConnection, Datum paramDatum, Class paramClass, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  220 */     Object localObject = null;
/*      */ 
/*  222 */     if ((paramDatum instanceof STRUCT))
/*      */     {
/*  228 */       if (paramClass == null)
/*      */       {
/*  230 */         localObject = paramMap != null ? ((STRUCT)paramDatum).toJdbc(paramMap) : paramDatum.toJdbc();
/*      */       }
/*      */       else
/*      */       {
/*  234 */         localObject = paramMap != null ? ((STRUCT)paramDatum).toClass(paramClass, paramMap) : ((STRUCT)paramDatum).toClass(paramClass);
/*      */       }
/*      */ 
/*      */     }
/*  238 */     else if (paramClass == null)
/*      */     {
/*  244 */       localObject = paramDatum.toJdbc();
/*      */     }
/*      */     else
/*      */     {
/*  252 */       int i = classNumber(paramClass);
/*      */ 
/*  254 */       switch (i)
/*      */       {
/*      */       case 0:
/*  258 */         localObject = paramDatum.stringValue();
/*      */ 
/*  260 */         break;
/*      */       case 1:
/*  263 */         localObject = new Boolean(paramDatum.longValue() != 0L);
/*      */ 
/*  265 */         break;
/*      */       case 2:
/*  268 */         localObject = new Integer((int)paramDatum.longValue());
/*      */ 
/*  270 */         break;
/*      */       case 3:
/*  273 */         localObject = new Long(paramDatum.longValue());
/*      */ 
/*  275 */         break;
/*      */       case 4:
/*  278 */         localObject = new Float(paramDatum.bigDecimalValue().floatValue());
/*      */ 
/*  280 */         break;
/*      */       case 5:
/*  283 */         localObject = new Double(paramDatum.bigDecimalValue().doubleValue());
/*      */ 
/*  285 */         break;
/*      */       case 6:
/*  288 */         localObject = paramDatum.bigDecimalValue();
/*      */ 
/*  290 */         break;
/*      */       case 7:
/*  293 */         localObject = paramDatum.dateValue();
/*      */ 
/*  295 */         break;
/*      */       case 8:
/*  298 */         localObject = paramDatum.timeValue();
/*      */ 
/*  300 */         break;
/*      */       case 9:
/*  303 */         localObject = paramDatum.timestampValue();
/*      */ 
/*  305 */         break;
/*      */       case -1:
/*      */       default:
/*  310 */         localObject = paramDatum.toJdbc();
/*      */ 
/*  312 */         if (paramClass.isInstance(localObject))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  317 */         DatabaseError.throwSqlException(59, "invalid data conversion");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  328 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static byte[] JavaToSQL(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/*  367 */     if (paramObject == null)
/*      */     {
/*  372 */       return null;
/*      */     }
/*      */ 
/*  380 */     Object localObject = null;
/*      */ 
/*  382 */     if ((paramObject instanceof Datum))
/*      */     {
/*  384 */       localObject = (Datum)paramObject;
/*      */     }
/*  386 */     else if ((paramObject instanceof ORAData))
/*      */     {
/*  388 */       localObject = ((ORAData)paramObject).toDatum(paramOracleConnection);
/*      */     }
/*  390 */     else if ((paramObject instanceof CustomDatum))
/*      */     {
/*  392 */       localObject = paramOracleConnection.toDatum((CustomDatum)paramObject);
/*      */     }
/*  394 */     else if ((paramObject instanceof SQLData))
/*      */     {
/*  396 */       localObject = STRUCT.toSTRUCT(paramObject, paramOracleConnection);
/*      */     }
/*      */ 
/*  403 */     if (localObject != null)
/*      */     {
/*  413 */       if (!checkDatumType((Datum)localObject, paramInt, paramString))
/*      */       {
/*  415 */         localObject = null;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  428 */       localObject = makeDatum(paramOracleConnection, paramObject, paramInt, paramString);
/*      */     }
/*      */ 
/*  431 */     byte[] arrayOfByte = null;
/*      */ 
/*  436 */     if (localObject != null)
/*      */     {
/*  438 */       if ((localObject instanceof STRUCT))
/*  439 */         arrayOfByte = ((STRUCT)localObject).toBytes();
/*  440 */       else if ((localObject instanceof ARRAY))
/*  441 */         arrayOfByte = ((ARRAY)localObject).toBytes();
/*  442 */       else if ((localObject instanceof OPAQUE))
/*  443 */         arrayOfByte = ((OPAQUE)localObject).toBytes();
/*      */       else {
/*  445 */         arrayOfByte = ((Datum)localObject).shareBytes();
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  452 */       DatabaseError.throwSqlException(1, "attempt to convert a Datum to incompatible SQL type");
/*      */     }
/*      */ 
/*  458 */     return (B)arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static Datum makeDatum(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt1, String paramString, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  500 */     Object localObject1 = null;
/*      */ 
/*  509 */     int i = paramOracleConnection.getDbCsId();
/*  510 */     int j = CharacterSetMetaData.getRatio(i, 1);
/*      */     Object localObject2;
/*  512 */     switch (paramInt1)
/*      */     {
/*      */     case 96:
/*  521 */       if ((paramInt2 != 0) && (paramInt2 < paramArrayOfByte.length) && (j == 1)) {
/*  522 */         localObject1 = new CHAR(paramArrayOfByte, 0, paramInt2, CharacterSet.make(paramOracleConnection.getJdbcCsId()));
/*      */       }
/*      */       else {
/*  525 */         localObject1 = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getJdbcCsId()));
/*      */       }
/*      */ 
/*  528 */       break;
/*      */     case 1:
/*      */     case 8:
/*  535 */       localObject1 = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getJdbcCsId()));
/*      */ 
/*  538 */       break;
/*      */     case 2:
/*      */     case 6:
/*  543 */       localObject1 = new NUMBER(paramArrayOfByte);
/*      */ 
/*  545 */       break;
/*      */     case 100:
/*  548 */       localObject1 = new BINARY_FLOAT(paramArrayOfByte);
/*      */ 
/*  550 */       break;
/*      */     case 101:
/*  553 */       localObject1 = new BINARY_DOUBLE(paramArrayOfByte);
/*      */ 
/*  555 */       break;
/*      */     case 23:
/*      */     case 24:
/*  560 */       localObject1 = new RAW(paramArrayOfByte);
/*      */ 
/*  562 */       break;
/*      */     case 104:
/*  565 */       localObject1 = new ROWID(paramArrayOfByte);
/*      */ 
/*  567 */       break;
/*      */     case 102:
/*  576 */       DatabaseError.throwSqlException(1, "need resolution: do we want to handle ResultSet?");
/*      */ 
/*  579 */       break;
/*      */     case 12:
/*  582 */       localObject1 = new DATE(paramArrayOfByte);
/*      */ 
/*  584 */       break;
/*      */     case 182:
/*  587 */       localObject1 = new INTERVALYM(paramArrayOfByte);
/*      */ 
/*  589 */       break;
/*      */     case 183:
/*  592 */       localObject1 = new INTERVALDS(paramArrayOfByte);
/*      */ 
/*  594 */       break;
/*      */     case 180:
/*  597 */       localObject1 = new TIMESTAMP(paramArrayOfByte);
/*      */ 
/*  599 */       break;
/*      */     case 181:
/*  602 */       localObject1 = new TIMESTAMPTZ(paramArrayOfByte);
/*      */ 
/*  604 */       break;
/*      */     case 231:
/*  607 */       localObject1 = new TIMESTAMPLTZ(paramArrayOfByte);
/*      */ 
/*  609 */       break;
/*      */     case 113:
/*  612 */       localObject1 = paramOracleConnection.createBlob(paramArrayOfByte);
/*      */ 
/*  614 */       break;
/*      */     case 112:
/*  617 */       localObject1 = paramOracleConnection.createClob(paramArrayOfByte);
/*      */ 
/*  619 */       break;
/*      */     case 114:
/*  622 */       localObject1 = paramOracleConnection.createBfile(paramArrayOfByte);
/*      */ 
/*  624 */       break;
/*      */     case 109:
/*  628 */       localObject2 = TypeDescriptor.getTypeDescriptor(paramString, paramOracleConnection, paramArrayOfByte, 0L);
/*      */ 
/*  631 */       if ((localObject2 instanceof StructDescriptor))
/*      */       {
/*  633 */         localObject1 = new STRUCT((StructDescriptor)localObject2, paramArrayOfByte, paramOracleConnection);
/*      */       }
/*  635 */       else if ((localObject2 instanceof ArrayDescriptor))
/*      */       {
/*  637 */         localObject1 = new ARRAY((ArrayDescriptor)localObject2, paramArrayOfByte, paramOracleConnection);
/*      */       } else {
/*  639 */         if (!(localObject2 instanceof OpaqueDescriptor))
/*      */           break;
/*  641 */         localObject1 = new OPAQUE((OpaqueDescriptor)localObject2, paramArrayOfByte, paramOracleConnection); } break;
/*      */     case 111:
/*  649 */       localObject2 = getTypeDescriptor(paramString, paramOracleConnection);
/*      */ 
/*  651 */       if ((localObject2 instanceof StructDescriptor))
/*      */       {
/*  653 */         localObject1 = new REF((StructDescriptor)localObject2, paramOracleConnection, paramArrayOfByte);
/*      */       }
/*      */       else
/*      */       {
/*  660 */         DatabaseError.throwSqlException(1, "program error: REF points to a non-STRUCT");
/*      */       }
/*      */ 
/*  665 */       break;
/*      */     default:
/*  671 */       DatabaseError.throwSqlException(1, "program error: invalid SQL type code");
/*      */     }
/*      */ 
/*  679 */     return (Datum)(Datum)localObject1;
/*      */   }
/*      */ 
/*      */   public static Datum makeNDatum(OracleConnection paramOracleConnection, byte[] paramArrayOfByte, int paramInt1, String paramString, short paramShort, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  695 */     Object localObject = null;
/*      */ 
/*  697 */     switch (paramInt1)
/*      */     {
/*      */     case 96:
/*  705 */       int i = paramInt2 * CharacterSetMetaData.getRatio(paramOracleConnection.getNCharSet(), 1);
/*      */ 
/*  709 */       if ((paramInt2 != 0) && (i < paramArrayOfByte.length)) {
/*  710 */         localObject = new CHAR(paramArrayOfByte, 0, paramInt2, CharacterSet.make(paramOracleConnection.getNCharSet()));
/*      */       }
/*      */       else {
/*  713 */         localObject = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getNCharSet()));
/*      */       }
/*      */ 
/*  716 */       break;
/*      */     case 1:
/*      */     case 8:
/*  723 */       localObject = new CHAR(paramArrayOfByte, CharacterSet.make(paramOracleConnection.getNCharSet()));
/*      */ 
/*  726 */       break;
/*      */     case 112:
/*  729 */       localObject = paramOracleConnection.createClob(paramArrayOfByte, paramShort);
/*      */ 
/*  731 */       break;
/*      */     default:
/*  737 */       DatabaseError.throwSqlException(1, "program error: invalid SQL type code");
/*      */     }
/*      */ 
/*  745 */     return (Datum)localObject;
/*      */   }
/*      */ 
/*      */   public static Datum makeDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/*  777 */     return makeDatum(paramOracleConnection, paramObject, paramInt, paramString, false);
/*      */   }
/*      */ 
/*      */   public static Datum makeDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  791 */     Object localObject = null;
/*      */ 
/*  793 */     switch (paramInt)
/*      */     {
/*      */     case 1:
/*      */     case 8:
/*      */     case 96:
/*  803 */       localObject = new CHAR(paramObject, CharacterSet.make(paramBoolean ? paramOracleConnection.getNCharSet() : paramOracleConnection.getJdbcCsId()));
/*      */ 
/*  806 */       break;
/*      */     case 2:
/*      */     case 6:
/*  811 */       localObject = new NUMBER(paramObject);
/*      */ 
/*  813 */       break;
/*      */     case 100:
/*  816 */       localObject = new BINARY_FLOAT((Float)paramObject);
/*      */ 
/*  818 */       break;
/*      */     case 101:
/*  821 */       localObject = new BINARY_DOUBLE((Double)paramObject);
/*      */ 
/*  823 */       break;
/*      */     case 23:
/*      */     case 24:
/*  828 */       localObject = new RAW(paramObject);
/*      */ 
/*  830 */       break;
/*      */     case 104:
/*  833 */       localObject = new ROWID((byte[])paramObject);
/*      */ 
/*  835 */       break;
/*      */     case 102:
/*  844 */       DatabaseError.throwSqlException(1, "need resolution: do we want to handle ResultSet");
/*      */ 
/*  847 */       break;
/*      */     case 12:
/*  850 */       localObject = new DATE(paramObject);
/*      */ 
/*  852 */       break;
/*      */     case 180:
/*  855 */       if ((paramObject instanceof TIMESTAMP))
/*      */       {
/*  857 */         localObject = (Datum)paramObject;
/*      */       }
/*  859 */       else if ((paramObject instanceof Timestamp)) {
/*  860 */         localObject = new TIMESTAMP((Timestamp)paramObject);
/*  861 */       } else if ((paramObject instanceof Date)) {
/*  862 */         localObject = new TIMESTAMP((Date)paramObject);
/*  863 */       } else if ((paramObject instanceof Time)) {
/*  864 */         localObject = new TIMESTAMP((Time)paramObject);
/*  865 */       } else if ((paramObject instanceof DATE)) {
/*  866 */         localObject = new TIMESTAMP((DATE)paramObject);
/*  867 */       } else if ((paramObject instanceof String)) {
/*  868 */         localObject = new TIMESTAMP((String)paramObject); } else {
/*  869 */         if (!(paramObject instanceof byte[])) break;
/*  870 */         localObject = new TIMESTAMP((byte[])paramObject); } break;
/*      */     case 181:
/*  875 */       if ((paramObject instanceof TIMESTAMPTZ))
/*      */       {
/*  877 */         localObject = (Datum)paramObject;
/*      */       }
/*  879 */       else if ((paramObject instanceof Timestamp)) {
/*  880 */         localObject = new TIMESTAMPTZ(paramOracleConnection, (Timestamp)paramObject);
/*  881 */       } else if ((paramObject instanceof Date)) {
/*  882 */         localObject = new TIMESTAMPTZ(paramOracleConnection, (Date)paramObject);
/*  883 */       } else if ((paramObject instanceof Time)) {
/*  884 */         localObject = new TIMESTAMPTZ(paramOracleConnection, (Time)paramObject);
/*  885 */       } else if ((paramObject instanceof DATE)) {
/*  886 */         localObject = new TIMESTAMPTZ(paramOracleConnection, (DATE)paramObject);
/*  887 */       } else if ((paramObject instanceof String)) {
/*  888 */         localObject = new TIMESTAMPTZ(paramOracleConnection, (String)paramObject); } else {
/*  889 */         if (!(paramObject instanceof byte[])) break;
/*  890 */         localObject = new TIMESTAMPTZ((byte[])paramObject); } break;
/*      */     case 231:
/*  895 */       if ((paramObject instanceof TIMESTAMPLTZ))
/*      */       {
/*  897 */         localObject = (Datum)paramObject;
/*      */       }
/*  899 */       else if ((paramObject instanceof Timestamp)) {
/*  900 */         localObject = new TIMESTAMPLTZ(paramOracleConnection, (Timestamp)paramObject);
/*  901 */       } else if ((paramObject instanceof Date)) {
/*  902 */         localObject = new TIMESTAMPLTZ(paramOracleConnection, (Date)paramObject);
/*  903 */       } else if ((paramObject instanceof Time)) {
/*  904 */         localObject = new TIMESTAMPLTZ(paramOracleConnection, (Time)paramObject);
/*  905 */       } else if ((paramObject instanceof DATE)) {
/*  906 */         localObject = new TIMESTAMPLTZ(paramOracleConnection, (DATE)paramObject);
/*  907 */       } else if ((paramObject instanceof String)) {
/*  908 */         localObject = new TIMESTAMPLTZ(paramOracleConnection, (String)paramObject); } else {
/*  909 */         if (!(paramObject instanceof byte[])) break;
/*  910 */         localObject = new TIMESTAMPLTZ((byte[])paramObject); } break;
/*      */     case 113:
/*  915 */       if (!(paramObject instanceof BLOB))
/*      */         break;
/*  917 */       localObject = (Datum)paramObject; break;
/*      */     case 112:
/*  923 */       if (!(paramObject instanceof CLOB))
/*      */         break;
/*  925 */       localObject = (Datum)paramObject; break;
/*      */     case 114:
/*  931 */       if (!(paramObject instanceof BFILE))
/*      */         break;
/*  933 */       localObject = (Datum)paramObject; break;
/*      */     case 109:
/*  939 */       if ((!(paramObject instanceof STRUCT)) && (!(paramObject instanceof ARRAY)) && (!(paramObject instanceof OPAQUE))) {
/*      */         break;
/*      */       }
/*  942 */       localObject = (Datum)paramObject; break;
/*      */     case 111:
/*  948 */       if (!(paramObject instanceof REF))
/*      */         break;
/*  950 */       localObject = (Datum)paramObject; break;
/*      */     }
/*      */ 
/*  959 */     if (localObject == null)
/*      */     {
/*  964 */       DatabaseError.throwSqlException(1, "Unable to construct a Datum from the specified input");
/*      */     }
/*      */ 
/*  970 */     return (Datum)localObject;
/*      */   }
/*      */ 
/*      */   private static int classNumber(Class paramClass)
/*      */   {
/*  987 */     int i = -1;
/*  988 */     Integer localInteger = (Integer)classTable.get(paramClass);
/*      */ 
/*  990 */     if (localInteger != null)
/*      */     {
/*  992 */       i = localInteger.intValue();
/*      */     }
/*      */ 
/*  997 */     return i;
/*      */   }
/*      */ 
/*      */   public static Object getTypeDescriptor(String paramString, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/* 1033 */     Object localObject = null;
/*      */ 
/* 1038 */     SQLName localSQLName = new SQLName(paramString, paramOracleConnection);
/* 1039 */     String str = localSQLName.getName();
/*      */ 
/* 1044 */     localObject = paramOracleConnection.getDescriptor(str);
/*      */ 
/* 1046 */     if (localObject != null)
/*      */     {
/* 1051 */       return localObject;
/*      */     }
/*      */ 
/* 1057 */     OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
/* 1058 */     localOracleTypeADT.init(paramOracleConnection);
/*      */ 
/* 1060 */     OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();
/*      */ 
/* 1065 */     switch (localOracleNamedType.getTypeCode())
/*      */     {
/*      */     case 2003:
/* 1070 */       localObject = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);
/*      */ 
/* 1074 */       break;
/*      */     case 2002:
/*      */     case 2008:
/* 1080 */       localObject = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);
/*      */ 
/* 1084 */       break;
/*      */     case 2007:
/* 1088 */       localObject = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);
/*      */ 
/* 1092 */       break;
/*      */     case 2004:
/*      */     case 2005:
/*      */     case 2006:
/*      */     default:
/* 1098 */       DatabaseError.throwSqlException(1, "Unrecognized type code");
/*      */     }
/*      */ 
/* 1105 */     paramOracleConnection.putDescriptor(str, localObject);
/*      */ 
/* 1110 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static boolean checkDatumType(Datum paramDatum, int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1140 */     boolean bool = false;
/*      */ 
/* 1142 */     switch (paramInt)
/*      */     {
/*      */     case 1:
/*      */     case 8:
/*      */     case 96:
/* 1150 */       bool = paramDatum instanceof CHAR;
/*      */ 
/* 1152 */       break;
/*      */     case 2:
/*      */     case 6:
/* 1157 */       bool = paramDatum instanceof NUMBER;
/*      */ 
/* 1159 */       break;
/*      */     case 100:
/* 1162 */       bool = paramDatum instanceof BINARY_FLOAT;
/*      */ 
/* 1164 */       break;
/*      */     case 101:
/* 1167 */       bool = paramDatum instanceof BINARY_DOUBLE;
/*      */ 
/* 1169 */       break;
/*      */     case 23:
/*      */     case 24:
/* 1174 */       bool = paramDatum instanceof RAW;
/*      */ 
/* 1176 */       break;
/*      */     case 104:
/* 1179 */       bool = paramDatum instanceof ROWID;
/*      */ 
/* 1181 */       break;
/*      */     case 12:
/* 1184 */       bool = paramDatum instanceof DATE;
/*      */ 
/* 1186 */       break;
/*      */     case 180:
/* 1189 */       bool = paramDatum instanceof TIMESTAMP;
/*      */ 
/* 1191 */       break;
/*      */     case 181:
/* 1194 */       bool = paramDatum instanceof TIMESTAMPTZ;
/*      */ 
/* 1196 */       break;
/*      */     case 231:
/* 1199 */       bool = paramDatum instanceof TIMESTAMPLTZ;
/*      */ 
/* 1201 */       break;
/*      */     case 113:
/* 1204 */       bool = paramDatum instanceof BLOB;
/*      */ 
/* 1206 */       break;
/*      */     case 112:
/* 1209 */       bool = paramDatum instanceof CLOB;
/*      */ 
/* 1211 */       break;
/*      */     case 114:
/* 1214 */       bool = paramDatum instanceof BFILE;
/*      */ 
/* 1216 */       break;
/*      */     case 111:
/* 1219 */       bool = ((paramDatum instanceof REF)) && (((REF)paramDatum).getBaseTypeName().equals(paramString));
/*      */ 
/* 1222 */       break;
/*      */     case 109:
/* 1225 */       if ((paramDatum instanceof STRUCT))
/*      */       {
/* 1227 */         bool = ((STRUCT)paramDatum).isInHierarchyOf(paramString);
/*      */       }
/* 1229 */       else if ((paramDatum instanceof ARRAY))
/*      */       {
/* 1231 */         bool = ((ARRAY)paramDatum).getSQLTypeName().equals(paramString);
/*      */       } else {
/* 1233 */         if (!(paramDatum instanceof OPAQUE))
/*      */           break;
/* 1235 */         bool = ((OPAQUE)paramDatum).getSQLTypeName().equals(paramString); } break;
/*      */     case 102:
/*      */     default:
/* 1243 */       bool = false;
/*      */     }
/*      */ 
/* 1248 */     return bool;
/*      */   }
/*      */ 
/*      */   public static boolean implementsInterface(Class paramClass1, Class paramClass2)
/*      */   {
/* 1268 */     if (paramClass1 == null)
/*      */     {
/* 1270 */       return false;
/*      */     }
/*      */ 
/* 1273 */     if (paramClass1 == paramClass2)
/*      */     {
/* 1275 */       return true;
/*      */     }
/*      */ 
/* 1278 */     Class[] arrayOfClass = paramClass1.getInterfaces();
/*      */ 
/* 1280 */     for (int i = 0; i < arrayOfClass.length; i++)
/*      */     {
/* 1282 */       if (implementsInterface(arrayOfClass[i], paramClass2))
/*      */       {
/* 1284 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1288 */     return implementsInterface(paramClass1.getSuperclass(), paramClass2);
/*      */   }
/*      */ 
/*      */   public static Datum makeOracleDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 1317 */     return makeOracleDatum(paramOracleConnection, paramObject, paramInt, paramString, false);
/*      */   }
/*      */ 
/*      */   public static Datum makeOracleDatum(OracleConnection paramOracleConnection, Object paramObject, int paramInt, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1332 */     Datum localDatum = makeDatum(paramOracleConnection, paramObject, getInternalType(paramInt), paramString, paramBoolean);
/*      */ 
/* 1337 */     return localDatum;
/*      */   }
/*      */ 
/*      */   public static int getInternalType(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1346 */     int i = 0;
/*      */ 
/* 1348 */     switch (paramInt)
/*      */     {
/*      */     case -7:
/*      */     case -6:
/*      */     case -5:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/* 1370 */       i = 6;
/*      */ 
/* 1372 */       break;
/*      */     case 100:
/* 1375 */       i = 100;
/*      */ 
/* 1377 */       break;
/*      */     case 101:
/* 1380 */       i = 101;
/*      */ 
/* 1382 */       break;
/*      */     case 999:
/* 1385 */       i = 999;
/*      */ 
/* 1387 */       break;
/*      */     case 1:
/* 1390 */       i = 96;
/*      */ 
/* 1392 */       break;
/*      */     case 12:
/* 1395 */       i = 1;
/*      */ 
/* 1397 */       break;
/*      */     case -1:
/* 1400 */       i = 8;
/*      */ 
/* 1402 */       break;
/*      */     case 91:
/*      */     case 92:
/* 1407 */       i = 12;
/*      */ 
/* 1409 */       break;
/*      */     case -100:
/*      */     case 93:
/* 1414 */       i = 180;
/*      */ 
/* 1416 */       break;
/*      */     case -101:
/* 1419 */       i = 181;
/*      */ 
/* 1421 */       break;
/*      */     case -102:
/* 1424 */       i = 231;
/*      */ 
/* 1426 */       break;
/*      */     case -104:
/* 1429 */       i = 183;
/*      */ 
/* 1431 */       break;
/*      */     case -103:
/* 1434 */       i = 182;
/*      */ 
/* 1436 */       break;
/*      */     case -3:
/*      */     case -2:
/* 1441 */       i = 23;
/*      */ 
/* 1443 */       break;
/*      */     case -4:
/* 1446 */       i = 24;
/*      */ 
/* 1448 */       break;
/*      */     case -8:
/* 1451 */       i = 104;
/*      */ 
/* 1453 */       break;
/*      */     case 2004:
/* 1456 */       i = 113;
/*      */ 
/* 1458 */       break;
/*      */     case 2005:
/* 1461 */       i = 112;
/*      */ 
/* 1463 */       break;
/*      */     case -13:
/* 1466 */       i = 114;
/*      */ 
/* 1468 */       break;
/*      */     case -10:
/* 1471 */       i = 102;
/*      */ 
/* 1473 */       break;
/*      */     case 2002:
/*      */     case 2003:
/*      */     case 2007:
/*      */     case 2008:
/* 1482 */       i = 109;
/*      */ 
/* 1484 */       break;
/*      */     case 2006:
/* 1487 */       i = 111;
/*      */ 
/* 1489 */       break;
/*      */     default:
/* 1495 */       DatabaseError.throwSqlException(4, "get_internal_type");
/*      */     }
/*      */ 
/* 1501 */     return i;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 1714 */       classTable.put(Class.forName("java.lang.String"), new Integer(0));
/*      */ 
/* 1716 */       classTable.put(Class.forName("java.lang.Boolean"), new Integer(1));
/*      */ 
/* 1718 */       classTable.put(Class.forName("java.lang.Integer"), new Integer(2));
/*      */ 
/* 1720 */       classTable.put(Class.forName("java.lang.Long"), new Integer(3));
/* 1721 */       classTable.put(Class.forName("java.lang.Float"), new Integer(4));
/*      */ 
/* 1723 */       classTable.put(Class.forName("java.lang.Double"), new Integer(5));
/*      */ 
/* 1725 */       classTable.put(Class.forName("java.math.BigDecimal"), new Integer(6));
/*      */ 
/* 1727 */       classTable.put(Class.forName("java.sql.Date"), new Integer(7));
/* 1728 */       classTable.put(Class.forName("java.sql.Time"), new Integer(8));
/* 1729 */       classTable.put(Class.forName("java.sql.Timestamp"), new Integer(9));
/*      */     }
/*      */     catch (ClassNotFoundException localClassNotFoundException)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.SQLUtil
 * JD-Core Version:    0.6.0
 */