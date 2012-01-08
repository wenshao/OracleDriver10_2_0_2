/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import javax.sql.XAConnection;
/*      */ import javax.transaction.xa.XAException;
/*      */ import javax.transaction.xa.XAResource;
/*      */ import javax.transaction.xa.Xid;
/*      */ import oracle.jdbc.xa.OracleXAConnection;
/*      */ import oracle.jdbc.xa.OracleXid;
/*      */ import oracle.jdbc.xa.client.OracleXADataSource;
/*      */ import oracle.jdbc.xa.client.OracleXAResource;
/*      */ 
/*      */ class T4CXAResource extends OracleXAResource
/*      */ {
/*      */   T4CConnection physicalConn;
/*   36 */   int[] applicationValueArr = new int[1];
/*   37 */   boolean isTransLoose = false;
/*      */   byte[] context;
/*      */   int[] errorNumber;
/*      */   private String password;
/* 1057 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*      */ 
/*      */   T4CXAResource(T4CConnection paramT4CConnection, OracleXAConnection paramOracleXAConnection, boolean paramBoolean)
/*      */     throws XAException
/*      */   {
/*   47 */     super(paramT4CConnection, paramOracleXAConnection);
/*      */ 
/*   51 */     this.physicalConn = paramT4CConnection;
/*   52 */     this.isTransLoose = paramBoolean;
/*   53 */     this.errorNumber = new int[1];
/*      */   }
/*      */ 
/*      */   protected int doStart(Xid paramXid, int paramInt)
/*      */     throws XAException
/*      */   {
/*   63 */     int i = -1;
/*      */ 
/*   65 */     synchronized (this.physicalConn)
/*      */     {
/*   67 */       synchronized (this)
/*      */       {
/*   91 */         if (this.isTransLoose) {
/*   92 */           paramInt |= 65536;
/*      */         }
/*      */ 
/*   99 */         int j = paramInt & 0x8200000;
/*      */ 
/*  102 */         if ((j == 134217728) && (OracleXid.isLocalTransaction(paramXid))) {
/*  103 */           return 0;
/*      */         }
/*      */ 
/*  142 */         this.applicationValueArr[0] = 0;
/*      */         try
/*      */         {
/*      */           try
/*      */           {
/*  148 */             T4CTTIOtxse localT4CTTIOtxse = this.physicalConn.otxse;
/*  149 */             byte[] arrayOfByte1 = null;
/*  150 */             byte[] arrayOfByte2 = paramXid.getGlobalTransactionId();
/*  151 */             byte[] arrayOfByte3 = paramXid.getBranchQualifier();
/*      */ 
/*  153 */             int k = 0;
/*  154 */             int m = 0;
/*      */ 
/*  156 */             if ((arrayOfByte2 != null) && (arrayOfByte3 != null))
/*      */             {
/*  158 */               k = Math.min(arrayOfByte2.length, 64);
/*  159 */               m = Math.min(arrayOfByte3.length, 64);
/*  160 */               arrayOfByte1 = new byte[''];
/*      */ 
/*  162 */               System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, k);
/*  163 */               System.arraycopy(arrayOfByte3, 0, arrayOfByte1, k, m);
/*      */             }
/*      */ 
/*  166 */             int n = 0;
/*      */ 
/*  169 */             if (((paramInt & 0x200000) != 0) || ((paramInt & 0x8000000) != 0))
/*  170 */               n |= 4;
/*      */             else {
/*  172 */               n |= 1;
/*      */             }
/*  174 */             if ((paramInt & 0x100) != 0) {
/*  175 */               n |= 256;
/*      */             }
/*  177 */             if ((paramInt & 0x200) != 0) {
/*  178 */               n |= 512;
/*      */             }
/*  180 */             if ((paramInt & 0x400) != 0) {
/*  181 */               n |= 1024;
/*      */             }
/*  183 */             if ((paramInt & 0x10000) != 0) {
/*  184 */               n |= 65536;
/*      */             }
/*  186 */             this.physicalConn.needLine();
/*  187 */             this.physicalConn.sendPiggyBackedMessages();
/*  188 */             localT4CTTIOtxse.marshal(1, null, arrayOfByte1, paramXid.getFormatId(), k, m, this.timeout, n);
/*      */ 
/*  192 */             byte[] arrayOfByte4 = localT4CTTIOtxse.receive(this.applicationValueArr);
/*      */ 
/*  194 */             if (arrayOfByte4 != null) {
/*  195 */               this.context = arrayOfByte4;
/*      */             }
/*  197 */             i = 0;
/*      */           }
/*      */           catch (IOException localIOException)
/*      */           {
/*  206 */             DatabaseError.throwSqlException(localIOException);
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (SQLException localSQLException)
/*      */         {
/*  214 */           i = localSQLException.getErrorCode();
/*      */ 
/*  218 */           if (i == 0) {
/*  219 */             throw new XAException(-6);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  263 */     return i;
/*      */   }
/*      */ 
/*      */   protected int doEnd(Xid paramXid, int paramInt)
/*      */     throws XAException
/*      */   {
/*  273 */     int i = -1;
/*      */ 
/*  275 */     synchronized (this.physicalConn)
/*      */     {
/*  277 */       synchronized (this)
/*      */       {
/*      */         try
/*      */         {
/*      */           try
/*      */           {
/*  301 */             T4CTTIOtxse localT4CTTIOtxse = this.physicalConn.otxse;
/*  302 */             byte[] arrayOfByte1 = null;
/*  303 */             byte[] arrayOfByte2 = paramXid.getGlobalTransactionId();
/*  304 */             byte[] arrayOfByte3 = paramXid.getBranchQualifier();
/*      */ 
/*  306 */             int j = 0;
/*  307 */             int k = 0;
/*      */ 
/*  309 */             if ((arrayOfByte2 != null) && (arrayOfByte3 != null))
/*      */             {
/*  311 */               j = Math.min(arrayOfByte2.length, 64);
/*  312 */               k = Math.min(arrayOfByte3.length, 64);
/*  313 */               arrayOfByte1 = new byte[''];
/*      */ 
/*  315 */               System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, j);
/*  316 */               System.arraycopy(arrayOfByte3, 0, arrayOfByte1, j, k);
/*      */             }
/*      */ 
/*  319 */             byte[] arrayOfByte4 = this.context;
/*  320 */             int m = 0;
/*  321 */             if ((((paramInt & 0x2) == 2) || ((paramInt & 0x100000) != 1048576)) && ((paramInt & 0x2000000) == 33554432))
/*      */             {
/*  324 */               m = 1048576;
/*      */             }
/*      */ 
/*  328 */             this.physicalConn.needLine();
/*  329 */             this.physicalConn.sendPiggyBackedMessages();
/*  330 */             localT4CTTIOtxse.marshal(2, arrayOfByte4, arrayOfByte1, paramXid.getFormatId(), j, k, this.timeout, m);
/*      */ 
/*  334 */             byte[] arrayOfByte5 = localT4CTTIOtxse.receive(this.applicationValueArr);
/*      */ 
/*  336 */             if (arrayOfByte5 != null) {
/*  337 */               this.context = arrayOfByte5;
/*      */             }
/*  339 */             i = 0;
/*      */           }
/*      */           catch (IOException localIOException)
/*      */           {
/*  348 */             DatabaseError.throwSqlException(localIOException);
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (SQLException localSQLException)
/*      */         {
/*  356 */           i = localSQLException.getErrorCode();
/*      */ 
/*  360 */           if (i == 0) {
/*  361 */             throw new XAException(-6);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  366 */     return i;
/*      */   }
/*      */ 
/*      */   protected int doCommit(Xid paramXid, int paramInt)
/*      */     throws XAException
/*      */   {
/*  377 */     int i = -1;
/*      */ 
/*  379 */     synchronized (this.physicalConn)
/*      */     {
/*  381 */       synchronized (this)
/*      */       {
/*  384 */         int j = paramInt == 1 ? 4 : 2;
/*      */ 
/*  387 */         i = doTransaction(paramXid, 1, j);
/*      */ 
/*  390 */         if ((paramInt == 1) && ((i == 2) || (i == 4)))
/*      */         {
/*  397 */           i = 0;
/*      */         }
/*  399 */         else if ((paramInt != 1) && (i == 5))
/*      */         {
/*  404 */           i = 0;
/*      */         }
/*  406 */         else if (i == 8)
/*      */         {
/*  411 */           throw new XAException(106);
/*      */         }
/*      */ 
/*  415 */         if (i == 24756)
/*      */         {
/*  419 */           i = kputxrec(paramXid, 1, this.timeout + 120);
/*      */         }
/*  421 */         else if (i == 24780)
/*      */         {
/*  428 */           OracleXADataSource localOracleXADataSource = null;
/*  429 */           XAConnection localXAConnection = null;
/*      */           try
/*      */           {
/*  433 */             localOracleXADataSource = new OracleXADataSource();
/*      */ 
/*  435 */             localOracleXADataSource.setURL(this.physicalConn.url);
/*  436 */             localOracleXADataSource.setUser(this.physicalConn.user);
/*  437 */             this.physicalConn.getPasswordInternal(this);
/*  438 */             localOracleXADataSource.setPassword(this.password);
/*      */ 
/*  440 */             localXAConnection = localOracleXADataSource.getXAConnection();
/*      */ 
/*  442 */             XAResource localXAResource = localXAConnection.getXAResource();
/*      */ 
/*  444 */             localXAResource.commit(paramXid, paramInt == 1);
/*      */ 
/*  446 */             i = 0;
/*      */           }
/*      */           catch (SQLException localSQLException)
/*      */           {
/*  453 */             throw new XAException(-6);
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  459 */               if (localXAConnection != null) {
/*  460 */                 localXAConnection.close();
/*      */               }
/*  462 */               if (localOracleXADataSource != null) {
/*  463 */                 localOracleXADataSource.close();
/*      */               }
/*      */             }
/*      */             catch (Exception localException)
/*      */             {
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  474 */     return i;
/*      */   }
/*      */ 
/*      */   protected int doPrepare(Xid paramXid)
/*      */     throws XAException
/*      */   {
/*  484 */     int i = -1;
/*      */ 
/*  486 */     synchronized (this.physicalConn)
/*      */     {
/*  488 */       synchronized (this)
/*      */       {
/*  490 */         i = doTransaction(paramXid, 3, 0);
/*      */ 
/*  493 */         if (i == 8)
/*      */         {
/*  498 */           throw new XAException(106);
/*      */         }
/*  500 */         if (i == 4)
/*      */         {
/*  506 */           i = 24767;
/*      */         }
/*  510 */         else if (i == 1)
/*      */         {
/*  515 */           i = 0;
/*      */         }
/*  517 */         else if (i == 3)
/*      */         {
/*  522 */           i = 24761;
/*      */         } else {
/*  524 */           if ((i == 2054) || (i == 24756)) {
/*  525 */             return i;
/*      */           }
/*      */ 
/*  528 */           i = 24768;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  535 */     return i;
/*      */   }
/*      */ 
/*      */   protected int doForget(Xid paramXid)
/*      */     throws XAException
/*      */   {
/*  545 */     int i = -1;
/*      */ 
/*  547 */     synchronized (this.physicalConn)
/*      */     {
/*  549 */       synchronized (this)
/*      */       {
/*      */         try
/*      */         {
/*  554 */           if (OracleXid.isLocalTransaction(paramXid)) {
/*  555 */             j = 24771;
/*      */             return j;
/*      */           }
/*  559 */           int j = doStart(paramXid, 134217728);
/*      */ 
/*  561 */           if (j != 24756)
/*      */           {
/*  567 */             if (j == 0)
/*      */             {
/*      */               try
/*      */               {
/*  573 */                 doEnd(paramXid, 0);
/*      */               }
/*      */               catch (Exception localException)
/*      */               {
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  583 */             if ((j == 0) || (j == 2079) || (j == 24754) || (j == 24761) || (j == 24774) || (j == 24776) || (j == 25351))
/*      */             {
/*  590 */               k = 24769;
/*      */               return k;
/*      */             }
/*  591 */             if (j == 24752) {
/*  592 */               k = 24771;
/*      */               return k;
/*      */             }
/*  594 */             int k = j;
/*      */             return k;
/*      */           }
/*  597 */           i = kputxrec(paramXid, 4, 1);
/*      */         }
/*      */         finally
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  605 */     return i;
/*      */   }
/*      */ 
/*      */   protected int doRollback(Xid paramXid)
/*      */     throws XAException
/*      */   {
/*  615 */     int i = -1;
/*      */ 
/*  617 */     synchronized (this.physicalConn)
/*      */     {
/*  619 */       synchronized (this)
/*      */       {
/*  622 */         i = doTransaction(paramXid, 2, 3);
/*      */ 
/*  625 */         if (i == 8)
/*      */         {
/*  630 */           throw new XAException(106);
/*      */         }
/*  632 */         if ((i == 3) || (i == 25402))
/*      */         {
/*  639 */           i = 0;
/*      */         }
/*      */ 
/*  642 */         if (i == 24756)
/*      */         {
/*  646 */           i = kputxrec(paramXid, 2, this.timeout + 120);
/*      */         }
/*  648 */         else if (i == 24780)
/*      */         {
/*  654 */           OracleXADataSource localOracleXADataSource = null;
/*  655 */           XAConnection localXAConnection = null;
/*      */           try
/*      */           {
/*  659 */             localOracleXADataSource = new OracleXADataSource();
/*      */ 
/*  661 */             localOracleXADataSource.setURL(this.physicalConn.url);
/*  662 */             localOracleXADataSource.setUser(this.physicalConn.user);
/*  663 */             this.physicalConn.getPasswordInternal(this);
/*  664 */             localOracleXADataSource.setPassword(this.password);
/*      */ 
/*  666 */             localXAConnection = localOracleXADataSource.getXAConnection();
/*      */ 
/*  668 */             XAResource localXAResource = localXAConnection.getXAResource();
/*      */ 
/*  670 */             localXAResource.rollback(paramXid);
/*      */ 
/*  672 */             i = 0;
/*      */           }
/*      */           catch (SQLException localSQLException)
/*      */           {
/*  679 */             throw new XAException(-6);
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  685 */               if (localXAConnection != null) {
/*  686 */                 localXAConnection.close();
/*      */               }
/*  688 */               if (localOracleXADataSource != null) {
/*  689 */                 localOracleXADataSource.close();
/*      */               }
/*      */             }
/*      */             catch (Exception localException)
/*      */             {
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  699 */     return i;
/*      */   }
/*      */ 
/*      */   int doTransaction(Xid paramXid, int paramInt1, int paramInt2)
/*      */     throws XAException
/*      */   {
/*  707 */     int i = -1;
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/*  715 */         T4CTTIOtxen localT4CTTIOtxen = this.physicalConn.otxen;
/*  716 */         byte[] arrayOfByte1 = null;
/*  717 */         byte[] arrayOfByte2 = paramXid.getGlobalTransactionId();
/*  718 */         byte[] arrayOfByte3 = paramXid.getBranchQualifier();
/*      */ 
/*  720 */         int j = 0;
/*  721 */         int k = 0;
/*      */ 
/*  723 */         if ((arrayOfByte2 != null) && (arrayOfByte3 != null))
/*      */         {
/*  725 */           j = Math.min(arrayOfByte2.length, 64);
/*  726 */           k = Math.min(arrayOfByte3.length, 64);
/*  727 */           arrayOfByte1 = new byte[''];
/*      */ 
/*  729 */           System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, j);
/*  730 */           System.arraycopy(arrayOfByte3, 0, arrayOfByte1, j, k);
/*      */         }
/*      */ 
/*  733 */         byte[] arrayOfByte4 = this.context;
/*      */ 
/*  735 */         this.physicalConn.needLine();
/*  736 */         this.physicalConn.sendPiggyBackedMessages();
/*  737 */         localT4CTTIOtxen.marshal(paramInt1, arrayOfByte4, arrayOfByte1, paramXid.getFormatId(), j, k, this.timeout, paramInt2);
/*      */ 
/*  740 */         i = localT4CTTIOtxen.receive(this.errorNumber);
/*      */       }
/*      */       catch (IOException localIOException)
/*      */       {
/*  752 */         DatabaseError.throwSqlException(localIOException);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  760 */       this.errorNumber[0] = localSQLException.getErrorCode();
/*      */     }
/*      */ 
/*  765 */     if (this.errorNumber[0] == 0) {
/*  766 */       throw new XAException(-6);
/*      */     }
/*  768 */     if (i == -1)
/*      */     {
/*  771 */       i = this.errorNumber[0];
/*      */     }
/*      */ 
/*  776 */     return i;
/*      */   }
/*      */ 
/*      */   protected int kputxrec(Xid paramXid, int paramInt1, int paramInt2)
/*      */     throws XAException
/*      */   {
/*  789 */     int i = 0;
/*      */     int j;
/*  795 */     switch (paramInt1)
/*      */     {
/*      */     case 1:
/*  799 */       j = 3;
/*      */ 
/*  801 */       break;
/*      */     case 4:
/*  804 */       j = 2;
/*      */ 
/*  806 */       break;
/*      */     default:
/*  809 */       j = 0;
/*      */     }
/*      */ 
/*  812 */     int k = 0;
/*      */ 
/*  814 */     while (paramInt2-- > 0)
/*      */     {
/*  816 */       k = doTransaction(paramXid, 5, j);
/*      */ 
/*  818 */       if (k != 7)
/*      */         break;
/*      */       try
/*      */       {
/*  822 */         Thread.sleep(1000L);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  832 */     if (k == 7)
/*      */     {
/*  839 */       return 24763;
/*      */     }
/*  841 */     if (k == 24756)
/*  842 */       return 24756;
/*      */     int m;
/*  846 */     switch (k)
/*      */     {
/*      */     case 3:
/*  850 */       if (paramInt1 == 1) {
/*  851 */         m = 7; break label265;
/*      */       }
/*      */ 
/*  856 */       m = 8;
/*  857 */       i = -24762;
/*      */ 
/*  860 */       break;
/*      */     case 0:
/*  863 */       if (paramInt1 == 4)
/*      */       {
/*  865 */         m = 8;
/*  866 */         i = 24762; break label265;
/*      */       }
/*      */ 
/*  870 */       m = 7;
/*  871 */       i = paramInt1 == 1 ? 24756 : 0;
/*      */ 
/*  874 */       break;
/*      */     case 2:
/*  877 */       if (paramInt1 != 4)
/*      */         break;
/*  879 */       m = 8;
/*      */ 
/*  881 */       i = 24770;
/*      */ 
/*  883 */       break;
/*      */     case 1:
/*      */     }
/*      */ 
/*  887 */     Object localObject = new int[1];
/*      */ 
/*  889 */     i = kpuho2oc(k, localObject);
/*      */ 
/*  891 */     if ((paramInt1 == 4) && (localObject[0] == 1))
/*      */     {
/*  893 */       m = 7;
/*  894 */       i = 0;
/*      */     }
/*      */     else {
/*  897 */       m = 8;
/*      */     }
/*      */ 
/*  904 */     label265: localObject = this.physicalConn.k2rpc;
/*      */     try
/*      */     {
/*  908 */       ((T4CTTIk2rpc)localObject).marshal(3, m);
/*  909 */       ((T4CTTIk2rpc)localObject).receive();
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  916 */       throw new XAException(-7);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  923 */       throw new XAException(-6);
/*      */     }
/*      */ 
/*  926 */     return i;
/*      */   }
/*      */ 
/*      */   int kpuho2oc(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  932 */     int i = 0;
/*      */ 
/*  934 */     switch (paramInt)
/*      */     {
/*      */     case 5:
/*  938 */       paramArrayOfInt[0] = 1;
/*  939 */       i = 24764;
/*      */ 
/*  941 */       break;
/*      */     case 4:
/*  944 */       paramArrayOfInt[0] = 1;
/*  945 */       i = 24765;
/*      */ 
/*  947 */       break;
/*      */     case 6:
/*  950 */       paramArrayOfInt[0] = 1;
/*  951 */       i = 24766;
/*      */ 
/*  953 */       break;
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     default:
/*  972 */       i = 24762;
/*      */     }
/*      */ 
/*  975 */     return i;
/*      */   }
/*      */ 
/*      */   final void setPasswordInternal(String paramString)
/*      */   {
/*  980 */     this.password = paramString;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CXAResource
 * JD-Core Version:    0.6.0
 */