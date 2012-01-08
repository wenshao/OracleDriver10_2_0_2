package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import oracle.net.nl.RepConversion;

public class Packet
  implements SQLnetDef
{
  private int buffer2send;
  protected int sdu;
  protected int tdu;
  protected int length;
  public int type;
  protected int flags;
  protected int dataLen;
  protected int dataOff;
  protected String data;
  protected byte[] buffer;
  protected byte[] header = new byte[8];
  public SessionAtts sAtts;

  public Packet(SessionAtts paramSessionAtts)
  {
    this.sAtts = paramSessionAtts;
    this.sdu = paramSessionAtts.getSDU();
    this.tdu = paramSessionAtts.getTDU();
  }

  public Packet(SessionAtts paramSessionAtts, int paramInt)
  {
    this(paramSessionAtts);
    createBuffer(paramInt);
  }

  public Packet(SessionAtts paramSessionAtts, int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramSessionAtts);
    createBuffer(paramInt1, paramInt2, paramInt3);
  }

  public Packet(Packet paramPacket)
  {
    this(paramPacket.sAtts);
    this.length = paramPacket.length;
    this.type = paramPacket.type;
    this.flags = paramPacket.flags;
    this.dataLen = paramPacket.dataLen;
    this.dataOff = paramPacket.dataOff;
    this.buffer = paramPacket.buffer;
  }

  protected void createBuffer(int paramInt)
  {
    this.buffer = new byte[paramInt];
    this.buffer[0] = (byte)(paramInt / 256);
    this.buffer[1] = (byte)(paramInt % 256);
  }

  protected void createBuffer(int paramInt1, int paramInt2, int paramInt3)
  {
    this.buffer = new byte[paramInt1];
    this.buffer[0] = (byte)(paramInt1 / 256);
    this.buffer[1] = (byte)(paramInt1 % 256);
    this.buffer[5] = (byte)paramInt3;
    this.buffer[4] = (byte)paramInt2;
  }

  protected void receive()
    throws IOException, NetException
  {
    int i = 0;
    while (i < this.header.length)
      try
      {
        if (i += this.sAtts.ntInputStream.read(this.header, i, this.header.length - i) <= 0)
          throw new NetException(0);
      }
      catch (InterruptedIOException localInterruptedIOException1)
      {
        throw new NetException(504);
      }
    this.length = (this.header[0] & 0xFF);
    this.length <<= 8;
    this.length |= this.header[1] & 0xFF;
    this.type = this.header[4];
    this.flags = this.header[5];
    if (this.type > 19)
      throw new NetException(204);
    if ((this.length > 32767) || (this.length > this.sdu))
      throw new NetException(203);
    if (this.length < 8)
      throw new NetException(207);
    this.buffer[5] = (byte)this.flags;
    this.buffer[4] = (byte)this.type;
    while (i < this.length)
      try
      {
        if (i += this.sAtts.ntInputStream.read(this.buffer, i, this.length - i) <= 0)
          throw new NetException(0);
      }
      catch (InterruptedIOException localInterruptedIOException2)
      {
      }
  }

  protected void send()
    throws IOException
  {
    this.sAtts.ntOutputStream.write(this.buffer, 0, this.buffer.length);
  }

  protected void extractData()
    throws IOException, NetException
  {
    if (this.dataLen <= 0)
    {
      this.data = new String();
    }
    else if (this.length > this.dataOff)
    {
      this.data = new String(this.buffer, 0, this.dataOff, this.dataLen);
    }
    else
    {
      byte[] arrayOfByte = new byte[this.dataLen];
      if (this.sAtts.nsInputStream.read(arrayOfByte) < 0)
        throw new NetException(0);
      this.data = new String(arrayOfByte, 0);
    }
  }

  protected String getData()
  {
    return this.data;
  }

  protected void dump(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    System.out.println("Packet dump");
    System.out.println("buffer.length=" + paramArrayOfByte.length);
    System.out.println("offset       =" + paramInt1);
    System.out.println("len          =" + paramInt2);
    for (int j = paramInt1; j < paramInt2; j += 8)
    {
      System.out.print("|");
      for (int k = 0; (k < 8) && (i < paramInt2 - 1); k++)
      {
        i = j + k;
        RepConversion.printInHex(paramArrayOfByte[i]);
        System.out.print(" ");
      }
      System.out.println("|");
    }
    System.out.println("finish dump");
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.Packet
 * JD-Core Version:    0.6.0
 */