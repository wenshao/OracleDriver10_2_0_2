package oracle.net.resolver;

import oracle.net.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.TNSAddress.ServiceAlias;
import oracle.net.nt.ConnStrategy;

public class NavServiceAlias extends ServiceAlias
  implements NavSchemaObject
{
  public NavServiceAlias(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    ((NavSchemaObject)this.child).navigate(paramConnStrategy, localStringBuffer);
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NavServiceAlias
 * JD-Core Version:    0.6.0
 */