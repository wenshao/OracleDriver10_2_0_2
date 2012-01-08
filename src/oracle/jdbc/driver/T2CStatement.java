/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ 
/*      */ class T2CStatement extends OracleStatement
/*      */ {
/*   34 */   T2CConnection connection = null;
/*   35 */   int userResultSetType = -1;
/*   36 */   int userResultSetConcur = -1;
/*      */ 
/*   42 */   static int T2C_EXTEND_BUFFER = -3;
/*      */ 
/*   55 */   long[] t2cOutput = new long[10];
/*      */ 
/* 1417 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*      */ 
/*      */   T2CStatement(T2CConnection paramT2CConnection, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*   64 */     this(paramT2CConnection, paramInt1, paramInt2, -1, -1);
/*      */ 
/*   71 */     this.connection = paramT2CConnection;
/*      */   }
/*      */ 
/*      */   T2CStatement(T2CConnection paramT2CConnection, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*   81 */     super(paramT2CConnection, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*   87 */     this.userResultSetType = paramInt3;
/*   88 */     this.userResultSetConcur = paramInt4;
/*      */ 
/*   90 */     this.connection = paramT2CConnection;
/*      */   }
/*      */ 
/*      */   static native int t2cParseExecuteDescribe(OracleStatement paramOracleStatement, long paramLong, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, byte[] paramArrayOfByte1, int paramInt4, byte paramByte, int paramInt5, int paramInt6, short[] paramArrayOfShort1, int paramInt7, byte[] paramArrayOfByte2, char[] paramArrayOfChar1, int paramInt8, int paramInt9, short[] paramArrayOfShort2, int paramInt10, int paramInt11, byte[] paramArrayOfByte3, char[] paramArrayOfChar2, int paramInt12, int paramInt13, int[] paramArrayOfInt, short[] paramArrayOfShort3, byte[] paramArrayOfByte4, int paramInt14, int paramInt15, int paramInt16, int paramInt17, boolean paramBoolean5, boolean paramBoolean6, Accessor[] paramArrayOfAccessor, byte[][][] paramArrayOfByte, long[] paramArrayOfLong, byte[] paramArrayOfByte5, int paramInt18, char[] paramArrayOfChar3, int paramInt19, short[] paramArrayOfShort4, int paramInt20, boolean paramBoolean7);
/*      */ 
/*      */   static native int t2cDefineExecuteFetch(OracleStatement paramOracleStatement, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, byte[] paramArrayOfByte1, int paramInt5, byte paramByte, int paramInt6, int paramInt7, short[] paramArrayOfShort1, int paramInt8, byte[] paramArrayOfByte2, char[] paramArrayOfChar1, int paramInt9, int paramInt10, short[] paramArrayOfShort2, byte[] paramArrayOfByte3, int paramInt11, int paramInt12, boolean paramBoolean3, boolean paramBoolean4, Accessor[] paramArrayOfAccessor, byte[][][] paramArrayOfByte, long[] paramArrayOfLong, byte[] paramArrayOfByte4, int paramInt13, char[] paramArrayOfChar2, int paramInt14, short[] paramArrayOfShort3, int paramInt15);
/*      */ 
/*      */   static native int t2cDescribe(long paramLong, short[] paramArrayOfShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */ 
/*      */   static native int t2cDefineFetch(long paramLong, int paramInt1, short[] paramArrayOfShort1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte2, int paramInt4, char[] paramArrayOfChar, int paramInt5, short[] paramArrayOfShort2, int paramInt6, long[] paramArrayOfLong);
/*      */ 
/*      */   static native int t2cFetch(long paramLong, boolean paramBoolean, int paramInt1, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte, int paramInt2, char[] paramArrayOfChar, int paramInt3, short[] paramArrayOfShort, int paramInt4, long[] paramArrayOfLong);
/*      */ 
/*      */   static native int t2cCloseStatement(long paramLong);
/*      */ 
/*      */   static native int t2cEndToEndUpdate(long paramLong, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, byte[] paramArrayOfByte3, int paramInt3, byte[] paramArrayOfByte4, int paramInt4, int paramInt5);
/*      */ 
/*      */   static native int t2cGetRowsDmlReturned(long paramLong);
/*      */ 
/*      */   static native int t2cFetchDmlReturnParams(long paramLong, Accessor[] paramArrayOfAccessor, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort);
/*      */ 
/*      */   String bytes2String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  299 */     byte[] arrayOfByte = new byte[paramInt2];
/*      */ 
/*  301 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/*      */ 
/*  303 */     return this.connection.conversion.CharBytesToString(arrayOfByte, paramInt2);
/*      */   }
/*      */ 
/*      */   void processDescribeData()
/*      */     throws SQLException
/*      */   {
/*  319 */     this.described = true;
/*  320 */     this.describedWithNames = true;
/*      */ 
/*  331 */     if ((this.accessors == null) || (this.numberOfDefinePositions > this.accessors.length)) {
/*  332 */       this.accessors = new Accessor[this.numberOfDefinePositions];
/*      */     }
/*      */ 
/*  348 */     int i = this.connection.queryMetaData1Offset;
/*  349 */     int j = this.connection.queryMetaData2Offset;
/*  350 */     short[] arrayOfShort = this.connection.queryMetaData1;
/*  351 */     byte[] arrayOfByte = this.connection.queryMetaData2;
/*      */ 
/*  353 */     for (int k = 0; k < this.numberOfDefinePositions; )
/*      */     {
/*  356 */       int m = arrayOfShort[(i + 0)];
/*  357 */       int n = arrayOfShort[(i + 1)];
/*  358 */       int i1 = arrayOfShort[(i + 11)];
/*  359 */       boolean bool = arrayOfShort[(i + 2)] != 0;
/*  360 */       int i2 = arrayOfShort[(i + 3)];
/*  361 */       int i3 = arrayOfShort[(i + 4)];
/*  362 */       int i4 = 0;
/*  363 */       int i5 = 0;
/*  364 */       int i6 = 0;
/*  365 */       short s = arrayOfShort[(i + 5)];
/*  366 */       int i7 = arrayOfShort[(i + 6)];
/*  367 */       String str1 = bytes2String(arrayOfByte, j, i7);
/*  368 */       int i8 = arrayOfShort[(i + 12)];
/*  369 */       String str2 = null;
/*  370 */       OracleTypeADT localOracleTypeADT = null;
/*      */ 
/*  372 */       j += i7;
/*      */ 
/*  374 */       if (i8 > 0)
/*      */       {
/*  376 */         str2 = bytes2String(arrayOfByte, j, i8);
/*  377 */         j += i8;
/*  378 */         localOracleTypeADT = new OracleTypeADT(str2, this.connection);
/*  379 */         localOracleTypeADT.tdoCState = ((arrayOfShort[(i + 7)] & 0xFFFF) << 48 | (arrayOfShort[(i + 8)] & 0xFFFF) << 32 | (arrayOfShort[(i + 9)] & 0xFFFF) << 16 | arrayOfShort[(i + 10)] & 0xFFFF);
/*      */       }
/*      */ 
/*  387 */       Object localObject = this.accessors[k];
/*      */ 
/*  389 */       if ((localObject != null) && (!((Accessor)localObject).useForDescribeIfPossible(m, n, bool, i4, i2, i3, i5, i6, s, str2)))
/*      */       {
/*  393 */         localObject = null;
/*      */       }
/*  395 */       if (localObject == null)
/*      */       {
/*  397 */         switch (m)
/*      */         {
/*      */         case 1:
/*  401 */           localObject = new VarcharAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  405 */           if (i1 <= 0) break;
/*  406 */           ((Accessor)localObject).setDisplaySize(i1); break;
/*      */         case 96:
/*  411 */           localObject = new CharAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  415 */           if (i1 <= 0) break;
/*  416 */           ((Accessor)localObject).setDisplaySize(i1); break;
/*      */         case 2:
/*  421 */           localObject = new NumberAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  425 */           break;
/*      */         case 23:
/*  428 */           localObject = new RawAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  432 */           break;
/*      */         case 100:
/*  435 */           localObject = new BinaryFloatAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  439 */           break;
/*      */         case 101:
/*  442 */           localObject = new BinaryDoubleAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  446 */           break;
/*      */         case 8:
/*  449 */           localObject = new LongAccessor(this, k + 1, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  456 */           this.rowPrefetch = 1;
/*      */ 
/*  458 */           break;
/*      */         case 24:
/*  461 */           localObject = new LongRawAccessor(this, k + 1, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  468 */           this.rowPrefetch = 1;
/*      */ 
/*  470 */           break;
/*      */         case 104:
/*  473 */           localObject = new RowidAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  477 */           break;
/*      */         case 102:
/*      */         case 116:
/*  482 */           localObject = new T2CResultSetAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  486 */           break;
/*      */         case 12:
/*  489 */           localObject = new DateAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  493 */           break;
/*      */         case 180:
/*  496 */           localObject = new TimestampAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  500 */           break;
/*      */         case 181:
/*  503 */           localObject = new TimestamptzAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  507 */           break;
/*      */         case 231:
/*  510 */           localObject = new TimestampltzAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  514 */           break;
/*      */         case 182:
/*  517 */           localObject = new IntervalymAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  521 */           break;
/*      */         case 183:
/*  524 */           localObject = new IntervaldsAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  528 */           break;
/*      */         case 112:
/*  531 */           localObject = new ClobAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  535 */           break;
/*      */         case 113:
/*  538 */           localObject = new BlobAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  542 */           break;
/*      */         case 114:
/*  545 */           localObject = new BfileAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  549 */           break;
/*      */         case 109:
/*  552 */           localObject = new NamedTypeAccessor(this, n, bool, i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
/*      */ 
/*  557 */           break;
/*      */         case 111:
/*  560 */           localObject = new RefTypeAccessor(this, n, bool, i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
/*      */ 
/*  565 */           break;
/*      */         default:
/*  571 */           throw new SQLException("Unknown or unimplemented accessor type: " + m);
/*      */         }
/*      */ 
/*  579 */         this.accessors[k] = localObject;
/*      */       }
/*  581 */       else if (localOracleTypeADT != null)
/*      */       {
/*  583 */         ((Accessor)localObject).initMetadata();
/*      */       }
/*      */ 
/*  586 */       ((Accessor)localObject).columnName = str1;
/*      */ 
/*  354 */       k++; i += 13;
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDescribe(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  609 */     if (this.closed) {
/*  610 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  612 */     if (this.described == true)
/*      */     {
/*  614 */       return;
/*      */     }
/*      */ 
/*  617 */     if (!this.isOpen)
/*      */     {
/*  619 */       DatabaseError.throwSqlException(144);
/*      */     }
/*      */ 
/*      */     int i;
/*      */     do
/*      */     {
/*  626 */       i = 0;
/*      */ 
/*  631 */       this.numberOfDefinePositions = t2cDescribe(this.c_state, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.connection.queryMetaData1Size, this.connection.queryMetaData2Size);
/*      */ 
/*  644 */       if (this.numberOfDefinePositions == -1)
/*      */       {
/*  646 */         this.connection.checkError(this.numberOfDefinePositions);
/*      */       }
/*      */ 
/*  650 */       if (this.numberOfDefinePositions != T2C_EXTEND_BUFFER)
/*      */         continue;
/*  652 */       i = 1;
/*      */ 
/*  656 */       this.connection.reallocateQueryMetaData(this.connection.queryMetaData1Size * 2, this.connection.queryMetaData2Size * 2);
/*      */     }
/*      */ 
/*  660 */     while (i != 0);
/*      */ 
/*  662 */     processDescribeData();
/*      */   }
/*      */   void executeForDescribe() throws SQLException {
/*  704 */     this.t2cOutput[0] = 0L;
/*  705 */     this.t2cOutput[2] = 0L;
/*      */ 
/*  707 */     boolean bool1 = !this.described;
/*  708 */     boolean bool2 = false;
/*      */     int i;
/*      */     do {
/*  713 */       i = 0;
/*      */ 
/*  716 */       if (this.connection.endToEndAnyChanged)
/*      */       {
/*  718 */         pushEndToEndValues();
/*      */ 
/*  720 */         this.connection.endToEndAnyChanged = false;
/*      */       }
/*      */ 
/*  724 */       byte[] arrayOfByte = this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals);
/*  725 */       int j = t2cParseExecuteDescribe(this, this.c_state, this.numberOfBindPositions, 0, 0, false, this.needToParse, bool1, bool2, arrayOfByte, arrayOfByte.length, this.sqlKind, this.rowPrefetch, this.batch, this.bindIndicators, this.bindIndicatorOffset, this.bindBytes, this.bindChars, this.bindByteOffset, this.bindCharOffset, this.ibtBindIndicators, this.ibtBindIndicatorOffset, this.ibtBindIndicatorSize, this.ibtBindBytes, this.ibtBindChars, this.ibtBindByteOffset, this.ibtBindCharOffset, this.returnParamMeta, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.connection.queryMetaData1Size, this.connection.queryMetaData2Size, true, true, this.accessors, (byte[][][])null, this.t2cOutput, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.connection.plsqlCompilerWarnings);
/*      */ 
/*  774 */       this.validRows = (int)this.t2cOutput[1];
/*      */ 
/*  776 */       if (j == -1)
/*      */       {
/*  778 */         this.connection.checkError(j);
/*      */       }
/*  780 */       else if (j == T2C_EXTEND_BUFFER)
/*      */       {
/*  782 */         j = this.connection.queryMetaData1Size * 2;
/*      */       }
/*      */ 
/*  787 */       if (this.t2cOutput[3] != 0L)
/*      */       {
/*  789 */         foundPlsqlCompilerWarning();
/*      */       }
/*  791 */       else if (this.t2cOutput[2] != 0L)
/*      */       {
/*  793 */         this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */       }
/*      */ 
/*  798 */       this.connection.endToEndECIDSequenceNumber = (short)(int)this.t2cOutput[4];
/*      */ 
/*  801 */       this.needToParse = false;
/*  802 */       bool2 = true;
/*      */ 
/*  804 */       if (this.sqlKind == 0)
/*      */       {
/*  806 */         this.numberOfDefinePositions = j;
/*      */ 
/*  808 */         if (this.numberOfDefinePositions <= this.connection.queryMetaData1Size)
/*      */           continue;
/*  810 */         i = 1;
/*  811 */         bool2 = true;
/*      */ 
/*  814 */         this.connection.reallocateQueryMetaData(this.numberOfDefinePositions, this.numberOfDefinePositions * 8);
/*      */       }
/*      */       else
/*      */       {
/*  821 */         this.numberOfDefinePositions = 0;
/*  822 */         this.validRows = j;
/*      */       }
/*      */     }
/*  825 */     while (i != 0);
/*      */ 
/*  827 */     processDescribeData();
/*      */   }
/*      */ 
/*      */   void pushEndToEndValues()
/*      */     throws SQLException
/*      */   {
/*  839 */     T2CConnection localT2CConnection = this.connection;
/*  840 */     byte[] arrayOfByte1 = new byte[0];
/*  841 */     byte[] arrayOfByte2 = new byte[0];
/*  842 */     byte[] arrayOfByte3 = new byte[0];
/*  843 */     byte[] arrayOfByte4 = new byte[0];
/*      */ 
/*  845 */     if (localT2CConnection.endToEndValues != null)
/*      */     {
/*      */       String str;
/*  847 */       if (localT2CConnection.endToEndHasChanged[0] != 0)
/*      */       {
/*  849 */         str = localT2CConnection.endToEndValues[0];
/*      */ 
/*  851 */         if (str != null) {
/*  852 */           arrayOfByte1 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  855 */         localT2CConnection.endToEndHasChanged[0] = false;
/*      */       }
/*      */ 
/*  858 */       if (localT2CConnection.endToEndHasChanged[1] != 0)
/*      */       {
/*  860 */         str = localT2CConnection.endToEndValues[1];
/*      */ 
/*  862 */         if (str != null) {
/*  863 */           arrayOfByte2 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  866 */         localT2CConnection.endToEndHasChanged[1] = false;
/*      */       }
/*      */ 
/*  869 */       if (localT2CConnection.endToEndHasChanged[2] != 0)
/*      */       {
/*  871 */         str = localT2CConnection.endToEndValues[2];
/*      */ 
/*  873 */         if (str != null) {
/*  874 */           arrayOfByte3 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  877 */         localT2CConnection.endToEndHasChanged[2] = false;
/*      */       }
/*      */ 
/*  880 */       if (localT2CConnection.endToEndHasChanged[3] != 0)
/*      */       {
/*  882 */         str = localT2CConnection.endToEndValues[3];
/*      */ 
/*  884 */         if (str != null) {
/*  885 */           arrayOfByte4 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  888 */         localT2CConnection.endToEndHasChanged[3] = false;
/*      */       }
/*      */ 
/*  891 */       t2cEndToEndUpdate(this.c_state, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, localT2CConnection.endToEndECIDSequenceNumber);
/*      */     }
/*      */   }
/*      */ 
/*      */   void executeForRows(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  949 */     if (this.connection.endToEndAnyChanged)
/*      */     {
/*  951 */       pushEndToEndValues();
/*      */ 
/*  953 */       this.connection.endToEndAnyChanged = false;
/*      */     }
/*      */ 
/*  957 */     if (!paramBoolean)
/*      */     {
/*  965 */       if (this.numberOfDefinePositions > 0)
/*      */       {
/*  967 */         doDefineExecuteFetch();
/*      */       }
/*      */       else
/*      */       {
/*  971 */         executeForDescribe();
/*      */       }
/*      */     }
/*  974 */     else if (this.numberOfDefinePositions > 0) {
/*  975 */       doDefineFetch();
/*      */     }
/*      */ 
/*  979 */     this.needToPrepareDefineBuffer = false;
/*      */   }
/*      */ 
/*      */   void setupForDefine()
/*      */     throws SQLException
/*      */   {
/*  990 */     if (this.numberOfDefinePositions > this.connection.queryMetaData1Size)
/*      */     {
/*  993 */       this.connection.reallocateQueryMetaData(this.connection.queryMetaData1Size * 2, this.connection.queryMetaData2Size * 2 * 8);
/*      */     }
/*      */ 
/*  998 */     short[] arrayOfShort = this.connection.queryMetaData1;
/*  999 */     int i = this.connection.queryMetaData1Offset;
/*      */ 
/* 1002 */     for (int j = 0; j < this.numberOfDefinePositions; )
/*      */     {
/* 1005 */       Accessor localAccessor = this.accessors[j];
/*      */ 
/* 1007 */       if (localAccessor == null) {
/* 1008 */         DatabaseError.throwSqlException(21);
/*      */       }
/* 1010 */       arrayOfShort[(i + 0)] = (short)localAccessor.defineType;
/*      */ 
/* 1012 */       arrayOfShort[(i + 11)] = (short)localAccessor.charLength;
/*      */ 
/* 1014 */       arrayOfShort[(i + 1)] = (short)localAccessor.byteLength;
/*      */ 
/* 1016 */       arrayOfShort[(i + 5)] = localAccessor.formOfUse;
/*      */ 
/* 1019 */       if (localAccessor.internalOtype != null)
/*      */       {
/* 1021 */         long l = ((OracleTypeADT)localAccessor.internalOtype).getTdoCState();
/*      */ 
/* 1024 */         arrayOfShort[(i + 7)] = (short)(int)((l & 0x0) >> 48);
/*      */ 
/* 1026 */         arrayOfShort[(i + 8)] = (short)(int)((l & 0x0) >> 32);
/*      */ 
/* 1028 */         arrayOfShort[(i + 9)] = (short)(int)((l & 0xFFFF0000) >> 16);
/*      */ 
/* 1030 */         arrayOfShort[(i + 10)] = (short)(int)(l & 0xFFFF);
/*      */       }
/* 1003 */       j++; i += 13;
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDefineFetch()
/*      */     throws SQLException
/*      */   {
/* 1038 */     if (!this.needToPrepareDefineBuffer) {
/* 1039 */       throw new Error("doDefineFetch called when needToPrepareDefineBuffer=false " + this.sqlObject.getSql(this.processEscapes, this.convertNcharLiterals));
/*      */     }
/*      */ 
/* 1042 */     setupForDefine();
/*      */ 
/* 1044 */     this.t2cOutput[2] = 0L;
/* 1045 */     this.validRows = t2cDefineFetch(this.c_state, this.rowPrefetch, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.accessors, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.t2cOutput);
/*      */ 
/* 1054 */     if (this.validRows == -1) {
/* 1055 */       this.connection.checkError(this.validRows);
/*      */     }
/*      */ 
/* 1058 */     if (this.t2cOutput[2] != 0L)
/*      */     {
/* 1060 */       this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDefineExecuteFetch()
/*      */     throws SQLException
/*      */   {
/* 1071 */     short[] arrayOfShort = null;
/*      */ 
/* 1073 */     if ((this.needToPrepareDefineBuffer) || (this.needToParse))
/*      */     {
/* 1075 */       setupForDefine();
/*      */ 
/* 1077 */       arrayOfShort = this.connection.queryMetaData1;
/*      */     }
/*      */ 
/* 1080 */     this.t2cOutput[0] = 0L;
/* 1081 */     this.t2cOutput[2] = 0L;
/*      */ 
/* 1083 */     byte[] arrayOfByte = this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals);
/*      */ 
/* 1085 */     this.validRows = t2cDefineExecuteFetch(this, this.c_state, this.numberOfDefinePositions, this.numberOfBindPositions, 0, 0, false, this.needToParse, arrayOfByte, arrayOfByte.length, this.sqlKind, this.rowPrefetch, this.batch, this.bindIndicators, this.bindIndicatorOffset, this.bindBytes, this.bindChars, this.bindByteOffset, this.bindCharOffset, arrayOfShort, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, true, true, this.accessors, (byte[][][])null, this.t2cOutput, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset);
/*      */ 
/* 1121 */     if (this.validRows == -1) {
/* 1122 */       this.connection.checkError(this.validRows);
/*      */     }
/* 1124 */     if (this.t2cOutput[2] != 0L) {
/* 1125 */       this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */     }
/*      */ 
/* 1130 */     this.connection.endToEndECIDSequenceNumber = (short)(int)this.t2cOutput[4];
/*      */ 
/* 1132 */     this.needToParse = false;
/*      */   }
/*      */ 
/*      */   void fetch()
/*      */     throws SQLException
/*      */   {
/* 1177 */     if (this.numberOfDefinePositions > 0)
/*      */     {
/* 1179 */       if (this.needToPrepareDefineBuffer) {
/* 1180 */         doDefineFetch();
/*      */       }
/*      */       else {
/* 1183 */         this.t2cOutput[2] = 0L;
/* 1184 */         this.validRows = t2cFetch(this.c_state, this.needToPrepareDefineBuffer, this.rowPrefetch, this.accessors, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.t2cOutput);
/*      */ 
/* 1190 */         if (this.validRows == -1) {
/* 1191 */           this.connection.checkError(this.validRows);
/*      */         }
/* 1193 */         if (this.t2cOutput[2] != 0L)
/*      */         {
/* 1195 */           this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void doClose()
/*      */     throws SQLException
/*      */   {
/* 1233 */     if (this.defineBytes != null)
/*      */     {
/* 1235 */       this.defineBytes = null;
/* 1236 */       this.accessorByteOffset = 0;
/*      */     }
/*      */ 
/* 1239 */     if (this.defineChars != null)
/*      */     {
/* 1241 */       this.defineChars = null;
/* 1242 */       this.accessorCharOffset = 0;
/*      */     }
/*      */ 
/* 1245 */     if (this.defineIndicators != null)
/*      */     {
/* 1247 */       this.defineIndicators = null;
/* 1248 */       this.accessorShortOffset = 0;
/*      */     }
/*      */ 
/* 1252 */     int i = t2cCloseStatement(this.c_state);
/*      */ 
/* 1254 */     if (i != 0) {
/* 1255 */       this.connection.checkError(i);
/*      */     }
/* 1257 */     this.t2cOutput = null;
/*      */   }
/*      */ 
/*      */   void closeQuery()
/*      */     throws SQLException
/*      */   {
/* 1279 */     if (this.streamList != null)
/*      */     {
/* 1281 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1285 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException)
/*      */         {
/* 1289 */           DatabaseError.throwSqlException(localIOException);
/*      */         }
/*      */ 
/* 1292 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*      */     Object localObject;
/* 1314 */     if (paramInt1 == 109)
/*      */     {
/* 1316 */       if (paramString == null) {
/* 1317 */         if (paramBoolean) {
/* 1318 */           DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */         }
/*      */         else {
/* 1321 */           DatabaseError.throwSqlException(60, "Unable to resolve type \"null\"");
/*      */         }
/*      */       }
/* 1324 */       localObject = new T2CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, paramInt3 - 1);
/*      */ 
/* 1327 */       ((Accessor)localObject).initMetadata();
/*      */ 
/* 1329 */       return localObject;
/*      */     }
/* 1331 */     if ((paramInt1 == 116) || (paramInt1 == 102))
/*      */     {
/* 1334 */       if ((paramBoolean) && (paramString != null)) {
/* 1335 */         DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */       }
/*      */ 
/* 1338 */       localObject = new T2CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
/*      */ 
/* 1341 */       return localObject;
/*      */     }
/*      */ 
/* 1344 */     return (Accessor)super.allocateAccessor(paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   void closeUsedStreams(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1350 */     while ((this.nextStream != null) && (this.nextStream.columnIndex < paramInt))
/*      */     {
/*      */       try
/*      */       {
/* 1357 */         this.nextStream.close();
/*      */       }
/*      */       catch (IOException localIOException1)
/*      */       {
/* 1361 */         DatabaseError.throwSqlException(localIOException1);
/*      */       }
/*      */ 
/* 1364 */       this.nextStream = this.nextStream.nextStream;
/*      */     }
/*      */ 
/* 1367 */     if (this.nextStream != null)
/*      */       try
/*      */       {
/* 1370 */         this.nextStream.needBytes();
/*      */       }
/*      */       catch (IOException localIOException2)
/*      */       {
/* 1374 */         DatabaseError.throwSqlException(localIOException2);
/*      */       }
/*      */   }
/*      */ 
/*      */   void fetchDmlReturnParams()
/*      */     throws SQLException
/*      */   {
/* 1381 */     this.rowsDmlReturned = t2cGetRowsDmlReturned(this.c_state);
/*      */ 
/* 1383 */     if (this.rowsDmlReturned != 0)
/*      */     {
/* 1385 */       allocateDmlReturnStorage();
/*      */ 
/* 1387 */       int i = t2cFetchDmlReturnParams(this.c_state, this.returnParamAccessors, this.returnParamBytes, this.returnParamChars, this.returnParamIndicators);
/*      */     }
/*      */ 
/* 1396 */     this.returnParamsFetched = true;
/*      */   }
/*      */ 
/*      */   void initializeIndicatorSubRange()
/*      */   {
/* 1408 */     this.bindIndicatorSubRange = (this.numberOfBindPositions * 5);
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CStatement
 * JD-Core Version:    0.6.0
 */