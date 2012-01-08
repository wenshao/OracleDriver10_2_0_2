package oracle.net.aso;

public class MD5
  implements C03
{
  private byte[] p;
  private static final char[] q = { '', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000', '\000' };
  private static final int r = 90;
  private SD5 s = new SD5();
  private static final char t = 'ÿ';
  private long[] u = new long[2];
  private byte[] v;
  private SD5 w = new SD5();
  private long[] x = new long[4];
  private static final int y = 5;
  private char[] z = new char[64];
  private SD5 A = new SD5();
  private boolean B;
  private char[] C = new char[16];
  private static final int D = 16;
  private static final int E = 180;

  private void a(char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramArrayOfChar[i] = (char)paramArrayOfByte[i];
  }

  private long b(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong3 | paramLong2 & (paramLong3 ^ 0xFFFFFFFF);
  }

  private long c(long paramLong, int paramInt)
  {
    long l1 = -1L;
    long l2 = paramLong << paramInt;
    long l3 = l1 << paramInt;
    long l4 = (l3 ^ 0xFFFFFFFF) & l1;
    long l5 = paramLong >>> 32 - paramInt & l4;
    return l5 | l2;
  }

  private void d(char[] paramArrayOfChar, long[] paramArrayOfLong, int paramInt)
  {
    int i = 0;
    for (int j = 0; i < paramInt; j += 4)
    {
      paramArrayOfChar[j] = (char)(int)(paramArrayOfLong[i] & 0xFF);
      paramArrayOfChar[(j + 1)] = (char)(int)(paramArrayOfLong[i] >> 8 & 0xFF);
      paramArrayOfChar[(j + 2)] = (char)(int)(paramArrayOfLong[i] >> 16 & 0xFF);
      paramArrayOfChar[(j + 3)] = (char)(int)(paramArrayOfLong[i] >> 24 & 0xFF);
      i++;
    }
  }

  private void e(long[] paramArrayOfLong, char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j += 4)
    {
      paramArrayOfLong[i] = (paramArrayOfChar[j] & 0xFF | paramArrayOfChar[(j + 1)] << '\b' & 0xFF00 | paramArrayOfChar[(j + 2)] << '\020' & 0xFF0000 | paramArrayOfChar[(j + 3)] << '\030' & 0xFF000000);
      i++;
    }
  }

  public void initKeyedMD5(boolean paramBoolean)
  {
    this.B = paramBoolean;
    takeSessionKey(this.v, this.p);
  }

  private long f(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    paramLong1 += k(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6;
    paramLong1 = c(paramLong1, paramInt);
    paramLong1 += paramLong2;
    return paramLong1;
  }

  public void init(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.B = true;
    n();
    if ((paramArrayOfByte1 != null) || (paramArrayOfByte2 != null))
    {
      this.v = paramArrayOfByte1;
      this.p = paramArrayOfByte2;
      initKeyedMD5(true);
    }
  }

  public boolean compare(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    MD5 localMD5 = new MD5();
    byte[] arrayOfByte1 = new byte[16];
    byte[] arrayOfByte2 = new byte[16];
    this.s.SD5ed(arrayOfByte2, arrayOfByte1, 16);
    localMD5.n();
    char[] arrayOfChar1 = new char[paramArrayOfByte1.length];
    a(arrayOfChar1, paramArrayOfByte1, paramArrayOfByte1.length);
    localMD5.MD5Update(arrayOfChar1, paramArrayOfByte1.length);
    char[] arrayOfChar2 = new char[arrayOfByte2.length];
    a(arrayOfChar2, arrayOfByte2, arrayOfByte2.length);
    localMD5.MD5Update(arrayOfChar2, 16);
    localMD5.MD5Final();
    for (int i = 0; i < 16; i++)
      if (paramArrayOfByte2[i] != (byte)(localMD5.C[i] & 0xFF))
        return true;
    return false;
  }

  private long g(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    paramLong1 += b(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6;
    paramLong1 = c(paramLong1, paramInt);
    paramLong1 += paramLong2;
    return paramLong1;
  }

  private long h(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 ^ paramLong2 ^ paramLong3;
  }

  public int size()
  {
    return 16;
  }

  private long i(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong1 & paramLong2 | (paramLong1 ^ 0xFFFFFFFF) & paramLong3;
  }

  private void j(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    long l1 = paramArrayOfLong1[0];
    long l2 = paramArrayOfLong1[1];
    long l3 = paramArrayOfLong1[2];
    long l4 = paramArrayOfLong1[3];
    l1 = o(l1, l2, l3, l4, paramArrayOfLong2[0], 7, -680876936L);
    l4 = o(l4, l1, l2, l3, paramArrayOfLong2[1], 12, -389564586L);
    l3 = o(l3, l4, l1, l2, paramArrayOfLong2[2], 17, 606105819L);
    l2 = o(l2, l3, l4, l1, paramArrayOfLong2[3], 22, -1044525330L);
    l1 = o(l1, l2, l3, l4, paramArrayOfLong2[4], 7, -176418897L);
    l4 = o(l4, l1, l2, l3, paramArrayOfLong2[5], 12, 1200080426L);
    l3 = o(l3, l4, l1, l2, paramArrayOfLong2[6], 17, -1473231341L);
    l2 = o(l2, l3, l4, l1, paramArrayOfLong2[7], 22, -45705983L);
    l1 = o(l1, l2, l3, l4, paramArrayOfLong2[8], 7, 1770035416L);
    l4 = o(l4, l1, l2, l3, paramArrayOfLong2[9], 12, -1958414417L);
    l3 = o(l3, l4, l1, l2, paramArrayOfLong2[10], 17, -42063L);
    l2 = o(l2, l3, l4, l1, paramArrayOfLong2[11], 22, -1990404162L);
    l1 = o(l1, l2, l3, l4, paramArrayOfLong2[12], 7, 1804603682L);
    l4 = o(l4, l1, l2, l3, paramArrayOfLong2[13], 12, -40341101L);
    l3 = o(l3, l4, l1, l2, paramArrayOfLong2[14], 17, -1502002290L);
    l2 = o(l2, l3, l4, l1, paramArrayOfLong2[15], 22, 1236535329L);
    l1 = g(l1, l2, l3, l4, paramArrayOfLong2[1], 5, -165796510L);
    l4 = g(l4, l1, l2, l3, paramArrayOfLong2[6], 9, -1069501632L);
    l3 = g(l3, l4, l1, l2, paramArrayOfLong2[11], 14, 643717713L);
    l2 = g(l2, l3, l4, l1, paramArrayOfLong2[0], 20, -373897302L);
    l1 = g(l1, l2, l3, l4, paramArrayOfLong2[5], 5, -701558691L);
    l4 = g(l4, l1, l2, l3, paramArrayOfLong2[10], 9, 38016083L);
    l3 = g(l3, l4, l1, l2, paramArrayOfLong2[15], 14, -660478335L);
    l2 = g(l2, l3, l4, l1, paramArrayOfLong2[4], 20, -405537848L);
    l1 = g(l1, l2, l3, l4, paramArrayOfLong2[9], 5, 568446438L);
    l4 = g(l4, l1, l2, l3, paramArrayOfLong2[14], 9, -1019803690L);
    l3 = g(l3, l4, l1, l2, paramArrayOfLong2[3], 14, -187363961L);
    l2 = g(l2, l3, l4, l1, paramArrayOfLong2[8], 20, 1163531501L);
    l1 = g(l1, l2, l3, l4, paramArrayOfLong2[13], 5, -1444681467L);
    l4 = g(l4, l1, l2, l3, paramArrayOfLong2[2], 9, -51403784L);
    l3 = g(l3, l4, l1, l2, paramArrayOfLong2[7], 14, 1735328473L);
    l2 = g(l2, l3, l4, l1, paramArrayOfLong2[12], 20, -1926607734L);
    l1 = m(l1, l2, l3, l4, paramArrayOfLong2[5], 4, -378558L);
    l4 = m(l4, l1, l2, l3, paramArrayOfLong2[8], 11, -2022574463L);
    l3 = m(l3, l4, l1, l2, paramArrayOfLong2[11], 16, 1839030562L);
    l2 = m(l2, l3, l4, l1, paramArrayOfLong2[14], 23, -35309556L);
    l1 = m(l1, l2, l3, l4, paramArrayOfLong2[1], 4, -1530992060L);
    l4 = m(l4, l1, l2, l3, paramArrayOfLong2[4], 11, 1272893353L);
    l3 = m(l3, l4, l1, l2, paramArrayOfLong2[7], 16, -155497632L);
    l2 = m(l2, l3, l4, l1, paramArrayOfLong2[10], 23, -1094730640L);
    l1 = m(l1, l2, l3, l4, paramArrayOfLong2[13], 4, 681279174L);
    l4 = m(l4, l1, l2, l3, paramArrayOfLong2[0], 11, -358537222L);
    l3 = m(l3, l4, l1, l2, paramArrayOfLong2[3], 16, -722521979L);
    l2 = m(l2, l3, l4, l1, paramArrayOfLong2[6], 23, 76029189L);
    l1 = m(l1, l2, l3, l4, paramArrayOfLong2[9], 4, -640364487L);
    l4 = m(l4, l1, l2, l3, paramArrayOfLong2[12], 11, -421815835L);
    l3 = m(l3, l4, l1, l2, paramArrayOfLong2[15], 16, 530742520L);
    l2 = m(l2, l3, l4, l1, paramArrayOfLong2[2], 23, -995338651L);
    l1 = f(l1, l2, l3, l4, paramArrayOfLong2[0], 6, -198630844L);
    l4 = f(l4, l1, l2, l3, paramArrayOfLong2[7], 10, 1126891415L);
    l3 = f(l3, l4, l1, l2, paramArrayOfLong2[14], 15, -1416354905L);
    l2 = f(l2, l3, l4, l1, paramArrayOfLong2[5], 21, -57434055L);
    l1 = f(l1, l2, l3, l4, paramArrayOfLong2[12], 6, 1700485571L);
    l4 = f(l4, l1, l2, l3, paramArrayOfLong2[3], 10, -1894986606L);
    l3 = f(l3, l4, l1, l2, paramArrayOfLong2[10], 15, -1051523L);
    l2 = f(l2, l3, l4, l1, paramArrayOfLong2[1], 21, -2054922799L);
    l1 = f(l1, l2, l3, l4, paramArrayOfLong2[8], 6, 1873313359L);
    l4 = f(l4, l1, l2, l3, paramArrayOfLong2[15], 10, -30611744L);
    l3 = f(l3, l4, l1, l2, paramArrayOfLong2[6], 15, -1560198380L);
    l2 = f(l2, l3, l4, l1, paramArrayOfLong2[13], 21, 1309151649L);
    l1 = f(l1, l2, l3, l4, paramArrayOfLong2[4], 6, -145523070L);
    l4 = f(l4, l1, l2, l3, paramArrayOfLong2[11], 10, -1120210379L);
    l3 = f(l3, l4, l1, l2, paramArrayOfLong2[2], 15, 718787259L);
    l2 = f(l2, l3, l4, l1, paramArrayOfLong2[9], 21, -343485551L);
    paramArrayOfLong1[0] += l1;
    paramArrayOfLong1[1] += l2;
    paramArrayOfLong1[2] += l3;
    paramArrayOfLong1[3] += l4;
  }

  public void MD5Update(char[] paramArrayOfChar, int paramInt)
  {
    long[] arrayOfLong = new long[16];
    int i = (int)(this.u[0] >>> 3 & 0x3F);
    if (this.u[0] + (paramInt << 3) < this.u[0])
      this.u[1] += 1L;
    this.u[0] += (paramInt << 3);
    this.u[1] += (paramInt >>> 29);
    int j = 0;
    while (paramInt-- > 0)
    {
      this.z[(i++)] = paramArrayOfChar[(j++)];
      if (i != 64)
        continue;
      e(arrayOfLong, this.z, 64);
      j(this.x, arrayOfLong);
      i = 0;
    }
  }

  public byte[] compute(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length < paramInt)
      return null;
    MD5 localMD5 = new MD5();
    byte[] arrayOfByte1 = new byte[16];
    byte[] arrayOfByte2 = new byte[16];
    this.w.SD5ed(arrayOfByte2, arrayOfByte1, 16);
    localMD5.n();
    char[] arrayOfChar1 = new char[arrayOfByte2.length];
    char[] arrayOfChar2 = new char[paramInt];
    a(arrayOfChar2, paramArrayOfByte, paramInt);
    a(arrayOfChar1, arrayOfByte2, 16);
    localMD5.MD5Update(arrayOfChar2, arrayOfChar2.length);
    localMD5.MD5Update(arrayOfChar1, arrayOfChar1.length);
    localMD5.MD5Final();
    l(arrayOfByte2, localMD5.C, localMD5.C.length);
    return arrayOfByte2;
  }

  public void renew()
  {
    byte[] arrayOfByte1 = { 0, 0, 0, 0, 0 };
    byte[] arrayOfByte2 = new byte[6];
    this.A.SD5ed(arrayOfByte2, arrayOfByte1, 5);
    arrayOfByte2[5] = (byte)(this.B ? '´' : 90);
    this.s.SboxInit(arrayOfByte2, 6);
    arrayOfByte2[5] = (byte)(this.B ? 90 : '´');
    this.w.SboxInit(arrayOfByte2, 6);
  }

  public byte[] digest(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length < paramInt)
      return null;
    char[] arrayOfChar = new char[paramInt];
    a(arrayOfChar, paramArrayOfByte, paramInt);
    MD5Update(arrayOfChar, arrayOfChar.length);
    MD5Final();
    byte[] arrayOfByte = new byte[this.C.length];
    l(arrayOfByte, this.C, this.C.length);
    return arrayOfByte;
  }

  private long k(long paramLong1, long paramLong2, long paramLong3)
  {
    return paramLong2 ^ (paramLong1 | paramLong3 ^ 0xFFFFFFFF);
  }

  private void l(byte[] paramArrayOfByte, char[] paramArrayOfChar, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramArrayOfByte[i] = (byte)(paramArrayOfChar[i] & 0xFF);
  }

  private long m(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    paramLong1 += h(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6;
    paramLong1 = c(paramLong1, paramInt);
    paramLong1 += paramLong2;
    return paramLong1;
  }

  private void n()
  {
    this.u[0] = 0L;
    this.u[1] = 0L;
    this.x[0] = 1732584193L;
    this.x[1] = -271733879L;
    this.x[2] = -1732584194L;
    this.x[3] = 271733878L;
  }

  public void MD5Final()
  {
    long[] arrayOfLong = new long[16];
    arrayOfLong[14] = this.u[0];
    arrayOfLong[15] = this.u[1];
    int i = (int)(this.u[0] >>> 3 & 0x3F);
    int j = i < 56 ? 56 - i : 120 - i;
    MD5Update(q, j);
    e(arrayOfLong, this.z, 56);
    j(this.x, arrayOfLong);
    d(this.C, this.x, 4);
  }

  private long o(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, int paramInt, long paramLong6)
  {
    paramLong1 += i(paramLong2, paramLong3, paramLong4) + paramLong5 + paramLong6;
    paramLong1 = c(paramLong1, paramInt);
    paramLong1 += paramLong2;
    return paramLong1;
  }

  public int takeSessionKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = paramArrayOfByte1.length;
    int j = paramArrayOfByte2.length;
    int n = 6 + j;
    int i1 = i - 5;
    byte[] arrayOfByte = new byte[n];
    if (i1 < 0)
      return -1;
    int k = 0;
    int m = 0;
    while (k < n)
    {
      if (i1 < paramArrayOfByte1.length)
        arrayOfByte[k] = paramArrayOfByte1[(i1++)];
      else if (i1++ == paramArrayOfByte1.length)
        arrayOfByte[k] = -1;
      else
        arrayOfByte[k] = paramArrayOfByte2[(m++)];
      k++;
    }
    this.A.SboxInit(arrayOfByte, n);
    renew();
    return 0;
  }

  private class SD5
  {
    private int c;
    private byte[] d = new byte[256];
    private byte[] e = new byte[256];
    private final char f = 'Ā';
    private int g;

    public void SD5ed(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
    {
      for (int i = 0; i < paramInt; i++)
      {
        int j = a();
        paramArrayOfByte1[i] = (byte)((paramArrayOfByte2[i] ^ j) & 0xFF);
      }
    }

    public SD5()
    {
    }

    public void SboxInit(byte[] paramArrayOfByte, int paramInt)
    {
      for (int i = 0; i < 256; i++)
      {
        this.d[i] = (byte)i;
        this.e[i] = paramArrayOfByte[(i % paramInt)];
      }
      this.g = (this.c = 0);
      i = 0;
      int j = 0;
      while (i < 256)
      {
        int k = this.d[i];
        j = j + k + this.e[i] & 0xFF;
        this.d[i] = this.d[j];
        this.d[j] = k;
        i++;
      }
    }

    private byte a()
    {
      int i = this.g;
      int j = this.c;
      i = i + 1 & 0xFF;
      j = j + this.d[i] & 0xFF;
      int k = this.d[i];
      this.d[i] = this.d[j];
      this.d[j] = k;
      int m = this.d[i] + this.d[j] & 0xFF;
      this.g = i;
      this.c = j;
      return this.d[m];
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.MD5
 * JD-Core Version:    0.6.0
 */