package com.lydb.controller.app.winPrize;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
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

import com.lydb.entity.db_coupon_business.DbCouponBusinessEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.lydb.entity.db_join_client2.DbJoinClient2Entity;
import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.xingluo.util.JpushUtils;

public class putZeroGoodsTask implements Job {
    private static String hql2 = "FROM  DbJoinClient2Entity j  WHERE j.dbCode = ? AND j.dbZgoodsSingleid = ? ";
    private static String notPrizeclientidsql = "SELECT DISTINCT j.db_app_clientid FROM db_join_client2 AS j WHERE j.db_zgoods_singleid= ?  AND j.db_app_clientid != ? ";


    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        //key任务组和名称
        JobKey key = context.getJobDetail().getKey();
        //JobDataMap  创建任务时所设置的参数集合
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String singleid = dataMap.getString("singleid");


        //获取service
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        SessionFactory sessionFactory = ApplicationContextUtil.getContext().getBean(SessionFactory.class);


        //获取singlegoods的对象
        DbZgoodsSingleEntity zgoodssingle = systemService.get(DbZgoodsSingleEntity.class, singleid);

        //通过singlegoods获得goods信息
        DbZeroGoodsEntity zgoods = systemService.get(DbZeroGoodsEntity.class, zgoodssingle.getDbZeroGoodsid());

        if(zgoodssingle.getCurrentJoinNum() == 0){
            //直接将开奖时间继续增加
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            zgoodssingle.setOpenTime(DateUtils.getDate(cal.getTimeInMillis()+18*60*60*1000+24*3600*1000*zgoods.getTime()));
            systemService.saveOrUpdate(zgoodssingle);
            return ;
        }

        if (StringUtil.isEmpty(zgoodssingle.getLuckyId()) && StringUtil.isEmpty(zgoodssingle.getDbAppClientid())) {
            Long luckyid = Long.valueOf(DateUtils.openZerogoods.format(zgoodssingle.getOpenTime())) % zgoodssingle.getCurrentJoinNum() + 10000001;

            //找到中奖者
            DbJoinClient2Entity join = null;

            Session session = sessionFactory.openSession();

            //根据查找出的幸运号找到对应的参加用户
            Query query2 = session.createQuery(hql2);
            query2.setString(0, luckyid.toString());
            query2.setString(1, singleid);
            List<DbJoinClient2Entity> joinlist2 = query2.list();
            join = joinlist2.get(0);


            //保存中奖者信息
            zgoodssingle.setDbAppClientid(join.getDbAppClientid());
            zgoodssingle.setLuckyId(luckyid.toString());

            session.close();

        }

        zgoodssingle.setStatus(2);
        systemService.saveOrUpdate(zgoodssingle);

        DbZeroGoodsEntity good=systemService.findUniqueByProperty(DbZeroGoodsEntity.class, "id", zgoodssingle.getDbZeroGoodsid());
        /**
         * 推送中奖消息，在后面统一做
         */
    
	      String content= "恭喜您中奖了！"+good.getZgoodsName()+"信息与收货地址已发送给商家，如收货地址为空或有误请尽快联系在线客服！如无特殊情况，商家将在三个工作日内发货，敬请期待！感谢您对1圆梦一路的支持！中奖了";
		JpushUtils.push2alias(content,zgoodssingle.getDbAppClientid());
			 
			 /*
			  * 推送商家，后面统一做
			  */


        /**
         * 所有没有获得奖品的用户，送给参与者一个优惠卷
         * 前提该商品有优惠卷
         */
        List<Map<String, Object>> notPrizeClientList = systemService.findForJdbc(notPrizeclientidsql, zgoodssingle.getId(), zgoodssingle.getDbAppClientid());

        //通过goods获得该商品的优惠卷信息
        DbZcouponBusinessEntity coupon = systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", zgoods.getId());

        if (coupon != null) {
            //调用一个存储过程来完成给没有中奖用户发优惠卷
            StringBuffer listString = new StringBuffer();
            for (int i = 0; i < notPrizeClientList.size(); i++) {
                if (i + 1 != notPrizeClientList.size()) {
                    listString.append((String) notPrizeClientList.get(i).get("db_app_clientid") + ",");
                } else {
                    listString.append((String) notPrizeClientList.get(i).get("db_app_clientid"));
                    systemService.executeSql("CALL  give_zero_goodscoupon(?,?)", listString.toString(), coupon.getId());
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
        if (zgoodssingle.getGoodsCurrentNum().equals(zgoodssingle.getGoodsAllNum())) {
            zgoods.setStatus("6");
            systemService.saveOrUpdate(zgoods);

        } else {
            // 上架下一期
            DbZgoodsSingleEntity nextsingle = new DbZgoodsSingleEntity();
            nextsingle.setCurrentJoinNum(0);
            nextsingle.setDbAppClientid("");
            nextsingle.setDbZeroGoodsid(zgoodssingle.getDbZeroGoodsid());
            nextsingle.setGoodsAllNum(zgoodssingle.getGoodsAllNum());
            nextsingle.setGoodsCurrentNum(zgoodssingle.getGoodsCurrentNum() + 1);
            nextsingle.setLuckyId("");
            nextsingle.setShare(0);
            nextsingle.setStatus(1);
            nextsingle.setStarTime(DateUtils.getDate());
           /* nextsingle.setOpenTime(DateUtils.getDate(System.currentTimeMillis() + 24 * 3600 * 1000 * zgoods.getTime()));*/
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);

            nextsingle.setOpenTime(DateUtils.getDate(cal.getTimeInMillis()+18*60*60*1000+24*3600*1000*zgoods.getTime()));
            systemService.save(nextsingle);

        }

    }

}
