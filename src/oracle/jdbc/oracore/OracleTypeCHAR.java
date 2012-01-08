/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.CHAR;
/*     */ import oracle.sql.CharacterSet;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeCHAR extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -6899444518695804629L;
/*     */   int form;
/*     */   int charset;
/*     */   int length;
/*     */   int characterSemantic;
/*     */   private transient OracleConnection connection;
/*     */   private short pickleCharaterSetId;
/*     */   private transient CharacterSet pickleCharacterSet;
/*     */   private short pickleNcharCharacterSet;
/*     */   static final int SQLCS_IMPLICIT = 1;
/*     */   static final int SQLCS_NCHAR = 2;
/*     */   static final int SQLCS_EXPLICIT = 3;
/*     */   static final int SQLCS_FLEXIBLE = 4;
/*     */   static final int SQLCS_LIT_NULL = 5;
/* 750 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   protected OracleTypeCHAR()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeCHAR(OracleConnection paramOracleConnection)
/*     */   {
/*  70 */     this.form = 0;
/*  71 */     this.charset = 0;
/*  72 */     this.length = 0;
/*  73 */     this.connection = paramOracleConnection;
/*  74 */     this.pickleCharaterSetId = 0;
/*  75 */     this.pickleNcharCharacterSet = 0;
/*  76 */     this.pickleCharacterSet = null;
/*     */     try
/*     */     {
/*  80 */       this.pickleCharaterSetId = this.connection.getStructAttrCsId();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 102 */       this.pickleCharaterSetId = -1;
/*     */     }
/*     */ 
/* 105 */     this.pickleCharacterSet = CharacterSet.make(this.pickleCharaterSetId);
/*     */   }
/*     */ 
/*     */   protected OracleTypeCHAR(OracleConnection paramOracleConnection, int paramInt)
/*     */   {
/* 110 */     super(paramInt);
/*     */ 
/* 114 */     this.form = 0;
/* 115 */     this.charset = 0;
/* 116 */     this.length = 0;
/* 117 */     this.connection = paramOracleConnection;
/* 118 */     this.pickleCharaterSetId = 0;
/* 119 */     this.pickleNcharCharacterSet = 0;
/* 120 */     this.pickleCharacterSet = null;
/*     */     try
/*     */     {
/* 124 */       this.pickleCharaterSetId = this.connection.getStructAttrCsId();
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 146 */       this.pickleCharaterSetId = -1;
/*     */     }
/*     */ 
/* 149 */     this.pickleCharacterSet = CharacterSet.make(this.pickleCharaterSetId);
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 176 */     if (paramObject == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     CHAR localCHAR = (paramObject instanceof CHAR) ? (CHAR)paramObject : new CHAR(paramObject, this.pickleCharacterSet);
/*     */ 
/* 184 */     return localCHAR;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 208 */     Datum[] arrayOfDatum = null;
/*     */ 
/* 210 */     if (paramObject != null)
/*     */     {
/* 212 */       if (((paramObject instanceof Object[])) && (!(paramObject instanceof char[][]))) {
/* 213 */         return super.toDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */       }
/* 215 */       arrayOfDatum = cArrayToDatumArray(paramObject, paramOracleConnection, paramLong, paramInt);
/*     */     }
/*     */ 
/* 218 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/* 235 */     super.parseTDSrec(paramTDSReader);
/*     */     try
/*     */     {
/* 240 */       this.length = paramTDSReader.readShort();
/* 241 */       this.form = paramTDSReader.readByte();
/* 242 */       this.characterSemantic = (this.form & 0x80);
/* 243 */       this.form &= 127;
/* 244 */       this.charset = paramTDSReader.readShort();
/*     */     }
/*     */     catch (SQLException localSQLException1)
/*     */     {
/* 248 */       DatabaseError.throwSqlException(47, "parseTDS");
/*     */     }
/*     */ 
/* 252 */     if ((this.form != 2) || (this.pickleNcharCharacterSet != 0)) {
/* 253 */       return;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 261 */       this.pickleNcharCharacterSet = this.connection.getStructAttrNCsId();
/*     */     }
/*     */     catch (SQLException localSQLException2)
/*     */     {
/* 283 */       this.pickleNcharCharacterSet = 2000;
/*     */     }
/*     */ 
/* 286 */     this.pickleCharaterSetId = this.pickleNcharCharacterSet;
/* 287 */     this.pickleCharacterSet = CharacterSet.make(this.pickleCharaterSetId);
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 306 */     switch (paramInt1)
/*     */     {
/*     */     case 1:
/* 311 */       if (paramUnpickleContext.isNull(this.nullOffset)) {
/* 312 */         return null;
/*     */       }
/* 314 */       paramUnpickleContext.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
/*     */ 
/* 316 */       if (paramInt2 == 9)
/*     */       {
/* 318 */         paramUnpickleContext.skipBytes(6);
/*     */ 
/* 320 */         return null;
/*     */       }
/*     */ 
/* 324 */       long l = paramUnpickleContext.readLong();
/*     */ 
/* 326 */       if (l == 0L)
/*     */       {
/* 328 */         paramUnpickleContext.skipBytes(2);
/* 329 */         paramUnpickleContext.mark();
/*     */       }
/*     */       else {
/* 332 */         paramUnpickleContext.markAndSkip(l);
/*     */       }
/* 334 */       byte[] arrayOfByte = paramUnpickleContext.readLengthBytes();
/*     */ 
/* 336 */       paramUnpickleContext.reset();
/*     */ 
/* 338 */       return toObject(arrayOfByte, paramInt2, paramMap);
/*     */     case 2:
/* 342 */       if ((paramUnpickleContext.readByte() & 0x1) != 1)
/*     */         break;
/* 344 */       paramUnpickleContext.skipBytes(4);
/*     */ 
/* 346 */       return null;
/*     */     case 3:
/* 353 */       if (paramInt2 == 9)
/*     */       {
/* 355 */         paramUnpickleContext.skipLengthBytes();
/*     */ 
/* 357 */         return null;
/*     */       }
/*     */ 
/* 361 */       return toObject(paramUnpickleContext.readLengthBytes(), paramInt2, paramMap);
/*     */     }
/*     */ 
/* 365 */     DatabaseError.throwSqlException(1, "format=" + paramInt1);
/*     */ 
/* 369 */     return null;
/*     */   }
/*     */ 
/*     */   protected int pickle81(PickleContext paramPickleContext, Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 395 */     CHAR localCHAR = getDbCHAR(paramDatum);
/*     */ 
/* 397 */     if ((this.characterSemantic != 0) && (this.form != 2))
/*     */     {
/* 401 */       if (localCHAR.getString().length() > this.length) {
/* 402 */         DatabaseError.throwSqlException(72, "\"" + localCHAR.getString() + "\"");
/*     */       }
/*     */ 
/*     */     }
/* 409 */     else if (localCHAR.getLength() > this.length) {
/* 410 */       DatabaseError.throwSqlException(72, "\"" + localCHAR.getString() + "\"");
/*     */     }
/*     */ 
/* 414 */     return super.pickle81(paramPickleContext, localCHAR);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 428 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 429 */       return null;
/*     */     }
/*     */ 
/* 433 */     CHAR localCHAR = null;
/*     */ 
/* 435 */     switch (this.form)
/*     */     {
/*     */     case 1:
/*     */     case 2:
/* 443 */       localCHAR = new CHAR(paramArrayOfByte, this.pickleCharacterSet);
/*     */ 
/* 445 */       break;
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/* 452 */       localCHAR = new CHAR(paramArrayOfByte, null);
/*     */     }
/*     */ 
/* 457 */     if (paramInt == 1)
/* 458 */       return localCHAR;
/* 459 */     if (paramInt == 2)
/* 460 */       return localCHAR.stringValue();
/* 461 */     if (paramInt == 3) {
/* 462 */       return paramArrayOfByte;
/*     */     }
/* 464 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 467 */     return null;
/*     */   }
/*     */ 
/*     */   private CHAR getDbCHAR(Datum paramDatum)
/*     */   {
/* 481 */     CHAR localCHAR1 = (CHAR)paramDatum;
/* 482 */     CHAR localCHAR2 = null;
/*     */ 
/* 484 */     if (localCHAR1.getCharacterSet().getOracleId() == this.pickleCharaterSetId)
/*     */     {
/* 486 */       localCHAR2 = localCHAR1;
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 492 */         localCHAR2 = new CHAR(localCHAR1.toString(), this.pickleCharacterSet);
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 515 */         localCHAR2 = localCHAR1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 521 */     return localCHAR2;
/*     */   }
/*     */ 
/*     */   private Datum[] cArrayToDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 552 */     Datum[] arrayOfDatum = null;
/*     */ 
/* 554 */     if (paramObject != null)
/*     */     {
/*     */       Object localObject;
/*     */       int i;
/*     */       int j;
/* 556 */       if ((paramObject instanceof char[][]))
/*     */       {
/* 558 */         localObject = (char[][])paramObject;
/* 559 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 562 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 564 */         for (j = 0; j < i; j++) {
/* 565 */           arrayOfDatum[j] = new CHAR(new String(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/* 568 */       if ((paramObject instanceof boolean[]))
/*     */       {
/* 570 */         localObject = (boolean[])paramObject;
/* 571 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 574 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 576 */         for (j = 0; j < i; j++) {
/* 577 */           arrayOfDatum[j] = new CHAR(new Boolean(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/* 580 */       if ((paramObject instanceof short[]))
/*     */       {
/* 582 */         localObject = (short[])paramObject;
/* 583 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 586 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 590 */         for (j = 0; j < i; j++) {
/* 591 */           arrayOfDatum[j] = new CHAR(new Integer(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/*     */ 
/* 595 */       if ((paramObject instanceof int[]))
/*     */       {
/* 597 */         localObject = (int[])paramObject;
/* 598 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 601 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 603 */         for (j = 0; j < i; j++) {
/* 604 */           arrayOfDatum[j] = new CHAR(new Integer(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/* 607 */       if ((paramObject instanceof long[]))
/*     */       {
/* 609 */         localObject = (long[])paramObject;
/* 610 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 613 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 615 */         for (j = 0; j < i; j++) {
/* 616 */           arrayOfDatum[j] = new CHAR(new Long(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/* 619 */       if ((paramObject instanceof float[]))
/*     */       {
/* 621 */         localObject = (float[])paramObject;
/* 622 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 625 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 627 */         for (j = 0; j < i; j++) {
/* 628 */           arrayOfDatum[j] = new CHAR(new Float(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */       }
/* 631 */       if ((paramObject instanceof double[]))
/*     */       {
/* 633 */         localObject = (double[])paramObject;
/* 634 */         i = (int)(paramInt == -1 ? localObject.length : Math.min(localObject.length - paramLong + 1L, paramInt));
/*     */ 
/* 637 */         arrayOfDatum = new Datum[i];
/*     */ 
/* 639 */         for (j = 0; j < i; j++) {
/* 640 */           arrayOfDatum[j] = new CHAR(new Double(localObject[((int)paramLong + j - 1)]), this.pickleCharacterSet);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 645 */       DatabaseError.throwSqlException(59, paramObject);
/*     */     }
/*     */ 
/* 650 */     return (Datum)arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 655 */     return this.length;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 668 */     paramObjectOutputStream.writeInt(this.form);
/* 669 */     paramObjectOutputStream.writeInt(this.charset);
/* 670 */     paramObjectOutputStream.writeInt(this.length);
/* 671 */     paramObjectOutputStream.writeInt(this.characterSemantic);
/* 672 */     paramObjectOutputStream.writeShort(this.pickleCharaterSetId);
/* 673 */     paramObjectOutputStream.writeShort(this.pickleNcharCharacterSet);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 683 */     this.form = paramObjectInputStream.readInt();
/* 684 */     this.charset = paramObjectInputStream.readInt();
/* 685 */     this.length = paramObjectInputStream.readInt();
/* 686 */     this.characterSemantic = paramObjectInputStream.readInt();
/* 687 */     this.pickleCharaterSetId = paramObjectInputStream.readShort();
/* 688 */     this.pickleNcharCharacterSet = paramObjectInputStream.readShort();
/*     */ 
/* 690 */     if (this.pickleNcharCharacterSet != 0)
/* 691 */       this.pickleCharacterSet = CharacterSet.make(this.pickleNcharCharacterSet);
/*     */     else
/* 693 */       this.pickleCharacterSet = CharacterSet.make(this.pickleCharaterSetId);
/*     */   }
/*     */ 
/*     */   public void setConnection(OracleConnection paramOracleConnection) throws SQLException
/*     */   {
/* 698 */     this.connection = paramOracleConnection;
/*     */   }
/*     */ 
/*     */   public boolean isNCHAR()
/*     */     throws SQLException
/*     */   {
/* 710 */     return this.form == 2;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeCHAR
 * JD-Core Version:    0.6.0
 */