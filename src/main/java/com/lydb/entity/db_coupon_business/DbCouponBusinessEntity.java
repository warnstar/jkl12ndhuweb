package com.lydb.entity.db_coupon_business;

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
 * @Description: 用户优惠券表
 * @author onlineGenerator
 * @date 2015-11-25 10:26:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_coupon_business", schema = "")
@SuppressWarnings("serial")
public class DbCouponBusinessEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**优惠券面值*/
	@Excel(name="优惠券面值")
	private java.lang.Integer couponValue;
	/**优惠券地址*/
	@Excel(name="优惠券地址")
	private java.lang.String couponUrl;
	/**商店名称*/
	@Excel(name="商店名称")
	private java.lang.String businessName;
	/**商品主表id*/
	@Excel(name="商品主表id")
	private java.lang.String dbGoodsid;

	@Excel(name="生成时间")
	private java.util.Date time;
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生成时间
	 */
	@Column(name ="TIME",nullable=true,length=32)
	public java.util.Date getTime(){
		return this.time;
	}
	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生成时间
	 */
	public void setTime(java.util.Date time){
		this.time = time;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  优惠券面值
	 */
	@Column(name ="COUPON_VALUE",nullable=false,length=11)
	public java.lang.Integer getCouponValue(){
		return this.couponValue;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  优惠券面值
	 */
	public void setCouponValue(java.lang.Integer couponValue){
		this.couponValue = couponValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠券地址
	 */
	@Column(name ="COUPON_URL",nullable=false,length=100)
	public java.lang.String getCouponUrl(){
		return this.couponUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券地址
	 */
	public void setCouponUrl(java.lang.String couponUrl){
		this.couponUrl = couponUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商店名称
	 */
	@Column(name ="BUSINESS_NAME",nullable=false,length=32)
	public java.lang.String getBusinessName(){
		return this.businessName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商店名称
	 */
	public void setBusinessName(java.lang.String businessName){
		this.businessName = businessName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品主表id
	 */
	@Column(name ="DB_GOODSID",nullable=true,length=36)
	public java.lang.String getDbGoodsid(){
		return this.dbGoodsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品主表id
	 */
	public void setDbGoodsid(java.lang.String dbGoodsid){
		this.dbGoodsid = dbGoodsid;
	}
}
