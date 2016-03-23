package com.lydb.service.db_zgoods_single;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbZgoodsSingleServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbZgoodsSingleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbZgoodsSingleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbZgoodsSingleEntity t);
 	//获得一个code
 	public String  getcode(String clientid,String singleid )throws Exception ;
 	/**
	 * 后台进行修改幸运号,能进行事务操作
	 */
	public void dochangeZeroLuckId(String singlegoodsId, String luckyId) throws Exception;
}
