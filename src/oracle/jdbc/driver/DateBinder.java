/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.Date;
/*       */ 
/*       */ class DateBinder extends DateCommonBinder
/*       */ {
/* 15651 */   Binder theDateCopyingBinder = OraclePreparedStatementReadOnly.theStaticDateCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 15656 */     paramBinder.type = 12;
/* 15657 */     paramBinder.bytelen = 7;
/*       */   }
/*       */ 
/*       */   DateBinder()
/*       */   {
/* 15662 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 15667 */     return this.theDateCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 15676 */     Date[] arrayOfDate = paramOraclePreparedStatement.parameterDate[paramInt3];
/* 15677 */     Date localDate = arrayOfDate[paramInt1];
/*       */ 
/* 15679 */     if (paramBoolean) {
/* 15680 */       arrayOfDate[paramInt1] = null;
/*       */     }
/* 15682 */     if (localDate == null)
/*       */     {
/* 15684 */       paramArrayOfShort[paramInt9] = -1;
/*       */     }
/*       */     else
/*       */     {
/* 15688 */       paramArrayOfShort[paramInt9] = 0;
/*       */ 
/* 15690 */       long l = setOracleCYMD(localDate.getTime(), paramArrayOfByte, paramInt6, paramOraclePreparedStatement);
/*       */ 
/* 15693 */       paramArrayOfByte[(6 + paramInt6)] = 1;
/* 15694 */       paramArrayOfByte[(5 + paramInt6)] = 1;
/* 15695 */       paramArrayOfByte[(4 + paramInt6)] = 1;
/*       */ 
/* 15697 */       paramArrayOfShort[paramInt8] = (short)paramInt4;
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.DateBinder
 * JD-Core Version:    0.6.0
 */