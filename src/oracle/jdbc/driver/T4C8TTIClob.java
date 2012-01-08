/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.sql.CLOB;
/*     */ import oracle.sql.CharacterSet;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ class T4C8TTIClob extends T4C8TTILob
/*     */ {
/*     */   int[] nBytes;
/* 631 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4C8TTIClob(T4CMAREngine paramT4CMAREngine, T4CTTIoer paramT4CTTIoer)
/*     */   {
/* 126 */     super(paramT4CMAREngine, paramT4CTTIoer);
/*     */ 
/* 128 */     this.nBytes = new int[1];
/*     */   }
/*     */ 
/*     */   long read(byte[] paramArrayOfByte, long paramLong1, long paramLong2, boolean paramBoolean, char[] paramArrayOfChar)
/*     */     throws SQLException, IOException
/*     */   {
/* 161 */     long l1 = 0L;
/*     */ 
/* 176 */     byte[] arrayOfByte = null;
/*     */ 
/* 179 */     initializeLobdef();
/*     */ 
/* 187 */     if ((paramArrayOfByte[6] & 0x80) == 128) {
/* 188 */       this.varWidthChar = true;
/*     */     }
/* 190 */     if (this.varWidthChar == true)
/* 191 */       arrayOfByte = new byte[(int)paramLong2 * 2];
/*     */     else {
/* 193 */       arrayOfByte = new byte[(int)paramLong2 * 3];
/*     */     }
/*     */ 
/* 201 */     if ((paramArrayOfByte[7] & 0x40) > 0) {
/* 202 */       this.littleEndianClob = true;
/*     */     }
/*     */ 
/* 206 */     this.lobops = 2L;
/* 207 */     this.sourceLobLocator = paramArrayOfByte;
/* 208 */     this.sourceOffset = paramLong1;
/* 209 */     this.lobamt = paramLong2;
/* 210 */     this.sendLobamt = true;
/* 211 */     this.outBuffer = arrayOfByte;
/*     */ 
/* 214 */     marshalFunHeader();
/*     */ 
/* 217 */     marshalOlobops();
/*     */ 
/* 222 */     receiveReply();
/*     */ 
/* 224 */     long l2 = this.lobamt;
/*     */ 
/* 230 */     long l3 = 0L;
/*     */ 
/* 235 */     if (this.varWidthChar == true)
/*     */     {
/* 237 */       if (this.meg.versionNumber < 10101)
/*     */       {
/* 242 */         DBConversion.ucs2BytesToJavaChars(arrayOfByte, arrayOfByte.length, paramArrayOfChar);
/*     */       }
/* 245 */       else if (this.littleEndianClob)
/*     */       {
/* 247 */         CharacterSet.convertAL16UTF16LEBytesToJavaChars(arrayOfByte, 0, paramArrayOfChar, 0, (int)this.lobBytesRead, true);
/*     */       }
/*     */       else
/*     */       {
/* 252 */         CharacterSet.convertAL16UTF16BytesToJavaChars(arrayOfByte, 0, paramArrayOfChar, 0, (int)this.lobBytesRead, true);
/*     */       }
/*     */ 
/*     */     }
/* 260 */     else if (!paramBoolean)
/*     */     {
/* 265 */       this.nBytes[0] = (int)this.lobBytesRead;
/*     */ 
/* 267 */       this.meg.conv.CHARBytesToJavaChars(arrayOfByte, 0, paramArrayOfChar, 0, this.nBytes, paramArrayOfChar.length);
/*     */     }
/*     */     else
/*     */     {
/* 278 */       this.nBytes[0] = (int)this.lobBytesRead;
/*     */ 
/* 280 */       this.meg.conv.NCHARBytesToJavaChars(arrayOfByte, 0, paramArrayOfChar, 0, this.nBytes, paramArrayOfChar.length);
/*     */     }
/*     */ 
/* 289 */     return l2;
/*     */   }
/*     */ 
/*     */   long write(byte[] paramArrayOfByte, long paramLong1, boolean paramBoolean, char[] paramArrayOfChar, long paramLong2, long paramLong3)
/*     */     throws SQLException, IOException
/*     */   {
/* 330 */     int i = 0;
/*     */ 
/* 333 */     if ((paramArrayOfByte[6] & 0x80) == 128) {
/* 334 */       i = 1;
/*     */     }
/* 336 */     if ((paramArrayOfByte[7] & 0x40) == 64) {
/* 337 */       this.littleEndianClob = true;
/*     */     }
/*     */ 
/* 341 */     long l1 = 0L;
/* 342 */     byte[] arrayOfByte = null;
/*     */ 
/* 348 */     if (i == 1)
/*     */     {
/* 353 */       arrayOfByte = new byte[(int)paramLong3 * 2];
/*     */ 
/* 355 */       if (this.meg.versionNumber < 10101)
/*     */       {
/* 358 */         DBConversion.javaCharsToUcs2Bytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
/*     */       }
/* 360 */       else if (this.littleEndianClob)
/*     */       {
/* 363 */         CharacterSet.convertJavaCharsToAL16UTF16LEBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
/*     */       }
/*     */       else
/*     */       {
/* 368 */         CharacterSet.convertJavaCharsToAL16UTF16Bytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 376 */       arrayOfByte = new byte[(int)paramLong3 * 3];
/*     */ 
/* 378 */       if (!paramBoolean)
/*     */       {
/* 383 */         l1 = this.meg.conv.javaCharsToCHARBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
/*     */       }
/*     */       else
/*     */       {
/* 391 */         l1 = this.meg.conv.javaCharsToNCHARBytes(paramArrayOfChar, (int)paramLong2, arrayOfByte, 0, (int)paramLong3);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 398 */     initializeLobdef();
/*     */ 
/* 401 */     this.lobops = 64L;
/* 402 */     this.sourceLobLocator = paramArrayOfByte;
/* 403 */     this.sourceOffset = paramLong1;
/* 404 */     this.lobamt = paramLong3;
/* 405 */     this.sendLobamt = true;
/* 406 */     this.inBuffer = arrayOfByte;
/*     */ 
/* 409 */     marshalFunHeader();
/*     */ 
/* 412 */     marshalOlobops();
/*     */ 
/* 416 */     if (i == 1)
/*     */     {
/* 421 */       if (this.meg.versionNumber < 10101)
/* 422 */         this.lobd.marshalLobDataUB2(arrayOfByte, 0L, paramLong3);
/*     */       else {
/* 424 */         this.lobd.marshalLobData(arrayOfByte, 0L, paramLong3 * 2L);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 431 */       this.lobd.marshalLobData(arrayOfByte, 0L, l1);
/*     */     }
/*     */ 
/* 435 */     receiveReply();
/*     */ 
/* 437 */     long l2 = this.lobamt;
/*     */ 
/* 442 */     return l2;
/*     */   }
/*     */ 
/*     */   Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 463 */     return createTemporaryLob(paramConnection, paramBoolean, paramInt, 1);
/*     */   }
/*     */ 
/*     */   Datum createTemporaryLob(Connection paramConnection, boolean paramBoolean, int paramInt, short paramShort)
/*     */     throws SQLException, IOException
/*     */   {
/* 479 */     if (paramInt == 12) {
/* 480 */       DatabaseError.throwSqlException(158);
/*     */     }
/*     */ 
/* 484 */     CLOB localCLOB = null;
/*     */ 
/* 487 */     initializeLobdef();
/*     */ 
/* 490 */     this.lobops = 272L;
/* 491 */     this.sourceLobLocator = new byte[86];
/* 492 */     this.sourceLobLocator[1] = 84;
/*     */ 
/* 495 */     this.lobamt = 10L;
/* 496 */     this.sendLobamt = true;
/*     */ 
/* 499 */     if (paramShort == 1)
/* 500 */       this.sourceOffset = 1L;
/*     */     else {
/* 502 */       this.sourceOffset = 2L;
/*     */     }
/*     */ 
/* 506 */     this.destinationOffset = 112L;
/*     */ 
/* 509 */     this.destinationLength = paramInt;
/*     */ 
/* 512 */     this.nullO2U = true;
/*     */ 
/* 517 */     this.characterSet = (paramShort == 2 ? this.meg.conv.getNCharSetId() : this.meg.conv.getServerCharSetId());
/*     */ 
/* 519 */     marshalFunHeader();
/*     */ 
/* 522 */     marshalOlobops();
/*     */ 
/* 525 */     receiveReply();
/*     */ 
/* 529 */     if (this.sourceLobLocator != null)
/*     */     {
/* 531 */       localCLOB = new CLOB((OracleConnection)paramConnection, this.sourceLobLocator);
/*     */     }
/*     */ 
/* 538 */     return localCLOB;
/*     */   }
/*     */ 
/*     */   boolean open(byte[] paramArrayOfByte, int paramInt)
/*     */     throws SQLException, IOException
/*     */   {
/* 558 */     boolean bool = false;
/*     */ 
/* 562 */     int i = 2;
/*     */ 
/* 564 */     if (paramInt == 0) {
/* 565 */       i = 1;
/*     */     }
/* 567 */     bool = _open(paramArrayOfByte, i, 32768);
/*     */ 
/* 572 */     return bool;
/*     */   }
/*     */ 
/*     */   boolean close(byte[] paramArrayOfByte)
/*     */     throws SQLException, IOException
/*     */   {
/* 592 */     boolean bool = false;
/*     */ 
/* 594 */     bool = _close(paramArrayOfByte, 65536);
/*     */ 
/* 599 */     return bool;
/*     */   }
/*     */ 
/*     */   boolean isOpen(byte[] paramArrayOfByte)
/*     */     throws SQLException, IOException
/*     */   {
/* 619 */     boolean bool = false;
/*     */ 
/* 621 */     bool = _isOpen(paramArrayOfByte, 69632);
/*     */ 
/* 626 */     return bool;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTIClob
 * JD-Core Version:    0.6.0
 */