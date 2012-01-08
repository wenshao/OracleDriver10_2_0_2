/*     */package oracle.sql;

/*     */
/*     */import java.sql.Connection;
/*     */
import java.sql.SQLException;
/*     */
import java.util.Hashtable;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.driver.DatabaseError;
/*     */
import oracle.jdbc.internal.OracleConnection;

/*     */
/*     */public class JAVA_STRUCT extends STRUCT
/*     */{
	/* 175 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";

	/*     */
	/*     */public JAVA_STRUCT(StructDescriptor paramStructDescriptor,
			Connection paramConnection, Object[] paramArrayOfObject)
	/*     */throws SQLException
	/*     */{
		/* 60 */super(paramStructDescriptor, paramConnection,
				paramArrayOfObject);
		/*     */}

	/*     */
	/*     */public JAVA_STRUCT(StructDescriptor paramStructDescriptor,
			byte[] paramArrayOfByte, Connection paramConnection)
	/*     */throws SQLException
	/*     */{
		/* 79 */super(paramStructDescriptor, paramArrayOfByte, paramConnection);
		/*     */}

	/*     */
	/*     */public Object toJdbc()
	/*     */throws SQLException
	/*     */{
		/* 100 */Object localObject1 = getInternalConnection()
				.getJavaObjectTypeMap();
		/*     */
		/* 102 */Class localClass = null;
		/*     */
		/* 104 */if (localObject1 != null) {
			/* 105 */localClass = this.descriptor.getClass((Map) localObject1);
			/*     */}
		/*     */else {
			/* 108 */localObject1 = new Hashtable(10);
			/*     */
			/* 110 */getInternalConnection().setJavaObjectTypeMap(
					(Map) localObject1);
			/*     */}
		/*     */
		/* 113 */if (localClass == null)
		/*     */{
			/* 115 */localObject2 = StructDescriptor.getJavaObjectClassName(
					getInternalConnection(), getDescriptor());
			/*     */
			/* 120 */String str = getDescriptor().getSchemaName();
			/*     */
			/* 122 */if ((localObject2 == null)
					|| (((String) localObject2).length() == 0))
			/*     */{
				/* 127 */DatabaseError.throwSqlException(1);
				/*     */}
			/*     */
			/*     */try
			/*     */{
				/* 132 */localClass = getInternalConnection()
						.classForNameAndSchema((String) localObject2, str);
				/*     */}
			/*     */catch (ClassNotFoundException localClassNotFoundException)
			/*     */{
				/* 142 */DatabaseError.throwSqlException(49,
						"ClassNotFoundException: "
								+ localClassNotFoundException.getMessage());
				/*     */}
			/*     */
			/* 147 */((Map) localObject1).put(getSQLTypeName(), localClass);
			/*     */}
		/*     */
		/* 150 */Object localObject2 = toClass(localClass, getMap());
		/*     */
		/* 154 */return localObject2;
		/*     */}

	/*     */
	/*     */public Object toJdbc(Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 170 */return toJdbc();
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.JAVA_STRUCT JD-Core
 * Version: 0.6.0
 */