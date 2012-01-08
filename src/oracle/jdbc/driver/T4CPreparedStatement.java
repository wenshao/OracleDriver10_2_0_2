package oracle.jdbc.driver;

import java.io.IOException;

import java.sql.SQLException;

class T4CPreparedStatement extends OraclePreparedStatement {
	/* 43 */static final byte[] EMPTY_BYTE = new byte[0];
	T4CConnection t4Connection;
	/* 1225 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	public static final boolean TRACE = false;
	public static final boolean PRIVATE_TRACE = false;
	public static final String BUILD_DATE = "Tue_Jan_24_08:54:32_PST_2006";

	T4CPreparedStatement(PhysicalConnection paramPhysicalConnection, String paramString, int paramInt1, int paramInt2) throws SQLException {
		/* 29 */super(paramPhysicalConnection, paramString, paramPhysicalConnection.defaultBatch, paramPhysicalConnection.defaultRowPrefetch, paramInt1, paramInt2);

		/* 31 */this.nbPostPonedColumns = new int[1];
		/* 32 */this.nbPostPonedColumns[0] = 0;
		/* 33 */this.indexOfPostPonedColumn = new int[1][3];
		/* 34 */this.t4Connection = ((T4CConnection) paramPhysicalConnection);

		/* 36 */this.theRowidBinder = theStaticT4CRowidBinder;
		/* 37 */this.theRowidNullBinder = theStaticT4CRowidNullBinder;
	}

	void doOall8(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) throws SQLException, IOException {
		if ((paramBoolean1) || (paramBoolean4) || (!paramBoolean2) || ((this.sqlKind != 2) && (this.sqlKind != 1) && (this.sqlKind != 4))) {
			this.oacdefSent = null;
		}
		this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.doOall8");

		if ((this.sqlKind != 1) && (this.sqlKind != 4) && (this.sqlKind != 3) && (this.sqlKind != 0) && (this.sqlKind != 2)) {
			DatabaseError.throwSqlException(439);
		}

		int i = this.numberOfDefinePositions;

		if (this.sqlKind == 2)
			i = 0;
		int j;
		if (paramBoolean3) {
			if (this.accessors != null) {
				for (j = 0; j < this.numberOfDefinePositions; j++) {
					if (this.accessors[j] == null)
						continue;
					this.accessors[j].lastRowProcessed = 0;
				}

			}

			if (this.outBindAccessors != null) {
				for (j = 0; j < this.outBindAccessors.length; j++) {
					if (this.outBindAccessors[j] == null)
						continue;
					this.outBindAccessors[j].lastRowProcessed = 0;
				}
			}

		}

		/* 111 */if (this.returnParamAccessors != null) {
			/* 113 */for (j = 0; j < this.numberOfBindPositions; j++) {
				/* 115 */if (this.returnParamAccessors[j] == null)
					continue;
				/* 117 */this.returnParamAccessors[j].lastRowProcessed = 0;
			}

		}

		/* 127 */if (this.bindIndicators != null) {
			/* 129 */j = this.bindIndicators[(this.bindIndicatorSubRange + 2)] & 0xFFFF;

			/* 132 */int k = 0;

			/* 134 */if (this.ibtBindChars != null) {
				/* 135 */k = this.ibtBindChars.length * this.connection.conversion.cMaxCharSize;
			}
			/* 137 */for (int m = 0; m < this.numberOfBindPositions; m++) {
				/* 139 */int n = this.bindIndicatorSubRange + 3 + 10 * m;

				/* 143 */int i1 = this.bindIndicators[(n + 2)] & 0xFFFF;

				/* 147 */if (i1 == 0) {
					continue;
				}
				/* 150 */int i2 = this.bindIndicators[(n + 9)] & 0xFFFF;

				/* 154 */if (i2 == 2) {
					/* 156 */k = Math.max(i1 * this.connection.conversion.maxNCharSize, k);
				} else {
					/* 161 */k = Math.max(i1 * this.connection.conversion.cMaxCharSize, k);
				}

			}

			/* 167 */if (this.tmpBindsByteArray == null) {
				/* 169 */this.tmpBindsByteArray = new byte[k];
			}
			/* 171 */else if (this.tmpBindsByteArray.length < k) {
				/* 173 */this.tmpBindsByteArray = null;
				/* 174 */this.tmpBindsByteArray = new byte[k];
			}

		} else {
			/* 186 */this.tmpBindsByteArray = null;
		}

		/* 195 */allocateTmpByteArray();

		/* 197 */T4C8Oall localT4C8Oall = this.t4Connection.all8;

		/* 199 */this.t4Connection.sendPiggyBackedMessages();

		/* 202 */this.oacdefSent = localT4C8Oall.marshal(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, this.sqlKind, this.cursorId,
				this.sqlObject.getSqlBytes(this.processEscapes, this.convertNcharLiterals), this.rowPrefetch, this.outBindAccessors, this.numberOfBindPositions, this.accessors, i,
				this.bindBytes, this.bindChars, this.bindIndicators, this.bindIndicatorSubRange, this.connection.conversion, this.tmpBindsByteArray, this.parameterStream,
				this.parameterDatum, this.parameterOtype, this, this.ibtBindBytes, this.ibtBindChars, this.ibtBindIndicators, this.oacdefSent, this.definedColumnType,
				this.definedColumnSize, this.definedColumnFormOfUse);
		try {
			/* 216 */localT4C8Oall.receive();

			/* 218 */this.cursorId = localT4C8Oall.getCursorId();
		} catch (SQLException localSQLException) {
			/* 222 */this.cursorId = localT4C8Oall.getCursorId();

			/* 224 */if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110)) {
				/* 227 */this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
			} else {
				/* 232 */throw localSQLException;
			}
		}
	}

	void allocateTmpByteArray() {
		/* 243 */if (this.tmpByteArray == null) {
			/* 248 */this.tmpByteArray = new byte[this.sizeTmpByteArray];
		}
		/* 250 */else if (this.sizeTmpByteArray > this.tmpByteArray.length) {
			/* 255 */this.tmpByteArray = new byte[this.sizeTmpByteArray];
		}
	}

	void allocateRowidAccessor() throws SQLException {
		/* 268 */this.accessors[0] = new T4CRowidAccessor(this, 128, 1, -8, false, this.t4Connection.mare);
	}

	void reparseOnRedefineIfNeeded() throws SQLException {
		/* 283 */this.needToParse = true;
	}

	protected void defineColumnTypeInternal(int paramInt1, int paramInt2, int paramInt3, short paramShort, boolean paramBoolean, String paramString) throws SQLException {
		/* 292 */if (this.connection.disableDefineColumnType) {
			/* 297 */return;
		}

		/* 303 */if (paramInt1 < 1) {
			/* 304 */DatabaseError.throwSqlException(3);
		}
		/* 306 */if (paramBoolean) {
			/* 310 */if ((paramInt2 == 1) || (paramInt2 == 12)) {
				/* 311 */this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 108);
			}

		}
		/* 317 */else if (paramInt3 < 0) {
			/* 318 */DatabaseError.throwSqlException(53);
		}

		/* 321 */if ((this.currentResultSet != null) && (!this.currentResultSet.closed)) {
			/* 322 */DatabaseError.throwSqlException(28);
		}

		/* 329 */int i = paramInt1 - 1;
		int[] arrayOfInt;
		/* 331 */if ((this.definedColumnType == null) || (this.definedColumnType.length <= i)) {
			/* 333 */if (this.definedColumnType == null) {
				/* 334 */this.definedColumnType = new int[(i + 1) * 4];
			} else {
				/* 337 */arrayOfInt = new int[(i + 1) * 4];

				/* 339 */System.arraycopy(this.definedColumnType, 0, arrayOfInt, 0, this.definedColumnType.length);

				/* 342 */this.definedColumnType = arrayOfInt;
			}
		}

		/* 346 */this.definedColumnType[i] = paramInt2;

		/* 348 */if ((this.definedColumnSize == null) || (this.definedColumnSize.length <= i)) {
			/* 350 */if (this.definedColumnSize == null) {
				/* 351 */this.definedColumnSize = new int[(i + 1) * 4];
			} else {
				/* 354 */arrayOfInt = new int[(i + 1) * 4];

				/* 356 */System.arraycopy(this.definedColumnSize, 0, arrayOfInt, 0, this.definedColumnSize.length);

				/* 359 */this.definedColumnSize = arrayOfInt;
			}
		}

		/* 363 */this.definedColumnSize[i] = paramInt3;

		/* 365 */if ((this.definedColumnFormOfUse == null) || (this.definedColumnFormOfUse.length <= i)) {
			/* 367 */if (this.definedColumnFormOfUse == null) {
				/* 368 */this.definedColumnFormOfUse = new int[(i + 1) * 4];
			} else {
				/* 371 */arrayOfInt = new int[(i + 1) * 4];

				/* 373 */System.arraycopy(this.definedColumnFormOfUse, 0, arrayOfInt, 0, this.definedColumnFormOfUse.length);

				/* 376 */this.definedColumnFormOfUse = arrayOfInt;
			}
		}

		/* 380 */this.definedColumnFormOfUse[i] = paramShort;

		/* 382 */if ((this.accessors != null) && (i < this.accessors.length) && (this.accessors[i] != null)) {
			/* 384 */this.accessors[i].definedColumnSize = paramInt3;

			/* 389 */if (((this.accessors[i].internalType == 96) || (this.accessors[i].internalType == 1)) && ((paramInt2 == 1) || (paramInt2 == 12))) {
				/* 393 */if (paramInt3 <= this.accessors[i].oacmxl) {
					/* 399 */this.needToPrepareDefineBuffer = true;
					/* 400 */this.columnsDefinedByUser = true;

					/* 402 */this.accessors[i].initForDataAccess(paramInt2, paramInt3, null);
					/* 403 */this.accessors[i].calculateSizeTmpByteArray();
				}
			}
		}
	}

	public synchronized void clearDefines() throws SQLException {
		/* 411 */super.clearDefines();

		/* 413 */this.definedColumnType = null;
		/* 414 */this.definedColumnSize = null;
		/* 415 */this.definedColumnFormOfUse = null;
	}

	void saveDefineBuffersIfRequired(char[] paramArrayOfChar, byte[] paramArrayOfByte, short[] paramArrayOfShort, boolean paramBoolean) throws SQLException {
		/* 456 */if (paramBoolean) {
			/* 465 */paramArrayOfShort = new short[this.defineIndicators.length];
			/* 466 */i = this.accessors[0].lengthIndexLastRow;
			/* 467 */int j = this.accessors[0].indicatorIndexLastRow;

			/* 469 */for (int n = 1; n <= this.accessors.length; n++) {
				/* 471 */int k = i + this.rowPrefetchAtExecute * n - 1;
				/* 472 */int m = j + this.rowPrefetchAtExecute * n - 1;
				/* 473 */paramArrayOfShort[m] = this.defineIndicators[m];
				/* 474 */paramArrayOfShort[k] = this.defineIndicators[k];
			}

		}

		/* 481 */for (int i = 0; i < this.accessors.length; i++) {
			/* 488 */this.accessors[i].saveDataFromOldDefineBuffers(paramArrayOfByte, paramArrayOfChar, paramArrayOfShort,
					this.rowPrefetchAtExecute != -1 ? this.rowPrefetchAtExecute : this.rowPrefetch, this.rowPrefetch);
		}
	}

	Accessor allocateAccessor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short paramShort, String paramString, boolean paramBoolean) throws SQLException {
		/* 516 */Object localObject = null;

		/* 518 */switch (paramInt1) {
		case 96:
			/* 522 */localObject = new T4CCharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 525 */break;
		case 8:
			/* 528 */if (paramBoolean)
				break;
			/* 530 */localObject = new T4CLongAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);

			/* 533 */break;
		case 1:
			/* 538 */localObject = new T4CVarcharAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 541 */break;
		case 2:
			/* 544 */localObject = new T4CNumberAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 547 */break;
		case 6:
			/* 550 */localObject = new T4CVarnumAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 553 */break;
		case 24:
			/* 556 */if (!paramBoolean) {
				/* 558 */localObject = new T4CLongRawAccessor(this, paramInt3, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
			}

			/* 561 */break;
		case 23:
			/* 566 */if ((paramBoolean) && (paramString != null)) {
				/* 567 */DatabaseError.throwSqlException(12, "sqlType=" + paramInt2);
			}

			/* 570 */if (paramBoolean) {
				/* 571 */localObject = new T4COutRawAccessor(this, paramInt4, paramShort, paramInt2, this.t4Connection.mare);
			} else {
				/* 574 */localObject = new T4CRawAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
			}

			/* 577 */break;
		case 100:
			/* 580 */localObject = new T4CBinaryFloatAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 583 */break;
		case 101:
			/* 586 */localObject = new T4CBinaryDoubleAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 589 */break;
		case 104:
			/* 592 */localObject = new T4CRowidAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 595 */break;
		case 102:
			/* 598 */localObject = new T4CResultSetAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 601 */break;
		case 12:
			/* 604 */localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 607 */break;
		case 113:
			/* 610 */localObject = new T4CBlobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 613 */break;
		case 112:
			/* 616 */localObject = new T4CClobAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 619 */break;
		case 114:
			/* 622 */localObject = new T4CBfileAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 625 */break;
		case 109:
			/* 628 */localObject = new T4CNamedTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 631 */((Accessor) localObject).initMetadata();

			/* 633 */break;
		case 111:
			/* 636 */localObject = new T4CRefTypeAccessor(this, paramString, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 639 */((Accessor) localObject).initMetadata();

			/* 641 */break;
		case 180:
			/* 644 */if (this.connection.v8Compatible) {
				/* 645 */localObject = new T4CDateAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
			} else {
				/* 648 */localObject = new T4CTimestampAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);
			}

			/* 651 */break;
		case 181:
			/* 654 */localObject = new T4CTimestamptzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 657 */break;
		case 231:
			/* 660 */localObject = new T4CTimestampltzAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 663 */break;
		case 182:
			/* 666 */localObject = new T4CIntervalymAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 669 */break;
		case 183:
			/* 672 */localObject = new T4CIntervaldsAccessor(this, paramInt4, paramShort, paramInt2, paramBoolean, this.t4Connection.mare);

			/* 675 */break;
		case 995:
			/* 687 */DatabaseError.throwSqlException(89);
		}

		/* 692 */return (Accessor) localObject;
	}

	void doDescribe(boolean paramBoolean) throws SQLException {
		/* 721 */if (!this.isOpen) {
			/* 723 */DatabaseError.throwSqlException(144);
		}

		try {
			/* 728 */this.t4Connection.sendPiggyBackedMessages();
			/* 729 */this.t4Connection.describe.init(this, 0);
			/* 730 */this.t4Connection.describe.marshal();

			/* 732 */this.accessors = this.t4Connection.describe.receive(this.accessors);
			/* 733 */this.numberOfDefinePositions = this.t4Connection.describe.numuds;

			/* 735 */for (int i = 0; i < this.numberOfDefinePositions; i++) {
				/* 736 */this.accessors[i].initMetadata();
			}

		} catch (IOException localIOException) {
			/* 743 */((T4CConnection) this.connection).handleIOException(localIOException);
			/* 744 */DatabaseError.throwSqlException(localIOException);
		}

		/* 747 */this.describedWithNames = true;
		/* 748 */this.described = true;
	}

	void executeForDescribe() throws SQLException {
		/* 785 */this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.execute_for_describe");
		/* 786 */cleanOldTempLobs();
		try {
			/* 789 */if (this.t4Connection.useFetchSizeWithLongColumn) {
				/* 795 */doOall8(true, true, true, true);
			} else {
				/* 799 */doOall8(true, true, false, true);
			}

		} catch (SQLException localSQLException) {
			/* 807 */throw localSQLException;
		} catch (IOException localIOException) {
			/* 814 */((T4CConnection) this.connection).handleIOException(localIOException);
			/* 815 */DatabaseError.throwSqlException(localIOException);
		} finally {
			/* 819 */this.rowsProcessed = this.t4Connection.all8.rowsProcessed;
			/* 820 */this.validRows = this.t4Connection.all8.getNumRows();
		}

		/* 823 */this.needToParse = false;

		/* 826 */for (int i = 0; i < this.numberOfDefinePositions; i++) {
			/* 827 */this.accessors[i].initMetadata();
		}
		/* 829 */this.needToPrepareDefineBuffer = false;
	}

	void executeMaybeDescribe() throws SQLException {
		/* 837 */if (!this.t4Connection.useFetchSizeWithLongColumn) {
			/* 839 */super.executeMaybeDescribe();
		} else {
			/* 847 */if (this.rowPrefetchChanged) {
				/* 849 */if ((this.streamList == null) && (this.rowPrefetch != this.definesBatchSize)) {
					/* 850 */this.needToPrepareDefineBuffer = true;
				}
				/* 852 */this.rowPrefetchChanged = false;
			}

			/* 855 */if (!this.needToPrepareDefineBuffer) {
				/* 859 */if (this.accessors == null) {
					/* 863 */this.needToPrepareDefineBuffer = true;
					/* 864 */} else if (this.columnsDefinedByUser) {
					/* 865 */this.needToPrepareDefineBuffer = (!checkAccessorsUsable());
				}
			}
			/* 868 */boolean bool = false;
			try {
				/* 873 */this.isExecuting = true;

				/* 875 */if (this.needToPrepareDefineBuffer) {
					/* 877 */executeForDescribe();

					/* 879 */bool = true;
				} else {
					/* 886 */int i = this.accessors.length;

					/* 888 */for (int j = this.numberOfDefinePositions; j < i; j++) {
						/* 890 */Accessor localAccessor = this.accessors[j];

						/* 892 */if (localAccessor != null) {
							/* 893 */localAccessor.rowSpaceIndicator = null;
						}
					}
					/* 896 */executeForRows(bool);
				}

			} catch (SQLException localSQLException) {
				/* 902 */this.needToParse = true;
				/* 903 */throw localSQLException;
			} finally {
				/* 907 */this.isExecuting = false;
			}
		}
	}

	void executeForRows(boolean paramBoolean) throws SQLException {
		try {
			try {
				/* 955 */doOall8(this.needToParse, !paramBoolean, true, false);

				/* 957 */this.needToParse = false;
			} finally {
				/* 961 */this.validRows = this.t4Connection.all8.getNumRows();
			}

		} catch (SQLException localSQLException) {
			/* 969 */throw localSQLException;
		} catch (IOException localIOException) {
			/* 976 */((T4CConnection) this.connection).handleIOException(localIOException);
			/* 977 */DatabaseError.throwSqlException(localIOException);
		}
	}

	void fetch() throws SQLException {
		if (this.streamList != null) {
			while (this.nextStream != null) {
				try {
					this.nextStream.close();
				} catch (IOException localIOException1) {
					((T4CConnection) this.connection).handleIOException(localIOException1);
					DatabaseError.throwSqlException(localIOException1);
				}

				this.nextStream = this.nextStream.nextStream;
			}
		}

		try {
			doOall8(false, false, true, false);

			this.validRows = this.t4Connection.all8.getNumRows();
		} catch (IOException localIOException2) {
			((T4CConnection) this.connection).handleIOException(localIOException2);
			DatabaseError.throwSqlException(localIOException2);
		}
	}

	void continueReadRow(int paramInt) throws SQLException {
		try {
			/* 1058 */if (!this.connection.useFetchSizeWithLongColumn) {
				/* 1060 */T4C8Oall localT4C8Oall = this.t4Connection.all8;

				/* 1062 */localT4C8Oall.continueReadRow(paramInt);
			}

		} catch (IOException localIOException) {
			/* 1070 */((T4CConnection) this.connection).handleIOException(localIOException);
			/* 1071 */DatabaseError.throwSqlException(localIOException);
		} catch (SQLException localSQLException) {
			/* 1075 */if (localSQLException.getErrorCode() == DatabaseError.getVendorCode(110)) {
				/* 1078 */this.sqlWarning = DatabaseError.addSqlWarning(this.sqlWarning, 110);
			} else {
				/* 1083 */throw localSQLException;
			}
		}
	}

	void doClose() throws SQLException {
		/* 1110 */this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.do_close");
		try {
			/* 1114 */if (this.cursorId != 0) {
				/* 1116 */this.t4Connection.cursorToClose[(this.t4Connection.cursorToCloseOffset++)] = this.cursorId;

				/* 1119 */if (this.t4Connection.cursorToCloseOffset >= this.t4Connection.cursorToClose.length) {
					/* 1122 */this.t4Connection.sendPiggyBackedMessages();
				}

			}

		} catch (IOException localIOException) {
			/* 1131 */((T4CConnection) this.connection).handleIOException(localIOException);
			/* 1132 */DatabaseError.throwSqlException(localIOException);
		}

		/* 1135 */this.tmpByteArray = null;
		/* 1136 */this.tmpBindsByteArray = null;
		/* 1137 */this.definedColumnType = null;
		/* 1138 */this.definedColumnSize = null;
		/* 1139 */this.definedColumnFormOfUse = null;
		/* 1140 */this.oacdefSent = null;
	}

	void closeQuery() throws SQLException {
		/* 1162 */this.t4Connection.assertLoggedOn("oracle.jdbc.driver.T4CPreparedStatement.closeQuery");

		/* 1165 */if (this.streamList != null) {
			/* 1169 */while (this.nextStream != null) {
				try {
					/* 1173 */this.nextStream.close();
				} catch (IOException localIOException) {
					/* 1177 */((T4CConnection) this.connection).handleIOException(localIOException);
					/* 1178 */DatabaseError.throwSqlException(localIOException);
				}

				/* 1181 */this.nextStream = this.nextStream.nextStream;
			}
		}
	}

}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.jdbc.driver.T4CPreparedStatement JD-Core Version: 0.6.0
 */