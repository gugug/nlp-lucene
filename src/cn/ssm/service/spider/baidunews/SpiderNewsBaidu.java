package cn.ssm.service.spider.baidunews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SpiderNewsBaidu {
	public static Calendar calendar = Calendar.getInstance();

	public String getContent(String url) {
		String content = null;
		try {
			Document doc = Jsoup.connect(url).data("query", "Java") // 请求参数
					.userAgent("I'm JSOUP") // 设置 User-Agent
					.cookie("auth", "token") // 设置 cookie
					.timeout(50000) // 设置连接超时时间
					.get(); // 访问url
			content = doc.toString(); // 获取新闻事件网页的静态源代码
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

//	public String getContent(String url) {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            HttpGet httpget = new HttpGet(url);
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            try {
//                // 获取响应实体
//                HttpEntity entity = response.getEntity();
//                // 打印响应状态
//                if (entity != null) {
//                    return EntityUtils.toString(entity);
//                }
//            } finally {
//                response.close();
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭连接,释放资源
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
	
	
//	public String getContent(String url) {
//		String result = "";
//		BufferedReader bf = null;
//		try {
//	
//			URL realUrl = new URL(url);
//			URLConnection connection = realUrl.openConnection();
//			connection.setConnectTimeout(2000);  
//			connection.setReadTimeout(2000);  
//			connection.connect();
//			InputStream is = connection.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is,"utf-8");
//			bf = new BufferedReader(isr);
//			String line;
//			while ((line = bf.readLine()) != null) {
//				result += line;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (bf != null) {
//				try {
//					bf.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	//	System.out.println("content"+result);
//	
//		return result;
//	}
	
	private String getNewsUrl(String newsBlockContent) {
		String newsUrlReg = "<h3 class=\"c-title\"><a href=\"(.*?)\"";
		Pattern newsUrlPattern = Pattern.compile(newsUrlReg);
		Matcher newsUrl = newsUrlPattern.matcher(newsBlockContent);
		String newsUrlContent = null;
		while (newsUrl.find()) {
			newsUrlContent = newsUrl.group(1);
		}
		return newsUrlContent;
	}

	private String getNewsTitle(String newsBlockContent) {
		String newsTitleReg = "<h3 class=\"c-title\">.*?target=\"_blank\">(.*?)</a></h3>";
		Pattern newsTitlePattern = Pattern.compile(newsTitleReg,Pattern.MULTILINE | Pattern.DOTALL);
		Matcher newsTitle = newsTitlePattern.matcher(newsBlockContent);
		String newsTitleContent = null;
		while (newsTitle.find()) {
			newsTitleContent = newsTitle.group(1);
			newsTitleContent = newsTitleContent.replaceAll("<em>|</em>", "");
		}
		return newsTitleContent;

	}
	
	private String[] getMediaAndTime(String newsBlockContent){
		String newsMediaTimeReg = "<p class=\"c-author\">(.*?)&nbsp;&nbsp;(.*?)</p>";
		Pattern newsMediaTimePattern = Pattern.compile(newsMediaTimeReg,Pattern.MULTILINE | Pattern.DOTALL);
		Matcher newsMediaTime = newsMediaTimePattern.matcher(newsBlockContent);
		String newsMediaContent = null;
		String newsReleaseTimeContent = null;
		while (newsMediaTime.find()) {
			newsMediaContent = newsMediaTime.group(1);
			newsReleaseTimeContent = newsMediaTime.group(2);
			newsReleaseTimeContent = getReleaseTime(newsReleaseTimeContent);
		}
		String[] newsMediaTimeContent = {newsMediaContent,newsReleaseTimeContent};
		return newsMediaTimeContent;
	}
	
	
	private String getReleaseTime(String newsTime){
		
		if(newsTime.contains("小时前")){
			String ago=newsTime.replace("小时前", "");
			newsTime = CalendarUtil.getNumHoursAgoTime(Integer.parseInt(ago));
		}else if(newsTime.contains("分钟前")){
			String ago = newsTime.replaceAll("分钟前", "");
			newsTime = CalendarUtil.getNumMinsAgoTime(Integer.parseInt(ago));
		}
		return newsTime;
	}
	
	private String getNewsSummary(String newsBlockContent){
		String newsSummaryReg = "</p>(.*?)<span class=\"c-info\">";
		Pattern newsSummaryPattern = Pattern.compile(newsSummaryReg,Pattern.MULTILINE | Pattern.DOTALL);
		Matcher newsSummary = newsSummaryPattern.matcher(newsBlockContent);
		String newsSummaryContent = null;
		while (newsSummary.find()) {
			newsSummaryContent = newsSummary.group(1);
			newsSummaryContent = newsSummaryContent.replaceAll("<em>|</em>", "");
			newsSummaryContent = newsSummaryContent.replaceAll("\\s*|\t|\r|\n", "");

		}
		return newsSummaryContent;
		
	}
	
	private List<String> getNewsBlock(String content) {
		List<String> blockList = new ArrayList<String>();
		String newsBlockReg = "<h3 class=\"c-title\">(.*?)class=\"c-cache";
		Pattern newsBlockPattern = Pattern.compile(newsBlockReg,
				Pattern.MULTILINE | Pattern.DOTALL);
		Matcher newsBlock = newsBlockPattern.matcher(content);
		String newsBlockContent = null;
		while (newsBlock.find()) {
			newsBlockContent = newsBlock.group();
			blockList.add(newsBlockContent);
			
		}
		return blockList;
	}
	
	public List<NewsBaidu> getNews(String content){
		List<NewsBaidu> newsList = new ArrayList<NewsBaidu>();
		List<String> newsBlockList = getNewsBlock(content);
		for (String newsBlockContent : newsBlockList){
			String newsUrl = getNewsUrl(newsBlockContent);
			String newsTitle = getNewsTitle(newsBlockContent);
			String[] mediaTime = getMediaAndTime(newsBlockContent);
			String newsSummary = getNewsSummary(newsBlockContent);
//
//			System.out.println(newsUrl);
//			System.out.println(newsTitle);
//			
//			System.out.println(mediaTime[0]);
//			System.out.println(mediaTime[1]);
//			System.out.println(newsSummary);
			newsList.add(new NewsBaidu(newsUrl, newsTitle, mediaTime[0], mediaTime[1], newsSummary));
		}
		return newsList;
	}
	
	public static void main(String[] args) {
		String content = new SpiderNewsBaidu().getContent("http://cq.people.com.cn/GB/365409/c29461628.html");
		
		System.out.println(content);
	}

}
