/*     */package oracle.jdbc.oracore;

/*     */
/*     */import java.sql.SQLException;
/*     */
import oracle.jdbc.driver.DatabaseError;

/*     */
/*     */public class TDSPatch
/*     */{
	/*     */static final int S_NORMAL_PATCH = 0;
	/*     */static final int S_SIMPLE_PATCH = 1;
	/*     */int typeId;
	/*     */OracleType owner;
	/*     */long position;
	/*     */int uptCode;
	/* 126 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:27_PST_2006";

	/*     */
	/*     */public TDSPatch(int paramInt1, OracleType paramOracleType,
			long paramLong, int paramInt2)
	/*     */throws SQLException
	/*     */{
		/* 36 */this.typeId = paramInt1;
		/* 37 */this.owner = paramOracleType;
		/* 38 */this.position = paramLong;
		/* 39 */this.uptCode = paramInt2;
		/*     */}

	/*     */
	/*     */int getType() throws SQLException
	/*     */{
		/* 44 */return this.typeId;
		/*     */}

	/*     */
	/*     */OracleNamedType getOwner() throws SQLException
	/*     */{
		/* 49 */return (OracleNamedType) this.owner;
		/*     */}

	/*     */
	/*     */long getPosition() throws SQLException
	/*     */{
		/* 54 */return this.position;
		/*     */}

	/*     */
	/*     */byte getUptTypeCode() throws SQLException
	/*     */{
		/* 59 */return (byte) this.uptCode;
		/*     */}

	/*     */
	/*     */void apply(OracleType paramOracleType) throws SQLException
	/*     */{
		/* 64 */apply(paramOracleType, -1);
		/*     */}

	/*     */
	/*     */void apply(OracleType paramOracleType, int paramInt)
	/*     */throws SQLException
	/*     */{
		/*     */Object localObject;
		/*     */OracleNamedType localOracleNamedType;
		/* 72 */if (this.typeId == 0)
		/*     */{
			/* 76 */localObject = (OracleTypeUPT) this.owner;
			/*     */
			/* 78 */((OracleTypeUPT) localObject).realType = ((OracleTypeADT) paramOracleType);
			/*     */
			/* 81 */if ((paramOracleType instanceof OracleNamedType))
			/*     */{
				/* 83 */localOracleNamedType = (OracleNamedType) paramOracleType;
				/*     */
				/* 85 */localOracleNamedType
						.setParent(((OracleTypeUPT) localObject).getParent());
				/* 86 */localOracleNamedType
						.setOrder(((OracleTypeUPT) localObject).getOrder());
				/*     */}
			/*     */}
		/* 89 */else if (this.typeId == 1)
		/*     */{
			/* 93 */localObject = (OracleTypeCOLLECTION) this.owner;
			/*     */
			/* 95 */((OracleTypeCOLLECTION) localObject).opcode = paramInt;
			/* 96 */((OracleTypeCOLLECTION) localObject).elementType = paramOracleType;
			/*     */
			/* 99 */if ((paramOracleType instanceof OracleNamedType))
			/*     */{
				/* 101 */localOracleNamedType = (OracleNamedType) paramOracleType;
				/*     */
				/* 103 */localOracleNamedType
						.setParent((OracleTypeADT) localObject);
				/* 104 */localOracleNamedType.setOrder(1);
				/*     */}
			/*     */}
		/*     */else {
			/* 108 */DatabaseError.throwSqlException(1);
			/*     */}
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.oracore.TDSPatch
 * JD-Core Version: 0.6.0
 */