/*      */package oracle.jdbc.driver;

/*      */
/*      */import B;
/*      */
import java.io.ByteArrayInputStream;
/*      */
import java.io.IOException;
/*      */
import java.io.InputStream;
/*      */
import java.io.OutputStream;
/*      */
import java.sql.SQLException;
/*      */
import java.util.Vector;
/*      */
import oracle.net.ns.BreakNetException;
/*      */
import oracle.net.ns.Communication;
/*      */
import oracle.net.ns.NetException;

/*      */
/*      */class T4CMAREngine
/*      */{
	/*      */static final int TTCC_MXL = 252;
	/*      */static final int TTCC_ESC = 253;
	/*      */static final int TTCC_LNG = 254;
	/*      */static final int TTCC_ERR = 255;
	/*      */static final int TTCC_MXIN = 64;
	/*      */static final byte TTCLXMULTI = 1;
	/*      */static final byte TTCLXMCONV = 2;
	/*      */T4CTypeRep types;
	/*      */Communication net;
	/*      */DBConversion conv;
	/* 97 */short versionNumber = -1;
	/*      */byte proSvrVer;
	/*      */InputStream inStream;
	/*      */OutputStream outStream;
	/* 132 */final byte[] ignored = new byte['ÿ'];
	/* 133 */final byte[] tmpBuffer1 = new byte[1];
	/* 134 */final byte[] tmpBuffer2 = new byte[2];
	/* 135 */final byte[] tmpBuffer3 = new byte[3];
	/* 136 */final byte[] tmpBuffer4 = new byte[4];
	/* 137 */final byte[] tmpBuffer5 = new byte[5];
	/* 138 */final byte[] tmpBuffer6 = new byte[6];
	/* 139 */final byte[] tmpBuffer7 = new byte[7];
	/* 140 */final byte[] tmpBuffer8 = new byte[8];
	/* 141 */final int[] retLen = new int[1];
	/*      */
	/* 2767 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*      */public static final boolean TRACE = false;
	/*      */public static final boolean PRIVATE_TRACE = false;
	/*      */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*      */
	/*      */static String toHex(long paramLong, int paramInt)
	/*      */{
		/*      */String str;
		/* 155 */switch (paramInt)
		/*      */{
		/*      */case 1:
			/* 159 */str = "00" + Long.toString(paramLong & 0xFF, 16);
			/*      */
			/* 161 */break;
		/*      */case 2:
			/* 164 */str = "0000" + Long.toString(paramLong & 0xFFFF, 16);
			/*      */
			/* 166 */break;
		/*      */case 3:
			/* 169 */str = "000000" + Long.toString(paramLong & 0xFFFFFF, 16);
			/*      */
			/* 171 */break;
		/*      */case 4:
			/* 174 */str = "00000000"
					+ Long.toString(paramLong & 0xFFFFFFFF, 16);
			/*      */
			/* 176 */break;
		/*      */case 5:
			/* 179 */str = "0000000000"
					+ Long.toString(paramLong & 0xFFFFFFFF, 16);
			/*      */
			/* 181 */break;
		/*      */case 6:
			/* 184 */str = "000000000000"
					+ Long.toString(paramLong & 0xFFFFFFFF, 16);
			/*      */
			/* 186 */break;
		/*      */case 7:
			/* 189 */str = "00000000000000"
					+ Long.toString(paramLong & 0xFFFFFFFF, 16);
			/*      */
			/* 192 */break;
		/*      */case 8:
			/* 195 */return toHex(paramLong >> 32, 4)
					+ toHex(paramLong, 4).substring(2);
			/*      */default:
			/* 199 */return "more than 8 bytes";
			/*      */}
		/*      */
		/* 202 */return "0x" + str.substring(str.length() - 2 * paramInt);
		/*      */}

	/*      */
	/*      */static String toHex(byte paramByte)
	/*      */{
		/* 207 */String str = "00" + Integer.toHexString(paramByte & 0xFF);
		/*      */
		/* 209 */return "0x" + str.substring(str.length() - 2);
		/*      */}

	/*      */
	/*      */static String toHex(short paramShort)
	/*      */{
		/* 214 */return toHex(paramShort, 2);
		/*      */}

	/*      */
	/*      */static String toHex(int paramInt)
	/*      */{
		/* 219 */return toHex(paramInt, 4);
		/*      */}

	/*      */
	/*      */static String toHex(byte[] paramArrayOfByte, int paramInt)
	/*      */{
		/* 224 */if (paramArrayOfByte == null) {
			/* 225 */return "null";
			/*      */}
		/* 227 */if (paramInt > paramArrayOfByte.length) {
			/* 228 */return "byte array not long enough";
			/*      */}
		/* 230 */String str = "[";
		/* 231 */int i = Math.min(64, paramInt);
		/*      */
		/* 233 */for (int j = 0; j < i; j++)
		/*      */{
			/* 235 */str = str + toHex(paramArrayOfByte[j]) + " ";
			/*      */}
		/*      */
		/* 238 */if (i < paramInt) {
			/* 239 */str = str + "...";
			/*      */}
		/* 241 */return str + "]";
		/*      */}

	/*      */
	/*      */static String toHex(byte[] paramArrayOfByte)
	/*      */{
		/* 246 */if (paramArrayOfByte == null) {
			/* 247 */return "null";
			/*      */}
		/* 249 */return toHex(paramArrayOfByte, paramArrayOfByte.length);
		/*      */}

	/*      */
	/*      */T4CMAREngine(Communication paramCommunication)
	/*      */throws SQLException, IOException
	/*      */{
		/* 267 */if (paramCommunication == null)
		/*      */{
			/* 272 */DatabaseError.throwSqlException(433);
			/*      */}
		/*      */
		/* 276 */this.net = paramCommunication;
		/*      */try
		/*      */{
			/* 280 */this.inStream = paramCommunication.getInputStream();
			/* 281 */this.outStream = paramCommunication.getOutputStream();
			/*      */}
		/*      */catch (NetException localNetException)
		/*      */{
			/* 285 */throw new IOException(localNetException.getMessage());
			/*      */}
		/*      */
		/* 288 */this.types = new T4CTypeRep();
		/*      */
		/* 290 */this.types.setRep(1, 2);
		/*      */}

	/*      */
	/*      */void initBuffers()
	/*      */{
		/*      */}

	/*      */
	/*      */void marshalSB1(byte paramByte)
	/*      */throws IOException
	/*      */{
		/* 332 */this.outStream.write(paramByte);
		/*      */}

	/*      */
	/*      */void marshalUB1(short paramShort)
	/*      */throws IOException
	/*      */{
		/* 347 */this.outStream.write((byte) (paramShort & 0xFF));
		/*      */}

	/*      */
	/*      */void marshalSB2(short paramShort)
	/*      */throws IOException
	/*      */{
		/* 366 */int i = value2Buffer(paramShort, this.tmpBuffer2, 1);
		/*      */
		/* 371 */if (i != 0)
			/* 372 */this.outStream.write(this.tmpBuffer2, 0, i);
		/*      */}

	/*      */
	/*      */void marshalUB2(int paramInt)
	/*      */throws IOException
	/*      */{
		/* 387 */marshalSB2((short) (paramInt & 0xFFFF));
		/*      */}

	/*      */
	/*      */void marshalSB4(int paramInt)
	/*      */throws IOException
	/*      */{
		/* 405 */int i = value2Buffer(paramInt, this.tmpBuffer4, 2);
		/*      */
		/* 411 */if (i != 0)
			/* 412 */this.outStream.write(this.tmpBuffer4, 0, i);
		/*      */}

	/*      */
	/*      */void marshalUB4(long paramLong)
	/*      */throws IOException
	/*      */{
		/* 426 */marshalSB4((int) (paramLong & 0xFFFFFFFF));
		/*      */}

	/*      */
	/*      */void marshalSB8(long paramLong)
	/*      */throws IOException
	/*      */{
		/* 444 */int i = value2Buffer(paramLong, this.tmpBuffer8, 3);
		/*      */
		/* 450 */if (i != 0)
			/* 451 */this.outStream.write(this.tmpBuffer8, 0, i);
		/*      */}

	/*      */
	/*      */void marshalSWORD(int paramInt)
	/*      */throws IOException
	/*      */{
		/* 465 */marshalSB4(paramInt);
		/*      */}

	/*      */
	/*      */void marshalUWORD(long paramLong)
	/*      */throws IOException
	/*      */{
		/* 479 */marshalSB4((int) (paramLong & 0xFFFFFFFF));
		/*      */}

	/*      */
	/*      */void marshalB1Array(byte[] paramArrayOfByte)
	/*      */throws IOException
	/*      */{
		/* 494 */if (paramArrayOfByte.length > 0)
			/* 495 */this.outStream.write(paramArrayOfByte);
		/*      */}

	/*      */
	/*      */void marshalB1Array(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*      */throws IOException
	/*      */{
		/* 513 */if (paramArrayOfByte.length > 0)
			/* 514 */this.outStream.write(paramArrayOfByte, paramInt1,
					paramInt2);
		/*      */}

	/*      */
	/*      */void marshalUB4Array(long[] paramArrayOfLong)
	/*      */throws IOException
	/*      */{
		/* 528 */for (int i = 0; i < paramArrayOfLong.length; i++)
			/* 529 */marshalSB4((int) (paramArrayOfLong[i] & 0xFFFFFFFF));
		/*      */}

	/*      */
	/*      */void marshalO2U(boolean paramBoolean)
	/*      */throws IOException
	/*      */{
		/* 573 */if (paramBoolean)
			/* 574 */addPtr(1);
		/*      */else
			/* 576 */addPtr(0);
		/*      */}

	/*      */
	/*      */void marshalNULLPTR()
	/*      */throws IOException
	/*      */{
		/* 594 */addPtr(0);
		/*      */}

	/*      */
	/*      */void marshalPTR()
	/*      */throws IOException
	/*      */{
		/* 614 */addPtr(1);
		/*      */}

	/*      */
	/*      */void marshalCHR(byte[] paramArrayOfByte)
	/*      */throws IOException
	/*      */{
		/* 627 */marshalCHR(paramArrayOfByte, 0, paramArrayOfByte.length);
		/*      */}

	/*      */
	/*      */void marshalCHR(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	/*      */throws IOException
	/*      */{
		/* 636 */if (paramInt2 > 0)
		/*      */{
			/* 638 */if (this.types.isConvNeeded())
				/* 639 */marshalCLR(paramArrayOfByte, paramInt1, paramInt2);
			/*      */else
				/* 641 */this.outStream.write(paramArrayOfByte, paramInt1,
						paramInt2);
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalCLR(byte[] paramArrayOfByte, int paramInt)
	/*      */throws IOException
	/*      */{
		/* 657 */marshalCLR(paramArrayOfByte, 0, paramInt);
		/*      */}

	/*      */
	/*      */void marshalCLR(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	/*      */throws IOException
	/*      */{
		/* 672 */if (paramInt2 > 64)
		/*      */{
			/* 674 */int k = 0;
			/*      */
			/* 680 */this.outStream.write(-2);
			/*      */do
			/*      */{
				/* 684 */int i = paramInt2 - k;
				/* 685 */int j = i > 64 ? 64 : i;
				/*      */
				/* 699 */this.outStream.write((byte) (j & 0xFF));
				/* 700 */this.outStream.write(paramArrayOfByte, paramInt1 + k,
						j);
				/*      */
				/* 703 */k += j;
				/*      */}
			/* 705 */while (k < paramInt2);
			/*      */
			/* 707 */this.outStream.write(0);
			/*      */}
		/*      */else
		/*      */{
			/* 719 */this.outStream.write((byte) (paramInt2 & 0xFF));
			/*      */
			/* 726 */if (paramArrayOfByte.length != 0)
				/* 727 */this.outStream.write(paramArrayOfByte, paramInt1,
						paramInt2);
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalKEYVAL(byte[][] paramArrayOfByte1, int[] paramArrayOfInt1,
			byte[][] paramArrayOfByte2, int[] paramArrayOfInt2,
			byte[] paramArrayOfByte, int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 744 */for (int i = 0; i < paramInt; i++)
		/*      */{
			/* 746 */if ((paramArrayOfByte1[i] != null)
					&& (paramArrayOfInt1[i] > 0))
			/*      */{
				/* 748 */marshalUB4(paramArrayOfInt1[i]);
				/* 749 */marshalCLR(paramArrayOfByte1[i], 0,
						paramArrayOfInt1[i]);
				/*      */}
			/*      */else {
				/* 752 */marshalUB4(0L);
				/*      */}
			/* 754 */if ((paramArrayOfByte2[i] != null)
					&& (paramArrayOfInt2[i] > 0))
			/*      */{
				/* 756 */marshalUB4(paramArrayOfInt2[i]);
				/* 757 */marshalCLR(paramArrayOfByte2[i], 0,
						paramArrayOfInt2[i]);
				/*      */}
			/*      */else {
				/* 760 */marshalUB4(0L);
				/*      */}
			/*      */
			/* 763 */if (paramArrayOfByte[i] != 0)
				/* 764 */marshalUB4(1L);
			/*      */else
				/* 766 */marshalUB4(0L);
			/*      */}
		/*      */}

	/*      */
	/*      */void marshalKEYVAL(byte[][] paramArrayOfByte1,
			byte[][] paramArrayOfByte2, byte[] paramArrayOfByte, int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 777 */int[] arrayOfInt1 = new int[paramInt];
		/* 778 */int[] arrayOfInt2 = new int[paramInt];
		/* 779 */for (int i = 0; i < paramInt; i++)
		/*      */{
			/* 781 */if (paramArrayOfByte1[i] != null)
				/* 782 */arrayOfInt1[i] = paramArrayOfByte1[i].length;
			/* 783 */if (paramArrayOfByte2[i] != null)
				/* 784 */arrayOfInt2[i] = paramArrayOfByte2[i].length;
			/*      */}
		/* 786 */marshalKEYVAL(paramArrayOfByte1, arrayOfInt1,
				paramArrayOfByte2, arrayOfInt2, paramArrayOfByte, paramInt);
		/*      */}

	/*      */
	/*      */void marshalDALC(byte[] paramArrayOfByte)
	/*      */throws SQLException, IOException
	/*      */{
		/* 806 */if ((paramArrayOfByte == null)
				|| (paramArrayOfByte.length < 1))
		/*      */{
			/* 808 */this.outStream.write(0);
			/*      */}
		/*      */else
		/*      */{
			/* 812 */marshalSB4(0xFFFFFFFF & paramArrayOfByte.length);
			/* 813 */marshalCLR(paramArrayOfByte, paramArrayOfByte.length);
			/*      */}
		/*      */}

	/*      */
	/*      */void addPtr(byte paramByte)
	/*      */throws IOException
	/*      */{
		/* 838 */if ((this.types.rep[4] & 0x1) > 0) {
			/* 839 */this.outStream.write(paramByte);
			/*      */}
		/*      */else
		/*      */{
			/* 847 */int i = value2Buffer(paramByte, this.tmpBuffer4, 4);
			/*      */
			/* 850 */if (i != 0)
				/* 851 */this.outStream.write(this.tmpBuffer4, 0, i);
			/*      */}
		/*      */}

	/*      */
	/*      */byte value2Buffer(int paramInt, byte[] paramArrayOfByte, byte paramByte)
	/*      */throws IOException
	/*      */{
		/* 877 */int i = 1;
		/* 878 */byte b = 0;
		/*      */
		/* 882 */for (int j = paramArrayOfByte.length - 1; j >= 0; j--)
		/*      */{
			/* 884 */paramArrayOfByte[b] = (byte) (paramInt >>> 8 * j & 0xFF);
			/*      */
			/* 891 */if ((this.types.rep[paramByte] & 0x1) > 0)
			/*      */{
				/* 893 */if ((i != 0) && (paramArrayOfByte[b] == 0))
					/*      */continue;
				/* 895 */i = 0;
				/* 896 */b = (byte) (b + 1);
				/*      */}
			/*      */else
			/*      */{
				/* 900 */b = (byte) (b + 1);
				/*      */}
			/*      */}
		/*      */
		/* 904 */if ((this.types.rep[paramByte] & 0x1) > 0) {
			/* 905 */this.outStream.write(b);
			/*      */}
		/*      */
		/* 909 */if ((this.types.rep[paramByte] & 0x2) > 0) {
			/* 910 */reverseArray(paramArrayOfByte, b);
			/*      */}
		/*      */
		/* 936 */return b;
		/*      */}

	/*      */
	/*      */byte value2Buffer(long paramLong, byte[] paramArrayOfByte,
			byte paramByte)
	/*      */throws IOException
	/*      */{
		/* 946 */int i = 1;
		/* 947 */byte b = 0;
		/*      */
		/* 951 */for (int j = paramArrayOfByte.length - 1; j >= 0; j--)
		/*      */{
			/* 953 */paramArrayOfByte[b] = (byte) (int) (paramLong >>> 8 * j & 0xFF);
			/*      */
			/* 960 */if ((this.types.rep[paramByte] & 0x1) > 0)
			/*      */{
				/* 962 */if ((i != 0) && (paramArrayOfByte[b] == 0))
					/*      */continue;
				/* 964 */i = 0;
				/* 965 */b = (byte) (b + 1);
				/*      */}
			/*      */else
			/*      */{
				/* 969 */b = (byte) (b + 1);
				/*      */}
			/*      */}
		/*      */
		/* 973 */if ((this.types.rep[paramByte] & 0x1) > 0) {
			/* 974 */this.outStream.write(b);
			/*      */}
		/*      */
		/* 978 */if ((this.types.rep[paramByte] & 0x2) > 0) {
			/* 979 */reverseArray(paramArrayOfByte, b);
			/*      */}
		/*      */
		/* 1005 */return b;
		/*      */}

	/*      */
	/*      */void reverseArray(byte[] paramArrayOfByte, byte paramByte)
	/*      */{
		/* 1023 */for (int j = 0; j < paramByte / 2; j++)
		/*      */{
			/* 1025 */int i = paramArrayOfByte[j];
			/* 1026 */paramArrayOfByte[j] = paramArrayOfByte[(paramByte - 1 - j)];
			/* 1027 */paramArrayOfByte[(paramByte - 1 - j)] = i;
			/*      */}
		/*      */}

	/*      */
	/*      */byte unmarshalSB1()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1070 */int i = (byte) unmarshalUB1();
		/*      */
		/* 1074 */return i;
		/*      */}

	/*      */
	/*      */short unmarshalUB1()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1093 */int i = 0;
		/*      */try
		/*      */{
			/* 1099 */i = (short) this.inStream.read();
			/*      */}
		/*      */catch (BreakNetException localBreakNetException)
		/*      */{
			/* 1107 */this.net.sendReset();
			/* 1108 */throw localBreakNetException;
			/*      */}
		/*      */
		/* 1113 */if (i < 0)
		/*      */{
			/* 1118 */DatabaseError.throwSqlException(410);
			/*      */}
		/*      */
		/* 1129 */return i;
		/*      */}

	/*      */
	/*      */short unmarshalSB2()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1149 */int i = (short) unmarshalUB2();
		/*      */
		/* 1154 */return i;
		/*      */}

	/*      */
	/*      */int unmarshalUB2()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1177 */int i = (int) buffer2Value(1);
		/*      */
		/* 1188 */return i & 0xFFFF;
		/*      */}

	/*      */
	/*      */int unmarshalUCS2(byte[] paramArrayOfByte, long paramLong)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1207 */int i = unmarshalUB2();
		/*      */
		/* 1209 */this.tmpBuffer2[0] = (byte) ((i & 0xFF00) >> 8);
		/* 1210 */this.tmpBuffer2[1] = (byte) (i & 0xFF);
		/*      */
		/* 1213 */if (paramLong + 1L < paramArrayOfByte.length)
		/*      */{
			/* 1217 */paramArrayOfByte[(int) paramLong] = this.tmpBuffer2[0];
			/* 1218 */paramArrayOfByte[((int) paramLong + 1)] = this.tmpBuffer2[1];
			/*      */}
		/*      */
		/* 1244 */return this.tmpBuffer2[0] == 0 ? 2
				: this.tmpBuffer2[1] == 0 ? 1 : 3;
		/*      */}

	/*      */
	/*      */int unmarshalSB4()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1264 */int i = (int) unmarshalUB4();
		/*      */
		/* 1268 */return i;
		/*      */}

	/*      */
	/*      */long unmarshalUB4()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1290 */long l = buffer2Value(2);
		/*      */
		/* 1296 */return l;
		/*      */}

	/*      */
	/*      */int unmarshalSB4(byte[] paramArrayOfByte)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1321 */long l = buffer2Value(2, new ByteArrayInputStream(
				paramArrayOfByte));
		/*      */
		/* 1327 */return (int) l;
		/*      */}

	/*      */
	/*      */long unmarshalSB8()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1349 */long l = buffer2Value(3);
		/*      */
		/* 1354 */return l;
		/*      */}

	/*      */
	/*      */int unmarshalRefCursor(byte[] paramArrayOfByte)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1366 */int i = unmarshalSB4(paramArrayOfByte);
		/*      */
		/* 1370 */return i;
		/*      */}

	/*      */
	/*      */int unmarshalSWORD()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1392 */int i = (int) unmarshalUB4();
		/*      */
		/* 1396 */return i;
		/*      */}

	/*      */
	/*      */long unmarshalUWORD()
	/*      */throws SQLException, IOException
	/*      */{
		/* 1417 */long l = unmarshalUB4();
		/*      */
		/* 1421 */return l;
		/*      */}

	/*      */
	/*      */byte[] unmarshalNBytes(int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1440 */byte[] arrayOfByte = new byte[paramInt];
		/*      */try
		/*      */{
			/* 1446 */if (this.inStream.read(arrayOfByte) < 0)
			/*      */{
				/* 1451 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (BreakNetException localBreakNetException)
		/*      */{
			/* 1461 */this.net.sendReset();
			/* 1462 */throw localBreakNetException;
			/*      */}
		/*      */
		/* 1468 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */int unmarshalNBytes(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1487 */int i = 0;
		/*      */
		/* 1489 */while (i < paramInt2) {
			/* 1490 */i += getNBytes(paramArrayOfByte, paramInt1 + i, paramInt2
					- i);
			/*      */}
		/*      */
		/* 1494 */return i;
		/*      */}

	/*      */
	/*      */int getNBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1514 */int i = 0;
		/*      */try
		/*      */{
			/* 1520 */if ((i = this.inStream.read(paramArrayOfByte, paramInt1,
					paramInt2)) < 0)
			/*      */{
				/* 1525 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (BreakNetException localBreakNetException)
		/*      */{
			/* 1535 */this.net.sendReset();
			/* 1536 */throw localBreakNetException;
			/*      */}
		/*      */
		/* 1544 */return i;
		/*      */}

	/*      */
	/*      */byte[] getNBytes(int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1562 */byte[] arrayOfByte = new byte[paramInt];
		/*      */try
		/*      */{
			/* 1568 */if (this.inStream.read(arrayOfByte) < 0)
			/*      */{
				/* 1573 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (BreakNetException localBreakNetException)
		/*      */{
			/* 1583 */this.net.sendReset();
			/* 1584 */throw localBreakNetException;
			/*      */}
		/*      */
		/* 1590 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */byte[] unmarshalTEXT(int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1613 */int i = 0;
		/*      */
		/* 1617 */byte[] arrayOfByte1 = new byte[paramInt];
		/*      */
		/* 1620 */while (i < paramInt)
		/*      */{
			/*      */try
			/*      */{
				/* 1626 */if (this.inStream.read(arrayOfByte1, i, 1) < 0)
				/*      */{
					/* 1631 */DatabaseError.throwSqlException(410);
					/*      */}
				/*      */
				/*      */}
			/*      */catch (BreakNetException localBreakNetException)
			/*      */{
				/* 1641 */this.net.sendReset();
				/* 1642 */throw localBreakNetException;
				/*      */}
			/*      */
			/* 1646 */if (arrayOfByte1[(i++)] == 0) {
				/* 1647 */break;
				/*      */}
			/*      */}
		/*      */
		/* 1651 */i--;
		/*      */byte[] arrayOfByte2;
		/* 1651 */if (arrayOfByte1.length == i)
		/*      */{
			/* 1653 */arrayOfByte2 = arrayOfByte1;
			/*      */}
		/*      */else
		/*      */{
			/* 1657 */arrayOfByte2 = new byte[i];
			/*      */
			/* 1659 */System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
			/*      */}
		/*      */
		/* 1664 */return arrayOfByte2;
		/*      */}

	/*      */
	/*      */byte[] unmarshalCHR(int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1684 */Object localObject = null;
		/*      */
		/* 1686 */if (this.types.isConvNeeded())
		/*      */{
			/* 1688 */localObject = unmarshalCLR(paramInt, this.retLen);
			/*      */
			/* 1690 */if (localObject.length != this.retLen[0])
			/*      */{
				/* 1692 */byte[] arrayOfByte = new byte[this.retLen[0]];
				/*      */
				/* 1694 */System.arraycopy(localObject, 0, arrayOfByte, 0,
						this.retLen[0]);
				/*      */
				/* 1696 */localObject = arrayOfByte;
				/*      */}
			/*      */}
		/*      */else
		/*      */{
			/* 1701 */localObject = getNBytes(paramInt);
			/*      */}
		/*      */
		/* 1706 */return (B) localObject;
		/*      */}

	/*      */
	/*      */void unmarshalCLR(byte[] paramArrayOfByte, int paramInt,
			int[] paramArrayOfInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1713 */unmarshalCLR(paramArrayOfByte, paramInt, paramArrayOfInt,
				2147483647);
		/*      */}

	/*      */
	/*      */void unmarshalCLR(byte[] paramArrayOfByte, int paramInt1,
			int[] paramArrayOfInt, int paramInt2)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1738 */int i = 0;
		/* 1739 */int j = paramInt1;
		/* 1740 */int k = 0;
		/* 1741 */int m = 0;
		/* 1742 */int n = 0;
		/*      */
		/* 1748 */int i1 = -1;
		/*      */
		/* 1750 */i = unmarshalUB1();
		/*      */
		/* 1753 */if (i < 0)
		/*      */{
			/* 1758 */DatabaseError.throwSqlException(401);
			/*      */}
		/*      */
		/* 1761 */if (i == 0)
		/*      */{
			/* 1767 */paramArrayOfInt[0] = 0;
			/*      */
			/* 1769 */return;
			/*      */}
		/*      */
		/* 1772 */if (escapeSequenceNull(i))
		/*      */{
			/* 1778 */paramArrayOfInt[0] = 0;
			/*      */
			/* 1780 */return;
			/*      */}
		/*      */int i2;
		/* 1783 */if (i != 254)
		/*      */{
			/* 1790 */n = Math.min(paramInt2 - m, i);
			/* 1791 */j = unmarshalBuffer(paramArrayOfByte, j, n);
			/* 1792 */m += n;
			/*      */
			/* 1795 */i2 = i - n;
			/*      */
			/* 1797 */if (i2 > 0)
				/* 1798 */unmarshalBuffer(this.ignored, 0, i2);
			/*      */}
		/*      */else
		/*      */{
			/* 1802 */i1 = -1;
			/*      */while (true)
			/*      */{
				/* 1809 */if (i1 != -1)
				/*      */{
					/* 1811 */i = unmarshalUB1();
					/*      */
					/* 1814 */if (i <= 0) {
						/*      */break;
						/*      */}
					/*      */}
				/* 1818 */if (i == 254)
				/*      */{
					/* 1820 */switch (i1)
					/*      */{
					/*      */case -1:
						/* 1828 */i1 = 1;
						/*      */
						/* 1830 */break;
					/*      */case 1:
						/* 1837 */i1 = 0;
						/*      */
						/* 1839 */break;
					/*      */case 0:
						/* 1842 */if (k != 0)
						/*      */{
							/* 1848 */i1 = 0;
							/*      */}
						/*      */else
						/*      */{
							/* 1857 */i1 = 0;
							/*      */}
						/* 1859 */break;
					/*      */}
					/*      */
					/*      */}
				/*      */
				/* 1867 */if (j == -1)
				/*      */{
					/* 1869 */unmarshalBuffer(this.ignored, 0, i);
					/*      */}
				/*      */else
				/*      */{
					/* 1878 */n = Math.min(paramInt2 - m, i);
					/* 1879 */j = unmarshalBuffer(paramArrayOfByte, j, n);
					/* 1880 */m += n;
					/*      */
					/* 1883 */i2 = i - n;
					/*      */
					/* 1885 */if (i2 > 0) {
						/* 1886 */unmarshalBuffer(this.ignored, 0, i2);
						/*      */}
					/*      */}
				/*      */
				/* 1890 */i1 = 0;
				/*      */
				/* 1892 */if (i > 252) {
					/* 1893 */k = 1;
					/*      */}
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1899 */if (paramArrayOfInt != null)
		/*      */{
			/* 1901 */if (j != -1) {
				/* 1902 */paramArrayOfInt[0] = m;
				/*      */}
			/*      */else
				/* 1905 */paramArrayOfInt[0] = (paramArrayOfByte.length - paramInt1);
			/*      */}
		/*      */}

	/*      */
	/*      */byte[] unmarshalCLR(int paramInt, int[] paramArrayOfInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1923 */byte[] arrayOfByte = new byte[paramInt
				* this.conv.c2sNlsRatio];
		/*      */
		/* 1925 */unmarshalCLR(arrayOfByte, 0, paramArrayOfInt, paramInt);
		/*      */
		/* 1929 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */int unmarshalKEYVAL(byte[][] paramArrayOfByte1,
			byte[][] paramArrayOfByte2, int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1940 */byte[] arrayOfByte = new byte[1000];
		/* 1941 */int[] arrayOfInt = new int[1];
		/* 1942 */int i = 0;
		/*      */
		/* 1945 */for (int k = 0; k < paramInt; k++)
		/*      */{
			/* 1947 */int j = unmarshalSB4();
			/*      */
			/* 1949 */if (j > 0)
			/*      */{
				/* 1951 */unmarshalCLR(arrayOfByte, 0, arrayOfInt);
				/*      */
				/* 1953 */paramArrayOfByte1[k] = new byte[arrayOfInt[0]];
				/*      */
				/* 1955 */System.arraycopy(arrayOfByte, 0,
						paramArrayOfByte1[k], 0, arrayOfInt[0]);
				/*      */}
			/*      */
			/* 1958 */j = unmarshalSB4();
			/*      */
			/* 1960 */if (j > 0)
			/*      */{
				/* 1962 */unmarshalCLR(arrayOfByte, 0, arrayOfInt);
				/*      */
				/* 1964 */paramArrayOfByte2[k] = new byte[arrayOfInt[0]];
				/*      */
				/* 1966 */System.arraycopy(arrayOfByte, 0,
						paramArrayOfByte2[k], 0, arrayOfInt[0]);
				/*      */}
			/*      */
			/* 1969 */i = unmarshalSB4();
			/*      */}
		/*      */
		/* 1972 */arrayOfByte = null;
		/*      */
		/* 1975 */return i;
		/*      */}

	/*      */
	/*      */int unmarshalBuffer(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2)
	/*      */throws SQLException, IOException
	/*      */{
		/* 1985 */if (paramInt2 <= 0) {
			/* 1986 */return paramInt1;
			/*      */}
		/* 1988 */if (paramArrayOfByte.length < paramInt1 + paramInt2)
		/*      */{
			/* 1990 */unmarshalNBytes(paramArrayOfByte, paramInt1,
					paramArrayOfByte.length - paramInt1);
			/*      */
			/* 1993 */unmarshalNBytes(this.ignored, 0, paramInt1 + paramInt2
					- paramArrayOfByte.length);
			/*      */
			/* 1998 */paramInt1 = -1;
			/*      */}
		/*      */else
		/*      */{
			/* 2004 */unmarshalNBytes(paramArrayOfByte, paramInt1, paramInt2);
			/*      */
			/* 2006 */paramInt1 += paramInt2;
			/*      */}
		/*      */
		/* 2011 */return paramInt1;
		/*      */}

	/*      */
	/*      */byte[] unmarshalCLRforREFS()
	/*      */throws SQLException, IOException
	/*      */{
		/* 2021 */int i = 0;
		/* 2022 */int j = 0;
		/* 2023 */byte[] arrayOfByte1 = null;
		/* 2024 */Vector localVector = new Vector(10, 10);
		/*      */
		/* 2026 */int k = unmarshalUB1();
		/*      */
		/* 2034 */if (k < 0)
		/*      */{
			/* 2039 */DatabaseError.throwSqlException(401);
			/*      */}
		/*      */
		/* 2042 */if (k == 0)
		/*      */{
			/* 2047 */return null;
			/*      */}
		/*      */
		/* 2051 */if (!escapeSequenceNull(k))
		/*      */{
			/* 2053 */if (k == 254)
			/*      */{
				/* 2057 */while ((i = unmarshalUB1()) > 0)
				/*      */{
					/* 2063 */if ((i == 254) &&
					/* 2065 */(this.types.isServerConversion()))
					/*      */{
						/*      */continue;
						/*      */}
					/* 2069 */j = (short) (j + i);
					/*      */
					/* 2074 */arrayOfByte2 = new byte[i];
					/*      */
					/* 2076 */unmarshalBuffer(arrayOfByte2, 0, i);
					/* 2077 */localVector.addElement(arrayOfByte2);
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 2082 */j = k;
			/*      */
			/* 2084 */byte[] arrayOfByte2 = new byte[k];
			/*      */
			/* 2086 */unmarshalBuffer(arrayOfByte2, 0, k);
			/* 2087 */localVector.addElement(arrayOfByte2);
			/*      */
			/* 2094 */arrayOfByte1 = new byte[j];
			/*      */
			/* 2096 */int m = 0;
			/*      */
			/* 2098 */while (localVector.size() > 0)
			/*      */{
				/* 2100 */int n = ((byte[]) localVector.elementAt(0)).length;
				/*      */
				/* 2102 */System.arraycopy(localVector.elementAt(0), 0,
						arrayOfByte1, m, n);
				/*      */
				/* 2105 */m += n;
				/*      */
				/* 2109 */localVector.removeElementAt(0);
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2115 */arrayOfByte1 = null;
		/*      */
		/* 2120 */return arrayOfByte1;
		/*      */}

	/*      */
	/*      */boolean escapeSequenceNull(int paramInt)
	/*      */throws SQLException
	/*      */{
		/* 2136 */int i = 0;
		/*      */
		/* 2138 */switch (paramInt)
		/*      */{
		/*      */case 0:
			/* 2149 */i = 1;
			/*      */
			/* 2151 */break;
		/*      */case 253:
			/* 2158 */DatabaseError.throwSqlException(401);
			/*      */case 255:
			/* 2170 */i = 1;
			/*      */
			/* 2172 */break;
		/*      */case 254:
			/* 2184 */break;
		/*      */}
		/*      */
		/* 2196 */return i;
		/*      */}

	/*      */
	/*      */int processIndicator(boolean paramBoolean, int paramInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 2210 */int i = unmarshalSB2();
		/* 2211 */int j = 0;
		/*      */
		/* 2217 */if (!paramBoolean)
		/*      */{
			/* 2219 */if (i == 0)
				/* 2220 */j = paramInt;
			/* 2221 */else if ((i == -2) || (i > 0)) {
				/* 2222 */j = i;
				/*      */}
			/*      */else
			/*      */{
				/* 2226 */j = 65536 + i;
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 2231 */return j;
		/*      */}

	/*      */
	/*      */long unmarshalDALC(byte[] paramArrayOfByte, int paramInt,
			int[] paramArrayOfInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 2264 */long l = unmarshalUB4();
		/*      */
		/* 2266 */if (l > 0L) {
			/* 2267 */unmarshalCLR(paramArrayOfByte, paramInt, paramArrayOfInt);
			/*      */}
		/*      */
		/* 2272 */return l;
		/*      */}

	/*      */
	/*      */byte[] unmarshalDALC()
	/*      */throws SQLException, IOException
	/*      */{
		/* 2281 */long l = unmarshalUB4();
		/* 2282 */byte[] arrayOfByte = new byte[(int) (0xFFFFFFFF & l)];
		/*      */
		/* 2284 */if (arrayOfByte.length > 0)
		/*      */{
			/* 2286 */arrayOfByte = unmarshalCLR(arrayOfByte.length,
					this.retLen);
			/*      */
			/* 2288 */if (arrayOfByte == null)
			/*      */{
				/* 2293 */DatabaseError.throwSqlException(401);
				/*      */}
			/*      */}
		/*      */else {
			/* 2297 */arrayOfByte = new byte[0];
			/*      */}
		/*      */
		/* 2332 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */byte[] unmarshalDALC(int[] paramArrayOfInt)
	/*      */throws SQLException, IOException
	/*      */{
		/* 2341 */long l = unmarshalUB4();
		/* 2342 */byte[] arrayOfByte = new byte[(int) (0xFFFFFFFF & l)];
		/*      */
		/* 2345 */if (arrayOfByte.length > 0)
		/*      */{
			/* 2347 */arrayOfByte = unmarshalCLR(arrayOfByte.length,
					paramArrayOfInt);
			/*      */
			/* 2349 */if (arrayOfByte == null)
			/*      */{
				/* 2354 */DatabaseError.throwSqlException(401);
				/*      */}
			/*      */}
		/*      */else {
			/* 2358 */arrayOfByte = new byte[0];
			/*      */}
		/*      */
		/* 2397 */return arrayOfByte;
		/*      */}

	/*      */
	/*      */long buffer2Value(byte paramByte)
	/*      */throws SQLException, IOException
	/*      */{
		/* 2427 */int i = 0;
		/*      */
		/* 2429 */long l2 = 0L;
		/* 2430 */int j = 0;
		/*      */
		/* 2433 */if ((this.types.rep[paramByte] & 0x1) > 0)
		/*      */{
			/*      */try
			/*      */{
				/* 2439 */i = this.inStream.read();
				/*      */}
			/*      */catch (BreakNetException localBreakNetException1)
			/*      */{
				/* 2448 */this.net.sendReset();
				/* 2449 */throw localBreakNetException1;
				/*      */}
			/*      */
			/* 2454 */if ((i & 0x80) > 0)
			/*      */{
				/* 2456 */i &= 127;
				/* 2457 */j = 1;
				/*      */}
			/*      */
			/* 2465 */if (i < 0)
			/*      */{
				/* 2470 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/* 2476 */if (i == 0)
			/*      */{
				/* 2482 */return 0L;
				/*      */}
			/*      */
			/* 2486 */if (((paramByte == 1) && (i > 2))
					|| ((paramByte == 2) && (i > 4))
					|| ((paramByte == 3) && (i > 8)))
			/*      */{
				/* 2493 */DatabaseError.throwSqlException(412);
				/*      */}
			/*      */
			/*      */}
		/* 2498 */else if (paramByte == 1) {
			/* 2499 */i = 2;
			/* 2500 */} else if (paramByte == 2) {
			/* 2501 */i = 4;
			/* 2502 */} else if (paramByte == 3) {
			/* 2503 */i = 8;
			/*      */}
		/*      */byte[] arrayOfByte;
		/* 2506 */switch (i)
		/*      */{
		/*      */case 1:
			/* 2510 */arrayOfByte = this.tmpBuffer1;
			/*      */
			/* 2512 */break;
		/*      */case 2:
			/* 2515 */arrayOfByte = this.tmpBuffer2;
			/*      */
			/* 2517 */break;
		/*      */case 3:
			/* 2520 */arrayOfByte = this.tmpBuffer3;
			/*      */
			/* 2522 */break;
		/*      */case 4:
			/* 2525 */arrayOfByte = this.tmpBuffer4;
			/*      */
			/* 2527 */break;
		/*      */case 5:
			/* 2530 */arrayOfByte = this.tmpBuffer5;
			/*      */
			/* 2532 */break;
		/*      */case 6:
			/* 2535 */arrayOfByte = this.tmpBuffer6;
			/*      */
			/* 2537 */break;
		/*      */case 7:
			/* 2540 */arrayOfByte = this.tmpBuffer7;
			/*      */
			/* 2542 */break;
		/*      */case 8:
			/* 2545 */arrayOfByte = this.tmpBuffer8;
			/*      */
			/* 2547 */break;
		/*      */default:
			/* 2550 */arrayOfByte = new byte[i];
			/*      */}
		/*      */
		/*      */try
		/*      */{
			/* 2564 */if (this.inStream.read(arrayOfByte) < 0)
			/*      */{
				/* 2569 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (BreakNetException localBreakNetException2)
		/*      */{
			/* 2579 */this.net.sendReset();
			/* 2580 */throw localBreakNetException2;
			/*      */}
		/*      */
		/* 2584 */for (int k = 0; k < arrayOfByte.length; k++)
		/*      */{
			/*      */long l1;
			/* 2593 */if ((this.types.rep[paramByte] & 0x2) > 0)
			/*      */{
				/* 2598 */l1 = arrayOfByte[(arrayOfByte.length - 1 - k)] & 0xFF & 0xFF;
				/*      */}
			/*      */else
			/*      */{
				/* 2606 */l1 = arrayOfByte[k] & 0xFF & 0xFF;
				/*      */}
			/*      */
			/* 2609 */l2 |= l1 << 8 * (arrayOfByte.length - 1 - k);
			/*      */}
		/*      */
		/* 2615 */if (paramByte != 3)
		/*      */{
			/* 2619 */l2 &= -1L;
			/*      */}
		/*      */
		/* 2622 */if (j != 0) {
			/* 2623 */l2 = -l2;
			/*      */}
		/*      */
		/* 2628 */return l2;
		/*      */}

	/*      */
	/*      */long buffer2Value(byte paramByte,
			ByteArrayInputStream paramByteArrayInputStream)
	/*      */throws SQLException, IOException
	/*      */{
		/* 2653 */int j = 0;
		/*      */
		/* 2655 */long l = 0L;
		/* 2656 */int k = 0;
		/*      */
		/* 2659 */if ((this.types.rep[paramByte] & 0x1) > 0)
		/*      */{
			/* 2661 */j = paramByteArrayInputStream.read();
			/*      */
			/* 2664 */if ((j & 0x80) > 0)
			/*      */{
				/* 2666 */j &= 127;
				/* 2667 */k = 1;
				/*      */}
			/*      */
			/* 2675 */if (j < 0)
			/*      */{
				/* 2680 */DatabaseError.throwSqlException(410);
				/*      */}
			/*      */
			/* 2686 */if (j == 0)
			/*      */{
				/* 2692 */return 0L;
				/*      */}
			/*      */
			/* 2696 */if (((paramByte == 1) && (j > 2))
					|| ((paramByte == 2) && (j > 4)))
			/*      */{
				/* 2702 */DatabaseError.throwSqlException(412);
				/*      */}
			/*      */
			/*      */}
		/* 2708 */else if (paramByte == 1) {
			/* 2709 */j = 2;
			/* 2710 */} else if (paramByte == 2) {
			/* 2711 */j = 4;
			/*      */}
		/*      */
		/* 2719 */byte[] arrayOfByte = new byte[j];
		/*      */
		/* 2726 */if (paramByteArrayInputStream.read(arrayOfByte) < 0)
		/*      */{
			/* 2731 */DatabaseError.throwSqlException(410);
			/*      */}
		/*      */
		/* 2734 */for (int m = 0; m < arrayOfByte.length; m++)
		/*      */{
			/*      */int i;
			/* 2742 */if ((this.types.rep[paramByte] & 0x2) > 0)
				/* 2743 */i = (short) (arrayOfByte[(arrayOfByte.length - 1 - m)] & 0xFF);
			/*      */else {
				/* 2745 */i = (short) (arrayOfByte[m] & 0xFF);
				/*      */}
			/* 2747 */l |= i << 8 * (arrayOfByte.length - 1 - m);
			/*      */}
		/*      */
		/* 2754 */l &= -1L;
		/*      */
		/* 2756 */if (k != 0) {
			/* 2757 */l = -l;
			/*      */}
		/*      */
		/* 2762 */return l;
		/*      */}
	/*      */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CMAREngine
 * JD-Core Version: 0.6.0
 */