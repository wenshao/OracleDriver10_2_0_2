/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.BINARY_FLOAT;
/*     */ import oracle.sql.Datum;
/*     */ 
/*     */ public class OracleTypeBINARY_FLOAT extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  47 */     BINARY_FLOAT localBINARY_FLOAT = null;
/*     */ 
/*  49 */     if (paramObject != null)
/*     */     {
/*  51 */       if ((paramObject instanceof BINARY_FLOAT))
/*  52 */         localBINARY_FLOAT = (BINARY_FLOAT)paramObject;
/*  53 */       else if ((paramObject instanceof Float))
/*  54 */         localBINARY_FLOAT = new BINARY_FLOAT((Float)paramObject);
/*  55 */       else if ((paramObject instanceof byte[]))
/*  56 */         localBINARY_FLOAT = new BINARY_FLOAT((byte[])paramObject);
/*     */       else {
/*  58 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/*  62 */     return localBINARY_FLOAT;
/*     */   }
/*     */ 
/*     */   public Datum[] toDatumArray(Object paramObject, OracleConnection paramOracleConnection, long paramLong, int paramInt)
/*     */     throws SQLException
/*     */   {
/*  77 */     Datum[] arrayOfDatum = null;
/*     */ 
/*  79 */     if (paramObject != null)
/*     */     {
/*  81 */       if ((paramObject instanceof Object[]))
/*     */       {
/*  83 */         Object[] arrayOfObject = (Object[])paramObject;
/*     */ 
/*  85 */         int i = (int)(paramInt == -1 ? arrayOfObject.length : Math.min(arrayOfObject.length - paramLong + 1L, paramInt));
/*     */ 
/*  88 */         arrayOfDatum = new Datum[i];
/*     */ 
/*  90 */         for (int j = 0; j < i; j++)
/*     */         {
/*  92 */           Object localObject = arrayOfObject[((int)paramLong + j - 1)];
/*     */ 
/*  94 */           if (localObject != null)
/*     */           {
/*  96 */             if ((localObject instanceof Float)) {
/*  97 */               arrayOfDatum[j] = new BINARY_FLOAT(((Float)localObject).floatValue());
/*     */             }
/*  99 */             else if ((localObject instanceof BINARY_FLOAT))
/* 100 */               arrayOfDatum[j] = ((BINARY_FLOAT)localObject);
/*     */             else
/* 102 */               DatabaseError.throwSqlException(68);
/*     */           }
/*     */           else {
/* 105 */             DatabaseError.throwSqlException(68);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 112 */     return arrayOfDatum;
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/* 120 */     return 100;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 143 */     DatabaseError.throwSqlException(23, "no 8.0 pickle format for BINARY_FLOAT");
/*     */ 
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 167 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 168 */       return null;
/*     */     }
/* 170 */     if (paramInt == 1)
/* 171 */       return new BINARY_FLOAT(paramArrayOfByte);
/* 172 */     if (paramInt == 2)
/* 173 */       return new BINARY_FLOAT(paramArrayOfByte).toJdbc();
/* 174 */     if (paramInt == 3) {
/* 175 */       return paramArrayOfByte;
/*     */     }
/* 177 */     DatabaseError.throwSqlException(59, paramArrayOfByte);
/*     */ 
/* 180 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeBINARY_FLOAT
 * JD-Core Version:    0.6.0
 */