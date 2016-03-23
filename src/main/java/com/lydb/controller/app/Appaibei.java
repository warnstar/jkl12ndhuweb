package com.lydb.controller.app;

import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.entity.shoppingcart.ShoppingcartEntity;
import com.lydb.service.db_goods_single.DbGoodsSingleServiceI;
import com.lydb.service.shoppingcart.ShoppingcartServiceI;
import com.xingluo.aibei.IAppPaySDKConfi_ForAndriod;
import com.xingluo.aibei.IAppPaySDKConfig;
import com.xingluo.aibei.Order;
import com.xingluo.aibei.OrderForAndriod;
import com.xingluo.aibei.sign.SignHelper;
import com.xingluo.util.JpushUtils;
import com.xingluo.util.RestJson;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/app_lydb/apppay")
public class Appaibei extends BaseController {
    private static final Logger logger = Logger.getLogger(Appaibei.class);
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
     * 充值获取流水号 For IOS
     * @throws Exception
     */
    @RequestMapping(value = "deposit")
    @ResponseBody
    public RestJson deposit(String cporderid,float price,String type,@RequestHeader(value="token") String token)throws Exception {
        RestJson j = new RestJson();
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if(dbtoken == null){
            j.setError("您的登录过期，请重新登录");
            j.setCode(7);
            return j;
        }else {
            Order order = new Order();
            String transid ="";
            if(type.equals("1")) {
                transid = order.CheckSign(IAppPaySDKConfig.APP_ID, IAppPaySDKConfig.WARES_ID_1, "充值抢币", cporderid, price, dbtoken.getDbAppClientid(), "付款成功", "http://dbback.stzero.cn/xingluo/rest/app_lydb/apppay/async");
            }else if(type.equals("2")){
                transid = order.CheckSign(IAppPaySDKConfig.APP_ID, IAppPaySDKConfig.WARES_ID_1, "充值购买", cporderid, price, dbtoken.getDbAppClientid(), "付款成功", "http://dbback.stzero.cn/xingluo/rest/app_lydb/apppay/asyncOnline");
            }
            j.setInfo(transid);
            j.setCode(0);
        }
        return j;
    }

    /**
     * 充值获取流水号 FOR Andriod
     * @throws Exception
     */
    @RequestMapping(value = "depositForAndriod")
    @ResponseBody
    public RestJson depositForAndriod(String cporderid,float price,@RequestHeader(value="token") String token)throws Exception {
        RestJson j = new RestJson();
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if(dbtoken == null){
            j.setError("您的登录过期，请重新登录");
            j.setCode(7);
            return j;
        }else {
            OrderForAndriod order = new OrderForAndriod();
            String transid ="";
            transid = order.CheckSign(IAppPaySDKConfi_ForAndriod.APP_ID, IAppPaySDKConfi_ForAndriod.WARES_ID_1, "充值抢币", cporderid, price, dbtoken.getDbAppClientid(), "付款成功", "http://dbback.stzero.cn/xingluo/rest/app_lydb/apppay/asyncForAndriod");
            j.setInfo(transid);
            j.setCode(0);
        }
        return j;
    }

    /**
     *异步回调充值接口，收到爱贝的回调后给用户充值
     */
    @RequestMapping(value="async")
    @ResponseBody
    public String async(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
            request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();

        String transdata = null;
        String sign = null;
        String signtype = null;
        try {
            transdata = request.getParameter("transdata");
            sign = (String) request.getParameter("sign");
            signtype = request.getParameter("signtype");
        } catch (Exception e) {
            e.printStackTrace();
            return "FALSE";
        }

        JSONObject json=new JSONObject().fromObject(transdata);
			if(signtype==null)
        		{
        			return "FALSE";
        		}else{

        			/*
        			 * 调用验签接口
        			 *
        			 * 主要 目的 确定 收到的数据是我们 发的数据，是没有被非法改动的
        			 */
        			if (SignHelper.verify(transdata, sign, IAppPaySDKConfig.PLATP_KEY)) {
                        //保存支付成功的信息
                        AccountLogEntity acc;
                        acc=systemService.findUniqueByProperty(AccountLogEntity.class,"orderNum",json.optString("cporderid"));
                        if(acc==null) {
                            DbAppClientEntity client=systemService.get(DbAppClientEntity.class,json.optString("appuserid"));
                            acc = new AccountLogEntity();
                            acc.setAccountType(1);
                            acc.setBusinessMobile("");
                            acc.setDbWebBusinessid("");
                            acc.setContent("App用户爱贝充值！("+client.getMobile()+")");
                            acc.setCreateTime(DateUtils.getDate());
                            acc.setOrderNum(json.optString("cporderid"));
                            acc.setRmb(Double.valueOf(json.optString("money")));
                            systemService.save(acc);

                            /**
                             * 完成用户的充值
                             */
                            DbAppClientRmbEntity userrmb=systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", client.getId());
                            //给充值的用户增加抢币
                            userrmb.setRmb(userrmb.getRmb()+(int)Double.valueOf(json.optString("money")).doubleValue());
                            systemService.saveOrUpdate(userrmb);

                            return "SUCCESS";
                        }else{
                            return "FALSE";
                        }
        			} else {
                        logger.info("交易流水号"+json.optString("transid") + "   用户"+json.optString("appuserid")+"付款不成功");
                        return "FALSE";
        			}
		}
    }

    /**
     *异步回调充值接口，FORAndriod
     */
    @RequestMapping(value="asyncForAndriod")
    @ResponseBody
    public String asyncForAndriod(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();

        String transdata = null;
        String sign = null;
        String signtype = null;
        try {
            transdata = request.getParameter("transdata");
            sign = (String) request.getParameter("sign");
            signtype = request.getParameter("signtype");
        } catch (Exception e) {
            e.printStackTrace();
            return "FALSE";
        }

        JSONObject json=new JSONObject().fromObject(transdata);
        if(signtype==null)
        {
            return "FALSE";
        }else{

        			/*
        			 * 调用验签接口
        			 *
        			 * 主要 目的 确定 收到的数据是我们 发的数据，是没有被非法改动的
        			 */
            if (SignHelper.verify(transdata, sign, IAppPaySDKConfi_ForAndriod.PLATP_KEY)) {
                //保存支付成功的信息
                AccountLogEntity acc;
                acc=systemService.findUniqueByProperty(AccountLogEntity.class,"orderNum",json.optString("cporderid"));
                if(acc==null) {
                    DbAppClientEntity client=systemService.get(DbAppClientEntity.class,json.optString("appuserid"));
                    acc = new AccountLogEntity();
                    acc.setAccountType(1);
                    acc.setBusinessMobile("");
                    acc.setDbWebBusinessid("");
                    acc.setContent("App用户爱贝充值！("+client.getMobile()+")");
                    acc.setCreateTime(DateUtils.getDate());
                    acc.setOrderNum(json.optString("cporderid"));
                    acc.setRmb(Double.valueOf(json.optString("money")));
                    systemService.save(acc);

                    return "SUCCESS";
                }else{
                    return "FALSE";
                }
            } else {
                logger.info("交易流水号"+json.optString("transid") + "   用户"+json.optString("appuserid")+"付款不成功");
                return "FALSE";
            }
        }
    }
    /**
     *异步回调充值接口，收到爱贝的回调后给用户充值
     */
    @RequestMapping(value="asyncOnline")
    @ResponseBody
    public String asyncOnline(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        String transdata = null;
        String sign = null;
        String signtype = null;
        try {
            transdata = request.getParameter("transdata");
            sign = (String) request.getParameter("sign");
            signtype = request.getParameter("signtype");
        } catch (Exception e) {
            e.printStackTrace();
            return  "FALSE";
        }

        JSONObject json=new JSONObject().fromObject(transdata);
        if(signtype==null)
        {
            return "FALSE";
        }else{

        			/*
        			 * 调用验签接口
        			 *
        			 * 主要 目的 确定 收到的数据是我们 发的数据，是没有被非法改动的
        			 */
            if (SignHelper.verify(transdata, sign, IAppPaySDKConfig.PLATP_KEY)) {
                //保存支付成功的信息
                AccountLogEntity acc;
                acc=systemService.findUniqueByProperty(AccountLogEntity.class,"orderNum",json.optString("cporderid"));
                if(acc==null) {
                    DbAppClientEntity client=systemService.get(DbAppClientEntity.class,json.optString("appuserid"));
                    acc = new AccountLogEntity();
                    acc.setAccountType(1);
                    acc.setBusinessMobile("");
                    acc.setDbWebBusinessid("");
                    acc.setContent("App用户爱贝充值！("+client.getMobile()+")");
                    acc.setCreateTime(DateUtils.getDate());
                    acc.setOrderNum(json.optString("cporderid"));
                    acc.setRmb(Double.valueOf(json.optString("money")));
                    systemService.save(acc);

                    /**
                     * 根据用户的id对用户的购物车进行购买
                     */
                    /** 通过用户userid找到用户的购物车里面的所有数据*/
                    List<ShoppingcartEntity> shoplist = systemService.findByProperty(ShoppingcartEntity.class, "dbappclientid", client.getId());
                    if (shoplist.size() > 0) {
                        for (ShoppingcartEntity shopping : shoplist) {
                            /** 用户的购物车不为空，开始给用户进行购买操作*/
                            String buycode = dbGoodsSingleService.doOnliBuyGoods(shopping.getDbappclientid(), shopping.getDbgoodsid(), shopping.getNumber());//购买操作，接受买回来的号码
                            //如果出现buycode为""的情况，说明购买出现了失败，但对用的抢币是没有构成影响的，只是没有购买成功而已，app直接显示就可以了
                            if (StringUtil.isNotEmpty(buycode)) {
                                //删除购物车里面的内容
                                systemService.delete(shopping);
                                //极光推送购买成功
                                JpushUtils.push2alias("恭喜您，购买宝贝成功，敬请期待开奖 !",client.getId());
                            }else{
                                /**
                                 * 出现购买失败的情况
                                 * 将用户支付的钱，返还到用户的抢币信息中
                                 */
                                dbGoodsSingleService.returnByRMB(client.getId(),shopping.getNumber());
                                //极光推送购买成功
                                JpushUtils.push2alias("购买失败，已退回抢币 !",client.getId());
                            }
                        }
                        return "SUCCESS";
                    } else {
                        return "FALSE";
                    }
                }else{
                    return "FALSE";
                }
            } else {
                logger.info("交易流水号"+json.optString("transid") + "   用户"+json.optString("appuserid")+"付款不成功");
                return "FALSE";
            }
        }
    }
}
