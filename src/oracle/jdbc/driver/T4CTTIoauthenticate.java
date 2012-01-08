/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.TimeZone;
/*      */ import oracle.jdbc.util.RepConversion;
/*      */ import oracle.net.ns.BreakNetException;
/*      */ import oracle.security.o3logon.O3LoginClientHelper;
/*      */ import oracle.sql.converter.CharacterSetMetaData;
/*      */ 
/*      */ class T4CTTIoauthenticate extends T4CTTIfun
/*      */ {
/*      */   byte[] user;
/*      */   String userStr;
/*      */   byte[] password;
/*      */   String passwordStr;
/*      */   byte[] terminal;
/*      */   byte[] machine;
/*      */   byte[] sysUserName;
/*      */   byte[] processID;
/*      */   byte[] programName;
/*      */   long flag;
/*      */   byte[] encryptedSK;
/*      */   byte[] internalName;
/*      */   byte[] externalName;
/*      */   byte[] alterSession;
/*      */   byte[] aclValue;
/*      */   byte[] clientname;
/*      */   String ressourceManagerId;
/*      */   T4CConnection conn;
/*   87 */   boolean isSessionTZ = true;
/*      */ 
/*   95 */   Properties connectionValues = new Properties();
/*      */   static final int SERVER_VERSION_81 = 8100;
/*      */   static final int KPZ_LOGON = 1;
/*      */   static final int KPZ_CPW = 2;
/*      */   static final int KPZ_SRVAUTH = 4;
/*      */   static final int KPZ_ENCRYPTED_PASSWD = 256;
/*      */   static final int KPZ_LOGON_MIGRATE = 16;
/*      */   static final int KPZ_LOGON_SYSDBA = 32;
/*      */   static final int KPZ_LOGON_SYSOPER = 64;
/*      */   static final int KPZ_LOGON_PRELIMAUTH = 128;
/*      */   static final int KPZ_PASSWD_ENCRYPTED = 256;
/*      */   static final int KPZ_LOGON_DBCONC = 512;
/*      */   static final int KPZ_PROXY_AUTH = 1024;
/*      */   static final int KPZ_SESSION_CACHE = 2048;
/*      */   static final int KPZ_PASSWD_IS_VFR = 4096;
/*      */   static final String AUTH_TERMINAL = "AUTH_TERMINAL";
/*      */   static final String AUTH_PROGRAM_NM = "AUTH_PROGRAM_NM";
/*      */   static final String AUTH_MACHINE = "AUTH_MACHINE";
/*      */   static final String AUTH_PID = "AUTH_PID";
/*      */   static final String AUTH_SID = "AUTH_SID";
/*      */   static final String AUTH_SESSKEY = "AUTH_SESSKEY";
/*      */   static final String AUTH_VFR_DATA = "AUTH_VFR_DATA";
/*      */   static final String AUTH_PASSWORD = "AUTH_PASSWORD";
/*      */   static final String AUTH_INTERNALNAME = "AUTH_INTERNALNAME_";
/*      */   static final String AUTH_EXTERNALNAME = "AUTH_EXTERNALNAME_";
/*      */   static final String AUTH_ACL = "AUTH_ACL";
/*      */   static final String AUTH_ALTER_SESSION = "AUTH_ALTER_SESSION";
/*      */   static final String AUTH_INITIAL_CLIENT_ROLE = "INITIAL_CLIENT_ROLE";
/*      */   static final String AUTH_VERSION_SQL = "AUTH_VERSION_SQL";
/*      */   static final String AUTH_VERSION_NO = "AUTH_VERSION_NO";
/*      */   static final String AUTH_XACTION_TRAITS = "AUTH_XACTION_TRAITS";
/*      */   static final String AUTH_VERSION_STATUS = "AUTH_VERSION_STATUS";
/*      */   static final String AUTH_SERIAL_NUM = "AUTH_SERIAL_NUM";
/*      */   static final String AUTH_SESSION_ID = "AUTH_SESSION_ID";
/*      */   static final String AUTH_CLIENT_CERTIFICATE = "AUTH_CLIENT_CERTIFICATE";
/*      */   static final String AUTH_PROXY_CLIENT_NAME = "PROXY_CLIENT_NAME";
/*      */   static final String AUTH_CLIENT_DN = "AUTH_CLIENT_DISTINGUISHED_NAME";
/*      */   static final String AUTH_INSTANCENAME = "AUTH_INSTANCENAME";
/*      */   static final String AUTH_DBNAME = "AUTH_DBNAME";
/*      */   static final String AUTH_INSTANCE_NO = "AUTH_INSTANCE_NO";
/*      */   static final String AUTH_SC_SERVER_HOST = "AUTH_SC_SERVER_HOST";
/*      */   static final String AUTH_SC_INSTANCE_NAME = "AUTH_SC_INSTANCE_NAME";
/*      */   static final String AUTH_SC_INSTANCE_ID = "AUTH_SC_INSTANCE_ID";
/*      */   static final String AUTH_SC_INSTANCE_START_TIME = "AUTH_SC_INSTANCE_START_TIME";
/*      */   static final String AUTH_SC_DBUNIQUE_NAME = "AUTH_SC_DBUNIQUE_NAME";
/*      */   static final String AUTH_SC_SERVICE_NAME = "AUTH_SC_SERVICE_NAME";
/*      */   static final String AUTH_SC_SVC_FLAGS = "AUTH_SC_SVC_FLAGS";
/*      */   static final String AUTH_COPYRIGHT = "AUTH_COPYRIGHT";
/*      */   static final String COPYRIGHT_STR = "\"Oracle\nEverybody follows\nSpeedy bits exchange\nStars await to glow\"\nThe preceding key is copyrighted by Oracle Corporation.\nDuplication of this key is not allowed without permission\nfrom Oracle Corporation. Copyright 2003 Oracle Corporation.";
/* 1028 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*      */ 
/*      */   T4CTTIoauthenticate(T4CMAREngine paramT4CMAREngine, String paramString1, String paramString2, Properties paramProperties, long paramLong, String paramString3, T4CTTIoer paramT4CTTIoer, T4CConnection paramT4CConnection)
/*      */     throws SQLException
/*      */   {
/*  170 */     super(3, 0, 0);
/*      */ 
/*  174 */     this.oer = paramT4CTTIoer;
/*      */ 
/*  176 */     setMarshalingEngine(paramT4CMAREngine);
/*      */ 
/*  178 */     this.user = paramT4CMAREngine.conv.StringToCharBytes(paramString1);
/*  179 */     this.userStr = paramString1;
/*  180 */     this.passwordStr = paramString2;
/*  181 */     this.flag = paramLong;
/*  182 */     this.ressourceManagerId = paramString3;
/*  183 */     this.conn = paramT4CConnection;
/*      */ 
/*  185 */     setSessionFields(paramProperties);
/*      */ 
/*  187 */     this.isSessionTZ = (this.conn.ver.getVersionNumber() >= 8100);
/*      */   }
/*      */ 
/*      */   void marshalOsesskey()
/*      */     throws IOException, SQLException
/*      */   {
/*  198 */     this.funCode = 118;
/*      */ 
/*  200 */     marshalFunHeader();
/*  201 */     this.meg.marshalPTR();
/*  202 */     this.meg.marshalSB4(this.user.length);
/*  203 */     this.meg.marshalUB4(this.flag | 1L);
/*  204 */     this.meg.marshalPTR();
/*      */ 
/*  208 */     int i = 4;
/*      */ 
/*  210 */     if (this.programName != null) {
/*  211 */       i++;
/*      */     }
/*  213 */     this.meg.marshalUB4(i);
/*  214 */     this.meg.marshalPTR();
/*  215 */     this.meg.marshalPTR();
/*      */ 
/*  218 */     this.meg.marshalCHR(this.user);
/*      */ 
/*  220 */     byte[][] arrayOfByte1 = new byte[i][];
/*  221 */     byte[][] arrayOfByte2 = new byte[i][];
/*      */ 
/*  225 */     byte[] arrayOfByte = new byte[i];
/*  226 */     int j = 0;
/*      */ 
/*  229 */     arrayOfByte1[(j++)] = this.meg.conv.StringToCharBytes("AUTH_TERMINAL");
/*      */ 
/*  233 */     if (this.programName != null)
/*      */     {
/*  235 */       arrayOfByte1[j] = this.meg.conv.StringToCharBytes("AUTH_PROGRAM_NM");
/*      */ 
/*  237 */       arrayOfByte2[(j++)] = this.programName;
/*      */     }
/*      */ 
/*  241 */     arrayOfByte1[j] = this.meg.conv.StringToCharBytes("AUTH_MACHINE");
/*  242 */     arrayOfByte2[(j++)] = this.machine;
/*      */ 
/*  245 */     arrayOfByte1[j] = this.meg.conv.StringToCharBytes("AUTH_PID");
/*  246 */     arrayOfByte2[(j++)] = this.processID;
/*      */ 
/*  249 */     arrayOfByte1[j] = this.meg.conv.StringToCharBytes("AUTH_SID");
/*  250 */     arrayOfByte2[(j++)] = this.sysUserName;
/*      */ 
/*  252 */     this.meg.marshalKEYVAL(arrayOfByte1, arrayOfByte2, arrayOfByte, i);
/*      */   }
/*      */ 
/*      */   void receiveOsesskey()
/*      */     throws IOException, SQLException
/*      */   {
/*  264 */     Object localObject1 = (byte[][])null;
/*  265 */     Object localObject2 = (byte[][])null;
/*      */     while (true)
/*      */     {
/*      */       try
/*      */       {
/*  271 */         int i = this.meg.unmarshalSB1();
/*      */ 
/*  273 */         switch (i)
/*      */         {
/*      */         case 8:
/*  278 */           int j = this.meg.unmarshalUB2();
/*      */ 
/*  280 */           localObject1 = new byte[j][];
/*  281 */           localObject2 = new byte[j][];
/*      */ 
/*  283 */           this.meg.unmarshalKEYVAL(localObject1, localObject2, j);
/*      */ 
/*  285 */           break;
/*      */         case 4:
/*  288 */           this.oer.init();
/*  289 */           this.oer.unmarshal();
/*      */           try
/*      */           {
/*  293 */             this.oer.processError();
/*      */           }
/*      */           catch (SQLException localSQLException)
/*      */           {
/*  297 */             throw localSQLException;
/*      */           }
/*      */ 
/*  300 */           break;
/*      */         default:
/*  305 */           DatabaseError.throwSqlException(401);
/*      */         }
/*      */ 
/*  311 */         continue; } catch (BreakNetException localBreakNetException) {
/*      */       }
/*      */     }
/*  314 */     if ((localObject1 == null) || (localObject1.length < 1)) {
/*  315 */       DatabaseError.throwSqlException(438);
/*      */     }
/*      */ 
/*  321 */     this.encryptedSK = localObject2[0];
/*      */ 
/*  324 */     if ((this.encryptedSK == null) || (this.encryptedSK.length != 16))
/*  325 */       DatabaseError.throwSqlException(438);
/*      */   }
/*      */ 
/*      */   void marshalOauth()
/*      */     throws IOException, SQLException
/*      */   {
/*  337 */     this.funCode = 115;
/*      */ 
/*  346 */     if (this.encryptedSK.length > 16) {
/*  347 */       DatabaseError.throwSqlException(413);
/*      */     }
/*      */ 
/*  356 */     String str1 = this.userStr.trim();
/*  357 */     String str2 = this.passwordStr.trim();
/*      */ 
/*  359 */     this.passwordStr = null;
/*      */ 
/*  361 */     String str3 = str1;
/*  362 */     String str4 = str2;
/*      */ 
/*  364 */     if ((str1.startsWith("\"")) || (str1.endsWith("\""))) {
/*  365 */       str3 = removeQuotes(str1);
/*      */     }
/*  367 */     if ((str2.startsWith("\"")) || (str2.endsWith("\""))) {
/*  368 */       str4 = removeQuotes(str2);
/*      */     }
/*      */ 
/*  375 */     O3LoginClientHelper localO3LoginClientHelper = new O3LoginClientHelper(this.meg.conv.isServerCSMultiByte);
/*      */ 
/*  377 */     byte[] arrayOfByte3 = localO3LoginClientHelper.getSessionKey(str3, str4, this.encryptedSK);
/*      */ 
/*  384 */     byte[] arrayOfByte1 = this.meg.conv.StringToCharBytes(str4);
/*      */     int i;
/*  387 */     if (arrayOfByte1.length % 8 > 0)
/*  388 */       i = (byte)(8 - arrayOfByte1.length % 8);
/*      */     else {
/*  390 */       i = 0;
/*      */     }
/*  392 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + i];
/*      */ 
/*  394 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/*      */ 
/*  399 */     byte[] arrayOfByte4 = localO3LoginClientHelper.getEPasswd(arrayOfByte3, arrayOfByte2);
/*      */ 
/*  402 */     this.password = new byte[2 * arrayOfByte2.length + 1];
/*      */ 
/*  405 */     if (this.password.length < 2 * arrayOfByte4.length) {
/*  406 */       DatabaseError.throwSqlException(413);
/*      */     }
/*  408 */     RepConversion.bArray2Nibbles(arrayOfByte4, this.password);
/*      */ 
/*  411 */     this.password[(this.password.length - 1)] = RepConversion.nibbleToHex(i);
/*      */ 
/*  415 */     marshalFunHeader();
/*  416 */     this.meg.marshalPTR();
/*  417 */     this.meg.marshalSB4(this.user.length);
/*  418 */     this.meg.marshalUB4(this.flag | 1L | 0x100);
/*      */ 
/*  420 */     this.meg.marshalPTR();
/*      */ 
/*  422 */     int j = 0;
/*      */ 
/*  424 */     if (!this.ressourceManagerId.equals("0000")) {
/*  425 */       j = 1;
/*      */     }
/*      */ 
/*  431 */     int k = 6;
/*      */ 
/*  433 */     if (j != 0) {
/*  434 */       k += 2;
/*      */     }
/*  436 */     k++;
/*      */ 
/*  438 */     if (this.programName != null) {
/*  439 */       k++;
/*      */     }
/*  441 */     if (this.clientname != null) {
/*  442 */       k++;
/*      */     }
/*  444 */     this.meg.marshalUB4(k);
/*  445 */     this.meg.marshalPTR();
/*  446 */     this.meg.marshalPTR();
/*      */ 
/*  449 */     this.meg.marshalCHR(this.user);
/*      */ 
/*  451 */     byte[][] arrayOfByte5 = new byte[k][];
/*  452 */     byte[][] arrayOfByte6 = new byte[k][];
/*  453 */     byte[] arrayOfByte7 = new byte[k];
/*  454 */     int m = 0;
/*      */ 
/*  461 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_PASSWORD");
/*  462 */     arrayOfByte6[(m++)] = this.password;
/*      */ 
/*  465 */     arrayOfByte5[(m++)] = this.meg.conv.StringToCharBytes("AUTH_TERMINAL");
/*      */ 
/*  468 */     if (this.programName != null)
/*      */     {
/*  472 */       arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_PROGRAM_NM");
/*      */ 
/*  474 */       arrayOfByte6[(m++)] = this.programName;
/*      */     }
/*      */ 
/*  478 */     if (this.clientname != null)
/*      */     {
/*  480 */       arrayOfByte5[m] = this.meg.conv.StringToCharBytes("PROXY_CLIENT_NAME");
/*      */ 
/*  482 */       arrayOfByte6[(m++)] = this.clientname;
/*      */     }
/*      */ 
/*  486 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_MACHINE");
/*  487 */     arrayOfByte6[(m++)] = this.machine;
/*      */ 
/*  490 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_PID");
/*  491 */     arrayOfByte6[(m++)] = this.processID;
/*      */ 
/*  493 */     if (j != 0)
/*      */     {
/*  497 */       arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_INTERNALNAME_");
/*      */ 
/*  502 */       arrayOfByte5[m][(arrayOfByte5[m].length - 1)] = 0;
/*  503 */       arrayOfByte6[(m++)] = this.internalName;
/*      */ 
/*  506 */       arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_EXTERNALNAME_");
/*      */ 
/*  508 */       arrayOfByte5[m][(arrayOfByte5[m].length - 1)] = 0;
/*  509 */       arrayOfByte6[(m++)] = this.externalName;
/*      */     }
/*      */ 
/*  513 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_ACL");
/*  514 */     arrayOfByte6[(m++)] = this.aclValue;
/*      */ 
/*  517 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_ALTER_SESSION");
/*      */ 
/*  519 */     arrayOfByte6[m] = this.alterSession;
/*  520 */     arrayOfByte7[(m++)] = 1;
/*      */ 
/*  522 */     arrayOfByte5[m] = this.meg.conv.StringToCharBytes("AUTH_COPYRIGHT");
/*      */ 
/*  524 */     arrayOfByte6[(m++)] = this.meg.conv.StringToCharBytes("\"Oracle\nEverybody follows\nSpeedy bits exchange\nStars await to glow\"\nThe preceding key is copyrighted by Oracle Corporation.\nDuplication of this key is not allowed without permission\nfrom Oracle Corporation. Copyright 2003 Oracle Corporation.");
/*      */ 
/*  527 */     this.meg.marshalKEYVAL(arrayOfByte5, arrayOfByte6, arrayOfByte7, k);
/*      */   }
/*      */ 
/*      */   void marshalOauth(int paramInt1, Properties paramProperties, int paramInt2, int paramInt3)
/*      */     throws IOException, SQLException
/*      */   {
/*  539 */     this.funCode = 115;
/*      */ 
/*  541 */     int i = 0;
/*  542 */     int j = 0;
/*  543 */     int k = 0;
/*  544 */     int m = 0;
/*  545 */     byte[] arrayOfByte1 = null;
/*  546 */     byte[] arrayOfByte2 = null;
/*  547 */     Object localObject1 = (byte[][])null;
/*      */     Object localObject2;
/*  549 */     if (paramInt1 == 1)
/*      */     {
/*  551 */       localObject2 = paramProperties.getProperty("PROXY_USER_NAME");
/*      */ 
/*  553 */       this.user = this.meg.conv.StringToCharBytes((String)localObject2);
/*  554 */       i = 1;
/*      */     }
/*  556 */     else if (paramInt1 == 2)
/*      */     {
/*  558 */       localObject2 = paramProperties.getProperty("PROXY_DISTINGUISHED_NAME");
/*      */ 
/*  561 */       arrayOfByte1 = this.meg.conv.StringToCharBytes((String)localObject2);
/*  562 */       j = 1;
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  568 */         arrayOfByte2 = (byte[])paramProperties.get("PROXY_CERTIFICATE");
/*      */ 
/*  570 */         localObject2 = new StringBuffer();
/*      */ 
/*  574 */         for (int i2 = 0; i2 < arrayOfByte2.length; i2++)
/*      */         {
/*  576 */           String str = Integer.toHexString(0xFF & arrayOfByte2[i2]);
/*  577 */           int i1 = str.length();
/*      */ 
/*  579 */           if (i1 == 0) {
/*  580 */             ((StringBuffer)localObject2).append("00");
/*  581 */           } else if (i1 == 1)
/*      */           {
/*  583 */             ((StringBuffer)localObject2).append('0');
/*  584 */             ((StringBuffer)localObject2).append(str);
/*      */           }
/*      */           else {
/*  587 */             ((StringBuffer)localObject2).append(str);
/*      */           }
/*      */         }
/*  590 */         arrayOfByte2 = ((StringBuffer)localObject2).toString().getBytes();
/*      */       }
/*      */       catch (Exception localException1) {
/*      */       }
/*  594 */       k = 1;
/*      */     }
/*      */ 
/*  598 */     String[] arrayOfString = null;
/*      */     try
/*      */     {
/*  602 */       arrayOfString = (String[])paramProperties.get("PROXY_ROLES");
/*      */     }
/*      */     catch (Exception localException2) {
/*      */     }
/*  606 */     if (arrayOfString != null)
/*      */     {
/*  608 */       localObject1 = new byte[arrayOfString.length][];
/*      */ 
/*  610 */       for (n = 0; n < arrayOfString.length; n++) {
/*  611 */         localObject1[n] = this.meg.conv.StringToCharBytes(arrayOfString[n]);
/*      */       }
/*  613 */       m = 1;
/*      */     }
/*      */ 
/*  617 */     marshalFunHeader();
/*      */ 
/*  619 */     if (i != 0)
/*      */     {
/*  621 */       this.meg.marshalPTR();
/*  622 */       this.meg.marshalSB4(this.user.length);
/*      */     }
/*      */     else
/*      */     {
/*  626 */       this.meg.marshalNULLPTR();
/*  627 */       this.meg.marshalSB4(0);
/*      */     }
/*      */ 
/*  630 */     this.meg.marshalUB4(1025L);
/*  631 */     this.meg.marshalPTR();
/*      */ 
/*  637 */     int n = 7;
/*      */ 
/*  639 */     n++;
/*      */ 
/*  641 */     if ((j != 0) || (k != 0)) {
/*  642 */       n++;
/*      */     }
/*  644 */     if (m != 0) {
/*  645 */       n += localObject1.length;
/*      */     }
/*  647 */     if (this.programName != null) {
/*  648 */       n++;
/*      */     }
/*  650 */     this.meg.marshalUB4(n);
/*  651 */     this.meg.marshalPTR();
/*  652 */     this.meg.marshalPTR();
/*      */ 
/*  655 */     if (i != 0) {
/*  656 */       this.meg.marshalCHR(this.user);
/*      */     }
/*  658 */     byte[][] arrayOfByte3 = new byte[n][];
/*  659 */     byte[][] arrayOfByte4 = new byte[n][];
/*  660 */     byte[] arrayOfByte5 = new byte[n];
/*      */ 
/*  662 */     int i3 = 0;
/*      */ 
/*  665 */     if (m != 0)
/*      */     {
/*  667 */       for (int i4 = 0; i4 < localObject1.length; i4++)
/*      */       {
/*  669 */         arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("INITIAL_CLIENT_ROLE");
/*      */ 
/*  671 */         arrayOfByte4[i3] = localObject1[i4];
/*  672 */         i3++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  677 */     if (j != 0)
/*      */     {
/*  679 */       arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_CLIENT_DISTINGUISHED_NAME");
/*      */ 
/*  681 */       arrayOfByte4[(i3++)] = arrayOfByte1;
/*      */     }
/*  683 */     else if (k != 0)
/*      */     {
/*  685 */       arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_CLIENT_CERTIFICATE");
/*      */ 
/*  687 */       arrayOfByte4[(i3++)] = arrayOfByte2;
/*      */     }
/*      */ 
/*  690 */     arrayOfByte3[(i3++)] = this.meg.conv.StringToCharBytes("AUTH_TERMINAL");
/*      */ 
/*  693 */     if (this.programName != null)
/*      */     {
/*  695 */       arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_PROGRAM_NM");
/*      */ 
/*  697 */       arrayOfByte4[(i3++)] = this.programName;
/*      */     }
/*      */ 
/*  700 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_MACHINE");
/*  701 */     arrayOfByte4[(i3++)] = this.machine;
/*      */ 
/*  703 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_PID");
/*  704 */     arrayOfByte4[(i3++)] = this.processID;
/*      */ 
/*  706 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_ACL");
/*  707 */     arrayOfByte4[(i3++)] = this.aclValue;
/*      */ 
/*  709 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_ALTER_SESSION");
/*      */ 
/*  711 */     arrayOfByte4[i3] = this.alterSession;
/*  712 */     arrayOfByte5[(i3++)] = 1;
/*      */ 
/*  714 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_SESSION_ID");
/*      */ 
/*  716 */     arrayOfByte4[(i3++)] = this.meg.conv.StringToCharBytes(Integer.toString(paramInt2));
/*      */ 
/*  719 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_SERIAL_NUM");
/*      */ 
/*  721 */     arrayOfByte4[(i3++)] = this.meg.conv.StringToCharBytes(Integer.toString(paramInt3));
/*      */ 
/*  724 */     arrayOfByte3[i3] = this.meg.conv.StringToCharBytes("AUTH_COPYRIGHT");
/*      */ 
/*  726 */     arrayOfByte4[(i3++)] = this.meg.conv.StringToCharBytes("\"Oracle\nEverybody follows\nSpeedy bits exchange\nStars await to glow\"\nThe preceding key is copyrighted by Oracle Corporation.\nDuplication of this key is not allowed without permission\nfrom Oracle Corporation. Copyright 2003 Oracle Corporation.");
/*      */ 
/*  729 */     this.meg.marshalKEYVAL(arrayOfByte3, arrayOfByte4, arrayOfByte5, n);
/*      */   }
/*      */ 
/*      */   void receiveOauth()
/*      */     throws IOException, SQLException
/*      */   {
/*  743 */     Object localObject1 = (byte[][])null;
/*  744 */     Object localObject2 = (byte[][])null;
/*  745 */     int j = 0;
/*      */     while (true)
/*      */     {
/*      */       try
/*      */       {
/*  751 */         int i = this.meg.unmarshalSB1();
/*      */ 
/*  753 */         switch (i)
/*      */         {
/*      */         case 8:
/*  758 */           j = this.meg.unmarshalUB2();
/*  759 */           localObject1 = new byte[j][];
/*  760 */           localObject2 = new byte[j][];
/*      */ 
/*  762 */           this.meg.unmarshalKEYVAL(localObject1, localObject2, j);
/*      */ 
/*  764 */           break;
/*      */         case 15:
/*  770 */           this.oer.init();
/*  771 */           this.oer.unmarshalWarning();
/*      */           try
/*      */           {
/*  775 */             this.oer.processWarning();
/*      */           }
/*      */           catch (SQLWarning localSQLWarning)
/*      */           {
/*  779 */             this.conn.setWarnings(DatabaseError.addSqlWarning(this.conn.getWarnings(), localSQLWarning));
/*      */           }
/*      */ 
/*      */         case 4:
/*  785 */           this.oer.init();
/*  786 */           this.oer.unmarshal();
/*      */           try
/*      */           {
/*  790 */             this.oer.processError();
/*      */           }
/*      */           catch (SQLException localSQLException)
/*      */           {
/*  794 */             throw localSQLException;
/*      */           }
/*      */ 
/*  797 */           break;
/*      */         default:
/*  802 */           DatabaseError.throwSqlException(401);
/*      */         }
/*      */ 
/*  808 */         continue;
/*      */       }
/*      */       catch (BreakNetException localBreakNetException)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  816 */     for (int k = 0; k < j; k++)
/*      */     {
/*  818 */       String str1 = this.meg.conv.CharBytesToString(localObject1[k], localObject1[k].length);
/*  819 */       String str2 = "";
/*  820 */       if (localObject2[k] != null) {
/*  821 */         str2 = this.meg.conv.CharBytesToString(localObject2[k], localObject2[k].length);
/*      */       }
/*  823 */       this.connectionValues.setProperty(str1, str2);
/*      */     }
/*      */   }
/*      */ 
/*      */   int getSessionId()
/*      */   {
/*  832 */     int i = -1;
/*  833 */     String str = this.connectionValues.getProperty("AUTH_SESSION_ID");
/*      */     try
/*      */     {
/*  837 */       i = Integer.parseInt(str);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*      */     }
/*  840 */     return i;
/*      */   }
/*      */ 
/*      */   int getSerialNumber()
/*      */   {
/*  845 */     int i = -1;
/*  846 */     String str = this.connectionValues.getProperty("AUTH_SERIAL_NUM");
/*      */     try
/*      */     {
/*  850 */       i = Integer.parseInt(str);
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*      */     }
/*  853 */     return i;
/*      */   }
/*      */ 
/*      */   void setSessionFields(Properties paramProperties)
/*      */     throws SQLException
/*      */   {
/*  869 */     String str1 = paramProperties.getProperty("v$session.terminal");
/*  870 */     String str2 = paramProperties.getProperty("v$session.machine");
/*  871 */     String str3 = paramProperties.getProperty("v$session.osuser");
/*  872 */     String str4 = paramProperties.getProperty("v$session.program");
/*  873 */     String str5 = paramProperties.getProperty("v$session.process");
/*  874 */     String str6 = paramProperties.getProperty("v$session.iname");
/*  875 */     String str7 = paramProperties.getProperty("v$session.ename");
/*  876 */     String str8 = paramProperties.getProperty("PROXY_CLIENT_NAME");
/*      */ 
/*  878 */     if (str1 == null)
/*      */     {
/*  881 */       str1 = "unknown";
/*      */     }
/*  883 */     if (str2 == null)
/*      */     {
/*      */       try
/*      */       {
/*  889 */         str2 = InetAddress.getLocalHost().getHostName();
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  895 */         str2 = "jdbcclient";
/*      */       }
/*      */     }
/*      */ 
/*  899 */     if (str3 == null)
/*      */     {
/*  903 */       str3 = OracleDriver.getSystemPropertyUserName();
/*      */ 
/*  906 */       if (str3 == null) {
/*  907 */         str3 = "jdbcuser";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  921 */     if (str5 == null) {
/*  922 */       str5 = "1234";
/*      */     }
/*  924 */     if (str6 == null) {
/*  925 */       str6 = "jdbc_ttc_impl";
/*      */     }
/*      */ 
/*  928 */     if (str7 == null) {
/*  929 */       str7 = "jdbc_" + this.ressourceManagerId;
/*      */     }
/*      */ 
/*  936 */     this.terminal = this.meg.conv.StringToCharBytes(str1);
/*  937 */     this.machine = this.meg.conv.StringToCharBytes(str2);
/*  938 */     this.sysUserName = this.meg.conv.StringToCharBytes(str3);
/*      */ 
/*  940 */     if (str4 != null) {
/*  941 */       this.programName = this.meg.conv.StringToCharBytes(str4);
/*      */     }
/*  943 */     this.processID = this.meg.conv.StringToCharBytes(str5);
/*  944 */     this.internalName = this.meg.conv.StringToCharBytes(str6);
/*  945 */     this.externalName = this.meg.conv.StringToCharBytes(str7);
/*      */ 
/*  947 */     if (str8 != null)
/*      */     {
/*  949 */       this.clientname = this.meg.conv.StringToCharBytes(str8);
/*      */     }
/*      */ 
/*  952 */     TimeZone localTimeZone = TimeZone.getDefault();
/*  953 */     int i = localTimeZone.getRawOffset();
/*  954 */     int j = i / 3600000;
/*  955 */     int k = i / 60000 % 60;
/*      */ 
/*  957 */     if ((localTimeZone.useDaylightTime()) && (localTimeZone.inDaylightTime(new Date()))) {
/*  958 */       j++;
/*      */     }
/*  960 */     String str9 = (j < 0 ? "" + j : new StringBuffer().append("+").append(j).toString()) + (k < 10 ? ":0" + k : new StringBuffer().append(":").append(k).toString());
/*      */ 
/*  962 */     String str10 = CharacterSetMetaData.getNLSLanguage(Locale.getDefault());
/*  963 */     String str11 = CharacterSetMetaData.getNLSTerritory(Locale.getDefault());
/*  964 */     if (str10 == null) {
/*  965 */       DatabaseError.throwSqlException(176);
/*      */     }
/*  967 */     this.alterSession = this.meg.conv.StringToCharBytes("ALTER SESSION SET " + (this.isSessionTZ ? "TIME_ZONE='" + str9 + "'" : "") + " NLS_LANGUAGE='" + str10 + "' NLS_TERRITORY='" + str11 + "' ");
/*      */ 
/*  971 */     this.aclValue = this.meg.conv.StringToCharBytes("4400");
/*  972 */     this.alterSession[(this.alterSession.length - 1)] = 0;
/*      */   }
/*      */ 
/*      */   String removeQuotes(String paramString)
/*      */   {
/*  996 */     int i = 0; int j = paramString.length() - 1;
/*      */ 
/*  998 */     for (int k = 0; k < paramString.length(); k++)
/*      */     {
/* 1000 */       if (paramString.charAt(k) == '"')
/*      */         continue;
/* 1002 */       i = k;
/*      */ 
/* 1004 */       break;
/*      */     }
/*      */ 
/* 1008 */     for (k = paramString.length() - 1; k >= 0; k--)
/*      */     {
/* 1010 */       if (paramString.charAt(k) == '"')
/*      */         continue;
/* 1012 */       j = k;
/*      */ 
/* 1014 */       break;
/*      */     }
/*      */ 
/* 1018 */     String str = paramString.substring(i, j + 1);
/*      */ 
/* 1023 */     return str;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIoauthenticate
 * JD-Core Version:    0.6.0
 */