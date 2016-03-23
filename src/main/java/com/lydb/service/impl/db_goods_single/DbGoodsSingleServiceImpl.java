package com.lydb.service.impl.db_goods_single;

import com.lydb.controller.app.winPrize.openPrize;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.lydb.entity.orderforgoods.OrderForGoodsEntity;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jeecgframework.core.common.dao.impl.CommonDao;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service("dbGoodsSingleService")
@Transactional(rollbackFor = Exception.class)
public class DbGoodsSingleServiceImpl extends CommonServiceImpl implements DbGoodsSingleServiceI {

    @Autowired
    private CommonDao commonDao;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((DbGoodsSingleEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((DbGoodsSingleEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((DbGoodsSingleEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     *
     * @return
     */
    public boolean doAddSql(DbGoodsSingleEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     *
     * @return
     */
    public boolean doUpdateSql(DbGoodsSingleEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     *
     * @return
     */
    public boolean doDelSql(DbGoodsSingleEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @return
     */
    public String replaceVal(String sql, DbGoodsSingleEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{db_goodsid}", String.valueOf(t.getDbGoodsid()));
        sql = sql.replace("#{goods_current_num}", String.valueOf(t.getGoodsCurrentNum()));
        sql = sql.replace("#{goods_all_num}", String.valueOf(t.getGoodsAllNum()));
        sql = sql.replace("#{star_time}", String.valueOf(t.getStarTime()));
        sql = sql.replace("#{status}", String.valueOf(t.getStatus()));
        sql = sql.replace("#{share}", String.valueOf(t.getShare()));
        sql = sql.replace("#{current_join_num}", String.valueOf(t.getCurrentJoinNum()));
        sql = sql.replace("#{all_join_num}", String.valueOf(t.getAllJoinNum()));
        sql = sql.replace("#{lucky_id}", String.valueOf(t.getLuckyId()));
        sql = sql.replace("#{avalue}", String.valueOf(t.getAvalue()));
        sql = sql.replace("#{client_join_num}", String.valueOf(t.getClientJoinNum()));
        sql = sql.replace("#{db_app_clientid}", String.valueOf(t.getDbAppClientid()));
        sql = sql.replace("#{open_time}", String.valueOf(t.getOpenTime()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 封装抢币购买操作，具有回滚能力
     * <p/>
     * list != null 表示购买成功
     */
    public String doBuyGoods(String clientid, String GoodsId, int rmb) {

        List li = null;
        String buylist = "";
        Session s = null;
        try {
            //由于取出的数据个数有限制，获得session进行处理
            s = commonDao.getSession();
            //对购买进行优化
            SQLQuery query = s.createSQLQuery("{CALL buy_dbcode(?,?,?,?)}");
            query.setInteger(0, rmb);
            query.setString(1, clientid);
            query.setString(2, clientid);
            query.setString(3, GoodsId);
            li = query.list();
            buylist = li.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        successBuy(s, buylist, GoodsId, clientid, rmb);

        //操作成功，返回购买到的幸运号
        return buylist;


    }

    /**
     * 封装在线购买购买操作，具有回滚能力
     * <p/>
     * list != null 表示购买成功
     */
    public String doOnliBuyGoods(String clientid, String GoodsId, int rmb) {

        List li = null;
        String buylist = "";
        Session s = null;
        try {
            //由于取出的数据个数有限制，获得session进行处理
            s = commonDao.getSession();
            //对购买进行优化
            SQLQuery query = s.createSQLQuery("{CALL buy_dbcode(?,?,?,?)}");
            query.setInteger(0, rmb);
            query.setString(1, clientid);
            query.setString(2, "");
            query.setString(3, GoodsId);
            li = query.list();
            buylist = li.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        successBuy(s, buylist, GoodsId, clientid, rmb);

        //操作成功，返回购买到的幸运号
        return buylist;
    }

    /**
     * 根据传入的goodsId,返回一个DBgoodsSingleEntity
     */
    public DbGoodsSingleEntity searchByGoodsid(String GoodsId) {
        Session s = commonDao.getSession();
        DbGoodsSingleEntity goodssingles;
        DbGoodsEntity goods = (DbGoodsEntity)commonDao.get(DbGoodsEntity.class, GoodsId);
        if(Integer.valueOf(goods.getStatus())<6){
            goodssingles = (DbGoodsSingleEntity) s.createQuery("FROM  DbGoodsSingleEntity s WHERE s.dbGoodsid=? AND s.status=0 or s.status=1").setString(0, GoodsId).list().get(0);
        }else{
            goodssingles = (DbGoodsSingleEntity) s.createQuery("FROM  DbGoodsSingleEntity s WHERE s.dbGoodsid=? AND s.goodsCurrentNum=s.goodsAllNum ");
        }
        return goodssingles;
    }
    /**
     * 根据用户的id，返回用户剩余的抢币个数
     */
    public int userRMBById(String userId){
        DbAppClientRmbEntity user=(DbAppClientRmbEntity)commonDao.findUniqueByProperty(DbAppClientRmbEntity.class,"idAppClientid",userId);
        return user.getRmb();
    }
    /**
     * 因为购买失败，将用户的钱，返还到抢币中
     */
    public void returnByRMB(String userId,int num){
        DbAppClientRmbEntity user=(DbAppClientRmbEntity)commonDao.findUniqueByProperty(DbAppClientRmbEntity.class,"idAppClientid",userId);
        user.setRmb(user.getRmb()+num);
        commonDao.saveOrUpdate(user);
        return ;
    }
    /*
    购买成功后生成订单并看是否要开奖
     */
    private void successBuy(Session s, String buylist, String GoodsId, String clientid, int rmb) {
        if (buylist.length() > 0) {
            //获得要操作的singlegoods
            DbGoodsSingleEntity goodssingles = (DbGoodsSingleEntity) s.createQuery("FROM DbGoodsSingleEntity s WHERE s.dbGoodsid=? AND s.status=0 ").setString(0, GoodsId).list().get(0);
            DbAppClientEntity client = (DbAppClientEntity) commonDao.get(DbAppClientEntity.class, clientid);
            /**
             * 购买成功生成该用户的购买订单信息
             *
             */
            OrderForGoodsEntity order = new OrderForGoodsEntity();
            order.setDbAppClientid(clientid);
            order.setDbGoodsSingleid(goodssingles.getId());
            order.setIpAddress(client.getIpAddress());
            order.setAddressInfo(client.getAddressInfo());
            order.setRmbNum(rmb);
            order.setCreateTime(DateUtils.datemillFormat.format(new java.util.Date()).toString());
            //本次订单的夺宝号
            order.setOrderCode(buylist);
            commonDao.save(order);

            /**
             * 购买后判断商品参与者是否已经达到购买人数
             * 如果达到，则进行倒计时任务操作
             */
            if (goodssingles.getAllJoinNum().equals(goodssingles.getCurrentJoinNum())) {
                //修改当前的状态,为1
                goodssingles.setStatus(1);
                //设置开奖时间为当前时间的3分钟后
                goodssingles.setOpenTime(new java.util.Date(System.currentTimeMillis() + 3 * 60 * 1000L));
                //设置三分钟后开奖任务

                try {
                    //1.获得定时任务
                    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                    //2.任务中的执行部分,任务名中用一个随机字符串
                    String random = UUIDGenerator.generate().substring(24, 28);
                    JobDetail job = newJob(openPrize.class)
                            .withIdentity("job" + random, "group" + random)
                            .usingJobData("id", goodssingles.getId())//需要开奖的single商品的id传入任务中
                            .build();
                    //3.任务中的定时部分
                    SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                            .withIdentity("trigger", "group" + random)
                            .startAt(futureDate(3, DateBuilder.IntervalUnit.MINUTE)) //3分钟的定时，且执行一次
                            .build();
                    //4.定时任务开始
                    scheduler.scheduleJob(job, trigger);
                    scheduler.start();

                } catch (SchedulerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //保存修改状态和开奖时间的goodssingle信息
                commonDao.saveOrUpdate(goodssingles);
            }
        }
    }


    @Override
    public void dochangeLuckId(String singlegoodsId, String luckyId)
            throws Exception {
        DbGoodsSingleEntity single = (DbGoodsSingleEntity) commonDao.get(DbGoodsSingleEntity.class, singlegoodsId);
        //如果只剩下不到20s时，不允许修改
        if (single == null | single.getOpenTime().getTime() - 1000 * 20 < System.currentTimeMillis()) {
            throw new Exception();
        }
        //luckyId必须是在范围内
        if (Long.valueOf(luckyId) - 10000000 > single.getAllJoinNum() || Long.valueOf(luckyId) - 10000000 <= 0) {
            throw new Exception();
        }

        //数据正常，开始后台修改，只改一个人保证数据正常
        Session session = commonDao.getSession();
        String HQL = "FROM  DbJoinClientEntity j WHERE j.dbGoodsSingle= ? ORDER BY j.joinTime DESC ";
        Query query = session.createQuery(HQL);
        query.setString(0, singlegoodsId);
        query.setFirstResult(0);
        query.setMaxResults(60);

        List<DbJoinClientEntity> joinlist = query.list();

        //数值A=为截至该商品最后购买时间点前最后60条参与记录的参与时间(时、分、秒和毫秒)相加
        //获得数组A
        Long A = 0L;
        int num = 0;
        for (DbJoinClientEntity client : joinlist) {
            if (num <= 4 || num >= joinlist.size() - 5) {
                num++;
                continue;
            } else {
                A = A + stringTOlong(client.getJoinTime());
                num++;
            }
        }
        //获得数值B
        Long B = Long.valueOf(single.getAllJoinNum());
        Long lucky = (A % B) + 10000001;

        A = A + Long.valueOf(luckyId) - lucky;
        if (single.getAllJoinNum() < 60000) {
            //拿出一个参加的对象改掉他参加时间
            joinlist.get(6).setJoinTime(changeJoinTime(joinlist.get(6).getJoinTime(), lucky, Long.valueOf(luckyId), B));
        }
        //根据查找出的幸运号找到对应的参加用户
        String hql2 = "FROM  DbJoinClientEntity j  WHERE j.dbCode = ? AND j.dbGoodsSingle = ? ";
        Query query2 = session.createQuery(hql2);
        query2.setString(0, luckyId);
        query2.setString(1, singlegoodsId);
        List<DbJoinClientEntity> joinlist2 = query2.list();
        DbJoinClientEntity join = joinlist2.get(0);

        //设置中奖用户到single中并更新
        single.setDbAppClientid(join.getDbAppClientid());
        single.setLuckyId(luckyId);
        single.setAvalue(A.toString());


        if (single.getAllJoinNum() > 100) {
            //删除所有
            String sql = "DELETE FROM  db_join_client  WHERE db_goods_single= ?";
            commonDao.executeSql(sql, singlegoodsId);
            //保存60
            for (DbJoinClientEntity entity : joinlist) {
                commonDao.save(entity);
            }

        }
        commonDao.saveOrUpdate(single);

    }

    /**
     * 修改一个yyyy-MM-dd HH:mm:ss:SSS，使除出来的余数是自己设定的
     */
    private static String changeJoinTime(String Jointime, Long testA, Long testB, Long num) {
        String s = Jointime;//需要修改的时间
        String m = s.substring(17, 23).replace(".", "").replace(":", "");
        String f = s.substring(0, 17);

        //testA;原来的余数
        // testB;想要的余数
        //num 被除数
        Long result = Long.valueOf(m) + testB - testA;
        if (result > 59999) {
            result = result - num;
        } else if (result < 0) {
            result = result + num;
        }
        String str = String.format("%05d", result);
        return f + str.substring(0, 2) + "." + str.substring(2, 5);

    }


    /**
     * 将yyyy-MM-dd HH:mm:ss:SSS字符串转换为一个Long
     */
    private static Long stringTOlong(String stringtime) {
        return Long.valueOf(stringtime.replace("-", "").replace(":", "").replace(".", "").replace(" ", "").substring(8, 17));

    }


}