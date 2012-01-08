package oracle.net.aso;

public class C04
  implements C00
{
  private int d = 40;
  private static final int e = 170;
  private boolean f = true;
  private static final byte g = 123;
  private C12 h;
  private C12 i;
  private static final int j = 85;
  private C12 k;

  public int b()
  {
    return 1;
  }

  private void a()
  {
    byte[] arrayOfByte1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32 };
    int m = this.d / 8;
    byte[] arrayOfByte2 = this.k.a(arrayOfByte1, m);
    if (this.f)
    {
      int tmp229_228 = (m - 1);
      byte[] tmp229_225 = arrayOfByte2;
      tmp229_225[tmp229_228] = (byte)(tmp229_225[tmp229_228] ^ 0xAA);
    }
    this.i.b(arrayOfByte2, m);
    int tmp250_249 = (m - 1);
    byte[] tmp250_246 = arrayOfByte2;
    tmp250_246[tmp250_249] = (byte)(tmp250_246[tmp250_249] ^ 0xAA);
    this.h.b(arrayOfByte2, m);
  }

  public C04(boolean paramBoolean, int paramInt)
    throws C02
  {
    switch (paramInt)
    {
    case 40:
    case 56:
    case 128:
    case 256:
      this.d = paramInt;
      break;
    default:
      throw new C02(100);
    }
    this.f = paramBoolean;
  }

  public byte[] b(byte[] paramArrayOfByte, int paramInt)
    throws C02
  {
    if (paramInt > paramArrayOfByte.length)
      throw new C02(104);
    byte[] arrayOfByte1 = this.h.a(paramArrayOfByte, paramInt);
    if (this.f)
    {
      byte[] arrayOfByte2 = new byte[paramInt + 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramInt);
      arrayOfByte2[paramInt] = 0;
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }

  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
    {
      a();
      return;
    }
    int m = this.d / 8;
    if (paramArrayOfByte1.length < m)
      throw new C02(102);
    int n = 0;
    if (paramArrayOfByte2 != null)
      n = paramArrayOfByte2.length;
    byte[] arrayOfByte = new byte[m + 1 + n];
    System.arraycopy(paramArrayOfByte1, paramArrayOfByte1.length - m, arrayOfByte, 0, m);
    arrayOfByte[m] = 123;
    if (paramArrayOfByte2 != null)
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, m + 1, paramArrayOfByte2.length);
    this.k.b(arrayOfByte, arrayOfByte.length);
    a();
  }

  public void c(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    this.k = new C12(this);
    this.h = new C12(this);
    this.i = new C12(this);
    a(paramArrayOfByte1, paramArrayOfByte2);
  }

  public C04()
  {
  }

  public byte[] d(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = this.h.a(paramArrayOfByte, paramArrayOfByte.length);
    if (this.f)
    {
      byte[] arrayOfByte2 = new byte[paramArrayOfByte.length + 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramArrayOfByte.length);
      arrayOfByte2[paramArrayOfByte.length] = 0;
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }

  public byte[] c(byte[] paramArrayOfByte, int paramInt)
    throws C02
  {
    if (paramInt > paramArrayOfByte.length)
      throw new C02(104);
    if (this.f)
    {
      arrayOfByte1 = this.i.a(paramArrayOfByte, paramInt - 1);
      byte[] arrayOfByte2 = new byte[paramInt - 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramInt - 1);
      return arrayOfByte2;
    }
    byte[] arrayOfByte1 = this.i.a(paramArrayOfByte, paramInt);
    return arrayOfByte1;
  }

  public C04(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    switch (paramInt)
    {
    case 40:
    case 56:
    case 128:
    case 256:
      this.d = paramInt;
      break;
    default:
      throw new C02(100);
    }
    c(paramArrayOfByte1, paramArrayOfByte2);
  }

  public byte[] e(byte[] paramArrayOfByte)
  {
    if (this.f)
    {
      arrayOfByte1 = this.i.a(paramArrayOfByte, paramArrayOfByte.length - 1);
      byte[] arrayOfByte2 = new byte[paramArrayOfByte.length - 1];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, paramArrayOfByte.length - 1);
      return arrayOfByte2;
    }
    byte[] arrayOfByte1 = this.i.a(paramArrayOfByte, paramArrayOfByte.length);
    return arrayOfByte1;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C04
 * JD-Core Version:    0.6.0
 */