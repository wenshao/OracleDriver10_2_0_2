/*     */package oracle.jdbc.oracore;

/*     */
/*     */import java.io.IOException;
/*     */
import java.io.ObjectInputStream;
/*     */
import java.io.ObjectOutputStream;
/*     */
import java.io.PrintWriter;
/*     */
import java.io.Serializable;
/*     */
import java.sql.PreparedStatement;
/*     */
import java.sql.ResultSet;
/*     */
import java.sql.SQLException;
/*     */
import java.util.HashMap;
/*     */
import java.util.Map;
/*     */
import oracle.jdbc.driver.DatabaseError;
/*     */
import oracle.jdbc.internal.OracleConnection;
/*     */
import oracle.sql.Datum;
/*     */
import oracle.sql.SQLName;
/*     */
import oracle.sql.TypeDescriptor;

/*     */
/*     */public abstract class OracleNamedType extends OracleType
/*     */implements Serializable
/*     */{
	/*     */transient OracleConnection connection;
	/* 36 */SQLName sqlName = null;
	/* 37 */transient OracleTypeADT parent = null;
	/*     */transient int idx;
	/* 39 */transient TypeDescriptor descriptor = null;
	/*     */
	/* 302 */static String getUserTypeTreeSql = "/*+ RULE */select level depth, parent_type, child_type, ATTR_NO, child_type_owner from  (select TYPE_NAME parent_type, ELEM_TYPE_NAME child_type, 0 ATTR_NO,       ELEM_TYPE_OWNER child_type_owner     from USER_COLL_TYPES  union   select TYPE_NAME parent_type, ATTR_TYPE_NAME child_type, ATTR_NO,       ATTR_TYPE_OWNER child_type_owner     from USER_TYPE_ATTRS  ) start with parent_type  = ?  connect by prior  child_type = parent_type";
	/*     */
	/* 362 */static String getAllTypeTreeSql = "/*+ RULE */select parent_type, parent_type_owner, child_type, ATTR_NO, child_type_owner from ( select TYPE_NAME parent_type,  OWNER parent_type_owner,     ELEM_TYPE_NAME child_type, 0 ATTR_NO,     ELEM_TYPE_OWNER child_type_owner   from ALL_COLL_TYPES union   select TYPE_NAME parent_type, OWNER parent_type_owner,     ATTR_TYPE_NAME child_type, ATTR_NO,     ATTR_TYPE_OWNER child_type_owner   from ALL_TYPE_ATTRS ) start with parent_type  = ?  and parent_type_owner = ? connect by prior child_type = parent_type   and ( child_type_owner = parent_type_owner or child_type_owner is null )";
	/*     */
	/* 454 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";

	/*     */
	/*     */protected OracleNamedType()
	/*     */{
		/*     */}

	/*     */
	/*     */public OracleNamedType(String paramString,
			OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 48 */setConnectionInternal(paramOracleConnection);
		/* 49 */this.sqlName = new SQLName(paramString, paramOracleConnection);
		/*     */}

	/*     */
	/*     */protected OracleNamedType(OracleTypeADT paramOracleTypeADT,
			int paramInt, OracleConnection paramOracleConnection)
	/*     */{
		/* 58 */setConnectionInternal(paramOracleConnection);
		/* 59 */this.parent = paramOracleTypeADT;
		/* 60 */this.idx = paramInt;
		/*     */}

	/*     */
	/*     */public String getFullName()
	/*     */throws SQLException
	/*     */{
		/* 68 */return getFullName(false);
		/*     */}

	/*     */
	/*     */public String getFullName(boolean paramBoolean)
	/*     */throws SQLException
	/*     */{
		/* 76 */String str = null;
		/*     */
		/* 78 */if ((paramBoolean | this.sqlName == null))
		/*     */{
			/* 81 */if ((this.parent != null)
					&& ((str = this.parent.getAttributeType(this.idx)) != null))
			/*     */{
				/* 85 */this.sqlName = new SQLName(str, this.connection);
				/*     */}
			/*     */else {
				/* 88 */DatabaseError.throwSqlException(1,
						"Unable to resolve name");
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 93 */return this.sqlName.getName();
		/*     */}

	/*     */
	/*     */public String getSchemaName() throws SQLException
	/*     */{
		/* 98 */if (this.sqlName == null)
			getFullName();
		/*     */
		/* 101 */return this.sqlName.getSchema();
		/*     */}

	/*     */
	/*     */public String getSimpleName() throws SQLException
	/*     */{
		/* 106 */if (this.sqlName == null)
			getFullName();
		/*     */
		/* 109 */return this.sqlName.getSimpleName();
		/*     */}

	/*     */public boolean hasName() throws SQLException {
		/* 112 */return this.sqlName != null;
		/*     */}

	/* 114 */public OracleTypeADT getParent() throws SQLException {
		return this.parent;
	}

	/*     */
	/*     */public void setParent(OracleTypeADT paramOracleTypeADT)
			throws SQLException
	/*     */{
		/* 118 */this.parent = paramOracleTypeADT;
		/*     */}

	/*     */public int getOrder() throws SQLException {
		/* 121 */return this.idx;
		/*     */}

	/* 123 */public void setOrder(int paramInt) throws SQLException {
		this.idx = paramInt;
		/*     */}

	/*     */
	/*     */public OracleConnection getConnection()
	/*     */throws SQLException
	/*     */{
		/* 134 */return this.connection;
		/*     */}

	/*     */
	/*     */public void setConnection(OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 147 */setConnectionInternal(paramOracleConnection);
		/*     */}

	/*     */
	/*     */public void setConnectionInternal(
			OracleConnection paramOracleConnection)
	/*     */{
		/* 152 */this.connection = paramOracleConnection;
		/*     */}

	/*     */
	/*     */public Datum unlinearize(byte[] paramArrayOfByte, long paramLong,
			Datum paramDatum, int paramInt, Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 168 */DatabaseError.throwSqlException(23);
		/*     */
		/* 170 */return null;
		/*     */}

	/*     */
	/*     */public Datum unlinearize(byte[] paramArrayOfByte, long paramLong1,
			Datum paramDatum, long paramLong2, int paramInt1, int paramInt2,
			Map paramMap)
	/*     */throws SQLException
	/*     */{
		/* 186 */DatabaseError.throwSqlException(23);
		/*     */
		/* 188 */return null;
		/*     */}

	/*     */
	/*     */public byte[] linearize(Datum paramDatum)
	/*     */throws SQLException
	/*     */{
		/* 197 */DatabaseError.throwSqlException(23);
		/*     */
		/* 199 */return null;
		/*     */}

	/*     */
	/*     */public TypeDescriptor getDescriptor() throws SQLException
	/*     */{
		/* 204 */return this.descriptor;
		/*     */}

	/*     */
	/*     */public void setDescriptor(TypeDescriptor paramTypeDescriptor)
			throws SQLException
	/*     */{
		/* 209 */this.descriptor = paramTypeDescriptor;
		/*     */}

	/*     */
	/*     */public int getTypeVersion()
	/*     */{
		/* 214 */return 1;
		/*     */}

	/*     */
	/*     */private void writeObject(ObjectOutputStream paramObjectOutputStream)
	/*     */throws IOException
	/*     */{
		/*     */try
		/*     */{
			/* 229 */paramObjectOutputStream.writeUTF(getFullName());
			/*     */}
		/*     */catch (SQLException localSQLException)
		/*     */{
			/* 233 */DatabaseError.SQLToIOException(localSQLException);
			/*     */}
		/*     */}

	/*     */
	/*     */private void readObject(ObjectInputStream paramObjectInputStream)
	/*     */throws IOException, ClassNotFoundException
	/*     */{
		/* 243 */String str = paramObjectInputStream.readUTF();
		/*     */try {
			this.sqlName = new SQLName(str, null);
		} catch (SQLException localSQLException) {
			/* 245 */}
		this.parent = null;
		/* 246 */this.idx = -1;
		/*     */}

	/*     */
	/*     */public void fixupConnection(OracleConnection paramOracleConnection)
	/*     */throws SQLException
	/*     */{
		/* 252 */if (this.connection == null)
			setConnection(paramOracleConnection);
		/*     */}

	/*     */
	/*     */public void printXML(PrintWriter paramPrintWriter, int paramInt)
	/*     */throws SQLException
	/*     */{
		/* 262 */for (int i = 0; i < paramInt; i++)
			paramPrintWriter.print("  ");
		/* 263 */paramPrintWriter.println("<OracleNamedType/>");
		/*     */}

	/*     */
	/*     */public void initNamesRecursively() throws SQLException
	/*     */{
		/* 268 */Map localMap = createTypesTreeMap();
		/* 269 */if (localMap.size() > 0)
			/* 270 */initChildNamesRecursively(localMap);
		/*     */}

	/*     */
	/*     */public void setNames(String paramString1, String paramString2)
			throws SQLException
	/*     */{
		/* 275 */this.sqlName = new SQLName(paramString1, paramString2,
				this.connection);
		/*     */}

	/*     */
	/*     */public void setSqlName(SQLName paramSQLName)
	/*     */{
		/* 280 */this.sqlName = paramSQLName;
		/*     */}

	/*     */
	/*     */public Map createTypesTreeMap()
	/*     */throws SQLException
	/*     */{
		/* 292 */Map localMap = null;
		/* 293 */String str = this.connection.getUserName();
		/* 294 */if (this.sqlName.getSchema().equals(str)) {
			/* 295 */localMap = getNodeMapFromUserTypes();
			/*     */}
		/* 297 */if (localMap == null)
			/* 298 */localMap = getNodeMapFromAllTypes();
		/* 299 */return localMap;
		/*     */}

	/*     */
	/*     */Map getNodeMapFromUserTypes()
	/*     */throws SQLException
	/*     */{
		/* 323 */HashMap localHashMap = new HashMap();
		/* 324 */PreparedStatement localPreparedStatement = null;
		/* 325 */ResultSet localResultSet = null;
		/*     */try
		/*     */{
			/* 328 */localPreparedStatement = this.connection
					.prepareStatement(getUserTypeTreeSql);
			/* 329 */localPreparedStatement.setString(1,
					this.sqlName.getSimpleName());
			/* 330 */localResultSet = localPreparedStatement.executeQuery();
			/*     */
			/* 332 */while (localResultSet.next())
			/*     */{
				/* 334 */int i = localResultSet.getInt(1);
				/* 335 */String str1 = localResultSet.getString(2);
				/* 336 */String str2 = localResultSet.getString(3);
				/* 337 */int j = localResultSet.getInt(4);
				/* 338 */String str3 = localResultSet.getString(5);
				/* 339 */if ((str3 != null)
						&& (!str3.equals(this.sqlName.getSchema())))
					/* 340 */throw new NodeMapException();
				/* 341 */if (str1.length() <= 0)
					/*     */continue;
				/* 343 */SQLName localSQLName = new SQLName(
						this.sqlName.getSchema(), str1, this.connection);
				/* 344 */TypeTreeElement localTypeTreeElement = null;
				/* 345 */if (localHashMap.containsKey(localSQLName)) {
					/* 346 */localTypeTreeElement = (TypeTreeElement) localHashMap
							.get(localSQLName);
					/*     */}
				/*     */else {
					/* 349 */localTypeTreeElement = new TypeTreeElement(
							this.sqlName.getSchema(), str1);
					/* 350 */localHashMap.put(localSQLName,
							localTypeTreeElement);
					/*     */}
				/* 352 */localTypeTreeElement.putChild(
						this.sqlName.getSchema(), str2, j);
				/*     */}
			/*     */}
		/*     */catch (NodeMapException localNodeMapException) {
			/* 356 */localHashMap = null;
			localNodeMapException.printStackTrace(System.err);
		} finally {
			/* 357 */if (localResultSet != null)
				localResultSet.close();
			if (localPreparedStatement != null)
				localPreparedStatement.close();
			/*     */}
		/* 358 */return localHashMap;
		/*     */}

	/*     */
	/*     */Map getNodeMapFromAllTypes()
	/*     */throws SQLException
	/*     */{
		/* 382 */HashMap localHashMap = new HashMap();
		/* 383 */PreparedStatement localPreparedStatement = null;
		/* 384 */ResultSet localResultSet = null;
		/*     */try
		/*     */{
			/* 387 */localPreparedStatement = this.connection
					.prepareStatement(getAllTypeTreeSql);
			/* 388 */localPreparedStatement.setString(1,
					this.sqlName.getSimpleName());
			/* 389 */localPreparedStatement.setString(2,
					this.sqlName.getSchema());
			/* 390 */localResultSet = localPreparedStatement.executeQuery();
			/*     */
			/* 392 */while (localResultSet.next())
			/*     */{
				/* 394 */String str1 = localResultSet.getString(1);
				/* 395 */String str2 = localResultSet.getString(2);
				/* 396 */String str3 = localResultSet.getString(3);
				/* 397 */int i = localResultSet.getInt(4);
				/* 398 */String str4 = localResultSet.getString(5);
				/* 399 */if (str4 == null)
					str4 = "SYS";
				/* 400 */if (str1.length() <= 0)
					/*     */continue;
				/* 402 */SQLName localSQLName = new SQLName(str2, str1,
						this.connection);
				/* 403 */TypeTreeElement localTypeTreeElement = null;
				/* 404 */if (localHashMap.containsKey(localSQLName)) {
					/* 405 */localTypeTreeElement = (TypeTreeElement) localHashMap
							.get(localSQLName);
					/*     */}
				/*     */else {
					/* 408 */localTypeTreeElement = new TypeTreeElement(str2,
							str1);
					/* 409 */localHashMap.put(localSQLName,
							localTypeTreeElement);
					/*     */}
				/* 411 */localTypeTreeElement.putChild(str4, str3, i);
				/*     */}
			/*     */}
		/*     */finally {
			/* 415 */if (localResultSet != null)
				localResultSet.close();
			if (localPreparedStatement != null)
				localPreparedStatement.close();
			/*     */}
		/* 416 */return localHashMap;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.oracore.OracleNamedType
 * JD-Core Version: 0.6.0
 */