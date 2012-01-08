/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.converter.CharacterConverterFactory;
/*     */ import oracle.sql.converter.CharacterConverterFactoryJDBC;
/*     */ import oracle.sql.converter.CharacterConverters;
/*     */ 
/*     */ public abstract class CharacterSetWithConverter extends CharacterSet
/*     */ {
/*  67 */   public static CharacterConverterFactory ccFactory = new CharacterConverterFactoryJDBC();
/*     */   CharacterConverters m_converter;
/*     */ 
/*     */   CharacterSetWithConverter(int paramInt, CharacterConverters paramCharacterConverters)
/*     */   {
/*  80 */     super(paramInt);
/*     */ 
/*  82 */     this.m_converter = paramCharacterConverters;
/*     */   }
/*     */ 
/*     */   static CharacterSet getInstance(int paramInt)
/*     */   {
/*  92 */     CharacterConverters localCharacterConverters = ccFactory.make(paramInt);
/*     */ 
/*  94 */     if (localCharacterConverters == null)
/*     */     {
/*  96 */       return null;
/*     */     }
/*     */ 
/* 101 */     Object localObject = null;
/*     */ 
/* 103 */     if ((localObject = CharacterSet1Byte.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 106 */       return localObject;
/*     */     }
/*     */ 
/* 111 */     if ((localObject = CharacterSetSJIS.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 114 */       return localObject;
/*     */     }
/*     */ 
/* 117 */     if ((localObject = CharacterSetShift.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 120 */       return localObject;
/*     */     }
/*     */ 
/* 123 */     if ((localObject = CharacterSet2ByteFixed.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 126 */       return localObject;
/*     */     }
/*     */ 
/* 129 */     if ((localObject = CharacterSetGB18030.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 132 */       return localObject;
/*     */     }
/*     */ 
/* 135 */     if ((localObject = CharacterSet12Byte.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 138 */       return localObject;
/*     */     }
/*     */ 
/* 141 */     if ((localObject = CharacterSetJAEUC.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 144 */       return localObject;
/*     */     }
/*     */ 
/* 147 */     if ((localObject = CharacterSetZHTEUC.getInstance(paramInt, localCharacterConverters)) != null)
/*     */     {
/* 150 */       return localObject;
/*     */     }
/*     */ 
/* 154 */     return (CharacterSet)CharacterSetLCFixed.getInstance(paramInt, localCharacterConverters);
/*     */   }
/*     */ 
/*     */   public boolean isLossyFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 159 */     return paramCharacterSet.getOracleId() != getOracleId();
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleFrom(CharacterSet paramCharacterSet)
/*     */   {
/* 164 */     return paramCharacterSet.getOracleId() == getOracleId();
/*     */   }
/*     */ 
/*     */   public String toStringWithReplacement(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 169 */     return this.m_converter.toUnicodeStringWithReplacement(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 175 */     return this.m_converter.toUnicodeString(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public byte[] convert(String paramString) throws SQLException
/*     */   {
/* 180 */     return this.m_converter.toOracleString(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convertWithReplacement(String paramString)
/*     */   {
/* 185 */     return this.m_converter.toOracleStringWithReplacement(paramString);
/*     */   }
/*     */ 
/*     */   public byte[] convert(CharacterSet paramCharacterSet, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws SQLException
/*     */   {
/* 191 */     if (paramCharacterSet.getOracleId() == getOracleId())
/*     */     {
/* 193 */       return useOrCopy(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */ 
/* 197 */     return convert(paramCharacterSet.toString(paramArrayOfByte, paramInt1, paramInt2));
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.CharacterSetWithConverter
 * JD-Core Version:    0.6.0
 */