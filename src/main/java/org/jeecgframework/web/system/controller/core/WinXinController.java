package org.jeecgframework.web.system.controller.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.xingluo.wxdemo.AdvancedUtil;
import com.xingluo.wxdemo.Userinfo;
import com.xingluo.wxdemo.WeiXinOauth2Token;

/**
 * 处理微信请求控制器demo
 * @author fy
 *
 */

@Controller
@RequestMapping(value = "/WinXinController")
public class WinXinController {
	
	@Autowired
	private SystemService systemService;
	
	/*
	 * 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 */
	private String redirect_uri=AdvancedUtil.urlEncodeUTF8("http://home.xingl123.com/xingluo/WinXinController.do?check");
	
	@RequestMapping(params = "weixin")
	@ResponseBody
	public void weixin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	/**
	 * 第一步：用户同意授权，获取code
	 *在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
	 *默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开如下页面：
	 *https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=
	 *REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 *若提示“该链接无法访问”，请检查参数是否填写错误，是否拥有scope参数对应的授权作用域权限。
	 */
		String demo="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6a6d55a7a9ed926e&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		response.sendRedirect(demo);
	}
	
	/**
	 * 重定向的回调链接控制器，所有对用户信息操作的业务逻辑写这里
	 * 方宇
	 */
	@RequestMapping(params = "check")
	@ResponseBody
	public Userinfo check(String code,HttpServletRequest request){
	       String openId ="";
	       String access_token="";
	       Userinfo ui=null;
	       
          if (!"authdeny".equals(code)) {
      
                   WeiXinOauth2Token weiXinOauth2Token = AdvancedUtil
                                   .getOauth2AccessToken("wx6a6d55a7a9ed926e",
                                                   "0fd4969d4787460d3463bc3d0d6e5400", code);
                   openId = weiXinOauth2Token.getOpenId();
                   access_token=weiXinOauth2Token.getAccessToken();
                    ui=AdvancedUtil.getuserinfo(access_token, openId);
           }
         // request.getSession().setAttribute("openId", openId);
           
           return ui;
	}

}
