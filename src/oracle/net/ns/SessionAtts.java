package oracle.net.ns;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import oracle.net.ano.Ano;
import oracle.net.nt.ConnOption;
import oracle.net.nt.NTAdapter;

public class SessionAtts
  implements SQLnetDef
{
  private int sdu;
  private int tdu;
  protected NTAdapter nt;
  protected InputStream ntInputStream;
  protected OutputStream ntOutputStream;
  protected NetInputStream nsInputStream;
  protected NetOutputStream nsOutputStream;
  protected ConnOption cOption;
  protected boolean dataEOF;
  protected boolean connected;
  public boolean onBreakReset;
  public ClientProfile profile;
  public Ano ano;
  public boolean anoEnabled;
  public boolean isEncryptionActive;
  public boolean isChecksumActive;
  public boolean areEncryptionAndChecksumActive;

  public SessionAtts(int paramInt1, int paramInt2)
  {
    this.sdu = paramInt1;
    this.tdu = paramInt2;
  }

  public void setSDU(int paramInt)
  {
    if (paramInt <= 0)
      this.sdu = 2048;
    else if (paramInt > 32767)
      this.sdu = 32767;
    else if (paramInt < 512)
      this.sdu = 512;
    else
      this.sdu = paramInt;
  }

  public int getSDU()
  {
    return this.sdu;
  }

  public void setTDU(int paramInt)
  {
    if (paramInt <= 0)
      this.tdu = 32767;
    else if (paramInt > 32767)
      this.tdu = 32767;
    else if (paramInt < 255)
      this.tdu = 255;
    else
      this.tdu = paramInt;
  }

  public int getTDU()
  {
    return this.tdu;
  }

  public NTAdapter getNTAdapter()
  {
    return this.nt;
  }

  public void print()
  {
    System.out.println("Session Attributes: ");
    System.out.println("sdu            : " + this.sdu);
    System.out.println("tdu            : " + this.tdu);
    System.out.println("nt             : " + this.nt);
    System.out.println("ntInputStream  : " + this.ntInputStream);
    System.out.println("ntOutputStream : " + this.ntOutputStream);
    System.out.println("nsInputStream  : " + this.nsInputStream);
    System.out.println("nsOutputStream : " + this.nsOutputStream);
    System.out.println("profile        : " + this.profile);
    System.out.println("cOption        : " + this.cOption);
    System.out.println("onBreakReset   : " + this.onBreakReset);
    System.out.println("dataEOF        : " + this.dataEOF);
    System.out.println("connected      : " + this.connected);
  }

  public void turnEncryptionOn(NetInputStream paramNetInputStream, NetOutputStream paramNetOutputStream)
    throws NetException
  {
    if ((paramNetInputStream != null) && (paramNetOutputStream != null))
    {
      this.nsInputStream = paramNetInputStream;
      this.nsOutputStream = paramNetOutputStream;
    }
    else
    {
      throw new NetException(300);
    }
  }

  public int getANOFlags()
  {
    int i = 1;
    if (this.ano != null)
      i = this.ano.getNAFlags();
    return i;
  }

  public OutputStream getOutputStream()
  {
    return this.nsOutputStream;
  }

  public InputStream getInputStream()
  {
    return this.nsInputStream;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.SessionAtts
 * JD-Core Version:    0.6.0
 */