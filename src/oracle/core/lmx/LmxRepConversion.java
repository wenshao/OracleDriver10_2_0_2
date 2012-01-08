package oracle.core.lmx;

import java.io.PrintStream;

public class LmxRepConversion {
	public static void printInHex(byte paramByte) {
		/* 21 */System.out.print((char) nibbleToHex((byte) ((paramByte & 0xF0) >> 4)));
		/* 22 */System.out.print((char) nibbleToHex((byte) (paramByte & 0xF)));
	}

	public static byte nibbleToHex(byte paramByte) {
		/* 36 */paramByte = (byte) (paramByte & 0xF);
		/* 37 */return (byte) (paramByte < 10 ? paramByte + 48 : paramByte - 10 + 65);
	}

	public static byte asciiHexToNibble(byte paramByte) {
		byte i;
		/* 57 */if ((paramByte >= 97) && (paramByte <= 102)) {
			/* 58 */i = (byte) (paramByte - 97 + 10);
		}
		/* 60 */else if ((paramByte >= 65) && (paramByte <= 70)) {
			/* 61 */i = (byte) (paramByte - 65 + 10);
		}
		/* 63 */else if ((paramByte >= 48) && (paramByte <= 57)) {
			/* 64 */i = (byte) (paramByte - 48);
		} else {
			/* 67 */i = paramByte;
		}
		/* 69 */return i;
	}

	public static void bArray2nibbles(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
		/* 81 */for (int i = 0; i < paramArrayOfByte1.length; i++) {
			/* 83 */paramArrayOfByte2[(i * 2)] = nibbleToHex((byte) ((paramArrayOfByte1[i] & 0xF0) >> 4));
			/* 84 */paramArrayOfByte2[(i * 2 + 1)] = nibbleToHex((byte) (paramArrayOfByte1[i] & 0xF));
		}
	}

	public static String bArray2String(byte[] paramArrayOfByte) {
		/* 97 */StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
		/* 98 */for (int i = 0; i < paramArrayOfByte.length; i++) {
			/* 100 */localStringBuffer.append((char) nibbleToHex((byte) ((paramArrayOfByte[i] & 0xF0) >> 4)));
			/* 101 */localStringBuffer.append((char) nibbleToHex((byte) (paramArrayOfByte[i] & 0xF)));
		}
		/* 103 */return localStringBuffer.toString();
	}

	public static byte[] nibbles2bArray(byte[] paramArrayOfByte) {
		/* 117 */byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];

		/* 120 */for (int i = 0; i < arrayOfByte.length; i++) {
			/* 122 */arrayOfByte[i] = (byte) (asciiHexToNibble(paramArrayOfByte[(i * 2)]) << 4);
			int tmp31_30 = i;
			byte[] tmp31_29 = arrayOfByte;
			tmp31_29[tmp31_30] = (byte) (tmp31_29[tmp31_30] | asciiHexToNibble(paramArrayOfByte[(i * 2 + 1)]));
		}
		/* 125 */return arrayOfByte;
	}

	public static void printInHex(long paramLong) {
		/* 130 */byte[] arrayOfByte = toHex(paramLong);
		/* 131 */System.out.print(new String(arrayOfByte, 0));
	}

	public static void printInHex(int paramInt) {
		/* 136 */byte[] arrayOfByte = toHex(paramInt);
		/* 137 */System.out.print(new String(arrayOfByte, 0));
	}

	public static void printInHex(short paramShort) {
		/* 142 */byte[] arrayOfByte = toHex(paramShort);
		/* 143 */System.out.print(new String(arrayOfByte, 0));
	}

	public static byte[] toHex(long paramLong) {
		/* 148 */int i = 16;
		/* 149 */byte[] arrayOfByte = new byte[i];

		/* 151 */for (int j = i - 1; j >= 0; j--) {
			/* 153 */arrayOfByte[j] = nibbleToHex((byte) (int) (paramLong & 0xF));
			/* 154 */paramLong >>= 4;
		}
		/* 156 */return arrayOfByte;
	}

	public static byte[] toHex(int paramInt) {
		/* 161 */int i = 8;
		/* 162 */byte[] arrayOfByte = new byte[i];

		/* 164 */for (int j = i - 1; j >= 0; j--) {
			/* 166 */arrayOfByte[j] = nibbleToHex((byte) (paramInt & 0xF));
			/* 167 */paramInt >>= 4;
		}
		/* 169 */return arrayOfByte;
	}

	public static byte[] toHex(short paramShort) {
		/* 174 */int i = 4;
		/* 175 */byte[] arrayOfByte = new byte[i];

		/* 177 */for (int j = i - 1; j >= 0; j--) {
			/* 179 */arrayOfByte[j] = nibbleToHex((byte) (paramShort & 0xF));
			/* 180 */paramShort = (short) (paramShort >> 4);
		}
		/* 182 */return arrayOfByte;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.core.lmx.LmxRepConversion
 * JD-Core Version: 0.6.0
 */