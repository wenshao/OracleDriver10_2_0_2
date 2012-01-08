/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.sql.ARRAY;
/*     */ import oracle.sql.ArrayDescriptor;
/*     */ 
/*     */ public class ArrayLocatorResultSet extends OracleResultSetImpl
/*     */ {
/*  24 */   static int COUNT_UNLIMITED = -1;
/*     */   Map map;
/*     */   long beginIndex;
/*     */   int count;
/*     */   long currentIndex;
/* 191 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";
/*     */ 
/*     */   public ArrayLocatorResultSet(OracleConnection paramOracleConnection, ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*  44 */     this(paramOracleConnection, paramArrayDescriptor, paramArrayOfByte, 0L, COUNT_UNLIMITED, paramMap);
/*     */   }
/*     */ 
/*     */   public ArrayLocatorResultSet(OracleConnection paramOracleConnection, ArrayDescriptor paramArrayDescriptor, byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/*  65 */     super((PhysicalConnection)paramOracleConnection, (OracleStatement)null);
/*     */ 
/*  70 */     if ((paramArrayDescriptor == null) || (paramOracleConnection == null)) {
/*  71 */       DatabaseError.throwSqlException(1, "Invalid arguments");
/*     */     }
/*     */ 
/*  76 */     this.close_statement_on_close = true;
/*     */ 
/*  78 */     this.count = paramInt;
/*  79 */     this.currentIndex = 0L;
/*  80 */     this.beginIndex = paramLong;
/*     */ 
/*  82 */     this.map = paramMap;
/*     */ 
/*  84 */     OraclePreparedStatement localOraclePreparedStatement = null;
/*     */ 
/*  87 */     ARRAY localARRAY = new ARRAY(paramArrayDescriptor, paramOracleConnection, (byte[])null);
/*     */ 
/*  89 */     localARRAY.setLocator(paramArrayOfByte);
/*     */ 
/*  92 */     if ((paramArrayDescriptor.getBaseType() == 2002) || (paramArrayDescriptor.getBaseType() == 2008))
/*     */     {
/*  98 */       localOraclePreparedStatement = (OraclePreparedStatement)paramOracleConnection.prepareStatement("SELECT ROWNUM, SYS_NC_ROWINFO$ FROM TABLE( CAST(:1 AS " + paramArrayDescriptor.getName() + ") )");
/*     */     }
/*     */     else
/*     */     {
/* 107 */       localOraclePreparedStatement = (OraclePreparedStatement)paramOracleConnection.prepareStatement("SELECT ROWNUM, COLUMN_VALUE FROM TABLE( CAST(:1 AS " + paramArrayDescriptor.getName() + ") )");
/*     */     }
/*     */ 
/* 112 */     localOraclePreparedStatement.setArray(1, localARRAY);
/* 113 */     localOraclePreparedStatement.executeQuery();
/*     */ 
/* 115 */     this.statement = localOraclePreparedStatement;
/*     */   }
/*     */ 
/*     */   public synchronized boolean next()
/*     */     throws SQLException
/*     */   {
/* 127 */     if (this.currentIndex < this.beginIndex)
/*     */     {
/* 129 */       while (this.currentIndex < this.beginIndex)
/*     */       {
/* 131 */         this.currentIndex += 1L;
/*     */ 
/* 133 */         if (!super.next()) {
/* 134 */           return false;
/*     */         }
/*     */       }
/* 137 */       return true;
/*     */     }
/*     */ 
/* 141 */     if (this.count == COUNT_UNLIMITED)
/*     */     {
/* 143 */       return super.next();
/*     */     }
/* 145 */     if (this.currentIndex < this.beginIndex + this.count - 1L)
/*     */     {
/* 147 */       this.currentIndex += 1L;
/*     */ 
/* 149 */       return super.next();
/*     */     }
/*     */ 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   public synchronized Object getObject(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 165 */     return getObject(paramInt, this.map);
/*     */   }
/*     */ 
/*     */   public synchronized int findColumn(String paramString)
/*     */     throws SQLException
/*     */   {
/* 177 */     if (paramString.equalsIgnoreCase("index"))
/* 178 */       return 1;
/* 179 */     if (paramString.equalsIgnoreCase("value")) {
/* 180 */       return 2;
/*     */     }
/* 182 */     DatabaseError.throwSqlException(6, "get_column_index");
/*     */ 
/* 186 */     return 0;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.ArrayLocatorResultSet
 * JD-Core Version:    0.6.0
 */