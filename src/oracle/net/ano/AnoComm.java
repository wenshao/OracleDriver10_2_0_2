package oracle.net.ano;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import oracle.net.ns.NetException;
import oracle.net.ns.SessionAtts;

public class AnoComm
  implements AnoServices
{
  private static final boolean h = false;
  private InputStream i;
  private OutputStream j;

  protected int readUB2()
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[2];
    int k = (int)g(arrayOfByte);
    return k & 0xFFFF;
  }

  public AnoComm(SessionAtts paramSessionAtts)
  {
    this.j = paramSessionAtts.getOutputStream();
    this.i = paramSessionAtts.getInputStream();
  }

  protected void sendUB2(int paramInt)
    throws IOException
  {
    sendPktHeader(2, 3);
    writeUB2(paramInt);
  }

  protected void sendString(String paramString)
    throws IOException
  {
    sendPktHeader(paramString.length(), 0);
    this.j.write(paramString.getBytes());
  }

  protected short readUB1()
    throws NetException, IOException
  {
    int k = 0;
    try
    {
      if ((k = (short)this.i.read()) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    return k;
  }

  protected byte[] receiveRaw()
    throws NetException, IOException
  {
    int k = f(1);
    return c(k);
  }

  protected long receiveVersion()
    throws NetException, IOException
  {
    f(5);
    return readUB4();
  }

  protected void sendStatus(int paramInt)
    throws IOException
  {
    sendPktHeader(2, 6);
    writeUB2(paramInt);
  }

  protected void sendUB4(long paramLong)
    throws IOException
  {
    sendPktHeader(4, 4);
    writeUB4(paramLong);
  }

  protected void sendPktHeader(int paramInt1, int paramInt2)
    throws NetException, IOException
  {
    d(paramInt1, paramInt2);
    writeUB2(paramInt1);
    writeUB2(paramInt2);
  }

  protected int receiveUB2()
    throws NetException, IOException
  {
    f(3);
    int k = readUB2();
    return k & 0xFFFF;
  }

  private void a(int paramInt1, int paramInt2, int paramInt3)
    throws NetException
  {
    if ((paramInt2 < 0) || (paramInt2 > 7))
      throw new NetException(313);
    if (paramInt2 != paramInt3)
      throw new NetException(314);
    switch (paramInt3)
    {
    case 0:
    case 1:
      break;
    case 2:
      if (paramInt1 <= 1)
        break;
      throw new NetException(312);
    case 3:
    case 6:
      if (paramInt1 <= 2)
        break;
      throw new NetException(312);
    case 4:
    case 5:
      if (paramInt1 <= 4)
        break;
      throw new NetException(312);
    case 7:
      if (paramInt1 >= 10)
        break;
      throw new NetException(312);
    default:
      throw new NetException(313);
    }
  }

  protected void writeVersion()
    throws IOException
  {
    writeUB4(getVersion());
  }

  protected void sendVersion()
    throws IOException
  {
    sendPktHeader(4, 5);
    writeUB4(getVersion());
  }

  protected void writeUB1(short paramShort)
    throws IOException
  {
    this.j.write(0xFF & paramShort);
  }

  protected void flush()
    throws IOException
  {
    this.j.flush();
  }

  protected short receiveUB1()
    throws NetException, IOException
  {
    f(2);
    int k = readUB1();
    return k;
  }

  protected int[] receiveUB2Array()
    throws NetException, IOException
  {
    int k = f(1);
    long l1 = readUB4();
    int m = readUB2();
    long l2 = readUB4();
    int[] arrayOfInt = new int[(int)l2];
    if ((l1 != -559038737L) || (m != 3))
      throw new NetException(310);
    for (int n = 0; n < arrayOfInt.length; n++)
      arrayOfInt[n] = readUB2();
    return arrayOfInt;
  }

  protected void sendUB2Array(int[] paramArrayOfInt)
    throws IOException
  {
    sendPktHeader(10 + paramArrayOfInt.length * 2, 1);
    writeUB4(-559038737L);
    writeUB2(3);
    writeUB4(paramArrayOfInt.length);
    for (int k = 0; k < paramArrayOfInt.length; k++)
      writeUB2(paramArrayOfInt[k] & 0xFFFF);
  }

  protected void writeUB2(int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[2];
    int k = b((short)(0xFFFF & paramInt), arrayOfByte);
    this.j.write(arrayOfByte, 0, k);
  }

  protected long getVersion()
  {
    long l = 135286784L;
    return l;
  }

  private byte b(int paramInt, byte[] paramArrayOfByte)
  {
    int k = 0;
    for (int m = paramArrayOfByte.length - 1; m >= 0; m--)
    {
      k = (byte)(k + 1);
      paramArrayOfByte[k] = (byte)(paramInt >>> 8 * m & 0xFF);
    }
    return k;
  }

  private byte[] c(int paramInt)
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    try
    {
      if (this.i.read(arrayOfByte) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    return arrayOfByte;
  }

  protected long readUB4()
    throws NetException, IOException
  {
    byte[] arrayOfByte = new byte[4];
    long l = g(arrayOfByte);
    return l;
  }

  protected void writeUB4(long paramLong)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4];
    int k = b((int)(0xFFFFFFFF & paramLong), arrayOfByte);
    this.j.write(arrayOfByte, 0, k);
  }

  private void d(int paramInt1, int paramInt2)
    throws NetException
  {
    if ((paramInt2 < 0) || (paramInt2 > 7))
      throw new NetException(313);
    switch (paramInt2)
    {
    case 0:
    case 1:
      break;
    case 2:
      if (paramInt1 <= 1)
        break;
      throw new NetException(312);
    case 3:
    case 6:
      if (paramInt1 <= 2)
        break;
      throw new NetException(312);
    case 4:
    case 5:
      if (paramInt1 <= 4)
        break;
      throw new NetException(312);
    case 7:
      if (paramInt1 >= 10)
        break;
      throw new NetException(312);
    default:
      throw new NetException(313);
    }
  }

  private int e(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws NetException, IOException
  {
    int k = 0;
    try
    {
      if ((k = this.i.read(paramArrayOfByte, paramInt1, paramInt2)) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    return k;
  }

  protected void sendRaw(byte[] paramArrayOfByte)
    throws IOException
  {
    sendPktHeader(paramArrayOfByte.length, 1);
    this.j.write(paramArrayOfByte);
  }

  protected long receiveUB4()
    throws NetException, IOException
  {
    f(4);
    long l = readUB4();
    return l;
  }

  protected String receiveString()
    throws NetException, IOException
  {
    int k = f(0);
    return new String(c(k));
  }

  private int f(int paramInt)
    throws NetException, IOException
  {
    int k = readUB2();
    int m = readUB2();
    a(k, m, paramInt);
    return k;
  }

  private long g(byte[] paramArrayOfByte)
    throws NetException, IOException
  {
    long l = 0L;
    try
    {
      if (this.i.read(paramArrayOfByte) < 0)
        throw new NetException(0);
    }
    catch (NetException localNetException)
    {
      throw new IOException(localNetException.getMessage());
    }
    for (int k = 0; k < paramArrayOfByte.length; k++)
      l |= (paramArrayOfByte[k] & 0xFF) << 8 * (paramArrayOfByte.length - 1 - k);
    l &= -1L;
    return l;
  }

  protected int receiveStatus()
    throws NetException, IOException
  {
    f(6);
    return readUB2();
  }

  protected void sendUB1(short paramShort)
    throws IOException
  {
    sendPktHeader(1, 2);
    this.j.write(0xFF & paramShort);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.AnoComm
 * JD-Core Version:    0.6.0
 */