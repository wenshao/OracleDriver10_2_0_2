/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ 
/*      */ class T2CPreparedStatement extends OraclePreparedStatement
/*      */ {
/*   39 */   T2CConnection connection = null;
/*   40 */   int userResultSetType = -1;
/*   41 */   int userResultSetConcur = -1;
/*      */ 
/*   47 */   static int T2C_EXTEND_BUFFER = -3;
/*      */ 
/*   60 */   long[] t2cOutput = new long[10];
/*      */ 
/* 1286 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:30_PST_2006";
/*      */ 
/*      */   T2CPreparedStatement(T2CConnection paramT2CConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*   69 */     super(paramT2CConnection, paramString, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*   72 */     this.userResultSetType = paramInt3;
/*   73 */     this.userResultSetConcur = paramInt4;
/*      */ 
/*   76 */     this.connection = paramT2CConnection;
/*      */   }
/*      */ 
/*      */   String bytes2String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*   92 */     byte[] arrayOfByte = new byte[paramInt2];
/*      */ 
/*   94 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/*      */ 
/*   96 */     return this.connection.conversion.CharBytesToString(arrayOfByte, paramInt2);
/*      */   }
/*      */ 
/*      */   void processDescribeData()
/*      */     throws SQLException
/*      */   {
/*  112 */     this.described = true;
/*  113 */     this.describedWithNames = true;
/*      */ 
/*  124 */     if ((this.accessors == null) || (this.numberOfDefinePositions > this.accessors.length)) {
/*  125 */       this.accessors = new Accessor[this.numberOfDefinePositions];
/*      */     }
/*      */ 
/*  141 */     int i = this.connection.queryMetaData1Offset;
/*  142 */     int j = this.connection.queryMetaData2Offset;
/*  143 */     short[] arrayOfShort = this.connection.queryMetaData1;
/*  144 */     byte[] arrayOfByte = this.connection.queryMetaData2;
/*      */ 
/*  146 */     for (int k = 0; k < this.numberOfDefinePositions; )
/*      */     {
/*  149 */       int m = arrayOfShort[(i + 0)];
/*  150 */       int n = arrayOfShort[(i + 1)];
/*  151 */       int i1 = arrayOfShort[(i + 11)];
/*  152 */       boolean bool = arrayOfShort[(i + 2)] != 0;
/*  153 */       int i2 = arrayOfShort[(i + 3)];
/*  154 */       int i3 = arrayOfShort[(i + 4)];
/*  155 */       int i4 = 0;
/*  156 */       int i5 = 0;
/*  157 */       int i6 = 0;
/*  158 */       short s = arrayOfShort[(i + 5)];
/*  159 */       int i7 = arrayOfShort[(i + 6)];
/*  160 */       String str1 = bytes2String(arrayOfByte, j, i7);
/*  161 */       int i8 = arrayOfShort[(i + 12)];
/*  162 */       String str2 = null;
/*  163 */       OracleTypeADT localOracleTypeADT = null;
/*      */ 
/*  165 */       j += i7;
/*      */ 
/*  167 */       if (i8 > 0)
/*      */       {
/*  169 */         str2 = bytes2String(arrayOfByte, j, i8);
/*  170 */         j += i8;
/*  171 */         localOracleTypeADT = new OracleTypeADT(str2, this.connection);
/*  172 */         localOracleTypeADT.tdoCState = ((arrayOfShort[(i + 7)] & 0xFFFF) << 48 | (arrayOfShort[(i + 8)] & 0xFFFF) << 32 | (arrayOfShort[(i + 9)] & 0xFFFF) << 16 | arrayOfShort[(i + 10)] & 0xFFFF);
/*      */       }
/*      */ 
/*  180 */       Object localObject = this.accessors[k];
/*      */ 
/*  182 */       if ((localObject != null) && (!((Accessor)localObject).useForDescribeIfPossible(m, n, bool, i4, i2, i3, i5, i6, s, str2)))
/*      */       {
/*  186 */         localObject = null;
/*      */       }
/*  188 */       if (localObject == null)
/*      */       {
/*  190 */         switch (m)
/*      */         {
/*      */         case 1:
/*  194 */           localObject = new VarcharAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  198 */           if (i1 <= 0) break;
/*  199 */           ((Accessor)localObject).setDisplaySize(i1); break;
/*      */         case 96:
/*  204 */           localObject = new CharAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  208 */           if (i1 <= 0) break;
/*  209 */           ((Accessor)localObject).setDisplaySize(i1); break;
/*      */         case 2:
/*  214 */           localObject = new NumberAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  218 */           break;
/*      */         case 23:
/*  221 */           localObject = new RawAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  225 */           break;
/*      */         case 100:
/*  228 */           localObject = new BinaryFloatAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  232 */           break;
/*      */         case 101:
/*  235 */           localObject = new BinaryDoubleAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  239 */           break;
/*      */         case 8:
/*  242 */           localObject = new LongAccessor(this, k + 1, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  249 */           this.rowPrefetch = 1;
/*      */ 
/*  251 */           break;
/*      */         case 24:
/*  254 */           localObject = new LongRawAccessor(this, k + 1, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  261 */           this.rowPrefetch = 1;
/*      */ 
/*  263 */           break;
/*      */         case 104:
/*  266 */           localObject = new RowidAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  270 */           break;
/*      */         case 102:
/*      */         case 116:
/*  275 */           localObject = new T2CResultSetAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  279 */           break;
/*      */         case 12:
/*  282 */           localObject = new DateAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  286 */           break;
/*      */         case 180:
/*  289 */           localObject = new TimestampAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  293 */           break;
/*      */         case 181:
/*  296 */           localObject = new TimestamptzAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  300 */           break;
/*      */         case 231:
/*  303 */           localObject = new TimestampltzAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  307 */           break;
/*      */         case 182:
/*  310 */           localObject = new IntervalymAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  314 */           break;
/*      */         case 183:
/*  317 */           localObject = new IntervaldsAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  321 */           break;
/*      */         case 112:
/*  324 */           localObject = new ClobAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  328 */           break;
/*      */         case 113:
/*  331 */           localObject = new BlobAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  335 */           break;
/*      */         case 114:
/*  338 */           localObject = new BfileAccessor(this, n, bool, i4, i2, i3, i5, i6, s);
/*      */ 
/*  342 */           break;
/*      */         case 109:
/*  345 */           localObject = new NamedTypeAccessor(this, n, bool, i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
/*      */ 
/*  350 */           break;
/*      */         case 111:
/*  353 */           localObject = new RefTypeAccessor(this, n, bool, i4, i2, i3, i5, i6, s, str2, localOracleTypeADT);
/*      */ 
/*  358 */           break;
/*      */         default:
/*  364 */           throw new SQLException("Unknown or unimplemented accessor type: " + m);
/*      */         }
/*      */ 
/*  372 */         this.accessors[k] = localObject;
/*      */       }
/*  374 */       else if (localOracleTypeADT != null)
/*      */       {
/*  376 */         ((Accessor)localObject).initMetadata();
/*      */       }
/*      */ 
/*  379 */       ((Accessor)localObject).columnName = str1;
/*      */ 
/*  147 */       k++; i += 13;
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDescribe(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  402 */     if (this.closed) {
/*  403 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  405 */     if (this.described == true)
/*      */     {
/*  407 */       return;
/*      */     }
/*      */ 
/*  410 */     if (!this.isOpen)
/*      */     {
/*  412 */       DatabaseError.throwSqlException(144);
/*      */     }
/*      */ 
/*      */     int i;
/*      */     do
/*      */     {
/*  419 */       i = 0;
/*      */ 
/*  424 */       this.numberOfDefinePositions = T2CStatement.t2cDescribe(this.c_state, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.connection.queryMetaData1Size, this.connection.queryMetaData2Size);
/*      */ 
/*  437 */       if (this.numberOfDefinePositions == -1)
/*      */       {
/*  439 */         this.connection.checkError(this.numberOfDefinePositions);
/*      */       }
/*      */ 
/*  443 */       if (this.numberOfDefinePositions != T2C_EXTEND_BUFFER)
/*      */         continue;
/*  445 */       i = 1;
/*      */ 
/*  449 */       this.connection.reallocateQueryMetaData(this.connection.queryMetaData1Size * 2, this.connection.queryMetaData2Size * 2);
/*      */     }
/*      */ 
/*  453 */     while (i != 0);
/*      */ 
/*  455 */     processDescribeData();
/*      */   }
/*      */   void executeForDescribe() throws SQLException {
/*  497 */     this.t2cOutput[0] = 0L;
/*  498 */     this.t2cOutput[2] = 0L;
/*      */ 
/*  500 */     boolean bool1 = !this.described;
/*  501 */     boolean bool2 = false;
/*      */     int i;
/*      */     do {
/*  506 */       i = 0;
/*      */ 
/*  509 */       if (this.connection.endToEndAnyChanged)
/*      */       {
/*  511 */         pushEndToEndValues();
/*      */ 
/*  513 */         this.connection.endToEndAnyChanged = false;
/*      */       }
/*      */ 
/*  517 */       byte[] arrayOfByte = this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals);
/*  518 */       int j = T2CStatement.t2cParseExecuteDescribe(this, this.c_state, this.numberOfBindPositions, this.numberOfBindRowsAllocated, this.firstRowInBatch, false, this.needToParse, bool1, bool2, arrayOfByte, arrayOfByte.length, this.sqlKind, this.rowPrefetch, this.batch, this.bindIndicators, this.bindIndicatorOffset, this.bindBytes, this.bindChars, this.bindByteOffset, this.bindCharOffset, this.ibtBindIndicators, this.ibtBindIndicatorOffset, this.ibtBindIndicatorSize, this.ibtBindBytes, this.ibtBindChars, this.ibtBindByteOffset, this.ibtBindCharOffset, this.returnParamMeta, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.connection.queryMetaData1Size, this.connection.queryMetaData2Size, this.preparedAllBinds, this.preparedCharBinds, this.accessors, this.parameterDatum, this.t2cOutput, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.connection.plsqlCompilerWarnings);
/*      */ 
/*  567 */       this.validRows = (int)this.t2cOutput[1];
/*      */ 
/*  569 */       if (j == -1)
/*      */       {
/*  571 */         this.connection.checkError(j);
/*      */       }
/*  573 */       else if (j == T2C_EXTEND_BUFFER)
/*      */       {
/*  575 */         j = this.connection.queryMetaData1Size * 2;
/*      */       }
/*      */ 
/*  580 */       if (this.t2cOutput[3] != 0L)
/*      */       {
/*  582 */         foundPlsqlCompilerWarning();
/*      */       }
/*  584 */       else if (this.t2cOutput[2] != 0L)
/*      */       {
/*  586 */         this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */       }
/*      */ 
/*  591 */       this.connection.endToEndECIDSequenceNumber = (short)(int)this.t2cOutput[4];
/*      */ 
/*  594 */       this.needToParse = false;
/*  595 */       bool2 = true;
/*      */ 
/*  597 */       if (this.sqlKind == 0)
/*      */       {
/*  599 */         this.numberOfDefinePositions = j;
/*      */ 
/*  601 */         if (this.numberOfDefinePositions <= this.connection.queryMetaData1Size)
/*      */           continue;
/*  603 */         i = 1;
/*  604 */         bool2 = true;
/*      */ 
/*  607 */         this.connection.reallocateQueryMetaData(this.numberOfDefinePositions, this.numberOfDefinePositions * 8);
/*      */       }
/*      */       else
/*      */       {
/*  614 */         this.numberOfDefinePositions = 0;
/*  615 */         this.validRows = j;
/*      */       }
/*      */     }
/*  618 */     while (i != 0);
/*      */ 
/*  620 */     processDescribeData();
/*      */   }
/*      */ 
/*      */   void pushEndToEndValues()
/*      */     throws SQLException
/*      */   {
/*  632 */     T2CConnection localT2CConnection = this.connection;
/*  633 */     byte[] arrayOfByte1 = new byte[0];
/*  634 */     byte[] arrayOfByte2 = new byte[0];
/*  635 */     byte[] arrayOfByte3 = new byte[0];
/*  636 */     byte[] arrayOfByte4 = new byte[0];
/*      */ 
/*  638 */     if (localT2CConnection.endToEndValues != null)
/*      */     {
/*      */       String str;
/*  640 */       if (localT2CConnection.endToEndHasChanged[0] != 0)
/*      */       {
/*  642 */         str = localT2CConnection.endToEndValues[0];
/*      */ 
/*  644 */         if (str != null) {
/*  645 */           arrayOfByte1 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  648 */         localT2CConnection.endToEndHasChanged[0] = false;
/*      */       }
/*      */ 
/*  651 */       if (localT2CConnection.endToEndHasChanged[1] != 0)
/*      */       {
/*  653 */         str = localT2CConnection.endToEndValues[1];
/*      */ 
/*  655 */         if (str != null) {
/*  656 */           arrayOfByte2 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  659 */         localT2CConnection.endToEndHasChanged[1] = false;
/*      */       }
/*      */ 
/*  662 */       if (localT2CConnection.endToEndHasChanged[2] != 0)
/*      */       {
/*  664 */         str = localT2CConnection.endToEndValues[2];
/*      */ 
/*  666 */         if (str != null) {
/*  667 */           arrayOfByte3 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  670 */         localT2CConnection.endToEndHasChanged[2] = false;
/*      */       }
/*      */ 
/*  673 */       if (localT2CConnection.endToEndHasChanged[3] != 0)
/*      */       {
/*  675 */         str = localT2CConnection.endToEndValues[3];
/*      */ 
/*  677 */         if (str != null) {
/*  678 */           arrayOfByte4 = DBConversion.stringToDriverCharBytes(str, localT2CConnection.m_clientCharacterSet);
/*      */         }
/*      */ 
/*  681 */         localT2CConnection.endToEndHasChanged[3] = false;
/*      */       }
/*      */ 
/*  684 */       T2CStatement.t2cEndToEndUpdate(this.c_state, arrayOfByte1, arrayOfByte1.length, arrayOfByte2, arrayOfByte2.length, arrayOfByte3, arrayOfByte3.length, arrayOfByte4, arrayOfByte4.length, localT2CConnection.endToEndECIDSequenceNumber);
/*      */     }
/*      */   }
/*      */ 
/*      */   void executeForRows(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  742 */     if (this.connection.endToEndAnyChanged)
/*      */     {
/*  744 */       pushEndToEndValues();
/*      */ 
/*  746 */       this.connection.endToEndAnyChanged = false;
/*      */     }
/*      */ 
/*  750 */     if (!paramBoolean)
/*      */     {
/*  758 */       if (this.numberOfDefinePositions > 0)
/*      */       {
/*  760 */         doDefineExecuteFetch();
/*      */       }
/*      */       else
/*      */       {
/*  764 */         executeForDescribe();
/*      */       }
/*      */     }
/*  767 */     else if (this.numberOfDefinePositions > 0) {
/*  768 */       doDefineFetch();
/*      */     }
/*      */ 
/*  772 */     this.needToPrepareDefineBuffer = false;
/*      */   }
/*      */ 
/*      */   void setupForDefine()
/*      */     throws SQLException
/*      */   {
/*  783 */     if (this.numberOfDefinePositions > this.connection.queryMetaData1Size)
/*      */     {
/*  786 */       this.connection.reallocateQueryMetaData(this.connection.queryMetaData1Size * 2, this.connection.queryMetaData2Size * 2 * 8);
/*      */     }
/*      */ 
/*  791 */     short[] arrayOfShort = this.connection.queryMetaData1;
/*  792 */     int i = this.connection.queryMetaData1Offset;
/*      */ 
/*  795 */     for (int j = 0; j < this.numberOfDefinePositions; )
/*      */     {
/*  798 */       Accessor localAccessor = this.accessors[j];
/*      */ 
/*  800 */       if (localAccessor == null) {
/*  801 */         DatabaseError.throwSqlException(21);
/*      */       }
/*  803 */       arrayOfShort[(i + 0)] = (short)localAccessor.defineType;
/*      */ 
/*  805 */       arrayOfShort[(i + 11)] = (short)localAccessor.charLength;
/*      */ 
/*  807 */       arrayOfShort[(i + 1)] = (short)localAccessor.byteLength;
/*      */ 
/*  809 */       arrayOfShort[(i + 5)] = localAccessor.formOfUse;
/*      */ 
/*  812 */       if (localAccessor.internalOtype != null)
/*      */       {
/*  814 */         long l = ((OracleTypeADT)localAccessor.internalOtype).getTdoCState();
/*      */ 
/*  817 */         arrayOfShort[(i + 7)] = (short)(int)((l & 0x0) >> 48);
/*      */ 
/*  819 */         arrayOfShort[(i + 8)] = (short)(int)((l & 0x0) >> 32);
/*      */ 
/*  821 */         arrayOfShort[(i + 9)] = (short)(int)((l & 0xFFFF0000) >> 16);
/*      */ 
/*  823 */         arrayOfShort[(i + 10)] = (short)(int)(l & 0xFFFF);
/*      */       }
/*  796 */       j++; i += 13;
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDefineFetch()
/*      */     throws SQLException
/*      */   {
/*  831 */     if (!this.needToPrepareDefineBuffer) {
/*  832 */       throw new Error("doDefineFetch called when needToPrepareDefineBuffer=false " + this.sqlObject.getSql(this.processEscapes, this.convertNcharLiterals));
/*      */     }
/*      */ 
/*  835 */     setupForDefine();
/*      */ 
/*  837 */     this.t2cOutput[2] = 0L;
/*  838 */     this.validRows = T2CStatement.t2cDefineFetch(this.c_state, this.rowPrefetch, this.connection.queryMetaData1, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.accessors, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.t2cOutput);
/*      */ 
/*  847 */     if (this.validRows == -1) {
/*  848 */       this.connection.checkError(this.validRows);
/*      */     }
/*      */ 
/*  851 */     if (this.t2cOutput[2] != 0L)
/*      */     {
/*  853 */       this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */     }
/*      */   }
/*      */ 
/*      */   void doDefineExecuteFetch()
/*      */     throws SQLException
/*      */   {
/*  864 */     short[] arrayOfShort = null;
/*      */ 
/*  866 */     if ((this.needToPrepareDefineBuffer) || (this.needToParse))
/*      */     {
/*  868 */       setupForDefine();
/*      */ 
/*  870 */       arrayOfShort = this.connection.queryMetaData1;
/*      */     }
/*      */ 
/*  873 */     this.t2cOutput[0] = 0L;
/*  874 */     this.t2cOutput[2] = 0L;
/*      */ 
/*  876 */     byte[] arrayOfByte = this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals);
/*      */ 
/*  878 */     this.validRows = T2CStatement.t2cDefineExecuteFetch(this, this.c_state, this.numberOfDefinePositions, this.numberOfBindPositions, this.numberOfBindRowsAllocated, this.firstRowInBatch, false, this.needToParse, arrayOfByte, arrayOfByte.length, this.sqlKind, this.rowPrefetch, this.batch, this.bindIndicators, this.bindIndicatorOffset, this.bindBytes, this.bindChars, this.bindByteOffset, this.bindCharOffset, arrayOfShort, this.connection.queryMetaData2, this.connection.queryMetaData1Offset, this.connection.queryMetaData2Offset, this.preparedAllBinds, this.preparedCharBinds, this.accessors, this.parameterDatum, this.t2cOutput, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset);
/*      */ 
/*  914 */     if (this.validRows == -1) {
/*  915 */       this.connection.checkError(this.validRows);
/*      */     }
/*  917 */     if (this.t2cOutput[2] != 0L) {
/*  918 */       this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */     }
/*      */ 
/*  923 */     this.connection.endToEndECIDSequenceNumber = (short)(int)this.t2cOutput[4];
/*      */ 
/*  925 */     this.needToParse = false;
/*      */   }
/*      */ 
/*      */   void fetch()
/*      */     throws SQLException
/*      */   {
/*  970 */     if (this.numberOfDefinePositions > 0)
/*      */     {
/*  972 */       if (this.needToPrepareDefineBuffer) {
/*  973 */         doDefineFetch();
/*      */       }
/*      */       else {
/*  976 */         this.t2cOutput[2] = 0L;
/*  977 */         this.validRows = T2CStatement.t2cFetch(this.c_state, this.needToPrepareDefineBuffer, this.rowPrefetch, this.accessors, this.defineBytes, this.accessorByteOffset, this.defineChars, this.accessorCharOffset, this.defineIndicators, this.accessorShortOffset, this.t2cOutput);
/*      */ 
/*  983 */         if (this.validRows == -1) {
/*  984 */           this.connection.checkError(this.validRows);
/*      */         }
/*  986 */         if (this.t2cOutput[2] != 0L)
/*      */         {
/*  988 */           this.sqlWarning = this.connection.checkError(1, this.sqlWarning);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void doClose()
/*      */     throws SQLException
/*      */   {
/* 1026 */     if (this.defineBytes != null)
/*      */     {
/* 1028 */       this.defineBytes = null;
/* 1029 */       this.accessorByteOffset = 0;
/*      */     }
/*      */ 
/* 1032 */     if (this.defineChars != null)
/*      */     {
/* 1034 */       this.defineChars = null;
/* 1035 */       this.accessorCharOffset = 0;
/*      */     }
/*      */ 
/* 1038 */     if (this.defineIndicators != null)
/*      */     {
/* 1040 */       this.defineIndicators = null;
/* 1041 */       this.accessorShortOffset = 0;
/*      */     }
/*      */ 
/* 1045 */     int i = T2CStatement.t2cCloseStatement(this.c_state);
/*      */ 
/* 1047 */     if (i != 0) {
/* 1048 */       this.connection.checkError(i);
/*      */     }
/* 1050 */     this.t2cOutput = null;
/*      */   }
/*      */ 
/*      */   void closeQuery()
/*      */     throws SQLException
/*      */   {
/* 1072 */     if (this.streamList != null)
/*      */     {
/* 1074 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1078 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException)
/*      */         {
/* 1082 */           DatabaseError.throwSqlException(localIOException);
/*      */         }
/*      */ 
/* 1085 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*      */     Object localObject;
/* 1107 */     if (paramInt1 == 109)
/*      */     {
/* 1109 */       if (paramString == null) {
/* 1110 */         if (paramBoolean) {
/* 1111 */           DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */         }
/*      */         else {
/* 1114 */           DatabaseError.throwSqlException(60, "Unable to resolve type \"null\"");
/*      */         }
/*      */       }
/* 1117 */       localObject = new T2CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, paramInt3 - 1);
/*      */ 
/* 1120 */       ((Accessor)localObject).initMetadata();
/*      */ 
/* 1122 */       return localObject;
/*      */     }
/* 1124 */     if ((paramInt1 == 116) || (paramInt1 == 102))
/*      */     {
/* 1127 */       if ((paramBoolean) && (paramString != null)) {
/* 1128 */         DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */       }
/*      */ 
/* 1131 */       localObject = new T2CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean);
/*      */ 
/* 1134 */       return localObject;
/*      */     }
/*      */ 
/* 1137 */     return (Accessor)super.allocateAccessor(paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   void closeUsedStreams(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1143 */     while ((this.nextStream != null) && (this.nextStream.columnIndex < paramInt))
/*      */     {
/*      */       try
/*      */       {
/* 1150 */         this.nextStream.close();
/*      */       }
/*      */       catch (IOException localIOException1)
/*      */       {
/* 1154 */         DatabaseError.throwSqlException(localIOException1);
/*      */       }
/*      */ 
/* 1157 */       this.nextStream = this.nextStream.nextStream;
/*      */     }
/*      */ 
/* 1160 */     if (this.nextStream != null)
/*      */       try
/*      */       {
/* 1163 */         this.nextStream.needBytes();
/*      */       }
/*      */       catch (IOException localIOException2)
/*      */       {
/* 1167 */         DatabaseError.throwSqlException(localIOException2);
/*      */       }
/*      */   }
/*      */ 
/*      */   void fetchDmlReturnParams()
/*      */     throws SQLException
/*      */   {
/* 1174 */     this.rowsDmlReturned = T2CStatement.t2cGetRowsDmlReturned(this.c_state);
/*      */ 
/* 1176 */     if (this.rowsDmlReturned != 0)
/*      */     {
/* 1178 */       allocateDmlReturnStorage();
/*      */ 
/* 1180 */       int i = T2CStatement.t2cFetchDmlReturnParams(this.c_state, this.returnParamAccessors, this.returnParamBytes, this.returnParamChars, this.returnParamIndicators);
/*      */     }
/*      */ 
/* 1189 */     this.returnParamsFetched = true;
/*      */   }
/*      */ 
/*      */   void initializeIndicatorSubRange()
/*      */   {
/* 1201 */     this.bindIndicatorSubRange = (this.numberOfBindPositions * 5);
/*      */   }
/*      */ 
/*      */   void prepareBindPreambles(int paramInt1, int paramInt2)
/*      */   {
/* 1212 */     int i = this.bindIndicatorSubRange;
/*      */ 
/* 1214 */     initializeIndicatorSubRange();
/*      */ 
/* 1216 */     int j = this.bindIndicatorSubRange;
/*      */ 
/* 1218 */     this.bindIndicatorSubRange = i;
/*      */ 
/* 1220 */     int k = this.bindIndicatorSubRange - j;
/* 1221 */     OracleTypeADT[] arrayOfOracleTypeADT = this.parameterOtype == null ? null : this.parameterOtype[this.firstRowInBatch];
/*      */ 
/* 1225 */     for (int m = 0; m < this.numberOfBindPositions; m++)
/*      */     {
/* 1228 */       Binder localBinder = this.lastBinders[m];
/*      */       OracleTypeADT localOracleTypeADT;
/*      */       int n;
/* 1231 */       if (localBinder == this.theReturnParamBinder)
/*      */       {
/* 1233 */         localOracleTypeADT = (OracleTypeADT)this.returnParamAccessors[m].internalOtype;
/* 1234 */         n = 0;
/*      */       }
/*      */       else
/*      */       {
/* 1238 */         localOracleTypeADT = arrayOfOracleTypeADT == null ? null : arrayOfOracleTypeADT[m];
/*      */ 
/* 1240 */         if (this.outBindAccessors == null) {
/* 1241 */           n = 0;
/*      */         }
/*      */         else {
/* 1244 */           Accessor localAccessor = this.outBindAccessors[m];
/*      */ 
/* 1246 */           if (localAccessor == null) {
/* 1247 */             n = 0;
/* 1248 */           } else if (localBinder == this.theOutBinder)
/*      */           {
/* 1250 */             n = 1;
/*      */ 
/* 1252 */             if (localOracleTypeADT == null)
/* 1253 */               localOracleTypeADT = (OracleTypeADT)localAccessor.internalOtype;
/*      */           }
/*      */           else {
/* 1256 */             n = 2;
/*      */           }
/*      */         }
/* 1259 */         if (localBinder == this.theSetCHARBinder) {
/* 1260 */           n = (short)(n | 0x4);
/*      */         }
/*      */       }
/* 1263 */       this.bindIndicators[(k++)] = n;
/*      */ 
/* 1265 */       if (localOracleTypeADT != null)
/*      */       {
/* 1267 */         long l = localOracleTypeADT.getTdoCState();
/*      */ 
/* 1269 */         this.bindIndicators[(k + 0)] = (short)(int)(l >> 48 & 0xFFFF);
/*      */ 
/* 1271 */         this.bindIndicators[(k + 1)] = (short)(int)(l >> 32 & 0xFFFF);
/*      */ 
/* 1273 */         this.bindIndicators[(k + 2)] = (short)(int)(l >> 16 & 0xFFFF);
/*      */ 
/* 1275 */         this.bindIndicators[(k + 3)] = (short)(int)(l & 0xFFFF);
/*      */       }
/*      */ 
/* 1278 */       k += 4;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T2CPreparedStatement
 * JD-Core Version:    0.6.0
 */