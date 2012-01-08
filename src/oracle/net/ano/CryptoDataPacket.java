package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.C00;
import oracle.net.aso.C03;
import oracle.net.ns.DataPacket;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class CryptoDataPacket extends DataPacket
  implements SQLnetDef
{
  private C03 b = null;
  private Ano c = null;
  private int d = 0;
  private int e = 0;
  private C00 f = null;

  public void receive()
    throws IOException, NetException
  {
    super.receive();
    if (this.type != 6)
      return;
    this.c = this.sAtts.ano;
    if (this.c.encryptionAlg != null)
    {
      this.f = this.c.encryptionAlg;
      this.d += this.f.b();
      if (this.c.getRenewKey())
        this.f.a(null, null);
    }
    if (this.c.dataIntegrityAlg != null)
    {
      this.b = this.c.dataIntegrityAlg;
      this.d += this.b.size();
      if (this.c.getRenewKey())
        this.b.renew();
    }
    this.d += 1;
    this.c.setRenewKey(false);
    try
    {
      decryptAndChecksum();
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
  }

  public CryptoDataPacket(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.c = paramSessionAtts.ano;
    if (paramSessionAtts.ano.encryptionAlg != null)
    {
      this.f = paramSessionAtts.ano.encryptionAlg;
      this.d += this.f.b();
    }
    if (paramSessionAtts.ano.dataIntegrityAlg != null)
    {
      this.b = paramSessionAtts.ano.dataIntegrityAlg;
      this.d += this.b.size();
    }
    this.d += 1;
  }

  protected void decryptAndChecksum()
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[this.dataLen - 1];
    int i = this.buffer[(this.length - 1)];
    this.dataLen -= 1;
    System.arraycopy(this.buffer, this.dataOff, arrayOfByte1, 0, this.dataLen);
    byte[] arrayOfByte2;
    if (this.f != null)
      arrayOfByte2 = this.f.e(arrayOfByte1);
    else
      arrayOfByte2 = arrayOfByte1;
    if (arrayOfByte2 == null)
      throw new IOException("Bad buffer - Fail to decrypt buffer");
    this.dataLen = arrayOfByte2.length;
    if (this.b != null)
    {
      byte[] arrayOfByte3 = new byte[this.b.size()];
      this.dataLen -= this.b.size();
      System.arraycopy(arrayOfByte2, this.dataLen, arrayOfByte3, 0, this.b.size());
      byte[] arrayOfByte4 = new byte[this.dataLen];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, this.dataLen);
      if (this.b.compare(arrayOfByte4, arrayOfByte3) == true)
        throw new IOException("Checksum fail");
      System.arraycopy(arrayOfByte4, 0, this.buffer, this.dataOff, this.dataLen);
    }
    else
    {
      System.arraycopy(arrayOfByte2, 0, this.buffer, this.dataOff, this.dataLen);
    }
    this.length = (this.dataOff + this.dataLen);
    this.pktOffset = 10;
  }

  protected void checksumAndEncrypt()
    throws IOException
  {
    int i = this.availableBytesToSend + this.dataOff;
    byte[] arrayOfByte1 = new byte[this.availableBytesToSend];
    System.arraycopy(this.buffer, this.dataOff, arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte2 = null;
    this.dataLen = this.availableBytesToSend;
    if (this.b != null)
    {
      arrayOfByte2 = this.b.compute(arrayOfByte1, arrayOfByte1.length);
      if (arrayOfByte2 != null)
        this.dataLen += arrayOfByte2.length;
    }
    byte[] arrayOfByte3 = new byte[this.dataLen];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
    if (arrayOfByte2 != null)
      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
    if (this.f != null)
    {
      byte[] arrayOfByte4 = this.f.d(arrayOfByte3);
      if (arrayOfByte4 == null)
        throw new IOException("Fail to encrypt buffer");
      this.dataLen = arrayOfByte4.length;
      System.arraycopy(arrayOfByte4, 0, this.buffer, this.dataOff, this.dataLen);
    }
    else if (this.b != null)
    {
      System.arraycopy(arrayOfByte3, 0, this.buffer, this.dataOff, this.dataLen);
    }
    this.buffer[(this.dataOff + this.dataLen)] = (byte)(this.e >= 2 ? 1 : 0);
    this.dataLen += 1;
    this.pktOffset = (10 + this.dataLen);
    this.length = (10 + this.dataLen);
  }

  protected void send(int paramInt)
    throws IOException
  {
    if (this.e < 2)
      try
      {
        a();
      }
      catch (IOException localIOException1)
      {
        throw localIOException1;
      }
    try
    {
      checksumAndEncrypt();
    }
    catch (IOException localIOException2)
    {
      throw localIOException2;
    }
    super.send(paramInt);
  }

  private void a()
    throws IOException
  {
    byte[] arrayOfByte1 = this.c.getSessionKey();
    byte[] arrayOfByte2 = this.c.getO3logSessionKey();
    if (arrayOfByte2 != null)
    {
      byte[] arrayOfByte3 = new byte[Math.max(arrayOfByte2.length, arrayOfByte1.length)];
      byte[] arrayOfByte4 = arrayOfByte2.length > arrayOfByte1.length ? arrayOfByte2 : arrayOfByte1;
      System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 0, arrayOfByte4.length);
      if ((arrayOfByte2.length < 8) || (arrayOfByte1.length < 8))
        throw new IOException("Key is too small");
      for (int i = 0; i < 8; i++)
        arrayOfByte3[i] = (byte)((arrayOfByte2[i] ^ arrayOfByte1[i]) & 0xFF);
      byte[] arrayOfByte5 = this.c.getInitializationVector();
      if (this.f != null)
        this.f.a(arrayOfByte3, arrayOfByte5);
      if (this.b != null)
        this.b.takeSessionKey(arrayOfByte3, arrayOfByte5);
      this.e = 3;
    }
  }

  protected int putDataInBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = Math.min(this.buffer.length - this.d - this.pktOffset, paramInt2);
    if (i > 0)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.pktOffset, i);
      this.pktOffset += i;
      this.isBufferFull = (this.pktOffset + this.d == this.buffer.length);
      this.availableBytesToSend = (this.dataOff < this.pktOffset ? this.pktOffset - this.dataOff : 0);
    }
    return i;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.CryptoDataPacket
 * JD-Core Version:    0.6.0
 */