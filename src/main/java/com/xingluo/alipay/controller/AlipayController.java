package com.xingluo.alipay.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xingluo.alipay.config.AlipayConfig;
import com.xingluo.alipay.util.AlipayNotify;
import com.xingluo.alipay.util.AlipaySubmit;
import com.xingluo.alipay.util.Constants;
import com.xingluo.alipay.util.StringUtil;



/**
 * 支付宝Controller
 * 用来解决支付宝的后台
 * @author fy
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/aliapi")
public class AlipayController extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(AlipayController.class);
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response){
				return "webapge/alipay/index";
	}
	
	@RequestMapping(value = "/deposit", method = RequestMethod.POST,produces = "application/json")  
    public String deposit(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception { 
//    	PrintWriter out = response.getWriter();
        Date date = new Date();  
        // 支付类型  
        // 必填，不能修改  
        String payment_type = "1";  
        // 服务器异步通知页面路径  
        // 需http://格式的完整路径，不能加?id=123这类自定义参数  
        String notify_url = "http://192.168.0.222:8080/live/aliapi/async";  
        // 页面跳转同步通知页面路径  
        // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/  
        String return_url = "http://192.168.0.222:8080/live/aliapi/return_url";  
        // 商户订单号.  
        // 商户网站订单系统中唯一订单号，必填  
        //String out_trade_no = date.getTime() + "";  
        // 订单名称  
        // 必填  
        String subject = "充值测试";  
        // 防钓鱼时间戳  
        // 若要使用请调用类文件submit中的query_timestamp函数  
        String anti_phishing_key = "";  
        // 客户端的IP地址  
        // 非局域网的外网IP地址，如：221.0.0.1  
        String exter_invoke_ip = "";  
          
          
     
        String body = ServletRequestUtils.getStringParameter(request, "body","test");
		//商品展示地址
		String show_url = ServletRequestUtils.getStringParameter(request, "show_url","http://www.elve.cn");
		//需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

		
		
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");//接口服务----即时到账
		sParaTemp.put("partner", AlipayConfig.partner);//支付宝PID
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);//统一编码
		sParaTemp.put("payment_type", payment_type);//支付类型
		sParaTemp.put("notify_url", notify_url);//异步通知页面
		sParaTemp.put("return_url", return_url);//页面跳转同步通知页面
		sParaTemp.put("seller_email", Constants.SELLER_EMAIL);//卖家支付宝账号
		sParaTemp.put("out_trade_no", date.getTime()+payment_type);//商品订单编号
		sParaTemp.put("subject", subject);//商品名称
		sParaTemp.put("total_fee", "0.01");//价格
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
         
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");  
		StringUtil.writeToWeb(sHtmlText, "html", response);
		return null;
    }
	
	/**
	 * 同步通知的页面的Controller
	 * @param request
	 * @param response
	 * @return
	 * @author 宗潇帅
	 */
	@RequestMapping(value="/return_url")
    public String Return_url(HttpServletRequest request,HttpServletResponse response){
		 Map<String,String> params = new HashMap<String,String>();  
	        Map requestParams = request.getParameterMap();  
	        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {  
	            String name = (String) iter.next();  
	            String[] values = (String[]) requestParams.get(name);  
	            String valueStr = "";  
	            for (int i = 0; i < values.length; i++) {  
	                valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";  
	            }  
	            params.put(name, valueStr);  
	        }  
	        String tradeNo = request.getParameter("out_trade_no");  
	        String tradeStatus = request.getParameter("trade_status");  
	        //String notifyId = request.getParameter("notify_id");  
	        if(AlipayNotify.verify(params)){//验证成功  
	            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {  
	            	//要写的逻辑。自己按自己的要求写
	            	log.error("ok.......");
	                System.out.println(">>>>>充值成功" + tradeNo);  
	            }  
	            return "web/pay/success";  
	        }else{//验证失败  
	            return "web/pay/fail";  
	        }  
    }
    /**
     * 异步通知付款状态的Controller
     * @param request
     * @param response
     * @return
     * @author 宗潇帅
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/async")
	public String async(HttpServletRequest request,
			HttpServletResponse response){
		 Map<String,String> params = new HashMap<String,String>();  
	        Map requestParams = request.getParameterMap();  
	        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {  
	            String name = (String) iter.next();  
	            String[] values = (String[]) requestParams.get(name);  
	            String valueStr = "";  
	            for (int i = 0; i < values.length; i++) {  
	                valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";  
	            }  
	            params.put(name, valueStr);  
	        }  
	        String tradeNo = request.getParameter("out_trade_no");  
	        String tradeStatus = request.getParameter("trade_status");  
	        //String notifyId = request.getParameter("notify_id");  
	        if(AlipayNotify.verify(params)){//验证成功  
	            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {  
	            	//要写的逻辑。自己按自己的要求写
	            	log.error("ok.......");
	                System.out.println(">>>>>充值成功" + tradeNo);  
	            }  
	            return "web/pay/success";  
	        }else{//验证失败  
	            return "web/pay/fail";  
	        }  
		
	}
}
