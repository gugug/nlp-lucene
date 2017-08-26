package cn.ssm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ssm.po.BaiduBaike;
import cn.ssm.po.Douban;
import cn.ssm.po.News;
import cn.ssm.po.Weibo;
import cn.ssm.searchForKeyword.SearchWord;
import cn.ssm.service.MyFileUtils;

@Controller
@RequestMapping("/index")
public class MySearchController {
	// 获取索引
	private Map<String, ArrayList<String>> fileIndex = null;

	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	public String index() throws Exception {
		return "/index/index";
	}

	@RequestMapping(value = "/search", method = { RequestMethod.POST, RequestMethod.GET })
	public String search(Model model, HttpServletRequest request,
			@RequestParam(value = "searchContent", required = true, defaultValue = "电影") String scontent)
			throws Exception {
		System.out.println(scontent);

		// 获取索引
		fileIndex = SearchWord.mainLucene(scontent);
		if (fileIndex.size() == 0) {
			System.out.println("没有结果");
			return "error";
		}
		MyFileUtils myFileUtils = new MyFileUtils();

		BaiduBaike baiduBaike = null;
		Douban douban = null;
		News news = null;
		Weibo weibo = null;
		List<String> baidubaikePathList = fileIndex.get("baidubaikeData");
		List<String> doubanPathList = fileIndex.get("doubanData");
		List<String> newsPathList = fileIndex.get("newsData");
		List<String> weiboPathList = fileIndex.get("weiboData");

		try {
			/**
			 * 百度百科
			 */
			baiduBaike = myFileUtils.getBaidubaike(baidubaikePathList.get(0));
		} catch (Exception e) {
			baiduBaike = new BaiduBaike("抱歉，百度百科没有找到您想要的内容！");
		}

		try {
			/**
			 * 豆瓣
			 */
			douban = myFileUtils.getDouban(doubanPathList.get(0));
		} catch (Exception e) {
			douban = new Douban("抱歉，豆瓣没有找到你想要的内容！");
		}
		try {
			/**
			 * 新闻
			 */
			news = myFileUtils.getNews(newsPathList.get(0));
		} catch (Exception e) {
			news = new News("抱歉，新闻没有找到您想要的内容！");
		}

		try {
			/**
			 * 微博
			 */
			weibo = myFileUtils.getWeibo(weiboPathList.get(0));
		} catch (Exception e) {
			weibo = new Weibo("抱歉，微博没有找到您想要的内容！");
		}

		model.addAttribute("time", fileIndex.get("time"));
		model.addAttribute("scontent", scontent);
		model.addAttribute("baiduBaike", baiduBaike);
		model.addAttribute("douban", douban);
		model.addAttribute("news", news);
		model.addAttribute("weibo", weibo);
		return "/index/searchResult";
	}

	/**
	 * 更多新闻
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/moreNewsResult", method = { RequestMethod.POST, RequestMethod.GET })
	public String moreNewsResult(HttpServletRequest request, Model model, @RequestParam(value = "id") String id)
			throws Exception {
		System.out.println(id);
		
		List<News> newsList = new ArrayList<News>();
		List<String> newsPathList = fileIndex.get("newsData");
		System.out.println("More News: "+newsPathList);
		MyFileUtils myFileUtils = new MyFileUtils();
		for (String path : newsPathList) {
			News news = myFileUtils.getNews(path);
			newsList.add(news);
		}
		model.addAttribute("newsList", newsList);
		return "/index/moreNewsResult";
	}

	/**
	 * 更多微博
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/moreWeiboResult", method = { RequestMethod.POST, RequestMethod.GET })
	public String moreWeiboResult(Model model, @RequestParam(value = "id") String id) throws Exception {
		System.out.println(id);
		List<Weibo> weiboList = new ArrayList<Weibo>();
		List<String> weiboPathList = fileIndex.get("weiboData");
		System.out.println("More Weibo: "+weiboPathList);
		MyFileUtils myFileUtils = new MyFileUtils();
		for (String path : weiboPathList) {
			Weibo weibo = myFileUtils.getWeibo(path);
			weiboList.add(weibo);
		}
		model.addAttribute("weiboList", weiboList);
		return "/index/moreWeiboResult";
	}

}
