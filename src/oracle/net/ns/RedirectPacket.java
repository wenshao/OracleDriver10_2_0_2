package oracle.net.ns;

import java.io.IOException;

public class RedirectPacket extends Packet
  implements SQLnetDef
{
  public RedirectPacket(Packet paramPacket)
    throws IOException, NetException
  {
    super(paramPacket);
    this.dataOff = 10;
    this.dataLen = (this.buffer[8] & 0xFF);
    this.dataLen <<= 8;
    this.dataLen |= this.buffer[9] & 0xFF;
    extractData();
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.RedirectPacket
 * JD-Core Version:    0.6.0
 */