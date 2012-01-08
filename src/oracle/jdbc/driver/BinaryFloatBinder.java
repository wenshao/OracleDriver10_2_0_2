/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BinaryFloatBinder extends Binder
/*       */ {
/* 14761 */   Binder theBinaryFloatCopyingBinder = OraclePreparedStatementReadOnly.theStaticBinaryFloatCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 14766 */     paramBinder.type = 100;
/* 14767 */     paramBinder.bytelen = 4;
/*       */   }
/*       */ 
/*       */   BinaryFloatBinder()
/*       */   {
/* 14772 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 14777 */     return this.theBinaryFloatCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 14786 */     byte[] arrayOfByte = paramArrayOfByte;
/* 14787 */     int i = paramInt6;
/* 14788 */     float f = paramOraclePreparedStatement.parameterFloat[paramInt3][paramInt1];
/*       */ 
/* 14790 */     if (f == 0.0D)
/* 14791 */       f = 0.0F;
/* 14792 */     else if (f != f) {
/* 14793 */       f = (0.0F / 0.0F);
/*       */     }
/* 14795 */     int j = Float.floatToIntBits(f);
/*       */ 
/* 14799 */     int k = j;
/*       */ 
/* 14801 */     j >>= 8;
/*       */ 
/* 14803 */     int m = j;
/*       */ 
/* 14805 */     j >>= 8;
/*       */ 
/* 14807 */     int n = j;
/*       */ 
/* 14809 */     j >>= 8;
/*       */ 
/* 14811 */     int i1 = j;
/*       */ 
/* 14813 */     if ((i1 & 0x80) == 0) {
/* 14814 */       i1 |= 128;
/*       */     }
/*       */     else {
/* 14817 */       i1 ^= -1;
/* 14818 */       n ^= -1;
/* 14819 */       m ^= -1;
/* 14820 */       k ^= -1;
/*       */     }
/*       */ 
/* 14823 */     arrayOfByte[(i + 3)] = (byte)k;
/* 14824 */     arrayOfByte[(i + 2)] = (byte)m;
/* 14825 */     arrayOfByte[(i + 1)] = (byte)n;
/* 14826 */     arrayOfByte[i] = (byte)i1;
/*       */ 
/* 14828 */     paramArrayOfShort[paramInt9] = 0;
/* 14829 */     paramArrayOfShort[paramInt8] = 4;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BinaryFloatBinder
 * JD-Core Version:    0.6.0
 */