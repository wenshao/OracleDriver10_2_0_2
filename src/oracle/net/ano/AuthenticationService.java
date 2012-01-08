package oracle.net.ano;

import java.io.IOException;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class AuthenticationService extends Service
  implements AnoServices, SQLnetDef
{
  static final int a = 64255;
  static final int b = 2;
  static final int c = 64767;
  private int d;
  static final int e = 3;
  static final int f = 63999;
  static final int g = 63487;
  static final int h = 64511;
  static final int i = 65023;
  static final int j = 65279;
  static final int k = 57569;
  private boolean l = false;
  static final boolean m = true;
  static final int n = 63743;

  int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.service = 1;
    this.serviceSubPackets = 3;
    this.d = 64767;
    this.d = 64767;
    this.availableDrivers = new String[0];
    this.listOfDrivers = new String[0];
    this.level = 3;
    i(this.listOfDrivers, SQLnetDef.AUTH_CLASSNAME, this.level);
    this.serviceSubPackets += this.selectedDrivers.length * 2;
    return 1;
  }

  int f()
  {
    int i1 = 20;
    for (int i2 = 0; i2 < this.selectedDrivers.length; i2++)
    {
      i1 += 5;
      i1 += 4 + SQLnetDef.AUTH_NAME[this.selectedDrivers[i2]].length();
    }
    return i1;
  }

  void b()
    throws NetException, IOException
  {
    if (this.l);
  }

  public boolean isActive()
  {
    return this.l;
  }

  void d()
    throws NetException, IOException
  {
    if (this.numSubPackets != 2)
      throw new NetException(305);
    this.version = this.comm.receiveVersion();
    this.d = this.comm.receiveStatus();
    if (this.d == 64255)
      for (int i1 = 0; i1 < (this.numSubPackets - 2) / 2; i1++)
      {
        this.comm.receiveUB1();
        this.comm.receiveString();
        this.l = true;
      }
    if (this.d == 64511)
      this.l = false;
    else
      throw new NetException(307);
  }

  void n()
    throws NetException, IOException
  {
    this.comm.sendVersion();
    this.comm.sendUB2(57569);
    this.comm.sendStatus(this.d);
    for (int i1 = 0; i1 < this.selectedDrivers.length; i1++)
    {
      this.comm.sendUB1((short)this.selectedDrivers[i1]);
      this.comm.sendString(SQLnetDef.AUTH_NAME[this.selectedDrivers[i1]]);
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.AuthenticationService
 * JD-Core Version:    0.6.0
 */