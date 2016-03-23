package com.xingluo.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileService {
	// String 真实路径 D:\\.....
	// String 文件名 aa.txt
	public Map<String, String> queryAll(String dirPath) {
		//
		Map<String, String> map = new HashMap<String, String>();
		// 保存目录
		File dir = new File(dirPath);
		String[] paths = dir.list();
		String value = "";
		if(paths!=null){
			for (String item : paths) {
				value = item.substring(item.lastIndexOf(".") + 1);
				item=item.substring(0, item.lastIndexOf("."));
				map.put(item, value);
			}
		}
		return map;
	}
}
