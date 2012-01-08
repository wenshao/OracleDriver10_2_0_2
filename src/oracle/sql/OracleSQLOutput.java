/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLOutput;
/*     */ import java.sql.Struct;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import oracle.jdbc.OracleConnection;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ 
/*     */ public class OracleSQLOutput
/*     */   implements SQLOutput
/*     */ {
/*     */   private StructDescriptor descriptor;
/*     */   private Object[] attributes;
/*     */   private int index;
/*     */   private OracleConnection conn;
/* 728 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public OracleSQLOutput(StructDescriptor paramStructDescriptor, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/*  96 */     this.descriptor = paramStructDescriptor;
/*  97 */     this.attributes = new Object[paramStructDescriptor.getLength()];
/*  98 */     this.conn = paramOracleConnection;
/*  99 */     this.index = 0;
/*     */   }
/*     */ 
/*     */   public STRUCT getSTRUCT()
/*     */     throws SQLException
/*     */   {
/* 114 */     return new STRUCT(this.descriptor, this.conn, this.attributes);
/*     */   }
/*     */ 
/*     */   public void writeString(String paramString)
/*     */     throws SQLException
/*     */   {
/* 133 */     this.attributes[(this.index++)] = paramString;
/*     */   }
/*     */ 
/*     */   public void writeBoolean(boolean paramBoolean)
/*     */     throws SQLException
/*     */   {
/* 146 */     this.attributes[(this.index++)] = new Boolean(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void writeByte(byte paramByte)
/*     */     throws SQLException
/*     */   {
/* 160 */     this.attributes[(this.index++)] = new Integer(paramByte);
/*     */   }
/*     */ 
/*     */   public void writeShort(short paramShort)
/*     */     throws SQLException
/*     */   {
/* 173 */     this.attributes[(this.index++)] = new Integer(paramShort);
/*     */   }
/*     */ 
/*     */   public void writeInt(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 186 */     this.attributes[(this.index++)] = new Integer(paramInt);
/*     */   }
/*     */ 
/*     */   public void writeLong(long paramLong)
/*     */     throws SQLException
/*     */   {
/* 199 */     this.attributes[(this.index++)] = new Long(paramLong);
/*     */   }
/*     */ 
/*     */   public void writeFloat(float paramFloat)
/*     */     throws SQLException
/*     */   {
/* 212 */     this.attributes[(this.index++)] = new Float(paramFloat);
/*     */   }
/*     */ 
/*     */   public void writeDouble(double paramDouble)
/*     */     throws SQLException
/*     */   {
/* 225 */     this.attributes[(this.index++)] = new Double(paramDouble);
/*     */   }
/*     */ 
/*     */   public void writeBigDecimal(BigDecimal paramBigDecimal)
/*     */     throws SQLException
/*     */   {
/* 238 */     this.attributes[(this.index++)] = paramBigDecimal;
/*     */   }
/*     */ 
/*     */   public void writeBytes(byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 251 */     this.attributes[(this.index++)] = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   public void writeDate(Date paramDate)
/*     */     throws SQLException
/*     */   {
/* 264 */     this.attributes[(this.index++)] = paramDate;
/*     */   }
/*     */ 
/*     */   public void writeTime(Time paramTime)
/*     */     throws SQLException
/*     */   {
/* 277 */     this.attributes[(this.index++)] = paramTime;
/*     */   }
/*     */ 
/*     */   public void writeTimestamp(Timestamp paramTimestamp)
/*     */     throws SQLException
/*     */   {
/* 290 */     this.attributes[(this.index++)] = paramTimestamp;
/*     */   }
/*     */ 
/*     */   public void writeCharacterStream(Reader paramReader)
/*     */     throws SQLException
/*     */   {
/* 304 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 306 */     char[] arrayOfChar = new char[100];
/* 307 */     int i = 0;
/*     */     try
/*     */     {
/* 313 */       while ((i = paramReader.read(arrayOfChar)) != -1)
/*     */       {
/* 315 */         localStringBuffer.append(arrayOfChar, 0, i);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 323 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/* 326 */     String str = localStringBuffer.substring(0, localStringBuffer.length());
/*     */ 
/* 330 */     this.attributes[(this.index++)] = str;
/*     */   }
/*     */ 
/*     */   public void writeAsciiStream(InputStream paramInputStream)
/*     */     throws SQLException
/*     */   {
/* 347 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 349 */     byte[] arrayOfByte = new byte[100];
/* 350 */     char[] arrayOfChar = new char[100];
/* 351 */     int i = 0;
/*     */     try
/*     */     {
/* 355 */       while ((i = paramInputStream.read(arrayOfByte)) != -1)
/*     */       {
/* 357 */         for (int j = 0; j < i; j++) {
/* 358 */           arrayOfChar[j] = (char)arrayOfByte[j];
/*     */         }
/* 360 */         localStringBuffer.append(arrayOfChar, 0, i);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 368 */       DatabaseError.throwSqlException(localIOException);
/*     */     }
/*     */ 
/* 371 */     String str = localStringBuffer.substring(0, localStringBuffer.length());
/*     */ 
/* 375 */     this.attributes[(this.index++)] = str;
/*     */   }
/*     */ 
/*     */   public void writeBinaryStream(InputStream paramInputStream)
/*     */     throws SQLException
/*     */   {
/* 393 */     writeAsciiStream(paramInputStream);
/*     */   }
/*     */ 
/*     */   public void writeObject(SQLData paramSQLData)
/*     */     throws SQLException
/*     */   {
/* 419 */     STRUCT localSTRUCT = null;
/*     */ 
/* 421 */     if (paramSQLData != null)
/*     */     {
/* 423 */       StructDescriptor localStructDescriptor = StructDescriptor.createDescriptor(paramSQLData.getSQLTypeName(), this.conn);
/*     */ 
/* 426 */       SQLOutput localSQLOutput = localStructDescriptor.toJdbc2SQLOutput();
/*     */ 
/* 428 */       paramSQLData.writeSQL(localSQLOutput);
/*     */ 
/* 430 */       localSTRUCT = ((OracleSQLOutput)localSQLOutput).getSTRUCT();
/*     */     }
/*     */ 
/* 433 */     writeStruct(localSTRUCT);
/*     */   }
/*     */ 
/*     */   public void writeObject(Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 451 */     if ((paramObject != null) && ((paramObject instanceof SQLData)))
/* 452 */       writeObject((SQLData)paramObject);
/*     */     else
/* 454 */       this.attributes[(this.index++)] = paramObject;
/*     */   }
/*     */ 
/*     */   public void writeRef(Ref paramRef)
/*     */     throws SQLException
/*     */   {
/* 471 */     this.attributes[(this.index++)] = paramRef;
/*     */   }
/*     */ 
/*     */   public void writeBlob(Blob paramBlob)
/*     */     throws SQLException
/*     */   {
/* 484 */     this.attributes[(this.index++)] = paramBlob;
/*     */   }
/*     */ 
/*     */   public void writeClob(Clob paramClob)
/*     */     throws SQLException
/*     */   {
/* 497 */     this.attributes[(this.index++)] = paramClob;
/*     */   }
/*     */ 
/*     */   public void writeStruct(Struct paramStruct)
/*     */     throws SQLException
/*     */   {
/* 510 */     this.attributes[(this.index++)] = paramStruct;
/*     */   }
/*     */ 
/*     */   public void writeArray(Array paramArray)
/*     */     throws SQLException
/*     */   {
/* 523 */     this.attributes[(this.index++)] = paramArray;
/*     */   }
/*     */ 
/*     */   public void writeOracleObject(Datum paramDatum)
/*     */     throws SQLException
/*     */   {
/* 538 */     this.attributes[(this.index++)] = paramDatum;
/*     */   }
/*     */ 
/*     */   public void writeRef(REF paramREF)
/*     */     throws SQLException
/*     */   {
/* 553 */     this.attributes[(this.index++)] = paramREF;
/*     */   }
/*     */ 
/*     */   public void writeBlob(BLOB paramBLOB)
/*     */     throws SQLException
/*     */   {
/* 568 */     this.attributes[(this.index++)] = paramBLOB;
/*     */   }
/*     */ 
/*     */   public void writeBfile(BFILE paramBFILE)
/*     */     throws SQLException
/*     */   {
/* 583 */     this.attributes[(this.index++)] = paramBFILE;
/*     */   }
/*     */ 
/*     */   public void writeClob(CLOB paramCLOB)
/*     */     throws SQLException
/*     */   {
/* 598 */     this.attributes[(this.index++)] = paramCLOB;
/*     */   }
/*     */ 
/*     */   public void writeStruct(STRUCT paramSTRUCT)
/*     */     throws SQLException
/*     */   {
/* 613 */     this.attributes[(this.index++)] = paramSTRUCT;
/*     */   }
/*     */ 
/*     */   public void writeArray(ARRAY paramARRAY)
/*     */     throws SQLException
/*     */   {
/* 628 */     this.attributes[(this.index++)] = paramARRAY;
/*     */   }
/*     */ 
/*     */   public void writeNUMBER(NUMBER paramNUMBER)
/*     */     throws SQLException
/*     */   {
/* 643 */     this.attributes[(this.index++)] = paramNUMBER;
/*     */   }
/*     */ 
/*     */   public void writeCHAR(CHAR paramCHAR)
/*     */     throws SQLException
/*     */   {
/* 658 */     this.attributes[(this.index++)] = paramCHAR;
/*     */   }
/*     */ 
/*     */   public void writeDATE(DATE paramDATE)
/*     */     throws SQLException
/*     */   {
/* 673 */     this.attributes[(this.index++)] = paramDATE;
/*     */   }
/*     */ 
/*     */   public void writeRAW(RAW paramRAW)
/*     */     throws SQLException
/*     */   {
/* 688 */     this.attributes[(this.index++)] = paramRAW;
/*     */   }
/*     */ 
/*     */   public void writeROWID(ROWID paramROWID)
/*     */     throws SQLException
/*     */   {
/* 703 */     this.attributes[(this.index++)] = paramROWID;
/*     */   }
/*     */ 
/*     */   public void writeURL(URL paramURL)
/*     */     throws SQLException
/*     */   {
/* 722 */     DatabaseError.throwUnsupportedFeatureSqlException();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.OracleSQLOutput
 * JD-Core Version:    0.6.0
 */