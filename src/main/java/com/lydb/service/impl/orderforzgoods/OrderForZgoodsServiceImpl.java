package com.lydb.service.impl.orderforzgoods;
import com.lydb.service.orderforzgoods.OrderForZgoodsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.orderforzgoods.OrderForZgoodsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("orderForZgoodsService")
@Transactional
public class OrderForZgoodsServiceImpl extends CommonServiceImpl implements OrderForZgoodsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((OrderForZgoodsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((OrderForZgoodsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((OrderForZgoodsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(OrderForZgoodsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(OrderForZgoodsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(OrderForZgoodsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,OrderForZgoodsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{db_zgoods_singleid}",String.valueOf(t.getDbZgoodsSingleid()));
 		sql  = sql.replace("#{db_app_clientid}",String.valueOf(t.getDbAppClientid()));
 		sql  = sql.replace("#{rmb_num}",String.valueOf(t.getRmbNum()));
 		sql  = sql.replace("#{ip_address}",String.valueOf(t.getIpAddress()));
 		sql  = sql.replace("#{address_info}",String.valueOf(t.getAddressInfo()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}