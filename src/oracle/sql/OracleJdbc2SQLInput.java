/*      */ package oracle.sql;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLInput;
/*      */ import java.sql.Struct;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ public class OracleJdbc2SQLInput
/*      */   implements SQLInput
/*      */ {
/*      */   private int index;
/*      */   private Datum[] attributes;
/*      */   private Map map;
/*      */   private OracleConnection conn;
/* 1174 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*      */ 
/*      */   public OracleJdbc2SQLInput(Datum[] paramArrayOfDatum, Map paramMap, OracleConnection paramOracleConnection)
/*      */   {
/*   86 */     this.attributes = paramArrayOfDatum;
/*   87 */     this.map = paramMap;
/*   88 */     this.conn = paramOracleConnection;
/*   89 */     this.index = 0;
/*      */   }
/*      */ 
/*      */   public String readString()
/*      */     throws SQLException
/*      */   {
/*  108 */     String str = null;
/*      */     try
/*      */     {
/*  112 */       if (this.attributes[this.index] != null)
/*      */       {
/*  114 */         str = this.attributes[this.index].stringValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  119 */       this.index += 1;
/*      */     }
/*      */ 
/*  124 */     return str;
/*      */   }
/*      */ 
/*      */   public boolean readBoolean()
/*      */     throws SQLException
/*      */   {
/*  137 */     boolean bool = false;
/*      */     try
/*      */     {
/*  141 */       if (this.attributes[this.index] != null)
/*      */       {
/*  143 */         bool = this.attributes[this.index].booleanValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  148 */       this.index += 1;
/*      */     }
/*      */ 
/*  153 */     return bool;
/*      */   }
/*      */ 
/*      */   public byte readByte()
/*      */     throws SQLException
/*      */   {
/*  166 */     int i = 0;
/*      */     try
/*      */     {
/*  170 */       if (this.attributes[this.index] != null)
/*      */       {
/*  172 */         i = this.attributes[this.index].byteValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  177 */       this.index += 1;
/*      */     }
/*      */ 
/*  182 */     return i;
/*      */   }
/*      */ 
/*      */   public short readShort()
/*      */     throws SQLException
/*      */   {
/*  202 */     long l = readLong();
/*      */ 
/*  204 */     if ((l > 65537L) || (l < -65538L))
/*      */     {
/*  210 */       DatabaseError.throwSqlException(26, "readShort");
/*      */     }
/*      */ 
/*  214 */     int i = (short)(int)l;
/*      */ 
/*  218 */     return i;
/*      */   }
/*      */ 
/*      */   public int readInt()
/*      */     throws SQLException
/*      */   {
/*  231 */     int i = 0;
/*      */     try
/*      */     {
/*  235 */       if (this.attributes[this.index] != null)
/*      */       {
/*  237 */         i = this.attributes[this.index].intValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  242 */       this.index += 1;
/*      */     }
/*      */ 
/*  247 */     return i;
/*      */   }
/*      */ 
/*      */   public long readLong()
/*      */     throws SQLException
/*      */   {
/*  260 */     long l = 0L;
/*      */     try
/*      */     {
/*  264 */       if (this.attributes[this.index] != null)
/*      */       {
/*  266 */         l = this.attributes[this.index].longValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  271 */       this.index += 1;
/*      */     }
/*      */ 
/*  276 */     return l;
/*      */   }
/*      */ 
/*      */   public float readFloat()
/*      */     throws SQLException
/*      */   {
/*  289 */     float f = 0.0F;
/*      */     try
/*      */     {
/*  293 */       if (this.attributes[this.index] != null)
/*      */       {
/*  295 */         f = this.attributes[this.index].floatValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  300 */       this.index += 1;
/*      */     }
/*      */ 
/*  305 */     return f;
/*      */   }
/*      */ 
/*      */   public double readDouble()
/*      */     throws SQLException
/*      */   {
/*  318 */     double d = 0.0D;
/*      */     try
/*      */     {
/*  322 */       if (this.attributes[this.index] != null)
/*      */       {
/*  324 */         d = this.attributes[this.index].doubleValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  329 */       this.index += 1;
/*      */     }
/*      */ 
/*  334 */     return d;
/*      */   }
/*      */ 
/*      */   public BigDecimal readBigDecimal()
/*      */     throws SQLException
/*      */   {
/*  347 */     BigDecimal localBigDecimal = null;
/*      */     try
/*      */     {
/*  351 */       if (this.attributes[this.index] != null)
/*      */       {
/*  353 */         localBigDecimal = this.attributes[this.index].bigDecimalValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  358 */       this.index += 1;
/*      */     }
/*      */ 
/*  363 */     return localBigDecimal;
/*      */   }
/*      */ 
/*      */   public byte[] readBytes()
/*      */     throws SQLException
/*      */   {
/*  376 */     byte[] arrayOfByte = null;
/*      */     try
/*      */     {
/*  380 */       if (this.attributes[this.index] != null)
/*      */       {
/*  382 */         if ((this.attributes[this.index] instanceof RAW)) {
/*  383 */           arrayOfByte = ((RAW)this.attributes[this.index]).shareBytes();
/*      */         }
/*      */         else
/*      */         {
/*  389 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  396 */       this.index += 1;
/*      */     }
/*      */ 
/*  401 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public Date readDate()
/*      */     throws SQLException
/*      */   {
/*  414 */     Date localDate = null;
/*      */     try
/*      */     {
/*  418 */       if (this.attributes[this.index] != null)
/*      */       {
/*  420 */         localDate = this.attributes[this.index].dateValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  425 */       this.index += 1;
/*      */     }
/*      */ 
/*  430 */     return localDate;
/*      */   }
/*      */ 
/*      */   public Time readTime()
/*      */     throws SQLException
/*      */   {
/*  443 */     Time localTime = null;
/*      */     try
/*      */     {
/*  447 */       if (this.attributes[this.index] != null)
/*      */       {
/*  449 */         localTime = this.attributes[this.index].timeValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  454 */       this.index += 1;
/*      */     }
/*      */ 
/*  459 */     return localTime;
/*      */   }
/*      */ 
/*      */   public Timestamp readTimestamp()
/*      */     throws SQLException
/*      */   {
/*  472 */     Timestamp localTimestamp = null;
/*      */     try
/*      */     {
/*  476 */       if (this.attributes[this.index] != null)
/*      */       {
/*  478 */         localTimestamp = this.attributes[this.index].timestampValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  483 */       this.index += 1;
/*      */     }
/*      */ 
/*  488 */     return localTimestamp;
/*      */   }
/*      */ 
/*      */   public Reader readCharacterStream()
/*      */     throws SQLException
/*      */   {
/*  501 */     Reader localReader = null;
/*      */     try
/*      */     {
/*  505 */       Datum localDatum = this.attributes[this.index];
/*      */ 
/*  507 */       if (localDatum != null)
/*      */       {
/*  509 */         localReader = localDatum.characterStreamValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  514 */       this.index += 1;
/*      */     }
/*      */ 
/*  519 */     return localReader;
/*      */   }
/*      */ 
/*      */   public InputStream readAsciiStream()
/*      */     throws SQLException
/*      */   {
/*  532 */     InputStream localInputStream = null;
/*      */     try
/*      */     {
/*  536 */       Datum localDatum = this.attributes[this.index];
/*      */ 
/*  538 */       if (localDatum != null)
/*      */       {
/*  540 */         localInputStream = localDatum.asciiStreamValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  545 */       this.index += 1;
/*      */     }
/*      */ 
/*  550 */     return localInputStream;
/*      */   }
/*      */ 
/*      */   public InputStream readBinaryStream()
/*      */     throws SQLException
/*      */   {
/*  564 */     InputStream localInputStream = null;
/*      */     try
/*      */     {
/*  568 */       Datum localDatum = this.attributes[this.index];
/*      */ 
/*  570 */       if (localDatum != null)
/*      */       {
/*  572 */         localInputStream = localDatum.binaryStreamValue();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  577 */       this.index += 1;
/*      */     }
/*      */ 
/*  582 */     return localInputStream;
/*      */   }
/*      */ 
/*      */   public Object readObject()
/*      */     throws SQLException
/*      */   {
/*  610 */     Datum localDatum = (Datum)readOracleObject();
/*      */ 
/*  612 */     if (localDatum != null)
/*      */     {
/*  614 */       if ((localDatum instanceof STRUCT)) {
/*  615 */         return ((STRUCT)localDatum).toJdbc(this.map);
/*      */       }
/*  617 */       localDatum.toJdbc();
/*      */     }
/*      */ 
/*  620 */     return null;
/*      */   }
/*      */ 
/*      */   public Ref readRef()
/*      */     throws SQLException
/*      */   {
/*  633 */     return readREF();
/*      */   }
/*      */ 
/*      */   public Blob readBlob()
/*      */     throws SQLException
/*      */   {
/*  646 */     return readBLOB();
/*      */   }
/*      */ 
/*      */   public Clob readClob()
/*      */     throws SQLException
/*      */   {
/*  659 */     return readCLOB();
/*      */   }
/*      */ 
/*      */   public Array readArray()
/*      */     throws SQLException
/*      */   {
/*  672 */     return readARRAY();
/*      */   }
/*      */ 
/*      */   public Struct readStruct()
/*      */     throws SQLException
/*      */   {
/*  685 */     return readSTRUCT();
/*      */   }
/*      */ 
/*      */   public boolean wasNull()
/*      */     throws SQLException
/*      */   {
/*  699 */     if (this.index == 0)
/*      */     {
/*  704 */       DatabaseError.throwSqlException(24);
/*      */     }
/*      */ 
/*  707 */     int i = this.attributes[(this.index - 1)] == null ? 1 : 0;
/*      */ 
/*  711 */     return i;
/*      */   }
/*      */ 
/*      */   public Object readOracleObject()
/*      */     throws SQLException
/*      */   {
/*  724 */     Datum localDatum = this.attributes[(this.index++)];
/*      */ 
/*  728 */     return localDatum;
/*      */   }
/*      */ 
/*      */   public NUMBER readNUMBER()
/*      */     throws SQLException
/*      */   {
/*  741 */     NUMBER localNUMBER = null;
/*      */     try
/*      */     {
/*  745 */       if (this.attributes[this.index] != null)
/*      */       {
/*  747 */         if ((this.attributes[this.index] instanceof NUMBER)) {
/*  748 */           localNUMBER = (NUMBER)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  754 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  761 */       this.index += 1;
/*      */     }
/*      */ 
/*  766 */     return localNUMBER;
/*      */   }
/*      */ 
/*      */   public CHAR readCHAR()
/*      */     throws SQLException
/*      */   {
/*  779 */     CHAR localCHAR = null;
/*      */     try
/*      */     {
/*  783 */       if (this.attributes[this.index] != null)
/*      */       {
/*  785 */         if ((this.attributes[this.index] instanceof CHAR)) {
/*  786 */           localCHAR = (CHAR)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  792 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  799 */       this.index += 1;
/*      */     }
/*      */ 
/*  804 */     return localCHAR;
/*      */   }
/*      */ 
/*      */   public DATE readDATE()
/*      */     throws SQLException
/*      */   {
/*  817 */     DATE localDATE = null;
/*      */     try
/*      */     {
/*  821 */       if (this.attributes[this.index] != null)
/*      */       {
/*  823 */         if ((this.attributes[this.index] instanceof DATE)) {
/*  824 */           localDATE = (DATE)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  830 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  837 */       this.index += 1;
/*      */     }
/*      */ 
/*  842 */     return localDATE;
/*      */   }
/*      */ 
/*      */   public BFILE readBFILE()
/*      */     throws SQLException
/*      */   {
/*  855 */     BFILE localBFILE = null;
/*      */     try
/*      */     {
/*  859 */       if (this.attributes[this.index] != null)
/*      */       {
/*  861 */         if ((this.attributes[this.index] instanceof BFILE)) {
/*  862 */           localBFILE = (BFILE)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  868 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  875 */       this.index += 1;
/*      */     }
/*      */ 
/*  880 */     return localBFILE;
/*      */   }
/*      */ 
/*      */   public BLOB readBLOB()
/*      */     throws SQLException
/*      */   {
/*  893 */     BLOB localBLOB = null;
/*      */     try
/*      */     {
/*  897 */       if (this.attributes[this.index] != null)
/*      */       {
/*  899 */         if ((this.attributes[this.index] instanceof BLOB)) {
/*  900 */           localBLOB = (BLOB)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  906 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  913 */       this.index += 1;
/*      */     }
/*      */ 
/*  918 */     return localBLOB;
/*      */   }
/*      */ 
/*      */   public CLOB readCLOB()
/*      */     throws SQLException
/*      */   {
/*  931 */     CLOB localCLOB = null;
/*      */     try
/*      */     {
/*  935 */       if (this.attributes[this.index] != null)
/*      */       {
/*  937 */         if ((this.attributes[this.index] instanceof CLOB)) {
/*  938 */           localCLOB = (CLOB)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  944 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  951 */       this.index += 1;
/*      */     }
/*      */ 
/*  957 */     return localCLOB;
/*      */   }
/*      */ 
/*      */   public RAW readRAW()
/*      */     throws SQLException
/*      */   {
/*  970 */     RAW localRAW = null;
/*      */     try
/*      */     {
/*  974 */       if (this.attributes[this.index] != null)
/*      */       {
/*  976 */         if ((this.attributes[this.index] instanceof RAW)) {
/*  977 */           localRAW = (RAW)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/*  983 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  990 */       this.index += 1;
/*      */     }
/*      */ 
/*  995 */     return localRAW;
/*      */   }
/*      */ 
/*      */   public REF readREF()
/*      */     throws SQLException
/*      */   {
/* 1008 */     REF localREF = null;
/*      */     try
/*      */     {
/* 1012 */       if (this.attributes[this.index] != null)
/*      */       {
/* 1014 */         if ((this.attributes[this.index] instanceof REF)) {
/* 1015 */           localREF = (REF)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/* 1021 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1028 */       this.index += 1;
/*      */     }
/*      */ 
/* 1033 */     return localREF;
/*      */   }
/*      */ 
/*      */   public ROWID readROWID()
/*      */     throws SQLException
/*      */   {
/* 1046 */     ROWID localROWID = null;
/*      */     try
/*      */     {
/* 1050 */       if (this.attributes[this.index] != null)
/*      */       {
/* 1052 */         if ((this.attributes[this.index] instanceof ROWID)) {
/* 1053 */           localROWID = (ROWID)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/* 1059 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1066 */       this.index += 1;
/*      */     }
/*      */ 
/* 1071 */     return localROWID;
/*      */   }
/*      */ 
/*      */   public ARRAY readARRAY()
/*      */     throws SQLException
/*      */   {
/* 1084 */     ARRAY localARRAY = null;
/*      */     try
/*      */     {
/* 1088 */       if (this.attributes[this.index] != null)
/*      */       {
/* 1090 */         if ((this.attributes[this.index] instanceof ARRAY)) {
/* 1091 */           localARRAY = (ARRAY)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/* 1097 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1104 */       this.index += 1;
/*      */     }
/*      */ 
/* 1109 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   public STRUCT readSTRUCT()
/*      */     throws SQLException
/*      */   {
/* 1122 */     STRUCT localSTRUCT = null;
/*      */     try
/*      */     {
/* 1126 */       if (this.attributes[this.index] != null)
/*      */       {
/* 1128 */         if ((this.attributes[this.index] instanceof STRUCT)) {
/* 1129 */           localSTRUCT = (STRUCT)this.attributes[this.index];
/*      */         }
/*      */         else
/*      */         {
/* 1135 */           DatabaseError.throwSqlException(4, null);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1142 */       this.index += 1;
/*      */     }
/*      */ 
/* 1147 */     return localSTRUCT;
/*      */   }
/*      */ 
/*      */   public URL readURL()
/*      */     throws SQLException
/*      */   {
/* 1166 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*      */ 
/* 1168 */     return null;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.OracleJdbc2SQLInput
 * JD-Core Version:    0.6.0
 */