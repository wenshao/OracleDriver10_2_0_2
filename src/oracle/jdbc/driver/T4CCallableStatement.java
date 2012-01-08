/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ 
/*      */ class T4CCallableStatement extends OracleCallableStatement
/*      */ {
/*   42 */   static final byte[] EMPTY_BYTE = new byte[0];
/*      */   T4CConnection t4Connection;
/* 1241 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*      */ 
/*      */   T4CCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*   28 */     super(paramPhysicalConnection, paramString, paramPhysicalConnection.defaultBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
/*      */ 
/*   30 */     this.t4Connection = ((T4CConnection)paramPhysicalConnection);
/*   31 */     this.nbPostPonedColumns = new int[1];
/*   32 */     this.nbPostPonedColumns[0] = 0;
/*   33 */     this.indexOfPostPonedColumn = new int[1][3];
/*      */ 
/*   35 */     this.theRowidBinder = theStaticT4CRowidBinder;
/*   36 */     this.theRowidNullBinder = theStaticT4CRowidNullBinder;
/*      */   }
/*      */ 
/*      */   void doOall8(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*      */     throws SQLException, IOException
/*      */   {
/*   64 */     if ((paramBoolean1) || (paramBoolean4) || (!paramBoolean2) || ((this.sqlKind != 2) && (this.sqlKind != 1) && (this.sqlKind != 4))) {
/*   65 */       this.oacdefSent = null;
/*      */     }
/*   67 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CCallableStatement.doOall8");
/*      */ 
/*   69 */     if ((this.sqlKind != 1) && (this.sqlKind != 4) && (this.sqlKind != 3) && (this.sqlKind != 0) && (this.sqlKind != 2))
/*      */     {
/*   75 */       DatabaseError.throwSqlException(439);
/*      */     }
/*      */ 
/*   78 */     int i = this.numberOfDefinePositions;
/*      */ 
/*   80 */     if (this.sqlKind == 2)
/*   81 */       i = 0;
/*      */     int j;
/*   85 */     if (paramBoolean3)
/*      */     {
/*   87 */       if (this.accessors != null)
/*      */       {
/*   89 */         for (j = 0; j < this.numberOfDefinePositions; j++)
/*      */         {
/*   91 */           if (this.accessors[j] == null)
/*      */             continue;
/*   93 */           this.accessors[j].lastRowProcessed = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*   98 */       if (this.outBindAccessors != null)
/*      */       {
/*  100 */         for (j = 0; j < this.outBindAccessors.length; j++)
/*      */         {
/*  102 */           if (this.outBindAccessors[j] == null)
/*      */             continue;
/*  104 */           this.outBindAccessors[j].lastRowProcessed = 0;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  110 */     if (this.returnParamAccessors != null)
/*      */     {
/*  112 */       for (j = 0; j < this.numberOfBindPositions; j++)
/*      */       {
/*  114 */         if (this.returnParamAccessors[j] == null)
/*      */           continue;
/*  116 */         this.returnParamAccessors[j].lastRowProcessed = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  126 */     if (this.bindIndicators != null)
/*      */     {
/*  128 */       j = this.bindIndicators[(this.bindIndicatorSubRange + 2)] & 0xFFFF;
/*      */ 
/*  131 */       int k = 0;
/*      */ 
/*  133 */       if (this.ibtBindChars != null) {
/*  134 */         k = this.ibtBindChars.length * this.connection.conversion.cMaxCharSize;
/*      */       }
/*  136 */       for (int m = 0; m < this.numberOfBindPositions; m++)
/*      */       {
/*  138 */         int n = this.bindIndicatorSubRange + 3 + 10 * m;
/*      */ 
/*  142 */         int i1 = this.bindIndicators[(n + 2)] & 0xFFFF;
/*      */ 
/*  146 */         if (i1 == 0) {
/*      */           continue;
/*      */         }
/*  149 */         int i2 = this.bindIndicators[(n + 9)] & 0xFFFF;
/*      */ 
/*  153 */         if (i2 == 2)
/*      */         {
/*  155 */           k = Math.max(i1 * this.connection.conversion.maxNCharSize, k);
/*      */         }
/*      */         else
/*      */         {
/*  160 */           k = Math.max(i1 * this.connection.conversion.cMaxCharSize, k);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  166 */       if (this.tmpBindsByteArray == null)
/*      */       {
/*  168 */         this.tmpBindsByteArray = new byte[k];
/*      */       }
/*  170 */       else if (this.tmpBindsByteArray.length < k)
/*      */       {
/*  172 */         this.tmpBindsByteArray = null;
/*  173 */         this.tmpBindsByteArray = new byte[k];
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  185 */       this.tmpBindsByteArray = null;
/*      */     }
/*      */ 
/*  194 */     allocateTmpByteArray();
/*      */ 
/*  196 */     T4C8Oall localT4C8Oall = this.t4Connection.all8;
/*      */ 
/*  198 */     this.t4Connection.sendPiggyBackedMessages();
/*      */ 
/*  201 */     this.oacdefSent = localT4C8Oall.marshal(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, this.sqlKind, this.cursorId, this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals), this.rowPrefetch, this.outBindAccessors, this.numberOfBindPositions, this.accessors, i, this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.connection.conversion, this.tmpBindsByteArray, this.parameterStream, this.parameterDatum, this.parameterOtype, this, this.ibtBindBytes, this.ibtBindChars, this.ibtBindIndicators, this.oacdefSent, this.definedColumnType, this.definedColumnSize, this.definedColumnFormOfUse);
/*      */     try
/*      */     {
/*  215 */       localT4C8Oall.receive();
/*      */ 
/*  217 */       this.cursorId = localT4C8Oall.getCursorId();
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  221 */       this.cursorId = localT4C8Oall.getCursorId();
/*      */ 
/*  223 */       if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110))
/*      */       {
/*  226 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
/*      */       }
/*      */       else
/*      */       {
/*  231 */         throw localSQLException;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void allocateTmpByteArray()
/*      */   {
/*  242 */     if (this.tmpByteArray == null)
/*      */     {
/*  247 */       this.tmpByteArray = new byte[this.sizeTmpByteArray];
/*      */     }
/*  249 */     else if (this.sizeTmpByteArray > this.tmpByteArray.length)
/*      */     {
/*  254 */       this.tmpByteArray = new byte[this.sizeTmpByteArray];
/*      */     }
/*      */   }
/*      */ 
/*      */   void allocateRowidAccessor()
/*      */     throws SQLException
/*      */   {
/*  267 */     this.accessors[0] = new T4CRowidAccessor(this, 128, 1, -8, false, this.t4Connection.mare);
/*      */   }
/*      */ 
/*      */   void reparseOnRedefineIfNeeded()
/*      */     throws SQLException
/*      */   {
/*  282 */     this.needToParse = true;
/*      */   }
/*      */ 
/*      */   protected void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, short paramShort, boolean paramBoolean, String paramString)
/*      */     throws SQLException
/*      */   {
/*  291 */     if (this.connection.disableDefineColumnType)
/*      */     {
/*  296 */       return;
/*      */     }
/*      */ 
/*  302 */     if (paramInt1 < 1) {
/*  303 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  305 */     if (paramBoolean)
/*      */     {
/*  309 */       if ((paramInt2 == 1) || (paramInt2 == 12)) {
/*  310 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 108);
/*      */       }
/*      */ 
/*      */     }
/*  316 */     else if (paramInt3 < 0) {
/*  317 */       DatabaseError.throwSqlException(53);
/*      */     }
/*      */ 
/*  320 */     if ((this.currentResultSet != null) && (!this.currentResultSet.closed)) {
/*  321 */       DatabaseError.throwSqlException(28);
/*      */     }
/*      */ 
/*  328 */     int i = paramInt1 - 1;
/*      */     int[] arrayOfInt;
/*  330 */     if ((this.definedColumnType == null) || (this.definedColumnType.length <= i))
/*      */     {
/*  332 */       if (this.definedColumnType == null) {
/*  333 */         this.definedColumnType = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  336 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  338 */         System.arraycopy(this.definedColumnType, 0, arrayOfInt, 0, this.definedColumnType.length);
/*      */ 
/*  341 */         this.definedColumnType = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  345 */     this.definedColumnType[i] = paramInt2;
/*      */ 
/*  347 */     if ((this.definedColumnSize == null) || (this.definedColumnSize.length <= i))
/*      */     {
/*  349 */       if (this.definedColumnSize == null) {
/*  350 */         this.definedColumnSize = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  353 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  355 */         System.arraycopy(this.definedColumnSize, 0, arrayOfInt, 0, this.definedColumnSize.length);
/*      */ 
/*  358 */         this.definedColumnSize = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  362 */     this.definedColumnSize[i] = paramInt3;
/*      */ 
/*  364 */     if ((this.definedColumnFormOfUse == null) || (this.definedColumnFormOfUse.length <= i))
/*      */     {
/*  366 */       if (this.definedColumnFormOfUse == null) {
/*  367 */         this.definedColumnFormOfUse = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  370 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  372 */         System.arraycopy(this.definedColumnFormOfUse, 0, arrayOfInt, 0, this.definedColumnFormOfUse.length);
/*      */ 
/*  375 */         this.definedColumnFormOfUse = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  379 */     this.definedColumnFormOfUse[i] = paramShort;
/*      */ 
/*  381 */     if ((this.accessors != null) && (i < this.accessors.length) && (this.accessors[i] != null))
/*      */     {
/*  383 */       this.accessors[i].definedColumnSize = paramInt3;
/*      */ 
/*  388 */       if (((this.accessors[i].internalType == 96) || (this.accessors[i].internalType == 1)) && ((paramInt2 == 1) || (paramInt2 == 12)))
/*      */       {
/*  392 */         if (paramInt3 <= this.accessors[i].oacmxl)
/*      */         {
/*  398 */           this.needToPrepareDefineBuffer = true;
/*  399 */           this.columnsDefinedByUser = true;
/*      */ 
/*  401 */           this.accessors[i].initForDataAccess(paramInt2, paramInt3, null);
/*  402 */           this.accessors[i].calculateSizeTmpByteArray();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void clearDefines() throws SQLException
/*      */   {
/*  410 */     super.clearDefines();
/*      */ 
/*  412 */     this.definedColumnType = null;
/*  413 */     this.definedColumnSize = null;
/*  414 */     this.definedColumnFormOfUse = null;
/*      */   }
/*      */ 
/*      */   void saveDefineBuffersIfRequired(char[] paramArrayOfChar, byte[] paramArrayOfByte, short[] paramArrayOfShort, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  455 */     if (paramBoolean)
/*      */     {
/*  464 */       paramArrayOfShort = new short[this.defineIndicators.length];
/*  465 */       i = this.accessors[0].lengthIndexLastRow;
/*  466 */       int j = this.accessors[0].indicatorIndexLastRow;
/*      */ 
/*  468 */       for (int n = 1; n <= this.accessors.length; n++)
/*      */       {
/*  470 */         int k = i + this.rowPrefetchAtExecute * n - 1;
/*  471 */         int m = j + this.rowPrefetchAtExecute * n - 1;
/*  472 */         paramArrayOfShort[m] = this.defineIndicators[m];
/*  473 */         paramArrayOfShort[k] = this.defineIndicators[k];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  480 */     for (int i = 0; i < this.accessors.length; i++)
/*      */     {
/*  487 */       this.accessors[i].saveDataFromOldDefineBuffers(paramArrayOfByte, paramArrayOfChar, paramArrayOfShort, this.rowPrefetchAtExecute != -1 ? this.rowPrefetchAtExecute : this.rowPrefetch, this.rowPrefetch);
/*      */     }
/*      */   }
/*      */ 
/*      */   Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  515 */     Object localObject = null;
/*      */ 
/*  517 */     switch (paramInt1)
/*      */     {
/*      */     case 96:
/*  521 */       localObject = new T4CCharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  524 */       break;
/*      */     case 8:
/*  527 */       if (paramBoolean)
/*      */         break;
/*  529 */       localObject = new T4CLongAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */ 
/*  532 */       break;
/*      */     case 1:
/*  537 */       localObject = new T4CVarcharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  540 */       break;
/*      */     case 2:
/*  543 */       localObject = new T4CNumberAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  546 */       break;
/*      */     case 6:
/*  549 */       localObject = new T4CVarnumAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  552 */       break;
/*      */     case 24:
/*  555 */       if (!paramBoolean)
/*      */       {
/*  557 */         localObject = new T4CLongRawAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  560 */       break;
/*      */     case 23:
/*  565 */       if ((paramBoolean) && (paramString != null)) {
/*  566 */         DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */       }
/*      */ 
/*  569 */       if (paramBoolean) {
/*  570 */         localObject = new T4COutRawAccessor(this, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */       }
/*      */       else {
/*  573 */         localObject = new T4CRawAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  576 */       break;
/*      */     case 100:
/*  579 */       localObject = new T4CBinaryFloatAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  582 */       break;
/*      */     case 101:
/*  585 */       localObject = new T4CBinaryDoubleAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  588 */       break;
/*      */     case 104:
/*  591 */       localObject = new T4CRowidAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  594 */       break;
/*      */     case 102:
/*  597 */       localObject = new T4CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  600 */       break;
/*      */     case 12:
/*  603 */       localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  606 */       break;
/*      */     case 113:
/*  609 */       localObject = new T4CBlobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  612 */       break;
/*      */     case 112:
/*  615 */       localObject = new T4CClobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  618 */       break;
/*      */     case 114:
/*  621 */       localObject = new T4CBfileAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  624 */       break;
/*      */     case 109:
/*  627 */       localObject = new T4CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  630 */       ((Accessor)localObject).initMetadata();
/*      */ 
/*  632 */       break;
/*      */     case 111:
/*  635 */       localObject = new T4CRefTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  638 */       ((Accessor)localObject).initMetadata();
/*      */ 
/*  640 */       break;
/*      */     case 180:
/*  643 */       if (this.connection.v8Compatible) {
/*  644 */         localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */       else {
/*  647 */         localObject = new T4CTimestampAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  650 */       break;
/*      */     case 181:
/*  653 */       localObject = new T4CTimestamptzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  656 */       break;
/*      */     case 231:
/*  659 */       localObject = new T4CTimestampltzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  662 */       break;
/*      */     case 182:
/*  665 */       localObject = new T4CIntervalymAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  668 */       break;
/*      */     case 183:
/*  671 */       localObject = new T4CIntervaldsAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  674 */       break;
/*      */     case 995:
/*  686 */       DatabaseError.throwSqlException(89);
/*      */     }
/*      */ 
/*  691 */     return (Accessor)localObject;
/*      */   }
/*      */ 
/*      */   void doDescribe(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  720 */     if (!this.isOpen)
/*      */     {
/*  722 */       DatabaseError.throwSqlException(144);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  727 */       this.t4Connection.sendPiggyBackedMessages();
/*  728 */       this.t4Connection.describe.init(this, 0);
/*  729 */       this.t4Connection.describe.marshal();
/*      */ 
/*  731 */       this.accessors = this.t4Connection.describe.receive(this.accessors);
/*  732 */       this.numberOfDefinePositions = this.t4Connection.describe.numuds;
/*      */ 
/*  734 */       for (int i = 0; i < this.numberOfDefinePositions; i++) {
/*  735 */         this.accessors[i].initMetadata();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  742 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  743 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/*  746 */     this.describedWithNames = true;
/*  747 */     this.described = true;
/*      */   }
/*      */ 
/*      */   void executeForDescribe()
/*      */     throws SQLException
/*      */   {
/*  784 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CCallableStatement.execute_for_describe");
/*  785 */     cleanOldTempLobs();
/*      */     try
/*      */     {
/*  788 */       if (this.t4Connection.useFetchSizeWithLongColumn)
/*      */       {
/*  794 */         doOall8(true, true, true, true);
/*      */       }
/*      */       else
/*      */       {
/*  798 */         doOall8(true, true, false, true);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  806 */       throw localSQLException;
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  813 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  814 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */     finally
/*      */     {
/*  818 */       this.rowsProcessed = this.t4Connection.all8.rowsProcessed;
/*  819 */       this.validRows = this.t4Connection.all8.getNumRows();
/*      */     }
/*      */ 
/*  822 */     this.needToParse = false;
/*      */ 
/*  825 */     for (int i = 0; i < this.numberOfDefinePositions; i++) {
/*  826 */       this.accessors[i].initMetadata();
/*      */     }
/*  828 */     this.needToPrepareDefineBuffer = false;
/*      */   }
/*      */ 
/*      */   void executeMaybeDescribe()
/*      */     throws SQLException
/*      */   {
/*  836 */     if (!this.t4Connection.useFetchSizeWithLongColumn)
/*      */     {
/*  838 */       super.executeMaybeDescribe();
/*      */     }
/*      */     else
/*      */     {
/*  846 */       if (this.rowPrefetchChanged)
/*      */       {
/*  848 */         if ((this.streamList == null) && (this.rowPrefetch != this.definesBatchSize)) {
/*  849 */           this.needToPrepareDefineBuffer = true;
/*      */         }
/*  851 */         this.rowPrefetchChanged = false;
/*      */       }
/*      */ 
/*  854 */       if (!this.needToPrepareDefineBuffer)
/*      */       {
/*  858 */         if (this.accessors == null)
/*      */         {
/*  862 */           this.needToPrepareDefineBuffer = true;
/*  863 */         } else if (this.columnsDefinedByUser) {
/*  864 */           this.needToPrepareDefineBuffer = (!checkAccessorsUsable());
/*      */         }
/*      */       }
/*  867 */       boolean bool = false;
/*      */       try
/*      */       {
/*  872 */         this.isExecuting = true;
/*      */ 
/*  874 */         if (this.needToPrepareDefineBuffer)
/*      */         {
/*  876 */           executeForDescribe();
/*      */ 
/*  878 */           bool = true;
/*      */         }
/*      */         else
/*      */         {
/*  885 */           int i = this.accessors.length;
/*      */ 
/*  887 */           for (int j = this.numberOfDefinePositions; j < i; j++)
/*      */           {
/*  889 */             Accessor localAccessor = this.accessors[j];
/*      */ 
/*  891 */             if (localAccessor != null) {
/*  892 */               localAccessor.rowSpaceIndicator = null;
/*      */             }
/*      */           }
/*  895 */           executeForRows(bool);
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException localSQLException)
/*      */       {
/*  901 */         this.needToParse = true;
/*  902 */         throw localSQLException;
/*      */       }
/*      */       finally
/*      */       {
/*  906 */         this.isExecuting = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void executeForRows(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/*  954 */         doOall8(this.needToParse, !paramBoolean, true, false);
/*      */ 
/*  956 */         this.needToParse = false;
/*      */       }
/*      */       finally
/*      */       {
/*  960 */         this.validRows = this.t4Connection.all8.getNumRows();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  968 */       throw localSQLException;
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  975 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  976 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */   }
/*      */ 
/*      */   void fetch()
/*      */     throws SQLException
/*      */   {
/* 1004 */     if (this.streamList != null)
/*      */     {
/* 1008 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1012 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException1)
/*      */         {
/* 1016 */           ((T4CConnection)this.connection).handleIOException(localIOException1);
/* 1017 */           DatabaseError.throwSqlException(localIOException1);
/*      */         }
/*      */ 
/* 1020 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1026 */       doOall8(false, false, true, false);
/*      */ 
/* 1028 */       this.validRows = this.t4Connection.all8.getNumRows();
/*      */     }
/*      */     catch (IOException localIOException2)
/*      */     {
/* 1035 */       ((T4CConnection)this.connection).handleIOException(localIOException2);
/* 1036 */       DatabaseError.throwSqlException(localIOException2);
/*      */     }
/*      */   }
/*      */ 
/*      */   void continueReadRow(int paramInt)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 1057 */       if (!this.connection.useFetchSizeWithLongColumn)
/*      */       {
/* 1059 */         T4C8Oall localT4C8Oall = this.t4Connection.all8;
/*      */ 
/* 1061 */         localT4C8Oall.continueReadRow(paramInt);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/* 1069 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1070 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/* 1074 */       if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110))
/*      */       {
/* 1077 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
/*      */       }
/*      */       else
/*      */       {
/* 1082 */         throw localSQLException;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void doClose()
/*      */     throws SQLException
/*      */   {
/* 1109 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CCallableStatement.do_close");
/*      */     try
/*      */     {
/* 1113 */       if (this.cursorId != 0)
/*      */       {
/* 1115 */         this.t4Connection.cursorToClose[(this.t4Connection.cursorToCloseOffset++)] = this.cursorId;
/*      */ 
/* 1118 */         if (this.t4Connection.cursorToCloseOffset >= this.t4Connection.cursorToClose.length)
/*      */         {
/* 1121 */           this.t4Connection.sendPiggyBackedMessages();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/* 1130 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1131 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/* 1134 */     this.tmpByteArray = null;
/* 1135 */     this.tmpBindsByteArray = null;
/* 1136 */     this.definedColumnType = null;
/* 1137 */     this.definedColumnSize = null;
/* 1138 */     this.definedColumnFormOfUse = null;
/* 1139 */     this.oacdefSent = null;
/*      */   }
/*      */ 
/*      */   void closeQuery()
/*      */     throws SQLException
/*      */   {
/* 1161 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CCallableStatement.closeQuery");
/*      */ 
/* 1164 */     if (this.streamList != null)
/*      */     {
/* 1168 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1172 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException)
/*      */         {
/* 1176 */           ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1177 */           DatabaseError.throwSqlException(localIOException);
/*      */         }
/*      */ 
/* 1180 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   PlsqlIndexTableAccessor allocateIndexTableAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1230 */     return new T4CPlsqlIndexTableAccessor(this, paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramBoolean, this.t4Connection.mare);
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CCallableStatement
 * JD-Core Version:    0.6.0
 */