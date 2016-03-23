package com.lydb.service.db_goods_payment;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbGoodsPaymentServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbGoodsPaymentEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbGoodsPaymentEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbGoodsPaymentEntity t);
}
