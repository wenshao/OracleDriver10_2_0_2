/*    */package oracle.sql;

/*    */
/*    */import java.io.PrintStream;
/*    */
import java.sql.SQLException;

/*    */
/*    */abstract class CharacterSetFactory
/*    */{
	/*    */public static final short DEFAULT_CHARSET = -1;
	/*    */public static final short ASCII_CHARSET = 1;
	/*    */public static final short ISO_LATIN_1_CHARSET = 31;
	/*    */public static final short UNICODE_1_CHARSET = 870;
	/*    */public static final short UNICODE_2_CHARSET = 871;

	/*    */
	/*    */public abstract CharacterSet make(int paramInt);

	/*    */
	/*    */public static void main(String[] paramArrayOfString)
	/*    */{
		/* 77 */CharacterSet localCharacterSet1 = CharacterSet.make(871);
		/* 78 */int[] arrayOfInt = { 1, 31, 870, 871 };
		/*    */
		/* 83 */for (int i = 0; i < arrayOfInt.length; i++)
		/*    */{
			/* 85 */CharacterSet localCharacterSet2 = CharacterSet
					.make(arrayOfInt[i]);
			/*    */
			/* 88 */String str1 = "longlonglonglong";
			/* 89 */str1 = str1 + str1 + str1 + str1;
			/* 90 */str1 = str1 + str1 + str1 + str1;
			/* 91 */str1 = str1 + str1 + str1 + str1;
			/* 92 */str1 = str1 + str1 + str1 + str1;
			/*    */
			/* 94 */String[] arrayOfString = { "abc", "ab?c", "XYZ", str1 };
			/*    */
			/* 99 */for (int j = 0; j < arrayOfString.length; j++)
			/*    */{
				/* 101 */String str2 = arrayOfString[j];
				/* 102 */String str3 = str2;
				/*    */
				/* 104 */if (str2.length() > 16)
				/*    */{
					/* 106 */str3 = str3.substring(0, 16) + "...";
					/*    */}
				/*    */
				/* 109 */System.out.println("testing " + localCharacterSet2
						+ " against <" + str3 + ">");
				/*    */
				/* 111 */int k = 1;
				/*    */try
				/*    */{
					/* 115 */byte[] arrayOfByte1 = localCharacterSet2
							.convertWithReplacement(str2);
					/* 116 */String str4 = localCharacterSet2
							.toStringWithReplacement(arrayOfByte1, 0,
									arrayOfByte1.length);
					/*    */
					/* 119 */arrayOfByte1 = localCharacterSet2.convert(str4);
					/*    */
					/* 121 */String str5 = localCharacterSet2.toString(
							arrayOfByte1, 0, arrayOfByte1.length);
					/*    */
					/* 123 */if (!str4.equals(str5))
					/*    */{
						/* 125 */System.out.println("    FAILED roundTrip "
								+ str5);
						/*    */
						/* 127 */k = 0;
						/*    */}
					/*    */Object localObject;
					/* 130 */if (localCharacterSet2
							.isLossyFrom(localCharacterSet1))
					/*    */{
						/*    */try
						/*    */{
							/* 134 */byte[] arrayOfByte2 = localCharacterSet2
									.convert(str2);
							/* 135 */localObject = localCharacterSet2.toString(
									arrayOfByte2, 0, arrayOfByte2.length);
							/*    */
							/* 137 */if (!((String) localObject).equals(str5))
							/*    */{
								/* 139 */System.out
										.println("    FAILED roundtrip, no throw");
								/*    */}
							/*    */}
						/*    */catch (SQLException localSQLException) {
							/*    */}
						/*    */}
					/*    */else {
						/* 146 */if (!str5.equals(str2))
						/*    */{
							/* 148 */System.out.println("    FAILED roundTrip "
									+ str5);
							/*    */
							/* 150 */k = 0;
							/*    */}
						/*    */
						/* 153 */byte[] arrayOfByte3 = localCharacterSet1
								.convert(str2);
						/* 154 */localObject = localCharacterSet2.convert(
								localCharacterSet1, arrayOfByte3, 0,
								arrayOfByte3.length);
						/*    */
						/* 156 */String str6 = localCharacterSet2.toString(
								localObject, 0, localObject.length);
						/*    */
						/* 160 */if (!str6.equals(str2))
						/*    */{
							/* 162 */System.out
									.println("    FAILED withoutReplacement "
											+ str6);
							/*    */
							/* 165 */k = 0;
							/*    */}
						/*    */}
					/*    */}
				/*    */catch (Exception localException)
				/*    */{
					/* 171 */System.out.println("    FAILED with Exception "
							+ localException);
					/*    */}
				/*    */
				/* 174 */if (k == 0)
					/*    */continue;
				/* 176 */System.out
						.println("    PASSED "
								+ (localCharacterSet2
										.isLossyFrom(localCharacterSet1) ? "LOSSY"
										: ""));
				/*    */}
			/*    */}
		/*    */}
	/*    */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CharacterSetFactory
 * JD-Core Version: 0.6.0
 */