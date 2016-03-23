package com.lydb.service.impl.dbappclientzcoupon;
import com.lydb.service.dbappclientzcoupon.DbAppClientZcouponServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.dbappclientzcoupon.DbAppClientZcouponEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbAppClientZcouponService")
@Transactional
public class DbAppClientZcouponServiceImpl extends CommonServiceImpl implements DbAppClientZcouponServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbAppClientZcouponEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbAppClientZcouponEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbAppClientZcouponEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbAppClientZcouponEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbAppClientZcouponEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbAppClientZcouponEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbAppClientZcouponEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{db_app_clientid}",String.valueOf(t.getDbAppClientid()));
 		sql  = sql.replace("#{db_zcoupon_businessid}",String.valueOf(t.getDbZcouponBusinessid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}