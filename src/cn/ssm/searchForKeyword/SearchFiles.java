package cn.ssm.searchForKeyword;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class SearchFiles {
	
	
	public static String[]  seg(String text) throws IOException{
		ArrayList<String> words = new ArrayList<String>();
        StringReader sr=new StringReader(text);
        IKSegmenter ik=new IKSegmenter(sr, true);  
        Lexeme lex=null;  
        while((lex=ik.next())!=null){  
//            System.out.print(lex.getLexemeText()+"|");  
            words.add(lex.getLexemeText());
        }  
        int size=words.size();  
        String[] array = (String[])words.toArray(new String[size]);  
        return array;
	}
	
	public  static ArrayList<String> getResult(String indexPath,String[] fields,String word,int n) throws IOException{
		long  startTime  =   new Date().getTime();  
		Directory indexDir = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
		DirectoryReader directoryReader = DirectoryReader.open(indexDir);  
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);  
		
		//查看所有索引文件
//		directoryReader.maxDoc();
//		Document doc = null;
//		for(int i =0; i < directoryReader.maxDoc(); i++){
//			doc = indexSearcher.doc(i);
//			System.out.println("Doc[" + i + "] : name :"  + doc.get("name") + ",content:" + doc.get("content") + doc.get("content").getClass().getName());
//		}
		
//		Analyzer analyzer = new StandardAnalyzer();
		
//		
//		QueryBuilder queryBuilder = new QueryBuilder(analyzer); 
//        Query query = queryBuilder.createPhraseQuery(field, word);    

//		Query query = new TermQuery(new Term(field,word));
		 String [] words=seg(word);
		 int wordNum =words.length;
		 int fieldNum = fields.length;
		 String[] stringQuery = new String[wordNum*fieldNum];
		 String[] fieldsQuery = new String[wordNum*fieldNum];
		 Occur[] occ= new Occur[wordNum*fieldNum];
//		 String[] fields={"path","content"};
		 for(int i =0 ; i < fieldNum; i++){
			 for(int j = wordNum*i; j < wordNum*(i+1); j++){
				 stringQuery[j] = words[j-wordNum*i];	 
				 fieldsQuery[j] = fields[i];
				 occ[j] = Occur.SHOULD;
			 }
		 }
		
		Analyzer analyzer = new MyIkAnalyzer();
		ArrayList<String> fileNames = new ArrayList<String>();
		try{
			Query query = MultiFieldQueryParser.parse(stringQuery, fieldsQuery, occ, analyzer);
			TopDocs topDocs = indexSearcher.search(query, n);
			long endTime = new Date().getTime();
//            System.out.println("一共花费了" + (endTime - startTime) + "毫秒查询！");
//            System.out.println("检索到" + topDocs.totalHits + "条记录。");

			   //读出得到的索引文件
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
			
			for (ScoreDoc scoreDoc : scoreDocs) {    
				Document document = indexSearcher.doc(scoreDoc.doc);    
				fileNames.add( document.getField("path").stringValue());  
//				System.out.println("文本路径：" + document.getField("path").stringValue());
//				System.out.println("文本内容：" + document.getField("content").stringValue());
			}   
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		
		 indexDir.close();    //关闭，清除内存
		 return fileNames;
	}
}
