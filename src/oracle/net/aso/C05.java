package oracle.net.aso;

import java.util.Random;

public class C05
{
  boolean e = false;
  int f = 4181;
  int g = 104322;
  int h = 11113;
  int i = 971;

  public short a()
  {
    if (!this.e)
    {
      b();
    }
    else
    {
      this.f += 7;
      this.h += 1907;
      this.g += 73939;
      if (this.f >= 9973)
        this.f -= 9871;
      if (this.h >= 99991)
        this.h -= 89989;
      if (this.g >= 224729)
        this.g -= 96233;
      this.f = (this.f * this.i + this.h + this.g);
    }
    return (short)(this.f >> 16 ^ this.f & 0xFFFF);
  }

  private void b()
  {
    Random localRandom = new Random();
    this.f = localRandom.nextInt();
    this.e = true;
  }

  public void c(byte[] paramArrayOfByte, int paramInt)
  {
    for (int j = 0; j < paramInt; j++)
      paramArrayOfByte[j] = d();
  }

  public byte d()
  {
    return (byte)a();
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C05
 * JD-Core Version:    0.6.0
 */