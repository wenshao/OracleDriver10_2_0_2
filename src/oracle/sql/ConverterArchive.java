/*     */ package oracle.sql;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.net.URL;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class ConverterArchive
/*     */ {
/*     */   private String m_izipName;
/*  86 */   private FileOutputStream m_ifStream = null;
/*  87 */   private ZipOutputStream m_izStream = null;
/*  88 */   private InputStream m_riStream = null;
/*  89 */   private ZipFile m_rzipFile = null;
/*     */   private static final String TEMPFILE = "gsstemp.zip";
/* 471 */   private static final String _Copyright_2004_Oracle_All_Rights_Reserved_ = null;
/*     */   public static final boolean TRACE = false;
/*     */   public static final boolean PRIVATE_TRACE = false;
/*     */   public static final String BUILD_DATE = "Tue_Jan_24_08:54:25_PST_2006";
/*     */ 
/*     */   public void openArchiveforInsert(String paramString)
/*     */   {
/* 104 */     this.m_izipName = paramString;
/*     */     try
/*     */     {
/* 108 */       this.m_ifStream = new FileOutputStream(this.m_izipName);
/* 109 */       this.m_izStream = new ZipOutputStream(this.m_ifStream);
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException)
/*     */     {
/*     */     }
/*     */     catch (IOException localIOException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void closeArchiveforInsert() {
/*     */     try {
/* 120 */       this.m_izStream.close();
/* 121 */       this.m_ifStream.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertObj(Object paramObject, String paramString)
/*     */   {
/* 131 */     ZipEntry localZipEntry = null;
/* 132 */     ObjectOutputStream localObjectOutputStream = null;
/*     */ 
/* 134 */     localZipEntry = new ZipEntry(paramString);
/*     */     try
/*     */     {
/* 138 */       this.m_izStream.putNextEntry(localZipEntry);
/*     */ 
/* 140 */       localObjectOutputStream = new ObjectOutputStream(this.m_izStream);
/*     */ 
/* 142 */       localObjectOutputStream.writeObject(paramObject);
/* 143 */       localObjectOutputStream.close();
/* 144 */       this.m_izStream.closeEntry();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertSingleObj(String paramString1, Object paramObject, String paramString2)
/*     */     throws IOException
/*     */   {
/* 156 */     FileInputStream localFileInputStream = null;
/* 157 */     ZipInputStream localZipInputStream = null;
/* 158 */     FileOutputStream localFileOutputStream = null;
/* 159 */     ZipOutputStream localZipOutputStream = null;
/* 160 */     ZipEntry localZipEntry = null;
/*     */ 
/* 162 */     ObjectInputStream localObjectInputStream = null;
/* 163 */     ObjectOutputStream localObjectOutputStream = null;
/*     */ 
/* 166 */     File localFile1 = new File(paramString1);
/*     */ 
/* 169 */     if (localFile1.isFile())
/*     */     {
/*     */       try
/*     */       {
/* 177 */         localFileInputStream = new FileInputStream(paramString1);
/* 178 */         localZipInputStream = new ZipInputStream(localFileInputStream);
/*     */ 
/* 181 */         localFileOutputStream = new FileOutputStream("gsstemp.zip");
/* 182 */         localZipOutputStream = new ZipOutputStream(localFileOutputStream);
/*     */ 
/* 189 */         int i = localZipInputStream.available();
/*     */ 
/* 191 */         while (localZipInputStream.available() != 0)
/*     */         {
/* 193 */           localZipEntry = localZipInputStream.getNextEntry();
/*     */ 
/* 195 */           if ((localZipEntry == null) || (localZipEntry.getName().equals(paramString2)))
/*     */           {
/*     */             continue;
/*     */           }
/* 199 */           localZipOutputStream.putNextEntry(localZipEntry);
/*     */ 
/* 201 */           localObjectInputStream = new ObjectInputStream(localZipInputStream);
/* 202 */           localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);
/* 203 */           Object localObject = localObjectInputStream.readObject();
/*     */ 
/* 205 */           localObjectOutputStream.writeObject(localObject);
/*     */         }
/*     */ 
/* 210 */         localZipEntry = new ZipEntry(paramString2);
/*     */ 
/* 212 */         localZipOutputStream.putNextEntry(localZipEntry);
/*     */ 
/* 214 */         localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);
/*     */ 
/* 216 */         localObjectOutputStream.writeObject(paramObject);
/* 217 */         localObjectOutputStream.close();
/* 218 */         localZipInputStream.close();
/*     */       }
/*     */       catch (FileNotFoundException localFileNotFoundException1)
/*     */       {
/* 224 */         throw new IOException(localFileNotFoundException1.getMessage());
/*     */       }
/*     */       catch (StreamCorruptedException localStreamCorruptedException1)
/*     */       {
/* 228 */         throw new IOException(localStreamCorruptedException1.getMessage());
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/* 232 */         throw localIOException1;
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException)
/*     */       {
/* 236 */         throw new IOException(localClassNotFoundException.getMessage());
/*     */       }
/*     */ 
/* 239 */       File localFile2 = new File("gsstemp.zip");
/*     */ 
/* 241 */       localFile1.delete();
/*     */       try
/*     */       {
/* 245 */         if (!localFile2.renameTo(localFile1))
/* 246 */           throw new IOException("can't write to target file " + paramString1);
/*     */       }
/*     */       catch (SecurityException localSecurityException)
/*     */       {
/* 250 */         throw new IOException(localSecurityException.getMessage());
/*     */       }
/*     */       catch (NullPointerException localNullPointerException)
/*     */       {
/* 256 */         throw new IOException(localNullPointerException.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 269 */         localFileOutputStream = new FileOutputStream(paramString1);
/* 270 */         localZipOutputStream = new ZipOutputStream(localFileOutputStream);
/* 271 */         localZipEntry = new ZipEntry(paramString2);
/*     */ 
/* 273 */         localZipOutputStream.putNextEntry(localZipEntry);
/*     */ 
/* 275 */         localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);
/*     */ 
/* 277 */         localObjectOutputStream.writeObject(paramObject);
/* 278 */         localObjectOutputStream.close();
/*     */       }
/*     */       catch (FileNotFoundException localFileNotFoundException2)
/*     */       {
/* 282 */         throw new IOException(localFileNotFoundException2.getMessage());
/*     */       }
/*     */       catch (StreamCorruptedException localStreamCorruptedException2)
/*     */       {
/* 286 */         throw new IOException(localStreamCorruptedException2.getMessage());
/*     */       }
/*     */       catch (IOException localIOException2)
/*     */       {
/* 290 */         throw localIOException2;
/*     */       }
/*     */     }
/*     */ 
/* 294 */     System.out.print(paramString2 + " has been successfully stored in ");
/* 295 */     System.out.println(paramString1);
/*     */   }
/*     */ 
/*     */   public void insertObjtoFile(String paramString1, String paramString2, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 305 */     File localFile1 = new File(paramString1);
/* 306 */     File localFile2 = new File(paramString1 + paramString2);
/*     */ 
/* 311 */     if (!localFile1.isDirectory())
/*     */     {
/* 315 */       throw new IOException("directory " + paramString1 + " doesn't exist");
/*     */     }
/*     */ 
/* 318 */     if (localFile2.exists())
/*     */     {
/*     */       try
/*     */       {
/* 324 */         localFile2.delete();
/*     */       }
/*     */       catch (SecurityException localSecurityException)
/*     */       {
/* 328 */         throw new IOException("file exist, can't overwrite file.");
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 334 */       FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
/* 335 */       ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
/*     */ 
/* 337 */       localObjectOutputStream.writeObject(paramObject);
/* 338 */       localObjectOutputStream.close();
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException)
/*     */     {
/* 342 */       throw new IOException("file can't be created.");
/*     */     }
/*     */ 
/* 345 */     System.out.print(paramString2 + " has been successfully stored in ");
/* 346 */     System.out.println(paramString1);
/*     */   }
/*     */ 
/*     */   public void openArchiveforRead()
/*     */   {
/*     */     try
/*     */     {
/* 356 */       this.m_rzipFile = new ZipFile(this.m_izipName);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 360 */       localIOException.printStackTrace();
/* 361 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void closeArchiveforRead()
/*     */   {
/*     */     try
/*     */     {
/* 369 */       this.m_rzipFile.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 373 */       localIOException.printStackTrace();
/* 374 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object readObj(String paramString)
/*     */   {
/* 380 */     URL localURL = getClass().getResource(paramString);
/*     */ 
/* 382 */     if (localURL == null) {
/* 383 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 387 */       InputStream localInputStream = localURL.openStream();
/* 388 */       ObjectInputStream localObjectInputStream = null;
/* 389 */       Object localObject = null;
/*     */ 
/* 391 */       localObjectInputStream = new ObjectInputStream(localInputStream);
/* 392 */       localObject = localObjectInputStream.readObject();
/*     */ 
/* 394 */       return localObject;
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException)
/*     */     {
/*     */     }
/*     */ 
/* 409 */     return null;
/*     */   }
/*     */ 
/*     */   public Object readObj(String paramString1, String paramString2)
/*     */   {
/*     */     try
/*     */     {
/* 422 */       FileInputStream localFileInputStream = new FileInputStream(paramString1);
/* 423 */       ZipInputStream localZipInputStream = new ZipInputStream(localFileInputStream);
/* 424 */       ZipEntry localZipEntry = null;
/* 425 */       ObjectInputStream localObjectInputStream = null;
/* 426 */       Object localObject = null;
/* 427 */       int i = localZipInputStream.available();
/*     */ 
/* 429 */       while (localZipInputStream.available() != 0)
/*     */       {
/* 431 */         localZipEntry = localZipInputStream.getNextEntry();
/*     */ 
/* 433 */         if ((localZipEntry == null) || (!localZipEntry.getName().equals(paramString2)))
/*     */         {
/*     */           continue;
/*     */         }
/* 437 */         localObjectInputStream = new ObjectInputStream(localZipInputStream);
/* 438 */         localObject = localObjectInputStream.readObject();
/*     */       }
/*     */ 
/* 444 */       localZipInputStream.close();
/*     */ 
/* 446 */       return localObject;
/*     */     } catch (IOException localIOException) {
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {
/*     */     }
/* 451 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.sql.ConverterArchive
 * JD-Core Version:    0.6.0
 */