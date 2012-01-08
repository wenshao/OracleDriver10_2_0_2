/*      */ package oracle.sql;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Map;
/*      */ import oracle.jdbc.OraclePreparedStatement;
/*      */ import oracle.jdbc.OracleResultSet;
/*      */ import oracle.jdbc.driver.DatabaseError;
/*      */ import oracle.jdbc.oracore.OracleNamedType;
/*      */ import oracle.jdbc.oracore.OracleType;
/*      */ import oracle.jdbc.oracore.OracleTypeADT;
/*      */ import oracle.jdbc.oracore.OracleTypeCOLLECTION;
/*      */ import oracle.jdbc.oracore.OracleTypeFLOAT;
/*      */ import oracle.jdbc.oracore.OracleTypeNUMBER;
/*      */ import oracle.jdbc.oracore.OracleTypeREF;
/*      */ 
/*      */ public class ArrayDescriptor extends TypeDescriptor
/*      */   implements Serializable
/*      */ {
/*      */   public static final int TYPE_VARRAY = 3;
/*      */   public static final int TYPE_NESTED_TABLE = 2;
/*      */   public static final int CACHE_NONE = 0;
/*      */   public static final int CACHE_ALL = 1;
/*      */   public static final int CACHE_LAST = 2;
/*      */   static final long serialVersionUID = 3838105394346513809L;
/* 2035 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*      */   public static final boolean TRACE = false;
/*      */   public static final boolean PRIVATE_TRACE = false;
/*      */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:24_PST_2006";
/*      */ 
/*      */   public static ArrayDescriptor createDescriptor(String paramString, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  115 */     return createDescriptor(paramString, paramConnection, false, false);
/*      */   }
/*      */ 
/*      */   public static ArrayDescriptor createDescriptor(String paramString, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  139 */     if ((paramString == null) || (paramString.length() == 0) || (paramConnection == null))
/*      */     {
/*  144 */       DatabaseError.throwSqlException(60, "Invalid arguments");
/*      */     }
/*      */ 
/*  149 */     SQLName localSQLName = new SQLName(paramString, (oracle.jdbc.OracleConnection)paramConnection);
/*  150 */     return createDescriptor(localSQLName, paramConnection);
/*      */   }
/*      */ 
/*      */   public static ArrayDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  165 */     return createDescriptor(paramSQLName, paramConnection, false, false);
/*      */   }
/*      */ 
/*      */   public static ArrayDescriptor createDescriptor(SQLName paramSQLName, Connection paramConnection, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws SQLException
/*      */   {
/*  186 */     String str = paramSQLName.getName();
/*      */ 
/*  189 */     ArrayDescriptor localArrayDescriptor = null;
/*  190 */     if (!paramBoolean2) {
/*  191 */       localArrayDescriptor = (ArrayDescriptor)((oracle.jdbc.OracleConnection)paramConnection).getDescriptor(str);
/*      */     }
/*      */ 
/*  194 */     if (localArrayDescriptor == null)
/*      */     {
/*  196 */       localArrayDescriptor = new ArrayDescriptor(paramSQLName, paramConnection);
/*  197 */       if (paramBoolean1) localArrayDescriptor.initNamesRecursively();
/*  198 */       ((oracle.jdbc.OracleConnection)paramConnection).putDescriptor(str, localArrayDescriptor);
/*      */     }
/*      */ 
/*  202 */     return localArrayDescriptor;
/*      */   }
/*      */ 
/*      */   public static ArrayDescriptor createDescriptor(OracleTypeCOLLECTION paramOracleTypeCOLLECTION)
/*      */     throws SQLException
/*      */   {
/*  218 */     String str = paramOracleTypeCOLLECTION.getFullName();
/*  219 */     oracle.jdbc.internal.OracleConnection localOracleConnection = paramOracleTypeCOLLECTION.getConnection();
/*  220 */     ArrayDescriptor localArrayDescriptor = (ArrayDescriptor)localOracleConnection.getDescriptor(str);
/*      */ 
/*  224 */     if (localArrayDescriptor == null)
/*      */     {
/*  226 */       SQLName localSQLName = new SQLName(paramOracleTypeCOLLECTION.getSchemaName(), paramOracleTypeCOLLECTION.getSimpleName(), paramOracleTypeCOLLECTION.getConnection());
/*      */ 
/*  228 */       localArrayDescriptor = new ArrayDescriptor(localSQLName, paramOracleTypeCOLLECTION, localOracleConnection);
/*  229 */       localOracleConnection.putDescriptor(str, localArrayDescriptor);
/*      */     }
/*      */ 
/*  233 */     return localArrayDescriptor;
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor(String paramString, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  247 */     super(paramString, paramConnection);
/*      */ 
/*  251 */     initPickler();
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor(SQLName paramSQLName, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  268 */     super(paramSQLName, paramConnection);
/*      */ 
/*  272 */     initPickler();
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor(SQLName paramSQLName, OracleTypeCOLLECTION paramOracleTypeCOLLECTION, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  288 */     super(paramSQLName, paramOracleTypeCOLLECTION, paramConnection);
/*      */   }
/*      */ 
/*      */   public ArrayDescriptor(OracleTypeCOLLECTION paramOracleTypeCOLLECTION, Connection paramConnection)
/*      */     throws SQLException
/*      */   {
/*  304 */     super(paramOracleTypeCOLLECTION, paramConnection);
/*      */   }
/*      */ 
/*      */   static ArrayDescriptor createDescriptor(SQLName paramSQLName, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, oracle.jdbc.internal.OracleConnection paramOracleConnection, byte[] paramArrayOfByte4)
/*      */     throws SQLException
/*      */   {
/*  313 */     OracleTypeCOLLECTION localOracleTypeCOLLECTION = new OracleTypeCOLLECTION(paramSQLName, paramArrayOfByte1, paramInt, paramArrayOfByte2, paramArrayOfByte3, paramOracleConnection, paramArrayOfByte4);
/*  314 */     return new ArrayDescriptor(paramSQLName, localOracleTypeCOLLECTION, paramOracleConnection);
/*      */   }
/*      */ 
/*      */   public int getBaseType()
/*      */     throws SQLException
/*      */   {
/*  332 */     int i = ((OracleTypeCOLLECTION)this.pickler).getElementType().getTypeCode();
/*      */ 
/*  336 */     return i;
/*      */   }
/*      */ 
/*      */   public String getBaseName()
/*      */     throws SQLException
/*      */   {
/*  351 */     String str = null;
/*      */     OracleNamedType localOracleNamedType;
/*  353 */     switch (getBaseType())
/*      */     {
/*      */     case 12:
/*  357 */       str = "VARCHAR";
/*      */ 
/*  359 */       break;
/*      */     case 1:
/*  362 */       str = "CHAR";
/*      */ 
/*  364 */       break;
/*      */     case -2:
/*  367 */       str = "RAW";
/*      */ 
/*  369 */       break;
/*      */     case 6:
/*  372 */       str = "FLOAT";
/*      */ 
/*  374 */       break;
/*      */     case 2:
/*  377 */       str = "NUMBER";
/*      */ 
/*  379 */       break;
/*      */     case 8:
/*  382 */       str = "DOUBLE";
/*      */ 
/*  384 */       break;
/*      */     case 3:
/*  387 */       str = "DECIMAL";
/*      */ 
/*  389 */       break;
/*      */     case 91:
/*  392 */       str = "DATE";
/*      */ 
/*  394 */       break;
/*      */     case 93:
/*  397 */       str = "TIMESTAMP";
/*      */ 
/*  399 */       break;
/*      */     case -101:
/*  402 */       str = "TIMESTAMPTZ";
/*      */ 
/*  404 */       break;
/*      */     case -102:
/*  407 */       str = "TIMESTAMPLTZ";
/*      */ 
/*  409 */       break;
/*      */     case 2004:
/*  412 */       str = "BLOB";
/*      */ 
/*  414 */       break;
/*      */     case 2005:
/*  417 */       str = "CLOB";
/*      */ 
/*  419 */       break;
/*      */     case -13:
/*  422 */       str = "BFILE";
/*      */ 
/*  424 */       break;
/*      */     case 2002:
/*      */     case 2003:
/*      */     case 2007:
/*      */     case 2008:
/*  434 */       localOracleNamedType = (OracleNamedType)((OracleTypeCOLLECTION)this.pickler).getElementType();
/*      */ 
/*  437 */       str = localOracleNamedType.getFullName();
/*      */ 
/*  439 */       break;
/*      */     case 2006:
/*  444 */       localOracleNamedType = (OracleNamedType)((OracleTypeCOLLECTION)this.pickler).getElementType();
/*      */ 
/*  447 */       str = "REF " + ((OracleTypeREF)localOracleNamedType).getFullName();
/*      */ 
/*  449 */       break;
/*      */     case 1111:
/*      */     default:
/*  457 */       str = null;
/*      */     }
/*      */ 
/*  465 */     return str;
/*      */   }
/*      */ 
/*      */   public OracleTypeCOLLECTION getOracleTypeCOLLECTION()
/*      */   {
/*  477 */     return (OracleTypeCOLLECTION)this.pickler;
/*      */   }
/*      */ 
/*      */   public int getArrayType()
/*      */     throws SQLException
/*      */   {
/*  491 */     int i = ((OracleTypeCOLLECTION)this.pickler).getUserCode();
/*      */ 
/*  495 */     return i;
/*      */   }
/*      */ 
/*      */   public long getMaxLength()
/*      */     throws SQLException
/*      */   {
/*  509 */     long l = getArrayType() == 3 ? ((OracleTypeCOLLECTION)this.pickler).getMaxLength() : 0L;
/*      */ 
/*  514 */     return l;
/*      */   }
/*      */ 
/*      */   public String descType()
/*      */     throws SQLException
/*      */   {
/*  528 */     String str = null;
/*  529 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/*  531 */     str = descType(localStringBuffer, 0);
/*      */ 
/*  535 */     return str;
/*      */   }
/*      */ 
/*      */   String descType(StringBuffer paramStringBuffer, int paramInt)
/*      */     throws SQLException
/*      */   {
/*  543 */     String str1 = "";
/*      */ 
/*  545 */     for (int i = 0; i < paramInt; i++) {
/*  546 */       str1 = str1 + "  ";
/*      */     }
/*  548 */     String str2 = str1 + "  ";
/*      */ 
/*  550 */     paramStringBuffer.append(str1);
/*  551 */     paramStringBuffer.append(getTypeName());
/*  552 */     paramStringBuffer.append("\n");
/*      */ 
/*  554 */     int j = getBaseType();
/*      */ 
/*  556 */     if ((j == 2002) || (j == 2008))
/*      */     {
/*  559 */       localObject = StructDescriptor.createDescriptor(getBaseName(), this.connection);
/*      */ 
/*  562 */       ((StructDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */     }
/*  564 */     else if (j == 2003)
/*      */     {
/*  566 */       localObject = createDescriptor(getBaseName(), this.connection);
/*      */ 
/*  569 */       ((ArrayDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */     }
/*  571 */     else if (j == 2007)
/*      */     {
/*  573 */       localObject = OpaqueDescriptor.createDescriptor(getBaseName(), this.connection);
/*      */ 
/*  576 */       ((OpaqueDescriptor)localObject).descType(paramStringBuffer, paramInt + 1);
/*      */     }
/*      */     else
/*      */     {
/*  580 */       paramStringBuffer.append(str2);
/*  581 */       paramStringBuffer.append(getBaseName());
/*  582 */       paramStringBuffer.append("\n");
/*      */     }
/*      */ 
/*  585 */     Object localObject = paramStringBuffer.substring(0, paramStringBuffer.length());
/*      */ 
/*  589 */     return (String)localObject;
/*      */   }
/*      */ 
/*      */   int toLength(ARRAY paramARRAY)
/*      */     throws SQLException
/*      */   {
/*  612 */     if (paramARRAY.numElems == -1)
/*      */     {
/*  614 */       if (paramARRAY.datumArray != null)
/*      */       {
/*  616 */         paramARRAY.numElems = paramARRAY.datumArray.length;
/*      */       }
/*  618 */       else if (paramARRAY.objArray != null)
/*      */       {
/*  620 */         if ((paramARRAY.objArray instanceof Object[]))
/*  621 */           paramARRAY.numElems = ((Object[])paramARRAY.objArray).length;
/*  622 */         else if ((paramARRAY.objArray instanceof int[]))
/*  623 */           paramARRAY.numElems = ((long[])paramARRAY.objArray).length;
/*  624 */         else if ((paramARRAY.objArray instanceof long[]))
/*  625 */           paramARRAY.numElems = ((float[])paramARRAY.objArray).length;
/*  626 */         else if ((paramARRAY.objArray instanceof float[]))
/*  627 */           paramARRAY.numElems = ((double[])paramARRAY.objArray).length;
/*  628 */         else if ((paramARRAY.objArray instanceof double[]))
/*  629 */           paramARRAY.numElems = ((boolean[])paramARRAY.objArray).length;
/*  630 */         else if ((paramARRAY.objArray instanceof boolean[]))
/*  631 */           paramARRAY.numElems = ((int[])paramARRAY.objArray).length;
/*  632 */         else if ((paramARRAY.objArray instanceof byte[]))
/*  633 */           paramARRAY.numElems = ((byte[])paramARRAY.objArray).length;
/*  634 */         else if ((paramARRAY.objArray instanceof short[]))
/*  635 */           paramARRAY.numElems = ((short[])paramARRAY.objArray).length;
/*  636 */         else if ((paramARRAY.objArray instanceof char[]))
/*  637 */           paramARRAY.numElems = ((char[])paramARRAY.objArray).length;
/*      */       }
/*  639 */       else if (paramARRAY.locator != null)
/*      */       {
/*  641 */         paramARRAY.numElems = toLengthFromLocator(paramARRAY.locator);
/*      */       }
/*  643 */       else if (paramARRAY.shareBytes() != null)
/*      */       {
/*  645 */         this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, 0, null);
/*      */ 
/*  648 */         if (paramARRAY.numElems == -1)
/*      */         {
/*  650 */           if (paramARRAY.locator != null) {
/*  651 */             paramARRAY.numElems = toLengthFromLocator(paramARRAY.locator);
/*      */           }
/*      */           else
/*      */           {
/*  657 */             DatabaseError.throwSqlException(1, "Unable to get array length");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  668 */         DatabaseError.throwSqlException(1, "Array is in inconsistent status");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  673 */     int i = paramARRAY.numElems;
/*      */ 
/*  677 */     return i;
/*      */   }
/*      */ 
/*      */   byte[] toBytes(ARRAY paramARRAY, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  691 */     byte[] arrayOfByte1 = paramARRAY.shareBytes();
/*      */ 
/*  693 */     if (arrayOfByte1 == null)
/*      */     {
/*  695 */       if ((paramARRAY.datumArray != null) || (paramARRAY.locator != null))
/*      */       {
/*  697 */         arrayOfByte1 = this.pickler.linearize(paramARRAY);
/*      */ 
/*  699 */         if (!paramBoolean) {
/*  700 */           paramARRAY.setShareBytes(null);
/*      */         }
/*      */ 
/*      */       }
/*  705 */       else if (paramARRAY.objArray != null)
/*      */       {
/*  707 */         paramARRAY.datumArray = toOracleArray(paramARRAY.objArray, 1L, -1);
/*  708 */         arrayOfByte1 = this.pickler.linearize(paramARRAY);
/*      */ 
/*  710 */         if (!paramBoolean)
/*      */         {
/*  712 */           paramARRAY.datumArray = null;
/*      */ 
/*  714 */           paramARRAY.setShareBytes(null);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  722 */         DatabaseError.throwSqlException(1, "Array is in inconsistent status");
/*      */       }
/*      */ 
/*      */     }
/*  726 */     else if (paramARRAY.imageLength != 0L)
/*      */     {
/*  728 */       if ((paramARRAY.imageOffset != 0L) || (paramARRAY.imageLength != arrayOfByte1.length))
/*      */       {
/*  730 */         byte[] arrayOfByte2 = new byte[(int)paramARRAY.imageLength];
/*      */ 
/*  732 */         System.arraycopy(arrayOfByte1, (int)paramARRAY.imageOffset, arrayOfByte2, 0, (int)paramARRAY.imageLength);
/*      */ 
/*  734 */         paramARRAY.setImage(arrayOfByte2, 0L, 0L);
/*      */ 
/*  738 */         return arrayOfByte2;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  746 */     return arrayOfByte1;
/*      */   }
/*      */ 
/*      */   Datum[] toOracleArray(ARRAY paramARRAY, long paramLong, int paramInt, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  759 */     Datum[] arrayOfDatum1 = paramARRAY.datumArray;
/*      */ 
/*  761 */     if (arrayOfDatum1 == null)
/*      */     {
/*  763 */       if (paramARRAY.objArray != null)
/*      */       {
/*  765 */         arrayOfDatum1 = toOracleArray(paramARRAY.objArray, paramLong, paramInt);
/*      */       }
/*  770 */       else if (paramARRAY.locator != null)
/*      */       {
/*  772 */         arrayOfDatum1 = toOracleArrayFromLocator(paramARRAY.locator, paramLong, paramInt, null);
/*      */       }
/*  777 */       else if (paramARRAY.shareBytes() != null)
/*      */       {
/*  779 */         this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt, 1, null);
/*      */ 
/*  782 */         if (paramARRAY.locator != null)
/*      */         {
/*  787 */           arrayOfDatum1 = toOracleArrayFromLocator(paramARRAY.locator, paramLong, paramInt, null);
/*      */         }
/*      */         else
/*      */         {
/*  795 */           arrayOfDatum1 = paramARRAY.datumArray;
/*      */         }
/*      */ 
/*  798 */         if (!paramBoolean) {
/*  799 */           paramARRAY.datumArray = null;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  806 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  811 */       if (paramLong > arrayOfDatum1.length)
/*      */       {
/*  816 */         return new Datum[0];
/*      */       }
/*      */ 
/*  819 */       int i = (int)(paramInt == -1 ? arrayOfDatum1.length - paramLong + 1L : Math.min(arrayOfDatum1.length - paramLong + 1L, paramInt));
/*      */ 
/*  822 */       arrayOfDatum1 = new Datum[i];
/*      */ 
/*  824 */       System.arraycopy(paramARRAY.datumArray, (int)paramLong - 1, arrayOfDatum1, 0, i);
/*      */     }
/*      */ 
/*  827 */     Datum[] arrayOfDatum2 = null;
/*      */ 
/*  829 */     if (paramBoolean)
/*      */     {
/*  831 */       paramARRAY.datumArray = arrayOfDatum1;
/*  832 */       arrayOfDatum2 = (Datum[])arrayOfDatum1.clone();
/*      */     }
/*      */     else
/*      */     {
/*  836 */       arrayOfDatum2 = arrayOfDatum1;
/*      */     }
/*      */ 
/*  841 */     return arrayOfDatum2;
/*      */   }
/*      */ 
/*      */   Object[] toJavaArray(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/*  854 */     Object[] arrayOfObject1 = null;
/*      */ 
/*  856 */     if (paramARRAY.objArray != null)
/*      */     {
/*  858 */       arrayOfObject1 = (Object[])((Object[])paramARRAY.objArray).clone();
/*      */ 
/*  860 */       int i = arrayOfObject1.length;
/*  861 */       int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));
/*      */ 
/*  864 */       if (j <= 0)
/*      */       {
/*  866 */         Object[] arrayOfObject2 = (Object[])makeJavaArray(j, getBaseType());
/*      */ 
/*  870 */         return arrayOfObject2;
/*      */       }
/*      */ 
/*  874 */       arrayOfObject1 = (Object[])makeJavaArray(j, getBaseType());
/*      */ 
/*  876 */       System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, arrayOfObject1, 0, j);
/*      */     }
/*      */     else
/*      */     {
/*  881 */       if (paramARRAY.datumArray != null)
/*      */       {
/*  883 */         arrayOfObject1 = (Object[])toJavaArray(paramARRAY.datumArray, paramLong, paramInt, paramMap);
/*      */       }
/*  885 */       else if (paramARRAY.locator != null)
/*      */       {
/*  887 */         arrayOfObject1 = toArrayFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
/*      */       }
/*  889 */       else if (paramARRAY.shareBytes() != null)
/*      */       {
/*  891 */         this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt, 2, paramMap);
/*      */ 
/*  894 */         if (paramARRAY.locator != null)
/*  895 */           arrayOfObject1 = toArrayFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
/*      */         else {
/*  897 */           arrayOfObject1 = (Object[])paramARRAY.objArray;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  907 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/*  912 */       if ((paramBoolean) && (getBaseType() != 2002) && (getBaseType() != 2008) && (arrayOfObject1 != null))
/*      */       {
/*  914 */         paramARRAY.objArray = arrayOfObject1.clone();
/*      */       }
/*  916 */       else paramARRAY.objArray = null;
/*      */ 
/*      */     }
/*      */ 
/*  921 */     return arrayOfObject1;
/*      */   }
/*      */ 
/*      */   private Datum[] toOracleArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  933 */     int i = toLengthFromLocator(paramArrayOfByte);
/*  934 */     int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));
/*      */ 
/*  937 */     Datum[] arrayOfDatum = null;
/*      */ 
/*  939 */     if (j <= 0)
/*      */     {
/*  941 */       arrayOfDatum = new Datum[0];
/*      */     }
/*      */     else
/*      */     {
/*  945 */       arrayOfDatum = new Datum[j];
/*      */ 
/*  947 */       ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt, paramMap);
/*      */ 
/*  950 */       for (int k = 0; localResultSet.next(); k++) {
/*  951 */         arrayOfDatum[k] = ((OracleResultSet)localResultSet).getOracleObject(2);
/*      */       }
/*  953 */       localResultSet.close();
/*      */     }
/*      */ 
/*  958 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   private Object[] toArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/*  971 */     int i = toLengthFromLocator(paramArrayOfByte);
/*  972 */     int j = (int)(paramInt == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt));
/*      */ 
/*  974 */     Object[] arrayOfObject = null;
/*      */ 
/*  976 */     if (j <= 0)
/*      */     {
/*  981 */       arrayOfObject = (Object[])makeJavaArray(0, getBaseType());
/*      */     }
/*      */     else
/*      */     {
/*  988 */       arrayOfObject = (Object[])makeJavaArray(j, getBaseType());
/*      */ 
/*  990 */       ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt, paramMap);
/*      */ 
/*  993 */       for (int k = 0; localResultSet.next(); k++) {
/*  994 */         arrayOfObject[k] = ((OracleResultSet)localResultSet).getObject(2, paramMap);
/*      */       }
/*  996 */       localResultSet.close();
/*      */     }
/*      */ 
/* 1001 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSet(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1011 */     ResultSet localResultSet = null;
/*      */ 
/* 1013 */     if (paramARRAY.datumArray != null)
/*      */     {
/* 1015 */       localResultSet = toResultSet(paramARRAY.datumArray, paramLong, paramInt, paramMap);
/*      */     }
/* 1017 */     else if (paramARRAY.locator != null)
/*      */     {
/* 1019 */       localResultSet = toResultSetFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
/*      */     }
/* 1021 */     else if (paramARRAY.objArray != null)
/*      */     {
/* 1023 */       localResultSet = toResultSet(toOracleArray(paramARRAY.objArray, paramLong, paramInt), 1L, -1, paramMap);
/*      */     }
/* 1026 */     else if (paramARRAY.shareBytes() != null)
/*      */     {
/* 1030 */       if (((OracleTypeCOLLECTION)this.pickler).isInlineImage(paramARRAY.shareBytes(), (int)paramARRAY.imageOffset))
/*      */       {
/* 1033 */         localResultSet = toResultSetFromImage(paramARRAY, paramLong, paramInt, paramMap);
/*      */       }
/*      */       else
/*      */       {
/* 1037 */         this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, 1, null);
/*      */ 
/* 1040 */         if (paramARRAY.locator != null) {
/* 1041 */           localResultSet = toResultSetFromLocator(paramARRAY.locator, paramLong, paramInt, paramMap);
/*      */         }
/*      */         else
/*      */         {
/* 1047 */           DatabaseError.throwSqlException(1);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1052 */     if (localResultSet == null)
/*      */     {
/* 1057 */       DatabaseError.throwSqlException(1, "Unable to create array ResultSet");
/*      */     }
/*      */ 
/* 1063 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSet(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1082 */     ResultSet localResultSet = null;
/*      */ 
/* 1084 */     if (paramInt == -1)
/* 1085 */       localResultSet = this.connection.newArrayDataResultSet(paramArrayOfDatum, paramLong, paramArrayOfDatum.length, paramMap);
/*      */     else {
/* 1087 */       localResultSet = this.connection.newArrayDataResultSet(paramArrayOfDatum, paramLong, paramInt, paramMap);
/*      */     }
/*      */ 
/* 1091 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSetFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1110 */     ResultSet localResultSet = this.connection.newArrayLocatorResultSet(this, paramArrayOfByte, paramLong, paramInt, paramMap);
/*      */ 
/* 1115 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSetFromImage(ARRAY paramARRAY, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1124 */     ResultSet localResultSet = this.connection.newArrayDataResultSet(paramARRAY, paramLong, paramInt, paramMap);
/*      */ 
/* 1128 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public static Object[] makeJavaArray(int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1142 */     Object localObject = null;
/*      */ 
/* 1144 */     switch (paramInt2)
/*      */     {
/*      */     case -7:
/*      */     case -6:
/*      */     case -5:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/* 1164 */       localObject = new BigDecimal[paramInt1];
/*      */ 
/* 1166 */       break;
/*      */     case 1:
/*      */     case 12:
/* 1171 */       localObject = new String[paramInt1];
/*      */ 
/* 1173 */       break;
/*      */     case -102:
/*      */     case -101:
/*      */     case 91:
/*      */     case 92:
/*      */     case 93:
/* 1184 */       localObject = new Timestamp[paramInt1];
/*      */ 
/* 1186 */       break;
/*      */     case 2002:
/*      */     case 2008:
/* 1191 */       localObject = new Object[paramInt1];
/*      */ 
/* 1193 */       break;
/*      */     case -13:
/* 1196 */       localObject = new BFILE[paramInt1];
/*      */ 
/* 1198 */       break;
/*      */     case 2004:
/* 1201 */       localObject = new BLOB[paramInt1];
/*      */ 
/* 1203 */       break;
/*      */     case 2005:
/* 1206 */       localObject = new CLOB[paramInt1];
/*      */ 
/* 1208 */       break;
/*      */     case -3:
/*      */     case -2:
/* 1213 */       localObject = new byte[paramInt1][];
/*      */ 
/* 1215 */       break;
/*      */     case 2006:
/* 1218 */       localObject = new REF[paramInt1];
/*      */ 
/* 1220 */       break;
/*      */     case 2003:
/* 1223 */       localObject = new Object[paramInt1];
/*      */ 
/* 1225 */       break;
/*      */     default:
/* 1233 */       DatabaseError.throwSqlException(1, "makeJavaArray doesn't support type " + paramInt2);
/*      */     }
/*      */ 
/* 1243 */     return (Object)localObject;
/*      */   }
/*      */ 
/*      */   private int toLengthFromLocator(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1257 */     ARRAY localARRAY = new ARRAY(this, this.connection, (byte[])null);
/*      */ 
/* 1259 */     localARRAY.setLocator(paramArrayOfByte);
/*      */ 
/* 1261 */     int i = 0;
/*      */ 
/* 1264 */     OraclePreparedStatement localOraclePreparedStatement = null;
/* 1265 */     OracleResultSet localOracleResultSet = null;
/*      */ 
/* 1267 */     localOraclePreparedStatement = (OraclePreparedStatement)this.connection.prepareStatement("SELECT count(*) FROM TABLE( CAST(:1 AS " + getName() + ") )");
/*      */ 
/* 1271 */     localOraclePreparedStatement.setArray(1, localARRAY);
/*      */ 
/* 1273 */     localOracleResultSet = (OracleResultSet)localOraclePreparedStatement.executeQuery();
/*      */ 
/* 1275 */     if (localOracleResultSet.next()) {
/* 1276 */       i = localOracleResultSet.getInt(1);
/*      */     }
/*      */     else
/*      */     {
/* 1282 */       DatabaseError.throwSqlException(1, "Fail to access array storage table");
/*      */     }
/*      */ 
/* 1286 */     localOracleResultSet.close();
/* 1287 */     localOraclePreparedStatement.close();
/*      */ 
/* 1291 */     return i;
/*      */   }
/*      */ 
/*      */   Datum[] toOracleArray(Object paramObject, long paramLong, int paramInt)
/*      */     throws SQLException
/*      */   {
/* 1308 */     Datum[] arrayOfDatum = null;
/*      */ 
/* 1310 */     if (paramObject != null)
/*      */     {
/* 1312 */       OracleType localOracleType = getElementType();
/*      */ 
/* 1314 */       arrayOfDatum = localOracleType.toDatumArray(paramObject, this.connection, paramLong, paramInt);
/*      */     }
/*      */ 
/* 1319 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   private Object toJavaArray(Datum[] paramArrayOfDatum, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1328 */     int i = (int)(paramInt == -1 ? paramArrayOfDatum.length - paramLong + 1L : Math.min(paramArrayOfDatum.length - paramLong + 1L, paramInt));
/*      */ 
/* 1331 */     if (i < 0) {
/* 1332 */       i = 0;
/*      */     }
/* 1334 */     Object[] arrayOfObject = (Object[])makeJavaArray(i, getBaseType());
/*      */ 
/* 1336 */     if (getBaseType() == 2002)
/*      */     {
/* 1338 */       localObject = null;
/*      */ 
/* 1340 */       for (j = 0; j < i; j++)
/*      */       {
/* 1342 */         localObject = (STRUCT)paramArrayOfDatum[((int)paramLong + j - 1)];
/* 1343 */         arrayOfObject[j] = (localObject != null ? ((STRUCT)localObject).toJdbc(paramMap) : null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1348 */     Object localObject = null;
/*      */ 
/* 1350 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1352 */       localObject = paramArrayOfDatum[((int)paramLong + j - 1)];
/* 1353 */       arrayOfObject[j] = (localObject != null ? ((Datum)localObject).toJdbc() : null);
/*      */     }
/*      */ 
/* 1359 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   private Object toNumericArray(Datum[] paramArrayOfDatum, long paramLong, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1369 */     Object localObject1 = null;
/*      */ 
/* 1371 */     int i = (int)(paramInt1 == -1 ? paramArrayOfDatum.length - paramLong + 1L : Math.min(paramArrayOfDatum.length - paramLong + 1L, paramInt1));
/*      */ 
/* 1374 */     if (i < 0)
/* 1375 */       i = 0;
/*      */     Object localObject2;
/*      */     int j;
/*      */     Datum localDatum;
/* 1377 */     switch (paramInt2)
/*      */     {
/*      */     case 4:
/* 1381 */       localObject2 = new int[i];
/*      */ 
/* 1383 */       for (j = 0; j < i; j++)
/*      */       {
/* 1385 */         localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];
/*      */ 
/* 1387 */         if (localDatum != null) {
/* 1388 */           localObject2[j] = localDatum.intValue();
/*      */         }
/*      */       }
/* 1391 */       localObject1 = localObject2;
/* 1392 */       break;
/*      */     case 5:
/* 1397 */       localObject2 = new double[i];
/*      */ 
/* 1399 */       for (j = 0; j < i; j++)
/*      */       {
/* 1401 */         localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];
/*      */ 
/* 1403 */         if (localDatum != null) {
/* 1404 */           localObject2[j] = localDatum.doubleValue();
/*      */         }
/*      */       }
/* 1407 */       localObject1 = localObject2;
/* 1408 */       break;
/*      */     case 6:
/* 1413 */       localObject2 = new float[i];
/*      */ 
/* 1415 */       for (j = 0; j < i; j++)
/*      */       {
/* 1417 */         localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];
/*      */ 
/* 1419 */         if (localDatum != null) {
/* 1420 */           localObject2[j] = localDatum.floatValue();
/*      */         }
/*      */       }
/* 1423 */       localObject1 = localObject2;
/* 1424 */       break;
/*      */     case 7:
/* 1429 */       localObject2 = new long[i];
/*      */ 
/* 1431 */       for (j = 0; j < i; j++)
/*      */       {
/* 1433 */         localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];
/*      */ 
/* 1435 */         if (localDatum != null) {
/* 1436 */           localObject2[j] = localDatum.longValue();
/*      */         }
/*      */       }
/* 1439 */       localObject1 = localObject2;
/* 1440 */       break;
/*      */     case 8:
/* 1445 */       localObject2 = new short[i];
/*      */ 
/* 1447 */       for (j = 0; j < i; j++)
/*      */       {
/* 1449 */         localDatum = paramArrayOfDatum[((int)paramLong + j - 1)];
/*      */ 
/* 1451 */         if (localDatum != null) {
/* 1452 */           localObject2[j] = ((NUMBER)localDatum).shortValue();
/*      */         }
/*      */       }
/* 1455 */       localObject1 = localObject2;
/* 1456 */       break;
/*      */     default:
/* 1466 */       DatabaseError.throwSqlException(23);
/*      */     }
/*      */ 
/* 1473 */     return localObject1;
/*      */   }
/*      */ 
/*      */   private Object toNumericArrayFromLocator(byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2)
/*      */     throws SQLException
/*      */   {
/* 1486 */     Object localObject1 = null;
/*      */ 
/* 1488 */     int i = toLengthFromLocator(paramArrayOfByte);
/*      */ 
/* 1490 */     ResultSet localResultSet = toResultSetFromLocator(paramArrayOfByte, paramLong, paramInt1, null);
/*      */ 
/* 1492 */     int j = 0;
/*      */     Object localObject2;
/* 1494 */     switch (paramInt2)
/*      */     {
/*      */     case 4:
/* 1498 */       localObject2 = new int[i];
/*      */ 
/* 1500 */       while ((localResultSet.next()) && (j < i)) {
/* 1501 */         localObject2[(j++)] = ((OracleResultSet)localResultSet).getInt(2);
/*      */       }
/* 1503 */       localResultSet.close();
/*      */ 
/* 1505 */       localObject1 = localObject2;
/* 1506 */       break;
/*      */     case 5:
/* 1511 */       localObject2 = new double[i];
/*      */ 
/* 1513 */       while ((localResultSet.next()) && (j < i)) {
/* 1514 */         localObject2[(j++)] = ((OracleResultSet)localResultSet).getDouble(2);
/*      */       }
/* 1516 */       localResultSet.close();
/*      */ 
/* 1518 */       localObject1 = localObject2;
/* 1519 */       break;
/*      */     case 6:
/* 1524 */       localObject2 = new float[i];
/*      */ 
/* 1526 */       while ((localResultSet.next()) && (j < i)) {
/* 1527 */         localObject2[(j++)] = ((OracleResultSet)localResultSet).getFloat(2);
/*      */       }
/* 1529 */       localResultSet.close();
/*      */ 
/* 1531 */       localObject1 = localObject2;
/* 1532 */       break;
/*      */     case 7:
/* 1537 */       localObject2 = new long[i];
/*      */ 
/* 1539 */       while ((localResultSet.next()) && (j < i)) {
/* 1540 */         localObject2[(j++)] = ((OracleResultSet)localResultSet).getLong(2);
/*      */       }
/* 1542 */       localResultSet.close();
/*      */ 
/* 1544 */       localObject1 = localObject2;
/* 1545 */       break;
/*      */     case 8:
/* 1550 */       localObject2 = new short[i];
/*      */ 
/* 1552 */       while ((localResultSet.next()) && (j < i)) {
/* 1553 */         localObject2[(j++)] = ((OracleResultSet)localResultSet).getShort(2);
/*      */       }
/* 1555 */       localResultSet.close();
/*      */ 
/* 1557 */       localObject1 = localObject2;
/* 1558 */       break;
/*      */     default:
/* 1565 */       DatabaseError.throwSqlException(23);
/*      */     }
/*      */ 
/* 1570 */     return localObject1;
/*      */   }
/*      */ 
/*      */   Object toNumericArray(ARRAY paramARRAY, long paramLong, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */     throws SQLException
/*      */   {
/* 1583 */     if ((!(getElementType() instanceof OracleTypeNUMBER)) && (!(getElementType() instanceof OracleTypeFLOAT)))
/*      */     {
/* 1589 */       DatabaseError.throwSqlException(23);
/*      */     }
/*      */ 
/* 1592 */     Object localObject1 = null;
/*      */ 
/* 1595 */     if (paramARRAY.objArray != null)
/*      */     {
/*      */       int i;
/*      */       Object localObject2;
/* 1597 */       if ((paramInt2 == 4) && ((paramARRAY.objArray instanceof int[])))
/*      */       {
/* 1599 */         i = ((int[])paramARRAY.objArray).length;
/*      */ 
/* 1601 */         if (paramLong > i) {
/* 1602 */           return new int[0];
/*      */         }
/* 1604 */         i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));
/*      */ 
/* 1607 */         localObject2 = new int[i];
/*      */ 
/* 1609 */         System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);
/*      */ 
/* 1612 */         localObject1 = localObject2;
/*      */       }
/* 1614 */       else if ((paramInt2 == 5) && ((paramARRAY.objArray instanceof double[])))
/*      */       {
/* 1616 */         i = ((double[])paramARRAY.objArray).length;
/*      */ 
/* 1618 */         if (paramLong > i) {
/* 1619 */           return new double[0];
/*      */         }
/* 1621 */         i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));
/*      */ 
/* 1624 */         localObject2 = new double[i];
/*      */ 
/* 1626 */         System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);
/*      */ 
/* 1629 */         localObject1 = localObject2;
/*      */       }
/* 1631 */       else if ((paramInt2 == 6) && ((paramARRAY.objArray instanceof float[])))
/*      */       {
/* 1633 */         i = ((float[])paramARRAY.objArray).length;
/*      */ 
/* 1635 */         if (paramLong > i) {
/* 1636 */           return new float[0];
/*      */         }
/* 1638 */         i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));
/*      */ 
/* 1641 */         localObject2 = new float[i];
/*      */ 
/* 1643 */         System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);
/*      */ 
/* 1646 */         localObject1 = localObject2;
/*      */       }
/* 1648 */       else if ((paramInt2 == 7) && ((paramARRAY.objArray instanceof long[])))
/*      */       {
/* 1650 */         i = ((long[])paramARRAY.objArray).length;
/*      */ 
/* 1652 */         if (paramLong > i) {
/* 1653 */           return new long[0];
/*      */         }
/* 1655 */         i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));
/*      */ 
/* 1658 */         localObject2 = new long[i];
/*      */ 
/* 1660 */         System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);
/*      */ 
/* 1663 */         localObject1 = localObject2;
/*      */       }
/* 1665 */       else if ((paramInt2 == 8) && ((paramARRAY.objArray instanceof short[])))
/*      */       {
/* 1667 */         i = ((short[])paramARRAY.objArray).length;
/*      */ 
/* 1669 */         if (paramLong > i) {
/* 1670 */           return new short[0];
/*      */         }
/* 1672 */         i = (int)(paramInt1 == -1 ? i - paramLong + 1L : Math.min(i - paramLong + 1L, paramInt1));
/*      */ 
/* 1675 */         localObject2 = new short[i];
/*      */ 
/* 1677 */         System.arraycopy(paramARRAY.objArray, (int)paramLong - 1, localObject2, 0, i);
/*      */ 
/* 1680 */         localObject1 = localObject2;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1687 */       if (paramARRAY.datumArray != null)
/*      */       {
/* 1689 */         localObject1 = toNumericArray(paramARRAY.datumArray, paramLong, paramInt1, paramInt2);
/*      */       }
/* 1694 */       else if (paramARRAY.locator != null)
/*      */       {
/* 1696 */         localObject1 = toNumericArrayFromLocator(paramARRAY.locator, paramLong, paramInt1, paramInt2);
/*      */       }
/* 1702 */       else if (paramARRAY.shareBytes() != null)
/*      */       {
/* 1704 */         this.pickler.unlinearize(paramARRAY.shareBytes(), paramARRAY.imageOffset, paramARRAY, paramLong, paramInt1, paramInt2, null);
/*      */ 
/* 1707 */         if (paramARRAY.locator != null)
/*      */         {
/* 1712 */           localObject1 = toNumericArrayFromLocator(paramARRAY.locator, paramLong, paramInt1, paramInt2);
/*      */         }
/*      */         else
/*      */         {
/* 1720 */           localObject1 = paramARRAY.objArray;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1731 */         DatabaseError.throwSqlException(1);
/*      */       }
/*      */ 
/* 1736 */       if (!paramBoolean) {
/* 1737 */         paramARRAY.objArray = null;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1743 */     return localObject1;
/*      */   }
/*      */ 
/*      */   private void initPickler()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 1754 */       OracleTypeADT localOracleTypeADT = new OracleTypeADT(getName(), this.connection);
/*      */ 
/* 1756 */       localOracleTypeADT.init(this.connection);
/*      */ 
/* 1758 */       this.pickler = ((OracleTypeCOLLECTION)localOracleTypeADT.cleanup());
/*      */ 
/* 1760 */       this.pickler.setDescriptor(this);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1770 */       if ((localException instanceof SQLException))
/*      */       {
/* 1772 */         throw ((SQLException)localException);
/*      */       }
/*      */ 
/* 1776 */       DatabaseError.throwSqlException(60, "Unable to resolve type: \"" + getName() + "\"");
/*      */     }
/*      */   }
/*      */ 
/*      */   private OracleType getElementType()
/*      */     throws SQLException
/*      */   {
/* 1797 */     OracleType localOracleType = ((OracleTypeCOLLECTION)this.pickler).getElementType();
/*      */ 
/* 1801 */     return localOracleType;
/*      */   }
/*      */ 
/*      */   public int getTypeCode()
/*      */     throws SQLException
/*      */   {
/* 1809 */     int i = 2003;
/*      */ 
/* 1813 */     return i;
/*      */   }
/*      */ 
/*      */   public byte[] toBytes(Datum[] paramArrayOfDatum)
/*      */     throws SQLException
/*      */   {
/* 1837 */     ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfDatum);
/*      */ 
/* 1839 */     return this.pickler.linearize(localARRAY);
/*      */   }
/*      */ 
/*      */   public byte[] toBytes(Object[] paramArrayOfObject)
/*      */     throws SQLException
/*      */   {
/* 1859 */     Datum[] arrayOfDatum = toArray(paramArrayOfObject);
/* 1860 */     byte[] arrayOfByte = toBytes(arrayOfDatum);
/*      */ 
/* 1864 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */   public int length(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1878 */     ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfByte);
/* 1879 */     int i = toLength(localARRAY);
/*      */ 
/* 1883 */     return i;
/*      */   }
/*      */ 
/*      */   public Datum[] toArray(byte[] paramArrayOfByte)
/*      */     throws SQLException
/*      */   {
/* 1898 */     Datum[] arrayOfDatum = null;
/* 1899 */     if (paramArrayOfByte != null)
/*      */     {
/* 1901 */       ARRAY localARRAY = new ARRAY(this, this.connection, paramArrayOfByte);
/*      */ 
/* 1903 */       arrayOfDatum = toOracleArray(localARRAY, 1L, -1, false);
/*      */     }
/*      */ 
/* 1907 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public Datum[] toArray(Object paramObject)
/*      */     throws SQLException
/*      */   {
/* 1924 */     Datum[] arrayOfDatum = toOracleArray(paramObject, 1L, -1);
/*      */ 
/* 1928 */     return arrayOfDatum;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSet(byte[] paramArrayOfByte, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1944 */     ResultSet localResultSet = null;
/* 1945 */     if (paramArrayOfByte != null)
/*      */     {
/* 1947 */       ARRAY localARRAY = (ARRAY)this.pickler.unlinearize(paramArrayOfByte, 0L, null, 1, null);
/*      */ 
/* 1950 */       localResultSet = toResultSet(localARRAY, 1L, -1, paramMap, false);
/*      */     }
/*      */ 
/* 1954 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public ResultSet toResultSet(byte[] paramArrayOfByte, long paramLong, int paramInt, Map paramMap)
/*      */     throws SQLException
/*      */   {
/* 1972 */     ResultSet localResultSet = null;
/*      */ 
/* 1974 */     if (paramArrayOfByte != null)
/*      */     {
/* 1976 */       ARRAY localARRAY = (ARRAY)this.pickler.unlinearize(paramArrayOfByte, 0L, (ARRAY)null, 1, null);
/*      */ 
/* 1979 */       localResultSet = toResultSet(localARRAY, paramLong, paramInt, paramMap, false);
/*      */     }
/*      */ 
/* 1984 */     return localResultSet;
/*      */   }
/*      */ 
/*      */   public static int getCacheStyle(ARRAY paramARRAY)
/*      */     throws SQLException
/*      */   {
/* 1995 */     int i = 2;
/*      */ 
/* 1997 */     if ((paramARRAY.getAutoIndexing()) && ((paramARRAY.getAccessDirection() == 2) || (paramARRAY.getAccessDirection() == 3)))
/*      */     {
/* 2000 */       i = 1;
/*      */     }
/*      */ 
/* 2005 */     return i;
/*      */   }
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ArrayDescriptor
 * JD-Core Version:    0.6.0
 */