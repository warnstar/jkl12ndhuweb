package com.lydb.service.impl.db_app_client_rmb;
import com.lydb.service.db_app_client_rmb.DbAppClientRmbServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.db_app_client_rmb.DbAppClientRmbEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbAppClientRmbService")
@Transactional
public class DbAppClientRmbServiceImpl extends CommonServiceImpl implements DbAppClientRmbServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbAppClientRmbEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbAppClientRmbEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbAppClientRmbEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbAppClientRmbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbAppClientRmbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbAppClientRmbEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbAppClientRmbEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{id_app_clientid}",String.valueOf(t.getIdAppClientid()));
 		sql  = sql.replace("#{rmb}",String.valueOf(t.getRmb()));
 		sql  = sql.replace("#{integrate_all}",String.valueOf(t.getIntegrateAll()));
 		sql  = sql.replace("#{rmb_invite}",String.valueOf(t.getRmbInvite()));
 		sql  = sql.replace("#{rmb_today}",String.valueOf(t.getRmbToday()));
 		sql  = sql.replace("#{integrate_today}",String.valueOf(t.getIntegrateToday()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}