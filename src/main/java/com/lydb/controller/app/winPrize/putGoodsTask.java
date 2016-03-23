package com.lydb.controller.app.winPrize;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

public class putGoodsTask implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		//key任务组和名称
		  JobKey key = context.getJobDetail().getKey();
		//JobDataMap  创建任务时所设置的参数集合
		  JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		  
		  SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class); 
		  
		//需要随机的个数和singleid  
		  int joinNum = dataMap.getInt("joinNum");
		  String singleid = dataMap.getString("singleid");
		  
		  List<Integer> listnext = new ArrayList<Integer>();
			for(int i=1; i<=joinNum ;i++){
				listnext.add(i+10000000);
			}
			Collections.shuffle(listnext); 
			  
			StringBuffer listString = new StringBuffer();
			for (int i = 0; i < listnext.size(); i++) {  
	            if ((i+1)%12000 !=0 && i+1!=listnext.size()) {  
	            	listString.append(listnext.get(i) + ",");  
	            } else {  
	            	listString.append(listnext.get(i)); 
	            	systemService.executeSql("CALL  Pr_Rand_insert(?,?)", listString.toString(),singleid);
	            	listString.setLength(0);
	            }  
	        } 
		
	}

}
