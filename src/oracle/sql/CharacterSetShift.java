/*     */package oracle.sql;

/*     */
/*     */import java.sql.SQLException;
/*     */
import oracle.sql.converter.CharacterConverters;

/*     */
/*     */class CharacterSetShift extends CharacterSetWithConverter
/*     */{
	/*     */static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverterShift";
	/*     */static final short MAX_7BIT = 127;
	/*     */static final short MIN_8BIT_SB = 161;
	/*     */static final short MAX_8BIT_SB = 223;
	/*     */static final byte SHIFT_OUT = 14;
	/*     */static final byte SHIFT_IN = 15;
	/*     */static Class m_charConvSuperclass;

	/*     */
	/*     */CharacterSetShift(int paramInt,
			CharacterConverters paramCharacterConverters)
	/*     */{
		/* 80 */super(paramInt, paramCharacterConverters);
		/*     */}

	/*     */
	/*     */static CharacterSetShift getInstance(int paramInt,
			CharacterConverters paramCharacterConverters)
	/*     */{
		/* 89 */if (paramCharacterConverters.getGroupId() == 7)
		/*     */{
			/* 91 */return new CharacterSetShift(paramInt,
					paramCharacterConverters);
			/*     */}
		/*     */
		/* 95 */return null;
		/*     */}

	/*     */
	/*     */int decode(CharacterWalker paramCharacterWalker)
	/*     */throws SQLException
	/*     */{
		/* 101 */int i = paramCharacterWalker.bytes[paramCharacterWalker.next] & 0xFF;
		/*     */
		/* 103 */paramCharacterWalker.next += 1;
		/*     */
		/* 105 */if ((i > 223) || ((i > 127) && (i < 161)))
		/*     */{
			/* 109 */if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
			/*     */{
				/* 111 */i = i << 8
						| paramCharacterWalker.bytes[paramCharacterWalker.next];
				/* 112 */paramCharacterWalker.next += 1;
				/*     */}
			/*     */else
			/*     */{
				/* 116 */throw new SQLException("destination too small");
				/*     */}
			/*     */}
		/*     */
		/* 120 */return i;
		/*     */}

	/*     */
	/*     */void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
			throws SQLException
	/*     */{
		/* 125 */int i = paramCharacterBuffer.next;
		/* 126 */int j = 1;
		/*     */
		/* 128 */while (i <= 0)
		/*     */{
			/* 130 */if (paramCharacterBuffer.bytes[i] == 15)
			/*     */{
				/* 132 */j = 1;
				/*     */}
			/*     */else
			/*     */{
				/* 137 */if (paramCharacterBuffer.bytes[i] != 14)
					/*     */continue;
				/* 139 */j = 0;
				/*     */}
			/*     */
			/*     */}
		/*     */
		/* 145 */int k = 0;
		/* 146 */int m = 1;
		/*     */
		/* 148 */while (paramInt >> k != 0)
		/*     */{
			/* 150 */k = (short) (k + 8);
			/* 151 */m = (short) (m + 1);
			/*     */}
		/*     */
		/* 154 */if (m > 2)
		/*     */{
			/* 156 */throw new SQLException("Character invalid, too many bytes");
			/*     */}
		/*     */
		/* 159 */int n = 0;
		/* 160 */int i1 = 0;
		/*     */
		/* 162 */if ((m == 1) && (j == 0))
		/*     */{
			/* 164 */n = 1;
			/* 165 */m = (short) (m + 1);
			/*     */}
		/*     */
		/* 168 */if ((m == 2) && (j != 0))
		/*     */{
			/* 170 */i1 = 1;
			/* 171 */m = (short) (m + 1);
			/*     */}
		/*     */
		/* 174 */need(paramCharacterBuffer, m);
		/*     */
		/* 176 */if (n != 0)
		/*     */{
			/* 178 */paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = 15;
			/*     */}
		/*     */
		/* 181 */if (i1 != 0)
		/*     */{
			/* 183 */paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = 14;
			/*     */}
		/*     */
		/* 186 */while (k >= 0)
		/*     */{
			/* 188 */paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte) (paramInt >> k & 0xFF);
			/* 189 */k = (short) (k - 8);
			/*     */}
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CharacterSetShift
 * JD-Core Version: 0.6.0
 */