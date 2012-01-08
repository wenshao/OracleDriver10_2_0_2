/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import oracle.net.ns.BreakNetException;
/*      */ import oracle.sql.Datum;
/*      */ 
/*      */ abstract class T4C8TTILob extends T4CTTIfun
/*      */ {
/*      */   static final int KPLOB_READ = 2;
/*      */   static final int KPLOB_WRITE = 64;
/*      */   static final int KPLOB_WRITE_APPEND = 8192;
/*      */   static final int KPLOB_PAGE_SIZE = 16384;
/*      */   static final int KPLOB_FILE_OPEN = 256;
/*      */   static final int KPLOB_FILE_ISOPEN = 1024;
/*      */   static final int KPLOB_FILE_EXISTS = 2048;
/*      */   static final int KPLOB_FILE_CLOSE = 512;
/*      */   static final int KPLOB_OPEN = 32768;
/*      */   static final int KPLOB_CLOSE = 65536;
/*      */   static final int KPLOB_ISOPEN = 69632;
/*      */   static final int KPLOB_TMP_CREATE = 272;
/*      */   static final int KPLOB_TMP_FREE = 273;
/*      */   static final int KPLOB_GET_LEN = 1;
/*      */   static final int KPLOB_TRIM = 32;
/*      */   static final int KOKL_ORDONLY = 1;
/*      */   static final int KOKL_ORDWR = 2;
/*      */   static final int KOLF_ORDONLY = 11;
/*      */   static final byte KOLBLOPEN = 8;
/*      */   static final byte KOLBLTMP = 1;
/*      */   static final byte KOLBLRDWR = 16;
/*      */   static final byte KOLBLABS = 64;
/*      */   static final byte ALLFLAGS = -1;
/*      */   static final byte KOLBLFLGB = 4;
/*      */   static final byte KOLLFLG = 4;
/*      */   static final byte KOLL3FLG = 7;
/*      */   static final byte KOLBLVLE = 64;
/*      */   static final int DTYCLOB = 112;
/*      */   static final int DTYBLOB = 113;
/*  128 */   byte[] sourceLobLocator = null;
/*  129 */   byte[] destinationLobLocator = null;
/*  130 */   long sourceOffset = 0L;
/*  131 */   long destinationOffset = 0L;
/*  132 */   int destinationLength = 0;
/*  133 */   short characterSet = 0;
/*  134 */   long lobamt = 0L;
/*  135 */   boolean lobnull = false;
/*  136 */   long lobops = 0L;
/*  137 */   int[] lobscn = null;
/*  138 */   int lobscnl = 0;
/*      */ 
/*  143 */   boolean nullO2U = false;
/*      */ 
/*  148 */   boolean sendLobamt = false;
/*      */ 
/*  152 */   byte[] inBuffer = null;
/*  153 */   byte[] outBuffer = null;
/*  154 */   int rowsProcessed = 0;
/*  155 */   long lobBytesRead = 0L;
/*  156 */   boolean varWidthChar = false;
/*  157 */   boolean littleEndianClob = false;
/*  158 */   T4C8TTILobd lobd = null;
/*      */ 
/* 1041 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*      */ 
/*      */   T4C8TTILob(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*      */   {
/*  164 */     super(3, 0, 96);
/*      */ 
/*  166 */     setMarshalingEngine(paramT4CMAREngine);
/*      */ 
/*  169 */     this.oer = paramT4CTTIoer;
/*      */ 
/*  172 */     this.lobd = new T4C8TTILobd(paramT4CMAREngine);
/*      */   }
/*      */ 
/*      */   long read(byte[] paramArrayOfByte1, long paramLong1, long paramLong2, byte[] paramArrayOfByte2)
/*      */     throws SQLException, IOException
/*      */   {
/*  188 */     initializeLobdef();
/*      */ 
/*  191 */     this.lobops = 2L;
/*  192 */     this.sourceLobLocator = paramArrayOfByte1;
/*  193 */     this.sourceOffset = paramLong1;
/*  194 */     this.lobamt = paramLong2;
/*  195 */     this.sendLobamt = true;
/*  196 */     this.outBuffer = paramArrayOfByte2;
/*      */ 
/*  199 */     marshalFunHeader();
/*      */ 
/*  202 */     marshalOlobops();
/*      */ 
/*  205 */     receiveReply();
/*      */ 
/*  210 */     return this.lobBytesRead;
/*      */   }
/*      */ 
/*      */   long write(byte[] paramArrayOfByte1, long paramLong1, byte[] paramArrayOfByte2, long paramLong2, long paramLong3)
/*      */     throws SQLException, IOException
/*      */   {
/*  226 */     long l = 0L;
/*      */ 
/*  229 */     initializeLobdef();
/*      */ 
/*  232 */     this.lobops = 64L;
/*  233 */     this.sourceLobLocator = paramArrayOfByte1;
/*  234 */     this.sourceOffset = paramLong1;
/*  235 */     this.lobamt = paramLong3;
/*  236 */     this.sendLobamt = true;
/*  237 */     this.inBuffer = paramArrayOfByte2;
/*      */ 
/*  240 */     marshalFunHeader();
/*      */ 
/*  243 */     marshalOlobops();
/*      */ 
/*  246 */     this.lobd.marshalLobData(paramArrayOfByte2, paramLong2, paramLong3);
/*      */ 
/*  249 */     receiveReply();
/*      */ 
/*  251 */     l = this.lobamt;
/*      */ 
/*  256 */     return l;
/*      */   }
/*      */ 
/*      */   long getLength(byte[] paramArrayOfByte)
/*      */     throws SQLException, IOException
/*      */   {
/*  270 */     long l = 0L;
/*      */ 
/*  273 */     initializeLobdef();
/*      */ 
/*  276 */     this.lobops = 1L;
/*  277 */     this.sourceLobLocator = paramArrayOfByte;
/*      */ 
/*  280 */     this.sendLobamt = true;
/*      */ 
/*  283 */     marshalFunHeader();
/*      */ 
/*  286 */     marshalOlobops();
/*      */ 
/*  289 */     receiveReply();
/*      */ 
/*  291 */     l = this.lobamt;
/*      */ 
/*  296 */     return l;
/*      */   }
/*      */ 
/*      */   long getChunkSize(byte[] paramArrayOfByte)
/*      */     throws SQLException, IOException
/*      */   {
/*  310 */     long l = 0L;
/*      */ 
/*  313 */     initializeLobdef();
/*      */ 
/*  316 */     this.lobops = 16384L;
/*  317 */     this.sourceLobLocator = paramArrayOfByte;
/*      */ 
/*  320 */     marshalFunHeader();
/*      */ 
/*  323 */     this.sendLobamt = true;
/*      */ 
/*  326 */     marshalOlobops();
/*      */ 
/*  329 */     receiveReply();
/*      */ 
/*  331 */     l = this.lobamt;
/*      */ 
/*  336 */     return l;
/*      */   }
/*      */ 
/*      */   long trim(byte[] paramArrayOfByte, long paramLong)
/*      */     throws SQLException, IOException
/*      */   {
/*  350 */     long l = 0L;
/*      */ 
/*  353 */     initializeLobdef();
/*      */ 
/*  356 */     this.lobops = 32L;
/*  357 */     this.sourceLobLocator = paramArrayOfByte;
/*  358 */     this.lobamt = paramLong;
/*  359 */     this.sendLobamt = true;
/*      */ 
/*  362 */     marshalFunHeader();
/*      */ 
/*  365 */     marshalOlobops();
/*      */ 
/*  368 */     receiveReply();
/*      */ 
/*  370 */     l = this.lobamt;
/*      */ 
/*  375 */     return l;
/*      */   }
/*      */ 
/*      */   abstract Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
/*      */     throws SQLException, IOException;
/*      */ 
/*      */   void freeTemporaryLob(byte[] paramArrayOfByte)
/*      */     throws SQLException, IOException
/*      */   {
/*  398 */     initializeLobdef();
/*      */ 
/*  401 */     this.lobops = 273L;
/*  402 */     this.sourceLobLocator = paramArrayOfByte;
/*      */ 
/*  405 */     marshalFunHeader();
/*      */ 
/*  408 */     marshalOlobops();
/*      */ 
/*  411 */     receiveReply();
/*      */   }
/*      */ 
/*      */   abstract boolean open(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException, IOException;
/*      */ 
/*      */   boolean _open(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException, IOException
/*      */   {
/*  439 */     int i = 0;
/*      */ 
/*  444 */     if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
/*      */     {
/*  449 */       if ((paramArrayOfByte[7] & 0x8) == 8)
/*      */       {
/*  451 */         DatabaseError.throwSqlException(445);
/*      */       }
/*      */       else
/*      */       {
/*      */         byte[] tmp48_45 = paramArrayOfByte; tmp48_45[7] = (byte)(tmp48_45[7] | 0x8);
/*      */ 
/*  461 */         if (paramInt1 == 2)
/*      */         {
/*      */           byte[] tmp63_60 = paramArrayOfByte; tmp63_60[7] = (byte)(tmp63_60[7] | 0x10);
/*      */         }
/*  464 */         i = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  473 */       initializeLobdef();
/*      */ 
/*  476 */       this.sourceLobLocator = paramArrayOfByte;
/*  477 */       this.lobops = paramInt2;
/*  478 */       this.lobamt = paramInt1;
/*  479 */       this.sendLobamt = true;
/*      */ 
/*  482 */       marshalFunHeader();
/*      */ 
/*  485 */       marshalOlobops();
/*      */ 
/*  488 */       receiveReply();
/*      */ 
/*  491 */       if (this.lobamt != 0L) {
/*  492 */         i = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  498 */     return i;
/*      */   }
/*      */ 
/*      */   abstract boolean close(byte[] paramArrayOfByte)
/*      */     throws SQLException, IOException;
/*      */ 
/*      */   boolean _close(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException, IOException
/*      */   {
/*  515 */     int i = 1;
/*      */ 
/*  523 */     if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
/*      */     {
/*  528 */       if ((paramArrayOfByte[7] & 0x8) != 8)
/*      */       {
/*  530 */         DatabaseError.throwSqlException(446);
/*      */       }
/*      */       else
/*      */       {
/*      */         byte[] tmp47_44 = paramArrayOfByte; tmp47_44[7] = (byte)(tmp47_44[7] & 0xFFFFFFE7);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  545 */       initializeLobdef();
/*      */ 
/*  548 */       this.sourceLobLocator = paramArrayOfByte;
/*  549 */       this.lobops = paramInt;
/*      */ 
/*  552 */       marshalFunHeader();
/*      */ 
/*  555 */       marshalOlobops();
/*      */ 
/*  558 */       receiveReply();
/*      */     }
/*      */ 
/*  564 */     return i;
/*      */   }
/*      */ 
/*      */   abstract boolean isOpen(byte[] paramArrayOfByte)
/*      */     throws SQLException, IOException;
/*      */ 
/*      */   boolean _isOpen(byte[] paramArrayOfByte, int paramInt)
/*      */     throws SQLException, IOException
/*      */   {
/*  582 */     boolean bool = false;
/*      */ 
/*  590 */     if (((paramArrayOfByte[7] & 0x1) == 1) || ((paramArrayOfByte[4] & 0x40) == 64))
/*      */     {
/*  595 */       if ((paramArrayOfByte[7] & 0x8) == 8) {
/*  596 */         bool = true;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  604 */       initializeLobdef();
/*      */ 
/*  607 */       this.sourceLobLocator = paramArrayOfByte;
/*  608 */       this.lobops = paramInt;
/*  609 */       this.nullO2U = true;
/*      */ 
/*  612 */       marshalFunHeader();
/*      */ 
/*  615 */       marshalOlobops();
/*      */ 
/*  618 */       receiveReply();
/*      */ 
/*  621 */       bool = this.lobnull;
/*      */     }
/*      */ 
/*  627 */     return bool;
/*      */   }
/*      */ 
/*      */   void initializeLobdef()
/*      */   {
/*  641 */     this.sourceLobLocator = null;
/*  642 */     this.destinationLobLocator = null;
/*  643 */     this.sourceOffset = 0L;
/*  644 */     this.destinationOffset = 0L;
/*  645 */     this.destinationLength = 0;
/*  646 */     this.characterSet = 0;
/*  647 */     this.lobamt = 0L;
/*  648 */     this.lobnull = false;
/*  649 */     this.lobops = 0L;
/*  650 */     this.lobscn = null;
/*  651 */     this.lobscnl = 0;
/*  652 */     this.inBuffer = null;
/*  653 */     this.outBuffer = null;
/*  654 */     this.nullO2U = false;
/*  655 */     this.sendLobamt = false;
/*  656 */     this.varWidthChar = false;
/*  657 */     this.littleEndianClob = false;
/*  658 */     this.lobBytesRead = 0L;
/*      */   }
/*      */ 
/*      */   void marshalOlobops()
/*      */     throws IOException
/*      */   {
/*  694 */     int i = 0;
/*      */ 
/*  705 */     if (this.sourceLobLocator != null)
/*      */     {
/*  707 */       i = this.sourceLobLocator.length;
/*      */ 
/*  709 */       this.meg.marshalPTR();
/*      */     }
/*      */     else {
/*  712 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  715 */     this.meg.marshalSB4(i);
/*      */ 
/*  718 */     if (this.destinationLobLocator != null)
/*      */     {
/*  720 */       this.destinationLength = this.destinationLobLocator.length;
/*      */ 
/*  722 */       this.meg.marshalPTR();
/*      */     }
/*      */     else {
/*  725 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  728 */     this.meg.marshalSB4(this.destinationLength);
/*      */ 
/*  731 */     if (this.meg.versionNumber >= 10000)
/*  732 */       this.meg.marshalUB4(0L);
/*      */     else {
/*  734 */       this.meg.marshalUB4(this.sourceOffset);
/*      */     }
/*      */ 
/*  737 */     if (this.meg.versionNumber >= 10000)
/*  738 */       this.meg.marshalUB4(0L);
/*      */     else {
/*  740 */       this.meg.marshalUB4(this.destinationOffset);
/*      */     }
/*      */ 
/*  743 */     if (this.characterSet != 0)
/*  744 */       this.meg.marshalPTR();
/*      */     else {
/*  746 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  750 */     if ((this.sendLobamt == true) && (this.meg.versionNumber < 10000))
/*  751 */       this.meg.marshalPTR();
/*      */     else {
/*  753 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  761 */     if (this.nullO2U == true)
/*  762 */       this.meg.marshalPTR();
/*      */     else {
/*  764 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  767 */     this.meg.marshalUB4(this.lobops);
/*      */ 
/*  770 */     if (this.lobscnl != 0)
/*  771 */       this.meg.marshalPTR();
/*      */     else {
/*  773 */       this.meg.marshalNULLPTR();
/*      */     }
/*      */ 
/*  776 */     this.meg.marshalSB4(this.lobscnl);
/*      */ 
/*  778 */     if (this.meg.versionNumber >= 10000)
/*      */     {
/*  780 */       this.meg.marshalSB8(this.sourceOffset);
/*  781 */       this.meg.marshalSB8(this.destinationOffset);
/*      */ 
/*  783 */       if (this.sendLobamt == true)
/*  784 */         this.meg.marshalPTR();
/*      */       else {
/*  786 */         this.meg.marshalNULLPTR();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  797 */     if (this.sourceLobLocator != null)
/*      */     {
/*  799 */       this.meg.marshalB1Array(this.sourceLobLocator);
/*      */     }
/*      */ 
/*  803 */     if (this.destinationLobLocator != null)
/*      */     {
/*  805 */       this.meg.marshalB1Array(this.destinationLobLocator);
/*      */     }
/*      */ 
/*  809 */     if (this.characterSet != 0)
/*      */     {
/*  811 */       this.meg.marshalUB2(this.characterSet);
/*      */     }
/*      */ 
/*  815 */     if ((this.sendLobamt == true) && (this.meg.versionNumber < 10000))
/*      */     {
/*  819 */       this.meg.marshalUB4(this.lobamt);
/*      */     }
/*      */ 
/*  824 */     if (this.lobscnl != 0)
/*      */     {
/*  826 */       for (int j = 0; j < this.lobscnl; j++)
/*      */       {
/*  828 */         this.meg.marshalUB4(this.lobscn[j]);
/*      */       }
/*      */     }
/*      */ 
/*  832 */     if ((this.sendLobamt == true) && (this.meg.versionNumber >= 10000))
/*      */     {
/*  834 */       this.meg.marshalSB8(this.lobamt);
/*      */     }
/*      */   }
/*      */ 
/*      */   void receiveReply()
/*      */     throws IOException, SQLException
/*      */   {
/*  866 */     int i = 0;
/*      */     while (true)
/*      */     {
/*      */       try
/*      */       {
/*  872 */         i = this.meg.unmarshalSB1();
/*      */ 
/*  877 */         switch (i)
/*      */         {
/*      */         case 8:
/*  883 */           unmarshalTTIRPA();
/*      */ 
/*  885 */           break;
/*      */         case 14:
/*  890 */           if (!this.varWidthChar) {
/*  891 */             this.lobBytesRead = this.lobd.unmarshalLobData(this.outBuffer);
/*      */           }
/*      */           else {
/*  894 */             if (this.meg.versionNumber >= 10101)
/*      */             {
/*      */               continue;
/*      */             }
/*      */ 
/*  900 */             this.lobBytesRead = this.lobd.unmarshalClobUB2(this.outBuffer); continue;
/*      */ 
/*  904 */             this.lobBytesRead = this.lobd.unmarshalLobData(this.outBuffer);
/*      */           }
/*      */ 
/*  908 */           break;
/*      */         case 4:
/*  913 */           this.oer.init();
/*  914 */           this.oer.unmarshal();
/*      */ 
/*  921 */           this.rowsProcessed = this.oer.getCurRowNumber();
/*      */ 
/*  926 */           if (this.oer.getRetCode() == 1403)
/*      */           {
/*      */             continue;
/*      */           }
/*  930 */           this.oer.processError();
/*      */ 
/*  932 */           break;
/*      */         case 9:
/*  935 */           break;
/*      */         default:
/*  938 */           DatabaseError.throwSqlException(401);
/*      */ 
/*  940 */           break;
/*      */         }
/*      */ 
/*  946 */         continue;
/*      */       }
/*      */       catch (BreakNetException localBreakNetException)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void unmarshalTTIRPA()
/*      */     throws SQLException, IOException
/*      */   {
/*      */     int i;
/*  977 */     if (this.sourceLobLocator != null)
/*      */     {
/*  979 */       i = this.sourceLobLocator.length;
/*      */ 
/*  981 */       this.meg.getNBytes(this.sourceLobLocator, 0, i);
/*      */     }
/*      */ 
/*  985 */     if (this.destinationLobLocator != null)
/*      */     {
/*  987 */       i = this.meg.unmarshalSB2();
/*      */ 
/*  989 */       this.destinationLobLocator = this.meg.unmarshalNBytes(i);
/*      */     }
/*      */ 
/*  993 */     if (this.characterSet != 0)
/*      */     {
/*  995 */       this.characterSet = this.meg.unmarshalSB2();
/*      */     }
/*      */ 
/*  999 */     if (this.sendLobamt == true)
/*      */     {
/* 1001 */       if (this.meg.versionNumber >= 10000)
/* 1002 */         this.lobamt = this.meg.unmarshalSB8();
/*      */       else {
/* 1004 */         this.lobamt = this.meg.unmarshalUB4();
/*      */       }
/*      */     }
/*      */ 
/* 1008 */     if (this.nullO2U == true)
/*      */     {
/* 1015 */       i = this.meg.unmarshalSB2();
/*      */ 
/* 1018 */       if (i != 0) {
/* 1019 */         this.lobnull = true;
/*      */       }
/*      */     }
/*      */ 
/* 1023 */     if (this.lobscnl != 0)
/*      */     {
/* 1025 */       for (i = 0; i < this.lobscnl; i++)
/*      */       {
/* 1027 */         this.lobscn[i] = this.meg.unmarshalSB4();
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTILob
 * JD-Core Version:    0.6.0
 */