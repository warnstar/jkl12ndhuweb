package com.lydb.controller.app;

import com.lydb.controller.app.winPrize.openPrize;
import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_join_client.DbJoinClientEntity;
import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.entity.orderforgoods.OrderForGoodsEntity;
import com.lydb.entity.rule_table.RuleTableEntity;
import com.lydb.entity.shoppingcart.ShoppingcartEntity;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import com.lydb.service.shoppingcart.ShoppingcartServiceI;
import com.xingluo.util.JpushUtils;
import com.xingluo.util.RestJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.DateBuilder.*;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
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
import org.jeecgframework.core.util.AddressUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

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
import org.json.JSONObject;


/**
 * 购物车接口用来操作用户购买
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/shoppingcart")
public class DbGoodsSingleController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DbGoodsSingleController.class);

    @Autowired
    private DbGoodsSingleServiceI dbGoodsSingleService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ShoppingcartServiceI shoppingcartServiceI;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 用户用抢币购买，商品的幸运号
     * 抢币支付接口‘
     * 参数rmbNum 为用户整个订单需要的抢币个数
     */
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    @ResponseBody
    public RestJson buy(int rmbNum, HttpServletRequest request, @RequestHeader(value = "token") String token) {
        RestJson rest = new RestJson();
        int buynumber = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();//用来组装购买后的所有信息
        if (dbtoken == null) {
            //token不存在 ，返回未知错误
            rest.setError("用户未登录！");
            rest.setCode(7);
            return rest;
        } else {
            /**首先判断用户还有多少抢币，能否购买*/
            DbAppClientRmbEntity clientRmb = systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
            if (clientRmb.getRmb() < rmbNum || rmbNum <= 0) {

                //用户的抢币不足，无法购买
                rest.setError("抢币不足，无法购买");
                map.put("joinAllNum", rmbNum);
                map.put("userrmb", clientRmb.getRmb());
                rest.setInfo(map);
                rest.setCode(8);
            } else {
                /** 通过用户userid找到用户的购物车里面的所有数据*/
                List<ShoppingcartEntity> shoplist = systemService.findByProperty(ShoppingcartEntity.class, "dbappclientid", dbtoken.getDbAppClientid());
                if (shoplist.size() > 0) {
                    for (ShoppingcartEntity shopping : shoplist) {
                        /** 用户的购物车不为空，开始给用户进行购买操作*/
                        Map<String, Object> mapinfo = new HashMap<String, Object>();
                        //购买之前获取一下goodssingle   免得购买后开始开奖  导致获得的那期不是购买的那一期
                        DbGoodsSingleEntity goodssingles = dbGoodsSingleService.searchByGoodsid(shopping.getDbgoodsid());
                        String buycode = dbGoodsSingleService.doBuyGoods(shopping.getDbappclientid(), shopping.getDbgoodsid(), shopping.getNumber());//购买操作，接受买回来的号码
                        //如果出现buycode为""的情况，说明购买出现了失败，但对用的抢币是没有构成影响的，只是没有购买成功而已，app直接显示就可以了
                        //封装需要返回的当前商品的信息，放入listmap中
                        mapinfo.put("buycode", buycode);
                        mapinfo.put("buynum", shopping.getNumber());
                        DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, shopping.getDbgoodsid());

                        mapinfo.put("CurrentNum", goodssingles.getGoodsCurrentNum());
                        mapinfo.put("goodsName", goods.getGoodsName());
                        listmap.add(mapinfo);

                        if (StringUtil.isNotEmpty(buycode)) {
                            buynumber = buynumber + shopping.getNumber();
                            //删除购物车里面的内容
                            systemService.delete(shopping);
                        }
                    }
                    //购买完成，返回code0
                    rest.setCode(0);
                    rest.setAttributes(listmap);
                    //返回用户剩余的抢币信息，和用户使用的抢币信息
                    map.put("buynumber", buynumber);//真正花掉的抢币
                    map.put("userRMB", dbGoodsSingleService.userRMBById(dbtoken.getDbAppClientid()));
                    rest.setInfo(map);
                    
                    /**
                     * 推送一个购买的通知，现阶段不用做，没有意义
                     */
                  /*  JSONObject jpush = new JSONObject();
            		jpush.put("content", "恭喜您，购买宝贝成功，敬请期待开奖");
            		jpush.put("type", 3);
                    JpushUtils.push2alias(jpush.toString(),dbtoken.getDbAppClientid());*/
                } else {
                    rest.setError("该用户的购物车为空！");
                    rest.setCode(9);
                }
            }

        }
      
        
        
        
        return rest;
    }

    /**
     * 用户用支付宝等直接购买购买，商品的幸运号
     * 支付宝等三方工具直接购买
     * 增加参数tradeNum来提交用户的订单号，在跟三方的异步调用时的信息进行判断
     */
    @RequestMapping(value = "/buyOnline", method = RequestMethod.POST)
    @ResponseBody
    public RestJson buyOnline(String tradeNum, int rmbNum, HttpServletRequest request, @RequestHeader(value = "token") String token) {
        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        int buynumber = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();//用来组装购买后的所有信息
        if (dbtoken == null) {
            //token不存在 ，返回未知错误
            rest.setError("未知错误");
            rest.setCode(7);
            return rest;
        } else {
            /**
             * 双重认证，当支付成功后，阿里会调异步接口通知
             * 去查找异步通知中的消息内容， 订单信息orderInfo   用户信息clientId    充值的个数 rmbNum   订单的时间 orderTime
             * 完全匹配后，才具有购买能力
             */
            //根据订单号找到异步调用时的订单信息

            AccountLogEntity acc = systemService.findUniqueByProperty(AccountLogEntity.class, "orderNum", tradeNum);
            if (acc == null) {
                map.put("joinAllNum", rmbNum);
                map.put("userrmb", dbGoodsSingleService.userRMBById(dbtoken.getDbAppClientid()));
                rest.setInfo(map);
                rest.setCode(8);
                rest.setError("订单号不存在，请确认后提交！");
                return rest;
            } else if (acc.getRmb() < rmbNum || rmbNum <= 0) { //订单不为空，且总的个数必须是大于0的情况下才能正常购买
                //将订单中的人民币以抢币的形式返还给用户
                DbAppClientRmbEntity clientRmb = systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", dbtoken.getDbAppClientid());
                clientRmb.setRmb(clientRmb.getRmb() + Integer.valueOf(acc.getRmb().toString()));
                systemService.saveOrUpdate(clientRmb);
                //修改后台存的订单号，使订单不可用
                acc.setOrderNum(acc.getOrderNum()+"-"+UUIDGenerator.generate().substring(10,15));
                systemService.saveOrUpdate(acc);
                map.put("joinAllNum", rmbNum);
                map.put("userrmb", dbGoodsSingleService.userRMBById(dbtoken.getDbAppClientid()));
                rest.setInfo(map);
                rest.setCode(8);
                rest.setError("购买的个数超过订单中的个数，已退回到抢币中，请重新购买！");
                return rest;
            }

            /** 通过用户userid找到用户的购物车里面的所有数据*/
            List<ShoppingcartEntity> shoplist = systemService.findByProperty(ShoppingcartEntity.class, "dbappclientid", dbtoken.getDbAppClientid());
            if (shoplist.size() > 0) {
                for (ShoppingcartEntity shopping : shoplist) {
                    /** 用户的购物车不为空，开始给用户进行购买操作*/
                    Map<String, Object> mapinfo = new HashMap<String, Object>();
                    //购买之前获取一下goodssingle   免得购买后开始开奖  导致获得的那期不是购买的那一期
                    DbGoodsSingleEntity goodssingles = dbGoodsSingleService.searchByGoodsid(shopping.getDbgoodsid());
                    String buycode = dbGoodsSingleService.doOnliBuyGoods(shopping.getDbappclientid(), shopping.getDbgoodsid(), shopping.getNumber());//购买操作，接受买回来的号码
                    //如果出现buycode为""的情况，说明购买出现了失败，但对用的抢币是没有构成影响的，只是没有购买成功而已，app直接显示就可以了
                    //封装需要返回的当前商品的信息，放入listmap中
                    mapinfo.put("buycode", buycode);
                    mapinfo.put("buynum", shopping.getNumber());
                    DbGoodsEntity goods = systemService.get(DbGoodsEntity.class, shopping.getDbgoodsid());

                    mapinfo.put("CurrentNum", goodssingles.getGoodsCurrentNum());
                    mapinfo.put("goodsName", goods.getGoodsName());
                    listmap.add(mapinfo);

                    if (StringUtil.isNotEmpty(buycode)) {
                        buynumber = buynumber + shopping.getNumber();
                        //删除购物车里面的内容
                        systemService.delete(shopping);
                    }else{
                        /**
                         * 出现购买失败的情况
                         * 将用户支付的钱，返还到用户的抢币信息中
                         */
                        dbGoodsSingleService.returnByRMB(dbtoken.getDbAppClientid(),shopping.getNumber());
                    }
                }
                //购买完成，返回code0
                rest.setCode(0);
                rest.setAttributes(listmap);
                //返回用户剩余的抢币信息，和用户使用的抢币信息
                map.put("buynumber", buynumber);//真正花掉的抢币
                map.put("userRMB", dbGoodsSingleService.userRMBById(dbtoken.getDbAppClientid()));
                rest.setInfo(map);
                //购买完成狗修改后台存的订单号，使订单不可用
                acc.setOrderNum(acc.getOrderNum()+"-"+UUIDGenerator.generate().substring(10,15));
                systemService.saveOrUpdate(acc);
              //极光推送购买成功
               /* JSONObject jpush = new JSONObject();
        		jpush.put("content", "恭喜您，购买宝贝成功，敬请期待开奖");
        		jpush.put("type", 3);
                JpushUtils.push2alias(jpush.toString(),dbtoken.getDbAppClientid());*/
            } else {
                rest.setError("该用户的购物车为空！");
                rest.setCode(9);
            }
        }
        
        return rest;
    }

}
