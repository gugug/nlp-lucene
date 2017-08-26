package cn.ssm.service.spider.baike;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Baike {
	
	public static void createFolder() throws IOException {
		//System.out.println("createFolder");
		// 中文编码创建文件夹
		String encoding = "UTF-8";
		//读取电影列表文件
		FileInputStream fin = new FileInputStream("E:/大三学习资料/大三上/自然语言处理/电影目录.txt");
		InputStreamReader inR = new InputStreamReader(fin, encoding);
		BufferedReader bfR = new BufferedReader(inR);
		String temp = null;// 临时缓存 保存读取到的每一行记录
		
		final List<String> MovieList = new ArrayList<String>();// 临时缓存
															// 保存用空格拆分该行记录得到的数组
		while ((temp = bfR.readLine()) != null) {
			//将读到的每一行加入到MovieList列表中
			MovieList.add(temp);

		}
		for (int i = 0; i <5; i++) {
			//根据读到的电影名创建相应文件夹
			File file1 = new File("./basictxt/" + MovieList.get(i));
			File file2 = new File("./basictxt/" + MovieList.get(i) + "/img");
			file1.mkdirs();
			file2.mkdirs();
			//生成相应电影的网页链接
			final String MURL = "http://baike.baidu.com/item/" + MovieList.get(i);
			//String MURL = "http://baike.baidu.com/item/" + MovieList.get(i);
			System.out.println(MURL);
			
			final int i1 = i;
			//使用多线程同时处理相同的多任务
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					String cm;
					try {
						//调用getSoursecode函数获得网页源码
						cm = getSoursecode(MURL);
						//System.out.println(i1+"as");
						//调用forimg得到可以下载图片的网页片段
						String tcm = forimg(cm);
						//getImageSrc获取网页中图片的超链接，并用Map<String, String> imgSrc保存对应的网页链接和图片名
						Map<String, String> imgSrc = getImageSrc(tcm);
						String s = MovieList.get(i1);
						//调用getContent，muhouContent两个函数进行网页中电影信息的爬取并保存到文件夹中
						getContent(cm, s);
						muhouContent(cm, s);
						//调用downloadImgByNet下载网页中的图片
						downloadImgByNet(imgSrc,"./basictxt/"+s+"/img");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			thread.start();

		}
		bfR.close();
	}

	
	
	public static String getSoursecode(String url) throws IOException {
		// 获取网页源代码
		URL uri = null;
		BufferedReader br = null;
		String content = null;

		try {
			uri = new URL(url);
			//建立连接
			HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
			
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			
			String msg = null;
			//按行写入
			while ((msg = br.readLine()) != null) {
				
				content += msg;
			}
			//用正则表达式去掉一些杂词
			content = content.replace("</em>编辑", "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// bw.flush();
			// bw.close();
			br.close();
		}
		
		return content;
	}

	public static void getContent(String content, String afile)
			throws IOException {
		// 截取网页中的目标信息
		// String content=getSoursecode(url);
		String title = "<title>.*?</title>";
		Pattern PTitle = Pattern.compile(title, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher MTitle = PTitle.matcher(content);
		if (MTitle.find()) {
			System.out.println(MTitle.group());
		}

		String r2 = "(基本信息.*?)剧情简介";
		Pattern basicPtn2 = Pattern.compile(r2, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher basicMte2 = basicPtn2.matcher(content);
		String clear2 = null;
		if (basicMte2.find()) {
			clear2 = basicMte2.group(1);

			// System.out.println(clear2);
			clear2 = clear2.replaceAll("基本信息", "基本信息\n");

			clear2 = clear2.replaceAll("<dt.*?>", "  ");
			clear2 = clear2.replaceAll("</dt>", "  ");
			clear2 = clear2.replaceAll("<.*?>", "");
			clear2 = clear2.replaceAll("&nbsp;", "");
			clear2 = clear2.replaceAll("<a name=\"", " ");

		}

		String clear3 = null;
		String reg2 = "</span>(剧情简介.*?)演职员表";
		Pattern scriptPtn = Pattern.compile(reg2, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher scriptMte = scriptPtn.matcher(content);
		if (scriptMte.find()) {
			clear3 = scriptMte.group(1);
			clear3 = clear3.replaceAll("剧情简介", "剧情简介\n");
			clear3 = clear3.replaceAll("编辑", "");
			clear3 = stripHtml(clear3);
		} else {
			// System.out.println(scriptMte.group(1));
		}

		// 演职员表
		String reg3 = "</span>演职员表(.*?)<a name=\"职员表\"";
		Pattern actorPtn = Pattern.compile(reg3, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher actorMte = actorPtn.matcher(content);
		if (actorMte.find()) {
			reg3 = actorMte.group(1);
			reg3 = reg3.replaceAll(afile + "演职员表", afile + "演职员表\n");
			reg3 = reg3.replaceAll("编辑", "");
			reg3 = reg3.replaceAll("<dl.*?>", "\n");
			reg3 = stripHtml(reg3);

		}

		// 职员表
		String reg4 = "</span>(职员表.*?)<a name=\"角色介绍\"";
		Pattern staffPtn = Pattern.compile(reg4, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher staffMte = staffPtn.matcher(content);
		if (staffMte.find()) {
			reg4 = staffMte.group(1);
			reg4.replaceAll(afile + "职员表", afile + "但丁密码职员表\n");
			reg4 = reg4.replaceAll("编辑", "");
			reg4 = reg4.replaceAll("<dl.*?>", "\n");
			reg4 = reg4.replaceAll("<tr.*?>", "\n");
			reg4 = reg4.replaceAll("</td>", "   ");
			reg4 = stripHtml(reg4);

		}

		String juese = "</span>(角色介绍.*?)(<a name=\"音乐原声\"|<h2.*?)";
		Pattern juesePtn = Pattern.compile(juese, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher jueseMte = juesePtn.matcher(content);
		if (jueseMte.find()) {
			juese = jueseMte.group(1);
			juese.replaceAll("角色介绍", "角色介绍\n");
			juese = juese.replaceAll("编辑", "");
			juese = juese.replaceAll("<span.*?>", "  ");
			juese = juese.replaceAll("<dl.*?>", "\n");
			juese = juese.replaceAll("</dt>", "\n");
			juese = juese.replaceAll("<tr.*?>", "\n");
			juese = juese.replaceAll("</td>", "   ");
			juese = stripHtml(juese);

		}

		String yinyue = "</span>(音乐原声.*?)<a name=\"幕后制作\"";
		Pattern yinyuePtn = Pattern.compile(yinyue, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher yinyueMte = yinyuePtn.matcher(content);
		if (yinyueMte.find()) {
			yinyue = yinyueMte.group(1);
			yinyue.replaceAll("音乐原声", "音乐原声\n");
			yinyue = yinyue.replaceAll("编辑", "");
			yinyue = yinyue.replaceAll(
					"<div class=\"para\" label-module=\"para\">", "  ");
			yinyue = yinyue.replaceAll("<span.*?>", "  ");
			yinyue = yinyue.replaceAll("<dl.*?>", "\n");
			yinyue = yinyue.replaceAll("</dt>", "\n");
			yinyue = yinyue.replaceAll("<tr.*?>", "\n");
			yinyue = yinyue.replaceAll("</td>", "   ");
			yinyue = stripHtml(yinyue);

		}
		 else{
		 yinyue=null;
		 }
		 rwFile(clear2 + "\n", "./basictxt/"+afile+"/基本信息.txt");
		 rwFile(clear3 + "\n", "./basictxt/"+afile+"/剧情简介.txt");
		 rwFile(reg3 + "\n", "./basictxt/"+afile+"/演员表.txt");
		 rwFile(reg4 + "\n","./basictxt/"+afile+"/职员表.txt");
		 rwFile(juese + "\n","./basictxt/"+afile+"/角色介绍.txt");
		 rwFile(yinyue + "\n","./basictxt/"+afile+"/音乐原声.txt");
	}

	public static void muhouContent(String content, String afile)
			throws IOException {
		// 截取网页中的目标信息
		// String content=getSoursecode(url);
		// String muhou="</span>(幕后制作.*?)<a name=\"(制作|发行|制作发行).*?\"";<span
		// class="title-prefix">一代宗师</span>
		String muhou = "</span>(幕后制作.*?)<a name=\"(制作|发行|制作发行).*?";
		Pattern muhouPtn = Pattern.compile(muhou, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher muhouMte = muhouPtn.matcher(content);
		if (muhouMte.find()) {
			muhou = muhouMte.group(1);
			muhou.replaceAll("幕后制作", "幕后制作 \n");
			muhou = muhou.replaceAll("编辑", "");
			muhou = muhou.replaceAll("<b>", "\n");
			muhou = muhou.replaceAll("</b>", "   ");
			muhou = muhou.replaceAll("<span.*?>", "  ");
			muhou = muhou.replaceAll("<dl.*?>", "\n");
			muhou = muhou.replaceAll("</dt>", "\n");
			muhou = muhou.replaceAll("<tr.*?>", "\n");
			muhou = muhou.replaceAll("</td>", "   ");
			muhou = stripHtml(muhou);
		}
		 else{
		 muhou=null;
		 }

		String faxing = "</span>((制作发行|发行信息).*?)<a name=\"影片评价\"";
		Pattern faxingPtn = Pattern.compile(faxing, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher faxingMte = faxingPtn.matcher(content);
		if (faxingMte.find()) {
			faxing = faxingMte.group(1);
			faxing = faxing.replaceAll("幕后制作", "幕后制作 \n");
			faxing = faxing.replaceAll("编辑", "");
			faxing = faxing.replaceAll("<b>", "\n");
			faxing = faxing.replaceAll("</b>", "   ");
			faxing = faxing.replaceAll("<span.*?>", "  ");
			faxing = faxing.replaceAll("<dl.*?>", "\n");
			faxing = faxing.replaceAll("</dt>", "\n");
			faxing = faxing.replaceAll("<tr.*?>", "\n");
			faxing = faxing.replaceAll("</td>", "   ");
			faxing = stripHtml(faxing);
		}
		 else{
		 faxing=null;
		 }

		String pingjia = "</span>(影片评价.*?)<span class=\"title-text\">电影评价";
		Pattern pingjiaPtn = Pattern.compile(pingjia, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher pingjiaMte = pingjiaPtn.matcher(content);
		if (pingjiaMte.find()) {
			pingjia = pingjiaMte.group(1);
			pingjia = pingjia.replaceAll("影片评价", "影片评价  \n");
			pingjia = pingjia.replaceAll("编辑", "");
			pingjia = pingjia.replaceAll("<b>", "\n");
			pingjia = pingjia.replaceAll("</b>", "   ");
			pingjia = pingjia.replaceAll("<span.*?>", "  ");
			pingjia = pingjia.replaceAll("<dl.*?>", "\n");
			pingjia = pingjia.replaceAll("</dt>", "\n");
			pingjia = pingjia.replaceAll("<tr.*?>", "\n");
			pingjia = pingjia.replaceAll("</td>", "   ");
			pingjia = stripHtml(pingjia);
		}
		String assess = "<span class=\"title-text\">(电影评价.*?)<span class=\"title\">词条图册";
		Pattern assessPtn = Pattern.compile(assess, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher assessMte = assessPtn.matcher(content);
		if (assessMte.find()) {
			assess = assessMte.group(1);
			assess = assess.replaceAll("电影评价", "电影评价  \n");
			assess = assess.replaceAll("编辑", "");
			assess = assess.replaceAll("<b>", "\n");
			assess = assess.replaceAll("</b>", "   ");
			assess = assess.replaceAll("<span.*?>", "  ");
			assess = assess.replaceAll("<dl.*?>", "\n");
			assess = assess.replaceAll("</dt>", "\n");
			assess = assess.replaceAll("<tr.*?>", "\n");
			assess = assess.replaceAll("</td>", "   ");
			assess = stripHtml(assess);
		}
		 rwFile(muhou + "\n","./basictxt/"+afile+"/幕后制作.txt");
		 rwFile(faxing + "\n","./basictxt/"+afile+"/制作发行.txt");
		 rwFile(pingjia + "\n","./basictxt/"+afile+"/影片评价.txt");
	}

	public static String stripHtml(String content) {

		content = content.replaceAll("<.*?>", "");
		content = content.replaceAll("<dt.*?>", "   ");
		content = content.replaceAll("</dt>", "   ");
		content = content.replaceAll("<.*?>", "");
		content = content.replaceAll("&nbsp;", "");
		content = content.replaceAll("<a name=\"", " ");
		return content;
	}

	public static String forimg(String content) throws IOException {
		// String content=getSoursecode(url);
		String mactor = "<dt>主要<em>演员</em>(.*?)<dt.*?>图集</em>";
		Pattern mactorPtn = Pattern.compile(mactor, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher mactorMte = mactorPtn.matcher(content);
		String xactor = "";
		if (mactorMte.find()) {
			xactor = mactorMte.group(1);
		}
		String am = "";
		String reg3 = "</span>演职员表(.*?)<a name=\"职员表\"";
		Pattern actorPtn = Pattern.compile(reg3, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher actorMte = actorPtn.matcher(content);
		if (actorMte.find()) {
			am = actorMte.group(1);
			xactor += am;
		}
		String bm = "";
		String juese = "</span>(角色介绍.*?)<a name=\"音乐原声\"";
		Pattern juesePtn = Pattern.compile(juese, Pattern.MULTILINE
				| Pattern.DOTALL);
		Matcher jueseMte = juesePtn.matcher(content);
		if (jueseMte.find()) {
			bm = jueseMte.group(1);
			xactor += bm;
			// System.out.println(xactor);
		}
		return xactor;
	}

	private static Map<String, String> getImageSrc(String htmlCode) {
		Map<String, String> map = new HashMap<String, String>();
		// List<String> imageSrcList = new ArrayList<String>();
		// String lt="<img src=\"(.*?)\".*?>.*?<a^.*?(?!img).*?$\">(.*?)</a>";
		String lt = "<img src=\"(.*?)\".*?>.*?<a (?<!<img).*?\">(.*?)</a>";
		Pattern lp = Pattern.compile(lt, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher lm = lp.matcher(htmlCode);
		// String ming=null;
		while (lm.find()) {
			// ming=lm.group(2);
			map.put(lm.group(1), lm.group(2));
		}
		return map;
	}
	//去除获取到的信息中的html源码
	public static String illegalSymbol(String str) {
		if (str.contains("/") || str.contains("\\") || str.contains("?")
				|| str.contains(":") || str.contains("*") || str.contains("\"")
				|| str.contains("<") || str.contains(">")) {
			str = str.substring(str.lastIndexOf("/") + 1, str.length());
			str = str.replaceAll("<.*?>", "");
			
			str = str.replaceAll(".*?>", "剧照2");
			str = str.replaceAll("<img.*?", "片场照");
			str = str.replaceAll("&nbsp;", "剧照1");
			return str;
		} else {
			return str;
		}
	}

	public static void downloadImgByNet(Map<String, String> imgSrc,
			String filePath) {
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		InputStream is = null;
		FileOutputStream fos = null;
		System.out.println(imgSrc.size());
		try {
			// Map<String,String> map = null;
			Set<String> set = imgSrc.keySet();

			for (String obj : set) {

				System.out.println(obj + "-->" + imgSrc.get(obj));
				String picUrl = (String) obj;
				String picName = (String) imgSrc.get(obj);
				picName = illegalSymbol(picName);
				
				URL uri = new URL(picUrl);
				URLConnection conn = uri.openConnection();
				conn.setConnectTimeout(3 * 1000);
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				is = conn.getInputStream();
				bis = new BufferedInputStream(is);
				byte[] bs = new byte[1024];
				int len = 0;
				File saveDir = new File(filePath);
				if (!saveDir.exists()) {
					saveDir.mkdir();
				}
				File file = new File(saveDir + File.separator + picName
						+ ".jpg");
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				while ((len = bis.read(bs)) != -1) {
					bos.write(bs, 0, len);
					bos.flush();

				}
				System.out.println("下载成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
			if (bis != null) {
				try {
					is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public static void rwFile(String data, String address) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(address);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		createFolder();
	}
}
