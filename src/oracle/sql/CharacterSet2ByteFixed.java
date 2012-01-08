/*     */package oracle.sql;

/*     */
/*     */import java.sql.SQLException;
/*     */
import oracle.sql.converter.CharacterConverters;

/*     */
/*     */class CharacterSet2ByteFixed extends CharacterSetWithConverter
/*     */{
	/*     */static final String CHAR_CONV_SUPERCLASS_NAME = "oracle.sql.converter.CharacterConverter2ByteFixed";
	/*     */static final short MAX_7BIT = 127;
	/*     */static final short MIN_8BIT_SB = 161;
	/*     */static final short MAX_8BIT_SB = 223;
	/*     */static final short CHARLENGTH = 2;
	/*     */static Class m_charConvSuperclass;

	/*     */
	/*     */CharacterSet2ByteFixed(int paramInt,
			CharacterConverters paramCharacterConverters)
	/*     */{
		/* 80 */super(paramInt, paramCharacterConverters);
		/*     */}

	/*     */
	/*     */static CharacterSet2ByteFixed getInstance(int paramInt,
			CharacterConverters paramCharacterConverters)
	/*     */{
		/* 89 */if (paramCharacterConverters.getGroupId() == 6)
		/*     */{
			/* 91 */return new CharacterSet2ByteFixed(paramInt,
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
		/* 106 */if (paramCharacterWalker.bytes.length > paramCharacterWalker.next)
		/*     */{
			/* 108 */i = i << 8
					| paramCharacterWalker.bytes[paramCharacterWalker.next];
			/* 109 */paramCharacterWalker.next += 1;
			/*     */}
		/*     */else
		/*     */{
			/* 113 */throw new SQLException("destination too small");
			/*     */}
		/*     */
		/* 116 */return i;
		/*     */}

	/*     */
	/*     */void encode(CharacterBuffer paramCharacterBuffer, int paramInt)
			throws SQLException
	/*     */{
		/* 121 */need(paramCharacterBuffer, 2);
		/*     */
		/* 123 */paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte) (paramInt >> 8 & 0xFF);
		/* 124 */paramCharacterBuffer.bytes[(paramCharacterBuffer.next++)] = (byte) (paramInt & 0xFF);
		/*     */}
	/*     */
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.sql.CharacterSet2ByteFixed
 * JD-Core Version: 0.6.0
 */