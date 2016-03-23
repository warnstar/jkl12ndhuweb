package com.lydb.controller.business;

import com.lydb.entity.db_coupon_business.DbCouponBusinessEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.service.db_coupon_business.DbCouponBusinessServiceI;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import com.lydb.service.db_web_business.DbWebBusinessServiceI;
import com.lydb.service.db_zcoupon_business.DbZcouponBusinessServiceI;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;
import com.sun.star.io.IOException;
import com.xingluo.util.JpushUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;



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
@RequestMapping("/storeSingleBusiness")
public class storeSingleController extends BaseController {
	
	
	private static final Logger logger = Logger.getLogger(storeSingleController.class);

	@Autowired
	private DbGoodsSingleServiceI dbGoodsSingleService;
	
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
	@RequestMapping(params="publish_goods_notopen")
	public void publish_goods_notopen(HttpServletRequest request,HttpServletResponse response,String name,String pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select b.serial_num,b.create_time,b.goods_name,b.goods_rmb,b.goods_num,a.goods_current_num,a.current_join_num,a.id from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where (a.status =0 OR a.status = 1) && b.db_web_businessid = ? && 5<b.status<8  ";
		String number="select count(b.id) from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.status =0 && b.db_web_businessid = ? && 5<b.status<8  ";
       if(name!=null){
		   notReady=notReady+" && b.goods_name LIKE ?" +" order by b.create_time DESC";
		   number=number+" && b.goods_name LIKE ?";
		   Object[] array = new Object[2];
			array[0]=request.getSession().getAttribute("id");
			array[1]="%"+name+"%";
			long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
			request.setAttribute("pagelist",(int)PageList(l));
			request.setAttribute("pagenow",pagenow);
			List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,Integer.parseInt(pagenow),20, request.getSession().getAttribute("id"),"%"+name+"%");
			
			request.setAttribute("name", name);
			request.setAttribute("goodslist", goodsList);
		}else{
	       if(search!=null){
				if(Integer.parseInt(search)==1){
					notReady=notReady+" order by b.create_time DESC";
				}else if(Integer.parseInt(search)==2){
					notReady=notReady+" order by b.goods_rmb DESC";
				}else if(Integer.parseInt(search)==3){
					notReady=notReady+" order by b.goods_num DESC";
				}
			}else{
				search="0";
			   notReady=notReady+" order by b.create_time DESC";
			}
	        Object[] array = new Object[1];
			array[0]=request.getSession().getAttribute("id");
			long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
			request.setAttribute("pagelist",(int)PageList(l));
			request.setAttribute("pagenow",pagenow);
			List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,Integer.parseInt(pagenow),20, request.getSession().getAttribute("id"));
		
			request.setAttribute("se", search);
			request.setAttribute("goodslist", goodsList);
		}
		request.getRequestDispatcher(reqpath+"publish_goods_notopen.jsp").forward(request, response);
	}
	
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_goods_notsend")
	public void publish_goods_notsend(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notsend="select b.create_time,b.goods_name,b.goods_rmb,b.serial_num,b.goods_num,a.goods_current_num,a.id from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.status =2 && b.db_web_businessid = ?  && b.status>4  ";
		String number="select count(b.id)  from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.status =2 && b.db_web_businessid = ?  && b.status>4 ";
		if(name!=null){
			   notsend=notsend+" && b.goods_name LIKE ? " + " order by b.create_time DESC";
			   number=number+" && b.goods_name LIKE ? ";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist",(int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notsend,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("name", name);
				request.setAttribute("notsendList", goodsList);
			}else{
				if(search!=null){
					if(Integer.parseInt(search)==1){
						notsend=notsend+" order by b.create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notsend=notsend+" order by b.goods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notsend=notsend+" order by b.goods_num DESC";
					}
				}else{
					search="0";
					notsend=notsend+" order by b.create_time DESC";
				}
		        Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notsend,pagenow,20, request.getSession().getAttribute("id"));
				request.setAttribute("notsendList", goodsList);
				
				
			}
		request.setAttribute("searchnumber", search);
		request.getRequestDispatcher(reqpath+"publish_goods_notsend.jsp").forward(request, response);
	}
	
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_goods_send")
	public void publish_goods_send(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select b.create_time,b.goods_name,b.goods_rmb,b.share_num,b.goods_num,a.goods_current_num,b.id from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.status =3 && b.db_web_businessid = ?  && b.status>4 ";
		String number="select count(b.id) from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.status =3 && b.db_web_businessid = ?  && b.status>4 ";
		if(name!=null){
			   notReady=notReady+" && b.goods_name LIKE ? order by b.create_time ASC";
			   number=number+" && b.goods_name LIKE ?";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist",(int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
			
				request.setAttribute("name", name);
				request.setAttribute("sendList", goodsList);
		}else{
	      if(search!=null){
				if(Integer.parseInt(search)==1){
					notReady=notReady+" order by b.create_time DESC";
				}else if(Integer.parseInt(search)==2){
					notReady=notReady+" order by b.goods_rmb DESC";
				}else if(Integer.parseInt(search)==3){
					notReady=notReady+" order by b.goods_num DESC";
				}
			}else{
				search="0";
			   notReady=notReady+" order by b.create_time DESC";
			}
		
	        Object[] array = new Object[1];
			array[0]=request.getSession().getAttribute("id");
			long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
			request.setAttribute("pagelist",(int) PageList(l));
			request.setAttribute("pagenow",pagenow);
			List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"));
			request.setAttribute("sendList", goodsList);
		
			request.setAttribute("se", search);
		}
		request.getRequestDispatcher(reqpath+"publish_goods_send.jsp").forward(request, response);
	}
	
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_goods_Ready")
	public void publish_goods_Ready(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select create_time,goods_rmb,goods_num,goods_name,id from db_goods where  status <8 && status >6 && db_web_businessid = ? && share_num=goods_num";
		String number="select count(id) from db_goods where  status <8 && status >6 && db_web_businessid = ? && share_num=goods_num";
		 if(name!=null){
			   notReady=notReady+" && goods_name LIKE ? " + " order by create_time DESC";
			   number=number+" && goods_name LIKE ?";
			   Object[] array = new Object[2];
			    array[1]="'%"+name+"%";
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("name", name);
			}else{
		       if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by goods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by goods_num DESC";
					}
				}else{
					search="0";
				   notReady=notReady+" order by create_time DESC";
				}
		        Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"));
				request.setAttribute("finushlist", goodsList);
		
				request.setAttribute("se", search);
			}
		request.getRequestDispatcher(reqpath+"publish_goods_Ready.jsp").forward(request, response);
	}
	
	/**
	 *用户未开奖商品列表详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_goods_finish")
	public void publish_goods_finish(HttpServletRequest request,HttpServletResponse response,String name,int pagenow,String search) throws ServletException, IOException, java.io.IOException{
		
		String notReady="select create_time,goods_rmb,goods_num,goods_name,id from db_goods where  status = 8 && db_web_businessid = ? && share_num=goods_num";
		String number="select count(id) from db_goods where  status =8 && db_web_businessid = ? && share_num=goods_num";
		 if(name!=null){
			   notReady=notReady+" && goods_name LIKE ? "+" order by create_time DESC ";
			   number=number+" && goods_name LIKE ?";
			   Object[] array = new Object[2];
				array[0]=request.getSession().getAttribute("id");
				array[1]="%"+name+"%";
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"),"%"+name+"%");
				request.setAttribute("finushlist", goodsList);
				request.setAttribute("name", name);
			}else{
		       if(search!=null){
					if(Integer.parseInt(search)==1){
						notReady=notReady+" order by create_time DESC";
					}else if(Integer.parseInt(search)==2){
						notReady=notReady+" order by goods_rmb DESC";
					}else if(Integer.parseInt(search)==3){
						notReady=notReady+" order by goods_num DESC";
					}
				}else{
					search="0";
				   notReady=notReady+" order by create_time DESC";
				}
		        Object[] array = new Object[1];
				array[0]=request.getSession().getAttribute("id");
				long l=dbGoodsSingleService.getCountForJdbcParam(number,array);
				request.setAttribute("pagelist", (int)PageList(l));
				request.setAttribute("pagenow",pagenow);
				List<Map<String,Object>> goodsList=dbGoodsService.findForJdbcParam(notReady,pagenow,20, request.getSession().getAttribute("id"));
				request.setAttribute("finushlist", goodsList);
		
				request.setAttribute("se", search);
			}
		request.getRequestDispatcher(reqpath+"publish_goods_finish.jsp").forward(request, response);
	}
	/**
	 *用户已上架商品列表未开奖商品详情
	 *何志颖
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws java.io.IOException 
	 * @throws java.io.IOException 
	 */
	@RequestMapping(params="publish_ready_detailnotopen")
	public void publish_ready_detailnotopen(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		try {
			String detailsql="select b.*,a.goods_current_num,a.current_join_num from db_goods_single As a inner join db_goods As b ON a.db_goodsid=b.id where a.id= ?";
			List<Map<String,Object>> detail=dbGoodsService.findForJdbc(detailsql, id);
			/*String  couponsql="select * from db_coupon_business where db_goodsid=?";
			List<Map<String,Object>> coupon=dbCouponBusinessService.findForJdbc(couponsql, detail.get(0).get("id"));*/
			DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", detail.get(0).get("id"));
			float money=(float)((Integer.parseInt(detail.get(0).get("goods_num").toString())*Integer.parseInt(detail.get(0).get("goods_rmb").toString()))*0.5);
			request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", coupon);
			request.getRequestDispatcher(reqpath+"publish_ready_detail_tan2.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect("/webpage/error.jsp");
			// TODO Auto-generated catch block
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
	@RequestMapping(params="publish_ready_detailnotsend")
	public void publish_ready_detailnotsend(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		
		try {
			DbGoodsSingleEntity single=dbGoodsSingleService.get(DbGoodsSingleEntity.class, id);
			String detailsql="select * from db_goods where id= ?";
			List<Map<String,Object>> detail=dbGoodsService.findForJdbc(detailsql, single.getDbGoodsid());
			/*String  couponsql="select * from db_coupon_business where db_goodsid= ?";
			List<Map<String,Object>> coupon=dbCouponBusinessService.findForJdbc(couponsql, detail.get(0).get("id"));*/
			DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", detail.get(0).get("id"));
			String sqlclient="select a.lucky_id,b.client_name,b.client_qq,b.mobile,c.ship_address,c.ship_phone,b.head_img from db_goods_single as a left join db_app_client as b on b.id=a.db_app_clientid left join db_ship_address as c on c.db_app_clientid=b.id WHERE a.id=?";
			List<Map<String,Object>> user=dbGoodsSingleService.findForJdbc(sqlclient, id);
			  float money=(float)((Integer.parseInt(detail.get(0).get("goods_num").toString())*Integer.parseInt(detail.get(0).get("goods_rmb").toString()))*0.5);
			request.setAttribute("number", single.getGoodsCurrentNum());
			request.setAttribute("id",id );
			 request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", coupon);
			request.setAttribute("userlist", user);
			request.getRequestDispatcher(reqpath+"publish_ready_detail_tan3.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect("/webpage/error.jsp");
			// TODO Auto-generated catch block
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
	@RequestMapping(params="publish_ready_detailsend")
	public void publish_ready_detailsend(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		
		try {
			DbGoodsEntity detail=dbGoodsService.get(DbGoodsEntity.class, id);
			/*String  couponsql="select * from db_coupon_business where db_goodsid= ?";
			List<Map<String,Object>> coupon=dbCouponBusinessService.findForJdbc(couponsql, id);*/
			DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", id);
			String sqlclient="select a.share,a.lucky_id,a.id,b.mobile,b.head_img,a.goods_current_num from db_goods_single As a inner join db_app_client As b ON a.db_app_clientid=b.id where a.db_goodsid = ?";
			List<Map<String,Object>> listUser=dbGoodsSingleService.findForJdbc(sqlclient, id);
			float money=(float)((Integer.parseInt(detail.getGoodsNum().toString())*Integer.parseInt(detail.getGoodsRmb().toString()))*0.5);
			request.setAttribute("moeny", money);
			request.setAttribute("detail", detail);
			request.setAttribute("coupon", coupon);
			request.setAttribute("userlist", listUser);
			request.getRequestDispatcher(reqpath+"publish_ready_detail_send.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect("/webpage/error.jsp");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *商家确认发货
	 *何志颖
	 */
	@RequestMapping(params="make_sure_send")
    @ResponseBody
	public AjaxJson make_sure_send(String id) throws ServletException, IOException, java.io.IOException{
		AjaxJson j = new AjaxJson();
		try {
			DbGoodsSingleEntity single = dbGoodsSingleService.get(DbGoodsSingleEntity.class, id);
			single.setStatus(3);
			single.setTime(DateUtils.getDate());
			dbGoodsSingleService.saveOrUpdate(single);
			//推送消息
			DbGoodsEntity goods=systemService.get(DbGoodsEntity.class, single.getDbGoodsid());
			
		    String content="您中奖的商品"+goods.getGoodsName()+"已经飞奔在路上，感谢您对1圆梦一路的支持！发货了";
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
	@RequestMapping(params="publish_show_winers")
	public void publish_show_winers(HttpServletRequest request,HttpServletResponse response,String id) throws ServletException, IOException, java.io.IOException{
		String sqlclient="select b.*,c.ship_address from db_goods_single as a left join db_app_client as b on b.id=a.db_app_clientid left join db_ship_address as c on c.db_app_clientid=b.id WHERE a.id=?";
		List<Map<String,Object>> winer=dbGoodsSingleService.findForJdbc(sqlclient, id);
		request.setAttribute("winer", winer);
		request.getRequestDispatcher(reqpath+"publish_show_winers.jsp").forward(request, response);
	}

}

