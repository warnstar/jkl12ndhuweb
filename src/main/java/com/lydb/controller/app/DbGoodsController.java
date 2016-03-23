package com.lydb.controller.app;

import com.lydb.controller.business.storeLoginController;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.entity.shoppingcart.ShoppingcartEntity;
import com.lydb.service.db_app_client.DbAppClientServiceI;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_token.DbTokenServiceI;
import com.lydb.service.shoppingcart.ShoppingcartServiceI;
import com.xingluo.util.CheckList;
import com.xingluo.util.RestJson;
import com.xingluo.util.alidayuText;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**   
 * 商品展示的接口集合
 * 不需要验证token，可以直接访问
 */

@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/goods")
public class DbGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbGoodsController.class);

	@Autowired
	private DbAppClientServiceI dbAppClientService;
	@Autowired
	private DbGoodsServiceI dbGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private DbTokenServiceI dbtokenService;
	@Autowired
	private ShoppingcartServiceI shoppingcartServiceI;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	private static StringBuffer sql1 =new StringBuffer();
	private static StringBuffer sql2 =new StringBuffer();
	private static StringBuffer sql3 =new StringBuffer();
	private static StringBuffer sql4 = new StringBuffer();
	private static StringBuffer sql5 = new StringBuffer();
	private static StringBuffer sql6 = new StringBuffer();
	private static StringBuffer sql7 = new StringBuffer();
	private static StringBuffer sql8 = new StringBuffer();
	private static StringBuffer sql9 = new StringBuffer();
	private static StringBuffer sql10 = new StringBuffer();
	private static StringBuffer sql11 = new StringBuffer();
	private static StringBuffer sql12 =new StringBuffer();
	private static StringBuffer sql13 =new StringBuffer();
	private static StringBuffer sql14 =new StringBuffer();
	private static StringBuffer sql15 =new StringBuffer();
	private static String Shopsql="select * from shoppingcart where dbappclientid = ?";
	static{
		//最新揭晓接口
	 sql1.append("SELECT gs.id,g.goods_headurl,g.goods_name,g.goods_rmb,gs.`status`,c.client_name,c.head_img,gs.client_join_num,gs.open_time ");
	 sql1.append("FROM (db_goods_single AS gs LEFT JOIN db_goods AS g ON gs.db_goodsid=g.id)LEFT JOIN db_app_client AS c ON gs.db_app_clientid=c.id ");
	 sql1.append("WHERE gs.`status` != 0 ");
	 sql1.append("ORDER BY gs.open_time DESC ");
		//商品详情接口
	 sql2.append("SELECT gs.id AS Goodsid,gs.client_join_num,gs.open_time,gs.lucky_id,c.client_name,c.head_img,o.ip_address,o.address_info,c.id AS upclientid ");
	 sql2.append("FROM (db_goods_single AS gs LEFT JOIN db_app_client AS c ON gs.db_app_clientid = c.id)LEFT JOIN order_for_goods AS o ON (gs.id=o.db_goods_singleid AND gs.db_app_clientid=o.db_app_clientid) ");
	 sql2.append("WHERE gs.db_goodsid = ? AND gs.goods_current_num =? ");
	 sql2.append("GROUP BY gs.id ");
	 // 本期参与的用户信息
	 sql3.append("SELECT c.id AS userId,c.client_name,c.head_img,o.rmb_num,o.create_time,o.ip_address,o.address_info ");
		sql3.append("FROM order_for_goods AS o LEFT JOIN db_app_client AS c	ON o.db_app_clientid = c.id ");
		sql3.append("WHERE o.db_goods_singleid=? ");
		sql3.append("ORDER BY o.create_time DESC ");
		// 查看已经开奖的商品的计算规则
		sql4.append("SELECT j.join_time,REPLACE(REPLACE(SUBSTRING(j.join_time,12),':',''),'.','') AS joinvalue,c.client_name ");
		sql4.append("FROM db_join_client AS j LEFT JOIN db_app_client AS c ON j.db_app_clientid=c.id ");
		sql4.append("WHERE j.db_goods_single = ?  ");
		sql4.append("ORDER BY join_time DESC  ");
		sql4.append("LIMIT 60 ");
		
		/*
		 * 他人个人中心
		 * 1.夺宝记录接口
		 * 2.中奖记录
		 * 3.晒单记录
		 */
		sql5.append("SELECT  s.id,s.goods_current_num,s.all_join_num,s.current_join_num,s.`status`,g.goods_name,g.goods_10,g.goods_headurl,o.rmb_num,o.order_code,c.client_name,c.head_img,s.lucky_id,s.client_join_num,s.open_time ");
		sql5.append("FROM ((order_for_goods AS o LEFT JOIN db_goods_single AS s ON o.db_goods_singleid=s.id)LEFT JOIN db_goods AS g ON g.id=s.db_goodsid)LEFT JOIN db_app_client AS c ON c.id=s.db_app_clientid ");
		sql5.append("WHERE   o.db_app_clientid=? ");
		sql5.append("ORDER BY o.create_time DESC ");
		
	
		sql6.append("SELECT s.id,s.goods_current_num,g.goods_headurl,g.goods_name,s.all_join_num,s.client_join_num,s.lucky_id,s.open_time ");
		sql6.append("FROM db_goods_single AS s LEFT JOIN db_goods AS g ON s.db_goodsid=g.id ");
		sql6.append("WHERE s.db_app_clientid=? ");
		sql6.append("ORDER BY s.open_time DESC ");
		
		//晒单记录
		sql7.append("SELECT sh.id,gs.goods_current_num,gs.all_join_num,gs.lucky_id,gs.open_time,g.goods_name,sh.share_title,sh.share_content,sh.share_time,GROUP_CONCAT(i.img_url) AS allImgUrl,ac.client_name,ac.head_img ");
		sql7.append("FROM (((db_share_goods AS sh LEFT JOIN db_img_url AS i ON sh.id=i.db_share_goodsid)LEFT JOIN db_goods_single AS gs ON sh.db_goods_singleid=gs.id)LEFT JOIN db_goods AS g ON gs.db_goodsid=g.id) LEFT JOIN db_app_client as ac ON sh.db_app_clientid=ac.id ");
		sql7.append("WHERE sh.db_app_clientid=? ");
		sql7.append("GROUP BY sh.id ");
		sql7.append("ORDER BY sh.share_time DESC ");
		
		// 首页热门宝物接口
	
		sql8.append("SELECT   COUNT(jc.id) AS 24HjoinNum,dg.id ");
		sql8.append("from (db_goods_single AS gs LEFT JOIN db_goods AS dg ON gs.db_goodsid=dg.id) LEFT JOIN db_join_client AS jc ON gs.id=jc.db_goods_single ");
		sql8.append("WHERE dg.`status`=5 AND jc.join_time > ? ");
		sql8.append("GROUP BY dg.id ");
		sql8.append("ORDER BY 24HjoinNum DESC ");
		
		// 根据商品名称进行模糊匹配
		sql9.append("select b.goods_headurl,b.goods_10,b.goods_name,a.goods_current_num,a.all_join_num,a.current_join_num,a.id ");
		sql9.append("from db_goods_single As a INNER JOIN db_goods As b on a.db_goodsid=b.id ");
		sql9.append("WHERE a.`status` = 0 && b.goods_name LIKE ? ");
		sql9.append("ORDER BY a.star_time ASC ");
		
		// 往期揭晓接口
		sql10.append("SELECT a.id,a.goods_current_num,a.open_time,a.lucky_id,a.client_join_num,a.`share`,b.ip_address,b.address_info,c.id AS clientid,c.client_name,c.head_img,dg.goods_name,sg.share_title,sg.share_content,sg.share_time,GROUP_CONCAT(iu.img_url) AS allImgUrl ");
		sql10.append("FROM (((((db_goods_single AS a LEFT JOIN order_for_goods AS b ON (a.id=b.db_goods_singleid AND a.db_app_clientid=b.db_app_clientid )) ");
		sql10.append("LEFT JOIN db_app_client AS c ON b.db_app_clientid=c.id )LEFT JOIN db_goods AS dg ON a.db_goodsid=dg.id) ");
		sql10.append("LEFT JOIN db_share_goods AS sg ON sg.db_goods_singleid = a.id)LEFT JOIN db_img_url as iu ON iu.db_share_goodsid=sg.id) ");
		sql10.append("WHERE a.db_goodsid = ?  AND a.`status` > 1 ");
		sql10.append("GROUP BY a.id ");
		sql10.append("ORDER BY a.star_time DESC ");
		
		//所有的晒单
		sql11.append("SELECT ac.id AS userId,ac.client_name,ac.head_img,sg.id AS shareOrderId,sg.share_time,sg.share_title,sg.share_content,GROUP_CONCAT(iu.img_url),gs.goods_current_num,g.goods_name,gs.all_join_num,gs.lucky_id,gs.open_time ");
		sql11.append("FROM ((((db_share_goods as sg LEFT JOIN db_app_client AS ac ON sg.db_app_clientid=ac.id) ");
		sql11.append("LEFT JOIN db_img_url AS iu ON sg.id=iu.db_share_goodsid)LEFT JOIN db_goods_single AS gs ON sg.db_goods_singleid=gs.id) ");
		sql11.append("LEFT JOIN db_goods AS g on gs.db_goodsid = g.id) ");
		sql11.append("GROUP BY sg.id ");
		sql11.append("ORDER BY sg.share_time DESC ");
		
		//请求单个商品揭晓
		sql12.append("SELECT gs.`status`,gs.id,gs.open_time,g.goods_headurl,g.goods_name,g.goods_rmb,c.client_name,c.head_img,gs.client_join_num ");
		sql12.append("FROM (db_goods_single AS gs LEFT JOIN db_goods AS g ON  gs.db_goodsid = g.id) LEFT JOIN db_app_client AS c ON gs.db_app_clientid=c.id ");
		sql12.append("WHERE gs.id= ? ");
		
		//所有商品的id
		sql13.append("SELECT  gs.id AS goodsId,dg.goods_10,dg.goods_name,dg.goods_headurl,gs.current_join_num,gs.all_join_num ");
		sql13.append("from (db_goods_single AS gs LEFT JOIN db_goods AS dg ON gs.db_goodsid=dg.id) LEFT JOIN db_join_client AS jc ON gs.id=jc.db_goods_single ");
		sql13.append("WHERE gs.`status`=0 ");
		sql13.append("GROUP BY gs.id ");
		sql13.append("ORDER BY gs.star_time ASC ");
		//广告的id
		sql14.append("select im.img_url,gs.id  ");
		sql14.append("from db_img_url AS im INNER JOIN db_goods_single AS gs INNER JOIN db_goods AS gd ");
		sql14.append("WHERE gs.db_goodsid=gd.id && gd.`status`=5 && gs.`status`=0 &&im.db_share_goodsid=gd.id ");
		sql14.append("ORDER BY gd.create_time DESC LIMIT 3 ");
		//热门商品的具体参数查询
		sql15.append("SELECT  gs.id AS goodsId,dg.goods_10,dg.goods_name,dg.goods_headurl,gs.current_join_num,gs.all_join_num ");
		sql15.append("FROM  db_goods AS dg INNER JOIN db_goods_single AS gs ON dg.id=gs.db_goodsid ");
		sql15.append("WHERE gs.`status`=0 && dg.id=? ");
	 }
	/**
	 * 获取人们商品的方法
	 */
	public List<Map<String,Object>> gethot(int pageNum) {
		String datetime = DateUtils.datemillFormat.format(new java.util.Date(System.currentTimeMillis() - 24 * 3600 * 1000));
		List<Map<String, Object>> listhot = systemService.findForJdbcParam(sql8.toString(), pageNum, 20, datetime);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> list3=new ArrayList<Map<String, Object>>();
		if (CheckList.CheckNull(listhot)) {
			List<Map<String, Object>> listhot2 = systemService.findForJdbcParam(sql8.toString(), 1, 1000, datetime);
			if(pageNum != 1){
				if (listhot2.size() <= 20) {
					pageNum = pageNum - 1;
				} else {
					pageNum = pageNum - listhot2.size() / 20 - 1;
				}
			}
            list = systemService.findForJdbc(sql13.toString(), pageNum, 20);
			//找到所有最热的商品
			if(!CheckList.CheckNull(listhot2) && !CheckList.CheckNull(list)) {
				for (Map<String, Object> m : listhot2) {
					if (!CheckList.CheckNull(systemService.findForJdbc(sql15.toString(), m.get("id")))) {
                        for (Map<String, Object> map2 : list) {
                            //如果出现重复的就添加到list3中
                            if (map2.get("goodsId").equals(systemService.findForJdbc(sql15.toString(), m.get("id")).get(0).get("goodsId"))){
                                list3.add(map2);
                            }
                        }
					}
				}
                list.removeAll(list3);
			}
            //在所有商品中剔除最热的商品

		} else {
			//查询到的结果放入rest的info中
			 list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : listhot) {
				if (!CheckList.CheckNull(systemService.findForJdbc(sql15.toString(), m.get("id")))) {
					list.add(systemService.findForJdbc(sql15.toString(), m.get("id")).get(0));
				}

			}

		}
		return list;
	}
	/**
	 * 首页接口
	 *只获取第一页的内容
	 */
	@RequestMapping(value = "/firstPage")
	@ResponseBody
	public RestJson newOpenGoods(){
		RestJson rest= new RestJson();
		//查询最新揭晓的商品带分页，步长20
		//排序的规则为开奖时间
		try {
			//热门商品的信息
			List<Map<String,Object>> listgoods =gethot(1);
			//第一页三个正在开奖的信息
			List<Map<String,Object>> listPrice=systemService.findForJdbc(DbGoodsController.sql1.toString(), 1, 3);
			//获取广告
			List<Map<String,Object>> listad = systemService.findForJdbc(sql14.toString(), null);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("time",new java.util.Date(System.currentTimeMillis()));
			m.put("listPrice", listPrice);
			m.put("listgoods",listgoods);
			m.put("listad",listad);
			rest.setInfo(m);
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}




	/**
	 *最新揭晓接口
	 *该接口的访问量较高，需要注意性能
	 */
	@RequestMapping(value = "/newOpenGoods",method=RequestMethod.POST)
	@ResponseBody
	public RestJson newOpenGoods(int pageNum){
		RestJson rest= new RestJson();
		//查询最新揭晓的商品带分页，步长20
		//排序的规则为开奖时间
		try {
			List<Map<String,Object>> list=systemService.findForJdbc(DbGoodsController.sql1.toString(), pageNum, 20);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("time",new java.util.Date(System.currentTimeMillis()));
			m.put("list", list);
			rest.setInfo(m);
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	/**
	 *单个商品揭晓接口
	 *该接口的访问量较高，需要注意性能
	 */
	@RequestMapping(value = "/newSingleOpenGoods",method=RequestMethod.POST)
	@ResponseBody
	public RestJson newSingleOpenGoods(String id){
		RestJson rest= new RestJson();
		//查询最新揭晓的商品带分页，步长20
		//排序的规则为开奖时间
		try {
	//加入系统时间
			List<Map<String,Object>> list=systemService.findForJdbc(DbGoodsController.sql12.toString(),id);
			if(Integer.parseInt(list.get(0).get("status").toString())>=2){
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("time",new java.util.Date(System.currentTimeMillis()));
				m.put("list", list);
				rest.setInfo(m);
				rest.setCode(0);
			}else{
				rest.setError("没有计算出来");
				rest.setCode(3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	/**
	 * 商品详情接口
	 * 商品有三种状态：0表示正在参与中，1表示正在开奖中，>=2表示已经开奖
	 */
	@RequestMapping(value = "/GoodsDetails",method=RequestMethod.POST)
	@ResponseBody
	public RestJson GoodsDetails(String singleGoodsId){
		RestJson rest= new RestJson();
		try {
			//开奖期数的中奖信息
		
			/*
			 * 根据商品的状态返回不同的商品数据
			 * 给前端也返回状态，让其选择数据组装页面
			 */
			DbGoodsSingleEntity singleGoods = systemService.get(DbGoodsSingleEntity.class, singleGoodsId);
			if(singleGoods==null){
				rest.setCode(8);
				rest.setError("这件商品可能没有了！");
				return rest;
			}
			String sql="select img_url from db_img_url where db_share_goodsid = ?";
			List<Map<String,Object>> listimg=systemService.findForJdbc(sql, singleGoods.getDbGoodsid());
			Map<String,Object> map = new HashMap<String,Object>();
			//找到goods商品信息
			DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, singleGoods.getDbGoodsid());
			//开始填充信息
			map.put("goodsHeadurl",listimg.get(0).get("img_url"));
			//map.put("goodsHeadurl",goods.getGoodsHeadurl());
			map.put("istenGoods", goods.getGoods10());
			map.put("goodsName", goods.getGoodsName());
			map.put("id", singleGoods.getId());
			map.put("status",singleGoods.getStatus());
			map.put("CurrentNum", singleGoods.getGoodsCurrentNum());
			map.put("AllNum", singleGoods.getGoodsAllNum());
			map.put("time",new java.util.Date(System.currentTimeMillis()));
			if(singleGoods.getStatus()==0){
				//0表示正在参与中
				//放入参与人数与总人数
				map.put("CurrentJoinNum", singleGoods.getCurrentJoinNum());
				map.put("AllJoinNum",singleGoods.getAllJoinNum());
				
				//如果商品当前期数大于1需要找到上一期的商品的记录
				if(singleGoods.getGoodsCurrentNum()>1){
					Map<String,Object> upgoodsmap= systemService.findOneForJdbc(sql2.toString(), singleGoods.getDbGoodsid(),singleGoods.getGoodsCurrentNum()-1);
					map.put("upGoodsInfo", upgoodsmap);
				}
			}else if(singleGoods.getStatus()==1){
				//1表示正在开奖中
				map.put("openTime", singleGoods.getOpenTime());
			}else if(singleGoods.getStatus()>=2){
				//>=2表示已经开奖
				//当前期数的商品的中奖者信息
				Map<String,Object> goodsmap= systemService.findOneForJdbc(sql2.toString(), singleGoods.getDbGoodsid(),singleGoods.getGoodsCurrentNum());
				map.put("upGoodsInfo", goodsmap);
				
				//找到正在开奖的期数和singleid
				String findSql= "SELECT gs.id AS newGoodsid,gs.goods_current_num AS newGoodsCurrentNum FROM db_goods_single AS  gs where gs.db_goodsid=? AND (gs.`status`=0 or gs.status=1 ) order by goods_current_num DESC limit 1";
				Map<String,Object> currentGoods = systemService.findOneForJdbc(findSql, singleGoods.getDbGoodsid());
				map.put("currentGoods", currentGoods);
				
			}
			rest.setInfo(map);
			rest.setCode(0);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	/**
	 * 本期参与的用户信息
	 * 参数db_goods_singleid
	 * 分页参数pageNum
	 */
	@RequestMapping(value = "/GoodsJoinIngo",method=RequestMethod.POST)
	@ResponseBody
	public RestJson GoodsJoinIngo(String singleGoodsId,int pageNum){
		RestJson rest= new RestJson();
		try {	
			List<Map<String,Object>> listmap = systemService.findForJdbcParam(sql3.toString(), pageNum, 20, singleGoodsId);
			rest.setInfo(listmap);
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	/**
	 * 查看已经开奖的商品的计算规则
	 * A值 B值   60条数据
	 * 
	 */
	@RequestMapping(value = "/GoodsCountRule",method=RequestMethod.POST)
	@ResponseBody
	public RestJson GoodsCountRule(String singleGoodsId){
		RestJson rest= new RestJson();
		try {
			DbGoodsSingleEntity singleGoods = systemService.get(DbGoodsSingleEntity.class, singleGoodsId);
			if(singleGoods==null ){
				throw new Exception();
			}else if(singleGoods.getStatus()<2){
				throw new Exception();
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("Avalue", singleGoods.getAvalue());
			map.put("Bvalue", singleGoods.getAllJoinNum());
			map.put("LuckyId", singleGoods.getLuckyId());
			
			List<Map<String,Object>>  listmap= systemService.findForJdbc(sql4.toString(), singleGoodsId);
			map.put("joinLog", listmap);
			
			rest.setInfo(map);
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	
//hzy
	/**
	 * 往期揭晓接口
	 */
	@RequestMapping(value = "/GoodsOpenAlreadyList",method=RequestMethod.POST)
	@ResponseBody
	public RestJson GoodsOpneAlreadyList(int pageNum,String singleGoodsId){
		RestJson rest= new RestJson();
		try {
			DbGoodsSingleEntity singleGoods = systemService.get(DbGoodsSingleEntity.class, singleGoodsId);
			if(singleGoods==null ){
				throw new Exception();
			}
			List<Map<String,Object>>  listmap= systemService.findForJdbcParam(sql10.toString(),pageNum,20, singleGoods.getDbGoodsid());	
			rest.setInfo(listmap);
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setError("对不起出错了");
			rest.setCode(8);
			return rest;
		}
		return rest;
	}
	
	/**
	 *他人个人中心
	 * 1.夺宝记录接口
	 * 2.中奖记录
	 * 3.晒单记录
	 * 分页参数pageNum
	 * 如果返回的info中为  NUll  说明该用户的
	 */
	@RequestMapping(value = "/otherJoinLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson otherJoinLog(int pageNum,String dbAppClientid){
		RestJson rest = new RestJson();
		
		
			try {
			
				DbAppClientEntity people= dbAppClientService.getEntity(DbAppClientEntity.class, dbAppClientid);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", people.getClientName());
				map.put("head_url",people.getHeadImg());
				map.put("userid",people.getId());
				
				List<Map<String,Object>> list= systemService.findForJdbcParam(sql5.toString(),pageNum, 20,dbAppClientid);
				if(CheckList.CheckNull(list)){
					list=null;
				}
				map.put("list",list);	
				//查询到的结果放入rest的info中
				rest.setInfo(map);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}

	return rest;
}
	
	/**
	 * 中奖记录查询
	 */
	
	@RequestMapping(value = "/otherWinLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson otherWinLog(int pageNum,String dbAppClientid){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
			try {
				List<Map<String,Object>> list= systemService.findForJdbcParam(sql6.toString(),pageNum, 20,dbAppClientid);
				if(CheckList.CheckNull(list)){
					list=null;
				}else{

				}
				DbAppClientEntity people= dbAppClientService.getEntity(DbAppClientEntity.class, dbAppClientid);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", people.getClientName());
				map.put("head_url",people.getHeadImg());
				map.put("userid",people.getId());	
				//查询到的结果放入rest的info中
				map.put("list",list);	
				//查询到的结果放入rest的info中
				rest.setInfo(map);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}

	return rest;
}
	
	
	
	/**
	 * 晒单记录
	 */
	
	@RequestMapping(value = "/otherShareLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson otherShareLog(int pageNum,String dbAppClientid){
		RestJson rest = new RestJson();
			try {
			
				
				List<Map<String,Object>> list= systemService.findForJdbcParam(sql7.toString(),pageNum, 20,dbAppClientid);
				if(CheckList.CheckNull(list)){
					list=null;
				}
				DbAppClientEntity people= dbAppClientService.getEntity(DbAppClientEntity.class, dbAppClientid);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", people.getClientName());
				map.put("head_url",people.getHeadImg());
				map.put("userid",people.getId());
				//查询到的结果放入rest的info中
				map.put("list",list);	
				//查询到的结果放入rest的info中
				rest.setInfo(map);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
			return rest;
	}	
	/**
	 * 首页所有商品接口
	 * 
	 * 按当前时间的前24小时内，参与人数最多的宝物进行排序
	 */
	@RequestMapping(value = "/SearchAll",method = RequestMethod.POST)
	@ResponseBody
	public RestJson SearchAll(int pageNum){
		RestJson rest = new RestJson();
			try {
					List<Map<String,Object>> listnormal= systemService.findForJdbc(sql13.toString(),pageNum,20);
					rest.setInfo(listnormal);
					rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
			return rest;
	}
	/**
	 * 首页热门宝物接口
	 * 
	 * 按当前时间的前24小时内，参与人数最多的宝物进行排序
	 */
	@RequestMapping(value = "/SearchByHot",method = RequestMethod.POST)
	@ResponseBody
	public RestJson SearchByHot(int pageNum){
		RestJson rest = new RestJson();
			try {
				List<Map<String,Object>> list=gethot(pageNum);
				rest.setInfo(list);
				rest.setCode(0);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了！");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
			return rest;
	}
	
	/**
	 * 根据类型进行搜索商品
	 * isTen  1表示查十元商品   0标识不查
	 * type差商品类型
	 */
	@RequestMapping(value = "/SearchByType",method = RequestMethod.POST)
	@ResponseBody
	public RestJson SearchByType(int pageNum,String type,String  isTen){
		RestJson rest = new RestJson();
			try {
				StringBuffer sql = new StringBuffer();
				List<Map<String,Object>> list =null;
				sql.append("select b.goods_headurl,b.goods_10,b.goods_name,a.goods_current_num,a.all_join_num,a.current_join_num,a.id ");
				sql.append("from db_goods_single As a INNER JOIN db_goods As b on a.db_goodsid=b.id ");
				if(isTen.equals("2")){
					sql.append("WHERE a.`status` = 0 ");
					sql.append("ORDER BY a.star_time ASC ");
					list= systemService.findForJdbcParam(sql.toString(),pageNum, 20);	
				}else{
					if(isTen.equals("1")){
						sql.append("WHERE a.`status` = 0 && b.goods_10= ? ");
						sql.append("ORDER BY a.star_time ASC ");
						list= systemService.findForJdbcParam(sql.toString(),pageNum, 20,isTen);	
					}else{
						
						sql.append("WHERE a.`status` = 0 && b.goods_type = ? ");
						sql.append("ORDER BY a.star_time ASC ");
						list= systemService.findForJdbcParam(sql.toString(),pageNum, 20,type);	
					}	
						if(CheckList.CheckNull(list)){
							list=null;
						}
				}
				//查询到的结果放入rest的info中
				rest.setInfo(list);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
			
				e.printStackTrace();
				return rest;
			}
			return rest;
	}

	
	
	
	/**
	 * 根据商品名称进行模糊匹配
	 */
	@RequestMapping(value = "/SearchByName",method = RequestMethod.POST)
	@ResponseBody
	public RestJson SearchByName(int pageNum,String name){
		RestJson rest = new RestJson();
			try {
				List<Map<String,Object>> list= systemService.findForJdbcParam(sql9.toString(),pageNum, 20,"%"+name+"%");
				if(CheckList.CheckNull(list)){
					list=null;
				}
				//查询到的结果放入rest的info中
				rest.setInfo(list);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("提交的参数出现异常！");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
			return rest;
	}
	
	/**
	 * 奖品晒单
	 * 所有的晒单根据时间进行排序
	 * 
	 */
	@RequestMapping(value = "/AllShareOrder",method = RequestMethod.POST)
	@ResponseBody
	public RestJson AllShareOrder(int pageNum){
		RestJson rest = new RestJson();
			try {
			
				List<Map<String,Object>> list= systemService.findForJdbc(sql11.toString(),pageNum, 20);
				if(CheckList.CheckNull(list)){
					list=null;
				}
				//查询到的结果放入rest的info中
				rest.setInfo(list);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
			return rest;
	}
	/**
	 * 加入购物车
	 * 
	 */
	@RequestMapping(value = "/AddShoppingCart",method = RequestMethod.POST)
	@ResponseBody
	public RestJson AddShoppingCart(int number,String id,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(number<=0 ){
			rest.setCode(8);
			rest.setError("您输入的个数有误");
			return rest;
			}
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			try {
			   DbGoodsSingleEntity single=systemService.getEntity(DbGoodsSingleEntity.class, id);
				
				String number1="select * from shoppingcart where  dbappclientid= ?";
				List<Map<String,Object>> numberCart= systemService.findForJdbc(number1,dbtoken.getDbAppClientid());
				DbGoodsEntity goods=systemService.findUniqueByProperty(DbGoodsEntity.class, "id", single.getDbGoodsid());
				if(goods.getStatus().equals("5")){
				//判断购物车有没有满10件商品
					if(numberCart.size()<11){
						String sql="select * from shoppingcart where dbgoodsid= ? && dbappclientid= ? ";
						List<Map<String,Object>> list= shoppingcartServiceI.findForJdbc(sql,single.getDbGoodsid(),dbtoken.getDbAppClientid());
						int current=single.getAllJoinNum()-single.getCurrentJoinNum();
						//判断购物车中有没有这件商品
						
						if(CheckList.CheckNull(list)){
							ShoppingcartEntity shop=new ShoppingcartEntity();
							shop.setDbappclientid(dbtoken.getDbAppClientid());
							//加入购物车的数量小于个数的话就加入大于剩余个数加入所有剩余
							if(number<current){
								if(goods.getGoods10()==1 ){
									if(number%10!=0){
										rest.setCode(8);
										rest.setError("您输入的个数有误");
									}else{
										shop.setNumber(number);
									}
								}else{
								    shop.setNumber(number);
								}
							}else{
									shop.setNumber(current);
							}
							shop.setDbgoodsid(single.getDbGoodsid());
							shoppingcartServiceI.save(shop);
							if(CheckList.CheckNull(numberCart)){
								rest.setInfo(1);
							}
							rest.setInfo(numberCart.size()+1);
							
						}else{
							ShoppingcartEntity en = shoppingcartServiceI.findUniqueByProperty(ShoppingcartEntity.class, "id",list.get(0).get("id"));
							//判断购物车是否溢出
							if((en.getNumber()+number)<current){
								if(goods.getGoods10()==1 ){
									if(number%10!=0){
										rest.setCode(8);
										rest.setError("您输入的个数有误");
									}else{
										en.setNumber(en.getNumber()+number);
									}
								}else{
									en.setNumber(en.getNumber()+number);
								}
								shoppingcartServiceI.saveOrUpdate(en);
								rest.setInfo(numberCart.size());
								
								return rest;
							}else{
								en.setNumber(current);
								shoppingcartServiceI.saveOrUpdate(en);
								rest.setInfo(numberCart.size());
								rest.setCode(4);
								rest.setError("不能再增加了");
								return rest;
							}
							
						
						}
						rest.setCode(0);
					}else{
						rest.setInfo(10);
						rest.setCode(6);
						rest.setError("购物车已经满了，清空一下吧");
						return rest;
					}
				}else{
					rest.setCode(4);
					rest.setError("这个商品已经卖完了，看看其他的吧");
					return rest;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
		}
		return rest;
	}
	/**
	 * 增减数量
	 * 
	 */
	@RequestMapping(value = "/AddShoppingnumber",method = RequestMethod.POST)
	@ResponseBody
	public RestJson AddShoppingnumber(int number,String shopcartid,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(number<=0 ){
			rest.setCode(8);
			rest.setError("您输入的个数有误");
			return rest;
		}
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			try {
				ShoppingcartEntity shopcart=systemService.getEntity(ShoppingcartEntity.class, shopcartid);
				shopcart.setNumber(number);
				shoppingcartServiceI.saveOrUpdate(shopcart);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
		}
		return rest;
	}
	/**
	 * 删除购物车东西
	 * 
	 */
	@RequestMapping(value = "/DeleteShoppingCart",method = RequestMethod.POST)
	@ResponseBody
	public RestJson DeleteShoppingCart(String[] id,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			String sql="select id from shoppingcart where dbappclientid = ?";
			List<Map<String,Object>> list=systemService.findForJdbc(sql, dbtoken.getDbAppClientid());
			try {
				for(String l : id){
					ShoppingcartEntity shop=systemService.getEntity(ShoppingcartEntity.class, l);
					shoppingcartServiceI.delete(shop);
				}
				List<Map<String,Object>> list2=systemService.findForJdbc(sql, dbtoken.getDbAppClientid());
				if(CheckList.CheckNull(list2)){
					rest.setInfo(0);
				}else{
					rest.setInfo(list2.size());
				}
				
				
				
			
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
		}
	
		return rest;
	}
	
	/**
	 * 获取购物车的东西
	 * 
	 */
	@RequestMapping(value = "/GetShoppingCart",method = RequestMethod.POST)
	@ResponseBody
	public RestJson GetShoppingCart(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			try {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
				//获取购物车数据库的所有信息
				List<Map<String,Object>> list1= systemService.findForJdbc(Shopsql.toString(),dbtoken.getDbAppClientid());
				for(Map<String,Object> m : list1){
					ShoppingcartEntity shop = shoppingcartServiceI.findUniqueByProperty(ShoppingcartEntity.class, "id",m.get("id"));
					if(shop.getNumber()>0){
						DbGoodsEntity goods=systemService.findUniqueByProperty(DbGoodsEntity.class,"id", m.get("dbgoodsid"));
						//判断商品是否卖完
						if(Integer.valueOf(goods.getStatus())==5){
							String sql="select id,all_join_num,current_join_num from db_goods_single where db_goodsid = ? && status=0";
							//找到正在进行中的商品参与信息
							List<Map<String,Object>> list2=systemService.findForJdbc(sql,goods.getId());
							if(CheckList.CheckNull(list2)){
								systemService.delete(shop);
								break;
							}
							int left=Integer.valueOf(list2.get(0).get("all_join_num").toString())-Integer.valueOf(list2.get(0).get("current_join_num").toString());
							//判断数量是否溢出
							if(shop.getNumber()> left ){
								shop.setNumber(Integer.valueOf(list2.get(0).get("all_join_num").toString())-Integer.valueOf(list2.get(0).get("current_join_num").toString()));
								shoppingcartServiceI.saveOrUpdate(shop);
							}//10元商品是否价格正确
							if(goods.getGoods10()==1){
								if(shop.getNumber()%10!=0){
									shop.setNumber(10);
								}
							}
							//加入显示
							Map<String,Object> goodssingle=new HashMap<String, Object>();		
							goodssingle.put("headurl", goods.getGoodsHeadurl());
							goodssingle.put("goodsname", goods.getGoodsName());
							goodssingle.put("goodsid", goods.getId());
							//购物车的id
							goodssingle.put("id",m.get("id"));
							goodssingle.put("isten",goods.getGoods10());
							goodssingle.put("number", shop.getNumber());
							goodssingle.put("goodssingleid",list2.get(0).get("id"));
							goodssingle.put("goodsallnum", list2.get(0).get("all_join_num"));
							goodssingle.put("goodscurrentnum", list2.get(0).get("current_join_num"));
							list.add(goodssingle);
						}else{
							shoppingcartServiceI.delete(shop);
						}
					}else{
						//删除无效的条目
							shoppingcartServiceI.delete(shop);
					}
				}
				rest.setAttributes(list);
				rest.setCode(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setError("对不起出错了");
				rest.setCode(8);
				e.printStackTrace();
				return rest;
			}
		}
		return rest;
	}
	/**
	 * 广告图片
	 * 
	 */
	@RequestMapping(value = "/AD",method = RequestMethod.GET)
	@ResponseBody
	public RestJson AD(){
		RestJson rest = new RestJson();
		List<Map<String, Object>> listmap;
		try {
		    listmap = systemService.findForJdbc(sql14.toString(), null);
			rest.setInfo(listmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rest.setCode(8);
			e.printStackTrace();
			rest.setError("对不起服务器出错了，请重新登录我们app");
		}
		
		return rest;
	}
	/**
	 * 广告图片
	 *
	 */
	@RequestMapping(value = "/text",method = RequestMethod.POST)
	@ResponseBody
	public RestJson text(String mobile){
		RestJson rest = new RestJson();
		try {
			String code = storeLoginController.starcode();
			alidayuText.Text(mobile, code);
			rest.setCode(0);
			rest.setInfo(code);
		} catch (Exception e) {
			e.printStackTrace();
			rest.setCode(8);
			rest.setError("对不起出错了");
			return rest;
		}
		return rest;
	}
	@RequestMapping(value = "/text2",method = RequestMethod.POST)
	@ResponseBody
	public RestJson sendchangecode(String mobile) {
		RestJson rest = new RestJson();
		try {
			String code = storeLoginController.starcode();
			alidayuText.TextMessage(mobile, code);
			rest.setCode(0);
			rest.setInfo(code);
		} catch (Exception e) {
			e.printStackTrace();
			rest.setCode(8);
			rest.setError("对不起出错了");
			return rest;
		}
		return rest;
	}
}
