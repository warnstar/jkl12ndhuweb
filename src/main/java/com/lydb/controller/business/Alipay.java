package com.lydb.controller.business;

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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_goods_payment.DbGoodsPaymentServiceI;
import com.lydb.service.db_web_business.DbWebBusinessServiceI;
import com.xingluo.alipay.config.AlipayConfig;
import com.xingluo.alipay.util.AlipayNotify;
import com.xingluo.alipay.util.AlipaySubmit;
import com.xingluo.alipay.util.Constants;
import com.xingluo.alipay.util.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝Controller
 * 用来解决支付宝的后台
 * @author fy
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value="/app_lydb/alipayBusiness")
public class Alipay extends BaseController{
	private static final Logger logger = Logger.getLogger(Alipay.class);
	@Autowired
	private DbGoodsServiceI dbGoodsService;
	@Autowired
	private DbGoodsPaymentServiceI dbGoodsPaymentService;
	
	@Autowired
	private DbWebBusinessServiceI dbWebBusinessService;
	
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@RequestMapping(value = "/deposit")
    public String deposit(HttpServletRequest request,HttpServletResponse response,String id) throws Exception { 
//    	PrintWriter out = response.getWriter();
     //   Date date = new Date();  
        DbGoodsEntity en =dbGoodsService.getEntity(DbGoodsEntity.class,id);
		DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"dbGoodsid" , id);
		storeController store=new storeController();
		pay.setStarttime(store.Time()); 
		pay.setOrderNum(UUIDGenerator.generate());

		dbGoodsPaymentService.saveOrUpdate(pay);
        // 支付类型  
        // 必填，不能修改  
        String payment_type = "1";  
        // 服务器异步通知页面路径  
        // 需http://格式的完整路径，不能加?id=123这类自定义参数  
        String notify_url = "http://dbback.stzero.cn/xingluo/rest/app_lydb/alipayBusiness/async";
        // 页面跳转同步通知页面路径  
        // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/  
        String return_url = "http://dbback.stzero.cn/xingluo/rest/app_lydb/alipayBusiness/returnurl";
        // 商户订单号.  
        // 商户网站订单系统中唯一订单号，必填  
        //String out_trade_no = date.getTime() + "";  
        // 订单名称  
        // 必填  
        String subject = en.getGoodsName();
        // 防钓鱼时间戳  
        // 若要使用请调用类文件submit中的query_timestamp函数  
        String anti_phishing_key = "";  
        // 客户端的IP地址  
        // 非局域网的外网IP地址，如：221.0.0.1  
        String exter_invoke_ip = "";     
        String body = ServletRequestUtils.getStringParameter(request, "body","保证金支付");
		//商品展示地址
		String show_url = ServletRequestUtils.getStringParameter(request, "show_url","");
		//需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");//接口服务----即时到账
		sParaTemp.put("partner", AlipayConfig.partner);//支付宝PID
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);//统一编码
		sParaTemp.put("payment_type", payment_type);//支付类型
		sParaTemp.put("notify_url", notify_url);//异步通知页面
		sParaTemp.put("return_url", return_url);//页面跳转同步通知页面
		sParaTemp.put("seller_email", Constants.SELLER_EMAIL);//卖家支付宝账号
		sParaTemp.put("out_trade_no", pay.getOrderNum());//商品订单编号
		sParaTemp.put("subject", subject);//商品名称
		sParaTemp.put("total_fee","0.01");//价格
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
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(value ="/returnurl")
	@ResponseBody
    public String  Return_url(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		 /*Map<String,String> params = new HashMap<String,String>();
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
	            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {  */
	            	 request.getRequestDispatcher("/webpage/business/loginTimeOut2.jsp").forward(request, response);
	             /*   System.out.println(">>>>>充值成功" + tradeNo);
	            }  
	            *//*return "web/pay/success";  *//*
	        }else{//验证失败  
	           *//* return "web/pay/fail";  *//*
				System.out.println(">>>>>充值失败" );

		request.getRequestDispatcher("/webpage/business/error.jsp").forward(request, response);
	        }  */
		return "success";
    }
    /**
     * 异步通知付款状态的Controller
     * @param request
     * @param response
     * @return
     * @author 宗潇帅
     * @throws IOException 
     * @throws ServletException 
     * @throws ParseException 
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/async")
    @ResponseBody
	public String async(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, ParseException{
		Date date = new Date();  
		String success="success";
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
	            	//   System.out.println(">>>>>充值成功" + tradeNo);
					DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"orderNum" ,tradeNo);
					DbGoodsEntity goods=systemService.findUniqueByProperty( DbGoodsEntity.class,"id" ,pay.getDbGoodsid());
					if(goods.getStatus().equals("5")){
						return "success";
					}
					try {
						pay.setPaymentStatus(1);
						dbGoodsPaymentService.saveOrUpdate(pay);
						goods.setStatus("4");
						dbGoodsService.saveOrUpdate(goods);
						DbWebBusinessEntity business=systemService.findUniqueByProperty(DbWebBusinessEntity.class,"id",goods.getDbWebBusinessid());
						AccountLogEntity acc =new AccountLogEntity();
						acc.setAccountType(1);
						acc.setBusinessMobile(business.getMobile());
						acc.setDbWebBusinessid(business.getId());
						acc.setOrderNum(tradeNo);
						acc.setContent(goods.getSerialNum().toString()+goods.getGoodsName()+goods.getGoodsRmb().toString()+goods.getGoodsNum().toString());
						acc.setCreateTime(DateUtils.getDate());
						acc.setRmb(pay.getDeposit()+pay.getServicefee());
						systemService.save(acc);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						return success;
					}
				}
	            return success;
	           /* return "web/pay/success";*/  
	        }else{//验证失败  
	        	DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"orderNum" ,tradeNo);
	        	logger.info(DateUtils.getDate().toString()+"商品编号为"+pay.getDbGoodsid().toString()+"的产品付款不成功");
	        	return "fail";
	        	/*return "web/pay/fail"; */ 
	        }  
		
	}
}
