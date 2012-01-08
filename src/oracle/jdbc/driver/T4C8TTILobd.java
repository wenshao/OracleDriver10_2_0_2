/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4C8TTILobd extends T4CTTIMsg
/*     */{
	/*     */static final int LOBD_STATE0 = 0;
	/*     */static final int LOBD_STATE1 = 1;
	/*     */static final int LOBD_STATE2 = 2;
	/*     */static final int LOBD_STATE3 = 3;
	/*     */static final int LOBD_STATE_EXIT = 4;
	/*     */static final short TTCG_LNG = 254;
	/*     */static final short LOBDATALENGTH = 252;
	/* 119 */static byte[] ucs2Char = new byte[2];
	/*     */
	/* 655 */private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
	/*     */public static final boolean TRACE = false;
	/*     */public static final boolean PRIVATE_TRACE = false;
	/*     */public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";

	/*     */
	/*     */T4C8TTILobd(T4CMAREngine paramT4CMAREngine)
	/*     */{
		/* 132 */super(14);
		/*     */
		/* 134 */setMarshalingEngine(paramT4CMAREngine);
		/*     */}

	/*     */
	/*     */void marshalLobData(byte[] paramArrayOfByte, long paramLong1,
			long paramLong2)
	/*     */throws SQLException, IOException
	/*     */{
		/* 173 */long l1 = paramLong2;
		/* 174 */int i = 0;
		/*     */
		/* 177 */marshalTTCcode();
		/*     */
		/* 180 */if (l1 > 252L)
		/*     */{
			/* 182 */i = 1;
			/*     */
			/* 184 */this.meg.marshalUB1(254);
			/*     */}
		/*     */
		/* 188 */long l2 = 0L;
		/*     */
		/* 190 */for (; l1 > 252L; l1 -= 252L)
		/*     */{
			/* 192 */this.meg.marshalUB1(252);
			/* 193 */this.meg.marshalB1Array(paramArrayOfByte,
					(int) (paramLong1 + l2 * 252L), 252);
			/*     */
			/* 190 */l2 += 1L;
			/*     */}
		/*     */
		/* 197 */if (l1 > 0L)
		/*     */{
			/* 199 */this.meg.marshalUB1((short) (int) l1);
			/* 200 */this.meg.marshalB1Array(paramArrayOfByte,
					(int) (paramLong1 + l2 * 252L), (int) l1);
			/*     */}
		/*     */
		/* 205 */if (i == 1)
			/* 206 */this.meg.marshalUB1(0);
		/*     */}

	/*     */
	/*     */void marshalLobDataUB2(byte[] paramArrayOfByte, long paramLong1,
			long paramLong2)
	/*     */throws SQLException, IOException
	/*     */{
		/* 255 */long l1 = paramLong2;
		/* 256 */int i = 0;
		/*     */
		/* 258 */marshalTTCcode();
		/*     */
		/* 261 */if (l1 > 84L)
		/*     */{
			/* 263 */i = 1;
			/*     */
			/* 265 */this.meg.marshalUB1(254);
			/*     */}
		/*     */
		/* 271 */long l2 = 0L;
		/*     */
		/* 273 */for (; l1 > 84L; l1 -= 84L)
		/*     */{
			/* 277 */this.meg.marshalUB1(252);
			/*     */
			/* 282 */for (int j = 0; j < 84; j++)
			/*     */{
				/* 286 */this.meg.marshalUB1(2);
				/*     */
				/* 289 */this.meg.marshalB1Array(paramArrayOfByte,
						(int) (paramLong1 + l2 * 168L + j * 2), 2);
				/*     */}
			/* 273 */l2 += 1L;
			/*     */}
		/*     */
		/* 295 */if (l1 > 0L)
		/*     */{
			/* 299 */long l3 = l1 * 3L;
			/*     */
			/* 301 */this.meg.marshalUB1((short) (int) l3);
			/*     */
			/* 306 */for (int k = 0; k < l1; k++)
			/*     */{
				/* 310 */this.meg.marshalUB1(2);
				/*     */
				/* 313 */this.meg.marshalB1Array(paramArrayOfByte,
						(int) (paramLong1 + l2 * 168L + k * 2), 2);
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 320 */if (i == 1)
			/* 321 */this.meg.marshalUB1(0);
		/*     */}

	/*     */
	/*     */long unmarshalLobData(byte[] paramArrayOfByte)
	/*     */throws SQLException, IOException
	/*     */{
		/* 388 */long l1 = 0L;
		/* 389 */long l2 = 0L;
		/* 390 */int i = 0;
		/*     */
		/* 393 */int j = 0;
		/*     */
		/* 396 */while (j != 4)
		/*     */{
			/* 398 */switch (j)
			/*     */{
			/*     */case 0:
				/* 403 */i = this.meg.unmarshalUB1();
				/*     */
				/* 408 */if (i == 254) {
					/* 409 */j = 2;
					continue;
					/*     */}
				/*     */
				/* 415 */j = 1;
				/*     */
				/* 417 */break;
			/*     */case 1:
				/* 425 */this.meg.getNBytes(paramArrayOfByte, (int) l2, i);
				/*     */
				/* 427 */l1 += i;
				/* 428 */j = 4;
				/*     */
				/* 430 */break;
			/*     */case 2:
				/* 439 */i = this.meg.unmarshalUB1();
				/*     */
				/* 442 */if (i > 0) {
					/* 443 */j = 3;
					continue;
					/*     */}
				/*     */
				/* 448 */j = 4;
				/*     */
				/* 450 */break;
			/*     */case 3:
				/* 459 */this.meg.getNBytes(paramArrayOfByte, (int) l2, i);
				/*     */
				/* 462 */l1 += i;
				/*     */
				/* 465 */l2 += i;
				/*     */
				/* 470 */j = 2;
				/*     */
				/* 472 */continue;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 486 */return l1;
		/*     */}

	/*     */
	/*     */long unmarshalClobUB2(byte[] paramArrayOfByte)
	/*     */throws SQLException, IOException
	/*     */{
		/* 547 */long l1 = 0L;
		/* 548 */long l2 = 0L;
		/* 549 */int i = 0;
		/* 550 */int j = 0;
		/* 551 */int k = 0;
		/*     */
		/* 554 */int m = 0;
		/*     */
		/* 557 */while (m != 4)
		/*     */{
			/* 559 */switch (m)
			/*     */{
			/*     */case 0:
				/* 564 */i = this.meg.unmarshalUB1();
				/*     */
				/* 569 */if (i == 254) {
					/* 570 */m = 2;
					continue;
					/*     */}
				/*     */
				/* 576 */m = 1;
				/*     */
				/* 578 */break;
			/*     */case 1:
				/* 586 */for (j = 0; j < i; l2 += 2L)
				/*     */{
					/* 588 */k = this.meg.unmarshalUCS2(paramArrayOfByte, l2);
					/*     */
					/* 586 */j += k;
					/*     */}
				/*     */
				/* 591 */l1 += i;
				/* 592 */m = 4;
				/*     */
				/* 594 */break;
			/*     */case 2:
				/* 603 */i = this.meg.unmarshalUB1();
				/*     */
				/* 606 */if (i > 0) {
					/* 607 */m = 3;
					continue;
					/*     */}
				/*     */
				/* 612 */m = 4;
				/*     */
				/* 614 */break;
			/*     */case 3:
				/* 624 */for (j = 0; j < i; l2 += 2L)
				/*     */{
					/* 626 */k = this.meg.unmarshalUCS2(paramArrayOfByte, l2);
					/*     */
					/* 624 */j += k;
					/*     */}
				/*     */
				/* 630 */l1 += i;
				/*     */
				/* 635 */m = 2;
				/*     */
				/* 637 */continue;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 650 */return l1;
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4C8TTILobd
 * JD-Core Version: 0.6.0
 */