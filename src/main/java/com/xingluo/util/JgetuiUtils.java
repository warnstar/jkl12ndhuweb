package com.xingluo.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 个推
 * 
 */
public class JgetuiUtils {

	private static String appId = "uPjAMSjgrW5FNR5KYyqRn3";
	private static String appKey = "PBTemuwAyM7sf9J59QGXB8";
	//private static String appSecret = "pECecj0zM98d756g78xLF";
	private static String masterSecret = "WMHhJekPLb6fU8xd9V1Yz8";
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	public enum Device {
		ios,
		
		android
		
	}
	
	
	/**
	 * 单用户推送
	 * @param device
	 * @param clientId
	 */
	public static void pushSingleMessage(Device device,String clientOrToken,String content){
		IGtPush push = new IGtPush(host,appKey, masterSecret);
		
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		
		if (device.equals(Device.ios)) {
			APNPayload apnpayload = new APNPayload();
			SimpleAlertMsg alertMsg = new SimpleAlertMsg("Tips");
			apnpayload.setAlertMsg(alertMsg);
			apnpayload.setBadge(5);
			apnpayload.setContentAvailable(1);
			apnpayload.setCategory("ACTIONABLE");
			template.setAPNInfo(apnpayload);
		}
		
		SingleMessage message = new SingleMessage();
		message.setData(template);
		message.setOffline(true);
		message.setOfflineExpireTime(24 * 1000 * 3600);
		
		if (device.equals(Device.ios)) {
			IPushResult ret = push.pushAPNMessageToSingle(appId, clientOrToken, message);
			System.out.println(ret.getResponse().toString());
		}else{
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(clientOrToken);
			IPushResult ret = push.pushMessageToSingle(message, target);
			System.out.println(ret.getResponse().toString());
		}
		
	}
	
	/**
	 * 单用户推送
	 * @param device
	 * @param clientId
	 */
	public static void pushListMessage(Device device,String... clientId){
		IGtPush push = new IGtPush(host,appKey, masterSecret);
		
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(1);
		template.setTransmissionContent("os-toApp");
		
		if (device.equals(Device.ios)) {
			APNPayload apnpayload = new APNPayload();
			SimpleAlertMsg alertMsg = new SimpleAlertMsg("Tips");
			apnpayload.setAlertMsg(alertMsg);
			apnpayload.setBadge(5);
			apnpayload.setContentAvailable(1);
			apnpayload.setCategory("ACTIONABLE");
			template.setAPNInfo(apnpayload);
		}
		
		SingleMessage message = new SingleMessage();
		message.setData(template);
		message.setOffline(true);
		message.setOfflineExpireTime(24 * 1000 * 3600);
		
		Target target = new Target();
		target.setAppId(appId);
//		target.setClientId(clientId);
//		IPushResult ret = push.pu
//		System.out.println(ret.getResponse().toString());
	}
}
