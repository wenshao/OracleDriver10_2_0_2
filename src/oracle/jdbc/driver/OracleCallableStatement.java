/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Map;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.BFILE;
/*      */ import oracle.sql.BINARY_DOUBLE;
/*      */ import oracle.sql.BINARY_FLOAT;
/*      */ import oracle.sql.BLOB;
/*      */ import oracle.sql.CHAR;
/*      */ import oracle.sql.CLOB;
/*      */ import oracle.sql.CustomDatum;
/*      */ import oracle.sql.CustomDatumFactory;
/*      */ import oracle.sql.DATE;
/*      */ import oracle.sql.Datum;
/*      */ import oracle.sql.INTERVALDS;
/*      */ import oracle.sql.INTERVALYM;
/*      */ import oracle.sql.NUMBER;
/*      */ import oracle.sql.OPAQUE;
/*      */ import oracle.sql.ORAData;
/*      */ import oracle.sql.ORADataFactory;
/*      */ import oracle.sql.RAW;
/*      */ import oracle.sql.REF;
/*      */ import oracle.sql.ROWID;
/*      */ import oracle.sql.STRUCT;
/*      */ import oracle.sql.StructDescriptor;
/*      */ import oracle.sql.TIMESTAMP;
/*      */ import oracle.sql.TIMESTAMPLTZ;
/*      */ import oracle.sql.TIMESTAMPTZ;
/*      */ 
/*      */ public abstract class OracleCallableStatement extends OraclePreparedStatement
/*      */   implements oracle.jdbc.internal.OracleCallableStatement
/*      */ {
/*   49 */   boolean atLeastOneOrdinalParameter = false;
/*   50 */   boolean atLeastOneNamedParameter = false;
/*      */ 
/*   53 */   String[] namedParameters = new String[8];
/*      */ 
/*   56 */   int parameterCount = 0;
/*      */ 
/*   59 */   final String errMsgMixedBind = "Ordinal binding and Named binding cannot be combined!";
/*      */ 
/* 4952 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:28_PST_2006";
/*      */ 
/*      */   OracleCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*   80 */     this(paramPhysicalConnection, paramString, paramInt1, paramInt2, 1003, 1007);
/*      */   }
/*      */ 
/*      */   OracleCallableStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*   99 */     super(paramPhysicalConnection, paramString, 1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*  105 */     this.statementType = 2;
/*      */   }
/*      */ 
/*      */   void registerOutParameterInternal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
/*      */     throws SQLException
/*      */   {
/*  119 */     int i = paramInt1 - 1;
/*  120 */     if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
/*  121 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  123 */     if (paramInt2 == 0)
/*  124 */       DatabaseError.throwSqlException(4);
/*  125 */     int j = getInternalType(paramInt2);
/*      */ 
/*  127 */     resetBatch();
/*  128 */     this.currentRowNeedToPrepareBinds = true;
/*      */ 
/*  130 */     if (this.currentRowBindAccessors == null) {
/*  131 */       this.currentRowBindAccessors = new Accessor[this.numberOfBindPositions];
/*      */     }
/*      */ 
/*  134 */     switch (paramInt2)
/*      */     {
/*      */     case -4:
/*      */     case -3:
/*      */     case -1:
/*      */     case 1:
/*      */     case 12:
/*  141 */       break;
/*      */     default:
/*  144 */       paramInt4 = 0;
/*      */     }
/*      */ 
/*  148 */     this.currentRowBindAccessors[i] = allocateAccessor(j, paramInt2, i + 1, paramInt4, this.currentRowFormOfUse[i], paramString, true);
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(int paramInt1, int paramInt2, String paramString)
/*      */     throws SQLException
/*      */   {
/*  183 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  184 */       DatabaseError.throwSqlException(60, "empty Object name");
/*      */     }
/*      */ 
/*  190 */     synchronized (this.connection)
/*      */     {
/*  192 */       synchronized (this)
/*      */       {
/*  194 */         registerOutParameterInternal(paramInt1, paramInt2, 0, 0, paramString);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*  229 */     registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*  262 */     registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
/*      */   }
/*      */ 
/*      */   public synchronized void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/*  283 */     registerOutParameterInternal(paramInt1, paramInt2, paramInt3, paramInt4, null);
/*      */   }
/*      */ 
/*      */   public synchronized void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/*  304 */     int i = addNamedPara(paramString);
/*  305 */     registerOutParameter(i, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   void resetBatch()
/*      */   {
/*  316 */     this.batch = 1;
/*      */   }
/*      */ 
/*      */   public synchronized void setExecuteBatch(int paramInt)
/*      */     throws SQLException
/*      */   {
/*      */   }
/*      */ 
/*      */   public synchronized int sendBatch()
/*      */     throws SQLException
/*      */   {
/*  347 */     return this.validRows;
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  363 */     registerOutParameter(paramInt1, paramInt2, 0, -1);
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/*  373 */     registerOutParameter(paramInt1, paramInt2, paramInt3, -1);
/*      */   }
/*      */ 
/*      */   public boolean wasNull()
/*      */     throws SQLException
/*      */   {
/*  379 */     return wasNullValue();
/*      */   }
/*      */ 
/*      */   public String getString(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  389 */     if (this.closed) {
/*  390 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  392 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  394 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  398 */     Accessor localAccessor = null;
/*  399 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  402 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  404 */     this.lastIndex = paramInt;
/*      */ 
/*  406 */     if (this.streamList != null) {
/*  407 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  410 */     return localAccessor.getString(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Datum getOracleObject(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  420 */     if (this.closed) {
/*  421 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  423 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  425 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  429 */     Accessor localAccessor = null;
/*  430 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  433 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  435 */     this.lastIndex = paramInt;
/*      */ 
/*  437 */     if (this.streamList != null) {
/*  438 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  441 */     return localAccessor.getOracleObject(this.currentRank);
/*      */   }
/*      */ 
/*      */   public ROWID getROWID(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  451 */     if (this.closed) {
/*  452 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  454 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  456 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  460 */     Accessor localAccessor = null;
/*  461 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  464 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  466 */     this.lastIndex = paramInt;
/*      */ 
/*  468 */     if (this.streamList != null) {
/*  469 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  472 */     return localAccessor.getROWID(this.currentRank);
/*      */   }
/*      */ 
/*      */   public NUMBER getNUMBER(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  482 */     if (this.closed) {
/*  483 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  485 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  487 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  491 */     Accessor localAccessor = null;
/*  492 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  495 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  497 */     this.lastIndex = paramInt;
/*      */ 
/*  499 */     if (this.streamList != null) {
/*  500 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  503 */     return localAccessor.getNUMBER(this.currentRank);
/*      */   }
/*      */ 
/*      */   public DATE getDATE(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  513 */     if (this.closed) {
/*  514 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  516 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  518 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  522 */     Accessor localAccessor = null;
/*  523 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  526 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  528 */     this.lastIndex = paramInt;
/*      */ 
/*  530 */     if (this.streamList != null) {
/*  531 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  534 */     return localAccessor.getDATE(this.currentRank);
/*      */   }
/*      */ 
/*      */   public INTERVALYM getINTERVALYM(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  544 */     if (this.closed) {
/*  545 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  547 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  549 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  553 */     Accessor localAccessor = null;
/*  554 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  557 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  559 */     this.lastIndex = paramInt;
/*      */ 
/*  561 */     if (this.streamList != null) {
/*  562 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  565 */     return localAccessor.getINTERVALYM(this.currentRank);
/*      */   }
/*      */ 
/*      */   public INTERVALDS getINTERVALDS(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  575 */     if (this.closed) {
/*  576 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  578 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  580 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  584 */     Accessor localAccessor = null;
/*  585 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  588 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  590 */     this.lastIndex = paramInt;
/*      */ 
/*  592 */     if (this.streamList != null) {
/*  593 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  596 */     return localAccessor.getINTERVALDS(this.currentRank);
/*      */   }
/*      */ 
/*      */   public TIMESTAMP getTIMESTAMP(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  606 */     if (this.closed) {
/*  607 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  609 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  611 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  615 */     Accessor localAccessor = null;
/*  616 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  619 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  621 */     this.lastIndex = paramInt;
/*      */ 
/*  623 */     if (this.streamList != null) {
/*  624 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  627 */     return localAccessor.getTIMESTAMP(this.currentRank);
/*      */   }
/*      */ 
/*      */   public TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  637 */     if (this.closed) {
/*  638 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  640 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  642 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  646 */     Accessor localAccessor = null;
/*  647 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  650 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  652 */     this.lastIndex = paramInt;
/*      */ 
/*  654 */     if (this.streamList != null) {
/*  655 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  658 */     return localAccessor.getTIMESTAMPTZ(this.currentRank);
/*      */   }
/*      */ 
/*      */   public TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  668 */     if (this.closed) {
/*  669 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  671 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  673 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  677 */     Accessor localAccessor = null;
/*  678 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  681 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  683 */     this.lastIndex = paramInt;
/*      */ 
/*  685 */     if (this.streamList != null) {
/*  686 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  689 */     return localAccessor.getTIMESTAMPLTZ(this.currentRank);
/*      */   }
/*      */ 
/*      */   public REF getREF(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  699 */     if (this.closed) {
/*  700 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  702 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  704 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  708 */     Accessor localAccessor = null;
/*  709 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  712 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  714 */     this.lastIndex = paramInt;
/*      */ 
/*  716 */     if (this.streamList != null) {
/*  717 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  720 */     return localAccessor.getREF(this.currentRank);
/*      */   }
/*      */ 
/*      */   public ARRAY getARRAY(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  730 */     if (this.closed) {
/*  731 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  733 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  735 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  739 */     Accessor localAccessor = null;
/*  740 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  743 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  745 */     this.lastIndex = paramInt;
/*      */ 
/*  747 */     if (this.streamList != null) {
/*  748 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  751 */     return localAccessor.getARRAY(this.currentRank);
/*      */   }
/*      */ 
/*      */   public STRUCT getSTRUCT(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  761 */     if (this.closed) {
/*  762 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  764 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  766 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  770 */     Accessor localAccessor = null;
/*  771 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  774 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  776 */     this.lastIndex = paramInt;
/*      */ 
/*  778 */     if (this.streamList != null) {
/*  779 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  782 */     return localAccessor.getSTRUCT(this.currentRank);
/*      */   }
/*      */ 
/*      */   public OPAQUE getOPAQUE(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  792 */     if (this.closed) {
/*  793 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  795 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  797 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  801 */     Accessor localAccessor = null;
/*  802 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  805 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  807 */     this.lastIndex = paramInt;
/*      */ 
/*  809 */     if (this.streamList != null) {
/*  810 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  813 */     return localAccessor.getOPAQUE(this.currentRank);
/*      */   }
/*      */ 
/*      */   public CHAR getCHAR(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  823 */     if (this.closed) {
/*  824 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  826 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  828 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  832 */     Accessor localAccessor = null;
/*  833 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  836 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  838 */     this.lastIndex = paramInt;
/*      */ 
/*  840 */     if (this.streamList != null) {
/*  841 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  844 */     return localAccessor.getCHAR(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Reader getCharacterStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  854 */     if (this.closed) {
/*  855 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  857 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  859 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  863 */     Accessor localAccessor = null;
/*  864 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  867 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  869 */     this.lastIndex = paramInt;
/*      */ 
/*  871 */     if (this.streamList != null) {
/*  872 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  875 */     return localAccessor.getCharacterStream(this.currentRank);
/*      */   }
/*      */ 
/*      */   public RAW getRAW(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  885 */     if (this.closed) {
/*  886 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  888 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  890 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  894 */     Accessor localAccessor = null;
/*  895 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  898 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  900 */     this.lastIndex = paramInt;
/*      */ 
/*  902 */     if (this.streamList != null) {
/*  903 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  906 */     return localAccessor.getRAW(this.currentRank);
/*      */   }
/*      */ 
/*      */   public BLOB getBLOB(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  917 */     if (this.closed) {
/*  918 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  920 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  922 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  926 */     Accessor localAccessor = null;
/*  927 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  930 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  932 */     this.lastIndex = paramInt;
/*      */ 
/*  934 */     if (this.streamList != null) {
/*  935 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  938 */     return localAccessor.getBLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public CLOB getCLOB(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  948 */     if (this.closed) {
/*  949 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  951 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  953 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  957 */     Accessor localAccessor = null;
/*  958 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  961 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  963 */     this.lastIndex = paramInt;
/*      */ 
/*  965 */     if (this.streamList != null) {
/*  966 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/*  969 */     return localAccessor.getCLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public BFILE getBFILE(int paramInt)
/*      */     throws SQLException
/*      */   {
/*  979 */     if (this.closed) {
/*  980 */       DatabaseError.throwSqlException(9);
/*      */     }
/*  982 */     if (this.atLeastOneNamedParameter)
/*      */     {
/*  984 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/*  988 */     Accessor localAccessor = null;
/*  989 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/*  992 */       DatabaseError.throwSqlException(3);
/*      */     }
/*  994 */     this.lastIndex = paramInt;
/*      */ 
/*  996 */     if (this.streamList != null) {
/*  997 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1000 */     return localAccessor.getBFILE(this.currentRank);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1010 */     if (this.closed) {
/* 1011 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1013 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1015 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1019 */     Accessor localAccessor = null;
/* 1020 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1023 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1025 */     this.lastIndex = paramInt;
/*      */ 
/* 1027 */     if (this.streamList != null) {
/* 1028 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1031 */     return localAccessor.getBoolean(this.currentRank);
/*      */   }
/*      */ 
/*      */   public byte getByte(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1041 */     if (this.closed) {
/* 1042 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1044 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1046 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1050 */     Accessor localAccessor = null;
/* 1051 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1054 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1056 */     this.lastIndex = paramInt;
/*      */ 
/* 1058 */     if (this.streamList != null) {
/* 1059 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1062 */     return localAccessor.getByte(this.currentRank);
/*      */   }
/*      */ 
/*      */   public short getShort(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1072 */     if (this.closed) {
/* 1073 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1075 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1077 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1081 */     Accessor localAccessor = null;
/* 1082 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1085 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1087 */     this.lastIndex = paramInt;
/*      */ 
/* 1089 */     if (this.streamList != null) {
/* 1090 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1093 */     return localAccessor.getShort(this.currentRank);
/*      */   }
/*      */ 
/*      */   public int getInt(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1103 */     if (this.closed) {
/* 1104 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1106 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1108 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1112 */     Accessor localAccessor = null;
/* 1113 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1116 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1118 */     this.lastIndex = paramInt;
/*      */ 
/* 1120 */     if (this.streamList != null) {
/* 1121 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1124 */     return localAccessor.getInt(this.currentRank);
/*      */   }
/*      */ 
/*      */   public long getLong(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1134 */     if (this.closed) {
/* 1135 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1137 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1139 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1143 */     Accessor localAccessor = null;
/* 1144 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1147 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1149 */     this.lastIndex = paramInt;
/*      */ 
/* 1151 */     if (this.streamList != null) {
/* 1152 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1155 */     return localAccessor.getLong(this.currentRank);
/*      */   }
/*      */ 
/*      */   public float getFloat(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1165 */     if (this.closed) {
/* 1166 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1168 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1170 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1174 */     Accessor localAccessor = null;
/* 1175 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1178 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1180 */     this.lastIndex = paramInt;
/*      */ 
/* 1182 */     if (this.streamList != null) {
/* 1183 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1186 */     return localAccessor.getFloat(this.currentRank);
/*      */   }
/*      */ 
/*      */   public double getDouble(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1196 */     if (this.closed) {
/* 1197 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1199 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1201 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1205 */     Accessor localAccessor = null;
/* 1206 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1209 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1211 */     this.lastIndex = paramInt;
/*      */ 
/* 1213 */     if (this.streamList != null) {
/* 1214 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1217 */     return localAccessor.getDouble(this.currentRank);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1227 */     if (this.closed) {
/* 1228 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1230 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1232 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1236 */     Accessor localAccessor = null;
/* 1237 */     if ((paramInt1 <= 0) || (paramInt1 > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt1 - 1)]) == null))
/*      */     {
/* 1240 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1242 */     this.lastIndex = paramInt1;
/*      */ 
/* 1244 */     if (this.streamList != null) {
/* 1245 */       closeUsedStreams(paramInt1);
/*      */     }
/*      */ 
/* 1248 */     return localAccessor.getBigDecimal(this.currentRank);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1258 */     if (this.closed) {
/* 1259 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1261 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1263 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1267 */     Accessor localAccessor = null;
/* 1268 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1271 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1273 */     this.lastIndex = paramInt;
/*      */ 
/* 1275 */     if (this.streamList != null) {
/* 1276 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1279 */     return localAccessor.getBytes(this.currentRank);
/*      */   }
/*      */ 
/*      */   public byte[] privateGetBytes(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1289 */     if (this.closed) {
/* 1290 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1292 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1294 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1298 */     Accessor localAccessor = null;
/* 1299 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1302 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1304 */     this.lastIndex = paramInt;
/*      */ 
/* 1306 */     if (this.streamList != null) {
/* 1307 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1310 */     return localAccessor.privateGetBytes(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Date getDate(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1320 */     if (this.closed) {
/* 1321 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1323 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1325 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1329 */     Accessor localAccessor = null;
/* 1330 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1333 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1335 */     this.lastIndex = paramInt;
/*      */ 
/* 1337 */     if (this.streamList != null) {
/* 1338 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1341 */     return localAccessor.getDate(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1351 */     if (this.closed) {
/* 1352 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1354 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1356 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1360 */     Accessor localAccessor = null;
/* 1361 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1364 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1366 */     this.lastIndex = paramInt;
/*      */ 
/* 1368 */     if (this.streamList != null) {
/* 1369 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1372 */     return localAccessor.getTime(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1382 */     if (this.closed) {
/* 1383 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1385 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1387 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1391 */     Accessor localAccessor = null;
/* 1392 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1395 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1397 */     this.lastIndex = paramInt;
/*      */ 
/* 1399 */     if (this.streamList != null) {
/* 1400 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1403 */     return localAccessor.getTimestamp(this.currentRank);
/*      */   }
/*      */ 
/*      */   public InputStream getAsciiStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1413 */     if (this.closed) {
/* 1414 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1416 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1418 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1422 */     Accessor localAccessor = null;
/* 1423 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1426 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1428 */     this.lastIndex = paramInt;
/*      */ 
/* 1430 */     if (this.streamList != null) {
/* 1431 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1434 */     return localAccessor.getAsciiStream(this.currentRank);
/*      */   }
/*      */ 
/*      */   public InputStream getUnicodeStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1444 */     if (this.closed) {
/* 1445 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1447 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1449 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1453 */     Accessor localAccessor = null;
/* 1454 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1457 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1459 */     this.lastIndex = paramInt;
/*      */ 
/* 1461 */     if (this.streamList != null) {
/* 1462 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1465 */     return localAccessor.getUnicodeStream(this.currentRank);
/*      */   }
/*      */ 
/*      */   public InputStream getBinaryStream(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1475 */     if (this.closed) {
/* 1476 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1478 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1480 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1484 */     Accessor localAccessor = null;
/* 1485 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1488 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1490 */     this.lastIndex = paramInt;
/*      */ 
/* 1492 */     if (this.streamList != null) {
/* 1493 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1496 */     return localAccessor.getBinaryStream(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Object getObject(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1506 */     if (this.closed) {
/* 1507 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1509 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1511 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1515 */     Accessor localAccessor = null;
/* 1516 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1519 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1521 */     this.lastIndex = paramInt;
/*      */ 
/* 1523 */     if (this.streamList != null) {
/* 1524 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1527 */     return localAccessor.getObject(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Object getAnyDataEmbeddedObject(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1539 */     if (this.closed) {
/* 1540 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1542 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1544 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1548 */     Accessor localAccessor = null;
/* 1549 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1552 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1554 */     this.lastIndex = paramInt;
/*      */ 
/* 1556 */     if (this.streamList != null) {
/* 1557 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1560 */     return localAccessor.getAnyDataEmbeddedObject(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Object getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
/*      */     throws SQLException
/*      */   {
/* 1570 */     if (this.closed) {
/* 1571 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1573 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1575 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1579 */     Accessor localAccessor = null;
/* 1580 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1583 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1585 */     this.lastIndex = paramInt;
/*      */ 
/* 1587 */     if (this.streamList != null) {
/* 1588 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1591 */     return localAccessor.getCustomDatum(this.currentRank, paramCustomDatumFactory);
/*      */   }
/*      */ 
/*      */   public Object getORAData(int paramInt, ORADataFactory paramORADataFactory)
/*      */     throws SQLException
/*      */   {
/* 1601 */     if (this.closed) {
/* 1602 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1604 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1606 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1610 */     Accessor localAccessor = null;
/* 1611 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1614 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1616 */     this.lastIndex = paramInt;
/*      */ 
/* 1618 */     if (this.streamList != null) {
/* 1619 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1622 */     return localAccessor.getORAData(this.currentRank, paramORADataFactory);
/*      */   }
/*      */ 
/*      */   public ResultSet getCursor(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1632 */     if (this.closed) {
/* 1633 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1635 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1637 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1641 */     Accessor localAccessor = null;
/* 1642 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1645 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1647 */     this.lastIndex = paramInt;
/*      */ 
/* 1649 */     if (this.streamList != null) {
/* 1650 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1653 */     return localAccessor.getCursor(this.currentRank);
/*      */   }
/*      */ 
/*      */   public synchronized void clearParameters()
/*      */     throws SQLException
/*      */   {
/* 1662 */     super.clearParameters();
/*      */   }
/*      */ 
/*      */   public Object getObject(int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1682 */     if (this.closed) {
/* 1683 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1685 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1687 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1691 */     Accessor localAccessor = null;
/* 1692 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1695 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1697 */     this.lastIndex = paramInt;
/*      */ 
/* 1699 */     if (this.streamList != null) {
/* 1700 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1703 */     return localAccessor.getObject(this.currentRank, paramMap);
/*      */   }
/*      */ 
/*      */   public Ref getRef(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1713 */     if (this.closed) {
/* 1714 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1716 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1718 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1722 */     Accessor localAccessor = null;
/* 1723 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1726 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1728 */     this.lastIndex = paramInt;
/*      */ 
/* 1730 */     if (this.streamList != null) {
/* 1731 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1734 */     return localAccessor.getREF(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Blob getBlob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1744 */     if (this.closed) {
/* 1745 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1747 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1749 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1753 */     Accessor localAccessor = null;
/* 1754 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1757 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1759 */     this.lastIndex = paramInt;
/*      */ 
/* 1761 */     if (this.streamList != null) {
/* 1762 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1765 */     return localAccessor.getBLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Clob getClob(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1775 */     if (this.closed) {
/* 1776 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1778 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1780 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1784 */     Accessor localAccessor = null;
/* 1785 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1788 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1790 */     this.lastIndex = paramInt;
/*      */ 
/* 1792 */     if (this.streamList != null) {
/* 1793 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1796 */     return localAccessor.getCLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Array getArray(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1806 */     if (this.closed) {
/* 1807 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1809 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1811 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1815 */     Accessor localAccessor = null;
/* 1816 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1819 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1821 */     this.lastIndex = paramInt;
/*      */ 
/* 1823 */     if (this.streamList != null) {
/* 1824 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1827 */     return localAccessor.getARRAY(this.currentRank);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1837 */     if (this.closed) {
/* 1838 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1840 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1842 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1846 */     Accessor localAccessor = null;
/* 1847 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1850 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1852 */     this.lastIndex = paramInt;
/*      */ 
/* 1854 */     if (this.streamList != null) {
/* 1855 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1858 */     return localAccessor.getBigDecimal(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Date getDate(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1868 */     if (this.closed) {
/* 1869 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1871 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1873 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1877 */     Accessor localAccessor = null;
/* 1878 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1881 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1883 */     this.lastIndex = paramInt;
/*      */ 
/* 1885 */     if (this.streamList != null) {
/* 1886 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1889 */     return localAccessor.getDate(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Time getTime(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1899 */     if (this.closed) {
/* 1900 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1902 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1904 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1908 */     Accessor localAccessor = null;
/* 1909 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1912 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1914 */     this.lastIndex = paramInt;
/*      */ 
/* 1916 */     if (this.streamList != null) {
/* 1917 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1920 */     return localAccessor.getTime(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 1930 */     if (this.closed) {
/* 1931 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 1933 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 1935 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 1939 */     Accessor localAccessor = null;
/* 1940 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 1943 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 1945 */     this.lastIndex = paramInt;
/*      */ 
/* 1947 */     if (this.streamList != null) {
/* 1948 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 1951 */     return localAccessor.getTimestamp(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void addBatch()
/*      */     throws SQLException
/*      */   {
/* 1997 */     if (this.currentRowBindAccessors != null)
/*      */     {
/* 1999 */       DatabaseError.throwSqlException(90, "Stored procedure with out or inout parameters cannot be batched");
/*      */     }
/*      */ 
/* 2003 */     super.addBatch();
/*      */   }
/*      */ 
/*      */   protected void alwaysOnClose()
/*      */     throws SQLException
/*      */   {
/* 2013 */     this.sqlObject.resetNamedParameters();
/*      */ 
/* 2016 */     this.parameterCount = 0;
/* 2017 */     this.atLeastOneOrdinalParameter = false;
/* 2018 */     this.atLeastOneNamedParameter = false;
/*      */ 
/* 2020 */     super.alwaysOnClose();
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2057 */     registerOutParameter(paramString, paramInt, 0, -1);
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(String paramString, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 2088 */     registerOutParameter(paramString, paramInt1, paramInt2, -1);
/*      */   }
/*      */ 
/*      */   public void registerOutParameter(String paramString1, int paramInt, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2131 */     int i = addNamedPara(paramString1);
/* 2132 */     registerOutParameter(i, paramInt, paramString2);
/*      */   }
/*      */ 
/*      */   public URL getURL(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2158 */     if (this.closed) {
/* 2159 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 2161 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 2163 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 2167 */     Accessor localAccessor = null;
/* 2168 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 2171 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 2173 */     this.lastIndex = paramInt;
/*      */ 
/* 2175 */     if (this.streamList != null) {
/* 2176 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 2179 */     return localAccessor.getURL(this.currentRank);
/*      */   }
/*      */ 
/*      */   public void setURL(String paramString, URL paramURL)
/*      */     throws SQLException
/*      */   {
/* 2199 */     int i = addNamedPara(paramString);
/* 2200 */     setURLInternal(i, paramURL);
/*      */   }
/*      */ 
/*      */   public void setNull(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2218 */     int i = addNamedPara(paramString);
/* 2219 */     setNullInternal(i, paramInt);
/*      */   }
/*      */ 
/*      */   public void setBoolean(String paramString, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2238 */     int i = addNamedPara(paramString);
/* 2239 */     setBooleanInternal(i, paramBoolean);
/*      */   }
/*      */ 
/*      */   public void setByte(String paramString, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 2258 */     int i = addNamedPara(paramString);
/* 2259 */     setByteInternal(i, paramByte);
/*      */   }
/*      */ 
/*      */   public void setShort(String paramString, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 2278 */     int i = addNamedPara(paramString);
/* 2279 */     setShortInternal(i, paramShort);
/*      */   }
/*      */ 
/*      */   public void setInt(String paramString, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2298 */     int i = addNamedPara(paramString);
/* 2299 */     setIntInternal(i, paramInt);
/*      */   }
/*      */ 
/*      */   public void setLong(String paramString, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 2318 */     int i = addNamedPara(paramString);
/* 2319 */     setLongInternal(i, paramLong);
/*      */   }
/*      */ 
/*      */   public void setFloat(String paramString, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 2338 */     int i = addNamedPara(paramString);
/* 2339 */     setFloatInternal(i, paramFloat);
/*      */   }
/*      */ 
/*      */   public void setBinaryFloat(String paramString, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 2356 */     int i = addNamedPara(paramString);
/* 2357 */     setBinaryFloatInternal(i, paramFloat);
/*      */   }
/*      */ 
/*      */   public void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
/*      */     throws SQLException
/*      */   {
/* 2371 */     int i = addNamedPara(paramString);
/* 2372 */     setBinaryFloatInternal(i, paramBINARY_FLOAT);
/*      */   }
/*      */ 
/*      */   public void setDouble(String paramString, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 2391 */     int i = addNamedPara(paramString);
/* 2392 */     setDoubleInternal(i, paramDouble);
/*      */   }
/*      */ 
/*      */   public void setBinaryDouble(String paramString, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 2409 */     int i = addNamedPara(paramString);
/* 2410 */     setBinaryDoubleInternal(i, paramDouble);
/*      */   }
/*      */ 
/*      */   public void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
/*      */     throws SQLException
/*      */   {
/* 2425 */     int i = addNamedPara(paramString);
/* 2426 */     setBinaryDoubleInternal(i, paramBINARY_DOUBLE);
/*      */   }
/*      */ 
/*      */   public void setBigDecimal(String paramString, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 2446 */     int i = addNamedPara(paramString);
/* 2447 */     setBigDecimalInternal(i, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public void setString(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2469 */     int i = addNamedPara(paramString1);
/* 2470 */     setStringInternal(i, paramString2);
/*      */   }
/*      */ 
/*      */   public void setStringForClob(String paramString1, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2494 */     int i = addNamedPara(paramString1);
/* 2495 */     if ((paramString2 == null) || (paramString2.length() == 0))
/*      */     {
/* 2497 */       setNull(i, 2005);
/* 2498 */       return;
/*      */     }
/* 2500 */     setStringForClob(i, paramString2);
/*      */   }
/*      */ 
/*      */   public void setStringForClob(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 2517 */     if ((paramString == null) || (paramString.length() == 0))
/*      */     {
/* 2519 */       setNull(paramInt, 2005);
/* 2520 */       return;
/*      */     }
/* 2522 */     synchronized (this.connection)
/*      */     {
/* 2524 */       synchronized (this.connection)
/*      */       {
/* 2526 */         setStringForClobCritical(paramInt, paramString);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setBytes(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2549 */     int i = addNamedPara(paramString);
/* 2550 */     setBytesInternal(i, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2572 */     int i = addNamedPara(paramString);
/* 2573 */     setBytesForBlob(i, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public void setBytesForBlob(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 2589 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
/*      */     {
/* 2591 */       setNull(paramInt, 2004);
/* 2592 */       return;
/*      */     }
/* 2594 */     synchronized (this.connection)
/*      */     {
/* 2596 */       synchronized (this.connection)
/*      */       {
/* 2598 */         setBytesForBlobCritical(paramInt, paramArrayOfByte);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDate(String paramString, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 2619 */     int i = addNamedPara(paramString);
/* 2620 */     setDateInternal(i, paramDate);
/*      */   }
/*      */ 
/*      */   public void setTime(String paramString, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 2639 */     int i = addNamedPara(paramString);
/* 2640 */     setTimeInternal(i, paramTime);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(String paramString, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 2660 */     int i = addNamedPara(paramString);
/* 2661 */     setTimestampInternal(i, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2689 */     int i = addNamedPara(paramString);
/* 2690 */     setAsciiStreamInternal(i, paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2717 */     int i = addNamedPara(paramString);
/* 2718 */     setBinaryStreamInternal(i, paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */   public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 2759 */     int i = addNamedPara(paramString);
/* 2760 */     setObjectInternal(i, paramObject, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setObject(String paramString, Object paramObject, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2781 */     setObject(paramString, paramObject, paramInt, 0);
/*      */   }
/*      */ 
/*      */   public void setObject(String paramString, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 2821 */     int i = addNamedPara(paramString);
/* 2822 */     setObjectInternal(i, paramObject);
/*      */   }
/*      */ 
/*      */   public void setCharacterStream(String paramString, Reader paramReader, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 2853 */     int i = addNamedPara(paramString);
/* 2854 */     setCharacterStreamInternal(i, paramReader, paramInt);
/*      */   }
/*      */ 
/*      */   public void setDate(String paramString, Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2880 */     int i = addNamedPara(paramString);
/* 2881 */     setDateInternal(i, paramDate, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void setTime(String paramString, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2907 */     int i = addNamedPara(paramString);
/* 2908 */     setTimeInternal(i, paramTime, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 2935 */     int i = addNamedPara(paramString);
/* 2936 */     setTimestampInternal(i, paramTimestamp, paramCalendar);
/*      */   }
/*      */ 
/*      */   public void setNull(String paramString1, int paramInt, String paramString2)
/*      */     throws SQLException
/*      */   {
/* 2973 */     int i = addNamedPara(paramString1);
/* 2974 */     setNullInternal(i, paramInt, paramString2);
/*      */   }
/*      */ 
/*      */   public String getString(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3000 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3002 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3006 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3009 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3011 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3014 */     i++;
/*      */ 
/* 3016 */     Accessor localAccessor = null;
/* 3017 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3020 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3022 */     this.lastIndex = i;
/*      */ 
/* 3024 */     if (this.streamList != null) {
/* 3025 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3028 */     return localAccessor.getString(this.currentRank);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3047 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3049 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3053 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3056 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3058 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3061 */     i++;
/*      */ 
/* 3063 */     Accessor localAccessor = null;
/* 3064 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3067 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3069 */     this.lastIndex = i;
/*      */ 
/* 3071 */     if (this.streamList != null) {
/* 3072 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3075 */     return localAccessor.getBoolean(this.currentRank);
/*      */   }
/*      */ 
/*      */   public byte getByte(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3094 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3096 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3100 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3103 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3105 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3108 */     i++;
/*      */ 
/* 3110 */     Accessor localAccessor = null;
/* 3111 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3114 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3116 */     this.lastIndex = i;
/*      */ 
/* 3118 */     if (this.streamList != null) {
/* 3119 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3122 */     return localAccessor.getByte(this.currentRank);
/*      */   }
/*      */ 
/*      */   public short getShort(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3141 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3143 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3147 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3150 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3152 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3155 */     i++;
/*      */ 
/* 3157 */     Accessor localAccessor = null;
/* 3158 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3161 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3163 */     this.lastIndex = i;
/*      */ 
/* 3165 */     if (this.streamList != null) {
/* 3166 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3169 */     return localAccessor.getShort(this.currentRank);
/*      */   }
/*      */ 
/*      */   public int getInt(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3189 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3191 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3195 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3198 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3200 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3203 */     i++;
/*      */ 
/* 3205 */     Accessor localAccessor = null;
/* 3206 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3209 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3211 */     this.lastIndex = i;
/*      */ 
/* 3213 */     if (this.streamList != null) {
/* 3214 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3217 */     return localAccessor.getInt(this.currentRank);
/*      */   }
/*      */ 
/*      */   public long getLong(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3237 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3239 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3243 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3246 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3248 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3251 */     i++;
/*      */ 
/* 3253 */     Accessor localAccessor = null;
/* 3254 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3257 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3259 */     this.lastIndex = i;
/*      */ 
/* 3261 */     if (this.streamList != null) {
/* 3262 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3265 */     return localAccessor.getLong(this.currentRank);
/*      */   }
/*      */ 
/*      */   public float getFloat(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3284 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3286 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3290 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3293 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3295 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3298 */     i++;
/*      */ 
/* 3300 */     Accessor localAccessor = null;
/* 3301 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3304 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3306 */     this.lastIndex = i;
/*      */ 
/* 3308 */     if (this.streamList != null) {
/* 3309 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3312 */     return localAccessor.getFloat(this.currentRank);
/*      */   }
/*      */ 
/*      */   public double getDouble(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3331 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3333 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3337 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3340 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3342 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3345 */     i++;
/*      */ 
/* 3347 */     Accessor localAccessor = null;
/* 3348 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3351 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3353 */     this.lastIndex = i;
/*      */ 
/* 3355 */     if (this.streamList != null) {
/* 3356 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3359 */     return localAccessor.getDouble(this.currentRank);
/*      */   }
/*      */ 
/*      */   public byte[] getBytes(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3379 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3381 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3385 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3388 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3390 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3393 */     i++;
/*      */ 
/* 3395 */     Accessor localAccessor = null;
/* 3396 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3399 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3401 */     this.lastIndex = i;
/*      */ 
/* 3403 */     if (this.streamList != null) {
/* 3404 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3407 */     return localAccessor.getBytes(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3426 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3428 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3432 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3435 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3437 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3440 */     i++;
/*      */ 
/* 3442 */     Accessor localAccessor = null;
/* 3443 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3446 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3448 */     this.lastIndex = i;
/*      */ 
/* 3450 */     if (this.streamList != null) {
/* 3451 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3454 */     return localAccessor.getDate(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3473 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3475 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3479 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3482 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3484 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3487 */     i++;
/*      */ 
/* 3489 */     Accessor localAccessor = null;
/* 3490 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3493 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3495 */     this.lastIndex = i;
/*      */ 
/* 3497 */     if (this.streamList != null) {
/* 3498 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3501 */     return localAccessor.getTime(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3520 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3522 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3526 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3529 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3531 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3534 */     i++;
/*      */ 
/* 3536 */     Accessor localAccessor = null;
/* 3537 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3540 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3542 */     this.lastIndex = i;
/*      */ 
/* 3544 */     if (this.streamList != null) {
/* 3545 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3548 */     return localAccessor.getTimestamp(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3574 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3576 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3580 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3583 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3585 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3588 */     i++;
/*      */ 
/* 3590 */     Accessor localAccessor = null;
/* 3591 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3594 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3596 */     this.lastIndex = i;
/*      */ 
/* 3598 */     if (this.streamList != null) {
/* 3599 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3602 */     return localAccessor.getObject(this.currentRank);
/*      */   }
/*      */ 
/*      */   public BigDecimal getBigDecimal(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3622 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3624 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3628 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3631 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3633 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3636 */     i++;
/*      */ 
/* 3638 */     Accessor localAccessor = null;
/* 3639 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3642 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3644 */     this.lastIndex = i;
/*      */ 
/* 3646 */     if (this.streamList != null) {
/* 3647 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3650 */     return localAccessor.getBigDecimal(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Object getObject(String paramString, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 3676 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3678 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3682 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3685 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3687 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3690 */     i++;
/*      */ 
/* 3692 */     Accessor localAccessor = null;
/* 3693 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3696 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3698 */     this.lastIndex = i;
/*      */ 
/* 3700 */     if (this.streamList != null) {
/* 3701 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3704 */     return localAccessor.getObject(this.currentRank, paramMap);
/*      */   }
/*      */ 
/*      */   public Ref getRef(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3724 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3726 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3730 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3733 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3735 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3738 */     i++;
/*      */ 
/* 3740 */     Accessor localAccessor = null;
/* 3741 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3744 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3746 */     this.lastIndex = i;
/*      */ 
/* 3748 */     if (this.streamList != null) {
/* 3749 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3752 */     return localAccessor.getREF(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Blob getBlob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3772 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3774 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3778 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3781 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3783 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3786 */     i++;
/*      */ 
/* 3788 */     Accessor localAccessor = null;
/* 3789 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3792 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3794 */     this.lastIndex = i;
/*      */ 
/* 3796 */     if (this.streamList != null) {
/* 3797 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3800 */     return localAccessor.getBLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Clob getClob(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3819 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3821 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3825 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3828 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3830 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3833 */     i++;
/*      */ 
/* 3835 */     Accessor localAccessor = null;
/* 3836 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3839 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3841 */     this.lastIndex = i;
/*      */ 
/* 3843 */     if (this.streamList != null) {
/* 3844 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3847 */     return localAccessor.getCLOB(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Array getArray(String paramString)
/*      */     throws SQLException
/*      */   {
/* 3867 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3869 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3873 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3876 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3878 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3881 */     i++;
/*      */ 
/* 3883 */     Accessor localAccessor = null;
/* 3884 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3887 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3889 */     this.lastIndex = i;
/*      */ 
/* 3891 */     if (this.streamList != null) {
/* 3892 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3895 */     return localAccessor.getARRAY(this.currentRank);
/*      */   }
/*      */ 
/*      */   public Date getDate(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3923 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3925 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3929 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3932 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3934 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3937 */     i++;
/*      */ 
/* 3939 */     Accessor localAccessor = null;
/* 3940 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3943 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 3945 */     this.lastIndex = i;
/*      */ 
/* 3947 */     if (this.streamList != null) {
/* 3948 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 3951 */     return localAccessor.getDate(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Time getTime(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 3979 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 3981 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 3985 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 3988 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 3990 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 3993 */     i++;
/*      */ 
/* 3995 */     Accessor localAccessor = null;
/* 3996 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 3999 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 4001 */     this.lastIndex = i;
/*      */ 
/* 4003 */     if (this.streamList != null) {
/* 4004 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 4007 */     return localAccessor.getTime(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4036 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 4038 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 4042 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 4045 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 4047 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 4050 */     i++;
/*      */ 
/* 4052 */     Accessor localAccessor = null;
/* 4053 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 4056 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 4058 */     this.lastIndex = i;
/*      */ 
/* 4060 */     if (this.streamList != null) {
/* 4061 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 4064 */     return localAccessor.getTimestamp(this.currentRank, paramCalendar);
/*      */   }
/*      */ 
/*      */   public URL getURL(String paramString)
/*      */     throws SQLException
/*      */   {
/* 4086 */     if (!this.atLeastOneNamedParameter)
/*      */     {
/* 4088 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 4092 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 4095 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 4097 */       if (str == this.namedParameters[i])
/*      */         break;
/*      */     }
/* 4100 */     i++;
/*      */ 
/* 4102 */     Accessor localAccessor = null;
/* 4103 */     if ((i <= 0) || (i > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(i - 1)]) == null))
/*      */     {
/* 4106 */       DatabaseError.throwSqlException(6);
/*      */     }
/* 4108 */     this.lastIndex = i;
/*      */ 
/* 4110 */     if (this.streamList != null) {
/* 4111 */       closeUsedStreams(i);
/*      */     }
/*      */ 
/* 4114 */     return localAccessor.getURL(this.currentRank);
/*      */   }
/*      */ 
/*      */   public synchronized void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws SQLException
/*      */   {
/* 4153 */     int i = paramInt1 - 1;
/* 4154 */     if ((i < 0) || (paramInt1 > this.numberOfBindPositions)) {
/* 4155 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 4157 */     int j = getInternalType(paramInt3);
/*      */ 
/* 4159 */     resetBatch();
/* 4160 */     this.currentRowNeedToPrepareBinds = true;
/*      */ 
/* 4162 */     if (this.currentRowBindAccessors == null) {
/* 4163 */       this.currentRowBindAccessors = new Accessor[this.numberOfBindPositions];
/*      */     }
/* 4165 */     this.currentRowBindAccessors[i] = allocateIndexTableAccessor(paramInt3, j, paramInt4, paramInt2, this.currentRowFormOfUse[i], true);
/*      */ 
/* 4173 */     this.hasIbtBind = true;
/*      */   }
/*      */ 
/*      */   PlsqlIndexTableAccessor allocateIndexTableAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 4188 */     return new PlsqlIndexTableAccessor(this, paramInt1, paramInt2, paramInt3, paramInt4, paramShort, paramBoolean);
/*      */   }
/*      */ 
/*      */   public synchronized Object getPlsqlIndexTable(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4214 */     Datum[] arrayOfDatum = getOraclePlsqlIndexTable(paramInt);
/*      */ 
/* 4216 */     PlsqlIndexTableAccessor localPlsqlIndexTableAccessor = (PlsqlIndexTableAccessor)this.outBindAccessors[(paramInt - 1)];
/*      */ 
/* 4219 */     int i = localPlsqlIndexTableAccessor.elementInternalType;
/*      */ 
/* 4221 */     Object localObject = null;
/*      */ 
/* 4223 */     switch (i)
/*      */     {
/*      */     case 9:
/* 4226 */       localObject = new String[arrayOfDatum.length];
/* 4227 */       break;
/*      */     case 6:
/* 4229 */       localObject = new BigDecimal[arrayOfDatum.length];
/* 4230 */       break;
/*      */     default:
/* 4232 */       DatabaseError.throwSqlException(1, "Invalid column type");
/*      */     }
/*      */ 
/* 4236 */     for (int j = 0; j < localObject.length; j++) {
/* 4237 */       localObject[j] = ((arrayOfDatum[j] != null) && (arrayOfDatum[j].getLength() != 0L) ? arrayOfDatum[j].toJdbc() : null);
/*      */     }
/*      */ 
/* 4244 */     return localObject;
/*      */   }
/*      */ 
/*      */   public synchronized Object getPlsqlIndexTable(int paramInt, Class paramClass)
/*      */     throws SQLException
/*      */   {
/* 4262 */     Datum[] arrayOfDatum = getOraclePlsqlIndexTable(paramInt);
/*      */ 
/* 4264 */     if ((paramClass == null) || (!paramClass.isPrimitive())) {
/* 4265 */       DatabaseError.throwSqlException(68);
/*      */     }
/* 4267 */     String str = paramClass.getName();
/*      */     Object localObject;
/*      */     int i;
/* 4269 */     if (str.equals("byte"))
/*      */     {
/* 4271 */       localObject = new byte[arrayOfDatum.length];
/* 4272 */       for (i = 0; i < arrayOfDatum.length; i++)
/* 4273 */         localObject[i] = (arrayOfDatum[i] != null ? arrayOfDatum[i].byteValue() : 0);
/* 4274 */       return localObject;
/*      */     }
/* 4276 */     if (str.equals("char"))
/*      */     {
/* 4278 */       localObject = new char[arrayOfDatum.length];
/* 4279 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4280 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? (char)arrayOfDatum[i].intValue() : 0);
/*      */       }
/* 4282 */       return localObject;
/*      */     }
/* 4284 */     if (str.equals("double"))
/*      */     {
/* 4286 */       localObject = new double[arrayOfDatum.length];
/* 4287 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4288 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].doubleValue() : 0.0D);
/*      */       }
/* 4290 */       return localObject;
/*      */     }
/* 4292 */     if (str.equals("float"))
/*      */     {
/* 4294 */       localObject = new float[arrayOfDatum.length];
/* 4295 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4296 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].floatValue() : 0.0F);
/*      */       }
/* 4298 */       return localObject;
/*      */     }
/* 4300 */     if (str.equals("int"))
/*      */     {
/* 4302 */       localObject = new int[arrayOfDatum.length];
/* 4303 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4304 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].intValue() : 0);
/*      */       }
/* 4306 */       return localObject;
/*      */     }
/* 4308 */     if (str.equals("long"))
/*      */     {
/* 4310 */       localObject = new long[arrayOfDatum.length];
/* 4311 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4312 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].longValue() : 0L);
/*      */       }
/* 4314 */       return localObject;
/*      */     }
/* 4316 */     if (str.equals("short"))
/*      */     {
/* 4318 */       localObject = new short[arrayOfDatum.length];
/* 4319 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4320 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? (short)arrayOfDatum[i].intValue() : 0);
/*      */       }
/* 4322 */       return localObject;
/*      */     }
/* 4324 */     if (str.equals("boolean"))
/*      */     {
/* 4326 */       localObject = new boolean[arrayOfDatum.length];
/* 4327 */       for (i = 0; i < arrayOfDatum.length; i++) {
/* 4328 */         localObject[i] = ((arrayOfDatum[i] != null) && (arrayOfDatum[i].getLength() != 0L) ? arrayOfDatum[i].booleanValue() : 0);
/*      */       }
/* 4330 */       return localObject;
/*      */     }
/*      */ 
/* 4333 */     DatabaseError.throwSqlException(23);
/*      */ 
/* 4335 */     return null;
/*      */   }
/*      */ 
/*      */   public synchronized Datum[] getOraclePlsqlIndexTable(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 4353 */     if (this.closed) {
/* 4354 */       DatabaseError.throwSqlException(9);
/*      */     }
/* 4356 */     if (this.atLeastOneNamedParameter)
/*      */     {
/* 4358 */       DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */     }
/*      */ 
/* 4362 */     Accessor localAccessor = null;
/* 4363 */     if ((paramInt <= 0) || (paramInt > this.numberOfBindPositions) || ((localAccessor = this.outBindAccessors[(paramInt - 1)]) == null))
/*      */     {
/* 4366 */       DatabaseError.throwSqlException(3);
/*      */     }
/* 4368 */     this.lastIndex = paramInt;
/*      */ 
/* 4370 */     if (this.streamList != null) {
/* 4371 */       closeUsedStreams(paramInt);
/*      */     }
/*      */ 
/* 4374 */     return localAccessor.getOraclePlsqlIndexTable(this.currentRank);
/*      */   }
/*      */ 
/*      */   public boolean execute()
/*      */     throws SQLException
/*      */   {
/* 4386 */     synchronized (this.connection) {
/* 4387 */       synchronized (this) {
/* 4388 */         ensureOpen();
/* 4389 */         if ((this.atLeastOneNamedParameter) && (this.atLeastOneOrdinalParameter)) {
/* 4390 */           DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */         }
/* 4392 */         if (this.sqlObject.setNamedParameters(this.parameterCount, this.namedParameters))
/* 4393 */           this.needToParse = true;
/* 4394 */         return super.execute();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int executeUpdate()
/*      */     throws SQLException
/*      */   {
/* 4408 */     synchronized (this.connection) {
/* 4409 */       synchronized (this) {
/* 4410 */         ensureOpen();
/* 4411 */         if ((this.atLeastOneNamedParameter) && (this.atLeastOneOrdinalParameter)) {
/* 4412 */           DatabaseError.throwSqlException(90, "Ordinal binding and Named binding cannot be combined!");
/*      */         }
/* 4414 */         if (this.sqlObject.setNamedParameters(this.parameterCount, this.namedParameters))
/* 4415 */           this.needToParse = true;
/* 4416 */         return super.executeUpdate();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void setNull(int paramInt1, int paramInt2, String paramString)
/*      */     throws SQLException
/*      */   {
/* 4428 */     this.atLeastOneOrdinalParameter = true;
/* 4429 */     setNullInternal(paramInt1, paramInt2, paramString);
/*      */   }
/*      */ 
/*      */   public synchronized void setNull(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4436 */     this.atLeastOneOrdinalParameter = true;
/* 4437 */     setNullInternal(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setBoolean(int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 4444 */     this.atLeastOneOrdinalParameter = true;
/* 4445 */     setBooleanInternal(paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */   public synchronized void setByte(int paramInt, byte paramByte)
/*      */     throws SQLException
/*      */   {
/* 4452 */     this.atLeastOneOrdinalParameter = true;
/* 4453 */     setByteInternal(paramInt, paramByte);
/*      */   }
/*      */ 
/*      */   public synchronized void setShort(int paramInt, short paramShort)
/*      */     throws SQLException
/*      */   {
/* 4460 */     this.atLeastOneOrdinalParameter = true;
/* 4461 */     setShortInternal(paramInt, paramShort);
/*      */   }
/*      */ 
/*      */   public synchronized void setInt(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4468 */     this.atLeastOneOrdinalParameter = true;
/* 4469 */     setIntInternal(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setLong(int paramInt, long paramLong)
/*      */     throws SQLException
/*      */   {
/* 4476 */     this.atLeastOneOrdinalParameter = true;
/* 4477 */     setLongInternal(paramInt, paramLong);
/*      */   }
/*      */ 
/*      */   public synchronized void setFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 4484 */     this.atLeastOneOrdinalParameter = true;
/* 4485 */     setFloatInternal(paramInt, paramFloat);
/*      */   }
/*      */ 
/*      */   public synchronized void setBinaryFloat(int paramInt, float paramFloat)
/*      */     throws SQLException
/*      */   {
/* 4492 */     this.atLeastOneOrdinalParameter = true;
/* 4493 */     setBinaryFloatInternal(paramInt, paramFloat);
/*      */   }
/*      */ 
/*      */   public synchronized void setBinaryFloat(int paramInt, BINARY_FLOAT paramBINARY_FLOAT)
/*      */     throws SQLException
/*      */   {
/* 4500 */     this.atLeastOneOrdinalParameter = true;
/* 4501 */     setBinaryFloatInternal(paramInt, paramBINARY_FLOAT);
/*      */   }
/*      */ 
/*      */   public synchronized void setBinaryDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 4508 */     this.atLeastOneOrdinalParameter = true;
/* 4509 */     setBinaryDoubleInternal(paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   public synchronized void setBinaryDouble(int paramInt, BINARY_DOUBLE paramBINARY_DOUBLE)
/*      */     throws SQLException
/*      */   {
/* 4516 */     this.atLeastOneOrdinalParameter = true;
/* 4517 */     setBinaryDoubleInternal(paramInt, paramBINARY_DOUBLE);
/*      */   }
/*      */ 
/*      */   public synchronized void setDouble(int paramInt, double paramDouble)
/*      */     throws SQLException
/*      */   {
/* 4524 */     this.atLeastOneOrdinalParameter = true;
/* 4525 */     setDoubleInternal(paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   public synchronized void setBigDecimal(int paramInt, BigDecimal paramBigDecimal)
/*      */     throws SQLException
/*      */   {
/* 4532 */     this.atLeastOneOrdinalParameter = true;
/* 4533 */     setBigDecimalInternal(paramInt, paramBigDecimal);
/*      */   }
/*      */ 
/*      */   public synchronized void setString(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 4540 */     this.atLeastOneOrdinalParameter = true;
/* 4541 */     setStringInternal(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public synchronized void setFixedCHAR(int paramInt, String paramString)
/*      */     throws SQLException
/*      */   {
/* 4548 */     this.atLeastOneOrdinalParameter = true;
/* 4549 */     setFixedCHARInternal(paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public synchronized void setCursor(int paramInt, ResultSet paramResultSet)
/*      */     throws SQLException
/*      */   {
/* 4556 */     this.atLeastOneOrdinalParameter = true;
/* 4557 */     setCursorInternal(paramInt, paramResultSet);
/*      */   }
/*      */ 
/*      */   public synchronized void setROWID(int paramInt, ROWID paramROWID)
/*      */     throws SQLException
/*      */   {
/* 4564 */     this.atLeastOneOrdinalParameter = true;
/* 4565 */     setROWIDInternal(paramInt, paramROWID);
/*      */   }
/*      */ 
/*      */   public synchronized void setRAW(int paramInt, RAW paramRAW)
/*      */     throws SQLException
/*      */   {
/* 4572 */     this.atLeastOneOrdinalParameter = true;
/* 4573 */     setRAWInternal(paramInt, paramRAW);
/*      */   }
/*      */ 
/*      */   public synchronized void setCHAR(int paramInt, CHAR paramCHAR)
/*      */     throws SQLException
/*      */   {
/* 4580 */     this.atLeastOneOrdinalParameter = true;
/* 4581 */     setCHARInternal(paramInt, paramCHAR);
/*      */   }
/*      */ 
/*      */   public synchronized void setDATE(int paramInt, DATE paramDATE)
/*      */     throws SQLException
/*      */   {
/* 4588 */     this.atLeastOneOrdinalParameter = true;
/* 4589 */     setDATEInternal(paramInt, paramDATE);
/*      */   }
/*      */ 
/*      */   public synchronized void setNUMBER(int paramInt, NUMBER paramNUMBER)
/*      */     throws SQLException
/*      */   {
/* 4596 */     this.atLeastOneOrdinalParameter = true;
/* 4597 */     setNUMBERInternal(paramInt, paramNUMBER);
/*      */   }
/*      */ 
/*      */   public synchronized void setBLOB(int paramInt, BLOB paramBLOB)
/*      */     throws SQLException
/*      */   {
/* 4604 */     this.atLeastOneOrdinalParameter = true;
/* 4605 */     setBLOBInternal(paramInt, paramBLOB);
/*      */   }
/*      */ 
/*      */   public synchronized void setBlob(int paramInt, Blob paramBlob)
/*      */     throws SQLException
/*      */   {
/* 4612 */     this.atLeastOneOrdinalParameter = true;
/* 4613 */     setBlobInternal(paramInt, paramBlob);
/*      */   }
/*      */ 
/*      */   public synchronized void setCLOB(int paramInt, CLOB paramCLOB)
/*      */     throws SQLException
/*      */   {
/* 4620 */     this.atLeastOneOrdinalParameter = true;
/* 4621 */     setCLOBInternal(paramInt, paramCLOB);
/*      */   }
/*      */ 
/*      */   public synchronized void setClob(int paramInt, Clob paramClob)
/*      */     throws SQLException
/*      */   {
/* 4628 */     this.atLeastOneOrdinalParameter = true;
/* 4629 */     setClobInternal(paramInt, paramClob);
/*      */   }
/*      */ 
/*      */   public synchronized void setBFILE(int paramInt, BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 4636 */     this.atLeastOneOrdinalParameter = true;
/* 4637 */     setBFILEInternal(paramInt, paramBFILE);
/*      */   }
/*      */ 
/*      */   public synchronized void setBfile(int paramInt, BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/* 4644 */     this.atLeastOneOrdinalParameter = true;
/* 4645 */     setBfileInternal(paramInt, paramBFILE);
/*      */   }
/*      */ 
/*      */   public synchronized void setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 4652 */     this.atLeastOneOrdinalParameter = true;
/* 4653 */     setBytesInternal(paramInt, paramArrayOfByte);
/*      */   }
/*      */ 
/*      */   public synchronized void setInternalBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4660 */     this.atLeastOneOrdinalParameter = true;
/* 4661 */     setInternalBytesInternal(paramInt1, paramArrayOfByte, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setDate(int paramInt, Date paramDate)
/*      */     throws SQLException
/*      */   {
/* 4668 */     this.atLeastOneOrdinalParameter = true;
/* 4669 */     setDateInternal(paramInt, paramDate);
/*      */   }
/*      */ 
/*      */   public synchronized void setTime(int paramInt, Time paramTime)
/*      */     throws SQLException
/*      */   {
/* 4676 */     this.atLeastOneOrdinalParameter = true;
/* 4677 */     setTimeInternal(paramInt, paramTime);
/*      */   }
/*      */ 
/*      */   public synchronized void setTimestamp(int paramInt, Timestamp paramTimestamp)
/*      */     throws SQLException
/*      */   {
/* 4684 */     this.atLeastOneOrdinalParameter = true;
/* 4685 */     setTimestampInternal(paramInt, paramTimestamp);
/*      */   }
/*      */ 
/*      */   public synchronized void setINTERVALYM(int paramInt, INTERVALYM paramINTERVALYM)
/*      */     throws SQLException
/*      */   {
/* 4692 */     this.atLeastOneOrdinalParameter = true;
/* 4693 */     setINTERVALYMInternal(paramInt, paramINTERVALYM);
/*      */   }
/*      */ 
/*      */   public synchronized void setINTERVALDS(int paramInt, INTERVALDS paramINTERVALDS)
/*      */     throws SQLException
/*      */   {
/* 4700 */     this.atLeastOneOrdinalParameter = true;
/* 4701 */     setINTERVALDSInternal(paramInt, paramINTERVALDS);
/*      */   }
/*      */ 
/*      */   public synchronized void setTIMESTAMP(int paramInt, TIMESTAMP paramTIMESTAMP)
/*      */     throws SQLException
/*      */   {
/* 4708 */     this.atLeastOneOrdinalParameter = true;
/* 4709 */     setTIMESTAMPInternal(paramInt, paramTIMESTAMP);
/*      */   }
/*      */ 
/*      */   public synchronized void setTIMESTAMPTZ(int paramInt, TIMESTAMPTZ paramTIMESTAMPTZ)
/*      */     throws SQLException
/*      */   {
/* 4716 */     this.atLeastOneOrdinalParameter = true;
/* 4717 */     setTIMESTAMPTZInternal(paramInt, paramTIMESTAMPTZ);
/*      */   }
/*      */ 
/*      */   public synchronized void setTIMESTAMPLTZ(int paramInt, TIMESTAMPLTZ paramTIMESTAMPLTZ)
/*      */     throws SQLException
/*      */   {
/* 4724 */     this.atLeastOneOrdinalParameter = true;
/* 4725 */     setTIMESTAMPLTZInternal(paramInt, paramTIMESTAMPLTZ);
/*      */   }
/*      */ 
/*      */   public synchronized void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4732 */     this.atLeastOneOrdinalParameter = true;
/* 4733 */     setAsciiStreamInternal(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4740 */     this.atLeastOneOrdinalParameter = true;
/* 4741 */     setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4748 */     this.atLeastOneOrdinalParameter = true;
/* 4749 */     setUnicodeStreamInternal(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4756 */     this.atLeastOneOrdinalParameter = true;
/* 4757 */     setCharacterStreamInternal(paramInt1, paramReader, paramInt2);
/*      */   }
/*      */ 
/*      */   public synchronized void setDate(int paramInt, Date paramDate, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4764 */     this.atLeastOneOrdinalParameter = true;
/* 4765 */     setDateInternal(paramInt, paramDate, paramCalendar);
/*      */   }
/*      */ 
/*      */   public synchronized void setTime(int paramInt, Time paramTime, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4772 */     this.atLeastOneOrdinalParameter = true;
/* 4773 */     setTimeInternal(paramInt, paramTime, paramCalendar);
/*      */   }
/*      */ 
/*      */   public synchronized void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar)
/*      */     throws SQLException
/*      */   {
/* 4780 */     this.atLeastOneOrdinalParameter = true;
/* 4781 */     setTimestampInternal(paramInt, paramTimestamp, paramCalendar);
/*      */   }
/*      */ 
/*      */   public synchronized void setURL(int paramInt, URL paramURL)
/*      */     throws SQLException
/*      */   {
/* 4788 */     this.atLeastOneOrdinalParameter = true;
/* 4789 */     setURLInternal(paramInt, paramURL);
/*      */   }
/*      */ 
/*      */   public void setArray(int paramInt, Array paramArray)
/*      */     throws SQLException
/*      */   {
/* 4797 */     this.atLeastOneOrdinalParameter = true;
/* 4798 */     setArrayInternal(paramInt, paramArray);
/*      */   }
/*      */ 
/*      */   public void setARRAY(int paramInt, ARRAY paramARRAY)
/*      */     throws SQLException
/*      */   {
/* 4805 */     this.atLeastOneOrdinalParameter = true;
/* 4806 */     setARRAYInternal(paramInt, paramARRAY);
/*      */   }
/*      */ 
/*      */   public void setOPAQUE(int paramInt, OPAQUE paramOPAQUE)
/*      */     throws SQLException
/*      */   {
/* 4813 */     this.atLeastOneOrdinalParameter = true;
/* 4814 */     setOPAQUEInternal(paramInt, paramOPAQUE);
/*      */   }
/*      */ 
/*      */   public void setStructDescriptor(int paramInt, StructDescriptor paramStructDescriptor)
/*      */     throws SQLException
/*      */   {
/* 4821 */     this.atLeastOneOrdinalParameter = true;
/* 4822 */     setStructDescriptorInternal(paramInt, paramStructDescriptor);
/*      */   }
/*      */ 
/*      */   public void setSTRUCT(int paramInt, STRUCT paramSTRUCT)
/*      */     throws SQLException
/*      */   {
/* 4829 */     this.atLeastOneOrdinalParameter = true;
/* 4830 */     setSTRUCTInternal(paramInt, paramSTRUCT);
/*      */   }
/*      */ 
/*      */   public void setCustomDatum(int paramInt, CustomDatum paramCustomDatum)
/*      */     throws SQLException
/*      */   {
/* 4837 */     this.atLeastOneOrdinalParameter = true;
/* 4838 */     setCustomDatumInternal(paramInt, paramCustomDatum);
/*      */   }
/*      */ 
/*      */   public void setORAData(int paramInt, ORAData paramORAData)
/*      */     throws SQLException
/*      */   {
/* 4845 */     this.atLeastOneOrdinalParameter = true;
/* 4846 */     setORADataInternal(paramInt, paramORAData);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 4853 */     this.atLeastOneOrdinalParameter = true;
/* 4854 */     setObjectInternal(paramInt1, paramObject, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt1, Object paramObject, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 4861 */     this.atLeastOneOrdinalParameter = true;
/* 4862 */     setObjectInternal(paramInt1, paramObject, paramInt2);
/*      */   }
/*      */ 
/*      */   public void setRefType(int paramInt, REF paramREF)
/*      */     throws SQLException
/*      */   {
/* 4869 */     this.atLeastOneOrdinalParameter = true;
/* 4870 */     setRefTypeInternal(paramInt, paramREF);
/*      */   }
/*      */ 
/*      */   public void setRef(int paramInt, Ref paramRef)
/*      */     throws SQLException
/*      */   {
/* 4877 */     this.atLeastOneOrdinalParameter = true;
/* 4878 */     setRefInternal(paramInt, paramRef);
/*      */   }
/*      */ 
/*      */   public void setREF(int paramInt, REF paramREF)
/*      */     throws SQLException
/*      */   {
/* 4885 */     this.atLeastOneOrdinalParameter = true;
/* 4886 */     setREFInternal(paramInt, paramREF);
/*      */   }
/*      */ 
/*      */   public void setObject(int paramInt, Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 4893 */     this.atLeastOneOrdinalParameter = true;
/* 4894 */     setObjectInternal(paramInt, paramObject);
/*      */   }
/*      */ 
/*      */   public void setOracleObject(int paramInt, Datum paramDatum)
/*      */     throws SQLException
/*      */   {
/* 4901 */     this.atLeastOneOrdinalParameter = true;
/* 4902 */     setOracleObjectInternal(paramInt, paramDatum);
/*      */   }
/*      */ 
/*      */   public synchronized void setPlsqlIndexTable(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*      */     throws SQLException
/*      */   {
/* 4914 */     this.atLeastOneOrdinalParameter = true;
/* 4915 */     setPlsqlIndexTableInternal(paramInt1, paramObject, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */   int addNamedPara(String paramString)
/*      */     throws SQLException
/*      */   {
/* 4929 */     String str = paramString.toUpperCase().intern();
/*      */ 
/* 4931 */     for (int i = 0; i < this.parameterCount; i++)
/*      */     {
/* 4933 */       if (str == this.namedParameters[i]) {
/* 4934 */         return i + 1;
/*      */       }
/*      */     }
/* 4937 */     if (this.parameterCount >= this.namedParameters.length)
/*      */     {
/* 4939 */       String[] arrayOfString = new String[this.namedParameters.length * 2];
/* 4940 */       System.arraycopy(this.namedParameters, 0, arrayOfString, 0, this.namedParameters.length);
/* 4941 */       this.namedParameters = arrayOfString;
/*      */     }
/*      */ 
/* 4944 */     this.namedParameters[(this.parameterCount++)] = str;
/*      */ 
/* 4946 */     this.atLeastOneNamedParameter = true;
/* 4947 */     return this.parameterCount;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleCallableStatement
 * JD-Core Version:    0.6.0
 */