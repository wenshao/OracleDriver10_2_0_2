/*     */ package oracle.jdbc.oracore;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.INTERVALDS;
/*     */ import oracle.sql.INTERVALYM;
/*     */ 
/*     */ public class OracleTypeINTERVAL extends OracleType
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1394800182554224957L;
/*  57 */   final int LDIINTYEARMONTH = 7;
/*  58 */   final int LDIINTDAYSECOND = 10;
/*     */ 
/*  60 */   final int SIZE_INTERVAL_YM = 5;
/*  61 */   final int SIZE_INTERVAL_DS = 11;
/*     */ 
/*  63 */   byte typeId = 0;
/*  64 */   int scale = 0;
/*  65 */   int precision = 0;
/*     */ 
/* 268 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:26_PST_2006";
/*     */ 
/*     */   protected OracleTypeINTERVAL()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleTypeINTERVAL(OracleConnection paramOracleConnection)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getTypeCode()
/*     */   {
/*  89 */     if (this.typeId == 7)
/*  90 */       return -103;
/*  91 */     if (this.typeId == 10) {
/*  92 */       return -104;
/*     */     }
/*  94 */     return 1111;
/*     */   }
/*     */ 
/*     */   public void parseTDSrec(TDSReader paramTDSReader)
/*     */     throws SQLException
/*     */   {
/* 104 */     this.typeId = paramTDSReader.readByte();
/* 105 */     this.precision = paramTDSReader.readByte();
/* 106 */     this.scale = paramTDSReader.readByte();
/*     */   }
/*     */ 
/*     */   public int getScale() throws SQLException
/*     */   {
/* 111 */     return this.scale;
/*     */   }
/*     */ 
/*     */   public int getPrecision() throws SQLException
/*     */   {
/* 116 */     return this.precision;
/*     */   }
/*     */ 
/*     */   public void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 127 */     this.typeId = paramObjectInputStream.readByte();
/* 128 */     this.precision = paramObjectInputStream.readByte();
/* 129 */     this.scale = paramObjectInputStream.readByte();
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 138 */     paramObjectOutputStream.writeByte(this.typeId);
/* 139 */     paramObjectOutputStream.writeByte(this.precision);
/* 140 */     paramObjectOutputStream.writeByte(this.scale);
/*     */   }
/*     */ 
/*     */   protected Object toObject(byte[] paramArrayOfByte, int paramInt, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 146 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 147 */       return null;
/*     */     }
/* 149 */     switch (paramInt)
/*     */     {
/*     */     case 1:
/* 153 */       if (paramArrayOfByte.length == 5)
/* 154 */         return new INTERVALYM(paramArrayOfByte);
/* 155 */       if (paramArrayOfByte.length != 11) break;
/* 156 */       return new INTERVALDS(paramArrayOfByte);
/*     */     case 2:
/* 161 */       if (paramArrayOfByte.length == 5)
/* 162 */         return INTERVALYM.toString(paramArrayOfByte);
/* 163 */       if (paramArrayOfByte.length != 11) break;
/* 164 */       return INTERVALDS.toString(paramArrayOfByte);
/*     */     case 3:
/* 169 */       return paramArrayOfByte;
/*     */     default:
/* 172 */       DatabaseError.throwSqlException(59);
/*     */     }
/*     */ 
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Object paramObject, OracleConnection paramOracleConnection)
/*     */     throws SQLException
/*     */   {
/* 191 */     Object localObject = null;
/*     */ 
/* 193 */     if (paramObject != null)
/*     */     {
/* 195 */       if (((paramObject instanceof INTERVALYM)) || ((paramObject instanceof INTERVALDS)))
/*     */       {
/* 197 */         localObject = (Datum)paramObject;
/* 198 */       } else if ((paramObject instanceof String))
/*     */       {
/*     */         try
/*     */         {
/* 205 */           localObject = new INTERVALDS((String)paramObject);
/*     */         }
/*     */         catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
/*     */         {
/* 209 */           localObject = new INTERVALYM((String)paramObject);
/*     */         }
/*     */       }
/*     */       else {
/* 213 */         DatabaseError.throwSqlException(59, paramObject);
/*     */       }
/*     */     }
/*     */ 
/* 217 */     return (Datum)localObject;
/*     */   }
/*     */ 
/*     */   protected Object unpickle80rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 239 */     DatabaseError.throwSqlException(90);
/*     */ 
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object unpickle81rec(UnpickleContext paramUnpickleContext, int paramInt1, int paramInt2, Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 258 */     DatabaseError.throwSqlException(90);
/*     */ 
/* 260 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.oracore.OracleTypeINTERVAL
 * JD-Core Version:    0.6.0
 */