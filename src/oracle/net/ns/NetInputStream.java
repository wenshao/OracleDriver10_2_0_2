package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;

public class NetInputStream extends InputStream
  implements SQLnetDef
{
  protected DataPacket daPkt;
  protected MarkerPacket mkPkt;
  protected SessionAtts sAtts;
  private byte[] tmpBuf = new byte[1];

  public NetInputStream(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.daPkt = new DataPacket(paramSessionAtts);
  }

  public int read()
    throws IOException, NetException, BreakNetException
  {
    return read(this.tmpBuf) < 0 ? -1 : this.tmpBuf[0] & 0xFF;
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException, NetException, BreakNetException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, NetException, BreakNetException
  {
    int i = 0;
    try
    {
      do
      {
        if ((this.daPkt == null) || (this.daPkt.availableBytesToRead <= 0) || (this.daPkt.type == 7))
          getNextPacket();
        i += this.daPkt.getDataFromBuffer(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      }
      while (i < paramInt2);
    }
    catch (NetException localNetException)
    {
      if (localNetException.getErrorNumber() == 0)
        return -1;
      throw localNetException;
    }
    return i;
  }

  public int available()
    throws IOException
  {
    return this.daPkt.availableBytesToRead;
  }

  protected void getNextPacket()
    throws IOException, NetException, BreakNetException
  {
    if (this.sAtts.dataEOF)
      throw new NetException(202);
    if (this.sAtts.nsOutputStream.available() > 0)
      this.sAtts.nsOutputStream.flush();
    this.daPkt.receive();
    switch (this.daPkt.type)
    {
    case 6:
      break;
    case 12:
      this.mkPkt = new MarkerPacket(this.daPkt);
      this.sAtts.onBreakReset = this.mkPkt.isBreakPkt();
      processMarker();
      throw new BreakNetException(500);
    case 7:
      break;
    default:
      throw new NetException(205);
    }
  }

  protected void processMarker()
    throws IOException, NetException, BreakNetException
  {
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.NetInputStream
 * JD-Core Version:    0.6.0
 */