package oracle.net.aso;

public class C10
  implements C00
{
  private static final int[] v;
  private static final short[] w;
  protected boolean x = true;
  private static final int[] y;
  protected static final int z = 3;
  protected boolean A = false;
  private byte[] B = new byte[8];
  private static final int[] C;
  private static final byte[] D;
  private int[] E = null;
  protected static final byte F = 0;
  protected static final byte G = 1;
  private static final byte[] H;
  private static final int[] I;
  private int[] J = null;
  protected static final int K = 1;
  protected static final int L = 2;
  private static final byte[] M;
  private static final byte[] N = { 1, 35, 69, 103, -119, -85, -51, -17 };
  private static final int[] O;
  private static final int[] P;
  private static final int[] Q;
  protected static final int R = 8;
  private static final int[] S;
  private static final int[] T;
  protected byte[] U = new byte[8];

  protected byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (byte)(paramInt % 8 == 0 ? 0 : 8 - paramInt % 8);
    int j = paramInt + i;
    byte[] arrayOfByte1 = new byte[j + 1];
    if (this.A == true)
      e(this.U, 0);
    for (int k = 0; k < paramInt; k += 8)
    {
      byte[] arrayOfByte2 = new byte[8];
      if (k <= paramInt - 8)
        System.arraycopy(paramArrayOfByte, k, arrayOfByte2, 0, 8);
      else
        System.arraycopy(paramArrayOfByte, k, arrayOfByte2, 0, paramInt & 0x7);
      byte[] arrayOfByte3 = m(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte1, k, 8);
    }
    arrayOfByte1[j] = (byte)(i + 1);
    return arrayOfByte1;
  }

  public byte[] b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if (this.x == true)
      return p(paramArrayOfByte2, paramInt);
    return g(this.U, paramArrayOfByte2, paramInt);
  }

  protected void c(byte[] paramArrayOfByte)
  {
    this.E = t(paramArrayOfByte, 0);
  }

  public byte[] d(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if (this.x == true)
      return p(paramArrayOfByte2, paramInt);
    return g(this.U, paramArrayOfByte2, paramInt);
  }

  public void c(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    if (paramArrayOfByte1.length < 8)
      throw new C02(102);
    System.arraycopy(paramArrayOfByte1, 0, this.U, 0, 8);
    this.A = true;
  }

  protected byte[] e(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if (this.A == true)
      e(paramArrayOfByte1, 0);
    byte[] arrayOfByte1 = new byte[8];
    int i = (byte)(paramInt % 8 == 0 ? 0 : 8 - paramInt % 8);
    byte[] arrayOfByte2 = new byte[paramInt + i + 1];
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte2, 0, paramInt);
    int j = 0;
    for (j = paramInt; j < arrayOfByte2.length; j++)
      arrayOfByte2[j] = 0;
    arrayOfByte2[(j - 1)] = (byte)(i + 1);
    for (j = 0; j < arrayOfByte2.length / 8; j++)
    {
      for (int k = 0; k < 8; k++)
        arrayOfByte1[k] = arrayOfByte2[(j * 8 + k)];
      f(arrayOfByte1);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j * 8, 8);
    }
    return arrayOfByte2;
  }

  static
  {
    w = new short[] { 128, 64, 32, 16, 8, 4, 2, 1 };
    T = new int[] { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
    M = new byte[] { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
    D = new byte[] { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
    H = new byte[] { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
    Q = new int[] { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
    O = new int[] { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, -2147483648, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, -2147483648, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, -2147483648, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, -2147483648, -2146435040, -2146402272, 1081344 };
    I = new int[] { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
    C = new int[] { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
    y = new int[] { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
    v = new int[] { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
    S = new int[] { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
    P = new int[] { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };
  }

  public byte[] c(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.x == true)
      return a(paramArrayOfByte, paramInt);
    return e(this.U, paramArrayOfByte, paramInt);
  }

  private void f(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[2];
    r(paramArrayOfByte, arrayOfInt);
    h(arrayOfInt, this.E);
    j(arrayOfInt, paramArrayOfByte);
  }

  protected byte[] g(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if (this.A == true)
      e(paramArrayOfByte1, 1);
    byte[] arrayOfByte1 = new byte[8];
    int i = paramArrayOfByte2[(paramInt - 1)];
    byte[] arrayOfByte2 = new byte[paramInt];
    for (int j = 0; j < paramInt / 8; j++)
    {
      for (int k = 0; k < 8; k++)
        arrayOfByte1[k] = paramArrayOfByte2[(j * 8 + k)];
      o(arrayOfByte1);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j * 8, 8);
    }
    byte[] arrayOfByte3 = new byte[arrayOfByte2.length - i];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte3.length);
    return arrayOfByte3;
  }

  protected void h(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i1 = 0;
    int i2 = 0;
    int m = paramArrayOfInt1[0];
    int k = paramArrayOfInt1[1];
    int j = (m >>> 4 ^ k) & 0xF0F0F0F;
    k ^= j;
    m ^= j << 4;
    j = (m >>> 16 ^ k) & 0xFFFF;
    k ^= j;
    m ^= j << 16;
    j = (k >>> 2 ^ m) & 0x33333333;
    m ^= j;
    k ^= j << 2;
    j = (k >>> 8 ^ m) & 0xFF00FF;
    m ^= j;
    k ^= j << 8;
    k = (k << 1 | k >>> 31 & 0x1) & 0xFFFFFFFF;
    j = (m ^ k) & 0xAAAAAAAA;
    m ^= j;
    k ^= j;
    m = (m << 1 | m >>> 31 & 0x1) & 0xFFFFFFFF;
    for (int n = 0; n < 8; n++)
    {
      j = k << 28 | k >>> 4;
      long l = 0L;
      l = paramArrayOfInt2[i2] | 0x0;
      j ^= paramArrayOfInt2[i2];
      i2++;
      int i = S[(j & 0x3F)];
      i |= y[(j >>> 8 & 0x3F)];
      i |= I[(j >>> 16 & 0x3F)];
      i |= Q[(j >>> 24 & 0x3F)];
      j = k ^ paramArrayOfInt2[i2];
      i2++;
      i |= P[(j & 0x3F)];
      i |= v[(j >>> 8 & 0x3F)];
      i |= C[(j >>> 16 & 0x3F)];
      i |= O[(j >>> 24 & 0x3F)];
      m ^= i;
      j = m << 28 | m >>> 4;
      j ^= paramArrayOfInt2[i2];
      i2++;
      i = S[(j & 0x3F)];
      i |= y[(j >>> 8 & 0x3F)];
      i |= I[(j >>> 16 & 0x3F)];
      i |= Q[(j >>> 24 & 0x3F)];
      j = m ^ paramArrayOfInt2[i2];
      i2++;
      i |= P[(j & 0x3F)];
      i |= v[(j >>> 8 & 0x3F)];
      i |= C[(j >>> 16 & 0x3F)];
      i |= O[(j >>> 24 & 0x3F)];
      k ^= i;
    }
    k = k << 31 | k >>> 1;
    j = (m ^ k) & 0xAAAAAAAA;
    m ^= j;
    k ^= j;
    m = m << 31 | m >>> 1;
    j = (m >>> 8 ^ k) & 0xFF00FF;
    k ^= j;
    m ^= j << 8;
    j = (m >>> 2 ^ k) & 0x33333333;
    k ^= j;
    m ^= j << 2;
    j = (k >>> 16 ^ m) & 0xFFFF;
    m ^= j;
    k ^= j << 16;
    j = (k >>> 4 ^ m) & 0xF0F0F0F;
    m ^= j;
    k ^= j << 4;
    paramArrayOfInt1[0] = k;
    paramArrayOfInt1[1] = m;
  }

  private void i(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int[] arrayOfInt = new int[2];
    r(paramArrayOfByte2, arrayOfInt);
    h(arrayOfInt, t(paramArrayOfByte1, 0));
    j(arrayOfInt, paramArrayOfByte2);
  }

  public byte[] d(byte[] paramArrayOfByte)
  {
    if (this.x == true)
      return a(paramArrayOfByte, paramArrayOfByte.length);
    return e(this.U, paramArrayOfByte, paramArrayOfByte.length);
  }

  public byte[] e(byte[] paramArrayOfByte)
  {
    if (this.x == true)
      return p(paramArrayOfByte, paramArrayOfByte.length);
    return g(this.U, paramArrayOfByte, paramArrayOfByte.length);
  }

  protected void j(int[] paramArrayOfInt, byte[] paramArrayOfByte)
  {
    int i = 0;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[0] >> 24 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[0] >> 16 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[0] >> 8 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[0] & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[1] >> 24 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[1] >> 16 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[1] >> 8 & 0xFF);
    i++;
    paramArrayOfByte[i] = (byte)(paramArrayOfInt[1] & 0xFF);
  }

  protected void k(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt)
  {
    int i;
    int j;
    if (paramInt == 1)
    {
      i = 8;
      for (j = 0; i-- > 0; j++)
        paramArrayOfByte1[j] = (byte)(paramArrayOfByte2[j] & paramArrayOfByte3[j]);
    }
    if (paramInt == 2)
    {
      i = 8;
      for (j = 0; i-- > 0; j++)
        paramArrayOfByte1[j] = (byte)(paramArrayOfByte2[j] ^ paramArrayOfByte3[j]);
    }
    if (paramInt == 3)
    {
      i = 8;
      for (j = 0; i-- > 0; j++)
        paramArrayOfByte1[j] = paramArrayOfByte2[j];
    }
  }

  private int[] l(int[] paramArrayOfInt)
  {
    int[] arrayOfInt3 = new int[32];
    int[] arrayOfInt1 = arrayOfInt3;
    int[] arrayOfInt2 = paramArrayOfInt;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    while (i < 16)
    {
      j = k++;
      arrayOfInt1[m] = ((arrayOfInt2[j] & 0xFC0000) << 6);
      arrayOfInt1[m] |= (arrayOfInt2[j] & 0xFC0) << 10;
      arrayOfInt1[m] |= (paramArrayOfInt[k] & 0xFC0000) >> 10;
      arrayOfInt1[m] |= (paramArrayOfInt[k] & 0xFC0) >> 6;
      m++;
      arrayOfInt1[m] = ((arrayOfInt2[j] & 0x3F000) << 12);
      arrayOfInt1[m] |= (arrayOfInt2[j] & 0x3F) << 16;
      arrayOfInt1[m] |= (paramArrayOfInt[k] & 0x3F000) >> 4;
      arrayOfInt1[m] |= paramArrayOfInt[k] & 0x3F;
      m++;
      i++;
      k++;
    }
    return arrayOfInt1;
  }

  private byte[] m(byte[] paramArrayOfByte, byte paramByte)
  {
    byte[] arrayOfByte1 = new byte[8];
    if (paramByte == 0)
    {
      k(arrayOfByte1, this.B, paramArrayOfByte, 2);
      f(arrayOfByte1);
      k(this.B, arrayOfByte1, null, 3);
    }
    else
    {
      byte[] arrayOfByte2 = new byte[8];
      k(arrayOfByte2, paramArrayOfByte, null, 3);
      o(paramArrayOfByte);
      k(arrayOfByte1, this.B, paramArrayOfByte, 2);
      k(this.B, arrayOfByte2, null, 3);
    }
    return arrayOfByte1;
  }

  public int b()
  {
    return 8;
  }

  protected void e(byte[] paramArrayOfByte, byte paramByte)
  {
    System.arraycopy(paramArrayOfByte, 0, this.U, 0, 8);
    this.E = t(this.U, paramByte);
    this.J = t(this.U, paramByte);
    if (this.x)
      k(this.B, N, null, 3);
    this.A = false;
  }

  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    this.A = true;
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
    {
      if (this.U == null)
        throw new C02(102);
      return;
    }
    if (paramArrayOfByte1.length < 8)
      throw new C02(102);
    System.arraycopy(paramArrayOfByte1, 0, this.U, 0, 8);
  }

  public void n()
  {
  }

  private void o(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[2];
    r(paramArrayOfByte, arrayOfInt);
    h(arrayOfInt, this.J);
    j(arrayOfInt, paramArrayOfByte);
  }

  protected byte[] p(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[(paramInt - 1)];
    if ((i < 0) || (i > 8))
      return null;
    int j = paramInt - i;
    byte[] arrayOfByte1 = new byte[paramInt - 1];
    paramInt--;
    if (this.A == true)
      e(this.U, 1);
    for (int k = 0; k < paramInt; k += 8)
    {
      byte[] arrayOfByte3 = new byte[8];
      System.arraycopy(paramArrayOfByte, k, arrayOfByte3, 0, 8);
      byte[] arrayOfByte4 = m(arrayOfByte3, 1);
      System.arraycopy(arrayOfByte4, 0, arrayOfByte1, k, 8);
    }
    byte[] arrayOfByte2 = new byte[j];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, j);
    return arrayOfByte2;
  }

  protected void q(byte[] paramArrayOfByte)
  {
    this.J = t(paramArrayOfByte, 1);
  }

  protected void r(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    int i = 0;
    paramArrayOfInt[0] = ((paramArrayOfByte[i] & 0xFF) << 24);
    i++;
    paramArrayOfInt[0] |= (paramArrayOfByte[i] & 0xFF) << 16;
    i++;
    paramArrayOfInt[0] |= (paramArrayOfByte[i] & 0xFF) << 8;
    i++;
    paramArrayOfInt[0] |= paramArrayOfByte[i] & 0xFF;
    i++;
    paramArrayOfInt[1] = ((paramArrayOfByte[i] & 0xFF) << 24);
    i++;
    paramArrayOfInt[1] |= (paramArrayOfByte[i] & 0xFF) << 16;
    i++;
    paramArrayOfInt[1] |= (paramArrayOfByte[i] & 0xFF) << 8;
    i++;
    paramArrayOfInt[1] |= paramArrayOfByte[i] & 0xFF;
  }

  private void s(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int[] arrayOfInt = new int[2];
    r(paramArrayOfByte2, arrayOfInt);
    h(arrayOfInt, t(paramArrayOfByte1, 1));
    j(arrayOfInt, paramArrayOfByte2);
  }

  protected int[] t(byte[] paramArrayOfByte, byte paramByte)
  {
    byte[] arrayOfByte1 = new byte[56];
    byte[] arrayOfByte2 = new byte[56];
    int[] arrayOfInt = new int[32];
    int k;
    int m;
    for (int j = 0; j < 56; j++)
    {
      k = M[j];
      m = k & 0x7;
      arrayOfByte1[j] = (byte)((paramArrayOfByte[(k >> 3)] & w[m]) != 0 ? 1 : 0);
    }
    for (int i = 0; i < 16; i++)
    {
      if (paramByte == 1)
        m = 15 - i << 1;
      else
        m = i << 1;
      int n = m + 1;
      int tmp120_119 = 0;
      arrayOfInt[n] = tmp120_119;
      arrayOfInt[m] = tmp120_119;
      for (j = 0; j < 28; j++)
      {
        k = j + D[i];
        if (k < 28)
          arrayOfByte2[j] = arrayOfByte1[k];
        else
          arrayOfByte2[j] = arrayOfByte1[(k - 28)];
      }
      for (j = 28; j < 56; j++)
      {
        k = j + D[i];
        if (k < 56)
          arrayOfByte2[j] = arrayOfByte1[k];
        else
          arrayOfByte2[j] = arrayOfByte1[(k - 28)];
      }
      for (j = 0; j < 24; j++)
      {
        if (arrayOfByte2[H[j]] != 0)
          arrayOfInt[m] |= T[j];
        if (arrayOfByte2[H[(j + 24)]] == 0)
          continue;
        arrayOfInt[n] |= T[j];
      }
    }
    return l(arrayOfInt);
  }

  public byte[] u(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.x == true)
      return p(paramArrayOfByte, paramInt);
    return g(this.U, paramArrayOfByte, paramInt);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C10
 * JD-Core Version:    0.6.0
 */