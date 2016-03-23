package com.xingluo.wxdemo;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

public class AdvancedUtil {
	private static final Log log =LogFactory.getLog(AdvancedUtil.class);
	/**
	 * 第一步在org.jeecgframework.web.system.controller.core控制器WinXinController中
	 * 强制让用户访问微信授权接口，不授权不然访问
	 */

	/**
	 * 
	 * 第二步：通过code换取网页授权access_token
	 *首先请注意，这里通过code换取的网页授权access_token,与基础支持中的access_token不同。\
	 *公众号可通过下述接口来获取网页授权access_token。如果网页授权的作用域为snsapi_base，
	 *则本步骤中获取到网页授权access_token的同时，也获取到了openid，snsapi_base式的网页授权流程即到此为止。
		 
	 *
	 *需要参数
	 *	参数		是否必须	说明
		appid		是	公众号的唯一标识
		secret		是	公众号的appsecret
		code		是	填写第一步获取的code参数
		grant_type	是	填写为authorization_code
		
		返回 参数
		参数						描述
		access_token		网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
		expires_in			access_token接口调用凭证超时时间，单位（秒）
		refresh_token		用户刷新access_token
		openid				用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
		scope				用户授权的作用域，使用逗号（,）分隔
		unionid				只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
	private static String oauth2Url= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public static String urlEncodeUTF8(String source){
        String result = source;
        try {
                result = java.net.URLEncoder.encode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }
        return result;
}
	
	public static WeiXinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeiXinOauth2Token wat = new WeiXinOauth2Token();
        String requestUrl = oauth2Url.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
        JSONObject jsonObject = CommonUtil
                        .httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
                try {
                        wat = new WeiXinOauth2Token();
                        wat.setAccessToken(jsonObject.getString("access_token"));
                        wat.setExpiresIn(jsonObject.getInt("expires_in"));
                        wat.setRefeshToken(jsonObject.getString("refresh_token"));
                        wat.setOpenId(jsonObject.getString("openid"));
                        wat.setScope(jsonObject.getString("scope"));
                } catch (Exception e) {
                        wat = null;
                        String errorCode = jsonObject.getString("errcode");
                        String errorMsg = jsonObject.getString("errmsg");
                        log.error("获取网页授权凭证失败 "+errorCode+","+errorMsg);
                }

        }
        return wat;
		}
		/**
		 * 第三步：刷新access_token（如果需要）
		 *由于access_token拥有较短的有效期，当access_token超时后，
		 *可以使用refresh_token进行刷新，refresh_token拥有较长的有效期（7天、30天、60天、90天），
		 *当refresh_token失效的后，需要用户重新授权。
		 *
			参数	是否必须	说明
			appid			是	公众号的唯一标识
			grant_type		是	填写为refresh_token
			refresh_token	是	填写通过access_token获取到的refresh_token参数
			
			参数	描述
			access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
			expires_in		access_token接口调用凭证超时时间，单位（秒）
			refresh_token	用户刷新access_token
			openid			用户唯一标识
			scope			用户授权的作用域，使用逗号（,）分隔

		 */
		private static String refresh_token=" https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		//如果需要则实现
	
		
		
		
		/**
		 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
		 *如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
		 *
		 *参数				描述
		access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
		openid			用户的唯一标识
		lang			返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
		
		参数						描述
			openid				用户的唯一标识
			nickname			用户昵称
			sex					用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
			province			用户个人资料填写的省份
			city				普通用户个人资料填写的城市
			country				国家，如中国为CN
			headimgurl			用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
			privilege			用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
			unionid				只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
		
		 */
	
		private static String getuserinfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		
		public static Userinfo getuserinfo(String access_token,String openid){
			Userinfo ui=null;
			String requestUrl =getuserinfoUrl.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
			 JSONObject jsonObject = CommonUtil
             .httpsRequest(requestUrl, "GET", null);
			 if (null != jsonObject) {
	                try {
	                        ui = new Userinfo();
	                        ui.setOpenid(jsonObject.getString("openid"));
	                        ui.setCity(jsonObject.getString("city"));
	                        ui.setCountry(jsonObject.getString("country"));
	                        ui.setHeadimgurl(jsonObject.getString("headimgurl"));
	                        ui.setNickname(jsonObject.getString("nickname"));
	                        ui.setPrivilege(jsonObject.getString("privilege"));
	                        ui.setProvince(jsonObject.getString("province"));
	                        ui.setSex(jsonObject.getString("sex"));
	                        
	                } catch (Exception e) {
	                        ui = null;
	                        String errorCode = jsonObject.getString("errcode");
	                        String errorMsg = jsonObject.getString("errmsg");
	                        log.error("获取用户信息失败 "+errorCode+","+errorMsg);
	                }

	        }
			return ui;
		}
}
