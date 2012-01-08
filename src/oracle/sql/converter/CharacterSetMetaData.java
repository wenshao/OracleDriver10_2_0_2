/*      */ package oracle.sql.converter;
/*      */ 
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ 
/*      */ public class CharacterSetMetaData
/*      */ {
/*      */   static final short WIDTH_SIZE = 8;
/*      */   static final short WIDTH_MASK = 255;
/*      */   static final short FLAG_FIXEDWIDTH = 256;
/*      */   public static final int ST_BADCODESET = 0;
/*   48 */   private static final HashMap language = new HashMap(58, 1.0F);
/*   49 */   private static final HashMap territory = new HashMap(134, 1.0F);
/*      */   private static final short[][] m_maxCharWidth;
/*      */ 
/*      */   public static String getNLSLanguage(Locale paramLocale)
/*      */   {
/* 1070 */     String str = null;
/*      */ 
/* 1072 */     str = (String)language.get(paramLocale.getLanguage() + "_" + paramLocale.getCountry());
/*      */ 
/* 1074 */     if (str == null)
/*      */     {
/* 1076 */       str = (String)language.get(paramLocale.getLanguage());
/*      */     }
/*      */ 
/* 1079 */     return str;
/*      */   }
/*      */ 
/*      */   public static String getNLSTerritory(Locale paramLocale)
/*      */   {
/* 1093 */     String str = (String)territory.get(paramLocale.getCountry());
/*      */ 
/* 1097 */     if (str == null)
/*      */     {
/* 1100 */       str = (String)territory.get(paramLocale.getLanguage() + "_" + paramLocale.getCountry());
/*      */ 
/* 1104 */       if ((str == null) && (paramLocale.getCountry().equals("ES"))) {
/* 1105 */         str = "SPAIN";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1110 */     if (str == null)
/*      */     {
/* 1112 */       str = (String)territory.get(paramLocale.getLanguage());
/*      */     }
/*      */ 
/* 1116 */     return str;
/*      */   }
/*      */ 
/*      */   public static boolean isFixedWidth(int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1127 */     if (paramInt == 0) return false;
/*      */ 
/* 1129 */     int i = -1;
/* 1130 */     int j = 0;
/* 1131 */     int k = m_maxCharWidth.length - 1;
/* 1132 */     int m = -1;
/*      */ 
/* 1134 */     while (j <= k)
/*      */     {
/* 1136 */       m = (j + k) / 2;
/*      */ 
/* 1138 */       if (paramInt < m_maxCharWidth[m][0])
/*      */       {
/* 1140 */         k = m - 1; continue;
/*      */       }
/* 1142 */       if (paramInt > m_maxCharWidth[m][0])
/*      */       {
/* 1144 */         j = m + 1; continue;
/*      */       }
/*      */ 
/* 1150 */       i = m;
/*      */     }
/*      */ 
/* 1156 */     if (i < 0)
/*      */     {
/* 1158 */       DatabaseError.throwSqlException(35);
/* 1159 */       return false;
/*      */     }
/*      */ 
/* 1162 */     return (m_maxCharWidth[i][1] & 0x100) != 0;
/*      */   }
/*      */ 
/*      */   public static int getRatio(int paramInt1, int paramInt2)
/*      */   {
/* 1200 */     int i = -1;
/* 1201 */     int j = 0;
/* 1202 */     int k = m_maxCharWidth.length - 1;
/*      */ 
/* 1205 */     if (paramInt2 == paramInt1)
/*      */     {
/* 1207 */       return 1;
/*      */     }
/*      */     int m;
/* 1210 */     while (j <= k)
/*      */     {
/* 1212 */       m = (j + k) / 2;
/*      */ 
/* 1214 */       if (paramInt1 < m_maxCharWidth[m][0])
/*      */       {
/* 1216 */         k = m - 1; continue;
/*      */       }
/* 1218 */       if (paramInt1 > m_maxCharWidth[m][0])
/*      */       {
/* 1220 */         j = m + 1; continue;
/*      */       }
/*      */ 
/* 1226 */       i = m;
/*      */     }
/*      */ 
/* 1232 */     if (i < 0)
/*      */     {
/* 1234 */       return 0;
/*      */     }
/*      */ 
/* 1238 */     int n = -1;
/*      */ 
/* 1240 */     j = 0;
/* 1241 */     k = m_maxCharWidth.length - 1;
/*      */ 
/* 1243 */     while (j <= k)
/*      */     {
/* 1245 */       m = (j + k) / 2;
/*      */ 
/* 1247 */       if (paramInt2 < m_maxCharWidth[m][0])
/*      */       {
/* 1249 */         k = m - 1; continue;
/*      */       }
/* 1251 */       if (paramInt2 > m_maxCharWidth[m][0])
/*      */       {
/* 1253 */         j = m + 1; continue;
/*      */       }
/*      */ 
/* 1259 */       n = m;
/*      */     }
/*      */ 
/* 1265 */     if (n < 0)
/*      */     {
/* 1267 */       return 0;
/*      */     }
/*      */ 
/* 1270 */     int i1 = m_maxCharWidth[i][1] & 0xFF;
/*      */ 
/* 1272 */     if (i1 == 1)
/*      */     {
/* 1274 */       return 1;
/*      */     }
/*      */ 
/* 1277 */     if (m_maxCharWidth[n][1] >> 8 == 0)
/*      */     {
/* 1281 */       return i1;
/*      */     }
/*      */ 
/* 1289 */     int i2 = m_maxCharWidth[n][1] & 0xFF;
/* 1290 */     int i3 = i1 / i2;
/*      */ 
/* 1292 */     if (i1 % i2 != 0)
/*      */     {
/* 1294 */       i3++;
/*      */     }
/*      */ 
/* 1297 */     return i3;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   52 */     language.put("", "AMERICAN");
/*   53 */     language.put("ar_EG", "EGYPTIAN");
/*   54 */     language.put("ar", "ARABIC");
/*   55 */     language.put("as", "ASSAMESE");
/*   56 */     language.put("bg", "BULGARIAN");
/*   57 */     language.put("bn", "BANGLA");
/*   58 */     language.put("ca", "CATALAN");
/*   59 */     language.put("cs", "CZECH");
/*   60 */     language.put("da", "DANISH");
/*   61 */     language.put("de", "GERMAN");
/*   62 */     language.put("el", "GREEK");
/*   63 */     language.put("en", "AMERICAN");
/*      */ 
/*   84 */     language.put("es_ES", "SPANISH");
/*   85 */     language.put("es_MX", "MEXICAN SPANISH");
/*   86 */     language.put("es", "LATIN AMERICAN SPANISH");
/*   87 */     language.put("et", "ESTONIAN");
/*   88 */     language.put("fi", "FINNISH");
/*   89 */     language.put("fr_CA", "CANADIAN FRENCH");
/*   90 */     language.put("fr", "FRENCH");
/*   91 */     language.put("gu", "GUJARATI");
/*   92 */     language.put("he", "HEBREW");
/*   93 */     language.put("hi", "HINDI");
/*   94 */     language.put("hr", "CROATIAN");
/*   95 */     language.put("hu", "HUNGARIAN");
/*   96 */     language.put("id", "INDONESIAN");
/*   97 */     language.put("in", "INDONESIAN");
/*   98 */     language.put("is", "ICELANDIC");
/*   99 */     language.put("it", "ITALIAN");
/*  100 */     language.put("iw", "HEBREW");
/*  101 */     language.put("ja", "JAPANESE");
/*  102 */     language.put("kn", "KANNADA");
/*  103 */     language.put("ko", "KOREAN");
/*  104 */     language.put("lt", "LITHUANIAN");
/*  105 */     language.put("lv", "LATVIAN");
/*  106 */     language.put("ml", "MALAYALAM");
/*  107 */     language.put("mr", "MARATHI");
/*  108 */     language.put("ms", "MALAY");
/*  109 */     language.put("nl", "DUTCH");
/*  110 */     language.put("no", "NORWEGIAN");
/*  111 */     language.put("or", "ORIYA");
/*  112 */     language.put("pa", "PUNJABI");
/*  113 */     language.put("pl", "POLISH");
/*  114 */     language.put("pt_BR", "BRAZILIAN PORTUGUESE");
/*  115 */     language.put("pt", "PORTUGUESE");
/*  116 */     language.put("ro", "ROMANIAN");
/*  117 */     language.put("ru", "RUSSIAN");
/*  118 */     language.put("sk", "SLOVAK");
/*  119 */     language.put("sl", "SLOVENIAN");
/*  120 */     language.put("sv", "SWEDISH");
/*  121 */     language.put("ta", "TAMIL");
/*  122 */     language.put("te", "TELUGU");
/*  123 */     language.put("th", "THAI");
/*  124 */     language.put("tr", "TURKISH");
/*  125 */     language.put("uk", "UKRAINIAN");
/*  126 */     language.put("vi", "VIETNAMESE");
/*  127 */     language.put("zh_HK", "TRADITIONAL CHINESE");
/*  128 */     language.put("zh_TW", "TRADITIONAL CHINESE");
/*  129 */     language.put("zh", "SIMPLIFIED CHINESE");
/*      */ 
/*  131 */     territory.put("AE", "UNITED ARAB EMIRATES");
/*  132 */     territory.put("AT", "AUSTRIA");
/*  133 */     territory.put("AU", "AUSTRALIA");
/*  134 */     territory.put("BD", "BANGLADESH");
/*  135 */     territory.put("BE", "BELGIUM");
/*  136 */     territory.put("BG", "BULGARIA");
/*  137 */     territory.put("BH", "BAHRAIN");
/*  138 */     territory.put("BR", "BRAZIL");
/*  139 */     territory.put("CA", "CANADA");
/*  140 */     territory.put("CH", "SWITZERLAND");
/*  141 */     territory.put("CL", "CHILE");
/*  142 */     territory.put("CN", "CHINA");
/*  143 */     territory.put("CO", "COLOMBIA");
/*  144 */     territory.put("CR", "COSTA RICA");
/*  145 */     territory.put("CY", "CYPRUS");
/*  146 */     territory.put("CZ", "CZECH REPUBLIC");
/*  147 */     territory.put("DE", "GERMANY");
/*  148 */     territory.put("DJ", "DJIBOUTI");
/*  149 */     territory.put("DK", "DENMARK");
/*  150 */     territory.put("DZ", "ALGERIA");
/*  151 */     territory.put("EE", "ESTONIA");
/*  152 */     territory.put("EG", "EGYPT");
/*  153 */     territory.put("es_ES", "SPAIN");
/*  154 */     territory.put("ca_ES", "CATALONIA");
/*      */ 
/*  157 */     territory.put("FI", "FINLAND");
/*  158 */     territory.put("FR", "FRANCE");
/*  159 */     territory.put("GB", "UNITED KINGDOM");
/*  160 */     territory.put("GR", "GREECE");
/*  161 */     territory.put("GT", "GUATEMALA");
/*  162 */     territory.put("HK", "HONG KONG");
/*  163 */     territory.put("HR", "CROATIA");
/*  164 */     territory.put("HU", "HUNGARY");
/*  165 */     territory.put("ID", "INDONESIA");
/*  166 */     territory.put("IE", "IRELAND");
/*  167 */     territory.put("IL", "ISRAEL");
/*  168 */     territory.put("IN", "INDIA");
/*  169 */     territory.put("IQ", "IRAQ");
/*  170 */     territory.put("IS", "ICELAND");
/*  171 */     territory.put("IT", "ITALY");
/*  172 */     territory.put("JO", "JORDAN");
/*  173 */     territory.put("JP", "JAPAN");
/*  174 */     territory.put("KR", "KOREA");
/*  175 */     territory.put("KW", "KUWAIT");
/*  176 */     territory.put("LB", "LEBANON");
/*  177 */     territory.put("LT", "LITHUANIA");
/*  178 */     territory.put("LU", "LUXEMBOURG");
/*  179 */     territory.put("LV", "LATVIA");
/*  180 */     territory.put("LY", "LIBYA");
/*  181 */     territory.put("MA", "MOROCCO");
/*  182 */     territory.put("MR", "MAURITANIA");
/*  183 */     territory.put("MX", "MEXICO");
/*  184 */     territory.put("MY", "MALAYSIA");
/*  185 */     territory.put("NI", "NICARAGUA");
/*  186 */     territory.put("NL", "THE NETHERLANDS");
/*  187 */     territory.put("NO", "NORWAY");
/*  188 */     territory.put("NZ", "NEW ZEALAND");
/*  189 */     territory.put("OM", "OMAN");
/*  190 */     territory.put("PA", "PANAMA");
/*  191 */     territory.put("PE", "PERU");
/*  192 */     territory.put("PL", "POLAND");
/*  193 */     territory.put("PR", "PUERTO RICO");
/*  194 */     territory.put("PT", "PORTUGAL");
/*  195 */     territory.put("QA", "QATAR");
/*  196 */     territory.put("RO", "ROMANIA");
/*  197 */     territory.put("RU", "CIS");
/*  198 */     territory.put("SA", "SAUDI ARABIA");
/*  199 */     territory.put("SD", "SUDAN");
/*  200 */     territory.put("SE", "SWEDEN");
/*  201 */     territory.put("SG", "SINGAPORE");
/*  202 */     territory.put("SI", "SLOVENIA");
/*  203 */     territory.put("SK", "SLOVAKIA");
/*  204 */     territory.put("SO", "SOMALIA");
/*  205 */     territory.put("SV", "EL SALVADOR");
/*  206 */     territory.put("SY", "SYRIA");
/*  207 */     territory.put("TH", "THAILAND");
/*  208 */     territory.put("TN", "TUNISIA");
/*  209 */     territory.put("TR", "TURKEY");
/*  210 */     territory.put("TW", "TAIWAN");
/*  211 */     territory.put("UA", "UKRAINE");
/*  212 */     territory.put("US", "AMERICA");
/*  213 */     territory.put("VE", "VENEZUELA");
/*  214 */     territory.put("VN", "VIETNAM");
/*  215 */     territory.put("YE", "YEMEN");
/*  216 */     territory.put("ZA", "SOUTH AFRICA");
/*      */ 
/*  218 */     territory.put("ar", "SAUDI ARABIA");
/*  219 */     territory.put("as", "INDIA");
/*  220 */     territory.put("bg", "BULGARIA");
/*  221 */     territory.put("bn", "BANGLADESH");
/*  222 */     territory.put("ca", "CATALONIA");
/*  223 */     territory.put("cs", "CZECH REPUBLIC");
/*  224 */     territory.put("da", "DENMARK");
/*  225 */     territory.put("de", "GERMANY");
/*  226 */     territory.put("el", "GREECE");
/*  227 */     territory.put("en", "AMERICA");
/*  228 */     territory.put("es", "AMERICA");
/*  229 */     territory.put("et", "ESTONIA");
/*  230 */     territory.put("fi", "FINLAND");
/*  231 */     territory.put("fr", "FRANCE");
/*  232 */     territory.put("gu", "INDIA");
/*  233 */     territory.put("he", "ISRAEL");
/*  234 */     territory.put("hi", "INDIA");
/*  235 */     territory.put("hr", "CROATIA");
/*  236 */     territory.put("hu", "HUNGARY");
/*  237 */     territory.put("id", "INDONESIA");
/*  238 */     territory.put("in", "INDONESIA");
/*  239 */     territory.put("is", "ICELAND");
/*  240 */     territory.put("it", "ITALY");
/*  241 */     territory.put("iw", "ISRAEL");
/*  242 */     territory.put("ja", "JAPAN");
/*  243 */     territory.put("kn", "INDIA");
/*  244 */     territory.put("ko", "KOREA");
/*  245 */     territory.put("lt", "LITHUANIA");
/*  246 */     territory.put("lv", "LATVIA");
/*  247 */     territory.put("ml", "INDIA");
/*  248 */     territory.put("mr", "INDIA");
/*  249 */     territory.put("ms", "MALAYSIA");
/*  250 */     territory.put("nl", "THE NETHERLANDS");
/*  251 */     territory.put("no", "NORWAY");
/*  252 */     territory.put("or", "INDIA");
/*  253 */     territory.put("pa", "INDIA");
/*  254 */     territory.put("pl", "POLAND");
/*  255 */     territory.put("pt", "PORTUGAL");
/*  256 */     territory.put("ro", "ROMANIA");
/*  257 */     territory.put("ru", "CIS");
/*  258 */     territory.put("sk", "SLOVAKIA");
/*  259 */     territory.put("sl", "SLOVENIA");
/*  260 */     territory.put("sv", "SWEDEN");
/*  261 */     territory.put("ta", "INDIA");
/*  262 */     territory.put("te", "INDIA");
/*  263 */     territory.put("th", "THAILAND");
/*  264 */     territory.put("tr", "TURKEY");
/*  265 */     territory.put("uk", "UKRAINE");
/*  266 */     territory.put("vi", "VIETNAM");
/*  267 */     territory.put("zh", "CHINA");
/*      */ 
/*  280 */     m_maxCharWidth = new short[][] { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 13, 1 }, { 14, 1 }, { 15, 1 }, { 16, 1 }, { 17, 1 }, { 18, 1 }, { 19, 1 }, { 20, 1 }, { 21, 1 }, { 22, 1 }, { 23, 1 }, { 25, 1 }, { 27, 1 }, { 28, 1 }, { 31, 1 }, { 32, 1 }, { 33, 1 }, { 34, 1 }, { 35, 1 }, { 36, 1 }, { 37, 1 }, { 38, 1 }, { 39, 1 }, { 40, 1 }, { 41, 1 }, { 42, 1 }, { 43, 1 }, { 44, 1 }, { 45, 1 }, { 46, 1 }, { 47, 1 }, { 48, 1 }, { 49, 1 }, { 50, 1 }, { 51, 1 }, { 61, 1 }, { 70, 1 }, { 72, 1 }, { 81, 1 }, { 82, 1 }, { 90, 1 }, { 91, 1 }, { 92, 1 }, { 93, 1 }, { 94, 1 }, { 95, 1 }, { 96, 1 }, { 97, 1 }, { 98, 1 }, { 99, 1 }, { 100, 1 }, { 101, 1 }, { 110, 1 }, { 113, 1 }, { 114, 1 }, { 140, 1 }, { 150, 1 }, { 152, 1 }, { 153, 1 }, { 154, 1 }, { 155, 1 }, { 156, 1 }, { 158, 1 }, { 159, 1 }, { 160, 1 }, { 161, 1 }, { 162, 1 }, { 163, 1 }, { 164, 1 }, { 165, 1 }, { 166, 1 }, { 167, 1 }, { 170, 1 }, { 171, 1 }, { 172, 1 }, { 173, 1 }, { 174, 1 }, { 175, 1 }, { 176, 1 }, { 177, 1 }, { 178, 1 }, { 179, 1 }, { 180, 1 }, { 181, 1 }, { 182, 1 }, { 183, 1 }, { 184, 1 }, { 185, 1 }, { 186, 1 }, { 187, 1 }, { 188, 1 }, { 189, 1 }, { 190, 1 }, { 191, 1 }, { 192, 1 }, { 193, 1 }, { 194, 1 }, { 195, 1 }, { 196, 1 }, { 197, 1 }, { 198, 1 }, { 199, 1 }, { 200, 1 }, { 201, 1 }, { 202, 1 }, { 203, 1 }, { 204, 1 }, { 205, 1 }, { 206, 1 }, { 207, 1 }, { 208, 1 }, { 210, 1 }, { 211, 1 }, { 221, 1 }, { 222, 1 }, { 223, 1 }, { 224, 1 }, { 225, 1 }, { 226, 1 }, { 230, 1 }, { 231, 1 }, { 232, 1 }, { 233, 1 }, { 235, 1 }, { 239, 1 }, { 241, 1 }, { 251, 1 }, { 261, 1 }, { 262, 1 }, { 263, 1 }, { 264, 1 }, { 265, 1 }, { 266, 1 }, { 267, 1 }, { 277, 1 }, { 278, 1 }, { 279, 1 }, { 301, 1 }, { 311, 1 }, { 312, 1 }, { 314, 1 }, { 315, 1 }, { 316, 1 }, { 317, 1 }, { 319, 1 }, { 320, 1 }, { 322, 1 }, { 323, 1 }, { 324, 1 }, { 351, 1 }, { 352, 1 }, { 353, 1 }, { 354, 1 }, { 368, 1 }, { 380, 1 }, { 381, 1 }, { 382, 1 }, { 383, 1 }, { 384, 1 }, { 385, 1 }, { 386, 1 }, { 390, 1 }, { 401, 1 }, { 500, 1 }, { 504, 1 }, { 505, 1 }, { 506, 1 }, { 507, 1 }, { 508, 1 }, { 509, 1 }, { 511, 1 }, { 514, 1 }, { 554, 1 }, { 555, 1 }, { 556, 1 }, { 557, 1 }, { 558, 1 }, { 559, 1 }, { 560, 1 }, { 561, 1 }, { 563, 1 }, { 565, 1 }, { 566, 1 }, { 567, 1 }, { 590, 1 }, { 798, 1 }, { 799, 258 }, { 829, 2 }, { 830, 3 }, { 831, 3 }, { 832, 2 }, { 833, 3 }, { 834, 2 }, { 835, 3 }, { 836, 2 }, { 837, 3 }, { 838, 2 }, { 840, 2 }, { 842, 3 }, { 845, 2 }, { 846, 2 }, { 850, 2 }, { 851, 2 }, { 852, 2 }, { 853, 3 }, { 854, 4 }, { 860, 4 }, { 861, 4 }, { 862, 2 }, { 863, 4 }, { 864, 3 }, { 865, 2 }, { 866, 2 }, { 867, 2 }, { 868, 2 }, { 870, 3 }, { 871, 3 }, { 872, 4 }, { 873, 4 }, { 994, 2 }, { 995, 2 }, { 996, 3 }, { 997, 2 }, { 998, 3 }, { 1001, 258 }, { 1830, 258 }, { 1832, 258 }, { 1833, 258 }, { 1840, 258 }, { 1842, 258 }, { 1850, 258 }, { 1852, 258 }, { 1853, 258 }, { 1860, 258 }, { 1863, 260 }, { 1864, 258 }, { 1865, 258 }, { 2000, 258 }, { 2002, 258 }, { 9996, 3 }, { 9997, 3 }, { 9998, 3 }, { 9999, 3 } };
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.converter.CharacterSetMetaData
 * JD-Core Version:    0.6.0
 */