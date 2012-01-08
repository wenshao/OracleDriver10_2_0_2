/*      */ package oracle.jdbc.oracore;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ public final class PickleContext
/*      */ {
/*      */   private PickleOutputStream outStream;
/*      */   byte[] image;
/*      */   int imageOffset;
/*      */   private byte[] lengthBuffer;
/*   46 */   static short KOPI20_LN_ELNL = 255;
/*   47 */   static short KOPI20_LN_5BLN = 254;
/*   48 */   static short KOPI20_LN_ATMN = 253;
/*   49 */   static short KOPI20_LN_IEMN = 252;
/*   50 */   static short KOPI20_LN_MAXV = 245;
/*      */ 
/*   56 */   static short KOPI20_IF_IS81 = 128;
/*   57 */   static short KOPI20_IF_CMSB = 64;
/*   58 */   static short KOPI20_IF_CLSB = 32;
/*   59 */   static short KOPI20_IF_DEGN = 16;
/*   60 */   static short KOPI20_IF_COLL = 8;
/*   61 */   static short KOPI20_IF_NOPS = 4;
/*   62 */   static short KOPI20_IF_ANY = 2;
/*   63 */   static short KOPI20_IF_NONL = 1;
/*      */ 
/*   70 */   static short KOPI20_CF_CMSB = 64;
/*   71 */   static short KOPI20_CF_CLSB = 32;
/*   72 */   static short KOPI20_CF_INDX = 16;
/*   73 */   static short KOPI20_CF_NOLN = 8;
/*      */ 
/*   75 */   static short KOPI20_VERSION = 1;
/*      */   static final byte KOPUP_INLINE_COLL = 1;
/*      */   static final byte KOPUP_TYPEINFO_NONE = 0;
/*      */   static final byte KOPUP_TYPEINFO_TOID = 4;
/*      */   static final byte KOPUP_TYPEINFO_TOBJN = 8;
/*      */   static final byte KOPUP_TYPEINFO_TDS = 12;
/*      */   static final byte KOPUP_VSN_PRESENT = 16;
/* 1012 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*      */ 
/*      */   public PickleContext()
/*      */   {
/*   93 */     this.lengthBuffer = new byte[5];
/*      */   }
/*      */ 
/*      */   public PickleContext(byte[] paramArrayOfByte)
/*      */   {
/*   98 */     this.lengthBuffer = new byte[5];
/*   99 */     this.image = paramArrayOfByte;
/*  100 */     this.imageOffset = 0;
/*      */   }
/*      */ 
/*      */   public PickleContext(byte[] paramArrayOfByte, long paramLong)
/*      */   {
/*  105 */     this.lengthBuffer = new byte[5];
/*  106 */     this.image = paramArrayOfByte;
/*  107 */     this.imageOffset = (int)paramLong;
/*      */   }
/*      */ 
/*      */   public void initStream(int paramInt)
/*      */   {
/*  116 */     this.outStream = new PickleOutputStream(paramInt);
/*      */   }
/*      */ 
/*      */   public void initStream()
/*      */   {
/*  124 */     this.outStream = new PickleOutputStream();
/*      */   }
/*      */ 
/*      */   public int lengthInBytes(int paramInt)
/*      */   {
/*  134 */     return paramInt <= KOPI20_LN_MAXV ? 1 : 5;
/*      */   }
/*      */ 
/*      */   public int writeElementNull()
/*      */     throws SQLException
/*      */   {
/*  143 */     this.outStream.write(KOPI20_LN_ELNL);
/*      */ 
/*  145 */     return 1;
/*      */   }
/*      */ 
/*      */   public int writeAtomicNull()
/*      */     throws SQLException
/*      */   {
/*  154 */     this.outStream.write(KOPI20_LN_ATMN);
/*      */ 
/*  156 */     return 1;
/*      */   }
/*      */ 
/*      */   public int writeImmediatelyEmbeddedElementNull(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  168 */     this.lengthBuffer[0] = (byte)KOPI20_LN_IEMN;
/*  169 */     this.lengthBuffer[1] = paramByte;
/*      */ 
/*  171 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  173 */     return 2;
/*      */   }
/*      */ 
/*      */   public int writeLength(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  186 */     if (paramInt <= KOPI20_LN_MAXV)
/*      */     {
/*  191 */       this.outStream.write((byte)paramInt);
/*      */ 
/*  193 */       return 1;
/*      */     }
/*      */ 
/*  201 */     this.lengthBuffer[0] = (byte)KOPI20_LN_5BLN;
/*  202 */     this.lengthBuffer[1] = (byte)(paramInt >> 24);
/*  203 */     paramInt &= 16777215;
/*  204 */     this.lengthBuffer[2] = (byte)(paramInt >> 16);
/*  205 */     paramInt &= 65535;
/*  206 */     this.lengthBuffer[3] = (byte)(paramInt >> 8);
/*  207 */     paramInt &= 255;
/*  208 */     this.lengthBuffer[4] = (byte)paramInt;
/*      */     try
/*      */     {
/*  212 */       this.outStream.write(this.lengthBuffer);
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  235 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/*  238 */     return 5;
/*      */   }
/*      */ 
/*      */   public int writeLength(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  248 */     if (!paramBoolean) {
/*  249 */       return writeLength(paramInt);
/*      */     }
/*      */ 
/*  254 */     if (paramInt <= KOPI20_LN_MAXV - 1)
/*      */     {
/*  260 */       this.outStream.write((byte)paramInt + 1);
/*      */ 
/*  262 */       return 1;
/*      */     }
/*      */ 
/*  269 */     paramInt += 5;
/*  270 */     this.lengthBuffer[0] = (byte)KOPI20_LN_5BLN;
/*  271 */     this.lengthBuffer[1] = (byte)(paramInt >> 24);
/*  272 */     paramInt &= 16777215;
/*  273 */     this.lengthBuffer[2] = (byte)(paramInt >> 16);
/*  274 */     paramInt &= 65535;
/*  275 */     this.lengthBuffer[3] = (byte)(paramInt >> 8);
/*  276 */     paramInt &= 255;
/*  277 */     this.lengthBuffer[4] = (byte)paramInt;
/*      */     try
/*      */     {
/*  281 */       this.outStream.write(this.lengthBuffer);
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  304 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/*  307 */     return 5;
/*      */   }
/*      */ 
/*      */   public byte[] to5bLengthBytes_pctx(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  321 */     this.lengthBuffer[0] = (byte)KOPI20_LN_5BLN;
/*  322 */     this.lengthBuffer[1] = (byte)(paramInt >> 24);
/*  323 */     paramInt &= 16777215;
/*  324 */     this.lengthBuffer[2] = (byte)(paramInt >> 16);
/*  325 */     paramInt &= 65535;
/*  326 */     this.lengthBuffer[3] = (byte)(paramInt >> 8);
/*  327 */     paramInt &= 255;
/*  328 */     this.lengthBuffer[4] = (byte)paramInt;
/*      */ 
/*  332 */     return this.lengthBuffer;
/*      */   }
/*      */ 
/*      */   public int writeData(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  341 */     this.outStream.write(paramByte);
/*      */ 
/*  343 */     return 1;
/*      */   }
/*      */ 
/*      */   public int writeData(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  354 */       this.outStream.write(paramArrayOfByte);
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  377 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/*  380 */     return paramArrayOfByte.length;
/*      */   }
/*      */ 
/*      */   public void patchImageLen(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  388 */     byte[] arrayOfByte = to5bLengthBytes_pctx(paramInt2);
/*      */ 
/*  390 */     this.outStream.overwrite(paramInt1, arrayOfByte, 0, arrayOfByte.length);
/*      */   }
/*      */ 
/*      */   public int writeImageHeader(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  401 */     return writeImageHeader(KOPI20_LN_MAXV + 1, paramBoolean);
/*      */   }
/*      */ 
/*      */   public int writeOpaqueImageHeader(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  414 */     int i = 2;
/*      */ 
/*  416 */     this.lengthBuffer[0] = (byte)(KOPI20_IF_IS81 | KOPI20_IF_NOPS | KOPI20_IF_NONL);
/*  417 */     this.lengthBuffer[1] = (byte)KOPI20_VERSION;
/*      */ 
/*  419 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  421 */     i += writeLength(paramInt + 2, true);
/*      */ 
/*  423 */     return i;
/*      */   }
/*      */ 
/*      */   public int writeImageHeader(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  436 */     int i = 2;
/*      */ 
/*  438 */     if (paramBoolean)
/*  439 */       this.lengthBuffer[0] = (byte)KOPI20_IF_IS81;
/*      */     else {
/*  441 */       this.lengthBuffer[0] = (byte)(KOPI20_IF_IS81 | KOPI20_IF_NOPS);
/*      */     }
/*  443 */     this.lengthBuffer[1] = (byte)KOPI20_VERSION;
/*      */ 
/*  445 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  447 */     i += writeLength(paramInt);
/*      */ 
/*  449 */     return i;
/*      */   }
/*      */ 
/*      */   public int writeCollImageHeader(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  460 */     return writeCollImageHeader(KOPI20_LN_MAXV + 1, paramInt);
/*      */   }
/*      */ 
/*      */   public int writeCollImageHeader(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  476 */     int i = 5;
/*      */ 
/*  478 */     this.lengthBuffer[0] = (byte)(KOPI20_IF_IS81 | KOPI20_IF_COLL);
/*  479 */     this.lengthBuffer[1] = (byte)KOPI20_VERSION;
/*      */ 
/*  481 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  483 */     i += writeLength(paramInt1);
/*  484 */     this.lengthBuffer[0] = 1;
/*  485 */     this.lengthBuffer[1] = 1;
/*      */ 
/*  487 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  489 */     this.lengthBuffer[0] = 0;
/*      */ 
/*  491 */     this.outStream.write(this.lengthBuffer, 0, 1);
/*      */ 
/*  493 */     i += writeLength(paramInt2);
/*      */ 
/*  495 */     return i;
/*      */   }
/*      */ 
/*      */   public int writeCollImageHeader(byte[] paramArrayOfByte) throws SQLException
/*      */   {
/*  500 */     return writeCollImageHeader(KOPI20_LN_MAXV + 1, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public int writeCollImageHeader(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  509 */     int i = paramArrayOfByte.length;
/*      */ 
/*  512 */     int j = 3 + i;
/*      */ 
/*  514 */     this.lengthBuffer[0] = (byte)(KOPI20_IF_IS81 | KOPI20_IF_DEGN);
/*  515 */     this.lengthBuffer[1] = (byte)KOPI20_VERSION;
/*      */ 
/*  517 */     this.outStream.write(this.lengthBuffer, 0, 2);
/*      */ 
/*  519 */     j += writeLength(paramInt);
/*  520 */     j += writeLength(i + 1);
/*      */ 
/*  522 */     this.lengthBuffer[0] = 0;
/*      */ 
/*  524 */     this.outStream.write(this.lengthBuffer, 0, 1);
/*  525 */     this.outStream.write(paramArrayOfByte, 0, i);
/*      */ 
/*  527 */     return j;
/*      */   }
/*      */ 
/*      */   public byte[] stream2Bytes()
/*      */     throws SQLException
/*      */   {
/*  535 */     return this.outStream.toByteArray();
/*      */   }
/*      */ 
/*      */   public byte readByte()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  547 */       int i = this.image[this.imageOffset];
/*      */       return i; } finally { this.imageOffset += 1; } throw localObject;
/*      */   }
/*      */ 
/*      */   public boolean readAndCheckVersion()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  564 */       int i = (this.image[this.imageOffset] & 0xFF) <= KOPI20_VERSION ? 1 : 0;
/*      */       return i; } finally { this.imageOffset += 1; } throw localObject;
/*      */   }
/*      */ 
/*      */   public int readLength()
/*      */     throws SQLException
/*      */   {
/*  585 */     int i = this.image[this.imageOffset] & 0xFF;
/*      */ 
/*  587 */     if (i > KOPI20_LN_MAXV)
/*      */     {
/*  589 */       if (i == KOPI20_LN_ELNL)
/*      */       {
/*  591 */         DatabaseError.throwSqlException(1, "Invalid null flag read");
/*      */       }
/*      */ 
/*  605 */       i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);
/*      */ 
/*  609 */       this.imageOffset += 5;
/*      */     }
/*      */     else
/*      */     {
/*  613 */       this.imageOffset += 1;
/*      */     }
/*      */ 
/*  617 */     return i;
/*      */   }
/*      */ 
/*      */   public void skipLength() throws SQLException
/*      */   {
/*  622 */     int i = this.image[this.imageOffset] & 0xFF;
/*      */ 
/*  624 */     if (i > KOPI20_LN_MAXV)
/*  625 */       this.imageOffset += 5;
/*      */     else
/*  627 */       this.imageOffset += 1;
/*      */   }
/*      */ 
/*      */   public int readRestOfLength(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  640 */     if ((paramByte & 0xFF) != KOPI20_LN_5BLN) {
/*  641 */       return paramByte & 0xFF;
/*      */     }
/*      */     try
/*      */     {
/*  645 */       int i = (((this.image[this.imageOffset] & 0xFF) * 256 + (this.image[(this.imageOffset + 1)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF);
/*      */       return i; } finally { this.imageOffset += 4; } throw localObject;
/*      */   }
/*      */ 
/*      */   public void skipRestOfLength(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  659 */     if ((paramByte & 0xFF) > KOPI20_LN_MAXV)
/*      */     {
/*  661 */       if ((paramByte & 0xFF) == KOPI20_LN_5BLN)
/*  662 */         this.imageOffset += 4;
/*      */       else
/*  664 */         DatabaseError.throwSqlException(1, "Invalid first length byte");
/*      */     }
/*      */   }
/*      */ 
/*      */   public int readLength(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  676 */     int i = this.image[this.imageOffset] & 0xFF;
/*      */ 
/*  678 */     if (i > KOPI20_LN_MAXV)
/*      */     {
/*  695 */       i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);
/*      */ 
/*  699 */       if (paramBoolean) {
/*  700 */         i -= 5;
/*      */       }
/*  702 */       this.imageOffset += 5;
/*      */     }
/*      */     else
/*      */     {
/*  707 */       if (paramBoolean) {
/*  708 */         i--;
/*      */       }
/*  710 */       this.imageOffset += 1;
/*      */     }
/*      */ 
/*  713 */     return i;
/*      */   }
/*      */ 
/*      */   public byte[] readPrefixSegment()
/*      */     throws SQLException
/*      */   {
/*  726 */     byte[] arrayOfByte = new byte[readLength()];
/*      */ 
/*  728 */     System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*  730 */     this.imageOffset += arrayOfByte.length;
/*      */ 
/*  734 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] readDataValue()
/*      */     throws SQLException
/*      */   {
/*  747 */     int i = this.image[this.imageOffset] & 0xFF;
/*      */ 
/*  750 */     if (i == KOPI20_LN_ELNL)
/*      */     {
/*  752 */       this.imageOffset += 1;
/*      */ 
/*  754 */       return null;
/*      */     }
/*      */ 
/*  757 */     if (i > KOPI20_LN_MAXV)
/*      */     {
/*  759 */       i = (((this.image[(this.imageOffset + 1)] & 0xFF) * 256 + (this.image[(this.imageOffset + 2)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 3)] & 0xFF)) * 256 + (this.image[(this.imageOffset + 4)] & 0xFF);
/*      */ 
/*  763 */       this.imageOffset += 5;
/*      */     }
/*      */     else {
/*  766 */       this.imageOffset += 1;
/*      */     }
/*      */ 
/*  769 */     byte[] arrayOfByte = new byte[i];
/*      */ 
/*  771 */     System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*  773 */     this.imageOffset += arrayOfByte.length;
/*      */ 
/*  777 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] readBytes(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  785 */     byte[] arrayOfByte = new byte[paramInt];
/*      */ 
/*  787 */     System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, paramInt);
/*      */ 
/*  789 */     this.imageOffset += paramInt;
/*      */ 
/*  793 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] read1ByteDataValue()
/*      */     throws SQLException
/*      */   {
/*  807 */     if ((this.image[this.imageOffset] & 0xFF) == KOPI20_LN_ELNL) {
/*  808 */       return null;
/*      */     }
/*      */ 
/*  812 */     byte[] arrayOfByte = new byte[this.image[this.imageOffset] & 0xFF];
/*      */ 
/*  814 */     System.arraycopy(this.image, this.imageOffset + 1, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*  816 */     this.imageOffset += arrayOfByte.length + 1;
/*      */ 
/*  820 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] readDataValue(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  833 */     byte[] arrayOfByte = new byte[readRestOfLength(paramByte)];
/*      */ 
/*  835 */     System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*  837 */     this.imageOffset += arrayOfByte.length;
/*      */ 
/*  841 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public byte[] readDataValue(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  852 */     byte[] arrayOfByte = new byte[paramInt];
/*      */ 
/*  854 */     System.arraycopy(this.image, this.imageOffset, arrayOfByte, 0, paramInt);
/*      */ 
/*  856 */     this.imageOffset += paramInt;
/*      */ 
/*  860 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public void skipDataValue()
/*      */     throws SQLException
/*      */   {
/*  872 */     if ((this.image[this.imageOffset] & 0xFF) == KOPI20_LN_ELNL)
/*  873 */       this.imageOffset += 1;
/*      */     else
/*  875 */       skipBytes(readLength());
/*      */   }
/*      */ 
/*      */   public void skipDataValue(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  885 */     skipBytes(readRestOfLength(paramByte));
/*      */   }
/*      */ 
/*      */   public void skipBytes(int paramInt) throws SQLException
/*      */   {
/*  890 */     if (paramInt > 0)
/*  891 */       this.imageOffset += paramInt;
/*      */   }
/*      */ 
/*      */   public int offset() throws SQLException
/*      */   {
/*  896 */     if (this.outStream != null) {
/*  897 */       return this.outStream.offset();
/*      */     }
/*  899 */     return this.imageOffset;
/*      */   }
/*      */ 
/*      */   public int absoluteOffset() throws SQLException
/*      */   {
/*  904 */     return this.imageOffset;
/*      */   }
/*      */ 
/*      */   public void skipTo(long paramLong) throws SQLException
/*      */   {
/*  909 */     if (paramLong > this.imageOffset)
/*  910 */       this.imageOffset = (int)paramLong;
/*      */   }
/*      */ 
/*      */   public byte[] image() throws SQLException
/*      */   {
/*  915 */     return this.image;
/*      */   }
/*      */ 
/*      */   public static boolean is81format(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  927 */     return (paramByte & 0xFF & KOPI20_IF_IS81) != 0;
/*      */   }
/*      */ 
/*      */   public static boolean isCollectionImage_pctx(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  936 */     return (paramByte & 0xFF & KOPI20_IF_COLL) != 0;
/*      */   }
/*      */ 
/*      */   public static boolean isDegenerateImage_pctx(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  945 */     return (paramByte & 0xFF & KOPI20_IF_DEGN) != 0;
/*      */   }
/*      */ 
/*      */   public static boolean hasPrefix(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  954 */     return (paramByte & 0xFF & KOPI20_IF_NOPS) == 0;
/*      */   }
/*      */ 
/*      */   public static boolean isAtomicNull(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  962 */     return (paramByte & 0xFF) == KOPI20_LN_ATMN;
/*      */   }
/*      */ 
/*      */   public static boolean isImmediatelyEmbeddedNull(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  970 */     return (paramByte & 0xFF) == KOPI20_LN_IEMN;
/*      */   }
/*      */ 
/*      */   public static boolean isElementNull(byte paramByte)
/*      */     throws SQLException
/*      */   {
/*  978 */     return (paramByte & 0xFF) == KOPI20_LN_ELNL;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.PickleContext
 * JD-Core Version:    0.6.0
 */