/*     */ package oracle.sql;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import oracle.core.lmx.CoreException;
/*     */ 
/*     */ class LnxLibThinFormat
/*     */ {
/* 599 */   boolean LNXNFFMI = false;
/* 600 */   boolean LNXNFFDS = false;
/* 601 */   boolean LNXNFFPR = false;
/* 602 */   boolean LNXNFFBL = false;
/* 603 */   boolean LNXNFFDA = false;
/* 604 */   boolean LNXNFFED = false;
/* 605 */   boolean LNXNFFSN = false;
/* 606 */   boolean LNXNFFVF = false;
/*     */ 
/* 608 */   boolean LNXNFFSH = false;
/* 609 */   boolean LNXNFFST = false;
/* 610 */   boolean LNXNFFCH = false;
/* 611 */   boolean LNXNFFCT = false;
/* 612 */   boolean LNXNFFRC = false;
/* 613 */   boolean LNXNFFRN = false;
/* 614 */   boolean LNXNFFLC = false;
/* 615 */   boolean LNXNFFIC = false;
/*     */ 
/* 617 */   boolean LNXNFNRD = false;
/* 618 */   boolean LNXNFRDX = false;
/* 619 */   boolean LNXNFFIL = false;
/*     */ 
/* 621 */   boolean LNXNFFPT = false;
/* 622 */   boolean LNXNFF05 = false;
/*     */ 
/* 624 */   boolean LNXNFFHX = false;
/* 625 */   boolean LNXNFFTM = false;
/* 626 */   boolean LNXNFFUN = false;
/*     */ 
/* 628 */   byte[] lnxnfgps = new byte[40];
/* 629 */   int lnxnflhd = 0;
/* 630 */   int lnxnfrhd = 0;
/* 631 */   int lnxnfsiz = 0;
/* 632 */   int lnxnfzld = 0;
/* 633 */   int lnxnfztr = 0;
/*     */ 
/* 638 */   private final int LNXPFL_US = 1;
/* 639 */   private final int LNXPFL_NLS = -1;
/* 640 */   private final int LXM_LILCURR = 11;
/*     */ 
/* 642 */   private final int LXM_LIUCURR = 11;
/*     */ 
/* 644 */   private final int LXM_LIICURR = 8;
/*     */ 
/* 647 */   private final int LXM_ROMOUT = 15;
/*     */ 
/*     */   public void parseFormat(String paramString)
/*     */     throws SQLException
/*     */   {
/*  53 */     int i = 0;
/*  54 */     int j = 0;
/*  55 */     int k = 0;
/*  56 */     int m = 0;
/*  57 */     int n = 0;
/*     */ 
/*  59 */     int i2 = 0;
/*  60 */     int i3 = 0;
/*     */ 
/*  62 */     int i4 = 0;
/*  63 */     int i5 = 0;
/*  64 */     int i6 = 0;
/*  65 */     int i7 = 0;
/*  66 */     int i8 = 0;
/*  67 */     int i9 = 39;
/*  68 */     int i10 = 0;
/*     */ 
/*  70 */     i3 = paramString.length();
/*     */ 
/*  72 */     char[] arrayOfChar = paramString.toCharArray();
/*     */ 
/*  74 */     this.LNXNFFIL = true;
/*     */ 
/*  76 */     while (i3 != 0)
/*     */     {
/*  78 */       int i1 = Character.toLowerCase(arrayOfChar[i4]);
/*     */ 
/*  81 */       switch (i1)
/*     */       {
/*     */       case 48:
/*     */       case 53:
/*     */       case 57:
/*     */       case 120:
/*  89 */         if (this.LNXNFFSN)
/*     */         {
/*  91 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/*  96 */         if ((i2 == 120) && (i1 != 120))
/*     */         {
/*  98 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 102 */         if (i1 == 53)
/*     */         {
/* 104 */           if (i3 == 2)
/*     */           {
/* 106 */             i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
/*     */           }
/* 108 */           else if (i3 == 3)
/*     */           {
/* 110 */             i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
/* 111 */             i7 = Character.toLowerCase(arrayOfChar[(i4 + 2)]);
/*     */           }
/*     */ 
/* 114 */           if ((!this.LNXNFF05) && ((i3 == 1) || ((i3 == 2) && (i6 == 115)) || (i6 == 99) || (i6 == 108) || (i6 == 117) || ((i3 == 3) && (((i6 == 112) && (i7 == 114)) || ((i6 == 112) && (i7 == 116)) || ((i6 == 109) && (i7 == 105))))))
/*     */           {
/* 124 */             this.LNXNFF05 = true;
/*     */           }
/*     */           else
/*     */           {
/* 128 */             throw new SQLException(CoreException.getMessage(5));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 134 */         if (i1 == 120)
/*     */         {
/* 137 */           if ((i2 == 0) || (i2 == 109) || (i2 == 48) || (i2 == 120))
/*     */           {
/* 141 */             this.LNXNFFHX = true;
/*     */ 
/* 143 */             if (arrayOfChar[i4] == 'x')
/*     */             {
/* 145 */               this.LNXNFFLC = true;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 150 */             throw new SQLException(CoreException.getMessage(5));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 155 */         i++;
/* 156 */         if (i1 != 48) {
/*     */           break label2099;
/*     */         }
/* 160 */         if ((k == 0) && (j != 0)) break label2099;
/* 162 */         j = i; break;
/*     */       case 103:
/* 169 */         if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0) || (i8 == i9) || (i10 > 0) || (i == 0))
/*     */         {
/* 176 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 179 */         i10 = -1;
/*     */ 
/* 181 */         this.lnxnfgps[i8] = (byte)(0x80 | i);
/* 182 */         i8++;
/* 183 */         break;
/*     */       case 44:
/* 187 */         if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0) || (i8 == i9) || (i10 < 0) || (i == 0))
/*     */         {
/* 194 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 199 */         m = 1;
/* 200 */         this.lnxnfgps[i8] = (byte)i;
/* 201 */         i8++;
/* 202 */         i10 = 1;
/* 203 */         break;
/*     */       case 99:
/*     */       case 108:
/*     */       case 117:
/* 208 */         if ((this.LNXNFFCH) || (this.LNXNFFCT) || (this.LNXNFFRC) || (this.LNXNFFSN) || (this.LNXNFFDS) || (this.LNXNFFHX))
/*     */         {
/* 210 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 213 */         if (i1 == 99)
/*     */         {
/* 215 */           n += 7;
/* 216 */           this.LNXNFFIC = true;
/*     */         }
/* 218 */         else if (i1 == 108)
/*     */         {
/* 220 */           n += 10;
/*     */         }
/*     */         else
/*     */         {
/* 224 */           n += 10;
/* 225 */           this.LNXNFFUN = true;
/*     */         }
/* 227 */         if (i4 == i5)
/*     */         {
/* 229 */           this.LNXNFFCH = true;
/* 230 */           break label2099;
/*     */         }
/*     */ 
/* 236 */         if (i3 == 2)
/*     */         {
/* 238 */           i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
/*     */         }
/* 240 */         else if (i3 == 3)
/*     */         {
/* 242 */           i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
/* 243 */           i7 = Character.toLowerCase(arrayOfChar[(i4 + 2)]);
/*     */         }
/*     */ 
/* 247 */         if ((i3 == 1) || ((i3 == 2) && (i6 == 115)) || ((i3 == 3) && (((i6 == 112) && (i7 == 114)) || ((i6 == 112) && (i7 == 116)) || ((i6 == 109) && (i7 == 105)))))
/*     */         {
/* 253 */           this.LNXNFFCT = true;
/* 254 */           break label2099;
/*     */         }
/*     */ 
/* 257 */         if (this.LNXNFF05)
/*     */         {
/* 260 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 265 */         this.LNXNFFRC = true;
/*     */       case 100:
/* 269 */         if ((i10 > 0) || (this.LNXNFFHX))
/*     */         {
/* 271 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 274 */         i10 = -1;
/*     */       case 118:
/* 278 */         if (i1 != 118)
/*     */           break;
/* 280 */         this.LNXNFNRD = true;
/*     */       case 46:
/* 286 */         if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0))
/*     */         {
/* 288 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 291 */         k = 1;
/* 292 */         this.lnxnflhd = i;
/* 293 */         if (j != 0)
/*     */         {
/* 295 */           this.lnxnfzld = (i - j + 1);
/* 296 */           j = 0;
/*     */         }
/*     */         else
/*     */         {
/* 300 */           this.lnxnfzld = 0;
/*     */         }
/*     */ 
/* 303 */         i = 0;
/* 304 */         if ((i1 != 46) && (i1 != 100)) break label2099;
/* 306 */         n++;
/*     */ 
/* 308 */         if (i1 != 46)
/*     */           break label2099;
/* 311 */         if (i10 < 0)
/*     */         {
/* 313 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 316 */         i10 = 1;
/* 317 */         this.LNXNFRDX = true; break;
/*     */       case 98:
/* 324 */         if ((this.LNXNFFSN) || (this.LNXNFFBL) || (this.LNXNFFHX))
/*     */         {
/* 326 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 329 */         this.LNXNFFBL = true;
/* 330 */         break;
/*     */       case 101:
/* 335 */         if ((this.LNXNFFSN) || (this.LNXNFF05) || (this.LNXNFFHX) || (m != 0))
/*     */         {
/* 337 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 342 */         this.LNXNFFSN = true;
/*     */ 
/* 345 */         if ((i3 < 4) || (arrayOfChar[i4] != arrayOfChar[(i4 + 1)]) || (arrayOfChar[i4] != arrayOfChar[(i4 + 2)]) || (arrayOfChar[i4] != arrayOfChar[(i4 + 3)]))
/*     */         {
/* 350 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 353 */         i4 += 3;
/* 354 */         i3 -= 3;
/* 355 */         n += 5;
/* 356 */         break;
/*     */       case 36:
/* 359 */         if ((this.LNXNFFSN) || (this.LNXNFFDS) || (this.LNXNFFCH) || (this.LNXNFFCT) || (this.LNXNFFRC) || (this.LNXNFFHX))
/*     */         {
/* 361 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 365 */         this.LNXNFFDS = true;
/* 366 */         n++;
/* 367 */         break;
/*     */       case 114:
/* 371 */         if ((i4 != i5) || (i3 != 2))
/*     */         {
/* 373 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 376 */         this.LNXNFFRN = true;
/*     */ 
/* 378 */         if (arrayOfChar[i4] == 'r')
/*     */         {
/* 380 */           this.LNXNFFLC = true;
/*     */         }
/* 382 */         this.lnxnfsiz = 15;
/*     */ 
/* 384 */         this.LNXNFFVF = true;
/* 385 */         return;
/*     */       case 102:
/* 387 */         if ((i4 != i5) || (!this.LNXNFFIL))
/*     */         {
/* 389 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 392 */         this.LNXNFFIL = false;
/* 393 */         i4++;
/* 394 */         if (Character.toLowerCase(arrayOfChar[i4]) == 'm')
/*     */         {
/* 396 */           i3--;
/*     */ 
/* 398 */           i5 = i4 + 1;
/* 399 */           i1 = 109;
/* 400 */           break label2099;
/*     */         }
/*     */ 
/* 403 */         throw new SQLException(CoreException.getMessage(5));
/*     */       case 112:
/* 408 */         if ((this.LNXNFFSH) || (this.LNXNFFST) || (this.LNXNFFHX))
/*     */         {
/* 410 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 413 */         n++;
/*     */ 
/* 415 */         i3--;
/* 416 */         if (i3 > 1)
/*     */         {
/* 418 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 422 */         i4++;
/* 423 */         if (Character.toLowerCase(arrayOfChar[i4]) == 'r')
/*     */         {
/* 425 */           this.LNXNFFPR = true;
/* 426 */           break label2099;
/*     */         }
/*     */ 
/* 429 */         if (Character.toLowerCase(arrayOfChar[i4]) == 't')
/*     */         {
/* 431 */           this.LNXNFFPT = true;
/* 432 */           break label2099;
/*     */         }
/*     */ 
/* 435 */         throw new SQLException(CoreException.getMessage(5));
/*     */       case 109:
/* 439 */         if ((this.LNXNFFSH) || (this.LNXNFFST) || (this.LNXNFFHX))
/*     */         {
/* 441 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 445 */         this.LNXNFFMI = true;
/*     */ 
/* 448 */         i4++;
/* 449 */         if (Character.toLowerCase(arrayOfChar[i4]) == 'i')
/*     */         {
/* 452 */           i3--;
/* 453 */           if (i3 <= 1) break label2099;
/* 455 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 461 */         throw new SQLException(CoreException.getMessage(5));
/*     */       case 115:
/* 467 */         if ((this.LNXNFFSH) || (this.LNXNFFHX))
/*     */         {
/* 469 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 472 */         if (i4 == i5)
/*     */         {
/* 474 */           this.LNXNFFSH = true;
/*     */ 
/* 476 */           i5++;
/* 477 */           break label2099;
/*     */         }
/* 479 */         if (i3 == 1)
/*     */         {
/* 481 */           this.LNXNFFST = true;
/* 482 */           break label2099;
/*     */         }
/*     */ 
/* 485 */         throw new SQLException(CoreException.getMessage(5));
/*     */       case 116:
/* 490 */         if ((i4 != i5) || (i3 < 2) || (i3 > 3))
/*     */         {
/* 492 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 496 */         if (Character.toLowerCase(arrayOfChar[(i4 + 1)]) != 'm')
/*     */         {
/* 498 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 501 */         this.LNXNFFTM = true;
/* 502 */         this.LNXNFFIL = false;
/* 503 */         switch (i3 == 3 ? Character.toLowerCase(arrayOfChar[(i4 + 2)]) : 57)
/*     */         {
/*     */         case '9':
/* 507 */           break;
/*     */         case 'e':
/* 509 */           this.LNXNFFSN = true;
/* 510 */           break;
/*     */         default:
/* 512 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/* 515 */         this.lnxnflhd = 0;
/* 516 */         this.lnxnfrhd = 0;
/* 517 */         this.lnxnfsiz = 64;
/* 518 */         this.lnxnfzld = 0;
/* 519 */         this.lnxnfztr = 0;
/*     */ 
/* 521 */         this.LNXNFFVF = true;
/* 522 */         return;
/*     */       case 37:
/*     */       case 38:
/*     */       case 39:
/*     */       case 40:
/*     */       case 41:
/*     */       case 42:
/*     */       case 43:
/*     */       case 45:
/*     */       case 47:
/*     */       case 49:
/*     */       case 50:
/*     */       case 51:
/*     */       case 52:
/*     */       case 54:
/*     */       case 55:
/*     */       case 56:
/*     */       case 58:
/*     */       case 59:
/*     */       case 60:
/*     */       case 61:
/*     */       case 62:
/*     */       case 63:
/*     */       case 64:
/*     */       case 65:
/*     */       case 66:
/*     */       case 67:
/*     */       case 68:
/*     */       case 69:
/*     */       case 70:
/*     */       case 71:
/*     */       case 72:
/*     */       case 73:
/*     */       case 74:
/*     */       case 75:
/*     */       case 76:
/*     */       case 77:
/*     */       case 78:
/*     */       case 79:
/*     */       case 80:
/*     */       case 81:
/*     */       case 82:
/*     */       case 83:
/*     */       case 84:
/*     */       case 85:
/*     */       case 86:
/*     */       case 87:
/*     */       case 88:
/*     */       case 89:
/*     */       case 90:
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/*     */       case 94:
/*     */       case 95:
/*     */       case 96:
/*     */       case 97:
/*     */       case 104:
/*     */       case 105:
/*     */       case 106:
/*     */       case 107:
/*     */       case 110:
/*     */       case 111:
/*     */       case 113:
/* 525 */       case 119: } throw new SQLException(CoreException.getMessage(5));
/*     */ 
/* 530 */       label2099: i2 = i1;
/*     */ 
/* 533 */       i4++;
/* 534 */       i3--;
/*     */     }
/*     */ 
/* 538 */     if (k != 0)
/*     */     {
/* 540 */       this.lnxnfrhd = i;
/* 541 */       this.lnxnfztr = ((this.LNXNFFIL) || (this.LNXNFNRD) ? i : j);
/*     */     }
/*     */     else
/*     */     {
/* 545 */       this.lnxnflhd = i;
/* 546 */       this.lnxnfzld = (j != 0 ? i - j + 1 : 0);
/* 547 */       this.lnxnfrhd = 0;
/* 548 */       this.lnxnfztr = 0;
/* 549 */       this.LNXNFNRD = true;
/*     */     }
/*     */ 
/* 554 */     if (this.LNXNFFSN)
/*     */     {
/* 560 */       if (this.lnxnflhd <= 1)
/*     */       {
/* 563 */         if (this.lnxnflhd == 0)
/*     */         {
/* 565 */           throw new SQLException(CoreException.getMessage(5));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 571 */         this.lnxnflhd = 1;
/*     */       }
/*     */ 
/* 574 */       if (this.lnxnfzld > 1)
/*     */       {
/* 576 */         this.lnxnfzld = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 582 */     n += this.lnxnflhd;
/* 583 */     n += this.lnxnfrhd;
/* 584 */     n += i8 + 1;
/*     */ 
/* 587 */     if (n > 64)
/*     */     {
/* 589 */       throw new SQLException(CoreException.getMessage(5));
/*     */     }
/*     */ 
/* 593 */     this.lnxnfsiz = n;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.LnxLibThinFormat
 * JD-Core Version:    0.6.0
 */