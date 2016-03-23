package com.lydb.controller.business;

import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.service.db_web_business.DbWebBusinessServiceI;
import com.sun.star.io.IOException;
import com.xingluo.util.Md5Util;
import com.xingluo.util.alidayuText;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 商家功能模块
 * @author 何志颖
 * @date 2015-11-25 10:27:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/storeLoginBusiness")
public class storeLoginController extends BaseController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(DbWebBusinessController.class);
	

	@Autowired
	private DbWebBusinessServiceI dbWebBusinessService;
	
	@Autowired
	private SystemService systemService;
	
	String reqpath = "/webpage/business/";
	String resppath = "/lydb/webpage/business/";
	/*
	 * 短信代码生成函数
	 * */
	public static String starcode() {
		   String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7",  
		  "8", "9" };  
		     List list = Arrays.asList(beforeShuffle);  
		     Collections.shuffle(list);  
		     StringBuilder sb = new StringBuilder();  
		     for (int i = 0; i < list.size(); i++) {  
		         sb.append(list.get(i));  
		     }  
		     String afterShuffle = sb.toString();  
		     String result = afterShuffle.substring(5, 9);  
		    return result;
	}
	/**
	 *用户登录页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="login_page")
	public String login_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		return "business/login";
	}

	/**
	 *用户注测页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="register_page")
	public String register_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		return "business/register";
	}
	/**
	 *用户忘记密码页面接口
	 *何志颖
	 * @throws IOException
	 * @throws ServletException
	 * @throws java.io.IOException
	 */
	@RequestMapping(params="resetpassword_page")
	public String resetpassword_page() throws ServletException, IOException, java.io.IOException{
		return "business/resetpassword";
	}

/**
 *用户注测接口
 *何志颖
 * @throws IOException 
 * @throws ServletException 
 * @throws java.io.IOException 
 * @throws ParseException 
 */
@RequestMapping(params="registerbusiness")
@ResponseBody
public AjaxJson registerbusiness(HttpServletRequest request,DbWebBusinessEntity en,String code) throws ServletException, IOException, java.io.IOException, ParseException{
	AjaxJson j=new AjaxJson();
	String message="1";
	if(code.equals(request.getSession().getAttribute(en.getMobile().toString()))){
		DbWebBusinessEntity business=systemService.findUniqueByProperty(DbWebBusinessEntity.class,"mobile",en.getMobile()); 
		 String sqlnum="select max(the_number)  from  db_web_business";
		 List<Map<String,Object>> list=systemService.findForJdbc(sqlnum, null);
		 if(business!=null){
			 message="此手机号已注册";
		 }else{
			 UUID uuid = UUID.randomUUID();
			 try {
				 if(list.get(0).get("max(the_number)")==null){
					en.setTheNumber(476001);
				 }else{
				 	en.setTheNumber(Integer.valueOf(list.get(0).get("max(the_number)").toString())+1);
				 }
				 	en.setPassword(Md5Util.EncoderByMd5(en.getPassword()));
					en.setCreateTime(new java.util.Date(System.currentTimeMillis()));
					en.setStatus("0");
				 	en.setId(uuid.toString());
				 	systemService.save(en);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					message="您申请出现了了状况请重新申请";
					e.printStackTrace();
				} 
		 }
	}else{
		message="您的验证码错误请重新获取";
	
	}
	  j.setObj(message);
	  return  j;
	}



/**
 *用户登录接口
 *何志颖
 * @throws IOException 
 * @throws ServletException 
 * @throws java.io.IOException 
 * @throws ParseException 
 */
@RequestMapping(params="login")
@ResponseBody
public AjaxJson login(HttpServletRequest request,HttpServletResponse response,String name,String pwd) throws ServletException, IOException, java.io.IOException, ParseException{
	 AjaxJson j = new AjaxJson();
	 String message="";
	 pwd=Md5Util.EncoderByMd5(pwd);
	 try {
		String sql="select count(mobile),status,id from db_web_business where '1'='1' and mobile = ? and password = ?;";
		
		 List<Map<String,Object>> demo = systemService.findForJdbc(sql,name,pwd);
		 
		 if(Integer.valueOf(demo.get(0).get("count(mobile)").toString())==1&&demo.get(0).get("status").toString().equals("1"))
		 {
			 request.getSession().setAttribute("id", demo.get(0).get("id").toString());
			 message="truecheck";
		 }else{
			 message="flasecheck";
		 }
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		response.sendRedirect("/webpage/error.jsp");
		e.printStackTrace();
	}	
	 j.setObj(message);
	 return j;
	}

/*
* 重置密码
*
*
*
* */

	@RequestMapping(params="resetpass")
	@ResponseBody
	public AjaxJson resetpass(HttpServletRequest request,String mobile,String password,String code) throws ServletException, IOException, java.io.IOException, ParseException{
		AjaxJson j=new AjaxJson();
		String message="1";
		System.out.println(code);
		System.out.println(request.getSession().getAttribute(mobile));
		if(code.equals(request.getSession().getAttribute(mobile))){
			DbWebBusinessEntity business=systemService.findUniqueByProperty(DbWebBusinessEntity.class,"mobile",mobile);
			if(business!=null){
				business.setPassword(Md5Util.EncoderByMd5(password));
				systemService.saveOrUpdate(business);
				j.setSuccess(true);
			}else{
				message="此手机号尚未注册";
				j.setSuccess(false);
			}
		}else{
			message="您的验证码错误请重新获取";
			j.setSuccess(false);
		}
		j.setMsg(message);
		return  j;
	}
/**
 *用户登录接口
 *何志颖
 * @throws IOException 
 * @throws ServletException 
 * @throws java.io.IOException 
 */
@RequestMapping(params="loginout")
public void loginout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{

	request.getSession().removeAttribute("id");
	request.getRequestDispatcher(reqpath+"login.jsp").forward(request, response);
  		
}
	/**
	 *用户主页页面接口
	 *何志颖
	 * @throws IOException
	 * @throws ServletException
	 * @throws java.io.IOException
	 * @throws java.io.IOException
	 */
	@RequestMapping(params="index")
	public void index(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{

	request.getRequestDispatcher(reqpath+"index.jsp").forward(request, response);
	
	}
	/**
	 *用户短信验证接口
	 *何志颖
	 * @throws IOException
	 * @throws ServletException
	 * @throws java.io.IOException
	 * @throws ParseException
	 */
	@RequestMapping(params="sendcode")
	@ResponseBody
	public AjaxJson sendcode(HttpServletRequest request,String mobile) throws ServletException, IOException, java.io.IOException, ParseException{
		AjaxJson j=new AjaxJson();
		String code=starcode();
		try {
			alidayuText.Text(mobile, code);
			request.getSession().setAttribute(mobile, code);
			j.setObj(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			j.setObj(false);
			e.printStackTrace();
		}

		return j;
	}

}
