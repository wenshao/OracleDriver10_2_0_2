/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.sql.SQLException;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.oracore.OracleType;
/*     */
import oracle.jdbc.oracore.OracleTypeADT;
/*     */
import oracle.sql.ARRAY;
/*     */
import oracle.sql.ArrayDescriptor;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.JAVA_STRUCT;
/*     */
import oracle.sql.OPAQUE;
/*     */
import oracle.sql.OpaqueDescriptor;
/*     */
import oracle.sql.STRUCT;
/*     */
import oracle.sql.StructDescriptor;
/*     */
import oracle.sql.TypeDescriptor;

/*     */
/*     */class NamedTypeAccessor extends TypeAccessor
/*     */{
	/*     */NamedTypeAccessor(OracleStatement paramOracleStatement,
			String paramString, short paramShort, int paramInt,
			boolean paramBoolean)
	/*     */throws SQLException
	/*     */{
		/* 33 */init(paramOracleStatement, 109, 109, paramShort, paramBoolean);
		/* 34 */initForDataAccess(paramInt, 0, paramString);
		/*     */}

	/*     */
	/*     */NamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort, String paramString)
	/*     */throws SQLException
	/*     */{
		/* 42 */init(paramOracleStatement, 109, 109, paramShort, false);
		/* 43 */initForDescribe(109, paramInt1, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort,
				paramString);
		/*     */
		/* 45 */initForDataAccess(0, paramInt1, paramString);
		/*     */}

	/*     */
	/*     */NamedTypeAccessor(OracleStatement paramOracleStatement, int paramInt1,
			boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4,
			int paramInt5, int paramInt6, short paramShort, String paramString,
			OracleType paramOracleType)
	/*     */throws SQLException
	/*     */{
		/* 53 */init(paramOracleStatement, 109, 109, paramShort, false);
		/*     */
		/* 55 */this.describeOtype = paramOracleType;
		/*     */
		/* 57 */initForDescribe(109, paramInt1, paramBoolean, paramInt2,
				paramInt3, paramInt4, paramInt5, paramInt6, paramShort,
				paramString);
		/*     */
		/* 60 */this.internalOtype = paramOracleType;
		/*     */
		/* 62 */initForDataAccess(0, paramInt1, paramString);
		/*     */}

	/*     */
	/*     */OracleType otypeFromName(String paramString) throws SQLException
	/*     */{
		/* 67 */if (!this.outBind) {
			/* 68 */return TypeDescriptor.getTypeDescriptor(paramString,
					this.statement.connection).getPickler();
			/*     */}
		/* 70 */if (this.externalType == 2003) {
			/* 71 */return ArrayDescriptor.createDescriptor(paramString,
					this.statement.connection).getOracleTypeCOLLECTION();
			/*     */}
		/* 73 */if (this.externalType == 2007) {
			/* 74 */return OpaqueDescriptor.createDescriptor(paramString,
					this.statement.connection).getPickler();
			/*     */}
		/*     */
		/* 77 */return StructDescriptor.createDescriptor(paramString,
				this.statement.connection).getOracleTypeADT();
		/*     */}

	/*     */
	/*     */void initForDataAccess(int paramInt1, int paramInt2, String paramString)
	/*     */throws SQLException
	/*     */{
		/* 84 */super.initForDataAccess(paramInt1, paramInt2, paramString);
		/*     */
		/* 86 */this.byteLength = this.statement.connection.namedTypeAccessorByteLen;
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 100 */return getObject(paramInt,
				this.statement.connection.getTypeMap());
		/*     */}

	/*     */
	/*     */Object getObject(int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 118 */if (this.rowSpaceIndicator == null) {
			/* 119 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 127 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/*     */Datum localDatum;
			/* 131 */if (this.externalType == 0)
			/*     */{
				/* 133 */localDatum = getOracleObject(paramInt);
				/*     */
				/* 137 */if (localDatum == null) {
					/* 138 */return null;
					/*     */}
				/* 140 */if ((localDatum instanceof STRUCT)) {
					/* 141 */return ((STRUCT) localDatum).toJdbc(paramMap);
					/*     */}
				/* 143 */if ((localDatum instanceof OPAQUE)) {
					/* 144 */return ((OPAQUE) localDatum).toJdbc(paramMap);
					/*     */}
				/* 146 */return localDatum.toJdbc();
				/*     */}
			/*     */
			/* 151 */switch (this.externalType)
			/*     */{
			/*     */case 2008:
				/* 157 */paramMap = null;
				/*     */case 2000:
				/*     */
			case 2002:
				/*     */
			case 2003:
				/*     */
			case 2007:
				/* 166 */localDatum = getOracleObject(paramInt);
				/*     */
				/* 170 */if (localDatum == null) {
					/* 171 */return null;
					/*     */}
				/* 173 */if ((localDatum instanceof STRUCT)) {
					/* 174 */return ((STRUCT) localDatum).toJdbc(paramMap);
					/*     */}
				/* 176 */return localDatum.toJdbc();
				/*     */case 2001:
				/*     */
			case 2004:
				/*     */
			case 2005:
				/*     */
			case 2006:
				/*     */}
			/*     */
			/* 183 */DatabaseError.throwSqlException(4);
			/*     */
			/* 185 */return null;
			/*     */}
		/*     */
		/* 194 */return null;
		/*     */}

	/*     */
	/*     */Datum getOracleObject(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 208 */Object localObject = null;
		/*     */
		/* 213 */if (this.rowSpaceIndicator == null) {
			/* 214 */DatabaseError.throwSqlException(21);
			/*     */}
		/*     */
		/* 222 */if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
		/*     */{
			/* 224 */byte[] arrayOfByte = pickledBytes(paramInt);
			/*     */
			/* 226 */if ((arrayOfByte == null) || (arrayOfByte.length == 0)) {
				/* 227 */return null;
				/*     */}
			/* 229 */PhysicalConnection localPhysicalConnection = this.statement.connection;
			/* 230 */OracleTypeADT localOracleTypeADT = (OracleTypeADT) this.internalOtype;
			/* 231 */TypeDescriptor localTypeDescriptor = TypeDescriptor
					.getTypeDescriptor(localOracleTypeADT.getFullName(),
							localPhysicalConnection, arrayOfByte, 0L);
			/*     */
			/* 234 */switch (localTypeDescriptor.getTypeCode())
			/*     */{
			/*     */case 2003:
				/* 238 */localObject = new ARRAY(
						(ArrayDescriptor) localTypeDescriptor, arrayOfByte,
						localPhysicalConnection);
				/*     */
				/* 240 */break;
			/*     */case 2002:
				/* 243 */localObject = new STRUCT(
						(StructDescriptor) localTypeDescriptor, arrayOfByte,
						localPhysicalConnection);
				/*     */
				/* 245 */break;
			/*     */case 2007:
				/* 248 */localObject = new OPAQUE(
						(OpaqueDescriptor) localTypeDescriptor, arrayOfByte,
						localPhysicalConnection);
				/*     */
				/* 250 */break;
			/*     */case 2008:
				/* 253 */localObject = new JAVA_STRUCT(
						(StructDescriptor) localTypeDescriptor, arrayOfByte,
						localPhysicalConnection);
				/*     */
				/* 255 */break;
			/*     */case 2004:
				/*     */
			case 2005:
				/*     */
			case 2006:
				/*     */
			default:
				/* 262 */DatabaseError.throwSqlException(1);
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 270 */return (Datum) localObject;
		/*     */}

	/*     */
	/*     */ARRAY getARRAY(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 284 */return (ARRAY) getOracleObject(paramInt);
		/*     */}

	/*     */
	/*     */STRUCT getSTRUCT(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 298 */return (STRUCT) getOracleObject(paramInt);
		/*     */}

	/*     */
	/*     */OPAQUE getOPAQUE(int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 312 */return (OPAQUE) getOracleObject(paramInt);
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.NamedTypeAccessor JD-Core Version: 0.6.0
 */