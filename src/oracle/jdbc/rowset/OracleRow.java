/*     */package oracle.jdbc.rowset;

/*     */
/*     */import java.io.DataInputStream;
/*     */
import java.io.DataOutputStream;
/*     */
import java.io.InputStream;
/*     */
import java.io.OutputStream;
/*     */
import java.io.Serializable;
/*     */
import java.math.BigDecimal;
/*     */
import java.sql.Date;
/*     */
import java.sql.SQLException;
/*     */
import java.sql.Timestamp;
/*     */
import java.util.Collection;
/*     */
import java.util.Vector;

/*     */
/*     */public class OracleRow
/*     */implements Serializable, Cloneable
/*     */{
	/*     */private Object[] column;
	/*     */private Object[] changedColumn;
	/*     */private boolean[] isOriginalNull;
	/*     */private byte[] columnChangeFlag;
	/* 75 */private int noColumn = 0;
	/*     */private int noColumnsInserted;
	/* 86 */private boolean rowDeleted = false;
	/*     */
	/* 91 */private boolean rowInserted = false;
	/*     */
	/* 96 */private final byte COLUMN_CHANGED = 17;
	/*     */
	/* 101 */private boolean rowUpdated = false;

	/*     */
	/*     */public OracleRow(int paramInt)
	/*     */{
		/* 125 */this.noColumn = paramInt;
		/* 126 */this.column = new Object[paramInt];
		/* 127 */this.changedColumn = new Object[paramInt];
		/* 128 */this.columnChangeFlag = new byte[paramInt];
		/* 129 */this.isOriginalNull = new boolean[paramInt];
		/* 130 */for (int i = 0; i < paramInt; i++)
			/* 131 */this.columnChangeFlag[i] = 0;
		/*     */}

	/*     */
	/*     */public OracleRow(int paramInt, boolean paramBoolean)
	/*     */{
		/* 143 */this(paramInt);
		/*     */
		/* 160 */this.rowInserted = paramBoolean;
		/* 161 */this.noColumnsInserted = 0;
		/*     */}

	/*     */
	/*     */public OracleRow(int paramInt, Object[] paramArrayOfObject)
	/*     */{
		/* 173 */this(paramInt);
		/*     */
		/* 196 */System.arraycopy(paramArrayOfObject, 0, this.column, 0,
				paramInt);
		/*     */}

	/*     */
	/*     */public void setColumnValue(int paramInt, Object paramObject)
	/*     */{
		/* 223 */if (this.rowInserted)
			/* 224 */this.noColumnsInserted += 1;
		/* 225 */this.column[(paramInt - 1)] = paramObject;
		/*     */}

	/*     */
	/*     */void markOriginalNull(int paramInt, boolean paramBoolean)
	/*     */throws SQLException
	/*     */{
		/* 246 */this.isOriginalNull[(paramInt - 1)] = paramBoolean;
		/*     */}

	/*     */
	/*     */boolean isOriginalNull(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 267 */return this.isOriginalNull[(paramInt - 1)];
		/*     */}

	/*     */
	/*     */public void updateObject(int paramInt, Object paramObject)
	/*     */{
		/* 295 */if (this.rowInserted)
			/* 296 */this.noColumnsInserted += 1;
		/* 297 */this.columnChangeFlag[(paramInt - 1)] = 17;
		/* 298 */this.changedColumn[(paramInt - 1)] = paramObject;
		/*     */}

	/*     */
	/*     */public void cancelRowUpdates()
	/*     */{
		/* 320 */this.noColumnsInserted = 0;
		/* 321 */for (int i = 0; i < this.noColumn; i++)
			/* 322 */this.columnChangeFlag[i] = 0;
		/* 323 */this.changedColumn = null;
		/* 324 */this.changedColumn = new Object[this.noColumn];
		/*     */}

	/*     */
	/*     */public Object getColumn(int paramInt)
	/*     */{
		/* 350 */return this.column[(paramInt - 1)];
		/*     */}

	/*     */
	/*     */public Object getModifiedColumn(int paramInt)
	/*     */{
		/* 377 */return this.changedColumn[(paramInt - 1)];
		/*     */}

	/*     */
	/*     */public boolean isColumnChanged(int paramInt)
	/*     */{
		/* 406 */return this.columnChangeFlag[(paramInt - 1)] == 17;
		/*     */}

	/*     */
	/*     */public boolean isRowUpdated()
	/*     */{
		/* 437 */if ((this.rowInserted) || (this.rowDeleted)) {
			/* 438 */return false;
			/*     */}
		/* 440 */for (int i = 0; i < this.noColumn; i++) {
			/* 441 */if (this.columnChangeFlag[i] == 17) {
				/* 442 */return true;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 458 */return false;
		/*     */}

	/*     */
	/*     */public void setRowUpdated(boolean paramBoolean)
	/*     */{
		/* 482 */this.rowUpdated = paramBoolean;
		/* 483 */if (!paramBoolean)
			/* 484 */cancelRowUpdates();
		/*     */}

	/*     */
	/*     */public boolean isRowInserted()
	/*     */{
		/* 509 */return this.rowInserted;
		/*     */}

	/*     */
	/*     */public void cancelRowDeletion()
	/*     */{
		/* 531 */this.rowDeleted = false;
		/*     */}

	/*     */
	/*     */public void setRowDeleted(boolean paramBoolean)
	/*     */{
		/* 555 */this.rowDeleted = paramBoolean;
		/*     */}

	/*     */
	/*     */public boolean isRowDeleted()
	/*     */{
		/* 577 */return this.rowDeleted;
		/*     */}

	/*     */
	/*     */public Object[] getOriginalRow()
	/*     */{
		/* 604 */return this.column;
		/*     */}

	/*     */
	/*     */public boolean isRowFullyPopulated()
	/*     */{
		/* 628 */if (!this.rowInserted) {
			/* 629 */return false;
			/*     */}
		/* 631 */return this.noColumnsInserted == this.noColumn;
		/*     */}

	/*     */
	/*     */public void setInsertedFlag(boolean paramBoolean)
	/*     */{
		/* 656 */this.rowInserted = paramBoolean;
		/*     */}

	/*     */
	/*     */void makeUpdatesOriginal()
	/*     */{
		/* 667 */for (int i = 0; i < this.noColumn; i++)
		/*     */{
			/* 669 */if (this.columnChangeFlag[i] != 17) {
				/*     */continue;
				/*     */}
			/* 672 */this.column[i] = this.changedColumn[i];
			/* 673 */this.changedColumn[i] = null;
			/* 674 */this.columnChangeFlag[i] = 0;
			/*     */}
		/*     */
		/* 678 */this.rowUpdated = false;
		/*     */}

	/*     */
	/*     */public void insertRow()
	/*     */{
		/* 703 */this.columnChangeFlag = null;
		/* 704 */this.columnChangeFlag = new byte[this.noColumn];
		/* 705 */System.arraycopy(this.changedColumn, 0, this.column, 0,
				this.noColumn);
		/* 706 */this.changedColumn = null;
		/* 707 */this.changedColumn = new Object[this.noColumn];
		/*     */}

	/*     */
	/*     */public Collection toCollection()
	/*     */{
		/* 730 */Vector localVector = new Vector(this.noColumn);
		/* 731 */for (int i = 1; i <= this.noColumn; i++) {
			/* 732 */localVector.add(isColumnChanged(i) ? getModifiedColumn(i)
					: getColumn(i));
			/*     */}
		/*     */
		/* 749 */return localVector;
		/*     */}

	/*     */
	/*     */public OracleRow createCopy()
	/*     */throws SQLException
	/*     */{
		/* 769 */OracleRow localOracleRow = new OracleRow(this.noColumn);
		/* 770 */for (int i = 0; i < this.noColumn; i++)
		/*     */{
			/* 772 */localOracleRow.column[i] = getCopy(this.column[i]);
			/* 773 */localOracleRow.changedColumn[i] = getCopy(this.changedColumn[i]);
			/*     */}
		/*     */
		/* 776 */System.arraycopy(this.columnChangeFlag, 0,
				localOracleRow.columnChangeFlag, 0, this.noColumn);
		/* 777 */localOracleRow.noColumnsInserted = this.noColumnsInserted;
		/* 778 */localOracleRow.rowDeleted = this.rowDeleted;
		/* 779 */localOracleRow.rowInserted = this.rowInserted;
		/* 780 */localOracleRow.rowUpdated = this.rowUpdated;
		/*     */
		/* 796 */return localOracleRow;
		/*     */}

	/*     */
	/*     */public Object getCopy(Object paramObject)
	/*     */throws SQLException
	/*     */{
		/* 817 */Object localObject = null;
		/*     */try
		/*     */{
			/* 820 */if (paramObject == null) {
				/* 821 */return null;
				/*     */}
			/* 823 */if ((paramObject instanceof String)) {
				/* 824 */localObject = new String((String) paramObject);
				/*     */}
			/* 826 */else if ((paramObject instanceof Number)) {
				/* 827 */localObject = new BigDecimal(
						((Number) paramObject).toString());
				/*     */}
			/* 829 */else if ((paramObject instanceof Date)) {
				/* 830 */localObject = new Date(((Date) paramObject).getTime());
				/*     */}
			/* 832 */else if ((paramObject instanceof Timestamp)) {
				/* 833 */localObject = new Timestamp(
						((Timestamp) paramObject).getTime());
				/*     */}
			/* 836 */else if ((paramObject instanceof InputStream)) {
				/* 837 */localObject = new DataInputStream(
						(InputStream) paramObject);
				/*     */}
			/* 839 */else if ((paramObject instanceof OutputStream)) {
				/* 840 */localObject = new DataOutputStream(
						(OutputStream) paramObject);
				/*     */}
			/*     */else {
				/* 843 */throw new SQLException(
						"Error, could not reproduce the copy of the object, "
								+ paramObject.getClass().getName());
				/*     */}
			/*     */}
		/*     */catch (Exception localException)
		/*     */{
			/* 848 */throw new SQLException(
					"Error while creating a copy of the column of type, "
							+ paramObject.getClass().getName() + "\n"
							+ localException.getMessage());
			/*     */}
		/*     */
		/* 867 */return localObject;
		/*     */}

	/*     */
	/*     */public Object clone()
	/*     */throws CloneNotSupportedException
	/*     */{
		/*     */try
		/*     */{
			/* 889 */return createCopy();
			/*     */} catch (SQLException localSQLException) {
			/*     */}
		/* 892 */throw new CloneNotSupportedException("Error while cloning\n"
				+ localSQLException.getMessage());
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.rowset.OracleRow
 * JD-Core Version: 0.6.0
 */