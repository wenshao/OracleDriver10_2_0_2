package oracle.net.ns;

import java.io.IOException;

public class AcceptPacket extends Packet
  implements SQLnetDef
{
  protected int version = this.buffer[8] & 0xFF;
  protected int options;
  protected int sduSize;
  protected int tduSize;
  protected int myHWByteOrder;
  protected int flag0;
  protected int flag1;

  public AcceptPacket(Packet paramPacket)
    throws IOException, NetException
  {
    super(paramPacket);
    this.version <<= 8;
    this.version |= this.buffer[9] & 0xFF;
    this.options = (this.buffer[10] & 0xFF);
    this.options <<= 8;
    this.options |= this.buffer[11] & 0xFF;
    this.sduSize = (this.buffer[12] & 0xFF);
    this.sduSize <<= 8;
    this.sduSize |= this.buffer[13] & 0xFF;
    this.tduSize = (this.buffer[14] & 0xFF);
    this.tduSize <<= 8;
    this.tduSize |= this.buffer[15] & 0xFF;
    this.myHWByteOrder = (this.buffer[16] & 0xFF);
    this.myHWByteOrder <<= 8;
    this.myHWByteOrder |= this.buffer[17] & 0xFF;
    this.dataLen = (this.buffer[18] & 0xFF);
    this.dataLen <<= 8;
    this.dataLen |= this.buffer[19] & 0xFF;
    this.dataOff = (this.buffer[20] & 0xFF);
    this.dataOff <<= 8;
    this.dataOff |= this.buffer[21] & 0xFF;
    this.flag0 = this.buffer[22];
    this.flag1 = this.buffer[23];
    extractData();
    this.sAtts.setSDU(this.sduSize);
    this.sAtts.setTDU(this.tduSize);
    if (this.tduSize < this.sduSize)
      this.sAtts.setSDU(this.tduSize);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.AcceptPacket
 * JD-Core Version:    0.6.0
 */