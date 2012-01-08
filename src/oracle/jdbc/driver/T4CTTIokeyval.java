/*     */package oracle.jdbc.driver;

/*     */
/*     */import java.io.IOException;
/*     */
import java.sql.SQLException;

/*     */
/*     */class T4CTTIokeyval extends T4CTTIfun
/*     */{
	/*     */static final byte KVASET_KPDUSR = 1;
	/*     */static final byte KVACLA_KPDUSR = 2;
	/*     */private byte[] namespaceByteArr;
	/*     */private char[] charArr;
	/*     */private byte[][] attrArr;
	/*     */private int[] attrArrSize;
	/*     */private byte[][] valueArr;
	/*     */private int[] valueArrSize;
	/*     */private byte[] kvalflg;

	/*     */
	/*     */T4CTTIokeyval(T4CMAREngine paramT4CMAREngine)
	/*     */throws IOException, SQLException
	/*     */{
		/* 60 */super(17, 0);
		/*     */
		/* 62 */setMarshalingEngine(paramT4CMAREngine);
		/* 63 */setFunCode(154);
		/*     */
		/* 65 */this.namespaceByteArr = new byte[100];
		/* 66 */this.charArr = new char[100];
		/*     */
		/* 68 */this.attrArr = new byte[10][];
		/* 69 */this.attrArrSize = new int[10];
		/* 70 */this.valueArr = new byte[10][];
		/* 71 */this.valueArrSize = new int[10];
		/*     */
		/* 73 */this.kvalflg = new byte[10];
		/*     */}

	/*     */
	/*     */void marshal(Namespace paramNamespace) throws IOException,
			SQLException
	/*     */{
		/* 78 */String str1 = paramNamespace.name;
		/* 79 */String[] arrayOfString1 = paramNamespace.keys;
		/* 80 */String[] arrayOfString2 = paramNamespace.values;
		/* 81 */boolean bool = paramNamespace.clear;
		/* 82 */int i = paramNamespace.nbPairs;
		/*     */
		/* 85 */int j = str1.length() * this.meg.conv.cMaxCharSize;
		/* 86 */if (j > this.namespaceByteArr.length)
			/* 87 */this.namespaceByteArr = new byte[j];
		/* 88 */if (str1.length() > this.charArr.length)
			/* 89 */this.charArr = new char[str1.length()];
		/* 90 */str1.getChars(0, str1.length(), this.charArr, 0);
		/* 91 */int k = this.meg.conv.javaCharsToCHARBytes(this.charArr, 0,
				this.namespaceByteArr, 0, str1.length());
		/*     */
		/* 98 */if (i > 0)
		/*     */{
			/* 100 */if (i > this.attrArr.length)
			/*     */{
				/* 102 */this.attrArr = new byte[i][];
				/* 103 */this.attrArrSize = new int[i];
				/* 104 */this.valueArr = new byte[i][];
				/* 105 */this.valueArrSize = new int[i];
				/* 106 */this.kvalflg = new byte[i];
				/*     */}
			/*     */
			/* 109 */for (int m = 0; m < i; m++)
			/*     */{
				/* 111 */String str2 = arrayOfString1[m];
				/* 112 */String str3 = arrayOfString2[m];
				/*     */
				/* 114 */int i1 = str2.length() * this.meg.conv.cMaxCharSize;
				/* 115 */if ((this.attrArr[m] == null)
						|| (this.attrArr[m].length < i1)) {
					/* 116 */this.attrArr[m] = new byte[i1];
					/*     */}
				/* 118 */int i2 = str3.length() * this.meg.conv.cMaxCharSize;
				/* 119 */if ((this.valueArr[m] == null)
						|| (this.valueArr[m].length < i2)) {
					/* 120 */this.valueArr[m] = new byte[i2];
					/*     */}
				/* 122 */if (str2.length() > this.charArr.length)
					/* 123 */this.charArr = new char[str2.length()];
				/* 124 */str2.getChars(0, str2.length(), this.charArr, 0);
				/* 125 */this.attrArrSize[m] = this.meg.conv
						.javaCharsToCHARBytes(this.charArr, 0, this.attrArr[m],
								0, str2.length());
				/*     */
				/* 130 */if (str3.length() > this.charArr.length)
					/* 131 */this.charArr = new char[str3.length()];
				/* 132 */str3.getChars(0, str3.length(), this.charArr, 0);
				/* 133 */this.valueArrSize[m] = this.meg.conv
						.javaCharsToCHARBytes(this.charArr, 0,
								this.valueArr[m], 0, str3.length());
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 140 */marshalFunHeader();
		/*     */
		/* 142 */this.meg.marshalPTR();
		/* 143 */this.meg.marshalUB4(k);
		/* 144 */if (i > 0)
			/* 145 */this.meg.marshalPTR();
		/*     */else
			/* 147 */this.meg.marshalNULLPTR();
		/* 148 */this.meg.marshalUB4(i);
		/* 149 */int n = 0;
		/* 150 */if (i > 0)
			/* 151 */n = 1;
		/* 152 */if (bool)
			/* 153 */n |= 2;
		/* 154 */this.meg.marshalUB2(n);
		/* 155 */this.meg.marshalNULLPTR();
		/*     */
		/* 159 */this.meg.marshalCHR(this.namespaceByteArr, 0, k);
		/* 160 */if (i > 0)
			/* 161 */this.meg.marshalKEYVAL(this.attrArr, this.attrArrSize,
					this.valueArr, this.valueArrSize, this.kvalflg, i);
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.jdbc.driver.T4CTTIokeyval
 * JD-Core Version: 0.6.0
 */