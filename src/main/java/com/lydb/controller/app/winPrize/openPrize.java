package com.lydb.controller.app.winPrize;

import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_coupon_business.DbCouponBusinessEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.xingluo.util.JpushUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.Map;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class openPrize implements Job {

	
	private static String sql2 = "SELECT SUM(o.rmb_num) AS allJionNum FROM order_for_goods  AS o WHERE o.db_app_clientid = ? AND o.db_goods_singleid= ? GROUP BY db_goods_singleid ";
	private static  String hql= "FROM  DbJoinClientEntity j  WHERE j.dbCode = ? AND  j.dbAppClientid = ?  AND j.dbGoodsSingle = ? ";
	private static  String hql2= "FROM  DbJoinClientEntity j  WHERE j.dbCode = ? AND j.dbGoodsSingle = ? ";
	//获得没有中奖的用户id  list (带去重)，查询的表为order_for_goods
	private static  String notPrizeclientidsql= "SELECT DISTINCT j.db_app_clientid FROM order_for_goods AS j WHERE j.db_goods_singleid= ?  AND j.db_app_clientid != ? ";
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		//key任务组和名称
		  JobKey key = context.getJobDetail().getKey();
		//JobDataMap  创建任务时所设置的参数集合
		  JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		
		  //需要开奖的db_goods_single的id
		  String id= dataMap.getString("id");
		 
		  //获取db_goods_single的service,获取db_join_client的service
		  SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
		  SessionFactory sessionFactory = ApplicationContextUtil.getContext().getBean(SessionFactory.class);
		  
		  //获得single的实体对象
		  DbGoodsSingleEntity goodsSingle =  systemService.get(DbGoodsSingleEntity.class, id);
		
		  
		  
		  
		  
		  
		  
		  /**
		   * 找到中奖的用户
		   */
		  
		  //判断是否已经设置有中奖用户如果有则不进行计算，按后台操作的来
		  //通过goodsSingle中的幸运号   用户的主键id  和自己的id  看是否能找到参加的用户；
		  Long A=0L;
		
		  if(!StringUtil.isEmpty(goodsSingle.getLuckyId())){
			  //如果有后台给的中奖用户，且给的是真实存在的
			  org.jeecgframework.core.util.LogUtil.info("后台已经修改中奖号，跳过计算！");
			  
		  }else{
			  //如果没有后台设置，则根据规则进行计算
			  //获得最后参与抽奖的用户的60个信息
			  Session session = sessionFactory.openSession();
			  
			  String HQL ="FROM  DbJoinClientEntity j WHERE j.dbGoodsSingle= ? ORDER BY j.joinTime DESC ";
			 
			  Query query = session.createQuery(HQL);
			  query.setString(0, id);
			  query.setFirstResult(0);
			  query.setMaxResults(60);
			  
			  List<DbJoinClientEntity> joinlist = query.list();
			 
			  //数值A=为截至该商品最后购买时间点前最后60条参与记录的参与时间(时、分、秒和毫秒)相加
			  //获得数组A
			
			  int num = 0;
			  for(DbJoinClientEntity client:joinlist){
				  if(num<=4 || num >=joinlist.size()-5){
					  num++;
					  continue;
				  }else{
					 A=A+stringTOlong(client.getJoinTime());
					  num++;
				  }
			  }
			  
			  //获得数值B
			  Long B=Long.valueOf(goodsSingle.getAllJoinNum());
			  
			  //算出幸运号，然后对幸运号进行查找
			  Long luckyid= (A % B) +   10000001;
			  
			  //根据查找出的幸运号找到对应的参加用户
			  Query query2= session.createQuery(hql2);
			  query2.setString(0, luckyid.toString());
			  query2.setString(1, id);
			  List<DbJoinClientEntity> joinlist2 = query2.list();
			  DbJoinClientEntity  join=joinlist2.get(0);
			 
			  //设置中奖用户到single中并更新
			  goodsSingle.setDbAppClientid(join.getDbAppClientid());
			  goodsSingle.setLuckyId(luckyid.toString());
			 
			  session.close();  
			  
			  /**
			   * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			   * 关键型内容
			   * 如果参与的人数是大于100,删除join表中，所需要的最后参与60条数据，其他的数据完全删除掉，使数据库资源释放
			   * 
			   */
			  if(goodsSingle.getAllJoinNum()>100){
				  //删除所有
				  String sql = "DELETE FROM  db_join_client  WHERE db_goods_single= ?";
				  systemService.executeSql(sql, id);
				  //保存60
				  for(DbJoinClientEntity entity:joinlist){
						 systemService.save(entity);
					  }
			  
			  }
		  }


		/**
		 * 推送中奖消息，在后面统一做
		 */
		DbGoodsEntity good=systemService.findUniqueByProperty(DbGoodsEntity.class, "id", goodsSingle.getDbGoodsid());
		String content= "恭喜您中奖了！"+good.getGoodsName()+"如收货地址为空或有误请尽快联系在线客服！如无特殊情况，商家将在三个工作日内发货，敬请期待！感谢您对1圆梦一路的支持！中奖了";

		JpushUtils.push2alias(content,goodsSingle.getDbAppClientid());
		  
		  
		  
		  
		  
			 /**
			  * 中奖后需要进行的操作
			  * 1.修改db_goods_single中的状态，将商品的状态由1  正在开奖中变成2
			  * 2.设置中奖的用户和幸运号到sinlge中
			  * 3.修改用户的中奖信息，
			  * 4.所有没有获得奖品的用户，送给参与者一个优惠卷
			  * 5.推送消息给用户提醒中奖
			  * 6.发送相应的中奖消息给商家，提示商家发货
			  */
		  //修改状态为：已开奖，未发货
		  
		  //修改计算时设置的A值
		  //保存goodssingle  
		  goodsSingle.setStatus(2);
		//修改goodsSingle中中奖用户的参与次数
		 List<Map<String,Object>> listmap= systemService.findForJdbc(sql2,goodsSingle.getDbAppClientid(),goodsSingle.getId());
		 if(listmap.size()>0){
			 //设置中奖用户的参与次数
			 goodsSingle.setClientJoinNum(Integer.valueOf(listmap.get(0).get("allJionNum").toString()));
		 }
		 //设置中奖时的A值
		 if(StringUtil.isEmpty(goodsSingle.getAvalue())){
			 goodsSingle.setAvalue(A.toString());
		 }
		  
		  systemService.saveOrUpdate(goodsSingle);
		  
		 DbAppClientEntity ac= systemService.get(DbAppClientEntity.class,goodsSingle.getDbAppClientid());
		 
		 ac.setWinNum(ac.getWinNum()+1);
		 systemService.saveOrUpdate(ac);
		 
		
		 
		 

		 
		 

		 
		 /*
		  * 推送商家，后面统一做
		  */
		 
		 
		 
		 
		 /**
		  * 所有没有获得奖品的用户，送给参与者一个优惠卷
		  * 前提该商品有优惠卷
		  */
		 List<Map<String,Object>> notPrizeClientList = systemService.findForJdbc(notPrizeclientidsql,goodsSingle.getId(),goodsSingle.getDbAppClientid());
		 //通过singlegoods获得goods信息
		 DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, goodsSingle.getDbGoodsid());
		 //通过goods获得该商品的优惠卷信息
		 DbCouponBusinessEntity   coupon = systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
		 
		 if(coupon!=null){
			 	//调用一个存储过程来完成给没有中奖用户发优惠卷
				 StringBuffer listString = new StringBuffer();
					for (int i = 0; i < notPrizeClientList.size(); i++) {  
			            if ( i+1!=notPrizeClientList.size()) {  
			            	listString.append((String)notPrizeClientList.get(i).get("db_app_clientid") + ",");  
			            } else {  
			            	listString.append((String)notPrizeClientList.get(i).get("db_app_clientid")); 
			            	systemService.executeSql("CALL  give_goodscoupon(?,?)", listString.toString(),coupon.getId());
			            	listString.setLength(0);
			            }  
			        } 
		 }
		 
		 
		 
		 /**
		   * 开奖后需要的操作
		   * 1.如果当前的期数小于总的期数，则继续进行开奖
		   * 2.如果当前的期数等于总的期数，则不再开奖，并进行db_goods的状态操作，将上架中的状态5改为6（所有交易已经完成，未返还保证金）
		   */
		  //先判断当前的期数
		 if(goodsSingle.getGoodsCurrentNum().equals(goodsSingle.getGoodsAllNum())){
			 //已经是最后一期，不在商家则进行对db_gooods进行操作
			
			 goods.setStatus("6");
			 
			 systemService.saveOrUpdate(goods);
			 
		 }else{
			 //下一期的商品进行上架
			 DbGoodsSingleEntity goodsSingleNext = new DbGoodsSingleEntity();
			 goodsSingleNext.setAllJoinNum(goodsSingle.getAllJoinNum());
			 goodsSingleNext.setCurrentJoinNum(0);
			 goodsSingleNext.setDbAppClientid("");
			 goodsSingleNext.setDbGoodsid(goodsSingle.getDbGoodsid());
			 goodsSingleNext.setGoodsAllNum(goodsSingle.getGoodsAllNum());
			 goodsSingleNext.setGoodsCurrentNum(goodsSingle.getGoodsCurrentNum()+1);
			 goodsSingleNext.setLuckyId("");
			 goodsSingleNext.setShare(0);
			 goodsSingleNext.setStarTime(DateUtils.getDate());
			 goodsSingleNext.setStatus(0);
			 goodsSingleNext.setClientJoinNum(0);
			 goodsSingleNext.setAvalue("");
			 
			 systemService.save(goodsSingleNext);
			 
			 /**
			  * 创建所有的参与号
			  * 用来随机给用户发购买的号码
			  */
			 try {
					//1.获得定时任务
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				//2.任务中的执行部分,任务名中用一个随机字符串
				String random =UUIDGenerator.generate().substring(24, 28);
				JobDetail job = newJob(putGoodsTask.class)
	    	    .withIdentity("job"+random, "group"+random)
	    	    .usingJobData("singleid", goodsSingleNext.getId())//需要开奖的single商品的id传入任务中
	    	    .usingJobData("joinNum", goodsSingleNext.getAllJoinNum())
	    	    .build();
				//3.任务中的定时部分
				SimpleTrigger trigger = (SimpleTrigger) newTrigger()
	            .withIdentity("trigger", "group"+random)
	            .startAt(futureDate(0, IntervalUnit.MINUTE)) //3分钟的定时，且执行一次
	            .build();
				//4.定时任务开始
				scheduler.scheduleJob(job,trigger);  	
	        	scheduler.start();
				
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			 
		 }
			 
		  

	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss:SSS字符串转换为一个Long
	 */
	public static Long stringTOlong(String stringtime){
		return Long.valueOf(stringtime.replace("-", "").replace(":", "").replace(".", "").replace(" ", "").substring(8, 17));
		
	}
	
}
