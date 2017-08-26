package cn.ssm.po;

import java.util.List;

public class BaiduBaike {
	private String bId;
	private String bUrl;
	private String bContent;
	private List<String> imgList;
	
	public BaiduBaike() {
		super();
	}

	public BaiduBaike(String bContent) {
		super();
		this.bContent = bContent;
	}



	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getbUrl() {
		return bUrl;
	}

	public void setbUrl(String bUrl) {
		this.bUrl = bUrl;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

}
