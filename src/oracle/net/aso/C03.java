package oracle.net.aso;

public abstract interface C03
{
  public abstract int takeSessionKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public abstract void renew();

  public abstract boolean compare(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public abstract byte[] compute(byte[] paramArrayOfByte, int paramInt);

  public abstract void init(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public abstract int size();
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C03
 * JD-Core Version:    0.6.0
 */