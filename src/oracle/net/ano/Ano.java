package oracle.net.ano;

import java.io.IOException;
import oracle.net.aso.C00;
import oracle.net.aso.C03;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class Ano
  implements AnoServices, SQLnetDef
{
  private short h;
  private Service[] i;
  private int j;
  protected byte[] iv;
  private int k;
  protected SessionAtts sAtts;
  protected C03 dataIntegrityAlg;
  private long l;
  protected C00 encryptionAlg;
  protected byte[] skey;
  private int m;
  private boolean n;
  private int o = 1;
  protected boolean cryptoNeeded = false;
  protected AnoComm anoComm;
  protected byte[] clientPK;
  private Service[] p;
  private byte[] q;

  public C00 getEncryptionAlg()
  {
    return this.encryptionAlg;
  }

  protected void setSessionKey(byte[] paramArrayOfByte)
  {
    this.skey = paramArrayOfByte;
  }

  public int getNAFlags()
  {
    return this.o;
  }

  private void a()
    throws NetException, IOException
  {
    this.anoComm.writeUB4(-559038737L);
    this.anoComm.writeUB2(this.m);
    this.anoComm.writeVersion();
    this.anoComm.writeUB2(SQLnetDef.SERVICES_INORDER.length);
    this.anoComm.writeUB1(this.h);
  }

  public C03 getDataIntegrityAlg()
  {
    return this.dataIntegrityAlg;
  }

  protected void setInitializationVector(byte[] paramArrayOfByte)
  {
    this.iv = paramArrayOfByte;
  }

  private boolean b(long paramLong)
  {
    return paramLong == -559038737L;
  }

  protected byte[] getInitializationVector()
  {
    return this.iv;
  }

  private void c()
    throws NetException, IOException
  {
    Service localService = new Service();
    localService.a(this.sAtts);
    for (int i1 = 0; i1 < this.j; i1++)
    {
      localService.j();
      this.p[localService.receivedService].b(localService);
    }
    localService = null;
  }

  protected void sendANOHeader(int paramInt1, int paramInt2, short paramShort)
    throws NetException, IOException
  {
    this.anoComm.writeUB4(-559038737L);
    this.anoComm.writeUB2(paramInt1);
    this.anoComm.writeVersion();
    this.anoComm.writeUB2(paramInt2);
    this.anoComm.writeUB1(paramShort);
  }

  private void d()
    throws NetException, IOException
  {
    for (int i1 = 0; i1 < SQLnetDef.SERVICES_INORDER.length; i1++)
      this.i[i1].a();
    this.anoComm.flush();
  }

  protected byte[] getSessionKey()
  {
    return this.skey;
  }

  private int e()
    throws NetException
  {
    int i1 = 0;
    for (int i2 = 0; i2 < SQLnetDef.SERV_INORDER_CLASSNAME.length; i2++)
    {
      try
      {
        this.i[i2] = ((Service)Class.forName("oracle.net.ano." + SQLnetDef.SERV_INORDER_CLASSNAME[i2]).newInstance());
      }
      catch (Exception localException)
      {
        throw new NetException(308);
      }
      this.o |= this.i[i2].a(this.sAtts);
      i1 += this.i[i2].l();
      this.p[this.i[i2].service] = this.i[i2];
    }
    if (((this.o & 0x10) > 0) && ((this.o & 0x8) > 0))
      this.o &= -17;
    return i1;
  }

  public boolean getRenewKey()
  {
    return this.n;
  }

  public void init(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.sAtts = paramSessionAtts;
    this.sAtts.ano = this;
    this.i = new Service[4];
    this.p = new Service[5];
    this.anoComm = new AnoComm(paramSessionAtts);
    this.k = e();
    this.m = (13 + this.k);
  }

  public byte[] getO3logSessionKey()
  {
    return this.q;
  }

  private void f()
    throws NetException, IOException
  {
    for (int i1 = 0; i1 < this.j; i1++)
      this.i[i1].c();
    this.cryptoNeeded = ((this.i[2].isActive()) || (this.i[3].isActive()));
  }

  public void setRenewKey(boolean paramBoolean)
  {
    this.n = paramBoolean;
  }

  public void negotiation()
    throws NetException, IOException
  {
    a();
    d();
    g();
    c();
    f();
    if (this.cryptoNeeded)
      this.sAtts.turnEncryptionOn(new AnoNetInputStream(this.sAtts), new AnoNetOutputStream(this.sAtts));
  }

  private int g()
    throws NetException, IOException
  {
    long l1 = this.anoComm.readUB4();
    if (!b(l1))
      throw new NetException(302);
    this.m = this.anoComm.readUB2();
    this.l = this.anoComm.readUB4();
    this.j = this.anoComm.readUB2();
    this.h = this.anoComm.readUB1();
    return this.j;
  }

  public void setO3logSessionKey(byte[] paramArrayOfByte)
  {
    this.q = paramArrayOfByte;
  }

  protected void setClientPK(byte[] paramArrayOfByte)
  {
    this.clientPK = paramArrayOfByte;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.Ano
 * JD-Core Version:    0.6.0
 */