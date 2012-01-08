/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.oracore.OracleNamedType;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ import oracle.jdbc.oracore.OracleTypeCOLLECTION;
/*     */ import oracle.jdbc.oracore.OracleTypeOPAQUE;
/*     */ 
/*     */ public abstract class TypeDescriptor
/*     */   implements Serializable
/*     */ {
/*  75 */   public static boolean DEBUG_SERIALIZATION = false;
/*     */   static final long serialVersionUID = 2022598722047823723L;
/*     */   SQLName sqlName;
/*     */   OracleNamedType pickler;
/*     */   transient oracle.jdbc.internal.OracleConnection connection;
/* 677 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   protected TypeDescriptor()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected TypeDescriptor(String paramString, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/*  96 */     if ((paramString == null) || (paramConnection == null))
/*     */     {
/* 101 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*     */     }
/*     */ 
/* 105 */     setPhysicalConnectionOf(paramConnection);
/*     */ 
/* 107 */     this.sqlName = new SQLName(paramString, getInternalConnection());
/*     */   }
/*     */ 
/*     */   protected TypeDescriptor(SQLName paramSQLName, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 118 */     if ((paramSQLName == null) || (paramConnection == null))
/*     */     {
/* 123 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*     */     }
/*     */ 
/* 127 */     this.sqlName = paramSQLName;
/*     */ 
/* 129 */     setPhysicalConnectionOf(paramConnection);
/*     */   }
/*     */ 
/*     */   protected TypeDescriptor(SQLName paramSQLName, OracleTypeADT paramOracleTypeADT, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 142 */     if ((paramSQLName == null) || (paramOracleTypeADT == null) || (paramConnection == null))
/*     */     {
/* 147 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*     */     }
/*     */ 
/* 151 */     this.sqlName = paramSQLName;
/*     */ 
/* 153 */     setPhysicalConnectionOf(paramConnection);
/*     */ 
/* 155 */     this.pickler = paramOracleTypeADT;
/*     */ 
/* 157 */     this.pickler.setDescriptor(this);
/*     */   }
/*     */ 
/*     */   protected TypeDescriptor(OracleTypeADT paramOracleTypeADT, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 169 */     if ((paramOracleTypeADT == null) || (paramConnection == null))
/*     */     {
/* 174 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*     */     }
/*     */ 
/* 178 */     setPhysicalConnectionOf(paramConnection);
/*     */ 
/* 180 */     this.sqlName = null;
/* 181 */     this.pickler = paramOracleTypeADT;
/*     */ 
/* 183 */     this.pickler.setDescriptor(this);
/*     */   }
/*     */ 
/*     */   public synchronized String getName()
/*     */     throws SQLException
/*     */   {
/* 197 */     if (this.sqlName == null) {
/* 198 */       initSQLName();
/*     */     }
/* 200 */     String str = this.sqlName.getName();
/*     */ 
/* 203 */     return str;
/*     */   }
/*     */ 
/*     */   public synchronized SQLName getSQLName()
/*     */     throws SQLException
/*     */   {
/* 215 */     if (this.sqlName == null) {
/* 216 */       initSQLName();
/*     */     }
/*     */ 
/* 221 */     return this.sqlName;
/*     */   }
/*     */ 
/*     */   void initSQLName()
/*     */     throws SQLException
/*     */   {
/* 229 */     if ((this.pickler == null) || (getInternalConnection() == null))
/*     */     {
/* 234 */       DatabaseError.throwSqlException(1);
/*     */     }
/*     */ 
/* 237 */     this.sqlName = new SQLName(this.pickler.getFullName(), getInternalConnection());
/*     */   }
/*     */ 
/*     */   public String getSchemaName()
/*     */     throws SQLException
/*     */   {
/* 252 */     String str = getSQLName().getSchema();
/*     */ 
/* 256 */     return str;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */     throws SQLException
/*     */   {
/* 268 */     String str = getSQLName().getSimpleName();
/*     */ 
/* 272 */     return str;
/*     */   }
/*     */ 
/*     */   public OracleNamedType getPickler()
/*     */   {
/* 284 */     return this.pickler;
/*     */   }
/*     */ 
/*     */   public oracle.jdbc.internal.OracleConnection getInternalConnection()
/*     */   {
/* 298 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public void setPhysicalConnectionOf(Connection paramConnection)
/*     */   {
/* 309 */     this.connection = ((oracle.jdbc.OracleConnection)paramConnection).physicalConnectionWithin();
/*     */   }
/*     */ 
/*     */   public abstract int getTypeCode()
/*     */     throws SQLException;
/*     */ 
/*     */   public static TypeDescriptor getTypeDescriptor(String paramString, oracle.jdbc.OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 342 */     Object localObject = null;
/*     */     try
/*     */     {
/* 348 */       SQLName localSQLName = new SQLName(paramString, paramOracleConnection);
/* 349 */       String str = localSQLName.getName();
/*     */ 
/* 352 */       localObject = (TypeDescriptor)paramOracleConnection.getDescriptor(str);
/*     */ 
/* 354 */       if (localObject == null)
/*     */       {
/* 358 */         OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
/* 359 */         oracle.jdbc.internal.OracleConnection localOracleConnection = (oracle.jdbc.internal.OracleConnection)paramOracleConnection;
/*     */ 
/* 362 */         localOracleTypeADT.init(localOracleConnection);
/*     */ 
/* 364 */         OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();
/*     */ 
/* 367 */         switch (localOracleNamedType.getTypeCode())
/*     */         {
/*     */         case 2002:
/*     */         case 2008:
/* 373 */           localObject = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 376 */           break;
/*     */         case 2003:
/* 379 */           localObject = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 383 */           break;
/*     */         case 2007:
/* 386 */           localObject = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 389 */           break;
/*     */         case 2004:
/*     */         case 2005:
/*     */         case 2006:
/*     */         default:
/* 395 */           DatabaseError.throwSqlException(1);
/*     */         }
/*     */ 
/* 399 */         paramOracleConnection.putDescriptor(str, localObject);
/*     */ 
/* 402 */         localOracleNamedType.setDescriptor((TypeDescriptor)localObject);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 410 */       if ((localException instanceof SQLException)) {
/* 411 */         DatabaseError.throwSqlException((SQLException)localException, 60, "Unable to resolve type \"" + paramString + "\"");
/*     */       }
/*     */       else
/*     */       {
/* 416 */         DatabaseError.throwSqlException(60, "Unable to resolve type \"" + paramString + "\"");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 423 */     return (TypeDescriptor)localObject;
/*     */   }
/*     */ 
/*     */   public static TypeDescriptor getTypeDescriptor(String paramString, oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 449 */     Object localObject = null;
/* 450 */     byte[][] arrayOfByte = new byte[1][];
/*     */ 
/* 453 */     String str = getSubtypeName(paramOracleConnection, paramArrayOfByte, paramLong);
/*     */ 
/* 455 */     if (str == null) {
/* 456 */       str = paramString;
/*     */     }
/*     */ 
/* 460 */     localObject = (TypeDescriptor)paramOracleConnection.getDescriptor(str);
/*     */ 
/* 462 */     if (localObject == null)
/*     */     {
/* 467 */       SQLName localSQLName = new SQLName(str, paramOracleConnection);
/*     */ 
/* 470 */       OracleTypeADT localOracleTypeADT = new OracleTypeADT(str, paramOracleConnection);
/* 471 */       oracle.jdbc.internal.OracleConnection localOracleConnection = (oracle.jdbc.internal.OracleConnection)paramOracleConnection;
/*     */ 
/* 474 */       localOracleTypeADT.init(localOracleConnection);
/*     */ 
/* 476 */       OracleNamedType localOracleNamedType = localOracleTypeADT.cleanup();
/*     */ 
/* 479 */       switch (localOracleNamedType.getTypeCode())
/*     */       {
/*     */       case 2002:
/*     */       case 2008:
/* 485 */         localObject = new StructDescriptor(localSQLName, (OracleTypeADT)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 488 */         break;
/*     */       case 2003:
/* 491 */         localObject = new ArrayDescriptor(localSQLName, (OracleTypeCOLLECTION)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 494 */         break;
/*     */       case 2007:
/* 497 */         localObject = new OpaqueDescriptor(localSQLName, (OracleTypeOPAQUE)localOracleNamedType, paramOracleConnection);
/*     */ 
/* 500 */         break;
/*     */       case 2004:
/*     */       case 2005:
/*     */       case 2006:
/*     */       default:
/* 506 */         DatabaseError.throwSqlException(1);
/*     */       }
/*     */ 
/* 510 */       paramOracleConnection.putDescriptor(str, localObject);
/*     */     }
/*     */ 
/* 515 */     return (TypeDescriptor)localObject;
/*     */   }
/*     */ 
/*     */   public boolean isInHierarchyOf(String paramString)
/*     */     throws SQLException
/*     */   {
/* 530 */     return false;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 546 */       if (this.sqlName == null) {
/* 547 */         initSQLName();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 554 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */ 
/* 557 */     paramObjectOutputStream.writeObject(this.sqlName);
/* 558 */     paramObjectOutputStream.writeObject(this.pickler);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 570 */     this.sqlName = ((SQLName)paramObjectInputStream.readObject());
/* 571 */     this.pickler = ((OracleNamedType)paramObjectInputStream.readObject());
/*     */   }
/*     */ 
/*     */   public void setConnection(Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 582 */     setPhysicalConnectionOf(paramConnection);
/* 583 */     this.pickler.setConnection(getInternalConnection());
/*     */   }
/*     */ 
/*     */   public static String getSubtypeName(oracle.jdbc.OracleConnection paramOracleConnection, byte[] paramArrayOfByte, long paramLong)
/*     */     throws SQLException
/*     */   {
/* 595 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0) || (paramOracleConnection == null))
/*     */     {
/* 600 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 603 */     String str = OracleTypeADT.getSubtypeName(paramOracleConnection, paramArrayOfByte, paramLong);
/*     */ 
/* 607 */     return str;
/*     */   }
/*     */ 
/*     */   public void initMetadataRecursively()
/*     */     throws SQLException
/*     */   {
/* 613 */     if (this.pickler != null)
/* 614 */       this.pickler.initMetadataRecursively();
/*     */   }
/*     */ 
/*     */   public void initNamesRecursively()
/*     */     throws SQLException
/*     */   {
/* 620 */     if (this.pickler != null)
/* 621 */       this.pickler.initNamesRecursively();
/*     */   }
/*     */ 
/*     */   public void fixupConnection(oracle.jdbc.internal.OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 627 */     if (this.connection == null) this.connection = paramOracleConnection;
/* 628 */     if (this.pickler != null) this.pickler.fixupConnection(paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public String toXMLString()
/*     */     throws SQLException
/*     */   {
/* 639 */     StringWriter localStringWriter = new StringWriter();
/* 640 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 641 */     printXMLHeader(localPrintWriter);
/* 642 */     printXML(localPrintWriter, 0);
/* 643 */     return localStringWriter.toString();
/*     */   }
/*     */ 
/*     */   public void printXML(PrintStream paramPrintStream)
/*     */     throws SQLException
/*     */   {
/* 655 */     PrintWriter localPrintWriter = new PrintWriter(paramPrintStream, true);
/* 656 */     printXMLHeader(localPrintWriter);
/* 657 */     printXML(localPrintWriter, 0);
/*     */   }
/*     */ 
/*     */   void printXML(PrintWriter paramPrintWriter, int paramInt) throws SQLException
/*     */   {
/* 662 */     String str = getClass().getName();
/* 663 */     int i = hashCode();
/* 664 */     paramPrintWriter.println("<" + str + " hashCode=\"" + i + "\" >");
/* 665 */     if (this.pickler != null) this.pickler.printXML(paramPrintWriter, paramInt + 1);
/* 666 */     paramPrintWriter.println("</" + str + ">");
/*     */   }
/*     */ 
/*     */   void printXMLHeader(PrintWriter paramPrintWriter) throws SQLException
/*     */   {
/* 671 */     paramPrintWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TypeDescriptor
 * JD-Core Version:    0.6.0
 */