/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ import javax.sql.rowset.spi.SyncProvider;
/*     */ 
/*     */ class OracleWebRowSetXmlWriterImpl
/*     */   implements OracleWebRowSetXmlWriter
/*     */ {
/*     */   private Writer xmlWriter;
/*     */   private Stack xmlTagStack;
/*     */   private static final String WEBROWSET_ELEMENT = "webRowSet";
/*     */   private static final String PROPERTIES_ELEMENT = "properties";
/*     */   private static final String METADATA_ELEMENT = "metadata";
/*     */   private static final String DATA_ELEMENT = "data";
/*     */   private static final String PROPERTY_COMMAND = "command";
/*     */   private static final String PROPERTY_CONCURRENCY = "concurrency";
/*     */   private static final String PROPERTY_DATASOURCE = "datasource";
/*     */   private static final String PROPERTY_ESCAPEPROCESSING = "escape-processing";
/*     */   private static final String PROPERTY_FETCHDIRECTION = "fetch-direction";
/*     */   private static final String PROPERTY_FETCHSIZE = "fetch-size";
/*     */   private static final String PROPERTY_ISOLATIONLEVEL = "isolation-level";
/*     */   private static final String PROPERTY_KEYCOLUMNS = "key-columns";
/*     */   private static final String PROPERTY_MAP = "map";
/*     */   private static final String PROPERTY_MAXFIELDSIZE = "max-field-size";
/*     */   private static final String PROPERTY_MAXROWS = "max-rows";
/*     */   private static final String PROPERTY_QUERYTIMEOUT = "query-timeout";
/*     */   private static final String PROPERTY_READONLY = "read-only";
/*     */   private static final String PROPERTY_ROWSETTYPE = "rowset-type";
/*     */   private static final String PROPERTY_SHOWDELETED = "show-deleted";
/*     */   private static final String PROPERTY_TABLENAME = "table-name";
/*     */   private static final String PROPERTY_URL = "url";
/*     */   private static final String PROPERTY_SYNCPROVIDER = "sync-provider";
/*     */   private static final String PROPERTY_NULL = "null";
/*     */   private static final String PROPERTY_KC_COLUMN = "column";
/*     */   private static final String PROPERTY_MAP_TYPE = "type";
/*     */   private static final String PROPERTY_MAP_CLASS = "class";
/*     */   private static final String PROPERTY_S_PROVIDERNAME = "sync-provider-name";
/*     */   private static final String PROPERTY_S_PROVIDERVENDOR = "sync-provider-vendor";
/*     */   private static final String PROPERTY_S_PROVIDERVERSION = "sync-provider-version";
/*     */   private static final String PROPERTY_S_PROVIDERGRADE = "sync-provider-grade";
/*     */   private static final String PROPERTY_S_DATASOURCELOCK = "data-source-lock";
/*     */   private static final String METADATA_COLUMNCOUNT = "column-count";
/*     */   private static final String METADATA_COLUMNDEFINITION = "column-definition";
/*     */   private static final String METADATA_COLUMNINDEX = "column-index";
/*     */   private static final String METADATA_AUTOINCREMENT = "auto-increment";
/*     */   private static final String METADATA_CASESENSITIVE = "case-sensitive";
/*     */   private static final String METADATA_CURRENCY = "currency";
/*     */   private static final String METADATA_NULLABLE = "nullable";
/*     */   private static final String METADATA_SIGNED = "signed";
/*     */   private static final String METADATA_SEARCHABLE = "searchable";
/*     */   private static final String METADATA_COLUMNDISPLAYSIZE = "column-display-size";
/*     */   private static final String METADATA_COLUMNLABEL = "column-label";
/*     */   private static final String METADATA_COLUMNNAME = "column-name";
/*     */   private static final String METADATA_SCHEMANAME = "schema-name";
/*     */   private static final String METADATA_COLUMNPRECISION = "column-precision";
/*     */   private static final String METADATA_COLUMNSCALE = "column-scale";
/*     */   private static final String METADATA_TABLENAME = "table-name";
/*     */   private static final String METADATA_CATALOGNAME = "catalog-name";
/*     */   private static final String METADATA_COLUMNTYPE = "column-type";
/*     */   private static final String METADATA_COLUMNTYPENAME = "column-type-name";
/*     */   private static final String METADATA_NULL = "null";
/*     */   private static final String DATA_CURRENTROW = "currentRow";
/*     */   private static final String DATA_INSERTROW = "insertRow";
/*     */   private static final String DATA_DELETEROW = "deleteRow";
/*     */   private static final String DATA_MODIFYROW = "modifyRow";
/*     */   private static final String DATA_COLUMNVALUE = "columnValue";
/*     */   private static final String DATA_UPDATEVALUE = "updateValue";
/*     */   private static final String DATA_NULL = "null";
/*     */ 
/*     */   public void writeXML(WebRowSet paramWebRowSet, Writer paramWriter)
/*     */     throws SQLException
/*     */   {
/* 163 */     if (!(paramWebRowSet instanceof OracleWebRowSet)) {
/* 164 */       throw new SQLException("Invalid WebRowSet argument");
/*     */     }
/* 166 */     this.xmlTagStack = new Stack();
/* 167 */     this.xmlWriter = paramWriter;
/* 168 */     writeRowSet((OracleWebRowSet)paramWebRowSet);
/*     */   }
/*     */ 
/*     */   public boolean writeData(RowSetInternal paramRowSetInternal)
/*     */     throws SQLException
/*     */   {
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   private void writeRowSet(OracleWebRowSet paramOracleWebRowSet)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 195 */       writeHeaderAndStartWebRowSetElement();
/* 196 */       writeProperties(paramOracleWebRowSet);
/* 197 */       writeMetaData(paramOracleWebRowSet);
/* 198 */       writeData(paramOracleWebRowSet);
/* 199 */       endWebRowSetElement();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 203 */       throw new SQLException("IOException: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeHeaderAndStartWebRowSetElement()
/*     */     throws IOException
/*     */   {
/* 210 */     this.xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/* 211 */     this.xmlWriter.write("\n");
/*     */ 
/* 213 */     setCurrentTag("webRowSet");
/*     */ 
/* 215 */     this.xmlWriter.write("<webRowSet xmlns=\"http://java.sun.com/xml/ns/jdbc\"\n");
/* 216 */     this.xmlWriter.write("           xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
/* 217 */     this.xmlWriter.write("           xsi:schemaLocation=\"http://java.sun.com/xml/ns/jdbc ");
/* 218 */     this.xmlWriter.write("http://java.sun.com/xml/ns/jdbc/webrowset.xsd");
/* 219 */     this.xmlWriter.write("\">\n");
/*     */   }
/*     */ 
/*     */   private void endWebRowSetElement()
/*     */     throws IOException
/*     */   {
/* 225 */     endTag("webRowSet");
/*     */   }
/*     */ 
/*     */   private void startElement(String paramString)
/*     */     throws IOException
/*     */   {
/* 231 */     startTag(paramString);
/* 232 */     this.xmlWriter.write("\n");
/*     */   }
/*     */ 
/*     */   private void endElement(String paramString)
/*     */     throws IOException
/*     */   {
/* 238 */     writeIndent(this.xmlTagStack.size());
/* 239 */     endTag(paramString);
/*     */   }
/*     */ 
/*     */   private void endElement()
/*     */     throws IOException
/*     */   {
/* 245 */     writeIndent(this.xmlTagStack.size());
/* 246 */     String str = getCurrentTag();
/* 247 */     this.xmlWriter.write("</" + str + ">\n");
/* 248 */     this.xmlWriter.flush();
/*     */   }
/*     */ 
/*     */   private void startTag(String paramString)
/*     */     throws IOException
/*     */   {
/* 254 */     setCurrentTag(paramString);
/* 255 */     writeIndent(this.xmlTagStack.size());
/* 256 */     this.xmlWriter.write("<" + paramString + ">");
/*     */   }
/*     */ 
/*     */   private void endTag(String paramString)
/*     */     throws IOException
/*     */   {
/* 262 */     String str = getCurrentTag();
/* 263 */     if (paramString.equals(str))
/* 264 */       this.xmlWriter.write("</" + str + ">\n");
/* 265 */     this.xmlWriter.flush();
/*     */   }
/*     */ 
/*     */   private void setCurrentTag(String paramString)
/*     */   {
/* 270 */     this.xmlTagStack.push(paramString);
/*     */   }
/*     */ 
/*     */   private String getCurrentTag()
/*     */   {
/* 275 */     return (String)this.xmlTagStack.pop();
/*     */   }
/*     */ 
/*     */   private void writeEmptyElement(String paramString)
/*     */     throws IOException
/*     */   {
/* 281 */     this.xmlWriter.write("<" + paramString + "/>");
/*     */   }
/*     */ 
/*     */   private void writeProperties(OracleWebRowSet paramOracleWebRowSet)
/*     */     throws IOException
/*     */   {
/* 287 */     startElement("properties");
/*     */     try
/*     */     {
/* 291 */       writeElementString("command", paramOracleWebRowSet.getCommand());
/* 292 */       writeElementInteger("concurrency", paramOracleWebRowSet.getConcurrency());
/* 293 */       writeElementString("datasource", paramOracleWebRowSet.getDataSourceName());
/* 294 */       writeElementBoolean("escape-processing", paramOracleWebRowSet.getEscapeProcessing());
/* 295 */       writeElementInteger("fetch-direction", paramOracleWebRowSet.getFetchDirection());
/* 296 */       writeElementInteger("fetch-size", paramOracleWebRowSet.getFetchSize());
/* 297 */       writeElementInteger("isolation-level", paramOracleWebRowSet.getTransactionIsolation());
/*     */ 
/* 299 */       startElement("key-columns");
/* 300 */       int[] arrayOfInt = paramOracleWebRowSet.getKeyColumns();
/* 301 */       for (int i = 0; (arrayOfInt != null) && (i < arrayOfInt.length); i++)
/* 302 */         writeElementInteger("column", arrayOfInt[i]);
/* 303 */       endElement("key-columns");
/*     */ 
/* 305 */       startElement("map");
/* 306 */       Map localMap = paramOracleWebRowSet.getTypeMap();
/*     */       Iterator localIterator;
/* 307 */       if (localMap != null)
/*     */       {
/* 310 */         for (localIterator = localMap.keySet().iterator(); localIterator.hasNext(); )
/*     */         {
/* 312 */           String str = (String)localIterator.next();
/* 313 */           localObject = (Class)localMap.get(str);
/* 314 */           writeElementString("type", str);
/* 315 */           writeElementString("class", ((Class)localObject).getName());
/*     */         }
/*     */       }
/* 318 */       endElement("map");
/*     */ 
/* 320 */       writeElementInteger("max-field-size", paramOracleWebRowSet.getMaxFieldSize());
/* 321 */       writeElementInteger("max-rows", paramOracleWebRowSet.getMaxRows());
/* 322 */       writeElementInteger("query-timeout", paramOracleWebRowSet.getQueryTimeout());
/* 323 */       writeElementBoolean("read-only", paramOracleWebRowSet.isReadOnly());
/* 324 */       writeElementInteger("rowset-type", paramOracleWebRowSet.getType());
/* 325 */       writeElementBoolean("show-deleted", paramOracleWebRowSet.getShowDeleted());
/* 326 */       writeElementString("table-name", paramOracleWebRowSet.getTableName());
/* 327 */       writeElementString("url", paramOracleWebRowSet.getUrl());
/*     */ 
/* 331 */       startElement("sync-provider");
/*     */ 
/* 336 */       Object localObject = paramOracleWebRowSet.getSyncProvider();
/* 337 */       writeElementString("sync-provider-name", ((SyncProvider)localObject).getProviderID());
/* 338 */       writeElementString("sync-provider-vendor", ((SyncProvider)localObject).getVendor());
/* 339 */       writeElementString("sync-provider-version", ((SyncProvider)localObject).getVersion());
/* 340 */       writeElementInteger("sync-provider-grade", ((SyncProvider)localObject).getProviderGrade());
/* 341 */       writeElementInteger("data-source-lock", ((SyncProvider)localObject).getDataSourceLock());
/*     */ 
/* 344 */       endElement("sync-provider");
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 348 */       throw new IOException("SQLException: " + localSQLException.getMessage());
/*     */     }
/*     */ 
/* 351 */     endElement("properties");
/*     */   }
/*     */ 
/*     */   private void writeMetaData(OracleWebRowSet paramOracleWebRowSet)
/*     */     throws IOException
/*     */   {
/* 357 */     startElement("metadata");
/*     */     try
/*     */     {
/* 361 */       ResultSetMetaData localResultSetMetaData = paramOracleWebRowSet.getMetaData();
/*     */ 
/* 363 */       int i = localResultSetMetaData.getColumnCount();
/* 364 */       writeElementInteger("column-count", i);
/*     */ 
/* 366 */       for (int j = 1; j <= i; j++)
/*     */       {
/* 368 */         startElement("column-definition");
/*     */ 
/* 370 */         writeElementInteger("column-index", j);
/* 371 */         writeElementBoolean("auto-increment", localResultSetMetaData.isAutoIncrement(j));
/* 372 */         writeElementBoolean("case-sensitive", localResultSetMetaData.isCaseSensitive(j));
/* 373 */         writeElementBoolean("currency", localResultSetMetaData.isCurrency(j));
/* 374 */         writeElementInteger("nullable", localResultSetMetaData.isNullable(j));
/* 375 */         writeElementBoolean("signed", localResultSetMetaData.isSigned(j));
/* 376 */         writeElementBoolean("searchable", localResultSetMetaData.isSearchable(j));
/* 377 */         writeElementInteger("column-display-size", localResultSetMetaData.getColumnDisplaySize(j));
/* 378 */         writeElementString("column-label", localResultSetMetaData.getColumnLabel(j));
/* 379 */         writeElementString("column-name", localResultSetMetaData.getColumnName(j));
/* 380 */         writeElementString("schema-name", localResultSetMetaData.getSchemaName(j));
/* 381 */         writeElementInteger("column-precision", localResultSetMetaData.getPrecision(j));
/* 382 */         writeElementInteger("column-scale", localResultSetMetaData.getScale(j));
/* 383 */         writeElementString("table-name", localResultSetMetaData.getTableName(j));
/* 384 */         writeElementString("catalog-name", localResultSetMetaData.getCatalogName(j));
/* 385 */         writeElementInteger("column-type", localResultSetMetaData.getColumnType(j));
/* 386 */         writeElementString("column-type-name", localResultSetMetaData.getColumnTypeName(j));
/*     */ 
/* 388 */         endElement("column-definition");
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 393 */       throw new IOException("SQLException: " + localSQLException.getMessage());
/*     */     }
/*     */ 
/* 396 */     endElement("metadata");
/*     */   }
/*     */ 
/*     */   private void writeElementBoolean(String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 402 */     startTag(paramString);
/* 403 */     writeBoolean(paramBoolean);
/* 404 */     endTag(paramString);
/*     */   }
/*     */ 
/*     */   private void writeElementInteger(String paramString, int paramInt)
/*     */     throws IOException
/*     */   {
/* 410 */     startTag(paramString);
/* 411 */     writeInteger(paramInt);
/* 412 */     endTag(paramString);
/*     */   }
/*     */ 
/*     */   private void writeElementString(String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/* 418 */     startTag(paramString1);
/* 419 */     writeString(paramString2);
/* 420 */     endTag(paramString1);
/*     */   }
/*     */ 
/*     */   private void writeData(OracleWebRowSet paramOracleWebRowSet)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 428 */       ResultSetMetaData localResultSetMetaData = paramOracleWebRowSet.getMetaData();
/* 429 */       int i = localResultSetMetaData.getColumnCount();
/*     */ 
/* 431 */       startElement("data");
/*     */ 
/* 433 */       paramOracleWebRowSet.beforeFirst();
/* 434 */       paramOracleWebRowSet.setShowDeleted(true);
/* 435 */       for (; paramOracleWebRowSet.next(); endElement())
/*     */       {
/* 437 */         if ((paramOracleWebRowSet.rowDeleted()) && (paramOracleWebRowSet.rowInserted()))
/* 438 */           startElement("modifyRow");
/* 439 */         else if (paramOracleWebRowSet.rowDeleted())
/* 440 */           startElement("deleteRow");
/* 441 */         else if (paramOracleWebRowSet.rowInserted())
/* 442 */           startElement("insertRow");
/*     */         else
/* 444 */           startElement("currentRow");
/* 445 */         for (int j = 1; j <= i; j++)
/*     */         {
/* 447 */           if (paramOracleWebRowSet.columnUpdated(j))
/*     */           {
/* 449 */             ResultSet localResultSet = paramOracleWebRowSet.getOriginalRow();
/* 450 */             localResultSet.next();
/*     */ 
/* 452 */             startTag("columnValue");
/* 453 */             writeValue(j, (RowSet)localResultSet);
/* 454 */             endTag("columnValue");
/*     */ 
/* 456 */             startTag("updateValue");
/* 457 */             writeValue(j, paramOracleWebRowSet);
/* 458 */             endTag("updateValue");
/*     */           }
/*     */           else
/*     */           {
/* 462 */             startTag("columnValue");
/* 463 */             writeValue(j, paramOracleWebRowSet);
/* 464 */             endTag("columnValue");
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 469 */       endElement("data");
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 473 */       throw new IOException("SQLException: " + localSQLException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeBigDecimal(BigDecimal paramBigDecimal)
/*     */     throws IOException
/*     */   {
/* 480 */     if (paramBigDecimal != null)
/* 481 */       this.xmlWriter.write(paramBigDecimal.toString());
/*     */     else
/* 483 */       writeEmptyElement("null");
/*     */   }
/*     */ 
/*     */   private void writeBoolean(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 489 */     this.xmlWriter.write(new Boolean(paramBoolean).toString());
/*     */   }
/*     */ 
/*     */   private void writeDouble(double paramDouble)
/*     */     throws IOException
/*     */   {
/* 495 */     this.xmlWriter.write(Double.toString(paramDouble));
/*     */   }
/*     */ 
/*     */   private void writeFloat(float paramFloat)
/*     */     throws IOException
/*     */   {
/* 501 */     this.xmlWriter.write(Float.toString(paramFloat));
/*     */   }
/*     */ 
/*     */   private void writeInteger(int paramInt)
/*     */     throws IOException
/*     */   {
/* 507 */     this.xmlWriter.write(Integer.toString(paramInt));
/*     */   }
/*     */ 
/*     */   private void writeLong(long paramLong)
/*     */     throws IOException
/*     */   {
/* 513 */     this.xmlWriter.write(Long.toString(paramLong));
/*     */   }
/*     */ 
/*     */   private void writeNull()
/*     */     throws IOException
/*     */   {
/* 519 */     writeEmptyElement("null");
/*     */   }
/*     */ 
/*     */   private void writeShort(short paramShort)
/*     */     throws IOException
/*     */   {
/* 525 */     this.xmlWriter.write(Short.toString(paramShort));
/*     */   }
/*     */ 
/*     */   private void writeBytes(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 531 */     this.xmlWriter.write(new String(paramArrayOfByte));
/*     */   }
/*     */ 
/*     */   private void writeString(String paramString)
/*     */     throws IOException
/*     */   {
/* 542 */     if (paramString != null)
/* 543 */       this.xmlWriter.write(paramString);
/*     */     else
/* 545 */       this.xmlWriter.write("");
/*     */   }
/*     */ 
/*     */   private void writeIndent(int paramInt)
/*     */     throws IOException
/*     */   {
/* 551 */     for (int i = 1; i < paramInt; i++)
/* 552 */       this.xmlWriter.write("  ");
/*     */   }
/*     */ 
/*     */   private void writeValue(int paramInt, RowSet paramRowSet)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 561 */       int i = paramRowSet.getMetaData().getColumnType(paramInt);
/* 562 */       switch (i)
/*     */       {
/*     */       case -7:
/*     */       case 5:
/* 566 */         short s = paramRowSet.getShort(paramInt);
/* 567 */         if (paramRowSet.wasNull())
/* 568 */           writeNull();
/*     */         else
/* 570 */           writeShort(s);
/* 571 */         break;
/*     */       case 4:
/* 574 */         int j = paramRowSet.getInt(paramInt);
/* 575 */         if (paramRowSet.wasNull())
/* 576 */           writeNull();
/*     */         else
/* 578 */           writeInteger(j);
/* 579 */         break;
/*     */       case -5:
/* 582 */         long l = paramRowSet.getLong(paramInt);
/* 583 */         if (paramRowSet.wasNull())
/* 584 */           writeNull();
/*     */         else
/* 586 */           writeLong(l);
/* 587 */         break;
/*     */       case 6:
/*     */       case 7:
/* 591 */         float f = paramRowSet.getFloat(paramInt);
/* 592 */         if (paramRowSet.wasNull())
/* 593 */           writeNull();
/*     */         else
/* 595 */           writeFloat(f);
/* 596 */         break;
/*     */       case 8:
/* 599 */         double d = paramRowSet.getDouble(paramInt);
/* 600 */         if (paramRowSet.wasNull())
/* 601 */           writeNull();
/*     */         else
/* 603 */           writeDouble(d);
/* 604 */         break;
/*     */       case 2:
/*     */       case 3:
/* 608 */         BigDecimal localBigDecimal = paramRowSet.getBigDecimal(paramInt);
/* 609 */         if (paramRowSet.wasNull())
/* 610 */           writeNull();
/*     */         else
/* 612 */           writeBigDecimal(localBigDecimal);
/* 613 */         break;
/*     */       case 91:
/* 616 */         Date localDate = paramRowSet.getDate(paramInt);
/* 617 */         if (paramRowSet.wasNull())
/* 618 */           writeNull();
/*     */         else
/* 620 */           writeLong(localDate.getTime());
/* 621 */         break;
/*     */       case 92:
/* 624 */         Time localTime = paramRowSet.getTime(paramInt);
/* 625 */         if (paramRowSet.wasNull())
/* 626 */           writeNull();
/*     */         else
/* 628 */           writeLong(localTime.getTime());
/* 629 */         break;
/*     */       case 93:
/* 632 */         Timestamp localTimestamp = paramRowSet.getTimestamp(paramInt);
/* 633 */         if (paramRowSet.wasNull())
/* 634 */           writeNull();
/*     */         else
/* 636 */           writeLong(localTimestamp.getTime());
/* 637 */         break;
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/*     */       case 2004:
/* 643 */         byte[] arrayOfByte = paramRowSet.getBytes(paramInt);
/* 644 */         if (paramRowSet.wasNull())
/* 645 */           writeNull();
/*     */         else
/* 647 */           writeBytes(arrayOfByte);
/* 648 */         break;
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/*     */       case 2005:
/* 654 */         String str = paramRowSet.getString(paramInt);
/* 655 */         if (paramRowSet.wasNull())
/* 656 */           writeNull();
/*     */         else
/* 658 */           writeString(str);
/* 659 */         break;
/*     */       default:
/* 662 */         throw new SQLException("The type " + i + " is not supported currently.");
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException)
/*     */     {
/* 667 */       throw new IOException("Failed to writeValue: " + localSQLException.getMessage());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleWebRowSetXmlWriterImpl
 * JD-Core Version:    0.6.0
 */