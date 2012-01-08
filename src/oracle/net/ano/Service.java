package oracle.net.ano;

import java.io.IOException;
import java.util.Vector;
import oracle.net.aso.C01;
import oracle.net.aso.C04;
import oracle.net.aso.C06;
import oracle.net.aso.C10;
import oracle.net.aso.C11;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class Service
  implements AnoServices, SQLnetDef
{
  protected int receivedService;
  protected long oracleError;
  protected String[] listOfDrivers;
  protected Ano ano;
  protected int status;
  protected String[] availableDrivers;
  protected AnoComm comm;
  protected int serviceSubPackets;
  protected long version;
  protected short algID;
  protected int numSubPackets;
  protected byte[] selectedDrivers;
  protected SessionAtts sAtts;
  protected int service;
  protected int level;

  void a()
    throws NetException, IOException
  {
    e();
    n();
  }

  void c()
    throws NetException, IOException
  {
  }

  void b(Service paramService)
    throws NetException, IOException
  {
    this.receivedService = paramService.receivedService;
    this.numSubPackets = paramService.numSubPackets;
    this.oracleError = paramService.oracleError;
    d();
    b();
  }

  void e()
    throws NetException, IOException
  {
    this.comm.writeUB2(this.service);
    this.comm.writeUB2(this.serviceSubPackets);
    this.comm.writeUB4(0L);
  }

  void b()
    throws NetException, IOException
  {
  }

  int f()
  {
    return 12 + this.selectedDrivers.length;
  }

  String[] g(String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws NetException
  {
    String[] arrayOfString;
    if ((paramArrayOfString1 == null) || (paramArrayOfString1.length == 0))
    {
      arrayOfString = paramArrayOfString2;
    }
    else
    {
      Vector localVector = new Vector(10);
      for (int i = 0; i < paramArrayOfString1.length; i++)
      {
        for (int j = 0; j < paramArrayOfString2.length; j++)
        {
          if (!paramArrayOfString2[j].equals(paramArrayOfString1[i]))
            continue;
          localVector.addElement(paramArrayOfString2[j]);
          break;
        }
        if (j != paramArrayOfString2.length)
          continue;
        throw new NetException(303);
      }
      int k = localVector.size();
      arrayOfString = new String[k];
      for (i = 0; i < k; i++)
        arrayOfString[i] = ((String)localVector.elementAt(i));
    }
    return arrayOfString;
  }

  String[] h(String paramString, String[] paramArrayOfString)
  {
    Vector localVector = new Vector(10);
    for (int i = 1; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i].equals(""))
        continue;
      try
      {
        if (paramArrayOfString[i].equals("DES56C"))
          new C10();
        else if (paramArrayOfString[i].equals("DES40C"))
          new C06();
        else if (paramArrayOfString[i].startsWith("RC4"))
          new C04();
        else if (paramArrayOfString[i].equals("3DES168"))
          new C01();
        else if (paramArrayOfString[i].equals("3DES112"))
          new C11();
        else
          Class.forName(paramString + paramArrayOfString[i]).newInstance();
        localVector.addElement(paramArrayOfString[i]);
      }
      catch (Exception localException)
      {
      }
    }
    i = localVector.size();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
      arrayOfString[j] = ((String)localVector.elementAt(j));
    return arrayOfString;
  }

  void i(String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt)
    throws NetException
  {
    int i;
    switch (paramInt)
    {
    case 0:
      this.selectedDrivers = new byte[paramArrayOfString1.length + 1];
      this.selectedDrivers[0] = 0;
      i = 0;
    case 1:
    case 2:
    case 3:
    }
    while (i < paramArrayOfString1.length)
    {
      if (!paramArrayOfString1[i].equals(""))
        this.selectedDrivers[(i + 1)] = k(paramArrayOfString2, paramArrayOfString1[i]);
      i++;
      continue;
      this.selectedDrivers = new byte[1];
      this.selectedDrivers[0] = 0;
      break;
      i = 0;
      this.selectedDrivers = new byte[paramArrayOfString1.length + 1];
      for (i = 0; i < paramArrayOfString1.length; i++)
      {
        if (paramArrayOfString1[i].equals(""))
          continue;
        this.selectedDrivers[i] = k(paramArrayOfString2, paramArrayOfString1[i]);
      }
      this.selectedDrivers[i] = 0;
      break;
      this.selectedDrivers = new byte[paramArrayOfString1.length];
      i = 0;
      while (i < paramArrayOfString1.length)
      {
        if (!paramArrayOfString1[i].equals(""))
          this.selectedDrivers[i] = k(paramArrayOfString2, paramArrayOfString1[i]);
        i++;
        continue;
        throw new NetException(304);
      }
    }
  }

  void d()
    throws NetException, IOException
  {
  }

  int a(SessionAtts paramSessionAtts)
    throws NetException
  {
    this.sAtts = paramSessionAtts;
    this.ano = paramSessionAtts.ano;
    this.comm = paramSessionAtts.ano.anoComm;
    this.level = 0;
    this.selectedDrivers = new byte[0];
    return 1;
  }

  void j()
    throws NetException, IOException
  {
    this.receivedService = this.comm.readUB2();
    this.numSubPackets = this.comm.readUB2();
    this.oracleError = this.comm.readUB4();
    if (this.oracleError != 0L)
      throw new NetException((int)this.oracleError);
  }

  byte k(String[] paramArrayOfString, String paramString)
    throws NetException
  {
    for (int i = 0; i < paramArrayOfString.length; i = (byte)(i + 1))
      if (paramString.equals(paramArrayOfString[i]))
        return i;
    throw new NetException(309);
  }

  public boolean isActive()
  {
    return false;
  }

  int l()
  {
    return 8 + f();
  }

  void m(String[] paramArrayOfString)
    throws NetException
  {
  }

  public static String getServiceName(int paramInt)
    throws NetException
  {
    String str;
    switch (paramInt)
    {
    case 1:
      str = "AUTHENTICATION";
      break;
    case 2:
      str = "ENCRYPTION";
      break;
    case 3:
      str = "DATAINTEGRITY";
      break;
    case 4:
      str = "SUPERVISOR";
      break;
    default:
      throw new NetException(323);
    }
    return str;
  }

  void n()
    throws NetException, IOException
  {
    this.comm.sendVersion();
    this.comm.sendRaw(this.selectedDrivers);
  }

  public static String getLevelString(int paramInt)
    throws NetException
  {
    String str;
    switch (paramInt)
    {
    case 0:
      str = "ACCEPTED";
      break;
    case 1:
      str = "REJECTED";
      break;
    case 2:
      str = "REQUESTED";
      break;
    case 3:
      str = "REQUIRED";
      break;
    default:
      throw new NetException(322);
    }
    return str;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.Service
 * JD-Core Version:    0.6.0
 */