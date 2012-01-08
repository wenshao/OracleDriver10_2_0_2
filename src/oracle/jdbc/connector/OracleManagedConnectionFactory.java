package oracle.jdbc.connector;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class OracleManagedConnectionFactory implements ManagedConnectionFactory {
	/* 45 */private XADataSource xaDataSource = null;
	/* 46 */private String xaDataSourceName = null;
	private static final String RAERR_MCF_SET_XADS = "invalid xads";
	private static final String RAERR_MCF_GET_PCRED = "no password credential";
	/* 488 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";

	public OracleManagedConnectionFactory() throws ResourceException {
	}

	public OracleManagedConnectionFactory(XADataSource paramXADataSource) throws ResourceException {
		/* 72 */this.xaDataSource = paramXADataSource;
		/* 73 */this.xaDataSourceName = "XADataSource";
	}

	public void setXADataSourceName(String paramString) {
		/* 84 */this.xaDataSourceName = paramString;
	}

	public String getXADataSourceName() {
		/* 95 */return this.xaDataSourceName;
	}

	public Object createConnectionFactory(ConnectionManager paramConnectionManager) throws ResourceException {
		/* 118 */if (this.xaDataSource == null) {
			/* 120 */setupXADataSource();
		}

		/* 123 */return (DataSource) this.xaDataSource;
	}

	public Object createConnectionFactory() throws ResourceException {
		/* 144 */return createConnectionFactory(null);
	}

	public ManagedConnection createManagedConnection(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo) throws ResourceException {
		Object localObject;
		try {
			/* 172 */if (this.xaDataSource == null) {
				/* 174 */setupXADataSource();
			}

			/* 177 */XAConnection localXAConnection = null;
			/* 178 */localObject = getPasswordCredential(paramSubject, paramConnectionRequestInfo);

			/* 180 */if (localObject == null) {
				/* 182 */localXAConnection = this.xaDataSource.getXAConnection();
			} else {
				/* 186 */localXAConnection = this.xaDataSource.getXAConnection(((PasswordCredential) localObject).getUserName(),
						new String(((PasswordCredential) localObject).getPassword()));
			}

			/* 190 */OracleManagedConnection localOracleManagedConnection = new OracleManagedConnection(localXAConnection);

			/* 192 */localOracleManagedConnection.setPasswordCredential((PasswordCredential) localObject);

			/* 195 */localOracleManagedConnection.setLogWriter(getLogWriter());

			/* 197 */return localOracleManagedConnection;
		} catch (SQLException localSQLException) {
			/* 201 */EISSystemException e = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 204 */((ResourceException) e).setLinkedException(localSQLException);
			/* 206 */throw (e);
		}

	}

	public ManagedConnection matchManagedConnections(Set paramSet, Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo) throws ResourceException {
		/* 238 */PasswordCredential localPasswordCredential = getPasswordCredential(paramSubject, paramConnectionRequestInfo);
		/* 239 */Iterator localIterator = paramSet.iterator();

		/* 241 */while (localIterator.hasNext()) {
			/* 243 */Object localObject = localIterator.next();

			/* 245 */if (!(localObject instanceof OracleManagedConnection))
				continue;
			/* 247 */OracleManagedConnection localOracleManagedConnection = (OracleManagedConnection) localObject;

			/* 250 */if (localOracleManagedConnection.getPasswordCredential().equals(localPasswordCredential)) {
				/* 252 */return localOracleManagedConnection;
			}

		}

		/* 257 */return null;
	}

	public void setLogWriter(PrintWriter paramPrintWriter) throws ResourceException {
		try {
			/* 282 */if (this.xaDataSource == null) {
				/* 284 */setupXADataSource();
			}

			/* 287 */this.xaDataSource.setLogWriter(paramPrintWriter);
		} catch (SQLException localSQLException) {
			/* 295 */EISSystemException localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 298 */localEISSystemException.setLinkedException(localSQLException);

			/* 300 */throw localEISSystemException;
		}
	}

	public PrintWriter getLogWriter() throws ResourceException {
		EISSystemException localEISSystemException;
		try {
			/* 324 */if (this.xaDataSource == null) {
				/* 326 */setupXADataSource();
			}

			/* 329 */return this.xaDataSource.getLogWriter();
		} catch (SQLException localSQLException) {
			/* 337 */localEISSystemException = new EISSystemException("SQLException: " + localSQLException.getMessage());

			/* 340 */localEISSystemException.setLinkedException(localSQLException);
		}
		/* 342 */throw localEISSystemException;
	}

	private void setupXADataSource() throws ResourceException {
		try {
			/* 393 */InitialContext localInitialContext = null;
			try {
				/* 397 */Properties localProperties = System.getProperties();

				/* 399 */localInitialContext = new InitialContext(localProperties);
			} catch (java.lang.SecurityException localSecurityException) {
			}

			/* 405 */if (localInitialContext == null) {
				/* 407 */localInitialContext = new InitialContext();
			}

			XADataSource localObject = (XADataSource) localInitialContext.lookup(this.xaDataSourceName);

			/* 412 */if (localObject == null) {
				/* 414 */throw new ResourceAdapterInternalException("Invalid XADataSource object");
			}

			/* 417 */this.xaDataSource = ((XADataSource) localObject);
		} catch (NamingException localNamingException) {
			/* 421 */ResourceException localObject = new ResourceException("NamingException: " + localNamingException.getMessage());

			/* 424 */((ResourceException) localObject).setLinkedException(localNamingException);

			/* 426 */throw (localObject);
		}
	}

	private PasswordCredential getPasswordCredential(Subject paramSubject, ConnectionRequestInfo paramConnectionRequestInfo) throws ResourceException {
		/* 443 */if (paramSubject != null) {
			/* 447 */Set<PasswordCredential> localObject1 = paramSubject.getPrivateCredentials(PasswordCredential.class);
			/* 448 */Iterator<PasswordCredential> localObject2 = ((Set) localObject1).iterator();

			/* 450 */while (((Iterator) localObject2).hasNext()) {
				/* 452 */PasswordCredential localPasswordCredential = (PasswordCredential) ((Iterator) localObject2).next();

				/* 454 */if (localPasswordCredential.getManagedConnectionFactory().equals(this)) {
					/* 456 */return localPasswordCredential;
				}
			}

			/* 460 */throw new javax.resource.spi.SecurityException("Can not find user/password information", "no password credential");
		}

		/* 465 */if (paramConnectionRequestInfo == null) {
			/* 467 */return null;
		}

		/* 471 */Object localObject1 = (OracleConnectionRequestInfo) paramConnectionRequestInfo;

		/* 473 */Object localObject2 = new PasswordCredential(((OracleConnectionRequestInfo) localObject1).getUser(), ((OracleConnectionRequestInfo) localObject1).getPassword()
				.toCharArray());

		/* 476 */((PasswordCredential) localObject2).setManagedConnectionFactory(this);

		/* 478 */return (PasswordCredential) (PasswordCredential) localObject2;
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.connector.OracleManagedConnectionFactory JD-Core Version: 0.6.0
 */