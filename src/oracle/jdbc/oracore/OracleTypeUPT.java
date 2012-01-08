/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.driver.OracleLog;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.ArrayDescriptor;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.OPAQUE;
/*     */ import oracle.sql.STRUCT;
/*     */ import oracle.sql.StructDescriptor;
/*     */ 
/*     */ public class OracleTypeUPT extends OracleTypeADT
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -1994358478872378695L;
/*     */   static final byte KOPU_UPT_ADT = -6;
/*     */   static final byte KOPU_UPT_COLL = -5;
/*     */   static final byte KOPU_UPT_REFCUR = 102;
/*     */   static final byte KOTTCOPQ = 58;
/*  52 */   byte uptCode = 0;
/*  53 */   OracleNamedType realType = null;
/*     */ 
/* 730 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   protected OracleTypeUPT()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeUPT(String paramString, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  67 */     super(paramString, paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public OracleTypeUPT(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  76 */     super(paramOracleTypeADT, paramInt, paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  95 */     if (paramObject != null) {
/*  96 */       return this.realType.toDatum(paramObject, paramOracleConnection);
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 114 */     if (paramObject != null) {
/* 115 */       return this.realType.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */     throws SQLException
/*     */   {
/* 125 */     switch (this.uptCode)
/*     */     {
/*     */     case -6:
/* 129 */       return this.realType.getTypeCode();
/*     */     case -5:
/* 132 */       return 2003;
/*     */     case 58:
/* 135 */       return 2007;
/*     */     }
/*     */ 
/* 141 */     DatabaseError.throwSqlException(1, "Invalid type code");
/*     */ 
/* 147 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isInHierarchyOf(OracleType paramOracleType)
/*     */     throws SQLException
/*     */   {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
/*     */     throws SQLException
/*     */   {
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isObjectType()
/*     */   {
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/* 181 */     this.nullOffset = paramTDSReader.nullOffset;
/* 182 */     paramTDSReader.nullOffset += 1;
/* 183 */     this.ldsOffset = paramTDSReader.ldsOffset;
/* 184 */     paramTDSReader.ldsOffset += 1;
/*     */ 
/* 187 */     long l = paramTDSReader.readLong();
/*     */ 
/* 189 */     this.uptCode = paramTDSReader.readByte();
/*     */ 
/* 191 */     paramTDSReader.addNormalPatch(l, this.uptCode, this);
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*     */     Object localObject;
/* 208 */     switch (paramInt1)
/*     */     {
/*     */     case 2:
/* 213 */       switch (this.uptCode)
/*     */       {
/*     */       case -5:
/* 218 */         if ((paramUnpickleContext.readByte() & 0x1) == 1)
/*     */         {
/* 220 */           paramUnpickleContext.skipBytes(2);
/*     */ 
/* 222 */           return null;
/*     */         }
/*     */ 
/* 225 */         return ((OracleTypeCOLLECTION)this.realType).unpickle80(paramUnpickleContext, (ARRAY)null, paramInt2 == 9 ? paramInt2 : 3, paramInt2, paramMap);
/*     */       case -6:
/* 232 */         if ((paramUnpickleContext.readByte() & 0x1) == 1)
/*     */         {
/* 234 */           paramUnpickleContext.skipBytes(4);
/*     */ 
/* 236 */           return null;
/*     */         }
/*     */ 
/* 239 */         switch (paramInt2)
/*     */         {
/*     */         case 1:
/* 243 */           return ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 3, paramInt2, paramMap);
/*     */         case 2:
/* 247 */           localObject = ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 1, paramInt2, paramMap);
/*     */ 
/* 250 */           return localObject == null ? localObject : ((STRUCT)localObject).toJdbc(paramMap);
/*     */         case 9:
/* 253 */           return ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 9, 1, paramMap);
/*     */         }
/*     */ 
/* 257 */         DatabaseError.throwSqlException(1);
/*     */ 
/* 261 */         break;
/*     */       default:
/* 264 */         DatabaseError.throwSqlException(48, "upt_type");
/*     */       }
/*     */ 
/* 269 */       break;
/*     */     case 3:
/* 273 */       switch (this.uptCode)
/*     */       {
/*     */       case -5:
/* 278 */         return ((OracleTypeCOLLECTION)this.realType).unpickle80(paramUnpickleContext, (ARRAY)null, paramInt2 == 9 ? paramInt2 : 3, paramInt2, paramMap);
/*     */       case -6:
/* 285 */         switch (paramInt2)
/*     */         {
/*     */         case 1:
/* 289 */           return ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 3, paramInt2, paramMap);
/*     */         case 2:
/* 293 */           localObject = ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 1, paramInt2, paramMap);
/*     */ 
/* 296 */           return localObject == null ? localObject : ((STRUCT)localObject).toJdbc(paramMap);
/*     */         case 9:
/* 299 */           return ((OracleTypeADT)this.realType).unpickle80(paramUnpickleContext, (STRUCT)null, 9, 1, paramMap);
/*     */         }
/*     */ 
/* 303 */         DatabaseError.throwSqlException(1);
/*     */ 
/* 307 */         break;
/*     */       default:
/* 310 */         DatabaseError.throwSqlException(48, "upt_type");
/*     */       }
/*     */ 
/* 315 */       break;
/*     */     case 1:
/* 319 */       switch (this.uptCode)
/*     */       {
/*     */       case -5:
/* 324 */         if (paramUnpickleContext.isNull(this.nullOffset))
/*     */         {
/* 326 */           paramUnpickleContext.skipBytes(4);
/*     */ 
/* 328 */           return null;
/*     */         }
/*     */ 
/* 331 */         paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
/*     */ 
/* 333 */         if (paramInt2 == 9)
/*     */         {
/* 335 */           paramUnpickleContext.skipBytes(4);
/*     */ 
/* 337 */           return null;
/*     */         }
/*     */ 
/* 340 */         paramUnpickleContext.markAndSkip();
/*     */ 
/* 342 */         localObject = ((OracleTypeCOLLECTION)this.realType).unpickle80(paramUnpickleContext, (ARRAY)null, 3, paramInt2, paramMap);
/*     */ 
/* 345 */         paramUnpickleContext.reset();
/*     */ 
/* 347 */         return localObject;
/*     */       }
/*     */ 
/* 351 */       DatabaseError.throwSqlException(1, "upt_type");
/*     */ 
/* 355 */       break;
/*     */     default:
/* 358 */       DatabaseError.throwSqlException(1, "upt_type");
/*     */     }
/*     */ 
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 376 */     int i = 0;
/*     */ 
/* 378 */     if (paramDatum == null)
/*     */     {
/* 380 */       i += paramPickleContext.writeElementNull();
/*     */     }
/*     */     else
/*     */     {
/* 384 */       int j = paramPickleContext.offset();
/*     */ 
/* 386 */       i += paramPickleContext.writeLength(PickleContext.KOPI20_LN_MAXV + 1);
/*     */ 
/* 388 */       int k = 0;
/*     */ 
/* 391 */       if ((this.uptCode == -6) && (!((OracleTypeADT)this.realType).isFinalType()))
/*     */       {
/* 393 */         if ((paramDatum instanceof STRUCT))
/*     */         {
/* 395 */           k = ((STRUCT)paramDatum).getDescriptor().getOracleTypeADT().pickle81(paramPickleContext, paramDatum);
/*     */         }
/*     */         else
/*     */         {
/* 400 */           DatabaseError.throwSqlException(1, "invalid upt state");
/*     */         }
/*     */       }
/*     */       else {
/* 404 */         k = this.realType.pickle81(paramPickleContext, paramDatum);
/*     */       }
/* 406 */       i += k;
/*     */ 
/* 408 */       paramPickleContext.patchImageLen(j, k);
/*     */     }
/*     */ 
/* 411 */     return i;
/*     */   }
/*     */ 
/*     */   protected Object unpickle81rec(PickleContext paramPickleContext, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 426 */     OracleLog.print(this, 16, 8, 32, "OracleTypeUPT.unpickle81rec(" + paramPickleContext + ") " + this.sqlName);
/*     */ 
/* 440 */     byte b = paramPickleContext.readByte();
/*     */ 
/* 442 */     if (PickleContext.isElementNull(b))
/*     */     {
/* 444 */       return null;
/*     */     }
/* 446 */     if (paramInt == 9)
/*     */     {
/* 448 */       paramPickleContext.skipBytes(paramPickleContext.readRestOfLength(b));
/*     */ 
/* 450 */       return null;
/*     */     }
/*     */ 
/* 454 */     paramPickleContext.skipRestOfLength(b);
/*     */ 
/* 456 */     return unpickle81UPT(paramPickleContext, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   protected Object unpickle81rec(PickleContext paramPickleContext, byte paramByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 471 */     long l = paramPickleContext.readRestOfLength(paramByte);
/*     */ 
/* 473 */     if (paramInt == 9)
/*     */     {
/* 475 */       paramPickleContext.skipBytes((int)l);
/*     */ 
/* 477 */       return null;
/*     */     }
/*     */ 
/* 480 */     return unpickle81UPT(paramPickleContext, paramInt, paramMap);
/*     */   }
/*     */ 
/*     */   private Object unpickle81UPT(PickleContext paramPickleContext, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*     */     Object localObject;
/* 486 */     switch (this.uptCode)
/*     */     {
/*     */     case -6:
/* 491 */       switch (paramInt)
/*     */       {
/*     */       case 1:
/* 495 */         return ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 3, paramInt, paramMap);
/*     */       case 2:
/* 499 */         localObject = ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 1, paramInt, paramMap);
/*     */ 
/* 502 */         return localObject == null ? localObject : ((STRUCT)localObject).toJdbc(paramMap);
/*     */       case 9:
/* 505 */         return ((OracleTypeADT)this.realType).unpickle81(paramPickleContext, (STRUCT)null, 9, 1, paramMap);
/*     */       }
/*     */ 
/* 512 */       DatabaseError.throwSqlException(1);
/*     */ 
/* 519 */       break;
/*     */     case -5:
/* 523 */       return ((OracleTypeCOLLECTION)this.realType).unpickle81(paramPickleContext, (ARRAY)null, paramInt == 9 ? paramInt : 3, paramInt, paramMap);
/*     */     case 58:
/* 529 */       switch (paramInt)
/*     */       {
/*     */       case 1:
/*     */       case 9:
/* 534 */         return ((OracleTypeOPAQUE)this.realType).unpickle81(paramPickleContext, (OPAQUE)null, paramInt, paramMap);
/*     */       case 2:
/* 538 */         localObject = ((OracleTypeOPAQUE)this.realType).unpickle81(paramPickleContext, (OPAQUE)null, paramInt, paramMap);
/*     */ 
/* 541 */         return localObject == null ? localObject : ((OPAQUE)localObject).toJdbc(paramMap);
/*     */       }
/*     */ 
/* 548 */       DatabaseError.throwSqlException(1);
/*     */     default:
/* 560 */       DatabaseError.throwSqlException(1, "Unrecognized UPT code");
/*     */     }
/*     */ 
/* 567 */     return null;
/*     */   }
/*     */ 
/*     */   protected Datum unpickle81datumAsNull(PickleContext paramPickleContext, byte paramByte1, byte paramByte2)
/*     */     throws SQLException
/*     */   {
/* 574 */     return null;
/*     */   }
/*     */ 
/*     */   StructDescriptor createStructDescriptor()
/*     */     throws SQLException
/*     */   {
/* 583 */     StructDescriptor localStructDescriptor = null;
/*     */ 
/* 585 */     if (this.sqlName == null)
/* 586 */       localStructDescriptor = new StructDescriptor((OracleTypeADT)this.realType, this.connection);
/*     */     else {
/* 588 */       localStructDescriptor = StructDescriptor.createDescriptor(this.sqlName, this.connection);
/*     */     }
/* 590 */     return localStructDescriptor;
/*     */   }
/*     */ 
/*     */   ArrayDescriptor createArrayDescriptor() throws SQLException
/*     */   {
/* 595 */     ArrayDescriptor localArrayDescriptor = null;
/*     */ 
/* 597 */     if (this.sqlName == null)
/* 598 */       localArrayDescriptor = new ArrayDescriptor((OracleTypeCOLLECTION)this.realType, this.connection);
/*     */     else {
/* 600 */       localArrayDescriptor = ArrayDescriptor.createDescriptor(this.sqlName, this.connection);
/*     */     }
/* 602 */     return localArrayDescriptor;
/*     */   }
/*     */ 
/*     */   public OracleType getRealType() throws SQLException
/*     */   {
/* 607 */     return this.realType;
/*     */   }
/*     */ 
/*     */   public int getNumAttrs() throws SQLException
/*     */   {
/* 612 */     return ((OracleTypeADT)this.realType).getNumAttrs();
/*     */   }
/*     */ 
/*     */   public OracleType getAttrTypeAt(int paramInt) throws SQLException
/*     */   {
/* 617 */     return ((OracleTypeADT)this.realType).getAttrTypeAt(paramInt);
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 630 */     paramObjectOutputStream.writeByte(this.uptCode);
/* 631 */     paramObjectOutputStream.writeObject(this.realType);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 641 */     this.uptCode = paramObjectInputStream.readByte();
/* 642 */     this.realType = ((OracleNamedType)paramObjectInputStream.readObject());
/*     */   }
/*     */ 
/*     */   public void setConnection(OracleConnection paramOracleConnection) throws SQLException
/*     */   {
/* 647 */     this.connection = paramOracleConnection;
/*     */ 
/* 649 */     this.realType.setConnection(paramOracleConnection);
/*     */   }
/*     */ 
/*     */   public void initChildNamesRecursively(Map paramMap) throws SQLException
/*     */   {
/* 654 */     if (this.realType != null)
/*     */     {
/* 656 */       this.realType.setSqlName(this.sqlName);
/* 657 */       this.realType.initChildNamesRecursively(paramMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initMetadataRecursively() throws SQLException
/*     */   {
/* 663 */     initMetadata(this.connection);
/* 664 */     if (this.realType != null) this.realType.initMetadataRecursively(); 
/*     */   }
/*     */ 
/*     */   public void cacheDescriptor()
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void printXML(PrintWriter paramPrintWriter, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 674 */     for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 675 */     paramPrintWriter.println("<OracleTypeUPT> sqlName=\"" + this.sqlName + "\" " + " toid=\"" + this.toid + "\" " + ">");
/*     */ 
/* 679 */     if (this.realType != null)
/* 680 */       this.realType.printXML(paramPrintWriter, paramInt + 1);
/* 681 */     for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 682 */     paramPrintWriter.println("</OracleTypeUPT>");
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeUPT
 * JD-Core Version:    0.6.0
 */