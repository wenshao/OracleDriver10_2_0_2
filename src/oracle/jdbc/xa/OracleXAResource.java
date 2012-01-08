package oracle.jdbc.xa;

import java.sql.Connection;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.Vector;

import javax.transaction.xa.XAException;

import javax.transaction.xa.XAResource;

import javax.transaction.xa.Xid;

import oracle.jdbc.internal.OracleConnection;

public abstract class OracleXAResource implements XAResource {
	public static final int XA_OK = 0;
	public static final short DEFAULT_XA_TIMEOUT = 60;
	/* 41 */protected boolean savedConnectionAutoCommit = false;
	/* 42 */protected boolean savedXAConnectionAutoCommit = false;
	public static final int TMNOFLAGS = 0;
	public static final int TMNOMIGRATE = 2;
	public static final int TMENDRSCAN = 8388608;
	public static final int TMFAIL = 536870912;
	public static final int TMMIGRATE = 1048576;
	public static final int TMJOIN = 2097152;
	public static final int TMONEPHASE = 1073741824;
	public static final int TMRESUME = 134217728;
	public static final int TMSTARTRSCAN = 16777216;
	public static final int TMSUCCESS = 67108864;
	public static final int TMSUSPEND = 33554432;
	public static final int ORATMREADONLY = 256;
	public static final int ORATMREADWRITE = 512;
	public static final int ORATMSERIALIZABLE = 1024;
	public static final int ORAISOLATIONMASK = 65280;
	public static final int ORATRANSLOOSE = 65536;
	/* 81 */protected Connection connection = null;
	/* 82 */protected OracleXAConnection xaconnection = null;
	/* 83 */protected int timeout = 60;
	/* 84 */protected String dblink = null;

	/* 87 */private Connection logicalConnection = null;

	/* 93 */private String synchronizeBeforeRecover = "BEGIN sys.dbms_system.dist_txn_sync(0); END;";

	/* 96 */private String recoverySqlRows = "SELECT formatid, globalid, branchid FROM SYS.DBA_PENDING_TRANSACTIONS";

	/* 101 */protected Vector locallySuspendedTransactions = new Vector();

	/* 106 */protected boolean canBeMigratablySuspended = false;

	/* 185 */protected Xid currentStackedXid = null;

	/* 943 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";

	public OracleXAResource() {
	}

	public OracleXAResource(Connection paramConnection, OracleXAConnection paramOracleXAConnection) throws XAException {
		/* 129 */this.connection = paramConnection;
		/* 130 */this.xaconnection = paramOracleXAConnection;

		/* 132 */if (this.connection == null)
			/* 133 */throw new XAException(-7);
	}

	public synchronized void setConnection(Connection paramConnection) throws XAException {
		/* 147 */this.connection = paramConnection;

		/* 149 */if (this.connection == null)
			/* 150 */throw new XAException(-7);
	}

	protected void push(Xid paramXid) {
		/* 200 */this.currentStackedXid = paramXid;

		/* 204 */enterGlobalTxnMode();
	}

	protected void pop() {
		/* 219 */this.currentStackedXid = null;

		/* 224 */exitGlobalTxnMode();
	}

	protected Xid suspendStacked(Xid paramXid) throws XAException {
		/* 249 */Xid localXid = null;

		/* 251 */if ((this.currentStackedXid != null) && (this.currentStackedXid != paramXid)) {
			/* 256 */localXid = this.currentStackedXid;

			/* 262 */end(localXid, 33554432);
		}

		/* 269 */return localXid;
	}

	protected Xid suspendStacked(Xid paramXid, int paramInt) throws XAException {
		/* 294 */Xid localXid = null;

		/* 297 */allowGlobalTxnModeOnly(-3);

		/* 299 */if ((paramInt == 67108864) && (this.currentStackedXid != null) && (paramXid != this.currentStackedXid)) {
			/* 307 */localXid = this.currentStackedXid;

			/* 313 */end(localXid, 33554432);

			/* 318 */start(paramXid, 134217728);
		}

		/* 325 */return localXid;
	}

	protected void resumeStacked(Xid paramXid) throws XAException {
		/* 346 */if (paramXid != null) {
			/* 352 */start(paramXid, 134217728);
		}
	}

	public abstract void start(Xid paramXid, int paramInt) throws XAException;

	public abstract void end(Xid paramXid, int paramInt) throws XAException;

	public abstract void commit(Xid paramXid, boolean paramBoolean) throws XAException;

	public abstract int prepare(Xid paramXid) throws XAException;

	public abstract void forget(Xid paramXid) throws XAException;

	public abstract void rollback(Xid paramXid) throws XAException;

	public Xid[] recover(int paramInt) throws XAException {
		/* 490 */if ((paramInt & 0x1800000) != paramInt) {
			/* 496 */throw new XAException(-5);
		}

		/* 499 */Statement localStatement = null;
		/* 500 */ResultSet localResultSet = null;
		/* 501 */ArrayList localArrayList = new ArrayList(50);
		try {
			/* 505 */localStatement = this.connection.createStatement();
			/* 506 */localStatement.execute(this.synchronizeBeforeRecover);

			/* 508 */localResultSet = localStatement.executeQuery(this.recoverySqlRows);

			/* 510 */while (localResultSet.next()) {
				/* 512 */localArrayList.add(new OracleXid(localResultSet.getInt(1), localResultSet.getBytes(2), localResultSet.getBytes(3)));
			}

		} catch (SQLException localSQLException) {
			/* 526 */throw new XAException(-3);
		} finally {
			try {
				/* 532 */if (localStatement != null) {
					/* 533 */localStatement.close();
				}
				/* 535 */if (localResultSet != null)
					/* 536 */localResultSet.close();
			} catch (Exception localException) {
			}
		}
		/* 541 */int i = localArrayList.size();
		/* 542 */Xid[] arrayOfXid = new Xid[i];
		/* 543 */System.arraycopy(localArrayList.toArray(), 0, arrayOfXid, 0, i);

		/* 548 */return arrayOfXid;
	}

	protected void restoreAutoCommitModeForGlobalTransaction() throws XAException {
		/* 563 */if ((this.savedConnectionAutoCommit) && (((OracleConnection) this.connection).getTxnMode() != 1)) {
			try {
				/* 569 */this.connection.setAutoCommit(this.savedConnectionAutoCommit);
				/* 570 */this.xaconnection.setAutoCommit(this.savedXAConnectionAutoCommit);
			} catch (SQLException localSQLException) {
			}
		}
	}

	protected void saveAndAlterAutoCommitModeForGlobalTransaction() throws XAException {
		try {
			/* 591 */this.savedConnectionAutoCommit = this.connection.getAutoCommit();
			/* 592 */this.connection.setAutoCommit(false);
			/* 593 */this.savedXAConnectionAutoCommit = this.xaconnection.getAutoCommit();
			/* 594 */this.xaconnection.setAutoCommit(false);
		} catch (SQLException localSQLException) {
		}
	}

	public void resume(Xid paramXid) throws XAException {
		/* 616 */start(paramXid, 134217728);
	}

	public void join(Xid paramXid) throws XAException {
		/* 633 */start(paramXid, 2097152);
	}

	public void suspend(Xid paramXid) throws XAException {
		/* 649 */end(paramXid, 33554432);
	}

	public void join(Xid paramXid, int paramInt) throws XAException {
		/* 667 */this.timeout = paramInt;

		/* 669 */start(paramXid, 2097152);
	}

	public void resume(Xid paramXid, int paramInt) throws XAException {
		/* 687 */this.timeout = paramInt;

		/* 689 */start(paramXid, 134217728);
	}

	public Connection getConnection() {
		/* 707 */return this.connection;
	}

	public int getTransactionTimeout() throws XAException {
		/* 730 */return this.timeout;
	}

	public boolean isSameRM(XAResource paramXAResource) throws XAException {
		/* 752 */Connection localConnection = null;

		/* 755 */if ((paramXAResource instanceof OracleXAResource)) {
			/* 756 */localConnection = ((OracleXAResource) paramXAResource).getConnection();
		} else {
			/* 763 */return false;
		}

		try {
			/* 768 */String str1 = ((OracleConnection) this.connection).getURL();
			/* 769 */String str2 = ((OracleConnection) this.connection).getProtocolType();

			/* 771 */if (localConnection != null) {
				/* 773 */int i = (localConnection.equals(this.connection)) || (((OracleConnection) localConnection).getURL().equals(str1))
						|| ((((OracleConnection) localConnection).getProtocolType().equals(str2)) && (str2.equals("kprb"))) ? 1 : 0;

				/* 782 */return i != 0;
			}

		} catch (SQLException localSQLException) {
			/* 794 */throw new XAException(-3);
		}

		/* 803 */return false;
	}

	public boolean setTransactionTimeout(int paramInt) throws XAException {
		/* 827 */if (paramInt < 0) {
			/* 828 */throw new XAException(-5);
		}
		/* 830 */this.timeout = paramInt;

		/* 835 */return true;
	}

	public String getDBLink() {
		/* 853 */return this.dblink;
	}

	public void setDBLink(String paramString) {
		/* 869 */this.dblink = paramString;
	}

	public void setLogicalConnection(Connection paramConnection) {
		/* 884 */this.logicalConnection = paramConnection;
	}

	protected void allowGlobalTxnModeOnly(int paramInt) throws XAException {
		/* 910 */if (((OracleConnection) this.connection).getTxnMode() != 1) {
			/* 912 */throw new XAException(paramInt);
		}
	}

	protected void exitGlobalTxnMode() {
		/* 920 */((OracleConnection) this.connection).setTxnMode(0);
	}

	protected void enterGlobalTxnMode() {
		/* 928 */((OracleConnection) this.connection).setTxnMode(1);
	}

	protected void checkError(int paramInt) throws OracleXAException {
		/* 937 */if ((paramInt & 0xFFFF) != 0)
			/* 938 */throw new OracleXAException(paramInt);
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.xa.OracleXAResource
 * JD-Core Version: 0.6.0
 */