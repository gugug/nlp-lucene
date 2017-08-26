package cn.ssm.searchForKeyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {
	
//	public  static String FileReaderAll(String filePath, String charset)   throws  IOException  {  
////BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),charset));
//		String line = new String();
//		String temp = new String();
//		while((line = reader.readLine()) != null){
//			temp += line;
//		}
//		reader.close();
//		return temp;
//	}    
	
	
	public  static String FileReaderAll(String filePath)   throws  IOException  {  
		String s;  
        StringBuffer text = new StringBuffer(); 
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while((s = br.readLine()) != null){
			text.append(s);
		}
		
		br.close();
		return text.toString();

    }    
	
	public static void indexDocs(IndexWriter writer, File file) throws IOException { 
			// file可以读取 
		if (file.canRead()) { 
		// 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况) 
			if (file.isDirectory()) {  
				String[] files = file.list();  
				if (files != null) {
					for (int i = 0; i < files.length; i++)  {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			}else {
				if(file.isFile()){
					String type = file.getName().substring(file.getName().lastIndexOf(".")+1);
					if("txt".equalsIgnoreCase(type)){
						fileIndex(writer,file);
					}
				}
			}
		}
	}
	
	public static void fileIndex(IndexWriter writer,File file) throws IOException{
		System.out.println("File " + file.getCanonicalPath() + "正在被索引.");
//		String temp = FileReaderAll(file.getCanonicalPath(), "UTF-8");
		String temp = FileReaderAll(file.getCanonicalPath());
		
		Document document = new Document();
		Field Path = new Field("path",file.getPath(),TextField.TYPE_STORED);
		Field fileContent = new Field("content",temp,TextField.TYPE_STORED);
		document.add(Path);
		document.add(fileContent);
		writer.addDocument(document);
	}
	
	public static void makeIndex(String filePath, String indexPath) throws IOException{
		//指明要索引文件夹的位置
		File fileDir = new File(filePath);
		
		 //这里放索引文件
		Directory indexDir = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
		
		
//		Analyzer luceneAnalyzer = new StandardAnalyzer();
		Analyzer luceneAnalyzer = new MyIkAnalyzer();
		IndexWriterConfig writerConfig = new IndexWriterConfig(luceneAnalyzer);		
		IndexWriter indexWriter = new IndexWriter(indexDir,writerConfig);
		indexWriter.deleteAll();    //清除以前的index
		
		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();
		

	        // 增加document到索引去   
		indexDocs(indexWriter,fileDir);
//		indexWriter.deleteAll();  
		indexWriter.close();
//		long endTime = new Date().getTime();
//		System.out.println("花费了" + (endTime - startTime) + "毫秒来把文档增加到索引里面！");
        	
	}		
}
