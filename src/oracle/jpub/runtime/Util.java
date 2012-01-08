/*     */ package oracle.jpub.runtime;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.BFILE;
/*     */ import oracle.sql.BLOB;
/*     */ import oracle.sql.CHAR;
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.CharacterSet;
/*     */ import oracle.sql.CustomDatum;
/*     */ import oracle.sql.CustomDatumFactory;
/*     */ import oracle.sql.DATE;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.NUMBER;
/*     */ import oracle.sql.OPAQUE;
/*     */ import oracle.sql.ORAData;
/*     */ import oracle.sql.ORADataFactory;
/*     */ import oracle.sql.RAW;
/*     */ import oracle.sql.REF;
/*     */ import oracle.sql.STRUCT;
/*     */ import oracle.sql.TIMESTAMP;
/*     */ 
/*     */ public class Util
/*     */ {
/*  31 */   static short lastCsId = 870;
/*  32 */   static CharacterSet lastCS = CharacterSet.make(870);
/*     */ 
/* 330 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public static Object convertToObject(Datum paramDatum, int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/*  42 */     Object localObject = _convertToObject(paramDatum, paramInt, paramObject);
/*     */ 
/*  47 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static Object _convertToObject(Datum paramDatum, int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/*  53 */     if (paramDatum == null) {
/*  54 */       return null;
/*     */     }
/*  56 */     if ((paramDatum instanceof STRUCT))
/*     */     {
/*  58 */       if ((paramObject instanceof ORADataFactory))
/*     */       {
/*  60 */         return ((ORADataFactory)paramObject).create(paramDatum, 2002);
/*     */       }
/*     */ 
/*  64 */       return ((CustomDatumFactory)paramObject).create(paramDatum, 2002);
/*     */     }
/*     */ 
/*  68 */     if ((paramDatum instanceof REF))
/*     */     {
/*  70 */       if ((paramObject instanceof ORADataFactory))
/*     */       {
/*  72 */         return ((ORADataFactory)paramObject).create(paramDatum, 2006);
/*     */       }
/*     */ 
/*  76 */       return ((CustomDatumFactory)paramObject).create(paramDatum, 2006);
/*     */     }
/*     */ 
/*  80 */     if ((paramDatum instanceof ARRAY))
/*     */     {
/*  82 */       if ((paramObject instanceof ORADataFactory))
/*     */       {
/*  84 */         return ((ORADataFactory)paramObject).create(paramDatum, 2003);
/*     */       }
/*     */ 
/*  88 */       return ((CustomDatumFactory)paramObject).create(paramDatum, 2003);
/*     */     }
/*     */ 
/*  92 */     if ((paramDatum instanceof OPAQUE))
/*     */     {
/*  94 */       if ((paramObject instanceof ORADataFactory))
/*     */       {
/*  96 */         return ((ORADataFactory)paramObject).create(paramDatum, 2007);
/*     */       }
/*     */ 
/* 100 */       return ((CustomDatumFactory)paramObject).create(paramDatum, 2007);
/*     */     }
/*     */ 
/* 104 */     if (paramObject != null)
/*     */     {
/* 106 */       if ((paramObject instanceof ORADataFactory))
/*     */       {
/* 108 */         return ((ORADataFactory)paramObject).create(paramDatum, paramInt);
/*     */       }
/*     */ 
/* 112 */       return ((CustomDatumFactory)paramObject).create(paramDatum, paramInt);
/*     */     }
/*     */ 
/* 116 */     if ((paramDatum instanceof NUMBER))
/*     */     {
/* 118 */       if ((paramInt == 2) || (paramInt == 3)) {
/* 119 */         return ((NUMBER)paramDatum).bigDecimalValue();
/*     */       }
/* 121 */       if ((paramInt == 8) || (paramInt == 6)) {
/* 122 */         return new Double(((NUMBER)paramDatum).doubleValue());
/*     */       }
/* 124 */       if ((paramInt == 4) || (paramInt == 5)) {
/* 125 */         return new Integer(((NUMBER)paramDatum).intValue());
/*     */       }
/* 127 */       if (paramInt == 7) {
/* 128 */         return new Float(((NUMBER)paramDatum).floatValue());
/*     */       }
/* 130 */       if (paramInt == 16) {
/* 131 */         return new Boolean(((NUMBER)paramDatum).booleanValue());
/*     */       }
/* 133 */       throw new SQLException("Unexpected java.sql.OracleTypes type: " + paramInt);
/*     */     }
/*     */ 
/* 137 */     return paramDatum.toJdbc();
/*     */   }
/*     */ 
/*     */   public static Datum convertToOracle(Object paramObject, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 146 */     Datum localDatum = _convertToOracle(paramObject, paramConnection);
/*     */ 
/* 150 */     return localDatum;
/*     */   }
/*     */ 
/*     */   private static Datum _convertToOracle(Object paramObject, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 156 */     if (paramObject == null) {
/* 157 */       return null;
/*     */     }
/* 159 */     if ((paramObject instanceof ORAData)) {
/* 160 */       return ((ORAData)paramObject).toDatum(paramConnection);
/*     */     }
/* 162 */     if ((paramObject instanceof CustomDatum))
/* 163 */       return ((CustomDatum)paramObject).toDatum((oracle.jdbc.driver.OracleConnection)paramConnection);
/*     */     short s;
/* 165 */     if ((paramObject instanceof String))
/*     */     {
/* 167 */       s = (paramConnection == null) || (!(paramConnection instanceof oracle.jdbc.driver.OracleConnection)) ? 870 : ((oracle.jdbc.driver.OracleConnection)paramConnection).getDbCsId();
/*     */ 
/* 172 */       if (s != lastCsId)
/*     */       {
/* 174 */         lastCsId = s;
/* 175 */         lastCS = CharacterSet.make(lastCsId);
/*     */       }
/*     */ 
/* 178 */       return new CHAR((String)paramObject, lastCS);
/*     */     }
/*     */ 
/* 181 */     if ((paramObject instanceof Character))
/*     */     {
/* 183 */       s = (paramConnection == null) || (!(paramConnection instanceof oracle.jdbc.driver.OracleConnection)) ? 870 : ((oracle.jdbc.driver.OracleConnection)paramConnection).getDbCsId();
/*     */ 
/* 188 */       if (s != lastCsId)
/*     */       {
/* 190 */         lastCsId = s;
/* 191 */         lastCS = CharacterSet.make(lastCsId);
/*     */       }
/*     */ 
/* 194 */       return new CHAR(((Character)paramObject).toString(), lastCS);
/*     */     }
/*     */ 
/* 198 */     if ((paramObject instanceof BigDecimal)) {
/* 199 */       return new NUMBER((BigDecimal)paramObject);
/*     */     }
/* 201 */     if ((paramObject instanceof BigInteger)) {
/* 202 */       return new NUMBER((BigInteger)paramObject);
/*     */     }
/* 204 */     if ((paramObject instanceof Double)) {
/* 205 */       return new NUMBER(((Double)paramObject).doubleValue());
/*     */     }
/* 207 */     if ((paramObject instanceof Float)) {
/* 208 */       return new NUMBER(((Float)paramObject).floatValue());
/*     */     }
/* 210 */     if ((paramObject instanceof Integer)) {
/* 211 */       return new NUMBER(((Integer)paramObject).intValue());
/*     */     }
/* 213 */     if ((paramObject instanceof Boolean)) {
/* 214 */       return new NUMBER(((Boolean)paramObject).booleanValue());
/*     */     }
/* 216 */     if ((paramObject instanceof Short)) {
/* 217 */       return new NUMBER(((Short)paramObject).shortValue());
/*     */     }
/* 219 */     if ((paramObject instanceof Byte)) {
/* 220 */       return new NUMBER(((Byte)paramObject).byteValue());
/*     */     }
/* 222 */     if ((paramObject instanceof Long)) {
/* 223 */       return new NUMBER(((Long)paramObject).longValue());
/*     */     }
/* 225 */     if ((paramObject instanceof Timestamp))
/*     */     {
/* 227 */       if (((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin().isV8Compatible()) {
/* 228 */         return new DATE((Timestamp)paramObject);
/*     */       }
/* 230 */       return new TIMESTAMP((Timestamp)paramObject);
/*     */     }
/*     */ 
/* 233 */     if ((paramObject instanceof java.sql.Date)) {
/* 234 */       return new DATE((java.sql.Date)paramObject);
/*     */     }
/* 236 */     if ((paramObject instanceof java.util.Date)) {
/* 237 */       return new DATE(new java.sql.Date(((java.util.Date)paramObject).getTime()));
/*     */     }
/* 239 */     if ((paramObject instanceof byte[])) {
/* 240 */       return new RAW((byte[])paramObject);
/*     */     }
/* 242 */     if ((paramObject instanceof Datum)) {
/* 243 */       return (Datum)paramObject;
/*     */     }
/* 245 */     throw new SQLException("Unable to convert object to oracle.sql.Datum: " + paramObject);
/*     */   }
/*     */ 
/*     */   static boolean isMutable(Datum paramDatum, ORADataFactory paramORADataFactory)
/*     */   {
/* 251 */     if (paramDatum == null) {
/* 252 */       return false;
/*     */     }
/*     */ 
/* 268 */     return ((paramDatum instanceof BFILE)) || ((paramDatum instanceof BLOB)) || ((paramDatum instanceof CLOB)) || ((paramORADataFactory != null) && (((paramDatum instanceof STRUCT)) || ((paramDatum instanceof OPAQUE)) || ((paramDatum instanceof ARRAY))));
/*     */   }
/*     */ 
/*     */   static boolean isMutable(Datum paramDatum, CustomDatumFactory paramCustomDatumFactory)
/*     */   {
/* 279 */     if (paramDatum == null) {
/* 280 */       return false;
/*     */     }
/*     */ 
/* 296 */     return ((paramDatum instanceof BFILE)) || ((paramDatum instanceof BLOB)) || ((paramDatum instanceof CLOB)) || ((paramCustomDatumFactory != null) && (((paramDatum instanceof STRUCT)) || ((paramDatum instanceof OPAQUE)) || ((paramDatum instanceof ARRAY))));
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jpub.runtime.Util
 * JD-Core Version:    0.6.0
 */