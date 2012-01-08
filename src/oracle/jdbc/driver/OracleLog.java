package oracle.jdbc.driver;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.PrintStream;

import java.io.PrintWriter;

import java.io.StringWriter;

import java.security.AccessController;

import java.security.PrivilegedAction;

import java.util.Date;

import java.util.logging.Level;

import java.util.logging.Logger;

import java.util.logging.SimpleFormatter;

import java.util.logging.StreamHandler;

public class OracleLog {
	private static StreamHandler printHandler;
	public static Logger rootLogger;
	public static Logger driverLogger;
	public static Logger poolLogger;
	public static Logger conversionLogger;
	public static Logger adtLogger;
	public static Logger thinLogger;
	public static Logger datumLogger;
	public static Logger kprbLogger;
	public static Logger xaLogger;
	public static Logger sqljLogger;
	public static Logger ociLogger;
	public static Logger jpubLogger;
	public static final int MASK_ALL_SET = 268435455;
	public static final int MAX_VECTOR_BITS = 32;
	public static final int MAX_MODULES = 32;
	public static final int MODULE_ALL = 268435455;
	public static final int MODULE_DRIVER = 1;
	public static final int MODULE_POOL = 2;
	public static final int MODULE_DBCONV = 4;
	public static final int MODULE_unused2 = 8;
	public static final int MODULE_PICKLE = 16;
	public static final int MODULE_JTTC = 32;
	public static final int MODULE_DATUM = 64;
	public static final int MODULE_KPRB = 128;
	public static final int MODULE_XA = 256;
	public static final int MODULE_SQLJ = 512;
	public static final int MODULE_JOCI = 1024;
	public static final int MODULE_JPUB = 2048;
	public static final int MODULE_T2C = 4096;
	public static final int MODULE_TOTAL = 13;
	/* 1783 */public static final String[] ModuleName = { "DRVR ", "POOL", "DBCV ", "unused", "PIKL ", "JTTC ", "DATM ", "KPRB ", "XA   ", "SQLJ ", "JOCI ", "JPUB ", "T2C" };
	public static final int SUBMOD_ALL = 268435455;
	public static final int SUBMOD_DEFAULT = 1;
	public static final int SUBMOD_DRVR_LOG = 2;
	public static final int SUBMOD_DRVR_ERR = 4;
	public static final int SUBMOD_DRVR_CONN = 8;
	public static final int SUBMOD_DRVR_STMT = 16;
	public static final int SUBMOD_DRVR_RSET = 32;
	public static final int SUBMOD_DRVR_UTIL = 64;
	public static final int SUBMOD_DRVR_SQL = 128;
	public static final int SUBMOD_DRVR_RWST = 256;
	public static final int SUBMOD_DRVR_POOL = 512;
	public static final int SUBMOD_DRVR_SCCH = 1024;
	public static final int SUBMOD_DRVR_CNTR = 2048;
	public static final int SUBMOD_T2C_DRVEXT = 2;
	public static final int SUBMOD_T2C_STATEMENT = 4;
	public static final int SUBMOD_T2C_PREPSTATEMENT = 8;
	public static final int SUBMOD_T2C_CALLSTATEMENT = 16;
	public static final int SUBMOD_T2C_INPUTSTREAM = 32;
	public static final int SUBMOD_T2C_CLOB = 64;
	public static final int SUBMOD_T2C_BLOB = 128;
	public static final int SUBMOD_T2C_BFILE = 256;
	public static final int SUBMOD_DBAC_DATA = 2;
	public static final int SUBMOD_PCKL_INIT = 2;
	public static final int SUBMOD_PCKL_TYPE = 4;
	public static final int SUBMOD_PCKL_PCKL = 8;
	public static final int SUBMOD_PCKL_UNPK = 16;
	public static final int SUBMOD_PCKL_CONV = 32;
	public static final int SUBMOD_PCKL_DESC = 64;
	public static final int SUBMOD_PCKL_PARS = 128;
	public static final int SUBMOD_PCKL_SERL = 256;
	public static final int SUBMOD_JTTC_BASE = 2;
	public static final int SUBMOD_JTTC_TX = 4;
	public static final int SUBMOD_JTTC_RX = 8;
	public static final int SUBMOD_JTTC_MARS = 16;
	public static final int SUBMOD_JTTC_UNMA = 32;
	public static final int SUBMOD_JTTC_CONN = 64;
	public static final int SUBMOD_JTTC_COMM = 128;
	public static final int SUBMOD_JTTC_STMT = 256;
	public static final int SUBMOD_JTTC_LOBS = 512;
	public static final int SUBMOD_JTTC_ADTS = 1024;
	public static final int SUBMOD_JTTC_ACCE = 2048;
	public static final int SUBMOD_KPRB_ERR = 2;
	public static final int SUBMOD_KPRB_CONN = 4;
	public static final int SUBMOD_KPRB_STMT = 8;
	public static final int SUBMOD_KPRB_RSET = 16;
	public static final int SUBMOD_KPRB_UTIL = 32;
	public static final int SUBMOD_KPRB_SQL = 64;
	public static final int SUBMOD_KPRB_DATA = 128;
	public static final int SUBMOD_KPRB_CONV = 256;
	public static final int SUBMOD_POOL_ALL = 2;
	public static final int SUBMOD_XA_DSRC = 2;
	public static final int SUBMOD_XA_CONN = 4;
	public static final int SUBMOD_XA_RSRC = 8;
	public static final int SUBMOD_XA_ARGS = 16;
	public static final int SUBMOD_XA_EXC = 32;
	public static final int SUBMOD_XA_XID = 64;
	public static final int SUBMOD_XA_HCON = 128;
	public static final int SUBMOD_XA_HCCB = 256;
	public static final int SUBMOD_XA_HRSC = 512;
	public static final int SUBMOD_SQLJ_RUN = 2;
	public static final int SUBMOD_JOCI_ACCS = 2;
	public static final int SUBMOD_JOCI_DSET = 4;
	public static final int SUBMOD_JOCI_ITEM = 8;
	public static final int SUBMOD_JOCI_STMT = 16;
	public static final int SUBMOD_JOCI_TYPE = 32;
	public static final int SUBMOD_JOCI_ENV = 64;
	public static final int SUBMOD_JPUB_RUNTIME = 2;
	public static final int CATEGORY_ALL = 268435455;
	public static final int USER_OPER = 1;
	public static final int PROG_ERROR = 2;
	public static final int ERROR = 4;
	public static final int WARNING = 8;
	public static final int FUNCTION = 16;
	public static final int DEBUG1 = 32;
	public static final int DEBUG2 = 64;
	public static final int SQL_STR = 128;
	public static final int CATEGORY_TOTAL = 8;
	/* 1939 */public static final String[] CategoryName = { "OPER ", "PERR ", "ERRO ", "WARN ", "FUNC ", "DBG1 ", "DBG2 ", "SQLS " };
	public static final int CATEGORY_LOW_VOL = 142;
	public static final int CATEGORY_MED_VOL = 143;
	public static final int CATEGORY_HIGH_VOL = 268435455;
	public static final int FIELD_NONE = 0;
	public static final int FIELD_ALL = 268435455;
	public static final int FIELD_NUMBER = 1;
	public static final int FIELD_TIME = 2;
	public static final int FIELD_MODULE = 4;
	public static final int FIELD_SUBMOD = 8;
	public static final int FIELD_CATEGORY = 16;
	public static final int FIELD_OBJECT = 32;
	public static final int FIELD_THREAD = 64;
	public static final int FIELD_DEFAULT = 20;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	/* 1986 */private static PrintWriter logWriter = null;
	/* 1987 */private static PrintStream logStream = null;
	/* 1988 */private static int printMask = 20;
	/* 1989 */private static int moduleMask = 268435455;
	/* 1990 */private static int[] submodMasks = null;
	/* 1991 */private static int categoryMask = 143;
	/* 1992 */private static int maxPrintBytes = 200;
	/* 1993 */private static boolean warningEnabled = true;
	/* 1994 */private static int msgNumber = 0;
	static boolean securityExceptionWhileGettingSystemProperties;

	public static boolean isDebugZip() {
		/* 263 */int i = 1;

		/* 266 */i = 0;
		/* 267 */return i == 1;
	}

	public static boolean isPrivateLogAvailable() {
		/* 282 */int i = 0;

		/* 286 */return i == 1;
	}

	public static boolean isEnabled() {
		/* 296 */return false;
	}

	/** @deprecated */
	public static void setLogWriter(PrintWriter paramPrintWriter) {
		/* 315 */if (!isDebugZip()) {
			/* 319 */if (paramPrintWriter != null) {
				/* 321 */paramPrintWriter.println("Oracle Jdbc tracing is not avaliable in a non-debug zip/jar file");
				/* 322 */paramPrintWriter.flush();
			}

			/* 325 */return;
		}

		/* 334 */if (paramPrintWriter != null) {
			/* 336 */paramPrintWriter.println("OracleLog.setLogWriter not supported. Use setLogStream instead, or better yet, java.util.logging.");

			/* 338 */paramPrintWriter.flush();
		}
	}

	/** @deprecated */
	public static PrintWriter getLogWriter() {
		/* 357 */return logWriter;
	}

	private static void initLoggers() {
		/* 381 */if (rootLogger == null) {
			/* 383 */rootLogger = Logger.getLogger("oracle.jdbc");
			/* 384 */driverLogger = Logger.getLogger("oracle.jdbc.driver");

			/* 386 */poolLogger = Logger.getLogger("oracle.jdbc.pool");

			/* 388 */conversionLogger = Logger.getLogger("oracle.jdbc.conversion");

			/* 390 */adtLogger = Logger.getLogger("oracle.jdbc.adt");

			/* 392 */thinLogger = Logger.getLogger("oracle.jdbc.thin");

			/* 394 */datumLogger = Logger.getLogger("oracle.jdbc.datum");

			/* 396 */kprbLogger = Logger.getLogger("oracle.jdbc.kprb");

			/* 398 */xaLogger = Logger.getLogger("oracle.jdbc.xa");

			/* 400 */sqljLogger = Logger.getLogger("oracle.jdbc.sqlj");

			/* 402 */ociLogger = Logger.getLogger("oracle.jdbc.oci");

			/* 404 */jpubLogger = Logger.getLogger("oracle.jdbc.jpub");
		}
	}

	/** @deprecated */
	public static void setLogStream(PrintStream paramPrintStream) {
		/* 426 */if (!isDebugZip()) {
			/* 430 */if (paramPrintStream != null) {
				/* 432 */paramPrintStream.println("Oracle Jdbc tracing is not avaliable in a non-debug zip/jar file");
				/* 433 */paramPrintStream.flush();
			}

			/* 436 */return;
		}

		/* 443 */initLoggers();

		/* 445 */if (printHandler != null) {
			OracleLog.rootLogger.removeHandler(printHandler);
		}
		/* 457 */printHandler = new StreamHandler(paramPrintStream, new SimpleFormatter());

		/* 460 */StreamHandler localStreamHandler = printHandler;
		/* 461 */AccessController.doPrivileged(new PrivilegedAction(localStreamHandler) {
			private final StreamHandler val$fprintHandler2;

			/* 465 */public Object run() {
				OracleLog.printHandler.setLevel(Level.FINEST);
				/* 466 */OracleLog.rootLogger.addHandler(this.val$fprintHandler2);
				/* 467 */return null;
			}

		});
		/* 470 */setTrace(true);
	}

	/** @deprecated */
	public static PrintStream getLogStream() {
		/* 488 */return logStream;
	}

	/** @deprecated */
	public static void enableWarning(boolean paramBoolean) {
		/* 505 */warningEnabled = paramBoolean;
	}

	/** @deprecated */
	public static void setLogVolume(int paramInt) {
		/* 531 */print(null, 1, 2, 1, "Set logging volume level to " + paramInt);

		/* 534 */int i = 142;

		/* 536 */switch (paramInt) {
		case 1:
			/* 540 */i = 142;

			/* 542 */break;
		case 2:
			/* 545 */i = 143;

			/* 547 */break;
		case 3:
			/* 550 */i = 268435455;

			/* 552 */break;
		default:
			/* 555 */print(null, 1, 2, 4, "Logging volume level " + paramInt + " is not in the range of 1 to 3");

			/* 558 */return;
		}

		/* 561 */config(printMask, moduleMask, i);
	}

	/** @deprecated */
	public static void startLogging() {
		/* 576 */setLogStream(System.out);
	}

	/** @deprecated */
	public static void stopLogging() {
		/* 591 */setLogStream(null);
	}

	/** @deprecated */
	public static void config(int paramInt1, int paramInt2, int paramInt3) {
		/* 623 */configForJavaUtilLogging(moduleMask, categoryMask);
	}

	static void configForJavaUtilLogging(int paramInt1, int paramInt2) {
		/* 639 */Level localLevel1 = Level.OFF;

		/* 641 */if ((paramInt2 & 0x40) != 0)
			/* 642 */localLevel1 = Level.FINEST;
		/* 643 */else if ((paramInt2 & 0x20) != 0)
			/* 644 */localLevel1 = Level.FINER;
		/* 645 */else if ((paramInt2 & 0x10) != 0)
			/* 646 */localLevel1 = Level.FINE;
		/* 647 */else if ((paramInt2 & 0x1) != 0)
			/* 648 */localLevel1 = Level.INFO;
		/* 649 */else if ((paramInt2 & 0x80) != 0)
			/* 650 */localLevel1 = Level.CONFIG;
		/* 651 */else if ((paramInt2 & 0x8) != 0)
			/* 652 */localLevel1 = Level.WARNING;
		/* 653 */else if ((paramInt2 & 0x2) != 0)
			/* 654 */localLevel1 = Level.SEVERE;
		/* 655 */else if ((paramInt2 & 0x4) != 0) {
			/* 656 */localLevel1 = Level.SEVERE;
		}
		/* 658 */initLoggers();
		/* 659 */Level localLevel2 = localLevel1;
		/* 660 */AccessController.doPrivileged(new PrivilegedAction(localLevel2) {
			private final Level val$flevel;

			/* 664 */public Object run() {
				OracleLog.rootLogger.setLevel(this.val$flevel);
				/* 665 */return null;
			}

		});
		/* 668 */Level localLevel3 = Level.OFF;
		/* 669 */int i = paramInt1;
		/* 670 */AccessController.doPrivileged(new PrivilegedAction(i, localLevel2, localLevel3) {
			private final int val$fmoduleMask;
			private final Level val$flevel;
			private final Level val$off;

			/* 674 */public Object run() {
				OracleLog.driverLogger.setLevel((this.val$fmoduleMask & 0x1) != 0 ? this.val$flevel : this.val$off);

				/* 676 */OracleLog.poolLogger.setLevel((this.val$fmoduleMask & 0x2) != 0 ? this.val$flevel : this.val$off);

				/* 678 */OracleLog.conversionLogger.setLevel((this.val$fmoduleMask & 0x4) != 0 ? this.val$flevel : this.val$off);

				/* 680 */OracleLog.adtLogger.setLevel((this.val$fmoduleMask & 0x10) != 0 ? this.val$flevel : this.val$off);

				/* 682 */OracleLog.thinLogger.setLevel((this.val$fmoduleMask & 0x20) != 0 ? this.val$flevel : this.val$off);

				/* 684 */OracleLog.datumLogger.setLevel((this.val$fmoduleMask & 0x40) != 0 ? this.val$flevel : this.val$off);

				/* 686 */OracleLog.kprbLogger.setLevel((this.val$fmoduleMask & 0x80) != 0 ? this.val$flevel : this.val$off);

				/* 688 */OracleLog.xaLogger.setLevel((this.val$fmoduleMask & 0x100) != 0 ? this.val$flevel : this.val$off);

				/* 690 */OracleLog.sqljLogger.setLevel((this.val$fmoduleMask & 0x200) != 0 ? this.val$flevel : this.val$off);

				/* 692 */OracleLog.ociLogger.setLevel((this.val$fmoduleMask & 0x400) != 0 ? this.val$flevel : this.val$off);

				/* 694 */OracleLog.jpubLogger.setLevel((this.val$fmoduleMask & 0x800) != 0 ? this.val$flevel : this.val$off);

				/* 696 */return null;
			}

		});
	}

	/** @deprecated */
	public static void setSubmodMask(int paramInt1, int paramInt2) {
		/* 722 */int i = getBitNumFromVector(paramInt1);

		/* 724 */submodMasks[i] = paramInt2;

		/* 726 */print(null, 1, 2, 1, "Set logging sub-mask for module " + getMaskHexStr(moduleMask) + "(number " + i + ") to " + getMaskHexStr(paramInt2));
	}

	/** @deprecated */
	public static void setMaxPrintBytes(int paramInt) {
		/* 748 */if (paramInt > 0) {
			/* 749 */maxPrintBytes = paramInt;
		}
		/* 751 */print(null, 1, 2, 1, "Set the maximum number of bytes to be printed to " + maxPrintBytes);
	}

	public static boolean registerClassNameAndGetCurrentTraceSetting(Class paramClass) {
		/* 763 */return false;
	}

	public static boolean registerClassNameAndGetCurrentPrivateTraceSetting(Class paramClass) {
		/* 770 */return false;
	}

	public static void setTrace(boolean paramBoolean) {
	}

	/** @deprecated */
	public static void setPrivateTrace(boolean paramBoolean) {
	}

	private static void initialize() {
		/* 805 */internalCodeChecks();

		/* 811 */setupFromSystemProperties();
	}

	private static void internalCodeChecks() {
		/* 816 */if (ModuleName.length != 13) {
			/* 818 */System.out.println("ERROR: OracleLog.ModuleName[] has " + ModuleName.length + " items (expected " + 13 + ")");
		}

		/* 823 */if (CategoryName.length != 8) {
			/* 825 */System.out.println("ERROR: OracleLog.CategoryName[] has " + ModuleName.length + " items (expected " + 13 + ")");
		}
	}

	/** @deprecated */
	public static void setupFromSystemProperties() {
		/* 844 */int i = 0;

		/* 846 */securityExceptionWhileGettingSystemProperties = false;

		/* 848 */PrintStream localPrintStream = System.out;
		try {
			/* 852 */String str = null;
			/* 853 */str = getSystemProperty("oracle.jdbc.LogFile", null);

			/* 855 */if (str != null) {
				try {
					/* 859 */File localFile = new File(str);

					/* 861 */localPrintStream = new PrintStream(new FileOutputStream(localFile));
					/* 862 */i = 1;
				} catch (IOException localIOException) {
					/* 866 */localIOException.printStackTrace(System.out);
				}

			}

			/* 871 */str = getSystemProperty("oracle.jdbc.Trace", null);

			/* 873 */if ((str != null) && (str.compareTo("true") == 0)) {
				/* 874 */i = 1;
			}
			/* 876 */str = getSystemProperty("oracle.jdbc.PrintMask", null);

			/* 878 */if (str != null) {
				/* 880 */printMask = Integer.parseInt(str, 16);
				/* 881 */i = 1;
			}

			/* 884 */str = getSystemProperty("oracle.jdbc.PrintFields", null);

			/* 886 */if (str != null) {
				/* 888 */if (str.equalsIgnoreCase("default")) {
					/* 889 */printMask = 20;
				}
				/* 891 */if (str.equalsIgnoreCase("all")) {
					/* 892 */printMask = 268435455;
				}
				/* 894 */if (str.equalsIgnoreCase("thread")) {
					/* 895 */printMask = 84;
				}
				/* 897 */if (str.equalsIgnoreCase("none")) {
					/* 898 */printMask = 0;
				}
				/* 900 */i = 1;
			}

			/* 903 */str = getSystemProperty("oracle.jdbc.ModuleMask", null);

			/* 905 */if (str != null) {
				/* 907 */moduleMask = Integer.parseInt(str, 16);
				/* 908 */i = 1;
			}

			/* 911 */str = getSystemProperty("oracle.jdbc.CategoryMask", null);

			/* 913 */if (str != null) {
				/* 915 */categoryMask = Integer.parseInt(str, 16);
				/* 916 */i = 1;
			}

			/* 919 */for (int j = 0; j < 32; j++) {
				/* 921 */str = getSystemProperty("oracle.jdbc.SubmodMask" + j, null);

				/* 924 */if (str == null)
					continue;
				/* 926 */submodMasks[j] = Integer.parseInt(str, 16);
				/* 927 */i = 1;
			}

			/* 931 */str = getSystemProperty("oracle.jdbc.MaxPrintBytes", null);

			/* 933 */if (str != null) {
				/* 935 */maxPrintBytes = Integer.parseInt(str, 10);
				/* 936 */i = 1;
			}
		} catch (SecurityException localSecurityException) {
			/* 941 */securityExceptionWhileGettingSystemProperties = true;
		}

		/* 944 */if (i != 0)
			/* 945 */setLogStream(localPrintStream);
	}

	/** @deprecated */
	private static String getSystemProperty(String paramString) {
		/* 968 */return getSystemProperty(paramString, null);
	}

	/** @deprecated */
	private static String getSystemProperty(String paramString1, String paramString2) {
		/* 981 */if (paramString1 != null) {
			/* 983 */String str1 = paramString1;
			/* 984 */String str2 = paramString2;
			/* 985 */String[] arrayOfString = { paramString2 };
			/* 986 */AccessController.doPrivileged(new PrivilegedAction(arrayOfString, str1, str2) {
				private final String[] val$retStr;
				private final String val$fstr;
				private final String val$fdefaultValue;

				/* 990 */public Object run() {
					this.val$retStr[0] = System.getProperty(this.val$fstr, this.val$fdefaultValue);
					/* 991 */return null;
				}

			});
			/* 994 */return arrayOfString[0];
		}
		/* 996 */return paramString2;
	}

	/** @deprecated */
	public static void print(Object paramObject, int paramInt1, int paramInt2, int paramInt3, String paramString) {
		/* 1056 */if (logWriter == null) {
			/* 1058 */return;
		}

		/* 1061 */int i = 0;

		/* 1072 */if (((paramInt1 & moduleMask) != 0) && ((paramInt2 & submodMasks[getBitNumFromVector(paramInt1)]) != 0) && ((paramInt3 & categoryMask) != 0)) {
			/* 1076 */i = 1;
		}

		/* 1079 */if ((paramInt3 & 0x4) != 0) {
			/* 1081 */i = 1;
		}

		/* 1084 */if ((warningEnabled == true) && ((paramInt3 & 0x8) != 0)) {
			/* 1086 */i = 1;
		}

		/* 1089 */if (i != 0) {
			/* 1091 */StringBuffer localStringBuffer = new StringBuffer("");

			/* 1093 */if ((printMask & 0x1) != 0) {
				/* 1094 */localStringBuffer.append(getMessageNumber());
			}
			/* 1096 */if ((printMask & 0x2) != 0) {
				/* 1097 */localStringBuffer.append(getCurrTimeStr());
			}
			/* 1099 */if ((printMask & 0x4) != 0) {
				/* 1100 */localStringBuffer.append(getModuleName(paramInt1));
			}
			/* 1102 */if ((printMask & 0x8) != 0) {
				/* 1103 */localStringBuffer.append(getBitNumFromVector(paramInt2) + " ");
			}
			/* 1105 */if ((printMask & 0x10) != 0) {
				/* 1106 */localStringBuffer.append(getCategoryName(paramInt3));
			}
			/* 1108 */if ((printMask & 0x40) != 0) {
				/* 1109 */localStringBuffer.append(Thread.currentThread() + "_" + Thread.currentThread().hashCode() + "_");
			}

			/* 1112 */localStringBuffer.append(paramString);

			/* 1114 */if ((printMask & 0x20) != 0) {
				/* 1116 */localStringBuffer.append(" " + paramObject);
			}

			/* 1122 */if (logStream == System.out) {
				/* 1123 */logWriter.println();
			}
			/* 1125 */logWriter.println(localStringBuffer.toString());
			/* 1126 */logWriter.flush();
		}
	}

	/** @deprecated */
	public static void print(Object paramObject, int paramInt1, int paramInt2, int paramInt3, String paramString, Exception paramException) {
		/* 1189 */if (logWriter == null) {
			/* 1191 */return;
		}

		/* 1194 */StringWriter localStringWriter = new StringWriter();
		/* 1195 */PrintWriter localPrintWriter = new PrintWriter(localStringWriter);

		/* 1197 */paramException.printStackTrace(localPrintWriter);
		/* 1198 */print(paramObject, paramInt1, paramInt2, paramInt3, paramString + localStringWriter.toString());
	}

	/** @deprecated */
	public static void print(Object paramObject, int paramInt1, int paramInt2, String paramString) {
		/* 1212 */print(paramObject, paramInt1, 1, paramInt2, paramString);
	}

	/** @deprecated */
	public static String info() {
		/* 1229 */String str = null;

		/* 1231 */if (isEnabled()) {
			/* 1233 */str = "Enabled logging (moduleMask " + getMaskHexStr(moduleMask) + ", categoryMask " + getMaskHexStr(categoryMask) + ")";
		} else {
			/* 1238 */str = "Disabled logging";
		}

		/* 1241 */return str;
	}

	/** @deprecated */
	public static String getModuleName(int paramInt) {
		/* 1261 */String str = null;
		/* 1262 */int i = 1;

		/* 1264 */for (int j = 0; j < 13; j++) {
			/* 1266 */if ((i & paramInt) != 0) {
				/* 1268 */str = ModuleName[j];

				/* 1270 */break;
			}

			/* 1273 */i <<= 1;
		}

		/* 1276 */return str;
	}

	/** @deprecated */
	public static String getCategoryName(int paramInt) {
		/* 1296 */String str = null;
		/* 1297 */int i = 1;

		/* 1299 */for (int j = 0; j < 8; j++) {
			/* 1301 */if ((i & paramInt) != 0) {
				/* 1303 */str = CategoryName[j];

				/* 1305 */break;
			}

			/* 1308 */i <<= 1;
		}

		/* 1311 */return str;
	}

	private static String getMessageNumber() {
		/* 1322 */StringBuffer localStringBuffer = new StringBuffer("");
		int i;
		/* 1327 */synchronized (logWriter) {
			/* 1329 */if (msgNumber == 2147483647) {
				/* 1331 */msgNumber = 0;
			} else {
				/* 1335 */msgNumber += 1;
			}

			/* 1338 */i = msgNumber;
		}

		/* 1341 */String str = Integer.toString(i);
		/* 1342 */int j = str.length();

		/* 1344 */for (int k = j; k < 10; k++) {
			/* 1346 */localStringBuffer.append("0");
		}

		/* 1349 */localStringBuffer.append(str);
		/* 1350 */localStringBuffer.append(" ");

		/* 1352 */return localStringBuffer.toString();
	}

	private static String getCurrTimeStr() {
		/* 1362 */Date localDate = new Date();

		/* 1364 */return localDate.toString() + " ";
	}

	/** @deprecated */
	public static String getMaskHexStr(int paramInt) {
		/* 1383 */int i = 8;
		/* 1384 */String str1 = Integer.toHexString(paramInt);
		/* 1385 */char[] arrayOfChar = new char[i - str1.length()];

		/* 1387 */for (int j = 0; j < arrayOfChar.length; j++) {
			/* 1389 */arrayOfChar[j] = '0';
		}

		/* 1392 */String str2 = new String(arrayOfChar);

		/* 1394 */return new String("0x" + str2 + str1);
	}

	/** @deprecated */
	public static int getBitNumFromVector(int paramInt) {
		/* 1414 */int i = 0;
		/* 1415 */int j = 1;

		/* 1417 */for (int k = 0; k < 32; k++) {
			/* 1419 */if ((paramInt & j) != 0) {
				/* 1421 */i = k;

				/* 1423 */break;
			}

			/* 1426 */j <<= 1;
		}

		/* 1429 */return i;
	}

	/** @deprecated */
	public static String byteToHexString(byte paramByte) {
		/* 1456 */StringBuffer localStringBuffer = new StringBuffer("");
		/* 1457 */int i = 0xFF & paramByte;

		/* 1459 */if (i <= 15)
			/* 1460 */localStringBuffer.append("0x0");
		else {
			/* 1462 */localStringBuffer.append("0x");
		}
		/* 1464 */localStringBuffer.append(Integer.toHexString(i));

		/* 1466 */return localStringBuffer.toString();
	}

	/** @deprecated */
	public static String bytesToPrintableForm(String paramString, byte[] paramArrayOfByte) {
		/* 1491 */int i = paramArrayOfByte == null ? 0 : paramArrayOfByte.length;

		/* 1493 */return bytesToPrintableForm(paramString, paramArrayOfByte, i);
	}

	/** @deprecated */
	public static String bytesToPrintableForm(String paramString, byte[] paramArrayOfByte, int paramInt) {
		/* 1521 */String str = null;

		/* 1523 */if (paramArrayOfByte == null)
			/* 1524 */str = paramString + ": null";
		else {
			/* 1526 */str = paramString + " (" + paramArrayOfByte.length + " bytes):\n" + bytesToFormattedStr(paramArrayOfByte, paramInt, "  ");
		}

		/* 1529 */return str;
	}

	/** @deprecated */
	public static String bytesToFormattedStr(byte[] paramArrayOfByte, int paramInt, String paramString) {
		/* 1560 */StringBuffer localStringBuffer = new StringBuffer("");

		/* 1562 */if (paramString == null) {
			/* 1563 */paramString = new String("");
		}
		/* 1565 */localStringBuffer.append(paramString);

		/* 1567 */if (paramArrayOfByte == null) {
			/* 1569 */localStringBuffer.append("byte [] is null");

			/* 1571 */return localStringBuffer.toString();
		}

		/* 1574 */for (int i = 0; i < paramInt; i++) {
			/* 1576 */if (i >= maxPrintBytes) {
				/* 1578 */localStringBuffer.append("\n" + paramString + "... last " + (paramInt - maxPrintBytes) + " bytes were not printed to limit the output size");

				/* 1581 */break;
			}

			/* 1584 */if ((i > 0) && (i % 20 == 0)) {
				/* 1585 */localStringBuffer.append("\n" + paramString);
			}
			/* 1587 */if (i % 20 == 10) {
				/* 1588 */localStringBuffer.append(" ");
			}
			/* 1590 */int j = 0xFF & paramArrayOfByte[i];

			/* 1592 */if (j <= 15) {
				/* 1593 */localStringBuffer.append("0");
			}
			/* 1595 */localStringBuffer.append(Integer.toHexString(j) + " ");
		}

		/* 1598 */return localStringBuffer.toString();
	}

	/** @deprecated */
	public static byte[] strToUcs2Bytes(String paramString) {
		/* 1619 */if (paramString == null) {
			/* 1620 */return null;
		}
		/* 1622 */return charsToUcs2Bytes(paramString.toCharArray());
	}

	/** @deprecated */
	public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar) {
		/* 1643 */if (paramArrayOfChar == null) {
			/* 1644 */return null;
		}
		/* 1646 */return charsToUcs2Bytes(paramArrayOfChar, paramArrayOfChar.length);
	}

	/** @deprecated */
	public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar, int paramInt) {
		/* 1669 */if (paramArrayOfChar == null) {
			/* 1670 */return null;
		}
		/* 1672 */if (paramInt < 0) {
			/* 1673 */return null;
		}
		/* 1675 */return charsToUcs2Bytes(paramArrayOfChar, paramInt, 0);
	}

	/** @deprecated */
	public static byte[] charsToUcs2Bytes(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
		/* 1691 */if (paramArrayOfChar == null) {
			/* 1692 */return null;
		}
		/* 1694 */if (paramInt1 > paramArrayOfChar.length - paramInt2) {
			/* 1695 */paramInt1 = paramArrayOfChar.length - paramInt2;
		}
		/* 1697 */if (paramInt1 < 0) {
			/* 1698 */return null;
		}
		/* 1700 */byte[] arrayOfByte = new byte[2 * paramInt1];

		/* 1702 */int j = paramInt2;
		for (int i = 0; j < paramInt1; j++) {
			/* 1704 */arrayOfByte[(i++)] = (byte) (paramArrayOfChar[j] >> '\b' & 0xFF);
			/* 1705 */arrayOfByte[(i++)] = (byte) (paramArrayOfChar[j] & 0xFF);
		}

		/* 1708 */return arrayOfByte;
	}

	/** @deprecated */
	public static String toPrintableStr(String paramString, int paramInt) {
		/* 1730 */if (paramString == null) {
			/* 1732 */return "null";
		}

		/* 1735 */if (paramString.length() > paramInt) {
			/* 1737 */return paramString.substring(0, paramInt - 1) + "\n ... the actual length was " + paramString.length();
		}

		/* 1741 */return paramString;
	}

	public static String toHex(long paramLong, int paramInt) {
		String str;
		/* 2116 */switch (paramInt) {
		case 1:
			/* 2120 */str = "00" + Long.toString(paramLong & 0xFF, 16);

			/* 2122 */break;
		case 2:
			/* 2125 */str = "0000" + Long.toString(paramLong & 0xFFFF, 16);

			/* 2127 */break;
		case 3:
			/* 2130 */str = "000000" + Long.toString(paramLong & 0xFFFFFF, 16);

			/* 2132 */break;
		case 4:
			/* 2135 */str = "00000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

			/* 2137 */break;
		case 5:
			/* 2140 */str = "0000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

			/* 2142 */break;
		case 6:
			/* 2145 */str = "000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

			/* 2147 */break;
		case 7:
			/* 2150 */str = "00000000000000" + Long.toString(paramLong & 0xFFFFFFFF, 16);

			/* 2153 */break;
		case 8:
			/* 2156 */return toHex(paramLong >> 32, 4) + toHex(paramLong, 4).substring(2);
		default:
			/* 2160 */return "more than 8 bytes";
		}

		/* 2163 */return "0x" + str.substring(str.length() - 2 * paramInt);
	}

	public static String toHex(byte paramByte) {
		/* 2168 */String str = "00" + Integer.toHexString(paramByte & 0xFF);

		/* 2170 */return "0x" + str.substring(str.length() - 2);
	}

	public static String toHex(short paramShort) {
		/* 2175 */return toHex(paramShort, 2);
	}

	public static String toHex(int paramInt) {
		/* 2180 */return toHex(paramInt, 4);
	}

	public static String toHex(byte[] paramArrayOfByte, int paramInt) {
		/* 2185 */if (paramArrayOfByte == null) {
			/* 2186 */return "null";
		}
		/* 2188 */if (paramInt > paramArrayOfByte.length) {
			/* 2189 */return "byte array not long enough";
		}
		/* 2191 */String str = "[";
		/* 2192 */int i = Math.min(64, paramInt);

		/* 2194 */for (int j = 0; j < i; j++) {
			/* 2196 */str = str + toHex(paramArrayOfByte[j]) + " ";
		}

		/* 2199 */if (i < paramInt) {
			/* 2200 */str = str + "...";
		}
		/* 2202 */return str + "]";
	}

	public static String toHex(byte[] paramArrayOfByte) {
		/* 2207 */if (paramArrayOfByte == null) {
			/* 2208 */return "null";
		}
		/* 2210 */return toHex(paramArrayOfByte, paramArrayOfByte.length);
	}

	static {
		/* 1998 */submodMasks = new int[32];

		/* 2000 */for (int i = 0; i < 32; i++) {
			/* 2002 */submodMasks[i] = 268435455;
		}

		/* 2013 */initialize();
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.OracleLog
 * JD-Core Version: 0.6.0
 */