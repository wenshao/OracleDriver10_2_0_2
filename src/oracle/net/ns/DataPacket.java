package oracle.net.ns;

import java.io.IOException;
import java.io.OutputStream;

public class DataPacket extends Packet
  implements SQLnetDef
{
  static final boolean DEBUG2 = false;
  protected int pktOffset;
  protected int dataFlags;
  protected boolean isBufferFull = false;
  protected boolean isBufferEmpty = false;
  protected int availableBytesToSend = 0;
  protected int availableBytesToRead = 0;

  public DataPacket(SessionAtts paramSessionAtts, int paramInt)
  {
    super(paramSessionAtts, paramInt, 6, 0);
    initialize(paramInt);
  }

  public DataPacket(SessionAtts paramSessionAtts)
  {
    this(paramSessionAtts, paramSessionAtts.getSDU());
  }

  protected void receive()
    throws IOException, NetException
  {
    super.receive();
    this.dataOff = (this.pktOffset = 10);
    this.dataLen = (this.length - this.dataOff);
    this.dataFlags = (this.buffer[8] & 0xFF);
    this.dataFlags <<= 8;
    this.dataFlags |= this.buffer[9] & 0xFF;
    if ((this.type == 6) && ((this.dataFlags & 0x40) != 0))
      this.sAtts.dataEOF = true;
    if ((this.type == 6) && (0 == this.dataLen))
      this.type = 7;
  }

  protected void send()
    throws IOException
  {
    send(0);
  }

  protected void send(int paramInt)
    throws IOException
  {
    this.buffer[8] = (byte)(paramInt / 256);
    this.buffer[9] = (byte)(paramInt % 256);
    setBufferLength(this.pktOffset);
    this.sAtts.ntOutputStream.write(this.buffer, 0, this.pktOffset);
    this.pktOffset = 10;
    this.availableBytesToSend = 0;
    this.isBufferFull = false;
  }

  protected int putDataInBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.buffer.length - this.pktOffset <= paramInt2 ? this.buffer.length - this.pktOffset : paramInt2;
    if (i > 0)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.pktOffset, i);
      this.pktOffset += i;
      this.isBufferFull = (this.pktOffset == this.buffer.length);
      this.availableBytesToSend = (this.dataOff < this.pktOffset ? this.pktOffset - this.dataOff : 0);
    }
    return i;
  }

  protected int getDataFromBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws NetException
  {
    int i = this.length - this.pktOffset <= paramInt2 ? this.length - this.pktOffset : paramInt2;
    if (i > 0)
    {
      System.arraycopy(this.buffer, this.pktOffset, paramArrayOfByte, paramInt1, i);
      this.pktOffset += i;
      this.isBufferEmpty = (this.pktOffset == this.length);
      this.availableBytesToRead = (this.dataOff + this.dataLen - this.pktOffset);
    }
    return i;
  }

  protected void setBufferLength(int paramInt)
    throws NetException
  {
    this.buffer[0] = (byte)(paramInt / 256);
    this.buffer[1] = (byte)(paramInt % 256);
  }

  protected void initialize(int paramInt)
  {
    this.dataOff = (this.pktOffset = 10);
    this.dataLen = (paramInt - this.dataOff);
    this.dataFlags = 0;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.DataPacket
 * JD-Core Version:    0.6.0
 */