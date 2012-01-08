/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.oracore.OracleType;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.jdbc.oracore.OracleTypeCHAR;
/*     */ import oracle.jdbc.oracore.OracleTypeFLOAT;
/*     */ import oracle.jdbc.oracore.OracleTypeNUMBER;
/*     */ import oracle.jdbc.oracore.OracleTypeRAW;
/*     */ import oracle.jdbc.oracore.OracleTypeREF;
/*     */ import oracle.sql.StructDescriptor;
/*     */ 
/*     */ public class StructMetaData
/*     */   implements oracle.jdbc.internal.StructMetaData
/*     */ {
/*     */   StructDescriptor descriptor;
/*     */   OracleTypeADT otype;
/*     */   OracleType[] types;
/* 635 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*     */ 
/*     */   public StructMetaData(StructDescriptor paramStructDescriptor)
/*     */     throws SQLException
/*     */   {
/*  46 */     if (paramStructDescriptor == null) {
/*  47 */       DatabaseError.throwSqlException(1, "illegal operation: descriptor is null");
/*     */     }
/*     */ 
/*  50 */     this.descriptor = paramStructDescriptor;
/*  51 */     this.otype = paramStructDescriptor.getOracleTypeADT();
/*  52 */     this.types = this.otype.getAttrTypes();
/*     */   }
/*     */ 
/*     */   public int getColumnCount()
/*     */     throws SQLException
/*     */   {
/*  66 */     return this.types.length;
/*     */   }
/*     */ 
/*     */   public boolean isAutoIncrement(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isSearchable(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isCurrency(int paramInt)
/*     */     throws SQLException
/*     */   {
/*  95 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/*  97 */     return ((this.types[i] instanceof OracleTypeNUMBER)) || ((this.types[i] instanceof OracleTypeFLOAT));
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 108 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 110 */     return this.types[i] instanceof OracleTypeCHAR;
/*     */   }
/*     */ 
/*     */   public int isNullable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 119 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isSigned(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   public int getColumnDisplaySize(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 141 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 143 */     if ((this.types[i] instanceof OracleTypeCHAR))
/* 144 */       return ((OracleTypeCHAR)this.types[i]).getLength();
/* 145 */     if ((this.types[i] instanceof OracleTypeRAW)) {
/* 146 */       return ((OracleTypeRAW)this.types[i]).getLength();
/*     */     }
/* 148 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getColumnLabel(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 157 */     return getColumnName(paramInt);
/*     */   }
/*     */ 
/*     */   public String getColumnName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 173 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 175 */     return this.otype.getAttributeName(paramInt);
/*     */   }
/*     */ 
/*     */   public String getSchemaName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 191 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 193 */     if ((this.types[i] instanceof OracleTypeADT)) {
/* 194 */       return ((OracleTypeADT)this.types[i]).getSchemaName();
/*     */     }
/* 196 */     return "";
/*     */   }
/*     */ 
/*     */   public int getPrecision(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 205 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 208 */     return this.types[i].getPrecision();
/*     */   }
/*     */ 
/*     */   public int getScale(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 217 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 220 */     return this.types[i].getScale();
/*     */   }
/*     */ 
/*     */   public String getTableName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCatalogName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */   public int getColumnType(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 247 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 249 */     return this.types[i].getTypeCode();
/*     */   }
/*     */ 
/*     */   public String getColumnTypeName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 266 */     int i = getColumnType(paramInt);
/* 267 */     int j = getValidColumnIndex(paramInt);
/*     */ 
/* 272 */     switch (i)
/*     */     {
/*     */     case 12:
/* 276 */       return "VARCHAR";
/*     */     case 1:
/* 279 */       return "CHAR";
/*     */     case -2:
/* 282 */       return "RAW";
/*     */     case 6:
/* 285 */       return "FLOAT";
/*     */     case 2:
/* 288 */       return "NUMBER";
/*     */     case 8:
/* 291 */       return "DOUBLE";
/*     */     case 3:
/* 294 */       return "DECIMAL";
/*     */     case 100:
/* 297 */       return "BINARY_FLOAT";
/*     */     case 101:
/* 300 */       return "BINARY_DOUBLE";
/*     */     case 91:
/* 303 */       return "DATE";
/*     */     case -104:
/* 306 */       return "INTERVALDS";
/*     */     case -103:
/* 309 */       return "INTERVALYM";
/*     */     case 93:
/* 312 */       return "TIMESTAMP";
/*     */     case -101:
/* 315 */       return "TIMESTAMPTZ";
/*     */     case -102:
/* 318 */       return "TIMESTAMPLTZ";
/*     */     case 2004:
/* 321 */       return "BLOB";
/*     */     case 2005:
/* 324 */       return "CLOB";
/*     */     case -13:
/* 327 */       return "BFILE";
/*     */     case 2002:
/*     */     case 2003:
/*     */     case 2007:
/*     */     case 2008:
/* 336 */       return ((OracleTypeADT)this.types[j]).getFullName();
/*     */     case 2006:
/* 339 */       return "REF " + ((OracleTypeREF)this.types[j]).getFullName();
/*     */     case 1111:
/*     */     }
/*     */ 
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isWritable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDefinitelyWritable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */   public String getColumnClassName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 381 */     int i = getColumnType(paramInt);
/*     */ 
/* 386 */     switch (i)
/*     */     {
/*     */     case 1:
/*     */     case 12:
/* 392 */       return "java.lang.String";
/*     */     case -2:
/* 395 */       return "byte[]";
/*     */     case 2:
/*     */     case 3:
/*     */     case 6:
/*     */     case 8:
/* 404 */       return "java.math.BigDecimal";
/*     */     case 91:
/* 407 */       return "java.sql.Timestamp";
/*     */     case -103:
/* 410 */       return "oracle.sql.INTERVALYM";
/*     */     case -104:
/* 413 */       return "oracle.sql.INTERVALDS";
/*     */     case 93:
/* 416 */       return "oracle.sql.TIMESTAMP";
/*     */     case -101:
/* 419 */       return "oracle.sql.TIMESTAMPTZ";
/*     */     case -102:
/* 422 */       return "oracle.sql.TIMESTAMPLTZ";
/*     */     case 2004:
/* 425 */       return "oracle.sql.BLOB";
/*     */     case 2005:
/* 428 */       return "oracle.sql.CLOB";
/*     */     case -13:
/* 431 */       return "oracle.sql.BFILE";
/*     */     case 2002:
/*     */     case 2008:
/* 436 */       return "oracle.sql.STRUCT";
/*     */     case 2007:
/* 439 */       return "oracle.sql.OPAQUE";
/*     */     case 2003:
/* 442 */       return "oracle.sql.ARRAY";
/*     */     case 2006:
/* 445 */       return "oracle.sql.REF";
/*     */     case 1111:
/*     */     }
/*     */ 
/* 450 */     return null;
/*     */   }
/*     */ 
/*     */   public String getOracleColumnClassName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 465 */     int i = getColumnType(paramInt);
/*     */ 
/* 470 */     switch (i)
/*     */     {
/*     */     case 1:
/*     */     case 12:
/* 476 */       return "CHAR";
/*     */     case -2:
/* 479 */       return "RAW";
/*     */     case 2:
/*     */     case 3:
/*     */     case 6:
/*     */     case 8:
/* 488 */       return "NUMBER";
/*     */     case 91:
/* 491 */       return "DATE";
/*     */     case -103:
/* 494 */       return "INTERVALYM";
/*     */     case -104:
/* 497 */       return "INTERVALDS";
/*     */     case 93:
/* 500 */       return "TIMESTAMP";
/*     */     case -101:
/* 503 */       return "TIMESTAMPTZ";
/*     */     case -102:
/* 506 */       return "TIMESTAMPLTZ";
/*     */     case 2004:
/* 509 */       return "BLOB";
/*     */     case 2005:
/* 512 */       return "CLOB";
/*     */     case -13:
/* 515 */       return "BFILE";
/*     */     case 2002:
/* 518 */       return "STRUCT";
/*     */     case 2008:
/* 521 */       return "JAVA_STRUCT";
/*     */     case 2007:
/* 524 */       return "OPAQUE";
/*     */     case 2003:
/* 527 */       return "ARRAY";
/*     */     case 2006:
/* 530 */       return "REF";
/*     */     case 1111:
/*     */     }
/*     */ 
/* 535 */     return null;
/*     */   }
/*     */ 
/*     */   public int getLocalColumnCount()
/*     */     throws SQLException
/*     */   {
/* 551 */     return this.descriptor.getLocalAttributeCount();
/*     */   }
/*     */ 
/*     */   public boolean isInherited(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 568 */     return paramInt <= getColumnCount() - getLocalColumnCount();
/*     */   }
/*     */ 
/*     */   public String getAttributeJavaName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 586 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 591 */     return this.descriptor.getAttributeJavaName(i);
/*     */   }
/*     */ 
/*     */   private int getValidColumnIndex(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 609 */     int i = paramInt - 1;
/*     */ 
/* 611 */     if ((i < 0) || (i >= this.types.length)) {
/* 612 */       DatabaseError.throwSqlException(3, "getValidColumnIndex");
/*     */     }
/*     */ 
/* 618 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isNCHAR(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 627 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 629 */     return this.types[i].isNCHAR();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.StructMetaData
 * JD-Core Version:    0.6.0
 */