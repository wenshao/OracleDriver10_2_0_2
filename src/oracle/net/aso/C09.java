package oracle.net.aso;

public class C09
{
  private static final int D = 65;

  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, int paramInt)
  {
    char[] arrayOfChar1 = new char[''];
    char[] arrayOfChar2 = new char[''];
    char[] arrayOfChar3 = new char[''];
    int n = e(paramArrayOfChar4, paramInt);
    int k = h(2 * n);
    int m = k / 16;
    int i1 = (n - 2) / 16;
    int i = m - i1 - 3;
    if (i < 0)
      i = 0;
    b(arrayOfChar3, 2 * paramInt + 2);
    A(arrayOfChar3, paramArrayOfChar3, 2 * paramInt);
    y(arrayOfChar2, paramArrayOfChar5, arrayOfChar3, i1, i, paramInt + 2);
    for (int j = 0; j < paramInt; j++)
      paramArrayOfChar1[j] = arrayOfChar2[(j + (m - i1))];
    c(arrayOfChar1, paramArrayOfChar1, paramArrayOfChar4, paramInt);
    f(paramArrayOfChar2, paramArrayOfChar3, arrayOfChar1, 0, paramInt);
    while (v(paramArrayOfChar2, paramArrayOfChar4, paramInt) >= 0)
    {
      f(paramArrayOfChar2, paramArrayOfChar2, paramArrayOfChar4, 0, paramInt);
      q(paramArrayOfChar1, paramInt);
    }
  }

  private static void b(char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
      paramArrayOfChar[(i++)] = '\000';
  }

  private static void c(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    b(paramArrayOfChar1, paramInt);
    int i = u(paramArrayOfChar3, 0, paramInt);
    for (int j = 0; j < paramInt; j++)
      if (i < paramInt - j)
        paramArrayOfChar1[(i + j)] = i(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, i);
      else
        i(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, paramInt - j);
  }

  public static void d(char[] paramArrayOfChar, int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    if (t(paramArrayOfByte, paramInt2) / 16 + 1 > paramInt1);
    int i = paramInt2 - 1;
    int j = 0;
    int k = paramInt1 < paramInt2 / 2 ? paramInt1 : paramInt2 / 2;
    paramInt1 -= k;
    paramInt2 -= 2 * k;
    while (k-- > 0)
    {
      paramArrayOfChar[j] = (char)((0xFF & (char)paramArrayOfByte[i]) + ((0xFF & (char)paramArrayOfByte[(i - 1)]) << '\b'));
      j++;
      i -= 2;
    }
    if ((paramInt1 > 0) && (paramInt2 % 2 == 1))
    {
      paramArrayOfChar[j] = (char)(0xFF & (char)paramArrayOfByte[i]);
      i--;
      j++;
      paramInt1--;
      paramInt2--;
    }
    while (paramInt1-- > 0)
      paramArrayOfChar[(j++)] = '\000';
  }

  private static int e(char[] paramArrayOfChar, int paramInt)
  {
    int i = (char)((paramArrayOfChar[(paramInt - 1)] & 0x8000) > 0 ? -1 : 0);
    for (int j = paramInt - 1; (j >= 0) && (paramArrayOfChar[j] == i); j--);
    if (j == -1)
      return 1;
    int m = 16;
    int k = 32768;
    while ((m >= 0) && (0 == (k & (i ^ paramArrayOfChar[j]))))
    {
      m--;
      k >>>= 1;
    }
    return 16 * j + m;
  }

  private static void f(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2)
  {
    int i = 1;
    for (int j = 0; j < paramInt2; j++)
    {
      i += paramArrayOfChar2[j];
      i += ((paramArrayOfChar3[(j + paramInt1)] ^ 0xFFFFFFFF) & 0xFFFF);
      paramArrayOfChar1[j] = (char)i;
      i >>>= 16;
    }
  }

  private static void g(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < paramInt2; i++)
      paramArrayOfChar[i] = '\000';
    paramArrayOfChar[(paramInt1 / 16)] = (char)(1 << paramInt1 % 16);
  }

  private static int h(int paramInt)
  {
    return 16 * ((paramInt + 1 + 15) / 16);
  }

  private static char i(char[] paramArrayOfChar1, int paramInt1, char paramChar, char[] paramArrayOfChar2, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (paramChar <= 0)
      return '\000';
    int j = paramChar;
    for (int k = 0; k < paramInt3; k++)
    {
      i += j * paramArrayOfChar2[(k + paramInt2)];
      i += paramArrayOfChar1[(k + paramInt1)];
      paramArrayOfChar1[(k + paramInt1)] = (char)i;
      i >>>= 16;
    }
    return (char)i;
  }

  private static void j(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    b(paramArrayOfChar1, 2 * paramInt);
    int i = u(paramArrayOfChar3, 0, paramInt);
    for (int j = 0; j < paramInt; j++)
      paramArrayOfChar1[(i + j)] = i(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0, i);
  }

  public static void k(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar1 = new char[67];
    boolean[] arrayOfBoolean = new boolean[64];
    char[][] arrayOfChar = new char[16][65];
    char[] arrayOfChar2 = new char[65];
    s(arrayOfChar1, paramArrayOfChar4, paramInt);
    int j = e(paramArrayOfChar3, paramInt);
    int k;
    if (j < 4)
      k = 1;
    else if (j < 16)
      k = 2;
    else if (j < 64)
      k = 3;
    else
      k = 4;
    z(arrayOfChar[0], 1, paramInt);
    A(arrayOfChar[1], paramArrayOfChar2, paramInt);
    arrayOfBoolean[0] = true;
    arrayOfBoolean[1] = true;
    for (int m = 2; m < 64; m++)
      arrayOfBoolean[m] = false;
    m = 0;
    int i = 0;
    int n = (char)(1 << j % 16);
    for (int i1 = j; i1 >= 0; i1--)
    {
      if (i != 0)
        m(arrayOfChar2, arrayOfChar2, paramArrayOfChar4, arrayOfChar1, paramInt);
      m <<= 1;
      if (arrayOfBoolean[m] == 0)
      {
        m(arrayOfChar[m], arrayOfChar[(m / 2)], paramArrayOfChar4, arrayOfChar1, paramInt);
        arrayOfBoolean[m] = true;
      }
      if ((paramArrayOfChar3[(i1 / 16)] & n) > 0)
        m += 1;
      if (n == 1)
        n = 32768;
      else
        n = (char)(n >>> 1 & 0x7FFF);
      if (arrayOfBoolean[m] == 0)
      {
        l(arrayOfChar[m], arrayOfChar[(m - 1)], paramArrayOfChar2, paramArrayOfChar4, arrayOfChar1, paramInt);
        arrayOfBoolean[m] = true;
      }
      if ((i1 != 0) && (m < 1 << k - 1))
        continue;
      if (i != 0)
        l(arrayOfChar2, arrayOfChar2, arrayOfChar[m], paramArrayOfChar4, arrayOfChar1, paramInt);
      else
        A(arrayOfChar2, arrayOfChar[m], paramInt);
      m = 0;
      i = 1;
    }
    A(paramArrayOfChar1, arrayOfChar2, paramInt);
  }

  private static void l(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, char[] paramArrayOfChar5, int paramInt)
  {
    char[] arrayOfChar = new char[''];
    j(arrayOfChar, paramArrayOfChar2, paramArrayOfChar3, paramInt);
    p(paramArrayOfChar1, arrayOfChar, paramArrayOfChar4, paramArrayOfChar5, paramInt);
  }

  private static void m(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar = new char[''];
    x(arrayOfChar, paramArrayOfChar2, paramInt);
    p(paramArrayOfChar1, arrayOfChar, paramArrayOfChar3, paramArrayOfChar4, paramInt);
  }

  private static void n(char[] paramArrayOfChar, int paramInt)
  {
    int i = 1;
    for (int j = 0; (j < paramInt - 1) && (i != 0); j++)
    {
      paramArrayOfChar[j] = (char)(paramArrayOfChar[j] - '\001');
      if (paramArrayOfChar[j] == 65535)
        continue;
      i = 0;
    }
    if (i != 0)
      paramArrayOfChar[j] = (char)(paramArrayOfChar[j] - '\001');
  }

  private static int o(int paramInt)
  {
    paramInt -= 1;
    int i = 0;
    while (paramInt > 0)
    {
      i++;
      paramInt >>>= 1;
    }
    return i;
  }

  private static void p(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt)
  {
    char[] arrayOfChar = new char[65];
    a(arrayOfChar, paramArrayOfChar1, paramArrayOfChar2, paramArrayOfChar3, paramArrayOfChar4, paramInt);
  }

  private static void q(char[] paramArrayOfChar, int paramInt)
  {
    int i = 1;
    for (int j = 0; (j < paramInt - 1) && (i != 0); j++)
    {
      paramArrayOfChar[j] = (char)(paramArrayOfChar[j] + '\001');
      if (paramArrayOfChar[j] <= 0)
        continue;
      i = 0;
    }
    if (i != 0)
      paramArrayOfChar[j] = (char)(paramArrayOfChar[j] + '\001');
  }

  public static void r(byte[] paramArrayOfByte, int paramInt1, char[] paramArrayOfChar, int paramInt2)
  {
    int i = paramInt1 - 1;
    int j = 0;
    int k = paramInt2 < paramInt1 / 2 ? paramInt2 : paramInt1 / 2;
    paramInt2 -= k;
    paramInt1 -= 2 * k;
    while (k-- > 0)
    {
      paramArrayOfByte[(i--)] = (byte)(0xFF & (byte)paramArrayOfChar[j]);
      paramArrayOfByte[(i--)] = (byte)(paramArrayOfChar[j] >>> '\b');
      j++;
    }
    if ((paramInt2 > 0) && (paramInt1 % 2 == 1))
    {
      paramArrayOfByte[(i--)] = (byte)(0xFF & (byte)paramArrayOfChar[j]);
      j++;
      paramInt2--;
      paramInt1--;
    }
    while (i-- > 0)
      paramArrayOfByte[(i--)] = 0;
  }

  private static void s(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    char[] arrayOfChar1 = new char[''];
    char[] arrayOfChar2 = new char[''];
    char[] arrayOfChar3 = new char[68];
    int i = e(paramArrayOfChar2, paramInt);
    int j = h(2 * i);
    int k = j / 16;
    int m = (i - 2) / 16;
    g(paramArrayOfChar1, j - i, paramInt + 2);
    q(paramArrayOfChar1, paramInt + 2);
    b(arrayOfChar3, paramInt + 3);
    A(arrayOfChar3, paramArrayOfChar2, paramInt);
    for (int n = 1 + o(j - i + 1); n > 0; n--)
    {
      x(arrayOfChar1, paramArrayOfChar1, paramInt + 2);
      w(arrayOfChar2, arrayOfChar3, arrayOfChar1, m, paramInt + 3);
      C(paramArrayOfChar1, paramArrayOfChar1, paramArrayOfChar1, paramInt + 2);
      f(paramArrayOfChar1, paramArrayOfChar1, arrayOfChar2, k - m, paramInt + 2);
    }
    q(paramArrayOfChar1, paramInt + 2);
    while (true)
    {
      j(arrayOfChar1, paramArrayOfChar1, arrayOfChar3, paramInt + 2);
      n(arrayOfChar1, 2 * (paramInt + 2));
      if (e(arrayOfChar1, 2 * (paramInt + 2)) <= j)
        break;
      n(paramArrayOfChar1, paramInt + 2);
    }
  }

  private static int t(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; (i < paramInt) && (paramArrayOfByte[i] == 0); i++);
    if (i == paramInt)
      return 0;
    int j = paramArrayOfByte[(i++)];
    int m = 8;
    for (int k = -128; (j & k) == 0; k = (byte)(k >>> 1))
      m--;
    return 8 * (paramInt - i) + m;
  }

  private static int u(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    for (int i = paramInt2 - 1; i >= 0; i--)
      if (paramArrayOfChar[(i + paramInt1)] > 0)
        return i + 1;
    return 0;
  }

  private static int v(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    int i = B(paramArrayOfChar1, paramInt);
    int j = B(paramArrayOfChar2, paramInt);
    if (i > j)
      return 1;
    if (i < j)
      return -1;
    for (int k = paramInt - 1; (k >= 0) && (paramArrayOfChar1[k] == paramArrayOfChar2[k]); k--);
    if (k == -1)
      return 0;
    if (paramArrayOfChar1[k] > paramArrayOfChar2[k])
      return 1;
    return -1;
  }

  private static void w(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2)
  {
    b(paramArrayOfChar1, 2 * paramInt2);
    int i = u(paramArrayOfChar3, 0 + paramInt1, paramInt2);
    for (int j = 0; j < paramInt2; j++)
      paramArrayOfChar1[(i + j)] = i(paramArrayOfChar1, j, paramArrayOfChar2[j], paramArrayOfChar3, 0 + paramInt1, i);
  }

  private static void x(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    int i = 0;
    b(paramArrayOfChar1, 2 * paramInt);
    int j = u(paramArrayOfChar2, 0, paramInt);
    if (j <= 0)
      return;
    for (int k = 0; k < j - 1; k++)
      paramArrayOfChar1[(j + k)] = i(paramArrayOfChar1, 2 * k + 1, paramArrayOfChar2[k], paramArrayOfChar2, k + 1, j - k - 1);
    C(paramArrayOfChar1, paramArrayOfChar1, paramArrayOfChar1, 2 * paramInt);
    for (k = 0; k < j; k++)
    {
      i += paramArrayOfChar2[k] * paramArrayOfChar2[k];
      i += paramArrayOfChar1[(2 * k)];
      paramArrayOfChar1[(2 * k)] = (char)i;
      i >>>= 16;
      i += paramArrayOfChar1[(2 * k + 1)];
      paramArrayOfChar1[(2 * k + 1)] = (char)i;
      i >>>= 16;
    }
    paramArrayOfChar1[(2 * k)] = (char)i;
  }

  private static void y(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt1, int paramInt2, int paramInt3)
  {
    b(paramArrayOfChar1, 2 * paramInt3);
    int j = u(paramArrayOfChar3, paramInt1, paramInt3);
    int k = paramInt2 >= paramInt3 - 1 ? paramInt2 - (paramInt3 - 1) : 0;
    for (int m = k; m < paramInt3; m++)
    {
      int i = paramInt2 >= m ? paramInt2 - m : 0;
      paramArrayOfChar1[(j + m)] = i(paramArrayOfChar1, m + i, paramArrayOfChar2[m], paramArrayOfChar3, i + paramInt1, j >= i ? j - i : 0);
    }
  }

  private static void z(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = (char)((paramInt1 & 0x8000) > 0 ? -1 : 0);
    paramArrayOfChar[0] = (char)paramInt1;
    for (int j = 1; j < paramInt2; j++)
      paramArrayOfChar[j] = i;
  }

  private static void A(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      paramArrayOfChar1[i] = paramArrayOfChar2[i];
  }

  private static int B(char[] paramArrayOfChar, int paramInt)
  {
    if ((paramArrayOfChar[(paramInt - 1)] & 0x8000) > 0)
      return -1;
    for (int i = paramInt - 1; i >= 0; i--)
      if (paramArrayOfChar[i] > 0)
        return 1;
    return 0;
  }

  private static void C(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j++)
    {
      i += paramArrayOfChar2[j];
      i += paramArrayOfChar3[j];
      paramArrayOfChar1[j] = (char)i;
      i >>>= 16;
    }
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.aso.C09
 * JD-Core Version:    0.6.0
 */