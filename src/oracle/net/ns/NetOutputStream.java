package oracle.net.ns;

import java.io.IOException;
import java.io.OutputStream;

public class NetOutputStream extends OutputStream
  implements SQLnetDef
{
  protected DataPacket daPkt;
  protected SessionAtts sAtts;
  private byte[] tmpBuf = new byte[1];

  public NetOutputStream(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts);
  }

  public NetOutputStream(SessionAtts paramSessionAtts, int paramInt)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts, paramInt);
  }

  public void write(int paramInt)
    throws IOException
  {
    this.tmpBuf[0] = (byte)paramInt;
    write(this.tmpBuf);
  }

  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = 0;
    int j = 0;
    while (paramInt2 > i)
    {
      i += this.daPkt.putDataInBuffer(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      if (!this.daPkt.isBufferFull)
        continue;
      j = paramInt2 > i ? 32 : 0;
      this.daPkt.send(j);
    }
  }

  public int available()
    throws IOException
  {
    return this.daPkt.availableBytesToSend;
  }

  public void flush()
    throws IOException
  {
    if (this.daPkt.availableBytesToSend > 0)
      this.daPkt.send(0);
  }

  public void close()
    throws IOException
  {
    this.daPkt.send(64);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.NetOutputStream
 * JD-Core Version:    0.6.0
 */