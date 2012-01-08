package oracle.net.aso;

import java.io.IOException;

public class C02 extends IOException
{
  public static final int b = 102;
  public static final int c = 104;
  static final int d = 300;
  public static final int e = 103;
  public static final int f = 100;
  public static final int g = 105;
  public static final int h = 101;
  static final int i = 100;
  public static final int j = 106;
  private int k;

  public C02(int paramInt)
  {
    this.k = paramInt;
  }

  public int a()
  {
    return this.k;
  }

  public String getMessage()
  {
    return Integer.toString(this.k);
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C02
 * JD-Core Version:    0.6.0
 */