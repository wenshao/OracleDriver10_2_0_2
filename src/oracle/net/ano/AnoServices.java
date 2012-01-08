package oracle.net.ano;

public abstract interface AnoServices
{
  public static final int AUTHENTICATION = 1;
  public static final int MAXSERVICE = 4;
  public static final String[] ENC_CLASSNAME_EX;
  public static final int UB2_TYPE = 3;
  public static final int ARRAY_PACKET_HEADER_LENGTH = 10;
  public static final int STATUS_LENGTH = 2;
  public static final int SERVICE_HEADER_LENGTH = 8;
  public static final int MINSERVICE = 1;
  public static final int REJECTED = 1;
  public static final int MIN_TYPE = 0;
  public static final int REQUESTED = 2;
  public static final int UB1_LENGTH = 1;
  public static final int SUPERVISOR = 4;
  public static final int RELEASE = 1;
  public static final int NA_HEADER_SIZE = 13;
  public static final short NO_ERROR = 0;
  public static final long NA_MAGIC = -559038737L;
  public static final int RAW_TYPE = 1;
  public static final int ENCRYPTION = 2;
  public static final String[] AUTH_CLASSNAME;
  public static final int VERSION_LENGTH = 4;
  public static final String[] DATAINTEGRITY_CLASSNAME;
  public static final String[] ENC_CLASSNAME;
  public static final String[] SERV_CLASSNAME;
  public static final int ACCEPTED = 0;
  public static final int UB4_LENGTH = 4;
  public static final int STRING_TYPE = 0;
  public static final int[] CRYPTO_LEN;
  public static final byte[] ENC_ID;
  public static final int DATAINTEGRITY = 3;
  public static final int STATUS_TYPE = 6;
  public static final boolean VERIFYING = false;
  public static final int NULLSERVICE = 0;
  public static final int NA_MAGIC_SIZE = 4;
  public static final String[] AUTH_NAME;
  public static final byte[] DATAINTEGRITY_ID;
  public static final int VERSION_TYPE = 5;
  public static final int REQUIRED = 3;
  public static final int MAX_TYPE = 7;
  public static final long DEADBEEF = -559038737L;
  public static final String[] SERVICES_INORDER;
  public static final int VERSION = 8;
  public static final int UB4_TYPE = 4;
  public static final int PORT = 0;
  public static final int PORTUPDATE = 0;
  public static final int SUBPACKET_LENGTH = 4;
  public static final int UPDATE = 5;
  public static final int UB2_LENGTH = 2;
  public static final String[] SERVICES = { "NULLSERVICE", "Authentication", "Encryption", "DataIntegrity", "Supervisor" };
  public static final byte[] AUTH_ID;
  public static final int UB1_TYPE = 2;
  public static final int ARRAY_TYPE = 7;
  public static final String[] SERV_INORDER_CLASSNAME;

  static
  {
    SERVICES_INORDER = new String[] { "Supervisor", "Authentication", "Encryption", "DataIntegrity" };
    SERV_CLASSNAME = new String[] { "NULLSERVICE", "AuthenticationService", "EncryptionService", "DataIntegrityService", "SupervisorService" };
    SERV_INORDER_CLASSNAME = new String[] { "SupervisorService", "AuthenticationService", "EncryptionService", "DataIntegrityService" };
    AUTH_ID = new byte[] { 0, 1, 2 };
    AUTH_CLASSNAME = new String[] { "", "", "" };
    AUTH_NAME = new String[] { "null", "auth", "beq" };
    ENC_ID = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    CRYPTO_LEN = new int[] { 0, 40, 56, 40, 0, 0, 256, 56, 56, 40, 128, 112, 168 };
    ENC_CLASSNAME = new String[] { "", "RC4_40", "DES56C", "DES40C", "", "", "RC4_256", "DES56R", "RC4_56", "DES40R", "RC4_128", "3DES112", "3DES168" };
    ENC_CLASSNAME_EX = new String[] { "", "RC4_40", "DES56C", "DES40C", "", "", "RC4_256", "", "RC4_56", "", "RC4_128", "3DES112", "3DES168" };
    DATAINTEGRITY_ID = new byte[] { 0, 1 };
    DATAINTEGRITY_CLASSNAME = new String[] { "", "MD5" };
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ano.AnoServices
 * JD-Core Version:    0.6.0
 */