/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.SQLException;
/*       */ 
/*       */ class PlsqlIbtBindInfo
/*       */ {
/*       */   Object[] arrayData;
/*       */   int maxLen;
/*       */   int curLen;
/*       */   int element_internal_type;
/*       */   int elemMaxLen;
/*       */   int ibtByteLength;
/*       */   int ibtCharLength;
/*       */   int ibtValueIndex;
/*       */   int ibtIndicatorIndex;
/*       */   int ibtLengthIndex;
/*       */ 
/*       */   PlsqlIbtBindInfo(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */     throws SQLException
/*       */   {
/* 17091 */     this.arrayData = paramArrayOfObject;
/* 17092 */     this.maxLen = paramInt1;
/* 17093 */     this.curLen = paramInt2;
/* 17094 */     this.element_internal_type = paramInt3;
/*       */ 
/* 17098 */     switch (paramInt3)
/*       */     {
/*       */     case 1:
/*       */     case 96:
/* 17106 */       this.elemMaxLen = (paramInt4 == 0 ? 2 : paramInt4 + 1);
/*       */ 
/* 17109 */       this.ibtCharLength = (this.elemMaxLen * paramInt1);
/* 17110 */       this.element_internal_type = 9;
/*       */ 
/* 17112 */       break;
/*       */     case 6:
/* 17115 */       this.elemMaxLen = 22;
/* 17116 */       this.ibtByteLength = (this.elemMaxLen * paramInt1);
/*       */ 
/* 17118 */       break;
/*       */     default:
/* 17121 */       DatabaseError.throwSqlException(97);
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.PlsqlIbtBindInfo
 * JD-Core Version:    0.6.0
 */