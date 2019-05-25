package cn.todev.examples.source;

import java.util.HashMap;
import java.util.Map;

public class HashMapSource {

	private static Map<Integer,Integer> container = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=0;i< 100;i++) {
			container.put(i,i);
			
			((Object)1).hashCode();
		}
		
		System.out.println(container.toString());
		System.out.println(((Object)2).hashCode());
		System.out.println(1 ^ (1 >>> 16));
	}

}
