package oracle.net.aso;

public class C11 extends C01
  implements C00
{
  public void c(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
      throw new C02(102);
    if (paramArrayOfByte1.length < 16)
      throw new C02(102);
    System.arraycopy(paramArrayOfByte1, 0, this.z, 0, 8);
    System.arraycopy(paramArrayOfByte1, 8, this.B, 0, 8);
    System.arraycopy(this.z, 0, this.E, 0, 8);
    this.u = true;
  }

  public void a()
  {
  }

  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws C02
  {
    this.u = true;
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null))
    {
      if (this.z == null)
        throw new C02(102);
      return;
    }
    if (paramArrayOfByte1.length < 16)
      throw new C02(102);
    System.arraycopy(paramArrayOfByte1, 0, this.z, 0, 8);
    System.arraycopy(paramArrayOfByte1, 8, this.B, 0, 8);
    System.arraycopy(this.z, 0, this.E, 0, 8);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C11
 * JD-Core Version:    0.6.0
 */