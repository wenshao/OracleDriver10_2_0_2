package oracle.net.ano;

import java.io.IOException;
import java.io.PrintStream;
import oracle.net.aso.C03;
import oracle.net.aso.C08;
import oracle.net.ns.ClientProfile;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class DataIntegrityService extends Service
  implements AnoServices, SQLnetDef
{
  private C03 e;
  static final int f = 2;
  private boolean g = false;
  private short h;
  private static final int i = 40;
  private byte[] j;
  private int k;

  public boolean isActive()
  {
    return this.g;
  }

  int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.service = 3;
    this.serviceSubPackets = 2;
    this.k = 0;
    this.level = paramSessionAtts.profile.getDataIntegrityLevelNum();
    this.availableDrivers = h("oracle.net.aso.", SQLnetDef.DATAINTEGRITY_CLASSNAME);
    this.listOfDrivers = g(paramSessionAtts.profile.getDataIntegrityServices(), this.availableDrivers);
    i(this.listOfDrivers, SQLnetDef.DATAINTEGRITY_CLASSNAME, this.level);
    int m = 1;
    if (this.selectedDrivers.length == 0)
    {
      if (this.level == 3)
        throw new NetException(315);
      m |= 8;
    }
    else if (this.level == 3)
    {
      m |= 16;
    }
    return m;
  }

  void b()
    throws NetException, IOException
  {
    for (int m = 0; m < this.selectedDrivers.length; m++)
    {
      if (this.selectedDrivers[m] != this.h)
        continue;
      this.k = m;
      break;
    }
    if (m == this.selectedDrivers.length)
      throw new NetException(319);
  }

  void c()
    throws NetException, IOException
  {
    if (this.g)
    {
      try
      {
        this.ano.dataIntegrityAlg = (this.e = (C03)Class.forName("oracle.net.aso." + SQLnetDef.DATAINTEGRITY_CLASSNAME[this.h]).newInstance());
      }
      catch (Exception localException)
      {
        System.out.println(" alg =" + SQLnetDef.DATAINTEGRITY_CLASSNAME[this.h]);
        localException.printStackTrace();
        throw new NetException(318);
      }
      this.e.init(this.ano.getSessionKey(), this.ano.getInitializationVector());
    }
    if (this.j != null)
    {
      int m = 13 + 8 + 4 + this.j.length;
      this.sAtts.ano.sendANOHeader(m, 1, 0);
      this.serviceSubPackets = 1;
      e();
      this.comm.sendRaw(this.j);
      this.comm.flush();
    }
  }

  void d()
    throws NetException, IOException
  {
    this.version = this.comm.receiveVersion();
    this.h = this.comm.receiveUB1();
    if ((this.numSubPackets != this.serviceSubPackets) && (this.numSubPackets == 8))
    {
      short s1 = (short)this.comm.receiveUB2();
      short s2 = (short)this.comm.receiveUB2();
      byte[] arrayOfByte1 = this.comm.receiveRaw();
      byte[] arrayOfByte2 = this.comm.receiveRaw();
      byte[] arrayOfByte3 = this.comm.receiveRaw();
      byte[] arrayOfByte4 = this.comm.receiveRaw();
      if ((s1 <= 0) || (s2 <= 0))
        throw new IOException("Bad parameters from server");
      int m = (s2 + 7) / 8;
      if ((arrayOfByte3.length != m) || (arrayOfByte2.length != m))
        throw new IOException("DiffieHellman negotiation out of synch");
      C08 localC08 = new C08(arrayOfByte1, arrayOfByte2, s1, s2);
      this.j = localC08.g();
      this.sAtts.ano.setClientPK(this.j);
      this.sAtts.ano.setInitializationVector(arrayOfByte4);
      this.sAtts.ano.setSessionKey(localC08.a(arrayOfByte3, arrayOfByte3.length));
    }
    this.g = (this.h > 0);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.DataIntegrityService
 * JD-Core Version:    0.6.0
 */