/*     */ package oracle.jdbc.rowset;
/*     */ 
/*     */ import javax.sql.RowSet;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ class OracleWebRowSetXmlReaderDomHandler extends OracleWebRowSetXmlReaderContHandler
/*     */ {
/*     */   OracleWebRowSetXmlReaderDomHandler(RowSet paramRowSet)
/*     */   {
/*  43 */     super(paramRowSet);
/*     */   }
/*     */ 
/*     */   void readXMLDocument(Document paramDocument)
/*     */     throws SAXException
/*     */   {
/*  51 */     Element localElement = paramDocument.getDocumentElement();
/*  52 */     startElement(null, null, "webRowSet", null);
/*     */ 
/*  55 */     Node localNode1 = localElement.getElementsByTagName("properties").item(0);
/*     */ 
/*  57 */     startElement(null, null, "properties", null);
/*     */ 
/*  60 */     NodeList localNodeList1 = localNode1.getChildNodes();
/*  61 */     int i = localNodeList1.getLength();
/*     */ 
/*  66 */     for (int j = 0; j < i; j++)
/*     */     {
/*  68 */       Node localNode2 = localNodeList1.item(j);
/*     */ 
/*  71 */       if ((localNode2 instanceof Text))
/*     */       {
/*     */         continue;
/*     */       }
/*  75 */       String str1 = localNode2.getNodeName();
/*  76 */       startElement(null, null, str1, null);
/*     */ 
/*  80 */       if (localNode2.hasChildNodes())
/*     */       {
/*  82 */         processElement(localNode2.getFirstChild().getNodeValue());
/*     */       }
/*     */       else
/*     */       {
/*  89 */         processElement("");
/*     */       }
/*     */ 
/*  92 */       endElement(null, null, str1);
/*     */     }
/*     */ 
/*  95 */     endElement(null, null, "properties");
/*     */ 
/*  98 */     Node localNode3 = localElement.getElementsByTagName("metadata").item(0);
/*     */ 
/* 100 */     startElement(null, null, "metadata", null);
/*     */ 
/* 103 */     Node localNode4 = localNode3.getFirstChild().getNextSibling();
/*     */ 
/* 105 */     String str2 = localNode4.getNodeName();
/* 106 */     startElement(null, null, str2, null);
/*     */ 
/* 109 */     processElement(localNode4.getFirstChild().getNodeValue());
/* 110 */     endElement(null, null, str2);
/*     */ 
/* 113 */     NodeList localNodeList2 = localNode3.getChildNodes();
/* 114 */     int k = localNodeList2.getLength();
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 117 */     for (int m = 3; m < k; m++)
/*     */     {
/* 119 */       localObject1 = localNodeList2.item(m);
/*     */ 
/* 122 */       NodeList localNodeList3 = ((Node)localObject1).getChildNodes();
/* 123 */       i1 = localNodeList3.getLength();
/*     */ 
/* 125 */       for (int i2 = 0; i2 < i1; i2++)
/*     */       {
/* 127 */         localObject2 = localNodeList3.item(i2);
/*     */ 
/* 130 */         if ((localObject2 instanceof Text))
/*     */         {
/*     */           continue;
/*     */         }
/* 134 */         localObject3 = ((Node)localObject2).getNodeName();
/* 135 */         startElement(null, null, (String)localObject3, null);
/*     */ 
/* 139 */         if (((Node)localObject2).hasChildNodes())
/*     */         {
/* 141 */           processElement(((Node)localObject2).getFirstChild().getNodeValue());
/*     */         }
/*     */         else
/*     */         {
/* 148 */           processElement("");
/*     */         }
/*     */ 
/* 151 */         endElement(null, null, (String)localObject3);
/*     */       }
/*     */     }
/*     */ 
/* 155 */     endElement(null, null, "metadata");
/*     */ 
/* 158 */     Node localNode5 = localElement.getElementsByTagName("data").item(0);
/* 159 */     startElement(null, null, "data", null);
/*     */ 
/* 162 */     Object localObject1 = localNode5.getChildNodes();
/* 163 */     int n = ((NodeList)localObject1).getLength();
/*     */ 
/* 165 */     for (int i1 = 0; i1 < n; i1++)
/*     */     {
/* 167 */       Node localNode6 = ((NodeList)localObject1).item(i1);
/*     */ 
/* 170 */       if ((localNode6 instanceof Text))
/*     */       {
/*     */         continue;
/*     */       }
/* 174 */       localObject2 = localNode6.getNodeName();
/* 175 */       startElement(null, null, (String)localObject2, null);
/*     */ 
/* 178 */       localObject3 = localNode6.getChildNodes();
/* 179 */       int i3 = ((NodeList)localObject3).getLength();
/*     */ 
/* 181 */       for (int i4 = 0; i4 < i3; i4++)
/*     */       {
/* 183 */         Node localNode7 = ((NodeList)localObject3).item(i4);
/*     */ 
/* 186 */         if ((localNode7 instanceof Text))
/*     */         {
/*     */           continue;
/*     */         }
/* 190 */         String str3 = localNode7.getNodeName();
/* 191 */         startElement(null, null, str3, null);
/*     */         String str4;
/* 196 */         if (localNode7.hasChildNodes())
/*     */         {
/* 198 */           str4 = localNode7.getFirstChild().getNodeValue();
/* 199 */           if (str4 == null)
/*     */           {
/* 201 */             startElement(null, null, "null", null);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 208 */           str4 = "";
/*     */         }
/*     */ 
/* 211 */         processElement(str4);
/*     */ 
/* 213 */         endElement(null, null, str3);
/*     */       }
/*     */ 
/* 218 */       endElement(null, null, (String)localObject2);
/*     */     }
/*     */ 
/* 222 */     endElement(null, null, "data");
/*     */ 
/* 224 */     endElement(null, null, "webRowSet");
/*     */ 
/* 226 */     endDocument();
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.jdbc.rowset.OracleWebRowSetXmlReaderDomHandler
 * JD-Core Version:    0.6.0
 */