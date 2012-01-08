package oracle.jdbc.xa;

import java.sql.Connection;

import java.sql.SQLException;

import javax.sql.XAConnection;

import javax.transaction.xa.XAException;

import javax.transaction.xa.XAResource;

import oracle.jdbc.pool.OraclePooledConnection;

public abstract class OracleXAConnection extends OraclePooledConnection implements XAConnection {
	/* 35 */protected XAResource xaResource = null;

	/* 116 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	public OracleXAConnection() throws XAException {
		/* 44 */this(null);
	}

	public OracleXAConnection(Connection paramConnection) throws XAException {
		/* 61 */super(paramConnection);
	}

	public abstract XAResource getXAResource();

	public synchronized Connection getConnection() throws SQLException {
		/* 93 */Connection localConnection = super.getConnection();

		/* 95 */if (this.xaResource != null) {
			/* 96 */((OracleXAResource) this.xaResource).setLogicalConnection(localConnection);
		}

		/* 101 */return localConnection;
	}

	boolean getAutoCommit() throws SQLException {
		/* 106 */return this.autoCommit;
	}

	void setAutoCommit(boolean paramBoolean) throws SQLException {
		/* 111 */this.autoCommit = paramBoolean;
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.xa.OracleXAConnection
 * JD-Core Version: 0.6.0
 */