/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.FactoryConfigurationError;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ class OracleWebRowSetXmlReaderImpl
/*     */   implements OracleWebRowSetXmlReader
/*     */ {
/*     */   private static final String JAVA_SAXPARSER_PROPERTY = "javax.xml.parsers.SAXParserFactory";
/*     */   private static final String JAVA_DOMPARSER_PROPERTY = "javax.xml.parsers.DocumentBuilderFactory";
/*     */   private static final String ORACLE_JAXP_SAXPARSER_FACTORY = "oracle.xml.jaxp.JXSAXParserFactory";
/*     */   private static final String ORACLE_JAXP_DOMPARSER_FACTORY = "oracle.xml.jaxp.JXDocumentBuilderFactory";
/*     */   private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
/*     */   private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*     */   private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
/*     */   private static final String WEBROWSET_SCHEMA = "http://java.sun.com/xml/ns/jdbc/webrowset.xsd";
/*     */   private Document document;
/*     */   private String parserStr;
/*     */ 
/*     */   OracleWebRowSetXmlReaderImpl()
/*     */   {
/*  89 */     this.document = null;
/*  90 */     this.parserStr = null;
/*     */   }
/*     */ 
/*     */   public void readXML(WebRowSet paramWebRowSet, Reader paramReader)
/*     */     throws SQLException
/*     */   {
/* 125 */     this.parserStr = getSystemProperty("javax.xml.parsers.SAXParserFactory");
/* 126 */     if (this.parserStr != null)
/*     */     {
/* 128 */       readXMLSax((OracleWebRowSet)paramWebRowSet, paramReader);
/*     */     }
/*     */     else
/*     */     {
/* 132 */       this.parserStr = getSystemProperty("javax.xml.parsers.DocumentBuilderFactory");
/* 133 */       if (this.parserStr != null)
/*     */       {
/* 135 */         readXMLDom((OracleWebRowSet)paramWebRowSet, paramReader);
/*     */       }
/*     */       else
/* 138 */         throw new SQLException("No valid JAXP parser property specified");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void readData(RowSetInternal paramRowSetInternal)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   private void readXMLSax(OracleWebRowSet paramOracleWebRowSet, Reader paramReader)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 165 */       InputSource localInputSource = new InputSource(paramReader);
/* 166 */       OracleWebRowSetXmlReaderContHandler localOracleWebRowSetXmlReaderContHandler = new OracleWebRowSetXmlReaderContHandler(paramOracleWebRowSet);
/*     */ 
/* 169 */       SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
/*     */ 
/* 172 */       localSAXParserFactory.setNamespaceAware(true);
/* 173 */       localSAXParserFactory.setValidating(true);
/* 174 */       SAXParser localSAXParser = localSAXParserFactory.newSAXParser();
/*     */ 
/* 177 */       localSAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/* 178 */       localSAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/ns/jdbc/webrowset.xsd");
/*     */ 
/* 180 */       XMLReader localXMLReader = localSAXParser.getXMLReader();
/* 181 */       localXMLReader.setContentHandler(localOracleWebRowSetXmlReaderContHandler);
/*     */ 
/* 195 */       localXMLReader.parse(localInputSource);
/*     */     }
/*     */     catch (SAXParseException localSAXParseException)
/*     */     {
/* 199 */       System.out.println("** Parsing error, line " + localSAXParseException.getLineNumber() + ", uri " + localSAXParseException.getSystemId());
/*     */ 
/* 202 */       System.out.println("   " + localSAXParseException.getMessage());
/* 203 */       localSAXParseException.printStackTrace();
/* 204 */       throw new SQLException(localSAXParseException.getMessage());
/*     */     }
/*     */     catch (SAXNotRecognizedException localSAXNotRecognizedException)
/*     */     {
/* 211 */       localSAXNotRecognizedException.printStackTrace();
/* 212 */       throw new SQLException("readXMLSax: SAXNotRecognizedException: " + localSAXNotRecognizedException.getMessage());
/*     */     }
/*     */     catch (SAXException localSAXException)
/*     */     {
/* 216 */       localSAXException.printStackTrace();
/* 217 */       throw new SQLException("readXMLSax: SAXException: " + localSAXException.getMessage());
/*     */     }
/*     */     catch (FactoryConfigurationError localFactoryConfigurationError)
/*     */     {
/* 221 */       localFactoryConfigurationError.printStackTrace();
/* 222 */       throw new SQLException("readXMLSax: Parser factory config: " + localFactoryConfigurationError.getMessage());
/*     */     }
/*     */     catch (ParserConfigurationException localParserConfigurationException)
/*     */     {
/* 226 */       localParserConfigurationException.printStackTrace();
/* 227 */       throw new SQLException("readXMLSax: Parser config: " + localParserConfigurationException.getMessage());
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 231 */       localException.printStackTrace();
/* 232 */       throw new SQLException("readXMLSax: " + localException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readXMLDom(OracleWebRowSet paramOracleWebRowSet, Reader paramReader)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 244 */       InputSource localInputSource = new InputSource(paramReader);
/* 245 */       OracleWebRowSetXmlReaderDomHandler localOracleWebRowSetXmlReaderDomHandler = new OracleWebRowSetXmlReaderDomHandler(paramOracleWebRowSet);
/*     */ 
/* 265 */       DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 269 */       localDocumentBuilderFactory.setNamespaceAware(true);
/* 270 */       localDocumentBuilderFactory.setValidating(true);
/*     */ 
/* 273 */       localDocumentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/* 274 */       localDocumentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/ns/jdbc/webrowset.xsd");
/* 275 */       DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
/*     */ 
/* 277 */       this.document = localDocumentBuilder.parse(localInputSource);
/*     */ 
/* 280 */       localOracleWebRowSetXmlReaderDomHandler.readXMLDocument(this.document);
/*     */     }
/*     */     catch (SAXException localSAXException)
/*     */     {
/* 284 */       localSAXException.printStackTrace();
/* 285 */       throw new SQLException("readXMLDom: SAXException: " + localSAXException.getMessage());
/*     */     }
/*     */     catch (FactoryConfigurationError localFactoryConfigurationError)
/*     */     {
/* 292 */       localFactoryConfigurationError.printStackTrace();
/* 293 */       throw new SQLException("readXMLDom: Parser factory config: " + localFactoryConfigurationError.getMessage());
/*     */     }
/*     */     catch (ParserConfigurationException localParserConfigurationException)
/*     */     {
/* 297 */       localParserConfigurationException.printStackTrace();
/* 298 */       throw new SQLException("readXMLDom: Parser config: " + localParserConfigurationException.getMessage());
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 302 */       localException.printStackTrace();
/* 303 */       throw new SQLException("readXMLDom: " + localException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getSystemProperty(String paramString)
/*     */   {
/* 312 */     String str = null;
/*     */     try
/*     */     {
/* 315 */       str = System.getProperty(paramString);
/*     */     }
/*     */     catch (SecurityException localSecurityException)
/*     */     {
/* 319 */       str = null;
/*     */     }
/*     */ 
/* 322 */     return str;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleWebRowSetXmlReaderImpl
 * JD-Core Version:    0.6.0
 */