/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.BINARY_DOUBLE;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeBINARY_DOUBLE extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  47 */     BINARY_DOUBLE localBINARY_DOUBLE = null;
/*     */ 
/*  49 */     if (paramObject != null)
/*     */     {
/*  51 */       if ((paramObject instanceof BINARY_DOUBLE))
/*  52 */         localBINARY_DOUBLE = (BINARY_DOUBLE)paramObject;
/*  53 */       else if ((paramObject instanceof Double))
/*  54 */         localBINARY_DOUBLE = new BINARY_DOUBLE((Double)paramObject);
/*  55 */       else if ((paramObject instanceof byte[]))
/*  56 */         localBINARY_DOUBLE = new BINARY_DOUBLE((byte[])paramObject);
/*     */       else {
/*  58 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/*  62 */     return localBINARY_DOUBLE;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  78 */     Datum[] arrayOfDatum = null;
/*     */ 
/*  80 */     if (paramObject != null)
/*     */     {
/*  82 */       if ((paramObject instanceof Object[]))
/*     */       {
/*  84 */         Object[] arrayOfObject = (Object[])paramObject;
/*     */ 
/*  86 */         int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));
/*     */ 
/*  89 */         arrayOfDatum = new Datum[i];
/*     */ 
/*  91 */         for (int j = 0; j < i; j++)
/*     */         {
/*  93 */           Object localObject = arrayOfObject[((int)paramLong + j - 1)];
/*     */ 
/*  95 */           if (localObject != null)
/*     */           {
/*  97 */             if ((localObject instanceof Double)) {
/*  98 */               arrayOfDatum[j] = new BINARY_DOUBLE(((Double)localObject).doubleValue());
/*     */             }
/* 100 */             else if ((localObject instanceof BINARY_DOUBLE))
/* 101 */               arrayOfDatum[j] = ((BINARY_DOUBLE)localObject);
/*     */             else
/* 103 */               DatabaseError.throwSqlException(68);
/*     */           }
/*     */           else {
/* 106 */             DatabaseError.throwSqlException(68);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 113 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/* 121 */     return 101;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 144 */     DatabaseError.throwSqlException(23, "no 8.0 pickle format for BINARY_DOUBLE");
/*     */ 
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 167 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 168 */       return null;
/*     */     }
/* 170 */     if (paramInt == 1)
/* 171 */       return new BINARY_DOUBLE(paramArrayOfByte);
/* 172 */     if (paramInt == 2)
/* 173 */       return new BINARY_DOUBLE(paramArrayOfByte).toJdbc();
/* 174 */     if (paramInt == 3) {
/* 175 */       return paramArrayOfByte;
/*     */     }
/* 177 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 180 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeBINARY_DOUBLE
 * JD-Core Version:    0.6.0
 */