package oracle.net.aso;

public class C08
{
  private static final short[] h = { 40, 41, 56, 128, 256 };
  private int i = 0;
  private int j;
  private static final short[] k;
  private static final byte[] l;
  private char[] m = new char[65];
  private static final short[] n = { 40, 64, 56, 128, 256 };
  private int o;
  private static final byte[] p;
  private char[] q = new char[65];
  private byte[] r = null;
  private short s;
  private byte[] t;
  private static final int u = 65;
  private byte[] v;
  private static final short[] w;
  private static final byte[][] x;
  private int y;
  private static final byte[] z;
  private byte[] A = null;
  private static final byte[][] B;
  private byte[] C = null;
  private int D = 0;
  private short E;
  private byte[] F = null;
  private static final byte[] G;

  static
  {
    k = new short[] { 80, 112, 112, 512, 512 };
    w = new short[] { 300, 512, 512, 512, 512 };
    p = new byte[] { 2, 83, -77, -14, -90, -115, 61, -69, 106, -61, -103, 9, -64, -41, 4, 5, -14, 91, -126, 97, 107, 122, -24, -36, 29, 123, 3, -106, 53, -30, -37, -17, 67, 102, -6, -48, 76, -63 };
    G = new byte[] { 12, 54, -127, -73, 4, 71, 3, -96, 120, 96, 81, 38, -116, -22, -101, -68, -93, 62, 124, 1, -85, 54, -117, 34, 117, -104, 119, 102, 53, -59, -128, -43, 36, -46, 80, 99, -72, -13 };
    z = new byte[] { -126, -104, -34, 73, -34, -9, 9, -27, -32, 13, -80, -96, -91, -100, -87, -14, 61, -10, -58, -89, -23, 74, 68, -93, -31, -121, 46, -11, 76, 31, -95, 122, -33, 92, -14, 117, -127, -19, 81, -61, 38, -18, -117, -31, 4, 3, 30, 103, 80, 83, -75, 124, 75, 69, 111, 21, 74, 23, 86, 11, 90, 21, -107, -91 };
    l = new byte[] { -36, -114, -93, 27, 8, 96, 105, -118, -52, -10, -47, -98, -121, 14, 52, -4, 103, -59, 89, 11, 78, -90, -79, 60, -43, -3, -17, 21, -84, -99, 95, 63, 33, 76, -36, 7, -52, -121, 74, -77, 1, -41, 127, 44, 67, 51, 81, 60, -34, 11, 30, -50, 100, 71, 118, 87, 92, 81, -52, -104, -77, -2, -25, -17 };
    B = new byte[][] { p, z, z, z, z };
    x = new byte[][] { G, l, l, l, l };
  }

  public byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    e(paramArrayOfByte, paramInt);
    return this.t;
  }

  private void b()
  {
    char[] arrayOfChar1 = new char[65];
    char[] arrayOfChar2 = new char[65];
    byte[] arrayOfByte = new byte[''];
    int i1 = (short)(this.E + 7) / 8;
    int i2 = (short)(this.s + 7) / 8;
    this.o = (short)i2;
    this.y = (this.s / 16 + 1);
    this.v = new byte[this.o];
    d(arrayOfByte, i1);
    int tmp81_80 = 0;
    byte[] tmp81_78 = arrayOfByte;
    tmp81_78[tmp81_80] = (byte)(tmp81_78[tmp81_80] & 255 >>> i1 - 8 * this.E);
    C09.d(arrayOfChar1, this.y, this.F, i2);
    C09.d(this.m, this.y, arrayOfByte, i1);
    C09.d(this.q, this.y, this.r, i2);
    C09.k(arrayOfChar2, arrayOfChar1, this.m, this.q, this.y);
    C09.r(this.v, this.o, arrayOfChar2, this.y);
  }

  public C08(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    f(paramArrayOfByte1, paramArrayOfByte2, paramInt);
  }

  private void c(int paramInt)
  {
    for (int i1 = 0; i1 < h.length; i1++)
    {
      if ((paramInt < h[i1]) || (paramInt > n[i1]))
        continue;
      this.E = k[i1];
      this.s = w[i1];
      this.F = new byte[(this.s + 7) / 8];
      this.r = new byte[(this.s + 7) / 8];
      if ((this.i * 8 >= this.s) && (this.D * 8 >= this.s))
      {
        System.arraycopy(this.A, 0, this.F, 0, this.F.length);
        System.arraycopy(this.C, 0, this.r, 0, this.r.length);
        break;
      }
      System.arraycopy(B[i1], 0, this.F, 0, this.F.length);
      System.arraycopy(x[i1], 0, this.r, 0, this.r.length);
      break;
    }
    if ((this.F != null) && (this.r == null));
  }

  C08(int paramInt)
  {
    f(null, null, paramInt);
  }

  public C08(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, short paramShort1, short paramShort2)
  {
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 != null))
    {
      this.F = paramArrayOfByte1;
      this.r = paramArrayOfByte2;
      this.s = paramShort2;
      this.E = paramShort1;
    }
    else
    {
      f(paramArrayOfByte1, paramArrayOfByte2, 40);
    }
  }

  private void d(byte[] paramArrayOfByte, int paramInt)
  {
    new C05().c(paramArrayOfByte, paramInt);
  }

  private void e(byte[] paramArrayOfByte, int paramInt)
  {
    char[] arrayOfChar1 = new char[65];
    char[] arrayOfChar2 = new char[65];
    this.j = this.o;
    this.t = new byte[this.j];
    C09.d(arrayOfChar1, this.y, paramArrayOfByte, paramInt);
    C09.k(arrayOfChar2, arrayOfChar1, this.m, this.q, this.y);
    C09.r(this.t, this.j, arrayOfChar2, this.y);
  }

  private void f(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    this.A = paramArrayOfByte1;
    if (paramArrayOfByte1 != null)
      this.i = paramArrayOfByte1.length;
    else
      this.i = 0;
    this.C = paramArrayOfByte2;
    if (paramArrayOfByte2 != null)
      this.D = paramArrayOfByte2.length;
    else
      this.D = 0;
    c(paramInt);
  }

  public byte[] g()
  {
    b();
    return this.v;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C08
 * JD-Core Version:    0.6.0
 */