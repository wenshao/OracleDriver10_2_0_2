/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.Timestamp;
/*       */ 
/*       */ class TimestampBinder extends DateCommonBinder
/*       */ {
/* 15796 */   Binder theTimestampCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimestampCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15801 */     paramBinder.type = 180;
/* 15802 */     paramBinder.bytelen = 11;
/*       */   }
/*       */ 
/*       */   TimestampBinder()
/*       */   {
/* 15807 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15812 */     return this.theTimestampCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15821 */     Timestamp[] arrayOfTimestamp = paramOraclePreparedStatement.parameterTimestamp[paramInt3];
/* 15822 */     Timestamp localTimestamp = arrayOfTimestamp[paramInt1];
/*       */ 
/* 15824 */     if (paramBoolean) {
/* 15825 */       arrayOfTimestamp[paramInt1] = null;
/*       */     }
/* 15827 */     if (localTimestamp == null)
/*       */     {
/* 15829 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15833 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15835 */       setOracleHMS(setOracleCYMD(localTimestamp.getTime(), paramArrayOfByte, paramInt6, paramOraclePreparedStatement), paramArrayOfByte, paramInt6);
/*       */ 
/* 15838 */       int i = localTimestamp.getNanos();
/*       */ 
/* 15840 */       if (i != 0)
/*       */       {
/* 15842 */         setOracleNanos(i, paramArrayOfByte, paramInt6);
/*       */ 
/* 15844 */         paramArrayOfShort[paramInt8] = (short)paramInt4;
/*       */       }
/*       */       else
/*       */       {
/* 15850 */         paramArrayOfShort[paramInt8] = 7;
/*       */       }
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TimestampBinder
 * JD-Core Version:    0.6.0
 */