/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.Time;
/*       */ 
/*       */ class TimeBinder extends DateCommonBinder
/*       */ {
/* 15737 */   Binder theTimeCopyingBinder = OraclePreparedStatementReadOnly.theStaticTimeCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15742 */     paramBinder.type = 12;
/* 15743 */     paramBinder.bytelen = 7;
/*       */   }
/*       */ 
/*       */   TimeBinder()
/*       */   {
/* 15748 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15753 */     return this.theTimeCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15762 */     Time[] arrayOfTime = paramOraclePreparedStatement.parameterTime[paramInt3];
/* 15763 */     Time localTime = arrayOfTime[paramInt1];
/*       */ 
/* 15765 */     if (paramBoolean) {
/* 15766 */       arrayOfTime[paramInt1] = null;
/*       */     }
/* 15768 */     if (localTime == null)
/*       */     {
/* 15770 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15774 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15776 */       setOracleHMS(setOracleCYMD(localTime.getTime(), paramArrayOfByte, paramInt6, paramOraclePreparedStatement), paramArrayOfByte, paramInt6);
/*       */ 
/* 15779 */       paramArrayOfShort[paramInt8] = (short)paramInt4;
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.TimeBinder
 * JD-Core Version:    0.6.0
 */