package oracle.net.TNSAddress;

public class SOException extends Throwable
{
  public String error = null;

  public SOException()
  {
  }

  public SOException(String paramString)
  {
    super(paramString);
    this.error = paramString;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.TNSAddress.SOException
 * JD-Core Version:    0.6.0
 */