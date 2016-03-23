package com.lydb.controller.app;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lydb.controller.business.storeController;
import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.xingluo.alipay.util.AlipayNotify;
import com.xingluo.wxpay.api.PayUtils;
import com.xingluo.wxpay.bean.PayCallbackNotify;


/**
 * app端  支付宝，微信，京东  异步通知交易完成的接口
 * @author fy
 *
 */

@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/commitRMB")
public class refillRmb extends BaseController {
	private static final Logger logger = Logger.getLogger(refillRmb.class);
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 支付宝异步通知接口
	 * 前端app完成支付后，除了同步通知，还需要异步通知后台，用来做该用户充值的凭证
	 */
	@RequestMapping(value="AlipayAsync")
	@ResponseBody
	public String AlipayAsync(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, ParseException{ 
		storeController store=new storeController();
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
	        String totalFee = request.getParameter("total_fee");
	        
	        //String notifyId = request.getParameter("notify_id");  
	        if(AlipayNotify.verify(params)){//验证成功  
	            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {  
	            	//   System.out.println(">>>>>充值成功" + tradeNo);  
	            	
	            	//保存支付成功的信息
	            	AccountLogEntity acc = new AccountLogEntity();
	            	acc.setAccountType(1);
	            	acc.setBusinessMobile("");
	            	acc.setContent("App用户支付宝充值！");
	            	acc.setCreateTime(DateUtils.getDate());
	            	acc.setOrderNum(tradeNo);
	            	acc.setRmb(Double.valueOf(totalFee));
	            	systemService.save(acc);
	            	
	            	
	            	return "success";
	            }  
	           /* return "web/pay/success";*/  
	        }else{//验证失败  
	        	logger.info(store.Time().toString()+"商品编号为"+"的产品付款不成功");
	        	
	        	return "fail";
	        	/*return "web/pay/fail"; */ 
	        }  
		return null;
	}
	
	/**
	 * 微信支付成功后的异步回调接口
	 * @throws IOException 
	 */
	@RequestMapping(value="WxpayAsync")
	@ResponseBody
	public String WxpayAsync(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		PayCallbackNotify payCallbackNotify = PayUtils.payCallbackNotify(request.getInputStream());
		if(payCallbackNotify.isPaySuccess()){
			
			//保存支付成功的信息
        	AccountLogEntity acc = new AccountLogEntity();
        	acc.setAccountType(1);
        	acc.setBusinessMobile("");
        	acc.setContent("App用户微信充值！");
        	acc.setCreateTime(DateUtils.getDate());
        	acc.setOrderNum(payCallbackNotify.getOut_trade_no());
        	acc.setRmb(Double.valueOf(payCallbackNotify.getTotal_fee()));
        	systemService.save(acc);
			
		    String content = PayUtils.generatePaySuccessReplyXML();
		    return content;
		}else{
			return "fail";
		}
	}
	
	
}
