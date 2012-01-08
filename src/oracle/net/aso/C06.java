package oracle.net.aso;

public class C06 extends C10
  implements C00
{
  private static final byte[] f;
  private static final byte[] g;
  private static final byte[] h = { -2, -2, -2, -2, -2, -2, -2, -2 };
  private static final byte[] i;

  public int b()
  {
    return 8;
  }

  public void a()
  {
  }

  static
  {
    g = new byte[] { 88, -46, 26, -119, 7, 0, -59, -68 };
    i = new byte[] { 103, 98, -82, -38, 116, -21, -92, -87 };
    f = new byte[] { 14, -2, 14, -2, 14, -2, 14, -2 };
  }

  public void b(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[8];
    byte[] arrayOfByte3 = new byte[8];
    int[] arrayOfInt = new int[32];
    k(arrayOfByte1, paramArrayOfByte, h, 1);
    arrayOfInt = t(i, 0);
    d(arrayOfInt, arrayOfByte1, arrayOfByte3);
    arrayOfInt = t(g, 0);
    d(arrayOfInt, arrayOfByte1, arrayOfByte2);
    k(arrayOfByte1, arrayOfByte3, arrayOfByte2, 2);
    k(paramArrayOfByte, arrayOfByte1, f, 1);
  }

  public byte[] c(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.A)
      e(this.U, 0);
    return super.c(paramArrayOfByte, paramInt);
  }

  private void d(int[] paramArrayOfInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int[] arrayOfInt = new int[2];
    r(paramArrayOfByte1, arrayOfInt);
    h(arrayOfInt, paramArrayOfInt);
    j(arrayOfInt, paramArrayOfByte2);
  }

  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    super.a(paramArrayOfByte1, paramArrayOfByte2);
  }

  public void c(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    super.c(paramArrayOfByte1, paramArrayOfByte2);
  }

  public void e(byte[] paramArrayOfByte, byte paramByte)
  {
    System.arraycopy(paramArrayOfByte, 0, this.U, 0, 8);
    b(this.U);
    super.e(this.U, paramByte);
  }

  public byte[] e(byte[] paramArrayOfByte)
  {
    if (this.A)
      e(this.U, 1);
    return super.e(paramArrayOfByte);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C06
 * JD-Core Version:    0.6.0
 */