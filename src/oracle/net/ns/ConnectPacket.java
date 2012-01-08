package oracle.net.ns;

import java.io.IOException;
import oracle.net.nt.ConnOption;

public class ConnectPacket extends Packet
  implements SQLnetDef
{
  private boolean connDataOflow;

  public ConnectPacket(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.data = paramSessionAtts.cOption.conn_data.toString();
    this.dataLen = (this.data == null ? 0 : this.data.length());
    this.connDataOflow = (this.dataLen > 230);
    int i = !this.connDataOflow ? 34 + this.dataLen : 34;
    createBuffer(i, 1, 0);
    this.buffer[8] = 1;
    this.buffer[9] = 52;
    this.buffer[10] = 1;
    this.buffer[11] = 44;
    this.buffer[12] = 0;
    this.buffer[13] = 0;
    this.buffer[14] = (byte)(this.sdu / 256);
    this.buffer[15] = (byte)(this.sdu % 256);
    this.buffer[16] = (byte)(this.tdu / 256);
    this.buffer[17] = (byte)(this.tdu % 256);
    this.buffer[18] = 79;
    this.buffer[19] = -104;
    this.buffer[22] = 0;
    this.buffer[23] = 1;
    this.buffer[24] = (byte)(this.dataLen / 256);
    this.buffer[25] = (byte)(this.dataLen % 256);
    this.buffer[27] = 34;
    if (!paramSessionAtts.anoEnabled)
    {
      int tmp296_295 = 4;
      this.buffer[33] = tmp296_295;
      this.buffer[32] = tmp296_295;
    }
    else
    {
      byte tmp319_318 = (byte)paramSessionAtts.getANOFlags();
      this.buffer[33] = tmp319_318;
      this.buffer[32] = tmp319_318;
    }
    if ((!this.connDataOflow) && (this.dataLen > 0))
      this.data.getBytes(0, this.dataLen, this.buffer, 34);
  }

  protected void send()
    throws IOException
  {
    super.send();
    if (this.connDataOflow)
    {
      byte[] arrayOfByte = new byte[this.dataLen];
      this.data.getBytes(0, this.dataLen, arrayOfByte, 0);
      this.sAtts.nsOutputStream.write(arrayOfByte);
      this.sAtts.nsOutputStream.flush();
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.ConnectPacket
 * JD-Core Version:    0.6.0
 */