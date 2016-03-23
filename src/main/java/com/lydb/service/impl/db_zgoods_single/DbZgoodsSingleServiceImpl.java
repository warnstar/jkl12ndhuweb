package com.lydb.service.impl.db_zgoods_single;
import com.lydb.service.db_zgoods_single.DbZgoodsSingleServiceI;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jeecgframework.core.common.dao.impl.CommonDao;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;

import com.lydb.entity.db_join_client2.DbJoinClient2Entity;
import com.lydb.entity.db_zgoods_single.DbZgoodsSingleEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("dbZgoodsSingleService")
@Transactional(rollbackFor = Exception.class)
public class DbZgoodsSingleServiceImpl extends CommonServiceImpl implements DbZgoodsSingleServiceI {

	   @Autowired
	   private CommonDao commonDao;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbZgoodsSingleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbZgoodsSingleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbZgoodsSingleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbZgoodsSingleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbZgoodsSingleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbZgoodsSingleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbZgoodsSingleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{goods_all_num}",String.valueOf(t.getGoodsAllNum()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{goods_current_num}",String.valueOf(t.getGoodsCurrentNum()));
 		sql  = sql.replace("#{star_time}",String.valueOf(t.getStarTime()));
 		sql  = sql.replace("#{share}",String.valueOf(t.getShare()));
 		sql  = sql.replace("#{current_join_num}",String.valueOf(t.getCurrentJoinNum()));
 		sql  = sql.replace("#{lucky_id}",String.valueOf(t.getLuckyId()));
 		sql  = sql.replace("#{db_app_clientid}",String.valueOf(t.getDbAppClientid()));
 		sql  = sql.replace("#{db_zero_goodsid}",String.valueOf(t.getDbZeroGoodsid()));
 		sql  = sql.replace("#{open_time}",String.valueOf(t.getOpenTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public String getcode(String clientid, String singleid)throws Exception  {
		
			
		//获得singleid对象
		DbZgoodsSingleEntity single = (DbZgoodsSingleEntity)commonDao.get(DbZgoodsSingleEntity.class, singleid);
		//参与人数加一
		single.setCurrentJoinNum(single.getCurrentJoinNum()+1);
		DbJoinClient2Entity join=new DbJoinClient2Entity();
		
		join.setDbAppClientid(clientid);
		join.setDbCode(String.valueOf(10000000+single.getCurrentJoinNum()));
		join.setDbZgoodsSingleid(singleid);
		join.setJoinTime(DateUtils.datemillFormat.format(DateUtils.getDate()));
		
		commonDao.saveOrUpdate(single);
		commonDao.save(join);
		
		return join.getDbCode();
	}

	@Override
	public void dochangeZeroLuckId(String singlegoodsId, String luckyId)
			throws Exception {
		DbZgoodsSingleEntity zgoodssingle=(DbZgoodsSingleEntity)commonDao.get(DbZgoodsSingleEntity.class, singlegoodsId);
		
		if(zgoodssingle==null ){
			throw new Exception();
		}else if(zgoodssingle.getCurrentJoinNum() == 0){
			throw new Exception();
		}
		//luckyId必须是在范围内
		if( Long.valueOf(luckyId)-10000000>zgoodssingle.getCurrentJoinNum() || Long.valueOf(luckyId)-10000000<=0){
			throw new Exception();
		}	
		//数据正常，开始后台修改，只改一个人保证数据正常
		Session session = commonDao.getSession();
        String hql2= "FROM  DbJoinClient2Entity j  WHERE j.dbCode = ? AND j.dbZgoodsSingleid = ? ";
        Query query2= session.createQuery(hql2);
		  query2.setString(0, luckyId);
		  query2.setString(1, singlegoodsId);
		  List<DbJoinClient2Entity> joinlist2 = query2.list();
		  DbJoinClient2Entity join=joinlist2.get(0);
		  
		  //保存中奖者信息
		  zgoodssingle.setDbAppClientid(join.getDbAppClientid());
		  zgoodssingle.setLuckyId(luckyId);
		  
		  commonDao.saveOrUpdate(zgoodssingle);
		 
	}
	
	
	
	
	
	
	
}