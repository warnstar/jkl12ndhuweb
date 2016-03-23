package com.lydb.controller.root;
import com.lydb.controller.app.winPrize.putZeroGoodsTask;
import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_payment.DbZgoodsPaymentEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;
import com.lydb.service.db_zgoods_single.DbZgoodsSingleServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.TimeZone;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;



/**   
 * @Title: Controller
 * @Description: 0元商品管理控制器
 * @author fy
 * @date 2015-11-25 10:29:12
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ZeroGoodsManagement")
public class ZeroGoodsManagement extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ZeroGoodsManagement.class);

	@Autowired
	private DbZeroGoodsServiceI dbZeroGoodsService;
	@Autowired
	private DbZgoodsSingleServiceI ZgoodsService;
	
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	/*
	 * 未上架零元商品
	 */
	@RequestMapping(params = "NotPutZeroGoods")
	public ModelAndView NotPutZeroGoods( HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("com/lydb/root/NotPutZeroGoods").addObject("id", "");
	}
	
	/**
	 * 0元商品  status 为 5-8的Tab页面跳转
	 */
	@RequestMapping(params = "ZeroGoodsTabs")
	public ModelAndView ZeroGoodsTabs(HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView("com/lydb/root/BusinessZeroGoodsList").addObject("id", "");
	}
	/**
	 * 设置开奖时间间隔页面跳转
	 */
	@RequestMapping(params = "setZeroGoodsTime")
	public ModelAndView setZeroGoodsTime(String ZgoodsID ,HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView("com/lydb/root/setZeroGoodsTime").addObject("id", ZgoodsID);
	}
	/**
	 * 设置开奖时间间隔
	 */
	@RequestMapping(params = "DosetZeroGoodsTime")
	@ResponseBody
	public AjaxJson DosetZeroGoodsTime(String id , String time){
		AjaxJson j = new AjaxJson();
		DbZeroGoodsEntity zgoods = systemService.get(DbZeroGoodsEntity.class, id);
		zgoods.setTime(Integer.valueOf(time));
		systemService.saveOrUpdate(zgoods);
		j.setMsg("设置成功！");
		return j;
	}
	
	/**
	 * 审核商品接口
	 * 审核通过 2   审核拒绝3
	 */
	@RequestMapping(params =  "checkGoods")
	@ResponseBody
	public AjaxJson checkGoods(String id,String status){
		AjaxJson j = new AjaxJson();
		DbZeroGoodsEntity zgoods = systemService.get(DbZeroGoodsEntity.class, id);
		if(zgoods.getStatus().equals("1")){
			zgoods.setStatus(status);
			systemService.saveOrUpdate(zgoods);
			//如果商品是审核通过,则给商品建立一个付费信息payment
			if(status.equals("2")){
				DbZgoodsPaymentEntity  payment= new DbZgoodsPaymentEntity();
				payment.setDbZeroGoodsid(zgoods.getId());
				payment.setServicefee(zgoods.getZgoodsRmb()*zgoods.getZgoodsNum()*0.1);
				payment.setDeposit(zgoods.getZgoodsRmb()*zgoods.getZgoodsNum()*0.4);
				payment.setPaymentStatus(0);
				//保存payment
				systemService.save(payment);
			}
			
		}else{
			j.setMsg("修改失败");
			j.setSuccess(false);
		}
		
		return j;
	}
	
	/**
	 * 0元商品上架，商品状态变成5
	 * 创建商品的开奖列表 db_zgoods_single
	 * 拆分成一个一个的物品进行抽奖
	 * 按顺序发放中奖号，   由于参与的人数是没有上限的，按时间节点来设置中奖时间
	 */
	@RequestMapping(params =  "putGoods")
	@ResponseBody
	public AjaxJson putGoods(String id,String status){
		AjaxJson j = new AjaxJson();
		DbZeroGoodsEntity zgoods = systemService.get(DbZeroGoodsEntity.class, id);
		if(zgoods.getStatus().equals("4")){
			//上架后先将商品的状态修改为5上架中，表示该商品正在商品池中进行抽奖


			try {
				DbZgoodsSingleEntity single = new DbZgoodsSingleEntity();
				single.setDbZeroGoodsid(zgoods.getId());
				single.setGoodsAllNum(zgoods.getZgoodsNum());
				single.setGoodsCurrentNum(1);

				single.setCurrentJoinNum(0);
				single.setLuckyId("");
				single.setDbAppClientid("");
				single.setShare(0);
				single.setStarTime(DateUtils.getDate(System.currentTimeMillis()));
				single.setStatus(1);
			/*single.setOpenTime(DateUtils.getDate(System.currentTimeMillis()+24*3600*1000*zgoods.getTime()));*/

				//将开奖时间设置为间隔多少天后，固定一个时间开奖（为每天的六点中开奖）
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.MILLISECOND, 0);
				single.setOpenTime(DateUtils.getDate(cal.getTimeInMillis()+18*60*60*1000+24*3600*1000*zgoods.getTime()));
				systemService.save(single);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				zgoods.setStatus("5");
				systemService.saveOrUpdate(zgoods);
				//创建商品的开奖列表db_goods_single,并保存
			}

			/**
			 * 设置一个开奖任务定时执行
			 */
			/*
			
			try {
				//1.获得定时任务
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			//2.任务中的执行部分,任务名中用一个随机字符串
			String random =UUIDGenerator.generate().substring(24, 28);
			JobDetail job = newJob(putZeroGoodsTask.class)
    	    .withIdentity("job"+random, "group"+random)
    	    .usingJobData("singleid", single.getId())//需要开奖的single商品的id传入任务中
    	    .build();
			//3.任务中的定时部分
			SimpleTrigger trigger = (SimpleTrigger) newTrigger()
            .withIdentity("trigger", "group"+random)
            .startAt(futureDate(zgoods.getTime(), IntervalUnit.DAY)) //天数的定时的定时，且执行一次
            .build();
			//4.定时任务开始
			scheduler.scheduleJob(job,trigger);  	
        	scheduler.start();
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			*/
		}else{
			j.setMsg("修改失败");
			j.setSuccess(false);
		}
		
		return j;
	}
	
	
	
	
	/**
	 * 修改中奖号码页面跳转
	 */
	@RequestMapping(params = "goChangeZeroLuckId")
	public ModelAndView goChangeZeroLuckId(String id){
		return new ModelAndView("com/lydb/root/ChangeZeroLuckId").addObject("id", id);
	}
	@RequestMapping(params =  "doChangeZeroLuckId")
	@ResponseBody
	public AjaxJson doChangeZeroLuckId(String id,String luckyid){
		AjaxJson j = new AjaxJson();
		try{
			//修改中奖号
			//小于6万可以解决时间的修改，大于6万只是修改了A值
			ZgoodsService.dochangeZeroLuckId(id, luckyid);	
		}catch(Exception e){
			j.setMsg("修改失败");
			j.setSuccess(false);
			return j;
		}
		return j;
	}
	
	
	/**
	 * 确认已经退款
	 */
	@RequestMapping(params = "confirmReturnRMB")
	@ResponseBody
	public AjaxJson confirmReturnRMB(String id){
		AjaxJson j = new AjaxJson();
		DbZeroGoodsEntity zgoods = systemService.get(DbZeroGoodsEntity.class, id);
		DbZgoodsPaymentEntity payment = systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class, "dbGoodsid", id);
		DbWebBusinessEntity bus = systemService.get(DbWebBusinessEntity.class, zgoods.getDbWebBusinessid());
		//    8.确认返还保证金，商品交易关闭
		zgoods.setStatus("8");
		//    2.已退款（在所有晒单完成后，退还保证金）
		
		payment.setPaymentStatus(2);
		payment.setEndtime(DateUtils.getDate());
		
		systemService.saveOrUpdate(zgoods);
		systemService.saveOrUpdate(payment);
		
		AccountLogEntity acc=new AccountLogEntity();
		acc.setAccountType(0);
		acc.setBusinessMobile(bus.getMobile());
		acc.setContent("返还商家："+bus.getName()+"关于商品："+zgoods.getZgoodsName()+"的保证金！");
		acc.setCreateTime(DateUtils.getDate());
		acc.setDbWebBusinessid(bus.getId());
		acc.setOrderNum(UUIDGenerator.generate());
		acc.setRmb(payment.getDeposit());
		systemService.save(acc);
		
		
		j.setMsg("确认返还保证金，商品交易关闭！");
		return j;
	}
	
	

	/**
	 * 删除0元商品主表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DbZeroGoodsEntity dbZeroGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		dbZeroGoods = systemService.getEntity(DbZeroGoodsEntity.class, dbZeroGoods.getId());
		message = "0元商品主表删除成功";
		try{
			dbZeroGoodsService.delete(dbZeroGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "0元商品主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
