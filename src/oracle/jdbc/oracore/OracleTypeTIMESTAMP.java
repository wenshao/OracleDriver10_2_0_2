/*     */package oracle.jdbc.oracore;

/*     */
/*     */import java.io.IOException;
/*     */
import java.io.ObjectInputStream;
/*     */
import java.io.ObjectOutputStream;
/*     */
import java.io.Serializable;
/*     */
import java.sql.Date;
/*     */
import java.sql.SQLException;
/*     */
import java.sql.Time;
/*     */
import java.sql.Timestamp;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.driver.DatabaseError;
/*     */
import oracle.jdbc.internal.OracleConnection;
/*     */
import oracle.sql.DATE;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.TIMESTAMP;

/*     */
/*     */public class OracleTypeTIMESTAMP extends OracleType
/*     */implements Serializable
/*     */{
	/*     */static final long serialVersionUID = 3948043338303602796L;
	/* 55 */int precision = 0;
	/*     */
	/* 243 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";

	/*     */
	/*     */protected OracleTypeTIMESTAMP()
	/*     */{
		/*     */}

	/*     */
	/*     */public OracleTypeTIMESTAMP(OracleConnection paramOracleConnection)
	/*     */{
		/*     */}

	/*     */
	/*     */public int getTypeCode()
	/*     */{
		/* 79 */return 93;
		/*     */}

	/*     */
	/*     */public void parseTDSrec(TDSReader paramTDSReader)
	/*     */throws SQLException
	/*     */{
		/* 89 */this.precision = paramTDSReader.readByte();
		/*     */}

	/*     */
	/*     */public int getScale() throws SQLException
	/*     */{
		/* 94 */return 0;
		/*     */}

	/*     */
	/*     */public int getPrecision() throws SQLException
	/*     */{
		/* 99 */return this.precision;
		/*     */}

	/*     */
	/*     */public void readObject(ObjectInputStream paramObjectInputStream)
	/*     */throws IOException, ClassNotFoundException
	/*     */{
		/* 110 */this.precision = paramObjectInputStream.readByte();
		/*     */}

	/*     */
	/*     */private void writeObject(ObjectOutputStream paramObjectOutputStream)
	/*     */throws IOException
	/*     */{
		/* 119 */paramObjectOutputStream.writeByte(this.precision);
		/*     */}

	/*     */
	/*     */protected Object toObject(byte[] paramArrayOfByte, int paramInt,
			Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 125 */if ((paramArrayOfByte == null)
				|| (paramArrayOfByte.length == 0)) {
			/* 126 */return null;
			/*     */}
		/* 128 */switch (paramInt)
		/*     */{
		/*     */case 1:
			/* 132 */return new TIMESTAMP(paramArrayOfByte);
			/*     */case 2:
			/* 135 */return TIMESTAMP.toTimestamp(paramArrayOfByte);
			/*     */case 3:
			/* 138 */return paramArrayOfByte;
			/*     */}
		/*     */
		/* 141 */DatabaseError.throwSqlException(59);
		/*     */
		/* 144 */return null;
		/*     */}

	/*     */
	/*     */public Datum toDatum(Object paramObject,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 160 */TIMESTAMP localTIMESTAMP = null;
		/*     */
		/* 162 */if (paramObject != null)
		/*     */{
			/*     */try
			/*     */{
				/* 166 */if ((paramObject instanceof TIMESTAMP))
					/* 167 */localTIMESTAMP = (TIMESTAMP) paramObject;
				/* 168 */else if ((paramObject instanceof byte[]))
					/* 169 */localTIMESTAMP = new TIMESTAMP(
							(byte[]) paramObject);
				/* 170 */else if ((paramObject instanceof Timestamp))
					/* 171 */localTIMESTAMP = new TIMESTAMP(
							(Timestamp) paramObject);
				/* 172 */else if ((paramObject instanceof DATE))
					/* 173 */localTIMESTAMP = new TIMESTAMP((DATE) paramObject);
				/* 174 */else if ((paramObject instanceof String))
					/* 175 */localTIMESTAMP = new TIMESTAMP(
							(String) paramObject);
				/* 176 */else if ((paramObject instanceof Date))
					/* 177 */localTIMESTAMP = new TIMESTAMP((Date) paramObject);
				/* 178 */else if ((paramObject instanceof Time))
					/* 179 */localTIMESTAMP = new TIMESTAMP((Time) paramObject);
				/*     */else {
					/* 181 */DatabaseError.throwSqlException(59, paramObject);
					/*     */}
				/*     */}
			/*     */catch (Exception localException)
			/*     */{
				/* 186 */DatabaseError.throwSqlException(59, paramObject);
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 191 */return localTIMESTAMP;
		/*     */}

	/*     */
	/*     */protected Object unpickle80rec(UnpickleContext paramUnpickleContext,
			int paramInt1, int paramInt2, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 214 */DatabaseError.throwSqlException(90);
		/*     */
		/* 216 */return null;
		/*     */}

	/*     */
	/*     */protected Object unpickle81rec(UnpickleContext paramUnpickleContext,
			int paramInt1, int paramInt2, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 233 */DatabaseError.throwSqlException(90);
		/*     */
		/* 235 */return null;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.oracore.OracleTypeTIMESTAMP JD-Core Version: 0.6.0
 */