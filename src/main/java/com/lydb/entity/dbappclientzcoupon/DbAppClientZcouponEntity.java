package com.lydb.entity.dbappclientzcoupon;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 用户的0元优惠卷关系表
 * @author onlineGenerator
 * @date 2015-12-08 17:04:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_app_client_zcoupon", schema = "")
@SuppressWarnings("serial")
public class DbAppClientZcouponEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**对应的用户id*/
	@Excel(name="对应的用户id")
	private java.lang.String dbAppClientid;
	/**对应的0元商品优惠卷表id*/
	@Excel(name="对应的0元商品优惠卷表id")
	private java.lang.String dbZcouponBusinessid;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  对应的用户id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=false,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  对应的用户id
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  对应的0元商品优惠卷表id
	 */
	@Column(name ="DB_ZCOUPON_BUSINESSID",nullable=false,length=36)
	public java.lang.String getDbZcouponBusinessid(){
		return this.dbZcouponBusinessid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  对应的0元商品优惠卷表id
	 */
	public void setDbZcouponBusinessid(java.lang.String dbZcouponBusinessid){
		this.dbZcouponBusinessid = dbZcouponBusinessid;
	}
}
