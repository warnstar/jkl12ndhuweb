package com.lydb.service.db_goods_single;
import com.lydb.entity.db_goods_single.DbGoodsSingleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface DbGoodsSingleServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbGoodsSingleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbGoodsSingleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbGoodsSingleEntity t);
 	
 	/**
 	 * 抢币购买操作，能进行事务回滚
 	 */
	public String doBuyGoods(String clientid ,String singleGoodsId ,int rmb) ;
	/**
 	 * 在线购买操作，能进行事务回滚
 	 */
	public String doOnliBuyGoods(String clientid ,String singleGoodsId ,int rmb);
	/**
	 * 后台进行修改幸运号,能进行事务操作
	 */
	public void dochangeLuckId(String singlegoodsId, String luckyId) throws Exception;

	/**
	 * 根据传入的goodsId,返回一个DBgoodsSingleEntity
	 */
	public DbGoodsSingleEntity searchByGoodsid(String GoodsId);
	/**
	 * 根据用户的id，返回用户剩余的抢币个数
	 */
	public int userRMBById(String userId);
	/**
	 * 因为购买失败，将用户的钱，返还到抢币中
	 */
	public void returnByRMB(String userId,int num);
}
