package com.xingluo.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.xingluo.util.http.HttpUtils;

/**
 * Utils - Map
 * 
 * @author Mounate Yan。
 * @version 1.0
 */
public class MapUtils {
	public static final String LOCATION_POINT_LATITUDE = "latitude";
	public static final String LOCATION_POINT_LONGITUDE = "longitude";
	
	private static String baiduAK =  "";
	
	private final static double EARTH_RADIUS = 6378.137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算地图两个坐标的距离
	 * 
	 * 计算有误，在下一个升级版本中将采用百度WEB API进行计算两点之间的距离。
	 * @return
	 */
	@Deprecated
	public static double getDistance(double lata, double lnga, double latb, double lngb) {
		double radlata = rad(lata);
		double radlatb = rad(latb);
		double a = radlata - radlatb;
		double b = rad(lnga) - rad(lngb);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radlata) * Math.cos(radlatb)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	/**
	 * 利用百度地图将地址转换为坐标
	 * @param address
	 * @return
	 */
	public static Map<String, Double> address2location(String address){
		try {
			 Map<String, Double> locationMap = new HashMap<String, Double>();
			 String result =  HttpUtils.create().addParam("ak", baiduAK).
			 	addParam("output", "json").addParam("address", address).get("http://api.map.baidu.com/geocoder/v2/");
			 if(JSONObject.fromObject(result).getInt("status") == 1){
				 return null;
			 }
			 JSONObject location = JSONObject.fromObject(result).getJSONObject("result").getJSONObject("location");
			 locationMap.put(LOCATION_POINT_LONGITUDE, location.getDouble("lng"));
			 locationMap.put(LOCATION_POINT_LATITUDE, location.getDouble("lat"));
			 return locationMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
