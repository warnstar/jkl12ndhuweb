package com.xingluo.util;

import java.util.List;
import java.util.Map;

public class CheckList {
	public static boolean CheckNull(List<Map<String,Object>> list) { 
		//不为空 flasse，为空返回ture
		for(Map<String,Object> m : list){
			for(Object value : m.values()){
				if(value!=null){
					return false;
				}
			}
		}
		return true;	
	}
}
