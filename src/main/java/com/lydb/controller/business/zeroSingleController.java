package com.lydb.controller.business;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.lydb.service.db_coupon_business.DbCouponBusinessServiceI;
import com.lydb.service.db_zcoupon_business.DbZcouponBusinessServiceI;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;
import com.lydb.service.db_zgoods_single.DbZgoodsSingleServiceI;
import com.sun.star.io.IOException;
import com.xingluo.util.JpushUtils;



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
@RequestMapping("/zeroSingleBusiness")
public class zeroSingleController extends BaseController {
	 /* Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(zeroSingleController.class);


	@Autowired
	private DbZgoodsSingleServiceI dbZgoodsSingleService;
	
	@Autowired
	private DbZcouponBusinessServiceI dbZcouponBusinessService;
	
	@Autowired
	private DbZeroGoodsServiceI dbZeroGoodsService;
	
	@Autowired
	private DbCouponBusinessServiceI dbCouponBusinessService;
	
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
	String resppath = "/webpage/business/";
	
	public double PageList(long n){
		double m=20;
		double k=(double)n;
		double p= Math.ceil(k/m);
		if(p<1.0){p=1;}
		return p;
	}
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zerogoods_notopen")
	public void publish_zerogoods_notopen(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select b.zserial_num,b.create_time,b.zgoods_name,b.zgoods_rmb,b.zgoods_num,a.goods_current_num,a.current_join_num,a.id from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =1 && b.db_web_businessid = ? ";
		String number="select count(b.id) from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =0 && b.db_web_businessid = ? &&b.status<8 && b.status>5";
        if(name!=null){
		   notReady=notReady+" && b.zgoods_name LIKE ?  order by b.create_time DESC";
		   number=number+" && b.zgoods_name LIKE ?";
		   Object[] array = new Object[2];
			array[0]=request.getSession().getAttribute("id");
			array[1]="%"+name+"%";
			long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
			request.setAttribute("pagelist", (int)PageList(l));
			request.setAttribute("pagenow", pagenow);
			List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
			request.setAttribute("goodslist", goodsList);
			request.setAttribute("pagenow", pagenow);
			request.setAttribute("name", name);
		}else {
	        if(search!=null){
				if(Integer.parseInt(search)==1){
					notReady=notReady+" order by b.create_time DESC";
				}else if(Integer.parseInt(search)==2){
					notReady=notReady+" order by b.zgoods_rmb DESC";
				}else if(Integer.parseInt(search)==3){
					notReady=notReady+" order by b.zgoods_num DESC";
				}
			}else{
				search="0";
				notReady=notReady+" order by b.create_time DESC";
			}
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"));
				request.setAttribute("goodslist", goodsList);
				request.setAttribute("pagenow", pagenow);
				request.setAttribute("se", search);
			}
			request.getRequestDispatcher(reqpath+"publish_zerogoods_notopen.jsp").forward(request, response);
		
		
	}
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zerogoods_notsend")
	public void publish_zerogoods_notsend(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select b.zserial_num,b.create_time,b.zgoods_name,b.zgoods_rmb,b.zgoods_num,a.goods_current_num,a.id from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =2 && b.db_web_businessid = ?  && b.status>4"; 
		String number="select count(b.id) from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =2 && b.db_web_businessid = ?  && b.status>4";
		 if(name!=null){
			   notReady=notReady+" && b.zgoods_name LIKE ?   order by b.create_time DESC";
			   number=number+" && b.zgoods_name LIKE ?";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("notsendList", goodsList);
				request.setAttribute("name", name);
			}else {
		        if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by b.create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by b.zgoods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by b.zgoods_num DESC";
					}
				}else{
					search="0";
					notReady=notReady+" order by b.create_time DESC";
				}
				
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"));
				request.setAttribute("notsendList", goodsList);
				request.setAttribute("se", search);
			}
			request.getRequestDispatcher(reqpath+"publish_zerogoods_notsend.jsp").forward(request, response);
		
		
	}
	@RequestMapping(params="publish_zerogoods_send")
	public void publish_zerogoods_send(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select b.zshare_num,b.create_time,b.zgoods_name,b.zgoods_rmb,b.zgoods_num,a.goods_current_num,b.id from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =3 && b.db_web_businessid = ? && b.status>4"; 
		String number="select count(b.id) from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.status =3 && b.db_web_businessid = ?  && b.status>4";
		if(name!=null){
			   notReady=notReady+" && b.zgoods_name LIKE ?   order by b.create_time DESC";
			   number=number+" && b.zgoods_name LIKE ?";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("sendList", goodsList);
				request.setAttribute("name", name);
			}else {
		        if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by b.create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by b.zgoods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by b.zgoods_num DESC";
					}
				}else{
					search="0";
					notReady=notReady+" order by b.create_time DESC";
				}
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist",(int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"));
				request.setAttribute("sendList", goodsList);
				request.setAttribute("se", search);
			}
			request.getRequestDispatcher(reqpath+"publish_zerogoods_send.jsp").forward(request, response);
		
		
	}
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zerogoods_Ready")
	public void publish_zerogoods_Ready(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select create_time,zgoods_rmb,zgoods_num,zgoods_name,id from db_zero_goods where  status <8 && status >6 && db_web_businessid = ?"; 
		String number="select count(id) from db_zero_goods where  status <8 && status >6 && db_web_businessid = ?";
		if(name!=null){
			   notReady=notReady+" && zgoods_name LIKE ?  order by create_time DESC";
			   number=number+" && zgoods_name LIKE ?";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("name", name);
			}else {
		        if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by zgoods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by zgoods_num DESC";
					}
				}else{
					search="0";
					notReady=notReady+" order by create_time DESC";
				}
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"));
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("se", search);
			}
			request.getRequestDispatcher(reqpath+"publish_zerogoods_Ready.jsp").forward(request, response);
		
		
	}
	/**
	 *用户已完成商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zerogoods_finish")
	public void publish_zerogoods_finish(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select create_time,zgoods_rmb,zgoods_num,zgoods_name,id from db_zero_goods where  status = 8 && db_web_businessid = ?"; 
		String number="select count(id) from db_zero_goods where  status <8 && status >6 && db_web_businessid = ?";
		if(name!=null){
			   notReady=notReady+" && zgoods_name LIKE ?   order by create_time DESC";
			   number=number+" && zgoods_name LIKE '%"+name+"%'";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("name", name);
			}else{
		        if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by zgoods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by zgoods_num DESC";
					}
				}else{
					search="0";
					notReady=notReady+" order by create_time DESC";
				}
				Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				
				long l=dbZgoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow", pagenow);
				List<Map<String,Object>> goodsList=dbZeroGoodsService.findForJdbcParam(notReady ,pagenow,20,request.getSession().getAttribute("id"));
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("se", search);
			}
			request.getRequestDispatcher(reqpath+"publish_zerogoods_Ready.jsp").forward(request, response);
		
		
	}
	
	/**
	 *用户已上架商品列表未开奖商品详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zeroready_detailnotopen")
	public void publish_zeroready_detailnotopen(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		String detailsql="select b.*,a.goods_current_num,a.current_join_num from db_zgoods_single As a inner join db_zero_goods As b ON a.db_zero_goodsid=b.id where a.id= ?";
		try {
			List<Map<String,Object>> detail=dbZeroGoodsService.findForJdbc(detailsql, id);
		/*	String  couponsql="select * from db_zcoupon_business where db_zero_goodsid=?";
			List<Map<String,Object>> coupon=dbZcouponBusinessService.findForJdbc(couponsql, detail.get(0).get("id"));*/
			DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid",detail.get(0).get("id"));
			float money=(float)((Integer.parseInt(detail.get(0).get("zgoods_num").toString())*Integer.parseInt(detail.get(0).get("zgoods_rmb").toString()))*0.5);
			request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", zcoupon);
			request.getRequestDispatcher(reqpath+"publish_zeroready_detail_tan2.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("/webpage/error.jsp");
			e.printStackTrace();
		}
	}
	/**
	 *用户已上架商品列表未发货商品详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zeroready_detailnotsend")
	public void publish_zeroready_detailnotsend(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
			/*AjaxJson j=new AjaxJson();
			String sqlentity="select * from db_zgoods_single where id = ?";
			List<Map<String,Object>> single=systemService.findForJdbc(sqlentity, id);*/
			DbZgoodsSingleEntity single=systemService.getEntity(DbZgoodsSingleEntity.class, id);
			String detailsql="select * from db_zero_goods where id= ?";
			List<Map<String,Object>> detail=systemService.findForJdbc(detailsql, single.getDbZeroGoodsid());
			/*String  couponsql="select * from db_zcoupon_business where db_zero_goodsid= ?";
			List<Map<String,Object>> coupon=systemService.findForJdbc(couponsql, detail.get(0).get("id"));*/
			DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid",detail.get(0).get("id"));
			String sqlclient="select a.lucky_id,b.client_qq,b.mobile,c.ship_address,c.ship_phone from (db_zgoods_single as a left join db_app_client as b on b.id=a.db_app_clientid) left join db_ship_address as c on c.db_app_clientid=b.id WHERE a.id=?";
			List<Map<String,Object>> user=systemService.findForJdbc(sqlclient, id);
			float money=(float)((Integer.parseInt(detail.get(0).get("zgoods_num").toString())*Integer.parseInt(detail.get(0).get("zgoods_rmb").toString()))*0.5);
			request.setAttribute("number", single.getGoodsCurrentNum());
			request.setAttribute("id",id );
			request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", zcoupon);
			request.setAttribute("userlist", user);
			request.getRequestDispatcher(reqpath+"publish_zeroready_detail_tan3.jsp").forward(request, response);
		/*	j.setObj(single);
		return j;*/
	}

	/**
	 *用户已上架商品列表未发货商品详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_zeroready_detailsend")
	public void publish_zeroready_detailsend(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		
	    try {
			DbZeroGoodsEntity detail=dbZeroGoodsService.get(DbZeroGoodsEntity.class, id);
			/*String  couponsql="select * from db_zcoupon_business where db_zero_goodsid= ?";
			List<Map<String,Object>> coupon=dbCouponBusinessService.findForJdbc(couponsql, id);*/
			DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid",id);
			String sqlclient="select a.share,a.lucky_id,a.id,b.mobile,a.goods_current_num from db_zgoods_single As a inner join db_app_client As b ON a.db_app_clientid=b.id where a.db_zero_goodsid = ?";
			List<Map<String,Object>> listUser=dbZgoodsSingleService.findForJdbc(sqlclient, id);
			  float money=(float)((Integer.parseInt(detail.getZgoodsNum().toString())*Integer.parseInt(detail.getZgoodsRmb().toString()))*0.5);
			request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", zcoupon);
			request.setAttribute("userlist", listUser);
			request.getRequestDispatcher(reqpath+"publish_zeroready_detail_send.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("/webpage/error.jsp");
		}
	}
	
	/**
	 *商家确认发货
	 *何志颖
	 */
	@RequestMapping(params="make_zerosure_send")
    @ResponseBody
	public AjaxJson make_zerosure_send(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		AjaxJson j = new AjaxJson();
		try {
			DbZgoodsSingleEntity single = systemService.findUniqueByProperty(DbZgoodsSingleEntity.class, "id", id);
			single.setStatus(3);
			single.setTime(DateUtils.getDate());
			dbZgoodsSingleService.saveOrUpdate(single);
			//
			DbZeroGoodsEntity goods=systemService.get(DbZeroGoodsEntity.class, single.getDbZeroGoodsid());
			
		    String content= "您中奖的商品"+goods.getZgoodsName()+"已经飞奔在路上，感谢您对1圆梦一路的支持！发货了";
			JpushUtils.push2alias(content,single.getDbAppClientid());
			message="确认发货成功等待客户收货";
		} catch (Exception e) {
			message="操作失败请重新操作";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.setObj(message);
		return j;
		
	}
	
	/**
	 *显示每一期的中奖用户
	 *何志颖
	 */
	@RequestMapping(params="publish_zeroshow_winers")
	public void publish_zeroshow_winers(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		String sqlclient="select b.*,c.ship_address from db_zgoods_single as a left join db_app_client as b on b.id=a.db_app_clientid left join db_ship_address as c on c.db_app_clientid=b.id WHERE a.id=?";
		List<Map<String,Object>> user=dbZgoodsSingleService.findForJdbc(sqlclient, id);
		request.setAttribute("winer", user);
		request.getRequestDispatcher(reqpath+"publish_zeroshow_winers.jsp").forward(request, response);
	}
	


}

