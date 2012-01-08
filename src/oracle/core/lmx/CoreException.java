/*     */ package oracle.core.lmx;
/*     */ 
/*     */ public class CoreException extends Exception
/*     */ {
/*     */   public static final byte UNIMPLEMENTED = 1;
/*     */   public static final byte UNDERFLOW = 2;
/*     */   public static final byte OVERFLOW = 3;
/*     */   public static final byte INVALIDORLN = 4;
/*     */   public static final byte BADFORMATORLN = 5;
/*     */   public static final byte INVALIDORLD = 6;
/*     */   public static final byte BADFORMATORLD = 7;
/*     */   public static final byte BADYEAR = 8;
/*     */   public static final byte BADDAYYEAR = 9;
/*     */   public static final byte BADJULIANDATE = 10;
/*     */   public static final byte INVALIDINPUTN = 11;
/*     */   public static final byte NLSNOTSUPPORTED = 12;
/*     */   public static final byte INVALIDINPUT = 13;
/*     */   public static final byte CONVERSIONERROR = 14;
/* 162 */   private static final String[] _errmsgs = { "Unknown Exception", "Unimplemented method called", "Underflow Exception", "Overflow Exception", "Invalid Oracle Number", "Bad Oracle Number format", "Invalid Oracle Date", "Bad Oracle Date format", "Year Not in Range", "Day of Year Not in Range", "Julian Date Not in Range", "Invalid Input Number", "NLS Not Supported", "Invalid Input", "Conversion Error" };
/*     */   private byte ecode;
/*     */ 
/*     */   public CoreException()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CoreException(String paramString)
/*     */   {
/*  27 */     super(paramString);
/*     */   }
/*     */ 
/*     */   public CoreException(byte paramByte)
/*     */   {
/*  36 */     this.ecode = paramByte;
/*     */   }
/*     */ 
/*     */   public void setErrorCode(byte paramByte)
/*     */   {
/*  46 */     this.ecode = paramByte;
/*     */   }
/*     */ 
/*     */   public byte getErrorCode()
/*     */   {
/*  56 */     return this.ecode;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  66 */     if (this.ecode == 0) {
/*  67 */       return super.getMessage();
/*     */     }
/*  69 */     return getMessage(this.ecode);
/*     */   }
/*     */ 
/*     */   public static String getMessage(int paramByte)
/*     */   {
/*  83 */     if ((paramByte < 1) || (paramByte > 14)) {
/*  84 */       return "Unknown exception";
/*     */     }
/*  86 */     return _errmsgs[paramByte];
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.core.lmx.CoreException
 * JD-Core Version:    0.6.0
 */