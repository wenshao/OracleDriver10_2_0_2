/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import oracle.jdbc.OraclePreparedStatement;
/*     */ import oracle.jdbc.OracleResultSet;
/*     */ import oracle.jdbc.driver.DatabaseError;
/*     */ import oracle.jdbc.internal.OracleConnection;
/*     */ import oracle.jdbc.oracore.OracleTypeADT;
/*     */ 
/*     */ public class REF extends DatumWithConnection
/*     */   implements Ref, Serializable, Cloneable
/*     */ {
/*     */   static final boolean DEBUG = false;
/*     */   static final long serialVersionUID = 1328446996944583167L;
/*     */   String typename;
/*     */   transient StructDescriptor descriptor;
/* 711 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public String getBaseTypeName()
/*     */     throws SQLException
/*     */   {
/*  79 */     if (this.typename == null)
/*     */     {
/*  81 */       if (this.descriptor != null) {
/*  82 */         this.typename = this.descriptor.getName();
/*     */       }
/*     */       else
/*     */       {
/*  88 */         DatabaseError.throwSqlException(52);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     return this.typename;
/*     */   }
/*     */ 
/*     */   public REF(String paramString, Connection paramConnection, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 108 */     super(paramArrayOfByte);
/*     */ 
/* 113 */     if ((paramConnection == null) || (paramString == null))
/*     */     {
/* 118 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 121 */     this.typename = paramString;
/* 122 */     this.descriptor = null;
/*     */ 
/* 124 */     setPhysicalConnectionOf(paramConnection);
/*     */   }
/*     */ 
/*     */   public REF(StructDescriptor paramStructDescriptor, Connection paramConnection, byte[] paramArrayOfByte)
/*     */     throws SQLException
/*     */   {
/* 142 */     super(paramArrayOfByte);
/*     */ 
/* 148 */     if ((paramConnection == null) || (paramStructDescriptor == null))
/*     */     {
/* 153 */       DatabaseError.throwSqlException(68);
/*     */     }
/*     */ 
/* 157 */     this.descriptor = paramStructDescriptor;
/*     */ 
/* 159 */     setPhysicalConnectionOf(paramConnection);
/*     */   }
/*     */ 
/*     */   public Object getValue(Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 179 */     STRUCT localSTRUCT = getSTRUCT();
/* 180 */     Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc(paramMap) : null;
/*     */ 
/* 184 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object getValue()
/*     */     throws SQLException
/*     */   {
/* 201 */     STRUCT localSTRUCT = getSTRUCT();
/* 202 */     Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc() : null;
/*     */ 
/* 206 */     return localObject;
/*     */   }
/*     */ 
/*     */   public synchronized STRUCT getSTRUCT()
/*     */     throws SQLException
/*     */   {
/* 220 */     STRUCT localSTRUCT = null;
/*     */ 
/* 222 */     OraclePreparedStatement localOraclePreparedStatement = (OraclePreparedStatement)getInternalConnection().prepareStatement("select deref(:1) from dual");
/*     */ 
/* 225 */     localOraclePreparedStatement.setRowPrefetch(1);
/* 226 */     localOraclePreparedStatement.setREF(1, this);
/*     */ 
/* 228 */     OracleResultSet localOracleResultSet = (OracleResultSet)localOraclePreparedStatement.executeQuery();
/*     */     try
/*     */     {
/* 232 */       if (localOracleResultSet.next()) {
/* 233 */         localSTRUCT = localOracleResultSet.getSTRUCT(1);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         DatabaseError.throwSqlException(52);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 244 */       localOracleResultSet.close();
/*     */ 
/* 246 */       localOracleResultSet = null;
/*     */ 
/* 248 */       localOraclePreparedStatement.close();
/*     */ 
/* 250 */       localOraclePreparedStatement = null;
/*     */     }
/*     */ 
/* 255 */     return localSTRUCT;
/*     */   }
/*     */ 
/*     */   public synchronized void setValue(Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 272 */     STRUCT localSTRUCT = STRUCT.toSTRUCT(paramObject, getInternalConnection());
/*     */ 
/* 274 */     if (localSTRUCT.getInternalConnection() != getInternalConnection())
/*     */     {
/* 279 */       DatabaseError.throwSqlException(77, "Incompatible connection object");
/*     */     }
/*     */ 
/* 283 */     if (!getBaseTypeName().equals(localSTRUCT.getSQLTypeName()))
/*     */     {
/* 288 */       DatabaseError.throwSqlException(77, "Incompatible type");
/*     */     }
/*     */ 
/* 293 */     byte[] arrayOfByte1 = localSTRUCT.toBytes();
/*     */ 
/* 296 */     byte[] arrayOfByte2 = localSTRUCT.getDescriptor().getOracleTypeADT().getTOID();
/*     */ 
/* 299 */     CallableStatement localCallableStatement = null;
/*     */     try
/*     */     {
/* 303 */       localCallableStatement = getInternalConnection().prepareCall("begin :1 := dbms_pickler.update_through_ref (:2, :3, :4, :5); end;");
/*     */ 
/* 306 */       localCallableStatement.registerOutParameter(1, 2);
/*     */ 
/* 308 */       localCallableStatement.setBytes(2, shareBytes());
/* 309 */       localCallableStatement.setInt(3, 0);
/* 310 */       localCallableStatement.setBytes(4, arrayOfByte2);
/* 311 */       localCallableStatement.setBytes(5, arrayOfByte1);
/*     */ 
/* 313 */       localCallableStatement.execute();
/*     */ 
/* 315 */       int i = 0;
/*     */ 
/* 317 */       if ((i = localCallableStatement.getInt(1)) != 0)
/*     */       {
/* 322 */         DatabaseError.throwSqlException(77, "ORA-" + i);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 328 */       if (localCallableStatement != null) {
/* 329 */         localCallableStatement.close();
/*     */       }
/* 331 */       localCallableStatement = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public StructDescriptor getDescriptor()
/*     */     throws SQLException
/*     */   {
/* 351 */     if (this.descriptor == null)
/*     */     {
/* 353 */       this.descriptor = StructDescriptor.createDescriptor(this.typename, getInternalConnection());
/*     */     }
/*     */ 
/* 359 */     return this.descriptor;
/*     */   }
/*     */ 
/*     */   public String getSQLTypeName()
/*     */     throws SQLException
/*     */   {
/* 373 */     String str = getBaseTypeName();
/*     */ 
/* 377 */     return str;
/*     */   }
/*     */ 
/*     */   public Object getObject(Map paramMap)
/*     */     throws SQLException
/*     */   {
/* 392 */     STRUCT localSTRUCT = getSTRUCT();
/* 393 */     Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc(paramMap) : null;
/*     */ 
/* 397 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object getObject()
/*     */     throws SQLException
/*     */   {
/* 426 */     STRUCT localSTRUCT = getSTRUCT();
/* 427 */     Object localObject = localSTRUCT != null ? localSTRUCT.toJdbc() : null;
/*     */ 
/* 431 */     return localObject;
/*     */   }
/*     */ 
/*     */   public void setObject(Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 466 */     PreparedStatement localPreparedStatement = getInternalConnection().prepareStatement("call sys.utl_ref.update_object( :1, :2 )");
/*     */ 
/* 469 */     localPreparedStatement.setRef(1, this);
/* 470 */     localPreparedStatement.setObject(2, paramObject);
/* 471 */     localPreparedStatement.execute();
/* 472 */     localPreparedStatement.close();
/*     */   }
/*     */ 
/*     */   public Object toJdbc()
/*     */     throws SQLException
/*     */   {
/* 499 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isConvertibleTo(Class paramClass)
/*     */   {
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */   public Object makeJdbcArray(int paramInt)
/*     */   {
/* 542 */     return new REF[paramInt];
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 550 */     REF localREF = null;
/*     */     try
/*     */     {
/* 554 */       localREF = new REF(getBaseTypeName(), getInternalConnection(), getBytes());
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 561 */       throw new CloneNotSupportedException(localSQLException.getMessage());
/*     */     }
/*     */ 
/* 566 */     return localREF;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 574 */     int i = 0;
/*     */     try
/*     */     {
/* 578 */       i = ((paramObject instanceof REF)) && (super.equals(paramObject)) && (getBaseTypeName().equals(((REF)paramObject).getSQLTypeName())) ? 1 : 0;
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */ 
/* 590 */     return i;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 624 */     byte[] arrayOfByte = shareBytes();
/* 625 */     int i = 0;
/*     */     int j;
/* 627 */     if ((arrayOfByte[2] & 0x5) == 5)
/*     */     {
/* 631 */       for (j = 0; j < 4; j++)
/*     */       {
/* 633 */         i *= 256;
/* 634 */         i += (arrayOfByte[(8 + j)] & 0xFF);
/*     */       }
/*     */     }
/* 637 */     if ((arrayOfByte[2] & 0x3) == 3)
/*     */     {
/* 642 */       for (j = 0; (j < 4) && (j < arrayOfByte.length); j++)
/*     */       {
/* 644 */         i *= 256;
/* 645 */         i += (arrayOfByte[(6 + j)] & 0xFF);
/*     */       }
/*     */     }
/* 648 */     if ((arrayOfByte[2] & 0x2) == 2)
/*     */     {
/* 653 */       for (j = 0; j < 4; j++)
/*     */       {
/* 655 */         i *= 256;
/* 656 */         i += (arrayOfByte[(8 + j)] & 0xFF);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 663 */     return i;
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 671 */     paramObjectOutputStream.writeObject(shareBytes());
/*     */     try
/*     */     {
/* 675 */       paramObjectOutputStream.writeUTF(getBaseTypeName());
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 682 */       throw new IOException("SQLException ORA-" + localSQLException.getErrorCode() + " " + localSQLException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 696 */     setBytes((byte[])paramObjectInputStream.readObject());
/*     */ 
/* 698 */     this.typename = paramObjectInputStream.readUTF();
/*     */   }
/*     */ 
/*     */   public Connection getJavaSqlConnection()
/*     */     throws SQLException
/*     */   {
/* 706 */     return super.getJavaSqlConnection();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.REF
 * JD-Core Version:    0.6.0
 */