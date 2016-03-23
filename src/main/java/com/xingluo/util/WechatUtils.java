package com.xingluo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecf.wechat.utils.CommonUtil;
import com.ecf.wechat.utils.MD5SignUtil;
import com.ecf.wechat.utils.SDKRuntimeException;
import com.xingluo.util.http.MetronicX509TrustManager;

public class WechatUtils {
	private static Logger log = LoggerFactory.getLogger(WechatUtils.class);
	public static final String gen_prepay_url ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String WECHAT_SIGN_TYPE = "sha1";
	
	/**
	 * App支付
	 */
	public static final String WECHAT_APP_ID = "wx14658f9874c6c7af";
	public static final String WECHAT_SECRET = "735b7e1dc089c732d249d799a14e14a9";
	public static final String PartnerKey = "1266652601";
	public static final String WECHAT_APP_PAY_SIGN_KEY = "Yunzhiya111666GG6197576V12NOMING";
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String request(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MetronicX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}

	private static HashMap<String, String> parameters = new HashMap<String, String>();
	
	/*
	public static String genPrepayId(Order order,String ip) throws SDKRuntimeException{
		parameters.clear();
		parameters.put("appid", WECHAT_APP_ID);
		parameters.put("body", order.getProduct().getName());
		parameters.put("mch_id", PartnerKey);
		parameters.put("out_trade_no", order.getSn());
		parameters.put("total_fee", order.getAmount().multiply(new BigDecimal(100)).intValue()+"");
		parameters.put("notify_url", SettingUtils.get().getSiteUrl().concat("/order/notify/"+order.getSn()+".json"));
		parameters.put("spbill_create_ip", ip);
		parameters.put("nonce_str", CommonUtil.CreateNoncestr());
		parameters.put("trade_type", "APP");
		parameters.put("sign", sign());
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<xml>");
		Set<String> objSet = parameters.keySet();
		for (Object key : objSet) {
			if (key == null) {
				continue;
			}
			strBuilder.append("<").append(key.toString()).append(">");
			Object value = parameters.get(key);
			strBuilder.append(value.toString());
			strBuilder.append("</").append(key.toString()).append(">");
		}
		strBuilder.append("</xml>");
		return request(gen_prepay_url, "POST",strBuilder.toString()).toString();
	}
	*/
	public static String sign() throws SDKRuntimeException{
		String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters, false);
		return MD5SignUtil.Sign(unSignParaString, WECHAT_APP_PAY_SIGN_KEY);
	}
	
	
	
}
