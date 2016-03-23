package com.xingluo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class QimenUtils {
	


	private QimenUtils() {
	}

	public static String sign(String url, String body, String secretKey) {

		Map<String, String> params = getParamsFromUrl(url);

		// 1. 第一步，确保参数已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// 2. 第二步，把所有参数名和参数值拼接在一起(包含body体)
		String joinedParams = joinRequestParams(params, body, secretKey, keys);

		// your_secretKeyapp_keyyour_appkeycustomerIdyour_customerIdformatxmlmethodyour_methodsign_methodmd5timestamp2015-04-26
		// 00:00:07vyour_versionyour_bodyyour_secretKey
		System.out.println(joinedParams);

		// 3. 第三步，使用加密算法进行加密（目前仅支持md5算法）
		String signMethod = params.get("sign_method");
		if (!"md5".equalsIgnoreCase(signMethod)) {
			// TODO
			return null;
		}
		byte[] abstractMesaage = digest(joinedParams);

		// 4. 把二进制转换成大写的十六进制
		String sign = byte2Hex(abstractMesaage);

		return sign;

	}

	private static Map<String, String> getParamsFromUrl(String url) {
		Map<String, String> requestParams = new HashMap<String, String>();
		try {
			String fullUrl = URLDecoder.decode(url, "UTF-8");
			String[] urls = fullUrl.split("\\?");
			if (urls.length == 2) {
				String[] paramArray = urls[1].split("&");
				for (String param : paramArray) {
					String[] params = param.split("=");
					if (params.length == 2) {
						requestParams.put(params[0], params[1]);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO
		}
		return requestParams;
	}

	private static String byte2Hex(byte[] bytes) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int j = bytes.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (byte byte0 : bytes) {
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	private static byte[] digest(String message) {
		try {
			MessageDigest md5Instance = MessageDigest.getInstance("MD5");
			md5Instance.update(message.getBytes("UTF-8"));
			return md5Instance.digest();
		} catch (UnsupportedEncodingException e) {
			// TODO
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO
			return null;
		}
	}

	private static String joinRequestParams(Map<String, String> params, String body,
			String secretKey, String[] sortedKes) {
		StringBuilder sb = new StringBuilder(secretKey); // 前面加上secretKey

		for (String key : sortedKes) {
			if ("sign".equals(key)) {
				continue; // 签名时不计算sign本身
			} else {
				String value = params.get(key);
				if (isNotEmpty(key) && isNotEmpty(value)) {
					sb.append(key).append(value);
				}
			}
		}
		sb.append(body); // 拼接body体
		sb.append(secretKey); // 最后加上secretKey
		return sb.toString();
	}

	private static boolean isNotEmpty(String s) {
		return null != s && !"".equals(s);
	}
	
	
	@SuppressWarnings("deprecation")
	public static String send(String url, String body) throws Exception {
		HttpClient httpClient = new HttpClient();
		PostMethod pm = new PostMethod();
		try {
			pm.setURI(new URI(url, false, "GB2312"));
		} catch (URIException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		RequestEntity entity = new StringRequestEntity(body);
		pm.setRequestEntity(entity);
		httpClient.executeMethod(pm);
		String resEntityStr =  pm.getResponseBodyAsString();
		System.out.println(resEntityStr);
		return new String(resEntityStr.getBytes("ISO-8859-1"), "UTF-8");
	}


}
