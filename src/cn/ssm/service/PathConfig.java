package cn.ssm.service;

/**
 * 路径的相关配置，其中RealPath这个路径是项目放置的位置(D:\\javaEE\\workspace\\nlphk\\)很重要
 * @author gu
 *
 */

public class PathConfig {
	private String RealPath = "D:\\javaEE\\workspace\\nlphk\\";
	private String baidubaikePath = ".\\DataDocuments\\baidubaikeData";
	private String doubanPath = ".\\DataDocuments\\doubanData";
	private String newsPath = ".\\DataDocuments\\newsData";
	private String weiboPath = ".\\DataDocuments\\weiboData";

	public PathConfig() {
		super();
	}

	/**
	 * 获取实际本地位置
	 * @return
	 */
	public String getRealPath() {
		return RealPath;
	}

	public void setRealPath(String realPath) {
		RealPath = realPath;
	}

	/**
	 * 获取百度百科的位置
	 * @return
	 */
	public String getBaidubaikePath() {
		return baidubaikePath;
	}

	public void setBaidubaikePath(String baidubaikePath) {
		this.baidubaikePath = baidubaikePath;
	}

	/**
	 * 获取豆瓣的位置
	 * @return
	 */
	public String getDoubanPath() {
		return doubanPath;
	}

	public void setDoubanPath(String doubanPath) {
		this.doubanPath = doubanPath;
	}

	/**
	 * 获取豆瓣的位置
	 * @return
	 */
	public String getNewsPath() {
		return newsPath;
	}

	public void setNewsPath(String newsPath) {
		this.newsPath = newsPath;
	}

	/**
	 * 获取微博的位置
	 * @return
	 */
	public String getWeiboPath() {
		return weiboPath;
	}

	public void setWeiboPath(String weiboPath) {
		this.weiboPath = weiboPath;
	}

}
