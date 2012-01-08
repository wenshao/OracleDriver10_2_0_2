package oracle.jdbc.xa.client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;

import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.driver.T2CConnection;
import oracle.jdbc.driver.T4CXAConnection;

public class OracleXADataSource extends oracle.jdbc.xa.OracleXADataSource {
	private static final boolean DEBUG = false;
	/* 46 */private int rmid = -1;
	/* 47 */private String xaOpenString = null;
	/* 48 */private static boolean libraryLoaded = false;
	private static final String dbSuffix = "HeteroXA";
	private static final String dllName = "heteroxa10";
	private static final char atSignChar = '@';
	/* 52 */private static int rmidSeed = 0;
	private static final int MAX_RMID_SEED = 65536;
	/* 56 */private String driverCharSetIdString = null;

	/* 60 */private String oldTnsEntry = null;

	/* 684 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	public OracleXADataSource() throws SQLException {
		/* 72 */this.isOracleDataSource = true;
	}

	public XAConnection getXAConnection() throws SQLException {
		/* 92 */Properties localProperties = new Properties(this.connectionProperties);
		/* 93 */if ((this.user == null) || (this.password == null)) {
			/* 96 */if ((this.connectionProperties == null) || (this.connectionProperties.get("user") == null) || (this.connectionProperties.get("password") == null)) {
				/* 99 */DatabaseError.throwSqlException(68);
			}
		} else {
			/* 103 */localProperties.setProperty("user", this.user);
			/* 104 */localProperties.setProperty("password", this.password);
		}

		/* 107 */return getXAConnection(localProperties);
	}

	public XAConnection getXAConnection(String paramString1, String paramString2) throws SQLException {
		/* 125 */Properties localProperties = new Properties(this.connectionProperties);
		/* 126 */if ((paramString1 != null) && (paramString2 != null)) {
			/* 128 */localProperties.setProperty("user", paramString1);
			/* 129 */localProperties.setProperty("password", paramString2);
		} else {
			/* 131 */DatabaseError.throwSqlException(68);
		}
		/* 133 */return getXAConnection(localProperties);
	}

	public XAConnection getXAConnection(Properties paramProperties) throws SQLException {
		/* 148 */if (this.connCachingEnabled) {
			/* 150 */DatabaseError.throwSqlException(163);

			/* 154 */return null;
		}

		/* 159 */return (XAConnection) getPooledConnection(paramProperties);
	}

	public PooledConnection getPooledConnection(String paramString1, String paramString2) throws SQLException {
		/* 180 */Properties localProperties = new Properties();
		/* 181 */localProperties.setProperty("user", paramString1);
		/* 182 */localProperties.setProperty("password", paramString2);
		/* 183 */return getPooledConnection(localProperties);
	}

	public PooledConnection getPooledConnection(Properties paramProperties)
     throws SQLException
   {
     try
     {
/* 204 */       String str1 = getURL();
/* 205 */       String str2 = paramProperties.getProperty("user");
/* 206 */       String str3 = paramProperties.getProperty("password");
/* 207 */       String str4 = null;
/* 208 */       String str5 = null;
/* 209 */       String str6 = null;
/* 210 */       int i = 0;
 
/* 216 */       if ((this.useNativeXA) && ((str1.startsWith("jdbc:oracle:oci8")) || (str1.startsWith("jdbc:oracle:oci"))))
       {
/* 220 */         long[] localObject1 = new long[] { 0L, 0L };
 
/* 225 */         String str7 = null;
/* 226 */         String str8 = null;
 
/* 228 */         synchronized (this)
         {
/* 231 */           if (this.tnsEntry != null)
/* 232 */             str7 = this.tnsEntry;
           else {
/* 234 */             str7 = getTNSEntryFromUrl(str1);
           }
 
/* 237 */           if (((str7 != null) && (str7.length() == 0)) || (str7.startsWith("(DESCRIPTION")))
           {
/* 241 */             DatabaseError.throwSqlException(207);
           }
 
/* 245 */           if (!libraryLoaded)
           {
/* 247 */             synchronized (getClass())
             {
/* 249 */               if (!libraryLoaded)
               {
                 try
                 {
/* 253 */                   System.loadLibrary("heteroxa10");
 
/* 255 */                   libraryLoaded = true;
                 }
                 catch (Error localError)
                 {
/* 266 */                   libraryLoaded = false;
 
/* 268 */                   throw localError;
                 }
 
               }
 
             }
 
           }
 
/* 279 */           if (this.connectionProperties != null)
           {
/* 281 */             str8 = this.connectionProperties.getProperty("oracle.jdbc.ociNlsLangBackwardCompatible");
           }
 
         }
 
/* 286 */         if ((str8 != null) && (str8.equalsIgnoreCase("true")))
         {
/* 293 */           int v = T2CConnection.getDriverCharSetIdFromNLS_LANG(null);
/* 294 */           this.driverCharSetIdString = Integer.toString(v);
         }
/* 298 */         else if (!str7.equals(this.oldTnsEntry))
         {
/* 301 */           int v = T2CConnection.getClientCharSetId();
 
/* 303 */           this.driverCharSetIdString = Integer.toString(v);
/* 304 */           this.oldTnsEntry = str7;
         }
 
/* 308 */         synchronized (this)
         {
/* 314 */           str4 = this.databaseName + "HeteroXA" + rmidSeed;
 
/* 316 */           this.rmid = (i = rmidSeed);
 
/* 318 */           synchronized (getClass())
           {
/* 320 */             rmidSeed = (rmidSeed + 1) % 65536;
           }
 
/* 323 */           int k = 0;
 
/* 352 */           String localOracleXAHeteroConnection = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;
 
/* 357 */           this.xaOpenString = (str6 = generateXAOpenString(str4, str7, str2, str3, 60, 2000, true, true, ".", k, false, (localOracleXAHeteroConnection != null) && (localOracleXAHeteroConnection.equalsIgnoreCase("true")), this.driverCharSetIdString, this.driverCharSetIdString));
 
/* 365 */           str5 = generateXACloseString(str4, false);
         }
 
/* 371 */         int j = doXaOpen(str6, i, 0, 0);
 
/* 374 */         if (j != 0)
         {
/* 376 */           DatabaseError.throwSqlException(-1 * j);
         }
 
/* 386 */         j = convertOciHandles(str4, localObject1);
 
/* 388 */         if (j != 0)
         {
/* 394 */           DatabaseError.throwSqlException(-1 * j);
         }
 
/* 406 */         paramProperties.put("OCISvcCtxHandle", String.valueOf(localObject1[0]));
/* 407 */         paramProperties.put("OCIEnvHandle", String.valueOf(localObject1[1]));
/* 408 */         paramProperties.put("JDBCDriverCharSetId", this.driverCharSetIdString);
 
/* 410 */         if (this.loginTimeout != 0) {
/* 411 */           paramProperties.put("oracle.net.CONNECT_TIMEOUT", "" + this.loginTimeout * 1000);
         }
 
/* 417 */         Connection localConnection = this.driver.connect(getURL(), paramProperties);
 
/* 421 */         OracleXAHeteroConnection localOracleXAHeteroConnection = new OracleXAHeteroConnection(localConnection);
 
/* 423 */         localOracleXAHeteroConnection.setUserName(str2, str3.toUpperCase());
/* 424 */         localOracleXAHeteroConnection.setRmid(i);
/* 425 */         localOracleXAHeteroConnection.setXaCloseString(str5);
/* 426 */         localOracleXAHeteroConnection.registerCloseCallback(new OracleXAHeteroCloseCallback(), localOracleXAHeteroConnection);
 
/* 431 */         return localOracleXAHeteroConnection;
       }
/* 433 */       if ((this.thinUseNativeXA) && (str1.startsWith("jdbc:oracle:thin")))
       {
/* 440 */         Properties localObject1 = new Properties();
/* 441 */         synchronized (this)
         {
/* 443 */           synchronized (getClass())
           {
/* 445 */             rmidSeed = (rmidSeed + 1) % 65536;
           }
 
/* 449 */           this.rmid = rmidSeed;
 
/* 451 */           if (this.connectionProperties == null) {
/* 452 */             this.connectionProperties = new Properties();
           }
/* 454 */           this.connectionProperties.put("RessourceManagerId", Integer.toString(this.rmid));
/* 455 */           if (str2 != null)
/* 456 */             ((Properties)localObject1).setProperty("user", str2);
/* 457 */           if (str3 != null)
/* 458 */             ((Properties)localObject1).setProperty("password", str3);
/* 459 */           ((Properties)localObject1).setProperty("stmt_cache_size", "" + this.maxStatements);
 
/* 461 */           ((Properties)localObject1).setProperty("ImplicitStatementCachingEnabled", "" + this.implicitCachingEnabled);
 
/* 464 */           ((Properties)localObject1).setProperty("ExplicitStatementCachingEnabled", "" + this.explicitCachingEnabled);
 
/* 467 */           ((Properties)localObject1).setProperty("LoginTimeout", "" + this.loginTimeout);
         }
 
/* 472 */         T4CXAConnection xaConn = new T4CXAConnection(super.getPhysicalConnection((Properties)localObject1));
 
/* 478 */         ((T4CXAConnection)xaConn).setUserName(str2, str3.toUpperCase());
 
/* 480 */         String p = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;
 
/* 485 */         ((OracleXAConnection) xaConn).isXAResourceTransLoose = ((p != null) && ((((String)p).equals("true")) || (((String)p).equalsIgnoreCase("true"))));
 
/* 488 */         return xaConn;
       }
 
/* 496 */       Object localObject1 = new Properties();
/* 497 */       synchronized (this)
       {
/* 499 */         if (str2 != null)
/* 500 */           ((Properties)localObject1).setProperty("user", str2);
/* 501 */         if (str3 != null)
/* 502 */           ((Properties)localObject1).setProperty("password", str3);
/* 503 */         ((Properties)localObject1).setProperty("stmt_cache_size", "" + this.maxStatements);
 
/* 505 */         ((Properties)localObject1).setProperty("ImplicitStatementCachingEnabled", "" + this.implicitCachingEnabled);
 
/* 508 */         ((Properties)localObject1).setProperty("ExplicitStatementCachingEnabled", "" + this.explicitCachingEnabled);
 
/* 511 */         ((Properties)localObject1).setProperty("LoginTimeout", "" + this.loginTimeout);
       }
 
/* 515 */       OracleXAConnection xaConn = new OracleXAConnection(super.getPhysicalConnection((Properties)localObject1));
 
/* 520 */       ((OracleXAConnection)xaConn).setUserName(str2, str3.toUpperCase());
 
/* 522 */       String p = this.connectionProperties != null ? this.connectionProperties.getProperty("oracle.jdbc.XATransLoose") : null;
 
/* 527 */       ((OracleXAConnection)xaConn).isXAResourceTransLoose = ((p != null) && ((((String)p).equals("true")) || (((String)p).equalsIgnoreCase("true"))));
 
/* 531 */       return xaConn;
     }
 catch (XAException localXAException)
     {
     }
 
/* 548 */     return (PooledConnection)(PooledConnection)(PooledConnection)null;
   }

	private native int doXaOpen(String paramString, int paramInt1, int paramInt2, int paramInt3);

	private native int convertOciHandles(String paramString, long[] paramArrayOfLong);

	synchronized void setRmid(int paramInt) {
		/* 578 */this.rmid = paramInt;
	}

	synchronized int getRmid() {
		/* 593 */return this.rmid;
	}

	synchronized void setXaOpenString(String paramString) {
		/* 610 */this.xaOpenString = paramString;
	}

	synchronized String getXaOpenString() {
		/* 625 */return this.xaOpenString;
	}

	private String generateXAOpenString(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, boolean paramBoolean1,
			boolean paramBoolean2, String paramString5, int paramInt3, boolean paramBoolean3, boolean paramBoolean4, String paramString6, String paramString7) {
		/* 650 */return new String("ORACLE_XA+DB=" + paramString1 + "+ACC=P/" + paramString3 + "/" + paramString4 + "+SESTM=" + paramInt2 + "+SESWT=" + paramInt1 + "+LOGDIR="
				+ paramString5 + "+SQLNET=" + paramString2 + (paramBoolean1 ? "+THREADS=true" : "") + (paramBoolean2 ? "+OBJECTS=true" : "") + "+DBGFL=0x" + paramInt3
				+ (paramBoolean3 ? "+CONNCACHE=t" : "+CONNCACHE=f") + (paramBoolean4 ? "+Loose_Coupling=t" : "") + "+CharSet=" + paramString6 + "+NCharSet=" + paramString7);
	}

	private String generateXACloseString(String paramString, boolean paramBoolean) {
		/* 667 */return new String("ORACLE_XA+DB=" + paramString + (paramBoolean ? "+CONNCACHE=t" : "+CONNCACHE=f"));
	}

	private String getTNSEntryFromUrl(String paramString) {
		/* 677 */int i = paramString.indexOf('@');

		/* 679 */return paramString.substring(i + 1);
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.xa.client.OracleXADataSource JD-Core Version: 0.6.0
 */