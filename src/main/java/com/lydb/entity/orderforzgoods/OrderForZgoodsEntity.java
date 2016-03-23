package com.lydb.entity.orderforzgoods;

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
 * @Description: 用户的零元商品下单表
 * @author onlineGenerator
 * @date 2015-12-15 14:14:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "order_for_zgoods", schema = "")
@SuppressWarnings("serial")
public class OrderForZgoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**零元商品的single的主键*/
	@Excel(name="零元商品的single的主键")
	private java.lang.String dbZgoodsSingleid;
	/**参加的用户id*/
	@Excel(name="参加的用户id")
	private java.lang.String dbAppClientid;
	/**参加购买的个数*/
	@Excel(name="参加购买的个数")
	private java.lang.Integer rmbNum;
	/**参加时的ip地址信息*/
	@Excel(name="参加时的ip地址信息")
	private java.lang.String ipAddress;
	/**ip地址对应的地理信息*/
	@Excel(name="ip地址对应的地理信息")
	private java.lang.String addressInfo;
	/**参加时的时间  精确到毫秒*/
	@Excel(name="参加时的时间  精确到毫秒")
	private java.lang.String createTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=20)
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
	 *@return: java.lang.String  零元商品的single的主键
	 */
	@Column(name ="DB_ZGOODS_SINGLEID",nullable=false,length=36)
	public java.lang.String getDbZgoodsSingleid(){
		return this.dbZgoodsSingleid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零元商品的single的主键
	 */
	public void setDbZgoodsSingleid(java.lang.String dbZgoodsSingleid){
		this.dbZgoodsSingleid = dbZgoodsSingleid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参加的用户id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加的用户id
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  参加购买的个数
	 */
	@Column(name ="RMB_NUM",nullable=true,length=32)
	public java.lang.Integer getRmbNum(){
		return this.rmbNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  参加购买的个数
	 */
	public void setRmbNum(java.lang.Integer rmbNum){
		this.rmbNum = rmbNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参加时的ip地址信息
	 */
	@Column(name ="IP_ADDRESS",nullable=true,length=32)
	public java.lang.String getIpAddress(){
		return this.ipAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加时的ip地址信息
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
	 *@return: java.lang.String  参加时的时间  精确到毫秒
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加时的时间  精确到毫秒
	 */
	public void setCreateTime(java.lang.String createTime){
		this.createTime = createTime;
	}
}
