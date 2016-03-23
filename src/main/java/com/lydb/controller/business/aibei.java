package com.lydb.controller.business;

import com.lydb.entity.account_log.AccountLogEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_payment.DbZgoodsPaymentEntity;
import com.xingluo.aibei.IAppPaySDKConfig;
import com.xingluo.aibei.Order;
import com.xingluo.aibei.sign.SignHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/2/24.
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/payBusiness")
public class aibei extends BaseController {
    private static final Logger logger = Logger.getLogger(aibei.class);
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @RequestMapping(params = "deposit")
    @ResponseBody
    public AjaxJson deposit(String id) throws Exception {
        AjaxJson j = new AjaxJson();
        String transid="";
        //订单生成
        DbGoodsEntity en = systemService.get(DbGoodsEntity.class, id);
        if(en!=null) {
            DbGoodsPaymentEntity pay = systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", id);
            storeController store = new storeController();
            pay.setStarttime(store.Time());

            pay.setOrderNum(en.getSerialNum().toString() + DateUtils.yyyymmddhhmmss.format(DateUtils.getDate()) + UUIDGenerator.generate().substring(10, 15));
            systemService.saveOrUpdate(pay);
            DbWebBusinessEntity business = systemService.get(DbWebBusinessEntity.class, en.getDbWebBusinessid());
            transid=Order.CheckSign(IAppPaySDKConfig.APP_ID,IAppPaySDKConfig.WARES_ID_1,"",pay.getOrderNum(),(float)(pay.getDeposit()+pay.getServicefee()),business.getMobile(),"恭喜您支付成功","http://120.25.145.15/xingluo/payBusiness.do?async");

        }else{
            DbZgoodsPaymentEntity payment=systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class,"dbZeroGoodsid" , id);

            DbZeroGoodsEntity zero = systemService.getEntity(DbZeroGoodsEntity.class, id);
            storeController store=new storeController();

            payment.setStarttime(store.Time());
            payment.setOrderNum(zero.getZserialNum().toString()+DateUtils.yyyymmddhhmmss.format(DateUtils.getDate())+UUIDGenerator.generate().substring(10, 15));
            systemService.saveOrUpdate(payment);
            DbWebBusinessEntity business = systemService.get(DbWebBusinessEntity.class, zero.getDbWebBusinessid());
            transid=Order.CheckSign(IAppPaySDKConfig.APP_ID,IAppPaySDKConfig.WARES_ID_1,"",payment.getOrderNum(),(float)(payment.getDeposit()+payment.getServicefee()),business.getMobile(),"恭喜您支付成功","http://120.25.145.15/xingluo/payBusiness.do?async");

        }
        /*appid：String类型 ，必填，商户后台获取为应用编号.
          waresid：int类型 ，必填，商户后台获取为商品编号.
          waresname: String 类型，可选，cp 自己定义。
        cporderid: String 类型，必填，cp 自己定义的订单号。
        price:float类型，可选（当商户后台配置的是：消费型_应用传入价格的计费方式时必填），
        appuserid: String 类型，必填，cp 自己定义。
        cpprivateinfo:String 类型，可选，用户自己定义，支付完成后发送支付结果通知时会透传给商户。
        notifyurl:String 类型，可选，商户服务端接收支付结果通知的地址。*/
        //String transid=Order.CheckSign(IAppPaySDKConfig.APP_ID,IAppPaySDKConfig.WARES_ID_1,"",pay.getOrderNum(),(float)(pay.getDeposit()+pay.getServicefee()),business.getMobile(),"恭喜您支付成功","http://127.0.0.1/lydb/pay.do?async");
        //String transid=Order.CheckSign(IAppPaySDKConfig.APP_ID,IAppPaySDKConfig.WARES_ID_1,"",pay.getOrderNum(),money,business.getMobile(),"恭喜您支付成功","http://120.25.145.15/xingluo/payBusiness.do?async");
         j.setObj(Order.H5orPCpay(transid));
        return j;
    }

    /**
     * 异步通知付款状态的Controller
     * @param request
     * @return
     * @author 何志颖

     * @throws ServletException

     */
    @RequestMapping(params="async")
    @ResponseBody
    public String async(HttpServletRequest request) {
           // request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();

			String transdata = request.getParameter("transdata");
			String sign = (String) request.getParameter("sign");
			String signtype = request.getParameter("signtype");
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

                        //未完成
                        DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"orderNum" , JSONObject.fromObject(transdata).get("cporderid"));
                        pay.setPaymentStatus(1);
                        systemService.saveOrUpdate(pay);
                        DbGoodsEntity goods=systemService.findUniqueByProperty( DbGoodsEntity.class,"id" ,pay.getDbGoodsid());
                        goods.setStatus("4");
                        systemService.saveOrUpdate(goods);
                        DbWebBusinessEntity business=systemService.findUniqueByProperty(DbWebBusinessEntity.class,"id",goods.getDbWebBusinessid());
                        AccountLogEntity acc =new AccountLogEntity();
                        acc.setAccountType(1);
                        acc.setBusinessMobile(business.getMobile());
                        acc.setDbWebBusinessid(business.getId());
                        //未完成
                        acc.setOrderNum(JSONObject.fromObject(transdata).get("cporderid").toString());
                        acc.setContent(goods.getSerialNum().toString()+goods.getGoodsName()+goods.getGoodsRmb().toString()+goods.getGoodsNum().toString());
                        //未完成
                        acc.setCreateTime(new java.util.Date(System.currentTimeMillis()));
                        acc.setRmb(pay.getDeposit()+pay.getServicefee());
                        systemService.save(acc);
                        return "SUCCESS";
        			} else {
                        /*DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"orderNum" ,tradeNo);
                        logger.info(store.Time().toString()+"商品编号为"+pay.getDbGoodsid().toString()+"的产品付款不成功");*/
                        return "FALSE";

        			}
		}
    }

    /**
     * 异步通知付款状态的Controller
     * @param request
     * @return
     * @author 何志颖

     * @throws ServletException

     */
    @RequestMapping(params="asynczero")
    @ResponseBody
    public String asynczero(HttpServletRequest request) {
        // request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();

        String transdata = request.getParameter("transdata");
        String sign = (String) request.getParameter("sign");
        String signtype = request.getParameter("signtype");
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

                DbZgoodsPaymentEntity pay=systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class,"orderNum" , JSONObject.fromObject(transdata).get("cporderid"));
                DbZeroGoodsEntity goods=systemService.findUniqueByProperty( DbZeroGoodsEntity.class,"id" ,pay.getDbZeroGoodsid());
                DbWebBusinessEntity business=systemService.findUniqueByProperty(DbWebBusinessEntity.class,"id" , goods.getDbWebBusinessid());
                storeController store=new storeController();

                pay.setPaymentStatus(1);
                systemService.saveOrUpdate(pay);

                goods.setStatus("4");
                systemService.saveOrUpdate(goods);
                AccountLogEntity acc =new AccountLogEntity();
                acc.setAccountType(1);
                acc.setBusinessMobile(business.getMobile());
                acc.setDbWebBusinessid(business.getId());
                acc.setOrderNum( JSONObject.fromObject(transdata).get("cporderid").toString());
                acc.setContent( goods.getZserialNum().toString()+goods.getZgoodsName()+goods.getZgoodsRmb().toString()+goods.getZgoodsNum().toString());

                acc.setCreateTime(new java.util.Date(System.currentTimeMillis()));
                acc.setRmb(pay.getDeposit()+pay.getServicefee());
                systemService.save(acc);


                return "SUCCESS";
            } else {
                        /*DbGoodsPaymentEntity pay=systemService.findUniqueByProperty(DbGoodsPaymentEntity.class,"orderNum" ,tradeNo);
                        logger.info(store.Time().toString()+"商品编号为"+pay.getDbGoodsid().toString()+"的产品付款不成功");*/
                return "FALSE";

            }
        }
    }
}
