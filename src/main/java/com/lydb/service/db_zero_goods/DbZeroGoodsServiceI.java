package com.lydb.service.db_zero_goods;
import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface DbZeroGoodsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DbZeroGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DbZeroGoodsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DbZeroGoodsEntity t);
 	/**
 	 * 未开奖的商品的封装
 	 */
	public JSONObject getNotPrizeZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid);

	/**
	 * 已开奖的商品封装
	 * 
	 */
	public JSONObject getIsPrizeZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid);
	/**
	 * 需返还保证金的商品
	 */
	public JSONObject getReturnZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid);

}
