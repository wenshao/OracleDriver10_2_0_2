package oracle.jdbc.internal;

public abstract interface ClientDataSupport
{
  public abstract Object getClientData(Object paramObject);

  public abstract Object setClientData(Object paramObject1, Object paramObject2);

  public abstract Object removeClientData(Object paramObject);
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.internal.ClientDataSupport
 * JD-Core Version:    0.6.0
 */