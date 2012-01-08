package oracle.jdbc.driver;

import java.sql.SQLException;

class AutoKeyInfo extends OracleResultSetMetaData {
	String originalSql;
	String newSql;
	String tableName;
	String[] columnNames;
	int[] columnIndexes;
	int numColumns;
	String[] tableColumnNames;
	int[] tableColumnTypes;
	int[] tableMaxLengths;
	boolean[] tableNullables;
	short[] tableFormOfUses;
	int[] tablePrecisions;
	int[] tableScales;
	String[] tableTypeNames;
	int autoKeyType;
	static final int KEYFLAG = 0;
	static final int COLUMNAME = 1;
	static final int COLUMNINDEX = 2;
	int[] returnTypes;
	Accessor[] returnAccessors;

	AutoKeyInfo(String paramString) {
		/* 53 */this.originalSql = paramString;
		/* 54 */this.autoKeyType = 0;
	}

	AutoKeyInfo(String paramString, String[] paramArrayOfString) {
		/* 61 */this.originalSql = paramString;
		/* 62 */this.columnNames = paramArrayOfString;
		/* 63 */this.autoKeyType = 1;
	}

	AutoKeyInfo(String paramString, int[] paramArrayOfInt) {
		/* 70 */this.originalSql = paramString;
		/* 71 */this.columnIndexes = paramArrayOfInt;
		/* 72 */this.autoKeyType = 2;
	}

	String getNewSql() throws SQLException {
		/* 79 */if (this.newSql != null)
			return this.newSql;

		/* 81 */switch (this.autoKeyType) {
		case 0:
			/* 84 */this.newSql = (this.originalSql + " RETURNING ROWID INTO ?");
			/* 85 */this.returnTypes = new int[1];
			/* 86 */this.returnTypes[0] = 104;
			/* 87 */return this.newSql;
		case 1:
			/* 89 */return getNewSqlByColumnName();
		case 2:
			/* 91 */return getNewSqlByColumnIndexes();
		}

		/* 97 */DatabaseError.throwSqlException(89);
		/* 98 */return null;
	}

	private String getNewSqlByColumnName() throws SQLException {
		/* 106 */this.returnTypes = new int[this.columnNames.length];

		/* 109 */this.columnIndexes = new int[this.columnNames.length];

		/* 111 */StringBuffer localStringBuffer = new StringBuffer(this.originalSql);
		/* 112 */localStringBuffer.append(" RETURNING ");

		/* 115 */for (int j = 0; j < this.columnNames.length; j++) {
			/* 117 */int i = getReturnParamTypeCode(j, this.columnNames[j], this.columnIndexes);
			/* 118 */this.returnTypes[j] = i;

			/* 120 */localStringBuffer.append(this.columnNames[j]);

			/* 122 */if (j >= this.columnNames.length - 1)
				continue;
			localStringBuffer.append(", ");
		}

		/* 125 */localStringBuffer.append(" INTO ");

		/* 127 */for (int j = 0; j < this.columnNames.length - 1; j++) {
			/* 129 */localStringBuffer.append("?, ");
		}

		/* 132 */localStringBuffer.append("?");

		/* 134 */this.newSql = new String(localStringBuffer);
		/* 135 */return this.newSql;
	}

	private String getNewSqlByColumnIndexes() throws SQLException {
		/* 140 */this.returnTypes = new int[this.columnIndexes.length];

		/* 142 */StringBuffer localStringBuffer = new StringBuffer(this.originalSql);
		/* 143 */localStringBuffer.append(" RETURNING ");

		/* 148 */for (int j = 0; j < this.columnIndexes.length; j++) {
			/* 150 */int k = this.columnIndexes[j] - 1;
			/* 151 */if ((k < 0) || (k > this.tableColumnNames.length)) {
				/* 153 */DatabaseError.throwSqlException(68);
			}

			/* 157 */int i = this.tableColumnTypes[k];
			/* 158 */String str = this.tableColumnNames[k];
			/* 159 */this.returnTypes[j] = i;

			/* 161 */localStringBuffer.append(str);

			/* 163 */if (j >= this.columnIndexes.length - 1)
				continue;
			localStringBuffer.append(", ");
		}

		/* 166 */localStringBuffer.append(" INTO ");

		/* 168 */for (int j = 0; j < this.columnIndexes.length - 1; j++) {
			/* 170 */localStringBuffer.append("?, ");
		}

		/* 173 */localStringBuffer.append("?");

		/* 175 */this.newSql = new String(localStringBuffer);
		/* 176 */return this.newSql;
	}

	private final int getReturnParamTypeCode(int paramInt, String paramString, int[] paramArrayOfInt) throws SQLException {
		/* 184 */for (int i = 0; i < this.tableColumnNames.length; i++) {
			/* 186 */if (!paramString.equalsIgnoreCase(this.tableColumnNames[i]))
				continue;
			/* 188 */paramArrayOfInt[paramInt] = (i + 1);
			/* 189 */return this.tableColumnTypes[i];
		}

		/* 194 */DatabaseError.throwSqlException(68);

		/* 199 */return -1;
	}

	static final boolean isInsertSqlStmt(String paramString) throws SQLException {
		/* 208 */if (paramString == null) {
			/* 210 */DatabaseError.throwSqlException(68);
		}

		/* 213 */return paramString.trim().toUpperCase().startsWith("INSERT");
	}

	String getTableName() throws SQLException {
		/* 218 */if (this.tableName != null)
			return this.tableName;

		/* 220 */String str = this.originalSql.trim().toUpperCase();

		/* 222 */int i = str.indexOf("INSERT");
		/* 223 */i = str.indexOf("INTO", i);

		/* 225 */if (i < 0) {
			/* 226 */DatabaseError.throwSqlException(68);
		}
		/* 228 */int j = str.length();
		/* 229 */int k = i + 5;

		/* 231 */while ((k < j) && (str.charAt(k) == ' '))
			k++;

		/* 233 */if (k >= j) {
			/* 234 */DatabaseError.throwSqlException(68);
		}
		/* 236 */int m = k + 1;

		/* 239 */while ((m < j) && (str.charAt(m) != ' ') && (str.charAt(m) != '('))
			m++;

		/* 241 */if (k == m - 1) {
			/* 242 */DatabaseError.throwSqlException(68);
		}
		/* 244 */this.tableName = str.substring(k, m);

		/* 246 */return this.tableName;
	}

	void allocateSpaceForDescribedData(int paramInt) throws SQLException {
		/* 251 */this.numColumns = paramInt;

		/* 253 */this.tableColumnNames = new String[paramInt];
		/* 254 */this.tableColumnTypes = new int[paramInt];
		/* 255 */this.tableMaxLengths = new int[paramInt];
		/* 256 */this.tableNullables = new boolean[paramInt];
		/* 257 */this.tableFormOfUses = new short[paramInt];
		/* 258 */this.tablePrecisions = new int[paramInt];
		/* 259 */this.tableScales = new int[paramInt];
		/* 260 */this.tableTypeNames = new String[paramInt];
	}

	void fillDescribedData(int paramInt1, String paramString1, int paramInt2, int paramInt3, boolean paramBoolean, short paramShort, int paramInt4, int paramInt5,
			String paramString2) throws SQLException {
		/* 268 */this.tableColumnNames[paramInt1] = paramString1;
		/* 269 */this.tableColumnTypes[paramInt1] = paramInt2;
		/* 270 */this.tableMaxLengths[paramInt1] = paramInt3;
		/* 271 */this.tableNullables[paramInt1] = paramBoolean;
		/* 272 */this.tableFormOfUses[paramInt1] = paramShort;
		/* 273 */this.tablePrecisions[paramInt1] = paramInt4;
		/* 274 */this.tableScales[paramInt1] = paramInt5;
		/* 275 */this.tableTypeNames[paramInt1] = paramString2;
	}

	void initMetaData(OracleReturnResultSet paramOracleReturnResultSet) throws SQLException {
		/* 280 */if (this.returnAccessors != null)
			return;

		/* 282 */this.returnAccessors = paramOracleReturnResultSet.returnAccessors;

		/* 285 */switch (this.autoKeyType) {
		case 0:
			/* 288 */initMetaDataKeyFlag();
			/* 289 */break;
		case 1:
		case 2:
			/* 292 */initMetaDataColumnIndexes();
		}
	}

	void initMetaDataKeyFlag() throws SQLException {
		/* 300 */this.returnAccessors[0].columnName = "ROWID";
		/* 301 */this.returnAccessors[0].describeType = 104;
		/* 302 */this.returnAccessors[0].describeMaxLength = 4;
		/* 303 */this.returnAccessors[0].nullable = true;
		/* 304 */this.returnAccessors[0].precision = 0;
		/* 305 */this.returnAccessors[0].scale = 0;
		/* 306 */this.returnAccessors[0].formOfUse = 0;
	}

	void initMetaDataColumnIndexes() throws SQLException {
		/* 314 */for (int j = 0; j < this.returnAccessors.length; j++) {
			/* 316 */Accessor localAccessor = this.returnAccessors[j];
			/* 317 */int i = this.columnIndexes[j] - 1;

			/* 319 */localAccessor.columnName = this.tableColumnNames[i];
			/* 320 */localAccessor.describeType = this.tableColumnTypes[i];
			/* 321 */localAccessor.describeMaxLength = this.tableMaxLengths[i];
			/* 322 */localAccessor.nullable = this.tableNullables[i];
			/* 323 */localAccessor.precision = this.tablePrecisions[i];
			/* 324 */localAccessor.scale = this.tablePrecisions[i];
			/* 325 */localAccessor.formOfUse = this.tableFormOfUses[i];
		}
	}

	int getValidColumnIndex(int paramInt) throws SQLException {
		/* 335 */if ((paramInt <= 0) || (paramInt > this.returnAccessors.length)) {
			/* 336 */DatabaseError.throwSqlException(3);
		}
		/* 338 */return paramInt - 1;
	}

	public int getColumnCount() throws SQLException {
		/* 343 */return this.returnAccessors.length;
	}

	public String getColumnName(int paramInt) throws SQLException {
		/* 349 */if ((paramInt <= 0) || (paramInt > this.returnAccessors.length)) {
			/* 350 */DatabaseError.throwSqlException(3);
		}
		/* 352 */return this.returnAccessors[(paramInt - 1)].columnName;
	}

	public String getTableName(int paramInt) throws SQLException {
		/* 358 */if ((paramInt <= 0) || (paramInt > this.returnAccessors.length)) {
			/* 359 */DatabaseError.throwSqlException(3);
		}
		/* 361 */return getTableName();
	}

	Accessor[] getDescription() throws SQLException {
		/* 366 */return this.returnAccessors;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.AutoKeyInfo
 * JD-Core Version: 0.6.0
 */