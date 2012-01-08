package oracle.net.ns;

public class Message10
  implements Message
{
  public String getMessage(int paramInt, String paramString)
  {
    return number2string(paramInt, paramString);
  }

  private String number2string(int paramInt, String paramString)
  {
    String str1 = null;
    if (paramInt > 12000)
      return "Oracle Error: ORA-" + paramInt;
    String str2 = paramString == null ? "" : paramString;
    switch (paramInt)
    {
    case 0:
      str1 = "GOT_MINUS_ONE" + str2;
      break;
    case 1:
      str1 = "ASSERTION_FAILED" + str2;
      break;
    case 20:
      str1 = "NT_CONNECTION_FAILED" + str2;
      break;
    case 21:
      str1 = "INVALID_NT_ADAPTER" + str2;
      break;
    case 100:
      str1 = "PROTOCOL_NOT_SPECIFIED" + str2;
      break;
    case 101:
      str1 = "CSTRING_PARSING" + str2;
      break;
    case 102:
      str1 = "INVALID_CONNECT_DATA" + str2;
      break;
    case 103:
      str1 = "HOSTNAME_NOT_SPECIFIED" + str2;
      break;
    case 104:
      str1 = "PORT_NOT_SPECIFIED" + str2;
      break;
    case 105:
      str1 = "CONNECT_DATA_MISSING" + str2;
      break;
    case 106:
      str1 = "SID_INFORMATION_MISSING" + str2;
      break;
    case 107:
      str1 = "ADDRESS_NOT_DEFINED" + str2;
      break;
    case 108:
      str1 = "JNDI_THREW_EXCEPTION" + str2;
      break;
    case 109:
      str1 = "JNDI_NOT_INITIALIZED" + str2;
      break;
    case 110:
      str1 = "JNDI_CLASSES_NOT_FOUND" + str2;
      break;
    case 111:
      str1 = "USER_PROPERTIES_NOT_DEFINED" + str2;
      break;
    case 112:
      str1 = "NAMING_FACTORY_NOT_DEFINED" + str2;
      break;
    case 113:
      str1 = "NAMING_PROVIDER_NOT_DEFINED" + str2;
      break;
    case 114:
      str1 = "PROFILE_NAME_NOT_DEFINED" + str2;
      break;
    case 115:
      str1 = "HOST_PORT_SID_EXPECTED" + str2;
      break;
    case 116:
      str1 = "PORT_NUMBER_ERROR" + str2;
      break;
    case 117:
      str1 = "EZ_CONNECT_FORMAT_EXPECTED" + str2;
      break;
    case 118:
      str1 = "EZ_CONNECT_UNKNOWN_HOST" + str2;
      break;
    case 121:
      str1 = "INVALID_READ_PATH" + str2;
      break;
    case 119:
      str1 = "TNS_ADMIN_EMPTY" + str2;
      break;
    case 120:
      str1 = "CONNECT_STRING_EMPTY" + str2;
      break;
    case 122:
      str1 = "NAMELOOKUP_FAILED" + str2;
      break;
    case 123:
      str1 = "NAMELOOKUP_FILE_ERROR" + str2;
      break;
    case 124:
      str1 = "INVALID_LDAP_URL" + str2;
      break;
    case 200:
      str1 = "NOT_CONNECTED" + str2;
      break;
    case 201:
      str1 = "CONNECTED_ALREADY" + str2;
      break;
    case 202:
      str1 = "DATA_EOF" + str2;
      break;
    case 203:
      str1 = "SDU_MISMATCH" + str2;
      break;
    case 204:
      str1 = "BAD_PKT_TYPE" + str2;
      break;
    case 205:
      str1 = "UNEXPECTED_PKT" + str2;
      break;
    case 206:
      str1 = "REFUSED_CONNECT" + str2;
      break;
    case 207:
      str1 = "INVALID_PKT_LENGTH" + str2;
      break;
    case 208:
      str1 = "CONNECTION_STRING_NULL" + str2;
      break;
    case 300:
      str1 = "FAILED_TO_TURN_ENCRYPTION_ON" + str2;
      break;
    case 301:
      str1 = "WRONG_BYTES_IN_NAPACKET" + str2;
      break;
    case 302:
      str1 = "WRONG_MAGIC_NUMBER" + str2;
      break;
    case 303:
      str1 = "UNKNOWN_ALGORITHM_12649" + str2;
      break;
    case 304:
      str1 = "INVALID_ENCRYPTION_PARAMETER" + str2;
      break;
    case 305:
      str1 = "WRONG_SERVICE_SUBPACKETS" + str2;
      break;
    case 306:
      str1 = "SUPERVISOR_STATUS_FAILURE" + str2;
      break;
    case 307:
      str1 = "AUTHENTICATION_STATUS_FAILURE" + str2;
      break;
    case 308:
      str1 = "SERVICE_CLASSES_NOT_INSTALLED" + str2;
      break;
    case 309:
      str1 = "INVALID_DRIVER" + str2;
      break;
    case 310:
      str1 = "ARRAY_HEADER_ERROR" + str2;
      break;
    case 311:
      str1 = "RECEIVED_UNEXPECTED_LENGTH_FOR_TYPE" + str2;
      break;
    case 312:
      str1 = "INVALID_NA_PACKET_TYPE_LENGTH" + str2;
      break;
    case 313:
      str1 = "INVALID_NA_PACKET_TYPE" + str2;
      break;
    case 314:
      str1 = "UNEXPECTED_NA_PACKET_TYPE_RECEIVED" + str2;
      break;
    case 315:
      str1 = "UNKNOWN_ENC_OR_DATAINT_ALGORITHM" + str2;
      break;
    case 316:
      str1 = "INVALID_ENCRYPTION_ALGORITHM_FROM_SERVER" + str2;
      break;
    case 317:
      str1 = "ENCRYPTION_CLASS_NOT_INSTALLED" + str2;
      break;
    case 318:
      str1 = "DATAINTEGRITY_CLASS_NOT_INSTALLED" + str2;
      break;
    case 319:
      str1 = "INVALID_DATAINTEGRITY_ALGORITHM_FROM_SERVER" + str2;
      break;
    case 320:
      str1 = "INVALID_SERVICES_FROM_SERVER" + str2;
      break;
    case 321:
      str1 = "INCOMPLETE_SERVICES_FROM_SERVER" + str2;
      break;
    case 322:
      str1 = "INVALID_LEVEL" + str2;
      break;
    case 323:
      str1 = "INVALID_SERVICE" + str2;
      break;
    case 500:
      str1 = "NS_BREAK" + str2;
      break;
    case 501:
      str1 = "NL_EXCEPTION" + str2;
      break;
    case 502:
      str1 = "SO_EXCEPTION" + str2;
      break;
    case 503:
      str1 = "SO_CONNECTTIMEDOUT" + str2;
      break;
    case 504:
      str1 = "SO_READTIMEDOUT" + str2;
      break;
    case 505:
      str1 = "INVALID_CONNECTTIMEOUT" + str2;
      break;
    case 506:
      str1 = "INVALID_READTIMEOUT" + str2;
      break;
    default:
      str1 = "UNDEFINED_ERROR" + str2;
    }
    return str1;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.Message10
 * JD-Core Version:    0.6.0
 */