/*      */ package oracle.sql;
/*      */ 
/*      */ import B;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLInput;
/*      */ import java.sql.SQLOutput;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import oracle.jdbc.OracleCallableStatement;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.oracore.OracleNamedType;
/*      */ import oracle.jdbc.oracore.OracleType;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ 
/*      */ public class StructDescriptor extends TypeDescriptor
/*      */   implements Serializable
/*      */ {
/*      */   static final boolean DEBUG = false;
/*      */   static final long serialVersionUID = 1013921343538311063L;
/*  112 */   transient Boolean isInstanciable = null;
/*  113 */   transient String supertype = null;
/*  114 */   transient int numLocalAttrs = -1;
/*  115 */   transient String[] subtypes = null;
/*  116 */   transient String[] attrJavaNames = null;
/*      */ 
/* 1416 */   final int LOCAL_TYPE = 0;
/* 1417 */   final int LOOK_FOR_USER_SYNONYM = 1;
/* 1418 */   final int LOOK_FOR_PUBLIC_SYNONYM = 2;
/* 1419 */   final String[] initMetaData1_9_0_SQL = { "SELECT INSTANTIABLE, supertype_owner, supertype_name, LOCAL_ATTRIBUTES FROM all_types WHERE type_name = :1 AND owner = :2 ", "DECLARE \n bind_synonym_name user_synonyms.synonym_name%type := :1; \n the_table_owner  user_synonyms.table_owner%type; \n the_table_name   user_synonyms.table_name%type; \n the_db_link      user_synonyms.db_link%type; \n sql_string       VARCHAR2(1000); \nBEGIN \n   SELECT /*+RULE*/ TABLE_NAME, TABLE_OWNER, DB_LINK INTO  \n         the_table_name, the_table_owner, the_db_link \n         FROM USER_SYNONYMS WHERE \n         SYNONYM_NAME = bind_synonym_name; \n \n   sql_string := 'SELECT /*+RULE*/ INSTANTIABLE, SUPERTYPE_OWNER,      SUPERTYPE_NAME, LOCAL_ATTRIBUTES FROM ALL_TYPES'; \n \n   IF the_db_link IS NOT NULL  \n   THEN \n     sql_string := sql_string || '@' || the_db_link; \n   END IF; \n   sql_string := sql_string       || ' WHERE TYPE_NAME = '''       || the_table_name   || ''' AND OWNER = '''       || the_table_owner  || ''''; \n   OPEN :2 FOR sql_string; \nEND;", "DECLARE \n bind_synonym_name user_synonyms.synonym_name%type := :1; \n the_table_owner  user_synonyms.table_owner%type; \n the_table_name   user_synonyms.table_name%type; \n the_db_link      user_synonyms.db_link%type; \n sql_string       VARCHAR2(1000); \nBEGIN \n   SELECT /*+RULE*/ TABLE_NAME, TABLE_OWNER, DB_LINK INTO  \n         the_table_name, the_table_owner, the_db_link \n         FROM ALL_SYNONYMS WHERE \n         OWNER = 'PUBLIC' AND \n         SYNONYM_NAME = bind_synonym_name; \n \n   sql_string := 'SELECT /*+RULE*/ INSTANTIABLE, SUPERTYPE_OWNER,      SUPERTYPE_NAME, LOCAL_ATTRIBUTES FROM ALL_TYPES'; \n \n   IF the_db_link IS NOT NULL  \n   THEN \n     sql_string := sql_string || '@' || the_db_link; \n   END IF; \n   sql_string := sql_string       || ' WHERE TYPE_NAME = '''       || the_table_name   || ''' AND OWNER = '''       || the_table_owner  || ''''; \n   OPEN :2 FOR sql_string; \nEND;" };
/*      */ 
/* 1794 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*      */ 
/*      */   public static StructDescriptor createDescriptor(String paramString, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  130 */     return createDescriptor(paramString, paramConnection, false, false);
/*      */   }
/*      */ 
/*      */   public static StructDescriptor createDescriptor(String paramString, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  150 */     if ((paramString == null) || (paramString.length() == 0) || (paramConnection == null))
/*      */     {
/*  155 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*      */     }
/*      */ 
/*  159 */     SQLName localSQLName = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);
/*      */ 
/*  161 */     return createDescriptor(localSQLName, paramConnection, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   public static StructDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  181 */     String str = paramSQLName.getName();
/*  182 */     StructDescriptor localStructDescriptor = null;
/*  183 */     if (!paramBoolean2)
/*      */     {
/*  186 */       localStructDescriptor = (StructDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);
/*      */ 
/*  190 */       if (localStructDescriptor == null)
/*      */       {
/*  192 */         localStructDescriptor = new StructDescriptor(paramSQLName, paramConnection);
/*  193 */         if (paramBoolean1) localStructDescriptor.initNamesRecursively();
/*  194 */         ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localStructDescriptor);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  199 */     return localStructDescriptor;
/*      */   }
/*      */ 
/*      */   public static StructDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  214 */     return createDescriptor(paramSQLName, paramConnection, false, false);
/*      */   }
/*      */ 
/*      */   public static StructDescriptor createDescriptor(OracleTypeADT paramOracleTypeADT)
/*      */     throws SQLException
/*      */   {
/*  231 */     String str = paramOracleTypeADT.getFullName();
/*  232 */     oracle.jdbc.internal.OracleConnection localOracleConnection = paramOracleTypeADT.getConnection();
/*  233 */     StructDescriptor localStructDescriptor = (StructDescriptor)localOracleConnection.getDescriptor(str);
/*      */ 
/*  237 */     if (localStructDescriptor == null)
/*      */     {
/*  239 */       SQLName localSQLName = new SQLName(paramOracleTypeADT.getSchemaName(), paramOracleTypeADT.getSimpleName(), paramOracleTypeADT.getConnection());
/*      */ 
/*  241 */       localStructDescriptor = new StructDescriptor(localSQLName, paramOracleTypeADT, localOracleConnection);
/*  242 */       localStructDescriptor.initNamesRecursively();
/*  243 */       localOracleConnection.putDescriptor(str, localStructDescriptor);
/*      */     }
/*      */ 
/*  247 */     return localStructDescriptor;
/*      */   }
/*      */ 
/*      */   public StructDescriptor(String paramString, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  260 */     super(paramString, paramConnection);
/*      */ 
/*  264 */     initPickler();
/*      */   }
/*      */ 
/*      */   public StructDescriptor(SQLName paramSQLName, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  277 */     super(paramSQLName, paramConnection);
/*      */ 
/*  281 */     initPickler();
/*      */   }
/*      */ 
/*      */   public StructDescriptor(SQLName paramSQLName, OracleTypeADT paramOracleTypeADT, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  295 */     super(paramSQLName, paramOracleTypeADT, paramConnection);
/*      */   }
/*      */ 
/*      */   static StructDescriptor createDescriptor(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, oracle.jdbc.internal.OracleConnection paramOracleConnection, byte[] paramArrayOfByte4)
/*      */     throws SQLException
/*      */   {
/*  304 */     OracleTypeADT localOracleTypeADT = new OracleTypeADT(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfByte3, paramOracleConnection, paramArrayOfByte4);
/*  305 */     return new StructDescriptor(paramSQLName, localOracleTypeADT, paramOracleConnection);
/*      */   }
/*      */ 
/*      */   private void initPickler()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  315 */       this.pickler = new OracleTypeADT(getName(), this.connection);
/*      */ 
/*  317 */       ((OracleTypeADT)this.pickler).init(this.connection);
/*      */ 
/*  320 */       this.pickler.setDescriptor(this);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*  327 */       if ((localException instanceof SQLException)) {
/*  328 */         throw ((SQLException)localException);
/*      */       }
/*      */ 
/*  331 */       DatabaseError.throwSqlException(60, "Unable to resolve type \"" + getName() + "\"");
/*      */     }
/*      */   }
/*      */ 
/*      */   public StructDescriptor(OracleTypeADT paramOracleTypeADT, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  352 */     super(paramOracleTypeADT, paramConnection);
/*      */   }
/*      */ 
/*      */   public int getTypeCode()
/*      */     throws SQLException
/*      */   {
/*  363 */     int i = this.pickler.getTypeCode();
/*      */ 
/*  367 */     return i;
/*      */   }
/*      */ 
/*      */   public int getTypeVersion()
/*      */     throws SQLException
/*      */   {
/*  375 */     int i = this.pickler.getTypeVersion();
/*      */ 
/*  379 */     return i;
/*      */   }
/*      */ 
/*      */   byte[] toBytes(STRUCT paramSTRUCT, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  395 */     Object localObject = paramSTRUCT.shareBytes();
/*      */ 
/*  397 */     if (localObject == null)
/*      */     {
/*  399 */       if (paramSTRUCT.datumArray != null)
/*      */       {
/*  401 */         localObject = this.pickler.linearize(paramSTRUCT);
/*      */ 
/*  403 */         if (!paramBoolean) {
/*  404 */           paramSTRUCT.setShareBytes(null);
/*      */         }
/*      */ 
/*      */       }
/*  409 */       else if (paramSTRUCT.objectArray != null)
/*      */       {
/*  411 */         paramSTRUCT.datumArray = toOracleArray(paramSTRUCT.objectArray);
/*  412 */         localObject = this.pickler.linearize(paramSTRUCT);
/*      */ 
/*  414 */         if (!paramBoolean)
/*      */         {
/*  416 */           paramSTRUCT.datumArray = null;
/*      */ 
/*  418 */           paramSTRUCT.setShareBytes(null);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  426 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/*      */     }
/*  436 */     else if (paramSTRUCT.imageLength != 0L)
/*      */     {
/*  438 */       if ((paramSTRUCT.imageOffset != 0L) || (paramSTRUCT.imageLength != localObject.length))
/*      */       {
/*  440 */         byte[] arrayOfByte = new byte[(int)paramSTRUCT.imageLength];
/*      */ 
/*  442 */         System.arraycopy(localObject, (int)paramSTRUCT.imageOffset, arrayOfByte, 0, (int)paramSTRUCT.imageLength);
/*      */ 
/*  444 */         paramSTRUCT.setImage(arrayOfByte, 0L, 0L);
/*      */ 
/*  446 */         localObject = arrayOfByte;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  455 */     return (B)localObject;
/*      */   }
/*      */ 
/*      */   Datum[] toOracleArray(STRUCT paramSTRUCT, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  464 */     Datum[] arrayOfDatum1 = paramSTRUCT.datumArray;
/*  465 */     Datum[] arrayOfDatum2 = null;
/*      */ 
/*  467 */     if (arrayOfDatum1 == null)
/*      */     {
/*  469 */       if (paramSTRUCT.objectArray != null)
/*      */       {
/*  471 */         arrayOfDatum1 = toOracleArray(paramSTRUCT.objectArray);
/*      */       }
/*  473 */       else if (paramSTRUCT.shareBytes() != null)
/*      */       {
/*  475 */         if (((paramSTRUCT.shareBytes()[0] & 0x80) <= 0) && (((OracleTypeADT)this.pickler).isEmbeddedADT()))
/*      */         {
/*  482 */           this.pickler = OracleTypeADT.shallowClone((OracleTypeADT)this.pickler);
/*      */         }
/*      */ 
/*  488 */         this.pickler.unlinearize(paramSTRUCT.shareBytes(), paramSTRUCT.imageOffset, paramSTRUCT, 1, null);
/*      */ 
/*  490 */         arrayOfDatum1 = paramSTRUCT.datumArray;
/*      */ 
/*  492 */         if (!paramBoolean) {
/*  493 */           paramSTRUCT.datumArray = null;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  500 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  505 */     if (paramBoolean)
/*      */     {
/*  507 */       paramSTRUCT.datumArray = arrayOfDatum1;
/*  508 */       arrayOfDatum2 = (Datum[])arrayOfDatum1.clone();
/*      */     }
/*      */     else
/*      */     {
/*  512 */       arrayOfDatum2 = arrayOfDatum1;
/*      */     }
/*      */ 
/*  517 */     return arrayOfDatum2;
/*      */   }
/*      */ 
/*      */   Object[] toArray(STRUCT paramSTRUCT, Map paramMap, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  528 */     Object[] arrayOfObject = null;
/*      */ 
/*  530 */     if (paramSTRUCT.objectArray == null)
/*      */     {
/*  532 */       if (paramSTRUCT.datumArray != null)
/*      */       {
/*  534 */         arrayOfObject = new Object[paramSTRUCT.datumArray.length];
/*      */ 
/*  536 */         for (int i = 0; i < paramSTRUCT.datumArray.length; i++)
/*      */         {
/*  538 */           if (paramSTRUCT.datumArray[i] == null)
/*      */             continue;
/*  540 */           if ((paramSTRUCT.datumArray[i] instanceof STRUCT))
/*  541 */             arrayOfObject[i] = ((STRUCT)paramSTRUCT.datumArray[i]).toJdbc(paramMap);
/*      */           else {
/*  543 */             arrayOfObject[i] = paramSTRUCT.datumArray[i].toJdbc();
/*      */           }
/*      */         }
/*      */       }
/*  547 */       if (paramSTRUCT.shareBytes() != null)
/*      */       {
/*  549 */         if (((paramSTRUCT.shareBytes()[0] & 0x80) <= 0) && (((OracleTypeADT)this.pickler).isEmbeddedADT()))
/*      */         {
/*  556 */           this.pickler = OracleTypeADT.shallowClone((OracleTypeADT)this.pickler);
/*      */         }
/*      */ 
/*  561 */         this.pickler.unlinearize(paramSTRUCT.shareBytes(), paramSTRUCT.imageOffset, paramSTRUCT, 2, paramMap);
/*      */ 
/*  563 */         arrayOfObject = paramSTRUCT.objectArray;
/*      */ 
/*  566 */         paramSTRUCT.objectArray = null;
/*      */       }
/*      */       else
/*      */       {
/*  576 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  584 */       arrayOfObject = (Object[])paramSTRUCT.objectArray.clone();
/*      */     }
/*      */ 
/*  589 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   public int getLength()
/*      */     throws SQLException
/*      */   {
/*  603 */     int i = getFieldTypes().length;
/*      */ 
/*  607 */     return i;
/*      */   }
/*      */ 
/*      */   public OracleTypeADT getOracleTypeADT()
/*      */   {
/*  619 */     OracleTypeADT localOracleTypeADT = (OracleTypeADT)this.pickler;
/*      */ 
/*  621 */     return localOracleTypeADT;
/*      */   }
/*      */ 
/*      */   private OracleType[] getFieldTypes()
/*      */     throws SQLException
/*      */   {
/*  633 */     OracleType[] arrayOfOracleType = ((OracleTypeADT)this.pickler).getAttrTypes();
/*      */ 
/*  637 */     return arrayOfOracleType;
/*      */   }
/*      */ 
/*      */   public SQLInput toJdbc2SQLInput(STRUCT paramSTRUCT, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  653 */     OracleJdbc2SQLInput localOracleJdbc2SQLInput = new OracleJdbc2SQLInput(toOracleArray(paramSTRUCT, false), paramMap, this.connection);
/*      */ 
/*  659 */     return localOracleJdbc2SQLInput;
/*      */   }
/*      */ 
/*      */   public SQLOutput toJdbc2SQLOutput()
/*      */     throws SQLException
/*      */   {
/*  672 */     OracleSQLOutput localOracleSQLOutput = new OracleSQLOutput(this, this.connection);
/*      */ 
/*  676 */     return localOracleSQLOutput;
/*      */   }
/*      */ 
/*      */   public Datum[] toOracleArray(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/*  693 */     Datum[] arrayOfDatum = null;
/*      */ 
/*  695 */     if (paramArrayOfObject != null)
/*      */     {
/*  697 */       OracleType[] arrayOfOracleType = getFieldTypes();
/*  698 */       int i = arrayOfOracleType.length;
/*      */ 
/*  701 */       if (paramArrayOfObject.length != i)
/*      */       {
/*  706 */         DatabaseError.throwSqlException(49, null);
/*      */       }
/*      */ 
/*  711 */       arrayOfDatum = new Datum[i];
/*      */ 
/*  713 */       oracle.jdbc.internal.OracleConnection localOracleConnection = this.connection;
/*      */ 
/*  716 */       for (int j = 0; j < i; j++) {
/*  717 */         arrayOfDatum[j] = arrayOfOracleType[j].toDatum(paramArrayOfObject[j], localOracleConnection);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  722 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public Datum[] toOracleArray(Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  730 */     Datum[] arrayOfDatum = null;
/*  731 */     int i = 0;
/*      */ 
/*  733 */     if (paramMap != null)
/*      */     {
/*  735 */       OracleType[] arrayOfOracleType = getFieldTypes();
/*  736 */       int j = arrayOfOracleType.length;
/*  737 */       int k = paramMap.size();
/*      */ 
/*  740 */       arrayOfDatum = new Datum[j];
/*      */ 
/*  742 */       oracle.jdbc.internal.OracleConnection localOracleConnection = this.connection;
/*      */ 
/*  745 */       for (int m = 0; m < j; m++)
/*      */       {
/*  747 */         Object localObject = paramMap.get(((OracleTypeADT)this.pickler).getAttributeName(m + 1));
/*      */ 
/*  750 */         arrayOfDatum[m] = arrayOfOracleType[m].toDatum(localObject, localOracleConnection);
/*      */ 
/*  752 */         if ((localObject == null) && (!paramMap.containsKey(((OracleTypeADT)this.pickler).getAttributeName(m + 1)))) {
/*      */           continue;
/*      */         }
/*  755 */         i++;
/*      */       }
/*      */ 
/*  758 */       if (i < k)
/*      */       {
/*  765 */         DatabaseError.throwSqlException(68, null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  772 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/*  790 */     ResultSetMetaData localResultSetMetaData = this.connection.newStructMetaData(this);
/*      */ 
/*  794 */     return localResultSetMetaData;
/*      */   }
/*      */ 
/*      */   public boolean isFinalType()
/*      */     throws SQLException
/*      */   {
/*  807 */     boolean bool = getOracleTypeADT().isFinalType();
/*      */ 
/*  811 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean isSubtype()
/*      */     throws SQLException
/*      */   {
/*  824 */     boolean bool = getOracleTypeADT().isSubType();
/*      */ 
/*  828 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(String paramString)
/*      */     throws SQLException
/*      */   {
/*  843 */     StructDescriptor localStructDescriptor = this;
/*  844 */     String str = localStructDescriptor.getName();
/*  845 */     int i = 0;
/*      */ 
/*  847 */     if (paramString.equals(str)) {
/*  848 */       i = 1;
/*      */     }
/*      */     else {
/*      */       while (true)
/*      */       {
/*  853 */         str = localStructDescriptor.getSupertypeName();
/*      */ 
/*  855 */         if (str == null)
/*      */         {
/*  857 */           i = 0;
/*      */ 
/*  859 */           break;
/*      */         }
/*      */ 
/*  862 */         if (paramString.equals(str))
/*      */         {
/*  864 */           i = 1;
/*      */ 
/*  866 */           break;
/*      */         }
/*      */ 
/*  869 */         localStructDescriptor = createDescriptor(str, this.connection);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  876 */     return i;
/*      */   }
/*      */ 
/*      */   public boolean isInstantiable()
/*      */     throws SQLException
/*      */   {
/*  889 */     if (this.isInstanciable == null) {
/*  890 */       initMetaData1();
/*      */     }
/*  892 */     boolean bool = this.isInstanciable.booleanValue();
/*      */ 
/*  896 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean isJavaObject()
/*      */     throws SQLException
/*      */   {
/*  911 */     boolean bool = getOracleTypeADT().isJavaObject();
/*      */ 
/*  915 */     return bool;
/*      */   }
/*      */ 
/*      */   public String getSupertypeName()
/*      */     throws SQLException
/*      */   {
/*  929 */     String str = null;
/*      */ 
/*  931 */     if (isSubtype())
/*      */     {
/*  933 */       if (this.supertype == null) {
/*  934 */         initMetaData1();
/*      */       }
/*  936 */       str = this.supertype;
/*      */     }
/*      */ 
/*  942 */     return str;
/*      */   }
/*      */ 
/*      */   public int getLocalAttributeCount()
/*      */     throws SQLException
/*      */   {
/*      */     int i;
/*  957 */     if (!isSubtype()) {
/*  958 */       i = getOracleTypeADT().getAttrTypes().length;
/*      */     }
/*      */     else {
/*  961 */       if (this.numLocalAttrs == -1) {
/*  962 */         initMetaData1();
/*      */       }
/*  964 */       i = this.numLocalAttrs;
/*      */     }
/*      */ 
/*  969 */     return i;
/*      */   }
/*      */ 
/*      */   public String[] getSubtypeNames()
/*      */     throws SQLException
/*      */   {
/*  983 */     if (this.subtypes == null) {
/*  984 */       initMetaData2();
/*      */     }
/*      */ 
/*  989 */     return this.subtypes;
/*      */   }
/*      */ 
/*      */   public String getJavaClassName()
/*      */     throws SQLException
/*      */   {
/* 1003 */     String str = null;
/*      */ 
/* 1005 */     if (isJavaObject()) {
/* 1006 */       str = getJavaObjectClassName(this.connection, this);
/*      */     }
/*      */ 
/* 1011 */     return str;
/*      */   }
/*      */ 
/*      */   public String getAttributeJavaName(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1026 */     String str = null;
/*      */ 
/* 1028 */     if (isJavaObject())
/*      */     {
/* 1030 */       if (this.attrJavaNames == null) {
/* 1031 */         initMetaData3();
/*      */       }
/* 1033 */       str = this.attrJavaNames[paramInt];
/*      */     }
/*      */ 
/* 1038 */     return str;
/*      */   }
/*      */ 
/*      */   public String[] getAttributeJavaNames()
/*      */     throws SQLException
/*      */   {
/* 1052 */     String[] arrayOfString = null;
/*      */ 
/* 1054 */     if (isJavaObject())
/*      */     {
/* 1056 */       if (this.attrJavaNames == null) {
/* 1057 */         initMetaData3();
/*      */       }
/* 1059 */       arrayOfString = this.attrJavaNames;
/*      */     }
/*      */     else
/*      */     {
/* 1063 */       arrayOfString = new String[0];
/*      */     }
/*      */ 
/* 1068 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */   public String getLanguage()
/*      */     throws SQLException
/*      */   {
/* 1080 */     String str = null;
/*      */ 
/* 1082 */     if (isJavaObject())
/* 1083 */       str = "JAVA";
/*      */     else {
/* 1085 */       str = "SQL";
/*      */     }
/*      */ 
/* 1089 */     return str;
/*      */   }
/*      */ 
/*      */   public Class getClass(Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1105 */     String str1 = getName();
/*      */ 
/* 1108 */     Class localClass = (Class)paramMap.get(str1);
/*      */ 
/* 1110 */     String str2 = getSchemaName();
/* 1111 */     String str3 = getTypeName();
/*      */ 
/* 1113 */     if (localClass == null)
/*      */     {
/* 1115 */       if (this.connection.getUserName().equals(str2)) {
/* 1116 */         localClass = (Class)paramMap.get(str3);
/*      */       }
/*      */     }
/* 1119 */     if (SQLName.s_parseAllFormat)
/*      */     {
/* 1121 */       if (localClass == null)
/*      */       {
/* 1123 */         if (this.connection.getUserName().equals(str2)) {
/* 1124 */           localClass = (Class)paramMap.get("\"" + str3 + "\"");
/*      */         }
/*      */       }
/* 1127 */       if (localClass == null)
/*      */       {
/* 1129 */         localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + "\"" + str3 + "\"");
/*      */       }
/*      */ 
/* 1132 */       if (localClass == null)
/*      */       {
/* 1134 */         localClass = (Class)paramMap.get("\"" + str2 + "\"" + "." + str3);
/*      */       }
/*      */ 
/* 1137 */       if (localClass == null)
/*      */       {
/* 1139 */         localClass = (Class)paramMap.get(str2 + "." + "\"" + str3 + "\"");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1145 */     return localClass;
/*      */   }
/*      */ 
/*      */   public static String getJavaObjectClassName(Connection paramConnection, StructDescriptor paramStructDescriptor)
/*      */     throws SQLException
/*      */   {
/* 1161 */     String str = getJavaObjectClassName(paramConnection, paramStructDescriptor.getSchemaName(), paramStructDescriptor.getTypeName());
/*      */ 
/* 1166 */     return str;
/*      */   }
/*      */ 
/*      */   public static String getJavaObjectClassName(Connection paramConnection, String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 1182 */     PreparedStatement localPreparedStatement = null;
/* 1183 */     ResultSet localResultSet = null;
/*      */ 
/* 1185 */     String str = null;
/*      */     try
/*      */     {
/* 1189 */       localPreparedStatement = paramConnection.prepareStatement("select external_name from all_sqlj_types where owner = :1 and type_name = :2");
/*      */ 
/* 1192 */       localPreparedStatement.setString(1, paramString1);
/* 1193 */       localPreparedStatement.setString(2, paramString2);
/*      */ 
/* 1195 */       localResultSet = localPreparedStatement.executeQuery();
/*      */ 
/* 1197 */       if (localResultSet.next()) {
/* 1198 */         str = localResultSet.getString(1);
/*      */       }
/*      */       else
/*      */       {
/* 1204 */         DatabaseError.throwSqlException(100);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/* 1215 */       if (localResultSet != null) {
/* 1216 */         localResultSet.close();
/*      */       }
/* 1218 */       if (localPreparedStatement != null) {
/* 1219 */         localPreparedStatement.close();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1224 */     return str;
/*      */   }
/*      */ 
/*      */   public String descType()
/*      */     throws SQLException
/*      */   {
/* 1237 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/* 1239 */     return descType(localStringBuffer, 0);
/*      */   }
/*      */ 
/*      */   String descType(StringBuffer paramStringBuffer, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1247 */     String str1 = "";
/*      */ 
/* 1249 */     for (int i = 0; i < paramInt; i++) {
/* 1250 */       str1 = str1 + "  ";
/*      */     }
/* 1252 */     String str2 = str1 + "  ";
/*      */ 
/* 1254 */     paramStringBuffer.append(str1);
/* 1255 */     paramStringBuffer.append(getTypeName());
/* 1256 */     paramStringBuffer.append("\n");
/* 1257 */     paramStringBuffer.append(str1);
/* 1258 */     paramStringBuffer.append("Subtype=" + getOracleTypeADT().isSubType());
/* 1259 */     paramStringBuffer.append(" JavaObject=" + getOracleTypeADT().isJavaObject());
/* 1260 */     paramStringBuffer.append(" FinalType=" + getOracleTypeADT().isFinalType());
/* 1261 */     paramStringBuffer.append("\n");
/*      */ 
/* 1263 */     ResultSetMetaData localResultSetMetaData = getMetaData();
/* 1264 */     int j = localResultSetMetaData.getColumnCount();
/*      */ 
/* 1266 */     for (int k = 0; k < j; k++)
/*      */     {
/* 1268 */       int m = localResultSetMetaData.getColumnType(k + 1);
/*      */       Object localObject;
/* 1270 */       if ((m == 2002) || (m == 2008))
/*      */       {
/* 1273 */         localObject = createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);
/*      */ 
/* 1277 */         ((StructDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */       }
/* 1279 */       else if (m == 2003)
/*      */       {
/* 1281 */         localObject = ArrayDescriptor.createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);
/*      */ 
/* 1285 */         ((ArrayDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */       }
/* 1287 */       else if (m == 2007)
/*      */       {
/* 1289 */         localObject = OpaqueDescriptor.createDescriptor(localResultSetMetaData.getColumnTypeName(k + 1), this.connection);
/*      */ 
/* 1293 */         ((OpaqueDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */       }
/*      */       else
/*      */       {
/* 1297 */         paramStringBuffer.append(str2);
/* 1298 */         paramStringBuffer.append(localResultSetMetaData.getColumnTypeName(k + 1));
/* 1299 */         paramStringBuffer.append("\n");
/*      */       }
/*      */     }
/*      */ 
/* 1303 */     String str3 = paramStringBuffer.substring(0, paramStringBuffer.length());
/*      */ 
/* 1307 */     return (String)str3;
/*      */   }
/*      */ 
/*      */   public byte[] toBytes(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/* 1331 */     Datum[] arrayOfDatum = toOracleArray(paramArrayOfObject);
/*      */ 
/* 1333 */     return toBytes(arrayOfDatum);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public byte[] toBytes(Datum[] paramArrayOfDatum)
/*      */     throws SQLException
/*      */   {
/* 1354 */     STRUCT localSTRUCT = new STRUCT(this, (byte[])null, this.connection);
/*      */ 
/* 1356 */     localSTRUCT.setDatumArray(paramArrayOfDatum);
/*      */ 
/* 1358 */     return this.pickler.linearize(localSTRUCT);
/*      */   }
/*      */ 
/*      */   public Datum[] toArray(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/* 1375 */     return toOracleArray(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public Datum[] toArray(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1390 */     STRUCT localSTRUCT = new STRUCT(this, paramArrayOfByte, this.connection);
/*      */ 
/* 1392 */     return toOracleArray(localSTRUCT, false);
/*      */   }
/*      */ 
/*      */   private void initMetaData1()
/*      */     throws SQLException
/*      */   {
/* 1404 */     int i = this.connection.getVersionNumber();
/*      */ 
/* 1406 */     if (i >= 9000)
/* 1407 */       initMetaData1_9_0();
/*      */     else
/* 1409 */       initMetaData1_pre_9_0();
/*      */   }
/*      */ 
/*      */   private void initMetaData1_9_0()
/*      */     throws SQLException
/*      */   {
/* 1485 */     int i = 0;
/*      */ 
/* 1489 */     synchronized (this.connection)
/*      */     {
/* 1491 */       synchronized (this)
/*      */       {
/* 1493 */         if (this.numLocalAttrs == -1)
/*      */         {
/* 1495 */           PreparedStatement localPreparedStatement = null;
/* 1496 */           OracleCallableStatement localOracleCallableStatement = null;
/* 1497 */           ResultSet localResultSet = null;
/* 1498 */           int j = -1;
/*      */           try
/*      */           {
/*      */             while (true)
/*      */             {
/* 1505 */               switch (i)
/*      */               {
/*      */               case 0:
/* 1508 */                 localPreparedStatement = this.connection.prepareStatement(this.initMetaData1_9_0_SQL[i]);
/*      */ 
/* 1511 */                 localPreparedStatement.setString(1, getTypeName());
/* 1512 */                 localPreparedStatement.setString(2, getSchemaName());
/*      */ 
/* 1514 */                 localPreparedStatement.setFetchSize(1);
/* 1515 */                 localResultSet = localPreparedStatement.executeQuery();
/*      */ 
/* 1517 */                 break;
/*      */               case 1:
/*      */               case 2:
/*      */                 try
/*      */                 {
/* 1523 */                   localOracleCallableStatement = (OracleCallableStatement)this.connection.prepareCall(this.initMetaData1_9_0_SQL[i]);
/*      */ 
/* 1526 */                   localOracleCallableStatement.setString(1, getTypeName());
/* 1527 */                   localOracleCallableStatement.registerOutParameter(2, -10);
/*      */ 
/* 1530 */                   localOracleCallableStatement.execute();
/*      */ 
/* 1532 */                   localResultSet = localOracleCallableStatement.getCursor(2);
/* 1533 */                   localResultSet.setFetchSize(1);
/*      */                 }
/*      */                 catch (SQLException localSQLException) {
/* 1536 */                   if (localSQLException.getErrorCode() == 1403)
/*      */                   {
/* 1538 */                     if (i == 1)
/*      */                     {
/* 1540 */                       localOracleCallableStatement.close();
/* 1541 */                       i++;
/* 1542 */                       continue;
/*      */                     }
/*      */ 
/* 1547 */                     DatabaseError.throwSqlException(1, "Inconsistent catalog view");
/*      */                   }
/*      */                   else
/*      */                   {
/* 1552 */                     throw localSQLException;
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/*      */               default:
/* 1559 */                 if (localResultSet.next())
/*      */                 {
/* 1561 */                   this.isInstanciable = new Boolean(localResultSet.getString(1).equals("YES"));
/* 1562 */                   this.supertype = (localResultSet.getString(2) + "." + localResultSet.getString(3));
/* 1563 */                   j = localResultSet.getInt(4);
/*      */ 
/* 1565 */                   break label362;
/*      */                 }
/*      */ 
/* 1570 */                 if (i == 2)
/*      */                 {
/* 1574 */                   DatabaseError.throwSqlException(1, "Inconsistent catalog view"); continue;
/*      */                 }
/*      */ 
/* 1578 */                 localResultSet.close();
/* 1579 */                 if (localOracleCallableStatement != null)
/* 1580 */                   localOracleCallableStatement.close();
/* 1581 */                 i++;
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */           finally
/*      */           {
/* 1588 */             label362: if (localResultSet != null) {
/* 1589 */               localResultSet.close();
/*      */             }
/* 1591 */             if (localPreparedStatement != null) {
/* 1592 */               localPreparedStatement.close();
/*      */             }
/* 1594 */             if (localOracleCallableStatement != null) {
/* 1595 */               localOracleCallableStatement.close();
/*      */             }
/*      */           }
/* 1598 */           this.numLocalAttrs = j;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void initMetaData1_pre_9_0()
/*      */     throws SQLException
/*      */   {
/* 1617 */     this.isInstanciable = new Boolean(true);
/* 1618 */     this.supertype = "";
/* 1619 */     this.numLocalAttrs = 0;
/*      */   }
/*      */ 
/*      */   private void initMetaData2()
/*      */     throws SQLException
/*      */   {
/* 1632 */     int i = this.connection.getVersionNumber();
/*      */ 
/* 1634 */     if (i >= 9000) {
/* 1635 */       initMetaData2_9_0();
/*      */     }
/*      */     else
/*      */     {
/* 1640 */       initMetaData2_pre_9_0();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void initMetaData2_9_0()
/*      */     throws SQLException
/*      */   {
/* 1655 */     synchronized (this.connection)
/*      */     {
/* 1657 */       synchronized (this)
/*      */       {
/* 1659 */         if (this.subtypes == null)
/*      */         {
/* 1661 */           PreparedStatement localPreparedStatement = null;
/* 1662 */           ResultSet localResultSet = null;
/* 1663 */           String[] arrayOfString = null;
/*      */           try
/*      */           {
/* 1667 */             localPreparedStatement = this.connection.prepareStatement("select owner, type_name from all_types where supertype_name = :1 and supertype_owner = :2");
/*      */ 
/* 1670 */             localPreparedStatement.setString(1, getTypeName());
/* 1671 */             localPreparedStatement.setString(2, getSchemaName());
/*      */ 
/* 1673 */             localResultSet = localPreparedStatement.executeQuery();
/*      */ 
/* 1675 */             Vector localVector = new Vector();
/*      */ 
/* 1677 */             while (localResultSet.next()) {
/* 1678 */               localVector.addElement(localResultSet.getString(1) + "." + localResultSet.getString(2));
/*      */             }
/* 1680 */             arrayOfString = new String[localVector.size()];
/*      */ 
/* 1682 */             for (int i = 0; i < arrayOfString.length; i++) {
/* 1683 */               arrayOfString[i] = ((String)localVector.elementAt(i));
/*      */             }
/* 1685 */             localVector.removeAllElements();
/*      */ 
/* 1687 */             localVector = null;
/*      */           }
/*      */           finally
/*      */           {
/* 1691 */             if (localResultSet != null) {
/* 1692 */               localResultSet.close();
/*      */             }
/* 1694 */             if (localPreparedStatement != null) {
/* 1695 */               localPreparedStatement.close();
/*      */             }
/*      */           }
/* 1698 */           this.subtypes = arrayOfString;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void initMetaData2_pre_9_0()
/*      */     throws SQLException
/*      */   {
/* 1719 */     this.subtypes = new String[0];
/*      */   }
/*      */ 
/*      */   private void initMetaData3()
/*      */     throws SQLException
/*      */   {
/* 1734 */     synchronized (this.connection)
/*      */     {
/* 1736 */       synchronized (this)
/*      */       {
/* 1738 */         if (this.attrJavaNames == null)
/*      */         {
/* 1740 */           String[] arrayOfString = null;
/* 1741 */           PreparedStatement localPreparedStatement = null;
/* 1742 */           ResultSet localResultSet = null;
/*      */           try
/*      */           {
/* 1746 */             localPreparedStatement = this.connection.prepareStatement("select EXTERNAL_ATTR_NAME from all_sqlj_type_attrs where owner = :1 and type_name = :2");
/*      */ 
/* 1749 */             localPreparedStatement.setString(1, getSchemaName());
/* 1750 */             localPreparedStatement.setString(2, getTypeName());
/*      */ 
/* 1752 */             localResultSet = localPreparedStatement.executeQuery();
/* 1753 */             arrayOfString = new String[getOracleTypeADT().getAttrTypes().length];
/*      */ 
/* 1756 */             for (int i = 0; localResultSet.next(); i++)
/* 1757 */               arrayOfString[i] = localResultSet.getString(1);
/*      */           }
/*      */           finally
/*      */           {
/* 1761 */             if (localResultSet != null) {
/* 1762 */               localResultSet.close();
/*      */             }
/* 1764 */             if (localPreparedStatement != null) {
/* 1765 */               localPreparedStatement.close();
/*      */             }
/*      */           }
/* 1768 */           this.attrJavaNames = arrayOfString;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.StructDescriptor
 * JD-Core Version:    0.6.0
 */