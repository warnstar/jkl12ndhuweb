package com.lydb.controller.app;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import com.lydb.service.db_zgoods_single.DbZgoodsSingleServiceI;
import com.lydb.service.message_log.MessageLogServiceI;
import com.xingluo.util.RestJson;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**   
 *购物车接口用来操作用户购买
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/getMessage")
public class MessageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbGoodsSingleController.class);
	
	@Autowired
	private DbGoodsSingleServiceI dbGoodsSingleService;
	@Autowired
	private DbZgoodsSingleServiceI dbZgoodsSingleService;
	@Autowired
	private MessageLogServiceI messageLogService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 消息接口  
	 */
	@RequestMapping(value = "/getMessage",method= RequestMethod.POST)
	@ResponseBody
	public RestJson getMessage(int type,HttpServletRequest request,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		try {
			DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		
			if(dbtoken == null){
				//token不存在 ，返回未知错误
				rest.setError("您的登录过期，请重新登录");
				rest.setCode(7);
				return rest;
			}else{  
				StringBuffer sqldetail = new StringBuffer();
				StringBuffer sqlzero = new StringBuffer();
				if(type==1){
					
					sqldetail.append("select a.status,a.id,a.time AS open_time,a.lucky_id,a.goods_current_num,b.goods_name ");
					sqldetail.append("from db_goods_single AS a INNER JOIN db_goods As b ON a.db_goodsid=b.id ");
					sqldetail.append("where a.db_app_clientid = ? ");
					sqldetail.append("order by a.open_time DESC ");
					sqlzero.append("select a.status,a.id,a.time AS open_time,a.lucky_id,a.goods_current_num,b.zgoods_name AS goods_name ");
					sqlzero.append("from db_zgoods_single AS a INNER JOIN db_zero_goods As b ON a.db_zero_goodsid=b.id ");
					sqlzero.append("where a.db_app_clientid = ? ");
					sqlzero.append("order by a.open_time DESC ");
				}else if(type==2){
					sqldetail.append("select a.status,a.id,a.time AS open_time,a.goods_current_num,b.goods_name,a.share ");
					sqldetail.append("from db_goods_single AS a INNER JOIN db_goods As b ON a.db_goodsid=b.id ");
					sqldetail.append("where a.db_app_clientid = ? && a.`status` > 2 ");
					sqldetail.append("order by a.open_time DESC ");
					sqlzero.append("select a.status,a.id,a.time AS open_time,a.goods_current_num,b.zgoods_name AS goods_name,a.share ");
					sqlzero.append("from db_zgoods_single AS a INNER JOIN db_zero_goods As b ON a.db_zero_goodsid=b.id ");
					sqlzero.append("where a.db_app_clientid = ? && a.`status` > 2 ");
					sqlzero.append("order by a.open_time DESC ");
				}
			  List<Map<String,Object>> message=systemService.findForJdbc(sqldetail.toString(),dbtoken.getDbAppClientid());
			  List<Map<String,Object>> zeromessage=systemService.findForJdbc(sqlzero.toString(),dbtoken.getDbAppClientid());
			  //类型0为普通商品1是零元商品
			 // List<Map<String,Object>> map=new ArrayList<Map<String,Object>>();
			  Map <String,Object> map=new HashMap<String, Object>();
			  map.put("message", message);
			  map.put("zeromessage", zeromessage);
			  rest.setInfo(map);
			  rest.setCode(0);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rest.setCode(8);
			rest.setError("参数错误");			
			e.printStackTrace();
			return rest;
		}
		
	return rest;
	}
	/**
	 * 确认收货
	 */
	@RequestMapping(value = "/affirmSend",method= RequestMethod.POST)
	@ResponseBody
	public RestJson affirmSend(String id,HttpServletRequest request,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		try {
			DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
			
			if(dbtoken == null){
				//token不存在 ，返回未知错误
				rest.setError("您的登录过期，请重新登录");
				rest.setCode(7);
				return rest;
			}else{
				
				DbGoodsSingleEntity goods=dbGoodsSingleService.getEntity(DbGoodsSingleEntity.class, id);
				
				//判断是否有这件商品
					if(goods!=null){
						if(dbtoken.getDbAppClientid().equals(goods.getDbAppClientid())){
							goods.setStatus(4);	
						    dbGoodsSingleService.saveOrUpdate(goods);
						}else{
							rest.setCode(5);
							rest.setError("您并未中奖");
							return rest;
						}
					}else{
						//判断零元有无这件商品
					
						DbZgoodsSingleEntity goodszero=dbZgoodsSingleService.get(DbZgoodsSingleEntity.class, id);
						if(goodszero!=null){
							if(dbtoken.getDbAppClientid().equals(goodszero.getDbAppClientid())){
								goodszero.setStatus(4);
								dbZgoodsSingleService.saveOrUpdate(goodszero);
							}else{
								rest.setCode(5);
								rest.setError("您并未中奖");
								return rest;
							}
						}else{
							rest.setCode(4);
							rest.setError("没有这件商品了");
							return rest;
						}
						
					}
					rest.setCode(0);
					rest.setInfo("确认收货成功！");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rest.setCode(8);
			rest.setError("参数错误");
			
			e.printStackTrace();
			return rest;
		}
		
	return rest;
	}
}
