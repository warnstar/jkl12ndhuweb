package com.lydb.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lydb.entity.dbversion.DbversionEntity;
import com.lydb.entity.rule_table.RuleTableEntity;
import com.xingluo.util.RestJson;



/**   
 *购物车接口用来操作用户购买
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/Rule")
public class RuleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleController.class);
	

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
	 * 
	 */
	@RequestMapping(value = "/getSummary")
	@ResponseBody
	public RestJson getSummary(){
		RestJson rest = new RestJson();
			RuleTableEntity rule=systemService.findUniqueByProperty(RuleTableEntity.class, "id", 5);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("topic", rule.getTitle());
			map.put("time",rule.getCreateTime());
			map.put("summary",rule.getBrief());
			rest.setInfo(map);
			rest.setCode(0);
	
	return rest;
	}
	/**
	 * 公告接口
	 */
	@RequestMapping(value = "/getRule")
	@ResponseBody
	public RestJson getRule(int id ){
		RestJson rest = new RestJson();
		
		try {
			RuleTableEntity rule=systemService.findUniqueByProperty(RuleTableEntity.class, "id", id);
		
			rest.setInfo(rule.getRuleContent());
			rest.setCode(0);
		} catch (Exception e) {
			rest.setCode(8);
			rest.setError("对不起出错了");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rest;
		}	
	return rest;
	}
	/**
	 * 版本接口
	 */
	@RequestMapping(value = "/getVersion")
	@ResponseBody
	public RestJson getVersion(){
		RestJson rest = new RestJson();
	
		try {
			List<DbversionEntity> list=systemService.getList(DbversionEntity.class);
			rest.setInfo(list.get(list.size()-1));
			rest.setCode(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rest.setCode(8);
			rest.setError("对不起出错了");
			return rest;
		}
		return rest;
	}

	
}
