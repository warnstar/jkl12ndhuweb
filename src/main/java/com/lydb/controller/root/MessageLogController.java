package com.lydb.controller.root;
import com.lydb.entity.message_log.MessageLogEntity;
import com.lydb.service.message_log.MessageLogServiceI;
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



/**   
 * @Title: Controller
 * @Description: 推送消息历史记录
 * @author onlineGenerator
 * @date 2015-12-01 21:01:18
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/messageLogController")
public class MessageLogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageLogController.class);

	@Autowired
	private MessageLogServiceI messageLogService;
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
	 * 推送消息历史记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "messageLog")
	public ModelAndView messageLog(HttpServletRequest request) {
		return new ModelAndView("com/lydb/root/messageLogList");
	}
	/**
	 * 环信客服页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "huanXin")
	public ModelAndView huanxin(HttpServletRequest request) {
		return new ModelAndView("com/lydb/root/huanxin");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MessageLogEntity messageLog,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MessageLogEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, messageLog, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.messageLogService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除推送消息历史记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MessageLogEntity messageLog, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		messageLog = systemService.getEntity(MessageLogEntity.class, messageLog.getId());
		message = "推送消息历史记录删除成功";
		try{
			messageLogService.delete(messageLog);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "推送消息历史记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除推送消息历史记录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "推送消息历史记录删除成功";
		try{
			for(String id:ids.split(",")){
				MessageLogEntity messageLog = systemService.getEntity(MessageLogEntity.class, 
				Integer.parseInt(id)
				);
				messageLogService.delete(messageLog);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "推送消息历史记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加推送消息历史记录
	 * 
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MessageLogEntity messageLog, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "推送消息成功";

		 

		try{//添加推送记录log
			HttpSession session = ContextHolderUtils.getSession();
			Client client=ClientManager.getInstance().getClient(session.getId());
			messageLog.setPullAll(1);
			messageLog.setCreateName(client.getUser().getRealName());
			messageLogService.save(messageLog);
			//推送代码在这里写
			String content=messageLog.getMessageTitle()+":"+messageLog.getMessageContent();

			JpushUtils.push2all(content);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}catch(Exception e){
			e.printStackTrace();
			message = "推送消息失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 添加推送消息给个人
	 * 
	 *
	 * @return
	 */
	@RequestMapping(params = "doAddClient")
	@ResponseBody
	public AjaxJson doAddClient(MessageLogEntity messageLog,String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "推送消息成功";
		//推送代码在这里写
		 
		String content=messageLog.getMessageTitle()+":"+messageLog.getMessageContent()+"  "+messageLog.getCreateTime();
		JpushUtils.push2alias(content,id);
		try{//添加推送记录log
			HttpSession session = ContextHolderUtils.getSession();
			Client client=ClientManager.getInstance().getClient(session.getId());
			messageLog.setPullAll(1);
			messageLog.setCreateName(client.getUser().getRealName());
			messageLogService.save(messageLog);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "推送消息失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新推送消息历史记录
	 * 
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MessageLogEntity messageLog, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "推送消息历史记录更新成功";
		MessageLogEntity t = messageLogService.get(MessageLogEntity.class, messageLog.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(messageLog, t);
			messageLogService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "推送消息历史记录更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 推送消息历史记录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MessageLogEntity messageLog, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(messageLog.getId())) {
			messageLog = messageLogService.getEntity(MessageLogEntity.class, messageLog.getId());
			req.setAttribute("messageLogPage", messageLog);
		}
		return new ModelAndView("com/lydb/root/messageLog-add");
	}
	/**
	 * 推送消息历史记录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MessageLogEntity messageLog, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(messageLog.getId())) {
			messageLog = messageLogService.getEntity(MessageLogEntity.class, messageLog.getId());
			req.setAttribute("messageLogPage", messageLog);
		}
		return new ModelAndView("com/lydb/message_log/messageLog-update");
	}
	

}
