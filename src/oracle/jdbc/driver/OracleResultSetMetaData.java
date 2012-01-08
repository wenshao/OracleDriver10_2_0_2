/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.oracore.OracleNamedType;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.sql.StructDescriptor;
/*     */ import oracle.sql.TypeDescriptor;
/*     */ 
/*     */ public class OracleResultSetMetaData
/*     */   implements oracle.jdbc.internal.OracleResultSetMetaData
/*     */ {
/*     */   PhysicalConnection connection;
/*     */   OracleStatement statement;
/*     */   int m_beginColumnIndex;
/* 756 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   public OracleResultSetMetaData()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleResultSetMetaData(PhysicalConnection paramPhysicalConnection, OracleStatement paramOracleStatement)
/*     */     throws SQLException
/*     */   {
/*  54 */     this.connection = paramPhysicalConnection;
/*  55 */     this.statement = paramOracleStatement;
/*     */ 
/*  57 */     paramOracleStatement.describe();
/*     */ 
/*  59 */     this.m_beginColumnIndex = 0;
/*     */   }
/*     */ 
/*     */   OracleResultSetMetaData(PhysicalConnection paramPhysicalConnection, OracleStatement paramOracleStatement, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  74 */     this.connection = paramPhysicalConnection;
/*  75 */     this.statement = paramOracleStatement;
/*     */ 
/*  77 */     paramOracleStatement.describe();
/*     */ 
/*  79 */     this.m_beginColumnIndex = paramInt;
/*     */   }
/*     */ 
/*     */   public OracleResultSetMetaData(OracleResultSet paramOracleResultSet)
/*     */     throws SQLException
/*     */   {
/*  93 */     this.statement = ((OracleStatement)paramOracleResultSet.getStatement());
/*  94 */     this.connection = ((PhysicalConnection)this.statement.getConnection());
/*     */ 
/*  96 */     this.statement.describe();
/*     */ 
/*  98 */     this.m_beginColumnIndex = paramOracleResultSet.getFirstUserColumnIndex();
/*     */   }
/*     */ 
/*     */   public int getColumnCount()
/*     */     throws SQLException
/*     */   {
/* 115 */     return this.statement.getNumberOfColumns() - this.m_beginColumnIndex;
/*     */   }
/*     */ 
/*     */   public boolean isAutoIncrement(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   int getValidColumnIndex(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 140 */     int i = paramInt + this.m_beginColumnIndex - 1;
/*     */ 
/* 142 */     if ((i < 0) || (i >= this.statement.getNumberOfColumns())) {
/* 143 */       DatabaseError.throwSqlException(3, "getValidColumnIndex");
/*     */     }
/*     */ 
/* 146 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean isCaseSensitive(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 157 */     int i = getColumnType(paramInt);
/*     */ 
/* 159 */     return (i == 1) || (i == 12) || (i == -1);
/*     */   }
/*     */ 
/*     */   public boolean isSearchable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 171 */     int i = getColumnType(paramInt);
/*     */ 
/* 174 */     return (i != -4) && (i != -1) && (i != 2004) && (i != 2005) && (i != -13) && (i != 2002) && (i != 2008) && (i != 2007) && (i != 2003) && (i != 2006) && (i != -10);
/*     */   }
/*     */ 
/*     */   public boolean isCurrency(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 191 */     int i = getColumnType(paramInt);
/*     */ 
/* 193 */     return (i == 2) || (i == 6);
/*     */   }
/*     */ 
/*     */   public int isNullable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 204 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 206 */     return getDescription()[i].nullable ? 1 : 0;
/*     */   }
/*     */ 
/*     */   public boolean isSigned(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   public int getColumnDisplaySize(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 229 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 231 */     return getDescription()[i].describeMaxLength;
/*     */   }
/*     */ 
/*     */   public String getColumnLabel(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 243 */     return getColumnName(paramInt);
/*     */   }
/*     */ 
/*     */   public String getColumnName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 254 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 256 */     return this.statement.getDescriptionWithNames()[i].columnName;
/*     */   }
/*     */ 
/*     */   public String getSchemaName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 268 */     return "";
/*     */   }
/*     */ 
/*     */   public int getPrecision(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 279 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 282 */     int j = getDescription()[i].describeType;
/*     */ 
/* 284 */     switch (j)
/*     */     {
/*     */     case 112:
/*     */     case 113:
/* 288 */       return -1;
/*     */     case 8:
/*     */     case 24:
/* 292 */       return 2147483647;
/*     */     case 1:
/*     */     case 96:
/* 296 */       return getDescription()[i].describeMaxLength;
/*     */     }
/*     */ 
/* 299 */     return getDescription()[i].precision;
/*     */   }
/*     */ 
/*     */   public int getScale(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 311 */     int i = getValidColumnIndex(paramInt);
/* 312 */     int j = getDescription()[i].scale;
/*     */ 
/* 314 */     return (j == -127) && (this.statement.connection.j2ee13Compliant) ? 0 : j;
/*     */   }
/*     */ 
/*     */   public String getTableName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 326 */     return "";
/*     */   }
/*     */ 
/*     */   public String getCatalogName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 338 */     return "";
/*     */   }
/*     */ 
/*     */   public int getColumnType(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 354 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 357 */     int j = getDescription()[i].describeType;
/*     */ 
/* 362 */     switch (j)
/*     */     {
/*     */     case 96:
/* 365 */       return 1;
/*     */     case 1:
/* 368 */       return 12;
/*     */     case 8:
/* 371 */       return -1;
/*     */     case 2:
/*     */     case 6:
/* 375 */       if ((this.statement.connection.j2ee13Compliant) && (getDescription()[i].precision != 0) && (getDescription()[i].scale == -127))
/*     */       {
/* 378 */         return 6;
/*     */       }
/* 380 */       return 2;
/*     */     case 100:
/* 383 */       return 100;
/*     */     case 101:
/* 386 */       return 101;
/*     */     case 23:
/* 389 */       return -3;
/*     */     case 24:
/* 392 */       return -4;
/*     */     case 104:
/* 395 */       return -8;
/*     */     case 102:
/* 398 */       return -10;
/*     */     case 12:
/* 402 */       return this.connection.v8Compatible ? 93 : 91;
/*     */     case 180:
/* 406 */       return 93;
/*     */     case 181:
/* 409 */       return -101;
/*     */     case 231:
/* 412 */       return -102;
/*     */     case 113:
/* 415 */       return 2004;
/*     */     case 112:
/* 418 */       return 2005;
/*     */     case 114:
/* 421 */       return -13;
/*     */     case 109:
/* 425 */       OracleNamedType localOracleNamedType = (OracleNamedType)getDescription()[i].describeOtype;
/*     */ 
/* 428 */       TypeDescriptor localTypeDescriptor = TypeDescriptor.getTypeDescriptor(localOracleNamedType.getFullName(), this.connection);
/*     */ 
/* 432 */       if (localTypeDescriptor != null) {
/* 433 */         return localTypeDescriptor.getTypeCode();
/*     */       }
/* 435 */       DatabaseError.throwSqlException(60);
/*     */ 
/* 437 */       return -1;
/*     */     case 111:
/* 441 */       return 2006;
/*     */     case 182:
/* 444 */       return -103;
/*     */     case 183:
/* 447 */       return -104;
/*     */     }
/*     */ 
/* 450 */     return 1111;
/*     */   }
/*     */ 
/*     */   public String getColumnTypeName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 462 */     int i = getColumnType(paramInt);
/*     */     int j;
/*     */     OracleTypeADT localOracleTypeADT;
/* 464 */     switch (i)
/*     */     {
/*     */     case 1:
/* 467 */       return "CHAR";
/*     */     case 12:
/* 470 */       return "VARCHAR2";
/*     */     case -1:
/* 473 */       return "LONG";
/*     */     case -3:
/* 476 */       return "RAW";
/*     */     case -4:
/* 479 */       return "LONG RAW";
/*     */     case 2:
/* 482 */       return "NUMBER";
/*     */     case 6:
/* 485 */       return "FLOAT";
/*     */     case 100:
/* 488 */       return "BINARY_FLOAT";
/*     */     case 101:
/* 491 */       return "BINARY_DOUBLE";
/*     */     case 91:
/* 495 */       return "DATE";
/*     */     case 93:
/* 498 */       return this.connection.v8Compatible ? "DATE" : "TIMESTAMP";
/*     */     case -101:
/* 501 */       return "TIMESTAMPTZ";
/*     */     case -102:
/* 504 */       return "TIMESTAMPLTZ";
/*     */     case -8:
/* 507 */       return "ROWID";
/*     */     case -10:
/* 510 */       return "REFCURSOR";
/*     */     case 2004:
/* 513 */       return "BLOB";
/*     */     case 2005:
/* 516 */       return "CLOB";
/*     */     case -13:
/* 519 */       return "BFILE";
/*     */     case -103:
/* 522 */       return "INTERVALYM";
/*     */     case -104:
/* 525 */       return "INTERVALDS";
/*     */     case 2002:
/*     */     case 2003:
/*     */     case 2007:
/*     */     case 2008:
/* 534 */       j = getValidColumnIndex(paramInt);
/* 535 */       localOracleTypeADT = (OracleTypeADT)getDescription()[j].describeOtype;
/*     */ 
/* 538 */       return localOracleTypeADT.getFullName();
/*     */     case 2006:
/* 545 */       j = getValidColumnIndex(paramInt);
/* 546 */       localOracleTypeADT = (OracleTypeADT)getDescription()[j].describeOtype;
/*     */ 
/* 549 */       return localOracleTypeADT.getFullName();
/*     */     case 1111:
/*     */     }
/*     */ 
/* 555 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 568 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isWritable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 580 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isDefinitelyWritable(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 592 */     return false;
/*     */   }
/*     */ 
/*     */   public String getColumnClassName(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 612 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 615 */     int j = getDescription()[i].describeType;
/*     */ 
/* 617 */     switch (j)
/*     */     {
/*     */     case 1:
/*     */     case 8:
/*     */     case 96:
/*     */     case 999:
/* 623 */       return "java.lang.String";
/*     */     case 2:
/*     */     case 6:
/* 627 */       if ((getDescription()[i].precision != 0) && (getDescription()[i].scale == -127))
/*     */       {
/* 629 */         return "java.lang.Double";
/*     */       }
/* 631 */       return "java.math.BigDecimal";
/*     */     case 23:
/*     */     case 24:
/* 635 */       return "byte[]";
/*     */     case 12:
/* 638 */       return "java.sql.Timestamp";
/*     */     case 180:
/* 641 */       return "oracle.sql.TIMESTAMP";
/*     */     case 181:
/* 644 */       return "oracle.sql.TIMESTAMPTZ";
/*     */     case 231:
/* 647 */       return "oracle.sql.TIMESTAMPLTZ";
/*     */     case 182:
/* 650 */       return "oracle.sql.INTERVALYM";
/*     */     case 183:
/* 653 */       return "oracle.sql.INTERVALDS";
/*     */     case 104:
/* 656 */       return "oracle.sql.ROWID";
/*     */     case 113:
/* 659 */       return "oracle.sql.BLOB";
/*     */     case 112:
/* 662 */       return "oracle.sql.CLOB";
/*     */     case 114:
/* 665 */       return "oracle.sql.BFILE";
/*     */     case 102:
/* 668 */       return "OracleResultSet";
/*     */     case 109:
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 672 */       switch (getColumnType(paramInt))
/*     */       {
/*     */       case 2003:
/* 675 */         return "oracle.sql.ARRAY";
/*     */       case 2007:
/* 678 */         return "oracle.sql.OPAQUE";
/*     */       case 2008:
/* 682 */         localObject1 = (OracleNamedType)getDescription()[i].describeOtype;
/*     */ 
/* 685 */         localObject2 = this.connection.getJavaObjectTypeMap();
/*     */ 
/* 687 */         if (localObject2 != null)
/*     */         {
/* 689 */           Class localClass = (Class)((Map)localObject2).get(((OracleNamedType)localObject1).getFullName());
/*     */ 
/* 691 */           if (localClass != null) {
/* 692 */             return localClass.getName();
/*     */           }
/*     */         }
/* 695 */         return StructDescriptor.getJavaObjectClassName(this.connection, ((OracleNamedType)localObject1).getSchemaName(), ((OracleNamedType)localObject1).getSimpleName());
/*     */       case 2002:
/* 701 */         localObject1 = this.connection.getTypeMap();
/*     */ 
/* 703 */         if (localObject1 != null)
/*     */         {
/* 705 */           localObject2 = (Class)((Map)localObject1).get(((OracleNamedType)getDescription()[i].describeOtype).getFullName());
/*     */ 
/* 708 */           if (localObject2 != null) {
/* 709 */             return ((Class)localObject2).getName();
/*     */           }
/*     */         }
/* 712 */         return "oracle.sql.STRUCT";
/*     */       case 2004:
/*     */       case 2005:
/*     */       case 2006:
/* 716 */       }DatabaseError.throwSqlException(1);
/*     */     case 111:
/* 721 */       return "oracle.sql.REF";
/*     */     }
/*     */ 
/* 724 */     return (String)(String)null;
/*     */   }
/*     */ 
/*     */   public boolean isNCHAR(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 741 */     int i = getValidColumnIndex(paramInt);
/*     */ 
/* 743 */     return getDescription()[i].formOfUse == 2;
/*     */   }
/*     */ 
/*     */   Accessor[] getDescription()
/*     */     throws SQLException
/*     */   {
/* 751 */     return this.statement.getDescription();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleResultSetMetaData
 * JD-Core Version:    0.6.0
 */