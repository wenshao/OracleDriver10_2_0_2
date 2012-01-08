package oracle.security.o3logon;

import java.security.SecureRandom;

public final class O3LoginProtocolHelper
{
  private static int a;
  private final byte[] b;
  private static long c = System.currentTimeMillis();
  private static final boolean d = false;
  private static C1 e;
  private final byte[] f = new byte[8];

  public byte[] getChallenge(byte[] paramArrayOfByte)
  {
    SecureRandom localSecureRandom = null;
    localSecureRandom = new SecureRandom(paramArrayOfByte);
    c += System.currentTimeMillis();
    localSecureRandom.setSeed(c);
    localSecureRandom.setSeed(this.b);
    localSecureRandom.nextBytes(this.f);
    C0 localC0 = new C0();
    byte[] arrayOfByte = localC0.b(this.b, this.f, this.f.length);
    return arrayOfByte;
  }

  public static byte[] getResponse(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    if (e == null)
      e = new C1();
    byte[] arrayOfByte1 = e.g(paramString1, paramString2);
    C0 localC0 = new C0();
    byte[] arrayOfByte3 = localC0.h(arrayOfByte1, paramArrayOfByte);
    byte[] arrayOfByte4 = paramString2.getBytes();
    int i;
    if (arrayOfByte4.length % 8 > 0)
      i = (byte)(8 - arrayOfByte4.length % 8);
    else
      i = 0;
    byte[] arrayOfByte2 = new byte[arrayOfByte4.length + i];
    System.arraycopy(arrayOfByte4, 0, arrayOfByte2, 0, arrayOfByte4.length);
    byte[] arrayOfByte5 = localC0.b(arrayOfByte3, arrayOfByte2, arrayOfByte2.length);
    byte[] arrayOfByte6 = new byte[arrayOfByte5.length + 1];
    System.arraycopy(arrayOfByte5, 0, arrayOfByte6, 0, arrayOfByte5.length);
    arrayOfByte6[(arrayOfByte6.length - 1)] = i;
    return arrayOfByte6;
  }

  public byte[] getVerifier(String paramString1, String paramString2)
  {
    if (e == null)
      e = new C1();
    return e.g(paramString1, paramString2);
  }

  public String getPassword(byte[] paramArrayOfByte)
  {
    int i = -1;
    C0 localC0 = new C0();
    int j = paramArrayOfByte[(paramArrayOfByte.length - 1)];
    byte[] arrayOfByte1 = new byte[paramArrayOfByte.length - 1];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = null;
    try
    {
      arrayOfByte2 = localC0.h(this.f, arrayOfByte1);
    }
    catch (Exception localException)
    {
      return null;
    }
    byte[] arrayOfByte3 = new byte[arrayOfByte2.length - j];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte3.length);
    String str = new String(arrayOfByte3).toUpperCase();
    return str;
  }

  public boolean authenticate(String paramString1, String paramString2)
  {
    try
    {
      Thread.sleep(a * 1000);
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    if (e == null)
      e = new C1();
    byte[] arrayOfByte = e.g(paramString1, paramString2);
    if (this.b.length != arrayOfByte.length)
    {
      a += 1;
      return false;
    }
    for (int i = 0; i < arrayOfByte.length; i++)
    {
      if (arrayOfByte[i] == this.b[i])
        continue;
      a += 1;
      return false;
    }
    return true;
  }

  public O3LoginProtocolHelper()
  {
    this.b = null;
  }

  static
  {
    a = 0;
  }

  public O3LoginProtocolHelper(byte[] paramArrayOfByte)
  {
    this.b = paramArrayOfByte;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.security.o3logon.O3LoginProtocolHelper
 * JD-Core Version:    0.6.0
 */