package oracle.net.resolver;

import java.util.Vector;
import oracle.net.TNSAddress.AddressList;
import oracle.net.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnOption;
import oracle.net.nt.ConnStrategy;

public class NavAddressList extends AddressList
  implements NavSchemaObject
{
  private Vector activeChildren = new Vector(1, 10);
  private int sBuflength;

  public NavAddressList(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    this.sBuflength = paramStringBuffer.length();
    paramStringBuffer.append("(ADDRESS_LIST=");
    if (this.sourceRoute)
    {
      this.activeChildren = this.children;
      ((NavSchemaObject)this.activeChildren.elementAt(0)).navigate(paramConnStrategy, paramStringBuffer);
      for (i = 1; i < this.activeChildren.size(); i++)
        ((NavSchemaObject)this.activeChildren.elementAt(i)).addToString(paramConnStrategy);
    }
    this.activeChildren = NavDescriptionList.setActiveChildren(this.children, this.failover, this.loadBalance);
    for (int i = 0; i < this.activeChildren.size(); i++)
      ((NavSchemaObject)this.activeChildren.elementAt(i)).navigate(paramConnStrategy, paramStringBuffer);
    closeNVPair(paramConnStrategy);
    paramStringBuffer.setLength(this.sBuflength);
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
    String str = toString();
    for (int i = paramConnStrategy.cOpts.size() - 1; (i >= 0) && (!((ConnOption)paramConnStrategy.cOpts.elementAt(i)).done); i--)
      ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append(str);
  }

  public String toString()
  {
    String str = new String("");
    str = str + "(ADDRESS_LIST=";
    for (int i = 0; i < this.children.size(); i++)
      str = str + ((NavSchemaObject)this.children.elementAt(i)).toString();
    if (this.sourceRoute)
      str = str + "(SOURCE_ROUTE=yes)(HOP_COUNT=0)";
    if (this.loadBalance)
      str = str + "(LOAD_BALANCE=yes)";
    if (!this.failover)
      str = str + "(FAILOVER=false)";
    str = str + ")";
    return str;
  }

  public int getChildrenSize()
  {
    return this.children.size();
  }

  public int getChildrenType(int paramInt)
  {
    return ((NavSchemaObject)this.children.elementAt(paramInt)).isA();
  }

  public NavAddress getChild(int paramInt)
  {
    return (NavAddress)this.children.elementAt(paramInt);
  }

  private void closeNVPair(ConnStrategy paramConnStrategy)
  {
    for (int i = paramConnStrategy.cOpts.size() - 1; (i >= 0) && (!((ConnOption)paramConnStrategy.cOpts.elementAt(i)).done); i--)
    {
      if (this.sourceRoute)
      {
        ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append("(SOURCE_ROUTE=yes)");
        ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append("(HOP_COUNT=0)");
      }
      ((ConnOption)paramConnStrategy.cOpts.elementAt(i)).conn_data.append(")");
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NavAddressList
 * JD-Core Version:    0.6.0
 */