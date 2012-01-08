package oracle.jdbc.connector;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

public class OracleConnectionManager implements ConnectionManager {
	/* 78 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:35_PST_2006";

	public Object allocateConnection(ManagedConnectionFactory paramManagedConnectionFactory, ConnectionRequestInfo paramConnectionRequestInfo) throws ResourceException {
		/* 71 */ManagedConnection localManagedConnection = paramManagedConnectionFactory.createManagedConnection(null, paramConnectionRequestInfo);

		/* 73 */return localManagedConnection.getConnection(null, paramConnectionRequestInfo);
	}
}