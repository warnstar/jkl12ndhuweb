package com.lydb.controller.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.service.db_zcoupon_business.DbZcouponBusinessServiceI;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;
import com.sun.star.io.IOException;



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
@RequestMapping("/zeroBusiness")
public class zeroController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(zeroController.class);

	
	@Autowired
	private DbZcouponBusinessServiceI dbZcouponBusinessService;
	@Autowired
	private DbZeroGoodsServiceI dbZeroGoodsService;

	

	
	
	
	
    private String message;
	
	 public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	String reqpath = "/webpage/business/";
	String resppath = "/webpage/business/";
	
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
	public double PageList(long n){
		
		double m=20;
		double k=(double)n;
		double p= Math.ceil(k/m);
		if(p<1.0){p=1;}
		return p;
	
	}
	
	/**
	 *用户普通商品列表页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zeronot_ready_page")
	public void publish_zeronot_ready_page(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, java.io.IOException{
		request.getRequestDispatcher(reqpath+"publish_zeronot_ready.jsp").forward(request, response);
	}
	
	
	/**
	 *用户普通商品列表页面接口
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zeronot_ready_list")

	public void publish_zeronot_ready_list(HttpServletRequest request,HttpServletResponse response,int pagenow,String whyl,String typel,String name) throws ServletException, IOException, java.io.IOException{
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
				String number="select count(id) from db_zero_goods where status < 5 and db_web_businessid = ?"; 
				String notReady="select * from db_zero_goods where status < 5 and db_web_businessid = ? order by create_time DESC";
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady,pagenow, 20, request.getSession().getAttribute("id"));
  
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbZeroGoodsService.getCountForJdbcParam(number,array);
				request.setAttribute("pagenow", pagenow);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("goodslist", goodsList);
				request.getRequestDispatcher(reqpath+"publish_zeronot_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(why!=0){
			String notReady="";
			if(why==1){
				 notReady="select * from db_zero_goods where status < 5 and status!=3 and db_web_businessid = ? order by zgoods_rmb DESC"; 	
			}else if(why==2){
				notReady="select * from db_zero_goods where status < 5 and status!=3 and db_web_businessid = ? order by create_time DESC"; 	
			}else{
			 notReady="select * from db_zero_goods where status < 5 and status!=3 and db_web_businessid = ? order by zgoods_num DESC"; 	
			}
			String number="select count(id) from db_zero_goods where status < 5 and status!=3 and db_web_businessid = ?"; 
			try {
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady, pagenow, 20,request.getSession().getAttribute("id"));
				
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbZeroGoodsService.getCountForJdbcParam(number,array);
				
				request.setAttribute("why", why);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("goodslist", goodsList);
				request.setAttribute("pagenow", pagenow);
				request.getRequestDispatcher(reqpath+"publish_zeronot_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}else if(type!=0){
			String notReady="select * from db_zero_goods where status =? and db_web_businessid = ? order by create_time DESC"; 	
			String number="select count(id) from db_zero_goods where status =? and db_web_businessid = ?"; 
			try {
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady, pagenow, 20,type,request.getSession().getAttribute("id"));
   
				Object[] array = new Object[2];
				array[1]=request.getSession().getAttribute("id");
				array[0]=type;
				
				long l=dbZeroGoodsService.getCountForJdbcParam(number,array);
				
				request.setAttribute("type",type );
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				request.setAttribute("goodslist", goodsList);
				request.getRequestDispatcher(reqpath+"publish_zeronot_ready_list.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("/webpage/error.jsp");
				e.printStackTrace();
			}
		}else if(name!=null){
			String namesql="select * from db_zero_goods where status < 5  and db_web_businessid = ? and zgoods_name LIKE ? order by create_time DESC";
			String number="select count(id) from db_zero_goods where status < 5  and db_web_businessid = ? and goods_name LIKE ?";
			List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(namesql,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
		    
		    Object[] array = new Object[2];
			array[0]=request.getSession().getAttribute("id");
			array[1]="%"+name+"%";
			long l=dbZeroGoodsService.getCountForJdbcParam(number,array);
		    request.setAttribute("pagelist", (int)PageList(l));
		    request.setAttribute("goodslist", goodsList);
			request.getRequestDispatcher(reqpath+"publish_zeronot_ready_list.jsp").forward(request, response);
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
	@RequestMapping(params="publish_zeronot_ready_detail")

	public void publish_zeronot_ready_detail(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{	
		DbZeroGoodsEntity en=dbZeroGoodsService.get(DbZeroGoodsEntity.class, id);	
	   /* String sql="select * from db_zcoupon_business where db_zero_goodsid= ?";
	    List<Map<String,Object>> coupon=dbZcouponBusinessService.findForJdbc(sql,id);*/
		DbZcouponBusinessEntity coupon = this.dbZcouponBusinessService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid",id);
		   request.setAttribute("coupon", coupon);


	    float money=(float)((en.getZgoodsNum()*en.getZgoodsRmb())*0.5);	 
	    request.setAttribute("detail", en);
	    if(en.getStatus().toString().equals("4")){
	    	request.setAttribute("money", money);
	    	request.getRequestDispatcher(reqpath+"publish_zeronot_ready_detail_tan4.jsp").forward(request, response);
	    }else if(en.getStatus().toString().equals("2")){
	    	request.setAttribute("money", money);
	    	request.getRequestDispatcher(reqpath+"publish_zeronot_ready_detail_tan3.jsp").forward(request, response);
	    }else{
	    	request.getRequestDispatcher(reqpath+"publish_zeronot_ready_detail_tan2.jsp").forward(request, response);
	    }	
	  
	}
	
	
}

