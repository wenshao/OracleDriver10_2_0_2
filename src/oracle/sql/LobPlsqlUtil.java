/*      */ package oracle.sql;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.internal.OracleCallableStatement;
/*      */ import oracle.jdbc.internal.OracleConnection;
/*      */ 
/*      */ public class LobPlsqlUtil
/*      */ {
/*   64 */   static boolean PLSQL_DEBUG = false;
/*      */   static final int MAX_PLSQL_SIZE = 32512;
/*      */   static final int MAX_PLSQL_INSTR_SIZE = 32512;
/*      */   static final int MAX_CHUNK_SIZE = 32512;
/* 1011 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*      */ 
/*      */   public static long hasPattern(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/*   83 */     return hasPattern(paramBLOB.getInternalConnection(), paramBLOB, 2004, paramArrayOfByte, paramLong);
/*      */   }
/*      */ 
/*      */   public static long isSubLob(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
/*      */     throws SQLException
/*      */   {
/*   93 */     return isSubLob(paramBLOB1.getInternalConnection(), paramBLOB1, 2004, paramBLOB2, paramLong);
/*      */   }
/*      */ 
/*      */   public static long hasPattern(CLOB paramCLOB, char[] paramArrayOfChar, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  109 */     if ((paramArrayOfChar == null) || (paramLong <= 0L)) {
/*  110 */       return 0L;
/*      */     }
/*  112 */     OracleConnection localOracleConnection = paramCLOB.getInternalConnection();
/*  113 */     long l1 = paramArrayOfChar.length;
/*  114 */     long l2 = length(localOracleConnection, paramCLOB, 2005);
/*      */ 
/*  117 */     if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
/*      */     {
/*  119 */       return 0L;
/*      */     }
/*      */ 
/*  123 */     if (l1 <= getPlsqlMaxInstrSize(localOracleConnection))
/*      */     {
/*  127 */       OracleCallableStatement localOracleCallableStatement = null;
/*      */       try
/*      */       {
/*  131 */         localOracleCallableStatement = (OracleCallableStatement)localOracleConnection.prepareCall("begin :1 := dbms_lob.instr(:2, :3, :4); end;");
/*      */ 
/*  134 */         localOracleCallableStatement.registerOutParameter(1, 2);
/*      */ 
/*  136 */         if (paramCLOB.isNCLOB())
/*      */         {
/*  138 */           localOracleCallableStatement.setFormOfUse(2, 2);
/*  139 */           localOracleCallableStatement.setFormOfUse(3, 2);
/*      */         }
/*      */ 
/*  142 */         localOracleCallableStatement.setCLOB(2, paramCLOB);
/*  143 */         localOracleCallableStatement.setString(3, new String(paramArrayOfChar));
/*  144 */         localOracleCallableStatement.setLong(4, paramLong);
/*  145 */         localOracleCallableStatement.execute();
/*      */ 
/*  147 */         l3 = localOracleCallableStatement.getLong(1);
/*      */         return l3;
/*      */       }
/*      */       finally
/*      */       {
/*  151 */         localOracleCallableStatement.close();
/*      */ 
/*  153 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  162 */     int i = 0;
/*  163 */     long l3 = paramLong;
/*  164 */     int j = 0;
/*      */ 
/*  167 */     long l5 = 0L;
/*      */ 
/*  170 */     while (j == 0)
/*      */     {
/*  172 */       if (l1 > l2 - l3 + 1L) {
/*  173 */         return 0L;
/*      */       }
/*  175 */       i = 0;
/*      */ 
/*  178 */       int k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);
/*      */ 
/*  182 */       char[] arrayOfChar = new char[k];
/*      */ 
/*  184 */       System.arraycopy(paramArrayOfChar, i, arrayOfChar, 0, k);
/*      */ 
/*  187 */       long l4 = hasPattern(paramCLOB, arrayOfChar, l3);
/*      */ 
/*  189 */       if (l4 == 0L)
/*      */       {
/*  191 */         return 0L;
/*      */       }
/*      */ 
/*  195 */       l5 = l4;
/*      */ 
/*  197 */       i += k;
/*  198 */       l3 = l4 + k;
/*      */ 
/*  201 */       int m = 1;
/*      */ 
/*  203 */       while (m != 0)
/*      */       {
/*  205 */         k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);
/*      */ 
/*  209 */         arrayOfChar = new char[k];
/*      */ 
/*  211 */         System.arraycopy(paramArrayOfChar, i, arrayOfChar, 0, k);
/*      */ 
/*  215 */         l4 = hasPattern(paramCLOB, arrayOfChar, l3);
/*      */ 
/*  217 */         if (l4 == l3)
/*      */         {
/*  221 */           i += k;
/*  222 */           l3 += k;
/*      */ 
/*  224 */           if (i != l1)
/*      */             continue;
/*  226 */           m = 0;
/*  227 */           j = 1; continue;
/*      */         }
/*      */ 
/*  230 */         if (l4 == 0L)
/*      */         {
/*  235 */           return 0L;
/*      */         }
/*      */ 
/*  242 */         l3 = l4 - i;
/*      */ 
/*  244 */         m = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  250 */     return l5;
/*      */   }
/*      */ 
/*      */   public static long isSubLob(CLOB paramCLOB1, CLOB paramCLOB2, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  261 */     if ((paramCLOB2 == null) || (paramLong <= 0L)) {
/*  262 */       return 0L;
/*      */     }
/*  264 */     OracleConnection localOracleConnection = paramCLOB1.getInternalConnection();
/*  265 */     long l1 = length(localOracleConnection, paramCLOB2, 2005);
/*  266 */     long l2 = length(localOracleConnection, paramCLOB1, 2005);
/*      */ 
/*  269 */     if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
/*      */     {
/*  271 */       return 0L;
/*      */     }
/*      */ 
/*  275 */     if (l1 <= getPlsqlMaxInstrSize(localOracleConnection))
/*      */     {
/*  279 */       char[] arrayOfChar1 = new char[(int)l1];
/*      */ 
/*  281 */       paramCLOB2.getChars(1L, (int)l1, arrayOfChar1);
/*      */ 
/*  283 */       return hasPattern(paramCLOB1, arrayOfChar1, paramLong);
/*      */     }
/*      */ 
/*  291 */     int i = 0;
/*  292 */     long l3 = paramLong;
/*  293 */     int j = 0;
/*      */ 
/*  296 */     long l5 = 0L;
/*      */ 
/*  299 */     while (j == 0)
/*      */     {
/*  301 */       if (l1 > l2 - l3 + 1L) {
/*  302 */         return 0L;
/*      */       }
/*  304 */       i = 0;
/*      */ 
/*  307 */       int k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);
/*      */ 
/*  311 */       char[] arrayOfChar2 = new char[k];
/*      */ 
/*  313 */       paramCLOB2.getChars(i + 1, k, arrayOfChar2);
/*      */ 
/*  316 */       long l4 = hasPattern(paramCLOB1, arrayOfChar2, l3);
/*      */ 
/*  318 */       if (l4 == 0L)
/*      */       {
/*  320 */         return 0L;
/*      */       }
/*      */ 
/*  324 */       l5 = l4;
/*      */ 
/*  326 */       i += k;
/*  327 */       l3 = l4 + k;
/*      */ 
/*  330 */       int m = 1;
/*      */ 
/*  332 */       while (m != 0)
/*      */       {
/*  334 */         k = (int)Math.min(getPlsqlMaxInstrSize(localOracleConnection), l1 - i);
/*      */ 
/*  338 */         arrayOfChar2 = new char[k];
/*      */ 
/*  340 */         paramCLOB2.getChars(i + 1, k, arrayOfChar2);
/*      */ 
/*  343 */         l4 = hasPattern(paramCLOB1, arrayOfChar2, l3);
/*      */ 
/*  345 */         if (l4 == l3)
/*      */         {
/*  349 */           i += k;
/*  350 */           l3 += k;
/*      */ 
/*  352 */           if (i != l1)
/*      */             continue;
/*  354 */           m = 0;
/*  355 */           j = 1; continue;
/*      */         }
/*      */ 
/*  358 */         if (l4 == 0L)
/*      */         {
/*  363 */           return 0L;
/*      */         }
/*      */ 
/*  370 */         l3 = l4 - i;
/*      */ 
/*  372 */         m = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  378 */     return l5;
/*      */   }
/*      */ 
/*      */   public static long hasPattern(BFILE paramBFILE, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  396 */     return hasPattern(paramBFILE.getInternalConnection(), paramBFILE, -13, paramArrayOfByte, paramLong);
/*      */   }
/*      */ 
/*      */   public static long isSubLob(BFILE paramBFILE1, BFILE paramBFILE2, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  406 */     return isSubLob(paramBFILE1.getInternalConnection(), paramBFILE1, -13, paramBFILE2, paramLong);
/*      */   }
/*      */ 
/*      */   public static String fileGetName(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/*  415 */     OracleCallableStatement localOracleCallableStatement = null;
/*  416 */     String str = null;
/*      */     try
/*      */     {
/*  420 */       localOracleCallableStatement = (OracleCallableStatement)paramBFILE.getInternalConnection().prepareCall("begin dbms_lob.fileGetName(:1, :2, :3); end; ");
/*      */ 
/*  423 */       localOracleCallableStatement.setBFILE(1, paramBFILE);
/*  424 */       localOracleCallableStatement.registerOutParameter(2, 12);
/*  425 */       localOracleCallableStatement.registerOutParameter(3, 12);
/*  426 */       localOracleCallableStatement.execute();
/*      */ 
/*  428 */       str = localOracleCallableStatement.getString(3);
/*      */     }
/*      */     finally
/*      */     {
/*  432 */       if (localOracleCallableStatement != null)
/*      */       {
/*  434 */         localOracleCallableStatement.close();
/*      */ 
/*  436 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  442 */     return str;
/*      */   }
/*      */ 
/*      */   public static String fileGetDirAlias(BFILE paramBFILE)
/*      */     throws SQLException
/*      */   {
/*  450 */     OracleCallableStatement localOracleCallableStatement = null;
/*  451 */     String str = null;
/*      */     try
/*      */     {
/*  455 */       localOracleCallableStatement = (OracleCallableStatement)paramBFILE.getInternalConnection().prepareCall("begin dbms_lob.fileGetName(:1, :2, :3); end; ");
/*      */ 
/*  458 */       localOracleCallableStatement.setBFILE(1, paramBFILE);
/*  459 */       localOracleCallableStatement.registerOutParameter(2, 12);
/*  460 */       localOracleCallableStatement.registerOutParameter(3, 12);
/*  461 */       localOracleCallableStatement.execute();
/*      */ 
/*  463 */       str = localOracleCallableStatement.getString(2);
/*      */     }
/*      */     finally
/*      */     {
/*  468 */       if (localOracleCallableStatement != null)
/*      */       {
/*  470 */         localOracleCallableStatement.close();
/*      */ 
/*  472 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  478 */     return str;
/*      */   }
/*      */ 
/*      */   private static int getPlsqlMaxInstrSize(OracleConnection paramOracleConnection)
/*      */     throws SQLException
/*      */   {
/*  493 */     boolean bool = paramOracleConnection.isCharSetMultibyte(paramOracleConnection.getDriverCharSet());
/*      */ 
/*  495 */     int i = paramOracleConnection.getMaxCharbyteSize();
/*      */ 
/*  497 */     int j = 32512;
/*      */ 
/*  499 */     if (bool) {
/*  500 */       j = 32512 / (paramOracleConnection.getC2SNlsRatio() * i);
/*      */     }
/*      */ 
/*  505 */     return j;
/*      */   }
/*      */ 
/*      */   public static long read(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/*  514 */     OracleCallableStatement localOracleCallableStatement = null;
/*  515 */     int i = 0;
/*      */     try
/*      */     {
/*  519 */       localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin dbms_lob.read (:1, :2, :3, :4); end;");
/*      */ 
/*  522 */       int j = 0;
/*  523 */       int k = 0;
/*      */ 
/*  526 */       if (isNCLOB(paramDatum))
/*      */       {
/*  528 */         localOracleCallableStatement.setFormOfUse(1, 2);
/*  529 */         localOracleCallableStatement.setFormOfUse(4, 2);
/*      */       }
/*      */ 
/*  532 */       localOracleCallableStatement.setObject(1, paramDatum, paramInt);
/*  533 */       localOracleCallableStatement.registerOutParameter(2, 2);
/*  534 */       localOracleCallableStatement.registerOutParameter(4, -3);
/*      */ 
/*  536 */       while (i < paramLong2)
/*      */       {
/*  538 */         k = Math.min((int)paramLong2, 32512);
/*      */ 
/*  543 */         localOracleCallableStatement.setInt(2, k);
/*  544 */         localOracleCallableStatement.setInt(3, (int)paramLong1 + i);
/*  545 */         localOracleCallableStatement.execute();
/*      */ 
/*  547 */         j = localOracleCallableStatement.getInt(2);
/*  548 */         byte[] arrayOfByte = localOracleCallableStatement.getBytes(4);
/*      */ 
/*  550 */         System.arraycopy(arrayOfByte, 0, paramArrayOfByte, i, j);
/*      */ 
/*  556 */         i += j;
/*  557 */         paramLong2 -= j;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  564 */       if (localSQLException.getErrorCode() != 1403)
/*      */       {
/*  569 */         throw localSQLException;
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  580 */       if (localOracleCallableStatement != null)
/*      */       {
/*  582 */         localOracleCallableStatement.close();
/*      */ 
/*  584 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  590 */     return i;
/*      */   }
/*      */ 
/*      */   public static long length(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  599 */     long l = 0L;
/*  600 */     OracleCallableStatement localOracleCallableStatement = null;
/*      */     try
/*      */     {
/*  604 */       localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin :1 := dbms_lob.getLength (:2); end;");
/*      */ 
/*  607 */       if (isNCLOB(paramDatum)) {
/*  608 */         localOracleCallableStatement.setFormOfUse(2, 2);
/*      */       }
/*  610 */       localOracleCallableStatement.setObject(2, paramDatum, paramInt);
/*  611 */       localOracleCallableStatement.registerOutParameter(1, 2);
/*  612 */       localOracleCallableStatement.execute();
/*      */ 
/*  614 */       l = localOracleCallableStatement.getLong(1);
/*      */     }
/*      */     finally
/*      */     {
/*  618 */       if (localOracleCallableStatement != null)
/*      */       {
/*  620 */         localOracleCallableStatement.close();
/*      */ 
/*  622 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  628 */     return l;
/*      */   }
/*      */ 
/*      */   public static long hasPattern(OracleConnection paramOracleConnection, Datum paramDatum, int paramInt, byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  639 */     if ((paramArrayOfByte == null) || (paramLong <= 0L)) {
/*  640 */       return 0L;
/*      */     }
/*  642 */     long l1 = paramArrayOfByte.length;
/*  643 */     long l2 = length(paramOracleConnection, paramDatum, paramInt);
/*      */ 
/*  646 */     if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
/*      */     {
/*  648 */       return 0L;
/*      */     }
/*      */ 
/*  652 */     if (l1 <= 32512L)
/*      */     {
/*  656 */       OracleCallableStatement localOracleCallableStatement = null;
/*      */       try
/*      */       {
/*  660 */         localOracleCallableStatement = (OracleCallableStatement)paramOracleConnection.prepareCall("begin :1 := dbms_lob.instr(:2, :3, :4); end;");
/*      */ 
/*  663 */         localOracleCallableStatement.registerOutParameter(1, 2);
/*  664 */         localOracleCallableStatement.setObject(2, paramDatum, paramInt);
/*  665 */         localOracleCallableStatement.setBytes(3, paramArrayOfByte);
/*  666 */         localOracleCallableStatement.setLong(4, paramLong);
/*  667 */         localOracleCallableStatement.execute();
/*      */ 
/*  669 */         l3 = localOracleCallableStatement.getLong(1);
/*      */         return l3;
/*      */       }
/*      */       finally
/*      */       {
/*  673 */         localOracleCallableStatement.close();
/*      */ 
/*  675 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  684 */     int i = 0;
/*  685 */     long l3 = paramLong;
/*  686 */     int j = 0;
/*      */ 
/*  689 */     long l5 = 0L;
/*      */ 
/*  692 */     while (j == 0)
/*      */     {
/*  694 */       if (l1 > l2 - l3 + 1L) {
/*  695 */         return 0L;
/*      */       }
/*  697 */       i = 0;
/*      */ 
/*  700 */       int k = (int)Math.min(32512L, l1 - i);
/*      */ 
/*  704 */       byte[] arrayOfByte = new byte[k];
/*      */ 
/*  706 */       System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, k);
/*      */ 
/*  709 */       long l4 = hasPattern(paramOracleConnection, paramDatum, paramInt, arrayOfByte, l3);
/*      */ 
/*  711 */       if (l4 == 0L)
/*      */       {
/*  713 */         return 0L;
/*      */       }
/*      */ 
/*  717 */       l5 = l4;
/*      */ 
/*  719 */       i += k;
/*  720 */       l3 = l4 + k;
/*      */ 
/*  723 */       int m = 1;
/*      */ 
/*  725 */       while (m != 0)
/*      */       {
/*  727 */         k = (int)Math.min(32512L, l1 - i);
/*      */ 
/*  731 */         arrayOfByte = new byte[k];
/*      */ 
/*  733 */         System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, k);
/*      */ 
/*  737 */         l4 = hasPattern(paramOracleConnection, paramDatum, paramInt, arrayOfByte, l3);
/*      */ 
/*  740 */         if (l4 == l3)
/*      */         {
/*  744 */           i += k;
/*  745 */           l3 += k;
/*      */ 
/*  747 */           if (i != l1)
/*      */             continue;
/*  749 */           m = 0;
/*  750 */           j = 1; continue;
/*      */         }
/*      */ 
/*  753 */         if (l4 == 0L)
/*      */         {
/*  758 */           return 0L;
/*      */         }
/*      */ 
/*  765 */         l3 = l4 - i;
/*      */ 
/*  767 */         m = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  773 */     return l5;
/*      */   }
/*      */ 
/*      */   public static long isSubLob(OracleConnection paramOracleConnection, Datum paramDatum1, int paramInt, Datum paramDatum2, long paramLong)
/*      */     throws SQLException
/*      */   {
/*  784 */     if ((paramDatum2 == null) || (paramLong <= 0L)) {
/*  785 */       return 0L;
/*      */     }
/*  787 */     long l1 = length(paramOracleConnection, paramDatum2, paramInt);
/*  788 */     long l2 = length(paramOracleConnection, paramDatum1, paramInt);
/*      */ 
/*  790 */     if ((l1 == 0L) || (l1 > l2 - paramLong + 1L) || (paramLong > l2))
/*      */     {
/*  792 */       return 0L;
/*      */     }
/*      */ 
/*  796 */     if (l1 <= 32512L)
/*      */     {
/*  800 */       byte[] arrayOfByte1 = new byte[(int)l1];
/*      */ 
/*  802 */       read(paramOracleConnection, paramDatum2, paramInt, 1L, l1, arrayOfByte1);
/*      */ 
/*  804 */       return hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte1, paramLong);
/*      */     }
/*      */ 
/*  812 */     int i = 0;
/*  813 */     long l3 = paramLong;
/*  814 */     int j = 0;
/*      */ 
/*  817 */     long l5 = 0L;
/*      */ 
/*  820 */     while (j == 0)
/*      */     {
/*  822 */       if (l1 > l2 - l3 + 1L) {
/*  823 */         return 0L;
/*      */       }
/*  825 */       i = 0;
/*      */ 
/*  828 */       int k = (int)Math.min(32512L, l1 - i);
/*      */ 
/*  832 */       byte[] arrayOfByte2 = new byte[k];
/*      */ 
/*  834 */       read(paramOracleConnection, paramDatum2, paramInt, i + 1, k, arrayOfByte2);
/*      */ 
/*  837 */       long l4 = hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte2, l3);
/*      */ 
/*  839 */       if (l4 == 0L)
/*      */       {
/*  841 */         return 0L;
/*      */       }
/*      */ 
/*  845 */       l5 = l4;
/*      */ 
/*  847 */       i += k;
/*  848 */       l3 = l4 + k;
/*      */ 
/*  851 */       int n = 1;
/*      */ 
/*  853 */       while (n != 0)
/*      */       {
/*  855 */         int m = (int)Math.min(32512L, l1 - i);
/*      */ 
/*  859 */         arrayOfByte2 = new byte[m];
/*      */ 
/*  861 */         read(paramOracleConnection, paramDatum2, paramInt, i + 1, m, arrayOfByte2);
/*      */ 
/*  864 */         l4 = hasPattern(paramOracleConnection, paramDatum1, paramInt, arrayOfByte2, l3);
/*      */ 
/*  867 */         if (l4 == l3)
/*      */         {
/*  871 */           i += m;
/*  872 */           l3 += m;
/*      */ 
/*  874 */           if (i != l1)
/*      */             continue;
/*  876 */           n = 0;
/*  877 */           j = 1; continue;
/*      */         }
/*      */ 
/*  880 */         if (l4 == 0L)
/*      */         {
/*  885 */           return 0L;
/*      */         }
/*      */ 
/*  892 */         l3 = l4 - i;
/*      */ 
/*  894 */         n = 0;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  900 */     return l5;
/*      */   }
/*      */ 
/*      */   private static boolean isNCLOB(Datum paramDatum)
/*      */   {
/*  911 */     Class localClass = null;
/*      */     try
/*      */     {
/*  915 */       localClass = Class.forName("oracle.sql.CLOB");
/*      */     }
/*      */     catch (ClassNotFoundException localClassNotFoundException)
/*      */     {
/*  922 */       return false;
/*      */     }
/*      */ 
/*  925 */     if (!localClass.isInstance(paramDatum)) {
/*  926 */       return false;
/*      */     }
/*  928 */     CLOB localCLOB = (CLOB)paramDatum;
/*      */ 
/*  930 */     return localCLOB.isNCLOB();
/*      */   }
/*      */ 
/*      */   public static Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt1, int paramInt2, short paramShort)
/*      */     throws SQLException
/*      */   {
/*  940 */     OracleCallableStatement localOracleCallableStatement = null;
/*  941 */     Datum localDatum = null;
/*      */     try
/*      */     {
/*  945 */       localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall("begin dbms_lob.createTemporary (:1," + (paramBoolean ? "TRUE" : "FALSE") + ", :2); end;");
/*      */ 
/*  949 */       localOracleCallableStatement.registerOutParameter(1, paramInt2);
/*  950 */       localOracleCallableStatement.setFormOfUse(1, paramShort);
/*      */ 
/*  953 */       localOracleCallableStatement.setInt(2, paramInt1);
/*  954 */       localOracleCallableStatement.execute();
/*      */ 
/*  956 */       localDatum = localOracleCallableStatement.getOracleObject(1);
/*      */     }
/*      */     finally
/*      */     {
/*  960 */       if (localOracleCallableStatement != null)
/*      */       {
/*  962 */         localOracleCallableStatement.close();
/*      */ 
/*  964 */         localOracleCallableStatement = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  970 */     return localDatum;
/*      */   }
/*      */ 
/*      */   public static void freeTemporaryLob(Connection paramConnection, Datum paramDatum, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  979 */     OracleCallableStatement localOracleCallableStatement = null;
/*      */     try
/*      */     {
/*  983 */       localOracleCallableStatement = (OracleCallableStatement)paramConnection.prepareCall("begin dbms_lob.freeTemporary (:1); end;");
/*      */ 
/*  986 */       localOracleCallableStatement.registerOutParameter(1, paramInt);
/*      */ 
/*  988 */       if (isNCLOB(paramDatum)) {
/*  989 */         localOracleCallableStatement.setFormOfUse(1, 2);
/*      */       }
/*  991 */       localOracleCallableStatement.setOracleObject(1, paramDatum);
/*  992 */       localOracleCallableStatement.execute();
/*  993 */       paramDatum.setShareBytes(localOracleCallableStatement.privateGetBytes(1));
/*      */     }
/*      */     finally
/*      */     {
/*  997 */       if (localOracleCallableStatement != null)
/*      */       {
/*  999 */         localOracleCallableStatement.close();
/*      */ 
/* 1001 */         localOracleCallableStatement = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.LobPlsqlUtil
 * JD-Core Version:    0.6.0
 */