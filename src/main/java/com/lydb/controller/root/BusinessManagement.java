package com.lydb.controller.root;

import com.lydb.entity.db_coupon_business.DbCouponBusinessEntity;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import com.lydb.entity.db_img_url.DbImgUrlEntity;
import com.lydb.entity.db_web_business.DbWebBusinessEntity;
import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;
import com.lydb.entity.db_zgoods_payment.DbZgoodsPaymentEntity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import com.lydb.service.db_goods.DbGoodsServiceI;
import com.lydb.service.db_goods_payment.DbGoodsPaymentServiceI;
import com.lydb.service.db_web_business.DbWebBusinessServiceI;
import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;

import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

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


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 商家管理的控制器
 * @date 2015-11-25 10:27:30
 */
@Scope("prototype")
@Controller
@RequestMapping("/BusManagement")
public class BusinessManagement extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BusinessManagement.class);

    @Autowired
    private DbWebBusinessServiceI dbWebBusinessService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private DbGoodsServiceI dbGoodsService;
    @Autowired
    private DbZeroGoodsServiceI dbZeroGoodsService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    //商家申请审核

    /**
     * 商家注册申请审核的页面跳转
     * 包含： 未审核，已通过， 已拒绝
     * fy
     */
    @RequestMapping(params = "BusinessCheck")
    public ModelAndView BusinessCheck(HttpServletRequest request, HttpServletResponse response) {
//		JSONObject jObject = this.dbWebBusinessService.getDatagrid3(business,dataGrid);
//		rootUtil.responseDatagrid(response, jObject);
        return new ModelAndView("com/lydb/root/dbWebBusinessList");
    }

    /**
     * 根据商家的id，返回商家的详情
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "BusinessDetail")
    public ModelAndView BusinessDetail(String id, HttpServletRequest request, HttpServletResponse response) {
        //根据商家id查询到商家
        DbWebBusinessEntity business = dbWebBusinessService.get(DbWebBusinessEntity.class, id);
        //根据商家的状态返回不同的商家详情页面
        if (business.getStatus().equals("0")) {
            return new ModelAndView("com/lydb/root/BusinessCheck").addObject("business", business);
        } else {
            return new ModelAndView("com/lydb/root/BusinessCheckPass").addObject("business", business);
        }
    }

    /**
     * 修改商家状态，从未审核到已通过或者拒绝
     */
    @RequestMapping(params = "ChangeBusinessStatus")
    @ResponseBody
    public AjaxJson ChangeBusinessStatus(String id, String status, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        DbWebBusinessEntity business = dbWebBusinessService.get(DbWebBusinessEntity.class, id);
        if (business.getStatus().equals("0")) {
            business.setStatus(status);
            dbWebBusinessService.saveOrUpdate(business);
        } else {
            j.setMsg("修改失败");
            j.setSuccess(false);
        }
        return j;
    }


    //已通过商家列表

    /**
     * 跳转到已通过的商家列表
     * fy
     */
    @RequestMapping(params = "PassBusiness")
    public ModelAndView PassBussiness(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/PassBusinessList");
    }

    /**
     * 商家列表中每个商家的查看商家商品列表、页面跳转
     * fy
     */
    @RequestMapping(params = "BusinessGoodsList")
    public ModelAndView BusinessGoodsList(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/BusinessGoodsList").addObject("id", id);
    }

    /*
     * 商家未上架商品
     * fy
     */
    @RequestMapping(params = "NotPutGoods")
    public ModelAndView NotPutGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/NotPutGoods").addObject("id", id);
    }

    /*
     * 商家未开奖商品
     * fy
     */
    @RequestMapping(params = "NotPrizeGoods")
    public ModelAndView NotPrizeGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/NotPrizeGoods").addObject("id", id);
    }

    /*
     * 商家已开奖商品列表
     */
    @RequestMapping(params = "IsPrizeGoods")
    public ModelAndView IsprizeGooods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/IsPrizeGoods").addObject("id", id);
    }

    /*
     * 需返还保证金的商品列表
     * fy
     */
    @RequestMapping(params = "ReturnRMBGoods")
    public ModelAndView ReturnRMBGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/ReturnRMBGoods").addObject("id", id);
    }


    /**
     * 商家列表中每个商家的查看商家商品列表、页面跳转
     * fy
     */
    @RequestMapping(params = "BusinessZeroGoodsList")
    public ModelAndView BusinessZeroGoodsList(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/BusinessZeroGoodsList").addObject("id", id);
    }

    /*
    /*
     * 未上架零元商品
     */
    @RequestMapping(params = "NotPutZeroGoods")
    public ModelAndView NotPutZeroGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/NotPutZeroGoods").addObject("id", id);
    }

    /*
     * 未开奖零元商品
     */
    @RequestMapping(params = "NotPrizeZeroGoods")
    public ModelAndView NotPrizeZeroGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/NotPrizeZeroGoods").addObject("id", id);
    }

    /*
     * 已开奖零元商品列表
     */
    @RequestMapping(params = "IsPrizeZeroGoods")
    public ModelAndView IsPrizeZeroGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/IsPrizeZeroGoods").addObject("id", id);
    }

    /*
     * 需返还保证金的零元商品列表
     */
    @RequestMapping(params = "ReturnRMBZeroGoods")
    public ModelAndView ReturnRMBZeroGoods(String id, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/lydb/root/ReturnRMBZeroGoods").addObject("id", id);
    }

    /**
     * 封装一个总的查询方法，只要带商家的id和需要查询商家的那种商品
     * 1-4  普通商品的查询，5-8 零元商品的查询
     * 主要是带入参数，选取不同的sql进行查询
     * fy
     */
    @RequestMapping(params = "BusinessGoodsByType")
    public ModelAndView BusinessGoodsByType(String GoodsID, String Type, HttpServletRequest request, HttpServletResponse response) {
        if (Type.equals("1") || Type.equals("3")) {
            DbGoodsEntity goods = this.systemService.get(DbGoodsEntity.class, GoodsID);
            DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            List<DbImgUrlEntity> imgurl=this.systemService.findByProperty(DbImgUrlEntity.class,"dbShareGoodsid",goods.getId());
            return new ModelAndView("com/lydb/root/NotCheckGoodsDetail")
                    .addObject("goods", goods)
                    .addObject("imgurl",imgurl.get(0).getImgUrl())
                    .addObject("coupon", coupon)
                    .addObject("business", business);
        } else if (Type.equals("2")) {
            DbGoodsEntity goods = this.systemService.get(DbGoodsEntity.class, GoodsID);
            DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbGoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", goods.getId());
            return new ModelAndView("com/lydb/root/witeGoodsDetail")
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment);
        } else if (Type.equals("4")) {
            DbGoodsEntity goods = this.systemService.get(DbGoodsEntity.class, GoodsID);
            DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbGoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", goods.getId());
            return new ModelAndView("com/lydb/root/NotPutGoodsDetail")
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment);
        } else if (Type.equals("5")) {
            //此时的goodid变成商品中每一个开奖的商品的db_singe_goodsid
            DbGoodsSingleEntity single = this.systemService.get(DbGoodsSingleEntity.class, GoodsID);
            DbGoodsEntity goods = this.systemService.get(DbGoodsEntity.class, single.getDbGoodsid());
            DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbGoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", goods.getId());
            return new ModelAndView("com/lydb/root/NotPrizeGoodsDetail")
                    .addObject("single", single)
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment);
        } else if (Type.equals("6")) {
            //此时的goodid变成商品中每一个开奖的商品的db_singe_goodsid
            DbGoodsSingleEntity single = this.systemService.get(DbGoodsSingleEntity.class, GoodsID);
            DbGoodsEntity goods = this.systemService.get(DbGoodsEntity.class, single.getDbGoodsid());
            DbCouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbCouponBusinessEntity.class, "dbGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbGoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbGoodsPaymentEntity.class, "dbGoodsid", goods.getId());
            StringBuilder sql_command = new StringBuilder();
            sql_command.append("SELECT s.share,s.db_app_clientid,s.goods_current_num,s.lucky_id,c.mobile ");
            sql_command.append("FROM db_goods_single AS s LEFT JOIN db_app_client AS c ON s.db_app_clientid=c.id ");
            sql_command.append("WHERE s.db_goodsid = ?  AND s.`status`>=2 ");
            sql_command.append("ORDER BY s.open_time ASC ");
            List<Map<String, Object>> dateMaps = this.systemService.findForJdbc(sql_command.toString(), single.getDbGoodsid());

            return new ModelAndView("com/lydb/root/IsPrizeGoodsDetail")
                    .addObject("single", single)
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment)
                    .addObject("dateMaps", dateMaps);
        }
        return new ModelAndView("com/lydb/root/error");
    }

    @RequestMapping(params = "BusinessZeroGoodsByType")
    public ModelAndView BusinessZeroGoodsByType(String zGoodsID, String Type, HttpServletRequest request, HttpServletResponse response) {
        if (Type.equals("1") || Type.equals("3")) {
            DbZeroGoodsEntity zgoods = this.systemService.get(DbZeroGoodsEntity.class, zGoodsID);
            DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", zgoods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, zgoods.getDbWebBusinessid());
            List<DbImgUrlEntity> imgurl=this.systemService.findByProperty(DbImgUrlEntity.class,"dbShareGoodsid",zgoods.getId());
            return new ModelAndView("com/lydb/root/NotCheckZeroGoodsDetail")
                    .addObject("goods", zgoods)
                    .addObject("imgurl",imgurl.get(0).getImgUrl())
                    .addObject("coupon", zcoupon)
                    .addObject("business", business);
        } else if (Type.equals("2")) {
            DbZeroGoodsEntity zgoods = this.systemService.get(DbZeroGoodsEntity.class, zGoodsID);
            DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", zgoods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, zgoods.getDbWebBusinessid());
            DbZgoodsPaymentEntity zpayment = this.systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class, "dbZeroGoodsid", zgoods.getId());
            return new ModelAndView("com/lydb/root/witeZeroGoodsDetail")
                    .addObject("goods", zgoods)
                    .addObject("coupon", zcoupon)
                    .addObject("business", business)
                    .addObject("payment", zpayment);
        } else if (Type.equals("4")) {
            DbZeroGoodsEntity zgoods = this.systemService.get(DbZeroGoodsEntity.class, zGoodsID);
            DbZcouponBusinessEntity zcoupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", zgoods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, zgoods.getDbWebBusinessid());
            DbZgoodsPaymentEntity zpayment = this.systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class, "dbZeroGoodsid", zgoods.getId());
            return new ModelAndView("com/lydb/root/NotPutZeroGoodsDetail")
                    .addObject("goods", zgoods)
                    .addObject("coupon", zcoupon)
                    .addObject("business", business)
                    .addObject("payment", zpayment);
        } else if (Type.equals("5")) {
            //此时的zGoodsID变成商品中每一个开奖的商品的db_singe_goodsid
            DbZgoodsSingleEntity single = this.systemService.get(DbZgoodsSingleEntity.class, zGoodsID);
            DbZeroGoodsEntity goods = this.systemService.get(DbZeroGoodsEntity.class, single.getDbZeroGoodsid());
            DbZcouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbZgoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class, "dbZeroGoodsid", goods.getId());
            return new ModelAndView("com/lydb/root/NotPrizeZeroGoodsDetail")
                    .addObject("single", single)
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment);
        } else if (Type.equals("6")) {
            //此时的zGoodsID变成商品中每一个开奖的商品的db_singe_goodsid
            DbZgoodsSingleEntity single = this.systemService.get(DbZgoodsSingleEntity.class, zGoodsID);
            DbZeroGoodsEntity goods = this.systemService.get(DbZeroGoodsEntity.class, single.getDbZeroGoodsid());
            DbZcouponBusinessEntity coupon = this.systemService.findUniqueByProperty(DbZcouponBusinessEntity.class, "dbZeroGoodsid", goods.getId());
            DbWebBusinessEntity business = this.systemService.get(DbWebBusinessEntity.class, goods.getDbWebBusinessid());
            DbZgoodsPaymentEntity payment = this.systemService.findUniqueByProperty(DbZgoodsPaymentEntity.class, "dbZeroGoodsid", goods.getId());
            StringBuilder sql_command = new StringBuilder();
            sql_command.append("SELECT s.share,s.db_app_clientid,s.goods_current_num,s.lucky_id,c.mobile ");
            sql_command.append("FROM db_zgoods_single AS s LEFT JOIN db_app_client AS c ON s.db_app_clientid=c.id ");
            sql_command.append("WHERE s.db_zero_goodsid = ?  AND s.`status`>=2 ");
            sql_command.append("ORDER BY s.open_time ASC ");
            List<Map<String, Object>> dateMaps = this.systemService.findForJdbc(sql_command.toString(), single.getDbZeroGoodsid());

            return new ModelAndView("com/lydb/root/IsPrizeZeroGoodsDetail")
                    .addObject("single", single)
                    .addObject("goods", goods)
                    .addObject("coupon", coupon)
                    .addObject("business", business)
                    .addObject("payment", payment)
                    .addObject("dateMaps", dateMaps);
        }
        return new ModelAndView("com/lydb/root/error");
    }


    @RequestMapping(params = "datagrid")
    public void datagrid(DbWebBusinessEntity dbWebBusiness, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(DbWebBusinessEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbWebBusiness, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.dbWebBusinessService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /*
     * 商品列表查询封装
     */
    @RequestMapping(params = "goodsdatagrid")
    public void goodsdatagrid(DbGoodsEntity dbgoods, String type, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String goods_name = request.getParameter("goods.name");
        String serial_num = request.getParameter("serial.num");
        String singlestatus = request.getParameter("singlestatus");
        if (type.equals("1")) {
            CriteriaQuery cq = new CriteriaQuery(DbGoodsEntity.class, dataGrid);
            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbgoods, request.getParameterMap());
            try {
                //自定义追加查询条件

            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            cq.add();
            this.dbGoodsService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } else if (type.equals("5")) {
            if (!StringUtil.isEmpty(goods_name)) {
                dbgoods.setGoodsName(goods_name);
            }
            if (!StringUtil.isEmpty(serial_num)) {
                dbgoods.setSerialNum(Integer.valueOf(serial_num));
            }
            if (!StringUtil.isEmpty(singlestatus)) {
                dbgoods.setStatus(singlestatus);
            }
            JSONObject jObject = this.dbGoodsService.getNotPrizeGoods(dbgoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        } else if (type.equals("6")) {
            if (!StringUtil.isEmpty(goods_name)) {
                dbgoods.setGoodsName(goods_name);
            }
            if (!StringUtil.isEmpty(serial_num)) {
                dbgoods.setSerialNum(Integer.valueOf(serial_num));
            }
            if (!StringUtil.isEmpty(singlestatus)) {
                dbgoods.setStatus(singlestatus);
            }
            JSONObject jObject = this.dbGoodsService.getIsPrizeGoods(dbgoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        } else if (type.equals("7")) {
            if (!StringUtil.isEmpty(goods_name)) {
                dbgoods.setGoodsName(goods_name);
            }
            if (!StringUtil.isEmpty(serial_num)) {
                dbgoods.setSerialNum(Integer.valueOf(serial_num));
            }
            JSONObject jObject = this.dbGoodsService.getReturnGoods(dbgoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        }/*else if(dbgoods.getStatus().equals("8")){
            JSONObject jObject = this.dbGoodsService.getReturnGoods(dbgoods, dataGrid);
			rootUtil.responseDatagrid(response, jObject);

		}*/

    }

    /*
     * 零元商品列表查询封装
     */
    @RequestMapping(params = "Zerogoodsdatagrid")
    public void Zerogoodsdatagrid(DbZeroGoodsEntity dbzerogoods, String type, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String zgoods_name = request.getParameter("zgoods.name");
        String zserial_num = request.getParameter("zserial.num");
        if (type.equals("1")) {
            CriteriaQuery cq = new CriteriaQuery(DbZeroGoodsEntity.class, dataGrid);
            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbzerogoods, request.getParameterMap());
            try {
                //自定义追加查询条件

            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            cq.add();
            this.dbZeroGoodsService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } else if (type.equals("5")) {
            if (!StringUtil.isEmpty(zgoods_name)) {
                dbzerogoods.setZgoodsName(zgoods_name);
            }
            if (!StringUtil.isEmpty(zserial_num)) {
                dbzerogoods.setZserialNum(Integer.valueOf(zserial_num));
            }
            JSONObject jObject = this.dbZeroGoodsService.getNotPrizeZeroGoods(dbzerogoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        } else if (type.equals("6")) {
            if (!StringUtil.isEmpty(zgoods_name)) {
                dbzerogoods.setZgoodsName(zgoods_name);
            }
            if (!StringUtil.isEmpty(zserial_num)) {
                dbzerogoods.setZserialNum(Integer.valueOf(zserial_num));
            }
            JSONObject jObject = this.dbZeroGoodsService.getIsPrizeZeroGoods(dbzerogoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        } else if (type.equals("7")) {
            if (!StringUtil.isEmpty(zgoods_name)) {
                dbzerogoods.setZgoodsName(zgoods_name);
            }
            if (!StringUtil.isEmpty(zserial_num)) {
                dbzerogoods.setZserialNum(Integer.valueOf(zserial_num));
            }
            JSONObject jObject = this.dbZeroGoodsService.getReturnZeroGoods(dbzerogoods, dataGrid);
            rootUtil.responseDatagrid(response, jObject);

        }
    }


    /**
     * 删除商家,根据商家主键，删除商家的所有信息，相当于全部清空
     * 该操作必须谨慎，操作不可还原，有事务操作不会删除不全
     */
    @RequestMapping(params = "deleteBusAllinfo")
    @ResponseBody
    public AjaxJson deleteBusAllinfo(String busid) {
        AjaxJson j = new AjaxJson();
        try {
            int result = systemService.executeSql("CALL del_businessInfo(?)", busid);
            if (result > 0) {
                j.setMsg("删除成功！");
            } else {
                j.setMsg("要删除的记录不存在");
                j.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("删除失败！");
            j.setSuccess(false);
            return j;
        }
        return j;
    }

    /**
     * 根据主键删除商家申请的商品
     */
    @RequestMapping(params = "gooddel")
    @ResponseBody
    public AjaxJson gooddel(DbGoodsEntity goods, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        goods = systemService.get(DbGoodsEntity.class, goods.getId());
        try {
            systemService.delete(goods);
            j.setMsg("商品删除成功！");
            systemService.addLog("商品删除成功", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品删除失败";
        }finally {
            return j;
        }

    }

    /**
     * 根据主键删除商家申请的零元商品
     */
    @RequestMapping(params = "zerogooddel")
    @ResponseBody
    public AjaxJson zerogooddel(DbZeroGoodsEntity zerogoods, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        zerogoods = systemService.get(DbZeroGoodsEntity.class, zerogoods.getId());
        try {
            systemService.delete(zerogoods);
            j.setMsg("零元商品删除成功！");
            systemService.addLog("零元商品删除成功", Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
        }finally {

            return j;
        }
    }


    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(DbWebBusinessEntity bus, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        bus = systemService.getEntity(DbWebBusinessEntity.class, bus.getId());
        try {
            systemService.delete(bus);
            message = "删除成功";
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
        }
        finally {
            j.setMsg(message);
            return j;
        }

    }

}
