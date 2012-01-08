/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public abstract class OracleInputStream extends OracleBufferedStream
/*     */ {
/*     */   int columnIndex;
/*     */   Accessor accessor;
/*     */   OracleInputStream nextStream;
/*  38 */   boolean hasBeenOpen = false;
/*     */ 
/* 264 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:29_PST_2006";
/*     */ 
/*     */   protected OracleInputStream(OracleStatement paramOracleStatement, int paramInt, Accessor paramAccessor)
/*     */   {
/*  42 */     super(paramOracleStatement, paramOracleStatement.connection.getDefaultStreamChunkSize());
/*     */ 
/*  47 */     this.closed = true;
/*  48 */     this.statement = paramOracleStatement;
/*  49 */     this.columnIndex = paramInt;
/*  50 */     this.accessor = paramAccessor;
/*  51 */     this.nextStream = null;
/*     */ 
/*  61 */     OracleInputStream localOracleInputStream = this.statement.streamList;
/*     */ 
/*  63 */     if ((localOracleInputStream == null) || (this.columnIndex < localOracleInputStream.columnIndex))
/*     */     {
/*  67 */       this.nextStream = this.statement.streamList;
/*  68 */       this.statement.streamList = this;
/*     */     }
/*  73 */     else if (this.columnIndex == localOracleInputStream.columnIndex)
/*     */     {
/*  78 */       this.nextStream = localOracleInputStream.nextStream;
/*     */ 
/*  82 */       localOracleInputStream.nextStream = null;
/*  83 */       this.statement.streamList = this;
/*     */     }
/*     */     else
/*     */     {
/*  91 */       while ((localOracleInputStream.nextStream != null) && (this.columnIndex > localOracleInputStream.nextStream.columnIndex))
/*     */       {
/*  93 */         localOracleInputStream = localOracleInputStream.nextStream;
/*     */       }
/*     */ 
/*  99 */       if ((localOracleInputStream.nextStream != null) && (this.columnIndex == localOracleInputStream.nextStream.columnIndex))
/*     */       {
/* 103 */         this.nextStream = localOracleInputStream.nextStream.nextStream;
/*     */ 
/* 107 */         localOracleInputStream.nextStream.nextStream = null;
/* 108 */         localOracleInputStream.nextStream = this;
/*     */       }
/*     */       else
/*     */       {
/* 114 */         this.nextStream = localOracleInputStream.nextStream;
/*     */ 
/* 118 */         localOracleInputStream.nextStream = this;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 128 */     return "OIS@" + Integer.toHexString(hashCode()) + "{" + "statement = " + this.statement + ", accessor = " + this.accessor + ", nextStream = " + this.nextStream + ", columnIndex = " + this.columnIndex + ", hasBeenOpen = " + this.hasBeenOpen + "}";
/*     */   }
/*     */ 
/*     */   public boolean needBytes()
/*     */     throws IOException
/*     */   {
/* 140 */     if (this.closed) {
/* 141 */       return false;
/*     */     }
/* 143 */     if (this.pos >= this.count)
/*     */     {
/*     */       try
/*     */       {
/* 147 */         int i = getBytes();
/*     */ 
/* 152 */         this.pos = 0;
/* 153 */         this.count = i;
/*     */ 
/* 155 */         if (this.count == -1)
/*     */         {
/* 159 */           if (this.nextStream == null) {
/* 160 */             this.statement.connection.releaseLine();
/*     */           }
/* 162 */           this.closed = true;
/*     */ 
/* 164 */           this.accessor.fetchNextColumns();
/*     */ 
/* 166 */           return false;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (SQLException localSQLException)
/*     */       {
/* 191 */         DatabaseError.SQLToIOException(localSQLException);
/*     */       }
/*     */     }
/*     */ 
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNull() throws IOException
/*     */   {
/* 200 */     boolean bool = false;
/*     */     try
/*     */     {
/* 204 */       bool = this.accessor.isNull(0);
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 208 */       DatabaseError.SQLToIOException(localSQLException);
/*     */     }
/*     */ 
/* 211 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean isClosed()
/*     */   {
/* 216 */     return this.closed;
/*     */   }
/*     */ 
/*     */   public void close() throws IOException
/*     */   {
/* 221 */     synchronized (this.statement.connection)
/*     */     {
/* 223 */       synchronized (this)
/*     */       {
/* 225 */         if ((!this.closed) && (this.hasBeenOpen))
/*     */         {
/* 229 */           while (this.statement.nextStream != this)
/*     */           {
/* 231 */             this.statement.nextStream.close();
/*     */ 
/* 233 */             this.statement.nextStream = this.statement.nextStream.nextStream;
/*     */           }
/*     */ 
/* 236 */           if (!isNull())
/*     */           {
/* 242 */             while (needBytes())
/*     */             {
/* 246 */               this.pos = this.count;
/*     */             }
/*     */           }
/*     */ 
/* 250 */           this.closed = true;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract int getBytes()
/*     */     throws IOException;
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.OracleInputStream
 * JD-Core Version:    0.6.0
 */