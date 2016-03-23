package com.lydb.entity.account_log;

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
 * @Description: 收支对账单
 * @author onlineGenerator
 * @date 2015-12-01 21:04:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "account_log", schema = "")
@SuppressWarnings("serial")
public class AccountLogEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**账单生成时间*/
	@Excel(name="账单生成时间")
	private java.util.Date createTime;
	/**账单内容描述*/
	@Excel(name="账单内容描述")
	private java.lang.String content;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String orderNum;
	/**商家id*/
	@Excel(name="商家id")
	private java.lang.String dbWebBusinessid;
	/**商家手机号*/
	@Excel(name="商家手机号")
	private java.lang.String businessMobile;
	/**金额 元*/
	@Excel(name="金额 元")
	private java.lang.Double rmb;
	/**收支类型*/
	@Excel(name="收支类型")
	private java.lang.Integer accountType;
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  账单生成时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  账单生成时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账单内容描述
	 */
	@Column(name ="CONTENT",nullable=true,length=100)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账单内容描述
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="ORDER_NUM",nullable=true,length=36)
	public java.lang.String getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderNum(java.lang.String orderNum){
		this.orderNum = orderNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家id
	 */
	@Column(name ="DB_WEB_BUSINESSID",nullable=true,length=36)
	public java.lang.String getDbWebBusinessid(){
		return this.dbWebBusinessid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家id
	 */
	public void setDbWebBusinessid(java.lang.String dbWebBusinessid){
		this.dbWebBusinessid = dbWebBusinessid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家手机号
	 */
	@Column(name ="BUSINESS_MOBILE",nullable=true,length=15)
	public java.lang.String getBusinessMobile(){
		return this.businessMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家手机号
	 */
	public void setBusinessMobile(java.lang.String businessMobile){
		this.businessMobile = businessMobile;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额 元
	 */
	@Column(name ="RMB",nullable=true,length=32)
	public java.lang.Double getRmb(){
		return this.rmb;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  金额 元
	 */
	public void setRmb(java.lang.Double rmb){
		this.rmb = rmb;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  收支类型
	 */
	@Column(name ="ACCOUNT_TYPE",nullable=true,length=4)
	public java.lang.Integer getAccountType(){
		return this.accountType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  收支类型
	 */
	public void setAccountType(java.lang.Integer accountType){
		this.accountType = accountType;
	}
}
