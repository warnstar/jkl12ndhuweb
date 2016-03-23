package com.lydb.service.db_goods;
import com.lydb.entity.db_goods.DbGoodsEntity;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbGoodsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbGoodsEntity t);
 	
 	/**
 	 * 未开奖的商品的封装
 	 */
	public JSONObject getNotPrizeGoods(DbGoodsEntity pageObj, DataGrid dataGrid);

	/**
	 * 已开奖的商品封装
	 * 
	 */
	public JSONObject getIsPrizeGoods(DbGoodsEntity pageObj, DataGrid dataGrid);
	/**
	 * 需返还保证金的商品
	 */
	public JSONObject getReturnGoods(DbGoodsEntity pageObj, DataGrid dataGrid);
	/**
	 * 参加的用户列表
	 */
	public JSONObject getcheckJoinClient(String id,String usermoblie,String dbcode,DataGrid dataGrid);
	
	//保存商品实体
	public void saveForWeb(DbGoodsEntity entity,String id);
}
