package com.lydb.entity.db_ship_address;

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
 * @Description: 详细地址表
 * @author onlineGenerator
 * @date 2015-11-25 10:25:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_ship_address", schema = "")
@SuppressWarnings("serial")
public class DbShipAddressEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**城市名*/
	@Excel(name="城市名")
	private java.lang.String cityId;
	/**详细地址*/
	@Excel(name="详细地址")
	private java.lang.String shipAddress;
	/**收货人名*/
	@Excel(name="收货人名")
	private java.lang.String shipName;
	/**收货人手机号*/
	@Excel(name="收货人手机号")
	private java.lang.String shipPhone;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**
	 * 是否第一次填写
	 */
	private java.lang.Integer giveRmb;
	
	@Column(name ="GIVE_RMB",nullable=true,length=4)
	public java.lang.Integer getGiveRmb() {
		return giveRmb;
	}

	public void setGiveRmb(java.lang.Integer giveRmb) {
		this.giveRmb = giveRmb;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市名
	 */
	@Column(name ="CITY_ID",nullable=true,length=36)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市名
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="SHIP_ADDRESS",nullable=true,length=100)
	public java.lang.String getShipAddress(){
		return this.shipAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  详细地址
	 */
	public void setShipAddress(java.lang.String shipAddress){
		this.shipAddress = shipAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人名
	 */
	@Column(name ="SHIP_NAME",nullable=true,length=32)
	public java.lang.String getShipName(){
		return this.shipName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人名
	 */
	public void setShipName(java.lang.String shipName){
		this.shipName = shipName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人手机号
	 */
	@Column(name ="SHIP_PHONE",nullable=true,length=15)
	public java.lang.String getShipPhone(){
		return this.shipPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人手机号
	 */
	public void setShipPhone(java.lang.String shipPhone){
		this.shipPhone = shipPhone;
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
}
