package cn.ssm.po;

public class News {
	private String nId;
	private String nUrl;
	private String nTitle;
	private String nMedia;
	private String nTime;
	private String nSummary;
	private String nContent;

	public News() {
		super();
	}

	
	/**
	 * 这个是用来做搜索不到结果的异常处理使用
	 * @param nSummary
	 */
	public News(String nSummary) {
		super();
		this.nSummary = nSummary;
	}



	public String getnId() {
		return nId;
	}

	public void setnId(String nId) {
		this.nId = nId;
	}

	public String getnUrl() {
		return nUrl;
	}

	public void setnUrl(String nUrl) {
		this.nUrl = nUrl;
	}

	public String getnTitle() {
		return nTitle;
	}

	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}

	public String getnMedia() {
		return nMedia;
	}

	public void setnMedia(String nMedia) {
		this.nMedia = nMedia;
	}

	public String getnTime() {
		return nTime;
	}

	public void setnTime(String nTime) {
		this.nTime = nTime;
	}

	public String getnSummary() {
		return nSummary;
	}

	public void setnSummary(String nSummary) {
		this.nSummary = nSummary;
	}

	public String getnContent() {
		return nContent;
	}

	public void setnContent(String nContent) {
		this.nContent = nContent;
	}

}
