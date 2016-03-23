package com.lydb.service.impl.db_join_client2;
import com.lydb.service.db_join_client2.DbJoinClient2ServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.db_join_client2.DbJoinClient2Entity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbJoinClient2Service")
@Transactional
public class DbJoinClient2ServiceImpl extends CommonServiceImpl implements DbJoinClient2ServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbJoinClient2Entity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbJoinClient2Entity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbJoinClient2Entity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbJoinClient2Entity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbJoinClient2Entity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbJoinClient2Entity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbJoinClient2Entity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{db_app_clientid}",String.valueOf(t.getDbAppClientid()));
 		sql  = sql.replace("#{join_time}",String.valueOf(t.getJoinTime()));
 		sql  = sql.replace("#{db_code}",String.valueOf(t.getDbCode()));
 		sql  = sql.replace("#{db_zgoods_singleid}",String.valueOf(t.getDbZgoodsSingleid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}