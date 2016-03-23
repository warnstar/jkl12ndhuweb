package com.lydb.controller.root;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.lydb.controller.app.winPrize.putGoodsTask;
import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.entity.orderforgoods.OrderForGoodsEntity;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 商品管理控制器
 * @author onlineGenerator
 * @date 2015-11-25 10:27:17
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/GoodsManagement")
public class GoodsManagement extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GoodsManagement.class);

	@Autowired
	private DbGoodsServiceI dbGoodsService;
	@Autowired
	private DbGoodsSingleServiceI singleService;
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
	 * 商家未上架商品
	 * fy
	 */
	@RequestMapping(params = "NotPutGoods")
	public ModelAndView NotPutGoods( HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("com/lydb/root/NotPutGoods").addObject("id", "");
	}

	/**
	 * 普通商品  status 为 5-8的Tab页面跳转
	 * 复用BussinessManagement的商家tab页面
	 * 通过将查询的BusinessId修改为LIKE匹配空
	 */
	@RequestMapping(params = "GoodsTabs")
	public ModelAndView GoodsTabs(HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView("com/lydb/root/BusinessGoodsList").addObject("id", "");
	}
	
	/**
	 * 审核商品接口
	 * 审核通过 2   审核拒绝3
	 */
	@RequestMapping(params =  "checkGoods")
	@ResponseBody
	public AjaxJson checkGoods(String id,String status){
		AjaxJson j = new AjaxJson();
		DbGoodsEntity goods = dbGoodsService.get(DbGoodsEntity.class, id);
		if(goods.getStatus().equals("1")){
			goods.setStatus(status);
			dbGoodsService.saveOrUpdate(goods);
			//如果商品是审核通过,则给商品建立一个付费信息payment
			if(status.equals("2")){
				DbGoodsPaymentEntity payment = new DbGoodsPaymentEntity();
				payment.setDbGoodsid(goods.getId());
				payment.setServicefee(goods.getGoodsRmb()*goods.getGoodsNum()*0.1);
				payment.setDeposit(goods.getGoodsRmb()*goods.getGoodsNum()*0.4);
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
	 * 商品上架，商品状态变成5
	 * 创建商品的开奖列表 db_goods_single
	 * 拆分成一个一个的物品进行抽奖
	 * 没上架一个商品，就创建所有的参与者的实体，将中奖号码随机放入
	 */
	@RequestMapping(params =  "putGoods")
	@ResponseBody
	public AjaxJson putGoods(String id,String status){
		AjaxJson j = new AjaxJson();
		DbGoodsEntity goods = dbGoodsService.get(DbGoodsEntity.class, id);
		if(goods.getStatus().equals("4")){

			//创建商品的开奖列表db_goods_single,并保存


			DbGoodsSingleEntity single = null;
			try {
				single = new DbGoodsSingleEntity();
				single.setDbGoodsid(id);
				single.setGoodsAllNum(goods.getGoodsNum());
				single.setGoodsCurrentNum(1);
			/*
			 * 如果商品的单价是小于60，则还是以单价，进行参与
			 * 商品在录入过程中就需要控制每个商品的单价不能低于20元
			 *
			 */

				single.setAllJoinNum(goods.getGoodsRmb());
				single.setCurrentJoinNum(0);
				single.setLuckyId("");
				single.setDbAppClientid("");
				single.setShare(0);
				single.setStarTime(DateUtils.getDate());
				single.setStatus(0);
				single.setClientJoinNum(0);
				single.setAvalue("");
				singleService.save(single);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//上架后先将商品的状态修改为5上架中，表示该商品正在商品池中进行抽奖
				goods.setStatus("5");
				goods.setCreateTime(DateUtils.getDate());
				dbGoodsService.saveOrUpdate(goods);
			}

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
    	    .usingJobData("singleid", single.getId())//需要开奖的single商品的id传入任务中
    	    .usingJobData("joinNum", single.getAllJoinNum())
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
			
		}else{
			j.setMsg("修改失败");
			j.setSuccess(false);
		}
		
		return j;
	}
	
	
	
	/**
	 * 修改中奖号码页面跳转
	 */
	@RequestMapping(params = "goChangeLuckId")
	public ModelAndView goChangeLuckId(String id){
		return new ModelAndView("com/lydb/root/ChangeLuckId").addObject("id", id);
	}
	@RequestMapping(params =  "doChangeLuckId")
	@ResponseBody
	public AjaxJson doChangeLuckId(String id,String luckyid){
		AjaxJson j = new AjaxJson();
		try{
			//修改中奖号
			//小于6万可以解决时间的修改，大于6万只是修改了A值
			singleService.dochangeLuckId(id, luckyid);	
		}catch(Exception e){
			j.setMsg("修改失败");
			j.setSuccess(false);
			return j;
		}
		return j;
	}
	
	
	/**
	 * 查看参与用户的列表页面跳转
	 */
	@RequestMapping(params = "checkJoinClient")
	public ModelAndView checkJoinClient(String id){
		return new ModelAndView("com/lydb/root/checkJoinClient").addObject("id", id);
	}
	@RequestMapping(params = "checkJoindatagrid")
	public void checkJoindatagrid(String id ,String mobile,String allcode,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = this.dbGoodsService.getcheckJoinClient(id,mobile,allcode, dataGrid);
		rootUtil.responseDatagrid(response, jObject);
	}
		
	
	/**
	 * 确认已经退款
	 */
	@RequestMapping(params = "confirmReturnRMB")
	@ResponseBody
	public AjaxJson confirmReturnRMB(String id){
		AjaxJson j = new AjaxJson();
		DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, id);
		DbGoodsPaymentEntity payment = systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", id);
		DbWebBusinessEntity bus = systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
		
		//    8.确认返还保证金，商品交易关闭
		goods.setStatus("8");
		//    2.已退款（在所有晒单完成后，退还保证金）
		
		payment.setPaymentStatus(2);
		payment.setEndtime(DateUtils.getDate());
		
		systemService.saveOrUpdate(goods);
		systemService.saveOrUpdate(payment);
		
		AccountLogEntity acc=new AccountLogEntity();
		acc.setAccountType(0);
		acc.setBusinessMobile(bus.getMobile());
		acc.setContent("返还商家："+bus.getName()+"关于商品："+goods.getGoodsName()+"的保证金！");
		acc.setCreateTime(DateUtils.getDate());
		acc.setDbWebBusinessid(bus.getId());
		acc.setOrderNum(UUIDGenerator.generate());
		acc.setRmb(payment.getDeposit());
		systemService.save(acc);
		
		
		j.setMsg("确认返还保证金，商品交易关闭！");
		
		return j;
	}
	
	/**
	 * 删除商品主表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DbGoodsEntity dbGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		dbGoods = systemService.getEntity(DbGoodsEntity.class, dbGoods.getId());
		message = "商品主表删除成功";
		try{
			dbGoodsService.delete(dbGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

}
