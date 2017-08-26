package cn.ssm.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIndex {
	
	public Map<String,List<String>> fileIndex(){
		Map<String, List<String>> indexMap = new HashMap<String, List<String>>();
		ArrayList<String> doubanList = new ArrayList<String>();
		ArrayList<String> weiboList = new ArrayList<String>();
		ArrayList<String> newsList = new ArrayList<String>();
		ArrayList<String> baidubaikeList = new ArrayList<String>();
		
		doubanList.add(".\\DataDocuments\\doubanData\\苏乞儿.txt");
		weiboList.add(".\\DataDocuments\\weiboData\\苏乞儿\\M_EmftgytYD.txt");
		weiboList.add(".\\DataDocuments\\weiboData\\苏乞儿\\M_EmxYJvHNk.txt");
		newsList.add(".\\DataDocuments\\newsData\\苏乞儿\\王美英加盟《苏乞儿》 “千面女侠”侠肝义胆.txt");
		newsList.add(".\\DataDocuments\\newsData\\苏乞儿\\醉侠苏乞儿.txt");
		baidubaikeList.add(".\\DataDocuments\\baidubaikeData\\苏乞儿\\基本信息.txt");
		
		indexMap.put("baidubaikeData",baidubaikeList);
		indexMap.put("doubanData",doubanList);
		indexMap.put("newsData",newsList);
		indexMap.put("weiboData",weiboList);
		return indexMap;
	}

}
