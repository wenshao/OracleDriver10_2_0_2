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
import java.sql.SQLException;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.driver.DatabaseError;
/*     */
import oracle.jdbc.internal.OracleConnection;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.OPAQUE;
/*     */
import oracle.sql.OpaqueDescriptor;
/*     */
import oracle.sql.StructDescriptor;

/*     */
/*     */public class OracleTypeOPAQUE extends OracleTypeADT
/*     */implements Serializable
/*     */{
	/*     */static final long KOLOFLLB = 1L;
	/*     */static final long KOLOFLCL = 2L;
	/*     */static final long KOLOFLUB = 4L;
	/*     */static final long KOLOFLFX = 8L;
	/*     */static final long serialVersionUID = -7279638692691669378L;
	/*     */long flagBits;
	/*     */long maxLen;
	/* 386 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";

	/*     */
	/*     */public OracleTypeOPAQUE(String paramString,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 51 */super(paramString, paramOracleConnection);
		/*     */}

	/*     */
	/*     */public OracleTypeOPAQUE(OracleTypeADT paramOracleTypeADT,
			int paramInt, OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 60 */super(paramOracleTypeADT, paramInt, paramOracleConnection);
		/*     */}

	/*     */
	/*     */public Datum toDatum(Object paramObject,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 79 */if (paramObject != null)
		/*     */{
			/* 81 */if ((paramObject instanceof OPAQUE)) {
				/* 82 */return (OPAQUE) paramObject;
				/*     */}
			/*     */
			/* 85 */OpaqueDescriptor localOpaqueDescriptor = createOpaqueDescriptor();
			/*     */
			/* 87 */return new OPAQUE(localOpaqueDescriptor, this.connection,
					paramObject);
			/*     */}
		/*     */
		/* 91 */return null;
		/*     */}

	/*     */
	/*     */public int getTypeCode()
	/*     */{
		/* 101 */return 2007;
		/*     */}

	/*     */
	/*     */public boolean isInHierarchyOf(OracleType paramOracleType)
	/*     */throws SQLException
	/*     */{
		/* 110 */return false;
		/*     */}

	/*     */
	/*     */public boolean isInHierarchyOf(StructDescriptor paramStructDescriptor)
	/*     */throws SQLException
	/*     */{
		/* 116 */return false;
		/*     */}

	/*     */
	/*     */public boolean isObjectType()
	/*     */{
		/* 121 */return false;
		/*     */}

	/*     */
	/*     */public void parseTDSrec(TDSReader paramTDSReader)
	/*     */throws SQLException
	/*     */{
		/* 135 */paramTDSReader.skipBytes(5);
		/*     */
		/* 137 */this.flagBits = paramTDSReader.readLong();
		/* 138 */this.maxLen = paramTDSReader.readLong();
		/*     */}

	/*     */
	/*     */public Datum unlinearize(byte[] paramArrayOfByte, long paramLong,
			Datum paramDatum, int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 152 */if (paramArrayOfByte == null) {
			/* 153 */return null;
			/*     */}
		/* 155 */if ((paramArrayOfByte[0] & 0x80) > 0)
		/*     */{
			/* 157 */PickleContext localPickleContext = new PickleContext(
					paramArrayOfByte, paramLong);
			/*     */
			/* 159 */return unpickle81(localPickleContext, (OPAQUE) paramDatum,
					paramInt, paramMap);
			/*     */}
		/*     */
		/* 165 */return null;
		/*     */}

	/*     */
	/*     */public byte[] linearize(Datum paramDatum)
	/*     */throws SQLException
	/*     */{
		/* 179 */return pickle81(paramDatum);
		/*     */}

	/*     */
	/*     */protected int pickle81(PickleContext paramPickleContext,
			Datum paramDatum)
	/*     */throws SQLException
	/*     */{
		/* 192 */OPAQUE localOPAQUE = (OPAQUE) paramDatum;
		/* 193 */byte[] arrayOfByte = localOPAQUE.getBytesValue();
		/* 194 */int i = 0;
		/*     */
		/* 196 */i += paramPickleContext
				.writeOpaqueImageHeader(arrayOfByte.length);
		/* 197 */i += paramPickleContext.writeData(arrayOfByte);
		/*     */
		/* 199 */return i;
		/*     */}

	/*     */
	/*     */OPAQUE unpickle81(PickleContext paramPickleContext,
			OPAQUE paramOPAQUE, int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 213 */return unpickle81datum(paramPickleContext, paramOPAQUE,
				paramInt);
		/*     */}

	/*     */
	/*     */protected Object unpickle81rec(PickleContext paramPickleContext,
			int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 219 */byte b = paramPickleContext.readByte();
		/* 220 */Object localObject = null;
		/*     */
		/* 222 */if (PickleContext.isElementNull(b)) {
			/* 223 */return null;
			/*     */}
		/* 225 */paramPickleContext.skipRestOfLength(b);
		/*     */
		/* 227 */switch (paramInt)
		/*     */{
		/*     */case 1:
			/* 231 */localObject = unpickle81datum(paramPickleContext, null);
			/* 232 */break;
		/*     */case 2:
			/* 235 */localObject = unpickle81datum(paramPickleContext, null)
					.toJdbc();
			/* 236 */break;
		/*     */case 3:
			/* 239 */localObject = new OPAQUE(createOpaqueDescriptor(),
					paramPickleContext.readDataValue(), this.connection);
			/*     */
			/* 243 */break;
		/*     */case 9:
			/* 245 */paramPickleContext.skipDataValue();
			/* 246 */break;
		/*     */case 4:
			/*     */
		case 5:
			/*     */
		case 6:
			/*     */
		case 7:
			/*     */
		case 8:
			/*     */
		default:
			/* 249 */DatabaseError.throwSqlException(1);
			/*     */}
		/*     */
		/* 252 */return localObject;
		/*     */}

	/*     */
	/*     */private OPAQUE unpickle81datum(PickleContext paramPickleContext,
			OPAQUE paramOPAQUE)
	/*     */throws SQLException
	/*     */{
		/* 263 */return unpickle81datum(paramPickleContext, paramOPAQUE, 1);
		/*     */}

	/*     */
	/*     */private OPAQUE unpickle81datum(PickleContext paramPickleContext,
			OPAQUE paramOPAQUE, int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 274 */paramPickleContext.skipBytes(2);
		/*     */
		/* 276 */long l = paramPickleContext.readLength(true) - 2;
		/*     */
		/* 281 */if (paramOPAQUE == null)
		/*     */{
			/* 283 */if (paramInt == 2)
			/*     */{
				/* 287 */return new OPAQUE(createOpaqueDescriptor(),
						paramPickleContext.readBytes((int) l), this.connection);
				/*     */}
			/*     */
			/* 294 */return new OPAQUE(createOpaqueDescriptor(),
					this.connection, paramPickleContext.readBytes((int) l));
			/*     */}
		/*     */
		/* 300 */paramOPAQUE.setValue(paramPickleContext.readBytes((int) l));
		/*     */
		/* 302 */return paramOPAQUE;
		/*     */}

	/*     */
	/*     */OpaqueDescriptor createOpaqueDescriptor()
	/*     */throws SQLException
	/*     */{
		/* 308 */if (this.sqlName == null) {
			/* 309 */return new OpaqueDescriptor(this, this.connection);
			/*     */}
		/* 311 */return OpaqueDescriptor.createDescriptor(this.sqlName,
				this.connection);
		/*     */}

	/*     */
	/*     */public long getMaxLength() throws SQLException
	/*     */{
		/* 316 */return this.maxLen;
		/*     */}

	/*     */
	/*     */public boolean isTrustedLibrary() throws SQLException
	/*     */{
		/* 321 */return (this.flagBits & 1L) != 0L;
		/*     */}

	/*     */
	/*     */public boolean isModeledInC() throws SQLException
	/*     */{
		/* 326 */return (this.flagBits & 0x2) != 0L;
		/*     */}

	/*     */
	/*     */public boolean isUnboundedSized() throws SQLException
	/*     */{
		/* 331 */return (this.flagBits & 0x4) != 0L;
		/*     */}

	/*     */
	/*     */public boolean isFixedSized() throws SQLException
	/*     */{
		/* 336 */return (this.flagBits & 0x8) != 0L;
		/*     */}

	/*     */
	/*     */private void writeObject(ObjectOutputStream paramObjectOutputStream)
	/*     */throws IOException
	/*     */{
		/*     */}

	/*     */
	/*     */private void readObject(ObjectInputStream paramObjectInputStream)
	/*     */throws IOException, ClassNotFoundException
	/*     */{
		/*     */}

	/*     */
	/*     */public void setConnection(OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 361 */this.connection = paramOracleConnection;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.oracore.OracleTypeOPAQUE JD-Core Version: 0.6.0
 */