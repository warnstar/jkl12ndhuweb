package com.lydb.service.impl.db_zcoupon_business;
import com.lydb.service.db_zcoupon_business.DbZcouponBusinessServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.db_zcoupon_business.DbZcouponBusinessEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbZcouponBusinessService")
@Transactional
public class DbZcouponBusinessServiceImpl extends CommonServiceImpl implements DbZcouponBusinessServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbZcouponBusinessEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbZcouponBusinessEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbZcouponBusinessEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbZcouponBusinessEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbZcouponBusinessEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbZcouponBusinessEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbZcouponBusinessEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{coupon_value}",String.valueOf(t.getCouponValue()));
 		sql  = sql.replace("#{coupon_url}",String.valueOf(t.getCouponUrl()));
 		sql  = sql.replace("#{business_name}",String.valueOf(t.getBusinessName()));
 		sql  = sql.replace("#{db_zero_goodsid}",String.valueOf(t.getDbZeroGoodsid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}