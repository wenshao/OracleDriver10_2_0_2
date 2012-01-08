package oracle.jdbc.xa;

import java.io.Serializable;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public class OracleXid implements Xid, Serializable {
	private int formatId;
	/* 39 */private byte[] gtrid = null;
	/* 40 */private byte[] bqual = null;
	/* 41 */private byte[] txctx = null;
	public static final int MAXGTRIDSIZE = 64;
	public static final int MAXBQUALSIZE = 64;
	private int state;
	/* 319 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	public OracleXid(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws XAException {
		/* 64 */this(paramInt, paramArrayOfByte1, paramArrayOfByte2, null);
	}

	public OracleXid(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3) throws XAException {
		/* 89 */this.formatId = paramInt;

		/* 91 */if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length > 64)) {
			/* 92 */throw new XAException(-4);
		}
		/* 94 */this.gtrid = paramArrayOfByte1;

		/* 96 */if ((paramArrayOfByte2 != null) && (paramArrayOfByte2.length > 64)) {
			/* 97 */throw new XAException(-4);
		}
		/* 99 */this.bqual = paramArrayOfByte2;
		/* 100 */this.txctx = paramArrayOfByte3;
		/* 101 */this.state = 0;
	}

	public void setState(int paramInt) {
		/* 117 */this.state = paramInt;
	}

	public int getState() {
		/* 130 */return this.state;
	}

	public int getFormatId() {
		/* 145 */return this.formatId;
	}

	public byte[] getGlobalTransactionId() {
		/* 161 */return this.gtrid;
	}

	public byte[] getBranchQualifier() {
		/* 176 */return this.bqual;
	}

	public byte[] getTxContext() {
		/* 189 */return this.txctx;
	}

	public void setTxContext(byte[] paramArrayOfByte) {
		/* 220 */this.txctx = paramArrayOfByte;
	}

	public static final boolean isLocalTransaction(Xid paramXid) {
		/* 228 */byte[] arrayOfByte = paramXid.getGlobalTransactionId();

		/* 230 */if (arrayOfByte == null) {
			/* 231 */return true;
		}
		/* 233 */for (int i = 0; i < arrayOfByte.length; i++) {
			/* 235 */if (arrayOfByte[i] != 0) {
				/* 236 */return false;
			}
		}
		/* 239 */return true;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.xa.OracleXid JD-Core
 * Version: 0.6.0
 */