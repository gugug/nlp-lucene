package cn.ssm.searchForKeyword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import cn.ssm.service.PathConfig;

public class SearchWord {

	/**
	 * 构建索引文件，运行一次就够了,索引文件存在indexPath
	 */
	public static void IndexContro() {
		Multi multi = new Multi();

		String indexPath = "./MovieLucene/index"; // 存放索引的路径
		String docPath = "./DataDocuments"; // 被索引文件的路径
		try {
			multi.createIndex(indexPath, docPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("建立索引结束。");
	}

	/**
	 * 对关键词搜索后出来的结果
	 * 
	 * @param keyWord
	 * @return Map<String,ArrayList<String>>,ArrayList是路径列表
	 * @throws IOException
	 */
	public static Map<String, ArrayList<String>> mainLucene(String keyWord) throws IOException {

		Multi multi = new Multi();
		// D:\\javaEE\\workspace\\nlphk\\
		String indexPath = new PathConfig().getRealPath() + "MovieLucene/index"; // 存放索引的路径
		String docPath = "./DataDocuments"; // 被索引文件的路径

		// 搜索关键词
		String[] fields = { "content", "path" }; // 搜索区域：文本内容，文本路径
		// String[] fields = {"content"};
		// String[] fields = {"path"};

		String word = keyWord; // 搜索关键词
		int num = 3; // 搜索返回列表数量

		long startTime = new Date().getTime();

		Map<String, ArrayList<String>> results = multi.search(indexPath, fields, word, num); // 获取搜索结果

		long endTime = new Date().getTime();
		System.out.println("花费了" + (endTime - startTime) + "毫秒搜索！");
		for (String type : results.keySet()) {
			System.out.println(type);
			ArrayList<String> file = results.get(type);
			for (String path : file) {
				System.out.println(path);
			}
		}
		ArrayList<String> timeList = new ArrayList<String>();
		timeList.add((endTime - startTime) + "");
		results.put("time", timeList);
		return results;
	}

	public static void main(String[] args) throws Exception {
		// SearchWord.IndexContro();
		// SearchWord.mainLucene("苏乞儿");

	}
}
