package oracle.jdbc.xa.client;

import java.sql.Connection;

import javax.sql.StatementEventListener;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

public class OracleXAConnection extends oracle.jdbc.xa.OracleXAConnection {
	/* 40 */protected boolean isXAResourceTransLoose = false;

	/* 105 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	public OracleXAConnection() throws XAException {
	}

	public OracleXAConnection(Connection paramConnection) throws XAException {
		/* 63 */super(paramConnection);
	}

	public synchronized XAResource getXAResource() {
		try {
			/* 82 */this.xaResource = new OracleXAResource(this.physicalConn, this);
			/* 83 */((OracleXAResource) this.xaResource).isTransLoose = this.isXAResourceTransLoose;

			/* 85 */if (this.logicalHandle != null) {
				/* 89 */((oracle.jdbc.xa.OracleXAResource) this.xaResource).setLogicalConnection(this.logicalHandle);
			}
		} catch (XAException localXAException) {
			/* 94 */this.xaResource = null;
		}

		/* 100 */return this.xaResource;
	}

	@Override
	public void addStatementEventListener(StatementEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeStatementEventListener(StatementEventListener listener) {
		// TODO Auto-generated method stub

	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.xa.client.OracleXAConnection JD-Core Version: 0.6.0
 */