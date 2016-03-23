package com.lydb.controller.root;
import com.lydb.entity.dbversion.DbversionEntity;
import com.lydb.service.dbversion.DbversionServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xingluo.alipay.util.DateUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 一圆梦版本检测
 * @author onlineGenerator
 * @date 2016-01-21 23:22:12
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/dbversionController")
public class DbversionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DbversionController.class);

	@Autowired
	private DbversionServiceI dbversionService;
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
	 * 一圆梦版本检测列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dbversion")
	public ModelAndView dbversion(HttpServletRequest request) {
		return new ModelAndView("com/lydb/root/dbversionList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(DbversionEntity dbversion,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DbversionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbversion, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.dbversionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除一圆梦版本检测
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DbversionEntity dbversion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		dbversion = systemService.getEntity(DbversionEntity.class, dbversion.getId());
		message = "一圆梦版本检测删除成功";
		try{
			dbversionService.delete(dbversion);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "一圆梦版本检测删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	


	/**
	 * 添加一圆梦版本检测
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(DbversionEntity dbversion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "一圆梦版本检测添加成功";
		try{
			dbversion.setCreateTime(DateUtils.getDate());
			dbversionService.save(dbversion);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "一圆梦版本检测添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新一圆梦版本检测
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(DbversionEntity dbversion, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "一圆梦版本检测更新成功";
		DbversionEntity t = dbversionService.get(DbversionEntity.class, dbversion.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(dbversion, t);
			dbversionService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "一圆梦版本检测更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 一圆梦版本检测新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(DbversionEntity dbversion, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbversion.getId())) {
			dbversion = dbversionService.getEntity(DbversionEntity.class, dbversion.getId());
			req.setAttribute("dbversionPage", dbversion);
		}
		return new ModelAndView("com/lydb/root/dbversion-add");
	}
	/**
	 * 一圆梦版本检测编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(DbversionEntity dbversion, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbversion.getId())) {
			dbversion = dbversionService.getEntity(DbversionEntity.class, dbversion.getId());
			req.setAttribute("dbversionPage", dbversion);
		}
		return new ModelAndView("com/lydb/root/dbversion-update");
	}
	

}
