package com.lydb.controller.business;

import com.lydb.entity.db_coupon_business.DbCouponBusinessEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_img_url.DbImgUrlEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.service.db_coupon_business.DbCouponBusinessServiceI;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_web_business.DbWebBusinessServiceI;
import com.lydb.service.db_zcoupon_business.DbZcouponBusinessServiceI;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;
import com.sun.star.io.IOException;
import com.xingluo.qiniudemo.qiniuController;
import com.xingluo.util.CheckList;
import com.xingluo.util.Md5Util;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping("/storeBusiness")
public class storeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(storeController.class);

	@Autowired
	private DbZcouponBusinessServiceI dbZcouponBusinessService;
	
	@Autowired
	private DbZeroGoodsServiceI dbZeroGoodsService;
	
	@Autowired
	private DbGoodsServiceI dbGoodsService;
	
	@Autowired
	private DbCouponBusinessServiceI dbCouponBusinessService;
	
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
	
	String reqpath = "/webpage/business/";
	String resppath = "/webpage/";
	
	/*
	 * 获取系统当前时间
	 * 
	 * */
	public Date Time() throws ParseException{
		
		String ly_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatter.parse(ly_time);
		//System.out.println(date.getTime());
		
		//	Date date=sdf.parse(s);
		return (date);
	}
	/*
	 * 分页函数
	 * 
	 * */
	public double Pagelist(long n){
		double m=20;
		double k=(double)n;
		double p= Math.ceil(k/m);
		if(p<1.0){p=1;}
		return p;
		
	
	}
	
	/**
	 *用户主页左页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="index_left")
	public void index_left(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		
		 request.getRequestDispatcher(reqpath+"left.jsp").forward(request, response);
		
		}	
	
	
	/**
	 *用户个人信息页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="information")
   // @ResponseBody
	public void information(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		//	AjaxJson j = new AjaxJson();
			try {
				String sql="select name,mobile,business_qq,shopname from db_web_business where id = ?";

				List<Map<String,Object>> Business=dbWebBusinessService.findForJdbc(sql, request.getSession().getAttribute("id"));
				request.setAttribute("mobile", Business.get(0).get("mobile"));
				request.setAttribute("name", Business.get(0).get("name"));
				request.setAttribute("business_qq", Business.get(0).get("business_qq"));
				request.setAttribute("shopname", Business.get(0).get("shopname"));
				request.getRequestDispatcher(reqpath+"information.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("/webpage/error.jsp");
			}
		  /*  j.setObj(Business);
		    return j ;*/
	}	
	/**
	 *用户个人信息页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_pwd_page")
	public void change_pwd_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
			request.getRequestDispatcher(reqpath+"change_pwd_page.jsp").forward(request, response);
		   
	}
	
	/**
	 *用户修改密码接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_pwd")
    @ResponseBody
	public AjaxJson change_pwd(HttpServletRequest request,HttpServletResponse response,String old,String newone) throws ServletException, IOException, java.io.IOException{
		AjaxJson j=new AjaxJson();
		try {
			/*String sql="select password,id from db_web_business where id=?";
			List<Map<String,Object>> demo =dbWebBusinessService.findForJdbc(sql,request.getSession().getAttribute("id"));*/
		   DbWebBusinessEntity demo = systemService.findUniqueByProperty(DbWebBusinessEntity.class,"id",request.getSession().getAttribute("id"));
			old=Md5Util.EncoderByMd5(old);
			if(demo.getPassword().equals(old)){
				DbWebBusinessEntity en=	dbWebBusinessService.get(DbWebBusinessEntity.class,demo.getId());
				en.setPassword(Md5Util.EncoderByMd5(newone));
				dbWebBusinessService.saveOrUpdate(en);
				message="1";
			}else{
				message="您输入的密码有误";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("/webpage/error.jsp");
		}
		j.setObj(message);
		return j;
	}
	
	/**
	 *用户修改qq页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_qq_page")
	public void change_qq_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		DbWebBusinessEntity businessEntity=systemService.findUniqueByProperty(DbWebBusinessEntity.class, "id", request.getSession().getAttribute("id"));
		request.setAttribute("qq",businessEntity.getBusinessQq());
			request.getRequestDispatcher(reqpath+"change_qq_page.jsp").forward(request, response);
		    
	}	
	/**
	 *用户修改店名页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_shopname_page")
	public void change_shopname_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		DbWebBusinessEntity businessEntity=systemService.findUniqueByProperty(DbWebBusinessEntity.class, "id", request.getSession().getAttribute("id"));
		request.setAttribute("qq",businessEntity.getShopname());
			request.getRequestDispatcher(reqpath+"change_shopname_page.jsp").forward(request, response);
		    
	}
	/**
	 *用户修改呢称页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_name_page")
	public void change_name_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		DbWebBusinessEntity businessEntity=systemService.findUniqueByProperty(DbWebBusinessEntity.class, "id", request.getSession().getAttribute("id"));
		request.setAttribute("qq",businessEntity.getName());
		request.getRequestDispatcher(reqpath+"change_name_page.jsp").forward(request, response);
		    
	}
	/**
	 *用户修改参数接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="change_Parameter")
    @ResponseBody
	public AjaxJson change_Parameter(HttpServletRequest request,HttpServletResponse response,String name,String shopname,String business_qq) throws ServletException, IOException, java.io.IOException{
		AjaxJson j=new AjaxJson();
		try {
			/*String sql="select shopname,business_qq,name,id from db_web_business where id=?";
			List<Map<String,Object>> demo =dbWebBusinessService.findForJdbc(sql, request.getSession().getAttribute("id"));*/
			 DbWebBusinessEntity demo = systemService.findUniqueByProperty(DbWebBusinessEntity.class,"id",request.getSession().getAttribute("id"));
			DbWebBusinessEntity en=	dbWebBusinessService.get(DbWebBusinessEntity.class, demo.getId());
			
			if(name!=""){
				en.setName(name);
			}else if(shopname!=""){
				en.setShopname(shopname);
			}else if(business_qq!=""){
				en.setBusinessQq(business_qq);
			}
			dbWebBusinessService.saveOrUpdate(en);
			message="1";
		} catch (Exception e) {
			message="修改失败";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.setObj(message);
		return j;
	}
	
	/**
	 *用户发布普通商品页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_normal_page")
	public void publish_normal_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
			
		
		request.getRequestDispatcher(reqpath+"publish_normal_page.jsp").forward(request, response);
		    
	}
	/**
	 *用户发布0$商品页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zero_page")
	public void publish_zero_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
			request.getRequestDispatcher(reqpath+"publish_zero_page.jsp").forward(request, response);
		    
	}
	/**
	 *帮助页面请求
	 *何志颖
	 * @throws IOException
	 * @throws ServletException
	 * @throws java.io.IOException
	 * @throws java.io.IOException
	 */
	@RequestMapping(params="help")
	public String help(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		return "business/help";

	}
	/**
	 *用户发布0$商品页面接口
	 *何志颖
	 * @throws IOException
	 * @throws ServletException
	 * @throws java.io.IOException
	 * @throws java.io.IOException
	 */
	@RequestMapping(params="showMessage")
	public void showMessage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		request.setAttribute("message",message);
		request.getRequestDispatcher(reqpath+"showMessage").forward(request, response);
	}
	
	/**
	 *用户发布普通商品接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_normal")
    @ResponseBody
	public AjaxJson publish_normal(HttpServletRequest request,HttpServletResponse response,DbGoodsEntity en,String couponUrl,String couponValue) throws ServletException, IOException, java.io.IOException{
		AjaxJson j=new AjaxJson();
		String sql="select id,shopname from db_web_business where id=?";
		List<Map<String,Object>> demo =dbWebBusinessService.findForJdbc(sql,request.getSession().getAttribute("id"));
		String ser_num= "select  max(serial_num)  from  db_goods";
		List<Map<String,Object>> number=systemService.findForJdbc(ser_num, null);
		try {
			String id = en.getId();
		 	en.setCreateTime(Time());
		 	en.setStatus("1");
		 	if(number.get(0).get("max(serial_num)")==null){
		 		en.setSerialNum(1000);
		 	}else{
		 		en.setSerialNum(Integer.valueOf(number.get(0).get("max(serial_num)").toString())+1);
		 	}
			if(en.getGoods10()==null){
				en.setGoods10(0);
			}
		 	en.setDbWebBusinessid(demo.get(0).get("id").toString());
			en.setShareNum(0);
			en.setGoodsContent("");
			systemService.save(en);
		
			DbImgUrlEntity img=systemService.findUniqueByProperty(DbImgUrlEntity.class, "id", id);
			img.setDbShareGoodsid(en.getId());
			systemService.saveOrUpdate(img);
			message="1";
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="您申请出现了了状况请重新申请";	
			e.printStackTrace();
		} 
		try {
			if(couponUrl!=""&&couponValue!=""){
				DbCouponBusinessEntity coupon= new DbCouponBusinessEntity();
                coupon.setTime(DateUtils.getDate());
				if(couponUrl.contains("http")) {
					coupon.setCouponUrl(couponUrl);
				}else{
					coupon.setCouponUrl("http://"+couponUrl);
				}
				coupon.setCouponValue(Integer.valueOf(couponValue));
				coupon.setBusinessName(demo.get(0).get("shopname").toString());
				coupon.setDbGoodsid(en.getId());
				dbCouponBusinessService.save(coupon);
			}
		} catch (NumberFormatException e) {
			message="您申请商品成功，优惠券生成有问题";
			e.printStackTrace();
		}
		j.setObj(message);
		return j;
	}
	
	/**
	 *用户发布零元商品接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zero")
    @ResponseBody
	public AjaxJson publish_zero(HttpServletRequest request,HttpServletResponse response,DbZeroGoodsEntity en,String couponUrl,String couponValue) throws ServletException, IOException, java.io.IOException{
		AjaxJson j=new AjaxJson();
		String sql="select id,shopname from db_web_business where id=?";
		List<Map<String,Object>> demo =dbWebBusinessService.findForJdbc(sql, request.getSession().getAttribute("id"));
		String ser_num= "SELECT MAX(zserial_num) from db_zero_goods";
		List<Map<String,Object>> number=systemService.findForJdbc(ser_num, null);
		try {
			        String id=en.getId();
				 	en.setCreateTime(Time());
				 	en.setStatus("1");
				 	if(number.get(0).get("MAX(zserial_num)")==null){
				 		en.setZserialNum(1000);
				 	}else{
				 		en.setZserialNum(Integer.valueOf(number.get(0).get("MAX(zserial_num)").toString())+1);
				 	}
				 	en.setDbWebBusinessid(demo.get(0).get("id").toString());
				 	en.setZshareNum(0);
					en.setZgoodsContent("");
					en.setTime(1);
					systemService.save(en);		
					DbImgUrlEntity img=systemService.findUniqueByProperty(DbImgUrlEntity.class, "id", id);
					img.setDbShareGoodsid(en.getId());
					systemService.saveOrUpdate(img);
					message="1";
					try {
						if(couponUrl!=""&&couponValue!=""){
                            DbZcouponBusinessEntity coupon= new DbZcouponBusinessEntity();
                            coupon.setTime(DateUtils.getDate());
                            if(couponUrl.contains("http")) {
                                coupon.setCouponUrl(couponUrl);
                            }else{
                                coupon.setCouponUrl("http://"+couponUrl);
                            }
							coupon.setCouponUrl(couponUrl);
							coupon.setCouponValue(Integer.valueOf(couponValue));
							coupon.setBusinessName(demo.get(0).get("shopname").toString());
							coupon.setDbZeroGoodsid(en.getId());
							dbZcouponBusinessService.save(coupon);
						}
					} catch (NumberFormatException e) {
						message="您申请商品成功，优惠券生成有问题";
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					message="您申请出现了了状况请重新申请";
					e.printStackTrace();
				} 

		j.setObj(message);
		return j;
	}
	
	/**
	 *用户普通商品列表页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_not_ready_page")
	public void publish_not_ready_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		request.getRequestDispatcher(reqpath+"publish_not_ready.jsp").forward(request, response);

	}
	
	
	/**
	 *用户普通商品列表页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_not_ready_list")
	public void publish_not_ready_list(HttpServletRequest request,HttpServletResponse response,int pagenow,String whyl,String typel,String name) throws ServletException, IOException, java.io.IOException{
		String number="";
		if(whyl==null){
			whyl="0";
		}
		if(typel==null){
			typel="0";
		}
		int why=Integer.parseInt(whyl);
		int type=Integer.parseInt(typel);
		if(why==0&&type==0&&name==""){
			try {
				number="select count(id) from db_goods where status < 5 and db_web_businessid = ?"; 
				String notReady="select * from db_goods where status < 5 and db_web_businessid = ?  order by create_time DESC";
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady, pagenow, 20,request.getSession().getAttribute("id"));
				request.setAttribute("goodslist", goodsList);
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist",(int)Pagelist(l));
				request.setAttribute("pagenow", pagenow);
				request.getRequestDispatcher(reqpath+"publish_not_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}else if(why!=0){
			String notReady="";
			try {
				if(why==1){
					 notReady="select * from db_goods where status < 5 and status!=3 and db_web_businessid = ? order by goods_rmb DESC"; 	
				}else if(why==2){
					notReady="select * from db_goods where status < 5 and status!=3 and db_web_businessid = ? order by create_time DESC"; 	
				}else{
				 notReady="select * from db_goods where status < 5 and status!=3 and db_web_businessid = ? order by goods_num DESC"; 	
				}
				number="select count(id) from db_goods where status < 5 and status!=3 and db_web_businessid = ?"; 
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady, pagenow, 20,request.getSession().getAttribute("id"));
				request.setAttribute("goodslist", goodsList);
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsService.getCountForJdbcParam(number,array);
				
				request.setAttribute("pagelist",(int)Pagelist(l));
				request.setAttribute("pagenow", pagenow);
				request.getRequestDispatcher(reqpath+"publish_not_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}else if(type!=0){
			try {
				String notReady="select * from db_goods where status =? and db_web_businessid = ? order by create_time DESC"; 	
			    number="select count(id) from db_goods where status =? and db_web_businessid = ?"; 
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady, pagenow, 20,type,request.getSession().getAttribute("id"));
				request.setAttribute("type",type );
				request.setAttribute("goodslist", goodsList);
				Object[] array = new Object[2];
				array[1]=request.getSession().getAttribute("id");
				array[0]=type;
				long l=dbGoodsService.getCountForJdbcParam(number,array);
				
				request.setAttribute("pagelist",(int) Pagelist(l));
				request.setAttribute("pagenow", pagenow);
				request.getRequestDispatcher(reqpath+"publish_not_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}else if(name!=null){
			try {
				String namesql="select * from db_goods where status < 5  and db_web_businessid = ? and goods_name LIKE ?";
				 number="select count(id) from db_goods where status < 5  and db_web_businessid = ? and goods_name LIKE ?";
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(namesql,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("goodslist", goodsList);
				Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbGoodsService.getCountForJdbcParam(number,array);
				
				request.setAttribute("pagelist", (int)Pagelist(l));
				request.setAttribute("pagenow", pagenow);
				request.getRequestDispatcher(reqpath+"publish_not_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 *用户普通商品详情接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_not_ready_detail")

	public void publish_not_ready_detail(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{	
		try {
			DbGoodsEntity en=dbGoodsService.get(DbGoodsEntity.class, id);	
			/*String sql="select * from db_coupon_business where db_goodsid=?";
			List<Map<String,Object>> coupon=dbCouponBusinessService.findForJdbc(sql, id);*/
			DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", id);
			request.setAttribute("coupon", coupon);

			float money=(float)((en.getGoodsNum()*en.getGoodsRmb())*0.5);
 
			request.setAttribute("detail", en);
			if(en.getStatus().toString().equals("4")){
				money=(float)(en.getGoodsNum()*en.getGoodsRmb()*0.5);
				request.setAttribute("money", money);
				request.getRequestDispatcher(reqpath+"publish_not_ready_detail_tan4.jsp").forward(request, response);
			}else if(en.getStatus().toString().equals("2")){
				money=(float)(en.getGoodsNum()*en.getGoodsRmb()*0.5);
				request.setAttribute("id", id);
				request.setAttribute("money", money);
				request.getRequestDispatcher(reqpath+"publish_not_ready_detail_tan3.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher(reqpath+"publish_not_ready_detail_tan2.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.sendRedirect("/webpage/error.jsp");
			e.printStackTrace();
		}	
	  
	}
	/**
	 *用户普通商品详情接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="account_page")

	public void account_page(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{	
		
	    	request.getRequestDispatcher(reqpath+"account_page.jsp").forward(request, response);
	}
	/**
	 *用户普通商品详情接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="zero_account_page")

	public void zero_account_page(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{	
		
	    	request.getRequestDispatcher(reqpath+"zero_account_page.jsp").forward(request, response);
	}
	/**
	 *用户账户接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="account")

	public void account(HttpServletRequest request,HttpServletResponse response,int type,int order,String name,int pagenow) throws ServletException, IOException, java.io.IOException{	
		String sql = "select a.*,b.create_time,b.serial_num,b.goods_name from db_goods_payment As a inner join db_goods As b ON a.db_goodsid=b.id where b.db_web_businessid= ? && payment_status >0 ";
		String number="select count(b.id) from db_goods_payment As a inner join db_goods As b ON a.db_goodsid=b.id where b.db_web_businessid=? && payment_status >0";
		List<Map<String, Object>> en = new ArrayList<Map<String, Object>>();
	
		 if(type!=0){
			 sql=sql+" && a.payment_status = ? ";
			 number=number+" && a.payment_status = ? ";
			 en=dbGoodsService.findForJdbcParam(sql,pagenow,20,request.getSession().getAttribute("id"),type);
			request.setAttribute("paylist",en);
			Object[] array = new Object[2];
			array[0]=request.getSession().getAttribute("id");
			array[1]=type;
			long l=dbGoodsService.getCountForJdbcParam(number,array);
			request.setAttribute("pagelist", (int)Pagelist(l));
			request.setAttribute("pagenow", pagenow);
	  }	else{
				if(type==0&&order==0&&name==null){
					     en=dbGoodsService.findForJdbcParam(sql,pagenow,20, request.getSession().getAttribute("id"));
					     request.setAttribute("paylist",en);
						Object[] array = new Object[1];
						array[0]=request.getSession().getAttribute("id");
						long l=dbGoodsService.getCountForJdbcParam(number,array);
						
						request.setAttribute("pagelist", (int)Pagelist(l));
						request.setAttribute("pagenow", pagenow);
				}else if(order!=0){
		            if(order==1){
		            	
					     sql=sql+" order by b.create_time DESC";
					
					     en=dbGoodsService.findForJdbcParam(sql,pagenow,20,request.getSession().getAttribute("id"));
				
					}else{
							sql=sql+" order by a.deposit DESC";
						en=dbGoodsService.findForJdbcParam(sql,pagenow,20,request.getSession().getAttribute("id"));
					}
		            request.setAttribute("paylist",en);
					Object[] array = new Object[1];
					array[0]=request.getSession().getAttribute("id");
					long l=dbGoodsService.getCountForJdbcParam(number,array);
					
					request.setAttribute("pagelist", (int)Pagelist(l));
					request.setAttribute("pagenow", pagenow);
				} 
				if(name!=null){
				
						sql=sql + " && b.goods_name LIKE ?";
					
						number=number +"&& b.goods_name LIKE ?";
						en=dbGoodsService.findForJdbcParam(sql,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");	
							request.setAttribute("paylist",en);
							Object[] array = new Object[2];
							array[1]="%"+name+"%";
							array[0]=request.getSession().getAttribute("id");
							long l=dbGoodsService.getCountForJdbcParam(number,array);
							
							request.setAttribute("pagelist", (int)Pagelist(l));
							request.setAttribute("pagenow", pagenow);
				}
	  }
		request.getRequestDispatcher(reqpath+"account.jsp").forward(request, response);
	}
	/**
	 *用户账户接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="zero_account")

	public void zero_account(HttpServletRequest request,HttpServletResponse response,int type,int order,String name,int pagenow) throws ServletException, IOException, java.io.IOException{	
		
		String sqlzero="select a.*,b.create_time,b.zserial_num,b.zgoods_name from db_zgoods_payment As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where b.db_web_businessid= ? && payment_status >0";
		String numberzero="select count(b.id) from db_zgoods_payment As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where b.db_web_businessid= ? && payment_status >0";
	
		 
	
		 if(type!=0){
			
			 sqlzero=sqlzero+" && a.payment_status = ?";
			 numberzero=numberzero+" && a.payment_status = ?";
			 List<Map<String, Object>>  zero=dbZeroGoodsService.findForJdbcParam(sqlzero, pagenow,20,request.getSession().getAttribute("id"),type);
			 request.setAttribute("payzero", zero);
			Object[] array2 = new Object[2];
			array2[0]=request.getSession().getAttribute("id");
			array2[1]=type;
			long k=dbZeroGoodsService.getCountForJdbcParam(numberzero,array2);
			request.setAttribute("pagelist", (int)Pagelist(k));
			request.setAttribute("pagenow", pagenow);
	  }	else{
				if(type==0&&order==0&&name==null){
					List<Map<String, Object>>	 zero=dbZeroGoodsService.findForJdbcParam(sqlzero,pagenow,20,request.getSession().getAttribute("id"));
					 request.setAttribute("payzero", zero);
					 Object[] array2 = new Object[1];
						array2[0]=request.getSession().getAttribute("id");
						long k=dbZeroGoodsService.getCountForJdbcParam(numberzero,array2);
						request.setAttribute("pagelist", (int)Pagelist(k));
						request.setAttribute("pagenow", pagenow);
				}else if(order!=0){
		            if(order==1){				  
					     sqlzero=sqlzero+" order by b.create_time DESC";		  
					     List<Map<String, Object>>	 zero=dbZeroGoodsService.findForJdbcParam(sqlzero, pagenow,20,request.getSession().getAttribute("id"));
					     request.setAttribute("payzero", zero);
					     Object[] array2 = new Object[1];
							array2[0]=request.getSession().getAttribute("id");
							long k=dbZeroGoodsService.getCountForJdbcParam(numberzero,array2);
							request.setAttribute("pagelist", (int)Pagelist(k));
							request.setAttribute("pagenow", pagenow);
					}else{
						sqlzero=sqlzero+" order by a.deposit DESC";				
						List<Map<String, Object>> zero=dbZeroGoodsService.findForJdbcParam(sqlzero, pagenow,20,request.getSession().getAttribute("id"));
						 request.setAttribute("payzero", zero);
						 Object[] array2 = new Object[1];
							array2[0]=request.getSession().getAttribute("id");
							long k=dbZeroGoodsService.getCountForJdbcParam(numberzero,array2);
							request.setAttribute("pagelist", (int)Pagelist(k));
							request.setAttribute("pagenow", pagenow);
					}
				}
				if(name!=null){

						sqlzero=sqlzero + " && b.zgoods_name like ?";
						numberzero=numberzero +" && b.zgoods_name like ?";	
						List<Map<String, Object>>zero=dbZeroGoodsService.findForJdbcParam(sqlzero, pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
					
				
						request.setAttribute("payzero", zero);
						Object[] array2 = new Object[2];
						array2[0]=request.getSession().getAttribute("id");
						array2[1]="%"+name+"%";
						long k=dbZeroGoodsService.getCountForJdbcParam(numberzero,array2);
						
						request.setAttribute("pagelist", (int)Pagelist(k));
						request.setAttribute("pagenow", pagenow);
			}
	  }
		request.getRequestDispatcher(reqpath+"zero_account.jsp").forward(request, response);
	}
	
	
/*
 * 详情图片上传
 * */
	
	@RequestMapping(params = "publish_detail_jpg" , method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson doAdPicture(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		qiniuController qiniu = ApplicationContextUtil.getContext().getBean(qiniuController.class);
		try {
			j = qiniu.updateQiniu(request, response);
			String url =((List<String>)j.getObj()).get(0);
			//更新到数据库中
			DbImgUrlEntity img=new DbImgUrlEntity();
			img.setImgUrl(url);
			systemService.save(img);
			j.setMsg("图上传成功");
			j.setObj(img.getId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			j.setMsg("上传失败！");
			j.setSuccess(false);
			return j;
		}
		return j;
	}



	/*
 * 详情图片上传
 * */

	@RequestMapping(params = "addfirstjpg" , method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson doAdfirstPicture(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		qiniuController qiniu = ApplicationContextUtil.getContext().getBean(qiniuController.class);
		try {
			j = qiniu.updateQiniu(request, response);
			String url =((List<String>)j.getObj()).get(0);
			//更新到数据库中
			j.setMsg("图上传成功");
			j.setObj(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			j.setMsg("上传失败！");
			j.setSuccess(false);
			return j;
		}
		return j;
	}
}

