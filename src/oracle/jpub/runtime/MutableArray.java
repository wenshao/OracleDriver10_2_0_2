/*      */ package oracle.jpub.runtime;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import oracle.jdbc.driver.OracleLog;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import oracle.sql.BFILE;
/*      */ import oracle.sql.BINARY_DOUBLE;
/*      */ import oracle.sql.BINARY_FLOAT;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.CHAR;
/*      */ import oracle.sql.CLOB;
/*      */ import oracle.sql.CustomDatum;
/*      */ import oracle.sql.CustomDatumFactory;
/*      */ import oracle.sql.DATE;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.INTERVALDS;
/*      */ import oracle.sql.INTERVALYM;
/*      */ import oracle.sql.NUMBER;
/*      */ import oracle.sql.ORAData;
/*      */ import oracle.sql.ORADataFactory;
/*      */ import oracle.sql.RAW;
/*      */ import oracle.sql.TIMESTAMP;
/*      */ import oracle.sql.TIMESTAMPLTZ;
/*      */ import oracle.sql.TIMESTAMPTZ;
/*      */ 
/*      */ public class MutableArray
/*      */ {
/*      */   int length;
/*      */   Object[] elements;
/*      */   Datum[] datums;
/*      */   ARRAY pickled;
/*      */   boolean pickledCorrect;
/*      */   int sqlType;
/*      */   ORADataFactory factory;
/*      */   CustomDatumFactory old_factory;
/* 1494 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*      */ 
/*      */   public MutableArray(int paramInt, ARRAY paramARRAY, ORADataFactory paramORADataFactory)
/*      */   {
/*   89 */     this.length = -1;
/*   90 */     this.elements = null;
/*   91 */     this.datums = null;
/*   92 */     this.pickled = paramARRAY;
/*   93 */     this.pickledCorrect = true;
/*   94 */     this.sqlType = paramInt;
/*   95 */     this.factory = paramORADataFactory;
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, Datum[] paramArrayOfDatum, ORADataFactory paramORADataFactory)
/*      */   {
/*  115 */     this.sqlType = paramInt;
/*  116 */     this.factory = paramORADataFactory;
/*      */ 
/*  118 */     setDatumArray(paramArrayOfDatum);
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, Object[] paramArrayOfObject, ORADataFactory paramORADataFactory)
/*      */   {
/*  138 */     this.sqlType = paramInt;
/*  139 */     this.factory = paramORADataFactory;
/*      */ 
/*  141 */     setObjectArray(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, double[] paramArrayOfDouble, ORADataFactory paramORADataFactory)
/*      */   {
/*  161 */     this.sqlType = paramInt;
/*  162 */     this.factory = paramORADataFactory;
/*      */ 
/*  164 */     setArray(paramArrayOfDouble);
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, int[] paramArrayOfInt, ORADataFactory paramORADataFactory)
/*      */   {
/*  184 */     this.sqlType = paramInt;
/*  185 */     this.factory = paramORADataFactory;
/*      */ 
/*  187 */     setArray(paramArrayOfInt);
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, float[] paramArrayOfFloat, ORADataFactory paramORADataFactory)
/*      */   {
/*  207 */     this.sqlType = paramInt;
/*  208 */     this.factory = paramORADataFactory;
/*      */ 
/*  210 */     setArray(paramArrayOfFloat);
/*      */   }
/*      */ 
/*      */   public MutableArray(int paramInt, short[] paramArrayOfShort, ORADataFactory paramORADataFactory)
/*      */   {
/*  230 */     this.sqlType = paramInt;
/*  231 */     this.factory = paramORADataFactory;
/*      */ 
/*  233 */     setArray(paramArrayOfShort);
/*      */   }
/*      */ 
/*      */   public MutableArray(ARRAY paramARRAY, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  253 */     this.length = -1;
/*  254 */     this.elements = null;
/*  255 */     this.datums = null;
/*  256 */     this.pickled = paramARRAY;
/*  257 */     this.pickledCorrect = true;
/*  258 */     this.sqlType = paramInt;
/*  259 */     this.old_factory = paramCustomDatumFactory;
/*      */   }
/*      */ 
/*      */   public MutableArray(Datum[] paramArrayOfDatum, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  279 */     this.sqlType = paramInt;
/*  280 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  282 */     setDatumArray(paramArrayOfDatum);
/*      */   }
/*      */ 
/*      */   public MutableArray(Object[] paramArrayOfObject, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  302 */     this.sqlType = paramInt;
/*  303 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  305 */     setObjectArray(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public MutableArray(double[] paramArrayOfDouble, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  325 */     this.sqlType = paramInt;
/*  326 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  328 */     setArray(paramArrayOfDouble);
/*      */   }
/*      */ 
/*      */   public MutableArray(int[] paramArrayOfInt, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  348 */     this.sqlType = paramInt;
/*  349 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  351 */     setArray(paramArrayOfInt);
/*      */   }
/*      */ 
/*      */   public MutableArray(float[] paramArrayOfFloat, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  371 */     this.sqlType = paramInt;
/*  372 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  374 */     setArray(paramArrayOfFloat);
/*      */   }
/*      */ 
/*      */   public MutableArray(short[] paramArrayOfShort, int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */   {
/*  394 */     this.sqlType = paramInt;
/*  395 */     this.old_factory = paramCustomDatumFactory;
/*      */ 
/*  397 */     setArray(paramArrayOfShort);
/*      */   }
/*      */ 
/*      */   public Datum toDatum(Connection paramConnection, String paramString)
/*      */     throws SQLException
/*      */   {
/*  418 */     if (!this.pickledCorrect)
/*      */     {
/*  422 */       this.pickled = new ARRAY(ArrayDescriptor.createDescriptor(paramString, paramConnection), paramConnection, getDatumArray(paramConnection));
/*      */ 
/*  424 */       this.pickledCorrect = true;
/*      */     }
/*      */ 
/*  440 */     return this.pickled;
/*      */   }
/*      */ 
/*      */   public Datum toDatum(oracle.jdbc.OracleConnection paramOracleConnection, String paramString)
/*      */     throws SQLException
/*      */   {
/*  446 */     return toDatum(paramOracleConnection, paramString);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Datum toDatum(oracle.jdbc.driver.OracleConnection paramOracleConnection, String paramString)
/*      */     throws SQLException
/*      */   {
/*  455 */     return toDatum(paramOracleConnection, paramString);
/*      */   }
/*      */ 
/*      */   public Object[] getOracleArray()
/*      */     throws SQLException
/*      */   {
/*  461 */     return getOracleArray(0L, 2147483647);
/*      */   }
/*      */ 
/*      */   public Object[] getOracleArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  485 */     int i = sliceLength(paramLong, paramInt);
/*      */ 
/*  487 */     if (i < 0)
/*  488 */       return null;
/*      */     Object localObject;
/*  492 */     switch (this.sqlType)
/*      */     {
/*      */     case -13:
/*  496 */       localObject = new BFILE[i];
/*      */ 
/*  498 */       break;
/*      */     case 2004:
/*  501 */       localObject = new BLOB[i];
/*      */ 
/*  503 */       break;
/*      */     case 1:
/*      */     case 12:
/*  508 */       localObject = new CHAR[i];
/*      */ 
/*  510 */       break;
/*      */     case 2005:
/*  513 */       localObject = new CLOB[i];
/*      */ 
/*  515 */       break;
/*      */     case 91:
/*  518 */       localObject = new DATE[i];
/*      */ 
/*  520 */       break;
/*      */     case 93:
/*  523 */       localObject = new TIMESTAMP[i];
/*      */ 
/*  525 */       break;
/*      */     case -101:
/*  528 */       localObject = new TIMESTAMPTZ[i];
/*      */ 
/*  530 */       break;
/*      */     case -102:
/*  533 */       localObject = new TIMESTAMPLTZ[i];
/*      */ 
/*  535 */       break;
/*      */     case -104:
/*  538 */       localObject = new INTERVALDS[i];
/*      */ 
/*  540 */       break;
/*      */     case -103:
/*  543 */       localObject = new INTERVALYM[i];
/*      */ 
/*  545 */       break;
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*  560 */       localObject = new NUMBER[i];
/*      */ 
/*  562 */       break;
/*      */     case -2:
/*  565 */       localObject = new RAW[i];
/*      */ 
/*  567 */       break;
/*      */     case 100:
/*  570 */       localObject = new BINARY_FLOAT[i];
/*      */ 
/*  572 */       break;
/*      */     case 101:
/*  575 */       localObject = new BINARY_DOUBLE[i];
/*      */ 
/*  577 */       break;
/*      */     case 0:
/*      */     case 2002:
/*      */     case 2003:
/*      */     case 2006:
/*      */     case 2007:
/*  588 */       if (this.old_factory == null)
/*      */       {
/*  590 */         localObject = new ORAData[i];
/*      */       }
/*      */       else
/*      */       {
/*  594 */         localObject = new CustomDatum[i];
/*      */       }
/*      */ 
/*  597 */       break;
/*      */     default:
/*  600 */       throw new SQLException("Unexpected OracleTypes type code: " + this.sqlType);
/*      */     }
/*      */ 
/*  603 */     return (Object)getOracleArray(paramLong, localObject);
/*      */   }
/*      */ 
/*      */   public Object[] getOracleArray(long paramLong, Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  623 */     if (paramArrayOfObject == null) {
/*  624 */       return null;
/*      */     }
/*  626 */     int i = sliceLength(paramLong, paramArrayOfObject.length);
/*  627 */     int j = (int)paramLong;
/*      */ 
/*  629 */     if (i != paramArrayOfObject.length)
/*  630 */       return null;
/*      */     int k;
/*  632 */     if ((this.sqlType == 2002) || (this.sqlType == 2007) || (this.sqlType == 2003) || (this.sqlType == 2006) || (this.sqlType == 0))
/*      */     {
/*  636 */       if (this.old_factory == null)
/*      */       {
/*  638 */         for (k = 0; k < i; k++) {
/*  639 */           paramArrayOfObject[k] = this.factory.create(getDatumElement(j++, null), this.sqlType);
/*      */         }
/*      */       }
/*      */ 
/*  643 */       for (k = 0; k < i; ) {
/*  644 */         paramArrayOfObject[k] = this.old_factory.create(getDatumElement(j++, null), this.sqlType);
/*      */ 
/*  643 */         k++; continue;
/*      */ 
/*  649 */         for (k = 0; k < i; k++) {
/*  650 */           paramArrayOfObject[k] = getDatumElement(j++, null);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  666 */     return paramArrayOfObject;
/*      */   }
/*      */ 
/*      */   public Object[] getOracleArray(Object[] paramArrayOfObject) throws SQLException
/*      */   {
/*  671 */     return getOracleArray(0L, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public Object[] getObjectArray() throws SQLException
/*      */   {
/*  676 */     return getObjectArray(0L, 2147483647);
/*      */   }
/*      */ 
/*      */   public Object[] getObjectArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  696 */     int i = sliceLength(paramLong, paramInt);
/*      */ 
/*  698 */     if (i < 0)
/*  699 */       return null;
/*      */     Object localObject;
/*  703 */     switch (this.sqlType)
/*      */     {
/*      */     case 1:
/*      */     case 12:
/*  709 */       localObject = new String[i];
/*      */ 
/*  711 */       break;
/*      */     case 91:
/*  714 */       localObject = new Date[i];
/*      */ 
/*  716 */       break;
/*      */     case 93:
/*  719 */       localObject = new Timestamp[i];
/*      */ 
/*  721 */       break;
/*      */     case 2:
/*      */     case 3:
/*  726 */       localObject = new BigDecimal[i];
/*      */ 
/*  728 */       break;
/*      */     case 6:
/*      */     case 8:
/*  733 */       localObject = new Double[i];
/*      */ 
/*  735 */       break;
/*      */     case 4:
/*      */     case 5:
/*  740 */       localObject = new Integer[i];
/*      */ 
/*  742 */       break;
/*      */     case 7:
/*  745 */       localObject = new Float[i];
/*      */ 
/*  747 */       break;
/*      */     case -2:
/*  750 */       localObject = new byte[i][];
/*      */ 
/*  752 */       break;
/*      */     default:
/*  755 */       return getOracleArray(paramLong, paramInt);
/*      */     }
/*      */ 
/*  758 */     return (Object)getObjectArray(paramLong, localObject);
/*      */   }
/*      */ 
/*      */   public Object[] getObjectArray(long paramLong, Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  778 */     if (paramArrayOfObject == null) {
/*  779 */       return null;
/*      */     }
/*  781 */     int i = sliceLength(paramLong, paramArrayOfObject.length);
/*  782 */     int j = (int)paramLong;
/*      */ 
/*  784 */     if (i != paramArrayOfObject.length) {
/*  785 */       return null;
/*      */     }
/*  787 */     switch (this.sqlType)
/*      */     {
/*      */     case -2:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*      */     case 12:
/*      */     case 91:
/*      */     case 93:
/*  803 */       for (int k = 0; k < i; k++) {
/*  804 */         paramArrayOfObject[k] = getObjectElement(j++);
/*      */       }
/*  806 */       return paramArrayOfObject;
/*      */     }
/*      */ 
/*  810 */     return getOracleArray(paramLong, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public Object[] getObjectArray(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  816 */     return getObjectArray(0L, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public Object getArray() throws SQLException
/*      */   {
/*  821 */     return getArray(0L, 2147483647);
/*      */   }
/*      */ 
/*      */   public Object getArray(long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  841 */     int i = sliceLength(paramLong, paramInt);
/*  842 */     int j = (int)paramLong;
/*      */ 
/*  844 */     if (i < 0)
/*  845 */       return null;
/*      */     Object localObject;
/*      */     int k;
/*  847 */     switch (this.sqlType)
/*      */     {
/*      */     case 6:
/*      */     case 8:
/*      */     case 101:
/*  856 */       localObject = new double[i];
/*      */ 
/*  858 */       for (k = 0; k < i; k++) {
/*  859 */         localObject[k] = ((Double)getObjectElement(j++)).doubleValue();
/*      */       }
/*  861 */       return localObject;
/*      */     case 100:
/*  866 */       localObject = new float[i];
/*      */ 
/*  868 */       for (k = 0; k < i; k++) {
/*  869 */         localObject[k] = ((Float)getObjectElement(j++)).floatValue();
/*      */       }
/*  871 */       return localObject;
/*      */     case 4:
/*  876 */       localObject = new int[i];
/*      */ 
/*  878 */       for (k = 0; k < i; k++) {
/*  879 */         localObject[k] = ((Integer)getObjectElement(j++)).intValue();
/*      */       }
/*  881 */       return localObject;
/*      */     case 5:
/*  886 */       localObject = new short[i];
/*      */ 
/*  888 */       for (k = 0; k < i; k++) {
/*  889 */         localObject[k] = (short)((Integer)getObjectElement(j++)).intValue();
/*      */       }
/*  891 */       return localObject;
/*      */     case 7:
/*  896 */       localObject = new float[i];
/*      */ 
/*  898 */       for (k = 0; k < i; k++) {
/*  899 */         localObject[k] = ((Float)getObjectElement(j++)).floatValue();
/*      */       }
/*  901 */       return localObject;
/*      */     }
/*      */ 
/*  905 */     return getObjectArray(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   public void setOracleArray(Object[] paramArrayOfObject)
/*      */   {
/*  911 */     if ((this.factory == null) && (this.old_factory == null))
/*  912 */       setDatumArray((Datum[])paramArrayOfObject);
/*      */     else
/*  914 */       setObjectArray(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public void setOracleArray(Object[] paramArrayOfObject, long paramLong) throws SQLException
/*      */   {
/*  919 */     if ((this.factory == null) && (this.old_factory == null))
/*  920 */       setDatumArray((Datum[])paramArrayOfObject, paramLong);
/*      */     else
/*  922 */       setObjectArray(paramArrayOfObject, paramLong);
/*      */   }
/*      */ 
/*      */   public void setObjectArray(Object[] paramArrayOfObject)
/*      */   {
/*  927 */     if (paramArrayOfObject == null) {
/*  928 */       setNullArray();
/*      */     }
/*      */     else {
/*  931 */       setArrayGeneric(paramArrayOfObject.length);
/*      */ 
/*  933 */       this.elements = new Object[this.length];
/*      */ 
/*  935 */       for (int i = 0; i < this.length; i++)
/*  936 */         this.elements[i] = paramArrayOfObject[i];
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setObjectArray(Object[] paramArrayOfObject, long paramLong) throws SQLException
/*      */   {
/*  942 */     if (paramArrayOfObject == null) {
/*  943 */       return;
/*      */     }
/*  945 */     int i = sliceLength(paramLong, paramArrayOfObject.length);
/*  946 */     int j = (int)paramLong;
/*      */ 
/*  948 */     for (int k = 0; k < i; k++)
/*      */     {
/*  950 */       setObjectElement(paramArrayOfObject[k], j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(double[] paramArrayOfDouble)
/*      */   {
/*  956 */     if (paramArrayOfDouble == null) {
/*  957 */       setNullArray();
/*      */     }
/*      */     else {
/*  960 */       setArrayGeneric(paramArrayOfDouble.length);
/*      */ 
/*  962 */       this.elements = new Object[this.length];
/*      */ 
/*  964 */       for (int i = 0; i < this.length; i++)
/*  965 */         this.elements[i] = new Double(paramArrayOfDouble[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(double[] paramArrayOfDouble, long paramLong) throws SQLException
/*      */   {
/*  971 */     if (paramArrayOfDouble == null) {
/*  972 */       return;
/*      */     }
/*  974 */     int i = sliceLength(paramLong, paramArrayOfDouble.length);
/*  975 */     int j = (int)paramLong;
/*      */ 
/*  977 */     for (int k = 0; k < i; k++)
/*      */     {
/*  979 */       setObjectElement(new Double(paramArrayOfDouble[k]), j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(int[] paramArrayOfInt)
/*      */   {
/*  985 */     if (paramArrayOfInt == null) {
/*  986 */       setNullArray();
/*      */     }
/*      */     else {
/*  989 */       setArrayGeneric(paramArrayOfInt.length);
/*      */ 
/*  991 */       this.elements = new Object[this.length];
/*      */ 
/*  993 */       for (int i = 0; i < this.length; i++)
/*  994 */         this.elements[i] = new Integer(paramArrayOfInt[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(int[] paramArrayOfInt, long paramLong) throws SQLException
/*      */   {
/* 1000 */     if (paramArrayOfInt == null) {
/* 1001 */       return;
/*      */     }
/* 1003 */     int i = sliceLength(paramLong, paramArrayOfInt.length);
/* 1004 */     int j = (int)paramLong;
/*      */ 
/* 1006 */     for (int k = 0; k < i; k++)
/*      */     {
/* 1008 */       setObjectElement(new Integer(paramArrayOfInt[k]), j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(float[] paramArrayOfFloat)
/*      */   {
/* 1014 */     if (paramArrayOfFloat == null) {
/* 1015 */       setNullArray();
/*      */     }
/*      */     else {
/* 1018 */       setArrayGeneric(paramArrayOfFloat.length);
/*      */ 
/* 1020 */       this.elements = new Object[this.length];
/*      */ 
/* 1022 */       for (int i = 0; i < this.length; i++)
/* 1023 */         this.elements[i] = new Float(paramArrayOfFloat[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(float[] paramArrayOfFloat, long paramLong) throws SQLException
/*      */   {
/* 1029 */     if (paramArrayOfFloat == null) {
/* 1030 */       return;
/*      */     }
/* 1032 */     int i = sliceLength(paramLong, paramArrayOfFloat.length);
/* 1033 */     int j = (int)paramLong;
/*      */ 
/* 1035 */     for (int k = 0; k < i; k++)
/*      */     {
/* 1037 */       setObjectElement(new Float(paramArrayOfFloat[k]), j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(short[] paramArrayOfShort)
/*      */   {
/* 1043 */     if (paramArrayOfShort == null) {
/* 1044 */       setNullArray();
/*      */     }
/*      */     else {
/* 1047 */       setArrayGeneric(paramArrayOfShort.length);
/*      */ 
/* 1049 */       this.elements = new Object[this.length];
/*      */ 
/* 1051 */       for (int i = 0; i < this.length; i++)
/* 1052 */         this.elements[i] = new Integer(paramArrayOfShort[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setArray(short[] paramArrayOfShort, long paramLong) throws SQLException
/*      */   {
/* 1058 */     if (paramArrayOfShort == null) {
/* 1059 */       return;
/*      */     }
/* 1061 */     int i = sliceLength(paramLong, paramArrayOfShort.length);
/* 1062 */     int j = (int)paramLong;
/*      */ 
/* 1064 */     for (int k = 0; k < i; k++)
/*      */     {
/* 1066 */       setObjectElement(new Integer(paramArrayOfShort[k]), j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object getObjectElement(long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1087 */     Object localObject = getLazyArray()[(int)paramLong];
/*      */ 
/* 1089 */     if (localObject == null)
/*      */     {
/*      */       Datum localDatum;
/* 1091 */       if (this.old_factory == null)
/*      */       {
/* 1093 */         localDatum = getLazyOracleArray()[(int)paramLong];
/*      */ 
/* 1095 */         localObject = Util.convertToObject(localDatum, this.sqlType, this.factory);
/* 1096 */         this.elements[(int)paramLong] = localObject;
/*      */ 
/* 1098 */         if (Util.isMutable(localDatum, this.factory))
/* 1099 */           resetOracleElement(paramLong);
/*      */       }
/*      */       else
/*      */       {
/* 1103 */         localDatum = getLazyOracleArray()[(int)paramLong];
/*      */ 
/* 1105 */         localObject = Util.convertToObject(localDatum, this.sqlType, this.old_factory);
/* 1106 */         this.elements[(int)paramLong] = localObject;
/*      */ 
/* 1108 */         if (Util.isMutable(localDatum, this.old_factory)) {
/* 1109 */           resetOracleElement(paramLong);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1126 */     return localObject;
/*      */   }
/*      */ 
/*      */   public Object getOracleElement(long paramLong) throws SQLException
/*      */   {
/* 1131 */     if ((this.factory == null) && (this.old_factory == null))
/*      */     {
/* 1133 */       Datum localDatum = getDatumElement(paramLong, null);
/*      */ 
/* 1135 */       if (Util.isMutable(localDatum, this.factory)) {
/* 1136 */         this.pickledCorrect = false;
/*      */       }
/* 1138 */       return localDatum;
/*      */     }
/*      */ 
/* 1141 */     return getObjectElement(paramLong);
/*      */   }
/*      */ 
/*      */   public void setObjectElement(Object paramObject, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1160 */     OracleLog.print(this, 2048, 2, 64, "MutableArray.setObjectElement( element = " + paramObject + ",  n = " + paramLong + ")");
/*      */ 
/* 1169 */     if (paramObject == null)
/*      */     {
/* 1171 */       getLazyOracleArray();
/*      */     }
/*      */ 
/* 1174 */     resetOracleElement(paramLong);
/*      */ 
/* 1176 */     getLazyArray()[(int)paramLong] = paramObject;
/*      */   }
/*      */ 
/*      */   public void setOracleElement(Object paramObject, long paramLong) throws SQLException
/*      */   {
/* 1181 */     if ((this.factory == null) && (this.old_factory == null))
/* 1182 */       setDatumElement((Datum)paramObject, paramLong);
/*      */     else
/* 1184 */       setObjectElement(paramObject, paramLong);
/*      */   }
/*      */ 
/*      */   public String getBaseTypeName() throws SQLException
/*      */   {
/* 1189 */     return this.pickled.getBaseTypeName();
/*      */   }
/*      */ 
/*      */   public int getBaseType() throws SQLException
/*      */   {
/* 1194 */     return this.pickled.getBaseType();
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor getDescriptor() throws SQLException
/*      */   {
/* 1199 */     return this.pickled.getDescriptor();
/*      */   }
/*      */ 
/*      */   Datum[] getDatumArray(Connection paramConnection) throws SQLException
/*      */   {
/* 1204 */     if (this.length < 0) {
/* 1205 */       getLazyOracleArray();
/*      */     }
/* 1207 */     if (this.datums == null) {
/* 1208 */       return null;
/*      */     }
/* 1210 */     Datum[] arrayOfDatum = new Datum[this.length];
/*      */ 
/* 1212 */     for (int i = 0; i < this.length; i++) {
/* 1213 */       arrayOfDatum[i] = getDatumElement(i, paramConnection);
/*      */     }
/* 1215 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   void setDatumArray(Datum[] paramArrayOfDatum)
/*      */   {
/* 1220 */     if (paramArrayOfDatum == null) {
/* 1221 */       setNullArray();
/*      */     }
/*      */     else {
/* 1224 */       this.length = paramArrayOfDatum.length;
/* 1225 */       this.elements = null;
/* 1226 */       this.datums = ((Datum[])paramArrayOfDatum.clone());
/* 1227 */       this.pickled = null;
/* 1228 */       this.pickledCorrect = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   void setDatumArray(Datum[] paramArrayOfDatum, long paramLong) throws SQLException
/*      */   {
/* 1234 */     if (paramArrayOfDatum == null) {
/* 1235 */       return;
/*      */     }
/* 1237 */     int i = sliceLength(paramLong, paramArrayOfDatum.length);
/* 1238 */     int j = (int)paramLong;
/*      */ 
/* 1240 */     for (int k = 0; k < i; k++)
/*      */     {
/* 1242 */       setDatumElement(paramArrayOfDatum[k], j++);
/*      */     }
/*      */   }
/*      */ 
/*      */   Datum getDatumElement(long paramLong, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/* 1263 */     Datum localDatum = getLazyOracleArray()[(int)paramLong];
/*      */ 
/* 1265 */     if (localDatum == null)
/*      */     {
/* 1267 */       Object localObject = getLazyArray()[(int)paramLong];
/*      */ 
/* 1273 */       localDatum = Util.convertToOracle(localObject, paramConnection);
/* 1274 */       this.datums[(int)paramLong] = localDatum;
/*      */     }
/*      */ 
/* 1290 */     return localDatum;
/*      */   }
/*      */ 
/*      */   void setDatumElement(Datum paramDatum, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1310 */     resetElement(paramLong);
/*      */ 
/* 1312 */     getLazyOracleArray()[(int)paramLong] = paramDatum;
/* 1313 */     this.pickledCorrect = false;
/*      */   }
/*      */ 
/*      */   void resetElement(long paramLong) throws SQLException
/*      */   {
/* 1318 */     if (this.elements != null)
/*      */     {
/* 1320 */       this.elements[(int)paramLong] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   void setNullArray()
/*      */   {
/* 1326 */     this.length = -1;
/* 1327 */     this.elements = null;
/* 1328 */     this.datums = null;
/* 1329 */     this.pickled = null;
/* 1330 */     this.pickledCorrect = false;
/*      */   }
/*      */ 
/*      */   void setArrayGeneric(int paramInt)
/*      */   {
/* 1335 */     this.length = paramInt;
/* 1336 */     this.datums = new Datum[paramInt];
/* 1337 */     this.pickled = null;
/* 1338 */     this.pickledCorrect = false;
/*      */   }
/*      */ 
/*      */   public int length() throws SQLException
/*      */   {
/* 1343 */     if (this.length < 0) {
/* 1344 */       getLazyOracleArray();
/*      */     }
/* 1346 */     return this.length;
/*      */   }
/*      */ 
/*      */   public int sliceLength(long paramLong, int paramInt) throws SQLException
/*      */   {
/* 1351 */     if (this.length < 0) {
/* 1352 */       getLazyOracleArray();
/*      */     }
/* 1354 */     if (paramLong < 0L) {
/* 1355 */       return (int)paramLong;
/*      */     }
/* 1357 */     return Math.min(this.length - (int)paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   void resetOracleElement(long paramLong)
/*      */     throws SQLException
/*      */   {
/* 1376 */     if (this.datums != null)
/*      */     {
/* 1378 */       this.datums[(int)paramLong] = null;
/*      */     }
/*      */ 
/* 1386 */     this.pickledCorrect = false;
/*      */   }
/*      */ 
/*      */   Object[] getLazyArray() throws SQLException
/*      */   {
/* 1391 */     if (this.length == -1) {
/* 1392 */       getLazyOracleArray();
/*      */     }
/* 1394 */     if (this.elements == null)
/*      */     {
/* 1396 */       this.elements = new Object[this.length];
/*      */     }
/*      */ 
/* 1399 */     return this.elements;
/*      */   }
/*      */ 
/*      */   Datum[] getLazyOracleArray()
/*      */     throws SQLException
/*      */   {
/* 1419 */     if (this.datums == null)
/*      */     {
/* 1423 */       if (this.pickled != null)
/*      */       {
/* 1427 */         this.datums = ((Datum[])this.pickled.getOracleArray());
/* 1428 */         this.length = this.datums.length;
/* 1429 */         this.pickledCorrect = true;
/*      */ 
/* 1435 */         if (this.elements != null)
/*      */         {
/* 1437 */           for (int i = 0; i < this.length; i++)
/*      */           {
/* 1439 */             if (this.elements[i] == null)
/*      */               continue;
/* 1441 */             this.datums[i] = null;
/* 1442 */             this.pickledCorrect = false;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/* 1449 */       else if (this.length >= 0) {
/* 1450 */         this.datums = new Datum[this.length];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1467 */     return this.datums;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jpub.runtime.MutableArray
 * JD-Core Version:    0.6.0
 */