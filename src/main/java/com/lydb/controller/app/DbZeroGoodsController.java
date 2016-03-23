package com.lydb.controller.app;

import com.lydb.entity.db_token.DbTokenEntity;
import com.lydb.service.db_zgoods_single.DbZgoodsSingleServiceI;
import com.xingluo.util.CheckList;
import com.xingluo.util.RestJson;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品展示的接口集合
 * 不需要验证token，可以直接访问
 */

@Scope("prototype")
@Controller
@RequestMapping(value = "/app_lydb/Zerogoods")
public class DbZeroGoodsController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DbZeroGoodsController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private DbZgoodsSingleServiceI DbZgoodsSingleService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static StringBuffer sql1 = new StringBuffer();
    private static StringBuffer sql2 = new StringBuffer();
    private static StringBuffer sql3 = new StringBuffer();
    private static StringBuffer sql4 = new StringBuffer();
    private static StringBuffer sql5 = new StringBuffer();

    static {
        //0元夺宝
        sql1.append("SELECT zs.`id` AS zgoodsId,zs.`current_join_num`,zs.`open_time`,zs.`star_time`,zg.`zgoods_headurl`,zg.`zgoods_name`,zg.`zgoods_rmb` ");
        sql1.append("FROM db_zgoods_single AS zs LEFT JOIN db_zero_goods AS zg ON zs.`db_zero_goodsid`=zg.`id` ");
        sql1.append("WHERE	zs.`status`=1 ");
        sql1.append("ORDER BY zs.`open_time` ASC ");

        //往期揭晓
        sql2.append("SELECT zs.`id` AS zgoodsId,zs.`current_join_num`,zs.`open_time`,zg.`zgoods_headurl`,zg.`zgoods_name`,zg.`zgoods_rmb` ");
        sql2.append("FROM db_zgoods_single AS zs LEFT JOIN db_zero_goods AS zg ON zs.`db_zero_goodsid`=zg.`id` ");
        sql2.append("WHERE	zs.`status`>1 ");
        sql2.append("ORDER BY zs.`open_time` DESC ");

        //所有的零元晒单
        sql3.append("SELECT sz.id AS shareid , ac.`head_img`,ac.`client_name`,sz.`share_time`,zg.`zgoods_name`,sz.`share_title`,sz.`share_content`,GROUP_CONCAT(iu.img_url) AS urlGroup ");
        sql3.append("FROM  ((((db_zgoods_single AS zs LEFT JOIN db_zero_goods AS zg ON zs.`db_zero_goodsid` = zg.`id`)  ");
        sql3.append("LEFT JOIN db_app_client AS ac ON ac.`id`=zs.`db_app_clientid`) ");
        sql3.append("LEFT JOIN db_share_zerogoods AS sz ON sz.`db_zgoods_singleid` = zs.`id` ) ");
        sql3.append("LEFT JOIN db_img_url AS iu ON sz.`id` = iu.`db_share_goodsid`) ");
        sql3.append("WHERE zs.`status`>1 AND zs.`share`=1 ");
        sql3.append("GROUP BY zs.`id` ");
        sql3.append("ORDER BY sz.`share_time` DESC ");

        //单个未开奖商品详情
        sql4.append("SELECT zs.`id` AS zgoodsId,zs.`current_join_num`,zs.`open_time`,img.img_url AS zgoods_headurl,zg.`zgoods_name`,zg.`zgoods_rmb`,(SELECT star_time from db_zgoods_single where db_zero_goodsid=zs.db_zero_goodsid ORDER BY star_time ASC limit 1) AS now ");
        sql4.append("FROM (db_zgoods_single AS zs LEFT JOIN db_zero_goods AS zg ON zs.`db_zero_goodsid`=zg.`id` )LEFT JOIN db_img_url AS img ON zg.id=img.db_share_goodsid ");
        sql4.append("WHERE	zs.`id`=? ");

        //单个已开奖商品详情
        sql5.append("SELECT zs.`id` AS zgoodsId,zs.`current_join_num`,zs.`open_time`,zs.`star_time`,zg.`zgoods_headurl`,zg.`zgoods_name`,zg.`zgoods_rmb`,zs.`lucky_id` ");
        sql5.append("FROM db_zgoods_single AS zs LEFT JOIN db_zero_goods AS zg ON zs.`db_zero_goodsid`=zg.`id` ");
        sql5.append("WHERE	zs.`id`=? ");
    }


    /**
     * 所有的零元夺宝的商品
     */
    @RequestMapping(value = "/AllZeroGoods", method = RequestMethod.POST)
    @ResponseBody
    public RestJson AllZeroGoods(int pageNum,@RequestHeader(value="token") String token) {
        RestJson rest = new RestJson();
    	DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
		if(dbtoken == null){
			//token不存在 ，返回未知错误
			rest.setError("您的登录过期，请重新登录");
			rest.setCode(7);
			return rest;
		}else{
	        try {
	            List<Map<String, Object>> info = systemService.findForJdbc(sql1.toString(), pageNum, 20);
	            for(Map<String, Object> m: info){
	            	String sql="SELECT id from db_join_client2 WHERE db_app_clientid=? && db_zgoods_singleid=? ";
	            	List<Map<String, Object>> check=systemService.findForJdbc(sql, dbtoken.getDbAppClientid(),m.get("zgoodsId"));
	            	if(CheckList.CheckNull(check)){
	            		m.put("isGet", 0);
	            	}else{
	            		m.put("isGet", 1);
	            	}
	            }
	            if(CheckList.CheckNull(info)){
	                info=null;
	            }
	            rest.setAttributes(info);
	            rest.setInfo(new java.util.Date(System.currentTimeMillis()));
	            rest.setCode(0);
	
	        } catch (Exception e) {
	            rest.setError("提交的参数出现异常！");
	            rest.setInfo(new java.util.Date(System.currentTimeMillis()));
	            rest.setCode(8);
	            e.printStackTrace();
	            return rest;
	        }
		}
        return rest;
    }

    /**
     * 往期揭晓的所有零元商品
     */
    @RequestMapping(value = "/AllIsPrizeZeroGoods", method = RequestMethod.POST)
    @ResponseBody
    public RestJson AllIsPrizeZeroGoods(int pageNum) {
        RestJson rest = new RestJson();
        try {
            List<Map<String, Object>> info = systemService.findForJdbc(sql2.toString(), pageNum, 20);
            if(CheckList.CheckNull(info)){
                info=null;
            }
            rest.setInfo(info);
            rest.setCode(0);

        } catch (Exception e) {
            rest.setError("提交的参数出现异常！");
            rest.setCode(8);
            e.printStackTrace();
            return rest;
        }
        return rest;
    }

    /**
     * 零元商品的获奖晒单
     */
    @RequestMapping(value = "/AllShareZeroGoods", method = RequestMethod.POST)
    @ResponseBody
    public RestJson AllShareZeroGoods(int pageNum) {
        RestJson rest = new RestJson();
        try {
            List<Map<String, Object>> info = systemService.findForJdbc(sql3.toString(), pageNum, 20);
            if(CheckList.CheckNull(info)){
                info=null;
            }
            rest.setInfo(info);
            rest.setCode(0);

        } catch (Exception e) {
            rest.setError("提交的参数出现异常！");
            rest.setCode(8);
            e.printStackTrace();
            return rest;
        }
        return rest;
    }

    /**
     * 零元夺宝
     * 如果已经夺宝过，直接返回该用户的夺宝号
     * 如果没有，则给一个夺宝号
     */

    @RequestMapping(value = "/getZeroCode", method = RequestMethod.POST)
    @ResponseBody
    public RestJson getZeroCode(String zgoodsId, @RequestHeader(value = "token") String token) {
        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if (dbtoken == null) {
            //token不存在 ，返回未知错误
            rest.setError("未知错误");
            rest.setCode(7);
            return rest;
        } else {
            //是否已经获得过
            String sqlcount = "SELECT COUNT(*) FROM db_join_client2 AS jc WHERE jc.`db_zgoods_singleid`=? AND jc.`db_app_clientid`=? ";
            Object[] object = {zgoodsId, dbtoken.getDbAppClientid()};
            Long count = systemService.getCountForJdbcParam(sqlcount, object);
            //商品详情
            List<Map<String, Object>> listmap = systemService.findForJdbc(sql4.toString(), zgoodsId);
          /*  for (Map<String,Object> m : listmap){
            	
            	m.put("now",new java.util.Date(System.currentTimeMillis()));
            }*/
            if (count >= 1) {

                //返回用户的夺宝号
                //用户的夺宝号
                List<Map<String, Object>> info = systemService.findForJdbc("SELECT db_code FROM db_join_client2 AS jc WHERE jc.`db_zgoods_singleid`=? AND jc.`db_app_clientid`=?", zgoodsId, dbtoken.getDbAppClientid());
                rest.setInfo(info);
            } else {
                String code = null;
                try {
                    code = DbZgoodsSingleService.getcode(dbtoken.getDbAppClientid(), zgoodsId);
                } catch (Exception e) {
                    rest.setError("出现异常，获取失败！");
                    rest.setCode(8);
                    return rest;
                }
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("db_code", code);
                list.add(map);
                rest.setInfo(list);
            }
            rest.setAttributes(listmap);
            rest.setCode(0);
        }


        return rest;
    }

    /**
     * 分享获得夺宝码
     */
    @RequestMapping(value = "/SharegetZeroCode", method = RequestMethod.POST)
    @ResponseBody
    public RestJson SharegetZeroCode(String zgoodsId, @RequestHeader(value = "token") String token) {
        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if (dbtoken == null) {
            //token不存在 ，返回未知错误
            rest.setError("未知错误");
            rest.setCode(7);
            return rest;
        } else {
            //是否已经获得过
            String sqlcount = "SELECT COUNT(*) FROM db_join_client2 AS jc WHERE jc.`db_zgoods_singleid`=? AND jc.`db_app_clientid`=? ";
            Object[] object = {zgoodsId, dbtoken.getDbAppClientid()};
            Long count = systemService.getCountForJdbcParam(sqlcount, object);
            if (count >= 2) {
                rest.setError("已经获得过夺宝吗！");
                rest.setCode(4);
            } else {
                String code = null;
                try {
                    code = DbZgoodsSingleService.getcode(dbtoken.getDbAppClientid(), zgoodsId);
                } catch (Exception e) {
                    rest.setError("出现异常，获取失败！");
                    rest.setCode(8);
                    return rest;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("db_code", code);
                rest.setInfo(map);
                rest.setCode(0);
            }

        }
        return rest;
    }

    /**
     * 往期揭晓的查看详情
     */
    @RequestMapping(value = "/ZeroSingleDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestJson ZeroSingleDetail(String zgoodsId, @RequestHeader(value = "token") String token) {
        RestJson rest = new RestJson();
        //通过token获得登录的用户
        DbTokenEntity dbtoken = systemService.findUniqueByProperty(DbTokenEntity.class, "tokenValue", token);
        if (dbtoken == null) {
            //token不存在 ，返回未知错误
            rest.setError("未知错误");
            rest.setCode(7);
            return rest;
        } else {
            //商品详情
            try {
                List<Map<String, Object>> listmap = systemService.findForJdbc(sql5.toString(), zgoodsId);
                List<Map<String, Object>> info = systemService.findForJdbc("SELECT db_code FROM db_join_client2 AS jc WHERE jc.`db_zgoods_singleid`=? AND jc.`db_app_clientid`=?", zgoodsId, dbtoken.getDbAppClientid());
                //用户的夺宝码，不管有没有
                if(CheckList.CheckNull(info)){
                    info=null;
                }
                rest.setInfo(info);
                //商品的详情
                rest.setAttributes(listmap);
                rest.setCode(0);
            } catch (Exception e) {
                rest.setCode(8);
                rest.setError("参数错误");
                // TODO Auto-generated catch block
                e.printStackTrace();
                return rest;
            }
        }
        return rest;
    }

}

