package oracle.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

public abstract interface OracleCallableStatement extends CallableStatement, OraclePreparedStatement
{
  public abstract ARRAY getARRAY(int paramInt)
    throws SQLException;

  public abstract InputStream getAsciiStream(int paramInt)
    throws SQLException;

  public abstract BFILE getBFILE(int paramInt)
    throws SQLException;

  public abstract InputStream getBinaryStream(int paramInt)
    throws SQLException;

  public abstract BLOB getBLOB(int paramInt)
    throws SQLException;

  public abstract CHAR getCHAR(int paramInt)
    throws SQLException;

  public abstract Reader getCharacterStream(int paramInt)
    throws SQLException;

  public abstract CLOB getCLOB(int paramInt)
    throws SQLException;

  public abstract ResultSet getCursor(int paramInt)
    throws SQLException;

  /** @deprecated */
  public abstract Object getCustomDatum(int paramInt, CustomDatumFactory paramCustomDatumFactory)
    throws SQLException;

  public abstract Object getORAData(int paramInt, ORADataFactory paramORADataFactory)
    throws SQLException;

  public abstract Object getAnyDataEmbeddedObject(int paramInt)
    throws SQLException;

  public abstract DATE getDATE(int paramInt)
    throws SQLException;

  public abstract NUMBER getNUMBER(int paramInt)
    throws SQLException;

  public abstract OPAQUE getOPAQUE(int paramInt)
    throws SQLException;

  public abstract Datum getOracleObject(int paramInt)
    throws SQLException;

  public abstract RAW getRAW(int paramInt)
    throws SQLException;

  public abstract REF getREF(int paramInt)
    throws SQLException;

  public abstract ROWID getROWID(int paramInt)
    throws SQLException;

  public abstract STRUCT getSTRUCT(int paramInt)
    throws SQLException;

  public abstract INTERVALYM getINTERVALYM(int paramInt)
    throws SQLException;

  public abstract INTERVALDS getINTERVALDS(int paramInt)
    throws SQLException;

  public abstract TIMESTAMP getTIMESTAMP(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPTZ getTIMESTAMPTZ(int paramInt)
    throws SQLException;

  public abstract TIMESTAMPLTZ getTIMESTAMPLTZ(int paramInt)
    throws SQLException;

  public abstract InputStream getUnicodeStream(int paramInt)
    throws SQLException;

  public abstract void registerOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  /** @deprecated */
  public abstract void registerOutParameterBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  /** @deprecated */
  public abstract void registerOutParameterChars(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  public abstract int sendBatch()
    throws SQLException;

  public abstract void setExecuteBatch(int paramInt)
    throws SQLException;

  public abstract Object getPlsqlIndexTable(int paramInt)
    throws SQLException;

  public abstract Object getPlsqlIndexTable(int paramInt, Class paramClass)
    throws SQLException;

  public abstract Datum[] getOraclePlsqlIndexTable(int paramInt)
    throws SQLException;

  public abstract void registerIndexTableOutParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException;

  public abstract void setBinaryFloat(String paramString, BINARY_FLOAT paramBINARY_FLOAT)
    throws SQLException;

  public abstract void setBinaryDouble(String paramString, BINARY_DOUBLE paramBINARY_DOUBLE)
    throws SQLException;

  public abstract void setStringForClob(String paramString1, String paramString2)
    throws SQLException;

  public abstract void setBytesForBlob(String paramString, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract void registerOutParameter(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.OracleCallableStatement
 * JD-Core Version:    0.6.0
 */