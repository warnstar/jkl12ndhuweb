package com.lydb.service.db_app_client_rmb;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbAppClientRmbServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbAppClientRmbEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbAppClientRmbEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbAppClientRmbEntity t);
}
