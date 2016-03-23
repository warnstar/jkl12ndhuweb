package com.xingluo.util;

import java.util.List;
import java.util.Map;

/**
 *用于rest类型的json数据封装标准
 *参考与陌途，可以后修改
 * @author fy
 *
 */
public class RestJson {
	/**
	 * 状态码 	说明
		0 	操作成功
		3 	要操作的对象不存在
		4 	要插入的数据已存在
		5 	传入的某个参数不能为空
		6	 数据库操作失败
		7 	未知错误
		8 	传入的参数错误
		403	无权限
	 */
	private int code = 0;
	
	private String token ;// 其他信息
	
	private  Object  info ; //放入一些对象信息
	
	private List<Map<String,Object>> attributes; // 其他参数
	
	private String error; //错误信息
	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Map<String, Object>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Map<String, Object>> attributes) {
		this.attributes = attributes;
	}
	
	
}
