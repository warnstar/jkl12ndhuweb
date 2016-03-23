package com.lydb.service.impl.db_goods;
import com.lydb.service.db_goods.DbGoodsServiceI;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

import com.lydb.entity.db_goods.DbGoodsEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

@Service("dbGoodsService")
@Transactional
public class DbGoodsServiceImpl extends CommonServiceImpl implements DbGoodsServiceI {
	
	@Autowired
	private JdbcDao jdbcDao;

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DbGoodsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((DbGoodsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((DbGoodsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	public boolean doAddSql(DbGoodsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	public boolean doUpdateSql(DbGoodsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	public boolean doDelSql(DbGoodsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DbGoodsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{db_web_businessid}",String.valueOf(t.getDbWebBusinessid()));
 		sql  = sql.replace("#{goods_name}",String.valueOf(t.getGoodsName()));
 		sql  = sql.replace("#{goods_rmb}",String.valueOf(t.getGoodsRmb()));
 		sql  = sql.replace("#{goods_num}",String.valueOf(t.getGoodsNum()));
 		sql  = sql.replace("#{goods_content}",String.valueOf(t.getGoodsContent()));
 		sql  = sql.replace("#{goods_headurl}",String.valueOf(t.getGoodsHeadurl()));
 		sql  = sql.replace("#{goods_type}",String.valueOf(t.getGoodsType()));
 		sql  = sql.replace("#{goods_10}",String.valueOf(t.getGoods10()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{share_num}",String.valueOf(t.getShareNum()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

 	/*
 	 * 	未开奖商品列表
 	 */
	@Override
	public JSONObject getNotPrizeGoods(DbGoodsEntity pageObj, DataGrid dataGrid) {
		String sqlWhere = getSqlWhere1(pageObj);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "SELECT count(*) " +
				"FROM (db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_single AS s ON g.`id`=s.`db_goodsid` " +
				"WHERE g.`status`='5' AND (s.`status`=0 OR s.`status`=1) AND g.`db_web_businessid` LIKE '%" +
				pageObj.getDbWebBusinessid()+"%' ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " and" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "SELECT s.`id`,s.goods_all_num,g.`goods_headurl`,s.`goods_current_num`,s.`current_join_num`,b.shopname,g.goods_name,s.`star_time`,g.`goods_rmb`,g.`serial_num`,g.`create_time`,s.`status` as singlestatus, TIMESTAMPDIFF(SECOND,NOW(),s.`open_time`) AS opentime " +
		"FROM (db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_single AS s ON g.`id`=s.`db_goodsid` " +
		"WHERE g.`status`='5' AND (s.`status`=0 OR s.`status`=1) AND g.`db_web_businessid` LIKE '%" +
		pageObj.getDbWebBusinessid() +"%' ";
		if (!sqlWhere.isEmpty()) {
			sql += " and" + sqlWhere;
		}
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("goods_all_num")
				,new Db2Page("goods_current_num")
				,new Db2Page("current_join_num")
				,new Db2Page("shopname")
				,new Db2Page("goods_name")
				,new Db2Page("star_time")
				,new Db2Page("goods_rmb")
				,new Db2Page("serial_num")
				,new Db2Page("create_time")
				,new Db2Page("goods_headurl")
				,new Db2Page("singlestatus")
				,new Db2Page("opentime")
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		// end of 方式3 ========================================= */

	}
	// 拼查询条件（where语句）
	String getSqlWhere1(DbGoodsEntity pageObj) {
		// 拼出条件语句
		String sqlWhere = "";
		if (StringUtil.isNotEmpty(pageObj.getGoodsName())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " g.goods_name like '%" + pageObj.getGoodsName() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getSerialNum())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " g.serial_num like '%" + pageObj.getSerialNum() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getStatus())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " s.`status` like '%" + pageObj.getStatus() + "%'";
		}
		
		return sqlWhere;
	}
	//保存商品的实习
	@Override
	public void saveForWeb(DbGoodsEntity entity,String id){
		try {
			
			 getSession().save (entity);
			 getSession().flush();
			 getSession().clear();
			 entity.setId(id);
			 getSession().saveOrUpdate(entity);
		} catch (RuntimeException e) {
			throw e;
		}
	}

 	/*
 	 * 	已开奖商品列表
 	 */
	@Override
	public JSONObject getIsPrizeGoods(DbGoodsEntity pageObj, DataGrid dataGrid) {
	String sqlWhere = getSqlWhere2(pageObj);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt ="SELECT count(*) " +
				"FROM (db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_single AS s ON g.`id`=s.`db_goodsid` " +
				"WHERE (g.`status`='5' OR g.`status`='6' ) AND s.`status`>=2 AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
				"%' " ;
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " and" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "SELECT s.`id`,s.goods_all_num,g.`goods_headurl`,s.`goods_current_num`,b.shopname,g.goods_name,g.`goods_rmb`,g.`serial_num`,g.`create_time`,s.`lucky_id`,s.`open_time`,s.`share`,s.`star_time`,s.`status` as singlestatus ,ac.`mobile` " +
		"FROM (((db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_single AS s ON g.`id`=s.`db_goodsid`)LEFT JOIN db_app_client AS ac ON ac.`id`=s.`db_app_clientid`) " +
		"WHERE (g.`status`='5' OR g.`status`='6' ) AND s.`status`>=2 AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
		"%' ";
		if (!sqlWhere.isEmpty()) {
			sql += " and" + sqlWhere;
		}
		sql=sql+" ORDER BY s.`open_time` DESC ";
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("goods_all_num")
				,new Db2Page("goods_current_num")
				,new Db2Page("shopname")
				,new Db2Page("goods_name")
				,new Db2Page("goods_rmb")
				,new Db2Page("serial_num")
				,new Db2Page("create_time")
				,new Db2Page("lucky_id")
				,new Db2Page("open_time")
				,new Db2Page("share")
				,new Db2Page("star_time")
				,new Db2Page("goods_headurl")
				,new Db2Page("singlestatus")
                ,new Db2Page("mobile")
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		// end of 方式3 ========================================= */

	}
	// 拼查询条件（where语句）
	String getSqlWhere2(DbGoodsEntity pageObj) {
		// 拼出条件语句
		String sqlWhere = "";
		if (StringUtil.isNotEmpty(pageObj.getGoodsName())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " g.goods_name like '%" + pageObj.getGoodsName() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getSerialNum())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " g.serial_num like '%" + pageObj.getSerialNum() + "%'";
		}
		if (StringUtil.isNotEmpty(pageObj.getStatus())) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " s.`status` like '%" + pageObj.getStatus() + "%'";
		}
		return sqlWhere;
	}

/*
 * 需返还保证金的
 *  */
	@Override
	public JSONObject getReturnGoods(DbGoodsEntity pageObj, DataGrid dataGrid) {
		String sqlWhere = getSqlWhere2(pageObj);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt ="SELECT count(*) " +
				"FROM (db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_payment AS p ON g.`id`=p.`db_goodsid` " +
				"WHERE g.`status`='7' AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
				"%' " ;
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " and" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "SELECT g.*,p.`deposit`,b.`shopname`,b.`mobile` " +
		"FROM (db_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_goods_payment AS p ON g.`id`=p.`db_goodsid` " +
		"WHERE g.`status`='7' AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
		"%' " ;
		if (!sqlWhere.isEmpty()) {
			sql += " and" + sqlWhere;
		}
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("deposit")
				,new Db2Page("shopname")
				,new Db2Page("goods_name")
				,new Db2Page("goods_rmb")
				,new Db2Page("goods_num")
				,new Db2Page("serial_num")
				,new Db2Page("create_time")
				,new Db2Page("goods_headurl")
				,new Db2Page("mobile")
		
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		// end of 方式3 ========================================= */
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 以下各函数可以提成共用部件 (Add by Quainty)
	/**
	 * 返回easyUI的DataGrid数据格式的JSONObject对象
	 * @param mapList : 从数据库直接取得的结果集列表
	 * @param iTotalCnt : 从数据库直接取得的结果集总数据条数
	 * @param dataExchanger : 页面表示数据与数据库字段的对应关系列表
	 * @return JSONObject
	 */
	public JSONObject getJsonDatagridEasyUI(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger) {
		//easyUI的dataGrid方式  －－－－这部分可以提取成统一处理
		String jsonTemp = "{\'total\':" + iTotalCnt + ",\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if (objValue == null) {
					jsonTemp += "null";
				} else {
					jsonTemp += "'" + objValue + "'";
				}
			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}
	
	// 数据变换的统一接口
	interface IMyDataExchanger {
		public Object exchange(Object value);
	}
	// 页面表示数据与数据库字段的对应关系
	class Db2Page {
		String fieldPage;		// 页面的fieldID
		String columnDB;		// 数据库的字段名
		IMyDataExchanger dataExchanger;		// 数据变换
		
		// 构造函数1：当页面的fieldID与数据库字段一致时（数据也不用变换）
		public Db2Page(String fieldPage) {
			this.fieldPage = fieldPage;
			this.columnDB = fieldPage;
			this.dataExchanger = null;
		}
		// 构造函数2：当页面的fieldID与数据库字段不一致时（数据不用变换）
		public Db2Page(String fieldPage, String columnDB) {
			this.fieldPage = fieldPage;
			if (columnDB == null) {// 与fieldPage相同
				this.columnDB = fieldPage;
			} else {
				this.columnDB = columnDB;
			}
			this.dataExchanger = null;
		}
		// 构造函数3：当页面的fieldID与数据库字段不一致，且数据要进行变换（当然都用这个构造函数也行）
		public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger) {
			this.fieldPage = fieldPage;
			if (columnDB == null) {// 与fieldPage相同
				this.columnDB = fieldPage;
			} else {
				this.columnDB = columnDB;
			}
			this.dataExchanger = dataExchanger;
		}
		
		/**
		 * 取页面表示绑定的fieldID
		 */
		public String getKey() {
			return fieldPage;
		}
		
		/**
		 * 取页面表示对应的值
		 * @param mapDB : 从数据库直接取得的结果集(一条数据的MAP)
		 * @return Object : 页面表示对应的值
		 */
		public Object getData(Map mapDB) {
			Object objValue = mapDB.get(columnDB);
			if (objValue == null) {
				return null;
			} else {
				if (dataExchanger != null) {
					return dataExchanger.exchange(objValue);
				} else {
					return objValue;
				}
			}
		}
	}
	
	// 性别的数据变换实体
	class MyDataExchangerSex implements IMyDataExchanger {
		public Object exchange(Object value) {
			if (value == null) {
				return "";
			} else if ("0".equals(value.toString())) {
				return "男";
			} else {
				return "女";
			}
		}
	}

	/**
	 * 用来获得一个商品的所有参与者的详情
	 */
	@Override
	public JSONObject getcheckJoinClient(String id,String usermoblie, String dbcode, DataGrid dataGrid) {
		
		String sqlWhere = getSqlWhere3(usermoblie,dbcode);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "SELECT count(*) " +
				"FROM order_for_goods as og	LEFT JOIN db_app_client AS ac ON og.db_app_clientid =ac.id " +
				"WHERE 1=1 and og.db_goods_singleid ='"+id+"' " 
				;
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " and" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		int result=executeSql("SET SESSION group_concat_max_len=102400");
		// 取出当前页的数据 
		String sql = "SELECT ac.id,ac.mobile,ac.client_name,og.create_time,SUM(og.rmb_num) AS joinnum,group_concat(og.order_code) as allcode,ac.win_num,ac.join_num,og.ip_address,og.address_info " +
		"FROM order_for_goods as og	LEFT JOIN db_app_client AS ac ON og.db_app_clientid =ac.id " +
		"WHERE 1=1 and og.db_goods_singleid ='"+id+"' ";
		if (!sqlWhere.isEmpty()) {
			sql += " and" + sqlWhere;
		}
		sql=sql+" GROUP BY ac.id ORDER BY og.create_time DESC ";
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("mobile")
				,new Db2Page("client_name")
				,new Db2Page("create_time")
				,new Db2Page("allcode")
				,new Db2Page("joinnum")
				,new Db2Page("win_num")
				,new Db2Page("join_num")
				,new Db2Page("ip_address")
				,new Db2Page("address_info")
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		// end of 方式3 ========================================= */
	}
	// 拼查询条件（where语句）
	String getSqlWhere3(String usermoblie, String dbcode) {
		// 拼出条件语句
		String sqlWhere = "";
		if (StringUtil.isNotEmpty(usermoblie)){
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " ac.mobile like '%" +usermoblie+ "%'";
		}
		if (StringUtil.isNotEmpty(dbcode) ){
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " og.order_code like '%" + dbcode+ "%'";
		}
		return sqlWhere;
	}
}