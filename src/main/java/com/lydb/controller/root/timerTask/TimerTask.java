package com.lydb.controller.root.timerTask;

import com.lydb.controller.app.winPrize.putZeroGoodsTask;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.controller.core.SystemController;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 定时任务
 * @author fy
 *
 */

@Service("TimerTask")
public class TimerTask {
		
	@Autowired
	private SystemService systemservice;
		
	/**
	 * 每天早上1点，完成token清理，完成用户的今日积分和今日抢币清空
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void run(){
	
		org.jeecgframework.core.util.LogUtil.info("===================清理数据库任务开始===================");
		try {
			//删除所有已经超时的token，先用三天表示超时
			String outdate = DateUtils.datetimeFormat.format(DateUtils.getDate(System.currentTimeMillis()-3*24*3600*1000));
			String delSQL="DELETE FROM db_token WHERE time < ? ";
			systemservice.executeSql(delSQL, outdate);
			
			//执行每天清理用户的rmb数据 ，当天的rmb和积分全部清空
			systemservice.executeSql("call update_clientrmb() ");

            /*//清理显示为最新的优惠卷
            systemservice.executeSql("CALL clearCoupon()");*/

			//清理用户手机号为空的情况
			systemservice.executeSql("DELETE FROM db_app_client WHERE mobile IS NULL ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.util.LogUtil.info("===================清理数据库任务结束===================");
		
	}

    /**
     * 每天17：50  开始执行是否有需要开奖的零元夺宝
     */
    @Scheduled(cron="0 50 17 * * ?")
    public void openPrize(){

        org.jeecgframework.core.util.LogUtil.info("===================零元夺宝任务开始===================");
        try {
            //获取所有状态为1的零元夺宝的single
            List<DbZgoodsSingleEntity>  list=systemservice.findByProperty(DbZgoodsSingleEntity.class,"status",1);

            for (DbZgoodsSingleEntity single:list){
                //如果single的开奖时间为今天，则打开一个开奖任务
                String today=DateUtils.date_sdf.format(DateUtils.getDate());
                String openTime=DateUtils.date_sdf.format(single.getOpenTime());
                if(today.equals(openTime)){

                    //如果开奖时间为今天，则进行开奖倒计时！
                    try {
                        //1.获得定时任务
                        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                        //2.任务中的执行部分,任务名中用一个随机字符串
                        String random = UUIDGenerator.generate().substring(24, 28);
                        JobDetail job = newJob(putZeroGoodsTask.class)
                                .withIdentity("job"+random, "group"+random)
                                .usingJobData("singleid", single.getId())//需要开奖的single商品的id传入任务中
                                .build();
                        //3.任务中的定时部分
                        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                                .withIdentity("trigger", "group"+random)
                                .startAt(futureDate((int)((single.getOpenTime().getTime()- System.currentTimeMillis())/1000), IntervalUnit.SECOND)) //天数的定时的定时，且执行一次
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        org.jeecgframework.core.util.LogUtil.info("===================零元夺宝任务结束===================");

    }

//补充一个任务，如果今天的开奖任然没有正常完成继续添加一遍任务
   @Scheduled(cron="0 5 18 * * ?")
    public void openPrize2(){

        org.jeecgframework.core.util.LogUtil.info("===================零元夺宝任务开始===================");
        try {
            //获取所有状态为1的零元夺宝的single
            List<DbZgoodsSingleEntity>  list=systemservice.findByProperty(DbZgoodsSingleEntity.class,"status",1);

            for (DbZgoodsSingleEntity single:list){
                //如果single的开奖时间为今天，则打开一个开奖任务
                String today=DateUtils.date_sdf.format(DateUtils.getDate());
                String openTime=DateUtils.date_sdf.format(single.getOpenTime());
                if(today.equals(openTime)){

                    //如果开奖时间为今天，则进行开奖倒计时！
                    try {
                        //1.获得定时任务
                        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                        //2.任务中的执行部分,任务名中用一个随机字符串
                        String random = UUIDGenerator.generate().substring(24, 28);
                        JobDetail job = newJob(putZeroGoodsTask.class)
                                .withIdentity("job"+random, "group"+random)
                                .usingJobData("singleid", single.getId())//需要开奖的single商品的id传入任务中
                                .build();
                        //3.任务中的定时部分
                        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                                .withIdentity("trigger", "group"+random)
                                .startAt(futureDate(10, IntervalUnit.SECOND)) //天数的定时的定时，且执行一次
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        org.jeecgframework.core.util.LogUtil.info("===================零元夺宝任务结束===================");

    }

}
