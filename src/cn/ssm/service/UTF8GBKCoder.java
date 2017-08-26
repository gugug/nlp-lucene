package cn.ssm.service;

public class UTF8GBKCoder {
	public static void main(String... args) throws Throwable {
		String errStr = "�?接近天堂的地�?";
		System.out.println(recover(errStr));
		}

		public static String recover(String str) throws Throwable {
		return new String(str.getBytes("gbk"), "utf-8");
		}


}
