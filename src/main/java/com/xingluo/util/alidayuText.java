package com.xingluo.util;

import net.sf.json.JSONObject;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class alidayuText {
	public static void Text(String phone,String code){
		String url="http://gw.api.taobao.com/router/rest";
		String appkey="23301967";
		String secret="9c9ad6cd6108388a66aabe900a9ba360";
		try {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("product","一圆梦平台");
			req.setExtend("123456");
			req.setSmsType("normal");
			req.setSmsFreeSignName("注册验证");
			req.setSmsParamString(obj.toString());
			req.setRecNum(phone);
			req.setSmsTemplateCode("SMS_4725856");
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		//	System.out.println(rsp.getBody());
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void TextMessage(String phone,String code){
		String url="http://gw.api.taobao.com/router/rest";
		String appkey="23301967";
		String secret="9c9ad6cd6108388a66aabe900a9ba360";
		try {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("product","一圆梦平台");
			req.setExtend("123456");
			req.setSmsType("normal");
			req.setSmsFreeSignName("变更验证");
			req.setSmsParamString(obj.toString());
			req.setRecNum(phone);
			req.setSmsTemplateCode("SMS_4725854");
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		//	System.out.println(rsp.getBody());
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
