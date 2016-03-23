package com.xingluo;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		//key任务组和名称
		  JobKey key = context.getJobDetail().getKey();
		//JobDataMap  创建任务时所设置的参数集合
		  
		  JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		  String jobSays = dataMap.getString("jobSays");
		  float myFloatValue = dataMap.getFloat("myFloatValue");

		  System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);

	}

}
