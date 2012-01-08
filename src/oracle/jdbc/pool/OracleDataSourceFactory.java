/*     */ package oracle.jdbc.pool;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.StringRefAddr;
/*     */ import javax.naming.spi.ObjectFactory;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.xa.client.OracleXADataSource;
/*     */ 
/*     */ public class OracleDataSourceFactory
/*     */   implements ObjectFactory
/*     */ {
/* 416 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";
/*     */ 
/*     */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable paramHashtable)
/*     */     throws Exception
/*     */   {
/*  45 */     Reference localReference = (Reference)paramObject;
/*  46 */     Object localObject1 = null;
/*  47 */     String str1 = localReference.getClassName();
/*  48 */     Properties localProperties = new Properties();
/*     */     Object localObject2;
/*     */     String str2;
/*     */     Object localObject3;
/*  50 */     if ((str1.equals("oracle.jdbc.pool.OracleDataSource")) || (str1.equals("oracle.jdbc.xa.client.OracleXADataSource")))
/*     */     {
/*  53 */       if (str1.equals("oracle.jdbc.pool.OracleDataSource"))
/*  54 */         localObject1 = new OracleDataSource();
/*     */       else {
/*  56 */         localObject1 = new OracleXADataSource();
/*     */       }
/*  58 */       localObject2 = null;
/*     */ 
/*  61 */       if ((localObject2 = (StringRefAddr)localReference.get("connectionCachingEnabled")) != null)
/*     */       {
/*  63 */         str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/*  65 */         if (str2.equals(String.valueOf("true"))) {
/*  66 */           ((OracleDataSource)localObject1).setConnectionCachingEnabled(true);
/*     */         }
/*     */       }
/*  69 */       if ((localObject2 = (StringRefAddr)localReference.get("connectionCacheName")) != null)
/*     */       {
/*  71 */         ((OracleDataSource)localObject1).setConnectionCacheName((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/*  74 */       if ((localObject2 = (StringRefAddr)localReference.get("connectionCacheProperties")) != null)
/*     */       {
/*  76 */         str2 = (String)((StringRefAddr)localObject2).getContent();
/*  77 */         localObject3 = extractConnectionCacheProperties(str2);
/*     */ 
/*  79 */         ((OracleDataSource)localObject1).setConnectionCacheProperties((Properties)localObject3);
/*     */       }
/*     */ 
/*  82 */       if ((localObject2 = (StringRefAddr)localReference.get("fastConnectionFailoverEnabled")) != null)
/*     */       {
/*  85 */         str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/*  87 */         if (str2.equals(String.valueOf("true"))) {
/*  88 */           ((OracleDataSource)localObject1).setFastConnectionFailoverEnabled(true);
/*     */         }
/*     */       }
/*  91 */       if ((localObject2 = (StringRefAddr)localReference.get("onsConfigStr")) != null)
/*     */       {
/*  93 */         ((OracleDataSource)localObject1).setONSConfiguration((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */     }
/*  96 */     else if (str1.equals("oracle.jdbc.pool.OracleConnectionPoolDataSource")) {
/*  97 */       localObject1 = new OracleConnectionPoolDataSource();
/*  98 */     } else if (str1.equals("oracle.jdbc.pool.OracleConnectionCacheImpl"))
/*     */     {
/* 100 */       localObject1 = new OracleConnectionCacheImpl();
/*     */     }
/* 107 */     else if (str1.equals("oracle.jdbc.pool.OracleOCIConnectionPool"))
/*     */     {
/* 110 */       localObject1 = new OracleOCIConnectionPool();
/*     */ 
/* 112 */       localObject2 = null;
/* 113 */       str2 = null;
/* 114 */       localObject3 = null;
/* 115 */       String str3 = null;
/* 116 */       String str4 = null;
/* 117 */       String str5 = null;
/* 118 */       String str6 = null;
/* 119 */       StringRefAddr localStringRefAddr = null;
/*     */ 
/* 121 */       Object localObject4 = null;
/* 122 */       String str7 = null;
/*     */ 
/* 126 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_MIN_LIMIT)) != null)
/*     */       {
/* 128 */         localObject2 = (String)localStringRefAddr.getContent();
/*     */       }
/* 130 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_MAX_LIMIT)) != null)
/*     */       {
/* 132 */         str2 = (String)localStringRefAddr.getContent();
/*     */       }
/* 134 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_INCREMENT)) != null)
/*     */       {
/* 136 */         localObject3 = (String)localStringRefAddr.getContent();
/*     */       }
/* 138 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_ACTIVE_SIZE)) != null)
/*     */       {
/* 140 */         str3 = (String)localStringRefAddr.getContent();
/*     */       }
/* 142 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_POOL_SIZE)) != null)
/*     */       {
/* 144 */         str4 = (String)localStringRefAddr.getContent();
/*     */       }
/* 146 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_TIMEOUT)) != null)
/*     */       {
/* 148 */         str5 = (String)localStringRefAddr.getContent();
/*     */       }
/* 150 */       if ((localStringRefAddr = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_NOWAIT)) != null)
/*     */       {
/* 152 */         str6 = (String)localStringRefAddr.getContent();
/*     */       }
/* 154 */       if ((localStringRefAddr = (StringRefAddr)localReference.get("transactions_distributed")) != null)
/*     */       {
/* 156 */         str7 = (String)localStringRefAddr.getContent();
/*     */       }
/*     */ 
/* 160 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_MIN_LIMIT, localObject2);
/* 161 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_MAX_LIMIT, str2);
/* 162 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_INCREMENT, localObject3);
/* 163 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_ACTIVE_SIZE, str3);
/*     */ 
/* 165 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_POOL_SIZE, str4);
/* 166 */       localProperties.put(OracleOCIConnectionPool.CONNPOOL_TIMEOUT, str5);
/*     */ 
/* 168 */       if (str6 == "true") {
/* 169 */         localProperties.put(OracleOCIConnectionPool.CONNPOOL_NOWAIT, str6);
/*     */       }
/* 171 */       if (str7 == "true") {
/* 172 */         localProperties.put("transactions_distributed", str7);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 179 */       return null;
/*     */     }
/* 181 */     if (localObject1 != null)
/*     */     {
/* 185 */       localObject2 = null;
/*     */ 
/* 187 */       if ((localObject2 = (StringRefAddr)localReference.get("url")) != null)
/*     */       {
/* 189 */         ((OracleDataSource)localObject1).setURL((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 192 */       if (((localObject2 = (StringRefAddr)localReference.get("userName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("u")) != null) || ((localObject2 = (StringRefAddr)localReference.get("user")) != null))
/*     */       {
/* 196 */         ((OracleDataSource)localObject1).setUser((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 199 */       if (((localObject2 = (StringRefAddr)localReference.get("passWord")) != null) || ((localObject2 = (StringRefAddr)localReference.get("password")) != null))
/*     */       {
/* 202 */         ((OracleDataSource)localObject1).setPassword((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 205 */       if (((localObject2 = (StringRefAddr)localReference.get("description")) != null) || ((localObject2 = (StringRefAddr)localReference.get("describe")) != null))
/*     */       {
/* 207 */         ((OracleDataSource)localObject1).setDescription((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/* 209 */       if (((localObject2 = (StringRefAddr)localReference.get("driverType")) != null) || ((localObject2 = (StringRefAddr)localReference.get("driver")) != null))
/*     */       {
/* 212 */         ((OracleDataSource)localObject1).setDriverType((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 215 */       if (((localObject2 = (StringRefAddr)localReference.get("serverName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("host")) != null))
/*     */       {
/* 218 */         ((OracleDataSource)localObject1).setServerName((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 221 */       if (((localObject2 = (StringRefAddr)localReference.get("databaseName")) != null) || ((localObject2 = (StringRefAddr)localReference.get("sid")) != null))
/*     */       {
/* 224 */         ((OracleDataSource)localObject1).setDatabaseName((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 227 */       if (((localObject2 = (StringRefAddr)localReference.get("networkProtocol")) != null) || ((localObject2 = (StringRefAddr)localReference.get("protocol")) != null))
/*     */       {
/* 230 */         ((OracleDataSource)localObject1).setNetworkProtocol((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 233 */       if (((localObject2 = (StringRefAddr)localReference.get("portNumber")) != null) || ((localObject2 = (StringRefAddr)localReference.get("port")) != null))
/*     */       {
/* 236 */         str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/* 238 */         ((OracleDataSource)localObject1).setPortNumber(Integer.parseInt(str2));
/*     */       }
/*     */ 
/* 241 */       if (((localObject2 = (StringRefAddr)localReference.get("tnsentryname")) != null) || ((localObject2 = (StringRefAddr)localReference.get("tns")) != null))
/*     */       {
/* 244 */         ((OracleDataSource)localObject1).setTNSEntryName((String)((StringRefAddr)localObject2).getContent());
/*     */       }
/*     */ 
/* 247 */       if (str1.equals("oracle.jdbc.pool.OracleConnectionCacheImpl"))
/*     */       {
/* 252 */         str2 = null;
/*     */ 
/* 254 */         if ((localObject2 = (StringRefAddr)localReference.get("minLimit")) != null)
/*     */         {
/* 256 */           str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/* 258 */           ((OracleConnectionCacheImpl)localObject1).setMinLimit(Integer.parseInt(str2));
/*     */         }
/*     */ 
/* 261 */         if ((localObject2 = (StringRefAddr)localReference.get("maxLimit")) != null)
/*     */         {
/* 263 */           str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/* 265 */           ((OracleConnectionCacheImpl)localObject1).setMaxLimit(Integer.parseInt(str2));
/*     */         }
/*     */ 
/* 268 */         if ((localObject2 = (StringRefAddr)localReference.get("cacheScheme")) != null)
/*     */         {
/* 270 */           str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */ 
/* 272 */           ((OracleConnectionCacheImpl)localObject1).setCacheScheme(Integer.parseInt(str2));
/*     */         }
/*     */ 
/*     */       }
/* 276 */       else if (str1.equals("oracle.jdbc.pool.OracleOCIConnectionPool"))
/*     */       {
/* 293 */         str2 = null;
/*     */ 
/* 295 */         if ((localObject2 = (StringRefAddr)localReference.get(OracleOCIConnectionPool.CONNPOOL_IS_POOLCREATED)) != null)
/*     */         {
/* 297 */           str2 = (String)((StringRefAddr)localObject2).getContent();
/*     */         }
/* 299 */         if (str2.equals(String.valueOf("true"))) {
/* 300 */           ((OracleOCIConnectionPool)localObject1).setPoolConfig(localProperties);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 307 */     return localObject1;
/*     */   }
/*     */ 
/*     */   private Properties extractConnectionCacheProperties(String paramString)
/*     */     throws SQLException
/*     */   {
/* 325 */     Properties localProperties = new Properties();
/*     */ 
/* 328 */     paramString = paramString.substring(1, paramString.length() - 1);
/*     */ 
/* 331 */     int i = paramString.indexOf("AttributeWeights", 0);
/*     */     String str1;
/*     */     String str3;
/* 333 */     if (i >= 0)
/*     */     {
/* 342 */       if ((paramString.charAt(i + 16) != '=') || ((i > 0) && (paramString.charAt(i - 1) != ' ')))
/*     */       {
/* 345 */         DatabaseError.throwSqlException(139);
/*     */       }
/*     */ 
/* 348 */       localObject1 = new Properties();
/* 349 */       int j = paramString.indexOf("}", i);
/* 350 */       str1 = paramString.substring(i, j);
/*     */ 
/* 353 */       String str2 = str1.substring(18);
/*     */ 
/* 355 */       StringTokenizer localStringTokenizer = new StringTokenizer(str2, ", ");
/*     */ 
/* 358 */       synchronized (localStringTokenizer)
/*     */       {
/* 360 */         while (localStringTokenizer.hasMoreTokens())
/*     */         {
/* 362 */           str3 = localStringTokenizer.nextToken();
/* 363 */           int n = str3.length();
/* 364 */           int i1 = str3.indexOf("=");
/*     */ 
/* 366 */           String str4 = str3.substring(0, i1);
/* 367 */           String str5 = str3.substring(i1 + 1, n);
/*     */ 
/* 369 */           ((Properties)localObject1).setProperty(str4, str5);
/*     */         }
/*     */       }
/*     */ 
/* 373 */       localProperties.put("AttributeWeights", localObject1);
/*     */ 
/* 376 */       if ((i > 0) && (j + 1 == paramString.length()))
/*     */       {
/* 378 */         paramString = paramString.substring(0, i - 2);
/*     */       }
/* 380 */       else if ((i > 0) && (j + 1 < paramString.length()))
/*     */       {
/* 382 */         ??? = paramString.substring(0, i - 2);
/* 383 */         str3 = paramString.substring(j + 1, paramString.length());
/*     */ 
/* 385 */         paramString = ((String)???).concat(str3);
/*     */       }
/*     */       else
/*     */       {
/* 389 */         paramString = paramString.substring(j + 2, paramString.length());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 394 */     Object localObject1 = new StringTokenizer(paramString, ", ");
/*     */ 
/* 396 */     synchronized (localObject1)
/*     */     {
/* 398 */       while (((StringTokenizer)localObject1).hasMoreTokens())
/*     */       {
/* 400 */         str1 = ((StringTokenizer)localObject1).nextToken();
/* 401 */         int k = str1.length();
/* 402 */         int m = str1.indexOf("=");
/*     */ 
/* 404 */         ??? = str1.substring(0, m);
/* 405 */         str3 = str1.substring(m + 1, k);
/*     */ 
/* 407 */         localProperties.setProperty((String)???, str3);
/*     */       }
/*     */     }
/*     */ 
/* 411 */     return (Properties)(Properties)localProperties;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.pool.OracleDataSourceFactory
 * JD-Core Version:    0.6.0
 */