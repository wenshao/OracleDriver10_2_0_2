/*     */ package oracle.jdbc.driver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ class T4CTTIoscid extends T4CTTIfun
/*     */ {
/*     */   static final int KPDUSR_CID_RESET = 1;
/*     */   static final int KPDUSR_PROXY_RESET = 2;
/*     */   static final int KPDUSR_PROXY_TKTSENT = 4;
/*     */   static final int KPDUSR_MODULE_RESET = 8;
/*     */   static final int KPDUSR_ACTION_RESET = 16;
/*     */   static final int KPDUSR_EXECID_RESET = 32;
/*     */   static final int KPDUSR_EXECSQ_RESET = 64;
/*     */   static final int KPDUSR_COLLCT_RESET = 128;
/*     */   static final int KPDUSR_CLINFO_RESET = 256;
/*     */ 
/*     */   T4CTTIoscid(T4CMAREngine paramT4CMAREngine)
/*     */     throws IOException, SQLException
/*     */   {
/* 116 */     super(17, 0);
/*     */ 
/* 118 */     setMarshalingEngine(paramT4CMAREngine);
/* 119 */     setFunCode(135);
/*     */   }
/*     */ 
/*     */   void marshal(boolean[] paramArrayOfBoolean, String[] paramArrayOfString, int paramInt)
/*     */     throws IOException, SQLException
/*     */   {
/* 125 */     marshalFunHeader();
/*     */ 
/* 127 */     int i = 64;
/*     */ 
/* 129 */     if (paramArrayOfBoolean[0] != 0) {
/* 130 */       i |= 16;
/*     */     }
/* 132 */     if (paramArrayOfBoolean[1] != 0) {
/* 133 */       i |= 1;
/*     */     }
/* 135 */     if (paramArrayOfBoolean[2] != 0) {
/* 136 */       i |= 32;
/*     */     }
/* 138 */     if (paramArrayOfBoolean[3] != 0) {
/* 139 */       i |= 8;
/*     */     }
/*     */ 
/* 143 */     this.meg.marshalNULLPTR();
/* 144 */     this.meg.marshalNULLPTR();
/* 145 */     this.meg.marshalUB4(i);
/*     */ 
/* 147 */     byte[] arrayOfByte1 = null;
/*     */ 
/* 149 */     if (paramArrayOfBoolean[1] != 0)
/*     */     {
/* 151 */       this.meg.marshalPTR();
/*     */ 
/* 153 */       if (paramArrayOfString[1] != null)
/*     */       {
/* 155 */         arrayOfByte1 = this.meg.conv.StringToCharBytes(paramArrayOfString[1]);
/*     */ 
/* 158 */         if (arrayOfByte1 != null)
/* 159 */           this.meg.marshalUB4(arrayOfByte1.length);
/*     */         else
/* 161 */           this.meg.marshalUB4(0L);
/*     */       }
/*     */       else {
/* 164 */         this.meg.marshalUB4(0L);
/*     */       }
/*     */     }
/*     */     else {
/* 168 */       this.meg.marshalNULLPTR();
/* 169 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */ 
/* 172 */     byte[] arrayOfByte2 = null;
/*     */ 
/* 175 */     if (paramArrayOfBoolean[3] != 0)
/*     */     {
/* 177 */       this.meg.marshalPTR();
/*     */ 
/* 179 */       if (paramArrayOfString[3] != null)
/*     */       {
/* 181 */         arrayOfByte2 = this.meg.conv.StringToCharBytes(paramArrayOfString[3]);
/*     */ 
/* 184 */         if (arrayOfByte2 != null)
/* 185 */           this.meg.marshalUB4(arrayOfByte2.length);
/*     */         else
/* 187 */           this.meg.marshalUB4(0L);
/*     */       }
/*     */       else {
/* 190 */         this.meg.marshalUB4(0L);
/*     */       }
/*     */     }
/*     */     else {
/* 194 */       this.meg.marshalNULLPTR();
/* 195 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */ 
/* 198 */     byte[] arrayOfByte3 = null;
/*     */ 
/* 200 */     if (paramArrayOfBoolean[0] != 0)
/*     */     {
/* 202 */       this.meg.marshalPTR();
/*     */ 
/* 204 */       if (paramArrayOfString[0] != null)
/*     */       {
/* 206 */         arrayOfByte3 = this.meg.conv.StringToCharBytes(paramArrayOfString[0]);
/*     */ 
/* 209 */         if (arrayOfByte3 != null)
/* 210 */           this.meg.marshalUB4(arrayOfByte3.length);
/*     */         else
/* 212 */           this.meg.marshalUB4(0L);
/*     */       }
/*     */       else {
/* 215 */         this.meg.marshalUB4(0L);
/*     */       }
/*     */     }
/*     */     else {
/* 219 */       this.meg.marshalNULLPTR();
/* 220 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */ 
/* 223 */     byte[] arrayOfByte4 = null;
/*     */ 
/* 225 */     if (paramArrayOfBoolean[2] != 0)
/*     */     {
/* 227 */       this.meg.marshalPTR();
/*     */ 
/* 229 */       if (paramArrayOfString[2] != null)
/*     */       {
/* 231 */         arrayOfByte4 = this.meg.conv.StringToCharBytes(paramArrayOfString[2]);
/*     */ 
/* 234 */         if (arrayOfByte4 != null)
/* 235 */           this.meg.marshalUB4(arrayOfByte4.length);
/*     */         else
/* 237 */           this.meg.marshalUB4(0L);
/*     */       }
/*     */       else {
/* 240 */         this.meg.marshalUB4(0L);
/*     */       }
/*     */     }
/*     */     else {
/* 244 */       this.meg.marshalNULLPTR();
/* 245 */       this.meg.marshalUB4(0L);
/*     */     }
/*     */ 
/* 248 */     this.meg.marshalUB2(0);
/* 249 */     this.meg.marshalUB2(paramInt);
/* 250 */     this.meg.marshalNULLPTR();
/* 251 */     this.meg.marshalUB4(0L);
/* 252 */     this.meg.marshalNULLPTR();
/* 253 */     this.meg.marshalUB4(0L);
/* 254 */     this.meg.marshalNULLPTR();
/* 255 */     this.meg.marshalUB4(0L);
/*     */ 
/* 258 */     if (arrayOfByte1 != null) {
/* 259 */       this.meg.marshalCHR(arrayOfByte1);
/*     */     }
/* 261 */     if (arrayOfByte2 != null) {
/* 262 */       this.meg.marshalCHR(arrayOfByte2);
/*     */     }
/* 264 */     if (arrayOfByte3 != null) {
/* 265 */       this.meg.marshalCHR(arrayOfByte3);
/*     */     }
/* 267 */     if (arrayOfByte4 != null)
/* 268 */       this.meg.marshalCHR(arrayOfByte4);
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.driver.T4CTTIoscid
 * JD-Core Version:    0.6.0
 */