/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ 
/*      */ class T4CStatement extends OracleStatement
/*      */ {
/*   24 */   static final byte[][][] parameterDatum = (byte[][][])null;
/*   25 */   static final OracleTypeADT[][] parameterOtype = (OracleTypeADT[][])null;
/*      */ 
/*   34 */   static final byte[] EMPTY_BYTE = new byte[0];
/*      */   T4CConnection t4Connection;
/* 1228 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*      */ 
/*      */   void doOall8(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*      */     throws SQLException, IOException
/*      */   {
/*   56 */     if ((paramBoolean1) || (paramBoolean4) || (!paramBoolean2) || ((this.sqlKind != 2) && (this.sqlKind != 1) && (this.sqlKind != 4))) {
/*   57 */       this.oacdefSent = null;
/*      */     }
/*   59 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CStatement.doOall8");
/*      */ 
/*   61 */     if ((this.sqlKind != 1) && (this.sqlKind != 4) && (this.sqlKind != 3) && (this.sqlKind != 0) && (this.sqlKind != 2))
/*      */     {
/*   67 */       DatabaseError.throwSqlException(439);
/*      */     }
/*      */ 
/*   70 */     int i = this.numberOfDefinePositions;
/*      */ 
/*   72 */     if (this.sqlKind == 2)
/*   73 */       i = 0;
/*      */     int j;
/*   77 */     if (paramBoolean3)
/*      */     {
/*   79 */       if (this.accessors != null)
/*      */       {
/*   81 */         for (j = 0; j < this.numberOfDefinePositions; j++)
/*      */         {
/*   83 */           if (this.accessors[j] == null)
/*      */             continue;
/*   85 */           this.accessors[j].lastRowProcessed = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*   90 */       if (this.outBindAccessors != null)
/*      */       {
/*   92 */         for (j = 0; j < this.outBindAccessors.length; j++)
/*      */         {
/*   94 */           if (this.outBindAccessors[j] == null)
/*      */             continue;
/*   96 */           this.outBindAccessors[j].lastRowProcessed = 0;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  102 */     if (this.returnParamAccessors != null)
/*      */     {
/*  104 */       for (j = 0; j < this.numberOfBindPositions; j++)
/*      */       {
/*  106 */         if (this.returnParamAccessors[j] == null)
/*      */           continue;
/*  108 */         this.returnParamAccessors[j].lastRowProcessed = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  118 */     if (this.bindIndicators != null)
/*      */     {
/*  120 */       j = this.bindIndicators[(this.bindIndicatorSubRange + 2)] & 0xFFFF;
/*      */ 
/*  123 */       int k = 0;
/*      */ 
/*  125 */       if (this.ibtBindChars != null) {
/*  126 */         k = this.ibtBindChars.length * this.connection.conversion.cMaxCharSize;
/*      */       }
/*  128 */       for (int m = 0; m < this.numberOfBindPositions; m++)
/*      */       {
/*  130 */         int n = this.bindIndicatorSubRange + 3 + 10 * m;
/*      */ 
/*  134 */         int i1 = this.bindIndicators[(n + 2)] & 0xFFFF;
/*      */ 
/*  138 */         if (i1 == 0) {
/*      */           continue;
/*      */         }
/*  141 */         int i2 = this.bindIndicators[(n + 9)] & 0xFFFF;
/*      */ 
/*  145 */         if (i2 == 2)
/*      */         {
/*  147 */           k = Math.max(i1 * this.connection.conversion.maxNCharSize, k);
/*      */         }
/*      */         else
/*      */         {
/*  152 */           k = Math.max(i1 * this.connection.conversion.cMaxCharSize, k);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  158 */       if (this.tmpBindsByteArray == null)
/*      */       {
/*  160 */         this.tmpBindsByteArray = new byte[k];
/*      */       }
/*  162 */       else if (this.tmpBindsByteArray.length < k)
/*      */       {
/*  164 */         this.tmpBindsByteArray = null;
/*  165 */         this.tmpBindsByteArray = new byte[k];
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  177 */       this.tmpBindsByteArray = null;
/*      */     }
/*      */ 
/*  186 */     allocateTmpByteArray();
/*      */ 
/*  188 */     T4C8Oall localT4C8Oall = this.t4Connection.all8;
/*      */ 
/*  190 */     this.t4Connection.sendPiggyBackedMessages();
/*      */ 
/*  193 */     this.oacdefSent = localT4C8Oall.marshal(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, this.sqlKind, this.cursorId, this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals), this.rowPrefetch, this.outBindAccessors, this.numberOfBindPositions, this.accessors, i, this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.connection.conversion, this.tmpBindsByteArray, this.parameterStream, parameterDatum, parameterOtype, this, this.ibtBindBytes, this.ibtBindChars, this.ibtBindIndicators, this.oacdefSent, this.definedColumnType, this.definedColumnSize, this.definedColumnFormOfUse);
/*      */     try
/*      */     {
/*  207 */       localT4C8Oall.receive();
/*      */ 
/*  209 */       this.cursorId = localT4C8Oall.getCursorId();
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  213 */       this.cursorId = localT4C8Oall.getCursorId();
/*      */ 
/*  215 */       if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110))
/*      */       {
/*  218 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
/*      */       }
/*      */       else
/*      */       {
/*  223 */         throw localSQLException;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void allocateTmpByteArray()
/*      */   {
/*  234 */     if (this.tmpByteArray == null)
/*      */     {
/*  239 */       this.tmpByteArray = new byte[this.sizeTmpByteArray];
/*      */     }
/*  241 */     else if (this.sizeTmpByteArray > this.tmpByteArray.length)
/*      */     {
/*  246 */       this.tmpByteArray = new byte[this.sizeTmpByteArray];
/*      */     }
/*      */   }
/*      */ 
/*      */   void allocateRowidAccessor()
/*      */     throws SQLException
/*      */   {
/*  259 */     this.accessors[0] = new T4CRowidAccessor(this, 128, 1, -8, false, this.t4Connection.mare);
/*      */   }
/*      */ 
/*      */   void reparseOnRedefineIfNeeded()
/*      */     throws SQLException
/*      */   {
/*  274 */     this.needToParse = true;
/*      */   }
/*      */ 
/*      */   protected void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, short paramShort, boolean paramBoolean, String paramString)
/*      */     throws SQLException
/*      */   {
/*  283 */     if (this.connection.disableDefineColumnType)
/*      */     {
/*  288 */       return;
/*      */     }
/*      */ 
/*  294 */     if (paramInt1 < 1) {
/*  295 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  297 */     if (paramBoolean)
/*      */     {
/*  301 */       if ((paramInt2 == 1) || (paramInt2 == 12)) {
/*  302 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 108);
/*      */       }
/*      */ 
/*      */     }
/*  308 */     else if (paramInt3 < 0) {
/*  309 */       DatabaseError.throwSqlException(53);
/*      */     }
/*      */ 
/*  312 */     if ((this.currentResultSet != null) && (!this.currentResultSet.closed)) {
/*  313 */       DatabaseError.throwSqlException(28);
/*      */     }
/*      */ 
/*  320 */     int i = paramInt1 - 1;
/*      */     int[] arrayOfInt;
/*  322 */     if ((this.definedColumnType == null) || (this.definedColumnType.length <= i))
/*      */     {
/*  324 */       if (this.definedColumnType == null) {
/*  325 */         this.definedColumnType = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  328 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  330 */         System.arraycopy(this.definedColumnType, 0, arrayOfInt, 0, this.definedColumnType.length);
/*      */ 
/*  333 */         this.definedColumnType = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  337 */     this.definedColumnType[i] = paramInt2;
/*      */ 
/*  339 */     if ((this.definedColumnSize == null) || (this.definedColumnSize.length <= i))
/*      */     {
/*  341 */       if (this.definedColumnSize == null) {
/*  342 */         this.definedColumnSize = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  345 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  347 */         System.arraycopy(this.definedColumnSize, 0, arrayOfInt, 0, this.definedColumnSize.length);
/*      */ 
/*  350 */         this.definedColumnSize = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  354 */     this.definedColumnSize[i] = paramInt3;
/*      */ 
/*  356 */     if ((this.definedColumnFormOfUse == null) || (this.definedColumnFormOfUse.length <= i))
/*      */     {
/*  358 */       if (this.definedColumnFormOfUse == null) {
/*  359 */         this.definedColumnFormOfUse = new int[(i + 1) * 4];
/*      */       }
/*      */       else {
/*  362 */         arrayOfInt = new int[(i + 1) * 4];
/*      */ 
/*  364 */         System.arraycopy(this.definedColumnFormOfUse, 0, arrayOfInt, 0, this.definedColumnFormOfUse.length);
/*      */ 
/*  367 */         this.definedColumnFormOfUse = arrayOfInt;
/*      */       }
/*      */     }
/*      */ 
/*  371 */     this.definedColumnFormOfUse[i] = paramShort;
/*      */ 
/*  373 */     if ((this.accessors != null) && (i < this.accessors.length) && (this.accessors[i] != null))
/*      */     {
/*  375 */       this.accessors[i].definedColumnSize = paramInt3;
/*      */ 
/*  380 */       if (((this.accessors[i].internalType == 96) || (this.accessors[i].internalType == 1)) && ((paramInt2 == 1) || (paramInt2 == 12)))
/*      */       {
/*  384 */         if (paramInt3 <= this.accessors[i].oacmxl)
/*      */         {
/*  390 */           this.needToPrepareDefineBuffer = true;
/*  391 */           this.columnsDefinedByUser = true;
/*      */ 
/*  393 */           this.accessors[i].initForDataAccess(paramInt2, paramInt3, null);
/*  394 */           this.accessors[i].calculateSizeTmpByteArray();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void clearDefines() throws SQLException
/*      */   {
/*  402 */     super.clearDefines();
/*      */ 
/*  404 */     this.definedColumnType = null;
/*  405 */     this.definedColumnSize = null;
/*  406 */     this.definedColumnFormOfUse = null;
/*      */   }
/*      */ 
/*      */   void saveDefineBuffersIfRequired(char[] paramArrayOfChar, byte[] paramArrayOfByte, short[] paramArrayOfShort, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  447 */     if (paramBoolean)
/*      */     {
/*  456 */       paramArrayOfShort = new short[this.defineIndicators.length];
/*  457 */       i = this.accessors[0].lengthIndexLastRow;
/*  458 */       int j = this.accessors[0].indicatorIndexLastRow;
/*      */ 
/*  460 */       for (int n = 1; n <= this.accessors.length; n++)
/*      */       {
/*  462 */         int k = i + this.rowPrefetchAtExecute * n - 1;
/*  463 */         int m = j + this.rowPrefetchAtExecute * n - 1;
/*  464 */         paramArrayOfShort[m] = this.defineIndicators[m];
/*  465 */         paramArrayOfShort[k] = this.defineIndicators[k];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  472 */     for (int i = 0; i < this.accessors.length; i++)
/*      */     {
/*  479 */       this.accessors[i].saveDataFromOldDefineBuffers(paramArrayOfByte, paramArrayOfChar, paramArrayOfShort, this.rowPrefetchAtExecute != -1 ? this.rowPrefetchAtExecute : this.rowPrefetch, this.rowPrefetch);
/*      */     }
/*      */   }
/*      */ 
/*      */   Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  507 */     Object localObject = null;
/*      */ 
/*  509 */     switch (paramInt1)
/*      */     {
/*      */     case 96:
/*  513 */       localObject = new T4CCharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  516 */       break;
/*      */     case 8:
/*  519 */       if (paramBoolean)
/*      */         break;
/*  521 */       localObject = new T4CLongAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */ 
/*  524 */       break;
/*      */     case 1:
/*  529 */       localObject = new T4CVarcharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  532 */       break;
/*      */     case 2:
/*  535 */       localObject = new T4CNumberAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  538 */       break;
/*      */     case 6:
/*  541 */       localObject = new T4CVarnumAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  544 */       break;
/*      */     case 24:
/*  547 */       if (!paramBoolean)
/*      */       {
/*  549 */         localObject = new T4CLongRawAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  552 */       break;
/*      */     case 23:
/*  557 */       if ((paramBoolean) && (paramString != null)) {
/*  558 */         DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
/*      */       }
/*      */ 
/*  561 */       if (paramBoolean) {
/*  562 */         localObject = new T4COutRawAccessor(this, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
/*      */       }
/*      */       else {
/*  565 */         localObject = new T4CRawAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  568 */       break;
/*      */     case 100:
/*  571 */       localObject = new T4CBinaryFloatAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  574 */       break;
/*      */     case 101:
/*  577 */       localObject = new T4CBinaryDoubleAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  580 */       break;
/*      */     case 104:
/*  583 */       localObject = new T4CRowidAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  586 */       break;
/*      */     case 102:
/*  589 */       localObject = new T4CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  592 */       break;
/*      */     case 12:
/*  595 */       localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  598 */       break;
/*      */     case 113:
/*  601 */       localObject = new T4CBlobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  604 */       break;
/*      */     case 112:
/*  607 */       localObject = new T4CClobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  610 */       break;
/*      */     case 114:
/*  613 */       localObject = new T4CBfileAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  616 */       break;
/*      */     case 109:
/*  619 */       localObject = new T4CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  622 */       ((Accessor)localObject).initMetadata();
/*      */ 
/*  624 */       break;
/*      */     case 111:
/*  627 */       localObject = new T4CRefTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  630 */       ((Accessor)localObject).initMetadata();
/*      */ 
/*  632 */       break;
/*      */     case 180:
/*  635 */       if (this.connection.v8Compatible) {
/*  636 */         localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */       else {
/*  639 */         localObject = new T4CTimestampAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */       }
/*      */ 
/*  642 */       break;
/*      */     case 181:
/*  645 */       localObject = new T4CTimestamptzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  648 */       break;
/*      */     case 231:
/*  651 */       localObject = new T4CTimestampltzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  654 */       break;
/*      */     case 182:
/*  657 */       localObject = new T4CIntervalymAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  660 */       break;
/*      */     case 183:
/*  663 */       localObject = new T4CIntervaldsAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
/*      */ 
/*  666 */       break;
/*      */     case 995:
/*  678 */       DatabaseError.throwSqlException(89);
/*      */     }
/*      */ 
/*  683 */     return (Accessor)localObject;
/*      */   }
/*      */ 
/*      */   void doDescribe(boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  712 */     if (!this.isOpen)
/*      */     {
/*  714 */       DatabaseError.throwSqlException(144);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  719 */       this.t4Connection.sendPiggyBackedMessages();
/*  720 */       this.t4Connection.describe.init(this, 0);
/*  721 */       this.t4Connection.describe.marshal();
/*      */ 
/*  723 */       this.accessors = this.t4Connection.describe.receive(this.accessors);
/*  724 */       this.numberOfDefinePositions = this.t4Connection.describe.numuds;
/*      */ 
/*  726 */       for (int i = 0; i < this.numberOfDefinePositions; i++) {
/*  727 */         this.accessors[i].initMetadata();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  734 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  735 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/*  738 */     this.describedWithNames = true;
/*  739 */     this.described = true;
/*      */   }
/*      */ 
/*      */   void executeForDescribe()
/*      */     throws SQLException
/*      */   {
/*  776 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CStatement.execute_for_describe");
/*  777 */     cleanOldTempLobs();
/*      */     try
/*      */     {
/*  780 */       if (this.t4Connection.useFetchSizeWithLongColumn)
/*      */       {
/*  786 */         doOall8(true, true, true, true);
/*      */       }
/*      */       else
/*      */       {
/*  790 */         doOall8(true, true, false, true);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  798 */       throw localSQLException;
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  805 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  806 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */     finally
/*      */     {
/*  810 */       this.rowsProcessed = this.t4Connection.all8.rowsProcessed;
/*  811 */       this.validRows = this.t4Connection.all8.getNumRows();
/*      */     }
/*      */ 
/*  814 */     this.needToParse = false;
/*      */ 
/*  817 */     for (int i = 0; i < this.numberOfDefinePositions; i++) {
/*  818 */       this.accessors[i].initMetadata();
/*      */     }
/*  820 */     this.needToPrepareDefineBuffer = false;
/*      */   }
/*      */ 
/*      */   void executeMaybeDescribe()
/*      */     throws SQLException
/*      */   {
/*  828 */     if (!this.t4Connection.useFetchSizeWithLongColumn)
/*      */     {
/*  830 */       super.executeMaybeDescribe();
/*      */     }
/*      */     else
/*      */     {
/*  838 */       if (this.rowPrefetchChanged)
/*      */       {
/*  840 */         if ((this.streamList == null) && (this.rowPrefetch != this.definesBatchSize)) {
/*  841 */           this.needToPrepareDefineBuffer = true;
/*      */         }
/*  843 */         this.rowPrefetchChanged = false;
/*      */       }
/*      */ 
/*  846 */       if (!this.needToPrepareDefineBuffer)
/*      */       {
/*  850 */         if (this.accessors == null)
/*      */         {
/*  854 */           this.needToPrepareDefineBuffer = true;
/*  855 */         } else if (this.columnsDefinedByUser) {
/*  856 */           this.needToPrepareDefineBuffer = (!checkAccessorsUsable());
/*      */         }
/*      */       }
/*  859 */       boolean bool = false;
/*      */       try
/*      */       {
/*  864 */         this.isExecuting = true;
/*      */ 
/*  866 */         if (this.needToPrepareDefineBuffer)
/*      */         {
/*  868 */           executeForDescribe();
/*      */ 
/*  870 */           bool = true;
/*      */         }
/*      */         else
/*      */         {
/*  877 */           int i = this.accessors.length;
/*      */ 
/*  879 */           for (int j = this.numberOfDefinePositions; j < i; j++)
/*      */           {
/*  881 */             Accessor localAccessor = this.accessors[j];
/*      */ 
/*  883 */             if (localAccessor != null) {
/*  884 */               localAccessor.rowSpaceIndicator = null;
/*      */             }
/*      */           }
/*  887 */           executeForRows(bool);
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException localSQLException)
/*      */       {
/*  893 */         this.needToParse = true;
/*  894 */         throw localSQLException;
/*      */       }
/*      */       finally
/*      */       {
/*  898 */         this.isExecuting = false;
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
/*  946 */         doOall8(this.needToParse, !paramBoolean, true, false);
/*      */ 
/*  948 */         this.needToParse = false;
/*      */       }
/*      */       finally
/*      */       {
/*  952 */         this.validRows = this.t4Connection.all8.getNumRows();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  960 */       throw localSQLException;
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  967 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/*  968 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */   }
/*      */ 
/*      */   void fetch()
/*      */     throws SQLException
/*      */   {
/*  996 */     if (this.streamList != null)
/*      */     {
/* 1000 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1004 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException1)
/*      */         {
/* 1008 */           ((T4CConnection)this.connection).handleIOException(localIOException1);
/* 1009 */           DatabaseError.throwSqlException(localIOException1);
/*      */         }
/*      */ 
/* 1012 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1018 */       doOall8(false, false, true, false);
/*      */ 
/* 1020 */       this.validRows = this.t4Connection.all8.getNumRows();
/*      */     }
/*      */     catch (IOException localIOException2)
/*      */     {
/* 1027 */       ((T4CConnection)this.connection).handleIOException(localIOException2);
/* 1028 */       DatabaseError.throwSqlException(localIOException2);
/*      */     }
/*      */   }
/*      */ 
/*      */   void continueReadRow(int paramInt)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 1049 */       if (!this.connection.useFetchSizeWithLongColumn)
/*      */       {
/* 1051 */         T4C8Oall localT4C8Oall = this.t4Connection.all8;
/*      */ 
/* 1053 */         localT4C8Oall.continueReadRow(paramInt);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/* 1061 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1062 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/* 1066 */       if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110))
/*      */       {
/* 1069 */         this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
/*      */       }
/*      */       else
/*      */       {
/* 1074 */         throw localSQLException;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void doClose()
/*      */     throws SQLException
/*      */   {
/* 1101 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CStatement.do_close");
/*      */     try
/*      */     {
/* 1105 */       if (this.cursorId != 0)
/*      */       {
/* 1107 */         this.t4Connection.cursorToClose[(this.t4Connection.cursorToCloseOffset++)] = this.cursorId;
/*      */ 
/* 1110 */         if (this.t4Connection.cursorToCloseOffset >= this.t4Connection.cursorToClose.length)
/*      */         {
/* 1113 */           this.t4Connection.sendPiggyBackedMessages();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/* 1122 */       ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1123 */       DatabaseError.throwSqlException(localIOException);
/*      */     }
/*      */ 
/* 1126 */     this.tmpByteArray = null;
/* 1127 */     this.tmpBindsByteArray = null;
/* 1128 */     this.definedColumnType = null;
/* 1129 */     this.definedColumnSize = null;
/* 1130 */     this.definedColumnFormOfUse = null;
/* 1131 */     this.oacdefSent = null;
/*      */   }
/*      */ 
/*      */   void closeQuery()
/*      */     throws SQLException
/*      */   {
/* 1153 */     this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CStatement.closeQuery");
/*      */ 
/* 1156 */     if (this.streamList != null)
/*      */     {
/* 1160 */       while (this.nextStream != null)
/*      */       {
/*      */         try
/*      */         {
/* 1164 */           this.nextStream.close();
/*      */         }
/*      */         catch (IOException localIOException)
/*      */         {
/* 1168 */           ((T4CConnection)this.connection).handleIOException(localIOException);
/* 1169 */           DatabaseError.throwSqlException(localIOException);
/*      */         }
/*      */ 
/* 1172 */         this.nextStream = this.nextStream.nextStream;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   T4CStatement(PhysicalConnection paramPhysicalConnection, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1219 */     super(paramPhysicalConnection, 1, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);
/* 1220 */     this.nbPostPonedColumns = new int[1];
/* 1221 */     this.nbPostPonedColumns[0] = 0;
/* 1222 */     this.indexOfPostPonedColumn = new int[1][3];
/* 1223 */     this.t4Connection = ((T4CConnection)paramPhysicalConnection);
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CStatement
 * JD-Core Version:    0.6.0
 */