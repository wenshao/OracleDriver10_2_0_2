/*      */ package oracle.jdbc.driver;
/*      */ 
/*      */ class OracleSqlReadOnly
/*      */ {
/*      */   private static final int BASE = 0;
/*      */   private static final int BASE_1 = 1;
/*      */   private static final int BASE_2 = 2;
/*      */   private static final int B_STRING = 3;
/*      */   private static final int B_NAME = 4;
/*      */   private static final int B_C_COMMENT = 5;
/*      */   private static final int B_C_COMMENT_1 = 6;
/*      */   private static final int B_COMMENT = 7;
/*      */   private static final int PARAMETER = 8;
/*      */   private static final int TOKEN = 9;
/*      */   private static final int B_EGIN = 10;
/*      */   private static final int BE_GIN = 11;
/*      */   private static final int BEG_IN = 12;
/*      */   private static final int BEGI_N = 13;
/*      */   private static final int BEGIN_ = 14;
/*      */   private static final int C_ALL = 15;
/*      */   private static final int CA_LL = 16;
/*      */   private static final int CAL_L = 17;
/*      */   private static final int CALL_ = 18;
/*      */   private static final int D_Eetc = 19;
/*      */   private static final int DE_etc = 20;
/*      */   private static final int DEC_LARE = 21;
/*      */   private static final int DECL_ARE = 22;
/*      */   private static final int DECLA_RE = 23;
/*      */   private static final int DECLAR_E = 24;
/*      */   private static final int DECLARE_ = 25;
/*      */   private static final int DEL_ETE = 26;
/*      */   private static final int DELE_TE = 27;
/*      */   private static final int DELET_E = 28;
/*      */   private static final int DELETE_ = 29;
/*      */   private static final int I_NSERT = 30;
/*      */   private static final int IN_SERT = 31;
/*      */   private static final int INS_ERT = 32;
/*      */   private static final int INSE_RT = 33;
/*      */   private static final int INSER_T = 34;
/*      */   private static final int INSERT_ = 35;
/*      */   private static final int S_ELECT = 36;
/*      */   private static final int SE_LECT = 37;
/*      */   private static final int SEL_ECT = 38;
/*      */   private static final int SELE_CT = 39;
/*      */   private static final int SELEC_T = 40;
/*      */   private static final int SELECT_ = 41;
/*      */   private static final int U_PDATE = 42;
/*      */   private static final int UP_DATE = 43;
/*      */   private static final int UPD_ATE = 44;
/*      */   private static final int UPDA_TE = 45;
/*      */   private static final int UPDAT_E = 46;
/*      */   private static final int UPDATE_ = 47;
/*      */   private static final int M_ERGE = 48;
/*      */   private static final int ME_RGE = 49;
/*      */   private static final int MER_GE = 50;
/*      */   private static final int MERG_E = 51;
/*      */   private static final int MERGE_ = 52;
/*      */   private static final int W_ITH = 53;
/*      */   private static final int WI_TH = 54;
/*      */   private static final int WIT_H = 55;
/*      */   private static final int WITH_ = 56;
/*      */   private static final int KNOW_KIND = 57;
/*      */   private static final int KNOW_KIND_1 = 58;
/*      */   private static final int KNOW_KIND_2 = 59;
/*      */   private static final int K_STRING = 60;
/*      */   private static final int K_NAME = 61;
/*      */   private static final int K_C_COMMENT = 62;
/*      */   private static final int K_C_COMMENT_1 = 63;
/*      */   private static final int K_COMMENT = 64;
/*      */   private static final int K_PARAMETER = 65;
/*      */   private static final int TOKEN_KK = 66;
/*      */   private static final int W_HERE = 67;
/*      */   private static final int WH_ERE = 68;
/*      */   private static final int WHE_RE = 69;
/*      */   private static final int WHER_E = 70;
/*      */   private static final int WHERE_ = 71;
/*      */   private static final int O_RDER_BY = 72;
/*      */   private static final int OR_DER_BY = 73;
/*      */   private static final int ORD_ER_BY = 74;
/*      */   private static final int ORDE_R_BY = 75;
/*      */   private static final int ORDER__BY = 76;
/*      */   private static final int ORDER_xBY = 77;
/*      */   private static final int ORDER_B_Y = 78;
/*      */   private static final int ORDER_BY_ = 79;
/*      */   private static final int ORDER_xBY_CC_1 = 80;
/*      */   private static final int ORDER_xBY_CC_2 = 81;
/*      */   private static final int ORDER_xBY_CC_3 = 82;
/*      */   private static final int ORDER_xBY_C_1 = 83;
/*      */   private static final int ORDER_xBY_C_2 = 84;
/*      */   private static final int F_OR_UPDATE = 84;
/*      */   private static final int FO_R_UPDATE = 85;
/*      */   private static final int FOR__UPDATE = 86;
/*      */   private static final int FOR_xUPDATE = 87;
/*      */   private static final int FOR_U_PDATE = 88;
/*      */   private static final int FOR_UP_DATE = 89;
/*      */   private static final int FOR_UPD_ATE = 90;
/*      */   private static final int FOR_UPDA_TE = 91;
/*      */   private static final int FOR_UPDAT_E = 92;
/*      */   private static final int FOR_UPDATE_ = 93;
/*      */   private static final int FOR_xUPDATE_CC_1 = 94;
/*      */   private static final int FOR_xUPDATE_CC_2 = 95;
/*      */   private static final int FOR_xUPDATE_CC_3 = 96;
/*      */   private static final int FOR_xUPDATE_C_1 = 97;
/*      */   private static final int FOR_xUPDATE_C_2 = 98;
/*      */   private static final int B_N_tick = 99;
/*      */   private static final int B_NCHAR = 100;
/*      */   private static final int K_N_tick = 101;
/*      */   private static final int K_NCHAR = 102;
/*      */   private static final int LAST_STATE = 103;
/* 1802 */   static final int[][] TRANSITION = new int[103][];
/*      */   static final int NO_ACTION = 0;
/*      */   static final int DML_ACTION = 1;
/*      */   static final int PLSQL_ACTION = 2;
/*      */   static final int CALL_ACTION = 3;
/*      */   static final int SELECT_ACTION = 4;
/*      */   static final int OTHER_ACTION = 5;
/*      */   static final int WHERE_ACTION = 6;
/*      */   static final int ORDER_ACTION = 7;
/*      */   static final int ORDER_BY_ACTION = 8;
/*      */   static final int FOR_ACTION = 9;
/*      */   static final int FOR_UPDATE_ACTION = 10;
/*      */   static final int QUESTION_ACTION = 11;
/*      */   static final int PARAMETER_ACTION = 12;
/*      */   static final int END_PARAMETER_ACTION = 13;
/*      */   static final int START_NCHAR_LITERAL_ACTION = 14;
/*      */   static final int END_NCHAR_LITERAL_ACTION = 15;
/* 1822 */   static final int[][] ACTION = new int[103][];
/*      */   static final int cMax = 127;
/*      */   private static final int cMaxLength = 128;
/*      */ 
/*      */   private static final int[] copy(int[] paramArrayOfInt)
/*      */   {
/* 1827 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*      */ 
/* 1829 */     System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
/*      */ 
/* 1831 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   private static final int[] newArray(int paramInt1, int paramInt2)
/*      */   {
/* 1836 */     int[] arrayOfInt = new int[paramInt1];
/*      */ 
/* 1838 */     for (int i = 0; i < paramInt1; i++) {
/* 1839 */       arrayOfInt[i] = paramInt2;
/*      */     }
/* 1841 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   private static final int[] copyReplacing(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 1846 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*      */ 
/* 1848 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 1850 */       int j = paramArrayOfInt[i];
/*      */ 
/* 1852 */       if (j == paramInt1)
/* 1853 */         arrayOfInt[i] = paramInt2;
/*      */       else {
/* 1855 */         arrayOfInt[i] = j;
/*      */       }
/*      */     }
/* 1858 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1868 */     int[] arrayOfInt1 = { 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57, 4, 9, 9, 57, 57, 3, 57, 57, 57, 57, 57, 2, 57, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 8, 57, 57, 57, 57, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 57, 57, 57, 9, 57, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 57, 9, 9, 9, 9 };
/*      */ 
/* 1905 */     int[] arrayOfInt2 = copyReplacing(arrayOfInt1, 57, 0);
/*      */ 
/* 1907 */     arrayOfInt2[66] = 10;
/* 1908 */     arrayOfInt2[98] = 10;
/* 1909 */     arrayOfInt2[67] = 15;
/* 1910 */     arrayOfInt2[99] = 15;
/* 1911 */     arrayOfInt2[68] = 19;
/* 1912 */     arrayOfInt2[100] = 19;
/* 1913 */     arrayOfInt2[73] = 30;
/* 1914 */     arrayOfInt2[105] = 30;
/* 1915 */     arrayOfInt2[78] = 99;
/* 1916 */     arrayOfInt2[110] = 99;
/* 1917 */     arrayOfInt2[83] = 36;
/* 1918 */     arrayOfInt2[115] = 36;
/* 1919 */     arrayOfInt2[85] = 42;
/* 1920 */     arrayOfInt2[117] = 42;
/* 1921 */     arrayOfInt2[109] = 48;
/* 1922 */     arrayOfInt2[77] = 48;
/* 1923 */     arrayOfInt2[87] = 53;
/* 1924 */     arrayOfInt2[119] = 53;
/*      */ 
/* 1927 */     int[] arrayOfInt3 = copy(arrayOfInt1);
/*      */ 
/* 1929 */     arrayOfInt3[34] = 61;
/* 1930 */     arrayOfInt3[39] = 60;
/* 1931 */     arrayOfInt3[45] = 59;
/* 1932 */     arrayOfInt3[47] = 58;
/* 1933 */     arrayOfInt3[58] = 65;
/* 1934 */     arrayOfInt3[32] = 66;
/*      */ 
/* 1936 */     int[] arrayOfInt4 = copyReplacing(arrayOfInt3, 9, 57);
/* 1937 */     arrayOfInt4[32] = 66;
/* 1938 */     arrayOfInt4[9] = 66;
/* 1939 */     arrayOfInt4[10] = 66;
/* 1940 */     arrayOfInt4[13] = 66;
/* 1941 */     arrayOfInt4[61] = 66;
/*      */ 
/* 1944 */     TRANSITION[0] = arrayOfInt2;
/* 1945 */     TRANSITION[1] = copy(arrayOfInt2);
/* 1946 */     TRANSITION[1][42] = 5;
/* 1947 */     TRANSITION[2] = copy(arrayOfInt2);
/* 1948 */     TRANSITION[2][45] = 7;
/* 1949 */     TRANSITION[3] = newArray(128, 3);
/* 1950 */     TRANSITION[3][39] = 0;
/* 1951 */     TRANSITION[99] = copy(arrayOfInt1);
/* 1952 */     TRANSITION[99][39] = 100;
/* 1953 */     TRANSITION[100] = newArray(128, 100);
/* 1954 */     TRANSITION[100][39] = 0;
/* 1955 */     TRANSITION[4] = newArray(128, 4);
/* 1956 */     TRANSITION[4][34] = 0;
/* 1957 */     TRANSITION[5] = newArray(128, 5);
/* 1958 */     TRANSITION[5][42] = 6;
/* 1959 */     TRANSITION[6] = newArray(128, 5);
/* 1960 */     TRANSITION[6][42] = 6;
/* 1961 */     TRANSITION[6][47] = 0;
/* 1962 */     TRANSITION[7] = newArray(128, 7);
/* 1963 */     TRANSITION[7][10] = 0;
/* 1964 */     TRANSITION[8] = copyReplacing(arrayOfInt1, 9, 8);
/* 1965 */     TRANSITION[9] = arrayOfInt1;
/* 1966 */     TRANSITION[10] = copy(arrayOfInt1);
/* 1967 */     TRANSITION[10][69] = 11;
/* 1968 */     TRANSITION[10][101] = 11;
/* 1969 */     TRANSITION[11] = copy(arrayOfInt1);
/* 1970 */     TRANSITION[11][71] = 12;
/* 1971 */     TRANSITION[11][103] = 12;
/* 1972 */     TRANSITION[12] = copy(arrayOfInt1);
/* 1973 */     TRANSITION[12][73] = 13;
/* 1974 */     TRANSITION[12][105] = 13;
/* 1975 */     TRANSITION[13] = copy(arrayOfInt1);
/* 1976 */     TRANSITION[13][78] = 14;
/* 1977 */     TRANSITION[13][110] = 14;
/* 1978 */     TRANSITION[14] = arrayOfInt3;
/* 1979 */     TRANSITION[15] = copy(arrayOfInt1);
/* 1980 */     TRANSITION[15][65] = 16;
/* 1981 */     TRANSITION[15][97] = 16;
/* 1982 */     TRANSITION[16] = copy(arrayOfInt1);
/* 1983 */     TRANSITION[16][76] = 17;
/* 1984 */     TRANSITION[16][108] = 17;
/* 1985 */     TRANSITION[17] = copy(arrayOfInt1);
/* 1986 */     TRANSITION[17][76] = 18;
/* 1987 */     TRANSITION[17][108] = 18;
/* 1988 */     TRANSITION[18] = arrayOfInt3;
/* 1989 */     TRANSITION[19] = copy(arrayOfInt1);
/* 1990 */     TRANSITION[19][69] = 20;
/* 1991 */     TRANSITION[19][101] = 20;
/* 1992 */     TRANSITION[20] = copy(arrayOfInt1);
/* 1993 */     TRANSITION[20][67] = 21;
/* 1994 */     TRANSITION[20][99] = 21;
/* 1995 */     TRANSITION[20][76] = 26;
/* 1996 */     TRANSITION[20][108] = 26;
/* 1997 */     TRANSITION[21] = copy(arrayOfInt1);
/* 1998 */     TRANSITION[21][76] = 22;
/* 1999 */     TRANSITION[21][108] = 22;
/* 2000 */     TRANSITION[22] = copy(arrayOfInt1);
/* 2001 */     TRANSITION[22][65] = 23;
/* 2002 */     TRANSITION[22][97] = 23;
/* 2003 */     TRANSITION[23] = copy(arrayOfInt1);
/* 2004 */     TRANSITION[23][82] = 24;
/* 2005 */     TRANSITION[23][114] = 24;
/* 2006 */     TRANSITION[24] = copy(arrayOfInt1);
/* 2007 */     TRANSITION[24][69] = 25;
/* 2008 */     TRANSITION[24][101] = 25;
/* 2009 */     TRANSITION[25] = arrayOfInt3;
/* 2010 */     TRANSITION[26] = copy(arrayOfInt1);
/* 2011 */     TRANSITION[26][69] = 27;
/* 2012 */     TRANSITION[26][101] = 27;
/* 2013 */     TRANSITION[27] = copy(arrayOfInt1);
/* 2014 */     TRANSITION[27][84] = 28;
/* 2015 */     TRANSITION[27][116] = 28;
/* 2016 */     TRANSITION[28] = copy(arrayOfInt1);
/* 2017 */     TRANSITION[28][69] = 29;
/* 2018 */     TRANSITION[28][101] = 29;
/* 2019 */     TRANSITION[29] = arrayOfInt3;
/* 2020 */     TRANSITION[30] = copy(arrayOfInt1);
/* 2021 */     TRANSITION[30][78] = 31;
/* 2022 */     TRANSITION[30][110] = 31;
/* 2023 */     TRANSITION[31] = copy(arrayOfInt1);
/* 2024 */     TRANSITION[31][83] = 32;
/* 2025 */     TRANSITION[31][115] = 32;
/* 2026 */     TRANSITION[32] = copy(arrayOfInt1);
/* 2027 */     TRANSITION[32][69] = 33;
/* 2028 */     TRANSITION[32][101] = 33;
/* 2029 */     TRANSITION[33] = copy(arrayOfInt1);
/* 2030 */     TRANSITION[33][82] = 34;
/* 2031 */     TRANSITION[33][114] = 34;
/* 2032 */     TRANSITION[34] = copy(arrayOfInt1);
/* 2033 */     TRANSITION[34][84] = 35;
/* 2034 */     TRANSITION[34][116] = 35;
/* 2035 */     TRANSITION[35] = arrayOfInt3;
/* 2036 */     TRANSITION[36] = copy(arrayOfInt1);
/* 2037 */     TRANSITION[36][69] = 37;
/* 2038 */     TRANSITION[36][101] = 37;
/* 2039 */     TRANSITION[37] = copy(arrayOfInt1);
/* 2040 */     TRANSITION[37][76] = 38;
/* 2041 */     TRANSITION[37][108] = 38;
/* 2042 */     TRANSITION[38] = copy(arrayOfInt1);
/* 2043 */     TRANSITION[38][69] = 39;
/* 2044 */     TRANSITION[38][101] = 39;
/* 2045 */     TRANSITION[39] = copy(arrayOfInt1);
/* 2046 */     TRANSITION[39][67] = 40;
/* 2047 */     TRANSITION[39][99] = 40;
/* 2048 */     TRANSITION[40] = copy(arrayOfInt1);
/* 2049 */     TRANSITION[40][84] = 41;
/* 2050 */     TRANSITION[40][116] = 41;
/* 2051 */     TRANSITION[41] = arrayOfInt3;
/* 2052 */     TRANSITION[42] = copy(arrayOfInt1);
/* 2053 */     TRANSITION[42][80] = 43;
/* 2054 */     TRANSITION[42][112] = 43;
/* 2055 */     TRANSITION[43] = copy(arrayOfInt1);
/* 2056 */     TRANSITION[43][68] = 44;
/* 2057 */     TRANSITION[43][100] = 44;
/* 2058 */     TRANSITION[44] = copy(arrayOfInt1);
/* 2059 */     TRANSITION[44][65] = 45;
/* 2060 */     TRANSITION[44][97] = 45;
/* 2061 */     TRANSITION[45] = copy(arrayOfInt1);
/* 2062 */     TRANSITION[45][84] = 46;
/* 2063 */     TRANSITION[45][116] = 46;
/* 2064 */     TRANSITION[46] = copy(arrayOfInt1);
/* 2065 */     TRANSITION[46][69] = 47;
/* 2066 */     TRANSITION[46][101] = 47;
/* 2067 */     TRANSITION[47] = arrayOfInt3;
/* 2068 */     TRANSITION[48] = copy(arrayOfInt1);
/* 2069 */     TRANSITION[48][69] = 49;
/* 2070 */     TRANSITION[48][101] = 49;
/* 2071 */     TRANSITION[49] = copy(arrayOfInt1);
/* 2072 */     TRANSITION[49][82] = 50;
/* 2073 */     TRANSITION[49][114] = 50;
/* 2074 */     TRANSITION[50] = copy(arrayOfInt1);
/* 2075 */     TRANSITION[50][71] = 51;
/* 2076 */     TRANSITION[50][103] = 51;
/* 2077 */     TRANSITION[51] = copy(arrayOfInt1);
/* 2078 */     TRANSITION[51][69] = 52;
/* 2079 */     TRANSITION[51][101] = 52;
/* 2080 */     TRANSITION[52] = arrayOfInt3;
/* 2081 */     TRANSITION[53] = copy(arrayOfInt1);
/* 2082 */     TRANSITION[53][73] = 54;
/* 2083 */     TRANSITION[53][105] = 54;
/* 2084 */     TRANSITION[54] = copy(arrayOfInt1);
/* 2085 */     TRANSITION[54][84] = 55;
/* 2086 */     TRANSITION[54][116] = 55;
/* 2087 */     TRANSITION[55] = copy(arrayOfInt1);
/* 2088 */     TRANSITION[55][72] = 56;
/* 2089 */     TRANSITION[55][104] = 56;
/* 2090 */     TRANSITION[56] = arrayOfInt3;
/* 2091 */     TRANSITION[57] = arrayOfInt4;
/* 2092 */     TRANSITION[58] = copy(arrayOfInt4);
/* 2093 */     TRANSITION[58][42] = 62;
/* 2094 */     TRANSITION[59] = copy(arrayOfInt4);
/* 2095 */     TRANSITION[59][45] = 64;
/* 2096 */     TRANSITION[62] = newArray(128, 62);
/* 2097 */     TRANSITION[62][42] = 63;
/* 2098 */     TRANSITION[63] = newArray(128, 62);
/* 2099 */     TRANSITION[63][42] = 63;
/* 2100 */     TRANSITION[63][47] = 66;
/* 2101 */     TRANSITION[64] = newArray(128, 64);
/* 2102 */     TRANSITION[64][10] = 66;
/* 2103 */     TRANSITION[61] = newArray(128, 61);
/* 2104 */     TRANSITION[61][34] = 57;
/* 2105 */     TRANSITION[60] = newArray(128, 60);
/* 2106 */     TRANSITION[60][39] = 57;
/* 2107 */     TRANSITION[65] = copyReplacing(arrayOfInt3, 9, 65);
/*      */ 
/* 2112 */     int[] arrayOfInt5 = copy(arrayOfInt4);
/* 2113 */     TRANSITION[66] = arrayOfInt5;
/*      */ 
/* 2116 */     TRANSITION[66][32] = 66;
/* 2117 */     TRANSITION[66][9] = 66;
/* 2118 */     TRANSITION[66][10] = 66;
/* 2119 */     TRANSITION[66][13] = 66;
/* 2120 */     TRANSITION[66][61] = 66;
/*      */ 
/* 2122 */     TRANSITION[66][87] = 67;
/* 2123 */     TRANSITION[66][119] = 67;
/* 2124 */     TRANSITION[67] = copy(arrayOfInt4);
/* 2125 */     TRANSITION[67][72] = 68;
/* 2126 */     TRANSITION[67][104] = 68;
/* 2127 */     TRANSITION[68] = copy(arrayOfInt4);
/* 2128 */     TRANSITION[68][69] = 69;
/* 2129 */     TRANSITION[68][101] = 69;
/* 2130 */     TRANSITION[69] = copy(arrayOfInt4);
/* 2131 */     TRANSITION[69][82] = 70;
/* 2132 */     TRANSITION[69][114] = 70;
/* 2133 */     TRANSITION[70] = copy(arrayOfInt4);
/* 2134 */     TRANSITION[70][69] = 71;
/* 2135 */     TRANSITION[70][101] = 71;
/* 2136 */     TRANSITION[71] = arrayOfInt4;
/*      */ 
/* 2138 */     TRANSITION[66][79] = 72;
/* 2139 */     TRANSITION[66][111] = 72;
/* 2140 */     TRANSITION[72] = copy(arrayOfInt4);
/* 2141 */     TRANSITION[72][82] = 73;
/* 2142 */     TRANSITION[72][114] = 73;
/* 2143 */     TRANSITION[73] = copy(arrayOfInt4);
/* 2144 */     TRANSITION[73][68] = 74;
/* 2145 */     TRANSITION[73][100] = 74;
/* 2146 */     TRANSITION[74] = copy(arrayOfInt4);
/* 2147 */     TRANSITION[74][69] = 75;
/* 2148 */     TRANSITION[74][101] = 75;
/* 2149 */     TRANSITION[75] = copy(arrayOfInt4);
/* 2150 */     TRANSITION[75][82] = 76;
/* 2151 */     TRANSITION[75][114] = 76;
/* 2152 */     TRANSITION[76] = copyReplacing(arrayOfInt5, 66, 77);
/* 2153 */     TRANSITION[76][47] = 80;
/* 2154 */     TRANSITION[76][45] = 83;
/*      */ 
/* 2156 */     TRANSITION[77] = copyReplacing(arrayOfInt5, 66, 77);
/* 2157 */     TRANSITION[77][47] = 80;
/* 2158 */     TRANSITION[80] = copy(arrayOfInt4);
/* 2159 */     TRANSITION[80][42] = 81;
/* 2160 */     TRANSITION[81] = newArray(128, 81);
/* 2161 */     TRANSITION[81][42] = 82;
/* 2162 */     TRANSITION[82] = newArray(128, 81);
/* 2163 */     TRANSITION[82][47] = 77;
/*      */ 
/* 2165 */     TRANSITION[77][45] = 83;
/* 2166 */     TRANSITION[83] = copy(arrayOfInt4);
/* 2167 */     TRANSITION[83][45] = 84;
/* 2168 */     TRANSITION[84] = newArray(128, 84);
/* 2169 */     TRANSITION[84][10] = 77;
/*      */ 
/* 2172 */     TRANSITION[77][66] = 78;
/* 2173 */     TRANSITION[77][98] = 78;
/* 2174 */     TRANSITION[78] = copy(arrayOfInt4);
/* 2175 */     TRANSITION[78][89] = 79;
/* 2176 */     TRANSITION[78][121] = 79;
/* 2177 */     TRANSITION[79] = arrayOfInt4;
/*      */ 
/* 2179 */     TRANSITION[66][70] = 84;
/* 2180 */     TRANSITION[66][102] = 84;
/* 2181 */     TRANSITION[84] = copy(arrayOfInt4);
/* 2182 */     TRANSITION[84][79] = 85;
/* 2183 */     TRANSITION[84][111] = 85;
/* 2184 */     TRANSITION[85] = copy(arrayOfInt4);
/* 2185 */     TRANSITION[85][82] = 86;
/* 2186 */     TRANSITION[85][114] = 86;
/* 2187 */     TRANSITION[86] = copyReplacing(arrayOfInt5, 66, 87);
/* 2188 */     TRANSITION[86][47] = 94;
/* 2189 */     TRANSITION[86][45] = 97;
/*      */ 
/* 2191 */     TRANSITION[87] = copyReplacing(arrayOfInt5, 66, 87);
/* 2192 */     TRANSITION[87][47] = 94;
/* 2193 */     TRANSITION[94] = copy(arrayOfInt4);
/* 2194 */     TRANSITION[94][42] = 95;
/* 2195 */     TRANSITION[95] = newArray(128, 95);
/* 2196 */     TRANSITION[95][42] = 96;
/* 2197 */     TRANSITION[96] = newArray(128, 95);
/* 2198 */     TRANSITION[96][47] = 87;
/*      */ 
/* 2200 */     TRANSITION[87][45] = 97;
/* 2201 */     TRANSITION[97] = copy(arrayOfInt4);
/* 2202 */     TRANSITION[97][45] = 98;
/* 2203 */     TRANSITION[98] = newArray(128, 98);
/* 2204 */     TRANSITION[98][10] = 87;
/*      */ 
/* 2207 */     TRANSITION[87][85] = 88;
/* 2208 */     TRANSITION[87][117] = 88;
/* 2209 */     TRANSITION[88] = copy(arrayOfInt4);
/* 2210 */     TRANSITION[88][80] = 89;
/* 2211 */     TRANSITION[88][112] = 89;
/* 2212 */     TRANSITION[89] = copy(arrayOfInt4);
/* 2213 */     TRANSITION[89][68] = 90;
/* 2214 */     TRANSITION[89][100] = 90;
/* 2215 */     TRANSITION[90] = copy(arrayOfInt4);
/* 2216 */     TRANSITION[90][65] = 91;
/* 2217 */     TRANSITION[90][97] = 91;
/* 2218 */     TRANSITION[91] = copy(arrayOfInt4);
/* 2219 */     TRANSITION[91][84] = 92;
/* 2220 */     TRANSITION[91][116] = 92;
/* 2221 */     TRANSITION[92] = copy(arrayOfInt4);
/* 2222 */     TRANSITION[92][69] = 93;
/* 2223 */     TRANSITION[92][101] = 93;
/* 2224 */     TRANSITION[93] = arrayOfInt4;
/*      */ 
/* 2226 */     TRANSITION[66][78] = 101;
/* 2227 */     TRANSITION[66][110] = 101;
/* 2228 */     TRANSITION[101] = copy(arrayOfInt4);
/* 2229 */     TRANSITION[101][39] = 102;
/* 2230 */     TRANSITION[102] = newArray(128, 102);
/* 2231 */     TRANSITION[102][39] = 66;
/*      */ 
/* 2233 */     int[] arrayOfInt6 = newArray(128, 0);
/*      */ 
/* 2235 */     int[] arrayOfInt7 = copy(arrayOfInt6);
/*      */ 
/* 2237 */     arrayOfInt7[63] = 11;
/*      */ 
/* 2239 */     int[] arrayOfInt8 = new int[''];
/*      */ 
/* 2241 */     for (int i = 0; i < arrayOfInt8.length; i++) {
/* 2242 */       if (TRANSITION[8][i] == 8)
/* 2243 */         arrayOfInt8[i] = 12;
/*      */       else
/* 2245 */         arrayOfInt8[i] = 13;
/*      */     }
/* 2247 */     int[] arrayOfInt9 = new int[''];
/*      */ 
/* 2249 */     for (int j = 0; j < arrayOfInt9.length; j++) {
/* 2250 */       if (TRANSITION[65][j] == 65)
/* 2251 */         arrayOfInt9[j] = 12;
/*      */       else
/* 2253 */         arrayOfInt9[j] = 13;
/*      */     }
/* 2255 */     int[] arrayOfInt10 = copy(arrayOfInt6);
/*      */ 
/* 2257 */     for (int k = 0; k < arrayOfInt10.length; k++) {
/* 2258 */       if (arrayOfInt3[k] != 9)
/* 2259 */         arrayOfInt10[k] = 2;
/*      */     }
/* 2261 */     int[] arrayOfInt11 = copyReplacing(arrayOfInt10, 2, 3);
/* 2262 */     int[] arrayOfInt12 = copyReplacing(arrayOfInt10, 2, 1);
/* 2263 */     int[] arrayOfInt13 = copyReplacing(arrayOfInt10, 2, 4);
/* 2264 */     int[] arrayOfInt14 = copyReplacing(arrayOfInt10, 2, 5);
/*      */ 
/* 2266 */     int[] arrayOfInt15 = copyReplacing(arrayOfInt10, 2, 7);
/*      */ 
/* 2268 */     for (int m = 0; m < arrayOfInt15.length; m++) {
/* 2269 */       if (arrayOfInt15[m] == 5) {
/* 2270 */         arrayOfInt15[m] = 0;
/*      */       }
/*      */     }
/* 2273 */     int[] arrayOfInt16 = copyReplacing(arrayOfInt15, 7, 8);
/* 2274 */     int[] arrayOfInt17 = copyReplacing(arrayOfInt15, 7, 6);
/* 2275 */     int[] arrayOfInt18 = copyReplacing(arrayOfInt15, 7, 9);
/* 2276 */     int[] arrayOfInt19 = copyReplacing(arrayOfInt15, 7, 10);
/*      */ 
/* 2279 */     int[] arrayOfInt20 = copy(arrayOfInt6);
/* 2280 */     arrayOfInt20[39] = 14;
/*      */ 
/* 2282 */     int[] arrayOfInt21 = copy(arrayOfInt6);
/* 2283 */     arrayOfInt21[39] = 15;
/*      */ 
/* 2285 */     ACTION[0] = arrayOfInt7;
/* 2286 */     ACTION[1] = arrayOfInt7;
/* 2287 */     ACTION[2] = arrayOfInt7;
/* 2288 */     ACTION[3] = arrayOfInt6;
/* 2289 */     ACTION[4] = arrayOfInt6;
/* 2290 */     ACTION[5] = arrayOfInt6;
/* 2291 */     ACTION[6] = arrayOfInt6;
/* 2292 */     ACTION[7] = arrayOfInt6;
/* 2293 */     ACTION[8] = arrayOfInt8;
/* 2294 */     ACTION[99] = arrayOfInt20;
/* 2295 */     ACTION[100] = arrayOfInt21;
/* 2296 */     ACTION[9] = arrayOfInt14;
/* 2297 */     ACTION[10] = arrayOfInt14;
/* 2298 */     ACTION[11] = arrayOfInt14;
/* 2299 */     ACTION[12] = arrayOfInt14;
/* 2300 */     ACTION[13] = arrayOfInt14;
/* 2301 */     ACTION[14] = arrayOfInt10;
/* 2302 */     ACTION[15] = arrayOfInt14;
/* 2303 */     ACTION[16] = arrayOfInt14;
/* 2304 */     ACTION[17] = arrayOfInt14;
/* 2305 */     ACTION[18] = arrayOfInt11;
/* 2306 */     ACTION[19] = arrayOfInt14;
/* 2307 */     ACTION[20] = arrayOfInt14;
/* 2308 */     ACTION[21] = arrayOfInt14;
/* 2309 */     ACTION[22] = arrayOfInt14;
/* 2310 */     ACTION[23] = arrayOfInt14;
/* 2311 */     ACTION[24] = arrayOfInt14;
/* 2312 */     ACTION[25] = arrayOfInt10;
/* 2313 */     ACTION[26] = arrayOfInt14;
/* 2314 */     ACTION[27] = arrayOfInt14;
/* 2315 */     ACTION[28] = arrayOfInt14;
/* 2316 */     ACTION[29] = arrayOfInt12;
/* 2317 */     ACTION[30] = arrayOfInt14;
/* 2318 */     ACTION[31] = arrayOfInt14;
/* 2319 */     ACTION[32] = arrayOfInt14;
/* 2320 */     ACTION[33] = arrayOfInt14;
/* 2321 */     ACTION[34] = arrayOfInt14;
/* 2322 */     ACTION[35] = arrayOfInt12;
/* 2323 */     ACTION[36] = arrayOfInt14;
/* 2324 */     ACTION[37] = arrayOfInt14;
/* 2325 */     ACTION[38] = arrayOfInt14;
/* 2326 */     ACTION[39] = arrayOfInt14;
/* 2327 */     ACTION[40] = arrayOfInt14;
/* 2328 */     ACTION[41] = arrayOfInt13;
/* 2329 */     ACTION[42] = arrayOfInt14;
/* 2330 */     ACTION[43] = arrayOfInt14;
/* 2331 */     ACTION[44] = arrayOfInt14;
/* 2332 */     ACTION[45] = arrayOfInt14;
/* 2333 */     ACTION[46] = arrayOfInt14;
/* 2334 */     ACTION[47] = arrayOfInt12;
/* 2335 */     ACTION[48] = arrayOfInt14;
/* 2336 */     ACTION[49] = arrayOfInt14;
/* 2337 */     ACTION[50] = arrayOfInt14;
/* 2338 */     ACTION[51] = arrayOfInt14;
/* 2339 */     ACTION[52] = arrayOfInt12;
/* 2340 */     ACTION[53] = arrayOfInt14;
/* 2341 */     ACTION[54] = arrayOfInt14;
/* 2342 */     ACTION[55] = arrayOfInt14;
/* 2343 */     ACTION[56] = arrayOfInt13;
/* 2344 */     ACTION[57] = arrayOfInt7;
/* 2345 */     ACTION[58] = arrayOfInt7;
/* 2346 */     ACTION[59] = arrayOfInt7;
/* 2347 */     ACTION[60] = arrayOfInt6;
/* 2348 */     ACTION[61] = arrayOfInt6;
/* 2349 */     ACTION[62] = arrayOfInt6;
/* 2350 */     ACTION[63] = arrayOfInt6;
/* 2351 */     ACTION[64] = arrayOfInt6;
/* 2352 */     ACTION[65] = arrayOfInt9;
/* 2353 */     ACTION[101] = arrayOfInt20;
/* 2354 */     ACTION[102] = arrayOfInt21;
/*      */ 
/* 2356 */     ACTION[66] = arrayOfInt7;
/*      */ 
/* 2358 */     ACTION[67] = arrayOfInt6;
/* 2359 */     ACTION[68] = arrayOfInt6;
/* 2360 */     ACTION[69] = arrayOfInt6;
/* 2361 */     ACTION[70] = arrayOfInt6;
/* 2362 */     ACTION[71] = arrayOfInt17;
/*      */ 
/* 2364 */     ACTION[72] = arrayOfInt6;
/* 2365 */     ACTION[73] = arrayOfInt6;
/* 2366 */     ACTION[74] = arrayOfInt6;
/* 2367 */     ACTION[75] = arrayOfInt6;
/* 2368 */     ACTION[76] = arrayOfInt15;
/*      */ 
/* 2370 */     ACTION[77] = arrayOfInt6;
/* 2371 */     ACTION[78] = arrayOfInt6;
/* 2372 */     ACTION[79] = arrayOfInt16;
/*      */ 
/* 2374 */     ACTION[80] = arrayOfInt6;
/* 2375 */     ACTION[81] = arrayOfInt6;
/* 2376 */     ACTION[82] = arrayOfInt6;
/* 2377 */     ACTION[83] = arrayOfInt6;
/* 2378 */     ACTION[84] = arrayOfInt6;
/*      */ 
/* 2380 */     ACTION[84] = arrayOfInt6;
/* 2381 */     ACTION[85] = arrayOfInt6;
/* 2382 */     ACTION[86] = arrayOfInt18;
/*      */ 
/* 2384 */     ACTION[87] = arrayOfInt7;
/* 2385 */     ACTION[88] = arrayOfInt6;
/* 2386 */     ACTION[89] = arrayOfInt6;
/* 2387 */     ACTION[90] = arrayOfInt6;
/* 2388 */     ACTION[91] = arrayOfInt6;
/* 2389 */     ACTION[92] = arrayOfInt6;
/* 2390 */     ACTION[93] = arrayOfInt19;
/*      */ 
/* 2392 */     ACTION[94] = arrayOfInt6;
/* 2393 */     ACTION[95] = arrayOfInt6;
/* 2394 */     ACTION[96] = arrayOfInt6;
/* 2395 */     ACTION[97] = arrayOfInt6;
/* 2396 */     ACTION[98] = arrayOfInt6;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleSqlReadOnly
 * JD-Core Version:    0.6.0
 */