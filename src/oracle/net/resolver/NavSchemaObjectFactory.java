package oracle.net.resolver;

import oracle.net.TNSAddress.SchemaObject;
import oracle.net.TNSAddress.SchemaObjectFactoryInterface;

public class NavSchemaObjectFactory
  implements SchemaObjectFactoryInterface
{
  public SchemaObject create(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      return new NavAddress(this);
    case 1:
      return new NavAddressList(this);
    case 2:
      return new NavDescription(this);
    case 3:
      return new NavDescriptionList(this);
    case 4:
      return new NavServiceAlias(this);
    }
    return null;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NavSchemaObjectFactory
 * JD-Core Version:    0.6.0
 */