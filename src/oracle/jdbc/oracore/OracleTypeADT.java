/*      */ package oracle.jdbc.oracore;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Blob;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLData;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.internal.ObjectData;
/*      */ import oracle.jdbc.internal.OracleCallableStatement;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.JAVA_STRUCT;
/*      */ import oracle.sql.NUMBER;
/*      */ import oracle.sql.SQLName;
/*      */ import oracle.sql.STRUCT;
/*      */ import oracle.sql.StructDescriptor;
/*      */ import oracle.sql.TypeDescriptor;
/*      */ 
/*      */ public class OracleTypeADT extends OracleNamedType
/*      */   implements Serializable
/*      */ {
/*      */   static final long serialVersionUID = 3031304012507165702L;
/*      */   static final int S_TOP = 1;
/*      */   static final int S_EMBEDDED = 2;
/*      */   static final int S_UPT_ADT = 4;
/*      */   static final int S_JAVA_OBJECT = 16;
/*      */   static final int S_FINAL_TYPE = 32;
/*      */   static final int S_SUB_TYPE = 64;
/*      */   static final int S_ATTR_TDS = 128;
/*      */   static final int S_HAS_METADATA = 256;
/*      */   static final int S_TDS_PARSED = 512;
/*   61 */   private int statusBits = 1;
/*      */ 
/*   68 */   int tdsVersion = -9999;
/*      */   static final int KOPT_V80 = 1;
/*      */   static final int KOPT_V81 = 2;
/*      */   static final int KOPT_VNFT = 3;
/*      */   static final int KOPT_VERSION = 3;
/*   78 */   boolean endOfAdt = false;
/*      */ 
/*   80 */   int typeVersion = -1;
/*      */ 
/*   84 */   byte[] lds = null;
/*   85 */   long[] ldsOffsetArray = null;
/*   86 */   long fixedDataSize = -1L;
/*   87 */   int alignmentRequirement = -1;
/*      */ 
/*   90 */   OracleType[] attrTypes = null;
/*      */   String[] attrNames;
/*      */   String[] attrTypeNames;
/*   94 */   public long tdoCState = 0L;
/*      */ 
/*   96 */   byte[] toid = null;
/*      */   byte[] fdo;
/*      */   int charSetId;
/*      */   int charSetForm;
/*      */   boolean bigEndian;
/*      */   int flattenedAttrNum;
/*      */   transient int opcode;
/*  108 */   transient int idx = 1;
/*      */   static final int CURRENT_USER_OBJECT = 0;
/*      */   static final int CURRENT_USER_SYNONYM = 1;
/*      */   static final int CURRENT_USER_PUBLIC_SYNONYM = 2;
/*      */   static final int OTHER_USER_OBJECT = 3;
/*      */   static final int OTHER_USER_SYNONYM = 4;
/*      */   static final int PUBLIC_SYNONYM = 5;
/*      */   static final int BREAK = 6;
/* 2280 */   static final String[] sqlString = { "SELECT /*+ RULE */ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME = :1 ORDER BY ATTR_NO", "SELECT /*+ RULE */ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME in (SELECT TABLE_NAME FROM USER_SYNONYMS START WITH SYNONYM_NAME = :1 CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME UNION SELECT :1 FROM DUAL) ORDER BY ATTR_NO", "SELECT /*+RULE*/ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM USER_TYPE_ATTRS WHERE TYPE_NAME IN (SELECT TABLE_NAME FROM ALL_SYNONYMS START WITH SYNONYM_NAME = :1 AND  OWNER = 'PUBLIC' CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER UNION SELECT :2  FROM DUAL) ORDER BY ATTR_NO", "SELECT /*+ RULE */ ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE OWNER = :1 AND TYPE_NAME = :2 ORDER BY ATTR_NO", "SELECT /*+ RULE */ ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME, ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS WHERE OWNER = (SELECT TABLE_OWNER FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:1) AND TYPE_NAME = (SELECT TABLE_NAME FROM ALL_SYNONYMS WHERE SYNONYM_NAME=:2) ORDER BY ATTR_NO", "DECLARE /*+RULE*/  the_owner VARCHAR2(100);   the_type  VARCHAR2(100); begin  SELECT /*+ RULE */TABLE_NAME, TABLE_OWNER INTO THE_TYPE, THE_OWNER  FROM ALL_SYNONYMS  WHERE TABLE_NAME IN (SELECT TYPE_NAME FROM ALL_TYPES)  START WITH SYNONYM_NAME = :1 AND OWNER = 'PUBLIC'  CONNECT BY PRIOR TABLE_NAME = SYNONYM_NAME AND TABLE_OWNER = OWNER; OPEN :2 FOR SELECT ATTR_NO, ATTR_NAME, ATTR_TYPE_NAME,  ATTR_TYPE_OWNER FROM ALL_TYPE_ATTRS  WHERE TYPE_NAME = THE_TYPE and OWNER = THE_OWNER; END;" };
/*      */   static final int TDS_SIZE = 4;
/*      */   static final int TDS_NUMBER = 1;
/*      */   static final int KOPM_OTS_SQL_CHAR = 1;
/*      */   static final int KOPM_OTS_DATE = 2;
/*      */   static final int KOPM_OTS_DECIMAL = 3;
/*      */   static final int KOPM_OTS_DOUBLE = 4;
/*      */   static final int KOPM_OTS_FLOAT = 5;
/*      */   static final int KOPM_OTS_NUMBER = 6;
/*      */   static final int KOPM_OTS_SQL_VARCHAR2 = 7;
/*      */   static final int KOPM_OTS_SINT32 = 8;
/*      */   static final int KOPM_OTS_REF = 9;
/*      */   static final int KOPM_OTS_VARRAY = 10;
/*      */   static final int KOPM_OTS_UINT8 = 11;
/*      */   static final int KOPM_OTS_SINT8 = 12;
/*      */   static final int KOPM_OTS_UINT16 = 13;
/*      */   static final int KOPM_OTS_UINT32 = 14;
/*      */   static final int KOPM_OTS_LOB = 15;
/*      */   static final int KOPM_OTS_CANONICAL = 17;
/*      */   static final int KOPM_OTS_OCTET = 18;
/*      */   static final int KOPM_OTS_RAW = 19;
/*      */   static final int KOPM_OTS_ROWID = 20;
/*      */   static final int KOPM_OTS_STAMP = 21;
/*      */   static final int KOPM_OTS_TZSTAMP = 23;
/*      */   static final int KOPM_OTS_INTERVAL = 24;
/*      */   static final int KOPM_OTS_PTR = 25;
/*      */   static final int KOPM_OTS_SINT16 = 26;
/*      */   static final int KOPM_OTS_UPT = 27;
/*      */   static final int KOPM_OTS_COLLECTION = 28;
/*      */   static final int KOPM_OTS_CLOB = 29;
/*      */   static final int KOPM_OTS_BLOB = 30;
/*      */   static final int KOPM_OTS_BFILE = 31;
/*      */   static final int KOPM_OTS_BINARY_INTEGE = 32;
/*      */   static final int KOPM_OTS_IMPTZSTAMP = 33;
/*      */   static final int KOPM_OTS_BFLOAT = 37;
/*      */   static final int KOPM_OTS_BDOUBLE = 45;
/*      */   static final int KOTTCOPQ = 58;
/*      */   static final int KOPT_OP_STARTEMBADT = 39;
/*      */   static final int KOPT_OP_ENDEMBADT = 40;
/*      */   static final int KOPT_OP_STARTADT = 41;
/*      */   static final int KOPT_OP_ENDADT = 42;
/*      */   static final int KOPT_OP_SUBTYPE_MARKER = 43;
/*      */   static final int KOPT_OP_EMBADT_INFO = 44;
/*      */   static final int KOPT_OPCODE_START = 38;
/*      */   static final int KOPT_OP_VERSION = 38;
/*      */   static final int REGULAR_PATCH = 0;
/*      */   static final int SIMPLE_PATCH = 1;
/* 3177 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*      */ 
/*      */   protected OracleTypeADT()
/*      */   {
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(byte[] paramArrayOfByte, int paramInt1, int paramInt2, short paramShort, String paramString)
/*      */     throws SQLException
/*      */   {
/*  128 */     this(paramString, (OracleConnection)null);
/*      */ 
/*  131 */     this.toid = paramArrayOfByte;
/*  132 */     this.typeVersion = paramInt1;
/*  133 */     this.charSetId = paramInt2;
/*  134 */     this.charSetForm = paramShort;
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(String paramString, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  142 */     super(paramString, (OracleConnection)paramConnection);
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(String paramString, Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  154 */     this(paramString, paramConnection);
/*      */ 
/*  158 */     this.fdo = paramArrayOfByte;
/*      */ 
/*  160 */     initEndianess(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(OracleTypeADT paramOracleTypeADT, int paramInt, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  169 */     super(paramOracleTypeADT, paramInt, (OracleConnection)paramConnection);
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(OracleTypeADT paramOracleTypeADT, int paramInt, Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  181 */     this(paramOracleTypeADT, paramInt, paramConnection);
/*      */ 
/*  186 */     this.fdo = paramArrayOfByte;
/*      */ 
/*  188 */     initEndianess(paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public OracleTypeADT(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, OracleConnection paramOracleConnection, byte[] paramArrayOfByte4)
/*      */     throws SQLException
/*      */   {
/*  199 */     this.fdo = paramArrayOfByte4;
/*  200 */     init(paramArrayOfByte2, paramOracleConnection);
/*  201 */     this.toid = paramArrayOfByte1;
/*  202 */     this.typeVersion = paramInt;
/*      */   }
/*      */ 
/*      */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  218 */     if (paramObject != null)
/*      */     {
/*  220 */       if ((paramObject instanceof STRUCT))
/*      */       {
/*  222 */         return (STRUCT)paramObject;
/*      */       }
/*  224 */       if (((paramObject instanceof SQLData)) || ((paramObject instanceof ObjectData)))
/*      */       {
/*  227 */         return STRUCT.toSTRUCT(paramObject, paramOracleConnection);
/*      */       }
/*  229 */       if ((paramObject instanceof Object[]))
/*      */       {
/*  231 */         StructDescriptor localStructDescriptor = createStructDescriptor();
/*  232 */         STRUCT localSTRUCT = createObjSTRUCT(localStructDescriptor, (Object[])paramObject);
/*      */ 
/*  236 */         return localSTRUCT;
/*      */       }
/*      */ 
/*  239 */       DatabaseError.throwSqlException(59, paramObject);
/*      */     }
/*      */ 
/*  245 */     return null;
/*      */   }
/*      */ 
/*      */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  260 */     Datum[] arrayOfDatum = null;
/*      */ 
/*  262 */     if (paramObject != null)
/*      */     {
/*  264 */       if ((paramObject instanceof Object[]))
/*      */       {
/*  266 */         Object[] arrayOfObject = (Object[])paramObject;
/*      */ 
/*  268 */         int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));
/*      */ 
/*  271 */         arrayOfDatum = new Datum[i];
/*      */ 
/*  273 */         for (int j = 0; j < i; j++) {
/*  274 */           arrayOfDatum[j] = toDatum(arrayOfObject[((int)paramLong + j - 1)], paramOracleConnection);
/*      */         }
/*      */       }
/*  277 */       DatabaseError.throwSqlException(59, paramObject);
/*      */     }
/*      */ 
/*  283 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public int getTypeCode()
/*      */     throws SQLException
/*      */   {
/*  291 */     if ((getStatus() & 0x10) != 0) {
/*  292 */       return 2008;
/*      */     }
/*  294 */     return 2002;
/*      */   }
/*      */ 
/*      */   public OracleType[] getAttrTypes() throws SQLException
/*      */   {
/*  299 */     if (this.attrTypes == null) {
/*  300 */       init(this.connection);
/*      */     }
/*  302 */     return this.attrTypes;
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(OracleType paramOracleType)
/*      */     throws SQLException
/*      */   {
/*  314 */     if (paramOracleType == null) {
/*  315 */       return false;
/*      */     }
/*  317 */     if (!paramOracleType.isObjectType()) {
/*  318 */       return false;
/*      */     }
/*  320 */     StructDescriptor localStructDescriptor = (StructDescriptor)paramOracleType.getTypeDescriptor();
/*      */ 
/*  323 */     return this.descriptor.isInHierarchyOf(localStructDescriptor.getName());
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
/*      */     throws SQLException
/*      */   {
/*  332 */     if (paramStructDescriptor == null) {
/*  333 */       return false;
/*      */     }
/*  335 */     return this.descriptor.isInHierarchyOf(paramStructDescriptor.getName());
/*      */   }
/*      */ 
/*      */   public boolean isObjectType()
/*      */   {
/*  340 */     return true;
/*      */   }
/*      */ 
/*      */   public TypeDescriptor getTypeDescriptor()
/*      */   {
/*  345 */     return this.descriptor;
/*      */   }
/*      */ 
/*      */   public synchronized void init(OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  362 */     byte[] arrayOfByte = initMetadata(paramOracleConnection);
/*  363 */     init(arrayOfByte, paramOracleConnection);
/*      */   }
/*      */ 
/*      */   public synchronized void init(byte[] paramArrayOfByte, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  377 */     this.statusBits = 1;
/*  378 */     this.connection = paramOracleConnection;
/*      */ 
/*  380 */     if (paramArrayOfByte != null) parseTDS(paramArrayOfByte, 0L);
/*  381 */     this.bigEndian = this.connection.getBigEndian();
/*  382 */     setStatusBits(256);
/*      */   }
/*      */ 
/*      */   public byte[] initMetadata(OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  396 */     byte[] arrayOfByte = null;
/*  397 */     if ((this.statusBits & 0x100) != 0) return null;
/*      */ 
/*  400 */     if (this.sqlName == null) getFullName();
/*      */ 
/*  406 */     synchronized (paramOracleConnection)
/*      */     {
/*  408 */       synchronized (this)
/*      */       {
/*  410 */         if ((this.statusBits & 0x100) == 0)
/*      */         {
/*  412 */           CallableStatement localCallableStatement = null;
/*      */           try
/*      */           {
/*  415 */             if (this.tdoCState == 0L) this.tdoCState = this.connection.getTdoCState(this.sqlName.getSchema(), this.sqlName.getSimpleName());
/*      */ 
/*  419 */             String str = null;
/*  420 */             int i = (this.fdo = this.connection.getFDO(false)) == null ? 1 : 0;
/*      */ 
/*  422 */             if (i == 0)
/*      */             {
/*  424 */               str = "begin :1 := dbms_pickler.get_type_shape(:2,:3,:4,:5,:6,:7); end;";
/*      */             }
/*      */             else
/*      */             {
/*  429 */               str = "begin :1 := dbms_pickler.get_type_shape(:2,:3,:4,:5,:6,:7);       :8 := dbms_pickler.get_format(:9); end;";
/*      */             }
/*      */ 
/*  435 */             int j = 0;
/*  436 */             localCallableStatement = this.connection.prepareCall(str);
/*      */ 
/*  438 */             localCallableStatement.registerOutParameter(1, 2);
/*  439 */             localCallableStatement.registerOutParameter(4, -4);
/*  440 */             localCallableStatement.registerOutParameter(5, 4);
/*  441 */             localCallableStatement.registerOutParameter(6, -4);
/*  442 */             localCallableStatement.registerOutParameter(7, -4);
/*  443 */             if (i != 0)
/*      */             {
/*  445 */               localCallableStatement.registerOutParameter(8, 2);
/*  446 */               localCallableStatement.registerOutParameter(9, -4);
/*      */             }
/*  448 */             localCallableStatement.setString(2, this.sqlName.getSchema());
/*  449 */             localCallableStatement.setString(3, this.sqlName.getSimpleName());
/*      */ 
/*  454 */             localCallableStatement.execute();
/*      */ 
/*  457 */             int k = localCallableStatement.getInt(1);
/*  458 */             if (k != 0)
/*      */             {
/*  462 */               if (k != 24331) {
/*  463 */                 DatabaseError.throwSqlException(74, this.sqlName.toString());
/*      */               }
/*      */ 
/*  466 */               if (k == 24331)
/*      */               {
/*  468 */                 j = 1;
/*  469 */                 localCallableStatement.registerOutParameter(6, 2004);
/*      */ 
/*  473 */                 localCallableStatement.execute();
/*      */ 
/*  476 */                 k = localCallableStatement.getInt(1);
/*  477 */                 if (k != 0)
/*      */                 {
/*  481 */                   DatabaseError.throwSqlException(74, this.sqlName.toString());
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  487 */             if (i != 0)
/*      */             {
/*  489 */               if (localCallableStatement.getInt(8) != 0) {
/*  490 */                 DatabaseError.throwSqlException(1, "dbms_pickler.get_format()");
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  495 */             this.toid = localCallableStatement.getBytes(4);
/*  496 */             this.typeVersion = NUMBER.toInt(localCallableStatement.getBytes(5));
/*  497 */             if (j == 0)
/*      */             {
/*  499 */               arrayOfByte = localCallableStatement.getBytes(6);
/*      */             }
/*      */             else
/*      */             {
/*      */               try
/*      */               {
/*  506 */                 Blob localBlob = ((OracleCallableStatement)localCallableStatement).getBlob(6);
/*  507 */                 InputStream localInputStream = localBlob.getBinaryStream();
/*  508 */                 arrayOfByte = new byte[(int)localBlob.length()];
/*  509 */                 localInputStream.read(arrayOfByte);
/*  510 */                 localInputStream.close();
/*  511 */                 ((BLOB)localBlob).freeTemporary();
/*      */               }
/*      */               catch (IOException localIOException) {
/*  514 */                 DatabaseError.throwSqlException(localIOException);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  521 */             if (i != 0)
/*      */             {
/*  523 */               this.fdo = localCallableStatement.getBytes(9);
/*  524 */               this.connection.setFDO(this.fdo);
/*      */             }
/*      */ 
/*  530 */             this.flattenedAttrNum = (Util.getUnsignedByte(arrayOfByte[8]) * 256 + Util.getUnsignedByte(arrayOfByte[9]));
/*      */ 
/*  532 */             localCallableStatement.getBytes(7);
/*      */           }
/*      */           finally
/*      */           {
/*  541 */             if (localCallableStatement != null) localCallableStatement.close();
/*      */           }
/*      */         }
/*  544 */         setStatusBits(256);
/*      */       }
/*      */     }
/*  547 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   private void initEndianess(byte[] paramArrayOfByte)
/*      */   {
/*  553 */     int[] arrayOfInt = Util.toJavaUnsignedBytes(paramArrayOfByte);
/*  554 */     int i = arrayOfInt[(6 + arrayOfInt[5] + arrayOfInt[6] + 5)];
/*      */ 
/*  559 */     int j = (byte)(i & 0x10);
/*      */ 
/*  561 */     if (j < 0) {
/*  562 */       j += 256;
/*      */     }
/*  564 */     this.bigEndian = (j > 0);
/*      */   }
/*      */ 
/*      */   void parseLDS(InputStream paramInputStream)
/*      */     throws SQLException
/*      */   {
/*  572 */     long l = Util.readLong(paramInputStream);
/*      */ 
/*  574 */     this.fixedDataSize = Util.readLong(paramInputStream);
/*  575 */     this.ldsOffsetArray = new long[this.flattenedAttrNum];
/*      */ 
/*  577 */     for (int i = 0; i < this.flattenedAttrNum; i++)
/*      */     {
/*  579 */       this.ldsOffsetArray[i] = Util.readLong(paramInputStream);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void generateLDS()
/*      */     throws SQLException
/*      */   {
/*  598 */     Vector localVector = generateLDSrec();
/*      */ 
/*  600 */     this.ldsOffsetArray = new long[localVector.size()];
/*      */ 
/*  602 */     for (int i = 0; i < localVector.size(); i++)
/*      */     {
/*  604 */       Integer localInteger = (Integer)localVector.elementAt(i);
/*      */ 
/*  606 */       this.ldsOffsetArray[i] = localInteger.longValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private Vector generateLDSrec()
/*      */     throws SQLException
/*      */   {
/*  624 */     int i = 0;
/*  625 */     int j = 0;
/*      */ 
/*  627 */     Vector localVector1 = new Vector();
/*  628 */     int k = getNumAttrs();
/*      */ 
/*  630 */     for (int m = 0; m < k; m++)
/*      */     {
/*  634 */       Vector localVector2 = null;
/*  635 */       OracleType localOracleType = getAttrTypeAt(m);
/*      */       int i1;
/*      */       int n;
/*  637 */       if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
/*      */       {
/*  641 */         localVector2 = ((OracleTypeADT)localOracleType).generateLDSrec();
/*  642 */         i1 = ((OracleTypeADT)localOracleType).getAlignmentReq();
/*  643 */         n = (int)((OracleTypeADT)localOracleType).getFixedDataSize();
/*      */       }
/*      */       else
/*      */       {
/*  649 */         n = localOracleType.getSizeLDS(this.fdo);
/*  650 */         i1 = localOracleType.getAlignLDS(this.fdo);
/*      */       }
/*      */ 
/*  659 */       if ((i & i1) > 0)
/*  660 */         i = Util.ldsRound(i, i1);
/*      */       int i2;
/*  662 */       if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
/*      */       {
/*  669 */         for (i2 = 0; i2 < localVector2.size(); )
/*      */         {
/*  671 */           Integer localInteger1 = (Integer)localVector2.elementAt(i2);
/*  672 */           Integer localInteger2 = new Integer(localInteger1.intValue() + i);
/*      */ 
/*  674 */           localVector1.addElement(localInteger2);
/*      */ 
/*  669 */           i2++; continue;
/*      */ 
/*  678 */           localVector1.addElement(new Integer(i));
/*      */         }
/*      */       }
/*  680 */       i += n;
/*      */ 
/*  683 */       if (i1 > j) {
/*  684 */         j = i1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  689 */     if ((i & j) > 0) {
/*  690 */       i = Util.ldsRound(i, j);
/*      */     }
/*  692 */     this.alignmentRequirement = j;
/*  693 */     this.fixedDataSize = i;
/*      */ 
/*  695 */     return localVector1;
/*      */   }
/*      */ 
/*      */   TDSReader parseTDS(byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  705 */     if (this.attrTypes != null) {
/*  706 */       return null;
/*      */     }
/*      */ 
/*  711 */     TDSReader localTDSReader = new TDSReader(paramArrayOfByte, paramLong);
/*      */ 
/*  714 */     long l1 = localTDSReader.readLong() + localTDSReader.offset();
/*      */ 
/*  718 */     localTDSReader.checkNextByte(38);
/*      */ 
/*  721 */     this.tdsVersion = localTDSReader.readByte();
/*      */ 
/*  724 */     localTDSReader.skipBytes(2);
/*      */ 
/*  727 */     this.flattenedAttrNum = localTDSReader.readShort();
/*      */ 
/*  730 */     if ((localTDSReader.readByte() & 0xFF) == 255) {
/*  731 */       setStatusBits(128);
/*      */     }
/*      */ 
/*  735 */     long l2 = localTDSReader.offset();
/*      */ 
/*  738 */     localTDSReader.checkNextByte(41);
/*      */ 
/*  743 */     if (localTDSReader.readShort() != 0) {
/*  744 */       DatabaseError.throwSqlException(47, "parseTDS");
/*      */     }
/*      */ 
/*  749 */     long l3 = localTDSReader.readLong();
/*      */ 
/*  752 */     parseTDSrec(localTDSReader);
/*      */ 
/*  755 */     if (this.tdsVersion >= 3)
/*      */     {
/*  759 */       localTDSReader.skip_to(l2 + l3 + 2L);
/*      */ 
/*  762 */       localTDSReader.skipBytes(2 * this.flattenedAttrNum);
/*      */ 
/*  765 */       byte b = localTDSReader.readByte();
/*      */ 
/*  768 */       if (localTDSReader.isJavaObject(this.tdsVersion, b)) {
/*  769 */         setStatusBits(16);
/*      */       }
/*      */ 
/*  773 */       if (localTDSReader.isFinalType(this.tdsVersion, b)) {
/*  774 */         setStatusBits(32);
/*      */       }
/*      */ 
/*  778 */       if (localTDSReader.readByte() != 1)
/*  779 */         setStatusBits(64);
/*      */     }
/*      */     else
/*      */     {
/*  783 */       setStatusBits(32);
/*      */     }
/*      */ 
/*  786 */     localTDSReader.skip_to(l1);
/*  787 */     return localTDSReader;
/*      */   }
/*      */ 
/*      */   public void parseTDSrec(TDSReader paramTDSReader)
/*      */     throws SQLException
/*      */   {
/*  796 */     Vector localVector = new Vector(5);
/*  797 */     OracleType localOracleType = null;
/*      */ 
/*  799 */     this.nullOffset = paramTDSReader.nullOffset;
/*  800 */     this.ldsOffset = paramTDSReader.ldsOffset;
/*      */ 
/*  806 */     paramTDSReader.nullOffset += 1;
/*      */ 
/*  816 */     this.idx = 1;
/*      */ 
/*  818 */     while ((localOracleType = getNextTypeObject(paramTDSReader)) != null)
/*      */     {
/*  820 */       localVector.addElement(localOracleType);
/*      */     }
/*      */ 
/*  824 */     if (this.opcode == 42)
/*      */     {
/*  826 */       this.endOfAdt = true;
/*      */ 
/*  828 */       applyTDSpatches(paramTDSReader);
/*      */     }
/*      */ 
/*  831 */     this.attrTypes = new OracleType[localVector.size()];
/*      */ 
/*  833 */     localVector.copyInto(this.attrTypes);
/*      */   }
/*      */ 
/*      */   private void applyTDSpatches(TDSReader paramTDSReader)
/*      */     throws SQLException
/*      */   {
/*  845 */     TDSPatch localTDSPatch = paramTDSReader.getNextPatch();
/*      */ 
/*  847 */     while (localTDSPatch != null)
/*      */     {
/*  853 */       paramTDSReader.moveToPatchPos(localTDSPatch);
/*      */ 
/*  855 */       int i = localTDSPatch.getType();
/*      */ 
/*  857 */       if (i == 0)
/*      */       {
/*  862 */         paramTDSReader.readByte();
/*      */ 
/*  864 */         int j = localTDSPatch.getUptTypeCode();
/*      */         OracleNamedType localOracleNamedType;
/*      */         Object localObject;
/*  866 */         switch (j)
/*      */         {
/*      */         case -6:
/*  871 */           paramTDSReader.readLong();
/*      */         case -5:
/*  878 */           localOracleNamedType = localTDSPatch.getOwner();
/*  879 */           localObject = null;
/*      */ 
/*  881 */           if (localOracleNamedType.hasName())
/*      */           {
/*  883 */             localObject = new OracleTypeADT(localOracleNamedType.getFullName(), this.connection, this.fdo);
/*      */           }
/*      */           else
/*      */           {
/*  888 */             localObject = new OracleTypeADT(localOracleNamedType.getParent(), localOracleNamedType.getOrder(), this.connection, this.fdo);
/*      */           }
/*      */ 
/*  892 */           ((OracleTypeADT)localObject).setUptADT();
/*  893 */           TDSReader localTDSReader = ((OracleTypeADT)localObject).parseTDS(paramTDSReader.tds(), paramTDSReader.absoluteOffset());
/*      */ 
/*  895 */           paramTDSReader.skipBytes((int)localTDSReader.offset());
/*  896 */           localTDSPatch.apply(((OracleTypeADT)localObject).cleanup());
/*      */ 
/*  899 */           break;
/*      */         case 58:
/*  903 */           localOracleNamedType = localTDSPatch.getOwner();
/*  904 */           localObject = null;
/*      */ 
/*  906 */           if (localOracleNamedType.hasName())
/*      */           {
/*  908 */             localObject = new OracleTypeOPAQUE(localOracleNamedType.getFullName(), this.connection);
/*      */           }
/*      */           else
/*      */           {
/*  912 */             localObject = new OracleTypeOPAQUE(localOracleNamedType.getParent(), localOracleNamedType.getOrder(), this.connection);
/*      */           }
/*      */ 
/*  916 */           ((OracleTypeOPAQUE)localObject).parseTDSrec(paramTDSReader);
/*  917 */           localTDSPatch.apply((OracleType)localObject);
/*      */ 
/*  920 */           break;
/*      */         default:
/*  926 */           DatabaseError.throwSqlException(1); break;
/*      */         }
/*      */ 
/*      */       }
/*  932 */       else if (i == 1)
/*      */       {
/*  937 */         OracleType localOracleType = getNextTypeObject(paramTDSReader);
/*      */ 
/*  941 */         localTDSPatch.apply(localOracleType, this.opcode);
/*      */       }
/*      */       else {
/*  944 */         DatabaseError.throwSqlException(47, "parseTDS");
/*      */       }
/*      */ 
/*  947 */       localTDSPatch = paramTDSReader.getNextPatch();
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized OracleNamedType cleanup()
/*      */   {
/*      */     Object localObject;
/*  960 */     if ((this.attrTypes.length == 1) && ((this.attrTypes[0] instanceof OracleTypeCOLLECTION)))
/*      */     {
/*  965 */       localObject = (OracleTypeCOLLECTION)this.attrTypes[0];
/*      */ 
/*  967 */       ((OracleTypeCOLLECTION)localObject).copy_properties(this);
/*      */ 
/*  969 */       return localObject;
/*      */     }
/*  971 */     if ((this.attrTypes.length == 1) && ((this.statusBits & 0x80) != 0) && ((this.attrTypes[0] instanceof OracleTypeUPT)) && ((((OracleTypeUPT)this.attrTypes[0]).realType instanceof OracleTypeOPAQUE)))
/*      */     {
/*  976 */       localObject = (OracleTypeOPAQUE)((OracleTypeUPT)this.attrTypes[0]).realType;
/*      */ 
/*  979 */       ((OracleTypeOPAQUE)localObject).copy_properties(this);
/*      */ 
/*  981 */       return localObject;
/*      */     }
/*      */ 
/*  984 */     return (OracleNamedType)this;
/*      */   }
/*      */ 
/*      */   void copy_properties(OracleTypeADT paramOracleTypeADT)
/*      */   {
/*  989 */     this.sqlName = paramOracleTypeADT.sqlName;
/*  990 */     this.parent = paramOracleTypeADT.parent;
/*  991 */     this.idx = paramOracleTypeADT.idx;
/*  992 */     this.connection = paramOracleTypeADT.connection;
/*  993 */     this.lds = paramOracleTypeADT.lds;
/*  994 */     this.toid = paramOracleTypeADT.toid;
/*  995 */     this.fdo = paramOracleTypeADT.fdo;
/*  996 */     this.tdsVersion = paramOracleTypeADT.tdsVersion;
/*  997 */     this.typeVersion = paramOracleTypeADT.typeVersion;
/*  998 */     this.tdoCState = paramOracleTypeADT.tdoCState;
/*  999 */     this.nullOffset = paramOracleTypeADT.nullOffset;
/* 1000 */     this.bigEndian = paramOracleTypeADT.bigEndian;
/* 1001 */     this.endOfAdt = paramOracleTypeADT.endOfAdt;
/*      */   }
/*      */ 
/*      */   OracleType getNextTypeObject(TDSReader paramTDSReader)
/*      */     throws SQLException
/*      */   {
/*      */     while (true)
/*      */     {
/* 1017 */       this.opcode = paramTDSReader.readByte();
/*      */ 
/* 1019 */       if (this.opcode == 43)
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 1025 */       if (this.opcode != 44)
/*      */       {
/*      */         break;
/*      */       }
/* 1029 */       byte b = paramTDSReader.readByte();
/*      */ 
/* 1031 */       if (paramTDSReader.isJavaObject(3, b)) {
/* 1032 */         setStatusBits(16);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1045 */     switch (this.opcode)
/*      */     {
/*      */     case 40:
/*      */     case 42:
/* 1052 */       return null;
/*      */     case 2:
/* 1057 */       localObject = new OracleTypeDATE();
/*      */ 
/* 1059 */       ((OracleTypeDATE)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1061 */       this.idx += 1;
/*      */ 
/* 1063 */       return localObject;
/*      */     case 7:
/* 1068 */       localObject = new OracleTypeCHAR(this.connection, 12);
/*      */ 
/* 1071 */       ((OracleTypeCHAR)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1073 */       this.idx += 1;
/*      */ 
/* 1075 */       return localObject;
/*      */     case 1:
/* 1080 */       localObject = new OracleTypeCHAR(this.connection, 1);
/*      */ 
/* 1082 */       ((OracleTypeCHAR)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1084 */       this.idx += 1;
/*      */ 
/* 1086 */       return localObject;
/*      */     case 39:
/* 1091 */       localObject = new OracleTypeADT(this, this.idx, this.connection, this.fdo);
/*      */ 
/* 1093 */       ((OracleTypeADT)localObject).setEmbeddedADT();
/* 1094 */       ((OracleTypeADT)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1096 */       this.idx += 1;
/*      */ 
/* 1098 */       return localObject;
/*      */     case 6:
/* 1103 */       localObject = new OracleTypeNUMBER(2);
/*      */ 
/* 1105 */       ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1107 */       this.idx += 1;
/*      */ 
/* 1109 */       return localObject;
/*      */     case 3:
/* 1114 */       localObject = new OracleTypeNUMBER(3);
/*      */ 
/* 1116 */       ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1118 */       this.idx += 1;
/*      */ 
/* 1120 */       return localObject;
/*      */     case 4:
/* 1125 */       localObject = new OracleTypeNUMBER(8);
/*      */ 
/* 1127 */       ((OracleTypeNUMBER)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1129 */       this.idx += 1;
/*      */ 
/* 1131 */       return localObject;
/*      */     case 5:
/* 1136 */       localObject = new OracleTypeFLOAT();
/*      */ 
/* 1138 */       ((OracleTypeFLOAT)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1140 */       this.idx += 1;
/*      */ 
/* 1142 */       return localObject;
/*      */     case 37:
/* 1147 */       localObject = new OracleTypeBINARY_FLOAT();
/*      */ 
/* 1149 */       ((OracleTypeBINARY_FLOAT)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1151 */       this.idx += 1;
/*      */ 
/* 1153 */       return localObject;
/*      */     case 45:
/* 1158 */       localObject = new OracleTypeBINARY_DOUBLE();
/*      */ 
/* 1160 */       ((OracleTypeBINARY_DOUBLE)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1162 */       this.idx += 1;
/*      */ 
/* 1164 */       return localObject;
/*      */     case 8:
/* 1169 */       localObject = new OracleTypeSINT32();
/*      */ 
/* 1171 */       ((OracleTypeSINT32)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1173 */       this.idx += 1;
/*      */ 
/* 1175 */       return localObject;
/*      */     case 9:
/* 1180 */       localObject = new OracleTypeREF(this, this.idx, this.connection);
/*      */ 
/* 1182 */       ((OracleTypeREF)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1184 */       this.idx += 1;
/*      */ 
/* 1186 */       return localObject;
/*      */     case 31:
/* 1191 */       localObject = new OracleTypeBFILE(this.connection);
/*      */ 
/* 1193 */       ((OracleTypeBFILE)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1195 */       this.idx += 1;
/*      */ 
/* 1197 */       return localObject;
/*      */     case 19:
/* 1202 */       localObject = new OracleTypeRAW();
/*      */ 
/* 1204 */       ((OracleTypeRAW)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1206 */       this.idx += 1;
/*      */ 
/* 1208 */       return localObject;
/*      */     case 29:
/* 1213 */       localObject = new OracleTypeCLOB(this.connection);
/*      */ 
/* 1215 */       ((OracleTypeCLOB)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1230 */       if ((this.sqlName != null) && (!this.endOfAdt)) {
/* 1231 */         this.connection.getForm(this, (OracleTypeCLOB)localObject, this.idx);
/*      */       }
/* 1233 */       this.idx += 1;
/*      */ 
/* 1235 */       return localObject;
/*      */     case 30:
/* 1240 */       localObject = new OracleTypeBLOB(this.connection);
/*      */ 
/* 1242 */       ((OracleTypeBLOB)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1244 */       this.idx += 1;
/*      */ 
/* 1246 */       return localObject;
/*      */     case 21:
/* 1251 */       localObject = new OracleTypeTIMESTAMP(this.connection);
/*      */ 
/* 1253 */       ((OracleTypeTIMESTAMP)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1255 */       this.idx += 1;
/*      */ 
/* 1257 */       return localObject;
/*      */     case 23:
/* 1262 */       localObject = new OracleTypeTIMESTAMPTZ(this.connection);
/*      */ 
/* 1264 */       ((OracleTypeTIMESTAMPTZ)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1266 */       this.idx += 1;
/*      */ 
/* 1268 */       return localObject;
/*      */     case 33:
/* 1273 */       localObject = new OracleTypeTIMESTAMPLTZ(this.connection);
/*      */ 
/* 1275 */       ((OracleTypeTIMESTAMPLTZ)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1277 */       this.idx += 1;
/*      */ 
/* 1279 */       return localObject;
/*      */     case 24:
/* 1284 */       localObject = new OracleTypeINTERVAL(this.connection);
/*      */ 
/* 1286 */       ((OracleTypeINTERVAL)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1288 */       this.idx += 1;
/*      */ 
/* 1290 */       return localObject;
/*      */     case 28:
/* 1295 */       localObject = new OracleTypeCOLLECTION(this, this.idx, this.connection);
/*      */ 
/* 1298 */       ((OracleTypeCOLLECTION)localObject).bigEndian = this.bigEndian;
/*      */ 
/* 1300 */       ((OracleTypeCOLLECTION)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1302 */       this.idx += 1;
/*      */ 
/* 1304 */       return localObject;
/*      */     case 27:
/* 1309 */       localObject = new OracleTypeUPT(this, this.idx, this.connection);
/*      */ 
/* 1312 */       ((OracleTypeUPT)localObject).bigEndian = this.bigEndian;
/*      */ 
/* 1314 */       ((OracleTypeUPT)localObject).parseTDSrec(paramTDSReader);
/*      */ 
/* 1316 */       this.idx += 1;
/*      */ 
/* 1318 */       return localObject;
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*      */     case 25:
/*      */     case 26:
/*      */     case 32:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 38:
/*      */     case 41:
/*      */     case 43:
/* 1338 */     case 44: } Object localObject = null;
/*      */ 
/* 1340 */     DatabaseError.throwSqlException(48, "get_next_type: " + this.opcode);
/*      */ 
/* 1343 */     return (OracleType)null;
/*      */   }
/*      */ 
/*      */   public synchronized byte[] linearize(Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/* 1364 */     return pickle81(paramDatum);
/*      */   }
/*      */ 
/*      */   public Datum unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1379 */     OracleConnection localOracleConnection = getConnection();
/* 1380 */     Datum localDatum = null;
/*      */ 
/* 1385 */     if (localOracleConnection == null)
/*      */     {
/* 1387 */       localDatum = _unlinearize(paramArrayOfByte, paramLong, paramDatum, paramInt, paramMap);
/*      */     }
/*      */     else
/*      */     {
/* 1391 */       synchronized (localOracleConnection)
/*      */       {
/* 1393 */         localDatum = _unlinearize(paramArrayOfByte, paramLong, paramDatum, paramInt, paramMap);
/*      */       }
/*      */     }
/*      */ 
/* 1397 */     return localDatum;
/*      */   }
/*      */ 
/*      */   public synchronized Datum _unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1414 */     if (paramArrayOfByte == null) {
/* 1415 */       return null;
/*      */     }
/* 1417 */     if (PickleContext.is81format(paramArrayOfByte[0]))
/*      */     {
/* 1421 */       localObject = new PickleContext(paramArrayOfByte, paramLong);
/*      */ 
/* 1423 */       return unpickle81((PickleContext)localObject, (STRUCT)paramDatum, 1, paramInt, paramMap);
/*      */     }
/*      */ 
/* 1430 */     Object localObject = new UnpickleContext(paramArrayOfByte, (int)paramLong, null, null, this.bigEndian);
/*      */ 
/* 1433 */     return (Datum)unpickle80((UnpickleContext)localObject, (STRUCT)paramDatum, 1, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   protected STRUCT unpickle80(UnpickleContext paramUnpickleContext, STRUCT paramSTRUCT, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1466 */     STRUCT localSTRUCT1 = paramSTRUCT;
/*      */ 
/* 1468 */     if (paramInt1 == 3)
/*      */     {
/* 1470 */       if (localSTRUCT1 == null)
/*      */       {
/* 1472 */         StructDescriptor localStructDescriptor = createStructDescriptor();
/*      */ 
/* 1474 */         localSTRUCT1 = createByteSTRUCT(localStructDescriptor, (byte[])null);
/*      */       }
/*      */ 
/* 1477 */       localSTRUCT1.setImage(paramUnpickleContext.image(), paramUnpickleContext.absoluteOffset(), 0L);
/*      */     }
/*      */ 
/* 1487 */     long l1 = paramUnpickleContext.readLong();
/*      */ 
/* 1489 */     if (l1 == 0L)
/*      */     {
/* 1491 */       return null;
/*      */     }
/* 1493 */     if (paramInt1 == 9)
/*      */     {
/* 1495 */       paramUnpickleContext.skipBytes((int)l1);
/*      */ 
/* 1497 */       return localSTRUCT1;
/*      */     }
/* 1499 */     if (paramInt1 == 3)
/*      */     {
/* 1503 */       localSTRUCT1.setImageLength(l1 + 4L);
/* 1504 */       paramUnpickleContext.skipBytes((int)l1);
/*      */ 
/* 1506 */       return localSTRUCT1;
/*      */     }
/*      */ 
/* 1509 */     paramUnpickleContext.skipBytes(1);
/*      */ 
/* 1511 */     int i = paramUnpickleContext.readByte();
/* 1512 */     boolean[] arrayOfBoolean = unpickle_nulls(paramUnpickleContext);
/* 1513 */     long l2 = paramUnpickleContext.readLong();
/* 1514 */     long l3 = paramUnpickleContext.offset() + l2;
/*      */     try
/*      */     {
/* 1523 */       if (arrayOfBoolean[0] == 0)
/*      */       {
/* 1525 */         UnpickleContext localUnpickleContext = new UnpickleContext(paramUnpickleContext.image(), paramUnpickleContext.absoluteOffset(), arrayOfBoolean, getLdsOffsetArray(), this.bigEndian);
/*      */ 
/* 1529 */         STRUCT localSTRUCT2 = unpickle80rec(localUnpickleContext, localSTRUCT1, paramInt2, paramMap);
/*      */         return localSTRUCT2;
/*      */       } } finally { paramUnpickleContext.skipTo(l3);
/*      */     }
/*      */ 
/* 1537 */     return localSTRUCT1;
/*      */   }
/*      */ 
/*      */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1550 */     STRUCT localSTRUCT = unpickle80rec(paramUnpickleContext, null, 1, paramMap);
/*      */ 
/* 1552 */     return toObject(localSTRUCT, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   private STRUCT unpickle80rec(UnpickleContext paramUnpickleContext, STRUCT paramSTRUCT, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1565 */     if (paramUnpickleContext.isNull(this.nullOffset)) {
/* 1566 */       return null;
/*      */     }
/* 1568 */     paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
/*      */ 
/* 1570 */     int i = getNumAttrs();
/* 1571 */     STRUCT localSTRUCT = paramSTRUCT;
/*      */     Object localObject;
/* 1573 */     if (localSTRUCT == null)
/*      */     {
/* 1575 */       localObject = createStructDescriptor();
/*      */ 
/* 1577 */       localSTRUCT = createByteSTRUCT((StructDescriptor)localObject, (byte[])null);
/*      */     }
/*      */     int j;
/* 1580 */     switch (paramInt)
/*      */     {
/*      */     case 1:
/* 1585 */       localObject = new Datum[i];
/*      */ 
/* 1588 */       for (j = 0; j < i; j++)
/*      */       {
/* 1592 */         localObject[j] = ((Datum)getAttrTypeAt(j).unpickle80rec(paramUnpickleContext, 1, paramInt, paramMap));
/*      */       }
/*      */ 
/* 1596 */       localSTRUCT.setDatumArray(localObject);
/*      */ 
/* 1599 */       break;
/*      */     case 2:
/* 1603 */       localObject = new Object[i];
/*      */ 
/* 1606 */       for (j = 0; j < i; j++)
/*      */       {
/* 1610 */         localObject[j] = getAttrTypeAt(j).unpickle80rec(paramUnpickleContext, 1, paramInt, paramMap);
/*      */       }
/*      */ 
/* 1614 */       localSTRUCT.setObjArray(localObject);
/*      */ 
/* 1617 */       break;
/*      */     default:
/* 1620 */       DatabaseError.throwSqlException(1);
/*      */     }
/*      */ 
/* 1624 */     return (STRUCT)localSTRUCT;
/*      */   }
/*      */ 
/*      */   private boolean[] unpickle_nulls(UnpickleContext paramUnpickleContext)
/*      */     throws SQLException
/*      */   {
/* 1632 */     paramUnpickleContext.skipBytes(4);
/*      */ 
/* 1634 */     byte[] arrayOfByte = paramUnpickleContext.readLengthBytes();
/*      */ 
/* 1638 */     boolean[] arrayOfBoolean = new boolean[(arrayOfByte.length - 2) * 4];
/*      */ 
/* 1640 */     int i = 0;
/*      */ 
/* 1642 */     for (int j = 0; j < arrayOfBoolean.length; j++)
/*      */     {
/* 1644 */       if (j % 4 == 0) {
/* 1645 */         i = arrayOfByte[(2 + j / 4)];
/*      */       }
/* 1647 */       arrayOfBoolean[j] = ((i & 0x3) != 0 ? 1 : false);
/* 1648 */       i = (byte)(i >> 2);
/*      */     }
/*      */ 
/* 1651 */     return arrayOfBoolean;
/*      */   }
/*      */ 
/*      */   protected STRUCT unpickle81(PickleContext paramPickleContext, STRUCT paramSTRUCT, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1674 */     STRUCT localSTRUCT = paramSTRUCT;
/* 1675 */     long l1 = paramPickleContext.offset();
/*      */ 
/* 1678 */     byte b = paramPickleContext.readByte();
/*      */ 
/* 1680 */     if (!PickleContext.is81format(b)) {
/* 1681 */       DatabaseError.throwSqlException(1, "Image is not in 8.1 format");
/*      */     }
/*      */ 
/* 1684 */     if (PickleContext.isCollectionImage_pctx(b)) {
/* 1685 */       DatabaseError.throwSqlException(1, "Image is a collection image, expecting ADT");
/*      */     }
/*      */ 
/* 1690 */     if (!paramPickleContext.readAndCheckVersion()) {
/* 1691 */       DatabaseError.throwSqlException(1, "Image version is not recognized");
/*      */     }
/*      */ 
/* 1694 */     switch (paramInt1)
/*      */     {
/*      */     case 9:
/* 1699 */       paramPickleContext.skipBytes(paramPickleContext.readLength(true) - 2);
/*      */ 
/* 1702 */       break;
/*      */     case 3:
/* 1706 */       long l2 = paramPickleContext.readLength();
/*      */ 
/* 1708 */       localSTRUCT = unpickle81Prefix(paramPickleContext, localSTRUCT, b);
/*      */ 
/* 1710 */       if (localSTRUCT == null)
/*      */       {
/* 1712 */         StructDescriptor localStructDescriptor = createStructDescriptor();
/*      */ 
/* 1714 */         localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
/*      */       }
/*      */ 
/* 1717 */       localSTRUCT.setImage(paramPickleContext.image(), l1, 0L);
/* 1718 */       localSTRUCT.setImageLength(l2);
/* 1719 */       paramPickleContext.skipTo(l1 + l2);
/*      */ 
/* 1722 */       break;
/*      */     default:
/* 1726 */       paramPickleContext.skipLength();
/*      */ 
/* 1729 */       localSTRUCT = unpickle81Prefix(paramPickleContext, localSTRUCT, b);
/*      */ 
/* 1731 */       if (localSTRUCT == null)
/*      */       {
/* 1733 */         localObject1 = createStructDescriptor();
/*      */ 
/* 1735 */         localSTRUCT = createByteSTRUCT((StructDescriptor)localObject1, (byte[])null);
/*      */       }
/*      */ 
/* 1739 */       Object localObject1 = localSTRUCT.getDescriptor().getOracleTypeADT().getAttrTypes();
/*      */       Object localObject2;
/*      */       int i;
/* 1742 */       switch (paramInt2)
/*      */       {
/*      */       case 1:
/* 1747 */         localObject2 = new Datum[localObject1.length];
/*      */ 
/* 1749 */         for (i = 0; i < localObject1.length; i++)
/*      */         {
/* 1751 */           localObject2[i] = ((Datum)localObject1[i].unpickle81rec(paramPickleContext, paramInt2, paramMap));
/*      */         }
/*      */ 
/* 1755 */         localSTRUCT.setDatumArray(localObject2);
/*      */ 
/* 1758 */         break;
/*      */       case 2:
/* 1762 */         localObject2 = new Object[localObject1.length];
/*      */ 
/* 1764 */         for (i = 0; i < localObject1.length; i++)
/*      */         {
/* 1766 */           localObject2[i] = localObject1[i].unpickle81rec(paramPickleContext, paramInt2, paramMap);
/*      */         }
/*      */ 
/* 1769 */         localSTRUCT.setObjArray(localObject2);
/*      */ 
/* 1772 */         break;
/*      */       default:
/* 1778 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1786 */     return (STRUCT)(STRUCT)localSTRUCT;
/*      */   }
/*      */ 
/*      */   protected STRUCT unpickle81Prefix(PickleContext paramPickleContext, STRUCT paramSTRUCT, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 1795 */     STRUCT localSTRUCT = paramSTRUCT;
/*      */ 
/* 1797 */     if (PickleContext.hasPrefix(paramByte))
/*      */     {
/* 1799 */       long l = paramPickleContext.readLength() + paramPickleContext.absoluteOffset();
/*      */ 
/* 1801 */       int i = paramPickleContext.readByte();
/*      */ 
/* 1808 */       int j = (byte)(i & 0xC);
/* 1809 */       int k = j == 0 ? 1 : 0;
/*      */ 
/* 1811 */       int m = j == 4 ? 1 : 0;
/*      */ 
/* 1813 */       int n = j == 8 ? 1 : 0;
/*      */ 
/* 1815 */       int i1 = j == 12 ? 1 : 0;
/*      */ 
/* 1818 */       int i2 = (i & 0x10) != 0 ? 1 : 0;
/*      */ 
/* 1821 */       if (m != 0)
/*      */       {
/* 1823 */         byte[] arrayOfByte = paramPickleContext.readBytes(16);
/* 1824 */         String str = toid2typename(this.connection, arrayOfByte);
/*      */ 
/* 1826 */         StructDescriptor localStructDescriptor = (StructDescriptor)TypeDescriptor.getTypeDescriptor(str, this.connection);
/*      */ 
/* 1830 */         if (localSTRUCT == null)
/* 1831 */           localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
/*      */         else {
/* 1833 */           localSTRUCT.setDescriptor(localStructDescriptor);
/*      */         }
/*      */       }
/* 1836 */       if (i2 != 0)
/*      */       {
/* 1838 */         this.typeVersion = paramPickleContext.readLength();
/*      */       }
/*      */       else {
/* 1841 */         this.typeVersion = 1;
/*      */       }
/* 1843 */       if ((n | i1) != 0) {
/* 1844 */         DatabaseError.throwSqlException(23);
/*      */       }
/* 1846 */       paramPickleContext.skipTo(l);
/*      */     }
/*      */ 
/* 1849 */     return localSTRUCT;
/*      */   }
/*      */ 
/*      */   protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1863 */     byte b1 = paramPickleContext.readByte();
/* 1864 */     byte b2 = 0;
/*      */ 
/* 1866 */     if (PickleContext.isAtomicNull(b1))
/* 1867 */       return null;
/* 1868 */     if (PickleContext.isImmediatelyEmbeddedNull(b1)) {
/* 1869 */       b2 = paramPickleContext.readByte();
/*      */     }
/* 1871 */     STRUCT localSTRUCT = unpickle81datum(paramPickleContext, b1, b2);
/*      */ 
/* 1873 */     return toObject(localSTRUCT, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1883 */     STRUCT localSTRUCT = unpickle81datum(paramPickleContext, paramByte, 0);
/*      */ 
/* 1885 */     return toObject(localSTRUCT, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   private STRUCT unpickle81datum(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
/*      */     throws SQLException
/*      */   {
/* 1895 */     int i = getNumAttrs();
/*      */ 
/* 1898 */     StructDescriptor localStructDescriptor = createStructDescriptor();
/* 1899 */     STRUCT localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
/* 1900 */     OracleType localOracleType = getAttrTypeAt(0);
/* 1901 */     Object localObject = null;
/*      */ 
/* 1905 */     if ((PickleContext.isImmediatelyEmbeddedNull(paramByte1)) && (paramByte2 == 1))
/* 1906 */       localObject = null;
/* 1907 */     else if (PickleContext.isImmediatelyEmbeddedNull(paramByte1)) {
/* 1908 */       localObject = ((OracleTypeADT)localOracleType).unpickle81datum(paramPickleContext, paramByte1, (byte)(paramByte2 - 1));
/*      */     }
/* 1910 */     else if (PickleContext.isElementNull(paramByte1))
/*      */     {
/* 1912 */       if ((localOracleType.getTypeCode() == 2002) || (localOracleType.getTypeCode() == 2008))
/*      */       {
/* 1914 */         localObject = localOracleType.unpickle81datumAsNull(paramPickleContext, paramByte1, paramByte2);
/*      */       }
/* 1916 */       else localObject = null;
/*      */     }
/*      */     else {
/* 1919 */       localObject = localOracleType.unpickle81rec(paramPickleContext, paramByte1, 1, null);
/*      */     }
/*      */ 
/* 1922 */     Datum[] arrayOfDatum = new Datum[i];
/*      */ 
/* 1924 */     arrayOfDatum[0] = ((Datum)localObject);
/*      */ 
/* 1926 */     for (int j = 1; j < i; j++)
/*      */     {
/* 1928 */       localOracleType = getAttrTypeAt(j);
/* 1929 */       arrayOfDatum[j] = ((Datum)localOracleType.unpickle81rec(paramPickleContext, 1, null));
/*      */     }
/*      */ 
/* 1932 */     localSTRUCT.setDatumArray(arrayOfDatum);
/*      */ 
/* 1934 */     return (STRUCT)localSTRUCT;
/*      */   }
/*      */ 
/*      */   protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
/*      */     throws SQLException
/*      */   {
/* 1940 */     int i = getNumAttrs();
/*      */ 
/* 1943 */     StructDescriptor localStructDescriptor = createStructDescriptor();
/* 1944 */     STRUCT localSTRUCT = createByteSTRUCT(localStructDescriptor, (byte[])null);
/* 1945 */     Datum[] arrayOfDatum = new Datum[i];
/*      */ 
/* 1948 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1950 */       OracleType localOracleType = getAttrTypeAt(j);
/* 1951 */       if ((localOracleType.getTypeCode() == 2002) || (localOracleType.getTypeCode() == 2008))
/*      */       {
/* 1953 */         arrayOfDatum[j] = localOracleType.unpickle81datumAsNull(paramPickleContext, paramByte1, paramByte2);
/*      */       }
/* 1955 */       else arrayOfDatum[j] = ((Datum)(j == 0 ? null : localOracleType.unpickle81rec(paramPickleContext, 1, null)));
/*      */     }
/* 1957 */     localSTRUCT.setDatumArray(arrayOfDatum);
/* 1958 */     return localSTRUCT;
/*      */   }
/*      */ 
/*      */   public byte[] pickle81(Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/* 1977 */     PickleContext localPickleContext = new PickleContext();
/*      */ 
/* 1979 */     localPickleContext.initStream();
/* 1980 */     pickle81(localPickleContext, paramDatum);
/*      */ 
/* 1982 */     byte[] arrayOfByte = localPickleContext.stream2Bytes();
/*      */ 
/* 1985 */     paramDatum.setShareBytes(arrayOfByte);
/*      */ 
/* 1987 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/* 1996 */     int i = paramPickleContext.offset() + 2;
/* 1997 */     int j = 0;
/*      */ 
/* 2000 */     j += paramPickleContext.writeImageHeader(shouldHavePrefix());
/*      */ 
/* 2002 */     j += pickle81Prefix(paramPickleContext);
/* 2003 */     j += pickle81rec(paramPickleContext, paramDatum, 0);
/*      */ 
/* 2005 */     paramPickleContext.patchImageLen(i, j);
/*      */ 
/* 2007 */     return j;
/*      */   }
/*      */ 
/*      */   private boolean hasTypeVersion()
/*      */   {
/* 2012 */     return this.typeVersion > 1;
/*      */   }
/*      */ 
/*      */   private boolean needsToid()
/*      */   {
/* 2017 */     return ((this.statusBits & 0x40) != 0) || ((this.statusBits & 0x20) == 0) || (hasTypeVersion());
/*      */   }
/*      */ 
/*      */   private boolean shouldHavePrefix()
/*      */   {
/* 2023 */     return (hasTypeVersion()) || (needsToid());
/*      */   }
/*      */ 
/*      */   protected int pickle81Prefix(PickleContext paramPickleContext)
/*      */     throws SQLException
/*      */   {
/* 2031 */     if (shouldHavePrefix())
/*      */     {
/* 2033 */       int i = 0;
/* 2034 */       int j = 1;
/* 2035 */       int k = 1;
/*      */ 
/* 2037 */       if (needsToid())
/*      */       {
/* 2039 */         k += getTOID().length;
/* 2040 */         j |= 4;
/*      */       }
/*      */ 
/* 2043 */       if (hasTypeVersion())
/*      */       {
/* 2045 */         j |= 16;
/*      */ 
/* 2047 */         if (this.typeVersion > PickleContext.KOPI20_LN_MAXV)
/* 2048 */           k += 5;
/*      */         else {
/* 2050 */           k++;
/*      */         }
/*      */       }
/* 2053 */       i = paramPickleContext.writeLength(k);
/*      */ 
/* 2055 */       i += paramPickleContext.writeData((byte)j);
/*      */ 
/* 2057 */       if (needsToid()) {
/* 2058 */         i += paramPickleContext.writeData(this.toid);
/*      */       }
/* 2060 */       if (hasTypeVersion()) {
/* 2061 */         i += paramPickleContext.writeLength(this.typeVersion);
/*      */       }
/* 2063 */       return i;
/*      */     }
/*      */ 
/* 2066 */     return 0;
/*      */   }
/*      */ 
/*      */   private int pickle81rec(PickleContext paramPickleContext, Datum paramDatum, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2077 */     int i = 0;
/*      */ 
/* 2079 */     Datum[] arrayOfDatum = ((STRUCT)paramDatum).getOracleAttributes();
/* 2080 */     int j = arrayOfDatum.length;
/* 2081 */     int k = 0;
/* 2082 */     OracleType localOracleType = getAttrTypeAt(0);
/*      */ 
/* 2084 */     if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
/*      */     {
/* 2092 */       k = 1;
/*      */ 
/* 2094 */       if (arrayOfDatum[0] == null)
/*      */       {
/* 2096 */         if (paramInt > 0)
/* 2097 */           i += paramPickleContext.writeImmediatelyEmbeddedElementNull((byte)paramInt);
/*      */         else {
/* 2099 */           i += paramPickleContext.writeAtomicNull();
/*      */         }
/*      */       }
/*      */       else {
/* 2103 */         i += ((OracleTypeADT)localOracleType).pickle81rec(paramPickleContext, arrayOfDatum[0], paramInt + 1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2108 */     for (; k < j; k++)
/*      */     {
/* 2110 */       localOracleType = getAttrTypeAt(k);
/*      */ 
/* 2114 */       if (arrayOfDatum[k] == null)
/*      */       {
/* 2116 */         if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
/*      */         {
/* 2122 */           i += paramPickleContext.writeAtomicNull();
/*      */         }
/*      */         else
/*      */         {
/* 2126 */           i += paramPickleContext.writeElementNull();
/*      */         }
/*      */ 
/*      */       }
/* 2131 */       else if (((localOracleType instanceof OracleTypeADT)) && (!(localOracleType instanceof OracleTypeCOLLECTION)) && (!(localOracleType instanceof OracleTypeUPT)))
/*      */       {
/* 2137 */         i += ((OracleTypeADT)localOracleType).pickle81rec(paramPickleContext, arrayOfDatum[k], 1);
/*      */       }
/*      */       else
/*      */       {
/* 2144 */         i += localOracleType.pickle81(paramPickleContext, arrayOfDatum[k]);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2149 */     return i;
/*      */   }
/*      */ 
/*      */   private Object toObject(STRUCT paramSTRUCT, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 2159 */     switch (paramInt)
/*      */     {
/*      */     case 1:
/* 2163 */       return paramSTRUCT;
/*      */     case 2:
/* 2166 */       if (paramSTRUCT == null) break;
/* 2167 */       return paramSTRUCT.toJdbc(paramMap);
/*      */     default:
/* 2175 */       DatabaseError.throwSqlException(1);
/*      */     }
/*      */ 
/* 2181 */     return null;
/*      */   }
/*      */ 
/*      */   public String getAttributeType(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2196 */     if (this.sqlName == null) {
/* 2197 */       getFullName();
/*      */     }
/* 2199 */     if (this.attrNames == null) {
/* 2200 */       initADTAttrNames();
/*      */     }
/* 2202 */     if ((paramInt < 1) || (paramInt > this.attrTypeNames.length)) {
/* 2203 */       DatabaseError.throwSqlException(1, "Invalid index");
/*      */     }
/* 2205 */     return this.attrTypeNames[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getAttributeType(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2215 */     if (paramBoolean) {
/* 2216 */       return getAttributeType(paramInt);
/*      */     }
/*      */ 
/* 2219 */     if ((paramInt < 1) || ((this.attrTypeNames != null) && (paramInt > this.attrTypeNames.length))) {
/* 2220 */       DatabaseError.throwSqlException(1, "Invalid index");
/*      */     }
/*      */ 
/* 2223 */     if (this.attrTypeNames != null) {
/* 2224 */       return this.attrTypeNames[(paramInt - 1)];
/*      */     }
/* 2226 */     return null;
/*      */   }
/*      */ 
/*      */   public String getAttributeName(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2238 */     if (this.attrNames == null) {
/* 2239 */       initADTAttrNames();
/*      */     }
/* 2241 */     synchronized (this)
/*      */     {
/* 2243 */       if ((paramInt < 1) || (paramInt > this.attrNames.length)) {
/* 2244 */         DatabaseError.throwSqlException(1, "Invalid index");
/*      */       }
/*      */     }
/*      */ 
/* 2248 */     return this.attrNames[(paramInt - 1)];
/*      */   }
/*      */ 
/*      */   public String getAttributeName(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2258 */     if (paramBoolean) {
/* 2259 */       return getAttributeName(paramInt);
/*      */     }
/*      */ 
/* 2262 */     if ((paramInt < 1) || ((this.attrNames != null) && (paramInt > this.attrNames.length))) {
/* 2263 */       DatabaseError.throwSqlException(1, "Invalid index");
/*      */     }
/*      */ 
/* 2266 */     if (this.attrNames != null) {
/* 2267 */       return this.attrNames[(paramInt - 1)];
/*      */     }
/* 2269 */     return null;
/*      */   }
/*      */ 
/*      */   private void initADTAttrNames()
/*      */     throws SQLException
/*      */   {
/* 2341 */     CallableStatement localCallableStatement = null;
/* 2342 */     PreparedStatement localPreparedStatement = null;
/* 2343 */     ResultSet localResultSet = null;
/* 2344 */     String[] arrayOfString1 = new String[this.attrTypes.length];
/* 2345 */     String[] arrayOfString2 = new String[this.attrTypes.length];
/* 2346 */     int i = 0;
/* 2347 */     int j = 0;
/*      */ 
/* 2352 */     synchronized (this.connection)
/*      */     {
/* 2354 */       synchronized (this)
/*      */       {
/* 2356 */         if (this.attrNames == null)
/*      */         {
/* 2358 */           i = this.sqlName.getSchema().equalsIgnoreCase(this.connection.getUserName()) ? 0 : 3;
/*      */           while (true)
/*      */           {
/* 2361 */             if (i != 6)
/*      */             {
/* 2363 */               switch (i)
/*      */               {
/*      */               case 0:
/* 2366 */                 localPreparedStatement = this.connection.prepareStatement(sqlString[i]);
/* 2367 */                 localPreparedStatement.setString(1, this.sqlName.getSimpleName());
/* 2368 */                 localResultSet = localPreparedStatement.executeQuery();
/* 2369 */                 i = 1;
/* 2370 */                 break;
/*      */               case 1:
/* 2372 */                 localPreparedStatement = this.connection.prepareStatement(sqlString[i]);
/* 2373 */                 localPreparedStatement.setString(1, this.sqlName.getSimpleName());
/* 2374 */                 localPreparedStatement.setString(2, this.sqlName.getSimpleName());
/* 2375 */                 i = 2;
/*      */                 try { localResultSet = localPreparedStatement.executeQuery();
/*      */                 }
/*      */                 catch (SQLException localSQLException)
/*      */                 {
/*      */                 }
/* 2381 */                 if (localSQLException.getErrorCode() == 1436) {
/*      */                   continue;
/*      */                 }
/* 2384 */                 throw localSQLException;
/*      */               case 2:
/* 2388 */                 localPreparedStatement = this.connection.prepareStatement(sqlString[i]);
/* 2389 */                 localPreparedStatement.setString(1, this.sqlName.getSimpleName());
/* 2390 */                 localPreparedStatement.setString(2, this.sqlName.getSimpleName());
/* 2391 */                 localResultSet = localPreparedStatement.executeQuery();
/* 2392 */                 i = 4;
/* 2393 */                 break;
/*      */               case 3:
/* 2395 */                 localPreparedStatement = this.connection.prepareStatement(sqlString[i]);
/* 2396 */                 localPreparedStatement.setString(1, this.sqlName.getSchema());
/* 2397 */                 localPreparedStatement.setString(2, this.sqlName.getSimpleName());
/* 2398 */                 localResultSet = localPreparedStatement.executeQuery();
/* 2399 */                 i = 4;
/* 2400 */                 break;
/*      */               case 4:
/* 2402 */                 localPreparedStatement = this.connection.prepareStatement(sqlString[i]);
/* 2403 */                 localPreparedStatement.setString(1, this.sqlName.getSimpleName());
/* 2404 */                 localPreparedStatement.setString(2, this.sqlName.getSimpleName());
/* 2405 */                 localResultSet = localPreparedStatement.executeQuery();
/* 2406 */                 i = 5;
/* 2407 */                 break;
/*      */               case 5:
/* 2409 */                 localCallableStatement = this.connection.prepareCall(sqlString[i]);
/* 2410 */                 localCallableStatement.setString(1, this.sqlName.getSimpleName());
/* 2411 */                 localCallableStatement.registerOutParameter(2, -10);
/* 2412 */                 localCallableStatement.execute();
/* 2413 */                 localResultSet = ((OracleCallableStatement)localCallableStatement).getCursor(2);
/* 2414 */                 i = 6;
/*      */               default:
/*      */                 try
/*      */                 {
/* 2420 */                   if (localPreparedStatement != null) {
/* 2421 */                     localPreparedStatement.setFetchSize(this.idx);
/*      */                   }
/* 2423 */                   j = 0;
/* 2424 */                   while ((j < this.attrTypes.length) && (localResultSet.next()))
/*      */                   {
/* 2427 */                     if (localResultSet.getInt(1) != j + 1) {
/* 2428 */                       DatabaseError.throwSqlException(1, "inconsistent ADT attribute");
/*      */                     }
/*      */ 
/* 2432 */                     arrayOfString1[j] = localResultSet.getString(2);
/*      */ 
/* 2435 */                     arrayOfString2[j] = (localResultSet.getString(4) + "." + localResultSet.getString(3));
/*      */ 
/* 2425 */                     j++;
/*      */                   }
/*      */ 
/* 2438 */                   if (j != 0)
/*      */                   {
/* 2440 */                     this.attrTypeNames = arrayOfString2;
/*      */ 
/* 2442 */                     this.attrNames = arrayOfString1;
/* 2443 */                     i = 6;
/*      */                   }
/*      */                   else {
/* 2446 */                     if (localResultSet != null)
/* 2447 */                       localResultSet.close();
/* 2448 */                     if (localPreparedStatement != null)
/* 2449 */                       localPreparedStatement.close();
/*      */                   }
/*      */                 } finally {
/* 2452 */                   if (localResultSet != null)
/* 2453 */                     localResultSet.close();
/* 2454 */                   if (localPreparedStatement != null)
/* 2455 */                     localPreparedStatement.close();
/* 2456 */                   if (localCallableStatement != null)
/* 2457 */                     localCallableStatement.close();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   StructDescriptor createStructDescriptor()
/*      */     throws SQLException
/*      */   {
/* 2478 */     return new StructDescriptor(this, this.connection);
/*      */   }
/*      */ 
/*      */   STRUCT createObjSTRUCT(StructDescriptor paramStructDescriptor, Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/* 2484 */     if ((this.statusBits & 0x10) != 0) {
/* 2485 */       return new JAVA_STRUCT(paramStructDescriptor, this.connection, paramArrayOfObject);
/*      */     }
/* 2487 */     return new STRUCT(paramStructDescriptor, this.connection, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   STRUCT createByteSTRUCT(StructDescriptor paramStructDescriptor, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2493 */     if ((this.statusBits & 0x10) != 0) {
/* 2494 */       return new JAVA_STRUCT(paramStructDescriptor, paramArrayOfByte, this.connection);
/*      */     }
/* 2496 */     return new STRUCT(paramStructDescriptor, paramArrayOfByte, this.connection);
/*      */   }
/*      */ 
/*      */   public static String getSubtypeName(Connection paramConnection, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2510 */     PickleContext localPickleContext = new PickleContext(paramArrayOfByte, paramLong);
/* 2511 */     byte b = localPickleContext.readByte();
/*      */ 
/* 2513 */     if ((!PickleContext.is81format(b)) || (PickleContext.isCollectionImage_pctx(b)) || (!PickleContext.hasPrefix(b)))
/*      */     {
/* 2515 */       return null;
/*      */     }
/*      */ 
/* 2519 */     if (!localPickleContext.readAndCheckVersion()) {
/* 2520 */       DatabaseError.throwSqlException(1, "Image version is not recognized");
/*      */     }
/*      */ 
/* 2525 */     localPickleContext.skipLength();
/*      */ 
/* 2528 */     localPickleContext.skipLength();
/*      */ 
/* 2530 */     b = localPickleContext.readByte();
/*      */ 
/* 2533 */     if ((b & 0x4) != 0)
/*      */     {
/* 2535 */       byte[] arrayOfByte = localPickleContext.readBytes(16);
/*      */ 
/* 2537 */       return toid2typename(paramConnection, arrayOfByte);
/*      */     }
/*      */ 
/* 2546 */     return null;
/*      */   }
/*      */ 
/*      */   public static String toid2typename(Connection paramConnection, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2554 */     String str = (String)((OracleConnection)paramConnection).getDescriptor(paramArrayOfByte);
/*      */ 
/* 2556 */     if (str == null)
/*      */     {
/* 2558 */       PreparedStatement localPreparedStatement = null;
/* 2559 */       ResultSet localResultSet = null;
/*      */       try
/*      */       {
/* 2563 */         localPreparedStatement = paramConnection.prepareStatement("select owner, type_name from all_types where type_oid = :1");
/*      */ 
/* 2566 */         localPreparedStatement.setBytes(1, paramArrayOfByte);
/*      */ 
/* 2568 */         localResultSet = localPreparedStatement.executeQuery();
/*      */ 
/* 2570 */         if (localResultSet.next())
/*      */         {
/* 2572 */           str = localResultSet.getString(1) + "." + localResultSet.getString(2);
/*      */ 
/* 2574 */           ((OracleConnection)paramConnection).putDescriptor(paramArrayOfByte, str);
/*      */         }
/*      */         else {
/* 2577 */           DatabaseError.throwSqlException(1, "Invalid type oid");
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 2582 */         if (localResultSet != null) {
/* 2583 */           localResultSet.close();
/*      */         }
/* 2585 */         if (localPreparedStatement != null) {
/* 2586 */           localPreparedStatement.close();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2591 */     return str;
/*      */   }
/*      */   public int getTdsVersion() {
/* 2594 */     return this.tdsVersion;
/*      */   }
/*      */ 
/*      */   public void printDebug()
/*      */   {
/*      */   }
/*      */ 
/*      */   private String debugText()
/*      */   {
/* 2613 */     StringWriter localStringWriter = new StringWriter();
/* 2614 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/*      */ 
/* 2616 */     localPrintWriter.println("OracleTypeADT = " + this);
/* 2617 */     localPrintWriter.println("sqlName = " + this.sqlName);
/*      */ 
/* 2619 */     localPrintWriter.println("OracleType[] : ");
/*      */ 
/* 2621 */     if (this.attrTypes != null)
/*      */     {
/* 2623 */       for (int i = 0; i < this.attrTypes.length; i++) {
/* 2624 */         localPrintWriter.println("[" + i + "] = " + this.attrTypes[i]);
/*      */       }
/*      */     }
/* 2627 */     localPrintWriter.println("null");
/*      */ 
/* 2629 */     localPrintWriter.println("LDS : ");
/*      */ 
/* 2631 */     if (this.lds != null)
/* 2632 */       printUnsignedByteArray(this.lds, localPrintWriter);
/*      */     else {
/* 2634 */       localPrintWriter.println("null");
/*      */     }
/* 2636 */     localPrintWriter.println("toid : ");
/*      */ 
/* 2638 */     if (this.toid != null)
/* 2639 */       printUnsignedByteArray(this.toid, localPrintWriter);
/*      */     else {
/* 2641 */       localPrintWriter.println("null");
/*      */     }
/* 2643 */     localPrintWriter.println("fdo : ");
/*      */ 
/* 2645 */     if (this.fdo != null)
/* 2646 */       printUnsignedByteArray(this.fdo, localPrintWriter);
/*      */     else {
/* 2648 */       localPrintWriter.println("null");
/*      */     }
/* 2650 */     localPrintWriter.println("tds version : " + this.tdsVersion);
/* 2651 */     localPrintWriter.println("type version : " + this.typeVersion);
/* 2652 */     localPrintWriter.println("type version : " + this.typeVersion);
/* 2653 */     localPrintWriter.println("bigEndian : " + (this.bigEndian ? "true" : "false"));
/* 2654 */     localPrintWriter.println("opcode : " + this.opcode);
/*      */ 
/* 2656 */     localPrintWriter.println("tdoCState : " + this.tdoCState);
/*      */ 
/* 2658 */     return localStringWriter.toString();
/*      */   }
/*      */ 
/*      */   public byte[] getTOID()
/*      */   {
/*      */     try
/*      */     {
/* 2674 */       if (this.toid == null)
/*      */       {
/* 2677 */         initMetadata(this.connection);
/*      */       }
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*      */     }
/* 2682 */     return this.toid;
/*      */   }
/*      */ 
/*      */   public int getImageFormatVersion()
/*      */   {
/* 2687 */     return PickleContext.KOPI20_VERSION;
/*      */   }
/*      */ 
/*      */   public int getTypeVersion()
/*      */   {
/*      */     try
/*      */     {
/* 2694 */       if (this.typeVersion == -1)
/*      */       {
/* 2696 */         initMetadata(this.connection);
/*      */       }
/*      */     } catch (SQLException localSQLException) {
/*      */     }
/* 2700 */     return this.typeVersion;
/*      */   }
/*      */ 
/*      */   public int getCharSet()
/*      */   {
/* 2705 */     return this.charSetId;
/*      */   }
/*      */ 
/*      */   public int getCharSetForm()
/*      */   {
/* 2710 */     return this.charSetForm;
/*      */   }
/*      */ 
/*      */   public synchronized long getTdoCState()
/*      */   {
/*      */     try
/*      */     {
/* 2721 */       if (this.tdoCState == 0L)
/*      */       {
/* 2723 */         getFullName();
/* 2724 */         this.tdoCState = this.connection.getTdoCState(this.sqlName.getSchema(), this.sqlName.getSimpleName());
/*      */       }
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*      */     }
/* 2729 */     return this.tdoCState;
/*      */   }
/*      */ 
/*      */   public long getFIXED_DATA_SIZE()
/*      */   {
/*      */     try
/*      */     {
/* 2736 */       return getFixedDataSize();
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*      */     }
/* 2740 */     return 0L;
/*      */   }
/*      */ 
/*      */   public long[] getLDS_OFFSET_ARRAY()
/*      */   {
/*      */     try
/*      */     {
/* 2747 */       return getLdsOffsetArray();
/*      */     }
/*      */     catch (SQLException localSQLException) {
/*      */     }
/* 2751 */     return null;
/*      */   }
/*      */ 
/*      */   synchronized long[] getLdsOffsetArray() throws SQLException
/*      */   {
/* 2756 */     if ((this.ldsOffsetArray == null) && (this.connection != null))
/*      */     {
/* 2758 */       generateLDS();
/*      */     }
/* 2760 */     return this.ldsOffsetArray;
/*      */   }
/*      */ 
/*      */   public long getFixedDataSize() throws SQLException
/*      */   {
/* 2765 */     return this.fixedDataSize;
/*      */   }
/*      */ 
/*      */   public int getAlignmentReq() throws SQLException
/*      */   {
/* 2770 */     return this.alignmentRequirement;
/*      */   }
/*      */ 
/*      */   public int getNumAttrs() throws SQLException
/*      */   {
/* 2775 */     if ((this.attrTypes == null) && (this.connection != null)) {
/* 2776 */       init(this.connection);
/*      */     }
/* 2778 */     return this.attrTypes.length;
/*      */   }
/*      */ 
/*      */   public OracleType getAttrTypeAt(int paramInt) throws SQLException
/*      */   {
/* 2783 */     if ((this.attrTypes == null) && (this.connection != null)) {
/* 2784 */       init(this.connection);
/*      */     }
/* 2786 */     return this.attrTypes[paramInt];
/*      */   }
/*      */ 
/*      */   public boolean isEmbeddedADT() throws SQLException
/*      */   {
/* 2791 */     return (this.statusBits & 0x2) != 0;
/*      */   }
/*      */ 
/*      */   public boolean isUptADT() throws SQLException
/*      */   {
/* 2796 */     return (this.statusBits & 0x4) != 0;
/*      */   }
/*      */ 
/*      */   public boolean isTopADT() throws SQLException
/*      */   {
/* 2801 */     return (this.statusBits & 0x1) != 0;
/*      */   }
/*      */ 
/*      */   public void setStatus(int paramInt) throws SQLException
/*      */   {
/* 2806 */     this.statusBits = paramInt;
/*      */   }
/*      */ 
/*      */   void setEmbeddedADT() throws SQLException
/*      */   {
/* 2811 */     maskAndSetStatusBits(-16, 2);
/*      */   }
/*      */ 
/*      */   void setUptADT() throws SQLException
/*      */   {
/* 2816 */     maskAndSetStatusBits(-16, 4);
/*      */   }
/*      */ 
/*      */   public boolean isSubType() throws SQLException
/*      */   {
/* 2821 */     return (this.statusBits & 0x40) != 0;
/*      */   }
/*      */ 
/*      */   public boolean isFinalType()
/*      */     throws SQLException
/*      */   {
/* 2828 */     return ((this.statusBits & 0x20) != 0 ? 1 : 0) | ((this.statusBits & 0x2) != 0 ? 1 : 0);
/*      */   }
/*      */ 
/*      */   public boolean isJavaObject() throws SQLException
/*      */   {
/* 2833 */     return (this.statusBits & 0x10) != 0;
/*      */   }
/*      */ 
/*      */   public int getStatus()
/*      */     throws SQLException
/*      */   {
/* 2840 */     if (((this.statusBits & 0x1) != 0) && ((this.statusBits & 0x100) == 0))
/* 2841 */       init(this.connection);
/* 2842 */     return this.statusBits;
/*      */   }
/*      */ 
/*      */   public static OracleTypeADT shallowClone(OracleTypeADT paramOracleTypeADT)
/*      */     throws SQLException
/*      */   {
/* 2848 */     OracleTypeADT localOracleTypeADT = new OracleTypeADT();
/* 2849 */     shallowCopy(paramOracleTypeADT, localOracleTypeADT);
/* 2850 */     return localOracleTypeADT;
/*      */   }
/*      */ 
/*      */   public static void shallowCopy(OracleTypeADT paramOracleTypeADT1, OracleTypeADT paramOracleTypeADT2)
/*      */     throws SQLException
/*      */   {
/* 2856 */     paramOracleTypeADT2.connection = paramOracleTypeADT1.connection;
/* 2857 */     paramOracleTypeADT2.sqlName = paramOracleTypeADT1.sqlName;
/* 2858 */     paramOracleTypeADT2.parent = paramOracleTypeADT1.parent;
/* 2859 */     paramOracleTypeADT2.idx = paramOracleTypeADT1.idx;
/* 2860 */     paramOracleTypeADT2.descriptor = paramOracleTypeADT1.descriptor;
/* 2861 */     paramOracleTypeADT2.statusBits = paramOracleTypeADT1.statusBits;
/*      */ 
/* 2863 */     paramOracleTypeADT2.nullOffset = paramOracleTypeADT1.nullOffset;
/* 2864 */     paramOracleTypeADT2.ldsOffset = paramOracleTypeADT1.ldsOffset;
/* 2865 */     paramOracleTypeADT2.sizeForLds = paramOracleTypeADT1.sizeForLds;
/* 2866 */     paramOracleTypeADT2.alignForLds = paramOracleTypeADT1.alignForLds;
/* 2867 */     paramOracleTypeADT2.typeCode = paramOracleTypeADT1.typeCode;
/* 2868 */     paramOracleTypeADT2.dbTypeCode = paramOracleTypeADT1.dbTypeCode;
/* 2869 */     paramOracleTypeADT2.tdsVersion = paramOracleTypeADT1.tdsVersion;
/* 2870 */     paramOracleTypeADT2.typeVersion = paramOracleTypeADT1.typeVersion;
/* 2871 */     paramOracleTypeADT2.lds = paramOracleTypeADT1.lds;
/* 2872 */     paramOracleTypeADT2.ldsOffsetArray = paramOracleTypeADT1.ldsOffsetArray;
/* 2873 */     paramOracleTypeADT2.fixedDataSize = paramOracleTypeADT1.fixedDataSize;
/* 2874 */     paramOracleTypeADT2.alignmentRequirement = paramOracleTypeADT1.alignmentRequirement;
/* 2875 */     paramOracleTypeADT2.attrTypes = paramOracleTypeADT1.attrTypes;
/* 2876 */     paramOracleTypeADT2.sqlName = paramOracleTypeADT1.sqlName;
/* 2877 */     paramOracleTypeADT2.tdoCState = paramOracleTypeADT1.tdoCState;
/* 2878 */     paramOracleTypeADT2.toid = paramOracleTypeADT1.toid;
/* 2879 */     paramOracleTypeADT2.fdo = paramOracleTypeADT1.fdo;
/* 2880 */     paramOracleTypeADT2.charSetId = paramOracleTypeADT1.charSetId;
/* 2881 */     paramOracleTypeADT2.charSetForm = paramOracleTypeADT1.charSetForm;
/* 2882 */     paramOracleTypeADT2.bigEndian = paramOracleTypeADT1.bigEndian;
/* 2883 */     paramOracleTypeADT2.flattenedAttrNum = paramOracleTypeADT1.flattenedAttrNum;
/* 2884 */     paramOracleTypeADT2.statusBits = paramOracleTypeADT1.statusBits;
/* 2885 */     paramOracleTypeADT2.attrNames = paramOracleTypeADT1.attrNames;
/* 2886 */     paramOracleTypeADT2.attrTypeNames = paramOracleTypeADT1.attrTypeNames;
/* 2887 */     paramOracleTypeADT2.opcode = paramOracleTypeADT1.opcode;
/* 2888 */     paramOracleTypeADT2.idx = paramOracleTypeADT1.idx;
/*      */   }
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 2901 */     paramObjectOutputStream.writeInt(this.statusBits);
/* 2902 */     paramObjectOutputStream.writeInt(this.tdsVersion);
/* 2903 */     paramObjectOutputStream.writeInt(this.typeVersion);
/* 2904 */     paramObjectOutputStream.writeObject(this.lds);
/* 2905 */     paramObjectOutputStream.writeObject(this.ldsOffsetArray);
/* 2906 */     paramObjectOutputStream.writeLong(this.fixedDataSize);
/* 2907 */     paramObjectOutputStream.writeInt(this.alignmentRequirement);
/* 2908 */     paramObjectOutputStream.writeObject(this.attrTypes);
/* 2909 */     paramObjectOutputStream.writeObject(this.attrNames);
/* 2910 */     paramObjectOutputStream.writeObject(this.attrTypeNames);
/* 2911 */     paramObjectOutputStream.writeLong(this.tdoCState);
/* 2912 */     paramObjectOutputStream.writeObject(this.toid);
/* 2913 */     paramObjectOutputStream.writeObject(this.fdo);
/* 2914 */     paramObjectOutputStream.writeInt(this.charSetId);
/* 2915 */     paramObjectOutputStream.writeInt(this.charSetForm);
/* 2916 */     paramObjectOutputStream.writeBoolean(this.bigEndian);
/* 2917 */     paramObjectOutputStream.writeInt(this.flattenedAttrNum);
/*      */   }
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 2926 */     this.statusBits = paramObjectInputStream.readInt();
/* 2927 */     this.tdsVersion = paramObjectInputStream.readInt();
/* 2928 */     this.typeVersion = paramObjectInputStream.readInt();
/* 2929 */     paramObjectInputStream.readObject();
/* 2930 */     paramObjectInputStream.readObject();
/* 2931 */     paramObjectInputStream.readLong();
/* 2932 */     paramObjectInputStream.readInt();
/* 2933 */     this.attrTypes = ((OracleType[])paramObjectInputStream.readObject());
/* 2934 */     this.attrNames = ((String[])paramObjectInputStream.readObject());
/* 2935 */     this.attrTypeNames = ((String[])paramObjectInputStream.readObject());
/* 2936 */     paramObjectInputStream.readLong();
/* 2937 */     this.toid = ((byte[])paramObjectInputStream.readObject());
/* 2938 */     this.fdo = ((byte[])paramObjectInputStream.readObject());
/* 2939 */     this.charSetId = paramObjectInputStream.readInt();
/* 2940 */     this.charSetForm = paramObjectInputStream.readInt();
/* 2941 */     this.bigEndian = paramObjectInputStream.readBoolean();
/* 2942 */     this.flattenedAttrNum = paramObjectInputStream.readInt();
/*      */   }
/*      */ 
/*      */   public synchronized void setConnection(OracleConnection paramOracleConnection) throws SQLException
/*      */   {
/* 2947 */     this.connection = paramOracleConnection;
/* 2948 */     for (int i = 0; i < this.attrTypes.length; i++)
/* 2949 */       this.attrTypes[i].setConnection(this.connection);
/*      */   }
/*      */ 
/*      */   private synchronized void setStatusBits(int paramInt)
/*      */   {
/* 2954 */     this.statusBits |= paramInt;
/*      */   }
/*      */ 
/*      */   private synchronized void maskAndSetStatusBits(int paramInt1, int paramInt2)
/*      */   {
/* 2959 */     this.statusBits &= paramInt1;
/* 2960 */     this.statusBits |= paramInt2;
/*      */   }
/*      */ 
/*      */   private void printUnsignedByteArray(byte[] paramArrayOfByte, PrintWriter paramPrintWriter)
/*      */   {
/* 2966 */     int i = paramArrayOfByte.length;
/*      */ 
/* 2969 */     int[] arrayOfInt = Util.toJavaUnsignedBytes(paramArrayOfByte);
/*      */ 
/* 2971 */     for (int j = 0; j < i; j++)
/*      */     {
/* 2973 */       paramPrintWriter.print("0x" + Integer.toHexString(arrayOfInt[j]) + " ");
/*      */     }
/*      */ 
/* 2976 */     paramPrintWriter.println();
/*      */ 
/* 2978 */     for (j = 0; j < i; j++)
/*      */     {
/* 2980 */       paramPrintWriter.print(arrayOfInt[j] + " ");
/*      */     }
/*      */ 
/* 2983 */     paramPrintWriter.println();
/*      */   }
/*      */ 
/*      */   public void initChildNamesRecursively(Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 2989 */     TypeTreeElement localTypeTreeElement = (TypeTreeElement)paramMap.get(this.sqlName);
/*      */ 
/* 2991 */     if ((this.attrTypes != null) && (this.attrTypes.length > 0))
/*      */     {
/* 2993 */       for (int i = 0; i < this.attrTypes.length; i++)
/*      */       {
/* 2995 */         OracleType localOracleType = this.attrTypes[i];
/* 2996 */         localOracleType.setNames(localTypeTreeElement.getChildSchemaName(i + 1), localTypeTreeElement.getChildTypeName(i + 1));
/* 2997 */         localOracleType.initChildNamesRecursively(paramMap);
/* 2998 */         localOracleType.cacheDescriptor();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cacheDescriptor() throws SQLException
/*      */   {
/* 3005 */     this.descriptor = StructDescriptor.createDescriptor(this);
/*      */   }
/*      */ 
/*      */   public void printXML(PrintWriter paramPrintWriter, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 3011 */     for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 3012 */     paramPrintWriter.print("<OracleTypeADT sqlName=\"" + this.sqlName + "\" " + " hashCode=\"" + hashCode() + "\" " + " toid=\"");
/*      */ 
/* 3016 */     if (this.toid != null)
/* 3017 */       printUnsignedByteArray(this.toid, paramPrintWriter);
/*      */     else {
/* 3019 */       paramPrintWriter.print("null");
/*      */     }
/* 3021 */     paramPrintWriter.println(" \"  typecode=\"" + this.typeCode + "\"" + " tds version=\"" + this.tdsVersion + "\"" + " is embedded=\"" + isEmbeddedADT() + "\"" + " is top level=\"" + isTopADT() + "\"" + " is upt=\"" + isUptADT() + "\"" + " finalType=\"" + isFinalType() + "\"" + " subtype=\"" + isSubType() + "\"" + " ldsOffset=\"" + this.ldsOffset + "\"" + " sizeForLds=\"" + this.sizeForLds + "\"" + " alignForLds=\"" + this.alignForLds + "\"" + " ldsOffsetArray size=\"" + (this.ldsOffsetArray == null ? "null" : Integer.toString(this.ldsOffsetArray.length)) + "\"" + ">");
/*      */ 
/* 3034 */     if ((this.attrTypes != null) && (this.attrTypes.length > 0))
/* 3035 */       for (i = 0; i < this.attrTypes.length; i++)
/* 3036 */         this.attrTypes[i].printXML(paramPrintWriter, paramInt + 1);
/* 3037 */     for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 3038 */     paramPrintWriter.println("</OracleTypeADT>");
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeADT
 * JD-Core Version:    0.6.0
 */