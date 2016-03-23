package com.xingluo.timetask;

import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
/**
 * 定时任务demo
 * @author fy
 *
 */

/*@Service("XingluoTask")*/
public class XingluoTask {
		
	@Autowired
	private SystemService systemservice;
		
	@Scheduled(cron="0 0/1 * * * ?")
	public void run(){
		long start = System.currentTimeMillis();
		org.jeecgframework.core.util.LogUtil.info("===================demo任务开始===================");
		try {
			System.out.println("demo 被执行");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		org.jeecgframework.core.util.LogUtil.info("===================demo任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒");
	}
}
