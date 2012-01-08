/*      */ package oracle.sql;
/*      */ 
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class LxMetaData
/*      */ {
/*      */   private static final String DEFAULT_ES_ORA_LANGUAGE = "LATIN AMERICAN SPANISH";
/*      */   private static final int WIDTH_SIZE = 8;
/*      */   private static final short WIDTH_MASK = 255;
/*      */   public static final int ST_BADCODESET = 0;
/*  302 */   private static final Locale EN_LOCALE = new Locale("en", "US");
/*      */ 
/*  304 */   private static Map ORACLE_LANG_2_ISO_A2_LANG = null;
/*      */ 
/*  379 */   private static Map ORACLE_TERR_2_ISO_A2_TERR = null;
/*      */ 
/*  485 */   private static Map ORACLE_LANG_2_TERR = null;
/*      */ 
/*  558 */   private static Map ISO_A2_LANG_2_ORACLE_LANG = null;
/*      */ 
/*  623 */   private static Map ISO_LANGUAGE_DEFAULT_TERRITORY = null;
/*      */ 
/*  688 */   private static Map ISO_LOCALE_2_ORACLE_LOCALE = null;
/*      */ 
/*  706 */   private static Map ISO_A2_TERR_2_ORACLE_TERR = null;
/*      */ 
/*  808 */   private static Map CHARSET_RATIO = null;
/*      */ 
/*      */   static Locale getJavaLocale(String paramString1, String paramString2)
/*      */   {
/*   49 */     if (paramString1 == null)
/*      */     {
/*   51 */       return null;
/*      */     }
/*      */ 
/*   54 */     String str1 = paramString1;
/*      */ 
/*   56 */     String str2 = EN_LOCALE.getLanguage();
/*   57 */     if (!"".equals(str1))
/*      */     {
/*   59 */       if (ORACLE_LANG_2_ISO_A2_LANG == null)
/*   60 */         ORACLE_LANG_2_ISO_A2_LANG = getLang2IsoLangMap();
/*   61 */       str2 = (String)ORACLE_LANG_2_ISO_A2_LANG.get(str1.toUpperCase(Locale.US));
/*      */ 
/*   63 */       if (str2 == null)
/*      */       {
/*   65 */         return null;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*   70 */       str1 = "AMERICAN";
/*      */     }
/*      */ 
/*   73 */     String str3 = null;
/*   74 */     if ((paramString2 == null) || ((str3 = paramString2.toUpperCase(Locale.US)) == null) || ("".equals(str3)))
/*      */     {
/*   78 */       if (ORACLE_LANG_2_TERR == null)
/*   79 */         ORACLE_LANG_2_TERR = getLang2Terr();
/*   80 */       str3 = (String)ORACLE_LANG_2_TERR.get(str1);
/*      */     }
/*      */ 
/*   83 */     if (ORACLE_TERR_2_ISO_A2_TERR == null)
/*   84 */       ORACLE_TERR_2_ISO_A2_TERR = getTerr2IsoTerrMap();
/*   85 */     String str4 = (String)ORACLE_TERR_2_ISO_A2_TERR.get(str3);
/*   86 */     if (str4 == null)
/*      */     {
/*   88 */       return null;
/*      */     }
/*      */ 
/*   91 */     return new Locale(str2, str4);
/*      */   }
/*      */ 
/*      */   public static String getNLSLanguage(Locale paramLocale)
/*      */   {
/*  115 */     String str = getOraLocale(paramLocale);
/*  116 */     if (str == null)
/*  117 */       return null;
/*  118 */     int i = str.indexOf('_');
/*  119 */     return i < 0 ? str : str.substring(0, i);
/*      */   }
/*      */ 
/*      */   public static String getNLSTerritory(Locale paramLocale)
/*      */   {
/*  135 */     String str = getOraLocale(paramLocale);
/*  136 */     if (str == null)
/*  137 */       return null;
/*  138 */     int i = str.indexOf('_');
/*  139 */     return i < 0 ? null : str.substring(i + 1);
/*      */   }
/*      */ 
/*      */   private static String getOraLocale(Locale paramLocale)
/*      */   {
/*  154 */     if (paramLocale == null)
/*      */     {
/*  156 */       return null;
/*      */     }
/*      */ 
/*  160 */     String str1 = paramLocale.getLanguage().equals("") ? EN_LOCALE.getLanguage() : paramLocale.getLanguage();
/*      */ 
/*  163 */     if (ISO_A2_LANG_2_ORACLE_LANG == null) {
/*  164 */       ISO_A2_LANG_2_ORACLE_LANG = getIsoLangToOracleMap();
/*      */     }
/*      */ 
/*  167 */     String str2 = (String)ISO_A2_LANG_2_ORACLE_LANG.get(str1);
/*  168 */     if (str2 == null) {
/*  169 */       return null;
/*      */     }
/*      */ 
/*  172 */     String str3 = paramLocale.getCountry();
/*      */ 
/*  174 */     if (str3.equals(""))
/*      */     {
/*  176 */       if (ISO_LANGUAGE_DEFAULT_TERRITORY == null) {
/*  177 */         ISO_LANGUAGE_DEFAULT_TERRITORY = getIsoLangDefaultTerrMap();
/*      */       }
/*  179 */       str3 = (String)ISO_LANGUAGE_DEFAULT_TERRITORY.get(str1);
/*      */ 
/*  181 */       if (str3 == null) {
/*  182 */         return null;
/*      */       }
/*      */     }
/*  185 */     Locale localLocale = new Locale(str1, str3);
/*      */ 
/*  188 */     if (ISO_LOCALE_2_ORACLE_LOCALE == null) {
/*  189 */       ISO_LOCALE_2_ORACLE_LOCALE = getIsoLocToOracleMap();
/*      */     }
/*  191 */     String str4 = (String)ISO_LOCALE_2_ORACLE_LOCALE.get(localLocale.toString());
/*      */ 
/*  194 */     if (str4 != null)
/*      */     {
/*  196 */       return str4;
/*      */     }
/*      */ 
/*  199 */     if ("es".equals(str1))
/*      */     {
/*  202 */       str2 = "LATIN AMERICAN SPANISH";
/*      */     }
/*      */ 
/*  206 */     if (ISO_A2_TERR_2_ORACLE_TERR == null) {
/*  207 */       ISO_A2_TERR_2_ORACLE_TERR = getIsoTerrToOracleMap();
/*      */     }
/*  209 */     String str5 = (String)ISO_A2_TERR_2_ORACLE_TERR.get(str3);
/*  210 */     return str5 != null ? str2 + "_" + str5 : null;
/*      */   }
/*      */ 
/*      */   public static int getRatio(int paramInt1, int paramInt2)
/*      */   {
/*  251 */     if (paramInt2 == paramInt1)
/*      */     {
/*  253 */       return 1;
/*      */     }
/*      */ 
/*  256 */     if (CHARSET_RATIO == null)
/*      */     {
/*  258 */       CHARSET_RATIO = getCharsetRatio();
/*      */     }
/*      */ 
/*  261 */     Object localObject = CHARSET_RATIO.get(Integer.toString(paramInt1));
/*  262 */     if (localObject == null) {
/*  263 */       return 0;
/*      */     }
/*  265 */     int i = Integer.parseInt((String)localObject);
/*      */ 
/*  268 */     localObject = CHARSET_RATIO.get(Integer.toString(paramInt2));
/*  269 */     if (localObject == null) {
/*  270 */       return 0;
/*      */     }
/*  272 */     int j = Integer.parseInt((String)localObject);
/*      */ 
/*  274 */     int k = i & 0xFF;
/*      */ 
/*  276 */     if (k == 1)
/*      */     {
/*  278 */       return 1;
/*      */     }
/*      */ 
/*  281 */     if (j >>> 8 == 0)
/*      */     {
/*  284 */       return k;
/*      */     }
/*      */ 
/*  291 */     int m = j & 0xFF;
/*  292 */     int n = k / m;
/*      */ 
/*  294 */     if (k % m != 0)
/*      */     {
/*  296 */       n++;
/*      */     }
/*      */ 
/*  299 */     return n;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getLang2IsoLangMap()
/*      */   {
/*  307 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  309 */     localHashMap.put("ALBANIAN", "sq");
/*  310 */     localHashMap.put("AMERICAN", "en");
/*  311 */     localHashMap.put("ARABIC", "ar");
/*  312 */     localHashMap.put("ASSAMESE", "as");
/*  313 */     localHashMap.put("AZERBAIJANI", "az");
/*  314 */     localHashMap.put("BANGLA", "bn");
/*  315 */     localHashMap.put("BENGALI", "bn");
/*  316 */     localHashMap.put("BRAZILIAN PORTUGUESE", "pt");
/*  317 */     localHashMap.put("BULGARIAN", "bg");
/*  318 */     localHashMap.put("CANADIAN FRENCH", "fr");
/*  319 */     localHashMap.put("CATALAN", "ca");
/*  320 */     localHashMap.put("CROATIAN", "hr");
/*  321 */     localHashMap.put("CYRILLIC KAZAKH", "kk");
/*  322 */     localHashMap.put("CYRILLIC SERBIAN", "sr");
/*  323 */     localHashMap.put("CYRILLIC UZBEK", "uz");
/*  324 */     localHashMap.put("CZECH", "cs");
/*  325 */     localHashMap.put("DANISH", "da");
/*  326 */     localHashMap.put("DUTCH", "nl");
/*  327 */     localHashMap.put("EGYPTIAN", "ar");
/*  328 */     localHashMap.put("ENGLISH", "en");
/*  329 */     localHashMap.put("ESTONIAN", "et");
/*  330 */     localHashMap.put("FINNISH", "fi");
/*  331 */     localHashMap.put("FRENCH", "fr");
/*  332 */     localHashMap.put("GERMAN", "de");
/*  333 */     localHashMap.put("GERMAN DIN", "de");
/*  334 */     localHashMap.put("GREEK", "el");
/*  335 */     localHashMap.put("GUJARATI", "gu");
/*  336 */     localHashMap.put("HEBREW", "iw");
/*  337 */     localHashMap.put("HINDI", "hi");
/*  338 */     localHashMap.put("HUNGARIAN", "hu");
/*  339 */     localHashMap.put("ICELANDIC", "is");
/*  340 */     localHashMap.put("INDONESIAN", "in");
/*  341 */     localHashMap.put("ITALIAN", "it");
/*  342 */     localHashMap.put("JAPANESE", "ja");
/*  343 */     localHashMap.put("KANNADA", "kn");
/*  344 */     localHashMap.put("KOREAN", "ko");
/*  345 */     localHashMap.put("LATIN AMERICAN SPANISH", "es");
/*  346 */     localHashMap.put("LATIN SERBIAN", "sr");
/*  347 */     localHashMap.put("LATIN UZBEK", "uz");
/*  348 */     localHashMap.put("LATVIAN", "lv");
/*  349 */     localHashMap.put("LITHUANIAN", "lt");
/*  350 */     localHashMap.put("MACEDONIAN", "mk");
/*  351 */     localHashMap.put("MALAY", "ms");
/*  352 */     localHashMap.put("MALAYALAM", "ml");
/*  353 */     localHashMap.put("MARATHI", "mr");
/*  354 */     localHashMap.put("MEXICAN SPANISH", "es");
/*  355 */     localHashMap.put("NORWEGIAN", "no");
/*  356 */     localHashMap.put("NUMERIC DATE LANGUAGE", "en");
/*  357 */     localHashMap.put("ORIYA", "or");
/*  358 */     localHashMap.put("POLISH", "pl");
/*  359 */     localHashMap.put("PORTUGUESE", "pt");
/*  360 */     localHashMap.put("PUNJABI", "pa");
/*  361 */     localHashMap.put("ROMANIAN", "ro");
/*  362 */     localHashMap.put("RUSSIAN", "ru");
/*  363 */     localHashMap.put("SIMPLIFIED CHINESE", "zh");
/*  364 */     localHashMap.put("SLOVAK", "sk");
/*  365 */     localHashMap.put("SLOVENIAN", "sl");
/*  366 */     localHashMap.put("SPANISH", "es");
/*  367 */     localHashMap.put("SWEDISH", "sv");
/*  368 */     localHashMap.put("TAMIL", "ta");
/*  369 */     localHashMap.put("TELUGU", "te");
/*  370 */     localHashMap.put("THAI", "th");
/*  371 */     localHashMap.put("TRADITIONAL CHINESE", "zh");
/*  372 */     localHashMap.put("TURKISH", "tr");
/*  373 */     localHashMap.put("UKRAINIAN", "uk");
/*  374 */     localHashMap.put("VIETNAMESE", "vi");
/*      */ 
/*  376 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getTerr2IsoTerrMap()
/*      */   {
/*  382 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  384 */     localHashMap.put("ALBANIA", "AL");
/*  385 */     localHashMap.put("ALGERIA", "DZ");
/*  386 */     localHashMap.put("AMERICA", "US");
/*  387 */     localHashMap.put("ARGENTINA", "AR");
/*  388 */     localHashMap.put("AUSTRALIA", "AU");
/*  389 */     localHashMap.put("AUSTRIA", "AT");
/*  390 */     localHashMap.put("AZERBAIJAN", "AZ");
/*  391 */     localHashMap.put("BAHRAIN", "BH");
/*  392 */     localHashMap.put("BANGLADESH", "BD");
/*  393 */     localHashMap.put("BELGIUM", "BE");
/*  394 */     localHashMap.put("BRAZIL", "BR");
/*  395 */     localHashMap.put("BULGARIA", "BG");
/*  396 */     localHashMap.put("CANADA", "CA");
/*  397 */     localHashMap.put("CATALONIA", "ES");
/*  398 */     localHashMap.put("CHILE", "CL");
/*  399 */     localHashMap.put("CHINA", "CN");
/*  400 */     localHashMap.put("CIS", "RU");
/*  401 */     localHashMap.put("COLOMBIA", "CO");
/*  402 */     localHashMap.put("COSTA RICA", "CR");
/*  403 */     localHashMap.put("CROATIA", "HR");
/*  404 */     localHashMap.put("CYPRUS", "CY");
/*  405 */     localHashMap.put("CZECH REPUBLIC", "CZ");
/*  406 */     localHashMap.put("CZECHOSLOVAKIA", "CZ");
/*  407 */     localHashMap.put("DENMARK", "DK");
/*  408 */     localHashMap.put("DJIBOUTI", "DJ");
/*  409 */     localHashMap.put("ECUADOR", "EC");
/*  410 */     localHashMap.put("EGYPT", "EG");
/*  411 */     localHashMap.put("EL SALVADOR", "SV");
/*  412 */     localHashMap.put("ESTONIA", "EE");
/*  413 */     localHashMap.put("FINLAND", "FI");
/*  414 */     localHashMap.put("FRANCE", "FR");
/*  415 */     localHashMap.put("FYR MACEDONIA", "MK");
/*  416 */     localHashMap.put("GERMANY", "DE");
/*  417 */     localHashMap.put("GREECE", "GR");
/*  418 */     localHashMap.put("GUATEMALA", "GT");
/*  419 */     localHashMap.put("HONG KONG", "HK");
/*  420 */     localHashMap.put("HUNGARY", "HU");
/*  421 */     localHashMap.put("ICELAND", "IS");
/*  422 */     localHashMap.put("INDIA", "IN");
/*  423 */     localHashMap.put("INDONESIA", "ID");
/*  424 */     localHashMap.put("IRAQ", "IQ");
/*  425 */     localHashMap.put("IRELAND", "IE");
/*  426 */     localHashMap.put("ISRAEL", "IL");
/*  427 */     localHashMap.put("ITALY", "IT");
/*  428 */     localHashMap.put("JAPAN", "JP");
/*  429 */     localHashMap.put("JORDAN", "JO");
/*  430 */     localHashMap.put("KAZAKHSTAN", "KZ");
/*  431 */     localHashMap.put("KOREA", "KR");
/*  432 */     localHashMap.put("KUWAIT", "KW");
/*  433 */     localHashMap.put("LATVIA", "LV");
/*  434 */     localHashMap.put("LEBANON", "LB");
/*  435 */     localHashMap.put("LIBYA", "LY");
/*  436 */     localHashMap.put("LITHUANIA", "LT");
/*  437 */     localHashMap.put("LUXEMBOURG", "LU");
/*  438 */     localHashMap.put("MACEDONIA", "MK");
/*  439 */     localHashMap.put("MALAYSIA", "MY");
/*  440 */     localHashMap.put("MAURITANIA", "MR");
/*  441 */     localHashMap.put("MEXICO", "MX");
/*  442 */     localHashMap.put("MOROCCO", "MA");
/*  443 */     localHashMap.put("NEW ZEALAND", "NZ");
/*  444 */     localHashMap.put("NICARAGUA", "NI");
/*  445 */     localHashMap.put("NORWAY", "NO");
/*  446 */     localHashMap.put("OMAN", "OM");
/*  447 */     localHashMap.put("PANAMA", "PA");
/*  448 */     localHashMap.put("PERU", "PE");
/*  449 */     localHashMap.put("PHILIPPINES", "PH");
/*  450 */     localHashMap.put("POLAND", "PL");
/*  451 */     localHashMap.put("PORTUGAL", "PT");
/*  452 */     localHashMap.put("PUERTO RICO", "PR");
/*  453 */     localHashMap.put("QATAR", "QA");
/*  454 */     localHashMap.put("ROMANIA", "RO");
/*  455 */     localHashMap.put("RUSSIA", "RU");
/*  456 */     localHashMap.put("SAUDI ARABIA", "SA");
/*  457 */     localHashMap.put("SERBIA AND MONTENEGRO", "CS");
/*  458 */     localHashMap.put("SINGAPORE", "SG");
/*  459 */     localHashMap.put("SLOVAKIA", "SK");
/*  460 */     localHashMap.put("SLOVENIA", "SI");
/*  461 */     localHashMap.put("SOMALIA", "SO");
/*  462 */     localHashMap.put("SOUTH AFRICA", "ZA");
/*  463 */     localHashMap.put("SPAIN", "ES");
/*  464 */     localHashMap.put("SUDAN", "SD");
/*  465 */     localHashMap.put("SWEDEN", "SE");
/*  466 */     localHashMap.put("SWITZERLAND", "CH");
/*  467 */     localHashMap.put("SYRIA", "SY");
/*  468 */     localHashMap.put("TAIWAN", "TW");
/*  469 */     localHashMap.put("THAILAND", "TH");
/*  470 */     localHashMap.put("THE NETHERLANDS", "NL");
/*  471 */     localHashMap.put("TUNISIA", "TN");
/*  472 */     localHashMap.put("TURKEY", "TR");
/*  473 */     localHashMap.put("UKRAINE", "UA");
/*  474 */     localHashMap.put("UNITED ARAB EMIRATES", "AE");
/*  475 */     localHashMap.put("UNITED KINGDOM", "GB");
/*  476 */     localHashMap.put("UZBEKISTAN", "UZ");
/*  477 */     localHashMap.put("VENEZUELA", "VE");
/*  478 */     localHashMap.put("VIETNAM", "VN");
/*  479 */     localHashMap.put("YEMEN", "YE");
/*  480 */     localHashMap.put("YUGOSLAVIA", "YU");
/*      */ 
/*  482 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getLang2Terr()
/*      */   {
/*  488 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  490 */     localHashMap.put("ALBANIAN", "ALBANIA");
/*  491 */     localHashMap.put("AMERICAN", "AMERICA");
/*  492 */     localHashMap.put("ARABIC", "UNITED ARAB EMIRATES");
/*  493 */     localHashMap.put("ASSAMESE", "INDIA");
/*  494 */     localHashMap.put("AZERBAIJANI", "AZERBAIJAN");
/*  495 */     localHashMap.put("BANGLA", "INDIA");
/*  496 */     localHashMap.put("BRAZILIAN PORTUGUESE", "BRAZIL");
/*  497 */     localHashMap.put("BULGARIAN", "BULGARIA");
/*  498 */     localHashMap.put("CANADIAN FRENCH", "CANADA");
/*  499 */     localHashMap.put("CATALAN", "CATALONIA");
/*  500 */     localHashMap.put("CROATIAN", "CROATIA");
/*  501 */     localHashMap.put("CYRILLIC KAZAKH", "KAZAKHSTAN");
/*  502 */     localHashMap.put("CYRILLIC SERBIAN", "SERBIA AND MONTENEGRO");
/*  503 */     localHashMap.put("CYRILLIC UZBEK", "UZBEKISTAN");
/*  504 */     localHashMap.put("CZECH", "CZECH REPUBLIC");
/*  505 */     localHashMap.put("DANISH", "DENMARK");
/*  506 */     localHashMap.put("DUTCH", "THE NETHERLANDS");
/*  507 */     localHashMap.put("EGYPTIAN", "EGYPT");
/*  508 */     localHashMap.put("ENGLISH", "UNITED KINGDOM");
/*  509 */     localHashMap.put("ESTONIAN", "ESTONIA");
/*  510 */     localHashMap.put("FINNISH", "FINLAND");
/*  511 */     localHashMap.put("FRENCH", "FRANCE");
/*  512 */     localHashMap.put("GERMAN", "GERMANY");
/*  513 */     localHashMap.put("GERMAN DIN", "GERMANY");
/*  514 */     localHashMap.put("GREEK", "GREECE");
/*  515 */     localHashMap.put("GUJARATI", "INDIA");
/*  516 */     localHashMap.put("HEBREW", "ISRAEL");
/*  517 */     localHashMap.put("HINDI", "INDIA");
/*  518 */     localHashMap.put("HUNGARIAN", "HUNGARY");
/*  519 */     localHashMap.put("ICELANDIC", "ICELAND");
/*  520 */     localHashMap.put("INDONESIAN", "INDONESIA");
/*  521 */     localHashMap.put("ITALIAN", "ITALY");
/*  522 */     localHashMap.put("JAPANESE", "JAPAN");
/*  523 */     localHashMap.put("KANNADA", "INDIA");
/*  524 */     localHashMap.put("KOREAN", "KOREA");
/*  525 */     localHashMap.put("LATIN AMERICAN SPANISH", "AMERICA");
/*  526 */     localHashMap.put("LATIN SERBIAN", "SERBIA AND MONTENEGRO");
/*  527 */     localHashMap.put("LATIN UZBEK", "UZBEKISTAN");
/*  528 */     localHashMap.put("LATVIAN", "LATVIA");
/*  529 */     localHashMap.put("LITHUANIAN", "LITHUANIA");
/*  530 */     localHashMap.put("MACEDONIAN", "FYR MACEDONIA");
/*  531 */     localHashMap.put("MALAY", "MALAYSIA");
/*  532 */     localHashMap.put("MALAYALAM", "INDIA");
/*  533 */     localHashMap.put("MARATHI", "INDIA");
/*  534 */     localHashMap.put("MEXICAN SPANISH", "MEXICO");
/*  535 */     localHashMap.put("NORWEGIAN", "NORWAY");
/*  536 */     localHashMap.put("ORIYA", "INDIA");
/*  537 */     localHashMap.put("POLISH", "POLAND");
/*  538 */     localHashMap.put("PORTUGUESE", "PORTUGAL");
/*  539 */     localHashMap.put("PUNJABI", "INDIA");
/*  540 */     localHashMap.put("ROMANIAN", "ROMANIA");
/*  541 */     localHashMap.put("RUSSIAN", "RUSSIA");
/*  542 */     localHashMap.put("SIMPLIFIED CHINESE", "CHINA");
/*  543 */     localHashMap.put("SLOVAK", "SLOVAKIA");
/*  544 */     localHashMap.put("SLOVENIAN", "SLOVENIA");
/*  545 */     localHashMap.put("SPANISH", "SPAIN");
/*  546 */     localHashMap.put("SWEDISH", "SWEDEN");
/*  547 */     localHashMap.put("TAMIL", "INDIA");
/*  548 */     localHashMap.put("TELUGU", "INDIA");
/*  549 */     localHashMap.put("THAI", "THAILAND");
/*  550 */     localHashMap.put("TRADITIONAL CHINESE", "TAIWAN");
/*  551 */     localHashMap.put("TURKISH", "TURKEY");
/*  552 */     localHashMap.put("UKRAINIAN", "UKRAINE");
/*  553 */     localHashMap.put("VIETNAMESE", "VIETNAM");
/*      */ 
/*  555 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getIsoLangToOracleMap()
/*      */   {
/*  561 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  563 */     localHashMap.put("ar", "ARABIC");
/*  564 */     localHashMap.put("as", "ASSAMESE");
/*  565 */     localHashMap.put("az", "AZERBAIJANI");
/*  566 */     localHashMap.put("bg", "BULGARIAN");
/*  567 */     localHashMap.put("bn", "BANGLA");
/*  568 */     localHashMap.put("ca", "CATALAN");
/*  569 */     localHashMap.put("cs", "CZECH");
/*  570 */     localHashMap.put("da", "DANISH");
/*  571 */     localHashMap.put("de", "GERMAN");
/*  572 */     localHashMap.put("el", "GREEK");
/*  573 */     localHashMap.put("en", "ENGLISH");
/*  574 */     localHashMap.put("es", "SPANISH");
/*  575 */     localHashMap.put("et", "ESTONIAN");
/*  576 */     localHashMap.put("fi", "FINNISH");
/*  577 */     localHashMap.put("fr", "FRENCH");
/*  578 */     localHashMap.put("gu", "GUJARATI");
/*  579 */     localHashMap.put("he", "HEBREW");
/*  580 */     localHashMap.put("hi", "HINDI");
/*  581 */     localHashMap.put("hr", "CROATIAN");
/*  582 */     localHashMap.put("hu", "HUNGARIAN");
/*  583 */     localHashMap.put("id", "INDONESIAN");
/*  584 */     localHashMap.put("in", "INDONESIAN");
/*  585 */     localHashMap.put("is", "ICELANDIC");
/*  586 */     localHashMap.put("it", "ITALIAN");
/*  587 */     localHashMap.put("iw", "HEBREW");
/*  588 */     localHashMap.put("ja", "JAPANESE");
/*  589 */     localHashMap.put("kk", "CYRILLIC KAZAKH");
/*  590 */     localHashMap.put("kn", "KANNADA");
/*  591 */     localHashMap.put("ko", "KOREAN");
/*  592 */     localHashMap.put("lt", "LITHUANIAN");
/*  593 */     localHashMap.put("lv", "LATVIAN");
/*  594 */     localHashMap.put("mk", "MACEDONIAN");
/*  595 */     localHashMap.put("ml", "MALAYALAM");
/*  596 */     localHashMap.put("mr", "MARATHI");
/*  597 */     localHashMap.put("ms", "MALAY");
/*  598 */     localHashMap.put("nl", "DUTCH");
/*  599 */     localHashMap.put("no", "NORWEGIAN");
/*  600 */     localHashMap.put("or", "ORIYA");
/*  601 */     localHashMap.put("pa", "PUNJABI");
/*  602 */     localHashMap.put("pl", "POLISH");
/*  603 */     localHashMap.put("pt", "PORTUGUESE");
/*  604 */     localHashMap.put("ro", "ROMANIAN");
/*  605 */     localHashMap.put("ru", "RUSSIAN");
/*  606 */     localHashMap.put("sk", "SLOVAK");
/*  607 */     localHashMap.put("sl", "SLOVENIAN");
/*  608 */     localHashMap.put("sq", "ALBANIAN");
/*  609 */     localHashMap.put("sr", "CYRILLIC SERBIAN");
/*  610 */     localHashMap.put("sv", "SWEDISH");
/*  611 */     localHashMap.put("ta", "TAMIL");
/*  612 */     localHashMap.put("te", "TELUGU");
/*  613 */     localHashMap.put("th", "THAI");
/*  614 */     localHashMap.put("tr", "TURKISH");
/*  615 */     localHashMap.put("uk", "UKRAINIAN");
/*  616 */     localHashMap.put("uz", "LATIN UZBEK");
/*  617 */     localHashMap.put("vi", "VIETNAMESE");
/*  618 */     localHashMap.put("zh", "SIMPLIFIED CHINESE");
/*      */ 
/*  620 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getIsoLangDefaultTerrMap()
/*      */   {
/*  626 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  628 */     localHashMap.put("ar", "AE");
/*  629 */     localHashMap.put("as", "IN");
/*  630 */     localHashMap.put("az", "AZ");
/*  631 */     localHashMap.put("bg", "BG");
/*  632 */     localHashMap.put("bn", "BD");
/*  633 */     localHashMap.put("ca", "ES");
/*  634 */     localHashMap.put("cs", "CZ");
/*  635 */     localHashMap.put("da", "DK");
/*  636 */     localHashMap.put("de", "DE");
/*  637 */     localHashMap.put("el", "GR");
/*  638 */     localHashMap.put("en", "US");
/*  639 */     localHashMap.put("es", "ES");
/*  640 */     localHashMap.put("et", "EE");
/*  641 */     localHashMap.put("fi", "FI");
/*  642 */     localHashMap.put("fr", "FR");
/*  643 */     localHashMap.put("gu", "IN");
/*  644 */     localHashMap.put("he", "IL");
/*  645 */     localHashMap.put("hi", "IN");
/*  646 */     localHashMap.put("hr", "HR");
/*  647 */     localHashMap.put("hu", "HU");
/*  648 */     localHashMap.put("id", "ID");
/*  649 */     localHashMap.put("in", "ID");
/*  650 */     localHashMap.put("is", "IS");
/*  651 */     localHashMap.put("it", "IT");
/*  652 */     localHashMap.put("iw", "IL");
/*  653 */     localHashMap.put("ja", "JP");
/*  654 */     localHashMap.put("kk", "KZ");
/*  655 */     localHashMap.put("kn", "IN");
/*  656 */     localHashMap.put("ko", "KR");
/*  657 */     localHashMap.put("lt", "LT");
/*  658 */     localHashMap.put("lv", "LV");
/*  659 */     localHashMap.put("mk", "MK");
/*  660 */     localHashMap.put("ml", "IN");
/*  661 */     localHashMap.put("mr", "IN");
/*  662 */     localHashMap.put("ms", "MY");
/*  663 */     localHashMap.put("nl", "NL");
/*  664 */     localHashMap.put("no", "NO");
/*  665 */     localHashMap.put("or", "IN");
/*  666 */     localHashMap.put("pa", "IN");
/*  667 */     localHashMap.put("pl", "PL");
/*  668 */     localHashMap.put("pt", "PT");
/*  669 */     localHashMap.put("ro", "RO");
/*  670 */     localHashMap.put("ru", "RU");
/*  671 */     localHashMap.put("sk", "SK");
/*  672 */     localHashMap.put("sl", "SI");
/*  673 */     localHashMap.put("sq", "AL");
/*  674 */     localHashMap.put("sr", "CS");
/*  675 */     localHashMap.put("sv", "SE");
/*  676 */     localHashMap.put("ta", "IN");
/*  677 */     localHashMap.put("te", "IN");
/*  678 */     localHashMap.put("th", "TH");
/*  679 */     localHashMap.put("tr", "TR");
/*  680 */     localHashMap.put("uk", "UA");
/*  681 */     localHashMap.put("uz", "UZ");
/*  682 */     localHashMap.put("vi", "VN");
/*  683 */     localHashMap.put("zh", "CN");
/*      */ 
/*  685 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getIsoLocToOracleMap()
/*      */   {
/*  691 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  693 */     localHashMap.put("ar_EG", "EGYPTIAN_EGYPT");
/*  694 */     localHashMap.put("ca_ES", "CATALAN_CATALONIA");
/*  695 */     localHashMap.put("en_US", "AMERICAN_AMERICA");
/*  696 */     localHashMap.put("es_ES", "SPANISH_SPAIN");
/*  697 */     localHashMap.put("es_MX", "MEXICAN SPANISH_MEXICO");
/*  698 */     localHashMap.put("fr_CA", "CANADIAN FRENCH_CANADA");
/*  699 */     localHashMap.put("pt_BR", "BRAZILIAN PORTUGUESE_BRAZIL");
/*  700 */     localHashMap.put("zh_HK", "TRADITIONAL CHINESE_HONG KONG");
/*  701 */     localHashMap.put("zh_TW", "TRADITIONAL CHINESE_TAIWAN");
/*      */ 
/*  703 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getIsoTerrToOracleMap()
/*      */   {
/*  709 */     HashMap localHashMap = new HashMap();
/*      */ 
/*  711 */     localHashMap.put("AE", "UNITED ARAB EMIRATES");
/*  712 */     localHashMap.put("AL", "ALBANIA");
/*  713 */     localHashMap.put("AR", "ARGENTINA");
/*  714 */     localHashMap.put("AT", "AUSTRIA");
/*  715 */     localHashMap.put("AU", "AUSTRALIA");
/*  716 */     localHashMap.put("AZ", "AZERBAIJAN");
/*  717 */     localHashMap.put("BD", "BANGLADESH");
/*  718 */     localHashMap.put("BE", "BELGIUM");
/*  719 */     localHashMap.put("BG", "BULGARIA");
/*  720 */     localHashMap.put("BH", "BAHRAIN");
/*  721 */     localHashMap.put("BR", "BRAZIL");
/*  722 */     localHashMap.put("CA", "CANADA");
/*  723 */     localHashMap.put("CH", "SWITZERLAND");
/*  724 */     localHashMap.put("CL", "CHILE");
/*  725 */     localHashMap.put("CN", "CHINA");
/*  726 */     localHashMap.put("CO", "COLOMBIA");
/*  727 */     localHashMap.put("CR", "COSTA RICA");
/*  728 */     localHashMap.put("CS", "SERBIA AND MONTENEGRO");
/*  729 */     localHashMap.put("CY", "CYPRUS");
/*  730 */     localHashMap.put("CZ", "CZECH REPUBLIC");
/*  731 */     localHashMap.put("DE", "GERMANY");
/*  732 */     localHashMap.put("DJ", "DJIBOUTI");
/*  733 */     localHashMap.put("DK", "DENMARK");
/*  734 */     localHashMap.put("DZ", "ALGERIA");
/*  735 */     localHashMap.put("EC", "ECUADOR");
/*  736 */     localHashMap.put("EE", "ESTONIA");
/*  737 */     localHashMap.put("EG", "EGYPT");
/*  738 */     localHashMap.put("ES", "SPAIN");
/*  739 */     localHashMap.put("FI", "FINLAND");
/*  740 */     localHashMap.put("FR", "FRANCE");
/*  741 */     localHashMap.put("GB", "UNITED KINGDOM");
/*  742 */     localHashMap.put("GR", "GREECE");
/*  743 */     localHashMap.put("GT", "GUATEMALA");
/*  744 */     localHashMap.put("HK", "HONG KONG");
/*  745 */     localHashMap.put("HR", "CROATIA");
/*  746 */     localHashMap.put("HU", "HUNGARY");
/*  747 */     localHashMap.put("ID", "INDONESIA");
/*  748 */     localHashMap.put("IE", "IRELAND");
/*  749 */     localHashMap.put("IL", "ISRAEL");
/*  750 */     localHashMap.put("IN", "INDIA");
/*  751 */     localHashMap.put("IQ", "IRAQ");
/*  752 */     localHashMap.put("IS", "ICELAND");
/*  753 */     localHashMap.put("IT", "ITALY");
/*  754 */     localHashMap.put("JO", "JORDAN");
/*  755 */     localHashMap.put("JP", "JAPAN");
/*  756 */     localHashMap.put("KR", "KOREA");
/*  757 */     localHashMap.put("KW", "KUWAIT");
/*  758 */     localHashMap.put("KZ", "KAZAKHSTAN");
/*  759 */     localHashMap.put("LB", "LEBANON");
/*  760 */     localHashMap.put("LT", "LITHUANIA");
/*  761 */     localHashMap.put("LU", "LUXEMBOURG");
/*  762 */     localHashMap.put("LV", "LATVIA");
/*  763 */     localHashMap.put("LY", "LIBYA");
/*  764 */     localHashMap.put("MA", "MOROCCO");
/*  765 */     localHashMap.put("MK", "FYR MACEDONIA");
/*  766 */     localHashMap.put("MR", "MAURITANIA");
/*  767 */     localHashMap.put("MX", "MEXICO");
/*  768 */     localHashMap.put("MY", "MALAYSIA");
/*  769 */     localHashMap.put("NI", "NICARAGUA");
/*  770 */     localHashMap.put("NL", "THE NETHERLANDS");
/*  771 */     localHashMap.put("NO", "NORWAY");
/*  772 */     localHashMap.put("NZ", "NEW ZEALAND");
/*  773 */     localHashMap.put("OM", "OMAN");
/*  774 */     localHashMap.put("PA", "PANAMA");
/*  775 */     localHashMap.put("PE", "PERU");
/*  776 */     localHashMap.put("PH", "PHILIPPINES");
/*  777 */     localHashMap.put("PL", "POLAND");
/*  778 */     localHashMap.put("PR", "PUERTO RICO");
/*  779 */     localHashMap.put("PT", "PORTUGAL");
/*  780 */     localHashMap.put("QA", "QATAR");
/*  781 */     localHashMap.put("RO", "ROMANIA");
/*  782 */     localHashMap.put("RU", "RUSSIA");
/*  783 */     localHashMap.put("SA", "SAUDI ARABIA");
/*  784 */     localHashMap.put("SD", "SUDAN");
/*  785 */     localHashMap.put("SE", "SWEDEN");
/*  786 */     localHashMap.put("SG", "SINGAPORE");
/*  787 */     localHashMap.put("SI", "SLOVENIA");
/*  788 */     localHashMap.put("SK", "SLOVAKIA");
/*  789 */     localHashMap.put("SO", "SOMALIA");
/*  790 */     localHashMap.put("SV", "EL SALVADOR");
/*  791 */     localHashMap.put("SY", "SYRIA");
/*  792 */     localHashMap.put("TH", "THAILAND");
/*  793 */     localHashMap.put("TN", "TUNISIA");
/*  794 */     localHashMap.put("TR", "TURKEY");
/*  795 */     localHashMap.put("TW", "TAIWAN");
/*  796 */     localHashMap.put("UA", "UKRAINE");
/*  797 */     localHashMap.put("US", "AMERICA");
/*  798 */     localHashMap.put("UZ", "UZBEKISTAN");
/*  799 */     localHashMap.put("VE", "VENEZUELA");
/*  800 */     localHashMap.put("VN", "VIETNAM");
/*  801 */     localHashMap.put("YE", "YEMEN");
/*  802 */     localHashMap.put("YU", "YUGOSLAVIA");
/*  803 */     localHashMap.put("ZA", "SOUTH AFRICA");
/*      */ 
/*  805 */     return localHashMap;
/*      */   }
/*      */ 
/*      */   private static synchronized Map getCharsetRatio()
/*      */   {
/*  811 */     HashMap localHashMap = new HashMap();
/*  812 */     localHashMap.put("2000", "258");
/*  813 */     localHashMap.put("873", "4");
/*  814 */     localHashMap.put("557", "1");
/*  815 */     localHashMap.put("507", "1");
/*  816 */     localHashMap.put("558", "1");
/*  817 */     localHashMap.put("508", "1");
/*  818 */     localHashMap.put("559", "1");
/*  819 */     localHashMap.put("509", "1");
/*  820 */     localHashMap.put("565", "1");
/*  821 */     localHashMap.put("566", "1");
/*  822 */     localHashMap.put("567", "1");
/*  823 */     localHashMap.put("61", "1");
/*  824 */     localHashMap.put("500", "1");
/*  825 */     localHashMap.put("320", "1");
/*  826 */     localHashMap.put("70", "1");
/*  827 */     localHashMap.put("514", "1");
/*  828 */     localHashMap.put("36", "1");
/*  829 */     localHashMap.put("560", "1");
/*  830 */     localHashMap.put("556", "1");
/*  831 */     localHashMap.put("506", "1");
/*  832 */     localHashMap.put("554", "1");
/*  833 */     localHashMap.put("504", "1");
/*  834 */     localHashMap.put("561", "1");
/*  835 */     localHashMap.put("511", "1");
/*  836 */     localHashMap.put("563", "1");
/*  837 */     localHashMap.put("555", "1");
/*  838 */     localHashMap.put("505", "1");
/*  839 */     localHashMap.put("72", "1");
/*  840 */     localHashMap.put("52", "1");
/*  841 */     localHashMap.put("173", "1");
/*  842 */     localHashMap.put("140", "1");
/*  843 */     localHashMap.put("191", "1");
/*  844 */     localHashMap.put("194", "1");
/*  845 */     localHashMap.put("314", "1");
/*  846 */     localHashMap.put("47", "1");
/*  847 */     localHashMap.put("179", "1");
/*  848 */     localHashMap.put("197", "1");
/*  849 */     localHashMap.put("43", "1");
/*  850 */     localHashMap.put("390", "1");
/*  851 */     localHashMap.put("233", "1");
/*  852 */     localHashMap.put("48", "1");
/*  853 */     localHashMap.put("19", "1");
/*  854 */     localHashMap.put("235", "1");
/*  855 */     localHashMap.put("185", "1");
/*  856 */     localHashMap.put("322", "1");
/*  857 */     localHashMap.put("323", "1");
/*  858 */     localHashMap.put("317", "1");
/*  859 */     localHashMap.put("188", "1");
/*  860 */     localHashMap.put("325", "1");
/*  861 */     localHashMap.put("326", "1");
/*  862 */     localHashMap.put("35", "1");
/*  863 */     localHashMap.put("49", "1");
/*  864 */     localHashMap.put("196", "1");
/*  865 */     localHashMap.put("51", "1");
/*  866 */     localHashMap.put("158", "1");
/*  867 */     localHashMap.put("159", "1");
/*  868 */     localHashMap.put("171", "1");
/*  869 */     localHashMap.put("11", "1");
/*  870 */     localHashMap.put("207", "1");
/*  871 */     localHashMap.put("222", "1");
/*  872 */     localHashMap.put("189", "1");
/*  873 */     localHashMap.put("180", "1");
/*  874 */     localHashMap.put("204", "1");
/*  875 */     localHashMap.put("225", "1");
/*  876 */     localHashMap.put("198", "1");
/*  877 */     localHashMap.put("182", "1");
/*  878 */     localHashMap.put("14", "1");
/*  879 */     localHashMap.put("202", "1");
/*  880 */     localHashMap.put("224", "1");
/*  881 */     localHashMap.put("232", "1");
/*  882 */     localHashMap.put("184", "1");
/*  883 */     localHashMap.put("301", "1");
/*  884 */     localHashMap.put("316", "1");
/*  885 */     localHashMap.put("32", "1");
/*  886 */     localHashMap.put("262", "1");
/*  887 */     localHashMap.put("162", "1");
/*  888 */     localHashMap.put("263", "1");
/*  889 */     localHashMap.put("163", "1");
/*  890 */     localHashMap.put("170", "1");
/*  891 */     localHashMap.put("150", "1");
/*  892 */     localHashMap.put("110", "1");
/*  893 */     localHashMap.put("113", "1");
/*  894 */     localHashMap.put("81", "1");
/*  895 */     localHashMap.put("327", "1");
/*  896 */     localHashMap.put("381", "1");
/*  897 */     localHashMap.put("324", "1");
/*  898 */     localHashMap.put("211", "1");
/*  899 */     localHashMap.put("37", "1");
/*  900 */     localHashMap.put("266", "1");
/*  901 */     localHashMap.put("166", "1");
/*  902 */     localHashMap.put("174", "1");
/*  903 */     localHashMap.put("380", "1");
/*  904 */     localHashMap.put("382", "1");
/*  905 */     localHashMap.put("386", "1");
/*  906 */     localHashMap.put("385", "1");
/*  907 */     localHashMap.put("172", "1");
/*  908 */     localHashMap.put("12", "1");
/*  909 */     localHashMap.put("201", "1");
/*  910 */     localHashMap.put("223", "1");
/*  911 */     localHashMap.put("208", "1");
/*  912 */     localHashMap.put("186", "1");
/*  913 */     localHashMap.put("401", "1");
/*  914 */     localHashMap.put("368", "1");
/*  915 */     localHashMap.put("17", "1");
/*  916 */     localHashMap.put("206", "1");
/*  917 */     localHashMap.put("200", "1");
/*  918 */     localHashMap.put("181", "1");
/*  919 */     localHashMap.put("25", "1");
/*  920 */     localHashMap.put("265", "1");
/*  921 */     localHashMap.put("165", "1");
/*  922 */     localHashMap.put("161", "1");
/*  923 */     localHashMap.put("23", "1");
/*  924 */     localHashMap.put("187", "1");
/*  925 */     localHashMap.put("92", "1");
/*  926 */     localHashMap.put("315", "1");
/*  927 */     localHashMap.put("38", "1");
/*  928 */     localHashMap.put("267", "1");
/*  929 */     localHashMap.put("167", "1");
/*  930 */     localHashMap.put("175", "1");
/*  931 */     localHashMap.put("154", "1");
/*  932 */     localHashMap.put("833", "2");
/*  933 */     localHashMap.put("835", "2");
/*  934 */     localHashMap.put("830", "3");
/*  935 */     localHashMap.put("837", "3");
/*  936 */     localHashMap.put("831", "3");
/*  937 */     localHashMap.put("836", "2");
/*  938 */     localHashMap.put("832", "2");
/*  939 */     localHashMap.put("838", "2");
/*  940 */     localHashMap.put("834", "2");
/*  941 */     localHashMap.put("829", "2");
/*  942 */     localHashMap.put("842", "2");
/*  943 */     localHashMap.put("840", "2");
/*  944 */     localHashMap.put("845", "2");
/*  945 */     localHashMap.put("846", "2");
/*  946 */     localHashMap.put("590", "1");
/*  947 */     localHashMap.put("114", "1");
/*  948 */     localHashMap.put("176", "1");
/*  949 */     localHashMap.put("383", "1");
/*  950 */     localHashMap.put("384", "1");
/*  951 */     localHashMap.put("192", "1");
/*  952 */     localHashMap.put("193", "1");
/*  953 */     localHashMap.put("195", "1");
/*  954 */     localHashMap.put("205", "1");
/*  955 */     localHashMap.put("190", "1");
/*  956 */     localHashMap.put("16", "1");
/*  957 */     localHashMap.put("40", "1");
/*  958 */     localHashMap.put("34", "1");
/*  959 */     localHashMap.put("18", "1");
/*  960 */     localHashMap.put("153", "1");
/*  961 */     localHashMap.put("155", "1");
/*  962 */     localHashMap.put("152", "1");
/*  963 */     localHashMap.put("13", "1");
/*  964 */     localHashMap.put("203", "1");
/*  965 */     localHashMap.put("226", "1");
/*  966 */     localHashMap.put("199", "1");
/*  967 */     localHashMap.put("183", "1");
/*  968 */     localHashMap.put("33", "1");
/*  969 */     localHashMap.put("15", "1");
/*  970 */     localHashMap.put("21", "1");
/*  971 */     localHashMap.put("353", "1");
/*  972 */     localHashMap.put("354", "1");
/*  973 */     localHashMap.put("41", "1");
/*  974 */     localHashMap.put("42", "1");
/*  975 */     localHashMap.put("319", "1");
/*  976 */     localHashMap.put("22", "1");
/*  977 */     localHashMap.put("82", "1");
/*  978 */     localHashMap.put("93", "1");
/*  979 */     localHashMap.put("312", "1");
/*  980 */     localHashMap.put("264", "1");
/*  981 */     localHashMap.put("164", "1");
/*  982 */     localHashMap.put("177", "1");
/*  983 */     localHashMap.put("156", "1");
/*  984 */     localHashMap.put("1", "1");
/*  985 */     localHashMap.put("221", "1");
/*  986 */     localHashMap.put("277", "1");
/*  987 */     localHashMap.put("4", "1");
/*  988 */     localHashMap.put("871", "3");
/*  989 */     localHashMap.put("872", "4");
/*  990 */     localHashMap.put("45", "1");
/*  991 */     localHashMap.put("44", "1");
/*  992 */     localHashMap.put("231", "1");
/*  993 */     localHashMap.put("230", "1");
/*  994 */     localHashMap.put("239", "1");
/*  995 */     localHashMap.put("2", "1");
/*  996 */     localHashMap.put("241", "1");
/*  997 */     localHashMap.put("96", "1");
/*  998 */     localHashMap.put("100", "1");
/*  999 */     localHashMap.put("7", "1");
/* 1000 */     localHashMap.put("97", "1");
/* 1001 */     localHashMap.put("98", "1");
/* 1002 */     localHashMap.put("9", "1");
/* 1003 */     localHashMap.put("27", "1");
/* 1004 */     localHashMap.put("99", "1");
/* 1005 */     localHashMap.put("95", "1");
/* 1006 */     localHashMap.put("8", "1");
/* 1007 */     localHashMap.put("5", "1");
/* 1008 */     localHashMap.put("90", "1");
/* 1009 */     localHashMap.put("6", "1");
/* 1010 */     localHashMap.put("91", "1");
/* 1011 */     localHashMap.put("94", "1");
/* 1012 */     localHashMap.put("101", "1");
/* 1013 */     localHashMap.put("210", "1");
/* 1014 */     localHashMap.put("3", "1");
/* 1015 */     localHashMap.put("278", "1");
/* 1016 */     localHashMap.put("31", "1");
/* 1017 */     localHashMap.put("46", "1");
/* 1018 */     localHashMap.put("39", "1");
/* 1019 */     localHashMap.put("279", "1");
/* 1020 */     localHashMap.put("351", "1");
/* 1021 */     localHashMap.put("352", "1");
/* 1022 */     localHashMap.put("178", "1");
/* 1023 */     localHashMap.put("251", "1");
/* 1024 */     localHashMap.put("50", "1");
/* 1025 */     localHashMap.put("10", "1");
/* 1026 */     localHashMap.put("28", "1");
/* 1027 */     localHashMap.put("160", "1");
/* 1028 */     localHashMap.put("261", "1");
/* 1029 */     localHashMap.put("20", "1");
/* 1030 */     localHashMap.put("850", "2");
/* 1031 */     localHashMap.put("853", "2");
/* 1032 */     localHashMap.put("852", "2");
/* 1033 */     localHashMap.put("851", "2");
/* 1034 */     localHashMap.put("854", "260");
/* 1035 */     localHashMap.put("865", "2");
/* 1036 */     localHashMap.put("866", "2");
/* 1037 */     localHashMap.put("864", "2");
/* 1038 */     localHashMap.put("862", "1");
/* 1039 */     localHashMap.put("868", "2");
/* 1040 */     localHashMap.put("992", "2");
/* 1041 */     localHashMap.put("867", "2");
/* 1042 */     localHashMap.put("860", "4");
/* 1043 */     localHashMap.put("861", "2");
/* 1044 */     localHashMap.put("863", "4");
/*      */ 
/* 1046 */     return localHashMap;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.LxMetaData
 * JD-Core Version:    0.6.0
 */