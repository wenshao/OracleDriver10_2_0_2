/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.StructDescriptor;
/*     */ import oracle.sql.TypeDescriptor;
/*     */ 
/*     */ public abstract class OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -6719430495533003861L;
/*     */   public static final int STYLE_ARRAY_LENGTH = 0;
/*     */   public static final int STYLE_DATUM = 1;
/*     */   public static final int STYLE_JAVA = 2;
/*     */   public static final int STYLE_RAWBYTE = 3;
/*     */   public static final int STYLE_INT = 4;
/*     */   public static final int STYLE_DOUBLE = 5;
/*     */   public static final int STYLE_FLOAT = 6;
/*     */   public static final int STYLE_LONG = 7;
/*     */   public static final int STYLE_SHORT = 8;
/*     */   public static final int STYLE_SKIP = 9;
/*     */   static final int FORMAT_ADT_ATTR = 1;
/*     */   static final int FORMAT_COLL_ELEM = 2;
/*     */   static final int FORMAT_COLL_ELEM_NO_INDICATOR = 3;
/*     */   int nullOffset;
/*     */   int ldsOffset;
/*     */   int sizeForLds;
/*     */   int alignForLds;
/*     */   int typeCode;
/*     */   int dbTypeCode;
/*     */   static final int KOPMAP_FLOAT = 2;
/*     */   static final int KOPMAP_SB4 = 4;
/*     */   static final int KOPMAP_PTR = 5;
/*     */   static final int KOPMAP_ORLD = 11;
/*     */   static final int KOPMAP_ORLN = 12;
/* 556 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   public OracleType()
/*     */   {
/*  66 */     this.nullOffset = 0;
/*  67 */     this.ldsOffset = 0;
/*  68 */     this.sizeForLds = 0;
/*     */   }
/*     */ 
/*     */   public OracleType(int paramInt)
/*     */   {
/*  73 */     this();
/*     */ 
/*  77 */     this.typeCode = paramInt;
/*     */   }
/*     */ 
/*     */   public boolean isInHierarchyOf(OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
/*     */     throws SQLException
/*     */   {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isObjectType()
/*     */   {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   public TypeDescriptor getTypeDescriptor()
/*     */   {
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public abstract Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException;
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 138 */     Datum[] arrayOfDatum = null;
/*     */ 
/* 140 */     if (paramObject != null)
/*     */     {
/* 142 */       if ((paramObject instanceof Object[]))
/*     */       {
/* 144 */         Object[] arrayOfObject = (Object[])paramObject;
/*     */ 
/* 146 */         int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 149 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 151 */         for (int j = 0; j < i; j++) {
/* 152 */           arrayOfDatum[j] = toDatum(arrayOfObject[((int)paramLong + j - 1)], paramOracleConnection);
/*     */         }
/*     */       }
/* 155 */       DatabaseError.throwSqlException(59, paramObject);
/*     */     }
/*     */ 
/* 161 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public void setTypeCode(int paramInt)
/*     */   {
/* 166 */     this.typeCode = paramInt;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */     throws SQLException
/*     */   {
/* 172 */     return this.typeCode;
/*     */   }
/*     */ 
/*     */   public void setDBTypeCode(int paramInt)
/*     */   {
/* 177 */     this.dbTypeCode = paramInt;
/*     */   }
/*     */ 
/*     */   public int getDBTypeCode() throws SQLException
/*     */   {
/* 182 */     return this.dbTypeCode;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/* 206 */     this.nullOffset = paramTDSReader.nullOffset;
/* 207 */     this.ldsOffset = paramTDSReader.ldsOffset;
/*     */ 
/* 209 */     paramTDSReader.nullOffset += 1;
/* 210 */     paramTDSReader.ldsOffset += 1;
/*     */   }
/*     */ 
/*     */   public int getSizeLDS(byte[] paramArrayOfByte)
/*     */   {
/* 224 */     if (this.sizeForLds == 0)
/*     */     {
/* 226 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 5);
/* 227 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 5);
/*     */     }
/*     */ 
/* 232 */     return this.sizeForLds;
/*     */   }
/*     */ 
/*     */   public int getAlignLDS(byte[] paramArrayOfByte)
/*     */   {
/* 246 */     if (this.sizeForLds == 0)
/*     */     {
/* 248 */       this.sizeForLds = Util.fdoGetSize(paramArrayOfByte, 5);
/* 249 */       this.alignForLds = Util.fdoGetAlign(paramArrayOfByte, 5);
/*     */     }
/*     */ 
/* 254 */     return this.alignForLds;
/*     */   }
/*     */ 
/*     */   protected abstract Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException;
/*     */ 
/*     */   protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 291 */     if (paramInt == 9)
/*     */     {
/* 293 */       paramPickleContext.skipDataValue();
/*     */ 
/* 295 */       return null;
/*     */     }
/*     */ 
/* 299 */     byte[] arrayOfByte = paramPickleContext.readDataValue();
/*     */ 
/* 301 */     return toObject(arrayOfByte, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 311 */     if (paramInt == 9)
/*     */     {
/* 313 */       paramPickleContext.skipDataValue();
/*     */ 
/* 315 */       return null;
/*     */     }
/*     */ 
/* 319 */     byte[] arrayOfByte = paramPickleContext.readDataValue(paramByte);
/*     */ 
/* 321 */     return toObject(arrayOfByte, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
/*     */     throws SQLException
/*     */   {
/* 328 */     DatabaseError.throwSqlException(1);
/* 329 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 356 */     int i = paramPickleContext.writeLength((int)paramDatum.getLength());
/*     */ 
/* 358 */     i += paramPickleContext.writeData(paramDatum.shareBytes());
/*     */ 
/* 362 */     return i;
/*     */   }
/*     */ 
/*     */   void writeSerializedFields(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 370 */     paramObjectOutputStream.writeInt(this.nullOffset);
/* 371 */     paramObjectOutputStream.writeInt(this.ldsOffset);
/* 372 */     paramObjectOutputStream.writeInt(this.sizeForLds);
/* 373 */     paramObjectOutputStream.writeInt(this.alignForLds);
/* 374 */     paramObjectOutputStream.writeInt(this.typeCode);
/* 375 */     paramObjectOutputStream.writeInt(this.dbTypeCode);
/*     */   }
/*     */ 
/*     */   void readSerializedFields(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 384 */     this.nullOffset = paramObjectInputStream.readInt();
/* 385 */     this.ldsOffset = paramObjectInputStream.readInt();
/* 386 */     this.sizeForLds = paramObjectInputStream.readInt();
/* 387 */     this.alignForLds = paramObjectInputStream.readInt();
/* 388 */     this.typeCode = paramObjectInputStream.readInt();
/* 389 */     this.dbTypeCode = paramObjectInputStream.readInt();
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 402 */     paramObjectOutputStream.writeInt(this.nullOffset);
/* 403 */     paramObjectOutputStream.writeInt(this.ldsOffset);
/* 404 */     paramObjectOutputStream.writeInt(this.sizeForLds);
/* 405 */     paramObjectOutputStream.writeInt(this.alignForLds);
/* 406 */     paramObjectOutputStream.writeInt(this.typeCode);
/* 407 */     paramObjectOutputStream.writeInt(this.dbTypeCode);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 417 */     this.nullOffset = paramObjectInputStream.readInt();
/* 418 */     this.ldsOffset = paramObjectInputStream.readInt();
/* 419 */     this.sizeForLds = paramObjectInputStream.readInt();
/* 420 */     this.alignForLds = paramObjectInputStream.readInt();
/* 421 */     this.typeCode = paramObjectInputStream.readInt();
/* 422 */     this.dbTypeCode = paramObjectInputStream.readInt();
/*     */   }
/*     */ 
/*     */   public void setConnection(OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isNCHAR()
/*     */     throws SQLException
/*     */   {
/* 437 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPrecision()
/*     */     throws SQLException
/*     */   {
/* 453 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getScale() throws SQLException
/*     */   {
/* 458 */     return 0;
/*     */   }
/*     */ 
/*     */   public void initMetadataRecursively()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void initNamesRecursively()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void initChildNamesRecursively(Map paramMap)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void cacheDescriptor()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setNames(String paramString1, String paramString2)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String toXMLString()
/*     */     throws SQLException
/*     */   {
/* 489 */     StringWriter localStringWriter = new StringWriter();
/* 490 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 491 */     printXMLHeader(localPrintWriter);
/* 492 */     printXML(localPrintWriter, 0);
/* 493 */     return localStringWriter.toString();
/*     */   }
/*     */ 
/*     */   public void printXML(PrintStream paramPrintStream) throws SQLException
/*     */   {
/* 498 */     PrintWriter localPrintWriter = new PrintWriter(paramPrintStream, true);
/* 499 */     printXMLHeader(localPrintWriter);
/* 500 */     printXML(localPrintWriter, 0);
/*     */   }
/*     */ 
/*     */   void printXMLHeader(PrintWriter paramPrintWriter) throws SQLException
/*     */   {
/* 505 */     paramPrintWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
/*     */   }
/*     */ 
/*     */   public void printXML(PrintWriter paramPrintWriter, int paramInt) throws SQLException
/*     */   {
/* 510 */     for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 511 */     paramPrintWriter.println("<OracleType typecode=\"" + this.typeCode + "\"" + " hashCode=\"" + hashCode() + "\" " + " ldsOffset=\"" + this.ldsOffset + "\"" + " sizeForLds=\"" + this.sizeForLds + "\"" + " alignForLds=\"" + this.alignForLds + "\"" + " />");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleType
 * JD-Core Version:    0.6.0
 */