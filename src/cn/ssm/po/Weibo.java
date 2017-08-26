package cn.ssm.po;

public class Weibo {
	private String wName;
	private String wId;
	private String wContent;
	private String wLike;
	private String wForward;
	private String wComment;
	
	public Weibo() {
		super();
	}

	public Weibo(String wContent) {
		super();
		this.wContent = wContent;
	}

	public String getwName() {
		return wName;
	}

	public void setwName(String wName) {
		this.wName = wName;
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public String getwContent() {
		return wContent;
	}

	public void setwContent(String wContent) {
		this.wContent = wContent;
	}

	public String getwLike() {
		return wLike;
	}

	public void setwLike(String wLike) {
		this.wLike = wLike;
	}

	public String getwForward() {
		return wForward;
	}

	public void setwForward(String wForward) {
		this.wForward = wForward;
	}

	public String getwComment() {
		return wComment;
	}

	public void setwComment(String wComment) {
		this.wComment = wComment;
	}


}
