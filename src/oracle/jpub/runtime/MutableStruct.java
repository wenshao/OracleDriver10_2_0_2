/*     */ package oracle.jpub.runtime;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import oracle.sql.CustomDatumFactory;
/*     */ import oracle.sql.Datum;
/*     */ import oracle.sql.ORADataFactory;
/*     */ import oracle.sql.STRUCT;
/*     */ import oracle.sql.StructDescriptor;
/*     */ 
/*     */ public class MutableStruct
/*     */ {
/*     */   int length;
/*     */   STRUCT pickled;
/*     */   Datum[] datums;
/*     */   Object[] attributes;
/*     */   CustomDatumFactory[] old_factories;
/*     */   ORADataFactory[] factories;
/*     */   int[] sqlTypes;
/*     */   boolean pickledCorrect;
/* 440 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:34_PST_2006";
/*     */ 
/*     */   public MutableStruct(STRUCT paramSTRUCT, int[] paramArrayOfInt, ORADataFactory[] paramArrayOfORADataFactory)
/*     */   {
/*  60 */     this.length = paramArrayOfORADataFactory.length;
/*  61 */     this.pickled = paramSTRUCT;
/*  62 */     this.factories = paramArrayOfORADataFactory;
/*  63 */     this.sqlTypes = paramArrayOfInt;
/*  64 */     this.pickledCorrect = true;
/*     */   }
/*     */ 
/*     */   public MutableStruct(Object[] paramArrayOfObject, int[] paramArrayOfInt, ORADataFactory[] paramArrayOfORADataFactory)
/*     */   {
/*  74 */     this.length = paramArrayOfORADataFactory.length;
/*  75 */     this.attributes = paramArrayOfObject;
/*  76 */     this.factories = paramArrayOfORADataFactory;
/*  77 */     this.sqlTypes = paramArrayOfInt;
/*  78 */     this.pickledCorrect = false;
/*     */   }
/*     */ 
/*     */   public MutableStruct(STRUCT paramSTRUCT, int[] paramArrayOfInt, CustomDatumFactory[] paramArrayOfCustomDatumFactory)
/*     */   {
/*  87 */     this.length = paramArrayOfCustomDatumFactory.length;
/*  88 */     this.pickled = paramSTRUCT;
/*  89 */     this.old_factories = paramArrayOfCustomDatumFactory;
/*  90 */     this.sqlTypes = paramArrayOfInt;
/*  91 */     this.pickledCorrect = true;
/*     */   }
/*     */ 
/*     */   public MutableStruct(Object[] paramArrayOfObject, int[] paramArrayOfInt, CustomDatumFactory[] paramArrayOfCustomDatumFactory)
/*     */   {
/* 100 */     this.length = paramArrayOfCustomDatumFactory.length;
/* 101 */     this.attributes = paramArrayOfObject;
/* 102 */     this.old_factories = paramArrayOfCustomDatumFactory;
/* 103 */     this.sqlTypes = paramArrayOfInt;
/* 104 */     this.pickledCorrect = false;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(Connection paramConnection, String paramString)
/*     */     throws SQLException
/*     */   {
/* 111 */     if (!this.pickledCorrect)
/*     */     {
/* 115 */       this.pickled = new STRUCT(StructDescriptor.createDescriptor(paramString, paramConnection), paramConnection, getDatumAttributes(paramConnection));
/*     */ 
/* 117 */       this.pickledCorrect = true;
/*     */     }
/*     */ 
/* 120 */     return this.pickled;
/*     */   }
/*     */ 
/*     */   public Datum toDatum(oracle.jdbc.OracleConnection paramOracleConnection, String paramString)
/*     */     throws SQLException
/*     */   {
/* 126 */     return toDatum(paramOracleConnection, paramString);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Datum toDatum(oracle.jdbc.driver.OracleConnection paramOracleConnection, String paramString)
/*     */     throws SQLException
/*     */   {
/* 135 */     return toDatum(paramOracleConnection, paramString);
/*     */   }
/*     */ 
/*     */   public Object getAttribute(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 145 */     Object localObject = getLazyAttributes()[paramInt];
/*     */ 
/* 147 */     if (localObject == null)
/*     */     {
/* 149 */       Datum localDatum = getLazyDatums()[paramInt];
/*     */ 
/* 151 */       if (this.old_factories == null)
/*     */       {
/* 153 */         localObject = Util.convertToObject(localDatum, this.sqlTypes[paramInt], this.factories[paramInt]);
/* 154 */         this.attributes[paramInt] = localObject;
/*     */ 
/* 156 */         if (Util.isMutable(localDatum, this.factories[paramInt]))
/* 157 */           resetDatum(paramInt);
/*     */       }
/*     */       else
/*     */       {
/* 161 */         localObject = Util.convertToObject(localDatum, this.sqlTypes[paramInt], this.old_factories[paramInt]);
/* 162 */         this.attributes[paramInt] = localObject;
/*     */ 
/* 164 */         if (Util.isMutable(localDatum, this.old_factories[paramInt])) {
/* 165 */           resetDatum(paramInt);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 171 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object getOracleAttribute(int paramInt)
/*     */     throws SQLException
/*     */   {
/*     */     Object localObject;
/*     */     Datum localDatum;
/* 181 */     if (this.old_factories == null)
/*     */     {
/* 183 */       if (this.factories[paramInt] == null)
/*     */       {
/* 185 */         localObject = getDatumAttribute(paramInt, null);
/*     */ 
/* 187 */         localDatum = getLazyDatums()[paramInt];
/*     */ 
/* 189 */         if (Util.isMutable(localDatum, this.factories[paramInt]))
/* 190 */           this.pickledCorrect = false;
/*     */       }
/*     */       else {
/* 193 */         localObject = getAttribute(paramInt);
/*     */       }
/*     */ 
/*     */     }
/* 197 */     else if (this.old_factories[paramInt] == null)
/*     */     {
/* 199 */       localObject = getDatumAttribute(paramInt, null);
/*     */ 
/* 201 */       localDatum = getLazyDatums()[paramInt];
/*     */ 
/* 203 */       if (Util.isMutable(localDatum, this.old_factories[paramInt]))
/* 204 */         this.pickledCorrect = false;
/*     */     }
/*     */     else {
/* 207 */       localObject = getAttribute(paramInt);
/*     */     }
/*     */ 
/* 212 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object[] getAttributes()
/*     */     throws SQLException
/*     */   {
/* 221 */     for (int i = 0; i < this.length; i++)
/*     */     {
/* 223 */       getAttribute(i);
/*     */     }
/*     */ 
/* 228 */     return this.attributes;
/*     */   }
/*     */ 
/*     */   public Object[] getOracleAttributes()
/*     */     throws SQLException
/*     */   {
/* 236 */     Object[] arrayOfObject = new Object[this.length];
/*     */ 
/* 238 */     for (int i = 0; i < this.length; i++)
/*     */     {
/* 240 */       arrayOfObject[i] = getOracleAttribute(i);
/*     */     }
/*     */ 
/* 245 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   public void setAttribute(int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 263 */     if (paramObject == null)
/*     */     {
/* 265 */       getLazyDatums();
/*     */     }
/*     */ 
/* 268 */     resetDatum(paramInt);
/*     */ 
/* 270 */     getLazyAttributes()[paramInt] = paramObject;
/*     */   }
/*     */ 
/*     */   public void setDoubleAttribute(int paramInt, double paramDouble) throws SQLException
/*     */   {
/* 275 */     setAttribute(paramInt, new Double(paramDouble));
/*     */   }
/*     */ 
/*     */   public void setFloatAttribute(int paramInt, float paramFloat) throws SQLException
/*     */   {
/* 280 */     setAttribute(paramInt, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */   public void setIntAttribute(int paramInt1, int paramInt2) throws SQLException
/*     */   {
/* 285 */     setAttribute(paramInt1, new Integer(paramInt2));
/*     */   }
/*     */ 
/*     */   public void setOracleAttribute(int paramInt, Object paramObject)
/*     */     throws SQLException
/*     */   {
/* 293 */     if (this.old_factories == null)
/*     */     {
/* 295 */       if (this.factories[paramInt] == null)
/* 296 */         setDatumAttribute(paramInt, (Datum)paramObject);
/*     */       else {
/* 298 */         setAttribute(paramInt, paramObject);
/*     */       }
/*     */ 
/*     */     }
/* 302 */     else if (this.old_factories[paramInt] == null)
/* 303 */       setDatumAttribute(paramInt, (Datum)paramObject);
/*     */     else
/* 305 */       setAttribute(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */   Datum getDatumAttribute(int paramInt, Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 314 */     Datum localDatum = getLazyDatums()[paramInt];
/*     */ 
/* 316 */     if (localDatum == null)
/*     */     {
/* 318 */       Object localObject = getLazyAttributes()[paramInt];
/*     */ 
/* 320 */       localDatum = Util.convertToOracle(localObject, paramConnection);
/* 321 */       this.datums[paramInt] = localDatum;
/*     */     }
/*     */ 
/* 326 */     return localDatum;
/*     */   }
/*     */ 
/*     */   void setDatumAttribute(int paramInt, Datum paramDatum) throws SQLException
/*     */   {
/* 331 */     resetAttribute(paramInt);
/*     */ 
/* 333 */     getLazyDatums()[paramInt] = paramDatum;
/* 334 */     this.pickledCorrect = false;
/*     */   }
/*     */ 
/*     */   Datum[] getDatumAttributes(Connection paramConnection) throws SQLException
/*     */   {
/* 339 */     for (int i = 0; i < this.length; i++)
/*     */     {
/* 341 */       getDatumAttribute(i, paramConnection);
/*     */     }
/*     */ 
/* 344 */     return (Datum[])this.datums.clone();
/*     */   }
/*     */ 
/*     */   void resetAttribute(int paramInt) throws SQLException
/*     */   {
/* 349 */     if (this.attributes != null)
/*     */     {
/* 351 */       this.attributes[paramInt] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   void resetDatum(int paramInt)
/*     */     throws SQLException
/*     */   {
/* 360 */     if (this.datums != null)
/*     */     {
/* 362 */       this.datums[paramInt] = null;
/*     */     }
/*     */ 
/* 370 */     this.pickledCorrect = false;
/*     */   }
/*     */ 
/*     */   Object[] getLazyAttributes()
/*     */   {
/* 375 */     if (this.attributes == null)
/*     */     {
/* 377 */       this.attributes = new Object[this.length];
/*     */     }
/*     */ 
/* 380 */     return this.attributes;
/*     */   }
/*     */ 
/*     */   Datum[] getLazyDatums()
/*     */     throws SQLException
/*     */   {
/* 389 */     if (this.datums == null)
/*     */     {
/* 393 */       if (this.pickled != null)
/*     */       {
/* 397 */         this.datums = this.pickled.getOracleAttributes();
/* 398 */         this.pickledCorrect = true;
/*     */ 
/* 404 */         if (this.attributes != null)
/*     */         {
/* 406 */           for (int i = 0; i < this.length; i++)
/*     */           {
/* 408 */             if (this.attributes[i] == null)
/*     */               continue;
/* 410 */             this.datums[i] = null;
/* 411 */             this.pickledCorrect = false;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 418 */         this.datums = new Datum[this.length];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 424 */     return this.datums;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jpub.runtime.MutableStruct
 * JD-Core Version:    0.6.0
 */