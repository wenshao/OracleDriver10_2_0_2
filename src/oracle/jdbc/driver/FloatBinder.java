/*       */ package oracle.jdbc.driver;
/*       */ 
/*       */ import java.sql.SQLException;
/*       */ import oracle.core.lmx.CoreException;
/*       */ 
/*       */ class FloatBinder extends VarnumBinder
/*       */ {
/*       */   void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
/*       */     throws SQLException
/*       */   {
/* 13571 */     byte[] arrayOfByte = paramArrayOfByte;
/* 13572 */     int i = paramInt6 + 1;
/* 13573 */     double d = paramOraclePreparedStatement.parameterDouble[paramInt3][paramInt1];
/* 13574 */     int j = 0;
/*       */ 
/* 13578 */     if (d == 0.0D)
/*       */     {
/* 13580 */       arrayOfByte[i] = -128;
/* 13581 */       j = 1;
/*       */     }
/* 13583 */     else if (d == (1.0D / 0.0D))
/*       */     {
/* 13585 */       arrayOfByte[i] = -1;
/* 13586 */       arrayOfByte[(i + 1)] = 101;
/* 13587 */       j = 2;
/*       */     }
/* 13589 */     else if (d == (-1.0D / 0.0D))
/*       */     {
/* 13591 */       arrayOfByte[i] = 0;
/* 13592 */       j = 1;
/*       */     }
/*       */     else
/*       */     {
/* 13597 */       boolean bool = d < 0.0D;
/*       */ 
/* 13599 */       if (bool) {
/* 13600 */         d = -d;
/*       */       }
/* 13602 */       long l = Double.doubleToLongBits(d);
/* 13603 */       int k = (int)(l >> 52 & 0x7FF);
/* 13604 */       int m = (k > 1023 ? 126 : 127) - (int)((k - 1023) / 6.643856189774725D);
/*       */       SQLException localSQLException;
/* 13607 */       if (m < 0)
/*       */       {
/* 13609 */         localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13613 */         throw localSQLException;
/*       */       }
/*       */ 
/* 13616 */       if (m > 192)
/*       */       {
/* 13618 */         localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13622 */         throw localSQLException;
/*       */       }
/*       */ 
/* 13625 */       if (d > factorTable[m]) {
/* 13626 */         for (; m > 0; d <= factorTable[m]) m--;
/*       */       }
/* 13628 */       while ((m < 193) && (d <= factorTable[(m + 1)])) {
/* 13629 */         m++;
/*       */       }
/* 13631 */       if (d == factorTable[m])
/*       */       {
/* 13633 */         if (m < 65)
/*       */         {
/* 13635 */           localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13640 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13643 */         if (m > 192)
/*       */         {
/* 13645 */           localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13650 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13653 */         if (bool)
/*       */         {
/* 13655 */           arrayOfByte[i] = (byte)(62 - (127 - m));
/* 13656 */           arrayOfByte[(i + 1)] = 100;
/* 13657 */           arrayOfByte[(i + 2)] = 102;
/* 13658 */           j = 3;
/*       */         }
/*       */         else
/*       */         {
/* 13662 */           arrayOfByte[i] = (byte)(192 + (128 - m));
/* 13663 */           arrayOfByte[(i + 1)] = 2;
/* 13664 */           j = 2;
/*       */         }
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/* 13670 */         if (m < 64)
/*       */         {
/* 13672 */           localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + d);
/*       */ 
/* 13677 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13680 */         if (m > 191)
/*       */         {
/* 13682 */           localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + d);
/*       */ 
/* 13687 */           throw localSQLException;
/*       */         }
/*       */ 
/* 13692 */         int n = Float.floatToIntBits((float)d);
/* 13693 */         int i1 = n & 0x7FFFFF;
/* 13694 */         int i2 = n >> 23 & 0xFF;
/*       */ 
/* 13696 */         char[] arrayOfChar = paramOraclePreparedStatement.digits;
/*       */         int i3;
/* 13701 */         if (i2 == 0)
/*       */         {
/* 13703 */           while ((i1 & 0x800000) == 0L)
/*       */           {
/* 13705 */             i1 <<= 1;
/* 13706 */             i2--;
/*       */           }
/*       */ 
/* 13709 */           i3 = 24 + i2;
/* 13710 */           i2++;
/*       */         }
/*       */         else
/*       */         {
/* 13714 */           i1 |= 8388608;
/* 13715 */           i3 = 24;
/*       */         }
/*       */ 
/* 13718 */         i2 -= 127;
/*       */ 
/* 13721 */         j = dtoa(arrayOfByte, i, d, bool, true, arrayOfChar, i2, i1 << 29, i3);
/*       */       }
/*       */ 
/*       */     }
/*       */ 
/* 13787 */     arrayOfByte[paramInt6] = (byte)j;
/* 13788 */     paramArrayOfShort[paramInt9] = 0;
/* 13789 */     paramArrayOfShort[paramInt8] = (short)(j + 1);
/*       */   }
/*       */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.FloatBinder
 * JD-Core Version:    0.6.0
 */