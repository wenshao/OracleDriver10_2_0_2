package oracle.net.TNSAddress;

import oracle.net.nl.NLException;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVNavigator;
import oracle.net.nl.NVPair;

public class Address
  implements SchemaObject
{
  public String addr;
  public String prot;
  protected SchemaObjectFactoryInterface f = null;

  public Address(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    this.f = paramSchemaObjectFactoryInterface;
  }

  public int isA()
  {
    return 0;
  }

  public String isA_String()
  {
    return "ADDRESS";
  }

  public void initFromString(String paramString)
    throws NLException, SOException
  {
    NVPair localNVPair = new NVFactory().createNVPair(paramString);
    initFromNVPair(localNVPair);
  }

  public void initFromNVPair(NVPair paramNVPair)
    throws SOException
  {
    init();
    if ((paramNVPair == null) || (!paramNVPair.getName().equalsIgnoreCase("address")))
      throw new SOException();
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair = localNVNavigator.findNVPair(paramNVPair, "PROTOCOL");
    if (localNVPair == null)
      throw new SOException();
    this.prot = localNVPair.getAtom();
    if (this.addr == null)
      this.addr = paramNVPair.toString();
  }

  public String toString()
  {
    return this.addr;
  }

  public String getProtocol()
  {
    return this.prot;
  }

  protected void init()
  {
    this.addr = null;
    this.prot = null;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.TNSAddress.Address
 * JD-Core Version:    0.6.0
 */