package com.lydb.service.impl.db_zero_goods;

import com.lydb.service.db_zero_goods.DbZeroGoodsServiceI;


import net.sf.json.JSONObject;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

import com.lydb.entity.db_goods.DbGoodsEntity;
import com.lydb.entity.db_zero_goods.DbZeroGoodsEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

@Service("dbZeroGoodsService")
@Transactional
public class DbZeroGoodsServiceImpl extends CommonServiceImpl implements DbZeroGoodsServiceI {
    @Autowired
    private JdbcDao jdbcDao;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((DbZeroGoodsEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((DbZeroGoodsEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((DbZeroGoodsEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     *
     * @param id
     * @return
     */
    public boolean doAddSql(DbZeroGoodsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     *
     * @param id
     * @return
     */
    public boolean doUpdateSql(DbZeroGoodsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     *
     * @param id
     * @return
     */
    public boolean doDelSql(DbZeroGoodsEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @return
     */
    public String replaceVal(String sql, DbZeroGoodsEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{zgoods_name}", String.valueOf(t.getZgoodsName()));
        sql = sql.replace("#{zgoods_rmb}", String.valueOf(t.getZgoodsRmb()));
        sql = sql.replace("#{zgoods_num}", String.valueOf(t.getZgoodsNum()));
        sql = sql.replace("#{zgoods_content}", String.valueOf(t.getZgoodsContent()));
        sql = sql.replace("#{zgoods_headurl}", String.valueOf(t.getZgoodsHeadurl()));
        sql = sql.replace("#{create_time}", String.valueOf(t.getCreateTime()));
        sql = sql.replace("#{status}", String.valueOf(t.getStatus()));
        sql = sql.replace("#{zserial_num}", String.valueOf(t.getZserialNum()));
        sql = sql.replace("#{db_web_businessid}", String.valueOf(t.getDbWebBusinessid()));
        sql = sql.replace("#{zserial_num}", String.valueOf(t.getZserialNum()));
        sql = sql.replace("#{zshare_num}", String.valueOf(t.getZshareNum()));
        sql = sql.replace("#{time}", String.valueOf(t.getTime()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /*
     * 	未开奖商品列表
     */
    @Override
    public JSONObject getNotPrizeZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid) {
        String sqlWhere = getSqlWhere1(pageObj);

        // 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
        String sqlCnt = "SELECT count(*)" +
                "FROM (db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_single AS s ON g.`id`=s.`db_zero_goodsid` " +
                "WHERE g.`status`='5' AND (s.`status`=0 OR s.`status`=1) AND g.`db_web_businessid` LIKE '%" +
                pageObj.getDbWebBusinessid() + "%' ";
        if (!sqlWhere.isEmpty()) {
            sqlCnt += " and" + sqlWhere;
        }
        Long iCount = getCountForJdbcParam(sqlCnt, null);

        // 取出当前页的数据
        String sql = "SELECT s.`id`,s.goods_all_num,g.`zgoods_headurl`,s.`goods_current_num`,s.`current_join_num`,b.shopname,g.zgoods_name,s.`star_time`,g.`zgoods_rmb`,g.`zserial_num`,g.`create_time`,s.`lucky_id`,s.`open_time`,g.`time` " +
                "FROM (db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_single AS s ON g.`id`=s.`db_zero_goodsid` " +
                "WHERE g.`status`='5' AND (s.`status`=0 OR s.`status`=1) AND g.`db_web_businessid` LIKE '%" +
                pageObj.getDbWebBusinessid() + "%' ";
        if (!sqlWhere.isEmpty()) {
            sql += " and" + sqlWhere;
        }
        List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

        // 将结果集转换成页面上对应的数据集
        Db2Page[] db2Pages = {
                new Db2Page("id")
                , new Db2Page("goods_all_num")
                , new Db2Page("goods_current_num")
                , new Db2Page("current_join_num")
                , new Db2Page("shopname")
                , new Db2Page("zgoods_name")
                , new Db2Page("star_time")
                , new Db2Page("zgoods_rmb")
                , new Db2Page("zserial_num")
                , new Db2Page("create_time")
                , new Db2Page("zgoods_headurl")
                , new Db2Page("lucky_id")
                , new Db2Page("open_time")
                , new Db2Page("time")
        };
        JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
        return jObject;
        // end of 方式3 ========================================= */

    }

    // 拼查询条件（where语句）
    String getSqlWhere1(DbZeroGoodsEntity pageObj) {
        // 拼出条件语句
        String sqlWhere = "";
        if (StringUtil.isNotEmpty(pageObj.getZgoodsName())) {
            if (!sqlWhere.isEmpty()) {
                sqlWhere += " and";
            }
            sqlWhere += " g.zgoods_name like '%" + pageObj.getZgoodsName() + "%'";
        }
        if (StringUtil.isNotEmpty(pageObj.getZserialNum())) {
            if (!sqlWhere.isEmpty()) {
                sqlWhere += " and";
            }
            sqlWhere += " g.zserial_num like '%" + pageObj.getZserialNum() + "%'";
        }
        return sqlWhere;
    }


    /*
     * 	已开奖商品列表
     */
    @Override
    public JSONObject getIsPrizeZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid) {
        String sqlWhere = getSqlWhere2(pageObj);

        // 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
        String sqlCnt = "SELECT count(*)" +
                "FROM (db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_single AS s ON g.`id`=s.`db_zero_goodsid` " +
                "WHERE (g.`status`='5' OR g.`status`='6' ) AND s.`status`>=2 AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
                "%' ";
        if (!sqlWhere.isEmpty()) {
            sqlCnt += " and" + sqlWhere;
        }
        Long iCount = getCountForJdbcParam(sqlCnt, null);

        // 取出当前页的数据
        String sql = "SELECT s.`id`,s.goods_all_num,g.`zgoods_headurl`,s.`goods_current_num`,b.shopname,g.zgoods_name,g.`zgoods_rmb`,g.`zserial_num`,g.`create_time`,s.`lucky_id`,s.`open_time`,s.`share`,s.`star_time`,s.`status` ,ac.`mobile` " +
                "FROM (((db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_single AS s ON g.`id`=s.`db_zero_goodsid`)LEFT JOIN db_app_client AS ac ON ac.`id`=s.`db_app_clientid`) " +
                "WHERE (g.`status`='5' OR g.`status`='6' ) AND s.`status`>=2 AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
                "%' ";
        if (!sqlWhere.isEmpty()) {
            sql += " and" + sqlWhere;
        }
        sql += " ORDER BY s.`open_time` DESC ";
        List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

        // 将结果集转换成页面上对应的数据集
        Db2Page[] db2Pages = {
                new Db2Page("id")
                , new Db2Page("goods_all_num")
                , new Db2Page("goods_current_num")
                , new Db2Page("shopname")
                , new Db2Page("zgoods_name")
                , new Db2Page("zgoods_rmb")
                , new Db2Page("zserial_num")
                , new Db2Page("create_time")
                , new Db2Page("lucky_id")
                , new Db2Page("open_time")
                , new Db2Page("share")
                , new Db2Page("star_time")
                , new Db2Page("zgoods_headurl")
                , new Db2Page("status")
                , new Db2Page("mobile")
        };
        JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
        return jObject;
        // end of 方式3 ========================================= */

    }

    // 拼查询条件（where语句）
    String getSqlWhere2(DbZeroGoodsEntity pageObj) {
        // 拼出条件语句
        String sqlWhere = "";
        if (StringUtil.isNotEmpty(pageObj.getZgoodsName())) {
            if (!sqlWhere.isEmpty()) {
                sqlWhere += " and";
            }
            sqlWhere += " g.zgoods_name like '%" + pageObj.getZgoodsName() + "%'";
        }
        if (StringUtil.isNotEmpty(pageObj.getZserialNum())) {
            if (!sqlWhere.isEmpty()) {
                sqlWhere += " and";
            }
            sqlWhere += " g.zserial_num like '%" + pageObj.getZserialNum() + "%'";
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
    public JSONObject getReturnZeroGoods(DbZeroGoodsEntity pageObj, DataGrid dataGrid) {
        String sqlWhere = getSqlWhere2(pageObj);

        // 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
        String sqlCnt = "SELECT count(*)" +
                "FROM (db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_payment AS p ON g.`id`=p.`db_zero_goodsid` " +
                "WHERE g.`status`='7' AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
                "%' ";
        if (!sqlWhere.isEmpty()) {
            sqlCnt += " and" + sqlWhere;
        }
        Long iCount = getCountForJdbcParam(sqlCnt, null);

        // 取出当前页的数据
        String sql = "SELECT g.*,p.`deposit`,b.`shopname`,b.`mobile`" +
                "FROM (db_zero_goods AS g LEFT JOIN db_web_business AS b ON g.`db_web_businessid`=b.`id`) LEFT JOIN  db_zgoods_payment AS p ON g.`id`=p.`db_zero_goodsid` " +
                "WHERE g.`status`='7' AND g.`db_web_businessid` LIKE '%" + pageObj.getDbWebBusinessid() +
                "%' ";
        if (!sqlWhere.isEmpty()) {
            sql += " and" + sqlWhere;
        }
        List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

        // 将结果集转换成页面上对应的数据集
        Db2Page[] db2Pages = {
                new Db2Page("id")
                , new Db2Page("deposit")
                , new Db2Page("shopname")
                , new Db2Page("zgoods_name")
                , new Db2Page("zgoods_rmb")
                , new Db2Page("zgoods_num")
                , new Db2Page("zserial_num")
                , new Db2Page("create_time")
                , new Db2Page("zgoods_headurl")
                , new Db2Page("mobile")

        };
        JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
        return jObject;
        // end of 方式3 ========================================= */
    }


    // 以下各函数可以提成共用部件 (Add by Quainty)

    /**
     * 返回easyUI的DataGrid数据格式的JSONObject对象
     *
     * @param mapList       : 从数据库直接取得的结果集列表
     * @param iTotalCnt     : 从数据库直接取得的结果集总数据条数
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
        String fieldPage;        // 页面的fieldID
        String columnDB;        // 数据库的字段名
        IMyDataExchanger dataExchanger;        // 数据变换

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
         *
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


}