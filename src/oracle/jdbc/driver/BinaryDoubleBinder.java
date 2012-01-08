/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ class BinaryDoubleBinder extends Binder
/*       */ {
/* 14868 */   Binder theBinaryDoubleCopyingBinder = OraclePreparedStatementReadOnly.theStaticBinaryDoubleCopyingBinder;
/*       */ 
/*       */   static void init(Binder paramBinder)
/*       */   {
/* 14873 */     paramBinder.type = 101;
/* 14874 */     paramBinder.bytelen = 8;
/*       */   }
/*       */ 
/*       */   BinaryDoubleBinder()
/*       */   {
/* 14879 */     init(this);
/*       */   }
/*       */ 
/*       */   Binder copyingBinder()
/*       */   {
/* 14884 */     return this.theBinaryDoubleCopyingBinder;
/*       */   }
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */   {
/* 14893 */     byte[] arrayOfByte = paramArrayOfByte;
/* 14894 */     int i = paramInt6;
/* 14895 */     double d = paramOraclePreparedStatement.parameterDouble[paramInt3][paramInt1];
/*       */ 
/* 14897 */     if (d == 0.0D)
/* 14898 */       d = 0.0D;
/* 14899 */     else if (d != d) {
/* 14900 */       d = (0.0D / 0.0D);
/*       */     }
/* 14902 */     long l = Double.doubleToLongBits(d);
/*       */ 
/* 14906 */     int j = (int)l;
/* 14907 */     int k = (int)(l >> 32);
/*       */ 
/* 14909 */     int m = j;
/*       */ 
/* 14911 */     j >>= 8;
/*       */ 
/* 14913 */     int n = j;
/*       */ 
/* 14915 */     j >>= 8;
/*       */ 
/* 14917 */     int i1 = j;
/*       */ 
/* 14919 */     j >>= 8;
/*       */ 
/* 14921 */     int i2 = j;
/*       */ 
/* 14923 */     int i3 = k;
/*       */ 
/* 14925 */     k >>= 8;
/*       */ 
/* 14927 */     int i4 = k;
/*       */ 
/* 14929 */     k >>= 8;
/*       */ 
/* 14931 */     int i5 = k;
/*       */ 
/* 14933 */     k >>= 8;
/*       */ 
/* 14935 */     int i6 = k;
/*       */ 
/* 14937 */     if ((i6 & 0x80) == 0) {
/* 14938 */       i6 |= 128;
/*       */     }
/*       */     else {
/* 14941 */       i6 ^= -1;
/* 14942 */       i5 ^= -1;
/* 14943 */       i4 ^= -1;
/* 14944 */       i3 ^= -1;
/* 14945 */       i2 ^= -1;
/* 14946 */       i1 ^= -1;
/* 14947 */       n ^= -1;
/* 14948 */       m ^= -1;
/*       */     }
/*       */ 
/* 14951 */     arrayOfByte[(i + 7)] = (byte)m;
/* 14952 */     arrayOfByte[(i + 6)] = (byte)n;
/* 14953 */     arrayOfByte[(i + 5)] = (byte)i1;
/* 14954 */     arrayOfByte[(i + 4)] = (byte)i2;
/* 14955 */     arrayOfByte[(i + 3)] = (byte)i3;
/* 14956 */     arrayOfByte[(i + 2)] = (byte)i4;
/* 14957 */     arrayOfByte[(i + 1)] = (byte)i5;
/* 14958 */     arrayOfByte[i] = (byte)i6;
/* 14959 */     paramArrayOfShort[paramInt9] = 0;
/* 14960 */     paramArrayOfShort[paramInt8] = 8;
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.BinaryDoubleBinder
 * JD-Core Version:    0.6.0
 */