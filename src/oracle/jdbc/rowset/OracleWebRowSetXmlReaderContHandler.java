/*      */ package oracle.jdbc.rowset;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.RowSetMetaData;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ class OracleWebRowSetXmlReaderContHandler extends DefaultHandler
/*      */ {
/*      */   private OracleWebRowSet wrset;
/*      */   private RowSetMetaData rsetMetaData;
/*      */   private Vector updatesToRowSet;
/*      */   private Vector keyCols;
/*      */   private String columnValue;
/*      */   private String propertyValue;
/*      */   private String metadataValue;
/*      */   private boolean isNullValue;
/*      */   private int columnIndex;
/*      */   private Hashtable propertyNameTagMap;
/*      */   private Hashtable metadataNameTagMap;
/*      */   private Hashtable dataNameTagMap;
/*      */   protected static final String WEBROWSET_ELEMENT_NAME = "webRowSet";
/*      */   protected static final String PROPERTIES_ELEMENT_NAME = "properties";
/*      */   protected static final String METADATA_ELEMENT_NAME = "metadata";
/*      */   protected static final String DATA_ELEMENT_NAME = "data";
/*      */   private int state;
/*      */   private static final int INITIAL_STATE = 0;
/*      */   private static final int PROPERTIES_STATE = 1;
/*      */   private static final int METADATA_STATE = 2;
/*      */   private static final int DATA_STATE = 3;
/*      */   private int tag;
/*      */   private static final int NO_TAG = -1;
/*   82 */   private String[] propertyNames = { "command", "concurrency", "datasource", "escape-processing", "fetch-direction", "fetch-size", "isolation-level", "key-columns", "map", "max-field-size", "max-rows", "query-timeout", "read-only", "rowset-type", "show-deleted", "table-name", "url", "sync-provider", "null", "column", "type", "class", "sync-provider-name", "sync-provider-vendor", "sync-provider-version", "sync-provider-grade", "data-source-lock" };
/*      */   private boolean readReadOnlyValue;
/*      */   private static final int PROPERTY_COMMAND_TAG = 0;
/*      */   private static final int PROPERTY_CONCURRENCY_TAG = 1;
/*      */   private static final int PROPERTY_DATASOURCETAG = 2;
/*      */   private static final int PROPERTY_ESCAPEPROCESSING_TAG = 3;
/*      */   private static final int PROPERTY_FETCHDIRECTION_TAG = 4;
/*      */   private static final int PROPERTY_FETCHSIZE_TAG = 5;
/*      */   private static final int PROPERTY_ISOLATIONLEVEL_TAG = 6;
/*      */   private static final int PROPERTY_KEYCOLUMNS_TAG = 7;
/*      */   private static final int PROPERTY_MAP_TAG = 8;
/*      */   private static final int PROPERTY_MAXFIELDSIZE_TAG = 9;
/*      */   private static final int PROPERTY_MAXROWS_TAG = 10;
/*      */   private static final int PROPERTY_QUERYTIMEOUT_TAG = 11;
/*      */   private static final int PROPERTY_READONLY_TAG = 12;
/*      */   private static final int PROPERTY_ROWSETTYPE_TAG = 13;
/*      */   private static final int PROPERTY_SHOWDELETED_TAG = 14;
/*      */   private static final int PROPERTY_TABLENAME_TAG = 15;
/*      */   private static final int PROPERTY_URL_TAG = 16;
/*      */   private static final int PROPERTY_SYNCPROVIDER_TAG = 17;
/*      */   private static final int PROPERTY_NULL_TAG = 18;
/*      */   private static final int PROPERTY_COLUMN_TAG = 19;
/*      */   private static final int PROPERTY_TYPE_TAG = 20;
/*      */   private static final int PROPERTY_CLASS_TAG = 21;
/*      */   private static final int PROPERTY_SYNCPROVIDERNAME_TAG = 22;
/*      */   private static final int PROPERTY_SYNCPROVIDERVENDOR_TAG = 23;
/*      */   private static final int PROPERTY_SYNCPROVIDERVERSION_TAG = 24;
/*      */   private static final int PROPERTY_SYNCPROVIDERGRADE_TAG = 25;
/*      */   private static final int PROPERTY_DATASOURCELOCK_TAG = 26;
/*  147 */   private String[] metadataNames = { "column-count", "column-definition", "column-index", "auto-increment", "case-sensitive", "currency", "nullable", "signed", "searchable", "column-display-size", "column-label", "column-name", "schema-name", "column-precision", "column-scale", "table-name", "catalog-name", "column-type", "column-type-name", "null" };
/*      */   private static final int METADATA_COLUMNCOUNT_TAG = 0;
/*      */   private static final int METADATA_COLUMNDEFINITION_TAG = 1;
/*      */   private static final int METADATA_COLUMNINDEX_TAG = 2;
/*      */   private static final int METADATA_AUTOINCREMENT_TAG = 3;
/*      */   private static final int METADATA_CASESENSITIVE_TAG = 4;
/*      */   private static final int METADATA_CURRENCY_TAG = 5;
/*      */   private static final int METADATA_NULLABLE_TAG = 6;
/*      */   private static final int METADATA_SIGNED_TAG = 7;
/*      */   private static final int METADATA_SEARCHABLE_TAG = 8;
/*      */   private static final int METADATA_COLUMNDISPLAYSIZE_TAG = 9;
/*      */   private static final int METADATA_COLUMNLABEL_TAG = 10;
/*      */   private static final int METADATA_COLUMNNAME_TAG = 11;
/*      */   private static final int METADATA_SCHEMANAME_TAG = 12;
/*      */   private static final int METADATA_COLUMNPRECISION_TAG = 13;
/*      */   private static final int METADATA_COLUMNSCALE_TAG = 14;
/*      */   private static final int METADATA_TABLENAME_TAG = 15;
/*      */   private static final int METADATA_CATALOGNAME_TAG = 16;
/*      */   private static final int METADATA_COLUMNTYPE_TAG = 17;
/*      */   private static final int METADATA_COLUMNTYPENAME_TAG = 18;
/*      */   private static final int METADATA_NULL_TAG = 19;
/*  195 */   private String[] dataNames = { "currentRow", "insertRow", "deleteRow", "modifyRow", "columnValue", "updateValue", "null" };
/*      */   private static final int DATA_CURRENTROW_TAG = 0;
/*      */   private static final int DATA_INSERTROW_TAG = 1;
/*      */   private static final int DATA_DELETEROW_TAG = 2;
/*      */   private static final int DATA_MODIFYROW_TAG = 3;
/*      */   private static final int DATA_COLUMNVALUE_TAG = 4;
/*      */   private static final int DATA_UPDATEVALUE_TAG = 5;
/*      */   private static final int DATA_NULL_TAG = 6;
/*      */ 
/*      */   OracleWebRowSetXmlReaderContHandler(RowSet paramRowSet)
/*      */   {
/*  222 */     this.wrset = ((OracleWebRowSet)paramRowSet);
/*      */ 
/*  224 */     initialize();
/*      */   }
/*      */ 
/*      */   public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws SAXException
/*      */   {
/*  245 */     String str = new String(paramArrayOfChar, paramInt1, paramInt2);
/*  246 */     processElement(str);
/*      */   }
/*      */ 
/*      */   public void endDocument()
/*      */     throws SAXException
/*      */   {
/*      */     try
/*      */     {
/*  259 */       if (this.readReadOnlyValue)
/*      */       {
/*  261 */         this.wrset.setReadOnly(this.readReadOnlyValue);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  269 */       throw new SAXException(localSQLException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void endElement(String paramString1, String paramString2, String paramString3)
/*      */     throws SAXException
/*      */   {
/*  294 */     String str = (paramString2 == null) || (paramString2.equals("")) ? paramString3 : paramString2;
/*      */ 
/*  296 */     switch (getState())
/*      */     {
/*      */     case 1:
/*  299 */       if (str.equals("properties"))
/*      */       {
/*  301 */         this.state = 0;
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/*  307 */           int i = ((Integer)this.propertyNameTagMap.get(str)).intValue();
/*      */ 
/*  309 */           switch (i)
/*      */           {
/*      */           case 7:
/*  312 */             if (this.keyCols == null)
/*      */               break;
/*  314 */             int[] arrayOfInt = new int[this.keyCols.size()];
/*      */ 
/*  316 */             for (int k = 0; k < arrayOfInt.length; k++) {
/*  317 */               arrayOfInt[k] = Integer.parseInt((String)this.keyCols.elementAt(k));
/*      */             }
/*  319 */             this.wrset.setKeyColumns(arrayOfInt);
/*      */           }
/*      */ 
/*  332 */           setPropertyValue(this.propertyValue);
/*      */         }
/*      */         catch (SQLException localSQLException1)
/*      */         {
/*  337 */           throw new SAXException(localSQLException1.getMessage());
/*      */         }
/*      */ 
/*  341 */         this.propertyValue = new String("");
/*      */ 
/*  344 */         setNullValue(false);
/*      */ 
/*  347 */         setTag(-1);
/*      */       }
/*      */ 
/*  350 */       break;
/*      */     case 2:
/*  353 */       if (str.equals("metadata"))
/*      */       {
/*      */         try
/*      */         {
/*  357 */           this.wrset.setMetaData(this.rsetMetaData);
/*      */ 
/*  362 */           this.state = 0;
/*      */         }
/*      */         catch (SQLException localSQLException2)
/*      */         {
/*  370 */           throw new SAXException("Error setting metadata in WebRowSet: " + localSQLException2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/*  389 */           setMetaDataValue(this.metadataValue);
/*      */         }
/*      */         catch (SQLException localSQLException3)
/*      */         {
/*  400 */           throw new SAXException("Error setting metadata value: " + localSQLException3.getMessage());
/*      */         }
/*      */ 
/*  408 */         this.metadataValue = new String("");
/*      */ 
/*  411 */         setNullValue(false);
/*      */ 
/*  414 */         setTag(-1);
/*      */       }
/*      */ 
/*  417 */       break;
/*      */     case 3:
/*  420 */       if (str.equals("data"))
/*      */       {
/*  422 */         this.state = 0;
/*  423 */         return;
/*      */       }
/*      */ 
/*  427 */       int j = ((Integer)this.dataNameTagMap.get(str)).intValue();
/*      */ 
/*  429 */       switch (j)
/*      */       {
/*      */       default:
/*  432 */         break;
/*      */       case 4:
/*      */         try
/*      */         {
/*  437 */           this.columnIndex += 1;
/*      */ 
/*  448 */           insertValue(this.columnValue);
/*      */ 
/*  452 */           this.columnValue = new String("");
/*      */ 
/*  455 */           setNullValue(false);
/*      */ 
/*  458 */           setTag(-1);
/*      */         }
/*      */         catch (SQLException localSQLException4)
/*      */         {
/*  462 */           throw new SAXException("Error inserting column values: " + localSQLException4.getMessage());
/*      */         }
/*      */ 
/*      */       case 0:
/*      */         try
/*      */         {
/*  472 */           this.wrset.insertRow();
/*  473 */           this.wrset.moveToCurrentRow();
/*  474 */           this.wrset.next();
/*      */ 
/*  476 */           OracleRow localOracleRow1 = this.wrset.getCurrentRow();
/*  477 */           localOracleRow1.setInsertedFlag(false);
/*  478 */           applyUpdates();
/*      */         }
/*      */         catch (SQLException localSQLException5)
/*      */         {
/*  482 */           throw new SAXException("Error constructing current row: " + localSQLException5.getMessage());
/*      */         }
/*      */ 
/*      */       case 2:
/*      */         try
/*      */         {
/*  492 */           this.wrset.insertRow();
/*  493 */           this.wrset.moveToCurrentRow();
/*  494 */           this.wrset.next();
/*      */ 
/*  496 */           OracleRow localOracleRow2 = this.wrset.getCurrentRow();
/*  497 */           localOracleRow2.setInsertedFlag(false);
/*  498 */           localOracleRow2.setRowDeleted(true);
/*  499 */           applyUpdates();
/*      */         }
/*      */         catch (SQLException localSQLException6)
/*      */         {
/*  503 */           throw new SAXException("Error constructing deleted row: " + localSQLException6.getMessage());
/*      */         }
/*      */ 
/*      */       case 1:
/*      */         try
/*      */         {
/*  513 */           this.wrset.insertRow();
/*  514 */           this.wrset.moveToCurrentRow();
/*  515 */           this.wrset.next();
/*      */ 
/*  517 */           applyUpdates();
/*      */         }
/*      */         catch (SQLException localSQLException7)
/*      */         {
/*  521 */           throw new SAXException("Error constructing inserted row: " + localSQLException7.getMessage());
/*      */         }
/*      */ 
/*      */       case 3:
/*      */         try
/*      */         {
/*  531 */           this.wrset.insertRow();
/*  532 */           this.wrset.moveToCurrentRow();
/*  533 */           this.wrset.next();
/*      */ 
/*  535 */           OracleRow localOracleRow3 = this.wrset.getCurrentRow();
/*  536 */           localOracleRow3.setRowDeleted(true);
/*  537 */           applyUpdates();
/*      */         }
/*      */         catch (SQLException localSQLException8)
/*      */         {
/*  541 */           throw new SAXException("Error constructing modified row: " + localSQLException8.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
/*      */     throws SAXException
/*      */   {
/*  560 */     String str = (paramString2 == null) || (paramString2.equals("")) ? paramString3 : paramString2;
/*      */ 
/*  563 */     switch (getState())
/*      */     {
/*      */     case 1:
/*  566 */       int i = ((Integer)this.propertyNameTagMap.get(str)).intValue();
/*      */ 
/*  571 */       if (i == 18)
/*      */       {
/*  576 */         setNullValue(true);
/*  577 */         this.propertyValue = null;
/*      */       }
/*      */       else
/*      */       {
/*  584 */         setTag(i);
/*      */       }
/*      */ 
/*  587 */       break;
/*      */     case 2:
/*  590 */       int j = ((Integer)this.metadataNameTagMap.get(str)).intValue();
/*      */ 
/*  595 */       if (j == 19)
/*      */       {
/*  600 */         setNullValue(true);
/*  601 */         this.metadataValue = null;
/*      */       }
/*      */       else
/*      */       {
/*  608 */         setTag(j);
/*      */       }
/*      */ 
/*  611 */       break;
/*      */     case 3:
/*  614 */       int k = ((Integer)this.dataNameTagMap.get(str)).intValue();
/*      */ 
/*  617 */       if (k == 6)
/*      */       {
/*  619 */         setNullValue(true);
/*  620 */         this.columnValue = null;
/*      */       }
/*      */       else
/*      */       {
/*  624 */         setTag(k);
/*      */ 
/*  626 */         if ((k != 0) && (k != 1) && (k != 2) && (k != 3))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  634 */         this.columnIndex = 0;
/*      */         try
/*      */         {
/*  638 */           this.wrset.moveToInsertRow();
/*      */         }
/*      */         catch (SQLException localSQLException)
/*      */         {
/*      */         }
/*      */       }
/*      */     default:
/*  645 */       setState(str);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void error(SAXParseException paramSAXParseException)
/*      */     throws SAXParseException
/*      */   {
/*  655 */     throw paramSAXParseException;
/*      */   }
/*      */ 
/*      */   public void warning(SAXParseException paramSAXParseException)
/*      */     throws SAXParseException
/*      */   {
/*  662 */     System.out.println("** Warning, line " + paramSAXParseException.getLineNumber() + ", uri " + paramSAXParseException.getSystemId());
/*      */ 
/*  664 */     System.out.println("   " + paramSAXParseException.getMessage());
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/*  676 */     this.propertyNameTagMap = new Hashtable(30);
/*  677 */     int i = this.propertyNames.length;
/*  678 */     for (int j = 0; j < i; j++) {
/*  679 */       this.propertyNameTagMap.put(this.propertyNames[j], new Integer(j));
/*      */     }
/*  681 */     this.metadataNameTagMap = new Hashtable(30);
/*  682 */     i = this.metadataNames.length;
/*  683 */     for (j = 0; j < i; j++) {
/*  684 */       this.metadataNameTagMap.put(this.metadataNames[j], new Integer(j));
/*      */     }
/*  686 */     this.dataNameTagMap = new Hashtable(10);
/*  687 */     i = this.dataNames.length;
/*  688 */     for (j = 0; j < i; j++) {
/*  689 */       this.dataNameTagMap.put(this.dataNames[j], new Integer(j));
/*      */     }
/*  691 */     this.updatesToRowSet = new Vector();
/*  692 */     this.columnValue = new String("");
/*  693 */     this.propertyValue = new String("");
/*  694 */     this.metadataValue = new String("");
/*  695 */     this.isNullValue = false;
/*  696 */     this.columnIndex = 0;
/*      */ 
/*  698 */     this.readReadOnlyValue = false;
/*      */   }
/*      */ 
/*      */   protected void processElement(String paramString)
/*      */     throws SAXException
/*      */   {
/*      */     try
/*      */     {
/*  707 */       switch (getState())
/*      */       {
/*      */       case 1:
/*  710 */         this.propertyValue = paramString;
/*  711 */         break;
/*      */       case 2:
/*  714 */         this.metadataValue = paramString;
/*  715 */         break;
/*      */       case 3:
/*  718 */         setDataValue(paramString);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException localSQLException)
/*      */     {
/*  725 */       throw new SAXException("processElement: " + localSQLException.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private BigDecimal getBigDecimalValue(String paramString)
/*      */   {
/*  731 */     return new BigDecimal(paramString);
/*      */   }
/*      */ 
/*      */   private byte[] getBinaryValue(String paramString)
/*      */   {
/*  736 */     return paramString.getBytes();
/*      */   }
/*      */ 
/*      */   private boolean getBooleanValue(String paramString)
/*      */   {
/*  741 */     return new Boolean(paramString).booleanValue();
/*      */   }
/*      */ 
/*      */   private byte getByteValue(String paramString)
/*      */   {
/*  746 */     return Byte.parseByte(paramString);
/*      */   }
/*      */ 
/*      */   private Date getDateValue(String paramString)
/*      */   {
/*  751 */     return new Date(getLongValue(paramString));
/*      */   }
/*      */ 
/*      */   private double getDoubleValue(String paramString)
/*      */   {
/*  756 */     return Double.parseDouble(paramString);
/*      */   }
/*      */ 
/*      */   private float getFloatValue(String paramString)
/*      */   {
/*  761 */     return Float.parseFloat(paramString);
/*      */   }
/*      */ 
/*      */   private int getIntegerValue(String paramString)
/*      */   {
/*  766 */     return Integer.parseInt(paramString);
/*      */   }
/*      */ 
/*      */   private long getLongValue(String paramString)
/*      */   {
/*  771 */     return Long.parseLong(paramString);
/*      */   }
/*      */ 
/*      */   private boolean getNullValue()
/*      */   {
/*  776 */     return this.isNullValue;
/*      */   }
/*      */ 
/*      */   private short getShortValue(String paramString)
/*      */   {
/*  781 */     return Short.parseShort(paramString);
/*      */   }
/*      */ 
/*      */   private String getStringValue(String paramString)
/*      */   {
/*  786 */     return paramString;
/*      */   }
/*      */ 
/*      */   private Time getTimeValue(String paramString)
/*      */   {
/*  791 */     return new Time(getLongValue(paramString));
/*      */   }
/*      */ 
/*      */   private Timestamp getTimestampValue(String paramString)
/*      */   {
/*  796 */     return new Timestamp(getLongValue(paramString));
/*      */   }
/*      */ 
/*      */   private Blob getBlobValue(String paramString)
/*      */     throws SQLException
/*      */   {
/*  802 */     return new OracleSerialBlob(paramString.getBytes());
/*      */   }
/*      */ 
/*      */   private Clob getClobValue(String paramString)
/*      */     throws SQLException
/*      */   {
/*  808 */     return new OracleSerialClob(paramString.toCharArray());
/*      */   }
/*      */ 
/*      */   private void applyUpdates()
/*      */     throws SAXException
/*      */   {
/*  814 */     if (this.updatesToRowSet.size() > 0)
/*      */     {
/*      */       try
/*      */       {
/*  819 */         Iterator localIterator = this.updatesToRowSet.iterator();
/*  820 */         while (localIterator.hasNext())
/*      */         {
/*  822 */           Object[] arrayOfObject = (Object[])localIterator.next();
/*  823 */           this.columnIndex = ((Integer)arrayOfObject[0]).intValue();
/*  824 */           insertValue((String)arrayOfObject[1]);
/*      */         }
/*      */ 
/*  827 */         this.wrset.updateRow();
/*      */       }
/*      */       catch (SQLException localSQLException)
/*      */       {
/*  831 */         throw new SAXException("Error updating row: " + localSQLException.getMessage());
/*      */       }
/*      */ 
/*  835 */       this.updatesToRowSet.removeAllElements();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertValue(String paramString)
/*      */     throws SQLException
/*      */   {
/*  842 */     if ((getNullValue()) || (paramString == null))
/*      */     {
/*  844 */       this.wrset.updateNull(this.columnIndex);
/*  845 */       return;
/*      */     }
/*      */ 
/*  848 */     int i = this.wrset.getMetaData().getColumnType(this.columnIndex);
/*      */ 
/*  850 */     switch (i)
/*      */     {
/*      */     case -7:
/*  853 */       this.wrset.updateByte(this.columnIndex, getByteValue(paramString));
/*  854 */       break;
/*      */     case 5:
/*  857 */       this.wrset.updateShort(this.columnIndex, getShortValue(paramString));
/*  858 */       break;
/*      */     case 4:
/*  861 */       this.wrset.updateInt(this.columnIndex, getIntegerValue(paramString));
/*  862 */       break;
/*      */     case -5:
/*  865 */       this.wrset.updateLong(this.columnIndex, getLongValue(paramString));
/*  866 */       break;
/*      */     case 6:
/*      */     case 7:
/*  870 */       this.wrset.updateFloat(this.columnIndex, getFloatValue(paramString));
/*  871 */       break;
/*      */     case 8:
/*  874 */       this.wrset.updateDouble(this.columnIndex, getDoubleValue(paramString));
/*  875 */       break;
/*      */     case 2:
/*      */     case 3:
/*  879 */       this.wrset.updateObject(this.columnIndex, getBigDecimalValue(paramString));
/*  880 */       break;
/*      */     case -4:
/*      */     case -3:
/*      */     case -2:
/*  885 */       this.wrset.updateBytes(this.columnIndex, getBinaryValue(paramString));
/*  886 */       break;
/*      */     case 91:
/*  889 */       this.wrset.updateDate(this.columnIndex, getDateValue(paramString));
/*  890 */       break;
/*      */     case 92:
/*  893 */       this.wrset.updateTime(this.columnIndex, getTimeValue(paramString));
/*  894 */       break;
/*      */     case 93:
/*  897 */       this.wrset.updateTimestamp(this.columnIndex, getTimestampValue(paramString));
/*  898 */       break;
/*      */     case -1:
/*      */     case 1:
/*      */     case 12:
/*  903 */       this.wrset.updateString(this.columnIndex, getStringValue(paramString));
/*  904 */       break;
/*      */     case 2004:
/*  907 */       this.wrset.updateBlob(this.columnIndex, getBlobValue(paramString));
/*  908 */       break;
/*      */     case 2005:
/*  911 */       this.wrset.updateClob(this.columnIndex, getClobValue(paramString));
/*  912 */       break;
/*      */     default:
/*  915 */       throw new SQLException("The type " + i + " is not supported currently.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setPropertyValue(String paramString)
/*      */     throws SQLException
/*      */   {
/*  922 */     boolean bool = getNullValue();
/*      */ 
/*  924 */     switch (getTag()) { case 7:
/*      */     case 8:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 21:
/*      */     default:
/*  931 */       break;
/*      */     case 0:
/*  934 */       if (bool)
/*      */       {
/*  939 */         this.wrset.setCommand(null);
/*      */       }
/*      */       else
/*      */       {
/*  946 */         this.wrset.setCommand(paramString);
/*      */       }
/*      */ 
/*  949 */       break;
/*      */     case 1:
/*  952 */       if (bool)
/*      */       {
/*  957 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/*  963 */       this.wrset.setConcurrency(getIntegerValue(paramString));
/*  964 */       break;
/*      */     case 2:
/*  967 */       if (bool)
/*      */       {
/*  972 */         this.wrset.setDataSourceName(null);
/*      */       }
/*      */       else
/*      */       {
/*  979 */         this.wrset.setDataSourceName(paramString);
/*      */       }
/*      */ 
/*  982 */       break;
/*      */     case 3:
/*  985 */       if (bool)
/*      */       {
/*  990 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/*  996 */       this.wrset.setEscapeProcessing(getBooleanValue(paramString));
/*  997 */       break;
/*      */     case 4:
/* 1000 */       if (bool)
/*      */       {
/* 1005 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1011 */       int i = this.wrset.getType();
/* 1012 */       if (i == 1005)
/*      */         break;
/* 1014 */       this.wrset.setFetchDirection(getIntegerValue(paramString)); break;
/*      */     case 5:
/* 1020 */       if (bool)
/*      */       {
/* 1025 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1031 */       this.wrset.setFetchSize(getIntegerValue(paramString));
/* 1032 */       break;
/*      */     case 6:
/* 1035 */       if (bool)
/*      */       {
/* 1040 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1046 */       this.wrset.setTransactionIsolation(getIntegerValue(paramString));
/* 1047 */       break;
/*      */     case 19:
/* 1050 */       if (this.keyCols == null)
/*      */       {
/* 1052 */         this.keyCols = new Vector();
/*      */       }
/*      */ 
/* 1055 */       this.keyCols.add(paramString);
/* 1056 */       break;
/*      */     case 9:
/* 1059 */       if (bool)
/*      */       {
/* 1064 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1070 */       this.wrset.setMaxFieldSize(getIntegerValue(paramString));
/* 1071 */       break;
/*      */     case 10:
/* 1074 */       if (bool)
/*      */       {
/* 1079 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1085 */       this.wrset.setMaxRows(getIntegerValue(paramString));
/* 1086 */       break;
/*      */     case 11:
/* 1089 */       if (bool)
/*      */       {
/* 1094 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1100 */       this.wrset.setQueryTimeout(getIntegerValue(paramString));
/* 1101 */       break;
/*      */     case 12:
/* 1104 */       if (bool)
/*      */       {
/* 1109 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1116 */       this.readReadOnlyValue = getBooleanValue(paramString);
/*      */ 
/* 1118 */       break;
/*      */     case 13:
/* 1121 */       if (bool)
/*      */       {
/* 1126 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1132 */       this.wrset.setType(getIntegerValue(paramString));
/*      */ 
/* 1134 */       break;
/*      */     case 14:
/* 1137 */       if (bool)
/*      */       {
/* 1142 */         throw new SQLException("Bad value; non-nullable property");
/*      */       }
/*      */ 
/* 1148 */       this.wrset.setShowDeleted(getBooleanValue(paramString));
/* 1149 */       break;
/*      */     case 15:
/* 1152 */       if (bool)
/*      */       {
/* 1157 */         this.wrset.setTableName(null);
/*      */       }
/*      */       else
/*      */       {
/* 1164 */         this.wrset.setTableName(paramString);
/*      */       }
/*      */ 
/* 1167 */       break;
/*      */     case 16:
/* 1170 */       if (bool)
/*      */       {
/* 1175 */         this.wrset.setUrl(null);
/*      */       }
/*      */       else
/*      */       {
/* 1182 */         this.wrset.setUrl(paramString);
/*      */       }
/*      */ 
/* 1185 */       break;
/*      */     case 22:
/* 1192 */       if (bool)
/*      */       {
/* 1197 */         this.wrset.setSyncProvider(null);
/*      */       }
/*      */       else
/*      */       {
/* 1204 */         this.wrset.setSyncProvider(paramString);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setMetaDataValue(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1217 */     boolean bool = getNullValue();
/*      */ 
/* 1219 */     switch (getTag())
/*      */     {
/*      */     case 1:
/*      */     default:
/* 1223 */       break;
/*      */     case 0:
/* 1226 */       if (bool)
/*      */       {
/* 1231 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1237 */       int i = getIntegerValue(paramString);
/*      */ 
/* 1240 */       this.rsetMetaData = new OracleRowSetMetaData(i);
/*      */ 
/* 1243 */       this.columnIndex = 0;
/*      */ 
/* 1245 */       break;
/*      */     case 2:
/* 1248 */       this.columnIndex += 1;
/* 1249 */       break;
/*      */     case 3:
/* 1252 */       if (bool)
/*      */       {
/* 1257 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1263 */       this.rsetMetaData.setAutoIncrement(this.columnIndex, getBooleanValue(paramString));
/* 1264 */       break;
/*      */     case 4:
/* 1267 */       if (bool)
/*      */       {
/* 1272 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1278 */       this.rsetMetaData.setCaseSensitive(this.columnIndex, getBooleanValue(paramString));
/* 1279 */       break;
/*      */     case 5:
/* 1282 */       if (bool)
/*      */       {
/* 1287 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1293 */       this.rsetMetaData.setCurrency(this.columnIndex, getBooleanValue(paramString));
/* 1294 */       break;
/*      */     case 6:
/* 1297 */       if (bool)
/*      */       {
/* 1302 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1308 */       this.rsetMetaData.setNullable(this.columnIndex, getIntegerValue(paramString));
/* 1309 */       break;
/*      */     case 7:
/* 1312 */       if (bool)
/*      */       {
/* 1317 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1323 */       this.rsetMetaData.setSigned(this.columnIndex, getBooleanValue(paramString));
/* 1324 */       break;
/*      */     case 8:
/* 1327 */       if (bool)
/*      */       {
/* 1332 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1338 */       this.rsetMetaData.setSearchable(this.columnIndex, getBooleanValue(paramString));
/* 1339 */       break;
/*      */     case 9:
/* 1342 */       if (bool)
/*      */       {
/* 1347 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1353 */       this.rsetMetaData.setColumnDisplaySize(this.columnIndex, getIntegerValue(paramString));
/* 1354 */       break;
/*      */     case 10:
/* 1357 */       if (bool)
/*      */       {
/* 1362 */         this.rsetMetaData.setColumnLabel(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1369 */         this.rsetMetaData.setColumnLabel(this.columnIndex, paramString);
/*      */       }
/*      */ 
/* 1372 */       break;
/*      */     case 11:
/* 1375 */       if (bool)
/*      */       {
/* 1380 */         this.rsetMetaData.setColumnName(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1387 */         this.rsetMetaData.setColumnName(this.columnIndex, paramString);
/*      */       }
/*      */ 
/* 1390 */       break;
/*      */     case 12:
/* 1393 */       if (bool)
/*      */       {
/* 1398 */         this.rsetMetaData.setSchemaName(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1405 */         this.rsetMetaData.setSchemaName(this.columnIndex, paramString);
/*      */       }
/*      */ 
/* 1408 */       break;
/*      */     case 13:
/* 1411 */       if (bool)
/*      */       {
/* 1416 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1422 */       this.rsetMetaData.setPrecision(this.columnIndex, getIntegerValue(paramString));
/* 1423 */       break;
/*      */     case 14:
/* 1426 */       if (bool)
/*      */       {
/* 1431 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1437 */       this.rsetMetaData.setScale(this.columnIndex, getIntegerValue(paramString));
/* 1438 */       break;
/*      */     case 15:
/* 1441 */       if (bool)
/*      */       {
/* 1446 */         this.rsetMetaData.setTableName(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1453 */         this.rsetMetaData.setTableName(this.columnIndex, paramString);
/*      */       }
/*      */ 
/* 1456 */       break;
/*      */     case 16:
/* 1459 */       if (bool)
/*      */       {
/* 1464 */         this.rsetMetaData.setCatalogName(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1471 */         this.rsetMetaData.setCatalogName(this.columnIndex, paramString);
/*      */       }
/*      */ 
/* 1474 */       break;
/*      */     case 17:
/* 1477 */       if (bool)
/*      */       {
/* 1482 */         throw new SQLException("Bad value; non-nullable metadata");
/*      */       }
/*      */ 
/* 1488 */       this.rsetMetaData.setColumnType(this.columnIndex, getIntegerValue(paramString));
/* 1489 */       break;
/*      */     case 18:
/* 1492 */       if (bool)
/*      */       {
/* 1497 */         this.rsetMetaData.setColumnTypeName(this.columnIndex, null);
/*      */       }
/*      */       else
/*      */       {
/* 1504 */         this.rsetMetaData.setColumnTypeName(this.columnIndex, paramString);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setDataValue(String paramString)
/*      */     throws SQLException
/*      */   {
/* 1515 */     switch (getTag())
/*      */     {
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     default:
/* 1521 */       break;
/*      */     case 4:
/* 1524 */       this.columnValue = paramString;
/* 1525 */       break;
/*      */     case 5:
/* 1528 */       Object[] arrayOfObject = new Object[2];
/* 1529 */       arrayOfObject[1] = paramString;
/* 1530 */       arrayOfObject[0] = new Integer(this.columnIndex);
/* 1531 */       this.updatesToRowSet.add(arrayOfObject);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void setNullValue(boolean paramBoolean)
/*      */   {
/* 1539 */     this.isNullValue = paramBoolean;
/*      */   }
/*      */ 
/*      */   private int getState()
/*      */   {
/* 1544 */     return this.state;
/*      */   }
/*      */ 
/*      */   private int getTag()
/*      */   {
/* 1549 */     return this.tag;
/*      */   }
/*      */ 
/*      */   private void setState(String paramString)
/*      */     throws SAXException
/*      */   {
/* 1555 */     if (paramString.equals("webRowSet")) {
/* 1556 */       this.state = 0;
/*      */     }
/* 1558 */     else if (paramString.equals("properties"))
/*      */     {
/* 1560 */       if (this.state != 1)
/* 1561 */         this.state = 1;
/*      */       else {
/* 1563 */         this.state = 0;
/*      */       }
/*      */     }
/* 1566 */     else if (paramString.equals("metadata"))
/*      */     {
/* 1568 */       if (this.state != 2)
/* 1569 */         this.state = 2;
/*      */       else {
/* 1571 */         this.state = 0;
/*      */       }
/*      */     }
/* 1574 */     else if (paramString.equals("data"))
/* 1575 */       if (this.state != 3)
/* 1576 */         this.state = 3;
/*      */       else
/* 1578 */         this.state = 0;
/*      */   }
/*      */ 
/*      */   private void setTag(int paramInt)
/*      */   {
/* 1583 */     this.tag = paramInt;
/*      */   }
/*      */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleWebRowSetXmlReaderContHandler
 * JD-Core Version:    0.6.0
 */