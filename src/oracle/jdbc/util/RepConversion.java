/*     */package oracle.jdbc.util;

/*     */
/*     */import java.io.PrintStream;

/*     */
/*     */public class RepConversion
/*     */{
	/* 493 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:33_PST_2006";

	/*     */
	/*     */public static void printInHex(byte paramByte)
	/*     */{
		/* 53 */System.out.print((char) nibbleToHex((byte) ((paramByte & 0xF0) >> 4)));
		/* 54 */System.out.print((char) nibbleToHex((byte) (paramByte & 0xF)));
		/*     */}

	/*     */
	/*     */public static byte nibbleToHex(byte paramByte)
	/*     */{
		/* 83 */paramByte = (byte) (paramByte & 0xF);
		/*     */
		/* 85 */return (byte) (paramByte < 10 ? paramByte + 48 : paramByte - 10 + 65);
		/*     */}

	/*     */
	/*     */public static byte asciiHexToNibble(byte paramByte)
	/*     */{
		/*     */int i;
		/* 120 */if ((paramByte >= 97) && (paramByte <= 102)) {
			/* 121 */i = (byte) (paramByte - 97 + 10);
			/*     */}
		/* 123 */else if ((paramByte >= 65) && (paramByte <= 70)) {
			/* 124 */i = (byte) (paramByte - 65 + 10);
			/*     */}
		/* 126 */else if ((paramByte >= 48) && (paramByte <= 57)) {
			/* 127 */i = (byte) (paramByte - 48);
			/*     */}
		/*     */else {
			/* 130 */i = paramByte;
			/*     */}
		/*     */
		/* 147 */return (byte) i;
		/*     */}

	/*     */
	/*     */public static void bArray2Nibbles(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
	/*     */{
		/* 178 */for (int i = 0; i < paramArrayOfByte1.length; i++)
		/*     */{
			/* 180 */paramArrayOfByte2[(i * 2)] = nibbleToHex((byte) ((paramArrayOfByte1[i] & 0xF0) >> 4));
			/* 181 */paramArrayOfByte2[(i * 2 + 1)] = nibbleToHex((byte) (paramArrayOfByte1[i] & 0xF));
			/*     */}
		/*     */}

	/*     */
	/*     */public static String bArray2String(byte[] paramArrayOfByte)
	/*     */{
		/* 210 */StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
		/*     */
		/* 212 */for (int i = 0; i < paramArrayOfByte.length; i++)
		/*     */{
			/* 214 */localStringBuffer.append((char) nibbleToHex((byte) ((paramArrayOfByte[i] & 0xF0) >> 4)));
			/* 215 */localStringBuffer.append((char) nibbleToHex((byte) (paramArrayOfByte[i] & 0xF)));
			/*     */}
		/*     */
		/* 233 */return localStringBuffer.toString();
		/*     */}

	/*     */
	/*     */public static byte[] nibbles2bArray(byte[] paramArrayOfByte)
	/*     */{
		/* 264 */byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];
		/*     */
		/* 267 */for (int i = 0; i < arrayOfByte.length; i++)
		/*     */{
			/* 269 */arrayOfByte[i] = (byte) (asciiHexToNibble(paramArrayOfByte[(i * 2)]) << 4);
			/*     */int tmp31_30 = i;
			/*     */byte[] tmp31_29 = arrayOfByte;
			tmp31_29[tmp31_30] = (byte) (tmp31_29[tmp31_30] | asciiHexToNibble(paramArrayOfByte[(i * 2 + 1)]));
			/*     */}
		/*     */
		/* 289 */return arrayOfByte;
		/*     */}

	/*     */
	/*     */public static void printInHex(long paramLong)
	/*     */{
		/* 309 */byte[] arrayOfByte = toHex(paramLong);
		/*     */
		/* 311 */System.out.print(new String(arrayOfByte, 0));
		/*     */}

	/*     */
	/*     */public static void printInHex(int paramInt)
	/*     */{
		/* 331 */byte[] arrayOfByte = toHex(paramInt);
		/*     */
		/* 333 */System.out.print(new String(arrayOfByte, 0));
		/*     */}

	/*     */
	/*     */public static void printInHex(short paramShort)
	/*     */{
		/* 353 */byte[] arrayOfByte = toHex(paramShort);
		/*     */
		/* 355 */System.out.print(new String(arrayOfByte, 0));
		/*     */}

	/*     */
	/*     */public static byte[] toHex(long paramLong)
	/*     */{
		/* 375 */int i = 16;
		/* 376 */byte[] arrayOfByte = new byte[i];
		/*     */
		/* 378 */for (int j = i - 1; j >= 0; j--)
		/*     */{
			/* 380 */arrayOfByte[j] = nibbleToHex((byte) (int) (paramLong & 0xF));
			/* 381 */paramLong >>= 4;
			/*     */}
		/*     */
		/* 399 */return arrayOfByte;
		/*     */}

	/*     */
	/*     */public static byte[] toHex(int paramInt)
	/*     */{
		/* 419 */int i = 8;
		/* 420 */byte[] arrayOfByte = new byte[i];
		/*     */
		/* 422 */for (int j = i - 1; j >= 0; j--)
		/*     */{
			/* 424 */arrayOfByte[j] = nibbleToHex((byte) (paramInt & 0xF));
			/* 425 */paramInt >>= 4;
			/*     */}
		/*     */
		/* 443 */return arrayOfByte;
		/*     */}

	/*     */
	/*     */public static byte[] toHex(short paramShort)
	/*     */{
		/* 463 */int i = 4;
		/* 464 */byte[] arrayOfByte = new byte[i];
		/*     */
		/* 466 */for (int j = i - 1; j >= 0; j--)
		/*     */{
			/* 468 */arrayOfByte[j] = nibbleToHex((byte) (paramShort & 0xF));
			/* 469 */paramShort = (short) (paramShort >> 4);
			/*     */}
		/*     */
		/* 487 */return arrayOfByte;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.util.RepConversion
 * JD-Core Version: 0.6.0
 */