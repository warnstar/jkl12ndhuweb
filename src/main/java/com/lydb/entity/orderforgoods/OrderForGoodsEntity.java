package com.lydb.entity.orderforgoods;

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
 * @Description: 用户的普通商品订单表
 * @author onlineGenerator
 * @date 2015-12-15 14:14:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "order_for_goods", schema = "")
@SuppressWarnings("serial")
public class OrderForGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**跟那个single商品有关*/
	@Excel(name="跟那个single商品有关")
	private java.lang.String dbGoodsSingleid;
	/**那个用户的订单*/
	@Excel(name="那个用户的订单")
	private java.lang.String dbAppClientid;
	/**用户的购买个数*/
	@Excel(name="用户的购买个数")
	private java.lang.Integer rmbNum;
	/**用户参加的ip地址*/
	@Excel(name="用户参加的ip地址")
	private java.lang.String ipAddress;
	/**ip地址对应的地理信息*/
	@Excel(name="ip地址对应的地理信息")
	private java.lang.String addressInfo;
	/**参加的时间*/
	@Excel(name="参加的时间")
	private java.lang.String createTime;
	/**
	 * 本次购买的夺宝号
	 */
	private  java.lang.String orderCode;
	
	
	@Column(name ="ORDER_CODE",nullable=true)
	public java.lang.String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(java.lang.String orderCode) {
		this.orderCode = orderCode;
	}

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
	 *@return: java.lang.String  跟那个single商品有关
	 */
	@Column(name ="DB_GOODS_SINGLEID",nullable=true,length=36)
	public java.lang.String getDbGoodsSingleid(){
		return this.dbGoodsSingleid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  跟那个single商品有关
	 */
	public void setDbGoodsSingleid(java.lang.String dbGoodsSingleid){
		this.dbGoodsSingleid = dbGoodsSingleid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  那个用户的订单
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  那个用户的订单
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户的购买个数
	 */
	@Column(name ="RMB_NUM",nullable=true,length=32)
	public java.lang.Integer getRmbNum(){
		return this.rmbNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户的购买个数
	 */
	public void setRmbNum(java.lang.Integer rmbNum){
		this.rmbNum = rmbNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户参加的ip地址
	 */
	@Column(name ="IP_ADDRESS",nullable=true,length=32)
	public java.lang.String getIpAddress(){
		return this.ipAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户参加的ip地址
	 */
	public void setIpAddress(java.lang.String ipAddress){
		this.ipAddress = ipAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip地址对应的地理信息
	 */
	@Column(name ="ADDRESS_INFO",nullable=true,length=32)
	public java.lang.String getAddressInfo(){
		return this.addressInfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip地址对应的地理信息
	 */
	public void setAddressInfo(java.lang.String addressInfo){
		this.addressInfo = addressInfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参加的时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加的时间
	 */
	public void setCreateTime(java.lang.String createTime){
		this.createTime = createTime;
	}
}
