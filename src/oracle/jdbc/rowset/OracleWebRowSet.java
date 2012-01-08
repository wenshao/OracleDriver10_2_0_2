/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ 
/*     */ public class OracleWebRowSet extends OracleCachedRowSet
/*     */   implements WebRowSet
/*     */ {
/*     */   private transient OracleWebRowSetXmlReader xmlReader;
/*     */   private transient OracleWebRowSetXmlWriter xmlWriter;
/*     */ 
/*     */   public OracleWebRowSet()
/*     */     throws SQLException
/*     */   {
/*  64 */     this.xmlReader = new OracleWebRowSetXmlReaderImpl();
/*  65 */     this.xmlWriter = new OracleWebRowSetXmlWriterImpl();
/*     */ 
/*  68 */     setReadOnly(false);
/*     */   }
/*     */ 
/*     */   public void readXml(Reader paramReader)
/*     */     throws SQLException
/*     */   {
/*  81 */     if (this.xmlReader != null)
/*     */     {
/*  83 */       this.xmlReader.readXML(this, paramReader);
/*     */     }
/*     */     else
/*     */     {
/*  87 */       throw new SQLException("Invalid reader");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeXml(Writer paramWriter)
/*     */     throws SQLException
/*     */   {
/* 103 */     if (this.xmlWriter != null)
/*     */     {
/* 105 */       this.xmlWriter.writeXML(this, paramWriter);
/*     */     }
/*     */     else
/*     */     {
/* 109 */       throw new SQLException("Invalid writer");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeXml(ResultSet paramResultSet, Writer paramWriter)
/*     */     throws SQLException
/*     */   {
/* 124 */     populate(paramResultSet);
/* 125 */     writeXml(paramWriter);
/*     */   }
/*     */ 
/*     */   public void readXml(InputStream paramInputStream)
/*     */     throws SQLException
/*     */   {
/* 138 */     readXml(new InputStreamReader(paramInputStream));
/*     */   }
/*     */ 
/*     */   public void writeXml(OutputStream paramOutputStream)
/*     */     throws SQLException
/*     */   {
/* 153 */     writeXml(new OutputStreamWriter(paramOutputStream));
/*     */   }
/*     */ 
/*     */   public void writeXml(ResultSet paramResultSet, OutputStream paramOutputStream)
/*     */     throws SQLException
/*     */   {
/* 167 */     writeXml(paramResultSet, new OutputStreamWriter(paramOutputStream));
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleWebRowSet
 * JD-Core Version:    0.6.0
 */