package cn.ssm.searchForKeyword;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Multi {
	
	public static void createIndex(String indexPath, String docPath) throws IOException{
		IndexFiles indexFiles = new IndexFiles();
		
		File docs = new File(docPath);
		File[] textList = docs.listFiles();
		for(File file: textList ){
			String path = file.getPath();
			String fileName = file.getName();
			String indexPath1= indexPath+"/"+fileName;
			indexFiles.makeIndex(path,indexPath1);   
		}
		System.out.println("构建索引文件完成！");
	}
	
	public static Map<String,ArrayList<String>> search(String indexPath,String[] fields,String word,int n) throws IOException{
		File docs = new File(indexPath);
		File[] indexList = docs.listFiles();
		SearchFiles searchFiles = new SearchFiles();
		Map<String,ArrayList<String>>  results = new HashMap<String,ArrayList<String>>();
		for(File file:indexList){
			String name = file.getName();
			String path = file.getPath();
			ArrayList<String> result = searchFiles.getResult(path, fields, word, n);
			results.put(name, result);
		}
		return results;
	}
	
	
}
