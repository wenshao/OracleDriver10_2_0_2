package oracle.jdbc.connector;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.Enumeration;

import java.util.Hashtable;

import javax.resource.ResourceException;

import javax.resource.spi.ConnectionEvent;

import javax.resource.spi.ConnectionEventListener;

import javax.resource.spi.ConnectionRequestInfo;

import javax.resource.spi.EISSystemException;

import javax.resource.spi.IllegalStateException;

import javax.resource.spi.LocalTransaction;

import javax.resource.spi.ManagedConnection;

import javax.resource.spi.ManagedConnectionMetaData;

import javax.resource.spi.security.PasswordCredential;

import javax.security.auth.Subject;

import javax.sql.XAConnection;

import javax.transaction.xa.XAResource;

import oracle.jdbc.driver.OracleLog;

import oracle.jdbc.internal.OracleConnection;

import oracle.jdbc.xa.OracleXAConnection;

public class OracleManagedConnection implements ManagedConnection {
	/* 42 */private OracleXAConnection xaConnection = null;
	/* 43 */private Hashtable connectionListeners = null;
	/* 44 */private Connection connection = null;
	/* 45 */private PrintWriter logWriter = null;
	/* 46 */private PasswordCredential passwordCredential = null;
	/* 47 */private OracleLocalTransaction localTxn = null;

	/* 517 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";

	OracleManagedConnection(XAConnection paramXAConnection) {
		/* 58 */this.xaConnection = ((OracleXAConnection) paramXAConnection);
		/* 59 */this.connectionListeners = new Hashtable(10);
	}

	public Object getConnection(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo) throws ResourceException {
		EISSystemException localEISSystemException;
		try {
			/* 94 */if (this.connection != null) {
				/* 95 */this.connection.close();
			}
			/* 97 */this.connection = this.xaConnection.getConnection();

			/* 99 */return this.connection;
		} catch (SQLException localSQLException) {
			/* 103 */localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 106 */localEISSystemException.setLinkedException(localSQLException);
		}
		/* 108 */throw localEISSystemException;
	}

	public void destroy() throws ResourceException {
		try {
			/* 128 */if (this.xaConnection != null) {
				/* 132 */Connection localConnection = this.xaConnection.getPhysicalHandle();

				if (((this.localTxn != null) && (this.localTxn.isBeginCalled)) || (((OracleConnection) localConnection).getTxnMode() == 1)) {
					IllegalStateException localObject = new IllegalStateException("Could not close connection while transaction is active");

					/* 143 */throw ( localObject);
				}
			}

			/* 147 */if (this.connection != null) {
				/* 148 */this.connection.close();
			}
			/* 150 */if (this.xaConnection != null) {
				/* 151 */this.xaConnection.close();
			}

		} catch (SQLException localSQLException) {
			/* 158 */ResourceException localObject = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 161 */((ResourceException) localObject).setLinkedException(localSQLException);

			/* 163 */throw (localObject);
		}
	}

	public void cleanup() throws ResourceException {
		try {
			/* 192 */if (this.connection != null) {
				/* 197 */if (((this.localTxn != null) && (this.localTxn.isBeginCalled)) || (((OracleConnection) this.connection).getTxnMode() == 1)) {
					/* 201 */IllegalStateException localIllegalStateException = new IllegalStateException("Could not close connection while transaction is active");

					/* 204 */throw localIllegalStateException;
				}

				/* 207 */this.connection.close();
			}

		} catch (SQLException localSQLException) {
			/* 215 */EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 218 */localEISSystemException.setLinkedException(localSQLException);

			/* 220 */throw localEISSystemException;
		}
	}

	public void associateConnection(Object paramObject) {
	}

	public void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener) {
		/* 263 */this.connectionListeners.put(paramConnectionEventListener, paramConnectionEventListener);
	}

	public void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener) {
		/* 286 */this.connectionListeners.remove(paramConnectionEventListener);
	}

	public XAResource getXAResource() throws ResourceException {
		/* 309 */return this.xaConnection.getXAResource();
	}

	public LocalTransaction getLocalTransaction() throws ResourceException {
		/* 329 */if (this.localTxn == null) {
			/* 331 */this.localTxn = new OracleLocalTransaction(this);
		}

		/* 334 */return this.localTxn;
	}

	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		/* 353 */return new OracleManagedConnectionMetaData(this);
	}

	public void setLogWriter(PrintWriter paramPrintWriter) throws ResourceException {
		/* 373 */this.logWriter = paramPrintWriter;

		/* 375 */OracleLog.setLogWriter(paramPrintWriter);
	}

	public PrintWriter getLogWriter() throws ResourceException {
		/* 392 */return this.logWriter;
	}

	Connection getPhysicalConnection() throws ResourceException {
		EISSystemException localEISSystemException;
		try {
			/* 406 */return this.xaConnection.getPhysicalHandle();
		} catch (Exception localException) {
			/* 413 */localEISSystemException = new EISSystemException("Exception: " + localException.getMessage());

			/* 416 */localEISSystemException.setLinkedException(localException);
		}
		/* 418 */throw localEISSystemException;
	}

	void setPasswordCredential(PasswordCredential paramPasswordCredential) {
		/* 433 */this.passwordCredential = paramPasswordCredential;
	}

	PasswordCredential getPasswordCredential() {
		/* 444 */return this.passwordCredential;
	}

	void eventOccurred(int paramInt) throws ResourceException {
		/* 456 */Enumeration localEnumeration = this.connectionListeners.keys();

		/* 458 */while (localEnumeration.hasMoreElements()) {
			/* 462 */ConnectionEventListener localConnectionEventListener = (ConnectionEventListener) localEnumeration.nextElement();

			/* 466 */ConnectionEvent localConnectionEvent = new ConnectionEvent(this, paramInt);

			/* 468 */switch (paramInt) {
			case 1:
				/* 475 */localConnectionEventListener.connectionClosed(localConnectionEvent);

				/* 477 */break;
			case 2:
				/* 483 */localConnectionEventListener.localTransactionStarted(localConnectionEvent);

				/* 485 */break;
			case 3:
				/* 488 */localConnectionEventListener.localTransactionCommitted(localConnectionEvent);

				/* 490 */break;
			case 4:
				/* 493 */localConnectionEventListener.localTransactionRolledback(localConnectionEvent);

				/* 495 */break;
			case 5:
				/* 501 */localConnectionEventListener.connectionErrorOccurred(localConnectionEvent);

				/* 503 */break;
			default:
				/* 506 */throw new IllegalArgumentException("Illegal eventType in eventOccurred(): " + paramInt);
			}
		}
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.connector.OracleManagedConnection JD-Core Version: 0.6.0
 */