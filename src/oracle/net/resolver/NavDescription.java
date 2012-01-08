package oracle.net.resolver;

import java.util.Vector;
import oracle.net.TNSAddress.Description;
import oracle.net.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnOption;
import oracle.net.nt.ConnStrategy;

public class NavDescription extends Description
  implements NavSchemaObject
{
  private Vector activeChildren = new Vector(1, 10);
  private int descProcessed;
  private boolean done = false;

  public NavDescription(SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface)
  {
    super(paramSchemaObjectFactoryInterface);
  }

  public void navigate(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    paramStringBuffer.setLength(0);
    paramStringBuffer.append("(DESCRIPTION=");
    try
    {
      if (this.SDU != null)
        paramConnStrategy.sdu = Integer.parseInt(this.SDU);
      if (this.TDU != null)
        paramConnStrategy.tdu = Integer.parseInt(this.TDU);
    }
    catch (Exception localException)
    {
    }
    paramConnStrategy.addSocketOptions(this.keepAlive);
    int i;
    if (this.sourceRoute)
    {
      if (!backwardCompatibilityCase(this.children, paramConnStrategy))
      {
        this.activeChildren = this.children;
        ((NavSchemaObject)this.activeChildren.elementAt(0)).navigate(paramConnStrategy, paramStringBuffer);
        for (i = 1; i < this.activeChildren.size(); i++)
          ((NavSchemaObject)this.activeChildren.elementAt(i)).addToString(paramConnStrategy);
      }
      setConnectionOption(paramConnStrategy, paramStringBuffer);
      closeNVPair(paramConnStrategy);
    }
    else
    {
      this.activeChildren = NavDescriptionList.setActiveChildren(this.children, this.failover, this.loadBalance);
      for (i = 0; i < this.activeChildren.size(); i++)
        ((NavSchemaObject)this.activeChildren.elementAt(i)).navigate(paramConnStrategy, paramStringBuffer);
      closeNVPair(paramConnStrategy);
    }
    this.done = true;
  }

  public void closeNVPair(ConnStrategy paramConnStrategy)
  {
    for (int i = paramConnStrategy.cOpts.size() - 1; (i >= 0) && (!((ConnOption)paramConnStrategy.cOpts.elementAt(i)).done); i--)
    {
      ConnOption localConnOption = (ConnOption)paramConnStrategy.cOpts.elementAt(i);
      if (this.sourceRoute)
        localConnOption.conn_data.append("(SOURCE_ROUTE=yes)");
      localConnOption.conn_data.append("(CONNECT_DATA=");
      localConnOption.conn_data.append("(CID=(PROGRAM=)(HOST=__jdbc__)(USER=))");
      localConnOption.conn_data.append(this.connectData);
      localConnOption.conn_data.append(")");
      if (this.SID != null)
        localConnOption.sid = this.SID;
      if (this.serviceName != null)
        localConnOption.service_name = this.serviceName;
      if (this.instanceName != null)
        localConnOption.instance_name = this.instanceName;
      if (this.sslServerCertDN != null)
        localConnOption.sslServerCertDN = this.sslServerCertDN;
      localConnOption.conn_data.append(")");
      localConnOption.done = true;
    }
  }

  public void addToString(ConnStrategy paramConnStrategy)
  {
  }

  private boolean backwardCompatibilityCase(Vector paramVector, ConnStrategy paramConnStrategy)
  {
    if ((paramVector.size() == 1) && (((NavSchemaObject)paramVector.elementAt(0)).isA() == 1) && (!((NavAddressList)paramVector.elementAt(0)).sourceRoute))
    {
      NavAddressList localNavAddressList = (NavAddressList)paramVector.elementAt(0);
      int i = localNavAddressList.getChildrenSize();
      if (i == 0)
        return false;
      for (int j = 0; j < i; j++)
        if (localNavAddressList.getChildrenType(j) != 0)
          return false;
      return true;
    }
    return false;
  }

  private void setConnectionOption(ConnStrategy paramConnStrategy, StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("(ADDRESS_LIST=");
    NavAddressList localNavAddressList = (NavAddressList)this.children.elementAt(0);
    NavAddress localNavAddress = localNavAddressList.getChild(0);
    int i = localNavAddressList.getChildrenSize();
    localNavAddress.navigate(paramConnStrategy, paramStringBuffer);
    for (int j = 1; j < i; j++)
      localNavAddressList.getChild(j).addToString(paramConnStrategy);
    ((ConnOption)paramConnStrategy.cOpts.elementAt(0)).conn_data.append(")");
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.resolver.NavDescription
 * JD-Core Version:    0.6.0
 */