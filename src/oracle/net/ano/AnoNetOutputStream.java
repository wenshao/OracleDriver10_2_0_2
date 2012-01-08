package oracle.net.ano;

import oracle.net.ns.NetOutputStream;
import oracle.net.ns.SessionAtts;

public class AnoNetOutputStream extends NetOutputStream
{
  public AnoNetOutputStream(SessionAtts paramSessionAtts)
  {
    super(paramSessionAtts);
    this.daPkt = new CryptoDataPacket(paramSessionAtts);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.AnoNetOutputStream
 * JD-Core Version:    0.6.0
 */