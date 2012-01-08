/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class TIMEZONETAB
/*     */ {
/* 489 */   private static Hashtable zonetab = new Hashtable();
/*     */ 
/* 492 */   private static int OFFSET_HOUR = 20;
/* 493 */   private static int OFFSET_MINUTE = 60;
/*     */ 
/* 496 */   private static int HOUR_MILLISECOND = 3600000;
/*     */ 
/* 499 */   private static int MINUTE_MILLISECOND = 60000;
/*     */ 
/* 502 */   private static int BYTE_SIZE = 10;
/*     */ 
/*     */   public static void addTrans(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/*  55 */     int[] arrayOfInt = new int[BYTE_SIZE];
/*     */ 
/*  58 */     int i = paramArrayOfByte[0] & 0xFF;
/*     */ 
/*  61 */     Vector localVector = new Vector(i);
/*     */ 
/*  64 */     for (int j = 1; j < i * BYTE_SIZE; j += BYTE_SIZE)
/*     */     {
/*  66 */       for (int k = 0; k < BYTE_SIZE; k++) {
/*  67 */         arrayOfInt[k] = (paramArrayOfByte[(k + j)] & 0xFF);
/*     */       }
/*     */ 
/*  72 */       k = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);
/*     */ 
/*  76 */       Calendar localCalendar = Calendar.getInstance();
/*  77 */       localCalendar.set(1, k);
/*  78 */       localCalendar.set(2, arrayOfInt[2] - 1);
/*  79 */       localCalendar.set(5, arrayOfInt[3]);
/*  80 */       localCalendar.set(11, arrayOfInt[4] - 1);
/*  81 */       localCalendar.set(12, arrayOfInt[5] - 1);
/*  82 */       localCalendar.set(13, arrayOfInt[6] - 1);
/*  83 */       localCalendar.set(14, 0);
/*     */ 
/*  88 */       long l = localCalendar.getTime().getTime();
/*     */ 
/*  92 */       int m = (arrayOfInt[7] - OFFSET_HOUR) * HOUR_MILLISECOND + (arrayOfInt[8] - OFFSET_MINUTE) * MINUTE_MILLISECOND;
/*     */ 
/*  96 */       byte b = (byte)arrayOfInt[9];
/*     */ 
/* 101 */       localVector.addElement(new OffsetDST(new Timestamp(l), m, b));
/*     */     }
/*     */ 
/* 107 */     zonetab.put(new Integer(paramInt & 0x1FF), localVector);
/*     */   }
/*     */ 
/*     */   public static byte getLocalOffset(Calendar paramCalendar, int paramInt, OffsetDST paramOffsetDST)
/*     */     throws NullPointerException, SQLException
/*     */   {
/* 128 */     Vector localVector = new Vector();
/*     */ 
/* 133 */     int k = 0;
/*     */ 
/* 135 */     int i1 = 0;
/*     */ 
/* 138 */     Calendar localCalendar1 = Calendar.getInstance();
/* 139 */     Calendar localCalendar2 = Calendar.getInstance();
/*     */ 
/* 143 */     Calendar localCalendar3 = Calendar.getInstance();
/* 144 */     localCalendar3.setTime(new Timestamp(paramCalendar.getTime().getTime()));
/*     */ 
/* 149 */     int i2 = 0; int i3 = 0;
/*     */ 
/* 153 */     Calendar localCalendar4 = Calendar.getInstance();
/* 154 */     localCalendar4.set(1, localCalendar3.get(1));
/* 155 */     localCalendar4.set(2, localCalendar3.get(2));
/* 156 */     localCalendar4.set(5, 1);
/* 157 */     localCalendar4.set(11, 0);
/* 158 */     localCalendar4.set(12, 0);
/* 159 */     localCalendar4.set(13, 0);
/* 160 */     localCalendar4.set(14, 0);
/*     */ 
/* 163 */     Timestamp localTimestamp = new Timestamp(localCalendar4.getTime().getTime());
/*     */ 
/* 165 */     int i4 = 0;
/*     */ 
/* 168 */     localVector = (Vector)zonetab.get(new Integer(paramInt & 0x1FF));
/*     */ 
/* 170 */     Enumeration localEnumeration = localVector.elements();
/*     */ 
/* 172 */     int i5 = 0;
/*     */ 
/* 176 */     while (localEnumeration.hasMoreElements())
/*     */     {
/* 180 */       if (localTimestamp.before(((OffsetDST)localEnumeration.nextElement()).getTimestamp()))
/*     */       {
/* 182 */         if (i5 == 0)
/*     */         {
/* 186 */           throw new SQLException();
/*     */         }
/*     */ 
/* 190 */         i2 = i5 - 1;
/* 191 */         i3 = i5;
/* 192 */         k = i5;
/*     */ 
/* 194 */         break;
/*     */       }
/* 196 */       i5++;
/*     */     }
/*     */ 
/* 204 */     if (i5 == localVector.size())
/*     */     {
/* 206 */       k = i5 - 1;
/*     */     }
/*     */     else
/*     */     {
/* 212 */       int i = ((OffsetDST)localVector.elementAt(i2)).getOFFSET();
/* 213 */       int j = ((OffsetDST)localVector.elementAt(i3)).getOFFSET();
/*     */ 
/* 216 */       int m = ((OffsetDST)localVector.elementAt(i2)).getDSTFLAG();
/* 217 */       int n = ((OffsetDST)localVector.elementAt(i3)).getDSTFLAG();
/*     */ 
/* 221 */       localCalendar1.setTime(((OffsetDST)localVector.elementAt(i2)).getTimestamp());
/* 222 */       localCalendar2.setTime(((OffsetDST)localVector.elementAt(i3)).getTimestamp());
/*     */ 
/* 225 */       localCalendar1.add(10, i / HOUR_MILLISECOND);
/* 226 */       localCalendar1.add(12, i % HOUR_MILLISECOND / MINUTE_MILLISECOND);
/*     */ 
/* 228 */       localCalendar2.add(10, j / HOUR_MILLISECOND);
/* 229 */       localCalendar2.add(12, j % HOUR_MILLISECOND / MINUTE_MILLISECOND);
/*     */ 
/* 232 */       boolean bool = localCalendar3.before(localCalendar1);
/*     */ 
/* 234 */       if (bool) {
/* 235 */         k -= 2;
/*     */       }
/* 238 */       else if ((localCalendar3.before(localCalendar2)) && (!localCalendar3.equals(localCalendar2)))
/*     */       {
/* 241 */         k--;
/*     */       }
/* 247 */       else if (m < n)
/*     */       {
/* 250 */         localCalendar3.add(10, -1);
/*     */ 
/* 252 */         if (localCalendar3.before(localCalendar1)) {
/* 253 */           k--;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 261 */       if (m < n)
/*     */       {
/* 263 */         localCalendar2.add(10, -1);
/*     */ 
/* 265 */         if (localCalendar2.after(paramCalendar)) {
/* 266 */           i1 = 0;
/*     */         }
/*     */         else {
/* 269 */           localCalendar2.add(10, 1);
/*     */ 
/* 271 */           if (localCalendar2.after(paramCalendar))
/* 272 */             i1 = 1;
/*     */           else
/* 274 */             i1 = 0;
/*     */         }
/*     */       }
/* 277 */       else if (n < m)
/*     */       {
/* 279 */         localCalendar2.add(10, 1);
/*     */ 
/* 281 */         if (localCalendar2.before(paramCalendar)) {
/* 282 */           i1 = 0;
/*     */         }
/*     */         else {
/* 285 */           localCalendar2.add(10, -1);
/*     */ 
/* 287 */           if (localCalendar2.after(paramCalendar))
/* 288 */             i1 = 0;
/*     */           else {
/* 290 */             i1 = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 295 */     paramOffsetDST.setOFFSET(((OffsetDST)localVector.elementAt(k)).getOFFSET());
/* 296 */     paramOffsetDST.setDSTFLAG(((OffsetDST)localVector.elementAt(k)).getDSTFLAG());
/*     */ 
/* 298 */     return i1;
/*     */   }
/*     */ 
/*     */   public static int getOffset(Calendar paramCalendar, int paramInt)
/*     */     throws NullPointerException, SQLException
/*     */   {
/* 319 */     Vector localVector = new Vector();
/*     */ 
/* 323 */     int i = 0;
/* 324 */     int j = 0;
/*     */ 
/* 328 */     Timestamp localTimestamp = new Timestamp(paramCalendar.getTime().getTime());
/*     */ 
/* 332 */     localVector = (Vector)zonetab.get(new Integer(paramInt & 0x1FF));
/*     */ 
/* 334 */     Enumeration localEnumeration = localVector.elements();
/*     */ 
/* 336 */     int k = 0;
/*     */ 
/* 338 */     while (localEnumeration.hasMoreElements())
/*     */     {
/* 341 */       if (localTimestamp.before(((OffsetDST)localEnumeration.nextElement()).getTimestamp()))
/*     */       {
/* 343 */         if (k != 0) {
/*     */           break;
/*     */         }
/* 346 */         throw new SQLException();
/*     */       }
/*     */ 
/* 351 */       k++;
/*     */     }
/* 353 */     i = k - 1;
/*     */ 
/* 357 */     return ((OffsetDST)localVector.elementAt(i)).getOFFSET();
/*     */   }
/*     */ 
/*     */   public static void displayTable(int paramInt)
/*     */   {
/* 377 */     Vector localVector = new Vector();
/*     */ 
/* 379 */     Timestamp localTimestamp = new Timestamp(0L);
/*     */ 
/* 381 */     int i = 0;
/*     */ 
/* 387 */     int j = 0;
/*     */ 
/* 390 */     localVector = (Vector)zonetab.get(new Integer(paramInt));
/*     */ 
/* 392 */     Enumeration localEnumeration = localVector.elements();
/*     */ 
/* 395 */     for (int k = 0; k < localVector.size(); k++)
/*     */     {
/* 397 */       System.out.print(((OffsetDST)localVector.elementAt(k)).getTimestamp().toString());
/* 398 */       System.out.print("    " + ((OffsetDST)localVector.elementAt(k)).getOFFSET());
/* 399 */       System.out.println("    " + ((OffsetDST)localVector.elementAt(k)).getDSTFLAG());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean checkID(int paramInt)
/*     */   {
/* 417 */     int i = paramInt & 0x1FF;
/*     */ 
/* 420 */     int[] arrayOfInt = new int[zonetab.size()];
/* 421 */     arrayOfInt = getIDs();
/*     */ 
/* 423 */     int j = 0;
/*     */ 
/* 426 */     while (j < zonetab.size())
/*     */     {
/* 428 */       if (i == arrayOfInt[j])
/*     */         break;
/* 430 */       j++;
/*     */     }
/*     */ 
/* 434 */     return j == zonetab.size();
/*     */   }
/*     */ 
/*     */   public static void updateTable(Connection paramConnection, int paramInt)
/*     */     throws SQLException, NullPointerException
/*     */   {
/* 456 */     byte[] arrayOfByte = TRANSDUMP.getTransitions(paramConnection, paramInt);
/*     */ 
/* 458 */     if (arrayOfByte == null) {
/* 459 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 463 */     addTrans(arrayOfByte, paramInt);
/*     */   }
/*     */ 
/*     */   private static int[] getIDs()
/*     */   {
/* 474 */     int[] arrayOfInt = new int[zonetab.size()];
/* 475 */     int i = 0;
/*     */ 
/* 477 */     Enumeration localEnumeration = zonetab.keys();
/*     */ 
/* 479 */     while (localEnumeration.hasMoreElements()) {
/* 480 */       arrayOfInt[(i++)] = ((Integer)localEnumeration.nextElement()).intValue();
/*     */     }
/* 482 */     return arrayOfInt;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.TIMEZONETAB
 * JD-Core Version:    0.6.0
 */