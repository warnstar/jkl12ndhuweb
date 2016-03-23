package com.lydb.controller.root;

import com.lydb.entity.db_app_client.DbAppClientEntity;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import com.lydb.entity.db_ship_address.DbShipAddressEntity;
import com.lydb.entity.message_log.MessageLogEntity;
import com.lydb.service.db_app_client.DbAppClientServiceI;
import com.xingluo.HttpsUtil;
import com.xingluo.qiniudemo.qiniuController;
import com.xingluo.util.IpLocationTool;
import com.xingluo.util.JpushUtils;
import com.xingluo.util.Md5Util;
import com.xingluo.util.easemob.EasemobAPIUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 用户主表
 * @date 2015-11-25 10:17:24
 */
@Scope("prototype")
@Controller
@RequestMapping("/AppClientManagement")
public class AppClientManagement extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(AppClientManagement.class);

    @Autowired
    private DbAppClientServiceI dbAppClientService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static StringBuffer sql7 = new StringBuffer();

    static {
        //晒单记录
        sql7.append("SELECT sh.id,gs.goods_current_num,gs.all_join_num,gs.lucky_id,gs.open_time,g.goods_name,sh.share_title,sh.share_content,sh.share_time,GROUP_CONCAT(i.img_url) AS allImgUrl ");
        sql7.append("FROM (((db_share_goods AS sh LEFT JOIN db_img_url AS i ON sh.id=i.db_share_goodsid)LEFT JOIN db_goods_single AS gs ON sh.db_goods_singleid=gs.id)LEFT JOIN db_goods AS g ON gs.db_goodsid=g.id) LEFT JOIN db_app_client as ac ON sh.db_app_clientid=ac.id ");
        sql7.append("WHERE sh.db_app_clientid=? ");
        sql7.append("GROUP BY sh.id ");
        sql7.append("ORDER BY sh.share_time DESC ");
    }

    /**
     * 创建用户 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "CreateClient")
    public ModelAndView CreateClient(HttpServletRequest request) {
        return new ModelAndView("com/lydb/root/CreateClient");
    }

    /**
     * 用户列表 页面跳转
     */
    @RequestMapping(params = "AllClientList")
    public ModelAndView AllClientList(HttpServletRequest request) {
        return new ModelAndView("com/lydb/root/AllClientList");
    }


    /**
     * 用户详情页面跳转
     */
    @RequestMapping(params = "ClientDetail")
    public ModelAndView ClientDetail(String id) {
        //用户信息
        DbAppClientEntity appclient = systemService.get(DbAppClientEntity.class, id);
        //用户动态
        DbAppClientRmbEntity rmb = systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", id);
        //用户收货地址
        DbShipAddressEntity address = systemService.findUniqueByProperty(DbShipAddressEntity.class, "dbAppClientid", id);

        //用户的晒单详情
        List<Map<String, Object>> list = systemService.findForJdbcParam(sql7.toString(), 1, 100, id);

        try {
            for (Map<String, Object> map : list) {
                map.put("open_time", DateUtils.datetimeFormat.format(DateUtils.getDate(((Timestamp) map.get("open_time")).getTime())));
                map.put("share_time", DateUtils.datetimeFormat.format(DateUtils.getDate(((Timestamp) map.get("share_time")).getTime())));
                String[] img_urls = ((String) map.get("allImgUrl")).split(",");
                map.put("allImgUrl", img_urls);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("com/lydb/root/ClientDetail")
                    .addObject("appclient", appclient)
                    .addObject("rmb", rmb)
                    .addObject("address", address);

        }

        return new ModelAndView("com/lydb/root/ClientDetail")
                .addObject("appclient", appclient)
                .addObject("rmb", rmb)
                .addObject("address", address)
                .addObject("list", list);
    }


    /**
     * 赠送抢币
     */
    @RequestMapping(params = "gogiveClientRMB")
    public ModelAndView gogiveClientRMB(String id) {
        return new ModelAndView("com/lydb/root/giveClientRMB").addObject("id", id);
    }

    @RequestMapping(params = "giveClientRMB")
    @ResponseBody
    public AjaxJson giveClientRMB(String id, int rmb) {
        AjaxJson j = new AjaxJson();
        //找到用户的抢币信息，给用户增加抢币
        if (rmb > 0 && rmb < 1000) {
            DbAppClientRmbEntity clientrmb = systemService.findUniqueByProperty(DbAppClientRmbEntity.class, "idAppClientid", id);
            clientrmb.setRmb(clientrmb.getRmb() + rmb);
            systemService.saveOrUpdate(clientrmb);
        } else {
            j.setMsg("赠送的抢币有误,赠送失败！");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 个人推送消息
     */
    @RequestMapping(params = "appClientJpush")
    public ModelAndView appClientJpush(String id) {
        return new ModelAndView("com/lydb/root/appClientJpush").addObject("id", id);
    }

    @RequestMapping(params = "appClientJpush1")
    @ResponseBody
    public AjaxJson appClientJpush1(String id, String messageTitle, String messageBrief, String messageContent) {
        AjaxJson j = new AjaxJson();
        message = "推送消息成功";
        //推送代码在这里写
        String content = messageTitle + ":" + messageBrief;
        JpushUtils.push2alias(content, id);
        try {//添加推送记录log
            MessageLogEntity messagelog = new MessageLogEntity();
            messagelog.setMessageTitle(messageTitle);
            messagelog.setCreateTime(DateUtils.getDate());
            messagelog.setMessageBrief(messageBrief);
            messagelog.setMessageContent(messageContent);
            systemService.save(messagelog);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "推送消息失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 跳转到添加app广告图片的页面
     * 并带上已经存好的图片信息
     */
    @RequestMapping(params = "goAdPicture")
    public ModelAndView goAdPicture() {
        String SQL = "select typename,typecode from t_s_type AS ts LEFT JOIN t_s_typegroup as tg ON ts.typegroupid = tg.id where tg.typegroupcode = 'adPic'";

        List<Map<String, Object>> listmap = systemService.findForJdbc(SQL, null);

        return new ModelAndView("com/lydb/root/AdPicture")
                .addObject("picture1", listmap.get(0).get("typecode").toString())
                .addObject("picture2", listmap.get(1).get("typecode").toString())
                .addObject("picture3", listmap.get(2).get("typecode").toString());
    }

    @RequestMapping(params = "doAdPicture", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doAdPicture(HttpServletRequest request,
                                HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        qiniuController qiniu = ApplicationContextUtil.getContext().getBean(qiniuController.class);
        try {
            j = qiniu.updateQiniu(request, response);
            String name = j.getAttributes().get("picture").toString();
            String url = ((List<String>) j.getObj()).get(0);
            //更新到数据库中
            String sql = "update t_s_type set typecode = ? where typename = ?";

            systemService.executeSql(sql, url, name);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setMsg("上传失败！");
            j.setSuccess(false);
            return j;
        }
        return j;
    }


    /**
     * 删除用户的操作，调用存储过程，将用户的所有信息删除
     */
    @RequestMapping(params = "deleteClient")
    @ResponseBody
    public AjaxJson deleteClient(String userid) {
        AjaxJson j = new AjaxJson();

        try {

            String Mobile=systemService.get(DbAppClientEntity.class,userid).getMobile();
            int result = systemService.executeSql("CALL del_appclientInfo(?)", userid);

            if (result > 0) {
                /**
                 * 进行环信的删除
                 */

                EasemobAPIUtils.deleteIMUserByuserName(Mobile);
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
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(DbAppClientEntity dbAppClient, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(DbAppClientEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbAppClient, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.dbAppClientService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 添加用户主表
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(DbAppClientEntity dbAppClient, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        DbAppClientEntity dbclient = systemService.findUniqueByProperty(DbAppClientEntity.class, "mobile", dbAppClient.getMobile());
        if (dbclient == null) {
            try {
                String ip = IpUtil.getRandomIp();
                String address = IpLocationTool.getCity(ip);
                systemService.executeSql("CALL create_Client2(?,?,?,?,?,?)", dbAppClient.getMobile(), Md5Util.EncoderByMd5(dbAppClient.getPassword()), ip, address, dbAppClient.getClientName(), dbAppClient.getClientQq());
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
                //环信注册
                JSONObject obj = new JSONObject();
                obj.put("username", dbAppClient.getMobile());
                obj.put("password", Md5Util.EncoderByMd5(dbAppClient.getPassword()));
                String tok = null;
                HttpsUtil.httpsRequest("https://a1.easemob.com/12530/yiyuanmeng/users", "POST", obj.toString(), tok);
                j.setMsg("用户添加成功！");

            } catch (Exception e) {
                e.printStackTrace();
                message = "用户添加失败";
                throw new BusinessException(e.getMessage());
            } finally {
                return j;
            }
        } else {
            j.setMsg("该用户已经存在，添加失败！");
            j.setSuccess(false);
            return j;
        }
    }

    /**
     * 更新用户主
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(DbAppClientEntity dbAppClient, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "用户主表更新成功";
        DbAppClientEntity t = dbAppClientService.get(DbAppClientEntity.class, dbAppClient.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(dbAppClient, t);
            dbAppClientService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "用户主表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 用户主表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(DbAppClientEntity dbAppClient, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(dbAppClient.getId())) {
            dbAppClient = dbAppClientService.getEntity(DbAppClientEntity.class, dbAppClient.getId());
            req.setAttribute("dbAppClientPage", dbAppClient);
        }
        return new ModelAndView("com/lydb/db_app_client/dbAppClient-add");
    }

    /**
     * 用户主表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(DbAppClientEntity dbAppClient, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(dbAppClient.getId())) {
            dbAppClient = dbAppClientService.getEntity(DbAppClientEntity.class, dbAppClient.getId());
            req.setAttribute("dbAppClientPage", dbAppClient);
        }
        return new ModelAndView("com/lydb/db_app_client/dbAppClient-update");
    }

}
