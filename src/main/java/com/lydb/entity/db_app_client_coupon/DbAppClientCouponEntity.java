package com.lydb.entity.db_app_client_coupon;

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
 * @Description: 优惠券和用户关系表
 * @author onlineGenerator
 * @date 2015-11-25 10:27:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_app_client_coupon", schema = "")
@SuppressWarnings("serial")
public class DbAppClientCouponEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**优惠券表id*/
	@Excel(name="优惠券表id")
	private java.lang.String dbCouponBusinessid;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户主表id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=false,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户主表id
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠券表id
	 */
	@Column(name ="DB_COUPON_BUSINESSID",nullable=false,length=36)
	public java.lang.String getDbCouponBusinessid(){
		return this.dbCouponBusinessid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券表id
	 */
	public void setDbCouponBusinessid(java.lang.String dbCouponBusinessid){
		this.dbCouponBusinessid = dbCouponBusinessid;
	}
}
