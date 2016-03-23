package com.xingluo.util.http;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

/**
 * Http Utils
 * 
 * @author Mounate Yan。
 * @version 1.0
 */
public class HttpUtils {
//	public static final String BASE_ERP_URL = "http://api.map.baidu.com/geocoder/v2/?ak=pHHKZxqGRG9O0c0roshOCNAZ&callback=renderOption&output=json&address=建业路华翠街68号&city=广东省广州市天河区";
	private HttpClient httpClient = new HttpClient();
	private List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
	private List<Header> headers = new ArrayList<Header>();
	
	private HttpUtils (){}
	
	public static HttpUtils create(){
		return new HttpUtils();
	}
	
	public HttpUtils addParam(String name, String value){
		if(StringUtils.isNotEmpty(value)){
			this.valuePairs.add(new NameValuePair(name, value));
		}
		return this;
	}
	
	public HttpUtils addParam(String queryString){
		if (ParamUtils.notEmpty(queryString)) {
			Map<String, String> queryMap = ParamUtils.queryToMap(queryString);
			Set<Entry<String, String>> querySet = queryMap.entrySet();
			for(Entry<String, String> nameVal : querySet){
				this.addParam(nameVal.getKey(), nameVal.getValue());
			}
		}
		return this;
	}
	public HttpUtils addHeader(String name, String value){
		if(StringUtils.isNotEmpty(value)){
			this.headers.add(new Header(name, value));
		}
		return this;
	}
	
	public String post(String url){
		
		PostMethod pm = new PostMethod();
		try {
			pm.setURI(new URI(url, false, "GB2312"));
		} catch (URIException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		if(this.valuePairs.size() > 0){
			pm.addParameters(this.valuePairs.toArray(new NameValuePair[this.valuePairs.size()]));
		}
		if (this.headers.size() > 0) {
			for (Header header : this.headers) {
				pm.addRequestHeader(header);
			}
		}
		try {
			
			httpClient.executeMethod(pm);
			return pm.getResponseBodyAsString();
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public String update(String url){
		PostMethod pm = new PostMethod();
		try {
			pm.setURI(new URI(url, false, "GB2312"));
		} catch (URIException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		if(this.valuePairs.size() > 0){
			pm.addParameters(this.valuePairs.toArray(new NameValuePair[this.valuePairs.size()]));
		}
		if (this.headers.size() > 0) {
			for (Header header : this.headers) {
				pm.addRequestHeader(header);
			}
		}
		try {
			httpClient.executeMethod(pm);
			return pm.getResponseBodyAsString();
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public String get(String url){
		GetMethod gm = new GetMethod(url);
		if (this.headers.size() > 0) {
			for (Header header : this.headers) {
				gm.addRequestHeader(header);
			}
		}
		gm.setQueryString(this.valuePairs.toArray(new NameValuePair[this.valuePairs.size()]));
		try {
			httpClient.executeMethod(gm);
			return gm.getResponseBodyAsString();
		} catch (Exception e) {
			
		}
		return "";
	}
}
