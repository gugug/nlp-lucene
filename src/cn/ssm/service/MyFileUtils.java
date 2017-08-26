package cn.ssm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cn.ssm.po.BaiduBaike;
import cn.ssm.po.Douban;
import cn.ssm.po.News;
import cn.ssm.po.Weibo;

/**
 * 打开文本的内容，进行对象的封装，分别是新闻、百度百科、微博、豆瓣四个语料的文本。 分别传入对应的具体路径，即可得到对应的对象
 * 
 * @author gu
 *
 */

public class MyFileUtils {

	/**
	 * 打开文本
	 * 
	 * @param source
	 *            需要打开的文本路径
	 * @return String 文本内容
	 */
	public String readFile(String source) {
		// 这个很重要因为在tomact上运行时根据相对路径找到文本会比较复杂，所以直接在这里指定绝对路径。
		source = new PathConfig().getRealPath() + source.replace(".\\", "");
		InputStreamReader inputStreamReader = null;
		BufferedReader br = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			File f = new File(source);
			inputStreamReader = new InputStreamReader(new FileInputStream(f), "utf-8");
			// System.out.println(inputStreamReader.getEncoding());
			br = new BufferedReader(inputStreamReader);

			String str;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 打开微博的文本
	 * 
	 * @param weiboPath
	 *            微博路径
	 * @return Weibo对象
	 */
	public Weibo getWeibo(String weiboPath) {
		// weiboPath = new PathConfig().getWeiboPath() + "\\" + weiboPath;
		String[] splitPath = weiboPath.split("\\\\");
		String wId = splitPath[splitPath.length - 2] + "/" + splitPath[splitPath.length - 1];
		String content = readFile(weiboPath);
		String[] splitContent = content.split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = 2; i < splitContent.length; i++) {
			sb.append(splitContent[i]);
		}
		
		Weibo weibo = new Weibo();
		weibo.setwContent(sb.toString());
		weibo.setwName(splitContent[0]);
		weibo.setwId(wId);
		return weibo;
	}

	/**
	 * 打开新闻文本
	 * 
	 * @param newsPath
	 *            新闻路径
	 * @return News对象
	 */
	public News getNews(String newsPath) {
		// newsPath = new PathConfig().getNewsPath() + "\\" + newsPath;
		String[] splitPath = newsPath.split("\\\\");
		// regex为\\\\，因为在java中\\表示一个\，而regex中\\也表示\，所以当\\\\解析成regex的时候为\\。
		String nId = splitPath[splitPath.length - 2] + "\\" + splitPath[splitPath.length - 1];
		String title = splitPath[splitPath.length - 1].replaceAll(".txt", "");
		String content = readFile(newsPath);
		String[] splitContent = content.split("\n");
		News news = new News();
		news.setnId(nId);
		news.setnTitle(title);
		if (splitContent.length == 5) {
			news.setnUrl(splitContent[0]);
			news.setnMedia(splitContent[1]);
			news.setnTime(splitContent[2]);
			news.setnSummary(splitContent[3]);
			String zhengwen = splitContent[4];
			zhengwen=zhengwen.replaceAll("[\\d|.|,]{10,}", "");
			news.setnContent(zhengwen);
		} else {
			news.setnUrl(splitContent[0]);
			news.setnMedia(splitContent[1]);
			news.setnTime(splitContent[2]);
			news.setnSummary(splitContent[3]);
			news.setnContent(splitContent[3]);
		}
		return news;
	}

	/**
	 * 打开豆瓣文本
	 * 
	 * @param doubanPath
	 *            豆瓣路径
	 * @return Douban对象
	 */
	public Douban getDouban(String doubanPath) {
		// doubanPath = new PathConfig().getDoubanPath() + "\\" + doubanPath;
		String content = readFile(doubanPath);
		String[] splitContent = content.split("`");
		Douban douban = new Douban();
		douban.setdId(splitContent[0]);
		douban.setdUrl("https://movie.douban.com/subject/" + splitContent[0]);
		douban.setdContent(splitContent[splitContent.length - 1]);
		return douban;
	}

	/**
	 * 打开百度百科文本
	 * 
	 * @param baidubaikePath
	 * @return BaiduBaike对象
	 */
	public BaiduBaike getBaidubaike(String baidubaikePath) {
		// baidubaikePath = new PathConfig().getBaidubaikePath() + "\\" +
		// baidubaikePath;
		String content = readFile(baidubaikePath);
		String[] splitContent = content.split("\n");
		BaiduBaike baiduBaike = new BaiduBaike();
		String[] splitName = baidubaikePath.split("\\\\");
		baiduBaike.setbId(splitContent[0]);
		baiduBaike.setbUrl(splitName[splitName.length - 2]);
		baiduBaike.setbContent(content);
		PathConfig pathConfig = new PathConfig();
		List<String> imgList = getBaiduBaikeImg(pathConfig.getRealPath()
				+ pathConfig.getBaidubaikePath().replace(".\\", "") + "\\" + splitName[splitName.length - 2] + "\\img");
		baiduBaike.setImgList(imgList);
		return baiduBaike;
	}

	public List<String> getBaiduBaikeImg(String imgPath) {
		// imgPath=new PathConfig().getBaidubaikePath()+"\\"+imgPath;
		System.out.println(imgPath);
		File file = new File(imgPath);
		String[] fileName = file.list();// 获取文件夹或文件的名字
		List<String> arrayList = Arrays.asList(fileName);
		/*
		 * Arrays.asList() 函数得到的 List 就是 AbstractList。
		 * 该AbstractList只是简单地在已有的元素数组上套了一层List 的接口，
		 * 所以不支持增删改操作。如果希望能增删改，必须 new 一个LinkedList 或ArrayList。
		 */
		List<String> imgList = new ArrayList<String>(arrayList);

	    Iterator<String> it = imgList.iterator();  
	    while(it.hasNext()){
	        String iii = it.next(); 
	        if (iii.equals("剧照.jpg")||(iii.equals(".jpg"))||(iii.equals("&nbsp;.jpg"))) {
	            it.remove(); //移除该对象
	        }
	    }
	    System.out.println(imgList);
		return imgList;
	}

	public static void main(String[] args) {
		MyFileUtils myFileUtils = new MyFileUtils();
		String wsource = ".\\DataDocuments\\weiboData\\苏乞儿\\M_EmftgytYD.txt";
		myFileUtils.getWeibo(wsource);
		String nsource = ".\\DataDocuments\\newsData\\苏乞儿\\王美英加盟《苏乞儿》 “千面女侠”侠肝义胆.txt";
		myFileUtils.getNews(nsource);
		String doubanPath = ".\\DataDocuments\\doubanData\\苏乞儿.txt";
		myFileUtils.getDouban(doubanPath);
		String baidubaikePath = ".\\DataDocuments\\baidubaikeData\\007：大破量子危机\\音乐原声.txt";
		myFileUtils.getBaidubaike(baidubaikePath);
	}

}
