/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.SQLException;
/*       */ import oracle.core.lmx.CoreException;
/*       */ 
/*       */ class DoubleBinder extends VarnumBinder
/*       */ {
/* 13801 */   char[] digits = new char[20];
/*       */ 
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */     throws SQLException
/*       */   {
/* 13809 */     byte[] arrayOfByte = paramArrayOfByte;
/* 13810 */     int i = paramInt6 + 1;
/* 13811 */     double d = paramOraclePreparedStatement.parameterDouble[paramInt3][paramInt1];
/* 13812 */     int j = 0;
/*       */ 
/* 13815 */     if (d == 0.0D)
/*       */     {
/* 13817 */       arrayOfByte[i] = -128;
/* 13818 */       j = 1;
/*       */     }
/* 13820 */     else if (d == (1.0D / 0.0D))
/*       */     {
/* 13822 */       arrayOfByte[i] = -1;
/* 13823 */       arrayOfByte[(i + 1)] = 101;
/* 13824 */       j = 2;
/*       */     }
/* 13826 */     else if (d == (-1.0D / 0.0D))
/*       */     {
/* 13828 */       arrayOfByte[i] = 0;
/* 13829 */       j = 1;
/*       */     }
/*       */     else
/*       */     {
/* 13834 */       boolean bool = d < 0.0D;
/*       */ 
/* 13836 */       if (bool) {
/* 13837 */         d = -d;
/*       */       }
/* 13839 */       long l1 = Double.doubleToLongBits(d);
/* 13840 */       int k = (int)(l1 >> 52 & 0x7FF);
/* 13841 */       int m = (k > 1023 ? 126 : 127) - (int)((k - 1023) / 6.643856189774725D);
/*       */       SQLException localSQLException;
/* 13844 */       if (m < 0)
/*       */       {
/* 13846 */         localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13850 */         throw localSQLException;
/*       */       }
/*       */ 
/* 13853 */       if (m > 192)
/*       */       {
/* 13855 */         localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13859 */         throw localSQLException;
/*       */       }
/*       */ 
/* 13862 */       if (d > factorTable[m]) {
/* 13863 */         for (; m > 0; d <= factorTable[m]) m--;
/*       */       }
/* 13865 */       while ((m < 193) && (d <= factorTable[(m + 1)])) {
/* 13866 */         m++;
/*       */       }
/* 13868 */       if (d == factorTable[m])
/*       */       {
/* 13870 */         if (m < 65)
/*       */         {
/* 13872 */           localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13877 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13880 */         if (m > 192)
/*       */         {
/* 13882 */           localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13887 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13890 */         if (bool)
/*       */         {
/* 13892 */           arrayOfByte[i] = (byte)(62 - (127 - m));
/* 13893 */           arrayOfByte[(i + 1)] = 100;
/* 13894 */           arrayOfByte[(i + 2)] = 102;
/* 13895 */           j = 3;
/*       */         }
/*       */         else
/*       */         {
/* 13899 */           arrayOfByte[i] = (byte)(192 + (128 - m));
/* 13900 */           arrayOfByte[(i + 1)] = 2;
/* 13901 */           j = 2;
/*       */         }
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/* 13907 */         if (m < 64)
/*       */         {
/* 13909 */           localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13914 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13917 */         if (m > 191)
/*       */         {
/* 13919 */           localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13924 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13931 */         long l2 = bool ? l1 & 0xFFFFFFFF : l1;
/* 13932 */         long l3 = l2 & 0xFFFFFFFF;
/* 13933 */         int n = k;
/*       */ 
/* 13935 */         char[] arrayOfChar = paramOraclePreparedStatement.digits;
/*       */         int i1;
/* 13940 */         if (n == 0)
/*       */         {
/* 13942 */           while ((l3 & 0x0) == 0L)
/*       */           {
/* 13944 */             l3 <<= 1;
/* 13945 */             n--;
/*       */           }
/*       */ 
/* 13948 */           i1 = 53 + n;
/* 13949 */           n++;
/*       */         }
/*       */         else
/*       */         {
/* 13953 */           l3 |= 4503599627370496L;
/* 13954 */           i1 = 53;
/*       */         }
/*       */ 
/* 13957 */         n -= 1023;
/*       */ 
/* 13960 */         j = dtoa(arrayOfByte, i, d, bool, false, arrayOfChar, n, l3, i1);
/*       */       }
/*       */ 
/*       */     }
/*       */ 
/* 14067 */     arrayOfByte[paramInt6] = (byte)j;
/* 14068 */     paramArrayOfShort[paramInt9] = 0;
/* 14069 */     paramArrayOfShort[paramInt8] = (short)(j + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.DoubleBinder
 * JD-Core Version:    0.6.0
 */