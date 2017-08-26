package cn.ssm.service.spider.baidunews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderNewsContent {

	private String getDetailsContent(String summary,String detailsContent) {
		StringBuffer sb = new StringBuffer();
		try {
			
			Thread.sleep(1000);
			String newsContentReg = summary.substring(0,3)+".*?</div>";
			
			
			Pattern newsContentPattern = Pattern.compile(newsContentReg,
					Pattern.MULTILINE | Pattern.DOTALL);
			Matcher newsContent = newsContentPattern.matcher(detailsContent);
			String newsPartContent = null;
			while (newsContent.find()) {
				
				newsPartContent = newsContent.group();
				newsPartContent = newsPartContent.replaceAll("<.*?>", "");

				newsPartContent = newsPartContent.replaceAll("\\(.*?\\)", "");

				newsPartContent = newsPartContent.replaceAll("\\{.*?\\}", "");
				newsPartContent = newsPartContent.replaceAll("\\[.*?\\]", "");

				newsPartContent = newsPartContent.replaceAll("http.*?\\)", "");

				newsPartContent = newsPartContent.replaceAll("varf.*?;", "");

				newsPartContent = newsPartContent.replaceAll("[^0-9\u4e00-\u9fa5.《》：、，,。？“”]+","");

				newsPartContent = newsPartContent.replaceAll("\\s*|\t|\r|\n", "");
				sb.append(newsPartContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();

	}

	private Map<String, String> searchNewsByMovie(String movie) {
		Map<String, String> resultMap = new HashMap<String, String>();
		SpiderNewsBaidu spiderNewsBaidu = new SpiderNewsBaidu();
		String indexContent = spiderNewsBaidu
				.getContent("http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=电影%20" + movie);
		if(indexContent!=null){
		List<NewsBaidu> newsBaiduList = spiderNewsBaidu.getNews(indexContent);

		for (NewsBaidu newsBaidu : newsBaiduList) {
			System.out.println(newsBaidu.getNewsUrl());

			String detailsContent = spiderNewsBaidu.getContent(newsBaidu
					.getNewsUrl());
			
//			System.out.println(detailsContent);
			System.out.println(newsBaidu.getNewsTitle());
			if (detailsContent!=null){
				try {
					Random random = new Random();
					int stime = random.nextInt(10000);
					Thread.sleep(stime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String newsPartContent = getDetailsContent(newsBaidu.getNewsSummary(),detailsContent);
				System.out.println("----------------------"+newsPartContent);
				if(newsPartContent!=null){
					StringBuffer sb = new StringBuffer();
					sb.append(newsBaidu.getNewsUrl() + "\n");
					sb.append(newsBaidu.getNewsMedia() + "\n");
					sb.append(newsBaidu.getReleaseTime() + "\n");
					sb.append(newsBaidu.getNewsSummary() + "\n");
					sb.append(newsPartContent);
					resultMap.put(newsBaidu.getNewsTitle(), sb.toString());
				}
				
				
			}
			
		}
		}
		return resultMap;
	}

	public static void main(String[] args) {
		
//		List< String> mlist =  new ArrayList<String>();
//		mlist.add("长江七号");
//		Set<Integer> nameKeySet = movieList.keySet();

		List<String> mlist = FileUtil.bufferReadTxt("./movie_list/movie_3.txt");
		System.out.println(mlist.size());
		for(String name :mlist){
			System.out.println(mlist.indexOf(name)+"  "+name);
			System.out.println("电影 "+name);
			SpiderNewsContent spiderNewsContent = new SpiderNewsContent();

			Map<String, String> searchNewsByMovie = spiderNewsContent
					.searchNewsByMovie(name);
			Set<String> results = searchNewsByMovie.keySet();
			for (String movieNews : results) {
				FileUtil.rwFile(searchNewsByMovie.get(movieNews),
						"./newsData1/"+name, movieNews + ".txt");
				System.out.println("_____________________________");
			}
		}


	}

}
