package cn.ssm.service.spider.baidunews;

public class NewsBaidu {
	private String moviceName;
	private String newsUrl;
	private String newsTitle;
	private String newsMedia;
	private String releaseTime;
	private String newsContent;
	private String newsSummary;
	
	public NewsBaidu() {
		super();
	}
	
	public NewsBaidu(String newsUrl, String newsTitle, String newsMedia,
			String releaseTime, String newsSummary) {
		super();
		this.newsUrl = newsUrl;
		this.newsTitle = newsTitle;
		this.newsMedia = newsMedia;
		this.releaseTime = releaseTime;
		this.newsSummary = newsSummary;
	}

	

	public String getMoviceName() {
		return moviceName;
	}

	public void setMoviceName(String moviceName) {
		this.moviceName = moviceName;
	}

	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsMedia() {
		return newsMedia;
	}
	public void setNewsMedia(String newsMedia) {
		this.newsMedia = newsMedia;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public String getNewsSummary() {
		return newsSummary;
	}
	public void setNewsSummary(String newsSummary) {
		this.newsSummary = newsSummary;
	}
	

}
