package oracle.jdbc;

import java.io.IOException;

public abstract interface OracleResultSetCache
{
  public abstract void put(int paramInt1, int paramInt2, Object paramObject)
    throws IOException;

  public abstract Object get(int paramInt1, int paramInt2)
    throws IOException;

  public abstract void remove(int paramInt)
    throws IOException;

  public abstract void remove(int paramInt1, int paramInt2)
    throws IOException;

  public abstract void clear()
    throws IOException;

  public abstract void close()
    throws IOException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleResultSetCache
 * JD-Core Version:    0.6.0
 */