package cn.ssm.service;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
	    List<Integer> list = new ArrayList<Integer>();
	    list.add(1);
	    list.add(2);
	    list.add(3);
	    list.add(3);
	    list.add(4);
	         
	    for (int i=0; i<list.size(); i++) {
	        if (list.get(i) == 3) {
	            list.remove(i--);//这样写不对，需要写成list.remove(i--) 
	        }
	    }
	         
	    System.out.println(list);
	}
}
