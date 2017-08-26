package cn.ssm.po;

public class Douban {
	private String dId;
	private String dUrl;
	private String dContent;
	
	public Douban() {
		super();
	}

	public Douban(String dContent) {
		super();
		this.dContent = dContent;
	}

	public String getdId() {
		return dId;
	}

	public void setdId(String dId) {
		this.dId = dId;
	}

	public String getdUrl() {
		return dUrl;
	}

	public void setdUrl(String dUrl) {
		this.dUrl = dUrl;
	}

	public String getdContent() {
		return dContent;
	}

	public void setdContent(String dContent) {
		this.dContent = dContent;
	}
	
}
