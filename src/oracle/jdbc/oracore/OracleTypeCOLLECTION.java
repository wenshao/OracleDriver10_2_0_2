/*      */ package oracle.jdbc.oracore;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.SQLName;
/*      */ import oracle.sql.StructDescriptor;
/*      */ import oracle.sql.TypeDescriptor;
/*      */ 
/*      */ public class OracleTypeCOLLECTION extends OracleTypeADT
/*      */   implements Serializable
/*      */ {
/*      */   static final long serialVersionUID = -7279638692691669378L;
/*      */   public static final int TYPE_PLSQL_INDEX_TABLE = 1;
/*      */   public static final int TYPE_NESTED_TABLE = 2;
/*      */   public static final int TYPE_VARRAY = 3;
/*   57 */   int userCode = 0;
/*   58 */   long maxSize = 0L;
/*   59 */   OracleType elementType = null;
/*      */ 
/* 1309 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*      */ 
/*      */   public OracleTypeCOLLECTION(String paramString, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*   68 */     super(paramString, paramOracleConnection);
/*      */   }
/*      */ 
/*      */   public OracleTypeCOLLECTION(OracleTypeADT paramOracleTypeADT, int paramInt, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*   77 */     super(paramOracleTypeADT, paramInt, paramOracleConnection);
/*      */   }
/*      */ 
/*      */   public OracleTypeCOLLECTION(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, OracleConnection paramOracleConnection, byte[] paramArrayOfByte4)
/*      */     throws SQLException
/*      */   {
/*   88 */     super(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfByte3, paramOracleConnection, paramArrayOfByte4);
/*      */   }
/*      */ 
/*      */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  105 */     if (paramObject != null)
/*      */     {
/*  107 */       if ((paramObject instanceof ARRAY)) {
/*  108 */         return (ARRAY)paramObject;
/*      */       }
/*      */ 
/*  111 */       ArrayDescriptor localArrayDescriptor = createArrayDescriptor();
/*      */ 
/*  113 */       return new ARRAY(localArrayDescriptor, this.connection, paramObject);
/*      */     }
/*      */ 
/*  117 */     return null;
/*      */   }
/*      */ 
/*      */   public int getTypeCode()
/*      */   {
/*  127 */     return 2003;
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(OracleType paramOracleType)
/*      */     throws SQLException
/*      */   {
/*  136 */     if (paramOracleType == null) {
/*  137 */       return false;
/*      */     }
/*  139 */     if (paramOracleType == this) {
/*  140 */       return true;
/*      */     }
/*  142 */     if (paramOracleType.getClass() != getClass()) {
/*  143 */       return false;
/*      */     }
/*  145 */     return paramOracleType.getTypeDescriptor().getName().equals(this.descriptor.getName());
/*      */   }
/*      */ 
/*      */   public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
/*      */     throws SQLException
/*      */   {
/*  152 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isObjectType()
/*      */   {
/*  157 */     return false;
/*      */   }
/*      */ 
/*      */   public void parseTDSrec(TDSReader paramTDSReader)
/*      */     throws SQLException
/*      */   {
/*  172 */     long l = paramTDSReader.readLong();
/*      */ 
/*  176 */     this.maxSize = paramTDSReader.readLong();
/*      */ 
/*  182 */     this.userCode = paramTDSReader.readByte();
/*      */ 
/*  185 */     paramTDSReader.addSimplePatch(l, this);
/*      */   }
/*      */ 
/*      */   public Datum unlinearize(byte[] paramArrayOfByte, long paramLong, Datum paramDatum, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  195 */     return unlinearize(paramArrayOfByte, paramLong, paramDatum, 1L, -1, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   public Datum unlinearize(byte[] paramArrayOfByte, long paramLong1, Datum paramDatum, long paramLong2, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  202 */     OracleConnection localOracleConnection = getConnection();
/*  203 */     Datum localDatum = null;
/*      */ 
/*  208 */     if (localOracleConnection == null)
/*      */     {
/*  210 */       localDatum = unlinearizeInternal(paramArrayOfByte, paramLong1, paramDatum, paramLong2, paramInt1, paramInt2, paramMap);
/*      */     }
/*      */     else
/*      */     {
/*  215 */       synchronized (localOracleConnection)
/*      */       {
/*  217 */         localDatum = unlinearizeInternal(paramArrayOfByte, paramLong1, paramDatum, paramLong2, paramInt1, paramInt2, paramMap);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  222 */     return localDatum;
/*      */   }
/*      */ 
/*      */   public synchronized Datum unlinearizeInternal(byte[] paramArrayOfByte, long paramLong1, Datum paramDatum, long paramLong2, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  236 */     if (paramArrayOfByte == null) {
/*  237 */       return null;
/*      */     }
/*  239 */     if ((paramArrayOfByte[0] & 0x80) > 0)
/*      */     {
/*  241 */       localObject = new PickleContext(paramArrayOfByte, paramLong1);
/*      */ 
/*  243 */       return unpickle81((PickleContext)localObject, (ARRAY)paramDatum, paramLong2, paramInt1, 1, paramInt2, paramMap);
/*      */     }
/*      */ 
/*  251 */     Object localObject = new UnpickleContext(paramArrayOfByte, (int)paramLong1, null, null, this.bigEndian);
/*      */ 
/*  254 */     return (Datum)unpickle80((UnpickleContext)localObject, (ARRAY)paramDatum, paramLong2, paramInt1, 1, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   public synchronized boolean isInlineImage(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  266 */     if (paramArrayOfByte == null) {
/*  267 */       return false;
/*      */     }
/*  269 */     if ((paramArrayOfByte[paramInt] & 0x80) > 0)
/*      */     {
/*  271 */       if (PickleContext.isCollectionImage_pctx(paramArrayOfByte[paramInt]))
/*  272 */         return true;
/*  273 */       if (PickleContext.isDegenerateImage_pctx(paramArrayOfByte[paramInt])) {
/*  274 */         return false;
/*      */       }
/*  276 */       DatabaseError.throwSqlException(1, "Image is not a collection image");
/*      */ 
/*  279 */       return false;
/*      */     }
/*      */ 
/*  286 */     if ((paramArrayOfByte[(paramInt + 0)] == 0) && (paramArrayOfByte[(paramInt + 1)] == 0) && (paramArrayOfByte[(paramInt + 2)] == 0) && (paramArrayOfByte[(paramInt + 3)] == 0))
/*      */     {
/*  288 */       return false;
/*      */     }
/*  290 */     return (paramArrayOfByte[(paramInt + 10)] & 0x1) == 1;
/*      */   }
/*      */ 
/*      */   protected ARRAY unpickle80(UnpickleContext paramUnpickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  308 */     return unpickle80(paramUnpickleContext, paramARRAY, 1L, -1, paramInt1, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   protected ARRAY unpickle80(UnpickleContext paramUnpickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, int paramInt3, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  320 */     ARRAY localARRAY = paramARRAY;
/*      */ 
/*  322 */     if (paramInt2 == 3)
/*      */     {
/*  324 */       if (localARRAY == null)
/*      */       {
/*  326 */         ArrayDescriptor localArrayDescriptor1 = createArrayDescriptor();
/*      */ 
/*  328 */         localARRAY = new ARRAY(localArrayDescriptor1, (byte[])null, this.connection);
/*      */       }
/*      */ 
/*  331 */       localARRAY.setImage(paramUnpickleContext.image(), paramUnpickleContext.absoluteOffset(), 0L);
/*      */     }
/*      */ 
/*  334 */     long l1 = paramUnpickleContext.readLong();
/*      */ 
/*  336 */     if (l1 == 0L)
/*      */     {
/*  338 */       return null;
/*      */     }
/*  340 */     if (paramInt2 == 9)
/*      */     {
/*  342 */       paramUnpickleContext.skipBytes((int)l1);
/*      */ 
/*  344 */       return localARRAY;
/*      */     }
/*  346 */     if (paramInt2 == 3)
/*      */     {
/*  350 */       localARRAY.setImageLength(l1 + 4L);
/*  351 */       paramUnpickleContext.skipBytes((int)l1);
/*      */ 
/*  353 */       return localARRAY;
/*      */     }
/*      */ 
/*  356 */     if (localARRAY == null)
/*      */     {
/*  358 */       ArrayDescriptor localArrayDescriptor2 = createArrayDescriptor();
/*      */ 
/*  360 */       localARRAY = new ARRAY(localArrayDescriptor2, (byte[])null, this.connection);
/*      */     }
/*      */ 
/*  363 */     paramUnpickleContext.skipBytes(2);
/*      */ 
/*  365 */     long l2 = paramUnpickleContext.readLong();
/*  366 */     int i = paramUnpickleContext.readByte();
/*      */ 
/*  368 */     localARRAY.setLocator(paramUnpickleContext.readBytes((int)l2 - 1));
/*      */ 
/*  371 */     int j = (i & 0x1) == 1 ? 1 : 0;
/*      */ 
/*  373 */     if (j != 0)
/*      */     {
/*  375 */       long l3 = paramUnpickleContext.readLong() + paramUnpickleContext.offset();
/*      */ 
/*  377 */       UnpickleContext localUnpickleContext = new UnpickleContext(paramUnpickleContext.image(), paramUnpickleContext.absoluteOffset(), null, null, this.bigEndian);
/*      */ 
/*  380 */       if ((paramLong == 1L) && (paramInt1 == -1)) {
/*  381 */         localARRAY = (ARRAY)unpickle80rec(localUnpickleContext, localARRAY, paramInt3, paramMap);
/*      */       }
/*      */       else {
/*  384 */         localARRAY = (ARRAY)unpickle80rec(localUnpickleContext, localARRAY, paramLong, paramInt1, paramInt3, paramMap);
/*      */       }
/*      */ 
/*  387 */       paramUnpickleContext.skipTo(l3);
/*      */     }
/*      */ 
/*  390 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  403 */     return unpickle80rec(paramUnpickleContext, (ARRAY)null, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   private Object unpickle80rec(UnpickleContext paramUnpickleContext, ARRAY paramARRAY, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  413 */     ARRAY localARRAY = paramARRAY;
/*      */ 
/*  415 */     if (localARRAY == null)
/*      */     {
/*  417 */       ArrayDescriptor localArrayDescriptor = createArrayDescriptor();
/*      */ 
/*  419 */       localARRAY = new ARRAY(localArrayDescriptor, (byte[])null, this.connection);
/*      */     }
/*      */ 
/*  423 */     int i = (int)paramUnpickleContext.readLong();
/*      */ 
/*  425 */     localARRAY.setLength(i);
/*      */ 
/*  427 */     if (paramInt == 0) {
/*  428 */       return localARRAY;
/*      */     }
/*      */ 
/*  433 */     int j = paramUnpickleContext.readByte();
/*      */ 
/*  435 */     if ((j != 0) && (j != 2)) {
/*  436 */       DatabaseError.throwSqlException(23, "collection flag = " + j);
/*      */     }
/*      */ 
/*  439 */     int k = j == 0 ? 2 : 3;
/*      */ 
/*  442 */     unpickle80rec_elems(paramUnpickleContext, localARRAY, 1, i, paramInt, paramMap, k);
/*      */ 
/*  445 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   private Object unpickle80rec(UnpickleContext paramUnpickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  457 */     ARRAY localARRAY = paramARRAY;
/*      */ 
/*  459 */     if (localARRAY == null)
/*      */     {
/*  461 */       ArrayDescriptor localArrayDescriptor = createArrayDescriptor();
/*      */ 
/*  463 */       localARRAY = new ARRAY(localArrayDescriptor, (byte[])null, this.connection);
/*      */     }
/*      */ 
/*  467 */     long l1 = paramUnpickleContext.readLong();
/*      */ 
/*  469 */     localARRAY.setLength((int)l1);
/*      */ 
/*  472 */     int i = paramUnpickleContext.readByte();
/*      */ 
/*  474 */     if ((i != 0) && (i != 2)) {
/*  475 */       DatabaseError.throwSqlException(23, "collection flag = " + i);
/*      */     }
/*      */ 
/*  478 */     int j = i == 0 ? 2 : 3;
/*      */ 
/*  481 */     int k = (int)getAccessLength(l1, paramLong, paramInt1);
/*  482 */     int m = ArrayDescriptor.getCacheStyle(localARRAY) == 1 ? 1 : 0;
/*      */ 
/*  486 */     if ((paramLong > 1L) && (k > 0))
/*      */     {
/*  488 */       if (((this.elementType instanceof OracleTypeNUMBER)) || ((this.elementType instanceof OracleTypeFLOAT)) || ((this.elementType instanceof OracleTypeSINT32)));
/*  494 */       switch (j)
/*      */       {
/*      */       case 2:
/*  498 */         paramUnpickleContext.skipBytes(23 * ((int)paramLong - 1));
/*      */ 
/*  500 */         break;
/*      */       case 3:
/*  503 */         paramUnpickleContext.skipBytes(22 * ((int)paramLong - 1));
/*      */ 
/*  505 */         break;
/*      */       default:
/*  507 */         DatabaseError.throwSqlException(1); break;
/*      */ 
/*  514 */         long l2 = localARRAY.getLastIndex();
/*      */         long l3;
/*  516 */         if (l2 < paramLong)
/*      */         {
/*  518 */           if (l2 > 0L)
/*  519 */             paramUnpickleContext.skipTo(localARRAY.getLastOffset());
/*      */           else {
/*  521 */             l2 = 1L;
/*      */           }
/*  523 */           if (m != 0)
/*      */           {
/*  525 */             for (l3 = l2; l3 < paramLong; l3 += 1L)
/*      */             {
/*  527 */               localARRAY.setIndexOffset(l3, paramUnpickleContext.offset());
/*  528 */               this.elementType.unpickle80rec(paramUnpickleContext, j, 9, null);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  533 */           for (l3 = l2; l3 < paramLong; l3 += 1L) {
/*  534 */             this.elementType.unpickle80rec(paramUnpickleContext, j, 9, null);
/*      */           }
/*      */         }
/*  537 */         if (l2 > paramLong)
/*      */         {
/*  539 */           l3 = localARRAY.getOffset(paramLong);
/*      */ 
/*  541 */           if (l3 != -1L)
/*      */           {
/*  543 */             paramUnpickleContext.skipTo(l3);
/*      */           }
/*      */           else
/*      */           {
/*  547 */             if (m != 0)
/*      */             {
/*  549 */               for (long l4 = 1L; l4 < paramLong; l4 += 1L)
/*      */               {
/*  551 */                 localARRAY.setIndexOffset(l4, paramUnpickleContext.offset());
/*  552 */                 this.elementType.unpickle80rec(paramUnpickleContext, j, 9, null);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  557 */             for (int n = 1; n < paramLong; n++)
/*  558 */               this.elementType.unpickle80rec(paramUnpickleContext, j, 9, null);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  563 */           paramUnpickleContext.skipTo(localARRAY.getLastOffset());
/*      */         }
/*  565 */         localARRAY.setLastIndexOffset(paramLong, paramUnpickleContext.offset());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  571 */     unpickle80rec_elems(paramUnpickleContext, localARRAY, (int)paramLong, k, paramInt2, paramMap, j);
/*      */ 
/*  574 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   private Object unpickle80rec_elems(UnpickleContext paramUnpickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, int paramInt3, Map paramMap, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*  586 */     int i = ArrayDescriptor.getCacheStyle(paramARRAY) == 1 ? 1 : 0;
/*      */     Object localObject;
/*      */     int j;
/*  590 */     switch (paramInt3)
/*      */     {
/*      */     case 1:
/*  597 */       localObject = new Datum[paramInt2];
/*      */ 
/*  599 */       if (i != 0)
/*      */       {
/*  601 */         for (j = 0; j < paramInt2; j++)
/*      */         {
/*  603 */           paramARRAY.setIndexOffset(paramInt1 + j, paramUnpickleContext.offset());
/*      */ 
/*  605 */           localObject[j] = ((Datum)this.elementType.unpickle80rec(paramUnpickleContext, paramInt4, paramInt3, paramMap));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  611 */       for (j = 0; j < paramInt2; j++)
/*      */       {
/*  613 */         localObject[j] = ((Datum)this.elementType.unpickle80rec(paramUnpickleContext, paramInt4, paramInt3, paramMap));
/*      */       }
/*      */ 
/*  618 */       paramARRAY.setDatumArray(localObject);
/*      */ 
/*  621 */       break;
/*      */     case 2:
/*  625 */       localObject = ArrayDescriptor.makeJavaArray(paramInt2, this.elementType.getTypeCode());
/*      */ 
/*  628 */       if (i != 0)
/*      */       {
/*  630 */         for (j = 0; j < paramInt2; j++)
/*      */         {
/*  632 */           paramARRAY.setIndexOffset(paramInt1 + j, paramUnpickleContext.offset());
/*      */ 
/*  634 */           localObject[j] = this.elementType.unpickle80rec(paramUnpickleContext, paramInt4, paramInt3, paramMap);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  640 */       for (j = 0; j < paramInt2; j++) {
/*  641 */         localObject[j] = this.elementType.unpickle80rec(paramUnpickleContext, paramInt4, paramInt3, paramMap);
/*      */       }
/*      */ 
/*  645 */       paramARRAY.setObjArray(localObject);
/*      */ 
/*  648 */       break;
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*  660 */       if (((this.elementType instanceof OracleTypeNUMBER)) || ((this.elementType instanceof OracleTypeFLOAT)))
/*      */       {
/*  663 */         paramARRAY.setObjArray(OracleTypeNUMBER.unpickle80NativeArray(paramUnpickleContext, 1L, paramInt2, paramInt3, paramInt4));
/*      */       }
/*      */       else
/*      */       {
/*  667 */         DatabaseError.throwSqlException(23);
/*      */       }
/*      */ 
/*  670 */       break;
/*      */     case 3:
/*      */     default:
/*  673 */       DatabaseError.throwSqlException(1);
/*      */     }
/*      */ 
/*  676 */     paramARRAY.setLastIndexOffset(paramInt1 + paramInt2, paramUnpickleContext.offset());
/*      */ 
/*  678 */     return paramARRAY;
/*      */   }
/*      */ 
/*      */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/*  693 */     ARRAY localARRAY = (ARRAY)paramDatum;
/*      */ 
/*  695 */     boolean bool = localARRAY.hasDataSeg();
/*  696 */     int i = 0;
/*  697 */     int j = paramPickleContext.offset() + 2;
/*      */ 
/*  699 */     if (bool)
/*      */     {
/*  701 */       Datum[] arrayOfDatum = localARRAY.getOracleArray();
/*      */ 
/*  704 */       if (this.userCode == 3)
/*      */       {
/*  706 */         if (arrayOfDatum.length > this.maxSize) {
/*  707 */           DatabaseError.throwSqlException(71, null);
/*      */         }
/*      */       }
/*      */ 
/*  711 */       i += paramPickleContext.writeCollImageHeader(arrayOfDatum.length);
/*      */ 
/*  713 */       for (int k = 0; k < arrayOfDatum.length; k++)
/*      */       {
/*  715 */         if (arrayOfDatum[k] == null)
/*  716 */           i += paramPickleContext.writeElementNull();
/*      */         else {
/*  718 */           i += this.elementType.pickle81(paramPickleContext, arrayOfDatum[k]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  726 */     i += paramPickleContext.writeCollImageHeader(localARRAY.getLocator());
/*      */ 
/*  729 */     paramPickleContext.patchImageLen(j, i);
/*      */ 
/*  731 */     return i;
/*      */   }
/*      */ 
/*      */   ARRAY unpickle81(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  740 */     return unpickle81(paramPickleContext, paramARRAY, 1L, -1, paramInt1, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   ARRAY unpickle81(PickleContext paramPickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, int paramInt3, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  752 */     ARRAY localARRAY = paramARRAY;
/*      */ 
/*  754 */     if (localARRAY == null)
/*      */     {
/*  756 */       ArrayDescriptor localArrayDescriptor = createArrayDescriptor();
/*      */ 
/*  758 */       localARRAY = new ARRAY(localArrayDescriptor, (byte[])null, this.connection);
/*      */     }
/*      */ 
/*  761 */     if (unpickle81ImgHeader(paramPickleContext, localARRAY, paramInt2, paramInt3))
/*      */     {
/*  763 */       if ((paramLong == 1L) && (paramInt1 == -1))
/*  764 */         unpickle81ImgBody(paramPickleContext, localARRAY, paramInt3, paramMap);
/*      */       else {
/*  766 */         unpickle81ImgBody(paramPickleContext, localARRAY, paramLong, paramInt1, paramInt3, paramMap);
/*      */       }
/*      */     }
/*      */ 
/*  770 */     return localARRAY;
/*      */   }
/*      */ 
/*      */   boolean unpickle81ImgHeader(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  776 */     int i = 1;
/*      */ 
/*  778 */     if (paramInt1 == 3)
/*      */     {
/*  780 */       paramARRAY.setImage(paramPickleContext.image(), paramPickleContext.absoluteOffset(), 0L);
/*      */     }
/*      */ 
/*  783 */     byte b = paramPickleContext.readByte();
/*      */ 
/*  785 */     if (!PickleContext.is81format(b)) {
/*  786 */       DatabaseError.throwSqlException(1, "Image is not in 8.1 format");
/*      */     }
/*      */ 
/*  789 */     if (!PickleContext.hasPrefix(b)) {
/*  790 */       DatabaseError.throwSqlException(1, "Image has no prefix segment");
/*      */     }
/*      */ 
/*  793 */     if (PickleContext.isCollectionImage_pctx(b))
/*  794 */       i = 1;
/*  795 */     else if (PickleContext.isDegenerateImage_pctx(b))
/*  796 */       i = 0;
/*      */     else {
/*  798 */       DatabaseError.throwSqlException(1, "Image is not a collection image");
/*      */     }
/*      */ 
/*  802 */     paramPickleContext.readByte();
/*      */ 
/*  805 */     if (paramInt1 == 9)
/*      */     {
/*  807 */       paramPickleContext.skipBytes(paramPickleContext.readLength(true) - 2);
/*      */ 
/*  809 */       return false;
/*      */     }
/*  811 */     if (paramInt1 == 3)
/*      */     {
/*  813 */       long l = paramPickleContext.readLength();
/*      */ 
/*  815 */       paramARRAY.setImageLength(l);
/*  816 */       paramPickleContext.skipTo(paramARRAY.getImageOffset() + l);
/*      */ 
/*  818 */       return false;
/*      */     }
/*      */ 
/*  821 */     paramPickleContext.skipLength();
/*      */ 
/*  824 */     int j = paramPickleContext.readLength();
/*      */ 
/*  829 */     paramARRAY.setPrefixFlag(paramPickleContext.readByte());
/*      */ 
/*  831 */     if (paramARRAY.isInline())
/*      */     {
/*  838 */       paramPickleContext.readDataValue(j - 1);
/*      */     }
/*      */     else
/*      */     {
/*  842 */       paramARRAY.setLocator(paramPickleContext.readDataValue(j - 1));
/*      */     }
/*      */ 
/*  845 */     return paramARRAY.isInline();
/*      */   }
/*      */ 
/*      */   void unpickle81ImgBody(PickleContext paramPickleContext, ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  855 */     paramPickleContext.readByte();
/*      */ 
/*  858 */     int i = paramPickleContext.readLength();
/*      */ 
/*  860 */     paramARRAY.setLength(i);
/*      */ 
/*  862 */     if (paramInt2 == 0) {
/*  863 */       return;
/*      */     }
/*      */ 
/*  867 */     int j = (int)getAccessLength(i, paramLong, paramInt1);
/*  868 */     int k = ArrayDescriptor.getCacheStyle(paramARRAY) == 1 ? 1 : 0;
/*      */ 
/*  873 */     if ((paramLong > 1L) && (j > 0))
/*      */     {
/*  875 */       long l1 = paramARRAY.getLastIndex();
/*      */       long l2;
/*  877 */       if (l1 < paramLong)
/*      */       {
/*  879 */         if (l1 > 0L)
/*  880 */           paramPickleContext.skipTo(paramARRAY.getLastOffset());
/*      */         else {
/*  882 */           l1 = 1L;
/*      */         }
/*  884 */         if (k != 0)
/*      */         {
/*  886 */           for (l2 = l1; l2 < paramLong; l2 += 1L)
/*      */           {
/*  888 */             paramARRAY.setIndexOffset(l2, paramPickleContext.offset());
/*  889 */             this.elementType.unpickle81rec(paramPickleContext, 9, null);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  894 */         for (l2 = l1; l2 < paramLong; l2 += 1L) {
/*  895 */           this.elementType.unpickle81rec(paramPickleContext, 9, null);
/*      */         }
/*      */       }
/*  898 */       if (l1 > paramLong)
/*      */       {
/*  900 */         l2 = paramARRAY.getOffset(paramLong);
/*      */ 
/*  902 */         if (l2 != -1L)
/*      */         {
/*  904 */           paramPickleContext.skipTo(l2);
/*      */         }
/*      */         else
/*      */         {
/*  908 */           if (k != 0)
/*      */           {
/*  910 */             for (int m = 1; m < paramLong; m++)
/*      */             {
/*  912 */               paramARRAY.setIndexOffset(m, paramPickleContext.offset());
/*  913 */               this.elementType.unpickle81rec(paramPickleContext, 9, null);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  918 */           for (int n = 1; n < paramLong; n++)
/*  919 */             this.elementType.unpickle81rec(paramPickleContext, 9, null);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  924 */         paramPickleContext.skipTo(paramARRAY.getLastOffset());
/*      */       }
/*  926 */       paramARRAY.setLastIndexOffset(paramLong, paramPickleContext.offset());
/*      */     }
/*      */ 
/*  930 */     unpickle81ImgBodyElements(paramPickleContext, paramARRAY, (int)paramLong, j, paramInt2, paramMap);
/*      */   }
/*      */ 
/*      */   void unpickle81ImgBody(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  940 */     paramPickleContext.readByte();
/*      */ 
/*  943 */     int i = paramPickleContext.readLength();
/*      */ 
/*  945 */     paramARRAY.setLength(i);
/*      */ 
/*  947 */     if (paramInt == 0) {
/*  948 */       return;
/*      */     }
/*      */ 
/*  952 */     unpickle81ImgBodyElements(paramPickleContext, paramARRAY, 1, i, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */   private void unpickle81ImgBodyElements(PickleContext paramPickleContext, ARRAY paramARRAY, int paramInt1, int paramInt2, int paramInt3, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  961 */     int i = ArrayDescriptor.getCacheStyle(paramARRAY) == 1 ? 1 : 0;
/*      */     Object localObject;
/*      */     int j;
/*  965 */     switch (paramInt3)
/*      */     {
/*      */     case 1:
/*  970 */       localObject = new Datum[paramInt2];
/*      */ 
/*  972 */       if (i != 0)
/*      */       {
/*  974 */         for (j = 0; j < paramInt2; j++)
/*      */         {
/*  976 */           paramARRAY.setIndexOffset(paramInt1 + j, paramPickleContext.offset());
/*      */ 
/*  978 */           localObject[j] = ((Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  984 */       for (j = 0; j < paramInt2; j++) {
/*  985 */         localObject[j] = ((Datum)this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap));
/*      */       }
/*      */ 
/*  989 */       paramARRAY.setDatumArray(localObject);
/*      */ 
/*  992 */       break;
/*      */     case 2:
/*  996 */       localObject = ArrayDescriptor.makeJavaArray(paramInt2, this.elementType.getTypeCode());
/*      */ 
/*  999 */       if (i != 0)
/*      */       {
/* 1001 */         for (j = 0; j < paramInt2; j++)
/*      */         {
/* 1003 */           paramARRAY.setIndexOffset(paramInt1 + j, paramPickleContext.offset());
/*      */ 
/* 1005 */           localObject[j] = this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1010 */       for (j = 0; j < paramInt2; j++) {
/* 1011 */         localObject[j] = this.elementType.unpickle81rec(paramPickleContext, paramInt3, paramMap);
/*      */       }
/*      */ 
/* 1014 */       paramARRAY.setObjArray(localObject);
/*      */ 
/* 1017 */       break;
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/* 1029 */       if (((this.elementType instanceof OracleTypeNUMBER)) || ((this.elementType instanceof OracleTypeFLOAT)))
/*      */       {
/* 1032 */         paramARRAY.setObjArray(OracleTypeNUMBER.unpickle81NativeArray(paramPickleContext, 1L, paramInt2, paramInt3));
/*      */       }
/*      */       else
/*      */       {
/* 1036 */         DatabaseError.throwSqlException(23, "This feature is limited to numeric collection");
/*      */       }
/*      */ 
/* 1040 */       break;
/*      */     case 3:
/*      */     default:
/* 1045 */       DatabaseError.throwSqlException(68, "Invalid conversion type " + this.elementType);
/*      */     }
/*      */ 
/* 1052 */     paramARRAY.setLastIndexOffset(paramInt1 + paramInt2, paramPickleContext.offset());
/*      */   }
/*      */ 
/*      */   private synchronized void initCollElemTypeName()
/*      */     throws SQLException
/*      */   {
/* 1061 */     Statement localStatement = this.connection.createStatement();
/* 1062 */     ResultSet localResultSet = localStatement.executeQuery("SELECT ELEM_TYPE_NAME, ELEM_TYPE_OWNER FROM ALL_COLL_TYPES WHERE TYPE_NAME='" + this.sqlName.getSimpleName() + "' AND OWNER='" + this.sqlName.getSchema() + "'");
/*      */ 
/* 1066 */     if (localResultSet.next())
/*      */     {
/* 1068 */       if (this.attrTypeNames == null) {
/* 1069 */         this.attrTypeNames = new String[1];
/*      */       }
/* 1071 */       this.attrTypeNames[0] = (localResultSet.getString(2) + "." + localResultSet.getString(1));
/*      */     }
/*      */     else {
/* 1074 */       DatabaseError.throwSqlException(1);
/*      */     }
/* 1076 */     localResultSet.close();
/* 1077 */     localStatement.close();
/*      */   }
/*      */ 
/*      */   public String getAttributeName(int paramInt) throws SQLException
/*      */   {
/* 1082 */     DatabaseError.throwSqlException(1);
/*      */ 
/* 1084 */     return null;
/*      */   }
/*      */ 
/*      */   public String getAttributeName(int paramInt, boolean paramBoolean) throws SQLException
/*      */   {
/* 1089 */     return getAttributeName(paramInt);
/*      */   }
/*      */ 
/*      */   public String getAttributeType(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1100 */     if (paramInt != 1) {
/* 1101 */       DatabaseError.throwSqlException(68);
/*      */     }
/* 1103 */     if (this.sqlName == null) {
/* 1104 */       getFullName();
/*      */     }
/* 1106 */     if (this.attrTypeNames == null) {
/* 1107 */       initCollElemTypeName();
/*      */     }
/* 1109 */     return this.attrTypeNames[0];
/*      */   }
/*      */ 
/*      */   public String getAttributeType(int paramInt, boolean paramBoolean) throws SQLException
/*      */   {
/* 1114 */     if (paramBoolean) {
/* 1115 */       return getAttributeType(paramInt);
/*      */     }
/*      */ 
/* 1118 */     if (paramInt != 1) {
/* 1119 */       DatabaseError.throwSqlException(68);
/*      */     }
/* 1121 */     if ((this.sqlName != null) && (this.attrTypeNames != null)) {
/* 1122 */       return this.attrTypeNames[0];
/*      */     }
/* 1124 */     return null;
/*      */   }
/*      */ 
/*      */   public int getNumAttrs()
/*      */     throws SQLException
/*      */   {
/* 1130 */     return 0;
/*      */   }
/*      */ 
/*      */   public OracleType getAttrTypeAt(int paramInt) throws SQLException
/*      */   {
/* 1135 */     return null;
/*      */   }
/*      */ 
/*      */   ArrayDescriptor createArrayDescriptor() throws SQLException
/*      */   {
/* 1140 */     return new ArrayDescriptor(this, this.connection);
/*      */   }
/*      */ 
/*      */   ArrayDescriptor createArrayDescriptorWithItsOwnTree() throws SQLException
/*      */   {
/* 1145 */     if (this.descriptor == null)
/*      */     {
/* 1147 */       if ((this.sqlName == null) && (getFullName(false) == null))
/*      */       {
/* 1149 */         this.descriptor = new ArrayDescriptor(this, this.connection);
/*      */       }
/*      */       else
/*      */       {
/* 1153 */         this.descriptor = ArrayDescriptor.createDescriptor(this.sqlName, this.connection);
/*      */       }
/*      */     }
/*      */ 
/* 1157 */     return (ArrayDescriptor)this.descriptor;
/*      */   }
/*      */ 
/*      */   public OracleType getElementType() throws SQLException
/*      */   {
/* 1162 */     return this.elementType;
/*      */   }
/*      */ 
/*      */   public int getUserCode() throws SQLException
/*      */   {
/* 1167 */     return this.userCode;
/*      */   }
/*      */ 
/*      */   public long getMaxLength() throws SQLException
/*      */   {
/* 1172 */     return this.maxSize;
/*      */   }
/*      */ 
/*      */   private long getAccessLength(long paramLong1, long paramLong2, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1178 */     if (paramLong2 > paramLong1) {
/* 1179 */       return 0L;
/*      */     }
/* 1181 */     if (paramInt < 0)
/*      */     {
/* 1183 */       return paramLong1 - paramLong2 + 1L;
/*      */     }
/*      */ 
/* 1187 */     return Math.min(paramLong1 - paramLong2 + 1L, paramInt);
/*      */   }
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1200 */     paramObjectOutputStream.writeInt(this.userCode);
/* 1201 */     paramObjectOutputStream.writeLong(this.maxSize);
/* 1202 */     paramObjectOutputStream.writeObject(this.elementType);
/*      */   }
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1211 */     this.userCode = paramObjectInputStream.readInt();
/* 1212 */     this.maxSize = paramObjectInputStream.readLong();
/* 1213 */     this.elementType = ((OracleType)paramObjectInputStream.readObject());
/*      */   }
/*      */ 
/*      */   public void setConnection(OracleConnection paramOracleConnection) throws SQLException
/*      */   {
/* 1218 */     this.connection = paramOracleConnection;
/*      */ 
/* 1220 */     this.elementType.setConnection(paramOracleConnection);
/*      */   }
/*      */ 
/*      */   public void initMetadataRecursively() throws SQLException
/*      */   {
/* 1225 */     initMetadata(this.connection);
/* 1226 */     if (this.elementType != null) this.elementType.initMetadataRecursively(); 
/*      */   }
/*      */ 
/*      */   public void initChildNamesRecursively(Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1231 */     TypeTreeElement localTypeTreeElement = (TypeTreeElement)paramMap.get(this.sqlName);
/*      */ 
/* 1233 */     if (this.elementType != null)
/*      */     {
/* 1235 */       this.elementType.setNames(localTypeTreeElement.getChildSchemaName(0), localTypeTreeElement.getChildTypeName(0));
/* 1236 */       this.elementType.initChildNamesRecursively(paramMap);
/* 1237 */       this.elementType.cacheDescriptor();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cacheDescriptor() throws SQLException
/*      */   {
/* 1243 */     this.descriptor = ArrayDescriptor.createDescriptor(this);
/*      */   }
/*      */ 
/*      */   public void printXML(PrintWriter paramPrintWriter, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1249 */     for (int i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 1250 */     paramPrintWriter.println("<OracleTypeCOLLECTION sqlName=\"" + this.sqlName + "\" " + " toid=\"" + this.toid + "\" " + ">");
/*      */ 
/* 1254 */     if (this.elementType != null)
/* 1255 */       this.elementType.printXML(paramPrintWriter, paramInt + 1);
/* 1256 */     for (i = 0; i < paramInt; i++) paramPrintWriter.print("  ");
/* 1257 */     paramPrintWriter.println("</OracleTypeCOLLECTION>");
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeCOLLECTION
 * JD-Core Version:    0.6.0
 */