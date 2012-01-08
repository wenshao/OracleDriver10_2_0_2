package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.C00;
import oracle.net.aso.C01;
import oracle.net.aso.C04;
import oracle.net.aso.C06;
import oracle.net.aso.C10;
import oracle.net.aso.C11;
import oracle.net.ns.ClientProfile;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class EncryptionService extends Service
  implements AnoServices, SQLnetDef
{
  private int a;
  static final int b = 2;
  private boolean c = false;
  private static boolean d = true;
  private C00 e;

  int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    super.a(paramSessionAtts);
    this.service = 2;
    this.serviceSubPackets = 2;
    this.level = paramSessionAtts.profile.getEncryptionLevelNum();
    this.availableDrivers = h("oracle.net.aso.", d ? SQLnetDef.ENC_CLASSNAME_EX : SQLnetDef.ENC_CLASSNAME);
    this.listOfDrivers = g(paramSessionAtts.profile.getEncryptionServices(), this.availableDrivers);
    i(this.listOfDrivers, d ? SQLnetDef.ENC_CLASSNAME_EX : SQLnetDef.ENC_CLASSNAME, this.level);
    int i = 1;
    if (this.selectedDrivers.length == 0)
    {
      if (this.level == 3)
        throw new NetException(315);
      i |= 8;
    }
    else if (this.level == 3)
    {
      i |= 16;
    }
    return i;
  }

  void b()
    throws NetException, IOException
  {
    for (int i = 0; i < this.selectedDrivers.length; i++)
    {
      if (this.selectedDrivers[i] != this.algID)
        continue;
      this.a = i;
      break;
    }
    if (i == this.selectedDrivers.length)
      throw new NetException(316);
  }

  public boolean isActive()
  {
    return this.c;
  }

  void d()
    throws NetException, IOException
  {
    if (this.numSubPackets != this.serviceSubPackets)
      throw new NetException(305);
    this.version = this.comm.receiveVersion();
    this.algID = this.comm.receiveUB1();
    this.c = (this.algID > 0);
  }

  void c()
    throws NetException, IOException
  {
    if (this.c)
      try
      {
        if (d)
        {
          if (SQLnetDef.ENC_CLASSNAME_EX[this.algID].equals("DES56C"))
            this.ano.encryptionAlg = (this.e = new C10());
          else if (SQLnetDef.ENC_CLASSNAME_EX[this.algID].equals("DES40C"))
            this.ano.encryptionAlg = (this.e = new C06());
          else if (SQLnetDef.ENC_CLASSNAME_EX[this.algID].equals("3DES168"))
            this.ano.encryptionAlg = (this.e = new C01());
          else if (SQLnetDef.ENC_CLASSNAME_EX[this.algID].equals("3DES112"))
            this.ano.encryptionAlg = (this.e = new C11());
          else if (SQLnetDef.ENC_CLASSNAME_EX[this.algID].startsWith("RC4"))
            this.ano.encryptionAlg = (this.e = new C04(true, SQLnetDef.CRYPTO_LEN[this.algID]));
        }
        else
          this.ano.encryptionAlg = (this.e = (C00)Class.forName("oracle.net.aso." + SQLnetDef.ENC_CLASSNAME[this.algID]).newInstance());
        this.e.c(this.ano.getSessionKey(), this.ano.getInitializationVector());
      }
      catch (Exception localException)
      {
        throw new NetException(317);
      }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.EncryptionService
 * JD-Core Version:    0.6.0
 */