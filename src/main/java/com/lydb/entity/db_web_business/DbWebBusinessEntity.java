package com.lydb.entity.db_web_business;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 商家表
 * @author onlineGenerator
 * @date 2015-11-26 11:29:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_web_business", schema = "")
@SuppressWarnings("serial")
public class DbWebBusinessEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商家手机号*/
	@Excel(name="商家手机号")
	private java.lang.String mobile;
	/**商家名子*/
	@Excel(name="商家名子")
	private java.lang.String name;
	/**密码*/
	@Excel(name="密码")
	private java.lang.String password;
	/**商家店名*/
	@Excel(name="商家店名")
	private java.lang.String shopname;
	/**商家qq*/
	@Excel(name="商家qq")
	private java.lang.String businessQq;
	/**商家创建时间*/
	@Excel(name="商家创建时间")
	private java.util.Date createTime;
	/**商家状态*/
	@Excel(name="商家状态")
	private java.lang.String status;
	/**申请信息*/
	@Excel(name="申请信息")
	private java.lang.String information;
	/**申请商家的编号*/
	@Excel(name="申请商家的编号")
	private java.lang.Integer theNumber;
	
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
	 *@return: java.lang.String  商家手机号
	 */
	@Column(name ="MOBILE",nullable=false,length=15)
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家手机号
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家名子
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家名子
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密码
	 */
	@Column(name ="PASSWORD",nullable=false,length=50)
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密码
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家店名
	 */
	@Column(name ="SHOPNAME",nullable=true,length=50)
	public java.lang.String getShopname(){
		return this.shopname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家店名
	 */
	public void setShopname(java.lang.String shopname){
		this.shopname = shopname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家qq
	 */
	@Column(name ="BUSINESS_QQ",nullable=true,length=32)
	public java.lang.String getBusinessQq(){
		return this.businessQq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家qq
	 */
	public void setBusinessQq(java.lang.String businessQq){
		this.businessQq = businessQq;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  商家创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  商家创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家状态
	 */
	@Column(name ="STATUS",nullable=true,length=32)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请信息
	 */
	@Column(name ="INFORMATION",nullable=true,length=300)
	public java.lang.String getInformation(){
		return this.information;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请信息
	 */
	public void setInformation(java.lang.String information){
		this.information = information;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  申请商家的编号
	 */
	@Column(name ="THE_NUMBER",nullable=true,length=32)
	public java.lang.Integer getTheNumber(){
		return this.theNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  申请商家的编号
	 */
	public void setTheNumber(java.lang.Integer theNumber){
		this.theNumber = theNumber;
	}
}
