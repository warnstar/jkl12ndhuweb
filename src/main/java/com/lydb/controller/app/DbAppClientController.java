package com.lydb.controller.app;
import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_app_client_coupon.DbAppClientCouponEntity;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import com.lydb.entity.db_exchange_rmb.DbExchangeRmbEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_img_url.DbImgUrlEntity;
import com.lydb.entity.db_share_goods.DbShareGoodsEntity;
import com.lydb.entity.db_share_zerogoods.DbShareZerogoodsEntity;
import com.lydb.entity.db_ship_address.DbShipAddressEntity;
import com.lydb.entity.db_sing_integrate.DbSignIntegrateEntity;
import com.lydb.entity.db_third_client.DbThirdClientEntity;
import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.lydb.entity.dbappclientzcoupon.DbAppClientZcouponEntity;
import com.lydb.entity.rule_table.RuleTableEntity;
import com.lydb.service.db_app_client.DbAppClientServiceI;
import com.lydb.service.db_app_client_rmb.DbAppClientRmbServiceI;
import com.lydb.service.db_exchange_rmb.DbExchangeRmbServiceI;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import com.lydb.service.db_ship_address.DbShipAddressServiceI;
import com.lydb.service.db_sing_integrate.DbSignIntegrateServiceI;
import com.lydb.service.db_third_client.DbThirdClientServiceI;
import com.lydb.service.db_token.DbTokenServiceI;
import com.xingluo.Check;
import com.xingluo.GenerateRandomNumber;
import com.xingluo.HttpsUtil;
import com.xingluo.util.*;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @Title: Controller
 * @Description: app用户登录接口 
 *	用户的各种需要操作的接口集合
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/user")
public class DbAppClientController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbAppClientController.class);

	@Autowired
	private DbAppClientServiceI dbAppClientService;
	@Autowired
	private DbAppClientRmbServiceI dbappclientrmbService;
	@Autowired
	private DbShipAddressServiceI dbAddress;
	@Autowired
	private DbTokenServiceI dbtokenService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private DbSignIntegrateServiceI dbsignService;
	@Autowired
	private DbExchangeRmbServiceI dbexchangeRmbService;
	@Autowired
	private DbGoodsSingleServiceI dbjoinclientservice;
	@Autowired
	private DbThirdClientServiceI dbthirdclientservice;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public static StringBuffer sql1 =new StringBuffer();
	public static StringBuffer sql2 =new StringBuffer();
	public static StringBuffer sql3=new StringBuffer();
	public static StringBuffer sql4 = new StringBuffer();
	public static StringBuffer sql5 = new StringBuffer();
	public static StringBuffer sql6 = new StringBuffer();
	static{
		/*
		 登录接口sql
		 * */
		
		sql1.append("SELECT a.id,a.password,a.head_img,a.client_name,a.mobile,a.client_qq,a.popularize_id,s.ship_address,s.city_id,s.ship_name,s.ship_phone,r.rmb,r.integrate AS integrate_all,a.login_time ");
		sql1.append("FROM (db_app_client AS a LEFT JOIN db_app_client_rmb AS r ON a.id=r.id_app_clientid ) LEFT JOIN db_ship_address AS s ON a.id=s.db_app_clientid ");
		sql1.append("where  a.id=? ");
		/*
		 * 优惠券sql
		 * */
		
		
		sql2.append("select  c.time,c.coupon_value,c.coupon_url,c.business_name,r.isnew,r.id,r.ctime ");
		sql2.append("FROM (db_app_client AS u LEFT JOIN db_app_client_coupon AS r ON u.id=r.db_app_clientid) LEFT JOIN db_coupon_business AS c ON r.db_coupon_businessid=c.id ");
		sql2.append("WHERE u.id=? ");
		sql3.append("select  c.time,c.coupon_value,c.coupon_url,c.business_name,r.isnew,r.id,r.ctime  ");
		sql3.append("FROM (db_app_client AS u LEFT JOIN db_app_client_zcoupon AS r ON u.id=r.db_app_clientid) LEFT JOIN db_zcoupon_business AS c ON r.db_zcoupon_businessid=c.id ");
		sql3.append("WHERE u.id=? ");
		/*
		 * 参与的sql
		 * */
		
		sql4.append("SELECT  s.id,s.goods_current_num,s.all_join_num,s.current_join_num,s.`status`,g.goods_10,g.goods_name,g.goods_headurl,o.rmb_num,o.order_code,c.client_name,c.head_img,s.lucky_id,s.client_join_num,s.open_time ");
		sql4.append("FROM ((order_for_goods AS o LEFT JOIN db_goods_single AS s ON o.db_goods_singleid=s.id)LEFT JOIN db_goods AS g ON g.id=s.db_goodsid)LEFT JOIN db_app_client AS c ON c.id=s.db_app_clientid ");
		sql4.append("WHERE   o.db_app_clientid=? ");
		sql4.append("ORDER BY o.create_time DESC ");
		
		/* 
		 * 中奖sql
		 * */
		
		sql5.append("SELECT s.id,g.goods_headurl,g.goods_name,s.all_join_num,s.client_join_num,s.lucky_id,s.open_time ");
		sql5.append("FROM db_goods_single AS s LEFT JOIN db_goods AS g ON s.db_goodsid=g.id ");
		sql5.append("WHERE s.db_app_clientid=? ");
		sql5.append("ORDER BY s.open_time DESC ");
		/*
		 * 晒单sql
		 * */
		
		sql6.append("SELECT sh.id,gs.goods_current_num,gs.all_join_num,gs.lucky_id,gs.open_time,g.goods_name,sh.share_title,sh.share_content,sh.share_time,GROUP_CONCAT(i.img_url) AS allImgUrl,ac.client_name,ac.head_img ");
		sql6.append("FROM (((db_share_goods AS sh LEFT JOIN db_img_url AS i ON sh.id=i.db_share_goodsid)LEFT JOIN db_goods_single AS gs ON sh.db_goods_singleid=gs.id)LEFT JOIN db_goods AS g ON gs.db_goodsid=g.id) LEFT JOIN db_app_client as ac ON sh.db_app_clientid=ac.id ");
		sql6.append("WHERE sh.db_app_clientid=? ");
		sql6.append("GROUP BY sh.id ");
		sql6.append("ORDER BY sh.share_time DESC ");
		
	}
	
	/**
	 * 用户登录接口
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/login",method= RequestMethod.POST)
	@ResponseBody
	public RestJson login(String phone,String password,HttpServletRequest request){
		RestJson rest=new RestJson();
		if(phone.isEmpty() || password.isEmpty() || !Check.isMobile(phone)){
			rest.setError("手机号或者密码输入错误");
			rest.setCode(5);
		}else if(!phone.isEmpty() && !password.isEmpty()){
			DbAppClientEntity user= systemService.findUniqueByProperty(DbAppClientEntity.class,"mobile",phone);
			
			if(user==null){
				rest.setError("您还没注册呢");
				rest.setCode(3);
				return rest;
			}
			if(password.equals(user.getPassword())){
				try {
					//登录成功
					rest.setCode(0);
					String ip = IpUtil.getIpAddr(request);
					String address=IpLocationTool.getCity(ip);
					user.setAddressInfo(address);
					user.setIpAddress(ip);
					user.setLoginTime(new java.util.Date(System.currentTimeMillis()));
					dbAppClientService.saveOrUpdate(user);
					//传回用户信息
					rest.setAttributes(systemService.findForJdbc(sql1.toString(), user.getId()));
					StringBuffer token = new StringBuffer();
					token.append(user.getMobile()+"-");
					token.append(UUID.randomUUID().toString().replace("-", "").substring(20, 30));
					DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class,"dbAppClientid", user.getId());
					if(dbtoken == null ){
						dbtoken = new DbTokenEntity();
						dbtoken.setTokenValue(token.toString());
						dbtoken.setDbAppClientid(user.getId());
						dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
						dbtokenService.save(dbtoken);
					}else{
					dbtoken.setTokenValue(token.toString());
					dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
					dbtokenService.saveOrUpdate(dbtoken);
					}
					//传回新的token
					rest.setToken(token.toString());
				
					//添加购物车中的数量
					String sql="select id from shoppingcart where dbappclientid = ?";
					List<Map<String,Object>> list=systemService.findForJdbc(sql, user.getId());
					if(CheckList.CheckNull(list)){
						rest.setInfo(0);
						}else{
						rest.setInfo(list.size());
						}
					
				
				} catch (Exception e) {
					//未知错误  code 7
					e.printStackTrace();
					rest.setError("对不起出错了");
					rest.setCode(7);
				}finally{
					return rest;
				}
			}else{
				rest.setError("您手机号或密码输入错误");
				rest.setCode(8);
			}
		}
		return rest;
	}
	
	/**
	 * 用户第三方登录接口
	 */
	@RequestMapping(value = "/thirdLogin",method= RequestMethod.POST)
	@ResponseBody
	public RestJson thirdLogin(HttpServletRequest request,String openid,String headurl,String name){
		RestJson rest=new RestJson();
		if(openid.isEmpty()){
			rest.setError("传人的参数有错");
		;
			rest.setCode(5);
		}else{
			try {
				DbThirdClientEntity third= systemService.findUniqueByProperty(DbThirdClientEntity.class,"openId",openid);
				if(third==null){
					//ip地址的获取
				    String ip = IpUtil.getIpAddr(request);
					String address=IpLocationTool.getCity(ip);
					systemService.executeSql("CALL Thrid_createClient(?,?,?,?,?,?)", openid,UUIDGenerator.generate(),name,headurl,ip,address);
					
					rest.setCode(1);
				}else{
					
						DbAppClientEntity user=dbAppClientService.findUniqueByProperty(DbAppClientEntity.class, "id",third.getDbAppClientid() );
						if(user==null){
							systemService.delete(third);
							rest.setCode(3);
							rest.setError("请重新绑定！");
							return rest;
						}
						if(StringUtil.isEmpty(user.getMobile())){		
							rest.setCode(5);
							rest.setInfo("没绑定手机号");
						}else{
							user.setLoginTime(new java.util.Date(System.currentTimeMillis()));
							rest.setAttributes(systemService.findForJdbc(sql1.toString(), user.getId()));
							StringBuffer token = new StringBuffer();
							token.append(UUIDGenerator.generate().substring(0,11)+"-");
							token.append(UUID.randomUUID().toString().replace("-", "").substring(20, 30));
							DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class,"dbAppClientid", user.getId());
							if(dbtoken == null ){
								dbtoken = new DbTokenEntity();
								dbtoken.setTokenValue(token.toString());
								dbtoken.setDbAppClientid(user.getId());
								dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
								dbtokenService.save(dbtoken);
							}else{
								dbtoken.setTokenValue(token.toString());
								dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
								dbtokenService.saveOrUpdate(dbtoken);
							}
							//传回新的token
							rest.setToken(token.toString());
						}
					}
			} catch (Exception e) {
				rest.setCode(8);
				rest.setError("对不起出错了");
				// TODO Auto-generated catch block
				e.printStackTrace();
				return rest;
			}					
		}	
				
	 return rest;
	}
	/**
	 *绑定手机号
	 * 
	 */
		
		@RequestMapping(value = "/bindPhone",method= RequestMethod.POST)
		@ResponseBody
		public RestJson bindPhone(String phone,String openid)throws Exception{
			RestJson rest= new RestJson();
			//绑定手机号
			try {
				String sql="select count(id) from db_app_client where mobile = ?";
				Object[] array = new Object[1];
				array[0]=phone;
				Long l=dbAppClientService.getCountForJdbcParam(sql,array);
				DbThirdClientEntity third = dbthirdclientservice.findUniqueByProperty(DbThirdClientEntity.class, "openId", openid);
				DbAppClientEntity client = dbAppClientService.findUniqueByProperty(DbAppClientEntity.class, "id", third.getDbAppClientid());
				if(client==null){
					systemService.delete(third);
					rest.setCode(3);
					rest.setError("请重新绑定！");
					return rest;
				}
				if(Integer.valueOf(l.toString())==1){
					systemService.delete(client);
					DbAppClientEntity clientalready=systemService.findUniqueByProperty(DbAppClientEntity.class, "mobile", phone);
					third.setDbAppClientid(clientalready.getId());
					systemService.saveOrUpdate(third);
					rest.setAttributes(systemService.findForJdbc(sql1.toString(), clientalready.getId()));
				}else{
					
					client.setMobile(phone);
					
					String password =GenerateRandomNumber.getCharAndNumr(6);
					
					//推送一个随机的6位数的密码
					
					String content="请输记住您的密码为: "+password;
					JpushUtils.push2alias(content,client.getId());
					
					
					client.setPassword(Md5Util.EncoderByMd5(password));
					dbAppClientService.saveOrUpdate(client);
					rest.setAttributes(systemService.findForJdbc(sql1.toString(), client.getId()));
					 //环信注册
				    JSONObject obj = new JSONObject();
		            obj.put("username", phone);
		            obj.put("password", client.getPassword()); 
		            String s=obj.toString();
		            String tok=null;
		            HttpsUtil.httpsRequest("https://a1.easemob.com/12530/yiyuanmeng/users","POST",s,tok);
				}
					
					StringBuffer token = new StringBuffer();
					token.append(UUID.randomUUID().toString().replace("-", "").substring(10, 20));
					token.append(UUID.randomUUID().toString().replace("-", "").substring(20, 30));
					DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class,"dbAppClientid", client.getId());
					if(dbtoken == null ){
						dbtoken = new DbTokenEntity();
						dbtoken.setTokenValue(token.toString());
						dbtoken.setDbAppClientid(client.getId());
						dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
						dbtokenService.save(dbtoken);
				    }else{
						dbtoken.setTokenValue(token.toString());
						dbtoken.setTime(new java.util.Date(System.currentTimeMillis()));
						dbtokenService.saveOrUpdate(dbtoken);
					}
					//传回新的token
					rest.setToken(token.toString());
					rest.setCode(0);
					rest.setInfo("注册成功");	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setCode(8);
				rest.setError("对不起出错了");				
				e.printStackTrace();
				return rest;
			}
			return rest;
		}
	/**
	 * 测试用户是否存在接口
	 * 
	 */
		
		@RequestMapping(value = "/checkPhone",method= RequestMethod.POST)
		@ResponseBody
		public RestJson checkPhone(String phone){
			RestJson rest= new RestJson();
			String sql="select count(id) from db_app_client where mobile = ?";
			Object[] array = new Object[1];
			array[0]=phone;
			try {
				Long l=dbAppClientService.getCountForJdbcParam(sql,array);
				if(Integer.valueOf(l.toString())==1){
					rest.setCode(4);
					rest.setError("该用户已存在");
				}else{
					rest.setCode(0);
					rest.setInfo("该用户可以注册");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				rest.setCode(8);
				rest.setError("对不起出错了");
				e.printStackTrace();
			}
			return rest;
		}
/**
 * 用户注册接口
 * 
 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/register",method= RequestMethod.POST)
	@ResponseBody
	public RestJson register(HttpServletRequest request,String phone,String password,String popularizeId){
		RestJson rest= new RestJson();
		
		if(phone.isEmpty() || password.isEmpty() || !Check.isMobile(phone)){
			//传人的参数有错  code 5
			rest.setError("传人的手机号或密码有错");
			rest.setCode(5);
		}else if(!phone.isEmpty() && !password.isEmpty()){
			DbAppClientEntity user= systemService.findUniqueByProperty(DbAppClientEntity.class,"mobile",phone);
			if(user!=null){
				//用户已存在  code 4
				rest.setError("用户已存在");
				rest.setCode(4);
				return rest;
			}else{
					 String ip = IpUtil.getIpAddr(request);
					 String  address=IpLocationTool.getCity(ip);  
					try{ 
						
						systemService.executeSql("CALL create_Client(?,?,?,?)", phone,password,ip,address);
						
							
						 //根据用户的推广id，给推广的用户添加一个抢币
						 if(popularizeId != null){
								DbAppClientEntity user2= systemService.findUniqueByProperty(DbAppClientEntity.class,"popularizeId",popularizeId);
								if(user2!=null){
									DbAppClientRmbEntity  userrmb2=dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", user2.getId());
									user2.setInviteNum(user2.getInviteNum()+1);
									userrmb2.setRmb(userrmb2.getRmb()+1);
									userrmb2.setRmbInvite(userrmb2.getRmbInvite()+1);
									userrmb2.setRmbToday(userrmb2.getRmbToday()+1);
									dbAppClientService.saveOrUpdate(user2);
									systemService.saveOrUpdate(userrmb2);
								}
						 }
						 //环信注册
						    JSONObject obj = new JSONObject();
				            obj.put("username", phone);
				            obj.put("password", password); 
				            String tok=null;
				            HttpsUtil.httpsRequest("https://a1.easemob.com/12530/yiyuanmeng/users","POST",obj.toString(),tok);
				          //创建成功返回code 0 
				            rest.setCode(0);
				} catch (Exception e) {
					//未知错误  code 8
					e.printStackTrace();
					rest.setError("注册失败！ ");
					rest.setCode(8);
				} finally{
					return rest;
				}
			}
		}
		
		return rest;

	}
	
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/passwordReset",method= RequestMethod.POST)
	@ResponseBody
	public RestJson passwordReset(String phone,String newPassword){
		RestJson rest = new RestJson();
		if(phone.isEmpty() || newPassword.isEmpty()){
			//传人的参数有错  code 5
			rest.setError("您传入的密码有误");
			rest.setCode(5);
		}else if(!phone.isEmpty() && !newPassword.isEmpty()){
			DbAppClientEntity user= systemService.findUniqueByProperty(DbAppClientEntity.class,"mobile",phone);
			if(user == null){
				//用户不存在  code 3
				rest.setError("用户不存在");
				rest.setCode(3);
				return rest;
			}
			//更新密码
			user.setPassword(newPassword);
			dbAppClientService.saveOrUpdate(user);
			//返回操作成功，提交用户名和密码进行登录
			
			//修改环信的密码
			try {
				
				JSONObject pwd = new JSONObject();
				pwd.put("newpassword", newPassword); 			
				String tok=HttpsUtil.getToken().getString("access_token");
				String url="https://a1.easemob.com/12530/yiyuanmeng/users/"+phone+"/password";
				HttpsUtil.httpsRequest(url,"PUT",pwd.toString(),tok);
			} catch (JSONException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				rest.setCode(6);
				rest.setError("修改密码成功，环信密码修改失败");
				return rest;
			}
			rest.setCode(0);
		}
		return rest;
	}
	
	/**
	 * 修改个人信息
	 * 通过token值判断是那个用户需要修改信息
	 * 头像 ，昵称 ， 手机号码 ， qq号码
	 * mobile,clientQq,headImg,clientName
	 * @throws Exception 
	 */
	@RequestMapping(value = "/updateInfo",method= RequestMethod.POST)
	@ResponseBody
	public RestJson updateInfo(DbAppClientEntity u,@RequestHeader(value="token") String token) throws Exception{
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
		DbAppClientEntity user = dbAppClientService.get(DbAppClientEntity.class, dbtoken.getDbAppClientid());
		//对象拷贝 数据对象空值不拷贝到目标对象
		MyBeanUtils.copyBeanNotNull2Bean(u,user);
		dbAppClientService.saveOrUpdate(user);
		rest.setCode(0);
		}
		return rest;
	}
	
	/**
	 * 充值接口，给用户增加抢币
	 * tradeNum用第三支付时的订单号，有唯一性
	 */
	@RequestMapping(value = "/updateRMB",method= RequestMethod.POST)
	@ResponseBody
	public RestJson updateRMB(String tradeNum,@RequestParam(value="rmb")int rmb,@RequestHeader(value="token") String token ){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			
			/**
			 * 双重认证，当支付成功后，阿里会调异步接口通知
			 * 去查找异步通知中的消息内容， 订单信息orderInfo   用户信息clientId    充值的个数 rmbNum   订单的时间 orderTime
			 * 
			 */
			
			//根据订单号找到异步调用时的订单信息
			AccountLogEntity acc= systemService.findUniqueByProperty(AccountLogEntity.class, "orderNum", tradeNum);
			
			if(acc == null){
				rest.setCode(8);
				rest.setError("订单号不存在，请确认后提交！");
			}else if(acc.getRmb()>=rmb){
				DbAppClientRmbEntity  userrmb=dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
				//给充值的用户增加抢币
				userrmb.setRmb(userrmb.getRmb()+rmb);
				dbappclientrmbService.saveOrUpdate(userrmb);
				rest.setCode(0);
				String RMB =userrmb.getRmb().toString();
				rest.setInfo(RMB);

				//修改后台存的订单号，使订单不可用
				acc.setOrderNum(acc.getOrderNum()+"-"+UUIDGenerator.generate().substring(10,15));
				systemService.saveOrUpdate(acc);
				rest.setCode(0);
				rest.setInfo("充值成功！");
			}
		}
		return rest;
	}
	
	/**
	 * 返回我的优惠卷
	 * 根据用户的token返回该用户的优惠卷
	 */
	@RequestMapping(value = "/mycoupon",method= RequestMethod.GET)
	@ResponseBody
	public RestJson  mycoupon(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
		//rest中   info放入0元商品的优惠卷   用Object来承载
			List<Map<String,Object>> listmap1= systemService.findForJdbc(sql2.toString(), dbtoken.getDbAppClientid());
			if(!CheckList.CheckNull(listmap1)){
				rest.setAttributes(listmap1);	
			}
			List<Map<String,Object>> listmap2=systemService.findForJdbc(sql3.toString(), dbtoken.getDbAppClientid());
			if(!CheckList.CheckNull(listmap2)){
				rest.setInfo(listmap2);
			}
			//普通商品的优惠卷信息放入Attribute里面	
			//0元商品的优惠卷信息放入info里面
			
			rest.setCode(0);
		}
		
		return rest;
		
	}
	/**
	 * 删除优惠券
	 * 根据用户的token返回该用户的优惠卷
	 */
	@RequestMapping(value = "/deletecoupon",method= RequestMethod.POST)
	@ResponseBody
	public RestJson  deletecoupon(int id,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{

			DbAppClientCouponEntity coupon=systemService.getEntity(DbAppClientCouponEntity.class,id);
			if(coupon!=null){
				systemService.delete(coupon);
			}else{
				rest.setCode(8);
				rest.setError("没有这件商品");
			}
			//普通商品的优惠卷信息放入Attribute里面
			//0元商品的优惠卷信息放入info里面

			rest.setCode(0);
		}

		return rest;

	}
	/**
	 * 删除零元优惠券
	 * 根据用户的token返回该用户的优惠卷
	 */
	@RequestMapping(value = "/deletezcoupon",method= RequestMethod.POST)
	@ResponseBody
	public RestJson  deletezcoupon(int id,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			DbAppClientZcouponEntity couponEntity=systemService.get(DbAppClientZcouponEntity.class,id);
			if(couponEntity!=null){
				systemService.delete(couponEntity);
			}else{
				rest.setCode(8);
				rest.setError("没有这个优惠券");
			}
			//普通商品的优惠卷信息放入Attribute里面
			//0元商品的优惠卷信息放入info里面

			rest.setCode(0);
		}

		return rest;

	}
	/**
	 * 保存收货地址
	 * 根据用户的token更新用户的收货地址
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value = "/updateAddress",method= RequestMethod.POST)
	@ResponseBody
	public RestJson  updateAddress(DbShipAddressEntity add,@RequestHeader(value="token") String token) throws Exception{
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else if(StringUtil.isEmpty(add.getCityId())||StringUtil.isEmpty(add.getShipAddress())||StringUtil.isEmpty(add.getShipName())||StringUtil.isEmpty(add.getShipPhone())){
			rest.setCode(5);
			rest.setError("不能有空哦");
		}else{
			//通过用户id找到用户的地址并更新
			DbShipAddressEntity useradd= dbAddress.findUniqueByProperty(DbShipAddressEntity.class, "dbAppClientid", dbtoken.getDbAppClientid());
			//更新用户地址信息（对象拷贝 数据对象空值不拷贝到目标对象）
			MyBeanUtils.copyBeanNotNull2Bean(add,useradd);
			rest.setToken("0");
			/*
			 * 若为第一次填写   则给该用户增加一个抢币
			 */
			if(useradd.getGiveRmb() == 0){
				DbAppClientRmbEntity userrmb = dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
				userrmb.setRmb(userrmb.getRmb()+1);
				userrmb.setRmbToday(userrmb.getRmbToday()+1);
				dbappclientrmbService.saveOrUpdate(userrmb);
				useradd.setGiveRmb(1);
				rest.setToken("1");
			}
			dbAddress.saveOrUpdate(useradd);
			//返回当前抢币信息
			DbAppClientRmbEntity clientRmbEntity=systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
			rest.setInfo(clientRmbEntity.getRmb());
			rest.setCode(0);
		}
		
	return rest;
	}
	
	/**
	 * 进入免费抢币页面时需要的信息
	 */
	@RequestMapping(value = "/rmbInfo",method= RequestMethod.GET)
	@ResponseBody
	public RestJson rmbInfo(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			DbAppClientRmbEntity rmb = dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
			rest.setCode(0);
			//该用户的抢币信息
			rest.setInfo(rmb);
		}
		return rest;
	}
	
	/**
	 * 签到接口
	 * 每天只能签到一次
	 */
	@RequestMapping(value = "/sign",method= RequestMethod.GET)
	@ResponseBody
	public RestJson sign(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			//先查询当天是否有签到记录,只需要统计当天是否有数据
			StringBuffer sql =new StringBuffer();
			sql.append("SELECT COUNT(*) FROM `db_sign_integrate` where time = '");
			sql.append(DateUtils.formatDate());
			sql.append("' AND db_app_clientid='");
			sql.append(dbtoken.getDbAppClientid()+"' ");
			
			
			Long count =dbsignService.getCountForJdbc(sql.toString());
				
				
			//如过已经签到过了，则表示操作的对象已经存在
			if(count == 1){
				rest.setError("当天已经签到");
				rest.setCode(4);
				return rest;
			}else{
				//今天没有签到，则进行签到操作
				DbAppClientRmbEntity rmb = dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
				DbSignIntegrateEntity sign = new DbSignIntegrateEntity();
				sign.setDbAppClientid(dbtoken.getDbAppClientid());
				sign.setTime(new java.sql.Date(DateUtils.getDate().getTime()));
				sign.setValue(10);
				//对该用户的rmb数据进行处理
				rmb.setIntegrate(rmb.getIntegrate()+10);
				rmb.setIntegrateAll(rmb.getIntegrateAll()+10);
				rmb.setIntegrateToday(rmb.getIntegrateToday()+10);
				
				//保存数据
				dbappclientrmbService.saveOrUpdate(rmb);
				dbsignService.saveOrUpdate(sign);
				//返回签到成功
				rest.setCode(0);
			}
			
		}
		return rest;
	}
	
	
	/**
	 * 积分收益明细的接口
	 */
	@RequestMapping(value = "/signdetail",method= RequestMethod.GET)
	@ResponseBody
	public RestJson signdetail(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
			String hql= "FROM DbSignIntegrateEntity s  WHERE s.dbAppClientid = ?  order by s.time DESC";
			List<DbSignIntegrateEntity> list= dbsignService.findHql(hql, dbtoken.getDbAppClientid());
			//查询到的结果放入rest的info中
			rest.setInfo(list);
			rest.setCode(0);
		}
		
	
	return rest;
}
	/**
	 * 积分兑换记录查询接口
	 * 只查询最近的30条记录
	 */
	@RequestMapping(value = "/exchangedetail",method= RequestMethod.GET)
	@ResponseBody
	public RestJson exchangedetail(@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
			//查询该用户的兑换记录   前三十条
			String sql= "SELECT * FROM db_exchange_rmb AS r WHERE r.db_app_clientid='"+dbtoken.getDbAppClientid()+"' ORDER BY r.time DESC ";
			List<Map<String,Object>> list= systemService.findForJdbc(sql, 1, 30);
				
			//查询到的结果放入rest的info中
			rest.setInfo(list);
			rest.setCode(0);
		}
		
	
	return rest;
}
	
	
	
	/**
	 * 积分兑换接口
	 * 100个积分兑换一个抢币
	 * 参数rmb 表示要兑换的个数
	 */
	@RequestMapping(value = "/exchangeRMB",method= RequestMethod.POST)
	@ResponseBody
	public RestJson exchangeRMB(int rmb,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
			//先查询出该用户的积分数据
			DbAppClientRmbEntity userrmb = dbappclientrmbService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
			if(userrmb.getIntegrate()<(rmb*100)){
				//该用户的剩余积分不足
				rest.setError("该用户的剩余积分不足");
				rest.setCode(8);
				return rest;
			}else{
				//该用户的剩余积分进行扣除
				userrmb.setIntegrate(userrmb.getIntegrate()-rmb*100);
				//给用户积分
				userrmb.setRmb(userrmb.getRmb()+rmb);
				userrmb.setRmbToday(userrmb.getRmbToday()+rmb);
				//保存rmb
				dbappclientrmbService.saveOrUpdate(userrmb);
				//添加用户的兑换记录
				DbExchangeRmbEntity exchangeRmb = new DbExchangeRmbEntity();
				exchangeRmb.setDbAppClientid(dbtoken.getDbAppClientid());
				exchangeRmb.setRmbNum(rmb);
				exchangeRmb.setStatus("已到帐");
				exchangeRmb.setTime(new java.sql.Date(DateUtils.getDate().getTime()));
				//exchangeRmb.setTime(new java.util.Date(System.currentTimeMillis()));
				dbexchangeRmbService.save(exchangeRmb);
				
				//返回code 0   和用户的rmb信息
				rest.setCode(0);
				rest.setInfo(userrmb);
			}
		}
	return rest;
}
	
	/**
	 * 抢币使用规则接口
	 * 
	 */
	@RequestMapping(value = "/rmbrule",method= RequestMethod.GET)
	@ResponseBody
	public RestJson rmbrule(){
		RestJson rest = new RestJson();
		RuleTableEntity rmbrule = systemService.get(RuleTableEntity.class, 4);
		rest.setCode(0);
		rest.setInfo(rmbrule);
		return rest;
}
	
	
	/**
	 * 我的足迹接口
	 * 1.我的夺宝记录接口
	 * 2.我的中奖记录
	 * 3.我的晒单记录
	 * 分页参数pageNum
	 * 如果返回的info中为  NUll  说明该用户的
	 */
	@RequestMapping(value = "/myJoinLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson myJoinLog(int pageNum,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
			//查询该用户的夺宝记录
	
			List<Map<String,Object>> list= systemService.findForJdbcParam(sql4.toString(),pageNum, 20, dbtoken.getDbAppClientid());
			//查询到的结果放入rest的info中
			if(!CheckList.CheckNull(list)){
				rest.setInfo(list);
			}
			
			
			rest.setCode(0);
		}
		
	
	return rest;
}
	
	/**
	 * 我的中奖记录
	 */
	
	@RequestMapping(value = "/myWinLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson myWinLog(int pageNum,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
			//查询该用户的中奖记录

			List<Map<String,Object>> list= systemService.findForJdbcParam(sql5.toString(),pageNum, 20, dbtoken.getDbAppClientid());
			if(!CheckList.CheckNull(list)){
				//查询到的结果放入rest的info中
				rest.setInfo(list);
			}
			
			rest.setCode(0);
		}
		
	
	return rest;
}
	
	
	
	/**
	 * 我的晒单记录
	 */

	
	@RequestMapping(value = "/myShareLog",method = RequestMethod.POST)
	@ResponseBody
	public RestJson myShareLog(int pageNum,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
			//查询该用户的晒单记录
			List<Map<String,Object>> list= systemService.findForJdbcParam(sql6.toString(), pageNum, 20, dbtoken.getDbAppClientid());

			if(!CheckList.CheckNull(list)){
				//查询到的结果放入rest的info中
				rest.setInfo(list);
			}	
			
			rest.setCode(0);
		}
		
	
	return rest;
}
	

	/**
	 * IOS晒单操作
	 */
	@RequestMapping(value = "/shareOrderIOS" )
	@ResponseBody
	public RestJson shareOrderJson(String singlegoodsid,String shareTitle,String shareContent,String img_url1,String img_url2,String img_url3,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
	
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(singlegoodsid==null ||shareTitle==null || shareContent==null || img_url1==null){
			rest.setCode(8);
			rest.setError("都不能有空的不填哦");
			return rest;
		}else{
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
				//根据singlegoodsid找到需要晒单的期数
				try {
					DbGoodsSingleEntity singleGoods= systemService.findUniqueByProperty(DbGoodsSingleEntity.class, "id", singlegoodsid);
					//当前期数的商品没有晒单   切中奖的用户与操作的用户是一致的，才能成功晒单
					if(singleGoods.getShare() == 0 && dbtoken.getDbAppClientid().equals(singleGoods.getDbAppClientid()) ){
						//晒单操作
						singleGoods.setShare(1);
						DbShareGoodsEntity shareGoodsEntity=new DbShareGoodsEntity();
						shareGoodsEntity.setDbAppClientid(dbtoken.getDbAppClientid());
						shareGoodsEntity.setDbGoodsSingleid(singlegoodsid);
						shareGoodsEntity.setShareTitle(shareTitle);
						shareGoodsEntity.setShareContent(shareContent);
						shareGoodsEntity.setShareTime(DateUtils.getDate());
						
						//更新保存
						systemService.saveOrUpdate(singleGoods);
						systemService.save(shareGoodsEntity);
						//保存晒单的图片

                        if(img_url1!=null) {
                            DbImgUrlEntity oneimg = new DbImgUrlEntity();
                            oneimg.setDbShareGoodsid(shareGoodsEntity.getId());
                            oneimg.setImgUrl(img_url1);
                            systemService.save(oneimg);
                        }
						if(img_url2!=null){
							DbImgUrlEntity oneimg2=new DbImgUrlEntity();
							oneimg2.setDbShareGoodsid(shareGoodsEntity.getId());
							oneimg2.setImgUrl(img_url2);
							systemService.save(oneimg2);
						}
						if(img_url3!=null){
							DbImgUrlEntity oneimg3=new DbImgUrlEntity();
							oneimg3.setDbShareGoodsid(shareGoodsEntity.getId());
							oneimg3.setImgUrl(img_url3);
							systemService.save(oneimg3);
						}
						
						/**
						 * 晒单后给相应的goods表修改状态，用来操作是否需要返还保证金
						 */
						DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, singleGoods.getDbGoodsid());
						//晒单个数加1
						goods.setShareNum(goods.getShareNum()+1);
						//如果晒单的个数达到了总的个数，则进入下一个状态，并准备返还保证金
						if(goods.getShareNum() == goods.getGoodsNum()){
							//商品进入下一个状态，商品交易完成且晒单完成，返还保证金
							goods.setStatus("7");
						}
						//保存商品信息
						systemService.saveOrUpdate(goods);
						
						
						
						rest.setCode(0);
						rest.setInfo("晒单成功");
						
					}else{
						rest.setCode(4);
						rest.setError("已经晒单成功过了哦！");
						
					}
				} catch (Exception e) {
					rest.setCode(8);
					rest.setError("对不起出错了");
					// TODO Auto-generated catch block
					e.printStackTrace();
					return rest;
				}
				
			}
		}
		return rest;
	}	
	
	/**
	 * IOS零元商品晒单操作
	 */
	@RequestMapping(value = "/shareZeroOrderIOS" )
	@ResponseBody
	public RestJson shareZeroOrderIOS(String singleZerogoodsid,String shareTitle,String shareContent,String img_url1,String img_url2,String img_url3,@RequestHeader(value="token") String token){
		//String singleZerogoodsid,String shareTitle,String shareContent,String[] img_url,
		//net.sf.json.JSONObject share=net.sf.json.JSONObject.fromObject(Jsonshare);	
		RestJson rest = new RestJson();
		if(singleZerogoodsid==null || shareTitle==null|| shareContent==null || img_url1==null){
			rest.setCode(8);
			rest.setError("都不能有空的不填哦");
			return rest;
		}else{
			//通过token获得登录的用户
			DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
			
			if(dbtoken == null){
				//token不存在 ，返回未知错误
				rest.setError("您的登录过期，请重新登录");
				rest.setCode(7);
				return rest;
			}else{   
				//根据singleZerogoodsid找到需要晒单零元商品
				DbZgoodsSingleEntity singleZeroGoods= systemService.get(DbZgoodsSingleEntity.class,singleZerogoodsid);
				//当前期数的商品没有晒单   切中奖的用户与操作的用户是一致的，才能成功晒单
				try {
					if(singleZeroGoods.getShare() == 0 && dbtoken.getDbAppClientid().equals(singleZeroGoods.getDbAppClientid()) ){
						//晒单操作
						singleZeroGoods.setShare(1);
						DbShareZerogoodsEntity shareZeroGoodsEntity=new DbShareZerogoodsEntity();
						shareZeroGoodsEntity.setDbZgoodsSingleid(singleZerogoodsid);
						shareZeroGoodsEntity.setDbAppClientid(dbtoken.getDbAppClientid());
						shareZeroGoodsEntity.setShareTitle(shareTitle);
						shareZeroGoodsEntity.setShareContent(shareContent);
						shareZeroGoodsEntity.setShareTime(DateUtils.getDate());
						
						//更新保存
						systemService.saveOrUpdate(singleZeroGoods);
						systemService.save(shareZeroGoodsEntity);
						//保存晒单的图片
						//保存晒单的图片

                        if(img_url1!=null) {
                            DbImgUrlEntity oneimg = new DbImgUrlEntity();
                            oneimg.setDbShareGoodsid(shareZeroGoodsEntity.getId());
                            oneimg.setImgUrl(img_url1);
                            systemService.save(oneimg);
                        }
						if(img_url2!=null){
							DbImgUrlEntity oneimg2=new DbImgUrlEntity();
							oneimg2.setDbShareGoodsid(shareZeroGoodsEntity.getId());
							oneimg2.setImgUrl(img_url2);
							systemService.save(oneimg2);
						}
						if(img_url3!=null){
							DbImgUrlEntity oneimg3=new DbImgUrlEntity();
							oneimg3.setDbShareGoodsid(shareZeroGoodsEntity.getId());
							oneimg3.setImgUrl(img_url3);
							systemService.save(oneimg3);
						}
						/**
						 * 晒单后给相应的goods表修改状态，用来操作是否需要返还保证金
						 */
						DbZeroGoodsEntity Zgoods = systemService.get(DbZeroGoodsEntity.class, singleZeroGoods.getDbZeroGoodsid());
						//晒单个数加1
						Zgoods.setZshareNum(Zgoods.getZshareNum()+1);
						//如果晒单的个数达到了总的个数，则进入下一个状态，并准备返还保证金
						if(Zgoods.getZshareNum() == Zgoods.getZgoodsNum()){
							//商品进入下一个状态，商品交易完成且晒单完成，返还保证金
							Zgoods.setStatus("7");
						}
						//保存商品信息
						systemService.saveOrUpdate(Zgoods);
						
						rest.setCode(0);
						rest.setInfo("晒单成功");
						
					}else{
						rest.setCode(4);
						rest.setError("操作的对象已经存在！");
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					rest.setCode(8);
					rest.setError("对不起出错了");
					e.printStackTrace();
					return rest;
				}
				
			}
		}
		return rest;
	}
	
	
	/**
	 * 晒单操作
	 */
	@RequestMapping(value = "/shareOrder" )
	@ResponseBody
	public RestJson shareOrder(String singlegoodsid,String shareTitle,String shareContent,String[] img_url,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		//通过token获得登录的用户
		DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(singlegoodsid==null || shareTitle==null || shareContent==null || img_url==null){
			rest.setCode(8);
			rest.setError("都不能有空的不填哦");
			return rest;
		}else{
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{   
				//根据singlegoodsid找到需要晒单的期数
				try {

					DbGoodsSingleEntity singleGoods= systemService.findUniqueByProperty(DbGoodsSingleEntity.class, "id", singlegoodsid);
					//当前期数的商品没有晒单   切中奖的用户与操作的用户是一致的，才能成功晒单
					if(singleGoods.getShare() == 0 && dbtoken.getDbAppClientid().equals(singleGoods.getDbAppClientid()) ){
						//晒单操作
						singleGoods.setShare(1);
						DbShareGoodsEntity shareGoodsEntity=new DbShareGoodsEntity();
						shareGoodsEntity.setDbAppClientid(dbtoken.getDbAppClientid());
						shareGoodsEntity.setDbGoodsSingleid(singlegoodsid);
						shareGoodsEntity.setShareTitle(shareTitle);
						shareGoodsEntity.setShareContent(shareContent);
						shareGoodsEntity.setShareTime(DateUtils.getDate());
						
						//更新保存
						systemService.saveOrUpdate(singleGoods);
						systemService.save(shareGoodsEntity);
						//保存晒单的图片
						if(img_url!=null){
							List list = Arrays.asList(img_url);
							Set set = new HashSet(list);
							String [] rid=(String [])set.toArray(new String[0]);
							for(String img : rid){
								DbImgUrlEntity oneimg=new DbImgUrlEntity();
								oneimg.setDbShareGoodsid(shareGoodsEntity.getId());
								oneimg.setImgUrl(img);
								systemService.save(oneimg);
							}
						}
						/**
						 * 晒单后给相应的goods表修改状态，用来操作是否需要返还保证金
						 */
						DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, singleGoods.getDbGoodsid());
						//晒单个数加1
						goods.setShareNum(goods.getShareNum()+1);
						//如果晒单的个数达到了总的个数，则进入下一个状态，并准备返还保证金
						if(goods.getShareNum() == goods.getGoodsNum()){
							//商品进入下一个状态，商品交易完成且晒单完成，返还保证金
							goods.setStatus("7");
						}
						//保存商品信息
						systemService.saveOrUpdate(goods);
						
						
						
						rest.setCode(0);
						rest.setInfo("晒单成功");
						
					}else{
						rest.setCode(4);
						rest.setError("已经晒单成功过了哦！");
						
					}
				} catch (Exception e) {
					rest.setCode(8);
					rest.setError("对不起出错了");
					// TODO Auto-generated catch block
					e.printStackTrace();
					return rest;
				}
				
			}
		}
		return rest;
	}
	
	
	/**
	 * 零元商品晒单操作
	 */
	@RequestMapping(value = "/shareZeroOrder" )
	@ResponseBody
	public RestJson shareZeroOrder(String singleZerogoodsid,String shareTitle,String shareContent,String[] img_url,@RequestHeader(value="token") String token){
		RestJson rest = new RestJson();
		if(singleZerogoodsid==null || shareTitle==null || shareContent==null || img_url==null){
			rest.setCode(8);
			rest.setError("都不能有空的不填哦");
			return rest;
		}else{
			//通过token获得登录的用户
			DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
			
			if(dbtoken == null){
				//token不存在 ，返回未知错误
				rest.setError("您的登录过期，请重新登录");
				rest.setCode(7);
				return rest;
			}else{   
				//根据singleZerogoodsid找到需要晒单零元商品
				DbZgoodsSingleEntity singleZeroGoods= systemService.get(DbZgoodsSingleEntity.class, singleZerogoodsid);
				//当前期数的商品没有晒单   切中奖的用户与操作的用户是一致的，才能成功晒单
				try {
					if(singleZeroGoods.getShare() == 0 && dbtoken.getDbAppClientid().equals(singleZeroGoods.getDbAppClientid()) ){
						//晒单操作
						singleZeroGoods.setShare(1);
						DbShareZerogoodsEntity shareZeroGoodsEntity=new DbShareZerogoodsEntity();
						shareZeroGoodsEntity.setDbZgoodsSingleid(singleZerogoodsid);
						shareZeroGoodsEntity.setDbAppClientid(dbtoken.getDbAppClientid());
						shareZeroGoodsEntity.setShareTitle(shareTitle);
						shareZeroGoodsEntity.setShareContent(shareContent);
						shareZeroGoodsEntity.setShareTime(DateUtils.getDate());
						
						//更新保存
						systemService.saveOrUpdate(singleZeroGoods);
						systemService.save(shareZeroGoodsEntity);
						//保存晒单的图片
						if(img_url!=null){
                            List list = Arrays.asList(img_url);
                            Set set = new HashSet(list);
                            String [] rid=(String [])set.toArray(new String[0]);
							for(String img : rid){
								DbImgUrlEntity oneimg=new DbImgUrlEntity();
								oneimg.setDbShareGoodsid(shareZeroGoodsEntity.getId());
								oneimg.setImgUrl(img);
								systemService.save(oneimg);
							}
						}
						/**
						 * 晒单后给相应的goods表修改状态，用来操作是否需要返还保证金
						 */
						DbZeroGoodsEntity Zgoods = systemService.get(DbZeroGoodsEntity.class, singleZeroGoods.getDbZeroGoodsid());
						//晒单个数加1
						Zgoods.setZshareNum(Zgoods.getZshareNum()+1);
						//如果晒单的个数达到了总的个数，则进入下一个状态，并准备返还保证金
						if(Zgoods.getZshareNum() == Zgoods.getZgoodsNum()){
							//商品进入下一个状态，商品交易完成且晒单完成，返还保证金
							Zgoods.setStatus("7");
						}
						//保存商品信息
						systemService.saveOrUpdate(Zgoods);
						
						rest.setCode(0);
						rest.setInfo("晒单成功");
						
					}else{
						rest.setCode(4);
						rest.setError("操作的对象已经存在！");
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					rest.setCode(8);
					rest.setError("对不起出错了");
					e.printStackTrace();
					return rest;
				}
				
			}
		}
		return rest;
	}

    /**
     * 是否有最新的优惠卷
     */
    @RequestMapping(value = "/isNew" )
    @ResponseBody
    public RestJson isNew(@RequestHeader(value="token") String token){

        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if(dbtoken == null){
            //token不存在 ，返回未知错误
            rest.setError("您的登录过期，请重新登录");
            rest.setCode(7);
            return rest;
        }else{
            String sql20="SELECT * FROM db_app_client_coupon WHERE isnew =1 AND db_app_clientid =? ";
            String sql21="SELECT * FROM db_app_client_zcoupon WHERE isnew =1 AND db_app_clientid =? ";
            List<Map<String,Object>> list= systemService.findForJdbc(sql20,  dbtoken.getDbAppClientid());
            List<Map<String,Object>> list2= systemService.findForJdbc(sql21,  dbtoken.getDbAppClientid());
            if(!CheckList.CheckNull(list) || !CheckList.CheckNull(list2)){
                //查询到的结果放入rest的info中
                rest.setInfo(list.size()+list2.size());
            }else{
                rest.setInfo(0);
            }
            rest.setCode(0);
        }
        return rest;
    }
    /**
     * 清除用户最新的优惠卷
     */
    @RequestMapping(value = "/clearNew" )
    @ResponseBody
    public RestJson clearNew(@RequestHeader(value="token") String token){

        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = dbtokenService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if(dbtoken == null){
            //token不存在 ，返回未知错误
            rest.setError("您的登录过期，请重新登录");
            rest.setCode(7);
            return rest;
        }else{
            systemService.executeSql("call clearCoupon(?)",dbtoken.getDbAppClientid());
            rest.setCode(0);
        }
        return rest;
    }




}
