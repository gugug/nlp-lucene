package cn.ssm.service.spider.baidunews;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
	public static void rwFile(String data, String filePath, String fileName) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		fileName = illegalSymbol(fileName);
		try {
			//是否需要创建文件夹  
            File saveDir = new File(filePath);    
            if(!saveDir.exists()){    
                saveDir.mkdir(); 
            }    
            File file = new File(saveDir+File.separator+fileName);     
			fw = new FileWriter(file);
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
	
	public static List<String> bufferReadTxt(String source) {
		FileReader fr = null;
		BufferedReader br = null;
//		Map<Integer,String> map = new HashMap<Integer,String>();
		List<String> mlist = new ArrayList<String>();

		try {
			File f = new File(source);
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String str;
			int count=1;
			while ((str = br.readLine()) != null) {
//				map.put(count++, str);
				mlist.add(str);
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
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mlist;
	}
	
	public static String illegalSymbol(String str) {
		if (str.contains("/") || str.contains("\\") || str.contains("?")
				|| str.contains(":") || str.contains("*") || str.contains("\"")
				|| str.contains("<") || str.contains(">") || str.contains("|")) {
			str = str.replace("/", " ");
			str = str.replace("\\", " ");
			str = str.replace("?", " ");
			str = str.replace(":", " ");
			str = str.replace("*", " ");
			str = str.replace("\"", " ");
			str = str.replace("<", " ");
			str = str.replace(">", " ");
			str = str.replace("|", " ");

			return str;
		} else {
			return str;
		}
	}

	
	public static void main(String[] args) {
		bufferReadTxt("./movie_list/movie_0.txt");
	}
}
