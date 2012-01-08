package oracle.net.aso;

class C12
{
  private int c;
  private final C04 d;
  private int e;
  private byte[] f;
  private final char g = 'Ā';

  public byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = this.c;
    int j = this.e;
    for (int k = 0; k < paramInt; k++)
    {
      i = i + 1 & 0xFF;
      int m = this.f[i];
      j = j + m & 0xFF;
      int n = this.f[j];
      this.f[i] = (byte)(n & 0xFF);
      this.f[j] = (byte)(m & 0xFF);
      int i1 = m + n & 0xFF;
      arrayOfByte[k] = (byte)((paramArrayOfByte[k] ^ this.f[i1]) & 0xFF);
    }
    this.c = i;
    this.e = j;
    return arrayOfByte;
  }

  public void b(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < 256; i++)
      this.f[i] = (byte)i;
    this.c = (this.e = 0);
    i = 0;
    int j = 0;
    for (int k = 0; i < 256; k++)
    {
      int m = this.f[i];
      if (k == paramInt)
        k = 0;
      j = j + m + paramArrayOfByte[k] & 0xFF;
      this.f[i] = this.f[j];
      this.f[j] = m;
      i++;
    }
  }

  public C12(C04 paramC04)
  {
    this.d = paramC04;
    this.g = 'Ā';
    this.f = new byte[256];
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C12
 * JD-Core Version:    0.6.0
 */