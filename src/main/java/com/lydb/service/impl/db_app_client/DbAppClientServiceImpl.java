package com.lydb.service.impl.db_app_client;
import com.lydb.service.db_app_client.DbAppClientServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.db_app_client.DbAppClientEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbAppClientService")
@Transactional
public class DbAppClientServiceImpl extends CommonServiceImpl implements DbAppClientServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbAppClientEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbAppClientEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbAppClientEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbAppClientEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbAppClientEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbAppClientEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbAppClientEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{mobile}",String.valueOf(t.getMobile()));
 		sql  = sql.replace("#{password}",String.valueOf(t.getPassword()));
 		sql  = sql.replace("#{client_qq}",String.valueOf(t.getClientQq()));
 		sql  = sql.replace("#{popularize_id}",String.valueOf(t.getPopularizeId()));
 		sql  = sql.replace("#{head_img}",String.valueOf(t.getHeadImg()));
 		sql  = sql.replace("#{client_name}",String.valueOf(t.getClientName()));
 		sql  = sql.replace("#{invite_num}",String.valueOf(t.getInviteNum()));
 		sql  = sql.replace("#{join_num}",String.valueOf(t.getJoinNum()));
 		sql  = sql.replace("#{win_num}",String.valueOf(t.getWinNum()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{login_time}",String.valueOf(t.getLoginTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}