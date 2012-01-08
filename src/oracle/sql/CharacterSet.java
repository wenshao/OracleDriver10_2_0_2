/*      */ package oracle.sql;
/*      */ 
/*      */ import java.sql.SQLException;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.sql.converter.CharacterConverterFactoryOGS;
/*      */ 
/*      */ public abstract class CharacterSet
/*      */ {
/*      */   public static final short DEFAULT_CHARSET = -1;
/*      */   public static final short ASCII_CHARSET = 1;
/*      */   public static final short ISO_LATIN_1_CHARSET = 31;
/*      */   public static final short UNICODE_1_CHARSET = 870;
/*      */   public static final short US7ASCII_CHARSET = 1;
/*      */   public static final short WE8DEC_CHARSET = 2;
/*      */   public static final short WE8HP_CHARSET = 3;
/*      */   public static final short US8PC437_CHARSET = 4;
/*      */   public static final short WE8EBCDIC37_CHARSET = 5;
/*      */   public static final short WE8EBCDIC500_CHARSET = 6;
/*      */   public static final short WE8EBCDIC285_CHARSET = 8;
/*      */   public static final short WE8PC850_CHARSET = 10;
/*      */   public static final short D7DEC_CHARSET = 11;
/*      */   public static final short F7DEC_CHARSET = 12;
/*      */   public static final short S7DEC_CHARSET = 13;
/*      */   public static final short E7DEC_CHARSET = 14;
/*      */   public static final short SF7ASCII_CHARSET = 15;
/*      */   public static final short NDK7DEC_CHARSET = 16;
/*      */   public static final short I7DEC_CHARSET = 17;
/*      */   public static final short NL7DEC_CHARSET = 18;
/*      */   public static final short CH7DEC_CHARSET = 19;
/*      */   public static final short YUG7ASCII_CHARSET = 20;
/*      */   public static final short SF7DEC_CHARSET = 21;
/*      */   public static final short TR7DEC_CHARSET = 22;
/*      */   public static final short IW7IS960_CHARSET = 23;
/*      */   public static final short IN8ISCII_CHARSET = 25;
/*      */   public static final short WE8ISO8859P1_CHARSET = 31;
/*      */   public static final short EE8ISO8859P2_CHARSET = 32;
/*      */   public static final short SE8ISO8859P3_CHARSET = 33;
/*      */   public static final short NEE8ISO8859P4_CHARSET = 34;
/*      */   public static final short CL8ISO8859P5_CHARSET = 35;
/*      */   public static final short AR8ISO8859P6_CHARSET = 36;
/*      */   public static final short EL8ISO8859P7_CHARSET = 37;
/*      */   public static final short IW8ISO8859P8_CHARSET = 38;
/*      */   public static final short WE8ISO8859P9_CHARSET = 39;
/*      */   public static final short NE8ISO8859P10_CHARSET = 40;
/*      */   public static final short TH8TISASCII_CHARSET = 41;
/*      */   public static final short TH8TISEBCDIC_CHARSET = 42;
/*      */   public static final short BN8BSCII_CHARSET = 43;
/*      */   public static final short VN8VN3_CHARSET = 44;
/*      */   public static final short VN8MSWIN1258_CHARSET = 45;
/*      */   public static final short WE8NEXTSTEP_CHARSET = 50;
/*      */   public static final short AR8ASMO708PLUS_CHARSET = 61;
/*      */   public static final short AR8EBCDICX_CHARSET = 70;
/*      */   public static final short AR8XBASIC_CHARSET = 72;
/*      */   public static final short EL8DEC_CHARSET = 81;
/*      */   public static final short TR8DEC_CHARSET = 82;
/*      */   public static final short WE8EBCDIC37C_CHARSET = 90;
/*      */   public static final short WE8EBCDIC500C_CHARSET = 91;
/*      */   public static final short IW8EBCDIC424_CHARSET = 92;
/*      */   public static final short TR8EBCDIC1026_CHARSET = 93;
/*      */   public static final short WE8EBCDIC871_CHARSET = 94;
/*      */   public static final short WE8EBCDIC284_CHARSET = 95;
/*      */   public static final short WE8EBCDIC1047_CHARSET = 96;
/*      */   public static final short EEC8EUROASCI_CHARSET = 110;
/*      */   public static final short EEC8EUROPA3_CHARSET = 113;
/*      */   public static final short LA8PASSPORT_CHARSET = 114;
/*      */   public static final short BG8PC437S_CHARSET = 140;
/*      */   public static final short EE8PC852_CHARSET = 150;
/*      */   public static final short RU8PC866_CHARSET = 152;
/*      */   public static final short RU8BESTA_CHARSET = 153;
/*      */   public static final short IW8PC1507_CHARSET = 154;
/*      */   public static final short RU8PC855_CHARSET = 155;
/*      */   public static final short TR8PC857_CHARSET = 156;
/*      */   public static final short CL8MACCYRILLIC_CHARSET = 158;
/*      */   public static final short CL8MACCYRILLICS_CHARSET = 159;
/*      */   public static final short WE8PC860_CHARSET = 160;
/*      */   public static final short IS8PC861_CHARSET = 161;
/*      */   public static final short EE8MACCES_CHARSET = 162;
/*      */   public static final short EE8MACCROATIANS_CHARSET = 163;
/*      */   public static final short TR8MACTURKISHS_CHARSET = 164;
/*      */   public static final short IS8MACICELANDICS_CHARSET = 165;
/*      */   public static final short EL8MACGREEKS_CHARSET = 166;
/*      */   public static final short IW8MACHEBREWS_CHARSET = 167;
/*      */   public static final short EE8MSWIN1250_CHARSET = 170;
/*      */   public static final short CL8MSWIN1251_CHARSET = 171;
/*      */   public static final short ET8MSWIN923_CHARSET = 172;
/*      */   public static final short BG8MSWIN_CHARSET = 173;
/*      */   public static final short EL8MSWIN1253_CHARSET = 174;
/*      */   public static final short IW8MSWIN1255_CHARSET = 175;
/*      */   public static final short LT8MSWIN921_CHARSET = 176;
/*      */   public static final short TR8MSWIN1254_CHARSET = 177;
/*      */   public static final short WE8MSWIN1252_CHARSET = 178;
/*      */   public static final short BLT8MSWIN1257_CHARSET = 179;
/*      */   public static final short D8EBCDIC273_CHARSET = 180;
/*      */   public static final short I8EBCDIC280_CHARSET = 181;
/*      */   public static final short DK8EBCDIC277_CHARSET = 182;
/*      */   public static final short S8EBCDIC278_CHARSET = 183;
/*      */   public static final short EE8EBCDIC870_CHARSET = 184;
/*      */   public static final short CL8EBCDIC1025_CHARSET = 185;
/*      */   public static final short F8EBCDIC297_CHARSET = 186;
/*      */   public static final short IW8EBCDIC1086_CHARSET = 187;
/*      */   public static final short CL8EBCDIC1025X_CHARSET = 188;
/*      */   public static final short N8PC865_CHARSET = 190;
/*      */   public static final short BLT8CP921_CHARSET = 191;
/*      */   public static final short LV8PC1117_CHARSET = 192;
/*      */   public static final short LV8PC8LR_CHARSET = 193;
/*      */   public static final short BLT8EBCDIC1112_CHARSET = 194;
/*      */   public static final short LV8RST104090_CHARSET = 195;
/*      */   public static final short CL8KOI8R_CHARSET = 196;
/*      */   public static final short BLT8PC775_CHARSET = 197;
/*      */   public static final short F7SIEMENS9780X_CHARSET = 201;
/*      */   public static final short E7SIEMENS9780X_CHARSET = 202;
/*      */   public static final short S7SIEMENS9780X_CHARSET = 203;
/*      */   public static final short DK7SIEMENS9780X_CHARSET = 204;
/*      */   public static final short N7SIEMENS9780X_CHARSET = 205;
/*      */   public static final short I7SIEMENS9780X_CHARSET = 206;
/*      */   public static final short D7SIEMENS9780X_CHARSET = 207;
/*      */   public static final short WE8GCOS7_CHARSET = 210;
/*      */   public static final short EL8GCOS7_CHARSET = 211;
/*      */   public static final short US8BS2000_CHARSET = 221;
/*      */   public static final short D8BS2000_CHARSET = 222;
/*      */   public static final short F8BS2000_CHARSET = 223;
/*      */   public static final short E8BS2000_CHARSET = 224;
/*      */   public static final short DK8BS2000_CHARSET = 225;
/*      */   public static final short S8BS2000_CHARSET = 226;
/*      */   public static final short WE8BS2000_CHARSET = 231;
/*      */   public static final short CL8BS2000_CHARSET = 235;
/*      */   public static final short WE8BS2000L5_CHARSET = 239;
/*      */   public static final short WE8DG_CHARSET = 241;
/*      */   public static final short WE8NCR4970_CHARSET = 251;
/*      */   public static final short WE8ROMAN8_CHARSET = 261;
/*      */   public static final short EE8MACCE_CHARSET = 262;
/*      */   public static final short EE8MACCROATIAN_CHARSET = 263;
/*      */   public static final short TR8MACTURKISH_CHARSET = 264;
/*      */   public static final short IS8MACICELANDIC_CHARSET = 265;
/*      */   public static final short EL8MACGREEK_CHARSET = 266;
/*      */   public static final short IW8MACHEBREW_CHARSET = 267;
/*      */   public static final short US8ICL_CHARSET = 277;
/*      */   public static final short WE8ICL_CHARSET = 278;
/*      */   public static final short WE8ISOICLUK_CHARSET = 279;
/*      */   public static final short WE8MACROMAN8_CHARSET = 351;
/*      */   public static final short WE8MACROMAN8S_CHARSET = 352;
/*      */   public static final short TH8MACTHAI_CHARSET = 353;
/*      */   public static final short TH8MACTHAIS_CHARSET = 354;
/*      */   public static final short HU8CWI2_CHARSET = 368;
/*      */   public static final short EL8PC437S_CHARSET = 380;
/*      */   public static final short EL8EBCDIC875_CHARSET = 381;
/*      */   public static final short EL8PC737_CHARSET = 382;
/*      */   public static final short LT8PC772_CHARSET = 383;
/*      */   public static final short LT8PC774_CHARSET = 384;
/*      */   public static final short EL8PC869_CHARSET = 385;
/*      */   public static final short EL8PC851_CHARSET = 386;
/*      */   public static final short CDN8PC863_CHARSET = 390;
/*      */   public static final short HU8ABMOD_CHARSET = 401;
/*      */   public static final short AR8ASMO8X_CHARSET = 500;
/*      */   public static final short AR8NAFITHA711T_CHARSET = 504;
/*      */   public static final short AR8SAKHR707T_CHARSET = 505;
/*      */   public static final short AR8MUSSAD768T_CHARSET = 506;
/*      */   public static final short AR8ADOS710T_CHARSET = 507;
/*      */   public static final short AR8ADOS720T_CHARSET = 508;
/*      */   public static final short AR8APTEC715T_CHARSET = 509;
/*      */   public static final short AR8NAFITHA721T_CHARSET = 511;
/*      */   public static final short AR8HPARABIC8T_CHARSET = 514;
/*      */   public static final short AR8NAFITHA711_CHARSET = 554;
/*      */   public static final short AR8SAKHR707_CHARSET = 555;
/*      */   public static final short AR8MUSSAD768_CHARSET = 556;
/*      */   public static final short AR8ADOS710_CHARSET = 557;
/*      */   public static final short AR8ADOS720_CHARSET = 558;
/*      */   public static final short AR8APTEC715_CHARSET = 559;
/*      */   public static final short AR8MSAWIN_CHARSET = 560;
/*      */   public static final short AR8NAFITHA721_CHARSET = 561;
/*      */   public static final short AR8SAKHR706_CHARSET = 563;
/*      */   public static final short AR8ARABICMAC_CHARSET = 565;
/*      */   public static final short AR8ARABICMACS_CHARSET = 566;
/*      */   public static final short AR8ARABICMACT_CHARSET = 567;
/*      */   public static final short LA8ISO6937_CHARSET = 590;
/*      */   public static final short US8NOOP_CHARSET = 797;
/*      */   public static final short WE8DECTST_CHARSET = 798;
/*      */   public static final short JA16VMS_CHARSET = 829;
/*      */   public static final short JA16EUC_CHARSET = 830;
/*      */   public static final short JA16EUCYEN_CHARSET = 831;
/*      */   public static final short JA16SJIS_CHARSET = 832;
/*      */   public static final short JA16DBCS_CHARSET = 833;
/*      */   public static final short JA16SJISYEN_CHARSET = 834;
/*      */   public static final short JA16EBCDIC930_CHARSET = 835;
/*      */   public static final short JA16MACSJIS_CHARSET = 836;
/*      */   public static final short JA16EUCTILDE_CHARSET = 837;
/*      */   public static final short JA16SJISTILDE_CHARSET = 838;
/*      */   public static final short KO16KSC5601_CHARSET = 840;
/*      */   public static final short KO16DBCS_CHARSET = 842;
/*      */   public static final short KO16KSCCS_CHARSET = 845;
/*      */   public static final short KO16MSWIN949_CHARSET = 846;
/*      */   public static final short ZHS16CGB231280_CHARSET = 850;
/*      */   public static final short ZHS16MACCGB231280_CHARSET = 851;
/*      */   public static final short ZHS16GBK_CHARSET = 852;
/*      */   public static final short ZHS16DBCS_CHARSET = 853;
/*      */   public static final short ZHS32GB18030 = 854;
/*      */   public static final short ZHS16MSWIN936_CHARSET = 854;
/*      */   public static final short ZHT32EUC_CHARSET = 860;
/*      */   public static final short ZHT32SOPS_CHARSET = 861;
/*      */   public static final short ZHT16DBT_CHARSET = 862;
/*      */   public static final short ZHT32TRIS_CHARSET = 863;
/*      */   public static final short ZHT16DBCS_CHARSET = 864;
/*      */   public static final short ZHT16BIG5_CHARSET = 865;
/*      */   public static final short ZHT16CCDC_CHARSET = 866;
/*      */   public static final short ZHT16MSWIN950_CHARSET = 867;
/*      */   public static final short AL24UTFFSS_CHARSET = 870;
/*      */   public static final short UTF8_CHARSET = 871;
/*      */   public static final short UTFE_CHARSET = 872;
/*      */   public static final short AL32UTF8_CHARSET = 873;
/*      */   public static final short KO16TSTSET_CHARSET = 996;
/*      */   public static final short JA16TSTSET2_CHARSET = 997;
/*      */   public static final short JA16TSTSET_CHARSET = 998;
/*      */   public static final short US16TSTFIXED_CHARSET = 1001;
/*      */   public static final short JA16EUCFIXED_CHARSET = 1830;
/*      */   public static final short JA16SJISFIXED_CHARSET = 1832;
/*      */   public static final short JA16DBCSFIXED_CHARSET = 1833;
/*      */   public static final short KO16KSC5601FIXED_CHARSET = 1840;
/*      */   public static final short KO16DBCSFIXED_CHARSET = 1842;
/*      */   public static final short ZHS16CGB231280FIXED_CHARSET = 1850;
/*      */   public static final short ZHS16GBKFIXED_CHARSET = 1852;
/*      */   public static final short ZHS16DBCSFIXED_CHARSET = 1853;
/*      */   public static final short ZHT32EUCFIXED_CHARSET = 1860;
/*      */   public static final short ZHT32TRISFIXED_CHARSET = 1863;
/*      */   public static final short ZHT16DBCSFIXED_CHARSET = 1864;
/*      */   public static final short ZHT16BIG5FIXED_CHARSET = 1865;
/*      */   public static final short AL16UTF16_CHARSET = 2000;
/*      */   public static final short AL16UTF16LE_CHARSET = 2002;
/*      */   public static final short UNICODE_2_CHARSET = 871;
/*      */   static CharacterSetFactory factory;
/*      */   private int oracleId;
/*      */   int rep;
/*      */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*      */ 
/*      */   CharacterSet(int paramInt)
/*      */   {
/*  423 */     this.oracleId = paramInt;
/*      */   }
/*      */ 
/*      */   public static CharacterSet make(int paramInt)
/*      */   {
/*  440 */     return factory.make(paramInt);
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  452 */     return "oracle-character-set-" + this.oracleId;
/*      */   }
/*      */ 
/*      */   public abstract boolean isLossyFrom(CharacterSet paramCharacterSet);
/*      */ 
/*      */   public abstract boolean isConvertibleFrom(CharacterSet paramCharacterSet);
/*      */ 
/*      */   public boolean isUnicode()
/*      */   {
/*  488 */     return false;
/*      */   }
/*      */ 
/*      */   boolean isWellFormed(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  506 */     return true;
/*      */   }
/*      */ 
/*      */   public int getOracleId()
/*      */   {
/*  517 */     return this.oracleId;
/*      */   }
/*      */ 
/*      */   int getRep()
/*      */   {
/*  523 */     return this.rep;
/*      */   }
/*      */ 
/*      */   public int getRatioTo(CharacterSet paramCharacterSet)
/*      */   {
/*  539 */     throw new Error("oracle.sql.CharacterSet.getRationTo Not Implemented");
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  553 */     return ((paramObject instanceof CharacterSet)) && (this.oracleId == ((CharacterSet)paramObject).oracleId);
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  565 */     return this.oracleId;
/*      */   }
/*      */ 
/*      */   public abstract String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*      */ 
/*      */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  603 */     String str = toStringWithReplacement(paramArrayOfByte, paramInt1, paramInt2);
/*  604 */     byte[] arrayOfByte = convert(str);
/*      */ 
/*  606 */     if (paramInt2 != arrayOfByte.length)
/*      */     {
/*  608 */       failCharacterConversion(this);
/*      */     }
/*      */ 
/*  611 */     for (int i = 0; i < paramInt2; i++)
/*      */     {
/*  614 */       if (arrayOfByte[i] == paramArrayOfByte[(paramInt1 + i)])
/*      */         continue;
/*  616 */       failCharacterConversion(this);
/*      */     }
/*      */ 
/*  620 */     return null;
/*      */   }
/*      */ 
/*      */   public abstract byte[] convert(String paramString)
/*      */     throws SQLException;
/*      */ 
/*      */   public abstract byte[] convertWithReplacement(String paramString);
/*      */ 
/*      */   public abstract byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException;
/*      */ 
/*      */   public byte[] convertUnshared(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  688 */     byte[] arrayOfByte = convert(paramCharacterSet, paramArrayOfByte, paramInt1, paramInt2);
/*      */ 
/*  690 */     if (arrayOfByte == paramArrayOfByte)
/*      */     {
/*  692 */       arrayOfByte = new byte[paramArrayOfByte.length];
/*      */ 
/*  694 */       System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt2);
/*      */     }
/*      */ 
/*  697 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   abstract int decode(CharacterWalker paramCharacterWalker)
/*      */     throws SQLException;
/*      */ 
/*      */   abstract void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
/*      */     throws SQLException;
/*      */ 
/*      */   static final void failCharacterConversion(CharacterSet paramCharacterSet)
/*      */     throws SQLException
/*      */   {
/*  728 */     DatabaseError.throwSqlException(55, paramCharacterSet);
/*      */   }
/*      */ 
/*      */   static final byte[] useOrCopy(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*      */     byte[] arrayOfByte;
/*  736 */     if ((paramArrayOfByte.length == paramInt2) && (paramInt1 == 0))
/*      */     {
/*  738 */       arrayOfByte = paramArrayOfByte;
/*      */     }
/*      */     else
/*      */     {
/*  742 */       arrayOfByte = new byte[paramInt2];
/*      */ 
/*  744 */       System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/*      */     }
/*      */ 
/*  747 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   static final void need(CharacterBuffer paramCharacterBuffer, int paramInt)
/*      */   {
/*  760 */     int i = paramCharacterBuffer.bytes.length;
/*  761 */     int j = paramInt + paramCharacterBuffer.next;
/*      */ 
/*  763 */     if (j <= i)
/*      */     {
/*  766 */       return;
/*      */     }
/*      */ 
/*  769 */     while (j > i)
/*      */     {
/*  771 */       i = 2 * i;
/*      */     }
/*      */ 
/*  774 */     byte[] arrayOfByte = paramCharacterBuffer.bytes;
/*      */ 
/*  776 */     paramCharacterBuffer.bytes = new byte[i];
/*      */ 
/*  778 */     System.arraycopy(arrayOfByte, 0, paramCharacterBuffer.bytes, 0, paramCharacterBuffer.next);
/*      */   }
/*      */ 
/*      */   public static final String UTFToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  805 */     return new String(UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2, paramBoolean));
/*      */   }
/*      */ 
/*      */   public static final String UTFToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  821 */     return UTFToString(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */ 
/*      */   public static final char[] UTFToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/*  842 */     return UTFToJavaChar(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */ 
/*      */   public static final char[] UTFToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  874 */     char[] arrayOfChar1 = null;
/*      */ 
/*  876 */     arrayOfChar1 = new char[paramInt2];
/*      */ 
/*  880 */     int[] arrayOfInt = new int[1];
/*      */ 
/*  882 */     arrayOfInt[0] = paramInt2;
/*      */ 
/*  884 */     int i = convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, paramBoolean);
/*      */ 
/*  886 */     char[] arrayOfChar2 = new char[i];
/*      */ 
/*  888 */     System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
/*      */ 
/*  890 */     arrayOfChar1 = null;
/*      */ 
/*  892 */     return arrayOfChar2;
/*      */   }
/*      */ 
/*      */   public static final char[] UTFToJavaCharWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  921 */     char[] arrayOfChar1 = null;
/*      */     try
/*      */     {
/*  925 */       arrayOfChar1 = new char[paramInt2];
/*      */ 
/*  929 */       int[] arrayOfInt = new int[1];
/*      */ 
/*  931 */       arrayOfInt[0] = paramInt2;
/*      */ 
/*  933 */       int i = convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, true);
/*      */ 
/*  935 */       char[] arrayOfChar2 = new char[i];
/*      */ 
/*  937 */       System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
/*      */ 
/*  939 */       arrayOfChar1 = null;
/*      */ 
/*  941 */       return arrayOfChar2;
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/*      */ 
/*  947 */     throw new IllegalStateException(localSQLException.getMessage());
/*      */   }
/*      */ 
/*      */   public static final int convertUTFBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  987 */     return convertUTFBytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, paramBoolean, paramArrayOfChar.length - paramInt2);
/*      */   }
/*      */ 
/*      */   public static final int convertUTFBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 1010 */     CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
/*      */ 
/* 1013 */     int i = paramArrayOfInt[0];
/*      */ 
/* 1015 */     paramArrayOfInt[0] = 0;
/*      */ 
/* 1017 */     int j = paramInt1;
/* 1018 */     int k = paramInt1 + i;
/* 1019 */     int m = paramInt2;
/* 1020 */     int n = paramInt2 + paramInt3;
/*      */ 
/* 1026 */     while (j < k)
/*      */     {
/* 1028 */       int i1 = paramArrayOfByte[(j++)];
/* 1029 */       i3 = i1 & 0xF0;
/*      */       char c1;
/* 1031 */       switch (i3 / 16)
/*      */       {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/* 1051 */         if (m < n) {
/* 1052 */           paramArrayOfChar[(m++)] = (char)(i1 & 0xFFFFFFFF); continue;
/*      */         }
/*      */ 
/* 1057 */         paramArrayOfInt[0] = (k - j + 2);
/*      */ 
/* 1059 */         break;
/*      */       case 12:
/*      */       case 13:
/* 1069 */         if (j >= k)
/*      */         {
/* 1071 */           paramArrayOfInt[0] = 1;
/*      */ 
/* 1073 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1075 */           break label592;
/*      */         }
/*      */ 
/* 1079 */         c1 = conv2ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)]);
/*      */ 
/* 1081 */         if (m < n) {
/* 1082 */           paramArrayOfChar[(m++)] = c1;
/*      */         }
/*      */         else
/*      */         {
/* 1087 */           paramArrayOfInt[0] = (k - j + 3);
/*      */ 
/* 1089 */           break label592;
/*      */         }
/*      */ 
/* 1092 */         localCharacterConverterBehavior.onFailConversion(c1);
/*      */ 
/* 1096 */         break;
/*      */       case 14:
/* 1101 */         if (j + 1 >= k)
/*      */         {
/* 1103 */           paramArrayOfInt[0] = (k - j + 1);
/*      */ 
/* 1105 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1107 */           break label592;
/*      */         }
/*      */ 
/* 1110 */         char c2 = conv3ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
/*      */ 
/* 1115 */         if ((i3 != 244) && (paramArrayOfByte[(j - 2)] != -65) && (paramArrayOfByte[(j - 1)] != -67))
/*      */         {
/* 1118 */           localCharacterConverterBehavior.onFailConversion(c2);
/*      */         }
/*      */ 
/* 1121 */         if (isHiSurrogate(c2))
/*      */         {
/* 1123 */           if (m > n - 2)
/*      */           {
/* 1127 */             paramArrayOfInt[0] = (k - j + 4);
/*      */ 
/* 1129 */             break label592;
/*      */           }
/*      */ 
/* 1132 */           if (j >= k)
/*      */             continue;
/* 1134 */           int i2 = paramArrayOfByte[j];
/*      */ 
/* 1136 */           if ((byte)(i2 & 0xF0) != -32)
/*      */           {
/* 1138 */             paramArrayOfChar[(m++)] = 65533;
/*      */ 
/* 1141 */             localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1143 */             continue;
/*      */           }
/*      */ 
/* 1146 */           j++;
/*      */ 
/* 1148 */           if (j + 1 >= k)
/*      */           {
/* 1152 */             paramArrayOfInt[0] = (k - j + 1);
/*      */ 
/* 1154 */             localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1156 */             break label592;
/*      */           }
/*      */ 
/* 1160 */           c1 = conv3ByteUTFtoUTF16(i2, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
/*      */ 
/* 1163 */           if (isLoSurrogate(c1)) {
/* 1164 */             paramArrayOfChar[(m++)] = c2;
/*      */           }
/*      */           else
/*      */           {
/* 1169 */             paramArrayOfChar[(m++)] = 65533;
/*      */ 
/* 1172 */             localCharacterConverterBehavior.onFailConversion();
/*      */           }
/*      */ 
/* 1175 */           paramArrayOfChar[(m++)] = c1; continue;
/*      */         }
/*      */ 
/* 1182 */         if (m < n) {
/* 1183 */           paramArrayOfChar[(m++)] = c2; continue;
/*      */         }
/*      */ 
/* 1188 */         paramArrayOfInt[0] = (k - j + 4);
/*      */ 
/* 1190 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       default:
/* 1200 */         if (m < n) {
/* 1201 */           paramArrayOfChar[(m++)] = 65533;
/*      */         }
/*      */         else
/*      */         {
/* 1207 */           paramArrayOfInt[0] = (k - j + 2);
/*      */ 
/* 1209 */           break label592;
/*      */         }
/*      */ 
/* 1212 */         localCharacterConverterBehavior.onFailConversion();
/*      */       }
/*      */     }
/*      */ 
/* 1216 */     label592: int i3 = m - paramInt2;
/*      */ 
/* 1225 */     return i3;
/*      */   }
/*      */ 
/*      */   public static final byte[] stringToUTF(String paramString)
/*      */   {
/* 1241 */     char[] arrayOfChar = paramString.toCharArray();
/* 1242 */     int i = arrayOfChar.length * 3;
/* 1243 */     byte[] arrayOfByte1 = null;
/* 1244 */     byte[] arrayOfByte2 = null;
/*      */ 
/* 1246 */     arrayOfByte1 = new byte[i];
/*      */ 
/* 1249 */     int j = convertJavaCharsToUTFBytes(arrayOfChar, 0, arrayOfByte1, 0, arrayOfChar.length);
/*      */ 
/* 1252 */     arrayOfByte2 = new byte[j];
/*      */ 
/* 1254 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
/*      */ 
/* 1256 */     arrayOfByte1 = null;
/*      */ 
/* 1265 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToUTFBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 1293 */     int i = paramInt1;
/* 1294 */     int j = paramInt1 + paramInt3;
/*      */ 
/* 1296 */     int k = paramInt2;
/*      */ 
/* 1299 */     for (int n = i; n < j; n++)
/*      */     {
/* 1302 */       m = paramArrayOfChar[n];
/*      */ 
/* 1304 */       if ((m >= 0) && (m <= 127))
/*      */       {
/* 1306 */         paramArrayOfByte[(k++)] = (byte)m;
/*      */       }
/* 1308 */       else if (m > 2047)
/*      */       {
/* 1310 */         paramArrayOfByte[(k++)] = (byte)(0xE0 | m >>> 12 & 0xF);
/* 1311 */         paramArrayOfByte[(k++)] = (byte)(0x80 | m >>> 6 & 0x3F);
/* 1312 */         paramArrayOfByte[(k++)] = (byte)(0x80 | m >>> 0 & 0x3F);
/*      */       }
/*      */       else
/*      */       {
/* 1316 */         paramArrayOfByte[(k++)] = (byte)(0xC0 | m >>> 6 & 0x1F);
/* 1317 */         paramArrayOfByte[(k++)] = (byte)(0x80 | m >>> 0 & 0x3F);
/*      */       }
/*      */     }
/*      */ 
/* 1321 */     int m = k - paramInt2;
/*      */ 
/* 1330 */     return m;
/*      */   }
/*      */ 
/*      */   public static final int UTFStringLength(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1350 */     int i = 0;
/* 1351 */     int j = paramInt1;
/* 1352 */     int k = paramInt1 + paramInt2;
/*      */ 
/* 1354 */     while (j < k)
/*      */     {
/* 1357 */       switch ((paramArrayOfByte[j] & 0xF0) >>> 4)
/*      */       {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/* 1375 */         j++;
/* 1376 */         i++;
/*      */ 
/* 1378 */         break;
/*      */       case 12:
/*      */       case 13:
/* 1384 */         if (j + 1 >= k)
/*      */         {
/* 1386 */           j = k; continue;
/*      */         }
/*      */ 
/* 1390 */         i++;
/* 1391 */         j += 2;
/*      */ 
/* 1394 */         break;
/*      */       case 14:
/* 1398 */         if (j + 2 >= k)
/*      */         {
/* 1400 */           j = k; continue;
/*      */         }
/*      */ 
/* 1404 */         i++;
/* 1405 */         j += 3;
/*      */ 
/* 1408 */         break;
/*      */       case 15:
/* 1412 */         if (j + 3 >= k)
/*      */         {
/* 1414 */           j = k; continue;
/*      */         }
/*      */ 
/* 1418 */         i += 2;
/* 1419 */         j += 4;
/*      */ 
/* 1422 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       default:
/* 1425 */         j++;
/* 1426 */         i++;
/*      */       }
/*      */     }
/*      */ 
/* 1430 */     return i;
/*      */   }
/*      */ 
/*      */   public static final int stringUTFLength(String paramString)
/*      */   {
/* 1444 */     char[] arrayOfChar = paramString.toCharArray();
/* 1445 */     return charArrayUTF8Length(arrayOfChar);
/*      */   }
/*      */ 
/*      */   static final int charArrayUTF8Length(char[] paramArrayOfChar)
/*      */   {
/* 1458 */     int i = 0;
/* 1459 */     int j = paramArrayOfChar.length;
/*      */ 
/* 1462 */     for (int m = 0; m < j; m++)
/*      */     {
/* 1465 */       int k = paramArrayOfChar[m];
/*      */ 
/* 1467 */       if ((k >= 0) && (k <= 127))
/*      */       {
/* 1469 */         i++;
/*      */       }
/* 1471 */       else if (k > 2047)
/*      */       {
/* 1473 */         i += 3;
/*      */       }
/*      */       else
/*      */       {
/* 1477 */         i += 2;
/*      */       }
/*      */     }
/*      */ 
/* 1481 */     return i;
/*      */   }
/*      */ 
/*      */   public static final String AL32UTF8ToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1501 */     return AL32UTF8ToString(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */ 
/*      */   public static final String AL32UTF8ToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */   {
/* 1508 */     char[] arrayOfChar = null;
/*      */     try
/*      */     {
/* 1512 */       arrayOfChar = AL32UTF8ToJavaChar(paramArrayOfByte, paramInt1, paramInt2, paramBoolean);
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*      */     }
/* 1517 */     return new String(arrayOfChar);
/*      */   }
/*      */ 
/*      */   public static final char[] AL32UTF8ToJavaChar(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1539 */     char[] arrayOfChar1 = null;
/*      */     try
/*      */     {
/* 1543 */       arrayOfChar1 = new char[paramInt2];
/*      */ 
/* 1548 */       int[] arrayOfInt = new int[1];
/*      */ 
/* 1550 */       arrayOfInt[0] = paramInt2;
/*      */ 
/* 1552 */       int i = convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, arrayOfChar1, 0, arrayOfInt, paramBoolean);
/*      */ 
/* 1554 */       char[] arrayOfChar2 = new char[i];
/*      */ 
/* 1556 */       System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, i);
/*      */ 
/* 1558 */       arrayOfChar1 = null;
/*      */ 
/* 1560 */       return arrayOfChar2;
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/* 1564 */       failUTFConversion();
/*      */     }
/* 1566 */     return new char[0];
/*      */   }
/*      */ 
/*      */   public static final int convertAL32UTF8BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1605 */     return convertAL32UTF8BytesToJavaChars(paramArrayOfByte, paramInt1, paramArrayOfChar, paramInt2, paramArrayOfInt, paramBoolean, paramArrayOfChar.length - paramInt2);
/*      */   }
/*      */ 
/*      */   public static final int convertAL32UTF8BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 1629 */     CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
/*      */ 
/* 1632 */     int i = paramArrayOfInt[0];
/*      */ 
/* 1634 */     paramArrayOfInt[0] = 0;
/*      */ 
/* 1636 */     int j = paramInt1;
/* 1637 */     int k = paramInt1 + i;
/* 1638 */     int m = paramInt2;
/* 1639 */     int n = paramInt2 + paramInt3;
/*      */ 
/* 1645 */     while (j < k)
/*      */     {
/* 1647 */       int i1 = paramArrayOfByte[(j++)];
/* 1648 */       int i2 = i1 & 0xF0;
/*      */       char c;
/* 1650 */       switch (i2 / 16)
/*      */       {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/* 1670 */         if (m < n) {
/* 1671 */           paramArrayOfChar[(m++)] = (char)(i1 & 0xFFFFFFFF); continue;
/*      */         }
/*      */ 
/* 1676 */         paramArrayOfInt[0] = (k - j + 2);
/*      */ 
/* 1678 */         break;
/*      */       case 12:
/*      */       case 13:
/* 1686 */         if (j >= k)
/*      */         {
/* 1688 */           paramArrayOfInt[0] = 1;
/*      */ 
/* 1690 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1692 */           break label503;
/*      */         }
/*      */ 
/* 1696 */         c = conv2ByteUTFtoUTF16(i1, paramArrayOfByte[(j++)]);
/*      */ 
/* 1698 */         if (m < n) {
/* 1699 */           paramArrayOfChar[(m++)] = c;
/*      */         }
/*      */         else
/*      */         {
/* 1704 */           paramArrayOfInt[0] = (k - j + 3);
/*      */ 
/* 1706 */           break label503;
/*      */         }
/*      */ 
/* 1709 */         localCharacterConverterBehavior.onFailConversion(c);
/*      */ 
/* 1712 */         break;
/*      */       case 14:
/* 1715 */         if (j + 1 >= k)
/*      */         {
/* 1717 */           paramArrayOfInt[0] = (k - j + 1);
/*      */ 
/* 1719 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1721 */           break label503;
/*      */         }
/*      */ 
/* 1725 */         c = conv3ByteAL32UTF8toUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)]);
/*      */ 
/* 1728 */         if (m < n) {
/* 1729 */           paramArrayOfChar[(m++)] = c;
/*      */         }
/*      */         else
/*      */         {
/* 1734 */           paramArrayOfInt[0] = (k - j + 4);
/*      */ 
/* 1736 */           break label503;
/*      */         }
/*      */ 
/* 1739 */         localCharacterConverterBehavior.onFailConversion(c);
/*      */ 
/* 1744 */         break;
/*      */       case 15:
/* 1747 */         if (j + 2 >= k)
/*      */         {
/* 1749 */           paramArrayOfInt[0] = (k - j + 1);
/*      */ 
/* 1751 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1753 */           break label503;
/*      */         }
/*      */ 
/* 1756 */         if (m > n - 2)
/*      */         {
/* 1760 */           paramArrayOfInt[0] = (k - j + 2);
/*      */ 
/* 1762 */           break label503;
/*      */         }
/*      */ 
/* 1765 */         i3 = conv4ByteAL32UTF8toUTF16(i1, paramArrayOfByte[(j++)], paramArrayOfByte[(j++)], paramArrayOfByte[(j++)], paramArrayOfChar, m);
/*      */ 
/* 1769 */         if (i3 == 1)
/*      */         {
/* 1771 */           localCharacterConverterBehavior.onFailConversion();
/*      */ 
/* 1773 */           m++; continue;
/*      */         }
/*      */ 
/* 1776 */         m += 2;
/*      */ 
/* 1778 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       default:
/* 1783 */         if (m < n) {
/* 1784 */           paramArrayOfChar[(m++)] = 65533;
/*      */         }
/*      */         else
/*      */         {
/* 1790 */           paramArrayOfInt[0] = (k - j + 2);
/*      */ 
/* 1792 */           break label503;
/*      */         }
/*      */ 
/* 1795 */         localCharacterConverterBehavior.onFailConversion();
/*      */       }
/*      */     }
/*      */ 
/* 1799 */     label503: int i3 = m - paramInt2;
/*      */ 
/* 1808 */     return i3;
/*      */   }
/*      */ 
/*      */   public static final byte[] stringToAL32UTF8(String paramString)
/*      */   {
/* 1822 */     char[] arrayOfChar = paramString.toCharArray();
/* 1823 */     int i = arrayOfChar.length * 3;
/* 1824 */     byte[] arrayOfByte1 = null;
/* 1825 */     byte[] arrayOfByte2 = null;
/*      */ 
/* 1827 */     arrayOfByte1 = new byte[i];
/*      */ 
/* 1829 */     int j = convertJavaCharsToAL32UTF8Bytes(arrayOfChar, 0, arrayOfByte1, 0, arrayOfChar.length);
/*      */ 
/* 1832 */     arrayOfByte2 = new byte[j];
/*      */ 
/* 1834 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
/*      */ 
/* 1836 */     arrayOfByte1 = null;
/*      */ 
/* 1845 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToAL32UTF8Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 1872 */     int i = paramInt1;
/* 1873 */     int j = paramInt1 + paramInt3;
/* 1874 */     int k = paramInt2;
/*      */ 
/* 1877 */     for (int m = i; m < j; m++)
/*      */     {
/* 1880 */       int n = paramArrayOfChar[m];
/* 1881 */       int i1 = 0;
/*      */ 
/* 1883 */       if ((n >= 0) && (n <= 127))
/*      */       {
/* 1885 */         paramArrayOfByte[(k++)] = (byte)n;
/*      */       }
/* 1887 */       else if (isHiSurrogate((char)n))
/*      */       {
/* 1891 */         if ((m + 1 < j) && (isLoSurrogate((char)(i1 = paramArrayOfChar[(m + 1)]))))
/*      */         {
/* 1896 */           i2 = (n >>> 6 & 0xF) + 1;
/* 1897 */           paramArrayOfByte[(k++)] = (byte)(i2 >>> 2 | 0xF0);
/* 1898 */           paramArrayOfByte[(k++)] = (byte)((i2 & 0x3) << 4 | n >>> 2 & 0xF | 0x80);
/*      */ 
/* 1900 */           paramArrayOfByte[(k++)] = (byte)((n & 0x3) << 4 | i1 >>> 6 & 0xF | 0x80);
/*      */ 
/* 1902 */           paramArrayOfByte[(k++)] = (byte)(i1 & 0x3F | 0x80);
/* 1903 */           m++;
/*      */         }
/*      */         else
/*      */         {
/* 1907 */           paramArrayOfByte[(k++)] = -17;
/* 1908 */           paramArrayOfByte[(k++)] = -65;
/* 1909 */           paramArrayOfByte[(k++)] = -67;
/*      */         }
/*      */       }
/* 1912 */       else if (n > 2047)
/*      */       {
/* 1914 */         paramArrayOfByte[(k++)] = (byte)(0xE0 | n >>> 12 & 0xF);
/* 1915 */         paramArrayOfByte[(k++)] = (byte)(0x80 | n >>> 6 & 0x3F);
/* 1916 */         paramArrayOfByte[(k++)] = (byte)(0x80 | n >>> 0 & 0x3F);
/*      */       }
/*      */       else
/*      */       {
/* 1920 */         paramArrayOfByte[(k++)] = (byte)(0xC0 | n >>> 6 & 0x1F);
/* 1921 */         paramArrayOfByte[(k++)] = (byte)(0x80 | n >>> 0 & 0x3F);
/*      */       }
/*      */     }
/*      */ 
/* 1925 */     int i2 = k - paramInt2;
/*      */ 
/* 1934 */     return i2;
/*      */   }
/*      */ 
/*      */   public static final int string32UTF8Length(String paramString)
/*      */   {
/* 1952 */     return charArray32UTF8Length(paramString.toCharArray());
/*      */   }
/*      */ 
/*      */   static final int charArray32UTF8Length(char[] paramArrayOfChar)
/*      */   {
/* 1964 */     int i = 0;
/* 1965 */     int j = paramArrayOfChar.length;
/*      */ 
/* 1967 */     for (int k = 0; k < j; k++)
/*      */     {
/* 1970 */       int m = paramArrayOfChar[k];
/*      */ 
/* 1972 */       if ((m >= 0) && (m <= 127))
/*      */       {
/* 1974 */         i++;
/*      */       }
/* 1976 */       else if (m > 2047)
/*      */       {
/* 1979 */         if (isHiSurrogate((char)m))
/*      */         {
/* 1982 */           if (k + 1 >= j)
/*      */             continue;
/* 1984 */           i += 4;
/* 1985 */           k++;
/*      */         }
/*      */         else
/*      */         {
/* 1990 */           i += 3;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1995 */         i += 2;
/*      */       }
/*      */     }
/*      */ 
/* 1999 */     return i;
/*      */   }
/*      */ 
/*      */   public static final String AL16UTF16BytesToString(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 2018 */     char[] arrayOfChar = new char[paramInt >>> 1];
/*      */ 
/* 2020 */     AL16UTF16BytesToJavaChars(paramArrayOfByte, paramInt, arrayOfChar);
/*      */ 
/* 2022 */     return new String(arrayOfChar);
/*      */   }
/*      */ 
/*      */   public static final int AL16UTF16BytesToJavaChars(byte[] paramArrayOfByte, int paramInt, char[] paramArrayOfChar)
/*      */   {
/* 2041 */     int k = paramInt >>> 1;
/*      */ 
/* 2044 */     int i = 0; for (int j = 0; i < k; i++)
/*      */     {
/* 2046 */       int m = paramArrayOfByte[j] << 8;
/* 2047 */       paramArrayOfChar[i] = (char)(m | paramArrayOfByte[(j + 1)] & 0xFF);
/*      */ 
/* 2044 */       j += 2;
/*      */     }
/*      */ 
/* 2050 */     return i;
/*      */   }
/*      */ 
/*      */   public static final int convertAL16UTF16BytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2079 */     CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
/*      */ 
/* 2082 */     int i = paramInt2;
/* 2083 */     int j = paramInt1;
/* 2084 */     int k = paramInt1 + paramInt3;
/*      */ 
/* 2089 */     for (; j + 1 < k; j += 2)
/*      */     {
/* 2091 */       int m = paramArrayOfByte[j] << 8;
/* 2092 */       char c1 = (char)(m | paramArrayOfByte[(j + 1)] & 0xFF);
/*      */ 
/* 2094 */       if (isHiSurrogate(c1))
/*      */       {
/* 2096 */         j += 2;
/*      */ 
/* 2098 */         if (j + 1 >= k)
/*      */           continue;
/* 2100 */         char c2 = (char)((paramArrayOfByte[j] << 8) + (paramArrayOfByte[(j + 1)] & 0xFF));
/*      */ 
/* 2102 */         if (isLoSurrogate(c2))
/*      */         {
/* 2104 */           paramArrayOfChar[(i++)] = c1;
/*      */         }
/*      */         else
/*      */         {
/* 2108 */           paramArrayOfChar[(i++)] = 65533;
/*      */         }
/*      */ 
/* 2112 */         paramArrayOfChar[(i++)] = c2;
/*      */       }
/*      */       else
/*      */       {
/* 2124 */         paramArrayOfChar[(i++)] = c1;
/*      */       }
/*      */     }
/*      */ 
/* 2128 */     j = i - paramInt2;
/*      */ 
/* 2137 */     return j;
/*      */   }
/*      */ 
/*      */   public static final int convertAL16UTF16LEBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 2158 */     CharacterConverterBehavior localCharacterConverterBehavior = paramBoolean ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
/*      */ 
/* 2161 */     int i = paramInt2;
/* 2162 */     int j = paramInt1;
/* 2163 */     int k = paramInt1 + paramInt3;
/*      */ 
/* 2168 */     for (; j + 1 < k; j += 2)
/*      */     {
/* 2170 */       int m = paramArrayOfByte[(j + 1)] << 8;
/* 2171 */       char c1 = (char)(m | paramArrayOfByte[j] & 0xFF);
/*      */ 
/* 2173 */       if (isHiSurrogate(c1))
/*      */       {
/* 2175 */         j += 2;
/*      */ 
/* 2177 */         if (j + 1 >= k)
/*      */           continue;
/* 2179 */         char c2 = (char)((paramArrayOfByte[(j + 1)] << 8) + (paramArrayOfByte[j] & 0xFF));
/*      */ 
/* 2181 */         if (isLoSurrogate(c2))
/*      */         {
/* 2183 */           paramArrayOfChar[(i++)] = c1;
/*      */         }
/*      */         else
/*      */         {
/* 2187 */           paramArrayOfChar[(i++)] = 65533;
/*      */         }
/*      */ 
/* 2191 */         paramArrayOfChar[(i++)] = c2;
/*      */       }
/*      */       else
/*      */       {
/* 2203 */         paramArrayOfChar[(i++)] = c1;
/*      */       }
/*      */     }
/*      */ 
/* 2207 */     j = i - paramInt2;
/*      */ 
/* 2209 */     return j;
/*      */   }
/*      */ 
/*      */   public static final byte[] stringToAL16UTF16Bytes(String paramString)
/*      */   {
/* 2230 */     char[] arrayOfChar = paramString.toCharArray();
/* 2231 */     int i = arrayOfChar.length;
/* 2232 */     byte[] arrayOfByte = new byte[i * 2];
/*      */ 
/* 2234 */     javaCharsToAL16UTF16Bytes(arrayOfChar, i, arrayOfByte);
/*      */ 
/* 2243 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static final int javaCharsToAL16UTF16Bytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
/*      */   {
/* 2259 */     int i = Math.min(paramInt, paramArrayOfByte.length >>> 1);
/*      */ 
/* 2261 */     return convertJavaCharsToAL16UTF16Bytes(paramArrayOfChar, 0, paramArrayOfByte, 0, i);
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToAL16UTF16Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 2268 */     int i = paramInt1;
/* 2269 */     int j = paramInt2;
/* 2270 */     int k = paramInt1 + paramInt3;
/*      */ 
/* 2272 */     for (; i < k; j += 2)
/*      */     {
/* 2274 */       paramArrayOfByte[j] = (byte)(paramArrayOfChar[i] >>> '\b' & 0xFF);
/* 2275 */       paramArrayOfByte[(j + 1)] = (byte)(paramArrayOfChar[i] & 0xFF);
/*      */ 
/* 2272 */       i++;
/*      */     }
/*      */ 
/* 2278 */     return j - paramInt2;
/*      */   }
/*      */ 
/*      */   public static final byte[] stringToAL16UTF16LEBytes(String paramString)
/*      */   {
/* 2292 */     char[] arrayOfChar = paramString.toCharArray();
/* 2293 */     byte[] arrayOfByte = new byte[arrayOfChar.length * 2];
/*      */ 
/* 2295 */     javaCharsToAL16UTF16LEBytes(arrayOfChar, arrayOfChar.length, arrayOfByte);
/*      */ 
/* 2297 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static final int javaCharsToAL16UTF16LEBytes(char[] paramArrayOfChar, int paramInt, byte[] paramArrayOfByte)
/*      */   {
/* 2313 */     return convertJavaCharsToAL16UTF16LEBytes(paramArrayOfChar, 0, paramArrayOfByte, 0, paramInt);
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToAL16UTF16LEBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 2320 */     int i = paramInt1;
/* 2321 */     int j = paramInt2;
/* 2322 */     int k = paramInt1 + paramInt3;
/*      */ 
/* 2324 */     for (; i < k; j += 2)
/*      */     {
/* 2326 */       paramArrayOfByte[j] = (byte)(paramArrayOfChar[i] & 0xFF);
/* 2327 */       paramArrayOfByte[(j + 1)] = (byte)(paramArrayOfChar[i] >>> '\b');
/*      */ 
/* 2324 */       i++;
/*      */     }
/*      */ 
/* 2330 */     return j - paramInt2;
/*      */   }
/*      */ 
/*      */   public static final int convertASCIIBytesToJavaChars(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 2369 */     int k = paramInt2 + paramInt3;
/*      */ 
/* 2371 */     int i = paramInt2; for (int j = paramInt1; i < k; j++)
/*      */     {
/* 2373 */       paramArrayOfChar[i] = (char)(0xFF & paramArrayOfByte[j]);
/*      */ 
/* 2371 */       i++;
/*      */     }
/*      */ 
/* 2383 */     return paramInt3;
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToASCIIBytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 2415 */     for (int i = 0; i < paramInt3; i++)
/*      */     {
/* 2420 */       paramArrayOfByte[(paramInt2 + i)] = (byte)paramArrayOfChar[(paramInt1 + i)];
/*      */     }
/*      */ 
/* 2430 */     return paramInt3;
/*      */   }
/*      */ 
/*      */   public static final int convertJavaCharsToISOLATIN1Bytes(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */     throws SQLException
/*      */   {
/* 2449 */     for (int i = 0; i < paramInt3; i++)
/*      */     {
/* 2451 */       int j = paramArrayOfChar[(paramInt1 + i)];
/*      */ 
/* 2453 */       if (j > 255)
/*      */       {
/* 2457 */         paramArrayOfByte[(paramInt2 + i)] = -65;
/*      */       }
/* 2459 */       else paramArrayOfByte[(paramInt2 + i)] = (byte)j;
/*      */ 
/*      */     }
/*      */ 
/* 2469 */     return paramInt3;
/*      */   }
/*      */ 
/*      */   public static final byte[] stringToASCII(String paramString)
/*      */   {
/* 2491 */     byte[] arrayOfByte = new byte[paramString.length()];
/*      */ 
/* 2493 */     paramString.getBytes(0, paramString.length(), arrayOfByte, 0);
/*      */ 
/* 2502 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public static final long convertUTF32toUTF16(long paramLong)
/*      */   {
/* 2511 */     if (paramLong > 65535L)
/*      */     {
/* 2513 */       long l = 0xD8 | paramLong - 65536L >> 18 & 0xFF;
/* 2514 */       l = paramLong - 65536L >> 10 & 0xFF | l << 8;
/* 2515 */       l = 0xDC | (paramLong & 0x3FF) >> 8 & 0xFF | l << 8;
/* 2516 */       l = paramLong & 0xFF | l << 8;
/*      */ 
/* 2518 */       return l;
/*      */     }
/*      */ 
/* 2523 */     return paramLong;
/*      */   }
/*      */ 
/*      */   static final boolean isHiSurrogate(char paramChar)
/*      */   {
/* 2545 */     return (char)(paramChar & 0xFC00) == 55296;
/*      */   }
/*      */ 
/*      */   static final boolean isLoSurrogate(char paramChar)
/*      */   {
/* 2561 */     return (char)(paramChar & 0xFC00) == 56320;
/*      */   }
/*      */ 
/*      */   static final boolean check80toBF(byte paramByte)
/*      */   {
/* 2576 */     return (paramByte & 0xFFFFFFC0) == -128;
/*      */   }
/*      */ 
/*      */   static final boolean check80to8F(byte paramByte)
/*      */   {
/* 2591 */     return (paramByte & 0xFFFFFFF0) == -128;
/*      */   }
/*      */ 
/*      */   static final boolean check80to9F(byte paramByte)
/*      */   {
/* 2604 */     return (paramByte & 0xFFFFFFE0) == -128;
/*      */   }
/*      */ 
/*      */   static final boolean checkA0toBF(byte paramByte)
/*      */   {
/* 2617 */     return (paramByte & 0xFFFFFFE0) == -96;
/*      */   }
/*      */ 
/*      */   static final boolean check90toBF(byte paramByte)
/*      */   {
/* 2630 */     return ((paramByte & 0xFFFFFFC0) == -128) && ((paramByte & 0x30) != 0);
/*      */   }
/*      */ 
/*      */   static final char conv2ByteUTFtoUTF16(byte paramByte1, byte paramByte2)
/*      */   {
/* 2646 */     if ((paramByte1 < -62) || (paramByte1 > -33) || (!check80toBF(paramByte2)))
/*      */     {
/* 2649 */       return 65533;
/*      */     }
/*      */ 
/* 2652 */     return (char)((paramByte1 & 0x1F) << 6 | paramByte2 & 0x3F);
/*      */   }
/*      */ 
/*      */   static final char conv3ByteUTFtoUTF16(byte paramByte1, byte paramByte2, byte paramByte3)
/*      */   {
/* 2671 */     if (((paramByte1 != -32) || (!checkA0toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -31) || (paramByte1 > -17) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3))))
/*      */     {
/* 2676 */       return 65533;
/*      */     }
/*      */ 
/* 2679 */     return (char)((paramByte1 & 0xF) << 12 | (paramByte2 & 0x3F) << 6 | paramByte3 & 0x3F);
/*      */   }
/*      */ 
/*      */   static final char conv3ByteAL32UTF8toUTF16(byte paramByte1, byte paramByte2, byte paramByte3)
/*      */   {
/* 2701 */     if (((paramByte1 != -32) || (!checkA0toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -31) || (paramByte1 > -20) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 != -19) || (!check80to9F(paramByte2)) || (!check80toBF(paramByte3))) && ((paramByte1 < -18) || (paramByte1 > -17) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3))))
/*      */     {
/* 2708 */       return 65533;
/*      */     }
/*      */ 
/* 2711 */     return (char)((paramByte1 & 0xF) << 12 | (paramByte2 & 0x3F) << 6 | paramByte3 & 0x3F);
/*      */   }
/*      */ 
/*      */   static final int conv4ByteAL32UTF8toUTF16(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, char[] paramArrayOfChar, int paramInt)
/*      */   {
/* 2731 */     int i = 0;
/*      */ 
/* 2733 */     if (((paramByte1 != -16) || (!check90toBF(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))) && ((paramByte1 < -15) || (paramByte1 > -13) || (!check80toBF(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))) && ((paramByte1 != -12) || (!check80to8F(paramByte2)) || (!check80toBF(paramByte3)) || (!check80toBF(paramByte4))))
/*      */     {
/* 2738 */       paramArrayOfChar[paramInt] = 65533;
/*      */ 
/* 2740 */       return 1;
/*      */     }
/*      */ 
/* 2743 */     paramArrayOfChar[paramInt] = (char)((((paramByte1 & 0x7) << 2 | paramByte2 >>> 4 & 0x3) - 1 & 0xF) << 6 | (paramByte2 & 0xF) << 2 | paramByte3 >>> 4 & 0x3 | 0xD800);
/*      */ 
/* 2748 */     paramArrayOfChar[(paramInt + 1)] = (char)((paramByte3 & 0xF) << 6 | paramByte4 & 0x3F | 0xDC00);
/*      */ 
/* 2750 */     return 2;
/*      */   }
/*      */ 
/*      */   static void failUTFConversion()
/*      */     throws SQLException
/*      */   {
/* 2818 */     DatabaseError.throwSqlException(55);
/*      */   }
/*      */ 
/*      */   public int encodedByteLength(String paramString)
/*      */   {
/* 2833 */     if ((paramString == null) || (paramString.length() == 0)) return 0;
/* 2834 */     return convertWithReplacement(paramString).length;
/*      */   }
/*      */ 
/*      */   public int encodedByteLength(char[] paramArrayOfChar)
/*      */   {
/* 2849 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) return 0;
/* 2850 */     return convertWithReplacement(new String(paramArrayOfChar)).length;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/*  359 */       Class.forName("oracle.i18n.text.converter.CharacterConverterSJIS");
/*      */ 
/*  361 */       CharacterSetWithConverter.ccFactory = new CharacterConverterFactoryOGS();
/*      */     }
/*      */     catch (ClassNotFoundException localClassNotFoundException)
/*      */     {
/*      */     }
/*      */ 
/*  399 */     factory = new CharacterSetFactoryDefault();
/*      */ 
/* 2861 */     _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   }
/*      */ 
/*      */   static abstract class CharacterConverterBehavior
/*      */   {
/* 2760 */     public static final char[] NULL_CHARS = new char[1];
/*      */     public static final char UTF16_REPLACEMENT_CHAR = '�';
/* 2762 */     public static final CharacterConverterBehavior REPORT_ERROR = new CharacterSet.2("Report Error");
/*      */ 
/* 2781 */     public static final CharacterConverterBehavior REPLACEMENT = new CharacterSet.1("Replacement");
/*      */     private final String m_name;
/*      */ 
/*      */     public CharacterConverterBehavior(String paramString)
/*      */     {
/* 2802 */       this.m_name = paramString;
/*      */     }
/*      */ 
/*      */     public abstract void onFailConversion(char paramChar)
/*      */       throws SQLException;
/*      */ 
/*      */     public abstract void onFailConversion()
/*      */       throws SQLException;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSet
 * JD-Core Version:    0.6.0
 */