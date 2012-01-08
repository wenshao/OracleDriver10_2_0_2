/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4C8TTIuds extends T4CTTIMsg
/*     */ {
/*     */   T4CTTIoac udsoac;
/*     */   boolean udsnull;
/*     */   short udscnl;
/*     */   byte optimizeOAC;
/*     */   byte[] udscolnm;
/*     */   short udscolnl;
/*     */   byte[] udssnm;
/*     */   long udssnl;
/*     */   int[] snnumchar;
/*     */   byte[] udstnm;
/*     */   long udstnl;
/*     */   int[] tnnumchar;
/*     */   int[] numBytes;
/* 240 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:31_PST_2006";
/*     */ 
/*     */   T4C8TTIuds(T4CMAREngine paramT4CMAREngine)
/*     */     throws SQLException, IOException
/*     */   {
/* 107 */     setMarshalingEngine(paramT4CMAREngine);
/*     */ 
/* 109 */     this.udsoac = new T4CTTIoac(paramT4CMAREngine);
/*     */   }
/*     */ 
/*     */   void unmarshal()
/*     */     throws IOException, SQLException
/*     */   {
/* 127 */     this.udsoac.unmarshal();
/*     */ 
/* 129 */     int i = this.meg.unmarshalUB1();
/* 130 */     this.udsnull = (i > 0);
/* 131 */     this.udscnl = this.meg.unmarshalUB1();
/*     */ 
/* 133 */     this.numBytes = new int[1];
/* 134 */     this.udscolnm = this.meg.unmarshalDALC(this.numBytes);
/*     */ 
/* 137 */     this.snnumchar = new int[1];
/* 138 */     this.udssnm = this.meg.unmarshalDALC(this.snnumchar);
/* 139 */     this.udssnl = this.udssnm.length;
/*     */ 
/* 142 */     this.tnnumchar = new int[1];
/* 143 */     this.udstnm = this.meg.unmarshalDALC(this.tnnumchar);
/* 144 */     this.udstnl = this.udstnm.length;
/*     */   }
/*     */ 
/*     */   byte[] getColumName()
/*     */   {
/* 152 */     return this.udscolnm;
/*     */   }
/*     */ 
/*     */   byte[] getTypeName()
/*     */   {
/* 157 */     return this.udstnm;
/*     */   }
/*     */ 
/*     */   byte[] getSchemaName()
/*     */   {
/* 162 */     return this.udssnm;
/*     */   }
/*     */ 
/*     */   short getTypeCharLength()
/*     */   {
/* 167 */     return (short)this.tnnumchar[0];
/*     */   }
/*     */ 
/*     */   short getColumNameByteLength()
/*     */   {
/* 172 */     return (short)this.numBytes[0];
/*     */   }
/*     */ 
/*     */   short getSchemaCharLength()
/*     */   {
/* 177 */     return (short)this.snnumchar[0];
/*     */   }
/*     */ 
/*     */   void print(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4C8TTIuds
 * JD-Core Version:    0.6.0
 */