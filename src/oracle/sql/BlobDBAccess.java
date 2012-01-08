package oracle.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public abstract interface BlobDBAccess
{
  public abstract long length(BLOB paramBLOB)
    throws SQLException;

  public abstract long position(BLOB paramBLOB, byte[] paramArrayOfByte, long paramLong)
    throws SQLException;

  public abstract long position(BLOB paramBLOB1, BLOB paramBLOB2, long paramLong)
    throws SQLException;

  public abstract int getBytes(BLOB paramBLOB, long paramLong, int paramInt, byte[] paramArrayOfByte)
    throws SQLException;

  public abstract int putBytes(BLOB paramBLOB, long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SQLException;

  public abstract int getChunkSize(BLOB paramBLOB)
    throws SQLException;

  public abstract void trim(BLOB paramBLOB, long paramLong)
    throws SQLException;

  public abstract BLOB createTemporaryBlob(Connection paramConnection, boolean paramBoolean, int paramInt)
    throws SQLException;

  public abstract void freeTemporary(BLOB paramBLOB)
    throws SQLException;

  public abstract boolean isTemporary(BLOB paramBLOB)
    throws SQLException;

  public abstract void open(BLOB paramBLOB, int paramInt)
    throws SQLException;

  public abstract void close(BLOB paramBLOB)
    throws SQLException;

  public abstract boolean isOpen(BLOB paramBLOB)
    throws SQLException;

  public abstract InputStream newInputStream(BLOB paramBLOB, int paramInt, long paramLong)
    throws SQLException;

  public abstract OutputStream newOutputStream(BLOB paramBLOB, int paramInt, long paramLong)
    throws SQLException;

  public abstract InputStream newConversionInputStream(BLOB paramBLOB, int paramInt)
    throws SQLException;

  public abstract Reader newConversionReader(BLOB paramBLOB, int paramInt)
    throws SQLException;
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.BlobDBAccess
 * JD-Core Version:    0.6.0
 */