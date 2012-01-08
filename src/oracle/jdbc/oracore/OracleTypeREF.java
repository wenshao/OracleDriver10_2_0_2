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
import oracle.sql.REF;
/*     */
import oracle.sql.StructDescriptor;

/*     */
/*     */public class OracleTypeREF extends OracleNamedType
/*     */implements Serializable
/*     */{
	/*     */static final long serialVersionUID = 3186448715463064573L;
	/* 253 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";

	/*     */
	/*     */protected OracleTypeREF()
	/*     */{
		/*     */}

	/*     */
	/*     */public OracleTypeREF(String paramString,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 40 */super(paramString, paramOracleConnection);
		/*     */}

	/*     */
	/*     */public OracleTypeREF(OracleTypeADT paramOracleTypeADT, int paramInt,
			OracleConnection paramOracleConnection)
	/*     */{
		/* 45 */super(paramOracleTypeADT, paramInt, paramOracleConnection);
		/*     */}

	/*     */
	/*     */public Datum toDatum(Object paramObject,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 57 */REF localREF = null;
		/*     */
		/* 59 */if (paramObject != null)
		/*     */{
			/* 61 */if ((paramObject instanceof REF))
				/* 62 */localREF = (REF) paramObject;
			/*     */else {
				/* 64 */DatabaseError.throwSqlException(59, paramObject);
				/*     */}
			/*     */}
		/*     */
		/* 68 */return localREF;
		/*     */}

	/*     */
	/*     */public int getTypeCode()
	/*     */{
		/* 80 */return 2006;
		/*     */}

	/*     */
	/*     */protected Object unpickle80rec(UnpickleContext paramUnpickleContext,
			int paramInt1, int paramInt2, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 96 */switch (paramInt1)
		/*     */{
		/*     */case 1:
			/* 101 */if (paramUnpickleContext.isNull(this.nullOffset)) {
				/* 102 */return null;
				/*     */}
			/* 104 */paramUnpickleContext
					.skipTo(paramUnpickleContext.ldsOffsets[this.ldsOffset]);
			/*     */
			/* 106 */if (paramInt2 == 9)
			/*     */{
				/* 108 */paramUnpickleContext.skipBytes(4);
				/*     */
				/* 110 */return null;
				/*     */}
			/*     */
			/* 113 */paramUnpickleContext.markAndSkip();
			/*     */
			/* 115 */byte[] arrayOfByte = paramUnpickleContext.readPtrBytes();
			/*     */
			/* 117 */paramUnpickleContext.reset();
			/*     */
			/* 119 */return toObject(arrayOfByte, paramInt2, null);
			/*     */case 2:
			/* 123 */if ((paramUnpickleContext.readByte() & 0x1) != 1)
				/*     */break;
			/* 125 */paramUnpickleContext.skipPtrBytes();
			/*     */
			/* 127 */return null;
			/*     */case 3:
			/* 133 */if (paramInt2 == 9)
			/*     */{
				/* 135 */paramUnpickleContext.skipPtrBytes();
				/*     */
				/* 137 */return null;
				/*     */}
			/*     */
			/* 140 */return toObject(paramUnpickleContext.readPtrBytes(),
					paramInt2, null);
			/*     */}
		/*     */
		/* 143 */DatabaseError.throwSqlException(1, "format=" + paramInt1);
		/*     */
		/* 147 */return null;
		/*     */}

	/*     */
	/*     */protected Object toObject(byte[] paramArrayOfByte, int paramInt,
			Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 166 */if ((paramArrayOfByte == null)
				|| (paramArrayOfByte.length == 0)) {
			/* 167 */return null;
			/*     */}
		/* 169 */if ((paramInt == 1) || (paramInt == 2))
		/*     */{
			/* 171 */StructDescriptor localStructDescriptor = createStructDescriptor();
			/*     */
			/* 173 */return new REF(localStructDescriptor, this.connection,
					paramArrayOfByte);
			/*     */}
		/* 175 */if (paramInt == 3)
		/*     */{
			/* 177 */return paramArrayOfByte;
			/*     */}
		/*     */
		/* 180 */DatabaseError.throwSqlException(59, paramArrayOfByte);
		/*     */
		/* 182 */return null;
		/*     */}

	/*     */
	/*     */StructDescriptor createStructDescriptor() throws SQLException
	/*     */{
		/* 187 */if (this.descriptor == null)
		/*     */{
			/* 189 */if ((this.sqlName == null) && (getFullName(false) == null))
			/*     */{
				/* 191 */OracleTypeADT localOracleTypeADT = new OracleTypeADT(
						getParent(), getOrder(), this.connection);
				/*     */
				/* 193 */this.descriptor = new StructDescriptor(
						localOracleTypeADT, this.connection);
				/*     */}
			/*     */else
			/*     */{
				/* 197 */this.descriptor = StructDescriptor.createDescriptor(
						this.sqlName, this.connection);
				/*     */}
			/*     */}
		/* 200 */return (StructDescriptor) this.descriptor;
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
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.oracore.OracleTypeREF
 * JD-Core Version: 0.6.0
 */