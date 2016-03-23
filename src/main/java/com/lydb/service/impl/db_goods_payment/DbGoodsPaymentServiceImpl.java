package com.lydb.service.impl.db_goods_payment;
import com.lydb.service.db_goods_payment.DbGoodsPaymentServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.lydb.entity.db_goods_payment.DbGoodsPaymentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("dbGoodsPaymentService")
@Transactional
public class DbGoodsPaymentServiceImpl extends CommonServiceImpl implements DbGoodsPaymentServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbGoodsPaymentEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbGoodsPaymentEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbGoodsPaymentEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbGoodsPaymentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbGoodsPaymentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbGoodsPaymentEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbGoodsPaymentEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{db_goodsid}",String.valueOf(t.getDbGoodsid()));
 		sql  = sql.replace("#{deposit}",String.valueOf(t.getDeposit()));
 		sql  = sql.replace("#{starttime}",String.valueOf(t.getStarttime()));
 		sql  = sql.replace("#{servicefee}",String.valueOf(t.getServicefee()));
 		sql  = sql.replace("#{payment_status}",String.valueOf(t.getPaymentStatus()));
 		sql  = sql.replace("#{endtime}",String.valueOf(t.getEndtime()));
 		sql  = sql.replace("#{order_num}",String.valueOf(t.getOrderNum()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}