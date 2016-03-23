package com.lydb.controller.root;

import com.lydb.entity.rule_table.RuleTableEntity;
import com.lydb.service.rule_table.RuleTableServiceI;
import com.xingluo.util.JpushUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 规则编辑表
 * @date 2015-12-01 21:04:16
 */
@Scope("prototype")
@Controller
@RequestMapping("/ruleTableController")
public class RuleTableController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(RuleTableController.class);

    @Autowired
    private RuleTableServiceI ruleTableService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 规则编辑表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "ruleTable")
    public ModelAndView ruleTable(HttpServletRequest request) {
        return new ModelAndView("com/lydb/root/ruleTableList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(RuleTableEntity ruleTable, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RuleTableEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ruleTable, request.getParameterMap());
        try {
            cq.notEq("id",5);
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.ruleTableService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除规则编辑表
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(RuleTableEntity ruleTable, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        ruleTable = systemService.getEntity(RuleTableEntity.class, ruleTable.getId());
        message = "规则编辑表删除成功";
        try {
            ruleTableService.delete(ruleTable);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "规则编辑表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加规则编辑表
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(RuleTableEntity ruleTable, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "规则编辑表添加成功";
        try {

            ruleTableService.save(ruleTable);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "规则编辑表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新规则编辑表
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(RuleTableEntity ruleTable, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        RuleTableEntity t = ruleTableService.get(RuleTableEntity.class, ruleTable.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(ruleTable, t);
            //审核的用户
            HttpSession session = ContextHolderUtils.getSession();
            Client client = ClientManager.getInstance().getClient(session.getId());
            t.setChangeName(client.getUser().getRealName());
            t.setCreateTime(new Date(System.currentTimeMillis()));
            ruleTableService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            if(ruleTable.getId()==5){
                JpushUtils.push2all(ruleTable.getBrief()+"系统公告");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 规则编辑表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(RuleTableEntity ruleTable, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(ruleTable.getId())) {
            ruleTable = ruleTableService.getEntity(RuleTableEntity.class, ruleTable.getId());
            req.setAttribute("ruleTablePage", ruleTable);
        }
        return new ModelAndView("com/lydb/root/ruleTable-update");
    }

    /**
     * 添加一个公告页面的跳转
     */
    @RequestMapping(params = "gonggaoWeb")
    public ModelAndView gonggaoWeb(HttpServletRequest req,String id) {
        RuleTableEntity ruleTable = ruleTableService.getEntity(RuleTableEntity.class, Integer.valueOf(id));
        req.setAttribute("ruleTablePage", ruleTable);
        return new ModelAndView("com/lydb/root/gonggao");
    }
    @RequestMapping(params = "gonggao")
    public ModelAndView gonggao(HttpServletRequest req ,String id) {
        RuleTableEntity ruleTable = ruleTableService.getEntity(RuleTableEntity.class, Integer.valueOf(id));
        req.setAttribute("ruleTablePage", ruleTable);
        return new ModelAndView("com/lydb/root/announcement");
    }
}
