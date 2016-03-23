package com.xingluo.Junit;


import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.DateBuilder.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jeecgframework.core.common.dao.impl.CommonDao;
import org.jeecgframework.core.util.AddressUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.lydb.controller.app.winPrize.openPrize;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.xingluo.util.IpLocationTool;


public class QuartzTest {

    public static void main(String[] args) {




        List<String> ips = new ArrayList<String>();
        ips.add("119.146.200.16");// 163
        ips.add("115.239.210.26");// 百度
        ips.add("203.88.210.28");// 百纳
        ips.add("14.17.32.211");// qq
        ips.add("210.36.200.2");// 梧州学院
        ips.add("106.3.42.5");// 桂平一中
        ips.add("183.60.55.43");
        ips.add("116.113.134.96");
        ips.add("121.14.231.61");// 凤皇网
        ips.add("10.10.10.10");
        ips.add("114.225.203.69");
        for (String ip : ips) {

            // System.out.println(IpTool.getLoc(ip).getCountry());
            System.out.println(IpLocationTool.getCity(ip));
        }
    /*try {
		System.out.println(UUIDGenerator.generate().substring(24, 28));
		// Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        	//and start it off
        	
        	// define the job and tie it to our HelloJob class
        	JobDetail job = newJob(HelloJob.class)
        	    .withIdentity("job1", "group1")
        	    .usingJobData("jobSays", "123456789")
        		.usingJobData("myFloatValue", 3.14f)
        	    .build();
        	// Trigger the job to run now, and then repeat every 40 seconds
        	
        	Trigger trigger = newTrigger()
        	    .withIdentity("trigger1", "group1")
        	    .startNow()
        	    .withSchedule(simpleSchedule()
        	            .withIntervalInSeconds(5)
        	            .repeatForever())            
        	    .build();
        	

        	
        	
        	SimpleTrigger trigger = (SimpleTrigger) newTrigger()
            .withIdentity("trigger", "group1")
            .startAt(futureDate(1, IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
            //.forJob("group1.job1") // identify job with its JobKey
            .build();

        	
        	// Tell quartz to schedule the job using our trigger
        
        	scheduler.scheduleJob(job,trigger);  	
        	scheduler.start();
        	System.out.println("test2");
        	Thread.sleep(70L*1000L);
        	System.out.println("test3");
        	scheduler.shutdown();
        	System.out.println("test4");
		} catch (Exception se) {
			se.printStackTrace();
			}*/
		
		
		/*System.out.println(openPrize.stringTOlong((new java.sql.Timestamp(System.currentTimeMillis())).toString()));
		System.out.println(DateUtils.datemillFormat.format(new java.util.Date()).toString());*/
		
		/*List<Integer> list = new ArrayList<Integer>();
		for(int i=1; i<=5000 ;i++){
			list.add(i+10000000);
			System.out.println(list.get(i-1));
		}
		Collections.shuffle(list); 
		System.out.println();
		
		Iterator ite = list.iterator();    
        while(ite.hasNext()){    
            System.out.print(ite.next().toString()+",");    
        } 
		Iterator ite = list.iterator();   
		String orderCode = "";
        while(ite.hasNext()){    
        	orderCode=orderCode+ite.next().toString()+",";    
        }   
		System.out.println(orderCode.substring(0, orderCode.length()-1));*/
		
		
	/*	String orderNum="1111".toString()+DateUtils.yyyymmddhhmmss.format(DateUtils.getDate())+UUIDGenerator.generate().substring(10, 15);
		System.out.println(orderNum);
		
		System.out.println(DateUtils.openZerogoods.format(new java.util.Date(System.currentTimeMillis()-24*3600*1000)));
		System.out.println(DateUtils.datetimeFormat.format(DateUtils.getDate(System.currentTimeMillis()-3*24*3600*1000)));*/
		
		/*String s= "2015-12-02 09:11:59.120";//需要修改的时间
		String m = s.substring(17, 23).replace(".", "").replace(":", "");
		String f = s.substring(0, 17);

		Long testA = 12342L;//原来的余数
		Long testB = 2313L;//想要的余数
		int   num = 23233; //被除数
		Long result=	Long.valueOf(m)+testB-testA;
		if(result>59999){
			result=result-num;
		}else if(result<0){
			result=result+num;
		}
		String str =  String.format("%05d", result);
		System.out.println(f+str.substring(0, 2)+"."+str.substring(2, 5));*/
		/*String urls="123,123";
		String[] img_urls=urls.split(",");
		for(String img :img_urls){
			System.out.println(img);
		}
		*/

    }
}