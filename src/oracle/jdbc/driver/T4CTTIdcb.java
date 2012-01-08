/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ 
/*     */ class T4CTTIdcb extends T4CTTIMsg
/*     */ {
/*     */   T4C8TTIuds[] uds;
/*     */   int numuds;
/*     */   String[] colnames;
/*     */   int colOffset;
/*     */   byte[] ignoreBuff;
/*  79 */   StringBuffer colNameSB = null;
/*     */ 
/*  81 */   OracleStatement statement = null;
/*     */ 
/* 981 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";
/*     */ 
/*     */   T4CTTIdcb(T4CMAREngine paramT4CMAREngine)
/*     */     throws IOException, SQLException
/*     */   {
/*  86 */     super(16);
/*     */ 
/*  88 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/*  90 */     this.ignoreBuff = new byte[40];
/*     */   }
/*     */ 
/*     */   void init(OracleStatement paramOracleStatement, int paramInt)
/*     */   {
/*  95 */     this.statement = paramOracleStatement;
/*  96 */     this.colOffset = paramInt;
/*     */   }
/*     */ 
/*     */   Accessor[] receive(Accessor[] paramArrayOfAccessor)
/*     */     throws SQLException, IOException
/*     */   {
/* 105 */     int i = this.meg.unmarshalUB1();
/*     */ 
/* 107 */     if (this.ignoreBuff.length < i) {
/* 108 */       this.ignoreBuff = new byte[i];
/*     */     }
/* 110 */     this.meg.unmarshalNBytes(this.ignoreBuff, 0, i);
/*     */ 
/* 112 */     int j = (int)this.meg.unmarshalUB4();
/*     */ 
/* 114 */     paramArrayOfAccessor = receiveCommon(paramArrayOfAccessor, false);
/*     */ 
/* 116 */     return paramArrayOfAccessor;
/*     */   }
/*     */ 
/*     */   Accessor[] receiveFromRefCursor(Accessor[] paramArrayOfAccessor)
/*     */     throws SQLException, IOException
/*     */   {
/* 122 */     int i = this.meg.unmarshalUB1();
/* 123 */     int j = (int)this.meg.unmarshalUB4();
/*     */ 
/* 125 */     paramArrayOfAccessor = receiveCommon(paramArrayOfAccessor, false);
/*     */ 
/* 127 */     return paramArrayOfAccessor;
/*     */   }
/*     */ 
/*     */   Accessor[] receiveCommon(Accessor[] paramArrayOfAccessor, boolean paramBoolean)
/*     */     throws SQLException, IOException
/*     */   {
/* 134 */     if (paramBoolean) {
/* 135 */       this.numuds = this.meg.unmarshalUB2();
/*     */     }
/*     */     else {
/* 138 */       this.numuds = (int)this.meg.unmarshalUB4();
/* 139 */       if (this.numuds > 0)
/*     */       {
/* 143 */         i = this.meg.unmarshalUB1();
/*     */       }
/*     */     }
/*     */ 
/* 147 */     this.uds = new T4C8TTIuds[this.numuds];
/* 148 */     this.colnames = new String[this.numuds];
/*     */     int j;
/* 150 */     for (int i = 0; i < this.numuds; i++)
/*     */     {
/* 152 */       this.uds[i] = new T4C8TTIuds(this.meg);
/*     */ 
/* 154 */       this.uds[i].unmarshal();
/*     */ 
/* 157 */       if (this.meg.versionNumber >= 10000)
/*     */       {
/* 159 */         j = this.meg.unmarshalUB2();
/*     */       }
/*     */ 
/* 162 */       this.colnames[i] = this.meg.conv.CharBytesToString(this.uds[i].getColumName(), this.uds[i].getColumNameByteLength());
/*     */     }
/*     */ 
/* 166 */     if (!paramBoolean)
/*     */     {
/* 170 */       this.meg.unmarshalDALC();
/*     */ 
/* 173 */       if (this.meg.versionNumber >= 10000)
/*     */       {
/* 175 */         i = (int)this.meg.unmarshalUB4();
/* 176 */         j = (int)this.meg.unmarshalUB4();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 198 */     if (this.statement.needToPrepareDefineBuffer)
/*     */     {
/* 203 */       if ((paramArrayOfAccessor == null) || (paramArrayOfAccessor.length != this.numuds + this.colOffset))
/*     */       {
/* 205 */         Accessor[] arrayOfAccessor = new Accessor[this.numuds + this.colOffset];
/*     */ 
/* 207 */         if ((paramArrayOfAccessor != null) && (paramArrayOfAccessor.length == this.colOffset))
/*     */         {
/* 209 */           System.arraycopy(paramArrayOfAccessor, 0, arrayOfAccessor, 0, this.colOffset);
/*     */         }
/*     */ 
/* 212 */         paramArrayOfAccessor = arrayOfAccessor;
/*     */       }
/*     */ 
/* 215 */       fillupAccessors(paramArrayOfAccessor, this.colOffset);
/*     */ 
/* 219 */       if (!paramBoolean)
/*     */       {
/* 221 */         this.statement.describedWithNames = true;
/* 222 */         this.statement.described = true;
/* 223 */         this.statement.numberOfDefinePositions = this.numuds;
/* 224 */         this.statement.accessors = paramArrayOfAccessor;
/*     */ 
/* 228 */         if (this.statement.connection.useFetchSizeWithLongColumn)
/*     */         {
/* 233 */           this.statement.prepareAccessors();
/*     */ 
/* 236 */           this.statement.allocateTmpByteArray();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 241 */     return paramArrayOfAccessor;
/*     */   }
/*     */ 
/*     */   void fillupAccessors(Accessor[] paramArrayOfAccessor, int paramInt)
/*     */     throws SQLException
/*     */   {
/* 251 */     int[] arrayOfInt1 = this.statement.definedColumnType;
/* 252 */     int[] arrayOfInt2 = this.statement.definedColumnSize;
/* 253 */     int[] arrayOfInt3 = this.statement.definedColumnFormOfUse;
/*     */ 
/* 260 */     for (int k = 0; k < this.numuds; k++)
/*     */     {
/* 262 */       int m = 0;
/* 263 */       int n = 0;
/* 264 */       int i1 = 0;
/*     */ 
/* 266 */       if ((arrayOfInt1 != null) && (arrayOfInt1.length > paramInt + k) && (arrayOfInt1[(paramInt + k)] != 0))
/*     */       {
/* 269 */         m = arrayOfInt1[(paramInt + k)];
/*     */       }
/* 271 */       if ((arrayOfInt2 != null) && (arrayOfInt2.length > paramInt + k) && (arrayOfInt2[(paramInt + k)] > 0))
/*     */       {
/* 274 */         n = arrayOfInt2[(paramInt + k)];
/*     */       }
/* 276 */       if ((arrayOfInt3 != null) && (arrayOfInt3.length > paramInt + k) && (arrayOfInt3[(paramInt + k)] > 0))
/*     */       {
/* 279 */         i1 = arrayOfInt3[(paramInt + k)];
/*     */       }
/* 281 */       T4C8TTIuds localT4C8TTIuds = this.uds[k];
/*     */ 
/* 283 */       String str1 = this.meg.conv.CharBytesToString(this.uds[k].getTypeName(), this.uds[k].getTypeCharLength());
/*     */ 
/* 287 */       String str2 = this.meg.conv.CharBytesToString(this.uds[k].getSchemaName(), this.uds[k].getSchemaCharLength());
/*     */ 
/* 290 */       String str3 = str2 + "." + str1;
/* 291 */       int i = localT4C8TTIuds.udsoac.oacmxl;
/*     */       int j;
/* 293 */       switch (localT4C8TTIuds.udsoac.oacdty)
/*     */       {
/*     */       case 96:
/* 307 */         if ((localT4C8TTIuds.udsoac.oacmxlc != 0) && (localT4C8TTIuds.udsoac.oacmxlc < i))
/*     */         {
/* 313 */           i = 2 * localT4C8TTIuds.udsoac.oacmxlc;
/*     */         }
/*     */ 
/* 318 */         j = i;
/*     */ 
/* 323 */         if (((m == 1) || (m == 12)) && (n > 0) && (n < i))
/*     */         {
/* 328 */           j = n;
/*     */         }
/* 330 */         paramArrayOfAccessor[(paramInt + k)] = new T4CCharAccessor(this.statement, j, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, i, m, n, this.meg);
/*     */ 
/* 345 */         if (((localT4C8TTIuds.udsoac.oacfl2 & 0x1000) != 4096) && (localT4C8TTIuds.udsoac.oacmxlc == 0))
/*     */           break;
/* 347 */         paramArrayOfAccessor[(this.colOffset + k)].setDisplaySize(localT4C8TTIuds.udsoac.oacmxlc); break;
/*     */       case 2:
/* 352 */         paramArrayOfAccessor[(paramInt + k)] = new T4CNumberAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 366 */         break;
/*     */       case 1:
/* 371 */         if ((localT4C8TTIuds.udsoac.oacmxlc != 0) && (localT4C8TTIuds.udsoac.oacmxlc < i)) {
/* 372 */           i = 2 * localT4C8TTIuds.udsoac.oacmxlc;
/*     */         }
/* 374 */         j = i;
/*     */ 
/* 376 */         if (((m == 1) || (m == 12)) && (n > 0) && (n < i))
/*     */         {
/* 380 */           j = n;
/*     */         }
/* 382 */         paramArrayOfAccessor[(paramInt + k)] = new T4CVarcharAccessor(this.statement, j, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, i, m, n, this.meg);
/*     */ 
/* 397 */         if (((localT4C8TTIuds.udsoac.oacfl2 & 0x1000) != 4096) && (localT4C8TTIuds.udsoac.oacmxlc == 0))
/*     */           break;
/* 399 */         paramArrayOfAccessor[(this.colOffset + k)].setDisplaySize(localT4C8TTIuds.udsoac.oacmxlc); break;
/*     */       case 8:
/* 404 */         if (((m == 1) || (m == 12)) && (this.meg.versionNumber >= 9000) && (n < 4001))
/*     */         {
/* 418 */           if (n > 0) {
/* 419 */             j = n;
/*     */           }
/*     */           else
/*     */           {
/* 423 */             j = 4000;
/*     */           }
/*     */ 
/* 429 */           i = -1;
/* 430 */           paramArrayOfAccessor[(paramInt + k)] = new T4CVarcharAccessor(this.statement, j, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, i, m, n, this.meg);
/*     */ 
/* 444 */           paramArrayOfAccessor[(paramInt + k)].describeType = 8;
/*     */         }
/*     */         else
/*     */         {
/* 451 */           i = 0;
/*     */ 
/* 453 */           paramArrayOfAccessor[(paramInt + k)] = new T4CLongAccessor(this.statement, paramInt + k + 1, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */         }
/*     */ 
/* 469 */         break;
/*     */       case 6:
/* 472 */         paramArrayOfAccessor[(paramInt + k)] = new T4CVarnumAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 486 */         break;
/*     */       case 100:
/* 489 */         paramArrayOfAccessor[(paramInt + k)] = new T4CBinaryFloatAccessor(this.statement, 4, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 508 */         break;
/*     */       case 101:
/* 511 */         paramArrayOfAccessor[(paramInt + k)] = new T4CBinaryDoubleAccessor(this.statement, 8, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 526 */         break;
/*     */       case 23:
/* 529 */         paramArrayOfAccessor[(paramInt + k)] = new T4CRawAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 543 */         break;
/*     */       case 24:
/* 547 */         if ((m == -2) && (n < 2001) && (this.meg.versionNumber >= 9000))
/*     */         {
/* 554 */           i = -1;
/* 555 */           paramArrayOfAccessor[(paramInt + k)] = new T4CRawAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 568 */           paramArrayOfAccessor[(paramInt + k)].describeType = 24;
/*     */         }
/*     */         else {
/* 571 */           paramArrayOfAccessor[(paramInt + k)] = new T4CLongRawAccessor(this.statement, paramInt + k + 1, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */         }
/*     */ 
/* 586 */         break;
/*     */       case 104:
/*     */       case 208:
/* 591 */         paramArrayOfAccessor[(paramInt + k)] = new T4CRowidAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 605 */         if (localT4C8TTIuds.udsoac.oacdty != 208) break;
/* 606 */         paramArrayOfAccessor[k].describeType = localT4C8TTIuds.udsoac.oacdty; break;
/*     */       case 102:
/* 611 */         paramArrayOfAccessor[(paramInt + k)] = new T4CResultSetAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 625 */         break;
/*     */       case 12:
/* 628 */         paramArrayOfAccessor[(paramInt + k)] = new T4CDateAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 642 */         break;
/*     */       case 113:
/* 645 */         if ((m == -4) && (this.meg.versionNumber >= 9000))
/*     */         {
/* 650 */           paramArrayOfAccessor[(paramInt + k)] = new T4CLongRawAccessor(this.statement, paramInt + k + 1, 2147483647, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 665 */           paramArrayOfAccessor[(paramInt + k)].describeType = 113;
/*     */         }
/* 667 */         else if ((m == -3) && (this.meg.versionNumber >= 9000))
/*     */         {
/* 672 */           paramArrayOfAccessor[(paramInt + k)] = new T4CRawAccessor(this.statement, 4000, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 685 */           paramArrayOfAccessor[(paramInt + k)].describeType = 113;
/*     */         }
/*     */         else
/*     */         {
/* 689 */           paramArrayOfAccessor[(paramInt + k)] = new T4CBlobAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */         }
/*     */ 
/* 703 */         break;
/*     */       case 112:
/* 706 */         short s = 1;
/* 707 */         if (i1 != 0) {
/* 708 */           s = (short)i1;
/*     */         }
/* 710 */         if ((m == -1) && (this.meg.versionNumber >= 9000))
/*     */         {
/* 715 */           i = 0;
/* 716 */           paramArrayOfAccessor[(paramInt + k)] = new T4CLongAccessor(this.statement, paramInt + k + 1, 2147483647, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, s, m, n, this.meg);
/*     */ 
/* 732 */           paramArrayOfAccessor[(paramInt + k)].describeType = 112;
/*     */         }
/* 734 */         else if (((m == 12) || (m == 1)) && (this.meg.versionNumber >= 9000))
/*     */         {
/* 740 */           j = 4000;
/* 741 */           if ((n > 0) && (n < j))
/*     */           {
/* 743 */             j = n;
/*     */           }
/* 745 */           paramArrayOfAccessor[(paramInt + k)] = new T4CVarcharAccessor(this.statement, j, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, s, 4000, m, n, this.meg);
/*     */ 
/* 760 */           paramArrayOfAccessor[(paramInt + k)].describeType = 112;
/*     */         }
/*     */         else {
/* 763 */           paramArrayOfAccessor[(paramInt + k)] = new T4CClobAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */         }
/*     */ 
/* 777 */         break;
/*     */       case 114:
/* 780 */         paramArrayOfAccessor[(paramInt + k)] = new T4CBfileAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 794 */         break;
/*     */       case 109:
/* 797 */         paramArrayOfAccessor[(paramInt + k)] = new T4CNamedTypeAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, str3, m, n, this.meg);
/*     */ 
/* 812 */         break;
/*     */       case 111:
/* 815 */         paramArrayOfAccessor[(paramInt + k)] = new T4CRefTypeAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, str3, m, n, this.meg);
/*     */ 
/* 830 */         break;
/*     */       case 180:
/* 833 */         paramArrayOfAccessor[(paramInt + k)] = new T4CTimestampAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 847 */         break;
/*     */       case 181:
/* 850 */         paramArrayOfAccessor[(paramInt + k)] = new T4CTimestamptzAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 864 */         break;
/*     */       case 231:
/* 867 */         paramArrayOfAccessor[(paramInt + k)] = new T4CTimestampltzAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 881 */         break;
/*     */       case 182:
/* 884 */         paramArrayOfAccessor[(paramInt + k)] = new T4CIntervalymAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 898 */         break;
/*     */       case 183:
/* 901 */         paramArrayOfAccessor[(paramInt + k)] = new T4CIntervaldsAccessor(this.statement, i, localT4C8TTIuds.udsnull, localT4C8TTIuds.udsoac.oacflg, localT4C8TTIuds.udsoac.oacpre, localT4C8TTIuds.udsoac.oacscl, localT4C8TTIuds.udsoac.oacfl2, localT4C8TTIuds.udsoac.oacmal, localT4C8TTIuds.udsoac.formOfUse, m, n, this.meg);
/*     */ 
/* 915 */         break;
/*     */       default:
/* 920 */         paramArrayOfAccessor[(paramInt + k)] = null;
/*     */       }
/*     */ 
/* 926 */       if (localT4C8TTIuds.udsoac.oactoid.length > 0)
/*     */       {
/* 928 */         paramArrayOfAccessor[(paramInt + k)].internalOtype = new OracleTypeADT(localT4C8TTIuds.udsoac.oactoid, localT4C8TTIuds.udsoac.oacvsn, localT4C8TTIuds.udsoac.ncs, localT4C8TTIuds.udsoac.formOfUse, str2 + "." + str1);
/*     */       }
/*     */       else
/*     */       {
/* 933 */         paramArrayOfAccessor[(paramInt + k)].internalOtype = null;
/*     */       }
/*     */ 
/* 936 */       paramArrayOfAccessor[(paramInt + k)].columnName = this.colnames[k];
/*     */ 
/* 940 */       if (this.uds[k].udsoac.oacmxl == 0) {
/* 941 */         paramArrayOfAccessor[k].isNullByDescribe = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 973 */     this.colNameSB = null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIdcb
 * JD-Core Version:    0.6.0
 */