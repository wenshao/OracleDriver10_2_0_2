/*     */ package oracle.jdbc.xa;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import oracle.jdbc.driver.OracleLog;
/*     */ 
/*     */ public class OracleMultiPhaseArgs
/*     */ {
/*  31 */   int action = 0;
/*  32 */   int nsites = 0;
/*  33 */   Vector dbLinks = null;
/*  34 */   Vector xids = null;
/*     */ 
/*     */   public OracleMultiPhaseArgs()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OracleMultiPhaseArgs(int paramInt1, int paramInt2, Vector paramVector1, Vector paramVector2)
/*     */   {
/*  73 */     if (paramInt2 <= 1)
/*     */     {
/*  75 */       this.action = 0;
/*  76 */       this.nsites = 0;
/*  77 */       this.dbLinks = null;
/*  78 */       this.xids = null;
/*     */     }
/*  97 */     else if ((!paramVector1.isEmpty()) && (!paramVector2.isEmpty()) && (paramVector2.size() == paramInt2) && (paramVector1.size() == 3 * paramInt2))
/*     */     {
/* 118 */       this.action = paramInt1;
/* 119 */       this.nsites = paramInt2;
/* 120 */       this.xids = paramVector1;
/* 121 */       this.dbLinks = paramVector2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public OracleMultiPhaseArgs(byte[] paramArrayOfByte)
/*     */   {
/* 161 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
/* 162 */     DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
/*     */ 
/* 164 */     this.xids = new Vector();
/* 165 */     this.dbLinks = new Vector();
/*     */     try
/*     */     {
/* 169 */       this.action = localDataInputStream.readInt();
/* 170 */       this.nsites = localDataInputStream.readInt();
/*     */ 
/* 172 */       int i = localDataInputStream.readInt();
/* 173 */       int j = localDataInputStream.readInt();
/* 174 */       byte[] arrayOfByte1 = new byte[j];
/* 175 */       int k = localDataInputStream.read(arrayOfByte1, 0, j);
/*     */ 
/* 177 */       for (int m = 0; m < this.nsites; m++)
/*     */       {
/* 179 */         int n = localDataInputStream.readInt();
/* 180 */         byte[] arrayOfByte2 = new byte[n];
/* 181 */         int i1 = localDataInputStream.read(arrayOfByte2, 0, n);
/*     */ 
/* 184 */         this.xids.addElement(new Integer(i));
/* 185 */         this.xids.addElement(arrayOfByte1);
/* 186 */         this.xids.addElement(arrayOfByte2);
/*     */ 
/* 188 */         String str = localDataInputStream.readUTF();
/*     */ 
/* 191 */         this.dbLinks.addElement(str);
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 196 */       localIOException.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] toByteArray()
/*     */   {
/* 235 */     return toByteArrayOS().toByteArray();
/*     */   }
/*     */ 
/*     */   public ByteArrayOutputStream toByteArrayOS()
/*     */   {
/* 240 */     Object localObject = null;
/* 241 */     int i = 0;
/*     */ 
/* 256 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 257 */     DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
/*     */     try
/*     */     {
/* 261 */       localDataOutputStream.writeInt(this.action);
/* 262 */       localDataOutputStream.writeInt(this.nsites);
/*     */ 
/* 264 */       for (int j = 0; j < this.nsites; j++)
/*     */       {
/* 266 */         String str = (String)this.dbLinks.elementAt(j);
/* 267 */         int k = ((Integer)this.xids.elementAt(j * 3)).intValue();
/* 268 */         byte[] arrayOfByte1 = (byte[])this.xids.elementAt(j * 3 + 1);
/* 269 */         byte[] arrayOfByte2 = (byte[])this.xids.elementAt(j * 3 + 2);
/*     */ 
/* 271 */         if (j == 0)
/*     */         {
/* 273 */           i = k;
/* 274 */           localObject = arrayOfByte1;
/*     */ 
/* 276 */           localDataOutputStream.writeInt(k);
/* 277 */           localDataOutputStream.writeInt(arrayOfByte1.length);
/* 278 */           localDataOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
/*     */         }
/*     */ 
/* 289 */         localDataOutputStream.writeInt(arrayOfByte2.length);
/* 290 */         localDataOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
/* 291 */         localDataOutputStream.writeUTF(str);
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 296 */       localIOException.printStackTrace();
/*     */     }
/*     */ 
/* 318 */     return localByteArrayOutputStream;
/*     */   }
/*     */ 
/*     */   public int getAction()
/*     */   {
/* 337 */     return this.action;
/*     */   }
/*     */ 
/*     */   public int getnsite()
/*     */   {
/* 356 */     return this.nsites;
/*     */   }
/*     */ 
/*     */   public Vector getdbLinks()
/*     */   {
/* 375 */     return this.dbLinks;
/*     */   }
/*     */ 
/*     */   public Vector getXids()
/*     */   {
/* 394 */     return this.xids;
/*     */   }
/*     */ 
/*     */   public void printMPArgs()
/*     */   {
/* 402 */     OracleLog.print(this, 256, 16, 64, "-------printMPArgs entry-------");
/*     */ 
/* 405 */     OracleLog.print(this, 256, 16, 64, "  action = " + this.action);
/*     */ 
/* 407 */     OracleLog.print(this, 256, 16, 64, "  nsites = " + this.nsites);
/*     */ 
/* 410 */     for (int i = 0; i < this.nsites; i++)
/*     */     {
/* 412 */       String str = (String)this.dbLinks.elementAt(i);
/* 413 */       int j = ((Integer)this.xids.elementAt(i * 3)).intValue();
/* 414 */       byte[] arrayOfByte1 = (byte[])this.xids.elementAt(i * 3 + 1);
/* 415 */       byte[] arrayOfByte2 = (byte[])this.xids.elementAt(i * 3 + 2);
/*     */ 
/* 417 */       OracleLog.print(this, 256, 16, 64, "  fmtid  [" + i + "] = " + j);
/*     */ 
/* 419 */       OracleLog.print(this, 256, 16, 64, "  gtrid  [" + i + "] = ");
/*     */ 
/* 421 */       printByteArray(arrayOfByte1);
/* 422 */       OracleLog.print(this, 256, 16, 64, "  bqual  [" + i + "] = ");
/*     */ 
/* 424 */       printByteArray(arrayOfByte2);
/* 425 */       OracleLog.print(this, 256, 16, 64, "  dblink [" + i + "] = " + str);
/*     */     }
/*     */ 
/* 429 */     OracleLog.print(this, 256, 16, 64, "-------printMPArgs return-------");
/*     */   }
/*     */ 
/*     */   private void printByteArray(byte[] paramArrayOfByte)
/*     */   {
/* 435 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 437 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 438 */       localStringBuffer.append(paramArrayOfByte[i] + " ");
/*     */     }
/* 440 */     OracleLog.print(this, 256, 16, 64, "         " + localStringBuffer.toString());
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.xa.OracleMultiPhaseArgs
 * JD-Core Version:    0.6.0
 */