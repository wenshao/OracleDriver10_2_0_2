package oracle.jdbc.driver;

import java.math.BigDecimal;
import java.sql.SQLException;
import oracle.core.lmx.CoreException;

class BigDecimalBinder extends VarnumBinder {
	void bind(OraclePreparedStatement paramOraclePreparedStatement, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, char[] paramArrayOfChar,
			short[] paramArrayOfShort, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean) throws SQLException {
		/* 14565 */byte[] arrayOfByte = paramArrayOfByte;
		/* 14566 */int i = paramInt6 + 1;
		/* 14567 */BigDecimal localBigDecimal1 = paramOraclePreparedStatement.parameterBigDecimal[paramInt3][paramInt1];
		/* 14568 */int j = 0;

		/* 14570 */Object localObject1 = localBigDecimal1.toString();
		int k;
		int str6;
		/* 14578 */if ((k = ((String) localObject1).indexOf("E")) != -1) {
			/* 14580 */Object localObject2 = "";
			/* 14581 */int str2 = 0;
			/* 14582 */BigDecimal localBigDecimal2 = null;
			/* 14583 */String str4 = ((String) localObject1).substring(k + 1);
			/* 14584 */String str5 = ((String) localObject1).substring(0, k);
			/* 14585 */localBigDecimal2 = new BigDecimal(str5);
			str6 = str4.charAt(0) == '-' ? 1 : 0;
			/* 14587 */str4 = str4.substring(1);
			/* 14588 */str2 = Integer.parseInt(str4);

			/* 14590 */int str7 = localBigDecimal2.toString().indexOf(".");
			/* 14591 */if (str7 != -1) {
				/* 14593 */double d = Math.pow(10.0D, str7);
				/* 14594 */localBigDecimal2 = localBigDecimal2.multiply(new BigDecimal(d));
				/* 14595 */str2 -= str7;
				/* 14596 */} else if (str6 != 0) {
				/* 14597 */str2--;
			}
			/* 14599 */String str8 = localBigDecimal2.toString();
			/* 14600 */str8 = str8.substring(0, str8.length() - (str7 + 1));
			/* 14601 */localObject2 = str6 != 0 ? "0." : str8;

			/* 14603 */for (int str9 = 0; str9 < str2; str9++) {
				/* 14604 */localObject2 = (String) localObject2 + "0";
			}
			/* 14606 */if (str6 != 0) {
				/* 14607 */localObject2 = (String) localObject2 + str8;
			}
			/* 14609 */localObject1 = localObject2;
		}
		/* 14611 */int str1 = ((String) localObject1).length();
		/* 14612 */int str2 = ((String) localObject1).indexOf('.');
		/* 14613 */int str3 = ((String) localObject1).charAt(0) == '-' ? 1 : 0;
		/* 14614 */int str4 = str3;

		/* 14618 */int i1 = 2;
		/* 14619 */int i2 = str1;

		/* 14623 */if (str2 == -1)
			/* 14624 */str2 = str1;
		/* 14625 */else if ((str1 - str2 & 0x1) != 0) {
			/* 14626 */int i3 = str1 + 1;
		}
		int m;
		/* 14628 */while ((str4 < str1) && (((m = ((String) localObject1).charAt(str4)) < '1') || (m > 57))) {
			/* 14629 */str4++;
		}
		/* 14631 */if (str4 >= str1) {
			/* 14633 */arrayOfByte[i] = -128;
			/* 14634 */j = 1;
		} else {
			/* 14639 */if (str4 < str2)
				/* 14640 */str6 = 2 - (str2 - str4 & 0x1);
			else {
				/* 14642 */str6 = 1 + (str4 - str2 & 0x1);
			}
			/* 14644 */int n = (str2 - str4 - 1) / 2;
			SQLException localSQLException;
			/* 14646 */if (n > 62) {
				/* 14648 */localSQLException = new SQLException(CoreException.getMessage(3) + " trying to bind " + localBigDecimal1);

				/* 14652 */throw localSQLException;
			}

			/* 14655 */if (n < -65) {
				/* 14657 */localSQLException = new SQLException(CoreException.getMessage(2) + " trying to bind " + localBigDecimal1);

				/* 14661 */throw localSQLException;
			}

			/* 14664 */int str10 = str4 + str6 + 38;
			int str11 = 0;
			if (str10 > str1) {
				str11 = str1;
			}
			/* 14669 */for (int str12 = str4 + str6; str12 < str11; str12 += 2) {
				/* 14671 */if (str12 == str2) {
					/* 14673 */str12--;

					/* 14675 */if (str11 < str1) {
						/* 14676 */str11++;
					}
				} else {
					/* 14681 */if ((((String) localObject1).charAt(str12) == '0') && ((str12 + 1 >= str1) || (((String) localObject1).charAt(str12 + 1) == '0'))) {
						continue;
					}
					/* 14684 */i1 = (str12 - str4 - str6) / 2 + 3;
				}
			}

			/* 14688 */int i4 = i + 2;

			/* 14690 */int str13 = str4 + str6;

			/* 14694 */if (str3 == 0) {
				/* 14696 */arrayOfByte[i] = (byte) (192 + n + 1);
				/* 14697 */int i5 = ((String) localObject1).charAt(str4) - '0';

				/* 14699 */if (str6 == 2) {
					/* 14700 */i5 = i5 * 10 + (str4 + 1 < str1 ? ((String) localObject1).charAt(str4 + 1) - '0' : 0);
				}

				/* 14703 */arrayOfByte[(i + 1)] = (byte) (i5 + 1);

				/* 14705 */while (i4 < i + i1) {
					/* 14707 */if (str13 == str2) {
						/* 14708 */str13++;
					}
					/* 14710 */i5 = (((String) localObject1).charAt(str13) - '0') * 10;

					/* 14712 */if (str13 + 1 < str1) {
						/* 14713 */i5 += ((String) localObject1).charAt(str13 + 1) - '0';
					}
					/* 14715 */arrayOfByte[(i4++)] = (byte) (i5 + 1);
					/* 14716 */str13 += 2;
				}

			}

			/* 14721 */arrayOfByte[i] = (byte) (62 - n);

			/* 14723 */int i5 = ((String) localObject1).charAt(str4) - '0';

			/* 14725 */if (str6 == 2) {
				/* 14726 */i5 = i5 * 10 + (str4 + 1 < str1 ? ((String) localObject1).charAt(str4 + 1) - '0' : 0);
			}

			/* 14729 */arrayOfByte[(i + 1)] = (byte) (101 - i5);

			/* 14731 */while (i4 < i + i1) {
				/* 14733 */if (str13 == str2) {
					/* 14734 */str13++;
				}
				/* 14736 */i5 = (((String) localObject1).charAt(str13) - '0') * 10;

				/* 14738 */if (str13 + 1 < str1) {
					/* 14739 */i5 += ((String) localObject1).charAt(str13 + 1) - '0';
				}
				/* 14741 */arrayOfByte[(i4++)] = (byte) (101 - i5);
				/* 14742 */str13 += 2;
			}

			/* 14745 */if (i1 < 21) {
				/* 14746 */arrayOfByte[(i + i1++)] = 102;
			}

			/* 14749 */j = i1;
		}

		/* 14752 */arrayOfByte[paramInt6] = (byte) j;
		/* 14753 */paramArrayOfShort[paramInt9] = 0;
		/* 14754 */paramArrayOfShort[paramInt8] = (short) (j + 1);
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.BigDecimalBinder
 * JD-Core Version: 0.6.0
 */